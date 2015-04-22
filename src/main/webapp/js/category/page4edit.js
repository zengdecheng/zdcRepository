requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
		"v" : "jqueryValidation/js/jquery.validationEngine.min",
		"vl" : "jqueryValidation/js/jquery.validationEngine-zh_CN"
	}
});
define([ "v", "vl" ], function(v, vl) {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var biz = {
		init : function() {
			biz.event.initValidation(); // 初始化验证框架
			$(".time_hid").each(function(i, n) {
				var temp = parseInt($(n).val()) * 100;
				if(isNaN(temp)) {
					temp = 0;
				}
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
				var tname = $(n).attr("tname");
				tname = tname.substr(0, tname.length - 4);
				$("#" + tname).val(temp);
			});
		},
		bind : function() {
			$("#saveBtn").on("click", biz.event.saveCategory); // 保存按钮绑定事件
		},
		event : {
			initValidation : function() {
				$("#categoryForm").validationEngine('attach', {
					scroll : true,
					autoHidePrompt : true,
					autoHideDelay : 1500,
					showOneMessage : true,
					fadeDuration : 0.3,
					promptPosition : "bottomLeft"
				});
			},
			saveCategory : function() { // 保存事件
				if ($("#categoryForm").validationEngine("validate")) {
					biz.event.setHidText();
				}
				$("#categoryForm").submit();
			},
			setHidText : function() { // 设置隐藏元素值
				$(".time_text").each(function(i, n) {
					var name = $(n).attr("tname");
					var temp = 0;
					if ("" != $(n).val()) {
						temp = parseFloat($(n).val()) * 1000 * 60 * 60;
					}
					$("#" + name + "_hid").val(temp);
				});
			}
		}
	};
	return fn;
});