<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="/css/index3.css" />
<link rel="stylesheet" type="text/css" href="/css/index4.css" />
<link rel="stylesheet" type="text/css"
	href="/css/order/processOrder.css" />
<link rel="Stylesheet"
	href="/js/pub/jqueryValidation/css/validationEngine.jquery.css" />
<script language="javascript" type="text/javascript"
	src="${ctx}/js/pub/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/pub/jquery_1.7.2.js"></script>
<script type="text/javascript" src="/js/pub/autoFill.js"></script>
<script type="text/javascript" src="/js/pub/require_min.js"></script>
<div id="iframeDiv">
	<form id="tabPurchase" method="post">
		<div
			style="background: #4b8df8; font-size: 14px; margin: 5px 5px 5px 0; color: #ffffff; width: 100%;">
			<p style="padding: 5px 5px">&nbsp;采购</p>
		</div>
		<div
			style="width: 100%; border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px; height: 28px; line-height: 28px;">
			<p style="padding: 5px 5px 5px 10px">
				面辅料采购清单<input id="materialRemoveBtn" type="button" value="删除行"
					class="operate_button"><input id="materialAddBtn"
					type="button" value="增加行" class="operate_button">
			</p>
		</div>
		<div
			style="width: 96%; font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0; overflow: auto;">
			<table class="yldp_table" id="materialTable">
				<tr height="28" />
			</table>
		</div>
		<div
			style="width: 100%; border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">管理信息</p>
		</div>
		<div
			style="width: 96%; font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0;">
			<table style="font-size: 12px; width: 100%;">
				<tr height="28px;">
					<td id="sale" width="20%">采购员： <s:select id="sales"
							theme="simple"
							list="@com.xbd.oa.utils.XbdBuffer@getStaffsByGroupName('pur')"
							listKey="map.login_name" listValue="map.login_name"
							cssClass="z_inp2 validate[required]" name="oaOrderDetail.worker"></s:select>
					</td>
					<td id="mr" width="45%">采购日期： <input type="text"
						class="z_inp2 createTime validate[required] "
						style="width: 150px;" name="oaOrderDetail.workTime"
						fillName="oaOrderDetail.processTime"
						onFocus="WdatePicker({readOnly:true,dateFmt: 'yyyy-MM-dd HH:mm:ss'})" />
					</td>
					<td></td>
				</tr>
				<tr>
					<td colspan="2">采购备注：&nbsp;&nbsp; <textarea id="common"
							style="width: 560px; height: 100px;"
							fillName="oaOrderDetail.content" name="oaOrderDetail.content"></textarea></td>
					<td>
						<div style="height: 105px;">
							<span style="line-height: 22px;">进度信息:</span>
									<div
										style="height: 100px; width: 80%; float: right; border: 1px solid rgb(205, 205, 205);">
										<p
											style="padding: 0 0 0 10px; margin: 0; background-color: yellow; line-height: 22px; display:none;"
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
											<li style="display:none;" id="detailRealTime">实际耗时:<span
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
			style="width: 100%; border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">异动跟踪</p>
		</div>
		<div
			style="width: 96%; font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0;">
			<table style="font-size: 12px; width: 100%;">
				<tr>
					<td colspan="3">
						<table style="font-size: 12px; width: 100%;" id="oaTrackesTable"></table>
					</td>
				</tr>
				<tr id="yidongMemo">
					<td width="55px">异动说明:</td>
					<td width="573px"><input type="text" name="memo"
						id="yidongContent"></td>
					<td><input type="button" value="发布" id="yidongBtn"
						class="process_button"></td>
				</tr>
			</table>
		</div>
		<input type="hidden" id="materiaList" name="materialDelIds"> <input
			type="hidden" id="materiaDesc" name="materialDescDelIds"> <input
			type="hidden" id="orderId" name="orderId"> <input
			type="hidden" id="processOrder" name="processOrder" value="false">
		<input type="hidden" id="tpeAdmin"
			value="<s:property value="%{@com.xbd.oa.utils.XbdBuffer@getAdminByGroupName('qc')}"/>">
	</form>
	<div
		style="width: 98%; height: 40px; line-height: 40px; text-align: center;"
		id="processOrderDive">
		<input type="button" value="保存并提交" class="process_button"
			id="processBtn"> <input type="button" value="保存草稿"
			class="process_button" id="saveBtn"><input type="button"
			value="退回上一步" class="process_button" id="backBtn"
			onclick="javascript:parent.backOrder('iframePurchase');"> <input
			onclick="javascript:if(confirm('是否确认放弃？')){history.go(-1);};"
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
	requirejs.config({
		baseUrl : "/js/bx/"
	});
	$(function() {
		require([ "order/tabPurchase" ], function(fn) {
			fn.init();
		});
	});

	
</script>