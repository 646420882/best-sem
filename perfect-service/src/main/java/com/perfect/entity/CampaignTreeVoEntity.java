package com.perfect.entity;

import java.util.List;

/**
 * Created by john on 2014/8/20.
 */
public class CampaignTreeVoEntity {

    private CampaignEntity rootNode;

    private List<AdgroupEntity> childNode;

    public CampaignTreeVoEntity(CampaignEntity rootNode, List<AdgroupEntity> childNode) {
        this.rootNode = rootNode;
        this.childNode = childNode;
    }

    public CampaignTreeVoEntity() {
    }

    public CampaignEntity getRootNode() {
        return rootNode;
    }

    public void setRootNode(CampaignEntity rootNode) {
        this.rootNode = rootNode;
    }

    public List<AdgroupEntity> getChildNode() {
        return childNode;
    }

    public void setChildNode(List<AdgroupEntity> childNode) {
        this.childNode = childNode;
    }
}
