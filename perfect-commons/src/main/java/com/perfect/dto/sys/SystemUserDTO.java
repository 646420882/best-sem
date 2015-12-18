package com.perfect.dto.sys;

import com.perfect.dto.BaseDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by baizz on 2014-11-26.
 */
public class SystemUserDTO extends BaseDTO implements Serializable {

    private String userName;

    private String password;

    private boolean payed;

    private String companyName;

    private Integer state;                                  // 审核状态

    private Integer access;                                 // 1.admin, 2.user

    private String email;

    private List<SystemUserModuleDTO> moduleDTOList;

    private List<BaiduAccountInfoDTO> baiduAccounts;

    private Integer accountState;

    private long startTime;

    private long endTime;

    private String contactName;                             // 联系人

    private String telephone;

    private String mobilephone;

    private String address;

    private long ctime; //注册时间

    private String displayCtime;

    public SystemUserDTO() {
    }

    public SystemUserDTO(String userName, String password,
                         List<BaiduAccountInfoDTO> baiduAccounts) {
        this.userName = userName;
        this.password = password;
        this.baiduAccounts = baiduAccounts;
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

    public List<BaiduAccountInfoDTO> getBaiduAccounts() {
        return baiduAccounts;
    }

    public void setBaiduAccounts(List<BaiduAccountInfoDTO> baiduAccounts) {
        this.baiduAccounts = baiduAccounts;
    }

    public Integer getAccountState() {
        return accountState;
    }

    public void setAccountState(Integer accountState) {
        this.accountState = accountState;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
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

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
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

    public String getDisplayCtime() {
        return displayCtime;
    }

    public void setDisplayCtime(String displayCtime) {
        this.displayCtime = displayCtime;
    }

    public List<SystemUserModuleDTO> getModuleDTOList() {
        return moduleDTOList;
    }

    public void setModuleDTOList(List<SystemUserModuleDTO> moduleDTOList) {
        this.moduleDTOList = moduleDTOList;
    }
}
