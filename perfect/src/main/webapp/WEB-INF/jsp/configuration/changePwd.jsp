<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="tab">
    <div class="configure over">
        <div class="configure_top over">
            <h3 class="fl">修改密码</h3>
        </div>
        <div class="configure_under over">
              <div class="login_input2">
                  <form id="defaultForm" method="post" class="form-horizontal" action="/register/add">
                      <ul>
                          <li>
                              <span> * 当前密码：</span>

                              <div class="form-group has-feedback fl">
                                  <div class="col-lg-5">
                                      <input type="text" class="form-control" name="username" id="text1">
                                  </div>
                              </div>
                          </li>
                          <li>
                              <span> * 新密码：</span>

                              <div class="form-group has-feedback fl">
                                  <div class="col-lg-5">
                                      <input type="password" class="form-control" name="password">
                                  </div>
                              </div>
                          </li>
                          <li>
                              <span> * 确认新密码：</span>

                              <div class="form-group has-feedback fl">
                                  <div class="col-lg-5"><input type="password" class="form-control"
                                                               name="confirmPassword">
                                  </div>
                              </div>
                          </li>
                          <li>
                              <span> * 验证码：</span>

                              <div class="form-group has-feedback fl">
                                  <div class="col-lg-5">
                                      <input type="text" class="form-control proving" id="input1" name="code_text">
                                      <input type="text" onclick="createCode()" name="code" readonly="readonly"
                                             id="checkCode" class="unchanged" style="width: 65px"/>
                                  </div>
                              </div>
                          </li>
                          <li style="margin:20px 0 0 30px;">
                              <button type="button" class="btn btn-primary" style="margin-right:10px; ">确认</button>
                              <button type="button" class="btn btn-default">取消</button>
                          </li>
                      </ul>
                  </form>
              </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/login/bootstrapValidator.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.min.js"></script>
<script type="text/javascript">
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
                code_text: {
                    validators: {
                        callback: {
                            notEmpty: {
                                message: '验证码不能为空'
                            },
                            message: '验证码错误',
                            callback: function (value, validator) {
                                if (value == null || value == "") {
                                    return false;
                                }
                                value = value.toUpperCase();
                                var captcha = $("input[name='code']:text").val().toUpperCase();
                                return value == captcha;
                            }
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

</script>

