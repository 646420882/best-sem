package com.perfect.vo;

import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordInfoDTO;

import java.util.List;

/**
 * @author xiaowei
 * @title 用于关键词检测返回结果集
 * @package com.perfect.vo
 * @description
 * @update 2015年11月03日. 下午5:16
 */
public class ValidateCreativeVO {
    private List<CreativeDTO> safeCreativeDTOList;
    private List<CreativeDTO> dbExistCreativeDTOList;
    private Integer endGetCount = 0;//错误的创意数量，错误原因为：重复

    public List<CreativeDTO> getSafeCreativeDTOList() {
        return safeCreativeDTOList;
    }

    public void setSafeCreativeDTOList(List<CreativeDTO> safeCreativeDTOList) {
        this.safeCreativeDTOList = safeCreativeDTOList;
    }

    public List<CreativeDTO> getDbExistCreativeDTOList() {
        return dbExistCreativeDTOList;
    }

    public void setDbExistCreativeDTOList(List<CreativeDTO> dbExistCreativeDTOList) {
        this.dbExistCreativeDTOList = dbExistCreativeDTOList;
    }

    public Integer getEndGetCount() {
        return endGetCount;
    }

    public void setEndGetCount(Integer endGetCount) {
        this.endGetCount = endGetCount;
    }
}
