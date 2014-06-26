<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2014-6-11
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>百度推广</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="${pageContext.request.contextPath}/public/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/css/jquery-ui-1.10.4.min.css" rel="stylesheet">
    <style type="text/css">
        body {
            padding-top: 60px;
            padding-bottom: 40px;
        }

        .sidebar-nav {
            padding: 9px 0;
        }

        @media (max-width: 980px) {
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }

        #table1 {
            width: 100%;
            border-collapse: collapse;
            margin: 0 auto;
            table-layout: fixed;
        }

        #table1 td {
            height: 30px;
            vertical-align: middle;
            text-align: left;
            font-size: 11pt;
        }

        #attentionTab {
            width: 100%;
            border-collapse: collapse;
            margin: 0 auto;
            table-layout: fixed;
        }

        #attentionTab thead td {
            height: 32px;
            border: 1px solid #f0f0e0;
            vertical-align: middle;
            text-align: center;
            background-color: #f1f1f1
        }

        #attentionTab tbody td {
            height: 30px;
            border: 1px solid #f0f0e0;
            vertical-align: middle;
            text-align: center;
            background-color: #fefede
        }

        #keyWordTable {
            width: 100%;
            border-collapse: collapse;
            margin: 0 auto;
            table-layout: fixed;
        }

        #keyWordTable thead td {
            height: 32px;
            border: 1px solid #f0f0e0;
            vertical-align: middle;
            text-align: center;
            background-color: #ececfe
        }

        #keyWordTable tbody td {
            height: 30px;
            border: 1px solid #f0f0e0;
            vertical-align: middle;
            text-align: center;
            background-color: #fcfcfc
        }
    </style>
