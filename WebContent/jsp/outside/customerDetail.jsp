<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="/js/pub/jquery_1.7.2.js"></script>
<script type="text/javascript" src="/js/outside/constant.js"></script>
<script type="text/javascript" src="/js/pub/require_min.js"></script>
<script type="text/javascript">
	requirejs.config({
		baseUrl : "${ctx}/js/"
	});
</script>
<style>
<!--
body {
	font-size: 12px;
}
-->
</style>
<head>
	<title>客户详情 | Singbada ERP</title>
</head>
<div class="z_goods_right" style="border: 1px solid #dbdbda;">
	<div
		style="background: #4b8df8; font-size: 14px; margin: 10px 10px 10px 0; color: #ffffff; width: 100%;">
		<p style="padding: 5px 5px">&nbsp;客户详情</p>
	</div>
	<form id="form1" action="/bx/saveCustomer" method="post">
		<input type="hidden" id="customerId"
			value="<s:property value="#parameters.customerId"/>" />
			
		<p style="margin: 10px;">客户名称：<span id="name"></span></p>
		<p style="margin: 10px;">客户编号：<span id="code"></span></p>
		<p style="margin: 10px;">客户等级：<span id="level"></span>类</p>
		<p style="margin: 10px;">客户来源：<span id="source"></span></p>
		<p style="margin: 10px;">客户类型：<span id="type"></span></p>
		<p style="margin: 10px;">客户标签：<span id="stage"></span></p>
		<p style="margin: 10px;">店铺旺旺：<span id="ww"></span></p>
		<p style="margin: 10px;">店铺网址：<span id="link"></span></p>
		<p style="margin: 10px;">主营品类：<span id="clothClass"></span></p>
		<p style="margin: 10px;">创建时间：<span id="addTime"></span></p>
		<p style="margin: 10px;">备注：<span id="memo"></span></p>

	</form>
</div>
<script type="text/javascript">
	$(function() {
		require([ "outside/customerDetail" ], function(fn) {
			fn.init();
		});
	});
</script>

