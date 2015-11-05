/**
 * Created by SubDong on 2014/9/15.
 */
/******************zTree********************/
var activate_Struts;
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
    $("#monitorFolder").empty();
    $.ajax({
        url: "/monitoring/getMonitorId",
        type: "GET",
        dataType: "json",
        data: {
            forlder: treeNode.id
        },
        success: function (data) {
            var html_Monitor = "";
            $.each(data.rows, function (i, item) {
                //计算机质量度
                var quanlityHtml = "";
                var quanlityText = "";
                if (item.quality > 0) {
                    quanlityHtml = getStar(item.quality);
                    quanlityText = getStarStr(item.quality);
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

                //关键词状态
                var status = getStr(item.object.status);
                //匹配模式
                var matchType = getMatching(item.object.matchType);

                var urlPc = ((item.object.pcDestinationUrl == undefined) ? '' : item.object.pcDestinationUrl.substring(0, 25));
                var urlMobile = ((item.object.mobileDestinationUrl == undefined) ? '' : item.object.mobileDestinationUrl.substring(0, 25));
                if (i % 2 == 0) {
                    html_Monitor = "<tr class='list2_box1' mon='monitorClick' cname=" + item.object.keywordId + " folderID=" + item.folderId + " mtid = " + item.monitorId + "pause = " + item.object.pause + ">"
                        + "<td>&nbsp;" + item.object.keyword + "</td>"
                        + "<td>&nbsp;" + item.campaignName + "</td>"
                        + "<td>&nbsp;" + item.adgroupName + "</td>"
                        + "<td>&nbsp;" + status + "</td>"
                        + "<td>&nbsp;" + ((item.object.pause == false) ? '启用' : '暂停') + "</td>"
                        + "<td>&nbsp;" + item.object.price + "</td>"
                        + "<td>&nbsp;" + quanlityHtml + quanlityText + "</td>"
                        + "<td>&nbsp;" + mobileQuanlityHtml + "</td>"
                        + "<td>&nbsp;" + matchType + "</td>"
                        + "<td>&nbsp;<a href=" + urlPc + " title=" + urlPc + " target='_blank'>" + urlPc + "</a></td>"
                        + "<td>&nbsp;<a href=" + urlMobile + " title=" + urlMobile + " target='_blank'>" + urlMobile + "</a></td>"
                        + "<td>&nbsp;" + item.folderName + "</td></tr>";
                } else {
                    html_Monitor = "<tr class='list2_box2' mon='monitorClick' cname=" + item.object.keywordId + " folderID=" + item.folderId + " mtid = " + item.monitorId + "pause = " + item.object.pause + ">"
                        + "<td>&nbsp;" + item.object.keyword + "</td>"
                        + "<td>&nbsp;" + item.campaignName + "</td>"
                        + "<td>&nbsp;" + item.adgroupName + "</td>"
                        + "<td>&nbsp;" + status + "</td>"
                        + "<td>&nbsp;" + ((item.object.pause == false) ? '启用' : '暂停') + "</td>"
                        + "<td>&nbsp;" + item.object.price + "</td>"
                        + "<td>&nbsp;" + quanlityHtml + quanlityText + "</td>"
                        + "<td>&nbsp;" + mobileQuanlityHtml + "</td>"
                        + "<td>&nbsp;" + matchType + "</td>"
                        + "<td>&nbsp;<a href=" + ((item.object.pcDestinationUrl == undefined) ? '' : item.object.pcDestinationUrl) + " title=" + ((item.object.pcDestinationUrl == undefined) ? '' : item.object.pcDestinationUrl) + " target='_blank'>" + ((item.object.pcDestinationUrl == undefined) ? '' : item.object.pcDestinationUrl) + "</a></td>"
                        + "<td>&nbsp;<a href=" + ((item.object.mobileDestinationUrl == undefined) ? '' : item.object.mobileDestinationUrl) + " title=" + ((item.object.mobileDestinationUrl == undefined) ? '' : item.object.mobileDestinationUrl) + " target='_blank'>" + ((item.object.mobileDestinationUrl == undefined) ? '' : item.object.mobileDestinationUrl) + "</a></td>"
                        + "<td>&nbsp;" + item.folderName + "</td></tr>";
                }

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
            $("#zTree").height($(".assistant_right")[0].offsetHeight - 230 + "px");
        }
        else {
            $(".j_list02").hide();
            $("#zTree").height($(".assistant_right")[0].offsetHeight - 130 + "px");
            $("#jiangkong_box3").hide();
            $("#jiangkong_box2").show();

        }
    });

    $("#jiangkong_box").click(function () {
        getMonitor();
        if ($("#jiangkong_box3").css("display") == "none") {//隐藏
            $("#jiangkong_box2").hide();
            $("#jiangkong_box3").show();
        }
    });


    /****读取数据方法****/
    getTreeM();
    /**********************数据同步*****************************/
    $("#downSync").click(function () {
        var verify = -1;
        $.ajax({
            url: "/monitoring/synchronous",
            type: "GET",
            dataType: "json",
            async: false,
            success: function (data) {
                verify = data;
                if (verify == 1) {
                    alert("同步已完成！请继续操作");
                    getFolder();
                    getMonitor();
                    getTreeM();
                    verify = -1;
                } else {
                    alert("同步过程中出现了意想不到的结果，请重新同步")
                }
            }
        });
    });
    var downSyncFuc = function () {
        $.ajax({
            url: "/monitoring/synchronous",
            type: "GET",
            dataType: "json",
            async: false,
            success: function (data) {
                getFolder();
                getMonitor();
                getTreeM();
            }
        });
    }
    //数据上传
    $("#upSync").click(function () {
        if (confirm("是否确定将本地监控文件夹数据上传到蜂巢")) {
            $.ajax({
                url: "/monitoring/upMonitor",
                type: "GET",
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data == 0) {
                        alert("上传完成！请继续操作");
                        downSyncFuc();
                    } else {
                        alert("上传过程中出现了意想不到的结果，请重新同步")
                    }
                }
            });
        }
    })
    /***************************************************/
    $("#jkwjj").click(function () {
        getFolder();
        getTreeM();
    });

    $("body").on("click", "tr[cname=trName]", function () {
        var cid = $(this).attr("cid");
        var folderName = $(this).attr("folderName");
        var count = $(this).attr("count");
        $("#count").val((count == "undefined" ? 0 : count));
        $("#folder").val(folderName);
        $("#count").attr("fol", cid);
    });

    /********************修改监控文件夹名称*******************************/
    var Judge = 0;
    $("body").on("dblclick", "input[cname=folderName]", function () {
        $(this).attr("type", "text");
        $(this).removeAttr("readonly");
        $(this).removeClass("list2_input");
        Judge = 1;
    });

    $("body").on("blur", "input[cname=folderName]", function () {
        if (Judge == 1) {
            $(this).removeAttr("type");
            $(this).attr("readonly", true);
            $(this).addClass("list2_input");
            if ($(this).parent().parent().attr("folderName") != $(this).val()) {
                if (confirm("是否确定保存你的修改操作")) {
                    var name = $(this).val();

                    var foid = $(this).parent().parent().attr("cid");
                    $.ajax({
                        url: "/monitoring/updateFolderName",
                        type: "GET",
                        dataType: "json",
                        data: {
                            forlderId: foid,
                            forlderName: name
                        },
                        success: function (data) {
                            if (data) {
                                getFolder();
                                getTreeM();
                            }
                        }
                    });
                } else {
                    $(this).val($(this).parent().parent().attr("folderName"));
                }
            }
        }
        Judge = 0;
    });
    /***************************************************/

    /*************************添加监控文件夹**************************/
    $("body").on("click", "#addFolderQR", function () {
        var name = $("#folderInput").val();
        if (name != "") {
            $.ajax({
                url: "/monitoring/addFolder",
                type: "GET",
                dataType: "json",
                data: {
                    forlderName: name
                },
                success: function (data) {
                    if (data == 1) {
                        $("#dialogMsg").empty();
                        $("#dialogMsg").append("添加成功");
                        getFolder();
                        getTreeM();
                        closeAlert();
                    } else if (data == 0) {
                        $("#dialogMsg").empty();
                        $("#dialogMsg").append("添加失败");
                    } else if (data == -1) {
                        $("#dialogMsg").empty();
                        $("#dialogMsg").append("监控文加件最多添加20个！！");
                    }
                }
            });
        } else {
            $("#dialogMsg").empty();
            $("#dialogMsg").append("文件夹名称不能为空！");
        }

    });
    /***************************************************/

    /*************************删除监控文件夹**************************/
    $("body").on("click", "#removeFolder", function () {
        var folId = $("#count").attr("fol");
        if (confirm("确定你的删除操作？")) {
            $.ajax({
                url: "/monitoring/removeFolder",
                type: "GET",
                dataType: "json",
                data: {
                    forlderId: folId
                },
                success: function (data) {
                    if (data) {
                        getFolder();
                        getMonitor();
                        getTreeM();
                        alert("删除成功");
                    } else {
                        alert("删除失败");
                    }
                }
            });
        }
    });
    /***************************************************/
    $("body").on("click", "tr[mon=monitorClick]", function () {
        $("#remoneMonitor").val($(this).attr("mtid"));
        $("#hiddenkwid_1").val($(this).attr("cname"));
        activate_Struts = $(this).attr("pause");
        if ($(this).attr("pause") == "false") {
            $("#activate").html("暂停");
        } else if ($(this).attr("pause") == "true") {
            $("#activate").html("激活");
        }
    })
    /*************************删除监控对象**************************/
    $("body").on("click", "#removeMonitor", function () {
        var mtId = $("#remoneMonitor").val();
        if (confirm("确定你的删除操作？")) {
            $.ajax({
                url: "/monitoring/removeMonitor",
                type: "GET",
                dataType: "json",
                data: {
                    monitorId: mtId
                },
                success: function (data) {
                    if (data) {
                        getFolder();
                        getMonitor();
                        getTreeM();
                        alert("删除成功");
                    } else {
                        alert("删除失败");
                    }
                }
            });
        }
    });
    /***************************************************/

    /**********************添加监控对象*************/
    $("body").on("click", "#addMonitorQR", function () {
        var aliId = $("#tbodyClick").find(".list2_box3").find("input").val();
        if (aliId.length >= 24) {
            alert("请上传更新，关键词变化后再进行添加监控！");
            return;
        }
        var folderId = $("#monitorSelect").val();
        var campaignid = $("#tbodyClick").find(".list2_box3").find("input").attr("camp");
        var adgroupId = $("#tbodyClick").find(".list2_box3").find("input").attr("adg");

        $.ajax({
            url: "/monitoring/addMonitor",
            type: "GET",
            dataType: "json",
            data: {
                folderID: folderId,
                campaignId: campaignid,
                adgroupId: adgroupId,
                acliId: aliId
            },
            success: function (data) {
                if (data == 1) {
                    alert("添加成功");
                    closeAlert();
                } else if (data == 0) {
                    alert("添加失败");
                } else if (data == -1) {
                    alert("监控对象最多添加2000个！");
                } else if (data == -2) {
                    alert("该文件夹下已有此监控对象！");
                }
            }
        });

    });
    /*****************************************************/

    $("#activate").click(function () {
        if (activate_Struts == "false") {
            activate_Struts = true;
        } else {
            activate_Struts = false;
        }
        whenBlurEditKeyword(7, activate_Struts);
        getMonitor();
    });
});

