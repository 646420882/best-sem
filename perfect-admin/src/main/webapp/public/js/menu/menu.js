/**
 * Created by guochunyan on 2015/12/16.
 */
/*权限模块中的全选*/
function checkboxCheck(obj, index, allId) {
    var isFlag = true;
    /*判断全选按钮是否选中*/
    var isFlag1 = true;
    /*判断全选按钮是否选中*/
    var isFlag2 = true;
    /*判断全选按钮是否选中*/
    if (index == 1) {/*点击1级全选*/
        if ($(obj).prop('checked') == false) {
            $("#" + allId).find("input").each(function () {
                $(this).prop('checked', false);
            })
        } else {
            $("#" + allId).find("input").each(function () {
                $(this).prop('checked', true);
            })
        }
    } else if (index == 2) {/*点击2级全选*/
        if ($(obj).prop('checked') == false) {
            $("#" + allId).find("input").each(function () {
                $(this).prop('checked', false);
            });
            $("#adminModuleAll").find("input").prop('checked', false);
        } else {
            $("#" + allId).find("input").each(function () {
                $(this).prop('checked', true);
            });
            if ($("#adminModuleWeb").find("input").prop('checked') == true && $("#adminModuleReport").find("input").prop('checked') == true) {
                $("#adminModuleAll").find("input").prop('checked', true);
            }
        }
    } else if (index == 3) {
        if ($(obj).prop('checked') == false) {
            $(obj).parent().parent().parent().find("input").each(function () {
                $(this).prop('checked', false);
            });
            $(obj).parent().parent().parent().parent().siblings().find("input").prop('checked', false);
            $("#adminModuleAll").find("input").prop('checked', false);
        } else {
            $(obj).parent().parent().siblings().find("input").each(function () {
                $(this).prop('checked', true);
            });
            $(obj).parent().parent().parent().parent().find("input").each(function (i) {
                if ($(this).prop('checked') == false) {
                    isFlag = false;
                    return;
                }
            });
            if (isFlag == true) {
                $(obj).parent().parent().parent().parent().prev().find("input").prop('checked', true);
                if ($("#adminModuleWeb").find("input").prop('checked') == true) {
                    $("#adminModuleAll").find("input").prop('checked', true);
                }
            }
        }
    } else if (index == 4) {
        if ($(obj).prop('checked') == false) {
            $(obj).parent().parent().parent().prev().find("input").prop('checked', false);
            $("#adminModuleAll").find("input").prop('checked', false);
        } else {
            $(obj).parent().parent().parent().find("input").each(function () {
                if ($(this).prop('checked') == false) {
                    isFlag = false;
                    return;
                }
            });
            if (isFlag == true) {
                $(obj).parent().parent().parent().prev().find("input").prop('checked', true);
                if ($("#adminModuleReport").find("input").prop('checked') == true) {
                    $("#adminModuleAll").find("input").prop('checked', true);
                }
            }
        }
    } else if (index == 5) {
        if ($(obj).prop('checked') == false) {
            $(obj).parent().parent().parent().find("input").eq(0).prop('checked', false);
            $(obj).parent().parent().parent().parent().prev().find("input").prop('checked', false);
            $("#adminModuleAll").find("input").prop('checked', false);
        } else {
            $(obj).parent().parent().parent().find("input").each(function (i) {
                if (i > 0) {
                    if ($(this).prop('checked') == false) {
                        isFlag = false;
                        return;
                    }
                }
            });
            if (isFlag == true) {
                $(obj).parent().parent().parent().find("input").eq(0).prop('checked', true);
                $(obj).parent().parent().parent().parent().find("input").each(function (i) {
                    if ($(this).prop('checked') == false) {
                        isFlag1 = false;
                        return;
                    }
                });
                if (isFlag1) {
                    $("#adminModuleReport").find("input").prop('checked', true);
                    $("#adminModuleWeb").find("input").each(function (i) {
                        if ($(this).prop('checked') == false) {
                            isFlag2 = false;
                            return;
                        }
                    });
                    if (isFlag2 == true) {
                        $("#adminModuleAll").find("input").each(function () {
                            $(this).prop('checked', true);
                        })
                    }
                }
            }
        }
    }
}
function skCheckAll(obj, index) {
    var isFlag = true;
    if (index == 1) {
        if ($(obj).prop('checked') == false) {
            $(obj).parent().parent().next().find("input").each(function () {
                $(this).prop('checked', false);
            })
        } else {
            $(obj).parent().parent().next().find("input").each(function () {
                $(this).prop('checked', true);
            })
        }
    } else if (index == 2) {
        if ($(obj).prop('checked') == false) {
            $(obj).parent().parent().parent().parent().prev().find("input").each(function () {
                $(this).prop('checked', false);
            })
        } else {
            $(obj).parent().parent().parent().find("input").each(function () {
                if ($(this).prop('checked') == false) {
                    isFlag = false;
                    return;
                }
            });
            if (isFlag == true) {
                $(obj).parent().parent().parent().parent().prev().find("input").each(function () {
                    $(this).prop('checked', true);
                })
            }
        }
    }
}
//切换模块
function transformModule(obj) {
    var _val = $(obj).val();
    if (_val == 1) {
        $("#skJurisdiction").css("display", "none");
        $("#hyJurisdiction").css("display", "block");
        $("#moduleName").html("慧眼")
    } else if (_val == 2) {
        $("#skJurisdiction").css("display", "block");
        $("#hyJurisdiction").css("display", "none");
        $("#moduleName").html("搜客")
    }
}

