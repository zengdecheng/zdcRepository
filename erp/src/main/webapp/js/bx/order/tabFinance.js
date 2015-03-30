requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
		"v":"jqueryValidation/js/jquery.validationEngine.min",
		"vl":"jqueryValidation/js/jquery.validationEngine-zh_CN"
	}
});
var wfStepIndex = $(window.parent.document).find("#wfStepIndex").val();//订单所在节点
var orderId = $(window.parent.document).find("#orderId").val();	//订单ID
var opt;		//autoFill配置
var index;		//当前所在index
var jsonData;	//后台传的数据
var isShowDetail=false;
var daoZhangMoney=0;//到账金额
var orderFee=0;//订单金额
var payTypeFather;//付款方式 
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
			biz.event.jsonGetDaHuoEightNode();
			biz.event.jsonGetFinancialRecord();
			biz.event.initValidation();
	/*		biz.event.initTrackes();*/
		},
		bind : function() {
			/*$("#yidongBtn").on("click", biz.event.jsonSaveTracke);
*/			$("#processBtn").on("click", biz.event.processOrder); //保存并提交
			$("#backBtn").on("click",biz.event.backOrder);//订单退回到上一步
			
		},
		event : {
			backOrder:function(){
				if($("#detailContent").val()=='' || $("#detailContent").val()==undefined ){
					alert("要退回订单，请先填写审核意见！");
					$("#detailContent").focus();
				}else{
					parent.backOrder('iframeFinance');
				}
			},
			initOrderData : function() {
				$("#orderId").val(orderId);
				var orderDetail = $(window.parent.document).find("#orderDetail").val(); //是否是查看订单详情
				payTypeFather=$(window.parent.document).find("#payType").val();//订单的支付方式
				$("#payType").text(payTypeFather);
				if("true" != orderDetail && "8" == wfStepIndex) { //判断是否可编辑
//			  if(true){
					isShowDetail = true;//可编辑
					$("#orderDetailDive").remove();
				}else{
					$("#processOrderDive").remove();
				/*	$("#yidongMemo").remove();*/
					$("form input,textarea").prop("disabled", true);
				}
				parent.iFrameHeight("iframeFinance");
				opt = {
					formFill:true,
					formFillName:'fillName',
					elementText:true,
					elementFillName:'elementfillname',
					debug : false
				};
			},
			jsonGetFinancialRecord:function(){//查询审核信息
				$.ajax({
					url:_url_prefix+'ext/jsonGetFinancialRecord?sellOrderId='+$(window.parent.document).find("#sellOrderId").val(),//获取销售订单Id
//					url:'http://192.168.1.200:8083/ext/jsonGetFinancialRecord?sellOrderId=2049',//获取销售订单Id
					type:"post",
					async:false,
					success:function(data){
						if("0"==data.code){
							if(undefined!=data.orderFee && null!=data.orderFee && "" !=data.orderFee){
								orderFee=data.orderFee;	
							}else{
								orderFee=0;	
							}
							$("#order_fee").html(data.orderFee);
							biz.event.showCheckRecord(data.fMap);//填充审核记录
						}else{
							alert(data.msg);
						}
						parent.iFrameHeight("iframeFinance");
					}
				});
			},
			showCheckRecord:function(fMap){
				if(fMap.length>0){
					for(var i=0;i<fMap.length;i++){
						if(fMap[i].state=="1"){//表示待确认
							var div="<div ><table style='width:80%;margin:5px 5px 5px 10px;font-size:12px;background-color:#FDE4EE'><tr height='28px'><td width='8%'>到账通知：</td>";
								div+="<td>"+fMap[i].money+"("+fMap[i].method+"%)&nbsp;&nbsp;提交时间："+fMap[i].apply_time+"</td><tr>";
								div+="<tr height='28px'><td>财务审核：</td><td>待确认</td></tr>";
								div+="<tr height='28px'><td>财务备注：</td><td>"+fMap[i].memo+"</td></tr></table></div>";
								$("#checkRedord").append(div);
						}else if(fMap[i].state=="2"){//表示已确认
							var div="<div ><table style='width:80%;margin:5px 5px 5px 10px;font-size:12px;background-color:#E1EEEE'><tr height='28px'><td width='8%'>到账通知：</td>";
							daoZhangMoney+=fMap[i].money;
							div+="<td>"+fMap[i].money+"("+fMap[i].method+"%)&nbsp;&nbsp;提交时间："+fMap[i].apply_time+"</td><tr>";
							div+="<tr height='28px'><td>财务审核：</td><td>已确认&nbsp;&nbsp;审核时间："+fMap[i].confirm_time+"</td></tr>";
							div+="<tr height='28px'><td>审核人：</td><td>"+fMap[i].confirm_staff+"</td></tr>";
							$("#operator").val(fMap[i].confirm_staff);//在订单跟踪报表中要使用
							div+="<tr height='28px'><td>财务备注：</td><td>"+fMap[i].memo+"</td></tr></table></div>";
							$("#checkRedord").append(div);
						}else if(fMap[i].state=="3"){//表示已拒绝
							var div="<div ><table style='width:80%;margin:5px 5px 5px 10px;font-size:12px;background-color:#E1EEEE'><tr height='28px'><td width='8%'>到账通知：</td>";
							div+="<td>"+fMap[i].money+"("+fMap[i].method+"%)&nbsp;&nbsp;提交时间："+fMap[i].apply_time+"</td><tr>";
							div+="<tr height='28px'><td>财务审核：</td><td>已拒绝&nbsp;&nbsp;审核时间："+fMap[i].confirm_time+"</td></tr>";
							$("#operator").val(fMap[i].confirm_staff);//在订单跟踪报表中要使用
							div+="<tr height='28px'><td>审核人：</td><td>"+fMap[i].confirm_staff+"</td></tr>";
							div+="<tr height='28px'><td>财务备注：</td><td>"+fMap[i].memo+"</td></tr></table></div>";
							$("#checkRedord").append(div);
						}
						parent.iFrameHeight("iframeFinance");
					}
					$("#daoZhangMoney").html(daoZhangMoney);
					var weiDaoZhang=parseFloat(orderFee)-parseFloat(daoZhangMoney);
					if(!isNaN(weiDaoZhang)){
						$("#weiDaoZhangMoney").html(weiDaoZhang);	
					}else{
						$("#weiDaoZhangMoney").html(0);	
					}
					
				}else{
					var div="<div ><table style='width:80%;margin:5px 5px 5px 10px;font-size:12px;background-color:#FDE4EE'><tr height='28px'><td width='8%'>暂无到账通知：</td>";
						div+="</tr></table></div>";
					$("#checkRedord").append(div);
				}
				
			},
			jsonGetDaHuoEightNode : function() { // 获取第八节点页面信息
				$.ajax({
                    url: '/bx/jsonGetDaHuoEightNode?oaOrderId='+orderId+"&wfStepIndex=" + wfStepIndex,
                    type: "post",
                    async: false,
                    success: function(data) {
                    	if("ajaxLogin" == data){
                			alert("登录超时，请重新登录");
                		} else if("0" ==  data.code){
                			jsonData=data;
                			
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
                			$("#tabFinance").autoFill(data, opt);
                			parent.iFrameHeight("iframeFinance");
                		} else {
                			alert(data.msg);
                		}
                    }
                });
			},
			initValidation:function(){
				$("#tabFinance").validationEngine('attach',{
					scroll:true,
					autoHidePrompt:true,
					autoHideDelay:1500,
					showOneMessage: true,
					fadeDuration : 0.3,
					promptPosition : "bottomLeft"
				});
			},
			/*jsonSaveTracke : function() { //保存异动信息
				if($.trim($("#yidongContent").val()) != "") {
					var params = {"oaTracke.memo" : $("#yidongContent").val(), "oaTracke.oaOrder" : orderId, "oaTracke.node" : $(window.parent.document).find("#wfStep").val()};
					$.ajax({
	                    url: "/bx/jsonSaveTracke",
	                    type: "post",
	                    data: params,
	                    success: function(data) {
	                		if("0" ==  data.code){
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
				parent.iFrameHeight("iframeFinance");
			},
			exportExcel : function() { //导出excel操作
				$("#exportBtn").attr("disabled", "true");
				$("#exportBtn").val("30秒后再次点击");
				top.location.href = "/bx/downOAOrder?oaOrder.id=" + orderId + "&node=6";
				setTimeout(function(){
					$("#exportBtn").removeAttr("disabled");
					$("#exportBtn").val("导出Excel");
				},30000);
			},*/
			processOrder : function() { //保存并提交订单
				$("#processOrder").val("true"); //true标识流程流转
				biz.event.jsonProcessOrder();
			},
			jsonProcessOrder:function(){//ajax提交表单
				//先要对所填写的数据进行验证合法后才进行提交
				var successFlag=$("#tabFinance").validationEngine("validate");
				if(successFlag){
					$("#processBtn").attr("disabled", "true");
					$("#backBtn").attr("disabled", "true");
					var params = $("#tabFinance").serializeArray();
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
		        				$("#backBtn").removeAttr("disabled");
	                		}
	                    },
	                    error: function() {
	        				$("#processBtn").removeAttr("disabled");
	        				$("#backBtn").removeAttr("disabled");
	                    }
	                });
				}
			}
		}
	}
	return fn;
});