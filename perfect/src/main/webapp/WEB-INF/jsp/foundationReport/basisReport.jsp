<%--
  Created by IntelliJ IDEA.
  User: SubDong
  Date: 2014/8/18
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">
    <script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/4.0.1/highcharts.js"></script>
    <script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/4.0.1/modules/exporting.js"></script>

    <style type="text/css">
        .tab_box {
            padding: 0px;
        }

        .list01_under3 {
            padding: 20px 2%;
        }

        .list2 table tr td ul li {
            width: 11.5%;
        }
    </style>
</head>
<body>
<jsp:include page="../homePage/pageBlock/head.jsp"/>
<div class="concent over">
<jsp:include page="../homePage/pageBlock/nav.jsp"/>
<div class="mid over fr">
<div class="on_title over">
    <a href="#">
        数据报告
    </a>
    &nbsp;&nbsp;>&nbsp;&nbsp;<span>基础报告</span>
</div>
<div id="tab">
<ul class="tab_menu">
    <li class="selected">账户报告</li>
    <li>明细数据</li>
</ul>
<div class="tab_box" style=" padding:inherit;">
<div class="containers  over">
    <div class="list01_under3 over">
        <div class="list01_top over"><Span>基础统计</Span><a href="#" class="question"></a>
        </div>
        <div class="list3 wd">
            <table border="0" cellspacing="0" cellspacing="0" width="100%">
                <thead>
                <tr class="list2_top">

                    <td>&nbsp;<span>时间</span> <b>
                        <p>
                            <input class="one" type="button"
                                   onclick="javascript:fieldName = 'date';sort = 0;accountBasisReport();">
                        </p>

                        <p>
                            <input class="two" type="button"
                                   onclick="javascript:fieldName = 'date';sort = 1;accountBasisReport();">
                        </p>
                    </b></td>
                    <td>&nbsp;<span>展现量</span><b>
                        <p>
                            <input class="one" type="button"
                                   onclick="javascript:fieldName = 'pcImpression';sort = 0;accountBasisReport();">
                        </p>

                        <p>
                            <input class="two" type="button"
                                   onclick="javascript:fieldName = 'pcImpression';sort = 1;accountBasisReport();">
                        </p>
                    </b><a href="#" class="question"></a></td>
                    <td>&nbsp;<span>点击量</span><b>
                        <p>
                            <input class="one" type="button"
                                   onclick="javascript:fieldName = 'pcClick';sort = 0;accountBasisReport();">
                        </p>

                        <p>
                            <input class="two" type="button"
                                   onclick="javascript:fieldName = 'pcClick';sort = 1;accountBasisReport();">
                        </p>
                    </b></td>
                    <td>&nbsp;<span>消费</span><b>
                        <p>
                            <input class="one" type="button"
                                   onclick="javascript:fieldName = 'pcCost';sort = 0;accountBasisReport();">
                        </p>

                        <p>
                            <input class="two" type="button"
                                   onclick="javascript:fieldName = 'pcCost';sort = 1;accountBasisReport();">
                        </p>
                    </b><a href="#" class="question"></a></td>
                    <td>&nbsp;<span>点击率</span><b>
                        <p>
                            <input class="one" type="button"
                                   onclick="javascript:fieldName = 'pcCtr';sort = 0;accountBasisReport();">
                        </p>

                        <p>
                            <input class="two" type="button"
                                   onclick="javascript:fieldName = 'pcCtr';sort = 1;accountBasisReport();">
                        </p>
                    </b><a href="#" class="question"></a></td>
                    <td>&nbsp;<span>平均点击价格</span><b>
                        <p>
                            <input class="one" type="button"
                                   onclick="javascript:fieldName = 'pcCpc';sort = 0;accountBasisReport();">
                        </p>

                        <p>
                            <input class="two" type="button"
                                   onclick="javascript:fieldName = 'pcCpc';sort = 1;accountBasisReport();">
                        </p>
                    </b><a href="#" class="question"></a></td>
                    <td>&nbsp;<span>转化(网页)</span><b>
                        <p>
                            <input class="one" type="button"
                                   <%--onclick="javascript:fieldName = 'pcConversion';sort = 0;accountBasisReport();"--%>>
                        </p>

                        <p>
                            <input class="two" type="button"
                                   <%--onclick="javascript:fieldName = 'pcConversion';sort = 1;accountBasisReport();"--%>>
                        </p>
                    </b> <a href="#" class="question"></a></td>
                    <td>&nbsp;<span>转化(商桥)</span><b>
                        <p>
                            <input class="one" type="button">
                        </p>

                        <p>
                            <input class="two" type="button">
                        </p>
                    </b><a href="#" class="question"></a></td>


                </tr>
                </thead>
                <tbody id="basisAccount">

                </tbody>
            </table>
        </div>
    </div>
    <div class="number_concent over">
        <div class="list01_top over"><Span>账户报告</Span>
        </div>
        <div class="shuju_detali over">
            <ul>
                <li>选择时间范围：<input type="text" class="time_input" placeholder="2014-01-30 至 2014-01-31"> <input
                        type="image" src="public/img/date.png"><input type="checkbox" style="margin:6px 3px 0px 5px; ">比较范围<input
                        type="text" class="time_input"><input type="image" src="public/img/date.png"></li>
                <li>选择推广设备：<a href="#" class="current">全部</a><span>|</span><a href="#">计算机</a><span>|</span><a href="#">移动设备</a>
                </li>
                <li>选择时间单位：<a href="#" class="current">默认</a><span>|</span><a href="time.html">分日</a><span>|</span><a
                        href="#">分周</a><span>|</span><a href="#">分月</a></li>
            </ul>
            <a href="#" class="become"> 生成报告</a>
        </div>
    </div>
    <div class="list01_under3 over">
        <div class="list03 wd">
            <table border="0" cellspacing="0" cellspacing="0">
                <tr class="list03_top">
                    <td>&nbsp;时间</td>
                    <td>&nbsp;展现</td>
                    <td>&nbsp;点击</td>
                    <td>&nbsp;消费</td>
                    <td>&nbsp;转化(页面)</td>
                    <td>&nbsp;转化(商桥)</td>
                    <td>&nbsp;转化(电话)</td>
                    <td>&nbsp;点击率</td>
                    <td>&nbsp;平均点击价格</td>
                </tr>
                <tr>
                    <td>&nbsp;2014-01-30至2014-01-31</td>
                    <td>&nbsp;<span>530</span><span class="green_arrow wd3"></span><span><b>60.18%</b></span></td>
                    <td>&nbsp;<span>530</span><span class="green_arrow wd3"></span><span><b>60.18%</b></span></td>
                    <td>&nbsp;<span>530</span><span class="green_arrow wd3"></span><span><b>60.18%</b></span></td>
                    <td>&nbsp;0 &nbsp;&nbsp;&nbsp;<b>0.00%</b></td>
                    <td>&nbsp;0 &nbsp;&nbsp;&nbsp;<b>0.00%</b></td>
                    <td>&nbsp;0 &nbsp;&nbsp;&nbsp;<b>0.00%</b></td>
                    <td>&nbsp;<span>530</span><span class="red_arrow wd3"></span><span><strong>60.18%</strong></span>
                    </td>
                    <td>&nbsp;<span>530</span><span class="green_arrow wd3"></span><span><b>60.18%</b></span></td>
                </tr>
                <tr>
                    <td colspan="9">&nbsp;<img src="public/img/vs.png"></td>
                </tr>
                <tr>
                    <td>&nbsp;2014-01-30至2014-01-31</td>
                    <td>&nbsp;1331</td>
                    <td>&nbsp;1331</td>
                    <td>&nbsp;1331</td>
                    <td>&nbsp;1331</td>
                    <td>&nbsp;1331</td>
                    <td>&nbsp;1331</td>
                    <td>&nbsp;1331</td>
                    <td>&nbsp;1331</td>
                </tr>
            </table>
        </div>
    </div>
