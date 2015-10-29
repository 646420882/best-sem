<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2014/8/31
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" type="image/ico"
          href="http://tuiguang-s1.bdstatic.com/nirvana/asset/resource/img/1d95fd9f0985feb4.ico">
    <script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/style.css">
<head>
<body>

<div id="ctrldialogplanRegionDialog" class="box"  control="planRegionDialog" style="display: none; overflow: visible">
    <h2 id="SetAera" >
        <span class="fl">设置推广地域</span>
        <a href="javascript:void(0)" onclick="closeAlert();" class="close">×</a></h2>
<div class="mainlist aeralist  " id="ctrldialogplanRegionDialogbody" style="overflow: visible">
<div class="manage_dialog">
    <input type="hidden" value="${cid}" id="campaignId">

<div class="manage_region" id="planRegionSwitch"><span id="useAcctRegion">使用账户推广地域</span> &nbsp;|&nbsp; <span
        class="current_region" id="usePlanRegion">使用计划推广地域</span></div>
<div id="acctRegionList" style="display: none;"> 账户推广地域：<span ui="" id="ctrllabelacctRegionList"
                                                              control="acctRegionList" logswitch="true" class="ui_label"
                                                              title=""></span><br/><br/>

    <div class="ui_dialog_foot">
        <div class="ui_button" logswitch="true" control="regionOk" id="useAcctregionOk" ui=""><a
                href="javascript:void(0)" id="Oklabel" class="ui_button_label">确定</a></div>
        <%--<div class="ui_button" logswitch="true" control="regionCancel" id="useAcctregionCancel" ui=""><a href="javascript:void(0)" id="Cancellabel" class="ui_button_label">取消</a></div>--%>
        <span id="useRegionErrorTooltip"></span>
    </div>
</div>
<div id="DenyAreaNotice" class="common_notice hide">
    <p>您的产品或业务涉及地域性问题，仅能以目前系统设置的地域进行推广；如有问题，请联系您的维护顾问或致电百度推广热线400-890-0088</p>
</div>


<div id="planRegionset" style="display: block;">
<div class="manage_region">
    <input type="radio" title="全部地域" checked="checked" name="region" value="0" id="ctrlradioboxallRegion"
           control="allRegion" logswitch="false" class="ui_radiobox" refer="">
    <label class="ui_radiobox_label" for="ctrlradioboxallRegion">全部地域</label>
    <input type="radio" title="部分地域" name="region" value="1" id="ctrlradioboxpartRegion" control="partRegion"
           logswitch="false" class="ui_radiobox" refer="">
    <label class="ui_radiobox_label" for="ctrlradioboxpartRegion">部分地域</label>
</div>
<div style="display: none;" ui="" id="ctrlregionregionBody" control="regionBody" class="ui_region">
<div id="regionList" class="region-secondarea-select">
    <%--地域设置--%>
    <jsp:include page="aeraselect.jsp" />
</div>
</div>
<br/><br/>

<div class="ui_dialog_foot">
    <div class="ui_button" logswitch="true" control="regionOk" id="ctrlbuttonregionOk" ui=""><a
            href="javascript:void(0)" id="ctrlbuttonregionOklabel" class="ui_button_label">确定</a></div>
    <%--<div class="ui_button" logswitch="true" control="regionCancel" id="ctrlbuttonregionCancel" ui=""><a href="javascript:void(0)" id="ctrlbuttonregionCancellabel" class="ui_button_label">取消</a></div>--%>
    <span id="regionErrorTooltip"></span>
</div>


</div>
</div>
</div>
</div>

