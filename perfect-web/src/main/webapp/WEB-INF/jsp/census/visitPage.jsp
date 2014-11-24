<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/11/19
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>受访页面</title>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" media="all" href="/public/css/daterangepicker-bs3.css"/>
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="/public/js/bootstrap-daterangepicker-moment.js"></script>
    <script src="/public/js/bootstrap-daterangepicker.js"></script>
    <script src="/public/js/census/allvariable.js"></script>
    <script src="/public/js/census/visit.js"></script>
    <style>
        * {
            font-size: 12px;
        }

        ul {
            list-style: none;
        }
        table .text {
            color: #999;
            line-height: 24px;
        }

        table .value {
            color: #333;
            font-size: 20px
        }
    </style>
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
        <div class="well well-sm">
            <span style="font-weight: bold;">受访页面 (2014/11/20)</span>&nbsp;
            <a href="javascript:void(0)" onclick="Test();"><span class="glyphicon glyphicon-question-sign"></span></a>

            <div class="alert alert-success" role="alert" style="display: none">
                <p>本报告助您分析：</p>

                <p>
                    访客对您网站各个页面的访问情况，重点从页面价值、入口页和退出页进行分析。页面价值分析：重点考量浏览量、贡献下游浏览量和平均停留时长；入口页分析：重点考量浏览量和入口页次数；退出页分析：重点考量浏览量、退出页次数和退出率；</p>
            </div>
        </div>
        <div class="alert alert-warning alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span
                    class="sr-only">Close</span></button>
            <strong>注意!</strong> 2012年11月1日起，受访页面新增访客数（UV）和IP数指标，11月1日前该指标数值为0。
        </div>
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
                                            });
                                        </script>
                                        <form class="navbar-form navbar-left" role="search" style="margin-top: 14px;">
                                            <div class="checkbox">
                                                <label>
                                                    <input type="checkbox"  onchange="dB(this.checked)" name="db"> 与其他时间对比
                                                </label>
                                            </div>
                                        </form>
                                </ul>

                                <ul class="nav navbar-nav navbar-right">
                                    <li>
                                        <button type="button" class="btn btn-default navbar-btn" id="scroll"
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
                    <ul class="nav nav-tabs" role="tablist"  id="tab1">
                        <li role="presentation" class="active"><a href="#">指标概览</a></li>
                        <li role="presentation"><a href="#">页面价值分析</a></li>
                        <li role="presentation"><a href="#">入口页分析</a></li>
                        <li role="presentation"><a href="#">退出页分析</a></li>
                    </ul>
                </div>
                <table class="table table-bordered">
                    <thead>
                    <tr class="danger">
                        <td style="padding: 8px 30px;">
                            <span class="text"> 浏览量(PV)<a href="javascript:void(0)"> </a></span>
                            <br>
                            <span class="value" title="1,059">1,059</span>
                        </td>
                        <td style="padding: 8px 30px;">
                            <span class="text">入口页次数<a href="javascript:void(0)"> </a></span>
                            <br>
                            <span class="value" title="1,059">1,722</span></td>
                        <td style="padding: 8px 30px;">
                            <span class="text"> 退出页次数<a href="javascript:void(0)"> </a></span>
                            <br>
                            <span class="value" title="1,059">1,631</span></td>
                        <td style="padding: 8px 30px;">
                            <span class="text">平均停留时长 <a href="javascript:void(0)"> </a></span>
                            <br>
                            <span class="value" title="1,059">00:02:06</span></td>
                        <td style="padding: 8px 30px;">
                            <span class="text">退出率  <a href="javascript:void(0)"> </a></span>
                            <br>
                            <span class="value" title="1,059">82.25%</span></td>
                    </tr>
                    </thead>
                </table>
            </div>

            <div class="panel panel-default">
                <div class="panel-body">
                    <ul class="nav nav-tabs" role="tablist" id="tab2">
                        <li role="presentation" class="active"><a href="#" onclick="Ts();"
                                                                  style="cursor: pointer;">高级选项</a></li>
                    </ul>
                    <div id="filter"
                         style="background-color: #f9f9f9;line-height: 30px; padding: 10px 15px; display: none;">
                        <ul>
                            <li>
                                <div>
                                    <span>
                                     <label>来源过滤</label>
                                     <select style="margin-left: 20px;">
                                         <option value="all">全部</option>
                                         <option value="through">直接访问</option>
                                         <option value="search">搜索引擎</option>
                                         <option value="link">外部链接</option>
                                     </select>
                                 </span>
                                    <span style="margin-left: 10px;">
                                        <a href="javascript:void(0)">百度</a>
                                        <a href="javascript:void(0)">Google</a>
                                    </span>
                                </div>
                            </li>
                            <li>
                                <div>
                                    <div>
                                        <label>访客过滤</label>
                                        <label style="margin-left: 20px;">
                                            <input type="radio" name="tangram-checkGroup--TANGRAM__8-Visitors"
                                                   checked="checked" title="全部" value="all">
                                            全部
                                        </label>
                                        <label>
                                            <input type="radio" name="tangram-checkGroup--TANGRAM__8-Visitors"
                                                   title="新访客" value="new">
                                            新访客
                                        </label>
                                        <label>
                                            <input type="radio" name="tangram-checkGroup--TANGRAM__8-Visitors"
                                                   title="老访客" value="old">
                                            老访客
                                        </label>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <table class="table table-striped">
                    <thead>
                    <tr class="active">
                        <td>页面Url</td>
                        <td>&nbsp;</td>
                        <td>浏览量(PV)</td>
                        <td>访客数(UV)</td>
                        <td>退出页次数<span class="glyphicon glyphicon-arrow-down"></span></td>
                        <td>平均停留时长</td>
                        <td>退出率</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><a href='javascript:void(0)'>http://editor.com.perfect.api.baidu.com</a></td>
                        <td><span class="glyphicon glyphicon-random"></span></td>
                        <td>1,487</td>
                        <td>1,229</td>
                        <td>1,288</td>
                        <td>00:02:11</td>
                        <td>86.62%</td>
                    </tr>
                    <tr>
                        <td><a href='javascript:void(0)'>http://editor.com.perfect.api.baidu.com</a></td>
                        <td><span class="glyphicon glyphicon-random"></span></td>
                        <td>1,487</td>
                        <td>1,229</td>
                        <td>1,288</td>
                        <td>00:02:11</td>
                        <td>86.62%</td>
                    </tr>
                    <tr>
                        <td><a href='javascript:void(0)'>http://editor.com.perfect.api.baidu.com</a></td>
                        <td><span class="glyphicon glyphicon-random"></span></td>
                        <td>1,487</td>
                        <td>1,229</td>
                        <td>1,288</td>
                        <td>00:02:11</td>
                        <td>86.62%</td>
                    </tr>
                    <tr>
                        <td><a href='javascript:void(0)'>http://editor.com.perfect.api.baidu.com</a></td>
                        <td><span class="glyphicon glyphicon-random"></span></td>
                        <td>1,487</td>
                        <td>1,229</td>
                        <td>1,288</td>
                        <td>00:02:11</td>
                        <td>86.62%</td>
                    </tr>
                    <tr>
                        <td><a href='javascript:void(0)'>http://editor.com.perfect.api.baidu.com</a></td>
                        <td><span class="glyphicon glyphicon-random"></span></td>
                        <td>1,487</td>
                        <td>1,229</td>
                        <td>1,288</td>
                        <td>00:02:11</td>
                        <td>86.62%</td>
                    </tr>
                    <tr>
                        <td><a href='javascript:void(0)'>http://editor.com.perfect.api.baidu.com</a></td>
                        <td><span class="glyphicon glyphicon-random"></span></td>
                        <td>1,487</td>
                        <td>1,229</td>
                        <td>1,288</td>
                        <td>00:02:11</td>
                        <td>86.62%</td>
                    </tr>
                    </tr>
                    <tr>
                        <td><a href='javascript:void(0)'>http://editor.com.perfect.api.baidu.com</a></td>
                        <td><span class="glyphicon glyphicon-random"></span></td>
                        <td>1,487</td>
                        <td>1,229</td>
                        <td>1,288</td>
                        <td>00:02:11</td>
                        <td>86.62%</td>
                    </tr>
                    </tr>
                    <tr>
                        <td><a href='javascript:void(0)'>http://editor.com.perfect.api.baidu.com</a></td>
                        <td><span class="glyphicon glyphicon-random"></span></td>
                        <td>1,487</td>
                        <td>1,229</td>
                        <td>1,288</td>
                        <td>00:02:11</td>
                        <td>86.62%</td>
                    </tr>
                    </tr>
                    <tr>
                        <td><a href='javascript:void(0)'>http://editor.com.perfect.api.baidu.com</a></td>
                        <td><span class="glyphicon glyphicon-random"></span></td>
                        <td>1,487</td>
                        <td>1,229</td>
                        <td>1,288</td>
                        <td>00:02:11</td>
                        <td>86.62%</td>
                    </tr>
                    </tr>
                    <tr>
                        <td><a href='javascript:void(0)'>http://editor.com.perfect.api.baidu.com</a></td>
                        <td><span class="glyphicon glyphicon-random"></span></td>
                        <td>1,487</td>
                        <td>1,229</td>
                        <td>1,288</td>
                        <td>00:02:11</td>
                        <td>86.62%</td>
                    </tr>
                    </tbody>
                    <tfoot>
                    <tr class="active">
                        <td >当页汇总</td>
                        <td >&nbsp;</td>
                        <td ><span style="font-weight: bold">1,983</span></td>
                        <td ><span style="font-weight: bold">1,722</span></td>
                        <td ><span style="font-weight: bold">1,631</span></td>
                        <td ><span style="font-weight: bold">00:02:06</span></td>
                        <td><span style="font-weight: bold">82.25%</span></td>
                    </tr>
                    </tfoot>
                </table>
            </div>

        </div>
    </div>
</div>

</body>
</html>
