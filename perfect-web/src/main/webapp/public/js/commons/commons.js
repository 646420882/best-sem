/**
 * @author  xiaowei
 * @description 公共类js包，包含一些全局方法
 * @update 15-9-21. 上午11:26
 */
var $rootScope = {
    basePageSize: 10,
    pageIndex: 0
};

var commons = {
    foRShow: function (type, _this) {
        var exist_selected = jsonData;
        if (!exist_selected.cid) {
            alert("请选择一个推广计划！");
            return;
        }
        if ($(_this).find("ul").attr("class") == "hide") {
            $(_this).find("ul").removeClass("hide");
            $(_this).find("ul").mouseleave(function () {
                $(_this).find("ul").addClass("hide");
            });
        }
        else {
            $(_this).find("ul").addClass("hide");
        }
        $(".Textreplacement").click(function () {
            $(".TB_overlayBG").css({
                display: "block", height: $(document).height()
            });
            $("#findOrReplace").css({
                left: ($("body").width() - $("#download").width()) / 2 - 20 + "px",
                top: ($(window).height() - $("#download").height()) / 2 + $(window).scrollTop() + "px",
                display: "block"
            });
        });

        $("input[name='findText'],input[name='replaceText']").attr("style", "").val('');
        if (type) {
            $("#forType").val(type);
            switch (type) {
                case "creative":
                    $("#forPlace").foRGetPlace(type);
                    break;
                case "adgroup":
                    $("#forPlace").foRGetPlace(type);
                    break;
                case "campaign":
                    $("#forPlace").foRGetPlace(type);
                    break;
                default:
                    $("#forPlace").foRGetPlace(type);
                    break;
            }
        }
    },
    foRClose: function () {
        var _hiddenType = $("#forType");
        _hiddenType.val('');
        $(".TB_overlayBG").css("display", "none");
        $("#findOrReplace").css("display", "none");
    }
}

