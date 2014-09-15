<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta id="viewport" name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>大数据智能营销</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/media.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/respond.js"></script>
</head>
<style type="text/css">
    .tab_box {
        padding: 10px 0 0 2px;
    }
</style>
<body>
<div id="background" class="background hides"></div>
<div id="progressBar" class="progressBar hides">数据加载中，请稍等...</div>
<jsp:include page="../homePage/pageBlock/head.jsp"/>
<div class="concent over">
<jsp:include page="../homePage/pageBlock/nav.jsp"/>
<div class="mid over">
<div class="on_title over">
    <a href="#">
        推广助手
    </a>
</div>
<div id="tab">
<ul class="tab_menu">
    <li class="selected">智能竞价</li>
    <li>重点词竞价</li>
</ul>
<div class="tab_box">
<div class="containers over">
    <div class=" jiangjia_concent over">
        <div class="jingjia_left fl over">
            <div class="j_l_top over">
                <span class="fl"><h3>账户目录</h3></span>
                <a href="#" class="fr">刷新</a>
            </div>
            <div class="j_l_top2 over">
                <span class="fl">查找计划单元</span>
                <input class="fr" type="image" src="${pageContext.request.contextPath}/public/img/search.png">
            </div>
            <div class="j_list01 over">
                <ul id="zTree" class="ztree over">

                </ul>
            </div>
            <div class="j_l_under over">
                <a href="#">监控文件夹</a>

            </div>
        </div>
        <div class="jingjia_right fr over">
            <div class="content_wraps over">
                <div class="wrap_list01 over wd ">
                    <ul>
                        <li>
                            <div class="w_list01 fl over">是否参与竞价：</div>
                            <div class="w_list02 fl over">
                                        <span>
                                            <form>
                                                <input type="radio" name="in" checked="checked" value="0">&nbsp;全部 &nbsp;
                                                <input type="radio" name="in" value="-1">&nbsp;未参与 &nbsp;
                                                <input type="radio" name="in" value="1"> &nbsp;已参加
                                            </form>
                                        </span>
                                        <span>
                                               <dl><input type="image"
                                                          src="${pageContext.request.contextPath}/public/img/search2.png">
                                               </dl>
                                               <dl><input type="checkbox" style=" margin-top:5px;" name="fullmatch"></dl>
                                                <dl>
                                                    <input type="text" class="w_text"
                                                           name="qtext"
                                                           value="关键词精准查询，多个关键词用半角逗号隔开"
                                                           onfocus="if(value=='关键词精准查询，多个关键词用半角逗号隔开') {value=''}"
                                                           onblur="if (value=='') {value='关键词精准查询，多个关键词用半角逗号隔开'}">
                                                    <input type="image" name="search"
                                                           src="${pageContext.request.contextPath}/public/img/search3.png">
                                                </dl>
                                          </span>
                                          <span>
                                              <input type="button" value="高级搜索" class="advanced_search">
                                        </span>
                            </div>
                        </li>
                    </ul>
                    <div class="Senior over hides">
                        <ul>
                            <li>
                                <div class="w_list01 fl over">账户余额：</div>
                                <div class="w_list02 fl over">
                                    <span><input type="checkbox" checked="checked">&nbsp;精准</span>
                                    <span><input type="checkbox" checked="checked">&nbsp;短语-核心</span>
                                    <span><input type="checkbox" checked="checked">&nbsp;短语-精准</span>
                                    <span><input type="checkbox" checked="checked">&nbsp;短语-同义</span>
                                    <span><input type="checkbox" checked="checked">&nbsp;广泛</span>
                                </div>
                            </li>
                            <li>
                                <div class="w_list01 fl over">质量度：</div>
                                <div class="w_list02 fl over">
                                    <ul>
                                        <li>

                                            <span><input type="checkbox" checked="checked">&nbsp;一星词</span>
                                            <span><input type="checkbox" checked="checked">&nbsp;二星词</span>
                                            <span><input type="checkbox" checked="checked">&nbsp;三星词</span>
                                            <span><input type="checkbox" checked="checked">&nbsp;四星词</span>
                                            <span><input type="checkbox" checked="checked">&nbsp;五星词</span>
                                        </li>
                                        <li>
                                            <span><input type="checkbox" checked="checked">&nbsp;六星词</span>
                                            <span><input type="checkbox" checked="checked">&nbsp;七星词</span>
                                            <span><input type="checkbox" checked="checked">&nbsp;八星词</span>
                                            <span><input type="checkbox" checked="checked">&nbsp;九星词</span>
                                            <span><input type="checkbox" checked="checked">&nbsp;十星词</span>
                                        </li>
                                    </ul>
                                </div>
                            </li>

                            <li>
                                <div class="w_list01 fl over">出价：</div>
                                <div class="w_list02 fl over">
                                               <span>
                                                   <input type="text" class="price"> - 　
                                                   <input type="text" class="price">
                                               </span></div>
                            </li>
                        </ul>

                    </div>
                </div>
                <div class="w_list03 ">
                    <ul class="jiangjia_list">
                        <li class="current" id="showbox">设置规则</li>
                        <li id="updateBtn">更新账户数据</li>
                        <li id="rankBtn">检查当前排名</li>
                        <li id="showbox2">修改出价</li>
                        <li id="showbox7">启动竞价</li>
                        <li id="showbox3">暂停竞价</li>
                        <li id="showbox4">修改访问网址</li>
                        <li id="showbox5">分组</li>
                        <li id="showbox6">自定义列</li>
                    </ul>
                    <div class="over wd">
                        <span class="fl">当前显示数据日期：昨天</span>
                    </div>
                    <div class="list4">
                        <table border="0" cellspacing="0" width="101%" id="table1">
                            <thead>
                            <tr class="list02_top">
                                <td>&nbsp;<input type="checkbox" id="checkAll"> 序号</td>
                                <td>&nbsp;关键词</td>
                                <td>&nbsp;消费</td>
                                <td>&nbsp;当前排名</td>
                                <td>&nbsp;展现量</td>
                                <td>&nbsp;点击率</td>
                                <td>&nbsp;出价</td>
                                <td>&nbsp;质量度</td>
                                <td>&nbsp;移动端质量度</td>
                                <td>&nbsp;状态</td>
                                <td>&nbsp;竞价规则</td>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                        <script type="application/javascript">

                            var rows = [];
                            for (i = 0; i < 10; i++) {
                                var row = "<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>" +
                                        "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>"
                                rows.push(row)
                            }
                            $("#table1 tbody").html(rows);
                            $("#table1 tbody tr:odd").addClass("list2_box1"),
                                    $("#table1 tbody tr:even").addClass("list2_box2")
                        </script>
                    </div>
                    <div>每页显示<select id="size">
                        <option value="20">20</option>
                        <option value="50">50</option>
                        <option value="100">100</option>
                    </select>
                    </div>
                    <div class="page2">
                        <a href="#" class="nextpage1"><span></span></a><a class="curpage" href="#">1</a><a
                            href="#">2</a><a href="#">3</a><a
                            href="#">4</a><a href="#">5</a><a href="#">6</a><a href="#" class="nextpage2"><span></span></a><span
                            style="margin-right:10px;">跳转到 <input type="text" class="price"></span>&nbsp;&nbsp;<a
                            href="#" class='page_go'> GO</a>

                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<div class="containers over hides">
    <div class=" jiangjia_concent over">
        <div class="jingjia_left fl over">
            <div class="j_l_top over">
                <span class="fl"><h3>账户目录</h3></span>
                <a href="#" class="fr">刷新</a>
            </div>
            <div class="j_l_top2 over">
                <span class="fl">查找计划单元</span>
                <input class="fr" type="image" src="${pageContext.request.contextPath}/public/img/search.png">
            </div>
            <div class="j_list01 over">
                <ul id="zTree2" class="ztree over">
                </ul>
            </div>
            <div class="j_l_under over">
                <a href="#">监控文件夹</a>
            </div>
        </div>
        <div class="jingjia_right fr over">
            <div>
                <div class="content_wraps over">
                    <div class="w_list03">
                        <ul class="jiangjia_list">
                            <li class="current Screenings">筛选</li>
                            <li class="showbox3">暂停竞价</li>
                            <li class="showbox2">修改出价</li>
                            <li class="showbox4">修改访问网址</li>
                            <li class="showbox5">分组</li>
                            <li class="showbox6">自定义列</li>
                        </ul>
                        <div class="Screening_concent over">
                            <div class="Screening_row over">
                            </div>
                            <div class="Screening over wd">
                                筛选设置：<span><input type="checkbox">&nbsp;<select>
                                <option>按计划</option>
                            </select>&nbsp;<input type="button" value="+" class="Screening_input"></span>
                                <span><input type="checkbox">&nbsp;<select>
                                    <option>按单元</option>
                                </select>&nbsp;<input type="button" value="+" class="Screening_input1"></span>
                                <span><input type="checkbox">&nbsp;<input type="text" class="sc_input3" value="如何在网上推广"
                                                                          onfocus="if(value=='如何在网上推广') {value=''}"
                                                                          onblur="if (value=='') {value='如何在网上推广'}"></span>

                            </div>
                        </div>

                        <div class="list4">
                            <table id="table2" border="0" cellspacing="0" width="101%">


                                <thead>
                                <tr class="list02_top">
                                    <td>&nbsp;<input type="checkbox" id="checkAll2"></td>
                                    <td>&nbsp; 序号</td>
                                    <td>&nbsp;关键词</td>
                                    <td>&nbsp;消费</td>
                                    <td>&nbsp;当前排名</td>
                                    <td>&nbsp;展现量</td>
                                    <td>&nbsp;点击率</td>
                                    <td>&nbsp;出价</td>
                                    <td>&nbsp;质量度</td>
                                    <td>&nbsp;移动端质量度</td>
                                    <td>&nbsp;状态</td>
                                    <td>&nbsp;竞价规则</td>
                                </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                            <script type="application/javascript">
                                var rows = [];
                                for (i = 0; i < 10; i++) {
                                    var row = "<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>" +
                                            "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>"
                                    rows.push(row)
                                }
                                $("#table2 tbody").html(rows);
                                $("#table2 tbody tr:odd").addClass("list2_box1"),
                                        $("#table2 tbody tr:even").addClass("list2_box2")
                            </script>
                        </div>
                        <div class="page2">
                            <a href="#" class="nextpage1"><span></span></a><a href="#">1</a><a href="#">2</a><a
                                href="#">3</a><a href="#">4</a><a href="#">5</a><a href="#">6</a><a href="#"
                                                                                                    class="nextpage2"><span></span></a><span
                                style="margin-right:10px;">跳转到 <input type="text" class="price"></span>&nbsp;&nbsp;<a
                                href="#" class='page_go'> GO</a>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
