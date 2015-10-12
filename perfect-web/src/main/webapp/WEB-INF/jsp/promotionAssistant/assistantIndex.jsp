<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 14-8-20
  Time: 上午11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10">
    <title>大数据智能营销</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/table/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/css/accountCss/assistantStyle.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/media.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui-dialog.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/plugs/jQuery-smartMenu/smartMenu.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/css/table/bootstrap-responsive.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/css/pagination/pagination.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/grid/ui.jqgrid.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/index.css">
    <style rel="stylesheet" type="text/css">
        #sDiv .span-error {
            color: red;
        }

        #sDiv .span-ok {
            color: #9f9f9f;
        }

        form {
            margin: 0 0 5px 0;
        }

        .footer {
            position: absolute;
            background: #fff;
        }

        * {
            box-sizing: border-box;
        }

        *:before, *:after {
            box-sizing: border-box;
        }

        *:before, *:after {
            box-sizing: border-box;
        }

        .mainlist ul li input {
            height: 30px;
        }

    </style>
    <script>
        String.prototype.trims = function () {
            return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
        };

    </script>
</head>
<body>
<div id="background" class="background"></div>
<div id="progressBar" class="progressBar">数据加载中，请稍等...</div>
<jsp:include page="../homePage/pageBlock/head.jsp"/>
<jsp:include page="../promotionAssistant/alert/adgroupAlert.jsp"/>
<jsp:include page="../promotionAssistant/alert/addkeyword.jsp"/>
<div class="concent over">
<jsp:include page="../homePage/pageBlock/nav.jsp"/>
<div class="mid fr over">
<div class="title_box">
    <div class="on_title over">
        <a href="#"> 推广助手</a>
    </div>
</div>
<div id="tab">
<div class=" jiangjia_concent over">
<div class="jingjia_right assistant_right fl over">
<div ID="testIframe" Name="testIframe" width="100%" onLoad="iFrameHeight()">
<div class="content_wraps over">
<div class="zhushou over">
<div class="zhushou_menu">
    <ul class="zs_nav">
        <li class="showbox"><a id="downloadAccountData"><span><img
                src="../public/img/dowland.png"></span><span>下载账户</span></a></li>
        <li onclick="uploadDialog()"><a><span><img
                src="../public/img/update.png"></span><span>上传更新</span></a></li>
        <li class="current"><a href="javascript:void(0)"
                               onclick="alert('该功能还在开发中!');"><span><img
                src="../public/img/Advanced_search.png"></span><span>高级搜索</span></a>
        </li>
        <li class="nav_menu"><a href="javascript:void(0)" onclick="alert('该功能还在开发中!');"><span><img
                src="../public/img/Repeat_keyword.png"></span><span>重复关键词</span></a>
            <%--<ul>--%>
            <%--<li class="showbox3 current">重复关键词</li>--%>
            <%--<li>设置</li>--%>
            <%--</ul>--%>
        </li>
        <li class="showbox4"><a href="javascript:void(0)" onclick="alert('该功能还在开发中!');"> <span><img
                src="../public/img/Estimate.png"></span><span>估算工具</span></a></li>
    </ul>
</div>
<div class="zhushou_concent over" id="jiangkong_box2">
<div class="zs_concent_top over">
    <ul class="zh_menu2 fl" id="tabMenu">
        <li class="current" cname="table1">关键词</li>
        <li cname="table2">普通创意</li>
        <li cname="table3">附加创意</li>
        <li cname="table4">推广单元</li>
        <li cname="table5">推广计划</li>
        <li id="limenuClick">账户</li>
    </ul>
