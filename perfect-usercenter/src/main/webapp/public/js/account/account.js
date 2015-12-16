/**
 * Created by guochunyan on 2015/12/15.
 */
$(function () {
    var data = [{
        id: 1,
        name: 'baidu-perfect2151880',
        url: "http://www.perfect-cn.cn",
        platform: "百度",
        time: "2015年12月25日",
        action: "1"

    }, {
        id: 2,
        name: 'baidu-perfect2151880',
        url: "http://www.perfect-cn.cn",
        platform: "百度",
        time: "2015年12月25日",
        action: "1"
    }]
    var datas = [{
        id: 1,
        name: 'baidu-perfect2151880',
        remark: "http://www.perfect-cn.cn",
        wedName: "百度",
        wedUrl: "2015年12月25日",
        wedCode: "",
        action: "1"

    }, {
        id: 2,
        name: 'baidu-perfect2151880',
        remark: "http://www.perfect-cn.cn",
        wedName: "百度",
        wedUrl: "2015年12月25日",
        wedCode: "",
        action: "1"
    }]
    $('#account_table').bootstrapTable({
        data: data
    });
    $('#AccountTable').bootstrapTable({
        data: datas

    });
    $(window).resize(function () {
        $('#account_table').bootstrapTable('resetView');

    });
    /*   $('#AccountTable').on('click-row.bs.table', function (e, row, $element) {

     });*/
    $(window).resize(function () {
        $('#AccountTable').bootstrapTable('resetView');

    });
})

function operateFormatter(value, row, index) {
    return [
        '<a class="binding" href="javascript:void(0)" title="绑定">',
        '绑定',
        '</a>',
        '<a class="editor" href="javascript:void(0)" title="修改">',
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
        '<a class="editor" href="javascript:void(0)" title="修改">',
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
        if ($(this).html() == "禁用") {
            $('#modelbox').modal()
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
            editorBottom.prev().hide();
            editorBottom.next(".preserve").attr("style", "display:block");

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
        preserveHtML.prevAll().show();
    },
    'click .delete': function (e, value, row, index) {
        var tabledelete = $(this).parent().parent();
        $('#modelbox').modal();
        $("#modelboxTitle").html("是否删除？");
        $("#modelboxBottom").click(function () {
            $('#modelbox').modal('hide');
            tabledelete.remove();

        })
    }
};
var $table = $('#account_table'),
    $button = $('#Tablebutton');
$(function () {
    $button.click(function () {
        $table.bootstrapTable('append', firstAdd());
        $table.bootstrapTable('scrollTo', 'bottom');
    });
});

$(function () {
    $("#SecendTablebutton").click(function () {
        $('#AccountTable').bootstrapTable('append', secondAdd());
        $('#AccountTable').bootstrapTable('scrollTo', 'bottom');
    });
});
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
