/**
 * Created by XiaoWei on 2014/9/19.
 */
$(function () {


});
var imzTree = true;//标识分组树是否是第一次加载
var imId="";//获取点击关键字的编号
var ima="";//关键词名
var imap="";//关键词名，用于向后台发送
var imcampaignName="";//计划名
var imadgroupName="";//单元名
var imcost="";//消费
var imimpression="";//展现
var imclick="";//点击
var imctr="";//点击率
var imprice="";//消费
var imcpc="";//平均点击价格
var imcpm="";//PC千次展现消费
var impcQuality="";//pc质量度
var immQuality="";//移动质量度
var imstatusStr="";//状态
var impcDestinationUrl="";//pc展示Url
var immobileDestinationUrl="";//移动展示Url
var imbiddingStatus="";//竞价状态
var imrule="";//竞价规则
var imzTreeSetting = {
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
        beforeClick: iMbeforeClick,
        beforeAsync: null,
        onAsyncError: null,
        onAsyncSuccess: null
    }
};
function iMbeforeClick(treeId, treeNode) {
    if (treeNode.level == 0) {
        alert(treeNode.id);
    } else if (treeNode.level == 1) {
        alert(treeNode.id);
    }
}
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

    }
    imzTree = false;
}
//点击分组动态加载分组信息

