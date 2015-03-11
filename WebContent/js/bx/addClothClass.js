requirejs.config({
	baseUrl : "/js/pub/",
    paths: {
        "u": "util",
        "v": "validateAlert"
    }
});
var i = 0;
define(["u", "v"], function(u, v) {
    var __b = {
        init: function() {
            var me = this;
            $("#clothClass_0").click(function() {
                me.add();
            });
			$('#btn_next').on("click", me.next);
			$('#btn_update').on("click", me.update);
			$('.go_back').each(function() {
				$(this).on("click", me.goBack);
			});
        },
        add: function() {
        	var id = "clothClass_" + i;
        	i++;
        	var newId = "clothClass_" + i;
        	$("#" + id).after('<br id="' + newId + '_br" /><input id="' + newId + '_text" type="text" class="input_txt" style="margin-top: 12px; width:165px;" /><a id="' + newId + '" href="javascript:outInerb.del(' + i +')" class="z_del">&nbsp;&nbsp;X</a>');
        },
        del: function(key) {
        	for(var k = key; k < i; k++) {
        		$("#clothClass_" + k + "_text").val($("#clothClass_" + (k + 1) + "_text").val());
        	}
    		$("#clothClass_" + i).remove();
    		$("#clothClass_" + i + "_br").remove();
    		$("#clothClass_" + i + "_text").remove();
        	i--;
        },
		next : function() {
			$('.input_txt').each(function(index, obj) {
				var val = $.trim($(this).val());
				$(this).attr("name","oaDts[" + index + "].value");
			});
			$('#clothClass_form').submit();
		},
		update : function() {
			$('#clothClass_form').attr("action", "/bx/updateClothClass");
			$('#clothClass_form').submit();
		},
		goBack : function() {
			javascript: history.go(-1);
		}
    }
    return __b;
});