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

    </style>

</head>
<body>
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
                                    <div class="k_r_under over">
                                        <div class="download over ">
                                            <a href="javascript: downloadCSV();">下载全部</a>
                                        </div>
                                        <div class="list3 over">
                                            <table border="0" cellspacing="0">
                                                <thead>
                                                <tr class="list2_top">
                                                    <td><span>分组</span></td>
                                                    <td><span>种子词</span></td>
                                                    <td><span>关键词</span></td>
                                                    <td><span>日均搜索量</span></td>
                                                    <td><span>竞争激烈程度</span></td>
                                                    <td><span>一级推荐理由</span></td>
                                                    <td><span>二级推荐理由</span></td>
                                                </tr>
                                                </thead>
                                                <tbody id="tbody1">
                                                </tbody>
                                            </table>

                                        </div>
                                    </div>
                                    <div class="page2">
                                        <a href="javascript:toPrevPage();">上一页</a><a
                                            href="javascript:toNextPage();">下一页</a><span
                                            style="margin-right:10px;">跳转到 <input type="text" class="price"></span>&nbsp;&nbsp;<a
                                            href="javascript:toAnyPage();"> GO</a>
                                        <a href="#"><span>共计</span><span><b
                                                id="totalPage1"></b></span><span>页</span></a>
                                    </div>
                                </div>
                                <div class="k_r_top2  hides over">
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
                                            <a href="javascript:findWordFromPerfect();" class="become2">开始拓词</a>
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
                                    <div class="k_r_under over">
                                        <div class="download over ">
                                            <a href="javascript: downloadCSV();">下载全部</a>
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
                                                <tbody id="tbody2">
                                                </tbody>
                                            </table>

                                        </div>
                                    </div>
                                    <div class="page2">
                                        <a href="javascript:toPrevPage();">上一页</a><a
                                            href="javascript:toNextPage();">下一页</a><span
                                            style="margin-right:10px;">跳转到 <input type="text" class="price"></span>&nbsp;&nbsp;<a
                                            href="javascript:toAnyPage();"> GO</a>
                                        <a href="#"><span>共计</span><span><b
                                                id="totalPage2"></b></span><span>页</span></a>
                                    </div>

                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <jsp:include page="../homePage/pageBlock/footer.jsp"/>
        </div>
    </div>
</div>
<iframe id="downloadhelper_iframe" style="display: none">#document</iframe>
<script type="text/javascript" src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-ui-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.livequery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript">

var type = 1;    //1, baidu; 2, perfect

var krFileId;

var skip = 0;

var limit = 10;

var total = 0;

var _trade;

var _category;

$(function () {
    var $tab_li = $('.tab_menu2 li');
    $('.tab_menu2 li').click(function () {
        $(this).addClass('current').siblings().removeClass('current');
        var index = $tab_li.index(this);
        $('div.table_concent2  > div').eq(index).show().siblings().hide();
    });

    $("#trade").change(function () {
        var trade = $("#trade option:selected").val();
        _trade = trade;
        $.getJSON("/getKRWords/getCategories",
                {trade: trade},
                function (data) {
                    var category = "", datas = data.rows;
                    category += "<option value='' selected='selected'></option>";
                    for (var i = 0, l = datas.length; i < l; i++) {
                        if (i == 0) {
                            category += "<option value='" + datas[i].category + "' selected='selected'>" + datas[i].category + "</option>";
                            _category = datas[i].category;
                            continue;
                        }
                        category += "<option value='" + datas[i].category + "'>" + datas[i].category + "</option>";
                    }

                    $("#category").empty();
                    $("#category").append(category);//休闲鞋-疑问-什么品牌好
                });
    });

    $("#category").change(function () {
        var category = $("#category option:selected").val();
        _category = category;
    });

    $("#textarea1").livequery('keyup', function () {
        var seedWords = $("#textarea1").val().trim().split("\n");
        $("#textarea1").parent().next().text("可输入词根" + (100 - seedWords.length) + "/100");
    });
});


var downloadCSV = function () {
    var _url;
    if (type == 1) {
        $.getJSON("/getKRWords/getBaiduCSVFilePath",
                {krFileId: krFileId},
                function (data) {
                    _url = data.path;
                    document.getElementById("downloadhelper_iframe").src = _url;
                });
    } else if (type == 2) {
        _url = "/getKRWords/downloadCSV?trade=" + _trade + "&category=" + _category;
        document.getElementById("downloadhelper_iframe").src = _url;
    }
};

var findWordFromBaidu = function () {
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
            "krFileId": krFileId
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
                            "<td>" + item.competition + "</td>" +
                            "<td>" + item.recommendReason1 + "</td>" +
                            "<td>" + item.recommendReason2 + "</td>" +
                            "</tr>";
                    $("#tbody1").append(newTr);
                });
            }
        }
    });
};

var findWordFromPerfect = function () {
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
                "krFileId": krFileId
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
                                "<td>" + item.competition + "</td>" +
                                "<td>" + item.recommendReason1 + "</td>" +
                                "<td>" + item.recommendReason2 + "</td>" +
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
                "krFileId": krFileId
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
                                "<td>" + item.competition + "</td>" +
                                "<td>" + item.recommendReason1 + "</td>" +
                                "<td>" + item.recommendReason2 + "</td>" +
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
    skip = $(".price").val();
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
                "krFileId": krFileId
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
                                "<td>" + item.competition + "</td>" +
                                "<td>" + item.recommendReason1 + "</td>" +
                                "<td>" + item.recommendReason2 + "</td>" +
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