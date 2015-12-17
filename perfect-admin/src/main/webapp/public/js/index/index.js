/**
 * Created by guochunyan on 2015/12/17.
 */
$(function () {
    var data = [{
        id: 1,
        name: 'baidu-perfect2151880',
        url: "http://www.perfect-cn.cn",
        platform: "百度",
        time: "2015年12月25日",
        action: "1",
        password:"3213612321"

    }, {
        id: 2,
        name: 'baidu-perfect2151880',
        url: "http://www.perfect-cn.cn",
        platform: "百度",
        time: "2015年12月25日",
        action: "1",
        password:"3213612321"
    }]
    var datas = [{
        id: 1,
        name: 'baidu-perfect2151880',
        remark: "http://www.perfect-cn.cn",
        wedName: "百度",
        wedUrl: "2015年12月25日",
        wedCode: "",
        action: "1",
        password:"3213612321"

    }, {
        id: 2,
        name: 'baidu-perfect2151880',
        remark: "http://www.perfect-cn.cn",
        wedName: "百度",
        wedUrl: "2015年12月25日",
        wedCode: "",
        action: "1",
        password:"3213612321"
    }]
    $('#userAdmin').bootstrapTable({
        data: data
    });
    $(window).resize(function () {
        $('#userAdmin').bootstrapTable('resetView');

    });
})
function disableFormatter(value, row, index) {
    return [
        '<a class="disable tab_operate" href="javascript:void(0)" title="禁用">',
        '禁用',
        '</a>'
    ].join('');
};
function LookUp(value, row, index) {
    return [
        '<a class="look tab_operate" href="javascript:void(0)" title="查看">',
        '查看',
        '</a>'
    ].join('');
}
function passwordFormatter (value, row, index) {
    return [
        "<span class='fl'>*******</span>"+
        '<a class="password_reset" href="javascript:void(0)" title="重置">',
        '重置',
        '</a>'
    ].join('')
}
