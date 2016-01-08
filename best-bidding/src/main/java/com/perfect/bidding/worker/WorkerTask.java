package com.perfect.bidding.worker;

import com.perfect.api.baidu.BaiduApiService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.KeywordType;
import com.perfect.bidding.core.BaiduApiWorker;
import com.perfect.bidding.core.BaiduApiWorkerExecutor;
import com.perfect.bidding.redis.Constants;
import com.perfect.bidding.redis.JRedisPools;
import com.perfect.commons.bdlogin.BaiduSearchPageUtils;
import com.perfect.commons.constants.BiddingStrategyConstants;
import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.core.AppContext;
import com.perfect.dto.CookieDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.bidding.BiddingRuleDTO;
import com.perfect.dto.bidding.StrategyDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.service.AccountManageService;
import com.perfect.service.BiddingRuleService;
import com.perfect.service.CookieService;
import com.perfect.service.KeywordService;
import com.perfect.utils.json.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by vbzer_000 on 2014/9/24.
 */
public class WorkerTask implements Runnable, Constants {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerTask.class);

    private static final BigDecimal FAST_PRICE = BigDecimal.ONE.divide(BigDecimal.ONE.add(BigDecimal.ONE));

    private static final BigDecimal ECON_PRICE = BigDecimal.valueOf(0.05);

    private Worker worker;

    public WorkerTask(Worker worker) {
        this.worker = worker;
    }

    @Override
    public void run() {

        while (true) {

            String task = worker.popTask();
            if (task != null) {
                String params[] = task.split(JOB_SEP);

                AppContext.setUser(params[0], Long.valueOf(params[1]));

                BiddingRuleService biddingRuleService = (BiddingRuleService) ApplicationContextHelper
                        .getBeanByName("biddingRuleService");
                CookieService cookieService = (CookieService) ApplicationContextHelper
                        .getBeanByName("cookieService");
                AccountManageService accountManageService = (AccountManageService) ApplicationContextHelper
                        .getBeanByName("accountManageService");
                KeywordService keywordService = (KeywordService) ApplicationContextHelper
                        .getBeanByName("keywordService");

                ModuleAccountInfoDTO baiduAccountInfoDTO = accountManageService.getBaiduAccountInfoById(Long.valueOf(params[1]));
                String host = baiduAccountInfoDTO.getRegDomain();
                List<Integer> regionList = baiduAccountInfoDTO.getRegionTarget();

                BiddingRuleDTO biddingRuleDTO = biddingRuleService.takeOneById(params[0], params[2]);
                String keyword = biddingRuleDTO.getKeyword();

                BaiduApiService baiduApiService = null;
                KeywordDTO keywordDTO = keywordService.findOne(biddingRuleDTO.getKeywordId());

                try {
                    baiduApiService = new BaiduApiService(ServiceFactory.getInstance(baiduAccountInfoDTO.getBaiduUserName(), baiduAccountInfoDTO.getBaiduPassword(), baiduAccountInfoDTO.getToken(), null));
                } catch (ApiException e) {
                    e.printStackTrace();
                }

                for (Integer region : regionList) {
                    BigDecimal currentPrice = biddingRuleDTO.getStrategyDTO().getMinPrice();
                    if (currentPrice == null) {
                        currentPrice = BigDecimal.ZERO;
                    }

                    Long accountId = biddingRuleDTO.getAccountId();
                    Long keywordId = biddingRuleDTO.getKeywordId();

                    // retrieve cookie
                    CookieDTO cookieDTO;
                    while (true) {
                        cookieDTO = cookieService.takeOne();
                        if (cookieDTO == null) {
                            sleep(1);
                            continue;
                        }
                        break;
                    }

                    // 竞价逻辑
                    {
                        while (true) {
                            if (biddingRuleService.isPause(accountId, keywordId))
                                break;

                            StrategyDTO strategyDTO = biddingRuleDTO.getStrategyDTO();

                            int pos = strategyDTO.getExpPosition();

                            String html = BaiduSearchPageUtils.getBaiduSearchPage(cookieDTO.getCookie(), keyword, region);
                            if (html.length() < 200) {
                                if (LOGGER.isDebugEnabled())
                                    LOGGER.debug("暂未获取到任何排名信息");
                                cookieService.delete(cookieDTO.getId());
                                break;
                            }
                            int rank = BaiduSearchPageUtils.where(html, host);

                            boolean match = false;
                            if (pos == BiddingStrategyConstants.POS_LEFT_1.value()) {
                                match = (rank == 1);
                            } else if (pos == BiddingStrategyConstants.POS_LEFT_2_3.value()) {
                                match = (rank == 2);
                            } else if (pos == BiddingStrategyConstants.POS_RIGHT_1_3.value()) {
                                match = (rank == -1);
                            } else if (pos == BiddingStrategyConstants.POS_RIGHT_OTHERS.value()) {
                                match = (rank == -1 * strategyDTO.getExpPosition());
                            }

                            // 已经达到排名
                            if (match) {
                                if (LOGGER.isDebugEnabled()) {
                                    LOGGER.debug("达到排名..." + region + " " + host + " " + keywordDTO.getKeyword() + "--当前出价: " + keywordDTO.getPrice().doubleValue());
                                }

                                break;
                            } else {
                                // 竞价日志
                                // TODO 竞价日志模块未重构完成
//                    BiddingLogEntity biddingLogEntity = new BiddingLogEntity();
//                    biddingLogEntity.setKeywordId(keywordEntity.getKeywordId());
//                    biddingLogEntity.setDate(System.currentTimeMillis());

                                if (currentPrice.compareTo(strategyDTO.getMinPrice()) == -1) {
                                    currentPrice = strategyDTO.getMinPrice();
//                        biddingLogEntity.setBefore(keywordEntity.getPrice());
//                        biddingLogEntity.setAfter(currentPrice);
                                } else {
//                        biddingLogEntity.setBefore(currentPrice);
                                    if (strategyDTO.getMode() == BiddingStrategyConstants.SPD_FAST.value()) {
                                        currentPrice = currentPrice.add(FAST_PRICE);
                                    } else {
                                        currentPrice = currentPrice.add(ECON_PRICE);
                                    }
//                        biddingLogEntity.setAfter(currentPrice);

                                }

                                if (currentPrice.compareTo(strategyDTO.getMaxPrice()) == 1) {
                                    if (LOGGER.isDebugEnabled()) {
                                        LOGGER.debug("未达到排名..." + host + " " + keywordDTO.getKeyword() + "\n出价已超过最大价格!");
                                    }
                                    int failed = strategyDTO.getFailedStrategy();
                                    if (failed == BiddingStrategyConstants.FAILED_KEEP.value()) {
                                        // 保持当前排名
                                        if (LOGGER.isDebugEnabled()) {
                                            LOGGER.debug("根据竞价失败规则..." + host + " " + keywordDTO.getKeyword() + "\n保持当前排名.");
                                        }
                                    } else if (failed == BiddingStrategyConstants.FAILED_ROLLBACK.value()) {
                                        //竞价失败 恢复之前的原始竞价
                                        if (LOGGER.isDebugEnabled()) {
                                            LOGGER.debug("根据竞价失败规则..." + host + " " + keywordDTO.getKeyword() +
                                                    "\n恢复原始竞价价格.");
                                        }
                                        KeywordType keywordType = new KeywordType();
                                        keywordType.setKeywordId(keywordDTO.getKeywordId());
                                        keywordType.setPrice(keywordDTO.getPrice().doubleValue());

                                        BaiduApiWorker baiduApiWorker = new BaiduApiWorker(baiduApiService, keywordType);
                                        BaiduApiWorkerExecutor.execute(baiduApiWorker);

//                            biddingLogEntity.setAfter(BigDecimal.valueOf(keywordType.getPrice()));
//                            biddingLogService.save(biddingLogEntity);

                                    }
                                    break;
                                } else {
                                    // 正常流程
                                    KeywordType keywordType = new KeywordType();
                                    keywordType.setKeywordId(keywordDTO.getKeywordId());
                                    keywordType.setPrice(currentPrice.doubleValue());
                                    BaiduApiWorker baiduApiWorker = new BaiduApiWorker(baiduApiService, keywordType);
                                    BaiduApiWorkerExecutor.execute(baiduApiWorker);

//                        biddingLogService.save(biddingLogEntity);

                                    if (LOGGER.isDebugEnabled()) {
                                        LOGGER.debug("未达到排名..." + host + "\n出价完成,3秒后进行下一次出价.");
                                    }

                                    sleep(3);
                                }
                            }
                        }

                    }


                    if (cookieDTO != null)
                        cookieService.returnOne(cookieDTO.getId());

                }

                done(biddingRuleDTO);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("竞价规则结束 " + keyword);
                }

                continue;
            }

            sleep(1);
        }
    }

    public void done(BiddingRuleDTO biddingRuleDTO) {

        // 计算下次竞价执行时间
        StrategyDTO strategyEntity = biddingRuleDTO.getStrategyDTO();

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

                biddingRuleDTO.setNext(next);
            } else {
                // 按照次数
                int currentTime = biddingRuleDTO.getCurrentTimes();
                if (currentTime != -1)
                    biddingRuleDTO.setCurrentTimes(--currentTime);
            }

        } else {
            // 重复竞价
            int interval = strategyEntity.getInterval();
            long nextTime = nextTime(strategyEntity.getTimes(), interval);

            biddingRuleDTO.setNext(nextTime);
        }


        biddingRuleDTO.setRunning(false);

