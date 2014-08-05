<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/8/4
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>批量上传</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/plugs/swfUpload/swfupload.js"></script>
    //upload上传对象
    <script type="text/javascript">
        var swfu;
        window.onload=function(){
            var settings_object = {//定义参数配置对象
                upload_url : "aaa123.do",
                flash_url : "${pageContext.request.contextPath}/public/plugs/swfUpload/swfupload.swf",
                file_post_name : "cvsFile",
                post_params : {
                    "post_param_name_1" : "post_param_value_1",
                    "post_param_name_2" : "post_param_value_2",
                    "post_param_name_n" : "post_param_value_n"
                },
                use_query_string : false,
                requeue_on_error : false,
                http_success : [201, 202],
                assume_success_timeout : 0,
                file_types : "*.jpg;*.gif",
                file_types_description: "Web Image Files",
                file_size_limit : "1024",
                file_upload_limit : 10,
                file_queue_limit : 2,
                debug : false,
                prevent_swf_caching : false,
                preserve_relative_urls : false,
                button_placeholder_id : "element_id",
                button_image_url : "${pageContext.request.contextPath}/public/plugs/swfUpload/images/XPButtonUploadText_61x22.png",
                button_width : 61,
                button_height : 22,
                button_text : "<b>Click</b> <span class='redText'>here</span>",
                button_text_style : ".redText { color: #FF0000; }",
                button_text_left_padding : 3,
                button_text_top_padding : 2,
                button_action : SWFUpload.BUTTON_ACTION.SELECT_FILES,
                button_disabled : false,
                button_cursor : SWFUpload.CURSOR.HAND,
                button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
                swfupload_loaded_handler : swfupload_loaded_function,
                file_dialog_start_handler : file_dialog_start_function,
                file_queued_handler : file_queued_function,
                file_queue_error_handler : file_queue_error_function,
                file_dialog_complete_handler : file_dialog_complete_function,
                upload_start_handler : upload_start_function,
                upload_progress_handler : upload_progress_function,
                upload_error_handler : upload_error_function,
                upload_success_handler : upload_success_function,
                upload_complete_handler : upload_complete_function,
                debug_handler : debug_function
            };
            swfu = new SWFUpload(settings_object);//实例化一个SWFUpload，传入参数配置对象

        }
        function swfupload_loaded_function(){}
        function file_dialog_start_function(){}
    </script>
</head>
<body>
test
</body>
</html>
