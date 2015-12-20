/**
 * Created by guochunyan on 2015/12/17.
 */

function Modify(_this) {
    var list = $("#userList").find("b");
    list.each(function () {
        var listMessage = $(this).html();
        console.log($(this).parent().first().find("span").html());

        $(this).html("<input type='text' class='form-control' style='display:inline-block;max-width:300px' value='" + listMessage + "'> ");

        $(_this).hide();
        $(_this).next().removeClass("hide");
    })
};
function Preservation(_this) {
    var list = $("#userList").find("input");
    list.each(function () {
        var listMessage = $(this).val();
        console.log(listMessage);
        $(this).replaceWith(listMessage);
        $(_this).parent().prev().show();
        $(_this).parent().addClass("hide");

    })
}
function Cancel(_this) {
    var list = $("#userList").find("input");
    list.each(function () {
            var listMessage = $(this).val();
            $(this).replaceWith(listMessage);
            $(_this).parent().prev().show();
            $(_this).parent().addClass("hide");
        }
    )
}

