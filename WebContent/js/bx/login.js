+(function() {
	
	requirejs.config({
		baseUrl : "/js/pub/",
		paths : {
			"md5" : "md5"
		}
	});
	
	require(["md5"], function() {
		
		if($("#hid_err").val() == "login"){
			alert("登录超时，请重新登录！");
		}
		
		jQuery.ajaxSetup({
			error: function(XMLHttpRequest, textStatus, errorThrown){
			},
			success: function(){
			},
			beforeSend: function(XMLHttpRequest,targer){
				if(~targer.url.indexOf("?")){
					targer.url += "&random=" + Math.random();
				}else{
					targer.url += "?random=" + Math.random();
				}
			}
		});
		
		var login = {
			loginSubmit : function() {
				
				var name = $("#txt_loginname").val();
				var pwd = $("#txt_password").val();

				if (name === "" || name === $("#txt_loginname")[0].defaultValue) {
					alert("用户名不能为空！");
					$("#txt_loginname").focus();
					return;
				}
				if (pwd === "") {
					alert("密码不能为空！");
					$("#password_mask").focus();
					return;
				}

				$("#span_login").hide();
				$("#span_logining").show();
				
				$.post("/bx/login", {
					'loginName' : name,
					'password' : pwd
				}, function(rtn) {
					if (rtn === 'success') {
						if($("#chk_next_pass").attr("checked")){
							$.cookie("xbd_admin_login_user_name",name,{expires:7});
							$.cookie("xbd_admin_login_password",pwd,{expires:7});
						}else{
							$.cookie("xbd_admin_login_user_name","");
							$.cookie("xbd_admin_login_password","");
						}
						window.location.href = "/bx/todo";
					} else {
						$("#span_logining").hide();
						$("#span_login").show();
						alert("账号或密码错误！");
					}
				});
			},
			bind : function() {
				$("#txt_loginname").focus(function() {
                    if ($(this).val() == "邮箱/用户名") {
                        $(this).val("");
                        $(this).css("color", "#000");
                    }
                }).blur(function() {
                    if ($(this).val() == "") {
                        $(this).val("邮箱/用户名");
                        $(this).css("color", "#8C8C8C");
                    }
                });
                $("#password_mask").focus(function() {
                    $(this).hide();
                    $("#txt_password").show();
                    $("#txt_password").focus();
                });
                $("#txt_password").blur(function() {
                    if ($(this).val() == "") {
                        $(this).hide();
                        $("#password_mask").show();
                    }
                });
                $("#txt_loginname").on("keypress",function(e){
                	if(e.keyCode === 13){
                		$("#password_mask").focus();
                	}
                });
                $("#txt_password").on("keypress",function(e){
                	if(e.keyCode === 13){
                		login.loginSubmit();
                	}
                });
				$("#btn_submit").on("click", function() {
					login.loginSubmit();
				});
				
				$("#chk_next_pass").on("click",function(){
					if($(this).attr("checked")){
						if(!confirm("若在公共场合上网，请慎用该功能，确认开启？")){
							$(this).attr("checked",false);
						}
					}
				});
				$("#txt_loginname").focus();
				$("#notification").on("click",function(){
					
					 if (!("Notification" in window)) {
                  	    alert("当前浏览器不支持桌面通知，请使用火狐或谷歌浏览器");
                  	  }

                  	  // Let's check if the user is okay to get some notification
                  	  else if (Notification.permission === "granted") {
                  	    // If it's okay let's create a notification
                  	    var notification = new Notification('辛巴达OA系统提示：',{icon:"http://oa.singbada.cn/images/logo_login_3.jpg",body:"桌面通知已启用！"});
//                  	  var notification = new Notification("我的消息",{
//                  	    body : '内容',
//                  	    iconUrl : 'http://oa.singbada.cn/images/logo_login_3.jpg',
//                  	    tag : {} // 可以加一个tag
//                  	  });
                  	  }

                  	  // Otherwise, we need to ask the user for permission
                  	  // Note, Chrome does not implement the permission static property
                  	  // So we have to check for NOT 'denied' instead of 'default'
                  	  else if (Notification.permission !== 'denied') {
                  	    Notification.requestPermission(function (permission) {

                  	      // Whatever the user answers, we make sure we store the information
                  	      if(!('permission' in Notification)) {
                  	        Notification.permission = permission;
                  	      }

                  	      // If the user is okay, let's create a notification
                  	      if (permission === "granted") {
                  	        var notification = new Notification('辛巴达OA系统提示：',{body:"桌面通知已启用！",iconUrl:"http://oa.singbada.cn/images/logo_login_3.jpg"});
                  	      }
                  	    });
                  	  }
					
					/* if(window.webkitNotifications){
						if(window.webkitNotifications.checkPermission() == 0 ){
							var notificationInstance = window.webkitNotifications.createNotification("http://oa.singbada.cn/images/logo_login_3.jpg","辛巴达OA系统提示：",
	            				    "已启用提醒功能");
	            				notificationInstance.onclick = function() {
	            						this.cancel();
	            					};
	            				notificationInstance.show();
						}else{
							window.webkitNotifications.requestPermission();
						}
						
			         }else{
			        	 alert("您的浏览器不支持桌面通知! 请使用chrome浏览器。");
			         }*/
				});
			},
			autoLogin : function(){
				var u = $.cookie("xbd_admin_login_user_name") || "";
				var p = $.cookie("xbd_admin_login_password") || "";
				if(u !== "" && u !== ""){
					
					$("#span_login").hide();
					$("#span_logining").show();
					
					$.post("/bx/login", {
						'loginName' : u,
						'password' : p
					}, function(rtn) {
						if (rtn === 'success') {
							window.location.href = "/bx/index";
						} else {
							$("#span_logining").hide();
							$("#span_login").show();
							alert("账号或密码错误！");
						}
					});
				}
			}
		 };
		 var slide = {
            timer: null,
            curIndex: 0,
            len: 0,
            dire: -1,
            enterFrame: 3,
            init: function() {
                var me = this;
                this.len = $("#ScrollSmall dl").size();
                $("#ScrollSmall dl").each(function(i) {
                    $(this).mouseover(function() {
                        $("#ScrollSmall dl.act").removeClass("act");
                        $("#ScrollList").stop().animate({
                            "left": i * 737 * -1 + 'px'
                        });
                        $(this).addClass("act");
                        this.timer && clearInterval(this.timer);
                    }).mouseout(function() {
                        if (!this.timer) {
                            this.timer = setInterval(function() {
                                me.scroll();
                            }, me.enterFrame * 1000);
                        }
                    });
                });
                this.timer && clearInterval(this.timer);
                this.timer = setInterval(function() {
                    me.scroll();
                }, me.enterFrame * 1000);
            },
            scroll: function() {
                var me = this;
                if (me.curIndex == 0 || me.curIndex == me.len - 1) {
                    me.dire = me.dire * -1;
                }
                me.curIndex += me.dire;
                $("#ScrollSmall dl").eq(me.curIndex).trigger("mouseover");
            }
        };
		login.bind();
		login.autoLogin();
		slide.init();
		
	});
})();