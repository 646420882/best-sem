var htmlArr = new Array();
$(function () {
    //弹出框样式
    var winH = $(window).height();
    var winW = $(window).width();
    $(".black").css({
        height: winH,
        width: winW
    });
    addHistoryRadio();
    addHistoryCheckbox();
    judgeRadio(9);
    //选择单选按钮
    var thisradio;
    $('.radioselect-box label').click(function () {
        
        //var radioId = $(this).attr('value');
         thisradio = $(this);
        $('.radioselect-box label').removeAttr('class') && $(thisradio).attr('class', 'checked');
        //提示框
        $(".tip").show();
        $(".close-tip").click(function (event) {
            $(".tip").hide();
        });
        $(".operation .cancel").click(function (event) {
            $(".tip").hide();
        });
    });
    //清空数据提示
    $(".submit").click(function (event) {
        $(".list-view-box  :checkbox").attr('disabled', false);
        $('.radioselect-box label').removeAttr('class') && $(thisradio).attr('class', 'checked');
        var RadiocheckedId = $("label[class='checked']").attr("value");

        $(".tip").hide();
        //推广账户选择  添加对象不可点
        judgeRadio(RadiocheckedId);

        //禁用复选框
        
        //alert(radioId.substring(6)) ;  
        //alert($(".list-view").find('#'+RadiocheckedId).parent().parent().attr('class'));
        //$(".list-view").find('#'+RadiocheckedId).parent().parent().prevAll().find(" :checkbox").attr('disabled', 'true');

        // alert(disabledCheck);
       
        //alert(RadiocheckedId);
        // 判断推广单元一下的一层的操作
        if (RadiocheckedId == "2" || RadiocheckedId == "5") {
            $(".list-view").find('#cbMain' + RadiocheckedId).parent().parent().siblings().find(" :checkbox").attr('disabled', 'true');
        }
        else if (RadiocheckedId == "3" || RadiocheckedId == "4" || RadiocheckedId == "6") {
            $(".list-view-box").find('#cbMain' + RadiocheckedId).parent().parent().siblings().find(" :checkbox").attr('disabled', 'true');
            $(".list-view-r").find('#cbMain' + RadiocheckedId).siblings().find(" :checkbox").attr('disabled', 'true');
        } else {
            var disabledCheck = $(".list-view").find('#cbMain' + RadiocheckedId).parent().parent().prevAll().find(" :checkbox");
            $(disabledCheck).attr('disabled', 'true');
        }
        AllCheck();
    });

    //选择操作弹出框
    $(".ctrlbuttonoptSelect").click(function (event) {
        $(".operation-tip").show();
     });


    //默认全选中
    $("#operation-tip-list :checkbox").attr("checked", true);


    //获取选中的值
    $(".operation-tip .submit").click(function () {
    	$(".operation-tip").hide();
    });
    //获取复选框选中的值
    function getCheckBoxsValue() {
        var valArr = new Array;
        $("input[name='cbSub']").each(function (i) {

            if ($(this).prop("disabled") == false && ($(this).prop("checked"))) {
                valArr.push($(this).attr('value'));
            }
        });
        var vals = valArr.join(',');//转换为逗号隔开的字符串 
        return vals;
    }
   


    //取消选中任何一个复选框跳到自定义 
    $("#operation-tip-list :checkbox").click(function () {
        allchk();
    });

    //分组全选-左侧
    $(".list-view-box .list-view :checkbox").click(function () {
        if ($(this).prop("checked")) {
        	$(this).parent().siblings().find("input[name='cbSub']").attr("checked", true);
        }
        else {
            $(this).parent().siblings().find(":checkbox[disabled!='disabled']").removeAttr("checked");
        }
    });
    //分组全选-右侧
    $(".list-view-box .list-view-r :checkbox").click(function () {
        var thisCheckbox = $(this);
        groupallchk(thisCheckbox);
    });

    /* -----选择操作弹出框-----*/
    $(".operation-tip .operation .cancel").click(function (event) {
        $(".operation-tip").hide();
    });
    $(".close-tip").click(function (event) {
        $(".operation-tip").hide();
    });

    $(".plan-box .close-ico").click(function (event) {
        $(".plan-box").hide();
    });

    $('#reservation').daterangepicker({
        "showDropdowns": true,
        "timePicker24Hour": true,
        timePicker: false,
        timePickerIncrement: 30,
        "linkedCalendars": false,
        "format": "YYYY-MM-DD",
        autoUpdateInput: true,
        "locale": {
            "format": "YYYY-MM-DD",
            "separator": " - ",
            "applyLabel": "确定",
            "cancelLabel": "关闭",
            "fromLabel": "From",
            "toLabel": "To",
            "customRangeLabel": "Custom",
            "daysOfWeek": [
                "日",
                "一",
                "二",
                "三",
                "四",
                "五",
                "六"
            ],
            "monthNames": [
                "一月",
                "二月",
                "三月",
                "四月",
                "五月",
                "六月",
                "七月",
                "八月",
                "九月",
                "十月",
                "十一月",
                "十二月"
            ]
        }
    },
    function (start, end, label) {
    	//alert(new Date(start).getTime());
    	$("#start_time").val(new Date(start).getTime());
    	$("#end_time").val(new Date(end).getTime());
    });


 // 对Date的扩展，将 Date 转化为指定格式的String 
 // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
 // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
 // 例子： 
 // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
 // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
 Date.prototype.Format = function(fmt) 
 { //author: meizz 
   var o = { 
     "M+" : this.getMonth()+1,                 //月份 
     "d+" : this.getDate(),                    //日 
     "h+" : this.getHours(),                   //小时 
     "m+" : this.getMinutes(),                 //分 
     "s+" : this.getSeconds(),                 //秒 
     "q+" : Math.floor((this.getMonth()+3)/3), //季度 
     "S"  : this.getMilliseconds()             //毫秒 
   }; 
   if(/(y+)/.test(fmt)) 
     fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
   for(var k in o) 
     if(new RegExp("("+ k +")").test(fmt)) 
   fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
   return fmt; 
 }
 var isQuery=false;
    $("#query_but").click(function (event) {
        	isQuery=true;
        	query_data(1);
    });
    
    
    function query_data(page_index){
    	
        var RadiocheckedId = $("label[class='checked']").attr("value");
        var arr=$(".obj-select-list div.obj-list a");
        if(arr.length==0){
        	alert("请添加对象");
        	return;
        }
        var optContent=[];
        for(var i=0;i<arr.length;i++){
        	optContent[i]=$(arr[i]).attr('name');
        }
    	
//    	alert(optContent.join(","));
//    	 alert(getCheckBoxsValue());
//    	 
    	 //获取 所有权限数据
        var params={
 			level:RadiocheckedId,
 			opt_id:optContent.join(","),
 			opt_obj:getCheckBoxsValue(),
 			start_time:$("#start_time").val(),
 			end_time:$("#end_time").val(),
 			page_index:page_index,
 			page_size:20
 		}
     	$.ajax({
     		url:'/assistant/queryLog',// 跳转到 action
     		data:params,
     	    type:'post',    
     	    cache:false,    
     	    dataType:'json',    
     	    success:function(data) { 
     	    	if(data.success){
     	    		var d=data.lists;
     	    		if(d.length>0){
     	    			var downParam="/assistant/download?level="+params.level+"&opt_id="+params.opt_id;
     	    				downParam+="&opt_obj="+params.opt_obj+"&start_time="+params.start_time+"&end_time="+params.end_time;
     	    			
     	    			$("#downloadhistory").attr("href",downParam);
     	    			$("#downloadhistory").css("display","block");
     	    		}
     	    		var content='';
     	    		$('#tbodydata').html('');
     	    		
     	    		for(var i=0;i<d.length;i++){
     	    			content+='<tr>'
     	    				var obj=d[i];
     	    				content +='<td>'+new Date(obj.optTime).Format("yyyy-MM-dd hh:mm:ss")+'</td>';
     	    				content +='<td>'+obj.bdfcName+'</td>';
     	    				content +='<td>'+obj.dName+'</td>';
     	    				content +='<td>'+obj.optContent+'</td>';
     	    				if(obj.optTypeName.indexOf('修改')!=-1||obj.optTypeName.indexOf('编辑')!=-1){
     	    					content +='<td>'+obj.optTypeName+obj.dName+obj.optContent+","+obj.optObj+'旧值'+obj.oldValue+'，新值'+obj.newValue+'</td>';
     	    				}else{
     	    					content +='<td>'+obj.optTypeName+obj.dName+obj.optContent+'</td>';
     	    				}
     	    				
     	    				content+='</tr>';
     	    		}
     	    		$('#tbodydata').html(content);
     	    		if(isQuery){
     	    			$("#pagination").pagination(data.total, {
                            callback: PageCallback,//() 为翻页调用次函数。
                            prev_text: " 上一页",
                            next_text: "下一页 ",
                            items_per_page: data.pageSize, //每页的数据个数
                            num_display_entries: 4, //两侧首尾分页条目数
                               //当前页码
                            num_edge_entries: 3,//连续分页主体部分分页条目数
                            link_to:"#pagination"

                        });
     	    			
     	    		}
     	    		
     	    		
     	    	}
     	    	
     	     },    
     	     error : function() {    
     	             
     	          alert("异常！");    
     	     } 
     	}); 
    }
    
    
    function PageCallback(page_index, jq){
    	if(!isQuery){
    		alert(1);
    		query_data(page_index+1);
    	}else{
    		isQuery=false;
    	}
    }
    
    $("#btnsearch").click(function () {
        var newArr = new Array();
        var keystr = $("#txtkey").val();
        if (keystr!="") {
            $.each(htmlArr, function (i) {
                var cname = $(this).find("span").text();
                if (cname.indexOf(keystr) != -1) {
                    newArr.push(htmlArr[i]);
                }
            });
        }
        else {
       
            newArr = htmlArr;
        }
        setPlanByHTML(newArr);
    });
    

    $("#addobj-button").click(function (event) {
    	var RadiocheckedId = $("label[class='checked']").attr("value");
  	  addPlan(RadiocheckedId);

      /* Act on the event */
      $(".plan-box").show();
      // event.stopPropagation();
      //添加计划
      $(".query-result ul li a").click(function (event) {

          $(this).addClass('default');
          var addli = $(this).siblings().text();
          var addliId = $(this).attr('id');
          if ($(".obj-select-list div.obj-list a[name=" + addliId + "]").length <= 0) {
              //$(this).unbind('click');
              //<div class="obj-list" id="obj-list"><a href="#">baidu-perfect2151880</a></div>
              var addliBox = '<div class="obj-list"><a href="#" name="' + addliId + '" >' + addli + '</a><div class="obj-list-del"></div></div>';
              $(".obj-select-list").append(addliBox);
              //对选定的对象列表进行操作
              operatObj();
          }
      });
  });
});



