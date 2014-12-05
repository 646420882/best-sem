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

    var _li = $("#tab1 li");
    _li.each(function(i,o){
        $(o).hover(function(){
                _li.each(function(i,o){
                    $(o).removeClass("active");
                });
            $(this).addClass("active");
        });
    });

    var _li2=$("#tab2 li")   ;
    _li2.each(function(i,o){
        $(o).hover(function(){
            _li2.each(function(i,o){
                $(o).removeClass("active");
                $("#filter li:eq(" + i + ")").css("display", "none");
            });
            $(this).addClass("active");
            $("#filter li:eq(" + i + ")").css("display", "block");
        });
    });

    $("tr[class^=child]").hide();
    var _child = $("tr[class^='child']")
    $(".parent").click(function () {
        var _p = $(this).find("td:eq(0)");
        if (_p.html() == "-") {
            _p.html("+");
        } else {
            _p.html("-");
        }
        $(this).siblings('.child_' + this.id).toggle();
    });



});
function Ts(){
    if ($("#filter").css("display") == "block") {
        $("#filter").css("display", "none");
    } else {
        $("#filter").css("display", "block");
    }
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
function chartToggle() {
    $("#filter-speak").toggle();
    $(".fold a").toggle();
    $("#chart_div").toggle();
}

