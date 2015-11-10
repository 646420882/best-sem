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
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/css/zTreeStyle/zTreeStyle.css">
    <style type="text/css">
        h1, h2, h3 {
            margin: 0px;
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

        .newkeyword_right_mid textarea {
            height: 250px;
        }

        .ztree {
            height: 450px;

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
<div id="background" class="background hides"></div>
<div id="progressBar" class="progressBar hides">数据处理中，请稍等...</div>
<div style="background-color: #f8f8f8; width:900px;height:900px;">
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
                        <li><input type="radio" name="Target" class="current">输入信息包含推广计划名称（第一项）、推广单元名称（第二项）</li>
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

                                    <p>例如：鲜花,精确,1.0,http://pc.xxx,http://pcs.xxx,启用</p>

                                    <p style="color:red;">要设定高级匹配模式的语法如下：短语-同义,短语-核心,短语-精确</p>
                                    <textarea onkeyup="getColumn(this)" id="TextAreaChoose"
                                              style="font-size: 12px;font-family: 微软雅黑;"></textarea>

                                    <p id="pError"><span><span id="checkedNodes">0</span>x<span
                                            id="column">0</span>=<span
                                            id="totalStr"></span>/<span id="maxLength">10000</span></span>
                                    </p>

                                    <p><input type="checkbox" id="isReplace">&nbsp;用这些关键词替换目标推广单元的所有对应内容&nbsp;<span
                                            style="color:red;">您的域名:&nbsp;</span><span
                                            id="doMain"></span></p>
                                    <%-- <p><input type="checkbox">&nbsp;用输入的关键词搜索更多相关关键词，把握题词质量</p>--%>
                                </div>
                                <div class="main_bottom" style="margin:0px; background:none;">
                                    <div class="w_list03">
                                        <ul>
                                            <li class="current" id="downloadAccount">下一步</li>
                                            <li class="close" onclick="closeDialog()">取消</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="containers2 over hides">
                            <div class="newkeyword_right fr over" style="width:100%;">
                                <h3> 输入关键词 </h3>

                                <p>请输入关键词信息（每行一个），并用tab键或逗号（英文）分隔各字段，也可直接从Excel复制并粘贴</p>

                                <div class="newkeyword_right_mid">
                                    <p>格式：关键词名称（必填），匹配模式，出价（为0则使用推广单元出价），访问URL，移动访问URL，启用/暂停</p>

                                    <p>例如：鲜花，精确，1.0，www.com.perfect.api.baidu.com,www.com.perfect.api.baidu.com,启用</p>
                                    <textarea id="specialText" onkeyup="getColumn(this)"></textarea>


                                    <p id="sError">
                                        不超过：<span id="nowColumn">0</span>
                                        <
                                        <span id="sMaxColumns">5000</span>
                                    </p>

                                    <p>或者从相同格式的csv文件上传：<input type="file" name="fileName" id="suFile">&nbsp;(<20万行)
                                    </p>

                                    <p><input type="checkbox" id="csvReplace">&nbsp;用这些关键词替换目标推广单元的所有对应内容</p>

                                </div>
                                <div class="main_bottom" style="margin:0px;  background:none;">
                                    <div class="w_list03">
                                        <ul>
                                            <li class="current" onclick="specialUpload()">下一步</li>
                                            <li class="close" onclick="closeDialog()">取消</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>


            </div>

        </div>
        <div class="containers over hides" id="validateDiv">
            <div class="assembly_under over">
                <div class="assembly_right3 over">
                    <div class="newkeword_end">
                        <div style="width:99%;height: 400px;background:#fff;overflow: auto; font-size:12px; border: 1px solid #dadadd;">

                            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                                <div class="panel panel-default">
                                    <div class="panel-heading" role="tab" id="headingOne">
                                        <h4 class="panel-title">
                                            <a role="button" data-toggle="collapse" data-parent="#accordion"
                                               href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                                <span class="mycollapse">[ - ] </span><span
                                                    style="font-weight: bold; line-height:30px;padding:10px;">新增的关键字：<span
                                                    id="criSize">0</span></span>
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel"
                                         aria-labelledby="headingOne">
                                        <div class="panel-body">
                                            <p><input type="radio" name="insertRadio" value="1" checked="checked">添加这些关键词
                                            </p>

                                            <p><input type="radio" name="insertRadio" value="0">不添加这些关键词</p>
                                            <table border="0" cellspacing="0" width="100%" id="createTable"
                                                   class="table2 table-bordered" data-resizable-columns-id="demo-table">
                                                <thead>
                                                <tr class="list02_top">
                                                    <th>&nbsp;计划名称</th>
                                                    <th>&nbsp;单元名称</th>
                                                    <th>&nbsp;关键字名称</th>
                                                    <th>&nbsp;匹配模式</th>
                                                    <th>&nbsp;出价</th>
                                                    <th>&nbsp;访问url</th>
                                                    <th>&nbsp;移动访问url</th>
                                                    <th>&nbsp;启用/暂停</th>
                                                </tr>
                                                </thead>
                                                <tbody id="tbodyClick2">
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel panel-default">
                                    <div class="panel-heading" role="tab" id="headingTwo">
                                        <h4 class="panel-title">
                                            <a class="collapsed" role="button" data-toggle="collapse"
                                               data-parent="#accordion" href="#collapseTwo" aria-expanded="false"
                                               aria-controls="collapseTwo">
                                                <span class="mycollapse">[ + ] </span> <span
                                                    style="font-weight: bold; line-height:30px;padding:10px;">忽略的关键词(本地已存在的关键词)：<span
                                                    id="existCount">0</span></span>
                                                <span>错误输入的关键词：</span><span id="sepakError"></span>
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel"
                                         aria-labelledby="headingTwo">
                                        <div class="panel-body">
                                            <%--<p><input type="radio" name="hasRadio" checked="checked">更新这些关键词</p>--%>

                                            <%--<p><input type="radio" name="hasRadio">不更新这些关键词</p>--%>
                                            <table border="0" cellspacing="0" width="100%"
                                                   class="table2 table-bordered" data-resizable-columns-id="demo-table">
                                                <thead>
                                                <tr class="list02_top">
                                                    <th>&nbsp;计划名称</th>
                                                    <th>&nbsp;单元名称</th>
                                                    <th>&nbsp;关键字名称</th>
                                                    <th>&nbsp;匹配模式</th>
                                                    <th>&nbsp;出价</th>
                                                    <th>&nbsp;访问url</th>
                                                    <th>&nbsp;移动访问url</th>
                                                    <th>&nbsp;启用/暂停</th>
                                                </tr>
                                                </thead>
                                                <tbody id="existKeyword">
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div class="progress_box">
                <span>完成数据验证</span>

                <div class="progress" style="width:300px;">
                    <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0"
                         aria-valuemax="100" style="width: 60%;">
                        60%
                    </div>
                </div>
            </div>

            <div class="main_bottom" style="margin:0px;background:none;">
                <div class="w_list03">
                    <ul>
                        <li class="current lastStep" onclick="csvPrev()">上一步</li>
                        <li id="finish">完成</li>
                        <%--<li class="close">取消</li>--%>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/json2/20140204/json2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/untils/untils.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/ajaxFileUpload.js"></script>
<script type="text/javascript">
    var selected_index = 0;
    $(function () {
        var $tab_li = $('.newkeyeord_title ul li input');
        $('.newkeyeord_title ul li input').click(function () {
            $(this).addClass('current').siblings().removeClass('current');
            var index = $tab_li.index(this);
            selected_index = index;
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

        $('#accordion').on('show.bs.collapse', function () {
            $(this).find(".mycollapse").html("<span>[ + ]</span>")
        })
        $('#accordion').on('hide.bs.collapse', function () {
            $(this).find(".mycollapse").html("<span>[-]</span>")
        })

    });
</script>
<script type="text/javascript">


    initDomain();
    //进入此页面加载树形列表数据
    getCampaiTreeData();

    function initDomain() {
        var dm = $("#doMain").html();
        if (dm == "") {
            $.get("/assistantCreative/getDomain", function (result) {
                if (result != "0") {
                    $("#doMain").html(result);
                }
            });
        }
    }
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
        var v = getSelectedNodeToString();
        if (v != "") {
            var campaign = v.split("-");
            $("#column").html(campaign.length - 1);
            focuspError();
        } else {
            $("#column").html(0);
        }
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
    function getSelectedNodeNameToString() {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var selectNode = treeObj.getCheckedNodes(true);
        var v = "";
        var parentNode = "";
        for (var i = 0; i < selectNode.length; i++) {
            if (selectNode[i].isParent == true) {
                parentNode = selectNode[i].name;
            } else {
                v = v + parentNode + "," + selectNode[i].name + "-";
            }
        }
        v = v.substr(0, v.length - 1);
        return v;
    }
    function getChar(str) {
        var char = str.match(/[^\x00-\xff]/ig);
        return str.length + (char == null ? 0 : char.length);
    }

    //下一步按钮的单击事件
    var pdata = null;
    function nextStepAjax() {
        var txt = $("#TextAreaChoose").val().trim();
        if (getSelectedNodeToString() != "" && txt != "") {
            var dm = "." + $("#doMain").html();
            var v = getSelectedNodeToString();
            var vs = getSelectedNodeNameToString();
            var ids = v.split("-");
            var names = vs.split("-");
            var _createTable = $("#createTable tbody");
            var txtSize = txt.split("\n");
            for (var j = 0; j < txtSize.length; j++) {
                var kwd = txtSize[j].split(",")[0] != undefined ? txtSize[j].split(",")[0] : "";
                var pr = txtSize[j].split(",")[2] != undefined ? txtSize[j].split(",")[2] : "";
                var pc = txtSize[j].split(",")[3] != undefined ? txtSize[j].split(",")[3] : "";
                var mib = txtSize[j].split(",")[4] != undefined ? txtSize[j].split(",")[4] : "";
                if (parseInt(getChar(kwd)) > 30 || parseInt(getChar(kwd)) < 1) {
                    alert("第" + (j + 1) + "行的\"关键字\"长度最大为30个字符，30个英文字符,并且不能为空，汉字占两个字符！");
                    return;
                }
                // /^-?\d+\.?\d*$/
                if (!/^-?\d+\.?\d*$/.test(pr)) {
                    alert("第" + (j + 1) + "行的\"出价\"小数输入不正确!");
                    return;
                } else {
                    if (parseFloat(pr).toFixed(3) >= 999.9) {
                        alert("第" + (j + 1) + "行的\"出价\"大小为：(0,999.9]<=关键词出价&&<=所属计划预算");
                        return;
                    }
                }
                if (pc != "") {
                    if (pc != "空") {
                        if (parseInt(getChar(pc)) > 1024) {
                            alert("访问Url不能超过1024个字符");
                            return;
                        } else {
                            if ((pc.indexOf(dm) == -1)) {
                                alert("第" + (j + 1) + "行的\"访问\"Url地址必须包含以\"" + dm + "\"的域名！");
                                return;
                            }
                            else {
                                if (pc.substr(pc.indexOf(dm)) != dm) {
                                    alert("第" + (j + 1) + "行的\"访问\"Url地址必须以\"" + dm + "\"结尾！");
                                    return false;
                                }
                            }
                        }
                    }
                } else {
                    alert("第" + (j + 1) + "行的\"访问\"Url地址必须如果不输入请输入字符:\"空\"");
                    return;
                }
                if (mib != "") {
                    if (mib != "空") {
                        if (parseInt(getChar(mib)) > 1024) {
                            alert("访问Url不能超过1024个字符");
                            return;
                        } else {
                            if ((mib.indexOf(dm) == -1)) {
                                alert("第" + (j + 1) + "行的\"移动访问\"Url地址必须包含以\"" + dm + "\"的域名！");
                                return;
                            }
                            else {
                                if (mib.substr(mib.indexOf(dm)) != dm) {
                                    alert("第" + (j + 1) + "行的\"移动访问\"Url地址必须以\"" + dm + "\"结尾！");
                                    return false;
                                }
                            }
                        }
                    }
                } else {
                    alert("第" + (j + 1) + "行的\"移动访问\"Url地址如果不输入请输入字符:\"空\"");
                    return;
                }
            }
            $("#tabUl li:eq(1)").addClass("current");
            $("#inputDwdInfo").hide();
            $("#validateDiv").show();
            _createTable.empty();
            var _trClass = "";
            $("#criSize").html(txtSize.length * names.length);
            for (var i = 0; i < names.length; i++) {
                _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
                for (var j = 0; j < txtSize.length; j++) {
                    var c0 = txtSize[j].split(",")[0] != undefined ? txtSize[j].split(",")[0] : "";
                    var c1 = txtSize[j].split(",")[1] != undefined ? txtSize[j].split(",")[1] : "";
                    var c2 = txtSize[j].split(",")[2] != undefined ? txtSize[j].split(",")[2] : "";
                    var c3 = txtSize[j].split(",")[3] != undefined ? txtSize[j].split(",")[3] : "";
                    var c4 = txtSize[j].split(",")[4] != undefined ? txtSize[j].split(",")[4] : "";
                    var c5 = txtSize[j].split(",")[5] != undefined ? txtSize[j].split(",")[5] : "";
                    var c5_pause = c5 == "启用" ? "启用" : "暂停";
                    var _tbody = "<tr class='" + _trClass + "'>" +
                            "<td>" + names[i].split(",")[0] + "<input type='hidden' value=" + ids[i].split(",")[0] + "></td>" +
                            "<td>" + names[i].split(",")[1] + "<input type='hidden' value=" + ids[i].split(",")[1] + "></td>" +
                            "<td>" + c0 + "</td>" +
                            "<td>" + c1 + "</td>" +
                            "<td>" + c2 + "</td>" +
                            "<td>" + c3 + "</td>" +
                            "<td>" + c4 + "</td>" +
                            "<td>" + c5_pause + "</td>" +
                            "</tr>";
                    _createTable.append(_tbody);

                }
            }
        } else {
            alert("请选择某单元或者填写正确的关键词数据！");
        }
    }
    function overStep(isReplace) {
        var str = "你确定要添加这些关键字吗";
        if (isReplace) {
            str = "你确定要添加或者替换这些关键字吗？";
        } else {
            str = "你确定要添加关键字吗？";
        }
        var con = confirm(str);
        if (con) {
            var _table = $("#createTable tbody");
            var trs = _table.find("tr");
            var cids = "";
            var aids = "";
            var kwds = "";
            var mts = "";
            var pts = "";
            var prices = "";
            var pcs = "";
            var mibs = "";
            var pauses = "";
            $(trs).each(function (i, o) {
                var _tr = $(o);
                cids = cids + _tr.find("td:eq(0) input").val() + "\n";
                aids = aids + _tr.find("td:eq(1) input").val() + "\n";
                kwds = kwds + _tr.find("td:eq(2)").html() + "\n";
                var starMatchType = _tr.find("td:eq(3)").html();
                var phraseType = "1";
                var matchType = "";
                if (starMatchType.indexOf("短语-") > -1) {
                    matchType = 2;//如果输入短语-xx，必定匹配模式是2
                    switch (starMatchType.split("-")[1]) {
                        case "同义":
                            phraseType = 1;
                            break;
                        case "精确":
                            phraseType = 2;
                            break;
                        case "核心":
                            phraseType = 3;
                            break;
                        default :
                            phraseType = 1;
                            break;
                    }
                } else {
                    matchType = until.getMatchTypeNumByName(starMatchType);
                }
                mts = mts + matchType + "\n";
                pts = pts + phraseType + "\n";
                var money = _tr.find("td:eq(4)").html();
                if (/^-?\d+\.?\d*$/.test(money)) {
                    prices = prices + money + "\n";
                } else {
                    prices = prices + "0.0" + "\n";
                }
                pcs = pcs + _tr.find("td:eq(5)").html() + "\n";
                mibs = mibs + _tr.find("td:eq(6)").html() + "\n";
                var pause = _tr.find("td:eq(7)").html();
                var pause_ToF = pause != "启用" ? false : true;
                pauses = pauses + pause_ToF + "\n";
            });
            cids = cids.slice(0, -1);
            aids = aids.slice(0, -1);
            kwds = kwds.slice(0, -1);
            mts = mts.slice(0, -1);
            pts = pts.slice(0, -1);
            prices = prices.slice(0, -1);
            pcs = pcs.slice(0, -1);
            mibs = mibs.slice(0, -1);
            pauses = pauses.slice(0, -1);
            $.post("../assistantKeyword/batchAddOrUpdate",
                    {
                        index: selected_index,
                        isReplace: isReplace,
                        cids: cids,
                        aids: aids,
                        kwds: kwds,
                        mts: mts,
                        pts: pts,
                        prices: prices,
                        pcs: pcs,
                        mibs: mibs,
                        pauses: pauses
                    },
                    function (rs) {
                        if (rs == "1") {
                            alert("操作成功!");
                        } else {
                            alert("添加失败!");
                        }
                        top.dialog.getCurrent().close().remove();
                    });
        }
    }
    function closeDialog() {
        top.dialog.getCurrent().close().remove();
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
        var dis = $(this).attr("disable");
        if (dis == "disable") {
            return;
        }
        if (selected_index == 0) {
            var isReplace = $("#isReplace")[0].checked;
            overStep(isReplace);
        } else {
            var isReplace = $("#csvReplace")[0].checked;
            overStep(isReplace);
        }

//    if (isReplace == true) {
//        var isOk = window.confirm("该次操作会将已选定单元下的所有关键词替换，确定要继续?");
//        if (!isOk) {
//            return;
//        }
//    }
//
//    var jsonData = {};
//
//    if ($("#addRadio")[0] != undefined) {
//        var isAdd = $("#addRadio")[0].checked;
//        if (isAdd == true) {
//            jsonData["insertList"] = JSON.stringify(pdata.insertList);
//        }
//    }
//
//    if ($("#updateRadio")[0] != undefined) {
//        var isUpdate = $("#updateRadio")[0].checked;
//        if (isUpdate == true) {
//            jsonData["updateList"] = JSON.stringify(pdata.updateList);
//        }
//    }
//
//    jsonData["isReplace"] = isReplace;
//    $.ajax({
//        url: "/assistantKeyword/batchAddOrUpdate",
//        type: "post",
//        data: jsonData,
//        dataType: "json",
//        success: function (data) {
//            window.location.reload(true);
//        }
//    });
    });


    //loading
    //    var ajaxbg = $("#background,#progressBar");
    //    ajaxbg.hide();
    //    $(document).ajaxStart(function () {
    //        ajaxbg.show();
    //    });
    //    $(document).ajaxStop(function () {
    //        ajaxbg.fadeOut(1000);
    //    });

    /**
     获取文本框中数据的行数*
     * @param rs
     */
    function getColumn(rs) {
        var _this = $(rs);
        var id = _this.attr("id");
        var column_id = "checkedNodes";
        if (id == "specialText") {
            column_id = "nowColumn";
        } else {
            column_id = "checkedNodes";
        }
        if (_this.val() != "") {
            var column = _this.val().trim().split("\n");
            $("#" + column_id).html(column.length);
            if (id == "specialText") {
                specialError();
            } else {
                focuspError();
            }
        } else {
            $("#" + column_id).html(0);
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
    function specialError() {
        var plans = parseInt($("#nowColumn").html());
        if (plans > 10000) {
            $("#sError").css("color", "red");
        } else {
            $("#sError").css("color", "black");
        }
    }


    function specialUpload() {
        var suText = $("#specialText").val();
        if (suText) {
            getSpecialText(suText);
        } else {
            var suFile = $("#suFile");
            var fileName = suFile.val();
            if (fileName) {
                uploadFile();
            } else {
                alert("请输入要添加的关键词或者要上传的excel文件或者csv文件！");
            }
        }

    }
    //    test test 鲜花 精确 1.2 http://www.perfect-cn.cn http://www.perfect-cn.cn 启用
    //    test,test,鲜花,精确,1.2,http://www.perfect-cn.cn,http://www.perfect-cn.cn,启用
    function getSpecialText(text) {
        var textArea = text.split("\n");
        validateKeyword(textArea);
    }
    function validateKeyword(textArea) {
        var dm = "." + $("#doMain").html();
        for (var i = 0; i < textArea.length; i++) {
            var splitType = "\t";
            if (textArea[i].indexOf(",") > 0) {
                splitType = ",";
            }
            var t = textArea[i].split(splitType);
            var campaignName = t[0] ? t[0] : "";
            var adgroupName = t[1] ? t[1] : "";
            var kwd = t[2] ? t[2] : "";
            var matchType = t[3] ? t[3] : "";
            var price = t[4] ? t[4] : "";
            var pc = t[5] ? t[5] : "";
            var mib = t[6] ? t[6] : "";
            // vaildateKeyword info start
            if (!campaignName) {
                alert("第" + (i + 1) + "行请输入计划名称!");
                return;
            }
            if (!adgroupName) {
                alert("第" + (i + 1) + "行请输入单元名称!");
                return;
            }
            if (parseInt(getChar(kwd)) > 30 || parseInt(getChar(kwd)) < 1) {
                alert("第" + (i + 1) + "行的\"关键字\"长度最大为30个字符，30个英文字符,并且不能为空，汉字占两个字符！");
                return;
            }
            if (!matchType) {
                alert("请输入匹配模式！")
                return;
            }
            if (!/^-?\d+\.?\d*$/.test(price)) {
                alert("第" + (i + 1) + "行的\"出价\"小数输入不正确!");
                return;
            } else {
                if (parseFloat(price).toFixed(3) >= 999.9) {
                    alert("第" + (i + 1) + "行的\"出价\"大小为：(0,999.9]<=关键词出价&&<=所属计划预算");
                    return;
                }
            }

            if (pc != "") {
                if (pc != "空") {
                    if (parseInt(getChar(pc)) > 1024) {
                        alert("访问Url不能超过1024个字符");
                        return;
                    } else {
                        if ((pc.indexOf(dm) == -1)) {
                            alert("第" + (i + 1) + "行的\"访问\"Url地址必须包含以\"" + dm + "\"的域名！");
                            return;
                        }
                        else {
                            if (pc.substr(pc.indexOf(dm)) != dm) {
                                alert("第" + (i + 1) + "行的\"访问\"Url地址必须以\"" + dm + "\"结尾！");
                                return false;
                            }
                        }
                    }
                }
            } else {
                alert("第" + (i + 1) + "行的\"访问\"Url地址必须如果不输入请输入字符:\"空\"");
                return;
            }

            if (mib != "") {
                if (mib != "空") {
                    if (parseInt(getChar(mib)) > 1024) {
                        alert("访问Url不能超过1024个字符");
                        return;
                    } else {
                        if ((mib.indexOf(dm) == -1)) {
                            alert("第" + (i + 1) + "行的\"移动访问\"Url地址必须包含以\"" + dm + "\"的域名！");
                            return;
                        }
                        else {
                            if (mib.substr(mib.indexOf(dm)) != dm) {
                                alert("第" + (i + 1) + "行的\"移动访问\"Url地址必须以\"" + dm + "\"结尾！");
                                return false;
                            }
                        }
                    }
                }
            } else {
                alert("第" + (i + 1) + "行的\"移动访问\"Url地址如果不输入请输入字符:\"空\"");
                return;
            }
        }
        // vaildateKeyword info end

        //vaildateKeyword count start


        if ($("#sError").attr("style") == "color: red;") {
            alert("网页中最多只支持上传10000行关键词，如果需要更多上传，请选择csv导入!");
            return;
        }


//        鲜花6,精确,1,http://www.perfect-cn.cn,http://www.perfect-cn.cn,启用

        //添加到预览关键字添加页面
        $("#tabUl li:eq(1)").addClass("current");
        $("#inputDwdInfo").hide();
        $("#validateDiv").show();
        $("#criSize").html(textArea.length);
        var _createTable = $("#createTable tbody");
        _createTable.empty();
        var _trClass = "";
        for (var j = 0; j < textArea.length; j++) {
            _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
            var campaginName = textArea[j].split("\t")[0] ? textArea[j].split("\t")[0] : "";
            var adgroupName = textArea[j].split("\t")[1] ? textArea[j].split("\t")[1] : "";
            var keywordName = textArea[j].split("\t")[2] ? textArea[j].split("\t")[2] : "";
            var matchType = textArea[j].split("\t")[3] ? textArea[j].split("\t")[3] : "";
            var price = textArea[j].split("\t")[4] ? textArea[j].split("\t")[4] : "";
            var pcUrl = textArea[j].split("\t")[5] ? textArea[j].split("\t")[5] : "";
            var pcsUrl = textArea[j].split("\t")[6] ? textArea[j].split("\t")[6] : "";
            var c7 = textArea[j].split("\t")[7] ? textArea[j].split("\t")[7] : "";
            var pause = c7 == "启用" ? "启用" : "暂停";
            var _tbody = "<tr class='" + _trClass + "'>" +
                    "<td>" + campaginName + "<input type='hidden' value=" + campaginName + "></td>" +
                    "<td>" + campaginName + "<input type='hidden' value=" + adgroupName + "></td>" +
                    "<td>" + keywordName + "</td>" +
                    "<td>" + matchType + "</td>" +
                    "<td>" + price + "</td>" +
                    "<td>" + pcUrl + "</td>" +
                    "<td>" + pcsUrl + "</td>" +
                    "<td>" + pause + "</td>" +
                    "</tr>";
            _createTable.append(_tbody);
        }
        vaildateKeyword();
    }
    function vaildateKeyword() {
        var isReplace = $("#csvReplace")[0].checked;
        var _table = $("#createTable tbody");
        var trs = _table.find("tr");
        var cids = "";
        var aids = "";
        var kwds = "";
        var mts = "";
        var pts = "";
        var prices = "";
        var pcs = "";
        var mibs = "";
        var pauses = "";
        $(trs).each(function (i, o) {
            var _tr = $(o);
            cids = cids + _tr.find("td:eq(0) input").val() + "\n";
            aids = aids + _tr.find("td:eq(1) input").val() + "\n";
            kwds = kwds + _tr.find("td:eq(2)").html() + "\n";
            var starMatchType = _tr.find("td:eq(3)").html();
            var phraseType = "1";
            var matchType = "";
            if (starMatchType.indexOf("短语-") > -1) {
                matchType = 2;//如果输入短语-xx，必定匹配模式是2
                switch (starMatchType.split("-")[1]) {
                    case "同义":
                        phraseType = 1;
                        break;
                    case "精确":
                        phraseType = 2;
                        break;
                    case "核心":
                        phraseType = 3;
                        break;
                    default :
                        phraseType = 1;
                        break;
                }
            } else {
                matchType = until.getMatchTypeNumByName(starMatchType);
            }
            mts = mts + matchType + "\n";
            pts = pts + phraseType + "\n";
            var money = _tr.find("td:eq(4)").html();
            if (/^-?\d+\.?\d*$/.test(money)) {
                prices = prices + money + "\n";
            } else {
                prices = prices + "0.0" + "\n";
            }
            pcs = pcs + _tr.find("td:eq(5)").html() + "\n";
            mibs = mibs + _tr.find("td:eq(6)").html() + "\n";
            var pause = _tr.find("td:eq(7)").html();
            var pause_ToF = pause != "启用" ? false : true;
            pauses = pauses + pause_ToF + "\n";
        });
        cids = cids.slice(0, -1);
        aids = aids.slice(0, -1);
        kwds = kwds.slice(0, -1);
        mts = mts.slice(0, -1);
        pts = pts.slice(0, -1);
        prices = prices.slice(0, -1);
        pcs = pcs.slice(0, -1);
        mibs = mibs.slice(0, -1);
        pauses = pauses.slice(0, -1);
        var data = {
            index: selected_index,
            isReplace: isReplace,
            cids: cids,
            aids: aids,
            kwds: kwds,
            mts: mts,
            pts: pts,
            prices: prices,
            pcs: pcs,
            mibs: mibs,
            pauses: pauses
        }

        $.post("../assistantKeyword/vaildateKeyword", data, function (res) {
            var errorbody = $("#existKeyword");
            var safeTbody = $("#createTable tbody");
            safeTbody.empty()
            var result = $.parseJSON(res);
            if (result.endGetCount) {
                $("#sepakError").html(result.endGetCount);
            }
            if (result.safeKeywordList.length) {
                var json = result.safeKeywordList;
                $("#criSize").html(result.safeKeywordList.length);
                renderSelfTableData(json, safeTbody);
            }

            if (result.dbExistKeywordList) {
                if (result.dbExistKeywordList.length) {
                    var json = result.dbExistKeywordList;
                    $("#existCount").html(result.dbExistKeywordList.length);
                    renderErrorTableData(json, errorbody);
                }
            }
        });

    }
    function renderSelfTableData(json, safeTbody) {
        var _trClass = "";
        for (var i = 0; i < json.length; i++) {
            _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
            var phraseType = json[i].object.phraseType;
            var _tbody = "<tr class='" + _trClass + "'>" +
                    "<td>" + json[i].campaignName + "<input type='hidden' value='" + json[i].campaignName + "'/></td>" +
                    "<td>" + json[i].adgroupName + "<input type='hidden' value='" + json[i].adgroupName + "'></td>" +
                    "<td>" + json[i].object.keyword + "</td>" +
                    "<td>" + until.getMatchTypeName(json[i].object.matchType + "", Number(phraseType)).replace("匹配", "") + "</td>" +
                    "<td>" + json[i].object.price + "</td>" +
                    "<td>" + json[i].object.pcDestinationUrl + "</td>" +
                    "<td>" + json[i].object.mobileDestinationUrl + "</td>" +
                    "<td>" + until.convert(json[i].object.pause, "启用:暂停") + "</td>" +
                    "</tr>";
            safeTbody.append(_tbody);
        }
    }
    function renderErrorTableData(json, errorbody) {
        var _trClass = "";
        for (var i = 0; i < json.length; i++) {
            _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
            var phraseType = json[i].object.phraseType;
            var _tbody = "<tr class='" + _trClass + "'>" +
                    "<td>" + json[i].campaignName + "</td>" +
                    "<td>" + json[i].adgroupName + "</td>" +
                    "<td>" + json[i].object.keyword + "</td>" +
                    "<td>" + until.getMatchTypeName(json[i].object.matchType + "", Number(phraseType)) + "</td>" +
                    "<td>" + json[i].object.price + "</td>" +
                    "<td>" + json[i].object.pcDestinationUrl + "</td>" +
                    "<td>" + json[i].object.mobileDestinationUrl + "</td>" +
                    "<td>" + until.convert(json[i].object.pause, "启用:暂停") + "</td>" +
                    "</tr>";
            errorbody.append(_tbody);
        }
    }
    function csvPrev() {
        $("#existCount").html(0);
        $("#existKeyword").empty();
        $("#sepakError").html(0);
    }

    function uploadFile() {
        console.log("123123")

        var fileObj = document.getElementById("suFile").files[0]; // 获取文件对象
        var FileController = "/assistantKeyword/importByFile";                    // 接收上传文件的后台地址
        console.log(document.getElementById("suFile").value);
        // FormData 对象
        var form = new FormData();
        form.append("author", "hooyes");                        // 可以增加表单数据
        form.append("file", fileObj);

        // XMLHttpRequest 对象
        var xhr = new XMLHttpRequest();
        xhr.open("post", FileController, true);
        xhr.onload = function () {
            var result = $.parseJSON(xhr.responseText);
            if (result.msg != "Ok") {
                alert(result.msg);
            } else {
                var errorbody = $("#existKeyword");
                var safeTbody = $("#createTable tbody");
                if (result.vk.safeKeywordList.length) {
                    var json = result.vk.safeKeywordList;
                    $("#criSize").html(result.vk.safeKeywordList.length);
                    renderSelfTableData(json, safeTbody);
                }

                if (result.vk.dbExistKeywordList) {
                    if (result.vk.dbExistKeywordList.length) {
                        var json = result.vk.dbExistKeywordList;
                        $("#existCount").html(result.vk.dbExistKeywordList.length);
                        renderErrorTableData(json, errorbody);
                    }
                }

                $("#inputDwdInfo").hide();
                $("#validateDiv").show();
            }
        };
        xhr.send(form);

//        $.ajaxFileUpload({
//            url: '/assistantKeyword/asdf',
//            dataType: 'json',
//            type:'post',
//            fileElementId: "suFile",
//            dataType:'json',
//            success: function (data, status) {
//                console.log(data);
//            },
//            error: function (data, status) {
//                console.log(data);
//            }
//        });
    }


    $(function () {
        $("input[name='insertRadio']").change(function () {
            if ($(this).val() == 0) {
                $("#finish").attr("disable", "disable");
            } else {
                $("#finish").attr("disable", "");
            }
        });
    })
</script>

</body>
</html>