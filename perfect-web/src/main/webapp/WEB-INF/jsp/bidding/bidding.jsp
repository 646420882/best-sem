<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    String strUp = "高级搜索∧";
    String strDown = "高级搜索∨";
%>
<!doctype html>
<html>
<head>
    <title>大数据智能营销</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/grid/ui.jqgrid.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/media.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/css/pagination/pagination.css">
    <script type="text/javascript" src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <style type="text/css">
        .tab_box {
            padding: 10px 0 0px 0;
        }

        .ui-jqgrid tr.jqgrow td {
            /* jqGrid cell content wrap  */
            white-space: normal !important;
            height: 30px;
            line-height: inherit;
        }

    </style>
</head>
<body>
<div id="background" class="background hides"></div>
<div id="progressBar" class="progressBar hides"><span></span>数据加载中，请稍等...</div>
<jsp:include page="../homePage/pageBlock/head.jsp"/>
<div class="concent over">
    <jsp:include page="../homePage/pageBlock/nav.jsp"/>
    <div id="main" class="mid fr over">
        <div class="title_box">
            <div class="on_title over">
                <a href="#"> 智能竞价 </a>
                <span id="TitleName"></span>
            </div>
        </div>
        <div id="tab">
            <ul class="tab_menu">
                <li class="selected">智能竞价</li>
                <li>重点词竞价</li>
            </ul>
            <div class="tab_box bidding_box">
                <div class="containers over">
                    <div class=" jiangjia_concent over">
                        <div class="jingjia_right fl over">
                            <div class="zhushou_concent zhushou over">
                                <div class="wrap_list01 over wd4 ">
                                    <ul>
                                        <li>
                                            <div class="w_list01 fl over">是否参与竞价：</div>
                                            <div class="w_list02 fl over">
                                        <span>
                                            <form class="aline">
                                                <div class="input_box"><label class='checkbox-inlines'><input
                                                        type="radio" name="in" checked="checked"
                                                        value="0">&nbsp;全部</label>&nbsp;</div>
                                                <div class="input_box"><label class='checkbox-inlines'><input
                                                        type="radio" name="in" value="-1">&nbsp;未参与</label>
                                                    &nbsp;</div>
                                                <div class="input_box"><label class='checkbox-inlines'><input
                                                        type="radio" name="in" value="1"> &nbsp;已参加</label>&nbsp;
                                                </div>
                                            </form>
                                        </span>
                                        <span>
                                               <dl class="aline">
                                                   <label class='checkbox-inlines'>
                                                       <input type="checkbox" style=" margin-top:5px;">精确搜索</label>
                                               </dl>
                                                <dl>
                                                    <input type="text" class="w_text"
                                                           name="qtext"
                                                           value="关键词精准查询，多个关键词用半角逗号隔开"
                                                           onfocus="if(value=='关键词精准查询，多个关键词用半角逗号隔开') {value=''}"
                                                           onblur="if (value=='') {value='关键词精准查询，多个关键词用半角逗号隔开'}">
                                                    <input style="height: 23px;" type="image" name="search"
                                                           src="${pageContext.request.contextPath}/public/img/search3.png">
                                                </dl>
                                          </span>
                                          <span>
                                              <input type="button" value="高级搜索∨" class="advanced_search">

                                        </span>
                                            </div>
                                        </li>
                                    </ul>
                                    <div id="advancedSearch" class="Senior over hides">
                                        <ul>
                                            <li>
                                                <div class="w_list01 fl over" id="advanceMathModel"><span class="fl">匹配模式：</span>
                                                    <label class='checkbox-inlines'>
                                                        <input style=" float: left"  type="checkbox" id="Matching" onchange="customColumns(this,0,'advanceMathModel','matchType')" name="keywordQuality" value="1">&nbsp;
                                                    全选</label>
                                                </div>
                                                <div class="w_list02 fl over aline" id="matchType">
                                                    <div class="input_box"><span><label class='checkbox-inlines'><input
                                                            type="checkbox" name="matchType" onchange="customColumns(this,1,'advanceMathModel','matchType')"
                                                            checked="checked" value="1">&nbsp;精准</label></span>
                                                    </div>
                                                    <div class="input_box"><span><label class='checkbox-inlines'><input
                                                            type="checkbox" name="matchType" onchange="customColumns(this,1,'advanceMathModel','matchType')"
                                                            value="2">&nbsp;短语-核心</label></span>
                                                    </div>
                                                    <div class="input_box"><span><label class='checkbox-inlines'><input
                                                            type="checkbox" name="matchType" onchange="customColumns(this,1,'advanceMathModel','matchType')"
                                                            value="3">&nbsp;短语-精准</label></span>
                                                    </div>
                                                    <div class="input_box"><span><label class='checkbox-inlines'><input
                                                            type="checkbox" name="matchType" onchange="customColumns(this,1,'advanceMathModel','matchType')"
                                                            value="4">&nbsp;短语-同义</label></span>
                                                    </div>
                                                    <div class="input_box"><span><label class='checkbox-inlines'><input
                                                            type="checkbox" name="matchType" onchange="customColumns(this,1,'advanceMathModel','matchType')"
                                                            value="5">&nbsp;广泛</label></span>
                                                    </div>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="w_list01 fl over" id="advanceQualityModel"><span class="fl">质量度：</span><label
                                                        class='checkbox-inlines'><input style=" float: left"
                                                                                        type="checkbox"
                                                                                        name="keywordQuality" onchange="customColumns(this,0,'advanceQualityModel','keywordQuality')"
                                                                                        value="1"
                                                                                        checked="checked" id="keywordQualitys">&nbsp;
                                                    全选</label></div>
                                                <div class="w_list02 fl over aline" id="keywordQuality">
                                                    <ul>
                                                        <li>

                                                            <div class="input_box"><span><label
                                                                    class='checkbox-inlines'><input style=" float: left"
                                                                                                    type="checkbox"
                                                                                                    name="keywordQuality" onchange="customColumns(this,1,'advanceQualityModel','keywordQuality')"
                                                                                                    value="1"
                                                                                                    checked="checked">&nbsp;
                                                                一分词</label></span>
                                                            </div>
                                                            <div class="input_box"><span><label
                                                                    class='checkbox-inlines'><input type="checkbox"
                                                                                                    name="keywordQuality" onchange="customColumns(this,1,'advanceQualityModel','keywordQuality')"
                                                                                                    value="2"
                                                                                                    checked="checked">&nbsp;
                                                                二分词</label></span>
                                                            </div>
                                                            <div class="input_box"><span><label
                                                                    class='checkbox-inlines'><input type="checkbox"
                                                                                                    name="keywordQuality"
                                                                                                    value="3" onchange="customColumns(this,1,'advanceQualityModel','keywordQuality')"
                                                                                                    checked="checked">&nbsp;
                                                                三分词</label></span>
                                                            </div>
                                                            <div class="input_box"><span><label
                                                                    class='checkbox-inlines'><input type="checkbox"
                                                                                                    name="keywordQuality"
                                                                                                    value="4" onchange="customColumns(this,1,'advanceQualityModel','keywordQuality')"
                                                                                                    checked="checked">&nbsp;
                                                                四分词</label></span>
                                                            </div>
                                                            <div class="input_box"><span><label
                                                                    class='checkbox-inlines'><input type="checkbox"
                                                                                                    name="keywordQuality"
                                                                                                    value="5" onchange="customColumns(this,1,'advanceQualityModel','keywordQuality')"
                                                                                                    checked="checked">&nbsp;
                                                                五分词</label></span>
                                                            </div>
                                                        </li>
                                                        <li>
                                                            <div class="input_box"><span><label
                                                                    class='checkbox-inlines'><input type="checkbox"
                                                                                                    name="keywordQuality"
                                                                                                    value="6" onchange="customColumns(this,1,'advanceQualityModel','keywordQuality')"
                                                                                                    checked="checked">&nbsp;
                                                                六分词</label></span>
                                                            </div>
                                                            <div class="input_box"><span><label
                                                                    class='checkbox-inlines'><input type="checkbox"
                                                                                                    name="keywordQuality"
                                                                                                    value="7" onchange="customColumns(this,1,'advanceQualityModel','keywordQuality')"
                                                                                                    checked="checked">&nbsp;
                                                                七分词</label></span>
                                                            </div>
                                                            <div class="input_box"><span><label
                                                                    class='checkbox-inlines'><input type="checkbox"
                                                                                                    name="keywordQuality"
                                                                                                    value="8" onchange="customColumns(this,1,'advanceQualityModel','keywordQuality')"
                                                                                                    checked="checked">&nbsp;
                                                                八分词</label></span>
                                                            </div>
                                                            <div class="input_box"><span><label
                                                                    class='checkbox-inlines'><input type="checkbox"
                                                                                                    name="keywordQuality"
                                                                                                    value="9" onchange="customColumns(this,1,'advanceQualityModel','keywordQuality')"
                                                                                                    checked="checked">&nbsp;
                                                                九分词</label></span>
                                                            </div>
                                                            <div class="input_box"><span><label
                                                                    class='checkbox-inlines'><input type="checkbox"
                                                                                                    name="keywordQuality"
                                                                                                    value="10" onchange="customColumns(this,1,'advanceQualityModel','keywordQuality')"
                                                                                                    checked="checked">&nbsp;
                                                                十分词</label></span>
                                                            </div>
                                                        </li>
                                                    </ul>

                                                </div>
                                            </li>

                                            <li>
                                                <div class="w_list01 fl over">出价：</div>
                                                <div class="w_list02 fl over" id="keywordPrice">
                                    <span class="aline2">
                                        <input type="text" class="price fl"><pre class="fl"> - </pre><input type="text"
                                                                                                            class="price fl "> <pre
                                            class="fl"> <p>元</p></pre>
                                    </span>

                                                </div>
                                            </li>
                                        </ul>
                                        <div class="fr">
                                            <button type="button" class="btn btn-primary btn-sm  "
                                                    onclick="SearchOk()">确认
                                            </button>
                                            <button type="button" class="btn btn-sm "
                                                    onclick="SearchCancel()">取消
                                            </button>
                                        </div>

                                    </div>
                                </div>
                                <div class="w_list03 ">
                                    <ul class="jiangjia_list">
                                        <li class="current" id="showbox" data-toggle="tooltip"
                                            data-placement="bottom" title="对选中的关键词，设置竞价规则，包括：竞价时段、竞价模式、竞价位置、出价规则等。"><img
                                                src="${pageContext.request.contextPath}/public/img/guize.png">设置规则
                                        </li>
                                        <li id="updateBtn" data-toggle="tooltip"
                                            data-placement="bottom" title="点此对账户数据进行更新。"><img
                                                src="${pageContext.request.contextPath}/public/img/gengxin.png">更新账户数据
                                        </li>
                                        <li id="rankBtn" data-toggle="tooltip"
                                            data-placement="bottom" title="点此查看关键词当前排名。"><img
                                                src="${pageContext.request.contextPath}/public/img/jiancha.png">检查当前排名
                                        </li>
                                        <li id="showbox2" data-toggle="tooltip"
                                            data-placement="bottom" title="选中关键词修改出价。"><img
                                                src="${pageContext.request.contextPath}/public/img/xiugai.png">修改出价
                                        </li>
                                        <li id="showbox7" data-toggle="tooltip"
                                            data-placement="bottom" title="对已设置智能竞价规则的关键词启动竞价模式。"><img
                                                src="${pageContext.request.contextPath}/public/img/qidong.png">启动竞价
                                        </li>
                                        <li id="showbox3" data-toggle="tooltip"
                                            data-placement="bottom" title="对已设置智能竞价规则的关键词暂停竞价。"><img
                                                src="${pageContext.request.contextPath}/public/img/zanting.png">暂停竞价
                                        </li>
                                        <li id="showbox4" data-toggle="tooltip"
                                            data-placement="bottom" title="修改关键词访问url。"><img
                                                src="${pageContext.request.contextPath}/public/img/wangzhi.png">修改访问网址
                                        </li>
                                        <li id="showbox5" data-toggle="tooltip"
                                            data-placement="bottom" title="对重点关注的关键词进行重点词竞价分组管理。"><img
                                                src="${pageContext.request.contextPath}/public/img/fenzu.png">重点词分组
                                        </li>
                                        <li id="showbox6" data-toggle="tooltip"
                                            data-placement="bottom" title="自定义设置重点关注的指标，满足个性需求。"><img
                                                src="${pageContext.request.contextPath}/public/img/zidingyi.png">自定义列
                                        </li>
                                    </ul>
                                </div>
                                <div class="w_list03" style="margin:0 10px 10px 0;"><span class="fl">当前显示数据日期：昨天</span>
                                </div>
                                <div class="gridcss">
                                    <table id="table1" border="0" cellspacing="0" width="100%">
                                    </table>
                                </div>
                                <div id="pagination1" class="pagination"></div>
                            </div>
                        </div>
                        <div class="jingjia_left fl over">
                            <div class="j_l_top over">
                                <span class="fl"><h3>账户目录</h3></span>
                                <a href="javascript:refreshAccountTree();" class="fr">刷新</a>
                            </div>
                            <div class="j_l_top2 over">
                                <span class="fl">查找计划单元</span>
                                <input class="fr" type="image"
                                       src="${pageContext.request.contextPath}/public/img/search.png">
                            </div>
                            <div class="j_list01 over">
                                <ul id="zTree" class="ztree over" style="height:570px;">
                                </ul>
                            </div>
                            <%--<div class="j_l_under over">
                                <a href="#">监控文件夹</a>
                            </div>--%>
                        </div>
                    </div>
                </div>
                <div class="containers over hides">
                    <div class=" jiangjia_concent over">
                        <div class="jingjia_right fl over">
                            <div>
                                <div class="zhushou_concent zhushou over">
                                    <div class="w_list03">
                                        <ul class="jiangjia_list">
                                            <li class="current Screenings"><img
                                                    src="${pageContext.request.contextPath}/public/img/shuaixuan.png">筛选
                                            </li>
                                            <li id="showbox_im" data-toggle="tooltip"
                                                data-placement="bottom" title="对选中的关键词，设置竞价规则，包括：竞价时段、竞价模式、竞价位置、出价规则等。">
                                                <img src="${pageContext.request.contextPath}/public/img/guize.png">设置规则
                                            </li>
                                            <li id="showbox3_im" data-toggle="tooltip"
                                                data-placement="bottom" title="对已设置智能竞价规则的关键词暂停竞价。"><img
                                                    src="${pageContext.request.contextPath}/public/img/zanting.png">暂停竞价
                                            </li>
                                            <li id="showbox2_im" data-toggle="tooltip"
                                                data-placement="bottom" title="选中关键词修改出价。"><img
                                                    src="${pageContext.request.contextPath}/public/img/xiugai.png">修改出价
                                            </li>
                                            <li id="showbox4_im" data-toggle="tooltip"
                                                data-placement="bottom" title="修改关键词访问url。"><img
                                                    src="${pageContext.request.contextPath}/public/img/wangzhi.png">修改访问网址
                                            </li>
                                            <li id="showbox5_im"><img
                                                    src="${pageContext.request.contextPath}/public/img/fenzu.png">分组(改变分组)
                                            </li>

                                            <li id="showboxD"><img
                                                    src="${pageContext.request.contextPath}/public/img/zidingyi.png">删除数据
                                            </li>
                                            <li id="showbox62" data-toggle="tooltip"
                                                data-placement="bottom" title="自定义设置重点关注的指标，满足个性需求。"><img
                                                    src="${pageContext.request.contextPath}/public/img/zidingyi.png">自定义列
                                            </li>
                                        </ul>
                                        <div class="Screening_concent over" style="display: none;">
                                            <div class="Screening_row over">
                                            </div>
                                            <div class="Screening over wd" id="search_div">
                                                筛选设置：<span><input type="checkbox"
                                                                  onclick="checkedStatus(this)">&nbsp;<select
                                                    name="campaignId" id="imCampaignSelect"
                                                    onchange="imloadUnit(this.value)">
                                            </select>&nbsp;</span>
                                <span><input type="checkbox" onclick="checkedStatus(this)">&nbsp;<select id="sUnit"
                                                                                                         name="adgroupId">
                                    <option value='-1'>--请选择单元--</option>
                                </select>&nbsp;</span>
                                <span><input type="checkbox" onclick="checkedStatus(this)">&nbsp;
                                 <input type="text" class="sc_input3" value="如何在网上推广" name="keywordName"
                                        onfocus="if(value=='如何在网上推广') {value=''}"
                                        onblur="if (value=='') {value='如何在网上推广'}"></span>
                                <span><input type="button" value="搜索" class="Screening_input1" id="imSearch"
                                             onclick="imSearchSubmit(this)"></span>
                                            </div>
                                        </div>

                                        <div class="over" style="width:100%;">
                                            <table id="table2" border="0" cellspacing="0" width="101%">
                                            </table>
                                        </div>
                                        <div id="pagination2" class="pagination"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="jingjia_left fl over">
                            <div class="j_l_top over">
                                <span class="fl"><h3>账户目录</h3></span>
                                <a href="javascript:;" onclick="initImzTree(true);" class="fr">刷新</a>
                            </div>
                            <div class="j_l_top2 over">
                                <span class="fl">查找计划单元</span>
                                <input class="fr" type="image"
                                       src="${pageContext.request.contextPath}/public/img/search.png">
                            </div>
                            <div class="j_list01 over">
                                <ul id="zTreeImport" class="ztree over" style="height:570px;">
                                </ul>
                            </div>
                            <%--<div class="j_l_under over">--%>
                            <%--<a href="#">监控文件夹</a>--%>
                            <%--</div>--%>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="../homePage/pageBlock/footer.jsp"/>
    </div>