/**
 * 获取监控文件夹 公用方法
 */
var getFolder = function () {
    $("#MonitorTbody").empty();
    $.ajax({
        url: "/monitoring/getFolder",
        type: "GET",
        dataType: "json",
        success: function (data) {
            var monitor_html = "";
            $.each(data.rows, function (i, items) {
                if (items.localStatus != 4) {
                    if (i % 2 != 0) {
                        monitor_html = "<tr class='list2_box1 folder' cname='trName' cid='" + items.folderId + "' folderName='" + items.folderName + "' count='" + items.countNumber + "'><td >&nbsp;<input cname='folderName' class='list2_input' readonly value=" + items.folderName + "></td><td>&nbsp;" + (items.countNumber == undefined ? 0 : items.countNumber) + "</td><td>&nbsp;</td>"
                    } else {
                        monitor_html = "<tr class='list2_box2 folder' cname='trName' cid='" + items.folderId + "' folderName='" + items.folderName + "' count='" + items.countNumber + "'><td >&nbsp;<input cname='folderName' class='list2_input' readonly value=" + items.folderName + "></td><td>&nbsp;" + (items.countNumber == undefined ? 0 : items.countNumber) + "</td><td>&nbsp;</td>"
                    }
                }
                $("#MonitorTbody").append(monitor_html);
            });
        }
    });
}

