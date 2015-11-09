/*加载列表数据start*/
/******************pagination*********************/
var items_per_page = 20;    //默认每页显示20条数据
var pageIndex = 0;  //当前页码
var records = 0;//数据的总条数
var skip = 0;
var limit = 20;//每一页显示的条数

var pageType = 1;

var pageSelectCallback = function (page_index, jq) {
    //值为1的时候代表是关键词的分页,2代表是推广计划的分页
    if (pageType == 1) {
        $("#pagination_keywordPage").append("<span style='margin-right:10px;'>跳转到 <input id='keywordPageNum' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipKeywordPage();' class='page_go'> GO</a>");
    } else if (pageType == 2) {
        $("#pagination_campaignPage").append("<span style='margin-right:10px;'>跳转到 <input id='campaignPageNum' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipCampaignPage();' class='page_go'> GO</a>");
    } else if (pageType == 3) {//创意分页
        $("#creativePager").append("<span style='margin-right:10px;'>跳转到 <input id='creativePageNum' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipCreativePage();' class='page_go'> GO</a>");
    } else if (pageType == 4) {//单元分页
        $("#adgroupPager").append("<span style='margin-right:10px;'>跳转到 <input id='adgroupPageNum' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipAdgroupPage();' class='page_go'> GO</a>");
    }

    if (pageIndex == page_index) {
        return false;
    }
    pageIndex = page_index;
    if (pageType == 1) {
        getKwdList(page_index);
    } else if (pageType == 2) {
        getCampaignList(page_index);
    } else if (pageType == 3) {
        loadCreativeData(page_index);
    } else if (pageType == 4) {
        loadAdgroupData(page_index);
    }
    return false;
};

var getOptionsFromForm = function (current_page) {
    var opt = {callback: pageSelectCallback};

    opt["items_per_page"] = items_per_page;
    opt["current_page"] = current_page;
    opt["prev_text"] = "上一页";
    opt["next_text"] = "下一页";
    opt["num_display_entries"] = 4;

    //avoid html injections
    var htmlspecialchars = {"&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;"};
    $.each(htmlspecialchars, function (k, v) {
        opt.prev_text = opt.prev_text.replace(k, v);
        opt.next_text = opt.next_text.replace(k, v);
    });
    return opt;
};
//var optInit = getOptionsFromForm(0);
/*************************************************/


//Go按钮单击事件
function skipKeywordPage() {
    var pageNo = $("#keywordPageNum").val();
    getKwdList(/^\d+$/.test(pageNo) == false ? 0 : parseInt(pageNo) - 1);
}
/**
 * 获取当前账户的注册域名，用于添加关键字，创意的时候验证！
 */
function initDomain() {
    var dm = $(".doMainS").html();
    if (dm == "") {
        $.get("/assistantCreative/getDomain", function (result) {
            if (result != "0") {
                $(".doMainS").html(result);
            }
        });
    }
}

//得到当前账户的所有关键词
function getKwdList(nowPage) {
    initDomain();
    pageType = 1;
    $("#tbodyClick").empty();
    $("#tbodyClick").html("加载中...");

    if (/^\d+$/.test(nowPage) == false) {
        nowPage = 0;
    }

    var param = getNowChooseCidAndAid();
    if (param == null) {
        param = {};
    }
    param["nowPage"] = nowPage;
    param["pageSize"] = items_per_page;

    $.ajax({
        url: "/assistantKeyword/list",
        type: "post",
        data: param,
        dataType: "json",
        success: function (data) {
            if (data != null) {
                $("#tbodyClick").empty();
                records = data.totalCount;
                pageIndex = data.nextPage;
                $("#pagination_keywordPage").pagination(records, getOptionsFromForm(pageIndex));
                if (data.list == null || data.list == undefined || data.list.length == 0) {
                    $("#tbodyClick").html("<tr><td>暂无数据</td></tr>");
                    return;
                }

                for (var i = 0; i < data.list.length; i++) {
                    var html = keywordDataToHtml(data.list[i], i);
                    $("#tbodyClick").append(html);
                    if (i == 0) {
                        setKwdValue($(".firstKeyword"), data.list[i].object.keywordId);
                        if (data.list[i].object.localStatus != null) {
                            $("#reduction").find("span").removeClass("z_function_hover");
                            $("#reduction").find("span").addClass("zs_top");
                        } else {
                            $("#reduction").find("span").removeClass("zs_top");
                            $("#reduction").find("span").addClass("z_function_hover");
                        }
                    }
                }
            } else {
                $("#tbodyClick").html("点击树，则加载！");
            }
        }
    });
}
/**
 * 单击某一行时将该行的值放入相应的文本框内
 */
$("#tbodyClick").delegate("tr", "click", function () {
    var span = $(this).find("td:last");
    $("#keywordbottom").fadeIn("slow");
    if (span.html() != "&nbsp;") {

        $("#reduction").find("span").removeClass("z_function_hover");
        $("#reduction").find("span").addClass("zs_top");
    } else {
        $("#reduction").find("span").removeClass("zs_top");
        $("#reduction").find("span").addClass("z_function_hover");
    }
    var obj = $(this);
    var keywordId = obj.find("input[type=hidden]").val();
    setKwdValue(obj, keywordId);
});
/**
 *将一条数据加到html中
 */
