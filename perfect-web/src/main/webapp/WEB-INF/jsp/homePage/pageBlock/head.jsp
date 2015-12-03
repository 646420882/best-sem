<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<div class="head_top">
    <div class="top_middle" id="top_middle">
        <div class="user_mid fr">
            <ul>
                <li> <div class="user_logo fl">
                    <div class="user_logo1">
                        <div class="user_img fl over">
                            <span id="head_click"><img id="user_img"
                                                       src="${pageContext.request.contextPath}/account/getImg"></span>
                        </div>
                        <div class="user_text fl">
                            <div class="user_top over">
                                <div class="fl"><b id="time"></b><a
                                        href="${pageContext.request.contextPath}/configuration/"><span>${currSystemUserName}</span></a>
                                </div>
                                <input type="image" onclick="downloadUser()"
                                       src="${pageContext.request.contextPath}/public/img/download.png"
                                       class="glyphicon-class" style="padding:0 3px;">

                                <div class="user_logo2 fl">
                                    <form name="logout" method="POST" action="${pageContext.request.contextPath}/logout">
                                        <button style="border: none;color: #777777;border:none;background: none;line-height: normal;"
                                                onclick="$('form[logout]').submit();">退出
                                        </button>

                                    </form>
                                </div>
                            </div>
                            <div class="user_select">
                                <span class="fl">百度账户:</span>
                                <div class="user_name">
                                    <span></span><img
                                        src="${pageContext.request.contextPath}/public/img/username_select.png">
                                </div>
                                <div id="switchAccount" class="user_names over hides">
                                    <input type="text" placeholder="请输入关键词..." id="searchCount"
                                           class="switchAccountSerach ">

                                    <div class="countname">
                                        <ul id="switchAccount_ul" class="switchAccount_ul">

                                        </ul>
                                    </div>
                                    <div id="switchAccount_ul_pages" class="switchAccount_ul_pages">
                                        <div class="page_ul">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div></li>
                <li>
                    <div class="user_detali fl over" style="min-width:300px;">
                        <ul>
                            <li>推广额度：<b><a href="#">${accountBalance}</a></b> 元<a href="/pftstis/getIndex"
                                                                                  style="color: white">.</a></li>
                            <li><span>余额预计可消费：${remainderDays}天</span><span>日预算：${accountBudget}元</span></li>
                        </ul>
                    </div>
                </li>
            </ul>


        </div>

        <div class="top_mid fl over" <%--id="argDialogDiv"--%>>

            <div class="logo">
                <a href="http://best-ad.cn/" target="_blank"><img
                        src="${pageContext.request.contextPath}/public/img/logo.png"></a>
            </div>
        </div>
    </div>
</div>
<%--</div>--%>
<%--用户头像修改--%>
<div class="TB_overlayBG"></div>
<div class="TB_overlayBG_alert"></div>
<div class="box" style="display:none; width:400px;" id="head_img">
    <h2 id="head_top">
        <span class="fl">修改头像</span>
        <a href="#" class="close2 fr" style="color:#fff;">×</a></h2>

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
<%--alert提示类--%>
<div class="box alertBox" style=" width: 230px;display:none;z-index: 10001" id="AlertPrompt">
    <h2 id="AlertPrompTitle">
        <span class="fl alert_span_title" id="AlertPrompt_title"></span>
        <%-- <a href="#" onclick="AlertPrompt.hide()" style="color: #cccccc;float: right;font-size: 20px;font-weight: normal;opacity: inherit;text-shadow: none;">×</a>--%>
    </h2>

    <div class="mainlist">
        <div class="w_list03">
            <ul class="zs_set">
                <li class="current" onclick="AlertPrompt.hide()">确认</li>
            </ul>
        </div>
    </div>
</div>
<%--内容提示提示类--%>
<div class="box" style=" width: 230px;display:none;z-index: 10001" id="PromptBox">
    <h2 id="PrompTitleBox">
        <span class="fl" id="PrompBoxTitle"></span>
        <a href="javascript:void(0)" onclick="PromptBox.hide();" class="close">×</a></h2>

    <div class="mainlist">
        <div id="PrompMain">
        </div>
        <div class="w_list03">
            <ul class="zs_set">
                <li class="current" onclick="PromptBox.hide()">确认</li>
                <li onclick="PromptBox.hide()">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--数据报告中搜索词快速添加--%>
