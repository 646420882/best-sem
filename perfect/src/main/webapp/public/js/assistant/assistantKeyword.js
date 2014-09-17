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
                var html = keywordDataToHtml(data.list[i], i);
                $("#tbodyClick").append(html);
                if (i == 0) {
                    setKwdValue($(".firstKeyword"), data.list[i].keywordId);
                    if(data.list[i].localStatus!=null){
                        $("#reduction").find("span").removeClass("z_function_hover");
                        $("#reduction").find("span").addClass("zs_top");
                    }else{
                        $("#reduction").find("span").removeClass("zs_top");
                        $("#reduction").find("span").addClass("z_function_hover");
                    }
                }
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
    var span = $(this).find("td:last");
    if(span.html()!="&nbsp;"){
        $("#reduction").find("span").removeClass("z_function_hover");
        $("#reduction").find("span").addClass("zs_top");
    }else{
        $("#reduction").find("span").removeClass("zs_top");
        $("#reduction").find("span").addClass("z_function_hover");
    }
    var obj = $(this);
    var keywordId = $(this).find("input[type=hidden]").val();
    setKwdValue(obj,keywordId);
});



/**
 *将一条数据加到html中
 */
function keywordDataToHtml(obj, index) {

    if(obj.keywordId==null){
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
        default:html = html+"<td>本地新增</td>";
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

    if(obj.localStatus!=null){
        if(obj.localStatus==3){
            html = html + "<td><span class='error' step='3'></span></td>";
        }else{
            html = html + "<td><span class='pen' step='"+obj.localStatus+"'></span></td>";
        }
    }else{
        html = html + "<td>&nbsp;</td>";
    }

    html = html + "</tr>";

    return html;
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
        data: jsonData,
        dataType:"json",
        success:function(data){
            var html = keywordDataToHtml(data,0);
            var tr = $("#tbodyClick").find(".list2_box3");
            tr.replaceWith(html);
        }
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
        data: {"kwids": ids},
        dataType:"json",
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


/**
 * 还原按钮的事件
 */
$("#reduction").click(function () {
    var choose = $("#tbodyClick").find(".list2_box3");
    if(choose!=undefined&&choose.find("td:last").html()!="&nbsp;"){
        if(confirm("是否还原选择的数据?")==false){
            return;
        }
        var step = choose.find("td:last span").attr("step");
        var id = $("#tbodyClick").find(".list2_box3").find("input[type=hidden]").val();
        switch (parseInt(step)){
            case 1:reducKwd_Add(id);break;
            case 2:reducKwd_update(id);break;
            case 3:reducKwd_del(id);break;
            case 4:alert("属于单元级联删除，如果要恢复该数据，则必须恢复单元即可！");break;
        }

    }
});


/**
 * 还原新增的关键词(localStatus为1的)
 * @param id
 */
function reducKwd_Add(id) {
    $.ajax({
        url:"/assistantKeyword/reducAdd",
        type:"post",
        data:{"id":id},
        dataType:"json",
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
        url:"/assistantKeyword/reducUpdate",
        type:"post",
        data:{"id":id},
        dataType:"json",
        success: function (data) {
            var html = keywordDataToHtml(data,0);
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
        url:"/assistantKeyword/reducDel",
        type:"post",
        data:{"id":id},
        dataType:"json",
        success: function (data) {
           $("#tbodyClick").find(".list2_box3 td:last").html("&nbsp;");
        }
    });
}