<jsp:include page="../homePage/pageBlock/footer.jsp"/>
</div>
</div>
<div class="TB_overlayBG"></div>
<div class="box10" style="display:none" id="downloadData">
    <h2 id="downloadBox">账户下载<a href="#" class="close">关闭</a></h2>

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


<div class="box" id="seetingRules" style="display:none">
    <h2 id="box1">设置规则<a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <ul>
            <li>
                <div>
                    <form name="biddingfrm" action="">
                        时段竞价：
                        <input type="radio" checked="checked" name="times" class="time_sl" value="1">&nbsp;单时段竞价 &nbsp;
                        <input type="radio" name="times" class="time_sl1" value="2"> &nbsp;多时段竞价 &nbsp;
                    </form>
                </div>
                <div class="time_select">
                    <select id="start">
                        <option>0点</option>
                    </select>&nbsp;至&nbsp;
                    <select id="end">
                        <option>24点</option>
                    </select>

                </div>
                <div class="time_select01 hides">
                    <ul>
                        <li><input name="mtimes" type="checkbox" data-id="1">
                            <select id="start1">
                                <option>0点</option>
                            </select>&nbsp;至&nbsp;<select id="end1">
                                <option>12点</option>
                            </select>&nbsp;上午
                        </li>
                        <li><input name="mtimes" type="checkbox" data-id="2"><select id="start2">
                            <option>12点</option>
                        </select>&nbsp;至&nbsp;<select id="end2">
                            <option>14点</option>
                        </select>&nbsp;中午
                        </li>
                        <li><input name="mtimes" type="checkbox" data-id="3"><select id="start3">
                            <option>14点</option>
                        </select>&nbsp;至&nbsp;<select id="end3">
                            <option>24点</option>
                        </select>&nbsp;下午
                        </li>
                    </ul>

                </div>
            </li>
            <li>
                <form>竞价模式：<input type="radio" checked="checked" name="mode" value="102">&nbsp;经济 &nbsp;<input
                        type="radio"
                        name="mode" value="101"> &nbsp;快速
                    &nbsp;</form>
            </li>
            <%--<li>--%>
            <%--<form>竞价规则：--%>
            <%--<select id="device">--%>
            <%--<option value="1">计算机</option>--%>
            <%--</select>--%>
            <%--</form>--%>
            <%--</li>--%>
            <li>
                <ul>
                    <li>
                        竞价位置：
                        <select id="pos">
                            <option class="right_define1" value="1">左侧:1位</option>
                            <option class="right_define1" value="2">左侧:2-3位</option>
                            <option class="right_define1" value="3">右侧:1-3位</option>
                            <option class="right_define" value="4">右侧自定义</option>
                        </select>

                        <div class="right_stes over">
                            <span class="right_sets1 hides over">右侧：<input name="rightpos" type="text" class="price2"> 位 </span>
                            <span>最高出价（最低区间0.01）<input type="text" id="max" class="price2" value="0.01"></span>
                            <span>最低出价（最低区间0.01）<input type="text" id="min" class="price2" value="0.01"></span>
                        </div>
                    </li>
                </ul>
                </form>
            </li>
            <li>
                <span class="fl">当出价达不到排名时  </span>

                <form class="fl" style=" margin-left:5px;">
                    <input name="failed" type="radio" checked value="1"> &nbsp;自动匹配最佳排名
                    &nbsp;<input type="radio" name="failed" value="2"> &nbsp;恢复账户设置 &nbsp;
                </form>
            </li>
            <li>
                <p>自动竞价模式：</p>
                <ul>
                    <li><input type="radio" name="auto" checked="checked" value="1"><span>单次竞价</span>
                        <br>
                        &nbsp; <input type="radio" name="sbid" checked="checked" value="everyday"> 每天执行
                        &nbsp;<input type="radio" name="sbid" value="bytime"> 竞价次数:<input class="times" type="text"
                                                                                          name="bytimes">
                    </li>

                    <li><input type="radio" name="auto" value="2"><span>重复竞价速度 每隔
                        <select id="interval">
                            <option value="20">20分钟</option>
                            <option value="30">半小时</option>
                            <option value="60">1小时</option>
                            <option value="120">2小时</option>
                        </select> 竞价一次</span>
                    </li>

                </ul>

            </li>
        </ul>
    </div>


    <form name="hidden" method="POST" action="save">
        <input type="hidden" name="pos">
    </form>

    <div class="main_bottom">
        <div class="w_list03">

            <ul>
                <li class="current" id="rulesave">保存</li>
                <li id="rulesaverun">保存并运行</li>
                <li class="close">取消</li>

            </ul>
        </div>
    </div>
