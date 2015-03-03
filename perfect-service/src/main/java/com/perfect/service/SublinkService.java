package com.perfect.service;

import com.perfect.dto.creative.SublinkDTO;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2015/2/25.
 */
public interface SublinkService {
    String customSave(SublinkDTO dto);
    PagerInfo findByParams(Map<String,Object> maps,int nowPage,int pageSize);
    PagerInfo findByPagerInfo(Long l,int nowPage,int pageSize);
    PagerInfo findByPagerInfoForLongs(List<Long> ls,int nowPage,int pageSize);
    PagerInfo findByPagerInfo(Map<String,Object> map,int nowPage,int pageSize);

}
