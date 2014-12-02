package com.perfect.dto.creative;

public class SublinkInfoDTO {

    private String description;
    private String destinationUrl;

    public void setDescription(String aDescription) {
        description = aDescription;
    }

    public void setDestinationUrl(String aDestinationUrl) {
        destinationUrl = aDestinationUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getDestinationUrl() {
        return destinationUrl;
    }

    public void delete() {
    }


    public String toString() {
        String outputString = "";
        return super.toString() + "[" +
                "description" + ":" + getDescription() + "," +
                "destinationUrl" + ":" + getDestinationUrl() + "]"
                + outputString;
    }
}