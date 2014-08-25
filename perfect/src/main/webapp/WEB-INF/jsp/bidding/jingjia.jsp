<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>大数据智能营销</title>
    <link rel="stylesheet" type="text/css" href="public/css/public.css">
    <link rel="stylesheet" type="text/css" href="public/css/style.css">
    <script type="text/javascript" src="public/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="public/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="public/js/tc.min.js"></script>
    <SCRIPT type="text/javascript">

        <!--
        var zTree;
        var demoIframe;

        var setting = {
            view: {
                dblClickExpand: false,
                showLine: true,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: ""
                }
            },
            callback: {
                beforeClick: function (treeId, treeNode) {
                    var zTree = $.fn.zTree.getZTreeObj("tree");
                    if (treeNode.isParent) {
                        zTree.expandNode(treeNode);
                        return false;
                    } else {
                        demoIframe.attr("src", treeNode.file + ".html");
                        return true;
                    }
                }
            }
        };

        var zNodes = [
            {id: 1, pId: 0, name: "[core] 基本功能 演示", open: true},
            {id: 101, pId: 1, name: "最简单的树 --  标准 JSON 数据", file: "core/standardData"},
            {id: 102, pId: 1, name: "最简单的树 --  简单 JSON 数据", file: "core/simpleData"},
            {id: 103, pId: 1, name: "不显示 连接线", file: "core/noline"},
            {id: 104, pId: 1, name: "不显示 节点 图标", file: "core/noicon"},
            {id: 105, pId: 1, name: "自定义图标 --  icon 属性", file: "core/custom_icon"},
            {id: 106, pId: 1, name: "自定义图标 --  iconSkin 属性", file: "core/custom_iconSkin"},
            {id: 107, pId: 1, name: "自定义字体", file: "core/custom_font"},
            {id: 115, pId: 1, name: "超链接演示", file: "core/url"},
            {id: 108, pId: 1, name: "异步加载 节点数据", file: "core/async"},
            {id: 109, pId: 1, name: "用 zTree 方法 异步加载 节点数据", file: "core/async_fun"},
            {id: 110, pId: 1, name: "用 zTree 方法 更新 节点数据", file: "core/update_fun"},
            {id: 111, pId: 1, name: "单击 节点 控制", file: "core/click"},
            {id: 112, pId: 1, name: "展开 / 折叠 父节点 控制", file: "core/expand"},
            {id: 113, pId: 1, name: "根据 参数 查找 节点", file: "core/searchNodes"},
            {id: 114, pId: 1, name: "其他 鼠标 事件监听", file: "core/otherMouse"},

            {id: 2, pId: 0, name: "[excheck] 复/单选框功能 演示", open: false},
            {id: 201, pId: 2, name: "Checkbox 勾选操作", file: "excheck/checkbox"},
            {id: 206, pId: 2, name: "Checkbox nocheck 演示", file: "excheck/checkbox_nocheck"},
            {id: 207, pId: 2, name: "Checkbox chkDisabled 演示", file: "excheck/checkbox_chkDisabled"},
            {id: 208, pId: 2, name: "Checkbox halfCheck 演示", file: "excheck/checkbox_halfCheck"},
            {id: 202, pId: 2, name: "Checkbox 勾选统计", file: "excheck/checkbox_count"},
            {id: 203, pId: 2, name: "用 zTree 方法 勾选 Checkbox", file: "excheck/checkbox_fun"},
            {id: 204, pId: 2, name: "Radio 勾选操作", file: "excheck/radio"},
            {id: 209, pId: 2, name: "Radio nocheck 演示", file: "excheck/radio_nocheck"},
            {id: 210, pId: 2, name: "Radio chkDisabled 演示", file: "excheck/radio_chkDisabled"},
            {id: 211, pId: 2, name: "Radio halfCheck 演示", file: "excheck/radio_halfCheck"},
            {id: 205, pId: 2, name: "用 zTree 方法 勾选 Radio", file: "excheck/radio_fun"},

            {id: 3, pId: 0, name: "[exedit] 编辑功能 演示", open: false},
            {id: 301, pId: 3, name: "拖拽 节点 基本控制", file: "exedit/drag"},
            {id: 302, pId: 3, name: "拖拽 节点 高级控制", file: "exedit/drag_super"},
            {id: 303, pId: 3, name: "用 zTree 方法 移动 / 复制 节点", file: "exedit/drag_fun"},
            {id: 304, pId: 3, name: "基本 增 / 删 / 改 节点", file: "exedit/edit"},
            {id: 305, pId: 3, name: "高级 增 / 删 / 改 节点", file: "exedit/edit_super"},
            {id: 306, pId: 3, name: "用 zTree 方法 增 / 删 / 改 节点", file: "exedit/edit_fun"},
            {id: 307, pId: 3, name: "异步加载 & 编辑功能 共存", file: "exedit/async_edit"},
            {id: 308, pId: 3, name: "多棵树之间 的 数据交互", file: "exedit/multiTree"},

            {id: 4, pId: 0, name: "大数据量 演示", open: false},
            {id: 401, pId: 4, name: "一次性加载大数据量", file: "bigdata/common"},
            {id: 402, pId: 4, name: "分批异步加载大数据量", file: "bigdata/diy_async"},
            {id: 403, pId: 4, name: "分批异步加载大数据量", file: "bigdata/page"},

            {id: 5, pId: 0, name: "组合功能 演示", open: false},
            {id: 501, pId: 5, name: "冻结根节点", file: "super/oneroot"},
            {id: 502, pId: 5, name: "单击展开/折叠节点", file: "super/oneclick"},
            {id: 503, pId: 5, name: "保持展开单一路径", file: "super/singlepath"},
            {id: 504, pId: 5, name: "添加 自定义控件", file: "super/diydom"},
            {id: 505, pId: 5, name: "checkbox / radio 共存", file: "super/checkbox_radio"},
            {id: 506, pId: 5, name: "左侧菜单", file: "super/left_menu"},
            {id: 513, pId: 5, name: "OutLook 样式的左侧菜单", file: "super/left_menuForOutLook"},
            {id: 507, pId: 5, name: "下拉菜单", file: "super/select_menu"},
            {id: 509, pId: 5, name: "带 checkbox 的多选下拉菜单", file: "super/select_menu_checkbox"},
            {id: 510, pId: 5, name: "带 radio 的单选下拉菜单", file: "super/select_menu_radio"},
            {id: 508, pId: 5, name: "右键菜单 的 实现", file: "super/rightClickMenu"},
            {id: 511, pId: 5, name: "与其他 DOM 拖拽互动", file: "super/dragWithOther"},
            {id: 512, pId: 5, name: "异步加载模式下全部展开", file: "super/asyncForAll"},

            {id: 6, pId: 0, name: "其他扩展功能 演示", open: false},
            {id: 601, pId: 6, name: "隐藏普通节点", file: "exhide/common"},
            {id: 602, pId: 6, name: "配合 checkbox 的隐藏", file: "exhide/checkbox"},
            {id: 603, pId: 6, name: "配合 radio 的隐藏", file: "exhide/radio"}
        ];

        $(document).ready(function () {
            var t = $("#tree");
            t = $.fn.zTree.init(t, setting, zNodes);
            demoIframe = $("#testIframe");
            demoIframe.bind("load", loadReady);
            var zTree = $.fn.zTree.getZTreeObj("tree");
            zTree.selectNode(zTree.getNodeByParam("id", 101));

        });

        function loadReady() {
            var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
                    htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
                    maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
                    h = demoIframe.height() >= maxH ? minH : maxH;
            if (h < 1000) h = 1000;
            demoIframe.height(h);
        }

        //-->

    </SCRIPT>
    <script type="text/javascript" language="javascript">

        /*******弹窗**************/

        window.onload = function () {
            rDrag.init(document.getElementById('box'));
        };

        $(function () {
            $(".showbox").click(function () {
                $(".TB_overlayBG").css({
                    display: "block", height: $(document).height()
                });
                $(".box").css({
                    left: ($("body").width() - $(".box").width()) / 2 - 20 + "px",
                    top: ($(window).height() - $(".box").height()) / 2 + $(window).scrollTop() + "px",
                    display: "block"
                });
            });
            $(".close").click(function () {
                $(".TB_overlayBG").css("display", "none");
                $(".box ").css("display", "none");
            });
        })
        /*******切换*****/
        $(document).ready(function () {
            $(".time_sl").click(function () {
                $(".time_select").show();
                $(".time_select01").hide();
            });
            $(".time_sl1").click(function () {
                $(".time_select01").show();
                $(".time_select").hide();
            });
        })
        /*******全选*****/
        $(function () {
            $("#checkAll input[type=checkbox]").click(function () {
                if ($(this).is(":checked") == true) {
                    $(this).parent("tr").next("td").find("input[type=checkbox]").prop("checked", "true");
                } else {
                    $(this).parent("tr").next("td").find("input[type=checkbox]").removeAttr("checked");
                }
            });
        })

        /* $(function() {
         $("#checkAll").click(function() {

         $('input[name="subBox"]').attr("checked",this.checked);
         alert("");
         });
         var $subBox = $("input[name='subBox']");
         $subBox.click(function(){
         $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
         });
         });*/
    </script>
