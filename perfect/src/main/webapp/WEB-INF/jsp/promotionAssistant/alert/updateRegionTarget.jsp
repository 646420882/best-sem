<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2014/8/31
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<html>
<head>

<head>
<body>--%>

<link rel="shortcut icon" type="image/ico" href="http://tuiguang-s1.bdstatic.com/nirvana/asset/resource/img/1d95fd9f0985feb4.ico">
<script src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/style.css">

<div id="updateRegionDialog" class="ui_dialog" control="planRegionDialog" style="width: 548px; left: 100px; z-index: 401; top: 0px;display: none;">
<input type="hidden" value="${cid}" id="campaignId">

<div class="ui_dialog_body" id="ctrldialogplanRegionDialogbody">
<div class="manage_dialog">

<div class="manage_region" id="planRegionSwitch"> <span id="useAcctRegion">使用账户推广地域</span> &nbsp;|&nbsp; <span class="current_region" id="usePlanRegion">使用计划推广地域</span> </div>
<div id="acctRegionList" style="display: none;"> 账户推广地域：<span ui="" id="ctrllabelacctRegionList" control="acctRegionList"  logswitch="true" class="ui_label" title=""></span><br/><br/>
    <div class="ui_dialog_foot">
        <div  class="ui_button" logswitch="true" control="regionOk" id="useAcctregionOk" ui=""><a href="javascript:void(0)" id="Oklabel" class="ui_button_label">确定</a></div>
        <div class="ui_button" logswitch="true" control="regionCancel" id="useAcctregionCancel" ui=""><a href="javascript:void(0)" id="Cancellabel" class="ui_button_label">取消</a></div>
        <span id="useRegionErrorTooltip"></span>
    </div>
</div>
<div id="DenyAreaNotice" class="common_notice hide">
    <p>您的产品或业务涉及地域性问题，仅能以目前系统设置的地域进行推广；如有问题，请联系您的维护顾问或致电百度推广热线400-890-0088</p>
</div>



<div id="planRegionset" style="display: block;">
<div class="manage_region">
    <input type="radio" title="全部地域" checked="checked" name="region" value="0" id="ctrlradioboxallRegion" control="allRegion"  logswitch="false" class="ui_radiobox" refer="">
    <label class="ui_radiobox_label" for="ctrlradioboxallRegion">全部地域</label>
    <input type="radio" title="部分地域" name="region" value="1" id="ctrlradioboxpartRegion" control="partRegion" logswitch="false" class="ui_radiobox" refer="">
    <label class="ui_radiobox_label" for="ctrlradioboxpartRegion">部分地域</label>
</div>
<div style="display: none;" ui="" id="ctrlregionregionBody" control="regionBody"   class="ui_region" >
<div id="regionList" class="region-secondarea-select">
<dl>
<dt>
    <input type="checkbox" id="ctrlregionregionBodyChina">
    <label for="ctrlregionregionBodyChina">中国地区</label>
</dt>
<dd>
<div id="China_regionBody">
<dl class="whitebg">
<dt>
    <input type="checkbox" id="ctrlregionregionBodyNorth">
    <label for="ctrlregionregionBodyNorth">华北地区</label>
</dt>
<dd>
<div id="North_regionBody">
<div class="leaf">
    <div class="second-area-leaf">
        <input type="checkbox" id="ctrlregionregionBody1">
        <label class=" " for="ctrlregionregionBody1">北京</label>
    </div>
