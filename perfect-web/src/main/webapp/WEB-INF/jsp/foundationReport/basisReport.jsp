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
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>大数据智能营销</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
    <%--<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">--%>
    <link rel="stylesheet" type="text/css" media="all"
          href="${pageContext.request.contextPath}/public/themes/flick/daterangepicker-bs2.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/media.css">
    <%--<link href="${pageContext.request.contextPath}/public/themes/flick/font-awesome.min.css" rel="stylesheet">--%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">--%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/jquery.cxcalendar.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/css/pagination/pagination.css">
    <script type="text/javascript" src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <style type="text/css">
        /*日历*/
        .list2 table .list2_top td, th {
            color: #000000;
        }

        .example {
            background: #FFF;
            width: 650px;
            margin: 20px auto;
            padding: 15px;
            position: relative;
            -moz-border-radius: 3px;
            -webkit-border-radius: 3px;
            line-height: 24px;
        }

        .ui-progressbar-value {
            display: block !important;
            color: #F7B54A;
        }

        .pbar {
            overflow: hidden;
        }

        .percent {
            position: relative;
            text-align: right;
        }

        .elapsed {
            position: relative;
            font-size: 14px;
            margin: 10px 0px 0px 0px;
            color: #F7B54A;
            font-weight: bold;
        }

        .pbar .ui-widget-header {
            border: 1px solid #ddd;
            background: #F7B54A;
            color: #444;
            font-weight: bold;
        }

        #percentNumber b {
            line-height: 2em;
            margin: 0px 5px 0 0;
        }

        #trTop .shijian_top {
            width: 180px;
        }

        #userStits, #shujuAll {
            background: #D8E1E8;
        }

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

        .list3 {
            min-height: 100px;
        }

    </style>

