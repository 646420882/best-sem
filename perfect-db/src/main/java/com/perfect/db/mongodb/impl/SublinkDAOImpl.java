package com.perfect.db.mongodb.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.creative.SublinkDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.creative.SublinkDTO;
import com.perfect.entity.creative.MobileSublinkEntity;
import com.perfect.entity.creative.MobileSublinkInfoEntity;
import com.perfect.entity.creative.SublinkEntity;
import com.perfect.entity.creative.SublinkInfoEntity;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.paging.PagerInfo;
import com.perfect.vo.SublinkInfoVo;
import com.perfect.vo.SublinkVo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2015/2/25.
 */
@Repository("sublinkDAO")
public class SublinkDAOImpl extends AbstractSysBaseDAOImpl<SublinkDTO, Long> implements SublinkDAO {
    @Override
    public Class<SublinkEntity> getEntityClass() {
        return SublinkEntity.class;
    }

    @Override
    public Class<SublinkDTO> getDTOClass() {
        return SublinkDTO.class;
    }

    @Override
    public String customSave(SublinkDTO sublinkDTO) {
        SublinkEntity entity = ObjectUtils.convert(sublinkDTO, SublinkEntity.class);
        getMongoTemplate().save(entity);
        return sublinkDTO.getId();
    }

    @Override
    public SublinkDTO findByAdgroupLongId(Long adgroupId) {
        SublinkEntity entity = getMongoTemplate().findOne(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(ADGROUP_ID).is(adgroupId)), SublinkEntity.class);
        SublinkDTO sublinkDTO = ObjectUtils.convert(entity, SublinkDTO.class);
        return sublinkDTO;
    }

    @Override
    public SublinkDTO findByAdgroupObjId(String objectId) {
        SublinkEntity entity = getMongoTemplate().findOne(new Query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId()).and(OBJ_ADGROUP_ID).is(objectId)), SublinkEntity.class);
        SublinkDTO sublinkDTO = ObjectUtils.convert(entity, SublinkDTO.class);
        return sublinkDTO;
    }

    @Override
    public PagerInfo findByParams(Map<String, Object> maps, int nowPage, int pageSize) {
        Query q = new Query();
        Criteria c = new Criteria();
        if (maps.size() > 0 || maps != null) {
            for (Map.Entry<String, Object> cri : maps.entrySet()) {
                c.and(cri.getKey()).is(cri.getValue());
            }
            q.addCriteria(c);
        }
        Integer totalCount = getTotalCount(q, SublinkEntity.class)+getTotalCount(q,MobileSublinkEntity.class);
        PagerInfo p = new PagerInfo(nowPage, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        List<SublinkEntity> sublinkEntityList = getMongoTemplate().find(q, SublinkEntity.class);
        List<MobileSublinkEntity> mobileSublinkEntityList = getMongoTemplate().find(q, MobileSublinkEntity.class);

        p.setList(getSublinkVos(sublinkEntityList,mobileSublinkEntityList));
        return null;
    }

    @Override
    public PagerInfo findByPagerInfo(Long l, int nowPage, int pageSize) {
        Query q = new Query(Criteria.where(ADGROUP_ID).in(l));
        Integer totalCount = getTotalCount(q, SublinkEntity.class)+getTotalCount(q,MobileSublinkEntity.class);
        PagerInfo p = new PagerInfo(nowPage, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        List<SublinkEntity> sublinkEntityList = getMongoTemplate().find(q, SublinkEntity.class);
        List<MobileSublinkEntity> mobileSublinkEntityList = getMongoTemplate().find(q, MobileSublinkEntity.class);
        p.setList(getSublinkVos(sublinkEntityList,mobileSublinkEntityList));
        return p;
    }

    @Override
    public PagerInfo findByPagerInfoForLongs(List<Long> ls, int nowPage, int pageSize) {
        Query q = new Query(Criteria.where(ADGROUP_ID).in(ls));
        Integer totalCount = getTotalCount(q, SublinkEntity.class)+getTotalCount(q,MobileSublinkEntity.class);
        PagerInfo p = new PagerInfo(nowPage, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        List<SublinkEntity> sublinkEntityList = getMongoTemplate().find(q, SublinkEntity.class);
        List<MobileSublinkEntity> mobileSublinkEntityList = getMongoTemplate().find(q, MobileSublinkEntity.class);
        p.setList(getSublinkVos(sublinkEntityList,mobileSublinkEntityList));
        return p;
    }

    @Override
    public PagerInfo findByPagerInfo(Map<String, Object> map, int nowPage, int pageSize) {
        Query q = new Query();
        Criteria c = new Criteria();
        if (map.size() > 0 || map != null) {
            for (Map.Entry<String, Object> cri : map.entrySet()) {
                c.and(cri.getKey()).is(cri.getValue());
            }
            q.addCriteria(c);
        }
        Integer totalCount = getTotalCount(q, SublinkEntity.class)+getTotalCount(q,MobileSublinkEntity.class);
        PagerInfo p = new PagerInfo(nowPage, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        List<SublinkEntity> sublinkEntityList = getMongoTemplate().find(q, SublinkEntity.class);
        List<MobileSublinkEntity> mobileSublinkEntityList = getMongoTemplate().find(q, MobileSublinkEntity.class);
        p.setList(getSublinkVos(sublinkEntityList,mobileSublinkEntityList));
        return null;
    }

    private List<SublinkVo> getSublinkVos(List<SublinkEntity> sublinkEntityList,List<MobileSublinkEntity> mobileSublinkEntityList){
        List<SublinkVo> sublinkVoList = new ArrayList<>();
        sublinkEntityList.stream().forEach(s -> {
            SublinkVo sublinkVo = new SublinkVo();
            sublinkVo.setAccountId(s.getAccountId());
            sublinkVo.setPause(s.getPause());
            sublinkVo.setAdgroupId(s.getAdgroupId());
            sublinkVo.setAdgroupObjId(s.getAdgroupObjId());
            sublinkVo.setId(s.getId());
            sublinkVo.setStatus(s.getStatus());
            sublinkVo.setSublinkId(s.getSublinkId());
            List<SublinkInfoEntity> sublinkInfoEntities = s.getSublinkInfos();
            String sublinkInfoVos="";
            for (SublinkInfoEntity sl:sublinkInfoEntities){
                sublinkInfoVos+=sl.getDescription()+","+sl.getDestinationUrl()+"|";
            }
//            sublinkInfoEntities.stream().forEach(j -> {
//                SublinkInfoVo sublinkInfoVo=new SublinkInfoVo();
//                sublinkInfoVo.setDescription(j.getDescription());
//                sublinkInfoVo.setDestinationUrl(j.getDestinationUrl());
//                sublinkInfoVos.add(sublinkInfoVo);
//            });
            sublinkVo.setSublinkInfoVos(sublinkInfoVos);
            sublinkVo.setmType(0);
            sublinkVoList.add(sublinkVo);
        });
        mobileSublinkEntityList.stream().forEach(s->{
            SublinkVo sublinkVo = new SublinkVo();
            sublinkVo.setAccountId(s.getAccountId());
            sublinkVo.setPause(s.getPause());
            sublinkVo.setAdgroupId(s.getAdgroupId());
            sublinkVo.setAdgroupObjId(s.getAdgroupObjId());
            sublinkVo.setId(s.getId());
            sublinkVo.setStatus(s.getStatus());
            sublinkVo.setSublinkId(s.getSublinkId());
            List<MobileSublinkInfoEntity> mobileSublinkInfoEntities=s.getMobileSublinkInfos();
            String sublinkInfoVos="";
            for (MobileSublinkInfoEntity sl:mobileSublinkInfoEntities){
                sublinkInfoVos+=sl.getDescription()+","+sl.getDestinationUrl()+"|";
            }
//            List<SublinkInfoVo> sublinkInfoVos=new ArrayList<>();
//            mobileSublinkInfoEntities.stream().forEach(j->{
//                SublinkInfoVo sublinkInfoVo=new SublinkInfoVo();
//                sublinkInfoVo.setDescription(j.getDescription());
//                sublinkInfoVo.setDestinationUrl(j.getDestinationUrl());
//                sublinkInfoVos.add(sublinkInfoVo);
//            });
            sublinkVo.setSublinkInfoVos(sublinkInfoVos);
            sublinkVo.setmType(1);
            sublinkVoList.add(sublinkVo);
        });
        return sublinkVoList;
    }

    private Integer getTotalCount(Query q, Class<?> cls) {
        return (int) getMongoTemplate().count(q, cls);
    }
}