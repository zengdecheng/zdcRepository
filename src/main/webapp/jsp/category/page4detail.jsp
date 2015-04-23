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
		<%-- <div
			style="background: #4b8df8; font-size: 14px; margin: 15px 5px 5px 0; color: #ffffff; width: 100%;">
			<p style="padding: 5px 5px">&nbsp;品类缓冲时间设置</p>
		</div>
		<div style="padding-left: 8px;">
			<table style="width: 1000px;">
				<tr>
					<td width="140" height="30"><span style="color: red">*</span>大货标准缓冲（小时）：</td>
					<td width="200" class="time_text">${oaCategory.dahuoCyc }</td>
					<td width="140"><span style="color: red">*</span>打版标准缓冲（小时）：</td>
					<td width="200" class="time_text">${oaCategory.dabanCyc }</td>
					<td width="86">&nbsp;</td>
					<td width="200">&nbsp;</td>
				</tr>
				<tr>
					<td height="30">绣花（小时）：</td>
					<td class="time_text">${oaCategory.embroidery }</td>
					<td>洗水（小时）：</td>
					<td class="time_text">${oaCategory.washwaterTime }</td>
					<td>印花（小时）：</td>
					<td class="time_text">${oaCategory.printingTime }</td>
				</tr>
				<tr>
					<td height="30">缩折/打条（小时）：</td>
					<td class="time_text">${oaCategory.foldingTime }</td>
					<td>打揽（小时）：</td>
					<td class="time_text">${oaCategory.dalanTime }</td>
					<td>订珠（小时）：</td>
					<td class="time_text">${oaCategory.beadsTime }</td>
				</tr>
				<tr>
					<td height="30">销售等待（小时）：</td>
					<td class="time_text">${oaCategory.sellWait }</td>
					<td>货款等待（小时）：</td>
					<td class="time_text">${oaCategory.paymentWait }</td>
					<td>其他（小时）：</td>
					<td class="time_text">${oaCategory.otherTime }</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="5" style="padding-right: 100px">${oaCategory.remark }</td>
				</tr>
			</table>
		</div> --%>
		<div
			style="background: #4b8df8; font-size: 14px; margin: 15px 5px 5px 0; color: #ffffff; width: 100%;">
			<p style="padding: 5px 5px">&nbsp;整体时间设置</p>
		</div>
		<div style="padding-left: 8px;">
			<table style="width: 1000px;">
				<tr>
					<td width="140" height="30"><span style="color: red">*</span>大货标准缓冲（小时）：</td>
					<td width="200" class="time_text">${oaCategory.dahuoCyc }</td>
					<td width="140"><span style="color: red">*</span>打版标准缓冲（小时）：</td>
					<td width="200" class="time_text">${oaCategory.dabanCyc }</td>
					<td width="86">&nbsp;</td>
					<td width="200">&nbsp;</td>
				</tr>
				<tr>
					<td height="30">销售等待（小时）：</td>
					<td class="time_text">${oaCategory.sellWait }</td>
					<td>货款等待（小时）：</td>
					<td class="time_text">${oaCategory.paymentWait }</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="5">${oaCategory.remark }</td>
				</tr>
			</table>
		</div>
		<div
			style="background: #4b8df8; font-size: 14px; margin: 15px 5px 5px 0; color: #ffffff; width: 100%;">
			<p style="padding: 5px 5px">&nbsp;大货-特殊工艺处理时间设置</p>
		</div>
		<div style="padding-left: 8px;">
			<table style="width: 1000px;">
				<tr>
					<td width="130" height="30">绣花（小时）：</td>
					<td width="200" class="time_text">${oaCategory.embroidery }</td>
					<td width="120">洗水（小时）：</td>
					<td width="200" class="time_text">${oaCategory.washwaterTime }</td>
					<td width="116">印花（小时）：</td>
					<td width="200" class="time_text">${oaCategory.printingTime }</td>
				</tr>
				<tr>
					<td height="30">缩折/打条（小时）：</td>
					<td class="time_text">${oaCategory.foldingTime }</td>
					<td>打揽（小时）：</td>
					<td class="time_text">${oaCategory.dalanTime }</td>
					<td>订珠（小时）：</td>
					<td class="time_text">${oaCategory.beadsTime }</td>
				</tr>
				<tr>
					<td height="30">其他（小时）：</td>
					<td class="time_text">${oaCategory.otherTime }</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
		</div>
		<div
			style="background: #4b8df8; font-size: 14px; margin: 15px 5px 5px 0; color: #ffffff; width: 100%;">
			<p style="padding: 5px 5px">&nbsp;打版-特殊工艺处理时间设置</p>
		</div>
		<div style="padding-left: 8px;">
			<table style="width: 1000px;">
				<tr>
					<td width="130" height="30">绣花（小时）：</td>
					<td width="200" class="time_text">${oaCategory.dabanEmbroidery }</td>
					<td width="120">洗水（小时）：</td>
					<td width="200" class="time_text">${oaCategory.dabanWashwaterTime }</td>
					<td width="116">印花（小时）：</td>
					<td width="200" class="time_text">${oaCategory.dabanPrintingTime }</td>
				</tr>
				<tr>
					<td height="30">缩折/打条（小时）：</td>
					<td class="time_text">${oaCategory.dabanFoldingTime }</td>
					<td>打揽（小时）：</td>
					<td class="time_text">${oaCategory.dabanDalanTime }</td>
					<td>订珠（小时）：</td>
					<td class="time_text">${oaCategory.dabanBeadsTime }</td>
				</tr>
				<tr>
					<td height="30">其他（小时）：</td>
					<td class="time_text">${oaCategory.dabanOtherTime }</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
		</div>
		<div
			style="background: #4b8df8; font-size: 14px; margin: 15px 5px 5px 0; color: #ffffff; width: 100%;">
			<p style="padding: 5px 5px">&nbsp;大货-节点时间设置</p>
		</div>
		<div style="padding-left: 8px;">
			<table style="width: 1000px;">
				<tr>
					<td width="130" height="30">MR补录订单：</td>
					<td width="200" class="time_text">${dhTimebaseEntries[1].stepDuration }</td>
					<td width="120">技术：</td>
					<td width="200" class="time_text">${dhTimebaseEntries[2].stepDuration }</td>
					<td width="116">缓冲：</td>
					<td width="200" class="time_text">${dhTimebaseEntries[3].stepDuration }</td>
				</tr>
				<tr>
					<td height="30">采购（常规）：</td>
					<td class="time_text">${dhTimebaseEntries[4].stepDuration }</td>
					<td>采购（特殊）：</td>
					<td class="time_text">${dhTimebaseEntries[5].stepDuration }</td>
					<td>CQC：</td>
					<td class="time_text">${dhTimebaseEntries[6].stepDuration }</td>
				</tr>
				<tr>
					<td height="30">特殊工艺：</td>
					<td class="time_text">${dhTimebaseEntries[7].stepDuration }</td>
					<td>齐套：</td>
					<td class="time_text">${dhTimebaseEntries[8].stepDuration }</td>
					<td>车缝：</td>
					<td class="time_text">${dhTimebaseEntries[9].stepDuration }</td>
				</tr>
				<tr>
					<td height="30">QA：</td>
					<td class="time_text">${dhTimebaseEntries[10].stepDuration }</td>
					<td>财务：</td>
					<td class="time_text">${dhTimebaseEntries[11].stepDuration }</td>
					<td>物流：</td>
					<td class="time_text">${dhTimebaseEntries[12].stepDuration }</td>
				</tr>
			</table>
		</div>
		<div style="height: 68px; line-height: 80px; text-align: center;">
			<input type="button" value="返回"
				onclick="javascript:window.history.go(-1);"
				style="width: 115px; height: 25px;">&nbsp;
		</div>
	</form>
	<script type="text/javascript">
		$(function() {
			require([ "/js/category/page4detail.js" ], function(fn) {
				fn.init();
			})
		});
	</script>
</body>
</html>