/**
 * Created by XiaoWei on 2014/8/21.
 */
/**
 * 树加载数据需要的计划，单元参数，默认都为空
 * @type {{aid: null, cid: null}}
 */

var sparams = {aid: null, cid: null};
$(function () {
    loadCreativeData(sparams);
    InitMenu();
    rDrag.init(document.getElementById("dAdd"));
    rDrag.init(document.getElementById("dUpdate"));
});

/**
 * 菜单名，方法
 * @type {{text: string, func: func}}
 */
var add = {
    text: "添加创意",
    func: function () {
        addCreative();
    }
}, del = {
    text: "删除创意",
    func: function () {
        deleteByObjectId(tmp);
    }
}, update = {
    text: "修改创意",
    func: function () {
        updateCreatvie(tmp);
    }
}
/**
 * 右键菜单显示的选项
 * @type {*[]}
 */
var menuData = [
    [add, del, update]
];
/**
 * 用户缓存右键点击的对象
 * @type {null}
 */
var tmp = null;
/**
 * 菜单name值，标识唯一，beforeShow显示完成后方法
 * @type {{name: string, beforeShow: beforeShow}}
 */
var menuExt = {
    name: "creative",
    beforeShow: function () {
        var _this = $(this);
        tmp = _this;
        $.smartMenu.remove();
    }
};
/**
 * 初始化右键菜单
 * @constructor
 */
function InitMenu() {
    $("#createTable").on("mousedown", "tr", function () {
        $(this).smartMenu(menuData, menuExt);
    });
    $("#createTable").on("keydown", "input", function (event) {
        if (event.keyCode == 13) {
            var con = confirm("你确定要添加么？");
            if (con) {
                var _selects = $(this).parents("tr").find("select");
                var _input = $(this).parents("tr").find("input");
                var _tr = $(this).parents("tr");
                $(this).submit("../assistantCreative/add", function (rs) {
                    var json=eval("("+rs+")");
                    if (json.success == "1") {
                        _tr.remove();
                        var data = {};
                        for (var i = 0; i < _input.size(); i++) {
                            if (_input[i].name) {
                                data[_input[i].name] = _input[i].value;
                            }
                        }
                        for (var i = 0; i < _selects.length; i++) {
                            data[_selects[i].name] = _selects[i].value;
                        }
                        var p = data["pause"] == "true" ? "启用" : "暂停";
                        var s = until.getCreativeStatus(parseInt(data["status"]));
                        var _createTable = $("#createTable tbody");
                        var i = $("#createTable tbody tr").size();
                        var _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
                        var _tbody = "<tr class=" + _trClass + " onclick='on(this);''>" +
                            "<td>&nbsp;<span style='display: none;'>" + json.data + "</span></td>" +
                            "<td >" + until.substring(10, data["title"]) + "</td>" +
                            " <td >" + until.substring(10, data["description1"]) + "</td>" +
                            " <td >" + until.substring(10, data["description2"]) + "</td>" +
                            " <td ><a href='" + data["pcDestinationUrl"] + "' target='_blank'>" + until.substring(10, data["pcDestinationUrl"]) + "</a></td>" +
                            " <td >" + until.substring(10, data["pcDisplayUrl"]) + "</td>" +
                            " <td >" + until.substring(10, data["mobileDestinationUrl"]) + "</td>" +
                            " <td >" + until.substring(10, data["mobileDisplayUrl"]) + "</td>" +
                            " <td >" + p + "</td>" +
                            " <td >" + s + "</td>" +
                            "</tr>";
                        _createTable.append(_tbody);
                    }
                });
            }
        }
    });
}
/**
 * 加载创意数据
 * @param params
 */
