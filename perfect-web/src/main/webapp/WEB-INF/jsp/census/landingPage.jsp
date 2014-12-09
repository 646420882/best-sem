<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/11/21
  Time: 11:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>入口页面</title>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" media="all" href="/public/css/daterangepicker-bs3.css"/>
    <link rel="stylesheet" type="text/css" href="/public/css/count/count.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="/public/js/bootstrap-daterangepicker-moment.js"></script>
    <script src="/public/js/bootstrap-daterangepicker.js"></script>
    <script src="/public/js/census/publicController.js"></script>
    <style>
        table .text {
            color: #999;
            line-height: 24px;
        }

        table .value {
            color: #333;
            font-size: 20px
        }

        table .parent {
            cursor: pointer;
        }

        .group label {
            float: left;
            width: 140px;
        }

        div .fold {
            border-top: 2px solid #e5e5e5;
            margin-top: 2px;
            text-align: center;
        }

        .table-filter {
            background-color: #ebebeb;
            height: 34px;
            padding-bottom: 1px;
            padding-left: 10px;
        }
    </style>
</head>
<body>
<div class="container-fluid">
<jsp:include page="../count/count_head.jsp"/>
<jsp:include page="../count/count_nav.jsp"/>
<div class="count_content clearfix">
<div class="row">

<div class="col-md-12">

<div class="well well-sm">
    <span style="font-weight: bold;">入口页面 (2014/11/20)</span>&nbsp;
    <a href="javascript:void(0)" onclick="Test();"><span
            class="glyphicon glyphicon-question-sign"></span></a>
    <a style="float: right;" id="DownloadReport" href="javascript:void(0);"><span
            class="glyphicon glyphicon-download-alt"></span>下载</a>
</div>
<div class="alert alert-success" role="alert" style="display: none">
    <p>本报告助您分析：</p>

    <p>
        即为访客访问网站的第一个入口，即每次访问的第一个受访页面。重点从流量、新访客、吸引力和转化四个维度进行分析。其中流量分析：重点考量浏览量、访客数和IP数；新访客分析：重点考量新访客数和比率；吸引力分析：重点考量浏览量、跳出率、平均访问时长和平均访问页数；转化分析：重点考量转化次数和转化率；</p>
</div>
<div class="alert alert-warning alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span
            class="sr-only">Close</span></button>
    <strong>注意!</strong> 2012年11月1日起，新增查看来源分布功能，请从该日起查询数据。
</div>
<!--end <div class="alert alert-warning alert-dismissible" role="alert">-->

<div class="panel panel-default">
<div class="panel panel-heading" style="height: 75px;" id="panel1">
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav" id="nav1">
                    <li class="active"><a href="#">今天</a></li>
                    <li><a href="#">昨天</a></li>
                    <li><a href="#">最近7天</a></li>
                    <li><a href="#">近30天</a></li>
                    <form class="navbar-form navbar-left" role="search">
                        <div class="input-prepend input-group">
                                                        <span class="add-on input-group-addon"><i
                                                                class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
                            <input type="text" readonly="readonly" style="width: 200px"
                                   name="reservation" id="reservation" class="form-control"/>
                        </div>
                    </form>
                    <script type="text/javascript">
                        $(document).ready(function () {
                            $('#reservation').daterangepicker(null, function (start, end, label) {
                                console.log(start.toISOString(), end.toISOString(), label);
                            });
                            $("input[name='db']").daterangepicker(null, function (start, end, label) {
                                console.log(start.toISOString(), end.toISOString(), label);
                            });
                        });
                    </script>
                    <form class="navbar-form navbar-left" role="search" style="margin-top: 14px;">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" onchange="dB(this.checked)" name="db"> 与其他时间对比
                            </label>
                        </div>
                    </form>
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <button type="button" class="btn btn-default navbar-btn" style="margin:12px 10px 0 0;" id="scroll"
                                data-toggle="tooltip" data-placement="bottom" title="设置随页滚动">
                            <span class="glyphicon glyphicon-pushpin"></span></button>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
</div>
<div class="panel-body" style="margin-top: -25px;">
    <ul class="nav nav-tabs" role="tablist" id="tab1">
        <li role="presentation" class="active" style="cursor: pointer"><a href="#">指标概览</a></li>
        <li role="presentation"><a href="#" style="cursor: pointer">流量分析</a></li>
        <li role="presentation"><a href="#" style="cursor: pointer">新访客分析</a></li>
        <li role="presentation"><a href="#" style="cursor: pointer">吸引力分析</a></li>
        <li role="presentation"><a href="#" style="cursor: pointer">转化分析</a></li>
    </ul>
