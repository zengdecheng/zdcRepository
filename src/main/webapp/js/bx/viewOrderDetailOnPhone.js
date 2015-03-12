define(function() {
	var fn = {
		init : function() {
			biz.init();
		}
	};
	var biz = {
		init : function() {
			$(".z_order_bg").each(function(i, n) {
				if ($(n).text().indexOf("剩余") > -1) {
					$(n).addClass("z_order_bg1");
				} else if ($(n).text().indexOf("正在处理") > -1) {
					$(n).removeClass("z_order_bg");
				}
			});
		}
	}
	return fn;
});
