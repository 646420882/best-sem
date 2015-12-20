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

    $('#emailSetting').click(function () {
        $.ajax({
            url: "/account/email/sendCaptcha",
            type: "POST",
            dataType: "JSON",
            data: {
                "email": $('#email').val()
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
                $('#emailModal').modal('hide');
                $(".emailHide").removeClass("hide");
                $("#emailModalbottom").hide();
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
                $("#EmailChange").hide();
                $("#newEmail").removeClass("hide");
                break;
        }
    }
};

// 解绑
function phoneUnBundling(_this) {
    $("#modelbox").modal('show');
    $("#modelboxTitle").html("确认取消绑定？");
    $("#modelboxBottom").click(function () {
        $("#modelbox").modal('hide');
        $(_this).parent().addClass("hide");
        $(_this).parent().next("button").show();
    });
}

var validateEmail = function (email) {
    var regx = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regx.test(email);
};
