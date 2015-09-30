/**
 * Created by XiaoWei on 2015/1/19.
 */
function uploadDialog() {
    openUploadDialog();
}
function openUploadDialog() {
    loadOperateCampainList();
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    $("#uploadMerge").css({
        left: ($("body").width() - $("#uploadMerge").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#uploadMerge").height()) / 2 + $(window).scrollTop() + "px",
        display: "block"
    });
}
function closeUploadDialog() {
    $(".TB_overlayBG").css("display", "none");
    $("#uploadMerge").css("display", "none");
}
function loadOperateCampainList() {
    $.get("/uploadMerge/getCampList", function (res) {
        $("#existsCamp ul").empty();
        var results = res.data;
        if (results.length > 0) {
            $("#existsCamp ul").append("<li><a href='javascript:void(0)' onclick='allOnCheck()'>清空选择</a></li>");
            $.each(results, function (i, item) {
                var _li = "";
                if (item.campaignId != undefined) {
                    _li = "<li><input id='" + item.campaignId + "' type='checkbox' name='camp_o'>" + item.campaignName + "</li>";
                } else {
                    _li = "<li><input id='" + item.id + "' type='checkbox' name='camp_o'>" + item.campaignName + "</li>";
                }
                $("#existsCamp ul").append(_li);
            });
        }
    });
}
function allOnCheck() {
    $("input[name='camp_o']:checked").attr("checked", false);
}
function CheckCompletion() {
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    $("#CheckCompletion").css({
        left: ($("body").width() - $("#CheckCompletion").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#CheckCompletion").height()) / 2 + $(window).scrollTop() + "px",
        display: "block"
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $("#CheckCompletion").css("display", "none");
    });
}
function uploadDialogOk() {
    var uploadType = "";
    var allChose = document.getElementsByName("up1");
    for (var i = 0; i < allChose.length; i++) {
        if (allChose[i].checked == true) {
            uploadType = allChose[i].value;
        }
    }
    if (uploadType != "") {
        if (uploadType == "all") {//上传全部数据
            var conf = confirm("是否上传全部计划内包含的一系列操作过的模块到百度？");
            if (conf) {
                $.get("/uploadMerge/uploadByAll", function (res) {
                    if (res.msg == "1") {
                        reloadGrid();
                        loadTree();
                        closeUploadDialog();
                        CheckCompletion();
                    } else {
                        alert(res.msg);
                        reloadGrid();
                        loadTree();
                        closeUploadDialog();
                    }
                });
            }
        } else {//上传部分数据
            var campaignIds = "";
            $.each($("input[name='camp_o']:checked"), function (i, item) {
                if (item.checked) {
                    campaignIds += item.id + ","
                }
            });
            if (campaignIds.length == 0) {
                alert("请选择要上传的计划！");
                return;
            } else {
                campaignIds = campaignIds.slice(0, -1);
                var conf = confirm("是否上传选择的这些计划？");
                if (conf) {
                    $.post("/uploadMerge/uploadBySomeCamp", {cids: campaignIds}, function (res) {
                        if (res.msg == "1") {
                            reloadGrid();
                            loadTree();
                            closeUploadDialog();
                            CheckCompletion();
                        } else {
                            alert(res.msg);
                            reloadGrid();
                            loadTree();
                            closeUploadDialog();
                        }
                    });
                }
            }
        }
    } else {
        alert("请选择上传类型！");
    }
}
function reloadGrid() {
    var choseTable = $("#tabMenu li");
    var tabName = "";
    choseTable.each(function (i, o) {
        if ($(o).attr("class") == "current") {
            tabName = $(o).html();
        }
    });
    if (tabName == "关键词") {
        if (jsonData.cid != null) {
            getKwdList(0);
        }
    } else if (tabName == "推广计划") {
        getCampaignList(0);
    } else if (tabName == "普通创意") {
        if (jsonData.cid != null) {
            if (jsonData.cid != null && jsonData.aid != null) {
                getCreativeUnit(jsonData);
            } else {
                getCreativePlan(jsonData.cid);
            }
        }
    } else if (tabName == "附加创意") {

    } else if (tabName == "推广单元") {
        if (jsonData.cid != null) {
            getAdgroupPlan(jsonData.cid, jsonData.cn);
        }
    }
}
$(function () {
    $("#tabs").tabs();
});