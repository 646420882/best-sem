<%@ page import="com.perfect.app.web.WebUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2014-7-25
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Long accountId = 0l;
    if (request != null) {
        accountId = WebUtils.getAccountId(request);
    }
%>
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
                <div class="user_logo">
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
                            <span class="list_1"></span>

                            <h3>帐户全景</h3>
                        </a>
                        <span class='nav_input1'></span>
                    </li>
                    <li>
                        <a href="/assistant/index">
                            <span class="list_2"></span>

                            <h3>推广助手</h3>
                        </a>
                    </li>
                    <li>
                        <a href="/keyword_group"><span class="list_3"></span>

                            <h3>智能结构</h3></a>
                    </li>
                    <li>
                        <a href="/bidding/index"><span class="list_4"></span>

                            <h3>智能竞价</h3></a>
                    </li>
                    <li>
                        <a href="/reportIndex"><span class="list_5"></span>

                            <h3>数据报告</h3></a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="tips fl">
        <span class="nav_input hides"></span>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript">

    var baiduAccountId = <%=accountId%>;

    var loadBaiduAccount = function () {
        $.getJSON("/account/getAllBaiduAccount",
                {},
                function (data) {
                    var options, results = data.rows;
                    if (results != null && results.length > 0) {
                        var lis = "";
                        $.each(results, function (i, item) {
                            var _item = item.baiduUserName.substring(6);
                            if (baiduAccountId == item.id) {
                                $('.user_name span').html(_item);
                            }
                            lis += "<li value='" + item.id + "'>" + _item + "</li>";
                        });
                        $("#switchAccount_ul").empty();
                        $("#switchAccount_ul").append(lis);
                    }
                });
    };

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
            $(this).siblings().removeClass('current').find("span").remove(".nav_input1");
            if ($(this).find(".nav_input1").length == 1) {
                return false;
            }
            $(this).addClass('current').append("<span class='nav_input1'></span>");
        });
        $(".tips").click(function () {
            if ($(".nav_left").css("display") == "none") {//隐藏
                $(".nav_left").slideDown(600);
                $(".tips").css("position", "relative");
                $(".concent").css("width", "85%");
                $(".top").css("width", "85%");
                $(".nav_input").css("display", "none");
                $(".nav").css("z-index", "200");
            }
            else {
                $(".nav_left").hide();
                $(".tips").css("position", "fixed");
                $(".concent").css("width", "99.5%");
                $(".top").css("width", "99.5%");
                $(".nav_input").css("display", "block");
                $(".nav").css("z-index", "111");
            }
        });
        $(".nav_input1").click(function () {
            if ($(".nav_left").css("display") == "none") {//隐藏
                $(".nav_left").slideDown(600);
                $(".tips").css("position", "relative");
                $(".concent").css("width", "85%");
                $(".top").css("width", "85%");
                $(".nav_input").css("display", "none");
                $(".nav").css("z-index", "200");
            }
            else {
                $(".nav_left").hide();
                $(".tips").css("position", "fixed");
                $(".concent").css("width", "99.5%");
                $(".top").css("width", "99.5%");
                $(".nav_input").css("display", "block");
                $(".nav").css("z-index", "111");
            }
        });
        loadBaiduAccount();
        $('.user_name').click(function () {
            if ($("#switchAccount").css("display") == "none") {//隐藏
                $(this).next('#switchAccount').show();
                $("#switchAccount ").mouseleave(function () {
                    $("#switchAccount").css("display", "none");
                });
                $('#switchAccount li').click(function () {
                    $('.user_name span').html($(this).text());
                    var _accountId = $(this).val();
                    $('#switchAccount').hide();
                    $.ajax({
                        url: '/account/switchAccount',
                        type: 'POST',
                        async: false,
                        dataType: 'json',
                        data: {
                            "accountId": _accountId
                        },
                        success: function (data, textStatus, jqXHR) {
                            if (data.status != null && data.status == true) {
                                //location.replace(location.href);
                                window.location.reload(true);
                            }
                        }
                    })
                });
            }
            else {
                $("#switchAccount").hide();
            }
        });
    });

</script>