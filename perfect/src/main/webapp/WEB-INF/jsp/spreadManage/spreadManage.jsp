<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2014-6-9
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>百度推广</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/public/css/jquery-ui-1.10.4.min.css"
          rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/public/css/zTreeStyle/zTreeStyle.css"
          rel="stylesheet">
    <style type="text/css">
        body {
            padding-top: 60px;
            padding-bottom: 40px;
        }

        .sidebar-nav {
            padding: 9px 0;
        }

        @media (max-width: 980px) {
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }

        #recommendWordsTab {
            width: 100%;
            border-collapse: collapse;
            margin: 0 auto;
            table-layout: fixed;
        }

        #recommendWordsTab thead td {
            height: 32px;
            border: 1px solid #f0f0e0;
            vertical-align: middle;
            text-align: center;
            background-color: #f1f1f1
        }

        #recommendWordsTab tbody td {
            height: 25px;
            border: 1px solid #f0f0e0;
            vertical-align: middle;
            text-align: center;
            background-color: #fefefe
        }
    </style>
</head>
<body>
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid" style="margin-top: 10px">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="brand" href="#">Baidu推广</a>

            <div class="nav-collapse collapse">
                <p class="navbar-text pull-right">
                    baidu-bjperfrct2131113 <a href="#" class="navbar-link">退出</a>
                </p>
                <ul class="nav">
                    <li><a href="${pageContext.request.contextPath}/home">首页</a></li>
                    <li><a href="#accountCenter">账户中心</a></li>
                    <li><a href="#contact">财务</a></li>
                    <li>
                        <a href="${pageContext.request.contextPath}/main/convenienceManage">便捷管理</a>
                    </li>
                    <li class="active"><a href="${pageContext.request.contextPath}/main/spreadManage">推广管理</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="container-fluid">
<div class="row-fluid" style="margin-top: 10px">
<div class="span2" style="height: 500px; border: 1px solid #f1e1ff">
    <h5 style="background-color: #F0F0F0; text-align: center; margin-top: 0.5px; margin-left: 0.5px; margin-bottom: -2px">
        我的账户树
    </h5>

    <div style="font-weight: bold">
        <a id="userName" class="btn" style="height: 15px; width: 88%; border: 0" onclick=accountClick();>baidu-bjperfrct2131113</a>
    </div>

    <div id="zTree" class="ztree"
         style="table-layout: fixed; overflow: auto; height: 92%; scrollbar-base-color: #f0f0f0"></div>
</div>
<div class="span10">
<div class="row-fluid">
<div class="span12" style="background-color: #ececff; height: 72px">
    <p>账户：baidu-bjperfrct2131113</p>
    <span>账户状态：</span><span id="account_status" style="color: green">正常生效</span>&nbsp;&nbsp;&nbsp;
    <span>预算：</span><span id="budget" style="color: green">不限定</span>&nbsp;&nbsp;<a href="#">修改</a>&nbsp;&nbsp;&nbsp;
    <span>推广地域：</span><span id="spreadRegion" style="color: green">全部地域</span>&nbsp;&nbsp;&nbsp;<a
        href="#">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="#">批量下载</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#">其它设置</a>
</div>
<div class="tabbable">
<ul class="nav nav-tabs">
    <li id="campaign_nav" class="active"><a href="#tab1" data-toggle="tab">推广计划</a></li>
    <li id="adgroup_nav"><a href="#tab2" data-toggle="tab">推广单元</a></li>
    <li id="creative_nav"><a href="#tab3" data-toggle="tab">创意</a></li>
    <li id="keyword_nav"><a href="#tab4" data-toggle="tab">关键词</a></li>
</ul>
<select id="serviceStatus">
    <option value="0">全部状态</option>
    <option value="21">有效</option>
    <option value="22">处在暂停时段</option>
    <option value="23">暂停推广</option>
    <option value="24">推广计划预算不足</option>
    <option value="25">账户预算不足</option>
