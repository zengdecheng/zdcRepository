<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
	<title>${systemEnvironment }设置基准时间 | Singbada ERP</title>
</head>
<div class="right_panel">
	<div class="grid_panel">
		<div class="grid_panel_tool">
			<a href="/bx/timebaseConfig1" class="btn_lan">新增基础配置</a>
		</div>
		<div class="grid_body">
			<ul class="ui_tab">
				<li class="act"><span>基准时间配置</span></li>
			</ul>
			<div class="ui_tab_warp">
				<div class="ui_tab_content" style="display: block">
					<div class="base_time_cfg">
						<div class="base_time_cfg_left">
							<table cellpadding="0" cellspacing="0" border="0" width="98%">
								<tr>
									<th>所属类目</th>
									
									<th>所属品类</th>
									<th>所属流程</th>
									<th>流程时长(小时)</th>
									<th width="98px">操作</th>
								</tr>
								<s:iterator value="oaTimebases" status="st">
									<tr>
										<td align="left">${name }</td>
										<td>${clothClass}</td>
										<td>${defineKey }</td>
										<td class="tot">${totalDuration}</td>
										<td class="oper"><a
											href="/bx/editTimebaseConfig?oaTimebase.id=${id }" class="btn_edit"><em></em>
												<span>编辑</span></a><br /><a
											href="/bx/timebaseConfig1?oaTimebase.id=${id }" class="btn_edit"><em></em>
												<span>编辑总时长</span></a><br /> <a
											href="/bx/delTimebaseConfig?oaTimebase.id=${id }" onclick="return confirm('确认删除此配置？')" class="btn_del"><em></em>
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
<script type="text/javascript">
	$(function() {
		$('.tot').each(function() {
			var a=$(this).html();
			$(this).html(trms(a));
		});
	});
	function trms(arg1){
		arg2=1000 * 60 * 60;
		var t1 = 0, t2 = 0, r1, r2;
		try {
			t1 = (1 * arg1).toString().split(".")[1].length;
		} catch (e) {
		}
		try {
			t2 = (1 * arg2).toString().split(".")[1].length;
		} catch (e) {
		}
		with (Math) {
			r1 = Number((1 * arg1).toString().replace(".", ""));
			r2 = Number((1 * arg2).toString().replace(".", ""));
			var ss = (r1 / r2) * pow(10, t2 - t1);
			return Math.round(ss * 100) / 100;
		}
	}
</script>