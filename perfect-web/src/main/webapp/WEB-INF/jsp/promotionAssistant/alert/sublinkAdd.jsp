<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2015/2/26
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    #sublinkAddInput input {
        height: 28px;
        line-height: 28px;
    }

    /*.list2_box2 {*/
        /*background: none repeat scroll 0 0 #ecf3f9;*/
    /*}*/
</style>
<%--添加附加创意子链弹出框--%>
<div class="box" style="display:none" id="addSublinkDiv">
    <h2 id="SublinkDiv">
        <span class="fl">添加蹊径子链</span>
        <a href="javascript:void(0)" onclick="closeSubLinkDialog();" class="close">×</a></h2>

    <div class="mainlist">
        <div id="sublinkAddInput">
            <ul>
                <li>
                    <span>您的域名为：</span><span style="color:red;" class="doMainS"></span>
                    <span>子链名称总字节数：</span><span id="maxByte">0</span>
                </li>
                <li>
                    <label>投放设备</label>
                    <select id="subMtype" onchange="mobileTypeAdd();">
                        <option value="0">计算机</option>
                        <option value="1">移动设备</option>
                    </select>
                    <select id="subCampaign" onchange="initSelectAdgroup()"></select>
                    <select id="subAdgroup"></select>
                </li>
                <li><label>子链一名称：</label>
                    <input name="linkNameIn1" type="text" maxlength="16"/><span>0/16</span></li>
                <li>  <label>子链一URL：</label>
                    <input name="linkURLIn1" type="text" maxlength="1024"/><span>0/1024</span></li>
                <li>
                    <label>子链二名称：</label>
                    <input name="linkNameIn2" type="text" maxlength="16"/><span>0/16</span>
                </li>
                <li>      <label>子链二URL：</label>
                    <input name="linkURLIn2" type="text" maxlength="1024"/><span>0/1024</span></li>
                <li><label>子链三名称：</label>
                    <input name="linkNameIn3" type="text" maxlength="16"/><span>0/16</span></li>
                <li>
                    <label>子链三URL：</label>
                    <input name="linkURLIn3" type="text" maxlength="1024"/><span>0/1024</span>
                </li>
                <li>
                    <label>子链四名称：</label>
                    <input name="linkNameIn4" type="text" maxlength="16"/><span>0/16</span>
                </li>
                <li>
                    <label>子链四URL：</label>
                    <input name="linkURLIn4" type="text" maxlength="1024"/><span>0/1024</span>
                </li>
                <li>
                    <ul id="link5">
                        <li><label>子链五名称：</label>
                            <input name="linkNameIn5" type="text" maxlength="16"/><span>0/16</span></li>
                        <li> <label>子链五URL：</label>
                            <input name="linkURLIn5" type="text" maxlength="1024"/><span>0/1024</span></li>
                    </ul>
                </li>
                <li>
                    <label>是否启用:</label>
                    <select id="subPause">
                        <option value="true">启用</option>
                        <option value="false">暂停</option>
                    </select>
                </li>
            </ul>
        </div>

    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li onclick="addSubLinkOk();" class="current">确认</li>
                <li onclick="closeSubLinkDialog();">取消</li>
            </ul>
        </div>
    </div>
</div>
