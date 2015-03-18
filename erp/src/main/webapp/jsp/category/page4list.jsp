<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>${systemEnvironment }品类列表|Singbada ERP</title>
</head>

<body>
	<div class="step_tool" style="float: none; margin: 0 0 5px; width: 100%;">
		<span>一级品类管理&nbsp;&lt;&lt;&nbsp;品类列表</span>
	</div>
	<div>
		<table border="0" cellspacing="0" cellpadding="0"
			class="z_table_style2" width="1008">
			<tr>
				<th width="8" height="40"></th>
				<th width="80">编号</th>
				<th width="160">品类名称</th>
				<th width="200">说明</th>
				<th width="180">准生产缓冲时间-大货生产标</th>
				<th width="180">准生产缓冲时间-样衣打版标</th>
				<th width="100">状态</th>
				<th width="100">操作</th>
			</tr>
			<s:if test="beans.size()==0">
				<tr>
					<td colspan="8" align="center">当前没有任何品类信息，请单击添加品类按钮进行添加！</td>
				</tr>
			</s:if>
			<s:else>
				<s:iterator value="beans">
					<tr>
						<td></td>
						<td>${map.code }</td>
						<td>${map.name }</td>
						<td>${map.explain_txt }</td>
						<td>${dahuo_cyc }</td>
						<td>${map.daban_cyc }</td>
						<td>${map.status }</td>
						<td class="z_title_sty5"><a href="/category/page4detail?oaCategory.id=${map.id }">详情</a>&nbsp;&nbsp;<a href="/category/page4edit?oaCategory.id=${map.id }">编辑</a></td>
					</tr>
				</s:iterator>
			</s:else>
		</table>
		<s:include value="/jsp/parts/page.jsp"></s:include>
		<div style="float: right; margin-top: 17px; margin-right: 100px;">
			<input id="addCategory" type="button" value="添加品类"
				style="width: 100px; margin-right: 10px; height: 26px;" /> <!-- <input
				id="return_bt" type="button" value="返回"
				style="width: 100px; height: 26px;" /> -->
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			require([ "/category/page4list.js" ], function(fn) {
				fn.init();
			})
		});
	</script>
</body>

</html>
