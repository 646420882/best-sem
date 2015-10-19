<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2014-9-1
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
  <title></title>
  <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">--%>
  <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">--%>
  <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">--%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/themes/flick/daterangepicker-bs3.css">--%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">--%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/themes/flick/jquery.comiseo.daterangepicker.css">--%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/themes/flick/daterangepicker-bs3.css">--%>


    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/themes/flick/font-awesome.min.css"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/public/themes/flick/daterangepicker-bs2.css"
            />
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.min.js">
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.min.js">
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap-daterangepicker-moment.js">
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.js">
    </script>

</head>

<body>
<input type="text" style="width: 400px;display: none" name="reservation" id="reservationtime"
       class="form-control" value="08/01/2013 - 08/01/2013" class="span4"
        />


<script>

$(function() {
   $("div.daterangepicker").css({"display":"block","top":"0px","right":"auto"})
    $(".daterangepicker .ranges li:last").css({"display":"none"})
    $(".minuteselect").css({"display":"none"})
    $(".ui-popup-follow .ui-dialog-arrow-b").css({"display":"none"})


});
$('#reservationtime').daterangepicker({
    "showDropdowns": true,
    "timePicker24Hour": true,
    timePicker: true,
    timePickerIncrement: 30,
    format: 'MM/DD/YYYY ',
//    ranges : {
//        //'最近1小时': [moment().subtract('hours',1), moment()],
//        '今日': [moment().startOf('day'), moment()],
//        '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
//        '最近7日': [moment().subtract('days', 6), moment()],
//        '最近30日': [moment().subtract('days', 29), moment()]
//    },
    "locale": {
        "format": "MM/DD/YYYY",
        "separator": " - ",
        "applyLabel": "确定",
        "cancelLabel": "返回",
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
    "endDate":moment()
}, function(start, end, label) {
    console.log("New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')");
});

</script>

</body>


</html>
