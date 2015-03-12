<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:set name="ctx" value="%{pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
</head>
<body>
	<form action="/bx/staff_add">
	<label >${oaStaff.id}</label>
	<input name="oaStaff.loginName" value="${oaStaff.loginName}"></input>
	<button >提交</button>
	</form>
	
	<form action="/bx/staff_list">
	<input name="searchName" value="${searchName}"></input>
	<button>查询</button>
	</form>
	
	<s:iterator value="beans">
		${map.id} || ${map.login_name}
	</s:iterator>
</body>
</html>

