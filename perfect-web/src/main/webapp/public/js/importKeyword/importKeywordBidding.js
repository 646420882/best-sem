/**
 * Created by XiaoWei on 2014/9/19.
 */
$(function () {
    initImUpdatePrice();
    initImUpdateURL();
    initImGuiZe();
    initImStopBidStatus();
    imiUpdateCustomGroup();
    imiDeleteDocument();
});
var imzTree = true;//标识分组树是否是第一次加载
var imId = "";//获取点击关键字的编号
var ima = "";//关键词名
var imap = "";//关键词名，用于向后台发送
var imbiddingStatus = "";//竞价状态
var imrule = "";//竞价规则
var imadgroupId = "";
var imSearchData = {};
var selection = "";
//加载分组树
function initImzTree(tre) {
    tre = tre != undefined ? tre : imzTree;
    if (tre) {
        $.get("/importBid/getCustomTree", function (result) {
            var json = eval("(" + result + ")");
            if (json.trees != "[]") {
                $.fn.zTree.init($("#zTreeImport"), imzTreeSetting, json.trees);
            }
        });
        loadCampaign();
    }
    imzTree = false;
}
//点击分组动态加载分组信息
function loadCampaign() {
    var _selectObj = $("#imCampaignSelect");
    _selectObj.empty();
    $.get("/assistantCreative/getPlans", function (rs) {
        var json = eval("(" + rs + ")");
        if (json.length > 0) {
            _selectObj.append("<option value='-1'>-请选择计划-</option>");
            for (var i = 0; i < json.length; i++) {
                var _option = "<option value='" + json[i].campaignId + "'>" + json[i].campaignName + "</option>";
                _selectObj.append(_option);
            }
        }
    });
}
//点击计划前的复选框事件
function checkedStatus(re) {
    var _checkBox = $("#search_div input[type='checkbox']:checked");
    if (_checkBox.length != 0) {
        $("#imSearch").attr("class", "Screening_input");
    } else {
        $("#imSearch").attr("class", "Screening_input1");
    }
}
//加载重点词
function imloadUnit(rs) {
    var planId = rs;
    var _sUnit = $("#sUnit");
    var _def = "<option value='-1'>--请选择单元--</option>";
    $.get("/assistantCreative/getUnitsByPlanId", {planId: planId}, function (rs) {
        if (rs != "[]") {
            var json = eval("(" + rs + ")");
            if (json.length > 0) {
                _sUnit.empty();
                _sUnit.append(_def);
                for (var i = 0; i < json.length; i++) {
                    var _id = null;
                    if (json[i].adgroupId != null) {
                        _id = json[i].adgroupId;
                    } else {
                        _id = json[i].id;
                    }
                    var str = "<option value='" + _id + "'>" + json[i].adgroupName + "</option>";
                    $("#sUnit").append(str);
                }
            }
        } else {
            _sUnit.empty();
            _sUnit.append(_def);
        }
    });
}
//初始化分组下拉列表
function initCustomGroupSelect() {
    var _imKwd = $("#imKwd");
    var _checked = $("#table1 tbody input[type='checkbox']:checked");
    if (_checked.length <= 0) {
        return false;
    } else {
        convertData(_checked,_imKwd);
        return true;
    }
}
//选择分组点击事件
function checkGroupOk() {
    var selectVal = $("#cgroup :selected").val();
    if (selectVal != -1) {
        if ($("#imKwd").val() != "") {
            $.post("/importBid/addKeyword", {
                cgroupId: selectVal,
                imId: imId,
                imap: imap,
                imbiddingStatus: imbiddingStatus,
                imrule: imrule,
                imadgroupId: imadgroupId
            }, function (result) {
                if (result == 1) {
                    //alert("分组成功");
                    biddingAlertPrompt.show("分组成功!");
                    imclose();
                }
            });
        }
    } else {
        var cgroupName = $("input[name='cgroupName']").val();
        if (cgroupName != "") {
            var con = confirm("是否添加名为：\"" + cgroupName + "\"的分组?并添加选择项？");
            if (con) {
                $.post("/importBid/insert", {gname: cgroupName.trim()}, function (result) {
                    var json = eval("(" + result + ")");
                    if (json.success == 1) {
                        $("#cgroup").append("<option value='" + json.data + "'>" + cgroupName + "</option>");
                        $.post("/importBid/addKeyword", {
                            cgroupId: json.data,
                            imId: imId,
                            imap: imap,
                            imbiddingStatus: imbiddingStatus,
                            imrule: imrule,
                            imadgroupId: imadgroupId
                        }, function (result) {
                            if (result == 1) {
                                //alert("分组成功");
                                biddingAlertPrompt.show("分组成功!");
                                initImzTree(true);
                                imclose();
                            }
                        });

                    } else if (json.success == 0) {
                        //alert("已经存在名为：\"" + cgroupName + "\"的分组名了！");
                        biddingAlertPrompt.show("已经存在名为：\"" + cgroupName + "\"的分组名了！");
                    }
                });
            }
        }else{
            //alert("请输入分组名！");
            biddingAlertPrompt.show("请输入分组名！");
        }
    }
}
//当选择新增时弹出新增文本框
function cgroupInsert() {
    var _this = $("#cgroup :selected").val();
    _this = parseInt(_this);
    if (_this == "-1") {
        $("#showTxt").css("display", "block");
        $("input[name='cgroupName']").css("display", "none");
    } else {
        $("#showTxt").css("display", "none");
        $("input[name='cgroupName']").css("display", "none");
    }
}
//显示或者消失分组名
function showGroupNameTxt() {
    var _this = $("input[name='cgroupName']");
    $("input[name='cgroupName']").toggle(_this.css("display") == "none").val("");
}
//根据查询到的数据加载分组列表
function convertOption(data) {
    var json = eval("(" + data + ")");
    var result = "";
    for (var i = 0; i < json.length; i++) {
        result = result + "<option value='" + json[i].id + "'>" + json[i].groupName + "</option>"
    }
    return result;
}
//关闭弹窗
function imclose() {
    $(".TB_overlayBG").css("display", "none");
    $(".box5").css("display", "none");
}
//修改出价
function initImUpdatePrice(){
    $("#showbox2_im").click(function () {
        var keywordIds = getAllCheckedcbIm();
        if (keywordIds.length == 0) {
            //alert("请选择至少一个关键词!");
            biddingAlertPrompt.show("请选择至少一个关键词!");
            return false;
        } else {
            $("input[name='newPrice']").empty();
            selectedKeywordIds = keywordIds;
            $(".TB_overlayBG").css({
                display: "block", height: $(document).height()
            });
            $(".box2").css({
                left: ($("body").width() - $(".box2").width()) / 2 - 20 + "px",
                top: ($(window).height() - $(".box2").height()) / 2 + $(window).scrollTop() + "px",
                display: "block"
            });
        }
    });
}
//修改访问网址
function initImUpdateURL(){
    $("#showbox4_im").click(function () {
        var keywordIds = getAllCheckedcbIm();
        if (keywordIds.length == 0) {
            //alert("请选择至少一个关键词!");
            biddingAlertPrompt.show("请选择至少一个关键词!");
            return false;
        } else {
            $("input[name='urlAddress']").empty();
            selectedKeywordIds = keywordIds;
            $(".TB_overlayBG").css({
                display: "block", height: $(document).height()
            });
            $(".box4").css({
                left: ($("body").width() - $(".box4").width()) / 2 - 20 + "px",
                top: ($(window).height() - $(".box4").height()) / 2 + $(window).scrollTop() + "px",
                display: "block"
            });
        }
    });
}
//暂停竞价规则
function initImStopBidStatus(){
    $("#showbox3_im").click(function () {
        var keywordIds = getAllCheckedcbIm();
        if (keywordIds.length == 0) {
            //alert("请选择至少一个关键词!");
            biddingAlertPrompt.show("请选择至少一个关键词!");
            return false;
        }
        else {
            selectedKeywordIds = keywordIds;
            var ids = getAllSelectedBidRuleIm();
            if (ids.length > 0) {
                $.ajax({
                    url: "/bidding/enable",
                    data: {'ids': ids.toString(),
                        "ebl": false},
                    type: "POST",
                    success: function (datas) {
                        if (datas.code == 0) {
                            //alert("所选关键词竞价已暂停!");
                            biddingAlertPrompt.show("所选关键词竞价已暂停!");
                            return true;
                        } else {
                            //alert("暂停失败! " + datas.msg);
                            biddingAlertPrompt.show("暂停失败! " + datas.msg);
                            return false;
                        }
                    }
                });
            } else {
                //alert("所选关键词没有设置竞价规则!");
                biddingAlertPrompt.show("所选关键词没有设置竞价规则!");
            }
        }
    });
}
//设置规则
function initImGuiZe(){
    $("#showbox_im").click(function(){
        var keywordIds = getAllCheckedcbIm();
        if (keywordIds.length == 0) {
            //alert("请选择至少一个关键词!");
            biddingAlertPrompt.show("请选择至少一个关键词!");
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
            left: ($("body").width() - $("#seetingRules").width()) / 2 - 20 + "px",
            top: ($(window).height() - $("#seetingRules").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    });
}
function imiUpdateCustomGroup(){
    //设置分组
    $("#showbox5_im").click(function () {
        $("#showTxt").css("display", "block");
        if (initCustomGroupSelectUpdate()) {
            $(".TB_overlayBG").css({
                display: "block", height: $(document).height()
            });
            $(".box5").css({
                left: ($("body").width() - $(".box5").width()) / 2 - 20 + "px",
                top: ($(window).height() - $(".box5").height()) / 2 + $(window).scrollTop() + "px",
                display: "block"
            });
        } else {
            //alert("请至少选择一个关键词！");
            biddingAlertPrompt.show("请选择至少一个关键词!");
        }

    });
}
function imiDeleteDocument() {
    $("#showboxD").click(function () {
        var _checked = $("#table2 tbody input[type='checkbox']:checked");
        if (_checked.length <= 0) {
            //alert("请至少选择一个关键词！");
            biddingAlertPrompt.show("请选择至少一个关键词!");
            return false;
        } else {
            var id = "";
            _checked.each(function (i, o) {
                var kwdId = $(o).parents("tr").find("td:eq(1)").html();
                id = id + kwdId + ",";
            });
            id = id.substr(0, id.length - 1);
            if (confirm("你确定要撤销这些重点关键字？") == true) {
                $.get("/importBid/deleteBySelection", {cgid: selection, kwdId: id}, function (res) {
                    if (res.status = "1") {
                        //alert("删除成功");
                        biddingAlertPrompt.show("删除成功!");
                        _checked.each(function (i, o) {
                            $(o).parents("tr").remove();
                        });
                    } else {
                        //alert("异常");
                        biddingAlertPrompt.show("异常!");
                    }
                });
            }

        }
    });
}
function initCustomGroupSelectUpdate(){
    var _imKwd = $("#imKwd");
    var _checked = $("#table2 tbody input[type='checkbox']:checked");
    if (_checked.length <= 0) {
        return false;
    }else{
        convertData(_checked,_imKwd);
        return true;
    }
}
//获取选中的关键字
function convertData(_checked,_imKwd){
    ima = "";
    imap = "";
    imId = "";
    imbiddingStatus = "";//竞价状态
    imrule = "";
    imadgroupId = "";
    _checked.each(function (i, o) {
        var kwdId = $(o).parents("tr").find("td:eq(1)").html();
        var kwdName = $(o).parents("tr").find("td:eq(2)").html();
        var biddingStatus = $(o).parents("tr").find("td:eq(21)").html();
        var rule = $(o).parents("tr").find("td:eq(22)").html();
        var adgroupId = $(o).parents("tr").find("td:eq(23)").html();
        ima = ima + kwdName + "\n";
        imap = imap + kwdName + ",";
        imId = imId + kwdId + ",";
        imbiddingStatus = imbiddingStatus + biddingStatus + ",";
        imrule = imrule + rule + ",";
        imadgroupId = imadgroupId + adgroupId + ",";
    });
    imId = imId.substr(0, imId.length - 1);
    ima = ima.substr(0, ima.length - 1);
    imap = imap.substr(0, imap.length - 1);
    imbiddingStatus = imbiddingStatus.substr(0, imbiddingStatus.length - 1);
    imrule = imrule.substr(0, imrule.length - 1);
    imadgroupId = imadgroupId.substr(0, imadgroupId.length - 1);
    _imKwd.val(ima.trims());
    ImSelectInit();

}
//删除自定义分组
function ImDeleteCustomGroup(){
    var selectVal = $("#cgroup :selected").val();
    var selectText = $("#cgroup :selected").text();
    if(selectVal!=-1){
        var conf=confirm("是否删除名为：\""+selectText+"\"的分组?");
        if(conf){
            $.get("/importBid/deleteByCGId",{cgid:selectVal},function(res){
                if(res=="1"){
                    initImzTree(true);
                    $("#showTxt").css("display", "block");
                    //alert("删除成功!");
                    biddingAlertPrompt.show("删除成功!");
                    ImSelectInit();
                }
            });
        }
    }
}
function ImSelectInit(){
    $.get("/importBid/getList", function (result) {
        var selectObj = $("#cgroup");
        selectObj.empty();
        if (result != "[]") {
            selectObj.append("<option value='-1'>--新建分组--</option>");
            selectObj.append(convertOption(result));
        } else {
            selectObj.append("<option value='-1'>--新建分组--</option>");
        }});
}
