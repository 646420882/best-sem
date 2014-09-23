/**
 * Created by XiaoWei on 2014/9/19.
 */
$(function () {


});
var imzTree = true;//标识分组树是否是第一次加载
var imId="";//获取点击关键字的编号
var ima="";//关键词名
var imap="";//关键词名，用于向后台发送
var imbiddingStatus="";//竞价状态
var imrule="";//竞价规则


//加载分组树
function initImzTree(tre) {
    tre = tre != undefined ? tre : imzTree;
    if (tre) {
        $.get("/importBid/getCustomTree", function (result) {
            var json = eval("(" + result + ")");
            if (json.trees != "[]") {
                $.fn.zTree.init($("#zTreeImport"), imzTreeSetting, json.trees);
            }
        });

    }
    imzTree = false;
}
//点击分组动态加载分组信息

function initCustomGroupSelect() {
    var _imKwd=$("#imKwd");
    var _checked = $("#table1 tbody input[type='checkbox']:checked");
    if (_checked.length <= 0) {
        return false;
    } else {
         ima="";
        imap="";
        imId="";
         imbiddingStatus="";//竞价状态
         imrule="";
        _checked.each(function (i, o) {
            var kwdId = $(o).parents("tr").find("td:eq(1)").html();
            var kwdName = $(o).parents("tr").find("td:eq(2)").html();
            var biddingStatus=$(o).parents("tr").find("td:eq(21)").html();
            var rule=$(o).parents("tr").find("td:eq(22)").html();
            ima=ima+kwdName+"\n";
            imap=imap+kwdName+",";
            imId=imId+kwdId+",";
            imbiddingStatus=imbiddingStatus+biddingStatus+",";
            imrule=imrule+rule+",";
        });
        imId=imId.substr(0,imId.length-1);
        ima=ima.substr(0,ima.length-1);
        imap=imap.substr(0,imap.length-1);
        imbiddingStatus=imbiddingStatus.substr(0,imbiddingStatus.length-1);
        imrule=imrule.substr(0,imrule.length-1);
        _imKwd.val(ima.trim());
        $.get("/importBid/getList", function (result) {
            var selectObj = $("#cgroup");
            selectObj.empty();
            if (result != "[]") {
                selectObj.append("<option value='-1'>--新建分组--</option>");
                selectObj.append(convertOption(result));
            } else {
                selectObj.append("<option value='-1'>--新建分组--</option>");
            }
        });
        return true;
    }
}
//选择分组点击事件
function checkGroupOk() {
    var selectVal = $("#cgroup :selected").val();
    if (selectVal != -1) {
        if($("#imKwd").val()!=""){
            $.post("/importBid/addKeyword",{
                cgroupId:selectVal,
                imId:imId,
                imap:imap,
                imbiddingStatus:imbiddingStatus,
                imrule:imrule
            },function(result){
                if(result==1){
                    alert("分组成功");
                    imclose();
                }
            });
        }
    } else {
        var cgroupName = $("input[name='cgroupName']").val();
        if (cgroupName != "") {
            var con = confirm("是否添加名为：" + cgroupName + "的分组?");
            if (con) {
                $.post("/importBid/insert", {gname: cgroupName}, function (result) {
                    var json = eval("(" + result + ")");
                    if (json.success == 1) {
                        $("#cgroup").append("<option value='" + json.data + "'>" + cgroupName + "</option>");
                        alert("添加成功");
                    }else if(json.success == 0){
                        alert("已经存在名为："+cgroupName+"的分组名了！");
                    }
                });
            }
        }

    }
}
//当选择新增时弹出新增文本框
function cgroupInsert() {
    var _this = $("#cgroup :selected").val();
    _this = parseInt(_this);
    switch (_this) {
        case -1:
            $("#showTxt").css("display", "block");
            $("input[name='cgroupName']").css("display", "none");
            break;
        default :
            $("#showTxt").css("display", "none");
            $("input[name='cgroupName']").css("display", "none");
            break;

    }

}
//显示或者消失分组名
function showGroupNameTxt() {
    var _this = $("input[name='cgroupName']");
    $("input[name='cgroupName']").toggle(_this.css("display") == "none").val("");
}
//根据查询到的数据加载分组列表
function convertOption(data) {
    var json = eval("(" + data + ")");
    var result = "";
    for (var i = 0; i < json.length; i++) {
        result = result + "<option value='" + json[i].id + "'>" + json[i].groupName + "</option>"
    }
    return result;
}
//关闭弹窗
function imclose(){
    $(".TB_overlayBG").css("display", "none");
    $(".box5").css("display", "none");
}