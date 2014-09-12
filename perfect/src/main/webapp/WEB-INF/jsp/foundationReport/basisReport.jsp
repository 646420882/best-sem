<%--
  Created by IntelliJ IDEA.
  User: SubDong
  Date: 2014/8/18
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=7" />
    <meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title></title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/media.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/jquery.cxcalendar.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/respond.js"></script>


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

        .page2 .ajc {
            background: #ffb900;
            border: 1px solid #fab30b;
            color: #fff;
        }
        .list3{
            min-height:200px;
        }
    </style>
</head>
<body>
<jsp:include page="../homePage/pageBlock/head.jsp"/>
<div class="concent over">
<jsp:include page="../homePage/pageBlock/nav.jsp"/>
<div class="mid over ">
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
        <br/>

        <div class="page2 fl" id="pageJC">


        </div>
    </div>
</div>
<div class="number_concent over">
    <div class="list01_top over"><Span>账户报告</Span>
    </div>
    <div class="shuju_detali over">
        <ul>
            <li class="date">选择时间范围：<input type="text" class="time_input" placeholder="2014-01-30 至 2014-01-31" readonly>
                <input name="reservation" type="image" cname="dateClick"
                       onclick="_posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                       src="${pageContext.request.contextPath}/public/img/date.png">
                <input type="checkbox" id="checkboxInput" style="margin:6px 3px 0px 5px; ">
                比较范围
                <input name="mydate" type="text" id="inputTow" cname="dateClick" readonly style=" display:none;  height:20px;width:150px;border:1px solid #dadada; padding:0 12px;background:#fff url('/public/img/date.png') right 0px no-repeat;">
                <label id="dataComputing"></label>
            </li>
            <li id="deviceUser">选择推广设备：
                <a href="javascript:" class="current" cname="0">全部</a><span>|</span>
                <a href="javascript:" cname="1">计算机</a><span>|</span>
                <a href="javascript:" cname="2">移动设备</a>
            </li>
            <li id="dateLiUser">选择时间单位：
                <a href="javascript:" class="current" cname="0">默认</a><span>|</span>
                <a href="javascript:" cname="1">分日</a><span>|</span>
                <a href="javascript:" cname="2">分周</a><span>|</span>
                <a href="javascript:" cname="3">分月</a></li>
        </ul>

        <input type="hidden" id="devicesUser" value="0">
        <input type="hidden" id="dateLisUser" value="0">
        <input type="hidden" id="checkboxhidden" value="0">
        <a href="javascript:" id="userClick" class="become"> 生成报告</a>
    </div>
</div>
<div class="list01_under3 over">
    <div class="list3 wd">
        <table border="0" cellspacing="0" cellspacing="0">
            <thead>
            <tr class="list2_top" id="trTop">
                <td>&nbsp;<span>时间</span><b>
                    <p>
                        <input class="one" type="button"
                               onclick="javascript:sortVS = '1';reportDataVS()">
                    </p>

                    <p>
                        <input class="two" type="button"
                               onclick="javascript:sortVS = '-1';reportDataVS();">
                    </p></b>
                </td>
                <td>&nbsp;<span>展现</span><b>
                    <p>
                        <input class="one" type="button"
                               onclick="javascript:sortVS = '2';reportDataVS();">
                    </p>

                    <p>
                        <input class="two" type="button"
                               onclick="javascript:sortVS = '-2';reportDataVS();">
                    </p></b></td>
                <td>&nbsp;<span>点击</span><b>
                    <p>
                        <input class="one" type="button"
                               onclick="javascript:sortVS = '3';reportDataVS();">
                    </p>

                    <p>
                        <input class="two" type="button"
                               onclick="javascript:sortVS = '-3';reportDataVS();">
                    </p></b></td>
                <td>&nbsp;<span>消费</span><b>
                    <p>
                        <input class="one" type="button"
                               onclick="javascript:sortVS = '4';reportDataVS();">
                    </p>

                    <p>
                        <input class="two" type="button"
                               onclick="javascript:sortVS = '-4';reportDataVS();">
                    </p></b></td>
                <td>&nbsp;<span>点击率</span><b>
                    <p>
                        <input class="one" type="button"
                               onclick="javascript:sortVS = '6';reportDataVS();">
                    </p>

                    <p>
                        <input class="two" type="button"
                               onclick="javascript:sortVS = '-6';reportDataVS();">
                    </p></b></td>
                <td>&nbsp;<span>平均点击价格</span><b>
                    <p>
                        <input class="one" type="button"
                               onclick="javascript:sortVS = '5';reportDataVS();">
                    </p>

                    <p>
                        <input class="two" type="button"
                               onclick="javascript:sortVS = '-5';reportDataVS();">
                    </p></b></td>
                <td>&nbsp;<span>转化(页面)</span><b>
                    <p>
                        <input class="one" type="button"
                               onclick="javascript:sortVS = '7';reportDataVS();">
                    </p>

                    <p>
                        <input class="two" type="button"
                               onclick="javascript:sortVS = '-7';reportDataVS();">
                    </p></b></td>
                <td>&nbsp;<span>转化(商桥)</span></td>
                <td>&nbsp;<span>转化(电话)</span></td>
            </tr>
            </thead>
            <tbody id="userTbody">

            </tbody>
        </table>
    </div>
    <div class="page2 fl" id="pageVS">

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
                    <input name="reservation" type="image" cname="dateClick"
                           onclick="_posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                           src="${pageContext.request.contextPath}/public/img/date.png" readonly>
                </li>
                <li id="reportType">选择报告类型：
                    <a href="javascript:" class="current" cname="1">结构报告</a><span>|</span>
                    <a href="javascript:" cname="2">关键词报告</a><span>|</span>
                    <a href="javascript:" cname="3">创意报告</a><span>|</span>
                    <%--<a href="javascript:">附加创意报告</a><span>|</span>--%>
                    <a href="javascript:" cname="4">分地域报告</a>
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
        <%--<div class="download over fr"><a href="#">下载全部</a></div>--%>
        <div class="list01_under3 over">

            <div class="list3 wd" style="overflow-x: auto; width: 100%;">
                <table border="0" cellspacing="0" cellspacing="0" style="width:1600px;">
                    <thead id="shujuthead">

                    </thead>
                    <tbody id="shuju">

                    </tbody>
                </table>
            </div>
            <div class="page2 fl" id="pageDet">

            </div><br/>
            <div class="tubiao2 over">
                <div id="containerLegend"></div>
                <div id="container" style="width:100%;height:400px;display: none"></div>
                <div id="imprDiv" style="width:45%;height:400px;display: none;float: left"></div>
                <div id="clickDiv" style="width:54%;height:400px;display: none;float: right"></div>
                <div id="costDiv" style="width:45%;height:400px;display: none;float: left;margin-top: 40px;"></div>
                <div id="convDiv" style="width:53%;height:400px;display: none;float: right;margin-top: 40px;"></div>
            </div>

        </div>
    </div>
</div>

