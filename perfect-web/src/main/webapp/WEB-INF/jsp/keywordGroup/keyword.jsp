<%--
Created by IntelliJ IDEA.
User: baizz
Date: 2014-08-07
Time: 11:28
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title>大数据智能营销</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/multiple-select.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/media.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/pagination/pagination.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/creative/creativesearch.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/creative/creativesearch_img.css">
    <script type="text/javascript" src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
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
        <div class="title_box">
            <div class="on_title over">
                <a href="#">
                    智能结构
                </a>
                &nbsp;&nbsp;&gt;&nbsp;&nbsp;<span>关键词拓词</span>
            </div>
        </div>
        <div id="tab">
            <ul class="tab_menu" id="tab_menu">
                <li class="selected">关键词拓词</li>
                <li>创意推荐</li>
            </ul>
            <div class="tab_box">
                <div class="containers over">
                    <div class="list01_top over"><Span>关键词拓词</Span>
                        <button type="button" class="question  btn btn-default" data-toggle="tooltip"
                                data-placement="bottom"
                                title="分为词根拓词和行业拓词，拓词来源有百度、Google、360等平台抓取调用，并独有百思智能词库作为依托。"></button>
                    </div>
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
                                                <div class="k_top2_text1">
                                                    <textarea id="textarea1"
                                                              style="overflow:auto; resize: none"></textarea>
                                                </div>
                                                <p>可输入词根100/100</p>
                                                <a href="javascript: findWordFromBaidu();" class="become2">开始拓词</a>
                                            </div>
                                            <div class="K_top2_detali fl over">
                                                <div class="k_top2_detali2 over">
                                                    <div class="list01_top2 over">
                                                        <span>拓词来源</span>
                                                    </div>
                                                    <ul>
                                                        <li>·百度、Google、360等搜索引擎平台抓取调用</li>
                                                        <li>·百思智能词库</li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="k_r_under over">
                                            <div class="download over ">
                                                <%--<a class="fr" href="javascript: save1Keyword();">保存</a>--%>
                                                <a class="fr" href="javascript: downloadCSV();">下载全部</a>
                                            </div>
                                            <div class="list3 tuoci over">
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
                                                    </tr>
                                                    </thead>
                                                    <tbody id="tbody1">
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <div id="pagination1" class="pagination"></div>
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
                                                    <select id="category" multiple="multiple">
                                                    </select>
                                                    <select id="keyword_group" multiple="multiple">
                                                    </select>
                                                </div>
                                                <a href="javascript:findWordFromSystem();" class="become2">开始拓词</a>
                                            </div>
                                            <div class="K_top2_detali fl over">
                                                <div class="k_top2_detali2 over">
                                                    <div class="list01_top2 over">
                                                        <span>拓词来源</span>
                                                    </div>
                                                    <ul>
                                                        <li>·百度、Google、360等搜索引擎平台抓取调用</li>
                                                        <li>·百思智能词库</li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="k_r_under over">
                                            <div class="download over ">
                                                <%--<a class="fr" href="javascript: save2Keyword();">保存</a>--%>
                                                <a class="fr" href="javascript: downloadCSV();">下载全部</a>
                                            </div>
                                            <div class="list3 hangyetuoci over">
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
                                        <div id="pagination2" class="pagination"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="containers over hides">
                    <div class="list01_top over"><span>创意推荐</span>
                        <button type="button" class="question  btn btn-default" data-toggle="tooltip"
                                data-placement="bottom"
                                title="系统自动抓取搜索引擎页面信息，进行分析提取与当前创意相关的优秀词根及优秀创意作为新创意的参考，从而提升创意质量。"></button>
                        <b>输入关键词，系统为您推荐有限创意和优秀词根；关键词之间的相关性会影响推荐创意的质量。</b><a
                                href="#">同步账户</a></div>
                    <div class="originality over">
                        <div class="originality_left fl">
                            <ul>
                                <li><input type="radio" checked="checked" name="searchType" value="1"> 选择已有关键词</li>
                                <li><span>计划：</span>
                                    <select id="campagin">
                                        <option value="-1">请选择计划</option>
                                    </select></li>
                                <li><span>单元：</span>
                                    <select id="adgroup">
                                        <option value="-1">请选择单元</option>
                                    </select>
                                </li>
                                <li>
                                    <textarea id="txt1" name="" style="overflow:auto; resize: none"></textarea>
                                </li>
                                <%--<li style="color: #f00">1/20行</li>--%>
                            </ul>
                        </div>
                        <div class="originality_right fl">
                            <ul>
                                <li><input type="radio" name="searchType" value="2"> 输入新单元词</li>
                                <li>
                                    <textarea id="txt2" name="txt2" style="overflow:auto; resize: none"></textarea>
                                </li>
                                <%--<li style="color: #f00">1/20行</li>--%>
                            </ul>
                        </div>
                    </div>
                    <div class="riginality_middle over">
