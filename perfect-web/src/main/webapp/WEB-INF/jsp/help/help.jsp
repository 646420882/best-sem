<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 14-7-25
  Time: 下午6:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    <title>大数据智能营销</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">
    <%--    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/zTreeStyle/Normalize.css">--%>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/css/pagination/pagination.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/media.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/index.css">
    <%--<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/public/css/images/favicon.ico"/>--%>
    <script type="text/javascript" src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <script>
        var _pct = _pct || [];
        (function () {
            var hm = document.createElement("script");
            hm.src = "//t.best-ad.cn/t.js?tid=76c005e89e020c6e8813a5adaba384d7";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
    <style>
        .page2 .ajc {
            background: #ffb900;
            border: 1px solid #fab30b;
            color: #fff;
        }

    </style>
</head>
<body>
<div id="background" class="background hides"></div>
<jsp:include page="../homePage/pageBlock/head.jsp"/>

<div class="concent help over">

    <jsp:include page="../homePage/pageBlock/nav.jsp"/>
    <div class="mid  over">
        <div class="title_box">
            <div class="on_title over">
                <a href="#">
                    帮助中心
                    &nbsp;&nbsp;>&nbsp;&nbsp;<a href="/home" style="color: #8e8e8e;">账户全景</a>
                </a>
            </div>
        </div>

        <div id="tab">
            <ul class="tab_menu">
                <li class="selected">
                    账户全景
                </li>
                <li id="liClick">
                    推广助手
                </li>
                <li>
                    智能结构
                </li>
                <li>
                    智能竞价
                </li>
                <li>
                    数据报告
                </li>
            </ul>
            <div class="tab_box">
                <div class="containers help_content" id="qa0">

                </div>
                <div class="containers help_content  hides over" id="qa1">

                </div>
                <div class="containers help_content hides over" id="qa2">

                </div>
                <div class="containers help_content hides over" id="qa3">

                </div>
                <div class="containers help_content hides over" id="qa4">
                    <li class="question_help">Q：如何找到高质量、效果好的关键词进行推广？</li>
                    <li>A：需要在数据报告模块中定期获取数据，对其监控并分析，建立衡量的标准，为后期优化提供数据支持。</li>
                    <li class="question_help">Q：平均点击价格（CPC）过高如何进行优化？</li>
                    <li>A：在数据报告模块中，直接生成报告。平均点击价格（CPC）受到两个因素的影响，质量度、排名与竞争度。所以降低平均点击价格（CPC）需要在这两个方面考虑。</li>
                    <li class="question_help">Q：数据报告中的数据指标是什么关系？</li>
                    <li>
                        A：假设展现量为1000，其中产生了50个点击，点击率就是50/1000*100%=5%，而其中到达网站并且能够完全打开网页的访问数有40个，在网站上完成转化行为的有2个，那么转化率就是2/50*100%=4%。若平均点击价格（CPC）为1元，那么平均转化价格（CPA）就是1*50/2=25元。
                    </li>
                    <li><img
                            src="${pageContext.request.contextPath}/public/images/help_img.png"/></li>
                    <li class="question_help">Q：数据报告是否可以下载使用？</li>
                    <li>A：可以，不仅系统提供CSV格式，同时对于明细数据报告还为您提供可下载的图表格式。为您一站式打造各类数据报告。</li>
                    <li class="question_help">Q：我能利用数据报告做什么？</li>
                    <li>A：数据报告可以监控您每天的推广情况，可以针对不同的数据表现进行分析，以提升您的推广效果。</li>
                    <li></li>
                </div>
            </div>
        </div>

        <jsp:include page="../homePage/pageBlock/footer.jsp"/>
    </div>

    <!-- javascript -->
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://cdn.bootcss.com/jqueryui/1.11.2/jquery-ui.min.js"></script>
    <script type="text/javascript" src="http://cdn.bootcss.com/echarts/2.1.10/echarts-all.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/public/js/daterangepicker.jQuery.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/public/js/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="http://cdn.bootcss.com/json2/20140204/json2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/map.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/public/js/keyword/keywordQuality.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/untils/untils.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/public/js/pagination/jquery.pagination.js"></script>
    <script type="text/javascript">
        function query(a) {
            $.ajax({
                url: "/qa/findAnswers",
                dataType: "json",
                success: function (data) {
                    console.log(data);
                    $("#qa" + a).empty();
                    var html_account = "";
                    $.each(data.rows, function (i, item) {
                        if (item.questionType == a) {
                            html_account = "<li class='question_help' style='color: " + item.fontColor + "'>Q:" + item.questions + "</li><li>A:" + item.answers + "</li>"
                            $("#qa" + a).append(html_account);
                        }
                    });
                }
            });
        }
        query(0);
        //        选项卡
        var $tab_li = $('.tab_menu li');
        $('.tab_menu li').click(function () {
            $(this).addClass('selected').siblings().removeClass('selected');
            var index = $tab_li.index(this);
            query(index)
            typepage = index + 1;
            $('div.tab_box > div').eq(index).show().siblings().hide();
        });
        $("input[name=reservation]").click(function () {
            clickdddd = $(this);
        });
    </script>

</body>
</html>