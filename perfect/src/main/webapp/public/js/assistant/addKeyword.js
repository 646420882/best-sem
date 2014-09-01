$(function () {
    $("#addKeyword").livequery('click', function () {
        top.dialog({title: "添加关键词",
            padding: "5px",
            //fixed: true,
            //top: 'goldenRatio',
            content: "<iframe src='/keyword_group' width='900' height='500' marginwidth='0' marginheight='0' frameborder='0'></iframe>",
            oniframeload: function () {
            },
            onclose: function () {
//              if (this.returnValue) {
//                  $('#value').html(this.returnValue);
//              }
                window.location.reload(true);
            },
            onremove: function () {
                console.log('onremove');
            }
        }).showModal();
        return false;
    });
});