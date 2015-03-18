<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/11/20
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>受访域名</title>
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
    <span style="font-weight: bold;">受访域名 (2014/11/20)</span>&nbsp;
    <a href="javascript:void(0)" onclick="Test();"><span class="glyphicon glyphicon-question-sign"></span></a>
    <a style="float: right;" id="DownloadReport" href="javascript:void(0);"><span class="glyphicon glyphicon-download-alt"></span>下载</a>
</div>
<div class="alert alert-success" role="alert" style="display: none">
    <p>本报告助您分析：</p>
    <p>访客对您的网站的各个域名的访问情况；系统自动将具有相关规则的URL聚合在一起，对这一类页面的情况进行分析。</p>
</div>
<div class="alert alert-warning alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span
            class="sr-only">Close</span></button>
    <strong>注意!</strong> 2012年11月1日起，新增受访域名报告，请从该日起查询数据。
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
            <li role="presentation"><a href="#" style="cursor: pointer">页面价值分析</a></li>
            <li role="presentation"><a href="#" style="cursor: pointer">入口页分析</a></li>
            <li role="presentation"><a href="#" style="cursor: pointer">退出页分析</a></li>
        </ul>
    </div>
    <table class="table table-bordered">
        <thead>
        <tr class="danger">
            <td style="padding: 8px 30px;">
                <span class="text"> 浏览量(PV)<a href="javascript:void(0)"> </a></span>
                <br>
                <span class="value" title="1,059">1,821</span>
            </td>
            <td style="padding: 8px 30px;">
                <span class="text">访客数(UV)<a href="javascript:void(0)"> </a></span>
                <br>
                <span class="value" title="1,059">427</span></td>
            <td style="padding: 8px 30px;">
                <span class="text"> IP数<a href="javascript:void(0)"> </a></span>
                <br>
                <span class="value" title="1,059">425</span></td>
            <td style="padding: 8px 30px;">
                <span class="text">跳出率 <a href="javascript:void(0)"> </a></span>
                <br>
                <span class="value" title="1,059">92.86%</span></td>
            <td style="padding: 8px 30px;">
                <span class="text">平均停留时长  <a href="javascript:void(0)"> </a></span>
                <br>
                <span class="value" title="1,059">00:02:16</span></td>
        </tr>
        </thead>
    </table>
</div>
<!--end <div class="panel panel-default">-->

