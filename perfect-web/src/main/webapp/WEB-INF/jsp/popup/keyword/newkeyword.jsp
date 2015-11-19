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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/zTreeStyle/zTreeStyle.css">
    <style type="text/css">

        .list4 table {
            border: 1px solid #eaf0f3;
            overflow: auto;
            width: 100%;
        }
        .zs_function{
            margin-top:10px;
        }
        .keyworld_text2 {
            height: 320px;}
        .assembly_under{
            height:440px;

        }
        .ztree {
            height:280px;


        }
    </style>
</head>
<body>
<div style="background-color: #f3f5fd; width: 900px; height: 700px">
    <div class="addplan_top over">
        <ul>
            <li class="current">1、输入内容</li>
            <li><span></span>2、验证数据</li>
        </ul>
    </div>
    <div class="plan_under">
        <div class="containers newkeyword_mid">
            <div class="newkeyeord_top over">
                    <h3>关键词目标</h3>
                <div class="newkeyeord_title over">
                    <ul class="over" >
                        <li><input type="radio" checked="checked" name="Target" class="current">选择推广计划、推广单元</li>
                        <li><input type="radio"  name="Target" class="current">输入信息包含推广计划名称（第一项）、推广单元名称（第二项）</li>
                    </ul>
                    <div class="newkeyword_content over">
                        <div class="containers2 over">
                           <div class="newkeyword_left fl  over"  >
                                <div class="newkeyword_top1">
                                    <input type="text" placeholder="请输入关键词" class="zs_input3">
                                </div>
                                <div class="newkeyword_top2">
                                    <ul id="treeDemo" class="ztree"></ul>
                                    <div class="newkeyword_top3">
                                        <a href="#">空</a><a href="#">有效</a><a href="#">已暂停</a><a href="#" class="current">全部</a>
                                    </div>
                                </div>
                           </div>
                            <div class="newkeyword_right fr over">
                                <h3> 输入关键词 </h3>
                               <p>请输入关键词信息（每行一个），并用Tab键或逗号（英文）分隔各字段，也可直接从Excel复制并粘贴，输入-， 则将对应信息恢复为默认</p>
                                <div class="newkeyword_right_mid">
                                    <p>格式：关键词名称（必填），匹配模式，出价（为0则使用推广单元出价），访问URL，移动访问URL，启用/暂停</p>
                                    <p>例如：鲜花，精确，1.0，www.com.perfect.api.baidu.com,www.com.perfect.api.baidu.com,启用</p>
                                    <textarea>

                                    </textarea>
                                    <p><input type="checkbox">&nbsp;用这些关键词替换目标推广单元的所有对应内容</p>
                                    <p><input type="checkbox">&nbsp;用输入的关键词搜索更多相关关键词，把握题词质量</p>

                                </div>
                            </div>

                        </div>
                        <div class="containers2 over hides">
                            <div class="newkeyword_right fr over" style="width:100%;">
                                <h3> 输入关键词 </h3>
                                <p>请输入关键词信息（每行一个），并用Tab键或逗号（英文）分隔各字段，也可直接从Excel复制并粘贴，输入-， 则将对应信息恢复为默认</p>
                                <div class="newkeyword_right_mid">
                                    <p>格式：关键词名称（必填），匹配模式，出价（为0则使用推广单元出价），访问URL，移动访问URL，启用/暂停</p>
                                    <p>例如：鲜花，精确，1.0，www.com.perfect.api.baidu.com,www.com.perfect.api.baidu.com,启用</p>
                                    <textarea>

                                    </textarea>
                                    <p>或者从相同格式的csv文件输入：<input type="button" class="zs_input2" value="选择文件">&nbsp;(<20万行)</p>
                                    <p><input type="checkbox">&nbsp;用这些关键词替换目标推广单元的所有对应内容</p>

                                </div>
                            </div>
                        </div>

                    </div>
                </div>


            </div>
            <div class="main_bottom" style=" background:none;">
                <div class="w_list03">
                    <ul>
                        <li class="current" id="downloadAccount">下一步</li>
                        <li>完成</li>
                        <li class="close">取消</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="containers over hides">
            <div class="assembly_under over" >
                <div class="assembly_right3 over">
                    <div class="newkeword_end">
                        <ul>
                            <li>
                                <div class="newkeyword_end1"> <span>[+]</span>新增的关键词一个</div>
                                <div class="newkeyword_end2 ">
                                    <p><input type="radio" checked="checkde" name="addnew">添加已选择的关键词</p>
                                    <p><input type="radio"  name="addnew">不添加</p>
                                    <div class="list4" style="height:300px;">
                                        <table width="100%" cellspacing="0" border="0" width="500px">
                                            <thead>
                                            <tr class="list02_top">
                                                <td>&nbsp; <input type="checkbox" id="checkAll2" checked="checkde" >关键词</td>
                                                <td>&nbsp;展现理由</td>
                                                <td>&nbsp;日均搜索量</td>
                                                <td>&nbsp;竞争激烈程度
                                                    <div class="set fr"></div>
                                                </td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr class="list2_box2">
                                                <td> &nbsp;<input type="checkbox" checked="checked" name="subbox2">北京婚博会</td>
                                                <td>&nbsp;111</td>
                                                <td>&nbsp;1111</td>
                                                <td>&nbsp;1111 </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
              <div class="main_bottom" style="margin:0px; background:none;">
                <div class="w_list03">
                    <ul>
                        <li class="current" >上一步</li>
                        <li>完成</li>
                        <li class="close">取消</li>
                    </ul>
                </div>
            </div>

        </div>
    </div>
