<%--
  Created by IntelliJ IDEA.
  User: XiaoWei
  Date: 2015/2/5
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style type="text/css">

    .list4 table {
        border: 1px solid #eaf0f3;
        overflow: auto;
        min-width: 562px;
    }

    .keyworld_text2 ul li {
        word-wrap: break-word;
    }

    .keyword_left ul li select {
        margin: 20px 2% 0 2%;
        width: 95%;
        height: 24px;
        line-height: 22px;
        border: 1px solid #ccc;

    }

    .keyword_left ul li a, .assembly_search a {
        display: block;
        margin: 10px 10px 0 0;
        background: #ffbc04;
        height: 24px;
        line-height: 24px;
        padding: 0 15px;
        color: #000;
    }

    .assembly_search a {
        margin: 0 0 0 10px;

    }

    .assembly_search input {
        width: 200px;
    }


</style>
<div class="box" style="background-color: #f3f5fd; width: 900px; height: 550px;display: none;" id="addKeywordDiv">
    <h2 id="kAdd">添加关键字<a href="javascript:void(0)" onclick="closeAddKeywordDialog()" class="close">关闭</a></h2>

    <div class="assembly_under over">
        <div class="keyword_left fl over" style="height:460px;">
            <ul>
                <li><select id="campaign_select" class="team04">
                </select><select id="adgroup_select" class="team04" onchange="validateNoAllowKeyword(this.value)">
                </select></li>
                <li>
                    <div class="assembly_search over">
                                    <span style="margin-left: 10%;">
                                    <input id="price" type="text" placeholder="请输入关键词出价,默认为0.1"
                                           onkeypress='until.regDouble(this)' maxlength="5">
                                    </span>
                    </div>
                </li>
                <li>
                    <select id="matchType" class="team04">
                        <option value="1">精确</option>
                        <option value="2">短语</option>
                        <option value="3">广泛</option>
                    </select>

                    <div id="phraseTypeDiv" style="display: none;"><select id="phraseType" class="team04">
                        <option value="1">同义包含</option>
                        <option value="2">精确包含</option>
                        <option value="3">核心包含</option>
                    </select></div>
                </li>
                <li><a class="fr" href="javascript:saveKeyword();">保存</a><span id="bdAccountId"
                                                                               style="display: none"></span></li>
                <li>&nbsp;</li>
                <li>
                    <div class="assembly_search over">单元"广,短否定词"：<span id="adNeg"></span></div>
                </li>
                <li>
                    <div class="assembly_search over">单元"精确否定词"：<span id="adExNeg"></span></div>
                </li>
                <li>
                    <div class="assembly_search over">计划"广,短否定词"：<span id="caNeg"></span></div>
                </li>
                <li>
                    <div class="assembly_search over">计划"精确否定词"：<span id="caExNeg"></span></div>
                </li>
            </ul>

        </div>

        <div class="assembly_right fr  over">
            <div class="assembly_right_top over">
                <ul class="assembly_checkbox">
                    <li id="bWord" class="current">可能适合你的词</li>
                    <li id="tradeWord">行业词</li>
                </ul>
                <div class="assembly_right_under over">
                    <div class="containers over">
                        <div class="assembly_search over">
                            <span class="fl">搜索相关词 <input id="searchKeyword" type="text"/></span><a class="fl"
                                                                                                    href="javascript: searchKeyword();">搜索</a>
                        </div>
                        <div class="list4" style="height: 336px">

                            <table style="width: 100%; overflow-y: auto" cellspacing="0" border="0">
                                <thead>
                                <tr class="list02_top">
                                    <td><input id="checkbox1" type="checkbox"/></td>
                                    <td>&nbsp;关键词</td>
                                    <td>&nbsp;日均搜索量</td>
                                    <td>&nbsp;竞争激烈程度</td>
                                    <td>&nbsp;展现理由</td>
                                </tr>
                                </thead>
                                <tbody id="tbody1">
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="containers over hides">
                        <div class="assembly_top2 over">
                            <select id="trade">
                                <option value="" selected="selected">请选择行业</option>
                                <option value="电商">电商</option>
                                <option value="房产">房产</option>
                                <option value="教育">教育</option>
                                <option value="金融">金融</option>
                                <option value="旅游">旅游</option>
                            </select>
                            <select id="category">
                            </select>
                        </div>
                        <%--<div class="zs_function over">
                            <ul class="fl">
                                <li><a href="#" id="addKeyword"><span class="zs_top"><img
                                        src="../public/img/zs_function1.png"></span><b>添加全部</b></a></li>
                            </ul>
                        </div>--%>
                        <div class="list4" style="height: 358px">
                            <table style="width: 100%; overflow-y: auto" cellspacing="0" border="0">
                                <thead>
                                <tr class="list02_top">
                                    <td><input id="checkbox2" type="checkbox"/></td>
                                    <td>&nbsp;行业</td>
                                    <td>&nbsp;计划</td>
                                    <td>&nbsp;单元</td>
                                    <td>&nbsp;关键词</td>
                                </tr>
                                </thead>
                                <tbody id="tbody2">
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
