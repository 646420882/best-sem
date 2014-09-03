/**
 * Created by XiaoWei on 2014/8/21.
 */
var plans = {cid: null, cn: null};
$(function () {
    loadAdgroupData(plans);
    initAMenu();
    rDrag.init(document.getElementById("bAdd"));
    rDrag.init(document.getElementById("aKwd"));
    rDrag.init(document.getElementById("adUp"));
    rDrag.init(document.getElementById("apKwd"));
    rDrag.init(document.getElementById("apKwd"));
    initNoKwdKeyUp();
});
var atmp = null;
var sp = null;
var nn = null;
var ne = null;
var aAdd = {
    text: "添加单元",
    func: function () {
        addAdgroup();
    }
}, aDel = {
    text: "删除单元",
    func: function () {
        adgroupDel();
    }
}, aUpdate = {
    text: "修改单元",
    func: function () {
        adgroupUpdate();
    }
}
var aMeunData = [
    [aAdd, aDel, aUpdate]
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
    $("#adGroupTable").on("keydown", "input", function (event) {
        if (event.keyCode == 13) {
            var con = confirm("你确定要添加?");
            if (con) {
                var _this = $(this).parents("tr");
                $(this).submit("../assistantAdgroup/adAdd", function (rs) {
                    if (rs == "1") {
                        var _span = $("#" + sp + "").html();
                        if (_span == null) {
                            _span = "<span>未设置</span>";
                        }
                        _this.remove();
                        var i = $("#adGroupTable tbody tr").size();
                        var _createTable = $("#adGroupTable tbody");
                        var _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
                        var _tbody = "<tr class=" + _trClass + " onclick='aon(this)'>" +
                            "<td>&nbsp;<input type='hidden'  name='oid' value='" + subData["oid"] + "'/><input type='hidden' name='cid' value='" + subData["cid"] + "'/></td>" +
                            "<td>" + subData["adgroupName"] + "</td>" +
                            "<td>本地新增</td>" +
                            " <td>" + getAdgroupPauseByBoolean(subData["pause"]) + "</td>" +
                            "<td>" + parseFloat(subData["maxPrice"]).toFixed(2) + "</td>" +
                            "<td><span>" + _span + "</span><input type='hidden' value='" + subData["negativeWords"] + "'><input type='hidden' value='" + subData["exactNegativeWords"] + "'></td>" +
                            "<td>" + parseFloat(subData["mib"]).toFixed(2) + "</td>" +
                            "<td>" + plans.cn + "</td>" +
                            "</tr>";
                        _createTable.append(_tbody);
                    } else if (rs == "3") {
                        alert("异常");
                    }
                });
            }
        }
    });
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
        var arr = $(this).val().split("\n");
        $("#nKwd_size").html(arr.length);
    });
    $("#mKwd").keydown(function (e) {
        var arr = $(this).val().split("\n");
        $("#mKwd_size").html(arr.length);
    });

    $("#npKwd").keydown(function (e) {
        var arr = $(this).val().split("\n");
        $("#npKwd_size").html(arr.length);
    });
    $("#mpKwd").keydown(function (e) {
        var arr = $(this).val().split("\n");
        $("#mpKwd_size").html(arr.length);
    });
}
/**
 * 加载单元数据
 * @param plans 根据点击树结构的计划id，如果有则根据计划加载，如果没有，查询所有单元
 */
