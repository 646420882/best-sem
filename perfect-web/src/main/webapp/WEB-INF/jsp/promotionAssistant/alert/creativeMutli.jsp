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

        .list4 table {
            border: 1px solid #eaf0f3;
            overflow: auto;
            width: 100%;

        }
        .table2 tr td{
            text-align: center;
        }

        .assembly_under {
            height: 440px;

        }

        .newkeyword_right_mid textarea {
            height: 250px;
        }
        .ztree{
            height:450px;
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
<div style="background-color: #f3f5fd; width: 900px; height: 900px">
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
                        <li><input type="radio" checked="checked" name="Target" onclick="stepOne();">选择推广计划、推广单元</li>
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
                                <h3> 请输入普通创意 </h3>

                                <p>请输入普通创意信息（每行一个），并使用Tab键或逗号(英文)分隔各字段，也可以直接从Excel复制并粘贴，输入-，则将对应信息恢复为默认</p>

                                <div class="newkeyword_right_mid">
                                    <p>格式：创意标题(必填)，创意描述1，创意描述2，默认访问URL，默认显示URL，移动访问URL，移动显示URL，启用/暂停，设备偏好</p>

                                    <p>
                                        例如：标题,描述1,描述2,http://com.perfect.api.baidu.com,www.com.perfect.api.baidu.com,http://m.com.perfect.api.baidu.com,m.com.perfect.api.baidu.com,启用,全部设备</p>
                                    <textarea onkeyup="getColumn(this)" id="creativeMultiTxt"
                                              style="font-size: 12px;font-family: 微软雅黑;"></textarea>

                                    <p id="pError"><span><span id="checkedNodes">0</span>x<span
                                            id="column">0</span>=<span
                                            id="totalStr"></span>/<span id="maxLength">5000</span></span>
                                    </p>

                                    <p>
                                        <%--<span><input type="checkbox"/>用这些普通创意替换目标推广单元的所有相应内容<br/></span>--%>
                                            <span><input type="checkbox" id="isReplace"/>创意标题和描述相同时，更新该创意的url等其他设设置&nbsp;<span
                                                    style="color:red;">您的域名:&nbsp;</span><span
                                                    id="doMain"></span></span>
                                    </p>
                                </div>

                                <div class="main_bottom" style="margin:0px; padding-left:30%; background:none;">
                                    <div class="w_list03">
                                        <ul>
                                            <li class="current" onclick="nextStep();">下一步</li>
                                            <%--<li class="close">取消</li>--%>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="containers2 over inputKwdInfoDiv hides">

                            <div class="newkeyword_right fr over" style="width: 100%;">
                                <h3> 删除关键词 </h3>

                                <p>请输入关键词信息（每行一个），并用Tab键或逗号（英文）分隔各字段，也可直接从Excel复制并粘贴</p>

                                <div class="newkeyword_right_mid">
                                    <p>格式：推广计划名称（必填），推广单元名称（必填），关键词名称（必填）</p>

                                    <p>例如：北京推广，礼品，鲜花</p>
                                    <textarea id="deleteKwdText2"></textarea>

                                    <p>或者从相同格式的csv文件输入：<input type="button" class="zs_input2" value="选择文件">&nbsp;(<20万行)
                                    </p>

                                </div>


                                <div class="main_bottom" style="margin:0px; padding-left:30%; background:none;">
                                    <div class="w_list03">
                                        <ul>
                                            <li class="current delKwdByinputNext">下一步</li>
                                            <li class="close">取消</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>

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
                            <p><span style="font-weight: bold; line-height:30px;padding:10px;">新增的创意：<span id="criSize">0</span></span></p>
                            <table border="0" cellspacing="0" width="100%" id="createTable"
                                   class="table2 table-bordered" data-resizable-columns-id="demo-table">
                                <thead>
                                <tr class="list02_top">
                                    <th>&nbsp;推广计划</th>
                                    <th>&nbsp;推广单元</th>
                                    <th>&nbsp;创意标题</th>
                                    <th>&nbsp;创意描述1</th>
                                    <th>&nbsp;创意描述2</th>
                                    <th>&nbsp;默认访问URL</th>
                                    <th>&nbsp;默认显示URL</th>
                                    <th>&nbsp;移动访问URL</th>
                                    <th>&nbsp;移动显示URL</th>
                                    <th>&nbsp;启用/暂停</th>
                                    <th>&nbsp;设备偏好</th>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/untils/untils.js"></script>
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
    initDomain();
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
            $.fn.zTree.init($("#creativeMultiTree"), settingCreativeMutli, array);
        }
    });
}
function onCheck(e, treeId, treeNode) {
    count();
    var v = getSelectedNodeToString();
    var vs = getSelectedNodeNameToString();
    if (v != "") {
        var campaign = v.split("-");
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
            v = v + parentNode + "," + selectNode[i].id + "-";
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
            v = v + parentNode + "," + selectNode[i].name + "-";
        }
    }
    v = v.substr(0, v.length - 1);
    return v;
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
 *获取字节数
 * */
