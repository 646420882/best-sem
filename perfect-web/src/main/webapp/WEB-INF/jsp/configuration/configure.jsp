<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>大数据智能营销</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/login/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/login/login.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/media.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/tc.min.js"></script>
    <script type="text/javascript" src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <style type="text/css">
        .tab_box {
            padding: 0px;
        }

        .box h2 {
            width: 100%;
            *width: 90%;
        }

        .close:hover, .close:focus {
            color: #fff;
        }

        .login_input2 ul li {
            *height: 38px;
        }

        .login_input2 ul li input {
            width: 230px;
            *width: 210px;
            *padding: 0px;
            *line-height: 30px;
        }

        .help-block {
            width: 150px;
        }

        .col-lg-5 {
            width: 150px;
            height: 34px;
            padding: 0px;
        }

    </style>
</head>
<body>
<jsp:include page="../homePage/pageBlock/head.jsp"/>
<div class="concent over">
    <jsp:include page="../homePage/pageBlock/nav.jsp"/>
    <div class="mid over">
        <div class="title_box">
            <div class="on_title over">
                <a href="#">用户中心</a>&nbsp;&nbsp;&gt;&nbsp;&nbsp;<span id="TitleName">关联账户</span>
            </div>
        </div>
        <div id="tab">
            <ul class="tab_menu">
                <li class="selected">
                    关联账户
                </li>
                <li id="showPwdLi">
                    修改密码
                </li>
            </ul>
            <div class="tab_box">
                <div class="containers">
                    <div class="configure over">
                        <div class="configure_top over">
                            <h3 class="fl">关联账户</h3>
                            <a href="add" class="fr"> + 添加推广账户</a>
                        </div>
                        <div class="configure_under over">
                            <table width="100%" cellspacing="0" border="1">
                                <thead>
                                <tr>
                                    <td>&nbsp;<b>已绑定推广账户</b></td>
                                    <td>&nbsp;<b>推广URL</b></td>
                                    <td>&nbsp;<b>Token</b></td>
                                    <td>&nbsp;<b>备注名(双击更改)</b></td>
                                    <td>&nbsp;<b>操作</b></td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="i" items="${accountList}">
                                    <tr>
                                        <td>&nbsp;<span class="fl"><img
                                                src="${pageContext.request.contextPath}/public/images/c_logo.jpg"></span><b
                                                class="fl">${i.accountName}</b></td>
                                        <%--<td>&nbsp;<a href="${i.regDomain}">${i.regDomain}</a></td>--%>
                                        <td>&nbsp;<a href=""></a></td>
                                        <td>&nbsp;${i.token}</td>
                                        <td>&nbsp;<input type="text" id="updateInput" data-baidu-id="${i.accountId}"
                                                         style="border: none" remarkName="${i.remarkName}"
                                                         value="${i.remarkName}" width="10px" readonly></td>
                                        <td>&nbsp;<a href="#" class="showbox">同步密码</a> &nbsp; <a data-id="${i.id}"
                                                                                                 data-userName="${currSystemUserName}"
                                                                                                 class="delBtn"
                                                                                                 style="cursor: pointer">删除</a>
                                        </td>
                                    </tr>

                                </c:forEach>
                                <script type="application/javascript">
                                    $("#updateInput").dblclick(function () {
                                        if ($("#updateInput").attr("readonly") == "readonly") {
                                            $("#updateInput").attr("style", "border: none");
                                            $("#updateInput").removeAttr("readonly");
                                        }
                                    });
                                    $("#updateInput").blur(function () {
                                        if ($("#updateInput").attr("readonly") != "readonly") {
                                            if ($(this).val() != "") {
                                                $.ajax({
                                                    url: "/configuration/acc/upBaiduName",
                                                    type: "GET",
                                                    dataType: "json",
                                                    data: {
                                                        id: $(this).attr("data-baidu-id"),
                                                        name: $(this).val()
                                                    }
                                                })
                                            } else {
                                                $(this).val($(this).attr("remarkName"));
//                                            alert("备注名不能为空！！")
                                                AlertPrompt.show("备注名不能为空!")
                                            }

                                        }
                                        $("#updateInput").attr("style", "border: none");
                                        $("#updateInput").attr("readonly", "readonly");
                                    });
                                    $('.delBtn').click(function () {
                                        var id = $(this).attr('data-id');
                                        var userName = $(this).attr('data-userName');
                                        if (confirm("是否确定删除此账号")) {
                                            $.ajax({
                                                url: "/configuration/acc/deletebdUser",
                                                type: "GET",
                                                dataType: "json",
                                                data: {
                                                    id: id,
                                                    account: userName
                                                },
                                                success: function (datas) {
                                                    if (datas.status != null && datas.status == true) {
//                                                    alert("删除成功!请重新登陆你的账号");
                                                        AlertPrompt.show("删除成功!请重新登陆你的账号!")
                                                        window.location.reload(true);
                                                    } else {
//                                                    alert("删除失败!")
                                                        AlertPrompt.show("删除失败!")
                                                    }
                                                }
                                            });
                                        } else {
                                            return false;
                                        }

                                    })
                                </script>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="containers hides" id="showPwdDiv">
                    <jsp:include page="../configuration/changePwd.jsp"/>
                </div>
            </div>
            <jsp:include page="../homePage/pageBlock/footer.jsp"/>
        </div>
    </div>
</div>
<div class="TB_overlayBG"></div>
<div class="TB_overlayBG_alert"></div>
<div class="box" id="new_riginality" style="display:none; width:452px;">
    <h2 id="new_riginality2">同步密码<a href="#" class="close">×</a></h2>

    <div class="mainlist2 over" style="width:452px;">
        <div class="mainlist">
            <ul>
                <li><p style="color:red;">注：同步baidu推广账号密码-如果您更改了推广账号密码，请立即同步密码。</p></li>
                <li>
                    <p>请输入CubeSearch密码，以保证账户安全：</p>

                    <p><input type="text" class="c_input"></p>
                </li>
                <li>
                    <p>输入推广账号新密码：</p>

                    <p><input type="text" class="c_input"></p>
                </li>
                <li>
                    <p>输入推广URL：</p>

                    <p><input type="text" class="c_input"></p>
                </li>
                <li>
                    <p>输入推广账户Tolen：</p>

                    <p><input type="text" class="c_input"></p>
                </li>
            </ul>
        </div>
        <div class="main_bottom" style="margin:0px;">
            <div class="w_list03">
                <ul>
                    <li class="current">确认</li>
                </ul>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    window.onload = function () {
        rDrag.init(document.getElementById('new_riginality2'));
    };
    $(function () {
        $(".showbox").click(function () {
            $(".TB_overlayBG").css({
                display: "block", height: $(document).height()
            });
            $("#new_riginality").css({
                left: ($("body").width() - $("#new_riginality").width()) / 2 - 20 + "px",
                top: ($(window).height() - $("#new_riginality").height()) / 2 + $(window).scrollTop() + "px",
                display: "block"
            });
        });
        $(".close").click(function () {
            $(".TB_overlayBG").css("display", "none");
            $(".box").css("display", "none");
        });

    });
    $(function () {
        var $tab_li = $('.tab_menu li');
        $('.tab_menu li').click(function () {
            $(this).addClass('selected').siblings().removeClass('selected');
            var index = $tab_li.index(this);
            $('div.tab_box > div').eq(index).show().siblings().hide();
            if (index == 0) {
                $("#TitleName").html("关联账户");
            } else {
                $("#TitleName").html("修改密码");
            }
        });

    });


</script>
</body>
</html>
