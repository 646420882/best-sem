<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2014-08-07
  Time: 上午11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
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

        .team_box {
            background: #fff;
            left: 25%;
            position: absolute;
            top: 100px;
            padding: 20px;
            width: 60%;
            z-index: 999;
            border-radius: 10px;
        }

    </style>

    <!--文本框插件-->
    <style type="text/css">
        input {
            height: auto;
            font-size: 14px;
            font-weight: bold;
            color: #000
        }

        textarea {
            resize: none;
            border: 1px solid #7f9db9;
            font-size: 14px;
            width: 448px;
            color: #000
        }

        input {
            font-size: 14px;
            color: #000
        }

        .grey {
            color: #000
        }

        em {
            font-style: normal;
            color: #f00;
            font-size: 12px;
        }

        #ol {
            position: absolute;
            z-index: 1;
            padding: 0px;
            margin: 0px;
            border: 0px;
            background: #ecf0f5;
            width: 23px;
            text-align: left;
        }

        #li {
            background: #ecf0f5;
            height: 160px;
            overflow: hidden;
            width: 32px;
            min-height: 350px;
            border-right: 0;
            line-height: 20px;
            text-align: center
        }

        #c2 {
            font-family: Arial, Helvetica, sans-serif;
            height: 160px;
            margin: 0px;
            width: 420px;
            min-height: 350px;
            padding: 0 0 0 32px;
            overflow-x: hidden;
            line-height: 20px;
        }
    </style>
</head>
<body onLoad=keyUp()>
<jsp:include page="../homePage/pageBlock/head.jsp"/>
<div class="concent over">
    <jsp:include page="../homePage/pageBlock/nav.jsp"/>
    <div class="mid over fr">
        <div class="on_title over">
            <a href="#">
                账户全景
            </a>
            &nbsp;&nbsp;&gt;&nbsp;&nbsp;<span>账户分析</span>
        </div>
        <div id="tab">
            <div class="tab_box">
                <div class="list01_top over"><Span>关键词拓展</Span> <a href="#" class="question"></a></div>
                <div class="keyword over">
                    <div class="keyword_right over">
                        <div class="k_r_top over">
                            <ul class="tab_menu2 over">
                                <li class="current"><a>词根拓词</a></li>
                                <li><a>行业拓词</a></li>
                            </ul>
                            <div class="table_concent2 over">
                                <div class="k_r_top2 over">
                                    <div class="k_top2_text fl">
                                        <div class="k_top2_text1"><textarea id="textarea1"
                                                                            style="overflow:auto; resize: none"></textarea>
                                        </div>
                                        <p>可输入词根10/10</p>
                                        <a href="javascript: findWordFromBaidu();" class="become2">开始拓词</a>

                                    </div>
                                    <div class="K_top2_detali fr over">
                                        <div class="k_top2_detali2 over">
                                            <div class="list01_top2 over">
                                                <span>重点关键词监控</span>
                                                <a href="#" class="question"></a>
                                            </div>
                                            <ul>
                                                <li>· 搜索引擎（baidu，google）APP</li>
                                                <li>· 抓取搜索引擎先关搜索结果：baidu/google/sougo/soso/bing</li>
                                                <li>· 普菲特智能词库</li>
                                            </ul>
                                        </div>
                                        <div class="k_top2_detali2 over">
                                            <div class="list01_top2 over">
                                                <span>智能过滤</span>
                                                <a href="#" class="question"></a>
                                            </div>
                                            <ul>
                                                <li><input type="checkbox">&nbsp;&nbsp;搜索引擎（baidu，google）APP</li>
                                                <li><input type="checkbox">&nbsp;&nbsp;抓取搜索引擎先关搜索结果：baidu/google/sougo/soso/bing
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="k_r_top2  hides over">
                                    <div class="k_top2_text fl">
                                        <div class="k_top2_text1">
                                            <select>
                                                <option>请选择行业</option>
                                            </select>
                                        </div>

                                        <a href="#" class="become2">开始拓词</a>
                                    </div>
                                    <div class="K_top2_detali fr over">
                                        <div class="k_top2_detali2 over">
                                            <div class="list01_top2 over">
                                                <span>拓词来源</span>
                                                <a href="#" class="question"></a>
                                            </div>
                                            <ul>
                                                <li>· 搜索引擎（baidu，google）APP</li>
                                                <li>· 抓取搜索引擎先关搜索结果：baidu/google/sougo/soso/bing</li>
                                                <li>· 普菲特智能词库</li>
                                            </ul>
                                        </div>
                                        <div class="k_top2_detali2 over">
                                            <div class="list01_top2 over">
                                                <span>智能过滤</span>
                                                <a href="#" class="question"></a>
                                            </div>
                                            <ul>
                                                <li><input type="checkbox">&nbsp;&nbsp;搜索引擎（baidu，google）APP</li>
                                                <li><input type="checkbox">&nbsp;&nbsp;抓取搜索引擎先关搜索结果：baidu/google/sougo/soso/bing
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="k_r_under over">
                            <div class="download over ">
                                <%--<a id="addAll" href="#"> 添加全部</a>--%><a href="javascript: downloadCSV();">下载全部</a>
                            </div>
                            <div class="list2">
                                <table border="0" cellspacing="0">
                                    <thead>
                                    <tr class="list2_top">
                                        <td>
                                            <ul>
                                                <li><span>种子词</span></li>
                                                <li><span>关键词</span></li>
                                                <li><span>日均搜索量</span></li>
                                                <li><span>竞争激烈程度</span></li>
                                                <li><span>一级推荐理由</span></li>
                                                <li><span>二级推荐理由</span></li>
                                                <li><span>是否已购买</span></li>
                                            </ul>
                                        </td>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>

                            </div>
                        </div>
                        <div class="page2">
                            <a href="javascript:toPrevPage();">上一页</a><a href="javascript:toNextPage();">下一页</a><span
                                style="margin-right:10px;">跳转到 <input type="text" class="price"></span>&nbsp;&nbsp;<a
                                href="javascript:toAnyPage();"> GO</a>

                        </div>
                    </div>
                </div>
            </div>
            <jsp:include page="../homePage/pageBlock/footer.jsp"/>
        </div>
    </div>