</select>
<input type="text" id="serviceName" name="serviceName"/>
<a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">查询 &raquo;</a>

<div class="row-fluid">
    <div class="span6">
        日期：
        <input id="startDate" type="text" style="width: 100px; margin-top: 10px"/>&nbsp;&nbsp;至&nbsp;&nbsp;
        <input id="endDate" type="text" style="width: 100px; margin-top: 10px"/>
    </div>
    <div class="span6">
        <table>
            <tr>
                <td align="right" style="width: 15%; font-size: 20px; font-weight: bold">0</td>
                <td align="right" style="width: 15%; font-size: 20px; font-weight: bold">0</td>
                <td align="right" style="width: 15%; font-size: 20px; font-weight: bold">￥0.00</td>
                <td align="right" style="width: 15%; font-size: 20px; font-weight: bold">0</td>
                <td align="right" style="width: 20%; font-size: 20px; font-weight: bold">0.00%</td>
                <td align="right" style="width: 20%; font-size: 20px; font-weight: bold">￥0.00</td>
            </tr>
            <tr>
                <td align="right" style="width: 15%; font-size: 15px">展现</td>
                <td align="right" style="width: 15%; font-size: 15px">点击</td>
                <td align="right" style="width: 15%; font-size: 15px">消费</td>
                <td align="right" style="width: 15%; font-size: 15px">转化(网页)</td>
                <td align="right" style="width: 20%; font-size: 15px">点击率</td>
                <td align="right" style="width: 20%; font-size: 15px">平均点击</td>
            </tr>
        </table>
    </div>
</div>
<div class="tab-content">
<div class="tab-pane active" id="tab1">
    <div class="row-fluid">
        <div class="span12" style="background-color: #ececfe; height: 36px">
            <a class="btn">新建计划</a>&nbsp;&nbsp;&nbsp;<a class="btn">修改预算</a>&nbsp;&nbsp;&nbsp;<a
                class="btn">修改推广时段</a>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <table id="spreadCampaign">
                <tr align="center" style="background-color: #ddf; height: 32px">
                    <td style="width:3%"><input name="getCheckbox" type="checkbox"
                                                onclick=getCheckedBox();>
                    </td>
                    <td style="width:10%">推广计划</td>
                    <td style="width:6%">优化[+]</td>
                    <td style="width:8%">动态创意状态</td>
                    <td style="width:6%">消费</td>
                    <td style="width:6%">展现</td>
                    <td style="width:8%">转化(网页)</td>
                    <td style="width:8%">转化(电话)</td>
                    <td style="width:8%">状态</td>
                    <td style="width:10%">计划移动出价比例</td>
                    <td style="width:6%">点击</td>
                    <td style="width:8%">平均点击价格</td>
                </tr>
            </table>
        </div>
    </div>
</div>
<div class="tab-pane" id="tab2">
    <div class="row-fluid">
        <div class="span12" style="background-color: #ececfe; height: 36px">
            <a class="btn">新建单元</a>&nbsp;&nbsp;&nbsp;<a class="btn">修改单元出价</a>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <table id="spreadGroup">
                <tr align="center" style="background-color: #ddf; height: 32px">
                    <td style="width:3%"><input name="getCheckbox" type="checkbox"
                                                onclick=getCheckedBox();>
                    </td>
                    <td style="width:10%">推广计划</td>
                    <td style="width:6%">优化[+]</td>
                    <td style="width:8%">动态创意状态</td>
                    <td style="width:6%">消费</td>
                    <td style="width:6%">展现</td>
                    <td style="width:8%">转化(网页)</td>
                    <td style="width:8%">转化(电话)</td>
                    <td style="width:8%">状态</td>
                    <td style="width:10%">计划移动出价比例</td>
                    <td style="width:6%">点击</td>
                    <td style="width:8%">平均点击价格</td>
                </tr>
            </table>
        </div>
    </div>
