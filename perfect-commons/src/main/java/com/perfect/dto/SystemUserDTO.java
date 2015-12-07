package com.perfect.dto;

import com.perfect.dto.baidu.BaiduAccountInfoDTO;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by baizz on 2014-11-26.
 *
 * @see com.perfect.account.SystemUserInfoVO
 * @deprecated
 */
public class SystemUserDTO extends BaseDTO implements Serializable {

    private String userName;

    private String password;

    private String companyName;

    private Integer state;      //审核状态

    private Integer access;     //1.admin; 2.user

    private byte[] img;

    private String email;

    private List<BaiduAccountInfoDTO> baiduAccounts;

    private Integer accountState;

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

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
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


    @Override
    public String toString() {
        return "SystemUserDTO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", companyName='" + companyName + '\'' +
                ", state=" + state +
                ", access=" + access +
                ", img=" + Arrays.toString(img) +
                ", email='" + email + '\'' +
                ", baiduAccounts=" + baiduAccounts +
                ", accountState=" + accountState +
                '}';
    }
}
