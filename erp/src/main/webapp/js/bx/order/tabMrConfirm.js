requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
		"u" : "util",
		"up" : "uploadPreview.min"
	}
});
var sizeDetailTr,sizeTitle,width,detailUpdateFlag = false,picCount = 0,materialDescTr="",upImgHeight = 0;
var wfStepIndex; //订单节点index
define([ "u","up"], function(u,up) {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var biz = {
		init : function() {
			biz.event.initOrderData();
			biz.event.jsonGetDaBanSixNode();
			biz.event.setInputDisable();
			parent.iFrameHeight("iframeMrConfirm");
		},
		bind : function() {
			$("input[name='oaMrConfirm.ifQualified']").on("click", biz.event.ifQualifiedClick); //是否合格点击事件
			$("input[name='oaMrConfirm.ifRepeat']").on("click", biz.event.ifRepeatClick); //是否合格点击事件
            $("input[name='oaMrConfirm.ifDahuo']").on("click", biz.event.ifDahuoClick);//是否大货
			$("#processBtn").on("click", biz.event.processOrder); //保存并提交
			$(".download").on("click", biz.event.download); //下载文件
			$("#terminateBtn").on("click", biz.event.toTerminateOrderDetail); //跳转到终止订单
		},
		event : {
			initOrderData : function() { // 初始化表单数据，从父窗口中获取
				//默认初始值
				$("#orderId").val($(window.parent.document).find("#orderId").val());
				//判断是否可以编辑
				var orderDetail = $(window.parent.document).find("#orderDetail").val(); //是否是查看订单详情
				wfStepIndex = $(window.parent.document).find("#wfStepIndex").val(); //订单节点index
				if("true" != orderDetail && "6" == wfStepIndex) { //判断是否可编辑
					detailUpdateFlag = true;
				}
			},
			ifQualifiedClick : function() { //是否合格点击事件
				if("1" == $("input[name='oaMrConfirm.ifQualified']:checked").val()) {
					$("#unqualifiedReason").removeAttr("readonly", "true");
				} else {
					$("#unqualifiedReason").val("");
					$("#unqualifiedReason").attr("readonly", "true");
				}
			},
            ifDahuoClick : function(){
                if("1" == $("input[name='oaMrConfirm.ifDahuo']:checked").val()) {
                    $("input[name='oaMrConfirm.nodahuoReason']").removeAttr("readonly", true);
                    $("input[name='oaMrConfirm.nodahuoReason']").removeAttr("disabled", true);
                    $("#noDahuoOthReason").removeAttr("readonly", true);
                } else {
                    $("#noDahuoOthReason").val("");
                    $("input[name='oaMrConfirm.nodahuoReason']").attr("checked", false);
                    $("input[name='oaMrConfirm.nodahuoReason']").attr("disabled", true);
                    $("#noDahuoOthReason").attr("readonly", true);
                }
            },
            ifRepeatClick :function(){
                // 0不需要 1 需要
                if("0" == $("input[name='oaMrConfirm.ifRepeat']:checked").val()) {
                    //不复版则不选择是否生产大货
                } else {
                    $("input[name='oaMrConfirm.ifDahuo'][value ='1']").attr("checked",true);//复版则不生产大货
                    biz.event.ifDahuoClick();
                }
            },
			jsonGetDaBanSixNode : function() { // 获取打版第六个节点页面信息
				$.ajax({
                    url: "/bx/jsonGetDaBanSixNode?orderId=" + $(window.parent.document).find("#orderId").val() + "&wfStepIndex=" + wfStepIndex,
                    type: "post",
                    async: false,
                    success: function(data) {
                    	if("ajaxLogin" == data){
                			alert("登录超时，请重新登录");
                		} else if("0" ==  data.code){
                			biz.event.setMrConfirmInfo(data.oaMrConfirm); //设置MR确认信息
                			biz.event.setManageInfoData(data.oaOrderDetail); //设置管理信息
                		} else {
                			alert(data.msg);
                		}
                    }
                });
			},
			setMrConfirmInfo : function(oaMrConfirm) { //设置MR确认信息
				if(undefined != oaMrConfirm && null != oaMrConfirm) {
					if("1" == oaMrConfirm.ifRepeat) {
						$("input[name='oaMrConfirm.ifRepeat'][value='1']").attr("checked", true);
					} else {
						$("input[name='oaMrConfirm.ifRepeat'][value='0']").attr("checked", true);
					}
					if("1" == oaMrConfirm.ifQualified) {
						$("input[name='oaMrConfirm.ifQualified'][value='1']").attr("checked", true);
					} else {
						$("input[name='oaMrConfirm.ifQualified'][value='0']").attr("checked", true);
						$("#unqualifiedReason").attr("readonly", "true");
					}
                    if("1" == oaMrConfirm.ifDahuo){
                        $("input[name='oaMrConfirm.ifDahuo'][value='1']").attr("checked", true);
                    }else {
                        $("input[name='oaMrConfirm.ifDahuo'][value='0']").attr("checked", true);
                        $("#noDahuoOthReason").attr("readonly", "true");

                        if (undefined != oaMrConfirm.nodahuoReason && null != oaMrConfirm.nodahuoReason) {
                            var str = oaMrConfirm.nodahuoReason.split(", ");
                            $(str).each(function (index) {
                                $("input[name='oaMrConfirm.nodahuoReason'][value='" + str[index] + "']").attr("checked", true);
                            })
                        }
                        $("#noDahuoOthReason").val(oaMrConfirm.nodahuoOthreason);
                        $("#unqualifiedReason").val(oaMrConfirm.unqualifiedReason);
                        $("#mrConfirmId").val(oaMrConfirm.id);
                    }
				} else {
					$("input[name='oaMrConfirm.ifRepeat'][value='0']").attr("checked", true); //是否需要复版，默认为需要
					$("input[name='oaMrConfirm.ifQualified'][value='0']").attr("checked", true); //是否合格，默认为合格
					$("input[name='oaMrConfirm.ifDahuo'][value='1']").attr("checked", true); //是否生产大货，默认为合格
					$("#unqualifiedReason").attr("readonly", "true"); //不合格原因默认不可编辑
				}
			},
			setManageInfoData : function(orderDetail) { //设置管理信息
				if(undefined != orderDetail && null != orderDetail) {
					$("#detailOperator").text(orderDetail.operator);
					$("#detailDate").text(orderDetail.processTime);
					$("#detailContent").text(orderDetail.content);
					$("#detailAttachment").text(orderDetail.other_file);
					$("#wfStep").val(orderDetail.wf_step);
					//进度信息超时
					if(6 <= parseInt(wfStepIndex)) {
						if(undefined == orderDetail.wf_real_finish || null == orderDetail.wf_real_finish || "" == orderDetail.wf_real_finish) {
							orderDetail.schedule = "进行中";
						} else {
							orderDetail.schedule = orderDetail.schedule.replace("剩余", "提前");
						}
					}

					if(undefined != orderDetail.schedule && null != orderDetail.schedule) {
						if(orderDetail.schedule.indexOf("超时") >= 0) {
							$("#detailSchedule").css("background-color", "red");
							$("#detailSchedule").css("color", "white");
						} else if(orderDetail.schedule.indexOf("提前") >= 0) {
							$("#detailSchedule").css("background-color", "green");
						}
						$("#detailSchedule").text(orderDetail.schedule);
					}
					if(undefined != orderDetail.wf_real_start && null != orderDetail.wf_real_start) {
						$("#detailRealStart").text("流入时间:" + orderDetail.wf_real_start);
					}if(undefined != orderDetail.wf_real_finish && null != orderDetail.wf_real_finish) {
						$("#detailRealFinish").text("实际完成:" + orderDetail.wf_real_finish);
					}
					if(undefined != orderDetail.step_start_time_consume && null != orderDetail.step_start_time_consume && "" != orderDetail.step_start_time_consume) {
						var rate=parseFloat(orderDetail.step_start_time_consume);
						if( rate < 0 ) {
							$("#detailRealStartColor").css("background-color","#0000FF");
						}else if(rate <= 33){
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
						if( rate < 0 ) {
							$("#detailRealFinishColor").css("background-color","#0000FF");
						}else if(rate <= 33){
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
					}if(undefined != orderDetail.real_time && null != orderDetail.real_time) {
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
			processOrder : function() { //保存并提交订单
				$("#processOrder").val("true"); //true标识流程流转
				biz.event.jsonProcessOrder();
			},
			jsonProcessOrder : function() { //ajax提交订单
				//提交前，判断不合格是否勾选，如勾选则判断不合格原因  （为必填）
				var fleg=true;
				if("1"==$("input[name='oaMrConfirm.ifQualified']:checked").val()){
					var reason=$("#unqualifiedReason").val();
					if(undefined==reason || ""==reason || null==reason ){
						fleg=false;
						alert("请填写不合格原因！");
						$("#unqualifiedReason").focus();
					}
				}
                //判断是否选择大货生产
                if(undefined == $("input[name='oaMrConfirm.ifDahuo']").val() || null == $("input[name='oaMrConfirm.ifDahuo']").val() || "" == $("input[name='oaMrConfirm.ifDahuo']").val()){
                    alert("请选择是否大货生产！");
                    $("input[name= 'oaMrConfirm.ifDahuo']").focus();
                }
				if(fleg){
					$("#processBtn").attr("disabled", "true");
					$("#saveBtn").attr("disabled", "true");
					$("#backBtn").attr("disabled", "true");
					$("#terminateBtn").attr("disabled", "true");
					var params = $("#tabMrConfirm").serializeArray();
					$.ajax({
						url: "/bx/jsonProcessOrder",
		                type: "post",
		                data: params,
		                success: function(data) {
		                	if("ajaxLogin" == data){
	                			alert("登录超时，请重新登录");
	                		} else if("0" ==  data.code){
                                if( "true" == $("#processOrder").val() ){
                                    top.location.href = "/bx/todo";
                                }else {
                                    top.location.reload(true);
                                }
		                	} else {
		                		alert(data.msg);
		        				$("#processBtn").removeAttr("disabled");
		        				$("#saveBtn").removeAttr("disabled");
		        				$("#backBtn").removeAttr("disabled");
		        				$("#terminateBtn").removeAttr("disabled");
		                	}
		                },
		                error: function() {
		        			$("#processBtn").removeAttr("disabled");
		        			$("#saveBtn").removeAttr("disabled");
		        			$("#backBtn").removeAttr("disabled");
	        				$("#terminateBtn").removeAttr("disabled");
		                }
		            });
				}
			},
			setInputDisable : function() { //如果为查看详情，更改text属性为disable
				if(!detailUpdateFlag) {
					$("input").attr("readonly", "true");
					$("input[type=radio]").attr("disabled", "true");
                    $("input[type=checkbox]").attr("disabled", "true");
					$("textarea").attr("readonly", "true");
					$("#otherFileUploadTd").text("");
					$("#processOrderDive").hide();
					$("#orderDetailDive").show();
					if($("#org").val()=='周颠' && $(window.parent.document).find("#status").val()!='1' && "finish_999"!=$(window.parent.document).find("#wfStep").val()){//这里只有mR主管可以进行中止订单的修改
						$("#terminateBtn1").show();
					}
					parent.iFrameHeight("iframeMrConfirm");
				}
			},
			download : function() { //下载文件
				window.location.href = "/oaOrderFileDownload?fileUrl=" + encodeURI($(this).attr("downloadUrl"));
			},
			toTerminateOrderDetail: function() { //终止订单
				$("#processBtn").attr("disabled", "true");
				$("#saveBtn").attr("disabled", "true");
				$("#backBtn").attr("disabled", "true");
				$("#terminateBtn").attr("disabled", "true");
				parent.toTerminateOrderDetail();
			}
		}
	}
	return fn;
});

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
		var html = "<a href='" + json.url + "'>" + json.fileName+ "</a>";
		$("#otherFile").html(json.fileName);
	}
}

function showMessage(data) {
	alert(data);
}