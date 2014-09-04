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
    <title>大数据智能营销</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">
    <link rel="Shortcut Icon" href="${pageContext.request.contextPath}/public/css/images/favicon.ico" />
    <style>
        .page2 .ajc {
            background: #ffb900;
            border: 1px solid #fab30b;
            color: #fff;
        }
    </style>
</head>
<body>
<jsp:include page="pageBlock/nav.jsp"/>
<div class="concent fr over">
<jsp:include page="pageBlock/head.jsp"/>
<div class="mid over">
<div class="on_title over">
    <a href="#">
        账户全景
    </a>
    &nbsp;&nbsp;>&nbsp;&nbsp;<span>账户分析</span>
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
            <Span>账户诊断</Span>
            <a href="#" class="question"></a>
        </div>
        <div class="list01_under over">
            <ul>
                <li class="week">
                    上周账户共推广7天，账户各项主要指标有一定幅度波动，但“竞品词”单元波动最为明显
                    <a href="#" target="_blank">
                        详情…
                    </a><span>更新时间：06-30 08:20</span>
                    <input type="image" src="${pageContext.request.contextPath}/public/img/input1.png">
                </li>
                <li class="months">
                    上周账户共推广7天，账户各项主要指标有一定幅度波动，但“竞品词”单元波动最为明显
                    <a href="#" target="_blank">
                        详情…
                    </a><span>更新时间：06-30 08:20</span>
                    <input type="image" src="${pageContext.request.contextPath}/public/img/input2.png">
                </li>
            </ul>
        </div>
    </div>
    <div class="list01 over" style="border-top:1px solid #d5d5d8; padding-top:20px;">
        <div class="list01_top over">
            <Span>近期概览</Span>
            <ul id="clickLis">
                <li class="current"><a href="javascript:" onclick="lisClick(this,1)">昨天</a></li>
                <li><a href="javascript:" onclick="lisClick(this,7)">近7天</a></li>
                <li><a href="javascript:" onclick="lisClick(this,30)">近30天</a></li>
                <li class="date"><a href="javascript:" onclick="lisClick(this,null);">自定义<input name="reservation" type="image"
                                   onclick="javascript:genre = 'accountOverview';$(this).parent().parent().addClass('current');changedLiState($(this).parent()); _posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                               src="${pageContext.request.contextPath}/public/img/date.png">
                    </a>
                </li>
            </ul>
        </div>
        <div class="list01_under2 over">
            <ul>
                <li>
                    <div class="blue1 fl wd1"></div>
                    <div class="blue2 fl wd2"><Span class="impression"></Span>

                        <p>展现次数</p>
                    </div>
                </li>
                <li>
                    <div class="green1 fl wd1"></div>
                    <div class="green2 fl wd2"><Span class="click"></Span>

                        <p>点击次数</p>
                    </div>
                </li>
                <li>
                    <div class="red1 fl wd1"></div>
                    <div class="red2 fl wd2"><Span class="cos"></Span>

                        <p>消费</p>
                    </div>
                </li>
                <li>
                    <div class="yellow1 fl wd1"></div>
                    <div class="yellow2 fl wd2"><Span class="conversion"></Span>

                        <p>转化次数</p>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="containers  hides over">
    <div class="list01 over" style="border-bottom:1px solid #d5d5d8;">
        <div class="list01_top over">
            <Span>账户趋势图</Span>
            <a href="javascript:void(0)" class="question"></a>
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
        <div class="shuju">
            <div id="containerLegend"></div>
            <div id="container" style="width:100%;height:400px"></div>
        </div>
    </div>
    <div class="list01_under3 over" style=" padding-top:20px;">
        <div class="list01_top over">
            <Span>分日表现</Span>
            <a href="javascript:void(0)" class="question"></a>
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
                        <input name="reservation" class=" fa fa-calendar " type="image"
                               onclick="javascript:$('#pageUser').empty();judgeDet = 0;genre = 'importPerformanceDefault';$(this).parent().parent().addClass('current');changedLiState($(this).parent()); _posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                               src="${pageContext.request.contextPath}/public/img/date.png">
                    </a>
                </li>
            </ul>
        </div>
        <div class="download over">
            <a href="#" class="fr">下载全部 </a>
        </div>
        <div class="list2 wd">
            <table border="0" cellspacing="0" cellspacing="0">
                <thead>
                <tr class="list2_top">
                    <td>
                        <ul>
                            <li>
                                &nbsp;<span>时间</span><b>
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
                                &nbsp;<span>展现量</span><b>
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
                                &nbsp;<span>点击量</span><b>
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
                                &nbsp;<span>消费</span><b>
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
                                &nbsp;<span>点击率</span><b>
                                <p>
                                    <input class="one" type="button"
                                           onclick="javascript:category = 'ctr';sort = -5;loadPerformance(statDate)">
                                </p>

                                <p>
                                    <input class="two" type="button"
                                           onclick="javascript:category = 'ctr';sort = 5;loadPerformance(statDate)">
                                </p></b>
                                <a href="#" class="question">
                                </a>
                            </li>
                            <li>
                                &nbsp;<span>平均点击价格</span><b>
                                <p>
                                    <input class="one" type="button"
                                           onclick="javascript:category = 'cpc';sort = -6;loadPerformance(statDate)">
                                </p>

                                <p>
                                    <input class="two" type="button"
                                           onclick="javascript:category = 'cpc';sort = 6;loadPerformance(statDate)">
                                </p></b>
                                <a href="#" class="question">
                                </a>
                            </li>
                            <li>
                                &nbsp;<span>转化(页面)</span><b>
                                <p>
                                    <input class="one" type="button"
                                           onclick="javascript:category = 'conversion';sort = -7;loadPerformance(statDate)">
                                </p>

                                <p>
                                    <input class="two" type="button"
                                           onclick="javascript:category = 'conversion';sort = 7;loadPerformance(statDate)">
                                </p></b>
                                <a href="#" class="question">
                                </a>
                            </li>
                        </ul>
                    </td>
                </tr>
                </thead>
                <tbody id="performance">

                </tbody>
            </table>
            <div class="download over">
                <div class="page2 fl" id="pageUser">

                </div>
				<!--<span class="fr">每页显示
                            <select id="performanceLimit"
                                    onchange="javascript:limit = $('#performanceLimit option:selected').val();loadPerformance(statDate);">
                                <option selected="selected" value="10">10个</option>
                                <option value="20">20个</option>
                                <option value="30">30个</option>
                            </select> </span>
