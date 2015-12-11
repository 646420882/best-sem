/**
 * Created by XiaoWei on 2015/2/25.
 */
var subTable = null;
var subGrid = null;
$(function () {
    rDrag.init(document.getElementById("SublinkDiv"));

    $("#sublinkMenu li").each(function (i, o) {
        $(o).click(function () {
            $("div[id^='stb']").css("display", "none");
            if ($(o).attr("class") == "current") {
                $("div[id^='stb" + (i + 1) + "']").css("display", "block");
            }
        });
    });

    /*******附加创意底部****/
    var $tab_li = $('.w_list05 li');
    $('.w_list05 li').mouseover(function () {
        $(this).addClass('current').siblings().removeClass('current');
        var index = $tab_li.index(this);
        $('div.w_list06 > div').eq(index).show().siblings().hide();
    });

    subGrid = $("#subTable").jqGrid({
        datatype: 'json',
        url: "../sublink/getList",
        postData: {aid: "-1"},
        mtype: "POST",
        jsonReader: {
            root: "list",
            records: "totalCount",
            repeatitems: false
        },
        height: 300,
        shrinkToFit: true,
        colModel: [
            {label: 'sublinkInfoVos', name: "sublinkInfoVos", hidden: true},
            {label: ' 子链一名称', name: 'linkName1', sortable: false, align: 'center', width: 90},
            {label: ' 子链一URL', name: 'linkUrl1', sortable: false, align: 'center', width: 90},
            {label: ' 子链二名称', name: 'linkName2', sortable: false, align: 'center', width: 90},
            {label: ' 子链二URL', name: 'linkUrl2', sortable: false, align: 'center', width: 90},
            {label: ' 子链三名称', name: 'linkName3', sortable: false, align: 'center', width: 90},
            {label: ' 子链三URL', name: 'linkUrl3', sortable: false, align: 'center', width: 90},
            {label: ' 子链四名称', name: 'linkName4', sortable: false, align: 'center', width: 90},
            {label: ' 子链四URL', name: 'linkUrl4', sortable: false, align: 'center', width: 90},
            {label: ' 子链五名称', name: 'linkName5', sortable: false, align: 'center', width: 90},
            {label: ' 子链五URL', name: 'linkUrl5', sortable: false, align: 'center', width: 90},
            {label: ' 蹊径子链状态', name: 'status', sortable: false, align: 'center', width: 110},
            {label: ' 暂停/启用', name: 'pause', sortable: false, align: 'center', width: 90},
            {label: ' 投放设备', name: 'mType', sortable: false, align: 'center', width: 90},
            {label: ' 推广单元名称', name: 'adgroupId', sortable: false, align: 'center', width: 115},
            {label: ' 推广计划名称', name: 'campaignId', sortable: false, align: 'center', width: 115},
        ],
        rowNum: 10,// 默认每页显示记录条数
        loadui: 'disable',
        pgbuttons: false,
        autowidth: true,
        altRows: true,
        altclass: 'list2_box2',
        resizable: true,
        scroll: false,
        multiselect: true,
        gridComplete: function () {
            var pageCount = subGrid.getGridParam("records");
            if (pageCount == 0) {
                return false;
            }
            var graduateIds = jQuery("#subTable").jqGrid('getDataIDs');
            for (var i = 0, l = graduateIds.length; i < l; i++) {
                var rowId = graduateIds[i];
                var status = subGrid.jqGrid("getCell", rowId, "status");
                var pause = subGrid.jqGrid("getCell", rowId, "pause");
                var mType = subGrid.jqGrid("getCell", rowId, "mType");
                var sublinkInfoVos = subGrid.jqGrid("getCell", rowId, "sublinkInfoVos").slice(0, -1);
                $("#subTable").setCell(rowId, "status", until.getCreativeStatus(parseInt(status)));
                $("#subTable").setCell(rowId, "pause", until.convert(pause, "启用:暂停"));
                var _length = sublinkInfoVos.split("|").length;
                for (var j = 0; j < _length; j++) {
                    $("#subTable").setCell(rowId, "linkName" + (j + 1), sublinkInfoVos.split("|")[j].split(",")[0]);
                    $("#subTable").setCell(rowId, "linkUrl" + (j + 1), sublinkInfoVos.split("|")[j].split(",")[1]);
                }
                if (mType == 0) {
                    $("#subTable").setCell(rowId, "mType", "计算机");
                } else {
                    $("#subTable").setCell(rowId, "mType", "移动设备");
                }
            }
        },beforeSelectRow:function(rowid,e){
            $("div[name='subLinkReView'] input").val("");
            var tr=$("#subTable").getRowData(rowid);
            var sublinkInfoVos=tr.sublinkInfoVos.slice(0,-1);
            var mType=tr.mType;
            if(mType=="计算机"){
                $("#liLink5").show().find("input").val("");
            }else{
                $("#liLink5").hide().find("input").val("");
            }
            var _length=sublinkInfoVos.split("|").length;
            for(var i=0;i<_length;i++){
                $("div[name='subLinkReView'] input[name^='linkName']:eq("+i+")").val(sublinkInfoVos.split("|")[i].split(",")[0]);
                $("div[name='subLinkReView'] input[name^='linkUrl']:eq("+i+")").val(sublinkInfoVos.split("|")[i].split(",")[1]);
            }

            //$("div[name='subLinkReView'] input[name^='linkName']").each(function(i,o){
            //
            //});
            //for(var j=0;j<_length;j++){
            //    $("div[name='subLinkReView'] input:eq("+j+")").val(tr.sublinkInfoVos.split("|")[j].split(",")[0]);
            //}

            return false;
        }

    });

    //控制添加蹊径子链输入字符
    $("#sublinkAddInput input").keyup(function () {
        var val = $(this).val();
        var char = getChar(val);
        var max = $(this).next("span").html().split("/")[1];
        if (char > max) {
            $(this).next("span").css("color", "red");
        } else {
            $(this).next("span").css("color", "black");
        }
        $(this).next("span").html(char + "/" + max);
    });
    //控制添加蹊径子链名称输入字符总字节数
    $("#sublinkAddInput input[name^='linkName']").keyup(function () {
        var maxByte = 0;
        $("#sublinkAddInput input[name^='linkName']").each(function (i, o) {
            maxByte = parseInt(maxByte) + parseInt($(o).next("span").html().split("/")[0]);
        })
        if (maxByte > 56) {
            $("#maxByte").css("color", "red");
        } else {
            $("#maxByte").css("color", "black");
        }
        $("#maxByte").html(maxByte);
    });
    //$("#sublinkAddInput input[name^='linkName']").keyup(function () {
    //    var val = $(this).val();
    //    var char = getChar(val);
    //
    //    if (char > max) {
    //        $(this).next("span").css("color", "red");
    //    } else {
    //        $(this).next("span").css("color", "black");
    //    }
    //    $(this).next("span").html(char + "/" + max);
    //});
});
function getOnlyOnPage(){
}
function getSublinkListUnit(params) {
    var tmpValue = $("#subTable").jqGrid("getGridParam", "postData");
    $.extend(tmpValue, {
        postData: {
            cid: params.cid,
            aid: params.aid
        }
    });
    $("#subTable").jqGrid("setGridParam", tmpValue).trigger("reloadGrid");
}
function getSublinkListPlan(params) {
    var tmpValue = $("#subTable").jqGrid("getGridParam", "postData");
    $.extend(tmpValue, {
        postData: {
            cid: params,
            aid: ""
        }
    });
    $("#subTable").jqGrid("setGridParam", tmpValue).trigger("reloadGrid");
}
//添加附加创意方法
function addSublink() {
    initDialog();
    initSelectCampaign();
    showSubLinkDialog();
}
//初始化添加弹出框文本状态，以及字符提示数字
function initDialog() {
    $("#sublinkAddInput input").each(function (i, o) {
        var max = $(o).next("span").html().split("/")[1];
        $(o).next("span").html("0/" + max);
    }).val("");
    $("#maxByte").html("0");
    $("#subCampaign").empty();
    $("#subAdgroup").empty();
    $("#subMtype").val(0);
    $("#link5").show();
}

