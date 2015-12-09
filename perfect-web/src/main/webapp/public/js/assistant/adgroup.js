/**
 * Created by XiaoWei on 2014/8/21.
 */
var plans = {cid: null, cn: null, nowPage: 1, pageSize: 20};
var priceRatio = "";//获取计划下拉时该计划的移动出价比例，用于添加单元时fush到添加框中
$(function () {
    initAMenu();
    rDrag.init(document.getElementById("bAdd"));
    rDrag.init(document.getElementById("aKwd"));
    rDrag.init(document.getElementById("adUp"));
    rDrag.init(document.getElementById("apKwd"));
    initNoKwdKeyUp();
});
var atmp = null;
var adHtml = null;
var sp = null;
var nn = null;
var ne = null;
var aAdd = {
        text: "添加单元",
        img: "../public/img/zs_function1.png",
        func: function () {
            addAdgroup();
        }
    }, aDel = {
        text: "删除单元",
        img: "../public/img/zs_function2.png",
        func: function () {
            adgroupDel();
        }
    }, aUpdate = {
        text: "修改单元",
        img: "../public/img/zs_function4.png",
        func: function () {
            adgroupUpdate();
        }
    }, aAddMutli = {
        text: "批量添加单元",
        img: "../public/img/zs_function3.png",
        func: function () {
            adgroupMutli();
        }
    }, aReBack = {
        text: "还原",
        img: "../public/img/zs_function9.png",
        func: function () {
            agreBakClick();
        }
    }, aUpload = {
        text: "更新到凤巢",
        img: "../public/img/update2.png",
        func: function () {
            adgroupUpload();
        }
    }
    , menu_keyword_copy = {
        text: "复制",
        img: "../public/img/zs_function13.png",
        func: function () {
            editCommons.Copy();
        }
    }
    , menu_keyword_shear = {
        text: "剪切",
        img: "../public/img/zs_function14.png",
        func: function () {
            editCommons.Cut();
        }

    }
    , menu_keyword_paste = {
        text: "粘贴",
        img: "../public/img/zs_function15.png",
        func: function () {
            editCommons.Parse();
        }
    }
    , menu_keyword_select = {
        text: "全选",
        img: "../public/img/zs_function16.png",
        func: function () {
            CtrlAll();
        }

    }
var aMeunData = [
    [aAdd, aDel, aUpdate, aAddMutli, aReBack, aUpload, menu_keyword_copy, menu_keyword_shear, menu_keyword_paste, menu_keyword_select]
];
/**
 * 初始化单元右键菜单
 */
var aMenuExt = {
    name: "aMenu",
    beforeShow: function () {
        var _this = $(this);
        atmp = _this;
        $.smartMenu.remove();
    }
}
/**
 * 初始化右键
 */
