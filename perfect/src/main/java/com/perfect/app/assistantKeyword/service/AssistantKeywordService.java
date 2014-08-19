package com.perfect.app.assistantKeyword.service;

import com.perfect.dao.KeywordDAO;
import com.perfect.entity.KeywordEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by john on 2014/8/14.
 */
@Service
public class AssistantKeywordService {

    @Resource
    private KeywordDAO keywordDAO;


    public Iterable<KeywordEntity> getAllKeywordList(){
        return keywordDAO.findAll();
    }


    public void deleteKeywordById(Long[]  kwids) {
        List list = Arrays.asList(kwids);
        keywordDAO.deleteByIds(list);
    }
}
