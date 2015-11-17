<%--
  Created by IntelliJ IDEA.
  User: SubDong
  Date: 2014/10/20
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>审核帐号</title>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">
    <link rel="stylesheet" type="text/css" href="/public/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/public/css/accountCss/backstage.css">
</head>
<style>
    .mid {
        float: none;
        width: 1000px;
        margin: 0px auto;

    }

    .table tr td {
        text-align: center;
    }
</style>
<body>
<jsp:include page="../homePage/pageBlock/backstage_nav.jsp"/>
<div class="backstage_concent mid over">
    <div id="bidding_box">
            <div style="color: red;font-size: 16px;padding: 10px 0px 20px 20px">注：被停用的系统帐号将不能进行任何操作！请谨慎操作！！</div>
            <table id="table1" class="table table-striped table-bordered table-hover datatable dataTable"
                   aria-describedby="DataTables_Table_0_info">
                <thead>
                <tr>
                    <td>系统帐号</td>
                    <td>审核状态</td>
                    <td>启用状态</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody id="shuju"></tbody>
            </table>
    </div>
</div>
<div class="TB_overlayBG"></div>
<div class="TB_overlayBG_alert"></div>
<%--添加监控对象弹出窗口--%>
<div class="box" style="display:none" id="enableDiv">
    <h2 id="enableTUO">
        <span class="fl">提示框</span>
        <a href="javascript:closeAlert()" class="close">×</a></h2>

    <div class="mainlist">
        是否确认启用/禁用！！
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li id="enableAccount">确认</li>
                <li onclick="closeAlert();">取消</li>
            </ul>
        </div>
    </div>
</div>

<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="application/javascript">
    var _userName = "";
    var _baiduId = "";
    var _state = "";
    String.prototype.trims = function () {
        return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
    };
    $(function () {
        showData();
        $("body").on("click", "#enableAccount", function () {
            if (_userName == null || _userName.trims().length == 0) {
                return false;
            }
            $.ajax({
                url: "/account/updateAccountAllState",
                type: "POST",
                dataType: "json",
                data: {
                    userName: _userName,
                    baiduId: _baiduId,
                    state: _state
                },
                success: function (data, textStatus, jqXHR) {
                    showData();
                    closeAlert();
                    if (data.rows == 1) {
                    } else {
//                        alert("操作失败！");
                        baiduAccountAlertPrompt.show("操作失败！");
                    }
                }
            });
        });
    });


    /**
     * 添加监控对象弹出框显示
     */
    function showDialogMon(thisAudit, state) {
        _userName = $(thisAudit).parent().parent().find("td").attr("cname");
        _baiduId = $(thisAudit).parent().parent().find("td").attr("cid");
        _state = state;
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $("#enableDiv").css({
            left: ($("body").width() - $("#enableDiv").width()) / 2 - 20 + "px",
            top: ($(window).height() - $("#enableDiv").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    }

    function showData() {
        $.ajax({
            url: "/account/getAccountAll",
            dataType: "json",
            success: function (data) {
                $("#shuju").empty();
                var html_account = "";
                $.each(data.rows, function (i, item) {
                    html_account = "<tr><td cname=" + item.userName + " cid = " + item.accountState + ">" + item.userName + "</td><td " + ((item.userState == 0) ? 'style=color:red' : '') + ">" +
                    ((item.userState == 0) ? '未通过审核' : '已通过审核') + "</td><td " + ((item.accountState == 1) ? '' : 'style=color:red') + ">" +
                    ((item.accountState == 1) ? '已启用' : '已停用') + "</td><td>"
                    if (item.id != 0) {
                        html_account = html_account +
                        "<a href='javascript:void(0)' onclick='showDialogMon(this,1)'>启用</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                        "<a href='javascript:void(0)' onclick='showDialogMon(this,0)'>停用</a></td></tr>";
                    }

                    $("#shuju").append(html_account);
                });
            }
        });
    }

    /*    window.onload = function () {
     rDrag.init(document.getElementById('auditTUO'));
     };*/

    /**
     * 关闭弹窗
     */
    function closeAlert() {
        $(".TB_overlayBG").css("display", "none");
        //审核窗口关闭
        $("#enableDiv ").css("display", "none");
    }
</script>


</body>
</html>
