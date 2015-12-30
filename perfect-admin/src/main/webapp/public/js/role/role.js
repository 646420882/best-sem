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
            //if (row.id != -1) {
            return '<a class="password_reset" href="javascript:void(0)" title="重置">重置</a>';
            //}
            //return val;
        },
        operateFat: function (val, row) {
            //if (row.id != -1) {
            return '<a class="editor tab_operate" href="javascript:void(0)" title="修改">修改</a>' +
                    //'<button class="preserve btn btn-primary" style="display:none" title="保存">保存</button>' +
                    //'<a class="cancel tab_operate" style="display:none" href="javascript:void(0)" title="取消">取消</a>' +
                '<a class="delete tab_operate ml10" href="javascript:void(0)" title="删除" >删除</a>';
            /* } else {
             return
             '<a class="delete tab_operate ml10" href="javascript:void(0)" title="删除" >删除</a>';
             }*/
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
        }
    }
}
$(function () {
    role.init.data();
})
$("#roleEditBottom").click(function () {
    var _newRow = {};
    _newRow.name = $("#editRoleName").val();
    _newRow.title = $("#editRolePosition").val();
    _newRow.loginName = $("#editRoleAccount").val();
    _newRow.superAdmin = $("#editRoleManage :selected").val() == "true" ? true : false;
    _newRow.contact = $("#editRoleContact").val();
    _newRow.id = $("#editId").val();
    if (!_newRow.name) {
        alert("请输入用户名!");
        return;
    }
    if (!_newRow.title) {
        alert("请输入职务!");
        return;
    }
    if (!_newRow.loginName) {
        alert("请输入登录名!");
        return;
    }
    if (!_newRow.contact) {
        alert("请输入联系方式!");
        return;
    }
    if (_newRow.id == "-1") {/*添加*/
        _newRow.id = undefined;
        _newRow.password = $("#editRolePwd").val();
        _newRow.ctime = new Date().getTime();
        if (!_newRow.password) {
            alert("请输入密码!");
            return;
        }
        $.ajax({
            url: '/sysroles',
            type: "POST",
            contentType: 'application/json',
            data: JSON.stringify(_newRow),
            success: function (res) {
                if (res.code == 0) {
                    $("#roleAdmin").bootstrapTable("refresh");
                    alert("添加成功!");
                } else {
                    alert(res.msg);
                }
                $("#roleEdit").modal('hide');
            }
        });
    } else {/*修改*/
        _newRow.ctime = parseInt($("#editRoleCreateTime").val());
        _newRow.index = $("#rowIndex").val();
        $.ajax({
            type: 'POST',
            url: '/sysroles/' + _newRow.id,
            contentType: 'application/json',
            data: JSON.stringify(_newRow),
            success: function (result) {
                if (result.code == 0) {
                    $("#roleAdmin").bootstrapTable("updateRow", {index: _newRow.index, row: _newRow});
                } else {
                    $("#roleAdmin").bootstrapTable("updateRow", {index: _newRow.index, row: _newRow});
                    alert(result.msg);
                }
                $("#roleEdit").modal('hide');
            }
        });
    }
})
function showAddRoleModal() {
    $("#editRolePwdLi").show();
    $("#roleEdit").modal();
    $("#editId").val("-1")
    $("#editRoleTransCreateTime").val('暂无');
    $("#editRoleCreateTime").val('0');
    $("#rowIndex").val('');
    $("#editRoleName").val('');
    $("#editRolePosition").val('');
    $("#editRoleAccount").val('');
    $("#editRoleManage").val("false");
    $("#editRoleContact").val('');
    /*var addVal = {
     id: "-1",
     name: '<input class="form-control" name="name"/>',
     loginName: '<input class="form-control" name="loginName">',
     contact: '<input class="form-control" name="contact">',
     superAdmin: "<select name='superAdmin'><option value='false'>管理员</option><option value='true'>超级管理员</option></select>",
     password: "<input class='form-control' type='password' name='password'/>",
     ctiem: '0',
     title: "<input name='title' class='form-control'/>"
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
     }*/
}
