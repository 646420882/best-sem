/**
 * Created by john on 2015/9/21.
 */
//默认按照展现进行排名
var category = "impression";

//默认降序排列
var sort = -1;

//默认加载前10条数据
var limit = 10;

//日期控件-开始日期
var daterangepicker_start_date = null;

//日期控件-结束日期
var daterangepicker_end_date = null;

//区分当前展示的是昨天(1), 近7天(7), 近30天(30), 还是自定义日期(0)的数据
var statDate = 1;

//查询类别区分
var genre = "";

//日期控件坐标定位
var _posX = 0, _posY = 0;
var clickdddd = null;

$(function () {
    //数据初始化
    loadPerformanceCurve(this, 7);
    //
    var $tab_li = $('.tab_menu li');
    $('.tab_menu li').click(function () {
        $(this).addClass('selected').siblings().removeClass('selected');
        var index = $tab_li.index(this);
        typepage = index + 1;
        switch (typepage) {
            case 2:
                //曲线图表现-----默认加载7天数据
                loadPerformanceCurve(null, 7);
                break;
            case 3:
                //默认加载昨天的数据(质量度)
//                loadKeywordQualityData(null, 1);
                break;
            case 4:
                //重点词加载
                getImportKeywordDefault(null, 1);
                break;
        }
        $('div.tab_box > div').eq(index).show().siblings().hide();
    });
    $("input[name=reservation]").click(function () {
        clickdddd = $(this);
    });
    //加载日历控件
    //$("input[name=reservation]").daterangepicker();
    //$(".btnDone").on('click', function () {
    //    var _startDate = $('.range-start').datepicker('getDate');
    //    var _endDate = $('.range-end').datepicker('getDate');
    //    if (_startDate != null && _endDate != null) {
    //        if (_startDate > _endDate) {
    //            return false;
    //        }
    //        statDate = 0;
    //        daterangepicker_start_date = _startDate.Format("yyyy-MM-dd");
    //        daterangepicker_end_date = _endDate.Format("yyyy-MM-dd");
    //        if (genre == "keywordQualityCustom") {
    //            //区分当前展示的是昨天(1), 近7天(7), 近30天(30), 还是自定义日期(0)的数据
    //            loadKeywordQualityData();
    //        } else if (genre == "importKeywordDefault") {
    //            getImportKeywordDefault(null, 0);
    //        } else if (genre == "accountOverview") {
    //            lisClick();
    //        } else if (genre == "importPerformanceDefault") {
    //            category = "data";
    //            loadPerformance(0);
    //        } else if (genre == "importPerformanceCurveDefault") {
    //            category = "data";
    //            loadPerformanceCurve(0);
    //        }
    //
    //        showDate();
    //    }
    //});

    document.getElementById("background").style.display = "none";
    document.getElementById("progressBar1").style.display = "none";


    //账户表现-----默认加载7天数据
    loadPerformance(null, 7);

    //关键词质量度分析异步加载
    loadKeywordQualityData();

});

function TestBlack(TagName) {
    var obj = document.getElementById(TagName);
    if (obj.style.display == "") {
        obj.style.display = "none";
    } else {
        obj.style.display = "";
    }
}
function selectChange() {
    limit = $("#importKeywordSel :selected").val();
    getImportKeywordDefault(null, statDate);
}

/**
 * 获取昨天, 近七天, 近30天日期
 * 参数为1, 昨天
 * 参数为7, 7天
 * 参数为30, 近30天
 * @param day
 */
var getDateParam = function (day) {
    var currDate = new Date();
    if (day == 1) {
        currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24);
        daterangepicker_start_date = currDate.Format("yyyy-MM-dd");
        daterangepicker_end_date = daterangepicker_start_date;
    } else if (day == 7) {
        currDate = new Date();
        currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24);
        daterangepicker_end_date = currDate.Format("yyyy-MM-dd");
        currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24 * 6);
        daterangepicker_start_date = currDate.Format("yyyy-MM-dd");
    } else if (day == 30) {
        currDate = new Date();
        currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24);
        daterangepicker_end_date = currDate.Format("yyyy-MM-dd");
        currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24 * 29);
        daterangepicker_start_date = currDate.Format("yyyy-MM-dd");
    }
};

