<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script language="javascript" type="text/javascript" src="${ctx}/js/pub/My97DatePicker/WdatePicker.js"></script>
<link rel="Stylesheet" href="/css/jquery.autocomplete.css" />
<script type="text/javascript" src="/js/bx/jquery.autocomplete.js"></script>
<html>
<head>
	<title>${systemEnvironment }订单进度报表 | Singbada ERP</title>
</head>
<body>
<div class="z_assign_list" id="form_search_div">
	<form id="search_form" method="post" action="/bx/orderProgressReport">
		<table cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td height="40"><label class="">订单类型：</label></td>
				<td><s:select id="typeSelect" theme="simple" list="#{'3':'大货生产','2':'样衣打版','1':'模拟报价'}" cssClass="z_inp2" cssStyle="width: 170px;margin-right:19px" value="%{fsp.map.type}" name="fsp.map.type"></s:select></td>
				<td><label class="">客户：</label></td>
				<td><input type="text" name="fsp.map.cus_name" value="${fsp.map.cus_name}" class="z_inp2" style="width:167px;margin-right:19px" maxlength='20' /></td>
				<td height="40"><label class="">款号：</label></td>
				<td><input type="text" class="z_inp2" name="fsp.map.style_code" style="width:167px;margin-right:19px" value="${fsp.map.style_code}" maxlength='20' /></td>
				<td><label class="">工厂：</label></td>
				<td><input type="text" name="fsp.map.sewing_factory" value="${fsp.map.sewing_factory}" class="z_inp2" style="width:167px;" maxlength='20' /></td>
			</tr>
			<tr>
				<td><label class="">节点：</label></td>
				<td><select name="fsp.map.wf_step" id="nodeSelect" class="z_inp2" style="width:170px;">
						<option value=''>请选择</option>
						<option value='c_mr_improve_2'>订单详情</option>
						<option value='c_ppc_assign_3'>技术</option>
						<option value='c_fi_pay_4'>采购</option>
						<option value='c_ppc_factoryMsg_5'>CQC</option>
						<option value='c_qc_cutting_6'>TPE</option>
						<option value='c_ppc_confirm_7'>QA</option>
						<option value='c_qc_printing_8'>财务</option>
						<option value='c_ppc_confirm_9'>物流</option>
					</select>
					<input type="hidden" id="nodeSelectHid" value="${fsp.map.wf_step }">
				</td>
				<td><label class="">处理人：</label></td>
				<td>
					<select  name="fsp.map.operator" id="operatorSelect" class="z_inp2"  style="width:170px;">
						<option value=''>请选择</option>
					</select>
					<input type="hidden" id="operatoreSelectHid" value="${fsp.map.operator }">
				</td>
				<td height="40"><label class="">流入时间：</label></td>
				<td><input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.start_time1" value="${fsp.map.start_time1}" onFocus="WdatePicker({readOnly:true})" /> - <input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.start_time2" value="${fsp.map.start_time2}" onFocus="WdatePicker({readOnly:true})" /></td>
				<td height="40"><label class="">流出时间：</label></td>
				<td><input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.end_time1" value="${fsp.map.end_time1}" onFocus="WdatePicker({readOnly:true})" /> - <input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.end_time2" value="${fsp.map.end_time2}" onFocus="WdatePicker({readOnly:true})" /></td>
			</tr>
			<tr>
				<%-- <td height="40"><label class="">创建日期：</label></td>
				<td><input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.start_time1" value="${fsp.map.start_time1}" onFocus="WdatePicker({readOnly:true})" /> - <input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.start_time2" value="${fsp.map.start_time2}" onFocus="WdatePicker({readOnly:true})" /></td>
				<td><label class="">紧急订单：</label></td>
				<td><s:select cssClass="z_inp2" list="#{'':'全部','0':'加急','1':'普通'}" theme="simple" name="fsp.map.is_urgent" value="${fsp.map.is_urgent}" cssStyle="width: 170px"></s:select></td> --%>
				<td><input type="submit" value="查询" style="width: 60px;" /></td>
				<td><input id="reset" type="button" value="重置" style="width: 60px;" /></td>
			</tr>
		</table>
	</form>
