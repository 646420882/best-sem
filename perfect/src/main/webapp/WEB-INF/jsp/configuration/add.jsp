<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>大数据智能营销</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/media.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/login.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/respond.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.pin.js"></script>
    <script type="text/javascript">
        $(function () {
            var $ = function (ID) {
                        return document.getElementById(ID);
                    },
                    oTab = $("stepTable"),
                    oTd = oTab.getElementsByTagName("TD");
            for (var i = 0; i < oTd.length; i++) {
                (function (i) {
                    oTd[i].onclick = function () {
                        for (var j = 0; j < i; j++) {
                            oTd[j].className = "clicked";
                        }
                        for (var j = oTd.length - 1; j > i; j--) {
                            oTd[j].className = "";
                        }
                        this.className = "current";
                    }
                })(i);
            }
        });
    </script>

</head>
<body>
<jsp:include page="../homePage/pageBlock/head.jsp"/>
<div class="concent over">
  <jsp:include page="../homePage/pageBlock/nav.jsp"/>
    <div class="mid over">
        <div class="title_box">
            <div class="on_title over">
                <a href="#">用户中心</a>&nbsp;&nbsp;&gt;&nbsp;&nbsp;<span>添加推广帐号</span>
            </div>
        </div>
        <div id="tab">
            <div class="configure over">
                <div class="configure_top over">
                    <h3 class="fl">添加推广帐号</h3>
                    <a href="../configuration/"  class="fr"> → 返回管理</a>
                </div>
                <div class="configure_under2 over">
                    <table id="stepTable" class="step_table" border="0" cellpadding="0" cellspacing="0" width="100%">
                        <tbody>
                        <tr>
                            <td class="current">
                                <div class="con"><span>1</span>选择搜索引擎</div>
                            </td>
                            <td>
                                <div class="tri">
                                    <div class="con"><span>2</span>验证推广帐号绑定</div>
                                </div>
                            </td>
                            <td>
                                <div class="tri">
                                    <div class="con"><span>3</span>完成绑定</div>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                </div>

                <div id="step1">
                    <input name="source" type="radio"/> <img
                        src="${pageContext.request.contextPath}/public/images/bdlogo.png"/>

                </div>

                <div id="step2">
                    <form name="frm" method="post" action="/configuration/save">
                       <ul>
                           <li><div class="add_list01 fl">百度用户名: </div><input type="text" name="username"></li>
                           <li><div class="add_list01 fl">百度密码: </div><input type="password" name="password"></li>
                           <li><div class="add_list01 fl">再次确认百度密码:</div><input type="password" name="password1"></li>
                           <li><div class="add_list01 fl">  百度API Token: </div><input type="text" name="token"></li>
                       </ul>
                        <input id="submit" class="add_submit" type="submit"/>
                    </form>

                </div>
            </div>

        </div>
        <jsp:include page="../homePage/pageBlock/footer.jsp"/>
    </div>
</div>
<script type="application/javascript">

    $(function () {
        $('#submit').click(function () {
            var p = $("input[name='password']").val();
            var p1 = $("input[name='password1']").val();
            var n = $("input[name='username']").val();
            var t = $("input[name='token']").val();
            if (p == null || p1 == null || p != p1) {
                alert("密码验证错误,请核对后重新输入");
                return false;
            } else if (t == null) {
                alert("token不能为空!");
                return false;
            } else if (n == null) {
                alert("用户名不能为空!");
                return false;
            } else {
                $("form[name='frm']").submit();
            }
        })
    });
</script>
</body>
</html>