</div>
<!-------修改出价------------>
<div class="box2" style="display:none">
    <h2 id="box2">修改出价<a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <ul>
            <li><input type="radio" name="price1" checked="checked"><span
                    class="mainlist_left"> 输入新问价 </span><span><input class="zs_input3" type="text"></span></li>
            <li><input type="radio" name="price1" checked="checked"><span class="mainlist_left"> 使用单元出价 </span></li>
        </ul>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current">保存</li>
                <li class="close">取消</li>

            </ul>
        </div>
    </div>
</div>
<!---------修改匹配模式----------->
<div class="box3" style="display:none">
    <h2 id="box3">修改匹配模式<a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <ul>
            <li><input type="radio" name="price2" checked="checked"><span class="mainlist_left"> 广泛 </span></li>
            <li><input type="radio" name="price2" checked="checked"><span class="mainlist_left">精准</span></li>
            <li>
                <div class="fl"><input type="radio" name="price2" checked="checked" class="short"><span
                        class="mainlist_left">短语 </span></div>
                <form class="shorts hides fl ">
                    <input type="radio" name="price3" checked="checked"><span class="mainlist_left">核心包含</span>
                    <input type="radio" name="price3" checked="checked"><span class="mainlist_left">同义包含</span>
                    <input type="radio" name="price3" checked="checked"><span class="mainlist_left">精准包含</span>
                </form>
            </li>
        </ul>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current">保存</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>

