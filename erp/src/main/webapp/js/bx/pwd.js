define([ "/js/bx/util.js" ], function(u) {
	var fn = {
		init : function() {
			biz.bind();
		}
	};
	var biz = {
		bind : function() {
			$("#submit_btn").on("click", biz.event.savepwd);
		},
		event : {
			savepwd : function() {
				var oldPwd = $("#oldPwd").val();
				var newPwd = $("#newPwd").val();
				var newPwdAff = $("#newPwdAff").val();
				if (oldPwd == "") {
					$("#info").empty();
					$("#info").append("<span class='erro_msg'>请输入旧密码</span>");
					return;
				}
				if ("" == newPwd || "" == newPwdAff) {
					$("#info").empty();
					$("#info").append("<span class='erro_msg'>请输入新密码</span>");
					return;
				}
				if (newPwd != newPwdAff) {
					$("#info").empty();
					$("#info")
							.append("<span class='erro_msg'>两次密码输入不一致</span>");
					return;
				}
				
				var ajaxUrl = "/bx/modifypwd?oldPwd=" + oldPwd + "&newPwd=" + newPwd;
				$.ajax({
					type : "post",
					url : ajaxUrl,
					success : function(data) {
						if (data == "ok") {
							alert("密码修改成功");
							$("#oldPwd").val("");
							$("#newPwd").val("");
							$("#newPwdAff").val("");
							$("#info").empty();
						} else {
							alert("旧密码输入错误");
							$("#info").empty();
							$("#info").append("<span class='erro_msg'>旧密码输入错误</span>");
						}
					}
				});
			}
		}
	}
	return fn;
});