</div>
<%--<div class="TB_overlayBG"></div>--%>
<div class="box10" style="display:none" id="downloadData">
    <h2 id="downloadBox"><span class="fl">账户下载</span><a href="#" class="close">×</a></h2>

    <div class="mainlist">
        您希望下载账户的哪个部分？
        <ul class="zs_set">
            <li><label class='checkbox-inlines'><input type="radio" checked="checked" name="no1">&nbsp; 所有推广计划</label>
            </li>
            <li><label class='checkbox-inlines'><input type="radio" name="no1">&nbsp; 已下载的推广计划</label></li>
            <li><label class='checkbox-inlines'><input type="radio" name="no1">&nbsp; 从最新的推广计划列表中选择</label></li>
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
<!-------设置规则-------->
<div class="box" id="seetingRules" style="display:none">
    <h2 id="box1"><span class="fl">设置规则</span><a href="#" class="close">×</a></h2>

    <div class="mainlist jiangjia_main">
        <ul>
            <li>
                <b>时段竞价:</b>
                <ul>
                    <li class="jingjia_select">
                        <form name="biddingfrm" action="">
                            <label class='checkbox-inlines'> <input type="radio" checked="checked" name="times"
                                                                    class="time_sl" value="1">&nbsp;单时段竞价</label>
                            &nbsp;
                            <label class='checkbox-inlines'> <input type="radio" name="times" class="time_sl1"
                                                                    value="2"> &nbsp;多时段竞价 &nbsp;</label>
                        </form>
                        <div class="time_select">
                            <select id="start">
                                <option>0点</option>
                            </select><span>至</span>
                            <select id="end">
                                <option>24点</option>
                            </select>
                        </div>
                        <div class="time_select01 hides">
                            <ul>
                                <li><input name="mtimes" type="checkbox" data-id="1"><select id="start1">
                                    <option>0点</option>
                                </select><span>至</span>
                                    <select id="end1">
                                        <option>12点</option>
                                    </select>&nbsp;上午
                                </li>
                                <li><input name="mtimes" type="checkbox" data-id="2"><select id="start2">
                                    <option>12点</option>
                                </select> <span>至</span><select id="end2">
                                    <option>14点</option>
                                </select>
                                    &nbsp;中午
                                </li>
                                <li><input name="mtimes" type="checkbox" data-id="3"><select id="start3">
                                    <option>14点</option>
                                </select><span>至</span><select id="end3">
                                    <option>24点</option>
                                </select>&nbsp;下午
                                </li>
                            </ul>
                        </div>
                    </li>
                </ul>
            </li>
            <li>
                <b>竞价模式:</b>
                <ul class="fl">
                    <li>
                        <form><label class='checkbox-inlines'><input type="radio" checked="checked" name="mode"
                                                                     value="102">&nbsp;经济 </label>&nbsp;<label
                                class='checkbox-inlines'><input
                                type="radio"
                                name="mode" value="101"> &nbsp;快速</label>
                            &nbsp;</form>
                    </li>
                </ul>
            </li>
            <%--<li>--%>
            <%--<form>竞价规则：--%>
            <%--<select id="device">--%>
            <%--<option value="1">计算机</option>--%>
            <%--</select>--%>
            <%--</form>--%>
            <%--</li>--%>
            <li>
                <b>竞价位置:</b>
                <ul class="fl">
                    <li>
                        <select id="pos">
                            <option class="right_define1" value="1">左侧:1位</option>
                            <option class="right_define1" value="2">左侧:2-3位</option>
                            <option class="right_define1" value="3">右侧:1-3位</option>
                            <option class="right_define" value="4">右侧自定义</option>
                        </select>
                    </li>
                </ul>
            </li>
            <li>
                <b>出价规则:</b>
                <ul class="fl">
                    <%--  <li class="right_stes over">
                          <span class="right_sets1 hides over">右侧：<input name="rightpos" type="text" class="price2"> 位 </span>
                      </li>--%>
                    <li>最高出价（最低区间0.01）<input type="text" id="max" class="price2" value="0.01"></li>
                    <li>最低出价（最低区间0.01）<input type="text" id="min" class="price2" value="0.01"></li>
                </ul>
            </li>
            <li>
                <b class="fl">出价未达到排名:</b>
                <ul class="fl">
                    <li>
                        <form>
                            <label class='checkbox-inlines'> <input name="failed" type="radio" checked="checked"
                                                                    value="11"> &nbsp;自动匹配最佳排名</label>
                            &nbsp;<label class='checkbox-inlines'><input type="radio" name="failed" value="12"> &nbsp;恢复账户设置
                            &nbsp;</label>
                        </form>
                    </li>
                </ul>
            </li>
            <li>
                <b class="fl">自动竞价模式:</b>
                <ul class="fl">
                    <li>
                        <p><label class='checkbox-inlines'><input type="radio" name="auto" value="1" checked="checked"
                                                                  id="jingjia_adds"><span>单次竞价</span></label></p>
                    </li>
                    <li id="jiangjia_add" style="padding-left:10px;">
                        <label class='checkbox-inlines'><input type="radio" name="sbid"
                                                               value="everyday"><span>每天执行</span></label>
                        <label class='checkbox-inlines'> <input type="radio" name="sbid" value="bytime">
                            <span>竞价次数:</span></label><input class="times"
                                                             type="text"
                                                             name="bytimes">
                    </li>

                    <li><label class='checkbox-inlines'><input type="radio" name="auto" value="2"
                                                               id="jiangjia_chongfu"><span>重复竞价速度 每隔</span></label>
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
<div class="box2" style="display:none; *width:400px;">
    <h2 id="box2"><span class="fl">修改出价</span><a href="#" class="close">×</a></h2>

    <div class="mainlist">
        <ul>
            <li>
                <label class='checkbox-inlines'> <input type="radio" name="price1" checked="checked"> 输入新出价:</label>
                <input name="newPrice" class="zs_input3"
                       type="text"></li>
            <li><label class='checkbox-inlines'><input type="radio" name="price1"><span
                    class="mainlist_left"> 使用单元出价 </span></label></li>
        </ul>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li id="modifyPrice" class="current">保存</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>
