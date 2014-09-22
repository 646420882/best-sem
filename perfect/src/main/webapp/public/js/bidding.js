function initOptions(id, start, end) {

    var select = $('#' + id);
    select.empty();
    for (var i = start; i <= end; i++) {
        $("<option value='" + i + "'>" + i + "点</option>").appendTo(select);
    }
}

//function emptydata() {
//
//}

var type = 1;   //智能竞价1, 重点词竞价2
var selectedKeywordIds = [];

$(function () {
    $(".tab_menu").on('click', function () {
        var lis = $(".tab_menu").find("li");
        if (lis.eq(0).hasClass("selected")) {
            type = 1;
        } else {
            initImzTree();
            type = 2;
        }
    });
    //单一时段
    initOptions('start', 0, 23);
    initOptions('end', 1, 24);

    //多时段
    initOptions('start1', 0, 11);
    initOptions('end1', 1, 12);
    initOptions('start2', 12, 13);
    initOptions('end2', 13, 14);
    initOptions('start3', 14, 23);
    initOptions('end3', 15, 24);

    //顶部菜单切换
    var $tab_li = $('.tab_menu li');
    $('.tab_menu li').click(function () {
        $(this).addClass('selected').siblings().removeClass('selected');
        var index = $tab_li.index(this);
        $('div.tab_box > div').eq(index).show().siblings().hide();
    });
    //设置规则
    $("#showbox").click(function () {
        var keywordIds = getAllCheckedcb();
        if (keywordIds.length == 0) {
            alert("请选择至少一个关键词!");
            return false;
        }

        selectedKeywordIds = keywordIds;

//        if (keywordIds.length == 1) {
//            filldata(items.val());
//        } else {
//            emptydata();
//        }
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $("#seetingRules").css({
            left: ($("body").width() - $(".box").width()) / 2 - 20 + "px",
            top: ($(window).height() - $(".box").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $("#seetingRules").css("display", "none");
    });
    //修改出价
    $("#showbox2").click(function () {
        var keywordIds = getAllCheckedcb();
        if (keywordIds.length == 0) {
            alert("请选择至少一个关键词!");
            return false;
        }

        selectedKeywordIds = keywordIds;

        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $(".box2").css({
            left: ($("body").width() - $(".box2").width()) / 2 - 20 + "px",
            top: ($(window).height() - $(".box2").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box2").css("display", "none");
    });
    //修改访问网址
    $("#showbox4").click(function () {
        var keywordIds = getAllCheckedcb();
        if (keywordIds.length == 0) {
            alert("请选择至少一个关键词!");
            return false;
        }

        selectedKeywordIds = keywordIds;
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $(".box4").css({
            left: ($("body").width() - $(".box4").width()) / 2 - 20 + "px",
            top: ($(window).height() - $(".box4").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box4").css("display", "none");
    });
    //下载
    $("#updateBtn").click(function () {
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $("#downloadData").css({
            left: ($("body").width() - $("#downloadData").width()) / 2 - 20 + "px",
            top: ($(window).height() - $("#downloadData").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $("#downloadData").css("display", "none");
    });

    //暂停竞价规则
    $("#showbox3").click(function () {
        var keywordIds = getAllCheckedcb();
        if (keywordIds.length == 0) {
            alert("请选择至少一个关键词!");
            return false;
        }

        selectedKeywordIds = keywordIds;

        var ids = getAllSelectedBidRule();
//        items.each(function (i, item) {
//            if (item.parentNode.parentNode.childNodes[12].innerHTML == "无") {
//                return true;
//            }
//            ids.push($(item).val());
//        });

        if (ids.length > 0) {
            $.ajax({
                url: "/bidding/enable",
                data: {'ids': ids.toString(),
                    "ebl": false},
                type: "POST",
                success: function (datas) {
                    if (datas.code == 0) {
                        alert("所选关键词竞价已暂停!");
                        return true;
                    } else {
                        alert("暂停失败! " + datas.msg);
                        return false;
                    }
                }
            });
        } else {
            alert("所选关键词没有设置竞价规则!");
        }
    });

    //启动竞价
    $("#showbox7").click(function () {
        var keywordIds = getAllCheckedcb();
        if (keywordIds.length == 0) {
            alert("请选择至少一个关键词!");
            return false;
        }

        selectedKeywordIds = keywordIds;

        var ids = getAllSelectedBidRule();
//        items.each(function (i, item) {
//            if (item.parentNode.parentNode.childNodes[12].innerHTML == "无") {
//                return true;
//            }
//            ids.push($(item).val());
//        });

        if (ids.length > 0) {
            $.ajax({
                url: "/bidding/enable",
                data: {'ids': ids.toString(),
                    "ebl": true},
                type: "POST",
                success: function (datas) {
                    if (datas.code == 0) {
                        alert("所选关键词竞价已启动!");
                        return true;
                    } else {
                        alert("启动失败! " + datas.msg);
                        return false;
                    }
                }
            });
        } else {
            alert("所选关键词没有设置竞价规则!");
        }
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box3 ").css("display", "none");
    });

    //设置分组
    $("#showbox5").click(function () {
        if(initCustomGroupSelect()){
            $(".TB_overlayBG").css({
                display: "block", height: $(document).height()
            });
            $(".box5").css({
                left: ($("body").width() - $(".box5").width()) / 2 - 20 + "px",
                top: ($(window).height() - $(".box5").height()) / 2 + $(window).scrollTop() + "px",
                display: "block"
            });
        }else{
            alert("请至少选择一个关键词！");
        }

    });

    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box5").css("display", "none");
    });
    //自定义列
    $("#showbox6").click(function () {
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $(".box6").css({
            left: ($("body").width() - $(".box6").width()) / 2 - 20 + "px",
            top: ($(window).height() - $(".box6").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box6").css("display", "none");
    });
    //弹窗内部切换
    $(".time_sl").click(function () {
        $(".time_select").show();
        $(".time_select01").hide();
    });
    $(".time_sl1").click(function () {
        $(".time_select01").show();
        $(".time_select").hide();

    });
    //选择框全选
    $("#checkAll").click(function () {
        $('input[name="subbox"]').prop("checked", this.checked);
    });
    var $subbox = $("input[name='subbox']");
    $subbox.click(function () {
        $("#checkAll").prop("checked", $subbox.length == $("input[name='subbox']:checked").length ? true : false);
    });
    $("#checkAll2").click(function () {
        $('input[name="subbox2"]').prop("checked", this.checked);
    });
    var $subbox2 = $("input[name='subbox2']");
    $subbox2.click(function () {
        $("#checkAll2").prop("checked", $subbox2.length == $("input[name='subbox2']:checked").length ? true : false);
    });
    //高级搜索
    $(".advanced_search").click(function () {
        if ($(".Senior").css("display") == "none") {
            $(".Senior").show();
            $(".advanced_search").attr('value', "高级搜索∧");
        }
        else {
            $(".Senior").hide();
            $(".advanced_search").attr('value', "高级搜索∨");
        }
    });
    $(".Screenings").click(function () {
        $(".Screening_concent ").slideToggle();
    });
    //竞价列表
    $('.jiangjia_list li').click(function () {
        $(this).addClass('current').siblings().removeClass('current');
    });
    $(".short").click(function () {
        $(".shorts ").slideToggle();
    });
    //设置规则显示隐藏
    $(".right_define").click(function () {
        $(".right_sets1").show();
        $(".right_define1").click(function () {
            $(".right_sets1").hide();
        });
    });


    $('#rulesave').click(function () {
        sendReq(false);
    });

    $('#rulesaverun').click(function () {
        sendReq(true);
    });

    $('#rankBtn').click(function () {
        var keywordIds = getAllCheckedcb();
        if (keywordIds.length == 0) {
            alert("请选择至少一个关键词!");
            return false;
        }
//        var ids = [];
//        items.each(function (i, item) {
//            ids.push(item.value);
//        });

        $.ajax({
            url: "/bidding/rank",
            data: {'ids': keywordIds.toString()},
            type: "POST",
            success: function (data) {
                alert("排名检查完毕,请点击查看当前排名.");
            }
        })
    });

    var txt = '关键词精准查询，多个关键词用半角逗号隔开';

    //搜索框
    $('input[name=search]').click(function () {
        var text = $('input[name=qtext]').val();
        if (text == txt) {
            return false;
        }

        var f = input('fullmatch').prop("checked");
        var filter = checkedValue('in');
        var tbl = "table1";
//        var curPage = $('.curpage').text();
//        var size = $("#size").find("option:selected").val();
//        var skip = (curPage - 1) * size;
        var curPage = pageIndex;
        var size = limit;
        var skip = curPage * size;
        var _url = "";

        //高级搜索功能
        var matchType = 0;
        var keywordQuality = "";
        var keywordPrice = "";
        if (document.getElementById("advancedSearch").style.display == "block") {
            //matchType
            var checkedMatchType = $("input[name=matchType]:checked");
            if (checkedMatchType.length > 1) {
                alert("请选择一种匹配模式!");
                return false;
            }
            $.each(checkedMatchType, function (i, item) {
                matchType = item.value;
                return false;
            });

            //keywordQuality
            var checkedKeywordQuality = $("input[name=keywordQuality]:checked");
            $.each(checkedKeywordQuality, function (i, item) {
                keywordQuality += item.value + ",";
            });
            keywordQuality = keywordQuality.substring(0, keywordQuality.length - 1);
            //keywordPrice
            var texts = $("#keywordPrice").find("input[type=text]");
            $.each(texts, function (i, item) {
                if (i == 0) {
                    if (item.value == null || item.value.trim().length == 0 || parseFloat(item.value.trim()) < 0) {
                        keywordPrice = 0;
                    } else {
                        keywordPrice = item.value.trim();
                    }
                }
                if (i == 1) {
                    if (item.value == null || item.value.trim().length == 0 || parseFloat(item.value.trim()) < 0) {
                        if (parseFloat(keywordPrice) > 0) {
                            alert("价格区间不对!请重新输入！");
                            return false;
                        }
                        keywordPrice += 0;
                    } else {
                        if (parseFloat(keywordPrice) > parseFloat(item.value.trim())) {
                            alert("价格区间不对!请重新输入！");
                            return false;
                        }
                        keywordPrice += ("," + item.value.trim());
                    }
                }
            });
            _url = "/bidding/list?s=" + skip + "&l=" + size + "&q=" + text + "&f=" + f + "&filter=" + filter + "&matchType=" + matchType + "&price=" + keywordPrice;
        } else {
            _url = "/bidding/list?s=" + skip + "&l=" + size + "&q=" + text + "&f=" + f + "&filter=" + filter;
        }

        grid.setGridParam({url: _url}).trigger("reloadGrid");

//        $.ajax({
//            url: _url,
//            type: "POST",
//            dataType: "json",
//            async: false,
//            success: function (datas) {
//                if (datas.rows == undefined) {
//                    emptyTable(tbl);
//                } else {
//                    total = datas.total;
//                    $("#pagination1").pagination(total, optInit);
//                    fullItems(datas, tbl);
//                }
//            }
//        })

    });

});

//function emptyTable(name) {
//    var rows = [];
//    for (i = 0; i < 10; i++) {
//        var row = "<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>" +
//            "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
//        rows.push(row)
//    }
//    $("#" + name + " tbody").html(rows);
//    $("#" + name + " tbody tr:odd").addClass("list2_box1");
//    $("#" + name + " tbody tr:even").addClass("list2_box2");
//}

//function fullItems(datas, name) {
//    $('#' + name + ' tbody tr').remove();
//    var newrows = [];
//    datas.rows.forEach(function (item) {
//        var newrow = "";
//        var pcUrl = item.pcDestinationUrl;
//        if (pcUrl == null) {
//            pcUrl = "";
//        }
//        var mobileUrl = item.mobileDestinationUrl;
//        if (mobileUrl == null) {
//            mobileUrl = "";
//        }
//        var _price = 0;
//        if (item.price != null) {
//            _price = item.price;
//        }
//        if (item.keywordId != null) {
//            newrow = "<tr><td>&nbsp;<input type=\"checkbox\" name=\"subbox\" value='" + item.keywordId + "'></td>" +
//                "<td>&nbsp;" + item.keyword + "</td>" +
//                "<td>&nbsp;" + item.cost + "</td>" +
//                "<td id=>&nbsp;<a class='getRankBtn' data-id='" + item.keywordId + "'>查看最新排名</a></td>" +
//                "<td>&nbsp;" + item.impression + "</td>" +
//                "<td>&nbsp;" + item.ctr + "%</td>" +
//                "<td>&nbsp;" + _price + "</td>" +
//                "<td>&nbsp;" + item.pcQuality + "</td>" +
//                "<td>&nbsp;" + item.mQuality + "</td>" +
//                "<td>&nbsp;" + item.statusStr + "</td>";
//        } else {
//            newrow = "<tr><td>&nbsp;<input type=\"checkbox\" name=\"subbox\" value='" + item.id + "'></td>" +
//                "<td>&nbsp;" + item.keyword + "</td>" +
//                "<td>&nbsp;" + item.cost + "</td>" +
//                "<td id=>&nbsp;<a class='getRankBtn' data-id='" + item.id + "'>查看最新排名</a></td>" +
//                "<td>&nbsp;" + item.impression + "</td>" +
//                "<td>&nbsp;" + item.ctr + "%</td>" +
//                "<td>&nbsp;" + _price + "</td>" +
//                "<td>&nbsp;" + item.pcQuality + "</td>" +
//                "<td>&nbsp;" + item.mQuality + "</td>" +
//                "<td>&nbsp;" + item.statusStr + "</td>";
//        }
//
//        if (item.rule) {
//            var biddingStatus = "";
//            if (item.biddingStatus == 0) {
//                biddingStatus = "已暂停";
//            } else if (item.biddingStatus == 1) {
//                biddingStatus = "已启动";
//            }
//            newrow = newrow + "<td>&nbsp;<a class='addRuleBtn' data-id='" + item.keywordId + "'>" + item.ruleDesc + "</a></td><td><a target='_blank' href='" + pcUrl + "'>" + pcUrl + "</a></td><td><a target='_blank' href='" + mobileUrl + "'>" + mobileUrl + "</a></td><td>" + biddingStatus + "</td></tr>";
//        } else {
//            newrow = newrow + "<td>&nbsp;<a class='addRuleBtn' data-id='" + item.keywordId + "' onclick='addRule(this)'>" + "+添加规则</a></td><td><a target='_blank' href='" + pcUrl + "'>" + pcUrl + "</a></td><td><a target='_blank' href='" + mobileUrl + "'>" + mobileUrl + "</a></td><td>无</td></tr>";
//        }
//        newrows.push(newrow);
//    });
//
//    $('#' + name + ' tbody').html(newrows);
//    $("#" + name + " tbody tr:odd").addClass("list2_box1");
//    $("#" + name + " tbody tr:even").addClass("list2_box2");
//
//}

//保存当前关键词竞价规则
function sendReq(run) {
    var req = {};

    //最高最低出价
    req.max = $('#max').val();
    req.min = $('#min').val();

    if (req.max < 0.01 || req.min < 0.01) {
        alert('竞价格式错误!');
        return;
    }

    req.run = run;
//    var ids = [];
//    if ($.kwid == undefined) {
//        checked('subbox').each(function (i, item) {
//            ids.push(item.value);
//        });
//    } else {
//        ids.push($.kwid);
//    }
    req.ids = selectedKeywordIds;

    var timeRange = checked('times').val();
    req.timerange = timeRange;

    if (timeRange == 1) {
        var start = seleValue('start');
        var end = seleValue('end');
        if (!validate(start, end)) {
            return;
        }
        req.times = [start, end];
    } else if (timeRange == 2) {
        var times = [];
        checked('mtimes').each(function (i, item) {
            var start = seleValue('start' + $(item).data('id'));
            var end = seleValue('end' + $(item).data('id'));
            if (!validate(start, end)) {
                times = [];
                return false;
            }
            times.push(start, end);
        });
        if (times.length == 0) {
            return false;
        }
        req.times = times;
    }

    //竞价模式
    req.mode = checked('mode').val();

    //竞价设备
    req.device = seleValue('device');

    if (req.device == undefined) {
        req.device = 10000;
    }

    //竞价位置
    req.expPosition = seleValue('pos');
    if (req.expPosition == 4) {
        req.customPos = $('input[name=rightpos]').val();
    } else {
        req.customPos = -1;
    }

    req.failed = checked('failed').val();

    req.auto = checked('auto').val();
    if (req.auto == 1) {
        var input = checked('sbid').val();
        if (input == 'bytime') {
            req.runByTimes = $('input[name=bytimes]').val();
        } else {
            req.runByTimes = -1;
        }
    }

    //竞价区域
    req.target = null;

    if (req.auto == 2) {
        req.interval = seleValue('interval');
    } else {
        req.interval = -1;
    }

    $.ajax({
        url: "/bidding/save",
        data: JSON.stringify(req),
        type: "POST",
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            alert('创建规则成功');
            $('.close').click();
        }
    })
}

//function filldata(id) {
//    $.ajax({
//        url: "/bidding/keyword/" + id,
//        type: "GET",
//        success: function (datas) {
//            datas.rows.each(function (item) {
//                var s = item.strategyEntity;
//                setSeleValue('device', s.device);
//
//                var mtimes = (s.times.length == 2) ? 1 : 2;
//                checkValue('times', mtimes);
//
//                if (mtimes == 1) {
//                    setSeleValue('start', s.times[0]);
//                    setSeleValue('end', s.times[1]);
//                } else {
//                    for (i = 0; i < s.times.length; i + 2) {
//                        var start = s.times[i];
//                        if (start < 12) {
//                            checked()
//                        }
//                    }
//                }
//
//            });
//        }
//    })
//}

function validate(start, end) {
    if (start == end) {
        alert('开始与结束时间不能相同!');
        return false;
    }
    return true;
}

function validateDigi(value) {
    if (value == '') {
        return false;
    }

    if (value < 0.01) {
        return false;
    }

    return true;
}

function input(name) {
    return $("input[name=" + name + "]");
}

function seleValue(id) {
    return $('#' + id + ' option:selected').val();
}

function setSeleValue(id, value) {
    $('#' + id + ' option:selected').val(value);
}

function checkValue(name, value) {
    $("input[name=" + name + "][value=" + value + "]").attr("checked", 'checked');
    checked(name).click();
}

function setValue(id, value) {
    $('#' + id).val(value);
}
function checked(name) {
    return $('input[name=' + name + ']:checked');
}
function checkedValue(name) {
    return $('input[name=' + name + ']:checked').val();
}

var skipPage = function () {
    var _number = 0;
    if (type == 1) {
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

var toAnyPage = function (page_index) {
    if (keyWordPage == -1 && page_index == 0) {
        document.getElementById("background").style.display = "none";
        document.getElementById("progressBar").style.display = "none";
        return false;
    }
    if (VIPKeyWordPage == -1 && page_index == 0) {
        document.getElementById("background").style.display = "none";
        document.getElementById("progressBar").style.display = "none";
        return false;
    }
    keyWordPage = 0;
    VIPKeyWordPage = 0;
    skip = page_index * limit;

    var _url = "";
    if (type == 1) {
        if (_campaignId != null) {
            _url = "/bidding/list?cp=" + _campaignId + "&s=" + skip + "&l=" + limit;
        } else if (_adgroupId != null) {
            _url = "/bidding/list?ag=" + _adgroupId + "&s=" + skip + "&l=" + limit;
        } else {
            var txt = '关键词精准查询，多个关键词用半角逗号隔开';
            var text = $('input[name=qtext]').val();
            if (text == txt) {
                return false;
            }

            var f = input('fullmatch').prop("checked");
            var filter = checkedValue('in');
            _url = "/bidding/list?s=" + skip + "&l=" + limit + "&q=" + text + "&f=" + f + "&filter=" + filter;
        }
        grid.setGridParam({url: _url}).trigger("reloadGrid");
    } else {

    }
};
