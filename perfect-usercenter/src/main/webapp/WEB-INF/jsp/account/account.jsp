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
                当前位置：账户绑定
            </div>
            <div class="user_box" style="background: #f0f0f0">
                <div class="user_content account_box">
                    <h3>百思搜客绑定</h3>

                    <p>关联推广账户</p>
                    <table id="account_table" data-height="300">
                        <thead>
                        <tr>
                            <th data-field="name">关联推广账户</th>
                            <th data-field="stargazers_count">密码</th>
                            <th data-field="url">URL地址</th>
                            <th data-field="platform">账户所属平台</th>
                            <th data-field="time">绑定时间</th>
                            <th data-field="action" data-formatter="operateFormatter" data-events="operateEvents">操作
                            </th>
                        </tr>
                        </thead>
                    </table>
                </div>

                <div class="user_content account_box">
                    <h3>百思慧眼绑定</h3>

                    <p>关联推广账户</p>
                    <table id="AccountTable" data-height="300">
                        <thead>
                        <tr>
                            <th data-field="name">网站名称</th>
                            <th data-field="stargazers_count">网站URL</th>
                            <th data-field="url">统计代码</th>
                            <th data-field="action" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
                        </tr>
                        </thead>
                    </table>

                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/public/js/account/account.js"></script>
</body>
</html>