function initAMenu() {
    $("#adGroupTable").on("mousedown", "tr", function () {
        $(this).smartMenu(aMeunData, aMenuExt);
    });
    $("#adGroupTable").on("keydown", "tr", function (event) {
        if (event.keyCode == 13) {
            var _this = $(this);
            adgroupAddOperate(_this);
        }
    });
}
function adgroupAddOperate(obj) {
    var campBgt = $("#campBgt").html();
    var _this = $(obj);
    var _an = _this.find("input:eq(2)");
    var _anStr = getChar(_an.val());
    if (parseInt(_anStr) == 0 || parseInt(_anStr) >= 30) {
        //alert("\"推广单元名称\"长度应大于1个字符小于30个字符，汉子占两个字符,且不为空！");
        AlertPrompt.show("\"推广单元名称\"长度应大于1个字符小于30个字符，汉子占两个字符,且不为空！");
        return false;
    }
    // /^-?\d+\.?\d*$/
    var _mp = _this.find("input:eq(4)").val();
    if (!/^-?\d+\.?\d*$/.test(_mp)) {
        //alert("请输入正确的\"单元出价\"");
        AlertPrompt.show("请输入正确的\"单元出价\"");
        return;
    } else {
        if (_mp == "0") {
            //alert("单元出价不能为0");
            AlertPrompt.show("单元出价不能为0");
            return;
        } else {
            if (campBgt != "0") {
                if (parseInt(_mp) > parseInt(campBgt)) {
                    //alert("单元出价不能大于" + campBgt + "元的计划出价");
                    AlertPrompt.show("单元出价不能大于" + campBgt + "元的计划出价");
                    return;
                }
            }
        }
    }
    var con = confirm("你确定要添加?");
    if (con) {
        _this.formSubmit("../assistantAdgroup/adAdd", function (rs) {
            var json = eval("(" + rs + ")");
            if (json.success == "1") {
                loadTree();
                var _span = $("#" + sp + "").html();
                if (_span == null) {
                    _span = "<span>未设置</span>";
                }
                _this.remove();
                var i = $("#adGroupTable tbody tr").size();
                var _createTable = $("#adGroupTable tbody");
                var _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
                var _tbody = "<tr class=" + _trClass + " onclick='aon(this)'>" +
                    "<td class='table_add'><input type='checkbox' name='adgroupCheck' value='" + json.data + "' onchange='adgroupListCheck()'/><input type='hidden' name='cid' value='" + formData["cid"] + "'/><span class='new_add' step='1'></span></td>" +
                    "<td>" + formData["adgroupName"] + "</td>" +
                    "<td>本地新增</td>" +
                    " <td>" + getAdgroupPauseByBoolean(formData["pause"]) + "</td>" +
                    "<td class='InputTd'>" + "<span>" + parseFloat(formData["maxPrice"]).toFixed(2) + "</span>" + "<span  id='InputImg' onclick='InputPrice(this)'><img  src='../public/img/zs_table_input.png'></span>" + "</td>" +
                    "<td><span>" + _span + "</span><input type='hidden' value='" + formData["negativeWords"] + "'><input type='hidden' value='" + formData["exactNegativeWords"] + "'></td>" +
                    "<td>" + plans.cn + "</td>" +
                    "</tr>";
                _createTable.append(_tbody);
            } else if (rs == "3") {
                AlertPrompt.show("异常");
                //alert("异常");
            }
        });
    }
}
/**
 * 根据传入的字符串布尔类型返回暂停或则是启用
 * @param bol
 * @returns {string}
 */
function getAdgroupPauseByBoolean(bol) {
    return bol == "true" ? "启用" : "暂停";
}
/**
 * 初始化否定选择框输入关键词数量
 */
function initNoKwdKeyUp() {
    $("#nKwd").keydown(function (e) {
        var arr = $(this).val().trim().split("\n");
        $("#nKwd_size").html(arr.length);
    });
    $("#mKwd").keydown(function (e) {
        var arr = $(this).val().trim().split("\n");
        $("#mKwd_size").html(arr.length);
    });

    $("#npKwd").keydown(function (e) {
        var arr = $(this).val().trim().split("\n");
        $("#npKwd_size").html(arr.length);
    });
    $("#mpKwd").keydown(function (e) {
        var arr = $(this).val().trim().split("\n");
        $("#mpKwd_size").html(arr.length);
    });
}
/**
 * 加载单元数据
 * @param plans 根据点击树结构的计划id，如果有则根据计划加载，如果没有，查询所有单元
 */
function loadAdgroupData(page_index) {
    plans.pageSize = items_per_page;
    plans.nowPage = page_index;
    pageType = 4;
    initAgReback();
    var _adGroudTable = $("#adGroupTable tbody");
    _adGroudTable.empty().html("加载中....");
    $.post("../assistantAdgroup/getAdgroupList", plans, function (rs) {
        var gson = $.parseJSON(rs);
        if (gson.list != undefined) {
            if (gson.list.length > 0) {
                adgPagerInit(gson);
                _adGroudTable.empty();
                var _trClass = "";
                var json = gson.list;
                for (var i = 0; i < json.length; i++) {
                    _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
                    var _id = json[i].adgroupId != null ? json[i].adgroupId : json[i].id;
                    var _maxPrice = json[i].maxPrice != null ? json[i].maxPrice : 0.0;
                    var nn = json[i].negativeWords != null ? json[i].negativeWords : "";
                    var ne = json[i].exactNegativeWords != null ? json[i].exactNegativeWords : "";
                    var _edit = json[i].localStatus != null ? json[i].localStatus : -2;
                    var _ls = getLocalStatus(parseInt(_edit));
                    var _tbody = "<tr class=" + _trClass + " onclick=aon(this)>" +
                        "<td class='table_add'><input type='checkbox' name='adgroupCheck' value='" + _id + "' onchange='adgroupListCheck()'/><input type='hidden' name='cid' value='" + _id+ "'/>" + _ls + "</td>" +
                        "<td >" + json[i].adgroupName + "</td>" +
                        "<td >" + until.getAdgroupStatus(json[i].status) + "</td>" +
                        "<td >" + until.convert(json[i].pause, "启用:暂停") + "</td>" +
                        " <td class='InputTd'>" + "<span>" + parseFloat(_maxPrice).toFixed(2) + "</span>" + "<span  id='InputImg' onclick='InputPrice(this)'><img  src='../public/img/zs_table_input.png'></span>" + "</td>" +
                        "<td ><input type='hidden' value='" + nn + "'><input type='hidden' value='" + ne + "'>" + getNoAdgroupLabel(nn, ne) + "</td>" +
                        "<td >" + json[i].campaignName + "</td>" +
                        "</tr>";
                    _adGroudTable.append(_tbody);
                }
            }
        } else {
            _adGroudTable.empty();
            _adGroudTable.append("<tr><td>暂无数据</td></tr>");
        }
    });
}
/**
 * 初始化分页控件
 * @param data
 */
