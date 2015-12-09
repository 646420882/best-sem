/**
 * Created by john on 2015/12/4.
 */
function onClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.checkNode(treeNode, !treeNode.checked, null, true);
    return false;
}
function promotionUnitLevel() {
    $("#UnitLevel").show();
    $("#PlanLevel").hide();
}
function promotionPlanLevel() {
    $("#PlanLevel").show();
    $("#UnitLevel").hide();
}
function onCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getCheckedNodes(true),
        v = "";
    for (var i = 0, l = nodes.length; i < l; i++) {
        v += nodes[i].name + ",";
    }
    if (v.length > 0) v = v.substring(0, v.length - 1);
    var cityObj = $("#citySel");
    cityObj.attr("value", v);
}
$(function () {
    window.dialog = dialog;
    rDrag.init(document.getElementById('AccountChange'));
});
function downloadUser() {
    $(".TB_overlayBG").css({
        display: "block"
    });
    $("#open_account").css({
        left: ($("body").width() - $("#open_account").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#open_account").height()) / 2 + $(window).scrollTop() + "px",
        display: "block"
    });

    $("#head_close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $("#open_account").css("display", "none");
    });
    $("#account_close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $("#open_account").css("display", "none");
    });


}
NavCss();
$(window).resize(function () {
    NavCss();
});
function NavCss() {
    if ($(document.body).height() < 500) {
        $(".nav").css("position", "absolute");
    }
    else {
        $(".nav").css("position", "fixed");
    }
}

/*function downloadUser() {
 var d = top.dialog({
 id: 'my2',
 content: "<iframe src='/homePage/showCountDownload' width='540' height='340' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
 title: "账户下载",
 yesClose: "取消",
 skin: 'downPopup',
 time: 1000,
 //            className:'succeed noClose',

 //            url:'homePage/pageBlock/showcountDownload',

 //            content: "<iframe src='/assistantKeyword/showTimingPauseDialo+g' width='550'  height='300' marginwidth='200' marginheight='0' scrolling='no' frameborder='0'></iframe>",
 //            content: "<div style='width: 550px; height: 470px;'><span>选择账户</span>  <div class='j_list01 over'><ul id='treeDemo' class='ztree'></ul></div></div>",
 oniframeload: function () {
 },
 onclose: function () {

 },
 onremove: function () {
 }
 }).showModal(dockObj);

 console.log(top.dialog());
 }*/

var dockObj = document.getElementById('argDialogDiv');
$("#downloadUser").click(function () {
    downloadUser();
});
function cancelKeyWord() {
    $("#choicedKeyWord").css({'display': "none"});
    $("#confirmAddKeyWord").attr({'disabled': 'disabled'});
    $("#confirmAddKeyWord").css({"background": "#ccc", "cursor": 'not-allowed'})
}
function imageChange(obj) {
    var fileFormat = "jpg,jpeg,png,gif,bmp";
    var path = $(obj).val();
    var index = $(obj).attr('fileindex');
    var fileName = getFileName(path);
    var fileExtLowerCase = (/[.]/.exec(fileName)) ? /[^.]+$/.exec(fileName.toLowerCase()) : '';//文件后缀
    if (fileFormat.indexOf(fileExtLowerCase) >= 0) {
        ShowImage(obj, index, 72, 72);
    } else {
//            alert('请选择图片,格式（*.jpg|*.jpeg|*.png|*.gif|*.bmp）');
        AlertPrompt.show('请选择图片,格式（*.jpg|*.jpeg|*.png|*.gif|*.bmp）');
        $(obj).val('');
//            alert($("#imgHeadPhoto" + index).get(0).src);
        AlertPrompt.show($("#imgHeadPhoto" + index).get(0).src);
        $("#imgHeadPhoto" + index).get(0).src = '';
    }
}

function getFileName(obj) {
    var pos = obj.lastIndexOf("\\") * 1;
    return obj.substring(pos + 1);
}
//-->

var uploadUserImg = function () {
    document.getElementById("userImg").submit();
};

var basePath = "<%=basePath%>";

var callback = function (data) {
    if (data == "true") {
        $(".TB_overlayBG").css("display", "none");
        $("#head_img").css("display", "none");
//            alert("上传成功!");
        AlertPrompt.show("上传成功!");
        window.location.reload(true);
    }
    else
//            alert("上传失败!");
        AlertPrompt.show("上传失败!");
};