</head>
<body>
<%--<div id="background" class="background"></div>
<div id="progressBar" class="progressBar">数据加载中，请稍等...</div>--%>
<jsp:include page="../homePage/pageBlock/head.jsp"/>
<div class="concent over">
    <jsp:include page="../homePage/pageBlock/nav.jsp"/>
    <div class="mid over ">
        <div class="title_box">
            <div class="on_title over">
                <a href="#">
                    数据报告
                </a>
                &nbsp;&nbsp;>&nbsp;&nbsp;<span>基础报告</span>
            </div>
        </div>
        <div id="tab">
            <ul class="tab_menu">
                <li class="selected">账户报告</li>
                <li>明细数据</li>
            </ul>
            <div class="tab_box" style=" padding:inherit;">
                <div class="containers  over">
                    <div class="list01_under3 over">
                        <div class="list01_top over"><Span>基础统计</Span>
                            <button type="button" class="question  btn btn-default" data-toggle="tooltip"
                                    data-placement="bottom"
                                    title="通过对账户数据全方位的科学分析评估， 对账户数据对比、地域数据统计分析、广告系列、广告组、关键词、广告创意数据等进行统计分析。"></button>
                        </div>
                        <div class="list3 wd">
                            <table border="0" cellspacing="0" cellspacing="0" width="100%">
                                <thead>
                                <tr class="list2_top">
                                    <td>&nbsp;<span>时间</span> <b>
                                    </b></td>
                                    <td>&nbsp;<span>展现量</span><b>
                                    </b></td>
                                    <td>&nbsp;<span>点击量</span><b>
                                    </b></td>
                                    <td>&nbsp;<span>消费</span><b>
                                    </b></td>
                                    <td>&nbsp;<span>点击率</span><b>
                                    </b></td>
                                    <td>&nbsp;<span>平均点击价格</span><b>
                                    </b></td>
                                    <td>&nbsp;<span>转化(网页)</span><b>
                                    </b></td>
                                    <td>&nbsp;<span>转化(商桥)</span><b>
                                    </b></td>
                                </tr>
                                </thead>
                                <tbody id="basisAccount"></tbody>
                            </table>
                            <br/>

                            <div class="page2 fl" id="pageJC"></div>
                        </div>
                    </div>
                    <div class="number_concent over">
                        <div class="list01_top over"><Span>账户报告</Span>
                        </div>
                        <div class="shuju_detali over">
                            <ul>
                                <li class="date">选择时间范围：<input type="text" class="time_input" placeholder="请选择查询时间,默认昨天"
                                                               readonly>
                                    <input name="reservation" id="fzk" type="image" cname="dateClick"
                                           onclick="_posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                                           src="${pageContext.request.contextPath}/public/img/date.png" value="">
                                    <input type="checkbox" id="checkboxInput" style="margin:6px 3px 0px 5px; ">
                                    比较范围
                                    <input name="mydate" type="text" id="inputTow" cname="dateClick" readonly
                                           style=" display:none;height:24px;width:150px;border:1px solid #dadada; padding:0 12px;background:#fff url('/public/img/date.png') right 0px no-repeat;">
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
                                    <a href="javascript:" cname="2">分周</a><span id="fenyeo">|</span>
                                    <a href="javascript:" id="fenyue" cname="3">分月</a></li>
                            </ul>

                            <input type="hidden" id="devicesUser" value="0">
                            <input type="hidden" id="dateLisUser" value="0">
                            <input type="hidden" id="checkboxhidden" value="0">
                            <a href="javascript:" id="userClick" class="become fl" style="margin-right:50px; "> 生成报告</a>

                            <div id="downAccountReport">

                            </div>
                        </div>
                    </div>
                    <div class="list01_under3 over">
                        <div class="list3 over">
                            <table border="0" cellspacing="0" cellspacing="0">
                                <thead>
                                <tr class="list2_top" id="trTop">
                                    <td class="shijian_top">&nbsp;<span>时间</span><b>
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
                                <tbody id="userStits">

                                </tbody>
                            </table>
                        </div>
                        <!--<div class="page2 fl" id="pageVS"></div>-->
                        <div id="pageNumberVSSelect " class="over">
        <span class='fr'>每页显示<select id='importKeywordSelVS' onchange='selectChangeVs()'>
            <option value='20'>20个</option>
            <option value='50'>50个</option>
            <option value='100'>100个</option>
        </select> </span>
                        </div>
                        <div id="pagination1" class="pagination over"></div>
                    </div>
                </div>
                <div class="containers hides over">
                    <div class="number_concent over">
                        <div class="list01_top over"><Span>明细数据</Span>
                        </div>
                        <div class="shuju_detali over">
                            <ul>
                                <li>选择时间范围：
                                    <input type="text" class="time_input" placeholder="请选择查询时间,默认昨天" readonly>
                                    <input name="reservation" type="image" cname="dateClick"
                                           src="${pageContext.request.contextPath}/public/img/date.png" readonly>
                                </li>
                                <li id="reportType">选择报告类型：
                                    <a href="javascript:" onclick="OtherSearch()" class="current"
                                       cname="1">结构报告</a><span>|</span>
                                    <a href="javascript:" onclick="OtherSearch()" cname="2">关键词报告</a><span>|</span>
                                    <a href="javascript:" onclick="OtherSearch()" cname="3">创意报告</a><span>|</span>
                                    <a href="javascript:" onclick="OtherSearch()" cname="4">分地域报告</a><span>|</span>
                                    <a href="javascript:" id="SearchReport">搜索词报告</a>
                                </li>
                                <li id="device" class="searchhide">选择推广设备：
                                    <a href="javascript:" class="current" cname="0">全部</a><span>|</span>
                                    <a href="javascript:" cname="1">计算机</a><span>|</span>
                                    <a href="javascript:" cname="2">移动设备</a>
                                </li>
                                <li id="dateLi" class="searchhide">选择时间单位：
                                    <a href="javascript:" class="current" cname="0">默认</a><span>|</span>
                                    <a href="javascript:" cname="1">分日</a><span>|</span>
                                    <a href="javascript:" cname="2">分周</a><span>|</span>
                                    <a href="javascript:" cname="3">分月</a>
                                </li>
                            </ul>

                            <a href="javascript:" id="shengc" class="become searchhide fl" style="margin-right:50px; ">
                                生成报告</a>

                            <div id="downReport" class="searchhide">

                            </div>
                            <input type="hidden" id="jindut" value="">
                        </div>
                        <input type="hidden" id="reportTypes" value="1">
                        <input type="hidden" id="devices" value="0">
                        <input type="hidden" id="dateLis" value="0">
                    </div>
                    <div class="contant searchhide over">
                        <%--<div class="download over fr"><a href="#">下载全部</a></div>--%>
                        <div class="list01_under3 over">
                            <div class="tubiao2 over" style="width: 80%;margin:0 auto; ">
                                <div id="imprDiv" style="width:100%;height:530px;display: none;"></div>
                                <div id="pm_data" style="display: none;"></div>
                            </div>
                            <div class="list3 wd" style="overflow-x: auto; width: 100%;">
                                <table border="0" cellspacing="0" cellspacing="0">
                                    <thead id="shujuthead">

                                    </thead>
                                    <tbody id="shuju">

                                    </tbody>
                                    <tbody id="shujuAll">

                                    </tbody>
                                </table>
                                <img style="margin-left: 50%; display: none" id="pathImages"
                                     src='${pageContext.request.contextPath}/public/img/loading.gif'/>
                            </div>
                            <!--<div class="page2 fl" id="pageDet"></div>-->
                            <div id="pageNumberDetSelect" class="over">
                <span class='fr'>每页显示<select id='importKeywordSelDet' onchange='selectChangeDet()'>
                    <option value='20'>20个</option>
                    <option value='50'>50个</option>
                    <option value='100'>100个</option>
                </select> </span>
                            </div>
                            <div id="pagination2" class="pagination over"></div>
                            <br/>

                            <div class="tubiao2 over">
                                <div id="containerLegend"></div>
                                <div id="container" style="width:100%;height:400px;display: none"></div>
                            </div>

                        </div>
                    </div>
                </div>

                <%-----------------------------------------------------------------------------------------------------------------------------%>

                <div class="searh_report hides over">
                    <div class="number_concent over">
                        <div class="shuju_detali over">
                            <ul>
                                <%--          <li>选择时间范围：
                                              <input type="text" class="time_input" placeholder="请选择查询时间,默认昨天" readonly>
                                              <input name="reservation" type="image" cname="dateClick"
                                                     onclick="_posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                                                     src="${pageContext.request.contextPath}/public/img/date.png" readonly>
                                          </li>--%>
                                <li id="dateUnit">时间单位：
                                    <a href="javascript:" class="current" cname="0">合计</a><span>|</span>
                                    <a href="javascript:" cname="1">每日</a><span>|</span>
                                </li>
                                <li id="putin">选择投放设备：
                                    <a href="javascript:" class="current" cname="0">全部</a><span>|</span>
                                    <a href="javascript:" cname="1">计算机</a><span>|</span>
                                    <a href="javascript:" cname="2">移动设备</a>
                                </li>
                                <li>地域范围:
                                    <a href="javascript:void(0)" id="chooseRegion">选择地域 <input type="image"
                                                                                               src="../public/img/zs_input.png"></a>
                                </li>
                            </ul>

                            <a href="javascript:" id="createReport" class="become fl"
                               style="margin:10px 50px 10px 0 ; "> 生成报告</a>

                            <div id="downReportSearch">

                            </div>
                            <input type="hidden" id="jindut1" value="">
                        </div>
                    </div>
                    <div class="contant over">
                        <%--<div class="download over fr"><a href="#">下载全部</a></div>--%>
                        <div class="list01_under3 over">
                            <div class="tubiao2 over" style="width: 80%;margin:0 auto; ">
                                <div id="imprDiv1" style="width:100%;height:530px;display: none;"></div>
                                <div id="pm_data1" style="display: none;"></div>
                            </div>
                            <div class="list3 wd" style="overflow-x: auto; width: 100%;">
                                <table border="0" cellspacing="0" cellspacing="0">
                                    <thead id="shujuthead1">
                                    <tr class='list2_top'>
                                        <td>&nbsp;<span>时间</span><b><p><input class='one' type='button'
                                                                              onclick='javascript:sorts = -11;reportData()'>
                                        </p>

                                            <p><input class='two' type='button'
                                                      onclick='javascript:sorts = 11;reportData()'></p></b>
                                        </td>
                                        <td>&nbsp;<span>推广计划</span><b><p><input class='one' type='button'></p>

                                            <p><input class='two' type='button'></p></b></td>
                                        <td>&nbsp;<span>推广单元</span><b><p><input class='one' type='button'
                                                                                onclick='javascript:sorts = -7;reportData()'>
                                        </p>

                                            <p><input class='two' type='button'
                                                      onclick='javascript:sorts = 7;reportData()'></p></b>
                                        </td>
                                        <td>&nbsp;<span>搜索引擎</span><b><p><input class='one' type='button'
                                                                                onclick='javascript:sorts = -3;reportData()'>
                                        </p>

                                            <p><input class='two' type='button'
                                                      onclick='javascript:sorts = 3;reportData()'></p></b>
                                        </td>
                                        <td>&nbsp;<span>点击量</span><b><p><input class='one' type='button'
                                                                               onclick='javascript:sorts = -5;reportData()'>
                                        </p>

                                            <p><input class='two' type='button'
                                                      onclick='javascript:sorts = 5;reportData()'></p></b>
                                        </td>
                                        <td>&nbsp;<span>展现量</span><b><p><input class='one' type='button'
                                                                               onclick='javascript:sorts = -4;reportData()'>
                                        </p>

                                            <p><input class='two' type='button'
                                                      onclick='javascript:sorts = 4;reportData()'></p></b>
                                        </td>
                                        <td>&nbsp;<span>点击率</span><b><p><input class='one' type='button'></p></b></td>

                                        <td>&nbsp;<span>搜索词</span><b><p><input class='one' type='button'
                                                                               onclick='javascript:sorts = -6;reportData()'>
                                        </p>

                                            <p><input class='two' type='button'
                                                      onclick='javascript:sorts = 6;reportData()'></p></b>
                                        </td>
                                        <td>&nbsp;<span>关键词</span><b><p></p>

                                            <p></p></b></td>
                                        <td>&nbsp;<span>精确匹配扩展(地域词)触发</span><b><p></p>

                                            <p></p></b></td>
                                    </tr>
                                    </thead>
                                    <tbody id="searchWordTbody">

                                    </tbody>
                                    <tbody id="shujuAll1">

                                    </tbody>
                                </table>
                                <img style="margin-left: 50%; display: none" id="pathImages1"
                                     src='${pageContext.request.contextPath}/public/img/loading.gif'/>
                            </div>
                        </div>
                        <div id="pagination3" class="pagination over"></div>
                        <br/>

                        <div class="tubiao2 over">
                            <div id="containerLegend1"></div>
                            <div id="container1" style="width:100%;height:400px;display: none"></div>
                        </div>

                    </div>
                </div>
                <input type="hidden" id="putinInfo" value="0">
            </div>


            <%----------------------------------------------------------------------------————---------------------------------------------%>

        </div>
    </div>
    <%--设置推广地域--%>
    <jsp:include page="../promotionAssistant/alert/Region.jsp"/>
    <jsp:include page="../homePage/pageBlock/footer.jsp"/>
