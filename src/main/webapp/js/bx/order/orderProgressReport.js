requirejs.config({
	baseUrl : "/js/pub/",
});
//var sellStaffJsons, mrStaffJsons;
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
					//将各个节点的消耗时间解析并添加颜色
					$(".color_td").each(function(){
						var time_color=$(this).html();
						var temp=time_color.split("-");
						$(this).html(temp[0]);
						$(this).css("background-color",temp[1]);
					});
					//初始化超时和剩余的时间样式
					$(".z_title_sty3").each(function(i,n){
						if($(n).text().indexOf("剩余") > -1){
							$(n).addClass("z_title_sty4");
						}
					});
					biz.event.resetNodeSelect(true);
					biz.event.resetDealMan(true);
                    biz.event.addCellColor();
//					
//					biz.event.orderColorSelect();
				},
				bind : function() {
					$("#reset").click(function() {
						console.info("clear");
						$("#dpClearInput").trigger("click");
					});
					$("#typeSelect").change(biz.event.resetNodeSelect);//三级联动 根据订单类型的不同，重新设置流程节点
					$("#nodeSelect").change(biz.event.resetDealMan);//根据流程节点的不同，重新设置操作人列表
					$("#exportReport").on("click",biz.event.exportReport);//导出excel报表
					$("#reset").on("click",biz.event.resetFrom);//重置表单
					
				},
				event : {
					resetFrom : function(){
		                $(':input','#search_form')
		                    .not(':button, :submit, :reset, :hidden')
		                    .val('')
		                    .removeAttr('checked')
		                    .removeAttr('selected');
		            },
					exportReport:function(){
						$("#exportReport").attr("disabled",true);
						$("#exportReport").val("30秒后再次点击");
						var params=$("#search_form").find("input[name^='fsp.map'][type='text'][value!=''],select[name^='fsp.map'][value!=0][value!='']").serialize();
						window.location.href="/bx/outputOrderList?"+(!!params ? "&" +params :"");
					/*	$.get("/bx/outputOrderList?"+(!!params ? "&" +params :""),function(){
							window.location.href=data;
						});*/
						setTimeout(function(){
							$("#exportReport").removeAttr("disabled");
							$("#exportReport").val("导出Excel");
						},30000);
					},
					resetDealMan:function(flag){
						//@com.xbd.oa.utils.XbdBuffer@getStaffsByGroupName('pur')
						if ($("#nodeSelect").val() == "c_mr_improve_2" || $("#nodeSelect").val()=="b_mr_improve_2") {
							$("#operatorSelect").empty();
							getOperator('mr');
						} else if ($("#nodeSelect").val() == "c_ppc_assign_3" || $("#nodeSelect").val()=="b_pur_confirm_4") {
							$("#operatorSelect").empty();
							getOperator('it');
						} else if ($("#nodeSelect").val() == "c_fi_pay_4" || $("#nodeSelect").val()=="b_ppc_confirm_3") {
							$("#operatorSelect").empty();
							getOperator('pur');
						} else if ($("#nodeSelect").val() == "c_ppc_factoryMsg_5") {
							$("#operatorSelect").empty();
							getOperator('cqc');
						} else if ($("#nodeSelect").val() == "c_qc_cutting_6") {
							$("#operatorSelect").empty();
							getOperator('qc');
						} else if ($("#nodeSelect").val() == "c_ppc_confirm_7") {
							$("#operatorSelect").empty();
							getOperator('qa');
						} else if ($("#nodeSelect").val() == "c_qc_printing_8") {
							$("#operatorSelect").empty();
							getOperator('fi');
						} else if ($("#nodeSelect").val() == "c_ppc_confirm_9") {
							$("#operatorSelect").empty();
							getOperator('qa');
						} else if ($("#nodeSelect").val() == "b_ppc_confirm_5") {
							$("#operatorSelect").empty();
							getOperator('cp');
						} else if ($("#nodeSelect").val() == "b_qc_confirm_6") {
							$("#operatorSelect").empty();
							getOperator('qa');
						} else{
							$("#operatorSelect").empty();
							$("#operatorSelect").append("<option value=''>请选择</option>");
						}
						if(flag) {
							$("#operatorSelect").val($("#operatoreSelectHid").val());
						}
					},
					resetNodeSelect : function(flag) {
						var dahuoNode = "<option value=''>请选择</option><option value='c_mr_improve_2'>订单详情</option><option value='c_ppc_assign_3'>技术</option><option value='c_fi_pay_4'>采购</option><option value='c_ppc_factoryMsg_5'>CQC</option><option value='c_qc_cutting_6'>TPE</option><option value='c_ppc_confirm_7'>QA</option><option value='c_qc_printing_8'>财务</option><option value='c_ppc_confirm_9'>物流</option>";
						var dabanNode = "<option value=''>请选择</option><option value='b_mr_improve_2'>订单详情</option><option value='b_ppc_confirm_3'>采购</option><option value='b_pur_confirm_4'>技术</option><option value='b_ppc_confirm_5'>核价</option><option value='b_qc_confirm_6'>MR</option>";
						if ($("#typeSelect").val() == "2") {
							$("#nodeSelect").empty();
							$("#nodeSelect").append(dabanNode);
							$("#operatorSelect").empty();//将处理人重新设置
							$("#operatorSelect").append("<option value=''>请选择</option>");
						} else if ($("#typeSelect").val() == "3") {
							$("#nodeSelect").empty();
							$("#nodeSelect").append(dahuoNode);
							$("#operatorSelect").empty();
							$("#operatorSelect").append("<option value=''>请选择</option>");
						} else {
							$("#nodeSelect").empty();
							$("#nodeSelect").append("<option value=''>请选择</option>");
							$("#operatorSelect").empty();
							$("#operatorSelect").append("<option value=''>请选择</option>");
						}
						if(flag) {
							$("#nodeSelect").val($("#nodeSelectHid").val());
						}
					},
                    addCellColor : function(){
                        $("[name='wtep_color']").each(function(i,n){
                            if($(this).val() != null && $(this).val() == "green"){
                                $(this).parent().css("background-color", "#309865");
                            }else if($(this).val() != null && $(this).val() == "yellow"){
                                $(this).parent().css("background-color", "#FFFC05");
                            }else if($(this).val() != null && $(this).val() == "red"){
                                $(this).parent().css("background-color", "#FC0303");
                            }
                        });

                    }
				}
			}
			return fn;
		});
function getOperator(name){
	$.ajax({
		url:"/bx/jsonGetOperator?name="+name,
		type:'POST',
		async:false,//这里必须加上，这样是ajax同步调用的意思，否则，无法回显所需的值
		success:function(data){
			$.each(data, function(i, n) {
				$("#operatorSelect").append("<option value='"+data[i].login_name+"'>"+data[i].login_name+"</option>");
				/*if(i==0){
					$("#operatorSelect").append("<option value=''>请选择</option>");
					$("#operatorSelect").append("<option value='"+data[i].login_name+"'>"+data[i].login_name+"</option>");
				}else{
					$("#operatorSelect").append("<option value='"+data[i].login_name+"'>"+data[i].login_name+"</option>");
				}*/
			});
		}
	});
}