var d =  dialog({title: "批量添加/更新",
    padding: "5px",
    content: "<iframe src='/newkeyword' width='900' height='550' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
    oniframeload: function () {
    },
    onclose: function () {
//              if (this.returnValue) {
//                  $('#value').html(this.returnValue);
//              }
//                window.location.reload(true);
    },
    onremove: function () {
    }
});



$(function () {
    $("#addKeyword").livequery('click', function () {
        top.dialog({title: "关键词工具",
            padding: "5px",
            content: "<iframe src='/toAddPage' width='900' height='500' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
            oniframeload: function () {
            },
            onclose: function () {
//              if (this.returnValue) {
//                  $('#value').html(this.returnValue);
//              }
               // window.location.reload(true);
                whenClickTreeLoadData(getCurrentTabName(), getNowChooseCidAndAid());
            },
            onremove: function () {
            }
        }).showModal();
        return false;
    });
    $("#search_keyword").livequery('click', function () {
        top.dialog({title: "关键词工具",
            padding: "5px",
            content: "<iframe src='/toAddPage' width='900' height='500' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
            oniframeload: function () {
            },
            onclose: function () {
//              if (this.returnValue) {
//                  $('#value').html(this.returnValue);
//              }
               // window.location.reload(true);
                whenClickTreeLoadData(getCurrentTabName(), getNowChooseCidAndAid());
            },
            onremove: function () {
            }
        }).showModal();
        return false;
    });
    $("#addplan").livequery('click', function () {
        top.dialog({title: "快速新建计划",
            padding: "5px",
            content: "<iframe src='/addplan' width='900' height='550' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
            oniframeload: function () {
            },
            onclose: function () {
//              if (this.returnValue) {
//                  $('#value').html(this.returnValue);
//              }
                window.location.reload(true);
            },
            onremove: function () {
            }
        }).showModal();
        return false;
    });
    $("#newkeyword").livequery('click', function () {
        d.showModal();
        return false;
    });
    $("#deletekeyword").livequery('click', function () {
        top.dialog({title: "批量删除",
            padding: "5px",
            content: "<iframe src='/deletekeyword' width='900' height='550' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
            oniframeload: function () {
            },
            onclose: function () {
//              if (this.returnValue) {
//                  $('#value').html(this.returnValue);
//              }
               // window.location.reload(true);
            },
            onremove: function () {
            }
        }).showModal();
        return false;
    });
    $("#searchword").livequery('click', function () {
        top.dialog({title: "搜索词报告",
            padding: "5px",
            content: "<iframe src='/searchword' width='900' height='590' marginwidth='0' marginheight='0' scrolling='no' frameborder='0'></iframe>",
            oniframeload: function () {
            },
            onclose: function () {
//              if (this.returnValue) {
//                  $('#value').html(this.returnValue);
//              }
                window.location.reload(true);
            },
            onremove: function () {
            }
        }).showModal();
        return false;
    });



});