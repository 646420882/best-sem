var wordType = "bWord";

var _trade = "";

var _category = "";

var total = 0;  //当前从系统搜索的关键词总数
$(function(){
    rDrag.init(document.getElementById("kAdd"));
    $("#matchType").change(function () {
        if (this.value == "2") {
            $("#phraseTypeDiv").show();
        } else {
            $("#phraseTypeDiv").hide();
        }
    });
    var $tab_li = $('.assembly_checkbox li');
    $('.assembly_checkbox li').click(function () {
        wordType = $(this).attr("id");
        $(this).addClass('current').siblings().removeClass('current');
        var index = $tab_li.index(this);
        $('div.assembly_right_under > div').eq(index).show().siblings().hide();
    });

    //绑定键盘回车事件
    $('#searchKeyword').bind('keypress', function (event) {
        if (event.keyCode == "13") {
            searchKeyword();
        }
    });

    $("#campaign_select").change(function () {
        var campaignId = $("#campaign_select option:selected").val();
        if (campaignId.length < 24) {
            $.getJSON("/adgroup/getAdgroupByCampaignId/" + campaignId,
                {
                    campaignId: campaignId,
                    skip: 0,
                    limit: 100
                },
                function (data) {
                    var adgroups = "", datas = data.rows;
                    adgroups += "<option value='' selected='selected'>请选择推广单元</option>";
                    for (var i = 0, l = datas.length; i < l; i++) {
                        var _adgroupId = "";
                        if (datas[i].adgroupId != null) {
                            _adgroupId = datas[i].adgroupId;
                        } else {
                            _adgroupId = datas[i].id;
                        }
                        adgroups += "<option value=" + _adgroupId + ">" + datas[i].adgroupName + "</option>";
                    }
                    $("#adgroup_select").empty();
                    $("#adgroup_select").append(adgroups);
                });
        } else {
            $.getJSON("/adgroup/getAdgroupByCampaignObjId/" + campaignId,
                {
                    campaignId: campaignId,
                    skip: 0,
                    limit: 100
                },
                function (data) {
                    var adgroups = "", datas = data.rows;
                    adgroups += "<option value='' selected='selected'>请选择推广单元</option>";
                    for (var i = 0, l = datas.length; i < l; i++) {
                        adgroups += "<option value=" + datas[i].id + ">" + datas[i].adgroupName + "</option>";
                    }
                    $("#adgroup_select").empty();
                    $("#adgroup_select").append(adgroups);
                });
        }

    });


    $("#checkbox1").click( function () {
        var c1 = $("input[name=baiduKeyword]:checkbox");
        var l = c1.length;
        if ($("#checkbox1").is(':checked')) {
            for (var i = 0; i < l; i++) {
                c1[i].checked = true;
            }
        } else {
            for (var j = 0; j < l; j++) {
                c1[j].checked = false;
            }
        }
    });
    $("#checkbox2").click( function () {
        var c2 = $("input[name=perfectKeyword]:checkbox");
        var l = c2.length;
        if ($("#checkbox2").is(':checked')) {
            for (var i = 0; i < l; i++) {
                c2[i].checked = true;
            }
        } else {
            for (var j = 0; j < l; j++) {
                c2[j].checked = false;
            }
        }
    });





    $("#trade").change(function () {
        var trade = $("#trade option:selected").val();
        _trade = trade;
        $.getJSON("/getKRWords/getCategories",
            {trade: trade},
            function (data) {
                var category = "", datas = data.rows;
                category += "<option value='' selected='selected'>请选择类别</option>";
                for (var i = 0, l = datas.length; i < l; i++) {
                    if (i == 0) {
                        category += "<option value='" + datas[i].category + "' selected='selected'>" + datas[i].category + "</option>";
                        _category = datas[i].category;
                        continue;
                    }
                    category += "<option value='" + datas[i].category + "'>" + datas[i].category + "</option>";
                }

                $("#category").empty();
                $("#category").append(category);
                loadKeywordFromPerfect();
            });
    });

    $("#category").change(function () {
        var category = $("#category option:selected").val();
        loadKeywordFromPerfect();
    });

});
var searchKeyword = function () {
    var seedWord = $("#searchKeyword").val();
    if (seedWord == null || seedWord.trim().length == 0) {
        return;
    }
    $.ajax({
        url: '/getKRWords/getKRbySeedWord',
        async: false,
        data: {
            seedWord: seedWord
        },
        dataType: 'json',
        success: function (data, textStatus, jqXHR) {
            var results = data.rows;
            if (results != null && results.length > 0) {
                var trs = "";
                var _class = "";
                $.each(results, function (i, item) {
                    if (i % 2 == 0) {
                        _class = "list2_box1";
                    } else {
                        _class = "list2_box2";
                    }
                    var _tr = "<tr id='" + (wordType + i) + "' class='" + _class + "'><td><input type='checkbox' name='baiduKeyword'/></td>" +
                        "<td>" + item.word + "</td>" +
                        "<td>" + item.exactPV + "</td>" +
                        "<td>" + item.competition + "</td>" +
                        "<td>" + item.flag1 + "</td></tr>";
                    trs += _tr;
                });
                $("#tbody1").empty();
                $("#tbody1").append(trs);
            }
        }
    });
};

