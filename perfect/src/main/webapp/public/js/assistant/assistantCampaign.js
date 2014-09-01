//tbodyClick5

//子窗口拖动初始化
window.onload=function(){
    rDrag.init(document.getElementById('setFdKeywordDiv'));
    rDrag.init(document.getElementById('setExcludeIpDiv'));
    rDrag.init(document.getElementById('setExtensionDiv'));
    rDrag.init(document.getElementById('setScheduleDiv'));
}


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

    html = html + "<input type='hidden' value = "+obj.campaignId+" />";
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
    html = html+until.convert(obj.schedule==""||obj.schedule==null,"<td>全部</td>:"+"<td>已设置</td>");


    //推广地域！！！！！！！！！！！！
    html = html+until.convert(obj.regionTarget==null,"<td>使用账户推广地域</td>:"+"<td>使用账户推广地域</td>");


    var fd = obj.negativeWords.length;
    var jqfd = obj.exactNegativeWords.length;

    html = html+until.convert(fd==0&&jqfd==0,"<td>未设置</td>:"+"<td>"+fd+";"+jqfd+"</td>");

    html = html+"<td>"+obj.excludeIp.length+"</td>";

    html = html+until.convert(obj.budgetOfflineTime==null,"<td>-</td>:"+"<td>"+obj.budgetOfflineTime.length+"</td>");

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



/**
 *  发送编辑推广计划信息请求
 */
function editCampaignInfo(jsonData) {
    jsonData["cid"] = $("#hiddenCampaignId").val();
    $.ajax({
        url:"/assistantCampaign/edit",
        type:"post",
        data:jsonData
    });
}

/**
 * update
 * @param num
 * @param value
 */
function whenBlurEditCampaign(num,value){
    if($("#tbodyClick5").find("tr").length==0){
        return;
    }

    var jsonData = {};
    switch (num){
        case 1:jsonData["campaignName"] = value;break;
        case 2:jsonData["budget"] = value;break;
        case 3:jsonData["priceRatio"] = value;break;
        case 4:jsonData["schedule"] = value;break;
        case 5:jsonData["regionTarget"] = value;break;
        case 6:jsonData["isDynamicCreative"] = value;break;
        case 7:jsonData["negativeWords"] = value;break;
        case 8:jsonData["exactNegativeWords"] = value;break;
        case 9:jsonData["excludeIp"] = value;break;
        case 10:jsonData["showProb"] = value;break;
        case 11:jsonData["pause"] = value;break;
    }
    editCampaignInfo(jsonData);
    getCampaignList();
}


/**
 * 删除推广计划
 */
function deleteCampaign(){
    var cids = new Array();

    $("#tbodyClick5").find(".list2_box3").each(function(){
        cids.push($(this).find("input[type=hidden]").val());
    });
    if(cids.length==0){
        alert("请选择行再操作!");
        return;
    }

    var isDel = window.confirm("您确定要删除推广计划吗?");
    if(isDel==false){
        return;
    }

    $.ajax({
        url:"/assistantCampaign/delete",
        type:"post",
        data:{"cid":cids}
    });
    $("#tbodyClick5").find(".list2_box3").remove();
    setCampaignValue($("#tbodyClick5 tr:eq(0)"),$("#tbodyClick5 tr:eq(0)").find("input[type=hidden]").val());
}





/**
 * 否定关键词设置确定 单击事件
 */
$(".ntwOk").click(function(){
    var cid = $("#hiddenCampaignId").val();
    var negativeWords =  $("#ntwTextarea").val();
    var exactNegativeWords =  $("#entwTextarea").val();

    if(validateKeyword(negativeWords)==false){
        return;
    }
    if(validateKeyword(exactNegativeWords)==false){
        return;
    }
    whenBlurEditCampaign(7,negativeWords);
    whenBlurEditCampaign(8,exactNegativeWords);
    $(".TB_overlayBG,#setNegtiveWord").hide(0);

});

