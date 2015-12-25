<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    <title>百思-用户管理中心</title>
    <jsp:include page="../public/navujs.jsp"/>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="../public/header.jsp"/>
    <div class="containers">
        <jsp:include page="../public/nav.jsp"/>
        <div class="middle_containers">
            <div class="page_title">
                当前位置：角色管理
            </div>
            <div class="user_box">
                <div class="admin_title">
                    <label class="control-label fl"> 账户状态：</label>

                    <div class="fl select" id="userSelect">
                        <select onchange="role.event.selectUser(this)">
                            <option value="-1">所有人员</option>
                            <option value="0">管理员</option>
                            <option value="1">超级管理员</option>
                        </select>
                    </div>

                    <div class="fl">
                        <%--<input type="text" class="adminInput" placeholder="请输入姓名、登录账号">--%>
                        <span class="adminSearch">
                            <span class="glyphicon glyphicon-search"></span>
                        </span>
                    </div>
                    <!-- /.row -->

                </div>
                <div id="roleTable">
                    <table id="roleAdmin">
                        <thead>
                        <tr>
                            <th data-field="state" data-checkbox="true"></th>
                            <th data-field="id" data-formatter="role.column.noFat">序号</th>
                            <th data-field="name">姓名</th>
                            <th data-field="title">职务</th>
                            <%--<th data-field="roleProperty">角色属性</th>--%>
                            <th data-field="loginName">登录账号</th>
                            <th data-field="superAdmin" data-formatter="role.column.superAdminFat">是否超级管理员</th>
                            <th data-field="password" data-formatter="role.column.passFat" data-events="operateEvents">
                                登录密码
                            </th>
                            <th data-field="ctime" data-formatter="role.date.fat">创建日期</th>
                            <th data-field="contact">联系方式</th>
                            <th data-field="action" data-formatter="role.column.operateFat" data-events="operateEvents">
                                操作
                            </th>
                        </tr>
                        </thead>
                    </table>
                    <div class="adminAddBtn" onclick="role.event.showAddRoleModal()">
                        <a href="javascript:;"><strong>+ &nbsp;&nbsp;新增角色</strong></a>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<jsp:include page="../home/homemodel.jsp"/>
<script type="text/javascript" src="/public/js/role/role.js"></script>
</body>
</html>

