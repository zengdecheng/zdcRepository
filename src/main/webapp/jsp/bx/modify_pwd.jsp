<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<head>
	<title>${systemEnvironment }修改密码 | Singbada ERP</title>
</head>
<table style="margin-top:30px">
 <tr><td><span>旧的登陆密码</span></td><td><input type="password" class="z_inp" id="oldPwd"/></td></tr>   
 <tr><td><span>新密码</span></td><td><input type="password" class="z_inp" id="newPwd"/></td></tr>
 <tr><td><span>再次输入新密码</span></td><td><input type="password" class="z_inp" id="newPwdAff"/></td></tr>
 <tr></tr>
 <tr><td></td><td><input id="submit_btn" type="button" value="修改密码" class="mar_r z_btn1"/><span id="info"></span></td></tr>

 </table>

<script type="text/javascript">
	$(function() {
		require([ "../js/bx/pwd.js"], function(fn) {
			fn.init();
		});
	});
</script>