/*验证输入的否定关键词合法性*/
function validateKeyword(keywords){
    var kwds = keywords.split("\n");
    if(kwds.length>200){
        alert("设置的否定关键词最大不能超过200个!");
        return false;
    }
    for(var i = 0;i<kwds.length;i++){
        var len =  kwds[i].replace(/[^\x00-\xff]/g, 'xx').length;
        if(len>40){//否定关键词最大为40字节
            alert("关键词字节数最大不能超过40个字节。\n"+kwds[i]);
            return false;
        }
    }
    return true;
}




    //单击推广计划中的否定关键词设置的事件
    $(".negativeWords_5").click(function () {
        $("#ntwTextarea").val("");
        $("#entwTextarea").val("");


        var cid = $("#hiddenCampaignId").val();
        $.ajax({
            url:"/assistantCampaign/getObject",
            type:"post",
            data:{"cid":cid},
            dataType:"json",
            success:function(data){
                var negativeWords = data.negativeWords;
                var exactNegativeWords = data.exactNegativeWords;
                var ntwcontent = "";
                var exntcontent = "";

                for(var i = 0;i<negativeWords.length;i++){
                    if(i<negativeWords.length-1){
                        ntwcontent = ntwcontent+negativeWords[i]+"\r";
                    }else{
                        ntwcontent = ntwcontent+negativeWords[i];
                    }
                }
                for(var i = 0;i<exactNegativeWords.length;i++){
                    if(i<exactNegativeWords.length-1){
                        exntcontent = exntcontent+exactNegativeWords[i]+"\r";
                    }else{
                        exntcontent = exntcontent+exactNegativeWords[i];
                    }
                }
                $("#ntwTextarea").val(ntwcontent);
                $("#entwTextarea").val(exntcontent);
            }
        });

        setDialogCss("setNegtiveWord");
    });



//单击推广计划中的IP排除的事件
$(".excluedIp_5").click(function () {
    $("#IpListTextarea").val("");
    var cid = $("#hiddenCampaignId").val();

    $.ajax({
        url:"/assistantCampaign/getObject",
        type:"post",
        data:{"cid":cid},
        dataType:"json",
        success:function(data){
            var content = "";
            var excludeIp = data.excludeIp;
            for(var i = 0;i<excludeIp.length;i++){
                if(i<excludeIp.length-1){
                    content = content+excludeIp[i]+"\r";
                }else{
                    content = content+excludeIp[i];
                }
            }
            $("#IpListTextarea").val(content);
        }
    });

    setDialogCss("setExcludeIp");
});




/**
 * IP排除设置单击事件
 */
$(".excludeIpOk").click(function () {
    var cid = $("#hiddenCampaignId").val();
    var ipList =  $("#IpListTextarea").val();

    var ipArray = ipList.split("\n");
    var errorIp = "";

    if(ipArray.length>20){
        alert("IP排除数量最大为20个");
        return;
    }

    for(var i = 0;i<ipArray.length;i++){
        if(validateIPFormat(ipArray[i])==false){
            errorIp = errorIp+"\n"+ipArray[i];
        }
    }

    if(errorIp.length!=0){
        alert("IP地址格式输入不正确!"+errorIp);
        return;
    }
    whenBlurEditCampaign(9,ipList);
    $(".TB_overlayBG,#setExcludeIp").hide(0);
});



//单击推广计划中的推广时段的事件
$(".schedule_5").click(function(){
    createChooseTimeUI();
    setDialogCss("setExtension");
});




//单击设置推广时段窗口中确定按钮的事件
$(".scheduleOk").click(function () {
   //得到用户选择的时间段
    var schecdules =  getInputScheduleData();
    whenBlurEditCampaign(4,schecdules);
    $(".TB_overlayBG,#setExtension").hide(0);
});


//得到用户选择的数据
function getInputScheduleData() {
    var stringSchedule = "";
    $(".hours").find("input[type=checkbox]").each(function(){
        var is = $(this)[0];
        if(is.checked==false){
           var ul = $(this).parentsUntil("ul").parent();
           var lastHour=25;
           var startHour;
           var weekDay = $(this).attr("name");
            ul.find(".changeGray").each(function (index) {
                if(index==0){
                    startHour = $(this).html();
                }
                if((parseInt($(this).html())-1)>lastHour){
                    stringSchedule = stringSchedule+weekDay+"-";
                    stringSchedule=stringSchedule+startHour+"-";
                    stringSchedule=stringSchedule+(parseInt(lastHour)+1)+";";
                    startHour = $(this).html();
                }
                lastHour = $(this).html();
                if(ul.find(".changeGray").last().html()==$(this).html()){
                    stringSchedule = stringSchedule+weekDay+"-";
                    stringSchedule=stringSchedule+startHour+"-";
                    stringSchedule=stringSchedule+(parseInt($(this).html())+1)+";";
                }
            });

        }
    });
    return stringSchedule;
}