-->
            </div>
        </div>
    </div>
</div>
<div class="containers hides over">
<div class="list01_top over">
    <Span>质量度分析</Span>
    <a href="#" class="question"></a>
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
    <span class="fl" style=" color:#000; font-weight:bold;">查看完整版数据请点击下载全部→</span>
    <a href="/keywordQuality/downloadCSV" class="fr">
        下载全部
    </a>
</div>
<div class="list2 wd">
<table border="0" cellspacing="0" cellspacing="0">
<tr class="list2_top">

    <td>
        <ul>
            <li>
                &nbsp;<span>质量度</span><b>
                <p>
                    <input class="one" type="button">
                </p>

                <p>
                    <input class="two" type="button">
                </p></b>
            </li>
            <li>
                &nbsp;<span>关键词</span><b>
                <p>
                    <input class="one" type="button">
                </p>

                <p>
                    <input class="two" type="button">
                </p></b>
            </li>
            <li>
                &nbsp;<span>展现</span><b>
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
                &nbsp;<span>点击</span><b>
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
                &nbsp;<span>点击率</span><b>
                <p>
                    <input class="one" type="button"
                           onclick="javascript:category = 'ctr';sort = -1;loadKeywordQualityData();"/>
                </p>

                <p>
                    <input class="two" type="button"
                           onclick="javascript:category = 'ctr';sort = 1;loadKeywordQualityData();"/>
                </p></b>
                <a href="#" class="question">
                </a>
            </li>
            <li>
                &nbsp;<span>消费</span><b>
                <p>
                    <input class="one" type="button"
                           onclick="javascript:category = 'cost';sort = -1;loadKeywordQualityData();"/>
                </p>

                <p>
                    <input class="two" type="button"
                           onclick="javascript:category = 'cost';sort = 1;loadKeywordQualityData();"/>
                </p></b>
                <a href="#" class="question">
                </a>
            </li>
            <li>
                &nbsp;<span>平均点击价格</span><b>
                <p>
                    <input class="one" type="button"
                           onclick="javascript:category = 'cpc';sort = -1;loadKeywordQualityData();"/>
                </p>

                <p>
                    <input class="two" type="button"
                           onclick="javascript:category = 'cpc';sort = 1;loadKeywordQualityData();"/>
                </p></b>
                <a href="#" class="question"></a>
            </li>
            <li>
                &nbsp;<span>转化</span><b>
                <p>
                    <input class="one" type="button"
                           onclick="javascript:category = 'conversion';sort = -1;loadKeywordQualityData();"/>
                </p>

                <p>
                    <input class="two" type="button"
                           onclick="javascript:category = 'conversion';sort = 1;loadKeywordQualityData();"/>
                </p></b>
                <a href="#" class="question"></a>
            </li>
        </ul>
    </td>
