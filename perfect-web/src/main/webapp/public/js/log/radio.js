$(function() {

  //弹出框样式
  var winH=$(window).height();
  var winW=$(window).width();
  $(".black").css({
    height:winH,
    width: winW
  });
 judgeRadio();
//选择单选按钮
 $('.radioselect-box label').click(function(){   	
    var radioId = $(this).attr('name');
    var thisradio=$(this);   
    	//提示框
  	$(".tip").show();
 	$(".close-tip").click(function(event) {
 			$(".tip").hide();
 	});
 	 $(".operation .cancel").click(function(event) {
 			$(".tip").hide();
 	});

 	 $(".submit").click(function(event) {
    // $(".list-view").find('#'+RadiocheckedId).parent().parent().prevAll().find(" :checkbox").attr("checked", 'checked');
    $(".list-view-box  :checkbox").attr('disabled', false);
 	 	$('.radioselect-box label').removeAttr('class') && $(thisradio).attr('class', 'checked');
 	 	//alert($(thisradio).attr('class'));
 	  	$('input[type="radio"]').removeAttr('checked') && $('#' + radioId).attr('checked', 'checked');
 	  	//alert($('#' + radioId).attr('checked'));
 	  	$(".tip").hide();
      //推广账户选择  添加对象不可点
       judgeRadio();

      //禁用复选框
      var RadiocheckedId = radioId.substring(6) ;
     //alert(radioId.substring(6)) ;  
     //alert($(".list-view").find('#'+RadiocheckedId).parent().parent().attr('class'));
     //$(".list-view").find('#'+RadiocheckedId).parent().parent().prevAll().find(" :checkbox").attr('disabled', 'true');
    
    // alert(disabledCheck);
        
//alert(RadiocheckedId);
       // 判断推广单元一下的一层的操作
       if(RadiocheckedId == "gjc" || RadiocheckedId == "cy" ){
          $(".list-view").find('#'+RadiocheckedId).parent().parent().siblings().find(" :checkbox").attr('disabled', 'true');
       }
       else if(RadiocheckedId == "xj" || RadiocheckedId == "tgdh" || RadiocheckedId == "sqydzx" ){
          $(".list-view-box").find('#'+RadiocheckedId).parent().parent().siblings().find(" :checkbox").attr('disabled', 'true');
          $(".list-view-r").find('#'+RadiocheckedId).siblings().find(" :checkbox").attr('disabled', 'true');
       }else{
         var  disabledCheck= $(".list-view").find('#'+RadiocheckedId).parent().parent().prevAll().find(" :checkbox");
         $(disabledCheck).attr('disabled', 'true');
       }
        AllCheck();
 	 });

  });

//选择操作弹出框
 $(".ctrlbuttonoptSelect").click(function(event) {
 	$(".operation-tip").show();


 });


//默认全选中
 $("#operation-tip-list :checkbox").attr("checked", true); 


//获取选中的值
$(".operation-tip .submit").click(function(){ 
    var valArr = new Array; 
    $("#operation-tip-list :checkbox[checked]").each(function(i){ 
        valArr[i] = $(this).attr('id'); 
    }); 
    var vals = valArr.join(',');//转换为逗号隔开的字符串 
    alert(vals); 
}); 


//取消选中任何一个复选框跳到自定义 
$("#operation-tip-list :checkbox").click(function(){ 
    allchk(); 
});

//分组全选-左侧
$(".list-view-box .list-view :checkbox").click(function(){ 
    if($(this).attr("checked")=='checked'){ 
     $(this).parent().siblings().find(":checkbox[disabled!='disabled']").attr("checked", 'checked');
    }  
     else{
      $(this).parent().siblings().find(":checkbox[disabled!='disabled']").removeAttr("checked");  
     }    
}); 
//分组全选-右侧
$(".list-view-box .list-view-r :checkbox").click(function(){ 
    var thisCheckbox = $(this);
    groupallchk(thisCheckbox);
});

/* -----选择操作弹出框-----*/
$(".operation-tip .operation .cancel").click(function(event) {
    $(".operation-tip").hide();
});
$(".close-tip").click(function(event) {
    $(".operation-tip").hide();
});

$(".plan-box .close-ico").click(function(event) {
   $(".plan-box").hide();
});



});



