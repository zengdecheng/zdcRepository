<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>${systemEnvironment }品类列表 | Singbada ERP</title>
  </head>
  
  <body>
  	<div>
  		<span>一级品类管理>>品类列表</span>
  	</div>
   	<div>
   		<table border="0" cellspacing="0" cellpadding="0" class="z_table_style2" width="1008">
   			<tr>
   				<td>编号</td><td>品类名称</td><td>说明</td><td>准生产缓冲时间-大货生产标</td><td>准生产缓冲时间-样衣打版标</td><td>状态</td><td>操作</td>
   			</tr>
   			<s:if test="beans.size()==0">
   				<tr ><td colspan="7" align="center">当前没有任何品类信息，请单击添加品类按钮进行添加！</td></tr>
   			</s:if>
   			<s:else>
   				<s:iterator value="beans" >
   					<tr>
   						<td>${map.code }</td><td>${map.name }</td><td>${map.explain_txt }</td><td>${dahuo_cyc }</td>
   						<td>${map.daban_cyc }</td><td>${map.status }</td>
   						<td>详情/编辑</td>
   					</tr>	
   				</s:iterator>
   			</s:else>
   		</table>
   		<s:include value="/jsp/parts/page.jsp"></s:include>
   		 <div style="float: right; margin-top: 17px; margin-right: 250px;">
        	<input id="addCategory" type="button" value="添加品类" style="width: 120px;margin-right:10px" /> 
        	<input id="return_bt" type="button" value="返回" style="width: 120px;" />
    	</div>
   	</div>
  </body>
</html>
