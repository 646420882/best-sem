/**
 * Created by guochunyan on 2015/12/17.
 */
$(function () {
    var data = [{
        id: 1,
        companyName: '普菲特',
        userName: "perfect2015",
        userPwd: "123456",
        email: "abcd163.com",
        registerTime: "2015年12月25日",
        contactPerson: "sdf",
        companyPhone: "12345678989",
        mobile: "028-66666666",
        address: "天府软件园"

    }, {
        id: 2,
        companyName: '普菲特',
        userName: "perfect2015",
        userPwd: "123456",
        email: "abcd163.com",
        registerTime: "2015年12月25日",
        contactPerson: "sdf",
        companyPhone: "12345678989",
        mobile: "028-66666666",
        address: "天府软件园"
    }, {
        id: 3,
        companyName: '普菲特',
        userName: "perfect2015",
        userPwd: "123456",
        email: "abcd163.com",
        registerTime: "2015年12月25日",
        contactPerson: "sdf",
        companyPhone: "12345678989",
        mobile: "028-66666666",
        address: "天府软件园"
    }, {
        id: 4,
        companyName: '普菲特',
        userName: "perfect2015",
        userPwd: "123456",
        email: "abcd163.com",
        registerTime: "2015年12月25日",
        contactPerson: "sdf",
        companyPhone: "12345678989",
        mobile: "028-66666666",
        address: "天府软件园"
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
        '</a>' +
        '<span class="indexCret" style="position: relative;left: -31px;top: 21px;z-index:2;display: none"><img src="../../public/img/user_cret.png"></span>'
    ].join('');
}
function passwordFormatter(value, row, index) {
    return [
        "<span class='fl'>*******</span>&nbsp;&nbsp;&nbsp;&nbsp;" +
        '<a class="password_reset" href="javascript:void(0)" title="重置">',
        '重置',
        '</a>'
    ].join('')
}
function cancelLookUp() {
    $(".indexCret").css({'display':'none'});
    $("#userLookUpWrap").css({"display": "none"});
}
function relationAccountFormatter(item){
    var content = "";
    for(var i = 0;i < item.length; i++){
        content += '<div class="lookTableRow">'+item[i]+'</div>'
    }
    return [content].join('')
}
function relationPwdFormatter(item){
    var content = "";
    for(var i = 0;i < item.length; i++){
        content += '<div class="lookTableRow">'+item[i]+'</div>'
    }
    return [content].join('')
}
function relationApiFormatter(item){
    var content = "";
    for(var i = 0;i < item.length; i++){
        content += '<div class="lookTableRow">'+item[i]+'</div>'
    }
    return [content].join('')
}
function relationUrlFormatter(item){
    var content = "";
    for(var i = 0;i < item.length; i++){
        content += '<div class="lookTableRow">'+item[i]+'</div>'
    }
    return [content].join('')
}
function relationCodeFormatter(item){
    var content = "";
    for(var i = 0;i < item.length; i++){
        content += '<div class="lookTableRow">'+item[i]+'</div>'
    }
    return [content].join('')
}
function setFormatter(item){
    var content = "<a href='/admin/jurisdiction'>设置</a>";
    return [content].join('')
}