function keywordDataToHtml(obj, index) {
    if (obj.object.keywordId == null) {
        obj.object.keywordId = obj.object.id;
    }

    var html = "";
    if (index == 0) {
        var _tr = html + "<tr class='list2_box3 firstKeyword'>";
        if (obj.object.localStatus) {
            switch (obj.object.localStatus) {
                case -1:
                case 1:
                    _tr = html + "<tr class='list2_box3 firstKeyword add'>";
                    break;
                case 2:
                    _tr = html + "<tr class='list2_box3 firstKeyword update'>";
                    break
                case 3:
                    _tr = html + "<tr class='list2_box3 firstKeyword del'>";
                    break;
            }
        }
        html = _tr;
    } else if (index % 2 != 0) {
        var _tr = html + "<tr class='list2_box2'>";
        if (obj.object.localStatus) {
            switch (obj.object.localStatus) {
                case -1:
                case 1:
                    _tr = html + "<tr class='list2_box2 add'>";
                    break;
                case 2:
                    _tr = html + "<tr class='list2_box2 update'>";
                    break
                case 3:
                    _tr = html + "<tr class='list2_box2 del'>";
                    break;
            }
        }
        html = _tr;
    } else {
        var _tr = html + "<tr class='list2_box1'>";
        if (obj.object.localStatus) {
            switch (obj.object.localStatus) {
                case -1:
                case 1:
                    _tr = html + "<tr class='list2_box1 add'>";
                    break;
                case 2:
                    _tr = html + "<tr class='list2_box1 update'>";
                    break
                case 3:
                    _tr = html + "<tr class='list2_box1 del'>";
                    break;
            }
        }
        html = _tr;
    }

    //kwid
    var tmpHtml = "";
    if (obj.object.adgroupId != undefined) {
        tmpHtml = "<input type='hidden' camp='" + obj.campaignId + "' adg='" + obj.object.adgroupId + "' dirCount='" + obj.folderCount + "' value = " + obj.object.keywordId + " />";
    } else {
        tmpHtml = "<input type='hidden'  adg='" + obj.object.adgroupObjId + "' dirCount='" + obj.folderCount + "' value = " + obj.object.keywordId + " />";
    }

    html = html + tmpHtml;

 /*   if(obj.object.localStatus != -1){*/
        html = html + "<td><input type='checkbox' name='keywordCheck' value='" + obj.object.keywordId + "'/></td>";
   /* }else{
        html = html + "<td><input type='checkbox' name='keywordCheck' value='" + obj.object.keywordId + "'/><img src='../public/img/repeat.png' /></td>";
    }*/
    html = html + "<td>" + obj.object.keyword + "</td>";

    switch (obj.object.status) {
        case 40:
            html = html + "<td>有效-移动url审核中</td>";
            break;
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
        default:
            html = html + "<td>本地新增</td>";
    }

    html = html + "<td>" + until.convert(obj.object.pause, "启用:暂停") + "</td>";

    html = html + until.convert(obj.object.price == null, "<td><0.10></td>:<td class='InputTd'>" + "<span>" + obj.object.price + "</span>" + "<span  id='InputImg' onclick='InputPrice(this)'><img  src='../public/img/zs_table_input.png'></span>" + "</td>");
    //计算机质量度
    var quanlityHtml = "<span>";
    var quanlityText = "";
    var quanlityTextname = "";
    if (obj.quality > 0) {
        switch (parseInt(obj.quality)) {
            case 11:
            case 12:
            case 13:
                quanlityHtml += "<img src='/public/img/star.png'>";
                quanlityHtml += "<img src='/public/img/star3.png'>";
                quanlityHtml += "<img src='/public/img/star3.png'>";
                quanlityHtml += "<img src='/public/img/star3.png'>";
                quanlityHtml += "<img src='/public/img/star3.png'>";
                break;
            case 21:
            case 22:
                quanlityHtml += "<img src='/public/img/star.png'>";
                quanlityHtml += "<img src='/public/img/star.png'>";
                quanlityHtml += "<img src='/public/img/star3.png'>";
                quanlityHtml += "<img src='/public/img/star3.png'>";
                quanlityHtml += "<img src='/public/img/star3.png'>";
                break;
            case 3:
                quanlityHtml += "<img src='/public/img/star3.png'>";
                quanlityHtml += "<img src='/public/img/star3.png'>";
                quanlityHtml += "<img src='/public/img/star3.png'>";
                quanlityHtml += "<img src='/public/img/star3.png'>";
                quanlityHtml += "<img src='/public/img/star3.png'>";
        }

        switch (parseInt(obj.quality)) {
            case 11:
                quanlityText = "1";
                quanlityTextname = "一星较难优化";
                break;
            case 12:
                quanlityText = "1";
                quanlityTextname = "一星难度中等";
                break;
            case 13:
                quanlityText = "1";
                quanlityTextname = "一星较易优化";
                break;
            case 21:
                quanlityText = "2";
                quanlityTextname = "二星较难优化";
                break;
            case 22:
                quanlityText = "2";
                quanlityTextname = "二星较易优化";
                break;
            case 3:
                quanlityText = "3";
                quanlityTextname = "三星";
                break;
        }
    }
    quanlityHtml += "&nbsp;&nbsp;&nbsp;" + quanlityText + "</span>";
    html = html + "<td cname=" + obj.quality + ">" + "<a class='tabletooltip' href='#' title='" + quanlityTextname + "'>" + quanlityHtml + "</a></td>";

    $(".tabletooltip").tooltip();
    //移动质量度
    var mobileQuanlityHtml = "<span>";
    if (parseInt(obj.mobileQuality) > 0) {
        for (var i = 1; i <= 5; i++) {
            if (parseInt(obj.mobileQuality) >= i) {
                mobileQuanlityHtml += "<img src='/public/img/star.png'>";
            } else {
                mobileQuanlityHtml += "<img src='/public/img/star3.png'>";
            }
        }
        mobileQuanlityHtml += "&nbsp;&nbsp;&nbsp;" + obj.mobileQuality;
    }
    mobileQuanlityHtml += "</span>";
    html = html + "<td cname=" + obj.mobileQuality + ">" + mobileQuanlityHtml + "</td>";


    //匹配模式
    var matchType;
    switch (obj.object.matchType) {
        case 1:
            matchType = "精确";
            break;
        case 2:
            matchType = "短语";
            if (obj.object.phraseType == 1) {
                matchType = matchType + "-同义包含";
            } else if (obj.object.phraseType == 2) {
                matchType = matchType + "-精确包含";
            } else if (obj.object.phraseType == 3) {
                matchType = matchType + "-核心包含";
            }
            break;
        case 3:
            matchType = "广泛";
            break;
        default :
            matchType = "&nbsp;";
    }
    html = html + "<td>" + matchType + "</td>";


    html = html + "<td>" + (obj.object.pcDestinationUrl != null ? "<a target='_blank' href='" + obj.object.pcDestinationUrl + "'>" + obj.object.pcDestinationUrl.substr(0, 20) + "</a>" : "") + "</td>";
    html = html + "<td>" + (obj.object.mobileDestinationUrl != null ? "<a target='_blank' href='" + obj.object.mobileDestinationUrl + "'>" + obj.object.mobileDestinationUrl.substr(0, 20) + "</a>" : "") + "</td>";
    html = html + "<td>" + obj.campaignName + "</td>";

    if (obj.object.localStatus != null) {
        if (obj.object.localStatus == 3 || obj.object.localStatus == 4) {
            html = html + "<td><span class='error' step='" + obj.object.localStatus + "'></span></td>";
        } else if(obj.object.localStatus == -1) {
            html = html + "<td><span  class='repeat' step='" + obj.object.localStatus + "'></span></td>";
        }else{

            html = html + "<td><span class='pen' step='" + obj.object.localStatus + "'></span></td>";
        }

    } else {
        var replaceText = $("input[name='replaceText']").val();
        var ls = replaceText ? "<td>" + getLocalStatus(2) + "</td>" : "<td>&nbsp;</td>";
        html = html + ls;
    }

    html = html + "</tr>";

    return html;
}
/*input点击效果*/
function InputPrice(obj) {
    var htmlEm = $(obj).prev();
    var htmlValue = htmlEm.html();
    htmlEm.replaceWith("<input type='text' id='text' style='float:left;width:50px;height:20px;line-height:20px; margin-top:5px;' value='" + htmlValue + "' maxlength='5' />");
    $("#text").focus().val(htmlValue);
}
$("body").on("focusout", "#text", function () {
    var PriceVal = $("#text").val();
    if ($(this).val() == "") {
        $(this).replaceWith("<span> 0.1</span>");
    }
    $(this).replaceWith("<span>" + PriceVal + "</span>");
});

