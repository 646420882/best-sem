/**
 * Created by XiaoWei on 2014/8/21.
 */
var plans={cid:null};
$(function(){
    loadAdgroupData(plans);
    rDrag.init(document.getElementById("bAdd"));
});
/**
 * 加载单元数据
 * @param plans 根据点击树结构的计划id，如果有则根据计划加载，如果没有，查询所有单元
 */
function loadAdgroupData(plans){
    var _adGroudTable=$("#adGroupTable tbody");
    $.get("../assistantAdgroup/getAdgroupList",plans,function(rs){
        if(rs!="[]"){
            var json = eval("(" + rs + ")");
            if (json.length > 0) {
                _adGroudTable.empty();
                var _trClass = "";
                for (var i = 0; i < json.length; i++) {
                    _trClass = i % 2 == 0 ? "list2_box1" : "list2_box2";
                    var _tbody = "<tr class=" + _trClass + ">" +
                        "<td >&nbsp;<input type='hidden' value='"+json[i].adgroupId+"'/></td>" +
                        "<td >" +  json[i].adgroupName+ "</td>" +
                        "<td >" +  json[i].status+ "</td>" +
                        "<td >" +  until.convert(json[i].pause,"启用:暂停")+ "</td>" +
                        "<td >" + parseFloat(json[i].maxPrice).toFixed(2)+ "</td>" +
                        "<td >" +  json[i].negativeWords+ "</td>" +
                        "<td >" +  json[i].negativeWords+ "</td>" +
                        "<td >" +  json[i].campaignName+ "</td>" +
                        "</tr>";
                    _adGroudTable.append(_tbody);
                }
            }
        }else{
            _adGroudTable.empty();
            _adGroudTable.append("<tr><td>暂无数据</td></tr>");
        }
    });
}
function getAdgroupPlan(rs){
    plans.cid=rs;
    loadAdgroupData(plans);
}
/**
 * 点击添加按钮弹出框
 */
function adAlertShow(){
    var _adAdd=$("#adAdd");
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    _adAdd.css({
        left: ($("body").width() - _adAdd.width()) / 2 - 20 + "px",
        top: ($(window).height() - _adAdd.height()) / 2 + $(window).scrollTop() + "px",
        display: "block"
    });

}
/**
 * 选择计划弹出框关闭方法
 */
function adgroupAddAlertClose(){
    $(".TB_overlayBG").css("display", "none");
    $("#adAdd ").css("display", "none");
}
/**
 * 添加单元方法
 */
function addAdgroup(){
    var jcBox = $("#adUi");
    if(plans.cid==null){
        jcBox.empty();
        adgetPlans();
        jcBox.append("<li>推广计划<select id='aPlan' ><option value='-1'>请选择计划</option></select></li>");
        adAlertShow();
    }
}
/**
 * 获取
 */
function adgetPlans() {
    $.get("/assistantCreative/getPlans", function (rs) {
        var json = eval("(" + rs + ")");
        if (json.length > 0) {
            for (var i = 0; i < json.length; i++) {
                var str = "<option value='" + json[i].campaignId + "'>" + json[i].campaignName + "</option>";
                $("#aPlan").append(str);
            }
        }
    });
}
