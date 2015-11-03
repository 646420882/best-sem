<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2015-10-15
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
    <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/public/themes/flick/daterangepicker-bs2.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap-daterangepicker-moment.js"></script>
</head>
<body>
<input type="text" style="width: 400px;display: none" id="reservationtime" name="reservation"
       class="span4 form-control TimingDelDialog" value="08/01/2013 - 08/01/2013"/>
<input id="Timing" value="1" style="display: none">
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.js"></script>
<script type="application/javascript">
    function closeDialog() {
        top.dialog.getCurrent().close().remove();
    }
    $(function () {
        $("div.daterangepicker").css({"display": "block", "top": "0px", "right": "auto"});
        $(".daterangepicker .ranges li:last").css({"display": "none"});
        $(".minuteselect").css({"display": "none"});
        $(".ui-popup-follow .ui-dialog-arrow-b").css({"display": "none"});
    });

    $('#reservationtime').daterangepicker({
        "showDropdowns": true,
        "timePicker24Hour": true,
        timePicker: true,
        timePickerIncrement: 30,
        format: 'DD/MM/YYYY',
//        ranges: {
//            //'最近1小时': [moment().subtract('hours',1), moment()],
//            '今日': [moment().startOf('day'), moment()],
//            '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
//            '最近7日': [moment().subtract('days', 6), moment()],
//            '最近30日': [moment().subtract('days', 29), moment()]
//        },
        "locale": {
            "format": "DD/MM/YYYY",
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

    var uploadFunc = function (period) {
        var startArr = (period.split("-")[0]).split("/");
        var endArr = (period.split("-")[1]).split("/");


        var hour = $("#timePickerSlect").find("option:selected").val();

        var day;
        if (startArr[0].trim() == endArr[0].trim()) {
            day = startArr[0].trim();
        } else {
            day = startArr[0].trim() + "-" + endArr[0].trim();
        }

        var month;
        if (startArr[1].trim() == endArr[1].trim()) {
            month = startArr[1].trim();
        } else {
            month = startArr[1].trim() + "-" + endArr[1].trim();
        }

        var year;
        if (startArr[2].trim() == endArr[2].trim()) {
            year = startArr[2].trim();
        } else {
            year = startArr[2].trim() + "-" + endArr[2].trim();
        }

        var cronExpression = "0 0 " + hour + " " + day + " " + month + " ? " + year;

        $.ajax({
            url: '/material/schedule/upload',
            type: 'POST',
            dataType: 'json',
            data: {
                "cron": cronExpression
            },
            success: function (data, textStatus, jqXHR) {
                console.log(data);
            }
        });
    }

</script>
</body>
</html>