//时间提示
var now = new Date(), hour = now.getHours();
var time = document.getElementById('time');
if (0 < hour && hour < 6) {
    time.innerHTML = "凌晨,好！"
}
else if (hour < 9) {
    time.innerHTML = "早上,好！"
}
else if (hour < 12) {
    time.innerHTML = "上午,好！"
}
else if (hour < 14) {
    time.innerHTML = "中午,好！"
}
else if (hour < 18) {
    time.innerHTML = "下午,好！"
}
else if (hour < 23) {
    time.innerHTML = "晚上,好！"
}
else if (hour == 24) {
    time.innerHTML = "凌晨,好！"
}
else {
    time.innerHTML = "晚上,好！"
}
//弹窗
/*智能竞价中的alert提示*/
var AlertPrompt = {
    show: function (content) {
        $("#AlertPrompt_title").html(content);
        $(".TB_overlayBG_alert").css({
            display: "block", height: $(document).height()
        });
        /*蒙版显示*/
        $("#AlertPrompt").css({
            top: ($(window).height() - $("#AlertPrompt").height()) / 2 + $(window).scrollTop() + "px",
            left: ($("body").width() - $("#AlertPrompt").width()) / 2 - 20 + "px",
            display: "block"
        });
        /*显示提示DIV*/
    },
    hide: function () {
        $(".TB_overlayBG_alert").css({
            display: "none"
        });
        /*蒙版显示*/
        $("#AlertPrompt").css({
            display: "none"
        });
        /*显示提示DIV*/
    }
}
/*智能竞价中的alert提示*/
var PromptBox = {
    show: function (title, content, sure) {
        $("#PrompBoxTitle").html(title);
        $("#PrompMain").html(content);
        $(".TB_overlayBG_alert").css({
            display: "block", height: $(document).height()
        });
        /*蒙版显示*/
        $("#PromptBox").css({
            top: ($(window).height() - $("#PromptBox").height()) / 2 + $(window).scrollTop() + "px",
            left: ($("body").width() - $("#PromptBox").width()) / 2 - 20 + "px",
            display: "block"
        });
        /*显示提示DIV*/
        if (sure == 1) {
            $("#promptBottom").empty();
            $("#promptBottom").append("" +
                "<li class='current' onclick='PromptBox.hide()'>确认</li>")
        } else if (sure == 2) {
            $("#promptBottom").empty();
            $("#promptBottom").append("" +
                "<li onclick='PromptBox.hide()'>取消</li>")
        } else {
            $("#promptBottom").empty();
            $("#promptBottom").append("" +
                "<li class='current' onclick='PromptBox.hide()'>确认</li>" +
                "<li onclick='PromptBox.hide()'>取消</li>")
        }

    },
    hide: function () {
        $(".TB_overlayBG_alert").css({
            display: "none"
        });
        /*蒙版显示*/
        $("#PromptBox").css({
            display: "none"
        });
        /*显示提示DIV*/
    }
}
$(function () {
    rDrag.init(document.getElementById('head_top'));
    rDrag.init(document.getElementById('AlertPrompTitle'));
    rDrag.init(document.getElementById('findOrReplaceH'));
    rDrag.init(document.getElementById('changeCreative'));
    rDrag.init(document.getElementById('excludeIPChange'));
    rDrag.init(document.getElementById('budgetChange'));
    rDrag.init(document.getElementById('batchDel'));
    rDrag.init(document.getElementById('reportAddSearchWordTitile'));
    rDrag.init(document.getElementById('reportAddNoSearchWordTitile'));
    rDrag.init(document.getElementById('PrompTitleBox'));
});
$(function () {
    $("#head_click").click(function () {
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $("#head_img").css({
            left: ($("body").width() - $("#head_img").width()) / 2 - 20 + "px",
            top: ($(window).height() - $("#head_img").height()) / 2 + ($(window).scrollTop() - 153) + "px",
            display: "block"
        });
    });
    $(".close2").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $("#head_img").css("display", "none");
    });
});

/***account 账户弹框分页*****/
$(document).ready(function () {
    $.getJSON("/account/getAllBaiduAccount",
        {},
        function (data) {
//                    var tags = [];
//                    $.each(results, function (i, item) {
//                        var _item = item.baiduRemarkName;
//                        console.log(_item)
//                        tags.push(_item);
//                    });
            var a = data.rows.length;
            var b = 6;
            var c = Math.ceil(a / b);
            if (a == 1) {
                $("#switchAccount_ul_pages").hide()
            } else {
                for (var i = 1; i <= c; i++) {
                    if (i == 1) {
                        $("<div class='page_li page_li_hover '>" + i + "</div>").appendTo($('.page_ul'));
                    } else {
                        $("<div class='page_li '>" + i + "</div>").appendTo($('.page_ul'));
                    }
                }
            }

            var g = $('.switchAccount_ul li');
            g.hide();
            g.slice(0, b).show();
            $('.page_li ').click(function () {
                g.hide();
                $('.page_li').removeClass("page_li_hover");
                $(this).addClass("page_li_hover");
                var e = $(this).text() * b;
                g.slice(e - b, e).show();
            });

            //搜索
            var results = data.rows;
            var tags = [];
            if (results != null && results.length > 0) {
                var index = results[0].accountName.length;
                $.each(results, function (i, item) {
                    var _item = item.remarkName;
                    if (_item == undefined) _item = item.accountName.substring(0, (i > 0 ? index - 3 : index)) + (item.accountName.length > index ? "..." : "");
                    tags.push(
                        {
                            label: _item,
                            id: item.accountId
                        }
                    );

                });
            }

            $("#searchCount").autocomplete({
                source: tags,

                select: function (event, ui) {
                    // 这里的this指向当前输入框的DOM元素
                    // event参数是事件对象
                    // ui对象只有一个item属性，对应数据源中被选中的对象
                    $(this).value = ui.item.label;
                    $("#searchCount").val(ui.item.label);
                    $("#searchCount").attr('card', ui.item.id);
                    $('.user_name span').html(ui.item.label);
                    var _accountId = ui.item.id;
                    $('#switchAccount').hide();
                    $.ajax({
                        url: '/account/switchAccount',
                        type: 'POST',
                        async: false,
                        dataType: 'json',
                        data: {
                            "accountId": _accountId
                        },
                        success: function (data, textStatus, jqXHR) {
                            if (data.status != null && data.status == true) {
                                //location.replace(location.href);
                                window.location.reload(true);
                            }
                        }
                    })

                    // 必须阻止默认行为，因为autocomplete默认会把ui.item.value设为输入框的value值
                    event.preventDefault();

                }

            });
            $('#searchCount').bind('input propertychange', function () {
                setTimeout(function () {
                    if ($("#searchCount").val() == "") {
                        $("#searchCount").siblings().show()
                        $("#switchAccount").height($(".countname").height() + 44);
//                            $("#switchAccount").height($(window).height());
                    } else {
                        $("#searchCount").siblings().hide()
                        $("#switchAccount").height($(".ui-autocomplete").height() + 44);
                    }
                }, 1000);
            });

        })
});

var ajaxbg = $("#background,#progressBar");
ajaxbg.hide();