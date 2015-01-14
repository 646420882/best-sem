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
    }, getKeywordStatus: function (number) {
        switch (number) {
            case 41:
                return "有效";
                break;
            case 42:
                return "暂停推广";
                break;
            case 43:
                return "不宜推广";
                break;
            case 44:
                return "搜索无效";
                break;
            case 45:
                return "待激活";
                break;
            case 46:
                return "审核中";
                break;
            case 47:
                return "搜索量过低";
                break;
            case 48:
                return "部分无效";
                break;
            case 49:
                return "计算机搜索无效";
                break;
            case 50:
                return "移动搜索无效";
                break;
            default:
                return "本地新增";
        }
    }, regDouble: function (ids) {
        $(ids).keyup(function () {
            $(this).val($(this).val().replace(/[^0-9.]/g, ''));
        }).bind("paste", function () {  //CTR+V事件处理
            $(this).val($(this).val().replace(/[^0-9.]/g, ''));
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
    },
    getQuality: function (number) {
        var quanlityHtml = "<span>";
        var quanlityText = "";
        if (number > 0) {
            switch (number) {
                case 11:
                case 12:
                case 13:
                    quanlityHtml += "<img src='/public/img/star.png'>";
                    quanlityHtml += "<img src='/public/img/star3.png'>";
                    quanlityHtml += "<img src='/public/img/star3.png'>";
                    quanlityHtml += "<img src='/public/img/star3.png'>";
                    quanlityHtml += "<img src='/public/img/star3.png'>";
                    break;
                case 21:
                case 22:
                    quanlityHtml += "<img src='/public/img/star.png'>";
                    quanlityHtml += "<img src='/public/img/star.png'>";
                    quanlityHtml += "<img src='/public/img/star3.png'>";
                    quanlityHtml += "<img src='/public/img/star3.png'>";
                    quanlityHtml += "<img src='/public/img/star3.png'>";
                    break;
                case 3:
                    quanlityHtml += "<img src='/public/img/star3.png'>";
                    quanlityHtml += "<img src='/public/img/star3.png'>";
                    quanlityHtml += "<img src='/public/img/star3.png'>";
                    quanlityHtml += "<img src='/public/img/star3.png'>";
                    quanlityHtml += "<img src='/public/img/star3.png'>";
            }
            switch (number) {
                case 11:
                    quanlityText = "一星较难优化";
                    break;
                case 12:
                    quanlityText = "一星难度中等";
                    break;
                case 13:
                    quanlityText = "一星较易优化";
                    break;
                case 21:
                    quanlityText = "二星较难优化";
                    break;
                case 22:
                    quanlityText = "二星较易优化";
                    break;
                case 3:
                    quanlityText = "三星";
                    break;
            }
        }
        quanlityHtml += "&nbsp;&nbsp;&nbsp;" + quanlityText + "</span>";
        return quanlityHtml;
    }, getMobileQuanlity: function (number) {
        var mobileQuanlityHtml = "<span>";
        if (number > 0) {
            for (var i = 1; i <= 5; i++) {
                if (number >= i) {
                    mobileQuanlityHtml += "<img src='/public/img/star.png'>";
                } else {
                    mobileQuanlityHtml += "<img src='/public/img/star3.png'>";
                }
            }
            mobileQuanlityHtml += "&nbsp;&nbsp;&nbsp;" + number;
        }
        mobileQuanlityHtml += "</span>";
        return mobileQuanlityHtml;
    }, getCampaignStatus: function (number) {
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
                return "本地处理";
                break;
        }

    }
    , PlanStatus: function (number) {
        switch (number) {
            case 21:
                return "有效";
                break;
            case 22:
                return "处于暂停时段";
                break;
            case 23:
                return "暂停推广";
                break;
            case 24:
                return "推广计划预算不足";
                break;
            case 25:
                return "账户预算不足";
                break;
            default :
                return "本地处理";
                break;
        }

    }
};
