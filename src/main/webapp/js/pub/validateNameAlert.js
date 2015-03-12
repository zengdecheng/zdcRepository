define([], function() {
	var valaliEles=[];
	var optionsPub;
	validate = {
			 //得到配置的名称
			 getValidatorConfig: function(config) {
              return config;
				
			},

	        /**初始化方法 */
	        init: function(options) {
	            options = $.extend({
	                buttonId: "",
	                formId: "form1",
	                errorClass: "inputError",
	                focusClass: "inputFocus",
	                normalClass: "inputNormal",
	                callback: null,
	                config:null,
	                errors: {}
	            }, options);
	            optionsPub = options;
	            if (!validate.getDom(options.formId)) return;
	            var elems = validate.getDom(options.formId).elements,
	                me = this, id,
	                config = options.config,
	                rule = config.Rule;
	            options.config = config;
	            options.elems = elems;
	            $.each(elems, function() {
	                name = $(this).attr("name");
	                if (rule.hasOwnProperty(name)) {
	                    $(this).focus(function() {
	                        $("." + options.focusClass, $(options.formid))
	                        .removeClass(options.focusClass);
	                        $(this).addClass(options.focusClass)
	                        .removeClass(options.errorClass);
	                    }).blur(function() {
	                        //me.validate($(this), options);
	                        $(this).removeClass(options.focusClass)
	                    });
	                    valaliEles.push(this);
	                }
	            });
	            $("#" + options.buttonId).click(function() {
	                var isValidate = true;
	                $.each(valaliEles, function() {
	                    if (!me.validate($(this), options)) {
	                    	isValidate = false;
	                    	return false;
	                    }
	                });
	                if (isValidate) {
	                    options.callback && options.callback();
	                    //admincb && admincb();
	                    return true;
	                }
	                return false;
	            });
	        },
	        validateForm: function(){
	        	 var me = this;
	        	 var isValidate = true;
	                $.each(valaliEles, function() {
	                    if (!me.validate($(this), optionsPub)) {
	                    	isValidate = false;
	                    	return false;
	                    }
	                });
	                if (isValidate) {
	                	optionsPub.callback && optionsPub.callback();
	                    //admincb && admincb();
	                    return true;
	                }
	                return false;
	        },
	        getLength: function(str) {
	            var len = 0;
	            for (var i = 0; i < str.length; i++) {
	                if (str.charCodeAt(i) >= 0x4e00 && str.charCodeAt(i) <= 0x9fa5) {
	                    len += 1;
	                } else {
	                    len++;
	                }
	            }
	            return len;
	        },
	        showErrorMsg: function(options) {
	            var config = options.config,
	                messageContainer = config.messageContainer,
	                msg = [],
	                messageContainer = $("#" + messageContainer);
	            for (var i = 0, len = options.elems.length; i < len; i++) {
	                var id = $(options.elems[i]).attr("name");
	                if (options.errors[id] && options.errors[id] !== undefined) {
	                   // msg += "<p>" + options.errors[id] + "</p>";
	                	alert(options.errors[id]);
	                	$(options.elems[i]).focus();
	                	break;
	                }
	            }
	            /*if (msg != "") {
	                messageContainer.html(msg).show();
	            	//alert(msg);
	            } else {
	                messageContainer.html("").hide();
	            }*/
	        },
	        addError: function(obj, msgType, options) {
	            var config = options.config,
	                id = obj.attr("name"),
	                rule = config.Rule;
	            if (rule[id][msgType]) {
	                options.errors[id] = rule[id][msgType]
	            }
	           
	            //obj.addClass(options.errorClass);
	            this.showErrorMsg(options);
	        },
	        /***验证的方法*/
	        validate: function(obj, options) {
	            var me = this,
	                config = options.config,
	                id = obj.attr("name"),
	                rule = config.Rule[id],
	                reg = rule["reg"],
	                value = $.trim(obj.val()),
	                empty = rule["empty"],
	                min = rule["min"],
	                max = rule["max"],
	                compare = rule["compare"],
	                def = rule["def"],
	                isValidate = true;
	            if (!empty || (empty && value != '')) {
	                if (def && def === value) {
	                    me.addError(obj, "minerror", options);
	                    return false;
	                }
	                if (min && me.getLength(value) < min) {
	                    me.addError(obj, "minerror", options);
	                    return false;
	                }
	                if (reg && reg != "username") {
	                	if(typeof reg == 'string'){
	                		reg = new RegExp(validate.regexEnum[reg]);
		                    if (!reg.test(value)) {
		                        me.addError(obj, "error", options);
		                        return false;
		                    }
	                	}else{
	                		if (!reg.test(value)) {
		                        me.addError(obj, "error", options);
		                        return false;
		                    }
	                	}
	                }
	                if (max && me.getLength(value) > max) {
	                    me.addError(obj, "maxerror", options);
	                    return false;
	                }
	                if (compare) {
	                    if (value != $.trim($("#" + compare).val())) {
	                        me.addError(obj, "compareerror", options);
	                        return false;
	                    }
	                }
	                delete options.errors[id];
	                //me.showErrorMsg(options);
	                //用style不用
	                obj.addClass(options.normalClass).removeClass(options.errorClass);
	            }
	            return true;
	        },
	        getDom: function(dom) {
	            return typeof dom == 'string' ? document.getElementById(dom) : dom;
	        },
	        regexEnum: {
	            intege: "^-?[1-9]\\d*$",
	            intege1: "^[0-9]\\d*$",
	            telNo: "^[0-9]\\d*$",
	            float:"^([　\s]*)|(^\d*\.{0,1}\d+)$",
	            email: "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$",
	            mobile: "^(13|15|18)[0-9]{9}$",
	            mobileOrtel: "(^(13|15|18)[0-9]{9}$)|(^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$)",
	            tel: "^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$",
	            url: "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$",
	            zipcode: "^\\d{6}$",
	            username: "^\\w+$",
	            qq: "^[1-9]*[1-9][0-9]*$",
	            styleCode:"^[a-z|A-Z|0-9]+$",
	            assCode:"^[0-9]+$|^$",
	        }
	    }
	return validate;
});