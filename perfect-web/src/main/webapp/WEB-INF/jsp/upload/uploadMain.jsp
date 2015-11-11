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
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <script type="text/javascript" src="/public/plugs/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript">
        $(function () {
            loadFileList();
            $("#file_upload").uploadify({
                'buttonText': '请选择',
                'height': 30,
                'swf': '/public/plugs/uploadify/uploadify.swf',
                'uploader': '/upload/uploadFile?jsessionid=<%=request.getSession().getId()%>',
                'width': 120,
                'auto': false,
                'fileObjName': 'file',
                'onUploadSuccess': function (file, data, response) {
                    if (data == "1") {
//                        alert(file.name + "上传成功");
                        uploadMainAlertPrompt.show(file.name + "上传成功");

                    }
                }
            });
        });
        function loadFileList() {
            $("#t1").remove("tr:gt(0)");
            $.get("/upload/tmpList", function (result) {
                var json = eval("(" + result + ")");
                for (var i = 0; i < json.length; i++) {
                    var _tr = "<tr>";
                    var td = "<td>" + json[i].fileName + "</td><td>" + json[i].fileSize + "</td><td>" + json[i].fileExt + "</td><td><a href='/upload/getDefault?fileName=" + json[i].fileName + "'>下载</a>" +
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
                $.get("/upload/delDefault", {fileName: $(res).attr("step")}, function (result) {
                    if (result == "1") {
                        parents.remove();
                    }
                });
            }
        }
        /*智能竞价中的alert提示*/
        var uploadMainAlertPrompt = {
            show:function(content){
                $(".TB_overlayBG").css({
                    display: "block", height: $(document).height()
                });/*蒙版显示*/
                $("#uploadMainAlertPrompt").css({
                    left: ($("body").width() - $("#download").width()) / 2 - 20 + "px",
                    top: ($(window).height() - $("#download").height()) / 2 + $(window).scrollTop() + "px",
                    display: "block"
                });/*显示提示DIV*/
                $("#uploadMainAlertPrompt_title").html(content);
            },
            hide:function(){
                $(".TB_overlayBG").css({
                    display: "none"
                });/*蒙版显示*/
                $("#uploadMainAlertPrompt").css({
                    display: "none"
                });/*显示提示DIV*/
            }
        }
    </script>
</head>
<body>
<input type="file" name="fileName" id="file_upload"/>
<a href="javascript:$('#file_upload').uploadify('upload', '*')">上传文件</a> | <a
        href="javascript:$('#file_upload').uploadify('stop')">停止上传!</a>

<div style="width:400px;height: 200px;overflow: auto;border: 1px solid lightgrey;">
    <table id="t1" border="1">
        <tr>
            <td>文件名</td>
            <td>文件大小</td>
            <td>文件类型</td>
            <td>操作</td>
        </tr>
        <tr>
    </table>
</div>
<%--alert提示类--%>
<div class="box7" style=" width: 230px;display:none;z-index: 1001" id="uploadMainAlertPrompt">
    <h2>
        <span class="fl" id="uploadMainAlertPrompt_title"></span>
        <a href="#" class="close">×</a></h2>
    <div class="mainlist">
        <div class="w_list03">
            <ul class="zs_set">
                <li class="current" onclick="uploadMainAlertPrompt.hide()">确认</li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
