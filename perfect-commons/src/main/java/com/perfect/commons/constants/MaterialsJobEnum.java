package com.perfect.commons.constants;

/**
 * Created on 2015-09-30.
 * <p>物料定时任务类型和状态枚举.
 *
 * @author dolphineor
 */
public enum MaterialsJobEnum {

    // ============================== 任务状态 ==============================
    /**
     * 暂停
     */
    PAUSE(0, "pause"),

    /**
     * 激活
     */
    ACTIVE(1, "active"),


    // ============================== 任务类型 ==============================
    /**
     * 上传启用
     */
    UPLOAD_MATERIALS(10, "uploadAndStart"),

    /**
     * 暂停投放
     */
    PAUSE_MATERIALS(11, "pause"),


    // ============================== 任务层级 ==============================
    /**
     * 推广计划
     */
    CAMPAIGN(21, "campaign"),

    /**
     * 推广单元
     */
    ADGROUP(22, "adgroup"),

    /**
     * 关键词
     */
    KEYWORD(23, "keyword"),

    /**
     * 创意
     */
    CREATIVE(24, "creative");


    private final int value;
    private final String name;


    MaterialsJobEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }


    public int value() {
        return value;
    }

    public static String getName(int val) {
        for (MaterialsJobEnum jobEnum : MaterialsJobEnum.values()) {
            if (jobEnum.value == val) {
                return jobEnum.name;
            }
        }

        return null;
    }
}
