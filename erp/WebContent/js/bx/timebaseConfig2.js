define(function() {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		},
		valiateForm : function() {
			var result = true;
			$('.input_txt').each(function() {
				var val = $.trim($(this).val());
				if (val == "") {
					$(this).val("0");
					val = 0;
				}
				var reg = "^\\d+(\\.\\d+)?$";
				var regExp = new RegExp(reg);
				if (!regExp.test(val)) {
					alert("输入格式不对");
					$(this).focus();
					result = false;
					return false;
				}
			});
			return result;
		}
	};
	var biz = {
		init : function() {
			$('.input_txt').each(function() {
				var duration = parseFloat($(this).val());
				$(this).val(biz.accDiv(duration, 1000 * 60 * 60));
			});
			$('#dur0').attr("readonly", "true");
			$("#calDur0").val("0");
			$('#dur0').addClass("notselect");
			var totalTime = parseFloat($("#totalTime").html());
			$("#totalTime").html(
					"总时长为：" + biz.accDiv(totalTime, 1000 * 60 * 60) + "小时");
			biz.event.calTotalTime();
		},
		bind : function() {
			$('#btn_next').on("click", biz.event.next);
			$('.go_back').each(function() {
				$(this).on("click", biz.event.goBack);
			});
			$('.input_txt').each(function() {
				$(this).on("blur", biz.event.calTotalTime);
			});

			$("#add").on("click", biz.event.confirmDivShowOrHide);
			$('.cancel1').each(function() {
				$(this).on("click", biz.event.confirmDivShowOrHide);
			});

			$("#add1").on("click", biz.event.addtr);
			$("#confirmBtn").on("click", biz.event.confirmSubmit);
		},
		event : {
			next : function() {
				if (!fn.valiateForm()) { // 验证不通过则return
					return false;
				}
				// 判断总的时间是否分配完或分配超出
				var oaTimebase_totalDuration = parseInt($(
						"#oaTimebase_totalDuration").val());
				var oaTimebaseEntry_totalDuration = 0;
				$('.input_txt:not(input[readonly="readonly"])').each(function() {
					var duration = parseFloat($(this).val());
					duration *= (1000 * 60 * 60); // 换算成毫秒
					oaTimebaseEntry_totalDuration += duration;
				});

				if (oaTimebase_totalDuration > oaTimebaseEntry_totalDuration) {
					oaTimebase_totalDuration = biz.accDiv(
							oaTimebase_totalDuration, 1000 * 60 * 60);
					alert("你设置的各节点的时间和小于你设置的总时间\r\n你设置的总时间为："
							+ oaTimebase_totalDuration + "小时");
					return;
				} else if (oaTimebase_totalDuration < oaTimebaseEntry_totalDuration) {
					oaTimebase_totalDuration = biz.accDiv(
							oaTimebase_totalDuration, 1000 * 60 * 60);
					alert("你设置的各节点的时间和大于你设置的总时间\r\n你设置的总时间为："
							+ oaTimebase_totalDuration + "小时");
					return;
				}

				var calDur = 0;
				for (var i = 0; i < $('.input_txt').length; i++) {
					var duration = parseFloat($("#dur" + i).val());
					duration *= (1000 * 60 * 60); // 换算成毫秒
					$("#dur" + i + "_1").val(duration);
					if (!$("#dur" + i).attr("readonly")) {
						$("#calDur" + i).val(calDur);
						$("#calDur" + i).change();
						calDur += duration; // 计算到当前节点总时间
					}
				}
				var oaTimebase_id = $("#oaTimebase_id").val();
				if (null != oaTimebase_id && "" != oaTimebase_id) {
					$('#oaConfig2_form').attr("action",
							"/bx/updateTimebaseConfig");
				}
				$('#oaConfig2_form').submit();
			},
			goBack : function() {
				javascript: history.go(-1);
			},
			calTotalTime : function() {
				var totalTime = 0;
				$('.input_txt:not(input[readonly="readonly"])').each(function() {
					var val = ("" != $(this).val()) ? $(this).val() : "0";
					totalTime = biz.accAdd(totalTime, parseFloat(val));
				});
				$("#setTime").html("当前设置的总时长为：" + totalTime + "小时");
			},
			confirmDivShowOrHide : function() {
				if ($("#confirmForm").is(":hidden")) {
					$("#confirmForm").show();
					$("#Form").show();
				} else {
					$("#confirmForm").hide();
					$("#Form").hide();
				}
			},
			addtr : function() {
				var tr = $("#table1 tr").eq(1).clone();
				tr.appendTo("#table1");
			},
			confirmSubmit : function() {
				for (var i = 1; i < $('.input_txt').length; i++) {
					$("#dur" + i).removeAttr("readonly");  //删除只读
					$("#dur" + i).unbind("change");  //解除绑定
					$("#calDur" + i).unbind("change");  //解除绑定
				}
				
				$("#table1 tr").not(".notselect").each(function() {
					var obj1 = $(this).find('select:first');
					var obj2 = $(this).find('select:last');
					var index1 = $(obj1).val();
					var index2 = $(obj2).val();
					if (("-1" != index1) && ("-1" != index2) && (index1 != index2)) {
						$("#dur" + index2).attr("readonly", "readonly");
						$("#dur" + index2).val($("#dur" + index1).val());
						$("#dur" + index1).on("change", {obj1:$("#dur" + index1), obj2:$("#dur" + index2)}, biz.event.durChange);
						$("#calDur" + index1).on("change", {obj1:$("#calDur" + index1), obj2:$("#calDur" + index2)}, biz.event.durChange);
					}
				});
				biz.event.calTotalTime();
				biz.event.confirmDivShowOrHide();
			},
			durChange : function(e) {
				e.data.obj2.val(e.data.obj1.val());
			}
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
		},
		accAdd : function(arg1, arg2) {
			var m;
			m = arg1 * 100 + arg2 * 100;
			return Math.round(m) / 100;
		}
	}
	return fn;
});
