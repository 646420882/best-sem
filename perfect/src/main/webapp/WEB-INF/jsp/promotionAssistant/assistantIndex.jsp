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
    <title>大数据智能营销</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/plugs/jQuery-smartMenu/smartMenu.css">
</head>
<body>
<jsp:include page="../homePage/pageBlock/head.jsp"/>
<div class="concent over">
<jsp:include page="../homePage/pageBlock/nav.jsp"/>

<div class="mid over fr">
<div class="on_title over">
    <a href="#">
        推广助手
    </a>
</div>
<div id="tab">
<div class="tab_box">
<div class=" jiangjia_concent over">
<div class="jingjia_left fl over">
    <div class="j_l_top over">
        <span class="fl"><h3>账户目录</h3></span>
        <a href="javascript:void(0)" onclick="loadCreativeData(sparams)" class="fr">刷新</a>
    </div>
    <div class="j_l_top2 over">
        <span class="fl">查找计划单元</span>
        <input class="fr" type="image" src="../public/img/search.png">
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
<div ID="testIframe" Name="testIframe" width="100%" onLoad="iFrameHeight()">
<div class="content_wraps over">
<div class="zhushou over wd ">
<div class="zhushou_menu wd">
    <ul class="zs_nav">
        <li class="showbox"><a><span><img src="../public/img/dowland.png"></span><span>下载账户</span></a></li>
        <li><a><span><img src="../public/img/update.png"></span><span>上传更新</span></a></li>
        <li class="current"><a><span><img src="../public/img/Advanced_search.png"></span><span>高级搜索</span></a></li>
        <li class="nav_menu"><a><span><img src="../public/img/Repeat_keyword.png"></span><span>重复关键词</span></a>
            <ul>
                <li class="showbox3 current">重复关键词</li>
                <li>设置</li>
            </ul>
        </li>
        <li class="showbox4"><a> <span><img src="../public/img/Estimate.png"></span><span>估算工具</span></a></li>
    </ul>
</div>
<div class="zhushou_concent over">
<div class="zs_concent_top over">
    <ul class="zh_menu2 fl">
        <li class="current">关键词</li>
        <li>普通创意</li>
        <li>附加创意</li>
        <li>推广单元</li>
        <li>推广计划</li>
        <li>账户</li>
    </ul>
</div>
<div class="zs_line"></div>
<div class="zs_box over">
<!--关键词-->
<div class="containers  over">
    <div class="zs_function over">
        <ul class="fl">
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加</b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除</b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function3.png"></span><b>批量添加/更新</b></a>
            </li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function4.png"></span><b>编辑&nbsp;<input
                    type="image" src="../public/img/zs_input.png"></b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function5.png"></span><b>搜索</b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function6.png"></span><b>分析</b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function7.png"></span><b>估算</b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function8.png"></span><b>监控</b></a></li>
            <li><a href="#"><span class="z_function_hover"><img
                    src="../public/img/zs_function9.png"></span><b>还原</b></a>
            </li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function10.png"></span><b>搜索词</b></a></li>
            <li><a href="#"><span class="z_function_hover"><img
                    src="../public/img/zs_function11.png"></span><b>激活</b></a>
            </li>
        </ul>
        <span class="fr">1/10</span>
    </div>
    <div class="list4">
        <table border="0" cellspacing="0" width="100%">
            <thead>
            <tr class="list02_top">
                <td>&nbsp;关键词名称</td>
                <td>&nbsp;关键词状态</td>
                <td>&nbsp;启动/暂停</td>
                <td>&nbsp;出价</td>
                <td>&nbsp;计算机质量度</td>
                <td>&nbsp;移动质量度</td>
                <td>&nbsp;匹配模式</td>
                <td>&nbsp;访问URL</td>
                <td>&nbsp;移动访问URL</td>
                <td>&nbsp;推广计划名称
                    <div class="set fr"></div>
                </td>
            </tr>
            </thead>
            <tbody id="tbodyClick">

            </tbody>
        </table>
    </div>
    <div class="more_list over" style="display:none;">
        <ul>
            <li class="current"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加关键词</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除关键词</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function12.png"></span><b>验证关键词</b></li>
            <li><span class="z_function_hover"><img src="../public/img/zs_function9.png"></span><b>还原关键词</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function13.png"></span><b>复制</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function14.png"></span><b>剪贴</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function15.png"></span><b>粘贴</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function16.png"></span><b>全选</b></li>
        </ul>
    </div>
    <div class="zhanghu_input"></div>
    <div class="zs_bottom over">
        <div class="zs_bottom1 over fl ">
            <ul>
                <li>
                    <div class="t_list01 fl over">关键词名称：</div>
                    <div class="t_list02 fl over"><input type="text" class="zs_input1"></div>
                </li>
                <li>
                    <div class="t_list01 fl over">出价：</div>
                    <div class="t_list02 fl over"><input type="text" class="zs_input1"></div>
                </li>
                <li>
                    <div class="t_list01 fl over">访问URL:</div>
                    <div class="t_list02 fl over"><input type="text" class="zs_input1"><span>59/1024</span></div>
                </li>
                <li>
                    <div class="t_list01 fl over">移动访问URL：</div>
                    <div class="t_list02 fl over"><input type="text" class="zs_input1"><span>59/1024</span></div>
                </li>
            </ul>
        </div>
        <div class="zs_bottom2 over fr">
            <ul>
                <li>
                    <div class="w_list01 fl over">所属监控文件夹：</div>
                    <div class="w_list02 fl over"><em>0个</em></div>
                </li>
                <li>
                    <div class="w_list01 fl over">匹配模式：</div>
                    <div class="w_list02 fl over"><em>短语-核心包含</em></div>
                </li>
                <li>
                    <div class="w_list01 fl over">状态：</div>
                    <div class="w_list02 fl over"><b>有效</b></div>
                </li>
                <li>
                    <div class="w_list01 fl over">设备偏好：</div>
                    <div class="w_list02 fl over"><select>
                        <option>全部</option>
                    </select></div>
                </li>
                <li>
                    <div class="w_list01 fl over">启用/暂停：</div>
                    <div class="w_list02 fl over"><select>
                        <option>启用</option>
                    </select></div>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--普通创意-->
