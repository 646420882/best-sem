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
    <title></title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">
</head>
<body>
<div style="margin-top: 180px;">
        <ul style="width: 500px;text-align: left;margin-left: 35%;padding: 30px;border: 1px solid #ddd">
            <li><span style="color: red">注：如果没有选取时间则为默认拉取昨天的数据</span></li>
            <li style="margin-top: 10px">
                选择时间范围：
                <input type="text" id="date" style="width: 200px;height:25px;" readonly>
                <input name="reservationa" type="image"
                       onclick=" _posX = $(this).offset().left; _posY = ($(this).offset().top + $(this).outerHeight());"
                       src="${pageContext.request.contextPath}/public/img/date.png">
            </li>
            <li style="margin-top: 10px">
                选择拉取报告类型：
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
            <li style="margin-top: 10px;text-align: center">
                <input type="button" id="tijiao" value="确定">
            </li>
            <li>
                <div id="appendtext"></div>
            </li>
        </ul>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-ui-1.11.0.min.js"></script>
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
        $("#tijiao").click(function(){
            if(confirm("请再次确认拉取对象和时间是否正确"))

            {
                selectOP = $("#selectOP").find("option:selected").val();
                $("#appendtext").empty();
                $("#appendtext").append("<span style='color:red'>数据拉取中，需要时间较长！请耐心等待。。。</span>");
                if(number==0){
                    number++;
                    $.ajax({
                        url: "/admin/reportPull/getPullDatas",
                        type: "GET",
                        dataType: "json",
                        data: {
                            pullObj:selectOP,
                            startDate: daterangepicker_start_date,
                            endDate: daterangepicker_end_date
                        },
                        success: function (data) {
                            $("#appendtext").empty();
                            if(data == 1){
                                $("#appendtext").append("<span style='color:red'>数据拉取成功</span>");
                            }
                            if(data == -1){
                                $("#appendtext").append("<span style='color:red'>数据拉取失败</span>");
                            }
                            if(data == -2){
                                $("#appendtext").append("<span style='color:red'>登陆帐号无拉取数据权限</span>");
                            }
                            number = 0;
                        }
                    });
                }else{
                    alert("请耐心等待之前的拉取操作完成，请勿重复操作。")
                }

            }
        });
    });

</script>
</body>
</html>
