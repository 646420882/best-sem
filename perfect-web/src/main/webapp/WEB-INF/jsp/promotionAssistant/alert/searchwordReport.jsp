<%--
  Created by IntelliJ IDEA.
  User: guochunyan
  Date: 2014/9/3
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">
    <style type="text/css">
        .k_l_under {
            text-align: center;
            line-height: 24px;
        }

        .searchword_text textarea {
            width: 100%;
            height: 140px;
            overflow: auto;

        }

        .k_l_top {
            margin: 0px 2% 10px;
        }

        .k_l_under {
            margin: 0px 0 10px;
        }

        .become {
            margin: 20px 15px 0 0;
        }

        table tr, table td {
            border: 1px solid gray;
        }

        table tbody tr {
            cursor: pointer;
        }

        table thead td {
            background-color: #dcdcdc;
        }

        .choose {
            background-color: #f7ecb5;
        }

        table tbody td {
            border: 1px solid #ffffff;
            height: 30px;
            text-align: center;
        }
    </style>
</head>
<body>
<div id="background" class="background hides"></div>
<div id="progressBar" class="progressBar hides">数据处理中，请稍等...</div>
<div style="background-color: #f3f5fd; width: 895px; height: 800px;">
    <div class="searchword_top over">
        <ul>
            <li>
                <span>报告分析对象</span>
                <span>账户</span>
                <%--<select>
                    <option value="11">关键词</option>
                    <option value="7">普通创意</option>
                    <option value="5">推广单元</option>
                    <option value="3">推广计划</option>
                    <option value="2">账户</option>
                </select>--%>
            </li>
            <li>
                <span>时间范围</span>
                <select id="timeRange">
                    <option value="1">昨天</option>
                    <option value="7">最近七天</option>
                    <option value="30">最近一月</option>
                </select>
            </li>
            <li>
                <span>地域范围</span>
                <a href="#" id="chooseRegion">选择地域</a>
                <input type="image" src="../public/img/zs_input.png">
            </li>
            <li>
                <span>投放设备</span>
                <select id="device">
                    <option value="0">全部设备</option>
                    <option value="1">计算机设备</option>
                    <option value="2">移动设备</option>
                </select>
            </li>
            <%--<li>
                <span>搜索引擎范围</span>
               <select id="searchEngine">
                   <option>全部</option>
                   <option>百度推广</option>
                   <option>非百度推广</option>
               </select>
            </li>--%>
        </ul>
        <a class="become fr createReport" href="javascript:"> 生成报告</a>
    </div>
    <div class="searchword_under over">
        <div class="searchword_under1 fl over">
            <div class="search_input over">
                <div class="k_l_top over">
                    <span id="chooseKwdCount">已选关键词（0/500）</span>
                </div>
                <div class="searchword_text over">
                    <textarea id="kwdTextArea"></textarea>

                    <div id="choosePlaceDiv" style="display: none;">
                        <span>保存至账户</span><br/><br/>
                        <select id="select_campaign" onchange="loadAdgroupSelect();">
                        </select><br/><br/>
                        <select id="select_adgroup">
                            <option value='pleaseChoose'>请选择推广单元
                            <option>
                        </select><br/><br/>
                        <select id="select_matchType">
                            <option value="pleaseChoose">请选择匹配模式</option>
                            <option value="3-">广泛</option>
                            <option value="2-3">核心包含</option>
                            <option value="2-1">同义包含</option>
                            <option value="2-2">精确包含</option>
                            <option value="1-">精确匹配</option>
                        </select><br/><br/>
                        <a href="#" id="choosePlaceDiv_save">保存</a>&nbsp;&nbsp;
                        <a href="#" id="choosePlaceDiv_cancel">取消</a>
                    </div>
                </div>
                <div class="k_l_under over">
                    <a href="#" id="saveAccount">保存至账户</a>
                </div>
            </div>
            <div class="search_input over">
                <div class="k_l_top over">
                    <span id="chooseNeigKwdCount">已选否定词（0/500）</span>
                </div>
                <div class="searchword_text over">
                    <textarea id="neigTextArea"></textarea>

                    <div id="setNeigWordDiv" style="display: none;">
                        <span>设置至账户</span><br/><br/>
                        <select id="select_campaign2" onchange="loadAdgroupSelect2();">
                        </select><br/><br/>
                        <select id="select_adgroup2">
                            <option value='pleaseChoose'>请选择推广单元
                            <option>
                        </select><br/><br/>
                        <input type="radio" name="isNeig" checked="checked" value="1"/>否定&nbsp;&nbsp;&nbsp;
                        <input type="radio" name="isNeig" value="2"/>精确否定
                        <br/><br/>
                        <a href="#" id="setNeigWordDiv_ok">确定</a>&nbsp;&nbsp;
                        <a href="#" id="setNeigWordDiv_cancel">取消</a>
                    </div>
                </div>
                <div class="k_l_under over">
                    <a href="#" id="setToAccount">设置至账户</a>
                </div>

            </div>
        </div>
        <div class="searchword_under2 fr over">
            <input id="addKeyword" type="button" value="添加为关键词"/>
            <input id="addNeigWord" type="button" value="添加为否定词"/>

            <div class="search_mid over" style="height:430px;overflow: scroll;">
                <table style="width:1500px;" cellspacing="0" border="0">
                    <thead>
                    <tr class="list02_top">
                        <th>&nbsp;关键词</th>
                        <th>&nbsp;搜索词</th>
                        <th>&nbsp;点击量</th>
                        <th>&nbsp;展现量</th>
                        <th>&nbsp;搜索引擎</th>
                        <th>&nbsp;推广单元</th>
                        <th>&nbsp;推广计划</th>
                        <th>&nbsp;创意标题</th>
                        <th>&nbsp;创意描述1</th>
                        <th>&nbsp;创意描述2</th>
                        <th>&nbsp;日期</th>
                        <th>&nbsp;精确匹配扩展(地域词)触发</th>
                    </tr>
                    </thead>
                    <tbody id="searchWordTbody">
                    </tbody>
                </table>
            </div>
            <%--<input type="button" value="导出全部到文件" class="zs_input2">--%>
        </div>
    </div>
