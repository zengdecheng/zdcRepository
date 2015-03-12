<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />

<style type="text/css">
table{
	margin:30px auto;
}
.button {
	width: 140px;
	line-height: 38px;
	text-align: center;
	font-weight: bold;
	color: #fff;
	text-shadow: 1px 1px 1px #333;
	border-radius: 5px;
	margin: 0 20px 20px 0;
	position: relative;
	overflow: hidden;
}

.button:nth-child(6n) {
	margin-right: 0;
}
.button.blue{
border:1px solid #1e7db9;
box-shadow: 0 1px 2px #8fcaee inset,0 -1px 0 #497897 inset,0 -2px 3px #8fcaee inset;
background: -webkit-linear-gradient(top,#42a4e0,#2e88c0);
background: -moz-linear-gradient(top,#42a4e0,#2e88c0);
background: linear-gradient(top,#42a4e0,#2e88c0);
}
.ordernum{
	width:100%;
	line-height:38px;
}
</style>
<title>${systemEnvironment }订单进度查询——辛巴达</title>
</head>
<body>
	<table>
		<tr>
			<td style="font-size: 20px">请输入订单号:</td>
		</tr>
		<tr>
			<td><input class="ordernum" type="text" id="orderNum" /></td>
		</tr>
		<tr>
			<td><!-- <input type="button" value="查看" onclick="changePage()" /> -->
				<button class="button blue" type="button" onclick="changePage()">查询</button>
			</td>
		</tr>
	</table>
	<script>
		function changePage() {
			var orderNum = document.getElementById("orderNum").value;
			if (orderNum == "") {
				alert("请输入订单号");
			} else {
				window.location = "/bx/viewOrderDetailOnPhone?oaOrder.styleCode="
						+ orderNum;
			}
		}
	</script>
</body>
</html