<div class="box" id="reportAddSearchWord" style="display: none;width: 550px;">
    <h2 id="reportAddSearchWordTitile">
        <span class="fl">添加为关键词</span>
        <a href="javascript:void(0)" class="close">×</a>
    </h2>

    <div class="mainlist">
        <label>您要添加的关键词是：</label>

        <div id="choicedKeyWord">
            <h3><strong>百思克</strong></h3>

            <p>所属推广计划：<span>品牌计划</span></p>

            <p>所属推广单元：<span>百思产品</span><span class="choicedKeyWordCancel" onclick="cancelKeyWord()">取消</span></p>
        </div>

        <div class="mainChoiceList">
            <div>
                <label>添加到推广计划：</label>
                <select>
                    <option value="所属推广家户">所属推广家户</option>
                    <option value="test">test</option>
                    <option value="通用词">通用词</option>
                </select>
            </div>
            <div>
                <label>添加到推广单元：</label>
                <select>
                    <option value="所属推广单元">所属推广单元</option>
                    <option value="test">test</option>
                    <option value="通用词">通用词</option>
                </select>
            </div>
            <div>
                <label>匹配模式：</label>
                <select>
                    <option value="所属推广单元">广泛</option>
                    <option value="test">test</option>
                    <option value="通用词">通用词</option>
                </select>
            </div>
        </div>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <%--<li class="current" id="confirmAddKeyWord">确认</li>--%>
                <li class="current" style="padding: 0"><input id="confirmAddKeyWord" value="确认" type="button" style="border: none;background: #01aef0;width: 64px;"></li>
                <li>取消</li>
            </ul>
        </div>
    </div>
</div>
<%--数据报告中添加否定关键词--%>
<div class="box" id="reportAddNoSearchWord" style="display: none;width: 550px;">
    <h2 id="reportAddNoSearchWordTitile">
        <span class="fl">添加为关键词</span>
        <a href="javascript:void(0)" class="close">×</a>
    </h2>

    <div class="mainlist">
        <p>你要添加为否定关键词的有<span style="color: red">1</span>个</p>
        <p style="margin: 5px 0 15px 0">以下搜索词搜索到后将不触发你的搜索结果</p>

        <div id="choicedNoKeyWord">
            <h3><strong>百思克</strong></h3>

            <p>所属推广计划：<span>品牌计划</span></p>

            <p>所属推广单元：<span>百思产品</span><span class="choicedKeyWordCancel" onclick="cancelKeyWord()">取消</span></p>
        </div>

        <div class="mainChoiceList">
            <div class="fl"><label>添加到：</label></div>
            <div class="fl">
                <div>
                    <input id="promotionPlanLevel" type="radio" name="promotionLevel" onclick="promotionPlanLevel()" checked/>
                    <label for="promotionPlanLevel" onclick="promotionPlanLevel()">推广计划层级</label>
                    <div style="margin: 5px 0 0 17px;" id="PlanLevel">
                        <label>推广计划：</label>
                        <select style="width: 180px">
                            <option>所属推广计划</option>
                            <option>test</option>
                            <option>通用词</option>
                            <option>品牌计划</option>
                        </select>
                    </div>
                </div>
                <div style="margin: 5px 0 0 0">
                    <input id="promotionUnitLevel" type="radio" name="promotionLevel"  onclick="promotionUnitLevel()"/>
                    <label for="promotionUnitLevel" onclick="promotionUnitLevel()">推广单元层级</label>
                    <div id="UnitLevel" class="hides">
                        <div style="margin: 5px 0 0 17px;">
                            <label>推广计划：</label>
                            <select style="width: 180px">
                                <option>所属推广计划</option>
                                <option>test</option>
                                <option>通用词</option>
                                <option>品牌计划</option>
                            </select>
                        </div>
                        <div style="margin: 5px 0 0 17px;">
                            <label>推广单元：</label>
                            <select style="width: 180px">
                                <option>所属推广计划</option>
                                <option>test</option>
                                <option>通用词</option>
                                <option>品牌计划</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div style="margin-top: 15px;">
            <label>匹配模式：</label>
            <select style="width: 180px">
                <option value="精准否定关键词">精准否定关键词</option>
                <option value="否定关键词">否定关键词</option>
            </select>
        </div>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <%--<li class="current" id="confirmAddKeyWord">确认</li>--%>
                <li class="current" style="padding: 0"><input id="confirmAddNoKeyWord" value="确认" type="button" style="border: none;background: #01aef0;width: 64px;"></li>
                <li>取消</li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/login/userimg.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/tc.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/dialog.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/dialog-plus.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui-dialog.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/main.css">

