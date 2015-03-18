<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${systemEnvironment }添加品类|SingbadaERP</title>
</head>
<body>
	<style type="text/css">
input {
	height: 18px;
	width: 160px;
}
</style>
	<div class="step_tool"
		style="float: none; margin: 0 0 5px; width: 100%;">
		<span>一级品类管理&nbsp;&lt;&lt;&nbsp;品类详情</span>
	</div>
	<div
		style="background: #4b8df8; font-size: 14px; margin: 5px 5px 5px 0; color: #ffffff; width: 100%;">
		<p style="padding: 5px 5px">品类信息</p>
	</div>
	<form action="/category/add" method="post">
		<div style="padding-left: 8px;">
			<table style="width: 1000px;">
				<tr>
					<td height="35" style="width: 50px;"><span style="color: red">*</span>名称：</td>
					<td style="width: 283px;">${oaCategory.name}</td>
					<td style="width: 50px;"><span style="color: red">*</span>编号：</td>
					<td style="width: 283px;">${oaCategory.code}</td>
					<td style="width: 50px;"><span style="color: red">*</span>状态：</td>
					<td style="width: 283px;"><s:if test="0 == oaCategory.status">禁用</s:if>
						<s:elseif test="1 == oaCategory.status">激活</s:elseif> <s:else>未知</s:else></td>
				</tr>
				<tr>
					<td>说明：</td>
					<td colspan="5">${oaCategory.explainTxt }</td>
				</tr>
			</table>
		</div>
		<div
			style="background: #4b8df8; font-size: 14px; margin: 15px 5px 5px 0; color: #ffffff; width: 100%;">
			<p style="padding: 5px 5px">&nbsp;品类缓冲时间设置</p>
		</div>
		<div style="padding-left: 8px;">
			<table style="width: 1000px;">
				<tr>
					<td width="140" height="30"><span style="color: red">*</span>大货标准缓冲（小时）：</td>
					<td width="200">${oaCategory.dahuoCyc }</td>
					<td width="140"><span style="color: red">*</span>打版标准缓冲（小时）：</td>
					<td width="200">${oaCategory.dabanCyc }</td>
					<td width="86">&nbsp;</td>
					<td width="200">&nbsp;</td>
				</tr>
				<tr>
					<td height="30">绣花（小时）：</td>
					<td>${oaCategory.embroidery }</td>
					<td>洗水（小时）：</td>
					<td>${oaCategory.washwaterTime }</td>
					<td>印花（小时）：</td>
					<td>${oaCategory.printingTime }</td>
				</tr>
				<tr>
					<td height="30">缩折/打条（小时）：</td>
					<td>${oaCategory.foldingTime }</td>
					<td>打揽（小时）：</td>
					<td>${oaCategory.dalanTime }</td>
					<td>订珠（小时）：</td>
					<td>${oaCategory.beadsTime }</td>
				</tr>
				<tr>
					<td height="30">销售等待（小时）：</td>
					<td>${oaCategory.sellWait }</td>
					<td>货款等待（小时）：</td>
					<td>${oaCategory.paymentWait }</td>
					<td>其他（小时）：</td>
					<td>${oaCategory.otherTime }</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="5">${oaCategory.remark }</td>
				</tr>
			</table>
		</div>
		<div style="height: 40px; line-height: 80px; text-align: center;">
			<!-- <input type="submit" value="保存" style="width: 115px; height: 25px;"> -->
			<input type="button" value="返回"
				onclick="javascript:window.history.go(-1);"
				style="width: 115px; height: 25px;">&nbsp;
		</div>
	</form>
</body>
</html>