</div>
<div class="zs_line"></div>
<div class="zs_box over">
<!--关键词-->
<div class="containers">
    <div class="zs_function">
        <ul class="fl">
            <li><a id="addKeyword" href="#"><span class="zs_top"><img
                    src="../public/img/zs_function1.png"></span><b>添加</b></a>
            </li>
            <li><a href="javascript:deleteKwd()"><span class="zs_top"><img
                    src="../public/img/zs_function2.png"></span><b>删除</b></a>
            </li>
            <li><a id="addOrUpdateKwd" href="#"><span class="zs_top"><img
                    src="../public/img/zs_function3.png"></span><b>批量添加/更新&nbsp;</b></a><img id="deletekeywords"
                                                                                             src="../public/img/zs_input.png">
                <ul id="deletekeywordes" class="hides">
                    <li id="batchDelKwd"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function3.png"></span><b>批量删除</b></a>
                    </li>
                </ul>
            </li>
            <li><a id="search_keyword" href="#"><span class="zs_top"><img
                    src="../public/img/zs_function5.png"></span><b>搜索</b></a>
            </li>
            <li onclick="commons.foRShow('keyword',this)"><a href="javascript:;"
                    ><span class="z_function_hover"><img
                    src="../public/img/zs_function7.png"></span><b>编辑</b><img src="../public/img/zs_input.png"></a>
                <ul class="hide">
                    <li><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function14.png"></span><b>剪切</b></a></li>
                    <li onclick="Copy()"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function13.png"></span><b>复制</b></a></li>
                    <li onclick="Paste()"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function15.png"></span><b>粘贴</b></a></li>
                    <li class="Textreplacement"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function_text.png"></span><b>文字替换</b></a></li>
                </ul>
            </li>
            <li><a href="#"><span class="z_function_hover"><img
                    src="../public/img/zs_function6.png"></span><b>分析</b></a>
            </li>
            <li><a href="#"><span class="z_function_hover"><img
                    src="../public/img/zs_function7.png"></span><b>估算</b></a>
            </li>
            <li><a href="javascript:void(0)" onclick="MonitorDialog()"><span
                    class="zs_top"><img
                    src="../public/img/zs_function8.png"></span><b>监控</b></a>
            </li>
            <li><a href="#" id="reduction"><span class="z_function_hover"><img
                    src="../public/img/zs_function9.png"></span><b>还原</b></a><%--z_function_hover--%>
            </li>
            <li><a href="#" class="searchwordReport"><span class="zs_top"><img
                    src="../public/img/zs_function10.png"></span><b>搜索词</b></a>
            </li>
            <li><a href="#"><span class="z_function_hover"><img
                    src="../public/img/zs_function11.png"></span><b>激活</b></a>
            </li>
            <li><a href="javascript:void(0)" onclick="addCensus()"><span
                    class="zs_top"><img
                    src="../public/img/zs_function4.png"></span><b>添加统计代码</b></a>
            </li>
        </ul>
    </div>
    <div class="list4" id="kkeyword" style="height:400px;">
        <div class="container">
            <table border="0" cellspacing="0" width="100%"
                   class="table1 table-bordered"
                   data-resizable-columns-id="demo-table">
                <thead>
                <tr class="list02_top">
                    <th><input type="checkbox" name='keyAllCheck'
                               onchange="$.foRCheckAll('keyAllCheck')"/></th>
                    <th>&nbsp;关键词名称</th>
                    <th>&nbsp;关键词状态</th>
                    <th>&nbsp;启动/暂停</th>
                    <th>&nbsp;出价</th>
                    <th>&nbsp;计算机质量度</th>
                    <th>&nbsp;移动质量度</th>
                    <th>&nbsp;匹配模式</th>
                    <th>&nbsp;访问URL</th>
                    <th>&nbsp;移动访问URL</th>
                    <th class="username-column" data-noresize>&nbsp;推广计划名称</th>
                    <th class="username-column" data-noresize>&nbsp;
                        <div class="set fr"></div>
                    </th>
                </tr>
                </thead>
                <tbody id="tbodyClick" onmousedown="CtrlA()"
                       onclick="CtrlCancel();">
                </tbody>
            </table>
        </div>
    </div>
    <div class="zhanghu_input"></div>
    <div id="pagination_keywordPage" class="pagination"></div>
    <div class="zs_bottom over">
        <input type="hidden" id="hiddenkwid_1"/>

        <div class="zs_bottom1 over fl ">
            <ul>
                <li>
                    <div class="t_list01 fl over">关键词名称：</div>
                    <div class="t_list02 fl over"><input type="text"
                                                         disabled="disabled"
                                                         class="zs_input1 keyword_1">
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">出价：</div>
                    <div class="t_list02 fl over"><input type="text"
                                                         onblur="whenBlurEditKeyword(2,this.value)"
                                                         onkeydown="missBlur(event,this)"
                                                         class="zs_input1 price_1"
                                                         maxlength="5"
                                                         onkeypress='until.regDouble(this)'>
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">访问URL:</div>
                    <div class="t_list02 fl over"><input type="text"
                                                         onblur="whenBlurEditKeyword(3,this.value)"
                                                         onkeydown="missBlur(event,this)"
                                                         class="zs_input1 pcurl_1"
                                                         maxlength="1024"><span
                            class="pcurlSize_1">0/1024</span></div>
                </li>
                <li>
                    <div class="t_list01 fl over">移动访问URL：</div>
                    <div class="t_list02 fl over"><input type="text"
                                                         onblur="whenBlurEditKeyword(4,this.value)"
                                                         onkeydown="missBlur(event,this)"
                                                         class="zs_input1 mourl_1"
                                                         maxlength="1024"><span
                            class="mourlSize_1">0/1024</span></div>
                </li>
            </ul>
        </div>
        <div class="zs_bottom2 over fr">
            <ul>
                <li>
                    <div class="t_list01 fl over">所属监控文件夹：</div>
                    <div class="w_list02 fl over"><em
                            id="genusFolderCount">0个</em></div>
                </li>
                <li>
                    <div class="t_list01 fl over">匹配模式：</div>
                    <div class="w_list02 fl over"><select id="match_1"
                                                          onchange="whenBlurEditKeyword(5,this.value)">
                        <option value="-1">请选择匹配模式</option>
                        <option value="3">广泛</option>
                        <option value="1">精确</option>
                        <option value="2">短语</option>
                    </select></div>
                </li>
                <li id="phraseTypeLi" style="display: none;">
                    <div class="t_list01 fl over">高级匹配模式：</div>
                    <div class="w_list02 fl over"><select id="match_2"
                                                          onchange="whenBlurEditKeyword(6,this.value)">
                        <option value="-1">请选择高级匹配模式</option>
                        <option value="1">同义包含</option>
                        <option value="2">精确包含</option>
                        <option value="3">核心包含</option>
                    </select></div>
                </li>
                <li>
                    <div class="t_list01 fl over">状态：</div>
                    <div class="w_list02 fl over"><b class="status_1"></b></div>
                </li>
                <li>
                    <div class="t_list01 fl over">启用/暂停：</div>
                    <div class="w_list02 fl over"><select class="pause_1"
                                                          onchange="whenBlurEditKeyword(7,this.value)">
                    </select></div>
                </li>
                <li>
                    <div class="t_list01 fl over">您的注册域名为：</div>
                    <div class="w_list02 fl over"><span class="doMainS"
                                                        style="color:red;"></span>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--普通创意-->
