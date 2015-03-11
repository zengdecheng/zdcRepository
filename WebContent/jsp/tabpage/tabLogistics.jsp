<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="/css/index3.css" />
<link rel="stylesheet" type="text/css" href="/css/index4.css" />
<link rel="Stylesheet"
	href="/js/pub/jqueryValidation/css/validationEngine.jquery.css" />
<link rel="stylesheet" type="text/css"
	href="/css/order/processOrder.css" />
<script type="text/javascript" src="/js/pub/jquery_1.7.2.js"></script>
<script language="javascript" type="text/javascript" src="${ctx}/js/pub/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/pub/require_min.js"></script>
<script type="text/javascript" src="/js/pub/autoFill.js"></script>
<script type="text/javascript" src="/js/pub/fileUploadValidation.js"></script>
<script type="text/javascript">
	requirejs.config({
		baseUrl : "/js/bx/"
	});
</script>
<style>
	.yldp_table td{
		width: 40px;
		height: 30px;
		text-align: center;
	}
	#logisticsDetile ul{
		list-style-type: none;
		margin-top: 15px;
		font-size: 12px;
		margin-left: 0px;
		padding-left: 10px;
		height: 20px;
	}
	#logisticsDetile li{
		margin-left: 30px;
		width: 100px;
		float: left;
	}
	#logisticsDetile li:nth-child(1){
		margin-left: 0px;
		width: 20px;
	}
