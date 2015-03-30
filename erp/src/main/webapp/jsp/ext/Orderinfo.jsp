<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head lang="en">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="/css/ext/order.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/pub/jquery_1.7.2.js"></script>
		<script type="text/javascript" src="/js/ext/constant.js"></script>
<script type="text/javascript" src="/js/ext/Orderinfo.js"></script>
</head>
<body>
	<input type="hidden" id="orderId"
		value="<s:property value="#parameters.orderId"/>" />
	<div class="box">
		<p>订单信息</p>
		<div id="divContID">
			<ol>
			</ol>
		</div>
	</div>
</body>
</html>