<div class="containers hides">
    <div class="zs_function">
        <ul class="fl">
            <li><a href="javascript:void(0)" onclick="addCreative();"><span
                    class="zs_top"><img
                    src="../public/img/zs_function1.png"></span><b>添加</b></a>
            </li>
            <li><a href="javascript:void(0)" onclick="deleteByObjectId()"><span
                    class="zs_top"><img
                    src="../public/img/zs_function2.png"></span><b>删除</b></a>
            </li>
            <%--      <li><a href="javascript:void(0)" onclick="updateCreatvie()"><span
                          class="zs_top"><img
                          src="../public/img/zs_function7.png"></span><b>编辑</b></a>
                  </li>--%>
            <%--   <li><a href="javascript:;"
                      onclick="commons.foRShow('creative')"><span
                       class="z_function_hover"><img
                       src="../public/img/zs_function6.png"></span><b>文字(替换/查找)</b></a>
               </li>--%>
            <li onclick="commons.foRShow('creative',this)"><a href="javascript:;" class="editer"
                    ><span class="z_function_hover"><img
                    src="../public/img/zs_function7.png"></span><b>编辑</b><img src="../public/img/zs_input.png"></a>
                <ul class="hide">
                    <li><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function14.png"></span><b>剪切</b></a></li>
                    <li onclick="Copy()"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function13.png"></span><b>复制</b></a></li>
                    <li onclick="Paste()"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function15.png"></span><b>粘贴</b></a></li>
                    <li onclick="updateCreatvie()"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function15.png"></span><b>修改创意</b></a></li>
                    <li class="Textreplacement"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function_text.png"></span><b>文字替换</b></a></li>
                </ul>
            </li>
            <li><a href="#"><span class="zs_top"> <img
                    src="../public/img/zs_function3.png"></span><b
                    onclick="creativeMulti();">批量添加/更新</b></a></li>
            <li><a href="#"><span class="z_function_hover" id="reBak"
                                  onclick="reBakClick();"><img
                    src="../public/img/zs_function9.png"></span><b
                    onclick="reBakClick();">还原</b></a></li>
            <li><a href="javascript:void(0)" class="searchwordReport"><span
                    class="zs_top"><img
                    src="../public/img/zs_function10.png"></span><b>搜索词</b></a>
            </li>
            <li><a href="#"><span class="z_function_hover"><img
                    src="../public/img/zs_function11.png"></span><b>激活</b></a>
            </li>
        </ul>
    </div>
    <div class="list4" style="height:400px;" id="tcreative">
        <div class="container">
            <table border="0" cellspacing="0" width="100%" id="createTable"
                   class="table2 table-bordered"
                   data-resizable-columns-id="demo-table">
                <thead>
                <tr class="list02_top">
                    <th style="width: 40px;">&nbsp;操作</th>
                    <th><input type="checkbox" name='creativeAllCheck'
                               onchange="$.foRCheckAll('creativeAllCheck')"/></th>
                    <th>&nbsp;创意标题</th>
                    <th>&nbsp;创意描述1</th>
                    <th>&nbsp;创意描述2</th>
                    <th>&nbsp;默认访问URL</th>
                    <th>&nbsp;默认显示URL</th>
                    <th>&nbsp;移动访问URL</th>
                    <th>&nbsp;移动显示URL</th>
                    <th>&nbsp;启用/暂停</th>
                    <th>&nbsp;创意状态</th>
                    <th>&nbsp;设备偏好</th>
                    <th class="username-column" data-noresize>
                        <div class="set fr"></div>
                    </th>
                </tr>
                </thead>
                <tbody id="tbodyClick2" onmousedown="CtrlA()"
                       onclick="CtrlCancel();">
                </tbody>
            </table>
        </div>
    </div>
    <div class="zhanghu_input"></div>
    <div id="creativePager" class="pagination"></div>
    <div class="zs_bottom over" id="amsDiv">
        <div class="zs_bottom1 over fl " id="sDiv">
            <ul>
                <li>
                    <div class="t_list01 fl over">创意标题：</div>
                    <div class="t_list03 fl over">
                        <input type="text" class="zs_input1" id="sTitle"><span>49/50</span>
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">创意描述1：</div>
                    <div class="t_list03 fl over"><input type="text"
                                                         class="zs_input1"
                                                         id="sDes1"><span>79/80</span>
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">创意描述2：</div>
                    <div class="t_list03 fl over"><input type="text"
                                                         class="zs_input1"
                                                         id="sDes2"><span>79/80</span>
                    </div>
                </li>
                <li>
                    <div class="t_list04 fl over">
                        <div class="t_list01 fl over">默认访问URL：</div>
                        <div class="t_list05 fl over"><input type="text"
                                                             class="zs_input3"
                                                             id="sPc"/><span>0/1024</span>
                        </div>
                    </div>
                    <div class="t_list04 fr over">
                        <div class="t_list01 fl over">默认显示URL：</div>
                        <div class="t_list05 fl over"><input type="text"
                                                             class="zs_input3"
                                                             id="sPcs"/><span>35/36</span>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="t_list04 fl over">
                        <div class="t_list01 fl over">移动访问URL：</div>
                        <div class="t_list05 fl over"><input type="text"
                                                             class="zs_input3"
                                                             id="sMib"><span>49/1017</span>
                        </div>
                    </div>
                    <div class="t_list04 fr over">
                        <div class="t_list01 fl over">移动显示URL：</div>
                        <div class="t_list05 fl over"><input type="text"
                                                             class="zs_input3"
                                                             id="sMibs"><span>35/36</span>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">创意预览：</div>
                    <div class="w_list01 fl over" id="sPreview"></div>
                </li>
            </ul>
        </div>
        <div class="zs_bottom2 over fr" style="border:none;">
            <ul>
                <li>
                    <div class="t_list01 fl over">状态：</div>
                    <div class="w_list02 fl over"><b id="sStatus">有效</b></div>
                </li>
                <li>
                    <div class="t_list01 fl over">设备偏好：</div>
                    <div class="w_list02 fl over">
                        <select id="sD">
                            <option value="0">全部设备</option>
                            <option value="1">移动设备优先</option>
                        </select></div>
                </li>
                <li>
                    <div class="t_list01 fl over">启用/暂停：</div>
                    <div class="w_list02 fl over">
                        <select id="sPause">
                            <option value="true">启用</option>
                            <option value="false">暂停</option>
                        </select></div>
                </li>
                <li>
                    <div class="t_list01 fl over">您的注册域名为：</div>
                    <div class="w_list02 fl over"><span class="doMainS"
                                                        style="color:red;"></span>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--附加创意-->
<div class="containers  hides">
<div class="cy_menu over">
    <ul id="sublinkMenu">
        <li class="current">蹊径子链</li>
        <li>推广电话</li>
        <li>商桥移动质询</li>
    </ul>
</div>

