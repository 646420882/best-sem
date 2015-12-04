<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>历史操作记录</title>
    <meta name="renderer" content="webkit">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
    <%-- <link href="css/font-awesome.min.css" rel="stylesheet">--%>
    <link rel="stylesheet" type="text/css" media="all"
          href="${pageContext.request.contextPath}/public/themes/flick/daterangepicker-bs2.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/log/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/log/index.css">
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/public/js/bootstrap-daterangepicker-moment.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/daterangepicker.js"></script>
    <script type="text/javascript">
        //推广账户

        var tgzhJson = [
            {"id": "zhys", "name": "预算", "flag": false},
            {"id": "zhtgdy", "name": "推广地域", "flag": false},
            {"id": "zhdtcy", "name": " 动态创意", "flag": false},
            {"id": "zhpc", "name": " IP排除", "flag": false}
        ];
        //推广计划
        var tgjhJson = [
            {"id": "jhxjjh", "name": " 新建计划", "flag": false},
            {"id": "jhscjh", "name": " 删除推广计划", "flag": false},
            {"id": "jhzqtg", "name": " 暂停/启用推广", "flag": false},
            {"id": "jhsdgl", "name": " 推广时段管理", "flag": false},
            {"id": "jhys", "name": " 每日预算", "flag": false},
            {"id": "jhdy", "name": "推广地域", "flag": false},
            {"id": "jhpc", "name": " IP排除", "flag": false},
            {"id": "jhfs", "name": " 展现方式", "flag": false},
            {"id": "jhyngjc", "name": " 否定关键词", "flag": false},
            {"id": "jhjq", "name": "精确否定关键词", "flag": false},
            {"id": "jhxzurl", "name": " 新增可抓取URL", "flag": false},
            {"id": "jhscurl", "name": " 删除可抓取URL", "flag": false},
            {"id": "jhgxsb", "name": " 勾选投放设备", "flag": false},
            {"id": "jhqhsb", "name": " 切换投放设备", "flag": false},
            {"id": "jhtgdh", "name": " 推广电话", "flag": false},
            {"id": "jhzx", "name": " 商桥移动咨询", "flag": false},
            {"id": "jhcj", "name": " 修改移动出价", "flag": false},
            {"id": "jhpp", "name": " 高级精确匹配", "flag": false},
            {"id": "jhxs", "name": " 推广时段系数", "flag": false},
            {"id": "jhcy", "name": " 动态创意", "flag": false}
        ];
        //推广单元
        var tgdyJson = [
            {"id": "dyxj", "name": " 新建单元", "flag": false},
            {"id": "dysc", "name": " 删除单元", "flag": false},
            {"id": "dyzqtg", "name": " 暂停/启用推广", "flag": false},
            {"id": "dyxgcj", "name": " 修改单元出价", "flag": false},
            {"id": "dybjmc", "name": " 编辑单元名称", "flag": false},
            {"id": "dyfd", "name": " 否定关键词", "flag": false},
            {"id": "dyjq", "name": " 精确否定关键词", "flag": false},
            {"id": "dyxgbl", "name": " 修改移动出价比例", "flag": false},
            {"id": "dyxzurl", "name": " 新增可抓取URL", "flag": false},
            {"id": "dyscurl", "name": " 删除可抓取URL", "flag": false}
        ];
        //关键词
        var gjcJson = [
            {"id": "gjctj", "name": " 添加关键词", "flag": false},
            {"id": "gjcsc", "name": " 删除关键词", "flag": false},
            {"id": "gjczqtg", "name": " 暂停/启用推广", "flag": false},
            {"id": "gjcxgcj", "name": " 修改关键词出价", "flag": false},
            {"id": "gjcpp", "name": " 匹配方式", "flag": false},
            {"id": "gjcydurl", "name": " 移动URL", "flag": false},
            {"id": "gjczy", "name": " 转移关键词", "flag": false},
            {"id": "gjcurl", "name": " 关键词URL", "flag": false}
        ];
        //创意
        var cyJson = [
            {"id": "cyxj", "name": " 新建创意", "flag": false},
            {"id": "cysc", "name": " 删除创意", "flag": false},
            {"id": "cyzqtg", "name": " 暂停/启用推广", "flag": false},
            {"id": "cybj", "name": " 编辑创意", "flag": false},
            {"id": "cyxgsb", "name": " 修改设备偏好", "flag": false}
        ];

        //蹊径
        var xjJson = [
            {"id": "xjxj", "name": " 新建子链", "flag": false},
            {"id": "xjsc", "name": " 删除子链", "flag": false},
            {"id": "xjbj", "name": " 编辑子链", "flag": false},
            {"id": "xjzq", "name": " 暂停/启用子链", "flag": false}
        ];
        //推广电话
        var tgdhJson = [
            {"id": "dhxj", "name": " 新建推广电话", "flag": false},
            {"id": "dhsc", "name": " 删除推广电话", "flag": false},
            {"id": "dhbj", "name": " 编辑推广电话", "flag": false},
            {"id": "dhzq", "name": " 暂停/启用推广电话", "flag": false}
        ];
        //商桥移动咨询
        var sqydzxJson = [
            {"id": "ydzxxj", "name": " 新建商桥移动咨询", "flag": false},
            {"id": "ydzxxc", "name": " 删除商桥移动咨询", "flag": false},
            {"id": "ydzxzq", "name": " 暂停/启用商桥移动咨询", "flag": false}
        ];

        /*  var mainJson = [{"id": "3", "name": "3-1", data: details1},{"id": "3", "name": "3-2", data: details2}];*/
        var mainJson = [
            {"id": "tgzh", "name": "推广账户", "flag": false, data: tgzhJson},
            {"id": "tgjh", "name": "推广计划", "flag": false, data: tgjhJson},
            {"id": "tgdy", "name": "推广单元", "flag": false, data: tgdyJson},
            {"id": "gjc", "name": "关键词", "flag": false, data: gjcJson},
            {"id": "cy", "name": "普通创意", "flag": false, data: cyJson},
            /*  {"id": "3", "name": "蹊径", data: xj},
             {"id": "3", "name": "推广电话", data: tgdh},
             {"id": "3", "name": "商桥移动咨询", data: sqydzx}*/
        ];
        /*  {"id": "3", "name": "附加创意", data: fjcy}*/
        //附加创意
        var fjcyJson = [
            {"id": "xj", "name": "蹊径", "flag": false, data: xjJson},
            {"id": "tgdh", "name": "推广电话", "flag": false, data: tgdhJson},
            {"id": "sqydzx", "name": "商桥移动咨询", "flag": false, data: sqydzxJson}
        ];

        var planarry = [
            {"id": "1", "name": "test"},
            {"id": "2", "name": "test2"}
        ];

        $(function () {
            addHistoryRadio();
            addHistoryCheckbox();
        });

    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/js/log/radio.js"></script>
