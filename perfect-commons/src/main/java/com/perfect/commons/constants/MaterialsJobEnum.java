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
    PAUSE(0),

    /**
     * 激活
     */
    ACTIVE(1),


    // ============================== 任务类型 ==============================
    /**
     * 物料上传
     */
    UPLOAD_MATERIALS(10),

    /**
     * 暂停投放
     */
    PAUSE_MATERIALS(11);


    private final int value;


    MaterialsJobEnum(int value) {
        this.value = value;
    }


    public int value() {
        return value;
    }
}
