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
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/public/themes/flick/daterangepicker-bs2.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/backstage.css">



    <style>
        /*日历*/
        .list2 table .list2_top td, th {
            color: #000000;
        }
        .displayNone{
            display: none;
        }
        .table-condensed>thead>tr>th, .table-condensed>tbody>tr>th, .table-condensed>tfoot>tr>th, .table-condensed>thead>tr>td, .table-condensed>tbody>tr>td, .table-condensed>tfoot>tr>td {
            padding: 8px 5px;
        }

    </style>
</head>
<body>
<%--<div id="background" class="background"></div>
<div id="progressBar" class="progressBar">数据加载中，请稍等...</div>
<div id="progressBar1" class="progressBar">正在生成数据，请稍等...</div>--%>
<jsp:include page="../homePage/pageBlock/backstage_nav.jsp"/>
<div class="backstage_concent mid  over" >
    <div class="backstage_notice over">
        <span>注：如果没有选取时间则默认拉取昨天的数据,拉取时间较长，请勿关闭页面。</span>
    </div>
    <div class="backstage_list over">
        <ul>
            <li>
                <span>选择时间范围：</span>
                <input type="text" id="date" class="time_input" readonly>
                <input name="reservation"  type="image" class="date"
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
<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap-daterangepicker-moment.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.js"></script>
<script type="text/javascript">
    // 对Date的扩展，将 Date 转化为指定格式的String
    // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
    // 例子：
    // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2014-07-02 08:09:04.423
    // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2014-7-2 8:9:4.18
    var dateclicks = "";
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
        $("input[cname=dateClick]").click(function () {
            dateclicks = $(this)
        });
        var distance = 0;
        //加载日历控件
        $('input[name="reservation"]').daterangepicker({
                    "showDropdowns": true,
                    "timePicker24Hour": true,
                    timePicker: true,
                    timePickerIncrement: 30,
                    format: 'DD/MM/YYYY',
                    ranges: {
                        //'最近1小时': [moment().subtract('hours',1), moment()],
                        '今天': [moment().startOf('day'), moment()],
                        '昨天': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
                        '过去7天': [moment().subtract('days', 6), moment()],
                        '过去14天': [moment().subtract('days', 13), moment()],
                        '过去30天': [moment().subtract('days', 29), moment()],
                        '上个月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                    },
                    "locale": {
                        "format": "DD/MM/YYYY",
                        "separator": " - ",
                        "applyLabel": "确定",
                        "cancelLabel": "关闭",
                        "fromLabel": "From",
                        "toLabel": "To",
                        "customRangeLabel": "Custom",
                        "daysOfWeek": [
                            "日",
                            "一",
                            "二",
                            "三",
                            "四",
                            "五",
                            "六"
                        ],
                        "monthNames": [
                            "一月",
                            "二月",
                            "三月",
                            "四月",
                            "五月",
                            "六月",
                            "七月",
                            "八月",
                            "九月",
                            "十月",
                            "十一月",
                            "十二月"
                        ],
                        "firstDay": 1
                    },
                    "startDate": moment(),
                    "endDate": moment()
                },
                function (start, end, label,e) {
                        var _startDate = start.format('YYYY-MM-DD');
                        var _endDate = end.format('YYYY-MM-DD');
                        daterangepicker_start_date = _startDate;
                        daterangepicker_end_date = _endDate;
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
                    alert("请耐心等待,上一次的拉取操作尚未完成，请勿重复操作。")
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
