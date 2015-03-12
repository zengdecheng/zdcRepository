<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
  <script language="javascript" type="text/javascript" src="${ctx}/js/pub/My97DatePicker/WdatePicker.js"></script>
<%-- 	<form action="/bx/getDepartmentTimeDH" method="get" >
             <div class="z_assign_list" id="form_search_div">
                   <table cellpadding="0" cellspacing="0" border="0" width="760">
                   <tr>
                     <td><table cellpadding="0" cellspacing="0" border="0">
                       <tr>
                         <td width="150" height="40"><label class="">导出大货生产订单控制表：</label></td>
                         <td width="50" height="40" colspan="2"><label>操作人</label>
                         	<input type="text" class="z_inp2" name="fsp.map.operator" value="李小龙"/>
                         </td>
                         <td width="240" height="40" colspan="2" >
                         	<label class="">步骤:</label>
                         	<select class="z_inp2" name="fsp.map.step" >
	                         	<option name="fsp.map.step" value="c_create_dahuo_1">新建大货生产订单</option>
								<option name="fsp.map.step" value="c_fi_confirm_2">确认定金到账</option>
								<option name="fsp.map.step" value="c_ppc_assign_3">制作纸样、生产工艺制造单</option>
								<option name="fsp.map.step" value="c_fi_pay_4" selected>采购面料</option>
								<option name="fsp.map.step" value="c_ppc_factoryMsg_5">验布、裁剪	</option>
								<option name="fsp.map.step" value="c_qc_cutting_6">车缝</option>
								<option name="fsp.map.step" value="c_ppc_confirm_7">尾查</option>
								<option name="fsp.map.step" value="c_qc_printing_8">确认尾款到账</option>
								<option name="fsp.map.step" value="c_ppc_confirm_9">发货</option>
                         	</select>
                         </td>
                       </tr>
                       <tr>
						<td width="65"><label class="">订单时间：</label></td>
						<td width="260" colspan="2"><input id="begin_time" type="text" class="z_inp2" name="fsp.map.begin_time" value="${fsp.map.begin_time}" onFocus="WdatePicker({readOnly:true})" /> 
						- <input id="end_time" type="text"class="z_inp2" name="fsp.map.end_time" value="${fsp.map.end_time}" onFocus="WdatePicker({readOnly:true})" /></td>
                        <td colspan="2"><input id="search_btn" type="button" onclick="downCSV()" value="导&nbsp;&nbsp;出" class="z_submit2" /></td>
                       </tr>
                     	</table>
                     </td>
                   </tr>
                   </table>
            </div>
     </form> --%>
     
     <form action="/bx/getDepartmentTime" method="post" >
             <div class="z_assign_list" id="form_search_div">
                   <table cellpadding="0" cellspacing="0" border="0" width="760">
                   <tr>
                     <td><table cellpadding="0" cellspacing="0" border="0">
                       <tr>
                         <td width="60" height="40"><label class="">订单类型：</label></td>
                         <td width="100">
                         	<select id="orderType" >
                         		<option value="2" >样衣打版</option>
                         		<option value="3" >大货生产</option>
                         	</select>
                         </td>
                         <td width="60" height="40" ><label class="">流程节点:</label></td>
                         <td width="130" height="40" >
                         	<select class="z_inp2" name="fsp.map.step" >
	                         	<option  value="c_create_dahuo_1"   >新建大货生产订单</option>
								<option  value="c_fi_confirm_2" >确认定金到账</option>
								<option  value="c_ppc_assign_3" >制作纸样、生产工艺制造单</option>
								<option  value="c_fi_pay_4" >采购面料</option>
								<option  value="c_ppc_factoryMsg_5" >验布、裁剪	</option>
								<option  value="c_qc_cutting_6" >车缝</option>
								<option  value="c_ppc_confirm_7" >尾查</option>
								<option  value="c_qc_printing_8" >确认尾款到账</option>
								<option  value="c_ppc_confirm_9" >发货</option>
                         	</select>
                         </td>
                         <td width="50" height="40" align="center"><label>操作人：</label></td>
                         <td width="80">
                         	<select name="fsp.map.operator" >
                         		<option value="李小龙">李小龙</option>
                         		<option value="晴画">晴画</option>
                         		<option value="阿碧">阿碧</option>
                         	</select>
                         </td>
                         <td width="60" height="40"><label>是否超时</label></td>
                         <td><select >
                         		<option>全部</option>
                         		<option>未超时</option>
                         		<option>超时</option>
                         	</select>
                         </td>
                       </tr>
                        <tr>
				 		<td width="65"><label class="">订单时间：</label></td>
						<td width="260" colspan="3"><input id="begin_time" type="text" class="z_inp2" name="fsp.map.begin_time" value="${fsp.map.begin_time}" onFocus="WdatePicker({readOnly:true})" /> 
						- <input id="end_time" type="text"class="z_inp2" name="fsp.map.end_time" value="${fsp.map.end_time}" onFocus="WdatePicker({readOnly:true})" /></td>
                       	<td width="80" ><input id="search_btn" type="submit"   class="z_submit2" value="查&nbsp;&nbsp;询" /></td>
                        <td width="80"><a href = "${fsp.map.url}"  class="z_submit2" >导出</a> <!-- <input id="export" type="button" value="导&nbsp;&nbsp;出" class="z_submit2" /> --></td>
                       </tr>
                     	</table>
                     </td>
                   </tr>
                   </table>
            </div>
     </form>
       <table width="780" border="0" cellspacing="0" cellpadding="0" class="z_table_style2">
                 <tr>
                   <th width="20"></th>
                   <th height="40" width="70">样衣款号</th>
                   <th width="115">开始时间</th>
                   <th width="115">结束时间</th>
                   <th width="110">是否超时</th>
                   <th width="75">退回次数</th>
                   <th width="160">流程备注</th>
                   <th colspan="1" class="z_title_sty6" style="text-align:left;">操作</th>
                 </tr>
                 <s:iterator value="superList" status="status">
                 <tr>
                   <td></td>
                   <td class="z_title_sty1"><span>${map.style_code}</span></td>
                   <td class="z_title_sty1">${map.f_start}</td>
                   <td class="z_title_sty1">${map.f_finish}</td>
                   <td class="z_title_sty1">${map.out_time}</td>
                   <td class="z_title_sty1"><s:property value="${map.times} -1"/></td>
                   <td class="z_title_sty1" alt="${map.content}" style="text-overflow:ellipsis;white-space: nowrap; overflow: hidden;">${map.content}</td>
                   <td class="z_title_sty1"><a href="/bx/viewOrderDetail?oaOrder.id=${map.oa_order}">查看订单</a></td>
                  </tr>
                 </s:iterator>
     </table>
	<s:include value="%{ctx}/jsp/parts/pageControl.jsp"></s:include>

  <%--    <script type="text/javascript">
     	function downCSV(){
			var params = $("#form_search_div").find("input[name^='fsp.map'][type='text'][value!=''],select[name^='fsp.map'][value!=''][value!='0']").serialize();
//			window.location.href = "/mr/export_csv?" + (!!params ? "&" + params : "");
			alert(params);
			$.get("/bx/getDepartmentTime?" + (!!params ? "&" + params : ""),function(data){
				window.location.href=data;
//				window.open(data);
			});
     		
     	}
     </script> --%>
     <script type="text/javascript">
			$(function(){
				require(["DHTC"], function (fn) {
					fn.init();
				});
			});
	</script>