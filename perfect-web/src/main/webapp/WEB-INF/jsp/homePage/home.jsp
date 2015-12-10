<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 14-7-25
  Time: 下午6:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    <title>大数据智能营销</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <%--<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">--%>
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css">--%>
    <link rel="stylesheet" type="text/css" media="all"
          href="${pageContext.request.contextPath}/public/themes/flick/daterangepicker-bs2.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">

    <%--    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/zTreeStyle/Normalize.css">--%>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/css/pagination/pagination.css">
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">--%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/media.css">
    <%--<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/public/css/images/favicon.ico"/>--%>
    <script type="text/javascript" src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <script>
        var _pct = _pct || [];
        (function () {
            var hm = document.createElement("script");
            hm.src = "//t.best-ad.cn/t.js?tid=76c005e89e020c6e8813a5adaba384d7";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
    <style>
        /*日历*/
        .list2 table .list2_top td, th {
            color: #000000;
        }

        /*日历*/
        .page2 .ajc {
            background: #ffb900;
            border: 1px solid #fab30b;
            color: #fff;
        }

    </style>
</head>
<body>
<div id="background" class="background hides"></div>
<div id="progressBar1" class="progressBar hides">正在生成报告，请稍等...</div>
<jsp:include page="pageBlock/head.jsp"/>
<div class="concent over">
    <jsp:include page="pageBlock/nav.jsp"/>
    <div class="mid over">
        <div class="title_box">
            <div class="on_title over">
                <a href="#">
                    账户全景
                </a>
                &nbsp;&nbsp;>&nbsp;&nbsp;<span id="TitleName">账户概览</span>
            </div>
        </div>

        <div id="tab">
            <ul class="tab_menu">
                <li class="selected">
                    账户概览
                </li>
                <li id="liClick">
                    账户表现
                </li>
                <li>
                    质量度分析
                </li>
                <li>
                    关键词监控
                </li>
            </ul>
            <div class="tab_box">
                <div class="containers">
                    <div class="list01 over">
                        <div class="list01_top over">
                            <Span>近期概览</Span>
                            <button type="button" class="question  btn btn-default" data-toggle="tooltip"
                                    data-placement="bottom" title="总览大数据指标，查看维度昨天、近七天、近30天及自定义时间范围.
"></button>
                            <ul id="clickLis">
                                <li class="current"  ><a href="javascript:"  onclick="lisClick(this,1)">昨天</a></li>
                                <li><a href="javascript:" onclick="lisClick(this,7)">近7天</a></li>
                                <li><a href="javascript:" onclick="lisClick(this,30)">近30天</a></li>
                                <li class="date date_choice" onclick="javascript:genre = 'accountOverview';$(this).addClass('current');" id="date"><a href="javascript:"
                                                              onclick="lisClick(this,null);"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span><b>自定义</b><%--<input
                                        name="reservation"
                                        type="image"
                                        onclick="javascript:genre = 'accountOverview';$(this).parent().parent().addClass('current');changedLiState($(this).parent()); _posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                                        src="${pageContext.request.contextPath}/public/img/date.png" id="test">--%>
                                </a>
                                </li>
                            </ul>
                        </div>
                        <div class="datebox hides"></div>
                        <div class="list01_under2 over">
                            <ul id="countAssisted">
                            </ul>
                        </div>
                        <div class="list01_under2 list01_color  over">
                            <ul>
                                <li>
                                    <div class="blue1 fr wd1"></div>
                                    <div class="blue2 fl wd2">
                                        <p>展现次数</p>
                                        <Span class="impression"></Span>
                                    </div>
                                </li>
                                <li>
                                    <div class="green1 fr wd1"></div>
                                    <div class="green2 fl wd2">
                                        <p>点击次数</p>
                                        <Span class="click"></Span>
                                    </div>
                                </li>
                                <li>
                                    <div class="red1 fr wd1"></div>
                                    <div class="red2 fl wd2">
                                        <p>消费</p>
                                        <Span class="cos"></Span>
                                    </div>
                                </li>
                                <li>
                                    <div class="yellow1 fr wd1"></div>
                                    <div class="yellow2 fl wd2">
                                        <p>转化次数</p>
                                        <Span class="conversion"></Span>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="containers  hides over">
                    <div class="list01">
                        <div class="list01_top over">
                            <Span>账户趋势图</Span>
                            <button type="button" class="question  btn btn-default" data-toggle="tooltip"
                                    data-placement="bottom" title="分色标注了波动最大的指标及其升降趋势便于用户对关键数据信息一目了然。
"></button>

                            <ul id="clickqushi">
                                <li class="current">
                                    <a onclick="loadPerformanceCurve(this,7)">
                                        近7天
                                    </a>
                                </li>
                                <li>
                                    <a onclick="loadPerformanceCurve(this,30)">
                                        近30天
                                    </a>
                                </li>
                         <%--       <li class="date">
                                    <a href="javascript:void(0)">
                                        自定义
                                        <input name="reservation" class=" fa fa-calendar " type="image"
                                               onclick="javascript:genre = 'importPerformanceCurveDefault';$(this).parent().parent().addClass('current');changedLiState($(this).parent()); _posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                                               src="${pageContext.request.contextPath}/public/img/date.png">
                                    </a>
                                </li>--%>
                                <li class="date date_choice" onclick="javascript:genre = 'importPerformanceCurveDefault';$(this).addClass('current');$(this).prevAll().removeClass('current');changedLiState()" ><a href="javascript:void(0)"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span><b>自定义</b></a>
                                </li>
                            </ul>
                        </div>
                        <div class="datebox hides"></div>
                        <div class="shuju">
                            <div id="containerLegend"></div>
                            <div id="container" style="width:100%;height:400px"></div>
                        </div>
                    </div>
                    <div class="list01_under3 over">
                        <div class="list01_top over">
                            <Span>分日表现</Span>
                            <button type="button" class="question  btn btn-default" data-toggle="tooltip"
                                    data-placement="bottom" title="着重表现近七天、近30天等时间范围的各项细致指标数据值。"></button>
                            <ul id="clickfenri">
                                <li class="current">
                                    <a onclick="$('#pageUser').empty();judgeDet = 0;loadPerformance(this,7);">
                                        近7天
                                    </a>
                                </li>
                                <li>
                                    <a onclick="$('#pageUser').empty();judgeDet = 0;loadPerformance(this,30)">
                                        近30天
                                    </a>
                                </li>
                       <%--         <li class="date">
                                    <a href="javascript:void(0)">
                                        自定义
                                        <input name="reservation" id="expression" class=" fa fa-calendar " type="image"
                                               onclick="javascript:$('#pageUser').empty();judgeDet = 0;genre = 'importPerformanceDefault';$(this).parent().parent().addClass('current');changedLiState($(this).parent()); _posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                                               src="${pageContext.request.contextPath}/public/img/date.png">
                                    </a>
                                </li>--%>
                                <li class="date date_choice" onclick="javascript:$('#pageUser').empty();judgeDet = 0;genre = 'importPerformanceDefault';$(this).addClass('current');$(this).prevAll().removeClass('current')" ><a href="javascript:void(0)"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span><b>自定义</b></a>
                                </li>
                            </ul>
                            <div class="download fr over">
                                <a href="javascript:downAccount();">下载全部 </a>
                            </div>
                        </div>
                        <div class="datebox hides"></div>

                        <div class="list2 fenrilist wd">
                            <table border="0" cellspacing="0" cellspacing="0">
                                <thead>
                                <tr class="list2_top">
                                    <td>
                                        <ul>
                                            <li>
                                                <span>时间</span><b>
                                                <p>
                                                    <input class="one" type="button"
                                                           onclick="javascript:category = 'date';sort = -1;loadPerformance(statDate)">
                                                </p>

                                                <p>
                                                    <input class="two" type="button"
                                                           onclick="javascript:category = 'date';sort = 1;loadPerformance(statDate)">
                                                </p></b>
                                            </li>
                                            <li>
                                                <span>展现量</span><b>
                                                <p>
                                                    <input class="one" type="button"
                                                           onclick="javascript:category = 'impression';sort = -2;loadPerformance(statDate)">
                                                </p>

                                                <p>
                                                    <input class="two" type="button"
                                                           onclick="javascript:category = 'impression';sort = 2;loadPerformance(statDate)">
                                                </p></b>
                                            </li>
                                            <li>
                                                <span>点击量</span><b>
                                                <p>
                                                    <input class="one" type="button"
                                                           onclick="javascript:category = 'click';sort = -3;loadPerformance(statDate)">
                                                </p>

                                                <p>
                                                    <input class="two" type="button"
                                                           onclick="javascript:category = 'click';sort = 3;loadPerformance(statDate)">
                                                </p></b>
                                            </li>
                                            <li>
                                                <span>消费</span><b>
                                                <p>
                                                    <input class="one" type="button"
                                                           onclick="javascript:category = 'cost';sort = -4;loadPerformance(statDate)">
                                                </p>

                                                <p>
                                                    <input class="two" type="button"
                                                           onclick="javascript:category = 'cost';sort = 4;loadPerformance(statDate)">
                                                </p></b>
                                            </li>
                                            <li>
                                                <span>点击率</span><b>
                                                <p>
                                                    <input class="one" type="button"
                                                           onclick="javascript:category = 'ctr';sort = -6;loadPerformance(statDate)">
                                                </p>

                                                <p>
                                                    <input class="two" type="button"
                                                           onclick="javascript:category = 'ctr';sort = 6;loadPerformance(statDate)">
                                                </p></b>
                                            </li>
                                            <li>
                                                <span>平均点击价格</span><b>
                                                <p>
                                                    <input class="one" type="button"
                                                           onclick="javascript:category = 'cpc';sort = -5;loadPerformance(statDate)">
                                                </p>

                                                <p>
                                                    <input class="two" type="button"
                                                           onclick="javascript:category = 'cpc';sort = 5;loadPerformance(statDate)">
                                                </p></b>
                                            </li>
                                            <li>
                                                <span>转化(页面)</span><b>
                                                <p>
                                                    <input class="one" type="button"
                                                           onclick="javascript:category = 'conversion';sort = -7;loadPerformance(statDate)">
                                                </p>

                                                <p>
                                                    <input class="two" type="button"
                                                           onclick="javascript:category = 'conversion';sort = 7;loadPerformance(statDate)">
                                                </p></b>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                </thead>
                                <tbody id="performance">

                                </tbody>
                            </table>

                            <div class="download over">

                                <%--<div class="page2 fl" id="pageUser"></div>--%>
                <span class="fr">每页显示
                            <select id="performanceLimit"
                                    onchange="javascript:limitPer = $('#performanceLimit option:selected').val();startPer = 0;endPer = limitPer;loadPerformance();">
                                <option selected="selected" value="10">10个</option>
                                <option value="20">20个</option>
                                <option value="30">30个</option>
                            </select> </span>
                            </div>
                            <div id="pagination1" class="pagination"></div>
                        </div>
                    </div>
                </div>
                <div class="containers hides over">
                    <div class="list01_top over">
                        <Span>质量度分析</Span>
                        <button type="button" class="question  btn btn-default" data-toggle="tooltip"
                                data-placement="bottom" title="SEM核心基础元素，它影响着优化策略和思路的变化，实时把控质量度星级指标，助力核心优化。"></button>
                        <%--<ul id="keywordQualityClass">
                            <li class="current">
                                <a onclick="loadKeywordQualityData(this, 1);">
                                    昨天
                                </a>
                            </li>
                            <li>
                                <a onclick="loadKeywordQualityData(this, 7);">
                                    近7天
                                </a>
                            </li>
                            <li>
                                <a onclick="loadKeywordQualityData(this, 30);">
                                    近30天
                                </a>
                            </li>
                            <li class="date">
                                <a>
                                    自定义
                                    <input name="reservation" type="image"
                                           onclick="javascript:genre = 'keywordQualityCustom';$(this).parent().parent().addClass('current');changedLiState($(this).parent()); _posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                                           src="${pageContext.request.contextPath}/public/img/date.png">
                                </a>
                            </li>
                        </ul>--%>

                    </div>
                    <div class="download over ">
                        <a href="javascript:downloadKeywordQualityCSV();" class="fr">
                            下载全部
                        </a>
                        <span class="fr" style=" color:#000; font-weight:bold;line-height:26px;">查看完整版数据请点击下载全部→</span>
                    </div>
                    <div class="list2 zhilianglist wd">
                        <table id="keywordQualityTab" border="0" cellspacing="0" cellspacing="0">
                            <tr class="list2_top">
                                <td>
                                    <ul>
                                        <li class="home_quality">
                                            <span>质量度</span><b>
                                            <p>
                                                <input class="one" type="button">
                                            </p>

                                            <p>
                                                <input class="two" type="button">
                                            </p></b>
                                        </li>
                                        <li>
                                            <span>关键词数</span><b>
                                            <p>
                                                <input class="one" type="button">
                                            </p>

                                            <p>
                                                <input class="two" type="button">
                                            </p></b>
                                        </li>
                                        <li>
                                            <span>展现</span><b>
                                            <p>
                                                <input class="one" type="button"
                                                       onclick="javascript:category = 'impression';sort = -1;loadKeywordQualityData();"/>
                                            </p>

                                            <p>
                                                <input class="two" type="button"
                                                       onclick="javascript:category = 'impression';sort = 1;loadKeywordQualityData();"/>
                                            </p></b>
                                        </li>
                                        <li>
                                            <span>点击</span><b>
                                            <p>
                                                <input class="one" type="button"
                                                       onclick="javascript:category = 'click';sort = -1;loadKeywordQualityData();"/>
                                            </p>

                                            <p>
                                                <input class="two" type="button"
                                                       onclick="javascript:category = 'click';sort = 1;loadKeywordQualityData();"/>
                                            </p></b>
                                        </li>
                                        <li>
                                            <span>点击率</span><b>
                                            <p>
                                                <input class="one" type="button"
                                                       onclick="javascript:category = 'ctr';sort = -1;loadKeywordQualityData();"/>
                                            </p>

                                            <p>
                                                <input class="two" type="button"
                                                       onclick="javascript:category = 'ctr';sort = 1;loadKeywordQualityData();"/>
                                            </p></b>
                                        </li>
                                        <li>
                                            <span>消费</span><b>
                                            <p>
                                                <input class="one" type="button"
                                                       onclick="javascript:category = 'cost';sort = -1;loadKeywordQualityData();"/>
                                            </p>

                                            <p>
                                                <input class="two" type="button"
                                                       onclick="javascript:category = 'cost';sort = 1;loadKeywordQualityData();"/>
                                            </p></b>
                                        </li>
                                        <li>
                                            <span>平均点击价格</span><b>
                                            <p>
                                                <input class="one" type="button"
                                                       onclick="javascript:category = 'cpc';sort = -1;loadKeywordQualityData();"/>
                                            </p>

                                            <p>
                                                <input class="two" type="button"
                                                       onclick="javascript:category = 'cpc';sort = 1;loadKeywordQualityData();"/>
                                            </p></b>
                                        </li>
                                        <li>
                                            <span>转化</span><b>
                                            <p>
                                                <input class="one" type="button"
                                                       onclick="javascript:category = 'conversion';sort = -1;loadKeywordQualityData();"/>
                                            </p>

                                            <p>
                                                <input class="two" type="button"
                                                       onclick="javascript:category = 'conversion';sort = 1;loadKeywordQualityData();"/>
                                            </p></b>
                                        </li>
                                    </ul>
                                </td>
                            </tr>
                            <tr class="list2_box1" onclick="TestBlack('divo');">
                                <td>
                                    <ul>
                                        <li class="home_quality">
                                            <ul class="paihang">
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                            </ul>
                                            <span>0</span></li>
                                        <ol id="quality0">
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                        </ol>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td id="divo">
                                    <div class="list2_top2">
                                        <ul>
                                            <li class="home_quality"></li>
                                            <li><span>关键词</span></li>
                                            <li><span>展现</span></li>
                                            <li><span>点击</span></li>
                                            <li><span>点击率</span></li>
                                            <li><span>消费</span></li>
                                            <li><span>平均点击价格</span></li>
                                            <li><span>转化</span></li>
                                        </ul>

                                    </div>
                                    <div id="keywordQuality0"></div>
                                </td>
                            </tr>
                            <tr class="list2_box2" onclick="TestBlack('divo1');">
                                <td>
                                    <ul>
                                        <li class="home_quality">
                                            <ul class="paihang">
                                                <li><img src="${pageContext.request.contextPath}/public/img/star2.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                            </ul>
                                            <span>1</span></li>
                                        <ol id="quality1">
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                        </ol>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td id="divo1" style=" display:none;">
                                    <div class="list2_top2">
                                        <ul>
                                            <li class="home_quality"></li>
                                            <li><span>关键词</span><a href="#" class="question"></a></li>
                                            <li><span>展现</span></li>
                                            <li><span>点击</span></li>
                                            <li><span>点击率</span></li>
                                            <li><span>消费</span></li>
                                            <li><span>平均点击价格</span></li>
                                            <li><span>转化</span></li>
                                        </ul>
                                    </div>
                                    <div id="keywordQuality1"></div>
                                </td>
                            </tr>
                            <tr class="list2_box1" onclick="TestBlack('divo2');">
                                <td>
                                    <ul>
                                        <li class="home_quality">
                                            <ul class="paihang">
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                            </ul>
                                            <span>2</span></li>
                                        <ol id="quality2">
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                        </ol>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td id="divo2" style=" display:none;">
                                    <div class="list2_top2">
                                        <ul>
                                            <li class="home_quality"></li>
                                            <li><span>关键词</span><a href="#" class="question"></a></li>
                                            <li><span>展现</span></li>
                                            <li><span>点击</span></li>
                                            <li><span>点击率</span></li>
                                            <li><span>消费</span></li>
                                            <li><span>平均点击价格</span></li>
                                            <li><span>转化</span></li>
                                        </ul>
                                    </div>
                                    <div id="keywordQuality2"></div>
                                </td>
                            </tr>
                            <tr class="list2_box2" onclick="TestBlack('divo3');">
                                <td>
                                    <ul>
                                        <li class="home_quality">
                                            <ul class="paihang">
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star2.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                            </ul>
                                            <span>3</span></li>
                                        <ol id="quality3">
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                        </ol>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td id="divo3" style=" display:none;">
                                    <div class="list2_top2">
                                        <ul>
                                            <li class="home_quality"></li>
                                            <li><span>关键词</span><a href="#" class="question"></a></li>
                                            <li><span>展现</span></li>
                                            <li><span>点击</span></li>
                                            <li><span>点击率</span></li>
                                            <li><span>消费</span></li>
                                            <li><span>平均点击价格</span></li>
                                            <li><span>转化</span></li>
                                        </ul>
                                    </div>
                                    <div id="keywordQuality3"></div>
                                </td>
                            </tr>
                            <tr class="list2_box1" onclick="TestBlack('divo4');">
                                <td>
                                    <ul>
                                        <li class="home_quality">
                                            <ul class="paihang">
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                            </ul>
                                            <span>4</span></li>
                                        <ol id="quality4">
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                        </ol>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td id="divo4" style=" display:none;">
                                    <div class="list2_top2">
                                        <ul>
                                            <li class="home_quality"></li>
                                            <li><span>关键词</span><a href="#" class="question"></a></li>
                                            <li><span>展现</span></li>
                                            <li><span>点击</span></li>
                                            <li><span>点击率</span></li>
                                            <li><span>消费</span></li>
                                            <li><span>平均点击价格</span></li>
                                            <li><span>转化</span></li>
                                        </ul>
                                    </div>
                                    <div id="keywordQuality4"></div>
                                </td>
                            </tr>
                            <tr class="list2_box2" onclick="TestBlack('divo5');">
                                <td>
                                    <ul>
                                        <li class="home_quality">
                                            <ul class="paihang">
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star2.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                            </ul>
                                            <span>5</span></li>
                                        <ol id="quality5">
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                        </ol>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td id="divo5" style=" display:none;">
                                    <div class="list2_top2">
                                        <ul>
                                            <li class="home_quality"></li>
                                            <li><span>关键词</span><a href="#" class="question"></a></li>
                                            <li><span>展现</span></li>
                                            <li><span>点击</span></li>
                                            <li><span>点击率</span></li>
                                            <li><span>消费</span></li>
                                            <li><span>平均点击价格</span></li>
                                            <li><span>转化</span></li>
                                        </ul>
                                    </div>
                                    <div id="keywordQuality5"></div>
                                </td>
                            </tr>
                            <tr class="list2_box1" onclick="TestBlack('divo6');">
                                <td>
                                    <ul>
                                        <li class="home_quality">
                                            <ul class="paihang">
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                            </ul>
                                            <span>6</span></li>
                                        <ol id="quality6">
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                        </ol>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td id="divo6" style=" display:none;">
                                    <div class="list2_top2">
                                        <ul>
                                            <li class="home_quality"></li>
                                            <li><span>关键词</span><a href="#" class="question"></a></li>
                                            <li><span>展现</span></li>
                                            <li><span>点击</span></li>
                                            <li><span>点击率</span></li>
                                            <li><span>消费</span></li>
                                            <li><span>平均点击价格</span></li>
                                            <li><span>转化</span></li>
                                        </ul>
                                    </div>
                                    <div id="keywordQuality6"></div>
                                </td>
                            </tr>
                            <tr class="list2_box2" onclick="TestBlack('divo7');">
                                <td>
                                    <ul>
                                        <li class="home_quality">
                                            <ul class="paihang">
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star2.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                            </ul>
                                            <span>7</span></li>
                                        <ol id="quality7">
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                        </ol>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td id="divo7" style=" display:none;">
                                    <div class="list2_top2">
                                        <ul>
                                            <li class="home_quality"></li>
                                            <li><span>关键词</span><a href="#" class="question"></a></li>
                                            <li><span>展现</span></li>
                                            <li><span>点击</span></li>
                                            <li><span>点击率</span></li>
                                            <li><span>消费</span></li>
                                            <li><span>平均点击价格</span></li>
                                            <li><span>转化</span></li>
                                        </ul>
                                    </div>
                                    <div id="keywordQuality7"></div>
                                </td>
                            </tr>
                            <tr class="list2_box1" onclick="TestBlack('divo8');">
                                <td>
                                    <ul>
                                        <li class="home_quality">
                                            <ul class="paihang">
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star3.png">
                                                </li>
                                            </ul>
                                            <span>8</span></li>
                                        <ol id="quality8">
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                        </ol>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td id="divo8" style=" display:none;">
                                    <div class="list2_top2">
                                        <ul>
                                            <li class="home_quality"></li>
                                            <li><span>关键词</span><a href="#" class="question"></a></li>
                                            <li><span>展现</span></li>
                                            <li><span>点击</span></li>
                                            <li><span>点击率</span></li>
                                            <li><span>消费</span></li>
                                            <li><span>平均点击价格</span></li>
                                            <li><span>转化</span></li>
                                        </ul>
                                    </div>
                                    <div id="keywordQuality8"></div>
                                </td>
                            </tr>
                            <tr class="list2_box2" onclick="TestBlack('divo9');">
                                <td>
                                    <ul>
                                        <li class="home_quality">
                                            <ul class="paihang">
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star2.png">
                                                </li>
                                            </ul>
                                            <span>9</span></li>
                                        <ol id="quality9">
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                        </ol>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td id="divo9" style=" display:none;">
                                    <div class="list2_top2">
                                        <ul>
                                            <li class="home_quality"></li>
                                            <li><span>关键词</span><a href="#" class="question"></a></li>
                                            <li><span>展现</span></li>
                                            <li><span>点击</span></li>
                                            <li><span>点击率</span></li>
                                            <li><span>消费</span></li>
                                            <li><span>平均点击价格</span></li>
                                            <li><span>转化</span></li>
                                        </ul>
                                    </div>
                                    <div id="keywordQuality9"></div>
                                </td>
                            </tr>
                            <tr class="list2_box2" onclick="TestBlack('divo10');">
                                <td>
                                    <ul>
                                        <li class="home_quality">
                                            <ul class="paihang">
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                                <li><img src="${pageContext.request.contextPath}/public/img/star.png">
                                                </li>
                                            </ul>
                                            <span>10</span></li>
                                        <ol id="quality10">
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                            <li>&nbsp;</li>
                                        </ol>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td id="divo10" style=" display:none;">
                                    <div class="list2_top2">
                                        <ul>
                                            <li class="home_quality"></li>
                                            <li><span>关键词</span><a href="#" class="question"></a></li>
                                            <li><span>展现</span></li>
                                            <li><span>点击</span></li>
                                            <li><span>点击率</span></li>
                                            <li><span>消费</span></li>
                                            <li><span>平均点击价格</span></li>
                                            <li><span>转化</span></li>
                                        </ul>
                                    </div>
                                    <div id="keywordQuality10"></div>
                                </td>
                            </tr>
                        </table>
                        <div class="download over">
                            <%--<div class="page2 fl">
                                <a href="#" class="nextpage1"><span></span></a><a href="#">1</a><a href="#">2</a><a href="#">3</a><a
                                    href="#">4</a><a href="#">5</a><a href="#">6</a><a href="#" class="nextpage2"><span></span></a><span
                                    style="margin-right:10px;">跳转到 <input type="text" class="price"></span>&nbsp;&nbsp;<a href="#" class='page_go'> GO</a>

                            </div>--%>
                            <%--           <span class="fr">每页显示
                                           <select id="keywordQuality1Page"
                                                   onchange="javascript:limit = $('#keywordQuality1Page option:selected').val();loadKeywordQualityData(null, statDate);">
                                               <option selected="selected" value="10">10个</option>
                                               <option value="15">15个</option>
                                               <option value="20">20个</option>
                                           </select>
                                           </span>--%>
                        </div>
                    </div>
                </div>
                <div class="containers hides over">
                    <div class="list01_under3 over">
                        <div class="list01_top over">
                            <Span>重点关键词监控</Span>
                            <button type="button" class="question  btn btn-default" data-toggle="tooltip"
                                    data-placement="bottom" title="凤巢账户重点关键词同步监控。"></button>
                            <ul>
                                <li class="current">
                                    <a href="javascript:void(0)" onclick="getImportKeywordDefault(this, 1);">
                                        昨天
                                    </a>
                                </li>
                                <li>
                                    <a href="javascript:void(0)" onclick="getImportKeywordDefault(this, 7);">
                                        近7天
                                    </a>
                                </li>
                                <li>
                                    <a href="javascript:void(0)" onclick="getImportKeywordDefault(this, 30);">
                                        近30天
                                    </a>
                                </li>
                           <%--     <li class="date">
                                    <a href="javascript:void(0)">
                                        自定义
                                        <input name="reservation" id="" type="image"
                                               onclick="javascript:genre = 'importKeywordDefault';$(this).parent().parent().addClass('current');changedLiState($(this).parent()); _posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                                               src="${pageContext.request.contextPath}/public/img/date.png">
                                    </a>
                                </li>--%>
                                <li class="date date_choice" onclick="javascript:genre = 'importKeywordDefault';$(this).addClass('current');$(this).prevAll().removeClass('current')" ><a href="javascript:"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span><b>自定义</b></a></li>
                            </ul>
                            <div class="download fr over">
                                <a href="javascript:void(0)" onclick="importDownload(this);">下载全部 </a>
                            </div>
                        </div>
                        <div class="datebox hides" style="margin-bottom:0px;"></div>

                        <div class="list2 gjclist wd">
                            <table border="0" cellspacing="0" cellspacing="0">
                                <tr class="list2_top">
                                    <td>
                                        <ul>
                                            <li>
                                                <span>关键词</span><b>
                                                <p>
                                                    <input class="one" type="button">
                                                </p>

                                                <p>
                                                    <input class="two" type="button">
                                                </p></b>
                                            </li>
                                            <li>
                                                <span>展现</span><b>
                                                <p>
                                                    <input class="one" type="button"
                                                           onclick="javascript:sort = -1;getImportKeywordDefault(null, statDate);">
                                                </p>

                                                <p>
                                                    <input class="two" type="button"
                                                           onclick="javascript:sort = 1;getImportKeywordDefault(null, statDate);">
                                                </p></b>
                                            </li>
                                            <li>
                                                <span>点击</span><b>
                                                <p>
                                                    <input class="one" type="button"
                                                           onclick="javascript:sort = -2;getImportKeywordDefault(null, statDate);">
                                                </p>

                                                <p>
                                                    <input class="two" type="button"
                                                           onclick="javascript:sort = 2;getImportKeywordDefault(null, statDate);">
                                                </p></b>
                                            </li>
                                            <li>
                                                <span>消费</span><b>
                                                <p>
                                                    <input class="one" type="button"
                                                           onclick="javascript:sort = -3;getImportKeywordDefault(null, statDate);">
                                                </p>

                                                <p>
                                                    <input class="two" type="button"
                                                           onclick="javascript:sort = 3;getImportKeywordDefault(null, statDate);">
                                                </p></b>
                                            </li>
                                            <li>
                                                <span>平均点击价格</span><b>
                                                <p>
                                                    <input class="one" type="button"
                                                           onclick="javascript:sort = -4;getImportKeywordDefault(null, statDate);">
                                                </p>

                                                <p>
                                                    <input class="two" type="button"
                                                           onclick="javascript:sort = 4;getImportKeywordDefault(null, statDate);">
                                                </p></b>
                                            </li>
                                            <li>
                                                <span>点击率</span><b>
                                                <p>
                                                    <input class="one" type="button"
                                                           onclick="javascript:sort = -5;getImportKeywordDefault(null, statDate);">
                                                </p>

                                                <p>
                                                    <input class="two" type="button"
                                                           onclick="javascript:sort = 5;getImportKeywordDefault(null, statDate);">
                                                </p></b>
                                            </li>
                                            <li>
                                                <span>转化</span><b>
                                                <p>
                                                    <input class="one" type="button"
                                                           onclick="javascript:sort = -6;getImportKeywordDefault(null, statDate);">
                                                </p>

                                                <p>
                                                    <input class="two" type="button"
                                                           onclick="javascript:sort = 6;getImportKeywordDefault(null, statDate);">
                                                </p></b>
                                            </li>
                                            <li>
                                                <span>平均排名</span><b>
                                                <p>
                                                    <input class="one" type="button"
                                                           onclick="javascript:sort = -7;getImportKeywordDefault(null, statDate);">
                                                </p>

                                                <p>
                                                    <input class="two" type="button"
                                                           onclick="javascript:sort = 7;getImportKeywordDefault(null, statDate);">
                                                </p></b>
                                            </li>
                                            <li class="home_quality">
                                                <span>质量度</span><b>
                                                <p>
                                                    <input class="one" type="button">
                                                </p>

                                                <p>
                                                    <input class="two" type="button">
                                                </p></b>
                                            </li>
                                            <li>
                                                <span>匹配</span><b>
                                                <p>
                                                    <input class="one" type="button">
                                                </p>

                                                <p>
                                                    <input class="two" type="button">
                                                </p></b>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr class="list2_box1" id="importTr">
                                </tr>
                            </table>
                            <div class="over">
                            </div>
                            <div class="download over">
                <span class="fr">每页显示
                            <select id="importKeywordselect"
                                    onchange="javascript:limit = $('#importKeywordselect option:selected').val();getImportKeywordDefault();">
                                <option selected="selected" value="10">10个</option>
                                <option value="20">20个</option>
                                <option value="30">30个</option>
                            </select> </span>
                            </div>
                            <%--getImportKeywordDefault--%>
                            <div class="pagination" id="importPager"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="pageBlock/footer.jsp"/>
    </div>
</div>

<!-- javascript -->
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/echarts/2.1.10/echarts-all.js"></script>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.jQuery.js"></script>--%>
<%--<script type="text/javascript"--%>
<%--src="${pageContext.request.contextPath}/public/js/jquery.ui.datepicker-zh-CN.js"></script>--%>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/bootstrap-daterangepicker-moment.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/json2/20140204/json2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/map.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/keyword/keywordQuality.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/untils/untils.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/pagination/jquery.pagination.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/home/home.js"></script>
</body>
</html>