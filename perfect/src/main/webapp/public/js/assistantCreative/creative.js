/**
 * Created by XiaoWei on 2014/8/21.
 */
$(function () {
    loadCreativeData();
});

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
                    "<td>" + until.substring(10, json[i].title) + "</td>" +
                    " <td>" + until.substring(10, json[i].description1) + "</td>" +
                    " <td>" + until.substring(10, json[i].description2) + "</td>" +
                    " <td><a href='" + json[i].pcDestinationUrl + "'>" + until.substring(10, json[i].pcDestinationUrl) + "</a></td>" +
                    " <td>" + until.substring(10, json[i].pcDisplayUrl) + "</td>" +
                    " <td>" + until.substring(10, json[i].mobileDestinationUrl) + "</td>" +
                    " <td>" + until.convert(json[i].pause, "启用:暂停") + "</td>" +
                    " <td>" + until.getCreativeStatus(json[i].status) + "</td>" +
                    "</tr>";
                _createTable.append(_tbody);
            }
        }
    });
}

function on(obj) {
    $("#sDiv input[type='text']").val("");
    var _this = $(obj);
    var title = _this.find("td:eq(0) a").attr("title");
    var de1 = _this.find("td:eq(1) a").attr("title");
    var de2 = _this.find("td:eq(2) a").attr("title");
    var pc = _this.find("td:eq(3) a").attr("href");
    var mib=_this.find("td:eq(4) a").attr("title");
    $("#sTitle").val(title).keyup(function () {
        $("#sTitle_size").text($("#sTitle").val().length)
    });
    $("#sTitle_size").text(title.length);

    $("#sDes1").val(de1).keyup(function () {
        $("#sDes1_size").text($("#sDes1").val().length)
    });
    $("#sDes1_size").text(de1.length);

    $("#sDes2").val(de1).keyup(function () {
        $("#sDes2_size").text($("#sDes2").val().length)
    });
    $("#sDes2_size").text(de2.length);

    $("#sPc").val(pc).keyup(function(){
        $("#sPc_size").text($("#sPc").val().length);
    });
    $("#sPc_size").text(pc.length);

    $("#sMib").val(mib).keyup(function(){
        $("#sMib_size").text($("#sMib").val().length);
    });
    $("#sMib_size").text(mib.length);
}
function addTb(rs) {
//    var _this = $(rs);
//    var val = _this.prev("input");
    if (window.getSelection()) {
        alert(window.getSelection());
    } else if(document.getSelection()){
        alert(document.getSelection.toString());
    }else {
       alert(document.selection.createRange().text);
    }
}






