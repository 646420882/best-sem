/**
 * Created by guochunyan on 2015/12/17.
 */
$(function () {
    getAdminInfo();
});

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
    $(".indexCret").css({'display': 'none'});
    $("#userLookUpWrap").css({"display": "none"});
}
function relationAccountFormatter(item) {
    var content = "";
    for (var i = 0; i < item.length; i++) {
        content += '<div class="lookTableRow">' + item[i] + '</div>'
    }
    return [content].join('')
}
function relationPwdFormatter(item) {
    var content = "";
    for (var i = 0; i < item.length; i++) {
        content += '<div class="lookTableRow">' + item[i] + '</div>'
    }
    return [content].join('')
}
function relationApiFormatter(item) {
    var content = "";
    for (var i = 0; i < item.length; i++) {
        content += '<div class="lookTableRow">' + item[i] + '</div>'
    }
    return [content].join('')
}
function relationUrlFormatter(item) {
    var content = "";
    for (var i = 0; i < item.length; i++) {
        content += '<div class="lookTableRow">' + item[i] + '</div>'
    }
    return [content].join('')
}
function relationCodeFormatter(item) {
    var content = "";
    for (var i = 0; i < item.length; i++) {
        content += '<div class="lookTableRow">' + item[i] + '</div>'
    }
    return [content].join('')
}
function setFormatter(item, row) {
    console.log(row);
    var content = "<a href='/menus?userName=" + row.userName + "'>设置</a>";
    return [content].join('')
}

var getAdminInfo = function () {
    $('#userAdmin').bootstrapTable({
        locale: 'zh-CN',
        url: "/users",
        pagination: true,
        search: true,
        smartDisplay: false,
        pageSize: 20,
        searchAlign: 'left',
        pageList: new Array(20, 50, 100),
        paginationPreText: "上一页",
        paginationNextText: "下一页",
        showHeader: true,
        sortName: "username",
        sidePagination: 'server',
        queryParams: function (params) {
            return params;
        }
    });
}


