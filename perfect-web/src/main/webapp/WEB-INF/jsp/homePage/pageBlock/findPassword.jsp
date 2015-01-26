<%--
  Created by IntelliJ IDEA.
  User: guochunyan
  Date: 2014/9/29
  Time: 11:22
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
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/login/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/login/login.css">
</head>
<body>
<div class="login over">
    <img src="/public/img/login_bg.jpg" width="100%" height="100%">
</div>
<div class="login_box">
    <div class="login_box2">
        <div class="login_part1 ">
            <div class="login_logo2 ">
                <img src="/public/img/login_logo.png">
            </div>
            <div class="login_click over">
                <span id="tishi"></span>
                <a href="/login">→ 已有帐号？登陆</a>
            </div>
            <div class="login_input2">
                <form id="defaultForm" method="post" class="form-horizontal" action="">
                    <input type="hidden" value="${userName}" name="userName" />
                    <input type="hidden" value="${key}" name="key" />
                    <ul>
                        <li>
                            <span>子账户名：</span>

                            <div class="form-group has-feedback fl">
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" name="baiduAccountName">
                                </div>
                            </div>
                        </li>
                        <li>
                            <span>密码：</span>

                            <div class="form-group has-feedback fl">
                                <div class="col-lg-5">
                                    <input type="password" class="form-control" name="password">
                                </div>
                            </div>
                        </li>
                        <li>
                            <span>确认密码：</span>

                            <div class="form-group has-feedback fl">
                                <div class="col-lg-5"><input type="password" class="form-control"
                                                             name="confirmPassword">
                                </div>
                            </div>
                        </li>
                        <li>
                            <span>验证码：</span>

                            <div class="form-group has-feedback fl">
                                <div class="col-lg-5">
                                    <input type="text" class="form-control proving" id="input1" name="code_text">
                                    <input type="text" onclick="createCode()" name="code" readonly="readonly"
                                           id="checkCode" class="unchanged" style="width: 65px"/>

                                </div>
                            </div>
                        </li>
                    </ul>
                    <div class="login_part2">
                        <input type="button" id="submitBtn" value="重置密码" class="submit"></a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="login_contact over">
        <a href="#">普菲特官网 </a>如在使用过程中有任何问题请联系客服：010-84922996
    </div>
</div>
</div>
<input type="hidden" id="dataRe" value="${state}">
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/login/bootstrapValidator.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
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
    $(document).ready(function () {
        createCode();
        $('#defaultForm').bootstrapValidator({
            message: '此值无效',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                baiduAccountName: {
                    message: '子账户名无效',
                    validators: {
                        notEmpty: {
                            message: '子账户名不能为空'
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9_\.]+$/,
                            message: '密码包括数字、字母和下划线'
                        },
                        stringLength: {
                            min: 4,
                            max: 14,
                            message: '密码字段须大于4且小于14'
                        },
                        identical: {
                         field: 'confirmPassword',
                         message: '密码和确认是不一样的'
                         }
                    }
                },
                confirmPassword: {
                    validators: {
                        notEmpty: {
                            message: '确认密码不能为空'
                        },
                        identical: {
                            field: 'password',
                            message: '输入密码需和上面密码一致'
                        }
                    }
                },
                code_text: {
                    validators: {
                        callback: {
                            message: '验证码错误',
                            callback: function (value, validator) {
                                if (value == null || value == "") {
                                    return false;
                                }
                                value = value.toUpperCase();
                                var captcha = $("input[name='code']:text").val().toUpperCase();
                                return value == captcha;
                            }
                        },
                        notEmpty: {
                            message: '验证码不能为空'
                        }
                    }
                }
            }
        });
    });
    var code; //在全局 定义验证码
    function createCode() {
        code = "";
        var codeLength = 4;//验证码的长度
        var checkCode = document.getElementById("checkCode");
        var selectChar = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');//所有候选组成验证码的字符，当然也可以用中文的

        for (var i = 0; i < codeLength; i++) {

            var charIndex = Math.floor(Math.random() * 36);
            code += selectChar[charIndex];


        }
//       alert(code);
        if (checkCode) {
            checkCode.className = "code";
            checkCode.value = code;
        }

    }



    /**
    *重置密码按钮的单击事件
     */
    $("#submitBtn").click(function () {
        var key = $("#defaultForm").find("input[name='key']").val();
        var baiduAccountName = $("#defaultForm").find("input[name='baiduAccountName']").val();
        var userName = $("#defaultForm").find("input[name='userName']").val();
        var password = $("#defaultForm").find("input[name='password']").val();

        var confirmPassword = $("#defaultForm").find("input[name='confirmPassword']").val();
        var validateCode = $("#input1").val();
        var checkCode = $("#checkCode").val();

        if(isNull(baiduAccountName)==false){
            return;
        }
        if(isNull(password)==false){
            return;
        }
        if(isNull(confirmPassword)==false){
            return;
        }
        if(password.length<4){
            return;
        }
        if(confirmPassword.length<4){
            return;
        }
        if(password!=confirmPassword){
            return;
        }

        if(validateCode.toUpperCase()!=checkCode.toUpperCase()){
            return;
        }



        var XMLHttpReq;
        function createXMLHttpRequest() {
            try {
                XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");//IE高版本创建XMLHTTP
            }
            catch(E) {
                try {
                    XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");//IE低版本创建XMLHTTP
                }
                catch(E) {
                    XMLHttpReq = new XMLHttpRequest();//兼容非IE浏览器，直接创建XMLHTTP对象
                }
            }

        }
        function sendAjaxRequest(url) {
            createXMLHttpRequest();                                //创建XMLHttpRequest对象
            XMLHttpReq.open("post", url, true);
            XMLHttpReq.onreadystatechange = processResponse; //指定响应函数
            XMLHttpReq.send(null);
        }
        //回调函数
        function processResponse() {
            if (XMLHttpReq.readyState == 4) {
                if (XMLHttpReq.status == 200) {
                    var text = XMLHttpReq.responseText;
                    if(text=='"updateSuccess"'){
                        alert("重置密码成功!");
                        window.location = "/login";
                    }else if(text=='"updateFail"'){
                        alert("重置密码失败!");
                    }else if(text=='"NoAccount"'){
                       alert("该子账户不存在,不能重置密码!");
                    }else if(text=='"urlInvali"'){
                       alert("修改密码链接已失效，请重申!");
                        window.location = "/login";
                    }
                }
            }

        }

        sendAjaxRequest("/forgetPassword/resetPassword?key="+key+"&baiduAccountName="+baiduAccountName+"&userName="+userName+"&password="+password);
    });


    /**
    *验证是否已经有值
* @param str
* @returns {Number}
     */
    function isNull(str) {
        var len = str.replace(/ /g,"").length;
        if(len==0){
            return false;
        }else{
            return true;
        }
    }
    
</script>

</body>
</html>
