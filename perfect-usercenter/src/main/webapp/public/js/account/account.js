/**
 * Created by guochunyan on 2015/12/15.
 */
$(function () {
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
                    obj["state"] = item.state;  // 1 -> 显示取消绑定; 0 -> 显示绑定
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
    if (row.state == 1) {
        var content = "取消绑定"
    } else {
        var content = "绑定"
    }
    return [
        '<a class="binding" href="javascript:void(0)" title=' + content + '>',
        content,
        //'<a class="binding" href="javascript:void(0)" title="绑定">',
        //'绑定',
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
    var html = "";
    if (row.enable) {
        html = html + "<a class='disable' href='javascript:void(0)' title='启用'>启用</a>"
    } else {
        html = html + "<a class='disable' href='javascript:void(0)' title='禁用'>禁用</a>"
    }
    return [
        html,
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
                            id: row.id,
                            username: $('#sysUserName').val()
                        },
                        success: function (data) {
                            if (data.status) {
                                $('#modelbox').modal('hide');
                                $('.modal-backdrop').hide();
                                bindingtext.html("取消绑定");
                                alert("绑定成功!");
                                $("#modelboxBottom").unbind();
                            } else {
                                alert("绑定失败!");
                                $("#modelboxBottom").unbind();
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
                            id: row.id,
                            username: $('#sysUserName').val()
                        },
                        success: function (data) {
                            if (data.status) {

                                $('#modelbox').modal('hide');
                                $('.modal-backdrop').hide();
                                bindingtext.html("绑定");
                                alert("解除绑定成功!");
                                $("#modelboxBottom").unbind();
                            } else {
                                alert("解除绑定失败!");
                                $("#modelboxBottom").unbind();
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
                    $('#modelbox').modal('hide');
                    bindingtext.html("取消绑定");
                })

            } else {
                $('#modelbox').modal();
                $("#modelboxTitle").html("系统提示");
                $(".modal-body").html("是否取消绑定？");
                $("#modelboxBottom").click(function () {
                    $('#modelbox').modal('hide');
                    bindingtext.html("绑定");
                })

            }
        }
    },
    'click .disable': function (e, value, row, index) {
        var platform = row.platform;

        if (platform == "百思慧眼") {
            var bindingtext = $(this);
            if ($(this).html() == "禁用") {
                $('#modelbox').modal();
                $("#modelboxTitle").html("系统提示");
                $(".modal-body").html("是否禁用？");
                $("#modelboxBottom").click(function () {
                    $.ajax({
                        url: "/enableOrPause",
                        type: "post",
                        data: {
                            uid: row.id,
                            enable: true
                        },
                        cache: false,
                        dataType: "json",
                        success: function (user) {
                            console.log(user)
                            if (user.data != "" && user.data != undefined && user.data != null) {
                                $('#modelbox').modal('hide');
                                var tabledelete = $(this).parent().parent();
                                tabledelete.remove();
                                window.location.reload()
                            }
                        }
                    });
                })

            } else {
                $("#modelboxTitle").html("系统提示");
                $(".modal-body").html("是否启动？");
                $('#modelbox').modal();
                $("#modelboxBottom").click(function () {
                    $.ajax({
                        url: "/enableOrPause",
                        type: "post",
                        data: {
                            uid: row.id,
                            enable: false
                        },
                        cache: false,
                        dataType: "json",
                        success: function (user) {
                            if (user.data != "" && user.data != undefined && user.data != null) {
                                $('#modelbox').modal('hide');
                                var tabledelete = $(this).parent().parent();
                                tabledelete.remove();
                                window.location.reload()
                            }
                        }
                    });
                })

            }
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
                    url: '/account/souke/update/?username=' + $('#sysUserName').val(),
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

        if (platform == "百思慧眼") {
            $('#modelbox').modal();
            $('.modal-dialog').css({width: '400px'});
            $("#modelboxTitle").html("修改关联账户");
            $(".modal-body").html("<ul class='account_change'>" +
                "<li>关联账户名：</li>" +
                "<li>账户密码：</li>" +
                "<li id='rname'>备注名：</li>" +
                "<li id='huiyanUrl'>URL地址：</li>" +
                "<li>账户所属平台：</li>" +
                "<li>统计代码：</li>" +
                "<li id='webName'>网站名称：</li></ul>");
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

            $("#modelboxBottom").click(function () {
                $.ajax({
                    url: "/insightupdate",
                    type: "post",
                    data: {
                        uid: row.id,
                        bname: $("#bname input").val(),
                        bpwd: $("#bpwd input").val(),
                        rname: $("#rname input").val(),
                        url: $("#huiyanUrl input").val(),
                        webName: $("#webName input").val()
                    },
                    cache: false,
                    dataType: "json",
                    success: function (user) {
                        if (user.data != "" && user.data != undefined && user.data != null) {
                            $('#modelbox').modal('hide');
                            var tabledelete = $(this).parent().parent();
                            tabledelete.remove();
                            window.location.reload()
                        }
                    }
                });
            });
        }
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
                        id: row.id
                    },
                    success: function (data) {
                        if (data.status) {
                            $('#modelbox').modal('hide');
                            $('.modal-backdrop').hide();
                            tabledelete.remove();
                            alert("删除成功!");
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
                console.log(row.id)
                $.ajax({
                    url: "/insightDel/" + row.id,
                    type: "get",
                    dataType: "json",
                    success: function (user) {
                        if (user.data == "\"success\"") {
                            $('#modelbox').modal('hide');
                            tabledelete.remove();
                            window.location.reload()
                        }
                    }
                });
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
            if (IsURL(semAccountAddr)) {
                alert("请输入正确的URL地址!");
                return;
            }

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
            "<li>URL地址：<input class='form-control ' id='url' type='text'><b style='color:red;'>  *</b></li>" +
            "<li>网站名称：<input class='form-control ' id='urlname' type='text'><b style='color:red;'>  *</b></li>" +
            "<li>关联账户名：<input class='form-control ' id='buserName' type='text'></li>" +
            "<li>账户密码：<input class='form-control ' id='bpasswd' type='text'></li>" +
            "<li>备注名：<input class='form-control ' id='rname' type='text'></li>" +
            "<li>账户所属平台：<input class='form-control ' readonly value='百思慧眼' type='text'></li>" +
            "<li>可输入如下4种域名形式 <p/>1.主域名（如：www.baidu.com）<br>2.二级域名（如：sub.baidu.com) 3.子目录（如：www.baidu.com/sub）<br>4.wap站域名（如：wap.baidu.com）</li></ul>";
        $('#modelbox').modal();
        $('.modal-dialog').css({width: '400px'});
        $("#modelboxTitle").html("新增关联账户");
        $(".modal-body").html(acountContent);

        $("#modelboxBottom").click(function () {
            var url = $("#url").val();
            if (IsURL(url)) {
                alert("请输入正确的url地址");
                return
            }
            var urlname = $("#urlname").val();
            var buserName = $("#buserName").val();
            var bpasswd = $("#bpasswd").val();
            var rname = $("#rname").val();
            if (url == undefined || url == "") {
                alert("url地址不能为空");
                return
            }
            if (urlname == undefined || urlname == "") {
                alert("网站名称不能为空");
                return
            }
            var websiteDTO = {};
            websiteDTO["uid"] = $("#sysUserId").val();
            websiteDTO["site_url"] = url;
            websiteDTO["site_name"] = urlname;
            websiteDTO["bname"] = buserName;
            websiteDTO["bpasswd"] = bpasswd;
            websiteDTO["rname"] = rname;
            $.ajax({
                url: "/insightAdd",
                type: "post",
                data: websiteDTO,
                cache: false,
                dataType: "json",
                success: function (user) {
                    console.log(user)
                    if (user.data != "" && user.data != undefined && user.data != null) {
                        $('#modelbox').modal('hide');
                        var tabledelete = $(this).parent().parent();
                        tabledelete.remove();
                        window.location.reload()
                    }
                }
            });
        })
    });
    huiyanQuery();
});

