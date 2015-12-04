var settingdown = {
    view: {
        showLine: false,
        selectedMulti: false
    },
    check: {
        enable: true
//        chkboxType : { "Y" : "", "N" : "" }
    }
    ,
    data: {
        simpleData: {
            enable: true
        }
    }
};
var zNodes = "";


var loadAccountDownloadsTree = function () {
    //获取账户树数据
    $.ajax({
        url: "/multipleaccount/get_MultipAccountDownloadtree",
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            zNodes = data.trees;
            $.fn.zTree.init($("#zTrees"), settingdown, zNodes);
        }
    });
};


//获取选中的树节点，去下载对应的账户和计划
var downLoadAccountsTree = function (){
	 var downloadtreeObj = $.fn.zTree.getZTreeObj("zTrees");
     var downloadnodes = downloadtreeObj.getCheckedNodes(true);
     if(downloadnodes == null || downloadnodes == undefined || downloadnodes == ''){
    	 alert("请选择您希望下载的单元"); 
     }else{
	     var _list = [];
	     for(var i=0;i<downloadnodes.length;i++){
	    	 _list.push(downloadnodes[i].id);
	     }
	     $.ajax({
	    	 url:"/multipleaccount/updateMultipAccountData",
	    	 type:"POST",
	    	 async:false,
	    	 dataType:"json",
	    	 data: {
	             "downloadIds": JSON.stringify(_list)
	         },
	    	 success:function (data, textStatus, jqXHR){
	    		 alert("更新成功");
	             ajaxbg.hide();
	             $(".head_account").css("display", "none");
	             $("#open_account").css("display", "none");
	    	 }
	     });
    	 
     }
     

};

$("#downloadAccountsTree").on('click', function () {
	downLoadAccountsTree();
});

$(function(){
	loadAccountDownloadsTree();
});