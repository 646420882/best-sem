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
    $('#logAdmin').bootstrapTable({
        data: data
    });
    $(window).resize(function () {
        $('#logAdmin').bootstrapTable('resetView');

    });
})