<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/8/15
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>URL地址检测</title>
    <link href="/public/plugs/uploadify/uploadify.css" rel="stylesheet"/>
    <script type="text/javascript" src="/public/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="/public/plugs/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript">
        $(function(){
            $("#file_upload").uploadify({
                'buttonText' : '请选择',
                'height' : 30,
                'swf' : '/public/plugs/uploadify/uploadify.swf',
                'uploader' : '/upload/uploadFile?jsessionid=<%=request.getSession().getId()%>',
                'width' : 120,
                'auto':false,
                'fileObjName'   : 'file',
                'onUploadSuccess' : function(file, data, response) {
                    if(data=="1"){
                    alert(file.name+"上传成功");
                    }else{
                        alert(file.name+"文件后缀名不正确!!");
                    }
                }
            });
        });
    </script>
</head>
<body>
<input type="file" name="fileName" id="file_upload" />
<a href="javascript:$('#file_upload').uploadify('upload', '*')">上传文件</a> | <a href="javascript:$('#file_upload').uploadify('stop')">停止上传!</a>
</body>
</html>
