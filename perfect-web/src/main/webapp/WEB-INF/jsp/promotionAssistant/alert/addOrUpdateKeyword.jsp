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
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/css/zTreeStyle/zTreeStyle.css">
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
            height: 440px;

        }

        .ztree {
            height: 280px;

        }
    </style>
</head>
<body>
<div id="background" class="background hides"></div>
<div id="progressBar" class="progressBar hides">数据处理中，请稍等...</div>
<div style="background-color: #f3f5fd; width: 900px; height: 700px">
    <div class="addplan_top over">
        <ul id="tabUl">
            <li class="current">1、输入内容</li>
            <li><span></span>2、验证数据</li>
        </ul>
    </div>
    <div class="plan_under">
        <div class="containers newkeyword_mid" id="inputDwdInfo">
            <div class="newkeyeord_top over">
                <h3>关键词目标</h3>

                <div class="newkeyeord_title over">
                    <ul class="over">
                        <li><input type="radio" checked="checked" name="Target" class="current">选择推广计划、推广单元</li>
                        <%-- <li><input type="radio"  name="Target" class="current">输入信息包含推广计划名称（第一项）、推广单元名称（第二项）</li>--%>
                    </ul>
                    <div class="newkeyword_content over">
                        <div class="containers2 over">
                            <div class="newkeyword_left fl  over">
                                <%-- <div class="newkeyword_top1">
                                     <input type="text" placeholder="请输入关键词" class="zs_input3">
                                 </div>--%>
                                <div class="newkeyword_top2">
                                    <ul id="treeDemo" class="ztree"></ul>
                                </div>
                            </div>
                            <div class="newkeyword_right fr over">
                                <h3> 输入关键词 </h3>

                                <p>请输入关键词信息（每行一个），并用逗号（中文或者英文即可）分隔各字段，也可直接从Excel复制并粘贴</p>

                                <div class="newkeyword_right_mid">
                                    <p>格式：关键词名称（必填），匹配模式，出价（为0则使用推广单元出价），访问URL，移动访问URL，启用/暂停</p>

                                    <p>例如：鲜花，精确，1.0，www.com.perfect.api.baidu.com,www.com.perfect.api.baidu.com,启用</p>
                                    <textarea id="TextAreaChoose"></textarea>

                                    <p><input type="checkbox" id="isReplace">&nbsp;用这些关键词替换目标推广单元的所有对应内容</p>
                                    <%-- <p><input type="checkbox">&nbsp;用输入的关键词搜索更多相关关键词，把握题词质量</p>--%>
                                </div>
                            </div>

                        </div>
                        <div class="containers2 over hides">
                            <div class="newkeyword_right fr over" style="width:100%;">
                                <h3> 输入关键词 </h3>

                                <p>请输入关键词信息（每行一个），并用Tab键或逗号（英文）分隔各字段，也可直接从Excel复制并粘贴</p>

                                <div class="newkeyword_right_mid">
                                    <p>格式：关键词名称（必填），匹配模式，出价（为0则使用推广单元出价），访问URL，移动访问URL，启用/暂停</p>

                                    <p>例如：鲜花，精确，1.0，www.com.perfect.api.baidu.com,www.com.perfect.api.baidu.com,启用</p>
                                    <textarea></textarea>

                                    <p>或者从相同格式的csv文件输入：<input type="button" class="zs_input2" value="选择文件">&nbsp;(<20万行)
                                    </p>

                                    <p><input type="checkbox">&nbsp;用这些关键词替换目标推广单元的所有对应内容</p>

                                </div>
                            </div>
                        </div>

                    </div>
                </div>


            </div>
            <div class="main_bottom" style="margin:0px; padding-left:30%; background:none;">
                <div class="w_list03">
                    <ul>
                        <li class="current" id="downloadAccount">下一步</li>
                        <%--<li class="close">取消</li>--%>
                    </ul>
                </div>
            </div>
        </div>
        <div class="containers over hides" id="validateDiv">
            <div class="assembly_under over">
                <div class="assembly_right3 over">
                    <div class="newkeword_end">
                        <ul id="valideKwd">

                        </ul>
                    </div>
                    <div class="main_bottom" style="margin:0px; padding-left:30%; background:none;">
                        <div class="w_list03">
                            <ul>
                                <li class="current lastStep">上一步</li>
                                <li id="finish">完成</li>
                                <%--<li class="close">取消</li>--%>
                            </ul>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/untils/untils.js"></script>