</div>
<div class="leaf">
    <div class="second-area-leaf">
        <input type="checkbox" id="ctrlregionregionBody3">
        <label class=" " for="ctrlregionregionBody3">天津</label>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" id="ctrlregionregionBody13" style="visibility: visible;">
        <label for="ctrlregionregionBody13">河北</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>保定</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>沧州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>承德</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>邯郸</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>衡水</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>廊坊</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>秦皇岛</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>石家庄</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label >唐山</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>邢台</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>张家口</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox"  style="visibility: visible;">
        <label>内蒙古</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>阿拉善盟</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>巴彦淖尔</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>包头</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>赤峰</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>鄂尔多斯</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>呼和浩特</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>呼伦贝尔</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>通辽</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>乌海</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>乌兰察布</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>锡林郭勒盟</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>兴安盟</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>山西</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>长治</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>大同</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label >晋城</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>晋中</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>临汾</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>吕梁</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>朔州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>太原</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>忻州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>阳泉</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>运城</label>
            </div>
        </div>
    </div>
</div>
</div>
</dd>
</dl>
<dl class="graybg">
<dt>
    <input type="checkbox" id="ctrlregionregionBodyNorthEast">
    <label for="ctrlregionregionBodyNorthEast">东北地区</label>
</dt>
<dd>
<div id="NorthEast_regionBody">
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>黑龙江</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>大庆</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>大兴安岭</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>哈尔滨</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>鹤岗</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>黑河</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>鸡西</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>佳木斯</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>牡丹江</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>齐齐哈尔</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>七台河</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>双鸭山</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>绥化</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>伊春</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>吉林</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>白城</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>白山</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>长春</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>吉林</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>辽源</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>四平</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>松原</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>通化</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>延边</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox"style="visibility: visible;">
        <label>辽宁</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>鞍山</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label >本溪</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>朝阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>大连</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>丹东</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>抚顺</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>阜新</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>葫芦岛</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>锦州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>辽阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>盘锦</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>沈阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>铁岭</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>营口</label>
            </div>
        </div>
    </div>
</div>
</div>
</dd>
</dl>
<dl class="whitebg">
<dt>
    <input type="checkbox" id="ctrlregionregionBodyEast">
    <label for="ctrlregionregionBodyEast">华东地区</label>
</dt>
<dd>
<div id="East_regionBody">
<div class="leaf">
    <div class="second-area-leaf">
        <input type="checkbox">
        <label>上海</label>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>福建</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>福州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>龙岩</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>南平</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>宁德</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>莆田</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>泉州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>三明</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>厦门</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>漳州</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>安徽</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>安庆</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>蚌埠</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>亳州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>巢湖</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>池州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>滁州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>阜阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>合肥</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>淮北</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label >淮南</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>黄山</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label >六安</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>马鞍山</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>宿州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label >铜陵</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>芜湖</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label >宣城</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox"  style="visibility: visible;">
        <label >江苏</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>常州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label >淮安</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>连云港</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>南京</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>南通</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>宿迁</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>苏州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>泰州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>无锡</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>徐州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>盐城</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>扬州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>镇江</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>江西</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>抚州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>赣州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>吉安</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>景德镇</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>九江</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>南昌</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>萍乡</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>上饶</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>新余</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>宜春</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label >鹰潭</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>山东</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>滨州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>德州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>东营</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>菏泽</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label >济南</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label >济宁</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label >莱芜</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>聊城</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>临沂</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label >青岛</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>日照</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>泰安</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>潍坊</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>威海</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>烟台</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>枣庄</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>淄博</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label >浙江</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>杭州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>湖州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>嘉兴</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label >金华</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>丽水</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>宁波</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label >衢州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>绍兴</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>台州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>温州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>舟山</label>
            </div>
        </div>
    </div>
</div>
</div>
</dd>
</dl>
<dl class="graybg">
<dt>
    <input type="checkbox" id="ctrlregionregionBodyMiddle">
    <label for="ctrlregionregionBodyMiddle">华中地区</label>
