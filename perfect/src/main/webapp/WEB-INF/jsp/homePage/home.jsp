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
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" media="all"
          href="${pageContext.request.contextPath}/public/css/accountCss/daterangepicker-bs3.css"/>

</head>
<body>
<div class="top over">
    <div class="logo fl">
        <img src="${pageContext.request.contextPath}/public/img/logo.png">
    </div>
    <div class="logo_text fl">
        大数据智能营销
    </div>
</div>
<div class="concent over">
<div class="nav fl">
    <div class="nav_left over fl">
        <div class="user over">
            <div class="user_img fl">
							<span class="img">
								<img src="${pageContext.request.contextPath}/public/img/user.png">
							</span><span class="name"> JOHN DOE </span>
            </div>
            <div class="user_close fr">
                <a href="#">
                    退出
                </a>
            </div>
        </div>
        <div class="nav_mid">
            <div class="nav_top">
                帐户全景<span></span>
            </div>
            <div class="nav_under over">
                <ul>
                    <li>
                        <h3>推广助手</h3>
                    </li>
                    <li>
                        <span class="list1"></span>
                        <a href="#">
                            账户预警
                        </a>
                    </li>
                    <li>
                        <span class="list2"></span>
                        <a href="#">
                            批量上传
                        </a>
                    </li>
                    <li>
                        <span class="list3"></span>
                        <a href="#">
                            批量操作
                        </a>
                    </li>
                    <li>
                        <span class="list4"></span>
                        <a href="#">
                            关键词查找
                        </a>
                    </li>
                    <li>
                        <span class="list5"></span>
                        <a href="#">
                            推广查询
                        </a>
                    </li>
                    <li>
                        <h3>智能结构</h3>
                    </li>
                    <li>
                        <span class="list6"></span>
                        <a href="#">
                            关键词拓展
                        </a>
                    </li>
                    <li>
                        <span class="list7"></span>
                        <a href="#">
                            智能分组
                        </a>
                    </li>
                    <li>
                        <h3>智能竞价</h3>
                    </li>
                    <li>
                        <span class="list8"></span>
                        <a href="#">
                            诊断关键词
                        </a>
                    </li>
                    <li>
                        <span class="list9"></span>
                        <a href="#">
                            筛选关键词
                        </a>
                    </li>
                    <li>
                        <span class="list10"></span>
                        <a href="#">
                            设置规则
                        </a>
                    </li>
                    <li>
                        <h3>数据报告</h3>
                    </li>
                    <li>
                        <span class="list11"></span>
                        <a href="#">
                            基础报告
                        </a>
                    </li>
                    <li>
                        <span class="list12"></span>
                        <a href="#">
                            定制报告
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="tips fl">
        <input type="image" src="${pageContext.request.contextPath}/public/img/button2.png">
    </div>
</div>

<div class="mid over fr">
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
    <li>
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
    <div class="list01 over" style="border-top:1px solid #d5d5d8;">
        <div class="list01_top over">
            <Span>近期概览</Span>
            <ul id="clickLis">
                <li class="current"><a href="javascript:" onclick="lisClick(this,1)">昨天</a></li>
                <li><a href="javascript:" onclick="lisClick(this,7)">近7天</a></li>
                <li><a href="javascript:" onclick="lisClick(this,30)">近30天</a></li>
                <li class="date">
                    <a href="javascript:" onclick="lisClick(this,null);">
                        自定义

                    </a>
                    <input name="reservation" class=" fa fa-calendar " type="image"
                           src="${pageContext.request.contextPath}/public/img/date.png">

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
        <a href="#" class="question"></a>
        <ul>
            <li class="current">
                <a href="#">
                    昨天
                </a>
            </li>
            <li>
                <a href="#">
                    近7天
                </a>
            </li>
            <li>
                <a href="#">
                    近30天
                </a>
            </li>
            <li class="date">
                <a href="#">
                    自定义
                    <input name="reservation" class=" fa fa-calendar " type="image"
                           src="${pageContext.request.contextPath}/public/img/date.png">
                </a>
            </li>
        </ul>
    </div>
    <div class="shuju">
        <img src="images/tu.jpg">
    </div>
