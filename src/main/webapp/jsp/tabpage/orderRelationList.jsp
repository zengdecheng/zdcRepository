<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="/js/pub/jquery_1.7.2.js"></script>
<script language="javascript" type="text/javascript" src="${ctx}/js/pub/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="${ctx}/css/jquery.autocomplete.css" />
<script type="text/javascript" src="${ctx}/js/bx/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/js/pub/require_min.js"></script>
<script type="text/javascript">
	ctx="${pageContext.request.contextPath}";
</script>
<script type="text/javascript">requirejs.config({
	baseUrl : "${ctx}/js/bx/",
	urlArgs : "choose="+(new Date()).getTime()
	});</script>
<html>
<body>
<div>
<div>选择订单</div>
<div class="z_assign_list" id="form_search_div">
	<form method="post" action="/bx/orderRelationList">
		<table cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td height="40"><label class="">款号：</label></td>
				<td><input type="text" class="z_inp2" name="fsp.map.style_code" style="width:167px;margin-right: 116px" value="${fsp.map.style_code}" /></td>
				<td height="40" width="80"><label class="">订单类型：</label></td>
				<td><s:select theme="simple" list="#{'':'全部','2':'样衣打版','3':'大货生产'}" cssClass="z_inp2" cssStyle="width: 170px;" value="${fsp.map.type}" name="fsp.map.type"></s:select></td>
			</tr>
			<tr>
				<td height="40"><label class="">销售：</label></td>
				<td><select id="crm_staff_sales" class="z_inp2" style="width: 170px;" name="fsp.map.sales" value="${fsp.map.sales}"></select></td>
				<td><label class="">MR跟单：</label></td>
				<td><select id="crm_staff_mr" class="z_inp2" style="width: 170px;" name="fsp.map.mr_name" value="${fsp.map.mr_name}"></select></td>
			</tr>
			<tr>
				<td><label class="">客户：</label></td>
				<td><input type="text" name="fsp.map.codeOrName" value="${fsp.map.codeOrName}" class="z_inp2" style="width:167px;" /></td>
				<td width="65" height="40"><label class="">订单号：</label></td>
				<td width="185"><input type="text" class="z_inp2" name="fsp.map.sell_order_code" style="width:167px;" value="${fsp.map.sell_order_code}" /></td>	
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="搜索" style="width: 60px;" /></td>
				<td colspan="2" align="left"><input id="reset" type="reset" value="重置" style="width: 60px;" /></td>
			</tr>
			<%-- <tr>
				<td width="65" height="40"><label class="">订单号：</label></td>
				<td width="185"><input type="text" class="z_inp2" name="fsp.map.sell_order_code" style="width:167px;" value="${fsp.map.sell_order_code}" /></td>
				<td width="65"><label class="">男/女款：</label></td>
				<td width="185"><s:select cssClass="z_inp2" list="{'男','女','儿童'}" theme="simple" headerKey="" name="fsp.map.style_type" value="{'${fsp.map.style_type}' }" cssStyle="width: 170px" headerValue="请选择"></s:select></td>
				<td width="65"><label class="">特殊工艺：</label></td>
				<td width="185">
				<s:select theme="simple" list="{'印花','绣花','订珠','缩折/打条','打揽','洗水','其他'}" name="fsp.map.style_craft" value="{'${fsp.map.style_craft}'}" headerKey="" headerValue="请选择" cssStyle="width:167px;" cssClass="z_inp2"></s:select>
				</td>
				<td height="40"><label class="">订单类型：</label></td>
				<td><s:select theme="simple" list="#{'':'全部','2':'样衣打版','3':'大货生产'}" cssClass="z_inp2" cssStyle="width: 170px;" value="${fsp.map.type}" name="fsp.map.type"></s:select></td>
			</tr>
			<tr>
				<td height="40"><label class="">款号：</label></td>
				<td><input type="text" class="z_inp2" name="fsp.map.style_code" style="width:167px;" value="${fsp.map.style_code}" /></td>
				<td><label class="">一级分类：</label></td>
				<td><s:select  theme="simple" list="#{'针织裤子':'针织裤子','梳织裤子':'梳织裤子','针织上衣':'针织上衣','梳织上衣':'梳织上衣','连衣裙':'连衣裙','半身裙':'半身裙','羽绒':'羽绒','棉衣':'棉衣','夹克/外套':'夹克/外套','牛仔':'牛仔'}" headerKey="" name="fsp.map.style_class" value="{'${fsp.map.style_class}'}" cssStyle="width: 167px" cssClass="z_inp2" headerValue="请选择"></s:select></td>
				<td><label class="">客户：</label></td>
				<td><input type="text" name="fsp.map.cus_name" value="${fsp.map.cus_name}" class="z_inp2" style="width:167px;" /></td>
				<td><label class="">分公司：</label></td>
				<td><s:select theme="simple" list="{'杭州','广州'}" cssClass="z_inp2" cssStyle="width: 170px;" value="{'${fsp.map.city}'}" name="fsp.map.city" headerKey="" headerValue="请选择"></s:select></td>
			</tr>
			<tr>
				<td height="40"><label class="">销售：</label></td>
				<td><select id="crm_staff_sales" class="z_inp2" style="width: 170px;" name="fsp.map.sales" value="${fsp.map.sales}"></select></td>
				<td><label class="">MR跟单：</label></td>
				<td><select id="crm_staff_mr" class="z_inp2" style="width: 170px;" name="fsp.map.mr_name" value="${fsp.map.mr_name}"></select></td>
				<td><label class="">TPE：</label></td>
				<td><s:select theme="simple" list="@com.xbd.oa.utils.XbdBuffer@getStaffsByGroupName('qc')" listKey="map.login_name" listValue="map.login_name" headerKey="" headerValue="请选择" cssClass="z_inp2" cssStyle="width: 170px;" name="fsp.map.tpe_name" value="{'${fsp.map.tpe_name}'}"></s:select></td>
				<td><label class="">当前节点：</label></td>
				<td><s:select theme="simple" list="#{'':'请选择'}" cssClass="z_inp2" cssStyle="width: 170px;" value="{'${fsp.map.wf_step}'}" name="fsp.map.wf_step">
						<s:optgroup label="样衣打版" list="#{'b_mr_improve_2':'订单详情','b_ppc_confirm_3':'采购','b_pur_confirm_4':'技术','b_ppc_confirm_5':'核价','b_qc_confirm_6':'MR'}"></s:optgroup>
						<s:optgroup label="大货生产" list="#{'c_mr_improve_2':'订单详情','c_ppc_assign_3':'技术','c_fi_pay_4':'采购','c_ppc_factoryMsg_5':'CQC','c_qc_cutting_6':'TPE','c_ppc_confirm_7':'QA','c_qc_printing_8':'财务','c_ppc_confirm_9':'物流'}"></s:optgroup>
					</s:select></td>
			</tr>
			<tr>
				<td height="40"><label class="">订单类型：</label></td>
				<td><s:select theme="simple" list="#{'':'全部','2':'样衣打版','3':'大货生产'}" cssClass="z_inp2" cssStyle="width: 170px;" value="${fsp.map.type}" name="fsp.map.type"></s:select></td>
				<td><label class="">分公司：</label></td>
				<td><s:select theme="simple" list="{'杭州','广州'}" cssClass="z_inp2" cssStyle="width: 170px;" value="{'${fsp.map.city}'}" name="fsp.map.city" headerKey="" headerValue="请选择"></s:select></td>
				<td><label class="">当前节点：</label></td>
				<td><s:select theme="simple" list="#{'':'请选择'}" cssClass="z_inp2" cssStyle="width: 170px;" value="{'${fsp.map.wf_step}'}" name="fsp.map.wf_step">
						<s:optgroup label="样衣打版" list="#{'b_mr_improve_2':'订单详情','b_ppc_confirm_3':'采购','b_pur_confirm_4':'技术','b_ppc_confirm_5':'核价','b_qc_confirm_6':'MR'}"></s:optgroup>
						<s:optgroup label="大货生产" list="#{'c_mr_improve_2':'订单详情','c_ppc_assign_3':'技术','c_fi_pay_4':'采购','c_ppc_factoryMsg_5':'CQC','c_qc_cutting_6':'TPE','c_ppc_confirm_7':'QA','c_qc_printing_8':'财务','c_ppc_confirm_9':'物流'}"></s:optgroup>
					</s:select></td>
			</tr> 
			<tr>
				<td height="40"><label class="">创建日期：</label></td>
				<td><input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.start_time1" value="${fsp.map.start_time1}" onFocus="WdatePicker({readOnly:true})" /> - <input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.start_time2" value="${fsp.map.start_time2}" onFocus="WdatePicker({readOnly:true})" /></td>
				<td><label class="">紧急订单：</label></td>
				<td><s:select cssClass="z_inp2" list="#{'':'全部','0':'加急','1':'普通'}" theme="simple" name="fsp.map.is_urgent" value="${fsp.map.is_urgent}" cssStyle="width: 170px"></s:select></td>
				<td><input type="submit" value="查询" style="width: 60px;" /></td>
				<td><input id="reset" type="reset" value="重置" style="width: 60px;" /></td>
			</tr>--%>
			
		</table>
	</form>
