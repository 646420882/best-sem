package com.perfect.utils;

import com.perfect.autosdk.sms.v3.AdgroupType;
import com.perfect.autosdk.sms.v3.CampaignType;
import com.perfect.autosdk.sms.v3.CreativeType;
import com.perfect.autosdk.sms.v3.KeywordType;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
public class EntityConvertUtils {


    public static List<CampaignDTO> convertToCamEntity(List<CampaignType> list) {
        List<CampaignDTO> entities = new ArrayList<>(list.size());

        for (CampaignType campaignType : list) {
            entities.add(convertToEntity(campaignType));
        }

        return entities;
    }


    public static CampaignDTO convertToEntity(CampaignType type) {
        CampaignDTO campaignEntity = new CampaignDTO();

        BeanUtils.copyProperties(type, campaignEntity);

        return campaignEntity;
    }


    public static List<AdgroupDTO> convertToAdEntity(List<AdgroupType> list) {
        List<AdgroupDTO> entities = new ArrayList<>(list.size());

        for (AdgroupType adgroupType : list) {
            entities.add(convertToEntity(adgroupType));
        }

        return entities;
    }


    public static AdgroupDTO convertToEntity(AdgroupType type) {
        AdgroupDTO adgroupEntity = new AdgroupDTO();

        BeanUtils.copyProperties(type, adgroupEntity);

        return adgroupEntity;
    }

    public static List<KeywordDTO> convertToKwEntity(List<KeywordType> list) {
        List<KeywordDTO> entities = new ArrayList<>(list.size());

        for (KeywordType adgroupType : list) {
            entities.add(convertToEntity(adgroupType));
        }

        return entities;
    }

    public static List<CreativeDTO> convertToCrEntity(List<CreativeType> list) {
        List<CreativeDTO> entities = new ArrayList<>(list.size());

        for (CreativeType creativeType : list) {
            entities.add(convertToEntity(creativeType));
        }

        return entities;
    }

    private static CreativeDTO convertToEntity(CreativeType creativeType) {
        CreativeDTO creativeEntity = new CreativeDTO();

        BeanUtils.copyProperties(creativeType, creativeEntity);

        return creativeEntity;
    }


    public static KeywordDTO convertToEntity(KeywordType type) {
        KeywordDTO keywordEntity = new KeywordDTO();

        BeanUtils.copyProperties(type, keywordEntity);

        if (type.getPrice() == null) {
            keywordEntity.setPrice(BigDecimal.ZERO);
        } else {
            keywordEntity.setPrice(BigDecimal.valueOf(type.getPrice()));
        }
        return keywordEntity;
    }

    public static void main(String args[]) {
        CampaignType campaignType = new CampaignType();

        campaignType.setCampaignId(12313123l);


        System.out.println(convertToEntity(campaignType).getCampaignId());
    }
}