</div>
<div class="tab-pane" id="tab3">
    <div class="row-fluid">
        <div class="span12" style="background-color: #ececfe; height: 36px">
            <a class="btn">新建创意</a>&nbsp;&nbsp;&nbsp;<a class="btn">修改创意文字</a>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <table id="creativeTable">
                <tr align="center" style="background-color: #ddf; height: 32px">
                    <td style="width:3%"><input name="getCheckbox" type="checkbox"
                                                onclick=getCheckedBox();>
                    </td>
                    <td style="width:10%">推广计划</td>
                    <td style="width:6%">优化[+]</td>
                    <td style="width:8%">动态创意状态</td>
                    <td style="width:6%">消费</td>
                    <td style="width:6%">展现</td>
                    <td style="width:8%">转化(网页)</td>
                    <td style="width:8%">转化(电话)</td>
                    <td style="width:8%">状态</td>
                    <td style="width:10%">计划移动出价比例</td>
                    <td style="width:6%">点击</td>
                    <td style="width:8%">平均点击价格</td>
                </tr>
            </table>
        </div>
    </div>
</div>
<div class="tab-pane" id="tab4">
    <div class="row-fluid">
        <div class="span12" style="background-color: #ececfe; height: 36px">
            <a id="showAddKeywordModal" class="btn">添加关键词</a>&nbsp;&nbsp;&nbsp;<a class="btn">修改出价</a>&nbsp;&nbsp;&nbsp;<a
                class="btn">修改匹配模式</a>
        </div>
        <div id="addKeyword" class="modal hide fade" role="dialog"
             style="margin-top: -30px; margin-left: -450px;width: 900px; height: 600px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <label style="font-size: 15px; font-weight: bold">添加关键词</label>
            </div>
            <div id="div_1" style="height: 100%; display: block">
                <div style="margin-left :0.6%; width: 32%; height: 92%; background-color: #d1e9e9; border: 1px solid #a9a9a9">
                    <label style="text-align: center; font-size: 15px; font-weight: bold">待添加关键词</label>
                    <span style="font-size: 13px">&nbsp;每行一个关键词,每个最多20个汉字或40个英文。</span>
                    <textarea id="keywordTextarea" style="margin-left: 5px; width: 92%; height: 280px">
                    </textarea>
                    <span style="font-size: 13px">&nbsp;百度无法保证这些关键词能够提升您的推广效果;</span>
                    <span style="font-size: 13px">&nbsp;请认真审查上述关键词，确保其不违反任何法律</span>
                    <span style="font-size: 13px">&nbsp;法规,并保证您对关键词的使用不侵犯任何第三方</span>
                    <span style="font-size: 13px">&nbsp;权利。</span><br/>
                    &nbsp;<a class="btn" style="margin-bottom: 10px" onclick=autoGroup();>自动分组</a><br/>
                    &nbsp;<select id="spread_campaign" style="width: 135px; font-size: 13px">
                    <jsp:include page="getAllCampaign.jsp" flush="true"/>
                </select>
                    &nbsp;<select id="spread_adgroup" style="width: 135px; font-size: 13px">

                </select>
                    &nbsp;<a class="btn" onclick=saveWords();>保存</a>
                </div>
                <div style="margin-left: 33.6%; margin-top: -61.6%; width: 65%; height: 92%; border: 1px solid yellow">
                    <div style="margin-top: 5px; margin-left: 5px">
                        搜索关键词&nbsp;<input
                            id="searchKeyword" type="text"
                            style="width: 300px"
                            placeholder="请输入关键词..."/>
                        <a class="btn" style="margin-top: -10px" onclick=showRecommendWords();>搜索</a>

                        <div style="height: 36px; background-color: #f1f1fe">
                            <a class="btn" style="margin-top: 3px" onclick=addAllWords();>全部添加</a>
                        </div>
                        <div style="width: 100%; height: 470px; overflow-y: auto">
                            <table id="recommendWordsTab">
                                <thead>
                                <tr align="center">
                                    <td style="width:20%">关键词</td>
                                    <td style="width:40%">展现理由</td>
                                    <td style="width:15%">日均搜索量</td>
                                    <td style="width:25%">竞争激烈程度</td>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>
            </div>
            <div id="div_2" style="height: 90%; display: none; background-color: #f0f0f0">
                <div style="width: 100%; height: 50px; background-color: #d1e9e9">
                    <div style="width: 30%; height: 50px; border: 0.5px solid #d1e9e9">
                        <label style="margin-top: 15px; margin-left: -115px; font-size: 18px; text-align: center">关键词分组</label>
                    </div>
                    <div style="margin-top: -52px; margin-left:150px; width: 83%; height: 50px">
                        <label style="margin-top: 18px; font-size: 13px">
                            可通过拖拽关键词或修改保存单元调整分组结果; 系统为您新建的单元, 默认出价为1.00, 为了保证您的推广效果, 请进入单元修改。
                        </label>
                    </div>
                </div>
                <div style="width: 100%; height: 454px; background-color: #faf4ff">
                    <div class="row-fluid" style="margin-top: 2px">
                        <div id="div0" class="span3"
                             style="margin-left: 1.5px; background-color: #f1f1f1; width: 24.8%; height: 227px">
                        </div>
                        <div id="div1" class="span3"
                             style="margin-left: 1.5px; background-color: #f1f1f1; width: 24.8%; height: 227px">
                        </div>
                        <div id="div2" class="span3"
                             style="margin-left: 1.5px; background-color: #f1f1f1; width: 24.8%; height: 227px">
                        </div>
                        <div id="div3" class="span3"
                             style="margin-left: 1.5px; background-color: #f1f1f1; width: 24.8%; height: 227px">
                        </div>
                    </div>
                    <div class="row-fluid" style="margin-top: 2px">
                        <div id="div4" class="span3"
                             style="margin-left: 1.5px; background-color: #f1f1f1; width: 24.8%; height: 227px"></div>
                        <div id="div5" class="span3"
                             style="margin-left: 1.5px; background-color: #f1f1f1; width: 24.8%; height: 227px"></div>
                        <div id="div6" class="span3"
                             style="margin-left: 1.5px; background-color: #f1f1f1; width: 24.8%; height: 227px"></div>
                        <div id="div7" class="span3"
                             style="margin-left: 1.5px; background-color: #f1f1f1; width: 24.8%; height: 227px"></div>
                    </div>
                </div>
                <div style="width: 100%; height: 50px; background-color: #fcfcfc">
                    <a class="btn" style="margin-left: 20px; margin-top: 10px" onclick=saveWords();>保存</a>
                    <a class="btn" style="margin-left: 20px; margin-top: 10px" onclick=go_back();>返回</a>
                </div>

            </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <table id="keywordTable">
                <tr align="center" style="background-color: #ddf; height: 32px">
                    <td style="width:3%"><input name="getCheckbox" type="checkbox"
                                                onclick=getCheckedBox();>
                    </td>
                    <td style="width:10%">推广计划</td>
                    <td style="width:6%">优化[+]</td>
                    <td style="width:8%">动态创意状态</td>
                    <td style="width:6%">消费</td>
                    <td style="width:6%">展现</td>
                    <td style="width:8%">转化(网页)</td>
                    <td style="width:8%">转化(电话)</td>
                    <td style="width:8%">状态</td>
                    <td style="width:10%">计划移动出价比例</td>
                    <td style="width:6%">点击</td>
                    <td style="width:8%">平均点击价格</td>
                </tr>
            </table>
        </div>
    </div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>

