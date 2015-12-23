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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">
    <style type="text/css">

        .list4 table {
            border: 1px solid #eaf0f3;
            overflow: auto;
            min-width:562px;
        }
        .keyworld_text2 ul li{
            word-wrap: break-word;
        }
        .keyword_left ul li select{
            margin:20px 2% 0 2%;
            width:95%;
            height:24px;
            line-height:22px;
            border:1px solid #ccc;

        }
        .keyword_left ul li a ,.assembly_search a{
            display:block;
            margin:10px 10px 0  0;
            background:#ffbc04;
            height:24px;
            line-height:24px;
            padding:0 15px;
            color:#000;
        }
        .assembly_search a{
            margin:0 0 0 10px;

        }
        .assembly_search input{
            width:200px;
        }


    </style>
</head>
<body>
<div id="background" class="background hides"></div>
<div id="progressBar" class="progressBar hides"><span></span>数据加载中，请稍等...</div>
<div style="background-color: #f3f5fd; width: 900px; height: 700px">

    <div class="assembly_under over">

            <div class="keyword_left fl over" style="height:460px;">

                        <ul>
                            <li><select id="campaign" class="team04">
                            </select><select id="adgroup" class="team04">
                            </select></li>
                            <li>
                                <div class="assembly_search over">
                                    <span>
                                    <input id="price" type="text" placeholder="请输入关键词出价,默认为0.1" onkeypress='until.regDouble(this)' maxlength="5">
                                    </span>
                                </div>
                            </li>
                            <li>
                                <select id="matchType" class="team04">
                                    <option value="3">广泛</option>
                                    <option value="1">精确</option>
                                    <option value="2">短语</option>
                                </select>

                                <div id="phraseTypeDiv" style="display: none;"><select id="phraseType" class="team04">
                                    <option value="1">同义包含</option>
                                    <option value="2">精确包含</option>
                                    <option value="3">核心包含</option>
                                </select></div>
                            </li>
                            <li><a class="fr" href="javascript:saveKeyword();">保存</a></li>
                        </ul>

                </div>

        <div class="assembly_right fr  over">
            <div class="assembly_right_top over">
                <ul class="assembly_checkbox">
                    <li id="bWord" class="current">可能适合你的词</li>
                    <li id="tradeWord">行业词</li>
                </ul>
                <div class="assembly_right_under over">
                    <div class="containers over">
                        <div class="assembly_search over">
                            <span class="fl">搜索相关词 <input id="searchKeyword" type="text"/></span><a class="fl"  href="javascript: searchKeyword();">搜索</a>
                        </div>
                        <div class="list4" style="height: 336px">

                            <table style="width: 100%; overflow-y: auto" cellspacing="0" border="0">
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
                        <%--<div class="zs_function over">
                            <ul class="fl">
                                <li><a href="#" id="addKeyword"><span class="zs_top"><img
                                        src="../public/img/zs_function1.png"></span><b>添加全部</b></a></li>
                            </ul>
                        </div>--%>
                        <div class="list4" style="height: 358px">
                            <table style="width: 100%; overflow-y: auto" cellspacing="0" border="0">
                                <thead>
                                <tr class="list02_top">
                                    <td><input id="checkbox2" type="checkbox"/></td>
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
    <%--alert提示类--%>
    <div class="box alertBox" style=" width: 230px;display:none;z-index: 1005" id="addKeyAlertPrompt">
        <h2 id="addKeyAlertPromptTitle">
            <span class="fl alert_span_title" id="addKeyAlertPrompt_title"></span>
            <%--<a href="#" class="close">×</a></h2>--%>
        <%--<a href="#" onclick="addKeyAlertPrompt.hide()" style="color: #cccccc;float: right;font-size: 20px;font-weight: normal;opacity: inherit;text-shadow: none;">×</a></h2>--%>
        </h2>
        <div class="mainlist">
            <div class="w_list03">
                <ul class="zs_set">
                    <li class="current" onclick="addKeyAlertPrompt.hide()">确认</li>
                </ul>
            </div>
        </div>
    </div>