</head>
<body>

<jsp:include page="../homePage/pageBlock/head.jsp"/>


<div class="concent over">
<jsp:include page="../homePage/pageBlock/nav.jsp"/>

<div class="mid over fr">
<div id="tab">
<div class="tab_box">
<div class="list01_top over"><Span>智能竞价</Span> <a href="#" class="question"></a></div>
<div class=" jiangjia_concent over">

<div class="jingjia_left fl over">
    <div class="j_l_top over">
        <span class="fl"><h3>账户目录</h3></span>
        <a href="#" class="fr">刷新</a>
    </div>
    <div class="j_l_top2 over">
        <span class="fl">查找计划单元</span>
        <input class="fr" type="image" src="public/img/search.png">
    </div>
    <div class="j_list01 over">
        <ul id="tree" class="ztree over">

        </ul>
    </div>
    <div class="j_l_under over">
        <a href="#">监控文件夹</a>

    </div>
</div>
<div class="jingjia_right fr over">
<div ID="testIframe" Name="testIframe" width="100%" onLoad="iFrameHeight()">
<div class="content_wraps over">
<div class="wrap_list01 over wd ">
    <ul>
        <li>
            <div class="w_list01 fl over">账户余额：</div>
            <div class="w_list02 fl over"><span>381.1元</span> <span><input type="image" src="public/img/new.png"> 每日预算：￥<input
                    type="text" class="price"></span><span>推广地域:<b>上海</b><b>北京</b></span></div>

        </li>
        <li>
            <div class="w_list01 fl over">是否参与竞价：</div>
            <div class="w_list02 fl over">
                <span><form><input type="radio" checked="checked" name="no">&nbsp;未参与 &nbsp;<input type="radio"
                                                                                                   name="no"> &nbsp;已参加
                    &nbsp;<input type="radio" name="no">&nbsp;全部
                </form></span>
									<span>
										<dl><input type="image" src="public/img/search2.png"></dl>
										<dl><input type="checkbox" style=" margin-top:5px;"></dl>
										<dl>
                                            <input type="text" class="w_text" value="关键词精准查询，多个关键词用半角逗号隔开"
                                                   onfocus="if(value=='关键词精准查询，多个关键词用半角逗号隔开') {value=''}"
                                                   onblur="if (value=='') {value='关键词精准查询，多个关键词用半角逗号隔开'}">
                                            <input type="image" src="public/img/search3.png"></dl>
										</span>
										<span>
											<input type="button" value="高级搜索" class="advanced_search">
										</span>
            </div>
        </li>
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
            <div class="w_list02 fl over"><span><input type="text" class="price"> - <input type="text"
                                                                                           class="price"></span></div>

        </li>
    </ul>