</style>
<div id="iframeDiv">
	<form id="tabLogistics">
		<div
			style="background: #4b8df8; font-size: 14px; margin: 5px 5px 5px 0; color: #ffffff; width: 100%;">
			<p style="padding: 5px 5px">&nbsp;物流</p>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">检验合格数量</p>
		</div>
			<div>
			<table id="qualify_num_show" class="yldp_table">

			</table>
		</div>
			<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">物流信息</p>
		</div>
			<div style="width: 100%;" id="addLogistics">
			<table style="width: 98%; margin: 5px 5px 5px 10px; font-size: 12px;">
				<tr height="28px" width="100%">
					<td width="27%">物流单号：
						<input type="hidden" id="oaOrderId" name="oaLogistics.oaOrderId" />
						<input type="text" name="oaLogistics.logisticsNum" />
					</td>
					<td width="28%">物流公司：<input type="text" name="oaLogistics.logisticsCompany" /></td>
					<td width="33%">发货人：
						 <s:select  theme="simple" name="oaLogistics.deliveryPle" list="@com.xbd.oa.utils.XbdBuffer@getStaffsByGroupName('qa')"  listKey="map.login_name" listValue="map.login_name"
							cssClass="z_inp2 validate[required]" >
						</s:select>
					</td>
				</tr>
				<tr height="28px">
					<td><span style="color:red">*</span>发货数量：
					<input type="text" class="validate[required]"  name="oaLogistics.deliveryNum" /></td>
					<td>
						<span style="color:red">*</span>
						发货日期：<input type="text" name="oaLogistics.deliveryTime" class="validate[required]"  id="date1" onFocus="WdatePicker({readOnly:true,dateFmt: 'yyyy-MM-dd HH:mm:ss'})">
					</td>
					<td>车牌号：
						<input type="text"  name="oaLogistics.carNum" /></td>
				</tr>
				<tr height="28px">
					<td width="32%">备注：<input type="text" style="width:291px"  name="oaLogistics.remarks"></td>
					<td id="zhuangxiang_pic"><span style="color:red">*</span>装箱单：</td>
					<td>
						<div class="attachment_name" id="zhuangXiang"
							style="line-height: 40px; float: left; width: 50%; overflow: hidden; text-overflow: ellipsis;">&nbsp;</div>
						<input type="hidden" name="oaLogistics.fileUrl" id="hid_attachment_url_zhuang_xiang" value="">
						<div style=" margin-top: -1px;">
								<object width="100" height="30" id="objUpload"
									class="uploadFile" attr_index="6"
									classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
									codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
									<param name="movie" value="/swf/xbdInner.swf">
									<param name="wmode" value="transparent">
									<param name="FlashVars"
										value="url=/oaOrderImgUpload?sysLoginId=1&amp;name=pic&amp;size=10485760&ext=*.jpg;*.png;*.gif">
									<embed width="100" height="30" src="/swf/xbdInner.swf"
										id="objUpload" wmode="transparent"
										type="application/x-shockwave-flash"
										flashvars="url=/oaOrderImgUpload?sysLoginId=1&amp;name=pic&amp;size=10485760&ext=*.jpg;*.png;*.gif"
										pluginspage="http://www.adobe.com/go/getflashplayer">
								</object>
						</div>
					</td>
				</tr>
			</table>
			<div style="margin-top: 14px; margin-left: 33%; margin-bottom: 10px;">
				<input type="button" id="saveLogistics" value="保存发货记录">
			</div>
		</div>
		<div style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px; height: 28px; line-height: 28px; padding: 5px 1.5% 0 10px;">
			<input type='hidden' id="logCheckBoxVal" name='logCheckBoxVal'class='oaQiTaoDetailIDs'/>
			<input type="button" value="删除信息" id="logisticsRemove" class="operate_button">
		</div>
		<div style="width: 100%;">
			<table id="logisticsDetile" class="yldp_table">
				<tr>
					<td></td>
					<td>物流单号</td>
					<td>物流公司</td>
					<td>发货人</td>
					<td>发货数量</td>
					<td>发货日期</td>
					<td>查看集装箱单</td>
					<td>备注</td>
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
					<td width="20%">制单:
						 <s:select  theme="simple" list="@com.xbd.oa.utils.XbdBuffer@getStaffsByGroupName('qa')"  listKey="map.login_name" listValue="map.login_name"
							cssClass="z_inp2 validate[required]" name="oaOrderDetail.worker" id="worker">
						</s:select>
					</td>
					<td width="45%"></td>
					<td></td>
				</tr>
				<tr height="28px;">
					<td colspan="2">物流备注:&nbsp;&nbsp;<input type="hidden" name="oaOrderDetail_id" fillName="oaOrderDetail.id"/><textarea id="detailContent"
							fillName="oaOrderDetail.content" name="oaOrderDetail.content"
							style="width: 560px; height: 100px;" maxlength="1000"></textarea></td>
					<td>
						<div style="height: 99px;">
							<span style="line-height: 22px;">进度信息:<span>
									<div
										style="height: 142px; width: 80%; float: right; border: 1px solid rgb(205, 205, 205);">
										<p
											style="padding: 0 0 0 10px; margin: 0; background-color: yellow; line-height: 22px;"
											id="detailSchedule">还未流入</p>
										<ul style="margin: 0; padding: 0 0 0 10px; line-height: 19px;">
											<li id="detailRealStart">
												流入时间:<span elementFillName="oaOrderDetail.wf_real_start" />
											</li>
											<li id="detailRealFinish">
												实际完成:<span elementFillName="oaOrderDetail.wf_real_finish" />
											</li>
											<li>流入颜色:<div id="detailRealStartColor" style="color:#fff;margin: 1px 0 4px; width:45px; text-align: center; display: inline-block;"></div></li>
											<li>流出颜色:<div id="detailRealFinishColor" style="color:#fff;margin-bottom: 1px; width:45px; text-align: center; display: inline-block;"></div></li>
											<li id="detailDuration">
												标准工时:<span elementFillName="oaOrderDetail.wf_step_duration" />
											</li>
											<li id="detailRealTime">
												实际耗时:<span elementFillName="oaOrderDetail.real_time" />
											</li>
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
			class="process_button" id="saveBtn">
		<input type="button"
			value="退回上一步" class="process_button" id="backBtn"
			onclick="javascript:parent.backOrder('iframePurchase');">

		<%--<input onclick="javascript:if(confirm('确认要放弃吗？')) {history.go(-1);}"--%>
			<%--type="button" value="放弃" class="process_button">--%>
	</div>
	<div
		style="width: 98%; height: 40px; line-height: 40px; text-align: center;"
		id="orderDetailDive">
		<%--<input type="button" value="导出Excel" class="process_button"--%>
			<%--id="exportBtn">--%>
		<input onclick="javascript:history.go(-1);"
			type="button" value="返回列表" class="process_button">
	</div>

	<div>
		&nbsp;
	</div>
	<div>
		&nbsp;
	</div>
</div>



<script type="text/javascript">
	$(function() {
		require([ "order/tabLogistics" ], function(fn) {
			fn.init();
		});
	});
</script>

