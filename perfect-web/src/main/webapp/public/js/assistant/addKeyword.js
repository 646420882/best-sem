var wordType = "bWord";

var _trade = "";

var _category = "";

var total = 0;  //当前从系统搜索的关键词总数
$(function () {
    rDrag.init(document.getElementById("kAdd"));
    $("#matchType").change(function () {
        if (this.value == "2") {
            $("#phraseTypeDiv").show();
        } else {
            $("#phraseTypeDiv").hide();
        }
    });
    $("#matchTypeNew").change(function () {
        if (this.value == "2") {
            $("#phraseTypeDivNew").show();
        } else {
            $("#phraseTypeDivNew").hide();
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
        if (campaignId != "") {
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
        }
    });
    $("#campaign_selectNew").change(function () {
        var campaignId = $("#campaign_selectNew option:selected").val();
        if (campaignId != "") {
            changeAdgroup(campaignId);
        }
    });


    $("#checkbox1").click(function () {
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
    $("#checkbox2").click(function () {
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

function addKeywordInitCampSelect() {
    $.getJSON("/campaign/getAllCampaign", null, function (data) {
        if (data.rows.length > 0) {
            var campaigns = "", datas = data.rows;
            if (jsonData.cid != undefined && jsonData.cid != null) {
                campaigns += "<option value=''>请选择推广计划</option>";
            } else {
                campaigns += "<option value='' selected='selected'>请选择推广计划</option>";
            }

            for (var i = 0, l = datas.length; i < l; i++) {
                var _campaignId = "";
                if (datas[i].campaignId != null) {
                    _campaignId = datas[i].campaignId;
                } else {
                    _campaignId = datas[i].id;
                }
                if (jsonData.cid != undefined && jsonData.cid != null) {
                    if (jsonData.cid == _campaignId) {
                        campaigns += "<option value=" + _campaignId + " selected='selected'>" + datas[i].campaignName + "</option>";

                    } else {
                        campaigns += "<option value=" + _campaignId + ">" + datas[i].campaignName + "</option>";
                    }
                } else {
                    campaigns += "<option value=" + _campaignId + ">" + datas[i].campaignName + "</option>";
                }
            }
            $("#campaign_select").empty();
            $("#campaign_select").append(campaigns);
            $("#adgroup_select").empty();
            $("#adgroup_select").append("<option value=''>请选择推广计划</option>");
            $("#campaign_selectNew").empty();
            $("#campaign_selectNew").append(campaigns);
            $("#adgroup_selectNew").empty();
            $("#adgroup_selectNew").append("<option value=''>请选择推广计划</option>");
            if (jsonData.cid != undefined && jsonData.cid != null) {
                changeAdgroup(jsonData.cid);
            }
        }
    });
}

var saveKeywordNew = function () {
    var camBgt = $("#acBgt").html();
    var price = $("#priceNew").val();
    if (price != "") {
        if (!/^-?\d+\.?\d*$/.test(price)) {
            //alert("输入正确的关键词出价！");
            AlertPrompt.show("输入正确的关键词出价!");
            return;
        } else {
            if (parseFloat(price) > 999.9 || parseFloat(price) > parseFloat(camBgt)) {
                //alert("关键词出价为：(0,999.9]<=出价&&<计划预算" + camBgt + "元");
                AlertPrompt.show("关键词出价为：(0,999.9]<=出价&&<计划预算" + camBgt + "元");
                return;
            }
        }
    } else {
        price = $("#adgroup_selectNew option:selected").attr("maxprice");
    }
    var matchType = $("#matchTypeNew :selected").val();
    var phraseType = $("#phraseTypeNew :selected").val();
    var adgroupId = $("#adgroup_selectNew option:selected").val();
    //获取所有选中的关键词
    var jsonObj = [];

    var kwd = $("#statusNew").val().split("\n");
    kwd.forEach(function (item, i) {
        if (item != undefined) {
            var entity1 = {};
            //entity1["accountId"] = $("#bdAccountId").html();
            if (adgroupId.length < 24) {
                entity1["adgroupId"] = adgroupId;
            } else {
                entity1["adgroupObjId"] = adgroupId;
            }
            entity1["keyword"] = item;
            entity1["price"] = (price == undefined || price == "" ? "0.1" : price);
            entity1["matchType"] = matchType;
            entity1["pause"] = false;
            entity1["status"] = -1;
            if (matchType == "2") {
                entity1["phraseType"] = phraseType;
            } else {
                entity1["phraseType"] = 1;
            }
            entity1["localStatus"] = 1;
            jsonObj.push(entity1);
        }
    });
    var redioNumber = $('#inputRedio input[name="entering"]:checked ').val();
    if (redioNumber == 1) {
        $.ajax({
            url: "/keyword/add",
            type: "POST",
            dataType: "json",
            data: JSON.stringify(jsonObj),
            async: false,
            contentType: "application/json; charset=UTF-8",
            success: function (data, textStatus, jqXHR) {
                if (data.rows != undefined || data.rows != "") {
                    var string = "";
                    data.rows.forEach(function (item, i) {
                        string += item + "," + (i / 5 == 0 ? "\n" : "");
                    })
                    saveSeccuss(data);
                    $("#context").empty();
                    $("#context").append(string);
                    $("#SaveSet").css("display", "none");
                    $("#uploadFile").val("");
                    $("#statusNew").val("");
                    reloadGrid();
                }
            }
        });
    } else if (redioNumber == 2) {
        $("#adgroupIdxls").val(adgroupId);
        $("#pricexls").val(price);
        $("#matchTypexls").val(matchType);
        if (matchType == "2") {
            $("#phraseTypexls").val(phraseType);
        } else {
            $("#phraseTypexls").val(1);
        }
        var from = $("#frmKeyword");
        from[0].submit();
    }
}

var saveSeccuss = function (data) {
    if (data.rows.length) {
        $("#SaveSeccuss").css({
            left: ($("body").width() - $("#SaveSeccuss").width()) / 2 - 20 + "px",
            top: ($(window).height() - $("#SaveSeccuss").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
        $(".addcolse").click(function () {
            $(".TB_overlayBG").css("display", "none");
            $("#SaveSet").css("display", "none");
            $("#SaveSeccuss").css("display", "none");
        });
    } else {
        $(".TB_overlayBG").css("display", "none");
        $("#SaveSet").css("display", "none");
        $("#SaveSeccuss").css("display", "none");
        alert("添加成功");
    }

}

var saveKeyword = function () {

    var campaignId = $("#campaign_select option:selected").val();
    if (campaignId == null || campaignId.length == 0) {
        //alert("请选择推广计划!");
        AlertPrompt.show("请选择推广计划!");
        return;
    }
    var adgroupId = $("#adgroup_select option:selected").val();
    if (adgroupId == null || adgroupId.length == 0) {
        //alert("请选择推广单元!");
        AlertPrompt.show("请选择推广单元!");
        return;
    }
    var camBgt = $("#acBgt").html();
    var price = $("#price").val();
    if (price != "") {
        if (!/^-?\d+\.?\d*$/.test(price)) {
            //alert("输入正确的关键词出价！");
            AlertPrompt.show("输入正确的关键词出价!");
            return;
        } else {
            if (parseFloat(price) > 999.9 || parseFloat(price) > parseFloat(camBgt)) {
                //alert("关键词出价为：(0,999.9]<=出价&&<计划预算" + camBgt + "元");
                AlertPrompt.show("关键词出价为：(0,999.9]<=出价&&<计划预算" + camBgt + "元");
                return;
            }
        }
    } else {
        price = 0.1;
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
                entity1["price"] = price;
                entity1["matchType"] = matchType;
                entity1["pause"] = false;
                entity1["status"] = -1;
                if (matchType == "2") {
                    entity1["phraseType"] = phraseType;
                } else {
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
                entity2["price"] = price;
                entity2["matchType"] = matchType;
                entity2["pause"] = false;
                entity2["status"] = -1;
                if (matchType == "2") {
                    entity2["phraseType"] = phraseType;
                } else {
                    entity2["phraseType"] = 1;
                }
                entity2["localStatus"] = 1;
                jsonArr.push(entity2);
            }
        }
    }

    if (jsonArr.length == 0) {
        //alert("您没有选择关键词!");
        AlertPrompt.show("您没有选择关键词!");
        return;
    }
    for (var i = 0; i < jsonArr.length; i++) {
        var adNeg = $("#adNeg").html();
        var adExNeg = $("#adExNeg").html();
        var caNeg = $("#caNeg").html();
        var caExNeg = $("#caExNeg").html();
        var keyword_selected = jsonArr[i].keyword;
        if (matchType == 1) {
            if (adExNeg.indexOf(keyword_selected) > -1) {

                //alert("关键词\"" + keyword_selected + "\"存在于单元精确否定词中,该词不能被添加!");
                AlertPrompt.show("关键词\"" + keyword_selected + "\"存在于单元精确否定词中,该词不能被添加!");
                return;
            }
            if (caExNeg.indexOf(keyword_selected) > -1) {
                //alert("关键词\"" + keyword_selected + "\"存在于计划精确否定词中,该词不能被添加!");
                AlertPrompt.show("关键词\"" + keyword_selected + "\"存在于计划精确否定词中,该词不能被添加!");
                return;
            }
        }
        if (matchType == 3 || matchType == 2) {
            if (adNeg.indexOf(keyword_selected) > -1) {
                //alert("关键词\"" + keyword_selected + "\"存在于单元广泛，短语否定词中,该词不能被添加!");
                AlertPrompt.show("关键词\"" + keyword_selected + "\"存在于单元广泛，短语否定词中,该词不能被添加!");
                return;
            } else {
                if (adExNeg.indexOf(keyword_selected) > -1) {
                    //alert("关键词\"" + keyword_selected + "\"存在于单元精确否定词中,该词不能被添加!");
                    AlertPrompt.show("关键词\"" + keyword_selected + "\"存在于单元精确否定词中,该词不能被添加!");
                    return;
                }
            }
            if (caNeg.indexOf(keyword_selected) > -1) {
                //alert("关键词\"" + keyword_selected + "\"存在于计划广泛，短语否定词中,该词不能被添加!");
                AlertPrompt.show("关键词\"" + keyword_selected + "\"存在于计划广泛，短语否定词中,该词不能被添加!");
                return;
            } else {
                if (caExNeg.indexOf(keyword_selected) > -1) {
                    //alert("关键词\"" + keyword_selected + "\"存在于计划精确否定词中,该词不能被添加!");
                    AlertPrompt.show("关键词\"" + keyword_selected + "\"存在于计划精确否定词中,该词不能被添加!");
                    return;
                }
            }
        }
    }
    if (confirm("是否要添加选中的这些关键词？") == false) {
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
            if (data.stat == true) {
                //alert("添加成功");
                AlertPrompt.show("添加成功");
                closeAddKeywordDialog();
                reloadGrid();
            }
        }
    });

};

var d = dialog({
    title: "批量添加/更新",
    padding: "5px",
    align: 'right bottom',
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


function closeAddKeywordDialog() {
    $(".TB_overlayBG").css({display: "none"});
    $("#addKeywordDiv").css("display", "none");
}
$(function () {
    $("#addKeyword").click(function () {
        showSearchWord();
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
        top.dialog({
            title: "关键词工具",
            padding: "5px",
            align: 'right bottom',
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
        top.dialog({
            title: "快速新建计划",
            padding: "5px",
            align: 'right bottom',
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
        top.dialog({
            title: "批量删除",
            padding: "5px",
            align: 'right bottom',
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
        top.dialog({
            title: "搜索词报告",
            padding: "5px",
            align: 'right bottom',
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

//
    var $tab_li = $('.add_title li');
    $tab_li.click(function () {
        var index = $tab_li.index(this);
        $('div.add_titles > div').eq(index).show().siblings().hide();
    });

});
var callbackKwd = function (data) {
    data = "{\"rows\":" + data + "}"
    var jsonData = JSON.parse(data);
    if (jsonData.rows != undefined || jsonData.rows != "") {
        var string = "";
        jsonData.rows.forEach(function (item, i) {
            string += item + "," + (i / 5 == 0 ? "\n" : "");
        })
        saveSeccuss(jsonData);
        $("#context").empty();
        $("#context").append(string);
        $("#SaveSet").css("display", "none");
        $("#uploadFile").val("");
        $("#statusNew").val("");
        reloadGrid();
    }
}
var changeAdgroup = function (campaignId) {
    campaignId = campaignId.toString();
    if (campaignId.length < 24) {
        $.getJSON("/adgroup/getAdgroupByCampaignId/" + campaignId,
            {
                campaignId: campaignId,
                skip: 0,
                limit: 100
            },
            function (data) {
                var adgroups = "", datas = data.rows;
                if (jsonData.aid != undefined && jsonData.aid != null) {
                    adgroups += "<option value=''>请选择推广单元</option>";
                } else {
                    adgroups += "<option value='' selected='selected'>请选择推广单元</option>";
                }
                for (var i = 0, l = datas.length; i < l; i++) {
                    var _adgroupId = "";
                    if (datas[i].adgroupId != null) {
                        _adgroupId = datas[i].adgroupId;
                    } else {
                        _adgroupId = datas[i].id;
                    }
                    if (jsonData.aid != undefined && jsonData.aid != null) {
                        if (jsonData.aid == _adgroupId) {
                            adgroups += "<option maxPrice=" + datas[i].maxPrice + " value=" + _adgroupId + " selected='selected'>" + datas[i].adgroupName + "</option>";
                        } else {
                            adgroups += "<option maxPrice=" + datas[i].maxPrice + " value=" + _adgroupId + ">" + datas[i].adgroupName + "</option>";
                        }
                    } else {
                        adgroups += "<option maxPrice=" + datas[i].maxPrice + " value=" + _adgroupId + ">" + datas[i].adgroupName + "</option>";
                    }
                }
                $("#adgroup_selectNew").empty();
                $("#adgroup_selectNew").append(adgroups);
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
                if (jsonData.aid != undefined && jsonData.aid != null) {
                    adgroups += "<option value=''>请选择推广单元</option>";
                } else {
                    adgroups += "<option value='' selected='selected'>请选择推广单元</option>";
                }
                for (var i = 0, l = datas.length; i < l; i++) {
                    if (jsonData.aid != undefined && jsonData.aid != null) {
                        if (jsonData.aid == _adgroupId) {
                            adgroups += "<option maxPrice=" + datas[i].maxPrice + " value=" + _adgroupId + "selected='selected'>" + datas[i].adgroupName + "</option>";

                        } else {
                            adgroups += "<option maxPrice=" + datas[i].maxPrice + " value=" + datas[i].id + ">" + datas[i].adgroupName + "</option>";
                        }
                    } else {
                        adgroups += "<option maxPrice=" + datas[i].maxPrice + " value=" + datas[i].id + ">" + datas[i].adgroupName + "</option>";
                    }

                }
                $("#adgroup_selectNew").empty();
                $("#adgroup_selectNew").append(adgroups);
            });
    }
};