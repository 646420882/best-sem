<%--
Created by IntelliJ IDEA.
User: baizz
Date: 2014-08-07
Time: 上午11:28
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title></title>
    <meta charset="utf-8">
    <meta id="viewport" name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/media.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/respond.js"></script>
    <style type="text/css">
        .list2 table tr td ul li {
            width: 12%;
        }

        .download {
            margin-left: 20px;
        }

        .download a {
            margin-right: 20px;
        }
    </style>
</head>
<body>
<div id="background" class="background"></div>
<div id="progressBar" class="progressBar">数据加载中，请稍等...</div>
<div id="progressBar1" class="progressBar">正在生成数据，请稍等...</div>
<jsp:include page="../homePage/pageBlock/head.jsp"/>
<div class="concent over">
<jsp:include page="../homePage/pageBlock/nav.jsp"/>
<div class="mid over">
<div class="on_title over">
    <a href="#">
        账户全景
    </a>
    &nbsp;&nbsp;&gt;&nbsp;&nbsp;<span>账户分析</span>
</div>
<div id="tab">
<ul class="tab_menu" id="tab_menu">
    <li class="selected">关键词拓词</li>
    <li>创意推荐</li>
</ul>
<div class="tab_box">
<div class="containers over">
    <div class="list01_top over"><Span>关键词拓词</Span> <a href="#" class="question"></a></div>
    <div class="keyword over">
        <div class="keyword_right over">
            <div class="k_r_top over">
                <ul class="tab_menu2 over">
                    <li class="current"><a>词根拓词</a></li>
                    <li><a>行业拓词</a></li>
                </ul>
                <div class="table_concent2 over">
                    <div class="k_r_top2 over">
                        <div class="k_r_middle over">
                            <div class="k_top2_text fl">
                                <div class="k_top2_text1"><textarea id="textarea1"
                                                                    style="overflow:auto; resize: none"></textarea>
                                </div>
                                <p>可输入词根100/100</p>
                                <a href="javascript: findWordFromBaidu();" class="become2">开始拓词</a>
                            </div>
                            <div class="K_top2_detali fr over">
                                <div class="k_top2_detali2 over">
                                    <div class="list01_top2 over">
                                    </div>
                                    <ul>
                                        <li>· 抓取搜索引擎先关搜索结果：baidu/google/sougo/soso/bing</li>
                                        <li>· 普菲特智能词库</li>
                                    </ul>
                                </div>
                                <%--<div class="k_top2_detali2 over">
                                <div class="list01_top2 over">
                                <span>智能过滤</span>
                                <a href="#" class="question"></a>
                                </div>
                                <ul>
                                <li><input type="checkbox">&nbsp;&nbsp;搜索引擎（baidu，google）APP</li>
                                <li><input type="checkbox">&nbsp;&nbsp;抓取搜索引擎先关搜索结果：baidu/google/sougo/soso/bing
                                </li>
                                </ul>
                                </div>--%>
                            </div>
                        </div>
                        <div class="k_r_under over">
                            <div class="download over ">
                                <%--<a class="fr" href="javascript: save1Keyword();">保存</a>--%>
                                <a class="fr" href="javascript: downloadCSV();">下载全部</a>
                            </div>
                            <div class="list3 over">
                                <table border="0" cellspacing="0">
                                    <thead>
                                    <tr class="list2_top">
                                        <td><span>分组</span></td>
                                        <td><span>种子词</span></td>
                                        <td><span>关键词</span></td>
                                        <td><span>日均搜索量</span><b>
                                            <p>
                                                <input type="button"
                                                       onclick="javascript:fieldName = 'dsQuantity';sort = 1;findWordFromBaidu();"
                                                       class="one">
                                            </p>

                                            <p>
                                                <input type="button"
                                                       onclick="javascript:fieldName = 'dsQuantity';sort = -1;findWordFromBaidu();"
                                                       class="two">
                                            </p></b></td>
                                        <td><span>竞争激烈程度</span><b>
                                            <p>
                                                <input type="button"
                                                       onclick="javascript:fieldName = 'competition';sort = 1;findWordFromBaidu();"
                                                       class="one">
                                            </p>

                                            <p>
                                                <input type="button"
                                                       onclick="javascript:fieldName = 'competition';sort = -1;findWordFromBaidu();"
                                                       class="two">
                                            </p></b></td>
                                        <%--<td><span>一级推荐理由</span></td>--%>
                                        <%--<td><span>二级推荐理由</span></td>--%>
                                    </tr>
                                    </thead>
                                    <tbody id="tbody1">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="page2">
                            <a href="javascript:toPrevPage();">上一页</a>
                            <a onclick="javascript:startPer = 0;endPer = 10;loadPerformance()" cname="nameDet"
                               class="ajc" href="javascript:">1</a>
                            <a href="javascript:toNextPage();">下一页</a><span
                                style="margin-right:10px;">跳转到 <input id="pageNumber1" type="text" class="price"></span>&nbsp;&nbsp;<a
                                href="javascript:toAnyPage();" class='page_go'> GO</a>
                            <a href="#" class='page_go'><span>共计</span><span><b
                                    id="totalPage1"></b></span><span>页</span></a>
                        </div>
                    </div>
                    <div class="k_r_top2 hides over">
                        <div class="k_r_middle over">
                            <div class="k_top2_text fl">
                                <div class="k_top2_text1">
                                    <select id="trade">
                                        <option selected="selected" value="">请选择行业</option>
                                        <option value="电商">电商</option>
                                        <option value="房产">房产</option>
                                        <option value="教育">教育</option>
                                        <option value="金融">金融</option>
                                        <option value="旅游">旅游</option>
                                    </select>
                                    <select id="category">
                                    </select>
                                </div>
                                <a href="javascript:findWordFromSystem();" class="become2">开始拓词</a>
                            </div>
                            <div class="K_top2_detali fr over">
                                <div class="k_top2_detali2 over">
                                    <div class="list01_top2 over">
                                        <span>拓词来源</span>
                                        <a href="#" class="question"></a>
                                    </div>
                                    <ul>
                                        <li>· 抓取搜索引擎先关搜索结果：baidu/google/sougo/soso/bing</li>
                                        <li>· 普菲特智能词库</li>
                                    </ul>
                                </div>
                                <%--<div class="k_top2_detali2 over">
                                <div class="list01_top2 over">
                                <span>智能过滤</span>
                                <a href="#" class="question"></a>
                                </div>
                                <ul>
                                <li><input type="checkbox">&nbsp;&nbsp;搜索引擎（baidu，google）APP</li>
                                <li><input type="checkbox">&nbsp;&nbsp;抓取搜索引擎先关搜索结果：baidu/google/sougo/soso/bing
                                </li>
                                </ul>
                                </div>--%>
                            </div>
                        </div>
                        <div class="k_r_under over">
                            <div class="download over ">
                                <%--<a class="fr" href="javascript: save2Keyword();">保存</a>--%>
                                <a class="fr" href="javascript: downloadCSV();">下载全部</a>
                            </div>
                            <div class="list3 over">
                                <table border="0" cellspacing="0">
                                    <thead>
                                    <tr class="list2_top">
                                        <td><span>行业</span></td>
                                        <td><span>计划</span></td>
                                        <td><span>单元</span></td>
                                        <td><span>关键词</span></td>
                                    </tr>
                                    </thead>
                                    </thead>
                                    <tbody id="tbody2">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="page2">
                            <a href="javascript:toPrevPage();">上一页</a>
                            <a onclick="javascript:startPer = 0;endPer = 10;loadPerformance()" cname="nameDet"
                               class="ajc" href="javascript:">1</a>
                            <a href="javascript:toNextPage();">下一页</a><span
                                style="margin-right:10px;">跳转到 <input id="pageNumber2" type="text" class="price"></span>&nbsp;&nbsp;<a
                                href="javascript:toAnyPage();" class='page_go'> GO</a>
                            <a href="#" class='page_go'><span>共计</span><span><b
                                    id="totalPage2"></b></span><span>页</span></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="containers over hides">
    <div class="list01_top over"><span>创意推荐</span> <a class="question" href="#"></a><b>输入关键词，系统为您推荐有限创意和优秀词根；关键词之间的相关性会影响推荐创意的质量。</b><a
            href="#">同步账户</a></div>
    <div class="originality over">
        <div class="originality_left fl">
            <ul>
                <li><input type="radio" name="originality"> 选择单元词</li>
                <li><span>计划：</span><select>
                    <option>sem1999</option>
                </select></li>
                <li><span>单元：</span><select>
                    <option>sem1999</option>
                </select></li>
                <li><textarea></textarea></li>
            </ul>
        </div>
        <div class="originality_right fl">
            <ul>
                <li><input type="radio" name="originality"> 选择单元词</li>
                <li><textarea></textarea></li>
                <li><span class="fr">1/20行</span></li>
            </ul>
        </div>
    </div>
    <div class="riginality_middle over">