/*加载列表数据end*/

function setKwdValue(obj, kwid) {
    $("#hiddenkwid_1").val(kwid);
    $(".keyword_1").val($(obj).find("td:eq(1)").html());
    var price = $(obj).find("td:eq(4)").html();
    if (price == "&lt;0.10&gt;") {
        $(".price_1").val("<0.10>");
    } else if (price == "&nbsp;") {
        $(".price_1").val("");
    } else {
        $(".price_1").val($(obj).find("td:eq(4)").children().first(0).html())

    }


    if ($(obj).find("td:eq(8) a").attr("href") != undefined) {
        $(".pcurl_1").val($(obj).find("td:eq(8) a").attr("href"));
        $(".pcurlSize_1").html($(obj).find("td:eq(8) a").attr("href").length + "/1024");
    } else {
        $(".pcurl_1").val("");
        $(".pcurlSize_1").html("0/1024");
    }

    if ($(obj).find("td:eq(9) a").attr("href") != undefined) {
        $(".mourl_1").val($(obj).find("td:eq(9) a").attr("href"));
        $(".mourlSize_1").html($(obj).find("td:eq(9) a").attr("href").length + "/1017");
    } else {
        $(".mourl_1").val("");
        $(".mourlSize_1").html("0/1017");
    }

    $("#genusFolderCount").html(obj.find("input").attr("dircount") + "个");
    $(".matchModel_1").html($(obj).find("td:eq(7)").html());
    $(".status_1").html($(obj).find("td:eq(3)").html());

    if ($(obj).find("td:eq(3)").html() == "启用") {
        $(".pause_1").html("<option value='false'>暂停</option><option value='true' selected='selected'>启用</option>");
    } else {
        $(".pause_1").html("<option value='false' selected='selected'>暂停</option><option value='true' >启用</option>");
    }
    var matchType = $(obj).find("td:eq(7)").html();
    setSelectSelected(matchType);

}

