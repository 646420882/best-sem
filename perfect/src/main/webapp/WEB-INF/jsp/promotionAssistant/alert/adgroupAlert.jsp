<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2014/8/27
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--添加单元时，如果没有选择计划，则弹出该窗口--%>
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

<%--添加单元时点击设定关键词时弹出框--%>
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

<%--单元选择时右键点击修改弹出框--%>
<div class="box" style="display:none" id="adUpdate">
    <h2 id="adUp">修改<a href="#" class="close">关闭</a></h2>
    <div class="mainlist">
        <form id="adgroupUpdateForm">
            <input name="oid"/>
            <label>推广单元名称：</label><input name="adgroupName"/>
            <label>推广单元状态：</label><label id="adStatus"></label></br>
            <label>是否启用：</label><select name="pause"><option value="true">启用</option><option value="false">暂停</option></select>
            <label>出价</label><input name="maxPrice" onkeypress="until.regDouble(this)" maxlength="3"/>
            <label>否定关键词：</label><label id="usp"></label><a href="javascript:void(0)" onclick="adgroupUpdateNokwdMath();">设定</a></br>
            <label>移动出价比例：</label><input name="mib" onkeypress="until.regDouble(this)" maxlength="3"/></br>
            <input name="negativeWords"/><input name="exactNegativeWords"/>
        </form>
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

<%--修改时弹出的否定关键处理框--%>
<div class="box" style="display:none" id="apNoKwd">
    <h2 id="apKwd">否定关键词设定<a href="#" class="close">关闭</a></h2>
    <div class="mainlist">
        以下下设置仅对“广泛”，“短语”匹配的关键词生效。每行一词，没词20字以内，最多200项
        <ul style="list-style: circle; overflow:hidden;"  class="sets" >
            <li>
                <p>否定关键词<span>(<span id="npKwd_size">0</span>/200)</span></p>

                <div id="pnormalNokwd">
                    <textarea id="npKwd" rows="10"></textarea>
                </div>
            </li>
            <li>
                <p>精确否定关键词<span>(<span id="mpKwd_size">0</span>/200)</span></p>
                <div id="pmatchNokwd">
                    <textarea id="mpKwd" rows="10"></textarea>
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