<div class="containers  over hides">
    <div class="zs_function over">
        <ul class="fl">
            <li><a href="javascript:void(0)" onclick="addCreative();"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加</b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除</b></a></li>
            <li><a href="#"><span class="zs_top"> <img src="../public/img/zs_function3.png"></span><b>批量添加/更新</b></a>
            </li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function4.png"></span><b>编辑&nbsp;<input
                    type="image" src="../public/img/zs_input.png"></b></a></li>
            <li><a href="#"><span class="z_function_hover"><img
                    src="../public/img/zs_function9.png"></span><b>还原</b></a>
            </li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function10.png"></span><b>搜索词</b></a></li>
            <li><a href="#"><span class="z_function_hover"><img
                    src="../public/img/zs_function11.png"></span><b>激活</b></a>
            </li>

        </ul>
        <span class="fr">1/10</span>
    </div>
    <div class="list4">
        <table border="0" cellspacing="0" width="100%" id="createTable">
            <thead>
            <tr class="list02_top">
                <td>&nbsp;</td>
                <td>&nbsp;创意标题</td>
                <td>&nbsp;创意描述1</td>
                <td>&nbsp;创意描述2</td>
                <td>&nbsp;默认访问URL</td>
                <td>&nbsp;默认显示URL</td>
                <td>&nbsp;移动访问URL</td>
                <td>&nbsp;移动显示URL</td>
                <td>&nbsp;创意状态</td>
                <td>&nbsp;启用/暂停
                    <div class="set fr"></div>
                </td>
            </tr>
            </thead>
            <tbody id="tbodyClick2">
            <%--<tr class="list2_box3">--%>
            <%--<td>&nbsp;北京{婚博会}<span class="editor"></span></td>--%>
            <%--<td>&nbsp;有效<span class="editor"></span></td>--%>
            <%--<td>&nbsp;启用<span class="editor"></span></td>--%>
            <%--<td>&nbsp;一星<span class="editor"></span></td>--%>
            <%--<td>&nbsp;一星<span class="editor"></span></td>--%>
            <%--<td>&nbsp;短语-核心包含<span class="editor"></span></td>--%>
            <%--<td>&nbsp;<a href="#">http://tthunbohui.com </a><span class="editor"></span></td>--%>
            <%--<td>&nbsp;九星词<span class="editor"></span>	</td>--%>
            <%--</tr>--%>
            </tbody>
        </table>
        <div class="more_list over" style="display:none;">
            <ul>
                <li class="current" onclick="alert(123);"><span class="zs_top"><img
                        src="../public/img/zs_function1.png"></span><b>添加创意</b></li>
                <li><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除创意</b></li>
                <li><span class="zs_top"><img src="../public/img/zs_function12.png"></span><b>验证创意</b></li>
                <li><span class="z_function_hover"><img src="../public/img/zs_function9.png"></span><b>还原创意</b></li>
                <li><span class="zs_top"><img src="../public/img/zs_function13.png"></span><b>复制</b></li>
                <li><span class="zs_top"><img src="../public/img/zs_function14.png"></span><b>剪贴</b></li>
                <li><span class="zs_top"><img src="../public/img/zs_function15.png"></span><b>粘贴</b></li>
                <li><span class="zs_top"><img src="../public/img/zs_function16.png"></span><b>全选</b></li>
            </ul>
        </div>
    </div>
    <div class="zhanghu_input"></div>
    <div class="zs_bottom over">
        <div class="zs_bottom1 over fl " id="sDiv">
            <ul>
                <li>
                    <div class="t_list01 fl over">创意标题：</div>
                    <div class="t_list03 fl over">
                        <input type="text" class="zs_input1" id="sTitle"><input type="button" value="{}插入通配符" onclick="addTb(this)" class="zs_input2"><span><span
                            id="sTitle_size">49</span>/50</span>
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">创意描述1：</div>
                    <div class="t_list03 fl over">
                        <input type="text" class="zs_input1" id="sDes1"><input type="button" value="{}插入通配符"  class="zs_input2"><span><span
                            id="sDes1_size">49</span>/80</span>
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">创意描述2：</div>
                    <div class="t_list03 fl over">
                        <input type="text" class="zs_input1" id="sDes2"><input type="button" value="{}插入通配符" class="zs_input2"><span><span
                        id="sDes2_size">49</span>/80</span>
                    </div>
                </li>
                <li>
                    <div class="t_list04 fl over">
                        <div class="t_list01 fl over">默认访问URL：</div>
                        <div class="t_list02 fl over"><input type="text" class="zs_input3" id="sPc"/><span><span id="sPc_size">0</span>/1024</span>
                        </div>
                    </div>
                    <div class="t_list04 fr over">
                        <div class="t_list01 fl over">默认显示URL：</div>
                        <div class="t_list02 fl over"><input type="text" class="zs_input3" id="sPcs"/><span><span
                                id="sPcs_size">49</span>/50</span></div>
                    </div>
                </li>
                <li>
                    <div class="t_list04 fl over">
                        <div class="t_list01 fl over">移动访问URL：</div>
                        <div class="t_list02 fl over"><input type="text" class="zs_input3"
                                                             id="sMib"><span><span id="sMib_size">49</span>/1024</span></div>
                    </div>
                    <div class="t_list04 fr over">
                        <div class="t_list01 fl over">移动显示URL：</div>
                        <div class="t_list02 fl over"><input type="text" class="zs_input3"  id="sMibs"><span><span id="sMibs_size">35</span>/36</span></div>
                    </div>
                </li>
                <li>
                    <div class="t_list01 fl over">创意预览：</div>
                    <div class="t_list02 fl over" id="sPreview" style="width:557px;height: 98px;border:1px solid #CCC;"></div>
                </li>
            </ul>
        </div>
        <div class="zs_bottom2 over fr" style="border:none;">
            <ul>
                <li>
                    <div class="w_list01 fl over">状态：</div>
                    <div class="w_list02 fl over"><b>有效</b></div>
                </li>
                <li>
                    <div class="w_list01 fl over">设备偏好：</div>
                    <div class="w_list02 fl over"><select>
                        <option>全部</option>
                    </select></div>
                </li>
                <li>
                    <div class="w_list01 fl over">启用/暂停：</div>
                    <div class="w_list02 fl over"><select>
                        <option>启用</option>
                    </select></div>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--附加创意-->
