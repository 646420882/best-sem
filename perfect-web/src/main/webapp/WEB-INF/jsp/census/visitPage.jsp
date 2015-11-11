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
    <link rel="stylesheet" type="text/css" href="/public/css/count/count.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="/public/js/bootstrap-daterangepicker-moment.js"></script>
    <script src="/public/js/bootstrap-daterangepicker.js"></script>
    <script src="/public/js/census/publicController.js"></script>
    <script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
    <script>
        $(function(){
            var  ipset = ((returnCitySN["cip"] == undefined) ? "" : returnCitySN["cip"]);
            var _tableTbody = $("table:eq(1) tbody");
            $.post("/pftstis/getVisitPageCount",{ip:ipset},function(res){
                intiTr(res);
            });
        });
        function intiTr(res) {
            var json = res.result.sum;
            var _td0 = $("table:eq(0) thead tr");
            for (var i = 0; i < json.length; i++) {
                _td0.find("td:eq(" + i + ")").find("span:eq(1)").html(json[i]);
            }
            _td0.find("td:eq(3)").find("span:eq(1)").html("00:0" + getRamdom(2, 0) + ":" + getRamdom(59, 0));
            _td0.find("td:eq(4)").find("span:eq(1)").html(getRamdom(100, 2) + "%");
            $(".well span:eq(0)").html("受访页面(" + res.result.timeSpan + ")");
            initTable(res);
        }
        function initTable(res) {
            var _tableTbody = $("table:eq(1) tbody");
            _tableTbody.empty();
            var json = res.result.items;
            var finalTotalPv = 0;
            var finalTotaUv = 0;
            if (json != undefined) {
                for (var i = 0; i < json.length; i++) {
                    var url = json[i].censusUrl;
                    var totalPv = json[i].totalPv;
                    var totalUv = json[i].totalUv;
                    finalTotalPv = finalTotalPv + totalPv;
                    finalTotaUv = finalTotaUv + totalUv;
                    var entity = "<tr><td style='color: red;'>" + (i + 1) + "</td>" +
                            "<td><a href='" + url + "'>" + url + "</a></td>" +
                            "<td><a href='javascript:void(0)' onclick='AlertPrompt.show(\"该功能还在开发中!\");'><span class='glyphicon glyphicon-signal'></span></a></td>" +
                            "<td>" + totalPv + "</td>" +
                            "<td>" + totalUv + "</a></td>" +
                            "<td>" + getRamdom(totalPv, 0) + "</a></td>" +
                            "<td>00:0" + getRamdom(2, 0) + ":" + getRamdom(59, 0) + "</a></td>" +
                            "<td>" + getRamdom(100, 2) + "%</a></td>" +
                            "</tr>";
                    _tableTbody.append(entity);
                }
                console.log(getoutPageSum());
                var _footer = "<tr style='font-weight: bold;'><td>&nbsp;</td><td>当页汇总</td><td>&nbsp;</td><td>" + finalTotalPv + "</td><td>" + finalTotaUv + "</td><td>" + getoutPageSum()[0] + "</td><td>" + getoutPageSum()[1] + "</td><td>" + getoutPageSum()[2] + "</td></tr>";
                _tableTbody.append(_footer);
            }
        }
        function getRamdom(str, count) {
            if (count == 0) {
                return parseInt(Math.random() * str);
            }
            return parseFloat(Math.random() * str).toFixed(count);
        }
        function getoutPageSum(){
            var outCount=0;
            var timeTotal=0;
            var out=0;
            var _tr=$("table:eq(1) tbody tr");
            $(_tr).each(function(i,o){
                outCount=outCount+parseInt($(o).find("td:eq(5)").html());
                var time=$(o).find("td:eq(6)").html();
                timeTotal=timeTotal+parseInt(time.split(":")[1])*60+parseInt(time.split(":")[2]);
                out=out+parseInt($(o).find("td:eq(7)").html().split("%")[0]);
            })
            var avgOut=out/_tr.size();
            var avgTime=parseInt(timeTotal/_tr.size());
            var finalAvgTime=getTime(avgTime);
            return new Array(outCount,finalAvgTime,parseFloat(avgOut).toFixed(2)+"%");
        }
        function getTime(str){
            var min=0;
            var sec=0;
            if(str%60!=0){
                min=str/60;
                sec=str%60;
            }
            return "00:"+min+":"+sec;
        }
    </script>
    <style>
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
<jsp:include page="../count/count_head.jsp"/>
<jsp:include page="../count/count_nav.jsp"/>
<div class="count_content clearfix">
<div class="row">
<div class="col-md-12">
<div class="well well-sm">
            <span style="font-weight: bold;">受访页面 (2014/11/20)</span>&nbsp;
            <a href="javascript:void(0)" onclick="Test();"><span class="glyphicon glyphicon-question-sign"></span></a>
            <a style="float: right;" id="DownloadReport" href="javascript:void(0);"><span class="glyphicon glyphicon-download-alt"></span>下载</a>
        </div>
        <div class="alert alert-success" role="alert" style="display: none">
            <p>本报告助您分析：</p>
            <p>
                访客对您网站各个页面的访问情况，重点从页面价值、入口页和退出页进行分析。页面价值分析：重点考量浏览量、贡献下游浏览量和平均停留时长；入口页分析：重点考量浏览量和入口页次数；退出页分析：重点考量浏览量、退出页次数和退出率；</p>
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
                                                $("input[name='db']").daterangepicker(null, function (start, end, label) {
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
                    <ul class="nav nav-tabs" role="tablist"  id="tab1">
                        <li role="presentation" class="active"><a href="#" style="cursor: pointer;">指标概览</a></li>
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
                            <span class="value" title="1,059">1,059</span>
                        </td>
                        <td style="padding: 8px 30px;">
                            <span class="text">访客数(UV)<a href="javascript:void(0)"> </a></span>
                            <br>
                            <span class="value" title="1,059">1,722</span></td>
                        <td style="padding: 8px 30px;">
                            <span class="text"> IP数<a href="javascript:void(0)"> </a></span>
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
            </div><!--end <div class="panel panel-default">-->

            <div class="panel panel-default">
                <div class="panel-body">
                    <ul class="nav nav-tabs" role="tablist" id="tab2">
                        <li role="presentation" class="active"><a href="#" onclick="Ts();"
                                                                  style="cursor: pointer;">高级选项</a></li>
                    </ul>
                    <div id="filter"
                         style="background-color: #f9f9f9;line-height: 30px; padding: 10px 15px; display: none; margin-top: 8px;">
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
                        <td>退出页次数</td>
                        <td>平均停留时长</td>
                        <td>退出率</td>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                    <tfoot>
                    <%--<tr class="active">--%>
                    <%--<td >当页汇总</td>--%>
                    <%--<td >&nbsp;</td>--%>
                    <%--<td >&nbsp;</td>--%>
                    <%--<td ><span style="font-weight: bold">1,983</span></td>--%>
                    <%--<td ><span style="font-weight: bold">1,722</span></td>--%>
                    <%--<td ><span style="font-weight: bold">1,631</span></td>--%>
                    <%--<td ><span style="font-weight: bold">00:02:06</span></td>--%>
                    <%--<td><span style="font-weight: bold">82.25%</span></td>--%>
                    <%--</tr>--%>
                    </tfoot>
                </table>
            </div><!--end  <div class="panel panel-default">-->
        <div style="color: gray; line-height: 2;padding-top: 20px;">
            小贴士：
            <br>
            您可以在“页面上下游”报告中将贡献下游浏览量高的页面设置为监测页面。
        </div>
        </div><!--end col-md-10-->
    </div>
</div>
</div>
</body>
</html>