<div class="list4" style="height:760px;" id="stb1">
    <div class="zs_function over">
        <ul class="fl">
            <li><a href="javascript:void(0)" onclick="addSublink();"><span
                    class="zs_top"><img
                    src="../public/img/zs_function1.png"></span><b>添加</b></a>
            </li>
            <li><a href="#"><span class="zs_top"><img
                    src="../public/img/zs_function2.png"></span><b>删除</b></a>
            </li>
            <li><a href="#"><span class="zs_top"> <img
                    src="../public/img/zs_function3.png"></span><b>批量添加/更新</b></a>
            </li>
            <li onclick="commons.foRShow(this)"><a href="javascript:;" class="editer"
                    ><span class="z_function_hover"><img
                    src="../public/img/zs_function7.png"></span><b>编辑</b><img src="../public/img/zs_input.png"></a>
                <ul class="hide">
                    <li><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function14.png"></span><b>剪切</b></a></li>
                    <li onclick="Copy()"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function13.png"></span><b>复制</b></a></li>
                    <li onclick="Paste()"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function15.png"></span><b>粘贴</b></a></li>
                    <li class="Textreplacement"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function_text.png"></span><b>文字替换</b></a></li>
                </ul>
            </li>
            <li><a href="#"><span class="z_function_hover"><img
                    src="../public/img/zs_function9.png"></span><b>还原</b></a>
            </li>
        </ul>
    </div>
    <div class="container gridcss ">
        <table id="subTable">
        </table>
        <div id="subPager"></div>
    </div>
    <div class="zs_bottom3 over g">
        <div class="zs_bottom1 over fl " style="width:100%; border:none;"
             name="subLinkReView">
            <span id="onePage"></span>
            <ul>
                <li>
                    <div class="cy_bottom1 fl over">
                        <span>子链一 名称</span>
                        <input type="text" class="zs_input3"
                               name="linkName1">
                    </div>
                    <div class="cy_bottom1 fl over">
                        <span>URL：</span><input type="text"
                                                class="zs_input3"
                                                name="linkUrl1">
                    </div>
                </li>
                <li>
                    <div class="cy_bottom1 fl over">
                        <span>子链二 名称</span>
                        <input type="text" class="zs_input3"
                               name="linkName2">
                    </div>
                    <div class="cy_bottom1 fl over">
                        <span>URL：</span><input type="text"
                                                class="zs_input3"
                                                name="linkUrl1">
                    </div>
                </li>
                <li>
                    <div class="cy_bottom1 fl over">
                        <span>子链三 名称</span>
                        <input type="text" class="zs_input3"
                               name="linkName3">
                    </div>
                    <div class="cy_bottom1 fl over">
                        <span>URL：</span><input type="text"
                                                class="zs_input3"
                                                name="linkUrl1">
                    </div>
                </li>
                <li>
                    <div class="cy_bottom1 fl over">
                        <span>子链四 名称</span>
                        <input type="text" class="zs_input3"
                               name="linkName4">
                    </div>
                    <div class="cy_bottom1 fl over">
                        <span>URL：</span><input type="text"
                                                class="zs_input3"
                                                name="linkUrl1">
                    </div>
                </li>
                <li id="liLink5">
                    <div class="cy_bottom1 fl over">
                        <span>子链五名称</span>
                        <input type="text" class="zs_input3"
                               name="linkName5">
                    </div>
                    <div class="cy_bottom1 fl over">
                        <span>URL：</span><input type="text"
                                                class="zs_input3"
                                                name="linkUrl1">
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <div class="zs_bottom over">
        <div class="zs_bottom1 over fl ">
            <div class="w_list01 fl over">关键词名称：</div>
            <div class="w_list04 fl over">
                <ul class="w_list05 over">
                    <li class="current">推广位 <span>|</span><span
                            class="list06_point"></span></li>
                    <li>推广链接位</li>
                </ul>
                <div class="w_list06 over">
                    <div class="list06_concent">
                        <div class="list06_textarea"
                             style="padding: 3px;"></div>
                    </div>
                    <div class="list06_concent hides">
                        <div class="list06_textarea"
                             style="padding: 3px;"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="zs_bottom2 over fr">
            <ul>
                <li>
                    <div class="t_list01 fl over"><span>状态:</span></div>
                    <div class="w_list02 fl over"><b></b></div>
                </li>
                <li>
                    <div class="t_list01 fl over"><span>设备偏好：</span></div>
                    <div class="w_list02 fl over"><select>
                        <option>全部</option>
                    </select></div>
                </li>
                <li>
                    <div class="t_list01 fl over"><span>启用/暂停：</span></div>
                    <div class="w_list02 fl over"><select>
                        <option>启用</option>
                    </select></div>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="list4" style="height:358px;display:none;" id="stb2">
    <div class="zs_function over">
        <ul class="fl">
            <li><a href="#"><span class="zs_top"><img
                    src="../public/img/zs_function1.png"></span><b>添加</b></a>
            </li>
            <li><a href="#"><span class="zs_top"><img
                    src="../public/img/zs_function2.png"></span><b>删除</b></a>
            </li>
            <li><a href="#"><span class="zs_top"> <img
                    src="../public/img/zs_function3.png"></span><b>批量添加/更新</b></a>
            </li>
            <li><a href="#"><span class="zs_top"><img
                    src="../public/img/zs_function4.png"></span><b>编辑&nbsp;<input
                    type="image" src="../public/img/zs_input.png"></b></a>
            </li>
            <li><a href="#"><span class="z_function_hover"><img
                    src="../public/img/zs_function9.png"></span><b>还原</b></a>
            </li>
        </ul>
    </div>
    <div class="container">
        <table border="0" cellspacing="0" width="100%"
               class="table3 table-bordered"
               data-resizable-columns-id="demo-table">
            <thead>
            <tr class="list02_top">
                <th>&nbsp;推广电话号码</th>
                <th>&nbsp;电话状态</th>
                <th>&nbsp;暂停/启用</th>
                <th>&nbsp;推广计划名称</th>
                <th>&nbsp;推广单元名称</th>
            </tr>
            </thead>
            <tbody>
            <tr class="list2_box2">
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="list4" style="height:358px;display:none;" id="stb3">
    <div class="container">
        <table border="0" cellspacing="0" width="100%"
               class="table3 table-bordered"
               data-resizable-columns-id="demo-table">
            <thead>
            <tr class="list02_top">
                <th>&nbsp;商桥移动资讯</th>
                <th>&nbsp;商桥状态</th>
                <th>&nbsp;暂停/启用</th>
                <th>&nbsp;推广计划名称</th>
                <th>&nbsp;推广单元名称</th>
            </tr>
            </thead>
            <tbody>
            <tr class="list2_box2">
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</div>
<!--推广单元-->
<div class="containers hides">
    <div class="zs_function">
        <ul class="fl">
            <li><a href="javascript:void(0)" onclick="addAdgroup()"><span
                    class="zs_top"><img
                    src="../public/img/zs_function1.png"></span><b>添加</b></a><span
                    style="display: none;" id="campBgt"></span></li>
            <li><a href="javascript:void(0)" onclick="adgroupDel()"><span
                    class="zs_top"><img
                    src="../public/img/zs_function2.png"></span><b>删除</b></a>
            </li>
            <li><a href="#"><span class="zs_top"><img
                    src="../public/img/zs_function3.png"></span><b
                    onclick="adgroupMutli();">批量添加/更新</b></a></li>
            <%--    <li><a href="javascript:;"
                       onclick="commons.foRShow('adgroup')"><span
                        class="z_function_hover"><img
                        src="../public/img/zs_function6.png"></span><b>文字(替换/查找)</b></a>
                </li>--%>
            <li onclick="commons.foRShow('adgroup',this)"><a href="javascript:;" class="editer"
                    ><span class="z_function_hover"><img
                    src="../public/img/zs_function7.png"></span><b>编辑</b><img src="../public/img/zs_input.png"></a>
                <ul class="hide">
                    <li><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function14.png"></span><b>剪切</b></a></li>
                    <li onclick="Copy()"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function13.png"></span><b>复制</b></a></li>
                    <li onclick="Paste()"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function15.png"></span><b>粘贴</b></a></li>
                    <li class="Textreplacement"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function_text.png"></span><b>文字替换</b></a></li>
                </ul>
            </li>
            <li><a href="#"><span class="z_function_hover" id="agReback"
                                  onclick="agreBakClick()"><img
                    src="../public/img/zs_function9.png"></span><b
                    onclick="agreBakClick()">还原</b></a></li>
            <li><a href="javascript:void(0)" class="searchwordReport"><span
                    class="zs_top"><img
                    src="../public/img/zs_function10.png"></span><b>搜索词</b></a>
            </li>
        </ul>
    </div>
    <div class="list4" style="height:400px;" id="aadgroup">
        <div class="container">
            <table border="0" cellspacing="0" width="100%" id="adGroupTable"
                   class="table4 table-bordered"
                   data-resizable-columns-id="demo-table">
                <thead>
                <tr class="list02_top">
                    <th>&nbsp;操作</th>
                    <th><input type="checkbox" name='adgroupAllCheck'
                               onchange="$.foRCheckAll('adgroupAllCheck')"/></th>
                    <th>&nbsp;推广单元名称</th>
                    <th>&nbsp;推广单元状态</th>
                    <th>&nbsp;启动/暂停</th>
                    <th>&nbsp;出价</th>
                    <th>&nbsp;否定关键词</th>
                    <th>&nbsp;推广计划名称</th>
                    <th class="username-column" data-noresize>
                        <div class="set fr"></div>
                    </th>
                </tr>
                </thead>
                <tbody id="tbodyClick_campaign" onmousedown="CtrlA()"
                       onclick="CtrlCancel();">
                <%--<tr class="list2_box3">--%>
                <%--<td>&nbsp;北京{婚博会}<span class="editor"></span></td>--%>
                <%--<td>&nbsp;有效<span class="editor"></span></td>--%>
                <%--<td>&nbsp;启用<span class="editor"></span></td>--%>
                <%--<td>&nbsp;一星<span class="editor"></span></td>--%>
                <%--<td>&nbsp;一星<span class="editor"></span></td>--%>
                <%--<td>&nbsp;短语-核心包含<span class="editor"></span></td>--%>
                <%--<td>&nbsp;<a href="#">http://tthunbohui.com </a><span class="editor"></span></td>--%>
                <%--</tr>--%>
                </tbody>
            </table>
        </div>
    </div>
    <div class="more_list over" style="display:none;">
        <ul>
            <li class="current"><span class="zs_top"><img
                    src="../public/img/zs_function1.png"></span><b>添加推广单元</b>
            </li>
            <li><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除推广单元</b>
            </li>
            <li><span class="zs_top"><img src="../public/img/zs_function12.png"></span><b>验证推广单元</b>
            </li>
            <li><span class="z_function_hover"><img
                    src="../public/img/zs_function9.png"></span><b>还原推广单元</b>
            </li>
            <li><span class="zs_top"><img src="../public/img/zs_function13.png"></span><b>复制</b>
            </li>
            <li><span class="zs_top"><img src="../public/img/zs_function14.png"></span><b>剪贴</b>
            </li>
            <li><span class="zs_top"><img src="../public/img/zs_function15.png"></span><b>粘贴</b>
            </li>
            <li><span class="zs_top"><img src="../public/img/zs_function16.png"></span><b>全选</b>
            </li>
        </ul>
    </div>
    <div class="zhanghu_input"></div>
    <div id="adgroupPager" class="pagination"></div>
    <div class="zs_bottom over" id="aDiv">
        <div class="zs_bottom1 over fl ">
            <ul>
                <li>
                    <div class="t_list01 fl over">名称：</div>
                    <div class="t_list02 fl over"><input type="text"
                                                         class="zs_input1"
                                                         disabled="disabled">
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">出价：</div>
                    <div class="t_list02 fl over"><input type="text"
                                                         class="zs_input1"
                                                         disabled="disabled">
                    </div>
                </li>
            </ul>
        </div>
        <div class="zs_bottom2 over fr">
            <ul>
                <li>
                    <div class="t_list01 fl over">状态：</div>
                    <div class="w_list02 fl over"><b id="apStatus">有效</b></div>
                </li>
                <li>
                    <div class="t_list01 fl over">启用/暂停：</div>
                    <div class="w_list02 fl over"><select id="apPause"
                                                          onchange="adgroupdSelectChange(this);">
                        <option value="true">启用</option>
                        <option value="false">暂停</option>
                    </select></div>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--推广计划-->
