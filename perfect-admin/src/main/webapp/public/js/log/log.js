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
                //strictSearch:true,
                pageSize: 20,
                searchAlign: 'left',
                pageList: new Array(20, 50, 100),
                paginationPreText: "上一页",
                paginationNextText: "下一页",
                showHeader: true,
                sidePagination: 'server'
                //queryParams: function (params) {
                //    params["page"] = params.offset;
                //    return params;
                //}
                //data: slog.test.data
            });
        }
    }
}
slog.init.data();
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
        });

})