/**
 * 获取所有监控对象 公用方法
 * @param number
 * @returns {string}
 */

var getMonitor = function () {
    $("#monitorFolder").empty();
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
                    quanlityHtml = getStar(item.quality);
                    quanlityText = getStarStr(item.quality);
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
                //关键词状态
                var status = getStr(item.object.status);
                //匹配模式
                var matchType = getMatching(item.object.matchType);
                var urlPc = ((item.object.pcDestinationUrl == undefined) ? '' : item.object.pcDestinationUrl.substring(0, 25));
                var urlMobile = ((item.object.mobileDestinationUrl == undefined) ? '' : item.object.mobileDestinationUrl.substring(0, 25));
                if (item.localstatus != 4) {
                    if (i % 2 == 0) {
                        html_Monitor = "<tr class='list2_box1' mon='monitorClick' cname=" + item.object.keywordId + " folderID=" + item.folderId + " mtid = " + item.monitorId + " pause = " + item.object.pause + ">"
                            + "<td>&nbsp;" + item.object.keyword + "</td>"
                            + "<td>&nbsp;" + item.campaignName + "</td>"
                            + "<td>&nbsp;" + item.adgroupName + "</td>"
                            + "<td>&nbsp;" + status + "</td>"
                            + "<td>&nbsp;" + ((item.object.pause == false) ? '启用' : '暂停') + "</td>"
                            + "<td>&nbsp;" + item.object.price + "</td>"
                            + "<td>&nbsp;" + quanlityHtml + quanlityText + "</td>"
                            + "<td>&nbsp;" + mobileQuanlityHtml + "</td>"
                            + "<td>&nbsp;" + matchType + "</td>"
                            + "<td>&nbsp;<a href=" + urlPc + " title=" + urlPc + " target='_blank'>" + urlPc + "</a></td>"
                            + "<td>&nbsp;<a href=" + urlMobile + " title=" + urlMobile + " target='_blank'>" + urlMobile + "</a></td>"
                            + "<td>&nbsp;" + item.folderName + "</td></tr>";
                    } else {
                        html_Monitor = "<tr class='list2_box2' mon='monitorClick' cname=" + item.object.keywordId + " folderID=" + item.folderId + " mtid = " + item.monitorId + " pause = " + item.object.pause + ">"
                            + "<td>&nbsp;" + item.object.keyword + "</td>"
                            + "<td>&nbsp;" + item.campaignName + "</td>"
                            + "<td>&nbsp;" + item.adgroupName + "</td>"
                            + "<td>&nbsp;" + status + "</td>"
                            + "<td>&nbsp;" + ((item.object.pause == false) ? '启用' : '暂停') + "</td>"
                            + "<td>&nbsp;" + item.object.price + "</td>"
                            + "<td>&nbsp;" + quanlityHtml + quanlityText + "</td>"
                            + "<td>&nbsp;" + mobileQuanlityHtml + "</td>"
                            + "<td>&nbsp;" + matchType + "</td>"
                            + "<td>&nbsp;<a href=" + ((item.object.pcDestinationUrl == undefined) ? '' : item.object.pcDestinationUrl) + " title=" + ((item.object.pcDestinationUrl == undefined) ? '' : item.object.pcDestinationUrl) + " target='_blank'>" + ((item.object.pcDestinationUrl == undefined) ? '' : item.object.pcDestinationUrl) + "</a></td>"
                            + "<td>&nbsp;<a href=" + ((item.object.mobileDestinationUrl == undefined) ? '' : item.object.mobileDestinationUrl) + " title=" + ((item.object.mobileDestinationUrl == undefined) ? '' : item.object.mobileDestinationUrl) + " target='_blank'>" + ((item.object.mobileDestinationUrl == undefined) ? '' : item.object.mobileDestinationUrl) + "</a></td>"
                            + "<td>&nbsp;" + item.folderName + "</td></tr>";
                    }
                }
                $("#monitorFolder").append(html_Monitor);
            });
        }
    });
}