function adgPagerInit(data) {
    if (data.totalCount == 0) {
        return false;
    }
    $("#adgroupPager").pagination(data.totalCount, getOptionsFromForm(data.nextPage));
}
//输入数字传入分页
function skipAdgroupPage() {
    var pageNo = $("#adgroupPageNum").val();
    loadAdgroupData(/^\d+$/.test(pageNo) == false ? 0 : parseInt(pageNo) - 1);
}


/**
 * 添加成功后显示否定词比例
 * @param exp1
 * @param exp2
 * @returns {string}
 */
function getNoAdgroupLabel(exp1, exp2) {
    if (exp1 != null && exp2 != null) {
        if (exp1.length > 0 && exp2.length > 0) {
            return "<span>" + exp1.length + ":" + exp2.length + "</span>";
        } else {
            return "<span>未设置</span>";
        }
    } else {
        return "<span>未设置</span>";
    }
}
/**
 * 加载获取移动出价比例，如果系统不存在返回0.0
 * @param double
 * @returns {*}
 */
function getMib(double) {
    return double != null ? double : "0.0";
}
/**
 * 鼠标点击单条数据
 * @param ts
 */
function aon(ts) {
    var _this = $(ts);
    var tadd = _this.find("td:eq(0) a").html();
    if (tadd == undefined && adHtml == "删除") {
        adgroupAddOperate(atmp);
    }
    adHtml = _this.find("td:eq(1) a").html();
    atmp = _this;
    var _edit = _this.find("td:eq(8)").html()
    if (_edit != "") {
        onAgReback();
    } else {
        initAgReback();
    }
    var data = {};
    data[0] = _this.find("td:eq(1)").html();
    data[1] = _this.find("td:eq(4) span:eq(0)").html()?_this.find("td:eq(4) span:eq(0)").html():_this.find("td:eq(4) input").val();
    data[2] = _this.find("td:eq(5)").html();
    var status = _this.find("td:eq(2)").html();
    var pause = _this.find("td:eq(3)").html();
    $("#aDiv input").each(function (i, o) {
        if (data[i].indexOf("input") > -1) {
            $(o).val("");
            return;
        } else {
            $(o).val(data[i]);
        }
    });
    $("#apStatus").html(status);
    if (pause == "启用") {
        $("#apPause").get(0).selectedIndex = 0;
    } else {
        $("#apPause").get(0).selectedIndex = 1;
    }
}
/**
 * 根据点击的树的计划获取单元
 * @param rs
 */
function getAdgroupPlan(rs, name) {
    plans.cid = rs;
    plans.cn = name;
    loadAdgroupData(0);
}
/**
 * 点击添加按钮弹出框
 */
function adAlertShow() {
    var _adAdd = $("#adAdd");
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    _adAdd.css({
        left: ($("body").width() - _adAdd.width()) / 2 - 20 + "px",
        top: ($(window).height() - _adAdd.height()) / 2 + $(window).scrollTop() + "px",
        display: "block"
    });

}
/**
 * 选择计划弹出框关闭方法
 */
function adgroupAddAlertClose() {
    $(".TB_overlayBG").css("display", "none");
    $("#adAdd ").css("display", "none");
    $("#aNoKwd").css("display", "none");
    $("#adUpdate").css("display", "none");
}
function initCampBgt() {
    $.get("/assistantAdgroup/getCampBgt", {cid: plans.cid}, function (res) {
        $("#campBgt").html(res.data);
    });
}
/**
 * 添加单元方法
 */