</head>
<body>
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid" style="margin-top: 10px">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="brand" href="#">Baidu推广</a>

            <div class="nav-collapse collapse">
                <p class="navbar-text pull-right">
                    baidu-bjperfrct2131113 <a href="#" class="navbar-link">退出</a>
                </p>
                <ul class="nav">
                    <li><a href="${pageContext.request.contextPath}/home">首页</a></li>
                    <li><a href="#accountCenter">账户中心</a></li>
                    <li><a href="#contact">财务</a></li>
                    <li class="active"><a href="${pageContext.request.contextPath}/main/convenienceManage">便捷管理</a></li>
                    <li><a href="${pageContext.request.contextPath}/main/spreadManage">推广管理</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="container-fluid">
    <div class="row-fluid" style="margin-top: 10px">
        <div class="span3" style="height: 560px; border: 1px solid #f1e1ff">
            <div style="height: 200px; border: solid 1px #f1f1f1">
                <h5 style="background-color: #F0F0F0; text-align: left; height: 40px; margin-top: 0.5px; margin-left: 0.5px; margin-bottom: -2px">
                    baidu-bjtthunbohui2134115
                </h5>
                <table id="table1">
                    <tr>
                        <td>推广余额： <span style="color: #ffa007; font-size: 20px">38678.13 元</span></td>
                    </tr>
                    <tr>
                        <td>余额预计可消费： <span style="font-weight: bold">9天</span><span style="margin-left: 80px"><a
                                class="btn btn-small" style="color: #0080ff" href="#">充值</a></span></td>
                    </tr>
                    <tr>
                        <td>预算： <span>不限定&nbsp;&nbsp;<a href="#">修改</a></span></td>
                    </tr>
                    <tr>
                        <td>推广地域： <span>北京&nbsp;&nbsp;<a href="#">修改</a></span></td>
                    </tr>
                    <tr>
                        <td>快捷设置： <span><a href="#">账户</a>&nbsp;&nbsp;<a href="#">计划</a>&nbsp;&nbsp;<a href="#">单元</a>&nbsp;&nbsp;<a
                                href="#">关键词</a></span></td>
                    </tr>
                </table>
            </div>
            <div style="margin-top: 8px; height: 170px; border: solid 1px #f1f1f1">
                <div style="background-color: #f0f0f0; font-size: 16px; font-weight: bold; height: 40px; margin-top: 0.5px; margin-left: 0.5px; margin-bottom: -2px">
                    账户数据
                </div>
            </div>
            <div style="margin-top: 8px; height: 170px; border: solid 1px #f1f1f1">
                <div style="background-color: #f0f0f0; font-size: 16px; font-weight: bold; height: 40px; margin-top: 0.5px; margin-left: 0.5px; margin-bottom: -2px">
                    账户诊断
                </div>
            </div>
        </div>
        <div class="span9">
            <div class="row-fluid">

                <div class="tabbable">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#tab1" data-toggle="tab">看排名</a></li>
                        <li><a href="#tab2" data-toggle="tab">降成本</a></li>
                        <li><a href="#tab3" data-toggle="tab">移动优化</a></li>
                        <li><a href="#tab4" data-toggle="tab">拓客源</a></li>
                        <li><a href="#tab5" data-toggle="tab">提升质量度</a></li>
                        <li><a href="#tab6" data-toggle="tab">添好词</a></li>
                        <li><a href="#tab7" data-toggle="tab">超同行</a></li>
                    </ul>
                </div>
                <div class="tab-content">
                    <div class="tab-pane active" id="tab1">
                        <div style="margin-bottom: 10px; font-size: 16px">分析筛选： <span>全部</span></div>
                        <div style="height: 36px; border: solid 1px #ececfe">
                            <a class="btn" onclick=openModifyPriceModal();>修改出价</a>&nbsp;&nbsp;&nbsp;
                            <div id="modifyPrice" class="modal hide fade" role="dialog"
                                 style="margin-top: 8%; margin-left: -15%;width: 400px; height: 200px">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">&times;</button>
                                    <label style="font-size: 15px">关键词出价</label>
                                </div>
                                <div class="modal-body" style="height: 50%">
                                    <input type="radio" name="radio1" value="0"/>修改出价:<br/>
                                    <input type="hidden" id="keyWord_id" name="keyWord_id" value=""/>
                                    <input type="text" id="keywordPrice" name="keywordPrice"/><br/>
                                    <input type="radio" name="radio1" value="1"/>使用单元出价
                                </div>
                                <div class="modal-footer">
                                    <a href="#" class="btn btn-primary" name="saveKeywordPrice">确定</a>
                                    <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">取消</a>
                                </div>
                            </div>
                            <a class="btn" onclick=cancelAttention();>取消关注</a>&nbsp;&nbsp;&nbsp;
                            <a data-toggle="modal" class="btn" onclick=addAttention();>添加新关注</a>

                            <div id="addAttention" class="modal hide fade" role="dialog"
                                 style="margin-top: -30px; margin-left: -450px;width: 900px; height: 600px">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">&times;</button>
                                    <legend>添加新关注</legend>
                                </div>
                                <div class="modal-body" style="height: 100%">
                                    <label style="font-size: 16px">搜索添加</label>
                                    <input id="inputKeyword" type="text" placeholder="请输入关键词">
                                    <a class="btn" style="margin-top: -10px" onclick=addTable();>搜索</a>

                                    <div style="height: 36px; background-color: #f1f1fe">
                                        <a class="btn" style="margin-top: 3px" onclick=batchAddWord();>批量添加</a>
                                    </div>

                                    <table id="attentionTab">
                                        <thead>
                                        <tr>
                                            <td style="width:3%"><input name="keyword_add" type="checkbox"
                                                                        onclick=chooseAllCheckbox();>
                                            </td>
                                            <td style="width:20%">关键词</td>
                                            <td style="width:15%">所属计划</td>
                                            <td style="width:15%">所属单元</td>
                                            <td style="width:10%">最近7天消费</td>
                                            <td style="width:10%">最近7天操作量</td>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <table id="keyWordTable">
                            <thead>
                            <tr>
                                <td style="width:3%"><input name="keyword_select" type="checkbox"
                                                            onclick=selectAllAttention();>
                                </td>
                                <td style="width:25%">关键词</td>
                                <td style="width:15%">左侧排名</td>
                                <td style="width:10%">当前出价</td>
                                <td style="width:10%">昨日点击</td>
                                <td style="width:10%">昨日消费</td>
                                <td style="width:27%">质量度</td>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                    <div class="tab-pane" id="tab2">
                        <p>Howdy, I'm in Section 2.</p>
                    </div>
                </div>

            </div>

        </div>

    </div>

</div>

