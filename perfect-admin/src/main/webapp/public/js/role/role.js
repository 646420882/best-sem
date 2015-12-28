/**
 * Created by guochunyan on 2015/12/16.
 */
var role = {
    init: {
        data: function () {
            $("#roleAdmin").bootstrapTable({
                    locale: 'zh-CN',
                    pagination: true,
                    smartDisplay: false,
                    url: "/sysroles",
                    cache: false,
                    search: true,
                    uniqueId: "id",
                    pageSize: 20,
                    pageList: new Array(20, 50, 100),
                    searchAlign: 'left',
                    paginationPreText: "上一页",
                    paginationNextText: "下一页",
                    sidePagination: 'server',
                    queryParams: function (params) {
                        var selectVal = $("#userSelect :selected").val();
                        if (selectVal != -1) {
                            var _super = selectVal == "1" ? true : false;
                            params["super"] = _super;
                        }
                        return params;
                    }
                }
            );
        }
    },
    column: {
        noFat: function (val, row, index) {
            return index + 1;
        },
        passFat: function (val, row) {
            if (row.id != -1) {
                return '<a class="password_reset" href="javascript:void(0)" title="重置">重置</a>';
            }
            return val;
        },
        operateFat: function (val, row) {
            if (row.id != -1) {
                return '<a class="editor tab_operate" href="javascript:void(0)" title="修改">修改</a>' +
                    '<button class="preserve btn btn-primary" style="display:none" title="保存">保存</button>' +
                    '<a class="cancel tab_operate" style="display:none" href="javascript:void(0)" title="取消">取消</a>' +
                    '<a class="delete tab_operate ml10" href="javascript:void(0)" title="删除" >删除</a>';
            } else {
                return '<button class="addRole btn btn-primary" title="保存">保存</button>' +
                    '<a class="delete tab_operate ml10" href="javascript:void(0)" title="删除" >删除</a>';
            }
        },
        superAdminFat: function (val, row) {
            if (row.id != -1) {
                return val ? "超级管理员" : "管理员";
            }
            return val;
        }
    },
    date: {
        fat: function (val) {
            var _val = parseInt(val);
            if (_val)
                return new Date(_val).Format("yyyy-MM-dd hh:mm:ss") + "<span ctime='" + _val + "'></span>";
            else
                return "暂无";
        }
    },
    event: {
        selectUser: function (_this) {
            $("#roleAdmin").bootstrapTable("refresh");
        },
        showAddRoleModal: function () {
            var addVal = {
                id: "-1",
                name: '<input name="name"/>',
                loginName: '<input name="loginName">',
                contact: '<input name="contact">',
                superAdmin: "<select name='superAdmin'><option value='false'>管理员</option><option value='true'>超级管理员</option></select>",
                password: "<input type='password' name='password'/>",
                ctiem: '0',
                title: "<input name='title'/>"
            };
            var existNewAdd = true;
            var data = $("#roleAdmin").bootstrapTable("getData");
            if (data.length) {
                data.forEach(function (item, index) {
                    if (item.id == -1) {
                        existNewAdd = false;
                    }
                });
            }
            if (existNewAdd) {
                $("#roleAdmin").bootstrapTable("append", addVal);
                $("#roleAdmin").bootstrapTable('scrollTo', 'bottom');
            }
        }
    }
}
$(function () {
    role.init.data();
})