<div class="containers hides">
    <div class="zs_function ">
        <ul class="fl">
            <li><a href="#" id="addCampaign"><span class="zs_top"><img
                    src="../public/img/zs_function1.png"></span><b>添加</b></a>
            </li>
            <li><a href="#" id="quickAddplan"><span class="zs_top"><img
                    src="../public/img/zs_function17.png"></span><b>快速新建计划</b></a>
            </li>
            <li><a href="javascript:deleteCampaign();"><span class="zs_top"><img
                    src="../public/img/zs_function2.png"></span><b>删除</b></a>
            </li>
            <li onclick="commons.foRShow('campaign',this)"><a href="javascript:;" class="editer"
                    ><span class="z_function_hover"><img
                    src="../public/img/zs_function7.png"></span><b>编辑</b><img src="../public/img/zs_input.png"></a>
                <ul class="hide">
                    <li><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function14.png"></span><b>剪切</b></a></li>
                    <li onclick="Copy()"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function13.png"></span><b>复制</b></a></li>
                    <li onclick="Paste()"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function15.png"></span><b>粘贴</b></a></li>
                    <li class="Textreplacement"><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function_text.png"></span><b>文字替换</b></a></li>
                </ul>
            </li>
            <li><a href="#"><span class="zs_top"><img
                    src="../public/img/zs_function4.png"></span><b>编辑&nbsp;<input
                    type="image" src="../public/img/zs_input.png"></b></a></li>
            <li><a href="#" id="reduction_caipamgin"><span class="zs_top"><img
                    src="../public/img/zs_function9.png"></span><b>还原</b></a>
            </li>
            <li><a href="#" class="searchwordReport"><span class="zs_top"><img
                    src="../public/img/zs_function10.png"></span><b>搜索词</b></a>
            </li>
        </ul>
    </div>
    <div class="list4" id="ccampaign" style="height: 400px;">
        <div class="container">
            <table border="0" cellspacing="0" width="100%"
                   class="table5 table-bordered"
                   data-resizable-columns-id="demo-table">
                <thead>
                <tr class="list02_top">
                    <th>&nbsp;推广计划名称</th>
                    <th>&nbsp;推广计划状态</th>
                    <th>&nbsp;启用/暂停</th>
                    <th>&nbsp;每日预算</th>
                    <th>&nbsp;创意展现方式</th>
                    <th>&nbsp;动态创意状态</th>
                    <th>&nbsp;推广时段</th>
                    <th>&nbsp;推广地域</th>
                    <th>&nbsp;否定关键词</th>
                    <th>&nbsp;IP排除</th>
                    <th>&nbsp;到预算下线时间</th>
                    <th class="username-column" data-noresize
                        style="text-align:left; width:150px;"><span
                            class="fl"> </span>

                        <div class="set fr"></div>
                    </th>
                </tr>
                </thead>
                <tbody id="tbodyClick5" onmousedown="CtrlA()"
                       onclick="CtrlCancel();">
                </tbody>
            </table>
        </div>
    </div>
    <div class="zhanghu_input"></div>
    <div id="pagination_campaignPage" class="pagination"></div>
    <div class="zs_bottom over">
        <input type="hidden" id="hiddenCampaignId"/>

        <div class="zs_bottom2 over fl "
             style="border-right: 1px solid #e7e7e7;">
            <ul>
                <li>
                    <div class="t_list01 fl over">名称：</div>
                    <div class="t_list06 fl over"><input type="text"
                                                         onblur="whenBlurEditCampaign(1,this.value);"
                                                         onkeydown="missBlur(event,this);"
                                                         class="zs_input3 campaignName_5"
                                                         maxlength="30"></div>
                </li>
                <li>
                    <div class="t_list01 fl over">每日预算：</div>
                    <div class="t_list06 fl over"><input type="text"
                                                         onblur="whenBlurEditCampaign(2,this.value);"
                                                         onkeydown="missBlur(event,this);"
                                                         class="zs_input3 budget_5"
                                                         maxlength="5">
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over"><span
                            style="color:red;">提示：</span></div>
                    <div class="t_list06 fl over"><span>输入"&lt;不限定&gt;"则为计划不限定每日预算</span>
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">移动出价比例：</div>
                    <div class="t_list06 fl over"><input type="text"
                                                         onblur="whenBlurEditCampaign(3,this.value);"
                                                         onkeydown="missBlur(event,this);"
                                                         class="zs_input3 priceRatio_5"
                                                         maxlength="3"
                                                         onkeypress='until.regDouble(this)'>
                    </div>
                </li>
            </ul>
        </div>
        <div class="zs_bottom1 over fr" style="border-right:none;">
            <ul class="z_bottom3 fl">
                <li>
                    <div class="t_list01 fl over">推广时段：</div>
                    <div class="w_list02 fl over"><em class="schedule_5">全部</em>
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">推广地域：</div>
                    <div class="w_list02 fl over"><em class="regionTarget_5">使用账户推广地域</em>
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">动态创意状态：</div>
                    <div class="w_list02 fl over"><b
                            class="isDynamicCreative_5"></b></div>
                </li>
            </ul>
            <ul class="z_bottom3 fl">
                <li>
                    <div class="t_list01 fl over">否定关键词：</div>
                    <div class="w_list02 fl over "><em
                            class="negativeWords_5"></em></div>
                </li>
                <li>
                    <div class="t_list01 fl over">IP排除：</div>
                    <div class="w_list02 fl over"><em class="excluedIp_5"></em>
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">创意展现方式：</div>
                    <div class="w_list02 fl over">
                        <select class="selectShowProb_5"
                                onchange="whenBlurEditCampaign(10,this.value);"> </select>
                    </div>
                </li>
            </ul>
            <ul class="z_bottom3 fl">
                <li>
                    <div class="t_list01 fl over">状态：</div>
                    <div class="w_list02 fl over"><b class="status_5"></b></div>
                </li>
                <li>
                    <%--<div class="w_list01 fl over">IP排除：</div>
                    <div class="w_list02 fl over"><span> 0次</span></div>--%>
                    <div class="t_list01 fl over">达到预算下线：</div>
                    <div class="w_list02 fl over"><span
                            class="budgetOfflineTime_5"></span></div>
                </li>
                <li>
                    <div class="t_list01 fl over">启用/暂停：</div>
                    <div class="w_list02 fl over">
                        <select class="selectPause_5"
                                onchange="whenBlurEditCampaign(11,this.value);">
                        </select>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--账户-->