</dt>
<dd>
<div id="Middle_regionBody">
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox"  style="visibility: visible;">
        <label>河南</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>安阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>鹤壁</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>济源</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>焦作</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>开封</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>漯河</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>洛阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>南阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>平顶山</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>濮阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>三门峡</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>商丘</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>新乡</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>信阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>许昌</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>郑州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>周口</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>驻马店</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>湖北</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>鄂州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>恩施</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>黄冈</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>黄石</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>荆门</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>荆州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>潜江</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>神农架</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>十堰</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>随州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>天门</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>武汉</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>咸宁</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>仙桃</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label >襄阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>孝感</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>宜昌</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>湖南</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>常德</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>长沙</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>郴州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>衡阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>怀化</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>娄底</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>邵阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>湘潭</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>湘西</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>益阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>永州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>岳阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>张家界</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>株洲</label>
            </div>
        </div>
    </div>
</div>
</div>
</dd>
</dl>
<dl class="whitebg">
<dt>
    <input type="checkbox" id="ctrlregionregionBodySouth">
    <label for="ctrlregionregionBodySouth">华南地区</label>
</dt>
<dd>
<div id="South_regionBody">
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>广东</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>潮州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>东莞</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>佛山</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>广州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>河源</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>惠州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>江门</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>揭阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>茂名</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>梅州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>清远</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>汕头</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>汕尾</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>韶关</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>深圳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>阳江</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>云浮</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>湛江</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>肇庆</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>中山</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>珠海</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>海南</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>白沙黎族自治县</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>保亭黎族苗族自治县</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>昌江黎族自治县</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>澄迈县</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>儋州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>定安县</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>东方</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label >海口</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>乐东黎族自治县</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>临高县</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>陵水黎族自治县</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>琼海</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>琼中黎族苗族自治县</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>三亚</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>屯昌县</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>万宁</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>文昌</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label >五指山</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>广西</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>百色</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>北海</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>崇左</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>防城港</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>贵港</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>桂林</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>河池</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>贺州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>来宾</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>柳州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>南宁</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>钦州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>梧州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label >玉林</label>
            </div>
        </div>
    </div>
</div>
</div>
</dd>
</dl>
<dl class="graybg">
<dt>
    <input type="checkbox" id="ctrlregionregionBodySouthWest">
    <label for="ctrlregionregionBodySouthWest">西南地区</label>
</dt>
<dd>
<div id="SouthWest_regionBody">
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>贵州</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>安顺</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>毕节</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>贵阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>六盘水</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>黔东南</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>黔南</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>黔西南</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>铜仁</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>遵义</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>四川</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>阿坝</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>巴中</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>成都</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label >达州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>德阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>甘孜</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>广安</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>广元</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>乐山</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>凉山</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label >泸州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>眉山</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>绵阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>南充</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>内江</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>攀枝花</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>遂宁</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>雅安</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>宜宾</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>自贡</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>资阳</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>西藏</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>阿里</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>昌都</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>拉萨</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>林芝</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>那曲</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>日喀则</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>山南</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>云南</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>保山</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>楚雄</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>大理</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>德宏</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>迪庆</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>红河</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>昆明</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label >丽江</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>临沧</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>怒江</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>普洱</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>曲靖</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>文山</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>西双版纳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>玉溪</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>昭通</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf">
    <div class="second-area-leaf">
        <input type="checkbox">
        <label>重庆</label>
    </div>
</div>
</div>
</dd>
</dl>
<dl class="whitebg">
<dt>
    <input type="checkbox" id="ctrlregionregionBodyNorthWest">
    <label for="ctrlregionregionBodyNorthWest">西北地区</label>
</dt>
<dd>
<div id="NorthWest_regionBody">
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>甘肃</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>白银</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>定西</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>甘南</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>嘉峪关</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>金昌</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>酒泉</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>兰州</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>临夏</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>陇南</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>平凉</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>庆阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>天水</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>武威</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>张掖</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>宁夏</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>固原</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>石嘴山</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>吴忠</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>银川</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>中卫</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>青海</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>果洛</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>海北</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>海东</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>海南</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label >海西</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>黄南</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>西宁</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>玉树</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label>陕西</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>安康</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>宝鸡</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>汉中</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>商洛</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>铜川</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>渭南</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>西安</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>咸阳</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>延安</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>榆林</label>
            </div>
        </div>
    </div>
