package com.perfect.entity.sys;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by yousheng on 15/12/17.
 */
@Document(collection = "sys_roles")
public class SystemRoleEntity {

    @Id
    private String id;

    private String name;

    private String title;

    private boolean superAdmin;

    @Indexed(unique = true)
    private String loginName;

    private String password;

    private long ctime;

    private String contact;

    public String getId() {
        return id;
    }

    public SystemRoleEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SystemRoleEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SystemRoleEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public SystemRoleEntity setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
        return this;
    }

    public String isLoginName() {
        return loginName;
    }

    public SystemRoleEntity setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SystemRoleEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public long getCtime() {
        return ctime;
    }

    public SystemRoleEntity setCtime(long ctime) {
        this.ctime = ctime;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public SystemRoleEntity setContact(String contact) {
        this.contact = contact;
        return this;
    }
}
