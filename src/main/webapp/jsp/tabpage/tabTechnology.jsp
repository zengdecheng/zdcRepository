<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="/css/index3.css" />
<link rel="stylesheet" type="text/css" href="/css/index4.css" />
<link rel="Stylesheet"
	href="/js/pub/jqueryValidation/css/validationEngine.jquery.css" />
<link rel="stylesheet" type="text/css"
	href="/css/order/processOrder.css" />
<script type="text/javascript" src="/js/pub/jquery_1.7.2.js"></script>
<script type="text/javascript" src="/js/pub/require_min.js"></script>
<script type="text/javascript" src="/js/pub/fileUploadValidation.js"></script>
<script type="text/javascript">
	requirejs.config({
		baseUrl : "/js/bx/"
	});
</script>
<div id="iframeDiv">
	<form id="tabTechnology">
		<div
			style="background: #4b8df8; font-size: 14px; margin: 5px 5px 5px 0; color: #ffffff; width: 100%;">
			<p style="padding: 5px 5px">&nbsp;技术</p>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px; height: 28px; line-height: 28px; padding: 0 1.5% 0 10px;">
			用料说明<input id="materialDescRemoveBtn" type="button" value="删除行"
				class="operate_button"><input id="materialDescAddBtn"
				type="button" value="增加行" class="operate_button">
		</div>
		<div
			style="font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0; width: 96%;">
			<table class="yldp_table" id="materialDescTable">
				<input type="hidden" name="materialDelIds" id="materialDelIds"
					value="">
				<input type="hidden" name="materialDescDelIds"
					id="materialDescDelIds" value="">
				<tr height="28">
					<th width="20px"></th>
					<th width="16%">物料属性</th>
					<th width="13%">物料名称</th>
					<th width="9%">主辅料</th>
					<th width="11%">色号</th>
					<th width="16%">布封</th>
					<th>单件用量（M）</th>
					<th width="20%">位置说明</th>
				</tr>
			</table>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px; height: 28px; line-height: 28px; padding: 0 1.5% 0 10px;">
			尺寸表<input id="sizeDetailRemoveBtn" type="button" value="删除行"
				class="operate_button"><input id="sizeDetailAddBtn"
				type="button" value="增加行" class="operate_button">
		</div>
		<div
			style="width: 96%; font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0;">
			<table class="yldp_table">
				<tr height="36">
					<th><span style="float: left; font-size: 14px;">成品尺寸表<input
							type="hidden" id="oaClothesSizeId" name="oaClothesSize.id"></span><span
						style="float: right;">款式分类:</span></th>
					<th width="180"><select style="width: 140px;"
						id="oaClothesSizeType" name="oaClothesSize.type">
							<option value="shangzhuang">上装</option>
							<option value="xiazhuang">下装</option>
					</select></th>
					<th width="80"><span style="float: right;">单位:</span></th>
					<th width="140"><input type="text" name="oaClothesSize.unit"
						style="width: 120px;" id="oaClothesSizeUnit" value="CM"
						maxlength="20"></th>
					<th width="100"><span style="float: right;">样板码数:</span></th>
					<th width="140"><input type="text"
						name="oaClothesSize.sampleSize" style="width: 120px;"
						id="sampleSize" maxlength="20"></th>
				</tr>
			</table>
			<table class="yldp_table" id="clothesSizeTable">
				<input type="hidden" name="clothesSizeDetailDelIds"
					id="clothesSizeDetailDelIds" value="">
			</table>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">加工说明</p>
		</div>
		<div
			style="width: 96%; font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0;">
			<table class="yldp_table">
				<input type="hidden" id="oaProcessExplainId"
					name="oaProcessExplain.id">
				<tr>
					<td colspan="2" style="border: none; padding: 15px 0 5px;"><span>特殊工艺要求:</span>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="border: none;"><textarea
							id="specialArt" name="oaProcessExplain.specialArt"
							style="width: 100%; height: 50px;" maxlength="1000"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" style="border: none; padding: 15px 0 5px;"><span>裁床工艺要求:</span>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="border: none;"><textarea id="cutArt"
							name="oaProcessExplain.cutArt"
							style="width: 100%; height: 100px;" maxlength="1000">
						</textarea></td>
				</tr>
				<!-- 一下为度量图 -->
				<tr>
					<td colspan="2" style="border: none; padding: 15px 0 5px;"><span>度量图:</span>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center"
						style="border: 1px solid rgb(205, 205, 205);"><img
						id="picImg1"
						style="display: inherit; border: 1px solid rgb(205, 205, 205); max-width: 945px;" />
						<p>
							<input type="file" id="picFile1" fileIndex="1" style="width: 200" /><input
								type="hidden" name="oaProcessExplain.measurePic"
								id="picFileHid1">
						</p></td>
				</tr>
				<!-- 以下为车缝说明 -->
				<tr>
					<td colspan="2" style="border: none; padding: 15px 0 5px;"><span>车缝说明:</span>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="border: none;"><textarea
							name="oaProcessExplain.sewing" id="sewing"
							style="width: 100%; height: 100px;"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" align="center"
						style="border: 1px solid rgb(205, 205, 205);"><img
						id="picImg2"
						style="display: inherit; border: 1px solid rgb(205, 205, 205); max-width: 945px;" />
						<p>
							<input type="file" id="picFile2" fileIndex="2" style="width: 200" /><input
								type="hidden" name="oaProcessExplain.sewingPic" id="picFileHid2">
						</p></td>
				</tr>

				<%-- <tr>
					<td colspan="2" style="border: none;"><textarea id="sewing"
							name="oaProcessExplain.sewing" style="width: 100%; height: 50px;"
							maxlength="1000"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" style="border: none; padding: 15px 0 5px;"><span>车缝说明:<input
							id="picListRemoveBtn" type="button" value="删除行"
							class="operate_button"><input id="picListAddBtn"
							type="button" value="增加行" class="operate_button"></span></td>
				</tr>
				<tr>
					<td colspan="2" style="border: none;">
						<table class="yldp_table" style="width: 100%;" id="picListTable">
							<input type="hidden" id="picInfo" name="oaProcessExplain.picInfo">
						</table>
					</td>
				</tr> --%>

				<!-- 以下为尾部工艺要求 -->
				<tr>
					<td colspan="2" style="border: none; padding: 15px 0 5px;"><span>尾部工艺要求:</span>
					</td>
				</tr>
				<tr style="border: none;">
					<td align="center" valign="middle" width="20%">扣眼，钉扣</td>
					<td style="border: none;"><textarea id="tailButton"
							name="oaProcessExplain.tailButton"
							style="width: 100%; height: 50px;" maxlength="1000"></textarea></td>
				</tr>
				<tr style="border: none;">
					<td align="center" valign="middle" width="20%">大烫</td>
					<td style="border: none;"><textarea id="tailIroning"
							name="oaProcessExplain.tailIroning"
							style="width: 100%; height: 50px;" maxlength="1000"></textarea></td>
				</tr>
				<tr style="border: none;">
					<td align="center" valign="middle" width="20%">吊牌</td>
					<td style="border: none;"><textarea id="tailCard"
							name="oaProcessExplain.tailCard"
							style="width: 100%; height: 50px;" maxlength="1000"></textarea></td>
				</tr>
				<tr style="border: none;">
					<td align="center" valign="middle" width="20%">包装</td>
					<td style="border: none;"><textarea id="tailPackaging"
							name="oaProcessExplain.tailPackaging"
							style="width: 100%; height: 50px;" maxlength="1000"></textarea></td>
				</tr>
			</table>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">管理信息</p>
		</div>
		<div
			style="width: 96%; font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0;">
			<table style="font-size: 12px; width: 100%;">
				<tr height="28px;">
					<td width="20%">制单:<span id="detailOperator"></span></td>
					<td width="45%">处理日期:<span id="detailDate"></span></td>
					<td></td>
				</tr>
				<tr height="28px;">
					<td colspan="2">技术备注:&nbsp;&nbsp;<textarea id="detailContent"
							name="oaOrderDetail.content" style="width: 560px; height: 100px;"
							maxlength="1000"></textarea></td>
					<td>
						<div style="height: 105px;">
							<span style="line-height: 22px;">进度信息:</span>
									<div
										style="height: 100px; width: 80%; float: right; border: 1px solid rgb(205, 205, 205);">
										<p
											style="padding: 0 0 0 10px; margin: 0; background-color: yellow; line-height: 22px; display:none;"
											id="detailSchedule">还未流入</p>
										<ul style="margin: 0; padding: 0 0 0 10px; line-height: 19px;">
											<li id="detailRealStart">流入时间:</li>
											<li id="detailRealFinish">实际完成:</li>
											<li>流入颜色:<div id="detailRealStartColor" style="color:#fff;margin: 1px 0 4px; width:45px; text-align: center; display: inline-block;"></div></li>
											<li>流出颜色:<div id="detailRealFinishColor" style="color:#fff;margin-bottom: 1px; width:45px; text-align: center; display: inline-block;"></div></li>
											<li style="display:none;" id="detailDuration">标准工时:</li>
											<li id="detailRealTime">实际耗时:</li>
										</ul>
									</div>
						</div>
					</td>
				</tr>
				<tr height="45px">
					<td colspan="3"><div
							style="float: left; line-height: 40px; margin-right: 15px;">附件：</div>
						<div class="attachment_name" id="otherFile"
							style="line-height: 40px; float: left; width: 50%; overflow: hidden; text-overflow: ellipsis;">&nbsp;</div>
						<input type="hidden" name="oaOrderDetail.otherFile"
						id="hid_attachment_url">
						<div id="otherFileUploadTd" style="float: left;">
							<div style="padding-top: 5px; float: left;">
								<object width="100" height="30" id="objUpload"
									class="uploadFile" attr_index="6"
									classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
									codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
									<param name="movie" value="/swf/xbdInner.swf">
									<param name="wmode" value="transparent">
									<param name="FlashVars"
										value="url=/oaOrderFileUpload?sysLoginId=1&amp;name=&amp;size=10485760">
									<embed width="100" height="30" src="/swf/xbdInner.swf"
										id="objUpload" wmode="transparent"
										type="application/x-shockwave-flash"
										flashvars="url=/oaOrderFileUpload?sysLoginId=1&amp;name=&amp;size=10485760"
										pluginspage="http://www.adobe.com/go/getflashplayer">
								</object>
							</div>
							<div
								style="color: #b1b1b1; font-size: 12px; float: left; height: 40px; line-height: 40px;">（多个附件请打包后上传）</div>
						</div></td>
				</tr>
			</table>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">异动跟踪</p>
		</div>
		<div
			style="width: 96%; font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0;">
			<table style="font-size: 12px; width: 100%;">
				<tr>
					<td colspan="3">
						<table style="font-size: 12px; width: 100%;" id="oaTrackesTable">
						</table>
					</td>
				</tr>
				<tr id="yidongMemo">
					<td width="53px">异动说明:</td>
					<td width="573px"><input type="text" id="yidongContent"
						maxlength="200"></td>
					<td><input type="button" value="发布" id="yidongBtn"
						class="process_button"></td>
				</tr>
			</table>
		</div>
		<input type="hidden" id="orderId" name="orderId"> <input
			type="hidden" id="processOrder" name="processOrder" value="false">
		<input type="hidden" id="wfStep" name="wfStep">
	</form>
	<div
		style="width: 98%; height: 40px; line-height: 40px; text-align: center;"
		id="processOrderDive">
		<input type="button" value="保存并提交" class="process_button"
			id="processBtn"><input type="button" value="保存草稿"
			class="process_button" id="saveBtn"><input type="button"
			value="退回上一步" class="process_button" id="backBtn"
			onclick="javascript:parent.backOrder('iframeTechnology');"><input
			onclick="javascript:if(confirm('确认要放弃吗？')) {history.go(-1);}"
			type="button" value="放弃" class="process_button">
	</div>
	<div
		style="width: 98%; height: 40px; line-height: 40px; text-align: center; display: none;"
		id="orderDetailDive">
		<input type="button" value="导出Excel" class="process_button"
			id="exportBtn"><input onclick="javascript:history.go(-1);"
			type="button" value="返回列表" class="process_button">
	</div>
</div>

<script type="text/javascript">
	$(function() {
		require([ "order/tabTechnology" ], function(fn) {
			fn.init();
		});
	});
</script>

