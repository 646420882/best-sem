<%--
  Created by IntelliJ IDEA.
  User: xiaowei
  Date: 15-9-21
  Time: 上午11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="TB_overlayBG"></div>
<div class="box" style="display:none;*width:400px;" id="findOrReplace">
    <form submit-type="ajax">
        <input id="forType" type="hidden"/>

        <h2 id="findOrReplaceH"><span class="fl">文字替换</span><a href="#" class="close"
                                                               onclick="commons.foRClose()">关闭</a>
        </h2>


        <div class="mainlist">
            <label id="forTitle">替换所选项目中的文字(不能替换已提交的关键字名称)</label>
            <label>物料选择方式：</label>
            <select name="checkType" no-sub="true">
                <option value="0">当前选中</option>
                <option value="1">所有</option>
            </select>

            <label>查找文字：</label>
            <input name="findText" type="text" required/>

            <label>位于：</label>
            <select id="forPlace" name="forPlace">
            </select>

            <label class="checkbox-inline"><input type="checkbox" checked="checked" name="fQcaseLowerAndUpper"
                                                  />匹配大小写</label>
            <label class="checkbox-inline"><input type="checkbox" name="fQcaseAll" >匹配整个字词</label>
            <label class="checkbox-inline"><input type="checkbox" name="fQigonreTirm" >忽略文字两端空格</label>


            <label>替换为：</label>
            <input name="replaceText" type="text"/>

            <label class="checkbox-inline"><input type="checkbox" name="rQigonreTirm" />忽略文字两端空格</label>

            <%--<ul class="zs_set">--%>
            <%--<li><input type="radio" checked="checked" name="no1">&nbsp; 所有推广计划</li>--%>
            <%--<li><input type="radio" name="no1">&nbsp; 已下载的推广计划</li>--%>
            <%--<li><input type="radio" name="no1">&nbsp; 从最新的推广计划列表中选择</li>--%>
            <%--</ul>--%>
            <div class="zs_sets over">
            </div>
        </div>
        <div class="main_bottom">
            <div class="w_list03">
                <ul>
                    <li class="current" onclick="$.foROk(this)">确认</li>
                    <li class="close" onclick="commons.foRClose()">取消</li>
                </ul>
            </div>
        </div>
    </form>
</div>
