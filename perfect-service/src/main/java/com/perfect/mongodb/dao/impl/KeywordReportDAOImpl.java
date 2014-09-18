package com.perfect.mongodb.dao.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.KeywordReportDAO;
import com.perfect.entity.KeywordReportEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.DateUtils;
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.mongodb.utils.Pager;
import com.perfect.mongodb.utils.PagerInfo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by XiaoWei on 2014/9/17.
 */
@Repository("keywordReportDAO")
public class KeywordReportDAOImpl extends AbstractUserBaseDAOImpl<KeywordReportEntity, Long> implements KeywordReportDAO {
    @Override
    public Class<KeywordReportEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public PagerInfo findByPagerInfo(Map<String, Object> params) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        List<KeywordReportEntity> findlList = new ArrayList<>();
        Map<Long, KeywordReportEntity> imptMap = new HashMap<>();
        List<String> dateList = getCurrDate(params);
        List<Long> kwdIds = (List<Long>) params.get("kwdIds");
        String orderBy=params.get("orderBy").toString();
        if (dateList.size() > 0) {
            for (String str : dateList) {
                for (Long l : kwdIds) {
                    KeywordReportEntity list = mongoTemplate.findOne(new Query(Criteria.where(EntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()).and(EntityConstants.KEYWORD_ID).is(l)), KeywordReportEntity.class, str + "-" + EntityConstants.TBL_KEYWORD);
                    if (list != null) {
                        list.setOrderBy(orderBy);
                        findlList.add(list);
                    }
                }
            }
        }
        if (findlList.size() > 0) {
            for (int i = 0; i < findlList.size(); i++) {
                if (imptMap.get(findlList.get(i).getKeywordId()) == null) {
                    imptMap.put(findlList.get(i).getKeywordId(), findlList.get(i));
                } else {
                    KeywordReportEntity tmp = imptMap.get(findlList.get(i).getKeywordId());
                    Integer tMobileImpression=tmp.getMobileImpression()!=null?tmp.getMobileImpression():0;
                    Integer fMobileImpression= findlList.get(i).getMobileImpression()!=null?findlList.get(i).getMobileImpression():0;
                    Integer tClick=tmp.getMobileClick()!=null?tmp.getMobileClick():0;
                    Integer fClick=findlList.get(i).getMobileClick()!=null?findlList.get(i).getMobileClick():0;
                    Double tCtr=tmp.getMobileCtr()!=null?tmp.getMobileCtr():0.0;
                    Double fCtr=findlList.get(i).getMobileCtr()!=null?findlList.get(i).getMobileCtr():0.0;
                    BigDecimal tCost=tmp.getMobileCost()!=null?tmp.getMobileCost():BigDecimal.ZERO;
                    BigDecimal fCost=findlList.get(i).getMobileCost()!=null?findlList.get(i).getMobileCost():BigDecimal.ZERO;
                    BigDecimal tCpc= tmp.getMobileCpc()!=null? tmp.getMobileCpc():BigDecimal.ZERO;
                    BigDecimal fCpc= findlList.get(i).getMobileCpc()!=null? findlList.get(i).getMobileCpc():BigDecimal.ZERO;
                    BigDecimal tCpm=tmp.getMobileCpm()!=null?tmp.getMobileCpm():BigDecimal.ZERO;
                    BigDecimal fCpm=findlList.get(i).getMobileCpm()!=null?findlList.get(i).getMobileCpm():BigDecimal.ZERO;
                    Double tConversion=tmp.getMobileConversion()!=null?tmp.getMobileConversion():0.0;
                    Double fConversion= findlList.get(i).getMobileConversion()!=null? findlList.get(i).getMobileConversion():0.0;
                    Double tPosition=tmp.getMobilePosition()!=null?tmp.getMobilePosition():0.0;
                    Double fPosition=findlList.get(i).getMobilePosition()!=null?findlList.get(i).getMobilePosition():0.0;

                    Integer mibPcImpression = tMobileImpression +fMobileImpression;
                    Integer mibClick = tClick + fClick;
                    Double mibCtr = tCtr + fCtr;
                    BigDecimal mibCost = tCost.add(fCost);
                    BigDecimal mibCpc =tCpc.add(fCpc);
                    BigDecimal mibCpm = tCpm.add(fCpm);
                    Double mibConversion = tConversion +fConversion;
                    Double mibPosition = tPosition + fPosition;

                    Double needMibCtr=0.0;
                    if(mibClick>0.0&&mibPcImpression>0.0){
                        needMibCtr=Double.parseDouble(mibClick / mibPcImpression+"");
                    }
                    BigDecimal needMibCpc=BigDecimal.ZERO;
                    if(mibCost.compareTo(BigDecimal.ZERO)==1&&mibClick>0.0){
                        needMibCpc= mibCost.divide(BigDecimal.valueOf(mibClick),2,BigDecimal.ROUND_UP) ;
                    }
                    BigDecimal tmpPcpc=BigDecimal.ONE;
                    if(tmp.getPcClick()!=0){
                        tmpPcpc= tmp.getPcCost().divide(BigDecimal.valueOf(tmp.getPcClick()),2,BigDecimal.ROUND_UP);
                    }
                    BigDecimal fmpPcpc=BigDecimal.ONE;
                    if(findlList.get(i).getPcClick()!=0){
                        fmpPcpc= findlList.get(i).getPcCost().divide(BigDecimal.valueOf(findlList.get(i).getPcClick()),2,BigDecimal.ROUND_UP);
                    }
                    tmp.setPcImpression(tmp.getPcImpression() + findlList.get(i).getPcImpression() + mibPcImpression);
                    tmp.setPcClick(tmp.getPcClick() + findlList.get(i).getPcClick() + mibClick);
                    tmp.setPcCtr((tmp.getPcClick() / tmp.getPcImpression() + findlList.get(i).getPcClick() / findlList.get(i).getPcImpression()) +needMibCtr);
                    tmp.setPcCost(tmp.getPcCost().add(findlList.get(i).getPcCost()).add(mibCost));
                    tmp.setPcCpc((tmpPcpc.add(fmpPcpc)).add(needMibCpc));
                    tmp.setPcCpm(tmp.getPcCpm().add(findlList.get(i).getPcCpm()).add(mibCpm));
                    tmp.setPcConversion(tmp.getPcConversion() + findlList.get(i).getPcConversion() + mibConversion);
                    tmp.setPcPosition(tmp.getPcPosition() + findlList.get(i).getPcPosition() + mibPosition);

                    tmp.setMobileImpression(mibPcImpression);
                    tmp.setMobileClick(mibClick);
                    tmp.setMobileCtr(mibCtr);
                    tmp.setMobileCost(mibCost);
                    tmp.setMobileCpc(mibCpc);
                    tmp.setMobileCpm(mibCpm);
                    tmp.setMobileConversion(mibConversion);
                    tmp.setMobilePosition(mibPosition);
                }
            }
        }
        List<KeywordReportEntity> importList=new ArrayList<>();
        for (Map.Entry<Long,KeywordReportEntity> kwd:imptMap.entrySet()){
            importList.add(kwd.getValue());
        }
        Integer nowPage = Integer.parseInt(params.get("nowPage").toString());
        Integer pageSize = Integer.parseInt(params.get("pageSize").toString());
        Integer totalCount = imptMap.size();
        PagerInfo p = new PagerInfo(nowPage, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        } else {
            if (pageSize > totalCount) {
                importList = importList.subList(p.getFirstStation(),totalCount);
            } else {
                importList = importList.subList(p.getFirstStation(), p.getPageSize());
            }
        }
        Collections.sort(importList);
        p.setList(importList);
        return p;
    }

    private List<String> getCurrDate(Map<String, Object> params) {
        List<String> dateList = new ArrayList<>();
        if (params.containsKey("startDate") && params.containsKey("endDate")) {
            dateList = DateUtils.getPeriod(params.get("startDate").toString(), params.get("endDate").toString());
        }
        return dateList;
    }
}
