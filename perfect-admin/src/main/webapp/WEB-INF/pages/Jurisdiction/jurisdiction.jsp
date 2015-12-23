<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    <title>百思-用户管理中心</title>
    <jsp:include page="../public/navujs.jsp"/>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="../public/header.jsp"/>
    <div class="containers">
        <jsp:include page="../public/nav.jsp"/>
        <div class="middle_containers">
            <div class="page_title">
                当前位置：模块权限
            </div>
            <div class="user_box"
                 style="width: 100%;height: 100%;position: fixed;background: #f6f4f5;z-index: -1"></div>
            <div class="user_box" id="adminModule">
                <div class="adminModuleWrap">
                    <div class="fl moduleSearchLabel"><label>用户名：</label></div>
                    <div class="fl moduleSearch">
                        <div style="position: relative">
                            <input type="text" placeholder="perfect2015" class="form-control">
                            <span>
                                <span class="glyphicon glyphicon-search"></span>
                            </span>
                        </div>
                    </div>
                </div>
                <div style="clear: both"></div>
                <div class="adminModuleWrap">
                    <div class="fl moduleSearchLabel"><label>操作模块：</label></div>
                    <div class="fl moduleSearch">
                        <div>
                            <select onchange="transformModule(this)">
                                <option value="1">百思慧眼</option>
                                <option value="2">百思搜客</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div style="clear: both"></div>
                <div class="adminModuleWrap">
                    <div class="fl moduleSearchLabel"></div>
                    <div class="fl">
                        <div>
                            给百思账号设置<span id="moduleName">慧眼</span>使用权限
                        </div>
                    </div>
                </div>
                <div style="clear: both"></div>
                <div class="adminModuleWrap" id="hyJurisdiction">
                    <div class="fl moduleSearchLabel"></div>
                    <div class="fl" id="adminModuleReportWrap">
                        <div class="adminModuleAll" id="adminModuleAll" style="border-bottom: 1px solid #f6f4f5">
                            <label style="margin-left: 15px;"><input style="margin-right: 8px" type="checkbox"
                                                                     onchange="checkboxCheck(this,1,'adminModuleReportWrap')">全选/全不选</label>
                        </div>
                        <div class="adminModuleReport" id="adminModuleReport">
                            <div style="padding-left: 40px"><label><input type="checkbox"
                                                                          onchange="checkboxCheck(this,2,'adminModuleReport')">报告模块</label>
                            </div>
                            <div style="padding-left: 40px">
                                <div class="fl modalTree">
                                    <div><label><input type="checkbox" onchange="checkboxCheck(this,3,'')">趋势分析</label>
                                    </div>
                                    <div class="secondClass"><img
                                            src="${pageContext.request.contextPath}/public/img/modalTreePic.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">实时访客</label></div>
                                    <div class="secondClass"><img
                                            src="${pageContext.request.contextPath}/public/img/modalTreePic.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">今日统计</label></div>
                                    <div class="lastClass"><img
                                            src="${pageContext.request.contextPath}/public/img/lastModalTree.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">外部链接</label></div>
                                </div>
                                <div class="fl modalTree">
                                    <div><label><input type="checkbox" onchange="checkboxCheck(this,3,'')">来源分析</label>
                                    </div>
                                    <div class="secondClass"><img
                                            src="${pageContext.request.contextPath}/public/img/modalTreePic.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">全部来源</label></div>
                                    <div class="secondClass"><img
                                            src="${pageContext.request.contextPath}/public/img/modalTreePic.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">搜索引擎</label></div>
                                    <div class="secondClass"><img
                                            src="${pageContext.request.contextPath}/public/img/modalTreePic.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">搜索词</label></div>
                                    <div class="lastClass"><img
                                            src="${pageContext.request.contextPath}/public/img/lastModalTree.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">外部链接</label></div>
                                </div>
                                <div class="fl modalTree">
                                    <div><label><input type="checkbox" onchange="checkboxCheck(this,3,'')">页面分析</label>
                                    </div>
                                    <div class="secondClass"><img
                                            src="${pageContext.request.contextPath}/public/img/modalTreePic.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">受访页面</label></div>
                                    <div class="secondClass"><img
                                            src="${pageContext.request.contextPath}/public/img/modalTreePic.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">入口页面</label></div>
                                    <div class="lastClass"><img
                                            src="${pageContext.request.contextPath}/public/img/lastModalTree.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">页面热点图</label></div>
                                </div>
                                <div class="fl modalTree">
                                    <div><label><input type="checkbox" onchange="checkboxCheck(this,3,'')">访客分析</label>
                                    </div>
                                    <div class="secondClass"><img
                                            src="${pageContext.request.contextPath}/public/img/modalTreePic.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">访客地图</label></div>
                                    <div class="secondClass"><img
                                            src="${pageContext.request.contextPath}/public/img/modalTreePic.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">设置环境</label></div>
                                    <div class="lastClass"><img
                                            src="${pageContext.request.contextPath}/public/img/lastModalTree.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">新老访客</label></div>
                                </div>
                                <div class="fl modalTree">
                                    <div><label><input type="checkbox" onchange="checkboxCheck(this,3,'')">价值透析</label>
                                    </div>
                                    <div class="secondClass"><img
                                            src="${pageContext.request.contextPath}/public/img/modalTreePic.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">流量地图</label></div>
                                    <div class="lastClass"><img
                                            src="${pageContext.request.contextPath}/public/img/lastModalTree.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">频道流转</label></div>
                                </div>
                                <div class="fl modalTree">
                                    <div><label><input type="checkbox" onchange="checkboxCheck(this,3,'')">转化分析</label>
                                    </div>
                                    <div class="secondClass"><img
                                            src="${pageContext.request.contextPath}/public/img/modalTreePic.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">事件转化</label></div>
                                    <div class="lastClass"><img
                                            src="${pageContext.request.contextPath}/public/img/lastModalTree.png"><label><input
                                            type="checkbox" onchange="checkboxCheck(this,5,'')">页面转化</label></div>
                                </div>
                            </div>
                        </div>
                        <div style="clear: both"></div>
                        <div class="adminModuleReport" id="adminModuleWeb" style="border-top: 1px solid #f6f4f5">
                            <div style="padding-left: 40px"><label><input type="checkbox"
                                                                          onchange="checkboxCheck(this,2,'adminModuleWeb')">网站设置</label>
                            </div>
                            <div style="padding-left: 40px">
                                <div class="fl modalTree webSetWrap"><label><input type="checkbox"
                                                                                   onchange="checkboxCheck(this,4,'adminModuleWeb')">代码安装检查</label>
                                </div>
                                <div class="fl modalTree webSetWrap"><label><input type="checkbox"
                                                                                   onchange="checkboxCheck(this,4,'adminModuleWeb')">设置统计图表</label>
                                </div>
                                <div class="fl modalTree webSetWrap"><label><input type="checkbox"
                                                                                   onchange="checkboxCheck(this,4,'adminModuleWeb')">设置子目录</label>
                                </div>
                                <div class="fl modalTree webSetWrap"><label><input type="checkbox"
                                                                                   onchange="checkboxCheck(this,4,'adminModuleWeb')">设置统计规则</label>
                                </div>
                                <div class="fl modalTree webSetWrap"><label><input type="checkbox"
                                                                                   onchange="checkboxCheck(this,4,'adminModuleWeb')">设置页面转化</label>
                                </div>
                                <div class="fl modalTree webSetWrap"><label><input type="checkbox"
                                                                                   onchange="checkboxCheck(this,4,'adminModuleWeb')">设置事件转化目标</label>
                                </div>
                                <div class="fl modalTree webSetWrap"><label><input type="checkbox"
                                                                                   onchange="checkboxCheck(this,4,'adminModuleWeb')">代码获取</label>
                                </div>
                                <div class="fl modalTree webSetWrap"><label><input type="checkbox"
                                                                                   onchange="checkboxCheck(this,4,'adminModuleWeb')">设置指定广告耿总</label>
                                </div>
                                <div class="fl modalTree webSetWrap"><label><input type="checkbox"
                                                                                   onchange="checkboxCheck(this,4,'adminModuleWeb')">设置统计图标</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="adminModuleWrap" id="skJurisdiction" style="display: none">
                    <div class="fl moduleSearchLabel"></div>
                    <div class="fl" style="background: #fff;width: 88%;padding-bottom: 10px;">
                        <div class="adminModuleAll" style="border-bottom: 1px solid #f6f4f5">
                            <label style="margin-left: 15px;"><input style="margin-right: 8px" type="checkbox"
                                                                     onchange="skCheckAll(this,1)">全选/全不选</label>
                        </div>
                        <div class="adminModuleReport" style="border-top: 1px solid #f6f4f5">
                            <div style="padding: 15px 0 0 10px">
                                <div class="fl modalTree webSetWrap"><label><input type="checkbox"
                                                                                   onchange="skCheckAll(this,2)">账户全景</label>
                                </div>
                                <div class="fl modalTree webSetWrap"><label><input type="checkbox"
                                                                                   onchange="skCheckAll(this,2)">推广助手</label>
                                </div>
                                <div class="fl modalTree webSetWrap"><label><input type="checkbox"
                                                                                   onchange="skCheckAll(this,2)">智能结构</label>
                                </div>
                                <div class="fl modalTree webSetWrap"><label><input type="checkbox"
                                                                                   onchange="skCheckAll(this,2)">智能竞价</label>
                                </div>
                                <div class="fl modalTree webSetWrap"><label><input type="checkbox"
                                                                                   onchange="skCheckAll(this,2)">数据报告</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="clear: both"></div>
                <div class="adminModuleWrap">
                    <div class="fl moduleSearchLabel"></div>
                    <div class="fl">
                        <div class="setJurisdictionBtn">
                            <input type="button" class="btn current" value="确定">
                            <input type="button" class="btn" value="取消">
                        </div>
                    </div>
                </div>
                <div style="clear: both"></div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/public/js/jurisdiction/jurisdiction.js"></script>
</body>
</html>