$(function () {
    loadModuleMenusCache();

    $('.setJurisdictionBtn input[type=button]').first().click(function () {
        var sysUserName = $('#sysUserName').val();
        if (sysUserName == null || sysUserName.trim() == "") {
            alert("请输入用户名!");
            return;
        }

        var userId = getSysUserId(sysUserName);
        if (userId == null) {
            alert("该用户不存在!");
            return;
        }

        // 读取页面更新后的权限菜单
        var moduleName = $('#moduleSelected').find('option:selected').text();
        var moduleId = moduleIdNameMap.get(moduleName);

        var menus = [];

        $.each(moduleMenusCacheMap.get(moduleName), function (i, item) {
            if (moduleName == "百思搜客") {
                if ($("input[menuname='" + item + "']").prop('checked')) {
                    menus.push(item);
                }
            } else if (moduleName == "百思慧眼") {
                if (item == "报告模块") {
                    $.each(moduleMenusCacheMap.get(item), function (j, item1) {
                        if (moduleMenusCacheMap.containsKey(item1)) {
                            $.each(moduleMenusCacheMap.get(item1), function (k, item2) {
                                if ($("input[menuname='" + item2 + "']").prop('checked')) {
                                    menus.push(item1 + "|" + item2);
                                }
                            });
                        } else {
                            menus.push(item1);
                        }
                    });
                } else if (item == "网站统计设置") {
                    $.each(moduleMenusCacheMap.get(item), function (j, item1) {
                        if ($("input[menuname='" + item1 + "']").prop('checked')) {
                            menus.push(item + "|" + item1);
                        }
                    });
                }
            }
        });

        var userModuleMenuObj = {};
        userModuleMenuObj["menus"] = menus;

        $.ajax({
            url: '/users/' + userId + '/modules/' + moduleId + '/menus',
            type: 'POST',
            dataType: 'JSON',
            data: userModuleMenuObj,
            success: function (data) {
                console.log(JSON.stringify(data));
            }
        });
    });

    $('#sysUserName').blur(function () {
        loadUserModuleMsg();
    });

    $('#moduleSelected').change(function () {
        loadUserModuleMsg();
    });
});

