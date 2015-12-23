<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    <title>百思-用户管理中心</title>
    <jsp:include page="public/navujs.jsp"/>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="public/header.jsp"/>
    <div class="containers">
        <jsp:include page="public/nav.jsp"/>
        <div class="middle_containers">
            <div class="page_title">
                当前位置：用户管理
            </div>
            <div class="user_box">
                <div class="admin_title">
                    <label class="control-label fl"> 账户状态：</label>

                    <div class="fl select">
                        <select>
                            <option>所有账户</option>
                            <option>启用</option>
                            <option>禁用</option>
                        </select>
                    </div>

                    <div class="fl">
                        <span class="adminSearch">
                            <span class="glyphicon glyphicon-search"></span>
                        </span>
                    </div>
                    <!-- /.row -->

                </div>
                <div id="userTable">
                    <table id="userAdmin" data-click-to-select="true" data-query-params="queryParams" data-search="true"
                           data-search-align="left"
                           data-pagination="true">
                        <thead>
                        <tr>
                            <th data-field="state" data-checkbox="true"></th>
                            <th data-field="id">序号</th>
                            <th data-field="companyName">公司名称</th>
                            <th data-field="userName">用户名</th>
                            <th data-field="userPwd" data-formatter="passwordFormatter" data-events="operateEvents">密码
                            </th>
                            <th data-field="email">注册邮箱</th>
                            <th data-field="registerTime">注册日期</th>
                            <th data-field="contactPerson">联系人</th>
                            <th data-field="companyPhone">办公电话</th>
                            <th data-field="mobile">移动电话</th>
                            <th data-field="address">通讯地址</th>
                            <th data-field="look" data-formatter="LookUp" data-events="operateEvents">系统模块</th>
                            <th data-field="action" data-formatter="disableFormatter" data-events="operateEvents">账户状态
                            </th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
            <%--查看的内容--%>
            <div id="userLookUpWrap" style="display: none">
                <div class="lookUpContent">
                    <table id="userLookUpTable">
                        <thead>
                        <tr>
                            <th data-field="systemModal">系统模块</th>
                            <th data-field="userProperty">用户属性</th>
                            <th data-field="openStates">开通状态</th>
                            <th data-field="startDate">开始日期</th>
                            <th data-field="endDate">结束日期</th>
                            <th data-field="authorityAssignment">权限分配</th>
                            <th data-field="relatedAccount">关联账户</th>
                            <th data-field="relatedAccountPwd">关联账户密码</th>
                            <th data-field="APICode">API代理</th>
                            <th data-field="URLAddress">URL地址</th>
                            <th data-field="APICode">统计代码</th>
                        </tr>
                        </thead>
                    </table>
                    <div class="lookUpConfirmBtn fr">
                        <span class="adminButton" onclick="cancelLookUp()">确定</span>
                        <span class="adminButton" onclick="cancelLookUp()"
                              style="background-color: #e0e0e0;color: #000">取消</span>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="home/homemodel.jsp"/>
<script type="text/javascript" src="/public/js/index/index.js"></script>
</body>
</html>