</div>
<div class="w_list03">
<ul>
    <li class="current showbox">设置规则</li>
    <li class="all_check">全选</li>
    <li>检查当前排名</li>
    <li>修改出价</li>
    <li>修改匹配模式</li>
    <li>修改访问网址</li>
    <li>分组</li>
    <li>自定义列</li>

</ul>
<p>当前显示数据日期：昨天</p>

<div class="list4">
<table border="0" cellspacing="0" width="101%">
<tbody>
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
<tr class="list2_box1">
    <td>&nbsp;<input type="checkbox" name="subbox"></td>
    <td>&nbsp;贷款</td>
    <td>&nbsp;1307</td>
    <td>&nbsp;81</td>
    <td>&nbsp;1380.69</td>
    <td>&nbsp;6.20%</td>
    <td>&nbsp;17</td>
    <td>&nbsp;九星词</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>

<tr class="list2_box2">
    <td>&nbsp;<input type="checkbox" name="subbox"></td>
    <td>&nbsp;贷款</td>
    <td>&nbsp;1307</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;<input type="checkbox" name="subbox"></td>
    <td>&nbsp;贷款</td>
    <td>&nbsp;1307</td>
    <td>&nbsp;81</td>
    <td>&nbsp;1380.69</td>
    <td>&nbsp;6.20%</td>
    <td>&nbsp;17</td>
    <td>&nbsp;九星词</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>

