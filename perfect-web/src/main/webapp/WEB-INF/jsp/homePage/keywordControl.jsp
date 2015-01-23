<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/7/25
  Time: 9:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>重点关键词监控</title>
    <link rel="stylesheet" href="../public/css/accountCss/public.css"/>
    <link rel="stylesheet" href="../public/css/accountCss/style.css"/>
    <link rel="stylesheet" href="../public/js/plugs/grid.css"/>
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <script type="text/javascript" src="../public/js/plugs/framework.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            var $tab_li = $('.tab_menu li');
            $tab_li.hover(function(){
                $(this).addClass('selected').siblings().removeClass('selected');
                var index = $tab_li.index(this);
                $('div.tab_box > div').eq(index).show().siblings().hide();
            });

          var  gridClass =$("#gridTable").grid({
                url : "getData",
                start : 1,//开始页
                pageSize : 10,//最大页
                where:function(){
                    return {};
                },
                render:function(row,column,value,entity){
                    return value;
                }
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
<div class="container over">
<div class="list01_top over"> <Span>重点关键词监控</Span> <a href="#" class="question"></a>
    <ul>
        <li class="current"><a href="#">昨天</a></li>
        <li><a href="#">近7天</a></li>
        <li><a href="#">近30天</a></li>
        <li class="date"><a href="#">自定义
            <input type="image" src="../public/img/date.png">
        </a> </li>
    </ul>
</div>
<div class="list02 wd">
<table border="0" cellspacing="0" cellspacing="0">
<tr class="list02_top">
    <td>&nbsp;<span>关键词</span> <b>
        <p>
            <input class="one" type="button">
        </p>
        <p>
            <input class="two" type="button">
        </p>
    </b><a href="#" class="question"></a></td>
    <td>&nbsp;<span>展现</span><b>
        <p>
            <input class="one" type="button">
        </p>
        <p>
            <input class="two" type="button">
        </p>
    </b></td>
    <td>&nbsp;<span>点击</span><b>
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
    <td>&nbsp;<span>平均点击价格</span><b>
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
    </b></td>
    <td>&nbsp;<span>转化</span><b>
        <p>
            <input class="one" type="button">
        </p>
        <p>
            <input class="two" type="button">
        </p>
    </b></td>
    <td>&nbsp;<span>平均排名</span><b>
        <p>
            <input class="one" type="button">
        </p>
        <p>
            <input class="two" type="button">
        </p>
    </b></td>
    <td>&nbsp;<span>质量度</span><b>
        <p>
            <input class="one" type="button">
        </p>
        <p>
            <input class="two" type="button">
        </p>
    </b></td>
    <td>&nbsp;<span>匹配</span><b>
        <p>
            <input class="one" type="button">
        </p>
        <p>
            <input class="two" type="button">
        </p>
    </b> <a href="#" class="question"></a></td>
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
    <td>&nbsp;
        <ul class="paihang">
            <li><img src="../public/img/star.png"></li>
            <li><img src="../public/img/star2.png"></li>
            <li><img src="../public/img/star3.png"></li>
            <li><img src="../public/img/star3.png"></li>
            <li><img src="../public/img/star3.png"></li>
        </ul>
        <span>0</span></td>
    <td>&nbsp;广泛</td>
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
    <td>&nbsp;
        <ul class="paihang">
            <li><img src="../public/img/star.png"></li>
        <li><img src="../public/img/star2.png"></li>
        <li><img src="../public/img/star3.png"></li>
        <li><img src="../public/img/star3.png"></li>
        <li><img src="../public/img/star3.png"></li>
        </ul>
        <span>0</span></td>
    <td>&nbsp;广泛</td>
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
    <td>&nbsp;
        <ul class="paihang">
            <li><img src="../public/img/star.png"></li>
            <li><img src="../public/img/star2.png"></li>
            <li><img src="../public/img/star3.png"></li>
            <li><img src="../public/img/star3.png"></li>
            <li><img src="../public/img/star3.png"></li>
        </ul>
        <span>0</span></td>
    <td>&nbsp;广泛</td>
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
<div class="list02 wd">
    <table id="gridTable"  border="0" cellspacing="0" cellspacing="0">
        <tr class="list02_top">
            <th field="keyword"><span>关键词</span></th>
            <th field="caigoudingdanId"><span>展现</span></th>
            <th field="mingcheng"><span>点击</span></th>
            <th >操作</th>
        </tr>
    </table>
</div>


</body>
</html>