<div id="dialog1"></div>

<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-ui-1.10.4.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script>
var basePath = "<%=basePath%>";

var status = false;     //是否进行自动分组
var a_ids = new Array();    //分组的a标签id值
var textarea_ids = new Array();     //分组的textarea文本域id值

var campaignId = "";
var adgroupId = "";
var keywordId = "";

var setting = {
    view: {
        showLine: false
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
var zNodes = "";

function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    for (var i = 0, l = childNodes.length; i < l; i++) {
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
    }
    return childNodes;
}
function beforeClick(treeId, treeNode) {
    if (treeNode.level == 0) {
        //点击的是父节点(推广计划),则应该展示其下属的推广单元数据
        //
        alert(treeNode.id);
        //
        campaignId = treeNode.id + "," + "0";
        var levelOfDetails = 5;     //单元粒度
        var reportType = 11;      //实时数据类型
        var statRange = 5;      //统计范围
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        $.getJSON(basePath + "spreadManage/realTimeRequestType", {
            levelOfDetails: levelOfDetails,
            reportType: reportType,
            statRange: statRange,
            startDate: startDate,
            endDate: endDate,
            id: campaignId
        }, function (data) {
            if (data.rows.length > 0) {
                alert(JSON.stringify(data.rows));
            }
        });
    } else if (treeNode.level == 1) {
        //点击的是子节点(推广单元),则应该展示其下属的关键词数据
        //
        alert(treeNode.id);
        //
        adgroupId = treeNode.id + "," + "1";
        var _levelOfDetails = 11;     //关键词粒度
        var _reportType = 14;
        var _statRange = 11;
        var _startDate = $("#startDate").val();
        var _endDate = $("#endDate").val();
        $.getJSON(basePath + "spreadManage/realTimeRequestType", {
            levelOfDetails: _levelOfDetails,
            reportType: _reportType,
            statRange: _statRange,
            startDate: _startDate,
            endDate: _endDate,
            id: adgroupId
        }, function (data) {
            if (data.rows.length > 0) {
                alert(JSON.stringify(data.rows));
            }
        });
    }
}
var log, className = "dark";
function beforeAsync(treeId, treeNode) {
    className = (className === "dark" ? "" : "dark");
    showLog("[ " + getTime() + " beforeAsync ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
    return true;
}
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
    showLog("[ " + getTime() + " onAsyncError ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
}
function onAsyncSuccess(event, treeId, treeNode, msg) {
    showLog("[ " + getTime() + " onAsyncSuccess ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
}

function showLog(str) {
    if (!log) log = $("#log");
    log.append("<li class='" + className + "'>" + str + "</li>");
    if (log.children("li").length > 8) {
        log.get(0).removeChild(log.children("li")[0]);
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

$(function () {
    //绑定键盘回车事件
    $('#searchKeyword').bind('keypress', function (event) {
        if (event.keyCode == "13") {
            $("#recommendWordsTab tbody").empty();
            showRecommendWords();
        }
    });

    $("#spread_campaign").change(function () {
        var campaignId = $("#spread_campaign option:selected").val();
        $.getJSON(basePath + "spreadManage/getAdgroupByCampaignId",
                {campaignId: campaignId},
                function (data) {
                    var adgroups = "", datas = data.rows;
                    adgroups += "<option value='' selected='selected'>请选择推广单元</option>";
                    for (var i = 0, l = datas.length; i < l; i++)
                        adgroups += "<option value=" + datas[i].adgroupId + ">" + datas[i].adgroupName + "</option>";
                    $("#spread_adgroup").empty();
                    $("#spread_adgroup").append(adgroups);
                });
    });

    $("#showAddKeywordModal").click(function () {
        //$("#keywordTextarea").text("");
        //$("#searchKeyword").val("");
        //$("#recommendWordsTab tbody").empty();
        $("#addKeyword").modal("show");
    });

    //获取账户树数据
    $.ajax({
        url: basePath + "spreadManage/get_tree",
        type: "POST",
        dataType: "json",
        async: false,
        success: function (data, textStatus, jqXHR) {
            zNodes = data.tree;
        }
    });
    //加载树
    $.fn.zTree.init($("#zTree"), setting, zNodes);
    //日历插件
    $("#startDate").datepicker({
        changeYear: true,
        changeMonth: true,
        numberOfMonths: 1,
        showButtonPanel: true
    });
    $("#endDate").datepicker({
        changeYear: true,
        changeMonth: true,
        numberOfMonths: 1,
        showButtonPanel: true
    });

});

var getCheckedBox = function () {
    $("input[name=spreadCampaignRow]:checkbox").each(function () {
        if (true)
            $(this).attr("checked", true);
        else
            $(this).attr("checked", false);
    });
};

var accountClick = function () {
    var serviceStatus = $("#serviceStatus option:selected").val();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    var serviceName = $("#serviceName").val();
    $.getJSON(basePath + "spreadManage/realTimeRequestType",
            {
                serviceStatus: serviceStatus,
                serviceName: serviceName,
                startDate: startDate,
                endDate: endDate
            },
            function (data) {
                alert(JSON.stringify(data.rows));
            });
};

//显示搜索出的推荐词
var showRecommendWords = function () {
    $("#recommendWordsTab tbody").empty();
    var seedWord = $('#searchKeyword').val();
    $.ajax({
        url: basePath + 'spreadManage/getKRResult',
        type: "POST",
        data: {"aSeedWord": window.encodeURIComponent(seedWord)},
        dataType: "json",
        async: false,
        success: function (data, textStatus, jqXHR) {
            if (data.rows.length > 0) {
                $.each(data.rows, function (i, item) {
                    var newTr = "<tr id='" + (65535 + i) + "' align='center'>" +
                            "<td><a href='javascript:addToTextarea(" + (65535 + i) + ");'>" + item.word +
                            "</a></td><td>" + item.flag1 +
                            "</td><td>" + item.broadPV +
                            "</td><td>" + item.competition +
                            "</td></tr>";
                    $("#recommendWordsTab tbody").append(newTr);
                })
            }
        }
    });
};

//添加全部关键词到左侧textarea
var addAllWords = function () {
    var trs = $("#recommendWordsTab tbody").find("tr");
    trs.each(function (i) {
        if (i == 0) {
            var str = $("#keywordTextarea").text();
            if (str == null || str.trim().length == 0)
                $("#keywordTextarea").text($(this).find("td").eq(0).text());
            else
                $("#keywordTextarea").text(str + "\n" + $(this).find("td").eq(0).text());
            return true;
        }
        var _str = $("#keywordTextarea").text();
        $("#keywordTextarea").text(_str + "\n" + $(this).find("td").eq(0).text());
    });
};

//添加关键词到左侧textarea
var addToTextarea = function (rowId) {
    var _word = $("#" + rowId).find("td").eq(0).text();
    var str = $("#keywordTextarea").text();
    if (str == null || str.trim().length == 0)
        $("#keywordTextarea").text(_word);
    else
        $("#keywordTextarea").text(str + "\n" + _word);
};

//自动分组
var autoGroup = function () {
    var str = $("#keywordTextarea").text();
    if (str == null || str.trim().length == 0) {
        alert("没有合法的关键词可供分组");
        return false;
    }
    var _str = str.split("\n");
    var seedWords = "";
    for (var i = 0, l = _str.length; i < l; i++) {
        if (i == 0) {
            seedWords += _str[i];
            continue;
        }
        seedWords += ";" + _str[i];
    }
    //获取推广计划ID
    var campaignId = $("#spread_campaign option:selected").val();
    //获取推广单元ID
    var adgroupId = $("#spread_adgroup option:selected").val();
    $.ajax({
        url: basePath + "spreadManage/autoGroup",
        type: "POST",
        data: {
            "seedWords": window.encodeURIComponent(seedWords),
            "campaignId": campaignId,
            "adgroupId": adgroupId
        },
        async: false,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            $.each(data.rows, function (i, item) {
                var _a = "<a id='a_" + i + "' href='javascript:;'>新建计划</a>&gt;<a href='javascript:;'>" + item.adgroupName + "</a>";
                $("#div" + i).append(_a);
                a_ids.push("a_" + i);
                var textarea_id = "textarea" + i;
                var _textarea = "<textarea id='" + textarea_id + "' style='height: 86%'></textarea>";
                $("#div" + i).append(_textarea);
                textarea_ids.push(textarea_id);
                var _keywords = item.keywords;
                for (var j = 0, l1 = _keywords.length; j < l1; j++) {
                    var str = $("#" + textarea_id).text();
                    if (str == null || str.trim().length == 0)
                        $("#" + textarea_id).text(_keywords[j]);
                    else
                        $("#" + textarea_id).text(str + "\n" + _keywords[j]);
                }
            });
            status = true;
            $("#div_1").hide();
            $("#div_2").show();
        }
    });
};

var saveWords = function () {
    if (status) {
        //自动分组
        //先在后台新建计划和单元
        //计划名称
        var campaignName = $("#" + a_ids[0]).html();
        var newCampaignId = "";     //新建计划ID
        $.ajax({
            url: basePath + "spreadManage/getNewCampaignId",
            type: "GET",
            async: false,
            data: {campaignName: window.encodeURIComponent(campaignName)},
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                newCampaignId = data.new_campaignId;
            }
        });

        var adgroupNames = [];
        for (var _j = 0, _m = a_ids.length; _j < _m; _j++)
            adgroupNames.push(window.encodeURIComponent($("#" + a_ids[_j]).next().html()));
        var newAdgroupIds = new Array();        //新建的单元ID数组
        $.ajax({
            url: basePath + "spreadManage/getNewAdgroupId",
            type: "GET",
            async: false,
            data: {newCampaignId: newCampaignId,
                adgroupNames: adgroupNames},
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                $.each(data.rows, function (i, item) {
                    newAdgroupIds.push(item);
                });
            }
        });

        //生成JSON对象数组
        var dataJson = [];
        for (var c = 0, s = newAdgroupIds.length; c < s; c++) {
            var words = $("#" + textarea_ids[c]).text().split("\n");//关键词数组
            for (var d = 0, t = words.length; d < t; d++) {
                var keywordType = {};
                keywordType["adgroupId"] = newAdgroupIds[c];
                keywordType["keyword"] = words[d];
                dataJson.push(keywordType);
            }
        }
        $.ajax({
            url: basePath + "spreadManage/addKeywords",
            type: "POST",
            async: false,
            data: {
                "keywords": JSON.stringify(dataJson)
            },
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                if (data.flag) {
                    alert('添加成功!');
                    $("#addKeyword").modal("hide");
                }
            }
        });
    } else {
        //没有进行自动分组
        var str = $("#keywordTextarea").text();
        if (str == null || str.trim().length == 0) {
            alert("没有合法的关键词!");
            return false;
        }

        var _campaign = $("#spread_campaign option:selected").val();
        if (_campaign.trim().length == 0) {
            alert("请选择推广计划!");
            return false;
        }
        var _adgroup = $("#spread_adgroup option:selected").val();
        if (_adgroup.trim().length == 0) {
            alert("请选择推广单元!");
            return false;
        }

        //获取推广计划ID
        var campaignId = $("#spread_campaign option:selected").val();
        //获取推广单元ID
        var adgroupId = $("#spread_adgroup option:selected").val();

        var words1 = str.split("\n");
        var dataJson1 = [];
        for (var k = 0, h = words1.length; k < h; k++) {
            var keywordType1 = {};
            keywordType1["adgroupId"] = adgroupId;
            keywordType1["keyword"] = words1[k];
            dataJson1.push(keywordType1);
        }
        $.ajax({
            url: basePath + "spreadManage/addKeywords",
            type: "POST",
            async: false,
            data: {
                "keywords": JSON.stringify(dataJson1)
            },
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                if (data.flag) {
                    alert('添加成功!');
                    $("#addKeyword").modal("hide");
                }
            }
        });
    }
};

var go_back = function () {
    $("#div_2").hide();
    $("#div_1").show();
    status = false;
};
</script>
</body>
</html>