<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${systemEnvironment }终止订单|SingbadaERP</title>
</head>
<body>
	<link rel="stylesheet" type="text/css"
		href="/css/order/processOrder.css" />
	<script type="text/javascript" src="/js/outside/constant.js"></script>
	<!-- 基本信息 -->
	<div class="order_base_info">
		<p class="order_code">${bean.map.styleCode }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${bean.map.styleDesc }</p>
		<table>
			<tr>
				<td width="8%">订单编号：</td>
				<td width="13%">${bean.map.orderCode }</td>
				<td width="8%">订单类型：</td>
				<td width="25%">${bean.map.orderType }</td>
				<td width="8%">订单数量：</td>
				<td width="13%">${bean.map.wantCnt }</td>
				<td width="8%">创建日期：</td>
				<td width="17%">${bean.map.beginTime }</td>
			</tr>
			<tr>
				<td>客户编号：</td>
				<td>${bean.map.customerCode }</td>
				<td>客户名称：</td>
				<td>${bean.map.customerName }</td>
				<td>销售担当：</td>
				<td>${bean.map.sales }</td>
				<td>MR跟单：</td>
				<td>${bean.map.mr }</td>
			</tr>
		</table>
		<div id="rate"
			style="float: right; position: relative; left: 60px; margin-right: 83px; padding: 11px; width: 11%; bottom: 78px; text-align: center; color: white;">
			<span id="time_consume" data="${bean.map.time_consume }"
				style="font-size: 35px; font-weight: 800;">${bean.map.time_consume }%</span>
			<div>
				<span>当前节点：${bean.map.currentNode }</span>
			</div>
		</div>
	</div>
	<form style="width: 96%; padding: 10px 10px 0;" id="terminateForm">
		<input type="hidden" name="oaOrder.id" value="${bean.map.orderId }">
		<input type="hidden" id="sellOrderId" value="${bean.map.sellOrderId }">
		<input type="hidden" id="orderCode" value="${bean.map.orderCode }">
		<input type="hidden" id="status" value="${bean.map.status }">
		<input type="hidden" id="oaSec" value="${bean.map.oaSec }">
		<div style="padding: 10px 0 0;"><span style="color: red;">*</span>终止原因:</div>
		<textarea style="width: 100%; height: 135px; padding: 0; margin: 0; position: relative; bottom: 25px;" name="oaOrder.terminateMemo" id="terminateMemo"></textarea>
	</form>
	<div
		style="width: 98%; height: 40px; line-height: 40px; text-align: center;">
		<p style="line-height: 25px; height: 35px; color: red;">订单终止前，必须跟相应的销售、财务及其他相关部门进行充分沟通，订单一旦终止不可恢复，请谨慎操作！</p>
		<input type="button" value="确定终止" class="process_button"
			id="terminateBtn"><input onclick="javascript:top.location.href='/bx/todo';"
			type="button" value="返回列表" class="process_button">
	</div>
	<script type="text/javascript">
		$(function() {
			require([ "order/terminateOrder" ], function(fn) {
				fn.init();
				window.outInerb = fn;
			});
		});
	</script>
</body>
</html>