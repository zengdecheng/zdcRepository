<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${systemEnvironment }处理大货|SingbadaERP</title>
</head>
<body>
	<link rel="stylesheet" type="text/css" href="/css/order/processOrder.css" />
	<!-- <div class="z_process_back" style="float: none; width: 100%;">
		<a href="javascript:void(0)" class="go_back"><img
			src="/images/fabric3_38.gif" border="0" class="fab_style9" /></a>
	</div> -->
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
		<div id="rate" style="position: absolute; padding: 12px; width: 11%; right: 4%; top: 32px; text-align: center; color:white;">
			<span id="time_consume" data="${bean.map.time_consume }" style="font-size: 35px; font-weight: 800;">${bean.map.time_consume }%</span>
			<div>
				<span>当前节点：${bean.map.currentNode }</span>
			</div>
		</div>
	</div>
	<s:if test="1==bean.map.status">
		<form style="width: 96%; padding: 10px 10px 0;" id="terminateForm">
			<div style="width:49%; float:left;">终止操作人:${bean.map.terminateUser }</div>
			<div>终止时间:${bean.map.terminateTime }</div>
			<div style="padding: 10px 0 0; height: 20px;"><span style="color: red;">*</span>终止原因:</div>
			<textarea style="width: 100%; height: 135px; padding: 0; margin: 0; readonly='readonly'">${bean.map.terminateMemo }</textarea>
		</form>
	</s:if>
	<!-- 隐藏元素 -->
	<input type="hidden" id="styleCode" value="${bean.map.styleCode }">
	<input type="hidden" id="sellOrderId" value="${bean.map.sellOrderId }">
	<input type="hidden" id="orderCode" value="${bean.map.orderCode }">
	<input type="hidden" id="orderType" value="${bean.map.orderType }">
	<input type="hidden" id="orderTypeNum" value="${bean.map.orderTypeNum }">
	<input type="hidden" id="wantCnt" value="${bean.map.wantCnt }">
	<input type="hidden" id="beginTime" value="${bean.map.beginTime }">
	<input type="hidden" id="customerCode" value="${bean.map.customerCode }">
	<input type="hidden" id="customerName" value="${bean.map.customerName }">
	<input type="hidden" id="sales" value="${bean.map.sales }">
	<input type="hidden" id="mr" value="${bean.map.mr }">
	<input type="hidden" id="city" value="${bean.map.city }">
	<input type="hidden" id="isUrgent" value="${bean.map.isUrgent }">
	<input type="hidden" id="styleDesc" value="${bean.map.styleDesc }">
	<input type="hidden" id="styleType" value="${bean.map.styleType }">
	<input type="hidden" id="styleClass" value="${bean.map.styleClass }">
	<input type="hidden" id="clothClass" value="${bean.map.clothClass }">
	<input type="hidden" id="styleCraft" value="${bean.map.styleCraft }">
	<input type="hidden" id="sampleSize" value="${bean.map.sampleSize }">
	<input type="hidden" id="picFront" value="${bean.map.picFront }">
	<input type="hidden" id="picBack" value="${bean.map.picBack }">
	<input type="hidden" id="payType" value="${bean.map.payType }">
	<input type="hidden" id="contractCode" value="${bean.map.contractCode }">
	<input type="hidden" id="sendType" value="${bean.map.sendType }">
	<input type="hidden" id="salesMemo" value="${bean.map.salesMemo }">
	<input type="hidden" id="mrMemo" value="${bean.map.mrMemo }">
	<input type="hidden" id="tpe" value="${bean.map.tpe }">
	<input type="hidden" id="orderSizeId" value="${bean.map.orderSizeId }">
	<input type="hidden" id="orderId" value="${bean.map.orderId }">
	<input type="hidden" id="orderDetail" value="${bean.map.orderDetail }">
	<input type="hidden" id="oa_feeding_time" value="${bean.map.feeding_time }">
	<input type="hidden" id="sell_ready_time" value="${bean.map.sell_ready_time }">
	<input type="hidden" id="craft_time" value="${bean.map.craft_time }">
	<input type="hidden" id="standard_time" value="${bean.map.standard_time }">
	<input type="hidden" id="orderDetailId" value="${bean.map.orderDetailId }">
	<input type="hidden" id="wfStepIndex" value="${bean.map.wfStepIndex }">
	<input type="hidden" id="wfStep" value="${bean.map.wfStep }">
	<%-- <input type="hidden" id="detailOperator" value="${oaOrderDetail.operator }"> --%>
	<input type="hidden" id="detailContent" value="${oaOrderDetail.content }">
	<input type="hidden" id="detailAttachment" value="${oaOrderDetail.attachment }">
	<%--Add by ZQ 2014-12-22 --%>
	<input type="hidden" id="relatedOrderCode" value="${bean.map.relatedOrderCode }">
	<input type="hidden" id="relatedOrderId" value="${bean.map.relatedOrderId }">
	<input type="hidden" id="relatedOrderType" value="${bean.map.relatedOrderType }">
	<input type="hidden" id="hisOpt" value="${bean.map.hisOpt }">
	<input type="hidden" id="relatedOrderDetailId" value="${bean.map.relatedOrderDetailId }">
	<%-- <input type="hidden" id="detailAttachmentName" value="${bean.map.attachmentName }"> --%>
	<input type="hidden" id="status" value="${bean.map.status }">
	<input type="hidden" id="isPreProduct" value="${bean.map.isPreproduct }">
	<input type="hidden" id="preVersionDate" value="${bean.map.preVersionDate }">
	<input type="hidden" id="preProductDays" value="${bean.map.preProductDays }">
	<!-- 隐藏元素 -->
	<!-- tab标签页 -->
	<div class="order_tab_all">
		<div class="tabone order_tab" pageTo="tabPage2" id="tab2">
			<span>订单详情</span>
		</div>
		<div class="tabone order_tab" pageTo="tabPage3" id="tab3">
			<span>技术</span>
		</div>
		<div class="tabone order_tab" pageTo="tabPage4" id="tab4">
			<span>采购</span>
		</div>
		<div class="tabone order_tab" pageTo="tabPage5" id="tab5">
			<span>CQC</span>
		</div>
		<div class="tabone order_tab" pageTo="tabPage6" id="tab6">
			<span>TPE</span>
		</div>
		<div class="tabone order_tab" pageTo="tabPage7" id="tab7">
			<span>QA</span>
		</div>
		<div class="tabone order_tab" pageTo="tabPage8" id="tab8">
			<span>财务</span>
		</div>
		<div class="tabone order_tab" pageTo="tabPage9" id="tab9">
			<span>物流</span>
		</div>
		<div class="tabone order_tab" pageTo="tabPage10" id="tab10">
			<span>异动管理</span>
		</div>
	</div>

	<div id="tabPage2" class="tab_page tabpageone">
		<iframe id="iframeDetail" width="100%" frameborder="no" border="0" scrolling="no" src=""> </iframe>
	</div>
	<div id="tabPage3" class="tab_page tabpageone">
		<iframe id="iframeTechnology" width="100%" frameborder="no" border="0" scrolling="no" src=""> </iframe>
	</div>
	<div id="tabPage4" class="tab_page tabpageone" >
		<iframe id="iframePurchase" width="100%" frameborder="no" border="0" scrolling="no" src=""> </iframe>
	</div>
	<div id="tabPage5" class="tab_page tabpageone" >
		<iframe id="iframeCQC" width="100%" height="1150" frameborder="no" scrolling="no" border="0" src=""> </iframe>
	</div>
	<div id="tabPage6" class="tab_page tabpageone" >
		<iframe id="iframeTPE" width="100%" height="1150" frameborder="no" scrolling="no" border="0" src=""> </iframe>
	</div>
	<div id="tabPage7" class="tab_page tabpageone" >
		<iframe id="iframeQA" width="100%" height="1150" frameborder="no" scrolling="no" border="0" src=""> </iframe>
	</div>
	<div id="tabPage8" class="tab_page tabpageone" >
		<iframe id="iframeFinance" width="100%" height="1150" frameborder="no" scrolling="no" border="0" src=""> </iframe>
	</div>
	<div id="tabPage9" class="tab_page tabpageone" >
		<iframe id="iframeLogistics" width="100%" height="1150" frameborder="no" scrolling="no" border="0" src=""> </iframe>
	</div>
	<div id="tabPage10" class="tab_page tabpageone" >
		<iframe id="iframeOrderTimeout" width="100%" height="1150" frameborder="no" border="0" src=""> </iframe>
	</div>
	<script type="text/javascript">
		$(function() {
			require([ "order/processOrder" ], function(fn) {
				fn.init();
				window.outInerb = fn;
			});
		});
	</script>
</body>
</html>