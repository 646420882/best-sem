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
    $.get("/uploadMerge/upload",function(res){
        alert(res);
    });
}