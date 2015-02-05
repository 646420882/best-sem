<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/9/10
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/css/zTreeStyle/zTreeStyle.css">
    <style type="text/css">
        .newkeyword_right_mid textarea {
            height: 180px;
        }

        .ztree {
            height: 300px;

        }

        .list4 table {
            border: 1px solid #eaf0f3;
            overflow: auto;
            width: 100%;
        }

        .table2 tr td {
            text-align: center;
        }

        .assembly_under {
            height: 440px;
        }

        .list02_top {
            background: none repeat scroll 0 0 #edf0f1;
            color: #333;
            font-weight: bold;
            height: 30px;
            overflow: hidden;
        }

        .list2 table .list2_top td, th {
            color: #333;
        }


    </style>
</head>
<body>
<div style="background-color: #f3f5fd; width: 900px; height: 700px">
    <div class="addplan_top over">
        <ul id="step">
            <li class="current">1、输入内容</li>
            <li><span></span>2、验证数据</li>
        </ul>
    </div>
    <div class="plan_under">
        <div class="containers newkeyword_mid" id="creativeMulti">
            <div class="newkeyeord_top over">
                <h3>关键词目标</h3>

                <div class="newkeyeord_title over">
                    <ul class="over">
                        <li><input type="radio" checked="checked" name="Target" onclick="stepOne();">选择推广计划</li>
                        <%--<li><input type="radio" name="Target" onclick="stepTwo();">输入信息包含推广计划名称（第一项）、推广单元名称（第二项）</li>--%>
                    </ul>
                    <div class="newkeyword_content over">
                        <div class="containers2 over chooseKwdInfoDiv">
                            <div class="newkeyword_left fl  over">
                                <div class="newkeyword_top2">
                                    <ul id="creativeMultiTree" class="ztree">

                                    </ul>
                                </div>
                            </div>

                            <div class="newkeyword_right fr over">
                                <h3> 请输入推广单元 </h3>

                                <p>请输入推广单元信息（每行一个），并使用Tab键或逗号(英文)分隔各字段，也可以直接从Excel复制并粘贴，输入-，则将对应信息恢复为默认</p>

                                <div class="newkeyword_right_mid">
                                    <p>格式：推广单元名称(必填),启用/暂停,出价</p>

                                    <p>
                                        例如：单元,启用,1.0</p>
                                    <textarea onkeyup="getColumn(this)" id="creativeMultiTxt"></textarea>

                                    <p id="pError"><span><span id="checkedNodes">0</span>x<span
                                            id="column">0</span>=<span
                                            id="totalStr"></span>/<span id="maxLength">5000</span></span>
                                    </p>

                                    <%--<p>--%>
                                    <%--&lt;%&ndash;<span><input type="checkbox"/>用这些普通创意替换目标推广单元的所有相应内容<br/></span>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<span><input type="checkbox"&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;checked="checked"/>创意标题和描述相同时，更新该创意的url等其他设设置</span>&ndash;%&gt;--%>
                                    <%--</p>--%>
                                </div>

                                <div class="main_bottom" style="margin:0px; padding-left:30%; background:none;">
                                    <div class="w_list03">
                                        <ul>
                                            <li class="current" onclick="nextStep();">下一步</li>
                                            <li class="close" onclick="closeDialog();">取消</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <%--<div class="containers2 over inputKwdInfoDiv">--%>
                            <%--<div class="newkeyword_right fr over" style="width: 100%;">--%>
                                <%--<h3> 删除关键词 </h3>--%>

                                <%--<p>请输入关键词信息（每行一个），并用Tab键或逗号（英文）分隔各字段，也可直接从Excel复制并粘贴</p>--%>

                                <%--<div class="newkeyword_right_mid">--%>
                                    <%--<p>格式：推广计划名称（必填），推广单元名称（必填），关键词名称（必填）</p>--%>

                                    <%--<p>例如：北京推广，礼品，鲜花</p>--%>
                                    <%--<textarea id="deleteKwdText2"></textarea>--%>

                                    <%--<p>或者从相同格式的csv文件输入：<input type="button" class="zs_input2" value="选择文件">&nbsp;(<20万行)--%>
                                    <%--</p>--%>

                                <%--</div>--%>

                                <%--<div class="main_bottom" style="margin:0px; padding-left:30%; background:none;">--%>
                                    <%--<div class="w_list03">--%>
                                        <%--<ul>--%>
                                            <%--<li class="current delKwdByinputNext">下一步</li>--%>
                                            <%--<li class="close">取消</li>--%>
                                        <%--</ul>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                    </div>
                </div>
            </div>
        </div>
        <div class="containers over hides" id="creativeMultishowValidateDiv">
            <div class="assembly_under over">
                <div class="assembly_right3 over">
                    <div class="newkeword_end">
                        <ul id="creativeMultivalidateDelKwdUl">
                        </ul>
                        <div style="width:99%;height: 400px;background:#fff;overflow: auto; font-size:12px; border: 1px solid #dadadd;">
                            <p><span style="font-weight: bold; line-height:30px;">新增的单元：<span
                                    id="criSize">0</span></span></p>
                            <table border="0" cellspacing="0" width="100%" id="createTable"
                                   class="table2 table-bordered" data-resizable-columns-id="demo-table">
                                <thead>
                                <tr class="list02_top">
                                    <th>&nbsp;推广计划</th>
                                    <th>&nbsp;推广单元</th>
                                    <th>&nbsp;启用</th>
                                    <th>&nbsp;出价</th>
                                </tr>
                                </thead>
                                <tbody id="tbodyClick2">
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="main_bottom" style="margin:0px; padding-left:30%; background:none;">
                        <div class="w_list03">
                            <ul>
                                <li class="current delKwdLastStep" onclick="preStep();">上一步</li>
                                <li onclick="overStep()">完成</li>
                                <li class="close" onclick="preStep();">取消</li>
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
<script type="text/javascript">
    var settingCreativeMutli = {
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
    var columnSize = 0;
    $(function () {
        initMutliTree();

    });
    function initMutliTree() {
        $("#creativeMultiTree").html("正在加载数据...");
        $.ajax({
            url: "/assistantKeyword/campaignTree",
            type: "post",
            dataType: "json",
            success: function (data) {
                if (data.length == 0) {
                    $("#creativeMultiTree").html("暂无数据!");
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
                }
                $.fn.zTree.init($("#creativeMultiTree"), settingCreativeMutli, array);
            }
        });
    }
    function onCheck(e, treeId, treeNode) {
        count();
        var v = getSelectedNodeToString();
        if (v != "") {
            var campaign = v.split(",");
            $("#column").html(campaign.length);
            focuspError();
        } else {
            $("#column").html(0);
        }
    }
    function count() {
        function isForceHidden(node) {
            if (!node.parentTId) return false;
            var p = node.getParentNode();
            return !!p.isHidden ? true : isForceHidden(p);
        }

        var zTree = $.fn.zTree.getZTreeObj("creativeMultiTree"),
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

    /**
     * 获取选中树形结构的计划单元id
     * */
    function getSelectedNodeToString() {
        var treeObj = $.fn.zTree.getZTreeObj("creativeMultiTree");
        var selectNode = treeObj.getCheckedNodes(true);
        var v = "";
        var parentNode = "";
        for (var i = 0; i < selectNode.length; i++) {
            if (selectNode[i].isParent == true) {
                parentNode = selectNode[i].id;
            } else {
                v = v + selectNode[i].id + ",";
            }
        }
        v = v.substr(0, v.length - 1);
        return v;
    }
    /**
     * 获取选中树形结构的计划单元名字
     * */
    function getSelectedNodeNameToString() {
        var treeObj = $.fn.zTree.getZTreeObj("creativeMultiTree");
        var selectNode = treeObj.getCheckedNodes(true);
        var v = "";
        var parentNode = "";
        for (var i = 0; i < selectNode.length; i++) {
            if (selectNode[i].isParent == true) {
                parentNode = selectNode[i].name;
            } else {
                v = v + selectNode[i].name + ",";
            }
        }
        v = v.substr(0, v.length - 1);
        return v;
    }
    function getChar(str) {
        var char = str.match(/[^\x00-\xff]/ig);
        return str.length + (char == null ? 0 : char.length);
    }
    /**
     *选择单选的第一个
     */
    function stepOne() {
        $(".inputKwdInfoDiv").addClass("hides");
        $(".chooseKwdInfoDiv").removeClass("hides");
    }
    /**
     *选择单选的第二个
     */
    function stepTwo() {
        $(".inputKwdInfoDiv").removeClass("hides");
        $(".chooseKwdInfoDiv").addClass("hides");
    }
    /**
     下一步*
     */
    function nextStep() {
        var txt = $("#creativeMultiTxt").val().trim();
        if (getSelectedNodeToString() != "" && txt != "") {
            var vs = getSelectedNodeNameToString();
            var v = getSelectedNodeToString();
            var ids = v.split(",");
            var names = vs.split(",");
            var _createTable = $("#createTable tbody");
            var txtSize = txt.split("\n");
            for (var j = 0; j < txtSize.length; j++) {
                var c0 = txtSize[j].split(",")[0] != undefined ? txtSize[j].split(",")[0] : "";
                var c2 = txtSize[j].split(",")[2] != undefined ? txtSize[j].split(",")[2] : "";
                if (parseInt(getChar(c0)) > 30 || parseInt(getChar(c0)) == 0) {
                    alert("第" + (j + 1) + "行单元名长度不能超过30个字符，一个汉字占两个字符,且不为空");
                    return;
                }
                if (c2 != "") {
                    if (!/^-?\d+\.?\d*$/.test(c2)) {
                        alert("第" + (j + 1) + "行输入正确的单元出价！");
                        return;
                    }
                } else {
                    alert("第" + (j + 1) + "行单元出价不能为空!");
                    return;
                }
            }
            initNextStep();
            _createTable.empty();
            $("#criSize").html(txtSize.length);
            for (var i = 0; i < names.length; i++) {
                for (var j = 0; j < txtSize.length; j++) {
                    var c0 = txtSize[j].split(",")[0] != undefined ? txtSize[j].split(",")[0] : "";
                    var c1 = txtSize[j].split(",")[1] != undefined ? txtSize[j].split(",")[1] : "";
                    var c2 = txtSize[j].split(",")[2] != undefined ? txtSize[j].split(",")[2] : "0.1";
                    var c4_pause = "暂停";
                    if (c1 == "启用") {
                        c4_pause = "启用";
                    }
                    var _tbody = "<tr>" +
                            "<td>" + names[i] + "<input type='hidden' value=" + ids[i] + "></td>" +
                            "<td>" + c0 + "</td>" +
                            "<td>" + c4_pause + "</td>" +
                            "<td>" + c2 + "</td>" +
                            "</tr>";
                    _createTable.append(_tbody);
                }
            }
        } else {
            alert("请选择推广计划或者输入单元信息！");
        }
    }
    /**
     上一步*
     */
    function preStep() {
        initPreStep();
    }

    function creativeMultiNextStep() {
        var v = getSelectedNodeToString();
    }
    /**
     获取文本框中数据的行数*
     * @param rs
     */
    function getColumn(rs) {
        var _this = $(rs);
        if (_this.val() != "") {
            var column = _this.val().trim().split("\n");
            $("#checkedNodes").html(column.length);
            focuspError();
        } else {
            $("#checkedNodes").html(0);
        }
    }
    /**
     验证输入的字符串是否大于规定值*
     */
    function focuspError() {
        var plans = parseInt($("#column").html());
        var col = parseInt($("#checkedNodes").html());
        $("#totalStr").html(plans * col);
        if ((plans * col) > parseInt($("#maxLength").html())) {
            $("#pError").css("color", "red");
        } else {
            $("#pError").css("color", "black");
        }
    }
    /**
     初始化下一步效果*
     */
    function initNextStep() {
        $("#creativeMulti").addClass("hides");
        $("#creativeMultishowValidateDiv").removeClass("hides");
        $("#step").find("li").addClass("current");
    }
    /**
     初始化上一步效果*
     */
    function initPreStep() {
        $("#creativeMulti").removeClass("hides");
        $("#creativeMultishowValidateDiv").addClass("hides");
        $("#step").find("li:eq(1)").removeClass("current");
    }
    /**
     完成方法,循环添加批量的数据*
     */
    function overStep() {
        var con = confirm("你确定要添加或者替换这些单元吗？");
        if (con) {
            var _table = $("#createTable tbody");
            var trs = _table.find("tr");
            var data = {};
            $(trs).each(function (i, o) {
                var _tr = $(o);
                var cid = _tr.find("td:eq(0) input").val();
                var name = _tr.find("td:eq(1)").html();
                var pause = _tr.find("td:eq(2)").html();
                var maxPrice = _tr.find("td:eq(3)").html();
                var pause_ToF = pause != "启用" ? false : true;
                $.post("../assistantAdgroup/insertOrUpdate", {
                    cid: cid,
                    name: name,
                    pause: pause_ToF,
                    maxPrice: maxPrice,
                    status: -1
                }, function (rs) {
                    if (rs == "1") {
                        alert("操作成功");
                        top.dialog.getCurrent().close().remove();
                    }
                });
            });

        }
    }
    function closeDialog() {
        top.dialog.getCurrent().close().remove();
    }
</script>
</body>
</html>