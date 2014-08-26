/**
 * Created by XiaoWei on 2014/8/21.
 */
var until = {
    substring: function (count, str) {
        if (str != null) {
            if (str.length > count) {
                return str.substring(0, count) + "<a href='javascript:;;' title='" + str + "'>...</a>";
            } else {
                return "<span>"+str+"</span>";
            }
        }else{
            return "<span>空</span>";
        }
    },
    convert:function(bol,format) {
        return bol?format.split(":")[0]:format.split(":")[1];
    },
    getCreativeStatus:function(number){
        switch (number){
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
                return "新增";
                break;
        }
    }
};
