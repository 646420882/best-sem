/**
 * Created by guochunyan on 2015/12/16.
 */
$(function () {
    var data = [{
        id: 1,
        roleName: '普菲特',
        rolePosition: "运营经理",
        roleProperty: "管理员",
        roleAccount: "perfect2015",
        rolePassword: "123456",
        roleCreateTime:"2015-08-12",
        roleContactWay:'123456789'

    }, {
        id: 2,
        roleName: '普菲特',
        rolePosition: "运营经理",
        roleProperty: "管理员",
        roleAccount: "perfect2015",
        rolePassword: "123456",
        roleCreateTime:"2015-08-12",
        roleContactWay:'123456789'
    }, {
        id: 3,
        roleName: '普菲特',
        rolePosition: "运营经理",
        roleProperty: "管理员",
        roleAccount: "perfect2015",
        rolePassword: "123456",
        roleCreateTime:"2015-08-12",
        roleContactWay:'123456789'
    }, {
        id: 4,
        roleName: '普菲特',
        rolePosition: "运营经理",
        roleProperty: "管理员",
        roleAccount: "perfect2015",
        rolePassword: "123456",
        roleCreateTime:"2015-08-12",
        roleContactWay:'123456789'
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
        '<button class="preserve btn btn-primary" style="display:none" title="保存">',
        '保存',
        '</button>',
        '<a class="cancel tab_operate" style="display:none" href="javascript:void(0)" title="取消">',
        '取消',
        '</a>',
        '<a class="delete tab_operate ml10" href="javascript:void(0)" title="删除" >',
        '删除',
        '</a>'

    ].join('');
}
function passwordFormatter (value, row, index) {
    return [
        "<span class='fl'>*******</span>&nbsp;&nbsp;&nbsp;&nbsp;"+
        '<a class="password_reset" href="javascript:void(0)" title="重置">',
        '重置',
        '</a>'
    ].join('')
}
