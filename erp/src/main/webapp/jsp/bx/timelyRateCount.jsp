<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script language="javascript" type="text/javascript"
	src="${ctx}/js/pub/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="${ctx}/css/timelyRateCount.css">
<head>
	<title>${systemEnvironment }及时率统计 | Singbada ERP</title>
</head>
<div >
		<a href="${ctx }/bx/timelyRateCount" class="btn_lan" id="daHuo">大货生产</a>
		<a href="${ctx }/bx/daBanTimelyRateCount" class="btn_lan" id="daBan">样衣打版</a>
		<span class="daoChu_span">今天各部门大货生产及时率：</span>
		<input id="search_btn" type="button" onclick="downCSV()" value="导&nbsp;&nbsp;出" class="daoChu" />
</div>
<script type="text/javascript">
     	function downCSV(){
			//var params = $("#form_search_div").find("input[name^='fsp.map'][type='text'][value!=''],select[name^='fsp.map'][value!=''][value!='0']").serialize();
//			window.location.href = "/mr/export_csv?" + (!!params ? "&" + params : "");
			$.get("/bx/getDaHuoTimelyRate",function(data){
				window.location.href=data;
			});
     		
     	}
</script>
<table width="1071" border="0" cellspacing="0" cellpadding="0"
	class="z_table_style2">
	<tr>
		<td colspan="5" align="center"><h3>当前大货生产订单及时率统计</h3></td>
	</tr>
	<tr >
		<th width="1px"><span style="position:relative;left:55px;">部门</span></th>
		<th width="105" height="40">类别</th>
		<th width="95">异动订单数</th>
		<th width="95">订单总数</th>
		<th width="95">及时率</th>
	</tr>
	<s:iterator value="stepCount" status="status">
		<s:if test="#status.isLast()">
			<tr>
				<td width="5px"></th>
				<td width="105" height="40">总计</th>
				<td width="95">${fsp.map.timelyTotal }</th>
				<td width="95">${map.stepcount }</th>
				<td width="95">${fsp.map.timelyRate }%</th>
			</tr>
		</s:if>
		<s:else>	
			<tr>
				<%-- <td width="5"></td>
				<td>${map.wf_step_name }</td> --%>
				<s:if test='map.wf_step=="c_fi_confirm_2"'>
					<td width="5"><span style="position:relative;left:55px;">财务</span></td>
					<td>${map.wf_step_name }</td>
					<td>${fsp.map.daoZhang2 }</td>
					<td>${map.stepcount }</td>
					<td>${fsp.map.daoZhangRate }%</td>
				</s:if>
				<s:elseif test='map.wf_step=="c_ppc_assign_3"'>
					<td width="5"><span style="position:relative;left:55px;">技术部</span></td>
					<td>${map.wf_step_name }</td>
					<td>${fsp.map.zhiYang3 }</td>
					<td>${map.stepcount }</td>
					<td>${fsp.map.zhiYangRate }%</td>
				</s:elseif>
				<s:elseif test='map.wf_step=="c_fi_pay_4"'>
					<td width="5"><span style="position:relative;left:55px;">采购</span></td>
					<td>${map.wf_step_name }</td>
					<td>${fsp.map.mianLiao4 }</td>
					<td>${map.stepcount }</td>
					<td>${fsp.map.mianLiaoRate }%</td>
				</s:elseif>
				<s:elseif test='map.wf_step=="c_ppc_factoryMsg_5"'>
					<td width="5"><span style="position:relative;left:55px;">CQC</span></td>
					<td>${map.wf_step_name }</td>
					<td>${fsp.map.yanBu5 }</td>
					<td>${map.stepcount }</td>
					<td>${fsp.map.yanBuRate }%</td>
				</s:elseif>
				<s:elseif test='map.wf_step=="c_qc_cutting_6"'>
					<td width="5"><span style="position:relative;left:55px;">MQC</span></td>
					<td>${map.wf_step_name }</td>
					<td>${fsp.map.cheFeng6 }</td>
					<td>${map.stepcount }</td>
					<td>${fsp.map.cheFengRate }%</td>
				</s:elseif>
				<s:elseif test='map.wf_step=="c_ppc_confirm_7"'>
					<td width="5"><span style="position:relative;left:55px;">QA</span></td>
					<td>${map.wf_step_name }</td>
					<td>${fsp.map.weiCha7 }</td>
					<td>${map.stepcount }</td>
					<td>${fsp.map.weiChaRate }%</td>
				</s:elseif>
				<s:elseif test='map.wf_step=="c_qc_printing_8"'>
					<td width="5"><span style="position:relative;left:55px;">财务</span></td>
					<td>${map.wf_step_name }</td>
					<td>${fsp.map.weiKuan8 }</td>
					<td>${map.stepcount }</td>
					<td>${fsp.map.weiKuanRate }%</td>
				</s:elseif>
				<s:elseif test='map.wf_step=="c_ppc_confirm_9"'>
					<td width="5"><span style="position:relative;left:55px;">QA</span></td>
					<td>${map.wf_step_name }</td>
					<td>${fsp.map.faHuo9 }</td>
					<td>${map.stepcount }</td>
					<td>${fsp.map.faHuoRate }%</td>
				</s:elseif>
			</tr>
		</s:else>	
	</s:iterator>
</table>
<%-- <script type="text/javascript">
	$(function() {
		require([ "timelyRateCount" ], function(fn) {
			fn.init();
		});
	});
</script> --%>