</div>
<div class="list01_under3 over">
<div class="list01_top over">
    <Span>分日表现</Span>
    <a href="#" class="question"></a>
    <ul>
        <li class="current">
            <a href="#">
                昨天
            </a>
        </li>
        <li>
            <a href="#">
                近7天
            </a>
        </li>
        <li>
            <a href="#">
                近30天
            </a>
        </li>
        <li class="date">
            <a href="#">
                自定义
                <input name="reservation" class=" fa fa-calendar " type="image"
                       src="${pageContext.request.contextPath}/public/img/date.png">
            </a>
        </li>
    </ul>
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
                            <input class="one" type="button">
                        </p>

                        <p>
                            <input class="two" type="button">
                        </p></b>
                    </li>
                    <li>
                        &nbsp;<span>展现量</span><b>
                        <p>
                            <input class="one" type="button">
                        </p>

                        <p>
                            <input class="two" type="button">
                        </p></b>
                    </li>
                    <li>
                        &nbsp;<span>点击量</span><b>
                        <p>
                            <input class="one" type="button">
                        </p>

                        <p>
                            <input class="two" type="button">
                        </p></b>
                    </li>
                    <li>
                        &nbsp;<span>消费</span><b>
                        <p>
                            <input class="one" type="button">
                        </p>

                        <p>
                            <input class="two" type="button">
                        </p></b>
                    </li>
                    <li>
                        &nbsp;<span>点击率</span><b>
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
                        &nbsp;<span>平均点击价格</span><b>
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
                        &nbsp;<span>平均排名</span><b>
                        <p>
                            <input class="one" type="button">
                        </p>

                        <p>
                            <input class="two" type="button">
                        </p></b>
                        <a href="#" class="question"></a>
                    </li>
                    <li>
                        &nbsp;<span>转化(页面)</span><b>
                        <p>
                            <input class="one" type="button">
                        </p>

                        <p>
                            <input class="two" type="button">
                        </p></b>
                        <a href="#" class="question">
                        </a>
                    </li>
                </ul>
            </td>
        </tr>
        </thead>
        <tbody id="performance">
        <tr class="list2_box1">
            <td>
                <ul>
                    <li>
                        &nbsp;2014年6月9日
                    </li>
                    <li>
                        &nbsp;3.902
                    </li>
                    <li>
                        &nbsp;3.902
                    </li>
                    <li>
                        &nbsp;13
                    </li>
                    <li>
                        &nbsp;￥0.91
                    </li>
                    <li>
                        &nbsp;￥0.91
                    </li>
                    <li>
                        &nbsp;0.33%
                    </li>
                    <li>
                        &nbsp;0.00
                    </li>

                </ul>
            </td>
        </tr>
        <tr class="list2_box2" onclick="TestBlack('divc1');">
            <td>
                <ul>
                    <li>
                        &nbsp;2014年6月9日
                    </li>
                    <li>
                        &nbsp;3.902
                    </li>
                    <li>
                        &nbsp;3.902
                    </li>
                    <li>
                        &nbsp;13
                    </li>
                    <li>
                        &nbsp;￥0.91
                    </li>
                    <li>
                        &nbsp;￥0.91
                    </li>
                    <li>
                        &nbsp;0.33%
                    </li>
                    <li>
                        &nbsp;0.00
                    </li>

                </ul>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="download over fr">
										<span>每页显示
											<select>
                                                <option>10个</option>
                                                <option>9个</option>
                                                <option>8个</option>
                                            </select> </span>
        <a href="#">
            下载全部
        </a>
    </div>
</div>
</div>
</div>
<div class="containers hides over">
<div class="list01_top over">
    <Span>质量度分析</Span>
    <a href="#" class="question"></a>
    <ul id="keywordQualityClass">
        <li class="current">
            <a href="javascript:loadKeywordQualityData(1);">
                昨天
            </a>
        </li>
        <li>
            <a href="javascript:loadKeywordQualityData(7);">
                近7天
            </a>
        </li>
        <li>
            <a href="javascript:loadKeywordQualityData(30);">
                近30天
            </a>
        </li>
        <li class="date">
            <a>
                自定义
                <input name="reservation" class=" fa fa-calendar " type="image"
                       onclick="javascript:genre = 'keywordQualityCustom';"
                       src="${pageContext.request.contextPath}/public/img/date.png">
            </a>
        </li>
    </ul>
    <div class="download over fr">
        <a href="#">
            下载全部
        </a>
    </div>
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
                           onclick="javascript:category = 'impression';sort = -1;loadKeywordQualityData(statDate);"/>
                </p>

                <p>
                    <input class="two" type="button"
                           onclick="javascript:category = 'impression';sort = 1;loadKeywordQualityData(statDate);"/>
                </p></b>
            </li>
            <li>
                &nbsp;<span>点击</span><b>
                <p>
                    <input class="one" type="button"
                           onclick="javascript:category = 'click';sort = -1;loadKeywordQualityData(statDate);"/>
                </p>

                <p>
                    <input class="two" type="button"
                           onclick="javascript:category = 'click';sort = 1;loadKeywordQualityData(statDate);"/>
                </p></b>
            </li>
            <li>
                &nbsp;<span>点击率</span><b>
                <p>
                    <input class="one" type="button"
                           onclick="javascript:category = 'ctr';sort = -1;loadKeywordQualityData(statDate);"/>
                </p>

                <p>
                    <input class="two" type="button"
                           onclick="javascript:category = 'ctr';sort = 1;loadKeywordQualityData(statDate);"/>
                </p></b>
                <a href="#" class="question">
                </a>
            </li>
            <li>
                &nbsp;<span>消费</span><b>
                <p>
                    <input class="one" type="button"
                           onclick="javascript:category = 'cost';sort = -1;loadKeywordQualityData(statDate);"/>
                </p>

                <p>
                    <input class="two" type="button"
                           onclick="javascript:category = 'cost';sort = 1;loadKeywordQualityData(statDate);"/>
                </p></b>
                <a href="#" class="question">
                </a>
            </li>
            <li>
                &nbsp;<span>平均点击价格</span><b>
                <p>
                    <input class="one" type="button"
                           onclick="javascript:category = 'cpc';sort = -1;loadKeywordQualityData(statDate);"/>
                </p>

                <p>
                    <input class="two" type="button"
                           onclick="javascript:category = 'cpc';sort = 1;loadKeywordQualityData(statDate);"/>
                </p></b>
                <a href="#" class="question"></a>
            </li>
            <li>
                &nbsp;<span>转化</span><b>
                <p>
                    <input class="one" type="button"
                           onclick="javascript:category = 'conversion';sort = -1;loadKeywordQualityData(statDate);"/>
                </p>

                <p>
                    <input class="two" type="button"
                           onclick="javascript:category = 'conversion';sort = 1;loadKeywordQualityData(statDate);"/>
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
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star2.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                </ul>
                <span>0</span>
            </li>
            <li>
                &nbsp;17(0.11%)
            </li>
            <li>
                &nbsp;60(0.83%)
            </li>
            <li>
                &nbsp;1(4.76%)
            </li>
            <li>
                &nbsp;1.67%
            </li>
            <li>
                &nbsp;￥2.98(11.26%)
            </li>
            <li>
                &nbsp;￥2.98
            </li>
            <li>
                &nbsp;-
            </li>
        </ul>
    </td>
