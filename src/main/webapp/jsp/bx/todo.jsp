<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<title>${systemEnvironment }今日工作|SingbadaERP</title>
</head>
<link rel="stylesheet" type="text/css" href="/css/form3.css" />
<div class="z_assign_list" id="form_search_div">
	<form method="post" action="/bx/todo">
		<input type="hidden" name="orderField" id="orderField"
			value="${bean.map.orderField}" /> <input type="hidden"
			name="orderType" id="orderType" value="${bean.map.orderType}" />
		<table cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td><table cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="60" height="40"><label class="">客户名称：</label></td>
							<td width="135"><input type="text" class="z_inp2"
								name="fsp.map.cus_name" value="${fsp.map.cus_name}"
								maxlength='20' /></td>
							<td width="60"><label class="">款号：</label></td>
							<td width="135"><input type="text" class="z_inp2"
								name="fsp.map.style_code" value="${fsp.map.style_code}"
								maxlength='20' /></td>
							<td width="60"><label class="">订单编号：</label></td>
							<td width="135"><input type="text" class="z_inp2"
								name="fsp.map.sell_order_code"
								value="${fsp.map.sell_order_code}" maxlength='20' /></td>
							<td height="40"><label class="">订单类型：</label></td>
							<td width="135"><select name="fsp.map.type" class="z_inp3">
									<option value="">全部</option>
									<option value="1"
										<s:if test='fsp.map.type=="1"'>selected="selected"</s:if>>模拟报价</option>
									<option value="2"
										<s:if test='fsp.map.type=="2"'>selected="selected"</s:if>>样衣打版</option>
									<option value="3"
										<s:if test='fsp.map.type=="3"'>selected="selected"</s:if>>大货生产</option>
							</select></td>
							<td><input type="submit" value="查&nbsp;&nbsp;询"
								class="z_submit2" /></td>
						</tr>
					</table></td>
			</tr>
		</table>
	</form>
</div>

