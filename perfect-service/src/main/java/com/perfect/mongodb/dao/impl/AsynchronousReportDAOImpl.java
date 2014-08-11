package com.perfect.mongodb.dao.impl;

import com.perfect.api.baidu.AsynchronousReport;
import com.perfect.autosdk.sms.v3.RealTimeResultType;
import com.perfect.dao.AsynchronousReportDAO;
import com.perfect.entity.AdgroupRealTimeDataEntity;
import com.perfect.entity.CreativeRealTimeDataEntity;
import com.perfect.entity.KeywordRealTimeDataEntity;
import com.perfect.entity.RegionRealTimeDataEntity;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by baizz on 2014-08-07.
 */
@Repository("realTimeDataReportDAO")
public class AsynchronousReportDAOImpl implements AsynchronousReportDAO {

    /*private MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(
            UserUtil.getDatabaseName(AppContext.getUser().toString(), "report"));*/

    private MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate("user_perfect_report");

    private String mobileFilePath;

    public void getAdgroupRealTimeData() {
        AsynchronousReport report = new AsynchronousReport();
        String pcFilePath = report.getUnitRealTimeDataPC(null,null,"2014-01-25", "2014-02-25");
        mobileFilePath = report.getUnitRealTimeDataMobile(null,null,"2014-01-25", "2014-02-25");

        List<AdgroupRealTimeDataEntity> list;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        AdgroupRealTimeDataHandler task1 = new AdgroupRealTimeDataHandler(pcList, 0, pcList.size());
        Future<List<AdgroupRealTimeDataEntity>> voResult = forkJoinPool.submit(task1);
        try {
            list = voResult.get();
            System.out.println("=====================================");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }
    }

    public void getCreativeRealTimeData() {
        AsynchronousReport report = new AsynchronousReport();
        String pcFilePath = report.getCreativeRealTimeDataPC(null,null,"2014-01-25", "2014-02-25");
        mobileFilePath = report.getCreativeRealTimeDataMobile(null,null,"2014-01-25", "2014-02-25");
    }

    public void getKeywordRealTimeData() {
        AsynchronousReport report = new AsynchronousReport();
        String FilePath = report.getKeyWordidRealTimeDataPC(null,null,"2014-01-25", "2014-02-25");
        mobileFilePath = report.getKeyWordidRealTimeDataMobile(null,null,"2014-01-25", "2014-02-25");
    }

    public void getRegionRealTimeData() {
        AsynchronousReport report = new AsynchronousReport();
        String FilePath = report.getRegionalRealTimeDataPC(null,null,"2014-01-25", "2014-02-25");
        mobileFilePath = report.getRegionalRealTimeDataMobile(null,null,"2014-01-25", "2014-02-25");
    }

    class AdgroupRealTimeDataHandler extends RecursiveTask<List<AdgroupRealTimeDataEntity>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<RealTimeResultType> pcList;

        AdgroupRealTimeDataHandler(List<RealTimeResultType> pcList, int first, int last) {
            this.first = first;
            this.last = last;
            this.pcList = pcList;
        }

