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
                        <input type="image" src="${pageContext.request.contextPath}/public/img/date.png">
                    </a>
                </li>
            </ul>
        </div>
        <div class="list01_under2 over">
            <ul>
                <li>
                    <div class="blue1 fl wd1"></div>
                    <div class="blue2 fl wd2">
                        <Span>2,34,402</Span>

                        <p>
                            展现次数
                        </p>
                    </div>
                </li>
                <li>
                    <div class="green1 fl wd1"></div>
                    <div class="green2 fl wd2">
                        <Span>2344</Span>

                        <p>
                            点击次数
                        </p>
                    </div>
                </li>
                <li>
                    <div class="red1 fl wd1"></div>
                    <div class="red2 fl wd2">
                        <Span>2,34,402</Span>

                        <p>
                            展现次数
                        </p>
                    </div>
                </li>
                <li>
                    <div class="yellow1 fl wd1"></div>
                    <div class="yellow2 fl wd2">
                        <Span>2344</Span>

                        <p>
                            转化次数
                        </p>
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
                    <input type="image" src="${pageContext.request.contextPath}/public/img/date.png">
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
                <input type="image" src="${pageContext.request.contextPath}/public/img/date.png">
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
<tr class="list2_box1" onclick="TestBlack('divc');">
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
<td id="divc" style="display:none;">
<div class="list2_top2">
    <ul>
        <li></li>
        <li>
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
        </select>
    </dl>
</div>
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
<td id="divc1" style="display:none;">
<div class="list2_top2">
    <ul>
        <li></li>
        <li>
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
        </select>
    </dl>
</div>
</td>
</tr>
<tr class="list2_box1" onclick="TestBlack('divc2');">
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
<td id="divc2" style="display:none;">
<div class="list2_top2">
    <ul>
        <li></li>
        <li>
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
        </select>
    </dl>
</div>
</td>
</tr>
<tr class="list2_box2" onclick="TestBlack('divc3');">
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
<td id="divc3" style="display:none;">
<div class="list2_top2">
    <ul>
        <li></li>
        <li>
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
        </select>
    </dl>
</div>
</td>
</tr>
<tr class="list2_box1" onclick="TestBlack('divc4');">
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
<td id="divc4" style="display:none;">
<div class="list2_top2">
    <ul>
        <li></li>
        <li>
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
        </select>
    </dl>
</div>
</td>
</tr>
<tr class="list2_box2" onclick="TestBlack('divc5');">
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
<td id="divc5" style="display:none;">
<div class="list2_top2">
    <ul>
        <li></li>
        <li>
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
        </select>
    </dl>
</div>
</td>
</tr>
<tr class="list2_box1" onclick="TestBlack('divc6');">
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
<td id="divc6" style="display:none;">
<div class="list2_top2">
    <ul>
        <li></li>
        <li>
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
        </select>
    </dl>
