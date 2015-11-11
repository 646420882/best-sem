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
            width: 100%;
        }

        .zs_function {
            margin-top: 10px;
        }

        .keyworld_text2 {
            height: 320px;
        }

        .assembly_under {
            height: 500px

        }
    </style>
</head>
<body>
<div id="background" class="background hides"></div>
<div id="progressBar" class="progressBar hides">数据处理中，请稍等...</div>
<div style="background-color: #f3f5fd; width: 900px; height: 700px">
    <div class="addplan_top over">
        <ul id="tabUL">
            <li class="current">1、推广业务描述</li>
            <li><span></span>1、关键词筛选</li>
        </ul>
    </div>
    <div class="plan_under">
        <div class="containers inputCreateInfo">
            <div class="plan_under2">
                <ul>
                    <li>
                        <h3>简单的描述您从事的业务</h3>
                        <input type="text" id="inputKwd" style="color: #808080" onfocus="this.value=''"
                               value="多个以逗号分隔，如:雅思,出国">
                    </li>
                    <li><h3>希望哪里的客户看到您的商品或服务</h3>

                        <p>比如你是北京学校的老板，想让北京学英语的客户看到</p>

                        <p><span>投放地域</span>&nbsp;&nbsp;&nbsp;<span>使用账户推广地域</span><a href="#" id="updateRegion">修改</a>
                        </p>
                    </li>
                    <li><h3>计划每天花费多少让百度带来新客户</h3>

                        <p>该消费只会帮助我们提供更适合的方案给您，不会对您的预算造成任何影响</p>

                        <p><select id="selectPrice">
                            <option value="please">请选择</option>
                            <option value="51-100">51-100元</option>
                            <option value="101-200">101-200元</option>
                            <option value="201-500">201-500元</option>
                            <option value="501-1000">501-1000元</option>
                            <option value="1001-2000">1001-2000元</option>
                            <option value="2001-4000">2001-4000元</option>
                            <option value="4001-8000">4001-8000元</option>
                            <option value="8000-">8000以上</option>
                        </select>
                        </p>
                        <span id="mes"></span>
                    </li>
                </ul>
            </div>
            <div class="main_bottom" style="margin:0px; background:none;">
                <div class="w_list03">
                    <ul>
                        <li class="current nextStep" id="downloadAccount">下一步</li>
                        <li class="close" onclick="closeDialog();">取消</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="containers over hides chooseKwd">
            <div class="assembly_under over">
                <div class="assembly_left">
                    <div class="keyword_left fl over">
                        <div class="k_l_top over">
                            <span id="keywordCount">已添加关键词（0/500）</span> <a class="question" href="#"></a>
                        </div>
                        <div class="keyworld_text over">

                            <div class="keyworld_text2 fl" style="height:440px;">
                                <textarea style="width: 98%;height:100%;font-size:12px;" id="addedkwd"></textarea>
                            </div>
                        </div>
                        <%-- <div class="k_l_under over">
                             <div class="k_l_under1 over">
                                 <ul>
                                     <li><input class="zs_input3" placeholder="根据已添加关键词精准搜词">
                                     </li>
                                 </ul>
                             </div>
                         </div>--%>
                    </div>
                </div>

                <div class="assembly_right fr  over">
                    <div class="assembly_right_top over">
                        <div class="assembly_right_under over">
                            <div class="containers over">
                                <div class="assembly_search over">
                                    <span class="fl">搜索相关词 <input id="searchKeyword" type="text" style="width:300px; margin-right:5px;"/></span><a class="fl"
                                                                                                            href="javascript: clickSearch();">搜索</a>
                                </div>

                                <div class="zs_function over">
                                    <ul class="fl">
                                        <li><a href="#" id="addAllKeyword"><span class="zs_top"><img
                                                src="../public/img/zs_function1.png"></span><b>添加全部</b></a></li>
                                        <li><a href="#" id="addChooseKeyword"><span class="zs_top"><img
                                                src="../public/img/zs_function1.png"></span><b>添加选中</b></a></li>
                                    </ul>
                                </div>
                                <div class="list4" style="height: 336px">
                                    <table style="width: 100%; overflow-y: auto" cellspacing="0" border="0">
                                        <thead>
                                        <tr class="list02_top">
                                            <td>&nbsp;</td>
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
                        </div>
                    </div>
                    <div class="main_bottom" style="margin:0px;  background:none;">
                        <div class="w_list03">
                            <ul>
                                <li class="current lastStep">上一步</li>
                                <li onclick="javascript:saveChooseKeyword();">完成</li>
                                <%--<li class="close">取消</li>--%>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%--alert提示类--%>
