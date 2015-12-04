var skipPage;
var selectChange;
var selectChangeVs;
var selectChangeDet;
var reportData;
var reportDataVS;
var accountBasisReport;
var curve;
var pieChart;
var binFalg;
var daterangepicker_start_date_t;
var daterangepicker_end_date_t;
/*初始化数据变量*/
var dataOne = "";
var dataTow = "";

//日期
var t_date = new Array();
var dateInterval = 0;
var colorOne = "#4572A7";
var colorTow = "#40BC2A";

var nameOne = "展现";
var nameTow = "点击";

unitOne = "次";
unitTow = "次"
//展现
var t_impr = new Array();
//点击数
var t_clicks = new Array();
//消费
var t_cost = new Array();
//点击率
var t_ctr = new Array();
//平均点击价格
var t_cpc = new Array();
//转化
var t_conversion = new Array();
//饼状图参数
//展现
var tsay_impr = new Array();
var tsa_impr = 0;

//点击
var tsay_clicks = new Array();
var tsa_clicks = 0;

//消费
var tsay_cost = new Array();
var tsa_cost = 0;

//转化
var tsay_conversion = new Array();
var tsa_conversion = 0;

//点击率
var tsay_ctr = new Array();
var tsa_ctr = 0;

//平均价格
var tsay_cpc = new Array();
var tsa_cpc = 0;

var fieldName = 'date';

var sort = 1;
var dateclicks = "";


var startDet = 0;
var sorts = "-11";
var endDet = 20;
var limitDet = 20;
var sortVS = "-1";
var pageDetNumber = 0;
var judgeDet = 0;
//基础报告
var startJC = 0;
var limitJC = 20;
//账户报告
var startVS = 0;
var endVs = 20;
var limitVS = 20;
var dataid = 0;
var dataname = "0";
var judety = 0;
//日期控件-开始日期
var daterangepicker_start_date = null;

//日期控件-结束日期
var daterangepicker_end_date = null;
$(function () {
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

    $(function () {
        //数据初始化

        judgeVS = 0;
        $("#pagination1").empty();
        reportDataVS();
        //$("input[name=reservation]").daterangepicker();
        $("#inputTow").cxCalendar();
        $("input[cname=dateClick]").click(function () {
            dateclicks = $(this)
        });
        var distance = 0;
        //加载日历控件
        $('input[name="reservation"]').daterangepicker({
                "showDropdowns": true,
                "timePicker24Hour": true,
                timePicker: false,
                timePickerIncrement: 30,
                "linkedCalendars":false,
                format: 'DD/MM/YYYY',
                minDate:'1/1/2005',
                maxDate:moment().startOf('day'),
                autoUpdateInput:true,
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
                //"startDate":'1/1/2015',
                "startDate":moment().subtract('days', 1).startOf('day'),
                "endDate": moment().subtract('days', 1).startOf('day')
            },
            function (start, end, label, e) {
                var _startDate = start.format('YYYY-MM-DD');
                var _endDate = end.format('YYYY-MM-DD');
                daterangepicker_start_date_t =start._d;
                daterangepicker_end_date_t =end._d;
                if (_startDate != null && _endDate != null) {
                    daterangepicker_start_date = _startDate;
                    daterangepicker_end_date = _endDate;
                    if ($("#checkboxhidden").val() == 1) {
                        $("#date3").val(daterangepicker_start_date);
                    }
                    //计算两个时间相隔天数
                    var sDate = new Date(daterangepicker_start_date);
                    var eDate = new Date(daterangepicker_end_date);
                    var a = new Date(daterangepicker_start_date.replace(/-/g, '/'));
                    var b = new Date(daterangepicker_end_date.replace(/-/g, '/'));
                    var fen = ((b.getTime() - a.getTime()) / 1000) / 60;
                    if (fen < 0) {
                        daterangepicker_start_date = null;
                        daterangepicker_end_date = null;
                        //alert("请选择正确的时间范围");
                        AlertPrompt.show("请选择正确的时间范围");
                        dateclicks.prev().val();
                        return;
                    }
                    distance = parseInt(fen / (24 * 60)) + 1;   //相隔distance天
                    if ($("#checkboxhidden").val() == 1) {
                        $("#dataComputing").empty();
                        $("#dataComputing").append("起 " + distance + " 天");
                    }
                    dateclicks.prev().val(daterangepicker_start_date + " 至 " + daterangepicker_end_date);
                    var types = $("#reportTypes").val();
                    if (types == "4" || types == "5" || types == "6" || types == "7") {
                        $("#reportTypes").val(4)
                    }
                    dataid = 0;
                    dataname = "0";
                    $("#downAccountReport").empty();
                    $("#downReport").empty();
                    $("#downReportSearch").empty();
                }
                $(".time_input").css({width:"200px"})
            });
        $('input[name="reservation"]').click(function(){
            setTimeout(function(){
            $(".ranges li:eq(1)").click();
            },10)
        })

        $("#userClick").click(function () {
            judgeVS = 0;
            $("#pagination1").empty();
            reportDataVS();
        });


        var $tab_li = $('.tab_menu li');
        $('.tab_menu li').click(function () {
            $(this).addClass('selected').siblings().removeClass('selected');
            var index = $tab_li.index(this);
            console.log(index)
            $('div.tab_box > div').eq(index).show().siblings().hide();
            $("#pageDet").empty();
            if ($("#reportType>a").eq(4).attr("class") == "current") {
                SearchClass();
            }
            if (index == 0) {
                $("#TitleName").html("账户报告");
            }
            else {
                $("#TitleName").html("明细报告");
            }
        });
        $('.tab_menu li:first').click(function () {
            $(".searh_report").hide();
        })

        $("#reportType>a").click(function () {
            $("#reportType>a").removeClass("current");
            $(this).addClass("current");
            $("#reportTypes").val($(this).attr("cname"));
            $("#pageDet").empty();
            judgeDet = 0;
            dataid = 0;
            dataname = "0";
            var types = $("#reportTypes").val();
            if (types == "4" || types == "5" || types == "6" || types == "7") {
                $("#reportTypes").val(4)
            }
            $("#downReport").empty();
        });
        $("#device>a").click(function () {
            $("#device>a").removeClass("current");
            $(this).addClass("current");
            $("#devices").val($(this).attr("cname"));
            $("#pageDet").empty();
            judgeDet = 0;
            dataid = 0;
            dataname = "0";
            var types = $("#reportTypes").val();
            if (types == "4" || types == "5" || types == "6" || types == "7") {
                $("#reportTypes").val(4)
            }
            $("#downReport").empty();
        });
        $("#dateLi>a").click(function () {
            $("#dateLi>a").removeClass("current");
            $(this).addClass("current");
            $("#dateLis").val($(this).attr("cname"));
            $("#pageDet").empty();
            judgeDet = 0;
            dataid = 0;
            dataname = "0";
            var types = $("#reportTypes").val();
            if (types == "4" || types == "5" || types == "6" || types == "7") {
                $("#reportTypes").val(4)
            }
            $("#downReport").empty();
        })
        $("#deviceUser>a").click(function () {
            $("#deviceUser>a").removeClass("current");
            $(this).addClass("current");
            $("#devicesUser").val($(this).attr("cname"));
            $("#pageVS").empty();
            $("#downAccountReport").empty();
        });
        $("#dateLiUser>a").click(function () {
            $("#dateLiUser>a").removeClass("current");
            $(this).addClass("current");
            $("#dateLisUser").val($(this).attr("cname"));
            $("#pageVS").empty();
            $("#downAccountReport").empty();
        });
        $("#checkboxInput").click(function () {
            if ($(this).is(":checked")) {
                $("#inputTow").attr("style", "height:20px;width:150px;border:1px solid #dadada; padding:0 12px;background:#fff url('/public/img/date.png ') 150px 0px no-repeat;");
                $("#inputOne").removeAttr("disabled");
                $("#checkboxhidden").val(1);
                $("#dataComputing").append("起 " + distance + " 天");
                $("#fenyeo").hide();
                $("#fenyue").hide();
            } else {
                $("#inputTow").attr("style", "display:none");
                $("#inputOne").attr("disabled", "disabled");
                $("#checkboxhidden").val(0);
                $("#inputTow").val("");
                $("#dataComputing").empty();
                $("#fenyeo").show();
                $("#fenyue").show();
            }
        });

        //初始化基础统计
        accountBasisReport();

        /**
         *生成报告按钮点击
         */
        $("#shengc").click(function () {

            colorOne = "#4572A7";
            colorTow = "#40BC2A";
            nameOne = "展现";
            nameTow = "点击";
            unitOne = "次";
            unitTow = "次"
            $("#downReport").empty();
            $("#pagination1").empty()
            $("#pagination2").empty();
            $("#pm_data").empty();
            reportData();
        });


        $("body").on("click", "input[name=check]", function () {
            var name = $(this).attr("cname");
            if ($("input[type='checkbox']:checked").length <= 2) {
                if (name == "impr") {
                    if ($(this).is(':checked')) {
                        if (dataOne == "") {
                            $(this).attr("xname", "dataOne");
                            colorOne = "#078CC7";
                            nameOne = "展现";
                            unitOne = "次";
                            dataOne = {
                                name: '展现',
                                type: 'line',
                                smooth: true,
                                data: t_impr,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#078CC7'
                                        }
                                    }
                                }
                            };
                            curve();
                        } else if (dataTow == "") {
                            $(this).attr("xname", "dataTow");
                            colorTow = "#078CC7";
                            nameTow = "展现";
                            unitTow = "次";
                            dataTow = {
                                name: '展现',
                                type: 'line',
                                smooth: true,
                                data: t_impr,
                                yAxisIndex: 1,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#078CC7'
                                        }
                                    }
                                }
                            };
                            curve();
                        }
                    } else {
                        if ($(this).attr("xname") == "dataOne") {
                            dataOne = "";
                            $(this).attr("xname", "");
                            curve();
                        } else if ($(this).attr("xname") == "dataTow") {
                            dataTow = "";
                            $(this).attr("xname", "");
                            curve();
                        }
                    }
                } else if (name == "clicks") {
                    if ($(this).is(':checked')) {
                        if (dataOne == "") {
                            $(this).attr("xname", "dataOne");
                            colorOne = "#40BC2A";
                            nameOne = "点击";
                            unitOne = "次";
                            dataOne = {
                                name: '点击',
                                type: 'line',
                                smooth: true,
                                data: t_clicks,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#40BC2A'
                                        }
                                    }
                                }
                            };
                            curve();
                        } else if (dataTow == "") {
                            $(this).attr("xname", "dataTow");
                            colorTow = "#40BC2A";
                            nameTow = "点击";
                            unitTow = "次";
                            dataTow = {
                                name: '点击',
                                type: 'line',
                                smooth: true,
                                data: t_clicks,
                                yAxisIndex: 1,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#40BC2A'
                                        }
                                    }
                                }
                            };
                            curve();
                        }
                    } else {
                        if ($(this).attr("xname") == "dataOne") {
                            dataOne = "";
                            $(this).attr("xname", "");
                            curve();
                        } else if ($(this).attr("xname") == "dataTow") {
                            dataTow = "";
                            $(this).attr("xname", "");
                            curve();
                        }
                    }
                } else if (name == "cost") {
                    if ($(this).is(':checked')) {
                        if (dataOne == "") {
                            $(this).attr("xname", "dataOne");
                            colorOne = "#F1521B";
                            nameOne = "消费";
                            unitOne = " ￥";
                            dataOne = {
                                name: '消费',
                                type: 'line',
                                smooth: true,
                                data: t_cost,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#F1521B'
                                        }
                                    }
                                }
                            };
                            curve();
                        } else if (dataTow == "") {
                            $(this).attr("xname", "dataTow");
                            colorTow = "#F1521B";
                            nameTow = "消费";
                            unitTow = " ￥";
                            dataTow = {
                                name: '消费',
                                type: 'line',
                                smooth: true,
                                data: t_cost,
                                yAxisIndex: 1,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#F1521B'
                                        }
                                    }
                                }
                            };
                            curve();
                        }
                    } else {
                        if ($(this).attr("xname") == "dataOne") {
                            dataOne = "";
                            $(this).attr("xname", "");
                            curve();
                        } else if ($(this).attr("xname") == "dataTow") {
                            dataTow = "";
                            $(this).attr("xname", "");
                            curve();
                        }
                    }
                } else if (name == "ctr") {
                    if ($(this).is(':checked')) {
                        if (dataOne == "") {
                            $(this).attr("xname", "dataOne");
                            colorOne = "#26CAE5";
                            nameOne = "点击率";
                            unitOne = " %";
                            dataOne = {
                                name: '点击率',
                                type: 'line',
                                smooth: true,
                                data: t_ctr,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#26CAE5'
                                        }
                                    }
                                }
                            };
                            curve();
                        } else if (dataTow == "") {
                            $(this).attr("xname", "dataTow");
                            colorTow = "#26CAE5";
                            nameTow = "点击率";
                            unitTow = " %";
                            dataTow = {
                                name: '点击率',
                                type: 'line',
                                smooth: true,
                                data: t_ctr,
                                yAxisIndex: 1,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#26CAE5'
                                        }
                                    }
                                }
                            };
                            curve();
                        }
                    } else {
                        if ($(this).attr("xname") == "dataOne") {
                            dataOne = "";
                            $(this).attr("xname", "");
                            curve();
                        } else if ($(this).attr("xname") == "dataTow") {
                            dataTow = "";
                            $(this).attr("xname", "");
                            curve();
                        }
                    }
                } else if (name == "cpc") {
                    if ($(this).is(':checked')) {
                        if (dataOne == "") {
                            $(this).attr("xname", "dataOne");
                            colorOne = "#60E47E";
                            nameOne = "平均点击价格";
                            unitOne = " ￥";
                            dataOne = {
                                name: '平均点击价格',
                                type: 'line',
                                smooth: true,
                                data: t_cpc,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#60E47E'
                                        }
                                    }
                                }
                            };
                            curve();
                        } else if (dataTow == "") {
                            $(this).attr("xname", "dataTow");
                            colorTow = "#60E47E";
                            nameTow = "平均点击价格";
                            unitTow = " ￥";
                            dataTow = {
                                name: '平均点击价格',
                                type: 'line',
                                smooth: true,
                                data: t_cpc,
                                yAxisIndex: 1,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#60E47E'
                                        }
                                    }
                                }
                            };
                            curve();
                        }
                    } else {
                        if ($(this).attr("xname") == "dataOne") {
                            dataOne = "";
                            $(this).attr("xname", "");
                            curve();
                        } else if ($(this).attr("xname") == "dataTow") {
                            dataTow = "";
                            $(this).attr("xname", "");
                            curve();
                        }
                    }
                } else if (name == "conv") {
                    if ($(this).is(':checked')) {
                        if (dataOne == "") {
                            $(this).attr("xname", "dataOne");
                            colorOne = "#DEDF00";
                            nameOne = "转化";
                            unitOne = "";
                            dataOne = {
                                name: '转化',
                                type: 'line',
                                smooth: true,
                                data: t_conversion,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#DEDF00'
                                        }
                                    }
                                }
                            };
                            curve();
                        } else if (dataTow == "") {
                            $(this).attr("xname", "dataTow");
                            colorTow = "#DEDF00";
                            nameTow = "转化";
                            unitTow = "";
                            dataTow = {
                                name: '转化',
                                type: 'line',
                                smooth: true,
                                yAxisIndex: 1,
                                data: t_conversion,
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#DEDF00'
                                        }
                                    }
                                }
                            };
                            curve();
                        }
                    } else {
                        if ($(this).attr("xname") == "dataOne") {
                            dataOne = "";
                            $(this).attr("xname", "");
                            curve();
                        } else if ($(this).attr("xname") == "dataTow") {
                            dataTow = "";
                            $(this).attr("xname", "");
                            curve();
                        }
                    }
                }
            } else {
                $(this).attr("checked", false);
            }
        });
    });
