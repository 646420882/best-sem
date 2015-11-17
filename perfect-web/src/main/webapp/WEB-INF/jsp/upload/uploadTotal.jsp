<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/8/19
  Time: 12:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>统计</title>
    <link href="/public/plugs/uploadify/uploadify.css" rel="stylesheet"/>
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <script type="text/javascript" src="/public/plugs/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript">
        $(function () {
            loadFileList();
            $("#file_upload").uploadify({
                'buttonText': '请选择',
                'height': 30,
                'swf': '/public/plugs/uploadify/uploadify.swf',
                'uploader': '/upload/upTotal?jsessionid=<%=request.getSession().getId()%>',
                'width': 120,
                'auto': false,
                'fileObjName': 'file',
                'onUploadSuccess': function (file, data, response) {
                    if (data == "1") {
                        addSuccess(file);
                    }
                }
            });
        });
        function addSuccess(file) {
            var _tr = "<tr>";
            var td = "<td><input type='checkbox' name='cs' value=" + file.name + "></td><td>" + file.name + "</td><td>" + getSize(file.size) + "</td><td>" + file.type + "</td><td><a href='/upload/getTotal?fileName=" + file.name + "'>下载</a>" +
                    "&nbsp;<a href='javascript:void(0)' step='" + file.name + "' onclick='delFile(this)'>删除</a></td>";
            var _etr = "</tr>";
            $("#t1").append(_tr + td + _etr);
        }
        function getSize(fileSize) {
            var fileSizeString = "";
            if (fileSize < 1024) {
                fileSizeString = parseFloat(fileSize) + "B";
            } else if (fileSize < 1048576) {
                fileSizeString = parseFloat(fileSize / 1024).toFixed(2) + "Kb";
            } else if (fileS < 1073741824) {
                fileSizeString = parseFloat(fileSize / 1048576).toFixed(2) + "Mb";
            } else {
                fileSizeString = parseFloat(fileSize / 1073741824).toFixed(2) + "Git";
            }
            return fileSizeString;
        }
        function loadFileList() {
            $("#t1").remove("tr:gt(0)");
            $.get("/upload/talList", function (result) {
                var json = eval("(" + result + ")");
                for (var i = 0; i < json.length; i++) {
                    var _tr = "<tr>";
                    var td = "<td><input type='checkbox' name='cs' value=" + json[i].fileName + "></td><td>" + json[i].fileName + "</td><td>" + json[i].fileSize + "</td><td>" + json[i].fileExt + "</td><td><a href='/upload/getTotal?fileName=" + json[i].fileName + "'>下载</a>" +
                            "&nbsp;<a href='javascript:void(0)' step='" + json[i].fileName + "' onclick='delFile(this)'>删除</a></td>";
                    var _etr = "</tr>";
                    $("#t1").append(_tr + td + _etr);
                }

            });
        }
        function delFile(res) {
            var _this = $(res);
            var parents = _this.parents("tr");
            var conf = confirm("是否删除？");
            if (conf) {
                $.get("/upload/delTotal", {fileName: $(res).attr("step")}, function (result) {
                    if (result == "1") {
                        parents.remove();
                    }
                });
            }
        }
        function checkAlls(tag) {
            var check_size = document.getElementsByName("cs");
            if (tag) {
                for (var i = 0; i < check_size.length; i++) {
                    check_size[i].checked = true;
                }
            } else {
                for (var i = 0; i < check_size.length; i++) {
                    check_size[i].checked = false;
                }
            }
        }
        function getSum() {
            var check_size = document.getElementsByName("cs");
            var count = 0;
            var fileNames = "";
            for (var i = 0; i < check_size.length; i++) {
                if (check_size[i].checked == true) {
                    fileNames = fileNames + check_size[i].value + ",";
                    count++;
                }
            }
            if (count <= 0) {
                alert("请选择要统计的文件或者文件数量不能小于两个！");
            } else {
                fileNames = fileNames.substring(0, fileNames.length - 1);
                $.get("/upload/getSum", {fileNames: fileNames}, function (rs) {
                    alert(rs);
                });
            }
        }
        /*智能竞价中的alert提示*/
        var uploadTotalAlertPrompt = {
            show:function(content){
                $(".TB_overlayBG_alert").css({
                    display: "block", height: $(document).height()
                });/*蒙版显示*/
                $("#uploadTotalAlertPrompt").css({
                    left: ($("body").width() - $("#download").width()) / 2 - 20 + "px",
                    top: ($(window).height() - $("#download").height()) / 2 + $(window).scrollTop() + "px",
                    display: "block"
                });/*显示提示DIV*/
                $("#uploadTotalAlertPrompt_title").html(content);
            },
            hide:function(){
                $(".TB_overlayBG_alert").css({
                    display: "none"
                });/*蒙版显示*/
                $("#uploadTotalAlertPrompt").css({
                    display: "none"
                });/*显示提示DIV*/
            }
        }
    </script>

</head>
<body>
<input type="file" name="fileName" id="file_upload"/>
<a href="javascript:$('#file_upload').uploadify('upload', '*')">上传文件</a> | <a
        href="javascript:$('#file_upload').uploadify('stop')">停止上传!</a>|<a href="javascript:void(0)" onclick="getSum()">统计</a>

<div style="width:400px;height: 200px;overflow: auto;border: 1px solid lightgrey;">
    <table id="t1" border="1">
        <tr>
            <td><input type="checkbox" onchange="checkAlls(this)"></td>
            <td>文件名</td>
            <td>文件大小</td>
            <td>文件类型</td>
            <td>操作</td>
        <tr>
    </table>
</div>
<%--alert提示类--%>
<div class="box7" style=" width: 230px;display:none;z-index: 1005" id="uploadTotalAlertPrompt">
    <h2>
        <span class="fl alert_span_title" id="uploadTotalAlertPrompt_title"></span>
        <%--<a href="#" class="close">×</a></h2>--%>
    <%--<a href="#" onclick="uploadTotalAlertPrompt.hide()" style="color: #cccccc;float: right;font-size: 20px;font-weight: normal;opacity: inherit;text-shadow: none;">×</a></h2>--%>
    </h2>
    <div class="mainlist">
        <div class="w_list03">
            <ul class="zs_set">
                <li class="current" onclick="uploadTotalAlertPrompt.hide()">确认</li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
