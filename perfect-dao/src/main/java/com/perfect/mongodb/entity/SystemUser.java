package com.perfect.mongodb.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
@Document(collection = "SystemUser")
public class SystemUser {

    private String id;

    private String userName;

    private String password;

    private List<BaiduAccountInfo> baiduAccountInfos;

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

    public List<BaiduAccountInfo> getBaiduAccountInfos() {
        return baiduAccountInfos;
    }

    public void setBaiduAccountInfos(List<BaiduAccountInfo> baiduAccountInfos) {
        this.baiduAccountInfos = baiduAccountInfos;
    }

    public void addBaiduAccountInfo(BaiduAccountInfo baiduAccountInfo){
        if(baiduAccountInfos == null){
            baiduAccountInfos = new ArrayList<BaiduAccountInfo>();
        }
        baiduAccountInfos.add(baiduAccountInfo);
    }
}
