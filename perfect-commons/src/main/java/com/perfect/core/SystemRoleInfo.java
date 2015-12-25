package com.perfect.core;

/**
 * Created by yousheng on 15/12/16.
 */
public class SystemRoleInfo {
    //    private String ip;
    private String roleId;

    private String roleName;

    private boolean isSuper;

//    public String getIp() {
//        return ip;
//    }
//
//    public SystemUserInfo setIp(String ip) {
//        this.ip = ip;
//        return this;
//    }

    public String getRoleName() {
        return roleName;
    }

    public SystemRoleInfo setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    @Override
    public String toString() {
        return "SystemUserInfo{" +
                "user='" + roleName + '\'' +
                ", isSuper=" + isSuper +
                '}';
    }

    public boolean isSuper() {
        return isSuper;
    }

    public SystemRoleInfo setIsSuper(boolean isSuper) {
        this.isSuper = isSuper;
        return this;
    }

    public String getRoleId() {
        return roleId;
    }

    public SystemRoleInfo setRoleId(String roleId) {
        this.roleId = roleId;
        return this;
    }
}
