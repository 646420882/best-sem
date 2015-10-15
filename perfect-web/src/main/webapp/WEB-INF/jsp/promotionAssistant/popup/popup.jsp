<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2015/9/29
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!---------下载账户----------->
<div class="TB_overlayBG"></div>
<div class="box" style="display:none;*width:400px;" id="download">
    <h2 id="CampaignChange"><span class="fl">账户下载</span><a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        您希望下载账户的哪个部分？
        <ul class="zs_set">
            <li><input type="radio" checked="checked" name="no1">&nbsp; 所有推广计划</li>
            <li><input type="radio" name="no1">&nbsp; 已下载的推广计划</li>
            <li><input type="radio" name="no1">&nbsp; 从最新的推广计划列表中选择</li>
        </ul>
        <div class="zs_sets over">
            <div id="allCampaign" class="zs_ses1" style="overflow: auto">
                <ul>
                </ul>
            </div>
            <div id="existsCampaign" class="zs_ses1 hides" style="overflow: auto">
                <ul>
                </ul>
            </div>
            <div id="newCampaign" class="zs_ses1 hides" style="overflow: auto">
                <ul>
                </ul>
            </div>
        </div>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li id="downloadAccount" class="current">确认</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>
<!---------上传更新----------->
<div class="TB_overlayBG"></div>
<div class="box" style="display:none;*width:400px;" id="uploadMerge">
    <h2 id="uploadHead"><span class="fl">上传更新</span><a href="javascript:void(0)" onclick="closeUploadDialog()"
                                                       class="close">关闭</a></h2>

    <div class="mainlist">
        您希望上传账户的哪个部分？
        <ul class="zs_set">
            <li><input type="radio" value="all" name="up1">&nbsp; 所有计划</li>
            <li><input type="radio" value="opreated" name="up1">&nbsp; 从推广计划列表中选择</li>
        </ul>
        <div class="zs_sets over">
            <div id="allCamp" class="zs_ses1" style="overflow: auto">
                <ul>
                </ul>
            </div>
            <div id="existsCamp" class="zs_ses1 hides" style="overflow: auto">
                <ul>
                </ul>
            </div>
        </div>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current" onclick="uploadDialogOk();">确认</li>
                <li class="close" onclick="closeUploadDialog()">取消</li>
            </ul>
        </div>
    </div>
</div>
<!---------查找重复关键词----------->
<div class="box3" style="display:none;*width:400px;">
    <h2 id="RepeartChange">
        <span class="fl">查找重复关键词</span><a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <span>请指定重复关键词的标准，已删除的关键词不会被当作重复关键词。</span>
        <ul class="zs_set">
            <li><h3>匹配模式</h3></li>
            <li><input type="radio" checked="checked" name="ms" class="ms">&nbsp; 重复的关键词须为相同的匹配模式
                <div class="zs_sets2 over hides">
                    <input type="checkbox" name="ms2">&nbsp; 所有短语模式视为湘潭
                </div>
            </li>
            <li></li>
            <li><input type="radio" checked="checked" name="ms">&nbsp; 重复的关键词可为不同的匹配模式</li>
        </ul>
        <ul class="zs_set">
            <li><h3>重复关键词位置</h3></li>
            <li><input type="radio" checked="checked" name="ms3">&nbsp; 在同一推广单元内</li>
            <li><input type="radio" name="ms3">&nbsp; 在同一推广计划内（所有推广单元）</li>
            <li><input type="radio" name="ms3">&nbsp; 整个账户内（所有推广计划）</li>
        </ul>
        <ul class="zs_set">
            <li><h3>不显示以下关键词</h3></li>
            <li><input type="checkbox" checked="checked" name="ms4">&nbsp; 已删除的推广计划和推广单元内的重复关键词</li>
            <li><input type="checkbox" name="ms4">&nbsp; 已暂停推广的推广计划和推广单元内的重复关键词</li>

        </ul>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current">确认</li>
                <li class="close ">取消</li>
            </ul>
        </div>
    </div>
