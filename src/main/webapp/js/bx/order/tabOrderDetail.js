requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
		"u" : "util",
		"layer" : "layer/layer.min",
		"v":"jqueryValidation/js/jquery.validationEngine.min",
		"vl":"jqueryValidation/js/jquery.validationEngine-zh_CN"
	}
});
var materialTr,cusMaterialTr,sizeTitle,width,detailUpdateFlag = false,layerNum = 0;
var activeIndex = "2";
var wfStepIndex; //订单节点index
var selectedIndex = -1;
var categorys = {};
define([ "u","layer","v","vl" ], function(u,layer) {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var biz = {
		init : function() {
			//已关联订单的，不在显示关联订单按钮;已退回MR或流过MR的也不显示关联按钮
			var _nodeNum = $(window.parent.document).find("#hisOpt").val();
			_nodeNum = _nodeNum.substring(1).split(";").length-1;
			if(""==$(window.parent.document).find("#relatedOrderCode").val() && 2>=_nodeNum){
				$("#chose_relate").show();
			} else {
				if("" != $(window.parent.document).find("#relatedOrderCode").val()){
					$("#relatedOrderDetail").attr("href", "/bx/orderDetail?oaOrderDetail.id=" + $(window.parent.document).find("#relatedOrderDetailId").val());
					$("#relatedOrderDetail").show();
				}
			}
			biz.event.initOrderData();
			biz.event.jsonGetSecondNode();
			biz.event.setInputDisable();
			biz.event.initValidation();
		},
		bind : function() {
			$("#cusMaterialRemoveBtn").on("click", biz.event.cusMaterialRemove);
			$("#cusMaterialAddBtn").on("click", biz.event.cusMaterialAdd);
			$("#materialRemoveBtn").on("click", biz.event.materialRemove);
			$("#materialAddBtn").on("click", biz.event.materialAdd);
			$("#processBtn").on("click", biz.event.processOrder); //保存并提交
			$("#saveBtn").on("click", biz.event.saveOrder); //保存草稿
			$("#exportBtn").on("click", biz.event.exportExcel); //导出订单Excel
			$(".download").on("click", biz.event.download); //下载文件
			$("#terminateBtn").on("click", biz.event.toTerminateOrderDetail); //跳转到终止订单
			$("#terminateBtn1").on("click", biz.event.toTerminateOrderDetail); //跳转到终止订单
			$("#chose_relate").on("click",biz.event.choseRelate);
			//add by ZQ 2014-12-20 关联订单回显信息隐藏事件
			$("#chooseBtn").on("click",biz.event.jsonGetSecondNode2);
			$("#hidCloseBtn").on("click",function(){
				layer.close(layerNum);
			});
			$(".sp_style_craft").on("click",biz.event.choseStyle);
			$("#sp_none").on("click",biz.event.clearStyle);
			$("#ck_last").on("click",biz.event.clearStyle);
			$("input:checkbox[name='oaOrder.styleCraft']:not(:last)").on("click",biz.event.choseStyle);
			$("#categorys").change(function(){
				selectedIndex = this.selectedIndex;
				//清空特殊工艺选择
				biz.event.clearStyle();
				//初始化各个选项的耗时
				biz.event.setStyleCarft(selectedIndex);
				//计算出建议投料日期
				
			})
		},
		event : {
			clearStyle:function(){
				$("input:checkbox[name='oaOrder.styleCraft']:not(:last)").each(function(){
					$(this).prop("checked",false);
				});
			},
			choseStyle:function(){
				var url = 
				$("#ck_last").prop("checked",false);
				if($(this).prev("input:checkbox[name='oaOrder.styleCraft']").prop("checked")==true){
					$(this).prev("input:checkbox[name='oaOrder.styleCraft']").prop("checked",false);
				}else{
					$(this).prev("input:checkbox[name='oaOrder.styleCraft']").prop("checked",true);
				}
				var totalTime = 0;
				$("input:checkbox[name='oaOrder.styleCraft']:checked").each(function(index,data){
					totalTime += data.time;
				});
				
				//产前版完成日期
				if($("#isPreproduct").val()){
					url = "/bx/getFeedingTime?orderId=" + $(window.parent.document).find("#orderId").val() + "&craftTime=" +totalTime+"&productTime="+$("#isPreproduct").val();
				}else{
					url = "/bx/getFeedingTime?orderId=" + $(window.parent.document).find("#orderId").val() + "&craftTime=" +totalTime;
				}
				$.ajax({
                    url: url,
                    type: "post",
                    dataType: "json",
                    success: function(data) {
            			$("#feeding_time").html(data);
            			$("#feeding_time_value").val(data);
                    }
                });
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
			},
			initValidation:function(){
				$("#tabOrderDetail").validationEngine('attach',{
					scroll:true,
					autoHidePrompt:true,
					autoHideDelay:1500,
					showOneMessage: true,
					fadeDuration : 0.3,
					promptPosition : "bottomLeft"
				});
			},
			choseRelate : function() {
				layerNum++;
				$.layer({
					type: 2,
			        title: '',
			        shadeClose: true, //开启点击遮罩关闭层
			        area : ['667px' , '448px'],
			        offset : ['100px', ''],
			        iframe: {
			        	src: 'orderRelationList.jsp'
	        		}
				});
			},
			initOrderData : function() { // 初始化表单数据，从父窗口中获取
				$("#orderCode").text($(window.parent.document).find("#orderCode").val());
				$("#city").val($(window.parent.document).find("#city").val());
				$("#orderType").text($(window.parent.document).find("#orderType").val());
				if("0" == $(window.parent.document).find("#isUrgent").val()) {
					$("#isUrgent").attr("checked", true);
				} else {
					$("#isUrgent").removeAttr("checked");
				}
				$("#styleCode").text($(window.parent.document).find("#styleCode").val());
				$("#styleDesc").text($(window.parent.document).find("#styleDesc").val());
				$("#styleType").text($(window.parent.document).find("#styleType").val());
				
				
				$("#clothClass").val($(window.parent.document).find("#clothClass").val());
				//$("#styleCraft").text($(window.parent.document).find("#styleCraft").val());
				//初始化特殊工艺
				var styleCrafts=$(window.parent.document).find("#styleCraft").val().split(",");
				for(var i=0;i<styleCrafts.length;i++){
					$("input:checkbox[name='oaOrder.styleCraft']").each(function(){
						if($(this).val()==styleCrafts[i].trim()){
							$(this).prop("checked",true);
							return;
						}
					});
				}
				$("#sampleSize").val($(window.parent.document).find("#sampleSize").val());
				$("#wantCnt").text($(window.parent.document).find("#wantCnt").val());
				$("#picFront").attr("src", $(window.parent.document).find("#picFront").val());
				$("#picBack").attr("src", $(window.parent.document).find("#picBack").val());
				$("#customerCode").text($(window.parent.document).find("#customerCode").val());
				$("#customerName").text($(window.parent.document).find("#customerName").val());
				$("#payType").text($(window.parent.document).find("#payType").val());
				$("#contractCode").text($(window.parent.document).find("#contractCode").val());
				$("#sendType").val($(window.parent.document).find("#sendType").val());
				$("#salesMemo").text($(window.parent.document).find("#salesMemo").val());
				$("#mrMemo").text($(window.parent.document).find("#mrMemo").val());
				$("#sales").text($(window.parent.document).find("#sales").val());
				//判断订单类型
				if("样衣打版"==$(window.parent.document).find("#orderType").val()){
					$("#t_repeatNum").show();
					$("#t_repeatReason").show();
					$("#repeatNum").text($(window.parent.document).find("#repeatNum").val());
					$("#repeatReason").text($(window.parent.document).find("#repeatReason").val());	
				}else if("大货生产"==$(window.parent.document).find("#orderType").val()){
					$("#isPreProductDiv").show();
					$("#isPreproduct").val($(window.parent.document).find("#preVersionDate").val());//填充产前版日期	
				}
				//添加关联客户编号、ID、类型回显 Add by ZQ 2014-12-22
				$("#related_order_code").val($(window.parent.document).find("#relatedOrderCode").val());
				$("#related_order_id").val($(window.parent.document).find("#relatedOrderId").val());
				$("#related_order_type").val($(window.parent.document).find("#relatedOrderType").val());
				$("#mr").text($(window.parent.document).find("#mr").val());
				var tpe = $(window.parent.document).find("#tpe").val();
				if(undefined == tpe || null == tpe || "" == tpe) {
					tpe = $("#tpeAdmin").val();
				}
				$("#tpe").val(tpe);
				$("#orderId").val($(window.parent.document).find("#orderId").val());
				//thOrderSize
				if("样衣打版" == $("#styleType").val()) {
					$("#thOrderSize").text("打版用量");
				}
				//判断是否可以编辑
				var orderDetail = $(window.parent.document).find("#orderDetail").val(); //是否是查看订单详情
				wfStepIndex = $(window.parent.document).find("#wfStepIndex").val(); //订单节点index
				if("true" != orderDetail && "2" == wfStepIndex) {
					detailUpdateFlag = true;
				}
			},
			jsonGetSecondNode : function() { // 获取第二节点页面信息
				$.ajax({
                    url: "/bx/jsonGetSecondNode?orderId=" + $(window.parent.document).find("#orderId").val() + "&orderSizeId=" + $(window.parent.document).find("#orderSizeId").val() + "&wfStepIndex=" + wfStepIndex,
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function(data) {
                    	if("ajaxLogin" == data){
                			alert("登录超时，请重新登录");
                		} else if("0" ==  data.code){
                			biz.event.setOrderSize(data.orderSizeTitle, data.orderSizeInfo); //设置尺码数量显示
                			sizeTitle = data.orderSizeTitle;
                			biz.event.setCusMaterialTableTitle(); //拼接客供料表头
                			biz.event.setMaterialTableData(data.oaMaterialList,false); //设置用料搭配列表
                			biz.event.setCusMaterialTableData(data.oaCusMaterialList,0); //设置客供料列表other_file_name
                			biz.event.setManageInfoData(data.oaOrderDetail); //设置管理信息
                			biz.event.setCategorys(data.categorys); //设置管理信息
                		} else {
                			alert(data.msg);
                		}
                    }
                });
			},
			//Add by ZQ 2014-12-20 选择关联订单时,复制回显客供料、设置管理信息
			jsonGetSecondNode2 : function() { // 获取第二节点页面信息
				//值为关联订单"id,订单编号,订单类型,尺码信息ID"
				var iframeVal = $(window.frames["xubox_iframe"+layerNum].document).find("input[name=futureOrder]:checked").val().split(",");
				$("#related_order_id").val(iframeVal[0]);
				$("#related_order_code").val(iframeVal[1]);
				$("#related_order_type").val(iframeVal[2]);
				var sizeTitleStr = "";
				for(var i=0,j=sizeTitle.length-1;i<j;i++){
					sizeTitleStr += "-";
				}
				$("#related_order_type").after("<input type='hidden' name='isChoose' value='choosed' /><input type='hidden' name='sizeTitles' value='"+sizeTitleStr+"' />");
				layer.close(layerNum);
				$("#cancel_relate").show();
				$.ajax({
                    url: "/bx/jsonGetSecondNode?orderId=" + iframeVal[0] + "&orderSizeId=" + iframeVal[3] + "&isChoose=chooseOrder" + "&wfStepIndex=" + wfStepIndex,
                    type: "post",
                    async: false,
                    success: function(data) {
                    	if("ajaxLogin" == data){
                			alert("登录超时，请重新登录");
                		} else if("0" ==  data.code){
                			//样板码数
                			$("#sampleSize").val(data.chooseOaOrder.sampleSize);
                			//送货方式
                			$("#sendType").val(data.chooseOaOrder.sendtype);
                			//Mr备注
                			$("#mrMemo").text(data.chooseOaOrder.memo);
                			biz.event.removeOldInfos();//删除之前保存的用料、客供料
                			$("#cusMaterialTable").empty();//清空之前录入数据
                			biz.event.setCusMaterialTableTitle(); //拼接客供料表头
                			$("#materialTable").empty();//清空之前录入数据
                			biz.event.setMaterialTableData(data.oaMaterialList,true); //设置用料搭配列表
                			biz.event.setCusMaterialTableData(data.oaCusMaterialList,sizeTitle.length); //设置客供料列表other_file_name
//                			biz.event.setManageInfoData(data.oaOrderDetail); //设置管理信息
                			$(".has_id").remove();//移除所有以上所有记录先前id
                		} else {
                			alert(data.msg);
                		}
                    }
                });
			},
			setOrderSize : function(sizeTitle, sizeInfo) { // 尺码数量后台数据设置显示到页面
				var tr = "<tr height='28'><th>颜色\\码数</th>";
    			// 拼接尺码数量表头
    			for(var i = 0; i < sizeTitle.length; i++) {
    				tr +="<th>" + sizeTitle[i] + "</th>";
    			}
    			tr += "</tr>";
    			$("#orderSize").append(tr);
    			// 拼接尺码数量内容
    			for(var i = 0; i < sizeInfo.length; i++) {
    				var size = sizeInfo[i].split("-");
    				tr = "<tr height='25'>";
    				for(var j = 0; j < size.length; j++) {
    					tr += "<td>" + size[j] + "</td>";
    				}
    				tr += "</tr>";
    				$("#orderSize").append(tr);
//    				biz.event.addIframeHeight();
    				parent.iFrameHeight("iframeDetail");
    			}
			},
			setCusMaterialTableTitle : function() {
				var title1 = ["物料名称","用料","客供损耗(%)"];
				var title2 = ["合计","是否齐全","位置说明/备注"];
				var tr = "<tr height='28'><th width='20'>&nbsp;</th>";
				var count = title1.length + sizeTitle.length + title2.length;
				width = 720 / count;
				for(var i = 0; i < title1.length; i++) {
					tr += "<th style='width:" + width + "'>" + title1[i] + "</th>";
					count ++;
				}
				for(var i = 0; i < sizeTitle.length; i++) {
					tr += "<th style='width:" + width + "'>" + sizeTitle[i] + "</th>";
					count ++;
				}
				for(var i = 0; i < title2.length; i++) {
					tr += "<th style='width:" + width + "'>" + title2[i] + "</th>";
					count ++;
				}
				tr += "</tr>";
				$("#cusMaterialTable").append(tr);
			},
			setMaterialTableData : function(oaMaterialList,isChoose) { //填充用料搭配明细列表
				var count = oaMaterialList.length;
				var tr = "";
				// 查询到的数据填充到表格中
				$.each(oaMaterialList, function(i, v) {
					tr = "<tr height='25' class='material_tr'><td width='20'><input type='checkbox' class='material_td'><input name='id' type='hidden' class='table_text has_id' value='" + v.id + "'>";
					//从选择的关联订单过来时,拼一个被复制的用料ID
					if(isChoose){
						tr +="<input name='oldMaterIds' type='hidden' value='" + v.id + "'>";
					}
					tr +="</td>"
					v.materialProp = (null == v.materialProp) ? '' : v.materialProp;
					tr += "<td><input name='materialProp' maxlength='20' type='text' class='table_text validate[maxSize[20]]' value='" + v.materialProp + "'></td>";
					v.materialName = (null == v.materialName) ? '' : v.materialName;
					tr += "<td><input name='materialName' maxlength='20' type='text' class='table_text validate[maxSize[20]]' value='" + v.materialName + "'></td>";
					if("辅料" == v.type) {
						tr += "<td><select name='type' class='table_text'><option value='主料'>主料</option><option value='辅料' selected>辅料</option></select></td>";
					} else {
						tr += "<td><select name='type' class='table_text'><option value='主料'>主料</option><option value='辅料'>辅料</option></select></td>";
					}
					v.color = (null == v.color) ? '' : v.color;
					tr += "<td><input name='color' maxlength='20' type='text' class='table_text validate[maxSize[20]]' value='" + v.color + "'></td>";
					v.supplierName = (null == v.supplierName) ? '' : v.supplierName;
					tr += "<td><input name='supplierName' maxlength='20' type='text' class='table_text validate[maxSize[20]]' value='" + v.supplierName + "'></td>";
					v.supplierAddr = (null == v.supplierAddr) ? '' : v.supplierAddr;
					tr += "<td><input name='supplierAddr' maxlength='50' type='text' class='table_text validate[maxSize[50]]' value='" + v.supplierAddr + "'></td>";
					v.supplierTel  = (null == v.supplierTel ) ? '' : v.supplierTel ;
					tr += "<td><input name='supplierTel' maxlength='20' type='text' class='table_text validate[custom[phone],maxSize[20]]' value='" + v.supplierTel + "'></td>";
					v.orderNum = (null == v.orderNum) ? '' : v.orderNum;
					if(isChoose){
						v.orderNum = "";
					}
					tr += "<td><input name='orderNum' maxlength='10' type='text' class='table_text validate[maxSize[10],custom[number2]]' value='" + v.orderNum + "'></td>";
					v.position  = (null == v.position ) ? '' : v.position ;
					tr += "<td><input name='position' maxlength='20' type='text' class='table_text validate[maxSize[20]]' value='" + v.position + "'></td>";
					tr += "</tr>";
					$("#materialTable").append(tr);
//					biz.event.addIframeHeight();
    				parent.iFrameHeight("iframeDetail");
				});
				// 拼接新的tr
				materialTr = "<tr height='25' class='material_tr'><td width='20'><input type='checkbox' class='material_td'></td>";
				materialTr += "<td><input name='materialProp' maxlength='20' type='text' class='table_text validate[maxSize[20]]'></td>";
				materialTr += "<td><input name='materialName' maxlength='20' type='text' class='table_text validate[maxSize[20]]'></td>";
				materialTr += "<td><select name='type' class='table_text'><option value='主料'>主料</option><option value='辅料'>辅料</option></select></td>";
				materialTr += "<td><input name='color' maxlength='20' type='text' class='table_text validate[maxSize[20]]'></td>";
				materialTr += "<td><input name='supplierName' maxlength='20' type='text' class='table_text validate[maxSize[20]]'></td>";
				materialTr += "<td><input name='supplierAddr' maxlength='50' type='text' class='table_text validate[maxSize[50]]'></td>";
				materialTr += "<td><input name='supplierTel' maxlength='20' type='text' class='table_text validate[custom[phone],maxSize[20]]' ></td>";
				materialTr += "<td><input name='orderNum' maxlength='10' type='text' class='table_text validate[maxSize[10],custom[number2]]' ></td>";
				materialTr += "<td><input name='position' maxlength='20' type='text' class='table_text validate[maxSize[20]]' ></td>";
				materialTr += "</tr>";
				if(detailUpdateFlag) {
					// 不满5行，用新tr填充
					for(var i = 0; i < (5 - count); i++) {
						$("#materialTable").append(materialTr);
//						biz.event.addIframeHeight();
	    				parent.iFrameHeight("iframeDetail");
					}
				}
			},
			setCusMaterialTableData : function(oaCusMaterialList,emptySize) { //填充客供料明细列表
				var count = oaCusMaterialList.length;
				var tr = "";
				// 查询到的数据填充到表格中
				$.each(oaCusMaterialList, function(i, v) {
					tr = "<tr height='25' class='cus_material_tr'><td width='20'><input type='checkbox' class='cus_material_td'><input name='orderNum' class='table_text cus_material_num' type='hidden'><input name='id' type='hidden' class='table_text has_id' value='" + v.id + "'></td>";
					tr += "<td><input name='materialName' maxlength='20' type='text' class='table_text validate[maxSize[20]]' value='" + v.materialName + "'></td>";
					tr += "<td><input name='amount' maxlength='10' type='text' class='table_text validate[maxSize[10],custom[number2]]' value='" + v.amount + "'></td>";
					tr += "<td><input name='consume' maxlength='3' type='text' class='table_text validate[maxSize[3],custom[number2]]' value='" + v.consume + "'></td>";
					//emptySize为0时，即表示非关联订单操作时的填充 Modify by ZQ 2014-12-22
					if(0==emptySize){
						var orderNum = v.orderNum.split("-");
						titleSize = 0;
						for (var i = 0; i < orderNum.length; i++) {
							tr += "<td><input type='text' maxlength='9' class='table_text cus_material_size validate[maxSize[9],custom[number2]]' value='" + orderNum[i] + "' onblur='culSizeTotal(this);'></td>";
						}
						tr += "<td><input name='total' type='text' class='table_text' readonly value='" + v.total + "'></td>";
					//关联订单操作时的填充 Modify by ZQ 2014-12-22
					}else{
						for (var i = 0; i < emptySize; i++) {
							tr += "<td><input type='text' maxlength='9' class='table_text cus_material_size validate[maxSize[9],custom[number2]]' onblur='culSizeTotal(this);'></td>";
						}
						tr += "<td><input name='total' type='text' class='table_text' readonly ></td>";
					}
					if("否" == v.isComplete) {
						tr += "<td><select name='isComplete' class='table_text'><option value='是'>是</option><option value='否' selected>否</option></select></td>";
					} else {
						tr += "<td><select name='isComplete' class='table_text'><option value='是'>是</option><option value='否'>否</option></select></td>";
					}
					tr += "<td><input name='memo' maxlength='50' type='text' class='table_text validate[maxSize[50]]' value='" + v.memo + "'></td>";
					tr += "</tr>";
					$("#cusMaterialTable").append(tr);
//					biz.event.addIframeHeight();
    				parent.iFrameHeight("iframeDetail");
				});
				//拼接新的tr
				cusMaterialTr = "<tr height='25' class='cus_material_tr'><td width='20'><input type='checkbox' class='cus_material_td'><input name='orderNum' class='table_text cus_material_num' type='hidden'></td>";
				cusMaterialTr += "<td><input name='materialName' maxlength='20' type='text' class='table_text validate[maxSize[20]]'></td>";
				cusMaterialTr += "<td><input name='amount' maxlength='10' type='text' class='table_text validate[maxSize[10],custom[number2]]'></td>";
				cusMaterialTr += "<td><input name='consume' maxlength='3' type='text' class='table_text validate[maxSize[3],custom[number2]]'></td>";
				if(undefined != sizeTitle && null != sizeTitle) {
					for (var i = 0; i < sizeTitle.length; i++) {
						cusMaterialTr += "<td><input type='text' maxlength='9' class='table_text cus_material_size validate[maxSize[9],custom[number2]]' onblur='culSizeTotal(this);'></td>";
					}
				}
				cusMaterialTr += "<td><input name='total' type='text' class='table_text' readonly></td>";
				cusMaterialTr += "<td><select name='isComplete' class='table_text'><option value='是'>是</option><option value='否'>否</option></select></td>";
				cusMaterialTr += "<td><input name='memo' maxlength='50' type='text' class='table_text validate[maxSize[50]]'></td>";
				cusMaterialTr += "</tr>";
				// 不满5行，用新tr填充
				if(detailUpdateFlag) {
					for(var i = 0; i < (5 - count); i++) {
						$("#cusMaterialTable").append(cusMaterialTr);
//						biz.event.addIframeHeight();
	    				parent.iFrameHeight("iframeDetail");
					}
				}
			},setManageInfoData : function(orderDetail) { //设置管理信息
				if(undefined != orderDetail && null != orderDetail) {
//					$("#detailOperator").text(orderDetail.operator);
//					$("#detailDate").text(orderDetail.processTime);
//					$("#detailContent").text(orderDetail.content);
//					$("#detailAttachment").text(orderDetail.other_file);
//					$("#wfStep").val(orderDetail.wf_step);
					//进度信息超时
					if(parseInt(activeIndex) <= parseInt(wfStepIndex)) {
						if(undefined == orderDetail.wf_real_finish || null == orderDetail.wf_real_finish || "" == orderDetail.wf_real_finish) {
							orderDetail.schedule = "进行中";
						}
					}

					if(undefined != orderDetail.schedule && null != orderDetail.schedule) {
						if(orderDetail.schedule.indexOf("超时") >= 0) {
							$("#detailSchedule").css("background-color", "red");
							$("#detailSchedule").css("color", "white");
						} else if(orderDetail.schedule.indexOf("提前") >= 0) {
							$("#detailSchedule").css("background-color", "green");
							$("#detailSchedule").css("color", "white");
						}
						$("#detailSchedule").text(orderDetail.schedule);
					}

					if(undefined != orderDetail.wf_real_start && null != orderDetail.wf_real_start) {
						$("#detailRealStart").text("流入时间:" + orderDetail.wf_real_start);
					}
					if(undefined != orderDetail.wf_real_finish && null != orderDetail.wf_real_finish) {
						$("#detailRealFinish").text("实际完成:" + orderDetail.wf_real_finish);
					}
					if(undefined != orderDetail.step_start_time_consume && null != orderDetail.step_start_time_consume && "" != orderDetail.step_start_time_consume) {
						var rate=parseFloat(orderDetail.step_start_time_consume);
						if(rate <= 33){
							$("#detailRealStartColor").css("background-color","#33cc00");
						}else if(rate <= 66){
							$("#detailRealStartColor").css("background-color","#ff9900");
						}else if(rate <= 100){
							$("#detailRealStartColor").css("background-color","#ff3300");
						}else {
							$("#detailRealStartColor").css("background-color","#000000");
						}
						$("#detailRealStartColor").text(orderDetail.step_start_time_consume + "%");
					}
					if(undefined != orderDetail.step_finish_time_consume && null != orderDetail.step_finish_time_consume && "" != orderDetail.step_finish_time_consume) {
						var rate=parseFloat(orderDetail.step_finish_time_consume);
						if(rate <= 33){
							$("#detailRealFinishColor").css("background-color","#33cc00");
						}else if(rate <= 66){
							$("#detailRealFinishColor").css("background-color","#ff9900");
						}else if(rate <= 100){
							$("#detailRealFinishColor").css("background-color","#ff3300");
						}else {
							$("#detailRealFinishColor").css("background-color","#000000");
						}
						$("#detailRealFinishColor").text(orderDetail.step_finish_time_consume + "%");
					}
					if(undefined != orderDetail.wf_step_duration && null != orderDetail.wf_step_duration) {
						$("#detailDuration").text("标准工时:" + orderDetail.wf_step_duration);
					}
					if(undefined != orderDetail.real_time && null != orderDetail.real_time) {
						$("#detailRealTime").text("实际耗时:" + orderDetail.real_time);
					}
					//下载文件设置
					if(undefined != orderDetail.other_file && null != orderDetail.other_file && "" != orderDetail.other_file && "null" != orderDetail.other_file) {
						var downFile = "<a downloadurl='" + orderDetail.other_file + "' class='z_title_sty1 mar_l10 download_a download' href='javascript:void(0)'>" + orderDetail.other_file_name + "</a>";
						$("#otherFile").html(downFile);
						$("#hid_attachment_url").val(orderDetail.other_file);
					}
				}
			},
			setStyleCarft:function(idx){
				$("input:checkbox[name='oaOrder.styleCraft']:not(:last)").each(function(index,data){
					if(index==0){
						$(this).prop("time",categorys[idx].map.printing_time);
					}else if(index==1){
						$(this).prop("time",categorys[idx].map.embroidery);
					}else if(index==2){
						$(this).prop("time",categorys[idx].map.beads_time);
					}else if(index==3){
						$(this).prop("time",categorys[idx].map.folding_time);
					}else if(index==4){
						$(this).prop("time",categorys[idx].map.dalan_time);
					}else if(index==5){
						$(this).prop("time",categorys[idx].map.washwater_time);
					}else if(index==6){
						$(this).prop("time",categorys[idx].map.other_time);
					}
				});
			},
			setCategorys:function(datas){
				categorys = datas;
				var styleClass = $(window.parent.document).find("#styleClass").val();
				var temp = "";
				$.each(datas,function(index,data){
					if(data.map.name == styleClass){
						$("#categorys").append('<option selected="selected">'+data.map.name+"("+data.map.code+")</option>");
						selectedIndex = 0;
					}else{
						$("#categorys").append("<option>"+data.map.name+"("+data.map.code+")</option>");
					}
				});
				biz.event.setStyleCarft(0);
			},
			cusMaterialRemove : function() { //客供料删除行按钮操作
				if ($(".cus_material_td:checked").length <= 0) {
					alert("请勾选要删除的行");
				} else {
					if(confirm("确认要删除吗？")) {
						$(".cus_material_td:checked").each(function(i,n){
							var id = $(this).parent().find("input[name=id]").val();
							if(undefined != id && null != id && id.length > 0) {
								$("#cusMaterialDelIds").val($("#cusMaterialDelIds").val() + "," + id);
							}
							$(this).parent().parent().remove();
//							biz.event.minusIframeHeight();
		    				parent.iFrameHeight("iframeDetail");
						});
					}
				}
			},
			cusMaterialAdd : function() { //客供料增加行按钮操作
				if(biz.event.ifAllowAdd("cusMaterialTable", 15)) {
					$("#cusMaterialTable").append(cusMaterialTr);
//					biz.event.addIframeHeight();
    				parent.iFrameHeight("iframeDetail");
				}
			},
			materialRemove : function() { //用料明细删除行按钮操作
				if ($(".material_td:checked").length <= 0) {
					alert("请勾选要删除的行");
				} else {
					if(confirm("确认要删除吗？")) {
						$(".material_td:checked").each(function(i,n){
							var id = $(this).parent().find("input[name=id]").val();
							if(undefined != id && null != id && id.length > 0) {
								$("#materialDelIds").val($("#materialDelIds").val() + "," + id);
							}
							$(this).parent().parent().remove();
//							biz.event.minusIframeHeight();
		    				parent.iFrameHeight("iframeDetail");
						});
					}
				}
			},
			//关联订单时，删除先前已经保存过的用料、客供料明细 Add by ZQ 2014-12-22
			removeOldInfos :function(){
				//待删除的用料
				$(".material_td").each(function(i,n){
					var id = $(this).parent().find("input[name=id]").val();
					if(undefined != id && null != id && id.length > 0) {
						$("#materialDelIds").val($("#materialDelIds").val() + "," + id);
					}
				});
				//待删除的客供料
				$(".cus_material_td").each(function(i,n){
					var id = $(this).parent().find("input[name=id]").val();
					if(undefined != id && null != id && id.length > 0) {
						$("#cusMaterialDelIds").val($("#cusMaterialDelIds").val() + "," + id);
					}
				});
			},
			materialAdd : function() { //用料明细增加行按钮操作
				if(biz.event.ifAllowAdd("materialTable", 15)) {
					$("#materialTable").append(materialTr);
//					biz.event.addIframeHeight();
    				parent.iFrameHeight("iframeDetail");
				}
			},
			ifAllowAdd : function(tableName, trTotal) {
				var count = trTotal;
				if("materialTable" != tableName) {
					count++; //+1为了排除表头
				}
				if($("#" + tableName + " tr").length >= count) {
					alert("增加最多不能超过" + trTotal + "行");
					return false;
				}
				return true;
			},
//			addIframeHeight : function(eHeight) {
//				var height = parseInt($(window.parent.document).find("#iframeDetail").attr("height"));
//				eHeight = (undefined == eHeight || null == eHeight || eHeight <= 0) ? 27 : eHeight;
//				height += eHeight;
//				$(window.parent.document).find("#iframeDetail").attr("height", height);
//			},
//			minusIframeHeight : function(eHeight) {
//				var height = parseInt($(window.parent.document).find("#iframeDetail").attr("height"));
//				eHeight = (undefined == eHeight || null == eHeight || eHeight <= 0) ? 27 : eHeight;
//				height -= eHeight;
//				$(window.parent.document).find("#iframeDetail").attr("height", height);
//			},
			checkIsPreProductNeed : function(){//检查产前版是否需要必填isPreProduct
				if($(window.parent.document).find("#isPreProduct").val()=='1' && $("#isPreproduct").val()=='' ){
					alert("请录入产前版完成日期！");
					$("#isPreproduct").focus();
					return false;
				}
				return true;
			},
			processOrder : function() { //保存并提交订单
				if("choosed"==$("input[name=isChoose]").val()){
					if(!confirm("由于关联订单数据比较多，保存后不可再修改关联订单！\n该订单已关联编号为【"+$("#related_order_code").val()+"】的订单，请确认！")){
						return;
					}
				}
				$("#processOrder").val("true"); //true标识流程流转
				if(biz.event.checkIsPreProductNeed()){
					biz.event.setMaterialName();
					biz.event.jsonProcessOrder();
				}
			},
			saveOrder : function() { //保存草稿
				$("#processOrder").val("false"); //false标识流程不流转，只保存数据
				if("choosed"==$("input[name=isChoose]").val()){
					if(!confirm("由于关联订单数据比较多，保存后不可再修改关联订单！\n该订单已关联编号为【"+$("#related_order_code").val()+"】的订单，请确认！")){
						return;
					}
				}
				if(biz.event.checkIsPreProductNeed()){
					biz.event.setMaterialName();
					biz.event.jsonProcessOrder();
				}
			},
			setMaterialName : function() { //重设用料搭配表和客供料表input的name值
				//物料搭配
				$(".material_tr").each(function(i,n){
					$(this).find(".table_text").each(function(j,k){
						var name = $(this).attr("name");
						if(!biz.event.ifResetElementName(name, "oaMaterialLists[")) { //判断是否需要重设元素name
							name = "oaMaterialLists[" + i + "]." + name;
							$(this).attr("name", name);
						}
					});
				});
				//客供料
				$(".cus_material_tr").each(function(i,n){
					var orderNum = "";
					$(this).find(".cus_material_size").each(function(j,k){ //拼接尺码
						orderNum += $(this).val() + "-";
					});
					if(orderNum.length > 0) { //设置尺码到隐藏text中
						orderNum = orderNum.substring(0, orderNum.length - 1);
						$(this).find('.cus_material_num').val(orderNum);
					}
					$(this).find(".table_text").not(".cus_material_size").each(function(j,k){
						var name = $(this).attr("name");
						if(!biz.event.ifResetElementName(name, "oaCusMaterialLists[")) { //判断是否需要重设元素name
							name = "oaCusMaterialLists[" + i + "]." + name;
							$(this).attr("name", name);
						}
					});
				});
			},
			ifResetElementName : function(name, str) { //判断是否已经对元素名称重新设置
				if(name.indexOf(str) < 0) {
					return false;
				} else {
					return true;
				}
			},
			jsonProcessOrder : function() {
				var successFlag=$("#tabOrderDetail").validationEngine("validate");
				if(successFlag){
					$("#processBtn").attr("disabled", "true");
					$("#saveBtn").attr("disabled", "true");
					$("#terminateBtn").attr("disabled", "true");
					var params = $("#tabOrderDetail").serializeArray();
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
		        				$("#terminateBtn").removeAttr("disabled");
	                		}
	                    },
	                    error: function() {
	        				$("#processBtn").removeAttr("disabled");
	        				$("#saveBtn").removeAttr("disabled");
	        				$("#terminateBtn").removeAttr("disabled");
	                    }
	                });
				}
				
			},
			setInputDisable : function() { //如果为查看详情，更改text属性为disable
				if(!detailUpdateFlag) {
					$("#tabOrderDetail input").attr("readonly", "true");
					$("#tabOrderDetail input[type=checkbox]").attr("disabled", "true");
					$("#tabOrderDetail textarea").attr("readonly", "true");
					$("#tabOrderDetail select").attr("disabled", "true");
					$("#chose_relate").remove();
					$("#cusMaterialRemoveBtn").remove();
					$("#cusMaterialAddBtn").remove();
					$("#materialRemoveBtn").remove();
					$("#materialAddBtn").remove();
					$("#otherFileUploadTd").text("");
					$("#processOrderDive").hide();
					$(".sp_style_craft").removeClass("sp_style_craft");
					$("#orderDetailDive").show();

					if($("#org").val()=='周颠' && $(window.parent.document).find("#status").val()!='1' && "finish_999"!=$(window.parent.document).find("#wfStep").val()){//这里只有mR主管可以进行中止订单的修改
						$("#terminateBtn1").show();
					}
				}
			},
			exportExcel : function() { //导出excel操作
				$("#exportBtn").attr("disabled", "true");
				$("#exportBtn").val("30秒后再次点击");
				top.location.href = "/bx/downOAOrder?oaOrder.id=" + $("#orderId").val() + "&node=2";
				setTimeout(function(){
					$("#exportBtn").removeAttr("disabled");
					$("#exportBtn").val("导出Excel");
				},30000);
			},
			download : function() { //下载文件
				window.location.href = "/oaOrderFileDownload?fileUrl=" + encodeURI($(this).attr("downloadUrl"));
			},
			toTerminateOrderDetail: function() { //终止订单
				$("#processBtn").attr("disabled", "true");
				$("#saveBtn").attr("disabled", "true");
				$("#terminateBtn").attr("disabled", "true");
				parent.toTerminateOrderDetail();
			}
		}
	}
	return fn;
});

//计算总和填写到合计当中
function culSizeTotal (obj) {
	if(!$(obj).validationEngine('validate') && obj.value!=''){
		var total = 0;
		$(obj).parent().parent().find(".cus_material_size").each(function(i,n){
			if("" != $(this).val()) {
				total += parseFloat($(this).val());
			}
		});
		$(obj).parent().parent().find("input[name=total]").val(total);
	}
}

//文件上传回调函数
function uploadComplete(data) {
	data = data
			.replace(
					'<meta http-equiv="content-type" content="text/html; charset=UTF-8">',
					"");
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
//		var html = "<a href='javascript:void(0)' class='z_title_sty1 download'";
//		html += "downloadUrl='" + json.url + "'>" + json.fileName+ "</a>";
		var html = "<a href='" + json.url + "'>" + json.fileName+ "</a>";
		$("#otherFile").html(json.fileName);
	}
}

function showMessage(data) {
	alert(data);
}