function loadCreativeData(params) {
    $.get("/assistantCreative/getList", params, function (result) {
        var _createTable = $("#createTable tbody");
        if (result != "[]") {
            var json = eval("(" + result + ")");
            if (json.length > 0) {
                _createTable.empty();
                var _trClass = "";
                for (var i = 0; i < json.length; i++) {
                    var _id=json[i].creativeId!=null?json[i].creativeId:json[i].id;
                    _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
                    var _tbody = "<tr class=" + _trClass + " onclick='on(this);''>" +
                        "<td >&nbsp;<input type='hidden' value='" +_id + "'/></td>" +
                        "<td >" + until.substring(10, json[i].title) + "</td>" +
                        " <td >" + until.substring(10, json[i].description1) + "</td>" +
                        " <td >" + until.substring(10, json[i].description2) + "</td>" +
                        " <td ><a href='" + json[i].pcDestinationUrl + "' target='_blank'>" + until.substring(10, json[i].pcDestinationUrl) + "</a></td>" +
                        " <td >" + until.substring(10, json[i].pcDisplayUrl) + "</td>" +
                        " <td>" + until.substring(10, json[i].mobileDestinationUrl) + "</td>" +
                        " <td >" + until.substring(10, json[i].mobileDisplayUrl) + "</td>" +
                        " <td >" + until.convert(json[i].pause, "启用:暂停") + "</td>" +
                        " <td >" + until.getCreativeStatus(parseInt(json[i].status)) + "<input type='hidden' value='" + json[i].status + "'/></td>" +
                        "</tr>";
                    _createTable.append(_tbody);
                }
            }
        } else {
            _createTable.empty();
            _createTable.append("<tr><td>暂无数据</td></tr>");
        }
    });
}

/**
 * 鼠标单击显示详细信息
 * @param obj
 */
function on(obj) {

    preview(obj);
    $("#sDiv input[type='text']").val("");
    var _this = $(obj);
    var title = _this.find("td:eq(1) a").attr("title") != undefined ? _this.find("td:eq(1) a").attr("title") : _this.find("td:eq(1) input").val();
    if (title == undefined) {
        title = _this.find("td:eq(1) span").html();
    }
    var de1 = _this.find("td:eq(2) a").attr("title") != undefined ? _this.find("td:eq(2) a").attr("title") : _this.find("td:eq(2) input").val();
    if (de1 == undefined) {
        de1 = _this.find("td:eq(2) span").html();
    }
    var de2 = _this.find("td:eq(3) a").attr("title") != undefined ? _this.find("td:eq(3) a").attr("title") : _this.find("td:eq(3) input").val();
    if (de2 == undefined) {
        de2 = _this.find("td:eq(3) span").html();
    }
    var pc = _this.find("td:eq(4) a").attr("href") != undefined ? _this.find("td:eq(4) a").attr("href") : _this.find("td:eq(4) input").val();
    if (pc == undefined) {
        pc = _this.find("td:eq(4) span").html();
    }
    var pcs = _this.find("td:eq(5) a").attr("title") != undefined ? _this.find("td:eq(5) a").attr("title") : _this.find("td:eq(5) input").val();
    if (pcs == undefined) {
        pcs = _this.find("td:eq(5) span").html();
    }
    var mib = _this.find("td:eq(6) span:eq(0)").text() != "" ? _this.find("td:eq(6) span:eq(0)").text() : _this.find("td:eq(6) input").val();
    if (mib == undefined) {
        mib = _this.find("td:eq(6) a").attr("title");
    }
    var mibs = _this.find("td:eq(7) span:eq(0)").text() != "" ? _this.find("td:eq(7) span:eq(0)").text() : _this.find("td:eq(7) input").val();
    if (mibs == undefined) {
        mibs = _this.find("td:eq(7) a").attr("title");
    }
    var pause = _this.find("td:eq(8) select") == "" ? _this.find("td:eq(10)").find("select") : _this.find("td:eq(8)").html();
    var status = _this.find("td:eq(9) select") == "" ? _this.find("td:eq(9)").find("select") : _this.find("td:eq(9)").html();

    $("#sTitle").val(title).keyup(function (e) {
        $("#sTitle_size").text($("#sTitle").val().length);
    });
    $("#sTitle_size").text(title.length);

    $("#sDes1").val(de1).keyup(function () {
        $("#sDes1_size").text($("#sDes1").val().length);
    });
    $("#sDes1_size").text(de1.length);

    $("#sDes2").val(de2).keyup(function () {
        $("#sDes2_size").text($("#sDes2").val().length);
    });
    $("#sDes2_size").text(de2.length);

    $("#sPc").val(pc).keyup(function () {
        $("#sPc_size").text($("#sPc").val().length);
    });
    $("#sPc_size").text(pc.length);

    $("#sPcs").val(pcs).keyup(function () {
        $("#sPcs_size").text($("#sPcs").val().length);
    });
    $("#sPcs_size").text(pcs.length);

    $("#sMib").val(mib).keyup(function () {
        $("#Mib_size").text($("#sMib").val().length);
    });
    $("#sMib_size").text(mib.length);

    $("#sMibs").val(mibs).keyup(function () {
        $("#Mibs_size").text($("#sMibs").val().length);
    });
    $("#sMibs_size").text(mibs.length);
    $("#sPause").html(pause);
    $("#sStatus").html(status);

}
/**
 * 未知方法，待继续编码
 * @param rs
 */
