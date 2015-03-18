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
	<div class="step_tool" style="float: none; margin: 0 0 5px;">
		<span>一级品类管理&nbsp;&lt;&lt;&nbsp;品类编辑</span>
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
					<td style="width: 283px;"><input type="text"
						name="oaCategory.name" value="${oaCategory.name}"></td>
					<td style="width: 50px;"><span style="color: red">*</span>编号：</td>
					<td style="width: 283px;"><input type="text"
						name="oaCategory.code" value="${oaCategory.code}"></td>
					<td style="width: 50px;"><span style="color: red">*</span>状态：</td>
					<td style="width: 283px;"><select name="oaCategory.status"
						style="height: 18px; width: 120px;">
							<option>请选择</option>
							<option value="0" <s:if test="0 == oaCategory.status">selected</s:if>>禁用</option>
							<option value="1" <s:if test="1 == oaCategory.status">selected</s:if>>激活</option>
					</select></td>
				</tr>
				<tr>
					<td>说明：</td>
					<td colspan="5"><textarea style="width: 490px; height: 80px;"
							name="oaCategory.explainTxt">${oaCategory.explainTxt }</textarea></td>
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
					<td width="200"><input type="text" name="oaCategory.dahuoCyc" value="${oaCategory.dahuoCyc }"></td>
					<td width="140"><span style="color: red">*</span>打版标准缓冲（小时）：</td>
					<td width="200"><input type="text" name="oaCategory.dabanCyc" value="${oaCategory.dabanCyc }"></td>
					<td width="86">&nbsp;</td>
					<td width="200">&nbsp;</td>
				</tr>
				<tr>
					<td height="30">绣花（小时）：</td>
					<td><input type="text" name="oaCategory.embroidery" value="${oaCategory.embroidery }"></td>
					<td>洗水（小时）：</td>
					<td><input type="text" name="oaCategory.washwaterTime" value="${oaCategory.washwaterTime }"></td>
					<td>印花（小时）：</td>
					<td><input type="text" name="oaCategory.printingTime" value="${oaCategory.printingTime }"></td>
				</tr>
				<tr>
					<td height="30">缩折/打条（小时）：</td>
					<td><input type="text" name="oaCategory.foldingTime" value="${oaCategory.foldingTime }"></td>
					<td>打揽（小时）：</td>
					<td><input type="text" name="oaCategory.dalanTime" value="${oaCategory.dalanTime }"></td>
					<td>订珠（小时）：</td>
					<td><input type="text" name="oaCategory.beadsTime" value="${oaCategory.beadsTime }"></td>
				</tr>
				<tr>
					<td height="30">销售等待（小时）：</td>
					<td><input type="text" name="oaCategory.sellWait" value="${oaCategory.sellWait }"></td>
					<td>货款等待（小时）：</td>
					<td><input type="text" name="oaCategory.paymentWait" value="${oaCategory.paymentWait }"></td>
					<td>其他（小时）：</td>
					<td><input type="text" name="oaCategory.otherTime" value="${oaCategory.otherTime }"></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="5"><textarea name="oaCategory.remark"
							style="width: 505px; height: 80px;">${oaCategory.remark }</textarea></td>
				</tr>
			</table>
			<div style="height: 40px; line-height: 50px; text-align: center;">
				<input type="submit" value="保存" style="width: 115px; height: 25px;">
				<input type="button" value="取消" style="width: 115px; height: 25px;">
			</div>
		</div>
		<input type="hidden" name="oaCategory.id" value="${oaCategory.id }" >
	</form>
	<script type="text/javascript">
		$(function() {
			require("/category/page4add.js", function(fn) {
				fn.init();
			});
		});
	</script>
</body>
</html>