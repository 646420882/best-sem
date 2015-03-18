<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/11/19
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>网站概况</title>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="/public/css/count/count.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
    <script src="/public/js/census/websurvey.js"></script>
</head>
<body>
<div class="container-fluid">
<jsp:include page="../count/count_head.jsp"/>
<jsp:include page="../count/count_nav.jsp"/>
<div class="count_content clearfix">
<div class="row">
<div class="col-md-12">
<div class="well well-sm">
    <span style="font-weight: bold;">全部网站</span>&nbsp;
    <a href="javascript:void(0)" onclick="Test();"><span class="glyphicon glyphicon-question-sign"></span></a>&nbsp;
    <a href="javascript:void(0)"> 返回单站查看>></a>
</div>
<div class="alert alert-success" role="alert" style="display: none">
    <p>本报告助您分析：</p>
    <p>提供与账户对应的网站或子目录列表，可分别查看每个网站的详细报告。助您了解所拥有的网站或子目录整体情况。</p>
</div>
<div class="panel panel-default">
    <div class="panel panel-heading">
        <a href="javascrip:void(0)">暂无统计页面</a>&nbsp;&nbsp;|&nbsp;&nbsp;<span>暂无说明</span>
        <a href="javascript:void(0)" class="btn btn-default btn-xs" role="button"><span  class="glyphicon glyphicon-search"></span >&nbsp;查看报告</a>
               <span style="float: right;">排序方式：
                <select>
                    <option value="-1">请选择</option>
                    <option value="-1">请选择</option>
                    <option value="-1">请选择</option>
                </select>
            </span>
    </div>
    <div class="table-responsive">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>日期</th>
                <th>浏览量(PV)</th>
                <th>访客数(UV)</th>
                <th>IP数</th>
                <th>跳出率</th>
                <th>平均访问时长</th>
                <th>转化次数</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

<div class="panel panel-default" id="dir1">
    <div class="panel panel-heading">
        <a href="javascrip:void(0)">暂无统计页面</a>&nbsp;&nbsp;|&nbsp;&nbsp;<span>暂无说明</span>
        <a href="javascript:void(0)" class="btn btn-default btn-xs" role="button"><span  class="glyphicon glyphicon-search"></span >&nbsp;查看报告</a>
    </div>
    <div class="table-responsive">
        <table class="table table-hover table-condensed">
            <thead>
            <tr>
                <th>日期</th>
                <th>浏览量(PV)</th>
                <th>访客数(UV)</th>
                <th>IP数</th>
                <th>跳出率</th>
                <th>平均访问时长</th>
                <th>转化次数</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

<div class="panel panel-default" id="dir2">
    <div class="panel panel-heading">
        <a href="javascrip:void(0)">暂无统计页面</a>&nbsp;&nbsp;|&nbsp;&nbsp;<span>暂无说明</span>
        <a href="javascript:void(0)" class="btn btn-default btn-xs" role="button"><span  class="glyphicon glyphicon-search"></span >&nbsp;查看报告</a>
    </div>
    <div class="table-responsive">
        <table class="table table-hover table-condensed">
            <thead>
            <tr>
                <th>日期</th>
                <th>浏览量(PV)</th>
                <th>访客数(UV)</th>
                <th>IP数</th>
                <th>跳出率</th>
                <th>平均访问时长</th>
                <th>转化次数</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

<div class="panel panel-default" id="dir3">
    <div class="panel panel-heading">
        <a href="javascrip:void(0)">暂无统计页面</a>&nbsp;&nbsp;|&nbsp;&nbsp;<span>暂无说明</span>
        <a href="javascript:void(0)" class="btn btn-default btn-xs" role="button"><span  class="glyphicon glyphicon-search"></span >&nbsp;查看报告</a>
    </div>
    <div class="table-responsive">
        <table class="table table-hover table-condensed">
            <thead>
            <tr>
                <th>日期</th>
                <th>浏览量(PV)</th>
                <th>访客数(UV)</th>
                <th>IP数</th>
                <th>跳出率</th>
                <th>平均访问时长</th>
                <th>转化次数</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

</div>
</div>
</div>
</div>
</body>
</html>