</div>
<%--底部弹窗--%>
<div id="TB_overlayBG"></div>

<div class="team_box" id="team_box" style="display: none">
    <div id="list01_top" class="list01_top over" style="height: 30px">
        <span>智能分组</span>
        <a href="#" class="question"></a>
        <a href="#" class="close" style="display:block;float:right;">关闭</a>
    </div>
    <div id="group_list" class="team over">
        <ul>
            <!--
            <li>
                <div class="team_top">
                    <span class="fl"> 新建计划>小商品</span>
                    <a href="#" class="fr">编辑</a>
                </div>
                <div class="team_under">
                    <ul>
                        <li class="current"><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr team"
                                                                                             type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                    </ul>
                </div>

            </li>
            <li>
                <div class="team_top">
                    <span class="fl"> 新建计划>小商品</span>
                    <a href="#" class="fr">编辑</a>
                </div>
                <div class="team_under">
                    <ul>
                        <li class="current"><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr"
                                                                                             type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                    </ul>
                </div>

            </li>
            <li>
                <div class="team_top">
                    <span class="fl"> 新建计划>小商品</span>
                    <a href="#" class="fr">编辑</a>
                </div>
                <div class="team_under">
                    <ul>
                        <li class="current"><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr"
                                                                                             type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                    </ul>
                </div>

            </li>
            <li>
                <div class="team_top">
                    <span class="fl"> 新建计划>小商品</span>
                    <a href="#" class="fr">编辑</a>
                </div>
                <div class="team_under">
                    <ul>
                        <li class="current"><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr"
                                                                                             type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                    </ul>
                </div>

            </li>
            <li>
                <div class="team_top">
                    <span class="fl"> 新建计划>小商品</span>
                    <a href="#" class="fr">编辑</a>
                </div>
                <div class="team_under">
                    <ul>
                        <li class="current"><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr"
                                                                                             type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                    </ul>
                </div>

            </li>
            <li>
                <div class="team_top">
                    <span class="fl"> 新建计划>小商品</span>
                    <a href="#" class="fr">编辑</a>
                </div>
                <div class="team_under">
                    <ul>
                        <li class="current"><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr"
                                                                                             type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                    </ul>
                </div>

            </li>
            <li>
                <div class="team_top">
                    <span class="fl"> 新建计划>小商品</span>
                    <a href="#" class="fr">编辑</a>
                </div>
                <div class="team_under">
                    <ul>
                        <li class="current"><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr"
                                                                                             type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                    </ul>
                </div>

            </li>
            <li>
                <div class="team_top">
                    <span class="fl"> 新建计划>小商品</span>
                    <a href="#" class="fr">编辑</a>
                </div>
                <div class="team_under">
                    <ul>
                        <li class="current"><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr"
                                                                                             type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                        <li><a class="fl" href="#">浙江义乌小商品批发市场附近酒店</a><input class="fr" type="button"></li>
                    </ul>
                </div>

            </li>
            -->

        </ul>

    </div>
    <div class="main_bottom">
        <div class="w_list03">

            <ul>
                <li id="save_auto_group" class="current">保存</li>
                <li class="close">取消</li>

            </ul>
        </div>
    </div>

