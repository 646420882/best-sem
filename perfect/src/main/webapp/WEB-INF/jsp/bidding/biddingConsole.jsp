<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 14-9-26
  Time: 下午3:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title>Bidding Console</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css">
</head>
<body>

<div style="margin-left: 15%; margin-top: 20%">
    <div class="control-group info">
        <label class="control-label" for="url">请输入URL请求地址</label>

        <div class="controls">
            <input type="text" id="url">
        </div>
    </div>
    <div>
        <p>
            <button class="btn btn-primary" type="button" onclick=submitUrl()>提交</button>
        </p>
    </div>
</div>
<div style="margin-left: 45%; margin-top: -20%; width: 800px; height: 600px; border: 1px solid #30b0eb;">
    <table id="table1" border="0" cellspacing="0" width="100%">
        <thead>
        <tr>
            <td>URL</td>
            <td>是否空闲</td>
            <td>下次启动时间</td>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.min.js"></script>
<script type="application/javascript">
    var submitUrl = function () {
        var url = $("#url").val();
        $.ajax({
            url: "/biddingUrl",
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

    $(function () {
    });
</script>
</body>
</html>
