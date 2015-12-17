<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 14-7-25
  Time: 下午6:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>大数据智能营销</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">


    <style type="text/css">
        .title {
            width: 100%;
            height: 60px;
            background-color: #ffffff;
            border-bottom: 2px solid #CBC5D1;
        }

        .title span {
            float: left;
            margin-left: 15px;
            margin-top: 20px;
            font-size: 16px;
            font-family: "微软雅黑";
            height: 67%;
            border-bottom: 2px solid gray;
        }

    </style>

</head>
<body>
<div class="concent over">
    <div class="mid over fr">
        <div class="on_title over">
            <a href="#">
                推广助手
            </a>
            &nbsp;&nbsp;>&nbsp;&nbsp;<span>账户预警</span>
        </div>

        <div class="title"><span>消费提醒设置</span></div>
        <div><br/>
            <form action="/assistant/saveWarningRule" method="get">
                <span>百度账户</span>
                <select name="accountId">
                    <c:forEach items="${list}" var="va">
                        <option value="<c:out value="${va.menuId}"/>"><c:out value="${va.baiduUserName}"/></option>
                    </c:forEach>
                </select><br/><br/>

                <span>账户预算类型</span>
                <select name="budgetType">
                    <option value="0">不设置预算</option>
                    <option value="1">日预算</option>
                    <option value="2">周预算</option>
                </select><br/><br/>

                <span>预算金额</span><input type="text" name="budget"/><br/><br/>
                <span>预警百分率</span><input type="text" name="warningPercent"/><br/><br/>
                <span>邮件</span><input type="text" name="mails"/><span>多个邮件地址以逗号(英文)隔开</span><br/><br/>
                <span>手机</span><input type="text" name="tels"/><span>多个手机号码以逗号(英文)隔开</span><br/><br/>
                <span>是否启用</span> <select name="isEnable">
                <option value="0">不启用</option>
                <option value="1">启用</option>
            </select><br/><br/>
                <input type="submit" value="确定"/>
            </form>
        </div>


        <div class="footer">
            <p>
                <a href="#">
                    关于我们
                </a>
                |
                <a href="#">
                    联系我们
                </a>
                |
                <a href="#">
                    隐私条款
                </a>
                |
                <a href="#">
                    诚聘英才
                </a>
            </p>

            <p>
                Copyright@2013 perfect-cn.cn All Copyright Reserved. 版权所有：北京普菲特广告有限公司京ICP备***号
            </p>
        </div>
    </div>
</div>

</body>
</html>