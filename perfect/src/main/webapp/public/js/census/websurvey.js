/**
 * Created by XiaoWei on 2014/11/19.
 */
$(function(){


    $("a[href='toggle']").click(function () {
        if($(this).html()=="+"){
            $(this).html("-");
        }else{
            $(this).html("+");
        }
        var _this = $(this);
        $("div[id^='dir']").toggle();
        return false;
    });

    /*获取今日数据*/
    $.get("/pftstis/getTodayConstants",{url:finalUlr},function(res){
    });

});

function Test() {
    $("#alert").toggle();
}