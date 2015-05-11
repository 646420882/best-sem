/**
 * Created by XiaoWei on 2014/9/28.
 */
$.fn.selectionTp = function () {
    var s, e, range, stored_range;
    if (this[0].selectionStart == undefined) {
        var selection = document.selection;
        if (this[0].tagName.toLowerCase() != "textarea") {
            var val = this.val();
            range = selection.createRange().duplicate();
            range.moveEnd("character", val.length);
            s = (range.text == "" ? val.length : val.lastIndexOf(range.text));
            range = selection.createRange().duplicate();
            range.moveStart("character", -val.length);
            e = range.text.length;
        } else {
            range = selection.createRange(),
                stored_range = range.duplicate();
            stored_range.moveToElementText(this[0]);
            stored_range.setEndPoint('EndToEnd', range);
            s = stored_range.text.length - range.text.length;
            e = s + range.text.length;
        }
    } else {
        s = this[0].selectionStart,
            e = this[0].selectionEnd;
    }
    var te = this[0].value.substring(s, e);
    return {start: s, end: e, text: te};
};
var reViewData = {};//记录点击编辑按钮时读取的信息数据
var reParms = {cid: null, aid: null};
var doMain = "";
$(function () {
    textSizeValid();
    onKeyDownView();
});
//预览方法
function reView(res) {
    if ($("#dm").val() == "") {
        $.get("/assistantCreative/getDomain", function (result) {
            if (result != "0") {
                $("#dm").val(result);
            }
        });
    }
    var _this = $(res);
    var viewType = 0;
    var _liObj = _this.parents("li");
    if (_liObj.html().indexOf("tbody") == -1) {
        if (_liObj.find("div:eq(2)").html().indexOf("EC_palist") == -1) {
            if (_liObj.find("div:eq(2)").html().indexOf("EC_pap_big") == -1) {
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
                //判断如果这里是闪投创意
                if (viewType == 1) {
                    alert("该创意是闪投创意，暂不支持！");
//        $("#subTitle").html(title);
//        $("#Url").html(_liObj.find("div:eq(4) span:eq(0)").html());
//        subShowEditor(_liObj);
                } else {
                    reViewData["title"] = title;
                    reViewData["desc1"] = des1;
                    reViewData["desc2"] = des1;
                    reViewData["pcUrl"] = url;
                    reViewData["pcsUrl"] = url;
                    $("#_editor [name='title']").val(title);
                    $("#_editor [name='desc1']").val(des1);
                    $("#_editor [name='desc2']").val("");
                    $("#_editor [name='pcUrl']").val(url);
                    $("#_editor [name='pcsUrl']").val(url);
                    initEditView(res);
                    reShowEditor();
                }
            }else{
                alert("该创意是闪投创意，暂不支持！");
            } //end EC_pap_big
        }else{
            alert("该创意是闪投创意，暂不支持！");
        } //end EC_palist
    }else{
        alert("该创意是闪投创意，暂不支持！");
    }//end tbody

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
            var _thisSttmpr = $(o).prev("input[type=text]").val();
            var char = _thisSttmpr.match(/[^\x00-\xff]/ig);
            _thisStr = _thisSttmpr.length + (char == null ? 0 : char.length);
            if (parseInt(_thisStr) > parseInt(_max) || parseInt(_thisStr) == 0) {
                $(o).removeClass().addClass("span-error").css("color", "red");
            } else {
                $(o).removeClass().addClass("span-ok").css("color", "#b4b4b4");
            }
            $(o).html(_thisStr + "/" + _max);
        } else {
            var _thisSttmpr = $(o).prev("textarea").val();
            var char = _thisSttmpr.match(/[^\x00-\xff]/ig);
            _thisStr = _thisSttmpr.length + (char == null ? 0 : char.length);
            if (parseInt(_thisStr) > parseInt(_max) || parseInt(_thisStr) == 0) {
                $(o).removeClass().addClass("span-error").css("color", "red");
            } else {
                $(o).removeClass().addClass("span-ok").css("color", "#b4b4b4");
            }
            $(o).html(_thisStr + "/" + _max);
        }
    });
    var _text=$("#_editor :text");
    _text.each(function(i,o){
        if(i==0){
            var _max = $(o).next("span").html().split("/")[1];
            var char = $(o).val().match(/[^\x00-\xff]/ig);
            var  _thisStr = $(o).val().length + (char == null ? 0 : char.length);
            if (parseInt(_thisStr) > parseInt(_max) || parseInt(_thisStr) <= 0) {
                $("#titleMsg").html("<b style='color:red'>标题长度必须大于5个字符小于50个字符！</b>");
            } else {
                $("#titleMsg").html("");
            }
        }if(i==1){
            var dm = "." + $("#dm").val();
            if ($(this).val().indexOf(dm) == -1) {
                $("#pcsUrlMsg").html("<b style='color: red;'>\"显示\"Url地址中必须包含\"" + dm + "\"的域名后缀!！</b>");
            }
            else{
                $("#pcsUrlMsg").html("");
            }
            //下面注释是判断结尾是否以注册的域名结尾(已经不需要，百度官网也没有做这样验证，只验证了是否包含主域名)
            //else {
            //    if ($(this).val().substr($(this).val().indexOf(dm)) != dm) {
            //        $("#pcsUrlMsg").html("<b style='color: red;'>\"显示\"Url地址必须以\"" + dm + "\"结尾！</b>");
            //    } else {
            //        $("#pcsUrlMsg").html("");
            //    }
            //}
        }
    });
    var _textarea=$("#_editor textarea");
    _textarea.each(function(i,o){
        if(i==0){
            var _max = $(o).next("span").html().split("/")[1];
            var char = $(o).val().match(/[^\x00-\xff]/ig);
            var  _thisStr = $(o).val().length + (char == null ? 0 : char.length);
            if (parseInt(_thisStr) > parseInt(_max) || parseInt(_thisStr) <= 8) {
                $("#desc1Msg").html("<b style='color:red'>创意\"描述1\"长度必须大于8个字符小于80个字符！</b>");
            } else {
                $("#desc1Msg").html("");
            }
        }if(i==1){
            var _max = $(o).next("span").html().split("/")[1];
            var char = $(o).val().match(/[^\x00-\xff]/ig);
            var  _thisStr = $(o).val().length + (char == null ? 0 : char.length);
            if (parseInt(_thisStr) > parseInt(_max) || parseInt(_thisStr) <= 8) {
                $("#desc2Msg").html("<b style='color:red'>创意\"描述2\"长度必须大于8个字符小于80个字符！</b>");
            } else {
                $("#desc2Msg").html("");
            }
        }if(i==2){
            var dm = "." + $("#dm").val();
            if ($(this).val().indexOf(dm) == -1) {
                $("#pcUrlMsg").html("<b style='color: red;'>\"访问\"Url地址中必须包含\"" + dm + "\"的域名后缀!！</b>");
            }
            else {
                $("#pcUrlMsg").html("");
            }
            //下面注释是判断结尾是否以注册的域名结尾(已经不需要，百度官网也没有做这样验证，只验证了是否包含主域名)
            //else {
            //    if ($(this).val().substr($(this).val().indexOf(dm)) != dm) {
            //        $("#pcUrlMsg").html("<b style='color: red;'>\"访问\"Url地址必须以\"" + dm + "\"结尾！</b>");
            //    } else {
            //        $("#pcUrlMsg").html("");
            //    }
            //}
        }
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
    var _reView = "<a href='javascript:void(0)' class='EC_t EC_BL' id='left1Title'>" + title + "</a><br><span  class='ec_desc ec_font_small' id='left1Desc'>" + desc + "</span><span class='ec_url' id='left1Url'>" + url + "</span>";
    var _reView2 = "<a href='javascript:void(0)' class='EC_t EC_BL' id='left2Title'>" + title + "</a><font color='#080' id='left2Url'>" + url + "</font><br><span  class='ec_desc ec_font_small' id='left2Desc'>" + desc + "</span><br>";
    var _reView3 = "<a href='javascript:void(0)' class='EC_t EC_BL' id='rightTitle'>" + title + "</a><br><span  class='ec_desc ec_font_small' id='rightDesc'>" + desc + "</span><span class='ec_url' id='rightUrl'>" + url + "</span>";
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
function subShowEditor(_liObj) {
    $("#sub").empty();
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    $("#subLinkType").css({
        left: ($("body").width() - $("#subLinkType").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#subLinkType").height()) / 2 + ($(window).scrollTop() - 53) + "px",
        display: "block"
    });
    var _ul = _liObj.find("div:eq(3) li");
    _ul.each(function (i, o) {
        var _a = $(o).find("a");
        _a.each(function (j, k) {
            if ($(k).html() != "更多》") {
                var _input = "<li>描述：<input type='text' name='subtitle' value='" + $(k).html() + "'/>访问地址：<input type='text' name='subUrl'/><a href='javascript:void(0)' onclick='removeLi(this)'>删除</a></li>";
                $("#sub").append(_input);
            }
        });
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

    var dm = "." + $("#dm").val();
    var pcUrl = $("textarea[name='pcUrl']").val();
    var pcsUrl = $("input[name='pcsUrl']").val();
    if ($("#_editor span:eq(0)").attr("class") == "span-error") {
        alert("\"标题\"字符不符合规范，请重新输入！");
        return false;
    }
    if ($("#_editor span:eq(1)").attr("class") == "span-error") {
        alert("\"描述1\"字符不符合规范，请重新输入！");
        return false;
    }
    if ($("#_editor span:eq(2)").attr("class") == "span-error") {
        alert("\"描述2\"字符不符合规范，请重新输入！");
        return false;
    }
    if ($("#_editor span:eq(3)").attr("class") == "span-error") {
        alert("\"访问\"Url字符不符合规范，请重新输入！");
        return false;
    }
    if ($("#_editor span:eq(4)").attr("class") == "span-error") {
        alert("\"显示\"Url字符不符合规范，请重新输入！");
        return false;
    }
    if ($("#_editor textarea[name='pcUrl']").val().indexOf(dm) == -1) {
        $("#pcUrlMsg").html("<b style='color: red;'>\"访问\"Url地址中必须包含\"" + dm + "\"的域名后缀!！</b>");
        alert("\"访问\"Url地址中必须包含\"" + dm + "\"的域名后缀!");
        return false;
    }
    //下面注释是判断结尾是否以注册的域名结尾(已经不需要，百度官网也没有做这样验证，只验证了是否包含主域名)
    else {
        var _pcSize = $("#_editor textarea[name='pcUrl']").val();
        if ($("#_editor textarea[name='pcUrl']").val().substr($("#_editor textarea[name='pcUrl']").val().indexOf(dm)) != dm) {
            $("#pcUrlMsg").html("<b style='color: red;'>\"访问\"Url地址必须以\"" + dm + "\"结尾！</b>");
            alert("\"访问\"Url地址必须以\"" + dm + "\"结尾！");
            return false;
        }
    }
    if ($("#_editor input[name='pcsUrl']").val().indexOf(dm) == -1) {
        $("#pcsUrlMsg").html("<b style='color: red;'>\"显示\"Url地址中必须包含\"" + dm + "\"的域名后缀!！</b>");
        alert("\"显示\"Url地址中必须包含\"" + dm + "\"的域名后缀!");
        return false;
    }
    //下面注释是判断结尾是否以注册的域名结尾(已经不需要，百度官网也没有做这样验证，只验证了是否包含主域名)
    else {
        var _pcSize = $("#_editor input[name='pcsUrl']").val();
        if ($("#_editor input[name='pcsUrl']").val().substr($("#_editor input[name='pcsUrl']").val().indexOf(dm)) != dm) {
            $("#pcsUrlMsg").html("<b style='color: red;'>\"显示\"Url地址必须以\"" + dm + "\"结尾！</b>");
            alert("\"显示\"Url地址必须以\"" + dm + "\"结尾！");
            return false;
        }
    }


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
    doMain = "";
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
    $("#rUrl").html(reViewData["pcsUrl"]);
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
                $("#dm").val("");
                $("#reOkView").hide();
            } else {
                alert("创意添加失败，请检查后提交！");
            }
        });
    } else {
        alert("请选择计划或者单元！");
    }
}
function textSizeValid() {

    $("#_editor textarea").keyup(function () {
        var _max = $(this).next("span").html().split("/")[1];
        var _thisStr = $(this).val().length;
        if (8 > parseInt(_thisStr) > parseInt(_max)) {
            $(this).next("span").removeClass().addClass("span-error").css("color","red");
        } else {
            $(this).next("span").removeClass().addClass("span-ok").css("color", "#b4b4b4");
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
            var _input = "<li>描述：<input type='text' name='subtitle'/>访问地址：<input type='text' name='subUrl'/><a href='javascript:void(0)' onclick='removeLi(this)'>删除</a></li>";
            $("#sub").append(_input);
        }
    } else {
        alert("一次最多添加10条！");
    }
}
function removeLi(rs) {
    $(rs).parents("li").remove();
}
function onKeyDownView() {
    var strRegex = "^((https|http|ftp|rtsp|mms)://)?[a-z0-9A-Z]{3}\.[a-z0-9A-Z][a-z0-9A-Z]{0,61}?[a-z0-9A-Z]\.com|net|cn|cc (:s[0-9]{1-4})?/$";
    var re = new RegExp(strRegex);
    $("input[name='title']").keyup(function () {
        var regxl = new RegExp("{", "g");
        var regxr = new RegExp("}", "g");
        var title = $(this).val().replace(regxl, "<font color=\"#CC0000\">");
        title = title.replace(regxr, "</font>");
        $("#left1Title").html(title);
        $("#left2Title").html(title);
        $("#rightTitle").html(title);
        var _max = $(this).next("span").html().split("/")[1];
        var _thisSttmpr = $(this).next("span").prev("input[type=text]").val();
        var char = _thisSttmpr.match(/[^\x00-\xff]/ig);
        var _thisStr = _thisSttmpr.length + (char == null ? 0 : char.length);
        if (parseInt(_thisStr) > parseInt(_max) || parseInt(_thisStr) <= 8) {
            $(this).next("span").removeClass().addClass("span-error").css("color","red");
            $("#titleMsg").html("<b style='color:red'>标题长度必须大于8个字符小于50个字符！</b>");
        } else {
            $(this).next("span").removeClass().addClass("span-ok").css("color", "#b4b4b4");
            $("#titleMsg").html("");
        }
        $(this).next("span").html(_thisStr + "/" + _max);
    });
    $("textarea[name='desc1']").keyup(function () {
        var desc2 = $("textarea[name='desc2']").val();
        var desc = $(this).val();
        var regxl = new RegExp("{", "g");
        var regxr = new RegExp("}", "g");
        var descTotal = (desc + desc2).replace(regxl, "<font color=\"#CC0000\">");
        descTotal = descTotal.replace(regxr, "</font>");
        $("#left1Desc").html(descTotal);
        $("#left2Desc").html(descTotal);
        $("#rightDesc").html(descTotal);
        var _max = $(this).next("span").html().split("/")[1];
        var _thisSttmpr = $(this).next("span").prev("textarea").val();
        var char = _thisSttmpr.match(/[^\x00-\xff]/ig);
        var _thisStr = _thisSttmpr.length + (char == null ? 0 : char.length);
        if (parseInt(_thisStr) > parseInt(_max) || parseInt(_thisStr) <= 8) {
            $(this).next("span").removeClass().addClass("span-error").css("color","red");
            $("#desc1Msg").html("<b style='color:red'>创意\"描述1\"长度必须大于8个字符小于80个字符！</b>");
        } else {
            $(this).next("span").removeClass().addClass("span-ok").css("color", "#b4b4b4");
            $("#desc1Msg").html("");
        }
        $(this).next("span").html(_thisStr + "/" + _max);
    });
    $("textarea[name='desc2']").keyup(function () {
        var desc1 = $("textarea[name='desc1']").val();
        var desc = $(this).val();
        var regxl = new RegExp("{", "g");
        var regxr = new RegExp("}", "g");
        var descTotal = (desc1 + desc).replace(regxl, "<font color=\"#CC0000\">");
        descTotal = descTotal.replace(regxr, "</font>");
        $("#left1Desc").html(descTotal);
        $("#left2Desc").html(descTotal);
        $("#rightDesc").html(descTotal);
        var _max = $(this).next("span").html().split("/")[1];
        var _thisSttmpr = $(this).next("span").prev("textarea").val();
        var char = _thisSttmpr.match(/[^\x00-\xff]/ig);
        var _thisStr = _thisSttmpr.length + (char == null ? 0 : char.length);
        if (parseInt(_thisStr) > parseInt(_max) || parseInt(_thisStr) <= 8) {
            $(this).next("span").removeClass().addClass("span-error").css("color","red");
            $("#desc2Msg").html("<b style='color:red'>创意\"描述2\"长度必须大于8个字符小于80个字符！</b>");
        } else {
            $(this).next("span").removeClass().addClass("span-ok").css("color", "#b4b4b4");
            $("#desc2Msg").html("");
        }
        $(this).next("span").html(_thisStr + "/" + _max);
    });
    $("textarea[name='pcUrl']").keyup(function () {
        var title = $(this).val();
        var _max = $(this).next("span").html().split("/")[1];
        var _thisSttmpr = $(this).next("span").prev("textarea").val();
        var char = _thisSttmpr.match(/[^\x00-\xff]/ig);
        var _thisStr = _thisSttmpr.length + (char == null ? 0 : char.length);
        if (parseInt(_thisStr) > parseInt(_max) || parseInt(_thisStr) <= 5) {
            $(this).next("span").removeClass().addClass("span-error").css("color","red");
        } else {
            $(this).next("span").removeClass().addClass("span-ok").css("color", "#b4b4b4");
            var dm = "." + $("#dm").val();
            if ($(this).val().indexOf(dm) == -1) {
                $("#pcUrlMsg").html("<b style='color: red;'>\"访问\"Url地址中必须包含\"" + dm + "\"的域名后缀!！</b>");
            }
            //else{
            //    $("#pcUrlMsg").html("");
            //}
            //下面注释是判断结尾是否以注册的域名结尾(已经不需要，百度官网也没有做这样验证，只验证了是否包含主域名)
            else {
                if ($(this).val().substr($(this).val().indexOf(dm)) != dm) {
                    $("#pcUrlMsg").html("<b style='color: red;'>\"访问\"Url地址必须以\"" + dm + "\"结尾！</b>");
                } else {
                    $("#pcUrlMsg").html("");
                }
            }
        }
        $(this).next("span").html(_thisStr + "/" + _max);
    });
    $("input[name='pcsUrl']").keyup(function () {
        var title = $(this).val();
        $("#left1Url").html(title);
        $("#left2Url").html(title);
        $("#rightUrl").html(title);
        var _max = $(this).next("span").html().split("/")[1];
        var _thisSttmpr = $(this).next("span").prev("input[type=text]").val();
        var char = _thisSttmpr.match(/[^\x00-\xff]/ig);
        var _thisStr = _thisSttmpr.length + (char == null ? 0 : char.length);
        if (parseInt(_thisStr) > parseInt(_max) || parseInt(_thisStr) <= 5) {
            $(this).next("span").removeClass().addClass("span-error").css("color","red");
        } else {
            $(this).next("span").removeClass().addClass("span-ok").css("color", "#b4b4b4");
            var dm = "." + $("#dm").val();
            if ($(this).val().indexOf(dm) == -1) {
                $("#pcsUrlMsg").html("<b style='color: red;'>\"显示\"Url地址中必须包含\"" + dm + "\"的域名后缀!！</b>");
            }
            //else{
            //    $("#pcsUrlMsg").html("");
            //}
            //下面注释是判断结尾是否以注册的域名结尾(已经不需要，百度官网也没有做这样验证，只验证了是否包含主域名)
            else {
                if ($(this).val().substr($(this).val().indexOf(dm)) != dm) {
                    $("#pcsUrlMsg").html("<b style='color: red;'>\"显示\"Url地址必须以\"" + dm + "\"结尾！</b>");
                } else {
                    $("#pcsUrlMsg").html("");
                }
            }
        }
        $(this).next("span").html(_thisStr + "/" + _max);
    });
}
function callBackEditor() {
    reShowEditor();
}

function addTongPei() {
    var s = $("#title").selectionTp();
    var _val = $("#title");
    _val.val(_val.val().replace(s.text, "{" + s.text + "}"));
    var regxl = new RegExp("{", "g");
    var regxr = new RegExp("}", "g");
    var title = _val.val().replace(regxl, "<font color=\"#CC0000\">");
    title = title.replace(regxr, "</font>");
    $("#left1Title").html(title);
    $("#left2Title").html(title);
    $("#rightTitle").html(title);
}
function addTongPeiDe1() {
    var s = $("#desc1").selectionTp();
    var _val = $("#desc1");
    _val.val(_val.val().replace(s.text, "{" + s.text + "}"));
    var desc2 = $("textarea[name='desc2']").val();
    var desc = _val.val();
    var regxl = new RegExp("{", "g");
    var regxr = new RegExp("}", "g");
    var descTotal = (desc + desc2).replace(regxl, "<font color=\"#CC0000\">");
    descTotal = descTotal.replace(regxr, "</font>");
    $("#left1Desc").html(descTotal);
    $("#left2Desc").html(descTotal);
    $("#rightDesc").html(descTotal);
}
function addTongPeiDe2() {
    var s = $("#desc2").selectionTp();
    var _val = $("#desc2");
    _val.val(_val.val().replace(s.text, "{" + s.text + "}"));
    var desc1 = $("textarea[name='desc1']").val();
    var desc = _val.val();
    var regxl = new RegExp("{", "g");
    var regxr = new RegExp("}", "g");
    var descTotal = (desc1 + desc).replace(regxl, "<font color=\"#CC0000\">");
    descTotal = descTotal.replace(regxr, "</font>");
    $("#left1Desc").html(descTotal);
    $("#left2Desc").html(descTotal);
    $("#rightDesc").html(descTotal);
}