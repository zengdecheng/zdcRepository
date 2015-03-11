<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set name="ctx" value="%{pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>${systemEnvironment }订单进度查询——辛巴达</title>
</head>
<body>
<link rel="stylesheet" type="text/css" href="/css/index4.css" />
<script type="text/javascript" src="/js/pub/jquery_1.7.2.js"></script>
<script type="text/javascript" src="/js/pub/require_min.js"></script>
<script type="text/javascript">requirejs.config({baseUrl : "${ctx}/js/bx/"});</script>
<table border="0" cellpadding="0" cellspacing="0"
	align="center" class="z_table_style3">
	<tr bgcolor="#DEF0FB">
		<th width="20%">当前节点</th>
		<th width="25%">开始时间</th>
		<!-- <th width="60">时长(小时)</th> -->
		<!-- <th >实际完成时间</th> -->
		<th width="40%">实际偏差</th>
		<!-- <th >最终到期日</th> -->
		<th width="15%">责任人</th>
	</tr>
	<tr>
		<td height="23"></td>
	</tr>
	<s:iterator value="beans" status="st">
		<tr>
			<td><s:property
					value="%{@com.xbd.oa.utils.WebUtil@getWfIndx(map.wf_step)}" />.${map.wf_step_name}</td>
			<td>${map.wf_real_start }</td>
			<s:if test="#st.index == 0">
				<!-- <td>--</td>
				<td>--</td> -->
				<!-- <td>--</td> -->
				<td>--</td>
			</s:if>
			<s:else>
				<%-- <td height="24">${map.wf_step_duration}</td> --%>
				<%-- <td><div class="z_order_bg" title="${map.wf_real_offset }">${map.wf_real_finish }</div></td> --%>
				<td><div class="z_order_bg">${map.offset }</div></td>
				<%-- <td>${map.total }</td> --%>
			</s:else>
			<td>${map.operator }</td>
		</tr>
		<tr height="23"></tr>
	</s:iterator>
</table>
<script type="text/javascript">
	/* $(function() {
		require([ "viewOrderDetailOnPhone" ], function(fn) {
			fn.init();
			window.outInerb = fn;
		});
	}); */
	$(function (){
		require(["viewOrderDetailOnPhone"],function(fn){
			fn.init();
		});
	});
</script>
</body>
</html>