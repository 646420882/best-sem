<%--
  Created by IntelliJ IDEA.
  User: guochunyan
  Date: 2015/12/17
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Button trigger modal -->
<!-- Modal -->
<div class="modal fade bs-example-modal-sm" id="modelbox" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        class="glyphicon glyphicon-remove" style="font-size: 16px" aria-hidden="true"></span></button>
                <h4 class="modal-title" id="modelboxTitle"></h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>密码:</label>
                    <input class="form-control" type="password" name="resetPwd" placeholder="如果不输入则为系统初始密码"/>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="modelboxBottom">确认</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>

            </div>
        </div>
    </div>
</div>

<div class="modal fade bs-example-modal-sm" id="modelbox1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        class="glyphicon glyphicon-remove" style="font-size: 16px" aria-hidden="true"></span></button>
                <h4 class="modal-title" id="modelboxTitle1"></h4>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="modelboxBottom1">确认</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>

            </div>
        </div>
    </div>
</div>


<div class="modal fade bs-example-modal-sm" id="modelbox2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        class="glyphicon glyphicon-remove" style="font-size: 16px" aria-hidden="true"></span></button>
                <h4 class="modal-title" id="modelboxTitle2"></h4>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="modelboxBottom2">确认</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>

            </div>
        </div>
    </div>
</div>
<%--<div class="modal fade bs-example-modal-sm" id="addRoleModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">--%>
<%--<div class="modal-dialog" role="document">--%>
<%--<div class="modal-content">--%>
<%--<div class="modal-header">--%>
<%--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span--%>
<%--class="glyphicon glyphicon-remove" style="font-size: 16px" aria-hidden="true"></span></button>--%>
<%--<h4 class="modal-title" id="addRole"></h4>--%>
<%--</div>--%>
<%--<div class="modal-body">--%>

<%--</div>--%>
<%--<div class="modal-footer">--%>
<%--<button type="button" class="btn btn-primary" id="addRoleSubmit">确认</button>--%>
<%--<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>--%>

<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>


<div class="modal fade bs-example-modal-sm" id="tokenBox" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        class="glyphicon glyphicon-remove" style="font-size: 16px" aria-hidden="true"></span></button>
                <h4 class="modal-title" id="tokenBoxTitle"></h4>
            </div>
            <div class="modal-body">
                <input type="text" class="" id="tokenBoxInput" value="">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="tokenBoxBottom">确认</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>

            </div>
        </div>
    </div>
</div>

<div class="modal fade bs-example-modal-sm" id="roleEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-sm" role="document" style="width: 400px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        class="glyphicon glyphicon-remove" style="font-size: 16px" aria-hidden="true"></span></button>
                <h4 class="modal-title" id="roleEditTitle">角色修改</h4>
            </div>
            <div class="modal-body">
                <ul>
                    <li>
                        <label>姓名：</label>
                        <input type="hidden" id="editId">
                        <input type="hidden" id="rowIndex">
                        <input type="text" class="form-control" id="editRoleName">
                    </li>
                    <li>
                        <label>职务：</label>
                        <input type="text" class="form-control" id="editRolePosition">
                    </li>
                    <li>
                        <label>登录账号：</label>
                        <input type="text" class="form-control" id="editRoleAccount">
                    </li>
                    <li id="editRolePwdLi">
                        <label>登录密码：</label>
                        <input type="password" class="form-control" id="editRolePwd">
                    </li>
                    <li>
                        <label>是否超级管理员：</label>
                        <select id="editRoleManage">
                            <option value='false'>管理员</option>
                            <option value='true'>超级管理员</option>
                        </select>
                    </li>
                    <li>
                        <label>创建日期：</label>
                        <input type="text" class="form-control" id="editRoleTransCreateTime" disabled>
                        <input type="hidden" id="editRoleCreateTime">
                    </li>
                    <li>
                        <label>联系方式：</label>
                        <input type="text" class="form-control" id="editRoleContact">
                    </li>
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="roleEditBottom">确认</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>

            </div>
        </div>
    </div>
</div>

