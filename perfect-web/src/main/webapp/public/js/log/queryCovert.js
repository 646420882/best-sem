/**
 * @author  xiaowei
 * @description
 * @update 15-12-16. 下午7:05
 */
var lq = {
    initUILoaded: false,
    checkLevel: 0,
    currentLevel: 0,
    htmlArr: new Array(),
    initUI: function () {
        if (!this.initUILoaded) {
            $(".black").css({
                height: $(window).height(),
                width: $(window).width()
            });
            this.initAccount();
            this.loadOperation(0);
            radioMenu.forEach(function (i, o) {
                var _li = $("<label>");
                if (o == 0) {
                    _li.addClass("checked");
                }
                _li.attr("name", "radiolab");
                _li.attr("value", i.name);
                _li.html(i.label);
                _li.attr("onclick", "lq.addTopRadioListener(this)");
                $("#radioselect-box").append(_li);
            });
        }
        this.initUILoaded = true;
    },
    initAccount: function () {
        $.ajax({
            url: '/assistant/account',
            type: 'post',
            cache: false,
            dataType: 'json',
            success: function (data) {
                $(".obj-select-list").empty();
                var accountName = data.accountName;
                var accountObj = '<div class="obj-list"><a href="#" name="-1">' + accountName + '</a><div class="obj-list-del"></div></div>';
                $(".obj-select-list").append(accountObj);
                $("#obj-num").html(1);
            },
            error: function () {
                console.log("error");
            }
        });
    },
    initLevelTitle: function (level, text, id) {
        switch (level) {
            case 1:
                $("#boxheader").empty();
                var _a = $("<a href='javascript:;' onclick='lq.initOtherLevel(1," + id + ")' id='" + id + "'>计划列表</a> >> 计划:<span>" + text + "</span>");
                $("#boxheader").html(_a);
                break;
            case 2:
                var cid = $("#boxheader").find("a:eq(0)").attr("id");
                var _text = $("#boxheader").find("span:eq(0)").html();
                $("#boxheader").find("span:eq(0)").html("<a href='javascript:;' onclick='lq.initOtherLevel(2," + cid + ",\"" + _text + "\")'>" + _text + "</a> >> 单元:<span>" + text + "</span>")
                break;
        }

    },
    initOtherLevel: function (level, id, text) {
        if (level == 1) {
            $("#boxheader").html("<span>计划列表</span>")
        }
        if (level == 2) {
            this.initLevelTitle(1, text, id);
        }
        if (level == 3 || level == 4) {
            this.initLevelTitle(2, text, id);
        }
        if (lq.currentLevel == 4 & level == 3) {
            level = 4
        }
        $.ajax({
            url: '/assistant/getLevel',
            type: 'post',
            data: {level: level, id: id},
            dataType: 'json',
            success: function (res) {

                if (res != null) {
                    $("#query-result").empty();
                    var _ul = $("<ul>");
                    _ul.attr("id", "objList");
                    if (lq.currentLevel == level) {
                        res.forEach(function (item) {
                            var _li = $("<li>");
                            var _a = $("<a>");
                            _a.attr("href", "javascript:;");
                            _a.attr("id", item.id);
                            _a.html("添加");
                            _a.attr("onclick", 'lq.addItem(this)');
                            _li.append(_a);
                            var _span = $("<span>");
                            _span.html(item.name);
                            _li.append(_span);
                            _ul.append(_li);
                        });
                    } else {
                        level++;
                        res.forEach(function (item) {
                            var _li = $("<li>");
                            var _a = $("<a>");
                            _a.attr("href", "javascript:;");
                            _a.attr("onclick", "lq.initOtherLevel(" + level + "," + item.id + ",\'" + item.name + "\')");
                            _a.attr("id", item.id);
                            var _span = $("<span>");
                            _span.html(item.name);
                            _a.append(_span);
                            _li.append(_a);
                            _ul.append(_li);
                        });

                    }
                    $("#query-result").append(_ul);

                    lq.htmlArr = $("#objList li")
                }
            }
        })
    },
    addTopRadioListener: function (_this) {
        $(".tip").show();
        $("label[name='radiolab']").each(function (i, o) {
            if ($(o).attr("class") == "checked") {
                lq.checkLevel = i;
            }
        });
        $("label[name='radiolab']").each(function (i, o) {
            $(o).removeClass("checked");
            if ($(o).attr("value") == $(_this).attr("value")) {
                $(o).addClass("checked");
                lq.currentLevel = i;

            }
        });

        $(".plan-box").hide();
    },
    cancelCheck: function (_this) {
        var _cls = $(_this).attr("class");
        if (_cls == "cancel" || _cls == "close-tip") {
            $(".tip").hide();
            $("label[name='radiolab']").each(function (i, o) {
                $(o).removeClass("checked");
            });
            $("label[name='radiolab']:eq(" + lq.checkLevel + ")").addClass("checked");
            lq.loadOperation(lq.checkLevel);
        } else {
            $(".tip").hide();
            $(".obj-select-list").empty();
            $("#obj-num").html(0);
            if (lq.currentLevel == 0) {
                lq.initAccount();
                lq.loadOperation(0);
            }
            lq.loadOperation(lq.currentLevel);
            $("#tbodydata").empty();
        }
    },
    addObj: function () {
        if (this.currentLevel) {
            $(".plan-box").show();
            $("#query-result").empty();
            lq.initOtherLevel(1);
        }
    },
    logQuery: function () {
        var slp = {pageNo: 0, pageSize: 50};
        var levelData = $(".obj-select-list").find("a");
        var level = [];
        var property = [];
        var _tmpLevel = $("input[name='top']");
        _tmpLevel.each(function (i, o) {
            if ($(o).prop("checked") && !$(o).attr("disabled")) {
                //level.push($(o).attr("index"));
                var _rightCheck = $(o).parents("div").next(".list-view-r").find("input");
                if (_rightCheck.length) {
                    _rightCheck.each(function (c, b) {
                        if ($(b).prop("checked")) {
                            if ($(b).val() != "on") {
                                property.push($(b).val());
                            }
                        }
                    });
                }
            }
        });
        slp["level"] = lq.currentLevel;
        slp["property"] = property.toString();
        //if (level.length) {
        //    slp["level"] = level.toString();
        //}
        var idData = [];
        if (levelData.size()) {
            levelData.each(function (i, o) {
                idData.push($(o).attr("name"));
            });
            slp["oids"] = idData.toString();
        } else {
            alert("请选择对象！");
            return;
        }
        var _tmpStart = new Date().Format("yyyy-MM-dd 00:00:00");
        var _tmpEnd = new Date().Format("yyyy-MM-dd 23:59:59");
        var startTime = parseInt($("#start_time").val());
        var endTime = parseInt($("#end_time").val());
        if (!startTime) {
            startTime = new Date(_tmpStart).getTime();
        }
        if (!endTime) {
            endTime = new Date(_tmpEnd).getTime();
        }
        slp["start"] = startTime;
        slp["end"] = endTime;
        var selectPageSize = $("#pageSizeSelected :selected").val();
        if (selectPageSize) {
            slp.pageSize = selectPageSize;
        }
        lq.loadLogData(slp, function (result) {
            lq.pagination(slp, result);
        });
    },
    loadLogData: function (params, func) {
        $.ajax({
            url: '/assistant/queryLog',
            type: 'post',
            data: params,
            cache: false,
            dataType: 'json',
            success: function (data) {
                $("#tbodydata").empty();
                if (data.list.length) {
                    data.list.forEach(function (item) {
                        var _tr = "<tr> <td>" + new Date(item.time).Format("yyyy-MM-dd hh:mm") + "</td><td>" + (item.userName ? item.userName : '空') + "</td><td>" + lq.logDataConvert(item.type) + "</td><td>" + item.name + "</td><td>" + item.text + "</td></tr>";
                        $("#tbodydata").append(_tr);
                    });
                    if (func) {
                        func(data);
                    }
                } else {
                    $("#tbodydata").append("<tr><td colspan='5' style='text-align: center;'><span style='color:red;'>暂无数据..</span></td></tr>");
                }
            }
        });
    },
    pageIndex: 0,
    pagination: function (params, result) {
        var opt = {
            items_per_page: params.pageSize,
            num_display_entries: 3,
            num_edge_entries: 2,
            current_page: lq.pageIndex,
            prev_show_always: false,
            next_show_always: false,
            prev_text: "上一页",
            next_text: "下一页",
            callback: function (current_page, jq) {
                if (lq.pageIndex == current_page)return false;
                lq.pageIndex = current_page;
                params["pageNo"] = current_page;
                lq.loadLogData(params);
                return false;
            }
        }
        $("#logPagination").pagination(result.totalCount, opt);


    },
    logDataConvert: function (data, type) {
        var _data = data.toString();
        var type = parseInt(_data.split("0")[1]);
        switch (type) {
            case 1:
                return "词";
            case 2:
                return "创";
            case 3:
                return "单";
            case 4:
                return "计";
        }

    },
    /**
     * 切换层级时加载日志选项
     * @param currentLevel
     */
    loadOperation: function (currentLevel) {
        var paramList = $("#operation-tip-list");
        paramList.empty();
        radioMenu.forEach(function (item, index) {
            var _divTop = $("<div>");
            _divTop.attr("class", "list-view-box");

            var _left_div = $("<div>");
            _left_div.attr("class", "list-view");

            //var _mainInput = $("<input type='checkbox' name='top' value='" + item.name + "' index='" + index + "' checked='checked' onchange='lq.checkChild(this)'/>");
            var _mainInput = $("<input/>");
            _mainInput.attr("type", "checkbox");
            _mainInput.attr("name", "top");
            _mainInput.attr("value", item.name);
            _mainInput.attr("index", index);
            _mainInput.attr("onchange", "lq.checkChild(this)");
            _mainInput.attr("checked", "checked");
            if (index <= (currentLevel - 1)) {
                _mainInput.attr("disabled", "disabled");
            }
            if (currentLevel == 3 || currentLevel == 4) {
                if (index != currentLevel) {
                    _mainInput.attr("disabled", "disabled");
                }
            }
            var _mainLabel = $("<label >" + item.label + "</label>");
            _left_div.append(_mainInput);
            _left_div.append(_mainLabel);
            _divTop.append(_left_div);

            var _right_div = $("<div>");
            _right_div.attr("class", "list-view-r");

            var _right_ul = $("<ul>");
            item.opt.forEach(function (chi) {
                var _right_ul_li = $("<li>");
                var _val = "value=";
                if (chi.name) {
                    _val += chi.name;
                } else {
                    if (chi.type) {
                        _val += chi.value;
                    } else {
                        _val = "";
                    }
                }
                var _right_input = $("<input type='checkbox' name='chi' " + _val + " checked='checked' />");
                if (index <= (currentLevel - 1)) {
                    _right_input = $("<input type='checkbox' name='chi' " + _val + " checked='checked' disabled='disabled'/>");
                }
                if (currentLevel == 3 || currentLevel == 4) {
                    if (index != currentLevel) {
                        _mainInput = $("<input type='checkbox' name='chi' " + _val + " checked='checked' disabled='disabled'/>");
                    }
                }
                var _right_label = $("<label>" + chi.label + "</label>");
                _right_ul_li.append(_right_input);
                _right_ul_li.append(_right_label);
                _right_ul.append(_right_ul_li);
            });
            _right_div.append(_right_ul);
            _divTop.append(_right_div);
            paramList.append(_divTop);
        });
    },
    showOperation: function () {
        $(".operation-tip").show();
    },
    checkChild: function (_this) {
        var nextCheckbox = $(_this).parents("div").next(".list-view-r").find("input");
        if ($(_this).prop("checked")) {
            nextCheckbox.prop("checked", true);
        } else {
            nextCheckbox.prop("checked", false);
        }
    },
    hideOperation: function () {
        $(".operation-tip").hide();
    },
    addItem: function (_this) {
        $(_this).addClass('default');
        var ad_text = $(_this).siblings().text();
        var ad_id = $(_this).attr('id');
        if ($(".obj-select-list div.obj-list a[name=" + ad_id + "]").length <= 0) {
            var _div = $("<div>");
            _div.attr("class", "obj-list");
            var _a = $("<a>");
            _a.attr("href", "#");
            _a.attr("name", ad_id);
            _a.html(ad_text);
            _div.append(_a);
            var del_div = $("<div>");
            del_div.addClass("obj-list-del");
            _div.append(del_div);
            $(".obj-select-list").append(_div);

            this.itemHover();
        }
        $("#obj-num").html($(".obj-select-list").find(".obj-list").size())
    },
    itemHover: function () {
        $(".obj-list").hover(function () {
            $(this).children('.obj-list-del').show();
            $(".obj-list-del").click(function (event) {
                $(this).parent().remove();
                var thisN = $(this).siblings().attr('name');
                $(".query-result").find('#' + thisN).removeClass('default');
                $("#obj-num").html($(".obj-select-list").find(".obj-list").size())
            });
        }, function () {
            $(this).children('.obj-list-del').hide();
        });
    },
    searchClick: function (arr) {
        var content = '';
        if (arr.length > 0) {
            content = '<ul id="objList">';
            for (var i = 0; i < arr.length; i++) {
                var item = arr[i];
                content += '<li>';
                content += $(item).html();
                content += '</li>';
            }
            content += "</ul>"
        }
        var queryR = $("#query-result");
        queryR.html(content);
    }
}
$(function () {
    $(".close-ico").click(function () {
        $(".plan-box").hide();
    });
    $('#reservation').daterangepicker({
            "showDropdowns": true,
            "timePicker24Hour": true,
            timePicker: false,
            timePickerIncrement: 30,
            "linkedCalendars": false,
            "format": "YYYY-MM-DD",
            autoUpdateInput: true,
            "locale": {
                "format": "YYYY-MM-DD",
                "separator": " - ",
                "applyLabel": "确定",
                "cancelLabel": "关闭",
                "fromLabel": "From",
                "toLabel": "To",
                "customRangeLabel": "Custom",
                "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"
                ],
                "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"]
            }
        },
        function (start, end, label) {
            $("#start_time").val(new Date(start).getTime());
            $("#end_time").val(new Date(end).getTime());
        });

    $("#btnsearch").click(function () {
        var newArr = new Array();
        var keyStr = $("#txtkey").val();
        if (keyStr != "") {
            $.each(lq.htmlArr, function (i) {
                var name = $(this).find("span").text();
                if (name.indexOf(keyStr) != -1) {
                    newArr.push(lq.htmlArr[i]);
                }
            });
        }
        else {
            newArr = lq.htmlArr;
        }
        lq.searchClick(newArr);
    });
    $("#pageSizeSelected").val(50);
    $("#pageSizeSelected").change(function () {
        lq.logQuery();
    });
})
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
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
