<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<script type="text/javascript" src="http://ucapi.best-ad.cn/content/js/optLevel.js"></script>--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/log/Zradio.js"></script>--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/log/radioParams.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/log/queryCovert.js"></script>
<div id="LogPage" style="display:none">
    <div class="historydiv">
        <div class="history-sel-box">
            <!-- 选择 -->
            <div class="radioselect-box" id="radioselect-box"></div>
            <div class="line"></div>
            <div class="object">
                选定的对象列表：
                <span class="num">( <span id="obj-num">0</span>个 )</span>
            </div>
            <div class="obj-box">
                <div class="obj-select">
                    <div class="obj-select-list">
                        <!--   <div class="obj-list" id="obj-list">
                            <a href="#">baidu-perfect2151880</a>
                            <div class="obj-list-del"></div>
                          </div> -->
                    </div>
                    <div class="add-obj">
                        <a id="addobj-button" onclick="lq.addObj()">+添加对象</a>
                    </div>
                </div>
                <div class="select-time-box">
                    <p>选择时间范围：</p>

                    <div class="container date-box">
                        <form class="form-horizontal">
                            <fieldset>
                                <div class="control-group">
                                    <div class="controls">
                                        <div class="input-prepend input-group">
                                            <input type="text" readonly="readonly" style="width: 200px"
                                                   name="reservation"
                                                   id="reservation" class="form-control"/>
                                        <span class="add-on input-group-addon"><i
                                                class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                        </form>


                    </div>
                    <p style="margin-top:15px;">选择操作内容：</p>

                    <div class="ctrlbuttonoptSelect" onclick="lq.showOperation()"></div>
                </div>
                <!-- 计划列表 -->
                <div class="plan-box">
                    <div class="title"><span id="boxheader">计划列表</span><a href="javascript:;" class="close-ico"></a>
                    </div>
                    <div class="serach">
                        <ul>
                            <li><input type="text" id="txtkey"/></li>
                            <li>
                                <button id="btnsearch">查询</button>
                            </li>
                        </ul>
                    </div>

                    <div class="query-result" id="query-result">
                        <ul>
                            <li><a href="javascript:;">添加</a><span>test</span></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="download-box">
                <button onclick="lq.logQuery()" class="query-btn">查询</button>
                <input type="hidden" id="start_time"/>
                <input type="hidden" id="end_time"/>
                <a href="#" style="display: none;" target="_blank" id="downloadhistory" class="download">↓下载历史操作记录</a>
            </div>
        </div>
    </div>
    <div class="history-list-box" id="tableTry">

        <table cellspacing="0" id="tabledata" width="100%">
            <thead>
            <tr>
                <th width="300px">时间</th>
                <th width="300px">操作人</th>
                <th width="300px">层级</th>
                <th width="300px">层级名称</th>
                <th width="300px">操作内容</th>
            </tr>
            </thead>
            <tbody id="tbodydata">
            </tbody>
        </table>
    </div>
    <div>
        <div>
            <label>每页显示:</label>
            <select id="pageSizeSelected">
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
            </select></div>
        <div id="logPagination" class="pagination"></div>

    </div>
</div>
<!-- 提示弹出框 -->
<div class="black tip">
    <div class="tip-box">
        <h2>提醒 <a href="javascript:;" class="close-tip" onclick="lq.cancelCheck(this)"></a></h2>

        <p>您选择其他层级后，会清空您当前的选定对象列表，确认清空当前列表吗？</p>

        <div class="operation">
            <a href="javascript:;" class="cancel" onclick="lq.cancelCheck(this)">取消</a>
            <a href="javascript:;" class="submit" onclick="lq.cancelCheck(this)">确定</a>
        </div>
    </div>
</div>
<!-- 操作弹出框 -->
<div class="black operation-tip">
    <div class="tip-box">
        <h2>选择操作内容 <a href="javascript:;" class="close-tip" onclick="lq.hideOperation()"></a></h2>

        <div class="check-all">
            <a href="javascript:;" class="check-now" id="all-check">全部</a>|<a href="javascript:;"
                                                                              id="user-defined">自定义</a>
        </div>
        <div class="operation-tip-list" id="operation-tip-list">

        </div>
        <div class="operation">
            <a href="javascript:;" class="cancel" onclick="lq.hideOperation()">取消</a>
            <a href="javascript:;" class="submit" onclick="lq.hideOperation()">确定</a>
        </div>

    </div>
</div>