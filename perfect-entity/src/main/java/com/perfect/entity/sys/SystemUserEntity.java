package com.perfect.entity.sys;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
@Document(collection = "sys_user")
public class SystemUserEntity implements Serializable {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userName;//用户名

    private String password;//密码

    private String companyName;//公司名

    private Integer state;      //审核状态: 1审核通过, 0审核未通过

    private Integer access;     //1.admin; 2.user

    private byte[] img;//二进制头像图片

    private String email;//邮箱

    @Field(value = "bdAccounts")
    private List<BaiduAccountInfoEntity> baiduAccounts;//百度账号列表

    //系统帐号状态: 1.启用  0.禁用
    @Field(value = "acstate")
    private Integer accountState;

    public SystemUserEntity() {
    }

    @PersistenceConstructor
    public SystemUserEntity(String userName, String password,
                            List<BaiduAccountInfoEntity> baiduAccounts) {
        this.userName = userName;
        this.password = password;
        this.baiduAccounts = baiduAccounts;
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

    public byte[] getImgBytes() {
        return img;
    }

    public void setImgBytes(byte[] bytes) {
        this.img = bytes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<BaiduAccountInfoEntity> getBaiduAccounts() {
        return baiduAccounts;
    }

    public void setBaiduAccounts(List<BaiduAccountInfoEntity> baiduAccounts) {
        this.baiduAccounts = baiduAccounts;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public Integer getAccountState() {
        return accountState;
    }

    public void setAccountState(Integer accountState) {
        this.accountState = accountState;
    }

}
