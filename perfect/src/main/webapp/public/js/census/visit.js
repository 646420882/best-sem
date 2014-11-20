/**
 * Created by XiaoWei on 2014/11/20.
 */
$(function () {
    $("#scroll").click(function(){
        if($(this).attr("class").indexOf("active")>-1){
            var tmp=$("#panel1");
            $("nav[class^='navbar']").removeClass("navbar-fixed-top");
            $(this).removeClass("active");
            $("#panel1").css({
                height:"75px",
                padding:"10px 15px"
            });
        }else{
            $("nav[class^='navbar']").addClass("navbar-fixed-top");
            $(this).addClass("active");
            $("#panel1").css({
                height:"1px",
                padding:"0"
            });
        }
    });

    var _lit=$("#nav1 li");
    _lit.each(function(i,o){
        $(o).click(function(){
            _lit.each(function(i,o){
                $(o).removeClass("active");
            });
            $(this).addClass("active");
        });
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
});
function Ts(){
    $("#filter").toggle();
}
function Test() {
    $("div[class^='alert']").toggle();
}
function dB(str){
    if(!str){
        $("input[name='db']").daterangepicker(null, function (start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
        });
    }

}

