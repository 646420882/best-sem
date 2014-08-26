package com.perfect.schedule.task.execute;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.sms.v3.GetPreviewRequest;
import com.perfect.autosdk.sms.v3.KeywordType;
import com.perfect.dao.KeywordDAO;
import com.perfect.entity.*;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.service.BaiduApiService;
import com.perfect.schedule.core.IScheduleTaskDealMulti;
import com.perfect.schedule.core.TaskItemDefine;
import com.perfect.service.BiddingRuleService;
import com.perfect.service.HTMLAnalyseService;
import com.perfect.service.SystemUserService;
import com.perfect.service.impl.HTMLAnalyseServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by yousheng on 2014/8/14.
 *
 * @author yousheng
 */
@Component("biddingTask")
public class BiddingTask implements IScheduleTaskDealMulti<BiddingTask.TaskObject> {

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private BiddingRuleService biddingRuleService;

    @Resource
    private KeywordDAO keywordDAO;

    private Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    @Override
    public boolean execute(TaskObject[] tasks, String ownSign) throws Exception {
        int num = 0;
        // 调度策略
        for (TaskObject task : tasks) {

            BaiduAccountInfoEntity accountInfoEntity = task.getAccount();

            List<BiddingRuleEntity> biddingRuleEntityList = task.getList();

            BaiduApiService apiService = null;

            ServiceFactory service = ServiceFactory.getInstance(accountInfoEntity.getBaiduUserName(), accountInfoEntity.getBaiduPassword(), accountInfoEntity.getToken(), null);
            apiService = new BaiduApiService(service);

            List<KeywordType> keywordTypes = new ArrayList<>(biddingRuleEntityList.size());

            HTMLAnalyseService htmlAnalyseService = HTMLAnalyseServiceImpl.createService(service);

            Map<String, KeywordEntity> keywordEntityMap = new HashMap<>(5);
            List<BiddingRuleEntity> subList = new ArrayList<>(5);

            for (BiddingRuleEntity biddingRuleEntity : biddingRuleEntityList) {

                subList.add(biddingRuleEntity);
                KeywordEntity keywordEntity = keywordDAO.findOne(biddingRuleEntity.getKeywordId());
                keywordEntityMap.put(keywordEntity.getKeyword(), keywordEntity);

                GetPreviewRequest getPreviewRequest = new GetPreviewRequest();
                getPreviewRequest.setKeyWords(Arrays.asList(new String[]{keywordEntity.getKeyword()}));

                if (num == 5) {
                    // 生成一个任务
                    BiddingSubTask biddingSubTask = new BiddingSubTask(apiService, htmlAnalyseService, Lists.newArrayList(biddingRuleEntityList), Maps.newHashMap(keywordEntityMap), new ArrayList<KeywordType>());
                    keywordEntityMap.clear();
                    subList.clear();
                    executor.execute(biddingSubTask);
                }
            }

            if (!subList.isEmpty()) {
                BiddingSubTask biddingSubTask = new BiddingSubTask(apiService, htmlAnalyseService, Lists.newArrayList(biddingRuleEntityList), Maps.newHashMap(keywordEntityMap), new ArrayList<KeywordType>());
                executor.execute(biddingSubTask);
            }

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
                List<BiddingRuleEntity> biddingRuleEntityList = biddingRuleService.getTaskByAccountId(userEntity.getUserName(), baiduAccountInfoEntity.getId());

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

    public static class TaskObject {

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
