/**
 * 忘记密码单击事件
 */
function next(){
    $(".login_box:eq(0)").addClass("hides");
    $(".login_box:eq(1)").removeClass("hides")
}
function next_complete(){
    $(".login_box:eq(1)").addClass("hides");
    $(".login_box:eq(2)").removeClass("hides")
}