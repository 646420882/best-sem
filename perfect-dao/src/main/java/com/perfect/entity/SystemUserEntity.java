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
@Document(collection = "SystemUser")
public class SystemUserEntity implements Serializable {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userName;

    private String password;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemUserEntity that = (SystemUserEntity) o;

        if (baiduAccountInfoEntities != null ? !baiduAccountInfoEntities.equals(that.baiduAccountInfoEntities) : that.baiduAccountInfoEntities != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (baiduAccountInfoEntities != null ? baiduAccountInfoEntities.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SystemUserEntity{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", baiduAccountInfoEntities=" + baiduAccountInfoEntities +
                '}';
    }

}
