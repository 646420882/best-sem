package com.perfect.db.mongodb.impl;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.dao.CensusDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.count.CensusCfgDTO;
import com.perfect.dto.count.CensusDTO;
import com.perfect.dto.count.ConstantsDTO;
import com.perfect.dto.count.ConstantsDTO.CensusStatus;
import com.perfect.entity.CensusEntity;
import com.perfect.utils.paging.Pager;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

/**
 * Created by XiaoWei on 2014/11/11.
 */
@Component
public class CensusDAOImpl extends AbstractUserBaseDAOImpl<CensusDTO, Long> implements CensusDAO {


    @Override
    public CensusEntity saveParams(CensusEntity censusEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        if(mongoTemplate.exists(new Query(Criteria.where("uid").is(censusEntity.getUuid())),CensusEntity.class)){
            censusEntity.setUserType(0);
        }else{
            censusEntity.setUserType(1);
        }
        mongoTemplate.save(censusEntity, EntityConstants.SYS_CENSUS);
        return censusEntity;
    }

    @Override
    public Map<String, ConstantsDTO> getTodayTotal(String url) {
        ConstantsDTO toDayCensus=getTotalConstants(CensusStatus.TO_DAY,url);
        ConstantsDTO lastDayCensus=getTotalConstants(CensusStatus.LAST_DAY,url);
        ConstantsDTO lastWeekCensus=getTotalConstants(CensusStatus.LAST_WEEK,url);
        ConstantsDTO lastMonthCensus=getTotalConstants(CensusStatus.LAST_MONTH,url);
        Map<String,ConstantsDTO> returnMap=new HashMap<String,ConstantsDTO>();
        returnMap.put("t",toDayCensus);
        returnMap.put("ld",lastDayCensus);
        returnMap.put("lw",lastWeekCensus);
        returnMap.put("lm",lastMonthCensus);
        return returnMap;
    }

    @Override
    public List<ConstantsDTO> getVisitCustom(Map<String, Object> q) {
        MongoTemplate mongoTemplate=BaseMongoTemplate.getSysMongo();
        return null;
    }

    @Override
    public int saveConfig(CensusCfgDTO censusCfgDTO) {
        MongoTemplate mongoTemplate=BaseMongoTemplate.getSysMongo();
        Query q=new Query(Criteria.where("url").is(censusCfgDTO.getUrl()).and("ip").is(censusCfgDTO.getIp()));
        if(!mongoTemplate.exists(q,EntityConstants.SYS_CENSUS_CONFIG)){
            CensusCfgEntity censusCfgEntity=new CensusCfgEntity();
            BeanUtils.copyProperties(censusCfgDTO,censusCfgEntity);
            mongoTemplate.save(censusCfgEntity, MongoEntityConstants.SYS_CENSUS_CONFIG);
            return 1;
        }
        return 0;
    }

    @Override
    public List<CensusCfgDTO> getCfgList(String ip) {
        MongoTemplate mongoTemplate=BaseMongoTemplate.getSysMongo();
        List<CensusCfgEntity> list=mongoTemplate.find(new Query(Criteria.where("ip").is(ip)),CensusCfgEntity.class,EntityConstants.SYS_CENSUS_CONFIG);
        List<CensusCfgDTO> returnList=new ArrayList<>();
        for (CensusCfgEntity cfgEntity:list){
            CensusCfgDTO censusCfgDTO=new CensusCfgDTO();
            BeanUtils.copyProperties(cfgEntity,censusCfgDTO);
            returnList.add(censusCfgDTO);
        }
        return returnList;
    }

