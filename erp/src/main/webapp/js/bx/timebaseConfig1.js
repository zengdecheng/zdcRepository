define(function() {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var biz = {
		init : function() {
			biz.event.setProcess();
			var totalDuration = ("" != $('#totalDuration').val()) ? parseInt($('#totalDuration').val()) : 0;
			if (totalDuration != 0) {
				$('#totalDuration').val(biz.event.accDiv(totalDuration, 1000 * 60 * 60));
			}
		},
		bind : function() {
			$('#process').on("change", biz.event.setProcess);
			$('#btn_next').on("click", biz.event.next);
			$('.go_back').each(function() {
				$(this).on("click", biz.event.goBack);
			});
		},
		event : {
			setProcess : function() {
				$('#process_id').val(
						$('#process').children('option:selected').attr(
								"process_id"));
				$('#process_key').val(
						$('#process').children('option:selected').attr(
								"process_key"));
			},
			next : function() {
				var name = $.trim($("#name").val());
				if (name == "") {
					alert("名称不能为空");
					$("#name").focus();
					return false;
				}
				if (name.length > 50) {
					alert("名称不能大于50个字符");
					$("#name").focus();
					return false;
				}
				var totalDuration = $.trim($("#totalDuration").val());
				if (totalDuration == "") {
					alert("总时间不能为空");
					$("#totalDuration").focus();
					return false;
				}
				if (totalDuration.length > 12) {
					alert("总时间不能不能大于12位");
					$("#totalDuration").focus();
					return false;
				}
				var regExp = new RegExp("^\\d+(\\.\\d+)?$");
				if (!regExp.test(totalDuration)) {
					alert("总时间输入格式不对");
					$("#totalDuration").focus();
					return false;
				}
				$("#name").val(name);
				$("#totalDuration").val(totalDuration);
				var totalHour = parseFloat($('#totalDuration').val());
				$('#totalDuration_1').val(totalHour * 60 * 60 * 1000);
				$('#oaConfig1_form').submit();
			},
			goBack : function() {
				javascript: history.go(-1);
			},
			accDiv : function(arg1, arg2) {
				var t1 = 0, t2 = 0, r1, r2;
				try {
					t1 = (1 * arg1).toString().split(".")[1].length;
				} catch (e) {
				}
				try {
					t2 = (1 * arg2).toString().split(".")[1].length;
				} catch (e) {
				}
				with (Math) {
					r1 = Number((1 * arg1).toString().replace(".", ""));
					r2 = Number((1 * arg2).toString().replace(".", ""));
					var ss = (r1 / r2) * pow(10, t2 - t1);
					return Math.round(ss * 100) / 100;
				}
			}
		}
	}
	return fn;
});
