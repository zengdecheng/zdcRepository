<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="xbd_paging_by_holly" class="page">
	<div class="page-msg">
		共<em><s:property value="fsp.recordCount" /></em>条数据
	</div>

	<s:if test="fsp.pageNo > 1">
		<div class="page-item"><a href="javascript:void(0)" class="prev" pageno='<s:property value='fsp.pageNo-1'/>'>上一页</a></div>
	</s:if>
	<s:else>
		<span class="prev">上一页 </span>
	</s:else>

	<s:if test="fsp.recordCount > fsp.pageSize">
		<%
		int span = 1;
		org.use.base.FSPBean fsp = (org.use.base.FSPBean) request.getAttribute("fsp");
		int current = fsp.getPageNo();
		int count = (int) (fsp.getRecordCount() + fsp.getPageSize() - 1) / fsp.getPageSize();
		if ((current - span) > 1) {%>
			<div class="page-item"><a href="javascript:void(0)" pageno='<%=1%>'><%=1%></a></div>
			<%if ((current - span) > 2) {%>
				<span class="dot"></span>
			<%}%>
		<%}

		int begin = current > span ? (current - span) : 1;
		int end = current < (count - span) ? (current + span) : count;
		for (int i = begin; i <= end; i++) {
			if (i == current) {%>
			<div class="page-item page-item-cur"><a href="javascript:void(0)"><%=i%></a></div>
			<%} else {%>
			<div class="page-item"><a href="javascript:void(0)" pageno='<%=i%>'><%=i%></a></div>
			<%}
		}
		if ((current + span) < count) {
			if ((current + span) < (count - 1)) {%>
	   		  <span class="dot"></span>
       	  	<%}	%>
       	  	<div class="page-item"><a href="javascript:void(0)" pageno='<%=count%>'><%=count%></a></div>
		<%}%>
	</s:if>

	<s:if test="fsp.pageNo < (fsp.recordCount+fsp.pageSize-1)/fsp.pageSize">
		<div class="page-item"><a href="javascript:void(0)" class="next" pageno='<s:property value="fsp.pageNo+1"/>'>下一页</a></div>
	</s:if>
	<s:else>
		<span class="next">下一页 </span>
	</s:else>
	
	
	<div class="input-page">
		<input id="txt_number" name="fsp.pageNo" type="text" value="<s:property value="fsp.pageNo"/>" onkeypress="if(event.keyCode < 48 || event.keyCode > 57)return false;" />
	</div>
	<img id="btn_go" src="${ctx}/images/btn4.jpg" class="pageGoBtn" />	
	</div>