<script type="text/javascript">
    $('.ui_radiobox').click(function () {
        if (this.id == 'ctrlradioboxpartRegion') {
            $('#ctrlregionregionBody').show();
        } else {
            $('#ctrlregionregionBody').hide();
        }
    });
    $('.area-hover-event').mouseover(function () {
        $(this).addClass('ie7-index');
        $(this).children('.first-area-container').addClass('first-area-container-hover');
        $(this).children('.second-area-container').removeClass('hide');
        changeNumber(this);
    }).mouseout(function () {
        $(this).removeClass('ie7-index');
        $(this).children('.first-area-container').removeClass('first-area-container-hover');
        $(this).children('.second-area-container').addClass('hide');
    });
    $('.second-area-container').find(':checkbox').change(function () {
        var parent = $(this).parents('.area-hover-event');
        changeNumber(parent);
    });
    //checked-num-event
    function changeNumber(obj) {
        var count = $(obj).children('.second-area-container').find(':checkbox').length;
        var cknum = $(obj).children('.second-area-container').find(':checkbox:checked').length;
        if (cknum == count) {
            $(obj).children('.first-area-container').find(':checkbox').prop('checked', true);
            $(obj).children('.first-area-container').find(':checkbox').attr('style', 'visibility:visible;');
            $(obj).children('.first-area-container').find('.half-checked-icon').addClass('hide').removeClass('half-checked-icon-hover');
            $(obj).find('.checked-num-event').removeClass('checked-num').html('')
        } else if (cknum == 0) {
            $(obj).children('.first-area-container').find(':checkbox').attr('style', 'visibility:visible;');
            $(obj).children('.first-area-container').find('.half-checked-icon').addClass('hide').removeClass('half-checked-icon-hover');
            $(obj).children('.first-area-container').find(':checkbox').prop('checked', false);
            $(obj).find('.checked-num-event').removeClass('checked-num').html('')
        } else {
            $(obj).find('.checked-num-event').addClass('checked-num').html(cknum + "/" + count);
            $(obj).children('.first-area-container').find(':checkbox').attr('style', 'visibility:hidden;');
            $(obj).children('.first-area-container').find(':checkbox').prop('checked', true);
            $(obj).children('.first-area-container').find('.half-checked-icon').removeClass('hide').addClass('half-checked-icon-hover');
        }
    }
    $(function () {
        var ck = $('#ctrlradioboxpartRegion').prop('checked');
        if (ck) {
            $('#ctrlregionregionBody').show();
        }
    });
    /*******input_checked*****/

    $(function () {
        $("dt input[type=checkbox]").click(function () {
            if ($(this).is(":checked") == true) {
                $(this).parent("dt").next("dd").find("input[type=checkbox]").prop("checked", "true");
            } else {
                $(this).parent("dt").next("dd").find("input[type=checkbox]").removeAttr("checked");
            }
        });
    });
    $(function () {
        $(".first-area-container input[type=checkbox]").click(function () {
            if ($(this).is(":checked") == true) {
                $(this).parent(".first-area-container").next(".second-area-container").find("input[type=checkbox]").prop("checked", "true");
            } else {
                $(this).parent(".first-area-container").next(".second-area-container").find("input[type=checkbox]").removeAttr("checked");
            }
        });
    });
    $(function () {
        $(".second-area-leaf input[type=checkbox]").click(function () {
            if ($(this).is(":checked") == true) {
                $(this).parent("dt").find("input[type=checkbox]").prop('checked', true);
            } else {
                $(this).parent("dt").find("input[type=checkbox]").removeAttr("checked");

            }
        });
    });
    /*******切换*****/
    $(document).ready(function () {
        $("#useAcctRegion").click(function () {
            $("#acctRegionList").show();
            $("#useAcctRegion").addClass("current_region");
            $("#planRegionset").hide();
            $("#usePlanRegion").removeClass("current_region");

        });
        $("#usePlanRegion").click(function () {
            $("#planRegionset").show();
            $("#usePlanRegion").addClass("current_region");
            $("#acctRegionList").hide();
            $("#useAcctRegion").removeClass("current_region");

        });
    })
    /*******关闭窗口*****/
    $("#ctrldialogplanRegionDialogclose").click(function () {
        $("#ctrldialogplanRegionDialog").css("display", "none");
    });
    $("#ctrlbuttonregionCancellabel").click(function () {
        $("#ctrldialogplanRegionDialog").css("display", "none");
    });
</script>

