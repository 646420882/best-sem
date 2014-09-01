<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2014-9-1
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/accountCss/style.css">
    <style type="text/css">

        .list4 table {
            border: 1px solid #eaf0f3;
            overflow: auto;
            width: 100%;
        }
        .zs_function{
            margin-top:10px;
        }
    </style>
</head>
<body>
<div style="background-color: #f3f5fd; width: 900px; height: 700px">
    <div class="assembly_top over">
            <div class="fl"><h3>关键词拼装</h3></div>
             <div class="fr">
               <span class="fr">推广地域<a href="#">使用账户推广地域</a></span>
               <span class="fr">高级设置<a href="#">未设置</a></span>
            </div>
    </div>
    <div class="assembly_under over">
        <div class="assembly_left">
            <div class="keyword_left fl over">
                <div class="k_l_top over">
                    <span>已添加关键词（1/500）</span>   <a class="question" href="#"></a>
                </div>
                <div class="keyworld_text over">

                    <div class="keyworld_text2 fl">
                        <ul>
                            <li></li>
                        </ul>
                    </div>
                </div>
                <div class="k_l_under over">
                    <div class="k_l_under1 over">
                        <ul>
                            <li><select class="team04 color"><option>请选择推广计划</option></select><select class="team04 color"><option>请选择推广单元</option></select></li>
                            <li><select class="team05 color"><option>保存</option></select></li>
                        </ul>
                    </div>
                </div>

            </div>

            </div>

        <div class="assembly_right fr  over">
            <div class="assembly_right_top over">
                    <ul class="assembly_checkbox">
                        <li class="current">可能适合你的词</li>
                        <li>行业词</li>

                    </ul>
                <div class="assembly_right_under over">
                      <div class="containers over">

                                <div class="assembly_search over">
                                    搜索相关词 <input type="text">

                                </div>
                              <div class="list4">
                                  <div class="zs_function over">
                                      <ul class="fl">
                                          <li><a href="#" id="addKeyword"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加全部</b></a></li>

                                      </ul>

                                  </div>
                                  <table width="100%" cellspacing="0" border="0" width="500px">
                                      <thead>
                                      <tr class="list02_top">
                                          <td>&nbsp;关键词</td>
                                          <td>&nbsp;展现理由</td>
                                          <td>&nbsp;日均搜索量</td>
                                          <td>&nbsp;竞争激烈程度
                                              <div class="set fr"></div>
                                          </td>
                                      </tr>
                                      </thead>
                                      <tbody>
                                        <tr>
                                            <td>&nbsp;北京婚博会</td>
                                            <td>&nbsp;111</td>
                                            <td>&nbsp;1111</td>
                                            <td>&nbsp;1111 </td>
                                        </tr>
                                      </tbody>

                                  </table>
                              </div>
                              <div class="zhanghu_input"></div>

                     </div>
                    <div class="containers over hides">

                        <div class="assembly_top2 over" >
                                <select id="trade">
                                <option value="" selected="selected">请选择行业</option>
                                <option value="电商">电商</option>
                                <option value="房产">房产</option>
                                <option value="教育">教育</option>
                                <option value="金融">金融</option>
                                <option value="旅游">旅游</option>
                            </select>
                                <select id="category">
                                    <option value="电商">电商</option>
                                    <option value="房产">房产</option>
                                    <option value="教育">教育</option>
                                    <option value="金融">金融</option>
                                    <option value="旅游">旅游</option>
                                </select>
                        </div>
                        <div class="zs_function over">
                            <ul class="fl">
                                <li><a href="#" id="addKeyword"><span class="zs_top"><img src="../public/img/zs_function1.png"></span><b>添加全部</b></a></li>

                            </ul>

                        </div>
                        <div class="list4">
                            <table width="100%" cellspacing="0" border="0" width="500px">
                                <thead>
                                <tr class="list02_top">
                                    <td>&nbsp;关键词</td>
                                    <td>&nbsp;展现理由</td>
                                    <td>&nbsp;日均搜索量</td>
                                    <td>&nbsp;竞争激烈程度
                                        <div class="set fr"></div>
                                    </td>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>&nbsp;北京婚博会</td>
                                    <td>&nbsp;111</td>
                                    <td>&nbsp;1111</td>
                                    <td>&nbsp;1111 </td>
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
<script type="text/javascript" src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript">
    $(function () {
        var $tab_li = $('.assembly_checkbox li');
        $('.assembly_checkbox li').click(function () {
            $(this).addClass('current').siblings().removeClass('current');
            var index = $tab_li.index(this);
            $('div.assembly_right_under > div').eq(index).show().siblings().hide();
        });
    });
</script>
</body>
</html>
