<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<span>一级品类管理&nbsp;&lt;&lt;&nbsp;品类添加</span>
	</div>
	<div
		style="background: #4b8df8; font-size: 14px; margin: 5px 5px 5px 0; color: #ffffff; width: 100%;">
		<p style="padding: 5px 5px">品类信息</p>
	</div>
	<form action="/category/add" method="post" id="categoryForm">
		<div style="padding-left: 8px;">
			<table style="width: 1000px;">
				<tr>
					<td height="35" style="width: 50px;"><span style="color: red">*</span>名称：</td>
					<td style="width: 283px;"><input type="text"
						class="validate[required,maxSize[20]]" name="oaCategory.name"></td>
					<td style="width: 50px;"><span style="color: red">*</span>编号：</td>
					<td style="width: 283px;"><input type="text"
						class="validate[required,maxSize[20]]" name="oaCategory.code"></td>
					<td style="width: 50px;"><span style="color: red">*</span>状态：</td>
					<td style="width: 283px;"><select name="oaCategory.status"
						style="height: 18px; width: 120px;" class="validate[required]">
							<option value>请选择</option>
							<option value="0">禁用</option>
							<option value="1">激活</option>
					</select></td>
				</tr>
				<tr>
					<td>说明：</td>
					<td colspan="5"><textarea style="width: 490px; height: 80px;"
							class="validate[maxSize[200]]" name="oaCategory.explainTxt"></textarea></td>
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
					<td width="200"><input type="text"
						class="validate[custom[number2],required,maxSize[5]] time_text"
						tName="dahuoCyc"><input type="hidden" id="dahuoCyc_hid"
						name="oaCategory.dahuoCyc"></td>
					<td width="140"><span style="color: red">*</span>打版标准缓冲（小时）：</td>
					<td width="200"><input type="text"
						class="validate[custom[number2],required,maxSize[5]] time_text"
						tName="dabanCyc"><input type="hidden" id="dabanCyc_hid"
						name="oaCategory.dabanCyc"></td>
					<td width="86">&nbsp;</td>
					<td width="200">&nbsp;</td>
				</tr>
				<tr>
					<td height="30">绣花（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						tName="embroidery"><input type="hidden"
						name="oaCategory.embroidery" id="embroidery_hid"></td>
					<td>洗水（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						tName="washwaterTime"><input type="hidden"
						name="oaCategory.washwaterTime" id="washwaterTime_hid"></td>
					<td>印花（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						tName="printingTime"><input type="hidden"
						name="oaCategory.printingTime" id="printingTime_hid"></td>
				</tr>
				<tr>
					<td height="30">缩折/打条（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						tName="foldingTime"><input type="hidden"
						name="oaCategory.foldingTime" id="foldingTime_hid"></td>
					<td>打揽（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						tName="dalanTime"><input type="hidden"
						name="oaCategory.dalanTime" id="dalanTime_hid"></td>
					<td>订珠（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						tName="beadsTime"><input type="hidden"
						name="oaCategory.beadsTime" id="beadsTime_hid"></td>
				</tr>
				<tr>
					<td height="30">销售等待（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						tName="sellWait"><input type="hidden"
						name="oaCategory.sellWait" id="sellWait_hid"></td>
					<td>货款等待（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						tName="paymentWait"><input type="hidden"
						name="oaCategory.paymentWait" id="paymentWait_hid"></td>
					<td>其他（小时）：</td>
					<td><input type="text"
						class="validate[custom[number2],maxSize[5]] time_text"
						tName="otherTime"><input type="hidden"
						name="oaCategory.otherTime" id="otherTime_hid"></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="5"><textarea name="oaCategory.remark"
							class="validate[maxSize[200]]"
							style="width: 505px; height: 80px;"></textarea></td>
				</tr>
			</table>
			<div style="height: 40px; line-height: 50px; text-align: center;">
				<input type="button" id="saveBtn" value="保存"
					style="width: 115px; height: 25px;"> <input type="button"
					value="取消" onclick="javascript:window.history.go(-1);"
					style="width: 115px; height: 25px;">
			</div>
		</div>
	</form>
	<script type="text/javascript">
		$(function() {
			require([ "/js/category/page4add.js" ], function(fn) {
				fn.init();
			})
		});
	</script>
</body>
</html>