//判断是全选还是自定义
function allchk(){ 
    var chknum = $("#operation-tip-list :checkbox").size();//选项总个数 
    var chk = 0; 
    $("#operation-tip-list :checkbox").each(function () {   
        if($(this).attr("checked")=='checked'){ 
            chk++; 
        } 
     });    
    if(chk==chknum){//全选     
      $("#operation-tip-list :checkbox").attr("checked", true); 
      $(".operation-tip #user-defined").removeAttr('class');    
      $(".operation-tip #all-check").attr('class', 'check-now');
    }else{//不全选 
      $(".operation-tip #all-check").removeAttr('class');
      $(".operation-tip #user-defined").attr('class', 'check-now');
    }

}  
//分组全选
function groupallchk(a){ 
  var thisCheck = a;
 // alert($(thisCheck).attr('id'));
  var thisCheckP = $(thisCheck).parent().parent().parent();
  var chknum =  $(thisCheckP).find(':checkbox').size();
  //alert(chknum);
    //var chknum = $(".list-view-r :checkbox").size();//选项总个数 
    var chk = 0; 

    $(thisCheckP).find(':checkbox').each(function () {   
        if($(this).attr("checked")=='checked'){ 
            chk++; 
        } 
     });
    //alert(chk);
    if(chk>0 ){//全选  
     $(thisCheckP).siblings().find(':checkbox').attr("checked", true);     
    }else 
    {//不全选 
      $(thisCheckP).siblings().find(':checkbox').removeAttr('checked');
    }

} 
function addHistoryRadio(){
  var content = '';
    for(var i=0;i<mainJson.length;i++){
      var item = mainJson[i];
      if(i==0){
        content += '<input type="radio" checked="checked"  name="historyType"';
        content += ' value="'+item.id+'" id="radio-'+item.id+'">';
        content += '<label class="checked" name="radio-'+item.id+'" for="radio-'+item.id+'">' + item.name;
        content += '</label>';       
      }
      else{
        content += '<input type="radio" name="historyType"';
        content += ' value="'+item.id+'" id="radio-'+item.id+'">';
        content += '<label  name="radio-'+item.id+'" for="radio-'+item.id+'">' + item.name;
        content += '</label>';
      }
    }
      content += '附加创意：';
    for(var i=0;i<fjcyJson.length;i++){
      var item = fjcyJson[i];
        content += '<input type="radio" name="historyType"';
        content += ' value="'+item.id+'" id="radio-'+item.id+'">';
        content += '<label   name="radio-'+item.id+'" for="'+item.id+'">' + item.name;
        content += '</label>';


    }
    //onclick="labelclick();"
    content += '';
    var radioDiv = document.getElementById("radioselect-box");
    //radioDiv.innerHTML = content;
    radioDiv.innerHTML=content;
}

function addHistoryCheckbox(){
  var Tipcontent = '';
    for(var i=0,j=mainJson.length;i<j;i++){
      var item = mainJson[i];
      Tipcontent += '<div class="list-view-box"><div class="list-view">';
      Tipcontent += '<input type="checkbox" id="'+item.id+'">';
      Tipcontent += '<label for="'+item.id+'">'+item.name+'</label>';
      Tipcontent += '</div>';
      Tipcontent += '<div class="list-view-r"><ul>';
      var data = item.data;
      for(var m=0,n=data.length;m<n;m++){
        var t = data[m];
        Tipcontent += '<li>'
        Tipcontent += '<input type="checkbox" id="'+t.id+'">';
         Tipcontent += '<label for="'+t.id+'">'+t.name+'</label>';
        Tipcontent += '</li>'
      }
      Tipcontent += '</ul></div></div>';
    }
   Tipcontent +='<div class="list-view-box"><div class="list-view"><input type="checkbox" id="fjcy"><label for="fjcy">附加创意</label></div><div class="list-view-r">';

   for(var i=0,j=fjcyJson.length;i<j;i++){
      var item = fjcyJson[i];
      Tipcontent += '<ul id="'+item.id+'">';
      var data = item.data;
      for(var m=0,n=data.length;m<n;m++){
        var t = data[m];
        Tipcontent += '<li>'
        Tipcontent += '<input type="checkbox" id="'+t.id+'">';
         Tipcontent += '<label for="'+t.id+'">'+t.name+'</label>';
        Tipcontent += '</li>'
      }
      Tipcontent += '</ul>';
    }
    Tipcontent +='</div></div>';
    var div = document.getElementById("operation-tip-list");
    div.innerHTML= Tipcontent;
}

