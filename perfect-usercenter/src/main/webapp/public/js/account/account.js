/**
 * Created by guochunyan on 2015/12/15.
 */

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2014-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2014-7-2 8:9:4.18
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

$(function () {
    var huiyanData = [{
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


    var retrieveSemAccounts = function () {
        $.ajax({
            url: '/account/' + $('#sysUserName').val() + "/semAccount/list",
            type: 'GET',
            dataTye: 'JSON',
            success: function (data) {
                var semAccounts = [];
                data.rows.forEach(function (item) {
                    var obj = {};
                    obj["id"] = item.id;
                    obj["name"] = item.baiduUserName;
                    obj["password"] = item.baiduPassword;
                    obj["remark"] = item.baiduRemarkName;
                    obj["url"] = item.bestRegDomain;
                    obj["platform"] = "百思搜客";
                    obj["time"] = new Date(item.accountBindingTime).Format("yyyy年M月dd日");
                    obj["action"] = 1;
                    semAccounts.push(obj);
                });

                $('#account_table').bootstrapTable({
                    data: semAccounts
                });
            },
            error: function (error) {
                $('#account_table').bootstrapTable({
                    data: []
                });
            }
        });
    }();


    $('#AccountTable').bootstrapTable({
        data: huiyanData
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
            var bindingtext = $(this);
            if ($(this).html() == "绑定") {
                $('#modelbox').modal();
                $("#modelboxTitle").html("系统提示");
                $(".modal-body").html("是否绑定？");
                $("#modelboxBottom").click(function () {
                    $.ajax({
                        url: '/account/souke/active',
                        type: 'POST',
                        dataType: 'JSON',
                        data: {
                            moduleAccountId: row.id,
                            username: $('#sysUserName').val()
                        },
                        success: function (data) {
                            if (data.status) {
                                alert("绑定成功!");
                                $('#modelbox').modal('hide');
                                $('.modal-backdrop').hide();
                                bindingtext.html("取消绑定");
                            } else {
                                alert("绑定失败!");
                            }
                        }
                    });
                })
            } else {
                $('#modelbox').modal();
                $("#modelboxTitle").html("系统提示");
                $(".modal-body").html("是否取消绑定？");
                $("#modelboxBottom").click(function () {
                    $.ajax({
                        url: '/account/souke/unbind',
                        type: 'POST',
                        dataType: 'JSON',
                        data: {
                            moduleAccountId: row.id,
                            username: $('#sysUserName').val()
                        },
                        success: function (data) {
                            if (data.status) {
                                alert("解除绑定成功!");
                                $('#modelbox').modal('hide');
                                $('.modal-backdrop').hide();
                                bindingtext.html("绑定");
                            } else {
                                alert("解除绑定失败!");
                            }
                        }
                    });
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
        $('#modelbox').modal();
        $('.modal-dialog').css({width: '400px'});
        $("#modelboxTitle").html("修改关联账户");
        $(".modal-body").html("<ul class='account_change'>" +
            "<li id='selectedSemAccountName'>关联账户名：</li>" +
            "<li id='selectedSemAccountPassword'>账户密码：</li>" +
            "<li id='selectedSemAccountRemarkName'>备注名：</li>" +
            "<li id='selectedSemAccountUrl'>URL地址：</li>" +
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

        if (row.platform == "百思搜客") {
            $('#modelboxBottom').click(function () {
                var selectedSemAccountName = $('#selectedSemAccountName input').val();
                if (selectedSemAccountName == null || selectedSemAccountName.trim() == "") {
                    alert("请输入帐户!");
                    return;
                }

                var selectedSemAccountPassword = $('#selectedSemAccountPassword input').val();
                if (selectedSemAccountPassword == null || selectedSemAccountPassword.trim() == "") {
                    alert("请输入帐户密码!");
                    return;
                }

                var selectedSemAccountRemarkName = $('#selectedSemAccountRemarkName input').val();
                var selectedSemAccountUrl = $('#selectedSemAccountUrl input').val();
                if (selectedSemAccountUrl == null || selectedSemAccountUrl.trim() == "") {
                    alert("请输入URL地址!");
                    return;
                }

                var moduleAccount = {};
                moduleAccount["id"] = row.id;
                moduleAccount["baiduUserName"] = selectedSemAccountName;
                moduleAccount["baiduPassword"] = selectedSemAccountPassword;
                moduleAccount["baiduRemarkName"] = selectedSemAccountRemarkName;
                moduleAccount["bestRegDomain"] = selectedSemAccountUrl;
                $.ajax({
                    url: '/account/souke/update',
                    type: 'POST',
                    change: false,
                    dataType: 'JSON',
                    data: moduleAccount,
                    success: function (data) {
                        if (data.status) {
                            $('.modal').hide();
                            $('.modal-backdrop').hide();
                            window.location.reload();
                            alert("修改成功!");
                        } else {
                            alert("修改失败!");
                        }
                    }
                });
            });
        }
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

        if (platform == "百思搜客") {
            var tabledelete = $(this).parent().parent();
            $('#modelbox').modal();
            $("#modelboxTitle").html("系统提示");
            $(".modal-body").html("是否删除？");
            $("#modelboxBottom").click(function () {
                $.ajax({
                    url: '/account/souke/delete',
                    type: 'POST',
                    dataType: 'JSON',
                    data: {
                        username: $('#sysUserName').val(),
                        moduleAccountId: row.id
                    },
                    success: function (data) {
                        if (data.status) {
                            alert("删除成功!");
                            $('#modelbox').modal('hide');
                            $('.modal-backdrop').hide();
                            tabledelete.remove();
                        } else {
                            alert("删除失败!");
                        }
                    }
                });
            })
        } else if (platform == "百思慧眼") {
            var tabledelete = $(this).parent().parent();
            $('#modelbox').modal();
            $("#modelboxTitle").html("系统提示");
            $(".modal-body").html("是否删除？");
            $("#modelboxBottom").click(function () {
                $('#modelbox').modal('hide');
                tabledelete.remove();
            })
        }
    }
};
var $table = $('#account_table'), $button = $('#Tablebutton');

$(function () {
    /**
     * 新增搜客帐号
     *
     */
    $button.click(function () {
        var acountContent = "<ul class='account_add'>" +
            "<li>关联账户名：<input id='semAccountName' class='form-control ' type='text'></li>" +
            "<li>账户密码：<input id='semAccountPassword' class='form-control ' type='password'></li>" +
            "<li>备注名：<input id='semAccountRemarkName' class='form-control ' type='text'></li>" +
            "<li>URL地址：<input id='semAccountAddr' class='form-control ' type='text'></li>" +
            "<li>账户所属平台：<input id='semPlatForm' class='form-control ' readonly value='百思搜客' type='text'></li></ul>";
        $('#modelbox').modal();
        $('.modal-dialog').css({width: '400px'});
        $("#modelboxTitle").html("新增关联账户");
        $(".modal-body").html(acountContent);

        $('#modelboxBottom').click(function () {
            // 获取帐号属性
            var semAccountName = $('#semAccountName').val();
            if (semAccountName == null || semAccountName.trim() == "") {
                alert("请输入帐户!");
                return;
            }

            var semAccountPassword = $('#semAccountPassword').val();
            if (semAccountPassword == null || semAccountPassword.trim() == "") {
                alert("请输入帐户密码!");
                return;
            }

            var semAccountRemarkName = $('#semAccountRemarkName').val();
            var semAccountAddr = $('#semAccountAddr').val();
            if (semAccountAddr == null || semAccountAddr.trim() == "") {
                alert("请输入URL地址!");
                return;
            }
            // TODO URL验证

            var moduleAccount = {};
            moduleAccount["baiduUserName"] = semAccountName;
            moduleAccount["baiduPassword"] = semAccountPassword;
            moduleAccount["baiduRemarkName"] = semAccountRemarkName;
            moduleAccount["bestRegDomain"] = semAccountAddr;
            $.ajax({
                url: '/account/souke/add/?username=' + $('#sysUserName').val(),
                type: 'POST',
                change: false,
                dataType: 'JSON',
                data: moduleAccount,
                success: function (data) {
                    if (data.status) {
                        $('.modal').hide();
                        $('.modal-backdrop').hide();
                        window.location.reload();
                        alert("新增成功!");
                    } else {
                        alert("新增失败!");
                    }
                }
            });
        });
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

/**
 * 获取当前用户的所有搜客帐号
 *
 */
var getSemAccounts = function () {
    $.ajax({
        url: '/account/' + $('#sysUserName').val() + "/semAccount/list",
        type: 'GET',
        dataTye: 'JSON',
        success: function (data) {
            console.log(JSON.stringify(data.rows));
        }
    });
};
