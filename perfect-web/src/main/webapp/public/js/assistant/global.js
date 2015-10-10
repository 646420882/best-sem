var listnumber = null;
$("tbody").delegate("tr", "click", function (event) {
    listnumber = $(this);
    $(this).parent().find("tr").removeClass("list2_box3");
    $(this).addClass("list2_box3");
});
//复制
function Copy() {
    listnumber.html();
}
//粘贴
function Paste() {
    console.log($(".table-bordered").append(listnumber));
    $(".table-bordered").append(listnumber);
};

/*=======================================公用函数=====================================*/
/**
 * 点击推广计划树的时候调用
 * @param treeNode
 * @returns {{cid: null, aid: null}}
 */
var nowChoose = null;
var jsonData = {cid: null, aid: null, cn: null};
function getNowChooseCampaignTreeData(treeNode) {

    if (treeNode.level == 0) {
        //点击的是父节点(推广计划)
        jsonData.cid = treeNode.id;
        jsonData.cn = treeNode.name;
        jsonData.aid = null;
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
    whenClickTreeLoadData(getCurrentTabName(), jsonData);
}

/**
 * 得到当前选择的推广计划id或者推广单元的id
 */
function getNowChooseCidAndAid() {
    return nowChoose;
}

//刚进入该页面的时候加载的数据
//whenClickTreeLoadData(getCurrentTabName(), getNowChooseCidAndAid());


function whenClickTreeLoadData(tabName, param) {
    $("#jiangkong_box3").hide();
    $("#jiangkong_box2").show();
    param = param != null ? param : {aid: null, cid: null};
    var tabName = $.trim(tabName);
    if (tabName == "关键词") {
        if (param.cid != null) {
            getKwdList(0);
        }
    } else if (tabName == "推广计划") {
        getCampaignList(0);
    } else if (tabName == "普通创意") {
        if (param.cid != null) {
            if (param.cid != null && param.aid != null) {
                getCreativeUnit(param);
            } else {
                getCreativePlan(param.cid);
            }
        }
    } else if (tabName == "附加创意") {
        if (param.cid != null) {
            if (param.cid != null && param.aid != null) {
                getSublinkListUnit(param);
            } else {
                getSublinkListPlan(param.cid);
            }
        }

    } else if (tabName == "推广单元") {
        if (param.cid != null) {
            getAdgroupPlan(param.cid, param.cn);
        }
    }
    $(".jingjia_left").height($(".jingjia_right")[0].offsetHeight - 20 + "px");
    $("#zTree").height($(".jingjia_right")[0].offsetHeight - 130 + "px");
}

/**
 * 单击选项卡的事件
 */
$("#tabMenu li").click(function () {
    var tabName = $(this).html();
    whenClickTreeLoadData(tabName, getNowChooseCidAndAid());
});

/**
 * 得到当前切换的选项名称
 * @returns {*|jQuery}
 */
function getCurrentTabName() {
    return $("#tabMenu .current").html();
}


