/**
 * Created by XiaoWei on 2014/11/19.
 */
var leftFlag="";
var finalUlr="http://localhost:8080/login";
$(function(){
    var _ul = $("div[class^='list']");
    _ul.each(function (i, o) {
        $(o).find("a").click(function () {
            _ul.each(function (i, o) {
                $(o).find("a").removeClass("active");
            });
            $(this).addClass("active");
            leftFlag=$(this).attr("step");
        });
    });

});