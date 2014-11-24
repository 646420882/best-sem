package com.perfect.utils.web.dto;

/**
 * Created by XiaoWei on 2014/9/2.
 */

public final class MessageResult {
    private Object success;
    private Object data;


    /**
     * 构造器
     * @param success 是否成功
     * @param message 文字信息
     */
    public MessageResult(Object success, String message) {
        this.success = success;

    }

    /**
     * 构造器
     * @param success 是否成功
     * @param data 数据对象
     */
    public MessageResult(Object success, Object data) {
        this.success = success;
        this.data = data;
    }

    /**
     * 是否成功
     * @return
     */
    public Object getSuccess() {
        return success;
    }

    /**
     * 获取数据对象
     * @return
     */
    public Object getData() {
        return data;
    }

}
