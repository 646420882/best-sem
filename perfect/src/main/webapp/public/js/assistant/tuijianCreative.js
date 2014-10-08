/**
 * Created by XiaoWei on 2014/9/28.
 */
var reViewData = {};//记录点击编辑按钮时读取的信息数据
var reParms = {cid: null, aid: null};
$(function () {
    textSizeValid();
});
//预览方法
function reView(res) {
    var _this = $(res);

    var viewType = 0;
    var _liObj = _this.parents("li");
    var title = _liObj.find("a:eq(0) span").html() != undefined ? _liObj.find("a:eq(0) span").html() : _liObj.find("a:eq(0)").html();
    var regxl = new RegExp("<font color=\"#CC0000\">", "g");
    var regxr = new RegExp("</font>", "g");
    var reggn = new RegExp("\n", "g");
    title = title.replace(regxl, "{");
    title = title.replace(regxr, "}");
    var destmp = _liObj.find("a:eq(1)").html() != "" ? _liObj.find("a:eq(1)").html() : _liObj.find("div:eq(2)").html();
    var url = "请输入";
    url = destmp.split("<br>")[1] != undefined ? $(destmp.split("<br>")[1]).text() : _liObj.find("a:eq(1) span").text();
    if (url == "") {
        url = _liObj.find("div:eq(3) span:eq(0)").text();
    }
    if (destmp.indexOf("<br>") > -1) {
        destmp = destmp.substr(0, destmp.indexOf("<br>"));
    }
    var des1 = $(destmp).text();
    if (des1 == "") {
        if (_liObj.find("div:eq(2) ul").html() != undefined) {
            viewType = 1;
        } else {
            des1 = _liObj.find("div:eq(2)").html();
            des1 = des1.replace(reggn, "");
            des1 = des1.replace(regxl, "{");
            des1 = des1.replace(regxr, "}");
        }
    }
    if (viewType == 1) {
        $("#subTitle").html(title);
        $("#Url").html(_liObj.find("div:eq(4) span:eq(0)").html());
        subShowEditor();
    } else {
        reViewData["title"] = title;
        reViewData["desc1"] = des1;
        reViewData["desc2"] = des1;
        reViewData["pcUrl"] = url;
        reViewData["pcsUrl"] = url;
        $("#_editor [name='title']").val(title);
        $("#_editor [name='desc1']").val(des1);
        $("#_editor [name='desc2']").val(des1);
        $("#_editor [name='pcUrl']").val(url);
        $("#_editor [name='pcsUrl']").val(url);
        initEditView(res);
        reShowEditor();
    }
}
//初始化编辑窗体
function initEditView(res) {
    $("#_editor input[type='text']").empty();
    $("#_editor input[type='textarea']").empty();
    var _span = $("#_editor span");
    _span.each(function (i, o) {
        var _max = $(o).html().split("/")[1];
        var _thisStr = 0;
        if ($(o).prev("input[type=text]").val() != undefined) {
            _thisStr = $(o).prev("input[type=text]").val().length;
        } else {
            _thisStr = $(o).prev("textarea").val().length;
        }
        if (parseInt(_thisStr) > parseInt(_max)) {
            $(o).addClass("span-error");
        }
        $(o).html(_thisStr + "/" + _max);
    });
    var _thisUl = $("#terms li");
    var _thatUl = $("#repUl");
    _thatUl.empty();
    _thisUl.each(function (i, o) {
        var n = $(o).find("span").text();
        var v = $(o).find("b").text();
        _thatUl.append("<li>" + n + "<span>" + v + "</span></li>");
    });
    var regxl = new RegExp("{", "g");
    var regxr = new RegExp("}", "g");
    var title = reViewData["title"].replace(regxl, "<font color=\"#CC0000\">");
    title = title.replace(regxr, "</font>");
    var desc = reViewData["desc1"].replace(regxl, "<font color=\"#CC0000\">");
    desc = desc.replace(regxr, "</font>");
    var url = reViewData["pcUrl"];
    var _reView = "<a href='javascript:void(0)' class='EC_t EC_BL'>" + title + "</a><br><span  class='ec_desc ec_font_small'>" + desc + "</span><span class='ec_url'>" + url + "</span>";
    var _reView2 = "<a href='javascript:void(0)' class='EC_t EC_BL'>" + title + "<font color='#080'>" + url + "</font></a><br><span  class='ec_desc ec_font_small'>" + desc + "</span><br>";
    var _reView3 = "<a href='javascript:void(0)' class='EC_t EC_BL'>" + title + "</a><br><span  class='ec_desc ec_font_small'>" + desc + "</span><span class='ec_url'>" + url + "</span>";
    $("#reLeft1").empty().append(_reView);
    $("#reLeft2").empty().append(_reView2);
    $("#reRight").empty().append(_reView3);
}
function reShowEditor() {
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    $("#riginality_editor").css({
        left: ($("body").width() - $("#riginality_editor").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#riginality_editor").height()) / 2 + ($(window).scrollTop() - 53) + "px",
        display: "block"
    });
}
function subShowEditor() {
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    $("#subLinkType").css({
        left: ($("body").width() - $("#subLinkType").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#subLinkType").height()) / 2 + ($(window).scrollTop() - 53) + "px",
        display: "block"
    });
}
function subHideEditor() {
    $(".TB_overlayBG").css({
        display: "none", height: $(document).height()
    });
    $("#subLinkType").css({
        left: ($("body").width() - $("#subLinkType").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#subLinkType").height()) / 2 + ($(window).scrollTop() - 53) + "px",
        display: "none"
    });
}
function reHideEditor() {
    $(".TB_overlayBG").css({
        display: "none", height: $(document).height()
    });
    $("#riginality_editor").css({
        left: ($("body").width() - $("#riginality_editor").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#riginality_editor").height()) / 2 + ($(window).scrollTop() - 53) + "px",
        display: "none"
    });
}
function reShowSelect() {
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    $("#jcAdd").css({
        left: ($("body").width() - $("#jcAdd").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#jcAdd").height()) / 2 + ($(window).scrollTop() - 53) + "px",
        display: "block"
    });
}
function reHideSelect() {
    $(".TB_overlayBG").css({
        display: "none", height: $(document).height()
    });
    $("#jcAdd").css({
        left: ($("body").width() - $("#jcAdd").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#jcAdd").height()) / 2 + ($(window).scrollTop() - 53) + "px",
        display: "none"
    });
}
function reSave() {
    var _span = $("#_editor span");
    var error = 0;
    _span.each(function (i, o) {
        if ($(o).attr("class") == "span-error") {
            error++;
        }
    });
    if (parseInt(error) == 0) {
        reViewData["title"] = $("#_editor [name='title']").val();
        reViewData["desc1"] = $("#_editor [name='desc1']").val();
        reViewData["desc2"] = $("#_editor [name='desc2']").val();
        reViewData["pcUrl"] = $("#_editor [name='pcUrl']").val();
        reViewData["pcsUrl"] = $("#_editor [name='pcsUrl']").val();
        reHideEditor();
        reShowSelect();
        var jcBox = $("#jcUl");
        jcBox.empty();
        getPlans();
        jcBox.append("<li>推广计划<select id='sPlan' onchange='loadUnit(this.value)'><option value='-1'>请选择计划</option></select></li>");
        jcBox.append("<li>推广单元<select id='sUnit' ><option value='-1'>请选择单元</option></select></li>");
        $("#reOkView").show();
    } else {
        alert("请核对数据后再提交！");
    }
}
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
                    var _id = null;
                    if (json[i].adgroupId != null) {
                        _id = json[i].adgroupId;
                    } else {
                        _id = json[i].id;
                    }
                    var str = "<option value='" + _id + "'>" + json[i].adgroupName + "</option>";
                    $("#sUnit").append(str);
                }
            }
        } else {
            _sUnit.empty();
            _sUnit.append(_def);
        }
    });
}
function replanUnit() {
    var cid = $("#sPlan :selected").val() == undefined ? sparams.cid : $("#sPlan :selected").val();
    var aid = $("#sUnit :selected").val() == undefined ? sparams.aid : $("#sUnit :selected").val();
    if (cid == "-1") {
        alert("请选择计划");
    } else if (aid == "-1") {
        alert("请选择单元");
    } else {
        reParms.cid = cid;
        reParms.aid = aid;
        saveUpload();
        reHideEditor();
        reHideSelect();
    }
}
function recloseAlert() {
    reParms.cid = null;
    reParms.aid = null;
    reHideSelect();
    reShowEditor();
}
function saveUpload() {
    var regxl = new RegExp("{", "g");
    var regxr = new RegExp("}", "g");
    var title = reViewData["title"].replace(regxl, "<font color=\"#CC0000\">");
    title = title.replace(regxr, "</font>");
    var desc1 = reViewData["desc1"].replace(regxl, "<font color=\"#CC0000\">");
    desc1 = desc1.replace(regxr, "</font>");
    var desc2 = reViewData["desc2"].replace(regxl, "<font color=\"#CC0000\">");
    desc2 = desc2.replace(regxr, "</font>");
    $("#rTitle").html(title);
    $("#rDesc").html(desc1 + desc2);
    $("#rUrl").html(reViewData["pcUrl"]);
}
function addCreativeOk() {
    reViewData["aid"] = reParms.aid;
    reViewData["cid"] = reParms.cid;
    if (reViewData["aid"] != null && reViewData["cid"] != null) {
        $.post("/assistantCreative/uploadCreative", reViewData, function (result) {
            if (result == "1") {
                reParms.aid, reParms.cid = null;
                $("#rTitle").html("xxxxxxxx");
                $("#rDesc").html("xxxxxxxx");
                $("#rUrl").html("xxxxxxxxx");
                alert("上传成功!");
                $("#reOkView").hide();
            } else {
                alert("创意描述存在非法字符，请检查后提交！");
            }
        });
    } else {
        alert("请选择计划或者单元！");
    }
}
function textSizeValid() {
    $("#_editor input[type='text']").keyup(function () {
        var _max = $(this).next("span").html().split("/")[1];
        var _thisStr = $(this).val().length;
        if (parseInt(_thisStr) > parseInt(_max)) {
            $(this).next("span").removeClass().addClass("span-error");
        } else {
            $(this).next("span").removeClass().addClass("span-ok");
        }
        $(this).next("span").html(_thisStr + "/" + _max);
    });
    $("#_editor textarea").keyup(function () {
        var _max = $(this).next("span").html().split("/")[1];
        var _thisStr = $(this).val().length;
        if (parseInt(_thisStr) > parseInt(_max)) {
            $(this).next("span").removeClass().addClass("span-error");
        } else {
            $(this).next("span").removeClass().addClass("span-ok");
        }
        $(this).next("span").html(_thisStr + "/" + _max);
    });
}
function addSub() {
    var _ul = $("#sub");
    var _input = $("#sub input");
    var error = 0;
    if (_ul.find("li").size() < 10) {
        _input.each(function (i, o) {
            if ($(o).val() == "") {
                error++;
            }
        });
        if (error > 0) {
            alert("请正确输入！");
        } else {
            var _input = "<li>描述：<input type='text' name='subtitle'/>描述url<input type='text' name='subUrl'/><a href='javascript:void(0)' onclick='removeLi(this)'>删除</a></li>";
            $("#sub").append(_input);
        }
    } else {
        alert("一次最多添加10条！");
    }
}
function removeLi(rs) {
    $(rs).parents("li").remove();
}