<tr class="list2_box2">
    <td>&nbsp;<input type="checkbox" name="subbox"></td>
    <td>&nbsp;贷款</td>
    <td>&nbsp;1307</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;<input type="checkbox" name="subbox"></td>
    <td>&nbsp;贷款</td>
    <td>&nbsp;1307</td>
    <td>&nbsp;81</td>
    <td>&nbsp;1380.69</td>
    <td>&nbsp;6.20%</td>
    <td>&nbsp;17</td>
    <td>&nbsp;九星词</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>

<tr class="list2_box2">
    <td>&nbsp;<input type="checkbox" name="subbox"></td>
    <td>&nbsp;贷款</td>
    <td>&nbsp;1307</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;<input type="checkbox" name="subbox"></td>
    <td>&nbsp;贷款</td>
    <td>&nbsp;1307</td>
    <td>&nbsp;81</td>
    <td>&nbsp;1380.69</td>
    <td>&nbsp;6.20%</td>
    <td>&nbsp;17</td>
    <td>&nbsp;九星词</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>

<tr class="list2_box2">
    <td>&nbsp;<input type="checkbox" name="subbox"></td>
    <td>&nbsp;贷款</td>
    <td>&nbsp;1307</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;<input type="checkbox" name="subbox"></td>
    <td>&nbsp;贷款</td>
    <td>&nbsp;1307</td>
    <td>&nbsp;81</td>
    <td>&nbsp;1380.69</td>
    <td>&nbsp;6.20%</td>
    <td>&nbsp;17</td>
    <td>&nbsp;九星词</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>

<tr class="list2_box2">
    <td>&nbsp;<input type="checkbox" name="subbox"></td>
    <td>&nbsp;贷款</td>
    <td>&nbsp;1307</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr class="list2_box1">
    <td>&nbsp;<input type="checkbox" name="subbox"></td>
    <td>&nbsp;贷款</td>
    <td>&nbsp;1307</td>
    <td>&nbsp;81</td>
    <td>&nbsp;1380.69</td>
    <td>&nbsp;6.20%</td>
    <td>&nbsp;17</td>
    <td>&nbsp;九星词</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>

<tr class="list2_box2">
    <td>&nbsp;<input type="checkbox" name="subbox"></td>
    <td>&nbsp;贷款</td>
    <td>&nbsp;1307</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
    <td>&nbsp;1331</td>
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
    <td>&nbsp;</td>
</tr>
</tbody>
</table>

