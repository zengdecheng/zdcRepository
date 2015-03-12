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
<head>
	<title>款式详情 | Singbada ERP</title>
</head>
<div class="z_goods_right" style="border: 1px solid #dbdbda;">
	<input type="hidden" id="styleId"
		value="<s:property value="#parameters.styleId"/>" />
	<div
		style="background: #4b8df8; font-size: 14px; margin: 10px 10px 10px 0; color: #ffffff; width: 100%;">
		<p style="padding: 5px 5px">&nbsp;款式详情</p>
	</div>
	<div
		style="border-bottom: 1px solid rgb(205, 205, 205); padding: 5px 5px 5px 10px; font-size: 14px;">款式</div>
	<div style="width: 100%; margin: 10px 10px 10px 15px;">
		<table style="width: 100%; font-size: 12px;">
			<tr height="28px;">
				<td width="260px">品类：<span id="category"></span></td>
				<td width="260px">男/女款：<span id="type"></span></td>
				<td width="260px">描述：<span id="description"></span></td>
			</tr>
			<tr height="28px;">
				<td width="260px">款号：<span id="styleCode"></span></td>
				<td width="260px" colspan="2">特殊工艺：<span id="craft"></span></td>
			</tr>
		</table>
	</div>
	<div
		style="border-bottom: 1px solid rgb(205, 205, 205); padding: 5px 5px 5px 10px; font-size: 14px;">客户</div>
	<div style="width: 100%; margin: 10px 10px 10px 15px;">
		<table style="width: 100%; font-size: 12px;">
			<tr height="28px;">
				<td>编号：<span id="customerCode"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span
					id="customerName"></span></td>
			</tr>
		</table>
	</div>
	<div
		style="border-bottom: 1px solid rgb(205, 205, 205); padding: 5px 5px 5px 10px; font-size: 14px;">担当</div>
	<div style="width: 100%; margin: 10px 10px 10px 15px;">
		<table style="width: 100%; font-size: 12px;">
			<tr height="28px;">
				<td width="390px">销售：<span id="sales"></span></td>
				<td width="390px">Mr跟单：<span id="mr"></span></td>
			</tr>
		</table>
	</div>
	<div
		style="border-bottom: 1px solid rgb(205, 205, 205); padding: 5px 5px 5px 10px; font-size: 14px;">图片</div>
	<div style="width: 100%; margin: 10px 10px 10px 15px;">
		<table style="width: 100%; font-size: 12px;">
			<tr height="288px;">
				<td width="390px"><img id="picFront" src="" width="280"
					height="280"></td>
				<td width="390px"><img id="picBack" src="" width="280"
					height="280"></td>
			</tr>
			<tr height="20px;">
				<td width="390px">正面图</td>
				<td width="390px">背面图</td>
			</tr>
		</table>
	</div>
	<%-- <div style="width:785; height:40px; line-height:40px; margin-top:20px; text-align:center;">
<input onclick="javascript:history.go(-1);" type="button" value="返回" style="width:60px;">
<input onclick="window.location.href='/bx/stylePage4Edit?crmClothesStyle.id=${crmClothesStyle.id}'" type="button" value="修改" style="width:60px;">
</div> --%>
</div>
<script type="text/javascript">
	$(function() {
		require([ "outside/styleDetail" ], function(fn) {
			fn.init();
		});
	});
</script>