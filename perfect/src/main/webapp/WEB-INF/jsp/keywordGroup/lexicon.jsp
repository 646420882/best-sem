<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2014-10-9
  Time: 12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title>Lexicon Console</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/backstage.css">
</head>
<body>
<jsp:include page="../homePage/pageBlock/backstage_nav.jsp"/>
<div class="backstage_concent mid over">
    <!-- 用于文件上传的表单元素 -->
    <div class="backstage_list over">
      <form id="fileForm" name="fileForm" method="post" class="form-inline" enctype="multipart/form-data"
          action="${pageContext.request.contextPath}/admin/lexicon/upload">
          <ul>
              <li><span>Upload File: </span><input type="file" name="excelFile" style="border:none;"/></li>
              <li><input type="submit" class="btn sure" value="导入"/></li>
          </ul>
      </form>
       </div>
</div>
<!--
<div>Progessing (in Bytes): <span id="bytesRead">
 </span> / <span id="bytesTotal"></span>
</div>
-->
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.min.js"></script>
<script type="application/javascript">
    var uploadAndSubmit = function () {
        var form = document.forms["fileForm"];

        if (form["file"].files.length > 0) {
            // 寻找表单域中的 <input type="file" ... /> 标签
            var file = form["file"].files[0];
            // try sending
            var reader = new FileReader();

            reader.onloadstart = function () {
                // 这个事件在读取开始时触发
                console.log("onloadstart");
                document.getElementById("bytesTotal").textContent = file.size;
            };
            reader.onprogress = function (p) {
                // 这个事件在读取进行中定时触发
                console.log("onprogress");
                document.getElementById("bytesRead").textContent = p.loaded;
            };

            reader.onload = function () {
                // 这个事件在读取成功结束后触发
                console.log("load complete");
            };

            reader.onloadend = function () {
                // 这个事件在读取结束后，无论成功或者失败都会触发
                if (reader.error) {
                    console.log(reader.error);
                } else {
                    document.getElementById("bytesRead").textContent = file.size;
                    // 构造 XMLHttpRequest 对象，发送文件 Binary 数据
                    var xhr = new XMLHttpRequest();
                    xhr.open(/* method */ "POST",
                            /* target url */ "/admin/lexicon/upload?fileName=" + encodeURIComponent(file.name)
                            /*, async, default to true */);
                    xhr.overrideMimeType("application/octet-stream");
                    xhr.sendAsBinary(reader.result);
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState == 4) {
                            if (xhr.status == 200) {
                                console.log("upload complete");
                                console.log("response: " + xhr.responseText);
                            }
                        }
                    }
                }
            };

            reader.readAsBinaryString(file);
        } else {
            alert("Please choose a file.");
        }
    };
</script>
</body>
</html>
