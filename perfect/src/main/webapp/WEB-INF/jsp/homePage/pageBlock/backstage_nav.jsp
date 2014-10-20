<%--
  Created by IntelliJ IDEA.
  User: guochunyan
  Date: 2014/10/10
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="backstage_nav over">
    <div class="backstage_wd mid over">
        <div class="backstage_logo fl">
            <img src="${pageContext.request.contextPath}/public/img/logo.png">
        </div>
        <div class="backstage_text fl">
            后台管理系统
        </div>
        <div class="backstage_menu fl">
            <ul>
                <li class="current"><a href="${pageContext.request.contextPath}/admin/pullPage">数据拉取</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/biddingConsole">智能竞价</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/lexiconConsole">词库管理</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/getAccountPage">帐号审核</a></li>
            </ul>
        </div>
        <a href="${pageContext.request.contextPath}/logout" class="fr" style="color:#b4bcbf;">退出</a>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".backstage_menu>ul>li>a").each(function () {
            if ($($(this))[0].href == String(window.location)) {
                $(".backstage_menu>ul>li").removeClass("current");
                $(this).parent().addClass('current');
            }
        });
    })
</script>