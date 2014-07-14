package com.perfect.autosdk.core;

public class AuthHeader {

    private String username;
    private String password;
    private String target;
    private String token;
    private String action = "API-SDK";
    private String accessToken;
    private Integer account_type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Gets the value of the accessToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public final String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the value of the accessToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public final void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return the account_type
     */
    public final Integer getAccount_type() {
        return account_type;
    }

    /**
     * @param account_type the account_type to set
     */
    public final void setAccount_type(Integer account_type) {
        this.account_type = account_type;
    }

    @Override
    public String toString() {
        return "AuthHeader [username=" + username + ", password=" + password + ", target=" + target + ", token="
                + token + ", action=" + action + ", accessToken=" + accessToken + ", account_type=" + account_type
                + "]";
    }

}
