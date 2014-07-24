<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2014/7/24
  Time: 13:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <link type="text/css" href="../public/css/accountCss/style.css" rel="stylesheet">
    <link type="text/css" href="../public/css/accountCss/public.css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
    <title></title>
    <script type="text/javascript">
        $(document).ready(function(){
            alert(1);
            var $tab_li = $('.tab_menu li');
            $tab_li.hover(function(){
                $(this).addClass('selected').siblings().removeClass('selected');
                var index = $tab_li.index(this);
                $('div.tab_box > div').eq(index).show().siblings().hide();
            });
        });
        function TestBlack(TagName){
            var obj = document.getElementById(TagName);
            if(obj.style.display==""){
                obj.style.display = "none";
            }else{
                obj.style.display = "";
            }
        }
    </script>
</head>
<body>
<div class="concent over">
    <jsp:include page="pageBlock/nav.jsp" flush="true"/>
    <div class="list01 over"  style="border-bottom:1px solid #d5d5d8;">
        <div class="list01_top over"> <Span>账户趋势图</Span> <a href="#" class="question"></a>
            <ul>
                <li class="current"><a href="#">昨天</a></li>
                <li><a href="#">近7天</a></li>
                <li><a href="#">近30天</a></li>
                <li class="date"><a href="#">自定义
                    <input type="image" src="img/date.png">
                </a> </li>
            </ul>
        </div>
        <div class="shuju"> <img src="images/tu.jpg"> </div>
    </div>
    <div class="list01_under3 over" >
        <div class="list01_top over"> <Span>分日表现</Span> <a href="#" class="question"></a>
            <ul>
                <li class="current"><a href="#">昨天</a></li>
                <li><a href="#">近7天</a></li>
                <li><a href="#">近30天</a></li>
                <li class="date"><a href="#">自定义
                    <input type="image" src="img/date.png">
                </a> </li>
            </ul>
        </div>
        <div class="list02 wd">
            <table border="0" cellspacing="0" cellspacing="0">
                <tr class="list02_top">
                    <td>&nbsp;<span>时间</span> <b>
                        <p>
                            <input class="one" type="button">
                        </p>
                        <p>
                            <input class="two" type="button">
                        </p>
                    </b></td>
                    <td>&nbsp;<span>展现量</span><b>
                        <p>
                            <input class="one" type="button">
                        </p>
                        <p>
                            <input class="two" type="button">
                        </p>
                    </b></td>
                    <td>&nbsp;<span>点击量</span><b>
                        <p>
                            <input class="one" type="button">
                        </p>
                        <p>
                            <input class="two" type="button">
                        </p>
                    </b></td>
                    <td>&nbsp;<span>消费</span><b>
                        <p>
                            <input class="one" type="button">
                        </p>
                        <p>
                            <input class="two" type="button">
                        </p>
                    </b></td>
                    <td>&nbsp;<span>点击率</span><b>
                        <p>
                            <input class="one" type="button">
                        </p>
                        <p>
                            <input class="two" type="button">
                        </p>
                    </b><a href="#" class="question"></a></td>
                    <td>&nbsp;<span>平均点击价格</span><b>
                        <p>
                            <input class="one" type="button">
                        </p>
                        <p>
                            <input class="two" type="button">
                        </p>
                    </b><a href="#" class="question"></a></td>
                    <td>&nbsp;<span>平均排名</span><b>
                        <p>
                            <input class="one" type="button">
                        </p>
                        <p>
                            <input class="two" type="button">
                        </p>
                    </b> <a href="#" class="question"></a></td>
                    <td>&nbsp;<span>转化(页面)</span><b>
                        <p>
                            <input class="one" type="button">
                        </p>
                        <p>
                            <input class="two" type="button">
                        </p>
                    </b><a href="#" class="question"></a></td>
                </tr>
                <tr class="list02_box1">
                    <td>&nbsp;<span>搜索引擎优化</span> <span class="green_arrow wd3"></span></td>
                    <td>&nbsp;3.902</td>
                    <td>&nbsp;13</td>
                    <td>&nbsp;￥0.91</td>
                    <td>&nbsp;￥0.91</td>
                    <td>&nbsp;0.33%</td>
                    <td>&nbsp;0.00</td>
                    <td>&nbsp;2.24</td>
                </tr>
                <tr class="list02_box2">
                    <td>&nbsp;<span>搜索引擎优化</span> <span class="red_arrow wd3"></span></td>
                    <td>&nbsp;3.902</td>
                    <td>&nbsp;13</td>
                    <td>&nbsp;￥0.91</td>
                    <td>&nbsp;￥0.91</td>
                    <td>&nbsp;0.33%</td>
                    <td>&nbsp;0.00</td>
                    <td>&nbsp;2.24</td>
                </tr>
                <tr class="list02_box1">
                    <td>&nbsp;<span>搜索引擎优化</span> <span class="hot"></span></td>
                    <td>&nbsp;3.902</td>
                    <td>&nbsp;13</td>
                    <td>&nbsp;￥0.91</td>
                    <td>&nbsp;￥0.91</td>
                    <td>&nbsp;0.33%</td>
                    <td>&nbsp;0.00</td>
                    <td>&nbsp;2.24</td>
                </tr>
                <tr class="list02_box2">
                    <td>&nbsp;<span>搜索引擎优化</span> <span class="green_arrow wd3"></span></td>
                    <td>&nbsp;3.902</td>
                    <td>&nbsp;13</td>
                    <td>&nbsp;￥0.91</td>
                    <td>&nbsp;￥0.91</td>
                    <td>&nbsp;0.33%</td>
                    <td>&nbsp;0.00</td>
                    <td>&nbsp;2.24</td>
                </tr>
                <tr class="list02_box1">
                    <td>&nbsp;<span>搜索引擎优化</span> <span class="red_arrow wd3"></span></td>
                    <td>&nbsp;3.902</td>
                    <td>&nbsp;13</td>
                    <td>&nbsp;￥0.91</td>
                    <td>&nbsp;￥0.91</td>
                    <td>&nbsp;0.33%</td>
                    <td>&nbsp;0.00</td>
                    <td>&nbsp;2.24</td>
                </tr>
                <tr class="list02_box2">
                    <td>&nbsp;<span>搜索引擎优化</span> <span class="hot"></span></td>
                    <td>&nbsp;3.902</td>
                    <td>&nbsp;13</td>
                    <td>&nbsp;￥0.91</td>
                    <td>&nbsp;￥0.91</td>
                    <td>&nbsp;0.33%</td>
                    <td>&nbsp;0.00</td>
                    <td>&nbsp;2.24</td>
                </tr>
                <tr class="list02_box1">
                    <td>&nbsp;<span>搜索引擎优化</span> <span class="red_arrow wd3"></span></td>
                    <td>&nbsp;3.902</td>
                    <td>&nbsp;13</td>
                    <td>&nbsp;￥0.91</td>
                    <td>&nbsp;￥0.91</td>
                    <td>&nbsp;0.33%</td>
                    <td>&nbsp;0.00</td>
                    <td>&nbsp;2.24</td>
                </tr>
            </table>
            <div class="download over fr"> <span>每页显示
                <select>
                    <option>10个</option>
                    <option>9个</option>
                    <option>8个</option>
                </select>
                </span><a href="#">下载全部</a> </div>
        </div>
    </div>
    <jsp:include page="pageBlock/footer.jsp" flush="true"/>
</div>
</body>
</html>
