<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/11/24
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>页面点击图</title>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="/public/css/count/count.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="/public/js/census/publicController.js"></script>
    <style>
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
                <span style="font-weight: bold;">页面点击图</span>&nbsp;
                <a href="javascript:void(0)" onclick="Test();"><span
                        class="glyphicon glyphicon-question-sign"></span></a>
                <a style="float: right;" id="DownloadReport" href="javascript:void(0);"><span
                        class="glyphicon glyphicon-download-alt"></span>下载</a>
            </div>
            <div class="alert alert-success" role="alert" style="display: none">
                <p>本报告助您分析：</p>

                <p>
                    统计访客在您网页的鼠标点击情况，并通过不同颜色的区域展示出来。助您了解访客在网站上的关注点，并根据点击热度进行优化网页设计（只支持http和https协议的url，暂不支持框架页面的监控）。</p>
            </div>
            <div class="alert alert-warning alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <strong>注意!</strong> 2012年11月1日起，热力图和链接点击图提供最近30天查询功能，数据从11月1日开始计算。
            </div>

            <div class="panel panel-default">
                <div class="table-filter">
                    <form class="form-inline" role="form" style="float: left;">
                        <div class="form-group">
                            <input type="text" id="table-filter-url" class="form-control" placeholder="热力图监控页面...">
                        </div>
                        <button type="submit" class="btn btn-default btn-sm">查询</button>
                    </form>
                </div>
                <div class="panel panel-body">
                    本功能将同时搜索“手动设置”与“自动设置”两部分，通过关键字段模糊匹配对应的页面点击监控页面。
                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation" class="active"><a href="#"  style="cursor: pointer">手动设置</a></li>
                    </ul>
                </div>
                <table class="table table-striped">
                    <thead>
                    <tr class="active">
                        <td>点击图页面</td>
                        <td>想统计的页面</td>
                        <td>统计范围</td>
                        <td>状态</td>
                        <td>创建时间&nbsp;<span class="glyphicon glyphicon-arrow-down"></span></td>
                        <td>&nbsp;</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>主页</td>
                        <td><a href="http://editor.baidu.com/editor/news.html">http://editor.baidu.com/editor/news.html</a></td>
                        <td>http://editor.baidu.com/editor/news.html</td>
                        <td>统计中</td>
                        <td>2012-10-19</td>
                        <td>
                            <a href="javascript:void(0)">查看热力图</a>&nbsp;|&nbsp;
                            <a href="javascript:void(0)">查看链接点击图</a>
                        </td>
                    </tr>
                    <tr>
                        <td>功能特点页</td>
                        <td><a href="http://editor.baidu.com/function.html">http://editor.baidu.com/function.html</a></td>
                        <td>http://editor.baidu.com/function.html*</td>
                        <td>统计中</td>
                        <td>2012-12-19</td>
                        <td>
                            <a href="javascript:void(0)">查看热力图</a>&nbsp;|&nbsp;
                            <a href="javascript:void(0)">查看链接点击图</a>
                        </td>
                    </tr>
                    <tr>
                        <td>首页点击图</td>
                        <td><a href="http://editor.baidu.com/">http://editor.baidu.com/</a></td>
                        <td>http://editor.baidu.com/</td>
                        <td>统计中</td>
                        <td>2011-02-19</td>
                        <td>
                            <a href="javascript:void(0)">查看热力图</a>&nbsp;|&nbsp;
                            <a href="javascript:void(0)">查看链接点击图</a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div style="color: gray; line-height: 2;padding-top: 20px;">
                小贴士：
                <br>
                注意：目前仅支持在 <a href="http://www.mozillaonline.com/" target="_blank">Firefox</a>,
                <a href="http://www.google.com/chrome" target="_blank">Chrome</a>,
                <a href="http://www.microsoft.com/china/windows/internet-explorer/" target="_blank">IE8</a> 下查看点击图，推荐使用Firefox。
            </div>

        </div>
    </div>
    </div>
</body>
</html>
