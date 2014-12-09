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

var statDate,start_date,end_date,dateclicks;
//加载日历控件
$("input[name=reservation]").daterangepicker();

$("input[cname=dateClick]").click(function () {
    dateclicks = $(this);
});

$(".btnDone").on('click', function () {
    var _startDate = $('.range-start').datepicker('getDate');
    var _endDate = $('.range-end').datepicker('getDate');
    if (_startDate != null && _endDate != null) {
        if (_startDate > _endDate) {
            return false;
        }
        statDate = 0;
        start_date = _startDate.Format("yyyy-MM-dd");
        end_date = _endDate.Format("yyyy-MM-dd");
        dateclicks.parent().prev().val(start_date+" 至 "+end_date);
    }
});
$("#heDiv li").click(function(){
    $("#heDiv li").removeClass("active");
    $(this).addClass("active");
})
$(".winfont").click(function(){
    $(".winfont").removeClass("st");
    $(this).addClass("st");
});
$("#blankCheckbox").click(function(){
    if($(this).is(':checked')){
        $("#inputDiv2").removeClass("display");
    }else{
        $("#inputDiv2").addClass("display");
    }
});