</div>
<div class="leaf area-hover-event">
    <div class="first-area-container">
        <input type="checkbox" style="visibility: visible;">
        <label >新疆</label>
        <label class="checked-num-event"></label>
        <span class="half-checked-icon hide"></span> </div>
    <div class="second-area-container hide">
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>阿克苏</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label >阿拉尔</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>阿勒泰</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>巴音郭楞</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>博尔塔拉</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>昌吉</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>哈密</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>和田</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>喀什</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>克拉玛依</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>克孜勒苏柯尔克孜</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>石河子</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>塔城</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>图木舒克</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>吐鲁番</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>五家渠</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox" >
                <label>乌鲁木齐</label>
            </div>
        </div>
        <div class="leaf">
            <div class="second-area-leaf">
                <input type="checkbox">
                <label>伊犁</label>
            </div>
        </div>
    </div>
</div>
</div>
</dd>
</dl>
<dl class="graybg">
    <dt>
        <input type="checkbox" id="ctrlregionregionBodyOther">
        <label for="ctrlregionregionBodyOther">其他地区</label>
    </dt>
    <dd>
        <div id="Other_regionBody">
            <div class="leaf">
                <div class="second-area-leaf">
                    <input type="checkbox">
                    <label>香港</label>
                </div>
            </div>
            <div class="leaf">
                <div class="second-area-leaf">
                    <input type="checkbox">
                    <label>台湾</label>
                </div>
            </div>
            <div class="leaf">
                <div class="second-area-leaf">
                    <input type="checkbox">
                    <label>澳门</label>
                </div>
            </div>
        </div>
    </dd>
</dl>
</div>
</dd>
</dl>
<dl>
    <dt>
        <input type="checkbox" id="ctrlregionregionBodyAbroad">
        <label for="ctrlregionregionBodyAbroad">国外</label>
    </dt>
    <dd>
        <div id="Abroad_regionBody" class="graybg">
            <div class="leaf">
                <div class="second-area-leaf">
                    <input type="checkbox">
                    <label>日本</label>
                </div>
            </div>
            <div class="leaf">
                <div class="second-area-leaf">
                    <input type="checkbox">
                    <label>其他国家（除日本外）</label>
                </div>
            </div>
        </div>
    </dd>
</dl>
</div>
</div>
<div class="ui_dialog_foot">
    <div  class="ui_button" logswitch="true" control="regionOk" id="ctrlbuttonregionOk" ui=""><a href="javascript:void(0)" id="ctrlbuttonregionOklabel" class="ui_button_label">确定</a></div>
    <div class="ui_button" logswitch="true" control="regionCancel" id="ctrlbuttonregionCancel" ui=""><a href="javascript:void(0)" id="ctrlbuttonregionCancellabel" class="ui_button_label">取消</a></div>
    <span id="regionErrorTooltip"></span>
</div>


</div>
</div>
</div>
</div>

