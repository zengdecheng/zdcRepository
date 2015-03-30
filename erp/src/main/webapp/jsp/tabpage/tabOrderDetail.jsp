<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="/css/index3.css" />
<link rel="stylesheet" type="text/css" href="/css/index4.css" />
<link rel="Stylesheet"
	href="/js/pub/jqueryValidation/css/validationEngine.jquery.css" />
<link rel="stylesheet" type="text/css"
	href="/css/order/processOrder.css" />
<script type="text/javascript" src="/js/pub/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/js/pub/require_min.js"></script>
<script language="javascript" type="text/javascript"
	src="${ctx}/js/pub/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	requirejs.config({
		baseUrl : "/js/bx/"
	});
</script>

<div id="iframeDiv">
	<form id="tabOrderDetail">
		<div
			style="background: #4b8df8; font-size: 14px; margin: 5px 5px 5px 0; color: #ffffff; width: 100%;">
			<p style="padding: 5px 5px">&nbsp;补充订单信息</p>
		</div>
		<div
			style="width: 100%; border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">订单基本信息</p>
		</div>
		<div style="width: 100%;">
			<table style="width: 98%; margin: 5px 5px 5px 10px; font-size: 12px;">
				<tr height="28px;">
					<td width="260px">订单编号：<span id="orderCode"></span></td>
					<td width="260px"><span style="color: red;">*</span>分公司：<select
						id="city" style="width: 140px;" name="oaOrder.city">
							<option value="广州">广州</option>
							<option value="杭州">杭州</option>
					</select></td>
					<%-- <td width="260px">订单类型：<span id="orderType"></span></td> --%>
					<td width="260px" id="t_repeatNum" style="display: none">重复打版次数：<span
						id="repeatNum"></span></td>
				</tr>
				<tr height="28px;">
					<td width="260px">关联订单： <input type="text" class="z_inp st"
						style="background: #EEE0E5; width: 140px;"
						name="oaOrder.relatedOrderCode" id="related_order_code" readonly>
						<input type="hidden" name="oaOrder.relatedOrderId"
						id="related_order_id"> <input type="hidden"
						name="oaOrder.relatedOrderType" id="related_order_type"> <input
						type="button" value="选择" class="relateBtn" style="display: none"
						id="chose_relate"> <input type="button" id="cancel_relate"
						class="relateBtn" style="display: none" value="取消关联"
						onclick="if(confirm('你确定要取消关联吗？ '))window.location.reload();">
						<input type="hidden" id="chooseBtn" /> <%--用于关联订单触发信息回显操作 --%> <input
						type="hidden" id="hidCloseBtn" />&nbsp; <a
						id="relatedOrderDetail" href="#" target="_blank"
						style="display: none;">查看</a> <%--用于关联订单触发关闭弹窗操作 --%>
					</td>
					<td width="260px">紧急订单：<input type="checkbox" id="isUrgent"
						value="0" name="oaOrder.isUrgent">&nbsp;<font color="red">紧急</font></td>
					<td width="260px" id="t_repeatReason" style="display: none">重复打版原因：<span
						id="repeatReason"></span></td>
				</tr>
			</table>
		</div>
		<div
			style="width: 100%; border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">款式信息</p>
		</div>
		<div style="width: 100%;">
			<table style="width: 98%; margin: 5px 5px 5px 10px; font-size: 12px;">
				<tr height="28px;">
					<td width="260px">款号：<span id="styleCode"></span></td>
					<td width="260px">描述：<span id="styleDesc"></span></td>
					<td width="260px">男/女款：<span id="styleType"></span></td>
				</tr>
				<tr height="28px;">
					<td width="260px">建议投料日期：<span id="feeding_time"></span> <input
						type="hidden" name="oaOrder.feedingTime" id="feeding_time_value" />
						<input type="hidden" name="oaOrder.sellReadyTime"
						id="sell_ready_time" /> <input type="hidden"
						name="oaOrder.standardTime" id="standard_time" /> <input
						type="hidden" name="oaOrder.craftTime" id="oaOrder_craftTime" />
						<input type="hidden" name="oaOrder.PreproductDays"
						id="preProductDays" />
					</td>
					<td colspan="2">特殊工艺： <input name="oaOrder.styleCraft"
						type="checkbox" value="印花"><span class="sp_style_craft"
						style="cursor: pointer">印花</span>&nbsp;&nbsp; <input
						name="oaOrder.styleCraft" type="checkbox" value="绣花"><span
						class="sp_style_craft" style="cursor: pointer">绣花</span>&nbsp;&nbsp;
						<input name="oaOrder.styleCraft" type="checkbox" value="订珠"><span
						class="sp_style_craft" style="cursor: pointer">订珠</span>&nbsp;&nbsp;
						<input name="oaOrder.styleCraft" type="checkbox" value="缩折/打条"><span
						class="sp_style_craft" style="cursor: pointer">缩折/打条</span>&nbsp;&nbsp;
						<input name="oaOrder.styleCraft" type="checkbox" value="打揽"><span
						class="sp_style_craft" style="cursor: pointer">打揽</span>&nbsp;&nbsp;
						<input name="oaOrder.styleCraft" type="checkbox" value="洗水"><span
						class="sp_style_craft" style="cursor: pointer">洗水</span>&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="oaOrder.styleCraft" type="checkbox" value="其他"><span
						class="sp_style_craft" style="cursor: pointer">其他</span>&nbsp;&nbsp;
						<input id="ck_last" name="oaOrder.styleCraft" type="checkbox"
						value="无"><span id="sp_none" class="sp_style_craft"
						style="cursor: pointer">无</span>
					</td>
				</tr>
				<tr height="28px;">
					<td width="260px">
						<div style="padding: 16 0 16 0;" id="isPreProductDiv">
							交货日期：<span id="goods_time"></span>
						</div>
						<div style="padding: 16 0 16 0;" id="isPreProductDiv">
							<span style="color: red;">*</span>产前版完成日期：<input type="text"
								id="isPreproduct" class="z_inp2 createTime"
								style="width: 150px;" name="oaOrder.preVersionDate"
								onFocus="WdatePicker({readOnly:true,dateFmt: 'yyyy-MM-dd 18:00:00'})" />
						</div>
						<div style="padding: 16 0 16 0;">
							<span style="color: red;">*</span>一级品类：<select id="categorys"
								class="validate[required]" name="oaOrder.styleClass"></select>
						</div>
						<div style="padding: 16 0 16 0;">
							<span style="color: red;">*</span>二级品类：<select id="clothClass"
								name="oaOrder.clothClass" style="width: 140px;">
								<s:iterator
									value="%{@com.xbd.oa.utils.XbdBuffer@getOaDtList('1')}">
									<option value="<s:property value="code"/>"><s:property
											value="value" /></option>
								</s:iterator>
							</select>
						</div>
						<div style="padding: 16 0 16 0;">
							<span style="color: red;">*</span>样板码数：<input id="sampleSize"
								type="text" style="width: 140px;" class="validate[required]"
								name="oaOrder.sampleSize" maxlength="10">
						</div>
					</td>
					<td width="260px"><img alt="正面图" src="" id="picFront"
						width="240" height="240"></td>
					<td width="260px"><img alt="背面图" src="" id="picBack"
						width="240" height="240px"></td>
				</tr>
			</table>
		</div>
		<div
			style="width: 100%; border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">数量信息</p>
		</div>
		<!-- <div style="width: 100%;"> -->
		<div
			style="width: 96%; font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0;">
			<table id="orderSize" class="yldp_table">
			</table>
		</div>
		<div
			style="width: 96%; font-size: 13px; margin-left: 2.5%; padding: 5px 0 5px 0;">
			<span>订单总数：</span><span id="wantCnt"></span>
		</div>
		<!-- </div> -->
		<div
			style="width: 100%; border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">客户信息</p>
		</div>
		<div style="width: 100%;">
			<table style="width: 98%; margin: 5px 5px 5px 10px; font-size: 12px;">
				<tr height="28px;">
					<td width="33%">客户编号：<span id="customerCode"></span></td>
					<td width="33%">客户名称：<span id="customerName"></span></td>
					<td>付款方式：<span id="payType"></span></td>
				</tr>
				<tr height="28px;">
					<td>合同号：<span id="contractCode"></span></td>
					<td colspan="2">送货方式：<input type="text" id="sendType"
						style="width: 245px;" name="oaOrder.sendtype" maxlength="25"></td>
				</tr>
				<tr height="28px;">
					<td colspan="3">销售备注： <span id="salesMemo"
						style="line-height: 22px;"></span>
					</td>
				</tr>
				<tr height="28px;">
					<td colspan="2">Mr备注：<textarea name="oaOrder.memo" id="mrMemo"
							style="width: 585px; height: 100px;" maxlength="250"></textarea></td>
					<td>
						<div style="height: 105px;">
							<span style="line-height: 22px;">进度信息:<span>
									<div
										style="height: 100px; width: 80%; float: right; border: 1px solid rgb(205, 205, 205);">
										<p
											style="padding: 0 0 0 10px; margin: 0; background-color: yellow; line-height: 22px; display: none;"
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
											<li style="display: none;" id="detailDuration">标准工时:</li>
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
			style="width: 100%; border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">管理信息</p>
		</div>
		<div style="width: 100%;">
			<table style="width: 98%; margin: 5px 5px 5px 10px; font-size: 12px;">
				<tr height="28px;">
					<td width="260px">销售：<span id="sales"></span></td>
					<td width="260px">Mr跟单：<span id="mr"></span></td>
					<td width="260px"><span style="color: red;">*</span>TPE：<select
						name="oaOrder.tpeName" id="tpe" style="width: 140px;">
							<s:iterator
								value="%{@com.xbd.oa.utils.XbdBuffer@getStaffsByGroupName('qc')}">
								<option value="<s:property value="map.login_name"/>"><s:property
										value="map.login_name" /></option>
							</s:iterator>
					</select></td>
				</tr>
			</table>
			<div
				style="width: 96%; border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px; margin-left: 2.5%; height: 28px; line-height: 28px;">
				用料搭配明细<input id="materialRemoveBtn" type="button" value="删除行"
					class="operate_button"><input id="materialAddBtn"
					type="button" value="增加行" class="operate_button">
			</div>
			<div
				style="width: 96%; font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0;">
				<table class="yldp_table">
					<input type="hidden" name="materialDelIds" id="materialDelIds"
						value="">
					<tr height="28">
						<th width="10">&nbsp;</th>
						<th>物料属性</th>
						<th>物料名称</th>
						<th>主/辅料</th>
						<th>色号</th>
						<th>供应商名</th>
						<th>供应商地址</th>
						<th>供应商电话</th>
						<th id="thOrderSize">订单数量</th>
						<th>位置说明</th>
					</tr>
					<tbody id="materialTable"></tbody>
				</table>
			</div>
			<div
				style="width: 96%; border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px; margin-left: 2.5%; margin-top: 10px; height: 28px; line-height: 28px;">
				客供料明细<input id="cusMaterialRemoveBtn" type="button" value="删除行"
					class="operate_button"><input id="cusMaterialAddBtn"
					type="button" value="增加行" class="operate_button">
			</div>
			<div
				style="width: 96%; font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0;">
				<table class="yldp_table">
					<input type="hidden" name="cusMaterialDelIds"
						id="cusMaterialDelIds" value="">
					<tbody id="cusMaterialTable"></tbody>
				</table>
			</div>
		</div>
		<input type="hidden" id="orderId" name="orderId"> <input
			type="hidden" id="processOrder" name="processOrder" value="false">
		<input type="hidden" id="tpeAdmin"
			value="<s:property value="%{@com.xbd.oa.utils.XbdBuffer@getAdminByGroupName('qc')}"/>">
		<input id="org" type="hidden" value="${session.is_manager.admin }">
	</form>
	<div style="height: 40px; text-align: center;" id="processOrderDive">
		<input type="button" value="保存并提交" class="process_button"
			id="processBtn"><input type="button" value="保存草稿"
			class="process_button" id="saveBtn"><input
			onclick="javascript:if(confirm('确认要放弃吗？')) {top.location.href='/bx/todo';}"
			type="button" value="放弃" class="process_button">
		<s:if test='#session.is_manager != null'>
			<input type="button" value="终止" class="process_button"
				id="terminateBtn">
		</s:if>
	</div>
	<div
		style="width: 98%; height: 40px; line-height: 40px; text-align: center; display: none;"
		id="orderDetailDive">
		<input type="button" value="导出Excel" class="process_button"
			id="exportBtn"><input onclick="javascript:history.go(-1);"
			type="button" value="返回列表" class="process_button">
		<s:if test='#session.is_manager != null'>
			<input type="button" value="终止" class="process_button"
				id="terminateBtn1" style="display: none">
		</s:if>
	</div>
	<div>&nbsp;</div>
</div>

<script type="text/javascript">
	$(function() {
		require([ "order/tabOrderDetail" ], function(fn) {
			fn.init();
		});
	});
</script>
