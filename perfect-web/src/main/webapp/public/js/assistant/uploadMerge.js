/**
 * Created by XiaoWei on 2015/1/19.
 */
function dialogUpload(val){
    if(val=="all"){

    }else{
        loadOperateCampainList();
    }
}
function uploadDialog() {
    openUploadDialog();
}
function openUploadDialog(){
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    $("#uploadMerge").css({
        left: ($("body").width() - $("#uploadMerge").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#uploadMerge").height()) / 2 + $(window).scrollTop() + "px",
        display: "block"
    });
}
function closeUploadDialog() {
    $(".TB_overlayBG").css("display", "none");
    $("#uploadMerge").css("display", "none");
}
function loadOperateCampainList(){
    $.get("/uploadMerge/getCampList",function(res){
        $("#existsCamp ul").empty();
        var results = res.data;
        if (results.length > 0) {
            $.each(results, function (i, item) {
                var _li = "";
                if (i % 2 == 0) {
                    _li = "<li><input id='" + item.campaignId + "' type='checkbox' name='camp_o'>" + item.campaignName + "</li>";
                } else {
                    _li = "<li><input id='" + item.campaignId + "' type='checkbox' class='current' name='camp_o'>" + item.campaignName + "</li>";
                }
                $("#existsCamp ul").append(_li);
            });
        }
    });
}
function uploadDialogOk(){
    alert("该功能还在开发中...请暂时使用各模块的上传功能!!");
}