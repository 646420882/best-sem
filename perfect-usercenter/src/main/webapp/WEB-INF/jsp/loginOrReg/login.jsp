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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public.css">
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css">--%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/login/login.css">--%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/onlyLogin.css">
    <script>
        var _pct = _pct || [];
        (function () {
            var hm = document.createElement("script");
            hm.src = "//t.best-ad.cn/t.js?tid=76c005e89e020c6e8813a5adaba384d7";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
</head>
<body>
<div class="login over">
    <%--<img src="${pageContext.request.contextPath}/public/img/login_bg.jpg" width="100%" height="100%">--%>
</div>
<div class="login_box">
    <div class="login_logo ">
        <a href="http://best-ad.cn/" target="_blank"><img
                src="${pageContext.request.contextPath}/public/img/login_logo.png"></a>
    </div>
    <div class="login_box1">
        <div class="login_click over" style="margin-bottom: 35px">
            <span class="fl">用户登录</span>
            <a href="/register" class="fr">→ 还没有账号？点击注册</a>
        </div>
        <form action="/login" id="frmSubmit" method="post">
            <input type="hidden" name="redirect" value="${redirect_url}"/>
            <input type="hidden" name="url" value="<%=request.getParameter("url")%>"/>

            <div class="login_part1 ">
                <div class="login_input">
                    <ul>
                        <li>
                            <label for="j_username">用户名：</label>
                            <input type="text" id="j_username" name="j_username"/>

                            <%--<span><img src="${pageContext.request.contextPath}/public/img/login_user.png"></span>--%>

                            <div>
                                <b id="invalidUserName">${invalidUserName}</b>
                            </div>
                        </li>
                        <li>
                            <label for="j_password">密码：</label>
                            <input type="password" id="j_password" name="j_password"/>
                            <%--<span><img src="${pageContext.request.contextPath}/public/img/login_lock.png"></span>--%>

                            <div>
                                <b id="invalidPassword">${invalidPassword}</b>
                            </div>
                        </li>
                        <li>
                            <label for="j_validate">验证码：</label>
                            <input style="width: 50%" type="text" id="j_validate" name="j_validate"/>
                            <b id="code" style="cursor: pointer;padding: 10px;font-size: 16px" onclick="createCodeLogin()"></b>
                            <div>
                                <b id="codeMsg"></b>
                            </div>
                        </li>
                        <li>
                            <input type="button" id="loginSec" class="loginButton"
                                   onclick="_pct.putPar(['_trackEvent', 'Login', 'click', '登陆信息'])" value="登陆"/>
                        </li>
                        <li><a id="forgetPassword" class="fr" href="#">忘记密码?</a></li>
                    </ul>
                </div>
                <%--<div class="login_part2">
                    <input type="submit" onclick="_pct.putPar(['_trackEvent', 'Login', 'click', '登陆信息'])" value="登陆"/>
                </div>--%>
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
<%--<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>--%>
<script type="text/javascript">
    window.jQuery || document.write("<script src='http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js'>\x3C/script>");
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/loginOrReg/forgetPassword.js"></script>

<script type="text/javascript">

    /*$(window).resize(function () {
     $('.login_box').css({
     position: 'absolute',
     left: ($(window).width() - $('.login_box').outerWidth()) / 2,
     top: ($(window).height() - $('.login_box').outerHeight()) / 2 + $(document).scrollTop()
     });
     });*/
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

    var code; //在全局定义验证码
    //产生验证码
    var createCodeLogin = function () {
        code = "";
        var codeLength = 4;//验证码的长度
        var checkCode = document.getElementById("code");
        var random = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');//随机数
        for (var i = 0; i < codeLength; i++) {//循环操作
            var index = Math.floor(Math.random() * 36);//取得随机数的索引（0~35）
            code += random[index];//根据索引取得随机数加到code上
        }
        checkCode.innerHTML = code;//把code值赋给验证码
    };
    window.onload = createCodeLogin();
    //校验验证码
    $("#loginSec").click(function () {
        var inputCode = document.getElementById("j_validate").value.toUpperCase(); //取得输入的验证码并转化为大写
        if (inputCode.length <= 0) {
            $("#codeMsg").html("请输入验证码！");
            if ($("#codeMsg").html() == "") {
                $("#codeMsg").parent().removeClass("login_checkbox");
            } else {
                $("#codeMsg").parent().addClass("login_checkbox");
            }
        } else if (inputCode != code) {
            $("#codeMsg").html("验证码输入错误！");
            if ($("#codeMsg").html() == "") {
                $("#codeMsg").parent().removeClass("login_checkbox");
            } else {
                $("#codeMsg").parent().addClass("login_checkbox");
            }
            createCodeLogin();
            document.getElementById("j_validate").value = "";
        } else {
            $("#frmSubmit").submit();
        }
    })

</script>

</body>
</html>