<table border="0" cellspacing="0" cellpadding="0" class="z_table_style2"
	width="1008">
	<tr>
		<th width="60" height="40" class="orderBy" name="data"
			style="cursor: pointer;">优先级</th>
		<th width="65">订单编号</th>
		<th width="60">订单类型</th>
		<th width="65">款号</th>
		<th width="115">款式描述</th>
		<th width="135">客户名称</th>
		<th width="50">数量</th>
		<th width="70" class="orderBy" name="begin_time"
			style="cursor: pointer;">创建日期</th>
		<!-- <th width="90">剩余时间</th> -->
		<th width="90">当前节点剩余时间</th>
		<th width="50">负责MR</th>
		<th width="100">当前节点</th>
		<th width="50">负责人</th>
		<th colspan="3">操作</th>
	</tr>
	<s:if test="beans.size()==0">
		<tr>
			<td colspan="18" style="text-align: center;">当前没有任何数据！</td>
		</tr>
	</s:if>
	<s:iterator value="beans" status="st">
		<tr>
			<td><s:if test="map.data < 0">
					<div
						style="background-color: #0000FF; color: #fff; height: 22px; line-height: 22px; text-align: center;">${map.data }%</div>
				</s:if> <s:elseif test="map.data < 33">
					<div
						style="background-color: #33cc00; color: #fff; height: 22px; line-height: 22px; text-align: center;">${map.data }%</div>
				</s:elseif> <s:elseif test="map.data < 66">
					<div
						style="background-color: #ff9900; color: #fff; height: 22px; line-height: 22px; text-align: center;">${map.data }%</div>
				</s:elseif> <s:elseif test="map.data <= 100">
					<div
						style="background-color: #ff3300; color: #fff; height: 22px; line-height: 22px; text-align: center;">${map.data }%</div>
				</s:elseif> <s:else>
					<div
						style="background-color: #000; color: #fff; height: 22px; line-height: 22px; text-align: center;">${map.data }%</div>
				</s:else></td>
			<td><a class="z_title_sty5"
				href="/bx/orderDetail?oaOrderDetail.id=${map.oa_order_detail}"
				style="word-wrap: break-word;">${map.sell_order_code}</a></td>
			<td><s:property
					value="%{@com.xbd.oa.utils.WebUtil@getOrderTypeStr(map.type)}" /></td>
			<td><a class="z_title_sty5" target="_blank"
				href="${pageContext.request.contextPath}/jsp/outside/styleDetail.jsp?styleId=${map.style_id}">${map.style_code}</a></td>
			<td>${map.style_desc}</td>
			<td>${map.cus_name}</td>
			<td>${map.want_cnt}</td>
			<td>${map.begin_time}</td>
			<%-- <td class="z_title_sty3"><s:property
					value="%{@com.xbd.oa.utils.WebUtil@minusTime(map.except_finish)}" /></td> --%>
			<td class="z_title_sty3"><s:property
					value="%{@com.xbd.oa.utils.WebUtil@minusTimeAndOffset(map.wf_real_start,map.wf_step_duration)}" /></td>
			<td>${map.mr_name}</td>
			<td>${map.wf_step_name}</td>
			<td>${map.operator}</td>
			<s:if
				test="#session.is_manager != null && #session.is_manager.admin != map.operator">
				<td class="z_title_sty1" colspan="3">${map.operator}
			</s:if>
			<s:elseif
				test="#session.is_manager != null && #session.is_manager.admin == map.operator">
				<td class="z_title_sty5" colspan="3"><a class="assign_btn"
					oaOrderDetailId="${map.oa_order_detail}" oaOrderId="${map.id}"
					href="javascript:void(0)">分配</a> <a
					href="/bx/processOrder?oaOrderDetail.id=${map.oa_order_detail}">处理</a>
			</s:elseif>
			<s:else>
				<td class="z_title_sty5" colspan="3"><a
					href="/bx/processOrder?oaOrderDetail.id=${map.oa_order_detail}">处理</a>
			</s:else>
			<a class="z_title_sty5"
				href="/bx/viewOrderDetail?oaOrder.id=${map.id}">进度</a>
			</td>
		</tr>
	</s:iterator>
</table>
<div class="fab_form" id="staffListForm">
	<div class="z_distribut_title">
		<span>分配订单</span> <a href="javascript:void(0);" class="cancel">X</a>
	</div>
	<div class="z_distribut_con">
		<ul class="z_distribut_detail" id="operatorList">
		</ul>
		<input type="button" id="assignConfirmBtn" value="确&nbsp;&nbsp;定"
			class="z_submit3" style="margin-left: 60px;" /> <input type="button"
			value="取&nbsp;&nbsp;消" class="z_submit3 z_close cancel" />
	</div>
</div>
<table cellspacing="0" cellpadding="0" border="1" class="z_table_style2"
	width="950" style="margin-top: 10px; text-align: center">
	<tr>
		<td>生产总数</td>
		<td>${bean.map.counts[0]}</td>
		<td>黑色订单</td>
		<td>${bean.map.counts[1]}(${bean.map.scale[0]}%)</td>
		<td>红色订单</td>
		<td>${bean.map.counts[2]}(${bean.map.scale[1]}%)</td>
		<td>黄色订单</td>
		<td>${bean.map.counts[3]}(${bean.map.scale[2]}%)</td>
		<td>绿色订单</td>
		<td>${bean.map.counts[4]}(${bean.map.scale[3]}%)</td>
		<td>蓝色订单</td>
		<td>${bean.map.counts[5]}(${bean.map.scale[4]}%)</td>
	</tr>
</table>
<s:include value="/jsp/parts/page.jsp"></s:include>
<%-- <s:include value="%{ctx}/jsp/parts/pageControl.jsp"></s:include> --%>
<script type="text/javascript">
	$(function() {
		require([ "todo" ], function(fn) {
			fn.init();
		});
	});
</script>
