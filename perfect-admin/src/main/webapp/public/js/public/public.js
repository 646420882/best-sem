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
            $(".totalNav:nth-child(1) ").addClass("current");
        })
    } else if (href == "/role") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(2) ").addClass("current");
        })
    }
    else if (href == "/system") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(3) ").addClass("current");
        })
    }
    else if (href == "/jurisdiction") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(4) ").addClass("current");
        })
    }
    else if(href == "/log") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(5) ").addClass("current");
        })
    }
    //表格公用
    window.operateEvents = {
        'click .binding': function (e, value, row, index) {
            var bindingtext = $(this);
            if ($(this).html() == "绑定") {
                $('#modelbox').modal()
                $("#modelboxTitle").html("是否绑定？");
                $("#modelboxBottom").click(function () {
                    $('#modelbox').modal('hide');
                    bindingtext.html("取消绑定");
                })

            } else {
                $("#modelboxTitle").html("是否取消绑定？");
                $('#modelbox').modal()
                $("#modelboxBottom").click(function () {
                    $('#modelbox').modal('hide');
                    bindingtext.html("绑定");
                })

            }
        },
        'click .disable': function (e, value, row, index) {
            var bindingtext = $(this);
            console.log(bindingtext);
            if ($(this).html() == "禁用") {
                $('#modelbox').modal();
                $("#modelboxTitle").html("是否禁用？");
                $("#modelboxBottom").click(function () {
                    $('#modelbox').modal('hide');
                    bindingtext.html("启动");
                })

            } else {
                $("#modelboxTitle").html("是否启动？");
                $('#modelbox').modal()
                $("#modelboxBottom").click(function () {
                    $('#modelbox').modal('hide');
                    bindingtext.html("禁用");
                })

            }
        },
        'click .editor': function (e, value, row, index) {
            var editorBottom = $(this);
            var that = $(this).parent().prevAll("td");
            var that_value = that.each(function () {
                var that_html = $(this).html();
                $(this).html("<input type='text' class='form-control' value='" + that_html + "'> ");
                editorBottom.hide();
                editorBottom.next(".preserve").attr("style", "display:block");
                editorBottom.next().next(".cancel").attr("style", "display:block");
                editorBottom.next().next().next(".delete").attr("style", "display:none");
            });
        },
        'click .preserve': function (e, value, row, index) {
            var preserveHtML = $(this);
            var preserveThat = $(this).parent().prevAll("td");
            preserveThat.each(function () {
                var that_html = $(this).find("input").val();
                $(this).html(that_html);
            });
            preserveHtML.attr("style", "display:none");
            preserveHtML.next(".cancel").attr("style", "display:none");
            preserveHtML.next().next(".delete").attr("style", "display:block");
            preserveHtML.prevAll().show();
        },
        'click .cancel': function (e, value, row, index) {
            var preserveHtML = $(this);
            var preserveThat = $(this).parent().prevAll("td");
            preserveThat.each(function () {
                var that_html = $(this).find("input").val();
                $(this).html(that_html);
            });
            preserveHtML.attr("style", "display:none");
            preserveHtML.next(".delete").attr("style", "display:block");
            preserveHtML.prev().prev(".editor").attr("style", "display:block");
            preserveHtML.prev(".preserve").attr("style", "display:none");
        },
        'click .delete': function (e, value, row, index) {
            var tabledelete = $(this).parent().parent();
            $('#modelbox').modal();
            $("#modelboxTitle").html("是否删除？");
            $("#modelboxBottom").click(function () {
                $('#modelbox').modal('hide');
                tabledelete.remove();

            })
        },
        'click .look': function (e, value, row, index) {
            $(".indexCret").css({'display':'none'});
            $("#userLookUpWrap").css({"display":"block","top": 221+index*45+"px"});
            $(this).next().css("display",'block')
            var lookUpData = [{
                id: 1,
                systemModal: '百思搜客',
                userProperty: "使用账户",
                openStates: "正常使用",
                startDate: "2015-09-20",
                endDate: "2015-09-20",
                authorityAssignment: "设置",
                relatedAccount:["凤巢账户1","凤巢账户2"],
                relatedAccountPwd:"123123",
                APICode:"(运营人员双击填写)",
                URLAddress:"www.perfect-cn.cn",
                statisticalCode:"(运营人员双击填写)"

            }, {
                id: 2,
                systemModal: '百思慧眼',
                userProperty: "使用账户",
                openStates: "正常使用",
                startDate: "2015-09-20",
                endDate: "2015-09-20",
                authorityAssignment: "设置",
                relatedAccount:"凤巢账户1",
                relatedAccountPwd:"123123",
                APICode:"(运营人员双击填写)",
                URLAddress:"www.perfect-cn.cn",
                statisticalCode:"(运营人员双击填写)"
            }];
            $('#userLookUpTable').bootstrapTable({
                data: lookUpData
            });
        },
        'click .password_reset': function (e, value, row, index) {
            $('#modelbox').modal();
        }
    };
})
function firstAdd() {
    var startId = "<input type='text' class='form-control'>",
        rows = [];
    rows.push({
        id: startId,
        name: startId,
        remark: startId,
        url: startId,
        platform: startId,
        time: startId
    });
    return rows;
}
function secondAdd() {
    var startId = "<input type='text' class='form-control'>",
        rows = [];
    rows.push({
        id: startId,
        name: startId,
        remark: startId,
        wedName: startId,
        wedUrl: startId,
        wedCode: startId
    });
    return rows;
}
function queryParams() {
    return {
        type: 'owner',
        sort: 'updated',
        direction: 'desc',
        per_page: 100,
        page: 1
    };
}