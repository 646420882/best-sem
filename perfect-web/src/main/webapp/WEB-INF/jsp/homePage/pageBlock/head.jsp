<%--
  Created by IntelliJ IDEA.
  User: SubDong
  Date: 2014/8/11
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%--<div class="top_heade">--%>
<div class="top">
    <div class="top_middle">
<%--        <div class="top_mid fl over">
            <div class="logo fl">
                <a href="http://best-ad.cn/" target="_blank"><img
                        src="${pageContext.request.contextPath}/public/img/new_logo_02.png"></a>
            </div>
        </div>--%>
        <div class="user_mid fl">
            <div class="user_logo fl">
                <div class="user_logo1">
                    <div class="user_img fl over">
                            <span id="head_click">
                                <img id="user_img" src="${pageContext.request.contextPath}/account/getImg"
                                     class="img-circle"></span>
                    </div>
                    <div class="user_text fl">
                        <div class="user_top over">
                            <div class="fl"><b id="time"></b><a
                                    href="${pageContext.request.contextPath}/configuration/"><span>${currSystemUserName}</span></a>
                            </div>
                        </div>
                        <div class="user_select">
                            <div class="user_name">
                                <span></span>
                            </div>
                            <div id="switchAccount" class="user_names over hides">
                                <ul id="switchAccount_ul">
                                </ul>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div class="login_out fl">
                <form name="logout" method="POST" action="${pageContext.request.contextPath}/logout">
                    <input type="image" src="${pageContext.request.contextPath}/public/img/Sign_out.png"
                           onclick="$('form[logout]').submit();"/>
                </form>

            </div>
                <div class="user_detali fl over" id="argDialogDiv" style="min-width:300px;">

                    <ul>
                        <li>推广额度：<b><a href="#">${accountBalance}</a></b> 元<a href="/pftstis/getIndex"
                                                                               style="color: white">.</a></li>
                        <li><span>余额预计可消费：${remainderDays}天</span><span>日预算：${accountBudget}元</span></li>
                    </ul>

                </div>
            </div>
    <div class="logo fr">
        <a href="http://best-ad.cn/" target="_blank"><img src="${pageContext.request.contextPath}/public/img/logo.png"></a>
    </div>
       </div>

        </div>

    </div>
</div>
<%--</div>--%>
<%--用户头像修改--%>
<div class="TB_overlayBG"></div>
<div class="box" style="display:none; width:400px;" id="head_img">
    <h2 id="head_top">
        <span class="fl">修改头像</span>
        <a href="#" class="close2 fr" style="color:#fff;">关闭</a></h2>

    <div class="mainlist">
        <form id="userImg" name="userImg" action="${pageContext.request.contextPath}/account/uploadImg" method="post"
              enctype="multipart/form-data" target="fileIframe">
            <div id="divPreview1" class="user_photo">
                <img id="imgHeadPhoto1" src="${pageContext.request.contextPath}/public/img/user_img.png"
                     height="72" width="72" alt="照片预览"/>
            </div>
            <div class="user_photo">图片像素为：72*72</div>
            <div class="user_photo">图片支持jpg , jpeg , png, gif , bmp格式</div>
            <div id="divNewPreview1"
                 style="filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image); border: solid 1px #d2e2e2; display: none; "></div>
            <input id="userImgFile" name="userImgFile" class="input_200" type="file" fileindex="1"
                   onchange="imageChange(this)"/>
        </form>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li onclick="uploadUserImg();" class="current"> 确定</li>
                <li class="close2">取消</li>
            </ul>
        </div>
    </div>
</div>
<iframe id="fileIframe" name="fileIframe" style="display: none"></iframe>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/login/userimg.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/tc.min.js"></script>
<script type="text/javascript">
    <!--
    function imageChange(obj) {
        var fileFormat = "jpg,jpeg,png,gif,bmp";
        var path = $(obj).val();
        var index = $(obj).attr('fileindex');
        var fileName = getFileName(path);
        var fileExtLowerCase = (/[.]/.exec(fileName)) ? /[^.]+$/.exec(fileName.toLowerCase()) : '';//文件后缀
        if (fileFormat.indexOf(fileExtLowerCase) >= 0) {
            ShowImage(obj, index, 72, 72);
        } else {
            alert('请选择图片,格式（*.jpg|*.jpeg|*.png|*.gif|*.bmp）');
            $(obj).val('');
            alert($("#imgHeadPhoto" + index).get(0).src);
            $("#imgHeadPhoto" + index).get(0).src = '';
        }
    }

    function getFileName(obj) {
        var pos = obj.lastIndexOf("\\") * 1;
        return obj.substring(pos + 1);
    }
    //-->

    var uploadUserImg = function () {
        document.getElementById("userImg").submit();
    };

    var basePath = "<%=basePath%>";

    var callback = function (data) {
        if (data == "true") {
            $(".TB_overlayBG").css("display", "none");
            $("#head_img").css("display", "none");
            alert("上传成功!");
            window.location.reload(true);
        }
        else
            alert("上传失败!");
    };
</script>
<script type="text/javascript">
    //时间提示
    var now = new Date(), hour = now.getHours();
    var time = document.getElementById('time');
    if (0 < hour && hour < 6) {
        time.innerHTML = "凌晨,好！"
    }
    else if (hour < 9) {
        time.innerHTML = "早上,好！"
    }
    else if (hour < 12) {
        time.innerHTML = "上午,好！"
    }
    else if (hour < 14) {
        time.innerHTML = "中午,好！"
    }
    else if (hour < 18) {
        time.innerHTML = "下午,好！"
    }
    else if (hour < 23) {
        time.innerHTML = "晚上,好！"
    }
    else if (hour == 24) {
        time.innerHTML = "凌晨,好！"
    }
    else {
        time.innerHTML = "晚上,好！"
    }
    //弹窗
    window.onload = function () {
        rDrag.init(document.getElementById('head_top'));
    };
    $(function () {
        $("#head_click").click(function () {
            $(".TB_overlayBG").css({
                display: "block", height: $(document).height()
            });
            $("#head_img").css({
                left: ($("body").width() - $("#head_img").width()) / 2 - 20 + "px",
                top: ($(window).height() - $("#head_img").height()) / 2 + ($(window).scrollTop() - 153) + "px",
                display: "block"
            });
        });
        $(".close2").click(function () {
            $(".TB_overlayBG").css("display", "none");
            $("#head_img").css("display", "none");
        });
    });
</script>