<div class="box7" style=" width: 230px;display:none;z-index: 1001" id="quickCreatePlanAlertPrompt">
    <h2>
        <span class="fl" id="quickCreatePlanAlertPrompt_title"></span>
        <a href="#" class="close">×</a></h2>
    <div class="mainlist">
        <div class="w_list03">
            <ul class="zs_set">
                <li class="current" onclick="quickCreatePlanAlertPrompt.hide()">确认</li>
            </ul>
        </div>
    </div>
</div>
<jsp:include page="./updateRegionTarget.jsp"/>
<script type="text/javascript" src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/json2/20140204/json2.min.js"></script>

<script type="text/javascript">
    /*智能竞价中的alert提示*/
    var quickCreatePlanAlertPrompt = {
        show:function(content){
            $(".TB_overlayBG").css({
                display: "block", height: $(document).height()
            });/*蒙版显示*/
            $("#quickCreatePlanAlertPrompt").css({
                left: ($("body").width() - $("#download").width()) / 2 - 20 + "px",
                top: ($(window).height() - $("#download").height()) / 2 + $(window).scrollTop() + "px",
                display: "block"
            });/*显示提示DIV*/
            $("#quickCreatePlanAlertPrompt_title").html(content);
        },
        hide:function(){
            $(".TB_overlayBG").css({
                display: "none"
            });/*蒙版显示*/
            $("#quickCreatePlanAlertPrompt").css({
                display: "none"
            });/*显示提示DIV*/
        }
    }

$("#inputKwd").blur(function(){
    if($(this).val()==""){
        $(this).val("多个以逗号分隔，如:雅思,出国");
    }
});
/**
 *下一步按钮的单击事件
 */
$(".nextStep").click(function () {
    var selectValue = $("#selectPrice").val();
    var serchKeyword=$("#inputKwd").val();
    if (serchKeyword == "多个以逗号分隔，如:雅思,出国"||serchKeyword=="") {
//        alert("请输入您从事的业务!");
        quickCreatePlanAlertPrompt.show("请输入您从事的业务!");
        return;
    }

    if (selectValue == "please") {
        $("#mes").html("<span style='color:red;font-size:13px;'>请先选择</span>");
        return;
    }

    searchKeyword($("#inputKwd").val());

    $('.addplan_top ul li:eq(1)').addClass("current");

    $(".inputCreateInfo").addClass("hides");
    $(".chooseKwd").removeClass("hides");
});


/**
 *上一步按钮的单击事件
 */
$(".lastStep").click(function () {
    $('#tabUL li:eq(1)').removeClass("current");
    $('#tabUL li:eq(0)').addClass("current");
    $(".chooseKwd").addClass("hides");
    $(".inputCreateInfo").removeClass("hides");
});


//搜索按钮的单击事件
function clickSearch() {
    var seedWord = $("#searchKeyword").val();
    searchKeyword(seedWord);
}


var wordType = "bWord";
var wordName = "";
/**
 *查找关键词
 */
