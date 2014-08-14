<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 14-7-25
  Time: 下午6:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>大数据智能营销</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">


    <style type="text/css">
        .title{
            width:100%;
            height:60px;
            background-color: #ffffff;
            border-bottom: 2px solid #CBC5D1;
        }

        .title span{
            float:left;
            margin-left: 15px;
            margin-top: 20px;
            font-size:16px;
            font-family: "微软雅黑";
            height: 67%;
            border-bottom: 2px solid gray;
        }

    </style>

</head>
<body>
<div class="top over">
    <div class="logo fl">
        <img src="${pageContext.request.contextPath}/public/img/logo.png">
    </div>
    <div class="logo_text fl">
        大数据智能营销
    </div>
</div>
<div class="concent over">
<div class="nav fl">
    <div class="nav_left over fl">
        <div class="user over">
            <div class="user_img fl">
							<span class="img">
								<img src="${pageContext.request.contextPath}/public/img/user.png">
							</span><span class="name"> JOHN DOE </span>
            </div>
            <div class="user_close fr">
                <a href="#">
                    退出
                </a>
            </div>
        </div>
        <div class="nav_mid">
            <div class="nav_top">
                帐户全景<span></span>
            </div>
            <div class="nav_under over">
                <ul>
                    <li>
                        <h3>推广助手</h3>
                    </li>
                    <li>
                        <span class="list1"></span>
                        <a href="#">
                            账户预警
                        </a>
                    </li>
                    <li>
                        <span class="list2"></span>
                        <a href="#">
                            批量上传
                        </a>
                    </li>
                    <li>
                        <span class="list3"></span>
                        <a href="#">
                            批量操作
                        </a>
                    </li>
                    <li>
                        <span class="list4"></span>
                        <a href="#">
                            关键词查找
                        </a>
                    </li>
                    <li>
                        <span class="list5"></span>
                        <a href="#">
                            推广查询
                        </a>
                    </li>
                    <li>
                        <h3>智能结构</h3>
                    </li>
                    <li>
                        <span class="list6"></span>
                        <a href="#">
                            关键词拓展
                        </a>
                    </li>
                    <li>
                        <span class="list7"></span>
                        <a href="#">
                            智能分组
                        </a>
                    </li>
                    <li>
                        <h3>智能竞价</h3>
                    </li>
                    <li>
                        <span class="list8"></span>
                        <a href="#">
                            诊断关键词
                        </a>
                    </li>
                    <li>
                        <span class="list9"></span>
                        <a href="#">
                            筛选关键词
                        </a>
                    </li>
                    <li>
                        <span class="list10"></span>
                        <a href="#">
                            设置规则
                        </a>
                    </li>
                    <li>
                        <h3>数据报告</h3>
                    </li>
                    <li>
                        <span class="list11"></span>
                        <a href="#">
                            基础报告
                        </a>
                    </li>
                    <li>
                        <span class="list12"></span>
                        <a href="#">
                            定制报告
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="tips fl">
        <input type="image" src="${pageContext.request.contextPath}/public/img/button2.png">
    </div>
</div>

<div class="mid over fr">
<div class="on_title over">
    <a href="#">
        推广助手
    </a>
    &nbsp;&nbsp;>&nbsp;&nbsp;<span>账户预警</span>
</div>

    <div class="title"><span>消费提醒设置</span></div>
    <div><br/>
        <form action="/assistant/saveWarningRule" method="get">
        <span>百度账户</span>
            <select name = "accountId">
                <c:forEach items="${list}" var="va">
                <option value="<c:out value="${va.id}"/>"><c:out value="${va.baiduUserName}"/></option>
                </c:forEach>
            </select><br/><br/>

        <span>账户预算类型</span>
        <select name = "budgetType">
            <option value="0">不设置预算</option>
            <option value="1">日预算</option>
            <option value="2">周预算</option>
        </select><br/><br/>

        <span>预算金额</span><input type="text" name="budget" /><br/><br/>
        <span>预警百分率</span><input type="text" name="warningPercent" /><br/><br/>
        <span>邮件</span><input type="text" name = "mails"/><br/><br/>
        <span>手机</span><input type="text" name = "tels"/><br/><br/>
        <span>是否启用</span> <select name = "isEnable">
                                    <option value="0">不启用</option>
                                    <option value="1">启用</option>
                                </select><br/><br/>
        <input type="submit" value="确定" />
        </form>
    </div>