var loadKeywordFromPerfect = function () {
    var trade = $("#trade option:selected").val();
    var category = $("#category option:selected").val();

    _trade = trade;
    _category = category;
    $.ajax({
        url: "/getKRWords/p",
        type: "GET",
        data: {
            "trade": trade,
            "category": category,
            "skip": 0,
            "limit": 300
        },
        success: function (data, textStatus, jqXHR) {
            $("#tbody2").empty();
            if (data.rows.length > 0) {
                total = data.total;
                var _class = "";
                $.each(data.rows, function (i, item) {
                    if (i % 2 == 0) {
                        _class = "list2_box1";
                    } else {
                        _class = "list2_box2";
                    }

                    var newTr = "<tr id='" + (wordType + i) + "' class='" + _class + "'><td><input type='checkbox' name='perfectKeyword'/></td>" +
                        "<td>" + _trade + "</td>" +
                        "<td>" + _category + "</td>" +
                        "<td>" + item.group + "</td>" +
                        "<td>" + (item.keyword == null ? "" : item.keyword) + "</td>" +
                        "</tr>";
                    $("#tbody2").append(newTr);
                });
            }
        }
    });
};

function addKeywordInitCampSelect(){
    $.getJSON("/campaign/getAllCampaign", null, function (data) {
        if (data.rows.length > 0) {
            var campaigns = "", datas = data.rows;
            campaigns += "<option value='' selected='selected'>请选择推广计划</option>";
            for (var i = 0, l = datas.length; i < l; i++) {
                var _campaignId = "";
                if (datas[i].campaignId != null) {
                    _campaignId = datas[i].campaignId;
                } else {
                    _campaignId = datas[i].id;
                }
                campaigns += "<option value=" + _campaignId + ">" + datas[i].campaignName + "</option>";
            }
            $("#campaign_select").empty();
            $("#campaign_select").append(campaigns);
        }
    });
}
var saveKeyword = function () {

    var campaignId = $("#campaign_select option:selected").val();
    if (campaignId == null || campaignId.length == 0) {
        alert("请选择推广计划!");
        return;
    }
    var adgroupId = $("#adgroup_select option:selected").val();
    if (adgroupId == null || adgroupId.length == 0) {
        alert("请选择推广单元!");
        return;
    }
    var price=$("#price").val();
    if(price!=""){
        if(!/^-?\d+\.?\d*$/.test(price)){
            alert("输入正确的关键词出价！");
            return;
        }else{
            if(parseFloat(price)>999.9){
                alert("关键词出价为：(0,999.9]<=出价&&<计划预算!");
                return;
            }
        }
    }else{
        price=0.1;
    }
    var matchType = $("#matchType :selected").val();
    var phraseType = $("#phraseType :selected").val();

    //获取所有选中的关键词
    var jsonArr = [];
    if (wordType == "bWord") {
        var checkedTds1 = $("input[name=baiduKeyword]:checkbox");
        for (var i = 0, l = checkedTds1.length; i < l; i++) {
            if (checkedTds1[i].checked) {
                var entity1 = {};
                entity1["accountId"] = $("#bdAccountId").html();
                if (adgroupId.length < 24) {
                    entity1["adgroupId"] = adgroupId;
                } else {
                    entity1["adgroupObjId"] = adgroupId;
                }
                entity1["keyword"] = $("#" + wordType + i).find("td").eq(1).text();
                entity1["price"] =price ;
                entity1["matchType"] = matchType;
                entity1["pause"] = false;
                entity1["status"] = -1;
                if (matchType == "2") {
                    entity1["phraseType"] = phraseType;
                }else{
                    entity1["phraseType"] = 1;
                }
                entity1["localStatus"] = 1;
                jsonArr.push(entity1);
            }
        }
    } else {
        var checkedTds2 = $("input[name=perfectKeyword]:checkbox");
        for (var j = 0, m = checkedTds2.length; j < m; j++) {
            if (checkedTds2[j].checked) {
                var entity2 = {};
                entity2["accountId"] = $("#bdAccountId").html();
                if (adgroupId.length < 24) {
                    entity2["adgroupId"] = adgroupId;
                } else {
                    entity2["adgroupObjId"] = adgroupId;
                }
                entity2["keyword"] = $("#" + wordType + j).find("td").eq(4).text();
                entity2["price"] =price ;
                entity2["matchType"] = matchType;
                entity2["pause"] = false;
                entity2["status"] = -1;
                if (matchType == "2") {
                    entity2["phraseType"] = phraseType;
                }else{
                    entity2["phraseType"] = 1;
                }
                entity2["localStatus"] = 1;
                jsonArr.push(entity2);
            }
        }
    }

    if(jsonArr.length == 0){
        alert("您没有选择关键词!");
        return;
    }

    $.ajax({
        url: "/keyword/add",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(jsonArr),
        async: false,
        contentType: "application/json; charset=UTF-8",
        success: function (data, textStatus, jqXHR) {
            if(data.stat==true){
                alert("添加成功");
                closeAddKeywordDialog();
                reloadGrid();
            }
        }
    });

};

