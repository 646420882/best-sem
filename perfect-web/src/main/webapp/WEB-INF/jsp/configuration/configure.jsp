<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>大数据智能营销</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/login/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css">--%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/login/login.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/media.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/tc.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/respond.js"></script>
    <style type="text/css">
        .tab_box {
            padding: 0px;
        }
        .box h2{
                   width:100%;
                   *width:90%;
               }
        .close:hover, .close:focus{
            color:#fff;
        }
        .login_input2 ul li{
            *height:38px;
        }
        .login_input2 ul li input{
            width:230px;
            *width:210px;
            *padding:0px;
            *line-height:30px;
        }
        .help-block{
            width:150px;
        }
        .col-lg-5{
            width:150px;
            height:34px;
            padding:0px;
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
                <a href="#">用户中心</a>&nbsp;&nbsp;&gt;&nbsp;&nbsp;<span>关联账户</span>
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
                                <td>&nbsp;<b>操作</b></td>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${accountList}">
                                <tr>
                                    <td>&nbsp;<span class="fl"><img
                                            src="${pageContext.request.contextPath}/public/images/c_logo.jpg"></span><b
                                            class="fl">${i.baiduUserName}</b></td>
                                    <td>&nbsp;<a href="${i.regDomain}">${i.regDomain}</a></td>
                                    <td>&nbsp;${i.token}</td>
                                    <td>&nbsp;<a href="#" class="showbox">同步密码</a> &nbsp; <a data-id="${i.id}"
                                                                                             class="delBtn">删除</a></td>
                                </tr>

                            </c:forEach>
                            <script type="application/javascript">
                                $('.delBtn').click(function () {
                                    var id = this.data('id');
                                    $.ajax({
                                        url: "/configuration/acc/" + id,
                                        type: "DELETE",
                                        success: function (datas) {
                                            if (datas.status != null && datas.status == true) {
                                                alert("删除成功!");
                                                window.location.reload(true);
                                            } else {
                                                alert("删除失败!")
                                            }
                                        }
                                    });
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
<div class="box" id="new_riginality" style="display:none; width:552px;">
    <h2 id="new_riginality2">同步baidu推广帐号密码-如果您更改了推广帐号密码，请立即同步密码。<a href="#" class="close">关闭</a></h2>

    <div class="mainlist2 over">
        <div class="mainlist">
            <ul>
                <li>
                    <p>请输入CubeSearch密码，以保证账户安全：</p>

                    <p><input type="text" class="c_input"></p>
                </li>
                <li>
                    <p>输入推广帐号新密码：</p>

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
        <div class="main_bottom">
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
        });

    });


</script>

</body>
</html>