</div>
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
<div class="containers hides over">
<div class="list01_top over">
    <Span>质量度分析</Span>
    <a href="#" class="question"></a>
    <ul id="keywordQualityClass">
        <li class="current">
            <a href="javascript:loadKeywordQualityYesterdayData();">
                昨天
            </a>
        </li>
        <li>
            <a href="javascript:loadKeywordQuality7dayData();">
                近7天
            </a>
        </li>
        <li>
            <a href="javascript:loadKeywordQuality30dayData();">
                近30天
            </a>
        </li>
        <li class="date">
            <a href="javascript:loadKeywordQualityCustomData();">
                自定义
                <input name="reservation" class=" fa fa-calendar " type="image"
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
                    <input class="one" type="button" onclick="javascript:category = 'impression';sort = -1;"/>
                </p>

                <p>
                    <input class="two" type="button" onclick="javascript:category = 'impression';sort = 1;"/>
                </p></b>
            </li>
            <li>
                &nbsp;<span>点击</span><b>
                <p>
                    <input class="one" type="button" onclick="javascript:category = 'click';sort = -1;"/>
                </p>

                <p>
                    <input class="two" type="button" onclick="javascript:category = 'click';sort = 1;"/>
                </p></b>
            </li>
            <li>
                &nbsp;<span>点击率</span><b>
                <p>
                    <input class="one" type="button" onclick="javascript:category = 'ctr';sort = -1;"/>
                </p>

                <p>
                    <input class="two" type="button" onclick="javascript:category = 'ctr';sort = 1;"/>
                </p></b>
                <a href="#" class="question">
                </a>
            </li>
            <li>
                &nbsp;<span>消费</span><b>
                <p>
                    <input class="one" type="button" onclick="javascript:category = 'cost';sort = -1;"/>
                </p>

                <p>
                    <input class="two" type="button" onclick="javascript:category = 'cost';sort = 1;"/>
                </p></b>
                <a href="#" class="question">
                </a>
            </li>
            <li>
                &nbsp;<span>平均点击价格</span><b>
                <p>
                    <input class="one" type="button" onclick="javascript:category = 'cpc';sort = -1;"/>
                </p>

                <p>
                    <input class="two" type="button" onclick="javascript:category = 'cpc';sort = 1;"/>
                </p></b>
                <a href="#" class="question"></a>
            </li>
            <li>
                &nbsp;<span>转化</span><b>
                <p>
                    <input class="one" type="button" onclick="javascript:category = 'conversion';sort = -1;"/>
                </p>

                <p>
                    <input class="two" type="button" onclick="javascript:category = 'conversion';sort = 1;"/>
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
                <select onclick="reloadKeywordQuality();">
                    <option>10个</option>
                    <option>15个</option>
                    <option>20个</option>
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
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
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
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
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
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
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
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
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
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
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
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
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
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
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
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
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
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
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
            <span>关键词数</span>
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
            <option>10个</option>
            <option>9个</option>
            <option>8个</option>
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
            <a href="#">
                自定义
                <input type="image" src="${pageContext.request.contextPath}/public/img/date.png">
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
<tr class="list2_box1" onclick="TestBlack('divf');">
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
<tr id="divf" style="display:none;">
    <td>
        <div class="list2_top2">
            <ul>
                <li>
                    <span>关键词数</span>
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
                    <span>消费</span>
                </li>
                <li>
                    <span>平均点击价格</span>
                </li>
                <li>
                    <span>点击率</span>
                </li>
                <li>
                    <span>转化</span>
                </li>
                <li>
                    平均排名
                </li>

            </ul>
        </div>
        <div>
            <ul>
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
                <li></li>

            </ul>
        </div>


        <div class="page">
            <dl class="fr">
                每页显示
                <select>
                    <option>10个</option>
                    <option>9个</option>
                    <option>8个</option>
                </select>
            </dl>
        </div>
    </td>