</div>
<table class="table table-bordered">
    <thead>
    <tr class="danger">
        <td style="padding: 8px 30px;">
            <span class="text"> 访客数(UV)<a href="javascript:void(0)"> </a></span>
            <br>
            <span class="value" title="953">953</span>
        </td>
        <td style="padding: 8px 30px;">
            <span class="text">新访客数<a href="javascript:void(0)"> </a></span>
            <br>
            <span class="value" title="668">668</span>
        </td>
        <td style="padding: 8px 30px;">
            <span class="text"> 新访客比例<a href="javascript:void(0)"> </a></span>
            <br>
            <span class="value" title="70.09%">70.09%</span>
        </td>
        <td style="padding: 8px 30px;">
            <span class="text">平均访问时长 <a href="javascript:void(0)"> </a></span>
            <br>
            <span class="value" title="00:02:48">00:02:48</span>
        </td>
        <td style="padding: 8px 30px;">
            <span class="text">平均访问页数<a href="javascript:void(0)"> </a></span>
            <br>
            <span class="value" title="1.16">1.16</span>
        </td>
        <td style="padding: 8px 30px;">
            <span class="text">转化次数<a href="javascript:void(0)"> </a></span>
            <br>
            <span class="value" title="3">3</span>
        </td>
    </tr>
    </thead>
</table>
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script type="text/javascript">
    require.config({
        paths: {
            echarts: 'http://echarts.baidu.com/build/dist'
        }
    });
    require(
            [
                'echarts',
                'echarts/chart/bar',
                'echarts/chart/pie',
                'echarts/chart/line'
                // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                var myChart0 = ec.init(document.getElementById("chart0"));
                var myChart1 = ec.init(document.getElementById("chart1"));
                var option1 = {
                    title: {
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
//                    legend: {
//                        orient: 'vertical',
//                        x: 'left',
//                        data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
//                    },
                    calculable: true,
                    series: [
                        {
                            name: '访问来源',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: [
                                {value: 335, name: '直接访问'},
                                {value: 310, name: '邮件营销'},
                                {value: 234, name: '联盟广告'},
                                {value: 135, name: '视频广告'},
                                {value: 1548, name: '搜索引擎'}
                            ]
                        }
                    ]
                };
                var option2 = {
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['直接访问']
                    },
                    calculable: true,

                    xAxis: [
                        {
                            type: 'category',
                            boundaryGap: false,
                            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
                            axisLabel: {
                                interval: 'auto',    // {number}
                                rotate: 20
                            }
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '直接访问',
                            type: 'line',
                            stack: '总量',
                            data: [320, 332, 301, 334, 390, 330, 320]
                        }

                    ]
                };
                myChart0.setOption(option1);
                myChart1.setOption(option2);
            }
    );
</script>
<div class="row" style="margin-top: 15px;" id="chart_div">
    <div class="col-md-4" id="chart0" style="height:300px;"></div>
    <div class="col-md-8" id="chart1" style="height:300px;"></div>
</div>
<div id="filter-speak" style="display: none;"><span style="color: #606060;padding: 5px;">趋势图</span></div>
<div class="fold">
    <a onclick="chartToggle(this);" href="javascript:void(0);"> <span class="glyphicon glyphicon-chevron-up"></span></a>
    <a onclick="chartToggle(this);" href="javascript:void(0);" style="display: none;"> <span
            class="glyphicon glyphicon-chevron-down"></span></a>
</div>
<div class="table-filter">

    <form class="form-inline" role="form" style="float: left;">
        <div class="form-group">
            <input type="text" id="table-filter-url" class="form-control" placeholder="输入Url">
        </div>
        <button type="submit" class="btn btn-default btn-sm">查询</button>
    </form>

    <form class="form-inline" role="form" style="float: right;">
        <div class="form-group">
            <p class="form-control-static">转化目标:</p>
        </div>
        <select class="form-control">
            <option value="-1">全部页面转化</option>
        </select>
    </form>
