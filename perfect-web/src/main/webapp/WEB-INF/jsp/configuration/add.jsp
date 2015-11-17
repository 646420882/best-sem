<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>大数据智能营销</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/login/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/media.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/login/login.css">
    <script type="text/javascript" src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery.pin/1.0.1/jquery.pin.min.js"></script>
    <script type="text/javascript">

        var judeit =0;
       $(function () {
            var $ = function (ID) {
                        return document.getElementById(ID);
                    },
                    oTab = $("stepTable"),
                    oTd = oTab.getElementsByTagName("TD");
                for (var i = 0; i < oTd.length; i++) {
                (function (i) {
                    oTd[i].onclick = function () {
                        for (var j = oTd.length - 1; j > i; j--) {
                            oTd[j].className = "";
                        }
                        if(document.getElementById("checkboxid").checked) {
                        var dsf = this.getAttribute("cname");
                            if(dsf == "step3"){
                                if(judeit ==1){
                                    for (var j = 0; j < i; j++) {
                                        oTd[j].className = "clicked";
                                    }
                                    this.className = "current";
                                    document.getElementById("step1").className = "hide";
                                    document.getElementById("step2").className = "hide";
                                    document.getElementById("step3").className = "hide";
                                    document.getElementById(dsf).className = "";
                                }
                            }else {
                                for (var j = 0; j < i; j++) {
                                    oTd[j].className = "clicked";
                                }
                                this.className = "current";
                                document.getElementById("step1").className = "hide";
                                document.getElementById("step2").className = "hide";
                                document.getElementById("step3").className = "hide";
                                document.getElementById(dsf).className = "";
                                judeit = 0;
                            }
                        }
                    }
                })(i);
            }
       });
        function progress(number){
            var source = document.getElementsByName("source");
            for(var i=0;i<source.length;i++)
            {
                if(number==1){
                    //判断那个单选按钮为选中状态
                    if(source[i].checked)
                    {
                            document.getElementById("step1").className = "hide";
                            document.getElementById("step3").className = "hide";
                            document.getElementById("step2").className = "";
                            document.getElementById("buzhou2").className = "current";
                    }
                }
                if(number == 2){

                    var userName = document.getElementById("username").value;
                    var pwd = document.getElementById("pwd").value;
                    var token = document.getElementById("token").value;
                    if((userName != null && userName != undefined && userName != "") && (pwd != null && pwd != undefined && pwd !="") && (token != null && token != undefined && token != "")){
                        $.ajax({
                            url: "/configuration/save",
                            type: "GET",
                            dataType: "json",
                            data: {
                                username: userName,
                                password: pwd,
                                token: token
                            },
                            success: function (data) {
                                if(data.status == 1){
                                    document.getElementById("step1").className = "hide";
                                    document.getElementById("step3").className = "";
                                    document.getElementById("step2").className = "hide";
                                    document.getElementById("buzhou3").className = "current";
                                    setTimeout('location.href="/home"',2000)
                                    judeit =1;
                                }else{
//                                    alert("请确认你的信息是否填写正确后重新提交！");
                                    AlertPrompt.show("请确认你的信息是否填写正确后重新提交!")
                                }
                            }
                        });
                    }else{
                        if (pwd == null || pwd==undefined || pwd == "") {
//                            alert("密码不能为空！");
                            AlertPrompt.show("密码不能为空!")
                            return false;
                        } else if (token == null || token==undefined || token == "") {
//                            alert("token不能为空!");
                            AlertPrompt.show("token不能为空!")
                            return false;
                        } else if (userName == null || userName==undefined || userName == "") {
//                            alert("用户名不能为空!");
                            AlertPrompt.show("用户名不能为空!")
                            return false;
                        }
                    }
                }
            }
        };
    </script>
    <style type="text/css">
        .tab_box {
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
                <a href="../configuration/">用户中心</a>&nbsp;&nbsp;&gt;&nbsp;&nbsp;<a href="../configuration/">关联账号</a>&nbsp;&nbsp;&gt;&nbsp;&nbsp;<span>添加推广帐号</span>
            </div>
        </div>
        <div id="tab">
            <ul class="tab_menu">
                <li class="selected">
                    添加推广帐号
                </li>
            </ul>
            <div class="tab_box">
                <div class="containers">
                 <div class="configure over">
                <div class="configure_top over">
                    <h3 class="fl">添加推广帐号</h3>
                    <a href="../configuration/"  class="fr"> → 返回管理</a>
                </div>
                <div class="configure_under2 over">
                    <table id="stepTable" class="step_table" border="0" cellpadding="0" cellspacing="0" width="100%">
                        <tbody>
                        <tr>
                            <td class="current" cname="step1">
                                <div class="con"><span>1</span>选择搜索引擎</div>
                            </td>
                            <td  id="buzhou2" cname="step2">
                                <div class="tri">
                                    <div class="con"><span>2</span>验证推广帐号绑定</div>
                                </div>
                            </td>
                            <td id="buzhou3" cname="step3">
                                <div class="tri">
                                    <div class="con"><span>3</span>完成绑定</div>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="jindu over">
                    <div id="step1">
                        <input name="source" id="checkboxid" onclick="progress(1)" type="checkbox"/> <img
                            src="${pageContext.request.contextPath}/public/images/bdlogo.png"/>
                    </div>
                    <div id="step2" class="hide">
                        <form name="frm" method="post" action="">
                            <ul>
                                <li><div class="add_list01 fl">百度用户名: </div><input type="text" id="username" name="username"></li>
                                <li><div class="add_list01 fl">百度密码: </div><input type="password" id="pwd" name="password"></li>
                               <%-- <li><div class="add_list01 fl">再次确认百度密码:</div><input type="password" name="password1"></li>--%>
                                <li><div class="add_list01 fl">  百度API Token: </div><input type="text" id="token" name="token"></li>
                            </ul>
                            <input id="submit" class="add_submit" onclick="progress(2)" type="button" value="确认"/>
                        </form>
                    </div>
                    <div id="step3" class="hide" style="text-align: center">
                        <span style="font-size: 14px;margin-top: 100px;display: block;"><label style="color: red;font-weight: bold">绑定已完成.</label>&nbsp;&nbsp;&nbsp;2秒后自动跳转...如果没有跳转请点击 《<a href="${pageContext.request.contextPath}/home">此处</a>》跳转</span>
                    </div>
                </div>
            </div>
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
            /*var p1 = $("input[name='password1']").val();*/
            var n = $("input[name='username']").val();
            var t = $("input[name='token']").val();
            /*if (p == null || p1 == null || p != p1) {
                alert("密码验证错误,请核对后重新输入");
                return false;
            } else*/
            if (t == null) {
//                alert("token不能为空!");
                AlertPrompt.show("token不能为空!")
                return false;
            } else if (n == null) {
//                alert("用户名不能为空!");
                AlertPrompt.show("用户名不能为空!")
                return false;
            }
        })

    });
</script>
</body>
</html>
