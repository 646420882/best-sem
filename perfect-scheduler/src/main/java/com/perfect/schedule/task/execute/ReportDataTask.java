package com.perfect.schedule.task.execute;

import com.perfect.api.baidu.NewRealTimeDataReports;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.sms.v3.RealTimeResultType;
import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.KeywordRealTimeDataEntity;
import com.perfect.entity.SystemUserEntity;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import com.perfect.schedule.core.IScheduleTaskDealSingle;
import com.perfect.schedule.task.conf.TaskConfig;
import com.perfect.utils.CollectionNameUtils;
import com.perfect.utils.DBNameUtils;
import com.perfect.utils.NumberUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

import static com.perfect.constants.ReportConstants.*;

/**
 * Created by yousheng on 2014/8/7.
 *
 * @author yousheng
 */
@Component("reportDataTask")
public class ReportDataTask implements IScheduleTaskDealSingle<SystemUserEntity> {

    @Resource
    private SystemUserDAO systemUserDAO;


    @Override
    public boolean execute(SystemUserEntity task, String ownSign) throws Exception {

        List<BaiduAccountInfoEntity> baiduAccountInfoEntityList = task.getBaiduAccountInfoEntities();

        String dbname = DBNameUtils.getReportDBName(task.getUserName());

        for (BaiduAccountInfoEntity entity : baiduAccountInfoEntityList) {
            String tokenId = entity.getToken();
            String username = entity.getBaiduUserName();
            String pwd = entity.getBaiduPassword();

            ServiceFactory sf = ServiceFactory.getInstance(username, pwd
                    , tokenId, null);
            NewRealTimeDataReports reports = new NewRealTimeDataReports(sf);

            // 地域数据获取
            List<RealTimeResultType> pcRegionData = reports.getRegionalRealTimeDataPC(null, null);

            List<RealTimeResultType> mobileRegionData = reports.getRegionalRealTimeDataPC(null, null);

            pushReport(pcRegionData, mobileRegionData, task.getUserName(), CollectionNameUtils.getYesterdayRegion());

            // 关键词数据获取
            List<RealTimeResultType> pcKeywordData = reports.getKeyWordidRealTimeDataPC(null, null);

            List<RealTimeResultType> mobileKeywordData = reports.getKeyWordidRealTimeDataMobile(null, null);

            pushReport(pcKeywordData, mobileKeywordData, task.getUserName(), CollectionNameUtils.getYesterdayKc());

        }

        return true;
    }

    /**
     * @param taskParameter
     * @param ownSign
     * @param taskItemNum
     * @param taskItemList
     * @param eachFetchDataNum
     * @return
     * @throws Exception
     */
    @Override
    public List selectTasks(String taskParameter, String ownSign, int taskItemNum, List taskItemList, int eachFetchDataNum) throws Exception {
        if (ownSign == null || !ownSign.equals(TaskConfig.TASK_DOMAIN)) {
            return Collections.emptyList();
        }

        List<SystemUserEntity> userEntityList = systemUserDAO.findAll();
        return userEntityList;
    }

    @Override
    public Comparator getComparator() {
        return null;
    }

    public void pushReport(List<RealTimeResultType> aList, List<RealTimeResultType> bList, final String userName, final String collectionName) {

        List<KeywordRealTimeDataEntity> list = new ArrayList<KeywordRealTimeDataEntity>(aList.size());

        Map<Long, KeywordRealTimeDataEntity> dataEntityMap = new HashMap<>(list.size() / 2);

        // 查询PC统计数据
        for (RealTimeResultType resultType : aList) {
            KeywordRealTimeDataEntity dataEntity = new KeywordRealTimeDataEntity();

            dataEntity.setKeywordId(resultType.getID());

            dataEntity.setCampaignName(resultType.getName(IDX_NAME_CAMPAIGN));
            dataEntity.setAdgroupName(resultType.getName(IDX_NAME_ADGROUP));
            dataEntity.setKeywordName(resultType.getName(IDX_NAME_KEYWORD));

            dataEntity.setPcClick(NumberUtils.parseInt(resultType.getKPI(IDX_CLICK)));
            dataEntity.setPcCost(NumberUtils.parseDouble(resultType.getKPI(IDX_COST)));
            dataEntity.setPcCpc(NumberUtils.parseDouble(resultType.getKPI(IDX_CPC)));
            dataEntity.setPcCtr(NumberUtils.parseDouble(resultType.getKPI(IDX_CTR)));
            dataEntity.setPcImpression(NumberUtils.parseInt(resultType.getKPI(IDX_IMPRESSION)));
            dataEntity.setPcPosition(NumberUtils.parseDouble(resultType.getKPI(IDX_POSITION)));
            dataEntity.setPcConversion(NumberUtils.parseDouble(resultType.getKPI(IDX_CONVERSION)));

            list.add(dataEntity);
            dataEntityMap.put(dataEntity.getKeywordId(), dataEntity);
        }


        for (RealTimeResultType resultType : bList) {

            KeywordRealTimeDataEntity dataEntity = null;
            if (dataEntityMap.containsKey(dataEntity.getKeywordId())) {
                dataEntity = dataEntityMap.get(dataEntity.getKeywordId());
            } else {
                dataEntity = new KeywordRealTimeDataEntity();
                dataEntity.setKeywordId(resultType.getID());
                list.add(dataEntity);
                dataEntityMap.put(dataEntity.getKeywordId(), dataEntity);

                dataEntity.setCampaignName(resultType.getName(IDX_NAME_CAMPAIGN));
                dataEntity.setAdgroupName(resultType.getName(IDX_NAME_ADGROUP));
                dataEntity.setKeywordName(resultType.getName(IDX_NAME_KEYWORD));
            }

            dataEntity.setMobileClick(NumberUtils.parseInt(resultType.getKPI(IDX_CLICK)));
            dataEntity.setMobileCost(NumberUtils.parseDouble(resultType.getKPI(IDX_COST)));
            dataEntity.setMobileCpc(NumberUtils.parseDouble(resultType.getKPI(IDX_CPC)));
            dataEntity.setMobileCtr(NumberUtils.parseDouble(resultType.getKPI(IDX_CTR)));
            dataEntity.setMobileImpression(NumberUtils.parseInt(resultType.getKPI(IDX_IMPRESSION)));
            dataEntity.setMobilePosition(NumberUtils.parseDouble(resultType.getKPI(IDX_POSITION)));
            dataEntity.setMobileConversion(NumberUtils.parseDouble(resultType.getKPI(IDX_CONVERSION)));

        }

        MongoTemplate baseMongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(userName));

        if (!baseMongoTemplate.collectionExists(collectionName)) {
            baseMongoTemplate.insert(list, CollectionNameUtils.getYesterdayKc());
        }


    }
}
