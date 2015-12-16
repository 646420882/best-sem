<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    <title>百思-用户管理中心</title>
    <jsp:include page="WEB-INF/pages/public/navujs.jsp"/>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="WEB-INF/pages/public/header.jsp"/>
    <div class="containers">
        <jsp:include page="WEB-INF/pages/public/nav.jsp"/>
        <div class="middle_containers">
            <div class="page_title">
                当前位置：用户管理
            </div>
            <div class="user_box">
                <div class="admin_title">
                    <label class="control-label fl"> 账户状态：</label>

                    <div class="fl select">
                        <div class="btn-group">
                            <button type="button" class="btn btn-default ">Action</button>
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="false">
                                <span class="caret"></span>
                                <span class="sr-only">所有人员</span>
                            </button>
                            <ul class="dropdown-menu">
                                <li><a href="#">所有人员</a></li>
                                <li><a href="#">管理员</a></li>
                                <li><a href="#">超级管理员</a></li>
                            </ul>
                        </div>

                    </div>
                    <div class="user_content">
                        <button type="button" class="btn btn-primary" id="">修改</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

