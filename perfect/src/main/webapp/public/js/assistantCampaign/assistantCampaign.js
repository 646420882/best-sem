//tbodyClick5


/**
 * 得到所有推广计划
 */
function getCampaignList(){
    $.ajax({
        url:"/assistantCampaign/list",
        type:"post",
        dataType:"json",
        success:function(data){
            for(var i=0;i<data.length;i++){
                campaignDataToHtml(data[i],i);
            }
        }
    });

}


/**
 * 将一条推广计划数据添加到html
 * @param obj 传入的单个推广计划对象
 * @param index 传入对象的下标
 */
function campaignDataToHtml(obj,index){
    var html = "";

    if(index==0){
        html = html+"<tr class='list2_box3 firstCampaign' onclick='setCampaignValue(this,"+obj.campaignId+")'>";
    }else if(index%2!=0){
        html = html+"<tr class='list2_box2' onclick='setCampaignValue(this,"+obj.campaignId+")'>";
    }else{
        html = html+"<tr class='list2_box1' onclick='setCampaignValue(this,"+obj.campaignId+")'>";
    }

    html = html+"<td>"+obj.campaignName+"</td>";

    switch (obj.status){
        case 21: html = html+"<td>有效</td>";break;
        case 22: html = html+"<td>处于暂停时段</td>";break;
        case 23: html = html+"<td>暂停推广</td>";break;
        case 24: html = html+"<td>推广计划预算不足</td>";break;
        case 25: html = html+"<td>账户预算不足</td>";break;
    }

    html = html+until.convert(obj.pause==true,"<td>暂停</td>:"+"<td>启用</td>")
    html = html+until.convert(obj.budget==null,"<td><不限定></td>:"+"<td>"+obj.budget+"</td>")
    html = html+until.convert(obj.showProb==1,"<td>优选</td>:"+"<td>轮显</td>")
    html = html+until.convert(obj.isDynamicCreative==true,"<td>开启</td>:"+"<td>关闭</td>");
    html = html+until.convert(obj.schedule==null,"<td>全部</td>:"+"<td>已设置</td>");


    //推广地域！！！！！！！！！！！！
    html = html+until.convert(obj.regionTarget==null,"<td>使用账户推广地域</td>:"+"<td>使用账户推广地域</td>");


    var fd = obj.negativeWords.length;
    var jqfd = obj.exactNegativeWords.length;
    html = html+until.convert(fd==0&&jqfd==0,"<td>未设置</td>:"+"<td>"+fd+":"+jqfd+"</td>");

    html = html+"<td>"+obj.excludeIp.length+"</td>";

    html = html+until.convert(obj.budgetOfflineTime==null,"<td>-</td>:"+"<td>"+obj.budgetOfflineTime.length+"</td>");

    //!!!!!!
    html = html+"<input type='hidden' value="+obj.priceRatio+" class='hidden'/>";
    html = html+"</tr>";
    $("#tbodyClick5").append(html);

    if(index==0){
        setCampaignValue(".firstCampaign",obj.campaignId);
    }
}

//加载该页面就开始加载数据
getCampaignList();


/**
 *单击某一行的时候设置文本框值
 */
function setCampaignValue(obj,campaignId){
    $("#hiddenCampaignId").val(campaignId);
    var _tr = $(obj);
    $(".campaignName_5").val(_tr.find("td:eq(0)").html());

    if(/^[0-9]+.[0-9]*$/.test(_tr.find("td:eq(3)").html())){
        $(".budget_5").val(_tr.find("td:eq(3)").html());
    }else{
        $(".budget_5").val("<不限定>");
    }

    $(".priceRatio_5").val(_tr.find(".hidden").val());
    $(".schedule_5").html("<a>"+_tr.find("td:eq(6)").html()+"</a>");
    $(".regionTarget_5").html("<a>"+_tr.find("td:eq(7)").html()+"</a>");
    $(".isDynamicCreative_5").html("<a>"+_tr.find("td:eq(5)").html()+"</a>");
    $(".negativeWords_5").html(until.convert(_tr.find("td:eq(8)").html()!="未设置","<a>已设置("+_tr.find("td:eq(8)").html()+")</a>:<a>未设置</a>"));
    $(".excluedIp_5").html(until.convert(_tr.find("td:eq(9)").html()=="0","<a>未设置</a>"+":"+"<a>已设置("+_tr.find("td:eq(9)").html()+")</a>"));

    if(_tr.find("td:eq(4)").html()=="优选"){
        $(".selectShowProb_5").html("<option value = '1' selected='selected'>优选</option><option value='2'>轮显</option>");
    }else{
        $(".selectShowProb_5").html("<option value = '1' >优选</option><option value='2' selected='selected'>轮显</option>");
    }

    $(".status_5").html(_tr.find("td:eq(1)").html());

    //IP排除??

    //推广计划状态
     if(_tr.find("td:eq(2)").html()=="启用"){
        $(".selectPause_5").html("<option value = 'false' selected='selected'>启用</option><option value='true'>暂停</option>");
     }else{
         $(".selectPause_5").html("<option value = 'false' >启用</option><option value='true' selected='selected'>暂停</option>");
     }
}


var cp_campaignId = null;
var cp_campaignName = null;
var cp_budget = null;
var cp_priceRatio = null;
var cp_schedule = null;
var cp_regionTarget = null;
var cp_isDynamicCreative = null;
var cp_negativeWords = null;
var cp_exactNegativeWords = null;
var cp_excludeIp = null;
var cp_showProb = null;
var cp_pause = null;
/**
 * 编辑推广计划信息
 */
function editCampaignInfo() {
    cp_campaignId = $("#hiddenCampaignId").val();
    $.ajax({
        url:"/assistantCampaign/edit",
        type:"post",
        data:{
            "cid":cp_campaignId,
            "campaignName":cp_campaignName,
            "budget":cp_budget,
            "priceRatio":cp_priceRatio,
            "schedule":cp_schedule,
            "regionTarget":cp_regionTarget,
            "isDynamicCreative":cp_isDynamicCreative,
            "negativeWords":cp_negativeWords,
            "exactNegativeWords":cp_exactNegativeWords,
            "excludeIp":cp_excludeIp,
            "showProb":cp_showProb,
            "pause":cp_pause
        }
    });

     cp_campaignId = null;
     cp_campaignName = null;
     cp_budget = null;
     cp_priceRatio = null;
     cp_schedule = null;
     cp_regionTarget = null;
     cp_isDynamicCreative = null;
     cp_negativeWords = null;
     cp_exactNegativeWords = null;
     cp_excludeIp = null;
     cp_showProb = null;
     cp_pause = null;
}


function whenBlurEditCampaign(num,value){
    switch (num){
        case 1:cp_campaignName = value;break;
        case 2:cp_budget = value;break;
        case 3:cp_priceRatio = value;break;
        case 4:cp_schedule = value;break;
        case 5:cp_regionTarget = value;break;
        case 6:cp_isDynamicCreative = value;break;
        case 7:cp_negativeWords = value;break;
        case 8:cp_exactNegativeWords = value;break;
        case 9:cp_excludeIp = value;break;
        case 10:cp_showProb = value;break;
        case 11:cp_pause = value;break;
    }
    editCampaignInfo();
}



