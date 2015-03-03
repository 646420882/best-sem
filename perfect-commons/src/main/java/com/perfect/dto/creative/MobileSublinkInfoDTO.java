package com.perfect.dto.creative;

/**
 * Created by XiaoWei on 2015/2/27.
 */
public class MobileSublinkInfoDTO {
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
        return "MobileSublinkInfoDTO{" +
                "description='" + description + '\'' +
                ", destinationUrl='" + destinationUrl + '\'' +
                '}';
    }
}
