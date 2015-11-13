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
          href="${pageContext.request.contextPath}/public/css/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/css/public/main.css">
    <style>
        html, body {
            height: 100%;
        }

        .ztree li span.button.chk {
            margin: 0 6px 0 6px;
        }

        .ztree li {
            padding: 5px;
        }

        .countdownloadlist {
            background-color: white;
            overflow-y: scroll;
            height: 85%;
        }


    </style>
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/public/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/public/js/jquery.ztree.excheck-3.5.js"></script>
    <SCRIPT type="text/javascript">
        <!--
        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            view: {
                showLine: false,
                showIcon: false
            }
        };
        var zNodes = [
            {id: 11, pId: 1, name: "账户2", open: true,},
            {id: 111, pId: 11, name: "推广计划1"},
            {id: 111, pId: 11, name: "推广计划2"},
            {id: 22, pId: 2, name: "账户2", open: true},
            {id: 222, pId: 22, name: "推广计划1"},
            {id: 222, pId: 22, name: "推广计划2"},
            {id: 33, pId: 3, name: "账户2", open: true},
            {id: 333, pId: 33, name: "推广计划1"},
            {id: 333, pId: 33, name: "推广计划2"},
            {id: 333, pId: 33, name: "推广计划2"},
            {id: 333, pId: 33, name: "推广计划2"},
            {id: 333, pId: 33, name: "推广计划2"},

        ];
        function setCheck() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                    py = $("#py").attr("checked") ? "p" : "",
                    sy = $("#sy").attr("checked") ? "s" : "",
                    pn = $("#pn").attr("checked") ? "p" : "",
                    sn = $("#sn").attr("checked") ? "s" : "",
                    type = {"Y": py + sy, "N": pn + sn};
//      zTree.setting.check.chkboxType = type;
            zTree.setting.check.chkboxType = {"Y": "s", "N": "ps"};
        }
        function showCode(str) {
            if (!code) code = $("#code");
            code.empty();
            code.append("<li>" + str + "</li>");
        }

        $(document).ready(function () {
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            setCheck();
            $("#py").bind("change", setCheck);
            $("#sy").bind("change", setCheck);
            $("#pn").bind("change", setCheck);
            $("#sn").bind("change", setCheck);
        });
        //    弹窗关闭
        function closeDialog() {
            top.dialog.getCurrent().close().remove();
        }
        //-->
    </SCRIPT>
</head>
<body>
<div style="background-color: #f8f8f8;width: 100%;height: 90%;">
    <div style="font-size: 14px;height: 30px;">选择账户以及推广计划</div>
    <div class='j_list01 over countdownloadlist'>
        <ul id='treeDemo' class='ztree'></ul>
    </div>
</div>
<button onclick="closeDialog()" class="count_button_cancel">取消</button>
<button onclick="closeDialog()" class="count_button_sure">确定</button>
</body>
</html>
