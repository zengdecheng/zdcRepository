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
<script type="text/javascript" src="/js/outside/constant.js"></script>
<script type="text/javascript">
	requirejs.config({
		baseUrl : "/js/bx/"
	});
</script>
<div id="iframeDiv">
	<form id="tabFinance">
		<div
			style="background: #4b8df8; font-size: 14px; margin: 5px 5px 5px 0; color: #ffffff; width: 100%;">
			<p style="padding: 5px 5px">&nbsp;财务</p>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">审核记录</p>
		</div>
		<div style="width: 100%;">
			<table style="width: 98%; margin: 5px 5px 5px 10px; font-size: 12px;">
				<tr height="28px" width="100%">
					<td width="12%">订单金额：<span id="order_fee"></span></td>
					<td width="12%">已到账金额：<span id="daoZhangMoney"></span></td>
					<td width="12%">未到账金额：<span id="weiDaoZhangMoney"></span></td>
					<td width="33%">付款方式：<span id="payType"></span>
					</td>
				</tr>
			</table>
		</div>
		<div id="checkRedord">
			<input id="operator" type="hidden" name="oaOrderDetail.worker">
		</div>
		<div style="width: 96%; font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0;">
			<table style="font-size: 12px; width: 100%;">
				<tr height="28px;">
					<td colspan="2">
					<span style="position: relative; bottom: 85px;">审核意见：</span>
					<textarea id="detailContent"
							fillName="oaOrderDetail.content" name="oaOrderDetail.content"
							style="width: 560px; height: 100px;" maxlength="1000"></textarea></td>
					<td width="27%">
						<div style="height: 99px;float:left">
							<span style="line-height: 22px;">进度信息:<span>
									<div
										style="height: 142px; width: 80%; float: left; border: 1px solid rgb(205, 205, 205);margin-left:52px; position: relative; bottom: 22px;">
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
			</table>
		</div>
		<!-- <div
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
		 -->
		<input type="hidden" id="orderId" name="orderId"> <input
			type="hidden" id="processOrder" name="processOrder" value="false">
			<input type="hidden" id="wfStep" name="wfStep">
	</form>
	<div
		style="width: 98%; height: 40px; line-height: 40px; text-align: center;"
		id="processOrderDive">
		<input type="button" value="提交到下一步" class="process_button" id="processBtn">
		<input type="button" value="退回上一步" class="process_button" id="backBtn">
		<!-- <input onclick="javascript:if(confirm('确认要放弃吗？')) {history.go(-1);}"
			type="button" value="放弃" class="process_button">-->	
		<input onclick="javascript:history.go(-1);" type="button" value="返回列表" class="process_button">		
	</div>
	<div
		style="width: 98%; height: 40px; line-height: 40px; text-align: center;"
		id="orderDetailDive">
		<input onclick="javascript:history.go(-1);" type="button" value="返回列表" class="process_button" >
	</div>
	<div>&nbsp;</div>
	<div>&nbsp;</div>
</div>
<script type="text/javascript">
	$(function() {
		require([ "order/tabFinance" ], function(fn) {
			fn.init();
		});
	});
</script>

