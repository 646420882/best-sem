window.onload = function () {
    rDrag.init(document.getElementById('box'));
    rDrag.init(document.getElementById('box2'));
    rDrag.init(document.getElementById('box3'));
    rDrag.init(document.getElementById('box4'));
    rDrag.init(document.getElementById('box5'));
    rDrag.init(document.getElementById('box6'));
};

function initOptions(id, start, end) {

    var select = $('#' + id);
    select.empty();
    for (i = start; i <= end; i++) {
        $("<option value='" + i + "'>" + i + "点</option>").appendTo(select);
    }
}

function emptydata() {

}
$(function () {
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
    $(".showbox").click(function () {
        var items = checked("subbox2");

        if (items.length == 0) {
            alert("请选择至少一个关键词!");
            return;
        }

        if (items.length == 1) {
            filldata(items.val());
        } else {
            emptydata();
        }
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $(".box").css({
            left: ($("body").width() - $(".box").width()) / 2 - 20 + "px",
            top: ($(window).height() - $(".box").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box ").css("display", "none");
    });
//修改出价
    $(".showbox2").click(function () {
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
        $(".box2 ").css("display", "none");
    });
//修改匹配模式
    $(".showbox4").click(function () {
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
        $(".box4 ").css("display", "none");
    });
//修改访问网址
    $(".showbox3").click(function () {
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $(".box3").css({
            left: ($("body").width() - $(".box3").width()) / 2 - 20 + "px",
            top: ($(window).height() - $(".box3").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box3 ").css("display", "none");
    });
//设置规则
    $(".showbox5").click(function () {
        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $(".box5").css({
            left: ($("body").width() - $(".box5").width()) / 2 - 20 + "px",
            top: ($(window).height() - $(".box5").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box5").css("display", "none");
    });
//自定义列
    $(".showbox6").click(function () {
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
        $(".Senior").slideToggle();
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
    })

    $('#rulesaverun').click(function () {
        sendReq(true);
    })


    $('#rankBtn').click(function () {
        var items = checked("subbox2");

        if (items.length == 0) {
            alert("请选择至少一个关键词!");
            return;
        }
        var ids = [];
        items.each(function (i, item) {
            ids.push(item.value);
        })

        $.ajax({
            url: "/bidding/rank",
            data: {'ids': ids.toString()},
            type: "POST",
            success: function (datas) {
                datas.rows.each(function (item) {
                    if (item.rank == -1) {
                        $('#item.id').val("无当前排名");
                    } else {
                        $('#item.id').val(item.rank);
                    }
                });
            }
        })
    });


    $('#rulesave').click(function () {
        sendReq(false);
    })

    $('#rulesaverun').click(function () {
        sendReq(true);
        $(".close").click();
    })


    $('#rankBtn').click(function () {
        var items = checked("subbox2");

        if (items.length == 0) {
            alert("请选择至少一个关键词!");
            return;
        }
        var ids = [];
        items.each(function (i, item) {
            ids.push(item.value);
        })

        $.ajax({
            url: "/bidding/rank",
            data: {'ids': ids.toString()},
            type: "POST",
            success: function (datas) {
                datas.rows.each(function (item) {
                    if (item.rank == -1) {
                        $('#item.id').val("无当前排名");
                    } else {
                        $('#item.id').val(item.rank);
                    }
                });
            }
        })
    });

});

function sendReq(run) {
    var req = {};

    // 最高最低出价
    req.max = $('#max').val();
    req.min = $('#min').val();

    if (req.max < 0.01 || req.min < 0.01) {
        alert('竞价格式错误!')
        return;
    }

    req.run = run;
    var ids = [];
    if ($.kwid == undefined) {
        checked('subbox2').each(function (i, item) {
            ids.push(item.value);
        });
    } else {
        ids.push($.kwid);
    }
    req.ids = ids;

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

// 竞价设备
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
        contentType: "application/json",
        success: function (data) {
            alert('创建规则成功');
            $('.close').click();
            return;
        }
    })
}

function filldata(id) {
    $.ajax({
        url: "/bidding/keyword/" + id,
        type: "GET",
        success: function (datas) {
            datas.rows.each(function (item) {
                var s = item.strategyEntity;
                setSeleValue('device', s.device);

                var mtimes = (s.times.length == 2) ? 1 : 2;
                checkValue('times', mtimes);

                if (mtimes == 1) {
                    setSeleValue('start', s.times[0]);
                    setSeleValue('end', s.times[1]);
                } else {
                    for (i = 0; i < s.times.length; i + 2) {
                        var start = s.times[i];
                        if (start < 12) {
                            checked()
                        }
                    }
                }

            });
        }
    })
}

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

