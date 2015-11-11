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
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    <title>大数据智能营销</title>
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/backstage.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">
    <style>
        .displayNone{
            display: none;
        }
         </style>
</head>
<body>
<%--<div id="background" class="background"></div>
<div id="progressBar" class="progressBar">数据加载中，请稍等...</div>
<div id="progressBar1" class="progressBar">正在生成数据，请稍等...</div>--%>
<jsp:include page="../homePage/pageBlock/backstage_nav.jsp"/>
<div class="backstage_concent mid over">
        <div class="backstage_notice over">
            <span>注：如果没有选取时间则默认拉取昨天的数据,拉取时间较长，请勿关闭页面。</span>
        </div>
        <div class="backstage_list over">
            <ul>
                <li>
                    <span>选择时间范围：</span>
                    <input type="text" id="date" readonly>
                    <input name="reservationa" type="image" class="date"
                           onclick=" _posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                           src="${pageContext.request.contextPath}/public/img/date.png">
                </li>
                <li>
                    <span>选择拉取报告类型：</span>
                    <select id="selectOP">
                        <option value="0">拉取全部报告</option>
                        <option value="1">拉取账户报告</option>
                        <option value="2">拉取计划报告</option>
                        <option value="3">拉取单元报告</option>
                        <option value="4">拉取创意报告</option>
                        <option value="5">拉取关键词报告</option>
                        <option value="6">拉取地域报告</option>
                    </select>
                </li>
                <li>
                    <sapn style="color: #ff0000">注：如果不填写帐号拉取的是全账户的报告数据，填写后则是拉取填写帐号的报告数据</sapn>
                </li>
                <li>
                    <sapn class="fl">帐号: </sapn>
                    <input type="text" id="zhanghao">
                </li>
                <li>
                    <input type="button" id="tijiao" value="确定" class="sure">
                </li>
                <li>
                    <div id="appendtext"></div>
                </li>
            </ul>

        </div>
</div>
<div class="backstage_concent mid over" >
    <div style="font-size: 14px;font-weight: bold">拉取日志：</div>
    <div id="dataLog"></div>
</div>
<script type="text/javascript" src="http://cdn.bootcss.com/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.jQuery.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript">
    // 对Date的扩展，将 Date 转化为指定格式的String
    // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
    // 例子：
    // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2014-07-02 08:09:04.423
    // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2014-7-2 8:9:4.18
    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1,                 //月份
            "d+": this.getDate(),                    //日
            "h+": this.getHours(),                   //小时
            "m+": this.getMinutes(),                 //分
            "s+": this.getSeconds(),                 //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
</script>
<script>
    var getPullLog;
    var heartbeat = 10000;
    var daterangepicker_start_date=null,daterangepicker_end_date=null,selectOP = 0,number=0;
    $(document).ready(function(){
        $("input[name=reservationa]").daterangepicker();
        $(".btnDone").on('click', function () {
            var _startDate = $('.range-start').datepicker('getDate');
            var _endDate = $('.range-end').datepicker('getDate');
            daterangepicker_start_date = _startDate.Format("yyyy-MM-dd");
            daterangepicker_end_date = _endDate.Format("yyyy-MM-dd");
            $("#date").val(daterangepicker_start_date +" 至 "+ daterangepicker_end_date);
        });

        var getTime = setInterval("getPullLog()", heartbeat);
        $("#tijiao").click(function(){
            if(confirm("请再次确认拉取对象和时间是否正确"))
            {
                selectOP = $("#selectOP").find("option:selected").val();
                $("#appendtext").empty();
                $("#appendtext").append("<label class='mesLable'>数据拉取中，需要时间较长！请耐心等待。。。</label>");
                if(number==0){
//                    getTime = setInterval("getPullLog()", 2000);
                    var name = $("#zhanghao").val();
                    number++;
                    $.ajax({
                        url: "/admin/reportPull/getPullDatas",
                        type: "GET",
                        dataType: "json",
                        data: {
                            pullObj:selectOP,
                            startDate: daterangepicker_start_date,
                            endDate: daterangepicker_end_date,
                            userName:name
                        },
                        success: function (data) {
                            $("#appendtext").empty();
                            if(data == 1){
                                $("#appendtext").append("<label class='mesLable'>数据拉取成功</label>");
                            }
                            if(data == 0){
                                $("#appendtext").append("<label class='mesLable'>数据拉取失败</label>");
                            }
                            if(data == 3){
                                $("#appendtext").append("<label class='mesLable'>拉取数据出现异常数据</label>");
                            }
                            number = 0;
                        }
                    });
                    $("#tijiao").addClass("displayNone");
                    getTime = setInterval("getPullLog()", heartbeat);
                }else{
//                    alert("请耐心等待,上一次的拉取操作尚未完成，请勿重复操作。")
                    baiduAccountAlertPrompt.show("请耐心等待,上一次的拉取操作尚未完成，请勿重复操作。")
                }
            }
        });
        //loading
        var ajaxbg = $("#background,#progressBar");
        $("#progressBar1").hide();
        ajaxbg.hide();
        $(document).ajaxStart(function () {
            ajaxbg.show();
        });
        $(document).ajaxStop(function () {
            ajaxbg.fadeOut(1000);
        });

        getPullLog = function(){
            $.ajax({
                url: "/admin/reportPull/getPullLog",
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if(data.rows != "-1"){
                        $("#dataLog").empty();
                    }
                    $.each(data.rows,function(i,item){

                        if(item == '-1'){
                            clearInterval(getTime);
                            $("#tijiao").removeClass("displayNone");
                            ix=0;
                        }else{
                            $("#tijiao").removeClass("displayNone");
                            $("#tijiao").addClass("displayNone");
                            if(i == data.rows.length-1){
                                if(data.rows[data.rows.length-1] == "数据拉取完毕"){
                                    $("#dataLog").append("<div style='font-size: 12px;margin-top: 10px;'>"+item+"</div>");
                                }else{
                                    $("#dataLog").append("<div style='font-size: 12px;margin-top: 10px;'>"+item+"<img style='margin-left: 20px;' src='/public/img/loading.gif'></div>");
                                }
                            }else{
                                $("#dataLog").append("<div style='font-size: 12px;margin-top: 10px;'>"+item+"</div>");
                            }
                        }
                    });
                }

            });


        }
        getPullLog();
    });
</script>
</body>
</html>