<span>选择创意来源：<select>
    <option>河北</option>
</select></span>
        <span><a class="become2" href="javascript: findWordFromBaidu();">智能推荐</a></span>
    </div>
    <div class="riginality_under over">
        <div class="r_under_left fl">
            <div class="r_under_top over">
                <h3 class="fl">推荐创意</h3>
                <a href="#" class="fr" id="bulid">新建创意</a>
            </div>
            <ul>
                <li>
                    <div><img src="${pageContext.request.contextPath}/public/images/shuju.jpg"></div>
                    <div>
<span class="fr">
<a href="#">置顶 </a>|<a href="#" class="showbox">编辑</a> |<a href="#">
    删除</a>
</span>
                    </div>
                </li>
                <li>
                    <div><img src="${pageContext.request.contextPath}/public/images/shuju.jpg"></div>
                    <div>
<span class="fr">
<a href="#">置顶 </a>|<a href="#" class="showbox">编辑</a> |<a href="#">
    删除</a>
</span>
                    </div>
                </li>
                <li class="last">
                    <div><img src="${pageContext.request.contextPath}/public/images/shuju.jpg"></div>
                    <div>
<span class="fr">
<a href="#">置顶 </a>|<a href="#" class="showbox">编辑</a> |<a href="#">
    删除</a>