//判断是全选还是自定义
function allchk() {
    var chknum = $("#operation-tip-list :checkbox").size();//选项总个数 
    var chk = 0;
    $("#operation-tip-list :checkbox").each(function () {
        if ($(this).prop("checked")) {
            chk++;
        }
    });
    if (chk == chknum) {//全选     
        $("#operation-tip-list :checkbox").attr("checked", true);
        $(".operation-tip #user-defined").removeAttr('class');
        $(".operation-tip #all-check").attr('class', 'check-now');
    } else {//不全选 
        $(".operation-tip #all-check").removeAttr('class');
        $(".operation-tip #user-defined").attr('class', 'check-now');
    }

}
//分组全选
function groupallchk(a) {
    var thisCheck = a;
    // alert($(thisCheck).attr('id'));
    var thisCheckP = $(thisCheck).parent().parent().parent();
    var chknum = $(thisCheckP).find(':checkbox').size();
    //alert(chknum);
    //var chknum = $(".list-view-r :checkbox").size();//选项总个数 
    var chk = 0;

    $(thisCheckP).find(':checkbox').each(function () {
        if ($(this).prop("checked")) {
            chk++;
        }
    });
    //alert(chk);
    if (chk > 0) {//全选  
        $(thisCheckP).siblings().find(':checkbox').attr("checked", true);
    } else {//不全选 
        $(thisCheckP).siblings().find(':checkbox').removeAttr('checked');
    }

}
//创建层级分类主类 单选按钮组
function addHistoryRadio() {

    var jsons = eval(optLevels);
    var content = '';
    var len = jsons.length;
    if (len > 5) {
        len = len - (len - 5);
    }
    for (var i = 0; i < len; i++) {
        var item = jsons[i];
        if (i == 0) {
            content += '<label class="checked" name="radiolab" id="radio-' + item.id + '" for="radio-' + item.id + '" value="' + item.id + '">' + item.cname;
            content += '</label>';
        }
        else {
            content += '<label  name="radiolab" id="radio-' + item.id + '"  for="radio-' + item.id + '" value="' + item.id + '">' + item.cname;
            content += '</label>';
        }
    }
    if (len > 5) {
        content += '附加创意：';
        for (var i = jsons.length - (len - 5) ; i < jsons.length; i++) {
            var item = jsons[i];
            content += '<label   name="radiolab" id="radio-' + item.id + '" for="radio-' + item.id + '" value="' + item.id + '">' + item.cname;
            content += '</label>';
        }
    }
    content += '';
    var radioDiv = $("#radioselect-box");
    radioDiv.html(content);
}
//创建层级分类 复选框按钮组
function addHistoryCheckbox() {
    var jsons = eval(optLevels);
    var Tipcontent = '';
    var len = jsons.length;
    if (len > 5) {
        len = len - (len - 5);
    }
    for (var i = 0; i < len; i++) {
        var item = jsons[i];
        Tipcontent += '<div class="list-view-box"><div class="list-view">';
        Tipcontent += '<input type="checkbox" name="cbMain" id="cbMain' + item.id + '" value="' + item.id + '">';
        Tipcontent += '<label for="cbMain' + item.id + '">' + item.cname + '</label>';
        Tipcontent += '</div>';
        Tipcontent += '<div class="list-view-r"><ul>';
        var data = item.optcontents;
        for (var m = 0, n = data.length; m < n; m++) {
            var t = data[m];
            Tipcontent += '<li>'
            Tipcontent += '<input type="checkbox" name="cbSub" id="cbSub' + t.id + '" value="' + t.id + '">';
            Tipcontent += '<label for="cbSub' + t.id + '">' + t.cname + '</label>';
            Tipcontent += '</li>'
        }
        Tipcontent += '</ul></div></div>';
    }
    if (len > 5) {
        Tipcontent += '<div class="list-view-box"><div class="list-view"><input type="checkbox" id="fjcy" value="0"><label for="fjcy">附加创意</label></div><div class="list-view-r">';
        for (var j = jsons.length - (len - 5) ; j < jsons.length; j++) {
            var item = jsons[j];
            Tipcontent += '<ul id="cbMain' + item.id + '">';
            var data = item.optcontents;
            for (var m = 0, n = data.length; m < n; m++) {
                var t = data[m];
                Tipcontent += '<li>'
                Tipcontent += '<input type="checkbox" name="cbSub" id="cbSub' + t.id + '" value="' + t.id + '">';
                Tipcontent += '<label for="' + t.id + '">' + t.cname + '</label>';
                Tipcontent += '</li>'
            }
            Tipcontent += '</ul>';
        }
        Tipcontent += '</div></div>';
    }
    var div = $("#operation-tip-list");
    div.html(Tipcontent);
}

