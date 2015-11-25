<%--
  Created by IntelliJ IDEA.
  User: SubDong
  Date: 2014/10/8
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>大数据智能营销</title>
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css/css/public/public.css/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/css/accountCss/backstage.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <style>
        .displayNone {
            display: none;
        }
        .fl{
            float: left;
        }
    </style>
</head>
<body>
<%--<div id="background" class="background"></div>
<div id="progressBar" class="progressBar">数据加载中，请稍等...</div>
<div id="progressBar1" class="progressBar">正在生成数据，请稍等...</div>--%>
<jsp:include page="../homePage/pageBlock/backstage_nav.jsp"/>
<div class="backstage_concent mid over" style="height: 106px">
    <div class="backstage_notice over">
        <span>注：此页操作，请谨慎。稍不注意会给系统带来严重的后果！！！</span>
    </div>
    <div class="backstage_list backstage_list_redis  over">
        <ul>
            <li>  <span>输入要删除的key：</span></li>
           <li><input type="text" id="textY" style="float: none"></li>
          <li><a id="btnY" class="btn btn-info" style="height: 30px;line-height: 18px;">确定删除</a></li>
            <li><div id="appendtext"></div>  </li>
        </ul>
    </div>
</div>
<div class="backstage_concent mid over">
    <div style="font-size: 14px;font-weight: bold">redis  Key值：</div>
    <div id="dataLog"></div>
</div>
<script>
    $(document).ready(function () {
        var getdata = function(){
            $.ajax({
                url: "/admin/reportPull/getRedisKey",
                type: "GET",
                dataType: "json",
                success: function (data) {
                    $("#dataLog").empty();
                    $.each(data.rows, function (i, item) {
                        $("#dataLog").append("<div style='font-size: 12px;margin-top: 10px;'>" + item + "</div>");
                    });
                }
            });
        }
        getdata();

        $("#btnY").click(function(){
            var keyvalue = $("#textY").val();
            $.ajax({
                url: "/admin/reportPull/delRedisKey",
                type: "GET",
                dataType: "json",
                data:{
                    keyValues:keyvalue
                },
                success: function (data) {
                    if(data.rows == 1){
                        alert("删除成功！！！");
                        baiduAccountAlertPrompt.show("删除成功！！！");
                        getdata();
                    }else{
//                        alert("删除失败！！！")
                        baiduAccountAlertPrompt.show("删除失败！！！")
                    }
                }
            });
        })
    });
</script>
</body>
</html>
