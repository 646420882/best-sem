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
            alert("已"+edtTypeStr+"到粘贴板！");
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
        var replaceText = $("input[name='replaceText']").val();
        switch (result.type) {
            case "creative":
                var _createTable = $("#createTable tbody");
                if (result.data) {
                    if (result.data.length) {
                        var json = result.data;
                        _createTable.empty();
                        var _trClass = "";
                        for (var i = 0; i < json.length; i++) {
                            var _id = json[i].creativeId != null ? json[i].creativeId : json[i].id;
                            var _edit = json[i].localStatus != null ? json[i].localStatus : -1;
                            var ls = replaceText ? getLocalStatus(2) : getLocalStatus(parseInt(_edit));
                            _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
                            var _tbody = "<tr class=" + _trClass + " onclick='on(this);''>" +
                                "<td >&nbsp;<input type='hidden' value='" + _id + "'/></td>" +
                                "<td >&nbsp;<input type='checkbox' name='creativeCheck' value='" + _id + "'/></td>" +
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
                    createTable.empty();
                    _createTable.append("<tr><td>没有找到类似的数据</td></tr>");
                }
                break;
            case "adgroup":
                var _adGroudTable = $("#adGroupTable tbody");
                if (result.data) {
                    if (result.data.length > 0) {
                        _adGroudTable.empty();
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
                                "<td >&nbsp;<input type='hidden' value='" + _id + "'/></td>" +
                                "<td ><input type='checkbox' name='adgroupCheck' value='" + _id + "'/></td>" +
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
    }, foBatch: function (_this) {
        var form = $(_this).parents("form");
        var checkType = $("select[name='checkType'] :selected");
        var foR_params = {};
        var forType = $("#forType").val();
        if (checkType.val() == 0) {
            var checked_data = [];
            var checkChildren = $("input[name='keywordCheck']");
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
            foR_params = {type: forType, forType: 1, campaignId: jsonData.cid, adgroupId: jsonData.aid};
        }
        form.foRSubmit("../assistantCommons/batchDel", foR_params, function (result) {
            if (result.data) {
                $.foRComplete({type: foR_params.type, forType: foR_params.forType, data: result.data});
                commons.foRClose();
            }
        });
    }
});
//表格顶部选择弹窗
var tabselect =
    "<select>" +
    "<option>包含</option>" +
    "<option>不包含</option>" +
    "<option>等于</option>" +
    "<option>不等于</option>" +
    "<option>开始于</option>" +
    "<option>截止于</option></select>" +
    "<textarea> </textarea>" +
    "<p>最多同时搜1000个文本</p>";
var Computerquality =
    "<ul class='quality'>" +
    "<li>" + "<input type='checkbox'>" +
    "<span>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "</span>" + "<b>1</b>" +
    "</li>" +
    "<li>" + "<input type='checkbox'>" +
    "<span>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "</span>" + "<b>2</b>" +
    "</li>" +
    "<li>" + "<input type='checkbox'>" +
    "<span>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "</span>" + "<b>3</b>" +
    "</li>" +

    "<li>" + "<input type='checkbox'>" +
    "<span>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "</span>"
    + "<b>4</b>" +
    "</li>" +
    "<li>" + "<input type='checkbox'>" +
    "<span>" +
    "<img src='/public/img/star.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "<img src='/public/img/star3.png'>" +
    "</span>" + "<b>5</b>" +
    "</li>"
    + "</ul>"
var pause =
    "<ul><li><input type='checkbox'>全部</li>" +
    "<li><input type='checkbox'>启用</li>" +
    "<li><input type='checkbox'>暂停</li>";
var price =
    "<input type='number' name='points' min='00' max='10' />"
    + "到"
    + "<input type='number' name='points' min='00' max='10' />";
var TabModel = {
    Show: function (type, _this) {
        if ($(".dropdown-menus").css("display") == "none") {
            var tabtop = $(_this).offset().top + $(_this).outerHeight() + "px";
            var tableft = $(_this).offset().left + $(_this).outerWidth() + -$(_this).width() + "px";
            $(".dropdown-menus").css("top", tabtop);
            $(".dropdown-menus").css("left", tableft);
            $(".dropdown-menus").show();
            $(".dropdown-menus ").mouseleave(function () {
                $(".dropdown-menus").hide();
            });
        }
        else {
            $(".dropdown-menus").hide();
        }
        $("#CheckList").empty();
        switch (type) {
            case "Keywordname":
                $("#TabTitle").html("关键词名称");
                $("#CheckList").append(tabselect);
                break;
            case "Keywordstate":
                $("#TabTitle").html("关键词状态");
                var states =
                    "<ul><li><input type='checkbox'>有效</li>" +
                    "<li><input type='checkbox'>暂停推广</li>" +
                    "<li><input type='checkbox'>不宜推广</li>" +
                    "<li><input type='checkbox'>部分无效</li>" +
                    "<li><input type='checkbox'>审核中</li>" +
                    "<li><input type='checkbox'>搜索量过低</li>" +
                    "<li><input type='checkbox'>计算机搜索无效</li>" +
                    "<li><input type='checkbox'>移动搜索无效</li>" +
                    "<li><input type='checkbox'>搜索无效</li>" +
                    "<li><input type='checkbox'>待激活</li>" +
                    "<li><input type='checkbox'>有效-移动URL审核中</li>" +
                    "<li><input type='checkbox'>本地新增</li></ul>";
                $("#CheckList").append(states);
                break;
            case "Enablepause":
                $("#TabTitle").html("启用/暂停");
                $("#CheckList").append(pause);
                break;
            case "keywordprice":
                $("#TabTitle").html("关键词出价");
                $("#CheckList").append(price);
                break;
            case "keywordcomputerquality":
                $("#TabTitle").html("关键词计算机质量度");
                $("#CheckList").append(Computerquality);
                break;
            case "keywordmovingmass":
                $("#TabTitle").html("移动质量度");
                $("#CheckList").append(Computerquality);
                break;
            case "keywordmatching":
                $("#TabTitle").html("关键词匹配模式");
                var matching =
                    "<ul><li><input type='checkbox'>广泛</li>" +
                    "<li><input type='checkbox' id='Phrase'>短语" +
                    "<ul id='PhraseList'><li><input type='checkbox'>核心包含</li></li>" +
                    "<li><input type='checkbox'>同义包含</li>" +
                    "<li><input type='checkbox'>精确包含</li></ul>" +
                    "<li><input type='checkbox'>精确</li>" +
                    "<li><input type='checkbox'>基本匹配模式</li>" +
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
            case "visiturl":
                $("#TabTitle").html("关键词访问URL");
                $("#CheckList").append(tabselect);
                break;
            case "mobilevisiturl":
                $("#TabTitle").html("关键词移动访问URL");
                $("#CheckList").append(tabselect);
                break;
            case "keywordplanname":
                $("#TabTitle").html("推广计划名称");
                $("#CheckList").append(tabselect);
                break;
            case "creativetitle":
                $("#TabTitle").html("创意标题");
                $("#CheckList").append(tabselect);
                break;
            case "creativetitle1":
                $("#TabTitle").html("创意标题1");
                $("#CheckList").append(tabselect);
                break;
            case "creativetitle2":
                $("#TabTitle").html("创意标题2");
                $("#CheckList").append(tabselect);
                break;
            case "creativedefault":
                $("#TabTitle").html("创意默认访问URL");
                $("#CheckList").append(tabselect);
                break;
            case "creativemobileurl":
                $("#TabTitle").html("创意移动访问URL");
                $("#CheckList").append(tabselect);
                break;
            case "creativeshowurl":
                $("#TabTitle").html("创意移动显示URL");
                $("#CheckList").append(tabselect);
                break;
            case "creativepause":
                $("#TabTitle").html("启用/暂停");
                $("#CheckList").append(pause);
                break;
            case "creativestate":
                $("#TabTitle").html("创意状态");
                var CreativeState =
                    "<ul><li><input type='checkbox'>有效</li>" +
                    "<li><input type='checkbox'>不宜推广</li>" +
                    "<ul><li><input type='checkbox'>暂停推广</li>" +
                    "<li><input type='checkbox'>审核中</li>" +
                    "<li><input type='checkbox'>待激活</li></ul>" +
                    "<li><input type='checkbox'>部分无效</li>" +
                    "<li><input type='checkbox'>有效-移动URL审核中</li>" +
                    "<li><input type='checkbox'>本地新增</li>" +
                    "</ul>";
                $("#CheckList").append(CreativeState);
                break;
            case "creativeequipment":
                $("#TabTitle").html("创意设备偏好");
                var CreativeEquipment =
                    "<ul><li><input type='radio'>全部</li>" +
                    "<li><input type='radio'>全部设备</li>" +
                    "<li><input type='radio'>移动设备优先</li>";
                $("#CheckList").append(CreativeEquipment);
                break;
            case "extensionname":
                $("#TabTitle").html("推广单元名称");
                $("#CheckList").append(tabselect);
                break;
            case "extensionstate":
                $("#TabTitle").html("推广单元状态");
                $("#CheckList").append(tabselect);
                break;
            case "extensionpause":
                $("#TabTitle").html("启动/暂停");
                $("#CheckList").append(pause);
                break;
            case "extensionprice":
                $("#TabTitle").html("出价");
                $("#CheckList").append(price);
                break;
            case "extensionplanname":
                $("#TabTitle").html("推广计划名称");
                $("#CheckList").append(tabselect);
                break;
            case "promotionplan":
                $("#TabTitle").html("推广计划名称");
                $("#CheckList").append(tabselect);
                break;
            case "promotionstate":
                $("#TabTitle").html("推广计划状态");
                var PromotionState =
                    "<ul><li><input type='checkbox'>有效</li>" +
                    "<li><input type='checkbox'>暂停推广</li>" +
                    "<li><input type='checkbox'>处在暂停阶段</li>" +
                    "<li><input type='checkbox'>推广计划预算不足</li>" +
                    "<li><input type='checkbox'>账户预算部足</li>" +
                    "<li><input type='checkbox'>本地新增</li>";
                $("#CheckList").append(PromotionState);
                break;
            case "promotionpause":
                $("#TabTitle").html("启动/暂停");
                $("#CheckList").append(pause);
                break;
            case "promotionbudget":
                $("#TabTitle").html("推广计划每日预算");
                $("#CheckList").append(price);
                break;
            case "promotionshow":
                $("#TabTitle").html("推广计划创意展现方式");
                var PromotionShow =
                    "<ul><li><input type='radio'>全部</li>" +
                    "<li><input type='radio'>优选</li>" +
                    "<li><input type='radio'>轮替</li>";
                $("#CheckList").append(PromotionShow);
                break;
            case "promotiondynamic":
                $("#TabTitle").html("动态创意状态");
                var PromotionDynamic =
                    "<ul><li><input type='checkbox'>全部开启</li>" +
                    "<li><input type='checkbox'>全部关闭</li>" +
                    "<li><input type='checkbox'>部分开启</li>";
                $("#CheckList").append(PromotionDynamic);
                break;

        }
    },
    modelClose: function () {
        $(".tabmodel").hide();
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
