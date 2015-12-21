/**
 * Created by guochunyan on 2015/12/15.
 */
$(function () {
    var data = [{
        id: 1,
        name: 'baidu-perfect2151880',
        password: '123455',
        url: "http://www.perfect-cn.cn",
        platform: "百思搜客",
        time: "2015年12月25日",
        action: "1"

    }, {
        id: 2,
        name: 'baidu-perfect2151880',
        password: '123455',
        url: "http://www.perfect-cn.cn",
        platform: "百思搜客",
        time: "2015年12月25日",
        action: "1"
    }];
    var datas = [{
        id: 1,
        name: 'baidu-perfect2151880',
        password: '123455',
        remark: "http://www.perfect-cn.cn",
        platform: "百思慧眼",
        webName: "百度",
        webUrl: "http://localhost:8088/account",
        webCode: "http://localhost:8088/account",
        time: "2015年12月25日",
        action: "1"

    }, {
        id: 2,
        name: 'baidu-perfect2151880',
        password: '123455',
        remark: "http://www.perfect-cn.cn",
        platform: "百思慧眼",
        webUrl: "http://localhost:8088/account",
        webName: "百度",
        webCode: "http://localhost:8088/account",
        time: "2015年12月25日",
        action: "1"
    }];
    $('#account_table').bootstrapTable({
        data: data
    });
    $('#AccountTable').bootstrapTable({
        data: datas

    });
    $(window).resize(function () {
        $('#account_table').bootstrapTable('resetView');

    });
    //$('#AccountTable').on('click-row.bs.table', function (e, row, $element) {
    //
    //});
    $(window).resize(function () {
        $('#AccountTable').bootstrapTable('resetView');

    });
});

