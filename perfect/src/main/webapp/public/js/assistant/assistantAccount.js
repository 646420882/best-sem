/******************zTree********************/

var setting = {
    view: {
        showLine: false,
        selectedMulti: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeClick: beforeClick,
        beforeAsync: beforeAsync,
        onAsyncError: onAsyncError,
        onAsyncSuccess: onAsyncSuccess
    }
};
var zNodes = "";

function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    for (var i = 0, l = childNodes.length; i < l; i++) {
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
    }
    return childNodes;
}
function beforeClick(treeId, treeNode) {
    if (treeNode.level == 0) {
        //点击的是父节点(推广计划),则应该展示其下属的推广单元数据
//        alert(treeNode.id + "," + treeNode.name);
        campaignId = treeNode.id + "," + "0";
        getCreativePlan(treeNode.id);
        getAdgroupPlan(treeNode.id,treeNode.name);
        //事件处理
    } else if (treeNode.level == 1) {
        //点击的是子节点(推广单元),则应该展示其下属的关键词数据
//        alert(treeNode.id + "," + treeNode.name);
        adgroupId = treeNode.id + "," + "1";
        getCreativeUnit({cid:treeNode.getParentNode().id,aid:treeNode.id});
        //事件处理
    }
}
var log, className = "dark";
function beforeAsync(treeId, treeNode) {
    className = (className === "dark" ? "" : "dark");
    showLog("[ " + getTime() + " beforeAsync ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
    return true;
}
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
    showLog("[ " + getTime() + " onAsyncError ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
}
function onAsyncSuccess(event, treeId, treeNode, msg) {
    showLog("[ " + getTime() + " onAsyncSuccess ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
}

function showLog(str) {
    if (!log) log = $("#log");
    log.append("<li class='" + className + "'>" + str + "</li>");
    if (log.children("li").length > 8) {
        log.get(0).removeChild(log.children("li")[0]);
    }
}
function getTime() {
    var now = new Date(),
        h = now.getHours(),
        m = now.getMinutes(),
        s = now.getSeconds(),
        ms = now.getMilliseconds();
    return (h + ":" + m + ":" + s + " " + ms);
}
//================================================

/******************highcharts********************/

//定义展现、点击、点击率、消费、转化数组
var arr_impr = [];
var arr_click = [];
var arr_ctr = [];
var arr_cost = [];
var arr_conv = [];
var data1 = "";
var data2 = "";
var xAxis_categories = [];

var AccountPerformance = function () {
    $.ajax({
        url: "/account/get_reports",
        dataType: "json",
        data: {
            "number": 7
        },
        async: false,
        success: function (data, textStatus, jqXHR) {
            var results1 = data.rows;
            var results2 = data.dates;
            if (results1 != null && results1.length > 0) {
                $.each(results1, function (i, item) {
                    arr_impr.push(item.pcImpression);
                    arr_click.push(item.pcClick);
                    arr_ctr.push(item.pcCtr);
                    arr_cost.push(item.pcCost);
                    arr_conv.push(item.pcConversion);
                });
            }

            if (results2 != null && results2.length > 0) {
                for (var i = results2.length - 1; i >= 0; i--) {
                    xAxis_categories.push(results2[i]);
                }
            }
        }
    });

    $("#containerLegend").empty();
    $("#containerLegend").append("<div class='tu_top over'><ul><li><b>账户表现<b/></li>" +
//            + "<li>最近7天&nbsp;<input type='image' src='../public/img/zs_input.png'/></li>"
        "<li><input id='impression' name='chartcheckbox' type='checkbox' checked='checked'><span style='background-color: #1e90ff'></span><b>展现</b></li>" +
        "<li><input id='click' name='chartcheckbox' type='checkbox' checked='checked'><span style='background-color: #ff0000'></span><b>点击</b></li>" +
        "<li><input id='ctr' name='chartcheckbox' type='checkbox'><span style='background-color: #ffa500'></span><b>点击率</b></li>" +
        "<li><input id='cost' name='chartcheckbox' type='checkbox'><span style='background-color: #008000'></span><b>消费</b></li>" +
        "<li><input id='conversion' name='chartcheckbox' type='checkbox'><span style='background-color: #9370db'></span><b>转化</b></li>" +
        "<li><b style='color: red'>最多只能同时选择两项</b></li></ul></div>");

    data1 = {
        name: 'impression',
        color: '#1e90ff',
        data: arr_impr
    };

    data2 = {
        name: 'click',
        color: '#ff0000',
        yAxis: 1,
        data: arr_click
    };


    $('#container').highcharts({
        chart: {
            zoomType: 'xy',
            type: 'line'
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        credits: {
            enabled: false
        },
        xAxis: [
            {
                categories: xAxis_categories
            }
        ],
        yAxis: [
            { labels: {
                format: '{value}',
                style: {
                    color: '#4572A7'
                }
            }
            },
            {labels: {
                format: '{value}',
                style: {
                    color: '#40BC2A'
                }
            },
                min: 0,
                gridLineWidth: 0,
                opposite: true
            }
        ],
        exporting: {
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
            itemDistance: 20,
            borderRadius: 5,
            enabled: false
        },
        series: [
            data1,
            data2
        ]
    });
};

var reloadhighcharts = function (arr_checkbox) {
    var chart = $('#container').highcharts();
    while (chart.series.length > 0) {
        chart.series[0].remove();
    }

    //clear
    data1 = "";
    data2 = "";

    if (arr_checkbox.length == 1) {
        var attrValue1 = arr_checkbox.eq(0).attr("id");
        if (attrValue1 == "impression")
            chart.addSeries({
                name: attrValue1,
                color: '#1e90ff',
                data: arr_impr
            });
        else if (attrValue1 == "click")
            chart.addSeries({
                name: attrValue1,
                color: '#ff0000',
                data: arr_click
            });
        else if (attrValue1 == "ctr")
            chart.addSeries({
                name: attrValue1,
                color: '#ffa500',
                tooltip: {
                    valueSuffix: '%'
                },
                data: arr_ctr
            });
        else if (attrValue1 == "cost")
            chart.addSeries({
                name: attrValue1,
                color: '#008000',
                data: arr_cost
            });
        else if (attrValue1 == "conversion")
            chart.addSeries({
                name: attrValue1,
                color: '#9370db',
                data: arr_conv
            });
    }

    if (arr_checkbox.length == 2) {
        for (var j = 0, m = arr_checkbox.length; j < m; j++) {
            if (arr_checkbox[j].checked) {
                var attrValue2 = arr_checkbox.eq(j).attr("id");
                if (attrValue2 == "impression") {
                    if (data1 == "") {
                        data1 = {
                            name: attrValue2,
                            color: '#1e90ff',
                            data: arr_impr
                        };
                        chart.addSeries(data1);
                    } else if (data2 == "") {
                        data2 = {
                            name: attrValue2,
                            color: '#1e90ff',
                            yAxis: 1,
                            data: arr_impr
                        };
                        chart.addSeries(data2);
                    }
                } else if (attrValue2 == "click") {
                    if (data1 == "") {
                        data1 = {
                            name: attrValue2,
                            color: '#ff0000',
                            data: arr_click
                        };
                        chart.addSeries(data1);
                    } else if (data2 == "") {
                        data2 = {
                            name: attrValue2,
                            color: '#ff0000',
                            yAxis: 1,
                            data: arr_click
                        };
                        chart.addSeries(data2);
                    }
                } else if (attrValue2 == "ctr") {
                    if (data1 == "") {
                        data1 = {
                            name: attrValue2,
                            color: '#ffa500',
                            tooltip: {
                                valueSuffix: '%'
                            },
                            data: arr_ctr
                        };
                        chart.addSeries(data1);
                    } else if (data2 == "") {
                        data2 = {
                            name: attrValue2,
                            color: '#ffa500',
                            yAxis: 1,
                            tooltip: {
                                valueSuffix: '%'
                            },
                            data: arr_ctr
                        };
                        chart.addSeries(data2);
                    }
                } else if (attrValue2 == "cost") {
                    if (data1 == "") {
                        data1 = {
                            name: attrValue2,
                            color: '#008000',
                            data: arr_cost
                        };
                        chart.addSeries(data1);
                    } else if (data2 == "") {
                        data2 = {
                            name: attrValue2,
                            color: '#008000',
                            yAxis: 1,
                            data: arr_cost
                        };
                        chart.addSeries(data2);
                    }
                } else if (attrValue2 == "conversion") {
                    if (data1 == "") {
                        data1 = {
                            name: attrValue2,
                            color: '#9370db',
                            data: arr_conv
                        };
                        chart.addSeries(data1);
                    } else if (data2 == "") {
                        data2 = {
                            name: attrValue2,
                            color: '#9370db',
                            yAxis: 1,
                            data: arr_conv
                        };
                        chart.addSeries(data2);
                    }
                }
            }
        }
    }
};

//================================================

var campaignId;

var adgroupId;

$(function () {
    //获取账户树数据
    $.ajax({
        url: "/account/get_tree",
        type: "GET",
        dataType: "json",
        async: false,
        success: function (data, textStatus, jqXHR) {
            zNodes = data.trees;
        }
    });
    //加载账户树
    $.fn.zTree.init($("#zTree"), setting, zNodes);

    loadAccountData();

    AccountPerformance();

    $("#box7").text(loadDynamicCreativeStatus());

    $("#dynamicCreative").livequery('click', function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box7").css("display", "none");
        dynamicCreativeStatus = (dynamicCreativeStatus == 0 ? 1 : 0);
        $(".showbox7").text((dynamicCreativeStatus == 0) ? "开启" : "关闭");
        $("#box7").text(loadDynamicCreativeStatus());
        changeDynamicCreativeStatus();
    });

    $("#modifyAccountBudget_ok").livequery('click', function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box5").css("display", "none");
        modifyAccountBudget();
    });

    $("#excludeIP_ok").livequery('click', function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box6").css("display", "none");
        excludeIP();
    });

    $("input[name=chartcheckbox]:checkbox").livequery('click', function () {
        var arr_checkbox = $("input[name=chartcheckbox]:checked");
        if (arr_checkbox.length > 2) {
            return false;
        }
        reloadhighcharts(arr_checkbox);
    });

    $("#reachBudget").livequery('click', function () {
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $("#reachBudget1").css({
            left: ($("body").width() - $("#reachBudget1").width()) / 2 - 20 + "px",
            top: ($(window).height() - $("#reachBudget1").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
});

var loadAccountData = function () {
    $.ajax({
        url: "/account/getBaiduAccountInfoByUserId",
        dataType: "json",
        data: {
            "userId": ""
        },
        async: false,
        success: function (data, textStatus, jqXHR) {
            var result = data.rows;
            var _budget = 0;
            if (result != null) {
                $("#cost").text("￥" + data.cost);
                $("#balance").text("￥" + result.balance);
                _budget = result.balance;
                $("#accountBudget").text(result.budget);
                if (result.excludeIp.length > 0) {
                    var temp = result.excludeIp;
                    for (var i = 0, l = result.excludeIp.length; i < l; i++) {
                        var _val = $("#excludeIP_ta").val();
                        if (_val == null || _val.trim().length == 0)
                            $("#excludeIP_ta").val(temp[i]);
                        else
                            $("#excludeIP_ta").val(_val + "\n" + temp[i]);
                    }
                }
                dynamicCreativeStatus = (result.isDynamicCreative == null || result.isDynamicCreative == false) ? 0 : 1;
                $(".showbox7").text((dynamicCreativeStatus == 0) ? "开启" : "关闭");
            }

            //账户到达预算
            var _budgetOfflineTime = data.budgetOfflineTime;
            if (_budgetOfflineTime.trim().length > 0) {
                $.each(_budgetOfflineTime, function (i, item) {
                    var _time = parseInt((item.flag.split("/"))[1]);    //当日时间-小时
                    var _day = (item.flag.split("/"))[0];
                    if (i == 0) {
                        $("#reachBudget").text(_day);
                    }
                    var _li = "<li><b style='float: left; margin-left: 10px; margin-right: 30px'>" + _day + "</b>";
                    if (item.flag == 1) {
                        //上线
                        for (var k = 0; k <= 23; k++) {
                            if (k < _time) {
                                //之前的都是下线
                                _li += "<span style='background-color: lightgray'></span>";
                            } else {
                                _li += "<span style='background-color: orange'></span>";
                            }
                        }
                    } else {
                        //下线
                        for (var l = 0; l <= 23; l++) {
                            if (l < _time) {
                                //之前的都是上线
                                _li += "<span style='background-color: orange'></span>";
                            } else {
                                _li += "<span style='background-color: lightgray'></span>";
                            }
                        }
                    }
                    _li += "</li>";
                    $("#budgetList").append(_li);
                })
            } else {
                $("#reachBudget").text("无");
            }

            //消费升降
            if (data.costRate != null && (parseFloat(data.costRate)) != 0) {
                if (parseFloat(data.costRate) > 0) {
                    //上升
                    $("#costStatus").text(data.costRate + "%");
                } else {
                    //下降
                    $("#costStatus").text(-parseFloat(data.costRate) + "%");
                }
            }
        }
    });
};

//动态创意状态标识
var dynamicCreativeStatus;
var loadDynamicCreativeStatus = function () {
    var _content = "";
    if (dynamicCreativeStatus == 0)
        _content = "您确定要开启动态创意么?";
    else if (dynamicCreativeStatus == 1)
        _content = "您确定要关闭动态创意么?";

    return _content;
};

//开启或关闭动态创意
var changeDynamicCreativeStatus = function () {
    var jsonEntity = {};
    jsonEntity["isDynamicCreative"] = (dynamicCreativeStatus == 1);
    $.ajax({
        url: "/account/update",
        type: "POST",
        dateType: "json",
        data: JSON.stringify(jsonEntity),
        contentType: "application/json;charset=UTF-8",
        success: function (data, textStatus, jqXHR) {
        }
    });
};

//修改账户预算
var modifyAccountBudget = function () {
    var _budget = $("#budget_text").val();
    $("#accountBudget").text(_budget);
    var jsonEntity = {};
    jsonEntity["budget"] = _budget;
    $.ajax({
        url: "/account/update",
        type: "POST",
        dateType: "json",
        data: JSON.stringify(jsonEntity),
        contentType: "application/json;charset=UTF-8",
        success: function (data, textStatus, jqXHR) {
        }
    });
};

//IP排除
var excludeIP = function () {
    var jsonEntity = {};
    var excludeIps = [];

    //获取ip排除文本域输入框的ip值
    var ips = $("#excludeIP_ta").val().trim().split("\n");
    for (var i = 0, l = ips.length; i < l; i++) {
        excludeIps.push(ips[i]);
    }
    jsonEntity["excludeIp"] = excludeIps;
    $("#excludeIP_ta").val('');
    $.ajax({
        url: "/account/update",
        type: "POST",
        dateType: "json",
        data: JSON.stringify(jsonEntity),
        contentType: "application/json;charset=UTF-8",
        success: function (data, textStatus, jqXHR) {
        }
    });
};

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
};