</tr>
<tr>
    <td id="divo">
        <div class="list2_top2">
            <ul>
                <li></li>
                <li>
                    <span>关键词</span>
                    <a href="#" class="question">
                    </a>
                </li>
                <li>
                    <span>展现</span>
                </li>
                <li>
                    <span>点击</span>
                </li>
                <li>
                    <span>点击率</span>
                </li>
                <li>
                    <span>消费</span>
                </li>
                <li>
                    <span>平均点击价格</span>
                </li>
                <li>
                    <span>转化</span>
                </li>
            </ul>
        </div>

        <div id="keywordQuality1"></div>

        <div>
            <dl class="fr">
                每页显示
                <select id="keywordQuality1Page"
                        onchange="javascript:limit = $('#keywordQuality1Page option:selected').val();loadKeywordQualityData(statDate);">
                    <option selected="selected" value="10">10个</option>
                    <option value="15">15个</option>
                    <option value="20">20个</option>
                </select>
            </dl>
        </div>
    </td>
</tr>
<tr class="list2_box2" onclick="TestBlack('divo1');">
    <td>
        <ul>
            <li>
                <ul class="paihang">
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star2.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                </ul>
                <span>1</span>
            </li>
            <li>
                &nbsp;17(0.11%)
            </li>
            <li>
                &nbsp;60(0.83%)
            </li>
            <li>
                &nbsp;1(4.76%)
            </li>
            <li>
                &nbsp;1.67%
            </li>
            <li>
                &nbsp;￥2.98(11.26%)
            </li>
            <li>
                &nbsp;￥2.98
            </li>
            <li>
                &nbsp;-
            </li>
        </ul>
    </td>
</tr>
<td id="divo1" style=" display:none;">
    <div class="list2_top2">
        <ul>
            <li></li>
            <li>
                <span>关键词</span>
                <a href="#" class="question">
                </a>
            </li>
            <li>
                <span>展现</span>
            </li>
            <li>
                <span>点击</span>
            </li>
            <li>
                <span>点击率</span>
            </li>
            <li>
                <span>消费</span>
            </li>
            <li>
                <span>平均点击价格</span>
            </li>
            <li>
                <span>转化</span>
            </li>
        </ul>
    </div>
    <div id="keywordQuality2"></div>
    <div>
        <dl class="fr">
            每页显示
            <select>
                <option selected="selected" value="10">10个</option>
                <option value="15">15个</option>
                <option value="20">20个</option>
            </select>
        </dl>
    </div>
</td>
</tr>
<tr class="list2_box1" onclick="TestBlack('divo2');">
    <td>
        <ul>
            <li>
                <ul class="paihang">
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star2.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                </ul>
                <span>2</span>
            </li>
            <li>
                &nbsp;17(0.11%)
            </li>
            <li>
                &nbsp;60(0.83%)
            </li>
            <li>
                &nbsp;1(4.76%)
            </li>
            <li>
                &nbsp;1.67%
            </li>
            <li>
                &nbsp;￥2.98(11.26%)
            </li>
            <li>
                &nbsp;￥2.98
            </li>
            <li>
                &nbsp;-
            </li>
        </ul>
    </td>
