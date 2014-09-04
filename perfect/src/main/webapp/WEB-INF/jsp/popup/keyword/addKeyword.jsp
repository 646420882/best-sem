<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2014-9-1
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <style type="text/css">

        .list4 table {
            border: 1px solid #eaf0f3;
            overflow: auto;
            width: 100%;
        }

        .zs_function {
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div style="background-color: #f3f5fd; width: 900px; height: 700px">
    <div class="assembly_top over">
        <div class="fl"><h3>关键词拼装</h3></div>
        <div class="fr">
            <span class="fr">推广地域<a href="#">使用账户推广地域</a></span>
            <span class="fr">高级设置<a href="#">未设置</a></span>
        </div>
    </div>
    <div class="assembly_under over">
        <div class="assembly_left">
            <div class="keyword_left fl over">
                <div class="k_l_top over">
                    <span>已添加关键词（1/500）</span> <a class="question" href="#"></a>
                </div>
                <div class="keyworld_text over">

                    <div class="keyworld_text2 fl">
                        <ul>
                            <li></li>
                        </ul>
                    </div>
                </div>
                <div class="k_l_under over">
                    <div class="k_l_under1 over">
                        <ul>
                            <li><select id="campaign" class="team04">
                            </select><select id="adgroup" class="team04">
                            </select></li>
                            <li><a href="#">保存</a></li>
                        </ul>
                    </div>
                </div>

            </div>

        </div>

        <div class="assembly_right fr  over">
            <div class="assembly_right_top over">
                <ul class="assembly_checkbox">
                    <li class="current">可能适合你的词</li>
                    <li>行业词</li>

                </ul>
                <div class="assembly_right_under over">
                    <div class="containers over">

                        <div class="assembly_search over">
                            搜索相关词 <input id="searchKeyword" type="text"/><a href="javascript: searchKeyword();">搜索</a>

                        </div>
                        <div class="list4">
                            <div class="zs_function over">
                                <ul class="fl">
                                    <li><a href="#" id="addKeyword1"><span class="zs_top"><img
                                            src="../public/img/zs_function1.png"></span><b>添加全部</b></a></li>

                                </ul>

                            </div>
                            <table width="100%" cellspacing="0" border="0" width="500px">
                                <thead>
                                <tr class="list02_top">
                                    <td><input id="checkbox1" type="checkbox"/></td>
                                    <td>&nbsp;关键词</td>
                                    <td>&nbsp;日均搜索量</td>
                                    <td>&nbsp;竞争激烈程度</td>
                                    <td>&nbsp;展现理由</td>
                                </tr>
                                </thead>
                                <tbody id="tbody1">
                                </tbody>

                            </table>
                        </div>
                        <div class="zhanghu_input"></div>

                    </div>
                    <div class="containers over hides">

                        <div class="assembly_top2 over">
                            <select id="trade">
                                <option value="" selected="selected">请选择行业</option>
                                <option value="电商">电商</option>
                                <option value="房产">房产</option>
                                <option value="教育">教育</option>
                                <option value="金融">金融</option>
                                <option value="旅游">旅游</option>
                            </select>
                            <select id="category">
                            </select>
                        </div>
                        <div class="zs_function over">
                            <ul class="fl">
                                <li><a href="#" id="addKeyword"><span class="zs_top"><img
                                        src="../public/img/zs_function1.png"></span><b>添加全部</b></a></li>

                            </ul>

                        </div>
                        <div class="list4">
                            <table width="100%" cellspacing="0" border="0" width="500px">
                                <thead>
                                <tr class="list02_top">
                                    <td><input type="checkbox"/></td>
                                    <td>&nbsp;行业</td>
                                    <td>&nbsp;计划</td>
                                    <td>&nbsp;单元</td>
                                    <td>&nbsp;关键词</td>
                                </tr>
                                </thead>
                                <tbody id="tbody2">
                                </tbody>

                            </table>
                        </div>

                    </div>
                </div>
            </div>

        </div>

    </div>


</div>
<script type="text/javascript" src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.livequery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript">
    $(function () {
        var $tab_li = $('.assembly_checkbox li');
        $('.assembly_checkbox li').click(function () {
            $(this).addClass('current').siblings().removeClass('current');
            var index = $tab_li.index(this);
            $('div.assembly_right_under > div').eq(index).show().siblings().hide();
        });

        //绑定键盘回车事件
        $('#searchKeyword').bind('keypress', function (event) {
            if (event.keyCode == "13") {
                searchKeyword();
            }
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

        $("#checkbox1").livequery('click', function () {
            if($("#checkbox1").is(':checked')){
                //全选
            }else {
                //全不选
            }
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
                        $("#category").append(category);
                    });
        });

        $("#category").change(function () {
            var category = $("#category option:selected").val();
            loadKeywordFromPerfect();
        });

    });

    var searchKeyword = function () {
        var seedWord = $("#searchKeyword").val();
        if (seedWord == null || seedWord.trim().length == 0) {
            return;
        }
        $.ajax({
            url: '/getKRWords/getKRbySeedWord',
            async: false,
            data: {
                seedWord: seedWord
            },
            dataType: 'json',
            success: function (data, textStatus, jqXHR) {
                var results = data.rows;
                if (results != null && results.length > 0) {
                    var trs = "";
                    var _class = "";
                    $.each(results, function (i, item) {
                        if (i % 2 == 0) {
                            _class = "list2_box1";
                        } else {
                            _class = "list2_box2";
                        }
                        var _tr = "<tr class='" + _class + "'><td><input type='checkbox' name='baiduKeyword'/></td>" +
                                "<td>" + item.word + "</td>" +
                                "<td>" + item.exactPV + "</td>" +
                                "<td>" + item.competition + "</td>" +
                                "<td>" + item.flag1 + "</td></tr>";
                        trs += _tr;
                    });
                    $("#tbody1").empty();
                    $("#tbody1").append(trs);
                }
            }
        });
    };

    var _trade = "";

    var _category = "";

    var total = 0;  //当前从系统搜索的关键词总数

    var loadKeywordFromPerfect = function () {
        var trade = $("#trade option:selected").val();
        var category = $("#category option:selected").val();

        _trade = trade;
        _category = category;
        $.ajax({
            url: "/getKRWords/p",
            type: "GET",
            data: {
                "trade": trade,
                "category": category,
                "skip": 0,
                "limit": 300
            },
            success: function (data, textStatus, jqXHR) {
                $("#tbody2").empty();
                if (data.rows.length > 0) {
                    total = data.total;
                    var _class = "";
                    $.each(data.rows, function (i, item) {
                        if (i % 2 == 0) {
                            _class = "list2_box1";
                        } else {
                            _class = "list2_box2";
                        }

                        var newTr = "<tr class='" + _class + "'><td><input type='checkbox' name='perfectKeyword'/></td>" +
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
</script>
</body>
</html>
