package com.perfect.commons.constants;

import java.util.EnumMap;

/**
 * 操作内容格式
 * 默认是DEFAULT，可以通过匹配
 * Created by yousheng on 2015/12/13.
 */
public enum UserOperationLogLayoutEnum {

    DEFAULT(-1);

    private static EnumMap<UserOperationLogLayoutEnum, String> layoutMap = new EnumMap<UserOperationLogLayoutEnum, String>(UserOperationLogLayoutEnum.class);

    static{
        layoutMap.put(DEFAULT,"{%type% %name%} %prop% 从%before%到%after%");
    }
    private int type;

    UserOperationLogLayoutEnum(int type){
        this.type = type;
    }

    public String layout(UserOperationLogLayoutEnum userOperationLogLayoutEnum){
        return layoutMap.getOrDefault(userOperationLogLayoutEnum,"{%type% %name%} %prop% 从%before%到%after%");
    }

}