function addAdgroup() {
    var jcBox = $("#adUi");
    if (plans.cid == null) {
        jcBox.empty();
        adgetPlans();
        jcBox.append("<li>推广计划<select id='aPlan' onchange='getAdgroupPlan(this.value)'><option value='-1'>请选择计划</option></select></li>");
        adAlertShow();
    } else {
        initCampBgt();
        var i = $("#adGroupTable tbody tr").size();
        var lastTr = $("#adGroupTable tr:eq(" + i + ")").find("td:eq(1) a").html();
        if (lastTr == "删除") {
            //alert("请提交后再继续添加");
            AlertPrompt.show("请提交后再继续添加");
            return;
        }
        var _createTable = $("#adGroupTable tbody");
        var _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
        var _tbody = "<tr class=" + _trClass + " onclick='aon(this)'>" +
            "<td>&nbsp;<span><a href='javascript:void(0)' onclick='removeThe(this);'>删除</a></span><input type='hidden'  name='oid' value='" + getRandomId() + "'/><input type='hidden' name='cid' value='" + getAdgroupId() + "'/></td>" +
            "<td><input name='adgroupName' style='width:140px;' maxlength='30'></td>" +
            "<td><input type='hidden' name='status' value='-1'><span>本地新增</span></td>" +
            " <td><select name='pause'><option value='true'>启用</option><option value='false'>暂停</option></select></td>" +
            "<td><input name='maxPrice' style='width:50px;'  onkeypress='until.regDouble(this)' maxlength='4'></td>" +
            "<td><span id='" + getRandomId() + "sp'>未设置</span><input name='negativeWords' id='" + getRandomId() + "ni' type='hidden' readonly='readonly'><input type='button' onclick='adgroupNokeyword(this)' value='设置否定关键词'/><input name='exactNegativeWords' id='" + getRandomId() + "ne' type='hidden'  readonly='readonly'></td>" +
            "<td>" + plans.cn + "</td>" +
            "</tr>";
        _createTable.append(_tbody);
    }
}
/**
 * 删除方法，如果是新增还未添加的则无效
 */
function adgroupDel() {
    var _this = $(atmp);
    var oid = _this.find("td:eq(0) input[type='checkbox']").val();
    if (oid != undefined) {
        var con = confirm("是否删除该单元？");
        if (con) {
            $.get("../assistantAdgroup/del", {oid: oid}, function (rs) {
                if (rs == "1") {
                    loadTree();
                    $(atmp).find("td:eq(0)").find("span").attr("step", 3).attr("class", "table_delete");
                }
            });
        }
    } else {
        //alert("请选择单元！");
        AlertPrompt.show("请选择单元！");
    }
}
/**
 * 修改方法，根据右键选中的一条记录进行修改
 */
