/**
 * Created by guochunyan on 2015/12/15.
 */
$(function () {
    var data = [{
        id: 1,
        name: 'baidu-perfect2151880',
        password:'123455',
        url: "http://www.perfect-cn.cn",
        platform: "百度",
        time: "2015年12月25日",
        action: "1"

    }, {
        id: 2,
        name: 'baidu-perfect2151880',
        password:'123455',
        url: "http://www.perfect-cn.cn",
        platform: "百度",
        time: "2015年12月25日",
        action: "1"
    }]
    var datas = [{
        id: 1,
        name: 'baidu-perfect2151880',
        password:'123455',
        remark: "http://www.perfect-cn.cn",
        webName: "百度",
        webUrl: "http://localhost:8088/account",
        webCode: "http://localhost:8088/account",
        action: "1"

    }, {
        id: 2,
        name: 'baidu-perfect2151880',
        password:'123455',
        remark: "http://www.perfect-cn.cn",
        webUrl: "http://localhost:8088/account",
        webName: "百度",
        webCode: "",
        action: "1"
    }]
    $('#account_table').bootstrapTable({
        data: data
    });
    $('#AccountTable').bootstrapTable({
        data: datas

    });
    $(window).resize(function () {
        $('#account_table').bootstrapTable('resetView');

    });
    /*   $('#AccountTable').on('click-row.bs.table', function (e, row, $element) {

     });*/
    $(window).resize(function () {
        $('#AccountTable').bootstrapTable('resetView');

    });
})

function operateFormatter(value, row, index) {
    return [
        '<a class="binding" href="javascript:void(0)" title="绑定">',
        '绑定',
        '</a>',
        '<a class="editor suoke" href="javascript:void(0)" title="修改">',
        '修改',
        '</a>',
        '<button class="preserve btn btn-primary" style="display:none" href="javascript:void(0)" title="保存">',
        '保存',
        '</button>',
        '<button class="preserve newadd btn btn-primary" style="display:none" href="javascript:void(0)" title="新增">',
        '新增',
        '</button>',
        '<a class="delete ml10" href="javascript:void(0)" title="删除" >',
        '删除',
        '</a>'

    ].join('');
}
function disableFormatter(value, row, index) {
    return [
        '<a class="disable" href="javascript:void(0)" title="禁用">',
        '禁用',
        '</a>',
        '<a class="editor huiyan" href="javascript:void(0)" title="修改">',
        '修改',
        '</a>',
        '<button class="preserve btn btn-primary" style="display:none" href="javascript:void(0)" title="保存">',
        '保存',
        '</button>',
        '<button class="preserve newadd btn btn-primary" style="display:none" href="javascript:void(0)" title="新增">',
        '新增',
        '</button>',
        '<a class="delete ml10" href="javascript:void(0)" title="删除" >',
        '删除',
        '</a>'

    ].join('');
}
window.operateEvents = {
    'click .binding': function (e, value, row, index) {
        var bindingtext = $(this);
        if ($(this).html() == "绑定") {
            $('#modelbox').modal()
            $("#modelboxTitle").html("系统提示");
            $(".modal-body").html("是否绑定？");
            $("#modelboxBottom").click(function () {
                $('#modelbox').modal('hide');
                bindingtext.html("取消绑定");
            })

        } else {
            $('#modelbox').modal()
            $("#modelboxTitle").html("系统提示");
            $(".modal-body").html("是否取消绑定？");
            $("#modelboxBottom").click(function () {
                $('#modelbox').modal('hide');
                bindingtext.html("绑定");
            })

        }
    },
    'click .disable': function (e, value, row, index) {
        var bindingtext = $(this);
        if ($(this).html() == "禁用") {
            $('#modelbox').modal()
            $("#modelboxTitle").html("是否禁用？");
            $("#modelboxBottom").click(function () {
                $('#modelbox').modal('hide');
                bindingtext.html("启动");
            })

        } else {
            $("#modelboxTitle").html("是否启动？");
            $('#modelbox').modal()
            $("#modelboxBottom").click(function () {
                $('#modelbox').modal('hide');
                bindingtext.html("禁用");
            })

        }
    },
    'click .suoke': function (e, value, row, index) {
        $('#modelbox').modal();
        $('.modal-dialog').css({width:'400px'})
        $("#modelboxTitle").html("修改关联账户");
        $(".modal-body").html("<ul class='account_change'>" +
        "<li>关联账户名：</li>" +
        "<li>账户密码：</li>"+
        "<li>备注名：</li>" +
        "<li>URL地址：</li>" +
        "<li>账户所属平台：</li></ul>");
        var editorBottom = $(this);
        var that = $(this).parent().prevAll("td");
        var that_value = that.each(function (index) {
            var that_html = $(this).html();
            console.log(that_html)
            if(index==4){
                var acount_html="<input type='password' class='form-control' value='" + that_html + "'> "
            }else{
                var acount_html="<input type='text' class='form-control' value='" + that_html + "'> "
            }
            if(index>0){
                $(".account_change").find('li').eq(5-index).append(acount_html);
            }
                //$(this).html("<input type='text' class='form-control' value='" + that_html + "'> ").append();
                //editorBottom.hide();
                //editorBottom.prev().hide();
                //editorBottom.next(".preserve").attr("style", "display:block");
        });
    },
    'click .huiyan': function (e, value, row, index) {
        $('#modelbox').modal();
        $('.modal-dialog').css({width:'400px'})
        $("#modelboxTitle").html("修改关联账户");
        $(".modal-body").html("<ul class='account_change'>" +
        "<li>关联账户名：</li>" +
        "<li>账户密码：</li>" +
        "<li>备注名：</li>" +
        "<li>网站名称：</li>" +
        "<li>网站URL：" +
        "<li>统计代码：</li></ul>");
        var editorBottom = $(this);
        var that = $(this).parent().prevAll("td");
        var that_value = that.each(function (index) {
            var that_html = $(this).html();
            if(index==4){
                var acount_html="<input type='password' class='form-control' value='" + that_html + "'> "
            }else{
                var acount_html="<input type='text' class='form-control' value='" + that_html + "'> "
            }
            $(".account_change").find('li').eq(5-index).append(acount_html);

            //$(this).html("<input type='text' class='form-control' value='" + that_html + "'> ").append();
            //editorBottom.hide();
            //editorBottom.prev().hide();
            //editorBottom.next(".preserve").attr("style", "display:block");
        });
    },
    'click .preserve': function (e, value, row, index) {
        var preserveHtML = $(this);
        var preserveThat = $(this).parent().prevAll("td");
        preserveThat.each(function () {
            var that_html = $(this).find("input").val();
            $(this).html(that_html);
        });
        preserveHtML.attr("style", "display:none");
        preserveHtML.prevAll().show();
    },
    'click .delete': function (e, value, row, index) {
        var tabledelete = $(this).parent().parent();
        $('#modelbox').modal();
        $("#modelboxTitle").html("系统提示");
        $(".modal-body").html("是否删除？");
        $("#modelboxBottom").click(function () {
            $('#modelbox').modal('hide');
            tabledelete.remove();

        })
    }
};
var $table = $('#account_table'),
    $button = $('#Tablebutton');