function addTb(rs) {
    var _this = $(rs);
    var val = _this.prev("input");
}
/**
 * 添加创意
 */
function addCreative() {
    var jcBox = $("#jcUl");
    if (sparams.cid != null && sparams.aid != null) {
        var i = $("#createTable tbody tr").size();
        var _createTable = $("#createTable tbody");
        var _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
        var _tbody = "<tr class=" + _trClass + " onclick='on(this);''>" +
            "<td>&nbsp;<span><a href='javascript:void(0)' onclick='removeThe(this);'>删除</a></span><input type='hidden' name='cacheCativeId' value=''/><input type='hidden' name='aid' value='" + getCreativeAId() + "'/></td>" +
            "<td><input name='title' onkeyup='onKey(this);' style='width:140px;' maxlength='50'></td>" +
            " <td><input name='description1' onkeyup='onKey(this);'  style='width:140px;'  maxlength='80'></td>" +
            " <td><input name='description2' onkeyup='onKey(this);'  style='width:140px;' maxlength='80'></td>" +
            " <td><input name='pcDestinationUrl' onkeyup='onKey(this);' style='width:40px;'  maxlength='1024'></td>" +
            " <td><input name='pcDisplayUrl' onkeyup='onKey(this);' style='width:40px;'  maxlength='36'></td>" +
            " <td><input name='mobileDestinationUrl' onkeyup='onKey(this);' style='width:40px;' maxlength='1024'></td>" +
            " <td><input name='mobileDisplayUrl' onkeyup='onKey(this);' style='width:40px;' maxlength='36'></td>" +
            " <td><select name='pause'><option value='true'>启用</option><option value='false'>暂停</option></select></td>" +
            " <td><span>本地新增</span><input type='hidden' value='-1' name='status'></td>" +
            "</tr>";
        _createTable.append(_tbody);
    } else if (sparams.cid != null && sparams.aid == null) {
        jcBox.empty();
        loadUnit(sparams.cid);
        jcBox.append("<li>推广单元<select id='sUnit' onchange='loadTree(this.value)'><option value='-1'>请选择单元</option></select></li>");
        creativeAddBoxShow();
    } else if (sparams.cid == null && sparams.aid == null) {
        jcBox.empty();
        getPlans();
        jcBox.append("<li>推广计划<select id='sPlan' onchange='loadUnit(this.value)'><option value='-1'>请选择计划</option></select></li>");
        jcBox.append("<li>推广单元<select id='sUnit' onchange='loadTree(this.value)'><option value='-1'>请选择单元</option></select></li>");
        creativeAddBoxShow();
    } else {
        alert(sparams.cid + ":" + sparams.aid);
    }
}
function getCreativeAId() {
    return sparams.aid;
}
/**
 * 查询所有计划，生成select的Option对象
 */
function getAllPlan() {
}
/**
 * 添加创意时，如果没有选择计划或者单元，弹出框显示
 */