function adgroupUpdate() {
    var _tr = $(atmp);
    var oid = _tr.find("td:eq(0) input[type='checkbox']").val();
    if (oid) {
        var _adAdd = $("#adUpdate");
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height(),
            opacity: 0.2
        });
        _adAdd.css({
            left: ($("body").width() - _adAdd.width()) / 2 - 20 + "px",
            top: ($(window).height() - _adAdd.height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
        $("#adgroupUpdateForm input").empty();
        var oid = _tr.find("td:eq(0) input[type='checkbox']").val();
        var name = _tr.find("td:eq(1)").html();
        var status = _tr.find("td:eq(2) input").val();
        var pause = _tr.find("td:eq(3)").html();
        var maxPrice = _tr.find("td:eq(4) span:eq(0)").html()?_tr.find("td:eq(4) span:eq(0)").html():_tr.find("td:eq(4)").html();
        var sp = _tr.find("td:eq(5) span").html();
        var nn = _tr.find("td:eq(5) input").val();
        var ne = _tr.find("td:eq(5) input:eq(1)").val();
        var cn = _tr.find("td:eq(6)").html();
        $("#adgroupUpdateForm input[name='oid']").val(oid);
        $("#adgroupUpdateForm input[name='adgroupName']").val(name);
        $("#adStatus").html(adgroupConvertStatus(parseInt(status)));
        $("#auSpan").html(sp);
        if (pause == "启用") {
            $("#adgroupUpdateForm select[name='pause']").get(0).selectedIndex = 0;
        } else {
            $("#adgroupUpdateForm select[name='pause']").get(0).selectedIndex = 1;
        }
        $("#adgroupUpdateForm input[name='maxPrice']").val(maxPrice);
        $("#adgroupUpdateForm input[name='negativeWords']").val(nn);
        $("#adgroupUpdateForm input[name='exactNegativeWords']").val(ne);
        $("#adgroupUpdateForm input[name='cn']").val(cn);
    }
}
/**
 *  根据传入的单元状态Number号码获得单元状态
 * @param number status的数据值
 * @returns {string} 页面显示对应的字符串
 */
function adgroupConvertStatus(number) {
    switch (number) {
        case 31:
            return "有效";
            break;
        case 32:
            return "暂停推广";
            break;
        case 33:
            return "推广计划暂停推广";
            break;
        default :
            return "本地新增";
            break;
    }
}
/**
 * 获取点击树或者下拉列表记录下来的计划id
 * @returns {null}
 */
function getAdgroupId() {
    return plans.cid;
}
/**
 * 弹出设置否定关键词页面
 * @param rs
 */
function adgroupNokeyword(rs) {
    var _this = $(rs).prev("input");
    var _thisne = $(rs).next("input");
    var _sp = _this.prev("span");
    nn = _this.attr("id");
    ne = _thisne.attr("id");
    sp = _sp.attr("id");
    var arr = _this.val().trim().split(",");
    var arrne = _thisne.val().trim().split(",");
    if (_this.val() != "") {
        var str = "";
        for (var i = 0; i < arr.length; i++) {
            str = str + arr[i] + "\n";
        }
        $("#nKwd").val(str.trim());
    } else if (_thisne.val() != "") {
        var strne = "";
        for (var i = 0; i < arrne.length; i++) {
            strne = strne + arrne[i] + "\n";
        }
        $("#mKwd").val(strne.trim());
    } else {
        initNoKeyAlert();
    }
    var _adAdd = $("#aNoKwd");
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height(),
        opacity: 0.2
    });
    _adAdd.css({
        left: ($("body").width() - _adAdd.width()) / 2 - 20 + "px",
        top: ($(window).height() - _adAdd.height()) / 2 + $(window).scrollTop() + "px",
        display: "block"
    });


}
/**
 * 初始化否定关键词弹出框
 */
function initNoKeyAlert() {
    $("#" + sp + "sp").html("<span>未设置</span>");
    $("#nKwd").val("");
    $("#mKwd").val("");
    var span_size = $("#aNoKwd span").length;
    for (var i = 0; i < span_size; i++) {
        if (i % 2 != 0) {
            $("#aNoKwd span:eq(" + i + ")").text(0);
        }
    }
}
/**
 * 弹窗确定方法
 */
function adgroupNoKeywordOk() {
    var arr = $("#nKwd").val().split("\n");
    var arre = $("#mKwd").val().split("\n");
    $("#" + nn + "").val(arr.toString());
    $("#" + ne + "").val(arre.toString());
    if ($("#" + nn + "").val() == "" && $("#" + ne + "").val() == "") {
        $("#" + sp + "").html("<span>未设置</span>");
    } else {
        $("#" + sp + "").html(arr.length + ":" + arre.length);
    }
    adgroupAddAlertClose();
}
/**
 * 删除行
 * @param rs
 */
function adgroupremoveThe(rs) {
    var _this = $(rs);
    _this.parents("tr").remove();
    _this.remove();
    adgrouptoolBarInit();
}
/**
 * 工具栏初始化
 */
function adgrouptoolBarInit() {
    $("#aDiv input[type='text']").val("");
    var span_size = $("#aDiv span").length;
    for (var i = 0; i < span_size; i++) {
        if (i % 2 != 0) {
            $("#aDiv span:eq(" + i + ")").text(0);
        }
    }
}
/**
 * 获取计划的所有列表，用于加载在添加计划没有通过树点击匹配到目前的计划栏目下面
 */
function adgetPlans() {
    $.get("/assistantCreative/getPlans", function (rs) {
        var json = eval("(" + rs + ")");
        if (json.length > 0) {
            for (var i = 0; i < json.length; i++) {
                var str = "";
                if (json[i].campaignId != undefined) {
                    str = "<option value='" + json[i].campaignId + "' pr='" + json[i].priceRatio + "'>" + json[i].campaignName + "</option>";
                } else {
                    str = "<option value='" + json[i].id + "' pr='" + json[i].priceRatio + "'>" + json[i].campaignName + "</option>";
                }
                $("#aPlan").append(str);
            }
        }
    });
}
/**
 * 弹出框选择计划后确认方法
 */
