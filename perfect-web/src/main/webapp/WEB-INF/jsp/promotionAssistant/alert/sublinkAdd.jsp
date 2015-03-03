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

    .list2_box2 {
        background: none repeat scroll 0 0 #ecf3f9;
    }
</style>
<%--添加附加创意子链弹出框--%>
<div class="box" style="display:none" id="addSublinkDiv">
    <h2 id="SublinkDiv">
        <span class="fl">添加蹊径子链</span>
        <a href="javascript:void(0)" onclick="closeSubLinkDialog();" class="close">关闭</a></h2>

    <div class="mainlist">
        <div id="sublinkAddInput">
            <span>您的域名为：</span><span style="color:red;" class="doMainS"></span>
            <span>子链名称总字节数：</span><span id="maxByte">0</span>
            <label>投放设备</label>
            <select id="subMtype" onchange="mobileTypeAdd();">
                <option value="0">计算机</option>
                <option value="1">移动设备</option>
            </select>
            <select id="subCampaign" onchange="initSelectAdgroup()"></select>
            <select id="subAdgroup"></select>
            <label>子链一名称：</label>
            <input name="linkName1" type="text" maxlength="16"/><span>0/16</span>
            <label>子链一URL：</label>
            <input name="linkURL1" type="text" maxlength="1024"/><span>0/1024</span>
            <label>子链二名称：</label>
            <input name="linkName2" type="text" maxlength="16"/><span>0/16</span>
            <label>子链二URL：</label>
            <input name="linkURL2" type="text" maxlength="1024"/><span>0/1024</span>
            <label>子链三名称：</label>
            <input name="linkName3" type="text" maxlength="16"/><span>0/16</span>
            <label>子链三URL：</label>
            <input name="linkURL3" type="text" maxlength="1024"/><span>0/1024</span>
            <label>子链四名称：</label>
            <input name="linkName4" type="text" maxlength="16"/><span>0/16</span>
            <label>子链四URL：</label>
            <input name="linkURL4" type="text" maxlength="1024"/><span>0/1024</span>
            <div id="link5"><label>子链五名称：</label>
                <input name="linkName5" type="text" maxlength="16"/><span>0/16</span>
                <label>子链五URL：</label>
                <input name="linkURL5" type="text" maxlength="1024"/><span>0/1024</span>
            </div>
            <label>是否启用:</label>
            <select id="subPause">
                <option value="true">启用</option>
                <option value="false">暂停</option>
            </select>
        </div>

    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li onclick="addSubLinkOk();">确认</li>
                <li onclick="closeSubLinkDialog();">取消</li>
            </ul>
        </div>
    </div>
</div>