<div class="containers hides">
    <div class="zh_top over">
        <div class="fl">
            <span><b>baidu-bjtthunbohui2134115</b></span>
            <%--            <span><a href="#">还原帐户</a><input type="checkbox" checked="checkde"/>验证帐户</span>--%>
        </div>
        <div class="fr"><input type="image" src="../public/img/shuaxin.png">
        </div>
    </div>
    <div class="zh_list02 over">
        <ul>
            <li>
                <span>帐户余额：<b id="balance" class="blue">￥38678.1</b></span>
                <span>昨日消费：<b id="cost" class="blue">暂无数据</b></span>
                <span>消费升降：<b id="costStatus" class="blue">暂无数据</b></span>
                <span>动态创意:<a href="#" class="showbox7">开启</a></span>
            </li>
            <li>
                                                        <span>帐户预算：<b id="accountBudget">不限定</b><a href="#"
                                                                                                   class="showbox5">修改</a></span>
                <span>到达预算：<b id="reachBudget" class="blue">-</b></span>
                <span>IP排除：<a href="#" class="showbox6">设置</a></span>
            </li>
        </ul>
    </div>
    <div class="zh_list over">
        <div id="containerLegend" class="zs_function over">
        </div>
        <div id="container" style="height: 400px"></div>
    </div>