function adgroudAddAlertOk() {
    var aid = $("#aPlan :selected").val() != undefined ? $("#aPlan :selected").val() : plans.cid;
    var cn = $("#aPlan :selected").text() != undefined ? $("#aPlan :selected").text() : plans.cn;
    var pr = $("#aPlan :selected").attr("pr");
    if (aid == "-1") {
        AlertPrompt.show("请选择计划");
    } else {
        plans.cid = aid;
        plans.cn = cn;
        priceRatio = pr;
        adgroupAddAlertClose();
        addAdgroup();
    }
}
/**
 * 弹出框点击的设置否定关键词再弹出框
 */
function adgroupUpdateNokwdMath() {
    var _adAdd = $("#apNoKwd");
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    _adAdd.css({
        left: ($("body").width() - _adAdd.width()) / 2 - 20 + "px",
        top: ($(window).height() - _adAdd.height()) / 2 + $(window).scrollTop() + "px",
        display: "block"
    });
    var unn = $("#adgroupUpdateForm input[name='negativeWords']").val().trim();
    var une = $("#adgroupUpdateForm input[name='exactNegativeWords']").val().trim();
    if (unn.indexOf(",") > -1 || unn.length > 0) {
        var arr = unn.split(",");
        $("#npKwd_size").html(arr.length);
        var str = "";
        for (var i = 0; i < arr.length; i++) {
            str = str + arr[i].trim() + "\n";
        }
        $("#npKwd").val(str);
    } else {
        $("#npKwd").val("");
        $("#npKwd_size").html(0);
    }
    if (une.indexOf(",") > -1 || une.length > 0) {
        var arrne = une.split(",");
        $("#mpKwd_size").html(arrne.length);
        var strne = "";
        for (var i = 0; i < arrne.length; i++) {
            strne = strne + arrne[i].trim() + "\n";
        }
        $("#mpKwd").val(strne);
    } else {
        $("#mpKwd").val("");
        $("#mpKwd_size").html(0);
    }
}
/**
 * 弹出框内再弹出框关闭方法
 */
function adgroupUpdateNokwdMathClose() {
    $("#apNoKwd").css("display", "none");
}
/**
 * 再弹出框关输入否定关键词点击确定触发方法
 */
function adgroupUpdateNokwdMathOk() {
    var arr = $("#npKwd").val().trim().split("\n");
    var arrne = $("#mpKwd").val().trim().split("\n");
    $("#auSpan").html(getNoAdgroupLabel(arr, arrne));
    var unn = $("#adgroupUpdateForm input[name='negativeWords']").val(arr);
    var une = $("#adgroupUpdateForm input[name='exactNegativeWords']").val(arrne);
    adgroupUpdateNokwdMathClose();
}
/**
 * 点击修改确定方法
 */
