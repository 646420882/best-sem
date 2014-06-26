<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2014-6-8
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>百度推广</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="${pageContext.request.contextPath}/public/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/public/css/zTreeStyle/zTreeStyle.css"
          rel="stylesheet">
    <style type="text/css">
        body {
            padding-top: 60px;
            padding-bottom: 40px;
        }

        @media (max-width: 980px) {
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }
    </style>
</head>
<body>
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid" style="margin-top: 10px">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="brand" href="#">Baidu推广</a>

            <div class="nav-collapse collapse">
                <p class="navbar-text pull-right">
                    ${currSystemUserName} <a href="${pageContext.request.contextPath}/logout"
                                             class="navbar-link">退出</a>
                </p>
                <ul class="nav">
                    <li class="active"><a href="${pageContext.request.contextPath}/home">首页</a></li>
                    <li><a href="#accountCenter">账户中心</a></li>
                    <li><a href="#contact">财务</a></li>
                    <li><a href="${pageContext.request.contextPath}/main/convenienceManage">便捷管理</a></li>
                    <li><a href="${pageContext.request.contextPath}/main/spreadManage">推广管理</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="container-fluid">
    <div class="row-fluid" style="margin-top: 10px">
        <div class="span3" style="height: 560px; border: 1px solid #f1e1ff">
            <label>百度账户绑定</label>

            <div id="zTree" class="ztree"
                 style="table-layout: fixed; overflow: auto; height: 92%; scrollbar-base-color: #f0f0f0">
            </div>
            <a class="btn" onclick="javascript:$('#baiduAccount').show();">添加账户</a>

            <div id="baiduAccount" style="margin-top: -100%; display: none">
                百度账户: <input type="text" id="baiduAccountName" name="baiduAccountName" style="width: 200px"/><br/>
                密码: <input type="text" id="baiduAccountPasswd" name="baiduAccountPasswd" style="width: 200px"/><br/>
                token: <input type="text" id="baiduAccountToken" name="baiduAccountToken" style="width: 200px"/><br/>
                <a class="btn" onclick=addBaiduAccount();>提交</a>
            </div>
        </div>
    </div>
</div>

<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script>
    var basePath = "<%=basePath%>";

    var setting = {
        view: {
            showLine: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeClick: beforeClick,
            beforeAsync: beforeAsync,
            onAsyncError: onAsyncError,
            onAsyncSuccess: onAsyncSuccess
        }
    };
    var zNodes = "";

    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i = 0, l = childNodes.length; i < l; i++) {
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
    }
    function beforeClick(treeId, treeNode) {
        if (treeNode.level == 0) {
            //点击的是父节点
            //
            alert(treeNode.id);
            //

        }
    }
    var log, className = "dark";
    function beforeAsync(treeId, treeNode) {
        className = (className === "dark" ? "" : "dark");
        showLog("[ " + getTime() + " beforeAsync ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
        return true;
    }
    function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
        showLog("[ " + getTime() + " onAsyncError ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
    }
    function onAsyncSuccess(event, treeId, treeNode, msg) {
        showLog("[ " + getTime() + " onAsyncSuccess ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
    }

    function showLog(str) {
        if (!log) log = $("#log");
        log.append("<li class='" + className + "'>" + str + "</li>");
        if (log.children("li").length > 8) {
            log.get(0).removeChild(log.children("li")[0]);
        }
    }
    function getTime() {
        var now = new Date(),
                h = now.getHours(),
                m = now.getMinutes(),
                s = now.getSeconds(),
                ms = now.getMilliseconds();
        return (h + ":" + m + ":" + s + " " + ms);
    }

    $(function () {
        $.ajax({
            url: basePath + "account/getAllBaiduAccount",
            type: "POST",
            dataType: "json",
            async: false,
            success: function (data, statusText, jqXHR) {
                zNodes = data.tree;
            }
        });
        //加载树
        $.fn.zTree.init($("#zTree"), setting, zNodes);
    });

    var addBaiduAccount = function () {
        var name = $("#baiduAccountName").val();
        var passwd = $("#baiduAccountPasswd").val();
        var token = $("#baiduAccountToken").val();
        var currSystemUserName = "${currSystemUserName}";

        $.ajax({
            url: basePath + "account/addBaiduAccount",
            type: "POST",
            dataType: "json",
            data: {
                baiduAccountName: name,
                baiduAccountPasswd: passwd,
                baiduAccountToken: token,
                currSystemUserName: currSystemUserName
            },
            async: false,
            success: function (data, statusText, jqXHR) {
                if (data.status) {
                    alert("success!");
                }
            }
        });
    };

</script>
</body>
</html>