//质量度星级
function getStar(number) {
    var str = ""
    switch (number) {
        case 11:
        case 12:
        case 13:
            str += "<img src='/public/img/star.png'>";
            str += "<img src='/public/img/star3.png'>";
            str += "<img src='/public/img/star3.png'>";
            str += "<img src='/public/img/star3.png'>";
            str += "<img src='/public/img/star3.png'>";
            break;
        case 21:
        case 22:
            str += "<img src='/public/img/star.png'>";
            str += "<img src='/public/img/star.png'>";
            str += "<img src='/public/img/star3.png'>";
            str += "<img src='/public/img/star3.png'>";
            str += "<img src='/public/img/star3.png'>";
            break;
        case 3:
            str += "<img src='/public/img/star3.png'>";
            str += "<img src='/public/img/star3.png'>";
            str += "<img src='/public/img/star3.png'>";
            str += "<img src='/public/img/star3.png'>";
            str += "<img src='/public/img/star3.png'>";
    }
    return str;
}

//质量度星级注解
function getStarStr(number) {
    var str = "";
    switch (number) {
        case 11:
            str = "一星较难优化";
            break;
        case 12:
            str = "一星难度中等";
            break;
        case 13:
            str = "一星较易优化";
            break;
        case 21:
            str = "二星较难优化";
            break;
        case 22:
            str = "二星较易优化";
            break;
        case 3:
            str = "三星";
            break;
    }
    return str;
}