function loadAdgroupData(plans) {
    var _adGroudTable = $("#adGroupTable tbody");
    $.get("../assistantAdgroup/getAdgroupList", {cid: plans.cid}, function (rs) {
        if (rs != "[]") {
            var json = eval("(" + rs + ")");
            if (json.length > 0) {
                _adGroudTable.empty();
                var _trClass = "";
                for (var i = 0; i < json.length; i++) {
                    _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
                    var _tbody = "<tr class=" + _trClass + " onclick=aon(this)>" +
                        "<td >&nbsp;<input type='hidden' value='" + json[i].adgroupId + "'/></td>" +
                        "<td >" + json[i].adgroupName + "</td>" +
                        "<td ><input type='hidden' value='" + json[i].status + "'/>" + until.getAdgroupStatus(json[i].status) + "</td>" +
                        "<td >" + until.convert(json[i].pause, "启用:暂停") + "</td>" +
                        "<td >" + parseFloat(json[i].maxPrice).toFixed(2) + "</td>" +
                        "<td ><input type='hidden' value='" + json[i].negativeWords + "'><input type='hidden' value='" + json[i].exactNegativeWords + "'>" + getNoAdgroupLabel(json[i].negativeWords, json[i].exactNegativeWords) + "</td>" +
                        "<td >" + parseFloat(getMib(json[i].mib)).toFixed(2) + "</td>" +
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
    }else{
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
    atmp = _this;
    var data = {};
    data[0] = _this.find("td:eq(1)").html();
    data[1] = _this.find("td:eq(4)").html();
    data[2] = _this.find("td:eq(6)").html();
    var status = _this.find("td:eq(2)").html();
    var pause = _this.find("td:eq(3)").html();
    $("#aDiv input").each(function (i, o) {
        $(o).val(data[i]);
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
    loadAdgroupData(plans);
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
        var i = $("#adGroupTable tbody tr").size();
        var _createTable = $("#adGroupTable tbody");
        var _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
        var _tbody = "<tr class=" + _trClass + ">" +
            "<td>&nbsp;<span><a href='javascript:void(0)' onclick='removeThe(this);'>删除</a></span><input type='hidden'  name='oid' value='" + getRandomId() + "'/><input type='hidden' name='cid' value='" + getAdgroupId() + "'/></td>" +
            "<td><input name='adgroupName' style='width:140px;' maxlength='1024'></td>" +
            "<td><input type='hidden' name='status' value='-1'><span>本地新增</span></td>" +
            " <td><select name='pause'><option value='true'>启用</option><option value='false'>暂停</option></select></td>" +
            "<td><input name='maxPrice' style='width:50px;' value='0.0' onkeypress='until.regDouble(this)' maxlength='3'></td>" +
            "<td><span id='" + getRandomId() + "sp'>未设置</span><input name='negativeWords' id='" + getRandomId() + "ni' type='hidden' readonly='readonly'><input type='button' onclick='adgroupNokeyword(this)' value='设置否定关键词'/><input name='exactNegativeWords' id='" + getRandomId() + "ne' type='hidden'  readonly='readonly'></td>" +
            "<td><input name='mib' style='width:50px;' value='0.0' onkeypress='until.regDouble(this)' maxlength='3'></td>" +
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
    var oid = _this.find("td:eq(0) input").val();
    var td1 = _this.find("td:eq(1) input").val();
    if (td1 == undefined) {
        var con = confirm("是否删除该计划？");
        if (con) {
            $.get("../assistantAdgroup/del", {oid: oid}, function (rs) {
                if (rs == "1") {
                    adgroupremoveThe(_this);
                }
            });
        }
    }
}
/**
 * 修改方法，根据右键选中的一条记录进行修改
 */
function adgroupUpdate() {
    var _tr = $(atmp);
    var oid = _tr.find("td:eq(0) input").val();
    var td1 = _tr.find("td:eq(1) input").val();
    if (td1 == undefined) {
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
        var oid = _tr.find("td:eq(0) input").val();
        var name = _tr.find("td:eq(1)").html();
        var status = _tr.find("td:eq(2) input").val();
        var pause = _tr.find("td:eq(3)").html();
        var maxPrice = _tr.find("td:eq(4)").html();
        var sp = _tr.find("td:eq(5) span").html();
        var nn = _tr.find("td:eq(5) input").val();
        var ne = _tr.find("td:eq(5) input:eq(1)").val();
        var mib = _tr.find("td:eq(6)").html();
        $("#adgroupUpdateForm input[name='oid']").val(oid);
        $("#adgroupUpdateForm input[name='adgroupName']").val(name);
        $("#adStatus").html(adgroupConvertStatus(parseInt(status)));
        $("#auSpan").html(sp);
        if (pause == "启用") {
            $("#adgroupUpdateForm select[name='pause']").get(0).selectedIndex = 0;
        } else {
            $("#adgroupUpdateForm select[name='pause']").get(0).selectedIndex = 1;
        }
        $("#adgroupUpdateForm input[name='mib']").val(mib);
        $("#adgroupUpdateForm input[name='maxPrice']").val(maxPrice);
        $("#adgroupUpdateForm input[name='negativeWords']").val(nn);
        $("#adgroupUpdateForm input[name='exactNegativeWords']").val(ne);
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
    var arr = _this.val().split(",");
    var arrne = _thisne.val().split(",");
    if (_this.val() != "") {
        var str = "";
        for (var i = 0; i < arr.length; i++) {
            str = str + arr[i] + "\n";
            str.substring(0, str.length - 2);
        }
        $("#nKwd").val(str);
    } else if (_thisne.val() != "") {
        var strne = "";
        for (var i = 0; i < arrne.length; i++) {
            strne = strne + arrne[i] + "\n";
            strne.substring(0, strne.length - 2);
        }
        $("#mKwd").val(strne);
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
                var str = "<option value='" + json[i].campaignId + "'>" + json[i].campaignName + "</option>";
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
    if (aid == "-1") {
        alert("请选择计划");
    } else {
        plans.cid = aid;
        plans.cn = cn;
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
    var unn = $("#adgroupUpdateForm input[name='negativeWords']").val();
    var une = $("#adgroupUpdateForm input[name='exactNegativeWords']").val();
    if (unn.indexOf(",") > -1 || unn.length > 0) {
        var arr = unn.split(",");
        $("#npKwd_size").html(arr.length);
        var str = "";
        for (var i = 0; i < arr.length; i++) {
            str = str + arr[i] + "\n";
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
            strne = strne + arrne[i] + "\n";
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
    var arr = $("#npKwd").val().split("\n");
    var arrne = $("#mpKwd").val().split("\n");
    $("#auSpan").html(getNoAdgroupLabel(arr, arrne));
    var unn = $("#adgroupUpdateForm input[name='negativeWords']").val(arr);
    var une = $("#adgroupUpdateForm input[name='exactNegativeWords']").val(arrne);
    adgroupUpdateNokwdMathClose();
}
/**
 * 点击修改确定方法
 */
function adrgoupUpdateOk() {
    var _this = $(atmp);
    $("#adgroupUpdateForm").formSubmit("../assistantAdgroup/update", function (rs) {
        if (rs == "1") {
            adgroupAddAlertClose();
            var _span = $("#auSpan").html();
            _this.remove();
            var i = $("#adGroupTable tbody tr").size();
            var _createTable = $("#adGroupTable tbody");
            var _trClass = i % 2 == 0 ? "list2_box1 list2_box3" : "list2_box2 list2_box3";
            var _tbody = "<tr class=" + _trClass + " onclick='aon(this)'>" +
                "<td>&nbsp;<input type='hidden'  name='oid' value='" + formData["oid"] + "'/><input type='hidden' name='cid' value='" + formData["cid"] + "'/></td>" +
                "<td>" + formData["adgroupName"] + "</td>" +
                "<td>" + adgroupConvertStatus(formData["status"]) + "</td>" +
                " <td>" + getAdgroupPauseByBoolean(formData["pause"]) + "</td>" +
                "<td>" + parseFloat(formData["maxPrice"]).toFixed(2) + "</td>" +
                "<td><span>" + _span + "</span><input type='hidden' value='" + formData["negativeWords"] + "'><input type='hidden' value='" + formData["exactNegativeWords"] + "'></td>" +
                "<td>" + parseFloat(formData["mib"]).toFixed(2) + "</td>" +
                "<td>" + plans.cn + "</td>" +
                "</tr>";
            _createTable.append(_tbody);
            alert("修改完成");
        }
    });
}
function adgroupdSelectChange(rs) {
    var _this = $(rs);
    var _atmp = $(atmp);
    var _oid = _atmp.find("td:eq(0) input").val();
    var pause = _this.val();
    var params = {
        pause: pause,
        oid: _oid
    }
    $.get("../assistantAdgroup/updateByChange", params, function (rs) {
        if (rs == "1") {
            _atmp.find("td:eq(3)").html(_this.find("option:selected").text());
        }
    });
}