</div>
        <%--alert提示类--%>
        <div class="box alertBox" style=" width: 230px;display:none;z-index: 1005" id="newKeyWordAlertPrompt">
            <h2 id="newKeyWordAlertPromptTitle">
                <span class="fl alert_span_title" id="newKeyWordAlertPrompt_title"></span>
                <%--<a href="#" class="close">×</a></h2>--%>
            <%--<a href="#" onclick="newKeyWordAlertPrompt.hide()" style="color: #cccccc;float: right;font-size: 20px;font-weight: normal;opacity: inherit;text-shadow: none;">×</a></h2>--%>
            </h2>
            <div class="mainlist">
                <div class="w_list03">
                    <ul class="zs_set">
                        <li class="current" onclick="newKeyWordAlertPrompt.hide()">确认</li>
                    </ul>
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
    $(function () {
        rDrag.init(document.getElementById('newKeyWordAlertPromptTitle'));
        var $tab_li = $('.addplan_top ul li');
        $('.addplan_top ul li').click(function () {
            $(this).addClass('current');
            var index = $tab_li.index(this);
            $('div.plan_under > div').eq(index).show().siblings().hide();
        });
        var $tab_li = $('.newkeyeord_title ul li input');
        $('.newkeyeord_title ul li input').click(function () {
            $(this).addClass('current').siblings().removeClass('current');
            var index = $tab_li.index(this);
            $('div.newkeyword_content > div').eq(index).show().siblings().hide();
        });
        $(".newkeyword_add").click(function(){
            if ($(".newkeyword_list").css("display") == "none") {
                $(".newkeyword_list").show();
            }
            else {
                $(".newkeyword_list").hide();
            }
        });
        $(".newkeyword_end1").click(function(){
            if ($(".newkeyword_end2").css("display") == "none"){
                $(".newkeyword_end2").show();
                $(".newkeyword_end1").find(" span").text("[-]");
            }
            else {
                $(".newkeyword_end2").hide();
                $(".newkeyword_end1").find("span").text("[+]");
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
    var newKeyWordAlertPrompt = {
        show:function(content){
            $(".TB_overlayBG_alert").css({
                display: "block", height: $(document).height()
            });/*蒙版显示*/
            $("#newKeyWordAlertPrompt").css({
                left: ($("body").width() - $("#download").width()) / 2 - 20 + "px",
                top: ($(window).height() - $("#download").height()) / 2 + $(window).scrollTop() + "px",
                display: "block"
            });/*显示提示DIV*/
            $("#newKeyWordAlertPrompt_title").html(content);
        },
        hide:function(){
            $(".TB_overlayBG_alert").css({
                display: "none"
            });/*蒙版显示*/
            $("#newKeyWordAlertPrompt").css({
                display: "none"
            });/*显示提示DIV*/
        }
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

    var zNodes =[
        { id:1, pId:0, name:"父节点1", title:"", checked:true, open:true},
        { id:11, pId:1, name:"父节点11", title:"", checked:true},
        { id:111, pId:11, name:"叶子节点111", title:"", checked:true, isHidden:true},
        { id:112, pId:11, name:"叶子节点112", title:""},
        { id:113, pId:11, name:"叶子节点113", title:""},
        { id:12, pId:1, name:"父节点12", title:"", isHidden:true},
        { id:121, pId:12, name:"叶子节点121", title:""},
        { id:122, pId:12, name:"叶子节点122", title:"", isHidden:true},
        { id:123, pId:12, name:"叶子节点123", title:""},
        { id:2, pId:0, name:"父节点2", title:""},
        { id:21, pId:2, name:"父节点21", title:"", isHidden:true},
        { id:211, pId:21, name:"叶子节点211", title:""},
        { id:212, pId:21, name:"叶子节点212", title:""},
        { id:213, pId:21, name:"叶子节点213", title:""},
        { id:22, pId:2, name:"父节点22", title:""},
        { id:221, pId:22, name:"叶子节点221", title:""},
        { id:222, pId:22, name:"叶子节点222", title:""},
        { id:223, pId:22, name:"叶子节点223", title:""}
    ];

    function onCheck(e, treeId, treeNode) {
        count();
    }

    function setTitle(node) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = node ? [node]:zTree.transformToArray(zTree.getNodes());
        for (var i=0, l=nodes.length; i<l; i++) {
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

        for (var i=0, j=hiddenNodes.length; i<j; i++) {
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
//            alert("请至少选择一个节点");
            newKeyWordAlertPrompt.show("请至少选择一个节点");
            return;
        }
        zTree.hideNodes(nodes);
        setTitle();
        count();
    }

    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        $("#hideNodesBtn").bind("click", {type:"rename"}, hideNodes);
        $("#showNodesBtn").bind("click", {type:"icon"}, showNodes);
        setTitle();
        count();
    });

</script>
</body>
</html>