<%--<style> .ui-dialog-body {--%>
<%--background-color: #f8f8f8;--%>
<%--}</style>--%>
<script type="text/javascript">

    <!--
    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
    }
    function promotionUnitLevel(){
        $("#UnitLevel").show();
        $("#PlanLevel").hide();
    }
    function promotionPlanLevel() {
        $("#PlanLevel").show();
        $("#UnitLevel").hide();
    }
    function onCheck(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                nodes = zTree.getCheckedNodes(true),
                v = "";
        for (var i = 0, l = nodes.length; i < l; i++) {
            v += nodes[i].name + ",";
        }
        if (v.length > 0) v = v.substring(0, v.length - 1);
        var cityObj = $("#citySel");
        cityObj.attr("value", v);
    }
    $(function () {
        window.dialog = dialog;
    });
    function downloadUser() {
        var d = top.dialog({
            id: 'my2',
            content: "<iframe src='/homePage/showCountDownload' width='540' height='340' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
            title: "账户下载",
            yesClose: "取消",
            skin: 'downPopup',
            time: 1000,
//            className:'succeed noClose',

//            url:'homePage/pageBlock/showcountDownload',

//            content: "<iframe src='/assistantKeyword/showTimingPauseDialo+g' width='550'  height='300' marginwidth='200' marginheight='0' scrolling='no' frameborder='0'></iframe>",
//            content: "<div style='width: 550px; height: 470px;'><span>选择账户</span>  <div class='j_list01 over'><ul id='treeDemo' class='ztree'></ul></div></div>",
            oniframeload: function () {
            },
            onclose: function () {

            },
            onremove: function () {
            }
        }).showModal(dockObj);

        console.log(top.dialog());
    }

    var dockObj = document.getElementById('argDialogDiv');
    $("#downloadUser").click(function () {
        downloadUser();
    });
    function cancelKeyWord() {
        $("#choicedKeyWord").css({'display':"none"});
        $("#confirmAddKeyWord").attr({'disabled':'disabled'});
        $("#confirmAddKeyWord").css({"background":"#ccc","cursor":'not-allowed'})
    }
    function imageChange(obj) {
        var fileFormat = "jpg,jpeg,png,gif,bmp";
        var path = $(obj).val();
        var index = $(obj).attr('fileindex');
        var fileName = getFileName(path);
        var fileExtLowerCase = (/[.]/.exec(fileName)) ? /[^.]+$/.exec(fileName.toLowerCase()) : '';//文件后缀
        if (fileFormat.indexOf(fileExtLowerCase) >= 0) {
            ShowImage(obj, index, 72, 72);
        } else {
//            alert('请选择图片,格式（*.jpg|*.jpeg|*.png|*.gif|*.bmp）');
            AlertPrompt.show('请选择图片,格式（*.jpg|*.jpeg|*.png|*.gif|*.bmp）');
            $(obj).val('');
//            alert($("#imgHeadPhoto" + index).get(0).src);
            AlertPrompt.show($("#imgHeadPhoto" + index).get(0).src);
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
//            alert("上传成功!");
            AlertPrompt.show("上传成功!");
            window.location.reload(true);
        }
        else
//            alert("上传失败!");
            AlertPrompt.show("上传失败!");
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
    /*智能竞价中的alert提示*/
    var AlertPrompt = {
        show: function (content) {
            $("#AlertPrompt_title").html(content);
            $(".TB_overlayBG_alert").css({
                display: "block", height: $(document).height()
            });
            /*蒙版显示*/
            $("#AlertPrompt").css({
                top: ($(window).height() - $("#AlertPrompt").height()) / 2 + $(window).scrollTop() + "px",
                left: ($("body").width() - $("#AlertPrompt").width()) / 2 - 20 + "px",
                display: "block"
            });
            /*显示提示DIV*/
        },
        hide: function () {
            $(".TB_overlayBG_alert").css({
                display: "none"
            });
            /*蒙版显示*/
            $("#AlertPrompt").css({
                display: "none"
            });
            /*显示提示DIV*/
        }
    }
    /*智能竞价中的alert提示*/
    var PromptBox = {
        show: function (content) {
            $("#PrompBoxTitle").html(content);
            $("#PrompMain").html(content);
            $(".TB_overlayBG_alert").css({
                display: "block", height: $(document).height()
            });
            /*蒙版显示*/
            $("#PromptBox").css({
                top: ($(window).height() - $("#PromptBox").height()) / 2 + $(window).scrollTop() + "px",
                left: ($("body").width() - $("#PromptBox").width()) / 2 - 20 + "px",
                display: "block"
            });
            /*显示提示DIV*/
        },
        hide: function () {
            $(".TB_overlayBG_alert").css({
                display: "none"
            });
            /*蒙版显示*/
            $("#PromptBox").css({
                display: "none"
            });
            /*显示提示DIV*/
        }
    }
    $(function () {
        rDrag.init(document.getElementById('head_top'));
        rDrag.init(document.getElementById('AlertPrompTitle'));
        rDrag.init(document.getElementById('findOrReplaceH'));
        rDrag.init(document.getElementById('changeCreative'));
        rDrag.init(document.getElementById('excludeIPChange'));
        rDrag.init(document.getElementById('budgetChange'));
        rDrag.init(document.getElementById('batchDel'));
        rDrag.init(document.getElementById('reportAddSearchWordTitile'));
        rDrag.init(document.getElementById('reportAddNoSearchWordTitile'));
    });
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

    /***account 账户弹框分页*****/
    $(document).ready(function () {
        $.getJSON("/account/getAllBaiduAccount",
                {},
                function (data) {
//                    var tags = [];
//                    $.each(results, function (i, item) {
//                        var _item = item.baiduRemarkName;
//                        console.log(_item)
//                        tags.push(_item);
//                    });
                    var a = data.rows.length;
                    var b = 6;
                    var c = Math.ceil(a / b);
                    if (a == 1) {
                        $("#switchAccount_ul_pages").hide()
                    } else {
                        for (var i = 1; i <= c; i++) {
                            if (i == 1) {
                                $("<div class='page_li page_li_hover '>" + i + "</div>").appendTo($('.page_ul'));
                            } else {
                                $("<div class='page_li '>" + i + "</div>").appendTo($('.page_ul'));
                            }
                        }
                    }

                    var g = $('.switchAccount_ul li');
                    g.hide();
                    g.slice(0, b).show();
                    $('.page_li ').click(function () {
                        g.hide();
                        $('.page_li').removeClass("page_li_hover");
                        $(this).addClass("page_li_hover");
                        var e = $(this).text() * b;
                        g.slice(e - b, e).show();
                    })

                    //搜索
                    var results = data.rows;
                    var tags = [];
                    if (results != null && results.length > 0) {
                        var index = results[0].baiduUserName.length;
                        $.each(results, function (i, item) {
                            var _item = item.baiduRemarkName;
                            if (_item == undefined) _item = item.baiduUserName.substring(0, (i > 0 ? index - 3 : index)) + (item.baiduUserName.length > index ? "..." : "");
                            tags.push({label: _item, id: item.id});

                        });
                    }

                    $("#searchCount").autocomplete({
                        source: tags,

                        select: function (event, ui) {
                            // 这里的this指向当前输入框的DOM元素
                            // event参数是事件对象
                            // ui对象只有一个item属性，对应数据源中被选中的对象
                            $(this).value = ui.item.label;
                            $("#searchCount").val(ui.item.label);
                            $("#searchCount").attr('card', ui.item.id);
                            $('.user_name span').html(ui.item.label);
                            var _accountId = ui.item.id;
                            $('#switchAccount').hide();
                            $.ajax({
                                url: '/account/switchAccount',
                                type: 'POST',
                                async: false,
                                dataType: 'json',
                                data: {
                                    "accountId": _accountId
                                },
                                success: function (data, textStatus, jqXHR) {
                                    if (data.status != null && data.status == true) {
                                        //location.replace(location.href);
                                        window.location.reload(true);
                                    }
                                }
                            })

                            // 必须阻止默认行为，因为autocomplete默认会把ui.item.value设为输入框的value值
                            event.preventDefault();

                        }

                    });
                    $('#searchCount').bind('input propertychange', function () {
                        setTimeout(function () {
                            if ($("#searchCount").val() == "") {
                                $("#searchCount").siblings().show()
                                $("#switchAccount").height($(".countname").height() + 44);
//                            $("#switchAccount").height($(window).height());
                            } else {
                                $("#searchCount").siblings().hide()
                                $("#switchAccount").height($(".ui-autocomplete").height() + 44);
                            }
                        }, 1000);
                    });

                })
    });

</script>