</tr>
<td id="divo2" style=" display:none;">
    <div class="list2_top2">
        <ul>
            <li></li>
            <li>
                <span>关键词</span>
                <a href="#" class="question">
                </a>
            </li>
            <li>
                <span>展现</span>
            </li>
            <li>
                <span>点击</span>
            </li>
            <li>
                <span>点击率</span>
            </li>
            <li>
                <span>消费</span>
            </li>
            <li>
                <span>平均点击价格</span>
            </li>
            <li>
                <span>转化</span>
            </li>
        </ul>
    </div>
    <div id="keywordQuality3"></div>
    <div>
        <dl class="fr">
            每页显示
            <select>
                <option selected="selected" value="10">10个</option>
                <option value="15">15个</option>
                <option value="20">20个</option>
            </select>
        </dl>
    </div>
</td>
</tr>
<tr class="list2_box2" onclick="TestBlack('divo3');">
    <td>
        <ul>
            <li>
                <ul class="paihang">
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star2.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                </ul>
                <span>3</span>
            </li>
            <li>
                &nbsp;17(0.11%)
            </li>
            <li>
                &nbsp;60(0.83%)
            </li>
            <li>
                &nbsp;1(4.76%)
            </li>
            <li>
                &nbsp;1.67%
            </li>
            <li>
                &nbsp;￥2.98(11.26%)
            </li>
            <li>
                &nbsp;￥2.98
            </li>
            <li>
                &nbsp;-
            </li>
        </ul>
    </td>
</tr>
<td id="divo3" style=" display:none;">
    <div class="list2_top2">
        <ul>
            <li></li>
            <li>
                <span>关键词</span>
                <a href="#" class="question">
                </a>
            </li>
            <li>
                <span>展现</span>
            </li>
            <li>
                <span>点击</span>
            </li>
            <li>
                <span>点击率</span>
            </li>
            <li>
                <span>消费</span>
            </li>
            <li>
                <span>平均点击价格</span>
            </li>
            <li>
                <span>转化</span>
            </li>
        </ul>
    </div>
    <div id="keywordQuality4"></div>
    <div>
        <dl class="fr">
            每页显示
            <select>
                <option selected="selected" value="10">10个</option>
                <option value="15">15个</option>
                <option value="20">20个</option>
            </select>
        </dl>
    </div>
</td>
</tr>
<tr class="list2_box1" onclick="TestBlack('divo4');">
    <td>
        <ul>
            <li>
                <ul class="paihang">
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star2.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                </ul>
                <span>4</span>
            </li>
            <li>
                &nbsp;17(0.11%)
            </li>
            <li>
                &nbsp;60(0.83%)
            </li>
            <li>
                &nbsp;1(4.76%)
            </li>
            <li>
                &nbsp;1.67%
            </li>
            <li>
                &nbsp;￥2.98(11.26%)
            </li>
            <li>
                &nbsp;￥2.98
            </li>
            <li>
                &nbsp;-
            </li>
        </ul>
    </td>
</tr>
<td id="divo4" style=" display:none;">
    <div class="list2_top2">
        <ul>
            <li></li>
            <li>
                <span>关键词</span>
                <a href="#" class="question">
                </a>
            </li>
            <li>
                <span>展现</span>
            </li>
            <li>
                <span>点击</span>
            </li>
            <li>
                <span>点击率</span>
            </li>
            <li>
                <span>消费</span>
            </li>
            <li>
                <span>平均点击价格</span>
            </li>
            <li>
                <span>转化</span>
            </li>
        </ul>
    </div>
    <div id="keywordQuality5"></div>
    <div>
        <dl class="fr">
            每页显示
            <select>
                <option selected="selected" value="10">10个</option>
                <option value="15">15个</option>
                <option value="20">20个</option>
            </select>
        </dl>
    </div>
</td>
</tr>
<tr class="list2_box2" onclick="TestBlack('divo5');">
    <td>
        <ul>
            <li>
                <ul class="paihang">
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star2.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                </ul>
                <span>5</span>
            </li>
            <li>
                &nbsp;17(0.11%)
            </li>
            <li>
                &nbsp;60(0.83%)
            </li>
            <li>
                &nbsp;1(4.76%)
            </li>
            <li>
                &nbsp;1.67%
            </li>
            <li>
                &nbsp;￥2.98(11.26%)
            </li>
            <li>
                &nbsp;￥2.98
            </li>
            <li>
                &nbsp;-
            </li>
        </ul>
    </td>
