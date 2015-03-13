<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${systemEnvironment }添加品类 | Singbada ERP</title>
</head>
<body>
	<div>
  		<span>一级品类管理>>品类添加</span>
  	</div>
  	<div>
  		<span>品类信息</span>
  	</div>
  	<form action="/category/add" method="post">
  	<div>
  		<table>
  			<tr>
  				<td><span style="color:red">*</span>名称：</td><td><input type="text" name="oaCategory.name"></td>
  				<td><span style="color:red">*</span>编号：</td><td><input type="text" name="oaCategory.code"></td>
  			</tr>
  			<tr>
  				<td><span style="color:red">*</span>状态：</td>
  				<td>
  					<select name="oaCategory.status">
  						<option>请选择</option>
  						<option value="0">禁用</option>
  						<option value="1">激活</option>
  					</select>
  				</td>
  			</tr>
  			<tr>
  				<td>说明：</td><td><textarea rows="" cols="" name="oaCategory.explainTxt"></textarea></td>
  			</tr>
  		</table>
  	</div>
  	<div>
  		<span>品类缓冲时间设置</span>
  	</div>
  	<div>
  		<table>
  			<tr>
  				<td><span style="color:red">*</span>大货标准缓冲（小时）：</td><td><input type="text" name="oaCategory.dahuoCyc"></td>
  				<td><span style="color:red">*</span>打版标准缓冲（小时）：</td><td><input type="text" name="oaCategory.dabanCyc"></td>
  			</tr>
  			<tr>
  				<td>绣花（小时）：</td><td><input type="text" name="oaCategory.embroidery"></td>
  				<td>洗水（小时）：</td><td><input type="text" name="oaCategory.washwaterTime"></td>
  				<td>印花（小时）：</td><td><input type="text" name="oaCategory.printingTime"></td>
  			</tr>
  			<tr>
  				<td>缩折/打条（小时）：</td><td><input type="text" name="oaCategory.foldingTime"></td>
  				<td>打揽（小时）：</td><td><input type="text" name="oaCategory.dalanTime"></td>
  				<td>订珠（小时）：</td><td><input type="text" name="oaCategory.beadsTime"></td>
  			</tr>
  			<tr>
  				<td>销售等待（小时）：</td><td><input type="text" name="oaCategory.sellWait"></td>
  				<td>货款等待（小时）：</td><td><input type="text" name="oaCategory.paymentWait"></td>
  				<td>其他（小时）：</td><td><input type="text" name="oaCategory.otherTime"></td>
  			</tr>
  			<tr>
  				<td>备注：</td><td><textarea name="oaCategory.remark"></textarea></td>
  			</tr>
  		</table>
  		<div>
  			<input type="submit" value="保存">
  			<input type="button" value="取消">
  		</div>
  	</div>
  	</form>
  	<script type="text/javascript">
  		$(function(){
  			require("/category/page4add.js",function(fn){
  				fn.init();
  			});
  		});
  	</script>
</body>
</html>