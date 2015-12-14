<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.perfect.account.SystemUserInfoVO" %>
<%@ page import="com.perfect.commons.constants.AuthConstants" %>
<%@ page import="com.perfect.core.AppContext" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Long accountId = AppContext.getAccountId();
    List<String> menuPermissions = ((SystemUserInfoVO) request.getSession().getAttribute(AuthConstants.USER_INFORMATION)).getMenuPermissions();
%>
<div id="navigator" class="nav fl">
    <div class="nav_left fl  ">
        <div class="nav_mid">
            <div class="nav_under">
                <div class="tips" id="navigator_tips">
                    <span><img src='${pageContext.request.contextPath}/public/img/nav.png'/></span>
                </div>
                <ul>
                    <c:forEach var="item" items="<%=menuPermissions%>" varStatus="status">
                        <li <c:if test="${status.first}">class="current"</c:if>>
                            <a href="${pageContext.request.contextPath}${item.split(",")[1]}"
                               data-toggle="tooltip"
                               data-placement="right"
                               title="${item.split(",")[0]}">
                                <span class="list_${status.index + 1}"></span>

                                <h3>${item.split(",")[0]}</h3>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
                <div class="help_icon" data-toggle="tooltip" data-placement="right" title="帮助中心">
                    <a href="${pageContext.request.contextPath}/qa/getPage"><img
                            src="${pageContext.request.contextPath}/public/img/best_img/Question.png" alt=""/>
                        <span class="help_text" style=""></span>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var baiduAccountId = <%=accountId%>;
</script>
<%--<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>--%>
<%--<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>--%>
<%--<script type="text/javascript"  src="${pageContext.request.contextPath}/public/js/bootstrap-daterangepicker-moment.js"></script>--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.js"></script>--%>
<script type="text/javascript" src="http://cdn.bootcss.com/json2/20140204/json2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/commons/nav.js"></script>