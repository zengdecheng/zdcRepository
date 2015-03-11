<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script language="javascript" type="text/javascript" src="${ctx}/js/pub/My97DatePicker/WdatePicker.js"></script>
<link rel="Stylesheet" href="/css/jquery.autocomplete.css" />
<script type="text/javascript" src="/js/bx/jquery.autocomplete.js"></script>
<head>
<title>${systemEnvironment }历史订单|SingbadaERP</title>
</head>
<div class="z_assign_list" id="form_search_div">
	<form method="post" id="orderHis">
		<input type="hidden" name="hidStatusFlag" value="true">
		<%-- <input type="hidden" name="fsp.map.wf_step" value="${fsp.map.wf_step}"> --%>
		<table cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td><table cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="65" height="40"><label class="">客户名称：</label></td>
							<td width="185">
								<input type="text" class="z_inp2" style="width:167px;" name="fsp.map.cus_name" value="${fsp.map.cus_name}"  maxlength='20' />
							</td>
							<td width="65"><label class="">样衣款号：</label></td>
							<td width="185">
								<input type="text" class="z_inp2" style="width:167px;" name="fsp.map.style_code" value="${fsp.map.style_code}"  maxlength='20' />
							</td>
							<td width="65"><label class="">订单编号：</label></td>
							<td width="185">
								<input type="text" class="z_inp2" style="width:167px;" name="fsp.map.sell_order_code" value="${fsp.map.sell_order_code}"  maxlength='20' />
							</td>
							<td height="40"><label class="">订单类型：</label></td>
							<td>
								<select name="fsp.map.type" class="z_inp3" id="typeSel"  style="width: 170px;">
									<option value="">全部</option>
									<option value="1">模拟报价</option>
									<option value="2">样衣打版</option>
									<option value="3">大货生产</option>
								</select>
								<input type="hidden" hidName="hid.fsp.map.type" id="typeHid" class="notselect" value="${fsp.map.type}" />
							</td>

							<%--<td width="60" height="40"><label class="">客户编号：</label></td>--%>
							<%--<td width="130"><input type="text" class="z_inp2"--%>
								<%--name="fsp.map.cus_code" value="${fsp.map.cus_code}" /></td>--%>

							<%--<td width="60"><label class="">是否加急：</label></td>--%>
							<%--<td width="130"><select name="fsp.map.is_urgent"--%>
								<%--class="z_inp3">--%>
									<%--<option value="">全部</option>--%>
									<%--<option value="0">加急</option>--%>
									<%--<option value="1">不加急</option>--%>
							<%--</select> <input type="hidden" hidName="hid.fsp.map.is_urgent"--%>
								<%--class="notselect" value="${fsp.map.is_urgent}" /></td>--%>

						</tr>
						<tr>
							<td><label class="">分公司：</label></td>
							<td>
								<s:select theme="simple" list="{'杭州','广州'}" cssClass="z_inp2" cssStyle="width: 170px;" value="{'${fsp.map.city}'}" name="fsp.map.city" headerKey="" headerValue="请选择"></s:select>
							</td>
							<td width="60" height="40"><label class="">合同号：</label></td>
							<td width="130">
								<input type="text" class="z_inp2"  style="width:167px;" name="fsp.map.order_code" value="${fsp.map.order_code}"  maxlength='20' />
							</td>
							<td height="40"><label class="">销售：</label></td>
							<td>
								<select id="crm_staff_sales" class="z_inp2 " style="width: 170px;" name="fsp.map.sales" value="${fsp.map.sales}"></select>
							</td>
							<td><label class="">MR跟单：</label></td>
							<td>
								<select id="crm_staff_mr" class="z_inp2" style="width: 170px;" name="fsp.map.mr_name" value="${fsp.map.mr_name}"></select>
							</td>
						</tr>
						<tr>
							<td><label class="">TPE：</label></td>
							<td>
								<s:select theme="simple" list="@com.xbd.oa.utils.XbdBuffer@getStaffsByGroupName('qc')" listKey="map.login_name" listValue="map.login_name" headerKey="" headerValue="请选择" cssClass="z_inp2" cssStyle="width: 170px;" name="fsp.map.tpe_name" value="{'${fsp.map.tpe_name}'}"></s:select>
							</td>
							<td><label class="">技术：</label></td>
							<td>
								<s:select theme="simple" list="@com.xbd.oa.utils.XbdBuffer@getStaffsByGroupName('it')" listKey="map.login_name" listValue="map.login_name" headerKey="" headerValue="请选择" cssClass="z_inp2" cssStyle="width: 170px;" name="fsp.map.del_operator" value="{'${fsp.map.del_operator}'}"></s:select>
							</td>
							<td height="40"><label class="">核价：</label></td>
							<td>
								<s:select theme="simple" list="@com.xbd.oa.utils.XbdBuffer@getStaffsByGroupName('cp')" listKey="map.login_name" listValue="map.login_name" headerKey="" headerValue="请选择" cssClass="z_inp2" cssStyle="width: 170px;" name="fsp.map.cqdel_operator" value="{'${fsp.map.cqdel_operator}'}"></s:select>
							</td>
							<td><label class="">CQC：</label></td>
							<td>
								<s:select theme="simple" list="@com.xbd.oa.utils.XbdBuffer@getStaffsByGroupName('cqc')" listKey="map.login_name" listValue="map.login_name" headerKey="" headerValue="请选择" cssClass="z_inp2" cssStyle="width: 170px;" name="fsp.map.cqcdel_operator" value="{'${fsp.map.cqcdel_operator}'}"></s:select>
							</td>
						</tr>
						<tr>
							<td><label class="">QA：</label></td>
							<td>
								<s:select theme="simple" list="@com.xbd.oa.utils.XbdBuffer@getStaffsByGroupName('qa')" listKey="map.login_name" listValue="map.login_name" headerKey="" headerValue="请选择" cssClass="z_inp2" cssStyle="width: 170px;" name="fsp.map.qadel_operator" value="{'${fsp.map.qadel_operator}'}"></s:select>
							</td>

							<td><label class="">工厂：</label></td>
							<td><input type="text" name="fsp.map.sewing_factory" value="${fsp.map.sewing_factory}" class="z_inp2" style="width:167px;" maxlength='20' /></td>


							<td><label class="">订单状态：</label></td>
							<td><select name="fsp.map.status" class="z_inp3" id="statusSel" style="width: 170px">
									<option value="">全部</option>
									<option value="0">已完成</option>
									<option value="1">已终止</option>
							</select> <input type="hidden" hidName="hid.fsp.map.status" id="statusHid"
								class="notselect" value="${fsp.map.status}" /></td>
							<td height="40"><label class="">创建日期：</label></td>
							<td><input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.start_time1" value="${fsp.map.start_time1}" onFocus="WdatePicker({readOnly:true})" /> - <input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.start_time2" value="${fsp.map.start_time2}" onFocus="WdatePicker({readOnly:true})" /></td>
						</tr>
						<tr>
							<td><label class="">是否需要复版：</label></td>
							<td><select name="fsp.map.if_repeat" class="z_inp3" id="if_repeatSel"  style="width: 170px" >
								<option value="">全部</option>
								<option value="0">需要</option>
								<option value="1">不需要</option>
							</select> <input type="hidden" hidName="hid.fsp.map.if_repeat" id="if_repeatHid"
											 class="notselect" value="${fsp.map.if_repeat}" /></td>

							<td height="40"><label class="">QA完工日期：</label></td>
							<td><input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.qadel_wf_real_finish1" value="${fsp.map.qadel_wf_real_finish1}" onFocus="WdatePicker({readOnly:true})" /> - <input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.qadel_wf_real_finish2" value="${fsp.map.qadel_wf_real_finish2}" onFocus="WdatePicker({readOnly:true})" /></td>

							<td><label class="">样衣是否合格：</label></td>
							<td><select name="fsp.map.if_qualified" class="z_inp3" id="if_qualifiedSel"  style="width: 170px">
								<option value="">全部</option>
								<option value="0">合格</option>
								<option value="1">不合格</option>
							</select> <input type="hidden" hidName="hid.fsp.map.if_qualified" id="if_qualifiedHid"
											 class="notselect" value="${fsp.map.if_qualified}" /></td>
							<td height="40"><label class="">发货日期：</label></td>
							<td><input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.wldel_wf_real_finish1" value="${fsp.map.wldel_wf_real_finish1}" onFocus="WdatePicker({readOnly:true})" /> - <input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.wldel_wf_real_finish2" value="${fsp.map.wldel_wf_real_finish2}" onFocus="WdatePicker({readOnly:true})" /></td>

						</tr>
						<tr>
							<td colspan="8" align="right">
								<input id="search_btn" type="button" value="查&nbsp;&nbsp;询" class="z_submit2" />
								<input id="reset_btn" type="button" value="重&nbsp;&nbsp;置" class="z_submit2" />
							</td>
						</tr>
					</table></td>
			</tr>
		</table>
	</form>