//        BiddingRuleService biddingRuleService = (BiddingRuleService) ApplicationContextHelper.getBeanByName("biddingRuleService");
//        biddingRuleService.save(biddingRuleDTO);

        //publish message
        Jedis jedis = null;
        try {
            jedis = JRedisPools.getConnection();
            String message = getMessage(biddingRuleDTO);
            String _channel = worker.getWorkerId() + "$";
            jedis.publish(_channel, message);
        } finally {
            if (jedis != null) {
                JRedisPools.returnJedis(jedis);
            }
        }

        biddingRuleDTO.setId(null);
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

    private String getMessage(Object o) {
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ObjectOutputStream oos = new ObjectOutputStream(baos);
//            oos.writeObject(o);
//            return new String(baos.toByteArray());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
        return JSONUtils.getJsonString(o);
    }

    private void sleep(long time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Worker getWorker() {
        return worker;
    }
}
//                String uuid = UUID.randomUUID().toString();
//                BaiduKeywordScheduler.getInstance().push(uuid, cookieDTO.getRequest(), keyword, region);
//
//                Map<String, Object> pageResult = null;
//                try {
//                    Object val = Container.get(uuid);
//
//                    if (val != null) {
//                        pageResult = (Map<String, Object>) val;
//
//                        // session超时
//                        if (pageResult.containsKey(PAGE_TIMEOUT)) {
//                            if (logger.isDebugEnabled()) {
//                                logger.debug("暂未获取到任何排名信息");
//                            }
//                            cookieService.returnOne(cookieDTO);
//                            continue;
//                        }
//                    } else {
//                        // 页面为空
//                        cookieDTO.setFinishTime(System.currentTimeMillis() + 5000);
//                        cookieService.returnOne(cookieDTO);
//                        continue;
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//
//                StrategyDTO strategyDTO = biddingRuleDTO.getStrategyDTO();
//
//                int pos = strategyDTO.getExpPosition();
//
//TODO 保存到ES数据库
//
//                int rank = 0;
//                LinkedList<Map<String, Object>> leftList = (LinkedList<Map<String, Object>>) pageResult.get(PAGE_LEFT);
//                for (Map<String, Object> leftDTO : leftList) {
//                    if (("." + leftDTO.get(CREATIVE_URL)).contains(host)) {
//                        rank = leftList.indexOf(leftDTO) + 1;
//                        break;
//                    }
//                }
//
//
//                if (rank == 0) {
//                    LinkedList<Map<String, Object>> rightList = (LinkedList<Map<String, Object>>) pageResult.get(PAGE_LEFT);
//                    for (Map<String, Object> rightDTO : rightList) {
//                        if (("." + rightDTO.get(CREATIVE_URL)).contains(host)) {
//                            rank = -1 * rightList.indexOf(rightDTO) - 1;
//                            break;
//                        }
//                    }
//                }