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
    <h2 id="bAdd">添加单元<a href="javascript:void(0)" onclick="adgroupAddAlertClose()" class="close">×</a></h2>

    <div class="mainlist">
        请选择单元!
        <ul class="zs_set" id="adUi">
            <%--<li><input type="radio" checked="checked" name="no1">&nbsp; 所有推广计划</li>--%>
        </ul>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li onclick="adgroudAddAlertOk()" class="current">确认</li>
                <li onclick="adgroupAddAlertClose()">取消</li>
            </ul>
        </div>
    </div>
</div>

<%--添加单元时点击设定关键词时弹出框--%>
<div class="box" style="display:none" id="aNoKwd">
    <h2 id="aKwd">否定关键词设定<a href="#" class="close">×</a></h2>

    <div class="mainlist">
        以下下设置仅对“广泛”，“短语”匹配的关键词生效。每行一词，没词20字以内，最多200项
        <ul style="list-style: circle; overflow:hidden;" class="sets">
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
                <li onclick="adgroupNoKeywordOk()" class="current">确认</li>
                <li onclick="adgroupAddAlertClose()">取消</li>
            </ul>
        </div>
    </div>
</div>

<%--单元选择时右键点击修改弹出框--%>
<div class="box" style="display:none" id="adUpdate">
    <h2 id="adUp">修改单元<a href="#" class="close" onclick="adgroupAddAlertClose()">×</a></h2>

    <div class="mainlist">
        <div class="plan_box over">
            <form id="adgroupUpdateForm">
                <ul>
                    <li>
                        <div class="planbox1 fl"> 推广单元名称</div>
                        <div class="planbox2 fl">
                            <input type="hidden" name="oid"/>
                            <input type="hidden" name="cn"/>
                            <input type="text" onblur="if (value=='') {value='&lt;请输入推广单元名称&gt;'}"
                                   onfocus="if(value=='&lt;请输入推广单元名称&gt;') {value=''}" value="&lt;请输入推广单元名称&gt;"
                                   class="plan_input inputCampaignName" name="adgroupName">
                        </div>
                    </li>
                    <li>
                        <div class="planbox1 fl"> 推广单元状态:</div>
                        <div class="planbox2 fl">
                            <label id="adStatus"></label>
                        </div>
                    </li>
                    <li>
                        <div class="planbox1 fl"> 是否启用:</div>
                        <div class="planbox2 fl">
                            <select name="pause" class="inputCampaignPause">
                                <option value="true">启用</option>
                                <option value="false">暂停</option>
                            </select>
                        </div>
                    </li>
                    <li>
                        <div class="planbox1 fl">最高出价:</div>
                        <div class="planbox2 fl">
                            <input class="plan_input inputCampaignName" type="text" name="maxPrice"
                                   onkeypress="until.regDouble(this)" maxlength="4"/>
                        </div>
                    </li>
                    <li>
                        <div class="planbox1 fl">否定关键词:</div>
                        <div class="planbox2 fl">
                            <label id="usp"></label><label id="auSpan">未设定</label><a href="javascript:void(0)"
                                                                                     onclick="adgroupUpdateNokwdMath();">设定</a>
                        </div>
                    </li>
                    <li>
                        <div class="planbox1 fl">
                            <span></span>
                        </div>
                        <div class="planbox2 fl">
                            <input type="hidden" name="negativeWords"/><input type="hidden" name="exactNegativeWords"/>
                        </div>
                    </li>

                </ul>
            </form>
        </div>
        <%-- <form id="adgroupUpdateForm">
             <input type="hidden" name="oid"/>
             <label>推广单元名称：</label><input name="adgroupName" maxlength="30"/>
            &lt;%&ndash; <label>推广单元状态：</label><label id="adStatus"></label></br>&ndash;%&gt;
             <label>是否启用：</label><select name="pause"><option value="true">启用</option><option value="false">暂停</option></select>
             <label>最高出价</label><input name="maxPrice" onkeypress="until.regDouble(this)" maxlength="4"/>
           &lt;%&ndash;  <label>否定关键词：</label><label id="usp"></label><label id="auSpan">未设定</label><a href="javascript:void(0)" onclick="adgroupUpdateNokwdMath();">设定</a></br>&ndash;%&gt;

         </form>--%>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li onclick="adrgoupUpdateOk()">确认</li>
                <li onclick="adgroupAddAlertClose()">取消</li>
            </ul>
        </div>
    </div>
</div>

<%--修改时弹出的否定关键处理框--%>
<div class="box" style="display:none" id="apNoKwd">
    <h2 id="apKwd">否定关键词设定<b class="fr" onclick="adgroupUpdateNokwdMathClose()">×</b></h2>

    <div class="mainlist">
        以下下设置仅对“广泛”，“短语”匹配的关键词生效。每行一词，没词20字以内，最多200项
        <ul style="list-style: circle; overflow:hidden;" class="sets">
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
                <li onclick="adgroupUpdateNokwdMathOk()">确认</li>
                <li onclick="adgroupUpdateNokwdMathClose()">取消</li>
            </ul>
        </div>
    </div>
</div>


