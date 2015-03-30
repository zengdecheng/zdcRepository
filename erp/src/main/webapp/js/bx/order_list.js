requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
		"f": "jquery.form",
		"p": "paging"
	}
});
define(["p","f"], function(p,f) {
	var fn = {
		init : function(){
			biz.init();
			biz.bind();
			p.bind(biz.event.list);
		}
	};
	var biz = {
		init : function(){
			//初始化超时和剩余的时间样式
			$(".z_title_sty3").each(function(i,n){
				if($(n).text().indexOf("剩余") > -1){
					$(n).addClass("z_title_sty4");
				}
			});
			
			//初始化select
			$("select","#form_search_div").each(function(i,n){
				var str = $("[name='hid."+$(n).attr("name")+"']").val();
				if(str != ""){
					$(n).val(str);
				}
				
			});
			
		},
		bind : function(){
			$("#search_btn").on("click",biz.event.search);
			$("#search_btn_ot").on("click",biz.event.search_ot);//超时订单查询
		},
		event : {
			search : function(){
				biz.event.list();
			},
			//超时订单查询
			search_ot : function(){
				window.location.href = "/bx/order_list?fsp.map.outtime=1";
			},
			
			list : function(){
				var pageNo = arguments[0] || 1;
				 $("select","#form_search_div").each(function(i,n){
					   if($(n).val() == '999'){
						   $(n).addClass("notselect");
					   }
				  });
				var queryString = $("input,select","#form_search_div").not(".notselect").fieldSerialize();
				window.location.href = "/bx/order_list?fsp.pageNo=" +pageNo+"&"+queryString;
			}
		}
	}
	return fn;
});