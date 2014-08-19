package com.perfect.schedule.task.execute;

import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.sms.v3.GetPreviewRequest;
import com.perfect.autosdk.sms.v3.KeywordType;
import com.perfect.dao.KeywordDAO;
import com.perfect.entity.*;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.entity.bidding.StrategyEntity;
import com.perfect.main.BaiduApiService;
import com.perfect.schedule.core.IScheduleTaskDealMulti;
import com.perfect.schedule.core.TaskItemDefine;
import com.perfect.service.BiddingRuleService;
import com.perfect.service.HTMLAnalyseService;
import com.perfect.service.SystemUserService;
import com.perfect.service.impl.HTMLAnalyseServiceImpl;
import org.quartz.CronExpression;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static com.perfect.constants.BiddingStrategyConstants.*;

/**
 * Created by yousheng on 2014/8/14.
 *
 * @author yousheng
 */
@Component
public class BiddingTask implements IScheduleTaskDealMulti<BiddingTask.TaskObject> {

    private final int gid;

    private Map<String, CronExpression> CACHE = new WeakHashMap<>(2 << 5);

    private Map<Long, BaiduApiService> API_CACHE = new WeakHashMap<>(2 << 5);


    @Resource
    private SystemUserService systemUserService;

    @Resource
    private BiddingRuleService biddingRuleService;

    @Resource
    private KeywordDAO keywordDAO;

    public BiddingTask(int gid) {
        this.gid = gid;
    }

    @Override
    public boolean execute(TaskObject[] tasks, String ownSign) throws Exception {
        Date date = Calendar.getInstance().getTime();

        // 调度策略
        for (TaskObject task : tasks) {

            BaiduAccountInfoEntity accountInfoEntity = task.getAccount();

            List<BiddingRuleEntity> biddingRuleEntityList = task.getList();

            BaiduApiService apiService = null;

            ServiceFactory service = ServiceFactory.getInstance(accountInfoEntity.getBaiduUserName(), accountInfoEntity.getBaiduPassword(), accountInfoEntity.getToken(), null);
            apiService = new BaiduApiService(service);

            List<KeywordType> keywordTypes = new ArrayList<>(biddingRuleEntityList.size());

            HTMLAnalyseService htmlAnalyseService = HTMLAnalyseServiceImpl.createService(service);
            for (BiddingRuleEntity biddingRuleEntity : biddingRuleEntityList) {

                KeywordEntity keywordEntity = keywordDAO.findOne(biddingRuleEntity.getKeywordId());

                GetPreviewRequest getPreviewRequest = new GetPreviewRequest();
                getPreviewRequest.setKeyWords(Arrays.asList(new String[]{keywordEntity.getKeyword()}));

                List<HTMLAnalyseServiceImpl.PreviewData> datas = htmlAnalyseService.getPageData(getPreviewRequest);

                if (datas == null || datas.isEmpty()) {
                    continue;
                }


                HTMLAnalyseServiceImpl.PreviewData data = datas.get(0);
                int leftpos = getRank(data.getLeft(), keywordEntity);
                int rightpos = getRank(data.getRight(),keywordEntity);

                //排名分析
                StrategyEntity strategyEntity = biddingRuleEntity.getStrategyEntity();

                int positionStrategy = strategyEntity.getPositionStrategy();


                if (positionStrategy == POS_LEFT_1.value()) {
                    List<CreativeVOEntity> list = data.getLeft();
                    for (CreativeVOEntity entity : list) {
                        if (keywordEntity.getPcDestinationUrl().contains(entity.getUrl())) {

                        }
                    }
                } else if (positionStrategy == POS_LEFT_2_3.value()) {

                } else if (positionStrategy == POS_RIGHT_1_3.value()) {

                } else if (positionStrategy == POS_RIGHT_OTHERS.value()) {

                }

                String cronExpStr = strategyEntity.getCron();
                CronExpression cronExpression;
                if (CACHE.containsKey(cronExpStr)) {
                    cronExpression = CACHE.get(cronExpStr);
                } else {
                    cronExpression = new CronExpression(cronExpStr);
                    CACHE.put(cronExpStr, cronExpression);
                }

                Date nextDate = cronExpression.getNextValidTimeAfter(date);

                biddingRuleEntity.setNextTime(nextDate.getTime());


                double cur = biddingRuleEntity.getCurrentPrice();

                if (cur != strategyEntity.getMaxPrice()) {

                    if (strategyEntity.getSpd() == SPD_ECONOMIC.value()) {
                        cur = cur + 0.05;

                    } else if (strategyEntity.getSpd() == SPD_FAST.value()) {
                        cur = cur + 0.1;
                    }
                    if (cur > strategyEntity.getMaxPrice()) {
                        cur = strategyEntity.getMaxPrice();
                    }

                    biddingRuleEntity.setCurrentPrice(cur);
                }

                KeywordType type = new KeywordType();
                type.setKeywordId(biddingRuleEntity.getKeywordId());
                type.setPrice(cur);

                keywordTypes.add(type);
            }

            apiService.setKeywordPrice(keywordTypes);


            biddingRuleService.updateRule(biddingRuleEntityList);
        }
        return false;
    }

