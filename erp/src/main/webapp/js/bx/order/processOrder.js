requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
		"u" : "util",
	}
});
var _this, orderTypeNum;
var dabanIframeUrl = new Array();
var dahuoIframeUrl = new Array();
define([ "u" ], function(u) {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var biz = {
		init : function() {
			orderTypeNum = $("#orderTypeNum").val();
			if("2" == orderTypeNum) {
				dabanIframeUrl[0] = "/jsp/tabpage/tabOrderDetail.jsp";
				dabanIframeUrl[1] = "/jsp/tabpage/tabPurchase.jsp";
				dabanIframeUrl[2] = "/jsp/tabpage/tabTechnology.jsp";
				dabanIframeUrl[3] = "/jsp/tabpage/tabCosting.jsp";
				dabanIframeUrl[4] = "/jsp/tabpage/tabMrConfirm.jsp";
				dabanIframeUrl[5] = "/jsp/tabpage/tabOrderTimeout.jsp";
			} else if("3" == orderTypeNum) {
				dahuoIframeUrl[0] = "/jsp/tabpage/tabOrderDetail.jsp";
				dahuoIframeUrl[1] = "/jsp/tabpage/tabTechnology.jsp";
				dahuoIframeUrl[2] = "/jsp/tabpage/tabPurchase.jsp";
				dahuoIframeUrl[3] = "/jsp/tabpage/tabCQC.jsp";
				dahuoIframeUrl[4] = "/jsp/tabpage/tabTPE.jsp";
				dahuoIframeUrl[5] = "/jsp/tabpage/tabQA.jsp";
				dahuoIframeUrl[6] = "/jsp/tabpage/tabFinance.jsp";
				dahuoIframeUrl[7] = "/jsp/tabpage/tabLogistics.jsp";
				dahuoIframeUrl[8] = "/jsp/tabpage/tabOrderTimeout.jsp";
			}
//			if("样衣打版" == $("#orderType").val()) {
			var wfStepIndex = $("#wfStepIndex").val();
			if("true" == $("#orderDetail").val()) {
				wfStepIndex = 2;
			}
			$("#tab" + wfStepIndex).addClass("order_tab_active");
			$("#tab" + wfStepIndex).removeClass("order_tab");
			$("#tabPage" + wfStepIndex).addClass("tab_page_active");
			$("#tabPage" + wfStepIndex).removeClass("tab_page");
//			} else if("大货生产" == $("#orderType").val()) {
//				$("tabDahuo" + $("#wfStepIndex").val()).addClass("order_tab_active");
//				$("tabDahuo" + $("#wfStepIndex").val()).removeClass("order_tab");
//				$("tabDahuoPage" + $("#wfStepIndex").val()).addClass("tab_page_active");
//				$("tabDahuoPage" + $("#wfStepIndex").val()).removeClass("tab_page");
//			}
			biz.event.initColor();
			biz.event.iframeLoadData(wfStepIndex);
		},
		bind : function() {
			$('.tabone').on("click", biz.event.tabActive); //标签页选择绑定
		},
		event : {
			initColor:function(){
				$("#rate").css({"background-color":"red","color":"white"});
				if("1" == $("#status").val()) {
					$("#time_consume").text("已终止");
				} else {
					if("finish_999" == $("#wfStep").val()){
						$("#curNode").text("已完成");
					}
					
					var rate=$("#time_consume").attr("data");
					if(parseFloat(rate) <0){
						$("#rate").css({"background-color":"#0000ff","color":"white"});
					}else if(parseFloat(rate) < 33){
						$("#rate").css({"background-color":"#33cc00","color":"white"});
					}else if(parseFloat(rate) < 66){
						$("#rate").css({"background-color":"#ff9900","color":"white"});
					}else if(parseFloat(rate) <= 100){
						$("#rate").css({"background-color":"#ff3300","color":"white"});
					} else {
						$("#rate").css({"background-color":"#000","color":"white"});
					}
				}
			},
			tabActive : function() { //标签页选择
				$('.tabone').each(function() {
					$(this).removeClass("order_tab_active");
					$(this).removeClass("order_tab");
					$(this).addClass("order_tab");
				});
				$(this).removeClass("order_tab");
				$(this).addClass("order_tab_active");

				$(".tabpageone").each(function() {
					$(this).removeClass("tab_page_active");
					$(this).removeClass("tab_page");
					$(this).addClass("tab_page");
				});
				var pageTo = $(this).attr("pageTo");
				$("#" + pageTo).removeClass("tab_page");
				$("#" + pageTo).addClass("tab_page_active");
				biz.event.iframeLoadData(pageTo.substr((pageTo.length - 1), 1));
			},
			iframeLoadData : function(activeIndex) {
				if(parseInt(activeIndex) >= 0 && parseInt(activeIndex) < 2) {
					activeIndex = parseInt(activeIndex) + 10;
				}
				var iframe = $("#tabPage" + activeIndex).children();
				if("2" == orderTypeNum) {
					if(dabanIframeUrl[parseInt(activeIndex) - 2] != $(iframe).attr("src")) {
						$(iframe).attr("src", dabanIframeUrl[parseInt(activeIndex) - 2]);
					}
				} else if("3" == orderTypeNum) {
					if(dahuoIframeUrl[parseInt(activeIndex) - 2] != $(iframe).attr("src")) {
						$(iframe).attr("src", dahuoIframeUrl[parseInt(activeIndex) - 2]);
					}
				}
				if(null != iframe && undefined != iframe) {
					iFrameHeight(iframe.attr("id"));
				}
			}
		}
	}
	return fn;
});

//iframe 自适应高度
function iFrameHeight(iframe) {
	var ifm = $("#" + iframe);
	setTimeout(function(){
		var height = parseInt($(ifm).contents().find("#iframeDiv").height());
		$(ifm).attr("height", height);
	}, 100);
}

//退回上个流程节点
function backOrder(iframe) {
	if(confirm("确认要退回上一步吗？")) {
		if(null != iframe && undefined != iframe && "" != iframe) {
			$("#" + iframe).contents().find("#processBtn").attr("disabled", "true");
			$("#" + iframe).contents().find("#saveBtn").attr("disabled", "true");
			$("#" + iframe).contents().find("#backBtn").attr("disabled", "true");
		}
		window.location.href = "/bx/backOrder?oaOrderDetail.id=" + $("#orderDetailId").val();
	}
}

//终止订单页面跳转
function toTerminateOrderDetail() {
	top.location.href='/bx/orderDetail?terminate=true&oaOrderDetail.id=' + $("#orderDetailId").val();
}