<!---------修改匹配模式----------->
<div class="box3" style="display:none">
    <h2 id="box3"><span class="fl">修改匹配模式</span><a href="#" class="close">×</a></h2>

    <div class="mainlist">
        <ul>
            <li><label class='checkbox-inlines'><input type="radio" name="price2" checked="checked"><span
                    class="mainlist_left"> 广泛 </span></label></li>
            <li><label class='checkbox-inlines'><input type="radio" name="price2"><span class="mainlist_left">精准</span></label>
            </li>
            <li>
                <div class="fl"><label class='checkbox-inlines'><input type="radio" name="price2" class="short"><span
                        class="mainlist_left">短语 </span></label></div>
                <form class="shorts hides fl ">
                    <label class='checkbox-inlines'> <input type="radio" name="price3"><span
                            class="mainlist_left">核心包含</span></label>
                    <label class='checkbox-inlines'> <input type="radio" name="price3"><span
                            class="mainlist_left">同义包含</span></label>
                    <label class='checkbox-inlines'><input type="radio" name="price3"><span
                            class="mainlist_left">精准包含</span></label>
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
<div class="box4" style="display:none;*width:400px;">
    <h2 id="box4"><span class="fl">修改访问网址</span><a href="#" class="close">×</a></h2>

    <div class="mainlist">
        <ul>
            <li>输入指向网址：</li>
            <li>http://<input name="urlAddress" class="zs_input3" type="text"></li>
        </ul>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li id="modifyUrl" class="current">保存</li>
                <li class="close">取消</li>

            </ul>
        </div>
    </div>
</div>

<!---------分组管理----------->
<div class="box5" style="display:none;*width:400px;*width:410px;">
    <h2 id="box5"><span class="fl">重点词分组</span><a href="#" class="close">×</a></h2>

    <div class="mainlist">
        <div class="gusuan_bottom1 over fl ">
            <ul>
                <li>关键词列表：</li>
                <li><textarea class="zs_input5" id="imKwd" style="font-size: 12px;" disabled="disabled"
                              readonly="readonly"></textarea></li>
                <li>选择分组名：<a href="javascript:void(0)" onclick="ImDeleteCustomGroup()">删除该分组</a></li>
                <li><select id="cgroup" onchange="cgroupInsert();"></select></li>
            </ul>
            <input type="button" value="+" id="showTxt" onclick="showGroupNameTxt()"/>
            <input name="cgroupName" style="display: none;"/>
        </div>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li class="current" onclick="checkGroupOk()">保存</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>

<!--------------关键词当前排名----------------->
<div class="box8" id="paiming" style="display:none;*width:410px;">
    <h2 id="box8"><span class="fl">当前排名</span><a href="#" class="close">×</a></h2>

    <div class="mainlist">
        <div class="gusuan_bottom1 over fl ">
            <table id="rankTable" border="0" cellspacing="0" width="100%">
                <thead>
                <tr class="list04_top">
                    <td>地域</td>
                    <td>排名</td>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
