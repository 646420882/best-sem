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
    $('#account_table').bootstrapTable({
        data: data
    });
    $('#AccountTable').bootstrapTable({
        data: data

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
var $table = $('#account_table');
var $button = $('#Tablebutton');
$button.click(function () {
    var randomId = "<input type='text'>";
    $table.bootstrapTable('insertRow', {
        index: 1,
        row: {
            id: randomId,
            url: randomId,
            price: randomId
        }

    });
});