<span>选择创意来源：<select id="region">
    <option value="1">河北</option>
</select></span>
                        <span><a class="become2" href="javascript: search();">智能推荐</a></span>
                    </div>
                    <div class="riginality_under over">
                        <div class="r_under_left fl">
                            <div class="r_under_top over">
                                <h3 class="fl">推荐创意</h3>
                                <%--<a href="#" class="fr" id="bulid">新建创意</a>--%>
                            </div>
                            <ul id="creativeList">
                            </ul>
                        </div>
                        <div class="r_under_mid fl">
                            <div class="r_under_right">
                                <div class="r_under_top over">
                                    <ul>
                                        <li><h3>推荐词根</h3></li>
                                        <li><h3>词频占比</h3></li>
                                    </ul>
                                </div>
                                <div class="r_under_bottom over ">
                                    <ul id="terms">
                                    </ul>
                                </div>
                            </div>

                            <div class="r_under_right">
                                <div class="r_under_top over">
                                    <ul>
                                        <li><h3>竞争对手</h3></li>
                                        <li><h3>创意占比</h3></li>
                                    </ul>
                                </div>
                                <div class="r_under_bottom over ">
                                    <ul id="hosts">
                                    </ul>
                                </div>
                            </div>

                            <div class="r_under_right">
                                <div class="r_under_top over">
                                    <ul>
                                        <li><h3>地域</h3></li>
                                        <li><h3>创意占比</h3></li>
                                    </ul>
                                </div>
                                <div class="r_under_bottom over ">
                                    <ul id="regions">
                                    </ul>
                                </div>
                            </div>

                        </div>
                        <div class="r_under_mid fl" id="reOkView" style="display: none;">

                            <div class="r_under_top over">
                                <h3 class="fl">上传创意预览</h3>
                            </div>

                            <ul id="reViewUl">
                                <li>
                                    <div></div>
                                    <a data-is-main-url="true" href="javascript:void(0);" class="EC_t EC_BL"
                                       id="rTitle">
                                        暂无</a>
                                    <br>
                                    <a id="bdfs1" href="javascript:void(0);" class="EC_BL EC_desc"><font size="-1"
                                                                                                         id="rDesc">暂无</font><br>
                                        <font size="-1" class="EC_url" id="rUrl">暂无</font></a>

                                    <div class="c-tools" style="margin-left:5px;" id="tools_2">
                                        <a class="c-tip-icon"></a>
                                    </div>
                                    <div>
                                        <input type="button" value="保存" class="chuanyi_input"
                                               onclick="addCreativeOk()"/>
                                        <input type="button" value="返回编辑" class="chuanyi_input"
                                               onclick="callBackEditor()"/>
                                    </div>
                                </li>
                            </ul>
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
            <div class="mainlist" id="_editor">
                <ul>
                    <li>
                        <input type="hidden" id="dm"/>

                        <h3>标题：</h3>
                        <dl><input type="text" name="title" id="title" class="r_input" placeholder="请输入创意标题.且必须大于8个字符!"><span>27/50</span>
                        </dl>
                        <dl><input type="button" class="r_input2" value="{}插入通配符" onclick="addTongPei()"></dl>
                        <dl id="titleMsg"></dl>
                    </li>
                    <li>
                        <h3>描述1：</h3>
                        <dl><textarea name="desc1" id="desc1"
                                      placeholder="请输入描述1.且必须大于8个字符!"></textarea><span>27/80</span></dl>
                        <dl><input type="button" class="r_input2" value="{}插入通配符" onclick="addTongPeiDe1()"></dl>
                        <dl id="desc1Msg"></dl>
                    </li>
                    <li>
                        <h3>描述2：</h3>
                        <dl><textarea name="desc2" id="desc2"
                                      placeholder="请输入描述2.且必须大于8个字符!"></textarea><span>27/80</span></dl>
                        <dl><input type="button" class="r_input2" value="{}插入通配符" onclick="addTongPeiDe2()"></dl>
                        <dl id="desc2Msg"></dl>
                    </li>
                    <li>
                        <h3>访问URL：</h3>
                        <dl><textarea name="pcUrl" placeholder="该Url是真实访问Url."></textarea><span>27/1024</span></dl>
                        <dl id="pcUrlMsg"></dl>
                    </li>
                    <li>
                        <h3>显示URL：</h3>
                        <dl><input type="text" name="pcsUrl" class="r_input" placeholder="请输入显示Url."><span>27/36</span>
                            <dl id="pcsUrlMsg"></dl>
                        </dl>
                    </li>
                </ul>
            </div>
            <div class="main_bottom">
                <div class="w_list03">
                    <ul>
                        <li class="current" onclick="reSave()">保存</li>
                        <li class="close">取消</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="riginality_editor2 fl">
            <div class="tuijian over">
                <h3>推荐词根<span>|</span> 词頻占比</h3>
                <ul id="repUl">
                    <li>搜索<span>8%</span></li>
                </ul>
            </div>
            <div class="tuijian_under over">
                <ul>
                    <li>
                        <p>左侧推广连接位预览：</p>

                        <div id="reLeft1">
                        </div>
                    </li>
                    <li>
                        <p>左侧推广连接位预览：</p>

                        <div id="reLeft2">
                        </div>
                    </li>
                    <li>
                        <p>右侧推广位预览：</p>

                        <div id="reRight" style="width: 300px;">
                        </div>
                    </li>
                </ul>
                <%--<span><a class="become2 fl" href="javascript: findWordFromBaidu();">评估匹配度</a><a class="question"--%>
                <%--href="#"></a></span>--%>
            </div>
        </div>
    </div>
