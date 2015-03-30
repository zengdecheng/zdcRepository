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
					var name = $(n).attr("tName");
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