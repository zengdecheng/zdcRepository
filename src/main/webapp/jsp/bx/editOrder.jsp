<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<form id="form1" action="/bx/updateOrder" method="post"
	onsubmit="return onSubmit();">
	<div style="margin-top: 15px; margin-left: 20px;">
		<dt>
			<span>订单款号：</span>
		</dt>
		<dd>
			<input type="text" class="z_inp mra_r8" id="style_code_0"
				name="oaOrder.styleCode" value="${oaOrder.styleCode }" />
			<%-- <s:if test='oaOrder.type == "3"'>
			<a onclick="add()" id="add_style_code">+新增款号</a>
		</s:if> --%>
		</dd>
		<%-- <input type="hidden" id="total" name="oaOrder.styleCode"
			value="${oaOrder.styleCode }" /> --%>
		<input type="hidden" name="oaOrder.id" value="${oaOrder.id }" />
		<dd>
			<input type="submit" value="确定" class="mar_r z_btn1" id="bsubmit" /><input
				type="button" value="取消" class="z_btn2"
				onclick="javascript:history.go(-1);" />
		</dd>
		<dd><span>作废订单 （订单作废后将无法显示，请谨慎操作）</span></dd>
		<dd>
			<s:if test='@com.xbd.oa.utils.WebUtil@getCurrentLoginBxGroup() == "mr"'>
					<input type="button" id="submitBtn5" value="作废订单" onclick="confirmDivShowOrHide();"
				class="mar_r z_btn1" />
			</s:if>
		</dd>
		
		<div class="fab_form" id="confirmForm" style="display:none;z-index:10;position:absolute;background:#FFF;padding:0 0 5px 0;">
		<div class="z_distribut_title">
			<span>提示</span> <a href="javascript:void(0);" onclick="confirmDivShowOrHide()" class="cancel1">X</a>
		</div>
		<div id="confirmContent" style="margin: 30px;">确认要作废订单？</div>
		<input type="button" id="confirmBtn" onclick="finishSubmit()" value="确&nbsp;&nbsp;定"
			class="z_submit3" style="margin-left: 35px;" /> <input type="button"
			value="取&nbsp;&nbsp;消" onclick="confirmDivShowOrHide()" class="z_submit3 z_close cancel1" />
		</div>
		<div id="Form" style="position:absolute; top:0; width:160%; height:190%; background:#808080;_width:160%; _height:190%;_top:0; _background:#808080;/*for ie6*/ 
      filter: alpha(opacity=70);  /* ie 有效*/	
	  -moz-opacity:0.7; /* Firefox  有效*/	
	  opacity: 0.7; /* 通用，其他浏览器  有效*/
	  z-index:1;
	  margin-left: -484px;
	  margin-top: -85px;
	  display:none;"></div>
	</div>
</form>

<script>
	function onSubmit() {
		if ($("#style_code_0").val() == "") {
			alert("款号不能为空");
			return false;
		}
		return true;
	}

	function confirmDivShowOrHide () {
		if($("#confirmForm").is(":hidden")) {
			$("#confirmForm").show();
			$("#Form").show();
		} else {
			$("#confirmForm").hide();
			$("#Form").hide();	
		}
	}
	function finishSubmit() {
		confirmDivShowOrHide();
		window.location.href = "/bx/orderFinish?oaOrder.id=" + $("input[name='oaOrder.id']").val() ;
	}
	
</script>