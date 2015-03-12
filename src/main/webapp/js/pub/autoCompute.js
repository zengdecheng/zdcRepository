/**
 * by lanyu 2014-12-31
 */
(function($){
	var opts;
	$.fn.autoCompute = function (opt) {
		opts = $.extend({
			paramsType:"id",//元素属性类型 默认是Id [class]
			valType: "val",//获取元素内容的方式 默认是val [html]
			params:'',//要计算的值[aaa,bbb,ccc]
			process:'',//计算的公式是什么{a+b/c} [0]+[1]+[2]
			outParamsType:"id",//输出元素属性类型 默认为Id [class]
			outValType:"val",//输出元素内容的方式 默认为val [class]
			outPoint:''//输出计算值的位置
		},opt||{});
		//先将公式中的[] 进行遍历 和替换
		
		processReplace(this);
		/*
		if(""==outPoint){
			processReplace(this);
		}else{
			processReplace($(outPoint));
		}
		return this;*/
	};
	function processReplace($e){
		var tamp=opts.process+"";
		tamp=(tamp.replace(/\[/g,"parseFloat($(opts.params["));
		
		if("val"==opts.outValType){
			tamp=tamp.replace(/\]/g,"]).val())");
			if(!isNaN(eval(tamp))){
				$e.val(eval(tamp).toFixed(2));
			}else{
				var val1=tamp.substring(0,35);
				var val2=tamp.substring(36, 71);
				var val3=tamp.substring(72, 107);
				if(isNaN(eval(val1))){
//					val1=0;
					tamp=tamp.replace(val1, 0);
				}
				if(isNaN(eval(val2))){
//					val2=0;
					tamp=tamp.replace(val2, 0);
				}
				if(isNaN(eval(val3))){
//					val3=0;
					tamp=tamp.replace(val3, 0);
				}
//				$e.val((eval(val1)+eval(val2)+eval(val3)).toFixed(2));
				$e.val(eval(tamp).toFixed(2));
			}
		}else if("html"==opts.outValType){
			tamp=tamp.replace(/\]/g,"]).html())");
			if(!isNaN(eval(tamp))){
				$e.html(eval(tamp).toFixed(2));
			}else{
				$e.html("");
			}
		}
		
	}
})(jQuery);