</tr>
<td id="divo5" style=" display:none;">
    <div class="list2_top2">
        <ul>
            <li></li>
            <li>
                <span>关键词</span>
                <a href="#" class="question">
                </a>
            </li>
            <li>
                <span>展现</span>
            </li>
            <li>
                <span>点击</span>
            </li>
            <li>
                <span>点击率</span>
            </li>
            <li>
                <span>消费</span>
            </li>
            <li>
                <span>平均点击价格</span>
            </li>
            <li>
                <span>转化</span>
            </li>
        </ul>
    </div>
    <div id="keywordQuality6"></div>
    <div>
        <dl class="fr">
            每页显示
            <select>
                <option selected="selected" value="10">10个</option>
                <option value="15">15个</option>
                <option value="20">20个</option>
            </select>
        </dl>
    </div>
</td>
</tr>
<tr class="list2_box1" onclick="TestBlack('divo6');">
    <td>
        <ul>
            <li>
                <ul class="paihang">
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star2.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                </ul>
                <span>6</span>
            </li>
            <li>
                &nbsp;17(0.11%)
            </li>
            <li>
                &nbsp;60(0.83%)
            </li>
            <li>
                &nbsp;1(4.76%)
            </li>
            <li>
                &nbsp;1.67%
            </li>
            <li>
                &nbsp;￥2.98(11.26%)
            </li>
            <li>
                &nbsp;￥2.98
            </li>
            <li>
                &nbsp;-
            </li>
        </ul>
    </td>
</tr>
<td id="divo6" style=" display:none;">
    <div class="list2_top2">
        <ul>
            <li></li>
            <li>
                <span>关键词</span>
                <a href="#" class="question">
                </a>
            </li>
            <li>
                <span>展现</span>
            </li>
            <li>
                <span>点击</span>
            </li>
            <li>
                <span>点击率</span>
            </li>
            <li>
                <span>消费</span>
            </li>
            <li>
                <span>平均点击价格</span>
            </li>
            <li>
                <span>转化</span>
            </li>
        </ul>
    </div>
    <div id="keywordQuality7"></div>
    <div>
        <dl class="fr">
            每页显示
            <select>
                <option selected="selected" value="10">10个</option>
                <option value="15">15个</option>
                <option value="20">20个</option>
            </select>
        </dl>
    </div>
</td>
</tr>
<tr class="list2_box2" onclick="TestBlack('divo7');">
    <td>
        <ul>
            <li>
                <ul class="paihang">
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star2.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                </ul>
                <span>7</span>
            </li>
            <li>
                &nbsp;17(0.11%)
            </li>
            <li>
                &nbsp;60(0.83%)
            </li>
            <li>
                &nbsp;1(4.76%)
            </li>
            <li>
                &nbsp;1.67%
            </li>
            <li>
                &nbsp;￥2.98(11.26%)
            </li>
            <li>
                &nbsp;￥2.98
            </li>
            <li>
                &nbsp;-
            </li>
        </ul>
    </td>
</tr>
<td id="divo7" style=" display:none;">
    <div class="list2_top2">
        <ul>
            <li></li>
            <li>
                <span>关键词</span>
                <a href="#" class="question">
                </a>
            </li>
            <li>
                <span>展现</span>
            </li>
            <li>
                <span>点击</span>
            </li>
            <li>
                <span>点击率</span>
            </li>
            <li>
                <span>消费</span>
            </li>
            <li>
                <span>平均点击价格</span>
            </li>
            <li>
                <span>转化</span>
            </li>
        </ul>
    </div>
    <div id="keywordQuality8"></div>
    <div>
        <dl class="fr">
            每页显示
            <select>
                <option selected="selected" value="10">10个</option>
                <option value="15">15个</option>
                <option value="20">20个</option>
            </select>
        </dl>
    </div>
</td>
</tr>
<tr class="list2_box1" onclick="TestBlack('divo8');">
    <td>
        <ul>
            <li>
                <ul class="paihang">
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star2.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                </ul>
                <span>8</span>
            </li>
            <li>
                &nbsp;17(0.11%)
            </li>
            <li>
                &nbsp;60(0.83%)
            </li>
            <li>
                &nbsp;1(4.76%)
            </li>
            <li>
                &nbsp;1.67%
            </li>
            <li>
                &nbsp;￥2.98(11.26%)
            </li>
            <li>
                &nbsp;￥2.98
            </li>
            <li>
                &nbsp;-
            </li>
        </ul>
    </td>
</tr>
<td id="divo8" style=" display:none;">
    <div class="list2_top2">
        <ul>
            <li></li>
            <li>
                <span>关键词</span>
                <a href="#" class="question">
                </a>
            </li>
            <li>
                <span>展现</span>
            </li>
            <li>
                <span>点击</span>
            </li>
            <li>
                <span>点击率</span>
            </li>
            <li>
                <span>消费</span>
            </li>
            <li>
                <span>平均点击价格</span>
            </li>
            <li>
                <span>转化</span>
            </li>
        </ul>
    </div>
    <div id="keywordQuality9"></div>
    <div>
        <dl class="fr">
            每页显示
            <select>
                <option selected="selected" value="10">10个</option>
                <option value="15">15个</option>
                <option value="20">20个</option>
            </select>
        </dl>
    </div>
