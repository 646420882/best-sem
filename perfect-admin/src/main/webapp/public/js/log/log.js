/**
 * Created by guochunyan on 2015/12/16.
 */
var slog = {
    init: {
        data: function () {
            $('#logAdmin').bootstrapTable({
                locale: 'zh-CN',
                url: "/syslogs",
                pagination: true,
                search: true,
                smartDisplay: false,
                pageSize: 20,
                searchAlign: 'left',
                pageList: new Array(20, 50, 100),
                paginationPreText: "上一页",
                paginationNextText: "下一页",
                showHeader: true,
                sidePagination: 'server',
                queryParams: function (params) {
                    var _tmpStart = new Date().Format("yyyy-MM-dd 00:00:00");
                    var _tmpEnd = new Date().Format("yyyy-MM-dd 23:59:59");
                    var startTime = $("#startTime").val();
                    var endTime = $("#endTime").val();
                    if (startTime) {
                        params["start"] = startTime;
                    } else {
                        params["start"] = new Date(_tmpStart).getTime();
                    }
                    if (endTime) {
                        params["end"] = endTime;
                    } else {
                        params["end"] = new Date(_tmpEnd).getTime();
                    }
                    return params;
                }
            });
        }
    }
}
slog.init.data();
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18

$(function () {
    $('input[name="reservation"]').daterangepicker({
            "showDropdowns": true,
            "timePicker24Hour": true,
            timePicker: true,
            timePickerIncrement: 30,
            format: 'YY/MM/DD',
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
                "format": "YYYY/MM/DD",
                "separator": " - ",
                "applyLabel": "确定",
                "cancelLabel": "关闭",
                "fromLabel": "From",
                "toLabel": "To",
                "customRangeLabel": "Custom",
                "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
                "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                "firstDay": 1
            },
            "startDate": moment(),
            "endDate": moment()
        },
        function (start, end, label, e) {
            var _startDate = start.format('YY-MM-DD');
            var _endDate = end.format('YY-MM-DD');
            daterangepicker_start_date = _startDate;
            daterangepicker_end_date = _endDate;
            $("#date").val(daterangepicker_start_date + " 至 " + daterangepicker_end_date);
            var startTime = $("#startTime").val(new Date(new Date(start.format('YYYY-MM-DD')).Format("yyyy-MM-dd 00:00:00")).getTime());
            var endTime = $("#endTime").val(new Date(new Date(end.format('YYYY-MM-DD')).Format("yyyy-MM-dd 23:59:59")).getTime());
            $('#logAdmin').bootstrapTable("refresh");
        });

})
