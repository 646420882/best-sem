<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    <title>百思-用户管理中心</title>
    <jsp:include page="../public/navujs.jsp"/>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="../public/header.jsp"/>
    <div class="containers">
        <jsp:include page="../public/nav.jsp"/>
        <div class="middle_containers">
            <div class="page_title">
                当前位置：日志管理
            </div>
            <div class="user_box">
                <div class="admin_title">
                    <label class="control-label fl"> 日志日期：</label>

                    <div class="fl select">
                        <div class="dropdown">
                            <input type="text" class="dateinput" readonly id="date" name="reservation"/>
                        </div>
                    </div>

                    <div class="fl">
                        <input type="text" class="adminInput" placeholder="请输入操作账户或操作人">
                        <span class="adminSearch">
                            <span class="glyphicon glyphicon-search"></span>
                        </span>
                    </div>
                    <div class="fr">
                        <button class="btn " style="background-color: #01aef0;color: #ffffff;    margin-bottom: 3px;">导出</button>
                    </div>
                    <!-- /.row -->

                </div>
                <table id="logAdmin">
                    <thead>
                    <tr>
                        <%--<th data-field="name">序号</th>--%>
                        <th data-field="user">操作人</th>
                        <th data-field="ip">IP地址</th>
                        <th data-field="desc">日志描述</th>
                        <th data-field="displayTime">记录时间</th>
                    </tr>
                    </thead>
                </table>
                <%--<div class="skip" style="position: absolute; margin: -45px 0 0 52%;">跳转到 <input id="selectPages" class='form-control' maxlength='3'  style='width: 38px;height: 22px; margin-top: 4px; display: inline;text-align: center;padding: 0' type='text'/>&nbsp;&nbsp;<span class="selectPage">Go</span></div>--%>
            </div>
        </div>

    </div>
</div>
<%--<script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>--%>
<script type="text/javascript" src="/public/js/daterangerpicker/bootstrap-daterangepicker-moment.js"></script>
<script type="text/javascript" src="/public/js/daterangerpicker/daterangepicker.js"></script>
<script type="text/javascript" src="/public/js/log/log.js"></script>
<link rel="stylesheet" href="/public/css/daterangepicker-bs2.css"/>
</body>
</html>

