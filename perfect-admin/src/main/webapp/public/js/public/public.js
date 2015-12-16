/**
 * Created by guochunyan on 2015/12/14.
 */
$(function () {
    //路由控制
    var href = window.location.href;
    href = '/' + href.split("/").slice(-1);
    if (href == "/") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(1) ").addClass("current");
        })
    } else if (href == "/role") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(2) ").addClass("current");
        })
    }
    else if (href == "/system") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(3) ").addClass("current");
        })
    }
    else if (href == "/jurisdiction") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(4) ").addClass("current");
        })
    }
    else {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(5) ").addClass("current");
        })
    }
})
