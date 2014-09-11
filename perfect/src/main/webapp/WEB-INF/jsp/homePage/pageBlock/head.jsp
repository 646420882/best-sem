<%--
  Created by IntelliJ IDEA.
  User: SubDong
  Date: 2014/8/11
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="top">

        <div class="nav_bg">
            <img src="${pageContext.request.contextPath}/public/img/top_bg.jpg" width="100%" height="100%">
        </div>
    <div class="top_middle ">
        <div class="user_mid fl">
            <div class="user_logo fl">
                <div class="user_logo1">
                    <div class="user_img fl over">
                        <span> <img src="${pageContext.request.contextPath}/public/images/yixin_logo.png"></span>
                    </div>
                    <div class="user_text fl">
                        <p>上午，好！<a href="/configuration/"><span>${currSystemUserName}</span></a></p>

                        <div class="user_select">
                            <div class="user_name">
                                <span></span>
                            </div>
                            <div id="switchAccount" class="user_names over hides">
                                <ul id="switchAccount_ul">
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="user_logo2 fr">
                        <form name="logout" method="POST" action="/logout">
                            <input type="image" src="${pageContext.request.contextPath}/public/img/Sign_out.png"
                                   onclick="$('form[logout]').submit();"/>
                        </form>
                    </div>
                </div>
            </div>

            <div class="user_detali fl over">
                <ul>
                    <li>推广额度：<b><a href="#">${accountBalance}</a></b> 元<></li>
                    <li><span>余额预计可消费：${remainderDays}天</span><span>日预算：${accountBudget}元</span></li>

                </ul>
            </div>
        </div>
        <div class="top_mid fr over">
            <div class="logo_text fl">
                大数据智能营销
            </div>
            <div class="logo fl">
                <img src="${pageContext.request.contextPath}/public/img/logo.png">
            </div>
        </div>

    </div>

</div>
