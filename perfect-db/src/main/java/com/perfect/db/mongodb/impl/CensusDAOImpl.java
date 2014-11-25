package com.perfect.db.mongodb.impl;

import com.perfect.dao.CensusDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.commons.constants.ConstantsDTO;
import com.perfect.commons.constants.ConstantsDTO.CensusStatus;
import com.perfect.entity.CensusEntity;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dao.utils.Pager;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.perfect.commons.constants.MongoEntityConstants.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by XiaoWei on 2014/11/11.
 */
@Component
public class CensusDAOImpl extends AbstractUserBaseDAOImpl<CensusEntity, Long> implements CensusDAO {
    @Override
    public Class<CensusEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public CensusEntity saveParams(CensusEntity censusEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        if (mongoTemplate.exists(new Query(Criteria.where("uid").is(censusEntity.getUuid())), CensusEntity.class)) {
            censusEntity.setUserType(0);
        } else {
            censusEntity.setUserType(1);
        }
        mongoTemplate.save(censusEntity, SYS_CENSUS);
        return censusEntity;
    }

    @Override
    public ConstantsDTO getTodayTotal(String url) {
        return getTotalConstants(CensusStatus.TO_DAY, url);
    }

    @Override
    public ConstantsDTO getLastDayTotal(String url) {
        return getTotalConstants(CensusStatus.LAST_DAY, url);
    }

    @Override
    public ConstantsDTO getLastWeekTotal(String url) {
        return getTotalConstants(CensusStatus.LAST_WEEK, url);
    }

    @Override
    public ConstantsDTO getLastMonthTotal(String url) {
        return getTotalConstants(CensusStatus.LAST_MONTH, url);
    }

    private ConstantsDTO getTotalConstants(CensusStatus status, String url) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        Query q = new Query();
        Criteria c = getStaticCriteria(status, url);
        q.addCriteria(c);
        Aggregation ipCountAgg = Aggregation.newAggregation(
                match(c),
                project("ip"),
                group("ip").count().as("count")
        );
        AggregationResults<CensusIpVO> ipCountResult = mongoTemplate.aggregate(ipCountAgg, SYS_CENSUS, CensusIpVO.class);
        List<CensusIpVO> ipCountList = new ArrayList<>(ipCountResult.getMappedResults());

        Aggregation uidCountAgg = Aggregation.newAggregation(
                match(c),
                project("uid"),
                group("uid").count().as("count")
        );
        AggregationResults<CensusUidVO> uidCountResult = mongoTemplate.aggregate(uidCountAgg, SYS_CENSUS, CensusUidVO.class);
        List<CensusUidVO> uidCountList = new ArrayList<>(uidCountResult.getMappedResults());

        int totalPv = (int) mongoTemplate.count(q, SYS_CENSUS);
        int totalUv = uidCountList.size();
        int totalIp = ipCountList.size();
        ConstantsDTO constantsDTO = new ConstantsDTO();
        constantsDTO.setCensusUrl(url);
        constantsDTO.setTotalCount(totalPv);
        constantsDTO.setTotalPv(totalPv);
        constantsDTO.setTotalUv(totalUv);
        constantsDTO.setTotalIp(totalIp);

        return constantsDTO;
    }

    private Criteria getStaticCriteria(CensusStatus status, String url) {
        Criteria c = null;
        switch (status) {
            case TO_DAY:
                if (url == null) {
                    c = new Criteria("dat").gte(getTodayStartDate()).lte(getTodayEndDate());
                } else {
                    c = new Criteria("dat").gte(getTodayStartDate()).lte(getTodayEndDate()).and("lp").is(url);
                }
                break;
            case LAST_DAY:
                if (url == null) {
                    c = new Criteria("dat").gte(getLastDayStartDate()).lte(getLastDayEndDate());
                } else {
                    c = new Criteria("dat").gte(getLastDayStartDate()).lte(getLastDayEndDate()).and("lp").is(url);
                }
                break;
            case LAST_WEEK:
                if (url == null) {
                    c = new Criteria("dat").gte(getLastWeekDate()).lte(getTodayEndDate());
                } else {
                    c = new Criteria("dat").gte(getLastWeekDate()).lte(getTodayEndDate()).and("lp").is(url);
                }
                break;
            case LAST_MONTH:
                if (url == null) {
                    c = new Criteria("dat").gte(getLastMonthDate()).lte(getTodayEndDate());
                } else {
                    c = new Criteria("dat").gte(getLastMonthDate()).lte(getTodayEndDate()).and("lp").is(url);
                }
                break;
        }
        return c;
    }

    private Date getTodayStartDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    private Date getTodayEndDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    private Date getLastDayStartDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    private Date getLastDayEndDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    private Date getLastWeekDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_WEEK_IN_MONTH, -1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    private Date getLastMonthDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public class CensusIpVO {
        @Id
        private String ip;
        private int count;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "CensusIpVO{" +
                    "ip='" + ip + '\'' +
                    ", count=" + count +
                    '}';
        }
    }

    public class CensusUidVO {
        @Id
        private String uid;
        private int count;

        public String getUuid() {
            return uid;
        }

        public void setUuid(String uid) {
            this.uid = uid;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "CensusUidVO{" +
                    "uid='" + uid + '\'' +
                    ", count=" + count +
                    '}';
        }
    }

}
