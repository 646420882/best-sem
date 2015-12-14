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
                当前位置：安全工具
            </div>
            <div class="user_box">
                <div class="user_content safe_box">
                    <div class="panel panel-default">
                        <div class="panel-heading"><span aria-hidden="true" ng-class="icon"
                                                         class="glyphicon glyphicon-lock"></span>密保手机
                        </div>
                        <div class="panel-body">
                            <p>您可以在这里设置密保主手机和子手机，密保手机能够完成登录时的二次校验，提升账户安全等级，同时可以用来找回密码和接收相关安全提醒信息。</p>

                            <div class="panel_safe fl ">
                                <div class="fl">已绑定号码：15040****11<span>解绑</span><span>修改</span></div>
                                <button type="button" class="btn btn-primary fr" data-toggle="modal"
                                        data-target="#phoneModal">设置密保手机
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading"><span aria-hidden="true" ng-class="icon"
                                                         class="glyphicon glyphicon-lock"></span>密保问题
                        </div>
                        <div class="panel-body">
                            <p>设定1个您自己才知道的问题。以便找回密码或验证时使用。</p>

                            <div class="panel_safe fl ">
                                <div class="fl">已绑定问题：我的名字<span>解绑</span><span>修改</span></div>
                                <button type="button" class="btn btn-primary fr" data-toggle="modal"
                                        data-target="#questionModal">设置密保问题
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading"><span aria-hidden="true" ng-class="icon"
                                                         class="glyphicon glyphicon-lock"></span>密保邮箱
                        </div>
                        <div class="panel-body">
                            <p>与电子邮箱绑定，不但能在找回密码时使用，还能方便获取相关安全提醒信息。</p>

                            <div class="panel_safe fl ">
                                <div class="fl">已绑定邮箱：<span>解绑</span><span>修改</span></div>
                                <button type="button" class="btn btn-primary fr" data-toggle="modal"
                                        data-target="#emailModal">设置密保邮箱
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../safe/safemodel.jsp"/>
</body>
</html>
