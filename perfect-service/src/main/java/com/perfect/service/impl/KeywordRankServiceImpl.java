package com.perfect.service.impl;

import com.perfect.dao.keyword.KeywordRankDAO;
import com.perfect.dto.keyword.KeywordRankDTO;
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
    public KeywordRankDTO findRankByKeywordId(String id) {
        return keywordRankDAO.findByKeywordId(id);
    }

    @Override
    public void updateRanks(Collection<KeywordRankDTO> values) {
        List<KeywordRankDTO> newList = new ArrayList<>(values);

        Iterator<KeywordRankDTO> iterator = newList.iterator();
        while (iterator.hasNext()) {
            KeywordRankDTO entity = iterator.next();
            KeywordRankDTO keywordRankEntity = keywordRankDAO.findByKeywordId(entity.getKwid());

            if (keywordRankEntity != null) {
                String id = keywordRankEntity.getId();

                BeanUtils.copyProperties(entity, keywordRankEntity);
                keywordRankEntity.setId(id);
                keywordRankDAO.save(keywordRankEntity);
                iterator.remove();
            }
        }

        if (!newList.isEmpty()) {
            keywordRankDAO.save(newList);
        }
    }
}
