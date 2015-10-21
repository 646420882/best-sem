<%--
  Created by IntelliJ IDEA.
  User: xiaowei
  Date: 15-9-21
  Time: 上午11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="box" style="display:none;*width:400px;" id="findOrReplace">
    <form submit-type="ajax">
        <input id="forType" type="hidden"/>
        <h2 id="findOrReplaceH"><span class="fl">文字替换</span><a href="#" class="close"
                                                               onclick="commons.foRClose()">×</a>
        </h2>
        <div class="mainlist">
            <label id="forTitle">替换所选项目中的文字(不能替换已提交的关键字名称)</label>
            <ul>
                <li>
                    <div class="replaceleft fl">
                        <label>物料选择方式：</label>
                    </div>
                    <div class="replaceright fl">
                        <select name="checkType" no-sub="true">
                            <option value="0">当前选中</option>
                            <option value="1">所有(计划下所有)</option>
                        </select>
                    </div>
                </li>
                <li>
                    <div class="replaceleft fl">
                        <label>查找文字：</label>
                    </div>
                    <div class="replaceright fl">
                        <input name="findText" type="text" required/>
                    </div>
                </li>
                <li>
                    <div class="replaceleft fl">
                        <label>位于：</label>
                    </div>
                    <div class="replaceright fl">
                        <select id="forPlace" name="forPlace">
                        </select>
                        </div>
                </li>
                <li>
                    <label class="checkbox-inline fl"><input type="checkbox" checked="checked" name="fQcaseLowerAndUpper"
                            />匹配大小写</label>
                    <label class="checkbox-inline fl"><input type="checkbox" name="fQcaseAll" >匹配整个字词</label>
                    <label class="checkbox-inline fl"><input type="checkbox" name="fQigonreTirm" >忽略文字两端空格</label>
                </li>
                <li>
                    <div class="replaceleft fl">
                        <label>替换为：</label>
                    </div>
                    <div class="replaceright fl">
                        <input name="replaceText" type="text"/>
                        <label class="checkbox-inline"><input type="checkbox" name="rQigonreTirm" />复制相匹配的项目。然后替换所复制项目中的文字</label>
                        <label class="checkbox-inline"><input type="checkbox" name="rQigonreTirm" />忽略文字两端空格</label>
                    </div>
                </li>
            </ul>
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

<div class="box" style="display:none;*width:400px;" id="findBatchDel">
    <form submit-type="ajax">
        <h2 id="batchDel"><span class="fl">批量删除</span><a href="#" class="close"
                                                               onclick="commons.foRClose()">×</a>
        </h2>
        <div class="mainlist">
            <ul>
                <li id="pageNew">
                    <div class="replaceleft fl">
                        <label>物料选择方式：</label>
                    </div>
                    <div class="replaceright fl">
                        <select id="checkType" no-sub="true">
                            <option value="0">当前选中</option>
                            <option value="1">所有</option>
                        </select>
                    </div>
                </li>
            </ul>
            <div class="zs_sets over">
            </div>
        </div>
        <div class="main_bottom">
            <div class="w_list03">
                <ul>
                    <li class="current" onclick="$.foBatch(this)">确认</li>
                    <li class="close" onclick="commons.foRClose()">取消</li>
                </ul>
            </div>
        </div>
    </form>
</div>


