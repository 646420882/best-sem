var staticParams={cid:null,aid:null,cn:null,nowPage:0,pageSize:20};
$("tbody").delegate("tr", "click", function (event) {
    $(this).parent().find("tr").removeClass("list2_box2");
    $(this).addClass("list2_box2");
});

/*=======================================公用函数=====================================*/
/**
 * 点击推广计划树的时候调用
 * @param treeNode
 * @returns {{cid: null, aid: null}}
 */
//var nowChoose = null;
//var jsonData = {cid: null, aid: null, cn: null};
function loadData(treeNode) {
    if (treeNode.level == 0) {
        //点击的是父节点(推广计划)
        staticParams.cid = treeNode.id;
        staticParams.aid=null;
        staticParams.cn = treeNode.name;
        whenClickTreeLoadData(getCurrentTabName());

    } else if (treeNode.level == 1) {
        //点击的是子节点(推广单元)
        staticParams.cid = treeNode.getParentNode().id;
        staticParams.aid = treeNode.id;
        whenClickTreeLoadData(getCurrentTabName());

    } else {
        staticParams.cid=null;
        staticParams.aid=null;
        staticParams.cn=null;
    }

}
/**
 * 得到当前选择的推广计划id或者推广单元的id
 */
//刚进入该页面的时候加载的数据
function whenClickTreeLoadData(tabName) {
    $("#jiangkong_box3").hide();
    $("#jiangkong_box2").show();
    //param = param != null ? param : {aid: null, cid: null};
    var tabName = $.trim(tabName);
    if (tabName == "关键词") {
        keywordPageDynamic(0);
    }  else if (tabName == "普通创意") {
        if (staticParams.cid != null && staticParams.aid != null) {
            getCreativeUnit(staticParams);
        } else {
            getCreativePlan(staticParams.cid);
        }
    } else if (tabName == "附加创意") {

    } else if (tabName == "推广单元") {
        getAdgroupPlan(staticParams.cid, staticParams.cn);
    }

}

/**
 * 单击选项卡的事件
 */
$("#tabMenu li").click(function () {
    var tabName = $(this).html();
    whenClickTreeLoadData(tabName);
});

/**
 * 得到当前切换的选项名称
 * @returns {*|jQuery}
 */
function getCurrentTabName() {
    return $("#tabMenu .current").html();
}


