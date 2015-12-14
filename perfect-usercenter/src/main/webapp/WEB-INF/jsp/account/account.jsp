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
            <div class="user_box">
                <div class="user_title">
                    <span aria-hidden="true" ng-class="icon"
                          class="glyphicon glyphicon-user"></span>账户：<b>perfect2015</b>
                    <button type="button" class="btn btn-default">试用账户</button>
                </div>
                <div class="user_content password_box">
                    <p>关联推广账户</p>

                    <div class="bs-example" data-example-id="hoverable-table">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>关联推广账户</th>
                                <th>密码</th>
                                <th>URL地址</th>
                                <th>账户所属平台</th>
                                <th>绑定时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>

                            </tr>
                            <tr>
                                <th scope="row">2</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">3</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                                <td>@mdo</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

