/**
 * Created by SubDong on 2014/9/15.
 */
/******************zTree********************/

var setting1 = {
    view: {
        showLine: false,
        selectedMulti: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeClick: beforeClick,
        beforeAsync: beforeAsync,
        onAsyncError: onAsyncError,
        onAsyncSuccess: onAsyncSuccess
    }
};
var folderId;
function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    for (var i = 0, l = childNodes.length; i < l; i++) {
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
    }
    return childNodes;
}
function beforeClick(treeId, treeNode) {
    //点击的是父节点(推广计划),则应该展示其下属的推广单元数据
    $("#jiangkong_box3").show();
    $("#jiangkong_box2").hide();
    $.ajax({
        url: "/monitoring/getMonitorId",
        type: "GET",
        dataType: "json",
        data:{
            forlder:treeNode.id
        },
        success: function (data) {
            var html_Monitor = "";
            $.each(data.rows, function (i, item) {
                //计算机质量度
                var quanlityHtml = "";
                var quanlityText = "";
                if (item.quality > 0) {
                    switch (parseInt(item.quality)) {
                        case 11:
                        case 12:
                        case 13:
                            quanlityHtml += "<img src='/public/img/star.png'>";
                            quanlityHtml += "<img src='/public/img/star3.png'>";
                            quanlityHtml += "<img src='/public/img/star3.png'>";
                            quanlityHtml += "<img src='/public/img/star3.png'>";
                            quanlityHtml += "<img src='/public/img/star3.png'>";
                            break;
                        case 21:
                        case 22:
                            quanlityHtml += "<img src='/public/img/star.png'>";
                            quanlityHtml += "<img src='/public/img/star.png'>";
                            quanlityHtml += "<img src='/public/img/star3.png'>";
                            quanlityHtml += "<img src='/public/img/star3.png'>";
                            quanlityHtml += "<img src='/public/img/star3.png'>";
                            break;
                        case 3:
                            quanlityHtml += "<img src='/public/img/star3.png'>";
                            quanlityHtml += "<img src='/public/img/star3.png'>";
                            quanlityHtml += "<img src='/public/img/star3.png'>";
                            quanlityHtml += "<img src='/public/img/star3.png'>";
                            quanlityHtml += "<img src='/public/img/star3.png'>";
                    }

                    switch (parseInt(item.quality)) {
                        case 11:
                            quanlityText = "一星较难优化";
                            break;
                        case 12:
                            quanlityText = "一星难度中等";
                            break;
                        case 13:
                            quanlityText = "一星较易优化";
                            break;
                        case 21:
                            quanlityText = "二星较难优化";
                            break;
                        case 22:
                            quanlityText = "二星较易优化";
                            break;
                        case 3:
                            quanlityText = "三星";
                            break;
                    }
                }

                //移动质量度
                var mobileQuanlityHtml = "";
                if (parseInt(item.mobileQuality) > 0) {
                    for (var i = 1; i <= 5; i++) {
                        if (parseInt(item.mobileQuality) >= i) {
                            mobileQuanlityHtml += "<img src='/public/img/star.png'>";
                        } else {
                            mobileQuanlityHtml += "<img src='/public/img/star3.png'>";
                        }
                    }
                    mobileQuanlityHtml += "&nbsp;&nbsp;&nbsp;" + item.mobileQuality;
                }

                var status = getStr(item.object.status);
                var urlPc = ((item.object.pcDestinationUrl == undefined) ? '' : item.object.pcDestinationUrl.substring(0, 25));
                var urlMobile = ((item.object.mobileDestinationUrl == undefined) ? '' : item.object.mobileDestinationUrl.substring(0, 25));
                if (i % 2 == 0) {
                    html_Monitor = "<tr class='list2_box1' cname=" + item.object.keywordId + " folderID=" + item.folderId + ">"
                        + "<td>&nbsp;" + item.object.keyword + "</td>"
                        + "<td>&nbsp;" + item.campaignName + "</td>"
                        + "<td>&nbsp;" + item.adgroupName + "</td>"
                        + "<td>&nbsp;" + status + "</td>"
                        + "<td>&nbsp;" + ((item.object.pause == false) ? '暂停' : '启用') + "</td>"
                        + "<td>&nbsp;" + item.object.price + "</td>"
                        + "<td>&nbsp;" + quanlityHtml + quanlityText + "</td>"
                        + "<td>&nbsp;" + mobileQuanlityHtml + "</td>"
                        + "<td>&nbsp;" + item.object.matchType + "</td>"
                        + "<td>&nbsp;<a href=" + urlPc + " title=" + urlPc + " target='_blank'>" + urlPc + "</a></td>"
                        + "<td>&nbsp;<a href=" + urlMobile + " title=" + urlMobile + " target='_blank'>" + urlMobile + "</a></td>"
                        + "<td>&nbsp;" + item.folderName + "</td></tr>";
                } else {
                    html_Monitor = "<tr class='list2_box2' cname=" + item.object.keywordId + " folderID=" + item.folderId + ">"
                        + "<td>&nbsp;" + item.object.keyword + "</td>"
                        + "<td>&nbsp;" + item.campaignName + "</td>"
                        + "<td>&nbsp;" + item.adgroupName + "</td>"
                        + "<td>&nbsp;" + status + "</td>"
                        + "<td>&nbsp;" + ((item.object.pause == false) ? '暂停' : '启用') + "</td>"
                        + "<td>&nbsp;" + item.object.price + "</td>"
                        + "<td>&nbsp;" + quanlityHtml + quanlityText + "</td>"
                        + "<td>&nbsp;" + mobileQuanlityHtml + "</td>"
                        + "<td>&nbsp;" + item.object.matchType + "</td>"
                        + "<td>&nbsp;<a href=" + ((item.object.pcDestinationUrl == undefined) ? '' : item.object.pcDestinationUrl) + " title=" + ((item.object.pcDestinationUrl == undefined) ? '' : item.object.pcDestinationUrl) + " target='_blank'>" + ((item.object.pcDestinationUrl == undefined) ? '' : item.object.pcDestinationUrl) + "</a></td>"
                        + "<td>&nbsp;<a href=" + ((item.object.mobileDestinationUrl == undefined) ? '' : item.object.mobileDestinationUrl) + " title=" + ((item.object.mobileDestinationUrl == undefined) ? '' : item.object.mobileDestinationUrl) + " target='_blank'>" + ((item.object.mobileDestinationUrl == undefined) ? '' : item.object.mobileDestinationUrl) + "</a></td>"
                        + "<td>&nbsp;" + item.folderName + "</td></tr>";
                }
                $("#monitorFolder").empty();
                $("#monitorFolder").append(html_Monitor);
            });
        }
    });


}
var log1, className1 = "dark";
function beforeAsync(treeId, treeNode) {
    className1 = (className1 === "dark" ? "" : "dark");
    showlog1("[ " + getTime() + " beforeAsync ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
    return true;
}
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
    showlog1("[ " + getTime() + " onAsyncError ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
}
function onAsyncSuccess(event, treeId, treeNode, msg) {
    showlog1("[ " + getTime() + " onAsyncSuccess ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
}

function showlog1(str) {
    if (!log1) log1 = $("#log1");
    log1.append("<li class='" + className1 + "'>" + str + "</li>");
    if (log1.children("li").length > 8) {
        log1.get(0).removeChild(log1.children("li")[0]);
    }
}
function getTime() {
    var now = new Date(),
        h = now.getHours(),
        m = now.getMinutes(),
        s = now.getSeconds(),
        ms = now.getMilliseconds();
    return (h + ":" + m + ":" + s + " " + ms);
}
//================================================


$(function () {


    /*******监控文件夹****/
    $(".jiangkong").click(function () {
        if ($(".j_list02").css("display") == "none") {//隐藏
            $(".j_list02").show();
        }
        else {
            $(".j_list02").hide();
            $("#jiangkong_box3").hide();
            $("#jiangkong_box2").show();

        }
    });

    $("#jiangkong_box").click(function () {
        $.ajax({
            url: "/monitoring/getMonitor",
            type: "GET",
            dataType: "json",
            success: function (data) {
                var html_Monitor = "";
                $.each(data.rows, function (i, item) {

                    //计算机质量度
                    var quanlityHtml = "";
                    var quanlityText = "";
                    if (item.quality > 0) {
                        switch (parseInt(item.quality)) {
                            case 11:
                            case 12:
                            case 13:
                                quanlityHtml += "<img src='/public/img/star.png'>";
                                quanlityHtml += "<img src='/public/img/star3.png'>";
                                quanlityHtml += "<img src='/public/img/star3.png'>";
                                quanlityHtml += "<img src='/public/img/star3.png'>";
                                quanlityHtml += "<img src='/public/img/star3.png'>";
                                break;
                            case 21:
                            case 22:
                                quanlityHtml += "<img src='/public/img/star.png'>";
                                quanlityHtml += "<img src='/public/img/star.png'>";
                                quanlityHtml += "<img src='/public/img/star3.png'>";
                                quanlityHtml += "<img src='/public/img/star3.png'>";
                                quanlityHtml += "<img src='/public/img/star3.png'>";
                                break;
                            case 3:
                                quanlityHtml += "<img src='/public/img/star3.png'>";
                                quanlityHtml += "<img src='/public/img/star3.png'>";
                                quanlityHtml += "<img src='/public/img/star3.png'>";
                                quanlityHtml += "<img src='/public/img/star3.png'>";
                                quanlityHtml += "<img src='/public/img/star3.png'>";
                        }

                        switch (parseInt(item.quality)) {
                            case 11:
                                quanlityText = "一星较难优化";
                                break;
                            case 12:
                                quanlityText = "一星难度中等";
                                break;
                            case 13:
                                quanlityText = "一星较易优化";
                                break;
                            case 21:
                                quanlityText = "二星较难优化";
                                break;
                            case 22:
                                quanlityText = "二星较易优化";
                                break;
                            case 3:
                                quanlityText = "三星";
                                break;
                        }
                    }

                    //移动质量度
                    var mobileQuanlityHtml = "";
                    if (parseInt(item.mobileQuality) > 0) {
                        for (var i = 1; i <= 5; i++) {
                            if (parseInt(item.mobileQuality) >= i) {
                                mobileQuanlityHtml += "<img src='/public/img/star.png'>";
                            } else {
                                mobileQuanlityHtml += "<img src='/public/img/star3.png'>";
                            }
                        }
                        mobileQuanlityHtml += "&nbsp;&nbsp;&nbsp;" + item.mobileQuality;
                    }


                    var urlPc = ((item.object.pcDestinationUrl == undefined) ? '' : item.object.pcDestinationUrl.substring(0, 25));
                    var urlMobile = ((item.object.mobileDestinationUrl == undefined) ? '' : item.object.mobileDestinationUrl.substring(0, 25));
                    if (i % 2 == 0) {
                        html_Monitor = "<tr class='list2_box1' cname=" + item.object.keywordId + " folderID=" + item.folderId + ">"
                            + "<td>&nbsp;" + item.object.keyword + "</td>"
                            + "<td>&nbsp;" + item.campaignName + "</td>"
                            + "<td>&nbsp;" + item.adgroupName + "</td>"
                            + "<td>&nbsp;" + item.object.status + "</td>"
                            + "<td>&nbsp;" + ((item.object.pause == false) ? '暂停' : '启用') + "</td>"
                            + "<td>&nbsp;" + item.object.price + "</td>"
                            + "<td>&nbsp;" + quanlityHtml + quanlityText + "</td>"
                            + "<td>&nbsp;" + mobileQuanlityHtml + "</td>"
                            + "<td>&nbsp;" + item.object.matchType + "</td>"
                            + "<td>&nbsp;<a href=" + urlPc + " title=" + urlPc + " target='_blank'>" + urlPc + "</a></td>"
                            + "<td>&nbsp;<a href=" + urlMobile + " title=" + urlMobile + " target='_blank'>" + urlMobile + "</a></td>"
                            + "<td>&nbsp;" + item.folderName + "</td></tr>";
                    } else {
                        html_Monitor = "<tr class='list2_box2' cname=" + item.object.keywordId + " folderID=" + item.folderId + ">"
                            + "<td>&nbsp;" + item.object.keyword + "</td>"
                            + "<td>&nbsp;" + item.campaignName + "</td>"
                            + "<td>&nbsp;" + item.adgroupName + "</td>"
                            + "<td>&nbsp;" + item.object.status + "</td>"
                            + "<td>&nbsp;" + ((item.object.pause == false) ? '暂停' : '启用') + "</td>"
                            + "<td>&nbsp;" + item.object.price + "</td>"
                            + "<td>&nbsp;" + quanlityHtml + quanlityText + "</td>"
                            + "<td>&nbsp;" + mobileQuanlityHtml + "</td>"
                            + "<td>&nbsp;" + item.object.matchType + "</td>"
                            + "<td>&nbsp;<a href=" + ((item.object.pcDestinationUrl == undefined) ? '' : item.object.pcDestinationUrl) + " title=" + ((item.object.pcDestinationUrl == undefined) ? '' : item.object.pcDestinationUrl) + " target='_blank'>" + ((item.object.pcDestinationUrl == undefined) ? '' : item.object.pcDestinationUrl) + "</a></td>"
                            + "<td>&nbsp;<a href=" + ((item.object.mobileDestinationUrl == undefined) ? '' : item.object.mobileDestinationUrl) + " title=" + ((item.object.mobileDestinationUrl == undefined) ? '' : item.object.mobileDestinationUrl) + " target='_blank'>" + ((item.object.mobileDestinationUrl == undefined) ? '' : item.object.mobileDestinationUrl) + "</a></td>"
                            + "<td>&nbsp;" + item.folderName + "</td></tr>";
                    }
                    $("#monitorFolder").empty();
                    $("#monitorFolder").append(html_Monitor);
                });
            }
        });


        if ($("#jiangkong_box3").css("display") == "none") {//隐藏
            $("#jiangkong_box2").hide();
            $("#jiangkong_box3").show();
        }
        else {
            $("#jiangkong_box3").hide();
            $("#jiangkong_box2").show();
        }
    });


    /****读取数据方法****/
    $.ajax({
        url: "/monitoring/getTree",
        type: "GET",
        dataType: "json",
        success: function (data) {
            $.fn.zTree.init($("#zTree2"), setting1, data.tree);
        }
    });
    $("#downSync").click(function () {
        var verify = -1;
        $.ajax({
            url: "/monitoring/synchronous",
            type: "GET",
            dataType: "json",
            async: false,
            success: function (data) {
                verify = data;
            }
        });
        $.ajax({
            url: "/monitoring/getFolder",
            type: "GET",
            dataType: "json",
            success: function (data) {
                $.each(data.rows, function (i, items) {

                });

                if (verify == 1) {
                    alert("同步已完成！请继续操作");
                    verify = -1;
                } else {
                    alert("同步过程中出现了意想不到的结果，请重新同步")
                }
            }
        });
    });
    $("#jkwjj").click(function () {
        $.ajax({
            url: "/monitoring/getFolder",
            type: "GET",
            dataType: "json",
            success: function (data) {
                var monitor_html = "";
                $.each(data.rows, function (i, items) {
                    if (i % 2 != 0) {
                        monitor_html = "<tr class='list2_box1' cname='trName' cid='" + items.folderId + "' folderName='" + items.folderName + "' count='" + items.countNumber + "'><td >&nbsp;" + items.folderName + "</td><td>&nbsp;" + items.countNumber + "</td><td>&nbsp;</td>"
                    } else {
                        monitor_html = "<tr class='list2_box2' cname='trName' cid='" + items.folderId + "' folderName='" + items.folderName + "' count='" + items.countNumber + "'><td >&nbsp;" + items.folderName + "</td><td>&nbsp;" + items.countNumber + "</td><td>&nbsp;</td>"
                    }
                });
                $("#MonitorTbody").empty();
                $("#MonitorTbody").append(monitor_html);
            }
        });
    });
    $("body").on("click", "tr[cname=trName]", function () {
        var cid = $(this).attr("cid");
        var folderName = $(this).attr("folderName");
        var count = $(this).attr("count");
        $("#count").val(count);
        $("#folder").val(folderName);
    });
});
function getStr(number){
    var str ="";
    switch (number) {
        case 41:
            str = "有效";
            break;
        case 42:
            str = "暂停推广";
            break;
        case 43:
            str = "不宜推广";
            break;
        case 44:
            str = "搜索无效";
            break;
        case 45:
            str = "待激活";
            break;
        case 46:
            str = "审核中";
            break;
        case 47:
            str = "搜索量过低";
            break;
        case 48:
            str = "部分无效";
            break;
        case 49:
            str = "计算机搜索无效";
            break;
        case 50:
            str = "移动搜索无效";
            break;
        default:
            html = "本地新增";
    }
    return str;
}