        @Override
        protected List<AdgroupRealTimeDataEntity> compute() {
            List<AdgroupRealTimeDataEntity> list = new ArrayList<>();
            boolean status = (last - first) < threshold;
            if (status) {
                for (int i = first; i < last; i++) {
                    RealTimeResultType entity = pcList.get(i);
                    boolean temp = true;
                    for (RealTimeResultType type : mobileList) {
                        if (entity.getID().compareTo(type.getID()) == 0) {
                            AdgroupRealTimeDataEntity _entity = new AdgroupRealTimeDataEntity();
                            _entity.setAdgroupId(entity.getID());
                            _entity.setAdgroupName(entity.getName(2));
                            _entity.setCampaignName(entity.getName(1));
                            _entity.setPcImpression(Integer.valueOf(entity.getKPI(0)));
                            _entity.setPcClick(Integer.valueOf(entity.getKPI(1)));
                            _entity.setPcCtr(Double.valueOf(entity.getKPI(2)));
                            _entity.setPcCost(Double.valueOf(entity.getKPI(3)));
                            _entity.setPcCpc(Double.valueOf(entity.getKPI(4)));
                            _entity.setPcConversion(Double.valueOf(entity.getKPI(5)));
                            _entity.setMobileImpression(Integer.valueOf(type.getKPI(0)));
                            _entity.setMobileClick(Integer.valueOf(type.getKPI(1)));
                            _entity.setMobileCtr(Double.valueOf(type.getKPI(2)));
                            _entity.setMobileCost(Double.valueOf(type.getKPI(3)));
                            _entity.setMobileCpc(Double.valueOf(type.getKPI(4)));
                            _entity.setMobileConversion(Double.valueOf(type.getKPI(5)));
                            list.add(_entity);
                            temp = false;
                            break;
                        }
                    }
                    if (temp) {
                        AdgroupRealTimeDataEntity _entity = new AdgroupRealTimeDataEntity();
                        _entity.setAdgroupId(entity.getID());
                        _entity.setAdgroupName(entity.getName(2));
                        _entity.setCampaignName(entity.getName(1));
                        _entity.setPcImpression(Integer.valueOf(entity.getKPI(0)));
                        _entity.setPcClick(Integer.valueOf(entity.getKPI(1)));
                        _entity.setPcCtr(Double.valueOf(entity.getKPI(2)));
                        _entity.setPcCost(Double.valueOf(entity.getKPI(3)));
                        _entity.setPcCpc(Double.valueOf(entity.getKPI(4)));
                        _entity.setPcConversion(Double.valueOf(entity.getKPI(5)));
                        list.add(_entity);
                    }
                }
            } else {
                int middle = (first + last) / 2;
                AdgroupRealTimeDataHandler task1 = new AdgroupRealTimeDataHandler(pcList, first, middle);
                AdgroupRealTimeDataHandler task2 = new AdgroupRealTimeDataHandler(pcList, middle, last);

                invokeAll(task1, task2);
                list.clear();
                list.addAll(task1.join());
                list.addAll(task2.join());
            }
            System.out.println(list.size() + "***********************");
            return list;
        }
    }

    class CreativeRealTimeDataHandler extends RecursiveTask<List<CreativeRealTimeDataEntity>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<RealTimeResultType> pcList;

        CreativeRealTimeDataHandler(List<RealTimeResultType> pcList, int first, int last) {
            this.first = first;
            this.last = last;
            this.pcList = pcList;
        }

        @Override
        protected List<CreativeRealTimeDataEntity> compute() {
            List<CreativeRealTimeDataEntity> list = new ArrayList<>();
            boolean status = (last - first) < threshold;
            if (status) {
                for (int i = first; i < last; i++) {
                    RealTimeResultType entity = pcList.get(i);
                    boolean temp = true;
                    for (RealTimeResultType type : mobileList) {
                        if (entity.getID().compareTo(type.getID()) == 0) {
                            CreativeRealTimeDataEntity _entity = new CreativeRealTimeDataEntity();
                            _entity.setCreativeId(entity.getID());
                            _entity.setCreativeName(entity.getName(3));
                            _entity.setAdgroupName(entity.getName(2));
                            _entity.setCampaignName(entity.getName(1));
                            _entity.setPcImpression(Integer.valueOf(entity.getKPI(0)));
                            _entity.setPcClick(Integer.valueOf(entity.getKPI(1)));
                            _entity.setPcCtr(Double.valueOf(entity.getKPI(2)));
                            _entity.setPcCost(Double.valueOf(entity.getKPI(3)));
                            _entity.setPcCpc(Double.valueOf(entity.getKPI(4)));
                            _entity.setPcPosition(Double.valueOf(entity.getKPI(5)));
                            _entity.setPcConversion(Double.valueOf(entity.getKPI(6)));
                            _entity.setMobileImpression(Integer.valueOf(type.getKPI(0)));
                            _entity.setMobileClick(Integer.valueOf(type.getKPI(1)));
                            _entity.setMobileCtr(Double.valueOf(type.getKPI(2)));
                            _entity.setMobileCost(Double.valueOf(type.getKPI(3)));
                            _entity.setMobileCpc(Double.valueOf(type.getKPI(4)));
                            _entity.setMobilePosition(Double.valueOf(type.getKPI(5)));
                            _entity.setMobileConversion(Double.valueOf(type.getKPI(6)));
                            list.add(_entity);
                            temp = false;
                            break;
                        }
                    }
                    if (temp) {
                        CreativeRealTimeDataEntity _entity = new CreativeRealTimeDataEntity();
                        _entity.setCreativeId(entity.getID());
                        _entity.setCreativeName(entity.getName(3));
                        _entity.setAdgroupName(entity.getName(2));
                        _entity.setCampaignName(entity.getName(1));
                        _entity.setPcImpression(Integer.valueOf(entity.getKPI(0)));
                        _entity.setPcClick(Integer.valueOf(entity.getKPI(1)));
                        _entity.setPcCtr(Double.valueOf(entity.getKPI(2)));
                        _entity.setPcCost(Double.valueOf(entity.getKPI(3)));
                        _entity.setPcCpc(Double.valueOf(entity.getKPI(4)));
                        _entity.setPcPosition(Double.valueOf(entity.getKPI(5)));
                        _entity.setPcConversion(Double.valueOf(entity.getKPI(6)));
                        list.add(_entity);
                    }
                }
            } else {
                int middle = (first + last) / 2;
                AdgroupRealTimeDataHandler task1 = new AdgroupRealTimeDataHandler(pcList, first, middle);
                AdgroupRealTimeDataHandler task2 = new AdgroupRealTimeDataHandler(pcList, middle, last);

                invokeAll(task1, task2);
            }
            System.out.println(list.size() + "***********************");
            return list;
        }
    }

    class KeywordRealTimeDataHandler extends RecursiveTask<List<KeywordRealTimeDataEntity>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<RealTimeResultType> pcList;

        KeywordRealTimeDataHandler(List<RealTimeResultType> pcList, int first, int last) {
            this.first = first;
            this.last = last;
            this.pcList = pcList;
        }

        @Override
        protected List<KeywordRealTimeDataEntity> compute() {
            List<KeywordRealTimeDataEntity> list = new ArrayList<>();
            boolean status = (last - first) < threshold;
            if (status) {
                for (int i = first; i < last; i++) {
                    RealTimeResultType entity = pcList.get(i);
                    boolean temp = true;
                    for (RealTimeResultType type : mobileList) {
                        if (entity.getID().compareTo(type.getID()) == 0) {
                            KeywordRealTimeDataEntity _entity = new KeywordRealTimeDataEntity();
                            _entity.setKeywordId(entity.getID());
                            _entity.setKeywordName(entity.getName(3));
                            _entity.setAdgroupName(entity.getName(2));
                            _entity.setCampaignName(entity.getName(1));
                            _entity.setPcImpression(Integer.valueOf(entity.getKPI(0)));
                            _entity.setPcClick(Integer.valueOf(entity.getKPI(1)));
                            _entity.setPcCtr(Double.valueOf(entity.getKPI(2)));
                            _entity.setPcCost(Double.valueOf(entity.getKPI(3)));
                            _entity.setPcCpc(Double.valueOf(entity.getKPI(4)));
                            _entity.setPcPosition(Double.valueOf(entity.getKPI(5)));
                            _entity.setPcConversion(Double.valueOf(entity.getKPI(6)));
                            _entity.setMobileImpression(Integer.valueOf(type.getKPI(0)));
                            _entity.setMobileClick(Integer.valueOf(type.getKPI(1)));
                            _entity.setMobileCtr(Double.valueOf(type.getKPI(2)));
                            _entity.setMobileCost(Double.valueOf(type.getKPI(3)));
                            _entity.setMobileCpc(Double.valueOf(type.getKPI(4)));
                            _entity.setMobilePosition(Double.valueOf(type.getKPI(5)));
                            _entity.setMobileConversion(Double.valueOf(type.getKPI(6)));
                            list.add(_entity);
                            temp = false;
                            break;
                        }
                    }
                    if (temp) {
                        KeywordRealTimeDataEntity _entity = new KeywordRealTimeDataEntity();
                        _entity.setKeywordId(entity.getID());
                        _entity.setKeywordName(entity.getName(3));
                        _entity.setAdgroupName(entity.getName(2));
                        _entity.setCampaignName(entity.getName(1));
                        _entity.setPcImpression(Integer.valueOf(entity.getKPI(0)));
                        _entity.setPcClick(Integer.valueOf(entity.getKPI(1)));
                        _entity.setPcCtr(Double.valueOf(entity.getKPI(2)));
                        _entity.setPcCost(Double.valueOf(entity.getKPI(3)));
                        _entity.setPcCpc(Double.valueOf(entity.getKPI(4)));
                        _entity.setPcPosition(Double.valueOf(entity.getKPI(5)));
                        _entity.setPcConversion(Double.valueOf(entity.getKPI(6)));
                        list.add(_entity);
                    }
                }
            } else {
                int middle = (first + last) / 2;
                AdgroupRealTimeDataHandler task1 = new AdgroupRealTimeDataHandler(pcList, first, middle);
                AdgroupRealTimeDataHandler task2 = new AdgroupRealTimeDataHandler(pcList, middle, last);

                invokeAll(task1, task2);
            }
            System.out.println(list.size() + "***********************");
            return list;
        }
    }

    class RegionRealTimeDataHandler extends RecursiveTask<List<RegionRealTimeDataEntity>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<RealTimeResultType> pcList;

        RegionRealTimeDataHandler(List<RealTimeResultType> pcList, int first, int last) {
            this.first = first;
            this.last = last;
            this.pcList = pcList;
        }

        @Override
        protected List<RegionRealTimeDataEntity> compute() {
            List<RegionRealTimeDataEntity> list = new ArrayList<>();
            boolean status = (last - first) < threshold;
            if (status) {
                for (int i = first; i < last; i++) {
                    RealTimeResultType entity = pcList.get(i);
                    boolean temp = true;
                    for (RealTimeResultType type : mobileList) {
                        if (entity.getID().compareTo(type.getID()) == 0) {
                            RegionRealTimeDataEntity _entity = new RegionRealTimeDataEntity();
                            _entity.setRegionId(entity.getID());
                            _entity.setRegionName(entity.getName(3));
                            _entity.setAdgroupName(entity.getName(2));
                            _entity.setCampaignName(entity.getName(1));
                            _entity.setPcImpression(Integer.valueOf(entity.getKPI(0)));
                            _entity.setPcClick(Integer.valueOf(entity.getKPI(1)));
                            _entity.setPcCtr(Double.valueOf(entity.getKPI(2)));
                            _entity.setPcCost(Double.valueOf(entity.getKPI(3)));
                            _entity.setPcCpc(Double.valueOf(entity.getKPI(4)));
                            _entity.setPcPosition(Double.valueOf(entity.getKPI(5)));
                            _entity.setPcConversion(Double.valueOf(entity.getKPI(6)));
                            _entity.setMobileImpression(Integer.valueOf(type.getKPI(0)));
                            _entity.setMobileClick(Integer.valueOf(type.getKPI(1)));
                            _entity.setMobileCtr(Double.valueOf(type.getKPI(2)));
                            _entity.setMobileCost(Double.valueOf(type.getKPI(3)));
                            _entity.setMobileCpc(Double.valueOf(type.getKPI(4)));
                            _entity.setMobilePosition(Double.valueOf(type.getKPI(5)));
                            _entity.setMobileConversion(Double.valueOf(type.getKPI(6)));
                            list.add(_entity);
                            temp = false;
                            break;
                        }
                    }
                    if (temp) {
                        RegionRealTimeDataEntity _entity = new RegionRealTimeDataEntity();
                        _entity.setRegionId(entity.getID());
                        _entity.setRegionName(entity.getName(3));
                        _entity.setAdgroupName(entity.getName(2));
                        _entity.setCampaignName(entity.getName(1));
                        _entity.setPcImpression(Integer.valueOf(entity.getKPI(0)));
                        _entity.setPcClick(Integer.valueOf(entity.getKPI(1)));
                        _entity.setPcCtr(Double.valueOf(entity.getKPI(2)));
                        _entity.setPcCost(Double.valueOf(entity.getKPI(3)));
                        _entity.setPcCpc(Double.valueOf(entity.getKPI(4)));
                        _entity.setPcPosition(Double.valueOf(entity.getKPI(5)));
                        _entity.setPcConversion(Double.valueOf(entity.getKPI(6)));
                        list.add(_entity);
                    }
                }
            } else {
                int middle = (first + last) / 2;
                AdgroupRealTimeDataHandler task1 = new AdgroupRealTimeDataHandler(pcList, first, middle);
                AdgroupRealTimeDataHandler task2 = new AdgroupRealTimeDataHandler(pcList, middle, last);

                invokeAll(task1, task2);
            }
            System.out.println(list.size() + "***********************");
            return list;
        }
    }

    public static void main(String[] args) {
        new AsynchronousReportDAOImpl().getAdgroupRealTimeData();
    }
}
