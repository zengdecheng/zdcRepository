requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
		"v" : "jqueryValidation/js/jquery.validationEngine.min",
		"vl" : "jqueryValidation/js/jquery.validationEngine-zh_CN"
	}
});
var wfStepIndex = $(window.parent.document).find("#wfStepIndex").val();// 订单所在节点
var orderId = $(window.parent.document).find("#orderId").val(); // 订单ID
var index; // 当前所在index
var jsonData; // 后台传的数据
var isEdit = false; // 是否查看详情
var qiTaoTemplet; // 齐套模板
define([ "v", "vl" ], function(v, vl) {
	var fn = {
		init : function() {
			biz.init();
			biz.bind();
		}
	};
	var biz = {
		init : function() {
			biz.event.jsonGetFiveNode();
			biz.event.initOrderData();
			biz.event.initTrackes();
			biz.event.initValidation();
			biz.event.initNumDisplay();
		},
		bind : function() {
			$("#yidongBtn").on("click", biz.event.jsonSaveTracke);
			$("#processBtn").on("click", biz.event.processOrder); // 保存并提交
			$("#saveBtn").on("click", biz.event.saveOrder); // 保存草稿
			$(".download").on("click", biz.event.download); // 下载文件
			$("#exportBtn").on("click", biz.event.exportExcel); // 导出订单Excel
			$(".applyUnitNum").on("keyup", biz.event.calcNeedNum); // 需求数量计算
			$(".shearNum").on("keyup", biz.event.calcShearNum); // 实际总裁数计算
			$(".receiveNum").on("change", biz.event.calcKongCha); // 实到差数计算
			$(".receiveNum").on("keydown",biz.event.calReceiveNum);//计算验布实收数量
			$(".receiveNum").on("keydown", biz.event.calcLoss); // 衣友布损、公司损耗计算
			$(".applyUnitNum,.receiveNum,.lossBundles,.lossOther,.lossOddments").on("keyup", biz.event.calcLoss); // 衣友布损、公司损耗计算
			if (!isEdit) {
				$("#qiTaoAdd").on("click", biz.event.qiTaoAdd);
				$("#qiTaoRemove").on("click", biz.event.qiTaoRemove);
			} else {
				$("#qiTaoAdd,#qiTaoRemove").remove();
				$("#common").attr("disabled", "disabled");
				$("#yidongMemo").remove();
				$("#otherFileUploadTd").remove();
				parent.iFrameHeight("iframeCQC");
			}
		},
		event : {
			initNumDisplay:function(){
				$(".materialReceive").each(function(index, element) {
					if($(element).text()!=""){
						$(element).text(parseFloat($(element).text()).toFixed(2));
					}
				});
				$(".shearDivl").each(function(index,data){
					if($(data).text()!=""){
						$(data).text(parseFloat($(data).text()).toFixed(2));
					}
				});
			},
			calReceiveNum:function(e){
				var key=e.which;
				if(key==13){
					var index = $(this).parent().parent().index() - 1;
					// 得到当前验布实收数量的值
					var receiveNum = $(".receiveNum:eq(" + index + ")").val();
					$(".receiveNum:eq(" + index + ")").val(eval(receiveNum));
					var $this =$(".receiveNum:eq(" + index + ")") 
					if ($this.val() == "") {
						$this.parent().parent().find(".materialReceive").html("");
					} else {
						if (!$this.validationEngine("validate")) {
							var idx = $this.parent().parent().index() - 1;
							var t = parseFloat(eval($this.val())) - jsonData.materialPurchaseDescList[idx].num * jsonData.materialPurchaseDescList[idx].deviation / 100;
							$this.parent().parent().find(".materialReceive").html(t.toFixed(2));
						}
					}
				}
			},
			initValidation : function() {
				$("#tabCQC").validationEngine('attach', {
					scroll : true,
					autoHidePrompt : true,
					autoHideDelay : 1500,
					showOneMessage : true,
					fadeDuration : 0.3,
					promptPosition : "bottomLeft"
				});
			},
			jsonGetFiveNode : function() { // 获取第四节点页面信息
				$.ajax({
					url : '/bx/jsonGetFiveNode?orderId=' + orderId + '&wfStepIndex=' + wfStepIndex + '&cqcNewdate=' + new Date().getTime(),
					type : "post",
					async : false,
					success : function(data) {
						data = eval("("+data+")");
						if("ajaxLogin" == data){
                			alert("登录超时，请重新登录");
                		} else if ("0" == data.code) {
							jsonData = data;
							if (undefined != data.oaOrderDetail && null != data.oaOrderDetail && undefined != data.oaOrderDetail.other_file && null != data.oaOrderDetail.other_file && "" != data.oaOrderDetail.other_file && "null" != data.oaOrderDetail.other_file) {
								var downFile = "<a downloadurl='" + data.oaOrderDetail.other_file + "' class='z_title_sty1 mar_l10 download_a download' href='javascript:void(0)'>" + data.oaOrderDetail.other_file_name + "</a>";
								$("#otherFile").html(downFile);
							}
							if (undefined != data.oaOrderDetail && null != data.oaOrderDetail && undefined != data.oaOrderDetail.schedule && null != data.oaOrderDetail.schedule) {
								if (data.oaOrderDetail.schedule.indexOf("超时") >= 0) {
									$("#detailSchedule").css("background-color", "red");
									$("#detailSchedule").css("color", "white");
								} else if (data.oaOrderDetail.schedule.indexOf("提前") >= 0) {
									$("#detailSchedule").css("background-color", "green");
									$("#detailSchedule").css("color", "white");
								}
								$("#detailSchedule").text(data.oaOrderDetail.schedule);
							}
							// 计算流入颜色和流出颜色
							if (undefined != data.oaOrderDetail && undefined != data.oaOrderDetail.step_start_time_consume && null != data.oaOrderDetail.step_start_time_consume && "" != data.oaOrderDetail.step_start_time_consume) {
								var rate = parseFloat(data.oaOrderDetail.step_start_time_consume);
								if (rate <= 33) {
									$("#detailRealStartColor").css("background-color", "#33cc00");
								} else if (rate <= 66) {
									$("#detailRealStartColor").css("background-color", "#ff9900");
								} else if (rate <= 100) {
									$("#detailRealStartColor").css("background-color", "#ff3300");
								} else {
									$("#detailRealStartColor").css("background-color", "#000000");
								}
								$("#detailRealStartColor").text(data.oaOrderDetail.step_start_time_consume + "%");
							}
							if (undefined != data.oaOrderDetail && undefined != data.oaOrderDetail.step_finish_time_consume && null != data.oaOrderDetail.step_finish_time_consume && "" != data.oaOrderDetail.step_finish_time_consume) {
								var rate = parseFloat(data.oaOrderDetail.step_finish_time_consume);
								if (rate <= 33) {
									$("#detailRealFinishColor").css("background-color", "#33cc00");
								} else if (rate <= 66) {
									$("#detailRealFinishColor").css("background-color", "#ff9900");
								} else if (rate <= 100) {
									$("#detailRealFinishColor").css("background-color", "#ff3300");
								} else {
									$("#detailRealFinishColor").css("background-color", "#000000");
								}
								$("#detailRealFinishColor").text(data.oaOrderDetail.step_finish_time_consume + "%");
							}
							// 颜色
							var st = [];
							$.each(jsonData.oaOrderNum.numInfo.split(","), function(index, data) {
								st[index] = data.substr(0, data.indexOf("-"));
							})
							jsonData.oaOrderNum.numInfo = st;

							if (undefined != jsonData.oaCqcList && null != jsonData.oaCqcList) {
								$.each(jsonData.oaCqcList, function(index, data) {
									var shearNumInfo = data.shearNumInfo.split(",");
									jsonData.oaCqcList[index].shearNumInfo = shearNumInfo;
								});
							}
						} else {
							alert(data.msg);
						}
					}
				});
			},
			initOrderData : function() {
				$("#orderId").val(orderId);
				var isEdit = $(window.parent.document).find("#orderDetail").val(); // 是否是查看订单详情
				if ("true" != isEdit && "5" == wfStepIndex) { // 判断是否可编辑
					// if(true){
					isEdit = true;// 可编辑
					$("#orderDetailDive").remove();
				} else {
					isEdit = false;// 不可编辑
					$("#processOrderDive").remove();
					// update by 张华 2015-01-20
					$("#workerTd").html("制单:" + "<span elementFillName='oaOrderDetail.worker'/>");
					// $("#workerTimeTd").html("处理日期:"+"<span elementFillName='oaOrderDetail.wf_real_finish'/>");
				}
				if (parseInt(wfStepIndex) < 5) {
					$("#exportBtn").remove();
				}
				// 初始化模板
				var materialApplyTemplet; // 申购模板
				var materialReceiveTemplet; // 签收模板
				var materialShearTitle; // 裁剪标题
				var materialShearTemplet; // 裁剪模板
				var materialLossTemplet; // 损耗模板

				var materialApplyOpt; // 申购opt
				var materialReceiveOpt; // 签收opt
				var materialShearOpt; // 裁剪opt
				var materialLossOpt; // 损耗opt
				var qiTaoOpt; // 齐套opt

				// 需要根据jsonData来组织裁剪的表头
				materialShearTitle = "<tr><th>订单号</th><th>物料名称</th><th>色号</th>";
				jsonData.oaOrderNum.title = jsonData.oaOrderNum.title.split("-");
				$.each(jsonData.oaOrderNum.title, function(i, v) {
					materialShearTitle += "<th>" + v + "</th>";
				})
				materialShearTitle += "<th>实际总裁数</th><th>裁剪差数</th></tr>";
				$("#materialShear").append(materialShearTitle);

				materialApplyTemplet = "<tr><td elementFillName='oaOrder.beginTime'/>" + "<td elementFillName='oaOrder.sellOrderCode'/>" + "<td elementFillName='oaOrder.mrName'/>" + "<td elementFillName='oaMaterialList[].materialName'/>" + "<td elementFillName='oaMaterialList[].type'/>" + "<td elementFillName='oaMaterialList[].color'/>" + "<td elementFillName='oaMaterialList[].supplierName'/>" + "<td elementFillName='oaMaterialList[].supplierAddr'/>" + "<td elementFillName='oaMaterialList[].supplierTel'/>" + "<td elementFillName='materialPurchaseDescList[].order_num'/>";

				materialReceiveTemplet = "<tr><td elementFillName='oaOrder.sellOrderCode'/>" + "<td elementFillName='oaMaterialList[].materialName'/>" + "<td elementFillName='oaMaterialList[].color'/>" + "<td elementFillName='worker'/>" + "daoBuShiJian" + "<td elementFillName='materialPurchaseDescList[].num'/>" + "yanBuShiShouShuLiang" + "<td elementFillName='materialPurchaseDescList[].org'/>" + "<td elementFillName='materialPurchaseDescList[].deviation'/>";

				materialShearTemplet = "<tr><td elementFillName='oaOrder.sellOrderCode'/>" + "<td elementFillName='oaMaterialList[].materialName'/>" + "<td elementFillName='oaMaterialList[].color'/>";

				materialLossTemplet = "<tr><td elementFillName='oaOrder.sellOrderCode'/>" + "<td elementFillName='oaMaterialList[].materialName'/>" + "<td elementFillName='oaMaterialList[].color'/>";
				if (!isEdit) {
					$("#qiTaoRemove,#qiTaoAdd,#yidongMemo,#otherFileUploadTd").remove();
					$("form input[type='text'],textarea,select").prop("disabled", true);
					materialApplyTemplet += "<td elementFillName='oaCqcList[].applyUnitNum'/>" + "<td elementFillName='materialPurchaseDescList[].unit_num'/>" + "<td elementFillName='materialPurchaseDescList[].org'/>" + "<td elementFillName='materialPurchaseDescList[].need_num'/></tr>";

					materialReceiveTemplet = materialReceiveTemplet.replace("daoBuShiJian", "<td class='receiveNum' elementFillName='oaCqcList[].receiveTime'/>");
					materialReceiveTemplet = materialReceiveTemplet.replace("yanBuShiShouShuLiang", "<td elementFillName='oaCqcList[].receiveNum'/>");
					materialReceiveTemplet += "<td class='materialReceive' elementFillName='oaCqcList[].receiveNum-jsonData.materialPurchaseDescList[].num*jsonData.materialPurchaseDescList[].deviation/100'/><td elementFillName='oaCqcList[].receiveRate'/><td elementFillName='oaCqcList[].receiveMemo'/></tr>";

					var sumShear = 0;
					$.each(jsonData.oaOrderNum.title, function(index, data) {
						materialShearTemplet += "<td class='shearNuml' elementFillName='oaCqcList[].shearNumInfo[" + index + "]'/>";
						sumShear += isNaN(parseInt(data)) ? 0 : parseInt(data);
					});
					materialShearTemplet += "<td class='shearTotall'>" + sumShear + "</td><td class='shearDivl'></td></tr>";

					materialLossTemplet += "<td elementFillName='oaCqcList[].lossBundles'/>" + "<td elementFillName='oaCqcList[].lossOther'/>" + "<td elementFillName='oaCqcList[].lossOddments'/>" + "<td elementFillName='oaCqcList[].lossYiyou'/>" + "<td elementFillName='oaCqcList[].lossCompany'/>" + "<td elementFillName='oaCqcList[].lossMemo'/></tr>";

					qiTaoTemplet = "<tr><td/>" + "<td elementFillName='oaQiTaoDetails[].project'></td>" + "<td elementFillName='oaQiTaoDetails[].tracke'></td>" + "<td elementFillName='oaQiTaoDetails[].department'></td>" + "<td elementFillName='oaQiTaoDetails[].operator'></td></tr>";
				} else {
					materialApplyTemplet += "<td><input style='width: 60px;' name='oaCqcLists[].applyUnitNum' fillName='oaCqcList[].applyUnitNum' class='applyUnitNum validate[custom[number2],maxSize[10]]' type='text'></td>" +
					/* "<td><input style='width: 60px;' name='oaDaHuoInfos[].unitNum' fillName='materialPurchaseDescList[].unit_num' class='validate[custom[number2],maxSize[10]]' type='text'></td>" + */
					"<td style='width: 60px;' elementFillName='materialPurchaseDescList[].unit_num'/>" + "<td elementFillName='materialPurchaseDescList[].org'/>" + "<td style='width: 60px;' elementFillName='materialPurchaseDescList[].need_num' />" + "<input type='hidden' name='oaCqcLists[].id' fillName='oaCqcList[].id'/>" + "<input type='hidden' name='oaCqcLists[].oaMaterialList' fillName='oaMaterialList[].id'/>" + "<input type='hidden' name='oaDaHuoInfos[].id' fillName='materialPurchaseDescList[].daHuoId'/></td></tr>";

					materialReceiveTemplet = materialReceiveTemplet.replace("daoBuShiJian", "<td><input type='text' style='width: 140px;' name='oaCqcLists[].receiveTime' fillName='oaCqcList[].receiveTime' onFocus='WdatePicker({dateFmt:&#39;yyyy-MM-dd HH:mm:ss &#39;})'/></td>");
					materialReceiveTemplet = materialReceiveTemplet.replace("yanBuShiShouShuLiang", "<td><input style='width: 60px;' name='oaCqcLists[].receiveNum' fillName='oaCqcList[].receiveNum' class='receiveNum validate[custom[number2],maxSize[10]]' type='text'/></td>");
					materialReceiveTemplet += "<td class='materialReceive' elementFillName='oaCqcList[].receiveNum-jsonData.materialPurchaseDescList[].num*jsonData.materialPurchaseDescList[].deviation/100'/>" + "<td><input style='width: 60px;' name='oaCqcLists[].receiveRate' fillName='oaCqcList[].receiveRate' class='validate[maxSize[100]]' type='text'/></td>" + "<td><input style='width: 100px;' name='oaCqcLists[].receiveMemo' fillName='oaCqcList[].receiveMemo' class='validate[maxSize[500]]' type='text'/></tr>";

					$.each(jsonData.oaOrderNum.title, function(index, data) {
						materialShearTemplet += "<td><input style='width: 60px;' name='oaCqcLists[].shearNumInfo' fillName='oaCqcList[].shearNumInfo[" + index + "]' class='shearNum validate[custom[integer],maxSize[10]]' type='text'/></td>";
					});
					materialShearTemplet += "<td class='shearTotal'></td><td class='shearDiv'></td>";

					materialLossTemplet += "<td><input style='width: 60px;' name='oaCqcLists[].lossBundles' fillName='oaCqcList[].lossBundles' class='lossBundles validate[custom[number2],maxSize[10]]' type='text'/></td>" + "<td><input style='width: 60px;' name='oaCqcLists[].lossOther' fillName='oaCqcList[].lossOther' class='lossOther validate[custom[number2],maxSize[10]]' type='text'/></td>" + "<td><input style='width: 60px;' name='oaCqcLists[].lossOddments' fillName='oaCqcList[].lossOddments' class='lossOddments validate[custom[number2],maxSize[10]]' type='text'/></td>" + "<td><input style='width: 60px;' name='oaCqcLists[].lossYiyou' fillName='oaCqcList[].lossYiyou' class='lossYiyou validate[custom[number2p],maxSize[10]]' type='text'/></td>" + "<td><input style='width: 60px;' name='oaCqcLists[].lossCompany' fillName='oaCqcList[].lossCompany' class='lossCompany validate[custom[number2p],maxSize[10]]' type='text'/></td>"
							+ "<td><input style='width: 60px;' name='oaCqcLists[].lossMemo' fillName='oaCqcList[].lossMemo' class='validate[maxSize[500]]' type='text'/></td></tr>";

					qiTaoTemplet = "<tr><td><input type='checkbox'/><input type='hidden' name='oaQiTaoDetails[].id' fillName='oaQiTaoDetails[].id' class='oaQiTaoDetailIDs'/></td>" + "<td><input style='width: 140px;' name='oaQiTaoDetails[].project' fillName='oaQiTaoDetails[].project' class='oaQiTaoDetailIDsvalidate[maxSize[100]]' type='text'/></td>" + "<td><input style='width: 280px;' name='oaQiTaoDetails[].tracke' fillName='oaQiTaoDetails[].tracke' class='validate[maxSize[200]]' type='text'/></td>" + "<td><input style='width: 100px;' name='oaQiTaoDetails[].department' fillName='oaQiTaoDetails[].department' class='validate[maxSize[20]]' type='text'/></td>" + "<td><input style='width: 100px;' name='oaQiTaoDetails[].operator' fillName='oaQiTaoDetails[].operator' class='validate[maxSize[20]]' type='text'/></td></tr>";
				}

				var materialLength;
				var qiTaoLength;
				if (jsonData.oaMaterialList == undefined) {
					materialLength = 0;
				} else {
					materialLength = jsonData.oaMaterialList.length;
				}
				if (jsonData.oaQiTaoDetails == undefined) {
					qiTaoLength = 0;
				} else {
					qiTaoLength = jsonData.oaQiTaoDetails.length;
				}

				materialApplyOpt = {
					formFill : true,
					formFillName : 'fillName',
					elementText : true,
					elementFillName : 'elementFillName',
					addRow : true,
					addNum : materialLength,
					addPoint : 'materialApply',
					templet : materialApplyTemplet,
					isList : true,
					debug : false
				};
				materialReceiveOpt = {
					formFill : true,
					formFillName : 'fillName',
					elementText : true,
					elementFillName : 'elementFillName',
					addRow : true,
					addNum : materialLength,
					addPoint : 'materialReceive',
					templet : materialReceiveTemplet,
					isList : true,
					debug : false
				};
				materialShearOpt = {
					formFill : true,
					formFillName : 'fillName',
					elementText : true,
					elementFillName : 'elementFillName',
					addRow : true,
					addNum : materialLength,
					addPoint : 'materialShear',
					templet : materialShearTemplet,
					isList : true,
					debug : false
				};
				materialLossOpt = {
					formFill : true,
					formFillName : 'fillName',
					elementText : true,
					elementFillName : 'elementFillName',
					addRow : true,
					addNum : materialLength,
					addPoint : 'materialLoss',
					templet : materialLossTemplet,
					isList : true,
					debug : false
				};
				qiTaoOpt = {
					formFill : true,
					formFillName : 'fillName',
					elementText : true,
					elementFillName : 'elementFillName',
					addRow : true,
					addNum : qiTaoLength,
					addPoint : 'qiTao',
					templet : qiTaoTemplet,
					isList : true,
					debug : false
				};
				$("#worker").attr("fillName", "oaOrderDetail.worker");
				$("#tabCQC").autoFill(jsonData, materialApplyOpt);
				$("#tabCQC").autoFill(jsonData, materialReceiveOpt);
				$("#tabCQC").autoFill(jsonData, materialShearOpt);
				$("#tabCQC").autoFill(jsonData, materialLossOpt);

				$("#tabCQC").autoFill(jsonData, qiTaoOpt);
				$(".shearNum").each(function(index, element) {
					this.value = this.value.trim();
				})
				biz.event.calcShearNum("init");
				index = qiTaoOpt.addNum;
				if (!isEdit) {
					biz.event.calcShearNumHtml();
				}
			},
			calcNeedNum : function() {
				if ($(this).val() == "") {
					$(this).parent().parent().find(".need_num").val("");
				} else {
					if (!$(this).validationEngine("validate")) {
						var idx = $(this).parent().parent().index() - 1;
						var t = parseFloat($(this).val()) * parseFloat(jsonData.materialPurchaseDescList[idx].order_num);
						$(this).parent().parent().find(".need_num").val(t);
					}
					;
				}
			},
			calcKongCha : function() {
				console.info("calc kongcha");
				if ($(this).val() == "") {
					$(this).parent().parent().find(".materialReceive").html("");
				} else {
					if (!$(this).validationEngine("validate")) {
						var idx = $(this).parent().parent().index() - 1;
						var t = parseFloat(eval($(this).val())) - jsonData.materialPurchaseDescList[idx].num * jsonData.materialPurchaseDescList[idx].deviation / 100;
						$(this).parent().parent().find(".materialReceive").html(t.toFixed(2));
					}
				}
			},
			calcLoss : function() {
				// $(".lossYiyou").each(function(index,value){
				var index = $(this).parent().parent().index() - 1;
				// 验布实收数量
				var receiveNum = $(".receiveNum:eq(" + index + ")").val();
				// console.info("验布实收数量:"+receiveNum);
				// 实际单件用量
				var applyUnitNum = $(".applyUnitNum:eq(" + index + ")").val();
				// console.info("实际单件用量:"+applyUnitNum);
				// 实际总裁数
				var shearTotal = $(".shearTotal:eq(" + index + ")").html();
				// console.info("实际总裁数:"+shearTotal);
				// 捆条布
				var lossBundles = $(".lossBundles:eq(" + index + ")").val();
				// console.info("捆条布:"+lossBundles);
				// 预留配布及用途
				var lossOther = $(".lossOther:eq(" + index + ")").val();
				// console.info("预留配布及用途:"+lossOther);
				// 余料
				var lossOddments = $(".lossOddments:eq(" + index + ")").val();
				// console.info("余料:"+lossOddments);
				// (验布实收数量-实际单件用料X实际总裁数-捆条布-预留配布及用途-余料)/验布实收数量
				var lossYiyou = (receiveNum - applyUnitNum * shearTotal - lossBundles - lossOther - lossOddments) / receiveNum;
				// console.info("lossYiyou:"+lossYiyou);
				// (验布实收数量-实际单件用料X实际总裁数-捆条布)/验布实收数量
				var lossCompany = (receiveNum - applyUnitNum * shearTotal - lossBundles) / receiveNum;
				// console.info("lossCompany:"+lossCompany);
				if (isNaN(lossYiyou) || lossYiyou == Number.NEGATIVE_INFINITY) {
					$(".lossYiyou:eq(" + index + ")").val("");
				} else {
					$(".lossYiyou:eq(" + index + ")").val(lossYiyou.toFixed(2));
				}
				if (isNaN(lossCompany) || lossCompany == Number.NEGATIVE_INFINITY) {
					$(".lossCompany:eq(" + index + ")").val("");
				} else {
					$(".lossCompany:eq(" + index + ")").val(lossCompany.toFixed(2));
				}
				// });
			},
			calcShearNum : function(init) {
				var $this;
				if (init == "init") {
					$this = $(".shearTotal");
					$.each($this,function(index,data){
						var idx = $(data).parent().index() - 1;
						var result = 0;
						result = biz.event.initCalcShearNum($(data).parent().find(".shearNum"));
						$(data).parent().find(".shearTotal").html(result);
						$(data).parent().find(".shearDiv").html(parseFloat(result - jsonData.materialPurchaseDescList[idx].order_num).toFixed(2));
					});
				} else {
					$this = $(this);
					if (!$this.validationEngine("validate") || $this.val() == '') {
						var idx = $this.parent().parent().index() - 1;
						var result = 0;
						$this.parent().parent().find(".shearNum").each(function() {
							if ($(this).val() != '' && undefined != $(this).val()) {
								result += parseFloat($(this).val());
							}
						});
						$this.parent().parent().find(".shearTotal").html(result);
						$this.parent().parent().find(".shearDiv").html(parseFloat(result - jsonData.materialPurchaseDescList[idx].order_num).toFixed(2));
					}
				}
			},
			initCalcShearNum : function(varTr) {
				var result = 0;
				$.each(varTr, function(i, index) {
					if ($(this).val() != '' && undefined != $(this).val()) {
						result += parseFloat($(this).val());
					}
				});
				return result;
			},
			calcShearNumHtml : function(init) {
				var length = $("#materialShear tr").length;
				for (i = 1; i < length; i++) {
					var $nums = $("#materialShear tr:eq(" + i + ")");
					var result = 0;
					$.each($nums.find(".shearNuml"), function(i, index) {
						var t = parseFloat($(index).html());
						if (!isNaN(t)) {
							result += t;
						}
					});
					$nums.find(".shearTotall").html(result);
					$nums.find(".shearDivl").html(result - jsonData.materialPurchaseDescList[i - 1].order_num);
				}
			},
			initTrackes : function() {
				if (jsonData.oaTrackes) {
					$.each(jsonData.oaTrackes, function(i, v) {
						biz.event.putOaTrackesTrData(v.memo, v.user, v.time);
					})
				}
				parent.iFrameHeight("iframeCQC");
			},
			putOaTrackesTrData : function(td1, td2, td3) { // 添加一行异动信息到列表
				var tr = "<tr><td width='53px' height='22px'></td><td width='573px'>" + td1 + "</td><td>" + td2 + "/" + td3 + "</td></tr>";
				$("#oaTrackesTable").append(tr);
				parent.iFrameHeight("iframeCQC");
			},
			jsonSaveTracke : function() { // 保存异动信息
				if ($.trim($("#yidongContent").val()) != "") {
					var params = {
						"oaTracke.memo" : $("#yidongContent").val(),
						"oaTracke.oaOrder" : orderId,
						"oaTracke.node" : $(window.parent.document).find("#wfStep").val()
					};
					$.ajax({
						url : "/bx/jsonSaveTracke",
						type : "post",
						data : params,
						success : function(data) {
							if("ajaxLogin" == data){
	                			alert("登录超时，请重新登录");
	                		} else if ("0" == data.code) {
								biz.event.putOaTrackesTrData($("#yidongContent").val(), data.oaTrackeUser, data.oaTrackeTime);
								$("#yidongContent").val("");
								parent.iFrameHeight("iframeCQC");
							} else {
								alert(data.msg);
							}
						}
					});
				} else {
					alert("发布异动信息不能为空");
				}
			},
			exportExcel : function() { // 导出excel操作
				$("#exportBtn").attr("disabled", "true");
				$("#exportBtn").val("30秒后再次点击");
				top.location.href = "/bx/downOAOrder?oaOrder.id=" + orderId + "&node=5";
				setTimeout(function() {
					$("#exportBtn").removeAttr("disabled");
					$("#exportBtn").val("导出Excel");
				}, 30000);
			},
			download : function() { // 下载文件
				window.location.href = "/oaOrderFileDownload?fileUrl=" + encodeURI($(this).attr("downloadUrl"));
			},
			processOrder : function() { // 保存并提交订单
				$("#processOrder").val("true"); // true标识流程流转
				biz.event.jsonProcessOrder();
			},
			saveOrder : function() { // 保存草稿
				$("#processOrder").val("false"); //false标识流程不流转，只保存数据
				biz.event.jsonProcessOrder();
			},
			jsonProcessOrder : function() {// ajax提交表单
				// 先要对所填写的数据进行验证合法后才进行提交
				var successFlag = $("#tabCQC").validationEngine("validate");
				if (successFlag) {
					$("#processBtn").attr("disabled", "true");
					$("#saveBtn").attr("disabled", "true");
					$("#backBtn").attr("disabled", "true");
					var params = $("#tabCQC").serializeArray();
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
			},
			qiTaoRemove : function() {
				var $ckbs = $("input[type=checkbox]:checked");
				if ($ckbs.size() == 0) {
					alert("请勾选要删除的行");
					return;
				} else {
					if (confirm("是否确认删除选中行？")) {
						$ckbs.each(function() {
							var oaQiTaoDetailIDs = $('#oaQiTaoDetailIDs').val();
							var delML = $(this).parent().parent().find('.oaQiTaoDetailIDs').val();
							if (oaQiTaoDetailIDs == '') {
								$('#oaQiTaoDetailIDs').val(delML);
							} else {
								$('#oaQiTaoDetailIDs').val(oaQiTaoDetailIDs + ',' + delML);
							}
							$(this).parent().parent().remove();
						});
					}
				}
				parent.iFrameHeight("iframeCQC");
			},
			qiTaoAdd : function() {
				if ($("#qiTao>tbody tr").length > 15) {
					alert("最多添加十五行");
					return;
				}
				var temp;
				temp = qiTaoTemplet.replace(/\[]/g, "[" + index + "]").replace(/\{}/g, index);
				index += 1;
				$("#qiTao").append(temp);
				parent.iFrameHeight("iframeCQC");
			}
		}
	}
	return fn;
});

// 文件上传回调函数
function uploadComplete(data) {
	data = data.replace('<meta http-equiv="content-type" content="text/html; charset=UTF-8">', "");
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
		var html = "<a href='" + json.url + "'>" + json.fileName + "</a>";
		$("#otherFile").html(json.fileName);
	}
}

function showMessage(data) {
	alert(data);
}