</div>
<!------------新建创意-------------->
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

<%--创意添加选择计划，单元弹出窗口--%>
<div class="box" style="display:none" id="jcAdd">
    <h2 id="dAdd">
        <span class="fl">添加创意</span>
        <a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        选择要添加到的计划或者单元!
        <ul class="zs_set" id="jcUl">
            <%--<li><input type="radio" checked="checked" name="no1">&nbsp; 所有推广计划</li>--%>
        </ul>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li onclick="replanUnit()">确认</li>
                <li onclick="recloseAlert();">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--创意添加选择计划，单元弹出窗口--%>
<div class="box" style="display:none" id="subLinkType">
    <h2 id="sublink">
        <span class="fl">添加创意</span>
        <a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        添加
        <input type="button" value="添加" onclick="addSub()">

        <p id="subTitle"></p>

        <div>
            <ul id="sub">
            </ul>
            <p id="Url" style="color: green;"></p>
        </div>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li onclick="">确认</li>
                <li onclick="subHideEditor()">取消</li>
            </ul>
        </div>
    </div>
</div>

<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/pagination/jquery.pagination.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/json2/20140204/json2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/tc.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery.pin/1.0.1/jquery.pin.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.multiple.select.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/creativesearch.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistant/tuijianCreative.js"></script>
<script type="text/javascript">

    String.prototype.trims = function () {
        return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
    };

    var type = 1; //1, baidu; 2, system
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
        rDrag.init(document.getElementById('dAdd'));
        rDrag.init(document.getElementById('sublink'));
    };

    //loading
    var ajaxbg = $("#background,#progressBar");
    $("#progressBar1").hide();
    ajaxbg.hide();
    $(document).ajaxStart(function () {
        ajaxbg.show();
    });
    $(document).ajaxStop(function () {
        ajaxbg.fadeOut(1000);
    });

    /******************pagination*******************/
    var items_per_page = 10;    //默认每页显示10条数据
    var pageIndex = 0;
    var baiduWordPage = -1;
    var systemWordPage = -1;

    var pageSelectCallback = function (page_index, jq) {
        if (type == 1) {
            $("#pagination1").append("<span style='margin-right:10px;'>跳转到 <input id='anyPageNumber1' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipPage();' class='page_go'> GO</a>");
        } else {
            $("#pagination2").append("<span style='margin-right:10px;'>跳转到 <input id='anyPageNumber2' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipPage();' class='page_go'> GO</a>");
        }

        if (pageIndex == page_index) {
            return false;
        }
        pageIndex = page_index;
        toAnyPage(page_index);
        return false;
    };

    var getOptionsFromForm = function (current_page) {
        var opt = {callback: pageSelectCallback};

        opt["items_per_page"] = items_per_page;
        opt["current_page"] = current_page;
//    opt["num_display_entries"] = 10;
//    opt["num_edge_entries"] = 2;
        opt["prev_text"] = "上一页";
        opt["next_text"] = "下一页";

        var htmlspecialchars = {"&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;"};
        $.each(htmlspecialchars, function (k, v) {
            opt.prev_text = opt.prev_text.replace(k, v);
            opt.next_text = opt.next_text.replace(k, v);
        });
        return opt;
    };
    /***********************************************/
    var optInit = getOptionsFromForm(0);
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
                top: ($(window).height() - $("#new_riginality").height()) / 2 + ($(window).scrollTop() - 83) + "px",
                display: "block"
            });
        });

        $(".close").click(function () {
            $(".TB_overlayBG").css("display", "none");
            $(".box").css("display", "none");
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
                        var _l = datas.length;
                        for (var i = 0, l = datas.length; i < l; i++) {
                            category += "<option value='" + datas[i].category + "'>" + datas[i].category + "</option>";
                        }

                        $("#category").empty();
                        $("#category").append(category);
                        $("#category").multipleSelect({
                            placeholder: "请选择类别",
                            selectAll: false,
                            minumimCountSelected: 20,
                            multiple: true,
                            onClose: function () {
                                _category = $("#category").multipleSelect('getSelects');
                                if (_category.length > 0) {
                                    $.getJSON('/getKRWords/findKeywordByCategories',
                                            {categories: JSON.stringify(_category)},
                                            function (data) {
                                                var datum = data.rows;
                                                var keywordGroupOfTrade = "";
                                                for (var j = 0, s = datum.length; j < s; j++) {
                                                    keywordGroupOfTrade += "<option value='" + datum[j].category + "'>" + datum[j].category + "</option>";
                                                }

                                                $('#keyword_group').empty();
                                                $('#keyword_group').append(keywordGroupOfTrade);
                                                $('#keyword_group').multipleSelect({
                                                    placeholder: "请选择分组",
                                                    selectAll: false,
                                                    minumimCountSelected: 20,
                                                    multiple: true
                                                });
                                            });
                                } else {
                                    $('#keyword_group').empty();
                                }
                            }
                        });

                        // Clear
                        $('#keyword_group').empty();
                        $('#keyword_group').append("");
                        $('#keyword_group').multipleSelect({
                            placeholder: "请选择分组",
                            selectAll: false,
                            minumimCountSelected: 20,
                            multiple: true
                        });
                    });
        });