<script type="text/javascript">
    $(function () {
        var $tab_li = $('.newkeyeord_title ul li input');
        $('.newkeyeord_title ul li input').click(function () {
            $(this).addClass('current').siblings().removeClass('current');
            var index = $tab_li.index(this);
            $('div.newkeyword_content > div').eq(index).show().siblings().hide();
        });
        $(".newkeyword_add").click(function () {
            if ($(".newkeyword_list").css("display") == "none") {
                $(".newkeyword_list").show();
            }
            else {
                $(".newkeyword_list").hide();
            }
        });


        $("body").delegate(".newkeyword_end1", "click", function () {
            var next = $(this).next();
            if (next.css("display") == "none") {
                next.show();
                $(this).find(" span").text("[-]");
            } else {
                next.hide();
                $(this).find("span").text("[+]");
            }
        });


        $("#checkAll2").click(function () {
            $('input[name="subbox2"]').prop("checked", this.checked);
        });
        var $subbox2 = $("input[name='subbox2']");
        $subbox2.click(function () {
            $("#checkAll2").prop("checked", $subbox2.length == $("input[name='subbox2']:checked").length ? true : false);
        });


    });
</script>
<script type="text/javascript">


    //得到树形列表数据
    function getCampaiTreeData() {
        $("#treeDemo").html("正在加载数据...");
        $.ajax({
            url: "/assistantKeyword/campaignTree",
            type: "post",
            dataType: "json",
            success: function (data) {
                if (data.length == 0) {
                    $("#treeDemo").html("暂无数据!");
                    return;
                }
                var array = new Array();
                var json;
                var camId;
                for (var i = 0; i < data.length; i++) {
                    json = {};

                    if (data[i].rootNode.campaignId == null) {
                        camId = data[i].rootNode.id;
                    } else {
                        camId = data[i].rootNode.campaignId;
                    }
                    json["id"] = camId;
                    json["pId"] = 0;
                    json["name"] = data[i].rootNode.campaignName;
                    json["titile"] = "";
                    json["open"] = false;
                    array.push(json);
                    for (var j = 0; j < data[i].childNode.length; j++) {
                        json = {};

                        if (data[i].childNode[j].adgroupId == null) {
                            json["id"] = data[i].childNode[j].id;
                        } else {
                            json["id"] = data[i].childNode[j].adgroupId;
                        }

                        json["pId"] = camId;
                        json["name"] = data[i].childNode[j].adgroupName;
                        json["titile"] = "";
                        json["checked"] = false;
                        json["isHidden"] = true;
                        array.push(json);
                    }
                }
                initTree(array);
            }
        });
    }


    //进入此页面加载树形列表数据
    getCampaiTreeData();

    var setting = {
        check: {
            enable: true
        },
        data: {
            key: {
                title: "title"
            },
            simpleData: {
                enable: true
            }
        },
        callback: {
            onCheck: onCheck
        }
    };


    function onCheck(e, treeId, treeNode) {
        count();
    }

    function setTitle(node) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = node ? [node] : zTree.transformToArray(zTree.getNodes());
        for (var i = 0, l = nodes.length; i < l; i++) {
            var n = nodes[i];
            n.title = "[" + n.id + "] isFirstNode = " + n.isFirstNode + ", isLastNode = " + n.isLastNode;
            zTree.updateNode(n);
        }
    }
    function count() {
        function isForceHidden(node) {
            if (!node.parentTId) return false;
            var p = node.getParentNode();
            return !!p.isHidden ? true : isForceHidden(p);
        }

        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                checkCount = zTree.getCheckedNodes(true).length,
                nocheckCount = zTree.getCheckedNodes(false).length,
                hiddenNodes = zTree.getNodesByParam("isHidden", true),
                hiddenCount = hiddenNodes.length;

        for (var i = 0, j = hiddenNodes.length; i < j; i++) {
            var n = hiddenNodes[i];
            if (isForceHidden(n)) {
                hiddenCount -= 1;
            } else if (n.isParent) {
                hiddenCount += zTree.transformToArray(n.children).length;
            }
        }

        $("#isHiddenCount").text(hiddenNodes.length);
        $("#hiddenCount").text(hiddenCount);
        $("#checkCount").text(checkCount);
        $("#nocheckCount").text(nocheckCount);
    }
    function showNodes() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                nodes = zTree.getNodesByParam("isHidden", true);
        zTree.showNodes(nodes);
        setTitle();
        count();
    }
    function hideNodes() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                nodes = zTree.getSelectedNodes();
        if (nodes.length == 0) {
            alert("请至少选择一个节点");
            return;
        }
        zTree.hideNodes(nodes);
        setTitle();
        count();
    }

    function initTree(zNodes) {
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        $("#hideNodesBtn").bind("click", {type: "rename"}, hideNodes);
        $("#showNodesBtn").bind("click", {type: "icon"}, showNodes);
        setTitle();
        count();
    }
    ;

