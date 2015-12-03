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
                <div class="tips">
                    <span id="navigator_tips"><img src='${pageContext.request.contextPath}/public/img/nav.png'/></span>
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
                <div style="text-align: center;position: absolute;bottom: 111px;left: 30%">
                    <a href="${pageContext.request.contextPath}/qa/getPage" data-toggle="tooltip"
                       data-placement="right" title="帮助中心"><img
                            src="${pageContext.request.contextPath}/public/img/best_img/Question.png" alt=""/>
                        <span class="help_text"
                              style="display: block;float: right;padding: 2px 0 0 4px;color: #a7b1c2"></span>
                    </a>
                </div>
            </div>


        </div>
    </div>


</div>
<%--<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>--%>
<%--<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>--%>
<script type="text/javascript" src="http://cdn.bootcss.com/json2/20140204/json2.min.js"></script>
<%--<script type="text/javascript"  src="${pageContext.request.contextPath}/public/js/bootstrap-daterangepicker-moment.js"></script>--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.js"></script>--%>
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
        }
    });

    $(function () {
        //取屏幕宽度
        var nav = "<img src='${pageContext.request.contextPath}/public/img/nav.png'/>"
        var nav_open = "<img src='${pageContext.request.contextPath}/public/img/nav_open.png'/>"
        $('[data-toggle="tooltip"]').tooltip()
        $('#navigator_tips').click(function (_this) {
            /*        $('.concent').toggleClass('nav_hide', 1000, "easeOutSine");*/
            if ($(".concent").hasClass("nav_hide")) {
                $(".concent").removeClass("nav_hide");
                $('[data-toggle="tooltip"]').tooltip();
                $("#navigator_tips").html(nav);
                $(".help_text").html("");
            } else {
                $(".concent").addClass("nav_hide");
                $("#navigator_tips").html(nav_open);
                $('[data-toggle="tooltip"]').tooltip('destroy');
                $(".help_text").html("帮助中心");
            }
        });

        function ChangeAccountajax(_accountId) {
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
        }

        if (parseInt(baiduAccountId) != -1) {
            loadBaiduAccount();
        }
        document.onclick = function () {
            $("#switchAccount").hide();
            $("#filterSearchTemplate").hide();
            $(".remove").remove();
        }

        $("#switchAccount").click(function (e) {
            var ev = e || window.event;
            if (ev.stopPropagation) {
                ev.stopPropagation();
            }
            else if (window.event) {
                window.event.cancelBubble = true;//兼容IE
            }
        });
        $('.user_name').click(function (e) {
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
                    ChangeAccountajax(_accountId);

                });
                $('#searchCount').keydown(function (e) {
                    if (e.keyCode == 13) {
                        $('.user_name span').html($(this).val());
                        var _accountId = $('#searchCount').attr("card");
                        $('#switchAccount').hide();
                        ChangeAccountajax(_accountId);
                    }
                });
                var ev = e || window.event;
                if (ev.stopPropagation) {
                    ev.stopPropagation();
                }
                else if (window.event) {
                    window.event.cancelBubble = true;//兼容IE
                }
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
