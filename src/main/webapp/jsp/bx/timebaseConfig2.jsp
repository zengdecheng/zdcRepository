<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="/css/form3.css" />
<head>
	<title>${systemEnvironment }设置基准时间 | Singbada ERP</title>
</head>
<div class="right_panel">
	<%-- <div class="gg">
                <em></em>
                <span>公告内容</span>
            </div> --%>
	<div class="step_tool jdgj">
		<a href="javascript:void(0);" class="go_back">&lt;&lt;&nbsp;返回</a>
	</div>
	<div class="panel_lan">
		<div class="panel_lan_title">3.请录入基准时间</div>
		<div class="panel_lan_con">
			<div class="config_2">
				<span><a href="javascript:void(0);" id="add">添加关联流程</a></span><br>
				<span style="color: red;" id="totalTime">${oaTimebase.totalDuration }</span>
				<span style="color: red;" id="setTime"></span>
				<form id="oaConfig2_form" action="/bx/saveTimebaseConfig"
					method="post">
					<table cellpadding="0" cellspacing="1" border="0">
						<tr>
							<th width="167px">项目</th>
							<th>基准时间(小时)</th>
						</tr>
						<s:iterator value="oaTimebaseEntries" status="st">
							<tr>
								<td align="left" class="hui">${st.index + 1 }.${stepName }</td>
								<td><input type="hidden"
									name="oaTimebaseEntries[${st.index }].step" value="${step }">
									<input type="hidden"
									name="oaTimebaseEntries[${st.index }].stepName"
									value="${stepName }"> <input type="hidden"
									id="calDur${st.index }"
									name="oaTimebaseEntries[${st.index }].calculateDuration"
									value="${calculateDuration }"> <input
									id="dur${st.index }_1"
									name="oaTimebaseEntries[${st.index }].stepDuration"
									type="hidden" value="${stepDuration }" /> <input
									id="dur${st.index }" type="text" value="${stepDuration }"
									class="input_txt" /></td>
							</tr>
						</s:iterator>
					</table>
					<input type="hidden" id="oaTimebase_id" name="oaTimebase.id"
						value="${oaTimebase.id}"> <input type="hidden"
						name="oaTimebase.clothClass" value="${oaTimebase.clothClass}">
					<input type="hidden" name="oaTimebase.defineId"
						value="${oaTimebase.defineId}"> <input type="hidden"
						name="oaTimebase.defineKey" value="${oaTimebase.defineKey}">
					<input type="hidden" name="oaTimebase.name"
						value="${oaTimebase.name}"> <input type="hidden"
						id="oaTimebase_totalDuration" name="oaTimebase.totalDuration"
						value="${oaTimebase.totalDuration}">
				</form>
			</div>
			<dl class="form-list config_2_dl">
				<dd>
					<a href="javascript:void(0);" id="btn_next" class="btn_next">保存并完成</a>
				</dd>
			</dl>
		</div>
	</div>
</div>
<div class="fab_form" id="confirmForm">
	<div class="z_distribut_title">
		<span>添加关联流程</span> <a href="javascript:void(0);" class="cancel1">X</a>
	</div>
	<div>
		<div style="padding-left: 20px; padding-top: 10px;">
			<a href="javascript:void(0);" id="add1">添加关联流程</a>
		</div>
		<div id="div0" style="padding: 5px 10px 10px;">
			<table id="table1" style="text-align: center; width: 100%;">
				<tr class="notselect" style="height: 20px;">
					<th width="50%">主流程</th>
					<th>关联流程</th>
				</tr>
				<tr style="height: 20px;">
					<td><select id="select0">
							<option value="-1">请选择</option>
							<s:iterator value="oaTimebaseEntries" status="st">
								<option value="${st.index }">${st.index + 1 }.${stepName }</option>
							</s:iterator>
					</select></td>
					<td><select id="select0">
							<option value="-1">请选择</option>
							<s:iterator value="oaTimebaseEntries" status="st">
								<option value="${st.index }">${st.index + 1 }.${stepName }</option>
							</s:iterator>
					</select></td>
				</tr>
			</table>
		</div>
		<input type="button" id="confirmBtn" value="确&nbsp;&nbsp;定"
			class="z_submit3" style="margin-left: 35px;" /> <input type="button"
			value="取&nbsp;&nbsp;消" class="z_submit3 z_close cancel1" />
	</div>
</div>
<div id="Form"></div>
<script type="text/javascript">
	$(function() {
		require([ "timebaseConfig2" ], function(fn) {
			fn.init();
		});
	});
</script>