</script>


<script type="text/javascript">

//单击下一步按钮的事件
$("#downloadAccount").click(function () {
    nextStepAjax();
});


/**
 *得到被选中的推广计划和推广单元id
 */
function getSelectedNodeToString() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var selectNode = treeObj.getCheckedNodes(true);
    var v = "";
    var parentNode = "";
    for (var i = 0; i < selectNode.length; i++) {
        if (selectNode[i].isParent == true) {
            parentNode = selectNode[i].id;
        } else {
            v = v + parentNode + "," + selectNode[i].id + "-";
        }
    }
    return v;
}


//下一步按钮的单击事件
var pdata = null;
function nextStepAjax() {

    if (getSelectedNodeToString() == "") {
        alert("请先选择推广计划和推广单元!");
        return;
    }

    if ($("#TextAreaChoose").val() == "") {
        alert("请输入要添加或者更新的数据");
        return;
    }

    var isReplace = $("#isReplace")[0].checked;
    var selectNode = getSelectedNodeToString();
    var keywordInfos = $("#TextAreaChoose").val();
    $.ajax({
        url: "/assistantKeyword/addOrUpdateKeywordByChoose",
        type: "post",
        data: {"isReplace": isReplace, "chooseInfos": selectNode, "keywordInfos": keywordInfos},
        dataType: "json",
        success: function (data) {
            pdata = data;
            $("#valideKwd").html("");
            if (data.insertList.length > 0) {
                toHtml("insert", data.insertList);
            }
            if (data.updateList.length > 0) {
                toHtml("update", data.updateList);
            }
            if (data.igoneList.length > 0) {
                toHtml("igone", data.igoneList);
            }
        }
    });
}


//将请求返回的数据加载到页面
function toHtml(listType, list) {
    var igoneField = new Array("推广计划名称", "推广单元名称", "关键词名称", "匹配模式");
    var otherField = new Array("推广计划名称", "推广单元名称", "关键词名称", "匹配模式", "出价", "访问url", "移动访问url", "启用/暂停");
    var html = "";

    if (listType == "insert" || listType == "update") {
        html = createHtml(otherField, list, listType);
    } else if (listType == "igone") {
        html = createIgoneHtml(igoneField, list);
    }
    $("#valideKwd").append(html);
    $("#inputDwdInfo").hide();
    $("#validateDiv").show();
    $("#tabUl li:eq(1)").addClass("current");
}


