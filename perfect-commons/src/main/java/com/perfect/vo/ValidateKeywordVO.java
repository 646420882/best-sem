package com.perfect.vo;

import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.keyword.KeywordInfoDTO;

import java.util.List;

/**
 * @author xiaowei
 * @title 用于关键词检测返回结果集
 * @package com.perfect.vo
 * @description
 * @update 2015年11月03日. 下午5:16
 */
public class ValidateKeywordVO {
    private List<KeywordInfoDTO> safeKeywordList;
    private List<KeywordInfoDTO> dbExistKeywordList;
    private Integer endGetCount = 0;//错误的关键词数量，错误原因为：重复

    public List<KeywordInfoDTO> getSafeKeywordList() {
        return safeKeywordList;
    }

    public void setSafeKeywordList(List<KeywordInfoDTO> safeKeywordList) {
        this.safeKeywordList = safeKeywordList;
    }

    public List<KeywordInfoDTO> getDbExistKeywordList() {
        return dbExistKeywordList;
    }

    public void setDbExistKeywordList(List<KeywordInfoDTO> dbExistKeywordList) {
        this.dbExistKeywordList = dbExistKeywordList;
    }

    public Integer getEndGetCount() {
        return endGetCount;
    }

    public void setEndGetCount(Integer endGetCount) {
        this.endGetCount = endGetCount;
    }
}
