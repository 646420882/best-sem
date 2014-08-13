package com.perfect.utils;

import com.perfect.autosdk.sms.v3.AdgroupType;
import com.perfect.autosdk.sms.v3.CampaignType;
import com.perfect.autosdk.sms.v3.KeywordType;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.entity.KeywordEntity;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
public class EntityConvertUtils {


    public static List<CampaignEntity> convertToCamEntity(List<CampaignType> list) {
        List<CampaignEntity> entities = new ArrayList<>(list.size());

        for (CampaignType campaignType : list) {
            entities.add(convertToEntity(campaignType));
        }

        return entities;
    }


    public static CampaignEntity convertToEntity(CampaignType type) {
        CampaignEntity campaignEntity = new CampaignEntity();

        BeanUtils.copyProperties(type, campaignEntity);

        return campaignEntity;
    }


    public static List<AdgroupEntity> convertToAdEntity(List<AdgroupType> list) {
        List<AdgroupEntity> entities = new ArrayList<>(list.size());

        for (AdgroupType adgroupType : list) {
            entities.add(convertToEntity(adgroupType));
        }

        return entities;
    }


    public static AdgroupEntity convertToEntity(AdgroupType type) {
        AdgroupEntity adgroupEntity = new AdgroupEntity();

        BeanUtils.copyProperties(type, adgroupEntity);

        return adgroupEntity;
    }

    public static List<KeywordEntity> convertToKwEntity(List<KeywordType> list) {
        List<KeywordEntity> entities = new ArrayList<>(list.size());

        for (KeywordType adgroupType : list) {
            entities.add(convertToEntity(adgroupType));
        }

        return entities;
    }


    public static KeywordEntity convertToEntity(KeywordType type) {
        KeywordEntity adgroupEntity = new KeywordEntity();

        BeanUtils.copyProperties(type, adgroupEntity);

        return adgroupEntity;
    }

    public static void main(String args[]) {
        CampaignType campaignType = new CampaignType();

        campaignType.setCampaignId(12313123l);


        System.out.println(convertToEntity(campaignType).getCampaignId());
    }
}