/**
 * 推广时段时间选择效果
 */
$(".hours").delegate("li","click",function(){
        if($(this).attr("class")=="changeGreen"){
            $(this).removeClass("changeGreen");
            $(this).addClass("changeGray");
        }else{
            $(this).removeClass("changeGray");
            $(this).addClass("changeGreen");
        }

        var ul = $(this).parent();
        if(ul.find(".changeGray").length==0){
            ul.find("input[type=checkbox]")[0].checked=true;
        }else{
            ul.find("input[type=checkbox]")[0].checked=false;
        }

});


/**
 * 设置推广时段的选择星期几的复选框事件
 */
$(".hours").delegate("input[type=checkbox]","click",function(){
    var ul = $(this).parentsUntil("ul").parent();
    if($(this)[0].checked==true){
        ul.find("li[class=changeGray]").removeClass("changeGray");
        ul.find("li[class='']").addClass("changeGreen");
    }else{

    }
});



/**
 * 生成选择推广时段的ui
 */

//campaignObj
function createChooseTimeUI(){
    var cid = $("#hiddenCampaignId").val();
    $.ajax({
        url:"/assistantCampaign/getObject",
        type:"post",
        data:{"cid":cid},
        dataType:"json",
        success:function(data){
            var weeks = new Array("星期一","星期二","星期三","星期四","星期五","星期六","星期日");
            var html = "";
            for(var i = 0;i<weeks.length;i++){
                if(data.schedule==""){
                    html = html+"<ul>"+"<div><input type='checkbox' checked='checked'' name='"+(i+1)+"'/>"+weeks[i]+"</div>";
                }else{
                    for(var s = 0;s<data.schedule.length;s++){
                        if(data.schedule[s].weekDay==(i+1)){
                            html = html+"<ul>"+"<div><input type='checkbox'  name='"+(i+1)+"'/>"+weeks[i]+"</div>";
                            break;
                        }
                    }
                    if(s>=data.schedule.length){
                        html = html+"<ul>"+"<div><input type='checkbox' checked='checked'' name='"+(i+1)+"'/>"+weeks[i]+"</div>";
                    }
                }

                var changeGrayArray = new Array();
                for(var m = 0;m<data.schedule.length;m++){
                    if(data.schedule[m].weekDay==(i+1)){
                        for(var n = 0;n<24;n++){
                            if(n>=data.schedule[m].startHour&&n<data.schedule[m].endHour){
                                changeGrayArray.push(n);
                            }
                        }
                    }
                }

                for(var j = 0;j<=23;j++){
                    var className = "changeGreen";

                    for(var a = 0;a<changeGrayArray.length;a++){
                        if(changeGrayArray[a]==j){
                            className = "changeGray";
                            break;
                        }
                    }

                    if(j%6==0){
                        html = html+"<li style='margin-left: 10px;' class='"+className+"'>"+j+"</li>";
                    }else{
                        html = html+"<li class='"+className+"'>"+j+"</li>"
                    }
                }
                html = html+"</ul><br/><br/>";
            }
            $(".hours").html(html);
        }
    });
}








/*显示设置推广地域窗口*/
$(".regionTarget_5").click(function () {
    setDialogCss("setSchedule");
});


/**
 * 得到用户选择的推广地域
 */
function getChooseRegionTarget() {

}









/**
 * 验证输入的ip格式是否正确
 */
function validateIPFormat(ipAddress) {
    var regex = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])|(\*))$/;
    return regex.test(ipAddress);
}


/**
 * 设置弹出框css
 * @param idSelector id选择器
 */
function setDialogCss(idSelector){
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    $("#"+idSelector).css({
        left:($("body").width()-$("#"+idSelector).width())/2-20+"px",
        top:($(window).height()-$("#"+idSelector).height())/2+$(window).scrollTop()+"px",
        display:"block"
    });
}