</div>

<%--设置推广地域--%>
<jsp:include page="./Region.jsp"/>
</body>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript">
    // 对Date的扩展，将 Date 转化为指定格式的String
    // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
    // 例子：
    // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2014-07-02 08:09:04.423
    // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2014-7-2 8:9:4.18
    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1,                 //月份
            "d+": this.getDate(),                    //日
            "h+": this.getHours(),                   //小时
            "m+": this.getMinutes(),                 //分
            "s+": this.getSeconds(),                 //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
</script>
<script type="text/javascript">

/**
 * 获取昨天, 近七天, 近30天日期
 * 参数为1, 昨天
 * 参数为7, 7天
 * 参数为30, 近30天
 * @param day
 */
var getDateByDay = function (day) {
    var currDate = new Date();
    var jsonDate = {};
    jsonDate["startDate"] = null;
    jsonDate["endDate"] = null;
    if (day == 1) {
        currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24);
        jsonDate["startDate"] = currDate.Format("yyyy-MM-dd hh:mm:ss");
        jsonDate["endDate"] = currDate.Format("yyyy-MM-dd hh:mm:ss");
    } else if (day == 7) {
        currDate = new Date();
        currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24);
        jsonDate["endDate"] = currDate.Format("yyyy-MM-dd hh:mm:ss");
        currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24 * 6);
        jsonDate["startDate"] = currDate.Format("yyyy-MM-dd hh:mm:ss");
    } else if (day == 30) {
        currDate = new Date();
        currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24);
        jsonDate["endDate"] = currDate.Format("yyyy-MM-dd hh:mm:ss");
        currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24 * 29);
        jsonDate["startDate"] = currDate.Format("yyyy-MM-dd hh:mm:ss");
    }
    return jsonDate;
};


//预加载推广计划列表
function loadCampaignSelect() {
    $.ajax({
        url: "/assistantKeyword/getCampaignByAccountId",
        type: "post",
        dataType: "json",
        success: function (data) {
            var html = "<option value='pleaseChoose'>请选择推广计划</option>";
            for (var i = 0; i < data.length; i++) {
                if (data[i].campaignId == null) {
                    html += "<option value='" + data[i].id + "'>" + data[i].campaignName + "</option>";
                } else {
                    html += "<option value='" + data[i].campaignId + "'>" + data[i].campaignName + "</option>";
                }
            }
            $("#select_campaign").html(html);
            $("#select_campaign2").html(html);
        }
    });
}

