package com.perfect.service.impl;

import com.google.common.collect.Lists;
import com.perfect.dao.sys.LexiconDAO;
import com.perfect.dto.keyword.LexiconDTO;
import com.perfect.service.LexiconService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by baizz on 2014-12-2.
 */
@Service("lexiconService")
public class LexiconServiceImpl implements LexiconService {

    @Resource
    private LexiconDAO lexiconDAO;

    @Override
    public void deleteLexiconByTrade(String trade, String category) {
        lexiconDAO.deleteLexiconByTrade(trade, category);
    }

    @Override
    public List<LexiconDTO> save(List<LexiconDTO> dtoList) {
        return Lists.newArrayList(lexiconDAO.save(dtoList));
    }
}
