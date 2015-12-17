<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2014/8/31
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="shortcut icon" type="image/ico"
      href="http://tuiguang-s1.bdstatic.com/nirvana/asset/resource/img/1d95fd9f0985feb4.ico">
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/style.css">

<div id="ctrldialogplanRegionDialog" class="ui_dialog" control="planRegionDialog"
     style="width: 548px; left: 120px;z-index: 401; top: 0px;display: none;">
<input type="hidden" value="${cid}" id="campaignId">

<div class="ui_dialog_body" id="ctrldialogplanRegionDialogbody">
<div class="manage_dialog">

<div id="planRegionset" style="display: block;">
<div style="display: block;" ui="" id="ctrlregionregionBody" control="regionBody" class="ui_region">
<div id="regionList" class="region-secondarea-select">
    <jsp:include page="aeraselect.jsp" />
</div>
</div>
<div class="ui_dialog_foot">
    <div class="ui_button" logswitch="true" control="regionOk" id="regionOk" ui=""><a href="javascript:void(0)"
                                                                                      id="ctrlbuttonregionOklabel"
                                                                                      class="ui_button_label">确定</a>
    </div>
    <div class="ui_button" logswitch="true" control="regionCancel" id="ctrlbuttonregionCancel" ui=""><a
            href="javascript:void(0)" id="ctrlbuttonregionCancellabel" class="ui_button_label">取消</a></div>
    <span id="regionErrorTooltip"></span>
</div>


</div>
</div>
</div>
</div>

<script type="text/javascript">
    $('.ui_radiobox').click(function () {
        if (this.menuId == 'ctrlradioboxpartRegion') {
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


    //将该计划现已有的推广地域展示出来
    function setRegionTargetToChecked(regionsName) {
        var label = $("#regionList").find("div[class=leaf]").find("label");
        for (var i = 0; i < regionsName.length; i++) {
            label.each(function () {
                if (regionsName[i] == $(this).html()) {
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
     *搜索词报告中的确定按钮事件
     */
    $("#regionOk").click(function () {
        $("#ctrldialogplanRegionDialog").hide(0);
    });


</script>


