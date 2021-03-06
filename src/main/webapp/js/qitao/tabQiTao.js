requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
		"v":"jqueryValidation/js/jquery.validationEngine.min",
		"vl":"jqueryValidation/js/jquery.validationEngine-zh_CN"
	}
});
var wfStepIndex = $(window.parent.document).find("#wfStepIndex").val();//订单所在节点
var orderId = $(window.parent.document).find("#orderId").val();	//订单ID
var opt;		//autoFill配置
var index;		//当前所在index
var jsonData;	//后台传的数据
var isEdit=false;	//是否查看详情
var qiTaoTemplet;			//齐套模板
define([ "v","vl" ], function(v,vl) {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var biz = {
		init : function() {
			biz.event.jsonGetDaHuoNineNode();
			biz.event.initOrderData();
			biz.event.initTrackes();
			biz.event.initValidation();
		},
		bind : function() {
			$("#saveLogistics").on("click", biz.event.jsonSaveLogistics);
			$(".jizhuangPic").on("click", biz.event.showPic);
			$("#yidongBtn").on("click", biz.event.jsonSaveTracke);
			$("#processBtn").on("click", biz.event.processOrder); //保存并提交
			$("#saveBtn").on("click", biz.event.saveOrder); //保存草稿
			$(".download").on("click", biz.event.download); //下载文件
			//$("#exportBtn").on("click", biz.event.exportExcel); //导出订单Excel
			if(!isEdit){
				$("#logisticsRemove").on("click", biz.event.loggisticsRemove);
			}else{
				$("#logisticsRemove").remove();
				$("#common").attr("disabled","disabled");
				$("#yidongMemo").remove();
				$("#otherFileUploadTd").remove();
				parent.iFrameHeight("iframeQiTao");
			}
		},
		event : {
			initValidation:function(){
				$("#tabQiTao").validationEngine('attach',{
					scroll:true,
					autoHidePrompt:true,
					autoHideDelay:1500,
					showOneMessage: true,
					fadeDuration : 0.3,
					promptPosition : "bottomLeft"
				});
			},
			jsonGetDaHuoNineNode : function() { // 获取第四节点页面信息
				$.ajax({
                    url: '/qi_tao!getQiTaoInfo.action?oaOrderId='+orderId+'&wfStepIndex=' + wfStepIndex,  //='+orderId+'&wfStepIndex='+9,
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
							////计算流入颜色和流出颜色
							//if(undefined != data.oaOrderDetail && undefined != data.oaOrderDetail.step_start_time_consume && null != data.oaOrderDetail.step_start_time_consume && "" != data.oaOrderDetail.step_start_time_consume) {
							//	var rate=parseFloat(data.oaOrderDetail.step_start_time_consume);
							//	if( rate < 0 ) {
							//		$("#detailRealStartColor").css("background-color","#0000FF");
							//	}else if(0 <= rate <= 33){
							//		$("#detailRealStartColor").css("background-color","#33cc00");//绿色
							//	}else if(rate <= 66){
							//		$("#detailRealStartColor").css("background-color","#ff9900");//黄色
							//	}else if(rate <= 100){
							//		$("#detailRealStartColor").css("background-color","#ff3300");//红色
							//	}else {
							//		$("#detailRealStartColor").css("background-color","#000000");//黑色
							//	}
							//	$("#detailRealStartColor").text(data.oaOrderDetail.step_start_time_consume + "%");
							//}
							if(undefined != data.oaOrderDetail && undefined != data.oaOrderDetail.step_finish_time_consume && null != data.oaOrderDetail.step_finish_time_consume && "" != data.oaOrderDetail.step_finish_time_consume) {
								var rate=parseFloat(data.oaOrderDetail.step_finish_time_consume);
								if( rate < 0 ) {
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

							$("#oaOrderId").val(data.oaOrder.id);
							//biz.event.showQaTable(data);
							//biz.event.showLogistics(data);
                		} else {
                			alert(data.msg);
                		}
                    }
                });
			},
			initOrderData : function() {
				$("#orderId").val(orderId);
				var isEdit = $(window.parent.document).find("#orderDetail").val(); //是否是查看订单详情
                var qitaoReceiveTime = "";
                var qitaoTiHuoTime = "";
                var qitaoSendTime ="";
                var qitaoIsBackTime = "";
                if(typeof(jsonData.oaQiTaoStep) != 'undefined'){
                    qitaoReceiveTime = jsonData.oaQiTaoStep.qitaoReceiveTime;
                    qitaoTiHuoTime = jsonData.oaQiTaoStep.qitaoTiHuoTime;
                    qitaoSendTime = jsonData.oaQiTaoStep.qitaoSendTime;
                    qitaoIsBackTime = jsonData.oaQiTaoStep.qitaoIsBackTime;
                }
                if ("true" != isEdit && wfStepIndex == 8) { //判断是否可编辑
					isEdit = true;//可编辑
                    $("#qiTaoTime").empty().append("<span class='qi_tao_span'>接收时间：<input type='text' class='validate[required] z_inp2' style='width:150px;' name='oaQiTaoStep.qitaoReceiveTime' value='"+ qitaoReceiveTime + "'  onFocus=\"WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'})\" /></span>" +
                                                    "<span class='qi_tao_span'>可提货时间：<input type='text' class='validate[required] z_inp2' style='width:150px;' name='oaQiTaoStep.qitaoTiHuoTime' value='"+ qitaoTiHuoTime + "' onFocus=\"WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'})\" /></span>" +
                                                    "<span class='qi_tao_span'>实际外发时间：<input type='text' class='z_inp2' style='width:150px;' name='oaQiTaoStep.qitaoSendTime'  value='"+ qitaoSendTime + "' onFocus=\"WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'})\" /></span>" +
                                                    "<span class='qi_tao_span'>退单总计时：<input type='text' class='z_inp2' style='width:150px;' name='oaQiTaoStep.qitaoIsBackTime' value='"+ qitaoIsBackTime + "' onFocus=\"WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'})\" /></span>");

                } else {
					isEdit = false;//不可编辑

                    $("#qiTaoTime").empty().append("<span class='qi_tao_span'>接收时间："+ qitaoReceiveTime + "</span>" +
                                                    "<span class='qi_tao_span'>可提货时间："+ qitaoTiHuoTime + "</span>" +
                                                    "<span class='qi_tao_span'>实际外发时间："+ qitaoSendTime + "</span>" +
                                                    "<span class='qi_tao_span'>退单总计时："+ qitaoIsBackTime + "</span>");


                    $("#logisticsRemove,#yidongMemo,#otherFileUploadTd").remove();
					$("form input[type='text'],textarea,select").prop("disabled", true);
				}
				if (parseInt(wfStepIndex) < 9) {
					$("#exportBtn").remove();
				}
				opt = {
					formFill:true,
					formFillName:'fillName',
					elementText:true,
					elementFillName:'elementfillname',
					debug : false
				};
                $("#tabQiTao").autoFill(jsonData, opt);
				$("#worker").attr("fillName","oaOrderDetail.worker");
			},
			jsonSaveLogistics : function() {
				var successFlag=$("#tabQiTao").validationEngine("validate");

				if(successFlag){
					var params = $("#tabQiTao").serializeArray();
					$.ajax({
						url: "/bx/saveOaLogistics",
						type: "post",
						data: params,
						success: function(data) {
							if("ajaxLogin" == data){
	                			alert("登录超时，请重新登录");
	                		} else {
								$("#logisticsDetile").append("<tr>" +
								"<td><input type='checkbox' name='logCheckBox' value='"+data.oaLogistics.id+"' /></td>" +
								"<td><a href='http://www.kuaidi100.com/' target='_blank'>"+data.oaLogistics.logisticsNum+"</a></td>" +
								"<td>"+data.oaLogistics.logisticsCompany+"</td>" +
								"<td>"+data.oaLogistics.deliveryPle+"</td>" +
								"<td>"+data.oaLogistics.deliveryNum+"</td>" +
								"<td>"+data.oaLogistics.deliveryTime+"</td>" +
								"<td><a href='"+data.oaLogistics.fileUrl+"' target='_blank'>查看集装箱单</a></td>" +
								"<td>"+data.oaLogistics.remarks+"</td>" +
								"</tr>");
								parent.iFrameHeight("iframeQiTao");
	                		}
						}
					});
				}
			},
			showPic : function(){
				alert($(this).attr("data"));
				$("#login").show();
				$("#logisticsPic").attr("src", $(this).attr("data"));
			},
			initTrackes : function(){
				if(jsonData.oaTrackes){
					$.each(jsonData.oaTrackes,function(i,v){
						biz.event.putOaTrackesTrData(v.memo, v.user, v.time);
					})
				}
				parent.iFrameHeight("iframeQiTao");
			},
			putOaTrackesTrData : function(td1, td2, td3) { //添加一行异动信息到列表
				var tr = "<tr><td width='53px' height='22px'></td><td width='573px'>" + td1 + "</td><td>" + td2 + "/" + td3 + "</td></tr>";
				$("#oaTrackesTable").append(tr);
				parent.iFrameHeight("iframeQiTao");
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
	                			parent.iFrameHeight("iframeQiTao");
	                		} else {
	                			alert(data.msg);
	                		}
	                    }
	                });
				} else {
					alert("发布异动信息不能为空");
				}
			},
			exportExcel : function() { //导出excel操作
				$("#exportBtn").attr("disabled", "true");
				$("#exportBtn").val("30秒后再次点击");
				top.location.href = "/bx/downOAOrder?oaOrder.id=" + orderId + "&node=5";
				setTimeout(function(){
					$("#exportBtn").removeAttr("disabled");
					$("#exportBtn").val("导出Excel");
				},30000);
			},
			download : function() { //下载文件
				window.location.href = "/oaOrderFileDownload?fileUrl=" + encodeURI($(this).attr("downloadUrl"));
			},
			saveOrder : function() { //保存草稿
				$("#processOrder").val("false"); //false标识流程不流转，只保存数据
				biz.event.jsonProcessOrder();
			},
            processOrder : function() { // 保存并提交订单
                $("#processOrder").val("true"); // true标识流程流转
                biz.event.jsonProcessOrder();
            },
			jsonProcessOrder:function(){//ajax提交表单
				//先要对所填写的数据进行验证合法后才进行提交
				var successFlag= $("#tabQiTao").validationEngine("validate");
				if(successFlag){
					$("#processBtn").attr("disabled", "true");
					$("#saveBtn").attr("disabled", "true");
					$("#backBtn").attr("disabled", "true");
					var params = $("#tabQiTao").serializeArray();
					$.ajax({
	                    url: "/bx/jsonProcessOrder",
	                    type: "post",
	                    data: params,
	                    success: function(data) {
	                    	if("ajaxLogin" == data){
	                			alert("登录超时，请重新登录");
	                		} else if("0" ==  data.code){
	                			top.location.href = "/bx/todo";
                                //top.location.reload(true);
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
			},
			loggisticsRemove : function() {
				var $ckbs=$("input[type=checkbox]:checked");
				if($ckbs.size()==0){
				    alert("请勾选要删除的行");
					return;
				}else{
					if(confirm("是否确认删除选中行？")){
						$ckbs.each(function(){
							var oaQiTaoDetailIDs = $('#logCheckBoxVal').val();
							var delML =$(this).val(); //parent().parent().find('.oaQiTaoDetailIDs').val();
							if(oaQiTaoDetailIDs==''){
								$('#logCheckBoxVal').val(delML);
							}else{
								$('#logCheckBoxVal').val(oaQiTaoDetailIDs+','+delML);
							}
					 		$(this).parent().parent().remove();
						});
					}
				}
				parent.iFrameHeight("iframeQiTao");
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
	} else if (json.fileType == "pic") {
		$("#hid_attachment_url_zhuang_xiang").val(json.url);
		var html = "<a href='" + json.url + "'>" + json.fileName+ "</a>";
		$("#zhuangxiang_pic").html("<span style='color:red'>*</span>装箱单："+json.fileName);
	}
}

function showMessage(data) {
	alert(data);
}