<div class="containers  over hides">
<div class="cy_menu over">
    <ul>
        <li class="current">蹊径子链</li>
        <li>推广电话</li>
        <li>商桥移动质询</li>
    </ul>
</div>
<div class="zs_function over">
    <ul class="fl">
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加</b></a></li>

        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除</b></a></li>
        <li><a href="#"><span class="zs_top"> <img src="../public/img/zs_function3.png"></span><b>批量添加/更新</b></a></li>


        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function4.png"></span><b>编辑&nbsp;<input
                type="image" src="../public/img/zs_input.png"></b></a></li>

        <li><a href="#"><span class="z_function_hover"><img src="../public/img/zs_function9.png"></span><b>还原</b></a>
        </li>

    </ul>
    <span class="fr">1/10</span>
</div>
<div class="list4">
<table border="0" cellspacing="0" width="100%">
<thead>
<tr class="list02_top">
    <td>&nbsp;子链一名称</td>
    <td>&nbsp;子链一URL</td>
    <td>&nbsp;子链二名称</td>
    <td>&nbsp;子链二URL</td>
    <td>&nbsp;子链三名称</td>
    <td>&nbsp;子链三URL</td>
    <td>&nbsp;子链四名称</td>
    <td>&nbsp;子链四URL</td>
    <td>&nbsp;子链五名称</td>
    <td>&nbsp;子链五URL
        <div class="set fr"></div>
    </td>
</tr>
</thead>
<tbody id="tbodyClick4">
<tr class="list2_box3">
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
    <td>&nbsp;<span class="editor"></span></td>
</tr>
<tr class="list2_box2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tbody>
</table>
</div>
<div class="more_list over" style="display:none;">
    <ul>
        <li class="current"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加推广计划</b></li>
        <li><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除推广计划</b></li>
        <li><span class="zs_top"><img src="../public/img/zs_function12.png"></span><b>验证推广计划</b></li>
        <li><span class="z_function_hover"><img src="../public/img/zs_function9.png"></span><b>还原推广计划</b></li>
        <li><span class="zs_top"><img src="../public/img/zs_function13.png"></span><b>复制</b></li>
        <li><span class="zs_top"><img src="../public/img/zs_function15.png"></span><b>粘贴</b></li>
        <li><span class="zs_top"><img src="../public/img/zs_function16.png"></span><b>全选</b></li>
    </ul>
</div>
<div class="zhanghu_input"></div>
<div class="zs_bottom3 over">
    <div class="zs_bottom1 over fl " style="width:100%; border:none;">
        <ul>
            <li>
                <div class="cy_bottom1 fl over">
                    <span>子链一 名称 </span><input type="text" class="zs_input3">&nbsp;<span>0/16</span>
                </div>
                <div class="cy_bottom1 fl over">
                    <span>URL：</span><input type="text" class="zs_input3">&nbsp;<span>0/1024</span>
                </div>
            </li>
            <li>
                <div class="cy_bottom1 fl over">
                    <span>子链一 名称 </span><input type="text" class="zs_input3"> &nbsp;<span>0/16</span>
                </div>
                <div class="cy_bottom1 fl over">
                <span>URL：</span><input type="text" class="zs_input3">&nbsp;<span>0/1024</span>
                </div>
            </li>
            <li>
                <div class="cy_bottom1 fl over">
                  <span>子链一 名称 </span><input type="text" class="zs_input3">&nbsp;<span>0/16</span>
                </div>
                <div class="cy_bottom1 fl over">
                  <span>URL：</span><input type="text" class="zs_input3">&nbsp;<span>0/1024</span>
                </div>
            </li>
            <li>
                <div class="cy_bottom1 fl over">
                    <span>子链一 名称 </span><input type="text" class="zs_input3">&nbsp;<span>0/16</span>
                </div>
                <div class="cy_bottom1 fl over">
                    <span>URL：</span><input type="text" class="zs_input3">&nbsp;<span>0/1024</span>
                </div>
            </li>
            <li>
                <div class="cy_bottom1 fl over">
                    <span>子链一 名称 </span><input type="text" class="zs_input3">&nbsp;<span>0/16</span>
                </div>
                <div class="cy_bottom1 fl over">
                   <span>URL：</span><input type="text" class="zs_input3">&nbsp;<span>0/1024</span>
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
                <li class="current">推广位 <span>|</span><span class="list06_point"></span></li>
                <li>推广链接位</li>
            </ul>
            <div class="w_list06 over">
                <div class="list06_concent">
                    <textarea class="list06_textarea">
                    </textarea>
                </div>
                <div class="list06_concent hides">
                    <textarea class="list06_textarea">
                    </textarea>
                </div>

            </div>
        </div>
    </div>
    <div class="zs_bottom2 over fr">
        <ul>
            <li>
                <div class="w_list01 fl over"><span>状态:</span></div>
                <div class="w_list02 fl over"><b></b></div>
            </li>
            <li>
                <div class="w_list01 fl over"><span>设备偏好：</span></div>
                <div class="w_list02 fl over"><select>
                    <option>全部</option>
                </select></div>
            </li>
            <li>
                <div class="w_list01 fl over"><span>启用/暂停：</span></div>
                <div class="w_list02 fl over"><select>
                    <option>启用</option>
                </select></div>
            </li>
        </ul>
    </div>
