package com.perfect.commons.constants;

import java.util.EnumMap;

/**
 * Created by yousheng on 2015/12/13.
 */
public enum LogTypeEnum {
    ACCOUNT_ID(1), DEFAULT(-1);


    private static EnumMap<LogTypeEnum, String> logTypeMap = new EnumMap<LogTypeEnum, String>(LogTypeEnum.class);

    static{
        logTypeMap.put(ACCOUNT_ID,"{%type% %name%} %prop% 从%before%到%after%");
    }
    public String layout(LogTypeEnum logTypeEnum){
        return logTypeMap.getOrDefault(logTypeEnum,"{%type% %name%} %prop% 从%before%到%after%");
    }

    private int value;

    LogTypeEnum(int value){
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
