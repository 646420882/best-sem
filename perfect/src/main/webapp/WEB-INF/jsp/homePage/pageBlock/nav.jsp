<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2014/7/24
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="nav fl">
    <div class="nav_left over fl">
        <div class="nav_bg">
            <img src="${pageContext.request.contextPath}/public/img/nav_bg.jpg" width="100%" height="100%">

        </div>
        <div class="user over">
            <div class="nav_bg">
                <img src="${pageContext.request.contextPath}/public/img/user_bg.png" width="100%" height="100%">
            </div>
            <div class="user_mid">
                <div class="user_logo over">
                    <div class="user_logo1 over">
                        <div class="user_img fl over">
                            <span> <img src="${pageContext.request.contextPath}/public/images/yixin_logo.png"></span>
                        </div>
                        <div class="user_text fl">
                            <p>上午，好！<span>${currSystemUserName}</span></p>
                                <div class="user_select over">
                                    <select style=" ">
                                      <option>perfect</option>
                                    </select>
                                </div>


                        </div>

                    </div>
                    <div class="user_logo2">
                        <form name="logout" method="POST" action="/logout">
                            <input type="image" src="${pageContext.request.contextPath}/public/img/Sign_out.png" onclick="$('form[logout]').submit();"/>
                        </form>
                    </div>

                </div>

                <div class="user_detali over">
                    <ul>
                        <li>推广额度：<b><a href="#">${accountBalance}</a></b> 元<>
                        <li>余额预计可消费：${remainderDays}天<>
                        <li>日预算：${accountBudget}元<>
                    </ul>

                </div>
            </div>
        </div>
        <div class="nav_mid">
            <div class="nav_under over">
                <ul>
                    <li class="current">
                        <a href="/home">
                            <span class="list1"></span>

                            <h3>帐户全景</h3>
                        </a>
                    </li>

                    <li>
                        <a href="/assistant/index">
                            <span class="list2"></span>

                            <h3>推广助手</h3>
                        </a>
                    </li>
                    <li>
                        <a href="/keyword_group"><span class="list3"></span>

                            <h3>智能结构</h3></a>
                    </li>

                    <li>
                        <a href="/bidding/index"><span class="list4"></span>

                            <h3>智能竞价</h3></a>
                    </li>

                    <li>
                        <a href="#"><span class="list5"></span>

                            <h3>数据报告</h3></a>
                    </li>

                </ul>
            </div>
        </div>
    </div>
    <div class="tips fl">
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
    $(function () {
        var navH = $(".on_title").offset().top;
        $(window).scroll(function () {
            var scroH = $(this).scrollTop();
            if (scroH >= navH) {
                $(".on_title").css({"position": "fixed", "top": "77"});
            } else {
                $(".on_title").css({"position": "static", "margin": "0 auto"});
            }
        });
        $('.nav_under ul li').click(function () {
            $(this).addClass('current').siblings().removeClass('current');
        });

    });

</script>