</td>
</tr>
<tr class="list2_box2" onclick="TestBlack('divo9');">
    <td>
        <ul>
            <li>
                <ul class="paihang">
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star2.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                </ul>
                <span>9</span>
            </li>
            <li>
                &nbsp;17(0.11%)
            </li>
            <li>
                &nbsp;60(0.83%)
            </li>
            <li>
                &nbsp;1(4.76%)
            </li>
            <li>
                &nbsp;1.67%
            </li>
            <li>
                &nbsp;￥2.98(11.26%)
            </li>
            <li>
                &nbsp;￥2.98
            </li>
            <li>
                &nbsp;-
            </li>
        </ul>
    </td>
</tr>
<td id="divo9" style=" display:none;">
<div class="list2_top2">
    <ul>
        <li></li>
        <li>
            <span>关键词</span>
            <a href="#" class="question">
            </a>
        </li>
        <li>
            <span>展现</span>
        </li>
        <li>
            <span>点击</span>
        </li>
        <li>
            <span>点击率</span>
        </li>
        <li>
            <span>消费</span>
        </li>
        <li>
            <span>平均点击价格</span>
        </li>
        <li>
            <span>转化</span>
        </li>
    </ul>
</div>
<div>
    <ul>
        <li></li>
        <li>
            <span>搜索引擎优化</span><span class="green_arrow wd3"></span>
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
    </ul>
</div>
<div>
    <ul>
        <li></li>
        <li>
            <span>搜索引擎优化</span><span class="red_arrow  wd3"></span>
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
    </ul>
</div>
<div>
    <ul>
        <li></li>
        <li>
            <span>搜索引擎优化</span><span class="hot"></span>
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
    </ul>
</div>
<div>
    <ul>
        <li></li>
        <li>
            <span>搜索引擎优化</span><span class="green_arrow wd3"></span>
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
    </ul>
</div>
<div>
    <ul>
        <li></li>
        <li>
            <span>搜索引擎优化</span><span class="red_arrow wd3"></span>
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
    </ul>
</div>
<div>
    <ul>
        <li></li>
        <li>
            <span>搜索引擎优化</span><span class="hot"></span>
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
    </ul>
</div>
<div>
    <ul>
        <li></li>
        <li>
            <span>搜索引擎优化</span><span class="red_arrow wd3"></span>
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
        <li>
            17(0.11%)
        </li>
    </ul>
</div>
<div>
    <dl class="fr">
        每页显示
        <select>
            <option selected="selected" value="10">10个</option>
            <option value="15">15个</option>
            <option value="20">20个</option>
        </select>
    </dl>
</div>
</td>
</tr>
<tr class="list2_box2" onclick="TestBlack('divo10');">
    <td>
        <ul>
            <li>
                <ul class="paihang">
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star2.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                </ul>
                <span>10</span>
            </li>
            <li>
                &nbsp;17(0.11%)
            </li>
            <li>
                &nbsp;60(0.83%)
            </li>
            <li>
                &nbsp;1(4.76%)
            </li>
            <li>
                &nbsp;1.67%
            </li>
            <li>
                &nbsp;￥2.98(11.26%)
            </li>
            <li>
                &nbsp;￥2.98
            </li>
            <li>
                &nbsp;-
            </li>
        </ul>
    </td>
</tr>
<td id="divo10" style=" display:none;">
    <div class="list2_top2">
        <ul>
            <li></li>
            <li>
                <span>关键词</span>
                <a href="#" class="question">
                </a>
            </li>
            <li>
                <span>展现</span>
            </li>
            <li>
                <span>点击</span>
            </li>
            <li>
                <span>点击率</span>
            </li>
            <li>
                <span>消费</span>
            </li>
            <li>
                <span>平均点击价格</span>
            </li>
            <li>
                <span>转化</span>
            </li>
        </ul>
    </div>

    <div>
        <dl class="fr">
            每页显示
            <select>
                <option selected="selected" value="10">10个</option>
                <option value="15">15个</option>
                <option value="20">20个</option>
            </select>
        </dl>
    </div>