</div>
<!---------估算工具----------->
<div class="box4" style="display:none;*width:520px;">
    <h2 id="GusuanChange">
        <span class="fl">估算工具</span><a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <span>关键词：<em>（每行一个，最多100个）</em></span>

        <div class="over wd">
            <div class="gusuan_bottom1 over fl ">
                <p>
                    <span class="fl"><em>匹配模式：广泛=关键词 短语=”关键词” 精准=[关键词]</em></span>
                    <span class="fr"><em>1/100</em></span>
                </p>
                <textarea class="zs_input5"></textarea>
            </div>
            <div class="gusuan_bottom2 fr over">
                <ul>
                    <li>每次点击最高出价：</li>
                    <li><select>
                        <option>5.00</option>
                    </select></li>
                    <li>推广地域：<em>全部地域</em></li>
                    <li><h3>注意：</h3>重复的关键词按第一次出现时使用的匹配模式进行估算。</li>
                </ul>
            </div>
        </div>
        <div class="gusuan_under over">
            <div class="gusuan_under1 over wd">
                <input type="button" value="估算" class="zs_input2 fl"><span class="gs_remind fl">将联网进行估算，请您保持网络畅通。</span>
            </div>
            <div class="gusuan_under2 over wd">
                <span class="gusuan_under3"></span>
                <table border="0" cellspacing="0" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <td>&nbsp;关键词名称</td>
                        <td>&nbsp;匹配模式</td>
                        <td>&nbsp;估算状态</td>
                        <td>&nbsp;估算排名区间</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>&nbsp;二手车买卖</td>
                        <td>&nbsp;广泛</td>
                        <td>&nbsp;有效</td>
                        <td>&nbsp;1-3</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <p><em>估算排名是关键词在所有可展现的推广中的排名（实际展现位置受质量度的影响）。</em></p>
        </div>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current">确认</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>
<!---------修改账户预算----------->
<div class="box5" style="display:none; width: 230px">
    <h2 id="budgetChange">
        <span class="fl">修改账户预算</span>
        <a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <ul class="zs_set">
            <li><input id="budget_text" type="text" style="width: 350px"/></li>
        </ul>
    </div>
    <div class="main_bottom" style="margin-left: 20%">
        <div class="w_list03">
            <ul>
                <li id="modifyAccountBudget_ok" class="current">确认</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>
<!---------IP排除----------->
<div class="box6" style="display:none;width: 300px">
    <h2 id="excludeIPChange">
        <span class="fl">IP排除</span>
        <a href="#" class="close">关闭</a></h2>


    <div class="mainlist">
        <ul class="zs_set">
            <li><textarea id="excludeIP_ta" style="width: 250px; overflow:auto; resize: none"></textarea></li>
        </ul>
    </div>
    <div class="main_bottom" style="margin-left: 12%">
        <div class="w_list03">
            <ul>
                <li id="excludeIP_ok" class="current">确认</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>
<!---------修改动态创意状态----------->
<div class="box7" style="display:none; width: 230px">
    <h2 id="changeCreative"><a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <div class="w_list03">
            <ul class="zs_set">
                <li id="dynamicCreative" class="current">确认</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>
<div id="reachBudget1" class="box" style="display:none; width: 600px">
    <h2 id="reachBudget_head">
        <span class="fl">账户预算</span>
        <a href="javascript:void(0)" class="close">关闭</a></h2>

    <div class="mainlist">
        <div class="tu_top over">
            <ul id="budgetList" class="zs_set">
            </ul>
        </div>
    </div>
</div>
<%--创意添加选择计划，单元弹出窗口--%>
<div class="box" style="display:none" id="jcAdd">
    <h2 id="dAdd">
        <span class="fl">添加创意</span>
        <a href="javascript:void(0)" onclick="closeAlertCreative()" class="close">关闭</a></h2>

    <div class="mainlist">
        选择要添加到的计划或者单元!
        <ul class="zs_set" id="jcUl">
            <%--<li><input type="radio" checked="checked" name="no1">&nbsp; 所有推广计划</li>--%>
        </ul>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li onclick="planUnit()">确认</li>
                <li onclick="closeAlertCreative();">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--推广计划设置否定关键词窗口--%>