</div>
</div>
<!--推广单元-->
<div class="containers  over hides">
<div class="zs_function over">
    <ul class="fl">
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加</b></a></li>
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除</b></a></li>
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function3.png"></span><b>批量添加/更新</b></a></li>
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function4.png"></span><b>编辑&nbsp;<input
                type="image" src="../public/img/zs_input.png"></b></a></li>

        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function9.png"></span><b>还原</b></a></li>
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function10.png"></span><b>搜索词</b></a></li>
    </ul>
    <span class="fr">1/10</span>
</div>
<div class="list4">
    <table border="0" cellspacing="0" width="100%">
        <thead>
        <tr class="list02_top">
            <td>&nbsp;推广单元名称</td>
            <td>&nbsp;推广单元状态</td>
            <td>&nbsp;启动/暂停</td>
            <td>&nbsp;出价</td>
            <td>&nbsp;否定关键词</td>
            <td>&nbsp;移动出价比例</td>
            <td>&nbsp;推广计划名称
                <div class="set fr"></div>
            </td>
        </tr>
        </thead>
        <tbody id="tbodyClick_campaign">
        <tr class="list2_box3">
            <td>&nbsp;北京{婚博会}<span class="editor"></span></td>
            <td>&nbsp;有效<span class="editor"></span></td>
            <td>&nbsp;启用<span class="editor"></span></td>
            <td>&nbsp;一星<span class="editor"></span></td>
            <td>&nbsp;一星<span class="editor"></span></td>
            <td>&nbsp;短语-核心包含<span class="editor"></span></td>
            <td>&nbsp;<a href="#">http://tthunbohui.com </a><span class="editor"></span></td>
        </tr>
        <tr class="list2_box2">
            <td>&nbsp;北京{婚博会}</td>
            <td>&nbsp;有效</td>
            <td>&nbsp;启用</td>
            <td>&nbsp;一星</td>
            <td>&nbsp;一星</td>
            <td>&nbsp;短语-核心包含</td>
            <td>&nbsp;<a href="#">http://tthunbohui.com</a></td>
        </tr>
        <tr class="list2_box1">
            <td>&nbsp;北京{婚博会}</td>
            <td>&nbsp;有效</td>
            <td>&nbsp;启用</td>
            <td>&nbsp;一星</td>
            <td>&nbsp;一星</td>
            <td>&nbsp;短语-核心包含</td>
            <td>&nbsp;<a href="#">http://tthunbohui.com</a></td>
        </tr>
        <tr class="list2_box2">
            <td>&nbsp;北京{婚博会}</td>
            <td>&nbsp;有效</td>
            <td>&nbsp;启用</td>
            <td>&nbsp;一星</td>
            <td>&nbsp;一星</td>
            <td>&nbsp;短语-核心包含</td>
            <td>&nbsp;<a href="#">http://tthunbohui.com</a></td>
        </tr>
        <tr class="list2_box1">
            <td>&nbsp;北京{婚博会}</td>
            <td>&nbsp;有效</td>
            <td>&nbsp;启用</td>
            <td>&nbsp;一星</td>
            <td>&nbsp;一星</td>
            <td>&nbsp;短语-核心包含</td>
            <td>&nbsp;<a href="#">http://tthunbohui.com</a></td>
        </tr>
        <tr class="list2_box2">
            <td>&nbsp;北京{婚博会}</td>
            <td>&nbsp;有效</td>
            <td>&nbsp;启用</td>
            <td>&nbsp;一星</td>
            <td>&nbsp;一星</td>
            <td>&nbsp;短语-核心包含</td>
            <td>&nbsp;<a href="#">http://tthunbohui.com</a></td>
        </tr>
        <tr class="list2_box1">
            <td>&nbsp;北京{婚博会}</td>
            <td>&nbsp;有效</td>
            <td>&nbsp;启用</td>
            <td>&nbsp;一星</td>
            <td>&nbsp;一星</td>
            <td>&nbsp;短语-核心包含</td>
            <td>&nbsp;<a href="#">http://tthunbohui.com</a>
        </tr>
        <tr class="list2_box2">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr class="list2_box1">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr class="list2_box2">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr class="list2_box1">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr class="list2_box2">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr class="list2_box1">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr class="list2_box2">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr class="list2_box1">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr class="list2_box2">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        </tbody>
    </table>
