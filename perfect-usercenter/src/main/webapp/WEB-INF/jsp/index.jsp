<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <jsp:include page="public/navujs.jsp"/>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="public/header.jsp"/>
    <div class="containers">
        <jsp:include page="public/nav.jsp"/>
        <div class="middle_containers">
            <div class="page_title">
                当前位置：账户概览
            </div>
            <div class="user_box" style="position: relative">
                <div class="user_title" style="position: relative;text-align: center">
                    <span aria-hidden="true" ng-class="icon"
                          class="glyphicon glyphicon-user"></span>账户：<b>${user.userName}</b>
                    <button type="button" class="btn btn-default">${user.payed ? "付费账户":"试用账户"}</button>
                </div>
                <div class="user_content" style="width: 282px;
    margin: 0 auto;">
                    <ul id="userList">

                    </ul>
                    <button type="button" class="btn btn-primary" onclick="Modify(this)">修改</button>
                    <div class="ModifyHide hide" style="float: right;">
                        <button type="button" class="btn btn-primary" id="save">保存</button>
                        <button type="button" class="btn" style="margin-top:26px;" onclick="Cancel(this)">取消</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="userid" value="${user.id}">
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/public/bootstrapValidator.js"></script>
<script type="text/javascript" src="/public/js/date/date-formatter.js"></script>
<script type="text/javascript" src="/public/js/index/index.js"></script>
</body>
</html>

