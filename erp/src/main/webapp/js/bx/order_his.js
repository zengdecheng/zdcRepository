requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
        "f": "jquery.form",
        "p": "paging"
    }
});
var sellStaffJsons, mrStaffJsons;
define(["p","f"], function(p,f) {
	var fn = {
		init : function(){
			biz.init();
			biz.bind();
			biz.event.getSellStaffs(); // 获取销售人员
			biz.event.getMrStaffs(); // 获取mr人员
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
			
			////初始化select
			//$("select","#form_search_div").each(function(i,n){
			//	var str = $("[hidName='hid."+$(n).attr("name")+"']").val();
			//	if(str != ""){
			//		$(n).val(str);
			//	}
			//});
			$("#statusSel").val($("#statusHid").val());
			$("#typeSel").val($("#typeHid").val());
			$("#if_repeatSel").val($("#if_repeatHid").val());
			$("#if_qualifiedSel").val($("#if_qualifiedHid").val());
		},
		bind : function(){
			$("#search_btn").on("click",biz.event.search);
			$("#output").on("click",biz.event.outExcel);
			$("#reset_btn").on("click",biz.event.resetFrom);
		},
		event : {
			getSellStaffs : function(e) {
				if (undefined == sellStaffJsons || null == sellStaffJsons) {
					biz.event.getSellStaffJson();
				}
				$("#crm_staff_sales").empty();
				$("#crm_staff_sales").append(
					"<option value=''>请选择</option>");
				$.each(sellStaffJsons, function(i, n) {
					if (sales == sellStaffJsons[i].loginName) {
						$("#crm_staff_sales").append(
							"<option selected value='"
							+ sellStaffJsons[i].loginName + "'>"
							+ sellStaffJsons[i].loginName
							+ "</option>");
					} else {
						$("#crm_staff_sales").append(
							"<option value='" + sellStaffJsons[i].loginName
							+ "'>" + sellStaffJsons[i].loginName
							+ "</option>");
					}
				});
			},
			//从crm获取销售人员
			getSellStaffJson : function() {
				$.ajax({
					type : "POST",
					url : "http://14.23.89.228:9000/ext/jsonGetStaffs?org=3",
					async : false,
					success : function(data) {
						if ("0" == data.code) {
							sellStaffJsons = data.resList;
						} else {
							alert("获取销售人员出错");
							return false;
						}
					},
					complete : function(XMLHttpRequest, textStatus) {
					},
					dataType : "json"
				});
			},
			// 获取MR人员数据
			getMrStaffJson : function() {
				$.ajax({
					type : "POST",
					url : "/ext/getMrStaff",
					async : false,
					success : function(data) {
						if ("0" == data.code) {
							mrStaffJsons = data.resList;
						} else {
							alert("获取Mr出错");
							return false;
						}
					},
					complete : function(XMLHttpRequest, textStatus) {
					},
					dataType : "json"
				});
			},
			getMrStaffs : function() {
				if (undefined == mrStaffJsons || null == mrStaffJsons) {
					biz.event.getMrStaffJson();
				}
				var mrNameHid = $("#mrNameHid").val();
				$("#crm_staff_mr").empty();
				$("#crm_staff_mr").append(
					"<option value=''>请选择</option>");
				$.each(mrStaffJsons, function(i, n) {
					if (mr_name == mrStaffJsons[i].loginName) {
						$("#crm_staff_mr").append(
							"<option selected value='"
							+ mrStaffJsons[i].loginName
							+ "'>"
							+ mrStaffJsons[i].loginName
							+ "</option>");
					} else {
						$("#crm_staff_mr").append(
							"<option value='"
							+ mrStaffJsons[i].loginName
							+ "'>"
							+ mrStaffJsons[i].loginName
							+ "</option>");
					}
				});
			},
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
				window.location.href = "/bx/order_his?fsp.pageNo=" +pageNo+"&"+queryString;
			},
            outExcel : function(){
                $("#output").attr("disabled", "true");
                $("#output").val("30秒后再次点击");
                $("select","#form_search_div").each(function(i,n){
                    if($(n).val() == '999'){
                        $(n).addClass("notselect");
                    }
                });
                var queryString = $("input,select","#form_search_div").not(".notselect").fieldSerialize();
                window.location.href = "/bx/outExcel?"+queryString;
                //$.ajax({
                //    type : "POST",
                //    url : "/bx/outExcel?"+queryString,
                //    async : false,
                //    success : function(data) {
                //        //window.location.href = "/bx/outExcel?"+queryString;
                //    },
                //    complete : function(XMLHttpRequest, textStatus) {
                //    }
                //});

                setTimeout(function(){
                    $("#output").removeAttr("disabled");
                    $("#output").val("导出报表");
                },30000);
            },
            resetFrom : function(){
                $(':input','#orderHis')
                    .not(':button, :submit, :reset, :hidden')
                    .val('')
                    .removeAttr('checked')
                    .removeAttr('selected');
            }
		}
	}
	return fn;
});