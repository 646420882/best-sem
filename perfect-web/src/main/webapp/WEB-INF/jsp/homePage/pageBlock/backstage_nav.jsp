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
                <li><a href="${pageContext.request.contextPath}/admin/getRedisPage">redis</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/getAccountAllState">系统帐号启用/禁用</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/addQuestions">问答添加</a></li>
            </ul>
        </div>
        <a href="${pageContext.request.contextPath}/logout" class="fr" style="color:#b4bcbf;">退出</a>
    </div>
</div>
<%--alert提示类--%>
<div class="TB_overlayBG_alert"></div>
<div class="backAlertBox" style=" width: 230px;display:none;z-index: 10001" id="baiduAccountAlertPrompt">
    <h2 id="baiduAccountAlertPromptTitle">
        <span class="alert_span_title" id="baiduAccountAlertPrompt_title" style="float: left"></span>
    </h2>
    <div class="mainlist">
        <div class="w_list03">
            <ul class="zs_set">
                <li class="current" onclick="baiduAccountAlertPrompt.hide()">确认</li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/tc.min.js"></script>
<script type="text/javascript">
    /*智能竞价中的alert提示*/
    var baiduAccountAlertPrompt = {
        show:function(content){
            $("#baiduAccountAlertPrompt_title").html(content);
            $(".TB_overlayBG_alert").css({
                display: "block", height: $(document).height()
            });/*蒙版显示*/
            $("#baiduAccountAlertPrompt").css({
                left: ($("body").width() - $("#baiduAccountAlertPrompt").width()) / 2 - 20 + "px",
                top: (document.body.clientHeight - $("#baiduAccountAlertPrompt").height()) / 2 + $(window).scrollTop() + "px",
                display: "block"
            });/*显示提示DIV*/
        },
        hide:function(){
            $(".TB_overlayBG_alert").css({
                display: "none"
            });/*蒙版显示*/
            $("#baiduAccountAlertPrompt").css({
                display: "none"
            });/*显示提示DIV*/
        }
    }
    $(function () {
        rDrag.init(document.getElementById('baiduAccountAlertPromptTitle'));
        $(".backstage_menu>ul>li>a").each(function () {
            if ($($(this))[0].href == String(window.location)) {
                $(".backstage_menu>ul>li").removeClass("current");
                $(this).parent().addClass('current');
            }
        });
    })
</script>