package com.perfect.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
@Document(collection = "SystemUser")
public class SystemUserEntity {

    private String id;

    private String userName;

    private String password;

    private List<BaiduAccountInfoEntity> baiduAccountInfoEntities;

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

    public List<BaiduAccountInfoEntity> getBaiduAccountInfoEntities() {
        return baiduAccountInfoEntities;
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
}
