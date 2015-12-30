<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    <title>百思-用户管理中心</title>
    <link rel="stylesheet" href="/public/css/daterangepicker-bs2.css"/>
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
                        <select id="selectInfo" onchange="getAdminInfo2()">
                            <option value="0">所有账户</option>
                            <option value="true">启用</option>
                            <option value="false">禁用</option>
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
                    <table id="userAdmin">
                        <thead>
                        <tr>
                            <th data-field="state" data-checkbox="true"></th>
                            <%--<th data-field="id">序号</th>--%>
                            <th data-field="companyName">公司名称</th>
                            <th data-field="userName">用户名</th>
                            <th data-field="userPwd" data-formatter="passwordFormatterUser" data-events="operateEvents">密码
                            </th>
                            <th data-field="email">注册邮箱</th>
                            <th data-field="displayCtime">注册日期</th>
                            <th data-field="contactName">联系人</th>
                            <th data-field="telephone">办公电话</th>
                            <th data-field="mobilePhone">移动电话</th>
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
                            <th data-field="startDate" data-formatter="startTimeFormatter" data-events="operateEvents">开始日期</th>
                            <th data-field="endDate" data-formatter="endTimeFormatter" data-events="operateEvents">结束日期</th>
                            <th data-field="authorityAssignment" data-formatter="setFormatter">权限分配</th>
                            <th data-field="relatedAccount" data-formatter="relationAccountFormatter">关联账户</th>
                            <th data-field="relatedAccountPwd" data-formatter="relationPwdFormatter">关联账户密码</th>
                            <th data-field="APICode" data-formatter="relationApiFormatter" data-events="operateEvents">Token</th>
                            <th data-field="URLAddress" data-formatter="relationUrlFormatter">URL地址</th>
                            <th data-field="statisticalCode" data-formatter="relationCodeFormatter">统计代码</th>
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
<script type="text/javascript" src="/public/js/daterangerpicker/bootstrap-daterangepicker-moment.js"></script>
<script type="text/javascript" src="/public/js/daterangerpicker/daterangepicker.js"></script>
<script type="text/javascript" src="/public/js/index/index.js"></script>
</body>
</html>