function setSelectSelected(matStr) {
    var str = matStr;
    if (str == undefined) {
        return;
    }
    if (str.indexOf("-")) {
        str = matStr.split("-")[0];
    }
    var objSelect = document.getElementById("match_1");
    for (var i = 0; i < objSelect.options.length; i++) {
        if (objSelect.options[i].text == str) {
            if (str != "短语") {
                $("#phraseTypeLi").hide();
            } else {
                $("#phraseTypeLi").show();
            }
            objSelect.options[i].selected = true;
            break;
        }
    }
    var objSelect2 = document.getElementById("match_2");
    for (var i = 0; i < objSelect2.options.length; i++) {
        var _thisMathType = matStr.split("-")[1];
        if (objSelect2.options[i].text == _thisMathType) {
            $("#phraseTypeLi").show();
            objSelect2.options[i].selected = true;
        }
    }
}
/**
 * 编辑关键词信息
 * @param value
 */
function editKwdInfo(jsonData) {
    jsonData["kwid"] = $("#hiddenkwid_1").val();
    jsonData[""]

    $.ajax({
        url: "/assistantKeyword/edit",
        type: "post",
        data: jsonData,
        dataType: "json",
        success: function (data) {
            var jsonData = {};
            jsonData["object"] = data;
            jsonData["campaignName"] = $("#tbodyClick").find(".list2_box3 td:eq(10)").html();
            jsonData["quality"] = $("#tbodyClick").find(".list2_box3 td:eq(5)").attr("cname");
            jsonData["mobileQuality"] = $("#tbodyClick").find(".list2_box3 td:eq(6)").attr("cname");
            jsonData["campaignId"] = $("#tbodyClick").find(".list2_box3 input[type=hidden]").attr("camp");
            jsonData["folderCount"] = $("#tbodyClick").find(".list2_box3 input[type=hidden]").attr("dirCount");

            var html = keywordDataToHtml(jsonData, 0);
            var tr = $("#tbodyClick").find(".list2_box3");
            tr.replaceWith(html);
        }
    });
}
function getChar(str) {
    var char = str.match(/[^\x00-\xff]/ig);
    return str.length + (char == null ? 0 : char.length);
}

/**
 * 控件失去焦点时候触发
 * @param num
 * @param value
 */
