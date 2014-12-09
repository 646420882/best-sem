<%--
  Created by IntelliJ IDEA.
  User: SubDong
  Date: 2014/11/19
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>全部来源</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/count/count.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">

    <style>
        * {
            font-size: 12px;
            font-family: Microsoft Yahei, "微软雅黑", "新宋体", "宋体", "Arial Narrow";
        }
        .container_waibu {
            min-height: 40px;
            padding-top: 10px;
            font-size: 14px;
            font-weight: bold;
        }

        .container_head {
            width: 100%;
            height: 40px;
            border: 1px solid #DEEFFF;
            background-color: #ECF3FD;
            overflow: hidden;
            border-radius: 6px;
        }

        .form-control {
            height: 30px;
            width: 200px;
        }

        .col-lg-1 {
            font-weight: bold;
            width: 85px;
            padding: 11px;
        }
        .col-lg-2{
            padding-top: 5px;
        }
        .col-xs-1{
            width: 16.2%;
            border-right: 1px solid #E4E4E4;
            padding-top: 10px;
            font-size: 14px;
            font-weight: bold;
            min-height: 60px;
        }

        .span_div {
            border: 1px solid #E4E4E4;
            margin-top: 10px;
            min-height: 60px;
            background-color: #FDF2EF;
            overflow: hidden;
        }
        .st{
            background-color: #73b1e0;
            border-radius: 6px;
        }
        .st a{color: #ffffff}

        .chartsStyle {
            margin-top: 20px;
            min-height: 300px;
            width: 20%;
            float: left;
        }
        .chartsStyle1 {
            margin-top: 20px;
            min-height: 300px;
            width: 80%;
            float: left;
        }
        .echartsMain{
            display: block;
            border: 1px solid #DEEFFF;

        }

        .display{
            display: none;
        }


        .a_hover{
            width: 80px;
            height: 30px;
            float:left;
            text-align: center;
            line-height: 30px;
            font-family: "微软雅黑";
            font-size: 12px;
        }

        .a_hover:hover{
            background-color: #ebebeb;
            text-decoration: none;
        }
        .table{
            font-size: 12px;
        }
    </style>

</head>
<body>

<div class="container-fluid">
<jsp:include page="../count/count_head.jsp"/>
<jsp:include page="../count/count_nav.jsp"/>
<div class="count_content clearfix">
<div class="container_waibu">外部连接</div>
<div class="container_head">
    <div class="row text-center">
        <a href="javascript:dateTime=1;getData();"><div class="col-lg-1 winfont st" cname=dateClick style="margin-left: 40px;">今天</div></a>
        <a href="javascript:dateTime=-1;getData();"><div class="col-lg-1 winfont">昨天</div></a>
        <a href="javascript:dateTime=7;getData();"><div class="col-lg-1 winfont">最近7天</div></a>
        <a href="javascript:dateTime=30;getData();"><div class="col-lg-1 winfont">最近30天</div></a>
        <div class="col-lg-2">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="开始时间" value="">
                            <span class="input-group-addon"><input name="reservation" cname="dateClick" type="image"
                                                                   onclick="javascript: _posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                                                                   src="${pageContext.request.contextPath}/public/img/date.png"
                                                                   id="test"></span>
            </div>
        </div>
        <div class="col-lg-1"><input type="checkbox" id="blankCheckbox"></div>

        <div class="col-lg-2 display" id="inputDiv2">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="对比时间" value="">
                            <span class="input-group-addon"><input name="reservation"  cname="dateClick" type="image"
                                                                   onclick="javascript: _posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                                                                   src="${pageContext.request.contextPath}/public/img/date.png"></span>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2">
            <ul class="nav nav-tabs" id="heDiv">
                <li class="active"><a href="javascript:allTypes=1;getData()">全部来源</a></li>
                <li><a href="javascript:allTypes=2;getData()">搜索引擎</a></li>
                <li><a href="javascript:allTypes=3;getData()">搜索词</a></li>
                <li><a href="javascript:allTypes=4;getData()">外部链接</a></li>
            </ul>
        </div>
        <div class="span10 span_div">
            <div class="row text-center" id="head_Div_data">

            </div>
        </div>

        <div class="echartsMain">
            <div id="tubiao1" class="chartsStyle"></div>
            <div id="tubiao2" class="chartsStyle1"></div>
        </div>
        <script>
            require.config({
                paths: {
                    echarts: 'http://echarts.baidu.com/build/dist'
                }
            });

            // 使用
            require(
                    [
                        'echarts',
                        'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
                    ],
                    function (ec) {
                        // 基于准备好的dom，初始化echarts图表
                        var myChart1 = ec.init(document.getElementById('tubiao1'));

                        var option = {
                            tooltip : {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c} ({d}%)"
                            },
                            toolbox: {
                                show : false,
                                feature : {
                                    mark : {show: true},
                                    dataView : {show: true, readOnly: false},
                                    magicType : {
                                        show: true,
                                        type: ['pie', 'funnel'],
                                        option: {
                                            funnel: {
                                                x: '25%',
                                                width: '50%',
                                                funnelAlign: 'left',
                                                max: 1548
                                            }
                                        }
                                    },
                                    restore : {show: true},
                                    saveAsImage : {show: true}
                                }
                            },
                            calculable : true,
                            series : [
                                {
                                    name:'访问来源',
                                    type:'pie',
                                    radius : '55%',
                                    center: ['50%', '60%'],
                                    data:[
                                        {value:335, name:'直接访问'},
                                        {value:310, name:'邮件营销'},
                                        {value:234, name:'联盟广告'},
                                        {value:135, name:'视频广告'},
                                        {value:1548, name:'搜索引擎'}
                                    ]
                                }
                            ]
                        };

                        // 为echarts对象加载数据
                        myChart1.setOption(option);
                    }
            );
            // 使用
            require(
                    [
                        'echarts',
                        'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
                    ],
                    function (ec) {
                        // 基于准备好的dom，初始化echarts图表
                        var myChart = ec.init(document.getElementById('tubiao2'));

                        var option = {
                            tooltip : {
                                trigger: 'axis'
                            },
                            legend: {
                                data:['邮件营销']
                            },
                            toolbox: {
                                show : false,
                                feature : {
                                    mark : {show: true},
                                    dataView : {show: true, readOnly: false},
                                    magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                                    restore : {show: true},
                                    saveAsImage : {show: true}
                                }
                            },
                            calculable : true,
                            xAxis : [
                                {
                                    type : 'category',
                                    boundaryGap : false,
                                    data : ['周一','周二','周三','周四','周五','周六','周日']
                                }
                            ],
                            yAxis : [
                                {
                                    type : 'value'
                                }
                            ],
                            series : [
                                {
                                    name:'邮件营销',
                                    type:'line',
                                    stack: '总量',
                                    data:[120, 132, 101, 134, 90, 230, 210]
                                }
                            ]
                        };

                        // 为echarts对象加载数据
                        myChart.setOption(option);
                    }
            );
        </script>
        <div>
            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#zdyzb" role="tab" data-toggle="tab">自定义指标</a></li>
                <li role="presentation"><a href="#gjsx" role="tab" data-toggle="tab">高级筛选</a></li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content" style="background-color:ghostwhite;">
                <div role="tabpanel" class="tab-pane active" id="zdyzb">
                    <span class="col-md-10"><b>系统默认指标</b></span>
                    <span class="col-md-2">最多可筛选6个条件</span>

                    <div class="col-md-12">
                        <h6 class="col-md-1"><b>网址基础指标:</b></h6>
                        <h6 class="col-md-1"><input type="checkbox" checked="checked"/>浏览量(PV)</h6>
                        <h6 class="col-md-1"><input type="checkbox" checked="checked"/>浏览量占比</h6>
                        <h6 class="col-md-1"><input type="checkbox" checked="checked"/>方位次数</h6>
                        <h6 class="col-md-1"><input type="checkbox" checked="checked"/>访客数(UV)</h6>
                        <h6 class="col-md-1"><input type="checkbox"/>新访客数</h6>
                        <h6 class="col-md-1"><input type="checkbox"/>新访客比率</h6>
                        <h6 class="col-md-1"><input type="checkbox" checked="checked"/>IP数</h6>
                    </div>

                    <div class="col-md-12">
                        <h6 class="col-md-1"><b>流量质量指标:</b></h6>
                        <h6 class="col-md-1"><input type="checkbox" checked="checked"/>跳出率</h6>
                        <h6 class="col-md-1"><input type="checkbox" checked="checked"/>平均访问时长</h6>
                        <h6 class="col-md-1"><input type="checkbox" checked="checked"/>平均访问页数</h6>
                    </div>

                    <div class="col-md-12">
                        <h6 class="col-md-1"><b>转化指标指标:</b></h6>
                        <h6 class="col-md-1"><input type="checkbox" checked="checked"/>转化次数</h6>
                        <h6 class="col-md-1"><input type="checkbox"/>转换率</h6>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane" id="gjsx">
                    <br>
                    <div class="col-md-12">
                        <h6>
                            <b>设备过滤</b>&nbsp;&nbsp;
                            <input type="radio" name="sb" checked="checked"/>全部&nbsp;&nbsp;
                            <input type="radio" name="sb" /> 计算机&nbsp;&nbsp;
                            <input type="radio" name="sb" />移动设备&nbsp;&nbsp;
                        </h6>
                    </div><br><br>
                    <div class="col-md-12">
                        <h6>
                            <b>访客过滤</b>&nbsp;&nbsp;
                            <input type="radio" name="fk" checked="checked"/>全部&nbsp;&nbsp;
                            <input type="radio" name="fk" />新访客&nbsp;&nbsp;
                            <input type="radio" name="fk" />老访客&nbsp;&nbsp;
                        </h6>
                    </div>

                </div>
            </div>
            <%--<a  class="margin_10 a_hover" href="javascript:void(0)" onclick="showOrhide('.tiaojianDiv')">高级筛选</a>--%>
            <%--<div class="col-md-12 tiaojianDiv" style="display: none;background-color:ghostwhite;">
                <div class="col-md-12">
                    <h6>
                        <b>来源过滤</b>&nbsp;&nbsp;
                        <select>
                            <option>全部</option>
                            <option>直接访问</option>
                            <option>搜索引擎</option>
                            <option>外部链接</option>
                        </select>&nbsp;
                        <a href="#">百度</a>&nbsp;
                        <a href="#">google</a>
                    </h6>
                </div>

                <div class="col-md-12">
                    <h6>
                        <b>设备过滤</b>&nbsp;&nbsp;
                        <input type="radio" name="sb" checked="checked"/>全部&nbsp;&nbsp;
                        <input type="radio" name="sb" /> 计算机&nbsp;&nbsp;
                        <input type="radio" name="sb" />移动设备&nbsp;&nbsp;
                    </h6>
                </div>

                <div class="col-md-12">
                    <h6>
                        <b>地域过滤</b>&nbsp;&nbsp;
                        <select>
                            <option>全部</option>
                            <option>全国</option>
                            <option>省市自治区</option>
                            <option>其他</option>
                        </select>&nbsp;
                        <a href="#">北京</a>&nbsp;
                        <a href="#">上海</a>
                        <a href="#">广东</a>
                    </h6>
                </div>

                <div class="col-md-12">
                    <h6>
                        <b>访客过滤</b>&nbsp;&nbsp;
                        <select>
                            <option>全部</option>
                            <option>新访客</option>
                            <option>老访客</option>
                        </select>

                        <select>
                            <option>访问频次</option>
                            <option>1次</option>
                            <option>2次</option>
                            <option>3次</option>
                            <option>4次</option>
                            <option>5-10次</option>
                            <option>10-20次</option>
                            <option>20次以上</option>
                        </select>

                        <select>
                            <option>访问深度</option>
                            <option>1页</option>
                            <option>2页</option>
                            <option>3-5页</option>
                            <option>6-10页</option>
                            <option>11-15页</option>
                            <option>16-20页</option>
                            <option>20页以上</option>
                        </select>

                        <select>
                            <option>访问时长</option>
                            <option>0-30s</option>
                            <option>31s-60s</option>
                            <option>61s-90s</option>
                            <option>91s-180s</option>
                            <option>181s-300s</option>
                            <option>301s-600s</option>
                            <option>600s以上</option>
                        </select>
                    </h6>
                </div>
            </div>--%>

            <div class="col-md-12" style="background-color: #dcdcdc;margin-top: 20px">
                <span>关键词/搜索词</span><input type="text"/>
                <span>入口页面</span><input type="text"/>
                <span>访客标识码</span><input type="text"/>
                <span>IP</span><input type="text"/>
                <input type="button" value="查询" />
            </div>

            <%--表格--%>
            <table class="table table-striped col-md-10">
                <thead id="theadData">

                </thead>
                <tbody id="tbodyData">

                </tbody>
            </table>
        </div>
    </div>
</div>
<%--分页--%>
<nav>
    <ul class="pagination">
        <li><a href="#">&laquo;</a></li>
        <li><a href="#">1</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">5</a></li>
        <li><a href="#">6</a></li>
        <li><a href="#">7</a></li>
        <li><a href="#">8</a></li>
        <li><a href="#">&raquo;</a></li>
    </ul>
</nav>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-ui-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.jQuery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ui.datepicker-zh-CN.js"></script>
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/census/allSource.js"></script>
</body>
</html>
