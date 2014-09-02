<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2014-9-1
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <style type="text/css">

        .list4 table {
            border: 1px solid #eaf0f3;
            overflow: auto;
            width: 100%;
        }
        .zs_function{
            margin-top:10px;
        }
    </style>
</head>
<body>
<div style="background-color: #f3f5fd; width: 900px; height: 700px">
    <div class="addplan_top over">
         <ul>
             <li class="current">1、推广业务描述</li>
             <li>1、关键词筛选</li>
         </ul>
    </div>
     <div class="plan_under">
            <div class="containers">
                    <div class="plan_under2">
                         <ul>
                             <li>
                                 <h3>简单的描述您从事的业务</h3>
                                 <input type="text">
                             </li>
                             <li><h3>希望哪里的客户看到您的商品或服务</h3>
                                 <p>比如你是北京学校的老板，想让北京学英语的客户看到</p>
                                 <p><a href="#">投放地域</a><a href="#">使用账户推广地域</a><a href="#">修改</a></p>
                             </li>
                             <li><h3>计划每天花费多少让百度带来新客户</h3>
                                 <p>该消费只会帮助我们提供更适合的方案给您，不会对您的预算造成任何影响</p>
                                 <p><select><option>10-50</option><option>10-50</option></select></p>
                             </li>
                         </ul>

                    </div>
            </div>
         <div class="containers over hides">
             <div class="assembly_under over">
                 <div class="assembly_left">
                     <div class="keyword_left fl over">
                         <div class="k_l_top over">
                             <span>已添加关键词（1/500）</span>   <a class="question" href="#"></a>
                         </div>
                         <div class="keyworld_text over">

                             <div class="keyworld_text2 fl">
                                 <ul>
                                     <li></li>
                                 </ul>
                             </div>
                         </div>
                         <div class="k_l_under over">
                             <div class="k_l_under1 over">
                                 <ul>
                                     <li><select class="team04 color"><option>请选择推广计划</option></select><select class="team04 color"><option>请选择推广单元</option></select></li>
                                     <li><select class="team05 color"><option>保存</option></select></li>
                                 </ul>
                             </div>
                         </div>

                     </div>

                 </div>

                 <div class="assembly_right fr  over">
                     <div class="assembly_right_top over">
                         <div class="assembly_right_under over">
                             <div class="containers over">

                                 <div class="assembly_search over">
                                     搜索相关词 <input type="text">

                                 </div>
                                 <div class="list4">
                                     <div class="zs_function over">
                                         <ul class="fl">
                                             <li><a href="#" id="addKeyword"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加全部</b></a></li>

                                         </ul>

                                     </div>
                                     <table width="100%" cellspacing="0" border="0" width="500px">
                                         <thead>
                                         <tr class="list02_top">
                                             <td>&nbsp;关键词</td>
                                             <td>&nbsp;展现理由</td>
                                             <td>&nbsp;日均搜索量</td>
                                             <td>&nbsp;竞争激烈程度
                                                 <div class="set fr"></div>
                                             </td>
                                         </tr>
                                         </thead>
                                         <tbody>
                                         <tr>
                                             <td>&nbsp;北京婚博会</td>
                                             <td>&nbsp;111</td>
                                             <td>&nbsp;1111</td>
                                             <td>&nbsp;1111 </td>
                                         </tr>
                                         </tbody>

                                     </table>
                                 </div>
                                 <div class="zhanghu_input"></div>

                             </div>

                         </div>
                     </div>

                 </div>

             </div>

         </div>

     </div>



</div>
<script type="text/javascript" src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript">
    $(function () {
        var $tab_li = $('.addplan_top ul li');
        $('.addplan_top ul li').click(function () {
            $(this).addClass('current').siblings().removeClass('current');
            var index = $tab_li.index(this);
            $('div.plan_under > div').eq(index).show().siblings().hide();
        });


    });
</script>
</body>
</html>
