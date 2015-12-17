package com.perfect.dto.sys;

import com.perfect.dto.BaseDTO;

/**
 * Created by yousheng on 15/12/17.
 */
public class SystemRoleDTO extends BaseDTO {

    private String name;

    private String title;

    private boolean superAdmin;

    private String loginName;

    private String password;

    private long ctime;

    private String contact;

    public String getName() {
        return name;
    }

    public SystemRoleDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SystemRoleDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public SystemRoleDTO setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
        return this;
    }

    public String getLoginName() {
        return loginName;
    }

    public SystemRoleDTO setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SystemRoleDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public long getCtime() {
        return ctime;
    }

    public SystemRoleDTO setCtime(long ctime) {
        this.ctime = ctime;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public SystemRoleDTO setContact(String contact) {
        this.contact = contact;
        return this;
    }
}
