<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2014-6-27
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <title>Twurn | The page you were looking for doesn't exist (404)</title>
    <style type="text/css">
        body {
            background-color: #efefef;
            color: #333;
            font-family: Georgia, Palatino, 'Book Antiqua', serif;
            padding: 0;
            margin: 0;
            text-align: center;
        }

        p {
            font-style: italic;
        }

        div.dialog {
            width: 490px;
            margin: 4em auto 0 auto;
        }

        img {
            border: none;
        }
    </style>
</head>

<body>
<!-- This file lives in public/404.html -->
<div class="dialog">
    <a href="javascript:history.go(-1);"><img src="${pageContext.request.contextPath}/public/css/404.png"/></a>

    <p>It looks like that page you were looking has been mislaid, sorry.</p>
</div>
</body>
</html>