</div>
</div>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/jqueryui/1.11.2/jquery-ui.min.js"></script>

<%--jquery-daterangepicke--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.jQuery.js"></script>--%>
<%--<script type="text/javascript"  src="${pageContext.request.contextPath}/public/js/jquery.ui.datepicker-zh-CN.js"></script>--%>
<%--end--%>
<%--bootstrap-daterangepicker--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/bootstrap-daterangepicker-moment.js"></script>


<%--end--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.cxcalendar.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/pagination/jquery.pagination.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/echarts/2.1.10/echarts-all.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/json2/20140204/json2.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery.pin/1.0.1/jquery.pin.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/report/reportProgress.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/report/reportContext.js"></script>
<script type="text/javascript">

    $(function () {
        $("[data-toggle='tooltip']").tooltip();
    });

    function OtherSearch() {
        $(".searh_report").hide();
        $(".searchhide").show();
        $(".number_concent").css("border-bottom", "1px solid #dadadd");
        $(".number_concent").css("padding-bottom", "20px");
    }

    $(function () {
        var mydate = new Date();
        var str = "" + mydate.getFullYear() + "-";
        str += (mydate.getMonth() + 1) + "-";
        str += (mydate.getDate());
        var time = str + " " + " 至 " + " " + str
        $("#tacittime").val(time);
        $("#tacittime1").val(time);
        $("#tacittime2").val(time);
    })


    //bootstrap-daterangepicker-setting
    //    $("input[name=reservation]").daterangepicker({
    //       $('#fzk').click(function(){
    //
    //       })

    //    $('#fzk').click(function(){
    //        console.log($('.daterangepicker').css('display'));
    //        console.log($('.daterangepicker').css('display')=='block');
    //
    //        if($('.daterangepicker').css('display')=='block'){
    //            $('.daterangepicker').css("display", "none");
    //        }
    //        console.log($('.daterangepicker').css('display'));
    //    })
    //    console.log($("#daterangepicker").css('display'));


    //end
</script>
</body>

</html>
