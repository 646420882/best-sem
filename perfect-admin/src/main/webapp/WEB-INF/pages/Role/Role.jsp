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
                <div>
                    <table id="roleAdmin" data-click-to-select="true"    data-query-params="queryParams"  data-pagination="true" >
                        <thead>
                        <tr>
                            <th data-field="state" data-checkbox="true"></th>
                            <th data-field="name">序号</th>
                            <th data-field="name">公司名称</th>
                            <th data-field="remark">用户名</th>
                            <th data-field="wedName">密码</th>
                            <th data-field="wedUrl">注册邮箱</th>
                            <th data-field="wedCode">注册日期</th>
                            <th data-field="wedCode">联系人</th>
                            <th data-field="wedCode">办公电话</th>
                            <th data-field="wedCode">移动电话</th>
                            <th data-field="wedCode">通讯地址</th>
                            <th data-field="wedCode">系统模块</th>
                            <th data-field="action" data-formatter="disableFormatter" data-events="operateEvents">账户状态
                            </th>
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