<div class="footer">
    <p>
        <a href="#">
            关于我们
        </a>
        |
        <a href="#">
            联系我们
        </a>
        |
        <a href="#">
            隐私条款
        </a>
        |
        <a href="#">
            诚聘英才
        </a>
    </p>

    <p>
        Copyright@2013 perfect-cn.cn All Copyright Reserved. 版权所有：北京普菲特广告有限公司京ICP备***号
    </p>
</div>
</div>
</div>

<!-- javascript -->
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-ui-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.jQuery.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/4.0.1/highcharts.js"></script>
<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/4.0.1/modules/exporting.js"></script>
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
<script type="text/javascript">

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

    $(function () {
        var $tab_li = $('.tab_menu li');
        $('.tab_menu li').click(function () {
            $(this).addClass('selected').siblings().removeClass('selected');
            var index = $tab_li.index(this);
            $('div.tab_box > div').eq(index).show().siblings().hide();
        });

        var navH = $(".nav").offset().top;
        $(window).scroll(function () {
            var scroH = $(this).scrollTop();
            if (scroH >= navH) {
                $(".nav").css({
                    "position": "fixed",
                    "top": "0"
                });
            } else {
                $(".nav").css({
                    "position": "static",
                    "margin": "0 auto"
                });
            }
            console.log(scroH == navH);
        });

        //加载日历控件
        $("input[name=reservation]").daterangepicker();
        $(".btnDone").on('click', function () {
            var _startDate = $('.range-start').datepicker('getDate');
            var _endDate = $('.range-end').datepicker('getDate');
            if (_startDate != null && _endDate != null) {
                daterangepicker_start_date = _startDate.Format("yyyy-MM-dd");
                daterangepicker_end_date = _endDate.Format("yyyy-MM-dd");
                if (genre == "keywordQualityCustom") {
                    //区分当前展示的是昨天(1), 近7天(7), 近30天(30), 还是自定义日期(0)的数据
                    loadKeywordQualityData(null, 0);
                } else if (genre == "importKeywordDefault") {
                    getImportKeywordDefault(null, 0);
                } else if (genre == "accountOverview") {
                    lisClick();
                } else if (genre == "importPerformanceDefault") {
                    category = "data";
                    loadPerformance(0);
                } else if (genre == "importPerformanceCurveDefault") {
                    category = "data";
                    loadPerformanceCurve(0);
                }
            }
        });

        //默认加载昨天的数据
        loadKeywordQualityData(null, 1);
        getImportKeywordDefault(1);
        //账户表现-----默认加载7天数据
        loadPerformance(7);
        //曲线图表现-----默认加载7天数据
        loadPerformanceCurve(7);

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

    //关键词质量度数据加载
    var loadKeywordQualityData = function (obj, param) {
        if (obj != null) {
            changedLiState(obj);
        }
        statDate = param;
        getDateParam(param);
        $.ajax({
            url: "/keywordQuality/list",
            type: "GET",
            dataType: "json",
            data: {
                startDate: daterangepicker_start_date,
                endDate: daterangepicker_end_date,
                fieldName: category,
                sort: sort,
                limit: limit
            },
            success: function (data, textStatus, jqXHR) {
                $("#keywordQuality1").empty();
                if (data.rows.length > 0) {
                    $.each(data.rows, function (i, item) {
                        var _div = "<div><ul><li></li><li><span>" + item.keywordName + "</span><span class='green_arrow wd3'></span></li>" +
                                "<li>" + item.impression + "</li><li>" + item.click + "</li><li>" + item.ctr + "%</li><li>" + item.cost + "</li>" +
                                "<li>" + item.cpc + "</li><li>" + item.conversion + "</li></ul></div>";
                        $("#keywordQuality1").append(_div);
                    })
                }
            }
        });
    };

    /**
     * 分日表现数据加载
     * */
    var loadPerformance = function (date) {
        getDateParam(date);
        $.ajax({
            url: "/account/getPerformanceUser",
            type: "GET",
            dataType: "json",
            data: {
                startDate: daterangepicker_start_date,
                endDate: daterangepicker_end_date,
                fieldName: category,
                sort: sort,
                limit: limit
            },
            success: function (data) {
                var calssStr = "";
                if (data.rows.length > 0) {
                    $("#performance").empty();
                    $.each(data.rows, function (i, item) {
                        if (i % 2 == 0) {
                            calssStr = "list2_box1";
                        } else {
                            calssStr = "list2_box2";
                        }
                        var _div = "<tr class=" + calssStr + "><td><ul><li> &nbsp;" + item.date + "</li><li> &nbsp;" + item.impression + "</li><li> &nbsp;" + item.click + "</li><li> &nbsp;" + item.cost + "</li><li> &nbsp;" + item.ctr + "%</li>"
                                + "<li> &nbsp;" + item.cpc + "</li><li> &nbsp;" + item.conversion + "</li></ul></td></tr>";
                        $("#performance").append(_div);
                    })
                }
            }
        });
    };
</script>
<script>
/**
 * 曲线图数据配置
 * **/
var loadPerformanceCurve = function (date) {
    getDateParam(date);
    //日期
    var t_date = new Array();
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

    $.ajax({
        url: "/account/getPerformanceCurve",
        type: "GET",
        dataType: "json",
        cache: false,
        async: false,
        data: {
            startDate: daterangepicker_start_date,
            endDate: daterangepicker_end_date
        },
        success: function (data) {
            if (data.rows.length > 0) {
                $.each(data.rows, function (i, item) {
                    t_date.push(item.date);
                    t_impr.push(item.impression);
                    t_clicks.push(item.click);
                    t_cost.push(item.cost);
                    t_ctr.push(item.ctr);
                    t_cpc.push(item.cpc);
                    t_conversion.push(item.conversion);
                })
            }
        }
    });
    $('#container').highcharts({
        chart: {
            zoomType: 'xy'
        },
        title: {
            text: ' d'
        },
        subtitle: {
            text: ' d'
        },
        xAxis: [
            {
                categories: t_date
            }
        ],
        yAxis: [
            { // Primary yAxis
                labels: {
                    format: '{value}°C',
                    style: {
                        color: '#89A54E'
                    }
                },
                title: {
                    text: 'Temperature',
                    style: {
                        color: '#89A54E'
                    }
                }
            },
            { // Secondary yAxis
                title: {
                    text: 'Rainfall',
                    style: {
                        color: '#4572A7'
                    }
                },
                labels: {
                    format: '{value} mm',
                    style: {
                        color: '#4572A7'
                    }
                },
                opposite: true
            }
        ],
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
            borderRadius: 5

        },
        series: [
            {
                name: '展现',
                color: '#4572A7',
                type: 'spline',
                data: t_impr,
                tooltip: {
                    valueSuffix: ' 次'
                }

            },
            {
                name: '点击',
                color: '#89A54E',
                type: 'spline',
                data: t_clicks,
                tooltip: {
                    valueSuffix: '次'
                }
            },
            {
                name: '消费',
                color: '#ED561B',
                type: 'spline',
                data: t_cost,
                tooltip: {
                    valueSuffix: '￥'
                }
            },
            {
                name: '点击率',
                color: '#24CBE5',
                type: 'spline',
                data: t_ctr,
                tooltip: {
                    valueSuffix: '%'
                }
            },
            {
                name: '平均点击价格',
                color: '#64E572',
                type: 'spline',
                data: t_cpc,
                tooltip: {
                    valueSuffix: '￥'
                }
            },
            {
                name: '转化',
                color: '#DDDF00',
                type: 'spline',
                data: t_conversion
            }
        ]
    });
}


var getImportKeywordDefault = function (obj, day) {
    if (obj != null) {
        changedLiState(obj);
    }
    $("#importTr").empty();
    $("#importTr").append("<td style='color:red;'>加载中....</td>");
    statDate = day;
    getDateParam(day);
    $.ajax({
        url: "/main/getImportKeywordList",
        type: "GET",
        dataType: "json",
        data: {
            startDate: daterangepicker_start_date,
            endDate: daterangepicker_end_date,
            limit: limit,
            sort: sort
        },
        success: function (data, textStatus, jqXHR) {
            var calssStr = "";
            var _tr = $("#importTr");
            if (data.length > 0) {
                _tr.empty();
                $.each(data, function (i, item) {
                    if (i % 2 == 0) {
                        calssStr = "list2_box1";
                    } else {
                        calssStr = "list2_box2";
                    }
                    var _div = "<tr class=" + calssStr + "><td><ul><li> &nbsp;" + item.keywordName + "</li><li> &nbsp;" + item.impression + "</li><li> &nbsp;" + item.click + "</li><li> &nbsp;￥" + item.cost + "</li><li> &nbsp;￥" + item.cpc + "</li>"
                            + "<li> &nbsp;" + item.ctr * 100 + "%</li><li> &nbsp;" + item.conversion + "</li><li> &nbsp;" + item.position + "</li></ul></td></tr>";
                    $("#importTr").append(_div);

                })
            } else {
                _tr.empty();
                var _div = "<td colspan='9' style='color:royalblue;'>暂无数据哈</td>"
                _tr.append(_div);
            }
        }
    });
};


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
lisClick($(".current").get(1), 1);//默认显示昨天的汇总数据

</script>

</body>
</html>