/**
 * Created by SubDong on 2014/9/15.
 */
$(function () {
    $("#downSync").click(function () {
        var verify =-1;
        $.ajax({
            url: "/monitoring/synchronous",
            type: "GET",
            dataType: "json",
            success: function (data) {
                verify = data;
            }
        });
        $.ajax({
            url: "/monitoring/getFolder",
            type: "GET",
            dataType: "json",
            success: function (data) {
                $.each(data.rows,function(i,items){

                });

                if (verify == 1) {
                    alert("同步已完成！请继续操作");
                }else{
                    alert("同步过程中出现了意想不到的结果，请重新同步")
                }
            }
        });

        MonitorTbody
    });
});