var judgeDet = 0;
var perCount = 0;
var startPer = 0;
var pageDetNumber = 0;
var endPer = 10;
var limitPer = 20;
var skipPagePer;
/**
 * 分日表现数据加载
 * */
var loadPerformance = function (obj, date) {
    if (obj != null) {
        changedLiState(obj);
    }
    getDateParam(date);
    $.ajax({
        url: "/account/getPerformanceUser",
        type: "GET",
        dataType: "json",
        data: {
            startDate: daterangepicker_start_date,
            endDate: daterangepicker_end_date,
            sort: sort,
            limit: endPer,
            startPer: startPer
        },
        success: function (data) {
            var calssStr = "";
            if (data.rows.length > 0) {
                $("#performance").empty();
                $.each(data.rows, function (i, item) {
                    pageDetNumber = item.count;
                    if (i % 2 == 0) {
                        calssStr = "list2_box1";
                    } else {
                        calssStr = "list2_box2";
                    }
                    var _div = "<tr class=" + calssStr + "><td><ul><li> &nbsp;" + item.date + "</li><li> &nbsp;" + item.pcImpression + "</li><li> &nbsp;" + item.pcClick + "</li><li> &nbsp;" + Math.round(item.pcCost * 100) / 100 + "</li><li> &nbsp;" + Math.round(item.pcCtr * 100) / 100 + "%</li>"
                        + "<li> &nbsp;" + Math.round(item.pcCpc * 100) / 100 + "</li><li> &nbsp;" + item.pcConversion + "</li></ul></td></tr>";
                    $("#performance").append(_div);
                });
                records = pageDetNumber;
                typepage = 1;
                $("#pagination1").pagination(pageDetNumber, getOptionsFromForm(pageIndex));
            }
        }
    });
};
/******************pagination*********************/
var items_per_page = 10;    //默认每页显示20条数据
var limitPer = 10;
var pageIndex = 0;
var records = 0;
var skip = 0;
var typepage = 1;
var nowPage = 0;

var pageSelectCallback = function (page_index, jq) {
    if (typepage == 1) {
        $("#pagination1").append("<span style='margin-right:10px;'>跳转到 <input id='anyPageNumber1' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipPagePer();' class='page_go'> GO</a>");
    } else if (typepage == 2) {
        $("#pagination2").append("<span style='margin-right:10px;'>跳转到 <input id='anyPageNumber2' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipPagePer();' class='page_go'> GO</a>");
    } else if (typepage == 4) {
        $("#importPager").append("<span style='margin-right:10px;'>跳转到 <input id='anyPageNumber4' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipPagePer();' class='page_go'> GO</a>");

    }
    if (pageIndex == page_index) {
        return false;
    }
    pageIndex = page_index;
    if (typepage == 1) {
        startPer = (page_index + 1) * items_per_page - items_per_page;
        endPer = (page_index + 1) * items_per_page;
        loadPerformance();
    } else if (typepage == 2) {
        judety = 1;
        startPer = (page_index + 1) * items_per_page - items_per_page;
        endPer = (page_index + 1) * items_per_page;
        loadPerformance();
    } else if (typepage == 4) {

    }
    return false;
};

