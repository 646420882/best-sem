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
<div id="background" class="background"></div>
<div id="progressBar" class="progressBar">正在更新词库, 请稍候...</div>
<div id="progressBar1" class="progressBar">loading...</div>
<jsp:include page="../homePage/pageBlock/backstage_nav.jsp"/>
<div class="backstage_concent mid over">
    <div class="backstage_title over">
        <span class="backstage_title_mid">词库添加</span>
    </div>
    <!-- 用于文件上传的表单元素 -->
    <div class="backstage_list over">
        <form id="fileForm" name="fileForm" method="post" class="form-inline" enctype="multipart/form-data"
              action="${pageContext.request.contextPath}/admin/lexicon/upload" target="fileIframe">
            <ul>
                <li><b class="fl">Upload File: </b><input type="file" name="excelFile"
                                                          style="border:none; width:160px;"/><input type="button"
                                                                                                    id="submitForm"
                                                                                                    class="btn sure"
                                                                                                    value="导入"/></li>
                <li></li>
            </ul>
        </form>
    </div>
    <div class="backstage_title over">
        <span class="backstage_title_mid">词库内容删除</span>
    </div>
    <div class="backstage_list over">
        <div class="k_top2_text1">
            <ul>
                <li><select id="trade">
                    <option selected="selected" value="">请选择行业</option>
                    <option value="电商">电商</option>
                    <option value="房产">房产</option>
                    <option value="教育">教育</option>
                    <option value="金融">金融</option>
                    <option value="旅游">旅游</option>
                </select></li>
                <li><select id="category">
                </select></li>
                <li><input type="button" class="sure" value="删除" onclick="deleteLexicon();"/></li>
            </ul>
        </div>
    </div>
</div>

<iframe id="fileIframe" name="fileIframe" style="display: none"></iframe>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="application/javascript">

    var _trade = "";
    var _category = "";

    var ajaxbg = $("#background,#progressBar1");
    ajaxbg.hide();
    $(document).ajaxStart(function () {
        ajaxbg.show();
    });
    $(document).ajaxStop(function () {
        ajaxbg.hide();
    });

    $(function () {
        document.getElementById("background").style.display = "none";
        document.getElementById("progressBar").style.display = "none";

        $("#submitForm").on('click', function () {
            var excelFile = $("input[name='excelFile']").val();
            if (excelFile == null || excelFile == "") {
                return false;
            }
            // 获取文件后缀名
            var pos = excelFile.replace(/.+\./, "");
            if (pos != "xlsx") {
                alert("请选择正确的  xlsx 文件！！");
                return;
            }
            $("#fileForm").submit();
            document.getElementById("background").style.display = "block";
            document.getElementById("progressBar").style.display = "block";
        });

        $("#trade").change(function () {
            var trade = $("#trade option:selected").val();
            _trade = trade;
            $.getJSON("/getKRWords/getCategories",
                    {trade: trade},
                    function (data) {
                        var category = "", datas = data.rows;
                        category += "<option value='' selected='selected'>全部类别</option>";
                        for (var i = 0, l = datas.length; i < l; i++) {
                            if (i == 0) {
                                category += "<option value='" + datas[i].category + "' selected='selected'>" + datas[i].category + "</option>";
                                _category = datas[i].category;
                                continue;
                            }
                            category += "<option value='" + datas[i].category + "'>" + datas[i].category + "</option>";
                        }
                        $("#category").empty();
                        $("#category").append(category);
                    });
        });

    });

    var callback = function (data) {
        if (data == "true") {
            document.getElementById("background").style.display = "none";
            document.getElementById("progressBar").style.display = "none";
            alert("更新成功!");
        } else {
            document.getElementById("background").style.display = "none";
            document.getElementById("progressBar").style.display = "none";
            alert("更新失败!");
        }
    };

    var deleteLexicon = function () {
        if ($('#trade option:selected').val().length == 0) {
            alert("请选择行业!");
            return false;
        }

        $.ajax({
            url: "/admin/lexicon/delete",
            type: "POST",
            dataType: "json",
            data: {
                "trade": $('#trade option:selected').val(),
                "category": $('#category option:selected').val()
            },
            success: function (data, statusText, jqXHR) {
                if (data.status) {
                    alert("删除成功!");
                }
            }
        });

    };

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
