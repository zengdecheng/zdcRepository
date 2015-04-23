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
	<link rel="Stylesheet"
		href="/js/pub/jqueryValidation/css/validationEngine.jquery.css" />
	<style type="text/css">
input {
	height: 18px;
	width: 160px;
}
</style>
	<div class="step_tool"
		style="float: none; margin: 0 0 5px; width: 100%;">
		<span>一级品类管理&nbsp;&lt;&lt;&nbsp;品类编辑</span>
	</div>
	<div
		style="background: #4b8df8; font-size: 14px; margin: 5px 5px 5px 0; color: #ffffff; width: 100%;">
		<p style="padding: 5px 5px">品类信息</p>
	</div>
	<form action="/category/edit" method="post" id="categoryForm">
		<div style="padding-left: 8px;">
			<table style="width: 1000px;">
				<tr>
					<td height="35" style="width: 50px;"><span style="color: red">*</span>名称：</td>
					<td style="width: 283px;"><input type="text"
						class="validate[required,maxSize[20]]" name="oaCategory.name"
						value="${oaCategory.name}"></td>
					<td style="width: 50px;"><span style="color: red">*</span>编号：</td>
					<td style="width: 283px;"><input type="text"
						class="validate[required,maxSize[20]]" name="oaCategory.code"
						value="${oaCategory.code}"></td>
					<td style="width: 50px;"><span style="color: red">*</span>状态：</td>
					<td style="width: 283px;"><select name="oaCategory.status"
						style="height: 18px; width: 120px;" class="validate[required]">
							<option value>请选择</option>
							<option value="0"
								<s:if test="0 == oaCategory.status">selected</s:if>>禁用</option>
							<option value="1"
								<s:if test="1 == oaCategory.status">selected</s:if>>激活</option>
					</select></td>
				</tr>
				<tr>
					<td>说明：</td>
					<td colspan="5"><textarea style="width: 490px; height: 80px;"
							class="validate[maxSize[200]]" name="oaCategory.explainTxt">${oaCategory.explainTxt }</textarea></td>
				</tr>
			</table>
		</div>
		<div
			style="background: #4b8df8; font-size: 14px; margin: 15px 5px 5px 0; color: #ffffff; width: 100%;">
			<p style="padding: 5px 5px">&nbsp;整体时间设置</p>
		</div>
		<div style="padding-left: 8px;">
			<table style="width: 1000px;">
				<tr>
					<td width="140" height="30"><span style="color: red">*</span>大货标准缓冲（小时）：</td>
					<td width="200"><input type="text"
						class="validate[custom[number2],required,maxSize[5]] time_text"
						id="dahuoCyc" tname="dahuoCyc"><input type="hidden"
						tname="dahuoCyc_hid" id="dahuoCyc_hid" class="time_hid"
						name="oaCategory.dahuoCyc" value="${oaCategory.dahuoCyc }"></td>
					<td width="140"><span style="color: red">*</span>打版标准缓冲（小时）：</td>
					<td width="200"><input type="text"
						class="validate[custom[number2],required,maxSize[5]] time_text"
						id="dabanCyc" tname="dabanCyc"><input type="hidden"
						tname="dabanCyc_hid" id="dabanCyc_hid" class="time_hid"
						name="oaCategory.dabanCyc" value="${oaCategory.dabanCyc }"></td>
					<td width="86">&nbsp;</td>
					<td width="200">&nbsp;</td>
				</tr>
				<tr>
					<td height="30">销售等待（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="sellWait" tname="sellWait"><input type="hidden"
						name="oaCategory.sellWait" tname="sellWait_hid" id="sellWait_hid"
						class="time_hid" value="${oaCategory.sellWait }"></td>
					<td>货款等待（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="paymentWait" tname="paymentWait"><input type="hidden"
						name="oaCategory.paymentWait" tname="paymentWait_hid"
						id="paymentWait_hid" class="time_hid"
						value="${oaCategory.paymentWait }"></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="5"><textarea name="oaCategory.remark"
							class="validate[maxSize[200]]"
							style="width: 505px; height: 80px;">${oaCategory.remark }</textarea></td>
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
					<td width="200"><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="embroidery" tname="embroidery"><input type="hidden"
						name="oaCategory.embroidery" tname="embroidery_hid"
						id="embroidery_hid" class="time_hid"
						value="${oaCategory.embroidery }"></td>
					<td width="120">洗水（小时）：</td>
					<td width="200"><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="washwaterTime" tname="washwaterTime"><input
						type="hidden" name="oaCategory.washwaterTime"
						tname="washwaterTime_hid" id="washwaterTime_hid" class="time_hid"
						value="${oaCategory.washwaterTime }"></td>
					<td width="116">印花（小时）：</td>
					<td width="200"><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="printingTime" tname="printingTime"><input
						type="hidden" name="oaCategory.printingTime"
						tname="printingTime_hid" id="printingTime_hid" class="time_hid"
						value="${oaCategory.printingTime }"></td>
				</tr>
				<tr>
					<td height="30">缩折/打条（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="foldingTime" tname="foldingTime"><input type="hidden"
						name="oaCategory.foldingTime" tname="foldingTime_hid"
						id="foldingTime_hid" class="time_hid"
						value="${oaCategory.foldingTime }"></td>
					<td>打揽（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dalanTime" tname="dalanTime"><input type="hidden"
						name="oaCategory.dalanTime" tname="dalanTime_hid"
						id="dalanTime_hid" class="time_hid"
						value="${oaCategory.dalanTime }"></td>
					<td>订珠（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="beadsTime" tname="beadsTime"><input type="hidden"
						name="oaCategory.beadsTime" tname="beadsTime_hid"
						id="beadsTime_hid" class="time_hid"
						value="${oaCategory.beadsTime }"></td>
				</tr>
				<tr>
					<td height="30">其他（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="otherTime" tname="otherTime"><input type="hidden"
						name="oaCategory.otherTime" tname="otherTime_hid"
						id="otherTime_hid" class="time_hid"
						value="${oaCategory.otherTime }"></td>
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
					<td width="200"><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dabanEmbroidery" tname="dabanEmbroidery"><input
						type="hidden" name="oaCategory.dabanEmbroidery"
						tname="dabanEmbroidery_hid" id="dabanEmbroidery_hid"
						class="time_hid" value="${oaCategory.dabanEmbroidery }"></td>
					<td width="120">洗水（小时）：</td>
					<td width="200"><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dabanWashwaterTime" tname="dabanWashwaterTime"><input
						type="hidden" name="oaCategory.dabanWashwaterTime"
						tname="dabanWashwaterTime_hid" id="dabanWashwaterTime_hid"
						class="time_hid" value="${oaCategory.dabanWashwaterTime }"></td>
					<td width="116">印花（小时）：</td>
					<td width="200"><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dabanPrintingTime" tname="dabanPrintingTime"><input
						type="hidden" name="oaCategory.dabanPrintingTime"
						tname="dabanPrintingTime_hid" id="dabanPrintingTime_hid"
						class="time_hid" value="${oaCategory.dabanPrintingTime }"></td>
				</tr>
				<tr>
					<td height="30">缩折/打条（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dabanFoldingTime" tname="dabanFoldingTime"><input
						type="hidden" name="oaCategory.dabanFoldingTime"
						tname="dabanFoldingTime_hid" id="dabanFoldingTime_hid"
						class="time_hid" value="${oaCategory.dabanFoldingTime }"></td>
					<td>打揽（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dabanDalanTime" tname="dabanDalanTime"><input
						type="hidden" name="oaCategory.dabanDalanTime"
						tname="dabanDalanTime_hid" id="dabanDalanTime_hid"
						class="time_hid" value="${oaCategory.dabanDalanTime }"></td>
					<td>订珠（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dabanBeadsTime" tname="dabanBeadsTime"><input
						type="hidden" name="oaCategory.dabanBeadsTime"
						tname="dabanBeadsTime_hid" id="dabanBeadsTime_hid"
						class="time_hid" value="${oaCategory.dabanBeadsTime }"></td>
				</tr>
				<tr>
					<td height="30">其他（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dabanOtherTime" tname="dabanOtherTime"><input
						type="hidden" name="oaCategory.dabanOtherTime"
						tname="dabanOtherTime_hid" id="dabanOtherTime_hid"
						class="time_hid" value="${oaCategory.dabanOtherTime }"></td>
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
			<input type="hidden" name="dhTimebase.id" value="${dhTimebase.id }">
			<input type="hidden" name="dhTimebaseEntries[0].stepDuration"
				value="0"> <input type="hidden"
				name="dhTimebaseEntries[0].id" value="${dhTimebaseEntries[0].id }">
			<input type="hidden" name="dhTimebaseEntries[0].step"
				value="c_dahuo_1"> <input type="hidden"
				name="dhTimebaseEntries[0].stepName" value="新建大货订单"> <input
				type="hidden" name="dhTimebaseEntries[0].calculateDuration"
				value="0">
			<table style="width: 1000px;">
				<tr>
					<td width="130" height="30">MR补录订单：</td>
					<td width="200"><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dhStep1" tname="dhStep1"><input type="hidden"
						name="dhTimebaseEntries[1].stepDuration" tname="dhStep1_hid"
						id="dhStep1_hid" class="time_hid"
						value="${dhTimebaseEntries[1].stepDuration }"> <input
						type="hidden" name="dhTimebaseEntries[1].id"
						value="${dhTimebaseEntries[1].id }"> <input type="hidden"
						name="dhTimebaseEntries[1].step" value="c_dahuo_2"> <input
						type="hidden" name="dhTimebaseEntries[1].stepName" value="MR补录订单">
						<input type="hidden" name="dhTimebaseEntries[1].calculateDuration"
						value="1"></td>
					<td width="120">技术：</td>
					<td width="200"><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dhStep2" tname="dhStep2"><input type="hidden"
						name="dhTimebaseEntries[2].stepDuration" tname="dhStep2_hid"
						id="dhStep2_hid" class="time_hid"
						value="${dhTimebaseEntries[2].stepDuration }"> <input
						type="hidden" name="dhTimebaseEntries[2].id"
						value="${dhTimebaseEntries[2].id }"> <input type="hidden"
						name="dhTimebaseEntries[2].step" value="c_dahuo_3"> <input
						type="hidden" name="dhTimebaseEntries[2].stepName" value="技术">
						<input type="hidden" name="dhTimebaseEntries[2].calculateDuration"
						value="2"></td>
					<td width="116">缓冲：</td>
					<td width="200"><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dhStep3" tname="dhStep3"><input type="hidden"
						name="dhTimebaseEntries[3].stepDuration" tname="dhStep3_hid"
						id="dhStep3_hid" class="time_hid"
						value="${dhTimebaseEntries[3].stepDuration }"> <input
						type="hidden" name="dhTimebaseEntries[3].id"
						value="${dhTimebaseEntries[3].id }"> <input type="hidden"
						name="dhTimebaseEntries[3].step" value="c_dahuo_4"> <input
						type="hidden" name="dhTimebaseEntries[3].stepName" value="缓冲">
						<input type="hidden" name="dhTimebaseEntries[3].calculateDuration"
						value="3"></td>
				</tr>
				<tr>
					<td height="30">采购（常规）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dhStep4" tname="dhStep4"><input type="hidden"
						name="dhTimebaseEntries[4].stepDuration" tname="dhStep4_hid"
						id="dhStep4_hid" class="time_hid"
						value="${dhTimebaseEntries[4].stepDuration }"> <input
						type="hidden" name="dhTimebaseEntries[4].id"
						value="${dhTimebaseEntries[4].id }"> <input type="hidden"
						name="dhTimebaseEntries[4].step" value="c_dahuo_5"> <input
						type="hidden" name="dhTimebaseEntries[4].stepName" value="采购">
						<input type="hidden" name="dhTimebaseEntries[4].calculateDuration"
						value="4"></td>
					<td>采购（特殊）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dhStep5" tname="dhStep5"><input type="hidden"
						name="dhTimebaseEntries[5].stepDuration" tname="dhStep5_hid"
						id="dhStep5_hid" class="time_hid"
						value="${dhTimebaseEntries[5].stepDuration }"> <input
						type="hidden" name="dhTimebaseEntries[5].id"
						value="${dhTimebaseEntries[5].id }"> <input type="hidden"
						name="dhTimebaseEntries[5].step" value="c_dahuo_5_a"> <input
						type="hidden" name="dhTimebaseEntries[5].stepName" value="采购">
						<input type="hidden" name="dhTimebaseEntries[5].calculateDuration"
						value="5"></td>
					<td>CQC：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dhStep6" tname="dhStep6"><input type="hidden"
						name="dhTimebaseEntries[6].stepDuration" tname="dhStep6_hid"
						id="dhStep6_hid" class="time_hid"
						value="${dhTimebaseEntries[6].stepDuration }"> <input
						type="hidden" name="dhTimebaseEntries[6].id"
						value="${dhTimebaseEntries[6].id }"> <input type="hidden"
						name="dhTimebaseEntries[6].step" value="c_dahuo_6"> <input
						type="hidden" name="dhTimebaseEntries[6].stepName" value="CQC">
						<input type="hidden" name="dhTimebaseEntries[6].calculateDuration"
						value="6"></td>
				</tr>
				<tr>
					<td height="30">特殊工艺：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dhStep7" tname="dhStep7"><input type="hidden"
						name="dhTimebaseEntries[7].stepDuration" tname="dhStep7_hid"
						id="dhStep7_hid" class="time_hid"
						value="${dhTimebaseEntries[7].stepDuration }"> <input
						type="hidden" name="dhTimebaseEntries[7].id"
						value="${dhTimebaseEntries[7].id }"> <input type="hidden"
						name="dhTimebaseEntries[7].step" value="c_dahuo_7"> <input
						type="hidden" name="dhTimebaseEntries[7].stepName" value="特殊工艺">
						<input type="hidden" name="dhTimebaseEntries[7].calculateDuration"
						value="7"></td>
					<td>齐套：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dhStep8" tname="dhStep8"><input type="hidden"
						name="dhTimebaseEntries[8].stepDuration" tname="dhStep8_hid"
						id="dhStep8_hid" class="time_hid"
						value="${dhTimebaseEntries[8].stepDuration }"> <input
						type="hidden" name="dhTimebaseEntries[8].id"
						value="${dhTimebaseEntries[8].id }"> <input type="hidden"
						name="dhTimebaseEntries[8].step" value="c_dahuo_8"> <input
						type="hidden" name="dhTimebaseEntries[8].stepName" value="齐套">
						<input type="hidden" name="dhTimebaseEntries[8].calculateDuration"
						value="8"></td>
					<td>车缝：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dhStep9" tname="dhStep9"><input type="hidden"
						name="dhTimebaseEntries[9].stepDuration" tname="dhStep9_hid"
						id="dhStep9_hid" class="time_hid"
						value="${dhTimebaseEntries[9].stepDuration }"> <input
						type="hidden" name="dhTimebaseEntries[9].id"
						value="${dhTimebaseEntries[9].id }"> <input type="hidden"
						name="dhTimebaseEntries[9].step" value="c_dahuo_9"> <input
						type="hidden" name="dhTimebaseEntries[9].stepName" value="车缝">
						<input type="hidden" name="dhTimebaseEntries[9].calculateDuration"
						value="9"></td>
				</tr>
				<tr>
					<td height="30">QA：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dhStep10" tname="dhStep10"><input type="hidden"
						name="dhTimebaseEntries[10].stepDuration" tname="dhStep10_hid"
						id="dhStep10_hid" class="time_hid"
						value="${dhTimebaseEntries[10].stepDuration }"> <input
						type="hidden" name="dhTimebaseEntries[10].id"
						value="${dhTimebaseEntries[10].id }"> <input type="hidden"
						name="dhTimebaseEntries[10].step" value="c_dahuo_10"> <input
						type="hidden" name="dhTimebaseEntries[10].stepName" value="QA">
						<input type="hidden"
						name="dhTimebaseEntries[10].calculateDuration" value="10"></td>
					<td>财务：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dhStep11" tname="dhStep11"><input type="hidden"
						name="dhTimebaseEntries[11].stepDuration" tname="dhStep11_hid"
						id="dhStep11_hid" class="time_hid"
						value="${dhTimebaseEntries[11].stepDuration }"> <input
						type="hidden" name="dhTimebaseEntries[11].id"
						value="${dhTimebaseEntries[11].id }"> <input type="hidden"
						name="dhTimebaseEntries[11].step" value="c_dahuo_11"> <input
						type="hidden" name="dhTimebaseEntries[11].stepName" value="财务">
						<input type="hidden"
						name="dhTimebaseEntries[11].calculateDuration" value="11"></td>
					<td>物流：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						id="dhStep12" tname="dhStep12"><input type="hidden"
						name="dhTimebaseEntries[12].stepDuration" tname="dhStep12_hid"
						id="dhStep12_hid" class="time_hid"
						value="${dhTimebaseEntries[12].stepDuration }"> <input
						type="hidden" name="dhTimebaseEntries[12].id"
						value="${dhTimebaseEntries[12].id }"> <input type="hidden"
						name="dhTimebaseEntries[12].step" value="c_dahuo_12"> <input
						type="hidden" name="dhTimebaseEntries[12].stepName" value="物流">
						<input type="hidden"
						name="dhTimebaseEntries[12].calculateDuration" value="12"></td>
				</tr>
			</table>
			<div style="height: 40px; line-height: 50px; text-align: center;">
				<input type="button" id="saveBtn" value="保存"
					style="width: 115px; height: 25px;"> <input type="button"
					value="取消" onclick="javascript:window.history.go(-1);"
					style="width: 115px; height: 25px;">
			</div>
		</div>
		<input type="hidden" name="oaCategory.id" value="${oaCategory.id }">
	</form>
	<script type="text/javascript">
		$(function() {
			require([ "/js/category/page4edit.js" ], function(fn) {
				fn.init();
			})
		});
	</script>
</body>
</html>