/**
 * Created by XiaoWei on 2014/8/21.
 */
$(function () {
    loadCreativeData();
    InitMenu();
    $("#createTable").find("td").click(function () {
        alert($(this).html());
    });
});

var add = {
    text: "添加创意",
    func: function () {
        addCreative();
    }
}, del = {
    text: "删除创意",
    func: function (e) {

    }
}, update = {
    text: "验证创意",
    func: function () {
        alert("验证");
    }
}
var menuData = [
    [add, del, update]
];
function InitMenu() {
    $("#createTable").live("tr",function(){
            $(this).smartMenu(menuData, {
                name: "creative",
                beforeShow: function () {
                    var _this=$(this);
                    alert(_this.html());
                    this.smartMenu.remove();
                }
            })
    });
}
function loadCreativeData() {
    $.get("/assistantCreative/getList", function (result) {
        var json = eval("(" + result + ")");
        var _createTable = $("#createTable tbody");
        if (json.length > 0) {
            _createTable.empty();
            var _trClass = "";
            for (var i = 0; i < json.length; i++) {
                _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
                var _tbody = "<tr class=" + _trClass + " onclick='on(this);''>" +
                    "<td ondblclick='edit(this);'>&nbsp;<span></span></td>" +
                    "<td ondblclick='edit(this);'>" + until.substring(10, json[i].title) + "</td>" +
                    " <td ondblclick='edit(this);'>" + until.substring(10, json[i].description1) + "</td>" +
                    " <td ondblclick='edit(this);'>" + until.substring(10, json[i].description2) + "</td>" +
                    " <td ondblclick='edit(this);'><a href='" + json[i].pcDestinationUrl + "' target='_blank'>" + until.substring(10, json[i].pcDestinationUrl) + "</a></td>" +
                    " <td ondblclick='edit(this);'>" + until.substring(10, json[i].pcDisplayUrl) + "</td>" +
                    " <td ondblclick='edit(this);'>" + until.substring(10, json[i].mobileDestinationUrl) + "</td>" +
                    " <td ondblclick='edit(this);'>" + until.substring(10, json[i].mobileDisplayUrl) + "</td>" +
                    " <td ondblclick='edit(this);'>" + until.convert(json[i].pause, "启用:暂停") + "</td>" +
                    " <td ondblclick='edit(this);'>" + until.getCreativeStatus(json[i].status) + "</td>" +
                    "</tr>";
                _createTable.append(_tbody);
            }
        }
    });
}