    @Override
    public List<TaskObject> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {

        Iterable<SystemUserEntity> userEntityList = systemUserService.getAllUser();

        List<TaskObject> objectList = new ArrayList<>();
        for (SystemUserEntity userEntity : userEntityList) {
            List<BaiduAccountInfoEntity> accountInfoEntityList = userEntity.getBaiduAccountInfoEntities();
            for (BaiduAccountInfoEntity baiduAccountInfoEntity : accountInfoEntityList) {
                List<BiddingRuleEntity> biddingRuleEntityList = biddingRuleService.getNextRunByGroupId(userEntity.getUserName(), baiduAccountInfoEntity.getId(), gid);
                if (biddingRuleEntityList != null && !biddingRuleEntityList.isEmpty()) {
                    objectList.add(new TaskObject(userEntity.getUserName(), baiduAccountInfoEntity, biddingRuleEntityList));
                }

            }

        }


        return objectList;
    }

    private int getRank(List<CreativeVOEntity> data, KeywordEntity keywordEntity) {
        int rank = 1;
        for (CreativeVOEntity entity : data) {
            try {
                URL url = new URL(keywordEntity.getPcDestinationUrl());
                if (url.getHost().equals(entity.getUrl())) {
                    return rank;
                } else {
                    RankEntity rankEntity = new RankEntity();
                    rankEntity.setKeyword(keywordEntity.getKeyword());
                    rankEntity.setTitle(entity.getTitle());
                    rankEntity.setDesc(entity.getDescription());
                    rankEntity.setHost(entity.getUrl());

                    rank++;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
        return -1;
    }


    @Override
    public Comparator<TaskObject> getComparator() {
        return null;
    }

    static class TaskObject {

        private final List<BiddingRuleEntity> list;
        private final BaiduAccountInfoEntity account;
        private final String userName;

        public TaskObject(String userName, BaiduAccountInfoEntity account, List<BiddingRuleEntity> list) {
            this.userName = userName;
            this.account = account;
            this.list = list;
        }

        public List<BiddingRuleEntity> getList() {
            return list;
        }

        public BaiduAccountInfoEntity getAccount() {
            return account;
        }

        public String getUserName() {
            return userName;
        }
    }

    public static void main(String args[]) {

        try {
            URL url = new URL("http://www.163.com/index.html");
            System.out.println(url.getHost());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


//        long start = System.currentTimeMillis();
//
//        try {
//            int idx = 1000000;
//
//            while (idx-- > 0) {
//                CronExpression cronExpression = new CronExpression("0 0/20 14-15,18-23 * * ?");
//
//                Date date = Calendar.getInstance().getTime();
////                for (int i = 0; i <= 20; i++) {
//                date = cronExpression.getNextValidTimeAfter(date);
////                    System.out.println(date);
////                }
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println(System.currentTimeMillis() - start);
    }
}
