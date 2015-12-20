/**
 * Created by guochunyan on 2015/12/16.
 */

var captchaValidateStatus = true;

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

    var currentEmail = $('#oldEmail').html();
    if (currentEmail == null || currentEmail.trim() == "") {
    } else {
        $('#bindedEmail').html("已绑定邮箱: " + currentEmail);
        $('#emailModalbottom').css("display", "none");
        $(".emailHide").removeClass("hide");
    }

    $('#emailSetting').click(function () {
        $.ajax({
            url: "/account/email/sendCaptcha",
            type: "POST",
            dataType: "JSON",
            data: {
                email: $('#email').val()
            }
        });

    });

    $('#changeEmailAddr').click(function () {
        $.ajax({
            url: "/account/email/sendCaptcha",
            type: "POST",
            dataType: "JSON",
            data: {
                email: $('#oldEmail').html()
            }
        });

    });

    $('#setNewEmail').click(function () {
        var newEmail = $('#newEmailAddr').val();
        if (newEmail == null || newEmail.trim() == "") {
            alert("请输入邮箱地址!");
            return;
        }

        $('#emailChangeStepStatus').val(1);

        $.ajax({
            url: "/account/email/sendCaptcha",
            type: "POST",
            dataType: "JSON",
            data: {
                email: $('#newEmailAddr').val()
            }
        });

    });

    $('#captchaValidate').click(function () {
        var changeEmailCaptcha = $('#changeEmailCaptcha').val();
        if (changeEmailCaptcha == null || changeEmailCaptcha.trim() == "") {
            alert("请输入校验码!");
            return;
        }

        $.ajax({
            url: "/account/email/confirmCaptcha",
            type: "POST",
            dataType: "JSON",
            data: {
                username: $('#sysUserName').val(),
                email: $('#oldEmail').html(),
                captcha: $('#changeEmailCaptcha').val()
            },
            success: function (data) {
                switch (data.status) {
                    case 0:
                        captchaValidateStatus = false;
                        alert("验证码输入错误!");
                        break;
                    case -1:
                        captchaValidateStatus = false;
                        alert("验证码已失效!请重新发送");
                    default:
                        break;
                }
            }
        });

    });

    $(phoneModal)
});
// 绑定
var commons = {
    Binding: function (type) {
        switch (type) {
            case "phone":
                $('#phoneModal').modal('hide');
                $("#phoneModalbottom").hide();
                $(".phoneHide").removeClass("hide");
                break;
            case "email":
                $.ajax({
                    url: '/account/email/confirmCaptcha',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        username: $('#sysUserName').val(),
                        email: $('#email').val(),
                        captcha: $('#emailCaptcha').val()
                    },
                    success: function (data) {
                        switch (data.status) {
                            case 1:
                                $('#bindedEmail').html("已绑定邮箱: " + $('#email').val());
                                alert("绑定成功!");
                                $('#emailModal').modal('hide');
                                $(".emailHide").removeClass("hide");
                                $("#emailModalbottom").hide();
                                break;
                            case 0:
                                alert("验证码输入错误!");
                                break;
                            case -1:
                                alert("验证码已失效!请重新发送");
                            default:
                                break;
                        }
                    }
                });
                break;
        }
    },
    unBinding: function (type) {
        switch (type) {
            case "modifyPhone":
                $('#phoneModal').modal("show");
                $("#phoneTitle").html("修改手机号码");
                $("#phoneModalbottom").hide();
                $(".phoneHide").removeClass("hide");
                break;
            case "modifyEmail":
                $('#modifyemailModal').modal('show');
                $(".emailHide").removeClass("hide");
                $("#emailModalbottom").hide();
                break;
        }
    },
    oldEmail: function (type) {
        switch (type) {
            case "oldEmails":
                if (parseInt($('#emailChangeStepStatus').val()) == 1) {
                    captchaValidateStatus = true;

                    var newEmailCaptcha = $('#newEmailCaptcha').val();
                    if (newEmailCaptcha == null || newEmailCaptcha.trim() == "") {
                        alert("请输入验证码!");
                        return;
                    }

                    $('#emailChangeStepStatus').val(0);

                    $.ajax({
                        url: '/account/email/confirmCaptcha',
                        type: 'POST',
                        dataType: 'JSON',
                        data: {
                            username: $('#sysUserName').val(),
                            email: $('#newEmailAddr').val(),
                            captcha: $('#newEmailCaptcha').val()
                        },
                        success: function (data) {
                            switch (data.status) {
                                case 1:
                                {
                                    alert("绑定成功!");
                                    // TODO 修改邮箱成功之后弹出框没有关闭
                                    //$('#emailModal').modal('hide');
                                    //$(".emailHide").removeClass("hide");
                                    //$("#emailModalbottom").hide();
                                    $('#modifyemailModal').modal('show');
                                    $(".emailHide").removeClass("hide");
                                    $("#emailModalbottom").hide();
                                    break;
                                }
                                case 0:
                                {
                                    alert("验证码输入错误!");
                                    break;
                                }
                                case -1:
                                {
                                    alert("验证码已失效!请重新发送");
                                }
                                default:
                                    break;
                            }
                        }
                    });
                } else {
                    var changeEmailCaptcha = $('#changeEmailCaptcha').val();
                    if (changeEmailCaptcha == null || changeEmailCaptcha.trim() == "") {
                        alert("请输入校验码!");
                        return;
                    }

                    if (!captchaValidateStatus) {
                        alert("校验失败!");
                        return;
                    }

                    $("#EmailChange").hide();
                    $("#newEmail").removeClass("hide");
                }
                break;
            default:
                break;
        }
    }
};

// 解绑
function phoneUnBundling(_this) {
    $("#modelbox").modal('show');
    $("#modelboxTitle").html("确认取消绑定？");
    $("#modelboxBottom").click(function () {
        $.ajax({
            url: '/account/email/unbind',
            type: 'POST',
            dataType: 'JSON',
            data: {
                username: $('#sysUserName').val()
            },
            success: function (data) {
                if (data.status) {
                    $("#modelbox").modal('hide');
                    $(_this).parent().addClass("hide");
                    $(_this).parent().next("button").show();
                    alert("解除绑定成功!");
                } else {
                    alert("解除绑定失败!");
                }
            }
        });
    });
}

var validateEmail = function (email) {
    var regx = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regx.test(email);
};
