<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/12/17
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>获取安装代码</title>
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script>
        $(function () {
            $("#registerDomain input[type='radio']").change(function () {
                var a = document.getElementsByName("semAccount");
                if (a[0].checked == true) {
                    $("#selectDiv").hide();
                    $("#inputDiv").show();
                } else {
                    $("#selectDiv").show();
                    $("#inputDiv").hide();
                }
            });
        });
        function loadSEMDomain() {
            var selectDomain = $("select[name='baiduDomain']");
            var selectOption=selectDomain.find("option");
            var account=$("input[name='domain']").val();
            if (selectOption.length <= 1) {
                $.get("/pftstis/getDomainList",{account:account}, function (res) {

                });
            }
        }
    </script>
</head>
<body>
<div style="text-align: center;">
    <form id="registerDomain">

        <br/>
        <input type="radio" value="all" name="semAccount"/>输入域名
        <input type="radio" value="one" name="semAccount"/>选择SEM已有的域名
        <br/>
        <div id="inputDiv">
            <label>域名</label>
            <input name="domain" type="text" style="width:120px;"/>
        </div>

        <div id="selectDiv" style="display: none;">
            <input type="text" name="perfectAccount"/><input type="button" value="获取" style="cursor: pointer;" onclick="loadSEMDomain();"/>
            <select name="baiduDomain">
                <option value="-1">无注册域名信息</option>
            </select>
        </div>
    </form>
</div>
</body>
</html>
