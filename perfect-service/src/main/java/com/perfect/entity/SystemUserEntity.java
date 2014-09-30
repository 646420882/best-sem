package com.perfect.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
@Document(collection = "sys_user")
public class SystemUserEntity implements Serializable {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userName;

    private String password;
    //公司名字
    private String companyName;
    //审核状态
    private Integer state;

    private Integer access;     //1.admin; 2.user

    @Field(value = "bdAccounts")
    private List<BaiduAccountInfoEntity> baiduAccountInfoEntities;

    public SystemUserEntity() {
    }

    @PersistenceConstructor
    public SystemUserEntity(String userName, String password,
                            List<BaiduAccountInfoEntity> baiduAccountInfoEntities) {
        this.userName = userName;
        this.password = password;
        this.baiduAccountInfoEntities = baiduAccountInfoEntities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public List<BaiduAccountInfoEntity> getBaiduAccountInfoEntities() {
        return baiduAccountInfoEntities;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setBaiduAccountInfoEntities(List<BaiduAccountInfoEntity> baiduAccountInfoEntities) {
        this.baiduAccountInfoEntities = baiduAccountInfoEntities;
    }

    public void addBaiduAccountInfo(BaiduAccountInfoEntity baiduAccountInfoEntity) {
        if (baiduAccountInfoEntities == null) {
            baiduAccountInfoEntities = new ArrayList<BaiduAccountInfoEntity>();
        }
        baiduAccountInfoEntities.add(baiduAccountInfoEntity);
    }

    @Override
    public String toString() {
        return "SystemUserEntity{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", companyName='" + companyName + '\'' +
                ", state=" + state +
                ", access=" + access +
                ", baiduAccountInfoEntities=" + baiduAccountInfoEntities +
                '}';
    }
}
