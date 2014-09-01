package com.perfect.service.impl;

import com.perfect.dao.KeywordRankDAO;
import com.perfect.entity.bidding.KeywordRankEntity;
import com.perfect.service.KeywordRankService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/8/31.
 */
@Component
public class KeywordRankServiceImpl implements KeywordRankService {

    @Resource
    private KeywordRankDAO keywordRankDAO;

    @Override
    public KeywordRankEntity findRankByKeywordId(Long id) {
        return keywordRankDAO.findByKeywordId(id);
    }

    @Override
    public void updateRanks(Collection<KeywordRankEntity> values) {
        List<KeywordRankEntity> newList = new ArrayList<>(values);

        Iterator<KeywordRankEntity> iterator = newList.iterator();
        while (iterator.hasNext()) {
            KeywordRankEntity entity = iterator.next();
            KeywordRankEntity keywordRankEntity = keywordRankDAO.findByKeywordId(entity.getKwid());

            if (keywordRankEntity != null) {
                String id = keywordRankEntity.getId();

                BeanUtils.copyProperties(entity, keywordRankEntity);
                keywordRankEntity.setId(id);
                keywordRankDAO.save(keywordRankEntity);
                iterator.remove();
            }
        }

        if (!newList.isEmpty()) {
            keywordRankDAO.insertAll(newList);
        }
    }
}
