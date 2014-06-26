<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2014-6-23
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>百度推广</title>
    <link href="${pageContext.request.contextPath}/public/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<h1>Login</h1>

<div id="login-error">${error}</div>

<form action="../j_spring_security_check" method="post">

    <p>
        <label for="j_username">用户名: </label><input id="j_username"
                                                    name="j_username" type="text"/>
    </p>

    <p>
        <label for="j_password">密码: </label><input id="j_password"
                                                   name="j_password" type="password"/>
    </p>

    <input type="submit" class="btn" value="登录"/>

</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.min.js"></script>
</body>
</html>
