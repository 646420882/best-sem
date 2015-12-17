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
                    <ul id="userList">
                        <li><span>公司名称：</span><b>${user.companyName}</b></li>
                        <li><span>开通平台：</span><b>${user.companyName}</b></li>
                        <li><span>网站名称：</span><b>北京普菲特广告有限公司</b></li>
                        <li><span>网址：</span><b>http：//www.perfect-cn.cn</b></li>
                        <li><span>注册时间：</span>2015-10-10</li>
                        <li><span>联系人：</span><b>北京某某</b></li>
                        <li><span>办公电话：</span><b>12315464</b></li>
                        <li><span>移动电话：</span><b>1555555552</b></li>
                        <li><span>通讯地址：</span><b>XXX省xxx市XXXXXX</b></li>
                        <li><span>电子邮箱：</span><b>A@b.com</b></li>
                    </ul>
                    <button type="button" class="btn btn-primary" onclick="Modify(this)">修改</button>
                    <div class="ModifyHide hide">
                        <button type="button" class="btn btn-primary" onclick="Preservation(this)" >保存</button>
                        <button type="button" class="btn" style="margin-top:26px;" onclick="Cancel(this)" >取消</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/public/js/index/index.js"></script>
</body>
</html>