</div>
<iframe id="downloadhelper_iframe" style="display: none">#document</iframe>
<script type="text/javascript" src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-ui-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/tc-min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript">

var type = "bd";    //bd, baidu; p, local

var krFileId;

var skip = 0;

var limit = 10;

var total = 0;

window.onload = function () {
    rDrag.init(document.getElementById('list01_top'));
};

String.prototype.trim2 = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};
var F = function (objid) {
    return document.getElementById(objid).value;
};
var G = function (objid) {
    return document.getElementById(objid);
};

$(function () {
    var $tab_li = $('.tab_menu2 li');
    $('.tab_menu2 li').click(function () {
        $(this).addClass('current').siblings().removeClass('current');
        var index = $tab_li.index(this);
        $('div.table_concent2  > div').eq(index).show().siblings().hide();
    });

    $.getJSON("/campaign/getAllCampaign", null, function (data) {
        if (data.rows.length > 0) {
            var campaigns = "", datas = data.rows;
            campaigns += "<option value='' selected='selected'>请选择推广单元</option>";
            for (var i = 0, l = datas.length; i < l; i++)
                campaigns += "<option value=" + datas[i].campaignId + ">" + datas[i].campaignName + "</option>";
            $("#campaign").empty();
            $("#campaign").append(campaigns);
        }
    });

    $("#campaign").change(function () {
        var campaignId = $("#campaign option:selected").val();
        $.getJSON("/adgroup/getAdgroupByCampaignId/" + campaignId,
                {
                    campaignId: campaignId,
                    skip: 0,
                    limit: 100
                },
                function (data) {
                    var adgroups = "", datas = data.rows;
                    adgroups += "<option value='' selected='selected'>请选择推广单元</option>";
                    for (var i = 0, l = datas.length; i < l; i++)
                        adgroups += "<option value=" + datas[i].adgroupId + ">" + datas[i].adgroupName + "</option>";
                    $("#adgroup").empty();
                    $("#adgroup").append(adgroups);
                });
    });

    $("#addAll").on('click', function () {
        addAllWords();
    });

    $("#textarea1").change(function () {
        var seedWords = $("#textarea1").val().split("\n");
        $("#textarea1").parent().next().text("可输入词根" + (10 - seedWords.length) + "/10");
    });

    $("#save_auto_group").on('click', function () {
        saveAutoGroup();
    });

    //弹窗
    $(".team03").click(function () {
        autoGroup();

        $("#TB_overlayBG").css({
            display: "block", height: $(document).height()
        });

        $("#team_box").css({
            display: "block"
        });
    });
    $(".close").click(function () {
        $("#TB_overlayBG").css("display", "none");
        $("#team_box ").css("display", "none");
    });
});


var downloadCSV = function () {
    var _url = "/getKRWords/downloadCSV?trade=" + "电商行业" + "&category=" + "团购";
    document.getElementById("downloadhelper_iframe").src = _url;
};

