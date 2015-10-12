<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2014-9-1
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
  <title></title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">

</head>
<body>
<input name="reservation" readonly style="display: none">


<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.jQuery.js"></script>


<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/pagination/jquery.pagination.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/jquery.ui.datepicker-zh-CN.js"></script>


<script>

  $(function () {
    // 加载日历控件
    $("input[name=reservation]").daterangepicker();
    $("div.ui-daterangepicker").css("display","block");
  });
</script>

</body>


</html>
