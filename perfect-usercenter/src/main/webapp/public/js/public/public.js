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
            $(".totalNav:nth-child(1) ").addClass("current")
        });
        document.title='百思-账户概览';
    } else if (href == "/account") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(2) ").addClass("current")
        });
        document.title='百思-账户绑定';
    }
    else if (href == "/password") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(3) ").addClass("current")
        });
        document.title='百思-密码管理';
    }
    else if(href == "/safetyTool") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(4) ").addClass("current")
        });
        document.title='百思-安全工具';
    }
})
