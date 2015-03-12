<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:set name="ctx" value="%{pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/common.css" />
    <link rel="stylesheet" type="text/css" href="css/login.css" />
	<script type="text/javascript" src="${ctx}/js/pub/jquery-1.9.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/pub/jquery.cookie.pack.js"></script>
	<script type="text/javascript" data-main="${ctx}/js/bx/login" src="${ctx}/js/pub/require_min.js"></script>
	<title>辛巴达员工登录 | Singbada ERP</title>
</head>
<body>
	<div class="header-bg">
        <div id="Header">
            <div class="tip">
            </div>
            <!-- <ul class="link_msg">
                <a href="#">注册</a>&nbsp;|&nbsp;
                <a href="#">给我提建议</a>
            </ul> -->
        </div>
    </div>
    <div id="Main">
        <div class="login_box">
            <div class="logo">
                <a href="#">
                    <img src="images/logo_login.jpg" /></a>
            </div>
            <ul>
                <li>
                    <input type="text" id="txt_loginname" class="input_txt user" value="邮箱/用户名" />
                </li>
                <li>
                    <input type="text" id="password_mask" class="input_txt password" value="请输入密码" />
                    <input type="password" id="txt_password" class="input_txt password" value="" style="display:none;color:#000" />
                </li>
                <li>
                    <span>
                        <input id="chk_next_pass" type="checkbox" class="rd" /><label>下次自动登录</label></span>
                        <a href="javascript:void(0)" id="notification" class="get_pwd">启用提醒功能</a>
                  		<!--  <a href="#" class="get_pwd">忘记密码？</a> --> 
                </li>
                <li>
                	<span id="span_login">
                    	<a href="javascript:void(0)" id="btn_submit"><img src="images/login_btn.jpg" /></a>
                    </span>
                    <span id="span_logining" style="display:none;">
                    	登录中...
                    </span>
                </li>
                <li>
                   		 <!-- <a href="#" id="notification">启用提醒功能</a> -->
                </li>
                <li class="split"></li>
               <!--  <li>入驻工厂&gt;&gt;<br />
                    服务电话：400-777-356 </li> -->
            </ul>
        </div>
        <div class="right">
            <div class="scroll_container">
                <ul class="scroll_list" id="ScrollList">
                    <li>
                        <a href="http://www.singbada.cn/">
                            <img src="images/login_pic_1.jpg" /></a>
                    </li>
                    <li>
                        <a href="http://www.singbada.cn/">
                            <img src="images/login_pic_2.jpg" /></a>
                    </li>
                    <li>
                        <a href="http://www.singbada.cn/">
                            <img src="images/login_pic_3.jpg" /></a>
                    </li>
                    <li>
                        <a href="http://www.singbada.cn/">
                            <img src="images/login_pic_4.jpg" /></a>
                    </li>
                </ul>
            </div>
            <div class="scroll_small" id="ScrollSmall">
                <dl class="act">
                    <dd>
                        <div class="mask">
                        </div>
                        <img src="images/login_small_p1.jpg" /></dd>
                    <dt>
                        10000人工厂，ISO9000认证
                    </dt>
                </dl>
                <dl>
                    <dd>
                        <div class="mask">
                        </div>
                        <img src="images/login_small_p2.jpg" /></dd>
                    <dt>
                        20件小批量，订单柔性生产
                    </dt>
                </dl>
                <dl>
                    <dd>
                        <div class="mask">
                        </div>
                        <img src="images/login_small_p3.jpg" /></dd>
                    <dt>
                        3天快速补单追货
                    </dt>
                </dl>
                <dl style="margin: 0px">
                    <dd>
                        <div class="mask">
                        </div>
                        <img src="images/login_small_p4.jpg" /></dd>
                    <dt>
                        供应链融资服务
                    </dt>
                </dl>
            </div>
        </div>
    </div>
    <div id="Footer" style="margin:-70px 0 0 0">
        <div class="footer">
            <div class="l">
                 &copy;&nbsp;Copyright 2013 singbada.cn All rights	Reserved
            </div>
            <div class="r">
                <a href="#">关于我们</a>&nbsp;|&nbsp;
                <a href="#">隐私合作</a>
            </div>
        </div>
    </div>
	<input id="hid_err" type="hidden" value="<%=request.getParameter("err")%>"/>
</body>
</html>

