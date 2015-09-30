/*******推广助手顶部导航**************/
$(document).ready(function () {
    $(".nav_menu").click(function () {
        if ($(".nav_menu ul").css("display") == "none") {//隐藏
            $(".nav_menu ul").show();
            $(".nav_menu ").mouseleave(function () {
                $(".nav_menu ul").css("display", "none");
            });
        }
        else {
            $(".nav_menu ul").hide();

        }
        $('.nav_menu ul li').click(function () {
            $(this).addClass('current').siblings().removeClass('current');
        });
    });
    $("#deletekeywords").click(function () {
        if ($("#deletekeywordes").css("display") == "none") {//隐藏
            $("#deletekeywordes").show();
            $("#deletekeywordes").mouseleave(function () {
                $("#deletekeywordes").css("display", "none");
            });
        }
        else {
            $("#deletekeywordes").hide();

        }

    });
});

/*******下载账户**************/
window.onload = function () {
    rDrag.init(document.getElementById('box1'));
    rDrag.init(document.getElementById('box2'));
    rDrag.init(document.getElementById('box3'));
    rDrag.init(document.getElementById('box4'));
    rDrag.init(document.getElementById('box5'));
    rDrag.init(document.getElementById('box6'));
    rDrag.init(document.getElementById('box7'));
    rDrag.init(document.getElementById('box8'));
    rDrag.init(document.getElementById('box9'));
    rDrag.init(document.getElementById('reachBudget_head'));
    rDrag.init(document.getElementById('downloadBox'));
    rDrag.init(document.getElementById('CheckCompletion'));


};
$(function () {
    $("#downloadAccountData").click(function () {
        loadExistsCampaign();
        loadNewCampaignData();
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $("#download").css({
            left: ($("body").width() - $("#download").width()) / 2 - 20 + "px",
            top: ($(window).height() - $("#download").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $("#download").css("display", "none");
    });
    rDrag.init(document.getElementById('changeCreative'));
    rDrag.init(document.getElementById('excludeIPChange'));
    rDrag.init(document.getElementById('budgetChange'));
    rDrag.init(document.getElementById('CampaignChange'));
    rDrag.init(document.getElementById('uploadHead'));
    rDrag.init(document.getElementById('RepeartChange'));
    rDrag.init(document.getElementById('GusuanChange'));
    rDrag.init(document.getElementById('setFdKeywordDiv'));
    rDrag.init(document.getElementById('setExtensionDiv'));
    rDrag.init(document.getElementById('setExcludeIpDiv'));

});

/*******查找重复关键词**************/

$(function () {
    $(".showbox3").click(function () {
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $(".box3").css({
            left: ($("body").width() - $(".box3").width()) / 2 - 20 + "px",
            top: ($(window).height() - $(".box3").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box3").css("display", "none");
    });
    $(".time_sl").click(function () {
    });
    $(".time_sl").click(function () {
        $(".time_select").show();
    });
});
/*******估算工具**************/
$(function () {
    $(".showbox4").click(function () {
        return;
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $(".box4").css({
            left: ($("body").width() - $(".box4").width()) / 2 - 20 + "px",
            top: ($(window).height() - $(".box4").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box4").css("display", "none");
    });
    $(".time_sl").click(function () {
    });
    $(".time_sl").click(function () {
        $(".time_select").show();
    });
});
/*******修改账户预算**************/
$(function () {
    $(".showbox5").click(function () {
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $(".box5").css({
            left: ($("body").width() - $(".box5").width()) / 2 - 20 + "px",
            top: ($(window).height() - $(".box5").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box5").css("display", "none");
    });
});
/*******IP排除**************/
$(function () {
    $(".showbox6").click(function () {
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $(".box6").css({
            left: ($("body").width() - $(".box6").width()) / 2 - 20 + "px",
            top: ($(window).height() - $(".box6").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box6").css("display", "none");
    });
});
/*******动态创意状态修改**************/
$(function () {
    $(".showbox7").click(function () {
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $(".box7").css({
            left: ($("body").width() - $(".box7").width()) / 2 - 20 + "px",
            top: ($(window).height() - $(".box7").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box7").css("display", "none");
    });
});
/*******弹窗切换*****/
$(document).ready(function () {
    $(".ms").click(function () {
        $(".zs_sets2").slideToggle();

    });

});
/*******顶部菜单****/
$(document).ready(function () {
    $('.zhushou_menu ul li').click(function () {
        $(this).addClass('current').siblings().removeClass('current');
    });
});
/*******二级菜单****/
$(document).ready(function () {
    var $tab_li = $('.zh_menu2 li');
    $('.zh_menu2 li').click(function () {
        $(this).addClass('current').siblings().removeClass('current');
        var index = $tab_li.index(this);
        $('div.zs_box > div').eq(index).show().siblings().hide();
        var xx = $(this).attr("cname");
        setTimeout(function () {
            resizable(xx)
        }, 400);
    });
});

/*******表格****/
$(function () {
    var items = $(".list4 table tbody tr ");
    items.each(function (i) {
        var t = $(this);
        t.click(function () {

            t.addClass("list2_box3");
            t.siblings().removeClass("list2_box3");
        });
    });
    $("#tbodyClick tr").click(function () {
        $("#tbodyClick tr").find("td").find("span").remove();
        $(this).find("td").append("<span class='editor'></span>");
    });
    $("#tbodyClick2 tr").click(function () {
        $("#tbodyClick2 tr").find("td").find("span").remove();
        $(this).find("td").append("<span class='editor'></span>");
    });
    $("#tbodyClick3 tr").click(function () {
        $("#tbodyClick3 tr").find("td").find("span").remove();
        $(this).find("td").append("<span class='editor'></span>");
    });
    $("#tbodyClick4 tr").click(function () {
        $("#tbodyClick4 tr").find("td").find("span").remove();
        $(this).find("td").append("<span class='editor'></span>");
    });
    $("#tbodyClick5 tr").click(function () {
        $("#tbodyClick5 tr").find("td").find("span").remove();
        $(this).find("td").append("<span class='editor'></span>");
    });

    //小功能窗口
    $('.editor').on("click", function (e) {
        if ($(".more_list").css("display") == "none") {
            $(".more_list").css({ top: e.pageY + -70 + "px", left: e.pageX });
            $(".more_list").show();
            $(".more_list ").mouseleave(function () {
                $(".more_list").css("display", "none");
            });
        }
        else {
            $(".more_list").hide();

        }
    });

    //弹窗切换
    $('.more_list ul li').click(function () {
        $(this).addClass('current').siblings().removeClass('current');
    });

    /*******附加创业顶部****/
    $('.cy_menu ul li').click(function () {
        $(this).addClass('current').siblings().removeClass('current');
    });

    /*******附加创意底部****/


    /*******账户下载设置****/
    var $tab_li = $('.zs_set li input');
    $('.zs_set li input').click(function () {
        $(this).addClass('current').siblings().removeClass('current');
        var index = $tab_li.index(this);
        $('div.zs_sets > div').eq(index).show().siblings().hide();
    });

    $(".pinned").pin()
    $("#jingjia_adds").click(function () {
        $("#jiangjia_add").hide();
    });
    $("#jiangjia_chongfu").click(function () {
        $("#jiangjia_add").fadeIn();
    })
    //编辑弹窗关闭方法
    $(".bottom_close").click(function () {
        $(".bottom_close").parent().parent().fadeOut("slow");
    })
    $(".zh_menu2").click(function () {
        $(".bottom_close").parent().parent().fadeOut("slow");
    })
    $(function () {
        //绑定需要拖拽改变大小的元素对象
        bindResize(document.getElementById('kkeyword'));
        bindResize(document.getElementById('tcreative'));
        bindResize(document.getElementById('aadgroup'));
        bindResize(document.getElementById('ccampaign'));
    });
    function bindResize(el) {
        var els = el.style,
            y = 0;
        $(".zhanghu_input").mousedown(function (e) {

            /*     x = e.clientX - el.offsetWidth,*/
            y = e.clientY - el.offsetHeight;
            el.setCapture ? (
                el.setCapture(),
                    el.onmousemove = function (ev) {
                        mouseMove(ev || event);
                    },
                    el.onmouseup = mouseUp
                ) : (
                $(document).bind("mousemove", mouseMove).bind("mouseup", mouseUp)
                );
            e.preventDefault();
        });
        function mouseMove(e) {
//            els.width = e.clientX - x + 'px',
            els.height = e.clientY - y + 'px';

        }

        function mouseUp() {
            el.releaseCapture ? (
                el.releaseCapture(),
                    el.onmousemove = el.onmouseup = null
                ) : (
                $(document).unbind("mousemove", mouseMove).unbind("mouseup", mouseUp)
                );
        }
    }

});
