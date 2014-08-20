package com.perfect.app.assistantKeyword.vo;

import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.CampaignEntity;

import java.util.List;

/**
 * Created by john on 2014/8/20.
 */
public class CampaignTree {

    private CampaignEntity rootNode;

    private List<AdgroupEntity> childNode;

    public CampaignTree(CampaignEntity rootNode, List<AdgroupEntity> childNode) {
        this.rootNode = rootNode;
        this.childNode = childNode;
    }

    public CampaignTree() {
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
