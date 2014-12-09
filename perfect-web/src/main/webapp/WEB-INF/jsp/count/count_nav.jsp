<%--
  Created by IntelliJ IDEA.
  User: SubDong
  Date: 2014/11/24
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row-fluid count_nav">
    <ul>
        <li class="count_nav_top"><a href="/pftstis/getIndex">网站概况</a> </li>
        <li>
            <a class="count_nav_mid">趋势分析</a>
            <ul class="count_nav_list">
                <%--<li><a href="#">实时访客</a></li>--%>
                <%--<li><a href="#">今日统计</a></li>--%>
                <%--<li><a href="#">昨日统计</a></li>--%>
                <%--<li><a href="#">最近30天</a></li>--%>

            </ul>
        </li>
        <li>
            <a class="count_nav_mid">来源分析</a>
            <ul class="count_nav_list">
                <li><a href="/pftstis/getAllSourcePage?flag=1">全部来源</a></li>
                <li><a href="/pftstis/getAllSourcePage?flag=2">搜索引擎</a></li>
                <li><a href="/pftstis/getAllSourcePage?flag=3">搜索词</a></li>
                <li><a href="/pftstis/getAllSourcePage?flag=4">外部链接</a></li>

            </ul>

        </li>
        <li>
            <a class="count_nav_mid">页面分析</a>
            <ul class="count_nav_list">
                <li><a href="/pftstis/getVisitPage">受访页面</a></li>
                <%--<li><a href="/pftstis/getVisitHost">受访域名</a></li>--%>
                <%--<li><a href="/pftstis/getLandingPage">入口页面</a></li>--%>
                <%--<li><a href="/pftstis/getPageClk">页面点击图</a></li>--%>
                <%--<li><a href="#">页面上下游</a></li>--%>
            <%--</ul>--%>
        </li>
        <li>
            <a class="count_nav_mid">访客分析</a>
            <ul class="count_nav_list">
                <%--<li><a href="#">地域分布</a></li>--%>
                <%--<li><a href="#">系统环境</a></li>--%>
                <%--<li><a href="#">新老访客</a></li>--%>
                <%--<li><a href="#">访客属性</a></li>--%>
                <%--<li><a href="#">忠诚度</a></li>--%>
            </ul>
        </li>
        <li>
            <a class="count_nav_mid2">定制分析</a>
            <ul class="count_nav_list display" >
                <%--<li><a href="#">子目录</a></li>--%>
                <%--<li><a href="#">转化路径</a></li>--%>
                <%--<li><a href="#">指定广告跟踪</a></li>--%>
                <%--<li><a href="#">事件跟踪</a></li>--%>
                <%--<li><a href="#">自定义变量</a></li>--%>
            </ul>
        </li>
        <li>
            <a class="count_nav_mid">优化分析</a>
            <ul class="count_nav_list">
                <%--<li><a href="#">搜索词排名</a></li>--%>
                <%--<li><a href="#">百度索引量查询</a></li>--%>
                <%--<li><a href="#">网站速度诊断</a></li>--%>
                <%--<li><a href="#">升降榜</a></li>--%>
                <%--<li><a href="#">外链分析</a></li>--%>
                <%--<li><a href="#">抓取异常</a></li>--%>
                <li><a href="/pftstis/getConfigPag">监控页面设置</a>
            </ul>
        </li>
    </ul>
</div>
<input type="hidden" id="flag" value="${flag}" />
