/*加载列表数据start*/
//得到当前账户的所有关键词
function getKwdList() {
    $.ajax({
        url: "/assistantKeyword/list",
        type: "post",
        dataType: "json",
        success: function (data) {

            for (var i = 0; i < data.length; i++) {
                keywordDataToHtml(data[i], i);
            }
        }
    });
}


/**
 *将一条数据加到html中
 */
function keywordDataToHtml(obj, index) {
    var html = "";
    if (index == 0) {
        html = html + "<tr class='list2_box3 firstKeyword' onclick='setKwdValue(this,"+obj.keywordId+")'>";
    } else if (index % 2 != 0) {
        html = html + "<tr class='list2_box2' onclick='setKwdValue(this,"+obj.keywordId+")'>";
    } else {
        html = html + "<tr class='list2_box1' onclick='setKwdValue(this,"+obj.keywordId+")'>";
    }

    //kwid
    html = html + "<input type='hidden' value = "+obj.keywordId+" />";

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
    }

    html = html + "<td>"+until.convert(obj.pause,"暂停:启用")+"</td>";

    html = html + until.convert(obj.price==null,"<td><0.10></td>:<td>"+obj.price + "</td>");

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
                matchType = matchType+"-同义包含";
            } else if (obj.phraseType == 2) {
                matchType = matchType+"-精确包含";
            } else if(obj.phraseType == 3){
                matchType = matchType+"-核心包含";
            }
            ;
            break;
        case 3:
            matchType = "广泛";
            break;
    }
    html = html + "<td>" + matchType + "</td>";


    //url
    var pcUrl = "";
    var mobUrl = "";
    if (obj.pcDestinationUrl != null) {
        pcUrl = "<a href='"+obj.pcDestinationUrl+"'>"+obj.pcDestinationUrl.substr(0, 20)+"</a>";
    }
    if (obj.mobileDestinationUrl != null) {
        mobUrl = "<a href='"+obj.mobileDestinationUrl+"'>"+obj.mobileDestinationUrl.substr(0, 20)+"</a>";
    }
    html = html + "<td>" + pcUrl + "</td>";
    html = html + "<td>" + mobUrl + "</td>";
    html = html + "<td>推广单元名称</td>";
    html = html + "</tr>";
    $("#tbodyClick").append(html);

    if(index==0){
        setKwdValue($(".firstKeyword"),obj.keywordId);
    }
}


//进入这个页面就开始加载数据
getKwdList();

/*加载列表数据end*/


function setKwdValue(obj,kwid){
    $("#hiddenkwid_1").val(kwid);
    $(".keyword_1").val($(obj).find("td:eq(0)").html());


    $(".price_1").val($(obj).find("td:eq(3)").html());


    if($(obj).find("td:eq(7) a").attr("href")!=undefined){
        $(".pcurl_1").val($(obj).find("td:eq(7) a").attr("href"));
        $(".pcurlSize_1").html($(obj).find("td:eq(7) a").attr("href").length+"/1024");
    }else{
        $(".pcurl_1").val("");
        $(".pcurlSize_1").html("0/1024");
    }

    if($(obj).find("td:eq(8) a").attr("href")!=undefined){
        $(".mourl_1").val($(obj).find("td:eq(8) a").attr("href"));
        $(".mourlSize_1").html($(obj).find("td:eq(8) a").attr("href").length+"/1017");
    }else{
        $(".mourl_1").val("");
        $(".mourlSize_1").html("0/1017");
    }

    $(".matchModel_1").html($(obj).find("td:eq(6)").html());
    $(".status_1").html($(obj).find("td:eq(1)").html());

    if($(obj).find("td:eq(2)").html()=="启用"){
        $(".pause_1").html("<option value='true'>暂停</option><option value='false' selected='selected'>启用</option>");
    }else{
        $(".pause_1").html("<option value='true' selected='selected'>暂停</option><option value='false' >启用</option>");
    }
}



/**
 * 编辑关键词信息
 * @param value
 */
function editKwdInfo(jsonData){
    jsonData["kwid"] = $("#hiddenkwid_1").val();
    $.ajax({
        url:"/assistantKeyword/edit",
        type:"post",
        data:jsonData
    });
}


/**
 * 控件失去焦点时候触发
 * @param num
 * @param value
 */
function whenBlurEditKeyword(num,value){
    if($("#tbodyClick").find("tr").length==0){
        return;
    }
    var jsonData = {};
    switch (num){
        case 2:jsonData["price"] = value;break;
        case 3:jsonData["pcDestinationUrl"] = value;break;
        case 4:jsonData["mobileDestinationUrl"] = value;break;
        case 5:jsonData["matchType"] = value;break;
        case 6:jsonData["phraseType"] = value;break;
        case 7:jsonData["pause"] = value;break;
    }
    editKwdInfo(jsonData);
}


/**
 * 删除关键词
 */
function deleteKwd(){
    var ids = new Array();
    $("#tbodyClick").find(".list2_box3").each(function(){
        ids.push($(this).find("input[type=hidden]").val());
    });

    if(ids.length==0){
        alert("请选择行再操作!");
        return;
    }

    var isDel = window.confirm("您确定要删除关键词吗?");
    if(isDel==false){
        return;
    }
    $.ajax({
        url:"/assistantKeyword/deleteById",
        type:"post",
        data:{"kwids":ids}
    });

    $("#tbodyClick").find(".list2_box3").remove();

    setKwdValue($("#tbodyClick tr:eq(0)"),$("#tbodyClick tr:eq(0)").find("input[type=hidden]").val());
}





/**
 * 失去焦点
 */
function missBlur(even,obj){
    if(even.keyCode==13){
        obj.blur();
    }
}



/**
 * 单击该单元格的时候
 */
/*
$("#tbodyClick").delegate(".kwdEdit","click blur",function(event){
    if(event.type=="click"){
        var tdValue = $(this).html();
        $(this).html("<input type='text' value="+tdValue+" />");
    }else{
        var text = $(this).find("input").val();

    }
});
*/









