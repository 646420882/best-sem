<%--
  Created by IntelliJ IDEA.
  User: guochunyan
  Date: 2015/12/14
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-default ng-scope" role="navigation" ng-controller="ngSelect">
  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#example-navbar-collapse">
      <span class="sr-only">切换效果</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a class="logo" href="/#index"><img src="/public/img/logo.png"></a>
  </div>
  <div class="collapse navbar-collapse top_menu fr" id="example-navbar-collapse">
    <ul class="nav navbar-nav">
      <li><a href="/platform"><span aria-hidden="true" class="glyphicon glyphicon-home"></span>首页</a></li>
      <li>
        <span aria-hidden="true" class="glyphicon glyphicon-user fl"></span>
        <span data-ng-bind="perfectUser" class="ng-binding">${user.userName}</span>
      </li>
      <li><a href="/logout"><span aria-hidden="true" class="glyphicon glyphicon-share"></span>退出</a></li>
    </ul>
  </div>
</nav>