</div>
<div style="width:100%;overflow-x:scroll;overflow-y:hidden">
	<table border="0" cellspacing="0" cellpadding="0" class="z_table_style2" width="1008">
		<tr>
			<th width="32" height="40" align="center">序号</th>
			<th width="75" height="40">订单号</th>
			<th width="173" height="40">客户</th>
			<th width="70">一级分类</th>
			<th width="194">二级分类</th>
			<th width="70">MR处理人</th>
			<s:if test="fsp.map.type eq 2">
				<th width="96">MR用时</th>
				<th width="96">采购用时</th>
				<th width="96">技术用时</th>
				<th width="96">核价用时</th>
				<th width="96">MR确认用时</th>
				<th width="96">汇总</th>
				<th width="64">订单数量</th>
			</s:if>
			<s:else>
				<th width="70">TPE</th>
				<th width="93">车缝工厂</th>
				<th width="96">MR</th>
				<th width="96">技术部</th>
				<th width="96">采购部</th>
				<th width="96">CQC用时</th>
				<th width="96">TPE用时</th>
				<th width="96">QA验货用时</th>
				<th width="96">财务确认用时</th>
				<th width="96">QA发货用时</th>
				<th width="96">汇总</th>
				<th width="64">订单数量</th>
				<th width="64">车缝数量</th>
				<th width="64">合格数量</th>
				<th width="64">不合格数量</th>
			</s:else>
		</tr>
		<s:if test="beans.size()==0">
			<tr><td colspan="16" style="text-align:center;">当前没有任何数据！</td></tr>	
		</s:if>
		<s:if test="fsp.map.type eq 2">
			<s:iterator value="beans" status="st">
				<tr>
					<td align="center">${st.count + (fsp.pageNo-1) * fsp.pageSize}</td>
					<td><a class="z_title_sty5" href="/bx/orderDetail?oaOrderDetail.id=${map.oa_order_detail}" style="word-wrap: break-word;">${map.sell_order_code}</a></td>
					<td>${map.cus_name }</td>
					<td>${map.style_class }</td>
					<td>${map.value }</td>
					<td>${map.mrdb_operator }</td>
					<td class="color_td">${map.b_mr_improve_2 } <input type="hidden" name="wtep_color" value="${map.b_mr_improve_2_color}" /></td>
					<td class="color_td">${map.b_ppc_confirm_3 } <input type="hidden" name="wtep_color" value="${map.b_ppc_confirm_3_color}" /></td>
					<td class="color_td">${map.b_pur_confirm_4 } <input type="hidden" name="wtep_color" value="${map.b_pur_confirm_4_color}" /></td>
					<td class="color_td">${map.b_ppc_confirm_5 } <input type="hidden" name="wtep_color" value="${map.b_ppc_confirm_5_color}" /></td>
					<td class="color_td">${map.b_qc_confirm_6 } <input type="hidden" name="wtep_color" value="${map.b_qc_confirm_6_color}" /></td>
					<td class="color_td">${map.huizong } <input type="hidden" name="wtep_color" value="${map.huizong_color}" /></td>
					<td>${map.order_num }</td>
				</tr>
			</s:iterator>
            <tr>
                <td colspan="6" align="right">平均值：</td>
                <td>${mapCount.avgMRDB }</td>
                <td>${mapCount.avgPurDB }</td>
                <td>${mapCount.avgTechnologyDB }</td>
                <td>${mapCount.avgHeJiaDB }</td>
                <td>${mapCount.avgMrCheckDB }</td>
                <td>${mapCount.avgOrderSumDB }</td>
                <td>${mapCount.orderNumSumDB }</td>
            </tr>
		</s:if>
		<s:else>
			<s:iterator value="beans" status="st">
				<tr>
					<td>${st.count  + (fsp.pageNo-1) * fsp.pageSize}</td>
					<td><a class="z_title_sty5" href="/bx/orderDetail?oaOrderDetail.id=${map.oa_order_detail}" style="word-wrap: break-word;">${map.sell_order_code}</a></td>
					<td>${map.cus_name }</td>
					<td>${map.style_class }</td>
					<td>${map.value }</td>
					<td>${map.mrdel_operator }</td>
					<td>${map.tpedel_operator }</td>
					<td>${map.sewing_factory }</td>
					<td class="color_td">${map.c_mr_improve_2 } <input type="hidden" name="wtep_color" value="${map.c_mr_improve_2_color}" /></td>
					<td class="color_td">${map.c_ppc_assign_3 } <input type="hidden" name="wtep_color" value="${map.c_ppc_assign_3_color}" /></td>
					<td class="color_td">${map.c_fi_pay_4 } <input type="hidden" name="wtep_color" value="${map.c_fi_pay_4_color}" /></td>
					<td class="color_td">${map.c_ppc_factoryMsg_5 } <input type="hidden" name="wtep_color" value="${map.c_ppc_factoryMsg_5_color}" /></td>
					<td class="color_td">${map.c_qc_cutting_6 } <input type="hidden" name="wtep_color" value="${map.c_qc_cutting_6_color}" /></td>
					<td class="color_td">${map.c_ppc_confirm_7 } <input type="hidden" name="wtep_color" value="${map.c_ppc_confirm_7_color}" /></td>
					<td class="color_td">${map.c_qc_printing_8 } <input type="hidden" name="wtep_color" value="${map.c_qc_printing_8_color}" /></td>
					<td class="color_td">${map.c_ppc_confirm_9 } <input type="hidden" name="wtep_color" value="${map.c_ppc_confirm_9_color}" /></td>
					<td class="color_td">${map.huizong } <input type="hidden" name="wtep_color" value="${map.huizong_color}" /></td>
					<td>${map.order_num }</td>
					<td>${map.sewing_total }</td>
					<td>${map.qualified_total }</td>
					<td>${map.unqualified_total }</td>
				</tr>
			</s:iterator>
                <tr>
                    <td colspan="8" align="right">平均值：</td>
                    <td>${mapCount.avgMRDH }</td>
                    <td>${mapCount.avgTechnologyDH }</td>
                    <td>${mapCount.avgPurDH }</td>
                    <td>${mapCount.avgCQCDH }</td>
                    <td>${mapCount.avgTPEDH }</td>
                    <td>${mapCount.avgQADH }</td>
                    <td>${mapCount.avgFinanceDH }</td>
                    <td>${mapCount.avgLogisticsDH }</td>
                    <td>${mapCount.avgOrderSumDH }</td>
                    <td>${mapCount.orderNumSumDH }</td>
                    <td>${mapCount.sewingNumSumDH }</td>
                    <td>${mapCount.qualifiedNumSumDH }</td>
                    <td>${mapCount.unqualifiedNumSumDH }</td>
                </tr>
		</s:else>
		
	
	</table>
</div>
<s:include value="/jsp/parts/page.jsp"></s:include>
<s:if test="beans.size()!=0">
	<div  style="height: 40px; line-height: 40px;float: right; width: 40%; position: relative; bottom: 45px;">
		<input id="exportReport" type="button" class="process_button" style="height: 25px;margin-right: 10px;width: 100px;" value="导出报表">
	</div>
</s:if>
<script type="text/javascript">
	$(function() {
		require([ "order/orderProgressReport"], function(fn) {
			fn.init();
		});
	});
</script>
</body>
</html>