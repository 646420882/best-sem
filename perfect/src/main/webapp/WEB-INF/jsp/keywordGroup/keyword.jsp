<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2014-08-07
  Time: 上午11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <style type="text/css">
        .list2 table tr td ul li {
            width: 23%;
        }

        .download {
            margin-left: 20px;
        }

        .download a {
            margin-right: 20px;
        }

    </style>
</head>
<body>

<div class="mid over fr">
<div class="list01_top over"><Span>关键词拓展</Span> <a href="#" class="question"></a></div>
<div class="keyword over">
<div class="keyword_left fl over">
    <div class="k_l_top over">
        <Span>待添加关键词（1/500）</Span> <a href="#" class="question"></a>
    </div>
    <p>每行一个关键词，每个最多20个汉字或40个英文。</p>

    <div class="keyworld_text over">
        <div class="keyworld_text1 fl">
            <ul>
                <li>1</li>
            </ul>
        </div>
        <div class="keyworld_text2 fl">
            <ul>
                <li></li>
            </ul>
        </div>
    </div>
    <p>请您认真复核将使用的关键词，确保其不违法、侵权，
        且与您的网页信息相关。</p>

    <div class="k_l_under over">
        <div class="k_l_under1 over">
            <ul>
                <li><span class="team03"><input type="image" src="public/img/team_input1.png">&nbsp;&nbsp;自动分组</span><a
                        href="#" class="question"></a></li>
                <li><select class="team04 color">
                    <option>请选择推广计划</option>
                </select><select class="team04 color">
                    <option>请选择推广单元</option>
                </select></li>
                <li><select class="team05 color">
                    <option>保存</option>
                </select></li>
            </ul>
        </div>
    </div>

