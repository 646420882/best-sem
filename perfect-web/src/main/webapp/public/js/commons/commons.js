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
        var tabMenu = $("#tabMenu").find("li:eq(4)").attr("class");
        if (!tabMenu) {
            if (!exist_selected.cid) {
                alert("请选择一个推广计划！");
                return;
            }
        }
        if ($(".assstant_editor").css("display") == "none") {
            var TabtopFirst = $(_this).offset().top + $(_this).outerHeight() + "px";
            var TableftNext = $(_this).offset().left + $(_this).outerWidth() + -$(_this).width() + "px";
            $(".assstant_editor").css("top", TabtopFirst);
            $(".assstant_editor").css("left", TableftNext);
            $(".assstant_editor").show();
            $(".assstant_editor ").mouseleave(function () {
                $(".assstant_editor").hide();
            });
        }
        else {
            $(".assstant_editor").hide();
        }
        $("#Textreplacement").click(function () {
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
        $("input[name='findText'],input[name='replaceText']").attr("style", "").val('');
        var _hiddenType = $("#forType");
        _hiddenType.val('');
        $(".TB_overlayBG").css("display", "none");
        $("#findOrReplace").css("display", "none");
        $("#findBatchDel").css("display", "none");
    },
    batchDel: function () {
        if ($("#forType").val() == "campaign") {
            $("#pageNew").empty();
            $("#pageNew").append("<div class='fl'><label>是否删除当前所选中的计划以及计划下的单元、创意、关键词</label></div>")
        }
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $("#findBatchDel").css({
            left: ($("body").width() - $("#download").width()) / 2 - 20 + "px",
            top: ($(window).height() - $("#download").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    }

}

var editCommons = {
    EditType: "keyword",
    EditTmp: {},
    Copy: function () {
        var type = this.EditType;
        switch (type) {
            case "keyword":
                this.getEditData("keyword", "copy");
                break;
            case "creative":
                this.getEditData("creative", "copy");
                break;
            case "adgroup":
                this.getEditData("adgroup", "copy");
                break;
            case "campaign":
                this.getEditData("campaign", "copy");
                break;
        }
    },
    Cut: function () {
        var type = this.EditType;
        switch (type) {
            case "keyword":
                this.getEditData("keyword", "cut");
                break;
            case "creative":
                this.getEditData("creative", "cut");
                break;
            case "adgroup":
                this.getEditData("adgroup", "cut");
                break;
            case "campaign":
                alert("推广计划暂不支持剪切功能！");
                break;
        }
    },
    getEditData: function (type, editType) {
        var cutCheck = $("input[name='" + type + "Check']");
        var selectedData = [];
        cutCheck.each(function (i, o) {
            if ($(o).prop("checked")) {
                selectedData.push($(o).val());
            }
        });
        var edtTypeStr = editType != "copy" ? "剪切" : "复制";
        if (selectedData.length) {
            this.EditTmp["type"] = type;
            this.EditTmp["editType"] = editType;
            this.EditTmp["editData"] = selectedData.toString();
            alert("已" + edtTypeStr + "到粘贴板！");
        } else {
            alert("请选择要" + edtTypeStr + "的数据！");
        }
    },
    Parse: function () {
        var type = this.EditType;
        switch (type) {
            case "keyword":
                this.ParseData("keyword", function (result) {
                    if (result.msg) {
                        getKwdList(0);
                    } else {
                        alert("粘贴失败");
                    }
                });
                break;
            case "creative":
                this.ParseData("creative", function (result) {
                    if (result.msg) {
                        if (jsonData.cid != null) {
                            if (jsonData.cid != null && jsonData.aid != null) {
                                getCreativeUnit(jsonData);
                            } else {
                                getCreativePlan(jsonData.cid);
                            }
                        }
                    } else {
                        alert("粘贴失败");
                    }
                });
                break;
            case "adgroup":
                this.ParseData("adgroup", function (result) {
                    if (result.msg) {
                        if (jsonData.cid != null) {
                            getAdgroupPlan(jsonData.cid, jsonData.cn);
                            loadTree();
                        }
                    } else {
                        alert("粘贴失败");
                    }
                });
                break;
            case "campaign":
                this.ParseData("campaign", function (result) {
                    if (result.msg) {
                        getCampaignList(0);
                        loadTree();
                    } else {
                        alert("粘贴失败");
                    }
                });
                break;
        }
    },
    ParseData: function (type, func) {
        if (this.EditTmp.editType && this.EditTmp.editData.length) {
            if (type == "keyword" || type == "creative") {
                if (!jsonData.aid) {
                    alert("请选择要粘贴的单元");
                    return;
                }
            }
            if (type == "adgroup") {
                if (!jsonData.cid) {
                    alert("请选择要粘贴的计划");
                    return;
                }
            }

            if (this.EditTmp.type == type) {
                $.ajax({
                    url: "../assistantCommons/dataParse",
                    data: JSON.stringify({
                        type: this.EditTmp.type,
                        editType: this.EditTmp.editType,
                        editData: this.EditTmp.editData,
                        aid: jsonData.aid,
                        cid: jsonData.cid
                    }),
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    success: function (result) {
                        if (func) {
                            func(result);
                        }
                    }
                });
            } else {
                alert("不同层级的数据无法粘贴，请选择相同的层级。")
            }
        } else {
            alert("粘贴板没有数据！")
        }
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
        if (params.adgroupId) {
            formData["adgroupId"] = params.adgroupId;
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
                $.leveComplete("creative", result);
                break;
            case "adgroup":
                $.leveComplete("adgroup", result);
                break;
            case "campaign":
                $.leveComplete("campaign", result);
                break;
            default:
                $.leveComplete("keyword", result);
                break;
        }
    },
    leveComplete: function (leveType, result) {
        switch (leveType) {
            case "creative":
                var _createTable = $("#createTable tbody");
                if (result.data) {
                    if (result.data.length) {
                        var json = result.data;
                        _createTable.empty();
                        var replaceText = $("input[name='replaceText']").val();
                        var _trClass = "";
                        for (var i = 0; i < json.length; i++) {
                            var _id = json[i].creativeId != null ? json[i].creativeId : json[i].id;
                            var _edit = json[i].localStatus != null ? json[i].localStatus : -1;
                            var ls = replaceText ? getLocalStatus(2) : getLocalStatus(parseInt(_edit));
                            _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
                            var _tbody = "<tr class=" + _trClass + " onclick='on(this);''>" +
                                "<td >&nbsp;<input type='checkbox' name='creativeCheck' value='" + _id + "'/></td>" +
                                "<td >&nbsp;<input type='hidden' value='" + _id + "'/></td>" +
                                "<td >" + until.substring(10, json[i].title) + "</td>" +
                                " <td >" + until.substring(10, json[i].description1) + "</td>" +
                                " <td >" + until.substring(10, json[i].description2) + "</td>" +
                                " <td ><a href='" + json[i].pcDestinationUrl + "' target='_blank'>" + until.substring(10, json[i].pcDestinationUrl) + "</a></td>" +
                                " <td >" + until.substring(10, json[i].pcDisplayUrl) + "</td>" +
                                " <td>" + until.substring(10, json[i].mobileDestinationUrl) + "</td>" +
                                " <td >" + until.substring(10, json[i].mobileDisplayUrl) + "</td>" +
                                " <td >" + until.convert(json[i].pause, "启用:暂停") + "</td>" +
                                " <td >" + until.getCreativeStatus(parseInt(json[i].status)) + "<input type='hidden' value='" + json[i].status + "'/></td>" +
                                "<td>" + until.convertDeviceByNum(parseInt(json[i].devicePreference)) + "</td>" +
                                " <td >" + ls + "</td>" +
                                "</tr>";
                            _createTable.append(_tbody);
                        }
                    } else {
                        _createTable.empty();
                        _createTable.append("<tr><td>没有找到类似的数据</td></tr>");
                    }
                } else {
                    _createTable.empty();
                    _createTable.append("<tr><td>没有找到类似的数据</td></tr>");
                }
                break;
            case "adgroup":
                var _adGroudTable = $("#adGroupTable tbody");
                if (result.data) {
                    if (result.data.length > 0) {
                        _adGroudTable.empty();
                        var replaceText = $("input[name='replaceText']").val();
                        var _trClass = "";
                        var json = result.data;
                        for (var i = 0; i < json.length; i++) {
                            _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
                            var _id = json[i].adgroupId != null ? json[i].adgroupId : json[i].id;
                            var _maxPrice = json[i].maxPrice != null ? json[i].maxPrice : 0.0;
                            var nn = json[i].negativeWords != null ? json[i].negativeWords : "";
                            var ne = json[i].exactNegativeWords != null ? json[i].exactNegativeWords : "";
                            var _edit = json[i].localStatus != null ? json[i].localStatus : -1;
                            var ls = replaceText ? getLocalStatus(2) : getLocalStatus(parseInt(_edit));
                            var _tbody = "<tr class=" + _trClass + " onclick=aon(this)>" +
                                "<td ><input type='checkbox' name='adgroupCheck' value='" + _id + "'/></td>" +
                                "<td >&nbsp;<input type='hidden' value='" + _id + "'/></td>" +
                                "<td >" + json[i].adgroupName + "</td>" +
                                "<td ><input type='hidden' value='" + json[i].status + "'/>" + until.getAdgroupStatus(json[i].status) + "</td>" +
                                "<td >" + until.convert(json[i].pause, "启用:暂停") + "</td>" +
                                "<td >" + parseFloat(_maxPrice).toFixed(2) + "</td>" +
                                "<td ><input type='hidden' value='" + nn + "'><input type='hidden' value='" + ne + "'>" + getNoAdgroupLabel(nn, ne) + "</td>" +
                                "<td >" + json[i].campaignName + "</td>" +
                                "<td >" + ls + "</td>" +
                                "</tr>";
                            _adGroudTable.append(_tbody);
                        }
                    } else {
                        _adGroudTable.empty();
                        _adGroudTable.append("<tr><td>没有找到类似的数据</td></tr>");
                    }
                } else {
                    _adGroudTable.empty();
                    _adGroudTable.append("<tr><td>没有找到类似的数据</td></tr>");
                }
                break;
            case "campaign":
                $("#tbodyClick5").empty();
                if (result.data) {
                    if (result.data.length == 0) {
                        $("#tbodyClick5").append("<tr><td>没有找到类似的数据</td></tr>");
                        return;
                    }
                    for (var i = 0; i < result.data.length; i++) {
                        var html = campaignDataToHtml(result.data[i], i);
                        $("#tbodyClick5").append(html);
                        if (i == 0) {
                            setCampaignValue(".firstCampaign", result.data[i].campaignId);
                            if (result.data[i].localStatus != null) {
                                $("#reduction_caipamgin").find("span").removeClass("z_function_hover");
                                $("#reduction_caipamgin").find("span").addClass("zs_top");
                            } else {
                                $("#reduction_caipamgin").find("span").removeClass("zs_top");
                                $("#reduction_caipamgin").find("span").addClass("z_function_hover");
                            }
                        }
                    }
                    loadTree();
                } else {
                    $("#tbodyClick5").append("<tr><td>没有找到类似的数据</td></tr>");
                }
                break;
            default :
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
    },
    foBatch: function (_this) {
        var form = $(_this).parents("form");
        var checkType = $("#checkType option:selected");
        var foR_params = {};
        var forType = $("#forType").val();
        if (checkType.val() == 0 || forType == "campaign") {
            var checked_data = [];
            var checkChildren = getMaterials(forType);
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
            if (jsonData.cid == null) {
                alert("至少选择一个计划")
                return;
            }
            foR_params = {type: forType, forType: 1, campaignId: jsonData.cid, adgroupId: jsonData.aid};
        }
        form.foRSubmit("../assistantCommons/batchDel", foR_params, function (result) {
            commons.foRClose();
            if (checkType.val() == 0 || checkType.val() == undefined) {
                getMaterials(forType).each(function () {
                    if (this.checked) {
                        $(this).parent().parent().find(">td:last").html("<span class='error' step='3'></span>")
                    }
                });
            } else {
                getMaterials(forType).each(function () {
                    $(this).parent().parent().find(">td:last").html("<span class='error' step='3'></span>")
                });
            }

        });
    }
});
function getMaterials(ma) {
    switch (ma) {
        case "keyword":
            return $("input[name='keywordCheck']");
        case "creative":
            return $("input[name='creativeCheck']");
        case "adgroup":
            return $("input[name='adgroupCheck']");
        case "campaign":
            return $("input[name='campaignCheck']");
    }
}
//表格顶部选择弹窗
var tabselect =
    "<select>" +
    "<option value='1'>包含</option>" +
    "<option value='11'>不包含</option>" +
    "<option value='2'>等于</option>" +
    "<option value='22'>不等于</option>" +
    "<option value='3'>开始于</option>" +
    "<option value='33'>截止于</option></select>" +
    "<textarea></textarea>" +
    "<p>最多同时搜1000个文本</p>";
var baiduStatus =
    "<ul>" +
    "<li><input type='checkbox' value='41'>有效</li>" +
    "<li><input type='checkbox' value='42'>暂停推广</li>" +
    "<li><input type='checkbox' value='43'>不宜推广</li>" +
    "<li><input type='checkbox' value='48'>部分无效</li>" +
    "<li><input type='checkbox' value='46'>审核中</li>" +
    "<li><input type='checkbox' value='47'>搜索量过低</li>" +
    "<li><input type='checkbox' value='49'>计算机搜索无效</li>" +
    "<li><input type='checkbox' value='50'>移动搜索无效</li>" +
    "<li><input type='checkbox' value='44'>搜索无效</li>" +
    "<li><input type='checkbox' value='45'>待激活</li>" +
    "<li><input type='checkbox' value='40'>有效-移动URL审核中</li>" +
    "<li class='list_bottom'><input type='checkbox' value='-1'>本地新增</li>" +
    "</ul>";
var Computerquality =
    "<ul class='quality'>" +
    "<li>" + "<input type='checkbox' value='2'>" +
    "<span>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "</span>" + "<b>1</b>" +
    "</li>" +
    "<li>" + "<input type='checkbox' value='4'>" +
    "<span>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "</span>" + "<b>2</b>" +
    "</li>" +
    "<li>" + "<input type='checkbox' value='6'>" +
    "<span>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "</span>" + "<b>3</b>" +
    "</li>" +

    "<li>" + "<input type='checkbox' value='8'>" +
    "<span>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star3.png'>" +
    "</span>"
    + "<b>4</b>" +
    "</li>" +
    "<li>" + "<input type='checkbox' value='10'>" +
    "<span>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star.png'>" +
    "</span>" + "<b>5</b>" +
    "</li>"
    + "</ul>";
var pause =
    "<ul><li><input type='radio' value='-1' name='filterPause'>全部</li>" +
    "<li class='list_bottom'><input type='radio' value='1' name='filterPause'>启用</li>" +
    "<li><input type='radio' value='0' name='filterPause'>暂停</li>";
var price =
    "<input type='number' name='min_points' min='00' max='10' />"
    + "到"
    + "<input type='number' name='max_points' min='00' max='10' />";
var errorMsg = $("#filter_msg");

var TabModel = {
    Show: function (type, _this) {
        if (!jsonData.cid) {
            if (editCommons.EditType != "campaign") {
                alert("请选择一个计划或者单元");
                return;
            }
        }
        $(_this).append("<span class='remove'><img src='../public/img/select.png'></span>");
         var tabtop = $(_this).offset().top + $(_this).outerHeight() + "px";
        var tableft = $(_this).offset().left + $(_this).outerWidth() + -$(_this).width() + "px";
        $("#filterSearchTemplate").css("top", tabtop);
        $("#filterSearchTemplate").css("left", tableft);
        $('#filterSearchTemplate').show(function(){
            document.body.addEventListener('click', boxCloser, false);
        });
        $("#CheckList").empty();
        $("input[name='filterField']").val(type);
        errorMsg.html('');
        switch (type) {
            case "Keyword_name":
                $("#TabTitle").html("关键词名称");
                $("#CheckList").append(tabselect);
                break;
            case "Keyword_state":
                $("#TabTitle").html("关键词状态");
                $("#CheckList").append(baiduStatus);
                break;
            case "Keyword_pause":
                $("#TabTitle").html("启用/暂停");
                $("#CheckList").append(pause);
                break;
            case "Keyword_price":
                $("#TabTitle").html("关键词出价");
                $("#CheckList").append(price);
                break;
            case "Keyword_cquality":
                $("#TabTitle").html("关键词计算机质量度");
                $("#CheckList").append(Computerquality);
                break;
            case "Keyword_mquality":
                $("#TabTitle").html("移动质量度");
                $("#CheckList").append(Computerquality);
                break;
            case "Keyword_matchType":
                $("#TabTitle").html("关键词匹配模式");
                var matching =
                    "<ul><li><input type='checkbox' name='matchType' value='3'>广泛</li>" +
                    "<li><input type='checkbox' id='Phrase' value='2'>短语" +
                    "<ul id='PhraseList'><li><input type='checkbox' name='matchType' value='33'>核心包含</li></li>" +
                    "<li><input type='checkbox' name='matchType' value='11'>同义包含</li>" +
                    "<li><input type='checkbox' name='matchType' value='22'>精确包含</li></ul>" +
                    "<li ><input type='checkbox' name='matchType' value='1'>精确</li>" +
                    "<li class='list_bottom'><input type='checkbox'>基本匹配模式</li>" +
                    "<li><input type='checkbox'>分匹配出价</li>" +
                    "</ul>";
                $("#CheckList").append(matching);
                $("#Phrase").change(function () {
                    if ($(this).is(":checked")) {
                        $(this).next().find("input").prop("checked", true);
                    } else {
                        $(this).next().find("input").prop("checked", false);
                    }
                });
                $("#PhraseList").find("input").change(function () {
                    if ($(this).is(":checked")) {
                        $("#Phrase").prop("checked", true);
                    } else {
                        $("#Phrase").prop("checked", false);
                    }
                });
                break;
            case "Keyword_pcUrl":
                $("#TabTitle").html("关键词访问URL");
                $("#CheckList").append(tabselect);
                break;
            case "Keyword_mibUrl":
                $("#TabTitle").html("关键词移动访问URL");
                $("#CheckList").append(tabselect);
                break;
            case "Creative_title":
                $("#TabTitle").html("创意标题");
                $("#CheckList").append(tabselect);
                break;
            case "Creative_desc1":
                $("#TabTitle").html("创意描述1");
                $("#CheckList").append(tabselect);
                break;
            case "Creative_desc2":
                $("#TabTitle").html("创意描述2");
                $("#CheckList").append(tabselect);
                break;
            case "Creative_pcUrl":
                $("#TabTitle").html("创意默认访问URL");
                $("#CheckList").append(tabselect);
                break;
            case "Creative_pcsUrl":
                $("#TabTitle").html("创意默认显示URL");
                $("#CheckList").append(tabselect);
                break;
            case "Creative_mibUrl":
                $("#TabTitle").html("创意移动显示URL");
                $("#CheckList").append(tabselect);
                break;
            case "Creative_mibsUrl":
                $("#TabTitle").html("创意移动显示URL");
                $("#CheckList").append(tabselect);
                break;
            case "Creative_pause":
                $("#TabTitle").html("启用/暂停");
                $("#CheckList").append(pause);
                break;
            case "Creative_state":
                $("#TabTitle").html("创意状态");
                var CreativeState =
                    "<ul><li><input type='checkbox' value='51'>有效</li>" +
                    "<li><input type='checkbox' value='53'>不宜推广</li>" +
                    "<ul><li><input type='checkbox' value='52'>暂停推广</li>" +
                    "<li><input type='checkbox' value='55'>审核中</li>" +
                    "<li><input type='checkbox' value='54'>待激活</li></ul>" +
                    "<li><input type='checkbox' value='56'>部分无效</li>" +
                    "<li><input type='checkbox' value='57'>有效-移动URL审核中</li>" +
                    "<li class='list_bottom'><input type='checkbox' value='-1'>本地新增</li>" +
                    "</ul>";
                $("#CheckList").append(CreativeState);
                break;
            case "Creative_quipment":
                $("#TabTitle").html("创意设备偏好");
                var CreativeEquipment =
                    "<ul><li><input type='radio' name='filterQuipment' value='-1'>全部</li>" +
                    "<li class='list_bottom'><input type='radio' name='filterQuipment'  value='0'>全部设备</li>" +
                    "<li><input type='radio' name='filterQuipment'  value='1'>移动设备优先</li>";
                $("#CheckList").append(CreativeEquipment);
                break;
            case "Adgroup_name":
                $("#TabTitle").html("推广单元名称");
                $("#CheckList").append(tabselect);
                break;
            case "Adgroup_state":
                $("#TabTitle").html("推广单元状态");
                var ExtensionState = "<ul><li><input type='checkbox' value='31'>有效</li>" +
                    "<li><input type='checkbox' value='32'>暂停推广</li>" +
                    "<li><input type='checkbox' value='33'>推广计划暂停推广</li>" +
                    "<li class='list_bottom'><input type='checkbox' value='-1'>本地新增</li>" +
                    "</ul>";
                $("#CheckList").append(ExtensionState);
                break;
            case "Adgroup_pause":
                $("#TabTitle").html("启动/暂停");
                $("#CheckList").append(pause);
                break;
            case "Adgroup_price":
                $("#TabTitle").html("出价");
                $("#CheckList").append(price);
                break;
            case "Campaign_name":
                $("#TabTitle").html("推广计划名称");
                $("#CheckList").append(tabselect);
                break;
            case "Campaign_state":
                $("#TabTitle").html("推广计划状态");
                var PromotionState =
                    "<ul><li><input type='checkbox' value='21'>有效</li>" +
                    "<li><input type='checkbox' value='23'>暂停推广</li>" +
                    "<li><input type='checkbox' value='22'>处在暂停阶段</li>" +
                    "<li><input type='checkbox' value='24'>推广计划预算不足</li>" +
                    "<li><input type='checkbox' value='24'>账户预算部足</li>" +
                    "<li class='list_bottom'><input type='checkbox' value='-1'>本地新增</li>";
                $("#CheckList").append(PromotionState);
                break;
            case "Campaign_pause":
                $("#TabTitle").html("启动/暂停");
                $("#CheckList").append(pause);
                break;
            case "Campaign_budget":
                $("#TabTitle").html("推广计划每日预算");
                $("#CheckList").append(price);
                break;
            case "Campaign_show":
                $("#TabTitle").html("推广计划创意展现方式");
                var PromotionShow =
                    "<ul><li><input type='radio' name='showPro' value='-1'>全部</li>" +
                    "<li class='list_bottom'><input type='radio' name='showPro' value='1'>优选</li>" +
                    "<li><input type='radio' name='showPro' value='2'>轮显</li>";
                $("#CheckList").append(PromotionShow);
                break;
            case "Campaign_dynamic":
                $("#TabTitle").html("动态创意状态");
                var PromotionDynamic =
                    "<ul><li><input type='radio' name='dynamic' value='-1'>全部</li>" +
                    "<ul><li><input type='radio' name='dynamic' value='1'>开启</li>" +
                    "<li><input type='radio' name='dynamic' value='0'>关闭</li>";
                $("#CheckList").append(PromotionDynamic);
                break;

        }
    },
    modelClose: function () {
        $("#filterSearchTemplate").hide();
        $(".remove").remove();
    },
    filterSearchOk: function () {
        var filterField = $("input[name='filterField']").val();
        switch (filterField) {
            case "Keyword_state":
                this.inputSubmit("checkbox", filterField, null, function (result) {
                    $.leveComplete("keyword", {data: result.data.list});
                });
                break;
            case "Keyword_pause":
                this.inputSubmit(null, filterField, null, function (result) {
                    $.leveComplete("keyword", {data: result.data.list});
                });
                break;
            case "Keyword_price":
                var min_pointer = $("input[name='min_points']").val();
                var max_pointer = $("input[name='max_points']").val();
                if (min_pointer && max_pointer && (max_pointer > min_pointer)) {
                    var formData = {};
                    var filterType = filterField.split("_")[0];
                    var filterFields = filterField.split("_")[1];
                    if (filterType && filterFields) {
                        formData["filterType"] = filterType;
                        formData["filterField"] = filterFields;
                    }
                    formData["filterValue"] = min_pointer + "," + max_pointer;
                    this.filterSearchSubmit(formData, null, function (result) {
                        $.leveComplete("keyword", {data: result.data.list});
                    });
                    errorMsg.html('');
                } else {
                    errorMsg.html("请输入正确的价格范围!");
                }
                break;
            case "Keyword_cquality":
                var quality = this.getCheckData();
                console.log(quality);
                break;
            case "Keyword_matchType":
                var checkData = [];
                var checkSelected = $("input[name='matchType']");
                checkSelected.each(function (i, o) {
                    if ($(o).prop("checked")) {
                        checkData.push($(o).val());
                    }
                });
                if (checkData.length) {
                    var formData = {};
                    var filterType = filterField.split("_")[0];
                    var filterFields = filterField.split("_")[1];
                    if (filterType && filterFields) {
                        formData["filterType"] = filterType;
                        formData["filterField"] = filterFields;
                    }
                    formData["filterValue"] = checkData.toString();
                    this.filterSearchSubmit(formData, null, function (result) {
                        $.leveComplete("keyword", {data: result.data.list});
                    });
                }
                break;
            case "Creative_pause":
                this.inputSubmit(null, filterField, "../assistantCreative/filterSearch", function (res) {
                    var result = $.parseJSON(res);
                    $.leveComplete("creative", {data: result.list});
                });
                break;
            case "Creative_state":
                this.inputSubmit("checkbox", filterField, "../assistantCreative/filterSearch", function (res) {
                    var result = $.parseJSON(res);
                    $.leveComplete("creative", {data: result.list});
                });
                break;
            case "Creative_quipment":
                this.inputSubmit(null, filterField, "../assistantCreative/filterSearch", function (res) {
                    var result = $.parseJSON(res);
                    $.leveComplete("creative", {data: result.list});
                });
                break;
            case "Adgroup_state":
                this.inputSubmit("checkbox", filterField, "../assistantAdgroup/filterSearch", function (res) {
                    var result = $.parseJSON(res);
                    $.leveComplete("adgroup", {data: result.list});
                });
                break;
            case "Adgroup_pause":
                this.inputSubmit(null, filterField, "../assistantAdgroup/filterSearch", function (res) {
                    var result = $.parseJSON(res);
                    $.leveComplete("adgroup", {data: result.list});
                });
                break;
            case "Adgroup_price":
                this.moneyAbuoutSubmit(filterField, "../assistantAdgroup/filterSearch", function (res) {
                    var result = $.parseJSON(res);
                    $.leveComplete("adgroup", {data: result.list});
                });
                break;
            case "Campaign_state":
                this.inputSubmit("checkbox", filterField, "../assistantCampaign/filterSearch", function (res) {
                    var result = $.parseJSON(res);
                    $.leveComplete("campaign", {data: result.list});
                });
                break;
            case "Campaign_pause":
                this.inputSubmit(null, filterField, "../assistantCampaign/filterSearch", function (res) {
                    var result = $.parseJSON(res);
                    $.leveComplete("campaign", {data: result.list});
                });
                break;
            case "Campaign_budget":
                this.moneyAbuoutSubmit(filterField, "../assistantCampaign/filterSearch", function (res) {
                    var result = $.parseJSON(res);
                    $.leveComplete("campaign", {data: result.list});
                });
                break;
            case "Campaign_show":
                this.inputSubmit(null, filterField, "../assistantCampaign/filterSearch", function (res) {
                    var result = $.parseJSON(res);
                    $.leveComplete("campaign", {data: result.list});
                });
                break;
            case "Campaign_dynamic":
                this.inputSubmit(null, filterField, "../assistantCampaign/filterSearch", function (res) {
                    var result = $.parseJSON(res);
                    $.leveComplete("campaign", {data: result.list});
                });
                break;
                break;
            default:
                var formData = this.getNormalFilterForm();
                if (formData) {
                    var filterType = filterField.split("_")[0];
                    var filterFields = filterField.split("_")[1];
                    if (filterType && filterFields) {
                        formData["filterType"] = filterType;
                        formData["filterField"] = filterFields;
                    }
                    formData["filterValue"] = formData.filterValue;
                    switch (formData["filterType"]) {
                        case "Creative":
                            this.filterSearchSubmit(formData, "../assistantCreative/filterSearch", function (result) {
                                var gson = $.parseJSON(result);
                                $.leveComplete("creative", {data: gson.list});
                            });
                            break;
                        case "Adgroup":
                            this.filterSearchSubmit(formData, "../assistantAdgroup/filterSearch", function (result) {
                                var gson = $.parseJSON(result);
                                $.leveComplete("adgroup", {data: gson.list});
                            });
                            break;
                        case "Campaign":
                            this.filterSearchSubmit(formData, "../assistantCampaign/filterSearch", function (res) {
                                var result = $.parseJSON(res);
                                $.leveComplete("campaign", {data: result.list});
                            });
                            break;
                        default:
                            this.filterSearchSubmit(formData, null, function (result) {
                                $.leveComplete("keyword", {data: result.data.list});
                            });
                            break;
                    }
                }
                break;
        }
    },
    filterSearchSubmit: function (form, url, func) {
        form["cid"] = jsonData.cid;
        form["aid"] = jsonData.aid ? jsonData.aid : '';
        url = url ? url : "../assistantKeyword/filterSearch";
        $.ajax({
            url: url,
            data: JSON.stringify(form),
            type: "POST",
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                if (func) {
                    func(result);
                }
            }
        });
    },
    getNormalFilterForm: function () {
        var selected = $("#CheckList").find("select :selected").val();
        var filterValue = $("#CheckList").find("textarea").val();
        if (selected && filterValue) {
            errorMsg.html('');
            return {selected: Number(selected), filterValue: filterValue};
        } else {
            errorMsg.html("请输入要筛选的条件内容！");
            return;
        }
    },
    getCheckData: function () {
        var checkedData = [];
        var baiduStatusChecked = $("#CheckList").find("input[type='checkbox']");
        baiduStatusChecked.each(function (i, o) {
            if ($(o).prop("checked")) {
                checkedData.push($(o).val());
            }
        });
        if (checkedData.length) {
            errorMsg.html('');
            return checkedData.toString();
        } else {
            errorMsg.html("必须选择一项状态进行筛选！")
            return;
        }
    },
    getRadioData: function () {
        var checkedData = [];
        var baiduStatusChecked = $("#CheckList").find("input[type='radio']");
        baiduStatusChecked.each(function (i, o) {
            if ($(o).prop("checked")) {
                checkedData.push($(o).val());
            }
        });
        if (checkedData.length) {
            errorMsg.html('');
            return checkedData.toString();
        } else {
            errorMsg.html("必须选择一项状态进行筛选！")
            return;
        }
    },
    inputSubmit: function (inputType, filterField, url, func) {
        var state = this.getRadioData();
        if (inputType == "checkbox") {
            state = this.getCheckData();
        }
        if (state) {
            var formData = {};
            var filterType = filterField.split("_")[0];
            var filterFields = filterField.split("_")[1];
            if (filterType && filterFields) {
                formData["filterType"] = filterType;
                formData["filterField"] = filterFields;
            }
            formData["filterValue"] = state;
            this.filterSearchSubmit(formData, url, function (res) {
                if (func) {
                    func(res);
                }
            });
        }
    },
    moneyAbuoutSubmit: function (filterField, url, func) {
        var min_pointer = $("input[name='min_points']").val();
        var max_pointer = $("input[name='max_points']").val();
        if (min_pointer && max_pointer && (max_pointer >= min_pointer)) {
            var formData = {};
            var filterType = filterField.split("_")[0];
            var filterFields = filterField.split("_")[1];
            if (filterType && filterFields) {
                formData["filterType"] = filterType;
                formData["filterField"] = filterFields;
            }
            formData["filterValue"] = min_pointer + "," + max_pointer;
            this.filterSearchSubmit(formData, url, function (res) {
                if (func) {
                    func(res)
                }
            });
            errorMsg.html('');
        } else {
            errorMsg.html("请输入正确的价格范围!");
        }
    }
}
function boxCloser(e){
    if(e.target.id != 'filterSearchTemplate'){
        document.body.removeEventListener('click', boxCloser, false);
        $('#filterSearchTemplate').hide();
        $(".remove").remove();
    }
}

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
