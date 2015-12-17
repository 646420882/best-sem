package com.perfect.param;

/**
 * Created by subdong on 15-12-17.
 */
public class RegisterParam {
    //公司名称
    private String companyname;
    //用户名
    private String username;
    //邮箱
    private String email;
    //密码
    private String password;
    //通信地址
    private String mailinAddress;
    //联系人
    private String contacts;
    //联系电话
    private String contactsPhone;
    //开通平台
    private String openPlatform;
    //百度凤巢账户
    private String phoenixNestUser;
    //百度凤巢密码
    private String phoenixNestPassword;
    //网站URL地址
    private String urlAddress;
    //用户类型   1 试用装户  2付费用户
    private String accountType;

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailinAddress() {
        return mailinAddress;
    }

    public void setMailinAddress(String mailinAddress) {
        this.mailinAddress = mailinAddress;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public String getOpenPlatform() {
        return openPlatform;
    }

    public void setOpenPlatform(String openPlatform) {
        this.openPlatform = openPlatform;
    }

    public String getPhoenixNestUser() {
        return phoenixNestUser;
    }

    public void setPhoenixNestUser(String phoenixNestUser) {
        this.phoenixNestUser = phoenixNestUser;
    }

    public String getPhoenixNestPassword() {
        return phoenixNestPassword;
    }

    public void setPhoenixNestPassword(String phoenixNestPassword) {
        this.phoenixNestPassword = phoenixNestPassword;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "RegisterParam{" +
                "companyname='" + companyname + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mailinAddress='" + mailinAddress + '\'' +
                ", contacts='" + contacts + '\'' +
                ", contactsPhone='" + contactsPhone + '\'' +
                ", openPlatform='" + openPlatform + '\'' +
                ", phoenixNestUser='" + phoenixNestUser + '\'' +
                ", phoenixNestPassword='" + phoenixNestPassword + '\'' +
                ", urlAddress='" + urlAddress + '\'' +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
