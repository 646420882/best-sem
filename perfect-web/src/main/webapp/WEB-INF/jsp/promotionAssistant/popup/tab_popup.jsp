<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2015/10/12
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="dropdown-menus tabmodel" id="filterSearchTemplate">
    <div class="tabmodel_title"><span id="TabTitle"></span><span class="fr glyphicon glyphicon-remove" onclick="TabModel.modelClose()"></span></div>
    <input type="hidden" name="filterField"/>
    <div class="tabmodel_content">
        <div id="CheckList">
        </div>
        <span style="color: red;" id="filter_msg"></span>
        <button type="button" class="btn btn-primary btn-sm " onclick="TabModel.filterSearchOk()" >确认</button>
        <button type="button" class="btn btn-default btn-sm " onclick="TabModel.modelClose()">取消</button>
    </div>
</div>
<div class="assstant_editor">
    <ul>
        <li onclick="editCommons.Cut()"><a href="#"><span class="zs_top"><img
                src="../public/img/zs_function14.png"></span><b>剪切</b></a></li>
        <li onclick="editCommons.Copy()"><a href="#"><span class="zs_top"><img
                src="../public/img/zs_function13.png"></span><b>复制</b></a></li>
        <li onclick="editCommons.Parse()"><a href="#"><span class="zs_top"><img
                src="../public/img/zs_function15.png"></span><b>粘贴</b></a></li>
        <li id="Textreplacement"><a href="#"><span class="zs_top"><img
                src="../public/img/zs_function_text.png"></span><b>文字替换</b></a></li>
        <li onclick="commons.batchDel()"><a href="#"><span class="zs_top"><img
                src="../public/img/zs_function_text.png"></span><b>批量删除</b></a></li>
    </ul>
</div>