</tr>
<tr class="list2_box1" onclick="TestBlack('divo');">
    <td>
        <ul>
            <li>
                <ul class="paihang">
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                </ul>
                <span>0</span></li>
            <ol id="quality0">
                <li>&nbsp;17(0.11%)</li>
                <li>&nbsp;60(0.83%)</li>
                <li>&nbsp;1(4.76%)</li>
                <li>&nbsp;1.67%</li>
                <li>&nbsp;￥2.98(11.26%)</li>
                <li>&nbsp;￥2.98</li>
                <li>&nbsp;22222</li>
            </ol>
        </ul>
    </td>
</tr>
<tr>
    <td id="divo">
        <div class="list2_top2">
            <ul>
                <li></li>
                <li><span>关键词数</span><a href="#" class="question"></a></li>
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
            <li>
                <ul class="paihang">
                    <li><img src="${pageContext.request.contextPath}/public/img/star2.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                </ul>
                <span>1</span></li>
            <ol id="quality1">
                <li>&nbsp;17(0.11%)</li>
                <li>&nbsp;60(0.83%)</li>
                <li>&nbsp;1(4.76%)</li>
                <li>&nbsp;1.67%</li>
                <li>&nbsp;￥2.98(11.26%)</li>
                <li>&nbsp;￥2.98</li>
                <li>&nbsp;22222</li>
            </ol>
        </ul>
    </td>
</tr>
<tr>
    <td id="divo1" style=" display:none;">
        <div class="list2_top2">
            <ul>
                <li></li>
                <li><span>关键词数</span><a href="#" class="question"></a></li>
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
            <li>
                <ul class="paihang">
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                </ul>
                <span>2</span></li>
            <ol id="quality2">
                <li>&nbsp;17(0.11%)</li>
                <li>&nbsp;60(0.83%)</li>
                <li>&nbsp;1(4.76%)</li>
                <li>&nbsp;1.67%</li>
                <li>&nbsp;￥2.98(11.26%)</li>
                <li>&nbsp;￥2.98</li>
                <li>&nbsp;22222</li>
            </ol>
        </ul>
    </td>
</tr>
<tr>
    <td id="divo2" style=" display:none;">
        <div class="list2_top2">
            <ul>
                <li></li>
                <li><span>关键词数</span><a href="#" class="question"></a></li>
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
            <li>
                <ul class="paihang">
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star2.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                </ul>
                <span>3</span></li>
            <ol id="quality3">
                <li>&nbsp;17(0.11%)</li>
                <li>&nbsp;60(0.83%)</li>
                <li>&nbsp;1(4.76%)</li>
                <li>&nbsp;1.67%</li>
                <li>&nbsp;￥2.98(11.26%)</li>
                <li>&nbsp;￥2.98</li>
                <li>&nbsp;22222</li>
            </ol>
        </ul>
    </td>
</tr>
<tr>
    <td id="divo3" style=" display:none;">
        <div class="list2_top2">
            <ul>
                <li></li>
                <li><span>关键词数</span><a href="#" class="question"></a></li>
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
            <li>
                <ul class="paihang">
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                </ul>
                <span>4</span></li>
            <ol id="quality4">
                <li>&nbsp;17(0.11%)</li>
                <li>&nbsp;60(0.83%)</li>
                <li>&nbsp;1(4.76%)</li>
                <li>&nbsp;1.67%</li>
                <li>&nbsp;￥2.98(11.26%)</li>
                <li>&nbsp;￥2.98</li>
                <li>&nbsp;22222</li>
            </ol>
        </ul>
    </td>