//明细报告
    reportData = function () {
        /*jQuery.ajaxSetup ({cache:false});*/
        $("#containerLegend").empty();
        $("#shujuthead").empty();
        $('#container').empty();
        $("#shuju").empty();
        $("#shujuAll").empty();
        $("#imprDiv").empty();
        $("#clickDiv").empty();
        $("#costDiv").empty();
        $("#convDiv").empty();
        $("#pm_data").hide();
        $("#container").hide();
        $("#imprDiv").hide();
        $("#pathImages").hide();
        $("#shuju").append("<div class='example'><div id='progress2'><div id='percentNumber'></div><div class='pbar'></div><div class='elapsed'></div></div></div>");
        var isMin = 0;
        if (judety <= 0) {
            isMin = 5;
        } else {
            $("#shuju").empty();
            $("#pathImages").show();
            isMin = 1;
            judety = 0;
        }
        // from second #5 till 15
        var iNow = new Date().setTime(new Date().getTime() + 1 * 1000); // now plus 1 secs
        var iEnd = new Date().setTime(new Date().getTime() + isMin * 1000); // now plus 10 secs
        $('#progress2').anim_progressbar({start: iNow, finish: iEnd, interval: 100});


        var reportTypes = $("#reportTypes").val();
        var devices = $("#devices").val();
        var dateLis = $("#dateLis").val();
        $.ajax({
            url: "/account/structureReport",
            type: "GET",
            dataType: "json",
            data: {
                startDate: daterangepicker_start_date,
                endDate: daterangepicker_end_date,
                reportType: reportTypes,
                devices: devices,
                dateType: dateLis,
                start: startDet,
                sort: sorts,
                limit: endDet,
                dataId: dataid,
                dataName: dataname
            },
            success: function (data) {
                inter = setInterval('if($("#jindut").val() >= 99){contextVal();clearInterval(inter);}', 500);
                contextVal = function () {
                    $("#shujuthead").empty();
                    $("#shuju").empty();
                    $("#shujuAll").empty();
                    $('#container').empty();
                    $("#pathImages").hide();
                    var html_head = "";
                    if (data.rows.length > 0) {
                        t_date.length = 0;
                        t_impr.length = 0;
                        t_clicks.length = 0;
                        t_cost.length = 0;
                        t_ctr.length = 0;
                        t_cpc.length = 0;
                        t_conversion.length = 0;
                        switch (reportTypes) {
                            case "1":
                                html_head = "<tr class='list2_top'><td>&nbsp;<span>时间</span><b><p><input class='one' type='button'onclick='javascript:sorts = -11;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 11;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>推广计划</span><b><p><input class='one' type='button'onclick='javascript:sorts = -8;reportData()'></p><p><input class='two' type='button'onclick='javascript:sorts = 8;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>推广单元</span><b><p><input class='one' type='button' onclick='javascript:sorts = -7;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 7;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>展现量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -1;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 1;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -2;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 2;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>消费</span><b><p><input class='one' type='button' onclick='javascript:sorts = -3;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 3;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击率</span><b><p><input class='one' type='button' onclick='javascript:sorts = -5;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 5;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>平均点击价格</span><b><p><input class='one' type='button' onclick='javascript:sorts = -4;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 4;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(网页)</span><b><p><input class='one' type='button' onclick='javascript:sorts = -6;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 6;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(商桥)</span><b><p></p><p></p></b></td>" +
                                "<td>&nbsp;<span>转化(电话)</span><b><p></p><p></p></b></td></td></tr>";
                                break;
                            case "2":
                                html_head = "<tr class='list2_top'><td>&nbsp;<span>时间</span><b><p><input class='one' type='button'onclick='javascript:sorts = -11;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 11;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>推广计划</span><b><p><input class='one' type='button'onclick='javascript:sorts = -8;reportData()'></p><p><input class='two' type='button'onclick='javascript:sorts = 8;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>推广单元</span><b><p><input class='one' type='button' onclick='javascript:sorts = -7;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 7;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>关键字</span><b><p><input class='one' type='button' onclick='javascript:sorts = -9;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 9;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>展现量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -1;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 1;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -2;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 2;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>消费</span><b><p><input class='one' type='button' onclick='javascript:sorts = -3;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 3;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击率</span><b><p><input class='one' type='button' onclick='javascript:sorts = -5;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 5;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>平均点击价格</span><b><p><input class='one' type='button' onclick='javascript:sorts = -4;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 4;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(网页)</span><b><p><input class='one' type='button' onclick='javascript:sorts = -6;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 6;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(商桥)</span><b><p></p><p></p></b></td>" +
                                "<td>&nbsp;<span>转化(电话)</span><b><p></p>    <p></p></b></td></td></tr>";
                                break;
                            case "3":
                                html_head = "<tr class='list2_top'><td>&nbsp;<span>时间</span><b><p><input class='one' type='button'onclick='javascript:sorts = -11;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 11;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>推广计划</span><b><p><input class='one' type='button'onclick='javascript:sorts = -8;reportData()'></p><p><input class='two' type='button'onclick='javascript:sorts = 8;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>推广单元</span><b><p><input class='one' type='button' onclick='javascript:sorts = -7;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 7;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>创意</span><b><p><input class='one' type='button' onclick='javascript:sorts = -12;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 12;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>展现量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -1;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 1;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -2;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 2;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>消费</span><b><p><input class='one' type='button' onclick='javascript:sorts = -3;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 3;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击率</span><b><p><input class='one' type='button' onclick='javascript:sorts = -5;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 5;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>平均点击价格</span><b><p><input class='one' type='button' onclick='javascript:sorts = -4;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 4;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(网页)</span><b><p><input class='one' type='button' onclick='javascript:sorts = -6;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 6;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(商桥)</span><b><p></p><p></p></b></td>" +
                                "<td>&nbsp;<span>转化(电话)</span><b><p></p><p></p></b></td></td></tr>";
                                break;
                            case "4":
                                html_head = "<tr class='list2_top'><td>&nbsp;<span>时间</span><b><p><input class='one' type='button'onclick='javascript:sorts = -11;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 11;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>账户</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                "<td>&nbsp;<span>地域</span><b><p><input class='one' type='button' onclick='javascript:sorts = -10;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 10;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>展现量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -1;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 1;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -2;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 2;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>消费</span><b><p><input class='one' type='button' onclick='javascript:sorts = -3;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 3;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击率</span><b><p><input class='one' type='button' onclick='javascript:sorts = -5;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 5;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>平均点击价格</span><b><p><input class='one' type='button' onclick='javascript:sorts = -4;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 4;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(网页)</span><b><p><input class='one' type='button' onclick='javascript:sorts = -6;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 6;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(商桥)</span><b><p></p><p></p></b></td>" +
                                "<td>&nbsp;<span>转化(电话)</span><b><p></p><p></p></b></td></td></tr>";
                                break;
                            case "5":
                                html_head = "<tr><td><a href='javascript:void(0)' class='returnUp' onclick='javascrpt:javascript:$(\"#reportTypes\").val(4);$(\"#pageDet\").empty();judgeDet=0;dataid=0;dataname=\"0\";reportData();'></a></td></tr><tr class='list2_top'><td>&nbsp;<span>时间</span><b><p><input class='one' type='button'onclick='javascript:sorts = -11;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 11;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>账户</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                "<td>&nbsp;<span>推广计划</span><b><p><input class='one' type='button' onclick='javascript:sorts = -7;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 7;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>展现量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -1;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 1;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -2;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 2;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>消费</span><b><p><input class='one' type='button' onclick='javascript:sorts = -3;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 3;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击率</span><b><p><input class='one' type='button' onclick='javascript:sorts = -5;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 5;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>平均点击价格</span><b><p><input class='one' type='button' onclick='javascript:sorts = -4;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 4;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(网页)</span><b><p><input class='one' type='button' onclick='javascript:sorts = -6;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 6;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(商桥)</span><b><p></p><p></p></b></td>" +
                                "<td>&nbsp;<span>转化(电话)</span><b><p></p><p></p></b></td></td></tr>";
                                break;
                            case "6":
                                html_head = "<tr><td><a href='javascript:void(0)' class='returnUp' onclick='javascrpt:javascript:$(\"#reportTypes\").val(5);$(\"#pageDet\").empty();judgeDet=0;dataid=0;dataname=\"0\";reportData();'></a></td></tr><tr class='list2_top'><td>&nbsp;<span>时间</span><b><p><input class='one' type='button'onclick='javascript:sorts = -11;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 11;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>账户</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                "<td>&nbsp;<span>推广计划</span><b><p><input class='one' type='button' onclick='javascript:sorts = -7;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 7;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>推广单元</span><b><p><input class='one' type='button' onclick='javascript:sorts = -7;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 7;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>展现量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -1;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 1;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -2;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 2;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>消费</span><b><p><input class='one' type='button' onclick='javascript:sorts = -3;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 3;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击率</span><b><p><input class='one' type='button' onclick='javascript:sorts = -5;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 5;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>平均点击价格</span><b><p><input class='one' type='button' onclick='javascript:sorts = -4;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 4;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(网页)</span><b><p><input class='one' type='button' onclick='javascript:sorts = -6;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 6;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(商桥)</span><b><p></p><p></p></b></td>" +
                                "<td>&nbsp;<span>转化(电话)</span><b><p></p><p></p></b></td></td></tr>";
                                break;
                            case "7":
                                html_head = "<tr><td><a href='javascript:void(0)' class='returnUp' onclick='javascrpt:javascript:$(\"#reportTypes\").val(6);$(\"#pageDet\").empty();judgeDet=0;reportData();'></a></td></tr><tr class='list2_top'><td>&nbsp;<span>时间</span><b><p><input class='one' type='button'onclick='javascript:sorts = -11;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 11;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>账户</span><b><p><input class='one' type='button'></p><p><input class='two' type='button'></p></b></td>" +
                                "<td>&nbsp;<span>推广计划</span><b><p><input class='one' type='button' onclick='javascript:sorts = -7;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 7;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>推广单元</span><b><p><input class='one' type='button' onclick='javascript:sorts = -7;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 7;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>关键字</span><b><p><input class='one' type='button' onclick='javascript:sorts = -9;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 9;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>展现量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -1;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 1;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击量</span><b><p><input class='one' type='button' onclick='javascript:sorts = -2;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 2;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>消费</span><b><p><input class='one' type='button' onclick='javascript:sorts = -3;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 3;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>点击率</span><b><p><input class='one' type='button' onclick='javascript:sorts = -5;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 5;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>平均点击价格</span><b><p><input class='one' type='button' onclick='javascript:sorts = -4;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 4;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(网页)</span><b><p><input class='one' type='button' onclick='javascript:sorts = -6;reportData()'></p><p><input class='two' type='button' onclick='javascript:sorts = 6;reportData()'></p></b></td>" +
                                "<td>&nbsp;<span>转化(商桥)</span><b><p></p><p></p></b></td>" +
                                "<td>&nbsp;<span>转化(电话)</span><b><p></p><p></p></b></td></td></tr>";
                                break;
                        }
                        $("#shujuthead").append(html_head);
                        //饼状图计算
                        var dataAll = new Array();
                        var nameAll = new Array();
                        var pie_impr = new Array();
                        var pie_click = new Array();
                        var pie_cost = new Array();
                        var pie_conv = new Array();
                        //数据联动
                        var pm_array = new Array()
                        var pie_num1 = 0;
                        var pie_num2 = 0;
                        var pie_num3 = 0;
                        var pie_num4 = 0;
                        if (reportTypes == 4) {
                            if (dateLis == 0) {
                                $.each(data.countData, function (i, countdata) {
                                    $.each(data.impr, function (i, impr) {
                                        if (impr.regionName != "其他") {
                                            if (devices == 2) {
                                                if (isNaN(impr.mobileImpression / countdata.mobileImpression) || (impr.mobileImpression / countdata.mobileImpression) == 'Infinity') {
                                                    pie_impr.push(eval("({name:'" + impr.regionName + "', value:0})"));
                                                } else {
                                                    var number = Math.round((impr.mobileImpression / countdata.mobileImpression) * 10000) / 100
                                                    pie_impr.push(eval("({name:'" + impr.regionName + "',value:" + number + "})"));
                                                    pie_num1 = 1;
                                                }
                                            } else {
                                                if (isNaN(impr.pcImpression / countdata.pcImpression) || (impr.mobileImpression / countdata.mobileImpression) == 'Infinity') {
                                                    pie_impr.push(eval("({name:'" + impr.regionName + "',value:0})"));
                                                } else {
                                                    var number = Math.round((impr.pcImpression / countdata.pcImpression) * 10000) / 100;
                                                    pie_impr.push(eval("({name:'" + impr.regionName + "',value:" + number + "})"));
                                                    pie_num1 = 1;
                                                }
                                            }
                                        }
                                    });
                                    $.each(data.click, function (i, impr) {
                                        if (impr.regionName != "其他") {
                                            if (devices == 2) {
                                                if (isNaN(impr.mobileClick / countdata.mobileClick) || (impr.mobileImpression / countdata.mobileImpression) == 'Infinity') {
                                                    pie_click.push(eval("({name:'" + impr.regionName + "', value:0})"));
                                                } else {
                                                    var number = Math.round((impr.mobileClick / countdata.mobileClick) * 10000) / 100;
                                                    pie_click.push(eval("({name:'" + impr.regionName + "',value:" + number + "})"));
                                                    pie_num2 = 1;
                                                }
                                            } else {
                                                if (isNaN(impr.pcClick / countdata.pcClick)) {
                                                    pie_click.push(eval("({name:'" + impr.regionName + "', value:0})"));
                                                } else {
                                                    var number = Math.round((impr.pcClick / countdata.pcClick) * 10000) / 100;
                                                    pie_click.push(eval("({name:'" + impr.regionName + "',value:" + number + "})"));
                                                    pie_num2 = 1;
                                                }
                                            }
                                        }
                                    });
                                    $.each(data.cost, function (i, impr) {
                                        if (impr.regionName != "其他") {
                                            if (devices == 2) {
                                                if (isNaN(impr.mobileCost / countdata.mobileCost) || (impr.mobileImpression / countdata.mobileImpression) == 'Infinity') {
                                                    pie_cost.push(eval("({name:'" + impr.regionName + "', value:0})"));
                                                } else {
                                                    var number = Math.round((impr.mobileCost / countdata.mobileCost) * 10000) / 100;
                                                    pie_cost.push(eval("({name:'" + impr.regionName + "',value:" + number + "})"));
                                                    pie_num3 = 1;
                                                }
                                            } else {
                                                if (isNaN(impr.pcCost / countdata.pcCost) || (impr.mobileImpression / countdata.mobileImpression) == 'Infinity') {
                                                    pie_cost.push(eval("({name:'" + impr.regionName + "', value:0})"));
                                                } else {
                                                    var number = Math.round((impr.pcCost / countdata.pcCost) * 10000) / 100;
                                                    pie_cost.push(eval("({name:'" + impr.regionName + "',value:" + number + "})"));
                                                    pie_num3 = 1;
                                                }
                                            }
                                        }
                                    });
                                    $.each(data.conv, function (i, impr) {
                                        if (impr.regionName != "其他") {
                                            if (devices == 2) {
                                                if (isNaN(impr.mobileConversion / countdata.mobileConversion) || (impr.mobileImpression / countdata.mobileImpression) == 'Infinity') {
                                                    pie_conv.push(eval("({name:'" + impr.regionName + "', value:0})"));
                                                } else {
                                                    var number = Math.round((impr.mobileConversion / countdata.mobileConversion) * 10000) / 100;
                                                    pie_conv.push(eval("({name:'" + impr.regionName + "',value:" + number + "})"));
                                                    pie_num4 = 1;
                                                }
                                            } else {
                                                if (isNaN(impr.pcConversion / countdata.pcConversion) || (impr.mobileImpression / countdata.mobileImpression) == Infinity) {
                                                    pie_conv.push(eval("({name:'" + impr.regionName + "', value:0})"));
                                                } else {
                                                    var number = Math.round((impr.pcConversion / countdata.pcConversion) * 10000) / 100;
                                                    pie_conv.push(eval("({name:'" + impr.regionName + "',value:" + number + "})"));
                                                    pie_num4 = 1;
                                                }
                                            }
                                        }
                                    });

                                    dataAll.push(pie_impr);
                                    nameAll.push("展现");
                                    dataAll.push(pie_click);
                                    nameAll.push("点击");
                                    dataAll.push(pie_cost);
                                    nameAll.push("消费");
                                    dataAll.push(pie_conv);
                                    nameAll.push("转化");
                                    binFalg = 1;
                                    for (var i = 0; i < dataAll.length; i++) {
                                        pm_array.push(dataAll[i]);
                                    }

                                    pieChart(dataAll, nameAll, "imprDiv", pm_array);


                                });
                            }

                        }
                        if (dateLis != 0) {
                            //曲线图数据装载
                            $.each(data.chart, function (i, dataItem) {
                                if (devices == 2) {
                                    t_date.push(dataItem.date);
                                    t_impr.push(dataItem.mobileImpression);
                                    t_clicks.push(dataItem.mobileClick);
                                    t_cost.push(Math.round(dataItem.mobileCost * 100) / 100);
                                    t_ctr.push(Math.round((dataItem.mobileCtr) * 10000) / 100);
                                    t_cpc.push(Math.round((dataItem.mobileCpc) * 10000) / 100);
                                    t_conversion.push(dataItem.mobileConversion);
                                } else {
                                    t_date.push(dataItem.date);
                                    t_impr.push(dataItem.pcImpression);
                                    t_clicks.push(dataItem.pcClick);
                                    t_cost.push(Math.round(dataItem.pcCost * 100) / 100);
                                    t_ctr.push(Math.round((dataItem.pcCtr) * 10000) / 100);
                                    t_cpc.push(Math.round((dataItem.pcCpc) * 10000) / 100);
                                    t_conversion.push(dataItem.pcConversion);
                                }
                            });
                        }

                        $.each(data.rows, function (i, items) {
                            if (data.length <= 0) {
                                $("#shuju").html("查无数据。。。。。");
                                return;
                            }
                            pageDetNumber = items.count;
                            var html_Go = "";
                            switch (reportTypes) {
                                case "1":
                                    if (i % 2 == 0) {
                                        if (devices == 2) {
                                            html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                        } else {
                                            html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                        }
                                    } else {
                                        if (devices == 2) {
                                            html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                        } else {
                                            html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                        }
                                    }
                                    break;
                                case "2":
                                    if (i % 2 == 0) {
                                        if (devices == 2) {
                                            html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.keywordName + "</td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                        } else {
                                            html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.keywordName + "</td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                        }
                                    } else {
                                        if (devices == 2) {
                                            html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.keywordName + "</td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                        } else {
                                            html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.keywordName + "</td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                        }
                                    }
                                    break;
                                case "3":
                                    if (i % 2 == 0) {
                                        if (devices == 2) {
                                            html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td><label style='color: dodgerblue'>" + items.creativeTitle + "</label><br/><label style='font-size: 8px'>" + items.description1 + "</label></td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                        } else {
                                            html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td><label style='color: dodgerblue'>" + items.creativeTitle + "</label><br/><label style='font-size: 8px'>" + items.description1 + "</label></td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                        }
                                    } else {
                                        if (devices == 2) {
                                            html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td><label style='color: dodgerblue'>" + items.creativeTitle + "</label><br/><label style='font-size: 8px'>" + items.description1 + "</label></td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                        } else {
                                            html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td><label style='color: dodgerblue'>" + items.creativeTitle + "</label><br/><label style='font-size: 8px'>" + items.description1 + "</label></td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                        }
                                    }
                                    break;
                                case "4":
                                    if (i % 2 == 0) {
                                        if (devices == 2) {
                                            html_Go = "<tr class='list2_box1' cname='pieClick' region='" + items.regionName + "' impr='" + items.mobileImpression + "' clicks='" + items.mobileClick + "' cost='" + Math.round(items.mobileCost * 100) / 100 + "' ctr='" + Math.round(items.mobileCtr) + "' cpc='" + Math.round(items.mobileCpc * 100) / 100 + "' conv='" + items.mobileConversion + "'>"
                                            + "<td>" + items.date + "</td>"
                                            + "<td><a href='javascript:void(0)' class='nextOne' onclick='javascript:$(\"#reportTypes\").val(5);$(\"#pageDet\").empty();judgeDet=0;reportData();'>" + items.account + "</a></td>"
                                            + "<td>" + items.regionName + "</td>"
                                            + "<td>" + items.mobileImpression + "</td>"
                                            + "<td>" + items.mobileClick + "</td>"
                                            + "<td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td>"
                                            + "<td>" + Math.round(items.mobileCpc * 100) / 100 + "</td>"
                                            + "<td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                        } else {
                                            html_Go = "<tr class='list2_box1' cname='pieClick' region='" + items.regionName + "' impr='" + items.pcImpression + "' clicks='" + items.pcClick + "' cost='" + Math.round(items.pcCost * 100) / 100 + "' ctr='" + Math.round(items.pcCtr) + "' cpc='" + Math.round(items.pcCpc * 100) / 100 + "' conv='" + items.pcConversion + "'><td>" + items.date + "</td>"
                                            + "<td><a href='javascript:void(0)' class='nextOne' onclick='javascript:$(\"#reportTypes\").val(5);$(\"#pageDet\").empty();judgeDet=0;reportData();'>" + items.account + "</a></td>"
                                            + "<td>" + items.regionName + "</td>"
                                            + "<td>" + items.pcImpression + "</td>"
                                            + "<td>" + items.pcClick + "</td>"
                                            + "<td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td>"
                                            + "<td>" + Math.round(items.pcCpc * 100) / 100 + "</td>"
                                            + "<td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                        }
                                    } else {
                                        if (devices == 2) {
                                            html_Go = "<tr class='list2_box2' cname='pieClick' region='" + items.regionName + "' impr='" + items.mobileImpression + "' clicks='" + items.mobileClick + "' cost='" + Math.round(items.mobileCost * 100) / 100 + "' ctr='" + Math.round(items.mobileCtr) + "' cpc='" + Math.round(items.mobileCpc * 100) / 100 + "' conv='" + items.mobileConversion + "'><td>" + items.date + "</td>"
                                            + "<td><a href='javascript:void(0)' class='nextOne' onclick='javascript:$(\"#reportTypes\").val(5);$(\"#pageDet\").empty();judgeDet=0;reportData();'>" + items.account + "</a></td>"
                                            + "<td>" + items.regionName + "</td>"
                                            + "<td>" + items.mobileImpression + "</td>"
                                            + "<td>" + items.mobileClick + "</td>"
                                            + "<td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td>"
                                            + "<td>" + Math.round(items.mobileCpc * 100) / 100 + "</td>"
                                            + "<td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                        } else {
                                            html_Go = "<tr class='list2_box2' cname='pieClick' region='" + items.regionName + "' impr='" + items.pcImpression + "' clicks='" + items.pcClick + "' cost='" + Math.round(items.pcCost * 100) / 100 + "' ctr='" + Math.round(items.pcCtr) + "' cpc='" + Math.round(items.pcCpc * 100) / 100 + "' conv='" + items.pcConversion + "'><td>" + items.date + "</td>"
                                            + "<td><a href='javascript:void(0)' class='nextOne' onclick='javascript:$(\"#reportTypes\").val(5);$(\"#pageDet\").empty();judgeDet=0;reportData();'>" + items.account + "</a></td>"
                                            + "<td>" + items.regionName + "</td>"
                                            + "<td>" + items.pcImpression + "</td>"
                                            + "<td>" + items.pcClick + "</td>"
                                            + "<td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td>"
                                            + "<td>" + Math.round(items.pcCpc * 100) / 100 + "</td>"
                                            + "<td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                        }
                                    }
                                    break;
                                case "5":
                                    if (i % 2 == 0) {
                                        if (devices == 2) {
                                            html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.account + "</td>"
                                            + "<td><a href='javascript:void(0)' class='nextOne' onclick='javascript:$(\"#reportTypes\").val(6);$(\"#pageDet\").empty();judgeDet=0;dataname=\"cid\";dataid=" + items.campaignId + ";reportData();'>" + items.campaignName + "</a></td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                        } else {
                                            html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.account + "</td>"
                                            + "<td><a href='javascript:void(0)' class='nextOne' onclick='javascript:$(\"#reportTypes\").val(6);$(\"#pageDet\").empty();judgeDet=0;dataname=\"cid\";dataid=" + items.campaignId + ";reportData();'>" + items.campaignName + "</a></td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                        }
                                    } else {
                                        if (devices == 2) {
                                            html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.account + "</td>"
                                            + "<td><a href='javascript:void(0)' class='nextOne' onclick='javascript:$(\"#reportTypes\").val(6);$(\"#pageDet\").empty();judgeDet=0;dataname=\"cid\";dataid=" + items.campaignId + ";reportData();'>" + items.campaignName + "</a></td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                        } else {
                                            html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.account + "</td>"
                                            + "<td><a href='javascript:void(0)' class='nextOne' onclick='javascript:$(\"#reportTypes\").val(6);$(\"#pageDet\").empty();judgeDet=0;dataname=\"cid\";dataid=" + items.campaignId + ";reportData();'>" + items.campaignName + "</a></td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                        }
                                    }
                                    break;
                                case "6":
                                    if (i % 2 == 0) {
                                        if (devices == 2) {
                                            html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.account + "</td>"
                                            + "<td>" + items.campaignName + "</td><td><a href='javascript:void(0)' class='nextOne' onclick='javascript:$(\"#reportTypes\").val(7);$(\"#pageDet\").empty();judgeDet=0;dataname=\"agid\";dataid=" + items.adgroupId + ";reportData();'>" + items.adgroupName + "</a></td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                        } else {
                                            html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.account + "</td>"
                                            + "<td>" + items.campaignName + "</td><td><a href='javascript:void(0)' class='nextOne' onclick='javascript:$(\"#reportTypes\").val(7);$(\"#pageDet\").empty();judgeDet=0;dataname=\"agid\";dataid=" + items.adgroupId + ";reportData();'>" + items.adgroupName + "</a></td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                        }
                                    } else {
                                        if (devices == 2) {
                                            html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.account + "</td>"
                                            + "<td>" + items.campaignName + "</td><td><a href='javascript:void(0)' class='nextOne' onclick='javascript:$(\"#reportTypes\").val(7);$(\"#pageDet\").empty();judgeDet=0;dataname=\"agid\";dataid=" + items.adgroupId + ";reportData();'>" + items.adgroupName + "</a></td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                        } else {
                                            html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.account + "</td>"
                                            + "<td>" + items.campaignName + "</td><td><a href='javascript:void(0)' class='nextOne' onclick='javascript:$(\"#reportTypes\").val(7);$(\"#pageDet\").empty();judgeDet=0;dataname=\"agid\";dataid=" + items.adgroupId + ";reportData();'>" + items.adgroupName + "</a></td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                        }
                                    }
                                    break;
                                case "7":
                                    if (i % 2 == 0) {
                                        if (devices == 2) {
                                            html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.account + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.keywordName + "</td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                        } else {
                                            html_Go = "<tr class='list2_box1'><td>" + items.date + "</td>"
                                            + "<td>" + items.account + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.keywordName + "</td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                        }
                                    } else {
                                        if (devices == 2) {
                                            html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.account + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.keywordName + "</td>"
                                            + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td><td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                        } else {
                                            html_Go = "<tr class='list2_box2'><td>" + items.date + "</td>"
                                            + "<td>" + items.account + "</td>"
                                            + "<td>" + items.campaignName + "</td><td>" + items.adgroupName + "</td><td>" + items.keywordName + "</td>"
                                            + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td><td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                            + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                        }
                                    }
                                    dataid = items.campaignId;
                                    dataname = "cid";
                                    break;
                            }

                            $("#shuju").append(html_Go);

                            if (dateLis != 0) {
                                $("#containerLegend").empty();
                                /*初始化曲线图所用需求*/
                                $("#containerLegend").html("<div class='tu_top over'><ul><li>展示曲线</li>"
                                + "<li><input name='check' cname='impr' xname='' type='checkbox' checked='checked'><span class='blue' ></span><b>展现</b></li>"
                                + "<li><input name='check' cname='clicks' xname='' type='checkbox' checked='checked'><span class='green'></span><b>点击</b></li>"
                                + "<li><input name='check' cname='cost' xname='' type='checkbox'><span class='red'></span><b>消费</b></li>"
                                + "<li><input name='check' cname='ctr' xname='' type='checkbox'><span class='blue2'></span><b>点击率</b></li>"
                                + "<li><input name='check' cname='cpc' xname='' type='checkbox'><span class='green2'></span><b>平均点击价格</b></li>"
                                + "<li><input name='check' cname='conv' xname='' type='checkbox'><span class='yellow'></span><b>转化</b></li><li><b style='color: red'>最多只能同时选择两项</b></li></ul></div>");

                                dataOne = {
                                    name: '展现',
                                    type: 'line',
                                    smooth: true,
                                    data: t_impr,
                                    itemStyle: {
                                        normal: {
                                            lineStyle: {
                                                color: '#4572A7'
                                            }
                                        }
                                    }
                                };
                                dataTow = {
                                    name: '点击',
                                    type: 'line',
                                    smooth: true,
                                    data: t_clicks,
                                    yAxisIndex: 1,
                                    itemStyle: {
                                        normal: {
                                            lineStyle: {
                                                color: '#40BC2A'
                                            }
                                        }
                                    }
                                };
                                $("input[cname=impr]").attr("xname", "dataOne");
                                $("input[cname=clicks]").attr("xname", "dataTow");
                                curve();
                            } else {
                                $("#containerLegend").empty();
                            }
                        });

                        var html_GoAll = "";
                        $.each(data.statist, function (i, items) {

                            if (reportTypes == "2" || reportTypes == "3") {
                                html_GoAll = "";
                                html_GoAll = html_GoAll + "<tr><td>计算机</td>"
                                + "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td>"
                                + "<td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                html_GoAll = html_GoAll + "<tr><td>移动设备</td>"
                                + "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td>"
                                + "<td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                html_GoAll = html_GoAll + "<tr><td>合计</td>"
                                + "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>" + (items.pcImpression + items.mobileImpression) + "</td><td>" + (items.pcClick + items.mobileClick) + "</td>"
                                + "<td>" + Math.round((items.pcCost + items.mobileCost) * 100) / 100 + "</td>"
                                + "<td>" + Math.round(((items.pcClick + items.mobileClick) / ((items.pcImpression + items.mobileImpression) == 0 ? 1 : items.pcImpression + items.mobileImpression)) * 10000) / 100 + "%</td>"
                                + "<td>" + Math.round(((items.pcCost + items.mobileCost) / ((items.pcClick + items.mobileClick) == 0 ? 1 : (items.pcClick + items.mobileClick))) * 100) / 100 + "</td>"
                                + "<td>" + (items.pcConversion + items.mobileConversion) + "</td><td>-</td><td>-</td></tr>";
                            } else {
                                var headTd = "";
                                var bodyTd = "";
                                if (reportTypes == "6") {
                                    headTd = "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>";
                                    bodyTd = "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>";
                                } else if (reportTypes == "7") {
                                    headTd = "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>";
                                    bodyTd = "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>";
                                } else {
                                    headTd = "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>";
                                    bodyTd = "<td>&nbsp;</td><td>&nbsp;</td>";
                                }
                                html_GoAll = html_GoAll + "<tr><td>计算机</td>"
                                + bodyTd + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td>"
                                + "<td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                                + "<td>" + Math.round(items.pcCtr * 100) / 100 + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                                html_GoAll = html_GoAll + "<tr><td>移动设备</td>"
                                + bodyTd + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td>"
                                + "<td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                                + "<td>" + Math.round(items.mobileCtr * 100) / 100 + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                                html_GoAll = html_GoAll + "<tr><td>合计</td>"
                                + bodyTd + "<td>" + (items.pcImpression + items.mobileImpression) + "</td><td>" + (items.pcClick + items.mobileClick) + "</td>"
                                + "<td>" + Math.round((items.pcCost + items.mobileCost) * 100) / 100 + "</td>"
                                + "<td>" + Math.round(((items.pcClick + items.mobileClick) / (items.pcImpression + items.mobileImpression)) * 10000) / 100 + "%</td>"
                                + "<td>" + Math.round(((items.pcCost + items.mobileCost) / (items.pcClick + items.mobileClick)) * 100) / 100 + "</td>"
                                + "<td>" + (items.pcConversion + items.mobileConversion) + "</td><td>-</td><td>-</td></tr>";
                            }


                        });
                        $("#shujuAll").empty();
                        $("#shujuAll").append(html_GoAll);
                        records = pageDetNumber;
                        typepage = 2;
                        $("#pagination2").pagination(pageDetNumber, getOptionsFromForm(pageIndex));

                        $("#downReport").empty();
                        var downUrl = "startDate=" + daterangepicker_start_date + "&endDate=" + daterangepicker_end_date + "&reportType=" + reportTypes
                            + "&devices=" + devices + "&dateType=" + dateLis + "&dataId=" + dataid;
                        $("#downReport").append("<a href='/report/downReportCSV?" + downUrl + "'  class='become fl'>下载报告</a>")
                    }
                    if (data.rows.length > 10) {
                        dateInterval = Math.round(data.rows.length / 10);
                    }
                };

            }
        });
    }


    var pageNumberVS = 0;
    var judgeVS = 0;
//账户报告比较数据
    reportDataVS = function () {
        var date3 = $("#inputTow").val();
        var devicesUser = $("#devicesUser").val();
        var dateLisUser = $("#dateLisUser").val();
        var checkboxhidden = $("#checkboxhidden").val();
        $.ajax({
            url: "/account/accountDateVs",
            type: "GET",
            dataType: "json",
            data: {
                date1: daterangepicker_start_date,
                date2: daterangepicker_end_date,
                date3: date3,
                dateType: dateLisUser,
                devices: devicesUser,
                compare: checkboxhidden,
                sortVS: sortVS,
                startVS: startVS,
                limitVS: endVs
            },
            success: function (data) {
                $("#userTbody").empty();
                $("#userStits").empty();
                if (checkboxhidden == 0) {
                    $.each(data.date, function (i, item) {
                        $.each(data.rows, function (z, items1) {
                            if (items1[item] != null) {
                                $.each(items1[item], function (s, items) {
                                    pageNumberVS = items.count - 1;
                                    var html_User = "";
                                    if (i % 2 == 0) {
                                        if (devicesUser == 2) {
                                            html_User = "<tr class='list2_box1'><td>" + item + "</td>"
                                            + "<td>" + ((items.mobileImpression == null) ? "-" : items.mobileImpression) + "</td><td>" + ((items.mobileClick == null) ? "-" : items.mobileClick) + "</td><td>" + ((items.mobileCost == null) ? "-" : Math.round(items.mobileCost * 100) / 100) + "</td>"
                                            + "<td>" + ((items.mobileCtr == null) ? "-" : Math.round(items.mobileCtr * 100) / 100) + "%</td><td>" + ((items.mobileCpc == null) ? "-" : Math.round(items.mobileCpc * 100) / 100) + "</td><td>" + ((items.mobileConversion == null) ? "-" : items.mobileConversion) + "</td><td>-</td><td>-</td></tr>";
                                        } else {
                                            html_User = "<tr class='list2_box1'><td>" + item + "</td>"
                                            + "<td>" + ((items.pcImpression == null) ? "-" : items.pcImpression) + "</td><td>" + ((items.pcClick == null) ? "-" : items.pcClick) + "</td><td>" + ((items.pcCost == null) ? "-" : Math.round(items.pcCost * 100) / 100) + "</td>"
                                            + "<td>" + ((items.pcCtr == null) ? "-" : Math.round(items.pcCtr * 100) / 100) + "%</td><td>" + ((items.pcCpc == null) ? "-" : Math.round(items.pcCpc * 100) / 100) + "</td><td>" + ((items.pcConversion == null) ? "-" : items.pcConversion) + "</td><td>-</td><td>-</td></tr>";
                                        }
                                    } else {
                                        if (devicesUser == 2) {
                                            html_User = "<tr class='list2_box2'><td>" + item + "</td>"
                                            + "<td>" + ((items.mobileImpression == null) ? "-" : items.mobileImpression) + "</td><td>" + ((items.mobileClick == null) ? "-" : items.mobileClick) + "</td><td>" + ((items.mobileCost == null) ? "-" : Math.round(items.mobileCost * 100) / 100) + "</td>"
                                            + "<td>" + ((items.mobileCtr == null) ? "-" : Math.round(items.mobileCtr * 100) / 100) + "%</td><td>" + ((items.mobileCpc == null) ? "-" : Math.round(items.mobileCpc * 100) / 100) + "</td><td>" + ((items.mobileConversion == null) ? "-" : items.mobileConversion) + "</td><td>-</td><td>-</td></tr>";
                                        } else {
                                            html_User = "<tr class='list2_box2'><td>" + item + "</td>"
                                            + "<td>" + ((items.pcImpression == null) ? "-" : items.pcImpression) + "</td><td>" + ((items.pcClick == null) ? "-" : items.pcClick) + "</td><td>" + ((items.pcCost == null) ? "-" : Math.round(items.pcCost * 100) / 100) + "</td>"
                                            + "<td>" + ((items.pcCtr == null) ? "-" : Math.round(items.pcCtr * 100) / 100) + "%</td><td>" + ((items.pcCpc == null) ? "-" : Math.round(items.pcCpc * 100) / 100) + "</td><td>" + ((items.pcConversion == null) ? "-" : items.pcConversion) + "</td><td>-</td><td>-</td></tr>";
                                        }
                                    }
                                    $("#userTbody").append(html_User);
                                });
                            }
                        });
                    });
                    var html_UserPro = "";
                    $.each(data.statist, function (i, items) {

                        html_UserPro = "";
                        html_UserPro = html_UserPro + "<tr><td>计算机</td>"
                        + "<td>" + items.pcImpression + "</td><td>" + items.pcClick + "</td>"
                        + "<td>" + Math.round(items.pcCost * 100) / 100 + "</td>"
                        + "<td>" + Math.round(items.pcCtr) + "%</td><td>" + Math.round(items.pcCpc * 100) / 100 + "</td><td>" + items.pcConversion + "</td><td>-</td><td>-</td></tr>"
                        html_UserPro = html_UserPro + "<tr><td>移动设备</td>"
                        + "<td>" + items.mobileImpression + "</td><td>" + items.mobileClick + "</td>"
                        + "<td>" + Math.round(items.mobileCost * 100) / 100 + "</td>"
                        + "<td>" + Math.round(items.mobileCtr) + "%</td><td>" + Math.round(items.mobileCpc * 100) / 100 + "</td><td>" + items.mobileConversion + "</td><td>-</td><td>-</td></tr>"
                        html_UserPro = html_UserPro + "<tr><td>合计</td>"
                        + "<td>" + (items.pcImpression + items.mobileImpression) + "</td><td>" + (items.pcClick + items.mobileClick) + "</td>"
                        + "<td>" + Math.round((items.pcCost + items.mobileCost) * 100) / 100 + "</td>"
                        + "<td>" + Math.round(((items.pcClick + items.mobileClick) / (items.pcImpression + items.mobileImpression)) * 10000) / 100 + "%</td>"
                        + "<td>" + Math.round(((items.pcCost + items.mobileCost) / (items.pcClick + items.mobileClick)) * 100) / 100 + "</td>"
                        + "<td>" + (items.pcConversion + items.mobileConversion) + "</td><td>-</td><td>-</td></tr>";

                    });


                    $("#userStits").append(html_UserPro);
                    records = pageNumberVS;
                    typepage = 1;
                    $("#pagination1").pagination(pageNumberVS, getOptionsFromForm(pageIndex));

                    $("#downAccountReport").empty();
                    var downUrl = "date1=" + daterangepicker_start_date + "&date2=" + daterangepicker_end_date + "&date3=" + date3
                        + "&dateType=" + dateLisUser + "&devices=" + devicesUser + "&sortVS=" + sortVS + "&startVS" + startVS + "&limitVS" + endVs;
                    $("#downAccountReport").append("<a href='/report/downAccoutReportCSV?" + downUrl + "'  class='become fl'>下载报告</a>")
                } else {
                    $("#downAccountReport").empty();
                    var dateEach = new Array(), impression = new Array(), click = new Array(), cost = new Array(), ctr = new Array(), cpc = new Array(), conversion = new Array();
                    var dateEach1 = new Array(), impression1 = new Array(), click1 = new Array(), cost1 = new Array(), ctr1 = new Array(), cpc1 = new Array(), conversion1 = new Array();
                    $.each(data.date, function (i, item) {
                        dateEach.push(item);
                        $.each(data.rows, function (i, item1) {
                            if (item1[item] != null) {
                                $.each(item1[item], function (i, items) {
                                    if (item1[item] != null) {
                                        if (devicesUser == 2) {
                                            impression.push((items.mobileImpression == null) ? 0 : items.mobileImpression);
                                            click.push((items.mobileClick == null) ? 0 : items.mobileClick);
                                            cost.push((items.mobileCost == null) ? 0 : Math.round(items.mobileCost * 100) / 100);
                                            ctr.push((items.mobileCtr == null) ? 0 : Math.round(items.mobileCtr * 100) / 100);
                                            cpc.push((items.mobileCpc == null) ? 0 : Math.round(items.mobileCpc * 100) / 100);
                                            conversion.push((items.mobileConversion == null) ? 0 : items.mobileConversion);
                                        } else {
                                            impression.push(items.pcImpression);
                                            click.push(items.pcClick);
                                            cost.push(Math.round(items.pcCost * 100) / 100);
                                            ctr.push(Math.round(items.pcCtr * 100) / 100);
                                            cpc.push(Math.round(items.pcCpc * 100) / 100);
                                            conversion.push(items.pcConversion);
                                        }
                                    }
                                });
                            }
                        });
                    });
                    $.each(data.date1, function (i, item) {
                        dateEach1.push(item);
                        $.each(data.rows, function (i, item1) {
                            if (item1[item] != null) {
                                $.each(item1[item], function (i, items) {
                                    if (item1[item] != null) {
                                        if (devicesUser == 2) {
                                            impression1.push((items.mobileImpression == null) ? 0 : items.mobileImpression);
                                            click1.push((items.mobileClick == null) ? 0 : items.mobileClick);
                                            cost1.push((items.mobileCost == null) ? 0 : Math.round(items.mobileCost * 100) / 100);
                                            ctr1.push((items.mobileCtr == null) ? 0 : Math.round(items.mobileCtr * 100) / 100);
                                            cpc1.push((items.mobileCpc == null) ? 0 : Math.round(items.mobileCpc * 100) / 100);
                                            conversion1.push((items.mobileConversion == null) ? 0 : items.mobileConversion);
                                        } else {
                                            impression1.push((items.pcImpression == null) ? 0 : items.pcImpression);
                                            click1.push((items.pcClick == null) ? 0 : items.pcClick);
                                            cost1.push((items.pcCost == null) ? 0 : Math.round(items.pcCost * 100) / 100);
                                            ctr1.push((items.pcCtr == null) ? 0 : Math.round(items.pcCtr * 100) / 100);
                                            cpc1.push((items.pcCpc == null) ? 0 : Math.round(items.pcCpc * 100) / 100);
                                            conversion1.push((items.pcConversion == null) ? 0 : items.pcConversion);
                                        }
                                    }
                                });
                            }
                        });
                    });
                    for (var i = 0; i < data.date1.length; i++) {
                        var html_User1 = "";
                        var html_User2 = "";
                        if (i % 2 == 0) {
                            html_User1 = "<tr class='list2_box1'><td>" + dateEach[i] + "</td>"
                            + "<td><span>" + ((impression[i] == undefined) ? '-' : (isNaN(impression[i])) ? '-' : impression[i]) + "</span>" + (((impression[i] - impression1[i]) < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                            + "<span>" + ((impression1[i] == 0) ? '-%' : ((impression[i] - impression1[i] >= 0) ? ((isNaN(impression1[i])) ? "-" : (isNaN(Math.round(((impression[i] - impression1[i]) / impression1[i]) * 10000) / 100)) ? '-' : Math.round(((impression[i] - impression1[i]) / impression1[i]) * 10000) / 100) + "%" : "<strong>" + ((isNaN(impression1[i])) ? "-" : (isNaN(Math.round(((impression[i] - impression1[i]) / impression1[i]) * 10000) / 100)) ? '-' : Math.round(((impression[i] - impression1[i]) / impression1[i]) * 10000) / 100) + "%</strong>")) + "</span></td>"
                            + "<td><span>" + ((click[i] == undefined) ? '-' : (isNaN(click[i])) ? '-' : click[i]) + "</span>" + (((click[i] - click1[i]) < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                            + "<span>" + ((click1[i] == 0) ? '-%' : ((click[i] - click1[i] >= 0) ? ((isNaN(click1[i])) ? "-" : (isNaN(Math.round(((click[i] - click1[i]) / click1[i]) * 10000) / 100)) ? '-' : Math.round(((click[i] - click1[i]) / click1[i]) * 10000) / 100) + "%" : "<strong>" + ((isNaN(click1[i])) ? "-" : (isNaN(Math.round(((click[i] - click1[i]) / click1[i]) * 10000) / 100)) ? '-' : Math.round(((click[i] - click1[i]) / click1[i]) * 10000) / 100) + "%</strong>")) + "</span></td>"
                            + "<td><span>" + ((cost[i] == undefined) ? '-' : (isNaN(cost[i])) ? '-' : cost[i]) + "</span>" + (((cost[i] - cost1[i]) < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                            + "<span>" + ((cost1[i] == 0) ? '-%' : ((cost[i] - cost1[i] >= 0) ? ((isNaN(cost1[i])) ? "-" : (isNaN(Math.round(((cost[i] - cost1[i]) / cost1[i]) * 10000) / 100)) ? '-' : Math.round(((cost[i] - cost1[i]) / cost1[i]) * 10000) / 100) + "%" : "<strong>" + ((isNaN(cost1[i])) ? "-" : (isNaN(Math.round(((cost[i] - cost1[i]) / cost1[i]) * 10000) / 100)) ? '-' : Math.round(((cost[i] - cost1[i]) / cost1[i]) * 10000) / 100) + "%</strong>")) + "</span></td>"
                            + "<td><span>" + ((ctr[i] == undefined) ? '-' : (isNaN(ctr[i])) ? '-' : ctr[i]) + "%</span>" + ((ctr[i] - ctr1[i] < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                            + "<span>" + ((ctr1[i] == 0) ? '-%' : ((ctr[i] - ctr1[i] >= 0) ? ((isNaN(ctr1[i])) ? "-" : (isNaN(Math.round(((ctr[i] - ctr1[i]) / ctr1[i]) * 10000) / 100)) ? '-' : Math.round(((ctr[i] - ctr1[i]) / ctr1[i]) * 10000) / 100) + "%" : "<strong>" + ((isNaN(ctr1[i])) ? "-" : (isNaN(Math.round(((ctr[i] - ctr1[i]) / ctr1[i]) * 10000) / 100)) ? '-' : Math.round(((ctr[i] - ctr1[i]) / ctr1[i]) * 10000) / 100) + "%</strong>")) + "</span></td>"
                            + "<td><span>" + ((cpc[i] == undefined) ? '-' : (isNaN(cpc[i])) ? '-' : cpc[i]) + "</span>" + ((cpc[i] - cpc1[i] < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                            + "<span>" + ((cpc1[i] == 0) ? '-%' : ((cpc[i] - cpc1[i] >= 0) ? ((isNaN(cpc1[i])) ? "-" : (isNaN(Math.round(((cpc[i] - cpc1[i]) / cpc1[i]) * 10000) / 100)) ? '-' : Math.round(((cpc[i] - cpc1[i]) / cpc1[i]) * 10000) / 100) + "%" : "<strong>" + ((isNaN(cpc1[i])) ? "-" : (isNaN(Math.round(((cpc[i] - cpc1[i]) / cpc1[i]) * 10000) / 100)) ? '-' : Math.round(((cpc[i] - cpc1[i]) / cpc1[i]) * 10000) / 100) + "%</strong>")) + "</span></td>"
                            + "<td><span>" + ((conversion[i] == undefined) ? '-' : (isNaN(conversion[i])) ? '-' : conversion[i]) + "</span>" + ((conversion[i] - conversion1[i] < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                            + "<span>" + ((conversion1[i] == 0) ? '-%' : ((conversion[i] - conversion1[i] >= 0) ? ((isNaN(conversion1[i])) ? "-" : (isNaN(Math.round(((conversion[i] - conversion1[i]) / conversion1[i]) * 10000) / 100)) ? '-' : Math.round(((conversion[i] - conversion1[i]) / conversion1[i]) * 10000) / 100) + "%" : "<strong>" + ((isNaN(conversion1[i])) ? "-" : (isNaN(Math.round(((conversion[i] - conversion1[i]) / conversion1[i]) * 10000) / 100)) ? '-' : Math.round(((conversion[i] - conversion1[i]) / conversion1[i]) * 10000) / 100) + "%</strong>")) + "</span></td>"
                            + "</td><td>-</td><td>-</td></tr>";

                            html_User2 = "<tr class='list2_box1'><td>" + dateEach1[i] + "</td>"
                            + "<td>" + ((impression1[i] == undefined) ? 0 : impression1[i]) + "</td><td>" + ((click1[i] == undefined) ? 0 : click1[i]) + "</td><td>" + ((cost1[i] == undefined) ? 0 : cost1[i]) + "</td>"
                            + "<td>" + ((ctr1[i] == undefined) ? 0 : ctr1[i]) + "%</td><td>" + ((cpc1[i] == undefined) ? 0 : cpc1[i]) + "</td><td>" + ((conversion1[i] == undefined) ? 0 : conversion1[i]) + "</td><td>-</td><td>-</td></tr>"
                            + "<tr><td colspan='9'>&nbsp;</td></tr>";
                        } else {
                            html_User1 = "<tr class='list2_box2'><td>" + dateEach[i] + "</td>"
                            + "<td><span>" + ((impression[i] == undefined) ? '-' : (isNaN(impression[i])) ? '-' : impression[i]) + "</span>" + (((impression[i] - impression1[i]) < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                            + "<span>" + ((impression1[i] == 0) ? '-%' : ((impression[i] - impression1[i] >= 0) ? ((isNaN(impression1[i])) ? "-" : (isNaN(Math.round(((impression[i] - impression1[i]) / impression1[i]) * 10000) / 100)) ? '-' : Math.round(((impression[i] - impression1[i]) / impression1[i]) * 10000) / 100) + "%" : "<strong>" + ((isNaN(impression1[i])) ? "-" : (isNaN(Math.round(((impression[i] - impression1[i]) / impression1[i]) * 10000) / 100)) ? '-' : Math.round(((impression[i] - impression1[i]) / impression1[i]) * 10000) / 100) + "%</strong>")) + "</span></td>"
                            + "<td><span>" + ((click[i] == undefined) ? '-' : (isNaN(click[i])) ? '-' : click[i]) + "</span>" + (((click[i] - click1[i]) < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                            + "<span>" + ((click1[i] == 0) ? '-%' : ((click[i] - click1[i] >= 0) ? ((isNaN(click1[i])) ? "-" : (isNaN(Math.round(((click[i] - click1[i]) / click1[i]) * 10000) / 100)) ? '-' : Math.round(((click[i] - click1[i]) / click1[i]) * 10000) / 100) + "%" : "<strong>" + ((isNaN(click1[i])) ? "-" : (isNaN(Math.round(((click[i] - click1[i]) / click1[i]) * 10000) / 100)) ? '-' : Math.round(((click[i] - click1[i]) / click1[i]) * 10000) / 100) + "%</strong>")) + "</span></td>"
                            + "<td><span>" + ((cost[i] == undefined) ? '-' : (isNaN(cost[i])) ? '-' : cost[i]) + "</span>" + (((cost[i] - cost1[i]) < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                            + "<span>" + ((cost1[i] == 0) ? '-%' : ((cost[i] - cost1[i] >= 0) ? ((isNaN(cost1[i])) ? "-" : (isNaN(Math.round(((cost[i] - cost1[i]) / cost1[i]) * 10000) / 100)) ? '-' : Math.round(((cost[i] - cost1[i]) / cost1[i]) * 10000) / 100) + "%" : "<strong>" + ((isNaN(cost1[i])) ? "-" : (isNaN(Math.round(((cost[i] - cost1[i]) / cost1[i]) * 10000) / 100)) ? '-' : Math.round(((cost[i] - cost1[i]) / cost1[i]) * 10000) / 100) + "%</strong>")) + "</span></td>"
                            + "<td><span>" + ((ctr[i] == undefined) ? '-' : (isNaN(ctr[i])) ? '-' : ctr[i]) + "%</span>" + ((ctr[i] - ctr1[i] < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                            + "<span>" + ((ctr1[i] == 0) ? '-%' : ((ctr[i] - ctr1[i] >= 0) ? ((isNaN(ctr1[i])) ? "-" : (isNaN(Math.round(((ctr[i] - ctr1[i]) / ctr1[i]) * 10000) / 100)) ? '-' : Math.round(((ctr[i] - ctr1[i]) / ctr1[i]) * 10000) / 100) + "%" : "<strong>" + ((isNaN(ctr1[i])) ? "-" : (isNaN(Math.round(((ctr[i] - ctr1[i]) / ctr1[i]) * 10000) / 100)) ? '-' : Math.round(((ctr[i] - ctr1[i]) / ctr1[i]) * 10000) / 100) + "%</strong>")) + "</span></td>"
                            + "<td><span>" + ((cpc[i] == undefined) ? '-' : (isNaN(cpc[i])) ? '-' : cpc[i]) + "</span>" + ((cpc[i] - cpc1[i] < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                            + "<span>" + ((cpc1[i] == 0) ? '-%' : ((cpc[i] - cpc1[i] >= 0) ? ((isNaN(cpc1[i])) ? "-" : (isNaN(Math.round(((cpc[i] - cpc1[i]) / cpc1[i]) * 10000) / 100)) ? '-' : Math.round(((cpc[i] - cpc1[i]) / cpc1[i]) * 10000) / 100) + "%" : "<strong>" + ((isNaN(cpc1[i])) ? "-" : (isNaN(Math.round(((cpc[i] - cpc1[i]) / cpc1[i]) * 10000) / 100)) ? '-' : Math.round(((cpc[i] - cpc1[i]) / cpc1[i]) * 10000) / 100) + "%</strong>")) + "</span></td>"
                            + "<td><span>" + ((conversion[i] == undefined) ? '-' : (isNaN(conversion[i])) ? '-' : conversion[i]) + "</span>" + ((conversion[i] - conversion1[i] < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>")
                            + "<span>" + ((conversion1[i] == 0) ? '-%' : ((conversion[i] - conversion1[i] >= 0) ? ((isNaN(conversion1[i])) ? "-" : (isNaN(Math.round(((conversion[i] - conversion1[i]) / conversion1[i]) * 10000) / 100)) ? '-' : Math.round(((conversion[i] - conversion1[i]) / conversion1[i]) * 10000) / 100) + "%" : "<strong>" + ((isNaN(conversion1[i])) ? "-" : (isNaN(Math.round(((conversion[i] - conversion1[i]) / conversion1[i]) * 10000) / 100)) ? '-' : Math.round(((conversion[i] - conversion1[i]) / conversion1[i]) * 10000) / 100) + "%</strong>")) + "</span></td>"
                            + "</td><td>-</td><td>-</td></tr>";

                            html_User2 = "<tr class='list2_box2'><td>" + dateEach1[i] + "</td>"
                            + "<td>" + ((impression1[i] == undefined) ? 0 : impression1[i]) + "</td><td>" + ((click1[i] == undefined) ? 0 : click1[i]) + "</td><td>" + ((cost1[i] == undefined) ? 0 : cost1[i]) + "</td>"
                            + "<td>" + ((ctr1[i] == undefined) ? 0 : ctr1[i]) + "%</td><td>" + ((cpc1[i] == undefined) ? 0 : cpc1[i]) + "</td><td>" + ((conversion1[i] == undefined) ? 0 : conversion1[i]) + "</td><td>-</td><td>-</td></tr>"
                            + "<tr><td colspan='9'>&nbsp;</td></tr>";
                        }
                        $("#userTbody").append(html_User1);
                        $("#userTbody").append(html_User2);
                    }
                }
            }
        });
    };
    reportDataVS
    /**
     *
     * 账户基础报告
     *
     */
    var number = 0;
    var jci = 0;
    accountBasisReport = function () {
        $.ajax({
            url: "/account/accountReport",
            type: "GET",
            dataType: "json",
            data: {
                Sorted: sort,
                fieldName: fieldName,
                startJC: startJC,
                limitJC: limitJC
            },
            success: function (data) {
                var basisHtml = "";
                $("#basisAccount").empty();
                $.each(data.rows, function (i, item) {
                    number = item.count;
                    $.each(data.Ring, function (is, items) {
                        if (i % 2 == 0) {
                            basisHtml = "<tr class='list2_box1'><td>&nbsp;" + item.dateRep + "</td>"
                            + "<td><span>&nbsp;" + item.pcImpression + "</span>" + ((items.mobileImpression < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>") + "<span><strong>" + ((items.pcImpression == 0) ? '-' : items.pcImpression / 100) + "%</strong></span></td>"
                            + "<td><span>&nbsp;" + item.pcClick + "</span>" + ((items.mobileClick < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>") + "<span><strong>" + ((items.pcClick == 0) ? '-' : items.pcClick / 100) + "%</strong></span></td>"
                            + "<td><span>&nbsp;" + item.pcCost + "</span>" + ((items.mobileCost < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>") + "<span><strong>" + ((items.pcCost == 0) ? '-' : items.pcCost) + "%</strong></span></td>"
                            + "<td><span>&nbsp;" + item.pcCtr + "%</span>" + ((items.mobileCtr < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>") + "<span><strong>" + ((items.pcCtr == 0) ? '-' : items.pcCtr / 100) + "%</strong></span></td>"
                            + "<td><span>&nbsp;" + item.pcCpc + "</span>" + ((items.mobileCpc < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>") + "<span><strong>" + ((items.pcCpc == 0) ? '-' : items.pcCpc) + "%</strong></span></td>"
                            + "<td><span>&nbsp;" + item.pcConversion + "</span>" + ((items.mobileCpc < 0) ? "<span class='red_arrow wd3'></span>" : "<span class='green_arrow wd3'></span>") + "<span><strong>" + ((items.pcConversion == 0) ? '-' : items.pcConversion / 100) + "%</strong></span></td>"
                            + "<td><span>&nbsp;-</td></tr>"
                        } else {
                            basisHtml = "<tr class='list2_box2'><td>&nbsp;" + item.dateRep + "</td><td>&nbsp;" + item.pcImpression + "</td><td>&nbsp;" + item.pcClick + "</td>"
                            + "<td>&nbsp;" + Math.round(item.pcCost * 100) / 100 + "</td><td>&nbsp;" + item.pcCtr + "%</td><td>&nbsp;" + item.pcCpc + "</td><td>&nbsp;" + item.pcConversion + "</td>"
                            + "<td>&nbsp;-</td></tr>"
                        }
                    });

                    $("#basisAccount").append(basisHtml);

                });
            }
        });
    }

//曲线图
    curve = function () {
        $("#container").show();
        $("#imprDiv").empty();
        $("#clickDiv").empty();
        $("#costDiv").empty();
        $("#convDiv").empty();
        var myChart = echarts.init(document.getElementById('container'));
        var dataNew;
        if (dataOne == "") {
            dataNew = [dataTow];
        } else if (dataTow == "") {
            dataNew = [dataOne];
        } else {
            dataNew = [dataOne, dataTow];
        }
        var option = {
            tooltip: {
                trigger: 'axis'
            },
            toolbox: {
                show: true,
                feature: {
                    magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: t_date
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: nameOne,
                    scale: true,
                    axisLabel: {
                        formatter: '{value} ' + unitOne
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: colorOne
                        }
                    }
                },
                {
                    type: 'value',
                    name: nameTow,
                    scale: true,
                    axisLabel: {
                        formatter: '{value} ' + unitTow
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: colorTow
                        }
                    }
                }
            ],
            series: dataNew
        };
        myChart.setOption(option);

    };
//map
    var legenddata;
    pieChart = function (showData, showName, showId, pm_array) {
        $("#pm_data").empty();
        if (binFalg == 1) {
            var html = "<table>";
            $.each(pm_array[0], function (i, item) {
                html = html + "<tr><td class='map_number'><em></em>" + (i + 1) + "</td><td><span>" + item.name + "</span></td><td><span>" + item.value + "%</span></td></tr>"
            });
            html = html + "</table>";
            $("#pm_data").append(html);
            legenddata = eval("({ " + showName[1] + " : false," + showName[2] + " : false," + showName[3] + " : false})");
        }
        $("#" + showId).show();
        $("#pm_data").show();
        $("#container").hide();
        var myChartes = echarts.init(document.getElementById(showId));
        var optionbing = {
            tooltip: {
                trigger: 'item',
                formatter: '{b} {a}<br/>占全国百分比：{c}%'
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                selected: legenddata,
                data: [showName[0], showName[1], showName[2], showName[3]]
            },
            dataRange: {
                min: 0,
                max: 100,
                text: ['高', '低'],           // 文本，默认为数值文本
                calculable: true
            },
            toolbox: {
                show: true,
                orient: 'vertical',
                x: 'right',
                y: 'center',
                feature: {
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            series: [
                {
                    name: showName[0],
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    itemStyle: {
                        normal: {label: {show: true}},
                        emphasis: {label: {show: true}}
                    },
                    data: showData[0]
                },
                {
                    name: showName[1],
                    type: 'map',
                    mapType: 'china',
                    itemStyle: {
                        normal: {label: {show: true}},
                        emphasis: {label: {show: true}}
                    },
                    data: showData[1]
                },
                {
                    name: showName[2],
                    type: 'map',
                    mapType: 'china',
                    itemStyle: {
                        normal: {label: {show: true}},
                        emphasis: {label: {show: true}}
                    },
                    data: showData[2]
                },
                {
                    name: showName[3],
                    type: 'map',
                    mapType: 'china',
                    itemStyle: {
                        normal: {label: {show: true}},
                        emphasis: {label: {show: true}}
                    },
                    data: showData[3]
                }
            ]
        };
        binFalg++;
        myChartes.setOption(optionbing);

        // 动态添加默认不显示的数据
        myChartes.on(echarts.config.EVENT.LEGEND_SELECTED, function (param) {
            var clickName = param.target;
            $("#pm_data").empty();
            var html = "<table>";
            if (clickName == showName[0]) {
                legenddata = eval("({ " + showName[1] + " : false," + showName[2] + " : false," + showName[3] + " : false})");
                $.each(pm_array[0], function (i, item) {
                    html = html + "<tr><td class='map_number'><em></em>" + (i + 1) + "</td><td><span>" + item.name + "</span></td><td><span>" + item.value + "%</span></td></tr>"
                });
                pieChart(showData, showName, showId, pm_array);
            }
            if (clickName == showName[1]) {
                legenddata = eval("({ " + showName[0] + " : false," + showName[2] + " : false," + showName[3] + " : false})");
                $.each(pm_array[1], function (i, item) {
                    html = html + "<tr><td class='map_number'><em></em>" + (i + 1) + "</td><td><span>" + item.name + "</span></td><td><span>" + item.value + "%</span></td></tr>"
                });
                pieChart(showData, showName, showId, pm_array);
            }
            if (clickName == showName[2]) {
                legenddata = eval("({ " + showName[0] + " : false," + showName[1] + " : false," + showName[3] + " : false})");
                $.each(pm_array[2], function (i, item) {
                    html = html + "<tr><td class='map_number'><em></em>" + (i + 1) + "</td><td><span>" + item.name + "</span></td><td><span>" + item.value + "%</span></td></tr>"
                });
                pieChart(showData, showName, showId, pm_array);
            }
            if (clickName == showName[3]) {
                legenddata = eval("({ " + showName[0] + " : false," + showName[1] + " : false," + showName[2] + " : false})");
                $.each(pm_array[3], function (i, item) {
                    html = html + "<tr><td class='map_number'><em></em>" + (i + 1) + "</td><td><span>" + item.name + "</span></td><td><span>" + item.value + "%</span></td></tr>"
                });
                pieChart(showData, showName, showId, pm_array);
            }
            html = html + "</table>";
            $("#pm_data").append(html);
        });
    };


    $("body").on("click", "a[cname=nameDet]", function () {
        $(this).addClass('ajc').siblings().removeClass('ajc');
    });
    function TestBlack(TagName) {
        var obj = document.getElementById(TagName);
        if (obj.style.display == "") {
            obj.style.display = "none";
        } else {
            obj.style.display = "";
        }
    }

    var ajaxbg = $("#background,#progressBar");
    ajaxbg.hide();
    $(document).ajaxStart(function () {
        ajaxbg.show();
    })
    $(document).ajaxStop(function () {
        ajaxbg.fadeOut(1000);
    });


    selectChange = function () {
        limitJC = parseInt($("#importKeywordSel :selected").val());
        $("#pageJC").empty();
        jci = 0;
        accountBasisReport();
    }
    selectChangeVs = function () {
        limitVS = parseInt($("#importKeywordSelVS :selected").val());
        startVS = 0;
        endVs = limitVS;
        $("#pageVS").empty();
        reportDataVS();
    }
    selectChangeDet = function () {
        limitDet = parseInt($("#importKeywordSelDet :selected").val());
        startDet = 0;
        endDet = limitDet;
        $("#pageDet").empty();
        reportData();
    }

    /******************pagination*********************/
    var items_per_page = 20;    //默认每页显示20条数据
    var pageIndex = 0;
    var records = 0;
    var skip = 0;
    var typepage = 1;

    var pageSelectCallback = function (page_index, jq) {
        if (typepage == 1) {
            $("#pagination1").append("<span style='margin-right:10px;'>跳转到 <input id='anyPageNumber1' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipPage();' class='page_go'> GO</a>");
        } else {
            $("#pagination2").append("<span style='margin-right:10px;'>跳转到 <input id='anyPageNumber2' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipPage();' class='page_go'> GO</a>");
        }
        if (pageIndex == page_index) {
            return false;
        }
        pageIndex = page_index;
        if (typepage == 1) {
            startVS = (page_index + 1) * items_per_page - items_per_page;
            endVs = (page_index + 1) * items_per_page;
            reportDataVS();
        } else if (typepage == 2) {
            judety = 1;
            startDet = (page_index + 1) * items_per_page - items_per_page;
            endDet = (page_index + 1) * items_per_page;
            reportData();
        }
        return false;
    };

    var getOptionsFromForm = function (current_page) {
        if (typepage == 1) {
            items_per_page = limitVS;
        } else if (typepage == 2) {
            items_per_page = limitDet;
        }

        var opt = {callback: pageSelectCallback};
        opt["items_per_page"] = items_per_page;
        opt["current_page"] = current_page;
        opt["prev_text"] = "上一页";
        opt["next_text"] = "下一页";

        //avoid html injections
        var htmlspecialchars = {"&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;"};
        $.each(htmlspecialchars, function (k, v) {
            opt.prev_text = opt.prev_text.replace(k, v);
            opt.next_text = opt.next_text.replace(k, v);
        });
        return opt;
    };
    var optInit = getOptionsFromForm(0);

    skipPage = function () {
        var _number = 0;
        if (typepage == 1) {
            _number = $("#anyPageNumber1").val() - 1;
            if (_number <= -1 || _number == pageIndex) {
                return;
            }
            $("#pagination1").pagination(records, getOptionsFromForm(_number));
        } else {
            _number = $("#anyPageNumber2").val() - 1;
            if (_number <= -1 || _number == pageIndex) {
                return;
            }
            $("#pagination2").pagination(records, getOptionsFromForm(_number));
        }
    };
    /**********************************************************************************/

    /********************************搜索词报告******************************************/
    $("#createReport").click(function () {
        $(".list01_under3").hide();
        $(".report_search").empty();
        $(".report_search").append("<div class='example'><div id='progress2'><div id='percentNumber'></div><div class='pbar'></div><div class='elapsed'></div></div></div>");
        var isMin = 0;
        if (judety <= 0) {
            isMin = 5;
        } else {
            $("#shuju").empty();
            $("#pathImages").show();
            isMin = 1;
            judety = 0;
        }
        // from second #5 till 15
        var iNow = new Date().setTime(new Date().getTime() + 1 * 1000); // now plus 1 secs
        var iEnd = new Date().setTime(new Date().getTime() + isMin * 1000); // now plus 10 secs
        $('#progress2').anim_progressbar({start: iNow, finish: iEnd, interval: 100});
        //console.log(111);
        setTimeout(function(){
            $(".report_search").empty();
            $(".list01_under3").show();
        },6000);
        var _startDate = daterangepicker_start_date_t;
        var _endDate = daterangepicker_end_date_t;
        //var _startDate = $('.range-start').datepicker('getDate');
        //var _endDate = $('.range-end').datepicker('getDate');
        var levelOfDetails = 12;//分析层级
        var device = $("#putinInfo").val();//投放设备
        var regions = getRegionNames();//推广地域
        var dateUnit = $("#dateUnit .current").attr("cname");
        $("#searchWordTbody").html("正在联网为您获取搜索词报告...");
        $.ajax({
            url: "/assistantKeyword/getSearchWordReport",
            type: "post",
            data: {
                "levelOfDetails": levelOfDetails,
                "startDate": _startDate != null ? _startDate.Format("yyyy-MM-dd hh:mm:ss") : _startDate,
                "endDate": _endDate != null ? _endDate.Format("yyyy-MM-dd hh:mm:ss") : _endDate,
                "device": device,
                "attributes": regions,
                "status": dateUnit
            },
            dataType: "json",
            success: function (data) {
                if (data.length == 0) {
                    $("#searchWordTbody").empty();
                    $("#searchWordTbody").html("没有该范围的数据!");
                } else {
                    var basisHtml = "";
                    data.forEach(function (item, i) {
                        if (i % 2 == 0) {
                            basisHtml = basisHtml + "<tr class='list2_box1'><td>&nbsp;" + item.date + "</td>"
                            + "<td><span>&nbsp;" + item.campaignName + "</td>"
                            + "<td><span>&nbsp;" + item.adgroupName + "</td>"
                                /*+ "<td><span>&nbsp;" + item.createTitle + "</td>"
                                 + "<td><span>&nbsp;" + item.createDesc1 + "</td>"
                                 + "<td><span>&nbsp;" + item.createDesc2 + "</td>"*/
                            + "<td><span>&nbsp;" + item.searchEngine + "</td>"
                            + "<td><span>&nbsp;" + item.click + "</td>"
                            + "<td><span>&nbsp;" + item.impression + "</td>"
                            + "<td><span>&nbsp;" + item.clickRate + "</td>"
                            + "<td><span>&nbsp;" + item.searchWord + "</td>"
                            + "<td><span>&nbsp;" + item.keyword + "</td>"
                            + "<td><span>&nbsp;" + item.parseExtent + "</td>"
                            + "</tr>"
                        } else {
                            basisHtml = basisHtml + "<tr class='list2_box2'><td>&nbsp;" + item.date + "</td>"
                            + "<td><span>&nbsp;" + item.campaignName + "</td>"
                            + "<td><span>&nbsp;" + item.adgroupName + "</td>"
                                /*+ "<td><span>&nbsp;" + item.createTitle + "</td>"
                                 + "<td><span>&nbsp;" + item.createDesc1 + "</td>"
                                 + "<td><span>&nbsp;" + item.createDesc2 + "</td>"*/
                            + "<td><span>&nbsp;" + item.searchEngine + "</td>"
                            + "<td><span>&nbsp;" + item.click + "</td>"
                            + "<td><span>&nbsp;" + item.impression + "</td>"
                            + "<td><span>&nbsp;" + item.clickRate + "</td>"
                            + "<td><span>&nbsp;" + item.searchWord + "</td>"
                            + "<td><span>&nbsp;" + item.keyword + "</td>"
                            + "<td><span>&nbsp;" + item.parseExtent + "</td>"
                            + "</tr>"
                        }
                    });

                    $("#searchWordTbody").empty();
                    $("#searchWordTbody").html(basisHtml);
                    $("#downReportSearch").empty();
                    var downUrl = "date1=" + daterangepicker_start_date + "&date2=" + daterangepicker_end_date;
                    var url = "assistantKeyword/downSeachKeyWordReportCSV?levelOfDetails=" + levelOfDetails + "&startDate=" + (_startDate != null ? _startDate.Format("yyyy-MM-dd hh:mm:ss") : _startDate) +
                        "&endDate=" + (_endDate != null ? _endDate.Format("yyyy-MM-dd hh:mm:ss") : _endDate) + "&attributes = " + regions + "&device=" + device + "&status=" + dateUnit;
                    $("#downReportSearch").append("<a href='" + url + "'  class='become fl'>下载报告</a>")
                }
            }
        });
    });
    $("#putin>a").click(function () {
        $("#putin>a").removeClass("current");
        $(this).addClass("current");
        $("#putinInfo").val($(this).attr("cname"));
        $("#downReportSearch").empty();
    });
    $("#dateUnit>a").click(function () {
        $("#dateUnit>a").removeClass("current");
        $(this).addClass("current");
        $("#downReportSearch").empty();
    });
    /**
     *选中推广地域
     */
    $("#chooseRegion").click(function () {
        $("#ctrldialogplanRegionDialog").css("top", "98px")
            .css("left", ($(this).offset().left + $(this).outerWidth()) + "px");
        $("#ctrldialogplanRegionDialog").show(0);
    });

    //得到已经选中的的地域
    function getRegionNames() {
        var regions = "";
        var leaf = $("#regionList").find("div[class=leaf]");
        leaf.each(function () {
            if ($(this).find("input[type=checkbox]")[0].checked == true) {
                regions += $(this).find("label").html() + ",";
            }
        });
        return regions;
    }

    $("#ctrlbuttonregionOklabel").click(function () {
        $("#downReportSearch").empty();
    });
    /**搜索词报告*/
    function SearchClass() {
        $(".searh_report").show();
        $(".list01_under3").hide();
        $(".number_concent").css("border", "none");
        $(".number_concent").css("padding-bottom", "0px");
        $(".searchhide").hide();
    }

    $("#SearchReport").click(function () {
        SearchClass();
    });

    /**********************************************************************************/
});
