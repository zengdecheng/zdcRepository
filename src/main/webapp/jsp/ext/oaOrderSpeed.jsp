<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="/css/ext/order.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/js/pub/jquery_1.7.2.js"></script>
		<script type="text/javascript" src="/js/ext/constant.js"></script>
	<script type="text/javascript" src="/js/ext/oaOrderSpeed.js"></script>
</head>
<body>
	<input type="hidden" id="orderId"
		value="<s:property value="#parameters.orderId"/>" />
	<table border="0" cellspacing="0" cellpadding="0" id="Speed_tab">
		<tr>
			<th>当前节点</th>
			<th>开始时间</th>
			<th>时长(小时)</th>
			<th>实际完成时间</th>
			<th>实际偏差</th>
			<th>最终到期日</th>
			<th>区域</th>
			<th>负责人</th>
		</tr>
	</table>
</body>
</html>
