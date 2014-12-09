package com.perfect.service.impl;

import com.perfect.dao.sys.CrawlWordDAO;
import com.perfect.dto.CrawlWordDTO;
import com.perfect.service.CrawlWordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by baizz on 2014-12-4.
 * 2014-12-4 refactor
 */
@Service("crawlWordService")
public class CrawlWordServiceImpl implements CrawlWordService {

    @Resource
    private CrawlWordDAO crawlWordDAO;

    @Override
    public void save(Iterable<CrawlWordDTO> crawlWordDTOs) {
        crawlWordDAO.save(crawlWordDTOs);
    }
}
