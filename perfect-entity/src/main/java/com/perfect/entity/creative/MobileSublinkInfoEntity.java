package com.perfect.entity.creative;

/**
 * Created by XiaoWei on 2015/2/27.
 */
public class MobileSublinkInfoEntity {
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
        return "MobileSublinkInfoEntity{" +
                "description='" + description + '\'' +
                ", destinationUrl='" + destinationUrl + '\'' +
                '}';
    }
}
