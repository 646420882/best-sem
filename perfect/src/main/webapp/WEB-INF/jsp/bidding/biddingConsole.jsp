<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2014-9-26
  Time: 下午3:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title>Bidding Console</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css">
    <style type="text/css">
        #table1 {
            width: 100%;
            border-collapse: collapse;
        }

        #table1 thead td {
            vertical-align: middle;
            text-align: center;
        }
    </style>
</head>
<body>
<div id="bidding_box" style="position:absolute; top:15%; left:10%;">
    <div style="overflow:hidden;">
        <div class="control-group fl">
            <label class="control-label fl" for="url" style="line-height:30px; margin-right: 10px;">请输入URL请求地址</label>
            <input type="text" class="form-control fl" id="url">
        </div>
        <div class="control-group fl ">
            <button class="btn btn-primary btn-lg" type="button" style="width:80px; margin-left:10px;"
                    onclick=submitUrl()>提交
            </button>
        </div>
    </div>
    <div style="width: 80%; height: 60%;">
        <table id="table1"
               class="table table-striped table-bordered table-hover datatable dataTable"
               aria-describedby="DataTables_Table_0_info">
            <thead>
            <tr>
                <td>index</td>
                <td>URL</td>
                <td>是否空闲</td>
                <%--<td>下次启动时间</td>--%>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.min.js"></script>
<script type="application/javascript">

    $(function () {
        $.ajax({
            url: "/biddingUrl/list",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                $("#table1 tbody").empty();
                var results = data.rows;
                if (results != null && results.length > 0) {
                    $.each(results, function (i, item) {
                        var tr = "<tr><td>" + (i + 1) + "</td><td>" + item.request + "</td><td>" + item.idle + "</td></tr>";
                        $("#table1 tbody").append(tr);
                    });
                }
            }
        });
    });

    var submitUrl = function () {
        var url = $("#url").val();
        if (url == null || url.trim().length == 0) {
            return false;
        }
        $.ajax({
            url: "/biddingUrl/add",
            type: "POST",
            dataType: "json",
            data: {
                url: encodeURIComponent(url)
            },
            success: function (data, textStatus, jqXHR) {
                alert(data.status);
            }
        });
    };


</script>
</body>
</html>
