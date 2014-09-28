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
            async : false,
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
    });
    $("#jkwjj").click(function(){
        $.ajax({
            url: "/monitoring/getFolder",
            type: "GET",
            dataType: "json",
            success: function (data) {
                var monitor_html = "";
                $.each(data.rows,function(i,items){
                    if(i % 2 != 0){
                        monitor_html = "<tr class='list2_box1' cname='trName' cid='"+items.folderId+"' folderName='"+items.folderName+"' count='"+items.countNumber+"'><td >&nbsp;" + items.folderName + "</td><td>&nbsp;" + items.countNumber + "</td><td>&nbsp;</td>"
                    }else{
                        monitor_html = "<tr class='list2_box2' cname='trName' cid='"+items.folderId+"' folderName='"+items.folderName+"' count='"+items.countNumber+"'><td >&nbsp;" + items.folderName + "</td><td>&nbsp;" + items.countNumber + "</td><td>&nbsp;</td>"
                    }
                });
                $("#MonitorTbody").empty();
                $("#MonitorTbody").append(monitor_html);
            }
        });
    });
    $("body").on("click","tr[cname=trName]",function(){
        var cid = $(this).attr("cid");
        var folderName = $(this).attr("folderName");
        var count = $(this).attr("count");
        $("#count").val(count);
        $("#folder").val(folderName);
    });
});
