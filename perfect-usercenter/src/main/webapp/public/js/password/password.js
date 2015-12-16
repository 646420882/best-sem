/**
 * Created by guochunyan on 2015/12/16.
 */
$(document).ready(function () {
    $('#defaultForm').bootstrapValidator({
        message: '此值无效',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            password:{
                validators: {
                notEmpty: {
                    message: '请输入旧密码'
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