function adrgoupUpdateOk() {
    var an = $("#adgroupUpdateForm input[name='adgroupName']").val();
    var mp = $("#adgroupUpdateForm input[name='maxPrice']").val();
    //  /^-?\d+\.?\d*$/
    if (parseInt(getChar(an)) > 30 || parseInt(getChar(an)) == 0) {
        //alert("单元名长度不能超过30个字符，一个汉字占两个字符,且不为空！");
        AlertPrompt.show("单元名长度不能超过30个字符，一个汉字占两个字符,且不为空！");
        return;
    }
    if (mp != "") {
        if (!/^-?\d+\.?\d*$/.test(mp)) {
            //alert("单元最高出价只能是小数");
            AlertPrompt.show("单元最高出价只能是小数");
            return;
        }
    } else {
        //alert("单元最高出价不能为空！");
        AlertPrompt.show("单元最高出价不能为空！");
        return;
    }

    if (confirm("你确定要修改该单元吗?") == true) {
        var _this = $(atmp);
        $("#adgroupUpdateForm").formSubmit("../assistantAdgroup/update", function (rs) {
            if (rs == "1") {
                adgroupAddAlertClose();
                var _span = $("#auSpan").html();
                var _edit = formData["oid"].length > 18 ? "<span class='new_add' step='1'></span>" : "<span class='pen' step='2'></span>";
                var _tbody =
                    "<td class='table_add'><input type='checkbox' name='adgroupCheck' value='" + formData["oid"] + "' onchange='adgroupListCheck()'/><input type='hidden' name='cid' value='" + formData["oid"] + "'/>" + _edit + "</td>" +
                    "<td>" + formData["adgroupName"] + "</td>" +
                    "<td>" + adgroupConvertStatus(formData["status"]) + "</td>" +
                    " <td>" + getAdgroupPauseByBoolean(formData["pause"]) + "</td>" +
                    "<td class='InputTd'>" + "<span>" + parseFloat(formData["maxPrice"]).toFixed(2) + "</span>" + "<span  id='InputImg' onclick='InputPrice(this)'><img  src='../public/img/zs_table_input.png'></span>" + "</td>" +
                    "<td><span>" + _span + "</span><input type='hidden' value='" + formData["negativeWords"] + "'><input type='hidden' value='" + formData["exactNegativeWords"] + "'></td>" +
                    "<td>" + formData["cn"] + "</td>";
                $(atmp).html(_tbody);
                //alert("修改完成");
                AlertPrompt.show("修改完成");
                loadTree();
            }
        });
    }
}
/**
 * 选择某条数据进行启用和不启用时触发方法
 * @param rs
 */
function adgroupdSelectChange(rs) {
    var _this = $(rs);
    var _atmp = $(atmp);
    var _oid = _atmp.find("td:eq(1) input").val();
    if (_oid != null) {
        var pause = _this.val();
        var params = {
            pause: pause,
            oid: _oid
        }
        $.get("../assistantAdgroup/updateByChange", params, function (rs) {
            if (rs == "1") {
                _atmp.find("td:eq(4)").html(_this.find("option:selected").text());
            }
        });
    }
}
function adragg() {
    var _height = $("#aadgroup").css("height");
    if (_height == "400px") {
        $("#aadgroup").css("height", "350px");
    } else {
        $("#aadgroup").css("height", "400px");
    }
}
/**
 * 初始化还原按钮
 */
function initAgReback() {
    atmp = null;
    $("#agReback").attr("class", "z_function_hover");
}
/**
 * 选中数据渲染可还原按钮样式
 */
function onAgReback() {
    $("#agReback").attr("class", "zs_top");
}
/**
 * 数据选中后进行还原操作方法
 */
function agreBakClick() {
    var _this = $(atmp);
    if (_this.html() != undefined) {
        var _edit = _this.find("td:eq(0) span").attr("step");
        if (_edit) {
            var con = confirm("是否还原选中的数据？");
            if (con) {
                var _localStatus = parseInt(_this.find("td:eq(0) span").attr("step"));
                var _oid = _this.find("td:eq(0) input[type='checkbox']").val() != undefined ? _this.find("td:eq(0) input[type='checkbox']").val() : _this.find("td:eq(0) span").html();
                switch (_localStatus) {
                    case 1:
                        adgroupDel();
                        break;
                    case 2:
                        agReBack(_oid);
                        break;
                    case 3:
                        agDelReBack(_oid);
                        break;
                    default :
                        break;
                }
            }
        }
    }
}
/**
 * 修改还原
 * @param oid
 */
function agReBack(oid) {
    $.get("../assistantAdgroup/agReBack", {oid: oid}, function (rs) {
        var json = eval("(" + rs + ")");
        if (json.success == "1") {
            if (json.data != null) {
                var _id = json.data.adgroupId != null ? json.data.adgroupId : json.data.id;
                var _maxPrice = json.data.maxPrice != null ? json.data.maxPrice : 0.0;
                var nn = json.data.negativeWords != null ? json.data.negativeWords : "";
                var ne = json.data.exactNegativeWords != null ? json.data.exactNegativeWords : "";
                var _edit = json.data.localStatus != null ? json.data.localStatus : null;
                var _ls = getLocalStatus(parseInt(_edit));
                var _tbody =
                    "<td class='table_add'><input type='checkbox' name='adgroupCheck' value='" + _id + "' onchange='adgroupListCheck()'>" + _ls + "</td>" +
                    "<td >" + json.data.adgroupName + "</td>" +
                    "<td ><input type='hidden' value='" + json.data.status + "'/>" + until.getAdgroupStatus(json.data.status) + "</td>" +
                    "<td >" + until.convert(json.data.pause, "启用:暂停") + "</td>" +
                    "<td class='InputTd'>" + "<span>" + parseFloat(_maxPrice).toFixed(2) + "</span>" + "<span  id='InputImg' onclick='InputPrice(this)'><img  src='../public/img/zs_table_input.png'></span>" + "</td>" +
                    "<td ><input type='hidden' value='" + nn + "'><input type='hidden' value='" + ne + "'>" + getNoAdgroupLabel(nn, ne) + "</td>" +
                    "<td >" + plans.cn + "</td>" +
                    "</tr>";
                $(atmp).html(_tbody);
                loadTree();
            } else {
                $(atmp).find("td:eq(8)").html(" ");
            }
        }
    });
}
/**
 * 删除还原
 * @param oid
 */