</tr>
<tr>
    <td id="divo4" style=" display:none;">
        <div class="list2_top2">
            <ul>
                <li></li>
                <li><span>关键词数</span><a href="#" class="question"></a></li>
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
            <li>
                <ul class="paihang">
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star2.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                </ul>
                <span>5</span></li>
            <ol id="quality5">
                <li>&nbsp;17(0.11%)</li>
                <li>&nbsp;60(0.83%)</li>
                <li>&nbsp;1(4.76%)</li>
                <li>&nbsp;1.67%</li>
                <li>&nbsp;￥2.98(11.26%)</li>
                <li>&nbsp;￥2.98</li>
                <li>&nbsp;22222</li>
            </ol>
        </ul>
    </td>
</tr>
<tr>
    <td id="divo5" style=" display:none;">
        <div class="list2_top2">
            <ul>
                <li></li>
                <li><span>关键词数</span><a href="#" class="question"></a></li>
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
            <li>
                <ul class="paihang">
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                </ul>
                <span>6</span></li>
            <ol id="quality6">
                <li>&nbsp;17(0.11%)</li>
                <li>&nbsp;60(0.83%)</li>
                <li>&nbsp;1(4.76%)</li>
                <li>&nbsp;1.67%</li>
                <li>&nbsp;￥2.98(11.26%)</li>
                <li>&nbsp;￥2.98</li>
                <li>&nbsp;22222</li>
            </ol>
        </ul>
    </td>
</tr>
<tr>
    <td id="divo6" style=" display:none;">
        <div class="list2_top2">
            <ul>
                <li></li>
                <li><span>关键词数</span><a href="#" class="question"></a></li>
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
            <li>
                <ul class="paihang">
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star2.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                </ul>
                <span>7</span></li>
            <ol id="quality7">
                <li>&nbsp;17(0.11%)</li>
                <li>&nbsp;60(0.83%)</li>
                <li>&nbsp;1(4.76%)</li>
                <li>&nbsp;1.67%</li>
                <li>&nbsp;￥2.98(11.26%)</li>
                <li>&nbsp;￥2.98</li>
                <li>&nbsp;22222</li>
            </ol>
        </ul>
    </td>
</tr>
<tr>
    <td id="divo7" style=" display:none;">
        <div class="list2_top2">
            <ul>
                <li></li>
                <li><span>关键词数</span><a href="#" class="question"></a></li>
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
            <li>
                <ul class="paihang">
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star3.png"></li>
                </ul>
                <span>8</span></li>
            <ol id="quality8">
                <li>&nbsp;17(0.11%)</li>
                <li>&nbsp;60(0.83%)</li>
                <li>&nbsp;1(4.76%)</li>
                <li>&nbsp;1.67%</li>
                <li>&nbsp;￥2.98(11.26%)</li>
                <li>&nbsp;￥2.98</li>
                <li>&nbsp;22222</li>
            </ol>
        </ul>
    </td>
</tr>
<tr>
    <td id="divo8" style=" display:none;">
        <div class="list2_top2">
            <ul>
                <li></li>
                <li><span>关键词数</span><a href="#" class="question"></a></li>
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
            <li>
                <ul class="paihang">
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star2.png"></li>
                </ul>
                <span>9</span></li>
            <ol id="quality9">
                <li>&nbsp;17(0.11%)</li>
                <li>&nbsp;60(0.83%)</li>
                <li>&nbsp;1(4.76%)</li>
                <li>&nbsp;1.67%</li>
                <li>&nbsp;￥2.98(11.26%)</li>
                <li>&nbsp;￥2.98</li>
                <li>&nbsp;22222</li>
            </ol>
        </ul>
    </td>
</tr>
<tr>
    <td id="divo9" style=" display:none;">
        <div class="list2_top2">
            <ul>
                <li></li>
                <li><span>关键词数</span><a href="#" class="question"></a></li>
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
            <li>
                <ul class="paihang">
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                    <li><img src="${pageContext.request.contextPath}/public/img/star.png"></li>
                </ul>
                <span>10</span></li>
            <ol id="quality10">
                <li>&nbsp;17(0.11%)</li>
                <li>&nbsp;60(0.83%)</li>
                <li>&nbsp;1(4.76%)</li>
                <li>&nbsp;1.67%</li>
                <li>&nbsp;￥2.98(11.26%)</li>
                <li>&nbsp;￥2.98</li>
                <li>&nbsp;22222</li>
            </ol>
        </ul>
    </td>
