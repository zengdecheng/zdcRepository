/**
 * by fangwei 2014-12-24
 */
(function($){
	var opts;
	$.fn.autoFill = function (jsonData, opt) {
		opts = $.extend({
			formFill:false,				// 表单自动补全
			formFillName:'name',		// 表单填充参考值
			
			elementText:false,			// 元素填充
			elementFillName:'name',		// 元素填充参考字段
			
			debug: false,				// 错误显示		true | false
			elementName: 'none',		// 模板关键字是否为对象 object| none
			hasHidden:true,				// 是否填充type hidden
			addRow:false,				// 自动添加行
			addNum:0,					// 添加的数量	addRow:true时有效
			addPoint:'',				// 添加点		element id
			templet:'',					// 模板			addRow:true时有效
			isList:false,				// name是否list 是list则name[0],name[1]	addRow:true时有效
			elementsEvents: ['checkbox', 'radio', 'select-one']
		},opt||{});
		if(opts.addRow){
			addRow();
		}
		if(opts.elementText){
			elementFill($(this),jsonData);
		}
		if(opts.formFill){
			formFill($(this),jsonData);
		}
		return this;
	};
	function addRow(){
		for(i=0;i<opts.addNum;i++){
			var temp;
			if(opts.isList){
				temp = opts.templet.replace(/\[]/g,"["+i+"]").replace(/\{}/g,i);
			}
			$("#"+opts.addPoint).append(temp);
		}
	}
	
	function formFill($obj,jsonData){
		var hasHidden=":hidden";
		if(opts.hasHidden){
			hasHidden = "";
		}
		$obj.find("input:not(:reset,:submit,:button"+hasHidden+"),select,textarea").each(function(i, item){
			try {
				var objName;
				var arrayAtribute;
				try {
					if (opts.elementName == "object") {
						if ($(item).attr(opts.formFillName).match(/\[[0-9]*\]/i)) {
							objName = $(item).attr(opts.formFillName).replace(/^[a-z]*[0-9]*[a-z]*\./i, 'jsonData.').replace(/\[[0-9]*\].*/i, "");
							arrayAtribute = ($(item).attr(opts.formFillName).match(/\[[0-9]*\]\.[a-z0-9]*/i)+"").replace(/\[[0-9]*\]\./i, "");
						} else {
							objName = $(item).attr(opts.formFillName).replace(/^[a-z]*[0-9]*[a-z]*\./i, 'jsonData.');
							arrayAtribute = objName.substring(objName.lastIndexOf(".")+1);
						}
					} else if (opts.elementName == "none") {
						objName = 'jsonData.' + $(item).attr(opts.formFillName);
					}
					var value = eval(objName);
				} catch(e) {
					if (opts.debug) {
						debug(e.message);
					}
				}
				if (value != null || $(item)[0].type =="checkbox") {
					switch ($(item)[0].type) {
						case "hidden":
							if(opts.hasHidden){
								$(item).val(value);
							}
							break;
						case "password":
						case "textarea":
							$(item).val(value);
							break;
						case "text":
							$(item).val(value);
							break;
						case "select-one":
							if (value) {
								$(item).val(value);
							}
							break;
						case "radio":
							$(item).each(function (i, radio) {
								if ($(radio).val() == value) {
									$(radio).attr("checked", "checked");
								}
							});
							break;
						case "checkbox":
							value=eval(objName.substring(0,objName.lastIndexOf(".")));
							if(value!=null){
								if ($.isArray(value)) {
									$.each(value, function(i, arrayItem) {
										if (typeof(arrayItem) == 'object') {
											arrayItemValue = eval("arrayItem." + arrayAtribute);
										} else {
											arrayItemValue = arrayItem;
										}
										if ($(item).val() == arrayItemValue) {
											$(item).attr("checked", "checked");
										}
									}); 
								} else {
									if ($(item).val() == value) {
										$(item).attr("checked", "checked");
									}
								}						
							}
							break;
					}
					executeEvents(item);
				}
			} catch(e) {
				if (opts.debug) {
					debug(e.message);
				}
			}
		});
	}
	
	function elementFill($obj,jsonData){
		$.each($('*['+opts.elementFillName+']'),function(i,item){
			try {
				var objName;
				var arrayAtribute;
				try {
					if (opts.elementName == "object") {
						if ($(item).attr(opts.elementFillName).match(/\[[0-9]*\]/i)) {
							objName = $(item).attr(opts.elementFillName).replace(/^[a-z]*[0-9]*[a-z]*\./i, 'jsonData.').replace(/\[[0-9]*\].*/i, "");
							arrayAtribute = ($(item).attr(opts.elementFillName).match(/\[[0-9]*\]\.[a-z0-9]*/i)+"").replace(/\[[0-9]*\]\./i, "");
						} else {
							objName = $(item).attr(opts.elementFillName).replace(/^[a-z]*[0-9]*[a-z]*\./i, 'jsonData.');
							arrayAtribute = objName.substring(objName.lastIndexOf(".")+1);
						}
					} else if (opts.elementName == "none") {
						objName = 'jsonData.' + $(item).attr(opts.elementFillName);
					}
					var value;
					if("NaN"==(eval(objName)+"")){
						value="";
					}else{
						value= eval(objName);
					}
				} catch(e) {
					if (opts.debug) {
						debug(e.message);
					}
				}
				if (value != null) {
					$(item).text(value);
					executeEvents(item);
				}
			} catch(e) {
				if (opts.debug) {
					debug(e.message);
				}
			}
		});
	}
	
	function executeEvents(element) {
		if (jQuery.inArray($(element)[0].type, opts.elementsEvents)) {
			if ($(element).attr('onchange')) {
				$(element).change();
			}
			if ($(element).attr('onclick')) {
				$(element).click();
			}
		}	
	};
	
	function debug(message) {
        if (console && console.info) {
           console.info(message);
        }
    };
})(jQuery);