package com.perfect.schedule.task.execute;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.commons.BiddingThreadTaskExecutors;
import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.constants.KeywordStatusEnum;
import com.perfect.core.AppContext;
import com.perfect.dto.CreativeDTO;
import com.perfect.entity.*;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.schedule.core.CronExpression;
import com.perfect.service.*;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.*;

/**
 * Created by yousheng on 2014/8/14.
 *
 * @author yousheng
 */
@Component("biddingJob")
public class BiddingJob {

    private Logger logger = LoggerFactory.getLogger(BiddingJob.class);

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private BiddingRuleService biddingRuleService;

    @Resource
    private SysAdgroupService sysAdgroupService;

    @Resource
    private SysKeywordService sysKeywordService;

    @Resource
    private BiddingThreadTaskExecutors executors;

    @Resource
    private ApplicationContextHelper applicationContextHelper;

    private ApplicationContext applicationContext;

    @Resource
    private SysCampaignService sysCampaignService;

    public boolean execute(TaskObject[] tasks) throws Exception {

        // 调度策略
        for (TaskObject taskObject : tasks) {
            // 针对不同用户的任务
            AppContext.setUser(taskObject.getUserName());
            BaiduAccountInfoEntity accountInfoEntity = taskObject.getAccount();

            List<BiddingRuleEntity> biddingRuleEntityList = taskObject.getList();

            ServiceFactory service = ServiceFactory.getInstance(accountInfoEntity.getBaiduUserName(), accountInfoEntity.getBaiduPassword(), accountInfoEntity.getToken(), null);

            Map<Integer, List<KeywordEntity>> regionKeywordMap = new HashMap<>();
            for (BiddingRuleEntity biddingRuleEntity : biddingRuleEntityList) {

                KeywordEntity keywordEntity = sysKeywordService.findById(biddingRuleEntity.getKeywordId());
                if (keywordEntity == null) {
                    continue;
                }

                //关键词状态为42 45 46时, 不进行竞价
                Integer keywordStatus = keywordEntity.getStatus();  //KeywordStatusEnum
                if (keywordStatus == KeywordStatusEnum.STATUS_PAUSE.getVal()
                        || keywordStatus == KeywordStatusEnum.STATUS_WAIT.getVal()
                        || keywordStatus == KeywordStatusEnum.STATUS_AUDITING.getVal()) {
                    continue;
                }

                //判断该关键词所在的单元或是计划是否处于暂停状态
                boolean isPause = keywordEntity.getPause();
                if (!isPause) {
                    isPause = sysAdgroupService.findByAdgroupId(keywordEntity.getAdgroupId()).getPause();
                    if (!isPause) {
                        isPause = sysCampaignService.findByKeywordId(keywordEntity.getKeywordId()).getPause();
                    }
                }
                if (isPause) {
                    continue;
                }

                //获取竞价区域
                Integer[] regionList = biddingRuleEntity.getStrategyEntity().getRegionTarget();
                if (regionList == null) {
                    CampaignEntity campaignEntity = sysCampaignService.findByKeywordId(keywordEntity.getKeywordId());
                    if (campaignEntity.getRegionTarget() != null && !campaignEntity.getRegionTarget().isEmpty()) {
                        regionList = campaignEntity.getRegionTarget().toArray(new Integer[]{});
                    } else {
                        regionList = accountInfoEntity.getRegionTarget().toArray(new Integer[]{});
                    }
                }
                // 根据竞价区域归类关键词
                for (Integer region : regionList) {
                    if (!regionKeywordMap.containsKey(region)) {
                        regionKeywordMap.put(region, new ArrayList<KeywordEntity>());
                    }
                    regionKeywordMap.get(region).add(keywordEntity);
                }
            }


            for (Map.Entry<Integer, List<KeywordEntity>> entry : regionKeywordMap.entrySet()) {
                Integer region = entry.getKey();

                List<KeywordEntity> kwList = entry.getValue();
                if (kwList.isEmpty()) {
                    continue;
                }

                List<KeywordEntity> tmpList = new ArrayList<>(5);
                for (int i = 0; i < kwList.size(); i++) {
                    tmpList.add(kwList.get(i));
                    if (tmpList.size() % 5 == 0) {
                        tmpList.clear();
                    }
                }

                if (!tmpList.isEmpty()) {
                    runBiddingSubTask(taskObject,region,accountInfoEntity,tmpList,service);
                    tmpList.clear();
                }

            }

        }
        return true;
    }

    private void runBiddingSubTask(TaskObject taskObject, Integer region, BaiduAccountInfoEntity accountInfoEntity,
                                   List<KeywordEntity> tmpList, CommonService service) {
        BiddingSubTask biddingSubTask = (BiddingSubTask) applicationContextHelper.getBeanByClass(BiddingSubTask.class);
        biddingSubTask.setAccountName(taskObject.getUserName());
        biddingSubTask.setAccountInfoEntity(accountInfoEntity);
        biddingSubTask.setRegion(region);
        biddingSubTask.setKeywordEntityList(new ArrayList<KeywordEntity>(tmpList));
        biddingSubTask.setService(service);
        executors.execute(biddingSubTask);
    }

    public List<TaskObject> selectTasks() throws Exception {

        Iterable<SystemUserEntity> userEntityList = systemUserService.getAllUser();

        List<TaskObject> objectList = new ArrayList<>();

        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        for (SystemUserEntity userEntity : userEntityList) {
//            if (userEntity.getAccess().compareTo(1) == 0) {
//                continue;
//            }
            List<BaiduAccountInfoEntity> accountInfoEntityList = userEntity.getBaiduAccountInfoEntities();
            for (BaiduAccountInfoEntity baiduAccountInfoEntity : accountInfoEntityList) {
                List<BiddingRuleEntity> biddingRuleEntityList = biddingRuleService.getTaskByAccountId(userEntity.getUserName(), baiduAccountInfoEntity.getId(), timeInMillis);

                if (biddingRuleEntityList != null && !biddingRuleEntityList.isEmpty()) {
                    objectList.add(new TaskObject(userEntity.getUserName(), baiduAccountInfoEntity, biddingRuleEntityList));
                }
            }
        }

        if (objectList.isEmpty()) {
            if (logger.isInfoEnabled()) {
                logger.info("当前暂无可执行的竞价规则!");
            }
            return Collections.EMPTY_LIST;
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


    public void work() throws JobExecutionException {
        try {
            List<TaskObject> taskObjects = selectTasks();

            execute(taskObjects.toArray(new TaskObject[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
