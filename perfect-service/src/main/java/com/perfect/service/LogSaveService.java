package com.perfect.service;

import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.utils.OperationRecordModelBuilder;


/**
 * @author xiaowei
 * @title LogSaveService
 * @package com.perfect.service
 * @description
 * @update 2015年12月08日. 下午6:44
 */
public interface LogSaveService {

    void saveKeywordLog(KeywordDTO newKeywordDTO);

    void updateKeywordLog(KeywordDTO dbFindKeyWord, Object oldVal, Object newVal, String optObj);

    void deleteKeywordLog(KeywordDTO dbFindKeyWord);

    void reduceKeywordLog(KeywordDTO dbFindKeyWord);

    void moveKeywordLog(KeywordDTO dbFindKeyWord, Object oldVal, Object newVal);


    void getCamAdgroupInfoByLong(Long adgroupId, OperationRecordModelBuilder builder);


}
