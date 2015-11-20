package com.perfect.param;

import java.util.List;

/**
 * @author xiaowei
 * @title 物料的剪切，复制的粘贴自定义参数类
 * @package com.perfect.param
 * @description
 * @update 2015年10月14日. 下午3:04
 */
public class EditParam {
    private String type;//获取粘贴的层级 keyword，creative....等
    private String editType;//粘贴的模式，剪切粘贴，复制粘贴
    private String editData;//被粘贴的数据
    private String cid;//计划id
    private String aid;//单元id


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEditType() {
        return editType;
    }

    public void setEditType(String editType) {
        this.editType = editType;
    }

    public String getEditData() {
        return editData;
    }

    public void setEditData(String editData) {
        this.editData = editData;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    @Override
    public String toString() {
        return "EditParam{" +
                "type='" + type + '\'' +
                ", editType='" + editType + '\'' +
                ", editData='" + editData + '\'' +
                ", cid='" + cid + '\'' +
                ", aid='" + aid + '\'' +
                '}';
    }
}
