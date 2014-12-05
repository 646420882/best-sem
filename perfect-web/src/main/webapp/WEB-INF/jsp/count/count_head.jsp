<%--
  Created by IntelliJ IDEA.
  User: SubDong
  Date: 2014/11/24
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="top">
    <div class="top_logo fl">
        <img src="${pageContext.request.contextPath}/public/img/logo.png">
    </div>
    <div class="top_nav fr">
        <ul>
            <li> <a href="#">返回首页</a></li>
            <li> <a href="#">帮助</a> </li>
            <li> <a href="#">最新功能</a> </li>
            <li>
                <ul class="nav nav-pills">
                    <li class="dropdown">
                                 <span class="dropdown-toggle"
                                       data-toggle="dropdown"
                                       href="#">
                                     移动统计
                                     <b class="caret"></b>
                                 </span>

                        <ul class="dropdown-menu">
                            <!-- links -->
                            <li>百度联盟</li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li>
                <ul class="nav nav-pills">
                    <li class="dropdown">
                                 <span class="dropdown-toggle"
                                       data-toggle="dropdown"
                                       href="#">
                                      官方博客
                                     <b class="caret"></b>
                                 </span>
                        <ul class="dropdown-menu">
                            <!-- links -->
                            <li>讨论区</li>
                            <li>联系我们</li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li class="top_select">
                <select>
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                    <option>4</option>
                    <option>5</option>
                </select>
            </li>
        </ul>
    </div>
</div>
