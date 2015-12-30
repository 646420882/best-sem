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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/onlyLogin.css">
</head>
<body>
<div class="loginBg" style="position: absolute;width: 100%;height: 100%;background: #e7e7e7;z-index: -1;"></div>

<div class="login">
    <div class="login_box">
        <div class="login_logo ">
            <a href="http://best-ad.cn/" target="_blank"><img
                    src="${pageContext.request.contextPath}/public/img/login_logo.png"></a>
        </div>
        <div class="login_box1">
            <div class="forget_click over">
                <span class="fl">找回密码</span>
                <a href="/" class="fr">→ 返回</a>
            </div>
            <div style="background-color: #f2fbff;padding: 40px 0 0 29px;"><img
                    src="${pageContext.request.contextPath}/public/img/surecount.png" alt=""/></div>
            <div class="forget_form">
                <div class="login_part1">
                    <form id="forgetFrm" action="recoverPwd" method="post">
                        <div class="forget_input">
                            <ul>
                                <li>
                                    <label for="j_username">请输入要找回的账户：</label>
                                    <input type="text" id="j_username" name="username" placeholder="请输入登录账号" value=""/>
                                </li>
                                <li>
                                    <label>请输入注册邮箱：</label>
                                    <input type="email" id="email" name="email" placeholder="请输入邮箱地址" value=""/>
                                </li>

                                <li>
                                    <input type="button" id="forBtn" class="loginButton" value="下一步"/>
                                </li>
                            </ul>
                            <div>
                                <b id="invalidMsg">${invalidMsg}</b>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="login_contact over">
            <a href="http://www.perfect-cn.cn/" target="_blank">普菲特官网 </a>如在使用过程中有任何问题请联系客服：010-84922996
            <script type="text/javascript">
                var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
                document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F9e0d99624d24d17bb95abc5ca469c64e' type='text/javascript'%3E%3C/script%3E"));
            </script>
        </div>
    </div>
</div>
<script type="text/javascript">
    window.jQuery || document.write("<script src='http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js'>\x3C/script>");
</script>
<script type="text/javascript">

    $(function () {
        $("#forBtn").click(function(){
            var username = $("#j_username").val();
            var email = $("#email").val();
            if(username == "" || username == undefined){
                userMsgInfos("请填写用户名！");
            }else if(email == "" || email==undefined){
                userMsgInfos("请填写邮箱地址！");
            }else{
                if(validateEmail(email)){
                    $("#forgetFrm").submit()
                }else{
                    userMsgInfos("请输入正确格式的邮箱地址！");
                }
            }
        });

    });
    var userMsgInfos = function (info) {
        var _invalidMsg = "";
        if (info != "" && info != undefined) {
            _invalidMsg = info;
        } else {
            _invalidMsg = "${invalidMsg}";
        }
        $("#invalidMsg").html(_invalidMsg);
        if (_invalidMsg == "") {
            $("#invalidMsg").removeClass("login_checkbox");
        } else {
            $("#invalidMsg").addClass("login_checkbox");
        }
    }
    var validateEmail = function (email) {
        var regx = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return regx.test(email);
    };
</script>

</body>
</html>