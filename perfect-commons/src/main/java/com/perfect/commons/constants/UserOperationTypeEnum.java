package com.perfect.commons.constants;

import java.util.EnumMap;

/**
 * Created by yousheng on 2015/12/13.
 */
public enum UserOperationTypeEnum {
    ACCOUNT_ID(1), DEFAULT(-1),
    ADD_CAMPAIGN(101),
    MODIFY_CAMPAIGN(102),
    DEL_CAMPAIGN(103),
    ADD_ADGROUP(201),
    MODIFY_ADGROUP(202),
    DEL_ADGROUP(203),
    ADD_KEYWORD(301),
    MODIFY_KEYWORD(302),
    DEL_KEYWORD(303),

    ADD_CREATIVE(401),
    MODIFY_CREATIVE(402),
    DEL_CREATIVE(403);


    private static EnumMap<UserOperationTypeEnum, String> logTypeMap = new EnumMap<UserOperationTypeEnum, String>(UserOperationTypeEnum.class);

    static {
        logTypeMap.put(ACCOUNT_ID, "{%type% %name%} %prop% 从%before%到%after%");
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
