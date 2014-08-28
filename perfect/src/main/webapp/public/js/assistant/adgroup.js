/**
 * Created by XiaoWei on 2014/8/21.
 */
var plans = {cid: null, cn: null};
$(function () {
    loadAdgroupData(plans);
    initAMenu();
    rDrag.init(document.getElementById("bAdd"));
    rDrag.init(document.getElementById("aKwd"));
    initNoKwdKeyUp();
});
var atmp = null;
var nn = null;
var ne=null;
var aAdd = {
    text: "添加单元",
    func: function () {
        addAdgroup();
    }
}, aDel = {
    text: "删除单元",
    func: function () {
    }
}, aUpdate = {
    text: "修改单元",
    func: function () {
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
    $("#adGroupTable").on("keydown","input",function(event){
        if(event.keyCode==13){
            var con=confirm("你确定要添加?");
            if(con){
                $(this).submit("aaa",function(rs){
                    alert(rs);
                });
            }
        }
    });
}
function initNoKwdKeyUp(){
    $("#nKwd").keydown(function (e) {
        var arr = $(this).val().split("\n");
        $("#nKwd_size").html(arr.length);
    });
    $("#mKwd").keydown(function (e) {
        var arr = $(this).val().split("\n");
        $("#mKwd_size").html(arr.length);
    });
}
/**
 * 加载单元数据
 * @param plans 根据点击树结构的计划id，如果有则根据计划加载，如果没有，查询所有单元
 */
function loadAdgroupData(plans) {
    var _adGroudTable = $("#adGroupTable tbody");
    $.get("../assistantAdgroup/getAdgroupList", {cid:plans.cid}, function (rs) {
        if (rs != "[]") {
            var json = eval("(" + rs + ")");
            if (json.length > 0) {
                _adGroudTable.empty();
                var _trClass = "";
                for (var i = 0; i < json.length; i++) {
                    _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
                    var _tbody = "<tr class=" + _trClass + "> onclick=aon(this)" +
                        "<td >&nbsp;<input type='hidden' value='" + json[i].adgroupId + "'/></td>" +
                        "<td >" + json[i].adgroupName + "</td>" +
                        "<td >" + json[i].status + "</td>" +
                        "<td >" + until.convert(json[i].pause, "启用:暂停") + "</td>" +
                        "<td >" + parseFloat(json[i].maxPrice).toFixed(2) + "</td>" +
                        "<td >" + json[i].negativeWords + "</td>" +
                        "<td >" + json[i].negativeWords + "</td>" +
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
function aon(ts) {

}
/**
 * 根据点击的树的计划获取单元
 * @param rs
 */
function getAdgroupPlan(rs,name) {
    plans.cid = rs;
    plans.cn=name;
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
            "<td>&nbsp;<span><a href='javascript:void(0)' onclick='removeThe(this);'>删除</a></span><input type='hidden'  name='oid' value='" + getRandomId() + "'/><input type='hidden' name='cid' value='"+getAdgroupId()+"'/></td>" +
            "<td><input name='adgroupName' style='width:140px;' maxlength='1024'></td>" +
            "<td><input type='hidden' name='status' value='-1'><span>本地新增</span></td>" +
            " <td><select name='pause'><option value='true'>启用</option><option value='false'>暂停</option></select></td>" +
            "<td><input name='maxPrice' style='width:50px;' value='0.0' onkeypress='until.regDouble(this)' maxlength='24'></td>" +
            "<td><input name='negativeWords' id='" + getRandomId() + "ni' type='hidden' readonly='readonly'><input type='button' onclick='adgroupNokeyword(this)' value='设置否定关键词'/><input name='exactNegativeWords' id='" + getRandomId() + "ne' type='hidden'  readonly='readonly'></td>" +
            "<td><input name='bili' style='width:50px;' value='0.0' onkeypress='until.regDouble(this)' maxlength='24'></td>" +
            "<td>" + plans.cn + "</td>" +
            "</tr>";
        _createTable.append(_tbody);
    }
}
function getAdgroupId(){
    return plans.cid;
}
/**
 * 弹出设置否定关键词页面
 * @param rs
 */
function adgroupNokeyword(rs) {
    var _this = $(rs).prev("input");
    var _thisne=$(rs).next("input");
    nn = _this.attr("id");
    ne=_thisne.attr("id");
    if (_this.val() != "") {
        var arr = _this.val().split(",");
        var str = "";
        for (var i = 0; i < arr.length; i++) {
            str =str+ arr[i] + "\n";
            str.substring(0,str.length-2);
        }
        $("#nKwd").val(str);
    } else if (_thisne.val() != "") {
        var arrne = _thisne.val().split(",");
        var strne = "";
        for (var i = 0; i < arrne.length; i++) {
            strne =strne+ arrne[i] + "\n";
            strne.substring(0,strne.length-2);
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
function initNoKeyAlert(){
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
 * 获取
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