</tr>
<tr>
    <td id="divo10" style=" display:none;">
        <div class="list2_top2">
            <ul>
                <li></li>
                <li><span>关键词数</span><a href="#" class="question"></a></li>
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
            style="margin-right:10px;">跳转到 <input type="text" class="price"></span>&nbsp;&nbsp;<a href="#"> GO</a>

    </div>--%>
            <span class="fr">每页显示
                <select id="keywordQuality1Page"
                        onchange="javascript:limit = $('#keywordQuality1Page option:selected').val();loadKeywordQualityData(null, statDate);">
                    <option selected="selected" value="10">10个</option>
                    <option value="15">15个</option>
                    <option value="20">20个</option>
                </select>
                </span>
</div>
</div>
</div>
<div class="containers hides over">
    <div class="list01_top over">
        <Span>重点关键词监控</Span>
        <a href="#" class="question"></a>
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
                    <input name="reservation" type="image"
                           onclick="javascript:genre = 'importKeywordDefault';$(this).parent().parent().addClass('current');changedLiState($(this).parent()); _posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                           src="${pageContext.request.contextPath}/public/img/date.png">
                </a>
            </li>
        </ul>
    </div>
    <div class="download over">
        <a href="#" class="fr">下载全部 </a>
    </div>
    <div class="list2 wd">
        <table border="0" cellspacing="0" cellspacing="0">
            <tr class="list2_top">
                <td>
                    <ul>
                        <li>
                            &nbsp;<span>关键词</span><b>
                            <p>
                                <input class="one" type="button">
                            </p>

                            <p>
                                <input class="two" type="button">
                            </p></b>
                            <a href="#" class="question">
                            </a>
                        </li>
                        <li>
                            &nbsp;<span>展现</span><b>
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
                            &nbsp;<span>点击</span><b>
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
                            &nbsp;<span>消费</span><b>
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
                            &nbsp;<span>平均点击价格</span><b>
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
                            &nbsp;<span>点击率</span><b>
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
                            &nbsp;<span>转化</span><b>
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
                            &nbsp;<span>平均排名</span><b>
                            <p>
                                <input class="one" type="button"
                                       onclick="javascript:sort = -7;getImportKeywordDefault(null, statDate);">
                            </p>

                            <p>
                                <input class="two" type="button"
                                       onclick="javascript:sort = 7;getImportKeywordDefault(null, statDate);">
                            </p></b>
                        </li>
                        <li>
                            &nbsp;<span>质量度</span><b>
                            <p>
                                <input class="one" type="button">
                            </p>

                            <p>
                                <input class="two" type="button">
                            </p></b>
                        </li>
                        <li>
                            &nbsp;<span>匹配</span><b>
                            <p>
                                <input class="one" type="button">
                            </p>

                            <p>
                                <input class="two" type="button">
                            </p></b>
                            <a href="#" class="question"></a>
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
            <div class="page2 fl">
                <a href="#" class="nextpage1"><span></span></a><a href="#">1</a><a href="#">2</a><a href="#">3</a><a
                    href="#">4</a><a href="#">5</a><a href="#">6</a><a href="#" class="nextpage2"><span></span></a><span
                    style="margin-right:10px;">跳转到 <input type="text" class="price"></span>&nbsp;&nbsp;<a href="#">
                GO</a>

            </div>
            <span class="fr">每页显示
                    <select id="importKeywordSel" onchange="selectChange()">
                        <option value="20">20个</option>
                        <option value="50">50个</option>
                        <option value="100">100个</option>
                    </select> </span>
        </div>
    </div>
</div>
</div>
</div>

<jsp:include page="pageBlock/footer.jsp"/>
</div>
</div>

