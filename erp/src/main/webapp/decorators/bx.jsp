<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="decorator" uri="sitemesh-decorator"%>
<%@ taglib uri="sitemesh-page" prefix="page"%>
<s:set name="ctx" value="%{pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="/css/index3.css" />
<link rel="stylesheet" type="text/css" href="/css/fab_sub.css" />
<link rel="stylesheet" type="text/css" href="/css/index4.css" />
<link rel="stylesheet" type="text/css" href="/css/mindex.css" />
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<link rel="stylesheet" type="text/css" href="/css/zTreeStyle/zTreeStyle.css" />
<link rel="icon" href="/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />  
<script type="text/javascript" src="/js/pub/jquery_1.7.2.js"></script>
<script src="/js/pub/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/pub/jquery.cookie.pack.js"></script>
<script type="text/javascript" src="/js/pub/require_min.js"></script>
<script type="text/javascript">requirejs.config({baseUrl : "${ctx}/js/bx/"});</script>
<title><decorator:title></decorator:title></title>
</head>

<body>
    <!--头部开始-->
    <div class="header_bg">
      <div class="fab_head">
         <a href="#" class="fab_head_left"><img src="/images/fabric3_1.gif" border="0"/></a>
         <div class="fab_head_right">
           <div class="fab_head_login">
             <div class="fab_head_offer">
               <ul>
                     <li class="fab_a_style1">Hi,<s:property value="%{@com.xbd.oa.utils.WebUtil@getCurrentLoginBx().getLoginName()}" />!</li>
                     <li class="fab_a_style2"></li>
                     <li class="fab_a_style3"><a href="#"><em></em></a></li>
                     <li class="fab_a_style4"><a href="${ctx}/bx/logout">退出</a></li>
               </ul>
              </div>
             <div class="fab_head_banner">
               <a href="#">首&nbsp;页</a>
             </div>
           </div>
          </div>
      </div>  
      </div> <!--头部结束-->