function initCustomGroupSelect() {
    var _imKwd=$("#imKwd");
    var _checked = $("#table1 tbody input[type='checkbox']:checked");
    if (_checked.length <= 0) {
        return false;
    } else {
         ima="";
        imap="";
        imId="";
         imcampaignName="";//计划名
         imadgroupName="";//单元名
         imcost="";//消费
         imimpression="";//展现
         imclick="";//点击
         imctr="";//点击率
         imprice="";//消费
         imcpc="";//平均点击价格
         imcpm="";//PC千次展现消费
         impcQuality="";//pc质量度
         immQuality="";//移动质量度
         imstatusStr="";//状态
         impcDestinationUrl="";//pc展示Url
         immobileDestinationUrl="";//移动展示Url
         imbiddingStatus="";//竞价状态
         imrule="";
        _checked.each(function (i, o) {
            var kwdId = $(o).parents("tr").find("td:eq(1)").html();
            var kwdName = $(o).parents("tr").find("td:eq(2)").html();
            var campaignName = $(o).parents("tr").find("td:eq(3)").html()!="&nbsp;"?$(o).parents("tr").find("td:eq(3)").html():"无";
            var adgroupName=$(o).parents("tr").find("td:eq(4)").html()!="&nbsp;"?$(o).parents("tr").find("td:eq(4)").html():"无";
            var cost=$(o).parents("tr").find("td:eq(5)").html()!="0"?$(o).parents("tr").find("td:eq(5)").html():"0.0";
            var impression=$(o).parents("tr").find("td:eq(7)").html()!="0"?$(o).parents("tr").find("td:eq(7)").html():"0";
            var click=$(o).parents("tr").find("td:eq(8)").html()!="0"?$(o).parents("tr").find("td:eq(8)").html():"0";
            var ctr=$(o).parents("tr").find("td:eq(9)").html()!="0"?$(o).parents("tr").find("td:eq(9)").html():"0.0";
            var price=$(o).parents("tr").find("td:eq(10)").html()!="0"?$(o).parents("tr").find("td:eq(10)").html():"0";
            var cpc=$(o).parents("tr").find("td:eq(11)").html()!="0"?$(o).parents("tr").find("td:eq(11)").html():"0";
            var cpm=$(o).parents("tr").find("td:eq(12)").html()!="0"?$(o).parents("tr").find("td:eq(12)").html():"0";
            var pcqu=$(o).parents("tr").find("td:eq(13)").html()!="&nbsp;"?$(o).parents("tr").find("td:eq(12)").html():"0";
            var mcqu=$(o).parents("tr").find("td:eq(14)").html()!="&nbsp;"?$(o).parents("tr").find("td:eq(14)").html():"0";
            var status=$(o).parents("tr").find("td:eq(15)").html();
            var pcDestinationUrl=$(o).parents("tr").find("td:eq(17)").html()!="&nbsp;"?$(o).parents("tr").find("td:eq(17)").html():"无";
            var mcDestinationUrl=$(o).parents("tr").find("td:eq(18) a").html()!=undefined?$(o).parents("tr").find("td:eq(18) a").html():"无";
            var biddingStatus=$(o).parents("tr").find("td:eq(19)").html();
            var rule=$(o).parents("tr").find("td:eq(20)").html();
            ima=ima+kwdName+"\n";
            imap=imap+kwdName+",";
            imId=imId+kwdId+",";
            imcampaignName=imcampaignName+campaignName+",";
            imadgroupName=imadgroupName+adgroupName+",";
            imcost=imcost+cost+",";
            imimpression=imimpression+impression+",";
            imclick=imclick+click+",";
            imctr=imctr+ctr+",";
            imprice=imprice+price+",";
            imcpc=imcpc+cpc+",";
            imcpm=imcpm+cpm+",";
            impcQuality=impcQuality+pcqu+",";
            immQuality=immQuality+mcqu+",";
            imstatusStr=imstatusStr+status+",";
            impcDestinationUrl=impcDestinationUrl+pcDestinationUrl+",";
            immobileDestinationUrl=immobileDestinationUrl+mcDestinationUrl+",";
            imbiddingStatus=imbiddingStatus+biddingStatus+",";
            imrule=imrule+rule+",";
        });
        imId=imId.substr(0,imId.length-1);
        ima=ima.substr(0,ima.length-1);
        imap=imap.substr(0,imap.length-1);
        imcampaignName=imcampaignName.substr(0,imcampaignName.length-1);
        imadgroupName=imadgroupName.substr(0,imadgroupName.length-1);
        imcost=imcost.substr(0,imcost.length-1);
        imimpression=imimpression.substr(0,imimpression.length-1);
        imclick=imclick.substr(0,imclick.length-1);
        imctr=imctr.substr(0,imctr.length-1);
        imprice=imprice.substr(0,imprice.length-1);
        imcpc=imcpc.substr(0,imcpc.length-1);
        imcpm=imcpm.substr(0,imcpm.length-1);
        impcQuality=impcQuality.substr(0,impcQuality.length-1);
        immQuality=immQuality.substr(0,immQuality.length-1);
        imstatusStr=imstatusStr.substr(0,imstatusStr.length-1);
        impcDestinationUrl=impcDestinationUrl.substr(0,impcDestinationUrl.length-1);
        immobileDestinationUrl=immobileDestinationUrl.substr(0,immobileDestinationUrl.length-1);
        imbiddingStatus=imbiddingStatus.substr(0,imbiddingStatus.length-1);
        imrule=imrule.substr(0,imrule.length-1);
        _imKwd.val(ima.trim());
        $.get("/importBid/getList", function (result) {
            var selectObj = $("#cgroup");
            selectObj.empty();
            if (result != "[]") {
                selectObj.append("<option value='-1'>--新建分组--</option>");
                selectObj.append(convertOption(result));
            } else {
                selectObj.append("<option value='-1'>--新建分组--</option>");
            }
        });
        return true;
    }
}
//选择分组点击事件
function checkGroupOk() {
    var selectVal = $("#cgroup :selected").val();
    if (selectVal != -1) {
        if($("#imKwd").val()!=""){
            $.post("/importBid/addKeyword",{
                cgroupId:selectVal,
                imId:imId,
                imap:imap,
                imcampaignName:imcampaignName,
                imadgroupName:imadgroupName,
                imcost:imcost,
                imimpression:imimpression,
                imclick:imclick,
                imctr:imctr,
                imprice:imprice,
                imcpc:imcpc,
                imcpm:imcpm,
                impcQuality:impcQuality,
                immQuality:immQuality,
                imstatusStr:imstatusStr,
                impcDestinationUrl:impcDestinationUrl,
                immobileDestinationUrl:immobileDestinationUrl,
                imbiddingStatus:imbiddingStatus,
                imrule:imrule
            },function(result){
                alert(result);
            });
        }
    } else {
        var cgroupName = $("input[name='cgroupName']").val();
        if (cgroupName != "") {
            var con = confirm("是否添加名为：" + cgroupName + "的分组?");
            if (con) {
                $.post("/importBid/insert", {gname: cgroupName}, function (result) {
                    var json = eval("(" + result + ")");
                    if (json.success == 1) {
                        $("#cgroup").append("<option value='" + json.data + "'>" + cgroupName + "</option>");
                    }
                });
            }
        }

    }
}
//当选择新增时弹出新增文本框
function cgroupInsert() {
    var _this = $("#cgroup :selected").val();
    _this = parseInt(_this);
    switch (_this) {
        case -1:
            $("#showTxt").css("display", "block");
            $("input[name='cgroupName']").css("display", "none");
            break;
        default :
            $("#showTxt").css("display", "none");
            $("input[name='cgroupName']").css("display", "none");
            break;

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