var findWordFromBaidu = function () {
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
            "krFileId": krFileId
        },
        success: function (data, textStatus, jqXHR) {
            $("tbody").empty();
            if (data.rows.length > 0) {
                krFileId = data.krFileId;
                total = data.total;
                var _class = "";
                $.each(data.rows, function (i, item) {
                    if (i % 2 == 0) {
                        _class = "list2_box1";
                    } else {
                        _class = "list2_box2";
                    }

                    var newTr = "<tr class='" + _class + "'><td><ul>" +
                            "<li>" + item.seedWord + "</li>" +
                            "<li>" + item.keywordName + "</li>" +
                            "<li>" + item.dsQuantity + "</li>" +
                            "<li>" + item.competition + "</li>" +
                            "<li>" + item.recommendReason1 + "</li>" +
                            "<li>" + item.recommendReason2 + "</li>" +
                            "<li>" + "否" + "</li>" +
                            "</ul></td></tr>";
                    $("tbody").append(newTr);
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

    $.ajax({
        url: "/getKRWords/" + type,
        type: "GET",
        async: false,
        data: {
            "seedWords": getSeedWords,
            "skip": skip,
            "limit": limit,
            "krFileId": krFileId
        },
        success: function (data, textStatus, jqXHR) {
            $("tbody").empty();
            if (data.rows.length > 0) {
                krFileId = data.krFileId;
                var _class = "";
                $.each(data.rows, function (i, item) {
                    if (i % 2 == 0) {
                        _class = "list2_box1";
                    } else {
                        _class = "list2_box2";
                    }

                    var newTr = "<tr class='" + _class + "'><td><ul>" +
                            "<li>" + item.seedWord + "</li>" +
                            "<li>" + item.keywordName + "</li>" +
                            "<li>" + item.dsQuantity + "</li>" +
                            "<li>" + item.competition + "</li>" +
                            "<li>" + item.recommendReason1 + "</li>" +
                            "<li>" + item.recommendReason2 + "</li>" +
                            "<li>" + "否" + "</li>" +
                            "</ul></td></tr>";
                    $("tbody").append(newTr);
                });
            }
        }
    });
};

var toNextPage = function () {
    if (skip + 2 > total / limit) {
        return;
    }

    skip++;

    $.ajax({
        url: "/getKRWords/" + type,
        type: "GET",
        async: false,
        data: {
            "seedWords": getSeedWords,
            "skip": skip,
            "limit": limit,
            "krFileId": krFileId
        },
        success: function (data, textStatus, jqXHR) {
            $("tbody").empty();
            if (data.rows.length > 0) {
                krFileId = data.krFileId;
                var _class = "";
                $.each(data.rows, function (i, item) {
                    if (i % 2 == 0) {
                        _class = "list2_box1";
                    } else {
                        _class = "list2_box2";
                    }

                    var newTr = "<tr class='" + _class + "'><td><ul>" +
                            "<li>" + item.seedWord + "</li>" +
                            "<li>" + item.keywordName + "</li>" +
                            "<li>" + item.dsQuantity + "</li>" +
                            "<li>" + item.competition + "</li>" +
                            "<li>" + item.recommendReason1 + "</li>" +
                            "<li>" + item.recommendReason2 + "</li>" +
                            "<li>" + "否" + "</li>" +
                            "</ul></td></tr>";
                    $("tbody").append(newTr);
                });
            }
        }
    });
};

var toAnyPage = function () {
    skip = $(".price").val();
    if ((skip > (total / limit)) || skip <= 0) {
        return;
    }

    skip--;

    $.ajax({
        url: "/getKRWords/" + type,
        type: "GET",
        async: false,
        data: {
            "seedWords": getSeedWords,
            "skip": skip,
            "limit": limit,
            "krFileId": krFileId
        },
        success: function (data, textStatus, jqXHR) {
            $("tbody").empty();
            if (data.rows.length > 0) {
                krFileId = data.krFileId;
                var _class = "";
                $.each(data.rows, function (i, item) {
                    if (i % 2 == 0) {
                        _class = "list2_box1";
                    } else {
                        _class = "list2_box2";
                    }

                    var newTr = "<tr class='" + _class + "'><td><ul>" +
                            "<li>" + item.groupName + "</li>" +
                            "<li>" + item.seedWord + "</li>" +
                            "<li>" + item.keywordName + "</li>" +
                            "<li>" + item.dsQuantity + "</li>" +
                            "<li>" + item.competition + "</li>" +
                            "<li>" + item.recommendReason1 + "</li>" +
                            "<li>" + item.recommendReason2 + "</li>" +
                            "<li>" + "否" + "</li>" +
                            "</ul></td></tr>";
                    $("tbody").append(newTr);
                });
            }
        }
    });
};

//添加全部关键词到左侧textarea
var addAllWords = function () {
    $.ajax({
        url: "/getKRWords/" + type,
        type: "GET",
        async: false,
        data: {
            "seedWords": getSeedWords,
            "skip": skip,
            "limit": 500,
            "krFileId": krFileId
        },
        success: function (data, textStatus, jqXHR) {
            if (data.rows.length > 0) {
                krFileId = data.krFileId;
                $.each(data.rows, function (i, item) {
                    if (i == 0) {
                        var str = $("#c2").val();
                        if (str == null || str.trim().length == 0) {
                            $("#c2").val(item.keywordName);
                            keyUp();
                        }
                        else {
                            $("#c2").val(str + "\n" + item.keywordName);
                            keyUp();
                        }
                        return true;
                    }
                    var _str = $("#c2").val();
                    $("#c2").val(_str + "\n" + item.keywordName);
                    keyUp();
                });
            }
        }
    });
};

//自动分组
var autoGroup = function () {
    var str = $("#c2").text();
    if (str == null || str.trim().length == 0) {
        alert("没有合法的关键词可供分组");
        return;
    }
    var _str = str.split("\n");
    var words = "";
    for (var i = 0, l = _str.length; i < l; i++) {
        if (i == 0) {
            words += _str[i];
            continue;
        }
        words += ";" + _str[i];
    }

    $.ajax({
        url: "/getKRWords/group",
        type: "POST",
        data: {
            "words": words
        },
        async: false,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            $("#group_list ul").empty();
            $.each(data.rows, function (i, item) {
                var _li = "" +
                        "<li>" +
                        "<div class='team_top'>" +
                        "<span class='fl'> 新建计划>" + item.adgroupName + "</span>" +
                        "<a href='#' class='fr'>编辑</a>" +
                        "</div>" +
                        "<div class='team_under'>" +
                        "<ul>";
                var _keywords = item.keywords;
                var lis = "";
                for (var j = 0, l = _keywords.length; j < l; j++) {
                    if (j == 0) {
                        lis = "<li class='current'><a class='fl' href='#'>" + _keywords[j] + "</a><input class='fr team' type='button'></li>";
                        continue;
                    }
                    lis += "<li><a class='fl' href='#'>" + _keywords[j] + "</a><input class='fr team' type='button'></li>";
                }
                _li += lis + "</ul></div></li>";
                $("#group_list>ul").append(_li);
            });
        }
    });
};

var saveAutoGroup = function () {
    var adgroups = [];
    var keywords = [];
    var lis1 = $("#group_list>ul>li");
    $.each(lis1, function (i) {
        var adgroup = {};
        var adgroupName = $(this).find("span").text();
        adgroupName = adgroupName.substring(adgroupName.indexOf(">") + 1);
        adgroup["adgroupId"] = (i + 1);
        adgroup["adgroupName"] = adgroupName;
        adgroups.push(adgroup);
        var lis2 = $(this).find("ul>li");
        $.each(lis2, function () {
            var keywordEntity = {};
            keywordEntity["adgroupId"] = (i + 1);
            keywordEntity["keyword"] = $(this).find("a").text();
            keywords.push(keywordEntity);
        });
    });

    $.ajax({
        url: "/getKRWords/addAdgroups",
        type: "POST",
        data: JSON.stringify(adgroups),
        async: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data, textStatus, jqXHR) {
        }
    });

    $.ajax({
        url: "/getKRWords/addKeywords",
        type: "POST",
        data: JSON.stringify(keywords),
        async: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data, textStatus, jqXHR) {
            alert(data.statusText);
        }
    });
};

