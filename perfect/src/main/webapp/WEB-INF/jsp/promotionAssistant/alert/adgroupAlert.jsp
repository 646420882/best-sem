<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/8/27
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div  class="box" style="display:none" id="adAdd">
    <h2 id="bAdd">添加创意<a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        请选择单元!
        <ul class="zs_set" id="adUi">
            <%--<li><input type="radio" checked="checked" name="no1">&nbsp; 所有推广计划</li>--%>
        </ul>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li onclick="">确认</li>
                <li onclick="adgroupAddAlertClose()">取消</li>
            </ul>
        </div>
    </div>
</div>