</div>
<div class="more_list over" style="display:none;">
    <ul>
        <li class="current"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加推广单元</b></li>
        <li><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除推广单元</b></li>
        <li><span class="zs_top"><img src="../public/img/zs_function12.png"></span><b>验证推广单元</b></li>
        <li><span class="z_function_hover"><img src="../public/img/zs_function9.png"></span><b>还原推广单元</b></li>
        <li><span class="zs_top"><img src="../public/img/zs_function13.png"></span><b>复制</b></li>
        <li><span class="zs_top"><img src="../public/img/zs_function14.png"></span><b>剪贴</b></li>
        <li><span class="zs_top"><img src="../public/img/zs_function15.png"></span><b>粘贴</b></li>
        <li><span class="zs_top"><img src="../public/img/zs_function16.png"></span><b>全选</b></li>
    </ul>
</div>
<div class="zhanghu_input"></div>
<div class="zs_bottom over">
    <div class="zs_bottom1 over fl ">
        <ul>
            <li>
                <div class="t_list01 fl over">名称：</div>
                <div class="t_list02 fl over"><input type="text" class="zs_input1"></div>
            </li>
            <li>
                <div class="t_list01 fl over">出价：</div>
                <div class="t_list02 fl over"><input type="text" class="zs_input1"></div>
            </li>
            <li>
                <div class="t_list01 fl over">移动出价比例：</div>
                <div class="t_list02 fl over"><input type="text" class="zs_input1"><span>59/1024</span></div>
            </li>
        </ul>
    </div>
    <div class="zs_bottom2 over fr">
        <ul>
            <li>
                <div class="w_list01 fl over">状态：</div>
                <div class="w_list02 fl over"><b>有效</b></div>
            </li>
            <li>
                <div class="w_list01 fl over">启用/暂停：</div>
                <div class="w_list02 fl over"><select>
                    <option>启用</option>
                </select></div>
            </li>
        </ul>
    </div>
</div>
</div>
<!--推广计划-->
<div class="containers  over hides">
    <div class="zs_function over">
        <ul class="fl">
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加</b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function17.png"></span><b>快速新建计划</b></a>
            </li>

            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除</b></a></li>

            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function4.png"></span><b>编辑&nbsp;<input
                    type="image" src="../public/img/zs_input.png"></b></a></li>

            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function9.png"></span><b>还原</b></a></li>
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function10.png"></span><b>搜索词</b></a></li>
        </ul>
        <span class="fr">1/10</span>
    </div>
    <div class="list4">
        <table border="0" cellspacing="0" width="100%">
            <thead>
            <tr class="list02_top">
                <td>&nbsp;推广计划名称</td>
                <td>&nbsp;推广计划状态</td>
                <td>&nbsp;启用/暂停</td>
                <td>&nbsp;每日预算</td>
                <td>&nbsp;创意展现方式</td>
                <td>&nbsp;动态创意状态</td>
                <td>&nbsp;推广时段</td>
                <td>&nbsp;推广地域</td>
                <td>&nbsp;否定关键词</td>
                <td>&nbsp;IP排除</td>
                <td>&nbsp;到达预算下线时间
                    <div class="set fr"></div>
                </td>
            </tr>
            </thead>
            <tbody id="tbodyClick5">

            </tbody>
        </table>
    </div>
    <div class="more_list over" style="display:none;">
        <ul>
            <li class="current"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加推广计划</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除推广计划</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function12.png"></span><b>验证推广计划</b></li>
            <li><span class="z_function_hover"><img src="../public/img/zs_function9.png"></span><b>还原推广计划</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function13.png"></span><b>复制</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function15.png"></span><b>粘贴</b></li>
            <li><span class="zs_top"><img src="../public/img/zs_function16.png"></span><b>全选</b></li>
        </ul>
    </div>
    <div class="zhanghu_input"></div>
    <div class="zs_bottom over">
        <div class="zs_bottom2 over fl " style="border-right: 1px solid #e7e7e7;">
            <ul>
                <li>
                    <div class="w_list01 fl over">名称：</div>
                    <div class="w_list02 fl over"><input type="text" class="zs_input3" value="婚博会"></div>
                </li>
                <li>
                    <div class="w_list01 fl over">每日预算：</div>
                    <div class="w_list02 fl over"><input type="text" class="zs_input3"></div>
                </li>
                <li>
                    <div class="w_list01 fl over">移动出价比例：</div>
                    <div class="w_list02 fl over"><input type="text" class="zs_input3"><span>59/1024</span></div>
                </li>
            </ul>
        </div>
        <div class="zs_bottom1 over fr" style="border-right:none;">
            <ul class="z_bottom3 fl">
                <li>
                    <div class="w_list01 fl over">推广时段：</div>
                    <div class="w_list02 fl over"><em>全部</em></div>
                </li>
                <li>
                    <div class="w_list01 fl over">推广地域：</div>
                    <div class="w_list02 fl over"><em>使用账户推广地域</em></div>
                </li>
                <li>
                    <div class="w_list01 fl over">动态创意状态：</div>
                    <div class="w_list02 fl over"><b>开启</b></div>
                </li>
            </ul>
            <ul class="z_bottom3 fl">
                <li>
                    <div class="w_list01 fl over">否定关键词：</div>
                    <div class="w_list02 fl over "><em>未设置</em></div>
                </li>
                <li>
                    <div class="w_list01 fl over">IP排除：</div>
                    <div class="w_list02 fl over"><em>未设置</em></div>
                </li>
                <li>
                    <div class="w_list01 fl over">创意展现方式：</div>
                    <div class="w_list02 fl over"><select>
                        <option>优选</option>
                    </select></div>
                </li>
            </ul>
            <ul class="z_bottom3 fl">
                <li>
                    <div class="w_list01 fl over">状态：</div>
                    <div class="w_list02 fl over"><b>暂停推广</b></div>
                </li>
                <li>
                    <div class="w_list01 fl over">IP排除：</div>
                    <div class="w_list02 fl over"><span> 0次</span></div>
                </li>
                <li>
                    <div class="w_list01 fl over">启用/暂停：</div>
                    <div class="w_list02 fl over"><select>
                        <option>启用</option>
                    </select></div>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--账户-->
