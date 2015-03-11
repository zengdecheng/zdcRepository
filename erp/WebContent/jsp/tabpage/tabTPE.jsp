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
<script type="text/javascript" src="/js/pub/autoFill.js"></script>
<script type="text/javascript" src="/js/pub/fileUploadValidation.js"></script>
<script type="text/javascript">
	requirejs.config({
		baseUrl : "/js/bx/"
	});
</script>
<div id="iframeDiv">
	<form id="tabTPE">
		<div
			style="background: #4b8df8; font-size: 14px; margin: 5px 5px 5px 0; color: #ffffff; width: 100%;">
			<p style="padding: 5px 5px">&nbsp;TPE</p>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">裁剪数量</p>
		</div>
		<div style="font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0; width: 96%;">
			<table id="cut_num_table" class="yldp_table" >
			</table>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">车缝数量</p>
		</div>
		<div style="font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0; width: 96%;">
			<table id="sewing_num_table"  class="yldp_table" >
			<input type="hidden" name="oaTpe.id" id="oaTpe_id"/>
			<input id="sewing_num" type="hidden" name="oaTpe.sewingNum">
			</table>
		</div>
		<div style="margin-top: 5px;">
			<span style="margin-left:2.5%">车缝产出数量：<input type="text" id="sewing_total" name="oaTpe.sewingTotal" class="validate[min[1]]"></span>
			<span style="color:red;margin-left:2.5%">*</span><span>生产工厂：<input type="text" class="validate[required]" id="sewing_factory" name="oaTpe.sewingFactory"></span>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">管理信息</p>
		</div>
		<div
			style="width: 96%; font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0;">
			<table style="font-size: 12px; width: 100%;">
				<tr height="28px;">
					<td width="20%" id="workerTd">制单:
						 <s:select id="qcs"  theme="simple" list="@com.xbd.oa.utils.XbdBuffer@getStaffsByGroupName('qc')"  listKey="map.login_name" listValue="map.login_name"
							cssClass="z_inp2 validate[required]" name="oaOrderDetail.worker">
						</s:select>
					</td>
					<td width="45%">处理日期:<span id="detailDate"
						elementFillName="oaOrderDetail.wf_real_start"></span></td>
					<td></td>
				</tr>
				<tr height="28px;">
					<td colspan="2">TPE备注:&nbsp;&nbsp;<textarea id="detailContent"
							fillName="oaOrderDetail.content" name="oaOrderDetail.content"
							style="width: 560px; height: 143px;" maxlength="1000"></textarea></td>
					<td>
						<div style="height: 99px;">
							<span style="line-height: 22px;">进度信息:<span>
									<div
										style="height: 142px; width: 80%; float: right; border: 1px solid rgb(205, 205, 205);">
										<p
											style="padding: 0 0 0 10px; margin: 0; background-color: yellow; line-height: 22px;"
											id="detailSchedule">还未流入</p>
										<ul style="margin: 0; padding: 0 0 0 10px; line-height: 19px;">
											<li id="detailRealStart">流入时间:<span
												elementFillName="oaOrderDetail.wf_real_start" /></li>
											<li id="detailRealFinish">实际完成:<span
												elementFillName="oaOrderDetail.wf_real_finish" /></li>
											<li>流入颜色:<div id="detailRealStartColor" style="color:#fff;margin: 1px 0 4px; width:45px; text-align: center; display: inline-block;"></div></li>
											<li>流出颜色:<div id="detailRealFinishColor" style="color:#fff;margin-bottom: 1px; width:45px; text-align: center; display: inline-block;"></div></li>
											<li id="detailDuration">标准工时:<span
												elementFillName="oaOrderDetail.wf_step_duration" /></li>
											<li id="detailRealTime">实际耗时:<span
												elementFillName="oaOrderDetail.real_time" /></li>
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
						id="hid_attachment_url" fillName="oaOrderDetail.other_file" >
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
			class="process_button" id="saveBtn">
			<input type="button" value="退回上一步" class="process_button" id="backBtn" onclick="javascript:parent.backOrder('iframeTPE');"> 
			<input onclick="javascript:if(confirm('确认要放弃吗？')) {history.go(-1);}"
			type="button" value="放弃" class="process_button">
	</div>
	<div
		style="width: 98%; height: 40px; line-height: 40px; text-align: center;"
		id="orderDetailDive">
		<input type="button" value="导出Excel" class="process_button"
			id="exportBtn"><input onclick="javascript:history.go(-1);"
			type="button" value="返回列表" class="process_button">
	</div>
	<div>&nbsp;</div>
</div>
<script type="text/javascript">
	$(function() {
		require([ "order/tabTPE" ], function(fn) {
			fn.init();
		});
	});
</script>

