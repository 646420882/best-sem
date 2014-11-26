package com.perfect.dto;

import com.perfect.dto.baidu.BaiduAccountInfoDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by baizz on 2014-11-26.
 */
public class SystemUserDTO implements Serializable {

    private String id;

    private String userName;

    private String password;

    private String companyName;

    private Integer state;      //审核状态

    private Integer access;     //1.admin; 2.user

    private byte[] img;

    private String email;

    private List<BaiduAccountInfoDTO> baiduAccountInfoDTOs;

    public SystemUserDTO() {
    }

    public SystemUserDTO(String userName, String password,
                         List<BaiduAccountInfoDTO> baiduAccountInfoDTOs) {
        this.userName = userName;
        this.password = password;
        this.baiduAccountInfoDTOs = baiduAccountInfoDTOs;
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

    public List<BaiduAccountInfoDTO> getBaiduAccountInfoDTOs() {
        return baiduAccountInfoDTOs;
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

    public void setBaiduAccountInfoDTOs(List<BaiduAccountInfoDTO> baiduAccountInfoDTOs) {
        this.baiduAccountInfoDTOs = baiduAccountInfoDTOs;
    }

    public void addBaiduAccountInfo(BaiduAccountInfoDTO baiduAccountInfoDTO) {
        if (baiduAccountInfoDTOs == null) {
            baiduAccountInfoDTOs = new ArrayList<>();
        }
        baiduAccountInfoDTOs.add(baiduAccountInfoDTO);
    }

    @Override
    public String toString() {
        return "SystemUserEntity{" +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", companyName='" + companyName + '\'' +
                ", state=" + state +
                ", access=" + access +
                ", img=" + Arrays.toString(img) +
                ", email='" + email + '\'' +
                ", baiduAccountInfoDTOs=" + baiduAccountInfoDTOs +
                '}';
    }
}
