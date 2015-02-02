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
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/css/accountCss/assistantStyle.css">
    <style type="text/css">

        .list4 table {
            border: 1px solid #eaf0f3;
            overflow: auto;
            width: 100%;
        }

        .assembly_under {
            height: 440px;

        }

        .newkeyword_right_mid textarea {
            height: 200px;
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
        <div class="containers newkeyword_mid" id="delKwdByChoose">
            <div class="newkeyeord_top over">
                <h3>关键词目标</h3>

                <div class="newkeyeord_title over">
                    <ul class="over">
                        <li><input type="radio" checked="checked" name="Target" class="chooseRadio">选择推广计划、推广单元</li>
                        <%--<li><input type="radio" name="Target" class="inputRadio">输入信息包含推广计划名称（第一项）、推广单元名称（第二项）</li>--%>
                    </ul>
                    <div class="newkeyword_content over">
                        <div class="containers2 over chooseKwdInfoDiv">
                            <div class="newkeyword_left fl  over">
                                <%-- <div class="newkeyword_top1">
                                     <input type="text" placeholder="请输入关键词" class="zs_input3">
                                 </div>--%>
                                <div class="newkeyword_top2">
                                    <ul id="treeDemo" class="ztree" style="height: 330px;"></ul>
                                </div>
                            </div>

                            <div class="newkeyword_right fr over">
                                <h3> 删除关键词 </h3>

                                <p>请输入关键词信息（每行一个），也可直接从Excel复制并粘贴</p>

                                <div class="newkeyword_right_mid">
                                    <p>格式：关键词名称（必填）</p>

                                    <p>例如：鲜花</p>
                                    <textarea id="deleteKwdTextChoose"></textarea>
                                </div>

                                <div class="main_bottom" style="margin:0px; padding-left:30%; background:none;">
                                    <div class="w_list03">
                                        <ul>
                                            <li class="current" id="kwdNextStep">下一步</li>
                                            <li class="close" onclick="closeDialog();">取消</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="containers2 over inputKwdInfoDiv hides">
                            <div class="newkeyword_right fr over" style="width: 100%;">
                                <h3> 删除关键词 </h3>

                                <p>请输入关键词信息（每行一个），并用逗号（中文或者英文即可）分隔各字段，也可直接从Excel复制并粘贴</p>

                                <div class="newkeyword_right_mid">
                                    <p>格式：推广计划名称（必填），推广单元名称（必填），关键词名称（必填）</p>

                                    <p>例如：北京推广，礼品，鲜花</p>
                                    <textarea id="deleteKwdText2"></textarea>
                                    <%--  <p>或者从相同格式的csv文件输入：<input type="button" class="zs_input2" value="选择文件">&nbsp;(<20万行)</p>--%>

                                </div>

                                <div class="main_bottom" style="margin:0px; padding-left:30%; background:none;">
                                    <div class="w_list03">
                                        <ul>
                                            <li class="current delKwdByinputNext">下一步</li>
                                            <li class="close" onclick="closeDialog();">取消</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <div class="containers over hides" id="showValidateDiv">
            <div class="assembly_under over">
                <div class="assembly_right3 over">
                    <div class="newkeword_end">
                        <ul id="validateDelKwdUl">
                        </ul>
                    </div>
                    <div class="main_bottom" style="margin:0px; padding-left:30%; background:none;">
                        <div class="w_list03">
                            <ul>
                                <li class="current delKwdLastStep">上一步</li>
                                <li class="delkwdFinish">完成</li>
                                <li class="close" onclick="closeDialog();">取消</li>
                            </ul>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/json2/20140204/json2.min.js"></script>
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


    $(".chooseRadio").click(function () {
        $(".chooseKwdInfoDiv").addClass("hides");
        $(".inputKwdInfoDiv").removeClass("hides");
    });

    $("inputRadio").click(function () {
        $(".inputKwdInfoDiv").addClass("hides");
        $(".chooseKwdInfoDiv").removeClass("hides");
    });


</script>

<script type="text/javascript">
    //得到树形列表数据
    function getCampaiTreeData() {
        $("#treeDemo").html("正在加载数据...");
        $.ajax({
            url: "/account/get_tree",
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.length == 0) {
                    $("#treeDemo").html("暂无数据!");
                    return;
                }
//                var array = new Array();
//                var json;
//                var camId;
//                for (var i = 0; i < data.length; i++) {
//                    json = {};
//
//                    if (data[i].rootNode.campaignId == null) {
//                        camId = data[i].rootNode.id;
//                    } else {
//                        camId = data[i].rootNode.campaignId;
//                    }
//                    json["id"] = camId;
//                    json["pId"] = 0;
//                    json["name"] = data[i].rootNode.campaignName;
//                    json["titile"] = "";
//                    json["open"] = false;
//                    array.push(json);
//                    for (var j = 0; j < data[i].childNode.length; j++) {
//                        json = {};
//
//                        if (data[i].childNode[j].adgroupId == null) {
//                            json["id"] = data[i].childNode[j].id;
//                        } else {
//                            json["id"] = data[i].childNode[j].adgroupId;
//                        }
//
//                        json["pId"] = camId;
//                        json["name"] = data[i].childNode[j].adgroupName;
//                        json["titile"] = "";
//                        json["checked"] = false;
//                        json["isHidden"] = true;
//                        array.push(json);
//                    }
//                }
                initTree(data.trees);
            }
        });
    }

    //进入此页面加载属性列表数据
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
$("#kwdNextStep").click(function () {
    nextStepAjax(1, 20);
});