</span>
                    </div>
                </li>
            </ul>
        </div>
        <div class="r_under_right fl">
            <div class="r_under_top over">
                <ul>
                    <li><h3>推荐词根</h3></li>
                    <li><h3>词频占比</h3></li>
                </ul>
            </div>
            <div class="r_under_bottom over ">
                <ul>
                    <li><span>搜索</span><b>8%</b></li>
                    <li><span>搜索</span><b>8%</b></li>
                    <li><span>搜索</span><b>8%</b></li>
                    <li><span>搜索</span><b>8%</b></li>
                    <li><span>搜索</span><b>8%</b></li>
                    <li><span>搜索</span><b>8%</b></li>
                    <li><span>搜索</span><b>8%</b></li>
                    <li><span>搜索</span><b>8%</b></li>
                    <li><span>搜索</span><b>8%</b></li>
                    <li class="last"><span>搜索</span><b>8%</b></li>
                </ul>
            </div>
        </div>
    </div>
</div>
</div>
</div>
<jsp:include page="../homePage/pageBlock/footer.jsp"/>
</div>
</div>
<!------------编辑创意弹窗-------------->
<div class="TB_overlayBG"></div>
<div class="box" id="riginality_editor" style="display:none;">
    <h2 id="riginality_editor1">编辑创意<a href="#" class="close">关闭</a></h2>

    <div class="mainlist2 over">
        <div class="riginality_editor1 fl">
            <div class="mainlist">
                <ul>
                    <li>
                        <h3>标题：</h3>
                        <dl><input type="text" class="r_input" placeholder="{sem搜索搜索引擎营销}，17个……"><span>27/50</span></dl>
                        <dl><input type="button" class="r_input2" value="{}插入通配符"></dl>
                    </li>
                    <li>
                        <h3>描述1：</h3>
                        <dl><textarea></textarea><span>27/50</span></dl>
                        <dl><input type="button" class="r_input2" value="{}插入通配符"></dl>
                    </li>
                    <li>
                        <h3>描述2：</h3>
                        <dl><textarea></textarea><span>27/50</span></dl>
                        <dl><input type="button" class="r_input2" value="{}插入通配符"></dl>
                    </li>
                    <li>
                        <h3>访问URL：</h3>
                        <dl><textarea></textarea><span>27/50</span></dl>
                        <dl><input type="button" class="r_input2" value="{}插入通配符"></dl>
                    </li>
                    <li>
                        <h3>显示URL：</h3>
                        <dl><input type="text" class="r_input" placeholder="{sem搜索搜索引擎营销}，17个……"><span>27/50</span></dl>
                    </li>
                </ul>
            </div>
            <div class="main_bottom">
                <div class="w_list03">
                    <ul>
                        <li class="current">保存</li>
                        <li>保存并上传</li>
                        <li class="close">取消</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="riginality_editor2 fl">
            <div class="tuijian over">
                <h3>推荐词根<span>|</span> 词頻占比</h3>
                <ul>
                    <li>搜索<span>8%</span></li>
                    <li>营销<span>8%</span></li>
                    <li>推广<span>8%</span></li>
                    <li>baidu<span>8%</span></li>
                    <li>sem<span>8%</span></li>
                    <li>去看<span>8%</span></li>
                    <li>优化<span>8%</span></li>
                    <li>全国<span>8%</span></li>
                    <li>百度<span>8%</span></li>
                    <li>seo<span>8%</span></li>
                </ul>
            </div>
            <div class="tuijian_under over">
                <ul>
                    <li>
                        <p>左侧推广连接位预览：</p>

                        <div>
                            <img src="${pageContext.request.contextPath}/public/images/shuju.jpg">
                        </div>
                    </li>
                    <li>
                        <p>左侧推广连接位预览：</p>

                        <div>
                            <img src="${pageContext.request.contextPath}/public/images/shuju.jpg">
                        </div>
                    </li>
                    <li>
                        <p>右侧推广位预览：：</p>

                        <div>
                            <img src="${pageContext.request.contextPath}/public/images/shuju.jpg">
                        </div>
                    </li>
                </ul>
