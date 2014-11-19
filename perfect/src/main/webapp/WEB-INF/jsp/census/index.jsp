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
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="/public/js/census/allvariable.js"></script>
    <script src="/public/js/census/websurvey.js"></script>
</head>
<body>
<div class="container-fluid">

<div class="row">
    <div class="col-md-2"></div>
    <div class="col-md-10"><h1>Perfect 统计</h1></div>
</div>
<div class="row">
<div class="col-md-2">
    <jsp:include page="left/left-nav.jsp"/>
</div>
<div class="col-md-10">

<div class="panel panel-default">
    <div class="panel panel-heading">
        <a href="toggle">-</a>&nbsp;网站概况(<a href="javascript:void(0)" onclick="Test()">?</a>)
        <a href="http://edit.baidu.com">edit.baidu.com</a>
        <a href="javascript:void(0)" class="btn btn-default btn-xs" role="button"><span  class="glyphicon glyphicon-search"></span >&nbsp;查看报告</a>

        <div id="alert" class="alert alert-danger" style="display: none;">
            <h4>本报告助您分析:</h4>

            <p>提供与账户对应的网站或子目录列表，可分别查看每个网站的详细报告。助您了解所拥有的网站或子目录整体情况.</p>
        </div>
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
            <tr>
                <td>今日:</td>
                <td>1276</td>
                <td>865</td>
                <td>812</td>
                <td>92.4%</td>
                <td>00:02:41</td>
                <td>1</td>
            <tr>
            <tr>
                <td>昨日:</td>
                <td>1276</td>
                <td>865</td>
                <td>812</td>
                <td>92.4%</td>
                <td>00:02:41</td>
                <td>1</td>
            <tr>
            <tr>
                <td>预计今日:</td>
                <td>1276<span class="glyphicon glyphicon-arrow-up" style="color: red;"></span></td>
                <td>865<span class="glyphicon glyphicon-arrow-up" style="color: red;"></span></td>
                <td>812<span class="glyphicon glyphicon-arrow-down" style="color: green;"></span></td>
                <td>92.4%</td>
                <td>00:02:41</td>
                <td>1</td>
            <tr>
            </tbody>
        </table>
    </div>
</div>

<div class="panel panel-default" id="dir1">
    <div class="panel panel-heading">
        edit.baidu.com&nbsp;&nbsp;功能特点页&nbsp;
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
            <tr>
                <td>今日:</td>
                <td>1276</td>
                <td>865</td>
                <td>812</td>
                <td>92.4%</td>
                <td>00:02:41</td>
                <td>1</td>
            <tr>
            <tr>
                <td>昨日:</td>
                <td>1276</td>
                <td>865</td>
                <td>812</td>
                <td>92.4%</td>
                <td>00:02:41</td>
                <td>1</td>
            <tr>
            <tr>
                <td>预计今日:</td>
                <td>1276</td>
                <td>865</td>
                <td>812</td>
                <td>92.4%</td>
                <td>00:02:41</td>
                <td>1</td>
            <tr>
            </tbody>
        </table>
    </div>
</div>

<div class="panel panel-default" id="dir2">
    <div class="panel panel-heading">
        edit.baidu.com&nbsp;&nbsp;1&nbsp;
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
            <tr>
                <td>今日:</td>
                <td>1276</td>
                <td>865</td>
                <td>812</td>
                <td>92.4%</td>
                <td>00:02:41</td>
                <td>1</td>
            <tr>
            <tr>
                <td>昨日:</td>
                <td>1276</td>
                <td>865</td>
                <td>812</td>
                <td>92.4%</td>
                <td>00:02:41</td>
                <td>1</td>
            <tr>
            <tr>
                <td>预计今日:</td>
                <td>1276</td>
                <td>865</td>
                <td>812</td>
                <td>92.4%</td>
                <td>00:02:41</td>
                <td>1</td>
            <tr>
            </tbody>
        </table>
    </div>
</div>

<div class="panel panel-default" id="dir3">
    <div class="panel panel-heading">
        edit.baidu.com&nbsp;&nbsp;搜索推广功能介绍页&nbsp;
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
            <tr>
                <td>今日:</td>
                <td>1276</td>
                <td>865</td>
                <td>812</td>
                <td>92.4%</td>
                <td>00:02:41</td>
                <td>1</td>
            <tr>
            <tr>
                <td>昨日:</td>
                <td>1276</td>
                <td>865</td>
                <td>812</td>
                <td>92.4%</td>
                <td>00:02:41</td>
                <td>1</td>
            <tr>
            <tr>
                <td>预计今日:</td>
                <td>1276</td>
                <td>865</td>
                <td>812</td>
                <td>92.4%</td>
                <td>00:02:41</td>
                <td>1</td>
            <tr>
            </tbody>
        </table>
    </div>
</div>

</div>
</div>
</div>
</body>
</html>
