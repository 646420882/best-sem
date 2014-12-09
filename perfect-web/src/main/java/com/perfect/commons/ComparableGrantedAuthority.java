package com.perfect.commons;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

/**
 * Created by vbzer_000 on 2014/10/14.
 */
public class ComparableGrantedAuthority implements Comparable<ComparableGrantedAuthority>, GrantedAuthority {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final String role;

    public ComparableGrantedAuthority(String role) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    public String getAuthority() {
        return role;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof ComparableGrantedAuthority) {
            return role.equals(((ComparableGrantedAuthority) obj).role);
        }

        return false;
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }

    @Override
    public int compareTo(ComparableGrantedAuthority o) {
        if (o.getAuthority() == null) {
            return -1;
        }

        if (this.getAuthority() == null) {
            return 1;
        }

        return this.getAuthority().compareTo(o.getAuthority());
    }
}