function operateFormatter(value, row, index) {
    return [
        '<a class="binding" href="javascript:void(0)" title="绑定">',
        '绑定',
        '</a>',
        '<a class="editor suoke" href="javascript:void(0)" title="修改">',
        '修改',
        '</a>',
        '<button class="preserve btn btn-primary" style="display:none" href="javascript:void(0)" title="保存">',
        '保存',
        '</button>',
        '<button class="preserve newadd btn btn-primary" style="display:none" href="javascript:void(0)" title="新增">',
        '新增',
        '</button>',
        '<a class="delete ml10" href="javascript:void(0)" title="删除" >',
        '删除',
        '</a>'

    ].join('');
}
function disableFormatter(value, row, index) {
    return [
        '<a class="disable" href="javascript:void(0)" title="禁用">',
        '禁用',
        '</a>',
        '<a class="editor huiyan" href="javascript:void(0)" title="修改">',
        '修改',
        '</a>',
        '<button class="preserve btn btn-primary" style="display:none" href="javascript:void(0)" title="保存">',
        '保存',
        '</button>',
        '<button class="preserve newadd btn btn-primary" style="display:none" href="javascript:void(0)" title="新增">',
        '新增',
        '</button>',
        '<a class="delete ml10" href="javascript:void(0)" title="删除" >',
        '删除',
        '</a>'

    ].join('');
}
window.operateEvents = {
    'click .binding': function (e, value, row, index) {
        var platform = row.platform;
        if (platform == "百思搜客") {
            alert(platform);
            var bindingtext = $(this);
            if ($(this).html() == "绑定") {
                $('#modelbox').modal();
                $("#modelboxTitle").html("系统提示");
                $(".modal-body").html("是否绑定？");
                $("#modelboxBottom").click(function () {
                    //
                    $('#modelbox').modal('hide');
                    bindingtext.html("取消绑定");
                })

            } else {
                $('#modelbox').modal();
                $("#modelboxTitle").html("系统提示");
                $(".modal-body").html("是否取消绑定？");
                $("#modelboxBottom").click(function () {
                    //
                    $('#modelbox').modal('hide');
                    bindingtext.html("绑定");
                })

            }
        } else if (platform == "百思慧眼") {
            alert(platform);
            var bindingtext = $(this);
            if ($(this).html() == "绑定") {
                $('#modelbox').modal();
                $("#modelboxTitle").html("系统提示");
                $(".modal-body").html("是否绑定？");
                $("#modelboxBottom").click(function () {
                    //
                    $('#modelbox').modal('hide');
                    bindingtext.html("取消绑定");
                })

            } else {
                $('#modelbox').modal();
                $("#modelboxTitle").html("系统提示");
                $(".modal-body").html("是否取消绑定？");
                $("#modelboxBottom").click(function () {
                    //
                    $('#modelbox').modal('hide');
                    bindingtext.html("绑定");
                })

            }
        }
    },
    'click .disable': function (e, value, row, index) {
        var platform = row.platform;

        var bindingtext = $(this);
        if ($(this).html() == "禁用") {
            $('#modelbox').modal();
            $("#modelboxTitle").html("系统提示");
            $(".modal-body").html("是否禁用？");
            $("#modelboxBottom").click(function () {
                $('#modelbox').modal('hide');
                bindingtext.html("启动");
            })

        } else {
            $("#modelboxTitle").html("系统提示");
            $(".modal-body").html("是否启动？");
            $('#modelbox').modal();
            $("#modelboxBottom").click(function () {
                $('#modelbox').modal('hide');
                bindingtext.html("禁用");
            })

        }
    },
    'click .suoke': function (e, value, row, index) {
        var platform = row.platform;

        $('#modelbox').modal();
        $('.modal-dialog').css({width: '400px'});
        $("#modelboxTitle").html("修改关联账户");
        $(".modal-body").html("<ul class='account_change'>" +
            "<li>关联账户名：</li>" +
            "<li>账户密码：</li>" +
            "<li>备注名：</li>" +
            "<li>URL地址：</li>" +
            "<li>账户所属平台：</li></ul>");
        var editorBottom = $(this);
        var that = $(this).parent().prevAll("td");
        var that_value = that.each(function (index) {
            var that_html = $(this).html();
            if (index == 4) {
                var acount_html = "<input type='password' class='form-control' value='" + that_html + "'> "
            } else if (index == 1) {
                var that_html = "百思搜客";
                var acount_html = "<input type='text' class='form-control' readonly value='" + that_html + "'> "
            } else {
                var acount_html = "<input type='text' class='form-control' value='" + that_html + "'> "
            }
            if (index > 0) {
                $(".account_change").find('li').eq(5 - index).append(acount_html);
            }
        });
    },
    'click .huiyan': function (e, value, row, index) {
        var platform = row.platform;

        $('#modelbox').modal();
        $('.modal-dialog').css({width: '400px'});
        $("#modelboxTitle").html("修改关联账户");
        $(".modal-body").html("<ul class='account_change'>" +
            "<li>关联账户名：</li>" +
            "<li>账户密码：</li>" +
            "<li>备注名：</li>" +
            "<li>URL地址：</li>" +
            "<li>账户所属平台：</li>" +
            "<li>统计代码：</li>" +
            "<li>网站名称：</li></ul>");
        var editorBottom = $(this);
        var that = $(this).parent().prevAll("td");
        var that_value = that.each(function (index) {
            var that_html = $(this).html();
            if (index == 6) {
                var acount_html = "<input type='password' class='form-control' value='" + that_html + "'> "
            } else if (index == 3) {
                var that_html = "百思慧眼";
                var acount_html = "<input type='text'  class='form-control' readonly value='" + that_html + "'> "
            }
            else {
                var acount_html = "<input type='text' class='form-control' value='" + that_html + "'> "
            }
            $(".account_change").find('li').eq(7 - index).append(acount_html);
        });
    },
    'click .preserve': function (e, value, row, index) {
        var platform = row.platform;

        var preserveHtML = $(this);
        var preserveThat = $(this).parent().prevAll("td");
        preserveThat.each(function () {
            var that_html = $(this).find("input").val();
            $(this).html(that_html);
        });
        preserveHtML.attr("style", "display:none");
        preserveHtML.prevAll().show();
    },
    'click .delete': function (e, value, row, index) {
        var platform = row.platform;

        var tabledelete = $(this).parent().parent();
        $('#modelbox').modal();
        $("#modelboxTitle").html("系统提示");
        $(".modal-body").html("是否删除？");
        $("#modelboxBottom").click(function () {
            $('#modelbox').modal('hide');
            tabledelete.remove();
        })
    }
};
var $table = $('#account_table'), $button = $('#Tablebutton');

$(function () {
    $button.click(function () {
        var acountContent = "<ul class='account_add'>" +
            "<li>关联账户名：<input class='form-control ' type='text'></li>" +
            "<li>账户密码：<input class='form-control ' type='text'></li>" +
            "<li>备注名：<input class='form-control ' type='text'></li>" +
            "<li>URL地址：<input class='form-control ' type='text'></li>" +
            "<li>账户所属平台：<input class='form-control ' readonly value='百思搜客' type='text'></li></ul>";
        $('#modelbox').modal();
        $('.modal-dialog').css({width: '400px'});
        $("#modelboxTitle").html("新增关联账户");
        $(".modal-body").html(acountContent);
    });
});

$(function () {
    $("#SecendTablebutton").click(function () {
        var acountContent = "<ul class='account_add'>" +
            "<li>关联账户名：<input class='form-control ' type='text'></li>" +
            "<li>账户密码：<input class='form-control ' type='text'></li>" +
            "<li>备注名：<input class='form-control ' type='text'></li>" +
            "<li>URL地址：<input class='form-control ' type='text'></li>" +
            "<li>统计代码：<input class='form-control '  type='text'></li>" +
            "<li>网站名称：<input class='form-control '  type='text'></li>" +
            "<li>账户所属平台：<input class='form-control ' readonly value='百思慧眼' type='text'></li></ul>";
        $('#modelbox').modal();
        $('.modal-dialog').css({width: '400px'});
        $("#modelboxTitle").html("新增关联账户");
        $(".modal-body").html(acountContent);
    });
});

