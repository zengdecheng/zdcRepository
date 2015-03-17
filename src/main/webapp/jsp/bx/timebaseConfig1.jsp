<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
	<title>${systemEnvironment }设置基准时间 | Singbada ERP</title>
</head>
<div class="right_panel">
	<%-- <div class="gg">
                <em></em>
                <span>公告内容</span>
            </div> --%>
	<div class="step_tool jdgj">
		<a href="javascript:void(0))" class="go_back">&lt;&lt;&nbsp;返回</a>
	</div>
	<div class="panel_lan">
		<div class="panel_lan_title">1.填写名称</div>
		<form id="oaConfig1_form" action="/bx/timebaseConfig2"
			method="post">
			<div class="panel_lan_con config_0">
				<dl class="form-list">
					<dd>名称：&nbsp;&nbsp;&nbsp;&nbsp;</dd>
					<dd>
						<input type="text" id="name" name="oaTimebase.name" value="${oaTimebase.name }" class="input_txt user" />
					</dd>
				</dl>
				<dl class="form-list">
					<dd>品类：&nbsp;&nbsp;&nbsp;&nbsp;</dd>
					<dd>
						<select name="oaTimebase.clothClass" style="width: 153px;">
							<s:iterator value="%{@com.xbd.oa.utils.XbdBuffer@getOaDtList('1')}">
								<s:if test="code = oaTimebase.clothClass">
									<option selected value="${code }">${value }</option>
								</s:if>
								<s:else>
									<option value="${code }">${value }</option>
								</s:else>
							</s:iterator>
						</select>
					</dd>
				</dl>
				<dl class="form-list">
					<dd>相关流程：</dd>
					<dd>
						<select id="process" style="width: 153px;">
							<s:iterator value="%{@com.xbd.oa.utils.WorkFlowUtil@getProcessDefinitionList()}">
								<s:if test="map.id = oaTimebase.defineId">
									<option selected process_id="${map.id }" process_key="${map.key }">${map.name }</option>
								</s:if>
								<s:else>
									<option process_id="${map.id }" process_key="${map.key }">${map.name }</option>
								</s:else>
							</s:iterator>
						</select>
					</dd>
				</dl>
				<dl class="form-list">
					<dd>总时间：&nbsp;&nbsp;</dd>
					<dd>
						<input type="text" id="totalDuration" value="${oaTimebase.totalDuration }" class="input_txt user" />&nbsp;&nbsp;小时
					</dd>
				</dl>
				<dl class="form-list">
					<dd>
						<input type="hidden" name="oaTimebase.id" value="${oaTimebase.id }" />
						<input name="oaTimebase.defineId" type="hidden" value="${oaTimebase.defineId }" id="process_id" />
						<input name="oaTimebase.defineKey" type="hidden" value="${oaTimebase.defineKey }" id="process_key" />
						<input type="hidden" id="totalDuration_1" value="${oaTimebase.totalDuration }" name="oaTimebase.totalDuration" />
						<a id="btn_next" href="javascript:void(0);" class="btn_next">下一步</a>
					</dd>
				</dl>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		require([ "timebaseConfig1" ], function(fn) {
			fn.init();
		});
	});
</script>