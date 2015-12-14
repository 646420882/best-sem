<%--
  Created by IntelliJ IDEA.
  User: guochunyan
  Date: 2015/12/14
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
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
                <div class="user_content password_box">
                    <div class="password_box_title"><b>登录密码修改</b><span>新密码至少6位，包括数字、大小写英文字母</span></div>
                    <form class="form-inline">
                        <div class="form-group col-xs-12 ">
                            <div class="input-group">
                                <div class="input-group-addon"><span aria-hidden="true" ng-class="icon"
                                                                     class="glyphicon glyphicon-lock"></span></div>
                                <input type="text" class="form-control" placeholder="请输入旧密码">
                            </div>
                        </div>
                        <div class="form-group col-xs-12">
                            <div class="input-group">
                                <div class="input-group-addon"><span aria-hidden="true" ng-class="icon"
                                                                     class="glyphicon glyphicon-lock"></span></div>
                                <input type="text" class="form-control" placeholder="请输入新密码">
                            </div>
                        </div>
                        <div class="form-group col-xs-12">
                            <div class="input-group">
                                <div class="input-group-addon"><span aria-hidden="true" ng-class="icon"
                                                                     class="glyphicon glyphicon-lock"></span></div>
                                <input type="text" class="form-control" placeholder="请再次输入新密码">
                            </div>
                        </div>
                    </form>
                    <button type="button" class="btn btn-primary" id="">确认</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