</div>
<div class="containers hides over">
    <div class="number_concent over">
        <div class="list01_top over"><Span>明细数据</Span>
        </div>
        <div class="shuju_detali over">
            <ul>
                <li>选择时间范围：
                    <input type="text" class="time_input" placeholder="2014-01-30 至 2014-01-31">
                    <input type="image" src="public/img/date.png">
                </li>
                <li id="reportType">选择报告类型：
                    <a href="javascript:" class="current" cname="1">结构报告</a><span>|</span>
                    <a href="javascript:" cname="2">关键词报告</a><span>|</span>
                    <a href="javascript:" cname="3">创意报告</a><span>|</span>
                    <a href="javascript:">附加创意报告</a><span>|</span>
                    <a href="diyu.html" cname="4">分地域报告</a>
                </li>
                <li id="device">选择推广设备：
                    <a href="javascript:" class="current" cname="0">全部</a><span>|</span>
                    <a href="javascript:" cname="1">计算机</a><span>|</span>
                    <a href="javascript:" cname="2">移动设备</a>
                </li>
                <li id="dateLi">选择时间单位：
                    <a href="javascript:" class="current" cname="0">默认</a><span>|</span>
                    <a href="javascript:" cname="1">分日</a><span>|</span>
                    <a href="javascript:" cname="2">分周</a><span>|</span>
                    <a href="javascript:" cname="3">分月</a>
                </li>
            </ul>
            <a href="javascript:" id="shengc" class="become"> 生成报告</a>
        </div>
        <input type="hidden" id="reportTypes" value="1">
        <input type="hidden" id="devices" value="0">
        <input type="hidden" id="dateLis" value="0">
    </div>
    <div class="contant over">
        <div class="download over fr"><a href="#">下载全部</a></div>
        <div class="list01_under3 over">

            <div class="list3 wd" style="overflow-x: auto; width: 100%;">
                <table border="0" cellspacing="0" cellspacing="0" style="width:1600px;">
                    <thead id="shujuthead">

                    </thead>
                    <tbody id="shuju">

                    </tbody>
                </table>
            </div>
            <div class="tubiao2 over">
                <div id="containerLegend"></div>
                <div id="container" style="width:100%;height:400px"></div>
            </div>

        </div>
    </div>