<span><a class="become2 fl" href="javascript: findWordFromBaidu();">评估匹配度</a><a class="question"
                                                                                href="#"></a></span>
            </div>
        </div>
    </div>
</div>
<!------------新建创意-------------->
<div class="TB_overlayBG"></div>
<div class="box" id="new_riginality" style="display:none;">
    <h2 id="new_riginality2">新建创意<a href="#" class="close">关闭</a></h2>

    <div class="mainlist2 over">
        <div class="riginality_editor1 fl">
            <div class="mainlist">
                <ul>
                    <li>
                        <h3>标题：</h3>
                        <dl><input type="text" class="r_input" placeholder="{sem搜索搜索引擎营销}，17个……"><span>27/50</span></dl>
                        <dl><input type="button" class="r_input2" value="{}插入通配符"></dl>
                    </li>
                    <li>
                        <h3>描述1：</h3>
                        <dl><textarea></textarea><span>27/50</span></dl>
                        <dl><input type="button" class="r_input2" value="{}插入通配符"></dl>
                    </li>
                    <li>
                        <h3>描述2：</h3>
                        <dl><textarea></textarea><span>27/50</span></dl>
                        <dl><input type="button" class="r_input2" value="{}插入通配符"></dl>
                    </li>
                    <li>
                        <h3>访问URL：</h3>
                        <dl><textarea></textarea><span>27/50</span></dl>
                        <dl><input type="button" class="r_input2" value="{}插入通配符"></dl>
                    </li>
                    <li>
                        <h3>显示URL：</h3>
                        <dl><input type="text" class="r_input" placeholder="{sem搜索搜索引擎营销}，17个……"><span>27/50</span></dl>
                    </li>
                </ul>
            </div>
            <div class="main_bottom">
                <div class="w_list03">
                    <ul>
                        <li class="current">确认</li>
                        <li class="close">取消</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="riginality_editor2 fl">
            <div class="tuijian_under over">
                <ul>
                    <li>
                        <p>推广位预览：</p>

                        <div>
                            <img src="${pageContext.request.contextPath}/public/images/shuju.jpg">
                        </div>
                    </li>
                    <li>
                        <p>推广连接位预览：</p>

                        <div>
                            <img src="${pageContext.request.contextPath}/public/images/shuju.jpg">
                        </div>
                    </li>
                    <li>
                        <p>右侧推广位预览：：</p>

                        <div>
                            <img src="${pageContext.request.contextPath}/public/images/shuju.jpg">
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-ui-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/tc.min.js"></script>
<script type="text/javascript">
var type = 1; //1, baidu; 2, perfect
var krFileId;
var fieldName = "dsQuantity";
var sort = -1;
var skip = 0;
var limit = 10;
var total = 0;
var _trade;
var _category;