//上一步按钮的事件
$(".delKwdLastStep").click(function () {
    $("#tabUl li:eq(1)").removeClass("current");
    $("#showValidateDiv").addClass("hides");
    $("#delKwdByChoose").removeClass("hides");
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


//下一步按钮的单击事件的ajax请求
function nextStepAjax(nowPage, pageSize) {

    var selectNote = getSelectedNodeToString();
    var inputKwdInfo = $("#deleteKwdTextChoose").val();

    if (selectNote == "") {
        alert("请先选择推广计划和推广单元!");
        return;
    }
    if ($("#deleteKwdTextChoose").val() == "") {
        alert("请输入关键词的删除信息");
        return;
    }

    $.ajax({
        url: "/assistantKeyword/deleteByNameChoose",
        type: "post",
        data: {"chooseInfos": selectNote, "keywordNames": inputKwdInfo, "nowPage": nowPage, "pageSize": pageSize},
        dataType: "json",
        success: function (data) {
            $("#validateDelKwdUl").empty();
            var html = "";
            if (data.deleteKwd.length > 0) {
                html = createDeleteKwdHtml("del", data.deleteKwd);
                $("#validateDelKwdUl").append(html);
            }
            if (data.ignoreList.length > 0) {
                html = createDeleteKwdHtml("igone", data.ignoreList);
                $("#validateDelKwdUl").append(html);
            }

            $("#tabUl li:eq(1)").addClass("current");
            $("#delKwdByChoose").addClass("hides");
            $("#showValidateDiv").removeClass("hides");
        }
    });
}

//创建验证关键词的htmnl
var ids = "";
function createDeleteKwdHtml(listType, list) {
    var html = "";
    html += "<li>";

    if (listType == "del") {
        html += "<div class='newkeyword_end1'> <span>[-]</span>删除的关键词:" + list.length + "个</div>";
        html += "<div class='newkeyword_end2 ' style = 'display:block;'>";
        html += "<p><input type='radio' class='delkwdRadio' checked='checked' name='addnew'>删除这些关键词</p>";
        html += "<p><input type='radio'  name='addnew'>不删除这些关键词</p>";
        html += "<div class='list4' style='height:300px;'>";
        html += "<table width='100%' cellspacing='0' border='0' width='500px'>";
        html += "<thead>";
        html += " <tr class='list02_top'><td>&nbsp;推广计划名称</td><td>&nbsp;推广单元名称</td> <td>&nbsp;关键词名称</td><td>&nbsp;匹配模式 </td><td>&nbsp;出价</td><td>&nbsp;访问URL</td>" +
                "<td>移动访问URL</td><td>启用/暂停</td></tr>";
        html += "</thead>";
        html += " <tbody>";

        for (var i = 0; i < list.length; i++) {
            if (list[i].object.keywordId == null) {
                ids += list[i].object.id + ",";
            } else {
                ids += list[i].object.keywordId + ",";
            }
            html += "<tr class='list2_box2'>" +
                    "<td> &nbsp;" + list[i].campaignName + "</td>" +
                    "<td>&nbsp;" + list[i].adgroupName + "</td>" +
                    "<td>&nbsp;" + list[i].object.keyword + "</td>" +
                    "<td>&nbsp;" + until.convert(list[i].object.matchType == 1, "精确:广泛") + "</td>" +
                    "<td>&nbsp;" + until.convert(list[i].object.price == null, "<0.10>:" + list[i].object.price) + "</td>" +
                    "<td>&nbsp;" + until.convert(list[i].object.pcDestinationUrl == null, "&nbsp;:" + list[i].object.pcDestinationUrl) + "</td>" +
                    "<td>&nbsp;" + until.convert(list[i].object.mobileDestinationUrl == null, "&nbsp;:" + list[i].object.mobileDestinationUrl) + "</td>" +
                    "<td>&nbsp;" + until.convert(list[i].object.pause == true, "启用:暂停") + "</td>" +
                    "</tr>";
        }
        html += " </tbody></table> </div>";
        html += "</div>"
    } else if (listType == "igone") {
        html += "<div class='newkeyword_end1'> <span>[-]</span>忽略的关键词:" + list.length + "个,所选的范围不包含关键词</div>";
        html += "<div class='newkeyword_end2 ' style = 'display:block;'>";
        html += "<div class='list4' style='height:300px;'>";
        html += "<table width='100%' cellspacing='0' border='0' width='500px'>";
        html += "<thead>";
        html += "<tr class='list02_top'><td>&nbsp;推广计划名称</td><td>&nbsp;推广单元名称</td> <td>&nbsp;关键词名称</td><td>&nbsp;匹配模式 </td></tr>";
        html += "</thead>";
        html += "<tbody>";

        for (var i = 0; i < list.length; i++) {
            html += "<tr class='list2_box2'>" +
                    "<td>&nbsp;" + list[i].campaignName + "</td>" +
                    "<td>&nbsp;" + list[i].adgroupName + "</td>" +
                    "<td>&nbsp;" + list[i].keywordName + "</td>" +
                    "<td>&nbsp;" + list[i].matchModel + "</td>" +
                    "</tr>";
        }
        html += " </tbody></table> </div>";
        html += "</div>"
    }
    html += "</li>";

    return html;
}


//输入推广计划，推广单元，关键词的方式
$(".delKwdByinputNext").click(function () {
    var deleteInfos = $("#deleteKwdText2").val();
    if (deleteInfos == "") {
        alert("请输入要删除的关键词信息");
        return;
    }

    var regExp = new RegExp("，", "g");//第二个参数,g指替换所有的，其中，第二参数也可以设置为("i"),表示只替换第一个字符串。

    var rows = deleteInfos.split("\n");
    var errorFormart = "";
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i].replace(regExp, ",");
        if ((row.split(",").length) != 3) {
            errorFormart += row + "\n";
        }
    }

    if (errorFormart != "") {
        alert("以下输入格式错误!\n" + errorFormart);
        return;
    }


    $.ajax({
        url: "/assistantKeyword/validateDeleteByInput",
        type: "post",
        data: {"deleteInfos": deleteInfos},
        dataType: "json",
        success: function (data) {
            var html = "";
            if (data.ignoreList.length > 0) {
                html = createDeleteKwdHtml("igone", data.ignoreList);
            }
            if (data.deleteKwd.length > 0) {
                html = createDeleteKwdHtml("del", data.deleteKwd);
            }
            $("#validateDelKwdUl").html(html);
            $("#tabUl li:eq(1)").addClass("current");
            $("#delKwdByChoose").addClass("hides");
            $("#showValidateDiv").removeClass("hides");
        }
    });
});


//完成按钮的事件
$(".delkwdFinish").click(function () {
    var isDel;
    if ($(".delkwdRadio")[0] != undefined) {
        isDel = $(".delkwdRadio")[0].checked;
    }
    if (isDel == true) {
        $.ajax({
            url: "/assistantKeyword/deleteById",
            type: "post",
            data: {"kwids": ids},
            success: function (data) {
                window.location.reload(true);
            }
        });
    } else {
        window.location.reload(true);
    }

});

function closeDialog(){
    top.dialog.getCurrent().close().remove();
}


//取消按钮的事件
$(".close").click(function () {

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
