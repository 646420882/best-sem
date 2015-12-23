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

                    <div class="fl select">
                        <div class="dropdown">
                            <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                所有人员
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                                <li><a href="#">所有人员</a></li>
                                <li><a href="#">管理员</a></li>
                                <li><a href="#">超级管理员</a></li>
                            </ul>
                        </div>
                    </div>

                    <div class="fl">
                        <input type="text" class="adminInput" placeholder="请输入姓名、登录账号">
                        <span class="adminSearch">
                            <span class="glyphicon glyphicon-search"></span>
                        </span>
                    </div>
                    <!-- /.row -->

                </div>
                <div>
                    <table id="roleAdmin" data-click-to-select="true" data-query-params="queryParams"  data-pagination="true" >
                        <thead>
                        <tr>
                            <th data-field="state" data-checkbox="true"></th>
                            <th data-field="roleName">姓名</th>
                            <th data-field="rolePosition">职务</th>
                            <th data-field="roleProperty">角色属性</th>
                            <th data-field="roleAccount">登录账号</th>
                            <th data-field="rolePassword" data-formatter="passwordFormatter" data-events="operateEvents">登录密码</th>
                            <th data-field="roleCreateTime">创建日期</th>
                            <th data-field="roleContactWay">联系方式</th>
                            <th data-field="action" data-formatter="disableFormatter" data-events="operateEvents">操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../home/homemodel.jsp"/>
<script type="text/javascript" src="/public/js/role/role.js"></script>
</body>
</html>

