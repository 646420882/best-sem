<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="menu">
    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel totalNav panel-default current">
            <div class="panel-heading" role="tab" id="headingOne">
                <h4 class="panel-title">
                    <a role="button" data-parent="#accordion" href="/index"
                       aria-expanded="true" aria-controls="collapseOne">
                        <span aria-hidden="true" ng-class="icon" class="glyphicon glyphicon-user"></span>
                        用户管理
                    </a>
                </h4>
            </div>
        </div>
        <div class="panel totalNav  panel-default">
            <div class="panel-heading" role="tab">
                <h4 class="panel-title">
                    <a class="collapsed" role="button" data-parent="#accordion"
                       href="${pageContext.request.contextPath}/admin/role" aria-expanded="false"
                       aria-controls="collapseTwo">
                        <span aria-hidden="true" ng-class="icon" class="glyphicon glyphicon-picture"></span> 角色管理
                    </a>
                </h4>
            </div>
        </div>
        <div class="panel totalNav  panel-default">
            <div class="panel-heading" role="tab">
                <h4 class="panel-title">
                    <a class="collapsed" role="button" data-parent="#accordion"
                       href="${pageContext.request.contextPath}/admin/system" aria-expanded="false"
                       aria-controls="collapseTwo">
                        <span aria-hidden="true" ng-class="icon" class="glyphicon glyphicon-adjust"></span> 系统模块
                    </a>
                </h4>
            </div>
        </div>
        <div class="panel totalNav  panel-default">
            <div class="panel-heading" role="tab">
                <h4 class="panel-title">
                    <a class="collapsed" role="button" data-parent="#accordion"
                       href="${pageContext.request.contextPath}/admin/jurisdiction" aria-expanded="false"
                       aria-controls="collapseTwo">
                        <span aria-hidden="true" ng-class="icon" class="glyphicon glyphicon-th"></span>模块权限
                    </a>
                </h4>
            </div>
        </div>
        <div class="panel totalNav  panel-default">
            <div class="panel-heading" role="tab">
                <h4 class="panel-title">
                    <a class="collapsed" role="button" data-parent="#accordion"
                       href="${pageContext.request.contextPath}/admin/log" aria-expanded="false"
                       aria-controls="collapseTwo">
                        <span aria-hidden="true" ng-class="icon" class="glyphicon glyphicon-list-alt"></span>日志管理
                    </a>
                </h4>
            </div>
        </div>
    </div>
</div>

</div>
