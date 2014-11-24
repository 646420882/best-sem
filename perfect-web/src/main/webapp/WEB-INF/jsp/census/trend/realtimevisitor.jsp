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

    <style type="text/css">
        .title{
            background-color: #ebebeb;
            height: 24px;
        }
        .wryh{
            font-size: 12px;
            font-family: "微软雅黑";
        }

        .zd_btn{
            margin-left:47%;
            margin-top:-20px;
            cursor: pointer;
        }
        .a_hover{
            width: 80px;
            height: 30px;
            float:left;
            text-align: center;
            line-height: 30px;
            font-family: "微软雅黑";
            font-size: 14px;
        }

        .a_hover:hover{
            background-color: #ebebeb;
            text-decoration: none;
        }

    </style>

    <style type="text/css">
       .fk_infoDiv ul{
           float:left;
           margin-left: 20px;
           list-style: none;
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
                        <b>&nbsp;&nbsp;实时访客</b>
                    </div>

                    <div class="title">
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
                    </div>


                    <%--Table--%>
                    <div>
                        <a  class="margin_10 a_hover" href="javascript:void(0)" onclick="showOrhide('.tiaojianDiv')">高级筛选</a>
                        <div class="col-md-12 tiaojianDiv" style="display: none;background-color:ghostwhite;">
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
                        </div>

                        <div class="col-md-12" style="background-color: #dcdcdc;">
                            <span>关键词/搜索词</span><input type="text"/>
                            <span>入口页面</span><input type="text"/>
                            <span>访客标识码</span><input type="text"/>
                            <span>IP</span><input type="text"/>
                            <input type="button" value="查询" />
                        </div>

                        <%--表格--%>
                        <table class="table table-striped col-md-10">
                            <thead>
                            <td></td>
                            <td></td>
                            <td>地域</td>
                            <td>访问时间</td>
                            <td>来源</td>
                            <td>访客标识码</td>
                            <td>访问IP</td>
                            <td>访问时长</td>
                            <td>访问页数</td>
                            </thead>
                            <tbody>
                            <tr>
                                 <%--<td><a href="javascript:void(0)" onclick="javascript:showOrhide('.fk1')">+</a></td>--%>
                                 <td><a href="javascript:" onclick="showOrhideForNextNode(this);">+</a></td>
                                 <td>1</td>
                                 <td>重庆</td>
                                 <td>14:06:03</td>
                                 <td>http://www.com.perfect.api.baidu.com/login</td>
                                 <td>f0a37486b9730687b23a2edee5d336db<a href="#" class="wryh">&nbsp;仅看该访客</a></td>
                                 <td>196.168.2.2<a href="#" class="wryh">&nbsp;仅看该IP</a></td>
                                 <td>6</td>
                                 <td>2</td>
                            </tr>
                            <tr style="display: none;">
                                <td colspan="9">
                                    <div class="fk_infoDiv">
                                        <ul>
                                            <li>信息1</li>
                                            <li>信息1</li>
                                            <li>信息1</li>
                                            <li>信息1</li>
                                            <li>信息1</li>
                                            <li>信息1</li>
                                        </ul>
                                        <ul>
                                            <li>信息1</li>
                                            <li>信息1</li>
                                            <li>信息1</li>
                                            <li>信息1</li>
                                            <li>信息1</li>
                                            <li>信息1</li>
                                        </ul>
                                        <ul>
                                            <li>信息1</li>
                                            <li>信息1</li>
                                            <li>信息1</li>
                                            <li>信息1</li>
                                            <li>信息1</li>
                                            <li>信息1</li>
                                        </ul>

                                    </div>
                                </td>
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

<script src="/public/js/census/trendAnalysis.js"></script>
<script type="text/javascript">
    function showOrhideForNextNode(s){
        $(s).parentsUntil("tr").parent().next().toggle(0);
    }
    function showOrhide(s){
        $(s).toggle(0);
    }
</script>
</body>
</html>
