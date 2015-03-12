requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
		"f": "jquery.form",
		"p": "paging",
	    "u": "util"
	}
});
var oaOrderDetailId = 0, oaOrderId = 0, staffList;
define(["p","f","u"], function(p,f,u) {
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
		},
		bind : function(){
			$("#search_btn").on("click",biz.event.search);
			$(".assign_btn").on("click",biz.event.jsonGetStaff); //分配事件绑定
			$('.cancel').on("click", biz.event.cancel); //取消事件绑定
			$("#assignConfirmBtn").on("click" , biz.event.jsonAssignOrder); //确认分配事件绑定
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
				window.location.href = "/bx/todo?fsp.pageNo=" +pageNo+"&"+queryString;
			},
			jsonGetStaff : function() {
				oaOrderDetailId = $(this).attr("oaOrderDetailId");
				oaOrderId = $(this).attr("oaOrderId");
				if(undefined == staffList || null == staffList) {
					var ajaxUrl = "/bx/jsonGetStaff";
		            $.ajax({
		                type: "post",
		                url: ajaxUrl,
		                async: false,
		                success: function(data) {
		                	if (data == "authorityError") {
		                		alert("你不是管理员，没有权限分配订单");
		                		return false;
		                	} else {
			                    var obj = jQuery.parseJSON(data);
			    				if (obj.length == 0) {
			    					alert("你没有人员可以进行分配");
			    					return;
			    				}
			    				staffList = obj;
		                	}
		                }
		            });
				}
                biz.event.loadData();
				$("#staffListForm").show();
				$("#Form").show();
			},
			loadData : function() {
				$('#operatorList li').remove();
				$.each(staffList, function(i, n) {
					$('#operatorList').append(u.format("<li><input type='radio' name='operator' class='z_radio1' value='" + staffList[i].id + "' /><label>" + staffList[i].login_name + "</label></li>"));
	            });
				$("input[name=operator]:eq(0)").attr("checked",'checked');
			},
			cancel : function() {
				$("#staffListForm").hide();
				$("#Form").hide();
			},
			jsonAssignOrder : function() {
				var ajaxUrl = "/bx/jsonAssignOrder";
	            ajaxUrl += "?oaOrder.id=" + oaOrderId + "&oaOrderDetail.id=" + oaOrderDetailId + "&oaStaffId=" + $('input[name="operator"]:checked').val();
	            
	            $.ajax({
	                type: "post",
	                url: ajaxUrl,
	                success: function(data) {
	                	if (data == "ok") {
	                		window.location.href = "/bx/todo";
	                	} else if(data == "oaStaffError") {
	                		alert("你所分配的人员不存在");
	                	} else {
	                		alert("订单出错");
	                	}
                		biz.event.cancel();
	                }
	            });
			},
			cancel : function() {
				$("#staffListForm").hide();
				$("#Form").hide();
			}
		}
	}
	return fn;
});