var d =  dialog({title: "批量添加/更新",
    padding: "5px",
    align:'right bottom',
    content: "<iframe src='/newkeyword' width='900' height='550' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
    oniframeload: function () {
    },
    onclose: function () {
//              if (this.returnValue) {
//                  $('#value').html(this.returnValue);
//              }
//                window.location.reload(true);
    },
    onremove: function () {
    }
});


function closeAddKeywordDialog(){
    $(".TB_overlayBG").css({display: "none"});
    $("#addKeywordDiv").css("display","none");
}
$(function () {
    $("#addKeyword").click(function(){
        addKeywordInitCampSelect();
        setDialogCss("addKeywordDiv");
    });
//    $("#addKeyword").livequery('click', function () {
//        top.dialog({title: "关键词工具",
//            padding: "5px",
//            align:'right bottom',
//            id:'keywordTool',
//            content: "<iframe src='/toAddPage' width='900' height='500' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
//            oniframeload: function () {
//            },
//            onclose: function () {
////              if (this.returnValue) {
////                  $('#value').html(this.returnValue);
////              }
//               // window.location.reload(true);
//                whenClickTreeLoadData(getCurrentTabName(),getNowChooseCidAndAid());
//            },
//            onremove: function () {
//            }
//        }).showModal(dockObj);
//        return false;
//    });
    $("#search_keyword").livequery('click', function () {
        top.dialog({title: "关键词工具",
            padding: "5px",
            align:'right bottom',
            content: "<iframe src='/toAddPage' width='900' height='500' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
            oniframeload: function () {
            },
            onclose: function () {
//              if (this.returnValue) {
//                  $('#value').html(this.returnValue);
//              }
               // window.location.reload(true);
                whenClickTreeLoadData(getCurrentTabName(), getNowChooseCidAndAid());
            },
            onremove: function () {
            }
        }).showModal(dockObj);
        return false;
    });
    $("#addplan").livequery('click', function () {
        top.dialog({title: "快速新建计划",
            padding: "5px",
            align:'right bottom',
            content: "<iframe src='/addplan' width='900' height='550' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
            oniframeload: function () {
            },
            onclose: function () {
//              if (this.returnValue) {
//                  $('#value').html(this.returnValue);
//              }
                window.location.reload(true);
            },
            onremove: function () {
            }
        }).showModal(dockObj);
        return false;
    });
    $("#newkeyword").livequery('click', function () {
        d.showModal(dockObj);
        return false;
    });
    $("#deletekeyword").livequery('click', function () {
        top.dialog({title: "批量删除",
            padding: "5px",
            align:'right bottom',
            content: "<iframe src='/deletekeyword' width='900' height='550' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
            oniframeload: function () {
            },
            onclose: function () {
//              if (this.returnValue) {
//                  $('#value').html(this.returnValue);
//              }
               // window.location.reload(true);
            },
            onremove: function () {
            }
        }).showModal(dockObj);
        return false;
    });
    $("#searchword").livequery('click', function () {
        top.dialog({title: "搜索词报告",
            padding: "5px",
            align:'right bottom',
            content: "<iframe src='/searchword' width='900' height='590' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
            oniframeload: function () {
            },
            onclose: function () {
//              if (this.returnValue) {
//                  $('#value').html(this.returnValue);
//              }
                window.location.reload(true);
            },
            onremove: function () {
            }
        }).showModal(dockObj);
        return false;
    });



});