<%--
  Created by IntelliJ IDEA.
  User: subdong
  Date: 15-10-19
  Time: 上午11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    <title>大数据智能营销</title>
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/css/accountCss/backstage.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">

    <style>
        .displayNone {
            display: none;
        }
    </style>
</head>
<body>
<jsp:include page="../homePage/pageBlock/backstage_nav.jsp"/>
<div class="backstage_concent mid over">
    <div class="backstage_list over">
        <a href='javascript:void(0)' onclick='showDialogMon(0)'>添加</a>
        <table id="table1" class="table table-striped table-bordered table-hover datatable dataTable"
               aria-describedby="DataTables_Table_0_info">
            <thead>
            <tr>
                <td width="40%">问题（双击修改数据）</td>
                <td width="40%">>答案</td>
                <td width="9%">>所属类别</td>
                <td width="5%">>字体颜色</td>
                <td width="6%">>操作</td>
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
        <span class="fl">数据</span>
        <a href="javascript:closeAlert()" class="close">×</a></h2>

    <div class="mainlist">
        <table class="table table-striped table-bordered table-hover datatable dataTable"
               aria-describedby="DataTables_Table_0_info">
            <thead>
            <tbody>
            <tr>
                <td>提问：</td>
                <td><textarea id="questions" style="width: 400px"></textarea></td>
            </tr>
            <tr>
                <td>回答：</td>
                <td><textarea id="answers" style="width: 400px"></textarea></td>
            </tr>
            <tr>
                <td>问答类型：</td>
                <td>
                    <select id="questionType" no-sub="true">
                        <option value="0">账户全景</option>
                        <option value="1">推广助手</option>
                        <option value="2">智能结构</option>
                        <option value="3">智能竞价</option>
                        <option value="4">数据报告</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>标题颜色：</td>
                <td>
                    <select id="color" no-sub="true">
                        <option value="black" style="background-color:black">黑色</option>
                        <option value="red" style="background-color:red">红色</option>
                        <option value="green" style="background-color:green">绿色</option>
                        <option value="blue" style="background-color:blue">蓝色</option>
                    </select>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul id="htmlHost">

            </ul>
        </div>
    </div>
</div>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<script>
    $.ajax({
        url: "/qa/findAnswers",
        dataType: "json",
        success: function (data) {
            console.log(data);
            $("#shuju").empty();
            var html_account = "";
            $.each(data.rows, function (i, item) {
                html_account = "<tr class='dbclickData' cid = " + item.id + "><td>" + item.questions + "</td><td>" + item.answers + "</td>" +
                        "<td>" + (item.questionType == 0 ? "账户全景" : item.questionType == 1 ? "推广助手" : item.questionType == 2 ? "智能结构" : item.questionType == 3 ? "智能竞价" : "数据报告") + "</td>" +
                        "<td>" + (item.fontColor == "black" ? "黑色" : item.fontColor == "red" ? "红色" : item.fontColor == "green" ? "绿色" : "蓝色") + "</td><td>"
                if (item.id != 0) {
                    html_account = html_account +
                            "<a href='javascript:void(0)' class='delete' cid = " + item.id + ">删除</a></td></tr>";
                }
                $("#shuju").append(html_account);
            });
        }
    });
    var _qid;
    $("body").on("dblclick", ".dbclickData", function () {
        _qid = $(this).attr("cid");
        var question = $(this).find("td")[0].innerText;
        var answers = $(this).find("td")[1].innerText;
        var type = $(this).find("td")[2].innerText;
        var color = $(this).find("td")[3].innerText;
        color = (color == "黑色" ? "black" : color == "红色" ? "red" : color == "绿色" ? "green" : "blue");
        type = (type == "账户全景" ? 0 : type == "推广助手" ? 1 : type == "智能结构" ? 2 : type == "智能竞价" ? 3 : 4);
        $("#questions").val(question);
        $("#answers").val(answers);
        $("#questionType").val(type);
        $("#color").val(color)
        showDialogMon()
    });
    $("body").on("click", "#modify", function () {
        var questions = $("#questions").val();
        var answers = $("#answers").val();
        var questionType = $("#questionType").val();
        var color = $("#color").val();
        var querstions = {
            id:_qid,
            questions: questions,
            answers: answers,
            questionType: questionType,
            fontColor: color
        };
        $.ajax({
            url: "/qa/modifyAnswers",
            type: "POST",
            data: JSON.stringify(querstions),
            contentType: "application/json; charset=utf-8",
            success: function () {
                location.reload();
            }
        });
    })
    $("body").on("click", ".delete", function () {
        $.ajax({
            url: "/qa/delAnswers",
            type: "GET",
            data: {
                id: $(this).attr("cid")
            },
            success: function () {
                location.reload();
            }
        });
    });
    $("body").on("click", "#addQA", function () {
        var questions = $("#questions").val();
        var answers = $("#answers").val();
        var questionType = $("#questionType").val();
        var color = $("#color").val();
        var querstions = {
            questions: questions,
            answers: answers,
            questionType: questionType,
            fontColor: color
        };
        $.ajax({
            url: "/qa/addAnswers",
            type: "POST",
            data: JSON.stringify(querstions),
            contentType: "application/json; charset=utf-8",
            success: function () {
                location.reload();
            }
        });

    });

    /**
     * 添加监控对象弹出框显示
     */
    function showDialogMon(type) {
        if (type == 0) {
            $("#htmlHost").html("<li id='addQA'>确认</li><li onclick='closeAlert();'>取消</li>");
        } else {
            $("#htmlHost").html("<li id='modify'>确认</li><li onclick='closeAlert();'>取消</li>")
        }
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $("#enableDiv").css({
            left: ($("body").width() - $("#enableDiv").width()) / 2 - 20 + "px",
            top: ($(window).height() - $("#enableDiv").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    }
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

