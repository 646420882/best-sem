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
            <div class="panel panel-default">
                <div class="panel panel-heading" style="height: 75px;" id="panel1">
                    <nav class="navbar navbar-default" role="navigation">
                        <div class="container-fluid">
                            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                                <ul class="nav navbar-nav">
                                    <li class="active"><a href="#">今天</a></li>
                                    <li><a href="#">昨天</a></li>
                                    <li><a href="#">最近7天</a></li>
                                    <li><a href="#">近30天</a></li>
                                    <li>
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
                                    </li>
                                    <li>
                                        <form class="navbar-form navbar-left" role="search" style="margin-top: 14px;">
                                            <div class="checkbox">
                                                <label>
                                                    <input type="checkbox"> 与其他时间对比
                                                </label>
                                            </div>
                                        </form>
                                    </li>
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
                            <span class="text">浏览量(PV)<a href="javascript:void(0)"> </a></span>
                            <br>
                            <span class="value" title="1,059">1,059</span></td>
                        <td style="padding: 8px 30px;">
                            <span class="text"> 浏览量(PV)<a href="javascript:void(0)"> </a></span>
                            <br>
                            <span class="value" title="1,059">1,059</span></td>
                        <td style="padding: 8px 30px;">
                            <span class="text">浏览量(PV) <a href="javascript:void(0)"> </a></span>
                            <br>
                            <span class="value" title="1,059">1,059</span></td>
                    </tr>
                    </thead>
                </table>
            </div>

            <div class="panel panel-default">
                <div class="panel-body">
                    <ul class="nav nav-tabs" role="tablist" id="tab2">
                        <li role="presentation" class="active"><a href="#"><span class="glyphicon glyphicon-star"></span>&nbsp;自定义指标</a></li>
                        <li role="presentation"><a href="#">高级选项</a></li>
                    </ul>
                </div>
            </div>


        </div>
    </div>
</div>

</body>
</html>
