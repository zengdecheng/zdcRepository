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
			$("#export").bind(biz.event.downCSV);
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
		},
		event : {
			search : function(){
				biz.event.list();
			},
			list : function(){
				var pageNo = arguments[0] || 1;
				 $("select","#form_search_div").each(function(i,n){
					   if($(n).val() == '999'){
						   $(n).addClass("notselect");
					   }
				  });
				var queryString = $("input,select","#form_search_div").not(".notselect").fieldSerialize();
				window.location.href = "/bx/getDepartmentTime?fsp.pageNo=" +pageNo+"&"+queryString;
			},
			downCSV: function(){
				window.location.href = "/mr/export_csv?" + (!!params ? "&" + params : "");
				alert(params);
				$.get("/bx/getDepartmentTime?" + (!!params ? "&" + params : ""),function(data){
					window.location.href=data;
//					window.open(data);
				});
	     		
			}
		}
	}
	return fn;
});