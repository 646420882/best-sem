/**
 * Created by XiaoWei on 2014/11/20.
 */
$(function () {
    $("#scroll").click(function(){
        if($(this).attr("class").indexOf("active")>-1){
            $("nav[class^='navbar']").removeClass("navbar-fixed-top");
            $(this).removeClass("active");

        }else{
            $("nav[class^='navbar']").addClass("navbar-fixed-top");
            $(this).addClass("active");
        }
    });

    var _li=$("#tab1 li")   ;
    _li.each(function(i,o){
        $(o).click(function(){
                _li.each(function(i,o){
                    $(o).removeClass("active");
                });
            $(this).addClass("active");
        });
    });

    var _li2=$("#tab2 li")   ;
    _li2.each(function(i,o){
        $(o).click(function(){
            _li2.each(function(i,o){
                $(o).removeClass("active");
            });
            $(this).addClass("active");
        });
    });
});