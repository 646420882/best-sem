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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/public/themes/flick/jquery-ui-1.11.0.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/ui.daterangepicker.css">
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
        <a href="javascript:void(0)" onclick="loadCreativeData()" class="fr">刷新</a>
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
    <span class="fr">查看搜索推广报告&nbsp;<input type="image" src="../public/img/zs_input.png"></span>
</div>
<div class="zs_line"></div>
<div class="zs_box over">
<!--关键词-->
<div class="containers  over">
<div class="zs_function over">
    <ul class="fl">
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加</b></a></li>
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function2.png"></span><b>删除</b></a></li>
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function3.png"></span><b>批量添加/更新</b></a></li>
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function4.png"></span><b>编辑&nbsp;<input
                type="image" src="../public/img/zs_input.png"></b></a></li>
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function5.png"></span><b>搜索</b></a></li>
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function6.png"></span><b>分析</b></a></li>
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function7.png"></span><b>估算</b></a></li>
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function8.png"></span><b>监控</b></a></li>
        <li><a href="#"><span class="z_function_hover"><img src="../public/img/zs_function9.png"></span><b>还原</b></a>
        </li>
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function10.png"></span><b>搜索词</b></a></li>
        <li><a href="#"><span class="z_function_hover"><img src="../public/img/zs_function11.png"></span><b>激活</b></a>
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
                <div class="w_list01 fl over">关键词名称：</div>
                <div class="w_list02 fl over"><input type="text" class="zs_input1"></div>
            </li>
            <li>
                <div class="w_list01 fl over">出价：</div>
                <div class="w_list02 fl over"><input type="text" class="zs_input1"></div>
            </li>
            <li>
                <div class="w_list01 fl over">访问URL</div>
                <div class="w_list02 fl over"><input type="text" class="zs_input1"><span>59/1024</span></div>
            </li>
            <li>
                <div class="w_list01 fl over">移动访问URL：</div>
                <div class="w_list02 fl over"><input type="text" class="zs_input1"><span>59/1024</span></div>
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
            <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加</b></a></li>
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
                <td>&nbsp;创意标题</td>
                <td>&nbsp;创意描述1</td>
                <td>&nbsp;创意描述2</td>
                <td>&nbsp;默认访问URL</td>
                <td>&nbsp;默认显示URL</td>
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
                    <div class="w_list01 fl over">创意标题：</div>
                    <div class="w_list02 fl over"><input type="text" class="zs_input1" id="sTitle"><input type="button"
                                                                                                          value="{}插入通配符"
                                                                                                          class="zs_input2"><span>49/50</span>
                    </div>
                </li>
                <li>
                    <div class="w_list01 fl over">创意描述1：</div>
                    <div class="w_list02 fl over"><input type="text" class="zs_input1" id="sDes1"><input type="button"
                                                                                                         value="{}插入通配符"
                                                                                                         class="zs_input2"><span>49/50</span>
                    </div>
                </li>
                <li>
                    <div class="w_list01 fl over">创意描述2：</div>
                    <div class="w_list02 fl over"><input type="text" class="zs_input1" id="sDes2"><input type="button"
                                                                                                         value="{}插入通配符"
                                                                                                         class="zs_input2"><span>49/50</span>
                    </div>
                </li>
                <li>
                    <div class="w_list03 fl over">
                        <div class="w_list01 fl over">默认访问URL：</div>
                        <div class="w_list02 fl over"><input type="text" class="zs_input3"
                                                             id="sDefUrl"><span>49/50</span></div>
                    </div>
                    <div class="w_list03 fr over">
                        <div class="w_list01 fl over">默认显示URL：</div>
                        <div class="w_list02 fl over"><input type="text" class="zs_input3"
                                                             id="sDefDisUrl"><span>49/50</span></div>
                    </div>
                </li>
                <li>
                    <div class="w_list03 fl over">
                        <div class="w_list01 fl over">移动访问URL：</div>
                        <div class="w_list02 fl over"><input type="text" class="zs_input3"
                                                             id="sMibUrl"><span>49/50</span></div>
                    </div>
                    <div class="w_list03 fr over">
                        <div class="w_list01 fl over">移动显示URL：</div>
                        <div class="w_list02 fl over"><input type="text" class="zs_input3"
                                                             id="sMibDisUrl"><span>49/50</span></div>
                    </div>
                </li>
                <li>
                    <div class="w_list01 fl over">创意预览：</div>
                    <div class="w_list02 fl over"><textarea class="zs_input4" id="sPreview"></textarea></div>
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
                    <div class="w_list01 fl over"><span>子链一 名称</span></div>
                    <div class="w_list02 fl over"><input type="text" class="zs_input3">&nbsp;<span>0/16</span></div>
                </div>
                <div class="cy_bottom1 fl over">
                    <div class="w_list02 fl over"><span>URL：</span><input type="text" class="zs_input1">&nbsp;<span>0/1024</span>
                    </div>
                </div>
            </li>
            <li>
                <div class="cy_bottom1 fl over">
                    <div class="w_list01 fl over"><span>子链一 名称</span></div>
                    <div class="w_list02 fl over"><input type="text" class="zs_input3">&nbsp;<span>0/16</span></div>
                </div>
                <div class="cy_bottom1 fl over">
                    <div class="w_list02 fl over"><span>URL：</span><input type="text" class="zs_input1">&nbsp;<span>0/1024</span>
                    </div>
                </div>
            </li>
            <li>
                <div class="cy_bottom1 fl over">
                    <div class="w_list01 fl over"><span>子链一 名称</span></div>
                    <div class="w_list02 fl over"><input type="text" class="zs_input3">&nbsp;<span>0/16</span></div>
                </div>
                <div class="cy_bottom1 fl over">
                    <div class="w_list02 fl over"><span>URL：</span><input type="text" class="zs_input1">&nbsp;<span>0/1024</span>
                    </div>
                </div>
            </li>
            <li>
                <div class="cy_bottom1 fl over">
                    <div class="w_list01 fl over"><span>子链一 名称</span></div>
                    <div class="w_list02 fl over"><input type="text" class="zs_input3">&nbsp;<span>0/16</span></div>
                </div>
                <div class="cy_bottom1 fl over">
                    <div class="w_list02 fl over"><span>URL：</span><input type="text" class="zs_input1">&nbsp;<span>0/1024</span>
                    </div>
                </div>
            </li>
            <li>
                <div class="cy_bottom1 fl over">
                    <div class="w_list01 fl over"><span>子链一 名称</span></div>
                    <div class="w_list02 fl over"><input type="text" class="zs_input3">&nbsp;<span>0/16</span></div>
                </div>
                <div class="cy_bottom1 fl over">
                    <div class="w_list02 fl over"><span>URL：</span><input type="text" class="zs_input1">&nbsp;<span>0/1024</span>
                    </div>
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
                <div class="w_list01 fl over">名称：</div>
                <div class="w_list02 fl over"><input type="text" class="zs_input1"></div>
            </li>
            <li>
                <div class="w_list01 fl over">出价：</div>
                <div class="w_list02 fl over"><input type="text" class="zs_input1"></div>
            </li>
            <li>
                <div class="w_list01 fl over">移动出价比例：</div>
                <div class="w_list02 fl over"><input type="text" class="zs_input1"><span>59/1024</span></div>
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
        <li><a href="#"><span class="zs_top"><img src="../public/img/zs_function17.png"></span><b>快速新建计划</b></a></li>

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
                <div class="w_list02 fl over"><em>未设置</em></div>
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
    <div class="fl"><span><b>baidu-bjtthunbohui2134115</b></span><span><a href="#">还原帐户</a> <input type="checkbox"
                                                                                                   checked="checkde">验证帐户</span>
    </div>
    <div class="fr"><input type="image" src="../public/img/shuaxin.png"></div>
