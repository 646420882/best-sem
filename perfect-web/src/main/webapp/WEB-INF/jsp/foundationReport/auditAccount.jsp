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
    .mid{
        float: none;
        width: 1000px;
        margin: 0px auto;

    }
    .table tr td{
        text-align: center;
    }
</style>
<body>
<jsp:include page="../homePage/pageBlock/backstage_nav.jsp"/>
<div class="backstage_concent mid over">
    <div id="bidding_box">
        <div>
            <table id="table1" class="table table-striped table-bordered table-hover datatable dataTable" aria-describedby="DataTables_Table_0_info">
                <thead>
                <tr>
                    <td>帐号</td>
                    <td>审核状态</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody id="shuju"></tbody>
            </table>
        </div>
    </div>
</div>
<div class="TB_overlayBG"></div>
<%--添加监控对象弹出窗口--%>
<div class="box" style="display:none" id="auditDiv">
    <h2 id="auditTUO">
        <span class="fl">审核帐号</span>
        <a href="javascript:closeAlert()" class="close">×</a></h2>
    <div class="mainlist">
        是否确认审核通过，使此帐号在系统中使用！！
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li id="auditAccount">确认</li>
                <li onclick="closeAlert();">取消</li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="application/javascript">
    var username="";
    String.prototype.trims = function () {
        return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
    };
    $(function () {
        showData();
        $("body").on("click","#auditAccount",function(){
            if (username == null || username.trims().length == 0) {
                return false;
            }
            $.ajax({
                url: "/account/auditAccount",
                type: "POST",
                dataType: "json",
                data: {
                    userName: username
                },
                success: function (data, textStatus, jqXHR) {
                    if(data.struts == 1){
                        showData();
                        closeAlert();
                        alert("审核成功！");
                    }
                }
            });
        });
    });


    /**
     * 添加监控对象弹出框显示
     */
    function showDialogMon(thisAudit) {
        username = $(thisAudit).parent().parent().find("td").attr("cname");
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $("#auditDiv").css({
            left: ($("body").width() - $("#auditDiv").width()) / 2 - 20 + "px",
            top: ($(window).height() - $("#auditDiv").height()) / 4 + $(window).scrollTop() + "px",
            display: "block"
        });
    }

    function showData(){
        $.ajax({
            url: "/account/getAccount",
            dataType: "json",
            success: function (data) {
                $("#shuju").empty();
                var html_account = "";
                $.each(data.rows,function(i,item){
                    html_account = "<tr><td cname="+item.userName+">"+item.userName+"</td><td>"+((item.state == 0)?'未审核':'已审核')+"</td><td><a href='javascript:void(0)' onclick='showDialogMon(this)'>审核</a></td></tr>";
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
        $("#auditDiv ").css("display", "none");
    }
</script>


</body>
</html>
