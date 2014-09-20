/**
 * Created by XiaoWei on 2014/9/19.
 */
$(function () {


});
var imzTree = true;//标识分组树是否是第一次加载
var imzTreeSetting={
    view: {
        showLine: false,
        selectedMulti: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeClick: iMbeforeClick,
        beforeAsync: null,
        onAsyncError: null,
        onAsyncSuccess: null
    }
};
function iMbeforeClick(treeId, treeNode) {
    if (treeNode.level == 0) {
        alert(treeNode.id);
    } else if (treeNode.level == 1) {
        alert(treeNode.id);
    }
}
//加载分组树
function initImzTree(tre) {
    tre = tre != undefined ? tre : imzTree;
    if (tre) {
        $.get("/importBid/getCustomTree",function(result){
            var json=eval("("+result+")");
            if(json.trees!="[]"){
                $.fn.zTree.init($("#zTreeImport"), imzTreeSetting, json.trees);
            }
        });

    }
    imzTree = false;
}
//点击分组动态加载分组信息
function initCustomGroupSelect(){
    $.get("/importBid/getList",function(result){
        var selectObj=$("#cgroup");
        selectObj.empty();
        if(result!="[]"){
            selectObj.append("<option value='-1'>--新建分组--</option>");
            selectObj.append(convertOption(result));
        }else{
            selectObj.append("<option value='-1'>--新建分组--</option>");
        }
    });
}
//选择分组点击事件
function checkGroupOk(){
    var selectVal=$("#cgroup :selected").val();
    if(selectVal!=-1){
        alert("可以保存了..");
    }else{
        var cgroupName=$("input[name='cgroupName']").val();
        if(cgroupName!=""){
            var con=confirm("是否添加名为："+cgroupName+"的分组?");
            if(con){
                $.post("/importBid/insert",{gname:cgroupName},function(result){
                    var json=eval("("+result+")");
                    if(json.success==1){
                        $("#cgroup").append("<option value='"+json.data+"'>"+cgroupName+"</option>");
                    }
                });
            }
        }

    }
}
//当选择新增时弹出新增文本框
function cgroupInsert(){
    var _this=$("#cgroup :selected").val();
    _this=parseInt(_this);
    switch(_this) {
        case -1:
            $("#showTxt").css("display","block");
            $("input[name='cgroupName']").css("display","none");
            break;
        default :
            $("#showTxt").css("display","none");
            $("input[name='cgroupName']").css("display","none");
            break;

    }

}
//显示或者消失分组名
function showGroupNameTxt(){
    var _this=$("input[name='cgroupName']");
    $("input[name='cgroupName']").toggle(_this.css("display")=="none").val("");
}
//根据查询到的数据加载分组列表
function convertOption(data){
    var json=eval("("+data+")");
    var result="";
    for(var i=0;i<json.length;i++){
        result=result+"<option value='"+json[i].id+"'>"+json[i].groupName+"</option>"
    }
    return result;
}