</div>
</div>
</div>
<div class="zhushou_concent over hides" id="jiangkong_box3">
    <div class="zs_concent_top over">
        <ul class="zh_menu2 fl">
            <li class="current" cname="table1">已监控关键词</li>
            <li cname="table2" id="jkwjj">监控文件夹</li>
        </ul>
    </div>
    <div class="zs_line"></div>

    <!--已监控关键词-->
    <%--<div class="containers  over">
        <div class="zs_function">
            <ul class="fl">
                <li><a  href="#"><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>停止</b></a></li>
                <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function10.png"></span><b>搜索词</b></a></li>
                <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function11.png"></span><b>激活&nbsp;<input type="image" src="../public/img/zs_input.png"></b></a> </li>
                <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function5.png"></span><b>搜索</b></a></li>
                <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function6.png"></span><b>分析</b></a></li>
                <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function7.png"></span><b>估算</b></a></li>
                <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function18.png"></span><b>下载同步</b></a></li>
                <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function19.png"></span><b>上传更新</b></a></li>
            </ul>
        </div>
        <div class="zs_line"></div>--%>
    <div class="zs_box over">
        <!--已监控关键词-->
        <div class="containers  over">
            <div class="zs_function">
                <ul class="fl">
                    <li><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function2.png"></span><b
                            id="removeMonitor">停止监控</b></a>
                    </li>
                    <li><a href="javascript:void(0);" onclick="searchword();"><span
                            class="zs_top"><img
                            src="../public/img/zs_function10.png"></span><b>搜索词</b></a>
                    </li>
                    <li><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function11.png"></span><b
                            id="activate">激活</b></a>
                    </li>
                    <li><a href="javascript:void(0);" onclick="showSearchWord();"><span
                            class="zs_top"><img
                            src="../public/img/zs_function5.png"></span><b>搜索</b></a>
                    </li>
                    <li><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function6.png"></span><b>分析</b></a>
                    </li>
                    <li><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function7.png"></span><b>估算</b></a>
                    </li>
                </ul>
            </div>
            <div class="list4">
                <div class="container">
                    <table border="0" cellspacing="0" width="100%"
                           class="table1 table-bordered"
                           data-resizable-columns-id="demo-table">
                        <thead>
                        <tr class="list02_top">
                            <th>&nbsp;关键词名称</th>
                            <th>&nbsp;推广计划名称</th>
                            <th>&nbsp;推广单元名称</th>
                            <th>&nbsp;关键词状态</th>
                            <th>&nbsp;启动/暂停</th>
                            <th>&nbsp;出价</th>
                            <th>&nbsp;计算机质量度</th>
                            <th>&nbsp;移动质量度</th>
                            <th>&nbsp;匹配模式</th>
                            <th>&nbsp;访问URL</th>
                            <th>&nbsp;移动访问URL</th>
                            <th class="username-column" data-noresize>&nbsp;监控文件夹
                                <div class="set fr"></div>
                            </th>
                        </tr>
                        </thead>
                        <tbody id="monitorFolder">
                        </tbody>
                    </table>
                    <input type="hidden" id="remoneMonitor">
                </div>
            </div>
            <div class="more_list over" style="display:none;">
                <ul>
                    <li class="current"><span class="zs_top"><img
                            src="../public/img/zs_function1.png"></span><b>添加关键词</b>
                    </li>
                    <li><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除关键词</b>
                    </li>
                    <li><span class="zs_top"><img src="../public/img/zs_function12.png"></span><b>验证关键词</b>
                    </li>
                    <li><span class="z_function_hover"><img
                            src="../public/img/zs_function9.png"></span><b>还原关键词</b>
                    </li>
                    <li><span class="zs_top"><img src="../public/img/zs_function13.png"></span><b>复制</b>
                    </li>
                    <li><span class="zs_top"><img src="../public/img/zs_function14.png"></span><b>剪贴</b>
                    </li>
                    <li><span class="zs_top"><img src="../public/img/zs_function15.png"></span><b>粘贴</b>
                    </li>
                    <li><span class="zs_top"><img src="../public/img/zs_function16.png"></span><b>全选</b>
                    </li>
                </ul>
            </div>
            <div class="zhanghu_input"></div>

            <div class="page kwdPage over">
                <ul>
                    <div>每页显示条数<select style="width:60px;" onchange="getKwdList()">
                        <option value="20">20</option>
                        <option value="40">40</option>
                        <option value="60">60</option>
                    </select></div>
                    <li><a href="#">首页</a></li>
                    <li><a href="#">上一页</a></li>
                    <li><a href="#">下一页</a></li>
                    <li><a href="#">尾页</a></li>
                    <li>当前页:1/0</li>
                    <li>共0条</li>
                    <li><input type="text" maxlength="10" class="inputNo kwdPageNo"/>&nbsp;<input
                            type="button"
                            value="GO"/></li>
                </ul>
            </div>
        </div>
        <!--监控文件夹-->
        <div class="containers  over hides">
            <div class="zs_function over">
                <ul class="fl">
                    <li><a href="javascript:void(0)" onclick="folderDialog();"><span
                            class="zs_top"><img
                            src="../public/img/zs_function1.png"></span><b>添加</b></a>
                    </li>
                    <li><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function2.png"></span><b
                            id="removeFolder">删除</b></a></li>
                    <li><a href="javascript:void(0);" onclick="searchword();"><span
                            class="zs_top"><img
                            src="../public/img/zs_function10.png"></span><b>搜索词</b></a>
                    </li>
                    <li id="downSync"><a href="javascript:void(0)"><span class="zs_top"><img
                            src="../public/img/zs_function18.png"></span><b>下载同步</b></a>
                    </li>
                    <li><a href="#"><span class="zs_top"><img
                            src="../public/img/zs_function19.png"></span><b>上传更新</b></a>
                    </li>
                </ul>
            </div>
            <div class="list4" style="height:400px;">
                <div class="container">
                    <table border="0" cellspacing="0" width="100%"
                           class="table2 table-bordered"
                           data-resizable-columns-id="demo-table">
                        <thead>
                        <tr class="list02_top">
                            <th>&nbsp;监控文件夹</th>
                            <th>&nbsp;监控文件夹内</th>
                            <th class="username-column" data-noresize>
                                <div class="set fr"></div>
                            </th>
                        </tr>
                        </thead>
                        <tbody id="MonitorTbody">
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="zhanghu_input"></div>
            <div class="zs_bottom over">
                <div class="zs_bottom1 over" style="width:100%;">
                    <ul>
                        <li>
                            <div class="t_list01 fl over" style="width:100px;">
                                监控关键词数量：
                            </div>
                            <div class="t_list02 fl over"><input type="text"
                                                                 class="zs_input1"
                                                                 id="count" readonly>
                            </div>
                        </li>
                        <li>
                            <div class="t_list01 fl over" style="width:100px;">
                                监控文件夹名称：
                            </div>
                            <div class="t_list02 fl over"><input type="text"
                                                                 class="zs_input1"
                                                                 id="folder" readonly>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
