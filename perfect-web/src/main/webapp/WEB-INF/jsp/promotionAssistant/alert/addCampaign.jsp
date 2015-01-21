<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2014/9/1
  Time: 13:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--------------添加推广计划---------------->
<div class="TB_overlayBG" style="display:none;"></div>
<div class="box" id="plan" style="display:none;  z-index:999;">
    <h2 id="plan2">添加推广计划<b class="close closeAddCampaign">关闭</b></h2>

    <div class="mainlist2 over">
        <div class="mainlist">
            <div class="plan_box over">
                <h3>添加推广计划<span>(*为必填选项)</span></h3>
                <ul>
                    <li>
                        <div class="planbox1 fl">
                            <span>*</span>计划名称：
                        </div>
                        <div class="planbox2 fl">
                            <input type="text" class="plan_input inputCampaignName" value="<请输入推广计划名称>"
                                   onfocus="if(value=='<请输入推广计划名称>') {value=''}"
                                   onblur="if (value=='') {value='<请输入推广计划名称>'}">
                        </div>
                    </li>
                    <li>
                        <div class="planbox1 fl">
                            <span></span>每日预算：
                        </div>
                        <div class="planbox2 fl">
                            <input type="text" class="plan_input inputBudget" value="<请输入每日预算，不填默认为不限定>"
                                   onfocus="if(value=='<请输入每日预算，不填默认为不限定>') {value=''}"
                                   onblur="if (value=='') {value='<请输入每日预算，不填默认为不限定>'}">
                        </div>
                    </li>
                    <li>
                        <div class="planbox1 fl">
                            <span>*</span>移动出价比例：
                        </div>
                        <div class="planbox2 fl">
                            <input type="text" class="plan_input inputPriceRatio">
                        </div>
                    </li>
                    <li>
                        <div class="planbox1 fl">
                            <span>*</span>启用/暂停：
                        </div>
                        <div class="planbox2 fl">
                            <select class="inputCampaignPause">
                                <option value="false">启用</option>
                                <option value="true">暂停</option>
                            </select>

                        </div>
                    </li>
                    <li>
                        <div class="planbox1 fl">
                            <span>*</span>创意展现：
                        </div>
                        <div class="planbox2 fl">
                            <select class="inputShowProb">
                                <option value="1">优选</option>
                                <option value="2">轮显</option>
                            </select>

                        </div>
                    </li>
                    <li>
                        <div class="planbox1 fl">
                            <span></span>推广时段：
                        </div>
                        <div class="planbox2 fl">
                            <a href="#" class="inputSchedule_add">点击设置</a>
                        </div>
                    </li>
                    <%-- <li>
                         <div class="planbox1 fl">
                             <span></span>推广地域：
                         </div>
                         <div class="planbox2 fl">
                             <a href="#">使用账户推广地域</a>
                         </div>
                     </li>--%>
                    <li>
                        <div class="planbox1 fl">
                            <span></span>否定关键词：
                        </div>
                        <div class="planbox2 fl">
                            <a href="#" class="inputNegativeWords_add">点击设置</a>
                        </div>
                    </li>
                    <li>
                        <div class="planbox1 fl">
                            <span></span>IP排除：
                        </div>
                        <div class="planbox2 fl">
                            <a href="#" class="inputExcludeIp_add">点击设置</a>
                        </div>
                    </li>
                </ul>
                <h3>为此推广计划创建第一个推广单元</h3>
                <ul>
                    <li>
                        <div class="planbox1 fl">
                            <span>*</span>单元名称：
                        </div>
                        <div class="planbox2 fl">
                            <input type="text" class="plan_input" id="inputAdgroupName" value="<请输入推广单元名称>"
                                   onfocus="if(value=='<请输入推广单元名称>') {value=''}"
                                   onblur="if (value=='') {value='<请输入推广单元名称>'}">
                        </div>
                    </li>
                    <li>
                        <div class="planbox1 fl">
                            <span></span>出价：
                        </div>
                        <div class="planbox2 fl">
                            <input type="text" id="inputAdgroupPrice"/>

                        </div>
                    </li>
                    <li>
                        <div class="planbox1 fl">
                            <span></span>启用/暂停：
                        </div>
                        <div class="planbox2 fl">
                            <select id="inputAdgroupPause">
                                <option value="false">启用</option>
                                <option value="true">暂停</option>
                            </select>

                        </div>
                    </li>
                    <li>
                        <div class="planbox1 fl">
                            <span></span>移动出价比例：
                        </div>
                        <div class="planbox2 fl">
                            <input type="text" id="inputAdgroupPriceRatio"/>
                            默认为计划移动出价比例
                        </div>
                    </li>
                </ul>

            </div>

        </div>
        <div class="main_bottom">
            <div class="w_list03">
                <ul>
                    <li id="createCampaignOk">确定创建推广计划</li>
                </ul>
            </div>
        </div>
    </div>
</div>
</div>