</td>
</tr>
</table>
</div>
</div>
<div class="containers hides over">
<div class="list01_top over">
    <Span>重点关键词监控</Span>
    <a href="#" class="question"></a>
    <ul>
        <li class="current">
            <a href="#">
                昨天
            </a>
        </li>
        <li>
            <a href="#">
                近7天
            </a>
        </li>
        <li>
            <a href="#">
                近30天
            </a>
        </li>
        <li class="date">
            <a href="javascript:getImportKeywordDefault(1)">
                自定义
                <input name="reservation" class=" fa fa-calendar " type="image"
                       src="${pageContext.request.contextPath}/public/img/date.png">
            </a>
        </li>
    </ul>
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
                    <input class="one" type="button">
                </p>

                <p>
                    <input class="two" type="button">
                </p></b>
            </li>
            <li>
                &nbsp;<span>点击</span><b>
                <p>
                    <input class="one" type="button">
                </p>

                <p>
                    <input class="two" type="button">
                </p></b>
            </li>
            <li>
                &nbsp;<span>消费</span><b>
                <p>
                    <input class="one" type="button">
                </p>

                <p>
                    <input class="two" type="button">
                </p></b>
            </li>
            <li>
                &nbsp;<span>平均点击价格</span><b>
                <p>
                    <input class="one" type="button">
                </p>

                <p>
                    <input class="two" type="button">
                </p></b>
            </li>
            <li>
                &nbsp;<span>点击率</span><b>
                <p>
                    <input class="one" type="button">
                </p>

                <p>
                    <input class="two" type="button">
                </p></b>
            </li>
            <li>
                &nbsp;<span>转化</span><b>
                <p>
                    <input class="one" type="button">
                </p>

                <p>
                    <input class="two" type="button">
                </p></b>
            </li>
            <li>
                &nbsp;<span>平均排名</span><b>
                <p>
                    <input class="one" type="button">
                </p>

                <p>
                    <input class="two" type="button">
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
<tr class="list2_box1" >
    <td>
        <ul>
            <li>
                &nbsp;<span>搜索引擎优化1</span><span class="green_arrow wd3"></span>
            </li>
            <li>
                &nbsp;3.902
            </li>
            <li>
                &nbsp;13
            </li>
            <li>
                &nbsp;￥0.91
            </li>
            <li>
                &nbsp;￥0.91
            </li>
            <li>
                &nbsp;0.33%
            </li>
            <li>
                &nbsp;0.00
            </li>
            <li>
                &nbsp;2.24
            </li>
            <li>
                &nbsp;
                <ul class="paihang">
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star2.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/public/img/star3.png">
                    </li>
                </ul>
                <span>0</span>
            </li>
            <li>
                &nbsp;广泛
            </li>
        </ul>
    </td>

</tr>



</table>
<div class="download over fr">
									<span>每页显示
										<select>
                                            <option>10个</option>
                                            <option>9个</option>
                                            <option>8个</option>
                                        </select> </span>
    <a href="#">
        下载全部
    </a>
</div>
</div>
</div>
</div>
</div>
<div class="footer">
    <p>
        <a href="#">
            关于我们
        </a>
        |
        <a href="#">
            联系我们
        </a>
        |
        <a href="#">
            隐私条款
        </a>
        |
        <a href="#">
            诚聘英才
        </a>
    </p>

    <p>
        Copyright@2013 perfect-cn.cn All Copyright Reserved. 版权所有：北京普菲特广告有限公司京ICP备***号
    </p>
</div>
</div>
</div>

