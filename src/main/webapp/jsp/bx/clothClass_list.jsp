<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
	<title>${systemEnvironment }品类管理 | Singbada ERP</title>
</head>
<div class="right_panel">
	<div class="grid_panel">
		<div class="grid_panel_tool">
			<a href="/bx/addClothClass" class="btn_lan">添加品类</a>
		</div>
		<div class="grid_body">
			<ul class="ui_tab">
				<li class="act"><span>品类管理</span></li>
			</ul>
			<div class="ui_tab_warp">
				<div class="ui_tab_content" style="display: block">
					<div class="base_time_cfg">
						<div class="base_time_cfg_left">
							<table cellpadding="0" cellspacing="0" border="0" width="98%">
								<tr>
									<th style="width:88px">编码</th>
									<th>品类</th>
									<th width="98px">操作</th>
								</tr>
								<s:iterator value="oaDts">
									<tr>
										<td>${code}</td>
										<td>${value }</td>
										<td class="oper"><a
											href="/bx/editClothClass?oaDt.id=${id }" class="btn_edit"><em></em>
												<span>编辑</span></a><br /> <a
											href="/bx/delClothClass?oaDt.id=${id }"
											onclick="return confirm('确认删除此品类？')" class="btn_del"><em></em>
												<span>删除</span></a></td>
									</tr>
								</s:iterator>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>