</div>
</div>
<jsp:include page="../homePage/pageBlock/footer.jsp"/>
</div>
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.cxcalendar.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-ui-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.jQuery.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/4.0.1/highcharts.js"></script>
<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/4.0.1/modules/exporting.js"></script>

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
//点击数
var t_clicks = new Array();
//消费
var t_cost = new Array();
//点击率
var t_ctr = new Array();
//平均点击价格
var t_cpc = new Array();
//转化
var t_conversion = new Array();
//饼状图参数
//展现
var tsay_impr = new Array();
var tsa_impr = 0;

//点击
var tsay_clicks = new Array();
var tsa_clicks = 0;

//消费
var tsay_cost = new Array();
var tsa_cost = 0;

//转化
var tsay_conversion = new Array();
var tsa_conversion = 0;

//点击率
var tsay_ctr = new Array();
var tsa_ctr = 0;

//平均价格
var tsay_cpc = new Array();
var tsa_cpc = 0;

var fieldName = 'date';

var sort = 1;
var dateclicks = "";

//日期控件-开始日期
var daterangepicker_start_date = null;

//日期控件-结束日期
var daterangepicker_end_date = null;


var startDet = 0;
var sorts = "-11";
var limitDet = 30;
var sortVS = "-1";
var pageDetNumber =0;
var judgeDet = 0;
//基础报告
var startJC = 0;
var limitJC = 10;
//账户报告
var startVS = 0;
var limitVS = 10;
$(document).ready(function () {
    //加载日历控件
    $("input[name=reservation]").daterangepicker();
    $("#inputTow").cxCalendar();
    $("input[cname=dateClick]").click(function () {
        dateclicks = $(this)
    });
    var distance = 0;
    $(".btnDone").on('click', function () {
        var _startDate = $('.range-start').datepicker('getDate');
        var _endDate = $('.range-end').datepicker('getDate');
        if (_startDate != null && _endDate != null) {
            daterangepicker_start_date = _startDate.Format("yyyy-MM-dd");
            daterangepicker_end_date = _endDate.Format("yyyy-MM-dd");
            if($("#checkboxhidden").val() == 1){
                $("#date3").val(daterangepicker_start_date);
            }
            //计算两个时间相隔天数
            var sDate = new Date(daterangepicker_start_date);
            var eDate = new Date(daterangepicker_end_date);
            var a = new Date(daterangepicker_start_date.replace(/-/g,'/'));
            var b = new Date(daterangepicker_end_date.replace(/-/g,'/'));
            var fen  = ((b.getTime()-a.getTime())/1000)/60;
            if(fen<0){
                daterangepicker_start_date = null;
                daterangepicker_end_date = null;
                alert("请选择正确的时间范围");
                dateclicks.prev().val();
                return;
            }
            distance = parseInt(fen/(24*60))+1;   //相隔distance天
            if($("#checkboxhidden").val() == 1){
                $("#dataComputing").empty();
                $("#dataComputing").append("起 "+distance+" 天");
            }
            dateclicks.prev().val(daterangepicker_start_date + " 至 " + daterangepicker_end_date);
        }
    });

    $("#userClick").click(function () {
        reportDataVS();
    });


    var $tab_li = $('.tab_menu li');
    $('.tab_menu li').click(function () {
        $(this).addClass('selected').siblings().removeClass('selected');
        var index = $tab_li.index(this);
        $('div.tab_box > div').eq(index).show().siblings().hide();
        $("#pageDet").empty();
        judgeDet = 0;
    });
    $("#reportType>a").click(function () {
        $("#reportType>a").removeClass("current");
        $(this).addClass("current");
        $("#reportTypes").val($(this).attr("cname"));
        $("#pageDet").empty();
        judgeDet = 0;
    });
    $("#device>a").click(function () {
        $("#device>a").removeClass("current");
        $(this).addClass("current");
        $("#devices").val($(this).attr("cname"));
        $("#pageDet").empty();
        judgeDet = 0;
    });
    $("#dateLi>a").click(function () {
        $("#dateLi>a").removeClass("current");
        $(this).addClass("current");
        $("#dateLis").val($(this).attr("cname"));
        $("#pageDet").empty();
        judgeDet = 0;
    })
    $("#deviceUser>a").click(function () {
        $("#deviceUser>a").removeClass("current");
        $(this).addClass("current");
        $("#devicesUser").val($(this).attr("cname"));
        $("#pageVS").empty();
        judgeVS = 0;
    });
    $("#dateLiUser>a").click(function () {
        $("#dateLiUser>a").removeClass("current");
        $(this).addClass("current");
        $("#dateLisUser").val($(this).attr("cname"));
        $("#pageVS").empty();
        judgeVS = 0;
    });
    $("#checkboxInput").click(function () {
        if ($(this).is(":checked")) {
            $("#inputTow").attr("style", "height:20px;width:150px;border:1px solid #dadada; padding:0 12px;background:#fff url('/public/img/date.png ') 130px 0px no-repeat;");
            $("#inputOne").removeAttr("disabled");
            $("#checkboxhidden").val(1);
            $("#dataComputing").append("起 "+distance+" 天");
        } else {
            $("#inputTow").attr("style", "display:none");
            $("#inputOne").attr("disabled", "disabled");
            $("#checkboxhidden").val(0);
            $("#inputTow").val("");
            $("#dataComputing").empty();
        }
    });

    //初始化基础统计
    accountBasisReport();

    /**
     *生成报告按钮点击
     */
    $("#shengc").click(function () {
        reportData();
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
});
//明细报告
var reportData = function () {
    $("#containerLegend").empty();
    $("#shujuthead").empty();
    $('#container').empty();
    $("#shuju").empty();
    $("#imprDiv").empty();
    $("#clickDiv").empty();
    $("#costDiv").empty();
    $("#convDiv").empty();

    $("#shuju").html("报告生成中，请稍后。。。。。（第一次生成报告时，需要时间较长，请耐心等待）");
    var reportTypes = $("#reportTypes").val();
    var devices = $("#devices").val();
    var dateLis = $("#dateLis").val();
    $.ajax({
        url: "/account/structureReport",
        type: "GET",
        dataType: "json",
        data: {
            startDate: daterangepicker_start_date,
            endDate: daterangepicker_end_date,
            reportType: reportTypes,
            devices: devices,
            dateType: dateLis,
            start: startDet,
            sort: sorts,
            limit: limitDet
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
                        html_head = "<tr class='list2_top'><td>&nbsp;<span>时间</span><b><p><input class='one' type='button'onclick='javascript:sorts = -11;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = -11;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>推广计划</span><b><p><input class='one' type='button'onclick='javascript:sorts = -8;reportData()'></p><p><input class='two' type='button'onclick='javascript:sorts = 8;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>推广单元</span><b><p><input class='one' type='button' onclick='javascript:sorts = -7;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 7;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>展现量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -1;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 1;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -2;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 2;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>消费</span><b><p><input class='one' type='button' onclick='javascript:sorts = -3;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 3;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击率</span><b><p><input class='one' type='button' onclick='javascript:sorts = -5;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 5;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>平均点击价格</span><b><p><input class='one' type='button' onclick='javascript:sorts = -4;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 4;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(网页)</span><b><p><input class='one' type='button' onclick='javascript:sorts = -6;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 6;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(商桥)</span><b><p></p><p></p></b></td>" +
                                "<td>&nbsp;<span>转化(电话))</span><b><p></p><p></p></b></td></td></tr>";
                        break;
                    case "2":
                        html_head = "<tr class='list2_top'><td>&nbsp;<span>时间</span><b><p><input class='one' type='button'onclick='javascript:sorts = -11;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = -11;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>推广计划</span><b><p><input class='one' type='button'onclick='javascript:sorts = -8;reportData()'></p><p><input class='two' type='button'onclick='javascript:sorts = 8;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>推广单元</span><b><p><input class='one' type='button' onclick='javascript:sorts = -7;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 7;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>关键字</span><b><p><input class='one' type='button' onclick='javascript:sorts = -9;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 9;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>展现量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -1;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 1;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -2;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 2;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>消费</span><b><p><input class='one' type='button' onclick='javascript:sorts = -3;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 3;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击率</span><b><p><input class='one' type='button' onclick='javascript:sorts = -5;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 5;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>平均点击价格</span><b><p><input class='one' type='button' onclick='javascript:sorts = -4;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 4;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(网页)</span><b><p><input class='one' type='button' onclick='javascript:sorts = -6;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 6;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(商桥)</span><b><p></p><p></p></b></td>" +
                                "<td>&nbsp;<span>转化(电话))</span><b><p></p><p></p></b></td></td></tr>";
                        break;
                    case "3":
                        html_head = "<tr class='list2_top'><td>&nbsp;<span>时间</span><b><p><input class='one' type='button'onclick='javascript:sorts = -11;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = -11;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>推广计划</span><b><p><input class='one' type='button'onclick='javascript:sorts = -8;reportData()'></p><p><input class='two' type='button'onclick='javascript:sorts = 8;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>推广单元</span><b><p><input class='one' type='button' onclick='javascript:sorts = -7;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 7;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>创意</span><b><p><input class='one' type='button' onclick='javascript:sorts = -12;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 12;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>展现量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -1;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 1;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -2;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 2;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>消费</span><b><p><input class='one' type='button' onclick='javascript:sorts = -3;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 3;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击率</span><b><p><input class='one' type='button' onclick='javascript:sorts = -5;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 5;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>平均点击价格</span><b><p><input class='one' type='button' onclick='javascript:sorts = -4;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 4;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(网页)</span><b><p><input class='one' type='button' onclick='javascript:sorts = -6;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 6;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(商桥)</span><b><p></p><p></p></b></td>" +
                                "<td>&nbsp;<span>转化(电话))</span><b><p></p><p></p></b></td></td></tr>";
                    case "4":
                        html_head = "<tr class='list2_top'><td>&nbsp;<span>时间</span><b><p><input class='one' type='button'onclick='javascript:sorts = -11;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = -11;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>推广计划</span><b><p><input class='one' type='button'onclick='javascript:sorts = -8;reportData()'></p><p><input class='two' type='button'onclick='javascript:sorts = 8;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>推广单元</span><b><p><input class='one' type='button' onclick='javascript:sorts = -7;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 7;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>地域</span><b><p><input class='one' type='button' onclick='javascript:sorts = -10;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 10;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>展现量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -1;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 1;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -2;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 2;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>消费</span><b><p><input class='one' type='button' onclick='javascript:sorts = -3;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 3;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击率</span><b><p><input class='one' type='button' onclick='javascript:sorts = -5;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 5;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>平均点击价格</span><b><p><input class='one' type='button' onclick='javascript:sorts = -4;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 4;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(网页)</span><b><p><input class='one' type='button' onclick='javascript:sorts = -6;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 6;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(商桥)</span><b><p></p><p></p></b></td>" +
                                "<td>&nbsp;<span>转化(电话))</span><b><p></p><p></p></b></td></td></tr>";
                        break;
                }
                $("#shujuthead").append(html_head);
                //饼状图计算
                var pie_impr = new Array();
                var pie_click = new Array();
                var pie_cost = new Array();
                var pie_conv = new Array();
                var pie_num1 = 0;
                var pie_num2 = 0;
                var pie_num3 = 0;
                var pie_num4 = 0;
                if (reportTypes == 4) {
                    if (dateLis == 0) {
                        $.each(data.countData, function (i, countdata) {
                            $.each(data.impr, function (i, impr) {
                                if (devices == 2) {
                                    if(isNaN(impr.mobileImpression / countdata.mobileImpression)){
                                        pie_impr.push([impr.regionName, 0]);
                                    }else {
                                        pie_impr.push([impr.regionName, Math.round((impr.mobileImpression / countdata.mobileImpression) * 10000) / 100]);
                                        pie_num1=1;
                                    }
                                } else {
                                    if(isNaN(impr.pcImpression / countdata.pcImpression)){
                                        pie_impr.push([impr.regionName, 0]);
                                    }else{
                                        pie_impr.push([impr.regionName, Math.round((impr.pcImpression / countdata.pcImpression) * 10000) / 100]);
                                        pie_num1=1;
                                    }
                                }
                            });
                            $.each(data.click, function (i, impr) {
                                if (devices == 2) {
                                    if(isNaN(impr.mobileClick / countdata.mobileClick)){
                                        pie_click.push([impr.regionName, 0]);
                                    }else {
                                        pie_click.push([impr.regionName, Math.round((impr.mobileClick / countdata.mobileClick) * 10000) / 100]);
                                        pie_num2=1;
                                    }
                                } else {
                                    if(isNaN(impr.pcClick / countdata.pcClick)){
                                        pie_click.push([impr.regionName, 0]);
                                    }else {
                                        pie_click.push([impr.regionName, Math.round((impr.pcClick / countdata.pcClick) * 10000) / 100]);
                                        pie_num2=1;
                                    }
                                }
                            });
                            $.each(data.cost, function (i, impr) {
                                if (devices == 2) {
                                    if(isNaN(impr.mobileCost / countdata.mobileCost)){
                                        pie_cost.push([impr.regionName, 0]);
                                    }else {
                                        pie_cost.push([impr.regionName, Math.round((impr.mobileCost / countdata.mobileCost) * 10000) / 100]);
                                        pie_num3=1;
                                    }
                                } else {
                                    if(isNaN(impr.pcCost / countdata.pcCost)){
                                        pie_cost.push([impr.regionName, 0]);
                                    }else {
                                        pie_cost.push([impr.regionName, Math.round((impr.pcCost / countdata.pcCost) * 10000) / 100]);
                                        pie_num3=1;
                                    }
                                }
                            });
                            $.each(data.conv, function (i, impr) {
                                if (devices == 2) {
                                    if(isNaN(impr.mobileConversion / countdata.mobileConversion)){
                                        pie_conv.push([impr.regionName, 0]);
                                    }else {
                                        pie_conv.push([impr.regionName, Math.round((impr.mobileConversion / countdata.mobileConversion) * 10000) / 100]);
                                        pie_num4=1;
                                    }
                                } else {
                                    if(isNaN(impr.pcConversion / countdata.pcConversion)){
                                        pie_conv.push([impr.regionName, 0]);
                                    }else {
                                        pie_conv.push([impr.regionName, Math.round((impr.pcConversion / countdata.pcConversion) * 10000) / 100]);
                                        pie_num4=1;
                                    }
                                }
                            });
                            if(pie_num1 == 1){
                                pieChart(pie_impr, "展现", "#imprDiv");
                            }
                            if(pie_num2 == 1){
                                pieChart(pie_click, "点击", "#clickDiv");
                            }
                            if(pie_num3 == 1) {
                                pieChart(pie_cost, "消费", "#costDiv");
                            }
                            if(pie_num2 == 1){
                                pieChart(pie_conv, "转化", "#convDiv");
                            }
                        });
                    }

                }
                if (dateLis != 0) {
                    //曲线图数据装载
                    $.each(data.chart, function (i, dataItem) {
                        if (devices == 2) {
                            t_date.push(dataItem.date);
                            t_impr.push(dataItem.mobileImpression);
                            t_clicks.push(dataItem.mobileClick);
                            t_cost.push(Math.round(dataItem.mobileCost * 100) / 100);
                            t_ctr.push(Math.round((dataItem.mobileCtr) * 10000) / 100);
                            t_cpc.push(Math.round((dataItem.mobileCpc) * 10000) / 100);
                            t_conversion.push(dataItem.mobileConversion);
                        } else {
                            t_date.push(dataItem.date);
                            t_impr.push(dataItem.pcImpression);
                            t_clicks.push(dataItem.pcClick);
                            t_cost.push(Math.round(dataItem.pcCost * 100) / 100);
                            t_ctr.push(Math.round((dataItem.pcCtr) * 10000) / 100);
                            t_cpc.push(Math.round((dataItem.pcCpc) * 10000) / 100);
                            t_conversion.push(dataItem.pcConversion);
                        }
                    });
                }

                $.each(data.rows, function (i, items) {
                    if (data.length <= 0) {
                        $("#shuju").html("查无数据。。。。。");
                        return;
                    }
                    pageDetNumber = items.count;
                    var html_Go = "";
                    switch (reportTypes) {
                        case "1":
                            if (i % 2 == 0) {
                                if (devices == 2) {
                                    html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                } else {
                                    html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                }
                            } else {
                                if (devices == 2) {
                                    html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                } else {
                                    html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                }
                            }
                            break;
                        case "2":
                            if (i % 2 == 0) {
                                if (devices == 2) {
                                    html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.keywordName + "</td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                } else {
                                    html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.keywordName + "</td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                }
                            } else {
                                if (devices == 2) {
                                    html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.keywordName + "</td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                } else {
                                    html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.keywordName + "</td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                }
                            }
                            break;
                        case "3":
                            if (i % 2 == 0) {
                                if (devices == 2) {
                                    html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td><label style='color: dodgerblue'>" + items.creativeTitle + "</label><br/><label style='font-size: 8px'>" + items.description1 + "</label></td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                } else {
                                    html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td><label style='color: dodgerblue'>" + items.creativeTitle + "</label><br/><label style='font-size: 8px'>" + items.description1 + "</label></td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                }
                            } else {
                                if (devices == 2) {
                                    html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td><label style='color: dodgerblue'>" + items.creativeTitle + "</label><br/><label style='font-size: 8px'>" + items.description1 + "</label></td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                } else {
                                    html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td><label style='color: dodgerblue'>" + items.creativeTitle + "</label><br/><label style='font-size: 8px'>" + items.description1 + "</label></td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                }
                            }
                            break;
                        case "4":
                            if (i % 2 == 0) {
                                if (devices == 2) {
                                    html_Go = "<tr class='list2_box1' cname='pieClick' region='" + items.regionName + "' impr='" + items.mobileImpression + "' clicks='" + items.mobileClick + "' cost='" + Math.round(items.mobileCost * 100) / 100 + "' ctr='" + Math.round(items.mobileCtr) + "' cpc='" + Math.round(items.mobileCpc * 100) / 100 + "' conv='" + items.mobileConversion + "'>"
                                            + "<td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td>"
                                            + "<td>" + items.regionName + "</td>"
                                            + "<td>" + items.mobileImpression + "</td>"
                                            + "<td>" + items.mobileClick + "</td>"
                                            + "<td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td>"
                                            + "<td>" + Math.round(items.mobileCpc * 100) / 100 + "</td>"
                                            + "<td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                } else {
                                    html_Go = "<tr class='list2_box1' cname='pieClick' region='" + items.regionName + "' impr='" + items.pcImpression + "' clicks='" + items.pcClick + "' cost='" + Math.round(items.pcCost * 100) / 100 + "' ctr='" + Math.round(items.pcCtr) + "' cpc='" + Math.round(items.pcCpc * 100) / 100 + "' conv='" + items.pcConversion + "'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td>"
                                            + "<td>" + items.regionName + "</td>"
                                            + "<td>" + items.pcImpression + "</td>"
                                            + "<td>" + items.pcClick + "</td>"
                                            + "<td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td>"
                                            + "<td>" + Math.round(items.pcCpc * 100) / 100 + "</td>"
                                            + "<td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                }
                            } else {
                                if (devices == 2) {
                                    html_Go = "<tr class='list2_box2' cname='pieClick' region='" + items.regionName + "' impr='" + items.mobileImpression + "' clicks='" + items.mobileClick + "' cost='" + Math.round(items.mobileCost * 100) / 100 + "' ctr='" + Math.round(items.mobileCtr) + "' cpc='" + Math.round(items.mobileCpc * 100) / 100 + "' conv='" + items.mobileConversion + "'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td>"
                                            + "<td>" + items.regionName + "</td>"
                                            + "<td>" + items.mobileImpression + "</td>"
                                            + "<td>" + items.mobileClick + "</td>"
                                            + "<td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td>"
                                            + "<td>" + Math.round(items.mobileCpc * 100) / 100 + "</td>"
                                            + "<td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                } else {
                                    html_Go = "<tr class='list2_box2' cname='pieClick' region='" + items.regionName + "' impr='" + items.pcImpression + "' clicks='" + items.pcClick + "' cost='" + Math.round(items.pcCost * 100) / 100 + "' ctr='" + Math.round(items.pcCtr) + "' cpc='" + Math.round(items.pcCpc * 100) / 100 + "' conv='" + items.pcConversion + "'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td>"
                                            + "<td>" + items.regionName + "</td>"
                                            + "<td>" + items.pcImpression + "</td>"
                                            + "<td>" + items.pcClick + "</td>"
                                            + "<td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td>"
                                            + "<td>" + Math.round(items.pcCpc * 100) / 100 + "</td>"
                                            + "<td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                }
                            }
                            break;
                    }
                    $("#shuju").append(html_Go);

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
                if (judgeDet < 1) {
                    var countNumber = 0;
                    if (pageDetNumber % 30 == 0) {
                        countNumber = pageDetNumber / 30;
                    } else {
                        countNumber = (pageDetNumber / 30);
                    }
                    var page_html = "<a href='javascript:' id='pageUpDet' class='nextpage1'><span></span></a>"
                    for (var i = 0; i < countNumber; i++) {
                        if(i<10){
                            if (i == 0) {
                                page_html = page_html + "<a href='javascript:' class='ajc' cname='nameDet' onclick='javascript:startDet = " + i + ";limitDet = " + (i + 30) + ";reportData()'>" + (i + 1) + "</a>";
                            } else {
                                page_html = page_html + "<a href='javascript:' cname='nameDet' onclick='javascript:startDet = " + (i * 30) + ";limitDet = " + (i * 30 + 30) + ";reportData()'>" + (i + 1) + "</a>";
                            }
                        }else{
                            if (i == 0) {
                                page_html = page_html + "<a href='javascript:' class='ajc' cname='nameDet' onclick='javascript:startDet = " + i + ";limitDet = " + (i + 30) + ";reportData()' style='display:none'>" + (i + 1) + "</a>";
                            } else {
                                page_html = page_html + "<a href='javascript:' cname='nameDet' onclick='javascript:startDet = " + (i * 30) + ";limitDet = " + (i * 30 + 30) + ";reportData()' style='display:none'>" + (i + 1) + "</a>";
                            }
                        }

                    }
                    page_html = page_html + "<a href='javascript:' id='pageDownDet' class='nextpage2'><span></span></a>" +
                            "<span style='margin-right:10px;'>跳转到 <input type='text' id='goDetID' class='price'></span>&nbsp;&nbsp;<a href='javascript:' id='goDet' class='page_go'> GO</a>"
                    $("#pageDet").append(page_html);
                    judgeDet++;
                }
            }
            if (data.rows.length > 10) {
                dateInterval = 5;
            }
        }
    });
}


var pageNumberVS = 0;
var judgeVS = 0;
//账户报告比较数据
var reportDataVS = function () {
    var date3 = $("#inputTow").val();
    var devicesUser = $("#devicesUser").val();
    var dateLisUser = $("#dateLisUser").val();
    var checkboxhidden = $("#checkboxhidden").val();
    $.ajax({
        url: "/account/accountDateVs",
        type: "GET",
        dataType: "json",
        data: {
            date1: daterangepicker_start_date,
            date2: daterangepicker_end_date,
            date3: date3,
            dateType: dateLisUser,
            devices: devicesUser,
            compare: checkboxhidden,
            sortVS: sortVS,
            startVS: startVS,
            limitVS: limitVS
        },
        success: function (data) {
            $("#userTbody").empty();
            if (checkboxhidden == 0) {
                /*$("#trTop").removeAttr("class");
                 $("#trTop").addClass("list02_top");*/
                $.each(data.date, function (i, item) {
                    $.each(data.rows, function (z, items1) {
                        if (items1[item] != null) {
                            $.each(items1[item], function (s, items) {
                                pageNumberVS = items.count - 1;
                                var html_User = "";
                                if (i % 2 == 0) {
                                    if (devicesUser == 2) {
                                        html_User = "<tr class='list2_box1'><td>" + item + "</td>"
                                                + "<td>" + ((items.mobileImpression == null) ? "-" : items.mobileImpression) + "</td><td>" + ((items.mobileClick == null) ? "-" : items.mobileClick) + "</td><td>" + ((items.mobileCost == null) ? "-" : Math.round(items.mobileCost * 100) / 100) + "</td>"
                                                + "<td>" + ((items.mobileCtr == null) ? "-" : Math.round(items.mobileCtr*100)/100) + "%</td><td>" + ((items.mobileCpc == null) ? "-" : Math.round(items.mobileCpc * 100) / 100) + "</td><td>" + ((items.mobileConversion == null) ? "-" : items.mobileConversion) + "</td><td>-</td><td>-</td></tr>";
                                    } else {
                                        html_User = "<tr class='list2_box1'><td>" + item + "</td>"
                                                + "<td>" + ((items.pcImpression == null) ? "-" : items.pcImpression) + "</td><td>" + ((items.pcClick == null) ? "-" : items.pcClick) + "</td><td>" + ((items.pcCost == null) ? "-" : Math.round(items.pcCost * 100) / 100) + "</td>"
                                                + "<td>" + ((items.pcCtr == null) ? "-" : Math.round(items.pcCtr*100)/100) + "%</td><td>" + ((items.pcCpc == null) ? "-" : Math.round(items.pcCpc * 100) / 100) + "</td><td>" + ((items.pcConversion == null) ? "-" : items.pcConversion) + "</td><td>-</td><td>-</td></tr>";
                                    }
                                } else {
                                    if (devicesUser == 2) {
                                        html_User = "<tr class='list2_box2'><td>" + item + "</td>"
                                                + "<td>" + ((items.mobileImpression == null) ? "-" : items.mobileImpression) + "</td><td>" + ((items.mobileClick == null) ? "-" : items.mobileClick) + "</td><td>" + ((items.mobileCost == null) ? "-" : Math.round(items.mobileCost * 100) / 100) + "</td>"
                                                + "<td>" + ((items.mobileCtr == null) ? "-" : Math.round(items.mobileCtr*100)/100) + "%</td><td>" + ((items.mobileCpc == null) ? "-" : Math.round(items.mobileCpc * 100) / 100) + "</td><td>" + ((items.mobileConversion == null) ? "-" : items.mobileConversion) + "</td><td>-</td><td>-</td></tr>";
                                    } else {
                                        html_User = "<tr class='list2_box1'><td>" + item + "</td>"
                                                + "<td>" + ((items.pcImpression == null) ? "-" : items.pcImpression) + "</td><td>" + ((items.pcClick == null) ? "-" : items.pcClick) + "</td><td>" + ((items.pcCost == null) ? "-" : Math.round(items.pcCost * 100) / 100) + "</td>"
                                                + "<td>" + ((items.pcCtr == null) ? "-" : Math.round(items.pcCtr*100)/100) + "%</td><td>" + ((items.pcCpc == null) ? "-" : Math.round(items.pcCpc * 100) / 100) + "</td><td>" + ((items.pcConversion == null) ? "-" : items.pcConversion) + "</td><td>-</td><td>-</td></tr>";
                                    }
                                }
                                $("#userTbody").append(html_User);
                            });
                        }
                    });
                });
                if (judgeVS < 1) {
                    var countNumber = 0;
                    if (pageNumberVS % 10 == 0) {
                        countNumber = pageNumberVS / 10;
                    } else {
                        countNumber = (pageNumberVS / 10);
                    }
                    var page_html = "<a href='javascript:' id='pageUpVS' class='nextpage1'><span></span></a>"
                    for (var i = 0; i < countNumber; i++) {
                        if(i<10){
                            if (i == 0) {
                                page_html = page_html + "<a href='javascript:' class='ajc' cname='nameVS' onclick='javascript:startVS = " + i + ";limitVS = " + (i + 10) + ";reportDataVS()'>" + (i + 1) + "</a>";
                            } else {
                                page_html = page_html + "<a href='javascript:' cname='nameVS' onclick='javascript:startVS = " + (i * 10) + ";limitVS = " + (i * 10 + 10) + ";reportDataVS()'>" + (i + 1) + "</a>";
                            }
                        }else{
                            if (i == 0) {
                                page_html = page_html + "<a href='javascript:' class='ajc' style='display:none' cname='nameVS' onclick='javascript:startVS = " + i + ";limitVS = " + (i + 10) + ";reportDataVS()'>" + (i + 1) + "</a>";
                            } else {
                                page_html = page_html + "<a href='javascript:'style='display:none' cname='nameVS' onclick='javascript:startVS = " + (i * 10) + ";limitVS = " + (i * 10 + 10) + ";reportDataVS()'>" + (i + 1) + "</a>";
                            }
                        }

                    }
                    page_html = page_html + "<a href='javascript:' id='pageDownVS' class='nextpage2'><span></span></a>" +
                            "<span style='margin-right:10px;'>跳转到 <input type='text' id='goVSID' class='price'></span>&nbsp;&nbsp;<a href='javascript:' id='goVS' class='page_go'> GO</a>"
                    $("#pageVS").append(page_html);
                    judgeVS++;
                }
            } else {
                /*$("#trTop").removeAttr("class");
                 $("#trTop").addClass("list03_top");*/
                var dateEach = new Array(), impression = new Array(), click = new Array(), cost = new Array(), ctr = new Array(), cpc = new Array(), conversion = new Array();
                var dateEach1 = new Array(), impression1 = new Array(), click1 = new Array(), cost1 = new Array(), ctr1 = new Array(), cpc1 = new Array(), conversion1 = new Array();
                $.each(data.date, function (i, item) {
                    dateEach.push(item);
                    $.each(data.rows, function (i, item1) {
                        if (item1[item] != null) {
                            $.each(item1[item], function (i, items) {
                                if (item1[item] != null) {
                                    if (devicesUser == 2) {
                                        impression.push((items.mobileImpression == null) ? "-" : items.mobileImpression);
                                        click.push((items.mobileClick == null) ? "-" : items.mobileClick);
                                        cost.push((items.mobileCost == null) ? "-" : Math.round(items.mobileCost * 100) / 100);
                                        ctr.push((items.mobileCtr == null) ? "-" : Math.round(items.mobileCtr * 100) / 100);
                                        cpc.push((items.mobileCpc == null) ? "-" : Math.round(items.mobileCpc * 100) / 100);
                                        conversion.push((items.mobileConversion == null) ? "-" : items.mobileConversion);
                                    } else {
                                        impression.push(items.pcImpression);
                                        click.push(items.pcClick);
                                        cost.push(Math.round(items.pcCost * 100) / 100);
                                        ctr.push(Math.round(items.pcCtr));
                                        cpc.push(Math.round(items.pcCpc * 100) / 100);
                                        conversion.push(items.pcConversion);
                                    }
                                }
                            });
                        }
                    });
                });
                $.each(data.date1, function (i, item) {
                    dateEach1.push(item);
                    $.each(data.rows, function (i, item1) {
                        if (item1[item] != null) {
                            $.each(item1[item], function (i, items) {
                                if (item1[item] != null) {
                                    if (devicesUser == 2) {
                                        impression1.push((items.mobileImpression == null) ? "-" : items.mobileImpression);
                                        click1.push((items.mobileClick == null) ? "-" : items.mobileClick);
                                        cost1.push((items.mobileCost == null) ? "-" : Math.round(items.mobileCost * 100) / 100);
                                        ctr1.push((items.mobileCtr == null) ? "-" : Math.round(items.mobileCtr * 100) / 100);
                                        cpc1.push((items.mobileCpc == null) ? "-" : Math.round(items.mobileCpc * 100) / 100);
                                        conversion1.push((items.mobileConversion == null) ? "-" : items.mobileConversion);
                                    } else {
                                        impression1.push((items.pcImpression == null) ? "-" : items.pcImpression);
                                        click1.push((items.pcClick == null) ? "-" : items.pcClick);
                                        cost1.push((items.pcCost == null) ? "-" : Math.round(items.pcCost * 100) / 100);
                                        ctr1.push((items.pcCtr == null) ? "-" : Math.round(items.pcCtr * 100) / 100);
                                        cpc1.push((items.pcCpc == null) ? "-" : Math.round(items.pcCpc * 100) / 100);
                                        conversion1.push((items.pcConversion == null) ? "-" : items.pcConversion);
                                    }
                                }
                            });
                        }
                    });
                });
                for (var i = 0; i < impression.length; i++) {
                    var html_User1 = "";
                    var html_User2 = "";
                    if (i % 2 == 0) {
                        html_User1 = "<tr class='list2_box1'><td>" + dateEach[i] + "</td>"
                                + "<td><span>" + impression[i] + "</span>" + (((impression[i] - impression1[i]) < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                                + "<span>" + ((impression[i] - impression1[i] >= 0) ? ((isNaN(impression1[i])) ? "-" : Math.round(((impression[i] - impression1[i]) / impression1[i]) * 100)) + "%" : "<strong>" + ((isNaN(impression1[i])) ? "-" : Math.round(((impression[i] - impression1[i]) / impression1[i]) * 100)) + "%</strong>") + "</span></td>"
                                + "<td><span>" + click[i] + "</span>" + (((click[i] - click1[i]) < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                                + "<span>" + ((click[i] - click1[i] >= 0) ? ((isNaN(click1[i])) ? "-" : Math.round(((click[i] - click1[i]) / click1[i]) * 100)) + "%" : "<strong>" + ((isNaN(click1[i])) ? "-" : Math.round(((click[i] - click1[i]) / click1[i]) * 100)) + "%</strong>") + "</span></td>"
                                + "<td><span>" + cost[i] + "</span>" + (((cost[i] - cost1[i]) < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                                + "<span>" + ((cost[i] - cost1[i] >= 0) ? ((isNaN(cost1[i])) ? "-" : Math.round(((cost[i] - cost1[i]) / cost1[i]) * 100)) + "%" : "<strong>" + ((isNaN(cost1[i])) ? "-" : Math.round(((cost[i] - cost1[i]) / cost1[i]) * 100)) + "%</strong>") + "</span></td>"
                                + "<td><span>" + ctr[i] + "%</span>" + ((ctr[i] - ctr1[i] < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                                + "<span>" + (((ctr[i] - ctr1[i]) >= 0) ? ((isNaN(ctr1[i])) ? "-" : Math.round(((ctr[i] - ctr1[i]) / ctr1[i]) * 100)) + "%" : "<strong>" + ((isNaN(ctr1[i])) ? "-" : Math.round(((ctr[i] - ctr1[i]) / ctr1[i]) * 100)) + "%</strong>") + "</span></td>"
                                + "<td><span>" + cpc[i] + "</span>" + ((cpc[i] - cpc1[i] < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                                + "<span>" + (((cpc[i] - cpc1[i]) >= 0) ?((isNaN(cpc1[i])) ? "-" : Math.round(((cpc[i] - cpc1[i]) / cpc1[i]) * 100)) + "%" : "<strong>" + ((isNaN(cpc1[i])) ? "-" : Math.round(((cpc[i] - cpc1[i]) / cpc1[i]) * 100)) + "%</strong>") + "</span></td>"
                                + "<td><span>" + conversion[i] + "</span>" + ((conversion[i] - conversion1[i] < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                                + "<span>" + (((conversion[i] - conversion1[i]) >= 0) ?  ((isNaN(conversion1[i])) ? "-" : Math.round(((conversion[i] - conversion1[i]) / conversion1[i]) * 100)) + "%" : "<strong>" + ((isNaN(conversion1[i])) ? "-" : Math.round(((conversion[i] - cpc1[i]) / conversion1[i]) * 100)) + "%</strong>") + "</span></td>"
                                + "</td><td>-</td><td>-</td></tr>";

                        html_User2 = "<tr class='list2_box1'><td>" + dateEach1[i] + "</td>"
                                + "<td>" + impression1[i] + "</td><td>" + click1[i] + "</td><td>" + cost1[i] + "</td>"
                                + "<td>" + ctr1[i] + "%</td><td>" + cpc1[i] + "</td><td>" + conversion1[i] + "</td><td>-</td><td>-</td></tr>"
                                + "<tr><td colspan='9'>&nbsp;</td></tr>";
                    } else {
                        html_User1 = "<tr class='list2_box2'><td>" + dateEach[i] + "</td>"
                                + "<td><span>" + impression[i] + "</span>" + (((impression[i] - impression1[i]) < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                                + "<span>" + ((impression[i] - impression1[i] >= 0) ? ((isNaN(impression1[i])) ? "-" : Math.round(((impression[i] - impression1[i]) / impression1[i]) * 100)) + "%" : "<strong>" + ((isNaN(impression1[i])) ? "-" : Math.round(((impression[i] - impression1[i]) / impression1[i]) * 100)) + "%</strong>") + "</span></td>"
                                + "<td><span>" + click[i] + "</span>" + (((click[i] - click1[i]) < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                                + "<span>" + ((click[i] - click1[i] >= 0) ? ((isNaN(click1[i])) ? "-" : Math.round(((click[i] - click1[i]) / click1[i]) * 100)) + "%" : "<strong>" + ((isNaN(click1[i])) ? "-" : Math.round(((click[i] - click1[i]) / click1[i]) * 100)) + "%</strong>") + "</span></td>"
                                + "<td><span>" + cost[i] + "</span>" + (((cost[i] - cost1[i]) < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                                + "<span>" + ((cost[i] - cost1[i] >= 0) ? ((isNaN(cost1[i])) ? "-" : Math.round(((cost[i] - cost1[i]) / cost1[i]) * 100)) + "%" : "<strong>" + ((isNaN(cost1[i])) ? "-" : Math.round(((cost[i] - cost1[i]) / cost1[i]) * 100)) + "%</strong>") + "</span></td>"
                                + "<td><span>" + ctr[i] + "%</span>" + ((ctr[i] - ctr1[i] < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                                + "<span>" + (((ctr[i] - ctr1[i]) >= 0) ? ((isNaN(ctr1[i])) ? "-" : Math.round(((ctr[i] - ctr1[i]) / ctr1[i]) * 100)) + "%" : "<strong>" + ((isNaN(ctr1[i])) ? "-" : Math.round(((ctr[i] - ctr1[i]) / ctr1[i]) * 100)) + "%</strong>") + "</span></td>"
                                + "<td><span>" + cpc[i] + "</span>" + ((cpc[i] - cpc1[i] < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                                + "<span>" + (((cpc[i] - cpc1[i]) >= 0) ? ((isNaN(cpc1[i])) ? "-" : Math.round(((cpc[i] - cpc1[i]) / cpc1[i]) * 100)) + "%" : "<strong>" + ((isNaN(cpc1[i])) ? "-" : Math.round(((cpc[i] - cpc1[i]) / cpc1[i]) * 100)) + "%</strong>") + "</span></td>"
                                + "<td><span>" + conversion[i] + "</span>" + ((conversion[i] - conversion1[i] < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                                + "<span>" + (((conversion[i] - conversion1[i]) >= 0) ? ((isNaN(conversion1[i])) ? "-" : Math.round(((conversion[i] - conversion1[i]) / conversion1[i]) * 100)) + "%" : "<strong>" + ((isNaN(conversion1[i])) ? "-" : Math.round(((conversion[i] - cpc1[i]) / conversion1[i]) * 100)) + "%</strong>") + "</span></td>"
                                + "</td><td>-</td><td>-</td></tr>";

                        html_User2 = "<tr class='list2_box2'><td>" + dateEach1[i] + "</td>"
                                + "<td>" + impression1[i] + "</td><td>" + click1[i] + "</td><td>" + cost1[i] + "</td>"
                                + "<td>" + ctr1[i] + "%</td><td>" + cpc1[i] + "</td><td>" + conversion1[i] + "</td><td>-</td><td>-</td></tr>"
                                + "<tr><td colspan='9'>&nbsp;</td></tr>";
                    }

                    $("#userTbody").append(html_User1);
                    $("#userTbody").append(html_User2);
                }
            }
        }
    });
}
/**
 *
 * 账户基础报告
 *
 */
var number = 0;
var jci = 0;
var accountBasisReport = function () {
    $.ajax({
        url: "/account/accountReport",
        type: "GET",
        dataType: "json",
        data: {
            Sorted: sort,
            fieldName: fieldName,
            startJC: startJC,
            limitJC: limitJC
        },
        success: function (data) {
            var basisHtml = "";
            $("#basisAccount").empty();
            $.each(data.rows, function (i, item) {
                number = item.count;
                var ctr = 0;
                if(item.pcImpression != 0){
                    ctr = item.pcClick / item.pcImpression;
                }
                var cpc = 0;
                if(item.pcClick !=0){
                    cpc = item.pcCost / item.pcClick;
                }
                if (i % 2 == 0) {
                    basisHtml = "<tr class='list2_box1'><td>&nbsp;" + item.dateRep + "</td><td>&nbsp;" + item.pcImpression + "</td><td>&nbsp;" + item.pcClick + "</td>"
                            + "<td>&nbsp;" + Math.round(item.pcCost * 100) / 100 + "</td><td>&nbsp;" + Math.round(ctr * 10000) / 100 + "%</td><td>&nbsp;" + Math.round(cpc * 100) / 100 + "</td><td>&nbsp;" + item.pcConversion + "</td>"
                            + "<td>&nbsp;-</td></tr>"
                } else {
                    basisHtml = "<tr class='list2_box2'><td>&nbsp;" + item.dateRep + "</td><td>&nbsp;" + item.pcImpression + "</td><td>&nbsp;" + item.pcClick + "</td>"
                            + "<td>&nbsp;" + Math.round(item.pcCost * 100) / 100 + "</td><td>&nbsp;" + Math.round(ctr * 10000) / 100 + "%</td><td>&nbsp;" + Math.round(cpc * 100) / 100 + "</td><td>&nbsp;" + item.pcConversion + "</td>"
                            + "<td>&nbsp;-</td></tr>"
                }
                $("#basisAccount").append(basisHtml);

            });
            if (jci < 1) {
                var countNumber = 0;
                if (number % 10 == 0) {
                    countNumber = number / 10;
                } else {
                    countNumber = (number / 10);
                }
                var page_html = "<a href='javascript:' id='pageUp' class='nextpage1'><span></span></a>"
                for (var i = 0; i < countNumber; i++) {
                    if (i == 0) {
                        page_html = page_html + "<a href='javascript:' class='ajc' cname='nameJC' onclick='javascript:startJC = " + i + ";limitJC = " + (i + 10) + ";accountBasisReport()'>" + (i + 1) + "</a>";
                    } else {
                        page_html = page_html + "<a href='javascript:' cname='nameJC' onclick='javascript:startJC = " + (i * 10) + ";limitJC = " + (i * 10 + 10) + ";accountBasisReport()'>" + (i + 1) + "</a>";
                    }
                }
                page_html = page_html + "<a href='javascript:' id='pageDown' class='nextpage2'><span></span></a>" +
                        "<span style='margin-right:10px;'>跳转到 <input type='text' id='goID' class='price'></span>&nbsp;&nbsp;<a href='javascript:' id='go' class='page_go'> GO</a>"
                $("#pageJC").append(page_html);
                jci++;
            }
        }
    });
}

//曲线图
var curve = function () {
    $("#container").show();
    $("#imprDiv").hide();
    $("#clickDiv").hide();
    $("#costDiv").hide();
    $("#convDiv").hide();
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
//饼状图
var a = 1;
var pieChart = function (showData, showName, showId) {
    $(showId).show();
    $("#container").hide()
    if (a == 1) {
        //使用饼状图进行颜色渐变
        Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function (color) {
            return {
                radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
                stops: [
                    [0, color],
                    [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
                ]
            };
        });
        a++;
    }
    //加载开始
    $(showId).highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            events: {
                load: function () {
                    // set up the updating of the chart each second
                    var series = this.series[0];
                    setInterval(function () {
                        series.setData(showData);
                    }, 2000);
                }
            }
        },
        title: {
            text: showName + '占有百分比',
            style:{"font-weight":"bold","font-size":"18px","color":"#fab30b"}
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                }
            }
        },
        series: [
            {
                type: 'pie',
                name: '占有率',
                data: [
                ]
            }
        ]
    });
}
//基础报告分页手动跳转
$("body").on("click", "#go", function () {
    var juo = ($('#goID').val() * 10 - 1);
    if (juo <= (number + 9)) {
        startJC = ($('#goID').val() * 10 - 10);
        limitJC = startJC + 10 - 1;
        accountBasisReport();
    }
});

var noneStart = 0;
var noneEnd = 10;
//基础报告上一页
$("body").on("click", "#pageUp", function () {
    if(noneStart >=10){
        $("a[cname=nameJC]").hide();
        noneStart -= 10;
        noneEnd -= 10;
        for(var i = noneStart;i<=noneEnd;i++){
            $("a[cname=nameJC]").eq(i).show();
        }
    }
});
//基础报告下一页
$("body").on("click", "#pageDown", function () {
    if(noneEnd <number/10){
        $("a[cname=nameJC]").hide();
        noneStart += 10;
        noneEnd += 10;
        for(var i = noneStart;i<=noneEnd;i++){
            $("a[cname=nameJC]").eq(i).show();
        }
    }
});
$("body").on("click", "a[cname=nameJC]", function () {
    $(this).addClass('ajc').siblings().removeClass('ajc');
});
//账户统计分页手动跳转
$("body").on("click", "#goVS", function () {
    if (($('#goVSID').val() * 10 - 1) <= (pageNumberVS + 9)) {
        startVS = ($('#goVSID').val() * 10 - 10);
        limitVS = startVS + 10 - 1;
        reportDataVS();
    }
});

var noneVsStart = 0;
var noneVsEnd = 10;
//账户统计上一页
$("body").on("click", "#pageUpVS", function () {
    if(noneVsStart >=10){
        $("a[cname=nameVS]").hide();
        noneVsStart -= 10;
        noneVsEnd -= 10;
        for(var i = noneVsStart;i<=noneVsEnd;i++){
            $("a[cname=nameVS]").eq(i).show();
        }
    }
});
//账户下一页
$("body").on("click", "#pageDownVS", function () {
    if(noneVsEnd <pageNumberVS/10){
        $("a[cname=nameVS]").hide();
        noneVsStart += 10;
        noneVsEnd += 10;
        for(var i = noneVsStart;i<=noneVsEnd;i++){
            $("a[cname=nameVS]").eq(i).show();
        }
    }
});
$("body").on("click", "a[cname=nameVS]", function () {
    $(this).addClass('ajc').siblings().removeClass('ajc');
});

//明细报告分页手动跳转
$("body").on("click", "#goDet", function () {
    if (($('#goDetID').val() * 30 - 1) <= (pageDetNumber + 29)) {
        startDet = ($('#goDetID').val() * 30 - 30);
        limitDet = startDet + 30 - 1;
        reportData();
    }
});
var noneNumStart=0;
var noneNumEnd=10;
//明细报告上一页
$("body").on("click", "#pageUpDet", function () {
    if(noneNumStart >=10){
        $("a[cname=nameDet]").hide();
        noneNumStart -= 10;
        noneNumEnd -= 10;
        for(var i = noneNumStart;i<=noneNumEnd;i++){
            $("a[cname=nameDet]").eq(i).show();
        }
    }
});
//明细报告下一页
$("body").on("click", "#pageDownDet", function () {
    if(noneNumEnd < pageDetNumber/30){
        $("a[cname=nameDet]").hide();
        noneNumStart +=10;
        noneNumEnd+=10;
        for(var i = noneNumStart;i<=noneNumEnd;i++){
            $("a[cname=nameDet]").eq(i).show();
        }
    }
});
$("body").on("click", "a[cname=nameDet]", function () {
    $(this).addClass('ajc').siblings().removeClass('ajc');
});
function TestBlack(TagName) {
    var obj = document.getElementById(TagName);
    if (obj.style.display == "") {
        obj.style.display = "none";
    } else {
        obj.style.display = "";
    }
}


</script>
</html>