</div>
<div class="zh_list02 over">
    <ul>
        <li>
            <span>帐户余额：<b>￥38678.1</b></span>
            <span>昨日消费：<b>暂无数据</b></span>
            <span>消费升降：<b>暂无数据</b></span>
            <span>动态创意:<a href="#">开启</a></span>
        </li>
        <li>
            <span>帐户预算：<b>不限定</b><a href="#">修改</a></span>
            <span>到达预算：<b>-</b></span>
            <span>IP排除：<a href="#">未设置</a></span>
            <span>推广地域：<a href="#">北京</a></span>
        </li>
    </ul>
</div>
<div class="zh_list over">
    <div class="zs_function over">
        <ul class="fl">
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
                                                        </span>
    </div>
    <div class="zs_list over">
        <img src="../public/images/zs_list.png">
    </div>
</div>
<div class="zh_list over">
<div class="zs_function over">
    <ul class="fl">
        <li><b>账户表现</b></li>
        <li><span>昨日&nbsp;</span><input type="image" src="../public/img/zs_input.png"></li>
        <li><span>账户&nbsp;</span><input type="image" src="../public/img/zs_input.png"></li>
        <li><span>前10&nbsp;</span><input type="image" src="../public/img/zs_input.png"></li>
        <li><span><a href="#">按消费降序</a></span></li>
    </ul>
</div>
<div class="list4">
<table border="0" cellspacing="0" width="100%">
<thead>
<tr class="list02_top">
    <td>&nbsp;账户名称</td>
    <td>&nbsp;展现</td>
    <td>&nbsp;点击</td>
    <td>&nbsp;点击率</td>
    <td>&nbsp;转化</td>
    <td>&nbsp;消费</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
</thead>
<tbody>
<tr class="list2_box3">
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
</tbody>
</table>
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
</div>
<!---------下载账户----------->
<div class="TB_overlayBG"></div>
<div class="box" style="display:none">
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


<!-- javascript -->
<script type="text/javascript" src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.livequery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/html.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/tc.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/untils/untils.js"></script>

<%--AssistantCreative Js--%>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/js/assistantCreative/creative.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistantKeyword.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assistantCampaign.js"></script>

<script type="text/javascript" >
    var campaignId;

    var adgroupId;

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
            alert(treeNode.id + "," + treeNode.name);
            campaignId = treeNode.id + "," + "0";
            //事件处理
        } else if (treeNode.level == 1) {
            //点击的是子节点(推广单元),则应该展示其下属的关键词数据
            alert(treeNode.id + "," + treeNode.name);
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
        //加载树
        $.fn.zTree.init($("#zTree"), setting, zNodes);
    });

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

<style type="text/css">
    .tab_box {
        padding: 0px 3% 0 0;
    }
</script>

</body>
</html>