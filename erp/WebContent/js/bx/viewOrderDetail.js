define(function() {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		},
		attachmentFormShowOrHide : function(id) {
			if ($("#" + id).is(":hidden")) {
				$("#" + id).show();
				$("#Form").show();
			} else {
				$("#" + id).hide();
				$("#Form").hide();
			}
		}
	};
	var biz = {
		init : function() {
			// 初始化超时和剩余的时间样式
			$(".z_order_bg").each(function(i, n) {
				if ($(n).text().indexOf("剩余") > -1) {
					$(n).addClass("z_order_bg1");
				} else if ($(n).text().indexOf("正在处理") > -1) {
					$(n).removeClass("z_order_bg");
				}
			});
		},
		bind : function() {
			$('.go_back').each(function() {
				$(this).on("click", biz.event.goBack);
			});
			
			$('.download').each(function() {
				var is_this = $(this)
				$(this).on("click", {obj:is_this}, biz.event.download);
			});
		},
		event : {
			goBack : function() {
				javascript: history.go(-1);
			},
			download : function(e) {
				window.location.href = "/oaOrderFileDownload?fileUrl=" + encodeURI(e.data.obj.attr("downloadUrl"));
			}
		}
	}
	return fn;
});
