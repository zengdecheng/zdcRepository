<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<form id="form1" action="/bx/addOrder" method="post" enctype="multipart/form-data">
	<dl>
		<dt>
			<span class="pad_t5">订单类型：</span>
		</dt>
		<dd>
			<label class="mar_r25"><input type="radio" class="z_radio"name="oaOrder.type" onclick="javascript:window.location='/bx/add_baojia'" value="1" />模拟报价</label> 
			<label class="mar_r25"><input type="radio" class="z_radio" name="oaOrder.type" onclick="javascript:window.location='/bx/add_daban'" value="2" />样衣打版</label> 
			<label class="mar_r25"><input type="radio" class="z_radio" name="oaOrder.type" onclick="javascript:window.location='/bx/add_dahuo'"value="3" />大货生产</label> 
			<label class="mar_r25"><input type="radio" class="z_radio" name="oaOrder.type" value="4" checked="checked" />售后服务</label>
		</dd>

		<dt>
			<span>关联订单：</span>
		</dt>
		<dd>
			<input type="text" class="z_inp" name="oaOrder.oaOrder" id="assOrder"/>
	</dd>

		<dt>
			<span>客户编号：</span>
		</dt>
		<dd>
			<input type="text" class="z_inp" style="display:none" name="cusCode" id="cusCode"
			title="支持中英文数字输入模糊匹配" /><input type="button" id="btnHid" value="手动获取" style="display:none">
		<input type="text" class="z_inp" name="oaOrder.cusCode" id="cusCodeHid" style="background:#EEE0E5;" readonly
			/>
		</dd>

		<dt>
			<span>业务担当：</span>
		</dt>
		<dd>
			<input type="text" class="z_inp" name="oaOrder.sales" id="sales" title="支持中文输入"/>

		<dt>
			<span>订单编号：</span>
		</dt>
		<dd>
			<input type="text" name="oaOrder.orderCode" class="z_inp" id="orderCode"  title="支持数字输入"/>
		</dd>

		<dt>
			<span>订单款号：</span>
		</dt>
		<dd>
			<input type="text" class="z_inp mra_r8" id="style_code_0" title="支持英文和数字输入" name="styleCode"
				onblur='style_code_total("style_code_0")' />
				<a onclick="add()" id="add_style_code">+新增款号</a><br />
		</dd>
		<input type="hidden" id="total" name="oaOrder.styleCode" />

		<dt>
			<span>客户手机：</span>
		</dt>
		<dd>
			<input type="text" class="z_inp" id="tel"/>
		<input type="hidden" name="oaOrder.tel" id="orderTel" value=""/>
		</dd>
		<dt>
			<span>上传附件:</span>
		</dt>
		<dd>
		<span style="display:inline-block;">
		<object id="objUpload" height="30"
			classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="/swf/xbdInner.swf" />
			<param name="wmode" value="transparent" />
			<param name="FlashVars" value="url=/oaOrderFileUpload?sysLoginId=1&size=1024000&ext=*.jpg;*.png;*.gif" />
			<embed src="/swf/xbdInner.swf" id="objUpload" width="260" height="30" align="middle"
				wmode="transparent" type="application/x-shockwave-flash"
				FlashVars="url=/oaOrderFileUpload?sysLoginId=1&size=1024000&ext=*.jpg;*.png;*.gif"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
		</object>
		</span>
		<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<label>包含&nbsp;&nbsp;售后服务处理表</label>		
		 <br/>
		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="fileShow" href="#">&nbsp;</a>
		<input type="hidden" name="oaOrder.fileUrl" id="hid_attachment_url"/>
		</dd>
		<a style="margin-left:60px" href="http://oa.singbada.cn/file/oa/workflow/file/summary.xls">汇总表格下载</a>
		<br/>
		<dt>
			<span>问题说明：</span>
		</dt>
		<dd>
			<textarea class="z_textarea1" id="wtsm"></textarea>
		</dd>

		<dt>
			<span>建议方案：</span>
		</dt>
		<dd>
			<textarea class="z_textarea1" id="jyfa"></textarea>
		</dd>

		<dt>
			<span>备注说明：</span>
		</dt>
		<dd>
			<textarea class="z_textarea2" id="bzsm"></textarea>
		</dd>
			<textatea name="oaOrder.memo" id="memo" style="display:none"></textatea>
		<dt></dt>
		<dd>
			<input type="button" value="确定" class="mar_r z_btn1" id="bsubmit"/> 
			<input type="button" value="取消" class="z_btn2" id="cancle"/>
		</dd>
		<input type="hidden" id="whichButton" name="whb" value="0"/>
	</dl>
</form>
<script type="text/javascript" src="../js/bx/styleCode.js"></script>
<link rel="Stylesheet" href="../css/jquery.autocomplete.css" />
<script type="text/javascript" src="../js/bx/jquery.autocomplete.js"></script>
<script type="text/javascript">
$(function(){
	require(["add_order"],function(fn){
		fn.init();
	});
});

//图片上传回调函数
function uploadComplete(data) {
	data = data.replace('<meta http-equiv="content-type" content="text/html; charset=UTF-8">',"");
			if (data === "error") {
				alert("上传失败！");
				return;
			}
			if (data === "empty") {
				alert("上传文件为空！");
				return;
			}
			if(data === "ok"){
				alert("新增模拟报价成功");
				return;
			}
			var json = $.parseJSON(data);
			var fileType = json.fileType;
			if (fileType == "file") {
				$("#hid_attachment_url").val(json.url);
				$("#fileShow").show();
				$("#fileShow").text(json.attachment_name);
				$("#fileShow").attr("href",json.url);
			} else if (fileType == "pic") {
				$("#hid_pic_url").val(json.url);
				$("#pic_show").show();
				$("#pic_show").text(json.attachment_name);
				$("#pic_show").attr("href",json.url);
			}			
		}



</script>