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
            <div class="user_box">
                <div class="user_title">
                    <span aria-hidden="true" ng-class="icon"
                          class="glyphicon glyphicon-user"></span>账户：<b>${user.userName}</b>
                    <button type="button" class="btn btn-default">试用账户</button>
                </div>
                <div class="user_content">
                    <ul>
                        <li><span>公司名称：</span>${user.companyName}</li>
                        <li><span>开通平台：</span>${user.companyName}北百思搜客、百思慧眼</li>
                        <li><span>网站名称：</span>北京普菲特广告有限公司</li>
                        <li><span>网址：</span>http：//www.perfect-cn.cn</li>
                        <li><span>注册时间：</span>2015-10-10</li>
                        <li><span>联系人：</span>北京某某</li>
                        <li><span>办公电话：</span>12315464</li>
                        <li><span>移动电话：</span>1555555552</li>
                        <li><span>通讯地址：</span>XXX省xxx市XXXXXX</li>
                        <li><span>电子邮箱：</span>A@b.com</li>
                    </ul>
                    <button type="button" class="btn btn-primary" id="">修改</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