</div>
<script type="text/javascript" src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.livequery.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/json2/20140204/json2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/tc.min.js"></script>
<script type="text/javascript">
    /*智能竞价中的alert提示*/
    var addKeyAlertPrompt = {
        show:function(content){
            $("#addKeyAlertPrompt_title").html(content);
            $(".TB_overlayBG_alert").css({
                display: "block", height: $(document).height()
            });/*蒙版显示*/
            $("#addKeyAlertPrompt").css({
                left: ($("body").width() - $("#addKeyAlertPrompt").width()) / 2 - 20 + "px",
                top: ($(window).height() - $("#addKeyAlertPrompt").height()) / 2 + $(window).scrollTop() + "px",
                display: "block"
            });/*显示提示DIV*/
        },
        hide:function(){
            $(".TB_overlayBG_alert").css({
                display: "none"
            });/*蒙版显示*/
            $("#addKeyAlertPrompt").css({
                display: "none"
            });/*显示提示DIV*/
        }
    }
$(function () {
    rDrag.init(document.getElementById('addKeyAlertPromptTitle'));
    $("#matchType ").change(function () {
        if (this.value == "2") {
            $("#phraseTypeDiv").show();
        } else {
            $("#phraseTypeDiv").hide();
        }
    });
    var $tab_li = $('.assembly_checkbox li');
    $('.assembly_checkbox li').click(function () {
        wordType = $(this).attr("id");
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
            campaigns += "<option value='' selected='selected'>请选择推广计划</option>";
            for (var i = 0, l = datas.length; i < l; i++) {
                var _campaignId = "";
                if (datas[i].campaignId != null) {
                    _campaignId = datas[i].campaignId;
                } else {
                    _campaignId = datas[i].id;
                }
                campaigns += "<option value=" + _campaignId + ">" + datas[i].campaignName + "</option>";
            }
            $("#campaign").empty();
            $("#campaign").append(campaigns);
        }
    });

    $("#campaign").change(function () {
        var campaignId = $("#campaign option:selected").val();
        if (campaignId.length < 24) {
            $.getJSON("/adgroup/getAdgroupByCampaignId/" + campaignId,
                    {
                        campaignId: campaignId,
                        skip: 0,
                        limit: 100
                    },
                    function (data) {
                        var adgroups = "", datas = data.rows;
                        adgroups += "<option value='' selected='selected'>请选择推广单元</option>";
                        for (var i = 0, l = datas.length; i < l; i++) {
                            var _adgroupId = "";
                            if (datas[i].adgroupId != null) {
                                _adgroupId = datas[i].adgroupId;
                            } else {
                                _adgroupId = datas[i].id;
                            }
                            adgroups += "<option value=" + _adgroupId + ">" + datas[i].adgroupName + "</option>";
                        }
                        $("#adgroup").empty();
                        $("#adgroup").append(adgroups);
                    });
        } else {
            $.getJSON("/adgroup/getAdgroupByCampaignObjId/" + campaignId,
                    {
                        campaignId: campaignId,
                        skip: 0,
                        limit: 100
                    },
                    function (data) {
                        var adgroups = "", datas = data.rows;
                        adgroups += "<option value='' selected='selected'>请选择推广单元</option>";
                        for (var i = 0, l = datas.length; i < l; i++) {
                            adgroups += "<option value=" + datas[i].id + ">" + datas[i].adgroupName + "</option>";
                        }
                        $("#adgroup").empty();
                        $("#adgroup").append(adgroups);
                    });
        }

    });

    $("#checkbox1").livequery('click', function () {
        var c1 = $("input[name=baiduKeyword]:checkbox");
        var l = c1.length;
        if ($("#checkbox1").is(':checked')) {
            for (var i = 0; i < l; i++) {
                c1[i].checked = true;
            }
        } else {
            for (var j = 0; j < l; j++) {
                c1[j].checked = false;
            }
        }
    });

    $("#checkbox2").livequery('click', function () {
        var c2 = $("input[name=perfectKeyword]:checkbox");
        var l = c2.length;
        if ($("#checkbox2").is(':checked')) {
            for (var i = 0; i < l; i++) {
                c2[i].checked = true;
            }
        } else {
            for (var j = 0; j < l; j++) {
                c2[j].checked = false;
            }
        }
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
                    loadKeywordFromPerfect();
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
                    var _tr = "<tr id='" + (wordType + i) + "' class='" + _class + "'><td><input type='checkbox' name='baiduKeyword'/></td>" +
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

var wordType = "bWord";

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

                    var newTr = "<tr id='" + (wordType + i) + "' class='" + _class + "'><td><input type='checkbox' name='perfectKeyword'/></td>" +
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

var saveKeyword = function () {

    var campaignId = $("#campaign option:selected").val();
    if (campaignId == null || campaignId.length == 0) {
//        alert("请选择推广计划!");
        addKeyAlertPrompt.show("请选择推广计划!");
        return;
    }
    var adgroupId = $("#adgroup option:selected").val();
    if (adgroupId == null || adgroupId.length == 0) {
//        alert("请选择推广单元!");
        addKeyAlertPrompt.show("请选择推广单元!");
        return;
    }
    var price=$("#price").val();
    if(price!=""){
        if(!/^-?\d+\.?\d*$/.test(price)){
//            alert("输入正确的关键词出价！");
            addKeyAlertPrompt.show("输入正确的关键词出价！");
            return;
        }else{
            if(parseFloat(price)>999.9){
//                alert("关键词出价为：(0,999.9]<=出价&&<计划预算!");
                addKeyAlertPrompt.show("关键词出价为：(0,999.9]<=出价&&<计划预算!");
                return;
            }
        }
    }else{
        price=0.1;
    }
    var matchType = $("#matchType :selected").val();
    var phraseType = $("#phraseType :selected").val();

    //获取所有选中的关键词
    var jsonArr = [];
    if (wordType == "bWord") {
        var checkedTds1 = $("input[name=baiduKeyword]:checkbox");
        for (var i = 0, l = checkedTds1.length; i < l; i++) {
            if (checkedTds1[i].checked) {
                var entity1 = {};
                entity1["accountId"] = "${sessionScope._accountId}";
                if (adgroupId.length < 24) {
                    entity1["adgroupId"] = adgroupId;
                } else {
                    entity1["adgroupObjId"] = adgroupId;
                }
                entity1["keyword"] = $("#" + wordType + i).find("td").eq(1).text();
                entity1["price"] =price ;
                entity1["matchType"] = matchType;
                entity1["pause"] = false;
                entity1["status"] = -1;
                if (matchType == "2") {
                    entity1["phraseType"] = phraseType;
                }else{
                    entity1["phraseType"] = 1;
                }
                entity1["localStatus"] = 1;
                jsonArr.push(entity1);
            }
        }
    } else {
        var checkedTds2 = $("input[name=perfectKeyword]:checkbox");
        for (var j = 0, m = checkedTds2.length; j < m; j++) {
            if (checkedTds2[j].checked) {
                var entity2 = {};
                entity2["accountId"] = "${sessionScope._accountId}";
                if (adgroupId.length < 24) {
                    entity2["adgroupId"] = adgroupId;
                } else {
                    entity2["adgroupObjId"] = adgroupId;
                }
                entity2["keyword"] = $("#" + wordType + j).find("td").eq(4).text();
                entity2["price"] =price ;
                entity2["matchType"] = matchType;
                entity2["pause"] = false;
                entity2["status"] = -1;
                if (matchType == "2") {
                    entity2["phraseType"] = phraseType;
                }else{
                    entity2["phraseType"] = 1;
                }
                entity2["localStatus"] = 1;
                jsonArr.push(entity2);
            }
        }
    }

    if(jsonArr.length == 0){
//        alert("您没有选择关键词!");
        addKeyAlertPrompt.show("您没有选择关键词!");
        return;
    }

    $.ajax({
        url: "/keyword/add",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(jsonArr),
        async: false,
        contentType: "application/json; charset=UTF-8",
        success: function (data, textStatus, jqXHR) {
            if(data.stat==true){
//                alert("添加成功");
                addKeyAlertPrompt.show("添加成功");
                top.dialog.getCurrent().close().remove();
            }
        }
    });

};


//loading
var ajaxbg = $("#background,#progressBar");
ajaxbg.hide();
$(document).ajaxStart(function () {
    ajaxbg.show();
});
$(document).ajaxStop(function () {
    ajaxbg.fadeOut(1000);
});



</script>
</body>
</html>