</div>
<div class="page2">
    <a href="#" class="nextpage1"><span></span></a><a href="#">1</a><a href="#">2</a><a href="#">3</a><a
        href="#">4</a><a href="#">5</a><a href="#">6</a><a href="#" class="nextpage2"><span></span></a><span
        style="margin-right:10px;">跳转到 <input type="text" class="price"></span>&nbsp;&nbsp;<a href="#"> GO</a>

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
</div>
<div class="TB_overlayBG"></div>
<div class="box" style="display:none">
    <h2 id="box">设置规则<a href="#" class="close">关闭</a></h2>

    <div class="mainlist">
        <ul>
            <li>
                <div>
                    <form name="biddingfrm" action="/bidding/save">
                        时段竞价：
                        <input type="radio" checked="checked" name="s" class="time_sl">&nbsp;单时段竞价 &nbsp;
                        <input type="radio" name="m" class="time_sl1"> &nbsp;多时段竞价 &nbsp;
                    </form>
                </div>
                <div class="time_select "><select>
                    <option>0点</option>
                </select>&nbsp;至&nbsp;<select>
                    <option>24点</option>
                </select></div>
                <div class="time_select01 hides">
                    <ul>
                        <li><input type="text"><select>
                            <option>0点</option>
                        </select>&nbsp;至&nbsp;<select>
                            <option>12点</option>
                        </select>&nbsp;上午
                        </li>
                        <li><input type="checkbox"><select>
                            <option>12点</option>
                        </select>&nbsp;至&nbsp;<select>
                            <option>14点</option>
                        </select>&nbsp;上午
                        </li>
                        <li><input type="checkbox"><select>
                            <option>14点</option>
                        </select>&nbsp;至&nbsp;<select>
                            <option>24点</option>
                        </select>&nbsp;上午
                        </li>
                    </ul>

                </div>
            </li>
            <li>
                <form>竞价模式：<input type="radio" checked="checked" name="jinji">&nbsp;经济 &nbsp;<input type="radio"
                                                                                                    name="jinji"> &nbsp;快速
                    &nbsp;</form>
            </li>
            <li>
                <form>竞价规则：<select name="device">
                    <option value="pc">计算机</option>
                    <option value="mobile">移动端</option>
                </select></form>
            </li>
            <li>
                <ul id="pos">
                    <li><input type="radio" name="guize" id="ck1" checked="checked"><span class="mainlist_left">左侧：1位 </span><span>最高出价（最低区间0.01）<input type="text" data-idx="price1" class="price2"></span>
                        <span>最低出价（最低区间0.01）<input type="text" data-idx="price1" class="price2"></span>
                    </li>

                    <li><input type="radio" name="guize" id="ck2">
                        <span class="mainlist_left">左侧：2-3位 </span><span>最高出价（最低区间0.01）
                            <input data-idx="price2" type="text" class="price2" disabled></span>
                        <span>最低出价（最低区间0.01）<input data-idx="price2" type="text" class="price2" disabled></span>
                    </li>


                    <li><input type="radio" name="guize" id="ck3">
                        <span class="mainlist_left">右侧：1-3位</span><span>最高出价（最低区间0.01）
                            <input data-idx="price3" type="text" class="price2" disabled></span>
                        <span>最低出价（最低区间0.01）<input data-idx="price3" type="text" class="price2" disabled></span>
                    </li>

                    <li><input type="radio" name="guize" id="ck4">
                        <span class="mainlist_left"> 左侧：<input data-idx="price4" type="text" class="price2" disabled> 位 </span>

                    <span>最高出价（最低区间0.01）<input data-idx="price4"
                                               type="text" class="price2" disabled></span>
                        <span>最低出价（最低区间0.01）<input data-idx="price4" type="text" class="price2" disabled></span>
                    </li>
                </ul>
                </form>
            </li>
            <li>
                <span class="fl">当出价达不到排名时  </span>

                <form class="fl" style=" margin-left:5px;">
                    <input type="radio" name="best"> &nbsp;自动匹配最佳排名
                    &nbsp;<input type="radio" name="restore"> &nbsp;恢复账户设置 &nbsp;
                </form>
            </li>
            <li>
                <p>自动竞价模式：</p>
                <ul>
                    <li><input type="radio" name="once" checked="checked"><span>单次竞价</span>
                    </li>

                    <li><input type="radio" name="notonce"><span>重复竞价速度 每隔 <select name="interval">
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
                <li class="current">保存</li>
                <li>保存并运行</li>
                <li class="close">取消</li>

            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">

    $(function(){
        var lis = $("#pos").children();
        lis.each(function(){
            var checkbox = $(this).children().first();

            checkbox.click(function(){
                // var lis = $("#pos").children();
                // lis.each(function(){

                var checkbox = $(this);



                console.log(checkbox.attr("id"));s
                var id = checkbox.attr("id");

                $(".price2").each(function(){

                    if($(this).data("idx") == "price" + id.charAt(2)){
                        $(this).removeAttr("disabled");
                    }else{
                        $(this).attr("disabled","true");
                    }
                    // });
                })

            })

        });
    });
</script>
</body>
</html>
