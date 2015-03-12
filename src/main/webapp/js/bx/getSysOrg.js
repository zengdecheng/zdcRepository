requirejs.config({
	baseUrl : "/js/pub/",
    paths: {
        "u": "util",
        "v": "validateAlert"
    }
});
define(["u", "v"], function(u, v) {
    var __b = {
        tree: null,
        staffId: -1,
        isPwInit: 0,
        admin : null,
        init: function() {
            var me = this;
            this.__loadingTree();
            $("#AddStaffBtn").click(function() {
                me._addInit();
            });
            $("#BtnCancel").click(function() {
                u.d.close('ACCOUNT_FORM');
            });
            
            /**初始化表单验证**/
            v.init({
                buttonId: "BtnSave",
                config: {
                    Rule: {
                    	"loginName": {
                            "min": 1,
                            "minerror": "登录账号不能为空"
                        },
                    	"password": {
                   	 		"max": 50,
                            "min": 6,
                            "maxerror": "密码最大为50位",
                            "minerror": "密码最小为6位",
                        },
                    	"password_1": {
                            "compare": "password",
                            "compareerror": "两次密码输入不一致"
                        },
                    	"LinkPhone": {
                   	 		"max": 11,
                            "min": 1,
                            "reg": "mobile",
                            "maxerror": "手机号填写不正确",
                            "minerror": "手机号不能为空",
                            "error": "手机号填写不正确"
                        }
                    }
                },
                callback: function() {
                    me.__saveStaff();
                }
            })
        },
        /**添加初始化弹层*/
        _addInit: function() {
            this.staffId = -1;
            this.isPwInit = 0;
            var selectNodes = this.tree.getSelectedNodes();

            /**清空表单数据*/
        	$("#dl_Admin,#dl_SetAdmin").hide();
            $("#DL_Password,#DL_Password1,#loginName,#LinkWW,#LinkPhone,#dl_loginName,#dl_LinkWW,#dl_LinkPhone,").show();
            $(":text,:password", $("#form1")).val("");

            if (selectNodes < 1) {
                alert("请选择要添加的组织机构!");
                return;
            }
            u.d.show("ACCOUNT_FORM");
        },
        /**修改初始化弹层*/
        editInit: function(staffId, loginName) {
            this.staffId = staffId;
            this.isPwInit = 0;
            if (staffId != -1) {
            	var selectNodes = this.tree.getSelectedNodes(), node, org;
	            if (selectNodes.length > 0) {
	                node = selectNodes[0];
	            }
	            
                $("#DL_Password,#DL_Password1").hide();
        		$("#loginName,#LinkWW,#LinkPhone,#dl_loginName,#dl_LinkWW,#dl_LinkPhone").show();
                var chidren = $("#TR_" + staffId).children();
                $("#loginName").val(chidren.eq(0).text());
                org = chidren.eq(1).text();
                $("#LinkWW").val(chidren.eq(2).text());
                $("#LinkPhone").val(chidren.eq(3).text());
                $("#OaRole").val(chidren.eq(3).attr("title"));
                //设置假数据，用于通过验证，不会更改
        		$("#password").val("123456");
        		$("#password_1").val("123456");
                
                if (null != node && null != org && org == node.id) {
                	if (null == this.admin || "" == this.admin) {
                		$("#admin").html("此部门暂无管理员");
                	} else {
                		$("#admin").html(this.admin + "为此部门的管理员");
                	}
                	if (this.admin != loginName) {
                		$("#btn_SetAdmin").html("<a style='cursor:pointer;' onclick='outInerb.setAdmin(\"" + loginName + "\")'>设置此用户为该组管理员</a>");
                    	$("#dl_SetAdmin").show();
                	} else {
                		$("#dl_SetAdmin").hide();
                	}
                	
                	$("#dl_Admin").show();
                } else {
                	$("#dl_Admin,#dl_SetAdmin").hide();
                }
                u.d.show("ACCOUNT_FORM");
            }
        },
        /**修改初始化弹层*/
        passwordInit: function(staffId) {
    		this.staffId = staffId;
    		this.isPwInit = 1;
    		$("#loginName,#LinkWW,#LinkPhone,#dl_loginName,#dl_LinkWW,#dl_LinkPhone,#dl_Admin,#dl_SetAdmin").hide();
            $("#DL_Password,#DL_Password1").show();
    		$("#password").val("");
    		$("#password_1").val("");
            //设置假数据，用于通过验证，不会更改
    		$("#loginName").val("loginName");
            $("#LinkWW").val("LinkWW");
            $("#LinkPhone").val("18888888888");
    		u.d.show("ACCOUNT_FORM");
        },
        delData: function(staffId) {
            var me = this,
                selectNodes = this.tree.getSelectedNodes(),
                node;
            if (selectNodes.length > 0) {
                node = selectNodes[0];
            }
            if (confirm("确定删除？")) {
            	var chidren = $("#TR_" + staffId).children();
                $.ajax({
                    url: "/bx/jsonDelStaff?oaStaffId=" + staffId + "&loginName=" + chidren.eq(0).text(),
                    type: "post",
                    success: function(data) {
                		if(data == 'delStaffError'){
                			alert("该用户是管理员，不能删除！");
                			return false;
                		}
                        me.__loadingStraff(null, null, node);
                    }
                });
            }
        },
        setAdmin: function(loginName) {
            var me = this,
            selectNodes = this.tree.getSelectedNodes(),
	            node;
	        if (selectNodes.length > 0) {
	            node = selectNodes[0];
	        }
	        params = $("#form1").serializeArray();
            $.ajax({
                url: "/bx/jsonUpdateAdmin?oaOrgId=" + node.id,
                type: "post",
                data: params,
                success: function(data) {
                    u.d.close('ACCOUNT_FORM');
                    me.__loadingStraff(null, null, node);
                }
            });
	    },
        /**保存数据*/
        __saveStaff: function() {
            var me = this,
                params = $("#form1").serializeArray(),
                node;
            var selectNodes = this.tree.getSelectedNodes();
            if (selectNodes.length > 0) {
                node = selectNodes[0];
            }
            if(this.isPwInit == 1){//密码重置
            	$.ajax({
                    url: "/bx/jsonPwInit?oaStaffId=" + me.staffId,
                    type: "post",
                    data: params,
                    success: function(data) {
                        u.d.close('ACCOUNT_FORM');
                        me.__loadingStraff(null, null, node);
                    }
                });
            }else if (this.staffId != -1) {//修改
                $.ajax({
                    url: "/bx/jsonUpdateStaff?oaStaffId=" + me.staffId,
                    type: "post",
                    data: params,
                    success: function(data) {
                        u.d.close('ACCOUNT_FORM');
                        me.__loadingStraff(null, null, node);
                    }
                });
            } else {
                $.ajax({
                    url: "/bx/jsonCreateStaff?oaOrgId=" + node.id,
                    type: "post",
                    data: params,
                    success: function(data) {
                		if(data == 'loginNameError'){
                			alert("登录名重复，请重新输入！");
                			return false;
                		}
                        u.d.close('ACCOUNT_FORM');
                        me.__loadingStraff(null, null, node);
                    }
                });
            }
        },
        __loadingTree: function() {
            var me = this;
            $.ajax({
                url: "/bx/getJsonSysOrg",
                success: function(data) {
                    //解析json放入控件
                    var nodes = jQuery.parseJSON(data);
                    var _setting = {
                        data: {
                            simpleData: {
                                enable: true,
                                idKey: "id",
                                pIdKey: "pid"
                            }
                        },
                        view: {
                            showIcon: false,
                            showIcon:1,
                            selectedMulti: false,
                            expandSpeed:""
                        },
                        callback: {
                            onClick: me.__loadingStraff
                        }
                    };
                    me.tree = $.fn.zTree.init($("#treeDemo"), _setting, nodes);
                    me.tree.expandAll(true);
                }
            });
            this.__loadingStraff(null, null, null);
        },
        __loadingStraff: function(event, treeId, treeNode) {
            var me = this,
                ajaxUrl = "/bx/getJsonOaStaff";
            if (treeNode) {
                ajaxUrl += "?treeNode=" + treeNode.id;
            }
            $.ajax({
                type: "GET",
                url: ajaxUrl,
                success: function(data) {
                    var obj = jQuery.parseJSON(data);
                    __b.admin = obj[0].admin;  //当前部门管理员赋值
                    __b.__addData(obj[0].data);
                }
            });
        },
        __addData: function(data) {
            var TableHtml = [];
            TableHtml.push(
                "<table cellpadding='0' cellspacing='0' border='0'>",
                    "<tr>",
                        "<th>登录名</th>",
                        "<th>部门</th>",
                        "<th>旺旺号</th>",
                        "<th>手机号</th>",
                        "<th>操作</th>",
                    "</tr>"
                );
            $.each(data, function(i, n) {
                TableHtml.push(
                    u.format([
                        "<tr id='TR_#{id}'>",
                            "<td align='center'>#{loginName}</td>",
                            "<td align='center' >#{oaOrg}</td>",
                            "<td align='center'>#{linkww}</td>",
                            "<td align='center' title='#{oaRole}'>#{linkphone}</td>",
                            "<td align='center'><a href='javascript:void(0)' onclick='outInerb.passwordInit(#{id})' class='lan'>重置密码</a>",
                            "&nbsp;<a href='javascript:void(0)' onclick='outInerb.editInit(#{id}, \"#{loginName}\")' class='lan'>修改</a>",
                            "&nbsp;<a href='javascript:void(0)' class='lan' onclick='outInerb.delData(#{id})'>删除</a></td>",
                        "</tr>"
                        ].join(""),
                        n
                    )
                );
            });
            if (data.length == 0) {
                TableHtml.push(
                     "<tr>",
                        "<td colspan='4' align='center'>暂无数据</td>",
                     "</tr>"
                );
            }
            TableHtml.push("</table>");
            $("#Gird_Body").html(TableHtml.join(""));
        }
    }
    return __b;
});