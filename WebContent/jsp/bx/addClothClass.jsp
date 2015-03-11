<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
	<title>${systemEnvironment }添加品类 | Singbada ERP</title>
</head>
<div class="right_panel">
	<div class="step_tool jdgj">
		<a href="javascript:void(0))" class="go_back">&lt;&lt;&nbsp;返回</a>
	</div>
	<div class="panel_lan">
		<div class="panel_lan_title">添加品类</div>
		<form id="clothClass_form" action="/bx/saveClothClass"
			method="post">
			<div class="panel_lan_con config_0">
				<dl class="form-list">
					<dd>品类：&nbsp;&nbsp;</dd>
					<dd>
						<input type="text" name="oaDt.value" value="${oaDt.value }" class="input_txt" style="width:165px;" />
						<s:if test="null == oaDt">
							<a id="clothClass_0" class="z_del" href="javascript:void(0);">&nbsp;新增品类</a><br />
						</s:if>
					</dd>
				</dl>
				<dl class="form-list">
					<dd>
						<s:if test="null == oaDt">
							<a id="btn_next" href="javascript:void(0);" class="btn_next">保存</a>
						</s:if>
						<s:else>
							<input type="hidden" name="oaDt.id" value="${oaDt.id }" />
							<input type="hidden" name="oaDt.type" value="${oaDt.type }" />
							<input type="hidden" name="oaDt.code" value="${oaDt.code }" />
							<input type="hidden" name="oaDt.inx" value="${oaDt.inx }" />
							<input type="hidden" name="oaDt.memo" value="${oaDt.inx }" />
							<a id="btn_update" href="javascript:void(0);" class="btn_next">保存</a>
						</s:else>
					</dd>
				</dl>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		require([ "addClothClass" ], function(fn) {
			fn.init();
            window.outInerb = fn;
		});
	});
</script>