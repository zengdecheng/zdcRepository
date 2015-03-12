<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set name="ctx" value="%{pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css" href="/css/form3.css" />
<head>
	<title>${systemEnvironment }进度详情 | Singbada ERP</title>
</head>
<div class="z_process_back">
	<a href="javascript:void(0);" class="go_back"><img
		src="/images/fabric3_38.gif" border="0" class="fab_style9" /></a>
</div>
<table border="0" cellpadding="0" cellspacing="0" width="1008"
	align="center" class="z_table_style3">
	<tr bgcolor="#DEF0FB">
		<th width="8"></th>
		<th height="40" width="180" style="padding: 0 0 0 15px;">当前节点</th>
		<th width="140">开始时间</th>
		<th width="80">时长(小时)</th>
		<th width="150">实际完成时间</th>
		<th width="150">实际偏差</th>
		<th width="140">最终到期日</th>
		<th width="80">区域</th>
		<th width="80">责任人</th>
	</tr>
	<tr>
		<td height="23"></td>
	</tr>
	<s:iterator value="beans" status="st">
		<tr>
			<td width="8"></td>
			<td height="24"><s:property
					value="%{@com.xbd.oa.utils.WebUtil@getWfIndx(map.wf_step)}" />.${map.wf_step_name}</td>
			<td height="24">${map.wf_real_start }</td>
			<s:if test="#st.index == 0">
				<td>--</td>
				<td>--</td>
				<td>--</td>
				<td>--</td>
			</s:if>
			<s:else>
				<td height="24">${map.wf_step_duration}</td>
				<td><div class="z_order_bg" title="${map.wf_real_offset }">${map.wf_real_finish }</div></td>
				<td><div class="z_order_bg">${map.offset }</div></td>
				<td>${map.total }</td>
			</s:else>
			<s:if test="#st.index == 0">
				<td>${oaOrder.city }</td>
			</s:if>
			<s:else>
				<td>--</td>
			</s:else>
			<td>${map.operator }</td>
		</tr>
		<tr>
			<td width="8"></td>
			<td height="28"><a href="javascript:void(0);"
				onclick="outInerb.attachmentFormShowOrHide('orderForm${st.index }')"><img
					src="/images/z_0412.gif"></a> <a href="javascript:void(0);"
				onclick="outInerb.attachmentFormShowOrHide('attachmentForm${st.index }')"><img
					src="/images/z_0413.gif"></a> <a href="javascript:void(0);"
				onclick="outInerb.attachmentFormShowOrHide('contentForm${st.index }')"><img
					src="/images/z_0414.gif"></a>
				<div class="fab_form1" id="orderForm${st.index }">
					<div class="z_distribut_title">
						<span>订单信息</span> <a href="javascript:void(0);" class="cancel2"
							onclick="outInerb.attachmentFormShowOrHide('orderForm${st.index }')">X</a>
					</div>
					<s:if test="null != oaOrder && null != oaOrder.id">
						<s:if test="oaOrder.type == 1">
							<p style="margin: 10px;">订单类型：模拟报价</p>
							<p style="margin: 10px;">
								加急程度：
								<s:if test="oaOrder.isurgent == 0">加急</s:if>
								<s:else>不加急</s:else>
							</p>
							<p style="margin: 10px;">客户编号：${oaOrder.cusCode }</p>
							<p style="margin: 10px;">业务担当：${oaOrder.sales }</p>
							<%-- <p style="margin: 10px;">订单编号：${oaOrder.orderCode }</p> --%>
							<p style="margin: 10px;">订单款号：${oaOrder.styleCode }</p>
							<p style="margin: 10px;">所属品类：${oaOrder.clothClass }</p>
							<p style="margin: 10px;">期望价格：${oaOrder.priceMin } -
								${oaOrder.priceMax }</p>
							<p style="margin: 10px;">预计数量：${oaOrder.wantCnt }</p>
							<p style="margin: 10px;">预计交期：${oaOrder.exceptFinish }</p>
							<p style="margin: 10px;">
								当前款式图片：<img src="${oaOrder.styleUrl }" width="100px"
									height="100px" />
							</p>
						</s:if>
						<s:elseif test="oaOrder.type == 2">
							<p style="margin: 10px;">订单类型：样衣打版</p>
							<p style="margin: 10px;">
								加急程度：
								<s:if test="oaOrder.isurgent == 0">加急</s:if>
								<s:else>不加急</s:else>
							</p>
							<%-- <p style="margin: 10px;">关联订单：${oaOrder.oaOrder }</p> --%>
							<p style="margin: 10px;">客户编号：${oaOrder.cusCode }</p>
							<p style="margin: 10px;">业务担当：${oaOrder.sales }</p>
							<%-- <p style="margin: 10px;">订单编号：${oaOrder.orderCode }</p> --%>
							<p style="margin: 10px;">订单款号：${oaOrder.styleCode }</p>
							<p style="margin: 10px;">所属品类：${oaOrder.clothClass }</p>
							<p style="margin: 10px;">期望价格：${oaOrder.priceMin } -
								${oaOrder.priceMax }</p>
							<p style="margin: 10px;">预计数量：${oaOrder.wantCnt }</p>
							<p style="margin: 10px;">预计交期：${oaOrder.exceptFinish }</p>
							<%-- <p style="margin: 10px;">销售备注：${oaOrder.sellMemo }</p>
							<p style="margin: 10px;">Mr备注：${oaOrder.memo }</p> --%>
							<p style="margin: 10px;">
								当前款式图片：<img src="${oaOrder.pictureFront }" width="100px"
									height="100px" />  
								<!-- <img  src="<s:property value='oaOrder.styleUrl.replace("oa.singbada.cn","121.196.128.123")' />"  width="100px" height="100px" />-->
							</p>
						</s:elseif>
						<s:elseif test="oaOrder.type == 3">
							<p style="margin: 10px;">订单类型：大货生产</p>
							<p style="margin: 10px;">
								加急程度：
								<s:if test="oaOrder.isurgent == 0">加急</s:if>
								<s:else>不加急</s:else>
							</p>
							<%-- <p style="margin: 10px;">关联订单：${oaOrder.oaOrder }</p> --%>
							<p style="margin: 10px;">客户编号：${oaOrder.cusCode }</p>
							<p style="margin: 10px;">业务担当：${oaOrder.sales }</p>
							<p style="margin: 10px;">订单编号：${oaOrder.orderCode }</p>
							<p style="margin: 10px;">订单款号：${oaOrder.styleCode }</p>
							<p style="margin: 10px;">所属品类：${oaOrder.clothClass }</p>
							<p style="margin: 10px;">付款方式：${oaOrder.payType }</p>
							<p style="margin: 10px;">预计数量：${oaOrder.wantCnt }</p>
							<p style="margin: 10px;">预计交期：${oaOrder.exceptFinish }</p>
							<p style="margin: 10px;">合同条款特殊说明：${oaOrder.contractSpecialMemo }</p>
							<%-- <p style="margin: 10px;">Mr备注：${oaOrder.memo }</p> --%>
							<p style="margin: 10px;">
								当前款式图片：<img src="${oaOrder.pictureFront }" width="100px"
									height="100px" />  
								<!-- <img  src="<s:property value='oaOrder.styleUrl.replace("oa.singbada.cn","121.196.128.123")' />"  width="100px" height="100px" />-->
							</p>
						</s:elseif>
					</s:if>
					<s:else>
						<p class="z_order_tip">订单信息出错</p>
					</s:else>
					<input type="button" value="关&nbsp;&nbsp;闭"
						class="z_submit3 cancel2"
						onclick="outInerb.attachmentFormShowOrHide('orderForm${st.index }')"
						style="margin-left: 35px;" />
				</div>
				<div class="fab_form1" id="attachmentForm${st.index }">
					<div class="z_distribut_title">
						<span>附件下载</span> <a href="javascript:void(0);" class="cancel2"
							onclick="outInerb.attachmentFormShowOrHide('attachmentForm${st.index }')">X</a>
					</div>
					<s:if test='(null != map.attachment && map.attachment != "") || (null != map.pic && map.pic != "")'>
						<s:if test='null != map.attachment && map.attachment != ""'>
							<p style="margin: 10px;">
								<a href="javascript:void(0)"
									class="z_title_sty1 mar_l10 download"
									downloadUrl="${map.attachment}">${map.attachment_name}</a><input
									type="button" downloadUrl="${map.attachment}"
									class="mar_l10 z_submit4 download" />
							</p>
						</s:if>
						<s:if test='null != map.pic && map.pic != ""'>
							<p style="margin: 10px;">
								<a href="javascript:void(0)"
									class="z_title_sty1 mar_l10 download" downloadUrl="${map.pic}">${map.pic_name}</a><input
									type="button" downloadUrl="${map.pic}"
									class="mar_l10 z_submit4 download" />
							</p>
						</s:if>
					</s:if>
					<s:else>
						<p class="z_order_tip">本流程没有上传附件/图片</p>
					</s:else>
					<input type="button" value="关&nbsp;&nbsp;闭"
						class="z_submit3 cancel2"
						onclick="outInerb.attachmentFormShowOrHide('attachmentForm${st.index }')"
						style="margin-left: 35px;" />
				</div>
				<div class="fab_form1" id="contentForm${st.index }">
					<div class="z_distribut_title">
						<span>备注信息</span> <a href="javascript:void(0);" class="cancel2"
							onclick="outInerb.attachmentFormShowOrHide('contentForm${st.index }')">X</a>
					</div>
					<p style="margin: 10px;">&nbsp;${map.content }</p>
					<input type="button" value="关&nbsp;&nbsp;闭"
						class="z_submit3 cancel2"
						onclick="outInerb.attachmentFormShowOrHide('contentForm${st.index }')"
						style="margin-left: 35px;" />
				</div></td>
			<td colspan="5">&nbsp;</td>
		</tr>
	</s:iterator>
</table>

<div id="Form" style="height: 157%;"></div>

<script type="text/javascript">
	$(function() {
		require([ "viewOrderDetail" ], function(fn) {
			fn.init();
			window.outInerb = fn;
		});
	});
</script>

