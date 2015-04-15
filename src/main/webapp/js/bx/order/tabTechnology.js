requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
		"u" : "util",
		"up" : "uploadPreview.min",
		"v":"jqueryValidation/js/jquery.validationEngine.min",
		"vl":"jqueryValidation/js/jquery.validationEngine-zh_CN"
	}
});
var sizeDetailTr,sizeTitle,width,detailUpdateFlag = false,picCount = 0,materialDescTr="",upImgHeight = 0;
var activeIndex = "3"; //默认为大货节点
var wfStepIndex; //订单节点index
var escapeMinus = "\\u002d"; //减号转义符
var escapeComma = "\\u002c"; //逗号转义符
var minusReg = /\\u002d/g; //减号转义符
var commaReg = /\\u002c/g; //逗号转义符
define([ "u","up","v","vl"  ], function(u,up) {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var biz = {
		init : function() {
			biz.event.initOrderData();
			biz.event.jsonGetThirdNode();
			biz.event.setInputDisable();
			biz.event.initValidation();
		},
		bind : function() {
			$("#materialDescRemoveBtn").on("click", biz.event.materialDescRemove);
			$("#materialDescAddBtn").on("click", biz.event.materialDescAdd);
			$("#sizeDetailRemoveBtn").on("click", biz.event.sizeDetailRemove);
			$("#sizeDetailAddBtn").on("click", biz.event.sizeDetailAdd);
			$("#oaClothesSizeType").on("change", biz.event.jsonGetSizeBySizeType);
			biz.event.bindUploadPic(1);
			biz.event.bindUploadPic(2);
			$("#yidongBtn").on("click", biz.event.jsonSaveTracke);
			$("#processBtn").on("click", biz.event.processOrder); //保存并提交
			$("#saveBtn").on("click", biz.event.saveOrder); //保存草稿
			$("#exportBtn").on("click", biz.event.exportExcel); //导出订单Excel
			$(".download").on("click", biz.event.download); //下载文件
		},
		event : {
			initValidation:function(){
				$("#tabTechnology").validationEngine('attach',{
					scroll:true,
					autoHidePrompt:true,
					autoHideDelay:1500,
					showOneMessage: true,
					fadeDuration : 0.3,
					promptPosition : "bottomLeft"
				});
			},
			initOrderData : function() { // 初始化表单数据，从父窗口中获取
				//默认初始值
				$("#cutArt").val("1：面料蒸汽过防缩，提前24小时松布放缩；\n2：面料检查，不能有破洞、疵点、色差、污迹、纬斜现象；\n3：裁前请检查样版，工艺单，唛架一致；裁片剪口位置准确，上下层不能偏刀；");
				$("#tailButton").val("1：\n2：线头清剪干净，保持成品清洁，不可有粉印，污迹；");
				$("#tailIroning").val("整件整烫平服，成品不可反光，起镜；");
				$("#tailPackaging").val("整烫2小时后才能包装；折叠整齐，一件入一胶袋（放拷贝纸），分色分码；注：饰品单独包装，领花，金属扣，拉链头等请用拷贝纸包住；");
				
				$("#sampleSize").val($(window.parent.document).find("#sampleSize").val()); //从订单信息带过来
				$("#orderId").val($(window.parent.document).find("#orderId").val());
				//判断是否可以编辑
				var orderDetail = $(window.parent.document).find("#orderDetail").val(); //是否是查看订单详情
				wfStepIndex = $(window.parent.document).find("#wfStepIndex").val(); //订单节点index
				var orderType = $(window.parent.document).find("#orderTypeNum").val(); //订单类型
				if("2" == orderType) {
					activeIndex = "4"; //样衣打版节点
				} else if("3" != orderType) { //既不是样衣也不是大货节点
					orderDetail = "true";
				}
				if("true" != orderDetail && activeIndex == wfStepIndex) { //判断是否可编辑
					detailUpdateFlag = true;
				}
				if(parseInt(wfStepIndex) < parseInt(activeIndex)) {
					$("#exportBtn").remove();
				}
			},
			jsonGetThirdNode : function() { // 获取第三节点页面信息
				$.ajax({
                    url: "/bx/jsonGetThirdNode?oaOrder.id=" + $(window.parent.document).find("#orderId").val() + "&oaOrder.oaOrderNumId=" + $(window.parent.document).find("#orderSizeId").val() + "&oaOrder.type=" + $(window.parent.document).find("#orderTypeNum").val() + "&wfStepIndex=" + wfStepIndex,
                    type: "post",
                    async: false,
                    success: function(data) {
                    	if("ajaxLogin" == data){
                			alert("登录超时，请重新登录");
                		} else if("0" ==  data.code){
                			biz.event.setMaterialDescTableData(data.materialDescList); //设置用料说明列表
                			biz.event.setClothesSizeTableData(data.oaClothesSize, data.oaClothesSizeDetails, data.oaOrderNum); //设置尺码信息
                			biz.event.setProcessExplainData(data.processExplain); //设置加工信息
                			biz.event.setManageInfoData(data.oaOrderDetail); //设置管理信息
                			biz.event.setOaTrackesTableData(data.oaTrackes); //设置异动信息列表
                		} else {
                			alert(data.msg);
                		}
                    }
                });
			},
			setMaterialDescTableData : function(materialDescList) { //填充用料说明
				var tr = "";
				// 查询到的数据填充到表格中
				$.each(materialDescList, function(i, v) {
					tr = "<tr class='material_desc_tr'><td width='20'><input type='checkbox' class='material_desc_td'><input name='id' type='hidden' class='table_text' value='" + v.id + "'><input name='id' type='hidden' class='table_text1' value='" + v.oa_material_list + "'></td><td><input name='materialProp' maxlength='20' type='text' class='table_text1 validate[maxSize[20]]' value='" + v.material_prop + "'></td>";
					tr += "<td><input name='materialName' maxlength='20' type='text' class='table_text1 validate[maxSize[20]]' value='" + v.material_name + "'></td>";
					if("辅料" == v.type) {
						tr += "<td><select name='type' class='table_text1'><option value='主料'>主料</option><option value='辅料' selected>辅料</option></select></td>";
					} else {
						tr += "<td><select name='type' class='table_text1'><option value='主料'>主料</option><option value='辅料'>辅料</option></select></td>";
					}
					tr += "<td><input name='color' maxlength='20' type='text' class='table_text1 validate[maxSize[20]]' value='" + v.color + "'></td>";
					tr += "<td><input name='oaMaterialList' type='hidden' class='table_text' value='" + v.oa_material_list + "'><input maxlength='10' name='buffon' type='text' class='table_text validate[maxSize[10],custom[number2]]' value='" + v.buffon + "'></td>";
					tr += "<td><input maxlength='10' name='unitNum' type='text' class='table_text validate[maxSize[10],custom[number2]]' value='" + v.unit_num + "'></td>";
					tr += "<td><input maxlength='100' name='position' type='text' class='table_text validate[maxSize[100]]' value='" + v.position + "'></td>";
//					tr += "<td><input maxlength='500' name='itMemo' type='text' class='table_text validate[maxSize[500]]' value='" + v.it_memo + "'></td>";
					tr += "</tr>";
					$("#materialDescTable").append(tr);
				});
				parent.iFrameHeight("iframeTechnology");
				materialDescTr = "<tr class='material_desc_tr'><td width='20'><input type='checkbox' class='material_desc_td'></td><td><input name='materialProp' maxlength='20' type='text' class='table_text1 validate[maxSize[20]]' value=''></td>";
				materialDescTr += "<td><input name='materialName' maxlength='20' type='text' class='table_text1  validate[maxSize[20]]' value=''></td>";
				materialDescTr += "<td><select name='type' class='table_text1'><option value='主料'>主料</option><option value='辅料'>辅料</option></select></td>";
				materialDescTr += "<td><input name='color' maxlength='20' type='text' class='table_text1  validate[maxSize[20]]' value=''></td>";
				materialDescTr += "<td><input maxlength='10' name='buffon' type='text' class='table_text validate[maxSize[10],custom[number2]]' value=''></td>";
				materialDescTr += "<td><input maxlength='10' name='unitNum' type='text' class='table_text validate[maxSize[10],custom[number2]]' value=''></td>";
				materialDescTr += "<td><input maxlength='100' name='position' type='text' class='table_text validate[maxSize[100]]' value=''></td>";
//				materialDescTr += "<td><input maxlength='500' name='itMemo' type='text' class='table_text validate[maxSize[500]]' value=''></td>";
				materialDescTr += "</tr>";
			},
			setClothesSizeTableData : function(oaClothesSize, oaClothesSizeDetails, oaOrderNum) { //设置尺码表内容
				//成品尺寸
				if (null != oaClothesSize && undefined != oaClothesSize) {
					$("#oaClothesSizeId").val(oaClothesSize.id);
					$("#oaClothesSizeType").val(oaClothesSize.type);
					$("#oaClothesSizeUnit").val(oaClothesSize.unit);
				}
				//成品尺寸列表表头拼接
				biz.event.setClothesSizeTableTitle(oaOrderNum);
				//成品尺寸列表内容设置
				biz.event.setClothesSizeTableList(oaClothesSizeDetails);
			},
			setClothesSizeTableTitle : function(oaOrderNum) { //成品尺寸列表表头拼接
				var title1 = ["部位名称与度法"];
				var title2 = ["基码纸样尺寸","公差(±)"];
				sizeTitle = oaOrderNum.title.split("-");
				var tr = "<tr height='28'><th width='3%'>&nbsp;</th>";
				var count = title1.length + sizeTitle.length + title2.length;
				width = 97 / count;
				for(var i = 0; i < title1.length; i++) {
					tr += "<th style='width:" + width + "%'>" + title1[i] + "</th>";
					count ++;
				}
				for(var i = 0; i < sizeTitle.length; i++) {
					tr += "<th style='width:" + width + "%'>" + sizeTitle[i] + "</th>";
					count ++;
				}
				for(var i = 0; i < title2.length; i++) {
					tr += "<th style='width:" + width + "%'>" + title2[i] + "</th>";
					count ++;
				}
				tr += "</tr>";
				$("#clothesSizeTable").append(tr);
			},
			setClothesSizeTableList : function(oaClothesSizeDetails) { //成品尺寸列表内容设置
				var tr = "";
				// 查询到的数据填充到表格中
				if(null != oaClothesSizeDetails && oaClothesSizeDetails.length > 0) {
					$.each(oaClothesSizeDetails, function(i, v) {
						tr = "<tr height='25' class='size_detail_tr'><td width='20'><input type='checkbox' class='size_detail_td'><input name='id' type='hidden' class='table_text' value='" + v.id + "'><input name='clothSize' type='hidden' class='table_text detail_cloth_size' value='" + v.clothSize + "'></td>";
						tr += "<td><input maxlength='50' name='position' type='text' class='table_text size_detail_position validate[maxSize[50]]' value='" + v.position + "'></td>";
						var clothSize = v.clothSize.split("-");
						for (var i = 0; i < clothSize.length; i++) {
							tr += "<td><input type='text' maxlength='10' class='table_text clothes_size_detail validate[maxSize[10],custom[number2]]' value='" + clothSize[i] + "'></td>";
						}
						tr += "<td><input maxlength='20' name='samplePageSize' type='text' class='table_text validate[maxSize[20]]' value='" + v.samplePageSize + "'></td>";
						tr += "<td><input maxlength='10' name='tolerance' type='text' class='table_text size_detail_tolerance validate[maxSize[10],custom[number2]]' value='" + v.tolerance + "'></td>";
						tr += "</tr>";
						$("#clothesSizeTable").append(tr);
					});
				} else {
					if(detailUpdateFlag) {
						biz.event.jsonGetSizeBySizeType();
					}
				}
				parent.iFrameHeight("iframeTechnology");
				//拼接新的tr
				sizeDetailTr = "<tr height='25' class='size_detail_tr'><td width='20'><input type='checkbox' class='size_detail_td'><input name='clothSize' type='hidden' class='table_text detail_cloth_size'></td>";
				sizeDetailTr += "<td><input maxlength='50' name='position' type='text' class='table_text size_detail_position validate[maxSize[50]]'></td>";
				if(undefined != sizeTitle && null != sizeTitle) {
					for (var i = 0; i < sizeTitle.length; i++) {
						sizeDetailTr += "<td><input type='text' maxlength='10' class='table_text clothes_size_detail validate[maxSize[10],custom[number2]]'></td>";
					}
				}
				sizeDetailTr += "<td><input maxlength='20' name='samplePageSize' type='text' class='table_text validate[maxSize[20]]'></td>";
				sizeDetailTr += "<td><input maxlength='10' name='tolerance' type='text' class='table_text size_detail_tolerance validate[maxSize[10],custom[number2]]'></td>";
				sizeDetailTr += "</tr>";
			},
			sizeDetailRemove : function() { //成品尺寸表删除行按钮操作
				if ($(".size_detail_td:checked").length <= 0) {
					alert("请勾选要删除的行");
				} else {
					if(confirm("确认要删除吗？")) {
						$(".size_detail_td:checked").each(function(i,n){
							var id = $(this).parent().find("input[name=id]").val();
							if(undefined != id && null != id && id.length > 0) {
								$("#clothesSizeDetailDelIds").val($("#clothesSizeDetailDelIds").val() + "," + id);
							}
							$(this).parent().parent().remove();
							parent.iFrameHeight("iframeTechnology");
						});
					}
				}
			},
			sizeDetailAdd : function() { //成品尺寸表增加行按钮操作
				if(biz.event.ifAllowAdd("clothesSizeTable", 99)) {
					$("#clothesSizeTable").append(sizeDetailTr);
					parent.iFrameHeight("iframeTechnology");
				}
			},
			materialDescRemove : function() { //用料说明删除行按钮操作
				if ($(".material_desc_td:checked").length <= 0) {
					alert("请勾选要删除的行");
				} else {
					if(confirm("确认要删除吗？")) {
						$(".material_desc_td:checked").each(function(i,n){
							var id = $(this).parent().find(".table_text,name:id").val(); //查找大货或打版id
							if(undefined != id && null != id && id.length > 0) {
								$("#materialDescDelIds").val($("#materialDescDelIds").val() + "," + id);
							}
							id = $(this).parent().find(".table_text1,name:id").val(); //查找用料搭配id
							if(undefined != id && null != id && id.length > 0) {
								$("#materialDelIds").val($("#materialDelIds").val() + "," + id);
							}
							$(this).parent().parent().remove();
							parent.iFrameHeight("iframeTechnology");
						});
					}
				}
			},
			materialDescAdd : function() { //用料说明增加行按钮操作
				if(biz.event.ifAllowAdd("materialDescTable", 99)) {
					$("#materialDescTable").append(materialDescTr);
					parent.iFrameHeight("iframeTechnology");
				}
			},
			jsonGetSizeBySizeType : function() { //根据款式分类选择，设置部位和公差
				$.ajax({
                    url: "/bx/getClothesPositionAndTolerance?position=" + $("#oaClothesSizeType").val(),
                    type: "post",
                    success: function(data) {
                    	if("ajaxLogin" == data){
                			alert("登录超时，请重新登录");
                		} else if("0" ==  data.code){
                			biz.event.putPositionAndTolerance(data); //设置默认部位和公差到列表
                		} else {
                			alert(data.msg);
                		}
                    }
                });
			},
			putPositionAndTolerance : function(data) { //设置默认部位和公差到列表
				var position = data.position.split(",");
				var tolerance = data.tolerance.split(",");
				var count = position.length - $("#clothesSizeTable tr").length + 1;
				if(count > 0) {
					for(var i = 0; i < count; i++) {
						biz.event.sizeDetailAdd();
					}
				}
				$.each($("#clothesSizeTable tr"),function(i,n){
					if(i > 0) {
						$(n).children("td:eq(1)").children().val(position[i - 1]);
						$(n).children("td:last").children().val(tolerance[i - 1]);
					}
				});
				parent.iFrameHeight("iframeTechnology");
			},
			setProcessExplainData : function(processExplain) { //填充加工说明
				if(null != processExplain) {
					$("#oaProcessExplainId").val(processExplain.id);
					$("#specialArt").val(processExplain.specialArt);
					$("#cutArt").val(processExplain.cutArt);
					$("#sewing").val(processExplain.sewing);
					$("#tailButton").val(processExplain.tailButton);
					$("#tailIroning").val(processExplain.tailIroning);
					$("#tailCard").val(processExplain.tailCard);
					$("#tailPackaging").val(processExplain.tailPackaging);
					//度量图
					if(null!=processExplain.measurePic && undefined!=processExplain.measurePic && "" != processExplain.measurePic) {
						$("#picImg1").attr("src", processExplain.measurePic);
						$("#picFileHid1").val(processExplain.measurePic);
					} else {
						$("#picImg1").attr("src", "../../images/nopic.gif");
					}
					if(null!=processExplain.measurePic && undefined!=processExplain.sewingPic && "" != processExplain.sewingPic) {
						$("#picImg2").attr("src",processExplain.sewingPic);
						$("#picFileHid2").val(processExplain.sewingPic);
					} else {
						$("#picImg2").attr("src", "../../images/nopic.gif");
					}
					//车缝图
					//图片信息列表
					/*if(null != processExplain.picInfo && processExplain.picInfo.length > 0) {
						var picList = processExplain.picInfo.split(",");
						$.each(picList,function(i,n){ //循环填充图片到列表中
							biz.event.picListAdd(n);
						});
					} else { //默认添加一行图片
						biz.event.picListAdd();
					}*/
				} else {
					$("#picImg1").attr("src", "../../images/nopic.gif");
					$("#picImg2").attr("src", "../../images/nopic.gif");
				}
				parent.iFrameHeight("iframeTechnology");
			},
			/*picListRemove : function() { //车缝说明图片删除行按钮操作
				if ($(".pic_list_td:checked").length <= 0) {
					alert("请勾选要删除的行");
				} else {
					if(confirm("确认要删除吗？")) {
						$(".pic_list_td:checked").each(function(i,n){
							$(this).parent().parent().remove();
							biz.event.minusIframeHeight(181);
						});
					}
				}
			},*/
			/*picListAdd : function(picInfo) { //车缝说明图片添加行按钮操作
				if(biz.event.ifAllowAdd("picListTable", 15)) {
					picCount ++;
					var picInfos;
					var picUrl = "../../images/nopic.gif", picMemo = "";
					var picUrlHid = "";
					if(undefined != picInfo && null != picInfo) {
						picInfos = picInfo.split("-");
						picUrl = picInfos[0].replace(minusReg, "-");
						picMemo = picInfos[1].replace(minusReg, "-");
						
						picUrl = picUrl.replace(commaReg, ",");
						picMemo = picMemo.replace(commaReg, ",");
						if(picUrl.length <= 0) {
							picUrl = "../../images/nopic.gif";
						} else {
							picUrlHid = picUrl;
						}
					}
					var picListTr = "<tr trIndex='" + picCount + "'>";
					picListTr += "<td width='20'><input type='checkbox' class='pic_list_td'></td>";
					picListTr += "<td width='180'><div style='width:150px; height:150px; border:1px solid rgb(205, 205, 205);padding: 2px;'><img id='picImg" + picCount + "' width='150' height='150' src='" + picUrl + "'></div>";
					picListTr += "<input type='file' fileIndex='" + picCount + "' id='picFile" + picCount + "' name='file' onchange='jsonUploadPic(this);'><input type='hidden' id='picFileHid" + picCount + "' value='" + picUrlHid + "'></td>";
					picListTr += "<td><textarea maxlength='100' style='width: 100%; height: 175px;' id='picMemo" + picCount + "'>" + picMemo + "</textarea></td></tr>";
					$("#picListTable").append(picListTr);
					biz.event.bindUploadPic(picCount);
					biz.event.addIframeHeight(181);
					$("#picFile"+picCount).change(function(){
						var opt = {fileTypes:['.gif','.jpg','.jpeg','.png','.bmp'],fileSize:1024*2};
						if(!$("#picFile"+picCount).fileValidation(opt)){
							$("#picImg"+picCount).attr("src","../../images/nopic.gif");
						}
					});
				}
			},*/
			ifAllowAdd : function(tableName, trTotal) {
				if($("#" + tableName + " tr").length >= (trTotal + 1)) { //+1为了排除表头
					alert("增加最多不能超过" + trTotal + "行");
					return false;
				}
				return true;
			},
			bindUploadPic : function(picCount) {
				$("#picFile" + picCount).uploadPreview({
					Img : "picImg" + picCount,
					Width : 150,
					Height : 150
				});
				$("#picFile"+picCount).change(function(){
					var opt = {fileTypes:['.gif','.jpg','.jpeg','.png','.bmp'],fileSize:1024*2};
					if(!$("#picFile"+picCount).fileValidation(opt)){
						$("#picImg"+picCount).attr("src","../../images/nopic.gif");
						return;
					}
					jsonUploadPic(this);
				});
			},
			setManageInfoData : function(orderDetail) { //设置管理信息
				if(undefined != orderDetail && null != orderDetail) {
					$("#detailOperator").text(orderDetail.operator);
					$("#detailDate").text(orderDetail.processTime);
					$("#detailContent").text(orderDetail.content);
					$("#detailAttachment").text(orderDetail.other_file);
					$("#wfStep").val(orderDetail.wf_step);
					//进度信息超时
					if(parseInt(activeIndex) <= parseInt(wfStepIndex)) {
						if(undefined == orderDetail.wf_real_finish || null == orderDetail.wf_real_finish || "" == orderDetail.wf_real_finish) {
							orderDetail.schedule = "进行中";
						}
					}

					if(undefined != orderDetail.schedule && null != orderDetail.schedule) {
						if(orderDetail.schedule.indexOf("超时") >= 0) {
							$("#detailSchedule").css("background-color", "red");
							$("#detailSchedule").css("color", "white");
						} else if(orderDetail.schedule.indexOf("提前") >= 0) {
							$("#detailSchedule").css("background-color", "green");
							$("#detailSchedule").css("color", "white");
						}
						$("#detailSchedule").text(orderDetail.schedule);
					}

					if(undefined != orderDetail.wf_real_start && null != orderDetail.wf_real_start) {
						$("#detailRealStart").text("流入时间:" + orderDetail.wf_real_start);
					}
					if(undefined != orderDetail.wf_real_finish && null != orderDetail.wf_real_finish) {
						$("#detailRealFinish").text("实际完成:" + orderDetail.wf_real_finish);
					}
					if(undefined != orderDetail.step_start_time_consume && null != orderDetail.step_start_time_consume && "" != orderDetail.step_start_time_consume) {
						var rate=parseFloat(orderDetail.step_start_time_consume);
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
						$("#detailRealStartColor").text(orderDetail.step_start_time_consume + "%");
					}
					if(undefined != orderDetail.step_finish_time_consume && null != orderDetail.step_finish_time_consume && "" != orderDetail.step_finish_time_consume) {
						var rate=parseFloat(orderDetail.step_finish_time_consume);
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
						$("#detailRealFinishColor").text(orderDetail.step_finish_time_consume + "%");
					}
					if(undefined != orderDetail.wf_step_duration && null != orderDetail.wf_step_duration) {
						$("#detailDuration").text("标准工时:" + orderDetail.wf_step_duration);
					}
					if(undefined != orderDetail.real_time && null != orderDetail.real_time) {
						$("#detailRealTime").text("实际耗时:" + orderDetail.real_time);
					}
					//下载文件设置
					if(undefined != orderDetail.other_file && null != orderDetail.other_file && "" != orderDetail.other_file && "null" != orderDetail.other_file) {
						var downFile = "<a downloadurl='" + orderDetail.other_file + "' class='z_title_sty1 mar_l10 download_a download' href='javascript:void(0)'>" + orderDetail.other_file_name + "</a>";
						$("#otherFile").html(downFile);
						$("#hid_attachment_url").val(orderDetail.other_file);
					}
				}
			},
			setOaTrackesTableData : function(oaTrackes) { //设置异动信息列表
				if(null != oaTrackes && undefined != oaTrackes && oaTrackes.length > 0) {
					$.each(oaTrackes,function(i,n){
						biz.event.putOaTrackesTrData(n.memo, n.user, n.time);
					});
				}
			},
			putOaTrackesTrData : function(td1, td2, td3) { //添加一行异动信息到列表
				var tr = "<tr><td width='53px' height='22px'></td>";
				tr += "<td width='573px'>" + td1 + "</td>";
				tr += "<td>" + td2 + "/" + td3 + "</td></tr>";
				$("#oaTrackesTable").append(tr);
				parent.iFrameHeight("iframeTechnology");
			},
			resetFormElement : function() { //重设表单中input的name值和value拼接
				var orderType = $(window.parent.document).find("#orderTypeNum").val(); //订单类型
				//用料说明
				var materialDesc = "oaDaBanInfos"; //默认为打版对象
				if("3" == orderType) {
					materialDesc = "oaDaHuoInfos"; //类型为大货，则为大货对象
				}
				$(".material_desc_tr").each(function(i,n){
					$(this).find(".table_text").each(function(j,k){
						var name = $(this).attr("name");
						if(!biz.event.ifResetElementName(name, materialDesc + "[")) { //判断是否需要重设元素name
							name = materialDesc + "[" + i + "]." + name;
							$(this).attr("name", name);
						}
					});
					//用料表修改
					$(this).find(".table_text1").each(function(j,k){
						var name = $(this).attr("name");
						if(!biz.event.ifResetElementName(name, "oaMaterialLists[")) { //判断是否需要重设元素name
							name = "oaMaterialLists[" + i + "]." + name;
							$(this).attr("name", name);
						}
					});
				});
				//成品尺寸表
				$(".size_detail_tr").each(function(i,n){
					var clothSize = "";
					$(this).find(".clothes_size_detail").each(function(j,k){ //拼接尺码
						clothSize += $(this).val() + "-";
					});
					if(clothSize.length > 0) { //设置尺码到隐藏text中
						clothSize = clothSize.substring(0, clothSize.length - 1);
						$(this).find('.detail_cloth_size').val(clothSize);
					}
					$(this).find(".table_text").not(".clothes_size_detail").each(function(j,k){
						var name = $(this).attr("name");
						if(!biz.event.ifResetElementName(name, "oaClothesSizeDetails[")) { //判断是否需要重设元素name
							name = "oaClothesSizeDetails[" + i + "]." + name;
							$(this).attr("name", name);
						}
					});
				});
				//加工说明
//				var picInfo = "";
//				$.each($("#picListTable tr"),function(i,n){
//					var trIndex = $(n).attr("trIndex"); //tr的index
//					var pic = $("#picFileHid" + trIndex).val();
//					var picMemo = $("#picMemo" + trIndex).val();
//					if($.trim(pic).length >0 || $.trim(picMemo).length > 0) { //判断图片和图片备注其中一项不为空就进行拼接
//						pic = pic.replace(/-/g, escapeMinus);
//						pic = pic.replace(/,/g, escapeComma);
//						picMemo = picMemo.replace(/-/g, escapeMinus);
//						picMemo = picMemo.replace(/,/g, escapeComma);
//						if("" != picInfo) {
//							picInfo += ",";
//						}
//						picInfo += pic + "-" + picMemo;
//					}
//				});
//				$("#picInfo").val(picInfo);
			},
			ifResetElementName : function(name, str) { //判断是否已经对元素名称重新设置
				if(name.indexOf(str) < 0) {
					return false;
				} else {
					return true;
				}
			},
			processOrder : function() { //保存并提交订单
				$("#processOrder").val("true"); //true标识流程流转
				biz.event.resetFormElement();
				biz.event.jsonProcessOrder();
			},
			saveOrder : function() { //保存草稿
				$("#processOrder").val("false"); //false标识流程不流转，只保存数据
				biz.event.resetFormElement();
				biz.event.jsonProcessOrder();
			},
			jsonProcessOrder : function() { //ajax提交订单
				var successFlag=$("#tabTechnology").validationEngine("validate");
				if(successFlag){
					$("#processBtn").attr("disabled", "true");
					$("#saveBtn").attr("disabled", "true");
					$("#backBtn").attr("disabled", "true");
					var params = $("#tabTechnology").serializeArray();
					$.ajax({
	                    url: "/bx/jsonProcessOrder",
	                    type: "post",
	                    data: params,
	                    success: function(data) {
	                    	if("ajaxLogin" == data){
	                			alert("登录超时，请重新登录");
	                		} else if("0" ==  data.code){
                                if( "true" == $("#processOrder").val() ){
                                    top.location.href = "/bx/todo";
                                }else {
                                    top.location.reload(true);
                                }
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
			jsonSaveTracke : function() { //保存异动信息
				if($.trim($("#yidongContent").val()) != "") {
					var params = {"oaTracke.memo" : $("#yidongContent").val(), "oaTracke.oaOrder" : $(window.parent.document).find("#orderId").val(), "oaTracke.node" : $(window.parent.document).find("#wfStep").val()};
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
			setInputDisable : function() { //如果为查看详情，更改text属性为disable
				if(!detailUpdateFlag) {
					$("input").attr("readonly", "true");
					$("input[type=checkbox]").attr("disabled", "true");
					$("textarea").attr("readonly", "true");
					$("select").attr("disabled", "true");
					$("#sizeDetailAddBtn").remove();
					$("#sizeDetailRemoveBtn").remove();
					$("#materialDescAddBtn").remove();
					$("#materialDescRemoveBtn").remove();
					$("#yidongMemo").remove();
					$("#otherFileUploadTd").text("");
					$("#processOrderDive").hide();
					$("#orderDetailDive").show();
					parent.iFrameHeight("iframeTechnology");
				}
			},
			exportExcel : function() { //导出excel操作
				$("#exportBtn").attr("disabled", "true");
				$("#exportBtn").val("30秒后再次点击");
				top.location.href = "/bx/downOAOrder?oaOrder.id=" + $("#orderId").val() + "&node=" + activeIndex;
				setTimeout(function(){
					$("#exportBtn").removeAttr("disabled");
					$("#exportBtn").val("导出Excel");
				},30000);
			},
			download : function() { //下载文件
				window.location.href = "/oaOrderFileDownload?fileUrl=" + encodeURI($(this).attr("downloadUrl"));
			}
		}
	}
	return fn;
});

//json上传图片
function jsonUploadPic(obj) {
	var fileIndex = $(obj).attr("fileIndex");
	var formdata = new FormData();
	formdata.append("file", $(obj)[0].files[0]);
	$.ajax({
		type : 'POST',
		url : '/bx/jsonUploadPic',
		data : formdata,
		//必须false才会自动加上正确的Content-Type  
		//必须false才会避开jQuery对 formdata 的默认处理                  
		//XMLHttpRequest会对 formdata 进行正确的处理                   
		contentType : false,
		processData : false
	}).then(function(data) {
		$("#picFileHid" + fileIndex).val(data);
		parent.iFrameHeight("iframeTechnology");
	}, function() {
		//failCal
	});
}

//文件上传回调函数
function uploadComplete(data) {
	data = data
			.replace(
					'<meta http-equiv="content-type" content="text/html; charset=UTF-8">',
					"");
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