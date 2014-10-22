/**
 * Created by XiaoWei on 2014/8/21.
 */
/**
 * 树加载数据需要的计划，单元参数，默认都为空
 * @type {{aid: null, cid: null}}
 */
$.fn.selectionTp= function () {
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
var sparams = {aid: null, cid: null,nowPage:0,pageSize:20};
$(function () {
    InitMenu();
    rDrag.init(document.getElementById("dAdd"));
    rDrag.init(document.getElementById("dUpdate"));
    initsDivKeyup();
    initUpdateInputKeyUp();
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
function getChar(str) {
    var char = str.match(/[^\x00-\xff]/ig);
    return  str.length + (char == null ? 0 : char.length);
}
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
            var strRegex = "^((https|http|ftp|rtsp|mms)://)?[a-z0-9A-Z]{3}\.[a-z0-9A-Z][a-z0-9A-Z]{0,61}?[a-z0-9A-Z]\.com|net|cn|cc (:s[0-9]{1-4})?/$";
            var re = new RegExp(strRegex);
            var _title = $(this).parents("tr").find("input:eq(2)");
            var _thisStr = getChar(_title.val());
            var dm = "." + $("#doMain").val();
            if (parseInt(_thisStr) > 50 || parseInt(_thisStr) <= 8) {
                alert("\"标题\"长度应大于8个字符小于50个字符，汉子占两个字符!");
                return false;
            }
            var _desc1 = $(this).parents("tr").find("input:eq(3)");
            var _thisStrDesc1 = getChar(_desc1.val());
            if (parseInt(_thisStrDesc1) > 80 || parseInt(_thisStrDesc1) <= 8) {
                alert("\"创意描述1\"长度应大于8个字符小于80个字符，汉子占两个字符!");
                return false;
            }
            var _desc2 = $(this).parents("tr").find("input:eq(4)");
            var _thisStrDesc2 = getChar(_desc2.val());
            if (parseInt(_thisStrDesc2) > 80 || parseInt(_thisStrDesc2) <= 8) {
                alert("\"创意描述2\"长度应大于8个字符小于80个字符，汉子占两个字符!");
                return false;
            }
            var _pc = $(this).parents("tr").find("input:eq(5)");
            var _thisStrpc = getChar(_pc.val());
            if (parseInt(_thisStrpc) > 1024 || parseInt(_thisStrpc) <= 1) {
                alert("默认\"访问\"Url地址长度应大于2个字符小于1024个字符，汉子占两个字符!");
                return false;
            } else {
//                if (!re.test(_pc.val())) {
//                    alert("默认\"访问\"Url地址格式不正确，请重新输入");
//                    _pc.val("");
//                    return false;
//                }
                if (_pc.val().indexOf(dm) == -1) {
                    alert("默认\"访问\"Url地址必须包含以\"" + dm + "\"的域名！");
                    return false;
                } else {
                    var _pcSize = _pc.val();
                    if (_pc.val().substr(_pc.val().indexOf(dm)) != dm) {
                        alert("默认\"访问\"Url地址必须以\"" + dm + "\"结尾！");
                        return false;
                    }
                }
            }
            var _pcs = $(this).parents("tr").find("input:eq(6)");
            var _thisStrpcs = getChar(_pcs.val());
            if (parseInt(_thisStrpcs) > 50 || parseInt(_thisStrpcs) <= 1) {
                alert("默认\"显示\"Url地址长度应大于2个字符小于50个字符，汉子占两个字符!");
                return false;
            } else {
//                if (!re.test(_pcs.val())) {
//                    alert("默认\"显示\"Url地址格式不正确，请重新输入");
//                    _pcs.val("");
//                    return false;
//                }
                if (_pcs.val().indexOf(dm) == -1) {
                    alert("默认\"显示\"Url地址必须包含以\"" + dm + "\"的域名！");
                    return false;
                } else {
                    var _pcsSize = _pcs.val();
                    if (_pcs.val().substr(_pcs.val().indexOf(dm)) != dm) {
                        alert("默认\"显示\"Url地址必须以\"" + dm + "\"结尾！");
                        return false;
                    }
                }
            }
            var _mib=$(this).parents("tr").find("input:eq(7)");
            if (_mib.val() != "空"||_mib.val() != "") {
                if (_mib.val().indexOf(dm) == -1) {
                    alert("移动\"访问\"Url地址必须包含以\"" + dm + "\"的域名！");
                    return false;
                } else {
                    if (_mib.val().substr(_mib.val().indexOf(dm)) != dm) {
                        alert("移动\"访问\"Url地址必须以\"" + dm + "\"结尾！");
                        return false;
                    }
                }
            }
            var _mibs=$(this).parents("tr").find("input:eq(8)");
            if (_mibs.val() != ""||_mibs.val() != "空") {
                if (_mibs.val().indexOf(dm) == -1) {
                    alert("移动\"显示\"Url地址必须包含以\"" + dm + "\"的域名！");
                    return false;
                } else {
                    if (_mibs.val().substr(_mibs.val().indexOf(dm)) != dm) {
                        alert("移动\"显示\"Url地址必须以\"" + dm + "\"结尾！");
                        return false;
                    }
                }
            }
            var con = confirm("你确定要添加么？");
            if (con) {
                var _selects = $(this).parents("tr").find("select");
                var _input = $(this).parents("tr").find("input");
                var _tr = $(this).parents("tr");
                $(this).submit("../assistantCreative/add", function (rs) {
                    var json = eval("(" + rs + ")");
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
                            " <td ><span class='pen'></span></td>" +
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
function loadCreativeData(page_index) {
    initRbackBtn();
    var _createTable = $("#createTable tbody");
    _createTable.empty().html("加载中...");
    sparams.nowPage=page_index;
    sparams.pageSize=items_per_page;
    pageType=3;
    $.post("/assistantCreative/getList", sparams, function (result) {
        var gson = $.parseJSON(result);

        if (gson.list != undefined) {
                var json = gson.list;
                pagerInit(gson);
                _createTable.empty();
                var _trClass = "";
                for (var i = 0; i < json.length; i++) {
                    var _id = json[i].creativeId != null ? json[i].creativeId : json[i].id;
                    var _edit = json[i].localStatus != null ? json[i].localStatus : -1;
                    var ls = getLocalStatus(parseInt(_edit));
                    _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
                    var _tbody = "<tr class=" + _trClass + " onclick='on(this);''>" +
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
                        " <td >" + ls + "</td>" +
                        "</tr>";
                    _createTable.append(_tbody);
                }
            }else {
                _createTable.empty();
                _createTable.append("<tr><td>暂无数据</td></tr>");
            }

    });
}
/**
 * 初始化分页控件
 */
function pagerInit(data) {
    if(data.totalCount==0){
        return false;
    }
    $("#creativePager").pagination(data.totalCount, getOptionsFromForm(data.pageNo));
}
function skipCreativePage(){
    var pageNo = $("#creativePageNum").val();
    loadCreativeData(/^\d+$/.test(pageNo) == false?0:parseInt(pageNo)-1);
}

/**
 * 鼠标单击显示详细信息
 * @param obj
 */
function on(obj) {
    var _this = $(obj);
    tmp = _this;
    var _edit = _this.find("td:eq(10)").html()
    if (_edit != "") {
        onRbackBtn();
    } else {
        initRbackBtn();
    }
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

    $("#sTitle").val(title);
    initKeyUp($("#sTitle"));

    $("#sDes1").val(de1);
    initKeyUp($("#sDes1"));

    $("#sDes2").val(de2);
    initKeyUp($("#sDes2"));

    $("#sPc").val(pc);
    initKeyUp($("#sPc"), 1);

    $("#sPcs").val(pcs);
    initKeyUp($("#sPcs"), 1);

    $("#sMib").val(mib);
    initKeyUp($("#sMib"), -1);

    $("#sMibs").val(mibs);
    initKeyUp($("#sMibs"), -1);

    $("#sPause").html(pause);
    $("#sStatus").html(status);

}
function initKeyUp(res, gtval) {
    gtval = gtval != undefined ? gtval : 8;
    var _max = $(res).next("span").text().split("/")[1];
    var _thisSttmpr = $(res).val();
    var char = _thisSttmpr.match(/[^\x00-\xff]/ig);
    var _thisStr = _thisSttmpr.length + (char == null ? 0 : char.length);
    if (parseInt(_thisStr) > parseInt(_max) || parseInt(_thisStr) <= gtval) {
        $(res).next("span").css("color", "red");
    } else {
        $(res).next("span").css("color", "#b4b4b4");
    }
    $(res).next("span").text(_thisStr + "/" + _max);
}

function initsDivKeyup(){
    var _input = $("#sDiv input");
    _input.each(function (i, o) {
        if (i < 3) {
            $(o).keyup(function () {
                initKeyUp($(o));
            });
        }
        if (i >= 3 && i < 5) {
            $(o).keyup(function () {
                initKeyUp($(o), 1);
            });
        }
        if (i >= 5) {
            $(o).keyup(function () {
                initKeyUp($(o), -1);
            });
        }
    });
}
/**
 * 未知方法，待继续编码
 * @param rs
 */
function addTb(rs) {
    var s=$("#cUpdateForm input[name='title']").selectionTp();
    var _val=$("#cUpdateForm input[name='title']");
    _val.val(_val.val().replace(s.text,"{"+ s.text+"}"));
}
function addTbDes1(){
    var s=$("#cUpdateForm input[name='description1']").selectionTp();
    var _val=$("#cUpdateForm input[name='description1']");
    _val.val(_val.val().replace(s.text,"{"+ s.text+"}"));
}
function addTbDes2(){
    var s=$("#cUpdateForm input[name='description2']").selectionTp();
    var _val=$("#cUpdateForm input[name='description2']");
    _val.val(_val.val().replace(s.text,"{"+ s.text+"}"));
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
            " <td><input name='pcDestinationUrl' onkeyup='onKey(this);' style='width:140px;'  maxlength='1024'></td>" +
            " <td><input name='pcDisplayUrl' onkeyup='onKey(this);' style='width:140px;'  maxlength='36'></td>" +
            " <td><input name='mobileDestinationUrl' onkeyup='onKey(this);' style='width:140px;' maxlength='1024'></td>" +
            " <td><input name='mobileDisplayUrl' onkeyup='onKey(this);' style='width:140px;' maxlength='36'></td>" +
            " <td><select name='pause'><option value='true'>启用</option><option value='false'>暂停</option></select></td>" +
            " <td><span>本地新增</span><input type='hidden' value='-1' name='status'></td>" +
            " <td><span class='pen'></span></td>" +
            "</tr>";
        _createTable.append(_tbody);
    } else if (sparams.cid != null && sparams.aid == null) {
        jcBox.empty();
        loadUnit(sparams.cid);
        jcBox.append("<li><span>推广单元</span><select id='sUnit' onchange='loadTree(this.value)'><option value='-1'>请选择单元</option></select></li>");
        creativeAddBoxShow();
    } else if (sparams.cid == null && sparams.aid == null) {
        jcBox.empty();
        getPlans();
        jcBox.append("<li><span>推广计划</span><select id='sPlan' onchange='loadUnit(this.value)'><option value='-1'>请选择计划</option></select></li>");
        jcBox.append("<li><span>推广单元</span><select id='sUnit' onchange='loadTree(this.value)'><option value='-1'>请选择单元</option></select></li>");
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
            initKeyUp($("#sTitle"));
            break;
        case "description1":
            $("#sDes1").val(val);
            initKeyUp($("#sDes1"));
            break;
        case "description2":
            $("#sDes2").val(val);
            initKeyUp($("#sDes2"));
            break;
        case "pcDestinationUrl":
            $("#sPc").val(val);
            initKeyUp($("#sPc"), 1);
            break;
        case "pcDisplayUrl":
            $("#sPcs").val(val);
            initKeyUp($("#sPcs"), 1);
            break;
        case "mobileDisplayUrl":
            $("#sMibs").val(val);
            initKeyUp($("#sMibs"), -1);
            break;
        case "mobileDestinationUrl":
            $("#sMib").val(val);
            initKeyUp($("#sMib"), -1);
            break;
    }

}
function initUpdateInputKeyUp() {
    var _this = $("#cUpdateForm :text");
    _this.each(function (i, o) {
        if (i < 3) {
            $(o).keyup(function () {
                initUpdateKeyupChar($(o));
            });
        }
        if (i >= 3 && i < 5) {
            $(o).keyup(function () {
                initUpdateKeyupChar($(o), 1);
            });
        }
        if (i >= 5) {
            $(o).keyup(function () {
                initUpdateKeyupChar($(o), -1);
            });
        }
    })
}
/**
 * 初始化工具栏
 */
function toolBarInit() {
    $("#sDiv input[type='text']").val("");
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
    title = title.replace("{", "<span class='red-color'>").replace("}", "</span>").replace("{", "<span class='red-color'>").replace("}", "</span>");
    de1 = de1.replace("{", "<span class='red-color'>").replace("}", "</span>");
    de2 = de2.replace("{", "<span class='red-color'>").replace("}", "</span>");
    var h3 = "<a href='" + pc + "' target='_blank'><h3>" + title + "</h3></a>" +
        "<span class='black-color'>" + de1 + "</span></br>" +
        "<span class='black-color'>" + de2 + "<span></br>" +
        "<span  class='green-color'>" + pc + "<span></br>";
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
    sparams.cid = cid;
    sparams.aid = null;
    loadCreativeData(0);
}
/**
 * 动态更新创意中的数据，如果点击单元树
 * @param con
 */
function getCreativeUnit(con) {
    sparams.cid = con.cid;
    sparams.aid = con.aid;
    loadCreativeData(0);
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
        var dm = $("#doMain").val();
        if (dm == "") {
            $.get("/assistantCreative/getDomain", function (result) {
                if (result != "0") {
                    $("#doMain").val(result);
                }
            });
        }
        sparams.cid = cid;
        sparams.aid = aid;
        closeAlertCreative();
        addCreative();
    }


}
/**
 * 关闭弹窗
 */
function closeAlertCreative() {
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
/**
 * 弹出框选择计划或者单元树对应加载
 * @param rs
 */
function loadTree(rs) {
    if (rs != "-1") {
        var cid = $("#sPlan :selected").val() == undefined ? sparams.cid : $("#sPlan :selected").val();
        sparams = {cid: cid, aid: rs};
        loadCreativeData(0);
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
                $(tmp).find("td:eq(10)").html("<span class='error' step='3'></span>");
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
    var dm = $("#doMain").val();
    if (dm == "") {
        $.get("/assistantCreative/getDomain", function (result) {
            if (result != "0") {
                $("#doMain").val(result);
            }
        });
    }
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
    var titleObj = $("#cUpdateForm input[name='title']");
    var des1Obj = $("#cUpdateForm input[name='description1']");
    var des2Obj = $("#cUpdateForm input[name='description2']");
    var pcUrlObj = $("#cUpdateForm input[name='pcDestinationUrl']");
    var pcsUrlObj = $("#cUpdateForm input[name='pcDisplayUrl']");
    var mibUrlObj = $("#cUpdateForm input[name='mobileDestinationUrl']");
    var mibsUrlObj = $("#cUpdateForm input[name='mobileDisplayUrl']");
    initUpdateKeyupChar(titleObj);
    initUpdateKeyupChar(des1Obj);
    initUpdateKeyupChar(des2Obj);
    initUpdateKeyupChar(pcUrlObj, 1);
    initUpdateKeyupChar(pcsUrlObj, 1);
    initUpdateKeyupChar(mibUrlObj, -1);
    initUpdateKeyupChar(mibsUrlObj, -1);
}
function initUpdateKeyupChar(res, gtval) {
    gtval = gtval != undefined ? gtval : 8;
    var _max = $(res).prev("label").find("span").text().split("/")[1];
    var val = $(res).val();
    var _thisStr = getChar(val);
    if (parseInt(_thisStr) > parseInt(_max) || parseInt(_thisStr) <= gtval) {
        $(res).prev("label").find("span").css("color", "red");
    } else {
        $(res).prev("label").find("span").css("color", "#b4b4b4");
    }
    $(res).prev("label").find("span").text(_thisStr + "/" + _max);
}
/**
 * 修改确认提交方法
 */
function updateOk() {
    var strRegex = "^((https|http|ftp|rtsp|mms)://)?[a-z0-9A-Z]{3}\.[a-z0-9A-Z][a-z0-9A-Z]{0,61}?[a-z0-9A-Z]\.com|net|cn|cc (:s[0-9]{1-4})?/$";
    var re = new RegExp(strRegex);
    var _title = $("#cUpdateForm input[name='title']");
    var _desc1 = $("#cUpdateForm input[name='description1']");
    var _desc2 = $("#cUpdateForm input[name='description2']");
    var _pc = $("#cUpdateForm input[name='pcDestinationUrl']");
    var _pcs = $("#cUpdateForm input[name='pcDisplayUrl']");
    var _mib = $("#cUpdateForm input[name='mobileDestinationUrl']");
    var _mibs = $("#cUpdateForm input[name='mobileDisplayUrl']");

    var _thisStr = getChar(_title.val());
    var dm = "." + $("#doMain").val();
    if (parseInt(_thisStr) > 50 || parseInt(_thisStr) <= 8) {
        alert("\"标题\"长度应大于8个字符小于50个字符，汉子占两个字符!");
        return false;
    }
    var _thisStrDesc1 = getChar(_desc1.val());
    if (parseInt(_thisStrDesc1) > 80 || parseInt(_thisStrDesc1) <= 8) {
        alert("\"创意描述1\"长度应大于8个字符小于80个字符，汉子占两个字符!");
        return false;
    }
    var _thisStrDesc2 = getChar(_desc2.val());
    if (parseInt(_thisStrDesc2) > 80 || parseInt(_thisStrDesc2) <= 8) {
        alert("\"创意描述2\"长度应大于8个字符小于80个字符，汉子占两个字符!");
        return false;
    }
    var _thisStrpc = getChar(_pc.val());
    if (parseInt(_thisStrpc) > 1024 || parseInt(_thisStrpc) <= 1) {
        alert("默认\"访问\"Url地址长度应大于2个字符小于1024个字符，汉子占两个字符!");
        return false;
    } else {
//        if (!re.test(_pc.val())) {
//            alert("默认\"访问\"Url地址格式不正确，请重新输入");
//            _pc.val("");
//            return false;
//        }
        if (_pc.val().indexOf(dm) == -1) {
            alert("默认\"访问\"Url地址必须包含以\"" + dm + "\"的域名！");
            return false;
        } else {
            var _pcSize = _pc.val();
            if (_pc.val().substr(_pc.val().indexOf(dm)) != dm) {
                alert("默认\"访问\"Url地址必须以\"" + dm + "\"结尾！");
                return false;
            }
        }
    }
    var _thisStrpcs = getChar(_pcs.val());
    if (parseInt(_thisStrpcs) > 50 || parseInt(_thisStrpcs) <= 1) {
        alert("默认\"显示\"Url地址长度应大于2个字符小于50个字符，汉子占两个字符!");
        return false;
    } else {
//        if (!re.test(_pcs.val())) {
//            alert("默认\"显示\"Url地址格式不正确，请重新输入");
//            _pcs.val("");
//            return false;
//        }
        if (_pcs.val().indexOf(dm) == -1) {
            alert("默认\"显示\"Url地址必须包含以\"" + dm + "\"的域名！");
            return false;
        } else {
            if (_pcs.val().substr(_pcs.val().indexOf(dm)) != dm) {
                alert("默认\"显示\"Url地址必须以\"" + dm + "\"结尾！");
                return false;
            }
        }
    }
    if (_mib.val() != "空"||_mib.val() != "") {
        if (_mib.val().indexOf(dm) == -1) {
            alert("移动\"访问\"Url地址必须包含以\"" + dm + "\"的域名！");
            return false;
        } else {
            if (_mib.val().substr(_mib.val().indexOf(dm)) != dm) {
                alert("移动\"访问\"Url地址必须以\"" + dm + "\"结尾！");
                return false;
            }
        }
    }
    if (_mibs.val() != ""||_mibs.val() != "空") {
        if (_mibs.val().indexOf(dm) == -1) {
            alert("移动\"显示\"Url地址必须包含以\"" + dm + "\"的域名！");
            return false;
        } else {
            if (_mibs.val().substr(_mibs.val().indexOf(dm)) != dm) {
                alert("移动\"显示\"Url地址必须以\"" + dm + "\"结尾！");
                return false;
            }
        }
    }
    var con = confirm("你确定要修改该创意吗？");
    if (con) {
    var _this = $(tmp);
    $("#cUpdateForm").formSubmit("/assistantCreative/update", function (rs) {
        if (rs == "1") {
            var p = formData["pause"] == "true" ? "启用" : "暂停";
            var _createTable = $("#createTable tbody");
            var i = $("#createTable tbody tr").size();
            var _trClass = i % 2 == 0 ? "list2_box1 list2_box3" : "list2_box2 list2_box3";
            var _edit = null;
            if (formData["oid"].length > 18) {
                _edit = "<span class='pen' step='1'></span>";
            } else {
                _edit = "<span class='pen' step='2'></span>";
            }
            var _tbody =
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
                " <td >" + _edit + "</td>";
            $(tmp).html(_tbody);
            alert("修改完成");
            closeAlertCreative();

        }
    });
    }
}
/**
 * 获取10为数字的随机数
 * @returns {number}
 */
function getRandomId() {
    return Math.floor(Math.random() * 10000000000) + 1;
}
function initRbackBtn() {
    tmp = null;
    $("#reBak").attr("class", "z_function_hover");
}
function onRbackBtn() {
    $("#reBak").attr("class", "zs_top");
}
function reBakClick() {
    var _this = $(tmp);
    if (_this.html() != undefined) {
        var _edit = _this.find("td:eq(10)").html();
        if (_edit != "") {
            var con = confirm("是否还原选中的数据？");
            if (con) {
                var _localStatus = parseInt(_this.find("td:eq(10) span").attr("step"));
                var _oid = _this.find("td:eq(0) input").val() != undefined ? _this.find("td:eq(0) input").val() : _this.find("td:eq(0) span").html();
                switch (_localStatus) {
                    case 1:
                        deleteByObjectId(tmp);
                        break;
                    case 2:
                        reBack(_oid);
                        break;
                    case 3:
                        delBack(_oid);
                        break;
                    case 4:
                        alert("属于单元级联删除，如果要恢复该数据，则必须恢复单元即可！");
                        break;
                    default :
                        break;
                }
            }
        }
    }
}
function reBack(oid) {
    $.get("../assistantCreative/reBack", {oid: oid}, function (rs) {
        var json = eval("(" + rs + ")");
        if (json.success == "1") {
            var crid = oid.length > 18 ? oid : json.data["creativeId"];
            var p = json.data["pause"] == "true" ? "启用" : "暂停";
            var s = until.getCreativeStatus(parseInt(json.data["status"]));
            var _tbody =
                "<td>&nbsp;<input type='hidden' value='" + crid + "'/></td>" +
                "<td >" + until.substring(10, json.data["title"]) + "</td>" +
                " <td >" + until.substring(10, json.data["description1"]) + "</td>" +
                " <td >" + until.substring(10, json.data["description2"]) + "</td>" +
                " <td ><a href='" + json.data["pcDestinationUrl"] + "' target='_blank'>" + until.substring(10, json.data["pcDestinationUrl"]) + "</a></td>" +
                " <td >" + until.substring(10, json.data["pcDisplayUrl"]) + "</td>" +
                " <td >" + until.substring(10, json.data["mobileDestinationUrl"]) + "</td>" +
                " <td >" + until.substring(10, json.data["mobileDisplayUrl"]) + "</td>" +
                " <td >" + p + "</td>" +
                " <td >" + s + "</td>" +
                " <td ></td>";
            $(tmp).html(_tbody);
        }
    });
}
function delBack(oid) {
    $.get("../assistantCreative/delBack", {oid: oid}, function (rs) {
        if (rs == "1") {
            $(tmp).find("td:eq(10)").html("");
        }
    });
}
function getLocalStatus(number) {
    switch (number) {
        case 1:
            return "<span class='pen' step=" + number + "></span>";
            break;
        case 2:
            return "<span class='pen' step=" + number + "></span>";
            break;
        case 3:
            return "<span class='error' step=" + number + "></span>";
            break;
        case 4:
            return "<span class='error' step=" + number + "></span>";
            break;
        default :
            return "";
            break;
    }
}
function creativeMulti() {
    top.dialog({title: "批量添加创意",
        padding: "5px",
        content: "<iframe src='/assistantCreative/updateMulti' width='900' height='650' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
        oniframeload: function () {

        },
        onclose: function () {
            loadCreativeData({cid: null, aid: null});
        },
        onremove: function () {
        }
    }).showModal();

}