window.onload = function () {
    rDrag.init(document.getElementById('riginality_editor1'));
    rDrag.init(document.getElementById('new_riginality2'));
};

//loading
var ajaxbg = $("#background,#progressBar");
$("#progressBar1").hide();
ajaxbg.hide();
$(document).ajaxStart(function () {
    ajaxbg.show();
});
$(document).ajaxStop(function () {
    ajaxbg.fadeOut(1500);
});

$(function () {
    $(".showbox").click(function () {
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $("#riginality_editor").css({
            left: ($("body").width() - $("#riginality_editor").width()) / 2 - 20 + "px",
            top: ($(window).height() - $("#riginality_editor").height()) / 2 + ($(window).scrollTop() - 153) + "px",
            display: "block"
        });
    });
    $("#bulid").click(function () {
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $("#new_riginality").css({
            left: ($("body").width() - $("#new_riginality").width()) / 2 - 20 + "px",
            top: ($(window).height() - $("#new_riginality").height()) / 2 + ($(window).scrollTop() - 153) + "px",
            display: "block"
        });
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box ").css("display", "none");
    });
    var $tab_li = $('#tab_menu li');
    $('#tab_menu li').click(function () {
        $(this).addClass('selected').siblings().removeClass('selected');
        var index = $tab_li.index(this);
        $('.containers').eq(index).show().siblings().hide();
    });
    var $tab_li2 = $('.tab_menu2 li');
    $('.tab_menu2 li').click(function () {
        $(this).addClass('current').siblings().removeClass('current');
        var index = $tab_li2.index(this);
        $('div.table_concent2 > div').eq(index).show().siblings().hide();
    });
    $("#trade").change(function () {
        var trade = $("#trade option:selected").val();
        _trade = trade;
        $.getJSON("/getKRWords/getCategories",
                {trade: trade},
                function (data) {
                    var category = "", datas = data.rows;
                    category += "<option value='' selected='selected'>请选择类别</option>";
                    for (var i = 0, l = datas.length; i < l; i++) {
                        if (i == 0) {
                            category += "<option value='" + datas[i].category + "' selected='selected'>" + datas[i].category + "</option>";
                            _category = datas[i].category;
                            continue;
                        }
                        category += "<option value='" + datas[i].category + "'>" + datas[i].category + "</option>";
                    }
                    $("#category").empty();
                    $("#category").append(category);
                });
    });
    $("#category").change(function () {
        var category = $("#category option:selected").val();
        _category = category;
    });
    $("#textarea1").on('keyup', function () {
        var seedWords = $("#textarea1").val().trim().split("\n");
        $("#textarea1").parent().next().text("可输入词根" + (100 - seedWords.length) + "/100");
    });
});
var downloadCSV = function () {
    var _url;
    if (type == 1) {
        document.getElementById("background").style.display = "block";
        document.getElementById("progressBar1").style.display = "block";
        _url = "/getKRWords/downloadBaiduCSV?seedWords=" + getSeedWords() + "&krFileId=" + krFileId;

        if (window.attachEvent) {
            ie_iframe = document.createElement("iframe");
            ie_iframe.id = "downloadhelper_iframe";
            ie_iframe.width = 0;
            ie_iframe.height = 0;
            ie_iframe.src = _url;
            ie_iframe.onload = ie_iframe.onreadystatechange = iframeLoad;
            document.body.appendChild(ie_iframe);
        } else {
            var iframe1 = document.createElement("iframe");
            iframe1.id = "downloadhelper_iframe";
            iframe1.width = 0;
            iframe1.height = 0;
            iframe1.src = _url;
            document.body.appendChild(iframe1);
            iframe1.onload = function () {
                document.getElementById("background").style.display = "none";
                document.getElementById("progressBar1").style.display = "none";
            };
        }
    } else if (type == 2) {
        document.getElementById("background").style.display = "block";
        document.getElementById("progressBar1").style.display = "block";
        _url = "/getKRWords/downloadCSV?trade=" + _trade + "&category=" + _category;

        if (window.attachEvent) {
            ie_iframe = document.createElement("iframe");
            ie_iframe.id = "downloadhelper_iframe";
            ie_iframe.width = 0;
            ie_iframe.height = 0;
            ie_iframe.src = _url;
//            ie_iframe.attachEvent("onload", iframeLoad);
            ie_iframe.onload = ie_iframe.onreadystatechange = iframeLoad;
            document.body.appendChild(ie_iframe);
        } else {
            var iframe2 = document.createElement("iframe");
            iframe2.id = "downloadhelper_iframe";
            iframe2.width = 0;
            iframe2.height = 0;
            iframe2.src = _url;
            document.body.appendChild(iframe2);
            iframe2.onload = function () {
                document.getElementById("background").style.display = "none";
                document.getElementById("progressBar1").style.display = "none";
            };
        }
    }
};
var ie_iframe;
var iframeLoad = function () {
    if (ie_iframe.readyState == "interactive") {
        document.getElementById('background').style.display = 'none';
        document.getElementById('progressBar1').style.display = 'none';
    }
};