//自定义表格
$.fn.extend({
    renderGrid: function (model, pageConfig) {
        var _tableId = $(this).attr('id');
        var _page_div = $("<div class='pagination' id='pd" + _tableId + "'/>");
        $(this).parent('div').append(_page_div);

        var defaultData = {},
            _table = $(this);

        if (pageConfig.pagination.params) {
            defaultData = pageConfig.pagination.params;
            defaultData["nowPage"] = pageConfig.pagination.nowPage ? pageConfig.pagination.nowPage : 0;
            defaultData["pageSize"] = pageConfig.pagination.pageSize ? pageConfig.pagination.pageSize : 10;
        } else {
            defaultData = {
                nowPage: pageConfig.pagination.nowPage ? pageConfig.pagination.nowPage : 0,
                pageSize: pageConfig.pagination.pageSize ? pageConfig.pagination.pageSize : 0
            };
        }
        var _load_tr = $("<tr/>");
        _load_tr.append($("<td class='text-info'  colspan='" + model.length + "' />").append($("<span class='help-block'>数据加载中...</span>")));
        _table.append(_load_tr);
        $.post(pageConfig.url, defaultData, function (res) {
            _table.empty();
            $("#pd" + _table.attr("id")).empty();
            var json = eval("(" + res + ")");
            $.renderData(_table.attr("id"), model, pageConfig, json.list);
            var opt = {
                items_per_page: $rootScope.basePageSize,
                num_display_entries: 10,
                num_edge_entries: 2,
                current_page: $rootScope.pageIndex,
                prev_show_always: false,
                next_show_always: false,
                callback: function (current_page) {
                    if ($rootScope.pageIndex == current_page)return false;
                    $rootScope.pageIndex = current_page;
                    _table.append(_load_tr);
                    defaultData["nowPage"] = current_page;
                    $.post(pageConfig.url, defaultData, function (data) {
                        var json = eval("(" + data + ")");
                        if (json.list.length) {
                            $.renderData(_table.attr("id"), model, pageConfig, json.list);
                        }
                    });
                    return false;
                }
            };
            $("#pd" + _table.attr("id")).pagination(json.totalCount, opt);
        });
    },
    foRGetPlace: function (type) {
        $(this).empty();
        var forTitle = "替换所选项目中的文字(不能替换已提交的关键字名称)";
        switch (type) {
            case "creative":
                $("#forTitle").html("替换项目中的文字");
                var _p_opt = "<option value='cTitle'>创意标题</option>" +
                    "<option value='cDesc1'>创意描述1</option>" +
                    "<option value='cDesc2'>创意描述2</option>" +
                    "<option value='titleAndDesc'>创意标题和描述</option>" +
                    "<option value='pcUrl'>默认访问Url</option>" +
                    "<option value='pcsUrl'>默认显示Url</option>" +
                    "<option value='pcAllUrl'>默认访问Url和默认显示Url</option>" +
                    "<option value='mibUrl'>移动访问Url</option>" +
                    "<option value='mibsUrl'>移动显示Url</option>" +
                    "<option value='mibAllUrl'>移动访问Url和移动显示Url</option>" +
                    "<option value='AllUrl'>全部Url</option>";
                $(this).append(_p_opt);
                break;
            case "adgroup":
                $("#forTitle").html("单元标题");
                var _p_opt = "<option value='adgroupName'>推广单元名称</option>";
                $(this).append(_p_opt);
                break;
            case "campaign":
                $("#forTitle").html("计划标题");
                var _p_opt = "<option value='campaignName'>推广计划名称</option>";
                $(this).append(_p_opt);
                break;
            default:
                $("#forTitle").html(forTitle);
                var _p_opt = "<option value='keyword'>关键字</option>" +
                    "<option value='pcUrl'>访问Url</option>" +
                    "<option value='mibUrl'>移动访问Url</option>" +
                    "<option value='allUrl'>全部Url</option>";
                $(this).append(_p_opt);
                break;
        }
    },
    foRSubmit: function (url, params, cb) {
        var errorCount = 0;
        var formData = {};
        var inputs = $(this).find("input[type='text']");
        inputs.each(function (i, o) {
            var name = $(o).attr("name");
            if (name) {
                if ($(o).attr("required")) {
                    if ($(o).val() == "") {
                        $(o).attr("style", "border:1px solid red;");
                        errorCount++;
                    } else {
                        $(o).attr("style", "");
                        formData[name] = $(o).val();
                    }
                } else {
                    if ($(o).val()) {
                        $(o).attr("style", "");
                        formData[name] = $(o).val();
                    }
                }
            }
        });
        var checkBoxs = $(this).find("input[type='checkbox']");
        for (var i = 0; i < checkBoxs.length; i++) {
            if (checkBoxs[i].name && $(checkBoxs[i]).prop("checked")) {
                formData[checkBoxs[i].name] = true;
            }
        }

        var selects = $(this).find("select[no-sub!='true']");
        selects.each(function (i, o) {
            var name = $(o).attr("name");
            if (name && $(o).val()) {
                formData[name] = $(o).val();
            }
        });
        formData["type"] = params.type;
        formData["forType"] = params.forType;
        if (!params.forType) {
            formData["checkData"] = params.checkData.toString();
        }
        if (params.campaignId) {
            formData["campaignId"] = params.campaignId;
        }
        if (!errorCount) {
            if (url) {
                var ajaxbg = $("#background,#progressBar");
                ajaxbg.hide();
                    $(document).ajaxStart(function () {
                        ajaxbg.show();
                    })

                $.ajax({
                    url: url,
                    data: JSON.stringify(formData),
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    success: function (result) {
                        if (cb) {
                            cb(result, errorCount);
                        }
                        $(document).ajaxStop(function () {
                            ajaxbg.hide();
                        });
                    }
                });
            }
        } else {
            alert("表单有错误，请正确操作！");
        }
    }
});
$.extend({
    renderData: function (tableId, model, config, data) {
        $("#" + tableId).empty();
        //renderHeader
        if (model.length) {
            var _thead = $("<thead/>");
            var _tr = $("<tr/>");
            config.headCls ? _tr.attr("class", config.headCls) : '';
            if (config.checkbox) {
                var _td = $("<th/>");
                _td.append($("<input type='checkbox' />"));
                _tr.append(_td);
            }
            model.forEach(function (item, index) {
                var _td = $("<th/>");
                switch (item.type) {
                    case "button":
                        var cls = item.cls ? item.cls : '';
                        var text = item.label ? item.label : '&nbsp;';
                        if (item.listener)
                            _td.append("<input type='button' onclick='" + item.listener + "' value='" + text + "' class='" + cls + "'/>");
                        else
                            _td.append("<input type='button' value='" + text + "' class='" + cls + "' />");
                        break;
                    case "int":
                        _td.append(item.label);
                        break;
                    default:
                        _td.append(item.label);
                        break;
                }
                _tr.append(_td);
            });
            _thead.append(_tr);
            $("#" + tableId).append(_thead);
        }


        //renderBody
        var _tbody = $("<tbody/>");
        if (data) {
            if (data.length) {
                data.forEach(function (e, index) {
                    var _tr = $("<tr/>");
                    var _trClass = index % 2 == 0 ? config.rowsClsO : config.rowsClsJ;
                    _tr.attr("class", _trClass);
                    var _td = $("<td/>");
                    if (config.checkbox) {
                        _td.append($("<input type='checkbox' name='child" + index + "'/>"));
                        _tr.append(_td);
                    }
                    model.forEach(function (item, i) {
                        var _td = $("<td/>");
                        if (config.dataRender) {
                            _td.append(config.dataRender(e[item.name], item.name, e, _tr));
                            _tr.append(_td);
                        } else {
                            _td.append(e[item.name]);
                            _tr.append(_td);
                        }
                        if (item.type == 'button') {
                            _td.append("<input value='" + item.label + "' type='button' class='" + item.cls + "'>");
                            _tr.append(_td);
                        }
                    });
                    _tbody.append(_tr);
                });
                $("#" + tableId).append(_tbody);
            }
        }
    },
    foROk: function (_this) {
        var form = $(_this).parents("form");
        var checkType = $("select[name='checkType'] :selected");
        var foR_params = {};
        var forType = $("#forType").val();
        if (checkType.val() == 0) {
            var checked_data = [];
            var checkChildren = $("input[name='" + forType + "Check']");
            for (var i = 0; i < checkChildren.length; i++) {
                if (checkChildren[i].checked == true) {
                    checked_data.push(checkChildren[i].value);
                }
            }
            if (!checked_data.length) {
                alert("您没有选择要所需物料!");
                return;
            }
            foR_params = {type: forType, forType: 0, checkData: checked_data};
        } else {
            foR_params = {type: forType, forType: 1, campaignId: jsonData.cid};
        }
        form.foRSubmit("../assistantCommons/checkSome", foR_params, function (result) {
            if (result.data) {
                $.foRComplete({type: foR_params.type, forType: foR_params.forType, data: result.data});
                commons.foRClose();
            }
        });
    },
    foRCheckAll: function (type) {
        var checkIdStr = "keywordCheck";
        switch (type) {
            case "creativeAllCheck":
                checkIdStr = "creativeCheck";
                break;
            case "adgroupAllCheck":
                checkIdStr = "adgroupCheck";
                break;
            case "campaignAllCheck":
                checkIdStr = "campaignCheck";
                break;
        }
        var all_check_input = $("input[name='" + type + "']");
        if (all_check_input.prop("checked")) {
            var children_checks = document.getElementsByName(checkIdStr);
            for (var i = 0; i < children_checks.length; i++) {
                children_checks[i].checked = true;
            }
        }
        else {
            var children_checks = document.getElementsByName(checkIdStr);
            for (var i = 0; i < children_checks.length; i++) {
                children_checks[i].checked = false;
            }
        }
    },
    foRComplete: function (result) {
        switch (result.type) {
            case "creative":
                break;
            case "adgroup":
                break;
            case "campaign":
                break;
            default:
                $("#tbodyClick").empty();

                if (result.data == null || result.data == undefined || result.data.length == 0) {
                    $("#tbodyClick").html("<tr><td>暂无数据</td></tr>");
                    return;
                }
                for (var i = 0; i < result.data.length; i++) {
                    var html = keywordDataToHtml(result.data[i], i);
                    $("#tbodyClick").append(html);
                    if (i == 0) {
                        setKwdValue($(".firstKeyword"), result.data[i].object.keywordId);
                        if (result.data[i].object.localStatus) {
                            $("#reduction").find("span").removeClass("z_function_hover");
                            $("#reduction").find("span").addClass("zs_top");
                        } else {
                            $("#reduction").find("span").removeClass("zs_top");
                            $("#reduction").find("span").addClass("z_function_hover");
                        }
                    }
                }
                break;
        }
    }
});

//foRSubmit: function () {
//    var gridModel = [
//        {label: '按钮1', name: '', cls: 'testBtn', type: 'button'},
//        {label: '按钮2', name: '', cls: 'testBtn', type: 'button'},
//        {label: '子链一', name: 'zilian1', type: 'string'},
//        {label: '子链er', name: 'zilian2', type: 'string'},
//        {label: '消费', name: 'cost', type: 'int'}
//    ];
//    var gridConfig = {
//        headCls: "list02_top",
//        rowsClsJ: 'list2_box2',
//        rowsClsO: 'list2_box1',
//        checkbox: true,
//        url: "/assistantKeyword/list",
//        pagination: {
//            nowPage: 0,
//            pageSize: 10,
//            params: {
//                cid: 25362491,
//                aid: '',
//                cn: '百思'
//            }
//        },
//        dataRender: function (v, n, tr) {
//            return v;
//        }
//    }
//    $("#testTable").renderGrid(gridModel, gridConfig);
//}
