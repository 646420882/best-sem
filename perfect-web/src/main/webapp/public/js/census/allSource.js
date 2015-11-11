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

var start_date, end_date, date_type=1, dateclicks;
var findType = 0;//0：全部来源  1：搜索引擎  2：搜索词   3：外部链接
var accessType = 0; //访问类型  0：全部     1：计算机    2：移动设备
var userTypes = 0; //访问用户类型  0：全部   1：新访客   2：老访客

//加载日历控件
$("input[name=reservation]").daterangepicker();

$("input[cname=dateClick]").click(function () {
    dateclicks = $(this);
});

/**
 * 获取今天, 近七天, 近30天日期
 * 参数为 -1， 昨天时间
 * 参数为  1, 今天时间
 * 参数为  7, 7天时间
 * 参数为 30, 近30天时间
 * @param day
 */
var getDateParam = function (day) {
    //alert(day)
    AlertPrompt.show(day)
    var createDate = new Date();
    switch (day){
        case -1:
            createDate.setTime(createDate.getTime() - 1000 * 60 * 60 *24);
            start_date = createDate.Format("yyyy-MM-dd");
            end_date = start_date;
            break;
        case 1:
            createDate = new Date();
            createDate.setTime(createDate.getTime());
            start_date = createDate.Format("yyyy-MM-dd");
            end_date = start_date;
            break
        case 7:
            createDate = new Date();
            createDate.setTime(createDate.getTime() - 1000 * 60 * 60 * 24);
            end_date = createDate.Format("yyyy-MM-dd");
            createDate.setTime(createDate.getTime() - 1000 * 60 * 60 * 24 * 6);
            start_date = createDate.Format("yyyy-MM-dd");
            break;
        case 30:
            createDate = new Date();
            createDate.setTime(createDate.getTime() - 1000 * 60 * 60 * 24);
            end_date = createDate.Format("yyyy-MM-dd");
            createDate.setTime(createDate.getTime() - 1000 * 60 * 60 * 24 * 29);
            start_date = createDate.Format("yyyy-MM-dd");
            break;
    }
};

var getData = function () {
    getDateParam(date_type);
    $.ajax({
        url: "/pftstis/getAllSource",
        type: "GET",
        dataType: "json",
        data: {
            startDate: start_date,
            endDate:end_date,
            findType:findType,
            accessType:accessType,
            allTypes:userTypes
        },
        success: function (data) {
            var head_hmtl;
            $("#theadData").empty();
            $.each(data.rows, function (i, item) {

                //头数据
                $.each(item.head, function (i, itemHead) {
                    $("#head_Div_data").empty();
                    switch (allTypes) {
                        case 1:
                            var html = "<div class='col-xs-1' style='margin-left: 40px;'>浏览量占比<br><label>" + itemHead.lllzb + "</label></div>" +
                                "<div class='col-xs-1'>访问次数<br><label>" + itemHead.fwcs + "</label></div>" +
                                "<div class='col-xs-1'>新访客数<br><label>" + itemHead.xfks + "</label></div>" +
                                "<div class='col-xs-1'>新访客比率<br><label>" + itemHead.xfkbl + "</label></div>" +
                                "<div class='col-xs-1'>IP数<br><label>" + itemHead.ips + "</label></div>" +
                                "<div class='col-xs-1'>跳出率<br><label>" + itemHead.tcl + "</label></div>";
                            break;
                        case 2:
                            var html = "<div class='col-xs-1' style='margin-left: 40px;'>浏览量(PV)<br><label>" + itemHead.lllzb + "</label></div>" +
                                "<div class='col-xs-1'>浏览量占比<br><label>" + itemHead.fwcs + "</label></div>" +
                                "<div class='col-xs-1'>访问次数<br><label>" + itemHead.xfks + "</label></div>" +
                                "<div class='col-xs-1'>访客数(UV)<br><label>" + itemHead.xfkbl + "</label></div>" +
                                "<div class='col-xs-1'>IP数<br><label>" + itemHead.ips + "</label></div>" +
                                "<div class='col-xs-1'>跳出率<br><label>" + itemHead.tcl + "</label></div>";
                            break;
                        case 3:
                            var html = "<div class='col-xs-1' style='margin-left: 40px;'>浏览量占比<br><label>" + itemHead.lllzb + "</label></div>" +
                                "<div class='col-xs-1'>新访客比率<br><label>" + itemHead.fwcs + "</label></div>" +
                                "<div class='col-xs-1'>跳出率<br><label>" + itemHead.xfks + "</label></div>" +
                                "<div class='col-xs-1'>平均访问时长<br><label>" + itemHead.xfkbl + "</label></div>" +
                                "<div class='col-xs-1'>转化次数<br><label>" + itemHead.ips + "</label></div>" +
                                "<div class='col-xs-1'>转化率<br><label>" + itemHead.tcl + "</label></div>";
                            break;
                        case 4:
                            var html = "<div class='col-xs-1' style='margin-left: 40px;'>浏览量占比<br><label>" + itemHead.lllzb + "</label></div>" +
                                "<div class='col-xs-1'>访问次数<br><label>" + itemHead.fwcs + "</label></div>" +
                                "<div class='col-xs-1'>访客数(UV)<br><label>" + itemHead.xfks + "</label></div>" +
                                "<div class='col-xs-1'>订单数<br><label>" + itemHead.xfkbl + "</label></div>" +
                                "<div class='col-xs-1'>订单金额<br><label>" + itemHead.ips + "</label></div>" +
                                "<div class='col-xs-1'>订单转化率<br><label>" + itemHead.tcl + "</label></div>";
                            break;
                    }


                    $("#head_Div_data").append(html)
                });
                $("#tbodyData").empty();

                //table数据
                $.each(item.tableData, function (i, itemTable) {

                    switch (allTypes){
                        case 1:
                            head_hmtl="<tr><td></td><td></td><td>来源类型</td><td>浏览量占比</td><td>访问次数</td><td>新访客数</td><td>新访客比率</td><td>IP数</td><td>跳出率</td></tr>";
                            var html = "<tr><td><a href='javascript:' onclick='showOrhideForNextNode(this);' cnameMan="+i+">+</a></td>" +
                                "<td>" + i + "</td>" +
                                "<td>" + itemTable.lylx + "</td>" +
                                "<td>" + itemTable.llbl + "</td>" +
                                "<td>" + itemTable.fwcs + "</td>" +
                                "<td>" + itemTable.fwks + "</td>" +
                                "<td>" + itemTable.xfkbl + "</td>" +
                                "<td>" + itemTable.ips + "</td>" +
                                "<td>" + itemTable.tcl + "</td></tr>";
                            var numberx = i;
                            var exphtml="";
                            $.each(item.expansion,function(i,expansions){
                                $.each(expansions[itemTable.id],function(i,expData){
                                    exphtml= exphtml +"<tr class='display' number"+numberx+"><td></td><td></td>"+
                                        "<td>"+expData.lylx+"</td>" +
                                        "<td>"+expData.llbl+"</td>" +
                                        "<td>"+expData.fwcs+"</td>" +
                                        "<td>"+expData.fwks+"</td>" +
                                        "<td>"+expData.xfkbl+"</td>" +
                                        "<td>"+expData.ips+"</td>" +
                                        "<td>"+expData.tcl+"</td></tr>";
                                })
                            })
                            html=html+exphtml;
                            break;
                        case 2:
                            head_hmtl="<tr><td></td><td></td><td>搜索引擎</td><td>浏览量(PV)</td><td>浏览量占比</td><td>访问次数</td><td>访客数(UV)</td><td>IP数</td><td>跳出率</td></tr>";
                            break;
                        case 3:
                            head_hmtl="<tr><td></td><td></td><td>搜索词</td><td>浏览量占比</td><td>新访客比率</td><td>跳出率</td><td>平均访问时长</td><td>转化次数</td><td>转化率</td></tr>";
                            break;
                        case 4:
                            head_hmtl="<tr><td></td><td></td><td>外部链接</td><td>浏览量占比</td><td>访问次数</td><td>访客数(UV)</td><td>订单数</td><td>订单金额</td><td>订单转化率</td></tr>";
                            break;
                    }
                    $("#tbodyData").append(html)
                });
            })
            $("#theadData").append(head_hmtl);
        }
    });
}