</div>
<jsp:include page="../homePage/pageBlock/footer.jsp"/>
</div>
</div>
</div>
</div>
</body>
<script type="text/javascript">
/*初始化数据变量*/
var dataOne = "";
var dataTow = "";

//日期
var t_date = new Array();
var dateInterval = 0;
var colorOne = "#4572A7";
var colorTow = "#40BC2A";
//展现
var t_impr = new Array();
var ts_impr = 0;
//点击数
var t_clicks = new Array();
var ts_clicks = 0;
//消费
var t_cost = new Array();
var ts_cost = 0;
//点击率
var t_ctr = new Array();
var ts_ctr = 0;
//平均点击价格
var t_cpc = new Array();
var ts_cpc = 0;
//转化
var t_conversion = new Array();
var ts_conversion = 0;


var fieldName ='date';

var sort = 0
$(document).ready(function () {
    var $tab_li = $('.tab_menu li');
    $('.tab_menu li').click(function () {
        $(this).addClass('selected').siblings().removeClass('selected');
        var index = $tab_li.index(this);
        $('div.tab_box > div').eq(index).show().siblings().hide();
    });
    $("#reportType>a").click(function () {
        $("#reportType>a").removeClass("current");
        $(this).addClass("current");
        $("#reportTypes").val($(this).attr("cname"));
    });
    $("#device>a").click(function () {
        $("#device>a").removeClass("current");
        $(this).addClass("current");
        $("#devices").val($(this).attr("cname"));
    });
    $("#dateLi>a").click(function () {
        $("#dateLi>a").removeClass("current");
        $(this).addClass("current");
        $("#dateLis").val($(this).attr("cname"));
    })

    //初始化基础统计
    accountBasisReport();

    /**
     *生成报告按钮点击
     */
    $("#shengc").click(function () {
        $("#containerLegend").empty();
        $("#shujuthead").empty();
        $('#container').empty();
        $("#shuju").empty();

        $("#shuju").html("报告生成中，请稍后。。。。。");
        var reportTypes = $("#reportTypes").val();
        var devices = $("#devices").val();
        var dateLis = $("#dateLis").val();
        $.ajax({
            url: "/account/structureReport",
            type: "GET",
            dataType: "json",
            data: {
                startDate: "2014-08-01",
                endDate: "2014-08-03",
                reportType: reportTypes,
                devices: devices,
                dateType: dateLis
            },
            success: function (data) {
                $("#shujuthead").empty();
                $("#shuju").empty();
                $('#container').empty();
                var html_head = "";
                if (data.rows.length > 0) {
                    t_date.length = 0;
                    t_impr.length = 0;
                    t_clicks.length = 0;
                    t_cost.length = 0;
                    t_ctr.length = 0;
                    t_cpc.length = 0;
                    t_conversion.length = 0;
                    switch (reportTypes) {
                        case "1":
                            html_head = "<tr class='list2_top'><td>&nbsp;<span>时间</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>推广计划</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>推广单元</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>展现量</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>点击量</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>消费</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>点击率</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>平均点击价格</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>转化(网页)</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>转化(商桥)</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>转化(电话))</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td></td></tr>";
                            break;
                        case "2":
                            html_head = "<tr class='list2_top'><td>&nbsp;<span>时间</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>推广计划</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>推广单元</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>关键字</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>展现量</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>点击量</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>消费</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>点击率</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>平均点击价格</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>转化(网页)</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>转化(商桥)</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>转化(电话))</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td></td></tr>";
                            break;
                        case "3":
                            html_head = "<tr class='list2_top'><td>&nbsp;<span>时间</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>推广计划</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>推广单元</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>创意</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>展现量</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>点击量</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>消费</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>点击率</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>平均点击价格</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>转化(网页)</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>转化(商桥)</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>转化(电话))</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td></td></tr>";
                            break;
                        case "4":
                            html_head = "<tr class='list2_top'><td>&nbsp;<span>时间</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>推广计划</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>推广单元</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>地域</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>展现量</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>点击量</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>消费</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>点击率</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>平均点击价格</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>转化(网页)</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>转化(商桥)</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                    "<td>&nbsp;<span>转化(电话))</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td></td></tr>";
                            break;
                    }
                    $("#shujuthead").append(html_head);
                    $.each(data.rows, function (i, item) {
                        ts_impr = 0;
                        ts_clicks = 0;
                        ts_cost = 0;
                        ts_conversion = 0;
                        $.each(data[item.date], function (i, items) {
                            if (i < 10) {
                                var html_Go = "";
                                switch (reportTypes) {
                                    case "1":
                                        if (i % 2 == 0) {
                                            if (devices == 2) {
                                                html_Go = "<tr class='list2_box1'><td>" + item.date + "</td>"
                                                        + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td>"
                                                        + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                                        + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                            } else {
                                                html_Go = "<tr class='list2_box1'><td>" + item.date + "</td>"
                                                        + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td>"
                                                        + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                                        + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                            }
                                        } else {
                                            if (devices == 2) {
                                                html_Go = "<tr class='list2_box2'><td>" + item.date + "</td>"
                                                        + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td>"
                                                        + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                                        + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                            } else {
                                                html_Go = "<tr class='list2_box2'><td>" + item.date + "</td>"
                                                        + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td>"
                                                        + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                                        + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                            }
                                        }
                                        break;
                                    case "2":
                                        if (i % 2 == 0) {
                                            if (devices == 2) {
                                                html_Go = "<tr class='list2_box1'><td>" + item.date + "</td>"
                                                        + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.keywordName + "</td>"
                                                        + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                                        + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                            } else {
                                                html_Go = "<tr class='list2_box1'><td>" + item.date + "</td>"
                                                        + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.keywordName + "</td>"
                                                        + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                                        + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                            }
                                        } else {
                                            if (devices == 2) {
                                                html_Go = "<tr class='list2_box2'><td>" + item.date + "</td>"
                                                        + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.keywordName + "</td>"
                                                        + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                                        + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                            } else {
                                                html_Go = "<tr class='list2_box2'><td>" + item.date + "</td>"
                                                        + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.keywordName + "</td>"
                                                        + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                                        + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                            }
                                        }
                                        break;
                                    case "3":
                                        if (i % 2 == 0) {
                                            if (devices == 2) {
                                                html_Go = "<tr class='list2_box1'><td>" + item.date + "</td>"
                                                        + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td><label style='color: dodgerblue'>" + items.creativeTitle + "</label><br/><label style='font-size: 8px'>" + items.description1 + "</label></td>"
                                                        + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                                        + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                            } else {
                                                html_Go = "<tr class='list2_box1'><td>" + item.date + "</td>"
                                                        + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td><label style='color: dodgerblue'>" + items.creativeTitle + "</label><br/><label style='font-size: 8px'>" + items.description1 + "</label></td>"
                                                        + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                                        + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                            }
                                        } else {
                                            if (devices == 2) {
                                                html_Go = "<tr class='list2_box2'><td>" + item.date + "</td>"
                                                        + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td><label style='color: dodgerblue'>" + items.creativeTitle + "</label><br/><label style='font-size: 8px'>" + items.description1 + "</label></td>"
                                                        + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                                        + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                            } else {
                                                html_Go = "<tr class='list2_box2'><td>" + item.date + "</td>"
                                                        + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td><label style='color: dodgerblue'>" + items.creativeTitle + "</label><br/><label style='font-size: 8px'>" + items.description1 + "</label></td>"
                                                        + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                                        + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                            }
                                        }
                                        break;
                                    case "4":
                                        if (i % 2 == 0) {
                                            if (devices == 2) {
                                                html_Go = "<tr class='list2_box1'><td>" + item.date + "</td>"
                                                        + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.regionName + "</td>"
                                                        + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                                        + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                            } else {
                                                html_Go = "<tr class='list2_box1'><td>" + item.date + "</td>"
                                                        + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.regionName + "</td>"
                                                        + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                                        + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                            }
                                        } else {
                                            if (devices == 2) {
                                                html_Go = "<tr class='list2_box2'><td>" + item.date + "</td>"
                                                        + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.regionName + "</td>"
                                                        + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                                        + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                            } else {
                                                html_Go = "<tr class='list2_box2'><td>" + item.date + "</td>"
                                                        + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.regionName + "</td>"
                                                        + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                                        + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                            }
                                        }
                                        break;
                                }
                                $("#shuju").append(html_Go);
                            }
                            //曲线图数据计算
                            if (devices == 2) {
                                ts_impr = ts_impr + items.mobileImpression;
                                ts_clicks = ts_clicks + items.mobileClick;
                                ts_cost = ts_cost + items.mobileCost;
                                ts_conversion = ts_conversion + items.mobileConversion;
                            } else {
                                ts_impr = ts_impr + items.pcImpression;
                                ts_clicks = ts_clicks + items.pcClick;
                                ts_cost = ts_cost + items.pcCost;
                                ts_conversion = ts_conversion + items.pcConversion;
                            }
                        });
                        if (dateLis != 0) {
                            $("#containerLegend").empty();
                            /*初始化曲线图所用需求*/
                            $("#containerLegend").html("<div class='tu_top over'><ul><li>展示曲线</li>"
                                    + "<li><input name='check' cname='impr' xname='' type='checkbox' checked='checked'><span class='blue' ></span><b>展现</b></li>"
                                    + "<li><input name='check' cname='clicks' xname='' type='checkbox' checked='checked'><span class='green'></span><b>点击</b></li>"
                                    + "<li><input name='check' cname='cost' xname='' type='checkbox'><span class='red'></span><b>消费</b></li>"
                                    + "<li><input name='check' cname='ctr' xname='' type='checkbox'><span class='blue2'></span><b>点击率</b></li>"
                                    + "<li><input name='check' cname='cpc' xname='' type='checkbox'><span class='green2'></span><b>平均点击价格</b></li>"
                                    + "<li><input name='check' cname='conv' xname='' type='checkbox'><span class='yellow'></span><b>转化</b></li><li><b style='color: red'>最多只能同时选择两项</b></li></ul></div>");

                            //曲线图数据装载
                            t_date.push(item.date);
                            t_impr.push(ts_impr);
                            t_clicks.push(ts_clicks);
                            t_cost.push(Math.round(ts_cost * 100) / 100);
                            t_ctr.push(Math.round((ts_clicks / ts_impr) * 10000) / 100);
                            t_cpc.push(Math.round((ts_cost / ts_clicks) * 10000) / 100);
                            t_conversion.push(ts_conversion);

                            dataOne = {
                                name: '展现',
                                color: '#4572A7',
                                type: 'spline',
                                yAxis: 1,
                                data: t_impr,
                                tooltip: {
                                    valueSuffix: ' 次'
                                }
                            }
                            dataTow = {
                                name: '点击',
                                color: '#40BC2A',
                                type: 'spline',
                                data: t_clicks,
                                tooltip: {
                                    valueSuffix: '次'
                                }
                            }
                            $("input[cname=impr]").attr("xname", "dataOne");
                            $("input[cname=clicks]").attr("xname", "dataTow");
                            curve();
                        } else {
                            $("#containerLegend").empty();
                        }
                    });
                }
                if (data.rows.length > 10) {
                    dateInterval = 5;
                }
            }
        });
    });

    $("body").on("click", "input[name=check]", function () {
        var name = $(this).attr("cname");
        if ($("input[type='checkbox']:checked").length <= 2) {
            if (name == "impr") {
                if ($(this).is(':checked')) {
                    if (dataOne == "") {
                        $(this).attr("xname", "dataOne");
                        colorOne = "#078CC7";
                        dataOne = {
                            name: '展现',
                            color: '#078CC7',
                            type: 'spline',
                            yAxis: 1,
                            data: t_impr,
                            tooltip: {
                                valueSuffix: ' 次'
                            }
                        };
                        curve();
                    } else if (dataTow == "") {
                        $(this).attr("xname", "dataTow");
                        colorTow = "#078CC7"
                        dataTow = {
                            name: '展现',
                            color: '#078CC7',
                            type: 'spline',
                            data: t_impr,
                            tooltip: {
                                valueSuffix: ' 次'
                            }
                        };
                        curve();
                    }
                } else {
                    if ($(this).attr("xname") == "dataOne") {
                        dataOne = "";
                        $(this).attr("xname", "");
                        curve();
                    } else if ($(this).attr("xname") == "dataTow") {
                        dataTow = "";
                        $(this).attr("xname", "");
                        curve();
                    }
                }
            } else if (name == "clicks") {
                if ($(this).is(':checked')) {
                    if (dataOne == "") {
                        $(this).attr("xname", "dataOne");
                        colorOne = "#40BC2A";
                        dataOne = {
                            name: '点击',
                            color: '#40BC2A',
                            type: 'spline',
                            yAxis: 1,
                            data: t_clicks,
                            tooltip: {
                                valueSuffix: ' 次'
                            }
                        };
                        curve();
                    } else if (dataTow == "") {
                        $(this).attr("xname", "dataTow");
                        colorTow = "#40BC2A";
                        dataTow = {
                            name: '点击',
                            color: '#40BC2A',
                            type: 'spline',
                            data: t_clicks,
                            tooltip: {
                                valueSuffix: ' 次'
                            }
                        };
                        curve();
                    }
                } else {
                    if ($(this).attr("xname") == "dataOne") {
                        dataOne = "";
                        $(this).attr("xname", "");
                        curve();
                    } else if ($(this).attr("xname") == "dataTow") {
                        dataTow = "";
                        $(this).attr("xname", "");
                        curve();
                    }
                }
            } else if (name == "cost") {
                if ($(this).is(':checked')) {
                    if (dataOne == "") {
                        $(this).attr("xname", "dataOne");
                        colorOne = "#F1521B";
                        dataOne = {
                            name: '消费',
                            color: '#F1521B',
                            type: 'spline',
                            yAxis: 1,
                            data: t_cost,
                            tooltip: {
                                valueSuffix: ' ￥'
                            }
                        };
                        curve();
                    } else if (dataTow == "") {
                        $(this).attr("xname", "dataTow");
                        colorTow = "#F1521B";
                        dataTow = {
                            name: '消费',
                            color: '#F1521B',
                            type: 'spline',
                            data: t_cost,
                            tooltip: {
                                valueSuffix: ' ￥'
                            }
                        };
                        curve();
                    }
                } else {
                    if ($(this).attr("xname") == "dataOne") {
                        dataOne = "";
                        $(this).attr("xname", "");
                        curve();
                    } else if ($(this).attr("xname") == "dataTow") {
                        dataTow = "";
                        $(this).attr("xname", "");
                        curve();
                    }
                }
            } else if (name == "ctr") {
                if ($(this).is(':checked')) {
                    if (dataOne == "") {
                        $(this).attr("xname", "dataOne");
                        colorOne = "#26CAE5";
                        dataOne = {
                            name: '点击率',
                            color: '#26CAE5',
                            type: 'spline',
                            yAxis: 1,
                            data: t_ctr,
                            tooltip: {
                                valueSuffix: ' %'
                            }
                        };
                        curve();
                    } else if (dataTow == "") {
                        $(this).attr("xname", "dataTow");
                        colorTow = "#26CAE5";
                        dataTow = {
                            name: '点击率',
                            color: '#26CAE5',
                            type: 'spline',
                            data: t_ctr,
                            tooltip: {
                                valueSuffix: ' %'
                            }
                        };
                        curve();
                    }
                } else {
                    if ($(this).attr("xname") == "dataOne") {
                        dataOne = "";
                        $(this).attr("xname", "");
                        curve();
                    } else if ($(this).attr("xname") == "dataTow") {
                        dataTow = "";
                        $(this).attr("xname", "");
                        curve();
                    }
                }
            } else if (name == "cpc") {
                if ($(this).is(':checked')) {
                    $(this).attr("xname", "dataOne");
                    colorOne = "#60E47E";
                    if (dataOne == "") {
                        dataOne = {
                            name: '平均点击价格',
                            color: '#60E47E',
                            type: 'spline',
                            yAxis: 1,
                            data: t_cpc,
                            tooltip: {
                                valueSuffix: ' ￥'
                            }
                        };
                        curve();
                    } else if (dataTow == "") {
                        $(this).attr("xname", "dataTow");
                        colorTow = "#60E47E";
                        dataTow = {
                            name: '平均点击价格',
                            color: '#60E47E',
                            type: 'spline',
                            data: t_cpc,
                            tooltip: {
                                valueSuffix: ' ￥'
                            }
                        };
                        curve();
                    }
                } else {
                    if ($(this).attr("xname") == "dataOne") {
                        dataOne = "";
                        $(this).attr("xname", "");
                        curve();
                    } else if ($(this).attr("xname") == "dataTow") {
                        dataTow = "";
                        $(this).attr("xname", "");
                        curve();
                    }
                }
            } else if (name == "conv") {
                if ($(this).is(':checked')) {
                    $(this).attr("xname", "dataOne");
                    colorOne = "#DEDF00";
                    if (dataOne == "") {
                        dataOne = {
                            name: '转化',
                            color: '#DEDF00',
                            type: 'spline',
                            yAxis: 1,
                            data: t_conversion,
                            tooltip: {
                                valueSuffix: ''
                            }
                        };
                        curve();
                    } else if (dataTow == "") {
                        $(this).attr("xname", "dataTow");
                        colorTow = "#DEDF00";
                        dataTow = {
                            name: '转化',
                            color: '#DEDF00',
                            type: 'spline',
                            data: t_conversion,
                            tooltip: {
                                valueSuffix: ''
                            }
                        };
                        curve();
                    }
                } else {
                    if ($(this).attr("xname") == "dataOne") {
                        dataOne = "";
                        $(this).attr("xname", "");
                        curve();
                    } else if ($(this).attr("xname") == "dataTow") {
                        dataTow = "";
                        $(this).attr("xname", "");
                        curve();
                    }
                }
            }
        } else {
            $(this).attr("checked", false);
        }
    });

    var curve = function () {
        $('#container').highcharts({
            chart: {
                zoomType: 'xy'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: t_date,
                tickInterval: (dateInterval)// 每个间隔
            },
            yAxis: [
                { // Primary yAxis
                    labels: {
                        format: '{value}',
                        style: {
                            color: colorTow
                        }
                    }
                },
                { // Secondary yAxis
                    labels: {
                        format: '{value}',
                        style: {
                            color: colorOne
                        }
                    },
                    opposite: true
                }
            ],
            credits: {
                enabled: false
            },
            tooltip: {
                shared: true
            },
            legend: {
                align: 'left',
                x: 10,
                verticalAlign: 'top',
                y: -10,
                floating: true,
                backgroundColor: '#FFFFFF',
                itemDistance: 20,
                borderRadius: 5,
                enabled: false
            },
            series: [
                dataOne,
                dataTow
            ]
        });
    }

});