//创建新增，更新，删除,html代码
function createHtml(fieldsArray, list, listType) {
    var stringName = "";

    switch (listType) {
        case "insert":
            stringName = "新增的关键词" + list.length + "个";
            break;
        case "update":
            stringName = "更新的关键词" + list.length + "个";
            break;
    }


    var html = "<li>";
    html += " <div class='newkeyword_end1'> <span>[+]</span>" + stringName + "</div>";
    html += "<div class='newkeyword_end2 ' style='display:none;'>";

    switch (listType) {
        case "insert":
            html += " <p><input type='radio' id='addRadio' checked='checkde' name='addnew'>添加已选择的关键词</p>";
            html += " <p><input type='radio' name='addnew'>不添加</p>";
            break;
        case "update":
            html += " <p><input type='radio' id='updateRadio' checked='checkde' name='updatenew'>更新已选择的关键词</p>";
            html += " <p><input type='radio'  name='updatenew'>不更新</p>";
            break;
    }

    html += " <div class='list4' style='height:300px;'>";
    html += "  <table width='100%' cellspacing='0' border='0' width='500px'>";
    html += " <thead>";
    html += "<tr class='list02_top'>";
    var i = 0;
    html += "<td>" + fieldsArray[i++] + "</td>";
    html += "<td>" + fieldsArray[i++] + "</td>";
    html += "<td>" + fieldsArray[i++] + "</td>";
    html += "<td>" + fieldsArray[i++] + "</td>";
    html += "<td>" + fieldsArray[i++] + "</td>";
    html += "<td>" + fieldsArray[i++] + "</td>";
    html += "<td>" + fieldsArray[i++] + "</td>";
    html += "<td>" + fieldsArray[i++] + "</td>";
    html += "</tr></thead>";
    html += "<tbody>"

    for (var i = 0; i < list.length; i++) {
        html += "  <tr class='list2_box2'>";
        html += "<td>" + list[i].campaignName + "</td>";
        html += "<td>" + list[i].adgroupName + "</td>";
        html += "<td>" + list[i].object.keyword + "</td>";

        //匹配模式
        var matchType;
        switch (list[i].object.matchType) {
            case 1:
                matchType = "精确";
                break;
            case 2:
                matchType = "短语";
                if (list[i].object.phraseType == 1) {
                    matchType = matchType + "-同义包含";
                } else if (list[i].object.phraseType == 2) {
                    matchType = matchType + "-精确包含";
                } else if (list[i].object.phraseType == 3) {
                    matchType = matchType + "-核心包含";
                }
                ;
                break;
            case 3:
                matchType = "广泛";
                break;
            default :
                matchType = "&nbsp;";
        }

        html += "<td>" + matchType + "</td>";
        html += "<td>" + until.convert(list[i].object.price == null, "<0.10>:" + list[i].object.price) + "</td>";
        html += "<td>" + until.convert(list[i].object.pcDestinationUrl == null, "&nbsp;:" + list[i].object.pcDestinationUrl) + "</td>";
        html += "<td>" + until.convert(list[i].object.mobileDestinationUrl == null, "&nbsp;:" + list[i].object.mobileDestinationUrl) + "</td>";
        html += "<td>" + until.convert(list[i].object.pause == true, "暂停:启用") + "</td>";
        html += "  </tr>";
    }

    html += "  </tbody>";
    html += " </table>";
    html += " </div></div> </li>";

    return html;

}

//创建忽略关键词的html
function createIgoneHtml(fieldsArray, list) {
    var html = "<li>";
    html += " <div class='newkeyword_end1'> <span>[+]</span>忽略的关键词" + list.length + "个</div>";
    html += "<div class='newkeyword_end2 ' style='display:none;'>";

    html += " <div class='list4' style='height:300px;'>";
    html += "  <table width='100%' cellspacing='0' border='0' width='500px'>";
    html += " <thead>";
    html += "<tr class='list02_top'>";
    var i = 0;
    html += "<td>" + fieldsArray[i++] + "</td>";
    html += "<td>" + fieldsArray[i++] + "</td>";
    html += "<td>" + fieldsArray[i++] + "</td>";
    html += "<td>" + fieldsArray[i++] + "</td>";
    html += "</tr></thead>";
    html += "<tbody>"

    for (var i = 0; i < list.length; i++) {
        html += "  <tr class='list2_box2'>";
        html += "<td>" + list[i].campaignName + "</td>";
        html += "<td>" + list[i].adgroupName + "</td>";
        html += "<td>" + list[i].keywordName + "</td>";
        html += "<td>" + list[i].matchModel + "</td>";
        html += "  </tr>";
    }

    html += "  </tbody>";
    html += " </table>";
    html += " </div></div> </li>";

    return html;

}

/**
 *单击上一步的事件
 */
$(".lastStep").click(function () {
    $("#inputDwdInfo").show();
    $("#validateDiv").hide();
    $("#tabUl li:eq(1)").removeClass("current");
});


/**
 *完成按钮的事件
 */
$("#finish").click(function () {

    var isReplace = $("#isReplace")[0].checked;
    if (isReplace == true) {
        var isOk = window.confirm("该次操作会将已选定单元下的所有关键词替换，确定要继续?");
        if (!isOk) {
            return;
        }
    }

    var jsonData = {};

    if ($("#addRadio")[0] != undefined) {
        var isAdd = $("#addRadio")[0].checked;
        if (isAdd == true) {
            jsonData["insertList"] = JSON.stringify(pdata.insertList);
        }
    }

    if ($("#updateRadio")[0] != undefined) {
        var isUpdate = $("#updateRadio")[0].checked;
        if (isUpdate == true) {
            jsonData["updateList"] = JSON.stringify(pdata.updateList);
        }
    }

    jsonData["isReplace"] = isReplace;
    $.ajax({
        url: "/assistantKeyword/batchAddOrUpdate",
        type: "post",
        data: jsonData,
        dataType: "json",
        success: function (data) {
            window.location.reload(true);
        }
    });
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