<div class="box" style="display:none;" id="setNegtiveWord">
    <h2 id="setFdKeywordDiv">
        <span class="fl">否定关键词设置</span>
        <a href="javascript:void(0)" onclick="closeSetNegtiveWord();"
           style="color: #fff;float: right;font-size: 12px; line-height: 46px;">关闭</a></h2>

    <div class="mainlist">
        <p>以下设置仅对"广泛","短语"匹配的关键词生效，每行一词，每词20汉字以内，最多200项。</p>

        <div class="inputKwdDiv fl">
            <div><p>否定关键词<span id="ntwCount">(0/200)</span></p></div>
            <textarea id="ntwTextarea" onkeydown="ntwTextareaCount();" style="height: 300px;width: 350px;"></textarea>
        </div>

        <div class="inputKwdDiv fr">
            <div><p>精确否定关键词<span id="entwCount">(0/200)</span></p></div>
            <textarea id="entwTextarea" onkeydown="entwTextareaCount();" style="height: 300px;width: 350px;"></textarea>
        </div>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current ntwOk">确认</li>
                <li onclick="closeSetNegtiveWord()">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--创意修改弹出窗口--%>
<div class="box" style="display:none;width:500px;" id="jcUpdate">
    <h2 id="dUpdate">
        <span class="fl">修改创意</span>
        <a href="javascript:void(0)" onclick="closeAlertCreative();" class="close">关闭</a></h2>

    <div class="mainlist">
        <form id="cUpdateForm">
            <ul class="chuangyi">
                <li><label style="display: none;">---:<span>4/50</span></label><input name="oid" type="hidden"/></li>
                <li><label>创意标题:<span>49/50</span></label><input name="title" class="chuangyi_input" maxlength="50"/>
                    <input type="button" class="chuangyi_fu" value="插入{}符" onclick="addTb()"/></li>
                <li><label>创意描述1:<span>49/80</span></label><input name="description1" class="chuangyi_input"
                                                                  maxlength="80"/><input type="button"
                                                                                         class="chuangyi_fu"
                                                                                         value="插入{}符"
                                                                                         onclick="addTbDes1()"/></li>
                <li><label>创意描述2:<span>49/80</span></label><input name="description2" class="chuangyi_input"
                                                                  maxlength="80"/><input type="button"
                                                                                         class="chuangyi_fu"
                                                                                         value="插入{}符"
                                                                                         onclick="addTbDes2()"/></li>
                <li><label>默认访问URL:<span>49/1024</span></label><input name="pcDestinationUrl" maxlength="1024"/></li>
                <li><label>默认显示URL:<span>35/36</span></label><input name="pcDisplayUrl" maxlength="36"/></li>
                <li><label>移动访问URL:<span>49/1017</span></label><input name="mobileDestinationUrl" maxlength="1017"/>
                </li>
                <li><label>移动显示URL:<span>35/36</span></label><input name="mobileDisplayUrl" maxlength="36"/></li>
                <li><label class="fl"><span>创意状态:</span></label><label id="cuStatus" class="fl">暂无</label><input
                        type="hidden" name="status"></li>
                <li><label class="fl"><span>是否启用:</span></label><select name="pause">
                    <option value="true">启用</option>
                    <option value="false">暂停</option>
                </select></li>
                <li><label class="fl"><span>设备偏好:</span></label><select name="devicePreference">
                    <option value="0">全部</option>
                    <option value="1">移动设备优先</option>
                </select></li>
            </ul>
        </form>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current" onclick="updateOk();">确认</li>
                <li onclick="closeAlertCreative();">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--推广计划设置IP排除窗口--%>
<div class="box" style="display:none;" id="setExcludeIp">
    <h2 id="setExcludeIpDiv">
        <span class="fl">IP排除列表</span>
        <a href="javascript:void(0)" onclick="closeSetExcludeIp();"
           style="color: #fff;float: right;font-size: 12px; line-height: 46px;">关闭</a></h2>

    <div class="mainlist">
        <ul>
            <li>你可将IP最后一段设为*，以屏蔽一段地址内的创意展现。</li>
            <li>每个IP地址占一行。IP排除的数量不能超过20</li>
        </ul>
        <span id="IpListCount">IP排除(0/20)</span>

        <div class="inputIpDiv" style="margin-left:0px;">
            <textarea id="IpListTextarea" onkeydown="IpListTextareaCount();"
                      style="width:365px;height: 350px;"></textarea>
        </div>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current excludeIpOk">确认</li>
                <li onclick="closeSetExcludeIp()">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--推广计划设置推广时段窗口--%>
