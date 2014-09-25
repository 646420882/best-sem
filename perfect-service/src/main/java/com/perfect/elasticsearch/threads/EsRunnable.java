package com.perfect.elasticsearch.threads;

import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.dto.CreativeDTO;
import com.perfect.entity.CreativeSourceEntity;
import com.perfect.entity.MD5;
import com.perfect.service.CreativeSourceService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
public class EsRunnable implements Runnable {

    private List<CreativeDTO> list;
    private Integer region;
    private String keyword;

    @Override
    public void run() {

        CreativeSourceService creativeSourceService = (CreativeSourceService) ApplicationContextHelper.getBeanByName("creativeSourceService");
        List<CreativeSourceEntity> resultList = new ArrayList<>();

        for (CreativeDTO creativeDTO : list) {
            CreativeSourceEntity creativeSourceEntity = new CreativeSourceEntity();
            String source = creativeDTO.getTitle() + creativeDTO.getDescription() + creativeDTO.getUrl();

            MD5.Builder md5 = new MD5.Builder();
            md5.password(source);
            md5.salt("hello,salt");

            String code = md5.build().getMD5();

            boolean exists = creativeSourceService.exits(code);
            if (!exists) {
                creativeSourceEntity.setId(code);
                creativeSourceEntity.setTitle(creativeDTO.getTitle());
                creativeSourceEntity.setBody(creativeDTO.getDescription());
                creativeSourceEntity.setHost(creativeDTO.getUrl());
                creativeSourceEntity.setRegion(region);
                creativeSourceEntity.setHtml(creativeDTO.getDescSource());
                creativeSourceEntity.setKeyword(keyword);
                resultList.add(creativeSourceEntity);
            }
        }

        if (resultList.isEmpty())
            return;
        creativeSourceService.save(resultList);
    }


    public void setList(List<CreativeDTO> list) {
        this.list = list;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public Integer getRegion() {
        return region;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
