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
				var temp = parseInt($(n).text()) * 100;
				temp = (temp / 1000 / 60 / 60);
				if (temp > 0) {
					temp = "" + temp;
					if ("0" == temp.substr(-1)) {
						temp = temp.substr(0, temp.length - 1);
						if ("0" == temp.substr(-1)) {
							temp = temp.substr(0, temp.length - 1);
						} else {
							temp = temp.substr(0, temp.length - 1) + "." + temp.substr(temp.length - 1, 1);
						}
					} else {
						temp = temp.substr(0, temp.length - 2) + "." + temp.substr(temp.length - 2, 2);
					}
				}
				$(n).text(temp + "小时");
			});
			$(".category_status").each(function(i, n) {
				if ("0" == $(n).text()) {
					$(n).text("禁用");
					$(n).css("color", "red");
				} else if ("1" == $(n).text()) {
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