function judgeRadio(type) {
	 $(".obj-select-list").html("");
    //推广账户选择  添加对象不可点
    if (type==9) {
        $(".add-obj a").removeAttr('href');
        $(".add-obj a").addClass('none');
        $("#addobj-button").click(function (event) {
            /* Act on the event */
            $(".plan-box").hide();
        });
       
        
      //获取 所有权限数据
    	$.ajax({
    		url:'/assistant/account',// 跳转到 action
    		
    	    type:'post',    
    	    cache:false,    
    	    dataType:'json',    
    	    success:function(data) { 
    	    	 $(".obj-select-list").html("");
    	    	var accountName = data.accountName;
    	        var accountobj = '<div class="obj-list"><a href="#" >' + accountName + '</a><div class="obj-list-del"></div></div>';
    	        $(".obj-select-list").append(accountobj);
    	     },    
    	     error : function() {    
    	             
    	          alert("异常！");    
    	     } 
    	});   
        
        
        

    }
    else {
        $(".add-obj a").removeClass('none');
        $(".add-obj a").attr('href', 'javascript:;');
        
      

    }
}


//创建添加对象弹出列表头部导航
function createBackBtn(state,type,id,lev, objName,tab) {
    var Obj = $("#boxheader");
    switch (state) {
        case 1:
            Obj.html("");
            var linkObj = $("<a>计划列表</a> ");
            linkObj.click(function () {
               // createBackBtn(id,objName,level,tab);
                add_levle(type,null,null)
            });
            Obj.append(linkObj);
            Obj.append(" >> " + tab + "：<span>" + objName + "</span>");
            break;
        case 2:
            var txt = Obj.find("span").text();
            var temphtml=Obj.html();
            var subLink = $("<a>" + txt + "</a>");
            Obj.find("span").html("");
            Obj.find("span").append(subLink);
            subLink.click(function () {
               // createBackBtn(id,objName,level,tab);
            	
            	 level(id,type,id,lev,objName);
            	 Obj.html(temphtml);
            });
            Obj.append(" >> " + tab + "：<span>" + objName + "</span>");
            break;
    }
}





