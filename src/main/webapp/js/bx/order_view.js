requirejs.config({
	//baseUrl : "/js/pub/",
	paths : {
		"u" : "util",
		"v" : "validateAlert",
		"form" : "jquery.form"
	}
});

define([ "u","v","form"],function(u,v,f) { 
	var order_view = {
		bind:function(){
			alert(1111);
		},
		createBizOrder : function (){
			$("#create_order_form").submit();
		}
	}
	return order_view;
});