var searchKeyword = function (seedWord) {
    wordName = seedWord;
    if (seedWord == null || seedWord.trim().length == 0) {
        return;
    }
    $("#tbody1").html("查找中...");
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


//添加选中按钮的事件
$("#addChooseKeyword").click(function () {
    var checkBoxs=$("#tbody1 :checkbox");
    var kwds = "";
    for(var i=0;i<checkBoxs.length;i++){
        if(checkBoxs[i].checked==true){
            var value=$(checkBoxs[i]).parents("tr").find("td:eq(1)").html();
            kwds += value + "\n";
        }
    }
    setNewkeywordToTextArea(kwds);
});

//添加全部按钮的单击事件
$("#addAllKeyword").click(function () {
    var checkedTds1 = $("input[name=baiduKeyword]:checkbox");
    var kwds = "";
    for (var i = 0, l = checkedTds1.length; i < l; i++) {
        kwds += $("#" + wordType + i).find("td").eq(1).text() + "\r";
    }
    setNewkeywordToTextArea(kwds);
});
$("#addedkwd").keyup(function(){
    $("#keywordCount").html("已添加关键词（" + $("#addedkwd").val().trim().split("\n").length + "/500）");
});
function setNewkeywordToTextArea(kwds) {
    $("#addedkwd").val(kwds);
    $("#keywordCount").html("已添加关键词（" + $("#addedkwd").val().trim().split("\n").length + "/500）");
}


//完成按钮的事件
var saveChooseKeyword = function () {
    //获取所有选中的关键词
    var jsonArr = [];
    var values=$("#addedkwd").val();
    var kwds = $("#addedkwd").val().split("\n");
    if(values==""){
//        alert("您没有选择关键词!");
        quickCreatePlanAlertPrompt.show("您没有选择关键词!");
        return;
    }
    if (kwds.length > 500) {
//        alert("添加的关键词数量不能超过500个");
        quickCreatePlanAlertPrompt.show("添加的关键词数量不能超过500个");
        return;
    }
    for (var i = 0; i < kwds.length; i++) {
        var entity1 = {};
        entity1["accountId"] = "${sessionScope._accountId}";
        entity1["keyword"] = kwds[i];
        entity1["price"] = 1.0;
        entity1["matchType"] = 1;
        entity1["pause"] = false;
        entity1["status"] = -1;
        entity1["phraseType"] = 1;
        entity1["localStatus"] = 1;
        jsonArr.push(entity1);
    }

    if (jsonArr.length == 0) {
//        alert("您没有选择关键词!");
        quickCreatePlanAlertPrompt.show("您没有选择关键词!");
        return;
    }

    $.ajax({
        url: "/assistantCampaign/quickCreateCampaign?name=" + wordName + "&regions=" + getChooseRegions(),
        type: "POST",
        dataType: "json",
        data: JSON.stringify(jsonArr),
        async: false,
        contentType: "application/json; charset=UTF-8",
        success: function (data, textStatus, jqXHR) {
            if(data=="success"){
//                alert("添加成功!");
                quickCreatePlanAlertPrompt.show("添加成功!");
                top.dialog.getCurrent().close().remove();
            }
        }
    });
};
function closeDialog(){
    top.dialog.getCurrent().close().remove();
}

/**
 *修改推广地域的单击事件
 */
$("#updateRegion").click(function () {
    $("#updateRegionDialog").show(0);
});


/***************************************************修改推广地域*******************************************************************/
/**
 *使用账户推广地域的确定按钮的事件
 */
var regions = "";
$("#Oklabel").click(function () {
    regions = "";
    $("#updateRegionDialog").css("display", "none");
});

function getChooseRegions() {
    return regions;
}

function getRegionsStr() {
    var region = $("input[name=region]");
    var value = null;
    region.each(function () {
        if ($(this)[0].checked == true) {
            value = $(this).val();
        }
    });

    //0是全部地域,1是部分地域
    var regionstr = "";
    if (value == 0) {
        regionstr += "全部区域" + ",";
    } else if (value == 1) {
        var leaf = $("#regionList").find("div[class=leaf]");
        leaf.each(function () {
            if ($(this).find("input[type=checkbox]")[0].checked == true) {
                regionstr += $(this).find("label").html() + ",";
            }
        });
    }
    return regionstr;
}

/**
 *推广计划的推广地域的确定按钮的事件
 */
$("#ctrlbuttonregionOklabel").click(function () {
    regions = getRegionsStr();
    $("#updateRegionDialog").css("display", "none");
});


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