function level(pid,type,parentid,level,name){
	var id=null;
	var lv=type;
	if(parentid!=null){
		id=parentid;
		//--------
	}else{
		lv=8;
	}
	
	if(level==2&& type!=7){
		lv=7;
	}
	

	//lv 8查询计划，7查询单元、5关键词、2创意(state,type,id,level, objName,tab)
	//------------显示标题--------
	
	if(lv==7){
		createBackBtn(1,type,pid,level+1, name, "计划");
	}else if(lv==5||lv==2) {
		createBackBtn(2,type,pid,level+1,name, "单元");
	}
	
	//获取 所有权限数据
	$.ajax({
		url:'/assistant/logLevel',// 跳转到 action
		
	    type:'post',    
	    cache:false,
	    data:{id:id==null?0:id,type:lv},
	    dataType:'json',    
	    success:function(data) {  
	    	 var content = '<ul id="objList">';
	    	 level--;
	    	    for (var i = 0; i < data.length; i++) {
	    	        var item = data[i];
	    	        content += '<li>';
	    	        if(level<=0){
	    	        	 content += '<a href="javascript:;" id="' + item.id + '">添加</a><span>' + item.name + '</span>';
	    	        }else{
	    	        	
	    	        	 content += '<a href="javascript:;" onclick="level('+id+','+type+','+item.id+','+ level +',\''+ item.name +'\')" id="' + item.id + '"><span>' + item.name + '</span></a>';
	    	        } 
	    	        content += '</li>';
	    	    }
	    	    content += "</ul>"
	    	    var queryR = $("#query-result");
	    	    //radioDiv.innerHTML = content;
	    	    queryR.html(content);
	    	   
	    	   
	    	    bindClick();
	    	    htmlArr = $("#objList li");
	    	    
	     },    
	     error : function() {    
	             
	          alert("异常！");    
	     } 
	});   
}