<div class="containers  over hides">
    <div class="zh_top over">
        <div class="fl">
            <span><b>baidu-bjtthunbohui2134115</b></span>
            <span><a href="#">还原帐户</a><input type="checkbox" checked="checkde"/>验证帐户</span>
        </div>
        <div class="fr"><input type="image" src="../public/img/shuaxin.png"></div>
    </div>
    <div class="zh_list02 over">
        <ul>
            <li>
                <span>帐户余额：<b id="balance">￥38678.1</b></span>
                <span>昨日消费：<b id="cost">暂无数据</b></span>
                <span>消费升降：<b id="costStatus">暂无数据</b></span>
                <span>动态创意:<a href="#" class="showbox7">开启</a></span>
            </li>
            <li>
                <span>帐户预算：<b id="accountBudget">不限定</b><a href="#" class="showbox5">修改</a></span>
                <span>到达预算：<b id="reachBudget">-</b></span>
                <span>IP排除：<a href="#" class="showbox6">未设置</a></span>
            </li>
        </ul>
    </div>
    <div class="zh_list over">
        <div id="containerLegend" class="zs_function over">
            <%--<ul>
                <li><b>账户表现</b></li>
                &lt;%&ndash;<li>最近7天&nbsp;<input type='image' src='../public/img/zs_input.png'/></li>&ndash;%&gt;
                <li><input name='chartcheckbox' type='checkbox' checked='checked'><span style='background-color: #5bc0de'></span><b>展现</b></li>
                <li><input name='chartcheckbox' type='checkbox' checked='checked'><span style='background-color: #000000'></span><b>点击</b></li>
                <li><input name='chartcheckbox' type='checkbox'><span style="background-color: #008000"></span><b>点击率</b></li>
                <li><input name='chartcheckbox' type='checkbox'><span style="background-color: #ffA500"></span><b>消费</b></li>
                <li><input name='chartcheckbox' type='checkbox'><span style='background-color: #8D67E0'></span><b>转化</b></li>
            </ul>--%>
            <%--<ul class="fl">
                <li><b>账户表现</b></li>
                <li>最近7天&nbsp;<input type="image" src="../public/img/zs_input.png"></li>
                <li><input type="checkbox" name="zs_time"> 展现</li>
                <li><input type="checkbox" name="zs_time"> 点击</li>
                <li><input type="checkbox" name="zs_time"> 点击率</li>
                <li><input type="checkbox" name="zs_time"> 转化</li>
                <li><input type="checkbox" checked="checked" name="zs_time">消费</li>
            </ul>
            <span class="fr">
                <input type="image" src="../public/img/zs_input2.png">
            </span>--%>
        </div>
        <div id="container" style="width: 58%; height: 40%"></div>
        <%--<div class="zs_list over">
            <img src="../public/images/zs_list.png">
        </div>--%>
    </div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
<!---------下载账户----------->
<div class="TB_overlayBG"></div>
<div class="box" style="display:none" id="download">
    <h2 id="box2">账户下载<a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        您希望下载账户的哪个部分？
        <ul class="zs_set">
            <li><input type="radio" checked="checked" name="no1">&nbsp; 所有推广计划</li>
            <li><input type="radio" name="no1">&nbsp; 已下载的推广计划</li>
            <li><input type="radio" name="no1">&nbsp; 从最新的推广计划列表中选择</li>
        </ul>
        <div class="zs_sets over">
            <div class="zs_ses1">
                <ul>
                    <li><input type="checkbox" checked="checked" name="zsets">北京婚博会</li>
                    <li class="current"><input type="checkbox" name="zsets">北京婚博会优化</li>
                    <li><input type="checkbox" name="zsets">北京婚展</li>
                    <li class="current"><input type="checkbox" name="zsets">北京婚博会优化</li>
                    <li><input type="checkbox" name="zsets">北京婚展</li>
                    <li class="current"><input type="checkbox" name="zsets">北京婚博会优化</li>
                    <li><input type="checkbox" name="zsets">北京婚展</li>
                    <li class="current"><input type="checkbox" name="zsets">北京婚博会优化</li>
                </ul>
            </div>
            <div class="zs_ses1 hides">
                <ul>
                    <li><input type="checkbox" checked="checked" name="zsets">北京婚博会</li>
                    <li class="current"><input type="checkbox" name="zsets">北京婚博会优化</li>
                    <li><input type="checkbox" name="zsets">北京婚展</li>
                    <li class="current"><input type="checkbox" name="zsets">北京婚博会优化</li>
                    <li><input type="checkbox" name="zsets">北京婚展</li>
                    <li class="current"><input type="checkbox" name="zsets">北京婚博会优化</li>
                </ul>
            </div>
            <div class="zs_ses1 hides">
                <ul>
                    <li><input type="checkbox" checked="checked" name="zsets">北京婚博会</li>
                    <li class="current"><input type="checkbox" name="zsets">北京婚博会优化</li>
                    <li><input type="checkbox" name="zsets">北京婚展</li>
                    <li class="current"><input type="checkbox" name="zsets">北京婚博会优化</li>
                </ul>
            </div>
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
<!---------查找重复关键词----------->
<div class="box3" style="display:none">
    <h2 id="box3">查找重复关键词<a href="#" class="close">关闭</a></h2>

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
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>
<!---------估算工具----------->
<div class="box4" style="display:none">
    <h2 id="box4">估算工具<a href="#" class="close">关闭</a></h2>

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
    <h2 id="box5">修改账户预算<a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <ul class="zs_set">
            <li><input id="budget_text" type="text" style="width: 180px"/></li>
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
!---------IP排除----------->
<div class="box6" style="display:none;width: 300px">
    <h2 id="box6">IP排除<a href="#" class="close">关闭</a></h2>

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
    <h2 id="box7"><a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <div class="w_list03">
            <ul class="zs_set">
                <li id="dynamicCreative" class="current">确认</li>
                <li class="close">取消</li>
            </ul>
        </div>
    </div>