<script type="text/javascript">
    $('.ui_radiobox').click(function(){
        if(this.id == 'ctrlradioboxpartRegion'){
            $('#ctrlregionregionBody').show();
        }else{
            $('#ctrlregionregionBody').hide();
        }
    });
    $('.area-hover-event').mouseover(function(){
        $(this).addClass('ie7-index');
        $(this).children('.first-area-container').addClass('first-area-container-hover');
        $(this).children('.second-area-container').removeClass('hide');
        changeNumber(this);
    }).mouseout(function(){
        $(this).removeClass('ie7-index');
        $(this).children('.first-area-container').removeClass('first-area-container-hover');
        $(this).children('.second-area-container').addClass('hide');
    });
    $('.second-area-container').find(':checkbox').change(function(){
        var parent = $(this).parents('.area-hover-event');
        changeNumber(parent);
    });
    //checked-num-event
    function changeNumber(obj){
        var count = $(obj).children('.second-area-container').find(':checkbox').length;
        var cknum = $(obj).children('.second-area-container').find(':checkbox:checked').length;
        if(cknum == count){
            $(obj).children('.first-area-container').find(':checkbox').prop('checked',true);
            $(obj).children('.first-area-container').find(':checkbox').attr('style','visibility:visible;');
            $(obj).children('.first-area-container').find('.half-checked-icon').addClass('hide').removeClass('half-checked-icon-hover');
            $(obj).find('.checked-num-event').removeClass('checked-num').html('')
        }else if(cknum == 0){
            $(obj).children('.first-area-container').find(':checkbox').attr('style','visibility:visible;');
            $(obj).children('.first-area-container').find('.half-checked-icon').addClass('hide').removeClass('half-checked-icon-hover');
            $(obj).children('.first-area-container').find(':checkbox').prop('checked',false);
            $(obj).find('.checked-num-event').removeClass('checked-num').html('')
        }else{
            $(obj).find('.checked-num-event').addClass('checked-num').html(cknum + "/" + count);
            $(obj).children('.first-area-container').find(':checkbox').attr('style','visibility:hidden;');
            $(obj).children('.first-area-container').find(':checkbox').prop('checked',true);
            $(obj).children('.first-area-container').find('.half-checked-icon').removeClass('hide').addClass('half-checked-icon-hover');
        }
    }
    $(function(){
        var ck = $('#ctrlradioboxpartRegion').prop('checked');
        if(ck){
            $('#ctrlregionregionBody').show();
        }
    });
    /*******input_checked*****/

    $(function() {
        $("dt input[type=checkbox]").click(function(){
            if($(this).is(":checked") == true){
                $(this).parent("dt").next("dd").find("input[type=checkbox]").prop("checked", "true");
            }else{
                $(this).parent("dt").next("dd").find("input[type=checkbox]").removeAttr("checked");
            }
        });
    });
    $(function() {
        $(".first-area-container input[type=checkbox]").click(function(){
            if($(this).is(":checked") == true){
                $(this).parent(".first-area-container").next(".second-area-container").find("input[type=checkbox]").prop("checked", "true");
            }else{
                $(this).parent(".first-area-container").next(".second-area-container").find("input[type=checkbox]").removeAttr("checked");
            }
        });
    });
    $(function() {
        $(".second-area-leaf input[type=checkbox]").click(function(){
            if($(this).is(":checked") == true){
                $(this).parent("dt").find("input[type=checkbox]").prop('checked',true);
            }else{
                $(this).parent("dt").find("input[type=checkbox]").removeAttr("checked");

            }
        });
    });
    /*******切换*****/
    $(document).ready(function(){
        $("#useAcctRegion").click(function(){
            $("#acctRegionList").show();
            $("#useAcctRegion").addClass("current_region");
            $("#planRegionset").hide();
            $("#usePlanRegion").removeClass("current_region");

        });
        $("#usePlanRegion").click(function(){
            $("#planRegionset").show();
            $("#usePlanRegion").addClass("current_region");
            $("#acctRegionList").hide();
            $("#useAcctRegion").removeClass("current_region");

        });
    })
    /*******关闭窗口*****/
    $("#ctrldialogplanRegionDialogclose").click(function(){
        $("#updateRegionDialog").css("display","none");
    });
    $("#ctrlbuttonregionCancellabel").click(function(){
        $("#updateRegionDialog").css("display","none");
    });
</script>

<script type="text/javascript">

    /**
    *得到账户级别的推广地域
     */
    function getAccountRegion() {
        $.ajax({
            url:"/assistantCampaign/getRegionByAcid",
            type:"post",
            dataType:"json",
            success:function(data){
                for(var i=0;i<data.length;i++){
                    $("#ctrllabelacctRegionList").attr("title",data[i]);
                    $("#ctrllabelacctRegionList").append(data[i]+"\t");
                }
            }
        });
    }

    getAccountRegion();

</script>

<%--</body>
</html>--%>

