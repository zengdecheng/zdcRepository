requirejs.config({
	baseUrl : "/js/pub/",
    paths: {
        "u": "util",
    }
});
var _this;
define(["u"], function(u) {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var biz = {
		init : function() {
			// 初始化超时和剩余的时间样式
			$(".z_bg1").each(function(i, n) {
				if ($(n).text().indexOf("剩余") > -1) {
					$(n).addClass("z_bg2");
				}
			});
			if ("" == $.trim($("#attachment_name1").html())) {
				$("#attachment_name").html("暂无文件");
			}
			if ("" == $.trim($("#img_pic_url1").html())) {
				$("#img_pic_url").html("暂无图片");
			}
		},
		bind : function() {
			$('.download').each(function() {
				var is_this = $(this)
				$(this).on("click", {obj:is_this}, biz.event.download);
			});
			
			$('.go_back').each(function() {
				$(this).on("click", biz.event.goBack);
			});
			
			$("#submitBtn1").unbind("click");
			$("#submitBtn1").on("click", biz.event.confirmOrder);
			$('.cancel1').each(function() {
				$(this).unbind("click");
				$(this).on("click", biz.event.confirmDivShowOrHide);
			});
			
			$("#submitBtn2").unbind("click");
			$("#submitBtn2").on("click", biz.event.backOrder);
			$('.cancel1').each(function() {
				$(this).unbind("click");
				$(this).on("click", biz.event.confirmDivShowOrHide);
			});
			
			$("#viewPic").unbind("click");
			$("#viewPic").on("click", biz.event.picFormShowOrHide);
			$('.cancel2').each(function() {
				$(this).unbind("click");
				$(this).on("click", biz.event.picFormShowOrHide);
			});

			$("#submitBtn4").on("click", biz.event.jsonGetStaff);
			$("#submitBtn5").on("click", biz.event.finishOrder);
			$('.cancel').each(function() {
				$(this).on("click", biz.event.cancel);
			});
			
			$("#assignConfirmBtn").on("click" , biz.event.jsonAssignOrder);
		},
		event : {
			download : function(e) {
				window.location.href = "/oaOrderFileDownload?fileUrl=" + e.data.obj.attr("downloadUrl");
			},
			goBack : function() {
				javascript:history.go(-1);
			},
			confirmOrder : function() {
				$("#confirmBtn").unbind("click");
				$("#confirmBtn").on("click",biz.event.confirmSubmit);
				$("#confirmContent").html("确认流转到下一步？");
				biz.event.confirmDivShowOrHide();
			},
			confirmSubmit : function() {
				if ($.trim($("#hid_attachment_url").val()) == "") {
					$("#hid_attachment_url").val($("#top_attachment").val());
				}
				if ($.trim($("#hid_pic_url").val()) == "") {
					$("#hid_pic_url").val($("#top_pic").val());
				}
				biz.event.confirmDivShowOrHide();
				$("#form1").submit();
			},
			backOrder : function() {
				var isBack = $.trim($("#submitBtn2").attr("isBack"));
				if ("1" != isBack) {
					alert("流程节点第二步不允许退回");
					return;
				}
				$("#confirmBtn").unbind("click");
				$("#confirmBtn").on("click",biz.event.backSubmit);
				$("#confirmContent").html("确认退回到上一步？");
				biz.event.confirmDivShowOrHide();
			},
			backSubmit : function() {
				biz.event.confirmDivShowOrHide();
				$("#form1").attr("action","/bx/backOrder");
				$("#form1").submit();
			},
			confirmDivShowOrHide : function() {
				if($("#confirmForm").is(":hidden")) {
					$("#confirmForm").show();
					$("#Form").show();
				} else {
					$("#confirmForm").hide();
					$("#Form").hide();	
				}
			},
			picFormShowOrHide : function() {
				if($("#picForm").is(":hidden")) {
					$("#picForm").show();
					$("#Form").show();
				} else {
					$("#picForm").hide();
					$("#Form").hide();	
				}
			},
			jsonGetStaff : function() {
				var ajaxUrl = "/bx/jsonGetStaff";
				
	            $.ajax({
	                type: "post",
	                url: ajaxUrl,
	                success: function(data) {
	                	if (data == "authorityError") {
	                		alert("你不是管理员，没有权限分配订单");
	                		return false;
	                	} else {
		                    var obj = jQuery.parseJSON(data);
		    				if (obj.length == 0) {
		    					alert("你没有人员可以进行分配");
		    					return;
		    				}
		                    biz.event.loadData(obj);
		    				$("#staffListForm").show();
		    				$("#Form").show();
	                	}
	                }
	            });
			},
			loadData : function(data) {
				$('#operatorList li').remove();
				$.each(data, function(i, n) {
					$('#operatorList').append(u.format("<li><input type='radio' name='operator' class='z_radio1' value='" + data[i].id + "' /><label>" + data[i].login_name + "</label></li>"));
	            });
				$("input[name=operator]:eq(0)").attr("checked",'checked');
			},
			cancel : function() {
				$("#staffListForm").hide();
				$("#Form").hide();
			},
			jsonAssignOrder : function() {
				var ajaxUrl = "/bx/jsonAssignOrder";
	            ajaxUrl += "?oaOrderDetail.id=" + $("#oaOrderDetailId").val() + "&oaStaffId=" + $('input[name="operator"]:checked').val();
	            
	            $.ajax({
	                type: "post",
	                url: ajaxUrl,
	                success: function(data) {
	                	if (data == "ok") {
	                		alert("分配成功");
	                		window.location.href = "/bx/todo";
	                	} else if(data == "oaStaffError") {
	                		alert("你所分配的人员不存在");
	                	} else {
	                		alert("订单出错");
	                	}
                		biz.event.cancel();
	                }
	            });
			},
			finishOrder : function() {
				$("#confirmBtn").unbind("click");
				$("#confirmBtn").on("click",biz.event.finishSubmit);
				$("#confirmContent").html("确认要作废订单？");
				biz.event.confirmDivShowOrHide();
			},
			finishSubmit : function() {
				biz.event.confirmDivShowOrHide();
				window.location.href = "/bx/orderFinish?oaOrder.id=" + $("#oa_order").val();
			}
		}
	}
	return fn;
});

//图片上传回调函数
function uploadComplete(data) {
	data = data.replace('<meta http-equiv="content-type" content="text/html; charset=UTF-8">',"");
	if (data === "error") {
		alert("上传失败！");
		return;
	}
	if (data === "empty") {
		alert("上传文件为空！");
		return;
	}
	var json = $.parseJSON(data);
	var fileType = json.fileType;
	if (fileType == "file") {
		$("#hid_attachment_url").val(json.url);
		var html = "<a id='attachment_name1' href='javascript:void(0)' class='z_title_sty1 download'";
		html += "downloadUrl='" + json.url + "'>" + json.attachment_name + "</a>";
		$("#attachment_name").html(html);
		outInerb.init();
	} else if (fileType == "pic") {
		$("#hid_pic_url").val(json.url);
		var html = "<a id='img_pic_url1' href='javascript:void(0)' class='z_title_sty1 download'";
		html += "downloadUrl='" + json.url + "'>" + json.attachment_name + "</a>";
		$("#img_pic_url").html(html);
		outInerb.init();
//		$("#img_pic_url").attr("src", json.url);
	}
}