<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="/js/pub/jquery_1.7.2.js"></script>
<script language="javascript" type="text/javascript"
	src="${ctx}/js/pub/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="${ctx}/css/jquery.autocomplete.css" />
<script type="text/javascript" src="${ctx}/js/bx/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/js/pub/require_min.js"></script>
<script type="text/javascript">
	ctx = "${pageContext.request.contextPath}";
</script>
<script type="text/javascript">
	requirejs.config({
		baseUrl : "${ctx}/js/bx/",
		/* urlArgs : "choose=" +(new Date()).getTime() */
	});
</script>
<html>
<body>
	<div>
		<div>选择订单</div>
		<div class="z_assign_list" id="orderList">
			<form method="post" action="/bx/orderRelationList">
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td height="40"><label class="">款号：</label></td>
						<td><input type="text" class="z_inp2"
							name="fsp.map.style_code"
							style="width: 167px; margin-right: 116px"
							value="${fsp.map.style_code}" /></td>
						<td height="40" width="80"><label class="">订单类型：</label></td>
						<td><s:select theme="simple"
								list="#{'':'全部','2':'样衣打版','3':'大货生产'}" cssClass="z_inp2"
								cssStyle="width: 170px;" value="%{fsp.map.type}"
								name="fsp.map.type"></s:select></td>
					</tr>
					<tr>
						<td height="40"><label class="">销售：</label></td>
						<td><select id="crm_staff_sales" class="z_inp2"
							style="width: 170px;" name="fsp.map.sales"
							value="${fsp.map.sales}"></select></td>
						<td><label class="">MR跟单：</label></td>
						<td><select id="crm_staff_mr" class="z_inp2"
							style="width: 170px;" name="fsp.map.mr_name"
							value="${fsp.map.mr_name}"></select></td>
					</tr>
					<tr>
						<td><label class="">客户：</label></td>
						<td><input type="text" name="fsp.map.codeOrName"
							value="${fsp.map.codeOrName}" class="z_inp2"
							style="width: 167px;" /></td>
						<td width="65" height="40"><label class="">订单号：</label></td>
						<td width="185"><input type="text" class="z_inp2"
							name="fsp.map.sell_order_code" style="width: 167px;"
							value="${fsp.map.sell_order_code}" /></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><input type="submit"
							value="搜索" style="width: 60px;" /></td>
						<td colspan="2" align="left"><input id="reset_btn" type="button"
							value="重置" style="width: 60px;" /></td>
					</tr>
				</table>
			</form>
		</div>
		<table border="0" cellspacing="0" cellpadding="0"
			class="z_table_style2">
			<s:if test="relationOrderList.size()>0">
				<tr>
					<th width="8" height="40"></th>
					<th width="144">订单号</th>
					<!-- 	<th width="70">订单类型</th> -->
					<th width="70">款号</th>
					<th width="123">款式描述</th>
					<th width="200">客户</th>
					<th>销售</th>
				</tr>
				<s:iterator value="relationOrderList" status="st">
					<tr>
						<td><input type="radio" name="futureOrder"
							class="order_relate"
							value="${map.id },${map.sell_order_code },${map.type},${map.oa_order_num_id}"></td>
						<td>${map.sell_order_code}</td>
						<td>${map.style_code }</td>
						<td>${map.style_desc}</td>
						<td>${map.cus_name}</td>
						<td>${map.sales}</td>
					</tr>
				</s:iterator>
			</s:if>
		</table>
		<s:include value="/jsp/parts/page.jsp"></s:include>
	</div>
	<div style="float: right; margin-right: 34px;">
		<input type="button" value="确认" id="confirm_form"> <input
			type="button" value="关闭" id="close_layer">
	</div>
	<script type="text/javascript">
		var sales = "${fsp.map.sales}";
		var mr_name = "${fsp.map.mr_name}";
		$(function() {
			require([ "order/orderList" ], function(fn) {
				fn.init();
			});
		});
	</script>
</body>
</html>