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
                当前位置：日志管理
            </div>
            <div class="user_box">
                <table id="logAdmin" data-click-to-select="true" data-query-params="queryParams" data-pagination="true">
                    <thead>
                    <tr>
                        <th data-field="state" data-checkbox="true"></th>
                        <th data-field="name">序号</th>
                        <th data-field="name">操作帐号</th>
                        <th data-field="remark">操作人</th>
                        <th data-field="password">IP地址</th>
                        <th data-field="wedUrl">日志描述</th>
                        <th data-field="wedCode">记录时间</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/public/js/log/log.js"></script>
</body>
</html>
