<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
	<title>${systemEnvironment }时间控制表导出 | Singbada ERP</title>
</head>
  <script language="javascript" type="text/javascript" src="${ctx}/js/pub/My97DatePicker/WdatePicker.js"></script>
	<form action="/bx/getDHTC2" method="get" >
             <div class="z_assign_list" id="form_search_div">
                   <table cellpadding="0" cellspacing="0" border="0" width="760">
                   <tr>
                     <td><table cellpadding="0" cellspacing="0" border="0">
                       <tr>
                         <td width="150" height="40"><label class="">导出大货生产订单控制表：</label></td>
                       </tr>
                       <tr>
						<td width="65"><label class="">订单时间：</label></td>
						<td width="260" colspan="2"><input id="begin_time" type="text" class="z_inp2" name="fsp.map.begin_time" value="${fsp.map.begin_time}" onFocus="WdatePicker({readOnly:true})" /> 
						- <input id="end_time" type="text"class="z_inp2" name="fsp.map.end_time" value="${fsp.map.end_time}" onFocus="WdatePicker({readOnly:true})" /></td>
                        <td><input id="search_btn" type="button" onclick="downCSV()" value="导&nbsp;&nbsp;出" class="z_submit2" /></td>
                       </tr>
                     	</table>
                     </td>
                   </tr>
                   </table>
            </div>
     </form>
     <script type="text/javascript">
     	function downCSV(){
			var params = $("#form_search_div").find("input[name^='fsp.map'][type='text'][value!=''],select[name^='fsp.map'][value!=''][value!='0']").serialize();
//			window.location.href = "/mr/export_csv?" + (!!params ? "&" + params : "");
			$.get("/bx/getDHTC3?" + (!!params ? "&" + params : ""),function(data){
				window.location.href=data;
//				window.open(data);
			});
     		
     	}
     </script>