var huiyanQuery = function () {
    $.ajax({
        url: "/insightQuery/" + $("#sysUserId").val(),
        type: "get",
        dataType: "json",
        success: function (user) {
            var datas = new Array();
            if (user.code == 0) {
                user.data.forEach(function (e, s) {
                    var obj = {};
                    obj.id = e.id;
                    obj.name = e.bname;
                    obj.remark = e.rname;
                    obj.platform = "百思慧眼";
                    obj.webName = e.site_name;
                    obj.webUrl = e.site_url;
                    var code = "<div id='base_code'>&lt;script&gt;<br>" +
                        "var _pct= _pct|| [];<br> " +
                        "(function() {<br>" +
                        "var hm = document.createElement(\"script\");<br>" +
                        "hm.src = \"//t.best-ad.cn/t.js?tid=" + e.track_id + "\";<br>" +
                        "var s = document.getElementsByTagName(\"script\")[0];<br>" +
                        "s.parentNode.insertBefore(hm, s);<br> " +
                        "})();<br>" +
                        "&lt;/script&gt;</div>";
                    obj.webCode = code
                    obj.time = e.ctime;
                    datas.push(obj);
                })
            }
            $('#AccountTable').bootstrapTable({
                data: datas
            });

        }
    });
};


function IsURL(str_url) {
    var strRegex = '^(((https|http)?://)?([a-zA-Z0-9]([a-zA-Z0-9\-]{0,61}[a-zA-Z0-9]))?\.+(com|top|cn|wang|net|org|hk|co|cc|me|pw|la|asia|biz|mobi|gov|name|info|tm|tv|tel|us|tw|website|host|press|cm|tw|sh|ws|in|io|vc|sc|ren))$';
    var re = new RegExp(strRegex);

    if (re.test(str_url)) {
        return false;
    } else {
        return true;
    }
}
