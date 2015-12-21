<%--
  Created by IntelliJ IDEA.
  User: guochunyan
  Date: 2015/12/14
  Time: 13:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="menu">
    <div class="panel-group " id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel totalNav  panel-default">
            <div class="panel-heading" role="tab">
                <h4 class="panel-title">
                    <a class="collapsed" role="button" data-parent="#accordion"
                       href="${pageContext.request.contextPath}/" aria-expanded="false"
                       aria-controls="collapseTwo">
                        <span aria-hidden="true" ng-class="icon" class="glyphicon glyphicon-th-large "></span> 账户概览
                    </a>
                </h4>
            </div>
        </div>
        <div class="panel totalNav  panel-default">
            <div class="panel-heading" role="tab">
                <h4 class="panel-title">
                    <a class="collapsed" role="button" data-parent="#accordion"
                       href="${pageContext.request.contextPath}/account" aria-expanded="false"
                       aria-controls="collapseTwo">
                        <span aria-hidden="true" ng-class="icon" class="glyphicon glyphicon-ok"></span> 账户绑定
                    </a>
                </h4>
            </div>
        </div>
        <div class="panel totalNav  panel-default">
            <div class="panel-heading" role="tab">
                <h4 class="panel-title">
                    <a class="collapsed" role="button" data-parent="#accordion"
                       href="${pageContext.request.contextPath}/password" aria-expanded="false"
                       aria-controls="collapseTwo">
                        <span aria-hidden="true" ng-class="icon" class="glyphicon glyphicon-lock"></span> 密码管理
                    </a>
                </h4>
            </div>
        </div>
        <div class="panel totalNav  panel-default">
            <div class="panel-heading" role="tab">
                <h4 class="panel-title">
                    <a class="collapsed" role="button" data-parent="#accordion"
                       href="${pageContext.request.contextPath}/safetyTool" aria-expanded="false"
                       aria-controls="collapseTwo">
                        <span aria-hidden="true" ng-class="icon" class="glyphicon glyphicon-wrench"></span> 安全工具
                    </a>
                </h4>
            </div>
        </div>
    </div>
</div>

</div>
