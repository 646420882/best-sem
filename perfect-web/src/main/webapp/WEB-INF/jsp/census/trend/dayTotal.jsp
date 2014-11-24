<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2014/11/19
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实时访客-百度统计</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">


    <style type="text/css">
       .fk_infoDiv ul{
           float:left;
           margin-left: 20px;
           list-style: none;
       }

    </style>

    <style>
        body {
            font-size: 12px;
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
        .col-md-2{
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
        .ddddd {
            margin-top: 20px;
        }
        .display{
            display: none;
        }

        .zd_btn{
            margin-left:47%;
            margin-top:-20px;
            cursor: pointer;
        }

    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="row">
                <%--leftDiv--%>
                <div class="col-md-2">
                    <jsp:include page="../left/left-nav.jsp"/>
                </div>

                 <%--right--%>
                <div class="col-md-10">

                    <div class="page-header">
                        <b>&nbsp;&nbsp;趋势分析<span>(2014/11/20)</span></b>
                    </div>

                    <div class="container_head">
                        <div class="row text-center">
                            <div class="col-lg-1 winfont st" cname=dateClick style="margin-left: 40px;"><a href="javascript:void(0)">今天</a></div>
                            <div class="col-lg-1 winfont"><a href="javascript:void(0)">昨天</a></div>
                            <div class="col-lg-1 winfont"><a href="javascript:void(0)">最近7天</a></div>
                            <div class="col-lg-1 winfont"><a href="javascript:void(0)">最近30天</a></div>
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

                    <div class="span2">
                        <ul class="nav nav-tabs" id="heDiv">
                            <li class="active"><a href="javascript:void(0)">全部来源</a></li>
                            <li><a href="javascript:void(0)">搜索引擎</a></li>
                            <li><a href="javascript:void(0)">搜索词</a></li>
                            <li><a href="javascript:void(0)">外部链接</a></li>
                        </ul>
                    </div>
                    <div class="span10 span_div">
                        <div class="row text-center">
                            <div class="col-md-2" style="margin-left: 40px;">浏览比例</div>
                            <div class="col-md-2">访问次数</div>
                            <div class="col-md-2">新访客数</div>
                            <div class="col-md-2">新访客比率</div>
                            <div class="col-md-2">订单转化率</div>
                        </div>
                    </div>

                    <div class="col-md-12 collapse in ddddd" id="zj30zxt" style="border: 1px solid #808080;">
                        各种条件<br/>
                        各种条件<br/>
                        各种条件<br/>
                        各种条件<br/>
                        各种条件<br/>
                        各种条件<br/>
                        各种条件<br/>
                        各种条件<br/>
                        各种条件<br/>
                        各种条件<br/>
                    </div>
                    <div class="col-md-12">
                        <hr/>
                        <img class="zd_btn" src="../public/img/zd_btn.png" onclick="javascript:$('.collapse').collapse('toggle');" data-target="#zj30zxt"/>
                    </div>

                    <%-- <div class="title">
                         <span class="wryh col-md-11">最近30分钟访问情况</span>
                         <span class="wryh col-md-1">当前在线人数&nbsp;&nbsp;4&nbsp;&nbsp;</span>
                     </div>

                     <div class="col-md-12 collapse in" id="zj30zxt" style="border: 1px solid #808080;">
                         各种条件<br/>
                         各种条件<br/>
                         各种条件<br/>
                         各种条件<br/>
                         各种条件<br/>
                         各种条件<br/>
                         各种条件<br/>
                         各种条件<br/>
                         各种条件<br/>
                         各种条件<br/>
                     </div>

                     <div class="col-md-12">
                         <hr/>
                         <img class="zd_btn" src="../public/img/zd_btn.png" onclick="javascript:$('.collapse').collapse('toggle');" data-target="#zj30zxt"/>
                     </div>

                     <div class="title col-md-12">
                         <span class="wryh">最近500次访客访问明细</span>
                     </div>--%>


                    <%--Table--%>
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
                                        <input type="radio" name="fk" checked="checked"/>全部&nbsp;&nbsp;
                                        <input type="radio" name="fk" />新访客&nbsp;&nbsp;
                                        <input type="radio" name="fk" />老访客&nbsp;&nbsp;
                                        </h6>
                                    </div>

                                </div>
                            </div>


                        <div class="col-md-12" style="background-color: #dcdcdc;">
                            <div class="col-md-10"></div>
                            <div class="col-xs-2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;转化目标
                                <select>
                                    <option>全部页面目标</option>
                                    <option>公告</option>
                                    <option>全部事件目标</option>
                                    <option>完整下载</option>
                                    <option>在线安装</option>
                                    <option>时长目标</option>
                                    <option>访问页数目标</option>
                                </select>
                            </div>
                        </div>

                        <%--表格--%>
                        <table class="table table-striped col-md-10">
                            <thead>
                            <td></td>
                            <td>日期</td>
                            <td>浏览量(PV)</td>
                            <td>浏览量占比</td>
                            <td>访问次数</td>
                            <td>访客数</td>
                            <td>IP数</td>
                            <td>转化次数</td>
                            </thead>
                            <tbody>
                            <tr>
                                <td>1</td>
                                <td>10:00-10:59</td>
                                <td>10</td>
                                <td>10%</td>
                                <td>101</td>
                                <td>101</td>
                                <td>101</td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>10:00-10:59</td>
                                <td>10</td>
                                <td>10%</td>
                                <td>101</td>
                                <td>101</td>
                                <td>101</td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>10:00-10:59</td>
                                <td>10</td>
                                <td>10%</td>
                                <td>101</td>
                                <td>101</td>
                                <td>101</td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>4</td>
                                <td>10:00-10:59</td>
                                <td>10</td>
                                <td>10%</td>
                                <td>101</td>
                                <td>101</td>
                                <td>101</td>
                                <td>1</td>
                            </tr>
                            </tbody>
                        </table>

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

            </div>
         </div>
        </div>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-ui-1.11.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.jQuery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ui.datepicker-zh-CN.js"></script>
<script src="/public/js/census/dayTotal.js"></script>
<script type="text/javascript">
    function showOrhide(select){
        $(select).toggle(0);
    }
</script>
</body>
</html>
