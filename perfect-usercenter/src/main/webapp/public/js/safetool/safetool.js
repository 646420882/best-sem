/**
 * Created by guochunyan on 2015/12/16.
 */
$(document).ready(function () {
    $('#phoneForm').bootstrapValidator({
        message: '此值无效',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            phone: {
                validators: {
                    notEmpty: {
                        message: '电话号码不能为空'
                    },
                    stringLength: {
                        min: 11,
                        max: 11,
                        message: '请输入正确的手机号码'
                    }
                }
            }
        },
        code_text: {
            validators: {
                notEmpty: {
                    message: '验证码不能为空'
                }

            }
        }
    });
});