</div>
<div  class="box" style="display:none" id="jcAdd">
    <h2 id="jcBox">添加创意<a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        选择要添加到的计划或者单元!
        <ul class="zs_set" id="jcUl">
            <%--<li><input type="radio" checked="checked" name="no1">&nbsp; 所有推广计划</li>--%>
        </ul>
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
<!-- javascript -->
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.livequery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/plugs/jQuery-smartMenu/jquery-smartMenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/html.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/tc.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/untils/untils.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistantCreative/creative.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistantKeyword.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistantCampaign.js"></script>

<script type="text/javascript">
    // 对Date的扩展，将 Date 转化为指定格式的String
    // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
    // 例子：
    // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2013-06-08 08:09:04.423
    // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2013-6-8 8:9:4.18
    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1,                 //月份
            "d+": this.getDate(),                    //日
            "h+": this.getHours(),                   //小时
            "m+": this.getMinutes(),                 //分
            "s+": this.getSeconds(),                 //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
</script>
<script type="text/javascript">

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
    if (treeNode.level == 0) {
        //点击的是父节点(推广计划),则应该展示其下属的推广单元数据
//        alert(treeNode.id + "," + treeNode.name);
        getCreativePlan(treeNode.id);
        campaignId = treeNode.id + "," + "0";
        //事件处理
    } else if (treeNode.level == 1) {
        //点击的是子节点(推广单元),则应该展示其下属的关键词数据
        getCreativeUnit({cid:treeNode.getParentNode().id,aid:treeNode.id});
//        alert(treeNode.id + "," + treeNode.name+"parents"+treeNode.getParentNode().id);
        adgroupId = treeNode.id + "," + "1";
        //事件处理
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

/******************highcharts********************/

var start_date;
var end_date;

/**
 * 获取昨天, 近七天, 近30天日期
 * 参数为1, 昨天
 * 参数为7, 7天
 * 参数为30, 近30天
 * @param day
 */
var getDateParam = function (day) {
    var currDate = new Date();
    if (day == 1) {
        currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24);
        start_date = currDate.Format("yyyy-MM-dd");
        end_date = start_date;
    } else if (day == 7) {
        currDate = new Date();
        currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24);
        end_date = currDate.Format("yyyy-MM-dd");
        currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24 * 6);
        start_date = currDate.Format("yyyy-MM-dd");
    } else if (day == 30) {
        currDate = new Date();
        currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24);
        end_date = currDate.Format("yyyy-MM-dd");
        currDate.setTime(currDate.getTime() - 1000 * 60 * 60 * 24 * 29);
        start_date = currDate.Format("yyyy-MM-dd");
    }
};

//定义展现、点击、点击率、消费、转化数组
var arr_impr = [7.0, 25, 9.5, 14.5, 18.2, 21.5, 100.2];
var arr_click = [0.2, 85, 5.7, 50.3, 17.0, 22.0, 24.8];
var arr_ctr = [0.9, 0.6, 95, 75, 13.5, 17.0, 18.6];
var arr_cost = [3.9, 10, 5.7, 8.5, 11.9, 65, 17.0];
var arr_conv = [115, 54, 77, 8.5, 130, 96, 100.0];

var xAxis_categories = [];

var AccountPerformance = function () {
    $("#containerLegend").empty();
    $("#containerLegend").append("<div class='tu_top over'><ul><li><b>账户表现<b/></li>"
//            + "<li>最近7天&nbsp;<input type='image' src='../public/img/zs_input.png'/></li>"
            + "<li><input id='impression' name='chartcheckbox' type='checkbox' checked='checked'><span class='orange'></span><b>展现</b></li>"
            + "<li><input id='click' name='chartcheckbox' type='checkbox'><span class='orange'></span><b>点击</b></li>"
            + "<li><input id='ctr' name='chartcheckbox' type='checkbox'><span class='orange'></span><b>点击率</b></li>"
            + "<li><input id='cost' name='chartcheckbox' type='checkbox'><span class='orange'></span><b>消费</b></li>"
            + "<li><input id='conversion' name='chartcheckbox' type='checkbox'><span class='orange'></span><b>转化</b></li></ul></div>");

    getDateParam(7);

    var containerWidth = $('#container').width();

    //加载账户表现曲线图
    $('#container').highcharts({
        chart: {
            type: 'line'
        },
        title: {
            text: ''
        },
        credits: {
            enabled: false
        },
        xAxis: {
            categories: ['08-01', '08-02', '08-03', '08-04', '08-05', '08-06', '08-07']
        },
        yAxis: {
            title: {
                text: 'Value'
            },
            plotLines: [
                {
                    value: 0,
                    width: 1,
                    color: '#808080'
                }
            ],
            min: 0,
            gridLineWidth: 0
        },
        exporting: {
            enabled: false
        },
        tooltip: {
            shared: true
        },
        legend: {
            align: 'left',
            x: 10,
            verticalAlign: 'top',
            y: -10,
            floating: true,
            itemDistance: 20,
            borderRadius: 5,
            enabled: false
        },
        series: [
            {
                name: 'impression',
                data: arr_impr
            }
        ]
    });

};

