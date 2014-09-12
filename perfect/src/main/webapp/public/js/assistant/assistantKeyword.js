/*加载列表数据start*/




//得到当前账户的所有关键词
function getKwdList(nowPage) {
    $("#tbodyClick").empty();
    $("#tbodyClick").html("加载中...");

    if(/^\d+$/.test(nowPage) == false){
        nowPage = 1;
    }

    var param = getNowChooseCidAndAid();
    if(param==null){
        param = {};
    }
    param["nowPage"] = nowPage;
    param["pageSize"] = $("#kwd_PageSize").val();

    $.ajax({
        url: "/assistantKeyword/list",
        type: "post",
        data: param,
        dataType: "json",
        success: function (data) {
            $("#tbodyClick").empty();
            setRedirectPageInfo_keyword(data);
            if(data.list.length==0){
                $("#tbodyClick").html("暂无数据!");
                return;
            }
            for (var i = 0; i < data.list.length; i++) {
                keywordDataToHtml(data.list[i], i);
            }
        }
    });
}


/**
 * 设置首页，上下页，尾页跳转信息
 * @param data
 */
function setRedirectPageInfo_keyword(data) {
    $(".kwdPage").find("li>a:eq(0)").attr("name",0);
    $(".kwdPage").find("li>a:eq(1)").attr("name",data.prePage);
    $(".kwdPage").find("li>a:eq(2)").attr("name",data.nextPage);
    $(".kwdPage").find("li>a:eq(3)").attr("name",data.totalPage);
    $(".kwdPage").find("li:eq(4)").html("当前页:"+data.pageNo+"/"+data.totalPage);
    $(".kwdPage").find("li:eq(5)").html("共"+data.totalCount+"条");
}

/**
 * 首页，上下页，尾页单击事件
 */
$(".kwdPage ul li>a").click(function(){
    var nowPage = $(this).attr("name");
    getKwdList(nowPage);
});
/**
 * 关键词Go按钮的单击事件
 */
$("#kwdGo").click(function(){
    var nowPage = $(".kwdPageNo").val();
    var totalPage = $(".kwdPage").find("li>a:eq(3)").attr("name");
    if(nowPage>parseInt(totalPage)){
        nowPage = parseInt(totalPage);
    }
    getKwdList(nowPage);
    $(".kwdPageNo").val("");
});



/**
 * 单击某一行时将该行的值放入相应的文本框内
 */
$("#tbodyClick").delegate("tr","click", function () {
    var obj = $(this);
    var keywordId = $(this).find("input[type=hidden]").val();
    setKwdValue(obj,keywordId);
});



/**
 *将一条数据加到html中
 */
function keywordDataToHtml(obj, index) {

    if (obj.keywordId == null) {
        obj.keywordId = obj.id;
    }

    var html = "";
    if (index == 0) {

        html = html + "<tr class='list2_box3 firstKeyword'>";
    } else if (index % 2 != 0) {
        html = html + "<tr class='list2_box2'>";
    } else {
        html = html + "<tr class='list2_box1'>";
    }

    //kwid
    html = html + "<input type='hidden' value = " + obj.keywordId + " />";

    html = html + "<td>" + obj.keyword + "</td>";

    switch (obj.status) {
        case 41:
            html = html + "<td>有效</td>";
            break;
        case 42:
            html = html + "<td>暂停推广</td>";
            break;
        case 43:
            html = html + "<td>不宜推广</td>";
            break;
        case 44:
            html = html + "<td>搜索无效</td>";
            break;
        case 45:
            html = html + "<td>待激活</td>";
            break;
        case 46:
            html = html + "<td>审核中</td>";
            break;
        case 47:
            html = html + "<td>搜索量过低</td>";
            break;
        case 48:
            html = html + "<td>部分无效</td>";
            break;
        case 49:
            html = html + "<td>计算机搜索无效</td>";
            break;
        case 50:
            html = html + "<td>移动搜索无效</td>";
            break;
        default:html = html+"<td>&nbsp;</td>";
    }

    html = html + "<td>" + until.convert(obj.pause, "暂停:启用") + "</td>";

    html = html + until.convert(obj.price == null, "<td><0.10></td>:<td>" + obj.price + "</td>");

    //质量度
    html = html + "<td>一星</td>";
    html = html + "<td>二星</td>";

    //匹配模式
    var matchType;
    switch (obj.matchType) {
        case 1:
            matchType = "精确";
            break;
        case 2:
            matchType = "短语";
            if (obj.phraseType == 1) {
                matchType = matchType + "-同义包含";
            } else if (obj.phraseType == 2) {
                matchType = matchType + "-精确包含";
            } else if (obj.phraseType == 3) {
                matchType = matchType + "-核心包含";
            }
            ;
            break;
        case 3:
            matchType = "广泛";
            break;
        default :matchType = "&nbsp;";
    }
    html = html + "<td>" + matchType + "</td>";


    //url
    var pcUrl = "";
    var mobUrl = "";
    if (obj.pcDestinationUrl != null) {
        pcUrl = "<a href='" + obj.pcDestinationUrl + "'>" + obj.pcDestinationUrl.substr(0, 20) + "</a>";
    }
    if (obj.mobileDestinationUrl != null) {
        mobUrl = "<a href='" + obj.mobileDestinationUrl + "'>" + obj.mobileDestinationUrl.substr(0, 20) + "</a>";
    }
    html = html + "<td>" + pcUrl + "</td>";
    html = html + "<td>" + mobUrl + "</td>";
    html = html + "<td>推广单元名称</td>";
    html = html + "</tr>";
    $("#tbodyClick").append(html);

    if (index == 0) {
        setKwdValue($(".firstKeyword"), obj.keywordId);
    }
}