var loadUserModuleMsg = function () {
    var sysUserName = $('#sysUserName').val();
    if (sysUserName != null && sysUserName.trim().length > 0) {
        var sysUserId = getSysUserId(sysUserName);
        if (sysUserId == null) {
            // 取消页面所有选中的checkbox
            $('#soukeSelectedAll').prop('checked', false);
            $('#adminModuleAll').find('input').prop('checked', false);

            $.each(moduleMenusCacheMap.values(), function (i, menus) {
                $.each(menus, function (j, menu) {
                    $("input[menuname='" + menu + "']").prop('checked', false);
                });
            });

            return;
        }

        // 加载给定用户的模块权限信息
        $.ajax({
            url: '/users/' + sysUserId + '/modules',
            type: 'GET',
            dataType: 'JSON',
            success: function (data) {
                var _moduleName = $('#moduleSelected').find('option:selected').text();

                $.each(data.data, function (i, item) {
                    moduleIdNameMap.put(item.moduleName, item.id);

                    if (item.moduleName == _moduleName) {
                        // 解析菜单信息
                        $.each(item.moduleMenus, function (j, item1) {
                            var menuMap = new Map();
                            $.each(item1.menus, function (k, item2) {
                                var _menuArr = item2.split("|");
                                var key = _menuArr[0];
                                if (_menuArr.length == 1) {
                                    var mi = $("input[menuname='" + key + "']");
                                    if (mi.length > 0) {
                                        menuMap.put(key, 1);
                                        mi.prop('checked', true);
                                    }
                                } else {
                                    var mj = $("input[menuname='" + _menuArr[1] + "']");
                                    if (mj.length > 0) {
                                        mj.prop('checked', true);

                                        if (menuMap.containsKey(key)) {
                                            menuMap.put(key, menuMap.get(key) + 1);
                                        } else {
                                            menuMap.put(key, 1);
                                        }
                                    }
                                }
                            });

                            menuMap.elements.forEach(function (item0) {
                                if (moduleMenusCacheMap.containsKey(item0.key)) {
                                    if (moduleMenusCacheMap.get(item0.key).length == item0.value) {
                                        $("input[menuname='" + item0.key + "']").prop('checked', true);
                                    }
                                }
                            });
                        });

                    }
                });

                if (_moduleName == "百思搜客") {
                    var indexForSouke = 0;
                    $.each(moduleMenusCacheMap.get(_moduleName), function (l, menuName) {
                        if ($("input[menuname='" + menuName + "']").prop('checked')) {
                            indexForSouke += 1;
                        }
                    });

                    if (moduleMenusCacheMap.get(_moduleName).length == indexForSouke) {
                        $('#soukeSelectedAll').prop('checked', true);
                    }
                } else if (_moduleName == "百思慧眼") {
                    $.each(moduleMenusCacheMap.get(_moduleName), function (index, item) {
                        var indexForHuiyan = 0;
                        $.each(moduleMenusCacheMap.get(item), function (j, menuName) {
                            if ($("input[menuname='" + menuName + "']").prop('checked')) {
                                indexForHuiyan += 1;
                            }
                        });

                        if (moduleMenusCacheMap.get(item).length == indexForHuiyan) {
                            $("input[menuname='" + item + "']").prop('checked', true);
                        }
                    });

                    var isSelectAllForHuiyan = $('input[menuname=报告模块]').prop('checked') &&
                        $('input[menuname=网站统计设置]').prop('checked');
                    if (isSelectAllForHuiyan) {
                        $('#adminModuleAll').find('input').prop('checked', true);
                    }
                }

            }
        });
    }
};

var getSysUserId = function (userName) {
    var _sysUserId = null;

    $.ajax({
        url: '/users/' + userName + '/getId',
        async: false,
        type: 'POST',
        dataType: 'JSON',
        success: function (data) {
            _sysUserId = data.userId;
        }
    });

    if (_sysUserId == null || _sysUserId.trim().length == 0) {
        return null;
    }

    return _sysUserId;
};

/**
 * 加载模块菜单缓存
 */
