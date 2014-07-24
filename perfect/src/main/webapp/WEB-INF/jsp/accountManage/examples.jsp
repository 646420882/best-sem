<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2014/7/24
  Time: 13:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <link type="text/css" href="../public/css/accountCss/style.css" rel="stylesheet">
    <link type="text/css" href="../public/css/accountCss/public.css" rel="stylesheet">
    <title></title>

</head>
<body>
<div class="concent over">
    <jsp:include page="pageBlock/nav.jsp" flush="true"/>
     士大夫士大夫士大夫似的
</div>
</body>
</html>