function agDelReBack(oid) {
    $.get("../assistantAdgroup/agDelBack", {oid: oid}, function (rs) {
        if (rs == "1") {
            $(atmp).find("td:eq(8)").html(" ");
        }
    });
}
function adgroupMutli() {
    top.dialog({
        title: "添加/更新多个单元",
        padding: "5px",
        align: 'right bottom',
        id: 'adgroupMutli',
        content: "<iframe src='/assistantAdgroup/adgroupMutli' width='900' height='550' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
        oniframeload: function () {

        },
        onclose: function () {
//            loadCreativeData({cid:plans.cid,aid:plans.aid});
            if (plans.cid != null) {
                loadAdgroupData(plans.nowPage);
            }
            loadTree();
        },
        onremove: function () {
            if (plans.cid != null) {
                loadAdgroupData(plans.nowPage);
            }
        }
    }).showModal(dockObj);
}
function adgroupUpload() {
    var _this = $(atmp);
    var oid = _this.find("td:first input[type='checkbox']").val();
    var _localStatus = _this.find("td:first span").attr("step");
    if (_localStatus != undefined) {
        if (confirm("是否上传选择的数据到凤巢?一旦上传将不能还原！") == false) {
            return;
        } else {
            switch (_localStatus) {
                case "1":
                    if (oid.length > 18 && oid != undefined) {
                        adgroupUploadOperate(oid, 1);
                    }
                    break;
                case "2":
                    if (oid.length < 18 && oid != undefined) {
                        adgroupUploadOperate(oid, 2,function(){
                            $(atmp).find("td:first span").remove();
                        });
                    }
                    break;
                case "3":
                    if (oid.length < 18 && oid != undefined) {
                        adgroupUploadOperate(oid, 3);
                    } else {
                        adgroupDel();
                    }
                    break;
            }
        }
    } else {
        //alert("已经是最新数据了！");
        AlertPrompt.show("已经是最新数据了！");
        return;
    }
}
function adgroupUploadOperate(aid, ls,func) {
    $.get("/assistantAdgroup/uploadOperate", {aid: aid, ls: ls}, function (str) {
        if (str.msg == "1") {
            //alert("上传成功");
            AlertPrompt.show("上传成功");
            if (plans.cid != null) {
                getAdgroupPlan(plans.cid, plans.cn);
                loadTree();
            }
            if(func){
                func();
            }
        } else if (str.msg == "noUp") {
            var conf = confirm("该单元上级及计划没有上传，是否要一并上传？");
            if (conf) {
                $.get("/assistantAdgroup/uploadAddByUp", {aid: aid}, function (res) {
                    if (res.msg == "1") {
                        //alert("上传成功");
                        AlertPrompt.show("上传成功");
                        if (plans.cid != null) {
                            getAdgroupPlan(plans.cid, plans.cn);
                            loadTree();
                        }
                    } else {
                        //alert(res.msg);
                        AlertPrompt.show(res.msg);
                    }
                });
            }
        } else {
            //alert(str.msg);
            AlertPrompt.show(str.msg);
        }
    })
}
function adgroupListCheck() {
    var CheckCount = $("input[name='adgroupCheck']").length;
    var readyCheckCount = 0;
    for (var i = 0; i < CheckCount; i++) {
        if ($("input[name='adgroupCheck']:eq(" + i + ")").prop("checked")) {
            readyCheckCount++;
        }
    }
    if (CheckCount == readyCheckCount) {
        document.getElementsByName("adgroupAllCheck")[0].checked = true;
    } else {
        document.getElementsByName("adgroupAllCheck")[0].checked = false;
    }
}