<div class="box" style="display:none;" id="setExtension">
    <h2 id="setExtensionDiv">
        <span class="fl">推广时段管理</span>
        <a href="javascript:void(0)" onclick="closeSetExtension();"
           style="color: #fff;float: right;font-size: 12px; line-height: 46px;">关闭</a></h2>

    <div class="chooseTime">
        <b class="fl">请选择时段&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(按住Ctrl键并且鼠标经过可多选时间段)</b>
        <ul class="fr">
            <li class="tfsjd"><span></span>&nbsp;&nbsp;&nbsp;投放时间段</li>
            <li class="ztsjd"><span></span>&nbsp;&nbsp;&nbsp;暂停时间段</li>
        </ul>
    </div>
    <br/>

    <div class="hours">
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current scheduleOk">确认</li>
                <li onclick="closeSetExtension();">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--添加监控文件夹弹出窗口--%>
<div class="box" style="display:none" id="addFolderDiv">
    <h2 id="folderTUO">
        <span class="fl">添加监控文件夹</span>
        <a href="javascript:void(0)" onclick="closeAlert();" class="close">关闭</a></h2>

    <div class="mainlist">
        <label id="dialogMsg">请输入要创建的监控文件夹名称!</label>
        <ul class="zs_set" id="adfd">
        </ul>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li id="addFolderQR">确认</li>
                <li onclick="closeAlert();">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--添加监控对象弹出窗口--%>
<div class="box" style="display:none" id="addMonitorDiv">
    <h2 id="MonitorTUO">
        <span class="fl">添加监控对象</span>
        <a href="javascript:void(0)" onclick="closeAlert();" class="close">关闭</a></h2>

    <div class="mainlist">
        <ul class="zs_set" id="admon">
        </ul>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li id="addMonitorQR">确认</li>
                <li onclick="closeAlert();">取消</li>
            </ul>
        </div>
    </div>
</div>
<%--检查完成--%>
<div class="box" style="display:none" id="CheckCompletion">
    <h2>
        <span class="fl">检查更改</span>
        <a href="javascript:void(0)" onclick="closeAlert();" class="close">关闭</a></h2>

    <div class="mainlist" style="width:400px;">
        <p>检查完成，请点击发布更改，将本地修改内容发布到线上</p>
        <!-- 进度条 -->
        <div style="height:38px; position:relative;margin:10px 0">
            <div class="barline" id="probar">
                <div id="percent"></div>
                <div id="line" w="100" style="width:0px;"></div>
                <%--<div id="msg" style=""></div>--%>
            </div>
        </div>

        <%--  <div class="progress" style="background:#61d677;height:38px;margin:10px 0">
          </div>--%>
        <div id="tabs">
            <ul>
                <li><a href="#tabs-1">可以发布的更改</a></li>
                <li><a href="#tabs-2">不会发布的更改</a></li>
            </ul>
            <div id="tabs-1">
                <table>
                    <tr>
                        <td>账户更改:</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>推广计划更改：</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>推广单元更改：</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>关键词更改：</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>创意更改：</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>附加创意更改：</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>排除对象更改：</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>监控文件夹更改：</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>监控关键词更改：</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>动态创意更改：</td>
                        <td>0</td>
                    </tr>
                </table>

            </div>
            <div id="tabs-2">
                <table>
                    <tr>
                        <td>账户更改:</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>推广计划更改：</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>推广单元更改：</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>关键词更改：</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>创意更改：</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>附加创意更改：</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>排除对象更改：</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>监控文件夹更改：</td>
                        <td>10</td>
                    </tr>
                    <tr>
                        <td>监控关键词更改：</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>动态创意更改：</td>
                        <td>0</td>
                    </tr>
                </table>
            </div>
        </div>
        <p>请您认真检查要发布的关键词、创意及附加信息，确保其不违法、侵权，且与您的网页信息相关</p>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li>发布更改</li>
                <li onclick="closeAlert();">关闭</li>
            </ul>
        </div>
    </div>
</div>