var acountContent="<ul class='account_add'>" +
    "<li>关联账户名：<input class='form-control ' type='text'></li>" +
    "<li>账户密码：<input class='form-control ' type='text'></li>" +
    "<li>备注名：<input class='form-control ' type='text'></li>" +
    "<li>URL地址：<input class='form-control ' type='text'></li>" +
    "<li>账户所属平台：<input class='form-control ' type='text'></li></ul>"
$(function () {
    $button.click(function () {
        $('#modelbox').modal();
        $('.modal-dialog').css({width:'400px'})
        $("#modelboxTitle").html("新增关联账户");
        $(".modal-body").html(acountContent);
        //$table.bootstrapTable('append', firstAdd());
        //$table.bootstrapTable('scrollTo', 'bottom');
    });
});

$(function () {
    $("#SecendTablebutton").click(function () {
        $('#modelbox').modal();
        $('.modal-dialog').css({width:'400px'})
        $("#modelboxTitle").html("新增关联账户");
        $(".modal-body").html(acountContent);
        //$('#modelbox').modal();
        //$("#modelboxTitle").html("是否删除？");
        //$("#modelboxBottom").click(function () {
        //    $('#modelbox').modal('hide');
        //    tabledelete.remove();
        //
        //})
        //$('#AccountTable').bootstrapTable('append', secondAdd());
        //$('#AccountTable').bootstrapTable('scrollTo', 'bottom');
    });
});
function firstAdd() {
    var startId = "<input type='text' class='form-control'>",
        rows = [];
    rows.push({
        id: startId,
        name: startId,
        remark: startId,
        url: startId,
        platform: startId,
        time: startId
    });
    return rows;
}
function secondAdd() {
    var startId = "<input type='text' class='form-control'>",
        rows = [];
    rows.push({
        id: startId,
        name: startId,
        remark: startId,
        wedName: startId,
        wedUrl: startId,
        wedCode: startId
    });
    return rows;
}
