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
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/census/perfectNew.js"></script>
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
                <div class="containers help_content">
                    <li class="question_help">Q：账户全景可以直接查看整月的数据吗？</li>
                    <li>A：可以，通过自定义选择时间范围查看。</li>
                    <li class="question_help">Q：账户趋势图可以同时选择多项数据吗？</li>
                    <li>A：不可以，最多只能同时选择两项数据。</li>
                    <li class="question_help">Q：为什么系统的重点关键词监控里没有数据？</li>
                    <li>A：首先，确定是否您已经在凤巢系统里添加重点关键词监控如已添加，确认是否有展现，关键词没有展现数据，不会出现在重点关键词监控里。</li>
                    <li class="question_help">Q：质量度分析模块有什么用处呢？</li>
                    <li>A：质量度是根据关键词的点击率、关键词与创意的相关性、账户整体表现（账户内其它关键词的点击率）等多个因素计算出来的。<br/>
                        百思系统质量度分析模块提供账户所有关键词的展现、点击、点击率等数据，并对不同的星级关键词进行汇总统计。便于您提高质量度，改善账户整体表现。
                    </li>
                </div>
                <div class="containers help_content  hides over">
                    <li class="question_help">Q：百度推广客户端可以实现对推广计划的哪些管理？</li>
                    <li>A：百度推广客户端支持对推广计划的添加、删除、修改、还原、查看等操作。</li>
                    <li class="question_help">Q：上传完成后，推广客户端会提示哪些物料上传成功了，哪些上传失败吗？</li>
                    <li>A：完成上传后，推广客户端将提示成功上传的信息数目，并将上传失败的内容标示在树状视图的相应位置。</li>
                    <li class="question_help">Q：什么是文字替换工具？</li>
                    <li>A：如果您需要在文字创意、关键词中替换文字，可以使用文字替换功能，选择需要编辑的文字创意、关键词，设定替换的文字即可。</li>
                    <li class="question_help">Q：如果操作有误，能否撤销上一次操作？</li>
                    <li>A：在发布更改之前，您可以通过“还原”功能，将所选物料还原到上一次与线上同步时的状态，即撤销上一次同步至今的全部更改，而不会仅撤销最后一次操作。</li>
                    <li class="question_help">Q：如何添加/更新多个文字创意？</li>
                    <li>A：在创意层级点击【批量添加/更新】按钮，可对多个创意进行批量添加/更新操作</li>
                </div>
                <div class="containers help_content hides over">
                    <li class="question_help">Q：搜客系统的拓词模块的关键词来源？</li>
                    <li>A：来自搜客智能词库和相关搜索引擎平台抓取调用，如：百度、Google、360等</li>
                    <li class="question_help">Q：关键词拓词的种子词最多可以输入多少？</li>
                    <li>100个。</li>
                    <li class="question_help">Q：关键词拓词模块做多一次可拓展多少关键词？
                    </li>
                    <li>A：没有数量限制，搜客系统根据所输入的种子词从智能词库抓取相关关键词。</li>
                    <li class="question_help">Q：关键词拓词模块提供关键词日均搜索量吗？</li>
                    <li>A：搜客关键词拓词模块提供关键词的日均搜索量和竞争激烈程度相关数据， 您可以择优选择关键词。</li>
                    <li class="question_help">Q：关键词拓词模块给出的关键词是否已分组？</li>
                    <li>A：是的，搜客系统拓展的关键词已经分组，您可以根据需求直接使用或自行调整。</li>
                    <li class="question_help">Q：行业拓词模块都包含几大行业？</li>
                    <li>A：搜客系统暂时提供5大行业关键词（电商、房产、教育、金融、旅游），后期会持续添加行业。</li>
                    <li class="question_help">Q：行业拓词模块拓展的关键词可以直接使用吗？是否已经分组？</li>
                    <li>A：系统拓展的关键词已经分计划、分单元，您可以根据需求直接下载使用或自己调整。</li>
                    <li class="question_help">Q：行业拓词模块每次能选择多个类别吗？</li>
                    <li>A：不可以，每次只能选择1个行业1个类别。</li>
                    <li class="question_help">Q：为什么我需要的关键词类别搜客行业拓词模块中没有包含？</li>
                    <li>A：搜客系统现提供的行业关键词来自搜客智能词库和相关搜索引擎平台， 您可以将您A：的需求反馈给我们，我们会及时补充更新，谢谢！</li>
                    <li class="question_help">Q：行业拓词给出的关键词有我不需要的怎么办？</li>
                    <li>A：搜客系统根据您选择的行业和类别自动抓取搜客智能词库和相关搜索引擎平台的关键词，您可以根据需求筛选关键词。</li>
                </div>
                <div class="containers help_content hides over">
                    <li class="question_help">Q：排名锁定是区分左右侧的么？</li>
                    <li>A：区分左右侧。多种竞价策略，您可以按需设置。</li>
                    <li class="question_help">Q：可以保证我的排名一直都在么？</li>
                    <li>A：通过我们的竞价模块，选择运行间隔，系统自动根据您设置的运行间隔进行排名锁定，以保证您的关键词在所需的排名范围内。</li>
                    <li class="question_help">Q：用你们的系统竞价，会不会让我们多花钱？</li>
                    <li>A：竞价系统有上限设置。您设置一个调价上限，如果系统已经运行到调价上限，但是还没有将排名调整到所需区间，那么您可以选择“自动匹配最佳排名”或“恢复账户设置“。</li>
                    <li class="question_help">Q：为什么竞价模块中没有排名？</li>
                    <li>A：竞价模块显示的排名是正在竞价中的关键词，如果您没有设置关键词竞价规则，则不会有排名显示。</li>
                    <li class="question_help">Q：智能竞价相对于手工调价有哪些优势？</li>
                    <li>A：效率高、成本低、精准出价、无须值守。</li>
                </div>
                <div class="containers help_content hides over">
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
            //        选项卡
            var $tab_li = $('.tab_menu li');
            $('.tab_menu li').click(function () {
                $(this).addClass('selected').siblings().removeClass('selected');
                var index = $tab_li.index(this);
                typepage = index + 1;
                $('div.tab_box > div').eq(index).show().siblings().hide();
            });
            $("input[name=reservation]").click(function () {
                clickdddd = $(this);
            });

    </script>

</body>
</html>