<script type="text/javascript">

    function getCampaignData() {
        var cid = $("#campaignId").val();
        $.ajax({
            url: "/assistantCampaign/getRegion",
            type: "post",
            data: {"cid": cid},
            dataType: "json",
            success: function (data) {
                //得到账户级别的推广地域
                getAccountRegion();
                if (data.campObj.regionTarget == null || data.campObj.regionTarget.length == 0) {
                    $("#useAcctRegion").addClass("current_region");
                    $("#usePlanRegion").removeClass("current_region");
                    $("#ctrlregionregionBody").hide(0);
                    $("#planRegionset").hide(0);
                    $("#acctRegionList").show(0);
                } else {
                    $("#ctrlradioboxallRegion")[0].checked = false;
                    $("#ctrlradioboxpartRegion")[0].checked = true;
                    $("#ctrlregionregionBody").show(0);

                    if (data.regions[0].stateName == "全部区域") {
                        $("#ctrlradioboxallRegion")[0].checked = true;
                        $("#ctrlregionregionBody").hide(0);
                    } else {
                        setRegionTargetToChecked(data.regions);
                    }
                }
            }
        });
    }

    //进入该页面的时候得到该计划
    getCampaignData();


    //将该计划现已有的推广地域展示出来
    function setRegionTargetToChecked(regionsName) {
        var label = $("#regionList").find("div[class=leaf]").find("label");
        for (var i = 0; i < regionsName.length; i++) {
            label.each(function () {
                var regName;
                if (regionsName[i].regionName == null || regionsName[i].regionName == "") {
                    regName = regionsName[i].provinceName;
                } else {
                    regName = regionsName[i].regionName;
                }

                if (regName == $(this).html()) {
                    if ($(this).parent().parent().parent().attr("class") != "second-area-container hide") {
                        $(this).parent().find("input[type=checkbox]")[0].checked = true;
                    } else {
                        var ico = $(this).parentsUntil(".area-hover-event").parent().find(".first-area-container>.half-checked-icon");
                        ico.removeClass("hide");
                        ico.addClass("half-checked-icon-hover");
                        $(this).parent().find("input[type=checkbox]")[0].checked = true;
                    }
                }
            });
        }

    }


    /**
     *得到账户级别的推广地域
     */
    function getAccountRegion() {
        $.ajax({
            url: "/assistantCampaign/getRegionByAcid",
            type: "post",
            dataType: "json",
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    if (data[i].stateName == "全部区域") {
                        $("#ctrllabelacctRegionList").attr("title", data[i].stateName);
                        $("#ctrllabelacctRegionList").append(data[i].stateName + "\t");
                    } else {
                        $("#ctrllabelacctRegionList").attr("title", data[i].provinceName);
                        $("#ctrllabelacctRegionList").append(data[i].provinceName + "\t");
                    }
                }
            }
        });
    }


    /**
     *使用账户推广地域的确定按钮的事件
     */
    $("#Oklabel").click(function () {
        var cid = $("#campaignId").val();
        $.ajax({
            url: "/assistantCampaign/useAccoutRegion",
            type: "post",
            data: {"cid": cid},
            dataType: "json",
            success: function (data) {
                alert("使用账户推广地域成功！");
            }
        });
    });


    /**
     *推广计划的推广地域的确定按钮的事件
     */
    $("#ctrlbuttonregionOklabel").click(function () {
        var region = $("input[name=region]");
        var value = null;
        region.each(function () {
            if ($(this)[0].checked == true) {
                value = $(this).val();
            }
        });

        //0是全部地域,1是部分地域
        var regions = "";
        if (value == 0) {
            regions += "全部区域" + ",";
        } else if (value == 1) {
            var leaf = $("#regionList").find("div[class=leaf]");
            leaf.each(function () {
                var checkbox = $(this).find("input[type=checkbox]");
                if (checkbox[0].checked == true) {
                    var parentsNode = $(this).parentsUntil(".area-hover-event").parent();
                    if (parentsNode.html() == undefined) {
                        regions += $(this).find("label").html() + ",";
                    } else {
                        if (parentsNode.next().find("input[type=checkbox]")[0].checked == true) {
                            regions += parentsNode.next().find("input[type=checkbox]").next().html() + ",";
                        } else {
                            regions += $(this).find("label").html() + ",";
                        }
                    }
                }
            });
        }

        var cid = $("#campaignId").val();
        $.ajax({
            url: "/assistantCampaign/usePlanRegion",
            type: "post",
            data: {"regions": regions, "cid": cid},
            dataType: "json",
            success: function (data) {
                alert("使用计划推广地域成功！");
            }
        });


    });


</script>

</body>
</html>

<%--
if(/ctrlregionregionBody/.test(checkbox.attr("id"))){
regions+=$(this).find("label").html()+"-prov"+",";
}else{
if($(this).find("label").html()=="日本"||$(this).find("label").html()=="其他国家（除日本外）"){
regions+=$(this).find("label").html()+"-contr"+",";
}else{
regions+=$(this).find("label").html()+"-region"+",";
}
}--%>