function creativeAddBoxShow() {
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    $("#jcAdd").css({
        left: ($("body").width() - $("#jcAdd").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#jcAdd").height()) / 2 + $(window).scrollTop() + "px",
        display: "block"
    });
}
/**
 *
 * @returns {string}动态获取创意添加选择状态
 */
function getStatus(number) {
    switch (number) {
        case "51":
            return "有效";
            break;
        case "52":
            return "暂停推广";
            break;
        case "53":
            return "不宜推广";
            break;
        case "54":
            return "待激活";
            break;
        case "55":
            return "审核中";
            break;
        case "56":
            return "部分无效";
            break;
        case "-1":
            return "本地新增";
            break;
    }
}
/**
 * 右键删除，点击删除
 * @param rs
 */
function removeThe(rs) {
    var _this = $(rs);
    _this.parents("tr").remove();
    _this.remove();
    toolBarInit();
}
/**
 * 动态添加的文本域数据数据时显示的字数多少
 * @param rs
 */
function onKey(rs) {
    var val = rs.value;
    var _this = $(rs).parents("td").find("input");
    var name = _this.attr("name");
    switch (name) {
        case "title":
            $("#sTitle").val(val);
            $("#sTitle_size").text(val.length);
            break;
        case "description1":
            $("#sDes1").val(val);
            $("#sDes1_size").text(val.length);
            break;
        case "description2":
            $("#sDes2").val(val);
            $("#sDes2_size").text(val.length);
            break;
        case "pcDestinationUrl":
            $("#sPc").val(val);
            $("#sPc_size").text(val.length);
            break;
        case "pcDisplayUrl":
            $("#sPcs").val(val);
            $("#sPcs_size").text(val.length);
            break;
        case "mobileDisplayUrl":
            $("#sMibs").val(val);
            $("#sMibs_size").text(val.length);
            break;
        case "mobileDestinationUrl":
            $("#sMib").val(val);
            $("#sMib_size").text(val.length);
            break;
    }

}
/**
 * 初始化工具栏
 */
function toolBarInit() {
    $("#sDiv input[type='text']").val("");
    var span_size = $("#sDiv span").length;
    for (var i = 0; i < span_size; i++) {
        if (i % 2 != 0) {
            $("#sDiv span:eq(" + i + ")").text(0);
        }
    }
}
/**
 * 加载预览效果
 * @param obj
 */
function preview(obj) {
    var _this = $(obj);
    var previeBody = $("#sPreview");
    previeBody.empty();
    var title = _this.find("td:eq(1) a").attr("title") != undefined ? _this.find("td:eq(1) a").attr("title") : _this.find("td:eq(1) input").val();
    if (title == undefined) {
        title = _this.find("td:eq(1)").html();
    }
    var de1 = _this.find("td:eq(2) a").attr("title") != undefined ? _this.find("td:eq(2) a").attr("title") : _this.find("td:eq(2) input").val();
    if (de1 == undefined) {
        de1 = _this.find("td:eq(2)").html();
    }
    var de2 = _this.find("td:eq(3) a").attr("title") != undefined ? _this.find("td:eq(3) a").attr("title") : _this.find("td:eq(3) input").val();
    if (de2 == undefined) {
        de2 = _this.find("td:eq(3)").html();
    }
    var pc = _this.find("td:eq(4) a").attr("href") != undefined ? _this.find("td:eq(4) a").attr("href") : _this.find("td:eq(4) input").val();
    var pcs = _this.find("td:eq(5) a").attr("title") != undefined ? _this.find("td:eq(5) a").attr("title") : _this.find("td:eq(5) input").val();
    var mib = _this.find("td:eq(6) span:eq(0)").text() != "" ? _this.find("td:eq(6) span:eq(0)").text() : _this.find("td:eq(6) input").val();
    var mibs = _this.find("td:eq(7) span:eq(0)").text() != "" ? _this.find("td:eq(7) span:eq(0)").text() : _this.find("td:eq(7) input").val();
    title = title.replace("{", "<span style='color:red;'>").replace("}", "</span>").replace("{", "<span style='color:red;'>").replace("}", "</span>");
    de1 = de1.replace("{", "<span style='color:red;'>").replace("}", "</span>");
    de2 = de2.replace("{", "<span style='color:red;'>").replace("}", "</span>");
    var h3 = "<a href='" + pc + "' target='_blank'><h3>" + title + "</h3></a>" +
        "<span style='color:black;'>" + de1 + "</span></br>" +
        "<span style='color:black;'>" + de2 + "<span></br>" +
        "<span style='color:green;font-size: 12px;'>" + pc + "<span></br>";
    previeBody.append(h3);
}
/**
 * 编辑方法，传入td内容
 * @param rs
 */
function edit(rs) {
    var _this = $(rs);
    var _tr = _this.parents("tr");
    var _td = _tr.find("td:eq(1)").html();
}
/**
 * 动态更新创意中的数据，如果 是点击计划树
 * @param cid
 */
function getCreativePlan(cid) {
    sparams = {cid: cid, aid: null};
    loadCreativeData(sparams);
}
/**
 * 动态更新创意中的数据，如果点击单元树
 * @param con
 */
function getCreativeUnit(con) {
    sparams = {cid: con.cid, aid: con.aid}
    loadCreativeData(sparams);
}
/**
 * 选择推广计划和单元
 */
function planUnit() {
    var cid = $("#sPlan :selected").val() == undefined ? sparams.cid : $("#sPlan :selected").val();
    var aid = $("#sUnit :selected").val() == undefined ? sparams.aid : $("#sUnit :selected").val();
    if (cid == "-1") {
        alert("请选择计划");
    } else if (aid == "-1") {
        alert("请选择单元");
    } else {
        sparams.cid = cid;
        sparams.aid = aid;
        closeAlert();
        addCreative();
    }


}
/**
 * 关闭弹窗
 */
function closeAlert() {
    $(".TB_overlayBG").css("display", "none");
    $("#jcAdd ").css("display", "none");
    $("#jcUpdate ").css("display", "none");
}
/**
 * 获取全部的推广计划，生成select选项
 */
function getPlans() {
    $.get("/assistantCreative/getPlans", function (rs) {
        var json = eval("(" + rs + ")");
        if (json.length > 0) {
            for (var i = 0; i < json.length; i++) {
                var str = "<option value='" + json[i].campaignId + "'>" + json[i].campaignName + "</option>";
                $("#sPlan").append(str);
            }
        }
    });
}
/**
 * 根据选择的计划加载单元
 */
function loadUnit(rs) {
    var planId = rs;
    var _sUnit = $("#sUnit");
    var _def = "<option value='-1'>请选择单元</option>";
    $.get("/assistantCreative/getUnitsByPlanId", {planId: planId}, function (rs) {
        if (rs != "[]") {
            var json = eval("(" + rs + ")");
            if (json.length > 0) {
                _sUnit.empty();
                _sUnit.append(_def);
                for (var i = 0; i < json.length; i++) {
                    var _id=null;
                    if(json[i].adgroupId!=null){
                        _id=json[i].adgroupId;
                    }else{
                        _id=json[i].id;
                    }
                    var str = "<option value='" +_id+ "'>" + json[i].adgroupName + "</option>";
                    $("#sUnit").append(str);
                }
            }
        } else {
            _sUnit.empty();
            _sUnit.append(_def);
        }
    });
}
/**
 * 弹出框选择计划或者单元树对应加载
 * @param rs
 */
function loadTree(rs) {
    if (rs != "-1") {
        var cid = $("#sPlan :selected").val() == undefined ? sparams.cid : $("#sPlan :selected").val();
        sparams = {cid: cid, aid: rs};
        loadCreativeData(sparams);
    }
}
/**
 * 根据mongoId 删除创意
 * @param temp 选择的对象
 */
function deleteByObjectId(temp) {
    var oid = temp.find("td:eq(0) input").val() != undefined ? temp.find("td:eq(0) input").val() : temp.find("td:eq(0) span").html();
    var con = confirm("是否删除该创意？");
    if (con) {
        $.get("/assistantCreative/del", {oid: oid}, function (rs) {
            if (rs == "1") {
                removeThe(temp);
            }
        });
    }
}
/**
 * 修改选中的创意
 * @param temp
 */
function updateCreatvie(temp) {
    var _update = $("#jcUpdate");
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height(),
        opacity: 0.2
    });
    _update.css({
        left: ($("body").width() - _update.width()) / 2 - 20 + "px",
        top: ($(window).height() - _update.height()) / 2 + $(window).scrollTop() + "px",
        display: "block"
    });
    var _tr = $(temp);
    var creativeId = _tr.find("td:eq(0) input").val() != undefined ? _tr.find("td:eq(0) input").val() : _tr.find("td:eq(0) span").html();
    var title = _tr.find("td:eq(1) a").attr("title") != undefined ? _tr.find("td:eq(1) a").attr("title") : _tr.find("td:eq(1) span").html();
    var description1 = _tr.find("td:eq(2) a").attr("title") != undefined ? _tr.find("td:eq(2) a").attr("title") : _tr.find("td:eq(2) span").html();
    var description2 = _tr.find("td:eq(3) a").attr("title") != undefined ? _tr.find("td:eq(3) a").attr("title") : _tr.find("td:eq(3) span").html();
    var pcDestinationUrl = _tr.find("td:eq(4) a").attr("href") != undefined ? _tr.find("td:eq(4) a").attr("href") : _tr.find("td:eq(4) span").html();
    var pcDisplayUrl = _tr.find("td:eq(5) a").attr("title") != undefined ? _tr.find("td:eq(5) a").attr("title") : _tr.find("td:eq(5) span").html();
    var mobileDestinationUrl = _tr.find("td:eq(6) a").attr("title") != undefined ? _tr.find("td:eq(6) a").attr("title") : _tr.find("td:eq(6) span").html();
    var mobileDisplayUrl = _tr.find("td:eq(7) a").attr("title") != undefined ? _tr.find("td:eq(7) a").attr("title") : _tr.find("td:eq(7) span").html();
    var status = _tr.find("td:eq(9) input").val();
    var pause = _tr.find("td:eq(8)").html();
    $("#cUpdateForm input[name='oid']").val(creativeId);
    $("#cUpdateForm input[name='title']").val(title);
    $("#cUpdateForm input[name='description1']").val(description1);
    $("#cUpdateForm input[name='description2']").val(description2);
    $("#cUpdateForm input[name='pcDestinationUrl']").val(pcDestinationUrl);
    $("#cUpdateForm input[name='pcDisplayUrl']").val(pcDisplayUrl);
    $("#cUpdateForm input[name='mobileDestinationUrl']").val(mobileDestinationUrl);
    $("#cUpdateForm input[name='mobileDisplayUrl']").val(mobileDisplayUrl);
    $("#cuStatus").html(getStatus(status));
    $("#cUpdateForm input[name='status']").val(status);
    if (pause == "启用") {
        $("#cUpdateForm select[name='pause']").get(0).selectedIndex = 0;
    } else {
        $("#cUpdateForm select[name='pause']").get(0).selectedIndex = 1;
    }
}
/**
 * 修改确认提交方法
 */
