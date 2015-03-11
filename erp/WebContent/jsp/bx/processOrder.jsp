<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${systemEnvironment }订单处理-采购 | Singbada ERP</title>
</head>
<link rel="stylesheet" type="text/css" href="/css/form3.css" />
<s:set name="ctx" value="%{pageContext.request.contextPath}" />
<body>
	<div class="z_process_back">
		<a href="javascript:void(0)" class="go_back"><img
			src="/images/fabric3_38.gif" border="0" class="fab_style9" /></a>
	</div>
	 <img src="${bean.map.picture_front}" width="100" height="100" class="z_img" /> 
	<!--  <img src="<s:property value='bean.map.orderPic.replace("oa.singbada.cn","121.196.128.123")' />" width="100" height="100" class="z_img" />-->
	<div class="z_order_det">
		<div class="z_det1">${bean.map.orderTitle}</div>
		<s:if test='"-1" == bean.map.exceptPrice'>
			<div class="z_det2">订单编号：${bean.map.orderCode}</div>
		</s:if>
		<div class="z_det2">预计数量：${bean.map.wantCnt}</div>
		<div class="z_det2">
			上个流程：<span class="z_title_sty5">${bean.map.wf_step_name}</span>
		</div>
		<div class="z_det2">预计交期：${bean.map.exceptFinish}</div>
		<div class="z_det2">上个流程担当：${bean.map.top_operator}</div>
		<div class="z_det2">
			<s:if test='"-1" == bean.map.exceptPrice'>
				付款方式：${bean.map.payType}
			</s:if>
			<s:else>
				期望价格：${bean.map.exceptPrice}
			</s:else>
		</div>
		<div class="z_det2" data="${bean.map.id }">订单号：${bean.map.sell_order_code}</div>
	</div>
	<div class="cl"></div>

	<table class="z_order_date" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<th height="36">上一个流程流转时间：</th>
			<td height="36">${bean.map.wf_real_finish}</td>
		</tr>
		<tr>
			<th height="36">预期完成时间：</th>
			<td height="36">${bean.map.wf_plan_finish}</td>
		</tr>
		<tr>
			<th height="36">实际偏差时间：</th>
			<td height="36">
				<div class="z_bg1">${bean.map.offset}</div>
			</td>
		</tr>
		<tr>
			<th height="36">总剩余时间：</th>
			<td height="36">
				<div class="z_bg1">${bean.map.rest}</div>
			</td>
		</tr>
	</table>
	<div class="cl"></div>

	<div class="z_order_title">
		<span class="z_title_sty1">上个流程附件</span>
	</div>
	<s:if
		test='bean.map.top_attachment_name != "" || bean.map.top_pic_name != ""'>
		<s:if test='bean.map.top_attachment_name != ""'>
			<p style="margin: 10px;">
				<a href="javascript:void(0)" class="z_title_sty1 mar_l10 download"
					downloadUrl="${bean.map.top_attachment}">${bean.map.top_attachment_name}</a><input
					type="button" downloadUrl="${bean.map.top_attachment}"
					class="mar_l10 z_submit4 download" />
			</p>
		</s:if>
		<s:if test='bean.map.top_pic_name != ""'>
			<p style="margin: 10px;">
				<a href="javascript:void(0)" id="viewPic"
					class="z_title_sty1 mar_l10" downloadUrl="${bean.map.top_pic}">${bean.map.top_pic_name}</a><input
					type="button" downloadUrl="${bean.map.top_pic}"
					class="mar_l10 z_submit4 download" />
			</p>
		</s:if>
	</s:if>
	<s:else>
		<p class="z_order_tip">上个流程暂无附件</p>
	</s:else>

	<div class="z_order_title">
		<span class="z_title_sty1">上个流程备注</span>
	</div>
	<p class="z_order_tip">${bean.map.top_content}&nbsp;</p>

	<form id="form1" action="/bx/confirmOrder" method="post">
		<div class="z_order_title z_bg">
			<span class="z_title_sty9">本流程附件</span>
		</div>
		<div class="z_order_up" style="float: left; width: 40%;">
			<object id="objUpload" width="260" height="30"
				classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
				codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
				<param name="movie" value="${ctx}/swf/xbdInner.swf" />
				<param name="wmode" value="transparent" />
				<param name="FlashVars"
					value="url=/oaOrderFileUpload?sysLoginId=1&name=${bean.map.fileName}&size=10485760" />
				<embed src="${ctx}/swf/xbdInner.swf" id="objUpload" width="260"
					height="30" wmode="transparent"
					type="application/x-shockwave-flash"
					FlashVars="url=/oaOrderFileUpload?sysLoginId=1&name=${bean.map.fileName}&size=10485760"
					pluginspage="http://www.adobe.com/go/getflashplayer">
				</embed>
			</object>
			<br /> <span id="attachment_name"
				style="margin-left: 10px; line-height: 28px;"><a
				id="attachment_name1" href="javascript:void(0)"
				class="z_title_sty1 download" downloadUrl="${bean.map.attachment}">${bean.map.attachment_name}</a>
			</span><br />
			<object id="objUpload" width="260" height="30"
				classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
				codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
				<param name="movie" value="${ctx}/swf/xbd.swf" />
				<param name="wmode" value="transparent" />
				<param name="FlashVars"
					value="url=/oaOrderImgUpload?sysLoginId=1&name=${bean.map.fileName}&size=10485760&ext=*.jpg;*.png;*.gif" />
				<embed src="${ctx}/swf/xbd.swf" id="objUpload" width="260"
					height="30" wmode="transparent"
					type="application/x-shockwave-flash"
					FlashVars="url=/oaOrderImgUpload?sysLoginId=1&name=${bean.map.fileName}&size=10485760&ext=*.jpg;*.png;*.gif"
					pluginspage="http://www.adobe.com/go/getflashplayer">
				</embed>
			</object>
			<br />
				<span id="img_pic_url"
					style="margin-left: 10px; line-height: 28px;"><a
					id="img_pic_url1" href="javascript:void(0)"
					class="z_title_sty1 download" downloadUrl="${bean.map.pic}">${bean.map.pic_name}</a>
				</span>
				<%-- <img id="img_pic_url" src="${bean.map.pic_name}" width="100"
				height="100" class="z_img" style="margin: 8px;" /> --%> <input
				type="hidden" id="top_attachment" value="${bean.map.top_attachment}" />
			<input type="hidden" id="top_pic" value="${bean.map.top_pic}" /> <input
				type="hidden" id="hid_attachment_url"
				name="oaOrderDetail.attachment" value="${bean.map.attachment}" /> <input
				type="hidden" id="hid_pic_url" name="oaOrderDetail.pic"
				value="${bean.map.pic}" />
		</div>
		<div style="height: 130px; margin-right: 28px; margin-top: 10px;">${bean.map.discrip}</div>
		<div class="z_order_title z_bg">
			<span class="z_title_sty9">本流程备注</span>
		</div>
		<textarea class="z_textarea3" name="oaOrderDetail.content">${bean.map.content}</textarea>

		<%-- <s:if
			test='null != bean.map.wf_step && "b_ppc_confirm_5" == bean.map.wf_step'>
			<div class="z_order_title z_bg">
				<span class="z_title_sty9">任务分配</span>
			</div>
			<p class="z_order_tip">
				<input type="radio" checked name="group" value="1" />
				技术部(IT)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"
					name="group" value="0" /> 外发部(QC)
			</p>
		</s:if>
 --%>
		<div class="z_order_sub">
			<input type="hidden" id="oaOrderDetailId" name="oaOrderDetail.id"
				value="${bean.map.id}" /> <input type="button" id="submitBtn1"
				value="确认流转" class="z_btn1 mar_r25" /> <input type="button"
				id="submitBtn2" value="退回上一步" isBack="${bean.map.isBack}"
				class="z_btn1 mar_r25 go_back" /> <input type="hidden" value="流程跳转"
				class="z_btn1 z_table_tip" />
		</div>
		<input type="hidden" id="operator" value="${bean.map.operator}" />
		<input type="hidden" id="oa_order" value="${bean.map.oa_order}" />
		<s:if test="bean.map.isAssign == 1">
			<input type="button" id="submitBtn4" value="分配订单"
				class="z_submit3 z_order_btn" />
		</s:if>
		<s:if test="bean.map.isFinish == 1">
			<input type="button" id="submitBtn5" value="作废订单"
				class="z_submit3 z_order_btn" />
		</s:if>
	</form>

	<div class="fab_form" id="staffListForm" style="top:350px;">
		<div class="z_distribut_title">
			<span>分配订单</span> <a href="javascript:void(0);" class="cancel">X</a>
		</div>
		<div class="z_distribut_con">
			<ul class="z_distribut_detail" id="operatorList">
			</ul>
			<input type="button" id="assignConfirmBtn" value="确&nbsp;&nbsp;定"
				class="z_submit3" style="margin-left: 60px;" /> <input
				type="button" value="取&nbsp;&nbsp;消"
				class="z_submit3 z_close cancel" />
		</div>
	</div>
	<div class="fab_form" id="confirmForm" style="top: 420px;">
		<div class="z_distribut_title">
			<span>提示</span> <a href="javascript:void(0);" class="cancel1">X</a>
		</div>
		<div id="confirmContent" style="margin: 30px;"></div>
		<input type="button" id="confirmBtn" value="确&nbsp;&nbsp;定"
			class="z_submit3" style="margin-left: 35px;" /> <input type="button"
			value="取&nbsp;&nbsp;消" class="z_submit3 z_close cancel1" />
	</div>
	<div class="fab_form" id="picForm">
		<div class="z_distribut_title">
			<span>图片预览</span> <a href="javascript:void(0);" class="cancel2">X</a>
		</div>
		<div style="margin: 30px;">
			<!-- <img alt="" src="${bean.map.top_pic}" width="100px" height="100px" /> -->
			<img alt="" src="<s:property value='bean.map.top_pic.replace("oa.singbada.cn","121.196.128.123")' />" width="100px" height="100px" />
		</div>
		<input type="button" value="关&nbsp;&nbsp;闭" class="z_submit3 cancel2"
			style="margin-left: 35px;" />
	</div>
	<div id="Form"></div>

	<script type="text/javascript">
		$(function() {
			require([ "processOrder" ], function(fn) {
				fn.init();
				window.outInerb = fn;
			});
		});
	</script>
</body>
</html>