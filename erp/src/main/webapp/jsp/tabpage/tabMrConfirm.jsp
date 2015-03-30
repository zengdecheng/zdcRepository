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
	<form id="tabMrConfirm">
		<div
			style="background: #4b8df8; font-size: 14px; margin: 5px 5px 5px 0; color: #ffffff; width: 100%;">
			<p style="padding: 5px 5px">&nbsp;MR确认</p>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px; height: 28px; line-height: 28px; padding: 0 1.5% 0 10px;">
			打版确认</div>
		<div
			style="font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0; width: 96%;">
			<table
				style="border-collapse: collapse; width: 100%; font-size: 12px;"
				id=mrConfirmTable>
				<tr>
					<td width="9%" height="35px">是否需要复版：</td>
					<td width="20%"><input type="radio"
						name="oaMrConfirm.ifRepeat" value="1">&nbsp;需要&nbsp;&nbsp;&nbsp;<input
						type="radio" name="oaMrConfirm.ifRepeat" value="0">&nbsp;不需要</td>
					<td width="9%">样衣是否合格：</td>
					<td><input type="radio" name="oaMrConfirm.ifQualified"
						value="0">&nbsp;合格&nbsp;&nbsp;&nbsp;<input type="radio"
						name="oaMrConfirm.ifQualified" value="1">&nbsp;不合格</td>
				</tr>
				<tr>
					<td width="9%" height="30px">不合格原因：<br> <span style="color:red">（如不合格，请填写原因）</span></td>
					<td colspan="3"><textarea maxlength="1000"
							style="width: 100%; height: 100px;"
							name="oaMrConfirm.unqualifiedReason" id="unqualifiedReason"></textarea></td>
				</tr>
			</table>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">MR确认</p>
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
					<td colspan="2">MR备注:&nbsp;&nbsp;<textarea id="detailContent"
							name="oaOrderDetail.content" style="width: 560px; height: 100px;"
							maxlength="1000"></textarea></td>
					<td>
						<div style="height: 105px;">
							<span style="line-height: 22px;">进度信息:<span>
									<div
										style="height: 100px; width: 80%; float: right; border: 1px solid rgb(205, 205, 205);">
										<p
											style="padding: 0 0 0 10px; margin: 0; background-color: yellow; line-height: 22px; display:none;"
											id="detailSchedule">还未流入</p>
										<ul style="margin: 0; padding: 0 0 0 10px; line-height: 19px;">
											<li id="detailRealStart">流入时间:</li>
											<li id="detailRealFinish">实际完成:</li>
											<li>流入颜色:
												<div id="detailRealStartColor"
													style="color: #fff; margin: 1px 0 4px; width: 45px; text-align: center; display: inline-block;"></div>
											</li>
											<li>流出颜色:
												<div id="detailRealFinishColor"
													style="color: #fff; margin-bottom: 1px; width: 45px; text-align: center; display: inline-block;"></div>
											</li>
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
		<input type="hidden" id="mrConfirmId" name="oaMrConfirm.id"> <input
			type="hidden" id="orderId" name="orderId"> <input
			type="hidden" id="processOrder" name="processOrder" value="false">
		<input type="hidden" id="wfStep" name="wfStep">
		<input id="org" type="hidden" value="${session.is_manager.admin }">
	</form>
	<div style="height: 40px; text-align: center;" id="processOrderDive">
		<input type="button" value="保存并提交" class="process_button"
			id="processBtn"><input type="button" value="退回上一步"
			class="process_button" id="backBtn"
			onclick="javascript:parent.backOrder('iframeMrConfirm');"><input
			onclick="javascript:if(confirm('确认要放弃吗？')) {history.go(-1);}"
			type="button" value="放弃" class="process_button">
		<s:if test='#session.is_manager != null'>
			<input type="button" value="终止" class="process_button"
				id="terminateBtn">
		</s:if>
	</div>
	<div
		style="width: 98%; height: 40px; line-height: 40px; text-align: center; display: none;"
		id="orderDetailDive">
		<input onclick="javascript:history.go(-1);" type="button" value="返回列表"
			class="process_button">
	</div>
</div>
<script type="text/javascript">
	$(function() {
		require([ "order/tabMrConfirm" ], function(fn) {
			fn.init();
		});
	});
</script>