<!---------修改访问网址----------->
<div class="box4" style="display:none">
    <h2 id="box4">修改访问网址<a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <ul>
            <li>输入指向网址：</li>
            <li>http://<input class="zs_input3" type="text"></li>
        </ul>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current">保存</li>
                <li class="close">取消</li>

            </ul>
        </div>
    </div>
</div>

<!---------分组管理----------->
<div class="box5" style="display:none">
    <h2 id="box5">分组管理<a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <div class="gusuan_bottom1 over fl ">
            <ul>
                <li>关键词列表：</li>
                <li><textarea class="zs_input5"></textarea></li>
                <li>输入分组管理名：</li>
                <li><input type="text" class="zs_input3"></li>
            </ul>
        </div>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current">保存</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>
<!---------自定义列----------->
<div class="box6" style="display:none">
    <h2 id="box6">自定义列<a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <ul>
            <li><span class="define fl"><input type="checkbox"> 推广单元</span><span class="define fl"><input
                    type="checkbox"> 匹配模式</span></li>
            <li><span class="define fl"><input type="checkbox"> 推广计划</span><span class="define fl"><input
                    type="checkbox"> 展现量</span></li>
            <li><span class="define fl"><input type="checkbox"> 状态</span><span class="define fl"><input type="checkbox"> 点击率</span>
            </li>
            <li><span class="define fl"><input type="checkbox"> 出价</span><span class="define fl"><input type="checkbox">平均点击价格 </span>
            </li>
            <li><span class="define fl"><input type="checkbox"> 质量度</span><span class="define fl"><input
                    type="checkbox">平均排名 </span></li>
            <li><span class="define fl"><input type="checkbox"> 点击量</span><span class="define fl"><input
                    type="checkbox"> 当前排名</span></li>
            <li><span class="define fl"><input type="checkbox"> 消费</span><span class="define fl"><input type="checkbox"> 访问URL</span>
            </li>
            <li><span class="define fl"><input type="checkbox"> 竞价规则</span><span class="define fl"><input
                    type="checkbox"> </span></li>
        </ul>

    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current">保存</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/tc.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jiangjia.js"></script>