function getChar(str) {
    var char = str.match(/[^\x00-\xff]/ig);
    return str.length + (char == null ? 0 : char.length);
}
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
/**
 下一步*
 */
function nextStep() {
    var txt = $("#creativeMultiTxt").val().trim();
    if (getSelectedNodeToString() != "" && txt != "") {
        var dm = "."+$("#doMain").html();
        var vs = getSelectedNodeNameToString();
        var v = getSelectedNodeToString();
        var ids = v.split("-");
        var names = vs.split("-");
        var _createTable = $("#createTable tbody");
        var txtSize = txt.split("\n");
        for (var j = 0; j < txtSize.length; j++) {
            var c0 = txtSize[j].split(",")[0] != undefined ? txtSize[j].split(",")[0] : "";
            var c1 = txtSize[j].split(",")[1] != undefined ? txtSize[j].split(",")[1] : "";
            var c2 = txtSize[j].split(",")[2] != undefined ? txtSize[j].split(",")[2] : "";
            var c3 = txtSize[j].split(",")[3] != undefined ? txtSize[j].split(",")[3] : "";
            var c4 = txtSize[j].split(",")[4] != undefined ? txtSize[j].split(",")[4] : "";
            var c5 = txtSize[j].split(",")[5] != undefined ? txtSize[j].split(",")[5] : "";
            var c6 = txtSize[j].split(",")[6] != undefined ? txtSize[j].split(",")[6] : "";
            if (parseInt(getChar(c0)) > 50 || parseInt(getChar(c0)) <= 8) {
                alert("第" + (j + 1) + "行的\"标题\"长度应大于8个字符小于50个字符，汉子占两个字符!");
                return false;
            }
            if (parseInt(getChar(c1)) > 80 || parseInt(getChar(c1)) <= 8) {
                alert("第" + (j + 1) + "行的\"创意描述1\"长度应大于8个字符小于80个字符，汉子占两个字符!");
                return false;
            }
            if (parseInt(getChar(c2)) > 80 || parseInt(getChar(c2)) <= 8) {
                alert("第" + (j + 1) + "行的\"创意描述2\"长度应大于8个字符小于80个字符，汉子占两个字符!");
                return false;
            }
            if (parseInt(getChar(c3)) > 1024 || parseInt(getChar(c3)) <= 1) {
                alert("第" + (j + 1) + "行的默认\"访问\"Url地址长度应大于2个字符小于1024个字符，汉子占两个字符!");
                return false;
            } else {
                if (c3.indexOf(dm) == -1) {
                    alert("第" + (j + 1) + "行的默认\"访问\"Url地址必须包含以\"" + dm + "\"的域名！");
                    return false;
                }
                //下面注释是判断结尾是否以注册的域名结尾已经不需要，百度官网也没有做这样验证，只验证了是否包含主域名)
                else {
                    if (c3.substr(c3.indexOf(dm)) != dm) {
                        alert("第"+(j+1)+"行的默认\"访问\"Url地址必须以\"" + dm + "\"结尾！");
                        return false;
                    }
                }
            }
            if (parseInt(getChar(c4)) > 36 || parseInt(getChar(c4)) <= 1) {
                alert("第" + (j + 1) + "行的默认\"显示\"Url地址长度应大于2个字符小于36个字符，汉子占两个字符!");
                return false;
            } else {
                if (c4.indexOf(dm) == -1) {
                    alert("第" + (j + 1) + "行的默认\"显示\"Url地址必须包含以\"" + dm + "\"的域名！");
                    return false;
                }
                //下面注释是判断结尾是否以注册的域名结尾已经不需要，百度官网也没有做这样验证，只验证了是否包含主域名)
                else {
                    if (c4.substr(c4.indexOf(dm)) != dm) {
                        alert("第"+(j+1)+"行的默认\"显示\"Url地址必须以\"" + dm + "\"结尾！");
                        return false;
                    }
                }
            }
            if (parseInt(getChar(c5)) > 1017 || parseInt(getChar(c5)) <= 1) {
                alert("第" + (j + 1) + "行的移动\"访问\"Url地址长度应大于2个字符小于1017个字符");
                return false;
            } else {
                if (c5.indexOf(dm) == -1) {
                    alert("第" + (j + 1) + "行的移动\"访问\"Url地址必须包含以\"" + dm + "\"的域名！");
                    return false;
                }
                else {//下面注释是判断结尾是否以注册的域名结尾已经不需要，百度官网也没有做这样验证，只验证了是否包含主域名)
                    if (c5.substr(c5.indexOf(dm)) != dm) {
                        alert("第"+(j+1)+"行的移动\"访问\"Url地址必须以\"" + dm + "\"结尾！");
                        return false;
                    }
                }
            }


            if (parseInt(getChar(c6)) > 36 || parseInt(getChar(c6)) <= 1) {
                alert("第" + (j + 1) + "行的移动\"显示\"Url地址长度应大于2个字符小于36个字符");
                return false;
            } else {
                if (c6.indexOf(dm) == -1) {
                    alert("第" + (j + 1) + "行的移动\"显示\"Url地址必须包含以\"" + dm + "\"的域名！");
                    return false;
                }
                //下面注释是判断结尾是否以注册的域名结尾已经不需要，百度官网也没有做这样验证，只验证了是否包含主域名)
                else {
                    if (c6.substr(c6.indexOf(dm)) != dm) {
                        alert("第"+(j+1)+"行的移动\"显示\"Url地址必须以\"" + dm + "\"结尾！");
                        return false;
                    }
                }
            }
        }
        initNextStep();
        _createTable.empty();
        var _trClass="";
        $("#criSize").html(txtSize.length*names.length);
        for (var i = 0; i < names.length; i++) {
            _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
            for (var j = 0; j < txtSize.length; j++) {
                var c0 = txtSize[j].split(",")[0] != undefined ? txtSize[j].split(",")[0] : "";
                var c1 = txtSize[j].split(",")[1] != undefined ? txtSize[j].split(",")[1] : "";
                var c2 = txtSize[j].split(",")[2] != undefined ? txtSize[j].split(",")[2] : "";
                var c3 = txtSize[j].split(",")[3] != undefined ? txtSize[j].split(",")[3] : "";
                var c4 = txtSize[j].split(",")[4] != undefined ? txtSize[j].split(",")[4] : "";
                var c5 = txtSize[j].split(",")[5] != undefined ? txtSize[j].split(",")[5] : "";
                var c6 = txtSize[j].split(",")[6] != undefined ? txtSize[j].split(",")[6] : "";
                var c7 = txtSize[j].split(",")[7] != undefined ? txtSize[j].split(",")[7] : "";
                var c7_pause = c7 == "启用" ? "启用" : "暂停";
                var c8 = txtSize[j].split(",")[8] != undefined ? txtSize[j].split(",")[8] : "";
                var _tbody = "<tr class='"+_trClass+"'>" +
                        "<td>" + names[i].split(",")[0] + "<input type='hidden' value=" + ids[i].split(",")[0] + "></td>" +
                        "<td>" + names[i].split(",")[1] + "<input type='hidden' value=" + ids[i].split(",")[1] + "></td>" +
                        "<td>" + c0 + "</td>" +
                        "<td>" + c1 + "</td>" +
                        "<td>" + c2 + "</td>" +
                        "<td>" + c3 + "</td>" +
                        "<td>" + c4 + "</td>" +
                        "<td>" + c5 + "</td>" +
                        "<td>" + c6 + "</td>" +
                        "<td>" + c7_pause + "</td>" +
                        "<td>" + c8 + "</td>" +
                        "</tr>";
                _createTable.append(_tbody);

            }

        }

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
    var isReplace = $("#isReplace")[0].checked;
    var str="你确定要添加这些创意吗？"
    if(isReplace){
        str="你确定要添加并替换这些创意吗？"
    }
    var con = confirm(str);
    if (con) {
        var _table = $("#createTable tbody");
        var trs = _table.find("tr");
        var aid = "";
        var title = "";
        var desc1 = "";
        var desc2 = "";
        var pc = "";
        var pcs = "";
        var mib = "";
        var mibs = "";
        var pause = "";
        var device = "";
        $(trs).each(function (i, o) {
            var _tr = $(o);
//            var cid = _tr.find("td:eq(0) input").val();
            aid = aid + _tr.find("td:eq(1) input").val() + "\n";
            title = title + _tr.find("td:eq(2)").html() + "\n";
            desc1 = desc1 + _tr.find("td:eq(3)").html() + "\n";
            desc2 = desc2 + _tr.find("td:eq(4)").html() + "\n";
            pc = pc + _tr.find("td:eq(5)").html() + "\n";
            pcs = pcs + _tr.find("td:eq(6)").html() + "\n";
            mib = mib + _tr.find("td:eq(7)").html() + "\n";
            mibs = mibs + _tr.find("td:eq(8)").html() + "\n";
            var pa = _tr.find("td:eq(9)").html();
            var pause_ToF = pa != "启用" ? false : true;
            var de = _tr.find("td:eq(10)").html()
            pause = pause + pause_ToF + "\n";
            device = device + until.convertDevice(de) + "\n";
        });
        aid = aid.slice(0, -1);
        title = title.slice(0, -1);
        desc1 = desc1.slice(0, -1);
        desc2 = desc2.slice(0, -1);
        pc = pc.slice(0, -1);
        pcs = pcs.slice(0, -1);
        mib = mib.slice(0, -1);
        mibs = mibs.slice(0, -1);
        pause = pause.slice(0, -1);
        device = device.slice(0, -1);
        $.post("../assistantCreative/insertOrUpdate", {
            isReplace: isReplace,
            aid: aid,
            title: title,
            description1: desc1,
            description2: desc2,
            pcDestinationUrl: pc,
            pcDisplayUrl: pcs,
            mobileDestinationUrl: mib,
            mobileDisplayUrl: mibs,
            pause: pause,
            device: device
        }, function (rs) {
            if (rs == "1") {
                alert("操作成功!");
            }
            top.dialog.getCurrent().close().remove();
        });
    }
}
</script>
</body>
</html>