/*加载列表数据end*/


function setKwdValue(obj, kwid) {
    $("#hiddenkwid_1").val(kwid);
    $(".keyword_1").val($(obj).find("td:eq(0)").html());


    if(($(obj).find("td:eq(3)").html())=="&lt;0.10&gt;"){
        $(".price_1").val("<0.10>");
    }else{
        $(".price_1").val($(obj).find("td:eq(3)").html());
    }


    if ($(obj).find("td:eq(7) a").attr("href") != undefined) {
        $(".pcurl_1").val($(obj).find("td:eq(7) a").attr("href"));
        $(".pcurlSize_1").html($(obj).find("td:eq(7) a").attr("href").length + "/1024");
    } else {
        $(".pcurl_1").val("");
        $(".pcurlSize_1").html("0/1024");
    }

    if ($(obj).find("td:eq(8) a").attr("href") != undefined) {
        $(".mourl_1").val($(obj).find("td:eq(8) a").attr("href"));
        $(".mourlSize_1").html($(obj).find("td:eq(8) a").attr("href").length + "/1017");
    } else {
        $(".mourl_1").val("");
        $(".mourlSize_1").html("0/1017");
    }

    $(".matchModel_1").html($(obj).find("td:eq(6)").html());
    $(".status_1").html($(obj).find("td:eq(1)").html());

    if ($(obj).find("td:eq(2)").html() == "启用") {
        $(".pause_1").html("<option value='true'>暂停</option><option value='false' selected='selected'>启用</option>");
    } else {
        $(".pause_1").html("<option value='true' selected='selected'>暂停</option><option value='false' >启用</option>");
    }
}


/**
 * 编辑关键词信息
 * @param value
 */
function editKwdInfo(jsonData) {
    jsonData["kwid"] = $("#hiddenkwid_1").val();
    $.ajax({
        url: "/assistantKeyword/edit",
        type: "post",
        data: jsonData
    });
}


/**
 * 控件失去焦点时候触发
 * @param num
 * @param value
 */
function whenBlurEditKeyword(num, value) {
    if ($("#tbodyClick").find("tr").length == 0) {
        return;
    }
    var jsonData = {};
    switch (num) {
        case 2:
            if (/^\d+$/.test(value) == false) {
                value = 0;
            }
            jsonData["price"] = value;
            break;
        case 3:
            jsonData["pcDestinationUrl"] = value;
            break;
        case 4:
            jsonData["mobileDestinationUrl"] = value;
            break;
        case 5:
            jsonData["matchType"] = value;
            break;
        case 6:
            jsonData["phraseType"] = value;
            break;
        case 7:
            jsonData["pause"] = value;
            break;
    }
    editKwdInfo(jsonData);
}


/**
 * 删除关键词
 */
function deleteKwd() {
    var ids = "";
    $("#tbodyClick").find(".list2_box3").each(function () {
        ids = ids + $(this).find("input[type=hidden]").val() + ",";
    });


    if (ids.split(",").length == 0) {
        alert("请选择行再操作!");
        return;
    }

    var isDel = window.confirm("您确定要删除关键词吗?");
    if (isDel == false) {
        return;
    }

    $.ajax({
        url: "/assistantKeyword/deleteById",
        type: "post",
        data: {"kwids": ids}
    });

    $("#tbodyClick").find(".list2_box3").remove();

    setKwdValue($("#tbodyClick tr:eq(0)"), $("#tbodyClick tr:eq(0)").find("input[type=hidden]").val());
}


/**
 * 失去焦点
 */
function missBlur(even, obj) {
    if (even.keyCode == 13) {
        obj.blur();
    }
}


/*function testBatchDel(){
 var choose1 = "18961624,443591981-";
 var info = "婚博会,精确";
 //    var name = "婚博会\n中国婚博会";
 *//* var input = "18961624,443591981,婚博会";*//*
 $.ajax({
 url:"/assistantKeyword/addOrUpdateKeywordByChoose",
 type:"post",
 data:{"isReplace":true,"chooseInfos":choose1,"keywordInfos":info},
 dataType:"json",
 success:function(data){
 alert(data.insertList.length+"=insert");
 alert(data.updateList.length+"=update");
 alert(data.igoneList.length+"igone");
 alert(data.delList.length+"=del");
 }
 });

 }*/


$("#addOrUpdateKwd").livequery('click', function () {
    top.dialog({title: "批量添加/更新",
        padding: "5px",
        content: "<iframe src='/assistantKeyword/showAddOrUpdateKeywordDialog' width='900' height='550' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
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
    }).showModal();
    return false;
});

$("#batchDelKwd").livequery('click', function () {
    top.dialog({title: "批量删除",
        padding: "5px",
        content: "<iframe src='/assistantKeyword/showBatchDelDialog' width='900' height='550' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
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
    }).showModal();
    return false;
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



