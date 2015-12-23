package com.perfect.entity.sys;

import org.springframework.data.annotation.Id;
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
    private String userName;                                        // 系统用户名

    private boolean payed;                                          // 是否付费 true -> 付费, false -> 试用

    private String password;                                        // 密码

    private String companyName;                                     // 公司名

    private String contactName;                                     // 联系人

    private String telephone;                                       // 办公电话

    private String mobilePhone;                                     // 移动电话

    private String address;                                         // 通讯地址

    private Integer state;                                          // 审核状态: 1审核通过, 0审核未通过

    private Integer access;                                         // 1.admin, 2.user

    private String email;                                           // 邮箱

    private long ctime;                                             // 注册时间

    @Field("modules")
    private List<SystemUserModuleEntity> systemUserModules;

    @Field(value = "acstate")
    private Integer accountState;                                   // 系统帐号状态: 1.启用, 0.禁用


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

    public Integer getAccountState() {
        return accountState;
    }

    public void setAccountState(Integer accountState) {
        this.accountState = accountState;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public List<SystemUserModuleEntity> getSystemUserModules() {
        return systemUserModules;
    }

    public void setSystemUserModules(List<SystemUserModuleEntity> systemUserModules) {
        this.systemUserModules = systemUserModules;
    }
}
