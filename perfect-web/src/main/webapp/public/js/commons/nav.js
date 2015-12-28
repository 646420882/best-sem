/**
 * Created by john on 2015/12/4.
 */
var loadBaiduAccount = function () {
    $.getJSON("/account/getAllBaiduAccount",
        {},
        function (data) {
            var results = data.rows;
            if (results != null && results.length > 0) {
                var lis = "";
                var index = results[0].baiduUserName.length;
                $.each(results, function (i, item) {
                    if (item.baiduAccountId == null || item.baiduAccountId == "null") {
                        // 此帐号还未被激活, 无法使用
                        return true;
                    }

                    var _item = item.baiduRemarkName;
                    if (_item == undefined) _item = item.baiduUserName.substring(0, (i > 0 ? index - 3 : index)) + (item.baiduUserName.length > index ? "..." : "");
                    if (baiduAccountId == item.baiduAccountId) {
                        $('.user_name span').html(_item);
                    }
                    lis += "<li  title='" + item.baiduUserName + "' value='" + item.baiduAccountId + "'>" + _item + "</li>";
                });
                $("#switchAccount_ul").empty();
                $("#switchAccount_ul").append(lis);

            }
        });
};
$(".nav_under>ul>li>a").each(function () {
    if ($($(this))[0].href == String(window.location)) {
        $(".nav_under>ul>li").removeClass("current");
        $(this).parent().addClass('current');
    }
});

$(function () {
    //取屏幕宽度
    var nav = "<img src='../public/img/nav.png'/>";
    var nav_open = "<img src='../public/img/nav_open.png'/>";
    $('[data-toggle="tooltip"]').tooltip();
    $('#navigator_tips').click(function (_this) {
        /*        $('.concent').toggleClass('nav_hide', 1000, "easeOutSine");*/
        if ($(".concent").hasClass("nav_hide")) {
            $(".concent").removeClass("nav_hide");
            $('[data-toggle="tooltip"]').tooltip();
            $("#navigator_tips").html(nav);
            $(".help_text").html("");
        } else {
            $(".concent").addClass("nav_hide");
            $("#navigator_tips").html(nav_open);
            $('[data-toggle="tooltip"]').tooltip('destroy');
            $(".help_text").html("帮助中心");
        }
    });

    function ChangeAccountajax(_accountId) {
        $.ajax({
            url: '/account/switchAccount',
            type: 'POST',
            async: false,
            dataType: 'json',
            data: {
                "accountId": _accountId
            },
            success: function (data, textStatus, jqXHR) {
                if (data.status != null && data.status == true) {
                    //location.replace(location.href);
                    window.location.reload(true);
                }
            }
        })
    }

    if (parseInt(baiduAccountId) != -1) {
        loadBaiduAccount();
    }
    document.onclick = function () {
        $("#switchAccount").hide();
        $("#filterSearchTemplate").hide();
        $(".remove").remove();
    }

    $("#switchAccount").click(function (e) {
        var ev = e || window.event;
        if (ev.stopPropagation) {
            ev.stopPropagation();
        }
        else if (window.event) {
            window.event.cancelBubble = true;//兼容IE
        }
    });
    $('.user_name').click(function (e) {
        if ($("#switchAccount").css("display") == "none") {//隐藏
            $(this).next('#switchAccount').show();

//                $("#switchAccount").mouseleave(function () {
//                    $("#switchAccount").css("display", "none");
//                    setTimeout(function () {
//                        $("#switchAccount").css("display", "none");
//                    }, 5000);
//                });
            $('#switchAccount li').click(function () {
                $('.user_name span').html($(this).text());
                var _accountId = $(this).val();
                $('#switchAccount').hide();
                ChangeAccountajax(_accountId);

            });
            //$('#searchCount').keydown(function (e) {
            //    if (e.keyCode == 13) {
            //        $('.user_name span').html($(this).val());
            //        var _accountId = $('#searchCount').attr("card");
            //        $('#switchAccount').hide();
            //        ChangeAccountajax(_accountId);
            //    }
            //});
            var ev = e || window.event;
            if (ev.stopPropagation) {
                ev.stopPropagation();
            }
            else if (window.event) {
                window.event.cancelBubble = true;//兼容IE
            }
        }
        else {
            $("#switchAccount").hide();
        }
    });

    $(document).ajaxComplete(function (event, req, settings) {
        if (req.getResponseHeader("sessionStatus") == "timeout") {
            window.location = "/login";
        }
    });

});
/*    $(document).ready(function () {
 $(".on_title").pin();
 });*/

