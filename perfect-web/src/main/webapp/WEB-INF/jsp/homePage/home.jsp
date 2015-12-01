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
                                <li class="date" id="date"><a href="javascript:"
                                                              onclick="lisClick(this,null);">自定义<input
                                        name="reservation"
                                        type="image"
                                        onclick="javascript:genre = 'accountOverview';$(this).parent().parent().addClass('current');changedLiState($(this).parent()); _posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                                        src="${pageContext.request.contextPath}/public/img/date.png" id="test">
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
                                <li class="date">
                                    <a href="javascript:void(0)">
                                        自定义
                                        <input name="reservation" class=" fa fa-calendar " type="image"
                                               onclick="javascript:genre = 'importPerformanceCurveDefault';$(this).parent().parent().addClass('current');changedLiState($(this).parent()); _posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                                               src="${pageContext.request.contextPath}/public/img/date.png">
                                    </a>
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
                                <li class="date">
                                    <a href="javascript:void(0)">
                                        自定义
                                        <input name="reservation" id="expression" class=" fa fa-calendar " type="image"
                                               onclick="javascript:$('#pageUser').empty();judgeDet = 0;genre = 'importPerformanceDefault';$(this).parent().parent().addClass('current');changedLiState($(this).parent()); _posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                                               src="${pageContext.request.contextPath}/public/img/date.png">
                                    </a>
                                </li>
                            </ul>
                            <div class="download fr over">
                                <a href="/account/downAccountCSV">下载全部 </a>
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
                                    data-placement="bottom" title="凤巢重点关键词同步监控。"></button>
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
                                <li class="date">
                                    <a href="javascript:void(0)">
                                        自定义
                                        <input name="reservation" id="" type="image"
                                               onclick="javascript:genre = 'importKeywordDefault';$(this).parent().parent().addClass('current');changedLiState($(this).parent()); _posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                                               src="${pageContext.request.contextPath}/public/img/date.png">
                                    </a>
                                </li>
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

    //默认按照展现进行排名
    var category = "impression";

    //默认降序排列
    var sort = -1;

    //默认加载前10条数据
    var limit = 10;

    //日期控件-开始日期
    var daterangepicker_start_date = null;

    //日期控件-结束日期
    var daterangepicker_end_date = null;

    //区分当前展示的是昨天(1), 近7天(7), 近30天(30), 还是自定义日期(0)的数据
    var statDate = 1;

    //查询类别区分
    var genre = "";

    //日期控件坐标定位
    var _posX = 0, _posY = 0;
    var clickdddd = null;
    //根据最近几天获取数据

    function lisClick(obj, days) {
        if (days != null) {
            getDateParam(days);
        }
        setTimeout(function(){

        if(days==1){
            $('input[name="reservation').data('daterangepicker').setStartDate(GetDateStr(-days));
            $('input[name="reservation').data('daterangepicker').setEndDate(GetDateStr(-days));
        }else{
            $('input[name="reservation').data('daterangepicker').setStartDate(GetDateStr(-days+1));
            $('input[name="reservation').data('daterangepicker').setEndDate(GetDateStr(0));
        }
        })
        htmlLoding();
        getData();
        changedLiState(obj);
    }

    $(document).ready(function () {
        /////count
        $.ajax({
            url: "/account/countAssistant",
            type: "GET",
            dataType: "json",
            success: function (data) {
                $("#countAssisted").empty();
                var classArray = ["blue", "green", "red", "yellow"];
                var html = "";
                data.rows.forEach(function (item, i) {
                    html = html + "<li><div class='" + classArray[i] + "1 fr wd1'></div><div class='" + classArray[i] + "2 fl wd2'><p>" + item.name + "</p><span> " + item.countNumber + " </span><span style='display:none'>修改: " + item.modifiyNumber + " </span></div></li>";
                });
                $("#countAssisted").append(html)
            }
        });

        var $tab_li = $('.tab_menu li');
        $('.tab_menu li').click(function () {
            $(this).addClass('selected').siblings().removeClass('selected');
            var index = $tab_li.index(this);
            typepage = index + 1;
            if(index ==0){
                $("#TitleName").html('账户概览');
            }
            switch (typepage) {
                case 2:
                    //曲线图表现-----默认加载7天数据
                    loadPerformanceCurve(null, 7);
                    $("#TitleName").html('账户表现');
                    break;
                case 3:
                    //默认加载昨天的数据(质量度)
//                loadKeywordQualityData(null, 1);
                    $("#TitleName").html('质量度分析');
                    break;
                case 4:
                    //重点词加载
                    getImportKeywordDefault(null, 1);
                    $("#TitleName").html('关键词监控');
                    break;
            }
            $('div.tab_box > div').eq(index).show().siblings().hide();
        });
        $("input[name=reservation]").click(function () {
            clickdddd = $(this);
        });
        //加载日历控件
        $('input[name="reservation"]').daterangepicker({
                    "showDropdowns": true,
                    "timePicker24Hour": true,
                    timePicker: false,
                    timePickerIncrement: 30,
                    "linkedCalendars":false,
                    "format": "YYYY-MM-DD",
                    minDate:'2005/1/1',
                    maxDate:moment().startOf('day'),
                    autoUpdateInput:true,
                    "locale": {
                        "format": "YYYY-MM-DD",
                        "separator": " - ",
                        "applyLabel": "确定",
                        "cancelLabel": "关闭",
                        "fromLabel": "From",
                        "toLabel": "To",
                        "customRangeLabel": "Custom",
                        "daysOfWeek": [
                            "日",
                            "一",
                            "二",
                            "三",
                            "四",
                            "五",
                            "六"
                        ],
                        "monthNames": [
                            "一月",
                            "二月",
                            "三月",
                            "四月",
                            "五月",
                            "六月",
                            "七月",
                            "八月",
                            "九月",
                            "十月",
                            "十一月",
                            "十二月"
                        ],
                        "firstDay": 1
                    },
                    "startDate":moment().subtract('days', 1).startOf('day'),
                    "endDate": moment().subtract('days', 1).startOf('day')
                },
                function (start, end, label, e) {
                    var _startDate = start.format('YYYY-MM-DD');
                    var _endDate = end.format('YYYY-MM-DD');
//                 console.log(daterangepicker_end_date);
                    if (_startDate != null && _endDate != null) {
                        if (_startDate > _endDate) {
                            return false;
                        }
                        statDate = 0;
                        daterangepicker_start_date = _startDate;
                        daterangepicker_end_date = _endDate;
                        if (genre == "keywordQualityCustom") {
                            //区分当前展示的是昨天(1), 近7天(7), 近30天(30), 还是自定义日期(0)的数据
                            loadKeywordQualityData();
                        } else if (genre == "importKeywordDefault") {
                            getImportKeywordDefault(null, 0);
                        } else if (genre == "accountOverview") {
                            lisClick();
                        } else if (genre == "importPerformanceDefault") {
                            category = "data";
                            loadPerformance(0);
                        } else if (genre == "importPerformanceCurveDefault") {
                            category = "data";
                            loadPerformanceCurve(0);
                        }
                        showDate();
                    }
                });
//        $("input[name=reservation]").daterangepicker();
        $(".btnDone").on('click', function () {

        });
//        $("#clickLis li").on('click', function () {
//            var day= $(this).find("a").attr('value');
//          $('input[name="reservation').data('daterangepicker').setStartDate(GetDateStr(-day));
//           if(day==1){
//              $('input[name="reservation').data('daterangepicker').setEndDate(GetDateStr(-day));
//           }else{
//           $('input[name="reservation').data('daterangepicker').setEndDate(GetDateStr(0));
//           }
//
//        });
        document.getElementById("background").style.display = "none";
        document.getElementById("progressBar1").style.display = "none";


        //账户表现-----默认加载7天数据
        loadPerformance(null, 7);

        //关键词质量度分析异步加载
        loadKeywordQualityData();

    });

    function TestBlack(TagName) {
        var obj = document.getElementById(TagName);
        if (obj.style.display == "") {
            obj.style.display = "none";
        } else {
            obj.style.display = "";
        }
    }
    function selectChange() {
        limit = $("#importKeywordSel :selected").val();
        getImportKeywordDefault(null, statDate);
    }

    /**
     * 获取昨天, 近七天, 近30天日期
     * 参数为1, 昨天
     * 参数为7, 7天
     * 参数为30, 近30天
     * @param day
     */
    var getDateParam = function (day) {
        var currDate = new Date();
        if (day == 1) {
            currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24);
            daterangepicker_start_date = currDate.Format("yyyy-MM-dd");
            daterangepicker_end_date = daterangepicker_start_date;
        } else if (day == 7) {
            currDate = new Date();
            currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24);
            daterangepicker_end_date = currDate.Format("yyyy-MM-dd");
            currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24 * 6);
            daterangepicker_start_date = currDate.Format("yyyy-MM-dd");
        } else if (day == 30) {
            currDate = new Date();
            currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24);
            daterangepicker_end_date = currDate.Format("yyyy-MM-dd");
            currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24 * 29);
            daterangepicker_start_date = currDate.Format("yyyy-MM-dd");
        }
    };

    var judgeDet = 0;
    var perCount = 0;
    var startPer = 0;
    var pageDetNumber = 0;
    var endPer = 10;
    var limitPer = 20;
    var skipPagePer;
    /**
     * 分日表现数据加载
     * */
    var loadPerformance = function (obj, date) {
        if (obj != null) {
            changedLiState(obj);
        }
        getDateParam(date);
        $.ajax({
            url: "/account/getPerformanceUser",
            type: "GET",
            dataType: "json",
            data: {
                startDate: daterangepicker_start_date,
                endDate: daterangepicker_end_date,
                sort: sort,
                limit: endPer,
                startPer: startPer
            },
            success: function (data) {
                var calssStr = "";
                if (data.rows.length > 0) {
                    $("#performance").empty();
                    $.each(data.rows, function (i, item) {
                        pageDetNumber = item.count;
                        if (i % 2 == 0) {
                            calssStr = "list2_box1";
                        } else {
                            calssStr = "list2_box2";
                        }
                        var _div = "<tr class=" + calssStr + "><td><ul><li> &nbsp;" + item.date + "</li><li> &nbsp;" + item.pcImpression + "</li><li> &nbsp;" + item.pcClick + "</li><li> &nbsp;" + Math.round(item.pcCost * 100) / 100 + "</li><li> &nbsp;" + Math.round(item.pcCtr * 100) / 100 + "%</li>"
                                + "<li> &nbsp;" + Math.round(item.pcCpc * 100) / 100 + "</li><li> &nbsp;" + item.pcConversion + "</li></ul></td></tr>";
                        $("#performance").append(_div);
                    });
                    records = pageDetNumber;
                    typepage = 1;
                    $("#pagination1").pagination(pageDetNumber, getOptionsFromForm(pageIndex));
                }
            }
        });
    };
    /******************pagination*********************/
    var items_per_page = 10;    //默认每页显示20条数据
    var limitPer = 10;
    var pageIndex = 0;
    var records = 0;
    var skip = 0;
    var typepage = 1;
    var nowPage = 0;

    var pageSelectCallback = function (page_index, jq) {
        if (typepage == 1) {
            $("#pagination1").append("<span style='margin-right:10px;'>跳转到 <input id='anyPageNumber1' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipPagePer();' class='page_go'> GO</a>");
        } else if (typepage == 2) {
            $("#pagination2").append("<span style='margin-right:10px;'>跳转到 <input id='anyPageNumber2' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipPagePer();' class='page_go'> GO</a>");
        } else if (typepage == 4) {
            $("#importPager").append("<span style='margin-right:10px;'>跳转到 <input id='anyPageNumber4' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipPagePer();' class='page_go'> GO</a>");

        }
        if (pageIndex == page_index) {
            return false;
        }
        pageIndex = page_index;
        if (typepage == 1) {
            startPer = (page_index + 1) * items_per_page - items_per_page;
            endPer = (page_index + 1) * items_per_page;
            loadPerformance();
        } else if (typepage == 2) {
            judety = 1;
            startPer = (page_index + 1) * items_per_page - items_per_page;
            endPer = (page_index + 1) * items_per_page;
            loadPerformance();
        } else if (typepage == 4) {

        }
        return false;
    };

    var getOptionsFromForm = function (current_page) {
        if (typepage == 1) {
            items_per_page = limitPer;
        } else if (typepage == 2) {
            items_per_page = limitPer;
        }

        var opt = {callback: pageSelectCallback};
        opt["items_per_page"] = items_per_page;
        opt["current_page"] = current_page;
        opt["prev_text"] = "上一页";
        opt["next_text"] = "下一页";

        //avoid html injections
        var htmlspecialchars = {"&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;"};
        $.each(htmlspecialchars, function (k, v) {
            opt.prev_text = opt.prev_text.replace(k, v);
            opt.next_text = opt.next_text.replace(k, v);
        });
        return opt;
    };
    var optInit = getOptionsFromForm(0);

    skipPagePer = function () {
        var _number = 0;
        if (typepage == 1) {
            _number = $("#anyPageNumber1").val() - 1;
            if (_number <= -1 || _number == pageIndex) {
                return;
            }
            $("#pagination1").pagination(records, getOptionsFromForm(_number));
        } else if (typepage == 2) {
            _number = $("#anyPageNumber2").val() - 1;
            if (_number <= -1 || _number == pageIndex) {
                return;
            }
            $("#pagination2").pagination(records, getOptionsFromForm(_number));
        } else if (typepage == 4) {
            _number = $("#anyPageNumber4").val();
            $("#importPager").pagination(records, getOptionsFromForm(/^\d+$/.test(_number) == false ? 0 : parseInt(_number) - 1));
        }
    };
    /**********************************************************************************/
