<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script language="javascript" type="text/javascript" src="${ctx}/js/pub/My97DatePicker/WdatePicker.js"></script>
<head>
<title>${systemEnvironment }供应链日报|SingbadaERP</title>
</head>

<div class="step_tool"
	style="float: none; margin: 0 0 5px; width: 100%;">
	<span>一级品类管理&nbsp;&lt;&lt;&nbsp;品类列表</span>
</div>
<div>
    <div class="z_assign_list" id="form_search_div">
        <form method="post" id="orderList">
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td height="40"><label class="">日期：</label></td>
                    <td><input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.odel_wf_real_start" value="${fsp.map.odel_wf_real_start}" onFocus="WdatePicker({readOnly:true})" /> - <input type="text" class="z_inp2 createTime" style="width: 75px;" name="fsp.map.odel_wf_real_start1" value="${fsp.map.odel_wf_real_start1}" onFocus="WdatePicker({readOnly:true})" /></td>
                </tr>

                <tr>
                    <td colspan="8" align="right"><input type="submit" value="查询" style="width: 60px;" />
                        <input id="reset_btn" type="button" value="重置" style="width: 60px;" />

                        <input type="hidden" id="yxjOrderHid" name="fsp.map.yxjOrderHid" value="${fsp.map.yxjOrderHid}" />
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div>
        <table border="0" cellspacing="0" cellpadding="0"
                   class="z_table_style2" width="1008">
            <tr>
                <th width="8" height="40"></th>
                <th width="80">编号</th>
                <th width="160">品类名称</th>
                <th width="200">说明</th>
                <th width="180">准生产缓冲时间-大货生产标</th>
                <th width="180">准生产缓冲时间-样衣打版标</th>
                <th width="100">状态</th>
                <th width="100">操作</th>
            </tr>
            <s:if test="beans.size()==0">
                <tr>
                    <td colspan="8" align="center">当前没有任何品类信息，请单击添加品类按钮进行添加！</td>
                </tr>
            </s:if>
            <s:else>
                <s:iterator value="beans">
                    <tr>
                        <td></td>
                        <td>${map.code }</td>
                        <td>${map.name }</td>
                        <td>${map.explain_txt }</td>
                        <td class="category_cyc">${map.dahuo_cyc }</td>
                        <td class="category_cyc">${map.daban_cyc }</td>
                        <td class="category_status">${map.status }</td>
                        <td class="z_title_sty5">
                            <a href="/category/page4detail?oaCategory.id=${map.id }">详情</a>&nbsp;&nbsp;<a
                                    href="/category/page4edit?oaCategory.id=${map.id }">编辑</a></td>
                    </tr>
                </s:iterator>
            </s:else>
        </table>
    </div>

	<s:include value="/jsp/parts/page.jsp"></s:include>
	<s:if test="beans.size()==0">
		<div style="float: right; margin-right: 100px; margin-top: 25px;">
			<input id="addCategory" type="button" value="添加品类"
				style="width: 100px; margin-right: 10px; height: 26px;" />
		</div>
	</s:if>
	<s:else>
		<div style="float: right; margin-right: 100px; margin-top: -48px;">
			<input id="addCategory" type="button" value="添加品类"
				style="width: 100px; margin-right: 10px; height: 26px;" />
		</div>
	</s:else>
</div>
<script type="text/javascript">
	$(function() {
		require([ "/js/category/page4list.js" ], function(fn) {
			fn.init();
		})
	});
</script>
