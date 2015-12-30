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
            document.title = '百思-用户管理';
        })
    } else if (href == "/roles") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(2) ").addClass("current");
        });
        document.title = '百思-角色管理';
    }
    else if (href == "/system") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(3) ").addClass("current");
        });
        document.title = '百思-系统模块';
    }
    else if (href == "/menus") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(4) ").addClass("current");
        });
        document.title = '百思-模块权限';
    }
    else if (href == "/logs") {
        $(".totalNav ").each(function () {
            $(this).removeClass("current");
            $(".totalNav:nth-child(5) ").addClass("current");
            document.title = '百思-日志管理';
        })
    }
    //表格公用
    window.operateEvents = {
        'click .binding': function (e, value, row, index) {
            var bindingtext = $(this);
            if ($(this).html() == "绑定") {
                $('#modelbox').modal()
                $("#modelboxTitle").html("是否绑定？");
                //取消绑定click事件
                removeClick("modelboxBottom");
                $("#modelboxBottom").click(function () {
                    $('#modelbox').modal('hide');
                    bindingtext.html("取消绑定");
                })

            } else {
                $("#modelboxTitle").html("是否取消绑定？");
                $('#modelbox').modal();
                //取消绑定click事件
                removeClick("modelboxBottom");
                $("#modelboxBottom").click(function () {
                    $('#modelbox').modal('hide');
                    bindingtext.html("绑定");
                })

            }
        },
        'click .disable': function (e, value, row, index) {
            var bindingtext = $(this);
            if ($(this).html() == "禁用") {
                $('#modelbox1').modal();
                $("#modelboxTitle1").html("是否禁用？");
                //取消绑定click事件
                removeClick("modelboxBottom1");
                $("#modelboxBottom1").click(function () {
                    $('#modelbox1').modal('hide');
                    $.ajax({
                        url: '/users/' + row.id + '/status',
                        type: 'post',
                        dataType: 'JSON',
                        data: {
                            status: 0
                        },
                        success: function (user) {
                            window.location.reload()
                        }
                    });
                });
            } else {
                $("#modelboxTitle1").html("是否启用？");
                $('#modelbox1').modal()
                //取消绑定click事件
                removeClick("modelboxBottom1");
                $("#modelboxBottom1").click(function () {
                    $('#modelbox1').modal('hide');
                    $.ajax({
                        url: '/users/' + row.id + '/status',
                        type: 'post',
                        dataType: 'JSON',
                        data: {
                            status: 1
                        },
                        success: function (user) {
                            window.location.reload()
                        }
                    });
                })

            }
        },
        'click .editor': function (e, value, row, index) {
            var cTime = new Date(parseInt(row.ctime)).Format("yyyy-MM-dd hh:mm:ss");
            $("#editRolePwdLi").hide();/*修改不要密码*/
            $("#roleEdit").modal();
            $("#rowIndex").val(index);
            $("#editId").val(row.id);
            $("#editRoleName").val(row.name);
            $("#editRolePosition").val(row.title);
            $("#editRoleAccount").val(row.loginName);
            $("#editRoleManage").val(row.superAdmin?"true":"false");
            $("#editRoleTransCreateTime").val(cTime);
            $("#editRoleCreateTime").val(row.ctime);
            $("#editRoleContact").val(row.contact);
            /*var editorBottom = $(this);
            var that = $(this).parent().prevAll("td");
            var that_value = that.each(function (i) {
                var that_html = $(this).html();
                if (i == 1) {
                    return;
                } else if (i == 2) {
                    $(this).html("<a class='password_reset' href='javascript:void(0)' title='重置'>重置</a> ");
                } else if (i == 3) {
                    if (row.superAdmin) {
                        $(this).html("<select><option value='true'>超级管理员</option><option value='false'>管理员</option></select>");
                    } else {
                        $(this).html("<select><option value='false'>管理员</option><option value='true'>超级管理员</option></select>");
                    }
                } else if (i == 7) {
                    $(this).html(index + 1);
                } else if (i == 8) {
                    $(this).html("<input data-index='" + index + "' name='btSelectItem' type='checkbox'  />");
                } else {
                    $(this).html("<input type='text' class='form-control' value='" + that_html + "'> ");
                }
            });
            editorBottom.hide();
            editorBottom.next(".preserve").attr("style", "display:block");
            editorBottom.next().next(".cancel").attr("style", "display:block");
            editorBottom.next().next().next(".delete").attr("style", "display:none");*/
        },
       /* 'click .preserve': function (e, value, row, index) {
            var preserveHtML = $(this);
            var preserveThat = $(this).parent().prevAll("td");
            var _newRow = {};
            if (confirm("是否需要修改?")) {
                preserveThat.each(function (i) {
                    var that_html = $(this).find("input").val();
                    _newRow["id"] = row.id;
                    switch (i) {
                        case 0:
                            _newRow["contact"] = that_html;
                            break;
                        case 1:
                            that_html = $(this).find("span").attr("ctime");
                            _newRow["ctime"] = that_html;
                            break;
                        case 3:
                            that_html = $(this).find("select :selected").val();
                            that_html = that_html == "true" ? true : false;
                            _newRow["superAdmin"] = that_html;
                            break;
                        case 4:
                            _newRow["loginName"] = that_html;
                            break;
                        case 5:
                            _newRow["title"] = that_html;
                            break;
                        case 6:
                            _newRow["name"] = that_html;
                            break;
                    }
                });
                $.ajax({
                    type: 'POST',
                    url: '/sysroles/' + row.id,
                    contentType: 'application/json',
                    data: JSON.stringify(_newRow),
                    success: function (result) {
                        if (result.code == 0) {
                            $("#roleAdmin").bootstrapTable("updateRow", {index: index, row: _newRow});
                        } else {
                            $("#roleAdmin").bootstrapTable("updateRow", {index: index, row: row});
                            alert(result.msg);
                        }
                    }
                });
            } else {
                $("#roleAdmin").bootstrapTable("updateRow", {index: index, row: row});
            }
            preserveHtML.attr("style", "display:none");
            preserveHtML.next(".cancel").attr("style", "display:none");
            preserveHtML.next().next(".delete").attr("style", "display:block");
            preserveHtML.prevAll().show();
        },*/
        'click .cancel': function (e, value, row, index) {
            var cancelHtML = $(this);
            $("#roleAdmin").bootstrapTable("updateRow", {index: index, row: row});
            cancelHtML.attr("style", "display:none");
            cancelHtML.next(".delete").attr("style", "display:block");
            cancelHtML.prev().prev(".editor").attr("style", "display:block");
            cancelHtML.prev(".preserve").attr("style", "display:none");
        },
        'click .delete': function (e, value, row, index) {
            if (row.id != -1) {
                $('#modelbox').modal();
                $("#modelboxTitle").html("是否删除？");
                //取消绑定click事件
                removeClick("modelboxBottom");
                $("#modelboxBottom").click(function () {
                    $.ajax({
                        url: '/sysroles/' + row.id,
                        type: "DELETE",
                        success: function (res) {
                            if (res.code == 0) {
                                $('#modelbox').modal('hide');
                                $("#roleAdmin").bootstrapTable("removeByUniqueId", row.id);
                                alert("删除成功!");
                            } else {
                                $('#modelbox').modal('hide');
                                if (res.msg) {
                                    alert(res.msg);
                                } else {
                                    alert("删除失败!")
                                }
                            }
                        }
                    })

                });
            } else {
                $("#roleAdmin").bootstrapTable("removeByUniqueId", row.id);
            }
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
                                uid: row.id
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
                $('input[name="reservation"]').daterangepicker({
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
                        $(this).val(_endDate)
                        var id = $(this)[0].element[0].id.replace("start", "").replace("end", "");
                        var startDate, endDate;
                        if ($(this)[0].element[0].id.indexOf("start") != -1) {
                            startDate = end.format('YYYY-M-DD');
                        } else {
                            endDate = end.format('YYYY-M-DD');
                        }
                        $.ajax({
                            url: "/users/" + id + "/time",
                            type: 'POST',
                            data: {
                                start: startDate,
                                end: endDate
                            },
                            success: function (res) {

                            }
                        })
                    });
                $(".lookTableRow").parent().attr('style', 'padding: 0 !important');
            }
        },
        'click .password_reset': function (e, value, row, index) {
            $('#modelbox').modal();
            $("#modelboxTitle").html("是否重置密码管理员用户密码！");
            $("#modelboxBottom").click(function () {
                $("#modelboxTitle").html("重置管理员密码");
                $("#modelboxBottom").click(function () {
                    if (confirm("是否重置用户:" + row.loginName + "的登录密码?")) {
                        var resetPwd = $("input[name='resetPwd']").val();
                        $.ajax({
                            url: '/sysroles/' + row.id + "/password",
                            type: 'POST',
                            data: {password: resetPwd},
                            success: function (res) {
                                if (res.code == 0) {
                                    $('#modelbox').modal('hide');
                                    alert("重置成功!");

                                } else {
                                    if (res.msg) {
                                        alert(res.msg);
                                    }
                                }
                            }
                        })
                    }
                })
            })
        },
        'click .password_User': function (e, value, row, index) {
            $('#modelbox2').modal();
            $("#modelboxTitle2").html("是否重置密码" + row.userName + "用户密码！");
            //取消绑定click事件
            removeClick("modelboxBottom2");
            $("#modelboxBottom2").click(function () {
                console.log(value)
                $('#modelbox2').modal('hide');
                $.ajax({
                    url: '/users/' + row.id + '/password',
                    type: 'post',
                    dataType: 'JSON',
                    success: function (user) {
                        if (user.code == 0) {
                            alert("密码重置成功，重置为：123456")
                        } else {
                            alert("密码重置失败")
                        }
                    }
                });
            })
        },
        'dblclick .updateToken': function (e, value, row, index) {
            $('#tokenBox').modal();
            $("#tokenBoxTitle").html("修改Token值！");
            //取消绑定click事件
            removeClick("tokenBoxBottom");
            $("#tokenBoxBottom").click(function () {
                var tokenid = $("#tokenBoxInput").val();
                if (tokenid.trim() != "" && tokenid != undefined) {
                    if (row.systemModal == "百思搜客") {
                        $.ajax({
                            url: '/users/' + row.userId + '/modules/' + row.systemModal + '/accounts/' + $(e.target).find("input").val() + '/token/' + $("#tokenBoxInput").val(),
                            type: 'post',
                            dataType: 'json',
                            success: function (user) {
                                if (user.code == 0) {
                                    alert("token修改成功");
                                    window.location.reload();
                                } else {
                                    alert("token修改失败,请查看账号密码和token是否正确");
                                }
                            }
                        });
                    } else {
                        $.ajax({
                            url: '/users/' + row.userId + '/modules/' + row.systemModal + '/accounts/' + $(e.target).find("input").val() + '/token/' + $("#tokenBoxInput").val(),
                            type: 'post',
                            dataType: 'json',
                            success: function (user) {
                                if (user.code == 0) {
                                    alert("token修改成功");
                                    window.location.reload();
                                } else {
                                    alert("token修改失败,请查看账号密码和token是否正确");
                                }
                            }
                        });
                    }
                } else {
                    alert("请填写Token");
                }

            })
        },
        /*'click .addRole': function (e, value, row, index) {
            var readyAddData = {ctime: new Date().getTime()};
            var preserveThat = $(this).parent().prevAll("td");
            preserveThat.find("input")
                .each(function (i, o) {
                    var name = $(o).attr("name");
                    if (name) {
                        readyAddData[name] = $(o).val();
                    }
                });
            preserveThat.find("select").each(function (i, o) {
                var name = $(o).attr("name");
                if (name) {
                    readyAddData[name] = $(o).val() == "true" ? true : false;
                }
            });
            if (!readyAddData.name) {
                alert("请输入用户名!");
                return;
            }
            if (!readyAddData.title) {
                alert("请输入职务!");
                return;
            }
            if (!readyAddData.loginName) {
                alert("请输入登录名!");
                return;
            }
            if (!readyAddData.password) {
                alert("请输入密码!");
                return;
            }
            if (!readyAddData.contact) {
                alert("请输入联系方式!");
                return;
            }
            $.ajax({
                url: '/sysroles',
                type: "POST",
                contentType: 'application/json',
                data: JSON.stringify(readyAddData),
                success: function (res) {
                    if (res.code == 0) {
                        $("#roleAdmin").bootstrapTable("updateRow", {index: index, row: readyAddData});
                        $("#roleAdmin").bootstrapTable("refresh");
                        alert("添加成功!");
                    } else {
                        alert(res.msg);
                    }
                }
            });
        },*/
        'dblclick .startTime': function (e, value, row, index) {

        },
        'dblclick .endTime': function (e, value, row, index) {


        }
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

var removeClick = function (id) {
    //判断是否绑定了click事件
    var objEvt = $._data($("#" + id + "")[0], "events");
    if (objEvt && objEvt["click"]) $("#" + id + "").unbind("click");
}