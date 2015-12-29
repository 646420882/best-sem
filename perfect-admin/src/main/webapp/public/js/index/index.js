/**
 * Created by guochunyan on 2015/12/17.
 */
$(function () {
    getAdminInfo();
});

function disableFormatter(value, row, index) {
    var html = "";
    if (row.accountState == 1) {
        html = html + "<a class='disable tab_operate' href='javascript:void(0)' title='禁用'>禁用</a>"
    } else {
        html = html + "<a class='disable tab_operate' href='javascript:void(0)' title='启用'>启用</a>"
    }
    return [html].join('');
};
function LookUp(value, row, index) {
    return [
        '<a class="look tab_operate" href="javascript:void(0)" title="查看">',
        '查看',
        '</a>' +
        '<span class="indexCret" style="position: relative;left: -31px;top: 21px;z-index:11;display: none"><img src="../../public/img/user_cret.png"></span>'
    ].join('');
}
function passwordFormatter(value, row, index) {
    return [
        '<a class="password_reset" href="javascript:void(0)" title="重置">',
        '重置',
        '</a>'
    ].join('')
}
function passwordFormatterUser(value, row, index) {
    return [
        '<a class="password_User" href="javascript:void(0)" title="重置">',
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
        content += '<div class="lookTableRow updateToken">' + item[i] + '</div>'
    }
    return [content].join('')
}
function relationUrlFormatter(item) {
    var content = "";
    for (var i = 0; i < item.length; i++) {
        content += '<div class="lookTableRow" >' + item[i] + '</div>'
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
    var content = "<a href='/menus?userName=" + row.userName + "'>设置</a>";
    return [content].join('')
}
function startTimeFormatter(item, row) {
    var content = '<input name="reservation_start" readonly  style="border: none;"  type="text" value=' + item + ' class="startTime">';
    setTimeout(function () {
        $('input[name="reservation_start"]').daterangepicker({
                "showDropdowns": true,
                "singleDatePicker": true,
                "timePicker24Hour": true,
                timePicker: true,
                timePickerIncrement: 30,
                format: 'YY/MM/DD',
                "locale": {
                    "format": "YYYY年M月DD日",
                    "separator": " - ",
                    "applyLabel": "确定",
                    "cancelLabel": "关闭",
                    "fromLabel": "From",
                    "toLabel": "To",
                    "customRangeLabel": "Custom",
                    "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
                    "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                    "firstDay": 1
                }
            },
            function (start, end, label, e) {
                var _endDate = end.format('YYYY年M月DD日');
                $('input[name="reservation_start"]').val(_endDate)
            });
    }, 500)
    return [content].join('')
}
function endTimeFormatter(item, row) {
    var content = '<input name="reservation_end" readonly style="border: none" type="text" value=' + item + ' class="endTime">';
    setTimeout(function () {
        $('input[name="reservation_end"]').daterangepicker({
                "showDropdowns": true,
                "singleDatePicker": true,
                "timePicker24Hour": true,
                timePicker: true,
                timePickerIncrement: 30,
                format: 'YY/MM/DD',
                "locale": {
                    "format": "YYYY年M月DD日",
                    "separator": " - ",
                    "applyLabel": "确定",
                    "cancelLabel": "关闭",
                    "fromLabel": "From",
                    "toLabel": "To",
                    "customRangeLabel": "Custom",
                    "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
                    "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                    "firstDay": 1
                }
            },
            function (start, end, label, e) {
                var _endDate = end.format('YYYY年M月DD日');
                $('input[name="reservation_end"]').val(_endDate)
            });
    }, 500)
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
        sortName: "ctime",
        sidePagination: 'server',
        queryParams: function (params) {
            if ($("#selectInfo option:selected").val() != 0) {
                params["account"] = $("#selectInfo option:selected").val();
            }
            if ($("#userTable input[placeholder='搜索']").val() != "") {
                params["user"] = $("#userTable input[placeholder='搜索']").val()
            }
            return params;
        }
    });
}
var getAdminInfo2 = function () {
    $("#userAdmin").bootstrapTable("refresh");
}



