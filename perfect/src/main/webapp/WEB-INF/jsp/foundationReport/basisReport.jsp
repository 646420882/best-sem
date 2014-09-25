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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/pagination/pagination.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/respond.js"></script>
    <style>
        .example{background:#FFF;width:650px;font-size:80%;margin:20px auto;padding:15px;position:relative;-moz-border-radius: 3px;-webkit-border-radius: 3px}
         h3 {text-align:center}
        .ui-progressbar-value {display:block !important;color: #F7B54A;}
        .pbar {overflow: hidden;}
        .percent {position:relative;text-align: right;}
        .elapsed {position:relative;margin: 10px 0px 0px 250px;}
        .pbar .ui-widget-header {
            border: 1px solid #ddd;
            background: #F7B54A;
            color: #444;
            font-weight: bold;
        }

    </style>

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
        <%--<div id="pageNumberJcSelect">
            <span class='fr'>每页显示<select id='importKeywordSel' onchange='selectChange()'><option value='20'>20个</option><option value='50'>50个</option><option value='100'>100个</option></select> </span>
        </div>--%>
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
            <tbody id="userStits">

            </tbody>
        </table>
    </div>
    <!--<div class="page2 fl" id="pageVS"></div>-->
    <div id="pagination1" class="pagination"></div>
    <div id="pageNumberVSSelect">
        <span class='fr'>每页显示<select id='importKeywordSelVS' onchange='selectChangeVs()'><option value='20'>20个</option><option value='50'>50个</option><option value='100'>100个</option></select> </span>
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
            <input type="hidden" id="jindut" value="">
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
                    <tbody id="shujuAll">

                    </tbody>
                </table>
            </div>
            <!--<div class="page2 fl" id="pageDet"></div>-->
            <div id="pagination2" class="pagination"></div>
            <div id="pageNumberDetSelect">
                <span class='fr'>每页显示<select id='importKeywordSelDet' onchange='selectChangeDet()'><option value='20'>20个</option><option value='50'>50个</option><option value='100'>100个</option></select> </span>
            </div>
            <br/>
            <div class="tubiao2 over">
                <div id="containerLegend"></div>
                <div id="container" style="width:100%;height:400px;display: none"></div>
                <div id="imprDiv" style="width:45%;display: none;float: left"></div>
                <div id="clickDiv" style="width:54%;display: none;float: right"></div>
                <div id="costDiv" style="width:45%;display: none;float: left;margin-top: 40px;"></div>
                <div id="convDiv" style="width:53%;display: none;float: right;margin-top: 40px;"></div>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/pagination/jquery.pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/4.0.1/highcharts.js"></script>
<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/4.0.1/modules/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.pin.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/report/reportProgress.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/report/reportContext.js"></script>



</html>
