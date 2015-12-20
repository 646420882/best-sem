/**
 * Created by guochunyan on 2015/12/16.
 */
$(function () {
    var data = [{
        id: 1,
        name: 'baidu-perfect2151880',
        url: "http://www.perfect-cn.cn",
        platform: "百度",
        time: "2015年12月25日",
        action: "1"

    }, {
        id: 2,
        name: 'tudou.com',
        url: "http://www.perfect-cn.cn",
        platform: "百度",
        time: "2015年12月25日",
        action: "1"
    }]
    $('#logAdmin').bootstrapTable({
        data: data
    });
    $(window).resize(function () {
        $('#logAdmin').bootstrapTable('resetView');

    });

    //分页
    //$(".page-pre").empty();
    $(".page-pre a").text("上一页");
    $(".page-next a").text("下一页");
    $("*").click(function(){
        $(".page-pre a").text("上一页");
        $(".page-next a").text("下一页");
        if( $(".pull-right.pagination").css("display")=='none' ) {
            $(".skip").css({display:"none"});
        }else{
            $(".skip").css({display:"block"});
        }
        console.log($('.pagination>.page-number>a'))
        for(var i=0;i<=$('.pagination>li>a').length;i++){
            if($('.pagination>li>a:eq(i)').text()==$("#selectPages").val()){
                $(this).addClass("active")
            }
        }

    })
    $(".selectPage").click(function(){
        var pages=$("#selectPages").val();
        $('#logAdmin').bootstrapTable('selectPage',pages);
    })
    $(".nextPage").click(function(){
        $('#logAdmin').bootstrapTable('nextPage');
    })






})