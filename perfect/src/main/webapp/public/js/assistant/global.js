$("tbody").delegate("tr","click", function (event) {
    $(this).parent().find("tr").removeClass("list2_box3");
    $(this).addClass("list2_box3");
});

/*=======================================公用函数=====================================*/
/**
 * 点击推广计划树的时候调用
 * @param treeNode
 * @returns {{cid: null, aid: null}}
 */
var nowChoose = null;
function getNowChooseCampaignTreeData(treeNode) {
    var jsonData = {cid: null, aid: null, cn: null};
    if (treeNode.level == 0) {
        //点击的是父节点(推广计划)
        jsonData.cid = treeNode.id;
        jsonData.cn = treeNode.name;
    } else if (treeNode.level == 1) {
        //点击的是子节点(推广单元)
        jsonData.cid = treeNode.getParentNode().id;
        jsonData.aid = treeNode.id;
    } else {
        jsonData.cid = null;
        jsonData.aid = null;
        jsonData.cn = null;
    }
    nowChoose = jsonData;
    whenClickTreeLoadData(getCurrentTabName(),jsonData);
}

/**
 * 得到当前选择的推广计划id或者推广单元的id
 */
function getNowChooseCidAndAid() {
    return nowChoose;
}

//刚进入该页面的时候加载的数据
whenClickTreeLoadData(getCurrentTabName(), getNowChooseCidAndAid());


function whenClickTreeLoadData(tabName, param) {
    param = param != null ? param : {aid: null, cid: null};
    var tabName = $.trim(tabName);
    if (tabName == "关键词") {
        getKwdList(1);
    } else if (tabName == "推广计划") {
        getCampaignList(1);
    } else if (tabName == "普通创意") {
        if (param.cid != null && param.aid != null) {
            getCreativeUnit(param);
        } else {
            getCreativePlan(param.cid);
        }
    } else if (tabName == "附加创意"){

    }else if (tabName == "推广单元"){
        getAdgroupPlan(param.cid, param.cn);
    }

}

/**
 * 单击选项卡的事件
 */
$("#tabMenu li").click(function () {
    var tabName = $(this).html();
    var param = getNowChooseCidAndAid();
    whenClickTreeLoadData(tabName, param);
});

/**
 * 得到当前切换的选项名称
 * @returns {*|jQuery}
 */
function getCurrentTabName() {
    return $("#tabMenu .current").html();
}