//预加载推广单元列表
function loadAdgroupSelect() {
    var cid = $("#select_campaign").val();
    if (cid == "pleaseChoose") {
        return;
    }

    $.ajax({
        url: "/assistantKeyword/getAdgroupByCid",
        type: "post",
        data: {"cid": cid},
        dataType: "json",
        success: function (data) {
            var html = "<option value='pleaseChoose'>请选择推广单元</option>";
            for (var i = 0; i < data.length; i++) {
                if (data[i].adgroupId == null) {
                    html += "<option value='" + data[i].id + "'>" + data[i].adgroupName + "</option>";
                } else {
                    html += "<option value='" + data[i].adgroupId + "'>" + data[i].adgroupName + "</option>";
                }
            }
            $("#select_adgroup").html(html);
        }
    });
}

//预加载推广单元列表
function loadAdgroupSelect2() {
    var cid = $("#select_campaign2").val();
    if (cid == "pleaseChoose") {
        return;
    }

    $.ajax({
        url: "/assistantKeyword/getAdgroupByCid",
        type: "post",
        data: {"cid": cid},
        dataType: "json",
        success: function (data) {
            var html = "<option value='pleaseChoose'>请选择推广单元</option>";
            for (var i = 0; i < data.length; i++) {
                if (data[i].adgroupId == null) {
                    html += "<option value='" + data[i].id + "'>" + data[i].adgroupName + "</option>";
                } else {
                    html += "<option value='" + data[i].adgroupId + "'>" + data[i].adgroupName + "</option>";
                }
            }
            $("#select_adgroup2").html(html);
        }
    });
}

//进入该页面的时候就加载推广计划
loadCampaignSelect();


//得到已经选中的的地域
function getRegionNames() {
    var regions = "";
    var leaf = $("#regionList").find("div[class=leaf]");
    leaf.each(function () {
        if ($(this).find("input[type=checkbox]")[0].checked == true) {
            regions += $(this).find("label").html() + ",";
        }
    });
    return regions;
}


/**
 *生成报告按钮的单击事件
 */
$(".createReport").click(function () {
    var timeRange = getDateByDay($("#timeRange").val());

    var levelOfDetails = 12;//分析层级
    var startDate = timeRange["startDate"];//开始日期
    var endDate = timeRange["endDate"];//结束日期
    var device = $("#device").val();//投放设备
    var regions = getRegionNames();//推广地域
//        var searchEngine = $("#searchEngine").val();//搜索引擎范围

    $("#searchWordTbody").html("正在联网为您获取搜索词报告...");
    $.ajax({
        url: "/assistantKeyword/getSearchWordReport",
        type: "post",
        data: {"levelOfDetails": levelOfDetails, "startDate": startDate, "endDate": endDate, "device": device, "attributes": regions},
        dataType: "json",
        success: function (data) {
            if (data.length == 0) {
                $("#searchWordTbody").empty();
                $("#searchWordTbody").html("没有该范围的数据!");
            } else {
                var html = toTableHtml(data);
                $("#searchWordTbody").empty();
                $("#searchWordTbody").html(html);
            }
        }
    });
});


/**
 *生成搜索词报告的html
 */
function toTableHtml(data) {
    var html = "";
    for (var i = 0; i < data.length; i++) {
        var className = "list2_box2";
        if (i % 2 == 0) {
            className = "list1_box1";
        }

        html += "<tr class='" + className + "'>";
        html += "<td>" + data[i].keyword + "</td>";
        html += "<td>" + data[i].searchWord + "</td>";
        html += "<td>" + data[i].click + "</td>";
        html += "<td>" + data[i].impression + "</td>";
        html += "<td>" + data[i].searchEngine + "</td>";
        html += "<td>" + data[i].adgroupName + "</td>";
        html += "<td>" + data[i].campaignName + "</td>";
        html += "<td>" + (data[i].createTitle.substring(0, 12)) + "</td>";
        html += "<td>" + (data[i].createDesc1.substring(0, 12)) + "</td>";
        html += "<td>" + (data[i].createDesc2.substring(0, 12)) + "</td>";
        html += "<td>" + data[i].date + "</td>";
        html += "<td>" + data[i].parseExtent + "</td>";
        html += "</tr>";
    }
    return html;
}

//choose
$("#searchWordTbody").delegate("tr", "click", function () {
    if (/choose/.test($(this).attr("class"))) {
        $(this).removeClass("choose");
    } else {
        $(this).addClass("choose");
    }
});


/**
 *添加为关键词按钮单击事件
 */
$("#addKeyword").click(function () {
    var kwds = getChooseKeyword();
    $("#kwdTextArea").val(kwds);
    $("#chooseKwdCount").html("已选关键词（" + getChooseCount(kwds) + "/500）");
});


/**
 *添加为关键词按钮单击事件
 */
