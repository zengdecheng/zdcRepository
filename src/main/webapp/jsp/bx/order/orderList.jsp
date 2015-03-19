<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script language="javascript" type="text/javascript" src="${ctx}/js/pub/My97DatePicker/WdatePicker.js"></script>
<link rel="Stylesheet" href="/css/jquery.autocomplete.css" />
<script type="text/javascript" src="/js/bx/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/js/pub/jquery.form.js"></script>
<html>
<head>
	<title>${systemEnvironment }订单列表 | Singbada ERP</title>
	<style>
		.yldp_table {
			width: 100%;
			font-size: 12px;
			text-align: left;
			border-collapse: collapse;
			border: none;
		}
	</style>
</head>
<body>
<div class="z_assign_list" id="form_search_div">
	<form method="post" id="orderList">
		<table cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td width="65" height="40"><label class="">订单号：</label></td>
				<td width="185"><input type="text" class="z_inp2" name="fsp.map.sell_order_code" style="width:167px;" value="${fsp.map.sell_order_code}" maxlength='20' /></td>
				<td width="65"><label class="">男/女款：</label></td>
				<td width="185"><s:select cssClass="z_inp2" list="{'男','女','儿童'}" theme="simple" headerKey="" name="fsp.map.style_type" value="%{fsp.map.style_type}" cssStyle="width: 170px" headerValue="请选择"></s:select></td>
				<td width="65"><label class="">特殊工艺：</label></td>
				<td width="185">
				<s:select theme="simple" list="{'印花','绣花','订珠','缩折/打条','打揽','洗水','其他'}" name="fsp.map.style_craft" value="%{fsp.map.style_craft}" headerKey="" headerValue="请选择" cssStyle="width:167px;" cssClass="z_inp2"></s:select>
				</td>
				<td height="40"><label class="">订单类型：</label></td>
				<td><s:select id="typeSelect" theme="simple" list="#{'':'全部','1':'模拟报价','2':'样衣打版','3':'大货生产'}" cssClass="z_inp2" cssStyle="width: 170px;" value="%{fsp.map.type}" name="fsp.map.type"></s:select></td>
			</tr>
			<tr>
				<td height="40"><label class="">款号：</label></td>
				<td><input type="text" class="z_inp2" name="fsp.map.style_code" style="width:167px;" value="${fsp.map.style_code}" maxlength='20' /></td>
				<td><label class="">一级分类：</label></td>
				<td><s:select  theme="simple" list="#{'针织裤子':'针织裤子','梳织裤子':'梳织裤子','针织上衣':'针织上衣','梳织上衣':'梳织上衣','连衣裙':'连衣裙','半身裙':'半身裙','羽绒':'羽绒','棉衣':'棉衣','夹克/外套':'夹克/外套','牛仔':'牛仔'}" headerKey="" name="fsp.map.style_class" value="%{fsp.map.style_class}" cssStyle="width: 167px" cssClass="z_inp2" headerValue="请选择"></s:select></td>
				<td><label class="">客户：</label></td>
				<td><input type="text" name="fsp.map.cus_name" value="${fsp.map.cus_name}" class="z_inp2" style="width:167px;" maxlength='20' /></td>
				<td><label class="">分公司：</label></td>
				<td>
					<s:select theme="simple" list="{'杭州','广州'}" cssClass="z_inp2" cssStyle="width: 170px;" value="%{fsp.map.city}" name="fsp.map.city" headerKey="" headerValue="请选择"></s:select>
				</td>
			</tr>
			<tr>
				<td height="40"><label class="">销售：</label></td>
				<td><select id="crm_staff_sales" class="z_inp2" style="width: 170px;" name="fsp.map.sales" value="${fsp.map.sales}"></select></td>
				<td><label class="">MR跟单：</label></td>
				<td><select id="crm_staff_mr" class="z_inp2" style="width: 170px;" name="fsp.map.mr_name" value="${fsp.map.mr_name}"></select></td>
				<td><label class="">TPE：</label></td>
				<td><s:select theme="simple" list="@com.xbd.oa.utils.XbdBuffer@getStaffsByGroupName('qc')" listKey="map.login_name" listValue="map.login_name" headerKey="" headerValue="请选择" cssClass="z_inp2" cssStyle="width: 170px;" name="fsp.map.tpe_name" value="%{fsp.map.tpe_name}"></s:select></td>
				<td><label class="">当前节点：</label></td>
				<td><select name="fsp.map.wf_step" id="nodeSelect" class="z_inp2" style="width:170px;">
						<option value="">请选择</option>
					</select><input type="hidden" id="nodeSelectHid" value="${fsp.map.wf_step }"></td>
			</tr>
			<tr>
				<td height="40"><label class="">创建日期：</label></td>
				<td><input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.start_time1" value="${fsp.map.start_time1}" onFocus="WdatePicker({readOnly:true})" /> - <input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.start_time2" value="${fsp.map.start_time2}" onFocus="WdatePicker({readOnly:true})" /></td>
				<td><label class="">紧急订单：</label></td>
				<td><s:select cssClass="z_inp2" list="#{'':'全部','0':'加急','1':'普通'}" theme="simple" name="fsp.map.is_urgent" value="%{fsp.map.is_urgent}" cssStyle="width: 170px"></s:select></td>

				<td><label class="">订单颜色：</label></td>
				<td>
					<input type="hidden" id="orderColorHid" value="${orderColor}" />
					<s:select cssClass="z_inp2" list="#{'':'全部','-1':'蓝色','0':'绿色','33':'橙色','66':'红色','100':'黑色'}" id="orderColor" theme="simple" name="orderColor" value="%{orderColor}" cssStyle="width: 170px"></s:select></td>

				<td><label class="">工厂：</label></td>
				<td><input type="text" name="fsp.map.sewing_factory" value="${fsp.map.sewing_factory}" class="z_inp2" style="width:167px;" maxlength='20' /></td>
			</tr>

			<tr>
				<td height="40"><label class="">MR补录订单日期：</label></td>
				<td><input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.odel_wf_real_start" value="${fsp.map.odel_wf_real_start}" onFocus="WdatePicker({readOnly:true})" /> - <input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.odel_wf_real_start1" value="${fsp.map.odel_wf_real_start1}" onFocus="WdatePicker({readOnly:true})" /></td>

				<td height="40"><label class="">MR完成日期：</label></td>
				<td><input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.odel_wf_real_finish" value="${fsp.map.odel_wf_real_finish}" onFocus="WdatePicker({readOnly:true})" /> - <input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.odel_wf_real_finish1" value="${fsp.map.odel_wf_real_finish1}" onFocus="WdatePicker({readOnly:true})" /></td>

                <td height="40"><label class="">技术完工日期（大货）：</label></td>
                <td><input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.ppc_wf_real_finish" value="${fsp.map.ppc_wf_real_finish}" onFocus="WdatePicker({readOnly:true})" /> - <input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.ppc_wf_real_finish1" value="${fsp.map.ppc_wf_real_finish1}" onFocus="WdatePicker({readOnly:true})" /></td>

                <td height="40"><label class="">QA完工日期：</label></td>
                <td><input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.odelqa_wf_real_finish" value="${fsp.map.odelqa_wf_real_finish}" onFocus="WdatePicker({readOnly:true})" /> - <input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.odelqa_wf_real_finish1" value="${fsp.map.odelqa_wf_real_finish1}" onFocus="WdatePicker({readOnly:true})" /></td>
            </tr>

            <tr>
                <td colspan="8" align="right"><input type="submit" value="查询" style="width: 60px;" />
                    <input id="reset_btn" type="button" value="重置" style="width: 60px;" />
                </td>
            </tr>
		</table>
	</form>