if($("#flag").val()=="1"){
    findType=1;
}else if($("#flag").val()=="2"){
    findType=2;
}else if($("#flag").val()=="3"){
    findType=3;
}else if($("#flag").val()=="4"){
    findType=4
}
getData();

$(".btnDone").on('click', function () {
    var _startDate = $('.range-start').datepicker('getDate');
    var _endDate = $('.range-end').datepicker('getDate');
    if (_startDate != null && _endDate != null) {
        if (_startDate > _endDate) {
            return false;
        }
        start_date = _startDate.Format("yyyy-MM-dd");
        end_date = _endDate.Format("yyyy-MM-dd");
        dateclicks.parent().prev().val(start_date + " 至 " + end_date);
        date_type = 0;
        getData();
    }
});
$("#heDiv li").click(function () {
    $("#heDiv li").removeClass("active");
    $(this).addClass("active");
})
$(".winfont").click(function () {
    $(".winfont").removeClass("st");
    $(this).addClass("st");
});
$("#blankCheckbox").click(function () {
    if ($(this).is(':checked')) {
        $("#inputDiv2").removeClass("display");
    } else {
        $("#inputDiv2").addClass("display");
    }
});


function showOrhide(select) {
    $(select).toggle(0);
}
function showOrhideForNextNode(s) {
    var a = $(s).attr("cnameMan");
    var b = $("tr[number"+a+"]").attr("class");
    if(b=="display"){
        $("tr[number"+a+"]").removeClass("display");
    }else{
        $("tr[number"+a+"]").addClass("display");
    }
}


/***
 *     Echarts 图表
 *
 ***/

// 路径配置
require.config({
    paths: {
        echarts: 'http://echarts.baidu.com/build/dist'
    }
});

// 使用
require(
    [
        'echarts',
        'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
    ],
    function (ec) {
        // 基于准备好的dom，初始化echarts图表
        var myChart1 = ec.init(document.getElementById('tubiao1'));

        var option = {
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            toolbox: {
                show : false,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {
                        show: true,
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left',
                                max: 1548
                            }
                        }
                    },
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            series : [
                {
                    name:'访问来源',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:[
                        {value:335, name:'直接访问'},
                        {value:310, name:'邮件营销'},
                        {value:234, name:'联盟广告'},
                        {value:135, name:'视频广告'},
                        {value:1548, name:'搜索引擎'}
                    ]
                }
            ]
        };

        // 为echarts对象加载数据
        myChart1.setOption(option);
    }
);
// 使用
require(
    [
        'echarts',
        'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
    ],
    function (ec) {
        // 基于准备好的dom，初始化echarts图表
        var myChart = ec.init(document.getElementById('tubiao2'));

        var option = {
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['邮件营销']
            },
            toolbox: {
                show : false,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : ['周一','周二','周三','周四','周五','周六','周日']
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'邮件营销',
                    type:'line',
                    stack: '总量',
                    data:[120, 132, 101, 134, 90, 230, 210]
                }
            ]
        };

        // 为echarts对象加载数据
        myChart.setOption(option);
    }
);