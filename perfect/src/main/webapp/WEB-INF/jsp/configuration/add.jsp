<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>大数据智能营销</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/login.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
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
<div class="concent fr over">
    <div class="mid over">
        <div class="on_title over">
            <a href="#">用户中心</a>&nbsp;&nbsp;&gt;&nbsp;&nbsp;<span>添加推广帐号</span>
        </div>
        <div id="tab">
            <div class="configure over">
                <div class="configure_top over">
                    <h3 class="fl">添加推广帐号</h3>
                    <a href="configure.html" class="fr"> → 返回管理</span></a>
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
            </div>
        </div>
    </div>
</div>

</body>
</html>
