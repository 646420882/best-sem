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
    <h2 id="AddKeywordsTitle">
        <span class="fl">添加关键词</span>
        <a href="javascript:void(0)" onclick="closeAlert();" class="close">×</a></h2>

    <div class="mainlist">
        <ul class="add_title">
            <li>
                <span>推广地域：</span><a href="#">使用账户推广地域</a>
            </li>
            <li>
                <span>推广设备：</span>
                <select class="selectpicker">
                    <option>全部设备</option>
                    <option>计算机</option>
                    <option>移动设备</option>
                </select>
            </li>
            <li>
                <input type="checkbox" checked="checked">与账户已有词去重
            </li>
        </ul>
        <div class="add_textarea">
            <textarea id="status" rows="12" cols="40" onkeydown='countChar("status","counter");'
                      onkeyup='countChar("status","counter");'></textarea>
            <span class="fr"><label id="counter" style="font-weight:normal"> 0</label>/ 5000</span>
        </div>
    </div>
    <div class="main_bottom add_bottom" style="background-color: #f8f8f8">
        <div class="w_list03">
            <select class="selectpicker fl">
                <option selected="true" disabled="true">请选择推广计划</option>
                <option> 新建推广计划</option>
                <option>百思</option>
                <option>通过词</option>
                <option>品牌计划</option>
            </select>
            <select class="selectpicker fl">
                <option selected="true" disabled="true">请选择推广计划</option>
                <option> 新建推广计划</option>
                <option>百思</option>
                <option>通过词</option>
                <option>品牌计划</option>
            </select>
            <button type="button" class="btn btn-primary fr" onclick="AddKeywordsSave()">保存</button>
        </div>
    </div>
</div>
<div class="box" style="display:none" id="SaveSet">
    <h2>
        <span class="fl">保存设置</span>
        <a href="javascript:void(0)" onclick="closeAlert();" class="close">×</a></h2>

    <div class="mainlist saveset">
        <ul>
            <li>
                <div class="planbox1 fl">统一出价：</div>
                <div class="planbox2 fl">
                    <input type="text" class="plan_input" onkeyup="this.value=this.value.replace(/\D/g,'')"
                           onafterpaste="this.value=this.value.replace(/\D/g,'')"> 为空则采用单元出价
                </div>
            </li>
            <li>

            </li>
            <li>
                <div class="planbox1 fl">统一匹配方式：</div>
                <div class="planbox1 fl">
                    <select class="selectpicker plan_input ">
                        <option>广泛</option>
                        <option>短语-核心包含</option>
                        <option>短语-同义包含</option>
                        <option>短语-同义包含</option>
                    </select>
                </div>
            </li>
        </ul>

    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current">确定</li>
                <li class="close" onclick="closeAlert();">取消</li>
            </ul>
        </div>
    </div>
</div>