<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2014-8-29
  Time: 上午11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>大数据智能营销</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css">--%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/login/login.css">
    <script src="/public/js/demo/perfect.js"></script>
</head>
<body>
<div class="login over">
    <img src="${pageContext.request.contextPath}/public/img/login_bg.jpg" width="100%" height="100%">
</div>
<div class="login_box">
    <div class="login_box1">
        <form action="../j_spring_security_check" method="post">
            <div class="login_part1 ">
                <div class="login_logo ">
                    <a href="http://best-ad.cn/" target="_blank"><img src="${pageContext.request.contextPath}/public/img/login_logo.png"></a>
                </div>
                <div class="login_click over">
                    <a href="/register/page">→ 还没有帐号？点击注册</a>
                </div>

                <div class="login_input">
                    <ul>
                        <li>
                            <input type="text" id="j_username" name="j_username"/>
                            <span><img src="${pageContext.request.contextPath}/public/img/login_user.png"></span>

                            <div>
                                <b id="invalidUserName">${invalidUserName}</b>
                            </div>
                        </li>
                        <li>
                            <input type="password" id="j_password" name="j_password"/>
                            <span><img src="${pageContext.request.contextPath}/public/img/login_lock.png"></span>

                            <div>
                                <b id="invalidPassword">${invalidPassword}</b>
                            </div>
                        </li>
                        <li><a id="forgetPassword" href="#'">忘记密码</a></li>
                    </ul>
                </div>

            </div>
            <div class="login_part2">
                <input type="submit" value="登陆"/>
            </div>
        </form>
    </div>
    <div class="login_contact over">
        <a href="http://www.perfect-cn.cn/" target="_blank">普菲特官网 </a>如在使用过程中有任何问题请联系客服：010-84922996
        <script type="text/javascript">
            var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
            document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F9e0d99624d24d17bb95abc5ca469c64e' type='text/javascript'%3E%3C/script%3E"));
        </script>
    </div>

</div>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.min.js"></script>--%>
<script type="text/javascript">
    window.jQuery || document.write("<script src='${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js'>\x3C/script>");
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/login/forgetPassword.js"></script>

<script type="text/javascript">

    $(window).resize(function () {
        $('.login_box').css({
            position: 'absolute',
            left: ($(window).width() - $('.login_box').outerWidth()) / 2,
            top: ($(window).height() - $('.login_box').outerHeight()) / 2 + $(document).scrollTop()
        });
    });
    //初始化函数
    $(window).resize();

    $(function () {

        var _invalidUserName = "${invalidUserName}";
        var _invalidPassword = "${invalidPassword}";

        if (_invalidUserName == "") {
            $("#invalidUserName").parent().removeClass("login_checkbox");
        } else {
            $("#invalidUserName").parent().addClass("login_checkbox");
        }

        if (_invalidPassword == "") {
            $("#invalidPassword").parent().removeClass("login_checkbox");

        } else {
            $("#invalidPassword").parent().addClass("login_checkbox");
        }
    });

</script>

</body>
</html>