$("#addNeigWord").click(function () {
    var kwds = getChooseKeyword();
    $("#neigTextArea").val(kwds);
    $("#chooseNeigKwdCount").html("已选否定词（" + getChooseCount(kwds) + "/500）");
});


$("#kwdTextArea").keydown(function () {
    $("#chooseKwdCount").html("已选关键词（" + getChooseCount($(this).val()) + "/500）");
});

$("#neigTextArea").keydown(function () {
    $("#chooseNeigKwdCount").html("已选否定词（" + getChooseCount($(this).val()) + "/500）");
});

function getChooseCount(kwds) {
    return kwds.split("\n").length;
}


/**
 *得到已经选中的关键词
 * @returns {string}
 */
function getChooseKeyword() {
    var keywords = "";
    var chooseKwd = $("#searchWordTbody").find(".choose");
    chooseKwd.each(function () {
        keywords += $(this).find("td:eq(1)").html() + "\n";
    });
    keywords=keywords.substring(0, keywords.lastIndexOf('\n'));
    return keywords;
}


/**
 *保存至账户按钮的事件
 */
$("#saveAccount").click(function () {
    if ($.trim($("#kwdTextArea").val()) == "") {
        alert("请先添加关键词!");
        return;
    }


    $("#kwdTextArea").hide(0);
    $("#choosePlaceDiv").show(0);
});

/**
 *添加为关键词的取消按钮的事件
 */
$("#choosePlaceDiv_cancel").click(function () {
    $("#choosePlaceDiv").hide(0);
    $("#kwdTextArea").show(0);
});


/**
 *添加为关键词中的保存按钮的单击事件
 */
$("#choosePlaceDiv_save").click(function () {
    if ($("#select_campaign").val() == "pleaseChoose") {
        alert("请选择计划!")
        return;
    }
    if ($("#select_adgroup").val() == "pleaseChoose") {
        alert("请选择单元!");
        return;
    }
    if ($("#select_matchType").val() == "pleaseChoose") {
        alert("请选择匹配模式!");
        return;
    }

    var agid = $("#select_adgroup").val();
    var matchType = $("#select_matchType").val();
    var keywords = $("#kwdTextArea").val();

    $.ajax({
        url: "/assistantKeyword/addSearchwordKeyword",
        type: "post",
        data: {"agid": agid, "keywords": keywords, "matchType": matchType},
        dataType: "json",
        success: function (data) {
            $("#kwdTextArea").val("");
            $("#searchWordTbody").find(".choose").removeClass("choose");
            $("#choosePlaceDiv").hide(0);
            $("#kwdTextArea").show(0);
        }
    });

});


/**
 *设置否定关键词中的设置至账户按钮的事件
 */
$("#setToAccount").click(function () {
    if ($.trim($("#neigTextArea").val()) == "") {
        alert("请先选择关键词作为否定词！");
        return;
    }
    $("#neigTextArea").hide(0);
    $("#setNeigWordDiv").show(0);
});


/**
 *设置否定关键词中的取消按钮的但单击事件
 */
$("#setNeigWordDiv_cancel").click(function () {
    $("#setNeigWordDiv").hide(0);
    $("#neigTextArea").show(0);
});


var isNeig = 1;
$("input[name=isNeig]").click(function () {
    isNeig = $(this).val();
});

/**
 *设置否定关键词中确定按钮的事件
 */
$("#setNeigWordDiv_ok").click(function () {

    if ($("#select_campaign2").val() == "pleaseChoose") {
        alert("请选择计划!")
        return;
    }
    if ($("#select_adgroup2").val() == "pleaseChoose") {
        alert("请选择单元!");
        return;
    }

    var agid = $("#select_adgroup2").val();
    var keywords = $("#neigTextArea").val();
    var neigType = isNeig;

    $.ajax({
        url: "/assistantKeyword/setNeigWord",
        type: "post",
        data: {"agid": agid, "keywords": keywords, "neigType": neigType},
        dataType: "json",
        success: function (data) {
            $("#neigTextArea").val("");
            $("#searchWordTbody").find(".choose").removeClass("choose");
            $("#setNeigWordDiv").hide(0);
            $("#neigTextArea").show(0);
        }
    });

});

/**
 *选中推广地域
 */
$("#chooseRegion").click(function () {
    $("#ctrldialogplanRegionDialog").show(0);
});


//loading
var ajaxbg = $("#background,#progressBar");
ajaxbg.hide();
$(document).ajaxStart(function () {
    ajaxbg.show();
});
$(document).ajaxStop(function () {
    ajaxbg.fadeOut(1000);
});


</script>


</html>
