requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
		"v":"jqueryValidation/js/jquery.validationEngine.min",
		"vl":"jqueryValidation/js/jquery.validationEngine-zh_CN"
	}
});
var wfStepIndex = $(window.parent.document).find("#wfStepIndex").val();//订单所在节点
var orderId = $(window.parent.document).find("#orderId").val();	//订单ID
var index;			//当前所在index
var jsonData;		//后台传的数据
var isEdit;	//是否查看详情
var qiTaoTemplet;	//齐套模板
var titleLength=0;
var numInfoLength=0;
define([ "v","vl" ], function(v,vl) {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var biz = {
		init : function() {
			biz.event.jsonGetQANode();
			biz.event.initOrderData();
			biz.event.initTrackes();
			biz.event.initValidation();
		},
		bind : function() {
			$("#yidongBtn").on("click", biz.event.jsonSaveTracke);
			$("#processBtn").on("click", biz.event.processOrder); //保存并提交
			$("#saveBtn").on("click", biz.event.saveOrder); //保存草稿
			$(".download").on("click", biz.event.download); //下载文件
			biz.event.initSewingTotal();
			biz.event.initQualified();
			if(!isEdit){
				biz.event.initQulifyNum("init");
				biz.event.initQualifiedTotal("init");
				$("#yidongMemo").remove();
				$("#otherFileUploadTd").remove();
			}else{
				biz.event.initQulifyNum();
				biz.event.initQualifiedTotal();
				$(".qualify_num, .color").on("keyup", biz.event.calcQulifyNum);
				parent.iFrameHeight("iframeQA");
			};
		},
		event : {
			initValidation:function(){
				$("#tabQA").validationEngine('attach',{
					scroll:true,
					autoHidePrompt:true,
					autoHideDelay:1500,
					showOneMessage: true,
					fadeDuration : 0.3,
					promptPosition : "bottomLeft"
				});
			},
			jsonGetQANode : function() {
				$.ajax({
                    url: '/bx/jsonGetQANode?orderId='+orderId+'&date='+new Date().getTime(),
                    type: "post",
                    async: false,
                    dataType: "json",
                    success: function(data) {
                    	if("ajaxLogin" == data){
                			alert("登录超时，请重新登录");
                		} else if("0" ==  data.code){
                			jsonData = data;
                			if(undefined != data.oaOrderDetail && null != data.oaOrderDetail && undefined != data.oaOrderDetail.other_file && null != data.oaOrderDetail.other_file && "" != data.oaOrderDetail.other_file && "null" != data.oaOrderDetail.other_file) {
                				var downFile = "<a downloadurl='" +data.oaOrderDetail.other_file + "' class='z_title_sty1 mar_l10 download_a download' href='javascript:void(0)'>" + data.oaOrderDetail.other_file_name + "</a>";
        						$("#otherFile").html(downFile);
            				}
                			if(undefined != data.oaOrderDetail && null != data.oaOrderDetail  && undefined != data.oaOrderDetail.schedule && null != data.oaOrderDetail.schedule) {
                				if(data.oaOrderDetail.schedule.indexOf("超时") >= 0) {
                					$("#detailSchedule").css("background-color", "red");
                					$("#detailSchedule").css("color", "white");
                				} else if(data.oaOrderDetail.schedule.indexOf("提前") >= 0) {
                					$("#detailSchedule").css("background-color", "green");
                					$("#detailSchedule").css("color", "white");
                				}
                				$("#detailSchedule").text(data.oaOrderDetail.schedule);
                			}
                			//计算流入颜色和流出颜色
                			if(undefined != data.oaOrderDetail && undefined != data.oaOrderDetail.step_start_time_consume && null != data.oaOrderDetail.step_start_time_consume && "" != data.oaOrderDetail.step_start_time_consume) {
        						var rate=parseFloat(data.oaOrderDetail.step_start_time_consume);
								if(rate < 0){
									$("#detailRealStartColor").css("background-color","#0000FF");
								}else if(rate <= 33){
        							$("#detailRealStartColor").css("background-color","#33cc00");
        						}else if(rate <= 66){
        							$("#detailRealStartColor").css("background-color","#ff9900");
        						}else if(rate <= 100){
        							$("#detailRealStartColor").css("background-color","#ff3300");
        						}else {
        							$("#detailRealStartColor").css("background-color","#000000");
        						}
        						$("#detailRealStartColor").text(data.oaOrderDetail.step_start_time_consume + "%");
        					}
        					if(undefined != data.oaOrderDetail && undefined != data.oaOrderDetail.step_finish_time_consume && null != data.oaOrderDetail.step_finish_time_consume && "" != data.oaOrderDetail.step_finish_time_consume) {
        						var rate=parseFloat(data.oaOrderDetail.step_finish_time_consume);
								if(rate < 0){
									$("#detailRealFinishColor").css("background-color","#0000FF");
								}else if(rate <= 33){
        							$("#detailRealFinishColor").css("background-color","#33cc00");
        						}else if(rate <= 66){
        							$("#detailRealFinishColor").css("background-color","#ff9900");
        						}else if(rate <= 100){
        							$("#detailRealFinishColor").css("background-color","#ff3300");
        						}else {
        							$("#detailRealFinishColor").css("background-color","#000000");
        						}
        						$("#detailRealFinishColor").text(data.oaOrderDetail.step_finish_time_consume + "%");
        					}
        					
        					if(undefined!=jsonData.qaInfo && undefined!=jsonData.qaInfo.sewing_num){
        						var st = [];
        						$.each(jsonData.qaInfo.sewing_num.split(","),function(index,data){
        							st[index] =data.split("-");
        						})
        						jsonData.qaInfo.sewing_num = st;
        					}
                		} else {
                			alert(data.msg);
                		}
                    }
                });
			},
			initOrderData : function() { 
				$("#orderId").val(orderId);
				isEdit = $(window.parent.document).find("#orderDetail").val(); //是否是查看订单详情
				if("true" != isEdit && "7" == wfStepIndex) { //判断是否可编辑
				//if(true){
					isEdit = true;//可编辑
					$("#orderDetailDive").remove();
				}else{
					isEdit = false;//可编辑
					$("#processOrderDive").remove();
				}
				if(parseInt(wfStepIndex) < 7) {
					$("#exportBtn").remove();
				}
				//初始化模板
				var sewing_num_show;
				var qualify_num_show;
				var unqualify_num_show;
				var sewing_num_opt;
				var qualify_num_opt;
				var unqualify_num_opt;
				var title="<tr><th style='width: 155px;'>颜色</th>";
				if(jsonData.qaInfo != undefined && jsonData.qaInfo.title !=undefined){
					$.each(jsonData.qaInfo.title,function(index,data){
						title+="<th style='width: 155px;'>"+data+"</th>";
					})
					titleLength = jsonData.qaInfo.title.length;
				}
				title+="<th style='width: 155px;'>小计</th></tr>";
				$("#sewing_num_show").append(title);
				$("#qualify_num_show").append(title);
				$("#unqualify_num_show").append(title);
				
				if(jsonData.qaInfo != undefined && jsonData.qaInfo.sewing_num != undefined){
					sewing_num_show = "<tr><td elementFillName='qaInfo.sewing_num[][0]'></td>";
					var i;
					for(i=1;i<titleLength+1;i++){
						sewing_num_show+="<td class='sewing_num' elementFillName='qaInfo.sewing_num[]["+i+"]'></td>";
					}
					sewing_num_show += "<td class='total'></td></tr>";
				}
				numInfoLength = jsonData.qaInfo.length;
				
				if(!isEdit){
					$("#operator").html("制单:<span elementFillname='oaOrderDetail.worker'></span>");
					$("#detailContent").prop("disabled", true);
					if(jsonData.qaInfo != undefined && jsonData.qaInfo.qualified_num_info !=undefined){
						var t = (typeof(jsonData.qaInfo.qualified_num_info[0]) == "object")?"[0]":"";
						qualify_num_show = "<tr><td elementFillName='qaInfo.qualified_num_info[]"+t+"'/>"
						var i;
						for(i=1;i<titleLength+1;i++){
							var t = (typeof(jsonData.qaInfo.qualified_num_info[0]) == "object")?"elementFillName='qaInfo.qualified_num_info[]["+i+"]'":"";
							qualify_num_show+="<td "+t+" class='qualify_num'/>";
						}
						qualify_num_show += "<td class='total'></td></tr>";
					}
					if(jsonData.qaInfo != undefined && jsonData.qaInfo.unqualified_num_info !=undefined){
						var t = (typeof(jsonData.qaInfo.unqualified_num_info[0]) == "object")?"[0]":"";
						unqualify_num_show = "<tr><td elementFillName='qaInfo.unqualified_num_info[]"+t+"'/>"
							var i;
						for(i=1;i<titleLength+1;i++){
							var t = (typeof(jsonData.qaInfo.unqualified_num_info[0]) == "object")?"elementFillName='qaInfo.unqualified_num_info[]["+i+"]'":"";
							unqualify_num_show+="<td "+t+" class='qualify_num'/>";
						}
						unqualify_num_show += "<td class='total'></td></tr>";
					}
				}else{
					$("#worker").attr("fillName","oaOrderDetail.worker");
					if(jsonData.qaInfo != undefined && jsonData.qaInfo.qualified_num_info !=undefined){
						var t = (typeof(jsonData.qaInfo.qualified_num_info[0]) == "object")?"[0]":"";
						qualify_num_show = "<tr><td><input style='width: 80px;' class='color' fillName='qaInfo.qualified_num_info[]"+t+"' class='validate[maxSize[10]]' type='text'/></td>"
						var i;
						for(i=1;i<titleLength+1;i++){
							var t = (typeof(jsonData.qaInfo.qualified_num_info[0]) == "object")?"fillName='qaInfo.qualified_num_info[]["+i+"]'":"";
							qualify_num_show+="<td><input style='width: 80px;' class='qualify_num validate[custom[number2],maxSize[10]]' "+t+" type='text'/></td>";
						}
						qualify_num_show += "<td class='total'></td></tr>";
					}
					if(jsonData.qaInfo != undefined && jsonData.qaInfo.unqualified_num_info !=undefined){
						var t = (typeof(jsonData.qaInfo.unqualified_num_info[0]) == "object")?"[0]":"";
						unqualify_num_show = "<tr><td><input style='width: 80px;' class='color' fillName='qaInfo.unqualified_num_info[]"+t+"' class='validate[maxSize[10]]' type='text'/></td>"
							var i;
						for(i=1;i<titleLength+1;i++){
							var t = (typeof(jsonData.qaInfo.unqualified_num_info[0]) == "object")?"fillName='qaInfo.unqualified_num_info[]["+i+"]'":"";
							unqualify_num_show+="<td><input style='width: 80px;' class='qualify_num validate[custom[number2],maxSize[10]]' "+t+" type='text'/></td>";
						}
						unqualify_num_show += "<td class='total'></td></tr>";
					}
				};
				
				sewing_num_opt = {
					formFill:true,
					formFillName:'fillName',
					elementText:true,
					elementFillName:'elementFillName',	
					addRow:true,
					addNum:numInfoLength,
					addPoint:'sewing_num_show',
					templet:sewing_num_show,
					isList:true,
					debug : false
				};
				qualify_num_opt = {
					formFill:true,
					formFillName:'fillName',
					elementText:true,
					elementFillName:'elementFillName',	
					addRow:true,
					addNum:numInfoLength,
					addPoint:'qualify_num_show',
					templet:qualify_num_show,
					isList:true,
					debug : false
				};
				unqualify_num_opt = {
					formFill:true,
					formFillName:'fillName',
					elementText:true,
					elementFillName:'elementFillName',	
					addRow:true,
					addNum:numInfoLength,
					addPoint:'unqualify_num_show',
					templet:unqualify_num_show,
					isList:true,
					debug : false
				};
				$("#tabQA").autoFill(jsonData, sewing_num_opt);
				$("#tabQA").autoFill(jsonData, qualify_num_opt);
				$("#tabQA").autoFill(jsonData, unqualify_num_opt);
				var qualify_num_total = "<tr><td colspan="+titleLength+"/><td>合计</td><td class='qualifiedTotal'></td><tr>";
				var unqualify_num_total ="<tr><td colspan="+titleLength+"/><td>合计</td><td class='unqualifiedTotal'></td><tr>";
				var sewingTotal ="<tr><td colspan="+titleLength+"/><td>合计</td><td class='sewingTotal'></td><tr>";
				$("#qualify_num_show").append(qualify_num_total);
				$("#unqualify_num_show").append(unqualify_num_total);
				$("#sewing_num_show").append(sewingTotal);
			},
			initSewingTotal:function(){
				$.each($("#sewing_num_show tr:gt(0)"),function(index,data){
					var total = 0;
					$.each($(data).find(".sewing_num"),function(i,d){
						d = parseFloat($(d).html());
						if(!isNaN(d)){
							total+=d;
						}
					});
					$("#sewing_num_show tr:eq("+(index+1)+") .total").html(total);
				});
			},
			calcQulifyNum:function(){
				var total=0;
				$.each($(this).parent().parent().find(".qualify_num"),function(index,data){
					if(!isNaN(parseFloat($(data).val()))){
						total+=parseFloat($(data).val());
					}
				});
				$(this).parent().parent().find(".total").html(total);
				biz.event.initQualifiedTotal();
				biz.event.initQualified();
			},
			initQualified:function(){
				var total="";
				$.each($("#qualify_num_show tr:gt(0)"),function(index,data){
					$.each($(data).find("input"),function(i,d){
						total+=$(d).val()+"-";
					});
					total = total.substr(0,total.length-1)+",";
				});
				total = total.substr(0,total.length-1);
				$("#qualifiedNumInfo").val(total);
				total="";
				$.each($("#unqualify_num_show tr:gt(0)"),function(index,data){
					$.each($(data).find("input"),function(i,d){
						total+=$(d).val()+"-";
					});
					total = total.substr(0,total.length-1)+",";
				});
				total = total.substr(0,total.length-1);
				$("#unqualifiedNumInfo").val(total);
			},
			initQualifiedTotal:function(init){
				var total=0;
				$.each($("#qualify_num_show .total"),function(index,data){
					if(!isNaN(parseFloat($(data).text()))){
						total+= parseFloat($(data).text());
					}
				});
				if(init !="init"){
					$("#qualifiedTotal").val(total);
				}
				$(".qualifiedTotal").text(total);
				
				total = 0;
				$.each($("#unqualify_num_show .total"),function(index,data){
					if(!isNaN(parseFloat($(data).text()))){
						total+= parseFloat($(data).text());
					}
				});
				if(init !="init"){
					$("#unqualifiedTotal").val(total);
				}
				$(".unqualifiedTotal").text(total);
				
				total = 0;
				$.each($("#sewing_num_show .total"),function(index,data){
					if(!isNaN(parseFloat($(data).text()))){
						total+= parseFloat($(data).text());
					}
				});
				$(".sewingTotal").text(total);
			},
			initQulifyNum:function(init){
				$.each($("#qualify_num_show tr:gt(0),#unqualify_num_show tr:gt(0)"),function(index,data){
					var total=0;
					$.each($(data).find(".qualify_num"),function(i,d){
						if(init=="init"){
							if(!isNaN(parseFloat($(d).html()))){
								total+=parseFloat($(d).html());
							}
						}else{
							if(!isNaN(parseFloat($(d).val()))){
								total+=parseFloat($(d).val());
							}
						}
					});
					$(data).find(".total").html(total);
				});
			},
			initTrackes : function(){
				if(jsonData.oaTrackes){
					$.each(jsonData.oaTrackes,function(i,v){
						biz.event.putOaTrackesTrData(v.memo, v.user, v.time);
					})
				}
				parent.iFrameHeight("iframeQA");
			},
			putOaTrackesTrData : function(td1, td2, td3) { //添加一行异动信息到列表
				var tr = "<tr><td width='53px' height='22px'></td><td width='573px'>" + td1 + "</td><td>" + td2 + "/" + td3 + "</td></tr>";
				$("#oaTrackesTable").append(tr);
				parent.iFrameHeight("iframeQA");
			},
			jsonSaveTracke : function() { //保存异动信息
				if($.trim($("#yidongContent").val()) != "") {
					var params = {"oaTracke.memo" : $("#yidongContent").val(), "oaTracke.oaOrder" : orderId, "oaTracke.node" : $(window.parent.document).find("#wfStep").val()};
					$.ajax({
	                    url: "/bx/jsonSaveTracke",
	                    type: "post",
	                    data: params,
	                    success: function(data) {
	                    	if("ajaxLogin" == data){
	                			alert("登录超时，请重新登录");
	                		} else if("0" ==  data.code){
	                			biz.event.putOaTrackesTrData($("#yidongContent").val(), data.oaTrackeUser, data.oaTrackeTime);
	                			$("#yidongContent").val("");
	                			parent.iFrameHeight("iframeQA");
	                		} else {
	                			alert(data.msg);
	                		}
	                    }
	                });
				} else {
					alert("发布异动信息不能为空");
				}
			},
			download : function() { //下载文件
				window.location.href = "/oaOrderFileDownload?fileUrl=" + encodeURI($(this).attr("downloadUrl"));
			},
			processOrder : function() { //保存并提交订单
				$("#processOrder").val("true"); //true标识流程流转
				biz.event.jsonProcessOrder();
			},
			saveOrder : function() { //保存草稿
				$("#processOrder").val("false"); //false标识流程不流转，只保存数据
				biz.event.jsonProcessOrder();
			},
			jsonProcessOrder:function(){//ajax提交表单
				//先要对所填写的数据进行验证合法后才进行提交
				var qualifiedTotal = isNaN(parseFloat($(".qualifiedTotal").text()))?0:parseFloat($(".qualifiedTotal").text());
				var unqualifiedTotal = isNaN(parseFloat($(".unqualifiedTotal").text()))?0:parseFloat($(".unqualifiedTotal").text());
				var sewingTotal = parseFloat($(".sewingTotal").text());
				if(sewingTotal<(qualifiedTotal+unqualifiedTotal)){
					alert("总合格数量+总不合格数量应 <= 车缝总数");
					return ;
				}
				var successFlag=$("#tabQA").validationEngine("validate");
				if(successFlag){
					$("#processBtn").attr("disabled", "true");
					$("#saveBtn").attr("disabled", "true");
					$("#backBtn").attr("disabled", "true");
					var params = $("#tabQA").serializeArray();
					$.ajax({
	                    url: "/bx/jsonProcessOrder",
	                    type: "post",
	                    data: params,
	                    success: function(data) {
	                    	if("ajaxLogin" == data){
	                			alert("登录超时，请重新登录");
	                		} else if("0" ==  data.code){
	                			top.location.href = "/bx/todo";
	                		} else {
	                			alert(data.msg);
		        				$("#processBtn").removeAttr("disabled");
		        				$("#saveBtn").removeAttr("disabled");
		        				$("#backBtn").removeAttr("disabled");
	                		}
	                    },
	                    error: function() {
		        			$("#processBtn").removeAttr("disabled");
		        			$("#saveBtn").removeAttr("disabled");
		        			$("#backBtn").removeAttr("disabled");
	                    }
	                });
				}
			}
		}
	}
	return fn;
});

//文件上传回调函数
function uploadComplete(data) {
	data = data.replace('<meta http-equiv="content-type" content="text/html; charset=UTF-8">',"");
	if (data === "extensionerror") {
		alert("图片格式不对，仅支持*.png、*.gif、*.jpg、*.bmp、*.jpeg，请重新上传！");
		return;
	}
	if (data === "error") {
		alert("上传失败！");
		return;
	}
	if (data === "empty") {
		alert("上传文件为空！");
		return;
	}
	var json = $.parseJSON(data);
	// 对上传文件时进行判断
	if (json.fileType == "file") {
		$("#hid_attachment_url").val(json.url);
		var html = "<a href='" + json.url + "'>" + json.fileName+ "</a>";
		$("#otherFile").html(json.fileName);
	}
}

function showMessage(data) {
	alert(data);
}