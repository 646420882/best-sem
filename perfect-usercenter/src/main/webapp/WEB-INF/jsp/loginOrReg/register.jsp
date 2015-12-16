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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public.css">
    <%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css">--%>
    <link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/login/login.css">--%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/multiple-select.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/onlyLogin.css">
</head>
<body>
<div class="login">
    <%--<img src="/public/img/login_bg.jpg" width="100%" height="100%">--%>
        <div class="loginBg" style="position: absolute;width: 100%;height: 100%;background: #e7e7e7;z-index: -1;"></div>
        <div class="registe_box">
            <div class="login_logo2 ">
                <a href="http://best-ad.cn/" target="_blank"><img src="/public/img/login_logo.png"></a>
            </div>
            <div class="login_box2">
                <div class="login_click over">
                    <span id="tishi" class="fl">用户注册</span>
                    <a href="/" class="fr">→ 已有账号？登陆</a>
                </div>
                <div class="login_part1 ">
                    <div class="login_input2">
                        <form id="defaultForm" method="post" class="form-horizontal" action="/userCenter/add">
                            <ul>
                                <li>
                                    <label class="fl">公司名称：</label>
                                    <%--<input type="text" name="companyname" value="个人用户请填写姓名"
                                           onfocus="if(value=='个人用户请填写姓名') {value=''}"
                                           onblur="if (value=='') {value='个人用户请填写姓名'}">--%>
                                    <div class="form-group has-feedback">
                                        <div>
                                            <input class="registeInput" type="text" name="companyname" placeholder="个人用户请填写姓名"
                                                   onfocus="if(value=='个人用户请填写姓名') {value=''}"
                                                   onblur="if (value=='') {value='个人用户请填写姓名'}">
                                        </div>
                                    </div>
                                    <span style="color: red" class="register_star">*</span>
                                </li>
                                <li>
                                    <label class="fl">用户名：</label>


                                    <div class="form-group has-feedback">
                                        <div>
                                            <input class="registeInput" type="text" name="username">
                                        </div>
                                    </div>

                                    <span style="color: red" class="register_star">*</span>
                                </li>
                                <li>
                                    <label class="fl">注册邮箱：</label>

                                    <div class="form-group has-feedback">
                                        <div>
                                            <input class="registeInput" type="text" name="email"/>
                                        </div>
                                    </div>
                                    <span style="color: red" class="register_star">*</span>
                                </li>
                                <li>
                                    <label class="fl">密码：</label>
                                    <div class="form-group has-feedback">
                                        <div>
                                            <input class="registeInput" type="password" name="password">
                                        </div>
                                    </div>
                                    <span style="color: red" class="register_star">*</span>
                                </li>
                                <li>
                                    <label class="fl">确认密码：</label>
                                    <div class="form-group has-feedback">
                                        <div>
                                            <input class="registeInput" type="password" name="confirmPassword">
                                        </div>
                                    </div>
                                    <span style="color: red" class="register_star">*</span>
                                </li>
                                <li>
                                    <label class="fl">通信地址：</label>
                                    <div class="form-group has-feedback">
                                        <div>
                                            <input class="registeInput" type="text" name="mailinAddress">
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <label class="fl">联系人：</label>
                                    <div class="form-group has-feedback">
                                        <div>
                                            <input class="registeInput" type="text" name="contacts">
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <label class="fl">联系电话：</label>
                                    <div class="form-group has-feedback">
                                        <div>
                                            <input class="registeInput" type="text" name="contactsPhone">
                                        </div>
                                    </div>
                                </li>
                                <li id="openPlatformLi">
                                    <label class="fl">开通平台：</label>
                                    <div class="form-group has-feedback">
                                        <div>
                                            <select id="openPlatform" multiple="multiple" name="openPlatform">
                                                <option value="1">百思搜客</option>
                                                <option value="2">百思慧眼</option>
                                            </select>
                                        </div>
                                    </div>
                                    <span style="color: red" class="register_star">*</span>
                                </li>
                                <li>
                                    <label class="fl">百度凤巢账户：</label>
                                    <div class="form-group has-feedback">
                                        <div>

                                            <input class="registeInput" type="text" name="phoenixNestUser">
                                        </div>
                                    </div>
                                    <span style="color: red" class="register_star">*</span>
                                </li>
                                <li>
                                    <label class="fl">百度凤巢密码：</label>
                                    <div class="form-group has-feedback">
                                        <div>

                                            <input class="registeInput" type="text" name="phoenixNestPassword">
                                        </div>
                                    </div>
                                    <span style="color: red" class="register_star">*</span>
                                </li>
                                <li>
                                    <label class="fl">网站URL地址：</label>
                                    <div class="form-group has-feedback">
                                        <div>
                                            <input class="registeInput" type="text" name="urlAddress">

                                        </div>
                                    </div>
                                    <span style="color: red" class="register_star">*</span>
                                </li>
                                <li style="margin-left: 115px;">
                                    <input name="accountType" checked type="radio" id="freeAccount" class="accountType" value="1"><label for="freeAccount" class="accountType" style="margin-right: 15px;">试用账户</label>
                                    <input type="radio" name="accountType" id="payAccount" class="accountType" value="2"><label for="payAccount" class="accountType">付费账户</label>
                                </li>
                                <li>
                                    <label class="fl">验证码：</label>

                                    <div class="form-group has-feedback">
                                        <div>
                                            <input type="text" class="proving registeInput" id="input1" name="code_text">
                                            <input type="text" onclick="createCode()" name="code" readonly="readonly"
                                                   id="checkCode" class="unchanged" style="width: 65px"/>

                                        </div>
                                    </div>
                                    <span style="color: red" class="register_star">*</span>
                                </li>
                                <li >
                                    <input type="submit" value="立即注册" class="submit registeButton">
                                </li>
                            </ul>
                            <%--<div class="login_part2" style="margin-top:20px;">
                                <input type="submit" id="" value="立即注册" class="submit">
                            </div>--%>
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

</div>
<input type="hidden" id="dataRe" value="${state}">
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/public/bootstrapValidator.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/public/jquery.multiple.select.js"></script>
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
    $(document).ready(function () {
        $("#openPlatform").multipleSelect({
            placeholder: "请选择开通平台",
            selectAll: false,
            minumimCountSelected: 20,
            multiple: true
        })
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
                email: {
                    validators: {
                        emailAddress: {
                            message: '请输入正确的邮箱地址'
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
        if(confirm('注册成功！点击按钮继续操作')){ location.href="/"}else{ location.href="/"}
    } else if ($("#dataRe").val() == -1) {
        $("#tishi").append("注册失败！用户名已存在！   ");
    }

</script>


</body>
</html>
