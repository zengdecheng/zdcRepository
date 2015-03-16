requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
		"v":"jqueryValidation/js/jquery.validationEngine.min",
		"vl":"jqueryValidation/js/jquery.validationEngine-zh_CN"
	}
});
var orderType;	//订单类型 2打版3大货
var wfStepIndex;//订单所在节点
var orderId;	//订单ID
var opt;		//autoFill配置
var index;		//当前所在index
var jsonData;	//后台传的数据
var activeIndex = "4"; //默认为大货节点
var isShowDetail=false;
define([ "v","vl" ], function(v,vl) {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var biz = {
		init : function() {
			biz.event.initOrderData();
			biz.event.jsonGetFourNode();
			biz.event.initTrackes();
			biz.event.initValidation();
		},
		bind : function() {
			$("#yidongBtn").on("click", biz.event.jsonSaveTracke);
			$("#processBtn").on("click", biz.event.processOrder); //保存并提交
			$("#saveBtn").on("click", biz.event.saveOrder); //保存草稿
			$(".download").on("click", biz.event.download); //下载文件
			$("#exportBtn").on("click", biz.event.exportExcel); //导出订单Excel
//			$(".price").on("keyup",biz.event.getTotalPrice);//自动计算出总金额
			$(document).on("keyup",".price",biz.event.getTotalPrice);//自动计算出总金额
			$(document).on("keyup",".numc",biz.event.getTotalPrice);//自动计算出总金额
//			$(".numc").on("keyup",biz.event.getTotalPrice);//自动计算出总金额
//			$(".price").on("keyup",biz.event.getTotal);//自动计算出合计
			$(document).on("keyup",".numc",biz.event.getTotal);//自动计算出合计
			$(document).on("keyup",".price",biz.event.getTotal);//自动计算出合计
			$(document).on("keyup",".test_price",biz.event.getTotal);//自动计算出合计
			$(document).on("keyup",".freight",biz.event.getTotal);//自动计算出合计
//			$(".orderNum,.unit_num").on("keyup",biz.event.calcNeedNum);//自动计算出需求数量
			$(document).on("keyup",".orderNum,.unit_num",biz.event.calcNeedNum);//自动计算出需求数量
//			$(".numc").on("keyup",biz.event.getTotal);//自动计算出合计
//			$(".test_price").on("keyup",biz.event.getTotal);//自动计算合计
//			$(".freight").on("keyup",biz.event.getTotal);//自动计算合计
			if(isShowDetail){
				$("#materialAddBtn").on("click", biz.event.materialAdd);
				$("#materialRemoveBtn").on("click", biz.event.materialRemove);
			}else{
				$("#materialAddBtn,#materialRemoveBtn").remove();
				$("#common").attr("disabled","disabled");
				$("#yidongMemo").remove();
				$("#otherFileUploadTd").remove();
				parent.iFrameHeight("iframePurchase");
			}
		},
		event : {
			getTotalPrice:function(){
				var $numc=$(this).parent().parent().find(".numc");
				var $price=$(this).parent().parent().find(".price");
				if(!$numc.validationEngine("validate") && !$price.validationEngine("validate")){
					var _num = $numc.val();
					var _price = $price.val();
					if(""==_num || "" ==_price){
						$(this).parent().parent().find(".total_price").val("");
						return;
					}
					$(this).parent().parent().find(".total_price").val(parseFloat(_num)*parseFloat(_price));
				}else{
					$(this).parent().parent().find(".total_price").val("");
				}
			},
			getTotal:function(){
				var $total_price=$(this).parent().parent().find(".total_price");
				var $test_price=$(this).parent().parent().find(".test_price");
				var $freight=$(this).parent().parent().find(".freight");
				if(!$test_price.validationEngine("validate") && !$freight.validationEngine("validate")){
					var _total_price=$total_price.val();
					var _test_price=$test_price.val();
					var _freight=$freight.val();
					if(""==_total_price || "" ==_test_price || ""==_freight){
						$(this).parent().parent().find(".total").val("");
						return;
					}
					$(this).parent().parent().find(".total").val(parseFloat(_total_price)+parseFloat(_test_price)+parseFloat(_freight));
				}else{
					$(this).parent().parent().find(".total").val("");
				}
			},
			calcNeedNum:function(){
				var index = $(this).parent().parent().index()-1;
				var orderNum =parseFloat($(".orderNum:eq("+index+")").val());
				var unitNum=parseFloat($(".unit_num:eq("+index+")").val());
				if(!isNaN(orderNum) && !isNaN(unitNum)){
					$(".need_num:eq("+index+")").val((orderNum*unitNum).toFixed(2));
				}else{
					$(".need_num:eq("+index+")").val("");
				}
			},
			initValidation:function(){
				$("#tabPurchase").validationEngine('attach',{
					scroll:true,
					autoHidePrompt:true,
					autoHideDelay:1500,
					showOneMessage: true,
					fadeDuration : 0.3,
					promptPosition : "bottomLeft"
				});
			},
			initOrderData : function() { 
				//订单类型 2打版3大货
				orderType = $(window.parent.document).find("#orderTypeNum").val();
				wfStepIndex = $(window.parent.document).find("#wfStepIndex").val();
				orderId = $(window.parent.document).find("#orderId").val();
				$("#orderId").val(orderId);
				//判断是否可以编辑
				var orderDetail = $(window.parent.document).find("#orderDetail").val(); //是否是查看订单详情
				if("2" == orderType) {
					activeIndex = "3"; //样衣打版节点
				} else if("3" != orderType) { //既不是样衣也不是大货节点
					orderDetail = "true";
				}
				if("true" != orderDetail && activeIndex == wfStepIndex) { //判断是否可编辑
//				if(true){
					$("#sales").attr("fillName","oaOrderDetail.worker");
					isShowDetail = true;//可编辑
					$("#orderDetailDive").remove();
				}else{
					$("#processOrderDive").remove();
					$("#sale").html("采购员："+"<span elementFillName='oaOrderDetail.worker'/>");
					$("#mr").html("采购日期："+"<span elementFillName='oaOrderDetail.wf_real_finish'/>");
				}
				if(parseInt(wfStepIndex) < parseInt(activeIndex)) {
					$("#exportBtn").remove();
				}
				//初始化模板
				var title;	//标题
				var templet;//模板
				title = "<th><input type='hidden' name='oaOrder.id' value='"+orderId+"'/><input type='hidden' name='oaOrder.type' value='"+orderType+"'/></th><th>位置说明</th><th>物料属性</th><th>主/辅料</th><th>物料名称</th><th>色号</th><th>供应商</th><th>地址</th><th>电话</th>";
				if(!isShowDetail){
					title = "<th>位置说明</th><th>物料属性</th><th>主/辅料</th><th>物料名称</th><th>色号</th><th>供应商</th><th>地址</th><th>电话</th>";
					templet ="<tr height='25' class='size_detail_tr'>"
						+"<td elementFillName='materialPurchaseDescList[].position'/>"
						+"<td elementFillName='materialPurchaseDescList[].material_prop'/>"
						+"<td elementFillName='materialPurchaseDescList[].type'/>"
						+"<td elementFillName='materialPurchaseDescList[].material_name'/>"
						+"<td elementFillName='materialPurchaseDescList[].color'/>"
						+"<td elementFillName='materialPurchaseDescList[].supplier_name'/>"
						+"<td elementFillName='materialPurchaseDescList[].supplier_addr'/>"
						+"<td elementFillName='materialPurchaseDescList[].supplier_tel'/>";
					if (orderType == '2') {
						title += "<th>克重(g)</th> <th>成分</th> <th>货期(天)</th> <th>采购损(%)</th> <th>纸筒</th> <th>空差</th> <th>散剪单价</th> <th>单位</th> <th>大货单价</th> <th>单位</th> <th>布封（CM）</th> <th>备注</th>";
						templet+="<td elementFillName='materialPurchaseDescList[].weight'/>"
							+"<td elementFillName='materialPurchaseDescList[].component'/>"
							+"<td elementFillName='materialPurchaseDescList[].delivery_time'/>"
							+"<td elementFillName='materialPurchaseDescList[].buyer_loss'/>"
							+"<td elementFillName='materialPurchaseDescList[].paper_tube'/>"
							+"<td elementFillName='materialPurchaseDescList[].deviation'/>"
							+"<td elementFillName='materialPurchaseDescList[].shear_price'/>"
							+"<td elementFillName='materialPurchaseDescList[].unit'/>"
							+"<td elementFillName='materialPurchaseDescList[].goods_price'/>"
							+"<td elementFillName='materialPurchaseDescList[].goods_unit'/>"
							+"<td elementFillName='materialPurchaseDescList[].buffon'/>"
							+"<td elementFillName='materialPurchaseDescList[].pur_memo'/></tr>";
					} else if (orderType == '3') {
						title += "<th>布封（CM）</th> <th>标准单件用量（M）</th> <th>订单数量</th> <th>需求数量</th> <th>采购单位</th> <th>采购数量</th> <th>单价</th> <th>总金额</th> <th>验布费用</th> <th>运费</th> <th>合计</th> <th >采购损（%）</th> <th>纸筒</th> <th>空差</th> <th>备注</th>";
						templet+="<td elementFillName='materialPurchaseDescList[].buffon'/>"
							+"<td elementFillName='materialPurchaseDescList[].unit_num'/>"
							+"<td elementFillName='materialPurchaseDescList[].order_num'/>"
							+"<td elementFillName='materialPurchaseDescList[].need_num'/>"
							+"<td elementFillName='materialPurchaseDescList[].org'/>"
							+"<td elementFillName='materialPurchaseDescList[].num'/>"
							+"<td elementFillName='materialPurchaseDescList[].price'/>"
							+"<td elementFillName='materialPurchaseDescList[].total_price'/>"
							+"<td elementFillName='materialPurchaseDescList[].test_price'/>"
							+"<td elementFillName='materialPurchaseDescList[].freight'/>"
							+"<td elementFillName='materialPurchaseDescList[].total'/>"
							+"<td elementFillName='materialPurchaseDescList[].buyer_loss'/>"
							+"<td elementFillName='materialPurchaseDescList[].paper_tube'/>"
							+"<td elementFillName='materialPurchaseDescList[].deviation'/>"
							+"<td elementFillName='materialPurchaseDescList[].pur_memo'/></tr>";
					}
				}else{
					templet ="<tr height='25' class='size_detail_tr'>"
						+"<td><input type='checkbox'/></td>"
						+"<td><input style='width: 60px;' name='oaMaterialLists[].position' fillName='materialPurchaseDescList[].position' class='validate[groupRequired[grp{}],maxSize[20]]' type='text'></td>"
						+"<td><input style='width: 60px;' name='oaMaterialLists[].materialProp' fillName='materialPurchaseDescList[].material_prop' class='validate[groupRequired[grp{}],maxSize[20]]' type='text'></td>"
						+"<td><select class='' name='oaMaterialLists[].type' fillName='materialPurchaseDescList[].type'><option value='主料'>主料</option><option value='辅料'>辅料</option></select></td>"
						+"<td><input style='width: 135px;' name='oaMaterialLists[].materialName' fillName='materialPurchaseDescList[].material_name' class='validate[groupRequired[grp{}],maxSize[20]]' type='text'></td>"
						+"<td><input style='width: 35px;' name='oaMaterialLists[].color' fillName='materialPurchaseDescList[].color' class='validate[groupRequired[grp{}],maxSize[10]]' type='text'></td>"
						+"<td><input style='width: 135px;' name='oaMaterialLists[].supplierName' fillName='materialPurchaseDescList[].supplier_name' class='validate[groupRequired[grp{}],maxSize[20]]' type='text'></td>"
						+"<td><input style='width: 135px;' name='oaMaterialLists[].supplierAddr' fillName='materialPurchaseDescList[].supplier_addr' class='validate[groupRequired[grp{}],maxSize[50]]' type='text'></td>"
						+"<td><input style='width: 60px;' name='oaMaterialLists[].supplierTel' fillName='materialPurchaseDescList[].supplier_tel' class='validate[groupRequired[grp{}],custom[phone],maxSize[20]]' type='text'></td>"
						+"<input type='hidden' name='oaMaterialLists[].oaOrderId' value='"+orderId+"'/>";
					if (orderType == '2') {
						title += "<th>克重（g）</th> <th>成分</th> <th>货期（天）</th> <th>采购损（%）</th> <th>纸筒</th> <th>空差</th> <th>散剪单价</th> <th>单位</th> <th>大货单价</th> <th>单位</th> <th>布封（CM）</th> <th>备注</th>";
						templet+="<td><input style='width: 35px;' name='oaDaBanInfos[].weight' fillName='materialPurchaseDescList[].weight' class='validate[custom[integer],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaDaBanInfos[].component' fillName='materialPurchaseDescList[].component' class='validate[maxSize[200]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaDaBanInfos[].deliveryTime' fillName='materialPurchaseDescList[].delivery_time' class='validate[custom[integer],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaDaBanInfos[].buyerLoss' fillName='materialPurchaseDescList[].buyer_loss' class='validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaDaBanInfos[].paperTube' fillName='materialPurchaseDescList[].paper_tube' class='validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaDaBanInfos[].deviation' fillName='materialPurchaseDescList[].deviation' class='validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaDaBanInfos[].shearPrice' fillName='materialPurchaseDescList[].shear_price' class='validate[custom[number3],maxSize[10]]' type='text'></td>"
							+"<td><select class='' name='oaDaBanInfos[].unit' fillName='materialPurchaseDescList[].unit'><option value='M'>M</option><option value='码'>码</option><option value='KG'>KG</option><option value='个'>个</option><option value='粒'>粒</option><option value='条'>条</option><option value='包'>包</option><option value='套'>套</option><option value='扎'>扎</option><option value='卷'>卷</option><option value='盒'>盒</option><option value='张'>张</option><option value='摞'>摞</option></select></td>"
							+"<td><input style='width: 35px;' name='oaDaBanInfos[].goodsPrice' fillName='materialPurchaseDescList[].goods_price' class='validate[custom[number3],maxSize[10]]' type='text'></td>"
							+"<td><select class='' name='oaDaBanInfos[].goodsUnit' fillName='materialPurchaseDescList[].goods_unit'><option value='M'>M</option><option value='码'>码</option><option value='KG'>KG</option><option value='个'>个</option><option value='粒'>粒</option><option value='条'>条</option><option value='包'>包</option><option value='套'>套</option><option value='扎'>扎</option><option value='卷'>卷</option><option value='盒'>盒</option><option value='张'>张</option><option value='摞'>摞</option></select></td>"
							+"<td><input style='width: 35px;' name='oaDaBanInfos[].buffon' fillName='materialPurchaseDescList[].buffon' class='validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 85px;' name='oaDaBanInfos[].purMemo' fillName='materialPurchaseDescList[].pur_memo' class='validate[maxSize[1000]]' type='text'></td>"
							+"<input type='hidden' class='materiaList' name='oaMaterialLists[].id' fillName='materialPurchaseDescList[].marterialListId'/><input type='hidden' class='materiaDesc' name='oaDaBanInfos[].id' fillName='materialPurchaseDescList[].daBanId'/></tr>";
					} else if (orderType == '3') {
						title += "<th>布封(CM)</th> <th>标准单件用量(M)</th> <th>订单数量</th> <th>需求数量</th> <th>采购单位</th> <th>采购数量</th> <th>单价(¥)</th> <th>总金额(¥)</th> <th>验布费用(¥)</th> <th>运费(¥)</th> <th>合计(¥)</th> <th>采购损(%)</th> <th>纸筒</th> <th>空差</th> <th>备注</th>";
						templet+="<td><input style='width: 35px;' name='oaDaHuoInfos[].buffon' fillName='materialPurchaseDescList[].buffon' class='validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaDaHuoInfos[].unitNum' fillName='materialPurchaseDescList[].unit_num' class='unit_num validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaMaterialLists[].orderNum' fillName='materialPurchaseDescList[].order_num' class='orderNum validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaDaHuoInfos[].needNum' fillName='materialPurchaseDescList[].need_num' class='need_num validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><select class='' name='oaDaHuoInfos[].org' fillName='materialPurchaseDescList[].org'><option value='M'>M</option><option value='码'>码</option><option value='KG'>KG</option><option value='个'>个</option><option value='粒'>粒</option><option value='条'>条</option><option value='包'>包</option><option value='套'>套</option><option value='扎'>扎</option><option value='卷'>卷</option><option value='盒'>盒</option><option value='张'>张</option><option value='摞'>摞</option></select></td>"
							+"<td><input style='width: 35px;' name='oaDaHuoInfos[].num' fillName='materialPurchaseDescList[].num' class='numc validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaDaHuoInfos[].price' fillName='materialPurchaseDescList[].price' class='price validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaDaHuoInfos[].totalPrice' fillName='materialPurchaseDescList[].total_price' class='total_price validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaDaHuoInfos[].testPrice' fillName='materialPurchaseDescList[].test_price' class='test_price validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaDaHuoInfos[].freight' fillName='materialPurchaseDescList[].freight' class='freight validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaDaHuoInfos[].total' fillName='materialPurchaseDescList[].total' class='total validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaDaHuoInfos[].buyerLoss' fillName='materialPurchaseDescList[].buyer_loss' class='validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaDaHuoInfos[].paperTube' fillName='materialPurchaseDescList[].paper_tube' class='validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 35px;' name='oaDaHuoInfos[].deviation' fillName='materialPurchaseDescList[].deviation' class='validate[custom[number2],maxSize[10]]' type='text'></td>"
							+"<td><input style='width: 85px;' name='oaDaHuoInfos[].purMemo' fillName='materialPurchaseDescList[].pur_memo' class='' type='text'></td>"
							+"<input type='hidden' class='materiaList' name='oaDaHuoInfos[].oaMaterialList' fillName='materialPurchaseDescList[].marterialListId'/><input type='hidden' name='oaMaterialLists[].id' fillName='materialPurchaseDescList[].marterialListId'/><input class='materiaDesc' type='hidden' name='oaDaHuoInfos[].id' fillName='materialPurchaseDescList[].daHuoId'/> </tr>";
					}
				}
				
				$("#materialTable tr:first").append(title);				//表头添加
				parent.iFrameHeight("iframePurchase");
				opt = {
					formFill:true,
					formFillName:'fillName',
					elementText:true,
					elementFillName:'elementFillName',	
					addRow:true,				// 自动添加行
					addNum:0,					// 添加的数量	addRow:true时有效
					addPoint:'materialTable',	// 添加点		element id
					templet:templet,			// 模板			addRow:true时有效
					isList:true,				// name是否list 是list则name[0],name[1]	addRow:true时有效
					debug : false
				};
			},
			materialRemove : function() {
				var $ckbs=$("input[type=checkbox]:checked");
				if($ckbs.size()==0){
				    alert("请勾选要删除的行");
					return;
				}else{
					if(confirm("是否确认删除选中行？")){
						$ckbs.each(function(){
							var materiaList = $('#materiaList').val();
							var materiaDesc = $('#materiaDesc').val();
							var delML =$(this).parent().parent().find('.materiaList').val();
							var delMD = $(this).parent().parent().find('.materiaDesc').val();
							if(materiaList==''){
								$('#materiaList').val(delML);
							}else{
								$('#materiaList').val(materiaList+','+delML);
							}
							if(materiaDesc==''){
								$('#materiaDesc').val(delML);
							}else{
								$('#materiaDesc').val(materiaDesc+','+delML);
							}
					 		$(this).parent().parent().remove();
						});
					}
				}
				parent.iFrameHeight("iframePurchase");
			},
			materialAdd : function() {
				if($("#materialTable>tbody tr").length>15){
					alert("最多添加十五行");
				 	return;
				}
				var temp;
				temp = opt.templet.replace(/\[]/g,"["+index+"]").replace(/\{}/g,index);
				index +=1;
				$("#materialTable").append(temp);
				
				parent.iFrameHeight("iframePurchase");
			},
			jsonGetFourNode : function() { // 获取第四节点页面信息
				$.ajax({
                    url: '/bx/jsonGetFourNode?orderId='+orderId+'&type='+orderType + "&wfStepIndex=" + wfStepIndex,
                    type: "post",
                    async: false,
                    success: function(data) {
                    	if("ajaxLogin" == data){
                			alert("登录超时，请重新登录");
                		} else if("0" ==  data.code){
                			if(undefined != data.oaOrderDetail && null != data.oaOrderDetail && undefined != data.oaOrderDetail.other_file && null != data.oaOrderDetail.other_file && "" != data.oaOrderDetail.other_file && "null" != data.oaOrderDetail.other_file) {
                				var downFile = "<a downloadurl='" +data.oaOrderDetail.other_file + "' class='z_title_sty1 mar_l10 download_a download' href='javascript:void(0)'>" + data.oaOrderDetail.other_file_name + "</a>";
        						$("#otherFile").html(downFile);
            				}
                			if(undefined != data.oaOrderDetail && null != data.oaOrderDetail  && undefined != data.oaOrderDetail.schedule && null != data.oaOrderDetail.schedule) {
                				if(data.oaOrderDetail.schedule.indexOf("超时") >= 0) {
                					$("#detailSchedule").css("background-color", "red");
                					$("#detailSchedule").css("color", "white");
                				} else if(data.oaOrderDetail.schedule.indexOf("提前") >= 0) {
                					$("#detailSchedule").css("background-color", "green");
                					$("#detailSchedule").css("color", "white");
                				}
                				$("#detailSchedule").text(data.oaOrderDetail.schedule);
                			}
                			//计算流入颜色和流出颜色
                			if(undefined != data.oaOrderDetail && undefined != data.oaOrderDetail.step_start_time_consume && null != data.oaOrderDetail.step_start_time_consume && "" != data.oaOrderDetail.step_start_time_consume) {
        						var rate=parseFloat(data.oaOrderDetail.step_start_time_consume);
        						if(rate <= 33){
        							$("#detailRealStartColor").css("background-color","#33cc00");
        						}else if(rate <= 66){
        							$("#detailRealStartColor").css("background-color","#ff9900");
        						}else if(rate <= 100){
        							$("#detailRealStartColor").css("background-color","#ff3300");
        						}else {
        							$("#detailRealStartColor").css("background-color","#000000");
        						}
        						$("#detailRealStartColor").text(data.oaOrderDetail.step_start_time_consume + "%");
        					}
        					if(undefined != data.oaOrderDetail && undefined != data.oaOrderDetail.step_finish_time_consume && null != data.oaOrderDetail.step_finish_time_consume && "" != data.oaOrderDetail.step_finish_time_consume) {
        						var rate=parseFloat(data.oaOrderDetail.step_finish_time_consume);
        						if(rate <= 33){
        							$("#detailRealFinishColor").css("background-color","#33cc00");
        						}else if(rate <= 66){
        							$("#detailRealFinishColor").css("background-color","#ff9900");
        						}else if(rate <= 100){
        							$("#detailRealFinishColor").css("background-color","#ff3300");
        						}else {
        							$("#detailRealFinishColor").css("background-color","#000000");
        						}
        						$("#detailRealFinishColor").text(data.oaOrderDetail.step_finish_time_consume + "%");
        					}
                			opt.addNum = data.materialPurchaseDescList.length;
                			index = opt.addNum;jsonData = data; 
                			//如果没有操作员默认为当前登录人
                			if(jsonData.oaOrderDetail.worker == null || jsonData.oaOrderDetail.worker == ""){
                				jsonData.oaOrderDetail.worker=jsonData.oaOrderDetail.operator;
                			}
                			$("#tabPurchase").autoFill(data, opt);
        					parent.iFrameHeight("iframePurchase");
                		} else {
                			alert(data.msg);
                		}
                    }
                });
				$(".unit_num").each(function(){
					if(0==$(this).val()){
						$(this).removeAttr("readonly");
					}
				})
				parent.iFrameHeight("iframePurchase");
			},
			initTrackes : function(){
				if(jsonData){
					$.each(jsonData.oaTrackes,function(i,v){
						biz.event.putOaTrackesTrData(v.memo, v.user, v.time);
					})
				}
				parent.iFrameHeight("iframePurchase");
			},
			putOaTrackesTrData : function(td1, td2, td3) { //添加一行异动信息到列表
				var tr = "<tr><td width='53px' height='22px'></td><td width='573px'>" + td1 + "</td><td>" + td2 + "/" + td3 + "</td></tr>";
				$("#oaTrackesTable").append(tr);
				parent.iFrameHeight("iframePurchase");
			},
			exportExcel : function() { //导出excel操作
				$("#exportBtn").attr("disabled", "true");
				$("#exportBtn").val("30秒后再次点击");
				top.location.href = "/bx/downOAOrder?oaOrder.id=" + orderId + "&node=" + activeIndex;
				setTimeout(function(){
					$("#exportBtn").removeAttr("disabled");
					$("#exportBtn").val("导出Excel");
				},30000);
			},
			jsonSaveTracke : function() { //保存异动信息
				if($.trim($("#yidongContent").val()) != "") {
					var params = {"oaTracke.memo" : $("#yidongContent").val(), "oaTracke.oaOrder" : orderId, "oaTracke.node" : $(window.parent.document).find("#wfStep").val()};
					$.ajax({
	                    url: "/bx/jsonSaveTracke",
	                    type: "post",
	                    data: params,
	                    success: function(data) {
	                    	if("ajaxLogin" == data){
	                			alert("登录超时，请重新登录");
	                		} else if("0" ==  data.code){
	                			biz.event.putOaTrackesTrData($("#yidongContent").val(), data.oaTrackeUser, data.oaTrackeTime);
	                			$("#yidongContent").val("");
	                			parent.iFrameHeight("iframePurchase");
	                		} else {
	                			alert(data.msg);
	                		}
	                    }
	                });
				} else {
					alert("发布异动信息不能为空");
				}
			},
			download : function() { //下载文件
				window.location.href = "/oaOrderFileDownload?fileUrl=" + encodeURI($(this).attr("downloadUrl"));
			},
			processOrder : function() { //保存并提交订单
				$("#processOrder").val("true"); //true标识流程流转
				biz.event.jsonProcessOrder();
			},
			saveOrder : function() { //保存草稿
				$("#processOrder").val("false"); //false标识流程不流转，只保存数据
				biz.event.jsonProcessOrder();
			},
			jsonProcessOrder:function(){//ajax提交表单
				//先要对所填写的数据进行验证合法后才进行提交
				var successFlag=$("#tabPurchase").validationEngine("validate");
				if(successFlag){
					$("#processBtn").attr("disabled", "true");
					$("#saveBtn").attr("disabled", "true");
					$("#backBtn").attr("disabled", "true");
					var params = $("#tabPurchase").serializeArray();
					$.ajax({
	                    url: "/bx/jsonProcessOrder",
	                    type: "post",
	                    data: params,
	                    success: function(data) {
	                    	if("ajaxLogin" == data){
	                			alert("登录超时，请重新登录");
	                		} else if("0" ==  data.code){
	                			top.location.href = "/bx/todo";
	                		} else {
	                			alert(data.msg);
		        				$("#processBtn").removeAttr("disabled");
		        				$("#saveBtn").removeAttr("disabled");
		        				$("#backBtn").removeAttr("disabled");
	                		}
	                    },
	                    error: function() {
	        				$("#processBtn").removeAttr("disabled");
	        				$("#saveBtn").removeAttr("disabled");
	        				$("#backBtn").removeAttr("disabled");
	                    }
	                });
				}
			}
		}
	}
	return fn;
});

//文件上传回调函数
function uploadComplete(data) {
	data = data.replace('<meta http-equiv="content-type" content="text/html; charset=UTF-8">',"");
	if (data === "extensionerror") {
		alert("图片格式不对，仅支持*.png、*.gif、*.jpg、*.bmp、*.jpeg，请重新上传！");
		return;
	}
	if (data === "error") {
		alert("上传失败！");
		return;
	}
	if (data === "empty") {
		alert("上传文件为空！");
		return;
	}
	var json = $.parseJSON(data);
	// 对上传文件时进行判断
	if (json.fileType == "file") {
		$("#hid_attachment_url").val(json.url);
		var html = "<a href='" + json.url + "'>" + json.fileName+ "</a>";
		$("#otherFile").html(json.fileName);
	}
}

function showMessage(data) {
	alert(data);
}