function showSubLinkDialog() {
    initDomain();
    $(".TB_overlayBG").css({
        display: "block", height: $(document).height()
    });
    $("#addSublinkDiv").css({
        left: ($("body").width() - $("#addSublinkDiv").width()) / 2 - 20 + "px",
        top: ($(window).height() - $("#addSublinkDiv").height()) / 2 + $(window).scrollTop() + "px",
        display: "block"
    });
}
//关闭添加蹊径子链弹窗
function closeSubLinkDialog() {
    $(".TB_overlayBG").css("display", "none");
    $("#addSublinkDiv ").css("display", "none");
}
//初始化添加蹊径子链对话框中的计划选择列表
function initSelectCampaign() {
    $.getJSON("/campaign/getAllCampaign", null, function (data) {
        if (data.rows.length > 0) {
            var campaigns = "", datas = data.rows;
            campaigns += "<option value='-1' selected='selected'>请选择推广计划</option>";
            for (var i = 0, l = datas.length; i < l; i++) {
                var _campaignId = "";
                if (datas[i].campaignId != null) {
                    _campaignId = datas[i].campaignId;
                } else {
                    _campaignId = datas[i].id;
                }
                campaigns += "<option value=" + _campaignId + ">" + datas[i].campaignName + "</option>";
            }
            $("#subCampaign").empty();
            $("#subCampaign").append(campaigns);
        }
    });
}
function initSelectAdgroup() {
    var campaignId = $("#subCampaign option:selected").val();
    if (campaignId != "") {
        if (campaignId.length < 24) {
            $.getJSON("/adgroup/getAdgroupByCampaignId/" + campaignId,
                {
                    campaignId: campaignId,
                    skip: 0,
                    limit: 100
                },
                function (data) {
                    var adgroups = "", datas = data.rows;
                    adgroups += "<option value='-1' selected='selected'>请选择推广单元</option>";
                    for (var i = 0, l = datas.length; i < l; i++) {
                        var _adgroupId = "";
                        if (datas[i].adgroupId != null) {
                            _adgroupId = datas[i].adgroupId;
                        } else {
                            _adgroupId = datas[i].id;
                        }
                        adgroups += "<option value=" + _adgroupId + ">" + datas[i].adgroupName + "</option>";
                    }
                    $("#subAdgroup").empty();
                    $("#subAdgroup").append(adgroups);
                });
        } else {
            $.getJSON("/adgroup/getAdgroupByCampaignObjId/" + campaignId,
                {
                    campaignId: campaignId,
                    skip: 0,
                    limit: 100
                },
                function (data) {
                    var adgroups = "", datas = data.rows;
                    adgroups += "<option value='-1' selected='selected'>请选择推广单元</option>";
                    for (var i = 0, l = datas.length; i < l; i++) {
                        adgroups += "<option value=" + datas[i].id + ">" + datas[i].adgroupName + "</option>";
                    }
                    $("#subAdgroup").empty();
                    $("#subAdgroup").append(adgroups);
                });
        }
    }

}
function mobileTypeAdd() {
    var mType = $("#subMtype option:selected").val();
    var link5 = $("#link5");
    if (mType == 0) {
        link5.show();
    } else {
        link5.hide();
    }
}

