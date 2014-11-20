<%--
  Created by IntelliJ IDEA.
  User: guochunyan
  Date: 2014/11/19
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<head>
    <title>大数据智能营销-统计</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/count/count.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/2.3.2/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $('body').on("click", ".count_nav_mid",function (){
                    $(this).next().addClass('display');
                    $(this).addClass('count_nav_mid2').removeClass('count_nav_mid');
            });
            $('body').on("click", ".count_nav_mid2",function () {
                $(this).next().removeClass('display');
                $(this).addClass('count_nav_mid').removeClass('count_nav_mid2');
            });
            })

    </script>
</head>
<body>
    <div class="container-fluid">
         <div class="top">
             <div class="top_logo fl">
              <img src="${pageContext.request.contextPath}/public/img/logo.png">
             </div>
             <div class="top_nav fr">
                 <ul>
                     <li> <a href="#">返回首页</a></li>
                     <li> <a href="#">帮助</a> </li>
                     <li> <a href="#">最新功能</a> </li>
                     <li>
                         <ul class="nav nav-pills">
                             <li class="dropdown">
                                 <span class="dropdown-toggle"
                                       data-toggle="dropdown"
                                       href="#">
                                     移动统计
                                     <b class="caret"></b>
                                 </span>

                                 <ul class="dropdown-menu">
                                     <!-- links -->
                                     <li>百度联盟</li>
                                 </ul>
                             </li>
                         </ul>
                     </li>
                     <li>
                         <ul class="nav nav-pills">
                             <li class="dropdown">
                                 <span class="dropdown-toggle"
                                    data-toggle="dropdown"
                                    href="#">
                                      官方博客
                                     <b class="caret"></b>
                                 </span>
                                 <ul class="dropdown-menu">
                                     <!-- links -->
                                     <li>讨论区</li>
                                     <li>联系我们</li>
                                 </ul>
                             </li>
                         </ul>
                     </li>
                     <li class="top_select">
                         <select>
                             <option>1</option>
                             <option>2</option>
                             <option>3</option>
                             <option>4</option>
                             <option>5</option>
                         </select>
                     </li>
                 </ul>
             </div>
         </div>
        <div class="row-fluid">
            <div class="count_nav">
                <ul>
                    <li class="count_nav_top"><a>网站概况</a> </li>
                    <li>
                        <a class="count_nav_mid">趋势分析</a>
                        <ul class="count_nav_list">
                            <li><a href="#">实时访客</a></li>
                            <li><a href="#">今日统计</a></li>
                            <li><a href="#">昨日统计</a></li>
                            <li><a href="#">最近30天</a></li>

                        </ul>
                    </li>
                    <li>
                        <a class="count_nav_mid">来源分析</a>
                        <ul class="count_nav_list">
                            <li><a href="#">全部来源</a></li>
                            <li><a href="#">搜索引擎</a></li>
                            <li><a href="#">搜索词</a></li>
                            <li><a href="#">外部链接</a></li>

                        </ul>

                    </li>
                    <li>
                        <a class="count_nav_mid">页面分析</a>
                        <ul class="count_nav_list">
                            <li><a href="#">受访页面</a></li>
                            <li><a href="#">受访域名</a></li>
                            <li><a href="#">入口页面</a></li>
                            <li><a href="#">页面点击图</a></li>
                            <li><a href="#">页面上下游</a></li>
                        </ul>
                    </li>
                    <li>
                        <a class="count_nav_mid">访客分析</a>
                        <ul class="count_nav_list">
                            <li><a href="#">地域分布</a></li>
                            <li><a href="#">系统环境</a></li>
                            <li><a href="#">新老访客</a></li>
                            <li><a href="#">访客属性</a></li>
                            <li><a href="#">忠诚度</a></li>
                        </ul>
                    </li>
                    <li>
                        <a class="count_nav_mid2">定制分析</a>
                        <ul class="count_nav_list display" >
                            <li><a href="#">子目录</a></li>
                            <li><a href="#">转化路径</a></li>
                            <li><a href="#">指定广告跟踪</a></li>
                            <li><a href="#">事件跟踪</a></li>
                            <li><a href="#">自定义变量</a></li>
                        </ul>
                    </li>
                    <li>
                        <a class="count_nav_mid">优化分析</a>
                        <ul class="count_nav_list">
                            <li><a href="#">搜索词排名</a></li>
                            <li><a href="#">百度索引量查询</a></li>
                            <li><a href="#">网站速度诊断</a></li>
                            <li><a href="#">升降榜</a></li>
                            <li><a href="#">外链分析</a></li>
                            <li><a href="#">抓取异常</a></li>
                        </ul>
                    </li>
                </ul>


            </div>
            <div class="count_content clearfix">

            </div>
        </div>
    </div>
</body>
</html>