<SCRIPT type="text/javascript">

<!--

/******************zTree********************/

var setting = {
    view: {
        showLine: false,
        selectedMulti: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeClick: beforeClick,
        beforeAsync: beforeAsync,
        onAsyncError: onAsyncError,
        onAsyncSuccess: onAsyncSuccess
    }
};
var zNodes = "";

function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    for (var i = 0, l = childNodes.length; i < l; i++) {
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
    }
    return childNodes;
}
function beforeClick(treeId, treeNode) {
    var tbl = "table1";
    if (treeId == 'zTree2') {
        tbl = "table2";
    }

    if (treeNode.level == 0) {
        //点击的是父节点(推广计划),则应该展示其下属的推广单元数据

//            alert(treeNode.id + "," + treeNode.name);
        campaignId = treeNode.id + "," + "0";
        //事件处理
        var curPage = $('.curpage').text();
        var size = $("#size").find("option:selected").val();
        var skip = (curPage - 1) * size;

        $.ajax({
            url: "/bidding/list?cp=" + treeNode.id + "&s=" + skip + "&l=" + size,
            type: "GET",
            dataType: "json",
//            async: false,
            success: function (datas) {
                if (datas.rows.length == 0) {
                    emptyTable(tbl);
                } else {
                    fullItems(datas, tbl);
                }
            }
        })
    } else if (treeNode.level == 1) {
        //点击的是子节点(推广单元),则应该展示其下属的关键词数据
//            alert(treeNode.id + "," + treeNode.name);
        adgroupId = treeNode.id + "," + "1";
        //事件处理
        $.ajax({
            url: "/bidding/list?ag=" + treeNode.id,
            type: "GET",
            dataType: "json",
//            async: false,
            success: function (datas) {
                if (datas.rows.length == 0) {
                    emptyTable(tbl);
                } else {
                    fullItems(datas, tbl);
                }
            }
        })
    }

}
var log, className = "dark";
function beforeAsync(treeId, treeNode) {
    className = (className === "dark" ? "" : "dark");
    showLog("[ " + getTime() + " beforeAsync ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
    return true;
}
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
    showLog("[ " + getTime() + " onAsyncError ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
}
function onAsyncSuccess(event, treeId, treeNode, msg) {
    showLog("[ " + getTime() + " onAsyncSuccess ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root"));
}

