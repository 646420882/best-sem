package com.perfect.dto;

import java.util.List;

/**
 * Created by john on 2014/8/20.
 */
public class CampaignTreeDTO {

    private CampaignDTO rootNode;

    private List<AdgroupDTO> childNode;

    public CampaignTreeDTO(CampaignDTO rootNode, List<AdgroupDTO> childNode) {
        this.rootNode = rootNode;
        this.childNode = childNode;
    }

    public CampaignTreeDTO() {
    }

    public CampaignDTO getRootNode() {
        return rootNode;
    }

    public void setRootNode(CampaignDTO rootNode) {
        this.rootNode = rootNode;
    }

    public List<AdgroupDTO> getChildNode() {
        return childNode;
    }

    public void setChildNode(List<AdgroupDTO> childNode) {
        this.childNode = childNode;
    }
}