function judgeRadio(){
  //推广账户选择  添加对象不可点
  if($("#radio-tgzh").attr('checked')=='checked'){
    $(".add-obj a").removeAttr('href');
    $(".add-obj a").addClass('none');
    $("#addobj-button").click(function(event) {
       /* Act on the event */
       $(".plan-box").hide();
    });      
    $(".obj-select-list").html("");
      var accountName ="baidu-perfect2151880";
     var accountobj = '<div class="obj-list"><a href="#" >'+accountName+'</a><div class="obj-list-del"></div></div>';
     $(".obj-select-list").append(accountobj);

  }
   else{
          $(".add-obj a").removeClass('none');
          $(".add-obj a").attr('href','javascript:;');
          AddObjPlan();
         $(".obj-select-list").html("");
          addPlan();    
         

        }
}
  //点击添加对象
function AddObjPlan(){
    $("#addobj-button").click(function(event) {
      /* Act on the event */
      $(".plan-box").show();
     // event.stopPropagation();
      //添加计划
        $(".query-result ul li a").click(function(event) {
        
          $(this).addClass('default');
          var addli = $(this).siblings().text();
          var addliId = $(this).attr('id');
              if($(".obj-select-list div.obj-list a[name="+addliId+"]").length<=0)
              {    
               //$(this).unbind('click');
            //<div class="obj-list" id="obj-list"><a href="#">baidu-perfect2151880</a></div>
             var addliBox =  '<div class="obj-list"><a href="#" name="'+addliId+'" >'+addli+'</a><div class="obj-list-del"></div></div>';
             $(".obj-select-list").append(addliBox);
            //对选定的对象列表进行操作
             operatObj();
            }
        });
    });
  /*  $(document).click(function(event) {  
       var _con = $('.obj-box');   // 设置目标区域
      if(!_con.is(event.target) && _con.has(event.target).length === 0){ // Mark 1
      //$('#divTop').slideUp('slow');   //滑动消失
      $('.plan-box').hide();          //淡出消失
      }
    });*/
}


function addPlan(){
/*  <li><a href="javascript:;">添加</a>test</li>*/
  var content = '<ul>';
    for(var i=0;i<planarry.length;i++){
      var item = planarry[i];
        content += '<li>';
        content += '<a href="javascript:;" id="'+item.id+'">添加</a><span>' + item.name+'</span>';
        content += '</li>';       
    }
    content +="</ul>"
   var queryR = document.getElementById("query-result");
    //radioDiv.innerHTML = content;
    queryR.innerHTML = content;            
}
//对选定的对象列表进行操作
function operatObj(){
   //选定列表按钮出现  
 $(".obj-list").hover(function() {
   $(this).children('.obj-list-del').show();
  $(".obj-list-del").click(function(event) {
   $(this).parent().remove();
   var thisN = $(this).siblings().attr('name');
   
   /*ar a= $(".query-result").find('#'+thisN).attr('id');
   alert(a);*/
   $(".query-result").find('#'+thisN).removeClass('default');
 });
 }, function() {
    $(this).children('.obj-list-del').hide();
 });  
}

//选择操作记录  全选
function  AllCheck(){
  //全选
$(".operation-tip #all-check").click(function(){  

      $(".operation-tip #all-check").attr('class', 'check-now'); 
      $("#operation-tip-list :checkbox[disabled!='disabled']").attr("checked", true);  
  $(".operation-tip #user-defined").removeAttr('class'); 

}); 

}
