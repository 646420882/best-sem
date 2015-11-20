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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/zTreeStyle/zTreeStyle.css">
    <style type="text/css">

        .list4 table {
            border: 1px solid #eaf0f3;
            overflow: auto;
            width: 100%;
        }

        .assembly_under{
            height:440px;

        }
        .newkeyword_right_mid textarea{
            height:200px;
        }

    </style>
</head>
<body>
<div style="background-color: #f3f5fd; width: 900px; height: 700px">
    <div class="addplan_top over">
        <ul>
            <li class="current">1、输入内容</li>
            <li><span></span>2、验证数据</li>
        </ul>
    </div>
    <div class="plan_under">
        <div class="containers newkeyword_mid">
            <div class="newkeyeord_top over">
                <h3>关键词目标</h3>
                <div class="newkeyeord_title over">
                    <ul class="over" >
                        <li><label class='checkbox-inlines'><input type="radio" checked="checked" name="Target" id="Target" class="current">选择推广计划、推广单元</label></li>
                        <li><label class='checkbox-inlines'><input type="radio"  name="Target"  id="Targets" class="current">输入信息包含推广计划名称（第一项）、推广单元名称（第二项）</label></li>
                    </ul>
                    <div class="newkeyword_content over">
                        <div class="containers2 over">
                            <div class="newkeyword_right over" style="width:100%;">
                                <h3> 删除关键词 </h3>
                                <p>请输入关键词信息（每行一个），并用Tab键或逗号（英文）分隔各字段，也可直接从Excel复制并粘贴</p>
                                <div class="newkeyword_right_mid">
                                    <p>格式：推广计划名称（必填），推广单元名称（必填），关键词名称（必填）</p>
                                    <p>例如：北京推广，礼品，鲜花</p>
                                    <textarea>

                                    </textarea>
                                    <p>或者从相同格式的csv文件输入：<input type="button" class="zs_input2" value="选择文件">&nbsp;(<20万行)</p>
                                    <p><input type="checkbox">&nbsp;用这些关键词替换目标推广单元的所有对应内容</p>

                                </div>
                            </div>
                        </div>

                    </div>
                </div>


            </div>
            <div class="main_bottom" style="background:none;">
                <div class="w_list03">
                    <ul>
                        <li class="current" id="downloadAccount">下一步</li>
                        <li>完成</li>
                        <li class="close">取消</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="containers over hides">
            <div class="assembly_under over" >
                <div class="assembly_right3 over">
                    <div class="newkeword_end">
                        <ul>
                            <li>
                                <div class="newkeyword_end1"> <span>[-]</span>新增的关键词一个</div>
                                <div class="newkeyword_end2 ">
                                    <p><input type="radio" checked="checkde" name="addnew">添加已选择的关键词</p>
                                    <p><input type="radio"  name="addnew">不添加</p>
                                    <div class="list4" style="height:300px;">
                                        <table width="100%" cellspacing="0" border="0" width="500px">
                                            <thead>
                                            <tr class="list02_top">
                                                <td>&nbsp;推广计划名称</td>
                                                <td>&nbsp;推广单元名称</td>
                                                <td>&nbsp;关键词名称</td>
                                                <td>&nbsp;匹配模式
                                                </td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr class="list2_box2">
                                                <td> &nbsp;北京婚博会</td>
                                                <td>&nbsp;111</td>
                                                <td>&nbsp;1111</td>
                                                <td>&nbsp;1111 </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="main_bottom" style="background:none;">
                        <div class="w_list03">
                            <ul>
                                <li class="current" >上一步</li>
                                <li>完成</li>
                                <li class="close">取消</li>
                            </ul>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/json2/20140204/json2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript">
    $(function () {
        var $tab_li = $('.addplan_top ul li');
        $('.addplan_top ul li').click(function () {
            $(this).addClass('current');
            var index = $tab_li.index(this);
            $('div.plan_under > div').eq(index).show().siblings().hide();
        });
        var $tab_li = $('.newkeyeord_title ul li input');
        $('.newkeyeord_title ul li input').click(function () {
            $(this).addClass('current').siblings().removeClass('current');
            var index = $tab_li.index(this);
            $('div.newkeyword_content > div').eq(index).show().siblings().hide();
        });
        $(".newkeyword_add").click(function(){
            if ($(".newkeyword_list").css("display") == "none") {
                $(".newkeyword_list").show();
            }
            else {
                $(".newkeyword_list").hide();
            }
        });
        $(".newkeyword_end1").click(function(){
            if ($(".newkeyword_end2").css("display") == "none"){
                $(".newkeyword_end2").show();
                $(".newkeyword_end1").find(" span").text("[-]");
            }
            else {
                $(".newkeyword_end2").hide();
                $(".newkeyword_end1").find("span").text("[+]");
            }
        });

        $("#checkAll2").click(function () {
            $('input[name="subbox2"]').prop("checked", this.checked);
        });
        var $subbox2 = $("input[name='subbox2']");
        $subbox2.click(function () {
            $("#checkAll2").prop("checked", $subbox2.length == $("input[name='subbox2']:checked").length ? true : false);
        });


    });
</script>

</body>
</html>
