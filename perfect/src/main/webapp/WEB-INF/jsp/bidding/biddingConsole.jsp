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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css">
</head>
<body>
<div id="bidding_box" style="position:absolute; top:15%; left:20%;">
<div style="overflow:hidden;">
    <div class="control-group fl">
        <label class="control-label fl" for="url" style="line-height:30px; margin-right: 10px;">请输入URL请求地址</label>
        <input type="text"  class="form-control fl" id="url">
    </div>
    <div  class="control-group fl " >
            <button class="btn btn-primary btn-lg" type="button" style="width:80px; margin-left:10px;" onclick=submitUrl() >提交</button>
    </div>
</div>
<div style="  width: 800px; height: 600px;  overflow:hidden; ">
    <table id="table1" border="0" cellspacing="0" width="100%" class="table table-striped table-bordered table-hover datatable dataTable" aria-describedby="DataTables_Table_0_info">
        <thead>
        <tr>
            <td>URL</td>
            <td>是否空闲</td>
            <td>下次启动时间</td>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>URL</td>
            <td>是否空闲</td>
            <td>下次启动时间</td>
        </tr>
        <tr>
            <td>URL</td>
            <td>是否空闲</td>
            <td>下次启动时间</td>
        </tr>
        <tr>
            <td>URL</td>
            <td>是否空闲</td>
            <td>下次启动时间</td>
        </tr>
        <tr>
            <td>URL</td>
            <td>是否空闲</td>
            <td>下次启动时间</td>
        </tr>
        <tr>
            <td>URL</td>
            <td>是否空闲</td>
            <td>下次启动时间</td>
        </tr>

        </tbody>
    </table>
</div>
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


</script>
</body>
</html>
