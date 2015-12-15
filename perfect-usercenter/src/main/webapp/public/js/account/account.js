/**
 * Created by guochunyan on 2015/12/15.
 */
$(function () {
    $('#account_table').bootstrapTable({
        data: [{
            id: 1,
            name: 'baidu-perfect2151880',
            stargazers_count: '$1',
            url: "http://www.perfect-cn.cn",
            platform: "百度",
            time: "绑定时间"

        }, {
            id: 2,
            name: 'baidu-perfect2151880',
            stargazers_count: '$1',
            url: "http://www.perfect-cn.cn",
            platform: "百度",
            time: "绑定时间"
        }]
    });
    function operateFormatter(value, row, index) {
        return [
            '<a class="like" href="javascript:void(0)" title="Like">',
            '<i class="glyphicon glyphicon-heart"></i>',
            '</a>',
            '<a class="edit ml10" href="javascript:void(0)" title="Edit">',
            '<i class="glyphicon glyphicon-edit"></i>',
            '</a>',
            '<a class="remove ml10" href="javascript:void(0)" title="Remove">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>'
        ].join('');
    }

    window.operateEvents = {
        'click .like': function (e, value, row, index) {
            alert('You click like icon, row: ' + JSON.stringify(row));
            console.log(value, row, index);
        },
        'click .edit': function (e, value, row, index) {
            alert('You click edit icon, row: ' + JSON.stringify(row));
            console.log(value, row, index);
        },
        'click .remove': function (e, value, row, index) {
            alert('You click remove icon, row: ' + JSON.stringify(row));
            console.log(value, row, index);
        }
    };
    $('#AccountTable').bootstrapTable({
        data: [{
            id: 1,
            name: 'baidu-perfect2151880',
            stargazers_count: '$1',
            url: "http://www.perfect-cn.cn",
            platform: "百度",
            time: "绑定时间"

        }, {
            id: 2,
            name: 'baidu-perfect2151880',
            stargazers_count: '$1',
            url: "http://www.perfect-cn.cn",
            platform: "百度",
            time: "绑定时间"
        }]

    });
})