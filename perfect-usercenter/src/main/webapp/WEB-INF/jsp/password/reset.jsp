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
    <div class="login_box ">
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
                    src="${pageContext.request.contextPath}/public/img/resetpassword.png" alt=""/></div>
            <div class="forget_form">

                <div class="login_part1 ">
                    <div class="forget_input">
                        <form id="resetFrm" action="/resetPwd" method="post">
                            <ul>
                                <li>
                                    <label for="pwd">请输入新密码：</label>
                                    <input type="password" id="pwd" name="pwd" placeholder="请输入登陆密码"/>
                                </li>
                                <li>
                                    <label>确认密码：</label>
                                    <input type="password" id="password" name="password" placeholder="请输入登陆密码"/>
                                </li>
                                <li>
                                    <label for="j_validate">验证码：</label>
                                    <input style="width: 50%" type="text" id="j_validate" name="j_validate"/>
                                    <b id="code" style="cursor: pointer;padding: 10px;font-size: 16px"
                                       onclick="createCodeLogins()"></b>
                                </li>
                                <li>
                                    <input type="hidden" name="userid" value="${userid}"/>
                                    <input type="hidden" name="userToken" value="${userToken}"/>
                                    <input type="button" id="resetPwd" class="loginButton" value="下一步"/>
                                </li>
                            </ul>
                        </form>
                        <div>
                            <b id="resetMsg">${resetsMsg}</b>
                        </div>
                    </div>
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
    <div class="login_box hides">
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
                    src="${pageContext.request.contextPath}/public/img/complete.png" alt=""/></div>
            <div class="forget_form">
                <div class="login_part1 ">
                    <div class="forget_input" style="margin-left: 120px;">
                        <ul>
                            <li>
                                密码已重置完成，请 <a href="/" class="completeButton" value="登陆"/>登录</a>
                            </li>
                        </ul>
                    </div>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/loginOrReg/forgetPassword.js"></script>
<script type="text/javascript">
    $(function () {
        $("#resetPwd").click(function () {
            var pwd = $("#pwd").val()
            var password = $("#password").val();
            if (pwd == "" || pwd == undefined || password == "" || password == undefined) {
                userMsgInfo("新密码或确认密码不能为空！");
            } else if (pwd != password) {
                userMsgInfo("输入的两次密码不一致");
            } else {
                submitToCode();
            }
        })
        if(${resetsMsg == "OK"}){
            next_complete();
        }else if(${resetsMsg == "fild"}){
            userMsgInfo("密码修改失败,请重试！")
        }
    });

    var userMsgInfo = function (info) {
        var _invalidUserName = "";
        if (info != "" && info != undefined) {
            _invalidUserName = info;
        } else {
            _invalidUserName = "${resetsMsg}";
        }
        $("#resetMsg").html(_invalidUserName);
        if (_invalidUserName == "") {
            $("#resetMsg").parent().removeClass("login_checkbox");
        } else {
            $("#resetMsg").parent().addClass("login_checkbox");
        }
    };

    function next_complete(){
        $(".login_box:eq(0)").addClass("hides");
        $(".login_box:eq(1)").removeClass("hides")
    }

    var code; //在全局定义验证码
    //产生验证码
    var createCodeLogins = function () {
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
    window.onload = createCodeLogins();
    //校验验证码
    $("#loginSec").click(function () {
        submitToCode();
    })
    document.onkeydown = function (event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if (e && e.keyCode == 13) { // enter 键
            submitToCode();
        }
    };

    var submitToCode = function () {
        var inputCode = document.getElementById("j_validate").value.toUpperCase(); //取得输入的验证码并转化为大写
        if (inputCode.length <= 0) {
            userMsgInfo("请输入验证码！");
        } else if (inputCode != code) {
            userMsgInfo("验证码输入错误！");
            createCodeLogins();
            document.getElementById("j_validate").value = "";
        } else {
            $("#resetFrm").submit();
        }
    }
</script>

</body>
</html>