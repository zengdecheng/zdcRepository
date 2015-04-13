<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="/css/index3.css" />
<link rel="stylesheet" type="text/css" href="/css/index4.css" />
<link rel="Stylesheet"
	href="/js/pub/jqueryValidation/css/validationEngine.jquery.css" />
<link rel="stylesheet" type="text/css"
	href="/css/order/processOrder.css" />
<script type="text/javascript" src="/js/pub/jquery_1.7.2.js"></script>
<script type="text/javascript" src="/js/pub/require_min.js"></script>
<script type="text/javascript" src="/js/pub/autoFill.js"></script>
<script type="text/javascript" src="/js/pub/autoCompute.js"></script>
<script type="text/javascript" src="/js/pub/fileUploadValidation.js"></script>
<script type="text/javascript">
	requirejs.config({
		baseUrl : "/js/bx/"
	});
</script>
<div id="iframeDiv">
	<form id="tabCosting">
		<div
			style="background: #4b8df8; font-size: 14px; margin: 5px 5px 5px 0; color: #ffffff; width: 100%;">
			<p style="padding: 5px 5px">&nbsp;核价</p>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px; height: 28px; line-height: 28px; padding: 0 1.5% 0 10px;">
			物料成本<input id="materialDescRemoveBtn" type="button" value="删除行"
				class="operate_button"><input id="materialDescAddBtn"
				type="button" value="增加行" class="operate_button">
		</div>
		<div
			style="font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0; width: 96%;">
			<table id="costing" class="yldp_table" id="materialDescTable">
				<input type="hidden" name="materialDelIds" id="materialDelIds">
				<input type="hidden" name="materialDescDelIds"
					id="materialDescDelIds">
				<tr height="28">
					<th width="20px"></th>
					<th width="11%">物料属性</th>
					<th width="5%">主辅料</th>
					<th width="13%">物料名称</th>
					<th width="7%">色号</th>
					<th width="7%">布封</th>
					<th width="7%">标准单件用量</th>
					<th width="7%">大货单价</th>
					<th width="7%">散剪单价</th>
					<th width="7%">损耗率</th>
					<th width="7%">大货金额</th>
					<th width="7%">散剪金额</th>
					<th width="14%">核价备注</th>
				</tr>
			</table>
		</div>
		<input type="hidden" name="oaCost.id" fillName='oaCost.id'>
		<div style="margin-top: 16px;">
			<span style="margin-left: 27px;">物料成本合计（大货）：<input
				id="mGoodsPrice" type="text" name='oaCost.mGoodsPrice'
				class='validate[custom[number2],maxSize[10]]'
				fillName='oaCost.mgoodsPrice'></span> <span
				style="margin-left: 37px;">物料成本合计（散剪）：<input id="mShearPrice"
				type="text" name='oaCost.mShearPrice'
				class='validate[custom[number2],maxSize[10]]'
				fillName='oaCost.mshearPrice'></span>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">外协成本</p>
		</div>
		<div style="margin-top: 5px;">
			<table class="yldp_table" style="margin-left: 26px; width: 96%;">
				<tr>
					<th colspan="8" align="center" height="22px">外协成本</th>
					<th align="center">备注</th>
				</tr>
				<tr>
					<td style="width: 50px">印花：</td>
					<td style="width: 110px"><input id="ostamp" type="text"
						name='oaCost.oStamp' fillName='oaCost.ostamp'
						class='validate[required,custom[number2],maxSize[10]]'
						style="width: 110px; height: 32px;"></td>
					<td style="width: 50px">绣花：</td>
					<td style="width: 110px"><input id="oembroider" type="text"
						name='oaCost.oEmbroider' fillName='oaCost.oembroider'
						class='validate[required,custom[number2],maxSize[10]]'
						style="width: 110px; height: 32px;"></td>
					<td style="width: 50px">洗水：</td>
					<td style="width: 110px"><input id="owash" type="text"
						name='oaCost.oWash' fillName='oaCost.owash'
						class='validate[required,custom[number2],maxSize[10]]'
						style="width: 110px; height: 32px;"></td>
					<td style="width: 80px">总外协费用：</td>
					<td style="width: 110px"><input type="text" id='oTotalPrice'
						name="oaCost.waiXieTotalPrice" fillName="oaCost.waiXieTotalPrice"
						style="width: 110px; height: 32px;"></td>
					<td><input type="text" name='oaCost.oMemo'
						fillName='oaCost.omemo' style="width: 286px; height: 32px;"></td>
				</tr>
			</table>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">加工成本</p>
		</div>
		<div style="margin-top: 5px;">
			<table class="yldp_table" style="margin-left: 26px; width: 96%">
				<tr>
					<th width="2%" height="26px"></th>
					<th colspan="3" width="1%" align="center">B</th>
					<th rowspan="7" width="2%"></th>
					<th colspan="3" width="1%" align="center">D</th>
					<th width="1%" align="center">备注</th>
				</tr>
				<tr>
					<td align="center">SAM</td>
					<td><input id="sam"
						class="validate[required,custom[number2],maxSize[10]]"
						type="text" name='oaCost.pSam' fillName='oaCost.psam'
						style="width: 100px"></td>
					<td align="center">分钟工资率</td>
					<td><input id="minute_wage_rate"
						class="validate[required,custom[number2],maxSize[10]]" value="0.3"
						type="text" name='oaCost.minuteWageRate' fillName='oaCost.minuteWageRate'
						style="width: 100px"></td>
					<td rowspan="6"><input id="cd0" type="text"
						class="validate[custom[number2],maxSize[10]]" name='oaCost.d1'
						fillName='oaCost.d1' style="width: 100px"></td>
					<td rowspan="6"><input id="cd1" type="text"
						class="validate[custom[number2],maxSize[10]]" name='oaCost.d2'
						fillName='oaCost.d2' style="width: 100px"></td>
					<td rowspan="6"><input id="cd2" type="text"
						class="validate[custom[number2],maxSize[10]]" name='oaCost.d3'
						fillName='oaCost.d3' style="width: 100px"></td>
					<td rowspan="6"><input type="text" name='oaCost.pMemo'
						fillName='oaCost.pmemo' style="width: 100px" /></td>
				</tr>
				<tr>
					<td align="center">匠人坊匹配系数</td>
					<td><input id="pfactory_match_0"
						class="validate[required,custom[number2],maxSize[10]]"
						type="text" name='oaCost.pFactoryMatch' fillName='oaCost.pfactoryMatch[0]'
						style="width: 100px"></td>
					<td><input id="pfactory_match_1"
						class="validate[required,custom[number2],maxSize[10]]"
						type="text" name='oaCost.pFactoryMatch' fillName='oaCost.pfactoryMatch[1]'
						style="width: 100px"></td>
					<td><input id="pfactory_match_2"
						class="validate[required,custom[number2],maxSize[10]]"
						type="text" name='oaCost.pFactoryMatch' fillName='oaCost.pfactoryMatch[2]'
						style="width: 100px"></td>
				</tr>
				<tr>
					<td align="center">裁剪</td>
					<td><input id="pcutting_0"
						class="he1 validate[required,custom[number2],maxSize[10]]"
						type="text" name='oaCost.pCutting' fillName='oaCost.pcutting[0]'
						style="width: 100px"></td>
					<td><input id="pcutting_1"
						class="he2 validate[required,custom[number2],maxSize[10]]"
						type="text" name='oaCost.pCutting' fillName='oaCost.pcutting[1]'
						style="width: 100px"></td>
					<td><input id="pcutting_2"
						class="he3 validate[required,custom[number2],maxSize[10]]"
						type="text" name='oaCost.pCutting' fillName='oaCost.pcutting[2]'
						style="width: 100px"></td>
				</tr>
				<tr>
					<td align="center">缝制</td>
					<td><input id="psew_0"
						class="he1 validate[required,custom[number2],maxSize[10]]"
						type="text" name='oaCost.pSew' fillName='oaCost.psew[0]'
						style="width: 100px"></td>
					<td><input id="psew_1"
						class="he2 validate[required,custom[number2],maxSize[10]]"
						type="text" name='oaCost.pSew' fillName='oaCost.psew[1]'
						style="width: 100px"></td>
					<td><input id="psew_2"
						class="he3 validate[required,custom[number2],maxSize[10]]"
						type="text" name='oaCost.pSew' fillName='oaCost.psew[2]'
						style="width: 100px"></td>
				</tr>
				<tr>
					<td align="center">后整</td>
					<td><input id="pLast_0"
						class="he1 validate[required,custom[number2],maxSize[10]]"
						type="text" name='oaCost.pLast' fillName='oaCost.plast[0]'
						style="width: 100px"></td>
					<td><input id="pLast_1"
						class="he2 validate[required,custom[number2],maxSize[10]]"
						type="text" name='oaCost.pLast' fillName='oaCost.plast[1]'
						style="width: 100px"></td>
					<td><input id="pLast_2"
						class="he3 validate[required,custom[number2],maxSize[10]]"
						type="text" name='oaCost.pLast' fillName='oaCost.plast[2]'
						style="width: 100px"></td>
				</tr>
				<tr>
					<td align="center">合计</td>
					<td><input type="text" id="cp0" style="width: 100px"
						name="oaCost.bhe1" fillName="oaCost.bhe1" /></td>
					<td><input type="text" id="cp1" style="width: 100px"
						name="oaCost.bhe2" fillName="oaCost.bhe2" /></td>
					<td><input type="text" id="cp2" style="width: 100px"
						name="oaCost.bhe3" fillName="oaCost.bhe3" /></td>
				</tr>
			</table>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">总报价</p>
		</div>
		<div style="margin-top: 5px;">
			<table class="yldp_table" style="width: 52%; margin-left: 26px;">
				<tr>
					<th colspan="4" height="26px" align="center">核价中心报价</th>
				</tr>
				<tr>
					<td width="10%">订单量：</td>
					<td width="12%" align="center"><input
						class="validate[custom[integer],maxSize[10]]" type="text"
						name='oaCost.orderNum1' fillName='oaCost.orderNum1'
						style="width: 160px"></td>
					<td align="center"><input
						class="validate[custom[integer],maxSize[10]]" type="text"
						name='oaCost.orderNum2' fillName='oaCost.orderNum2'
						style="width: 160px"></td>
					<td align="center"><input
						class="validate[custom[integer],maxSize[10]]" type="text"
						name='oaCost.orderNum3' fillName='oaCost.orderNum3'
						style="width: 160px"></td>
				</tr>
				<tr>
					<td align="center">B</td>
					<td><input id="b0" type="text" style="width: 160px"
						name="oaCost.btotal1" fillName='oaCost.btotal1'></td>
					<td><input id="b1" type="text" style="width: 160px"
						name="oaCost.btotal2" fillName='oaCost.btotal2'></td>
					<td><input id="b2" type="text" style="width: 160px"
						name="oaCost.btotal3" fillName='oaCost.btotal3'></td>
				</tr>
				<tr>
					<td align="center">D</td>
					<td><input id="d0" type="text" style="width: 160px"
						name="oaCost.dtotal1" fillName="oaCost.dtotal1"></td>
					<td><input id="d1" type="text" style="width: 160px"
						name="oaCost.dtotal2" fillName="oaCost.dtotal2"></td>
					<td><input id="d2" type="text" style="width: 160px"
						name="oaCost.dtotal3" fillName="oaCost.dtotal3"></td>
				</tr>
			</table>
			<div style="float: right; margin-right: 22%; position: relative; bottom: 60px;" >
				预计大货订单数量：<span elementFillName="oaOrder.wantDhcnt"></span>
			</div>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">管理信息</p>
		</div>
		<div
			style="width: 96%; font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0;">
			<table style="font-size: 12px; width: 100%;">
				<tr height="28px;">
					<td width="20%">制单:<span id="detailOperator"
						elementFillName="oaOrderDetail.operator"></span></td>
					<td width="45%">处理日期:<span id="detailDate"
						elementFillName="oaOrderDetail.wf_real_start"></span></td>
					<td></td>
				</tr>
				<tr height="28px;">
					<td colspan="2">核价备注:&nbsp;&nbsp;<textarea id="detailContent"
							fillName="oaOrderDetail.content" name="oaOrderDetail.content"
							style="width: 560px; height: 100px;" maxlength="1000"></textarea></td>
					<td>
						<div style="height: 105px;">
							<span style="line-height: 22px;">进度信息:<span>
									<div
										style="height: 100px; width: 80%; float: right; border: 1px solid rgb(205, 205, 205);">
										<p
											style="padding: 0 0 0 10px; margin: 0; background-color: yellow; line-height: 22px; display:none;"
											id="detailSchedule">还未流入</p>
										<ul style="margin: 0; padding: 0 0 0 10px; line-height: 19px;">
											<li id="detailRealStart">流入时间:<span
												elementFillName="oaOrderDetail.wf_real_start" /></li>
											<li id="detailRealFinish">实际完成:<span
												elementFillName="oaOrderDetail.wf_real_finish" /></li>
											<li>流入颜色:<div id="detailRealStartColor" style="color:#fff;margin: 1px 0 4px; width:45px; text-align: center; display: inline-block;"></div></li>
											<li>流出颜色:<div id="detailRealFinishColor" style="color:#fff;margin-bottom: 1px; width:45px; text-align: center; display: inline-block;"></div></li>
											<li style="display:none;" id="detailDuration">标准工时:<span
												elementFillName="oaOrderDetail.wf_step_duration" /></li>
											<li id="detailRealTime">实际耗时:<span
												elementFillName="oaOrderDetail.real_time" /></li>
										</ul>
									</div>
						</div>
					</td>
				</tr>
				<tr height="45px">
					<td colspan="3"><div
							style="float: left; line-height: 40px; margin-right: 15px;">附件：</div>
						<div class="attachment_name" id="otherFile"
							style="line-height: 40px; float: left; width: 50%; overflow: hidden; text-overflow: ellipsis;">&nbsp;</div>
						<input type="hidden" name="oaOrderDetail.otherFile"
						id="hid_attachment_url" fillName="oaOrderDetail.other_file" >
						<div id="otherFileUploadTd" style="float: left;">
							<div style="padding-top: 5px; float: left;">
								<object width="100" height="30" id="objUpload"
									class="uploadFile" attr_index="6"
									classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
									codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
									<param name="movie" value="/swf/xbd.swf">
									<param name="wmode" value="transparent">
									<param name="FlashVars"
										value="url=/oaOrderImgUpload?sysLoginId=1&amp;name=&amp;size=2097152&ext=*.jpg;*.png;*.gif;*.bmp;*.jpeg">
									<embed width="100" height="30" src="/swf/xbd.swf"
										id="objUpload" wmode="transparent"
										type="application/x-shockwave-flash"
										flashvars="url=/oaOrderImgUpload?sysLoginId=1&amp;name=&amp;size=2097152&ext=*.jpg;*.png;*.gif;*.bmp;*.jpeg"
										pluginspage="http://www.adobe.com/go/getflashplayer">
								</object>
							</div>
							<div
								style="color: #b1b1b1; font-size: 12px; float: left; height: 40px; line-height: 40px;">（多个附件请打包后上传）</div>
						</div></td>
				</tr>
			</table>
		</div>
		<div
			style="border-bottom: 1px solid rgb(205, 205, 205); font-size: 14px;">
			<p style="padding: 5px 5px 5px 10px; margin: 0;">异动跟踪</p>
		</div>
		<div
			style="width: 96%; font-size: 14px; margin-left: 2.5%; padding: 5px 0 5px 0;">
			<table style="font-size: 12px; width: 100%;">
				<tr>
					<td colspan="3">
						<table style="font-size: 12px; width: 100%;" id="oaTrackesTable">
						</table>
					</td>
				</tr>
				<tr id="yidongMemo">
					<td width="53px">异动说明:</td>
					<td width="573px"><input type="text" id="yidongContent"
						maxlength="200"></td>
					<td><input type="button" value="发布" id="yidongBtn"
						class="process_button"></td>
				</tr>
			</table>
		</div>
		<input type="hidden" id="orderId" name="orderId"> <input
			type="hidden" id="processOrder" name="processOrder" value="false">
		<input type="hidden" id="wfStep" name="wfStep">
	</form>
	<div
		style="width: 98%; height: 40px; line-height: 40px; text-align: center;"
		id="processOrderDive">
		<input type="button" value="保存并提交" class="process_button"
			id="processBtn"><input type="button" value="保存草稿"
			class="process_button" id="saveBtn"><input type="button"
			value="退回上一步" class="process_button" id="backBtn"
			onclick="javascript:parent.backOrder('iframeCosting');"> <input
			onclick="javascript:if(confirm('确认要放弃吗？')) {history.go(-1);}"
			type="button" value="放弃" class="process_button">
	</div>
	<div
		style="width: 98%; height: 40px; line-height: 40px; text-align: center;"
		id="orderDetailDive">
		<input type="button" value="导出Excel" class="process_button"
			id="exportBtn"><input onclick="javascript:history.go(-1);"
			type="button" value="返回列表" class="process_button">
	</div>
	<div>&nbsp;</div>
</div>
<script type="text/javascript">
	$(function() {
		require([ "order/tabCosting" ], function(fn) {
			fn.init();
		});
	});
</script>

