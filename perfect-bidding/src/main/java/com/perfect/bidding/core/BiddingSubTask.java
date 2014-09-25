package com.perfect.bidding.core;

import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduSpiderHelper;
import com.perfect.autosdk.sms.v3.KeywordType;
import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.constants.BiddingStrategyConstants;
import com.perfect.core.AppContext;
import com.perfect.dto.CreativeDTO;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.bidding.BiddingLogEntity;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.entity.bidding.StrategyEntity;
import com.perfect.service.BiddingLogService;
import com.perfect.service.BiddingRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/24.
 */
public class BiddingSubTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(BiddingSubTask.class);

    private static final BigDecimal FAST_PRICE = BigDecimal.ONE.divide(BigDecimal.ONE.add(BigDecimal.ONE));

    private static final BigDecimal ECON_PRICE = BigDecimal.valueOf(0.05);


    private final String keyword;
    private final Integer[] regionList;
    private BiddingRuleEntity biddingRuleEntity;
    private KeywordEntity keywordEntity;
    private BaiduApiService apiService;
    private String host;
    private String userName;

    public BiddingSubTask(String keyword, Integer[] regionList) {

        this.keyword = keyword;
        this.regionList = regionList;
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        AppContext.setUser(userName);

        BiddingLogService biddingLogService = (BiddingLogService) ApplicationContextHelper.getBeanByName
                ("biddingLogService");

        for (Integer region : regionList) {
            BigDecimal currentPrice = biddingRuleEntity.getStrategyEntity().getMinPrice();
            if (currentPrice == null) {
                currentPrice = BigDecimal.ZERO;
            }
            while (true) {

                List<BaiduSpiderHelper.PreviewData> datas = BaiduSpiderHelper.crawl(keyword, region);

                if (datas.isEmpty()) {
                    sleep(1000);
                    continue;
                }

                BaiduSpiderHelper.PreviewData previewData = datas.get(0);

                if ((previewData.getLeft() == null || previewData.getLeft().isEmpty()) && (previewData.getRight() == null || previewData.getRight().isEmpty())) {
                    sleep(1000);
                    continue;
                }

                StrategyEntity strategyEntity = biddingRuleEntity.getStrategyEntity();

                int pos = strategyEntity.getExpPosition();
                List<CreativeDTO> leftList = previewData.getLeft();
                List<CreativeDTO> rightList = previewData.getRight();
                if (leftList.isEmpty() && rightList.isEmpty()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("暂未获取到任何排名信息");
                    }
                    continue;
                } else {
                    //保存到ES数据库

                }

                int rank = 0;
                for (CreativeDTO leftDTO : leftList) {
                    if (("." + leftDTO.getUrl()).contains(host)) {
                        rank = leftList.indexOf(leftDTO) + 1;
                        break;
                    }
                }

                if (rank == 0) {
                    for (CreativeDTO rightDTO : rightList) {
                        if (("." + rightDTO.getUrl()).contains(host)) {
                            rank = -1 * rightList.indexOf(rightDTO) - 1;
                            break;
                        }
                    }
                }

                boolean match = false;
                if (pos == BiddingStrategyConstants.POS_LEFT_1.value()) {
                    match = (rank == 1);
                } else if (pos == BiddingStrategyConstants.POS_LEFT_2_3.value()) {
                    match = (rank == 2);
                } else if (pos == BiddingStrategyConstants.POS_RIGHT_1_3.value()) {
                    match = (rank == -1);
                } else if (pos == BiddingStrategyConstants.POS_RIGHT_OTHERS.value()) {
                    match = (rank == -1 * strategyEntity.getExpPosition());
                }

                // 已经达到排名
                if (match) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("达到排名..." + region + " " + host + " " + keywordEntity.getKeyword());
                    }
                    break;
                } else {
                    // 竞价日志
                    BiddingLogEntity biddingLogEntity = new BiddingLogEntity();
                    biddingLogEntity.setKeywordId(keywordEntity.getKeywordId());
                    biddingLogEntity.setDate(System.currentTimeMillis());

                    if (currentPrice.compareTo(strategyEntity.getMinPrice()) == -1) {
                        currentPrice = strategyEntity.getMinPrice();
                        biddingLogEntity.setBefore(keywordEntity.getPrice());
                        biddingLogEntity.setAfter(currentPrice);
                    } else {
                        biddingLogEntity.setBefore(currentPrice);
                        if (strategyEntity.getMode() == BiddingStrategyConstants.SPD_FAST.value()) {
                            currentPrice = currentPrice.add(FAST_PRICE);
                        } else {
                            currentPrice = currentPrice.add(ECON_PRICE);
                        }
                        biddingLogEntity.setAfter(currentPrice);

                    }

                    if (currentPrice.compareTo(strategyEntity.getMaxPrice()) == 1) {

                        if (logger.isDebugEnabled()) {
                            logger.debug("未达到排名..." + host + " " + keywordEntity.getKeyword() + "\n出价已超过最大价格!");
                        }
                        int failed = strategyEntity.getFailedStrategy();
                        if (failed == BiddingStrategyConstants.FAILED_KEEP.value()) {
                            // 保持当前排名
                            if (logger.isDebugEnabled()) {
                                logger.debug("根据竞价失败规则..." + host + " " + keywordEntity.getKeyword() + "\n保持当前排名" +
                                        ".");
                            }
                        } else if (failed == BiddingStrategyConstants.FAILED_ROLLBACK.value()) {
                            //竞价失败 恢复之前的原始竞价
                            if (logger.isDebugEnabled()) {
                                logger.debug("根据竞价失败规则..." + host + " " + keywordEntity.getKeyword() +
                                        "\n恢复原始竞价价格.");
                            }
                            KeywordType keywordType = new KeywordType();
                            keywordType.setKeywordId(keywordEntity.getKeywordId());
                            keywordType.setPrice(keywordEntity.getPrice().doubleValue());
                            apiService.setKeywordPrice(keywordType);

                            biddingLogEntity.setAfter(BigDecimal.valueOf(keywordType.getPrice()));
                            biddingLogService.save(biddingLogEntity);

                        }
                        break;
                    } else {
                        // 正常流程
                        KeywordType keywordType = new KeywordType();
                        keywordType.setKeywordId(keywordEntity.getKeywordId());
                        keywordType.setPrice(currentPrice.doubleValue());
                        apiService.setKeywordPrice(keywordType);

                        biddingLogService.save(biddingLogEntity);

                        if (logger.isDebugEnabled()) {
                            logger.debug("未达到排名..." + host + "\n出价完成,1秒后进行下一次出价.");
                        }
                    }
                }
            }

        }

        done(biddingRuleEntity);
    }

    public void done(BiddingRuleEntity biddingRuleEntity) {

        // 计算下次竞价执行时间
        StrategyEntity strategyEntity = biddingRuleEntity.getStrategyEntity();

        if (strategyEntity.getAuto() == 1) {
            // 单次竞价, 检查竞价次数
            int runTimes = strategyEntity.getRunByTimes();
            if (runTimes == -1) {
                // 每天执行
                Integer[] times = strategyEntity.getTimes();
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                long next = -1;
                for (int i = 0; i < times.length; i++) {
                    int time = times[i++];
                    if (time > hour) {
                        // 第一个大于当前时间的节点
                        calendar.set(Calendar.HOUR_OF_DAY, time);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        next = calendar.getTime().getTime();
                        break;
                    }
                }

                if (next == -1) {
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, times[0]);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    next = calendar.getTime().getTime();
                }

                biddingRuleEntity.setNext(next);
            } else {
                // 按照次数
                int currentTime = biddingRuleEntity.getCurrentTimes();
                if (currentTime != -1)
                    biddingRuleEntity.setCurrentTimes(--currentTime);
            }

        } else {
            // 重复竞价
            int interval = strategyEntity.getInterval();
            long nextTime = nextTime(strategyEntity.getTimes(), interval);

            biddingRuleEntity.setNext(nextTime);
        }


        biddingRuleEntity.setRunning(false);

        BiddingRuleService biddingRuleService = (BiddingRuleService) ApplicationContextHelper.getBeanByName("biddingRuleService");
        biddingRuleService.updateRule(biddingRuleEntity);
        biddingRuleEntity.setId(null);
    }

    private static long nextTime(Integer[] times, int interval) {

        long nextRunTime = System.currentTimeMillis() + interval * 60 * 1000;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(nextRunTime);

        int nextHour = cal.get(Calendar.HOUR_OF_DAY);
        long nextTime = -1;

        int[] dayOfHous = new int[24];
        Arrays.fill(dayOfHous, 0);
        // 获取运行区间
        for (int i = 0, len = times.length; i < len; ) {
            int start = times[i++];
            int end = times[i++];
            Arrays.fill(dayOfHous, start, end + 1, 1);
            if (start <= nextHour && nextHour <= end) {
                nextTime = nextRunTime;
                break;
            }
        }

        // 当前时间不在运行区间,取最近的下一个区间
        if (nextTime == -1) {
            for (int i = nextHour; i < dayOfHous.length; i++) {
                if (dayOfHous[i] == 1) {
                    cal.set(Calendar.HOUR_OF_DAY, i);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    nextTime = cal.getTimeInMillis();
                    break;
                }
            }
        }

        // 上一次寻找无时间段,从头开始查找
        if (nextTime == -1) {
            for (int i = 0; i < dayOfHous.length; i++) {
                if (dayOfHous[i] == 1) {
                    cal.add(Calendar.DAY_OF_YEAR, 1);
                    cal.set(Calendar.HOUR_OF_DAY, i);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    nextTime = cal.getTimeInMillis();
                    break;
                }
            }
        }

        return nextTime;
    }

    public void setBiddingRuleEntity(BiddingRuleEntity biddingRuleEntity) {
        this.biddingRuleEntity = biddingRuleEntity;
    }

    public BiddingRuleEntity getBiddingRuleEntity() {
        return biddingRuleEntity;
    }

    public void setKeywordEntity(KeywordEntity keywordEntity) {
        this.keywordEntity = keywordEntity;
    }

    public KeywordEntity getKeywordEntity() {
        return keywordEntity;
    }

    public void setApiService(BaiduApiService apiService) {
        this.apiService = apiService;
    }

    public BaiduApiService getApiService() {
        return apiService;
    }

    public void setHost(String host) {
        this.host = "." + host;
    }

    public String getHost() {
        return host;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
