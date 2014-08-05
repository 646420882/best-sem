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
    <link type="text/css" rel="stylesheet" href="${path}/public/plugs/swfUpload/css/default.css">
    <script type="text/javascript" src="${path}/public/plugs/swfUpload/swfupload.js"></script>
    <script type="text/javascript" src="${path}/public/plugs/swfUpload/swfupload.queue.js"></script>
    <script type="text/javascript" src="${path}/public/plugs/swfUpload/handlers.js"></script>
    <script type="text/javascript" src="${path}/public/plugs/swfUpload/fileprogress.js"></script>
    <%--upload上传对象--%>
    <script type="text/javascript">
        var swfu;
        window.onload = function () {
            var settings_object = {//定义参数配置对象
                /**提交的路径**/
                upload_url: "aaa123.do",
                /**Flash Settings**/
                flash_url: "${path}/public/plugs/swfUpload/swfupload.swf",
                file_post_name: "cvsFile",
                /**自定义提交额外的参数**/
                post_params: {
                    "name": "aa123"
                },
                use_query_string: false,
                requeue_on_error: false,
                http_success: [201, 202],
                assume_success_timeout: 0,
                /**文件的处理**/
                file_types: "*",
                file_types_description: "All Files",
                file_size_limit: "100 MB", //100MB
                file_upload_limit: 10,//批量上传的最大文件数
                file_queue_limit: 2,//上传队列的数量
                debug: false,
                prevent_swf_caching: true,//上传时是否加随机数防止缓存
                preserve_relative_urls: false,//转换swfupload.swf为绝对地址，以达到更好的兼容性
                /**按钮的处理**/
                button_image_url: "${path}/public/plugs/swfUpload/images/XPButtonUploadText_61x22.png",
                button_placeholder_id: "spanButtonPlaceHolder",//指定一个id，该元素在swfupload实例化后会被Flash按钮取代，这个元素相当于一个占位符
                button_width: 100,
                button_height: 26,
                button_text: " <span class='redText'></span>",
                button_text_style: ".redText { color: #FF0000; }",
                button_text_left_padding: 3,
                button_text_top_padding: 2,
                button_action: SWFUpload.BUTTON_ACTION.SELECT_FILES,
                button_disabled: false,
                button_cursor: SWFUpload.CURSOR.HAND,
                button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
                /**事件的处理**/
                swfupload_loaded_handler: swfupload_loaded_function,
                file_dialog_start_handler: swfupload_loaded_function,
                file_queued_handler: fileQueued,
                file_queue_error_handler: null,
                file_dialog_complete_handler: null,
                upload_start_handler: upload_start_function,
                upload_progress_handler: null,
                upload_error_handler: null,
                upload_success_handler: null,
                upload_complete_handler: null,
                debug_handler: null,
                custom_settings: {
                    progressTarget: "fsUploadProgress",
                    cancelButtonId: "btnCancel"
                }
            };
            swfu = new SWFUpload(settings_object);//实例化一个SWFUpload，传入参数配置对象

        }
        function swfupload_loaded_function() {
        }
        function file_dialog_start_function() {
        }
        function file_queued_function(){
            alert("1");
        }
        function upload_start_function(){

        }
    </script>
</head>
<body>
<div id="content">
    <form action="aaa123.do" method="post" enctype="multipart/form-data">
        <div class="fieldset flash" id="fsUploadProgress">
            <span class="legend">Upload Queue</span>
        </div>
        <div id="divStatus">0 Files Uploaded</div>
        <div>
            <span id="spanButtonPlaceHolder"></span>
            <input id="btnCancel" type="button" value="Cancel All Uploads" onclick="swfu.cancelQueue();"
                   disabled="disabled" style="margin-left: 2px; font-size: 8pt; height: 29px;"/>
        </div>
    </form>
</div>
</body>
</html>
