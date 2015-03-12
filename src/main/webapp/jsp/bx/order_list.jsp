<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<title>${systemEnvironment }时间异动订单| Singbada ERP</title>
</head>
<div class="z_assign_list" id="form_search_div">
	<table cellpadding="0" cellspacing="0" border="0">
		<tr>
			<td><table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="60" height="40"><label class="">客户编号：</label></td>
						<td width="135"><input type="text" class="z_inp2" name="fsp.map.cus_code" value="${fsp.map.cus_code}" /></td>
						<td width="60"><label class="">样衣款号：</label></td>
						<td width="135"><input type="text" class="z_inp2" name="fsp.map.style_code" value="${fsp.map.style_code}" /></td>
						<td width="60"><label class="">订单编号：</label></td>
						<td width="135"><input type="text" class="z_inp2" name="fsp.map.order_code" value="${fsp.map.order_code}" /></td>
						<td width="60"><label class="">业务担当：</label></td>
						<td width="135"><select name="fsp.map.sales" class="z_inp3">
								<option value="999">全部</option>
								<s:iterator value="%{@com.xbd.oa.utils.XbdBuffer@getStaffsByGroupName('销售部')}">
									<option value="<s:property value="map.login_name"/>"><s:property value="map.login_name" /></option>
								</s:iterator>
						</select> <input type="hidden" name="hid.fsp.map.sales" class="notselect" value="${fsp.map.sales}" /> <input type="hidden" name="fsp.map.is_timeout" value="${fsp.map.is_timeout}" /></td>
						<td height="40"><label class="">订单类型：</label></td>
						<td><select name="fsp.map.type" class="z_inp3">
								<option value="999">全部</option>
								<option value="1">模拟报价</option>
								<option value="2">样衣打版</option>
								<option value="3">大货生产</option>
						</select> <input type="hidden" name="hid.fsp.map.type" class="notselect" value="${fsp.map.type}" /></td>
					</tr>
					<tr>
						<%-- <td height="40"><label class="">订单类型：</label></td>
						<td><select name="fsp.map.type" class="z_inp3">
								<option value="999">全部</option>
								<option value="1">模拟报价</option>
								<option value="2">样衣打版</option>
								<option value="3">大货生产</option>
						</select> <input type="hidden" name="hid.fsp.map.type" class="notselect"
							value="${fsp.map.type}" /></td> --%>
						<td><label class="">是否加急：</label></td>
						<td><select name="fsp.map.is_urgent" class="z_inp3">
								<option value="999">全部</option>
								<option value="0">加急</option>
								<option value="1">不加急</option>
						</select> <input type="hidden" name="hid.fsp.map.is_urgent" class="notselect" value="${fsp.map.is_urgent}" /></td>
						<td><label class="">流程担当：</label></td>
						<td><select name="fsp.map.ppc" class="z_inp3">
								<option value="999">全部</option>
								<s:iterator value="%{@com.xbd.oa.utils.XbdBuffer@getStaffsByGroupName('ppc')}">
									<option value="<s:property value="map.login_name"/>"><s:property value="map.login_name" /></option>
								</s:iterator>
						</select> <input type="hidden" name="hid.fsp.map.ppc" class="notselect" value="${fsp.map.ppc}" /></td>
						<td colspan="2"><input id="search_btn" type="button" value="查&nbsp;&nbsp;询" class="z_submit2" /> &nbsp;&nbsp;<input id="search_btn_ot" type="button" value="超时订单" class="z_submit2"></td>
					</tr>
				</table></td>
		</tr>
	</table>
</div>
<table width="1008" border="0" cellspacing="0" cellpadding="0" class="z_table_style2">
	<tr>
		<th width="8"></th>
		<th height="40" width="100">客户编号</th>
		<th width="90">样衣款号</th>
		<th width="150">当前流程</th>
		<th width="120">当前计划完成时间</th>
		<th width="120">订单计划偏差</th>
		<th width="120">计划剩余时间</th>
		<th width="80">订单类型</th>
		<th width="60">区域</th>
		<th width="80">操作人</th>
		<th colspan="1" class="z_title_sty6" style="text-align: left;">操作</th>
	</tr>
	<s:iterator value="superList" status="status">
		<tr>
			<td></td>
			<td class="z_title_sty1" title="${map.cus_code}"><div style="width: 60px; display: block; word-break: keep-all; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">${map.cus_code}</div></td>
			<td class="z_title_sty1" title="${map.style_code}"><label><s:property value="%{@com.xbd.oa.utils.WebUtil@getUrgentStr(map.is_urgent)}" /></label>
				<div style="width: 60px; display: block; word-break: keep-all; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">${map.style_code}</div></td>
			<td class="z_title_sty1">${map.wf_step_name}</td>
			<td class="z_title_sty3"><s:property value="%{@com.xbd.oa.utils.WebUtil@minusTimeAndOffset(map.wf_real_start,map.wf_step_duration)}" /></td>
			<td class="z_title_sty3"><s:property value="%{@com.xbd.oa.utils.WebUtil@minusTimeAndOffset(map.wf_plan_start,map.wf_step_duration)}" /></td>
			<td class="z_title_sty3"><s:property value="%{@com.xbd.oa.utils.WebUtil@minusTime(map.except_finish)}" /></td>
			<td class="z_title_sty1"><s:property value="%{@com.xbd.oa.utils.WebUtil@getOrderTypeStr(map.type)}" /></td>
			<td class="z_title_sty1">${map.city}</td>
			<td class="z_title_sty1">${map.operator}</td>
			<td class="z_title_sty5"><a href="/bx/viewOrderDetail?oaOrder.id=${map.id}">进度</a> 
<%-- 			<s:if test='#session.is_manager != null && fsp.map.org_name == "mr"'>
					<a href="/bx/editOrder?oaOrder.id=${map.id}">修改</a>
				</s:if> --%></td>
		</tr>
	</s:iterator>

</table>
<s:include value="%{ctx}/jsp/parts/pageControl.jsp"></s:include>
<script type="text/javascript">
	$(function() {
		require([ "order_list" ], function(fn) {
			fn.init();
		});
	});

	
</script>