var save1Keyword = function () {
    if (krFileId == null || krFileId == "") {
        return;
    }
    $.ajax({
        url: '/getKRWords/save1',
        type: 'POST',
        dataType: 'json',
        data: {
            "seedWords": getSeedWords(),
            "krFileId": krFileId
        },
        success: function (data, textStatus, jqXHR) {
            if (data.status != null && data.status == true) {
                alert("保存成功!");
            }
        }
    });
};
var save2Keyword = function () {
    var value1 = $("#category option:selected").val();
    if (value1 == null || value1.trim().length == 0) {
        alert("请选择一个类别!");
        return;
    }
    var trade = $("#trade option:selected").val();
    var category = $("#category option:selected").val();
    $.ajax({
        url: '/getKRWords/save2',
        type: 'POST',
        dataType: 'json',
        data: {
            "trade": trade,
            "category": category
        },
        success: function (data, textStatus, jqXHR) {
            if (data.status != null && data.status == true) {
                alert("保存成功!");
            }
        }
    });
};
var findWordFromBaidu = function () {
    krFileId = null;
    var iframe = document.getElementById("downloadhelper_iframe");
    if (iframe != null) {
        document.body.removeChild(iframe);
    }

    type = 1;
    var seedWords = "";
    var words = $("#textarea1").val().split("\n");//种子词数组
    for (var i = 0, l = words.length; i < l; i++) {
        if (words[i].trim().length == 0) {
            continue;
        }
        if (i == 0)
            seedWords += words[i];
        else
            seedWords += "," + words[i];
    }
    getSeedWords = function () {
        return seedWords;
    };
    skip = 0;
    total = 0;
    $.ajax({
        url: "/getKRWords/bd",
        type: "GET",
        data: {
            "seedWords": seedWords,
            "skip": skip,
            "limit": limit,
            "krFileId": krFileId,
            "fieldName": fieldName,
            "sort": sort
        },
        success: function (data, textStatus, jqXHR) {
            $("#tbody1").empty();
            if (data.rows.length > 0) {
                krFileId = data.krFileId;
                total = data.total;
                $("#totalPage1").text(Math.ceil(total / limit));
                var _class = "";
                $.each(data.rows, function (i, item) {
                    if (i % 2 == 0) {
                        _class = "list2_box1";
                    } else {
                        _class = "list2_box2";
                    }
                    var newTr = "<tr class='" + _class + "'>" +
                            "<td>" + item.groupName + "</td>" +
                            "<td>" + item.seedWord + "</td>" +
                            "<td>" + item.keywordName + "</td>" +
                            "<td>" + item.dsQuantity + "</td>" +
                            "<td>" + item.competition + "%</td>" +
// "<td>" + item.recommendReason1 + "</td>" +
// "<td>" + item.recommendReason2 + "</td>" +
                            "</tr>";
                    $("#tbody1").append(newTr);
                });
            }
        }
    });
};
var findWordFromSystem = function () {
    var iframe = document.getElementById("downloadhelper_iframe");
    if (iframe != null) {
        document.body.removeChild(iframe);
    }

    type = 2;
    var trade = $("#trade option:selected").val();
    var category = $("#category option:selected").val();
    $.ajax({
        url: "/getKRWords/p",
        type: "GET",
        data: {
            "trade": trade,
            "category": category,
            "skip": skip,
            "limit": limit
        },
        success: function (data, textStatus, jqXHR) {
            $("#tbody2").empty();
            if (data.rows.length > 0) {
                total = data.total;
                $("#totalPage2").text(Math.ceil(total / limit));
                var _class = "";
                $.each(data.rows, function (i, item) {
                    if (i % 2 == 0) {
                        _class = "list2_box1";
                    } else {
                        _class = "list2_box2";
                    }
                    var newTr = "<tr class='" + _class + "'>" +
                            "<td>" + _trade + "</td>" +
                            "<td>" + _category + "</td>" +
                            "<td>" + item.group + "</td>" +
                            "<td>" + (item.keyword == null ? "" : item.keyword) + "</td>" +
                            "</tr>";
                    $("#tbody2").append(newTr);
                });
            }
        }
    });
};
var toPrevPage = function () {
    if (skip == 0) {
        return;
    }
    skip--;
    if (type == 1) {
        $.ajax({
            url: "/getKRWords/bd",
            type: "GET",
            async: false,
            data: {
                "seedWords": getSeedWords,
                "skip": skip,
                "limit": limit,
                "krFileId": krFileId,
                "fieldName": fieldName,
                "sort": sort
            },
            success: function (data, textStatus, jqXHR) {
                $("#tbody1").empty();
                if (data.rows.length > 0) {
                    krFileId = data.krFileId;
                    var _class = "";
                    $.each(data.rows, function (i, item) {
                        if (i % 2 == 0) {
                            _class = "list2_box1";
                        } else {
                            _class = "list2_box2";
                        }
                        var newTr = "<tr class='" + _class + "'>" +
                                "<td>" + item.groupName + "</td>" +
                                "<td>" + item.seedWord + "</td>" +
                                "<td>" + item.keywordName + "</td>" +
                                "<td>" + item.dsQuantity + "</td>" +
                                "<td>" + item.competition + "%</td>" +
// "<td>" + item.recommendReason1 + "</td>" +
// "<td>" + item.recommendReason2 + "</td>" +
                                "</tr>";
                        $("#tbody1").append(newTr);
                    });
                }
            }
        });
    } else {
        $.ajax({
            url: "/getKRWords/p",
            type: "GET",
            data: {
                "trade": _trade,
                "category": _category,
                "skip": skip,
                "limit": limit,
                "status": 1
            },
            success: function (data, textStatus, jqXHR) {
                $("#tbody2").empty();
                if (data.rows.length > 0) {
                    var _class = "";
                    $.each(data.rows, function (i, item) {
                        if (i % 2 == 0) {
                            _class = "list2_box1";
                        } else {
                            _class = "list2_box2";
                        }
                        var newTr = "<tr class='" + _class + "'>" +
                                "<td>" + _trade + "</td>" +
                                "<td>" + _category + "</td>" +
                                "<td>" + item.group + "</td>" +
                                "<td>" + (item.keyword == null ? "" : item.keyword) + "</td>" +
                                "</tr>";
                        $("#tbody2").append(newTr);
                    });
                }
            }
        });
    }
};
var toNextPage = function () {
    if (skip + 2 > total / limit) {
        return;
    }
    skip++;
    if (type == 1) {
        $.ajax({
            url: "/getKRWords/bd",
            type: "GET",
            async: false,
            data: {
                "seedWords": getSeedWords,
                "skip": skip,
                "limit": limit,
                "krFileId": krFileId,
                "fieldName": fieldName,
                "sort": sort
            },
            success: function (data, textStatus, jqXHR) {
                $("#tbody1").empty();
                if (data.rows.length > 0) {
                    krFileId = data.krFileId;
                    var _class = "";
                    $.each(data.rows, function (i, item) {
                        if (i % 2 == 0) {
                            _class = "list2_box1";
                        } else {
                            _class = "list2_box2";
                        }
                        var newTr = "<tr class='" + _class + "'>" +
                                "<td>" + item.groupName + "</td>" +
                                "<td>" + item.seedWord + "</td>" +
                                "<td>" + item.keywordName + "</td>" +
                                "<td>" + item.dsQuantity + "</td>" +
                                "<td>" + item.competition + "%</td>" +
// "<td>" + item.recommendReason1 + "</td>" +
// "<td>" + item.recommendReason2 + "</td>" +
                                "</tr>";
                        $("#tbody1").append(newTr);
                    });
                }
            }
        });
    } else {
        $.ajax({
            url: "/getKRWords/p",
            type: "GET",
            data: {
                "trade": _trade,
                "category": _category,
                "skip": skip,
                "limit": limit,
                "status": 1
            },
            success: function (data, textStatus, jqXHR) {
                $("#tbody2").empty();
                if (data.rows.length > 0) {
                    var _class = "";
                    $.each(data.rows, function (i, item) {
                        if (i % 2 == 0) {
                            _class = "list2_box1";
                        } else {
                            _class = "list2_box2";
                        }
                        var newTr = "<tr class='" + _class + "'>" +
                                "<td>" + _trade + "</td>" +
                                "<td>" + _category + "</td>" +
                                "<td>" + item.group + "</td>" +
                                "<td>" + (item.keyword == null ? "" : item.keyword) + "</td>" +
                                "</tr>";
                        $("#tbody2").append(newTr);
                    });
                }
            }
        });
    }
};
var toAnyPage = function () {
    if (type == 1) {
        skip = $("#pageNumber1").val();
    } else {
        skip = $("#pageNumber2").val();
    }
    if ((skip > (total / limit)) || skip <= 0) {
        return;
    }
    skip--;
    if (type == 1) {
        $.ajax({
            url: "/getKRWords/bd",
            type: "GET",
            async: false,
            data: {
                "seedWords": getSeedWords,
                "skip": skip,
                "limit": limit,
                "krFileId": krFileId,
                "fieldName": fieldName,
                "sort": sort
            },
            success: function (data, textStatus, jqXHR) {
                $("#tbody1").empty();
                if (data.rows.length > 0) {
                    krFileId = data.krFileId;
                    var _class = "";
                    $.each(data.rows, function (i, item) {
                        if (i % 2 == 0) {
                            _class = "list2_box1";
                        } else {
                            _class = "list2_box2";
                        }
                        var newTr = "<tr class='" + _class + "'>" +
                                "<td>" + item.groupName + "</td>" +
                                "<td>" + item.seedWord + "</td>" +
                                "<td>" + item.keywordName + "</td>" +
                                "<td>" + item.dsQuantity + "</td>" +
                                "<td>" + item.competition + "%</td>" +
// "<td>" + item.recommendReason1 + "</td>" +
// "<td>" + item.recommendReason2 + "</td>" +
                                "</tr>";
                        $("#tbody1").append(newTr);
                    });
                }
            }
        });
    } else {
        $.ajax({
            url: "/getKRWords/p",
            type: "GET",
            data: {
                "trade": _trade,
                "category": _category,
                "skip": skip,
                "limit": limit,
                "status": 1
            },
            success: function (data, textStatus, jqXHR) {
                $("#tbody2").empty();
                if (data.rows.length > 0) {
                    var _class = "";
                    $.each(data.rows, function (i, item) {
                        if (i % 2 == 0) {
                            _class = "list2_box1";
                        } else {
                            _class = "list2_box2";
                        }
                        var newTr = "<tr class='" + _class + "'>" +
                                "<td>" + _trade + "</td>" +
                                "<td>" + _category + "</td>" +
                                "<td>" + item.group + "</td>" +
                                "<td>" + (item.keyword == null ? "" : item.keyword) + "</td>" +
                                "</tr>";
                        $("#tbody2").append(newTr);
                    });
                }
            }
        });
    }
};
</script>
</body>
</html>