</script>
<script>

    /**初始化曲线图共用变量**/
    /*初始化数据变量*/
    var goLi = 0;
    var dataOne = "";
    var dataTow = "";
    //日期
    var t_date = [];
    var dateInterval = 0;
    var colorOne = "#4572A7";
    var colorTow = "#40BC2A";
    var nameOne = "展现";
    var nameTow = "点击";
    var unitOne = "次";
    var unitTow = "次"
    /**初始化结束**/
    /**
     * 曲线图数据配
     * **/
    var loadPerformanceCurve = function (obj, date) {
        $('input[name="reservation"]:eq(1)').data('daterangepicker').setStartDate(GetDateStr(-date+1));
            $('input[name="reservation"]:eq(1)').data('daterangepicker').setEndDate(GetDateStr(0));

        if (obj != null) {
            changedLiState(obj);
        }
        colorOne = "#4572A7";
        colorTow = "#40BC2A";
        nameOne = "展现";
        nameTow = "点击";
        unitOne = "次";
        unitTow = "次"
        $("#containerLegend").empty();
        /*初始化曲线图所用需求*/
        $("#containerLegend").append("<div class='tu_top over'><ul><li>展示曲线</li>"
        + "<li><label class='checkbox-inlines'><input name='check' cname='impr' xname='' type='checkbox' checked='checked'><span class='blue' ></span><b>展现</b></label></li>"
        + "<li><label class='checkbox-inlines'><input name='check' cname='clicks' xname='' type='checkbox' checked='checked'><span class='green'></span><b>点击</b></label></li>"
        + "<li><label class='checkbox-inlines'><input name='check' cname='cost' xname='' type='checkbox'><span class='red'></span><b>消费</b></label></li>"
        + "<li><label class='checkbox-inlines'><input name='check' cname='ctr' xname='' type='checkbox'><span class='blue2'></span><b>点击率</b></label></li>"
        + "<li><label class='checkbox-inlines'><input name='check' cname='cpc' xname='' type='checkbox'><span class='green2'></span><b>平均点击价格</b></label></li>"
        + "<li><label class='checkbox-inlines'><input name='check' cname='conv' xname='' type='checkbox'><span class='yellow'></span><b>转化</b></label></li><li><b style='color: red'>最多只能同时选择两项</b></li></ul></div>");

        getDateParam(date);
        //展现
        var t_impr = [];
        //点击数
        var t_clicks = [];
        //消费
        var t_cost = [];
        //点击率
        var t_ctr = [];
        //平均点击价格
        var t_cpc = [];
        //转化
        var t_conversion = [];

        $.ajax({
            url: "/account/getPerformanceCurve",
            type: "GET",
            dataType: "json",
            async: false,
            data: {
                startDate: daterangepicker_start_date,
                endDate: daterangepicker_end_date
            },
            success: function (data) {
                t_date.length = 0;
                t_impr.length = 0;
                t_clicks.length = 0;
                t_cost.length = 0;
                t_ctr.length = 0;
                t_cpc.length = 0;
                t_conversion.length = 0;
                if (data.rows.length > 0) {
                    $.each(data.rows, function (i, item) {
                        t_date.push(item.date);
                        t_impr.push(item.pcImpression);
                        t_clicks.push(item.pcClick);
                        t_cost.push(Math.round(item.pcCost * 100) / 100);
                        t_ctr.push(Math.round(item.pcCtr * 100) / 100);
                        t_cpc.push(Math.round(item.pcCpc * 100) / 100);
                        t_conversion.push(item.pcConversion);
                    })
                }
                if (data.rows.length > 10) {
                    dateInterval = Math.round(data.rows.length / 10);
                }
            }
        });

        dataOne = {
            name: '展现',
            type: 'line',
            smooth: true,
            data: t_impr,
            itemStyle: {
                normal: {
                    lineStyle: {
                        color: '#4572A7'
                    }
                }
            }
        };
        dataTow = {
            name: '点击',
            type: 'line',
            smooth: true,
            data: t_clicks,
            yAxisIndex: 1,
            itemStyle: {
                normal: {
                    lineStyle: {
                        color: '#40BC2A'
                    }
                }
            }
        };
        $("input[cname=impr]").attr("xname", "dataOne");
        $("input[cname=clicks]").attr("xname", "dataTow");

        $("input[name=check]").click(function () {
            var name = $(this).attr("cname");
            if ($("input[type='checkbox']:checked").length <= 2) {
                if (name == "impr") {
                    if ($(this).is(':checked')) {
                        if (dataOne == "") {
                            $(this).attr("xname", "dataOne");
                            colorOne = "#078CC7";
                            nameOne = "展现";
                            unitOne = "次";
                            dataOne = {
                                name: '展现',
                                type: 'line',
                                smooth: true,
                                data: t_impr,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#078CC7'
                                        }
                                    }
                                }
                            };
                            curve();
                        } else if (dataTow == "") {
                            $(this).attr("xname", "dataTow");
                            colorTow = "#078CC7";
                            nameTow = "展现";
                            unitTow = "次";
                            dataTow = {
                                name: '展现',
                                type: 'line',
                                smooth: true,
                                data: t_impr,
                                yAxisIndex: 1,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#078CC7'
                                        }
                                    }
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
                            nameOne = "点击";
                            unitOne = "次";
                            dataOne = {
                                name: '点击',
                                type: 'line',
                                smooth: true,
                                data: t_clicks,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#40BC2A'
                                        }
                                    }
                                }
                            };
                            curve();
                        } else if (dataTow == "") {
                            $(this).attr("xname", "dataTow");
                            colorTow = "#40BC2A";
                            nameTow = "点击";
                            unitTow = "次";
                            dataTow = {
                                name: '点击',
                                type: 'line',
                                smooth: true,
                                data: t_clicks,
                                yAxisIndex: 1,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#40BC2A'
                                        }
                                    }
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
                            nameOne = "消费";
                            unitOne = " ￥";
                            dataOne = {
                                name: '消费',
                                type: 'line',
                                smooth: true,
                                data: t_cost,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#F1521B'
                                        }
                                    }
                                }
                            };
                            curve();
                        } else if (dataTow == "") {
                            $(this).attr("xname", "dataTow");
                            colorTow = "#F1521B";
                            nameTow = "消费";
                            unitTow = " ￥";
                            dataTow = {
                                name: '消费',
                                type: 'line',
                                smooth: true,
                                data: t_cost,
                                yAxisIndex: 1,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#F1521B'
                                        }
                                    }
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
                            nameOne = "点击率";
                            unitOne = " %";
                            dataOne = {
                                name: '点击率',
                                type: 'line',
                                smooth: true,
                                data: t_ctr,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#26CAE5'
                                        }
                                    }
                                }
                            };
                            curve();
                        } else if (dataTow == "") {
                            $(this).attr("xname", "dataTow");
                            colorTow = "#26CAE5";
                            nameTow = "点击率";
                            unitTow = " %";
                            dataTow = {
                                name: '点击率',
                                type: 'line',
                                smooth: true,
                                data: t_ctr,
                                yAxisIndex: 1,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#26CAE5'
                                        }
                                    }
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
                        if (dataOne == "") {
                            $(this).attr("xname", "dataOne");
                            colorOne = "#60E47E";
                            nameOne = "平均点击价格";
                            unitOne = " ￥";
                            dataOne = {
                                name: '平均点击价格',
                                type: 'line',
                                smooth: true,
                                data: t_cpc,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#60E47E'
                                        }
                                    }
                                }
                            };
                            curve();
                        } else if (dataTow == "") {
                            $(this).attr("xname", "dataTow");
                            colorTow = "#60E47E";
                            nameTow = "平均点击价格";
                            unitTow = " ￥";
                            dataTow = {
                                name: '平均点击价格',
                                type: 'line',
                                smooth: true,
                                data: t_cpc,
                                yAxisIndex: 1,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#60E47E'
                                        }
                                    }
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
                        if (dataOne == "") {
                            $(this).attr("xname", "dataOne");
                            colorOne = "#DEDF00";
                            nameOne = "转化";
                            unitOne = "";
                            dataOne = {
                                name: '转化',
                                type: 'line',
                                smooth: true,
                                data: t_conversion,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#DEDF00'
                                        }
                                    }
                                }
                            };
                            curve();
                        } else if (dataTow == "") {
                            $(this).attr("xname", "dataTow");
                            colorTow = "#DEDF00";
                            nameTow = "转化";
                            unitTow = "";
                            dataTow = {
                                name: '转化',
                                type: 'line',
                                smooth: true,
                                yAxisIndex: 1,
                                data: t_conversion,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#DEDF00'
                                        }
                                    }
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
        setTimeout("curve()", 100);
    };
    $("#liClick").click(function () {
        setTimeout("curve()", 100);
    });
    var curve = function () {
        var myChart = echarts.init(document.getElementById('container'));
        window.onresize = myChart.resize;
        var dataNew;
        if (dataOne == "") {
            dataNew = [dataTow];
        } else if (dataTow == "") {
            dataNew = [dataOne];
        } else {
            dataNew = [dataOne, dataTow];
        }
        var option = {
            tooltip: {
                trigger: 'axis'
            },
            toolbox: {
                show: true,
                feature: {
                    magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: t_date
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: nameOne,
                    scale: true,
                    axisLabel: {
                        formatter: '{value} ' + unitOne
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: colorOne
                        }
                    }
                }, {
                    type: 'value',
                    name: nameTow,
                    scale: true,
                    axisLabel: {
                        formatter: '{value} ' + unitTow
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: colorTow
                        }
                    }
                }
            ],
            series: dataNew
        };
        myChart.setOption(option);


        /*$('#container').highcharts({
         chart: {
         zoomType: 'xy'
         },
         title: {
         text: ''
         },
         subtitle: {
         text: ''
         },
         exporting: {
         filename: 'Graph_Chart',
         buttons: {
         contextButton: {
         symbol: 'url(/public/images/reportDown.jpg)',
         menuItems:[{
         text:'导出 JPEG图片',
         onclick:function() {
         this.exportChart({
         type:'image/jpeg'
         });
         }
         },{
         text:'导出 PNG图片',
         onclick:function() {
         this.exportChart({
         type:'image/png'
         });
         }
         },{
         text:'导出 PDF',
         onclick:function() {
         this.exportChart({
         type:'application/pdf'
         });
         }
         },{
         text:'导出 SVG',
         onclick:function() {
         this.exportChart({
         type:'svg'
         });
         }
         }
         ]
         }
         }
         },
         xAxis: {
         categories: t_date,
         tickInterval: (dateInterval)// 每个间隔
         },
         yAxis: [
         { // Primary yAxis
         title: {
         text: nameTow,
         margin: 30,
         style:{'color':colorTow,'font-size':'16px','font-family': '宋体','font-weight': 'bold'}
         },
         labels: {
         format: '{value}',
         style: {
         color: colorTow
         }
         }
         },
         { // Secondary yAxis
         title: {
         text: nameOne,
         margin: 30,
         style:{'color':colorOne,'font-size':'16px','font-family': '宋体','font-weight': 'bold'}
         },
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
         });*/
    };

    var getImportKeywordDefault = function (obj, day) {
        if(day==1){
            $('input[name="reservation').data('daterangepicker').setStartDate(GetDateStr(-day));
            $('input[name="reservation').data('daterangepicker').setEndDate(GetDateStr(-day));
        }else{
            $('input[name="reservation').data('daterangepicker').setStartDate(GetDateStr(-day+1));
            $('input[name="reservation').data('daterangepicker').setEndDate(GetDateStr(0));
        }
        if (obj != null) {
            changedLiState(obj);
        }



        $("#importTr").empty();
        $("#importTr").append("<td style='color:red;'>加载中....</td>");
        statDate = day;
        getDateParam(day);
        var _tr = $("#importTr");
        $.post("/import/getImportKeywordList", {
            startDate: daterangepicker_start_date,
            endDate: daterangepicker_end_date,
            pageSize: limit,
            sort: sort,
            nowPage: nowPage
        }, function (result) {
            var gson = $.parseJSON(result);
            $("#importPager").pagination(gson.totaCount, getOptionsFromForm(gson.pageNo));
            if (gson.list != undefined) {
                _tr.empty();
                for (var i = 0; i < gson.list.length; i++) {
                    var calssStr = i % 2 != 0 ? "list2_box2" : "list2_box1";
                    var item = gson.list[i];
                    var _div = "<tr class=" + calssStr + "><td><ul><li> &nbsp;" + item.keywordName + "</li><li> &nbsp;" + item.pcImpression + "</li><li> &nbsp;" + item.pcClick + "</li><li> &nbsp;￥" + item.pcCost + "</li><li> &nbsp;￥" + item.pcCpc + "</li>"
                            + "<li> &nbsp;" + item.pcCtr + "%</li><li class='home_quality'> &nbsp;" + item.pcConversion + "</li><li> &nbsp;" + item.pcPosition + "</li><li> &nbsp;" + item.quality + "</li><li>&nbsp;" + until.getMatchTypeName(item.matchType) + "</li></ul></td></tr>";
                    $("#importTr").append(_div);
                }
            } else {
                _tr.empty();
                var _div = "<td colspan='9' style='color:royalblue;'>暂无数据...</td>";
                _tr.append(_div);
            }
        });
    };
    //初始化加载下载功能
    function importDownload(rs) {
        window.open("/import/getCSV?startDate=" + daterangepicker_start_date + "&&endDate=" + daterangepicker_end_date);
    }



    function GetDateStr(AddDayCount) {
        var dd = new Date();
        dd.setDate(dd.getDate() + AddDayCount);//获取AddDayCount天后的日期
        var y = dd.getFullYear();
        var m = (dd.getMonth() + 1) < 10 ? "0" + (dd.getMonth() + 1) : (dd.getMonth() + 1);//获取当前月份的日期
        var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();
        return y + "-" + m + "-" + d;
    }
    //改变li的样式状态
    function changedLiState(obj) {
        $(obj).parent().parent().find("li").each(function () {
            $(this).removeClass("current");
        });
        $(obj).parent().addClass("current");
    }

    //数据获取中。。。
    function htmlLoding() {
        $(".impression,.click,.cos,.conversion").html("加载中...");
    }
    htmlLoding();
    //获取数据
    function getData() {
        $.ajax({
            url: "/account/getAccountOverviewData",
            type: "get",
            dataType: "json",
            data: {"startDate": daterangepicker_start_date, "endDate": daterangepicker_end_date},
            success: function (data) {
                $(".impression").html(data.impression);
                $(".click").html(data.click);
                $(".cos").html(data.cos);
                $(".conversion").html(data.conversion);
            }

        });
    }

    //初始化账户概览页面数据
    lisClick($("#clickLis .current>a"), 1);//默认显示昨天的汇总数据
    var showDate = function () {
        $(".datebox").show();
        clickdddd.parent().parent().parent().parent().next().text("当前时间范围：" + daterangepicker_start_date + " 至 " + daterangepicker_end_date);
        $(".list01_top ul li").click(function () {
            $(".datebox").hide();
        });
    };
    $(function () {
        $("[data-toggle='tooltip']").tooltip();
        $(".off").removeClass(".active");
        $(".off").css({color:"white"});
    });
</script>
</body>
</html>