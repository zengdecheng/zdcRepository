  <%@ page language="java" contentType="text/html;charset=UTF-8"%>
  <%@ taglib prefix="s" uri="/struts-tags"%>
  <script language="javascript" type="text/javascript" src="${ctx}/js/pub/My97DatePicker/WdatePicker.js"></script>
  <script language="javascript" type="text/javascript"
	src="${ctx}/js/pub/ajaxfileupload.js"></script>
 <form id="form1" action="/bx/addOrder" method="post" enctype="multipart/form-data" >
      <dl>
            <dt><span class="pad_t5">订单类型：</span></dt>
	<dd>
		<label class="mar_r25"><input type="radio" class="z_radio" name="oaOrder.type" onclick="javascript:window.location='/bx/add_baojia'" value="1"/>模拟报价</label>
		<label class="mar_r25"><input type="radio" class="z_radio" name="oaOrder.type" value="2" checked="checked"/>样衣打版</label>
		<label class="mar_r25"><input type="radio" class="z_radio" name="oaOrder.type" onclick="javascript:window.location='/bx/add_dahuo'" value="3" />大货生产</label> 
		<label class="mar_r25"><input type="radio" class="z_radio" name="oaOrder.type" onclick="javascript:window.location='/bx/add_shouhou'" value="4"/>售后服务</label>
	</dd>
             
           <dt><span class="pad_t5">加急程度：</span></dt>
	<dd>
		<input type="radio" class="z_radio" name="oaOrder.isUrgent" value="1" checked="checked"/> <label class="mar_r43">正常</label> 
		<input type="radio" class="z_radio" name="oaOrder.isUrgent" value="0"/><label class="z_radio_tip">加急</label></dd>
             
           <%-- <dt><span>关联订单：</span></dt>
	<dd>
		<input type="text" class="z_inp" name="oaOrder.oaOrder" id="assOrder"/>
	</dd> --%>

             
         <dt><span>客户编号：</span></dt>
	<dd><input type="text" class="z_inp" style="display:none" name="cusCode" id="cusCode"
			title="支持中英文数字输入模糊匹配" /><input type="button" id="btnHid" value="手动获取" style="display:none">
		<input type="text" class="z_inp" name="oaOrder.cusCode" id="cusCodeHid" style="background:#EEE0E5;" readonly
			/></dd>
             
          <dt><span>业务担当：</span></dt>
	<dd><input type="text" class="z_inp" name="oaOrder.sales" id="sales" title="支持中文输入"/></dd>
             
           	<%-- <dt><span>订单编号：</span></dt>
	<dd><input type="text" class="z_inp" name="oaOrder.orderCode" id="orderCode"  title="支持数字输入"/></dd> --%>
           <dt><span>合同编号：</span></dt>
	<dd><input type="text" class="z_inp mra_r8" id="orderCode" name="oaOrder.orderCode" title="支持英文和数字输入" onblur='style_code_total("orderCode")'/>
		<!-- <a onclick="add()" id="add_style_code">+新增款号</a> --><br />
	</dd>
			<dt><span>合同金额：</span></dt>
	<dd><input type="text" class="z_inp mra_r8" id="contractAmount" name="oaOrder.contractAmount" title="支持数字输入" />
		<!-- <a onclick="add()" id="add_style_code">+新增款号</a> --><br />
	</dd>  
           <dt><span>订单款号：</span></dt>
	<dd><input type="text" class="z_inp mra_r8" id="style_code_0" name="styleCode" title="支持英文和数字输入" onblur='style_code_total("style_code_0")'/>
		<!-- <a onclick="add()" id="add_style_code">+新增款号</a> --><br />
	</dd>
	<input type="hidden" id="total" name="oaOrder.styleCode"/> 
    
    <dt>
		<span>客户手机：</span>
	</dt>
	<dd>
		<input type="text" class="z_inp" id="tel"/>
		<input type="hidden" name="oaOrder.tel" id="orderTel" value=""/>
	</dd>        
         <dt><span>所属品类：</span></dt>
	<dd>
	
	
	<input  class="z_select" autocomplete="off" type="text"
			style="width: 180px;height:22px;" id="cloth1"/>
	<input name="oaOrder.clothClass" autocomplete="off" type="hidden" id="cloth2"/>
	
	<%-- <select name="oaOrder.clothClass" class="z_select"
			style="width: 153px;" id="cloth2">
			<s:iterator value="%{@com.xbd.oa.utils.XbdBuffer@getOaDtList('1')}">
				<option value="<s:property value="code"/>"><s:property value="value"/></option>
			</s:iterator>
		</select> --%>
		
		
	</dd>
	<dt>
		<span>所属地区：</span>
	</dt>
	<dd>
		<select name="oaOrder.city" class="z_select"
			style="width: 153px;">
			<option value="广州">广州</option>
			<option value="杭州">杭州</option>
		</select>
	</dd>
             
            <dt><span>期望价格：</span></dt>
                  <dd><input type="text" class="z_inp1" name="oaOrder.priceMin" id="minPr" title="支持数字输入"/>
                  &nbsp;-&nbsp;
                  	  <input type="text" class="z_inp1" name="oaOrder.priceMax" id="maxPr" title="支持数字输入"/>
                  	  <span>单位:元/件</span>
                  	  <span id="priceError" style="display:none;color:#FF0000"></span>
                  	  </dd>
                          
           <dt><span>预计交期：</span></dt>
	<dd><input type="text" class="z_inp" name="oaOrder.exceptFinish" id="exceptFinish" onFocus="WdatePicker({readOnly:true})"/></dd>
             
            <dt><span>预计数量：</span></dt>
                  <dd>
                  	<input type="text" class="z_inp" name="oaOrder.wantCnt" id="wantCnt" title="支持数字输入"/>
                  	<span>单位:件</span>
                  </dd>
	<dt>
		<span>上传图片：</span>
	</dt>
		<dd >
				<div class="z_up_img mar_l60" id="style_pic">
				<span>+点击上传图片</span>
				</div>
				<input type="hidden" name="oaOrder.styleUrl" id="styleUrl"/>
				<input type="file" name="file1" id="file1" style="display:none" onchange="uploadImg()"/>
		</dd>
	<dt>
		<span>上传附件：</span>
	</dt>
	<dd style="height:124px">
		<div style="float:left; width:50%;height:110px;">
		EXCEL：
		<span style="display:inline-block;">
		<object id="objUpload" height="30"
			classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="/swf/xbdInner.swf" />
			<param name="wmode" value="transparent" />
			<param name="FlashVars" value="url=/oaOrderFileUpload?sysLoginId=1&size=10485760&ext=*.jpg;*.png;*.gif" />
			<embed src="/swf/xbdInner.swf" id="objUpload" width="260" height="30" align="middle"
				wmode="transparent" type="application/x-shockwave-flash"
				FlashVars="url=/oaOrderFileUpload?sysLoginId=1&size=10485760&ext=*.jpg;*.png;*.gif"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
		</object>
		</span>
		<br/>
		<label>包含&nbsp;&nbsp;样版通知单、样版控制表</label>		
		 <br/><a id="fileShow" href="#">&nbsp;</a>
		<input type="hidden" name="oaOrder.fileUrl" id="hid_attachment_url"/>
		<br/>
		图&nbsp;片：
		<span style="display:inline-block;">
		<object id="objUpload" height="30"
			classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="/swf/xbd.swf" />
			<param name="wmode" value="transparent" />
			<param name="FlashVars"
				value="url=/oaOrderImgUpload?sysLoginId=1&size=10485760&ext=*.jpg;*.png;*.gif" />
			<embed src="/swf/xbd.swf" id="objUpload" width="260" height="30" align="middle"
				wmode="transparent" type="application/x-shockwave-flash"
				FlashVars="url=/oaOrderImgUpload?sysLoginId=1&size=10485760&ext=*.jpg;*.png;*.gif"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
		</object>
		</span>
		<br/><a id="pic_show" href="#">&nbsp;</a>
		<input type="hidden" name="oaOrder.picUrl" id="hid_pic_url" />
		</div>
	</dd>
	<a style="margin-left:60px" href="http://oa.singbada.cn/file/oa/workflow/file/2.xls">样衣打版表格汇总 下载</a>
	<br/>                 
             <dt><span>备注说明：</span></dt>
             <dd><textarea class="z_textarea" name="oaOrder.memo"></textarea></dd>
             
             <dt></dt>
             <dd>
                 <input type="button" value="确定" class="mar_r z_btn1" id="bsubmit"/>
                 <input type="button" value="继续新增打版" class="mar_r z_btn1" id="submitC"/>
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

function uploadImg(){
	var ajaxUrl = "/oaOrderImgUpload?sysLoginId=1&size=10485760&ext=*.jpg;*.png;*.gif";
	$.ajaxFileUpload({
		url : ajaxUrl,//用于文件上传的服务器端请求地址
		secureuri : false,//一般设置为false
		fileElementId : 'file1',//文件上传空间的id属性  
		dataType : 'json',//返回值类型 一般设置为json
		success : function(data, status) //服务器成功响应处理函数
		{
			//var json = $.parseJSON(data);
			//alert(data.url);
			$("#style_pic").empty();
			/*$("#style_pic").html("<img src='"+data.url.replace("oa.singbada.cn","121.196.128.123")+"' width='180px' height='180px'/>");*/
			$("#style_pic").html("<img src='"+data.url+"' width='180px' height='180px'/>");
			$("#styleUrl").val(data.url);
		},
		error : function(data, status, e)//服务器响应失败处理函数
		{
			alert(e);
		}
	});
}

</script>