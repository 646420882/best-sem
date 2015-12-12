package com.perfect.commons.constants;

import java.util.EnumMap;

/**
 * 操作内容格式
 * 默认是DEFAULT，可以通过匹配
 * Created by yousheng on 2015/12/13.
 */
public enum LogLayoutEnum {

    DEFAULT(-1);

    private static EnumMap<LogLayoutEnum, String> layoutMap = new EnumMap<LogLayoutEnum, String>(LogLayoutEnum.class);

    static{
        layoutMap.put(DEFAULT,"{%type% %name%} %prop% 从%before%到%after%");
    }
    private int type;

    LogLayoutEnum(int type){
        this.type = type;
    }

    public String layout(LogLayoutEnum logLayoutEnum){
        return layoutMap.getOrDefault(logLayoutEnum,"{%type% %name%} %prop% 从%before%到%after%");
    }

}
