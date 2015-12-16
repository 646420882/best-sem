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
                当前位置：密码管理
            </div>
            <div class="user_box">
                <div class="user_title">
                    <span aria-hidden="true" ng-class="icon"
                          class="glyphicon glyphicon-user"></span>账户：<b>perfect2015</b>
                    <button type="button" class="btn btn-default">试用账户</button>
                </div>
                <div class="user_content">
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/public/js/jurisdiction/jurisdiction.js"></script>
</body>
</html>