<div class="navbar navbar-fixed-bottom">
    <div class="navbar-inner">
        <div class="container-fluid">
            <div class="row-fluid" style="background-color: #f0f0f0">
                <div class="span1" style="margin-top: 10px; text-align: center; font-size: 18px">工具栏

                </div>
                <div class="span2"
                     style="margin-top: 0px; margin-right:-32px; margin-left: -32px; text-align: center">
                    <a class="btn btn-primary">数据报告</a>

                </div>
                <div class="span2"
                     style="margin-top: 0px; margin-right:-32px; margin-left: -32px; text-align: center">
                    <a class="btn btn-primary">推广实况</a>

                </div>
                <div class="span2"
                     style="margin-top: 0px; margin-right:-32px; margin-left: -32px; text-align: center">
                    <a class="btn btn-primary">搜索词报告</a>

                </div>
                <div class="span2"
                     style="margin-top: 0px; margin-right:-32px; margin-left: -32px; text-align: center">
                    <a class="btn btn-primary">关键字工具</a>

                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-ui-1.10.4.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.livequery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/json2.js"></script>
<script type="text/javascript">
var basePath = "<%=basePath%>";
$(function () {
    //绑定键盘回车事件
    $('#inputKeyword').bind('keypress', function (event) {
        if (event.keyCode == "13") {
            $("#attentionTab tbody tr").empty();
            addTable();
        }
    });

    //动态绑定radio事件
    $("input[name='radio1']").livequery('click', function () {
        if (this.checked && this.value == 1)
        //使用单元出价时, 修改价格输入框处于屏蔽状态
            $("#keywordPrice").attr("readonly", "readonly");
        else
            $("#keywordPrice").removeAttr("readonly");
    });

    loadingKeywordReport();

    //修改关键词价格绑定click事件
    $("a[name='saveKeywordPrice']").click(function () {
        //获取关注的ID
        var keywordId = $("#keyWord_id").val();
        var keywordPrice = "";
        //遍历radio以获取价格
        $("input[name=radio1]:radio").each(function () {
            if (this.checked) {
                if (this.value == 0) {
                    //手动修改关键词价格
                    keywordPrice = $("#keywordPrice").val();
                    $.post(basePath + "convenienceManage/modifyKeywordPrice",
                            {
                                keywordId: keywordId,
                                keywordPrice: keywordPrice
                            },
                            function (data) {
                                if (data.flag) {
                                    $("#modifyPrice").modal("hide");
                                    loadingKeywordReport();
                                }
                            }, "json");
                } else if (this.value == 1) {
                    //使用单元出价
                    $.post(basePath + "convenienceManage/revertToUnitPrice",
                            {keywordId: keywordId},
                            function (data) {
                                if (data.flag) {
                                    $("#modifyPrice").modal("hide");
                                    loadingKeywordReport();
                                }
                            }, "json");
                }
            }
        });
    });
});

//加载关键词监控报告
var loadingKeywordReport = function () {
    $("#keyWordTable tbody tr").empty();
    $.ajax({
        url: basePath + "convenienceManage/keywordReport",
        dataType: "json",
        async: true,
        success: function (data, textStatus, jqXHR) {
            if (data.rows.length > 0) {
                var temp_id = null;
                $.each(data.rows, function (i, item) {
                    var newTr = "<tr id='" + item.keywordId + "' align='center'>" +
                            "<td style='text-align:center'><input type='checkbox' name='keyword_select1'/><input type='hidden' value='" + item.keywordId + "'/></td>" +
                            "</td><td>" + item.keyword +
                            "</td><td>" + item.rank +
                            "</td><td>" + item.currentPrice +
                            "</td><td>" + item.click +
                            "</td><td>" + item.cost +
                            "</td><td>" + item.qualityGrade +
                            "</td></tr>";
                    if (i == 0) {
                        $("#keyWordTable tbody").append(newTr);
                        temp_id = item.keywordId;
                    } else {
                        $("#" + temp_id).after(newTr);
                        temp_id = item.keywordId;
                    }
                });
            }
        }
    });
};

var addAttention = function () {
    var inputKeyword = $('#inputKeyword').val(null);
    //加载时清除tbody下面所有的tr
    $("#attentionTab tbody tr").empty();
    $("#addAttention").modal("show");
};

var addTable = function () {
    $("#attentionTab tbody tr").empty();
    var inputKeyword = $('#inputKeyword').val();
    $.getJSON(basePath + "convenienceManage/searchWord",
            {searchWord: window.encodeURIComponent(inputKeyword)},
            function (data) {
                if (data.rows.length > 0) {
                    var temp_id = null;
                    $.each(data.rows, function (i, item) {
                        var newTr = "<tr id='" + item.keywordId + "' align='center'>" +
                                "<td style='text-align:center'><input type='checkbox' name='keyword_add'/><input type='hidden' value='" + item.campaignId + "'/><input type='hidden' value='" + item.adgroupId + "'/></td>" +
                                "</td><td>" + item.keyword +
                                "</td><td>" + item.campaignName +
                                "</td><td>" + item.adgroupName +
                                "</td><td>" + '' +
                                "</td><td>" + '' +
                                "</td></tr>";
                        if (i == 0) {
                            $("#attentionTab tbody").append(newTr);
                            temp_id = item.keywordId;
                        } else {
                            $("#" + temp_id).after(newTr);
                            temp_id = item.keywordId;
                        }
                    });
                }
            });
};