<!-- javascript -->
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/accountJs/moment.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/accountJs/daterangepicker.js"></script>
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

    $(function () {
        var $tab_li = $('.tab_menu li');
        $('.tab_menu li').click(function () {
            $(this).addClass('selected').siblings().removeClass('selected');
            var index = $tab_li.index(this);
            $('div.tab_box > div').eq(index).show().siblings().hide();
        });

        var navH = $(".nav").offset().top;
        $(window).scroll(function () {
            var scroH = $(this).scrollTop();
            if (scroH >= navH) {
                $(".nav").css({
                    "position": "fixed",
                    "top": "0"
                });
            } else {
                $(".nav").css({
                    "position": "static",
                    "margin": "0 auto"
                });
            }
            console.log(scroH == navH);
        });

        //加载日历控件
        $("input[name=reservation]").daterangepicker({}, function (startDate, endDate) {
                    var date = new Date();
                    date.setTime(startDate);
                    daterangepicker_start_date = date.Format("yyyy-MM-dd");
                    date.setTime(endDate);
                    daterangepicker_end_date = date.Format("yyyy-MM-dd");
                    if (genre == "keywordQualityCustom") {
                        //区分当前展示的是昨天(1), 近7天(7), 近30天(30), 还是自定义日期(0)的数据
                        loadKeywordQualityData(0);
                    }

                }
        );

        //默认加载昨天的数据
        loadKeywordQualityData(1);
        getImportKeywordDefault(1);
        loadPerformance();

    });

    function TestBlack(TagName) {
        var obj = document.getElementById(TagName);
        if (obj.style.display == "") {
            obj.style.display = "none";
        } else {
            obj.style.display = "";
        }
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

    //关键词质量度数据加载
    var loadKeywordQualityData = function (param) {
        statDate = param;
        getDateParam(param);
        $.ajax({
            url: "/keywordQuality/list",
            type: "GET",
            dataType: "json",
            data: {
                startDate: daterangepicker_start_date,
                endDate: daterangepicker_end_date,
                fieldName: category,
                sort: sort,
                limit: limit
            },
            success: function (data, textStatus, jqXHR) {
                $("#keywordQuality1").empty();
                if (data.rows.length > 0) {
                    $.each(data.rows, function (i, item) {
                        var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                                "<li>" + item.impression + "</li><li>" + item.click + "</li><li>" + (item.ctr * 100) + "%</li><li>" + item.cost + "</li>" +
                                "<li>" + item.cpc + "</li><li>" + item.conversion + "</li></ul></div>";
                        $("#keywordQuality1").append(_div);
                    })
                }
            }
        });
    };
    var loadPerformance = function () {
        $.ajax({
            url: "/account/performance",
            type: "GET",
            dataType: "json",
            data: {
                startDate: daterangepicker_start_date,
                endDate: daterangepicker_end_date,
                fieldName: category
            },
            success: function (data, textStatus, jqXHR) {
                var calssStr = "";
                if (data.length > 0) {
                    $.each(data, function (i, item) {
                        if (i % 2 == 0) {
                            calssStr = "list2_box1";
                        } else {
                            calssStr = "list2_box2";
                        }
                        var _div = "<tr class=" + calssStr + "><td><ul><li> &nbsp;" + item.keywordName + "</li><li> &nbsp;" + item.impression + "</li><li> &nbsp;" + item.click + "</li><li> &nbsp;" + item.cost + "</li><li> &nbsp;" + item.ctr * 100 + "%</li>"
                                + "<li> &nbsp;" + item.cpc + "</li><li> &nbsp;" + item.conversion + "</li></ul></td></tr>";
                        $("#performance").append(_div);
                    })
                }
            }
        });
    };
</script>
<script>
    /*曲线图数据配置*/
    $(function () {
        $('#container').highcharts({
            chart: {
                zoomType: 'xy'
            },
            title: {
                text: 'Average Monthly Temperature and Rainfall in Tokyo'
            },
            subtitle: {
                text: 'Source: WorldClimate.com'
            },
            xAxis: [
                {
                    categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                        'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
                }
            ],
            yAxis: [
                { // Primary yAxis
                    labels: {
                        format: '{value}°C',
                        style: {
                            color: '#89A54E'
                        }
                    },
                    title: {
                        text: 'Temperature',
                        style: {
                            color: '#89A54E'
                        }
                    }
                },
                { // Secondary yAxis
                    title: {
                        text: 'Rainfall',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    labels: {
                        format: '{value} mm',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    opposite: true
                }
            ],
            tooltip: {
                shared: true
            },
            legend: {
                layout: 'vertical',
                align: 'left',
                x: 920,
                verticalAlign: 'top',
                y: 100,
                floating: true,
                backgroundColor: '#FFFFFF'
            },
            series: [
                {
                    name: '点击率',
                    color: '#4572A7',
                    type: 'spline',
                    data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4],
                    tooltip: {
                        valueSuffix: ' mm'
                    }

                },
                {
                    name: 'Temperature',
                    color: '#89A54E',
                    type: 'spline',
                    data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6],
                    tooltip: {
                        valueSuffix: '°C'
                    }
                }
            ]
        });
    });


    var getImportKeywordDefault = function (day) {
        getDateParam(day);
        $.get("/main/getImportKeywordList", {
            startDate: daterangepicker_start_date,
            endDate: daterangepicker_end_date,
            userTable: "aaa123"
        }, function (result) {

        });
    };

    /*===========================================================账户概览start================*/

    //根据最近几天获取数据
    function lisClick(obj, days) {
        htmlLoding();
        getData(days);
        changedLiState($(obj));
    }

    //改变li的样式状态
    function changedLiState(obj) {
        $("#clickLis li").each(function () {
            $(this).removeClass("current");
        });
        obj.parent().addClass("current");
    }

    //数据获取中。。。
    function htmlLoding() {
        $(".impression").html("<h6>数据获取中...</h6>");
        $(".click").html("<h6>数据获取中...</h6>");
        $(".cos").html("<h6>数据获取中...</h6>");
        $(".conversion").html("<h6>数据获取中...</h6>");
    }

    //默认为数据获取中
    htmlLoding();

    //获取数据
    function getData(days) {
        $.ajax({
            url: "/account/getAccountOverviewData",
            type: "get",
            dataType: "json",
            data: {"days": days, "startDate": daterangepicker_start_date, "endDate": daterangepicker_end_date},
            success: function (data) {
                $(".impression").html(data.impression);
                $(".click").html(data.click);
                $(".cos").html(data.cos);
                $(".conversion").html(data.conversion);
            }
        });
        daterangepicker_start_date = null;
        daterangepicker_end_date = null;
    }

    //初始化账户概览页面数据
    getData(1);//默认显示昨天的汇总数据


    /*===========================================================账户概览end==============================================*/

</script>

</body>
</html>