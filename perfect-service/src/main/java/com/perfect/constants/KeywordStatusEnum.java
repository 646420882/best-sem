package com.perfect.constants;

/**
 * Created by vbzer_000 on 2014/9/3.
 */
public enum KeywordStatusEnum {

    STATUS_UNKNOWN(-1, "未同步"),
    STATUS_AVAILABEL(41, "有效"), STATUS_PAUSE(42, "暂停推广"),
    STATUS_NOGOOD(43, "不宜推广"), STATUS_SEARCH_UNAVAILABLE(44, "搜索无效"),
    STATUS_WAIT(45, "待激活"), STATUS_AUDITING(46, "审核中"),
    STATUS_SEARCH_LOW(47, "搜索量过低"), STATUS_PART_UNAVAILABLE(48, "部分无效"),
    STATUS_PC_UNAVAILABLE(49, "计算机搜索无效"), STATUS_MOBILE_UNAVAILABLE(50, "移动搜索无效");
    private final int val;
    private final String name;

    private KeywordStatusEnum(int val, String name) {
        this.val = val;
        this.name = name;
    }

    public static String getName(int val) {
        for (KeywordStatusEnum statusEnum : KeywordStatusEnum.values()) {
            if (statusEnum.val == val) {
                return statusEnum.name;
            }
        }
        return null;
    }
}