</div>
</div>
<div class="jingjia_left assistant_left fl over">
    <div class="j_l_top over">
        <span class="fl"><h3>账户目录</h3></span>
        <a href="javascript:loadTree()" class="fr">刷新</a>
    </div>
    <div class="j_l_top2 over">
        <span class="fl">查找计划单元</span>
        <input class="fr" type="image" src="../public/img/search.png">
    </div>
    <div class="j_list01 over">
        <div id="loading">
        </div>
        <ul id="zTree" class="ztree over">
        </ul>
    </div>
    <div class="j_l_under over">
        <a href="javascript:void(0)" class="jiangkong">监控文件夹</a>

        <div class="j_list02 hides" style="height:100px; background:#fff;overflow:auto;">
            <div id="jiangkong_box">监控文件夹</div>
            <ul id="zTree2" class="ztree over" style="height:100px;">
            </ul>
        </div>
    </div>
</div>

</div>
</div>
<jsp:include page="../homePage/pageBlock/footer.jsp"/>
</div>
</div>
<jsp:include page="../promotionAssistant/popup/popup.jsp"/>
<%--添加推广计划弹出窗口--%>
<jsp:include page="../promotionAssistant/alert/addCampaign.jsp"/>
<jsp:include page="alert/sublinkAdd.jsp"/>
<jsp:include page="../popup/commons/findOrReplace.jsp"/>
<!-- javascript -->
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/echarts/2.1.10/echarts-all.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/store.js/1.3.14/store.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/jqgrid/4.6.0/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/table/jquery.resizableColumns.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.livequery.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/plugs/jQuery-smartMenu/jquery-smartMenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/dialog.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/dialog-plus.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/json2/20140204/json2.min.js"></script>
<%--<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/4.0.1/highcharts.js"></script>--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/html.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/tc.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/untils/untils.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistant/creative.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/assistant/assistantKeyword.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistant/global.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/assistant/assistantAccount.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/assistant/assistantCampaign.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistant/adgroup.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/assistant/updateAccountData.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistant/addKeyword.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistant/Monitoring.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery.pin/1.0.1/jquery.pin.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/pagination/jquery.pagination.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/table/Indextable.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistant/uploadMerge.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/assistant/assistantsublink.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/commons/commons.js"></script>
<script type="text/javascript">
    $(function () {
        window.dialog = dialog;
    });
    $(function () {
        setTimeout("resizable('table1')", 300);
    });
    var resizable = function (cla) {
        $("." + cla).resizableColumns({
            store: store
        });
    };
    //loading
    var ajaxbg = $("#background,#progressBar");
    ajaxbg.hide();
    $("#downloadAccount").click(function () {
        $(document).ajaxStart(function () {
            ajaxbg.show();
        })
        $(document).ajaxStop(function () {
            ajaxbg.hide();
        });
    })
    function CtrlA() {
        $(document).keydown(function (event) {
            if (event.ctrlKey && event.keyCode == 65) {
                jQuery('#tbodyClick tr').css('background', '#fcefc5');
                jQuery('#tbodyClick2 tr').css('background', '#fcefc5');
                jQuery('#tbodyClick_campaign tr').css('background', '#fcefc5');
                jQuery('#tbodyClick5 tr').css('background', '#fcefc5');
                return false;
            }
            return true;
        });
    }
    function CtrlAll() {
        jQuery('#tbodyClick tr').css('background', '#fcefc5');
        jQuery('#tbodyClick2 tr').css('background', '#fcefc5');
        jQuery('#tbodyClick_campaign tr').css('background', '#fcefc5');
        jQuery('#tbodyClick5 tr').css('background', '#fcefc5');
    }
    function CtrlCancel() {
        jQuery('#tbodyClick tr').css('background', '')
        jQuery('#tbodyClick2 tr').css('background', '')
        jQuery('#tbodyClick_campaign tr').css('background', '')
        jQuery('#tbodyClick5 tr').css('background', '');
    }

</script>
</body>
</html>