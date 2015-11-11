<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2015/10/28
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--添加监控文件夹弹出窗口--%>
<div class="box" style="display:none" id="AddKeywords">
    <h2 id="AddKeywordsTitleNew" class="boxtitle">
        <span class="fl">添加关键词</span>
        <a href="javascript:void(0)" onclick="closeAlert();" class="close">×</a></h2>

    <div class="mainlist">
        <ul class="add_title">
            <li>
                <span>推广地域：</span><a href="#">使用账户推广地域</a>
            </li>
            <li>
                <span>推广设备：</span>
                <select id="device_selectNew" class="selectpicker">
                    <option value="0">全部设备</option>
                    <option value="1">计算机</option>
                    <option value="2">移动设备</option>
                </select>
            </li>
            <li>
                <input type="checkbox" checked="checked">与账户已有词去重
            </li>
        </ul>
        <div class="add_textarea">
            <textarea id="statusNew" rows="12" cols="40" oninput="countAddKwd()"></textarea>
            <span class="fr"><label id="counterNew" style="font-weight:normal"> 0</label>/ <label id="countNumber">5000</label></span>
        </div>
        <input type="hidden" id="countkwd" value="">
    </div>
    <div class="main_bottom add_bottom" style="background-color: #f8f8f8">
        <div class="w_list03">
            <select id="campaign_selectNew" class="selectpicker fl"></select>
            <select id="adgroup_selectNew" class="selectpicker fl" onchange="validateNoAllowKeyword(this.value)"></select>
            <button type="button" class="btn btn-primary fr" onclick="AddKeywordsSave()">保存</button>
        </div>
    </div>
</div>
<%--  保存设置 --%>
<div class="box" style="display:none;z-index: 1001" id="SaveSet">
    <h2  id="AddKeywordsplanTitle">
        <span class="fl">保存设置</span>
        <a href="javascript:void(0)" onclick="closeAlert();" class="close">×</a></h2>

    <div class="mainlist saveset">
        <ul>
            <li>
                <div class="planbox1 fl">统一出价：</div>
                <div class="planbox2 fl">
                    <input id="priceNew" type="text" class="plan_input"
                           onkeyup='until.regDouble(this)' maxlength="7"> 为空则采用单元出价
                </div>
            </li>
            <li>

            </li>
            <li>
                <div class="planbox1 fl">统一匹配方式：</div>
                <div class="planbox1 fl">
                    <%--<select class="selectpicker plan_input ">
                        <option>广泛</option>
                        <option>短语-核心包含</option>
                        <option>短语-同义包含</option>
                        <option>短语-同义包含</option>
                        <option>精确</option>
                    </select>--%>
                    <select id="matchTypeNew" class="selectpicker plan_input">
                        <option value="1">精确</option>
                        <option value="2">短语</option>
                        <option value="3">广泛</option>
                    </select>

                    <div id="phraseTypeDivNew" style="display: none;">
                        <select id="phraseTypeNew" class="selectpicker plan_input">
                            <option value="1">同义包含</option>
                            <option value="2">精确包含</option>
                            <option value="3">核心包含</option>
                        </select>
                    </div>
                </div>
            </li>
        </ul>
        <span id="bdAccountIdNew" style="display: none"></span>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current" onclick="saveKeywordNew()">确定</li>
                <li class="close" onclick="closeAlert();">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--  添加成功 --%>
<div class="box" style="display:none" id="SaveSeccuss">
    <h2>
        <span class="fl">添加成功</span>
        <a href="javascript:void(0)" onclick="closeAlert();" class="close addcolse">×</a></h2>
    <div class="mainlist saveset">
        <div>以下关键词未被保存,因本地库中存在：</div>
        <div  id="context"></div>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current addcolse">确定</li>
            </ul>
        </div>
    </div>
</div>