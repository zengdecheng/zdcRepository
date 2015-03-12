<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- JSP分页模板 by fangwei 2014-12-04-->
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="fsp.recordCount != 0">
	<style type="text/css">
	.public_fy{heigh:40px;text-align:center;}
	.pagination {padding: 5px; font-size:12px; margin:20px auto; }
	.pagination a, .pagination a:link, .pagination a:visited {padding:2px 5px;margin:2px;border:1px solid #aaaadd;text-decoration:none;color:#006699;}
	.pagination a:hover, .pagination a:active {border: 1px solid #d1cdcd;color: #000;text-decoration: none;}
	.pagination span.current {padding: 2px 5px;margin: 2px;border: 1px solid #d1cdcd;font-weight: bold;background-color: #d6d2d2;color: #FFF;}
	.pagination span.disabled {padding: 2px 5px;margin: 2px;border: 1px solid #eee; color: #ddd;}
	#pagerDefine,#pagerSelect{width:50px;}
	</style>
	<div class="clear"></div>
	
	<!-- 初始化pageNo,pageCount,start,end -->
	<s:set name="pageCount" value="(fsp.recordCount+fsp.pageSize-1)/fsp.pageSize"/>
	<s:if test="fsp.pageNo<1">
		<s:set name="pageNo" value="1"></s:set>
	</s:if>
	<s:elseif test="fsp.pageNo > #pageCount">
		<s:set name="pageNo" value="pageCount"></s:set>
	</s:elseif>
	<s:else>
		<s:set name="pageNo" value="fsp.pageNo"></s:set>
	</s:else>
	<s:set name="start" value="#pageNo>4?#pageNo-1:1+\"\""></s:set>	
	<s:set name="end" value="#pageNo+1>#pageCount?#pageCount:#pageNo+1+\"\""></s:set>

 	<div class="pagination">
		共<s:property value="fsp.recordCount"/>条 &nbsp;&nbsp; 
		每页&nbsp;<select id="pagerDefine">
		<option value="5" <s:if test="fsp.pageSize==5">selected="selected"</s:if>>5</option>
		<option value="12" <s:if test="fsp.pageSize==12">selected="selected"</s:if>>12</option>
		<option value="20" <s:if test="fsp.pageSize==20">selected="selected"</s:if>>20</option>
		<option value="30" <s:if test="fsp.pageSize==30">selected="selected"</s:if>>30</option>
		<option value="40" <s:if test="fsp.pageSize==40">selected="selected"</s:if>>40</option>
		<option value="50" <s:if test="fsp.pageSize==50">selected="selected"</s:if>>50</option>
	</select>&nbsp;条&nbsp;
	
 	<!-- 上一页处理 -->
	<s:if test="#pageNo==1">
		<span class="disabled">&laquo;&nbsp;上一页</span>
	</s:if>
	<s:else>
		<a href="javascript:void(0)" onclick="turnOverPage(<s:property value="#pageNo - 1"/>)">&laquo;&nbsp;上一页</a>
	</s:else>
	
	<!-- 如果前面页数过多,显示... -->
	<s:if test="#pageNo>4">
		<a href="javascript:void(0)" onclick="turnOverPage(1)">1</a>
		<a href="javascript:void(0)" onclick="turnOverPage(2)">2</a>&hellip;
	</s:if>
  	<s:bean name="org.apache.struts2.util.Counter" id="counter">  
		<s:param name="first" value="#start"/>  
		<s:param name="last" value="#end" />  
		<s:iterator>  
			<s:if test="#pageNo ==(current-1)"><span class="current"><s:property value="current-1"/></span></s:if>
			<s:else><a href="javascript:void(0)" onclick="turnOverPage(<s:property value="current-1"/>)"><s:property value="current-1"/></a></s:else>  
		</s:iterator>  
	</s:bean>
	 <!-- 如果后面页数过多,显示... -->
	<s:if test="#end < #pageCount-2">&hellip;</s:if> 
	<s:if test="#end<#pageCount-1"><a href="javascript:void(0)" onclick="turnOverPage(<s:property value="#pageCount-1"/>)"><s:property value="#pageCount-1"/></a></s:if> 
	<s:if test="#end<#pageCount"><a href="javascript:void(0)" onclick="turnOverPage(<s:property value="#pageCount"/>)"><s:property value="#pageCount"/></a></s:if> 
	<s:if test="#pageNo == #pageCount"><span class="disabled">下一页&nbsp;&raquo;</span></s:if> 
	<s:else><a href="javascript:void(0)" onclick="turnOverPage(<s:property value="#pageNo+1"/>)">下一页&nbsp;&raquo;</a></s:else>
	&nbsp;转到&nbsp;<select id="pagerSelect">
	<s:bean name="org.apache.struts2.util.Counter" id="counter">  
		<s:param name="first" value="1"/>  
		<s:param name="last" value="#pageCount" />  
		<s:iterator>  
	   		<option value="<s:property value="current-1"/>" <s:if test="current-1 ==#pageNo}">selected="selected"</s:if>><s:property value="current-1"/></option>
		</s:iterator>  
	</s:bean>
	</select>&nbsp;页  
	<script language="javascript">
	$(function(){
		$("#pagerSelect").change(function(){
			turnOverPage($("#pagerSelect").val());
		});
		$("#pagerDefine").change(function(){
			turnOverPage($("#pagerSelect").val(),true);
		});
	});
	function turnOverPage(pageNo,reload){
		var qForm=$('form').get(0);
		if(pageNo>${pageCount}){
			pageNo=${pageCount};
		}else if(pageNo<1){
			pageNo=1;
		}
		if(!!reload) pageNo=1;
		qForm.method ="post";
		qForm.action = qForm.action+"?fsp.pageNo="+pageNo+"&fsp.pageSize="+$("#pagerDefine").val();
		qForm.submit();
	}
	</script>
	</div>
</s:if>
