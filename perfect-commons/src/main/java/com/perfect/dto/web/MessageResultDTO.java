package com.perfect.dto.web;

/**
 * Created by xiaowei on 14-11-25.
 */
public final class MessageResultDTO {
    private Object success;
    private Object data;

    /**
     * 构造器
     *
     * @param success 是否成功
     * @param data    数据对象
     */
    public MessageResultDTO(Object success, Object data) {
        this.success = success;
        this.data = data;
    }

    /**
     * 是否成功
     *
     * @return
     */
    public Object getSuccess() {
        return success;
    }

    /**
     * 获取数据对象
     *
     * @return
     */
    public Object getData() {
        return data;
    }
}