</div>
<table class="table table-striped">
    <thead>
    <tr class="active">
        <td>页面Url</td>
        <td>&nbsp;</td>
        <td>访问次数</td>
        <td>贡献浏览量</td>
        <td>转化次数<span class="glyphicon glyphicon-arrow-down"></span></td>
        <td>转化率</td>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><a href="http://editor.baidu.com/editor/news.html" title="http://editor.baidu.com/editor/news.html">http://editor.baidu.com/editor/news.html</a></td>
        <td> <a href="javascript:void(0)"><span class="glyphicon glyphicon-th-list"></span></a></td>
        <td>24</td>
        <td>25</td>
        <td>2</td>
        <td>8.33%</td>
    </tr>
    <tr>
        <td><a href="http://editor.baidu.com/?qq-pf-to=//10.42.7.217/?1416571156.24" title="http://editor.baidu.com/?qq-pf-to=//10.42.7.217/?1416571156.24">http://editor.baidu.com/?qq-pf-to=//10.42.7.217/?1416571156.24</a></td>
        <td> <a href="javascript:void(0)"><span class="glyphicon glyphicon-th-list"></span></a></td>
        <td>1</td>
        <td>1</td>
        <td>--</td>
        <td>--</td>
    </tr>
    <tr>
        <td><a href="http://editor.baidu.com/?qq-pf-to=javascript&" title="http://editor.baidu.com/?qq-pf-to=javascript&">http://editor.baidu.com/?qq-pf-to=javascript&</a></td>
        <td> <a href="javascript:void(0)"><span class="glyphicon glyphicon-th-list"></span></a></td>
        <td>1</td>
        <td>1</td>
        <td>--</td>
        <td>--</td>
    </tr>
    <tr>
        <td><a href="http://editor.baidu.com/?qq-pf-to=javascript&" title="http://editor.baidu.com/?qq-pf-to=javascript&">http://editor.baidu.com/account.html</a></td>
        <td> <a href="javascript:void(0)"><span class="glyphicon glyphicon-th-list"></span></a></td>
        <td>1</td>
        <td>1</td>
        <td>--</td>
        <td>--</td>
    </tr>
    <tr>
        <td><a href="http://editor.baidu.com/?qq-pf-to=javascript&" title="http://editor.baidu.com/?qq-pf-to=javascript&">http://editor.baidu.com/search.html</a></td>
        <td> <a href="javascript:void(0)"><span class="glyphicon glyphicon-th-list"></span></a></td>
        <td>1</td>
        <td>1</td>
        <td>--</td>
        <td>--</td>
    </tr>
    <tr>
        <td><a href="http://editor.baidu.com/?qq-pf-to=javascript&" title="http://editor.baidu.com/?qq-pf-to=javascript&">http://editor.baidu.com/function.html</a></td>
        <td> <a href="javascript:void(0)"><span class="glyphicon glyphicon-th-list"></span></a></td>
        <td>29</td>
        <td>38</td>
        <td>--</td>
        <td>--</td>
    </tr>
    <tr>
        <td><a href="http://editor.baidu.com/?qq-pf-to=javascript&" title="http://editor.baidu.com/?qq-pf-to=javascript&">http://editor.baidu.net.vn/account.html</a></td>
        <td> <a href="javascript:void(0)"><span class="glyphicon glyphicon-th-list"></span></a></td>
        <td>1</td>
        <td>1</td>
        <td>--</td>
        <td>--</td>
    </tr>
    </tbody>
    <tfoot>
    <tr>
        <td>当前汇总:</td>
        <td >&nbsp;</td>
        <td ><span style="font-weight: bold">56</span></td>
        <td ><span style="font-weight: bold">68</span></td>
        <td ><span style="font-weight: bold">2</span></td>
        <td ><span style="font-weight: bold">8.36%</span></td>
    </tr>
    </tfoot>

</table>
<div class="table-filter">
    <form class="form-inline" role="form" style="float: left;">
    <div class="form-group">
        <p class="form-control-static">每页显示:</p>
    </div>
    <select class="form-control">
        <option value="-1">20</option>
    </select>
</form>

    <form class="form-inline" role="form" style="float: right;">
        <nav>
            <ul class="pagination" style="margin: 2px;">
                <li><a href="#">&laquo;</a></li>
                <li><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><a href="#">&raquo;</a></li>
            </ul>
        </nav>
        <%--<div class="form-group">--%>
            <%--<p class="form-control-static">第</p>--%>
            <%--<input type="text" id="table-footer-pager" class="form-control" placeholder="页">--%>
        <%--</div>--%>
        <%--<button type="submit" class="btn btn-default btn-sm">确定</button>--%>
    </form>
</div>
</div>
<!--end    <div class="panel panel-default">-->

</div>
<!--end  <div class="col-md-10">-->
</div>
</div>
</div>
</body>
</html>