</div>
<div style="width:100%;overflow-x:scroll;overflow-y:hidden">
	<table border="0" cellspacing="0" cellpadding="0" class="z_table_style2" width="1208">
		<tr>
			<th width="8" height="40"></th>
			<th width="40" height="40">优先级</th>
			<th width="8" height="40"></th>
			<th width="80">订单号</th>
			<th width="70">订单类型</th>
			<th width="70">款号</th>
			<th width="100">款式描述</th>
			<th width="80">客户名称</th>
			<th width="50">数量</th>
			<th width="80">创建日期</th>
			<th width="90">建议投料日期</th>
			<th width="80">MR完成日期</th>
			<th width="90">技术完成日期（大货）</th>
			<th width="90">品类</th>
			<th width="50">负责MR</th>
			<th width="70">当前节点</th>
			<th width="50">负责人</th>
			<th>操作</th>
		</tr>
		<s:if test="beans.size()==0">
			<tr><td colspan="16" style="text-align:center;">当前没有任何数据！</td></tr>	
		</s:if>
		<s:iterator value="beans" status="st">
			<tr>
				<td></td>
				<td>
					<s:if test="map.data >= 0 && map.data <= 33">
						<div style="background-color:#33cc00;color:#fff;height:22px;line-height: 22px; text-align: center;">${map.data }%</div>
					</s:if>
					<s:elseif test="map.data >= 0 && map.data <= 66">
						<div style="background-color:#ff9900;color:#fff;height:22px;line-height: 22px; text-align: center;">${map.data }%</div>
					</s:elseif>
					<s:elseif test="map.data >= 0 && map.data <= 100">
						<div style="background-color:#ff3300;color:#fff;height:22px;line-height: 22px; text-align: center;">${map.data }%</div>
					</s:elseif>
					<s:elseif test="map.data < 0">
						<div style="background-color:rgb(0, 3, 255);color:#fff;height:22px;line-height: 22px; text-align: center;">${map.data }%</div>
					</s:elseif>
					<s:else>
						<div style="background-color:#000;color:#fff;height:22px; line-height: 22px; text-align: center;">${map.data }%</div>
					</s:else>
				</td>
				<td></td>
				<td><a class="z_title_sty5" href="/bx/orderDetail?oaOrderDetail.id=${map.oa_order_detail}" style="word-wrap: break-word;">${map.sell_order_code}</a></td>
				<td><s:if test="map.type==1">模拟报价</s:if> <s:if test="map.type==2">样衣打版</s:if> <s:if test="map.type==3">大货生产</s:if></td>
				<td><a class="z_title_sty5" target="_blank" href="${pageContext.request.contextPath}/jsp/outside/styleDetail.jsp?styleId=${map.style_id}">${map.style_code}</a></td>
				<td>${map.style_desc}</td>
				<td>${map.cus_name}</td>
				<td>${map.want_cnt}</td>
				<td>${map.begin_time}</td>
				<td>${map.feeding_time}</td>
				<td>${map.odel_wf_real_finish}</td>
				<td>${map.ppc_wf_real_finish}</td>
				<td>${map.style_class}</td>
				<td>${map.mr_name}</td>
				<td>${map.wf_step_name}</td>
				<td>${map.operator}</td>
				<td><a class="z_title_sty5" href="/bx/viewOrderDetail?oaOrder.id=${map.id}">进度</a>	</td>
			</tr>
		</s:iterator>
	</table>
</div>

<table cellspacing="0" cellpadding="0" border="1" class="z_table_style2" width="1008" style="margin-top: 20px; text-align: center">
	<tr>
		<td>生产总数</td>
		<td>${statisticsMap.want_count}</td>
		<td>黑色订单</td>
		<td>${statisticsMap.blackNum} (${statisticsMap.black}%)</td>
		<td>红色订单</td>
		<td>${statisticsMap.redNum} (${statisticsMap.red}%)</td>
		<td>黄色订单</td>
		<td>${statisticsMap.orangeNum} (${statisticsMap.orange}%)</td>
		<td>绿色订单</td>
		<td>${statisticsMap.greenNum} (${statisticsMap.green}%)</td>
		<td>蓝色订单</td>
		<td>${statisticsMap.blueNum} (${statisticsMap.green}%)</td>
	</tr>
</table>

	<s:include value="/jsp/parts/page.jsp"></s:include>

<s:if test="beans.size() > 0">
    <div style="float: right; margin-top: -45px; margin-right: 250px;">
        <input id="output" type="button" value="导出报表" style="width: 120px;" />
    </div>
</s:if>
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