</div>
<table border="0" cellspacing="0" cellpadding="0" class="z_table_style2" >
	<s:if test="relationOrderList.size()>0">
		<tr>
		<th width="8" height="40" ></th>
		<th width="144">订单号</th>
	<!-- 	<th width="70">订单类型</th> -->
		<th width="70">款号</th>
		<th width="123">款式描述</th>
		<th width="200">客户</th>
		<th>销售</th>
		<!-- <th width="50">数量</th>
		<th width="80">创建日期</th>
		<th width="90">剩余时间</th>
		<th width="90">当前节点剩余时间</th>
		<th width="60">负责MR</th>
		<th width="120">当前节点</th>
		<th width="60">负责人</th>
		<th>操作</th> -->
	</tr>
	<s:iterator value="relationOrderList" status="st">
		<tr>
			<td><input type="radio" name="futureOrder" class="order_relate" value="${map.id },${map.sell_order_code },${map.type},${map.oa_order_num_id}"></td>
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
	<input type="button" value="确认" id="confirm_form" >
	<input type="button" value="关闭" id="close_layer">
</div>
<script type="text/javascript">
	var sales = "${fsp.map.sales}";
	var mr_name = "${fsp.map.mr_name}";
	$(function() {
		require([ "order/orderList"], function(fn) {
			fn.init();
		});
	});
</script>
</body>
</html>