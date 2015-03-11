/**
 * 四个新增订单页面的元素内容检查及表单提交 by:lihouxuan
 */
requirejs.config({
	baseUrl : "/js/pub/",
	paths : {
		"u" : "util",
		"v" : "validateNameAlert"
	}
});
var sales=new Array(),
	orderTypeList=new Array(),
	orderTypeList1,
	customerCodeJsons, //客户编号列表 add by ZHUQUAN 2014-11-24
	quality=0;
define(["u","v"],function(u,v) {
			var fn = {
				init : function() {
					validata.init();
					biz.bind();
					//调取业务担当并写入全局数组sales中
					var url = "/bx/getSales";
					$.post(url, function(data) {
						if (data != "") {
							//var result = new Array();
							var obj = jQuery.parseJSON(data);
							$.each(obj, function(i, j) {
								sales.push(j.login_name);
							});
						} else {
							alert("调取业务担当失败");
						}
					});
					$.post("/bx/getOrderTypeList", function(data) {
						if (data != "") {
							var obj = jQuery.parseJSON(data);
							orderTypeList1=obj;
							$.each(obj, function(i, j) {
								orderTypeList.push(j.value);
							});
						} else {
							alert("调取订单品类失败");
						}
					});
					$("#tel").val("此手机号用于订单进度的跟踪");
					$("#tel").css("color","rgb(186, 182, 183)");
					$("#orderTel").val("");
				}
			};
			var biz = {
				bind : function() {
					//**********<<BEGIN初始化客户编号列表JSON add by ZHUQUAN 2014-11-24
					$("#cusCode").css("float","left");
					$("#cusCodeHid").css("float","left");
					$("#btnHid").attr("title","数据加载失败或找不到客户编号时可点击此按钮！").before("&nbsp;").after("<span class='ajaxMsg' style='color:#008B45;padding-left:5px;'></span>");
					biz.event.getCustomerJson("");//页面初始化得到客户编号JSON
					//手动同步客户编号用
					$("#btnHid").on("click",function(){
						biz.event.getCustomerJson("nameAndOrder");
					});
					$("#btnHid").show();
					//切换显示客户编号文本框和模糊匹配框
					$("#cusCode").on("click",biz.event.getCustomerCode);
					$("#cusCodeHid").focus(function(){
						$(this).hide();
						$("#cusCode").show();
						$("#cusCode").click();
					});
					
					$("#cusCode").blur(function(){
						$(this).hide();
						$("#cusCodeHid").show();
					});
					//**********END>>初始化客户编号列表JSON add by ZHUQUAN 2014-11-24
					
					// 绑定事件
					if ($("input[value='1']").attr("checked") == "checked") {
						$("#bsubmit").off().on("click", function () {
							$("#whichButton").val("1");
							v.validateForm();
						});
						$("#submitC").off().on("click",function(){
							$("#whichButton").val("0");
							v.validateForm();
						});
						$("#cancle").on("click",function(){
							location.href="/bx/order_list";
						});

						$("#sales").on("click", biz.event.getSalesList);
					} else if ($("input[value='2']").attr("checked") == "checked") {
						$("#bsubmit").off().on("click", function () {
							$("#whichButton").val("1");
							v.validateForm();
						});
						$("#submitC").off().on("click", function () {
							$("#whichButton").val("0");
							v.validateForm();
						});

						$("#assOrder").on("click", biz.event.getAssOrder);
						$("#sales").on("click", biz.event.getSalesList);
						$("#cancle").on("click",function(){
							location.href="/bx/order_list";
						});

					} else if ($("input[value='3']").attr("checked") == "checked") {

						$("#bsubmit").off().on("click", function () {
							v.validateForm();
						});

						$("#assOrder").on("click", biz.event.getAssOrder);
						$("#sales").on("click", biz.event.getSalesList);
						$("#cancle").on("click",function(){
							location.href="/bx/order_list";
						});
					} else if ($("input[value='4']").attr("checked") == "checked") {
						$("#bsubmit").off().on("click", function () {
							v.validateForm();
						});
						$("#assOrder").on("click", biz.event.getAssOrder);// 测试jquery-autocomplete
						$("#sales").on("click", biz.event.getSalesList);
						$("#cancle").on("click",function(){
							location.href="/bx/order_list";
						});
					}

					if ($("#cancle")) {
						$("#cancle").on("click", biz.event.test);
					}
					$("#asso_list").delegate("td", "click",
							biz.event.passOrderCode);
					$("#sales_list").delegate("td", "click",
							biz.event.passSalesCode);

					$("#style_pic").on("click",biz.event.uploadStylePic);

					$("#tel").on("focus", biz.event.tel);
					$("#tel").on("blur", biz.event.tels);
					$("#cloth1").on("click", biz.event.getOrderTypeList);
					$("#cloth1").on("focus", biz.event.cloth1focus);
					$("#cloth1").on("blur", biz.event.cloth1blur);
					$("#add_style_code").on("click", biz.event.addCode);
				},
				event : {
					getCustomerJson:function(txt){
						//if(""==txt)return;
						$("#cusCode").attr("disabled",true);
						$(".ajaxMsg").empty();
						$(".ajaxMsg").css("color","#008B45"); //字体绿色
						$(".ajaxMsg").text("正在加载客户编号列表,请耐心等候...");
						$.ajax({
							type:"POST",
							url:"http://14.23.89.228:9000/getCustomerJson",
							data:{nameOrCode:txt},
							//async:false,
							success:function(data){
								customerCodeJsons = data;
								//$("#btnHid").hide();
								$(".ajaxMsg").text("数据加载成功!");
								$("#cusCode").attr("disabled",false);
							},
							//add by ZHUQUAN 2014-11-24
							complete:function(XMLHttpRequest, textStatus){
								if(textStatus == "error"){
									$(".ajaxMsg").css("color","#EE0000"); //字体红色
									$(".ajaxMsg").text("客户编号数据请求失败!请手动获取！");
								}
							},
							dataType:"json"
						});
					},
					getCustomerCode:function(){
						if(undefined==customerCodeJsons||null==customerCodeJsons){
							biz.event.getCustomerJson("");
						}
						
						$("#cusCode").autocomplete(customerCodeJsons, {
							max : 100, //列表里的条目数
							minChars : 0, //自动完成激活之前填入的最小字符
							width : 400, //提示的宽度，溢出隐藏
							scrollHeight : 500, //提示的高度，溢出显示滚动条
							matchContains : true, //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
							autoFill : false, //自动填充
							formatItem : function (row, i, max) {
								return '[' + row.code + ']-'+row.name;
							},
							formatMatch : function (row, i, max) {
								return row.name + row.code;
							},
							formatResult : function (row) {
								return row.code;
							}
						}).result(function (event, row, formatted) {
							$("#cusCodeHid").val(row.code);
						});
						$("#cusCode").focus();
					},
					addCode:function(){
						quality++;
					},
					cloth1focus:function(){
						$("#cloth1").css("border","1px solid #EFA100");
					},
					cloth1blur:function(){
						$("#cloth1").css("border","1px solid #bebebe");
					},
					tel:function(){
						if($("#tel").val()=="此手机号用于订单进度的跟踪"){
							$("#tel").val("");
						}
						$("#tel").css({"color":"rgb(51, 51, 51)","border":"1px solid #EFA100"});
					},
					tels:function(){
						$("#tel").css("border","1px solid #bebebe");
						if($("#tel").val()==""){
							$("#tel").val("此手机号用于订单进度的跟踪");
							$("#tel").css("color","rgb(186, 182, 183)");
						}
					},
					checkDate : function() {

						var exceptFinish = $("#exceptFinish").val();
						if (exceptFinish !== "") {
							var wd = new Date(exceptFinish);
							wd.setDate(wd.getDate() + 1);
							if (wd < new Date()) {
								$("#exceptFinish").val("");
								alert("预计交期错误，不能小于当天");
							}
						}

					},
					checkBaojia : function() {
						if (biz.event.checkEmpty()) {
							$("#whichButton").val("1");
							return true;
						} else {
							// alert("1");
							return false;
						}
					},
					checkBaojiaC : function() {
						if (biz.event.checkEmpty()) {
							$("#whichButton").val("0");
							return true;
						} else {
							return false;
						}
					},
					checkDaban : function() {
						if (biz.event.checkEmpty()) {
							$("#whichButton").val("1");
							return true;
						} else {
							// alert("1");
							return false;
						}
					},
					checkDabanC : function() {
						if (biz.event.checkEmpty()) {
							$("#whichButton").val("0");
							$("#form").submit();
							return true;
						} else {
							return false;
						}
					},
					checkDahuo : function() {
						if (biz.event.checkEmpty()) {
							return true;
						} else {
							// alert("1");
							return false;
						}
					},

					checkShouhou : function() {
						if (biz.event.checkEmpty()) {
							return true;
						} else {
							// alert("1");
							return false;
						}
					},
					checkEmpty : function() {
						var result = true;
						var which = new Array();
						var alertt;
						$("input[type='text']")
								.each(
										function(i, v) {
											if (v.value == "") {
												if (v.name == "payOther") {
													if ($("#pay_other")) {
														if ($("#pay_other")
																.attr("checked") == "checked") {
															result = false;
															which.push(v.name);
														} else {
															return true;
														}
													}
												} else {
													result = false;
													which.push(v.name);
												}

											}
										});
						$.each(which, function(i, v) {
							if (v == "oaOrder.sales") {
								which[i] = "业务担当";
							}
							if (v == "oaOrder.cusCode") {
								which[i] = "客户编号";
							}
							if (v == "oaOrder.orderCode") {
								which[i] = "订单编号";
							}
							if (v == "oaOrder.priceMin") {
								which[i] = "期望价格（最低）";
							}
							if (v == "oaOrder.priceMax") {
								which[i] = "期望价格(最高)";
							}
							if (v == "oaOrder.wantCnt") {
								which[i] = "预计数量";
							}
							if (v == "oaOrder.exceptFinish") {
								which[i] = "预计交期";
							}
							if (v == "styleCode") {
								which[i] = "订单款号";
							}
							if (v == "oaOrder.oaOrder") {
								which[i] = "关联订单"
							}
							if (v == "payOther") {
								which[i] = "付款类型"
							}
						});
						$.each(which, function(i, v) {
							if (alertt == null) {
								alertt = v;
							} else {
								alertt += "," + v;
							}
						});
						var result_price = biz.event.checkPrice();
						if (result == false) {
							alert("输入有误，" + alertt + "不能为空");
						}
						if (result == false && result_price == true) {
							result = false;
						} else {
							result = result_price;
						}
						return result;
					},
					checkPrice : function() {
						var result = true;
						var objPriceMin = parseInt($(
								"input[name='oaOrder.priceMin']").val());
						var objPriceMax = parseInt($(
								"input[name='oaOrder.priceMax']").val());
						if (objPriceMin != '' && objPriceMax != '') {
							if (objPriceMin > objPriceMax) {
								result = false;
								alert("期望价格中，最低价格不能高于最高价格，请检查。");
							}
						}
						return result;
					},
					priceMinError : function() {
						var objPriceMin = parseInt($(
								"input[name='oaOrder.priceMin']").val());
						var objPriceMax = parseInt($(
								"input[name='oaOrder.priceMax']").val());
						if (objPriceMax != '') {
							if (objPriceMin > objPriceMax) {
								$("#priceError").text("错误，请检查！");
								$("#priceError").show();
							} else {
								$("#priceError").hide();
							}
						}
					},
					priceMaxError : function() {
						var objPriceMin = parseInt($(
								"input[name='oaOrder.priceMin']").val());
						var objPriceMax = parseInt($(
								"input[name='oaOrder.priceMax']").val());
						if (objPriceMin != '') {
							if (objPriceMin > objPriceMax) {
								$("#priceError").text("错误，请检查！");
								$("#priceError").show();
							} else {
								$("#priceError").hide();
							}
						}
					},
					getAssOrder : function() {
						var url = "/bx/getAssociationOrder";
						$.post(url, function(data) {
							if (data != "") {
								var result = new Array();
								var obj = jQuery.parseJSON(data);
								$.each(obj, function(i, j) {
									result.push(j.order_code);
								});
								$("#assOrder").autocomplete(result, {
									max : 50, // 列表里的条目数
									minChars : 0, // 自动完成激活之前填入的最小字符
									scrollHeight : 150, // 提示的高度，溢出显示滚动条
									matchContains : true, // 包含匹配，就是data参数里的数据，是否只要包文本框里的数据就显示
									autoFill : false, // 自动填充
									
								});
							} else {
								alert("调取订单编号失败");
							}
						});
					},
					getSalesList : function() {
						//$("#sales").val("").css("colors","#000000");
							
							$("#sales").autocomplete(sales, {
								max : 50, // 列表里的条目数
								minChars : 0, // 自动完成激活之前填入的最小字符
								scrollHeight : 150, // 提示的高度，溢出显示滚动条
								matchContains : true, // 包含匹配，就是data参数里的数据，是否只要包文本框里的数据就显示
								autoFill : false, // 自动填充
								
							});
					},
					getOrderTypeList : function() {
						$("#cloth1").autocomplete(orderTypeList, {
							max : 100, // 列表里的条目数
							minChars : 0, // 自动完成激活之前填入的最小字符
							scrollHeight : 150, // 提示的高度，溢出显示滚动条
							matchContains : true, // 包含匹配，就是data参数里的数据，是否只要包文本框里的数据就显示
							autoFill : false, // 自动填充
							
						});
					},
					uploadStylePic:function(){
//						var file = $("#file1"); 
//						file1.after(file1.clone().val("")); 
//						file.remove(); 
						$("#file1").click();
					},
//					uploadImg:function(){
//						var ajaxUrl = "/oaOrderImgUpload?sysLoginId=1&size=1024000&ext=*.jpg;*.png;*.gif";
//						$.ajaxFileUpload({
//							url : ajaxUrl,//用于文件上传的服务器端请求地址
//							secureuri : false,//一般设置为false
//							fileElementId : 'file1',//文件上传空间的id属性  
//							dataType : 'text',//返回值类型 一般设置为json
//							success : function(data, status) //服务器成功响应处理函数
//							{
//								data = data.replace("<pre>","");
//								data = data.replace("</pre>","");
//								var json = $.parseJSON(data);
//								alert(json.url);
//							},
//							error : function(data, status, e)//服务器响应失败处理函数
//							{
//								alert(e);
//							}
//						});
//					}
				}
			};
			validata = {
					init : function () {
						v.init({
							formId : "form1",
							config : validata.validataConfig,
							callback : function () {
									var exceptFinish = $("#exceptFinish").val();
									var objPriceMin = parseInt($("input[name='oaOrder.priceMin']").val());
									var objPriceMax = parseInt($("input[name='oaOrder.priceMax']").val());
									var salesName=$("#sales").val();
									if (exceptFinish !== "") {
										var wd = new Date(exceptFinish);
										//wd.setDate(wd.getDate() + 1);
										if (wd <=new Date()) {
											$("#exceptFinish").val("");
											alert("预计交期错误，不能晚于今日");
											return false;
										}
									}else{alert("请选择预计交期");return false;}
									if(objPriceMin>objPriceMax){alert("期望价格错误，最低价格不能高于最高价格");return false;}
									var salescontain=false;
									$.each(sales,function(k,v){
										if(v==salesName){salescontain=true;}
									});
									if(salescontain==false){alert("业务担当不存在，请修改");return false;}
									if($("#wtsm").length>0){$("#memo").val($("#memo").val()+"问题说明："+$("#wtsm").val()+" || ");};
									if($("#jyfa").length>0){$("#memo").val($("#memo").val()+"建议方案："+$("#jyfa").val()+" || ");};
									if($("#bzsm").length>0){$("#memo").val($("#memo").val()+"备注说明："+$("#bzsm").val()+" || ");};
									if($("#httk").length>0){$("#memo").val($("#memo").val()+"合同条款特殊说明："+$("#httk").val()+" || ");};
									var mobile = /^13[0-9]{1}[0-9]{8}|^15[0-9]{1}[0-9]{8}|^18[0-9]{1}[0-9]{8}$/g;
									if($("#tel").val()!="此手机号用于订单进度的跟踪"){
										if($("#tel").val().length==11&&mobile.test($("#tel").val())){
											if($("#tel").val()!="此手机号用于订单进度的跟踪"&&$("#tel").val()!=""){
												$("#orderTel").val($("#tel").val());
											}
										}else{
											alert("手机号码格式不正确！");
											$("#tel").focus();
											return false;
										}
									}
									if($("#cloth1").val()!=""){
										var n=0;
										if (orderTypeList1 != "") {
											$.each(orderTypeList1, function(i, j) {
												if($("#cloth1").val()==j.value){
													$("#cloth2").val(j.code);
													n++;
													return false;
												}
											});
										}
										if(n==0) {
											alert("输入的订单品类不存在，请从列表中选择！");
											$("#cloth1").focus();
											return false;
										}
									}else{
										alert("订单品类不能为空！");
										$("#cloth1").focus();
										return false;
									}
									var codeStr = /^\w+$/g;
									var temp,j;
									for(j=1;j<=quality;j++){
										temp=$("#style_code_"+j).val();
										if(temp!=""&&temp!=null){
											if(!temp.match(codeStr)){
												alert('新增订单款号 "'+temp+'" 不正确，请检查');
												return false;
											}
										}
									}
									
									$("#form1").submit();
							}
						});
						var elsInputs = [ "#assOrder","#sales","#orderCode","#style_code_0","#minPr","#maxPr","#wantCnt"];
						u.vInput($(elsInputs.join(",")), '#333', '#BAB6B7');
					},
					validataConfig : {
						Rule : {
							"oaOrder.oaOrder":{
								"min":0,
								"minerror":"请输入关联订单",
								"reg":"assCode",
								"error":"关联订单不正确，请检查"
							},
							"oaOrder.cusCode":{
								"min":1,
								"minerror":"请从列表中选择 客户编号",
								"def":"支持英文数字输入",
							},
							"oaOrder.sales" : {
								"min":1,
								"minerror" : "请输入 业务担当",
								"def":"支持中文输入",
								"reg":"",
								"error":"业务担当不正确，请检查"
							},
//							"oaOrder.orderCode" : {
//								"min":1,
//								"minerror" : "请输入 订单编号",
//								"def":"支持数字输入",
//								"reg":"intege1",
//								"error":"订单编号格式不正确，请检查"
//							},
							"styleCode":{
								"min":1,
								"def":"支持英文和数字输入",
								"minerror" : "请输入 订单款号",
								"reg":"styleCode",
								"error":"订单款号不正确，请检查"
							},
							"oaOrder.priceMin":{
								"min":1,
								"def":"支持数字输入",
								"minerror" : "请输入  最低期望价格",
								"reg":"intege1",
								"error":"期望价格不正确，请检查"
							},
							"oaOrder.priceMax":{
								"min":1,
								"def":"支持数字输入",
								"minerror" : "请输入  最高期望价格",
								"reg":"intege1",
								"error":"期望价格不正确，请检查"
							},
							"oaOrder.wantCnt":{
								"min":1,
								"def":"支持数字输入",
								"minerror" : "请输入 订单 数量",
								"reg":"intege1",
								"error":"订单数量不正确，请检查"
							},
//							"oaOrder.exceptFinish":{
//								"min":1,
//								"minerror" : "请输入 预计交期",
//							}
						}
					}
				};
			
			return fn;
		});