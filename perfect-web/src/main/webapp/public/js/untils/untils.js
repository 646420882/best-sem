/**
 * Created by XiaoWei on 2014/8/21.
 */
var subData = {};
var formData = {};
$.fn.extend({
    submit: function (url, func) {
        var data = {};
        var inputs = $(this).parents("tr").find("input");
        for (var i = 0; i < inputs.size(); i++) {
            if (inputs[i].name) {
                data[inputs[i].name] = inputs[i].value;
                subData[inputs[i].name] = inputs[i].value;
            }
        }
        var selects = $(this).parents("tr").find("select");
        for (var i = 0; i < selects.size(); i++) {
            if (selects[i].name) {
                data[selects[i].name] = selects[i].value;
                subData[selects[i].name] = selects[i].value;
            }
        }
        $.post(url, data, function (json) {
            if (func)
                func(json);
        });
    },
    formSubmit: function (url, func) {
        var inputs = $(this).find("input");
        var data = {};
        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].name) {
                data[inputs[i].name] = inputs[i].value;
                formData[inputs[i].name] = inputs[i].value;
            }
        }
        var selects = $(this).find("select");
        for (var i = 0; i < selects.length; i++) {
            data[selects[i].name] = selects[i].value;
            formData[selects[i].name] = selects[i].value;
        }
        $.post(url, data, function (json) {
            if (func)
                func(json);
        });

    }
});
var until = {
    substring: function (count, str) {
        if (str != null) {
            if (str.length > count) {
                return str.substring(0, count) + "<a href='javascript:;;' title='" + str + "'>...</a>";
            } else {
                return "<span>" + str + "</span>";
            }
        } else {
            return "<span>空</span>";
        }
    },
    convert: function (bol, format) {
        return bol ? format.split(":")[0] : format.split(":")[1];
    },
    getCreativeStatus: function (number) {
        switch (number) {
            case 51:
                return "有效";
                break;
            case 52:
                return "暂停推广";
                break;
            case 53:
                return "不宜推广";
                break;
            case 54:
                return "待激活";
                break;
            case 55:
                return "待审核";
                break;
            case 56:
                return "部分无效";
            default :
                return "本地新增";
                break;
        }
    }, getAdgroupStatus: function (number) {
        switch (number) {
            case 31:
                return "有效";
                break;
            case 32:
                return "暂停推广";
                break;
            case 33:
                return "推广计划暂停推广";
                break;
            default :
                return "本地新增";
                break;
        }
    },regDouble:function(ids){
        $(ids).keyup(function(){
            $(this).val($(this).val().replace(/[^0-9.]/g,''));
        }).bind("paste",function(){  //CTR+V事件处理
            $(this).val($(this).val().replace(/[^0-9.]/g,''));
        }).css("ime-mode", "disabled"); //CSS设置输入法不可用
    }, getMatchTypeName: function (num) {
        switch (num) {
            case "1":
                return "精确匹配";
                break;
            case "2":
                return "短语匹配";
                break;
            case "3":
                return "广泛匹配";
                break;
            default:
                return "精确匹配"
                break;
        }
    }
};
