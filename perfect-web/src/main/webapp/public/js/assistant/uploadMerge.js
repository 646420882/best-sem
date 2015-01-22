/**
 * Created by XiaoWei on 2015/1/19.
 */
function uploadDialog() {
    alert("该功能还在开发中..目前使用上传功能请直接在对应模块右键上传到凤巢！");
    return;
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