    @Override
    public void delete(String id) {
        MongoTemplate mongoTemplate=BaseMongoTemplate.getSysMongo();
        mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(id)),MongoEntityConstants.SYS_CENSUS_CONFIG);
    }

    @Override
    public CountDTO getVisitPage(String ip,CensusStatus status) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        Query q = new Query();
        Criteria c = Criteria.where("ip").is(ip);
        switch (status){
            case LAST_DAY:
                c.and("dat").gte(getLastDayStartDate()).lte(getLastDayEndDate());
                break;
            case LAST_WEEK:
                c.and("dat").gte(getLastWeekDate()).lte(getTodayEndDate());
                break;
            case LAST_MONTH:
                c.and("dat").gte(getLastMonthDate()).lte(getTodayEndDate());
                break;
            default:
                c.and("dat").gte(getTodayStartDate()).lte(getTodayEndDate());
        }
        q.addCriteria(c);
        //获取访问的url列表
        Aggregation pageAgg = Aggregation.newAggregation(
                match(c),
                project("lp"),
                group("lp").count().as("count")
        );
        AggregationResults<CountVO> pageAggResult = mongoTemplate.aggregate(pageAgg, MongoEntityConstants.SYS_CENSUS, CountVO.class);
        List<CountVO> pvCountList=new ArrayList<>(pageAggResult.getMappedResults());
        List<String> urlNames=getUrlName(pvCountList);
        List<ConstantsDTO> itemsList=getConstantsList(status,urlNames);

        CountDTO countDTO=new CountDTO();
        countDTO.setTotal(pvCountList.size());
        countDTO.setItems(itemsList);
        countDTO.setSum(getSum(itemsList));
        countDTO.setTimeSpan(getTimeSpan(status));
        return countDTO;
    }

    private String getTimeSpan(CensusStatus status) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
        switch (status){
            case LAST_DAY:
                return sdf.format(getLastDayStartDate());
            case LAST_WEEK:
                return sdf.format(getLastWeekDate());
            case LAST_MONTH:
                return  sdf.format(getLastMonthDate());
            default:
                return sdf.format(new Date());
        }
    }

    private List<Integer> getSum(List<ConstantsDTO> itemsList) {
        int pvCount=0;
        int uvCount=0;
        int ipCount=0;
        for(ConstantsDTO cd:itemsList){
            pvCount+=cd.getTotalPv();
            uvCount+=cd.getTotalUv();
            ipCount+=cd.getTotalIp();
        }
        List<Integer> sum=new ArrayList<>();
        sum.add(pvCount);
        sum.add(uvCount);
        sum.add(ipCount);
        return sum;
    }


    /**
     * 根据查询日期范围，url地址获得某url的统计数据
     * @param status
     * @param urlNames
     * @return
     */
    private List<ConstantsDTO> getConstantsList(CensusStatus status,List<String> urlNames) {
        List<ConstantsDTO> returnList=new ArrayList<>();
        for(String url:urlNames){
            ConstantsDTO constantsDTO=getTotalConstants(status,url);
            returnList.add(constantsDTO);
        }
        return returnList;
    }


    /**
     * 获取Url地址列表
     * @param pvCountList
     * @return
     */
    private List<String> getUrlName(List<CountVO> pvCountList) {
        List<String> listNames=new ArrayList<>();
        for (CountVO vo:pvCountList){
            listNames.add(vo.getLp());
        }
        return listNames;
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
        AggregationResults<CensusIpVO> ipCountResult = mongoTemplate.aggregate(ipCountAgg, MongoEntityConstants.SYS_CENSUS, CensusIpVO.class);
        List<CensusIpVO> ipCountList = new ArrayList<>(ipCountResult.getMappedResults());

        Aggregation uidCountAgg = Aggregation.newAggregation(
                match(c),
                project("uid"),
                group("uid").count().as("count")
        );
        AggregationResults<CensusUidVO> uidCountResult = mongoTemplate.aggregate(uidCountAgg, MongoEntityConstants.SYS_CENSUS, CensusUidVO.class);
        List<CensusUidVO> uidCountList = new ArrayList<>(uidCountResult.getMappedResults());

        int totalPv = (int) mongoTemplate.count(q, MongoEntityConstants.SYS_CENSUS);
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

    /**
     * 获取今日的开始时间
     * @return
     */
    private  Date getTodayStartDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 8);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 获取今日的结束时间
     * @return
     */
    private  Date getTodayEndDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 31);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * 获取昨日的开始时间
     * @return
     */
    private  Date getLastDayStartDate(){
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DATE,-1);
        c.set(Calendar.HOUR_OF_DAY, 8);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 获取昨日的结束时间
     * @return
     */
    private Date getLastDayEndDate(){
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DATE,-1);
        c.set(Calendar.HOUR_OF_DAY, 31);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * 获取上周的开始时间
     * @return
     */
    private  Date getLastWeekDate(){
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DAY_OF_WEEK_IN_MONTH,-1);
        c.set(Calendar.HOUR_OF_DAY, 8);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 获取上个月的开始时间
     * @return
     */
    private  Date getLastMonthDate(){
        Calendar c=Calendar.getInstance();
        c.add(Calendar.MONTH,-1);
        c.set(Calendar.HOUR_OF_DAY, 8);
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

    public class CountVO {
        @Id
        private String lp;
        private String uid;
        private String ip;
        private int count;

        public String getLp() {
            return lp;
        }

        public void setLp(String lp) {
            this.lp = lp;
        }

        public int getCount() {
            return count;
        }


        public void setCount(int count) {
            this.count = count;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        @Override
        public String toString() {
            return "CountVO{" +
                    "lp='" + lp + '\'' +
                    ", uid='" + uid + '\'' +
                    ", ip='" + ip + '\'' +
                    ", count=" + count +
                    '}';
        }
    }

}
