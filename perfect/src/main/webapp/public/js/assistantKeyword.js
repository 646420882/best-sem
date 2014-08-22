//得到当前账户的所有关键词
function getKwdList(){
    $.ajax({
        url:"/assistantKeyword/list",
        type:"post",
        dataType:"json",
        success:function(data){

            for(var i = 0;i<data.length;i++) {
                keywordDataToHtml(data[i],i);
            }
        }
    });
    }


/**
*将一条数据加到html中
*/
function keywordDataToHtml(obj,index){
    var html="";
    if(index==0){
    html=html+"<tr class='list2_box3' >";
    }else if(index%2!=0){
    html=html+"<tr class='list2_box2' >";
    }else{
    html=html+"<tr class='list2_box1' >";
    }


    html = html+"<td>"+obj.keyword+"</td>";

    switch (obj.status){
    case 41: html = html+"<td>有效</td>";break;
    case 42: html = html+"<td>暂停推广</td>";break;
    case 43: html = html+"<td>不宜推广</td>";break;
    case 44: html = html+"<td>搜索无效</td>";break;
    case 45: html = html+"<td>待激活</td>";break;
    case 46: html = html+"<td>审核中</td>";break;
    case 47: html = html+"<td>搜索量过低</td>";break;
    case 48: html = html+"<td>部分无效</td>";break;
    case 49: html = html+"<td>计算机搜索无效</td>";break;
    case 50: html = html+"<td>移动搜索无效</td>";break;
    }

    if(obj.pause==true){
    html=html+"<td>暂停</td>";
    }else{
    html=html+"<td>启用</td>";
    }

    html=html+"<td>"+obj.price+"</td>";

    //质量度
    html=html+"<td>一星</td>";
    html=html+"<td>二星</td>";

    //匹配模式
    var matchType;
    switch (obj.matchType){
    case 1:matchType="精确";break;
    case 2:if(obj.phraseType==1){
    matchType="短语-同义包含";
    }else if(obj.phraseType==2){
    matchType="短语-精确包含";
    }else{
    matchType="短语-核心包含";
    };break;
    case 3:matchType="广泛";break;
    }
    html=html+"<td>"+matchType+"</td>";


    //url
    var pcUrl="";
    var mobUrl="";
    if(obj.pcDestinationUrl!=null){
    pcUrl=obj.pcDestinationUrl.substr(0,35);
    }
    if(obj.mobileDestinationUrl!=null){
    mobUrl= obj.mobileDestinationUrl.substr(0,35);
    }
    html=html+"<td>"+pcUrl+"</td>";
    html=html+"<td>"+mobUrl+"</td>";
    html=html+"<td>推广单元名称</td>";
    html=html+"</tr>";
    $("#tbodyClick").append(html);

    }

getKwdList();
