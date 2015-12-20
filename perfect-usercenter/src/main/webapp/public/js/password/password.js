/**
 * Created by guochunyan on 2015/12/16.
 */
$(document).ready(function () {
    var valid = $("#valid").val();
    if (valid != null && valid != "" && valid != undefined) {
        if (valid) {
            alert("密码修改成功,请重新登录帐号！");
            location.href = "/logout";
        } else {
            alert("密码修改失败");
        }
    }
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
                        message: '请输入旧密码'
                    },
                    remote: {
                        url: '/account/password/validate',
                        type: 'POST',
                        delay: 2000,
                        data: {
                            pwd: function () {
                                return $("#password").val()
                            },
                            userName: $("#userName").val()
                        },
                        message: '与当前使用密码不一致!'
                    }
                }
            },
            NewPassword: {
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
                        field: 'NewPassword',
                        message: '输入密码需和上面密码一致'
                    }
                }
            }
        }
    });
});