function showLog(str) {
    if (!log) log = $("#log");
    log.append("<li class='" + className + "'>" + str + "</li>");
    if (log.children("li").length > 8) {
        log.get(0).removeChild(log.children("li")[0]);
    }
}
function getTime() {
    var now = new Date(),
            h = now.getHours(),
            m = now.getMinutes(),
            s = now.getSeconds(),
            ms = now.getMilliseconds();
    return (h + ":" + m + ":" + s + " " + ms);
}
//================================================


$(function () {
    //获取账户树数据
    $.ajax({
        url: "/account/get_tree",
        type: "GET",
        dataType: "json",
        async: false,
        success: function (data, textStatus, jqXHR) {
            zNodes = data.trees;
        }
    });
    //加载账户树
    $.fn.zTree.init($("#zTree"), setting, zNodes);
    $.fn.zTree.init($("#zTree2"), setting, zNodes);
});

function loadReady() {
    var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
            htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
            maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
            h = demoIframe.height() >= maxH ? minH : maxH;
    if (h < 1000) h = 1000;
    demoIframe.height(h);
}

function checkrank() {
    var boxes = $("input[name='subbox'][checked]")
    var ids = [];
    $(boxes).each(function () {
        ids.push($(this).val())
    })

}

$('.addRuleBtn').click(function () {
    $.kwid = $(this).data('id');

    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    $(".box").css({
        left: ($("body").width() - $(".box").width()) / 2 - 20 + "px",
        top: ($(window).height() - $(".box").height()) / 2 + $(window).scrollTop() + "px",
        display: "block"
    });
});


$('.getRankBtn').click(function () {
    var id = $(this).data('id');
    $.ajax({
        url: "/bidding/rank/" + id,
        type: "GET",
        success: function (datas) {
            var data = datas.rows;
            if (data == null) {
                alert("暂无排名信息,请刷新排名!");
                return false;
            }
            var msg = "当前排名获取时间: " + data.time + "\n";
            msg = msg + "关键词: " + data.name + "\n";
            msg = msg + "地域\t排名\n";
            if (data.length == 0) {
                msg = msg + "暂无排名";
            } else {
                data.forEach(function (i, item) {
                    msg = msg + item.region + "\t" + item.rank + "\n";
                })
            }
            alert(msg);
        }
    })
});
//-->
//loading
var ajaxbg = $("#background,#progressBar");
    ajaxbg.hide();
$(document).ajaxStart(function () {
    ajaxbg.show();
})
$(document).ajaxStop(function () {
    ajaxbg.fadeOut(1500);
        });
</SCRIPT>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/html.js"></script>


<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.livequery.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/assistant/updateAccountData.js"></script>


</body>
</html>