</div>

<!---------自定义列----------->
<div id="custom_col" class="box6" style="display:none; *width:280px;">
    <h2 id="box6"><span class="fl">自定义列</span><a href="#" class="close">×</a></h2>

    <div class="mainlist">
        <ul id="customColumns">
            <li>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,0,'customColumns','customColList')" type="checkbox" checked
                                                                               value="allColumns" name="columns"> 全部添加</label></span>
            </li>
        </ul>
        <ul id="customColList">
            <li>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="campaignName" name="columns"> 推广计划</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1)" type="checkbox" checked="checked"
                                                                               value="cpm"
                                                                               name="columns"> 千次展现消费</label></span>
            </li>
            <li>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="adgroupName" name="columns"> 推广单元</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="impression" name="columns">
                    展现量</label></span>
            </li>
            <li>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="statusStr"
                                                                               name="columns"> 状态</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="ctr" name="columns">
                    点击率</label></span>
            </li>
            <li>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="price" name="columns"> 出价</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="cpc"
                                                                               name="columns">平均点击价格</label></span>
            </li>
            <li>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="pcQuality" name="columns"> PC端质量度</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="mQuality"
                                                                               name="columns">移动端质量度</label></span>
            </li>
            <li>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="click"
                                                                               name="columns"> 点击量</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="currentRank" name="columns"> 当前排名</label></span>
            </li>
            <li>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="cost" name="columns">
                    消费</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="pcDestinationUrl" name="columns">
                    Pc&nbsp;URL</label></span>
            </li>
            <li><span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="ruleDesc"
                                                                               name="columns"> 竞价规则</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="mobileDestinationUrl"
                                                                               name="columns">
                    Mobile&nbsp;URL</label></span>
            </li>
            <li><span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="biddingStatus" name="columns">
                竞价状态</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input onchange="customColumns(this,1,'customColumns','customColList')" type="checkbox" checked="checked"
                                                                               value="matchType" name="columns">
                    匹配模式</label></span>
            </li>
        </ul>

    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li id="customCol" class="current">确定</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>

<!---------自定义列重点词分组----------->
<div id="custom_col2" class="box6" style="display:none; *width:280px;">
    <h2 id="box62"><span class="fl">自定义列</span><a href="#" class="close">×</a></h2>

    <div class="mainlist">
        <ul id="customColList2">
            <li>
                <span class="define fl"><label class='checkbox-inlines'><input type="checkbox" value="campaignName"
                                                                               name="columns"> 推广计划</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input type="checkbox" value="cpm"
                                                                               name="columns"> 千次展现消费</label></span>
            </li>
            <li>
                <span class="define fl"><label class='checkbox-inlines'><input type="checkbox" value="adgroupName"
                                                                               name="columns"> 推广单元</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input type="checkbox" checked="checked"
                                                                               value="impression" name="columns">
                    展现量</label></span>
            </li>
            <li>
                <span class="define fl"><label class='checkbox-inlines'><input type="checkbox" checked="checked"
                                                                               value="statusStr"
                                                                               name="columns"> 状态</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input type="checkbox" checked="checked"
                                                                               value="ctr" name="columns">
                    点击率</label></span>
            </li>
            <li>
                <span class="define fl"><label class='checkbox-inlines'><input type="checkbox" checked="checked"
                                                                               value="price" name="columns"> 出价</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input type="checkbox" value="cpc"
                                                                               name="columns">平均点击价格</label></span>
            </li>
            <li>
                <span class="define fl"><label class='checkbox-inlines'><input type="checkbox" checked="checked"
                                                                               value="pcQuality" name="columns"> PC端质量度</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input type="checkbox" checked="checked"
                                                                               value="mQuality"
                                                                               name="columns">移动端质量度</label></span>
            </li>
            <li>
                <span class="define fl"><label class='checkbox-inlines'><input type="checkbox" value="click"
                                                                               name="columns"> 点击量</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input type="checkbox" checked="checked"
                                                                               value="currentRank" name="columns"> 当前排名</label></span>
            </li>
            <li>
                <span class="define fl"><label class='checkbox-inlines'><input type="checkbox" checked="checked"
                                                                               value="cost" name="columns">
                    消费</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input type="checkbox" checked="checked"
                                                                               value="pcDestinationUrl"
                                                                               name="columns">
                    Pc&nbsp;URL</label></span>
            </li>
            <li><span class="define fl"><label class='checkbox-inlines'><input type="checkbox" checked="checked"
                                                                               value="ruleDesc"
                                                                               name="columns"> 竞价规则</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input type="checkbox" checked="checked"
                                                                               value="mobileDestinationUrl"
                                                                               name="columns">
                    Mobile&nbsp;URL</label></span>
            </li>
            <li><span class="define fl"><label class='checkbox-inlines'><input type="checkbox" checked="checked"
                                                                               value="biddingStatus" name="columns">
                竞价状态</label></span>
                <span class="define fl"><label class='checkbox-inlines'><input type="checkbox" value="matchType"
                                                                               name="columns"> 匹配模式</label></span>
            </li>
        </ul>

    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li id="customCol2" class="current">确定</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>

<div class="box9" style="display:none; *width:200px;">
    <h2 id="box9"><span class="fl">竞价状态</span><a href="#" class="close">×</a></h2>

    <div class="mainlist">
        <select id="biddingStatus">
            <option value="1" selected="selected">已启动</option>
            <option value="0">已暂停</option>
            <option value="2">无</option>
        </select>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li id="biddingStatusOk" class="current">确定</li>
                <li class="close">取消</li>

            </ul>
        </div>
    </div>
</div>

<div class="box7" style="display:none; *width:200px;">
    <h2 id="box7"><span class="fl">关键词状态</span><a href="#" class="close">×</a></h2>

    <div class="mainlist">
        <select id="statusStr">
            <option value="41" selected="selected">有效</option>
            <option value="42">暂停推广</option>
            <option value="43">不宜推广</option>
            <option value="44">搜索无效</option>
            <option value="45">待激活</option>
            <option value="46">审核中</option>
            <option value="47">搜索量过低</option>
            <option value="48">部分无效</option>
            <option value="49">计算机搜索无效</option>
            <option value="50">移动搜索无效</option>
        </select>
    </div>
    <div class="main_bottom">
        <div class="w_list03">
            <ul>
                <li id="statusStrOk" class="current">确定</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>