var moduleMenusCacheMap = new Map();
var loadModuleMenusCache = function () {
    // 百思搜客
    moduleMenusCacheMap.put("百思搜客", ["账户全景", "推广助手", "智能结构", "智能竞价", "数据报告"]);
    // 百思慧眼
    moduleMenusCacheMap.put("百思慧眼", ["报告模块", "网站统计设置"]);
    moduleMenusCacheMap.put("报告模块", ["网站概览", "趋向分析", "来源分析", "页面分析", "访客分析", "价值透析", "转化分析"]);
    moduleMenusCacheMap.put("网站统计设置", ["统计规则设置", "子目录管理", "页面转化目标", "事件转化目标", "时长转化目标", "指定广告跟踪"]);
    moduleMenusCacheMap.put("趋向分析", ["实时访客", "今日统计", "昨日统计", "最近30天"]);
    moduleMenusCacheMap.put("来源分析", ["全部来源", "搜索引擎", "搜索词", "外部链接"]);
    moduleMenusCacheMap.put("页面分析", ["受访页面", "入口页面", "页面热点图"]);
    moduleMenusCacheMap.put("访客分析", ["访客地图", "设备环境", "新老访客"]);
    moduleMenusCacheMap.put("价值透析", ["流量地图", "频道流转"]);
    moduleMenusCacheMap.put("转化分析", ["事件转化", "页面转化"]);
};

var moduleIdNameMap = new Map();
var setModuleIdNameMsg = function () {
    ;
};


/*
 * Map对象, 实现Map功能
 *
 * 接口:
 * size()     获取Map元素个数
 * isEmpty()    判断Map是否为空
 * clear()     删除Map所有元素
 * put(key, value)   向Map中增加元素（key, value)
 * remove(key)    删除指定key的元素，成功返回true, 失败返回false
 * get(key)    获取指定key的元素值value，失败返回NULL
 * element(index)   获取指定索引的元素(使用element.key, element.value获取key和value), 失败返回NULL
 * containsKey(key)  判断Map中是否含有指定KEY的元素
 * containsValue(value) 判断Map中是否含有指定VALUE的元素
 * values()    获取Map中所有value的数组（Array）
 * keys()     获取Map中所有key的数组（Array）
 *
 * ex:
 * var map = new Map();
 *
 * map.put("key", "value");
 * var val = map.get("key")
 * ……
 *
 */
function Map() {
    this.elements = [];

    //获取Map元素个数
    this.size = function () {
        return this.elements.length;
    };

    //判断Map是否为空
    this.isEmpty = function () {
        return (this.elements.length < 1);
    };

    //删除Map所有元素
    this.clear = function () {
        this.elements = [];
    };

    //向Map中增加元素（key, value)
    this.put = function (_key, _value) {
        if (this.get(_key) != null)
            this.remove(_key);
        this.elements.push({
            key: _key,
            value: _value
        });
    };

    //删除指定key的元素，成功返回true，失败返回false
    this.remove = function (_key) {
        var bln = false;
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    this.elements.splice(i, 1);
                    return true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    };

    //获取指定key的元素值value，失败返回NULL
    this.get = function (_key) {
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    return this.elements[i].value;
                }
            }
        } catch (e) {
            return null;
        }
    };

    //获取指定索引的元素(使用element.key, element.value获取key和value), 失败返回NULL
    this.element = function (_index) {
        if (_index < 0 || _index >= this.elements.length) {
            return null;
        }
        return this.elements[_index];
    };

    //判断Map中是否含有指定key的元素
    this.containsKey = function (_key) {
        var bln = false;
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    bln = true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    };

    //判断Map中是否含有指定value的元素
    this.containsValue = function (_value) {
        var bln = false;
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].value == _value) {
                    bln = true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    };

    //获取Map中所有value的数组（Array）
    this.values = function () {
        var arr = [];
        for (var i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].value);
        }
        return arr;
    };

    //获取Map中所有key的数组（Array）
    this.keys = function () {
        var arr = [];
        for (var i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].key);
        }
        return arr;
    };
}