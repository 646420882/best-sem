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
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/themes/flick/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/themes/flick/daterangepicker-bs2.css" type="text/css"      media="all" rel="stylesheet"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.min.js"></script>
    <script type="text/javascript"  src="${pageContext.request.contextPath}/public/js/bootstrap-daterangepicker-moment.js"></script>
</head>
<body>

<input type="text" style="width: 400px;display: none" name="reservation" id="reservationtime"
       class="span4 form-control TimingPauseDialog " value="08/01/2013 1:00 PM - 08/01/2013 1:30 PM"/>
<input id="Timing" value="2" style="display: none">
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.js"></script>
<script>
    function closeDialog(){
      top.dialog.getCurrent().close().remove();
    }
    $(function () {
        $("div.daterangepicker").css({"display": "block", "top": "0px", "right": "auto"})
        $(".daterangepicker .ranges li:last").css({"display": "none"})

    });
    $('#reservationtime').daterangepicker({
        "showDropdowns": true,
        "timePicker24Hour": true,
        timePicker: true,
        timePickerIncrement: 30,
        format: 'MM/DD/YYYY h:mm A',
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
        "endDate": moment()
    }, function (start, end, label) {
        console.log("New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')");
    });

</script>

</body>


</html>
