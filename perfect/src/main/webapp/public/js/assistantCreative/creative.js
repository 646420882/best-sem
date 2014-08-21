/**
 * Created by XiaoWei on 2014/8/21.
 */
$(function(){
    loadData();
});
function loadData(){
    $.get("/assistantCreative/getList",function(result){
        alert(result);
    });
}
