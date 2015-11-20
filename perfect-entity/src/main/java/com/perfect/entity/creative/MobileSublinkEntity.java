package com.perfect.entity.creative;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.entity.account.AccountIdEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Created by XiaoWei on 2015/2/27.
 * mobile蹊径子链
 */
@Document(collection = MongoEntityConstants.TBL_MOBILESUBLINK)
public class MobileSublinkEntity extends AccountIdEntity {
    @Id
    private String id;
    @Field(value =MongoEntityConstants.SUBLINK_ID)
    private Long sublinkId;     //mobile蹊径子链id

    @Field(value = "sif")
    private List<MobileSublinkInfoEntity> mobileSublinkInfos;//mobile蹊径子链信息

    @Field(value = MongoEntityConstants.ADGROUP_ID)
    private Long adgroupId;     //单元ID

    @Field(value = "p")
    private Boolean pause;      //暂停/启用

    @Field(value = "s")
    private Integer status;     //状态

    @Field(MongoEntityConstants.OBJ_ADGROUP_ID)
    private String adgroupObjId;    //本地单元ID

    public Long getAdgroupId() {
        return adgroupId;
    }

    public void setAdgroupId(Long adgroupId) {
        this.adgroupId = adgroupId;
    }

    public String getAdgroupObjId() {
        return adgroupObjId;
    }

    public void setAdgroupObjId(String adgroupObjId) {
        this.adgroupObjId = adgroupObjId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MobileSublinkInfoEntity> getMobileSublinkInfos() {
        return mobileSublinkInfos;
    }

    public void setMobileSublinkInfos(List<MobileSublinkInfoEntity> mobileSublinkInfos) {
        this.mobileSublinkInfos = mobileSublinkInfos;
    }

    public Boolean getPause() {
        return pause;
    }

    public void setPause(Boolean pause) {
        this.pause = pause;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getSublinkId() {
        return sublinkId;
    }

    public void setSublinkId(Long sublinkId) {
        this.sublinkId = sublinkId;
    }

    @Override
    public String toString() {
        return "MobileSublinkEntity{" +
                "adgroupId=" + adgroupId +
                ", id='" + id + '\'' +
                ", sublinkId=" + sublinkId +
                ", mobileSublinkInfos=" + mobileSublinkInfos +
                ", pause=" + pause +
                ", status=" + status +
                ", adgroupObjId='" + adgroupObjId + '\'' +
                '}';
    }
}