<!-- javascript -->
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-ui-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.jQuery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/keyword/keywordQuality.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/highcharts.js"></script>
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

    $(function () {
        var $tab_li = $('.tab_menu li');
        $('.tab_menu li').click(function () {
            $(this).addClass('selected').siblings().removeClass('selected');
            var index = $tab_li.index(this);
            $('div.tab_box > div').eq(index).show().siblings().hide();
        });
        var navH = $(".on_title").offset().top;
        $(window).scroll(function () {
            var scroH = $(this).scrollTop();
            if (scroH >= navH) {
                $(".on_title").css({"position": "fixed", "top": "77"});
            } else {
                $(".on_title").css({"position": "static", "margin": "0 auto"});
            }
        });
        //加载日历控件
        $("input[name=reservation]").daterangepicker();
        $(".btnDone").on('click', function () {
            var _startDate = $('.range-start').datepicker('getDate');
            var _endDate = $('.range-end').datepicker('getDate');
            if (_startDate != null && _endDate != null) {
                daterangepicker_start_date = _startDate.Format("yyyy-MM-dd");
                daterangepicker_end_date = _endDate.Format("yyyy-MM-dd");
                if (genre == "keywordQualityCustom") {
                    //区分当前展示的是昨天(1), 近7天(7), 近30天(30), 还是自定义日期(0)的数据
                    loadKeywordQualityData(null, 0);
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
            }
        });

        //默认加载昨天的数据
        loadKeywordQualityData(null, 1);
        getImportKeywordDefault(1);
        //账户表现-----默认加载7天数据
        loadPerformance(null, 7);
        //曲线图表现-----默认加载7天数据
        loadPerformanceCurve(null, 7);

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
    var perCount=0;
    var startPer = 0;
    var pageDetNumber = 0;
    var endPer = 10;
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
                startPer:startPer
            },
            success: function (data) {
                var calssStr = "";
                if (data.rows.length > 0) {
                    $("#performance").empty();
                    $.each(data.rows, function (i, item) {
                        pageDetNumber= item.count;
                        if (i % 2 == 0) {
                            calssStr = "list2_box1";
                        } else {
                            calssStr = "list2_box2";
                        }
                        var _div = "<tr class=" + calssStr + "><td><ul><li> &nbsp;" + item.date + "</li><li> &nbsp;" + item.pcImpression + "</li><li> &nbsp;" + item.pcClick + "</li><li> &nbsp;" + Math.round(item.pcCost * 100) / 100 + "</li><li> &nbsp;" + Math.round(item.pcCtr * 100)/100 + "%</li>"
                                + "<li> &nbsp;" + Math.round(item.pcCpc * 100)/100 + "</li><li> &nbsp;" + item.pcConversion + "</li></ul></td></tr>";
                        $("#performance").append(_div);
                    });
                    if (judgeDet < 1) {
                        var countNumber = 0;
                        if (pageDetNumber % endPer == 0) {
                            countNumber = pageDetNumber / endPer;
                        } else {
                            countNumber = (pageDetNumber / endPer);
                        }
                        var page_html = "<a href='javascript:' id='pageUpDet' class='nextpage1'><span></span></a>"
                        for (var i = 0; i < countNumber; i++) {
                            if(i<10){
                                if (i == 0) {
                                    page_html = page_html + "<a href='javascript:' class='ajc' cname='nameDet' onclick='javascript:startPer = " + i + ";endPer = " + (i + endPer) + ";loadPerformance()'>" + (i + 1) + "</a>";
                                } else {
                                    page_html = page_html + "<a href='javascript:' cname='nameDet' onclick='javascript:startPer = " + (i * endPer) + ";endPer = " + (i * endPer + endPer) + ";loadPerformance()'>" + (i + 1) + "</a>";
                                }
                            }else{
                                if (i == 0) {
                                    page_html = page_html + "<a href='javascript:' class='ajc' cname='nameDet' onclick='javascript:startPer = " + i + ";endPer = " + (i + endPer) + ";loadPerformance()' style='display:none'>" + (i + 1) + "</a>";
                                } else {
                                    page_html = page_html + "<a href='javascript:' cname='nameDet' onclick='javascript:startPer = " + (i * endPer) + ";endPer = " + (i * endPer + endPer) + ";loadPerformance()' style='display:none'>" + (i + 1) + "</a>";
                                }
                            }

                        }
                        page_html = page_html + "<a href='javascript:' id='pageDownDet' class='nextpage2'><span></span></a>" +
                                "<span style='margin-right:10px;'>跳转到 <input type='text' id='goDetID' class='price'></span>&nbsp;&nbsp;<a href='javascript:' id='goDet'> GO</a>"
                        $("#pageUser").append(page_html);
                        judgeDet++;
                    }
                }
            }
        });
        var noneNumStart=0;
        var noneNumEnd=endPer;
        //明细报告分页手动跳转
        $("body").on("click", "#goDet", function () {
            if (($('#goDetID').val() * endPer - 1) <= (pageDetNumber + (endPer-1))) {
                startPer = ($('#goDetID').val() * endPer - endPer);
                endPer = startPer + endPer - 1;
                loadPerformance();
            }
        });
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
        $("body").on("click", "#pageDownDet", function () {
            if(noneNumEnd < pageDetNumber/endPer){
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
    };
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
/**初始化结束**/
/**
 * 曲线图数据配置
 * **/
var loadPerformanceCurve = function (obj, date) {
    if (obj != null) {
        changedLiState(obj);
    }
    colorOne = "#4572A7";
    colorTow = "#40BC2A";
    $("#containerLegend").empty();
    /*初始化曲线图所用需求*/
    $("#containerLegend").append("<div class='tu_top over'><ul><li>展示曲线</li>"
            + "<li><input name='check' cname='impr' xname='' type='checkbox' checked='checked'><span class='blue' ></span><b>展现</b></li>"
            + "<li><input name='check' cname='clicks' xname='' type='checkbox' checked='checked'><span class='green'></span><b>点击</b></li>"
            + "<li><input name='check' cname='cost' xname='' type='checkbox'><span class='red'></span><b>消费</b></li>"
            + "<li><input name='check' cname='ctr' xname='' type='checkbox'><span class='blue2'></span><b>点击率</b></li>"
            + "<li><input name='check' cname='cpc' xname='' type='checkbox'><span class='green2'></span><b>平均点击价格</b></li>"
            + "<li><input name='check' cname='conv' xname='' type='checkbox'><span class='yellow'></span><b>转化</b></li><li><b style='color: red'>最多只能同时选择两项</b></li></ul></div>");

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
                    t_cost.push(Math.round(item.pcCost * 100)/100);
                    t_ctr.push(Math.round(item.pcCtr*100)/100);
                    t_cpc.push(Math.round(item.pcCpc*100)/100);
                    t_conversion.push(item.pcConversion);
                })
            }
            if (data.rows.length > 10) {
                dateInterval = 5;
            }
        }
    });

    dataOne = {
        name: '展现',
        color: '#4572A7',
        type: 'spline',
        yAxis: 1,
        data: t_impr,
        tooltip: {
            valueSuffix: ' 次'
        }
    };
    dataTow = {
        name: '点击',
        color: '#40BC2A',
        type: 'spline',
        data: t_clicks,
        tooltip: {
            valueSuffix: '次'
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
                        colorTow = "#078CC7";
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
    curve();
};
$("#liClick").click(function () {
    setTimeout("curve()", 200);
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
};

var getImportKeywordDefault = function (obj, day) {
    if (obj != null) {
        changedLiState(obj);
    }
    $("#importTr").empty();
    $("#importTr").append("<td style='color:red;'>加载中....</td>");
    statDate = day;
    getDateParam(day);
    $.ajax({
        url: "/import/getImportKeywordList",
        type: "GET",
        dataType: "json",
        data: {
            startDate: daterangepicker_start_date,
            endDate: daterangepicker_end_date,
            limit: limit,
            sort: sort
        },
        success: function (data, textStatus, jqXHR) {
            var calssStr = "";
            var _tr = $("#importTr");
            if (data.length > 0) {
                _tr.empty();
                $.each(data, function (i, item) {
                    if (i % 2 == 0) {
                        calssStr = "list2_box1";
                    } else {
                        calssStr = "list2_box2";
                    }
                    var _div = "<tr class=" + calssStr + "><td><ul><li> &nbsp;" + item.keywordName + "</li><li> &nbsp;" + item.impression + "</li><li> &nbsp;" + item.click + "</li><li> &nbsp;￥" + item.cost + "</li><li> &nbsp;￥" + item.cpc + "</li>"
                            + "<li> &nbsp;" + item.ctr * 100 + "%</li><li> &nbsp;" + item.conversion + "</li><li> &nbsp;" + item.position + "</li></ul></td></tr>";
                    $("#importTr").append(_div);

                })
            } else {
                _tr.empty();
                var _div = "<td colspan='9' style='color:royalblue;'></td>";
                _tr.append(_div);
            }
        }
    });
};


//根据最近几天获取数据
function lisClick(obj, days) {
    if (days != null) {
        getDateParam(days);
    }
    htmlLoding();
    getData();
    changedLiState(obj);
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

</script>

</body>
</html>