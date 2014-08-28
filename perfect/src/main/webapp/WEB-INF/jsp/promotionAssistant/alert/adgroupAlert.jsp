<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/8/27
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<style type="text/css">

</style>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="box" style="display:none" id="adAdd">
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
                <li onclick="adgroudAddAlertOk()">确认</li>
                <li onclick="adgroupAddAlertClose()">取消</li>
            </ul>
        </div>
    </div>
</div>


<div class="box" style="display:none" id="aNoKwd">
    <h2 id="aKwd">否定关键词设定<a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        以下下设置仅对“广泛”，“短语”匹配的关键词生效。每行一词，没词20字以内，最多200项
        <ul style="list-style: circle; overflow:hidden;"  class="sets" >
            <li>
                <p>否定关键词<span>(<span id="nKwd_size">0</span>/200)</span></p>

                <div id="normalNokwd">
                    <textarea id="nKwd" rows="10"></textarea>
                </div>
            </li>
            <li>
                <p>精确否定关键词<span>(<span id="mKwd_size">0</span>/200)</span></p>
                <div id="matchNokwd">
                    <textarea id="mKwd" rows="10"></textarea>
                </div>
            </li>
        </ul>
    </div>

    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li onclick="adgroupNoKeywordOk()">确认</li>
                <li onclick="adgroupAddAlertClose()">取消</li>
            </ul>
        </div>
    </div>
</div>