//匹配模式
function getMatching(number, phraseType) {
    var str = "";
    switch (number) {
        case 1:
            str = "精确";
            break;
        case 2:
            if (phraseType) {
                switch (phraseType) {
                    case 1:
                        str="短语-同义";
                        break;
                    case 2:
                        str="短语-精确";
                        break;
                    case 3:
                        str="短语-核心";
                        break;
                }
            } else {
                str = "短语";
            }
            break;
        case 3:
            str = "广泛";
            break;
    }
    return str;
}

//关键词状态
function getStr(number) {
    var str = "";
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

/**
 * 监控文件夹树
 */
function getTreeM() {
    $.ajax({
        url: "/monitoring/getTree",
        type: "GET",
        dataType: "json",
        success: function (data) {
            $("#zTree2").empty();
            $.fn.zTree.init($("#zTree2"), setting1, data.tree);
        }
    });
}


/**
 * 添加监控文件夹弹出框
 */
function folderDialog() {
    var jcBox = $("#adfd");
    jcBox.empty();
    jcBox.append("<li>监控文件夹名：<input type='text' id='folderInput'></li>");
    showDialog();
}
/**
 * 添加监控对象弹出框
 */
function MonitorDialog() {
    var jcBox = $("#admon");
    jcBox.empty();
    var de_html = "";
    $.ajax({
        url: "/monitoring/getFolder",
        type: "GET",
        dataType: "json",
        async: false,
        success: function (data) {
            de_html = "<select id='monitorSelect'>";
            $.each(data.rows, function (i, items) {
                de_html = de_html + "<option value=" + items.folderId + ">" + items.folderName + "</option>";
            });
            de_html = de_html + "</select>";
        }
    });
    jcBox.append("<li>选择监控文件夹名：" + de_html + "</li>");
    showDialogMon();
}

/**
 * 弹出框显示
 */
function showDialog() {
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    $("#addFolderDiv").css({
        left: ($("body").width() - $("#addFolderDiv").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#addFolderDiv").height()) / 2 + $(window).scrollTop() + "px",
        display: "block"
    });
}
/**
 * 添加监控对象弹出框显示
 */
function showDialogMon() {
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    $("#addMonitorDiv").css({
        left: ($("body").width() - $("#addMonitorDiv").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#addMonitorDiv").height()) / 2 + $(window).scrollTop() + "px",
        display: "block"
    });
}

window.onload = function () {
    rDrag.init(document.getElementById('folderTUO'));
    rDrag.init(document.getElementById('MonitorTUO'));
};

/**
 * 关闭弹窗
 */
function closeAlert() {
    $(".TB_overlayBG").css("display", "none");
    //监控对象窗口关闭
    $("#addMonitorDiv ").css("display", "none");
    //监控文件夹窗口关闭
    $("#addFolderDiv ").css("display", "none");
    //检查更改窗口关闭
    $("#CheckCompletion ").css("display", "none");
}