/**
 *
 * 账户基础报告
 *
 */
var accountBasisReport = function () {
    $.ajax({
        url: "/account/accountReport",
        type: "GET",
        dataType: "json",
        data: {
            Sorted: sort,
            fieldName: fieldName
        },
        success: function (data) {
            var basisHtml = "";
            $("#basisAccount").empty();
            $.each(data.rows, function (i, item) {
                var ctr = item.pcClick/item.pcImpression;
                var cpc = item.pcCost/item.pcClick;
                if (i % 2 == 0) {
                    basisHtml = "<tr class='list2_box1'><td>&nbsp;" + item.dateRep + "</td><td>&nbsp;" + item.pcImpression + "</td><td>&nbsp;" + item.pcClick + "</td>"
                            + "<td>&nbsp;" + Math.round(item.pcCost*100)/100 + "</td><td>&nbsp;" + Math.round(ctr*10000)/100 + "%</td><td>&nbsp;" + Math.round(cpc*100)/100 + "</td><td>&nbsp;" + item.pcConversion + "</td>"
                            + "<td>&nbsp;-</td></tr>"
                } else {
                    basisHtml = "<tr class='list2_box2'><td>&nbsp;" + item.dateRep + "</td><td>&nbsp;" + item.pcImpression + "</td><td>&nbsp;" + item.pcClick + "</td>"
                            + "<td>&nbsp;" + Math.round(item.pcCost*100)/100 + "</td><td>&nbsp;" + Math.round(ctr*10000)/100 + "%</td><td>&nbsp;" + Math.round(cpc*100)/100 + "</td><td>&nbsp;" + item.pcConversion + "</td>"
                            + "<td>&nbsp;-</td></tr>"
                }
                $("#basisAccount").append(basisHtml);
            });
        }
    });
}

function TestBlack(TagName) {
    var obj = document.getElementById(TagName);
    if (obj.style.display == "") {
        obj.style.display = "none";
    } else {
        obj.style.display = "";
    }
}
$(function () {
    var navH = $(".nav").offset().top;
    $(window).scroll(function () {
        var scroH = $(this).scrollTop();
        if (scroH >= navH) {
            $(".nav").css({"position": "fixed", "top": "0"});
        } else {
            $(".nav").css({"position": "static", "margin": "0 auto"});
        }
        console.log(scroH == navH);
    });
});


</script>
</html>