//批量添加新关注
var batchAddWord = function () {
    //生成JSON对象数组
    var dataJson = [];
    var allKeywords = $("input[name=keyword_add]:checkbox");
    var trs = $("#attentionTab").find("tr");
    for (var i = 1, l = allKeywords.length; i < l; i++) {
        var entityJson = {};
        if (allKeywords[i].checked) {
            entityJson["keywordId"] = trs.eq(i).attr("id");
            entityJson["keyword"] = trs.eq(i).find("td").eq(1).text();
            entityJson["adgroupId"] = trs.eq(i).find("td").eq(0).find("input").eq(2).val();
            entityJson["adgroupName"] = trs.eq(i).find("td").eq(2).text();
            entityJson["campaignId"] = trs.eq(i).find("td").eq(0).find("input").eq(1).val();
            entityJson["campaignName"] = trs.eq(i).find("td").eq(3).text();
            dataJson.push(entityJson);
        }
    }
    $.ajax({
        url: basePath + "convenienceManage/addAttention",
        type: "POST",
        data: JSON.stringify(dataJson),
        async: false,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data, textStatus, jqXHR) {
            if (data.flag) {
                alert("保存成功");
                $("#addAttention").modal("hide");
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("保存失败");
        }
    });
};

//添加新关注---选中或取消所有checkbox
var chooseAllCheckbox = function () {
    var _flag = $("input[name='keyword_add']:checkbox")[0].checked;
    $("input[name=keyword_add]:checkbox").each(function (i) {
        if (_flag) {
            $(this).attr('checked', true);
        } else {
            $(this).attr('checked', false);
        }
    });
};

//打开修改关键词出价模式窗体
var openModifyPriceModal = function () {
    /*
     * CSS样式未处理:
     *  没有选中任何关注时, 修改出价和取消关注处于屏蔽状态;
     *  选中一个关注时, 修改出价和取消关注都激活(全选按钮除外);
     *  选中多个关注时, 修改出价屏蔽, 取消关注激活.
     */
    $("input[name=radio1]:radio").each(function (i) {
        if (i == 0) {
            $("#keyWord_id").val("");
            $("#keywordPrice").val("");
        }
        if (this.value == 0) {
            $(this).attr("checked", "checked");
            return true;
        }
        $(this).attr("checked", false);
    });

    //遍历找出选中的关注
    $("input[name=keyword_select1]:checkbox").each(function (i) {
        if (this.checked) {
            $("#keyWord_id").val($("#keyWordTable tbody").find("tr").eq(i).attr("id"));
            $("#keywordPrice").val($("#keyWordTable tbody").find("tr").eq(i).find("td").eq(3).text());
        }
    });

    $("#modifyPrice").modal("show");
};

//取消关注
var cancelAttention = function () {
    var keywordIds = "";
    //遍历找出选中的关注
    $("input[name=keyword_select1]:checkbox").each(function (i) {
        if (this.checked) {
            if (i == 0)
                keywordIds += $("#keyWordTable tbody").find("tr").eq(i).attr("id");
            else {
                if (keywordIds.trim().length == 0) {
                    keywordIds += $("#keyWordTable tbody").find("tr").eq(i).attr("id");
                } else {
                    keywordIds += "," + $("#keyWordTable tbody").find("tr").eq(i).attr("id");
                }
            }
        }
    });
    if (window.confirm("是否取消所选关注?")) {
        $.post(basePath + "convenienceManage/cancelAttention",
                {keywordIds: keywordIds},
                function (data) {
                    if (data.flag)
                        alert("取消成功!");
                }, "json");
    }
};

//选中或取消全部关注
var selectAllAttention = function () {
    var _flag = $("input[name=keyword_select]:checkbox")[0].checked;
    $("input[name=keyword_select1]:checkbox").each(function (i) {
        if (_flag) {
            $(this).attr('checked', true);
        } else {
            $(this).attr('checked', false);
        }
    });
};
</script>
</body>
</html>
