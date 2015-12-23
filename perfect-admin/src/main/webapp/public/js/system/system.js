/**
 * Created by guochunyan on 2015/12/16.
 */
$(function () {
    //数据
    var jsonSystem = [
        {
            label: '百思慧眼', name: 'hy', children: [
            {label: '网站概览', name: 'gl'},
            {
                label: '百度推广', name: 'tg', children: [
                {label: '推广概括', name: 'gk'},
                {label: '推广方式', name: 'fs'},
                {label: '收索推广', name: 'ss'},
                {label: '网维推广', name: 'wm'}
            ]
            },
            {label: '趋向分析', name: 'qx'},
            {label: '来源分析', name: 'ly'}
        ]
        },
        {label: '百思搜客', name: 'sk',children: [
            {label: '网站概览', name: 'gl'}]},
        {label: '百思速投', name: 'st',children: [
            {label: '趋向分析', name: 'gl'}]}
    ];
    //数据插入
    //function system(className,k) {
    //    var j;
    //    var content="";
    //    var add=" <li class='system_input hides'><input class='form-control' type='text'/></li> <li class='add'>+新增</li> <li class='add hides sure'>+新增</li>"
    //    if (className==".webname") {
    //        j = jsonSystem
    //
    //    }else if(className==".webmenu"){
    //        j = jsonSystem[k].children;
    //    }
    //    for (var i = 0; i < j.length; i++) {
    //              content+="<li>" + j[i].label + "<span class='glyphicon glyphicon-triangle-bottom'></span></li>"
    //    }
    //    content+=add;
    //    $(className).append(content);
    //}
    //system(".webname");
    //前端效果
    $(".web_name").hover(function () {
        //var k= $(this).index();
        //$(".webmenu").empty();
        //system(".webmenu",k);

        //$(this).find("span").removeClass("glyphicon-triangle-bottom ");
        //$(this).find("span").addClass("glyphicon-triangle-right");
    });
    //$(".webmenu").hover();
    $(".webname").mouseout(function () {
        //$("body").bind("click", function () {
        //    $(".webmenu").addClass("hides");
        //});
        //$(this).find("span").addClass("glyphicon-triangle-bottom ");
        //$(this).find("span").removeClass("glyphicon-triangle-right");


    });
    $(".system_input").hover(function () {
        $(".webmenu").addClass("hides");
    });
    $(".webname .add").hover(function () {
        $(".webmenu").addClass("hides");
    });
    $(".webname .add").bind('click', function () {
        $(".system_input").removeClass('hides');
        $(this).hide();
        $(".webname .sure").show();
    });
    $(".webmenu").hover(function(){
        //$(".webname").removeClass("hides");
        //$(this).find("span").removeClass("glyphicon-triangle-bottom ");
        //$(this).find("span").addClass("glyphicon-triangle-right");

    })
    $(".webname .sure").bind('click', function () {
        var name = $(".webname input").val();
        $(".webname").append("<li>" + name + "<span class='glyphicon glyphicon-triangle-bottom'></span></li>");
        console.log(name)
    })
    $(".systemContent .web_name").hover(function(){
        console.log(111)
        $(".webmenu").css({'top': $(".webname").offset().top - 151,'left': $(".webname").offset().left +7});
        $(".webmenu").removeClass("hides");
        $(this).find("span").eq(0).removeClass("glyphicon-triangle-bottom ");
        $(this).find("span").eq(0).addClass("glyphicon-triangle-right");
    })
    $(".systemContent .web_name").mouseout(function(){
        console.log(222)
        $(this).find("span").eq(0).addClass("glyphicon-triangle-bottom ");
        $(this).find("span").eq(0).removeClass("glyphicon-triangle-right");
    })
})