//添加附加创意确定方法
function addSubLinkOk() {
    var array = [];
    var array2=[];
    var names = "";
    var links = "";
    var domain = $(".doMainS").html();
    var math = new RegExp("(http://|https://){1}.*" + domain + "+.*");
    var subPause = $("#subPause :selected").val();
    var mType = $("#subMtype :selected").val();
    var subCamp = $("#subCampaign :selected").val();
    var subAdgroup = $("#subAdgroup :selected").val();
    if(mType==0){
        var linkName1 = $("input[name='linkNameIn1']").val();
        var linkUrl1 = $("input[name='linkURLIn1']").val();
        names="";
        links="";
        if (parseInt(getChar(linkName1)) > 16 || linkName1 == "") {
            //alert("蹊径子链一名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
            AlertPrompt.show("蹊径子链一名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
            return;
        }
        if (parseInt(getChar(linkUrl1)) > 1024 || linkUrl1 == "") {
            //alert("蹊径子链一Url不能为空，并且不能大于1024个字节");
            AlertPrompt.show("蹊径子链一Url不能为空，并且不能大于1024个字节");
            return;
        } else {
            if (!math.test(linkUrl1)) {
                //alert("蹊径子链一Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                AlertPrompt.show("蹊径子链一Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                return;
            }
        }
        names += linkName1 + "\n";
        links += linkUrl1 + "\n";
        array.push(1);
        var linkName2 = $("input[name='linkNameIn2']").val();
        var linkUrl2 = $("input[name='linkURLIn2']").val();
        if (parseInt(getChar(linkName2)) > 16 || linkName2 == "") {
            //alert("蹊径子链二名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
            AlertPrompt.show("蹊径子链二名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
            return;
        }
        if (parseInt(getChar(linkUrl2)) > 1024 || linkUrl2 == "") {
            //alert("蹊径子链二Url不能为空，并且不能大于1024个字节");
            AlertPrompt.show("蹊径子链二Url不能为空，并且不能大于1024个字节");
            return;
        } else {
            if (!math.test(linkUrl2)) {
                //alert("蹊径子链二Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                AlertPrompt.show("蹊径子链二Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                return;
            }
        }
        names += linkName2 + "\n";
        links += linkUrl2 + "\n";
        array.push(1);
        var linkName3 = $("input[name='linkNameIn3']").val();
        var linkUrl3 = $("input[name='linkURLIn3']").val();
        if (parseInt(getChar(linkName3)) > 16 || linkName3 == "") {
            //alert("蹊径子链三名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
            AlertPrompt.show("蹊径子链三名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
            return;
        }
        if (parseInt(getChar(linkUrl3)) > 1024 || linkUrl3 == "") {
            //alert("蹊径子链三Url不能为空，并且不能大于1024个字节");
            AlertPrompt.show("蹊径子链三Url不能为空，并且不能大于1024个字节");
            return;
        } else {
            if (!math.test(linkUrl3)) {
                //alert("蹊径子链三Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                AlertPrompt.show("蹊径子链三Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                return;
            }
        }
        names += linkName3 + "\n";
        links += linkUrl3 + "\n";
        array.push(1);
        var linkName4 = $("input[name='linkNameIn4']").val();
        var linkUrl4 = $("input[name='linkURLIn4']").val();
        if (linkName4 != "" && linkUrl4 != "") {
            if (parseInt(getChar(linkName4)) > 16) {
                //alert("蹊径子链四名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
                AlertPrompt.show("蹊径子链四名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
                return;
            } else {
                names += linkName4 + "\n";
            }
            if (parseInt(getChar(linkUrl4)) > 1024) {
                //alert("蹊径子链四Url不能为空，并且不能大于1024个字节");
                AlertPrompt.show("蹊径子链四Url不能为空，并且不能大于1024个字节");
                return;
            } else {
                if (!math.test(linkUrl4)) {
                    //alert("蹊径子链四Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                    AlertPrompt.show("蹊径子链四Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                    return;
                } else {
                    links += linkUrl4 + "\n";
                }
            }
        }
        var linkName5 = $("input[name='linkNameIn5']").val();
        var linkUrl5 = $("input[name='linkURLIn5']").val();
        if (linkName5 != "" && linkUrl5 != "") {
            if (parseInt(getChar(linkName5)) > 16) {
                //alert("蹊径子链五名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
                AlertPrompt.show("蹊径子链五名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
                return;
            } else {
                names += linkName5 + "\n";
            }
            if (parseInt(getChar(linkUrl5)) > 1024) {
                //alert("蹊径子链五Url不能为空，并且不能大于1024个字节");
                AlertPrompt.show("蹊径子链五Url不能为空，并且不能大于1024个字节");
                return;
            } else {
                if (!math.test(linkUrl5)) {
                    //alert("蹊径子链五Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                    AlertPrompt.show("蹊径子链五Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                    return;
                } else {
                    links += linkUrl5 + "\n";
                }
            }
        }
        if (subCamp == "-1") {
            //alert("请选择计划");
            AlertPrompt.show("请选择计划");
            return;
        }
        if (subAdgroup == "-1") {
            //alert("请选择单元");
            AlertPrompt.show("请选择单元");
            return;
        }
        if (array.length >= 3) {
            var maxByte = getChar(linkName1 + linkName2 + linkName3 + linkName4 + linkName5);
            if (maxByte > 56) {
                //alert("蹊径子链的名称总字节数不能超过56个字节，一个汉字占两个字节");
                AlertPrompt.show("蹊径子链的名称总字节数不能超过56个字节，一个汉字占两个字节");
            } else {
                if (confirm("你确定要添加该蹊径子链吗？") == true) {
                    names = names.slice(0, -1);
                    links = links.slice(0, -1);
                    $.post("../sublink/customSave", {
                        names: names,
                        links: links,
                        subPause: subPause,
                        mType: mType,
                        adgroupId: subAdgroup
                    }, function (res) {
                        if (res.msg == "1") {
                            //alert("添加成功");
                            AlertPrompt.show("添加成功");
                            closeSubLinkDialog();
                            $("#subTable").trigger("reloadGrid");
                        } else {
                            //alert(res.msg);
                            AlertPrompt.show(res.msg);
                        }
                    });
                }
            }
        } else {
            //alert("计算机蹊径子链必须大于3条!")
            AlertPrompt.show("计算机蹊径子链必须大于3条!");
        }
    }else{
        var linkName1 = $("input[name='linkName1']").val();
        var linkUrl1 = $("input[name='linkURL1']").val();
        names="";
        links="";
        if (parseInt(getChar(linkName1)) > 16 || linkName1 == "") {
            //alert("蹊径子链一名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
            AlertPrompt.show("蹊径子链一名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
            return;
        }
        if (parseInt(getChar(linkUrl1)) > 1024 || linkUrl1 == "") {
            //alert("蹊径子链一Url不能为空，并且不能大于1024个字节");
            AlertPrompt.show("蹊径子链一Url不能为空，并且不能大于1024个字节");
            return;
        } else {
            if (!math.test(linkUrl1)) {
                //alert("蹊径子链一Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                AlertPrompt.show("蹊径子链一Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                return;
            }
        }
        names += linkName1 + "\n";
        links += linkUrl1 + "\n";
        array2.push(1);
        var linkName2 = $("input[name='linkName2']").val();
        var linkUrl2 = $("input[name='linkURL2']").val();
        if (linkName2 != "" && linkUrl2 != "") {
            if (parseInt(getChar(linkName2)) > 16) {
                //alert("蹊径子链二名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
                AlertPrompt.show("蹊径子链二名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
                return;
            } else {
                names += linkName2 + "\n";
            }
            if (parseInt(getChar(linkUrl2)) > 1024) {
                //alert("蹊径子链二Url不能为空，并且不能大于1024个字节");
                AlertPrompt.show("蹊径子链二Url不能为空，并且不能大于1024个字节");
                return;
            } else {
                if (!math.test(linkUrl2)) {
                    //alert("蹊径子链二Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                    AlertPrompt.show("蹊径子链二Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                    return;
                } else {
                    links += linkUrl2+ "\n";
                }
            }
        }
        var linkName3 = $("input[name='linkName3']").val();
        var linkUrl3 = $("input[name='linkURL3']").val();
        if (linkName3 != "" && linkUrl3 != "") {
            if (parseInt(getChar(linkName3)) > 16) {
                //alert("蹊径子链三名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
                AlertPrompt.show("蹊径子链三名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
                return;
            } else {
                names += linkName3 + "\n";
            }
            if (parseInt(getChar(linkUrl3)) > 1024) {
                //alert("蹊径子链三Url不能为空，并且不能大于1024个字节");
                AlertPrompt.show("蹊径子链三Url不能为空，并且不能大于1024个字节");
                return;
            } else {
                if (!math.test(linkUrl3)) {
                    //alert("蹊径子链三Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                    AlertPrompt.show("蹊径子链三Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                    return;
                } else {
                    links += linkUrl3+ "\n";
                }
            }
        }
        var linkName4 = $("input[name='linkName4']").val();
        var linkUrl4 = $("input[name='linkURL4']").val();
        if (linkName4 != "" && linkUrl4 != "") {
            if (parseInt(getChar(linkName4)) > 16) {
                //alert("蹊径子链四名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
                AlertPrompt.show("蹊径子链四名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
                return;
            } else {
                names += linkName4 + "\n";
            }
            if (parseInt(getChar(linkUrl4)) > 1024) {
                //alert("蹊径子链四Url不能为空，并且不能大于1024个字节");
                AlertPrompt.show("蹊径子链四Url不能为空，并且不能大于1024个字节");
                return;
            } else {
                if (!math.test(linkUrl4)) {
                    //alert("蹊径子链四Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                    AlertPrompt.show("蹊径子链四Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                    return;
                } else {
                    links += linkUrl4 + "\n";
                }
            }
        }
        var linkName5 = $("input[name='linkName5']").val();
        var linkUrl5 = $("input[name='linkURL5']").val();
        if (linkName5 != "" && linkUrl5 != "") {
            if (parseInt(getChar(linkName5)) > 16) {
                //alert("蹊径子链五名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
                AlertPrompt.show("蹊径子链五名称不能为空，并且不能大于16个字节，一个汉字占两个字节!");
                return;
            } else {
                names += linkName5 + "\n";
            }
            if (parseInt(getChar(linkUrl5)) > 1024) {
                //alert("蹊径子链五Url不能为空，并且不能大于1024个字节");
                AlertPrompt.show("蹊径子链五Url不能为空，并且不能大于1024个字节");
                return;
            } else {
                if (!math.test(linkUrl5)) {
                    //alert("蹊径子链五Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                    AlertPrompt.show("蹊径子链五Url必须以http://或者https://开头，并且要包含\"" + domain + "\"的域名");
                    return;
                } else {
                    links += linkUrl5 + "\n";
                }
            }
        }
        if(array2.length==1){
            if (parseInt(getChar(linkName1)) > 16||parseInt(getChar(linkName1))<8) {
                //alert("如果只有一条数据，移动蹊径子链最少长度不能低于8个字符!");
                AlertPrompt.show("如果只有一条数据，移动蹊径子链最少长度不能低于8个字符!");
                return;
            }
        }
        if (subCamp == "-1") {
            //alert("请选择计划");
            AlertPrompt.show("请选择计划");
            return;
        }
        if (subAdgroup == "-1") {
            //alert("请选择单元");
            AlertPrompt.show("请选择单元");
            return;
        }

        if (array2.length >=1) {
            var maxByte = getChar(linkName1 + linkName2 + linkName3 + linkName4 + linkName5);
            if (maxByte > 32) {
                //alert("移动蹊径子链的名称总字节数不能超过32个字节，一个汉字占两个字节");
                AlertPrompt.show("移动蹊径子链的名称总字节数不能超过32个字节，一个汉字占两个字节");
            } else {
                if (confirm("你确定要添加该移动蹊径子链吗？") == true) {
                    names = names.slice(0, -1);
                    links = links.slice(0, -1);
                    $.post("../sublink/customSave", {
                        names: names,
                        links: links,
                        subPause: subPause,
                        mType: mType,
                        adgroupId: subAdgroup
                    }, function (res) {
                        if (res.msg == "1") {
                            //alert("添加成功");
                            AlertPrompt.show("添加成功");
                            closeSubLinkDialog();
                            $("#subTable").trigger("reloadGrid");
                        } else {
                            //alert(res.msg);
                            AlertPrompt.show(res.msg);
                        }
                    });
                }
            }
        } else {
            //alert("移动蹊径子链必须大于1条!")
            AlertPrompt.show("移动蹊径子链必须大于1条!");
        }
    }

    //var ai=[];
    //$("#sublinkAddInput input[name^='linkName']").each(function (i, o) {
    //    $(ai).each(function(j,k){
    //        if($(o).val()==k){
    //            alert("子链"+(i+1)+"与子链"+(j+1)+"重复了");
    //        }else{
    //            ai.push($(o).val());
    //        }
    //    });
    //});


    //var _linkNames = $("input[name^='linkName']");
    //var linkNames = "";
    //for (var i = 0; i < _linkNames.length; i++) {
    //    if (_linkNames[i].value != "") {
    //        linkNames += _linkNames[i].value + "\n";
    //    }
    //}
    //linkNames = linkNames.slice(0, -1);
    //var _linkUrls = $("input[name^='linkURL']");
    //var linkUrls = "";
    //for (var i = 0; i < _linkUrls.length; i++) {
    //    if (_linkUrls[i].value != "") {
    //        linkUrls += _linkUrls[i].value + "\n";
    //    }
    //}
    //linkUrls = linkUrls.slice(0, -1);
    ////alert(linkNames + "------" + linkUrls);
    //alert(ln+"----"+lr);
    //var ln=linkNames.split("\n").length;
    //var lr=linkUrls.split("\n").length;
    //alert(ln+":::::"+lr);

}






