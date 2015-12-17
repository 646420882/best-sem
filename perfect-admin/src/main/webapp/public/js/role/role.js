/**
 * Created by guochunyan on 2015/12/16.
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
    $('#roleAdmin').bootstrapTable({
        data: data
    });
    $(window).resize(function () {
        $('#roleAdmin').bootstrapTable('resetView');

    });
})
function disableFormatter(value, row, index) {
    return [
        '<a class="editor tab_operate" href="javascript:void(0)" title="修改">',
        '修改',
        '</a>',
        '<button class="preserve btn btn-primary" style="display:none" href="javascript:void(0)" title="保存">',
        '保存',
        '</button>',
        '<a class="delete tab_operate ml10" href="javascript:void(0)" title="删除" >',
        '删除',
        '</a>'

    ].join('');
}
