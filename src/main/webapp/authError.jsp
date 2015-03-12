<%@ page language="java" contentType="text/html;charset=UTF-8"%>

<p
	style="font-size: 19px; font-weight: bold; margin-left: 55px; margin-top: 30px;">非法操作</p>
<script type="text/javascript">
	$(function() {
		alert("当前用户不属于mr，增加订单请联系mr！");
		window.location.href = "/bx/order_list";
	});
</script>