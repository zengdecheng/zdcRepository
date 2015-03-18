requirejs.config({});
define(function() {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var biz = {
		init : function() {
			$(".category_cyc").each(function(i, n) {
				var temp = parseInt($(n).text());
				temp = temp / 1000 / 60 / 60;
				$(n).text(temp + "小时");
			});
			$(".category_status").each(function(i, n) {
				if("0" == $(n).text()) {
					$(n).text("禁用");
					$(n).css("color", "red");
				} else if("1" == $(n).text()) {
					$(n).text("激活");
					$(n).css("color", "green");
				} else {
					$(n).text("未知");
				}
			});
		},
		bind : function() {
			$("#addCategory").on("click", biz.event.addCategoryUrl);
		},
		event : {
			addCategoryUrl : function() {
				window.location.href = "page4add";
			}
		}
	};
	return fn;
});