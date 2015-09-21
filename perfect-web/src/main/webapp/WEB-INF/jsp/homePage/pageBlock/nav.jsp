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
    <div class="totle_menu fl" id="menu">
        <img src="${pageContext.request.contextPath}../public/img/total_menu.png">

    </div>
    <div class="nav_left fl over ">
        <ul>
            <li class="current">
                <a href="${pageContext.request.contextPath}/home">
                    <img src="../public/img/home.png">
                    <%--  <h3>帐户全景</h3>--%>
                </a>
            </li>
            <li>
                <a href="/assistant/index">
                    <img src="../public/img/assistant.png">
                    <%-- <h3>推广助手</h3>--%>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/keyword_group">
                    <img src="../public/img/keyword.png">
                    <%--<h3>智能结构</h3>--%></a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/bidding/index" style="padding-top:8px;"><img src="../public/img/bidding.png">

                    <%--<h3>智能竞价</h3>--%></a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/reportIndex" style="padding-top:9px;"><img src="../public/img/reportindex.png">

                    <%-- <h3>数据报告</h3>--%></a>
            </li>
        </ul>

    </div>
    <%--    <div id="navigator_tips" class="tips fl" title="点击隐藏导航">
            <span class="nav_input hides"></span>
        </div>--%>
</div>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/json2/20140204/json2.min.js"></script>
<script type="text/javascript">
    var baiduAccountId = <%=accountId%>;
    var loadBaiduAccount = function () {
        $.getJSON("/account/getAllBaiduAccount",
                {},
                function (data) {
                    var results = data.rows;
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
    $(".nav_left>ul>li>a").each(function () {
        if ($($(this))[0].href == String(window.location)) {
            $(".nav_left>ul>li").removeClass("current");
            $(this).parent().addClass('current');
            /*  $(this).siblings().removeClass('current').find("span").remove(".nav_input1");
             $(this).addClass('current').append("<span class='nav_input1' id='nav_input1'></span>");*/
        }
    });
    $(function () {
        //取屏幕宽度
        var mid_width = $(document.body).width() - 60;
        var max_width = $(document.body).width() - 8;
   /*     $("#menu").on("click", function () {
           $(".nav_left").css("display", "none");
            $(".mid").css("padding-left", "0px");
        });*/
        var top_width = $(document.body).width();
        $(".top").css("width", top_width);
        $(window).resize(function () {
            top_width = $(document.body).width();
            $(".top").css("width", top_width);
        });
        });
        if (parseInt(baiduAccountId) != -1) {
            loadBaiduAccount();
        }
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

        $(document).ajaxComplete(function (event, req, settings) {
            if (req.getResponseHeader("sessionStatus") == "timeout") {
                window.location = "/login";
            }

    });
    /*    $(document).ready(function () {
     $(".on_title").pin();
     });*/

</script>
