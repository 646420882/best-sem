/**
 * Created by guochunyan on 2015/12/14.
 */
$(function () {
    //路由控制
    var href = window.location.href;
    href = '/' + href.split("/").slice(-1);
    if (href == "/") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(1) ").addClass("current");
        })
    } else if (href == "/role") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(2) ").addClass("current");
        })
    }
    else if (href == "/system") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(3) ").addClass("current");
        })
    }
    else if (href == "/jurisdiction") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(4) ").addClass("current");
        })
    }
    else if (href == "/log") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(5) ").addClass("current");
        })
    }
    //表格公用
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
        'click .disable': function (e, value, row, index) {
            var bindingtext = $(this);
            if ($(this).html() == "禁用") {
                $('#modelbox').modal();
                $("#modelboxTitle").html("是否禁用？");
                $("#modelboxBottom").click(function () {
                    $('#modelbox').modal('hide');
                    $.ajax({
                        url: '/users/' + row.id + '/status',
                        type: 'post',
                        dataType: 'JSON',
                        data: {
                            status: 0,
                        },
                        success: function (user) {
                            window.location.reload()
                        }
                    });
                });
            } else {
                $("#modelboxTitle").html("是否启用？");
                $('#modelbox').modal()
                $("#modelboxBottom").click(function () {
                    $('#modelbox').modal('hide');
                    $.ajax({
                        url: '/users/' + row.id + '/status',
                        type: 'post',
                        dataType: 'JSON',
                        data: {
                            status: 1,
                        },
                        success: function (user) {
                            window.location.reload()
                        }
                    });
                })

            }
        },
        'click .editor': function (e, value, row, index) {
            var editorBottom = $(this);
            var that = $(this).parent().prevAll("td");
            var that_value = that.each(function (i) {
                var that_html = $(this).html();
                if (i == 1) {
                    return;
                } else if (i == 2) {
                    $(this).html("<input type='password' class='form-control' value='******''> ");
                } else if (i == 7) {
                    $(this).html('')
                } else {
                    $(this).html("<input type='text' class='form-control' value='" + that_html + "'> ");
                }
                editorBottom.hide();
                editorBottom.next(".preserve").attr("style", "display:block");
                editorBottom.next().next(".cancel").attr("style", "display:block");
                editorBottom.next().next().next(".delete").attr("style", "display:none");
            });
        },
        'click .preserve': function (e, value, row, index) {
            var preserveHtML = $(this);
            var preserveThat = $(this).parent().prevAll("td");
            preserveThat.each(function () {
                var that_html = $(this).find("input").val();
                $(this).html(that_html);
            });
            preserveHtML.attr("style", "display:none");
            preserveHtML.next(".cancel").attr("style", "display:none");
            preserveHtML.next().next(".delete").attr("style", "display:block");
            preserveHtML.prevAll().show();
        },
        'click .cancel': function (e, value, row, index) {
            var cancelHtML = $(this);
            var cancelThat = $(this).parent().prevAll("td");
            cancelThat.each(function (i) {
                var that_html = $(this).find("input").val();
                if (i == 2) {
                    $(this).html('<a class="password_reset" href="javascript:void(0)" title="重置">重置</a>');
                } else if (i == 7) {
                    $(this).html('<input data-index="' + index + '" name="btSelectItem" type="checkbox">')
                } else {
                    $(this).html(that_html);
                }
            });
            cancelHtML.attr("style", "display:none");
            cancelHtML.next(".delete").attr("style", "display:block");
            cancelHtML.prev().prev(".editor").attr("style", "display:block");
            cancelHtML.prev(".preserve").attr("style", "display:none");
        },
        'click .delete': function (e, value, row, index) {
            var tabledelete = $(this).parent().parent();
            $('#modelbox').modal();
            $("#modelboxTitle").html("是否删除？");
            $("#modelboxBottom").click(function () {
                $('#modelbox').modal('hide');
                tabledelete.remove();

            })
        },
        'click .look': function (e, value, row, index) {
            $(".indexCret").css({'display': 'none'});
            $("#userLookUpWrap").css({"display": "block", "top": 221 + index * 45 + "px"});
            $(this).next().css("display", 'block');
            if (row != undefined && row != "") {
                var datas = [];
                row.systemUserModules.forEach(function (item, i) {
                    var data = {};
                    data.id = item.id;
                    data.userName = row.userName;
                    data.userId = row.id;
                    data.systemModal = item.moduleName;
                    data.userProperty = (item.enabled ? "已启用" : "已禁用");
                    data.openStates = (item.payed ? "已购买" : "未购买")
                    data.startDate = new Date(item.startTime).Format("yyyy年M月dd日");
                    data.endDate = new Date(item.endTime).Format("yyyy年M月dd日")
                    data.authorityAssignment = "设置";
                    var baiduName = [];
                    var baiduPwd = [];
                    var token = [];
                    var bestDomain = [];
                    var huiyanCode = [];

                    if (item.moduleName == "百思慧眼") {
                        $.ajax({
                            url: '/usersHuiYan',
                            type: 'get',
                            dataType: 'JSON',
                            async: false,
                            data: {
                                uid: row.id,
                            },
                            success: function (user) {
                                console.log(user);
                                if (user.data.length > 0) {
                                    user.data.forEach(function (huiyan, i) {
                                        baiduName.push(huiyan.bname == null ? "-" : huiyan.bname);
                                        baiduPwd.push(huiyan.bpasswd == null ? "-" : huiyan.bpasswd);
                                        token.push(huiyan.token == null ? "-(双击修改)<input type='hidden' value='" + huiyan.id + "'/>" : huiyan.token + "<input type='hidden' value='" + huiyan.id + "'/>(双击修改)");
                                        bestDomain.push(huiyan.site_url == null ? "-" : huiyan.site_url);
                                        var html = "<div id='base_code'>&lt;script&gt;<br>" +
                                            "var _pct= _pct|| [];<br> " +
                                            "(function() {<br>" +
                                            "var hm = document.createElement(\"script\");<br>" +
                                            "hm.src = \"//t.best-ad.cn/t.js?tid=" + huiyan.track_id + "\";<br>" +
                                            "var s = document.getElementsByTagName(\"script\")[0];<br>" +
                                            "s.parentNode.insertBefore(hm, s);<br> " +
                                            "})();<br>" +
                                            "&lt;/script&gt;</div>";
                                        huiyanCode.push(html)
                                    })
                                }
                            }
                        });
                    } else {
                        item.accounts.forEach(function (baidu, i) {
                            baiduName.push(baidu.baiduUserName == null ? "-" : baidu.baiduUserName);
                            baiduPwd.push(baidu.baiduPassword == null ? "-" : baidu.baiduPassword);
                            token.push(baidu.token == null ? "-(双击修改)<input type='hidden' value='" + baidu.id + "'/>" : baidu.token + "<input type='hidden' value='" + baidu.id + "'/>(双击修改)");
                            bestDomain.push(baidu.bestRegDomain == null ? "-" : baidu.bestRegDomain);
                        });
                    }
                    data.relatedAccount = baiduName;
                    data.relatedAccountPwd = baiduPwd;
                    data.APICode = token;
                    data.URLAddress = bestDomain;
                    data.statisticalCode = huiyanCode;
                    datas.push(data);
                });
                $("#userLookUpTable").bootstrapTable("removeAll")
                $("#userLookUpTable").bootstrapTable("append", datas);
                $('#userLookUpTable').bootstrapTable({
                    data: datas
                });
                $(".lookTableRow").parent().attr('style', 'padding: 0 !important');
            }
        },
        'click .password_reset': function (e, value, row, index) {
            $('#modelbox').modal();
            $("#modelboxTitle").html("是否重置密码" + row.userName + "用户密码！");
            $("#modelboxBottom").click(function () {
                $('#modelbox').modal('hide');
                $.ajax({
                    url: '/users/' + row.id + '/password',
                    type: 'post',
                    dataType: 'JSON',
                    success: function (user) {
                        if(user.code == 0){
                            alert("密码重置成功，重置为：123456")
                        }else{
                            alert("密码重置失败")
                        }
                    }
                });
            })
        },
        'dblclick .updateToken': function (e, value, row, index) {
            $('#tokenBox').modal();
            $("#tokenBoxTitle").html("修改Token值！");
            //判断是否绑定了click事件
            var objEvt = $._data($("#tokenBoxBottom")[0], "events");
            $("#tokenBoxBottom").click(function () {
                if (row.systemModal == "百思搜客") {
                    console.log(row.accountid);
                    $.ajax({
                        url: '/users/' + row.userId + '/modules/' + row.systemModal + '/accounts/' + $(e.target).find("input").val() + '/token/' + $("#tokenBoxInput").val(),
                        type: 'post',
                        dataType: 'json',
                        success: function (user) {
                            if (user.code == 0) {
                                alert("token修改成功");
                                window.location.reload();
                            } else {
                                alert("token修改失败");
                            }
                        }
                    });
                } else {
                    console.log(row.huiyanid);
                    $.ajax({
                        url: '/updateHuiYan/' + $(e.target).find("input").val() + '/token/' + $("#tokenBoxInput").val(),
                        type: 'post',
                        dataType: 'json',
                        success: function (user) {
                            console.log(user);
                            if (user.code == 0) {
                                alert("token修改成功");
                                window.location.reload();
                            } else {
                                alert("token修改失败");
                            }
                        }
                    });
                }

            })
        },
    };
})
function firstAdd() {
    var startId = "<input type='text' class='form-control'>", rows = [];
    rows.push({
        id: startId,
        name: startId,
        remark: startId,
        url: startId,
        platform: startId,
        time: startId
    });
    return rows;
}
function secondAdd() {
    var startId = "<input type='text' class='form-control'>",
        rows = [];
    rows.push({
        id: startId,
        name: startId,
        remark: startId,
        wedName: startId,
        wedUrl: startId,
        wedCode: startId
    });
    return rows;
}
function queryParams() {
    return {
        type: 'owner',
        sort: 'updated',
        direction: 'desc',
        per_page: 100,
        page: 1
    };
}

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
}