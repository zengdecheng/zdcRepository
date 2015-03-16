requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
		"v":"jqueryValidation/js/jquery.validationEngine.min",
		"vl":"jqueryValidation/js/jquery.validationEngine-zh_CN"
	}
});
var wfStepIndex;//订单所在节点
var orderId = $(window.parent.document).find("#orderId").val();	//订单ID
var opt;		//autoFill配置
var index;		//当前所在index
var jsonData;	//后台传的数据
var isShowDetail=false;
define([ "v","vl" ], function(u,up) {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var biz = {
		init : function() {
			biz.event.initOrderData();
			biz.event.jsonGetDaBanFiveNode();
			biz.event.initValidation();
			biz.event.initTrackes();
		},
		bind : function() {
			$("#yidongBtn").on("click", biz.event.jsonSaveTracke);
			$("#exportBtn").on("click", biz.event.exportExcel); //导出订单Excel
			$("#processBtn").on("click", biz.event.processOrder); //保存并提交
			$("#saveBtn").on("click", biz.event.saveOrder); //保存草稿
			$(".download").on("click", biz.event.download); //下载文件
			$(document).on("keyup",".unit_num",biz.event.getCpTotalPrice);//自动获得大货金额
			$(document).on("keyup",".cp_price",biz.event.getCpTotalPrice);//自动获得大货金额
			$(document).on("keyup",".cp_loss",biz.event.getCpTotalPrice);//自动获得大货金额
			$(document).on("keyup",".cp_total_price",biz.event.getMGoodsPrice);//重新计算大货物料总金额
			$(document).on("keyup",".cp_shear_price",biz.event.getMShearPrice);//重新计算散剪物料总金额
			$(document).on("keyup",".cp_loss",biz.event.getCpShearPrice);//自动获得散剪金额
			$(document).on("keyup",".unit_num",biz.event.getCpShearPrice);//自动获得散剪金额
			$(document).on("keyup",".shear_price",biz.event.getCpShearPrice);//自动获得散剪金额
			$("#ostamp").on("keyup",biz.event.countWaiXiePrice);//得到总外协费用
			$("#oembroider").on("keyup",biz.event.countWaiXiePrice);//得到总外协费用
			$("#owash").on("keyup",biz.event.countWaiXiePrice);//得到总外协费用
			$("#pfactory_match_0").on("keyup",biz.event.calPswing0);//自动计算缝制
			$("#pfactory_match_1").on("keyup",biz.event.calPswing1);//自动计算缝制
			$("#pfactory_match_2").on("keyup",biz.event.calPswing2);//自动计算缝制
			$("#sam").on("keyup",biz.event.calPswing);//自动计算缝制
			$("#minute_wage_rate").on("keyup",biz.event.calPswing);//自动计算缝制
			$(".he1").on("keyup",biz.event.countHeJi1);//重新计算合计1
			$(".he2").on("keyup",biz.event.countHeJi2);//重新计算合计2
			$(".he3").on("keyup",biz.event.countHeJi3);//重新计算合计3
			$("#cd0").on("keyup",biz.event.countD1);//重新计算总报价D1
			$("#cd1").on("keyup",biz.event.countD2);//重新计算总报价D2
			$("#cd2").on("keyup",biz.event.countD3);//重新计算总报价D3
			$("#cp0").on("keyup",biz.event.countB1);//重新计算总报价B1
			$("#cp1").on("keyup",biz.event.countB2);//重新计算总报价B2
			$("#cp2").on("keyup",biz.event.countB3);//重新计算总报价B3
			$("#oTotalPrice").on("keyup",biz.event.repeteSumPrice);//重新计算总报价
			$("input").on("keydown",biz.event.calFillText);//每个单元格实现简单的计算
		},
		event : {
			calFillText:function(e){
				var key=e.which;
				if(key==13){
					var text=$(this).val();
					$(this).val(eval(text));
				}
			},
			calPswing:function(){
				$("#psew_0").autoCompute(calSwing0);//自动计算缝制0中的数值
				$("#psew_1").autoCompute(calSwing1);//自动计算缝制1中的数值
				$("#psew_2").autoCompute(calSwing2);//自动计算缝制2中的数值
				biz.event.countHeJi1();//重新计算合计1
				biz.event.countHeJi2();//重新计算合计2
				biz.event.countHeJi3();//重新计算合计3
			},
			calPswing0:function(){
				$("#psew_0").autoCompute(calSwing0);//自动计算缝制0中的数值
				biz.event.countHeJi1();//重新计算合计1
			},
			calPswing1:function(){
				$("#psew_1").autoCompute(calSwing1);//自动计算缝制1中的数值
				biz.event.countHeJi2();//重新计算合计2
			},
			calPswing2:function(){
				$("#psew_2").autoCompute(calSwing2);//自动计算缝制2中的数值
				biz.event.countHeJi3();//重新计算合计3
			},
			countB1:function(){
				if($("#cp0").val()>0){
    				$("#b0").autoCompute(calB0);//自动计算总报价B0
    			}
			},
			countB2:function(){
				if($("#cp1").val()>0){
    				$("#b1").autoCompute(calB1);//自动计算总报价B0
    			}
			},
			countB3:function(){
				if($("#cp2").val()>0){
    				$("#b2").autoCompute(calB2);//自动计算总报价B0
    			}
			},
			countD1:function(){
				if($("#cd0").val()>0){
    				$("#d0").autoCompute(calD0);//自动计算总报价D1
    			}
			},
			countD2:function(){
				if($("#cd1").val()>0){
    				$("#d1").autoCompute(calD1);//自动计算总报价D1
    			}
			},
			countD3:function(){
				if($("#cd2").val()>0){
    				$("#d2").autoCompute(calD2);//自动计算总报价D1
    			}
			},
			countHeJi1:function(){
				$("#cp0").autoCompute(calHeJi1);//重新计算合计1
				//重新计算B1
				if($("#cp0").val()>0){
    				$("#b0").autoCompute(calB0);//自动计算总报价B0
    			}
			},
			countHeJi2:function(){
				$("#cp1").autoCompute(calHeJi2);//重新计算合计2
				//重新计算B1
				if($("#cp1").val()>0){
    				$("#b1").autoCompute(calB1);//自动计算总报价B1
    			}
			},
			countHeJi3:function(){
				$("#cp2").autoCompute(calHeJi3);//重新计算合计3
				//重新计算B1
				if($("#cp2").val()>0){
    				$("#b2").autoCompute(calB2);//自动计算总报价B2
    			}
			},
			countWaiXiePrice:function(){
				$("#oTotalPrice").autoCompute(calWaiXie);//重新计算总外协费用
				biz.event.repeteSumPrice();//重新计算总报价
			},
			getCpTotalPrice:function(){
				var $unit_num=$(this).parent().parent().find(".unit_num");
				var $cp_price=$(this).parent().parent().find(".cp_price");
				var $cp_loss=$(this).parent().parent().find(".cp_loss");
				if(!$unit_num.validationEngine("validate") && !$cp_price.validationEngine("validate") && !$cp_loss.validationEngine("validate")){
					var _unit_num = $unit_num.val();
					var _cp_price = $cp_price.val();
					var _cp_loss = $cp_loss.val();
					if(""==_unit_num || ""==_cp_price || ""==_cp_loss){
						$(this).parent().parent().find(".cp_total_price").val("");
						return;
					}
					$(this).parent().parent().find(".cp_total_price").val((parseFloat(_unit_num)*parseFloat(_cp_price)*(1+parseFloat(_cp_loss/100))).toFixed(2));
				}else{
					$(this).parent().parent().find(".cp_total_price").val("");
				}
				//重新计算大货物料成本合计
				biz.event.getMGoodsPrice();
			},
			getCpShearPrice:function(){
				var $unit_num=$(this).parent().parent().find(".unit_num");
				var $shear_price=$(this).parent().parent().find(".shear_price");
				var $cp_loss=$(this).parent().parent().find(".cp_loss");
				if(!$unit_num.validationEngine("validate") && !$shear_price.validationEngine("validate") && !$cp_loss.validationEngine("validate")){
					var _unit_num = $unit_num.val();
					var _shear_price = $shear_price.val();
					var _cp_loss = $cp_loss.val();
					if(""==_unit_num || ""==_shear_price || ""==_cp_loss){
						$(this).parent().parent().find(".cp_shear_price").val("");
						return;
					}
					$(this).parent().parent().find(".cp_shear_price").val((parseFloat(_unit_num)*parseFloat(_shear_price)*(1+parseFloat(_cp_loss/100))).toFixed(2));
				}else{
					$(this).parent().parent().find(".cp_shear_price").val("");
				}
				//重新计算散剪物料成本合计
				biz.event.getMShearPrice();
			},
			getMShearPrice:function(){
				var result=0;
				$(".cp_shear_price").each(function(){
					if($(this).val()!='' &&  undefined!=$(this).val()){
						result+=parseFloat($(this).val());
					}
				});
				$("#mShearPrice").val(result.toFixed(2));//计算物料成本散剪合计
			},
			repeteSumPrice:function(){
				//重新计算总报价
				if($("#cp0").val()>0){
    				$("#b0").autoCompute(calB0);//自动计算总报价B0
    			}
    			if($("#cp1").val()>0){
    				$("#b1").autoCompute(calB1);//自动计算总报价B1
    			}
    			if($("#cp2").val()>0){
    				$("#b2").autoCompute(calB2);//自动计算总报价B2
    			}
    			if($("#cd0").val()>0){
    				$("#d0").autoCompute(calD0);//自动计算总报价D0
    			}
    			if($("#cd1").val()>0){
    				$("#d1").autoCompute(calD1);//自动计算总报价D1
    			}
    			if($("#cd2").val()>0){
    				$("#d2").autoCompute(calD2);//自动计算总报价D2
    			}
			},
			getMGoodsPrice:function(){
					var result=0;
					$(".cp_total_price").each(function(){
						result+=parseFloat($(this).val());
					});
					$("#mGoodsPrice").val(result.toFixed(2));//计算物料成本大货合计
					biz.event.repeteSumPrice();//重新计算总报价
					
			},
			initOrderData : function() {
				$("#orderId").val(orderId);
				var orderDetail = $(window.parent.document).find("#orderDetail").val(); //是否是查看订单详情
				wfStepIndex = $(window.parent.document).find("#wfStepIndex").val();//订单所在节点
				if("true" != orderDetail && "5" == wfStepIndex) { //判断是否可编辑
//			  if(true){
					$("#sales").attr("fillName","oaOrderDetail.worker");
					isShowDetail = true;//可编辑
					$("#materialDescAddBtn").on("click", biz.event.materialAdd);
					$("#materialDescRemoveBtn").on("click", biz.event.materialRemove);
					$("#orderDetailDive").remove();
				}else{
					$("#materialDescAddBtn,#materialDescRemoveBtn").remove();
					$("#costing tr:first th:first").remove();
					$("#processOrderDive").remove();
					$("#yidongMemo").remove();
					$("#otherFileUploadTd").remove();
					$("form input,textarea").prop("disabled", true);
				}
				if(parseInt(wfStepIndex) < 5) {
					$("#exportBtn").remove();
				}
				parent.iFrameHeight("iframeCosting");
				//初始化模板
				var templet;//模板
				if(!isShowDetail){
					templet ="<tr><td elementFillName='materialCost[].material_prop'/><td elementFillName='materialCost[].type'/><td elementFillName='materialCost[].material_name'/><td elementFillName='materialCost[].color'/><td elementFillName='materialCost[].buffon'/><td elementFillName='materialCost[].unit_num'/><td elementFillName='materialCost[].cp_price'/><td elementFillName='materialCost[].shear_price'/><td elementFillName='materialCost[].cp_loss'/><td><input style='width: 65px;background-color:#f5f5f5;border:none;color:#565656' readonly  fillName='materialCost[].cp_total_price' class='cp_total_price' type='text'></td><td ><input style='width: 65px;background-color:#f5f5f5;border:none;color:#565656' readonly  fillName='materialCost[].cp_shear_price' class='cp_shear_price' type='text'></td><td elementFillName='materialCost[].cp_memo'/></tr>";
				}else{
					templet ="<tr><td><input type='checkbox'/><input type='hidden' name='oaDaBanInfos[].oaMaterialList' fillName='materialCost[].ml_id'/>" +
							"<input type='hidden' class='materialDelIds' name='oaMaterialLists[].id' fillName='materialCost[].ml_id'/>" +
							"<input type='hidden' class='materiaDesc' name='oaDaBanInfos[].id' fillName='materialCost[].dbi_id'/>" +
							"</td><td elementFillName='materialCost[].material_prop'/>" +
							"<td elementFillName='materialCost[].type'/>" +
							"<td elementFillName='materialCost[].material_name'/><td elementFillName='materialCost[].color'/><td elementFillName='materialCost[].buffon'/><td><input style='width: 65px;' name='oaDaBanInfos[].unitNum' fillName='materialCost[].unit_num' class='unit_num validate[custom[number2],maxSize[10]]' type='text'></td><td><input style='width: 65px;' name='oaDaBanInfos[].cpPrice' fillName='materialCost[].cp_price' class='cp_price validate[custom[number3],maxSize[10]]' type='text'></td><td><input style='width: 65px;' name='oaDaBanInfos[].shearPrice' fillName='materialCost[].shear_price' class='shear_price validate[custom[number3],maxSize[10]]' type='text'></td><td><input style='width: 65px;' name='oaDaBanInfos[].cpLoss' fillName='materialCost[].cp_loss' class='cp_loss validate[custom[number2],maxSize[10]]' type='text'></td><td><input style='width: 65px;' name='oaDaBanInfos[].cpTotalPrice' fillName='materialCost[].cp_total_price' class='cp_total_price validate[custom[number2],maxSize[10]]' type='text'></td><td><input style='width: 65px;' name='oaDaBanInfos[].cpShearPrice' fillName='materialCost[].cp_shear_price' class='cp_shear_price validate[custom[number2],maxSize[10]]' type='text'></td><td><input style='width: 120px;' name='oaDaBanInfos[].cpMemo' fillName='materialCost[].cp_memo' class='validate[maxSize[100]]' type='text'></td></tr>";
				}
				opt = {
					formFill:true,
					formFillName:'fillName',
					elementText:true,
					elementFillName:'elementFillName',	
					addRow:true,				// 自动添加行
					addNum:0,					// 添加的数量	addRow:true时有效
					addPoint:'costing',	// 添加点		element id
					templet:templet,			// 模板			addRow:true时有效
					isList:true,				// name是否list 是list则name[0],name[1]	addRow:true时有效
					debug : false
				};
				calWaiXie={
					paramsType:"id",
					valType:"val",
					params:["#ostamp","#oembroider","#owash"],
					process:"[0]+[1]+[2]",
					outParamsType:"id",//输出元素属性类型 默认为Id [class]
					outValType:"val",//计算值的方式
					outPoint:'#oTotalPrice'//计算结果的输出点
				}
				calHeJi1={
						paramsType:"id",
						valType:"val",
						params:["#pcutting_0","#psew_0","#pLast_0"],
						process:"[0]+[1]+[2]",
						outParamsType:"id",//输出元素属性类型 默认为Id [class]
						outValType:"val"	
				}
				calHeJi2={
						paramsType:"id",
						valType:"val",
						params:["#pcutting_1","#psew_1","#pLast_1"],
						process:"[0]+[1]+[2]",
						outParamsType:"id",//输出元素属性类型 默认为Id [class]
						outValType:"val"	
				}
				calHeJi3={
						paramsType:"id",
						valType:"val",
						params:["#pcutting_2","#psew_2","#pLast_2"],
						process:"[0]+[1]+[2]",
						outParamsType:"id",//输出元素属性类型 默认为Id [class]
						outValType:"val"	
				}
				calB0={
						paramsType:"id",
						valType:"val",
						params:["#mGoodsPrice","#oTotalPrice","#cp0"],
						process:"[0]+[1]+[2]",
						outParamsType:"id",//输出元素属性类型 默认为Id [class]
						outValType:"val"	
				}
				calB1={
						paramsType:"id",
						valType:"val",
						params:["#mGoodsPrice","#oTotalPrice","#cp1"],
						process:"[0]+[1]+[2]",
						outParamsType:"id",//输出元素属性类型 默认为Id [class]
						outValType:"val"	
				}
				calB2={
						paramsType:"id",
						valType:"val",
						params:["#mGoodsPrice","#oTotalPrice","#cp2"],
						process:"[0]+[1]+[2]",
						outParamsType:"id",//输出元素属性类型 默认为Id [class]
						outValType:"val"	
				}
				calD0={
						paramsType:"id",
						valType:"val",
						params:["#mGoodsPrice","#oTotalPrice","#cd0"],
						process:"[0]+[1]+[2]",
						outParamsType:"id",//输出元素属性类型 默认为Id [class]
						outValType:"val"	
				}
				calD1={
						paramsType:"id",
						valType:"val",
						params:["#mGoodsPrice","#oTotalPrice","#cd1"],
						process:"[0]+[1]+[2]",
						outParamsType:"id",//输出元素属性类型 默认为Id [class]
						outValType:"val"	
				}
				calD2={
						paramsType:"id",
						valType:"val",
						params:["#mGoodsPrice","#oTotalPrice","#cd2"],
						process:"[0]+[1]+[2]",
						outParamsType:"id",//输出元素属性类型 默认为Id [class]
						outValType:"val"	
				}
				calSwing0={
						paramsType:"id",
						valType:"val",
						params:["#sam","#minute_wage_rate","#pfactory_match_0"],
						process:"[0]*[1]*[2]",
						outParamsType:"id",
						outValType:"val"
						
				}
				calSwing1={
						paramsType:"id",
						valType:"val",
						params:["#sam","#minute_wage_rate","#pfactory_match_1"],
						process:"[0]*[1]*[2]",
						outParamsType:"id",
						outValType:"val"
				}
				calSwing2={
						paramsType:"id",
						valType:"val",
						params:["#sam","#minute_wage_rate","#pfactory_match_2"],
						process:"[0]*[1]*[2]",
						outParamsType:"id",
						outValType:"val"
				}
			},
			jsonGetDaBanFiveNode : function() { // 获取第五节点页面信息
				$.ajax({
                    url: '/bx/jsonGetDaBanFiveNode?orderId='+orderId + "&wfStepIndex=" + wfStepIndex,
                    type: "post",
                    async: false,
                    success: function(data) {
                    	if("ajaxLogin" == data){
                			alert("登录超时，请重新登录");
                		} else if("0" ==  data.code){
                			//将大货单价转换为统一的类型进行填充
                			if(data.materialCost.length>0){
                				for(var i=0;i<data.materialCost.length;i++){
                					if(data.materialCost[i].cp_price==0){
                						var goodsUnit=data.materialCost[i].goods_unit;
                    					var goodsPrice=parseFloat(data.materialCost[i].goods_price);
                    					var buyer_loss=parseFloat(data.materialCost[i].buyer_loss);
                    					if((undefined == buyer_loss && isNaN(buyer_loss))|| buyer_loss==0){
                    						buyer_loss =100;
                    					}
                    					if("KG"==goodsUnit){
                    						var paper_tube=parseFloat(data.materialCost[i].paper_tube);
                    						var deviation=parseFloat(data.materialCost[i].deviation);
                    						var weight=parseInt(data.materialCost[i].weight);
                    						var buffon=parseFloat(data.materialCost[i].buffon);
                    						if(undefined != paper_tube && undefined != deviation && undefined != weight && undefined != buffon && undefined != buyer_loss && 
                    								!isNaN(weight) && !isNaN(buffon) && !isNaN(paper_tube) && !isNaN(deviation) && !isNaN(buyer_loss)){
                    							var cp_price=(((goodsPrice*(paper_tube+deviation)*41+goodsPrice*1000)/1000)/(1/(weight/1000*buffon/100)));
                    							data.materialCost[i].cp_price=cp_price.toFixed(3);
                    						}
                    					}else if("码"==goodsUnit){
                    						var cp_price=(goodsPrice/0.9144)/buyer_loss*100;
                    						data.materialCost[i].cp_price=cp_price.toFixed(3);
                    					}else if("M"==goodsUnit){
                    						var cp_price=(goodsPrice/buyer_loss)*100;
                    						data.materialCost[i].cp_price=cp_price.toFixed(3);
                    					}else{
                    						data.materialCost[i].cp_price=goodsPrice;
                    					//	continue;
                    					}
                					}
                				}
                				
                			}
                			if(undefined != data.oaCost && null != data.oaCost) {
	                			//B节点数据初始化
                				if(null!=data.oaCost.pcutting){
                					var pcutting = data.oaCost.pcutting.split(",");
                					data.oaCost.pcutting=pcutting;
                				}
	                			if(null != data.oaCost.psew){
	                				var psew = data.oaCost.psew.split(",");
	                				data.oaCost.psew=psew;
	                			}
	                			if(null != data.oaCost.plast){
	                				var plast = data.oaCost.plast.split(",");
	                				data.oaCost.plast=plast;
	                			}
	                			if(null != data.oaCost.pfactoryMatch){
	                				var pFactoryMatch=data.oaCost.pfactoryMatch.split(",");
	                				data.oaCost.pfactoryMatch=pFactoryMatch;
	                			}
	                			
                			}
                			
                			if(undefined != data.oaOrderDetail && null != data.oaOrderDetail && undefined != data.oaOrderDetail.other_file && null != data.oaOrderDetail.other_file && "" != data.oaOrderDetail.other_file && "null" != data.oaOrderDetail.other_file) {
                				var downFile = "<a downloadurl='" + data.oaOrderDetail.other_file + "' class='z_title_sty1 mar_l10 download_a download' href='javascript:void(0)'>" + data.oaOrderDetail.other_file_name + "</a>";
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
                			opt.addNum = data.materialCost.length;
                			index = opt.addNum;jsonData = data;
                			$("#tabCosting").autoFill(data, opt);
                			
                			$(".he1,.he2,.he3").each(function(index,element){
                				this.value = this.value.trim();
                			})
                			$("input[name='oaCost.pFactoryMatch']").each(function(index,element){
                				this.value = this.value.trim();
                			})
                			//添加默认的三条数据
                			/*var lastTr="<tr id='firstOther'><td/><td/><td>辅料</td><td>唛头</td><td/><td/><td><input style='width:65px;' type='text' value='2.00'/></td><td><input style='width:65px;' type='text' value='0.05'/></td><td/><td/><td><input id='mai_tou' style='width:65px;' type='text' value='0.10'/></td><td/><td/></tr>" +
                					"<tr><td/><td/><td>辅料</td><td>包装物料</td><td/><td/><td><input style='width:65px;' type='text' value='1.00'/></td><td><input style='width:65px;' type='text' value='0.50'/></td><td/><td/><td><input id='bao_zhuang_wl' style='width:65px;' type='text' value='0.50'/></td><td/><td/></tr>" +
                					"<tr><td/><td/><td>辅料</td><td>车缝线</td><td/><td/><td><input style='width:65px;' type='text' value='1.00'/></td><td><input style='width:65px;' type='text' value='0.30'/></td><td/><td/><td><input id='che_feng_xian' style='width:65px;' type='text' value='0.30'/></td><td/><td/></tr>";
                			$("#costing").append(lastTr);*/
                			if(undefined != data.oaCost && null != data.oaCost){
	                			if(null==data.oaCost.mgoodsPrice || isNaN(data.oaCost.mgoodsPrice) || undefined==data.oaCost.mgoodsPrice){
	                				biz.event.getMGoodsPrice();//计算物料成本合计(大货)
	                			}
	                			if(null==data.oaCost.mshearPrice || isNaN(data.oaCost.mshearPrice) || undefined==data.oaCost.mshearPrice){
	                				biz.event.getMShearPrice();//计算物料成本合计（散剪）
	                			}
	                			if(null==data.oaCost.waiXieTotalPrice || isNaN(data.oaCost.waiXieTotalPrice) || undefined==data.oaCost.waiXieTotalPrice){
	                				$("#oTotalPrice").autoCompute(calWaiXie);//自动计算总外协费用
	                			}
	                			if(null==data.oaCost.bhe1 || isNaN(data.oaCost.bhe1) || undefined==data.oaCost.bhe1){
	                				$("#cp0").autoCompute(calHeJi1);//自动计算合计1
	                			}
	                			if(null==data.oaCost.bhe2 || isNaN(data.oaCost.bhe2) || undefined==data.oaCost.bhe2){
	                				$("#cp1").autoCompute(calHeJi2);//自动计算合计2
	                			}
	                			if(null==data.oaCost.bhe3 || isNaN(data.oaCost.bhe3) || undefined==data.oaCost.bhe3){
	                				$("#cp2").autoCompute(calHeJi3);//自动计算合计3
	                			}
	                			if(null==data.oaCost.btotal1 || isNaN(data.oaCost.btotal1) || undefined==data.oaCost.btotal1){
	                				if($("#cp0").val()>0){
	                    				$("#b0").autoCompute(calB0);//自动计算总报价B0
	                    			}
	                			}
	                			if(null==data.oaCost.btotal2 || isNaN(data.oaCost.btotal2) || undefined==data.oaCost.btotal2){
	                				if($("#cp1").val()>0){
	                    				$("#b1").autoCompute(calB1);//自动计算总报价B1
	                    			}
	                			}
	                			if(null==data.oaCost.btotal3 || isNaN(data.oaCost.btotal3) || undefined==data.oaCost.btotal3){
	                				if($("#cp2").val()>0){
	                    				$("#b2").autoCompute(calB2);//自动计算总报价B2
	                    			}
	                			}
	                			if(null==data.oaCost.dtotal1 || isNaN(data.oaCost.dtotal1) || undefined==data.oaCost.dtotal1){
	                				if($("#cd0").val()>0){
	                    				$("#d0").autoCompute(calD0);//自动计算总报价D0
	                    			}
	                			}
	                			if(null==data.oaCost.dtotal2 || isNaN(data.oaCost.dtotal2) || undefined==data.oaCost.dtotal2){
	                				if($("#cd1").val()>0){
	                    				$("#d1").autoCompute(calD1);//自动计算总报价D1
	                    			}
	                			}
	                			if(null==data.oaCost.dtotal3 || isNaN(data.oaCost.dtotal3) || undefined==data.oaCost.dtotal3){
	                				if($("#cd2").val()>0){
	                    				$("#d2").autoCompute(calD2);//自动计算总报价D2
	                    			}
	                			}
                			}
//                			biz.event.repeteSumPrice();//自动计算总报价
                			parent.iFrameHeight("iframeCosting");
                		}else{
                			alert(data.msg);
                		}
                    }
                });
			},
			initValidation:function(){
				$("#tabCosting").validationEngine('attach',{
					scroll:true,
					autoHidePrompt:true,
					autoHideDelay:1500,
					showOneMessage: true,
					fadeDuration : 0.3,
					promptPosition : "bottomLeft"
				});
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
	                		} else {
	                			alert(data.msg);
	                		}
	                    }
	                });
				} else {
					alert("发布异动信息不能为空");
				}
			},
			initTrackes : function(){
				if(jsonData){
					$.each(jsonData.oaTrackes,function(i,v){
						biz.event.putOaTrackesTrData(v.memo, v.user, v.time);
					})
				}
			},
			putOaTrackesTrData : function(td1, td2, td3) { //添加一行异动信息到列表
				var tr = "<tr><td width='53px' height='22px'></td><td width='573px'>" + td1 + "</td><td>" + td2 + "/" + td3 + "</td></tr>";
				$("#oaTrackesTable").append(tr);
				parent.iFrameHeight("iframeCosting");
			},
			exportExcel : function() { //导出excel操作
				$("#exportBtn").attr("disabled", "true");
				$("#exportBtn").val("30秒后再次点击");
				top.location.href = "/bx/downOAOrder?oaOrder.id=" + orderId + "&node=5";
				setTimeout(function(){
					$("#exportBtn").removeAttr("disabled");
					$("#exportBtn").val("导出Excel");
				},30000);
			},
			processOrder : function() { //保存并提交订单
				$("#processOrder").val("true"); //true标识流程流转
				biz.event.jsonProcessOrder();
			},
			saveOrder : function() { //保存草稿
				$("#processOrder").val("false"); //false标识流程不流转，只保存数据
				biz.event.jsonProcessOrder();
			},
			materialRemove : function() {
				var $ckbs=$("input[type=checkbox]:checked");
				if($ckbs.size()==0){
				    alert("请勾选要删除的行");
					return;
				}else{
					if(confirm("是否确认删除选中行？")){
						$ckbs.each(function(){
							var materiaList = $('#materialDelIds').val();
							var materiaDesc = $('#materialDescDelIds').val();
							var delML =$(this).parent().parent().find('.materialDelIds').val();
							var delMD = $(this).parent().parent().find('.materiaDesc').val();
							if(materiaList==''){
								$('#materialDelIds').val(delML);
							}else{
								$('#materialDelIds').val(materiaList+','+delML);
							}
							if(materiaDesc==''){
								$('#materialDescDelIds').val(delMD);
							}else{
								$('#materialDescDelIds').val(materiaDesc+','+delMD);
							}
					 		$(this).parent().parent().remove();
						});
					}
				}
				parent.iFrameHeight("iframeCosting");
			},
			materialAdd : function() {
				if($("#costing>tbody tr").length>15){
					alert("最多添加十五行");
				 	return;
				}
				var temp;
				var templet="<tr><td><input type='checkbox'/><input type='hidden' name='oaDaBanInfos[].oaMaterialList' fillName='materialCost[].ml_id'/>" +
				"<input type='hidden' class='materialDelIds' name='oaMaterialLists[].id' fillName='materialCost[].ml_id'/>" +
				"<input type='hidden' class='materiaDesc' name='oaDaBanInfos[].id' fillName='materialCost[].dbi_id'/>" +
					"</td><td><input style='width: 105px;' name='oaMaterialLists[].materialProp' fillName='materialCost[].material_prop' class='validate[maxSize[20]]' type='text'></td>" +
				"<td><select name='oaMaterialLists[].type' fillName='materialCost[].type'><option value='主料'>主料</option><option value='辅料'>辅料</option></select></td>" +
				"<td><input style='width: 124px;' name='oaMaterialLists[].materialName' fillName='materialCost[].material_name' class='validate[maxSize[20]]' type='text'></td><td><input style='width: 65px;' name='oaMaterialLists[].color' fillName='materialCost[].color' class='validate[maxSize[10]]' type='text'></td><td><input style='width: 65px;' name='oaDaBanInfos[].buffon' fillName='materialCost[].buffon' class='validate[custom[number2],maxSize[10]]' type='text'></td><td><input style='width: 65px;' name='oaDaBanInfos[].unitNum' fillName='materialCost[].unit_num' class='unit_num validate[custom[number2],maxSize[10]]' type='text'></td><td><input style='width: 65px;' name='oaDaBanInfos[].cpPrice' fillName='materialCost[].cp_price' class='cp_price validate[custom[number3],maxSize[10]]' type='text'></td><td><input style='width: 65px;' name='oaDaBanInfos[].shearPrice' fillName='materialCost[].shear_price' class='shear_price validate[custom[number3],maxSize[10]]' type='text'></td><td><input style='width: 65px;' name='oaDaBanInfos[].cpLoss' fillName='materialCost[].cp_loss' class='cp_loss validate[custom[number2],maxSize[10]]' type='text'></td><td><input style='width: 65px;' name='oaDaBanInfos[].cpTotalPrice' fillName='materialCost[].cp_total_price' class='cp_total_price validate[custom[number2],maxSize[10]]' type='text'></td><td><input style='width: 65px;' name='oaDaBanInfos[].cpShearPrice' fillName='materialCost[].cp_shear_price' class='cp_shear_price validate[custom[number2],maxSize[10]]' type='text'></td><td><input style='width: 120px;' name='oaDaBanInfos[].cpMemo' fillName='materialCost[].cp_memo' class='validate[maxSize[100]]' type='text'></td></tr>";
				temp = templet.replace(/\[]/g,"["+index+"]");
				index +=1;
				//$("#firstOther").before(temp);
				$("#costing").append(temp);
				parent.iFrameHeight("iframeCosting");
			},
			jsonProcessOrder:function(){//ajax提交表单
				//先要对所填写的数据进行验证合法后才进行提交
				var successFlag=$("#tabCosting").validationEngine("validate");
				if(successFlag){
					$("#processBtn").attr("disabled", "true");
					$("#saveBtn").attr("disabled", "true");
					$("#backBtn").attr("disabled", "true");
					var params = $("#tabCosting").serializeArray();
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
			},
			download : function() { //下载文件
				window.location.href = "/oaOrderFileDownload?fileUrl=" + encodeURI($(this).attr("downloadUrl"));
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
	if (json.fileType == "pic") {
		$("#hid_attachment_url").val(json.url);
		var html = "<a href='" + json.url + "'>" + json.fileName+ "</a>";
		$("#otherFile").html(json.fileName);
	}
}

function showMessage(data) {
	alert(data);
}