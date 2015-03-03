package com.perfect.dao.creative;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.creative.SublinkDTO;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2015/2/25.
 */
public interface SublinkDAO extends HeyCrudRepository<SublinkDTO,Long> {
    String customSave(SublinkDTO sublinkDTO);
    SublinkDTO findByAdgroupLongId(Long adgroupId);
    SublinkDTO findByAdgroupObjId(String objectId);
    PagerInfo findByParams(Map<String, Object> maps,int nowPage,int pageSize);
    PagerInfo findByPagerInfo(Long l, int nowPage, int pageSize);
    PagerInfo findByPagerInfoForLongs(List<Long> ls, int nowPage, int pageSize);
    PagerInfo findByPagerInfo(Map<String,Object> map,int nowPage,int pageSize);

}
