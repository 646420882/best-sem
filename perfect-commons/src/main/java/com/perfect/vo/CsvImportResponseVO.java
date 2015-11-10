package com.perfect.vo;

/**
 * @author xiaowei
 * @title CsvImportResponseVO
 * @package com.perfect.vo
 * @description
 * @update 2015年11月10日. 下午4:31
 */
public class CsvImportResponseVO {
    String msg;
    Object data;
    ValidateKeywordVO vk;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ValidateKeywordVO getVk() {
        return vk;
    }

    public void setVk(ValidateKeywordVO vk) {
        this.vk = vk;
    }

    @Override
    public String toString() {
        return "CsvImportResponseVO{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                ", vk=" + vk +
                '}';
    }
}
