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
var isShowDetail=false;
var tr;
define([ "v","vl" ], function(u,up) {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var biz = {
		init : function() {
			biz.event.initOrderData();
			biz.event.jsonGetDaHuoFiveNode();
			biz.event.initValidation();
			biz.event.initTrackes();
		},
		bind : function() {
			$("#yidongBtn").on("click", biz.event.jsonSaveTracke);
			$("#processBtn").on("click", biz.event.processOrder); //保存并提交
			$("#saveBtn").on("click", biz.event.saveOrder); //保存草稿
			$(".download").on("click", biz.event.download); //下载文件
			$(".table_input").on("keyup",biz.event.computeSum);//自动计算小计
			
		},
		event : {
			computeSum:function(){
				//编辑车缝表格 小计进行计算
				var $tr=$(this).parent().parent();
				var sewing_num= $(this).parent().parent().find(".sewing_sum");
				var sum=0;
				$tr.find(".table_input").each(function(){
					if(parseInt($(this).val()) && !isNaN(parseInt($(this).val()))){
						sum+=parseInt($(this).val());
					}
				});
				$(sewing_num).val(sum);
				//计算车缝产出数量总数
				var total=0;
				$(".sewing_sum").each(function(){
					total+=parseInt($(this).val());
				});
				$("#sewing_total").val(total);
			},
			initOrderData : function() {
				$("#orderId").val(orderId);
				var orderDetail = $(window.parent.document).find("#orderDetail").val(); //是否是查看订单详情
				$("#qcs").attr("fillName","oaOrderDetail.worker");
				if("true" != orderDetail && "6" == wfStepIndex) { //判断是否可编辑
//			  if(true){
					isShowDetail = true;//可编辑
				/*	$("#materialDescAddBtn").on("click", biz.event.materialAdd);
					$("#materialDescRemoveBtn").on("click", biz.event.materialRemove);*/
					$("#orderDetailDive").remove();
				}else{
				/*	$("#materialDescAddBtn,#materialDescRemoveBtn").remove();
					$("#costing tr:first th:first").remove();*/
					$("#processOrderDive").remove();
					$("#yidongMemo").remove();
					$("#otherFileUploadTd").remove();
					$("form input,textarea").prop("disabled", true);
					// update by 张华 2015-01-20
					$("#workerTd").html("制单:"+"<span elementFillName='oaOrderDetail.worker'/>");
				}
				parent.iFrameHeight("iframeTPE");
				opt = {
					formFill:true,
					formFillName:'fillName',
					elementText:true,
					elementFillName:'elementfillname',
					debug : false
				};
			},
			setCutNum:function(cutNumTitle,cutNumInfo){//初始化颜色数量模板
				var tr="<tr height='28'><th>色号&#92;码数</th>";
				//拼接颜色数量表头
				for(var i=0;i<cutNumTitle.length;i++){
					tr+="<th>"+cutNumTitle[i]+"</th>"
				}
				tr+="<th>小计</th></tr>";
				$("#cut_num_table").append(tr);
				for(var i=0;i<cutNumInfo.length;i++){
					var size=cutNumInfo[i].shearNum.split(",");
					var sum=0;
					tr="<tr height='25'><td>"+cutNumInfo[i].shearColor+"</td>";
					for(var j=0;j<size.length;j++ ){
						tr+="<td>"+size[j]+"</td>";
						if(!isNaN(parseInt(size[j]))){
							sum+=parseInt(size[j]);
						}
					}
					tr+="<td>"+sum+"</td></tr>";//计算小计的结果
					$("#cut_num_table").append(tr);
					parent.iFrameHeight("iframeTPE");
				}
			},
			jsonGetDaHuoFiveNode : function() { // 获取第五节点页面信息
				$.ajax({
                    url: '/bx/jsonGetDaHuoSixNode?oaOrderId='+orderId+ "&oaOrderNumId=" + $(window.parent.document).find("#orderSizeId").val() + "&wfStepIndex=" + wfStepIndex,
                    type: "post",
                    async: false,
                    success: function(data) {
                    	if("ajaxLogin" == data){
                			alert("登录超时，请重新登录");
                		} else if("0" ==  data.code){
                			jsonData=data;
                			
                			$("#oaTpe_id").val(data.sewingId);//将oaTpe的ID设置到隐藏域中
                			$("#sewing_total").val(data.sewingTotal);//车缝总数量
                			$("#sewing_factory").val(data.sewingFactory);//车缝工厂
                			biz.event.setCutNum(data.sizeTitle.split("-"),data.shearNum);//显示裁剪数量表格
                			if(undefined!=data.sizeTitle && null!=data.sizeTitle && undefined!=data.shearNum && null!=data.shearNum ){
                				biz.event.setSewingNum(data.sizeTitle.split("-"),data.sewingNum);//显示车缝数量表格
                			}
                			
                			if(undefined != data.oaOrderDetail && null != data.oaOrderDetail && undefined != data.oaOrderDetail.other_file && null != data.oaOrderDetail.other_file && "" != data.oaOrderDetail.other_file && "null" != data.oaOrderDetail.other_file) {
                				var downFile = "<a downloadurl='" +data.oaOrderDetail.other_file + "' class='z_title_sty1 mar_l10 download_a download' href='javascript:void(0)'>" + data.oaOrderDetail.other_file_name + "</a>";
        						$("#otherFile").html(downFile);
            				}

        					//进度信息超时
        					if(6 <= parseInt(wfStepIndex)) {
        						if(undefined == data.oaOrderDetail.wf_real_finish || null == data.oaOrderDetail.wf_real_finish || "" == data.oaOrderDetail.wf_real_finish) {
        							data.oaOrderDetail.schedule = "进行中";
        						}
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
                			$("#tabTPE").autoFill(data, opt);
                			if(!isShowDetail){
                				$("#qcs").attr("disabled","disabled");
                				$(".table_input").attr("disabled",true);
                				$(".sewing_sum").attr("disabled",true);
                			}
                			parent.iFrameHeight("iframeTPE");
                		} else {
                			alert(data.msg);
                		}
                    }
                });
			},
			setSewingNum:function(sizeTitle,shearNum){
				//拼接表头
				var tr="<tr height='28px'><th style='width:8%'>颜色&#92;码数</th>";
				for(var i=0;i<sizeTitle.length;i++){
					tr+="<th style='width:8%'>"+sizeTitle[i]+"</th>";
				}
				tr+="<th style='width:8%'>小计</th></tr>";
				$("#sewing_num_table").append(tr);
				//拼接表格的内容
				var sewing_total=0;
				for(var i=0;i<shearNum.length;i++){
					var size=shearNum[i].split("-");
					tr="<tr class='sewing_num_tr' height='28'>";
					var sum=0;
					for(var j=0;j<size.length;j++){
						if(j==0){
							tr+="<td style='width:8%' ><input class='table_input' style='width:90%' type='text' readonly value='"+size[j]+"'/></td>";
						}else{
							tr+="<td style='width:8%'><input style='width:90%'  class='table_input validate[required,custom[integer],maxSize[10]]' type='text' value='"+size[j]+"'/></td>";
						}
						if(j>0 && !isNaN(parseInt(size[j]))){
							sum+=parseInt(size[j]);
						}
					}
					sewing_total+=parseInt(sum);
					tr+="<td style='width:8%'><input style='width:90%'  class='sewing_sum' type='text' value='"+sum+"'/></td></tr>";
					$("#sewing_num_table").append(tr);
					parent.iFrameHeight("iframeTPE");
				}
				if(null==$("#sewing_total").val() || undefined==$("#sewing_total").val() || "" == $("#sewing_total").val() || 0==$("#sewing_total").val()){
					$("#sewing_total").val(sewing_total);
				}
			},
			initValidation:function(){
				$("#tabTPE").validationEngine('attach',{
					scroll:true,
					autoHidePrompt:true,
					autoHideDelay:1500,
					showOneMessage: true,
					fadeDuration : 0.3,
					promptPosition : "bottomLeft"
				});
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
	                		} else {
	                			alert(data.msg);
	                		}
	                    }
	                });
				} else {
					alert("发布异动信息不能为空");
				}
			},
			initTrackes : function(){
				if(jsonData){
					$.each(jsonData.oaTrackes,function(i,v){
						biz.event.putOaTrackesTrData(v.memo, v.user, v.time);
					})
				}
			},
			putOaTrackesTrData : function(td1, td2, td3) { //添加一行异动信息到列表
				var tr = "<tr><td width='53px' height='22px'></td><td width='573px'>" + td1 + "</td><td>" + td2 + "/" + td3 + "</td></tr>";
				$("#oaTrackesTable").append(tr);
				parent.iFrameHeight("iframeTPE");
			},
			download : function() { //下载文件
				window.location.href = "/oaOrderFileDownload?fileUrl=" + encodeURI($(this).attr("downloadUrl"));
			},
			processOrder : function() { //保存并提交订单
				$("#processOrder").val("true"); //true标识流程流转
				biz.event.saveCutNum();//将车缝数据进行拼接
				biz.event.jsonProcessOrder();
			},
			saveOrder : function() { //保存草稿
				$("#processOrder").val("false"); //false标识流程不流转，只保存数据
				biz.event.saveCutNum();//将车缝数据进行拼接
				biz.event.jsonProcessOrder();
			},
			saveCutNum:function(){//将车封数据拼接保存
				var sewing_num="";
				$(".sewing_num_tr").each(function(i,n){
					sewing_num+=",";
					$(this).find(".table_input").each(function(){
						sewing_num+=$(this).val()+"-";
					})
					sewing_num=sewing_num.substring(0,sewing_num.length-1);
				});
				$("#sewing_num").val(sewing_num.substring(1,sewing_num.length));
			},
			jsonProcessOrder:function(){//ajax提交表单
				//先要对所填写的数据进行验证合法后才进行提交
				var successFlag=$("#tabTPE").validationEngine("validate");
				if(successFlag){
					$("#processBtn").attr("disabled", "true");
					$("#saveBtn").attr("disabled", "true");
					$("#backBtn").attr("disabled", "true");
					var params = $("#tabTPE").serializeArray();
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
