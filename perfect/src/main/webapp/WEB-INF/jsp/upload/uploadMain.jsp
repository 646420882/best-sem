<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/8/4
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>批量上传</title>
    <script type="text/javascript" src="${path}/public/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${path}/public/js/jquery.ui.widget.min.js"></script>
    <script type="text/javascript" src="${path}/public/js/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${path}/public/js/jquery.fileupload.js"></script>
    <script type="text/javascript" src="${path}/public/js/bootstrap.min.js"></script>
    <link href="${path}/public/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="${path}/public/css/dropzone.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript">
        $(function () {
            $('#fileupload').fileupload({
                dataType: 'json',
                done: function (e, data) {
                    $("tr:has(td)").remove();
                    $.each(data.result, function (index, file) {
                        $("#uploaded-files").append(
                                $('<tr/>')
                                        .append($('<td/>').text(file.fileName))
                                        .append($('<td/>').text(file.fileSize))
                                        .append($('<td/>').text(file.fileType))
                                        .append($('<td/>').html("<a href='/upload/get/"+index+"'>Click</a>"))
                        )//end $("#uploaded-files").append()
                    });
                },
                progressall: function (e, data) {
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    $('#progress .bar').css(
                            'width', progress + '%'
                    );
                },
                dropZone: $('#dropzone')
            });
        })
    </script>
</head>
<body>
<h4>Spring MVC - jQuery File Upload</h4>
<div style="width:500px;padding:20px">

    <input id="fileupload" type="file" name="files[]" data-url="/upload/up" multiple>

    <div id="dropzone" class="fade well">Drop files here</div>

    <div id="progress" class="progress">
        <div style="width: 0%;" class="bar"></div>
    </div>

    <table id="uploaded-files" class="table">
        <tr>
            <th>File Name</th>
            <th>File Size</th>
            <th>File Type</th>
            <th>Download</th>
        </tr>
    </table>

</div>
</body>
</html>