<div class="panel panel-default">
    <div class="panel-body">
        <ul class="nav nav-tabs" role="tablist" id="tab2">
            <li role="presentation" class="active">
                <a href="#" onclick="Ts();" style="cursor: pointer;"><span class="glyphicon glyphicon-star"></span>&nbsp;自定义选项</a>
            </li>
            <li role="presentation"><a href="#" onclick="Ts();" style="cursor: pointer;">高级选项</a></li>
        </ul>
        <div id="filter"
             style="background-color: #f9f9f9;line-height: 30px; padding: 10px 15px; display: none;margin-top: 8px;">
            <ul>
                <li style="display: none;">
                    <div>
                        <em style="color: gray;float: right">
                            提示：可同时选择
                            <em style="color: red;">6</em>
                            项
                        </em>
                        <a href="javascript:void(0)">系统默认指标</a>
                    </div>
                    <div class="group">
                        <label style="float: left; width: 140px;">网站基础指标:</label>

                        <div style="overflow: hidden;">
                            <label> <input type="checkbox" value="pv_count" title="浏览量(PV)"> 浏览量(PV)</label>
                            <label> <input type="checkbox" value="pv_ratio" title="浏览量占比"> 浏览量占比</label>
                            <label> <input type="checkbox" value="visit_count" title="访问次数"> 访问次数</label>
                            <label> <input type="checkbox" value="visitor_count" title="访客数(UV)"> 访客数(UV)</label>
                            <label> <input type="checkbox" value="new_visitor_count" title="新访客数"> 新访客数</label>
                            <label> <input type="checkbox" value="new_visitor_ratio" title="新访客比率"> 新访客比率</label>
                            <label> <input type="checkbox" value="ip_count" title="IP数"> IP数</label>
                        </div>
                    </div>
                    <div style="border-bottom:1px dashed #ccc;"></div>
                    <div class="group">
                        <label style="float: left; width: 140px;">流量质量指标:</label>

                        <div style="overflow: hidden;">
                            <label> <input type="checkbox" value="session_bounce_ratio" title="跳出率"> 跳出率</label>
                            <label> <input type="checkbox" value="average_stay_time" title="平均停留时长"> 平均停留时长</label>
                            <label> <input type="checkbox" value="avg_visit_pages" title="平均访问页数"> 平均访问页数</label>
                        </div>
                    </div>
                    <div>
                        <input type="button" class="btn btn-default btn-xs" value="确定"/>
                    </div>
                </li>
                <li style="display: none;">
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
            <td>&nbsp;</td>
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
        <tr class="parent" id="row_0">
            <td width="5"><a href="javascript:void(0)">+</a></td>
            <td><a href='javascript:void(0)'>http://editor.baidu.com</a></td>
            <td><span class="glyphicon glyphicon-signal"></span></td>
            <td>1,487</td>
            <td>1,229</td>
            <td>1,288</td>
            <td>00:02:11</td>
            <td>86.62%</td>
        </tr>
        <tr class="child_row_0">
            <td width="5">&nbsp;</td>
            <td><a href='javascript:void(0)'>http://editor.baidu.com</a></td>
            <td><span class="glyphicon glyphicon-signal"></span></td>
            <td>1,487</td>
            <td>1,229</td>
            <td>1,288</td>
            <td>00:02:11</td>
            <td>86.62%</td>
        </tr>
        <tr class="child_row_0">
            <td width="5">&nbsp;</td>
            <td><a href='javascript:void(0)'>http://editor.baidu.com</a></td>
            <td><span class="glyphicon glyphicon-signal"></span></td>
            <td>1,487</td>
            <td>1,229</td>
            <td>1,288</td>
            <td>00:02:11</td>
            <td>86.62%</td>
        </tr>
        <tr class="parent" id="row_1">
            <td><a href="javascript:void(0)">+</a></td>
            <td><a href='javascript:void(0)'>http://editor.baidu.com</a></td>
            <td><span class="glyphicon glyphicon-signal"></span></td>
            <td>1,487</td>
            <td>1,229</td>
            <td>1,288</td>
            <td>00:02:11</td>
            <td>86.62%</td>
        </tr>
        <tr class="child_row_1">
            <td>&nbsp;</td>
            <td><a href='javascript:void(0)'>http://editor.baidu.com</a></td>
            <td><span class="glyphicon glyphicon-signal"></span></td>
            <td>1,487</td>
            <td>1,229</td>
            <td>1,288</td>
            <td>00:02:11</td>
            <td>86.62%</td>
        </tr>
        <tr class="child_row_1">
            <td>&nbsp;</td>
            <td><a href='javascript:void(0)'>http://editor.baidu.com</a></td>
            <td><span class="glyphicon glyphicon-signal"></span></td>
            <td>1,487</td>
            <td>1,229</td>
            <td>1,288</td>
            <td>00:02:11</td>
            <td>86.62%</td>
        </tr>
        <tr class="child_row_1">
            <td>&nbsp;</td>
            <td><a href='javascript:void(0)'>http://editor.baidu.com</a></td>
            <td><span class="glyphicon glyphicon-signal"></span></td>
            <td>1,487</td>
            <td>1,229</td>
            <td>1,288</td>
            <td>00:02:11</td>
            <td>86.62%</td>
        </tr>
        </tbody>
        <tfoot>
        <tr class="active">
            <td>&nbsp;</td>
            <td>当页汇总</td>
            <td>&nbsp;</td>
            <td><span style="font-weight: bold">1,983</span></td>
            <td><span style="font-weight: bold">1,722</span></td>
            <td><span style="font-weight: bold">1,631</span></td>
            <td><span style="font-weight: bold">00:02:06</span></td>
            <td><span style="font-weight: bold">82.25%</span></td>
        </tr>
        </tfoot>
    </table>
</div>
<!--end <div class="panel panel-default">-->

<div style="color: gray; line-height: 2;padding-top: 20px;">
    小贴士：
    <br>
    由于数据量庞大，访客数（UV）和IP数指标约有6小时延时，每3小时更新；其余数据延时2小时，每小时更新。
    <br>
    详细页面不存在跳出率、访问次数和平均访问页数指标，因此无数据显示；详细页面浏览量占比为该页面浏览量占所在域名总浏览量的比值。
    <br>
    域名行展开后为该域名下按浏览量（PV）计算的top10详细页面URL，如要查看更多详细页面，请前往受访页面查询。
        </div>
</div>
<!--end <div class="col-md-10">-->
    </div>
</div>
</div>
</body>
</html>