</tr>
<tr class="list2_box2" onclick="TestBlack('divf1');">
    <td>
        <ul>
            <li>
                &nbsp;<span>搜索引擎优化2</span><span class="red_arrow wd3"></span>
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
</tr>
<tr id="divf1" style="display:none;">
    <td>
        <div class="list2_top2">
            <ul>
                <li>
                    <span>关键词数</span>
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
                    <span>消费</span>
                </li>
                <li>
                    <span>平均点击价格</span>
                </li>
                <li>
                    <span>点击率</span>
                </li>
                <li>
                    <span>转化</span>
                </li>
                <li>
                    平均排名
                </li>

            </ul>
        </div>
        <div>
            <ul>
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
                <li></li>

            </ul>
        </div>


        <div class="page">
            <dl class="fr">
                每页显示
                <select>
                    <option>10个</option>
                    <option>9个</option>
                    <option>8个</option>
                </select>
            </dl>
        </div>
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
<script type="text/javascript">

    //默认按照展现进行排名
    var category = "impression";

    //默认降序排列
    var sort = -1;

    var daterangepicker_start_date = null;

    var daterangepicker_end_date = null;

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
        $('input[name=reservation]').daterangepicker(null, function (start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
        });

        //默认加载昨天的数据
        loadKeywordQualityYesterdayData();

    });

    function TestBlack(TagName) {
        var obj = document.getElementById(TagName);
        if (obj.style.display == "") {
            obj.style.display = "none";
        } else {
            obj.style.display = "";
        }
    }

    var getCustomDate = function () {
        daterangepicker_start_date = $("input[name=daterangepicker_start]").val();
        daterangepicker_end_date = $("input[name=daterangepicker_end]").val();
    };

    var loadKeywordQualityYesterdayData = function () {
        $.ajax({
            url: "/keywordQuality/list",
            type: "GET",
            dataType: "json",
            data: {
                startDate: "2014-01-25",
                endDate: "2014-01-25",
                fieldName: category
            },
            success: function (data, textStatus, jqXHR) {
                if (data.rows.length > 0) {
                    $("#keywordQuality1").empty();
                    $.each(data.rows, function (i, item) {
                        var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                                "<li>" + item.impression + "</li><li>" + item.click + "</li><li>" + item.ctr + "</li><li>" + item.cost + "</li>" +
                                "<li>" + item.cpc + "</li><li>" + item.conversion + "</li></ul></div>";
                        $("#keywordQuality1").append(_div);
                    })
                }
            }
        });
    };

    var loadKeywordQuality7dayData = function () {
        //$("#keywordQualityClass").find("li").hasClass("current").removeClass("current");
        //$(this).parent("li").attr("'class", "current");
        $.ajax({
            url: "/keywordQuality/list",
            type: "GET",
            dataType: "json",
            data: {
                startDate: "2014-01-25",
                endDate: "2014-01-31",
                fieldName: category
            },
            success: function (data, textStatus, jqXHR) {
                if (data.rows.length > 0) {
                    $("#keywordQuality1").empty();
                    $.each(data.rows, function (i, item) {
                        var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                                "<li>" + item.impression + "</li><li>" + item.click + "</li><li>" + item.ctr + "</li><li>" + item.cost + "</li>" +
                                "<li>" + item.cpc + "</li><li>" + item.conversion + "</li></ul></div>";
                        $("#keywordQuality1").append(_div);
                    })
                }
            }
        });
    };

    var loadKeywordQuality30dayData = function () {
        $.ajax({
            url: "/keywordQuality/list",
            type: "GET",
            dataType: "json",
            data: {
                startDate: "2014-01-25",
                endDate: "2014-02-15",
                fieldName: category
            },
            success: function (data, textStatus, jqXHR) {
                if (data.rows.length > 0) {
                    $("#keywordQuality1").empty();
                    $.each(data.rows, function (i, item) {
                        var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                                "<li>" + item.impression + "</li><li>" + item.click + "</li><li>" + item.ctr + "</li><li>" + item.cost + "</li>" +
                                "<li>" + item.cpc + "</li><li>" + item.conversion + "</li></ul></div>";
                        $("#keywordQuality1").append(_div);
                    })
                }
            }
        });
    };

    var loadKeywordQualityCustomData = function () {
        $.ajax({
            url: "/keywordQuality/list",
            type: "GET",
            dataType: "json",
            data: {
                startDate: daterangepicker_start_date,
                endDate: daterangepicker_end_date,
                fieldName: category
            },
            success: function (data, textStatus, jqXHR) {
                if (data.rows.length > 0) {
                    $("#keywordQuality1").empty();
                    $.each(data.rows, function (i, item) {
                        var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                                "<li>" + item.impression + "</li><li>" + item.click + "</li><li>" + item.ctr + "</li><li>" + item.cost + "</li>" +
                                "<li>" + item.cpc + "</li><li>" + item.conversion + "</li></ul></div>";
                        $("#keywordQuality1").append(_div);
                    })
                }
            }
        });
    };

    var reloadKeywordQuality = function () {
        ;
    };
</script>

</body>
</html>