<%--
  Created by IntelliJ IDEA.
  User: guochunyan
  Date: 2015/12/14
  Time: 13:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="menu">
    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default current">
            <div class="panel-heading" role="tab" id="headingOne">
                <h4 class="panel-title">
                    <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                       aria-expanded="true" aria-controls="collapseOne">
                        <span aria-hidden="true" ng-class="icon" class="glyphicon glyphicon-th-large"></span>
                        账户概览
                    </a>
                </h4>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab">
                <h4 class="panel-title">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="../account/account.jsp" aria-expanded="false" aria-controls="collapseTwo">
                        <span aria-hidden="true" ng-class="icon" class="glyphicon glyphicon-ok"></span> 账户绑定
                    </a>
                </h4>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading" role="tab">
                    <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="../password/password.jsp.jsp" aria-expanded="false" aria-controls="collapseTwo">
                            <span aria-hidden="true" ng-class="icon" class="glyphicon glyphicon-lock"></span> 密码管理
                        </a>
                    </h4>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading" role="tab">
                    <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="../safe/safetool.jsp" aria-expanded="false" aria-controls="collapseTwo">
                            <span aria-hidden="true" ng-class="icon" class="glyphicon glyphicon-wrench"></span> 安全工具
                        </a>
                    </h4>
                </div>
            </div>
        </div>
    </div>

</div>
