package com.perfect.commons.constants;

import java.util.EnumMap;

/**
 * Created by yousheng on 2015/12/13.
 */
public enum UserOperationTypeEnum {
    OPERATION(0),

    ACCOUNT_ID(1), DEFAULT(-1),

    ADD_KEYWORD(101),
    ADD_CREATIVE(102),
    ADD_ADGROUP(103),
    ADD_CAMPAIGN(104),


    DEL_KEYWORD(201),
    DEL_CREATIVE(202),
    DEL_ADGROUP(203),
    DEL_CAMPAIGN(204),

    MODIFY_KEYWORD(301),
    MODIFY_CREATIVE(302),
    MODIFY_ADGROUP(303),
    MODIFY_CAMPAIGN(304);


    private static EnumMap<UserOperationTypeEnum, String> logTypeMap = new EnumMap<UserOperationTypeEnum, String>(UserOperationTypeEnum.class);

    static {
        logTypeMap.put(OPERATION, "{%type% %name%}");
    }

    public static String layout(UserOperationTypeEnum userOperationTypeEnum) {
        return logTypeMap.getOrDefault(userOperationTypeEnum, "{%type% %name%} %prop% 从%before%到%after%");
    }

    private int value;

    UserOperationTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

}
