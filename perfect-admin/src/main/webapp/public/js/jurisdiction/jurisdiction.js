/**
 * Created by guochunyan on 2015/12/16.
 */
/*权限模块中的全选*/
function checkboxCheck(obj,index,allId){
    var isFlag = true;/*判断全选按钮是否选中*/
    var isFlag1 = true;/*判断全选按钮是否选中*/
    var isFlag2 = true;/*判断全选按钮是否选中*/
    if(index == 1){/*点击1级全选*/
        if ($(obj).prop('checked') == false) {
            $("#" + allId).find("input").each(function () {
                $(this).prop('checked', false);
            })
        } else {
            $("#" + allId).find("input").each(function () {
                $(this).prop('checked', true);
            })
        }
    }else if(index == 2){/*点击2级全选*/
        if ($(obj).prop('checked') == false) {
            $("#" + allId).find("input").each(function () {
                $(this).prop('checked', false);
            })
            $("#adminModuleAll").find("input").prop('checked', false);
        } else {
            $("#" + allId).find("input").each(function () {
                $(this).prop('checked', true);
            })
            if($("#adminModuleWeb").find("input").prop('checked') == true && $("#adminModuleReport").find("input").prop('checked') == true){
                $("#adminModuleAll").find("input").prop('checked',true);
            }
        }
    }else if(index == 3){
        if ($(obj).prop('checked') == false) {
            $(obj).parent().parent().parent().find("input").each(function () {
                $(this).prop('checked', false);
            })
            $(obj).parent().parent().parent().parent().siblings().find("input").prop('checked', false);
            $("#adminModuleAll").find("input").prop('checked', false);
        } else {
            $(obj).parent().parent().siblings().find("input").each(function () {
                $(this).prop('checked', true);
            })
            $(obj).parent().parent().parent().parent().find("input").each(function (i) {
                if ($(this).prop('checked') == false) {
                    isFlag = false;
                    return;
                }
            });
            if (isFlag == true) {
                $(obj).parent().parent().parent().parent().prev().find("input").prop('checked', true);
                if($("#adminModuleWeb").find("input").prop('checked') == true){
                    $("#adminModuleAll").find("input").prop('checked',true);
                }
            }
        }
    }else if(index == 4){
        if ($(obj).prop('checked') == false) {
                $(obj).parent().parent().parent().prev().find("input").prop('checked', false);
            $("#adminModuleAll").find("input").prop('checked',false);
        } else {
            $(obj).parent().parent().parent().find("input").each(function () {
                if ($(this).prop('checked') == false) {
                    isFlag = false;
                    return;
                }
            })
            if (isFlag == true) {
                $(obj).parent().parent().parent().prev().find("input").prop('checked', true);
                if($("#adminModuleReport").find("input").prop('checked') == true){
                    $("#adminModuleAll").find("input").prop('checked',true);
                }
            }
        }
    }else if(index == 5){
        if ($(obj).prop('checked') == false) {
            $(obj).parent().parent().parent().find("input").eq(0).prop('checked', false);
            $(obj).parent().parent().parent().parent().prev().find("input").prop('checked', false);
            $("#adminModuleAll").find("input").prop('checked', false);
        }else{
            $(obj).parent().parent().parent().find("input").each(function(i){
                if(i > 0){
                    if ($(this).prop('checked') == false) {
                        isFlag = false;
                        return;
                    }
                }
            })
            if(isFlag == true){
                $(obj).parent().parent().parent().find("input").eq(0).prop('checked', true);
                $(obj).parent().parent().parent().parent().find("input").each(function(i){
                    if ($(this).prop('checked') == false) {
                        isFlag1 = false;
                        return;
                    }
                })
                if(isFlag1){
                    $("#adminModuleReport").find("input").prop('checked',true);
                    $("#adminModuleWeb").find("input").each(function(i){
                        if ($(this).prop('checked') == false) {
                            isFlag2 = false;
                            return;
                        }
                    })
                    if(isFlag2 == true){
                        $("#adminModuleAll").find("input").each(function () {
                            $(this).prop('checked', true);
                        })
                    }
                }
            }
        }
    }
}