<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${systemEnvironment }员工管理 | Singbada ERP</title>
</head>
<body>
        <div class="right_panel">
            <div class="grid_panel_tool">
                <a href="javascript:void(0)" class="add_btn" id="AddStaffBtn">创建用户</a>
            </div>
            <div class="user_tree">
                <ul id="treeDemo" class="ztree">
                </ul>
            </div>
            <div class="tree_grid_panel">
                <div class="grid_body" id="Gird_Body">
                    <table cellpadding="0" cellspacing="0" border="0">
                    </table>
                </div>
            </div>
        </div>
      <div class="ui-form" id="ACCOUNT_FORM" style="width: 350px">
        <div class="ui-form-title">
            <span>创建用户</span><div class="close" id="ACCOUNT_FORM_CLOSE">
            </div>
        </div>
        <div class="ui-form-content">
            <form id="form1">
            <div class="create_account">
                <dl class="form-list" id="dl_loginName"> 
                    <dd>
                        登录账号：
                    </dd>
                    <dt>
                        <span>辛巴达:</span><input type="text" id="loginName" name="loginName" class="input-txt" style="width: 168px" />
                    </dt>
                </dl>
                <dl class="form-list" id="DL_Password">
                    <dd>
                        登录密码：
                    </dd>
                    <dt>
                        <input type="password" name="password" id="password" class="input-txt" />
                    </dt>
                </dl>
                <dl class="form-list" id="DL_Password1">
                    <dd>
                        确认密码：
                    </dd>
                    <dt>
                        <input type="password" name="password_1" id="password_1" class="input-txt" />
                    </dt>
                </dl>
                <dl class="form-list" id="dl_LinkWW">
                    <dd>
                        旺旺号：
                    </dd>
                    <dt>
                        <input type="text" id="LinkWW" name="linkww" class="input-txt" />
                    </dt>
                </dl>
                <dl class="form-list" id="dl_LinkPhone">
                    <dd>
                        手机号：
                    </dd>
                    <dt>
                        <input type="text" id="LinkPhone" name="linkphone" class="input-txt" />
                    </dt>
                </dl>
          <dl class="form-list" id="dl_LinkPhone">
                    <dd>
                        角色号：
                    </dd>
                    <dt>
                        <input type="text" id="OaRole" name="oaRole" class="input-txt" />
                    </dt>
                </dl>
                <dl class="form-list" id="dl_Admin">
                    <dd>
                        &nbsp;
                    </dd>
                    <dt>
                       <span style="width:210px; color:red;" id="admin">张无忌为此部门的管理员</span> 
                    </dt>
                </dl>
                <dl class="form-list" id="dl_SetAdmin">
                    <dd>
                        &nbsp;
                    </dd>
                    <dt>
                        <span style="width:210px;" id="btn_SetAdmin"><a style="cursor:pointer;">设置此用户为该组管理员</a></span>
                    </dt>
                </dl>
                <dl class="form-list">
                    <dd>
                        &nbsp;
                    </dd>
                    <dt>
                        <a href="javascript:void(0)" id="BtnSave">
                            <img src="/images/btn28.jpg" /></a>
                        <!-- <a href="javascript:void(0)" class="cancel">
                            <img src="/images/btn29.jpg"  id="BtnCancel"/></a> -->
                    </dt>
                </dl>
            </div>
            </form>
        </div>
    </div>
    <script type="text/javascript">
        $(function() {
            require(["getSysOrg"], function(b) {
                b.init();
                window.outInerb = b;
            });
        });
    </script>
</body>
</html>