</head>
<body>
<div class="historydiv">
    <div class="history-sel-box">
        <!-- 选择 -->
        <div class="radioselect-box" id="radioselect-box"></div>
        <div class="line"></div>
        <div class="object">
            选定的对象列表：
            <span class="num">( <span id="obj-num">1</span>个 )</span>
        </div>
        <div class="obj-box">
            <div class="obj-select">
                <div class="obj-select-list">
                    <!--   <div class="obj-list" id="obj-list">
                        <a href="#">baidu-perfect2151880</a>
                        <div class="obj-list-del"></div>
                      </div> -->
                </div>
                <div class="add-obj">
                    <a id="addobj-button">+添加对象</a>
                </div>
            </div>
            <div class="select-time-box">
                <p>选择时间范围：</p>

                <div class="container date-box">
                    <form class="form-horizontal">
                        <fieldset>
                            <div class="control-group">
                                <div class="controls">
                                    <div class="input-prepend input-group">
                                        <input type="text" readonly="readonly" style="width: 200px" name="reservation"
                                               id="reservation" class="form-control"/>
                                        <span class="add-on input-group-addon"><i
                                                class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>
                <p style="margin-top:15px;">选择操作内容：</p>

                <div class="ctrlbuttonoptSelect"></div>
            </div>
            <!-- 计划列表 -->
            <div class="plan-box">
                <h2>计划列表<a href="javascript:;" class="close-ico"></a></h2>

                <form action="">
                    <input type="text">
                    <button>查询</button>
                </form>
                <div class="query-result" id="query-result">
                    <ul>
                        <li><a href="javascript:;">添加</a><span>test</span></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="download-box">
            <button class="query-btn">查询</button>
            <a href="#" class="download">↓下载历史操作记录</a>
        </div>
    </div>
</div>
<div class="history-list-box" id="tableTry">
    <table cellspacing="0" width="100%">
        <thead>
        <tr>
            <th width="300px">时间</th>
            <th width="300px">操作人</th>
            <th width="300px">层级</th>
            <th width="300px">层级名称</th>
            <th width="300px">操作内容</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>时间</td>
            <td>操作人</td>
            <td>层级</td>
            <td>层级名称</td>
            <td>操作内容</td>
        </tr>
        </tbody>
    </table>

</div>

<!-- 提示弹出框 -->
<div class="black tip">
    <div class="tip-box">
        <h2>提醒 <a href="javascript:;" class="close-tip"></a></h2>

        <p>您选择其他层级后，会清空您当前的选定对象列表，确认清空当前列表吗？</p>

        <div class="operation">
            <a href="javascript:;" class="cancel">取消</a>
            <a href="javascript:;" class="submit">确定</a>
        </div>
    </div>
</div>
<!-- 操作弹出框 -->
<div class="black operation-tip">
    <div class="tip-box">
        <h2>选择操作内容 <a href="javascript:;" class="close-tip"></a></h2>

        <div class="check-all">
            <a href="javascript:;" class="check-now" id="all-check">全部</a>|<a href="javascript:;"
                                                                              id="user-defined">自定义</a>
        </div>
        <div class="operation-tip-list" id="operation-tip-list">
            <!-- <div class="list-view">
                <input id="cccc" type="checkbox" checked="checked" name="1">
                <lable for="cccc">测试</lable>
            </div>
            <div class="list-view-r">
                <ul>
                    <li>
                        <input id="cd" type="checkbox" name="1">
                        <lable for="cd">测试</lable>
                    </li>
                </ul>
            </div>	 -->
        </div>
        <div class="operation">
            <a href="javascript:;" class="cancel">取消</a>
            <a href="javascript:;" class="submit">确定</a>
        </div>

    </div>
</div>
</body>
</html>