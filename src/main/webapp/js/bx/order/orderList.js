requirejs.config({
	baseUrl : "/js/pub/",
    paths : {}
});
var sellStaffJsons, mrStaffJsons;
define(
		[ "" ],
		function(p, f) {
			var fn = {
				init : function() {
					biz.init();
					biz.bind();
				}
			};
			var biz = {
				init : function() { // 初始化
					//初始化超时和剩余的时间样式
					$(".z_title_sty3").each(function(i,n){
						if($(n).text().indexOf("剩余") > -1){
							$(n).addClass("z_title_sty4");
						}
					});
					
					
					if($("#yxjOrderHid").val() == "1"){
                		$("#yxjOrder").html("优先级&nbsp;↑");
                	}else if($("#yxjOrderHid").val() == "2"){
                		$("#yxjOrder").html("优先级&nbsp;↓");
                	}else if($("#yxjOrderHid").val() == "3"){
                		$("#cjrqOrder").html("创建日期&nbsp;↑");
                	}else if($("#yxjOrderHid").val() == "4"){
                		$("#cjrqOrder").html("创建日期&nbsp;↓");
                	}
					biz.event.getSellStaffs(); // 获取销售人员
					biz.event.getMrStaffs(); // 获取mr人员
					biz.event.resetNodeSelect(true);
					biz.event.orderColorSelect();
					//$("#output").on("click",biz.event.output);
				},
				bind : function() {
					$("#reset").click(function() {
						console.info("clear");
						$("#dpClearInput").trigger("click");
					});
					$("#typeSelect").change(biz.event.resetNodeSelect);
					//绑定确认事件,Add by ZQ 2014-12-20
					$("#confirm_form").on("click",biz.event.confirmChoose);
					$("#close_layer").on("click",biz.event.closeWin);
                    $("#reset_btn").on("click",biz.event.resetFrom);
                    $("#output").on("click",biz.event.outExcel);
                    $("#yxjOrder").on("click",biz.event.yxjOrder);
                    $("#cjrqOrder").on("click",biz.event.cjrqOrder);
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
					resetNodeSelect : function(flag) {
						var dahuoNode = "<option value=''>请选择</option><option value='c_mr_improve_2'>MR补入订单</option><option value='c_ppc_assign_3'>技术</option><option value='c_fi_pay_4'>采购</option><option value='c_ppc_factoryMsg_5'>CQC</option><option value='c_qc_cutting_6'>TPE</option><option value='c_ppc_confirm_7'>QA</option><option value='c_qc_printing_8'>财务</option><option value='c_ppc_confirm_9'>物流</option>";
						var dabanNode = "<option value=''>请选择</option><option value='b_mr_improve_2'>MR补入订单</option><option value='b_ppc_confirm_3'>采购</option><option value='b_pur_confirm_4'>技术</option><option value='b_ppc_confirm_5'>核价</option><option value='b_qc_confirm_6'>MR确认</option>";
						if ($("#typeSelect").val() == "2") {
							$("#nodeSelect").empty();
							$("#nodeSelect").append(dabanNode);
						} else if ($("#typeSelect").val() == "3") {
							$("#nodeSelect").empty();
							$("#nodeSelect").append(dahuoNode);
						} else {
							$("#nodeSelect").empty();
							$("#nodeSelect").append("<option value=''>请选择</option>");
						}
						if(flag) {
							$("#nodeSelect").val($("#nodeSelectHid").val());
						}
					},
					//Add by ZQ 2014-12-20 判断是否选中，选中后进行业务操作
					confirmChoose : function(){
					var op = $("input[name=futureOrder]:checked").val();
					//判断是否选中
					if(undefined==op||""==op){
						alert("请选择中要关联的订单号");
						return;
					}
					//调用父窗口元素事件
					$(window.parent.document).find("#chooseBtn").click();
				
					},
					//Add by ZQ 2014-12-20 判断是否选中，选中后进行业务操作
					closeWin : function(){
						//调用父窗口元素事件
						$(window.parent.document).find("#hidCloseBtn").click();
					},
					output:function(){
						$("#output").attr("disabled", "true");
						$("#output").val("30秒后再次点击");
						var params = $("#orderList").serializeArray();
						$.ajax({
							url: "/bx/outputOrderList",
							type: "post",
							data: params,
							success: function(data) {
								
							},
							error: function() {
								//$("#output").val("导出报表");
							}
						});
						
						setTimeout(function(){
							$("#output").removeAttr("disabled");
							$("#output").val("导出报表");
						},30000);
					},
					orderColorSelect : function(){
						$("#orderColor").val($("#orderColorHid").val());
					},
                    resetFrom : function(){
                        $(':input','#orderList')
                            .not(':button, :submit, :reset, :hidden')
                            .val('')
                            .removeAttr('checked')
                            .removeAttr('selected');
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
                        window.location.href = "/bx/outExcelZz?"+queryString;
                        //$.ajax({
                        //    type : "POST",
                        //    url : "/bx/outExcelZz?"+queryString,
                        //    async : false,
                        //    success : function(data) {
                        //        alert("导出成功");
                        //    },
                        //    complete : function(XMLHttpRequest, textStatus) {
                        //    }
                        //});

                        setTimeout(function(){
                            $("#output").removeAttr("disabled");
                            $("#output").val("导出报表");
                        },30000);
                    },
                    yxjOrder : function(){
                    	if($("#yxjOrderHid").val() == "3" || $("#yxjOrderHid").val() == "4"){
                    		$("#yxjOrderHid").val("1");
                    	}
                    	$("#orderList").submit();
                    },
                    cjrqOrder : function(){
                    	if($("#yxjOrderHid").val() == "1" || $("#yxjOrderHid").val() == "2"){
                    		$("#yxjOrderHid").val("3");
                    	}
                    	$("#orderList").submit();
                    }
				}
			}
			return fn;
		});