</div>
<div style="width:100%;overflow-x:scroll;overflow-y:hidden">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		class="z_table_style2">
		<tr>
			<th width="8"></th>
			<th width="80">订单号</th>
			<th width="80">订单类型</th>
			<th width="80">款号</th>
			<th width="80">款式描述</th>
			<th height="40" width="80">客户名称</th>
			<th width="80">数量</th>
			<th width="80">下单日期</th>
			<th width="80">订单进度</th>
			<th width="80">负责MR</th>
			<th width="80">工厂</th>
			<th width="80">TPE</th>
			<th width="80">车缝产出数量</th>
			<th width="80">检验合格数量</th>
			<th width="80">QA完成日期</th>
			<th width="80">实际生产周期</th>
			<th width="80">订单完成日期</th>
			<%--<th class="z_title_sty6" style="text-align: left;">操作</th>--%>
		</tr>
		<s:if test="superList.size()==0">
			<tr>
				<td colspan="11" style="text-align: center;">当前没有任何数据！</td>
			</tr>
		</s:if>
		<s:iterator value="superList" status="status">
			<tr>
				<td></td>
				<td>
					<a class="z_title_sty5" href="/bx/orderDetail?oaOrderDetail.id=${map.oa_order_detail}"
					style="word-wrap: break-word;">${map.sell_order_code}</a></td>
				<td><s:if test="map.type==1">模拟报价</s:if> <s:if test="map.type==2">样衣打版</s:if> <s:if test="map.type==3">大货生产</s:if></td>
				<td class="z_title_sty1">
					<a class="z_title_sty5" target="_blank" href="/jsp/outside/styleDetail.jsp?styleId=${map.style_id}">${map.style_code}</a>
				</td>
				<td>${map.style_desc}</td>
				<td>${map.cus_name}</td>
				<td>${map.want_cnt}</td>
				<td>${map.begin_time}</td>
				<td>${map.wf_step_name}</td>
				<td>${map.mr_name}</td>
				<td>${map.sewing_factory}</td>
				<td>${map.tpe_name}</td>
				<td>${map.sewing_total}</td>
				<td>${map.qualified_total}</td>
				<td>${map.qadel_wf_real_finish}</td>
				<td>${map.actualDay}</td>
				<td>${map.end_time}</td>
				<%--<td class="z_title_sty1">${map.cus_code}</td>--%>
				<%--<td class="z_title_sty1"><a class="z_title_sty5" target="_blank"--%>
					<%--href="/jsp/outside/styleDetail.jsp?styleId=${map.style_id}">${map.style_code}</a></td>--%>
				<%--<td class="z_title_sty1">${map.wf_step_name}</td>--%>
				<%--<td class="z_title_sty3">${map.end_time}</td>--%>
				<%--<td class="z_title_sty3"><s:property--%>
						<%--value="%{@com.xbd.oa.utils.WebUtil@minusTime(map.wf_plan_start)}" /></td>--%>
				<%--<td class="z_title_sty3"><s:property--%>
						<%--value="%{@com.xbd.oa.utils.WebUtil@minusTime(map.except_finish)}" /></td>--%>
				<%--<td class="z_title_sty1"><s:property--%>
						<%--value="%{@com.xbd.oa.utils.WebUtil@getOrderTypeStr(map.type)}" /></td>--%>
				<%--<td class="z_title_sty5">${map.operator}</td>--%>
				<%--<td class="z_title_sty5"><a--%>
					<%--href="/bx/viewOrderDetail?oaOrder.id=${map.id}">进度</a></td>--%>
			</tr>
		</s:iterator>
	</table>


	<table cellspacing="0" cellpadding="0" border="1" class="z_table_style2" width="1008" style="margin-top: 20px; text-align: center">
		<tr>
			<td>车缝总产出</td>
			<td>${statisticsMap.want_count}</td>
			<td>检验合格数量</td>
			<td>${statisticsMap.qualified_nums} </td>
			<td>平均QA合格率</td>
			<td>${statisticsMap.qalu}%</td>
			<td>及时率</td>
			<td>${statisticsMap.timeOutLv}%</td>
			<td>平均生产周期</td>
			<td>${statisticsMap.actualDays} </td>
			<td>平均生产TOC</td>
			<td>${statisticsMap.toclv}% </td>
		</tr>
	</table>
</div>
<%-- <s:include value="%{ctx}/jsp/parts/pageControl.jsp"></s:include> --%>
<s:include value="/jsp/parts/page.jsp"></s:include>

<s:if test="superList.size() > 0">
    <div style="float: right; margin-top: -45px; margin-right: 250px;">
        <input id="output" type="button" value="导出报表" style="width: 120px;" />
    </div>
</s:if>


<script type="text/javascript">
	var sales = "${fsp.map.sales}";
	var mr_name = "${fsp.map.mr_name}";
	$(function() {
		require([ "order_his" ], function(fn) {
			fn.init();
		});
	});

	
</script>