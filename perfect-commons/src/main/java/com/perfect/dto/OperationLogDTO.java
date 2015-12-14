package com.perfect.dto;

/**
 * Created by yousheng on 14/12/1.
 */
public class OperationLogDTO extends BaseDTO {


    private Long bid;

    private String oid;

    private String type;

    private int opt;

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOpt() {
        return opt;
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

}
