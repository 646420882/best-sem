<%@ page import="com.perfect.core.AppContext" %>
<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2014-7-25
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Long accountId = AppContext.getAccountId();
%>
<div id="navigator" class="nav fl">
    <div class="nav_left fl  ">
        <div class="nav_mid">
            <div class="nav_under">
                <div class="tips" id="navigator_tips">
                    <span><img src='${pageContext.request.contextPath}/public/img/nav.png'/></span>
                </div>
                <ul>
                    <li class="current">
                        <a href="${pageContext.request.contextPath}/home" data-toggle="tooltip" data-placement="right"
                           title="账户全景">
                            <span class="list_1"></span>

                            <h3>账户全景</h3>
                        </a>
                    </li>
                    <li>
                        <a href="/assistant/index" data-toggle="tooltip" data-placement="right" title="推广助手">
                            <span class="list_2"></span>

                            <h3>推广助手</h3>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/keyword_group" data-toggle="tooltip"
                           data-placement="right" title="智能结构"><span class="list_3"></span>

                            <h3>智能结构</h3></a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/bidding/index" data-toggle="tooltip"
                           data-placement="right" title="智能竞价"><span class="list_4"></span>

                            <h3>智能竞价</h3></a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/reportIndex" data-toggle="tooltip"
                           data-placement="right" title="数据报告"><span class="list_5"></span>

                            <h3>数据报告</h3></a>
                    </li>

                </ul>
                <div class="help_icon" data-toggle="tooltip" data-placement="right" title="帮助中心">
                    <a href="${pageContext.request.contextPath}/qa/getPage"><img
                            src="${pageContext.request.contextPath}/public/img/best_img/Question.png" alt=""/>
                        <span class="help_text"
                              style=""></span>
                    </a>
                </div>
            </div>


        </div>
    </div>


</div>
<script> var baiduAccountId = <%=accountId%>;</script>
<%--<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>--%>
<%--<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>--%>
<script type="text/javascript" src="http://cdn.bootcss.com/json2/20140204/json2.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/commons/nav.js"></script>
<%--<script type="text/javascript"  src="${pageContext.request.contextPath}/public/js/bootstrap-daterangepicker-moment.js"></script>--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.js"></script>--%>



