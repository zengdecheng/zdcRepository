requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
		"u" : "util",
	}
});

define([ "u" ], function(u) {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var biz = {
		init : function() {
			biz.event.initColor();
		},
		bind : function() {
			$("#terminateBtn").on("click", biz.event.terminateOrder);
		},
		event : {
			initColor:function(){
				var rate=$("#time_consume").attr("data");
				if(parseFloat(rate) <= 33){
					$("#rate").css({"background-color":"#33cc00","color":"white"});
				}else if(parseFloat(rate) <= 66){
					$("#rate").css({"background-color":"#ff9900","color":"white"});
				}else if(parseFloat(rate) <= 100){
					$("#rate").css({"background-color":"#ff3300","color":"white"});
				}else {
					$("#rate").css({"background-color":"#000000","color":"white"});
				}
			},
			terminateOrder: function() {
				if($.trim($("#terminateMemo").val()) == "") {
					alert("终止原因不能为空");
					$("#terminateMemo").focus();
					return;
				}
				if(confirm('确认要终止吗？')) {
					$("#terminateBtn").attr("disabled", "true");
					var params = $("#terminateForm").serializeArray();
				  $.ajax({
	                    url: "/bx/terminateOrder",
	                    type: "post",
	                    data: params,
	                    success: function(data) {
	                    	if("ajaxLogin" == data){
	                			alert("登录超时，请重新登录");
	                		} else if("0" ==  data.code){
	                			//这里调用CRM中的接口 需要的参数是订单编号，订单ID，订单状态，数字签名
	        					var sellOrderId=$("#sellOrderId").val();//订单Id
	        					var orderCode=$("#orderCode").val();//订单编号
	        					var status=$("#status").val();//状态
	        					var oaSec=$("#oaSec").val();//数字签名
	        					var paramsStr={"order_code":orderCode,"order_id":sellOrderId,"order_status":'4',"oa_sec":oaSec};
	        					var para=$.param(paramsStr);//将参数格式化
	        					$.ajax({
	        	                    url: _url_prefix+"ext/modifyOrderStatus",
	        	                    type: "post",
	        	                    data: para,
	        	                    async:false,
	        	                    success: function(data) {
	        	                		if("success" ==  data.code){
	        	                		} else {
//	        	                			alert(data.msg);
	        		        				$("#terminateBtn").removeAttr("disabled");
	        	                		}
	        	                    },
	        	                    error: function() {
	        	        				$("#terminateBtn").removeAttr("disabled");
	        	                    }
	        	                });
	                			top.location.href = "/bx/todo";
	                		} else {
	                			alert(data.msg);
		        				$("#terminateBtn").removeAttr("disabled");
	                		}
	                    },
	                    error: function() {
	        				$("#terminateBtn").removeAttr("disabled");
	                    }
	                });
				}
			}
		}
	}
	return fn;
});
