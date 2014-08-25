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
        html = html+"<tr class='list2_box3' onclick='setCampaignValue(this)'>";
    }else if(index%2!=0){
        html = html+"<tr class='list2_box2' onclick='setCampaignValue(this)'>";
    }else{
        html = html+"<tr class='list2_box1' onclick='setCampaignValue(this)'>";
    }

    html = html+"<td>"+obj.campaignName+"</td>";

    switch (obj.status){
        case 21: html = html+"<td>有效</td>";break;
        case 22: html = html+"<td>处于暂停时段</td>";break;
        case 23: html = html+"<td>暂停推广</td>";break;
        case 24: html = html+"<td>推广计划预算不足</td>";break;
        case 25: html = html+"<td>账户预算不足</td>";break;
    }

    if(obj.pause==true){
        html = html+"<td>暂停</td>";
    }else{
        html = html+"<td>启用</td>";
    }

    if(obj.budget==null){
        html = html+"<td>&lt;不限定&gt;</td>";
    }else{
        html = html+"<td>"+obj.budget+"</td>";
    }

    if(obj.showProb==1){
        html = html+"<td>优选</td>";
    }else{
        html = html+"<td>轮显</td>";
    }

    if(obj.isDynamicCreative==true){
        html = html+"<td>开启</td>";
    }else{
        html = html+"<td>关闭</td>";
    }

    if(obj.schedule==null){
        html = html+"<td>全部</td>";
    }else{
        html = html+"<td>已设置</td>";
    }


    //推广地域！！！！！！！！！！！！
    if(obj.regionTarget==null){
        html = html+"<td>使用账户推广地域</td>";
    }else{
        html = html+"<td>使用账户推广地域</td>";
    }


    var fd = obj.negativeWords.length;
    var jqfd = obj.exactNegativeWords.length;

    if(fd==0&&jqfd==0){
        html = html+"<td>未设置</td>";
    }else{
        html = html+"<td>"+fd+":"+jqfd+"</td>";
    }

    html = html+"<td>"+obj.excludeIp.length+"</td>";

    if(obj.budgetOfflineTime==null){
        html = html+"<td>-</td>";
    }else{
        html = html+"<td>"+obj.budgetOfflineTime.length+"</td>";
    }

    //!!!!!!
    html = html+"<input type='hidden' value="+obj.priceRatio+" class='hidden'/>";

    html = html+"</tr>";

    $("#tbodyClick5").append(html);
}

//加载该页面就开始加载数据
getCampaignList();


/**
 *单击某一行的时候设置文本框值
 */
function setCampaignValue(obj){
    var _tr = $(obj);
    $(".campaignName_5").val(_tr.find("td:eq(0)").html());
    $(".budget_5").val(_tr.find("td:eq(3)").html());
    $(".priceRatio_5").val(_tr.find(".hidden").val());
    $(".schedule_5").html("<a>"+_tr.find("td:eq(6)").html()+"</a>");
    $(".regionTarget_5").html("<a>"+_tr.find("td:eq(7)").html()+"</a>");
    $(".isDynamicCreative_5").html("<a>"+_tr.find("td:eq(5)").html()+"</a>");
    until.convert(_tr.find("td:eq(8)").html()!="未设置","<a>已设置("+_tr.find("td:eq(8)").html()+")</a>:<a>未设置</a>");
    until.convert(_tr.find("td:eq(9)").html()=="0","<a>未设置</a>"+":"+"<a>已设置("+_tr.find("td:eq(9)").html()+")</a>");

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