function bindClick(){
	 $(".query-result ul li a").click(function (event) {

         $(this).addClass('default');
         var addli = $(this).siblings().text();
         var addliId = $(this).attr('id');
         if ($(".obj-select-list div.obj-list a[name=" + addliId + "]").length <= 0) {
             //$(this).unbind('click');
             //<div class="obj-list" id="obj-list"><a href="#">baidu-perfect2151880</a></div>
             var addliBox = '<div class="obj-list"><a href="#" name="' + addliId + '" >' + addli + '</a><div class="obj-list-del"></div></div>';
             $(".obj-select-list").append(addliBox);
             //对选定的对象列表进行操作
             operatObj();
         }
     });
}


function add_levle(type,parentid,name){
	var queryR = document.getElementById("query-result");
    //radioDiv.innerHTML = content;
    queryR.innerHTML = '';
     $("#boxheader").html('计划列表');
	var lev=0;
	if(type==8){
		parentid=null;
		lev=1;
	}
	if(type==7){
		lev=2;
	}
	if(type==5||type==2){
		lev=3;
	}
	level(null,type,parentid,lev,name);
}

function addPlan(type) {
	add_levle(type,null,null);
}

function setPlanByHTML(liarr)
{
    var content = '';
    if (liarr.length > 0) {
        content = '<ul id="objList">';
        for (var i = 0; i < liarr.length; i++) {
            var item = liarr[i];
            content += '<li>';
            content += $(item).html();
            content += '</li>';
        }
        content += "</ul>"
    }
    var queryR = $("#query-result");
    queryR.html(content);
    bindClick();
}

//对选定的对象列表进行操作
function operatObj() {
    //选定列表按钮出现  
    $(".obj-list").hover(function () {
        $(this).children('.obj-list-del').show();
        $(".obj-list-del").click(function (event) {
            $(this).parent().remove();
            var thisN = $(this).siblings().attr('name');

            /*ar a= $(".query-result").find('#'+thisN).attr('id');
            alert(a);*/
            $(".query-result").find('#' + thisN).removeClass('default');
        });
    }, function () {
        $(this).children('.obj-list-del').hide();
    });
}

//选择操作记录  全选
function AllCheck() {
    //全选
    $(".operation-tip #all-check").click(function () {

        $(".operation-tip #all-check").attr('class', 'check-now');
        $("#operation-tip-list :checkbox[disabled!='disabled']").attr("checked", true);
        $(".operation-tip #user-defined").removeAttr('class');

    });

}