function whenBlurEditKeyword(num, value) {
    var dm = "." + $(".doMainS").html();
    if ($("#tbodyClick").find("tr").length == 0) {
        return;
    }
    var jsonData = {};
    switch (num) {
        case 2:
            // /^-?\d+\.?\d*$/
            if (value != "") {
                if (!/^-?\d+\.?\d*$/.test(value)) {
                    //alert("请输入正确的关键字出价！");
                    assistantAlertPrompt.show("请输入正确的关键字出价!");
                    return;
                } else {
                    if (parseFloat(value).toFixed(3) > 999.9) {
                        //alert("关键词出价为：(0,999.9]<关键字出价&&<=计划预算!,如果设为0，则使用单元出价");
                        assistantAlertPrompt.show("关键词出价为：(0,999.9]<关键字出价&&<=计划预算!,如果设为0，则使用单元出价");
                        return;
                    } else {
                        jsonData["price"] = value;
                    }
                }
            } else {
                //alert("请输入关键字出价!");
                assistantAlertPrompt.show("请输入关键字出价!");
                return;
            }
            break;
        case 3:
            if (value != "") {
                if (getChar(value) > 1024) {
                    //alert("访问Url字符不能超过1024个字符");
                    assistantAlertPrompt.show("访问Url字符不能超过1024个字符");
                    return;
                } else {
                    if (value.indexOf(dm) == -1) {
                        //alert("\"访问Url\"必须包含" + dm + "的域名！");
                        assistantAlertPrompt.show("\"访问Url\"必须包含" + dm + "的域名！");
                        return;
                    } else {
                        //if (value.substr(value.indexOf(dm)) != dm) {
                        //    alert("\"访问\"Url地址必须以\"" + dm + "\"结尾！");
                        //    return false;
                        //} else {
                        jsonData["pcDestinationUrl"] = value;
                        //}
                    }
                }
            } else {
                jsonData["pcDestinationUrl"] = value;
            }
            break;
        case 4:
            if (value != "") {
                if (getChar(value) > 1024) {
                    //alert("移动访问Url字符不能超过1024个字符");
                    assistantAlertPrompt.show("移动访问Url字符不能超过1024个字符");
                    return;
                } else {
                    if (value.indexOf(dm) == -1) {
                        //alert("\"移动访问Url\"必须包含" + dm + "的域名！");
                        assistantAlertPrompt.show("\"移动访问Url\"必须包含" + dm + "的域名！");
                        return;
                    } else {
                        //if (value.substr(value.indexOf(dm)) != dm) {
                        //    alert("\"移动访问Url\"必须以\"" + dm + "\"结尾！");
                        //    return false;
                        //} else {
                        jsonData["mobileDestinationUrl"] = value;
                        //}
                    }
                }
            } else {
                jsonData["mobileDestinationUrl"] = value;
            }
            break;
        case 5:
            if (value != -1) {
                if (value == 2) {
                    $("#phraseTypeLi").show();
                    return;
                } else {
                    $("#phraseTypeLi").hide();
                    jsonData["matchType"] = value;
                }
            } else {
                return;
            }
            break;
        case 6:
            if (value != -1) {
                jsonData["matchType"] = 2;
                jsonData["phraseType"] = value;
            } else {
                return;
            }
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

    if (ids != "") {
        if (ids.split(",").length == 0) {
            //alert("请选择行再操作!");
            assistantAlertPrompt.show("请选择行再操作!");
            return;
        }
    } else {
        /*alert("请选择要删除的关键词!");*/
        assistantAlertPrompt.show("请选择要删除的关键词!");
        return;
    }
    var isDel = window.confirm("您确定要删除关键词吗?");
    if (isDel == false) {
        return;
    }

    $.ajax({
        url: "/assistantKeyword/deleteById",
        type: "post",
        data: {"kwids": ids},
        dataType: "json",
        success: function (data) {
            $("#tbodyClick").find(".list2_box3 td:last").html("<span class='error' step='3'></span>");
        }
    });

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


$("#addOrUpdateKwd").livequery('click', function () {
    batchAddOrUpdate();
});

function batchAddOrUpdate() {
    top.dialog({
        title: "批量添加/更新",
        padding: "5px",
        align: 'right bottom',
        content: "<iframe src='/assistantKeyword/showAddOrUpdateKeywordDialog' width='900' height='700' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
        oniframeload: function () {
        },
        onclose: function () {
//              if (this.returnValue) {
//                  $('#value').html(this.returnValue);
//              }
            if (jsonData.cid != null) {
                whenClickTreeLoadData(getCurrentTabName(), getNowChooseCidAndAid());
            }
        },
        onremove: function () {
        }
    }).showModal(dockObj);
    return false;
}

$("#batchDelKwd").livequery('click', function () {

    batchDelKeyword();
});

function batchDelKeyword() {
    top.dialog({
        title: "批量删除",
        padding: "5px",
        align: 'right bottom',
        content: "<iframe src='/assistantKeyword/showBatchDelDialog' width='900' height='550'  marginwidth='200' marginheight='0' scrolling='no' frameborder='0'></iframe>",
        oniframeload: function () {
        },
        onclose: function () {
//              if (this.returnValue) {
//                  $('#value').html(this.returnValue);
//              }
            whenClickTreeLoadData(getCurrentTabName(), getNowChooseCidAndAid());
        },
        onremove: function () {
        }
    }).showModal(dockObj);
    return false;
}

$("#timediglogUp").livequery('click', function () {

    timeUpdiglog();
});

function timeUpdiglog() {
   var d = top.dialog({
        title: "定时上传",
        padding: "5px",
        height: "auto",
        align: 'left bottom',
        content: "<iframe src='/assistantKeyword/showTimingDelDialog' width='550' height='300' marginwidth='200' marginheight='0' scrolling='no' frameborder='0'></iframe>",
        oniframeload: function () {
        },
        onclose: function () {

        },
        onremove: function () {
        }
    });
    d.show();
}
$("#timediglogDown").livequery('click', function () {

    timediglogDown();
});
function timediglogDown() {
   var d =  top.dialog({
        title: "定时暂停",
        padding: "5px",
        content: "<iframe src='/assistantKeyword/showTimingPauseDialog' width='550'  height='300' marginwidth='200' marginheight='0' scrolling='no' frameborder='0'></iframe>",
        oniframeload: function () {
        },
        onclose: function () {

        },
        onremove: function () {
        }
    });
    d.show();
}

$(".searchwordReport").livequery('click', function () {
    searchword();
});

function searchword() {
top.dialog({
        title: "搜索词报告",
        padding: "5px",
        align: 'right bottom',
        content: "<iframe src='/assistantKeyword/showSearchWordDialog' width='900' height='570' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
        oniframeload: function () {
        },
        onclose: function () {
//              if (this.returnValue) {
//                  $('#value').html(this.returnValue);
//              }
            // window.location.reload(true);
        },
        onremove: function () {
        },
        position: ['left', 'top']
    }).showModal(dockObj);
    return false;
}


/**
 * 还原按钮的事件
 */
$("#reduction").click(function () {
    reductionKeyword();
});

//还原关键词
function reductionKeyword() {

    var choose = $("#tbodyClick").find(".list2_box3");
    if (choose != undefined && choose.find("td:last").html() != "&nbsp;") {
        if (confirm("是否还原选择的数据?") == false) {
            return;
        }
        var step = choose.find("td:last span").attr("step");
        var id = $("#tbodyClick").find(".list2_box3").find("input[type=hidden]").val();
        switch (parseInt(step)) {
            case 1:
                reducKwd_Add(id);
                break;
            case 2:
                reducKwd_update(id);
                break;
            case 3:
                reducKwd_del(id);
                break;
            case 4:
                //alert("属于单元级联删除，如果要恢复该数据，则必须恢复单元即可！");
                assistantAlertPrompt.show("属于单元级联删除，如果要恢复该数据，则必须恢复单元即可！");
                break;
        }
    }
}


/**
 * 还原新增的关键词(localStatus为1的)
 * @param id
 */
function reducKwd_Add(id) {
    $.ajax({
        url: "/assistantKeyword/reducAdd",
        type: "post",
        data: {"id": id},
        dataType: "json",
        success: function (data) {
            $("#tbodyClick").find(".list2_box3").remove();
        }
    });
}

/**
 * 还原修改的关键词(localStatus为2的)
 * @param id
 */
function reducKwd_update(id) {
    $.ajax({
        url: "/assistantKeyword/reducUpdate",
        type: "post",
        data: {"id": id},
        dataType: "json",
        success: function (data) {
            var jsonData = {};
            jsonData["object"] = data;
            jsonData["campaignName"] = $("#tbodyClick").find(".list2_box3 td:eq(10)").html();
            jsonData["quality"] = $("#tbodyClick").find(".list2_box3 td:eq(5)").attr("cname");
            jsonData["mobileQuality"] = $("#tbodyClick").find(".list2_box3 td:eq(6)").attr("cname");

            var dirCount = $("#tbodyClick").find(".list2_box3 input[type=hidden]").attr("dirCount");
            jsonData["folderCount"] = dirCount;

            var html = keywordDataToHtml(jsonData, 0);
            var tr = $("#tbodyClick").find(".list2_box3");
            tr.replaceWith(html);
        }
    });
}


/**
 * 还原软删除
 * @param id
 */
function reducKwd_del(id) {
    $.ajax({
        url: "/assistantKeyword/reducDel",
        type: "post",
        data: {"id": id},
        dataType: "json",
        success: function (data) {
            $("#tbodyClick").find(".list2_box3 td:last").html("&nbsp;");
        }
    });
}


/************************************************************关键词的右击菜单************************************************************/
/**
 * 菜单名，方法
 * @type {{text: string, func: func}}
 */
var menu_keyword_add = {
        text: "添加关键词",
        img: "../public/img/zs_function1.png",
        func: function () {
           /* showSearchWord();*/
            AddKeywords();
        }
    }, menu_keyword_del = {
        text: "删除",
        img: "../public/img/zs_function2.png",
        func: function () {
            deleteKwd();
        }
    }, menu_keyword_batchAddOrUpdate = {
        text: "批量添加/更新",
        img: "../public/img/zs_function3.png",
        func: function () {
            batchAddOrUpdate();
        }
    }, menu_keyword_batchDel = {
        text: "批量删除",
        img: "../public/img/zs_function2.png",
        func: function () {
            batchDelKeyword();
        }
    }, menu_keyword_redu = {
        text: "还原",
        img: "../public/img/zs_function9.png",
        func: function () {
            reductionKeyword();
        }
    }, menu_keyword_upload = {
        text: "更新到凤巢",
        img: "../public/img/update2.png",
        func: function () {
            kUpload();
        }
    }, menu_keyword_searchWord = {
        text: "搜索词",
        img: "../public/img/zs_function10.png",
        func: function () {
            searchword();
        }

    }
    , menu_keyword_copy = {
        text: "复制",
        img: "../public/img/zs_function13.png",
        func: function () {
            editCommons.Copy();
        }

    }
    , menu_keyword_shear = {
        text: "剪切",
        img: "../public/img/zs_function14.png",
        func: function () {
            editCommons.Cut();
        }

    }
    , menu_keyword_paste = {
        text: "粘贴",
        img: "../public/img/zs_function15.png",
        func: function () {
            editCommons.Parse();
        }
    }
    , menu_keyword_select = {
        text: "全选",
        img: "../public/img/zs_function16.png",
        func: function () {
            CtrlAll();
        }

    }
function showSearchWord() {
    $("#adgroup_select").empty();
    $("#phraseTypeDiv").hide();
    $("span[id$=Neg]").empty();
    $("#trade").get(0).selectedIndex = 0;
    addKeywordInitCampSelect();
    setDialogCss("addKeywordDiv");
    //top.dialog({title: "关键词工具",
    //    padding: "5px",
    //    align:'right bottom',
    //    content: "<iframe src='/toAddPage' width='900' height='500' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
    //    onclose: function () {
    //        /* whenClickTreeLoadData(getCurrentTabName(),getNowChooseCidAndAid());*/
    //    }
    //}).showModal(dockObj);
    //return false;
}
/**
 * 右键菜单显示的选项
 * @type {*[]}
 */
var keywordMenuData = [
    [menu_keyword_add, menu_keyword_batchAddOrUpdate, menu_keyword_del, menu_keyword_batchDel, menu_keyword_redu, menu_keyword_upload, menu_keyword_searchWord, menu_keyword_copy, menu_keyword_shear, menu_keyword_paste, menu_keyword_select]
];
/**
 * 用户缓存右键点击的对象
 * @type {null}
 */
var tmp = null;
/**
 * 菜单name值，标识唯一，beforeShow显示完成后方法
 * @type {{name: string, beforeShow: beforeShow}}
 */
var keywordMenuExt = {
    name: "keyword",
    beforeShow: function () {
        var _this = $(this);
        tmp = _this;
        $.smartMenu.remove();
    }
};

function validateNoAllowKeyword(value) {
    if (value != "") {
        $.get("/assistantKeyword/getNoKeywords", {aid: value}, function (res) {
            var adNeg = "";
            var adExNeg = "";
            var caNeg = "";
            var caExNeg = "";
            if (res.data.ad.neg != null) {
                if (res.data.ad.neg.length > 0) {
                    for (var i = 0; i < res.data.ad.neg.length; i++) {
                        adNeg = adNeg + res.data.ad.neg[i] + ",";
                    }
                    adNeg = adNeg.slice(0, -1);
                    $("#adNeg").html(adNeg);
                } else {
                    $("#adNeg").html(res.data.ad.neg);
                }
            } else {
                $("#adNeg").html(res.data.ad.neg)
            }
            if (res.data.ad.exneg != null) {
                if (res.data.ad.exneg.length > 0) {
                    for (var i = 0; i < res.data.ad.exneg.length; i++) {
                        adExNeg = adExNeg + res.data.ad.exneg[i] + ",";
                    }
                    adExNeg = adExNeg.slice(0, -1);
                    $("#adExNeg").html(adExNeg);
                } else {
                    $("#adExNeg").html(res.data.ad.exneg);
                }
            } else {
                $("#adExNeg").html(res.data.ad.exneg);
            }
            if (res.data.ca.neg != null) {
                if (res.data.ca.neg.length > 0) {
                    for (var i = 0; i < res.data.ca.neg.length; i++) {
                        caNeg = caNeg + res.data.ca.neg[i] + ",";
                    }
                    caNeg = caNeg.slice(0, -1);
                    $("#caNeg").html(caNeg);
                } else {
                    $("#caNeg").html(res.data.ca.neg);
                }
            } else {
                $("#caNeg").html(res.data.ca.neg);
            }
            if (res.data.ca.exneg != null) {
                if (res.data.ca.exneg.length > 0) {
                    for (var i = 0; i < res.data.ca.exneg.length; i++) {
                        caExNeg = caExNeg + res.data.ca.exneg[i] + ",";
                    }
                    caExNeg = caExNeg.slice(0, -1);
                    $("#caExNeg").html(caExNeg);
                } else {
                    $("#caExNeg").html(res.data.ca.exneg);
                }
            } else {
                $("#caExNeg").html(res.data.ca.exneg);
            }
        });
        $.get("/keyword/getKeywordIdByAdgroupId/" + value, function (data) {
            $("#countkwd").val(data.rows == undefined ? 0 : data.rows == "" ? 0 : data.rows.length);
            $("#countNumber").html(5000 - (data.rows == undefined ? 0 : data.rows == "" ? 0 : data.rows.length));
        })
    } else {
        $("span[id$=Neg]").empty();
        $("#phraseTypeDiv").hide();
    }
}

$("#tbodyClick").on("mousedown", "tr", function () {
    $(this).smartMenu(keywordMenuData, keywordMenuExt);
});

function kUpload() {
    var choose = $("#tbodyClick").find(".list2_box3");
    if (choose != undefined && choose.find("td:last").html() != "&nbsp;") {
        if (confirm("是否上传选择的数据到凤巢?一旦上传将不能还原！") == false) {
            return;
        }
        var step = choose.find("td:last span").attr("step");
        var id = $("#tbodyClick").find(".list2_box3").find("input[type=hidden]").val();
        switch (parseInt(step)) {
            case 1:
                if (id.length > 18) {
                    kUploadOperate(id, 1);
                }
                break;
            case 2:
                kUploadOperate(id, 2);
                break;
            case 3:
                if (id.length < 18) {
                    kUploadOperate(id, 3);
                } else {
                    deleteKwd();
                }
                break;
        }
    } else {
        //alert("已经是最新数据了！");
        assistantAlertPrompt.show("已经是最新数据了！");
    }
}
function kUploadOperate(kid, ls) {
    $.get("/assistantKeyword/uploadOperate", {kid: kid, ls: ls}, function (res) {
        if (res.msg == "1") {
            //alert("上传成功!");
            assistantAlertPrompt.show("上传成功！");
            if (jsonData.cid != null) {
                getKwdList(0);
            }
        } else if (res.msg == "noUp") {
            var conf = confirm("该关键词上级单元以或计划没有上传，是否要一并上传？");
            if (conf) {
                $.get("/assistantKeyword/uploadAddByUp", {kid: kid}, function (res) {
                    if (res.msg == "1") {
                        assistantAlertPrompt.show("上传成功！");
                        //alert("上传成功!");
                        if (jsonData.cid != null) {
                            getKwdList(0);
                            loadTree();
                        }
                    } else {
                        //alert(res.msg);
                        assistantAlertPrompt.show(res.msg);
                    }
                });
            }
        } else {
            //alert(res.msg);
            assistantAlertPrompt.show(res.msg);
        }
    });
}
function addCensus() {
    if (confirm("是否添加关键字的统计代码？")) {
        $.get("/assistantKeyword/addCensus", function (res) {
            if (res.msg == "1") {
                getKwdList(0);
                //alert("添加成功");
                assistantAlertPrompt.show("添加成功");
            } else {
                //alert("添加失败")
                assistantAlertPrompt.show("添加失败");
            }
        });
    }
}
function AddKeywords() {
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    $("#AddKeywords").css({
        left: ($("body").width() - $("#AddKeywords").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#AddKeywords").height()) / 2 + $(window).scrollTop() + "px",
        display: "block"
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $("#AddKeywords").css("display", "none");
    });
    addKeywordInitCampSelect()
}
function AddKeywordsSave() {

    var campaignId = $("#campaign_selectNew option:selected").val();
    if (campaignId == null || campaignId.length == 0) {
        //alert("请选择推广计划!");
        assistantAlertPrompt.show("请选择推广计划!");
        return;
    }
    var adgroupId = $("#adgroup_selectNew option:selected").val();
    if (adgroupId == null || adgroupId.length == 0) {
        //alert("请选择推广单元!");
        assistantAlertPrompt.show("请选择推广单元!");
        return;
    }
    var device = $("#device_selectNew option:selected").val();
    if (device == null || device.length == 0) {
        assistantAlertPrompt.show("请选择推广设备!");
        //alert("请选择推广设备!");
        return;
    }
    if ($("#statusNew").val() == null || $("#statusNew").val() == "") {
        //alert("关键词不能为空！")
        assistantAlertPrompt.show("关键词不能为空!");
        return
    }

    var kwds = $("#statusNew").val().trims().split("\n");
    if (kwds[kwds.length - 1] == "") {
        kwds.splice(kwds.length - 1, 1);
    }
    var countkwd = $("#countkwd").val();
    countkwd = (countkwd == undefined ? 0 : countkwd == "" ? 0 : countkwd);
    if (kwds.length > 5000 - countkwd) {
        //alert("关键词个数大于" + (5000 - countkwd));
        assistantAlertPrompt.show("关键词个数大于" + (5000 - countkwd));
        return
    }


    $("#AddKeywords").css("display", "none");
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });

    $("#SaveSet").css({
        left: ($("body").width() - $("#SaveSet").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#SaveSet").height()) / 2 + $(window).scrollTop() + "px",
        display: "block"
    });
    $(".close").click(function () {
        $(".TB_overlayBG").css("display", "none");
        $("#SaveSet").css("display", "none");
    });
}
function countAddKwd() {
    var kwd = $("#statusNew").val().split("\n");
    kwd.forEach(function (e, i) {
        kwd[i] = e = e.trim();
        if (kwd.length != i + 1 && e.replace(/\s/g, "") == "") {
            kwd.splice(i, 1);
        }
    });

    $("#statusNew").val(kwd.toString().replace(/\,/g, "\n"));
    if (kwd[kwd.length - 1] == "") {
        kwd.splice(kwd.length - 1, 1);
    }
    var countkwd = $("#countkwd").val();
    countkwd = (countkwd == undefined ? 0 : countkwd == "" ? 0 : countkwd);
    if (kwd.length > 5000 - countkwd) {
        $("#counterNew").attr("style", "color:red");
    } else {
        if ($("#counterNew").attr("style") == "color:red") {
            $("#counterNew").attr("style", "font-weight:normal");
        }
    }
    document.getElementById("counterNew").innerHTML = kwd.length;
}

function countChar(textareaName, spanName) {
    document.getElementById(spanName).innerHTML = document.getElementById(textareaName).value.length;
}