<div class="main">
      <div class="fab_contain"><!--内容开始-->
         <div class="fab_contain_left"><!--左栏开始-->
            <div class="fab_contain_top"></div>
              <div class="fab_title">
                <img src="/images/bg_40.gif" alt="" border="0" />
                <a href="/bx/todo" ><span>今日工作</span></a>
            </div>
            <ul class="fab_left_list">
                <li>
                    <div class="fab_title">
                        <img src="/images/bg_28.gif" alt="" border="0" />
                        <span>订单管理</span>
                        <a href="#"><em></em></a>
                    </div>
                    <ul class="fab_menu">
                        <!--<s:if test="@com.xbd.oa.utils.WebUtil@getCurrentLoginBxGroup() == 'mr' " >
                        	<li class="fab_border"><a href="/bx/add_baojia">新增订单</a></li>
                        </s:if> -->
                        <li class="fab_border"><a href="/report!hello">测试测试</a></li>
                        <li class="fab_border"><a href="/bx/order_timeout">时间异动订单</a></li>
                        <!-- <li class="fab_border"><a href="/bx/order_list">订单列表1</a></li> -->
                    	<li class="fab_border"><a href="/bx/orderList">在制订单列表</a></li>
                        <li class="fab_border"><a href="/bx/order_his">历史订单分析报表</a></li>
                         <li class="fab_border"><a href="/bx/orderProgressReport">订单进度报表</a></li>
                        <s:if test="@com.xbd.oa.utils.WebUtil@getCurrentLoginBxGroup() == 'fi' " >
	                        <li class="fab_border"><a href="/jsp/bx/DHTC3.jsp">时间控制表导出</a></li>
                        </s:if>
                    </ul>
                </li>
                <s:if test="@com.xbd.oa.utils.WebUtil@ifAdmin()" >
                <li>
                    <div class="fab_title">
                        <img src="/images/fabric3_46.gif" alt="" border="0" />
                        <span>系统管理</span>
                        <a href="#"><em></em></a>
                    </div>
                    <ul class="fab_menu">
                        <li class="fab_border"><a href="/bx/timebaseConfig">设置基准时间</a></li>
                        <li class="fab_border"><a href="/category/list">一级品类管理</a></li>
                        <li class="fab_border"><a href="/bx/clothClass_list">二级品类管理</a></li>
                        <li class="fab_border"><a href="/bx/getSysOrg">员工管理</a></li>
                        <li class="fab_border"><a href="/jsp/bx/DHTC3.jsp">时间控制表导出</a></li>
                    </ul>
                </li>
                <li>
					<div class="fab_title">
						<img src="/images/bg_28.gif" alt="" border="0" /> <span>数据统计</span>
						<a href="#"><em></em></a>
					</div>
					<ul class="fab_menu">
						<li class="fab_border"><a href="/bx/timelyRateCount">及时率统计</a></li>
					</ul>
				</li>
                </s:if>
            </ul>
            
            <div class="fab_title">
                <img src="/images/bg_40.gif" alt="" border="0" />
                <a href="/bx/modify_pwd" ><span>修改密码</span></a>
            </div>
            <script type="text/javascript">
            $(function () {
				//菜单点击效果
				$(".fab_title").off().on("click", function() {
					if($(this).next().css("display") == 'none'){
						$(this).next().show();
					}else{
						if($(this).hasClass("menu_act")){
							$(this).next().hide();
						}
					}
					$(".fab_title").removeClass("menu_act");
					$(this).addClass("menu_act");
					
					var action = $("a",this).attr("href");
					
					$.cookie("xbd_mr_menu_navigate_parent",action, { path: '/' });
					$.cookie("xbd_mr_menu_navigate_child",null, { path: '/' });	
				});
				$(".fab_border").off().on("click", function(){
					
					var action = $("a",this).attr("href");
					
					$.cookie("xbd_mr_menu_navigate_parent",null, { path: '/' });
					$.cookie("xbd_mr_menu_navigate_child",action, { path: '/' });	
				});
				
				

				//当前地址再次动态判断被选中的菜单。
				//alert(window.location.href);
				//var curId;
				var curUrl = window.location.href;
				var hasInit = false;
				$("a",".fab_title,.fab_menu").each(function(i,n){
					if(curUrl.indexOf($(n).attr("href")) > -1){
						var curId = $(n).parent().attr("id");
						//var parObj = $(n).parent();
						/*if(curId.indexOf("menu_child_") > -1){
							$(n).addClass("item_act");
							$(n).parent().parent().prev().addClass("menu_act");
						}else{
							$(n).parent().addClass("menu_act");
						}*/
						if($(n).parent().hasClass("fab_title")){
							$(n).parent().addClass("menu_act");
						}else{
							$(n).addClass("item_act");
							$(n).parent().parent().prev().addClass("menu_act");
						}
						
						
						hasInit = true;
					}
				});
				
				//如果没有选中在从cookie里面初始化菜单样式
				if(hasInit){
					return;
				}
				$(".fab_title").removeClass("menu_act");
				var parentAction = $.cookie("xbd_mr_menu_navigate_parent");
				$("[href='"+parentAction+"']").parent().addClass("menu_act");
				//$("#" + parentId).addClass("menu_act");
				
				$(".fab_border").find("a").removeClass("item_act");
				var childAction = $.cookie("xbd_mr_menu_navigate_child");
				var obj = $("[href='"+childAction+"']");
				obj.addClass("item_act");
				obj.parent().prev().addClass("menu_act");  
			});
            setInterval('notification()',5*60*1000); //定时刷新页面 5分钟一次
            function notification(){
            	$.get("/bx/notification",function(data){
            		if( data == "0"){
            			
            		}else {
            			 if (!("Notification" in window)) {
            				//进入系统后不再提示
            				//alert("当前浏览器不支持桌面通知，请使用火狐或谷歌浏览器");
                     	  }
                     	  else if (Notification.permission === "granted") {
                     	    var notification = new Notification("辛巴达OA系统提示：",{body:"您还有" + data + "条任务未处理，请及时处理！",icon:"http://oa.singbada.cn/images/misson_2.jpg"});
                     	  }

                     	  else if (Notification.permission !== 'denied') {
                     	    Notification.requestPermission(function (permission) {

                     	      if(!('permission' in Notification)) {
                     	        Notification.permission = permission;
                     	      }

                     	      if (permission === "granted") {
                     	    	 var notification = new Notification("辛巴达OA系统提示：",{body:"您还有" + data + "条任务未处理，请及时处理！",icon:"http://oa.singbada.cn/images/misson_2.jpg"});
                     	      }
                     	    });
                     	  }

            			
            		/* 	if (window.webkitNotifications.checkPermission() == 0) {
            				  // you can pass any url as a parameter
            				var notificationInstance = window.webkitNotifications.createNotification("http://oa.singbada.cn/images/misson_2.jpg","辛巴达OA系统提示：",
            				    "您还有" + data + "条任务未处理，请及时处理！");
            				notificationInstance.onclick = function() {
            						this.cancel();
            					};
            				notificationInstance.show();
            			} else {
            				window.webkitNotifications.requestPermission();
            			} */
            		}
            	});
            }
			</script>
         </div><!--左栏结束-->
           <!--右栏开始-->
   <div class="z_goods_right">
     	 <decorator:body />
	</div>
           <!--右栏结束-->
      </div><!--内容结束-->
</div>
<div class="cl"></div>
     <!--底部开始-->
     <div class="foot_bg">
      <div class="fab_foot">
          <span class="fab_foot1">© Copyright 2013 Singbada.cn All rights Reserved</span>
          <span class="fab_foot2"><a href="#">关于我们</a>&nbsp;|&nbsp;<a href="#">隐私条款</a></span>
      </div>
     </div><!--底部结束-->
 
</body>
</html>

