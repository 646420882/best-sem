<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/9/26
  Time: 11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ext Demo</title>
    <script src="${pageContext.request.contextPath}/public/ext/js/ext-all.js"></script>
    <script src="${pageContext.request.contextPath}/public/ext/js/AppFramework.js"></script>
    <script>
        Ext.define("SEM.controller.Users",{
            init:function(){
                console.log("草泥马的！");
            }
        });
    </script>
</head>
<body >

</body>
</html>
