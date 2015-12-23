/**
 * Created by guochunyan on 2015/12/17.
 */
$(function () {
    $.ajax({
        url: "/user",
        dataType: "json",
        async: true,
        success: function (user) {
            var userInfo = user.data
            var userModules = "";
            userInfo.systemUserModules.forEach(function (item, e) {
                if (userInfo.systemUserModules.length > 1) {
                    userModules = userModules + item.moduleName + ","
                } else {
                    userModules = userModules + item.moduleName
                }
            });
            $("#userList").empty();
            var html = "<li><span>公司名称：</span><b cname='companyName'>" + userInfo.companyName + "</b></li>" +
                "<li><span>开通平台：</span>" + userModules + "</li>" +
                /*"<li><span>网站名称：</span><b>帐号信息需要此信息？&nbsp;</b></li>" +
                "<li><span>网址：</span><b>帐号信息需要此信息？&nbsp;</b></li>" +*/
                "<li><span>注册时间：</span>" + userInfo.ctime + "</li>" +
                "<li><span>联系人：</span><b cname='contactName'>" + userInfo.contactName + "&nbsp;</b></li>" +
                "<li><span>办公电话：</span><b cname='telephone'>" + userInfo.telephone + "&nbsp;</b></li>" +
                "<li><span>移动电话：</span><b cname='mobilephone'>" + userInfo.mobilephone + "&nbsp;</b></li>" +
                "<li><span>通讯地址：</span><b cname='address'>" + userInfo.address + "&nbsp;</b></li>" +
                "<li><span>电子邮箱：</span>" + userInfo.email + "</li>";
            $("#userList").append(html);
        }
    });
    $("body").on("click","#save",function(){
        var regxm = /^(13[0-9]{9})|(15[89][0-9]{8})$/;
        if(!regxm.test($("#userList input[cname=mobilephone]").val().trim())){
            alert("请输入正确的移动电话！");
            return
        }
        var dto = {};
        dto["companyName"] = $("#userList input[cname=companyName]").val().trim();
        dto["contactName"] = $("#userList input[cname=contactName]").val().trim();
        dto["telephone"] = $("#userList input[cname=telephone]").val().trim();
        dto["address"] = $("#userList input[cname=address]").val().trim();
        dto["mobilephone"] = $("#userList input[cname=mobilephone]").val().trim();

        $.ajax({
            url: "/user/"+$("#userid").val(),
            type: "post",
            data:dto,
            cache:false,
            dataType: "json",
            success: function (user) {
                location.href ="/"
            }
        });
    })
});


function Modify(_this) {
    var list = $("#userList").find("b");
    list.each(function () {
        var listMessage = $(this).html();
        var cname = $(this).attr("cname");
        if (cname != undefined) {
            $(this).html("<input type='text' cname='" + cname + "' class='form-control' style='display:inline-block;max-width:300px' value='" + listMessage + "'> ");
        } else {
            $(this).html("<input type='text' class='form-control' style='display:inline-block;max-width:300px' value='" + listMessage + "'> ");
        }

        $(_this).hide();
        $(_this).next().removeClass("hide");
    })
};
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