var reloadhighcharts = function () {
    var chart = $('#container').highcharts();
    while (chart.series.length > 0) {
        chart.series[0].remove();
    }
    var arr_checkbox = $("input[name=chartcheckbox]:checkbox");
    for (var i = 0, l = arr_checkbox.length; i < l; i++) {
        if (arr_checkbox[i].checked) {
            var attrValue = arr_checkbox.eq(i).attr("id");
            if (attrValue == "impression") {
                chart.addSeries({
                    name: attrValue,
                    data: arr_impr
                });
            } else if (attrValue == "click") {
                chart.addSeries({
                    name: attrValue,
                    data: arr_click
                });
            } else if (attrValue == "ctr") {
                chart.addSeries({
                    name: attrValue,
                    data: arr_ctr
                });
            } else if (attrValue == "cost") {
                chart.addSeries({
                    name: attrValue,
                    data: arr_cost
                });
            } else if (attrValue == "conversion") {
                chart.addSeries({
                    name: attrValue,
                    data: arr_conv
                });
            }
        }
    }
};

var loadAccountReports = function () {
    $.ajax({
        url: "/account/getReports",
        type: "GET",
        dataType: "json",
        data: {
            "startDate": start_date,
            "endDate": end_date
        },
        async: false,
        success: function (data, textStatus, jqXHR) {
            xAxis_categories = data.dates;
            var results = data.rows;
            if (results != null && results.length > 0) {
                $.each(results, function (i, item) {
                    arr_impr.push(item.pcImpression);
                    arr_click.push(item.pcClick);
                    arr_ctr.push(item.pcCtr);
                    arr_cost.push(item.pcCost);
                    arr_conv.push(item.pcConversion);
                });
            }
        }
    });
};

//================================================

var campaignId;

var adgroupId;

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

    loadAccountData();

    //loadAccountReports();

    $("#box7").text(loadDynamicCreativeStatus());

    $("#dynamicCreative").livequery('click', function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box7").css("display", "none");
        dynamicCreativeStatus = (dynamicCreativeStatus == 0 ? 1 : 0);
        changeDynamicCreativeStatus();
    });

    $("#modifyAccountBudget_ok").livequery('click', function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box5").css("display", "none");
        modifyAccountBudget();
    });

    $("#excludeIP_ok").livequery('click', function () {
        $(".TB_overlayBG").css("display", "none");
        $(".box6").css("display", "none");
        excludeIP();
    });

    AccountPerformance();

    $("input[name=chartcheckbox]:checkbox").livequery('click', function () {
        reloadhighcharts();
    });
});

var loadAccountData = function () {
    $.ajax({
        url: "/account/getBaiduAccountInfoByUserId",
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            var results = data.rows;
            if (results != null && results.length > 0) {
                $.each(results, function (i, item) {
                    $("#balance").text(item.balance);
                    $("#cost").text(data.cost);
                    $(".showbox7").text((item.isDynamicCreative == false) ? "开启" : "关闭");
                    $("#accountBudget").text(item.budget);
                    dynamicCreativeStatus = (item.isDynamicCreative == false) ? 0 : 1;
                    return false;
                });
            }
        }
    });
};

//动态创意状态标识
var dynamicCreativeStatus;
var loadDynamicCreativeStatus = function () {
    var _content = "";
    if (dynamicCreativeStatus == 0)
        _content = "您确定要开启动态创意么?";
    else if (dynamicCreativeStatus == 1)
        _content = "您确定要关闭动态创意么?";

    return _content;
};

//开启或关闭动态创意
var changeDynamicCreativeStatus = function () {
    var jsonEntity = {};
    jsonEntity["isDynamicCreative"] = (dynamicCreativeStatus == 1);
    $.ajax({
        url: "/account/update",
        dateType: "json",
        data: JSON.stringify(jsonEntity),
        success: function (data, textStatus, jqXHR) {
            alert("******");
        }
    });
};

//修改账户预算
var modifyAccountBudget = function () {
    var _budget = $("#budget_text").val();
    $("#accountBudget").text(_budget);
    var jsonEntity = {};
    jsonEntity["budget"] = _budget;
    $.ajax({
        url: "/account/update",
        dateType: "json",
        data: JSON.stringify(jsonEntity),
        success: function (data, textStatus, jqXHR) {
            alert("******");
        }
    });
};

//IP排除
var excludeIP = function () {
    var jsonEntity = {};
    $.ajax({
        url: "/account/update",
        dateType: "json",
        data: JSON.stringify(jsonEntity),
        success: function (data, textStatus, jqXHR) {
            alert("******");
        }
    });
};

/*$(function () {
 var t = $("#tree");
 t = $.fn.zTree.init(t, setting, zNodes);
 demoIframe = $("#testIframe");
 demoIframe.bind("load", loadReady);
 var zTree = $.fn.zTree.getZTreeObj("zTree");
 zTree.selectNode(zTree.getNodeByParam("id", 101));

 });*/


/*function loadReady() {
 var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
 htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
 maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
 h = demoIframe.height() >= maxH ? minH : maxH;
 if (h < 1000) h = 1000;
 demoIframe.height(h);
 }*/

</script>

</body>
</html>