function updateOk() {

    var _this = $(tmp);
    $("#cUpdateForm").formSubmit("/assistantCreative/update", function (rs) {
        if (rs == "1") {
            _this.remove();
            var p = formData["pause"] == "true" ? "启用" : "暂停";
            var _createTable = $("#createTable tbody");
            var i = $("#createTable tbody tr").size();
            var _trClass = i % 2 == 0 ? "list2_box1 list2_box3" : "list2_box2 list2_box3";
            var _tbody = "<tr class=" + _trClass + " onclick='on(this);''>" +
                "<td>&nbsp;<span style='display: none;'>" + formData["oid"] + "</span></td>" +
                "<td >" + until.substring(10, formData["title"]) + "</td>" +
                " <td >" + until.substring(10, formData["description1"]) + "</td>" +
                " <td >" + until.substring(10, formData["description2"]) + "</td>" +
                " <td ><a href='" + formData["pcDestinationUrl"] + "' target='_blank'>" + until.substring(10, formData["pcDestinationUrl"]) + "</a></td>" +
                " <td >" + until.substring(10, formData["pcDisplayUrl"]) + "</td>" +
                " <td >" + until.substring(10, formData["mobileDestinationUrl"]) + "</td>" +
                " <td >" + until.substring(10, formData["mobileDisplayUrl"]) + "</td>" +
                " <td >" + p + "</td>" +
                " <td >" + until.getCreativeStatus(parseInt(formData["status"])) + "</td>" +
                "</tr>";
            _createTable.append(_tbody);
            alert("修改完成");
            closeAlert();

        }
    });
}
/**
 * 获取10为数字的随机数
 * @returns {number}
 */
function getRandomId() {
    return Math.floor(Math.random() * 10000000000) + 1;
}