</script>
<!-- textarea js -->
<script type="text/javascript">
    var msgA = ["msg1", "msg2", "msg3", "msg4"];
    var c = ["c1", "c2", "c3", "c4"];
    var slen = [50, 20000, 20000, 60];  //最大可设置行数
    var num = "";
    var isfirst = [0, 0, 0, 0, 0, 0];
    function isEmpty(strVal) {
        return strVal == "";
    }
    function isBlank(testVal) {
        var regVal = /^\s*$/;
        return (regVal.test(testVal))
    }
    function chLen(strVal) {
        strVal = strVal.trim2();
        var cArr = strVal.match(/[^\x00-\xff]/ig);
        return strVal.length + (cArr == null ? 0 : cArr.length);
    }
    function check(i) {
        var iValue = F("c" + i);
        var iObj = G("msg" + i);
        var n = (chLen(iValue) > slen[i - 1]);
        if (iObj != null) {
            if ((isBlank(iValue) == true) || (isEmpty(iValue) == true) || n == true) {
                iObj.style.display = "block";
            } else {
                iObj.style.display = "none";
            }
        }
    }
    function checkAll() {
        for (var i = 0; i < msgA.length; i++) {
            check(i + 1);
            if (G(msgA[i]).style.display == "none") {
                continue;
            } else {
                return;
            }
        }
    }
    function clearValue(i) {
        G(c[i - 1]).style.color = "#000";
        keyUp();
        if (isfirst[i] == 0) {
            //G(c[i - 1]).value = "";
        }
        isfirst[i] = 1;
    }
    function keyUp() {
        var obj = G("c2");
        var str = obj.value;
        str = str.replace(/\r/gi, "");
        str = str.split("\n");
        n = str.length;
        line(n);
    }
    function line(n) {
        var lineobj = G("li");
        for (var i = 1; i <= n; i++) {
            if (document.all) {
                num += i + "\r\n";
            } else {
                num += i + "\n";
            }
        }
        lineobj.value = num;
        num = "";
    }
    function autoScroll() {
        var nV = 0;
        if (!document.all) {
            nV = G("c2").scrollTop;
            G("li").scrollTop = nV;
            setTimeout("autoScroll()", 20);
        }
    }
    if (!document.all) {
        window.addEventListener("load", autoScroll, false);
    }
</script>
</body>
</html>