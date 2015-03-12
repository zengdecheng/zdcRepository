<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head lang="en">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<link href="/css/ext/order.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="/js/pub/jquery_1.7.2.js"></script>
		<script type="text/javascript" src="/js/ext/constant.js"></script>
		<script type="text/javascript" src="/js/ext/oaOrderList.js"></script>
</head>
<body>
	<input type="hidden" id="cusCode" value="<s:property value="#parameters.cusCode"/>" />
	<div id="div1All">
		<div id="divHeaderID">
			<table border="0" cellspacing="0" cellpadding="0" id="idData">
				<tbody>
					<tr>
						<th>客户编号</th>
						<th>样衣款号</th>
						<th>当前流程</th>
						<th>当前计划完成时间</th>
						<th>订单计划偏差</th>
						<th>计划剩余时间</th>
						<th>订单类型</th>
						<th>区域</th>
						<th>操作人</th>
						<th>操作</th>
					</tr>
				</tbody>
			</table>
		</div>
		<div id="barcon" name="barcon"></div>
	</div>
</body>
</html>