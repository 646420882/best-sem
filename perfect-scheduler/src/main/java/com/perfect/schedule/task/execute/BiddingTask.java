package com.perfect.schedule.task.execute;

import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.core.AppContext;
import com.perfect.dto.CreativeDTO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.RankEntity;
import com.perfect.entity.SystemUserEntity;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.schedule.core.CronExpression;
import com.perfect.schedule.core.IScheduleTaskDealMulti;
import com.perfect.schedule.core.TaskItemDefine;
import com.perfect.service.*;
import com.perfect.service.impl.HTMLAnalyseServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
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
    private SysKeywordService sysKeywordService;

    @Resource
    private SysCampaignService sysCampaignService;

    private Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    @Override
    public boolean execute(TaskObject[] tasks, String ownSign) throws Exception {
        int num = 0;
        // 调度策略
        for (TaskObject task : tasks) {

            AppContext.setUser(task.getUserName());
            BaiduAccountInfoEntity accountInfoEntity = task.getAccount();

            List<BiddingRuleEntity> biddingRuleEntityList = task.getList();

            BaiduApiService apiService = null;

            ServiceFactory service = ServiceFactory.getInstance(accountInfoEntity.getBaiduUserName(), accountInfoEntity.getBaiduPassword(), accountInfoEntity.getToken(), null);
            apiService = new BaiduApiService(service);

            HTMLAnalyseService htmlAnalyseService = HTMLAnalyseServiceImpl.createService(service);

            for (BiddingRuleEntity biddingRuleEntity : biddingRuleEntityList) {

                KeywordEntity keywordEntity = sysKeywordService.findById(biddingRuleEntity.getKeywordId());

                // 生成一个任务
                BiddingSubTask biddingSubTask = new BiddingSubTask(task.getUserName(), service, biddingRuleService,
                        sysCampaignService, accountInfoEntity, biddingRuleEntity, keywordEntity);
                executor.execute(biddingSubTask);
            }

        }
        return false;
    }

    @Override
    public List<TaskObject> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {

        Iterable<SystemUserEntity> userEntityList = systemUserService.getAllUser();

        List<TaskObject> objectList = new ArrayList<>();

        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        for (SystemUserEntity userEntity : userEntityList) {
            List<BaiduAccountInfoEntity> accountInfoEntityList = userEntity.getBaiduAccountInfoEntities();
            for (BaiduAccountInfoEntity baiduAccountInfoEntity : accountInfoEntityList) {
                List<BiddingRuleEntity> biddingRuleEntityList = biddingRuleService.getTaskByAccountId(userEntity.getUserName(), baiduAccountInfoEntity.getId(), timeInMillis);

                if (biddingRuleEntityList != null && !biddingRuleEntityList.isEmpty()) {
                    objectList.add(new TaskObject(userEntity.getUserName(), baiduAccountInfoEntity, biddingRuleEntityList));
                }
            }
        }

        return objectList;
    }

    private int getRank(List<CreativeDTO> data, KeywordEntity keywordEntity) {
        int rank = 1;
        for (CreativeDTO entity : data) {
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

//        try {
//            URL url = new URL("http://www.163.com/index.html");
//            System.out.println(url.getHost());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }


        long start = System.currentTimeMillis();

        try {
            int idx = 1000000;

            while (idx-- > 0) {
                CronExpression cronExpression = new CronExpression("0 0 1-5/2 * * ?");

                Date date = Calendar.getInstance().getTime();
//                for (int i = 0; i <= 20; i++) {
                date = cronExpression.getNextValidTimeAfter(date);
//                    System.out.println(date);
//                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