//    $("#category").change(function () {
////        var category = $("#category option:selected").val();
//        var category = $("#category").multipleSelect('getSelects');
//        _category = category;
//    });

        $("#textarea1").on('keyup', function () {
            var seedWords = $("#textarea1").val().trims().split("\n");
            $("#textarea1").parent().next().text("可输入词根" + (100 - seedWords.length) + "/100");
        });

    });
    var downloadCSV = function () {
        var _url;
        if (type == 1) {
            if ($("#textarea1").val() != null && $("#textarea1").val().trims().length == 0) {
                return;
            }
            _url = "/getKRWords/downloadBaiduCSV?seedWords=" + getSeedWords() + "&krFileId=" + krFileId;

            if (!!window.ActiveXObject || "ActiveXObject" in window) {
                document.getElementById("background").style.display = "block";
                document.getElementById("progressBar1").style.display = "block";
                ie_iframe = document.createElement("iframe");
                ie_iframe.id = "downloadhelper_iframe";
                ie_iframe.width = 0;
                ie_iframe.height = 0;
                ie_iframe.src = _url;
//            ie_iframe.attachEvent("onload", iframeLoad);
                ie_iframe.onload = ie_iframe.onreadystatechange = iframeLoad;
                document.body.appendChild(ie_iframe);
            } else {
                if (window.navigator.userAgent.indexOf("Chrome") != -1) {
                    window.open(_url, "");
                } else {
                    document.getElementById("background").style.display = "block";
                    document.getElementById("progressBar1").style.display = "block";
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
            }
        } else if (type == 2) {
            if (_trade == null || _trade == "") {
                return;
            }

            _url = "/getKRWords/downloadCSV?trade=" + _trade
                    + "&categories=" + JSON.stringify($("#category").multipleSelect('getSelects'))
                    + "&groups=" + JSON.stringify($("#keyword_group").multipleSelect('getSelects'));

            if (!!window.ActiveXObject || "ActiveXObject" in window) {
                document.getElementById("background").style.display = "block";
                document.getElementById("progressBar1").style.display = "block";
                ie_iframe = document.createElement("iframe");
                ie_iframe.id = "downloadhelper_iframe";
                ie_iframe.width = 0;
                ie_iframe.height = 0;
                ie_iframe.src = _url;
                ie_iframe.onload = ie_iframe.onreadystatechange = iframeLoad;
                document.body.appendChild(ie_iframe);
            } else {
                if (window.navigator.userAgent.indexOf("Chrome") != -1) {
                    window.open(_url, "");
                } else {
                    document.getElementById("background").style.display = "block";
                    document.getElementById("progressBar1").style.display = "block";
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
        if (value1 == null || value1.trims().length == 0) {
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
        baiduWordPage = -1;
        krFileId = null;
        var iframe = document.getElementById("downloadhelper_iframe");
        if (iframe != null) {
            document.body.removeChild(iframe);
        }

        type = 1;
        var seedWords = "";
        if ($("#textarea1").val() != null && $("#textarea1").val().trims().length == 0) {
            return;
        }
        var words = $("#textarea1").val().split("\n");//种子词数组
        for (var i = 0, l = words.length; i < l; i++) {
            if (words[i].trims().length == 0) {
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
                    $("#pagination1").pagination(total, optInit);
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
                                "</tr>";
                        $("#tbody1").append(newTr);
                    });
                }
            }
        });
    };
    var findWordFromSystem = function () {
        systemWordPage = -1;
        var iframe = document.getElementById("downloadhelper_iframe");
        if (iframe != null) {
            document.body.removeChild(iframe);
        }

        type = 2;
        var trade = $("#trade option:selected").val();
        if (trade == null || trade == "") {
            return;
        }
//    var category = $("#category option:selected").val();
//    var categories = $("#category").multipleSelect('getSelects');
//    var groups = $("#keyword_group").multipleSelect('getSelects');
        $.ajax({
            url: "/getKRWords/p",
            type: "GET",
            data: {
                "trade": trade,
                "categories": JSON.stringify($("#category").multipleSelect('getSelects')),
                "groups": JSON.stringify($("#keyword_group").multipleSelect('getSelects')),
                "skip": skip,
                "limit": limit
            },
            success: function (data, textStatus, jqXHR) {
                $("#tbody2").empty();
                if (data.rows.length > 0) {
                    total = data.total;
                    $("#pagination2").pagination(total, optInit);
                    var _class = "";
                    $.each(data.rows, function (i, item) {
                        if (i % 2 == 0) {
                            _class = "list2_box1";
                        } else {
                            _class = "list2_box2";
                        }
                        var newTr = "<tr class='" + _class + "'>" +
                                "<td>" + _trade + "</td>" +
                                "<td>" + item.category + "</td>" +
                                "<td>" + item.group + "</td>" +
                                "<td>" + (item.keyword == null ? "" : item.keyword) + "</td>" +
                                "</tr>";
                        $("#tbody2").append(newTr);
                    });
                }
            }
        });
    };

    var skipPage = function () {
        var _number = 0;
        if (type == 1) {
            _number = $("#anyPageNumber1").val() - 1;
            if (_number <= -1 || _number == pageIndex) {
                return;
            }
            $("#pagination1").pagination(total, getOptionsFromForm(_number));
        } else {
            _number = $("#anyPageNumber2").val() - 1;
            if (_number <= -1 || _number == pageIndex) {
                return;
            }
            $("#pagination2").pagination(total, getOptionsFromForm(_number));
        }
    };

    var toAnyPage = function (page_index) {
        if (baiduWordPage == -1 && page_index == 0) {
            document.getElementById("background").style.display = "none";
            document.getElementById("progressBar").style.display = "none";
            return false;
        }
        if (systemWordPage == -1 && page_index == 0) {
            document.getElementById("background").style.display = "none";
            document.getElementById("progressBar").style.display = "none";
            return false;
        }
        baiduWordPage = 0;
        systemWordPage = 0;
        skip = page_index;

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
                    "categories": JSON.stringify($("#category").multipleSelect('getSelects')),
                    "groups": JSON.stringify($("#keyword_group").multipleSelect('getSelects')),
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
                                    "<td>" + item.category + "</td>" +
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
    $(function () {
        $("[data-toggle='tooltip']").tooltip();
    });

</script>
</body>
</html>