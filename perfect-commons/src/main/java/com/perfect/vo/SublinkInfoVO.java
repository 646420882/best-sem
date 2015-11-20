package com.perfect.vo;

/**
 * Created by XiaoWei on 2015/2/28.
 */
public class SublinkInfoVO {
    private String description;
    private String destinationUrl;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestinationUrl() {
        return destinationUrl;
    }

    public void setDestinationUrl(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }

    @Override
    public String toString() {
        return "SublinkInfoVO{" +
                "description='" + description + '\'' +
                ", destinationUrl='" + destinationUrl + '\'' +
                '}';
    }
}