<%--<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/index.css">
<script type="text/javascript" src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/jqgrid/4.6.0/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/pagination/jquery.pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/tc.min.js"></script>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/html.js"></script>--%>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/assistant/updateAccountData.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/importKeyword/importKeywordBidding.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bidding.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery.pin/1.0.1/jquery.pin.min.js"></script>
<script type="text/javascript">
    $(function () {
        $("[data-toggle='tooltip']").tooltip();
    });
    String.prototype.trims = function () {
        return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
    };
    //jqGrid表格宽度自适应
    $(document).ready(function () {
        $("#table1").jqGrid("setGridWidth", document.getElementById("main").clientWidth * 0.85, true);
        $("#table2").jqGrid("setGridWidth", document.getElementById("main").clientWidth * 0.85, true);

        $(window).resize(function () {
            $("#table1").jqGrid("setGridWidth", document.getElementById("main").clientWidth * 0.85, true);
            $("#table2").jqGrid("setGridWidth", document.getElementById("main").clientWidth * 0.85, true);
        });
        /*   function Tablewidth() {
            if ($(".nav_left").css("display") == "none") {
                $("#table1").jqGrid("setGridWidth", $(document.body).width() - 272, true);
                $("#table2").jqGrid("setGridWidth", $(document.body).width() - 272, true);
            }
            else {
                $("#table1").jqGrid("setGridWidth", $(document.body).width() - 452, true);
                $("#table2").jqGrid("setGridWidth", $(document.body).width() - 452, true);
            }
        }

     if (!!window.ActiveXObject || "ActiveXObject" in window) {
            target.attachEvent('onclick', function (event) {
                Tablewidth()
            });
        } else {
            target.addEventListener('click', function (event) {
                Tablewidth()
            }, false);
        }*
        if (!!window.ActiveXObject || "ActiveXObject" in window) {
            myId.attachEvent('onclick', function (event) {
                Tablewidth()
            });
        } else {
            myId.addEventListener('click', function (event) {
                Tablewidth()
            }, false);
        }*/

    });

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
    var imzTreeSetting = {
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
            beforeClick: iMbeforeClick,
            beforeAsync: null,
            onAsyncError: null,
            onAsyncSuccess: null
        }
    };
    var zNodes = "";
    var _campaignId = null;
    var _adgroupId = null;
    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i = 0, l = childNodes.length; i < l; i++) {
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
    }
    function beforeClick(treeId, treeNode) {
        if (treeNode.level == 0) {
            //点击的是父节点(推广计划),则应该展示其下属的推广单元数据
//            alert(treeNode.id + "," + treeNode.name);
            _campaignId = treeNode.id;
            _adgroupId = null;
            text = null;
            f = null;
            _filter = null;
            matchType = null;
            keywordQuality = null;
            keywordPrice = null;
            _statusStr = null;
            _biddingStatus = null;
            keyWordPage = -1;
            pageIndex = 0;
            //事件处理

            skip = 0;
            limit = 20;
//        dataUrl = "/bidding/list?cp=" + treeNode.id + "&s=" + skip + "&l=" + limit;
//
//        grid.setGridParam({url: dataUrl}).trigger("reloadGrid");

            var tmpValue1 = $("#table1").jqGrid("getGridParam", "postData");
            $.extend(tmpValue1, {
                postData: {
                    cp: treeNode.id,
                    ag: _adgroupId,
                    s: skip,
                    l: limit,
                    q: text,
                    f: f,
                    filter: _filter,
                    matchType: matchType,
                    price: keywordPrice,
                    quality: keywordQuality,
                    biddingStatus: _biddingStatus,
                    statusStr: _statusStr
                }
            });

            $("#table1").jqGrid("setGridParam", tmpValue1).trigger("reloadGrid");
            $(".jingjia_left").height($(".jingjia_right")[0].offsetHeight - 20 + "px");
            $("#zTree").height($(".jingjia_right")[0].offsetHeight - 130 + "px");

        } else if (treeNode.level == 1) {
            //点击的是子节点(推广单元),则应该展示其下属的关键词数据
//            alert(treeNode.id + "," + treeNode.name);
            _campaignId = null;
            _adgroupId = treeNode.id;
            text = null;
            f = null;
            _filter = null;
            matchType = null;
            keywordQuality = null;
            keywordPrice = null;
            _statusStr = null;
            _biddingStatus = null;
            keyWordPage = -1;
            pageIndex = 0;
            //事件处理

            skip = 0;
            limit = 20;
//        dataUrl = "/bidding/list?ag=" + treeNode.id + "&s=" + skip + "&l=" + limit;
//
//        grid.setGridParam({url: dataUrl}).trigger("reloadGrid");

            var tmpValue2 = $("#table1").jqGrid("getGridParam", "postData");
            $.extend(tmpValue2, {
                postData: {
                    cp: _campaignId,
                    ag: treeNode.id,
                    s: skip,
                    l: limit,
                    q: text,
                    f: f,
                    filter: _filter,
                    matchType: matchType,
                    price: keywordPrice,
                    quality: keywordQuality,
                    biddingStatus: _biddingStatus,
                    statusStr: _statusStr
                }
            });

            $("#table1").jqGrid("setGridParam", tmpValue2).trigger("reloadGrid");

        }

    }
    //重点词竞价树点击
    function iMbeforeClick(treeId, treeNode) {
        if (treeNode.level == 0) {
            keyWordPage = -1;
            pageIndex = 0;
            //事件处理

            skip = 0;
            limit = 20;
            selection = treeNode.id;
            dataUrl2 = "/importBid/loadData?cgId=" + treeNode.id + "&s=" + skip + "&l=" + limit;

            grid2.setGridParam({url: dataUrl2}).trigger("reloadGrid");
        }
    }
    //重点词竞价搜索按钮提交事件
    function imSearchSubmit(rs) {
        if ($(rs).attr("class") != "Screening_input1") {
            var _checkBox = $("#search_div input[type='checkbox']");
            imSearchData = {};
            for (var i = 0; i < _checkBox.length; i++) {
                var _check = $("#search_div input[type='checkbox']:eq(" + i + ")");
                if (_check.is(":checked") == true) {
                    imSearchData[_check.next().attr("name")] = _check.next().val();
                }
            }
            if (imSearchData.campaignId != undefined && imSearchData.adgroupId != undefined && imSearchData.keywordName != undefined) {
                dataUrl2 = "/importBid/loadData?keywordName=" + imSearchData.keywordName;
                grid2.setGridParam({url: dataUrl2}).trigger("reloadGrid");
            } else if (imSearchData.keywordName != undefined) {
                dataUrl2 = "/importBid/loadData?keywordName=" + imSearchData.keywordName;
                grid2.setGridParam({url: dataUrl2}).trigger("reloadGrid");
            } else if (imSearchData.campaignId != undefined && imSearchData.adgroupId != undefined) {
                if (imSearchData.adgroupId == -1) {
//                    alert("请选择一个单元!");
                    AlertPrompt.show("请选择一个单元!");
                } else {
                    dataUrl2 = "/importBid/loadData?campaignId=" + imSearchData.campaignId + "&adgroupId=" + imSearchData.adgroupId;
                    grid2.setGridParam({url: dataUrl2}).trigger("reloadGrid");
                }
            } else if (imSearchData.campaignId != undefined) {
                if (imSearchData.campaignId == -1) {
//                    alert("请选择一个计划!");
                    AlertPrompt.show("请选择一个计划!");
                } else {
                    dataUrl2 = "/importBid/loadData?campaignId=" + imSearchData.campaignId;
                    grid2.setGridParam({url: dataUrl2}).trigger("reloadGrid");
                }
            }


        } else {
//            alert("请选择筛选条件！");
            AlertPrompt.show("请选择筛选条件!");
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

    /******************pagination*********************/
    var items_per_page = 20;    //默认每页显示20条数据
    var pageIndex = 0;
    var keyWordPage = -1;
    var VIPKeyWordPage = -1;
    var records = 0;
    var records2 = 0;
    var skip = 0;
    var limit = 20;

    var pageSelectCallback = function (page_index, jq) {
        if (type == 1) {
            $("#pagination1").append("<span style='margin-right:10px;'>跳转到 <input id='anyPageNumber1' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipPage();' class='page_go'> GO</a>");
        } else {
            $("#pagination2").append("<span style='margin-right:10px;'>跳转到 <input id='anyPageNumber2' type='text' class='price'/></span>&nbsp;&nbsp;<a href='javascript:skipPage();' class='page_go'> GO</a>");
        }
        if (pageIndex == page_index) {
            return false;
        }
        pageIndex = page_index;
        toAnyPage(page_index);
        return false;
    };

    var getOptionsFromForm = function (current_page) {
        var opt = {callback: pageSelectCallback};

        opt["items_per_page"] = items_per_page;
        opt["current_page"] = current_page;
        opt["prev_text"] = "上一页";
        opt["next_text"] = "下一页";

        //avoid html injections
        var htmlspecialchars = {"&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;"};
        $.each(htmlspecialchars, function (k, v) {
            opt.prev_text = opt.prev_text.replace(k, v);
            opt.next_text = opt.next_text.replace(k, v);
        });
        return opt;
    };
    var optInit = getOptionsFromForm(0);
    /*************************************************/

    /**********************jqGrid*********************/
    var dataUrl = "/bidding/list";
    var grid = null;
    var dataUrl2 = "/importBid/loadData";

    var getAllCheckedcb = function () {
        var rowIds = $("#table1").jqGrid('getGridParam', 'selarrrow');
        var keywordIds = [];
        for (var i = 0, l = rowIds.length; i < l; i++) {
            keywordIds.push(grid.jqGrid('getCell', rowIds[i], "keywordId"));
        }
        return keywordIds;
    };
    var getAllCheckedcbIm = function () {
        var rowIds = $("#table2").jqGrid('getGridParam', 'selarrrow');
        var keywordIds = [];
        for (var i = 0, l = rowIds.length; i < l; i++) {
            keywordIds.push(grid2.jqGrid('getCell', rowIds[i], "keywordId"));
        }
        return keywordIds;
    };

    var getAllSelectedBidRule = function () {
        var rowIds = $("#table1").jqGrid('getGridParam', 'selarrrow');
        var keywordIds = [];
        for (var i = 0, l = rowIds.length; i < l; i++) {
            if (grid.jqGrid('getCell', rowIds[i], "biddingStatus") == "无")
                continue;

            keywordIds.push(grid.jqGrid('getCell', rowIds[i], "keywordId"));
        }
        return keywordIds;
    };
    var getAllSelectedBidRuleIm = function () {
        var rowIds = $("#table2").jqGrid('getGridParam', 'selarrrow');
        var keywordIds = [];
        for (var i = 0, l = rowIds.length; i < l; i++) {
            if (grid2.jqGrid('getCell', rowIds[i], "biddingStatus") == "无")
                continue;

            keywordIds.push(grid2.jqGrid('getCell', rowIds[i], "keywordId"));
        }
        return keywordIds;
    };

    var changeGridCol1 = function () {
        var cbs = $("#customColList").find("input[type=checkbox]");
        $.each(cbs, function (i, item) {
            if (item.checked)
                $("#table1").setGridParam().showCol(item.value);
            else
                $("#table1").setGridParam().hideCol(item.value);
        });
        $("#table1").jqGrid("setGridWidth", document.getElementById("main").clientWidth * 0.84, true);
//    $("#table1").closest(".ui-jqgrid-bdiv").css({'overflow-x': 'scroll'});
        $(".TB_overlayBG").css("display", "none");
        $("#custom_col").css("display", "none");
    };
    var changeGridCol2 = function () {
        var cbs = $("#customColList2").find("input[type=checkbox]");
        $.each(cbs, function (i, item) {
            if (item.checked)
                $("#table2").setGridParam().showCol(item.value);
            else
                $("#table2").setGridParam().hideCol(item.value);
        });
        $("#table2").jqGrid("setGridWidth", document.getElementById("main").clientWidth * 0.84, true);
        $(".TB_overlayBG").css("display", "none");
        $(".box6").css("display", "none");
    };
    /*************************************************/

    //loading
    var ajaxbg = $("#background,#progressBar");
    ajaxbg.hide();
    $(document).ajaxStart(function () {
        ajaxbg.show();
    });
    $(document).ajaxStop(function () {
        ajaxbg.fadeOut(1000);
    });

    var refreshAccountTree = function () {
        $.ajax({
            url: "/account/get_tree",
            type: "GET",
            async: false,
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                zNodes = data.trees;
                $.fn.zTree.init($("#zTree"), setting, zNodes);
//            $.fn.zTree.init($("#zTree2"), setting, zNodes);
            }
        });
    };

    var _biddingStatus = null;
    var _statusStr = null;

    var strUp = null;
    var strDown = null;

    $(function () {
        strUp = "<%=strUp%>";
        strDown = "<%=strDown%>";

        //获取账户树数据
        $.ajax({
            url: "/account/get_tree",
            type: "GET",
            async: false,
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                zNodes = data.trees;
                $.fn.zTree.init($("#zTree"), setting, zNodes);
//            $.fn.zTree.init($("#zTree2"), setting, zNodes);
            }
        });

        $("#customCol").on('click', function () {
            changeGridCol1();
        });

        $("#customCol2").on('click', function () {
            changeGridCol2();
        });

        $("#modifyPrice").on('click', function () {
            if ($("#box2").next().find("input[name=price1]:checked").length == 0) {
                return false;
            }
            var radios = $("#box2").next().find("input[name=price1]:radio");
            var price = 0;
            $.each(radios, function (i, item) {
                if (i == 0 && item.checked) {
                    //自定义新的出价
                    if ($("#box2").next().find("input[name=newPrice]:text").val().trims().length > 0) {
                        price = $("#box2").next().find("input[name=newPrice]:text").val();
                        return false;
                    }
                }
                //使用单元出价
            });
            $.ajax({
                url: "/keyword/update",
                dataType: "json",
                data: {
                    'ids': selectedKeywordIds.toString(),
                    "price": price
                },
                type: "POST",
                success: function (data) {
                    if (data.stat) {
                        $(".TB_overlayBG").css("display", "none");
                        $(".box2").css("display", "none");
//                        alert("修改成功!");
                        AlertPrompt.show("修改成功!");
                    } else {
                        AlertPrompt.show("修改失败!");
//                        alert("修改失败!");
                    }
                }
            });
        });

        $("#modifyUrl").on('click', function () {
            var url = $("input[name=urlAddress]:text").val();
            if (url.trims().length == 0) {
                return false;
            }

            url = "http://" + url;
            $.ajax({
                url: "/keyword/update",
                data: {
                    'ids': selectedKeywordIds.toString(),
                    "pcUrl": url
                },
                type: "POST",
                success: function (data) {
                    if (data.stat) {
                        $(".TB_overlayBG").css("display", "none");
                        $(".box4").css("display", "none");
                        AlertPrompt.show("修改成功!");
//                        alert("修改成功!");
                    } else {
                        AlertPrompt.show("修改失败!");
//                        alert("修改失败!");
                    }
                }
            });
        });

        $("#biddingStatusOk").on('click', function () {
            _biddingStatus = $("#biddingStatus option:selected").val();
            skip = pageIndex * limit;

            var tmpValue = $("#table1").jqGrid("getGridParam", "postData");
            $.extend(tmpValue, {
                postData: {
                    cp: _campaignId,
                    ag: _adgroupId,
                    s: skip,
                    l: limit,
                    q: text,
                    f: f,
                    filter: _filter,
                    matchType: matchType,
                    price: keywordPrice,
                    quality: keywordQuality,
                    biddingStatus: _biddingStatus,
                    statusStr: _statusStr
                }
            });

            $(".TB_overlayBG").css("display", "none");
            $(".box9").css("display", "none");

            $("#table1").jqGrid("setGridParam", tmpValue).trigger("reloadGrid");
        });

        $("#statusStrOk").on('click', function () {
            _statusStr = $("#statusStr option:selected").val();
            skip = pageIndex * limit;

            var tmpValue = $("#table1").jqGrid("getGridParam", "postData");
            $.extend(tmpValue, {
                postData: {
                    cp: _campaignId,
                    ag: _adgroupId,
                    s: skip,
                    l: limit,
                    q: text,
                    f: f,
                    filter: _filter,
                    matchType: matchType,
                    price: keywordPrice,
                    quality: keywordQuality,
                    biddingStatus: _biddingStatus,
                    statusStr: _statusStr
                }
            });

            $(".TB_overlayBG").css("display", "none");
            $(".box7").css("display", "none");

            $("#table1").jqGrid("setGridParam", tmpValue).trigger("reloadGrid");
        });

        //jqGrid table1
        grid = $("#table1").jqGrid({
            datatype: "json",
            url: dataUrl,
            mtype: "POST",
            jsonReader: {
                root: "rows",
                records: "records",
                repeatitems: false
            },
            height: 480,
            shrinkToFit: true,//此选项用于根据width计算每列宽度的算法,默认值true
            colModel: [
                // {label: '<input type=\"checkbox\" name=\"check_all\" onclick=\"checkAll();\" id=\"check_all\" >', name: 'checkall', width: 30,
                //sortable: false, align: 'center', formatter:function(v,x,r){ return "<input type='checkbox'/>"; }},
                {label: ' 关键词ID', name: 'keywordId', sortable: false, align: 'center', hidden: true},
                {label: ' 关键词', name: 'keyword', sortable: false, align: 'center'},
                {label: ' 推广计划', name: 'campaignName', sortable: false, align: 'center', hidden: true},
                {label: ' 推广单元', name: 'adgroupName', sortable: false, align: 'center', hidden: true},
                {label: ' 匹配模式', name: 'matchType', sortable: false, align: 'center', hidden: true},
                {label: ' 高级短语匹配模式', name: 'phraseType', sortable: false, align: 'center', hidden: true},
                {label: ' 当前排名', name: 'currentRank', sortable: false, align: 'center', classes: 'jqgrid_td_cursor'},
                {label: ' 消费', name: 'cost', sortable: false, align: 'center'},
                {label: ' 展现量', name: 'impression', sortable: false, align: 'center'},
                {label: ' 点击量', name: 'click', sortable: false, align: 'center', hidden: true},
                {label: ' 点击率', name: 'ctr', sortable: false, align: 'center'},
                {label: ' 出价', name: 'price', sortable: false, align: 'center'},
                {label: ' 平均点击价格', name: 'cpc', sortable: false, align: 'center', hidden: true},
                {label: ' 千次展现消费', name: 'cpm', sortable: false, align: 'center', hidden: true},
                {label: ' 质量度', name: 'pcQuality', sortable: false, align: 'center'},
                {label: ' 移动端质量度', name: 'mQuality', sortable: false, align: 'center'},
                {label: ' 状态', name: 'statusStr', sortable: false, align: 'center'},
                {label: ' 竞价规则', name: 'ruleDesc', sortable: false, align: 'center', classes: 'jqgrid_td_cursor'},
                {
                    label: ' Pc URL',
                    name: 'pcDestinationUrl',
                    sortable: false,
                    align: 'center',
                    formatter: 'link'
                },
                {
                    label: ' Mobile URL',
                    name: 'mobileDestinationUrl',
                    sortable: false,
                    align: 'center',
                    formatter: 'link'
                },
                {label: ' 竞价状态', name: 'biddingStatus', sortable: false, align: 'center', classes: 'jqgrid_td_cursor'},
                {label: ' 是否设置了rule', name: 'rule', sortable: false, align: 'center', hidden: true},
                {label: ' adgroupId', name: 'adgroupId', sortable: false, align: 'center', hidden: true}
            ],
            rowNum: 20,// 默认每页显示记录条数
            rownumbers: false,
            loadui: 'disable',
            pgbuttons: false,
            autowidth: true,
            altRows: true,
            altclass: 'list2_box2',
            resizable: true,
            scroll: false,
            multiselect: true,
            beforeRequest: function () {
            },
            beforeSelectRow: function (rowId, event) {
                var $myGrid = $(this),
                        iCol = $.jgrid.getCellIndex($(event.target).closest('td')[0]),
                        cm = $myGrid.jqGrid('getGridParam', 'colModel');
                if (cm[iCol].name === "cb") {
                    return true;
                }

                var statusStr = $("#table1").jqGrid('getCell', rowId, "statusStr");
                if (statusStr == "未同步") {
                    return false;
                }

                var ruleFlag = $("#table1").jqGrid('getCell', rowId, "rule"); //true, 已经设置竞价规则
                var keywordId = $("#table1").jqGrid('getCell', rowId, "keywordId");
                var bidRule = $("#table1").jqGrid("getCell", rowId, "ruleDesc");//设置竞价规则
                var bidStatus = $("#table1").jqGrid("getCell", rowId, "biddingStatus");//竞价状态: 已启动, 已暂停, 无
                if (iCol === 7) {//查看当前排名
                    getRank(keywordId);
                } else if (iCol === 18) {//设置竞价规则
                    $(".TB_overlayBG").css({
                        display: "block", height: $(document).height()
                    });
                    $("#seetingRules").css({
                        left: ($("body").width() - $("#seetingRules").width()) / 2 - 20 + "px",
                        top: ($(window).height() - $("#seetingRules").height()) / 2 + $(window).scrollTop() + "px",
                        display: "block"
                    });
                    selectedKeywordIds = [];
                    selectedKeywordIds.push(keywordId);
                } else if (iCol === 21) {   //修改竞价状态
                    if (bidStatus == "已暂停") {//竞价状态: 暂停
                        if (confirm("您要启动竞价吗?")) {
                            $.ajax({
                                url: "/bidding/pauseBidding",
                                dataType: "json",
                                async: false,
                                data: {
                                    'keywordId': keywordId,
                                    "biddingStatus": 1
                                },
                                type: "POST",
                                success: function (data) {
                                    if (data.stat) {
                                        $("#table1").setCell(rowId, "biddingStatus", "已启动");
                                        AlertPrompt.show("启动成功!");
//                                        alert("启动成功!");
                                    } else {
                                        AlertPrompt.show("启动失败!");
//                                        alert("启动失败!");
                                    }
                                }
                            });
                        }
                    } else if (bidStatus == "已启动") {//竞价状态: 启动
                        if (confirm("您要暂停竞价吗?")) {
                            $.ajax({
                                url: "/bidding/pauseBidding",
                                dataType: "json",
                                async: false,
                                data: {
                                    'keywordId': keywordId,
                                    "biddingStatus": 0
                                },
                                type: "POST",
                                success: function (data) {
                                    if (data.stat) {
                                        $("#table1").setCell(rowId, "biddingStatus", "已暂停");
                                        AlertPrompt.show("暂停成功!");
//                                        alert("暂停成功!");
                                    } else {
//                                        alert("暂停失败!");
                                        AlertPrompt.show("暂停失败!");
                                    }
                                }
                            });
                        }
                    }
                }

                return false;
            },
            onSelectRow: function (rowId, status, event) {
//            $("table1").jqGrid('setSelection', rowId);
            },
            onCellSelect: function (rowId, index, contents, event) {
            },

            loadComplete: function () {
//            alert($("#table1").jqGrid("getRowData").length);
            },

            gridComplete: function () {
//            alert(JSON.stringify($("#table1").jqGrid("getRowData")));
//
//                $("#jqgh_table1_biddingStatus").on('click', function () {
//                    $(".TB_overlayBG").css({
//                        display: "block", height: $(document).height()
//                    });
//                    $(".box9").css({
//                        left: ($("body").width() - $(".box9").width()) / 2 - 20 + "px",
//                        top: ($(window).height() - $(".box9").height()) / 2 + $(window).scrollTop() + "px",
//                        display: "block"
//                    });
//                });
//
//                $("#jqgh_table1_statusStr").on('click', function () {
//                    $(".TB_overlayBG").css({
//                        display: "block", height: $(document).height()
//                    });
//                    $(".box7").css({
//                        left: ($("body").width() - $(".box7").width()) / 2 - 20 + "px",
//                        top: ($(window).height() - $(".box7").height()) / 2 + $(window).scrollTop() + "px",
//                        display: "block"
//                    });
//                });
                records = grid.getGridParam("records");
                if (records == 0) {
                    return false;
                }
                var graduateIds = jQuery("#table1").jqGrid('getDataIDs');
                for (var i = 0, l = graduateIds.length; i < l; i++) {
                    var rowId = graduateIds[i];
                    var rank = grid.jqGrid("getCell", rowId, "currentRank").trims();//当前排名
                    var bidRule = grid.jqGrid("getCell", rowId, "ruleDesc");//设置竞价规则
                    var bidStatus = grid.jqGrid("getCell", rowId, "biddingStatus");//竞价状态
                    var matchType = grid.jqGrid("getCell", rowId, "matchType");//匹配模式
                    var phraseType = grid.jqGrid("getCell", rowId, "phraseType");//高级短语匹配模式
                    var statusStr = grid.jqGrid('getCell', rowId, "statusStr");//关键词状态
                    if (statusStr == "未同步") {
                        $("#table1").setCell(rowId, 0, ' ');
                    }
                    if (rank == 0) {
                        $("#table1").setCell(rowId, "currentRank", "查看当前排名");
                    }
                    if (bidRule.length == 0) {
                        $("#table1").setCell(rowId, "ruleDesc", "添加规则");
                    }
                    if (bidStatus == 0 && bidRule.length > 0) {
                        $("#table1").setCell(rowId, "biddingStatus", "已暂停");
                    } else if (bidStatus == 1) {
                        if (bidRule.length == 0) {
                            $("#table1").setCell(rowId, "biddingStatus", "无");
                        } else {
                            $("#table1").setCell(rowId, "biddingStatus", "已启动");
                        }
                    } else {
                        $("#table1").setCell(rowId, "biddingStatus", "无");
                    }
                    if (matchType == 1) {
                        $("#table1").setCell(rowId, "matchType", "精确匹配");
                    } else if (matchType == 2) {
                        if (phraseType == 1) {
                            $("#table1").setCell(rowId, "matchType", "同义包含");
                        } else if (phraseType == 2) {
                            $("#table1").setCell(rowId, "matchType", "精确包含");
                        } else if (phraseType == 3) {
                            $("#table1").setCell(rowId, "matchType", "核心包含");
                        } else {
                            $("#table1").setCell(rowId, "matchType", "高级短语匹配");
                        }
                    } else if (matchType == 3) {
                        $("#table1").setCell(rowId, "matchType", "广泛匹配");
                    }
                }

                $("#pagination1").pagination(records, getOptionsFromForm(pageIndex));
                _biddingStatus = null;
                _statusStr = null;
            }
        });
        grid2 = $("#table2").jqGrid({
            datatype: "json",
            url: dataUrl2,
            mtype: "POST",
            jsonReader: {
                root: "rows",
                records: "records",
                repeatitems: false
            },
            height: 500,
            shrinkToFit: true,//此选项用于根据width计算每列宽度的算法,默认值true
            colModel: [
                // {label: '<input type=\"checkbox\" name=\"check_all\" onclick=\"checkAll();\" id=\"check_all\" >', name: 'checkall', width: 30,
                //sortable: false, align: 'center', formatter:function(v,x,r){ return "<input type='checkbox'/>"; }},
                {label: ' 关键词ID', name: 'keywordId', sortable: false, align: 'center', hidden: true},
                {label: ' 关键词', name: 'keyword', sortable: false, align: 'center'},
                {label: ' 推广计划', name: 'campaignName', sortable: false, align: 'center', hidden: true},
                {label: ' 推广单元', name: 'adgroupName', sortable: false, align: 'center', hidden: true},
                {label: ' 匹配模式', name: 'matchType', sortable: false, align: 'center', hidden: true},
                {label: ' 高级短语匹配模式', name: 'phraseType', sortable: false, align: 'center', hidden: true},
                {label: ' 当前排名', name: 'currentRank', sortable: false, align: 'center'},
                {label: ' 消费', name: 'cost', sortable: false, align: 'center'},
                {label: ' 展现量', name: 'impression', sortable: false, align: 'center'},
                {label: ' 点击量', name: 'click', sortable: false, align: 'center', hidden: true},
                {label: ' 点击率', name: 'ctr', sortable: false, align: 'center'},
                {label: ' 出价', name: 'price', sortable: false, align: 'center'},
                {label: ' 平均点击价格', name: 'cpc', sortable: false, align: 'center', hidden: true},
                {label: ' 千次展现消费', name: 'cpm', sortable: false, align: 'center', hidden: true},
                {label: ' 质量度', name: 'pcQuality', sortable: false, align: 'center'},
                {label: ' 移动端质量度', name: 'mQuality', sortable: false, align: 'center'},
                {label: ' 状态', name: 'statusStr', sortable: false, align: 'center'},
                {label: ' 竞价规则', name: 'ruleDesc', sortable: false, align: 'center'},
                {
                    label: ' Pc URL',
                    name: 'pcDestinationUrl',
                    sortable: false,
                    width: 200,
                    align: 'center',
                    formatter: 'link'
                },
                {
                    label: ' Mobile URL',
                    name: 'mobileDestinationUrl',
                    sortable: false,
                    align: 'center',
                    formatter: 'link'
                },
                {label: ' 竞价状态', name: 'biddingStatus', sortable: false, align: 'center'},
                {label: ' 是否设置了rule', name: 'rule', sortable: false, align: 'center', hidden: true},
                {label: ' adgroupId', name: 'adgroupId', sortable: false, align: 'center', hidden: true}
            ],

            rowNum: 20,// 默认每页显示记录条数
            rownumbers: false,
            loadui: 'disable',
            pgbuttons: false,
            autowidth: true,
            altRows: true,
            altclass: 'list2_box2',
            resizable: true,
            scroll: false,
            multiselect: true,
            beforeRequest: function () {
            },
            beforeSelectRow: function (rowId, event) {
                var $myGrid = $(this),
                        iCol = $.jgrid.getCellIndex($(event.target).closest('td')[0]),
                        cm = $myGrid.jqGrid('getGridParam', 'colModel');
                if (cm[iCol].name === "cb") {
                    return true;
                }

                var ruleFlag = $("#table2").jqGrid('getCell', rowId, "rule"); //true, 已经设置竞价规则
                var keywordId = $("#table2").jqGrid('getCell', rowId, "keywordId");
                if (iCol === 7) {//查看当前排名
                    getRank(keywordId);
                }
                else if (iCol === 18 && ruleFlag == "false") {//设置竞价规则
                    $(".TB_overlayBG").css({
                        display: "block", height: $(document).height()
                    });
                    $(".box").css({
                        left: ($("body").width() - $(".box").width()) / 2 - 20 + "px",
                        top: ($(window).height() - $(".box").height()) / 2 + $(window).scrollTop() + "px",
                        display: "block"
                    });
                }

                return false;
            },
            onSelectRow: function (rowId, status, event) {
//            $("table1").jqGrid('setSelection', rowId);
            },
            onCellSelect: function (rowId, index, contents, event) {
            },

            loadComplete: function () {
//            alert($("#table1").jqGrid("getRowData").length);
            },

            gridComplete: function () {
//            alert(JSON.stringify($("#table1").jqGrid("getRowData")));
                records2 = grid2.getGridParam("records");
                if (records2 == 0) {
                    return false;
                }
                var graduateIds = jQuery("#table2").jqGrid('getDataIDs');
                for (var i = 0, l = graduateIds.length; i < l; i++) {
                    var rowId = graduateIds[i];
                    var rank = grid2.jqGrid("getCell", rowId, "currentRank").trims();//当前排名
                    var bidRule = grid2.jqGrid("getCell", rowId, "ruleDesc");//设置竞价规则
                    var bidStatus = grid2.jqGrid("getCell", rowId, "biddingStatus");//竞价状态
                    var matchType = grid2.jqGrid("getCell", rowId, "matchType");//匹配模式
                    var phraseType = grid2.jqGrid("getCell", rowId, "phraseType");//高级短语匹配模式
                    if (rank == 0) {
                        $("#table2").setCell(rowId, "currentRank", "查看当前排名");
                    }
                    if (bidRule.length == 0) {
                        $("#table2").setCell(rowId, "ruleDesc", "添加规则");
                    }
                    if (bidStatus == 0 && bidRule.length > 0) {
                        $("#table2").setCell(rowId, "biddingStatus", "已暂停");
                    } else if (bidStatus == 1) {
                        $("#table2").setCell(rowId, "biddingStatus", "已启动");
                    } else {
                        $("#table2").setCell(rowId, "biddingStatus", "无");
                    }
                    if (matchType == 1) {
                        $("#table2").setCell(rowId, "matchType", "精确匹配");
                    } else if (matchType == 2) {
                        if (phraseType == 1) {
                            $("#table2").setCell(rowId, "matchType", "同义包含");
                        } else if (phraseType == 2) {
                            $("#table2").setCell(rowId, "matchType", "精确包含");
                        } else if (phraseType == 3) {
                            $("#table2").setCell(rowId, "matchType", "核心包含");
                        } else {
                            $("#table2").setCell(rowId, "matchType", "高级短语匹配");
                        }
                    } else if (matchType == 3) {
                        $("#table2").setCell(rowId, "matchType", "广泛匹配");
                    }
                }

                $("#pagination2").pagination(records2, getOptionsFromForm(pageIndex));
            }
        });
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
        var boxes = $("input[name='subbox'][checked]");
        var ids = [];
        $(boxes).each(function () {
            ids.push($(this).val())
        })

    }

    function getRank(keywordId) {
//    var kwid = $(obj).attr("data-id");
        $.ajax({
            url: "/bidding/rank/" + keywordId,
            async: false,
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                $("#rankTable tbody tr").empty();
                if (data.rows == undefined) {
//                    alert("暂无当前排名信息!");
                    AlertPrompt.show("暂无当前排名信息!");
                } else if (data.rows.length == 0) {
//                    alert("暂无当前排名信息!");
                    AlertPrompt.show("暂无当前排名信息!");
                } else {
                    var result = data.rows;
                    var _class = "";
                    $.each(result, function (i, item) {
                        if (i % 2 == 0) {
                            _class = "list2_box1";
                        } else {
                            _class = "list2_box2";
                        }
                        var rank = item.rank;
                        var rankStr = "";
                        if (rank == 0) {
                            return true;
                        }
                        if (rank < 0) {
                            rankStr = "右侧" + (-rank) + "名";
                        } else {
                            rankStr = "左侧" + rank + "名";
                        }
                        var tr = "<tr class='" + _class + "'><td>" + item.regionName + "</td><td>" + rankStr + "</td></tr>";
                        $("#rankTable tbody").append(tr);

                    });
                    $(".TB_overlayBG").css({
                        display: "block", height: $(document).height()
                    });

                    $("#paiming").css({
                        left: ($("body").width() - $(".box8").width()) / 2 - 20 + "px",
                        top: ($(window).height() - $(".box8").height()) / 2 + $(window).scrollTop() + "px",
                        display: "block"
                    });
                }
            }
        });


    }

    function addRule(obj) {
        $.kwid = $(obj).data('id');

        $(".TB_overlayBG").css({
            display: "block", height: $(document).height()
        });
        $(".box").css({
            left: ($("body").width() - $(".box").width()) / 2 - 20 + "px",
            top: ($(window).height() - $(".box").height()) / 2 + $(window).scrollTop() + "px",
            display: "block"
        });
    }

    $('.getRankBtn').click(function () {
        var id = $(this).data('id');
        $.ajax({
            url: "/bidding/rank/" + id,
            type: "GET",
            success: function (datas) {
                var data = datas.rows;
                if (data == null) {
//                    alert("暂无排名信息,请刷新排名!");
                    AlertPrompt.show("暂无排名信息,请刷新排名!");
                    return false;
                }
                var msg = "当前排名获取时间: " + data.time + "\n";
                msg = msg + "关键词: " + data.name + "\n";
                msg = msg + "地域\t排名\n";
                if (data.length == 0) {
                    msg = msg + "暂无排名";
                } else {
                    for (var i = 0, l = data.length; i < l; i++) {
                        msg = msg + data[i].region + "\t" + data[i].rank + "\n";
                    }
                }
//                alert(msg);
                AlertPrompt.show(msg);
            }
        })
    });
    /********checked**********/
    //var inutstyle = $("input[name='keywordQuality'],input[name='fullmatch'],input[name='columns'],input[name='matchType']");
    //$(function () {
    //    inutstyle.wrap('<div class="check-box checkedBox"><i></i></div>');
    //    $('input[type="radio"]').wrap('<div class="radio-btn"><i></i></div>');
    //    $('input[checked="checked"]').parents("i").parents(".radio-btn").addClass('checkedRadio');
    //    $(".radio-btn").on('click', function () {
    //        var _this = $(this),
    //                block = _this.parent().parent();
    //        block.find(".radio-btn").removeClass('checkedRadio');
    //        block.find(".radio-btn").find("input:radio").removeAttr("checked");
    //        _this.addClass('checkedRadio');
    //        _this.find('input:radio').attr('checked', true);
    //    });
    //    $.fn.toggleCheckbox = function () {
    //        this.attr('checked', !this.attr('checked'));
    //    };
    //    $('.check-box').on('click', function () {
    //        $(this).find(':checkbox').toggleCheckbox();
    //        $(this).toggleClass('checkedBox');
    //        $(this).removeAttr("checked");
    //    });
    //
    //    $("input[name=matchType]:checkbox").each(function (i, item) {
    //        if (!item.checked) {
    //            $(item).parent().parent().removeClass('checkedBox');
    //        }
    //    });
    //
    //})


</script>
</body>
</html>
