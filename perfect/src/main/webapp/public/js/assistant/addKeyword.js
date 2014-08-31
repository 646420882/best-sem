var keywordDialog = dialog({title: "添加关键词",
    width: 900,
    height: 500,
    padding: '5px',
    url: '/keyword_group',
    content: '<iframe src="http://www2.baidu.com" width="900px" height="500px" frameborder="0"></iframe>',
    onshow: function () {
        console.log('onshow');
    },
    oniframeload: function () {
        console.log('oniframeload');
    },
    onclose: function () {
//        if (this.returnValue) {
//            $('#value').html(this.returnValue);
//        }
        console.log('onclose');
        window.location.reload(true);
    },
    onremove: function () {
        console.log('onremove');
    }
});

window.console = window.console || {log: function () {
}};

$(function () {
    $("#addKeyword").livequery('click', function () {
        keywordDialog.show();
        return false;
    });
});