</div>
<div class="keyword_right fr over">
<div class="k_r_top over">
    <ul class="tab_menu2 over">
        <li class="current"><a>词根拓词</a></li>
        <li><a>行业拓词</a></li>
    </ul>
    <div class="table_concent2 over">
        <div class="k_r_top2 over">
            <div class="k_top2_text fl">
                <div class="k_top2_text1"><textarea id="textarea1"></textarea></div>
                <p>可输入词根99/100</p>
                <a href="javascript: findWordFromBaidu();" class="become2">开始拓词</a>

            </div>
            <div class="K_top2_detali fr over">
                <div class="k_top2_detali2 over">
                    <div class="list01_top2 over">
                        <span>重点关键词监控</span>
                        <a href="#" class="question"></a>
                    </div>
                    <ul>
                        <li>· 搜索引擎（baidu，google）APP</li>
                        <li>· 抓取搜索引擎先关搜索结果：baidu/google/sougo/soso/bing</li>
                        <li>· 普菲特智能词库</li>
                    </ul>
                </div>
                <div class="k_top2_detali2 over">
                    <div class="list01_top2 over">
                        <span>智能过滤</span>
                        <a href="#" class="question"></a>
                    </div>
                    <ul>
                        <li><input type="checkbox">&nbsp;&nbsp;搜索引擎（baidu，google）APP</li>
                        <li><input type="checkbox">&nbsp;&nbsp;抓取搜索引擎先关搜索结果：baidu/google/sougo/soso/bing</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="k_r_top2  hides over">
            <div class="k_top2_text fl">
                <div class="k_top2_text1">
                    <select>
                        <option>请选择行业</option>
                    </select>
                </div>

                <a href="#" class="become2">开始拓词</a>
            </div>
            <div class="K_top2_detali fr over">
                <div class="k_top2_detali2 over">
                    <div class="list01_top2 over">
                        <span>拓词来源</span>
                        <a href="#" class="question"></a>
                    </div>
                    <ul>
                        <li>· 搜索引擎（baidu，google）APP</li>
                        <li>· 抓取搜索引擎先关搜索结果：baidu/google/sougo/soso/bing</li>
                        <li>· 普菲特智能词库</li>
                    </ul>
                </div>
                <div class="k_top2_detali2 over">
                    <div class="list01_top2 over">
                        <span>智能过滤</span>
                        <a href="#" class="question"></a>
                    </div>
                    <ul>
                        <li><input type="checkbox">&nbsp;&nbsp;搜索引擎（baidu，google）APP</li>
                        <li><input type="checkbox">&nbsp;&nbsp;抓取搜索引擎先关搜索结果：baidu/google/sougo/soso/bing</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="k_r_under over">
    <div class="download over ">
        <a href="#"> 添加全部</a><a href="#">下载全部</a>
    </div>
    <div class="list2">
        <table border="0" cellspacing="0">
            <tbody>
            <tr class="list2_top">
                <td>
                    <ul>
                        <li><span>种子词</span></li>
                        <li><span>关键词</span></li>
                        <li><span>日均搜索量</span></li>
                        <li><span>是否已购买</span></li>
                    </ul>
                </td>
            </tr>
            <tr class="list2_box1">
                <td>
                    <ul>
                        <li>二手车买卖</li>
                        <li>上海二手买卖</li>
                        <li>11</li>
                        <li>否</li>
                    </ul>
                </td>
            </tr>
            <tr class="list2_box2">
                <td>
                    <ul>
                        <li>二手车买卖</li>
                        <li>上海二手买卖</li>
                        <li>11</li>
                        <li>否</li>
                    </ul>
                </td>
            </tr>
            <tr class="list2_box1">
                <td>
                    <ul>
                        <li>二手车买卖</li>
                        <li>上海二手买卖</li>
                        <li>11</li>
                        <li>否</li>
                    </ul>
                </td>
            </tr>
            <tr class="list2_box2">
                <td>
                    <ul>
                        <li>二手车买卖</li>
                        <li>上海二手买卖</li>
                        <li>11</li>
                        <li>否</li>
                    </ul>
                </td>
            </tr>
            <tr class="list2_box1">
                <td>
                    <ul>
                        <li>二手车买卖</li>
                        <li>上海二手买卖</li>
                        <li>11</li>
                        <li>否</li>
                    </ul>
                </td>
            </tr>
            <tr class="list2_box2">
                <td>
                    <ul>
                        <li>二手车买卖</li>
                        <li>上海二手买卖</li>
                        <li>11</li>
                        <li>否</li>
                    </ul>
                </td>
            </tr>
            <tr class="list2_box1">
                <td>
                    <ul>
                        <li>二手车买卖</li>
                        <li>上海二手买卖</li>
                        <li>11</li>
                        <li>否</li>
                    </ul>
                </td>
            </tr>
            <tr class="list2_box2">
                <td>
                    <ul>
                        <li>二手车买卖</li>
                        <li>上海二手买卖</li>
                        <li>11</li>
                        <li>否</li>
                    </ul>
                </td>
            </tr>
            <tr class="list2_box1">
                <td>
                    <ul>
                        <li>二手车买卖</li>
                        <li>上海二手买卖</li>
                        <li>11</li>
                        <li>否</li>
                    </ul>
                </td>
            </tr>
            <tr class="list2_box2">
                <td>
                    <ul>
                        <li>二手车买卖</li>
                        <li>上海二手买卖</li>
                        <li>11</li>
                        <li>否</li>
                    </ul>
                </td>
            </tr>
            </tbody>
        </table>

    </div>
</div>
<div class="page2">
    <a href="#">上一页</a><a href="#">下一页</a><span style="margin-right:10px;">跳转到 <input type="text" class="price"></span>&nbsp;&nbsp;<a href="#"> GO</a>

</div>
</div>
</div>
</div>
<!--<iframe id="tmp_downloadhelper_iframe" style="display: none">#document</iframe>-->
<script type="text/javascript" src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-ui-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript">

    var krFileId;

    $(function () {
        var $tab_li = $('.tab_menu2 li');
        $('.tab_menu2 li').click(function () {
            $(this).addClass('current').siblings().removeClass('current');
            var index = $tab_li.index(this);
            $('div.table_concent2  > div').eq(index).show().siblings().hide();
        });
    });

    var findWordFromBaidu = function () {
        var seedWords = "";
        var words = $("#textarea1").val().split("\n");//种子词数组
        for (var i = 0, l = words.length; i < l; i++) {
            if (words[i].trim().length == 0) {
                continue;
            }
            if (i == 0)
                seedWords += words[i];
            else
                seedWords += "," + words[i];

        }

        $.ajax({
            url: "/getKRWords/bd",
            type: "GET",
            async: false,
            data: {
                "seedWords": seedWords,
                "skip": 0,
                "limit": 10,
                "krFileId": krFileId
            },
            success: function (data, textStatus, jqXHR) {
                krFileId = data.krFileId;
                alert(krFileId);
                alert(JSON.stringify(data.rows));
            }
        });
    };
</script>
</body>
</html>
