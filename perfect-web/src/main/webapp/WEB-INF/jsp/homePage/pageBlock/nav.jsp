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
    <div class="nav_left fl over  ">
        <div class="nav_bg">
            <img src="${pageContext.request.contextPath}/public/img/nav_bg.jpg" width="100%" height="100%">
        </div>
        <div class="nav_mid">
            <div class="nav_under over">
                <ul>
                    <li class="current">
                        <a href="${pageContext.request.contextPath}/home">
                            <span class="list_1"></span>

                            <h3>帐户全景</h3>
                        </a>
                    </li>
                    <li>
                        <a href="/assistant/index">
                            <span class="list_2"></span>

                            <h3>推广助手</h3>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/keyword_group"><span class="list_3"></span>

                            <h3>智能结构</h3></a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/bidding/index"><span class="list_4"></span>

                            <h3>智能竞价</h3></a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/reportIndex"><span class="list_5"></span>

                            <h3>数据报告</h3></a>
                    </li>

                </ul>
                <div style="text-align: center;position: absolute;bottom: 111px;left: 30%">
                    <img src="${pageContext.request.contextPath}/public/img/best_img/Question.png" alt=""/>
                    <a href="${pageContext.request.contextPath}/qa/getPage"><span class="list_5"
                                                                                  style="display: block;float: right;padding: 2px 0 0 4px;color: #000000">帮助中心</span>
                    </a>
                </div>
            </div>


        </div>
    </div>
    <div id="navigator_tips" class="tips fl" title="点击隐藏导航">
        <span class="nav_input hides"></span>
    </div>
</div>
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
                        var index = results[0].baiduUserName.length;
                        $.each(results, function (i, item) {
                            var _item = item.baiduRemarkName;
                            if (_item == undefined) _item = item.baiduUserName.substring(0, (i > 0 ? index - 3 : index)) + (item.baiduUserName.length > index ? "..." : "");
                            if (baiduAccountId == item.id) {
                                $('.user_name span').html(_item);
                            }
                            lis += "<li  title='" + item.baiduUserName + "' value='" + item.id + "'>" + _item + "</li>";
                        });
                        $("#switchAccount_ul").empty();
                        $("#switchAccount_ul").append(lis);
                    }
                });
    };
    $(".nav_under>ul>li>a").each(function () {
        if ($($(this))[0].href == String(window.location)) {
            $(".nav_under>ul>li").removeClass("current");
            $(this).parent().addClass('current');
            $(this).siblings().removeClass('current').find("span").remove(".nav_input1");
            $(this).addClass('current').append("<span class='nav_input1' id='nav_input1'></span>");
        }
    });
    $(function () {
        //取屏幕宽度
        /*     var top_width = $(document.body).width();
         $(".top").css("width", top_width);
         $(window).resize(function () {
         top_width = $(document.body).width();
         $(".top").css("width", top_width);
         });*/
        $(".help .nav_left").hide();
        $(".help .nav").css({"width": "8px", "z-index": "111"});
        $(".help .mid").css("padding-left", "8px");
        $(".help .tips").attr("title", "点击显示导航")
        $(".help .nav_input").css("display", "block");
//        $(".help .nav_left").css({"width": "8px","padding-left": "8px","position": "fixed","z-index": "111","display":"block","title": "点击显示导航"});
        function NavClick() {
            if ($(".nav_left").css("display") == "none") {//隐藏
                $(".nav_left").show();
                $(".tips").css("position", "relative");
                $(".nav").css("z-index", "200");
                $(".nav").css("width", "180px");
                $(".nav_input").css("display", "none");
                $(" .tips").attr("title", "点击隐藏导航")
                $(".mid").css("padding-left", "180px");
            }
            else {
                $(".nav_left").hide();
                $(".nav").css({"width": "8px", "z-index": "111"});
                $(".mid").css("padding-left", "8px");
                $(".nav_input").css("display", "block");
                $(".tips").attr("title", "点击显示导航")
            }
        }


        $(".tips").on("click", function () {
            NavClick()
        });

        $(".nav_input1").click(function () {
            $(this).parent().removeAttr('href');
            $(".nav_input1").attr('title', "点击隐藏导航");
            NavClick()
        });
        if (parseInt(baiduAccountId) != -1) {
            loadBaiduAccount();
        }
        $('.user_name').click(function () {
            if ($("#switchAccount").css("display") == "none") {//隐藏
                $(this).next('#switchAccount').show();

//                $("#switchAccount").mouseleave(function () {
//                    $("#switchAccount").css("display", "none");
//                    setTimeout(function () {
//                        $("#switchAccount").css("display", "none");
//                    }, 5000);
//                });

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

    });
    /*    $(document).ready(function () {
     $(".on_title").pin();
     });*/

</script>
