requirejs.config({
	
});
define(function(){
	var fn={
			init:function(){
				biz.bind();
			}
	};
	var biz={
		bind:function(){
			$("#addCategory").on("click",biz.event.addCategoryUrl);
		},
		event:{
			addCategoryUrl:function(){
				window.location.href="page4add";
			}
		}
	};
	return fn;
});