function trStyle(rs) {
    var _this = $(rs);
    var _tr_size = _this.parents("table").find("tr").size();
    for (var i = 0; i < _tr_size; i++) {
        _this.parents("table").find("tr").attr("style", i % 2 == 0 ? "list2_box1" : "list2_box2");
    }
    _this.css("background", "#FCEFC5");
}
function on(obj) {
    trStyle(obj);
    preview(obj);
    $("#sDiv input[type='text']").val("");
    var _this = $(obj);
    var title = _this.find("td:eq(1) a").attr("title") != undefined ? _this.find("td:eq(1) a").attr("title") : _this.find("td:eq(1) input").val();
    var de1 = _this.find("td:eq(2) a").attr("title") != undefined ? _this.find("td:eq(2) a").attr("title") : _this.find("td:eq(2) input").val();
    var de2 = _this.find("td:eq(3) a").attr("title") != undefined ? _this.find("td:eq(3) a").attr("title") : _this.find("td:eq(3) input").val();
    var pc = _this.find("td:eq(4) a").attr("href") != undefined ? _this.find("td:eq(4) a").attr("href") : _this.find("td:eq(4) input").val();
    var pcs = _this.find("td:eq(5) a").attr("title") != undefined ? _this.find("td:eq(5) a").attr("title") : _this.find("td:eq(5) input").val();
    var mib = _this.find("td:eq(6) span:eq(0)").text() != "" ? _this.find("td:eq(6) span:eq(0)").text() : _this.find("td:eq(6) input").val();
    var mibs = _this.find("td:eq(7) span:eq(0)").text() != "" ? _this.find("td:eq(7) span:eq(0)").text() : _this.find("td:eq(7) input").val();
    $("#sTitle").val(title).keyup(function () {
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
}
function addTb(rs) {
    var _this = $(rs);
    var val = _this.prev("input");
}
function addCreative() {
    var i = $("#createTable tbody tr").size();
    var _createTable = $("#createTable tbody");
    var _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
    var _tbody = "<tr class=" + _trClass + " onclick='on(this);''>" +
        "<td>&nbsp;<span><a href='javascript:void(0)' onclick='removeThe(this);'>删除</a></span></td>" +
        "<td><input name='title' onkeyup='onKey(this);' style='width:140px;' maxlength='50'></td>" +
        " <td><input name='description1' onkeyup='onKey(this);' style='width:140px;'  maxlength='80'></td>" +
        " <td><input name='description2' onkeyup='onKey(this);'  style='width:140px;' maxlength='80'></td>" +
        " <td><input name='pcDestinationUrl' onkeyup='onKey(this);' style='width:40px;'  maxlength='1024'></td>" +
        " <td><input name='pcDisplayUrl' onkeyup='onKey(this);' style='width:40px;'  maxlength='36'></td>" +
        " <td><input name='mobileDestinationUrl' onkeyup='onKey(this);' style='width:40px;' maxlength='1024'></td>" +
        " <td><input name='mobileDisplayUrl' onkeyup='onKey(this);' style='width:40px;' maxlength='36'></td>" +
        " <td><select name='pause'><option value='true'>启用</option><option value='false'>暂停</option></select></td>" +
        " <td><select name='status'>" + getStatus();
    +"</select></td>" +
    "</tr>";
    _createTable.append(_tbody);
}
/**
 *
 * @returns {string}动态获取创意添加选择状态
 */
function getStatus() {
    var op =
        "<option value='51'>有效</option>" +
        "<option value='52'>暂停推广</option>" +
        "<option value='53'>不宜推广</option>" +
        "<option value='54'>待激活</option>" +
        "<option value='55'>待审核</option>" +
        "<option value='56'>部分无效</option>";
    return op;
}
function removeThe(rs) {
    var _this = $(rs);
    _this.parents("tr").remove();
    toolBarInit();
}
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
function toolBarInit() {
    $("#sDiv input[type='text']").val("");
    var span_size = $("#sDiv span").length;
    for (var i = 0; i < span_size; i++) {
        if (i % 2 != 0) {
            $("#sDiv span:eq(" + i + ")").text(0);
        }
    }
}
function preview(obj) {
    var _this = $(obj);
    var previeBody = $("#sPreview");
    previeBody.empty();
    var title = _this.find("td:eq(1) a").attr("title") != undefined ? _this.find("td:eq(1) a").attr("title") : _this.find("td:eq(1) input").val();
    var de1 = _this.find("td:eq(2) a").attr("title") != undefined ? _this.find("td:eq(2) a").attr("title") : _this.find("td:eq(2) input").val();
    var de2 = _this.find("td:eq(3) a").attr("title") != undefined ? _this.find("td:eq(3) a").attr("title") : _this.find("td:eq(3) input").val();
    var pc = _this.find("td:eq(4) a").attr("href") != undefined ? _this.find("td:eq(4) a").attr("href") : _this.find("td:eq(4) input").val();
    var pcs = _this.find("td:eq(5) a").attr("title") != undefined ? _this.find("td:eq(5) a").attr("title") : _this.find("td:eq(5) input").val();
    var mib = _this.find("td:eq(6) span:eq(0)").text() != "" ? _this.find("td:eq(6) span:eq(0)").text() : _this.find("td:eq(6) input").val();
    var mibs = _this.find("td:eq(7) span:eq(0)").text() != "" ? _this.find("td:eq(7) span:eq(0)").text() : _this.find("td:eq(7) input").val();
    title = title.replace("{", "<span style='color:red;'>").replace("}", "</span>");
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
var tmp = null;
function edit(rs) {
    var _this = $(rs);
    var _tr = _this.parents("tr");
    tmp = _tr.html();
    var _td = _tr.find("td:eq(1)").html();
    alert(_td);
}







