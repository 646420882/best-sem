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
    <%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css">--%>
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
                <form id="defaultForm" method="post" class="form-horizontal" action="/register/add">
                    <ul>
                        <li>
                            <span>公司名称：</span>

                            <div class="form-group has-feedback fl">
                                <div class="col-lg-5">
                                    <input type="text" name="companyname" class="form-control" value="个人用户请填写姓名"
                                           onfocus="if(value=='个人用户请填写姓名') {value=''}"
                                           onblur="if (value=='') {value='个人用户请填写姓名'}">
                                </div>
                            </div>
                        </li>
                        <li>
                            <span>用户名：</span>

                            <div class="form-group has-feedback fl">
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" name="username" id="text1">
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
                        <input type="submit" id="" value="立即注册" class="submit"></a>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/login/bootstrapValidator.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.min.js"></script>
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
                username: {
                    message: '用户名无效',
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空'
                        },
                        stringLength: {
                            min: 4,
                            max: 14,
                            message: '用户名须大于4且小于14'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9_\.]+$/,
                            message: '用户名包括数字、字母和下划线'
                        }
                    }
                },
                companyname: {
                    validators: {
                        notEmpty: {
                            message: '公司名称不能为空'
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
                        }
                        /*identical: {
                         field: 'confirmPassword',
                         message: '密码和确认是不一样的'
                         }*/
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
                /*            code_text:{
                 validators: {
                 notEmpty: {
                 message: '验证码不能为空'
                 },
                 identical: {
                 field: 'code',
                 message: '请输入正确的验证码'
                 }
                 }
                 }
                 */
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
    $("#submitBtn").click(function () {
        $("#defaultForm").submit;
    });
    if ($("#dataRe").val() == 1) {
        $("#tishi").append("注册成功！                ");
    }else if($("#dataRe").val() == -1)
    {
        $("#tishi").append("注册失败！用户名已存在！   ");
    }

</script>

</body>
</html>