var getOptionsFromForm = function (current_page) {
    if (typepage == 1) {
        items_per_page = limitPer;
    } else if (typepage == 2) {
        items_per_page = limitPer;
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

skipPagePer = function () {
    var _number = 0;
    if (typepage == 1) {
        _number = $("#anyPageNumber1").val() - 1;
        if (_number <= -1 || _number == pageIndex) {
            return;
        }
        $("#pagination1").pagination(records, getOptionsFromForm(_number));
    } else if (typepage == 2) {
        _number = $("#anyPageNumber2").val() - 1;
        if (_number <= -1 || _number == pageIndex) {
            return;
        }
        $("#pagination2").pagination(records, getOptionsFromForm(_number));
    } else if (typepage == 4) {
        _number = $("#anyPageNumber4").val();
        $("#importPager").pagination(records, getOptionsFromForm(/^\d+$/.test(_number) == false ? 0 : parseInt(_number) - 1));
    }
};
/**********************************************************************************/

/**初始化曲线图共用变量**/
/*初始化数据变量*/
var goLi = 0;
var dataOne = "";
var dataTow = "";
//日期
var t_date = [];
var dateInterval = 0;
var colorOne = "#4572A7";
var colorTow = "#40BC2A";
var nameOne = "展现";
var nameTow = "点击";
var unitOne = "次";
var unitTow = "次"
/**初始化结束**/
/**
 * 曲线图数据配置
 * **/
var loadPerformanceCurve = function (obj, date) {
    if (obj != null) {
        changedLiState(obj);
    }
    colorOne = "#4572A7";
    colorTow = "#40BC2A";
    nameOne = "展现";
    nameTow = "点击";
    unitOne = "次";
    unitTow = "次"
    $("#containerLegend").empty();
    /*初始化曲线图所用需求*/
    $("#containerLegend").append("<div class='tu_top over'><ul><li>展示曲线</li>"
        + "<li><input name='check' cname='impr' xname='' type='checkbox' checked='checked'><span class='blue' ></span><b>展现</b></li>"
        + "<li><input name='check' cname='clicks' xname='' type='checkbox' checked='checked'><span class='green'></span><b>点击</b></li>"
        + "<li><input name='check' cname='cost' xname='' type='checkbox'><span class='red'></span><b>消费</b></li>"
        + "<li><input name='check' cname='ctr' xname='' type='checkbox'><span class='blue2'></span><b>点击率</b></li>"
        + "<li><input name='check' cname='cpc' xname='' type='checkbox'><span class='green2'></span><b>平均点击价格</b></li>"
        + "<li><input name='check' cname='conv' xname='' type='checkbox'><span class='yellow'></span><b>转化</b></li><li><b style='color: red'>最多只能同时选择两项</b></li></ul></div>");

    getDateParam(date);
    //展现
    var t_impr = [];
    //点击数
    var t_clicks = [];
    //消费
    var t_cost = [];
    //点击率
    var t_ctr = [];
    //平均点击价格
    var t_cpc = [];
    //转化
    var t_conversion = [];

    $.ajax({
        url: "/account/getPerformanceCurve",
        type: "GET",
        dataType: "json",
        async: false,
        data: {
            startDate: daterangepicker_start_date,
            endDate: daterangepicker_end_date
        },
        success: function (data) {
            t_date.length = 0;
            t_impr.length = 0;
            t_clicks.length = 0;
            t_cost.length = 0;
            t_ctr.length = 0;
            t_cpc.length = 0;
            t_conversion.length = 0;
            if (data.rows.length > 0) {
                $.each(data.rows, function (i, item) {
                    t_date.push(item.date);
                    t_impr.push(item.pcImpression);
                    t_clicks.push(item.pcClick);
                    t_cost.push(Math.round(item.pcCost * 100) / 100);
                    t_ctr.push(Math.round(item.pcCtr * 100) / 100);
                    t_cpc.push(Math.round(item.pcCpc * 100) / 100);
                    t_conversion.push(item.pcConversion);
                })
            }
            if (data.rows.length > 10) {
                dateInterval = Math.round(data.rows.length / 10);
            }
        }
    });

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

    $("input[name=check]").click(function () {
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
    setTimeout("curve()", 100);
};
$("#liClick").click(function () {
    setTimeout("curve()", 100);
});
var curve = function () {
    var myChart = echarts.init(document.getElementById('container'));
    window.onresize = myChart.resize;
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


    /*$('#container').highcharts({
     chart: {
     zoomType: 'xy'
     },
     title: {
     text: ''
     },
     subtitle: {
     text: ''
     },
     exporting: {
     filename: 'Graph_Chart',
     buttons: {
     contextButton: {
     symbol: 'url(/public/images/reportDown.jpg)',
     menuItems:[{
     text:'导出 JPEG图片',
     onclick:function() {
     this.exportChart({
     type:'image/jpeg'
     });
     }
     },{
     text:'导出 PNG图片',
     onclick:function() {
     this.exportChart({
     type:'image/png'
     });
     }
     },{
     text:'导出 PDF',
     onclick:function() {
     this.exportChart({
     type:'application/pdf'
     });
     }
     },{
     text:'导出 SVG',
     onclick:function() {
     this.exportChart({
     type:'svg'
     });
     }
     }
     ]
     }
     }
     },
     xAxis: {
     categories: t_date,
     tickInterval: (dateInterval)// 每个间隔
     },
     yAxis: [
     { // Primary yAxis
     title: {
     text: nameTow,
     margin: 30,
     style:{'color':colorTow,'font-size':'16px','font-family': '宋体','font-weight': 'bold'}
     },
     labels: {
     format: '{value}',
     style: {
     color: colorTow
     }
     }
     },
     { // Secondary yAxis
     title: {
     text: nameOne,
     margin: 30,
     style:{'color':colorOne,'font-size':'16px','font-family': '宋体','font-weight': 'bold'}
     },
     labels: {
     format: '{value}',
     style: {
     color: colorOne
     }
     },
     opposite: true
     }
     ],
     credits: {
     enabled: false
     },
     tooltip: {
     shared: true
     },
     legend: {
     align: 'left',
     x: 10,
     verticalAlign: 'top',
     y: -10,
     floating: true,
     backgroundColor: '#FFFFFF',
     itemDistance: 20,
     borderRadius: 5,
     enabled: false
     },
     series: [
     dataOne,
     dataTow
     ]
     });*/
};

var getImportKeywordDefault = function (obj, day) {
    if (obj != null) {
        changedLiState(obj);
    }
    $("#importTr").empty();
    $("#importTr").append("<td style='color:red;'>加载中....</td>");
    statDate = day;
    getDateParam(day);
    var _tr = $("#importTr");
    $.post("/import/getImportKeywordList", {
        startDate: daterangepicker_start_date,
        endDate: daterangepicker_end_date,
        pageSize: limit,
        sort: sort,
        nowPage: nowPage
    }, function (result) {
        var gson = $.parseJSON(result);
        $("#importPager").pagination(gson.totaCount, getOptionsFromForm(gson.pageNo));
        if (gson.list != undefined) {
            _tr.empty();
            for (var i = 0; i < gson.list.length; i++) {
                var calssStr = i % 2 != 0 ? "list2_box2" : "list2_box1";
                var item = gson.list[i];
                var _div = "<tr class=" + calssStr + "><td><ul><li> &nbsp;" + item.keywordName + "</li><li> &nbsp;" + item.pcImpression + "</li><li> &nbsp;" + item.pcClick + "</li><li> &nbsp;￥" + item.pcCost + "</li><li> &nbsp;￥" + item.pcCpc + "</li>"
                    + "<li> &nbsp;" + item.pcCtr * 100 + "%</li><li class='home_quality'> &nbsp;" + item.pcConversion + "</li><li> &nbsp;" + item.pcPosition + "</li><li> &nbsp;" + item.quality + "</li><li>&nbsp;" + until.getMatchTypeName(item.matchType) + "</li></ul></td></tr>";
                $("#importTr").append(_div);
            }
        } else {
            _tr.empty();
            var _div = "<td colspan='9' style='color:royalblue;'>暂无数据...</td>";
            _tr.append(_div);
        }
    });
};
//初始化加载下载功能
function importDownload(rs) {
    window.open("/import/getCSV?startDate=" + daterangepicker_start_date + "&&endDate=" + daterangepicker_end_date);
}


//根据最近几天获取数据
function lisClick(obj, days) {
    if (days != null) {
        getDateParam(days);
    }
    htmlLoding();
    getData();
    changedLiState(obj);
}

//改变li的样式状态
function changedLiState(obj) {
    $(obj).parent().parent().find("li").each(function () {
        $(this).removeClass("current");
    });
    $(obj).parent().addClass("current");
}

//数据获取中。。。
function htmlLoding() {
    $(".impression,.click,.cos,.conversion").html("加载中...");
}
htmlLoding();
//获取数据
function getData() {
    $.ajax({
        url: "/account/getAccountOverviewData",
        type: "get",
        dataType: "json",
        data: {"startDate": daterangepicker_start_date, "endDate": daterangepicker_end_date},
        success: function (data) {
            $(".impression").html(data.impression);
            $(".click").html(data.click);
            $(".cos").html(data.cos);
            $(".conversion").html(data.conversion);
        }

    });
}

//初始化账户概览页面数据
lisClick($("#clickLis .current>a"), 1);//默认显示昨天的汇总数据


var showDate = function () {
    $(".datebox").show();
    clickdddd.parent().parent().parent().parent().next().text("当前时间范围：" + daterangepicker_start_date + " 至 " + daterangepicker_end_date);
    $(".list01_top ul li").click(function () {
        $(".datebox").hide();
    });
};
$(function () {
    $("[data-toggle='tooltip']").tooltip();
});