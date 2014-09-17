package com.perfect.schedule.task.execute;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.sms.v3.KeywordType;
import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.constants.BiddingStrategyConstants;
import com.perfect.core.AppContext;
import com.perfect.dto.CreativeDTO;
import com.perfect.elasticsearch.threads.EsRunnable;
import com.perfect.elasticsearch.threads.EsThreadPoolTaskExecutor;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.bidding.BiddingLogEntity;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.entity.bidding.StrategyEntity;
import com.perfect.service.*;
import com.perfect.service.impl.HTMLAnalyseServiceImpl;
import com.perfect.utils.BiddingRuleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\  =  /O
 * ____/`---'\____
 * .'  \\|     |//  `.
 * /  \\|||  :  |||//  \
 * /  _||||| -:- |||||-  \
 * |   | \\\  -  /// |   |
 * | \_|  ''\---/''  |_/ |
 * \  .-\__  '-'  ___/-. /
 * __'. .'  /--.--\  `. .' __
 * ."" '<  `.___\_<|>_/___.'  >'"".
 * | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * \  \ `-.   \_ __\ /__ _/   .-` /  /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * 佛祖保佑       永无BUG
 * <p/>
 * Created by yousheng on 2014/8/19.
 *
 * @author yousheng
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BiddingSubTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(BiddingSubTask.class);
    private static final BigDecimal FAST_PRICE = BigDecimal.ONE.divide(BigDecimal.ONE.add(BigDecimal.ONE));

    private static final BigDecimal ECON_PRICE = BigDecimal.valueOf(0.05);

    public static final int RETRY = 5;
    private String accountName;

    private BaiduAccountInfoEntity accountInfoEntity;
    private List<KeywordEntity> keywordEntityList;

    private EsThreadPoolTaskExecutor executor;

    @Resource
    private BiddingLogService biddingLogService;

    private CommonService service;

    private BiddingRuleService biddingRuleService;

    private SysAdgroupService adgroupService;

    private ApplicationContextHelper applicationContextHelper;
    private Integer region;

    @Override
    public void run() {
        AppContext.setUser(accountName);
        if (logger.isInfoEnabled()) {
            logger.info("正在执行竞价..." + keywordEntityList);
        }

        String host = accountInfoEntity.getRegDomain();
        BaiduApiService apiService = new BaiduApiService(service);
        HTMLAnalyseService htmlService = HTMLAnalyseServiceImpl.createService(service);

        List<String> kwName = new ArrayList<>();
        Map<String, KeywordEntity> nameMap = new HashMap<>();
        Map<String, BiddingRuleEntity> ruleMap = new HashMap<>();


        for (KeywordEntity keywordEntity : keywordEntityList) {
            kwName.add(keywordEntity.getKeyword());
            nameMap.put(keywordEntity.getKeyword(), keywordEntity);

            BiddingRuleEntity biddingRuleEntity = biddingRuleService.findByKeywordId(keywordEntity.getKeywordId());
            ruleMap.put(keywordEntity.getKeyword(), biddingRuleEntity);

            StrategyEntity strategyEntity = biddingRuleEntity.getStrategyEntity();


            int interval = strategyEntity.getInterval();
            Date nextRun = null;


            if (interval >= 60) {
                nextRun = BiddingRuleUtils.getDateInvHour(strategyEntity.getTimes(), interval);
            } else if (interval > 0 && interval < 60) {
                nextRun = BiddingRuleUtils.getDateInvMinute(strategyEntity.getTimes(), interval);
            } else if (interval == -1) {

                //第一次执行竞价策略
                long nextTime = BiddingRuleUtils.getNextHourTime(strategyEntity.getTimes());
                biddingRuleEntity.setNext(nextTime);
            }

            if (nextRun != null) {
                if (nextRun.after(Calendar.getInstance().getTime())) {
                    if (biddingRuleEntity.getNext() != nextRun.getTime())
                        biddingRuleEntity.setNext(nextRun.getTime());
                } else {
                    biddingRuleEntity.setEnabled(false);
                }
            }
            int currentTime = biddingRuleEntity.getCurrentTimes();
            if (currentTime != -1)
                biddingRuleEntity.setCurrentTimes(--currentTime);

            biddingRuleEntity.setRunning(true);
            biddingRuleService.updateRule(biddingRuleEntity);
        }


        if (logger.isInfoEnabled()) {
            logger.info("竞价地域..." + region);
        }

        int retry = RETRY;
        while (!keywordEntityList.isEmpty()) {
            List<HTMLAnalyseServiceImpl.PreviewData> datas = htmlService.getPageData(kwName.toArray(new String[]{}),
                    region);

            if (datas.isEmpty()) {
                try {
                    Thread.sleep(1000);
                    if (retry-- == 0) {
                        break;
                    } else {
                        continue;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            List<KeywordType> typeList = new ArrayList<>();
            Map<KeywordType, BiddingLogEntity> typeLog = new HashMap<>();

            for (HTMLAnalyseServiceImpl.PreviewData previewData : datas) {

                if ((previewData.getLeft() == null || previewData.getLeft().isEmpty()) && (previewData.getRight() == null || previewData.getRight().isEmpty())) {
                    continue;
                }
                BiddingRuleEntity biddingRuleEntity = ruleMap.get(previewData.getKeyword());
                StrategyEntity strategyEntity = biddingRuleEntity.getStrategyEntity();

                int pos = strategyEntity.getExpPosition();
                List<CreativeDTO> leftList = previewData.getLeft();
                List<CreativeDTO> rightList = previewData.getRight();
                if (leftList.isEmpty() && rightList.isEmpty()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("暂未获取到任何排名信息");
                    }
                    try {
                        Thread.sleep(10000);
                        if (retry-- == 0) {
                            break;
                        } else {
                            continue;
                        }
                    } catch (InterruptedException e) {
                        if (logger.isErrorEnabled()) {
                            logger.error("InterruptedException", e);
                        }
                    }
                } else {
                    //保存到ES数据库
                    List<CreativeDTO> list = new ArrayList<>();
                    list.addAll(leftList);
                    list.addAll(rightList);

                    try {
                        EsRunnable esRunnable = (EsRunnable) applicationContextHelper.getBeanByClass(EsRunnable.class);
                        esRunnable.setKeyword(previewData.getKeyword());
                        esRunnable.setRegion(region);
                        esRunnable.setList(list);
                        executor.execute(esRunnable);
                    } catch (Exception e) {
                        if (logger.isErrorEnabled()) {
                            logger.error("ES error", e);
                        }
                    }
                }

                int rank = 0;
                try {
                    URL url = new URL("http", host, "");
                    for (CreativeDTO leftDTO : leftList) {
                        if (url.sameFile(new URL("http", leftDTO.getUrl(), ""))) {
                            rank = leftList.indexOf(leftDTO) + 1;
                            break;
                        }
                    }

                    if (rank == 0) {
                        for (CreativeDTO rightDTO : rightList) {
                            if (url.sameFile(new URL("http", rightDTO.getUrl(), ""))) {
                                rank = -1 * rightList.indexOf(rightDTO) - 1;
                                break;
                            }
                        }
                    }
                } catch (MalformedURLException e) {
                    if(logger.isErrorEnabled()){
                        logger.error("MalformedURLException",e);
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

                KeywordEntity keywordEntity = nameMap.get(previewData.getKeyword());
                if (logger.isDebugEnabled()) {
                    logger.debug("当前排名: " + host + " " + keywordEntity + " " + ((rank == 0) ? "暂无当前排名" : rank));
                }
                // 已经达到排名
                if (match) {

                    keywordEntityList.remove(keywordEntity);
                    nameMap.remove(keywordEntity.getKeyword());
                    ruleMap.remove(keywordEntity.getKeyword());
                    // 重复竞价 TODO
                    if (strategyEntity.getInterval() == -1) {
                        KeywordType keywordType = new KeywordType();
                        keywordType.setKeywordId(keywordEntity.getKeywordId());
                    }

                    if (logger.isDebugEnabled()) {
                        logger.debug("达到排名..." + host + " " + keywordEntity.getKeyword() + "\n下次启动时间: " + new Date
                                (biddingRuleEntity.getNext()));
                    }
                    biddingRuleEntity.setCurrentPrice(keywordEntity.getPrice());

                    break;
                } else {
                    // 出价策略
                    BigDecimal currentPrice = biddingRuleEntity.getCurrentPrice();

                    if (currentPrice == null) {
                        currentPrice = BigDecimal.ZERO;
                    }
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


                    if (logger.isDebugEnabled()) {
                        logger.debug("未达到排名..." + host + "\n最新出价: " + currentPrice);
                    }

                    if (currentPrice.compareTo(strategyEntity.getMaxPrice()) == 1) {

                        keywordEntityList.remove(keywordEntity);
                        nameMap.remove(keywordEntity.getKeyword());
                        ruleMap.remove(keywordEntity.getKeyword());

                        if (logger.isDebugEnabled()) {
                            logger.debug("未达到排名..." + host + "\n出价已超过最大价格!");
                        }
                        int failed = strategyEntity.getFailedStrategy();
                        if (failed == BiddingStrategyConstants.FAILED_KEEP.value()) {
                            // 保持当前排名
                            if (logger.isDebugEnabled()) {
                                logger.debug("未达到排名..." + host + "\n保持当前排名.");
                            }
                            keywordEntityList.remove(keywordEntity);
                            continue;
                        } else if (failed == BiddingStrategyConstants.FAILED_ROLLBACK.value()) {
                            //竞价失败 恢复之前的原始竞价
                            if (logger.isDebugEnabled()) {
                                logger.debug("未达到排名..." + host + "\n恢复原始竞价价格.");
                            }

                            KeywordType keywordType = new KeywordType();
                            keywordType.setKeywordId(keywordEntity.getKeywordId());
                            keywordType.setPrice(keywordEntity.getPrice().doubleValue());
                            typeList.add(keywordType);
//                            apiService.setKeywordPrice(keywordType);

                            biddingRuleEntity.setCurrentPrice(keywordEntity.getPrice());
                            biddingRuleService.updateRule(biddingRuleEntity);

                            biddingLogEntity.setAfter(BigDecimal.valueOf(keywordType.getPrice()));

                            typeLog.put(keywordType, biddingLogEntity);

                            keywordEntityList.remove(keywordEntity);
                            continue;
                        }
                        break;
                    } else {
                        // 正常流程
                        KeywordType keywordType = new KeywordType();
                        keywordType.setKeywordId(keywordEntity.getKeywordId());
                        keywordType.setPrice(currentPrice.doubleValue());
                        biddingRuleEntity.setCurrentPrice(currentPrice);

                        typeList.add(keywordType);

//                        apiService.setKeywordPrice(keywordType);
                        typeLog.put(keywordType, biddingLogEntity);

                        biddingRuleService.updateRule(biddingRuleEntity);

                        if (logger.isDebugEnabled()) {
                            logger.debug("未达到排名..." + host + "\n出价完成,十秒后进行下一次出价.");
                        }

                        try {
                            //竞价完成一个阶段
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            // 发送竞价请求
            List<KeywordType> returnList = apiService.setKeywordPrice(typeList);
            for (KeywordType returnType : returnList) {
                if (typeLog.containsKey(returnType)) {
                    biddingLogService.save(typeLog.get(returnType));
                }
            }

        }

    }


    public long computNext(StrategyEntity strategyEntity) {

        return 0l;
    }

//    private boolean innerBidding(BiddingRuleEntity biddingRuleEntity, GetPreviewRequest request) {
//        List<HTMLAnalyseServiceImpl.PreviewData> datas = service.getPageData(request);
//
//        if (datas.isEmpty()) {
//            return true;
////            try {
////                Thread.sleep(1000);
////                if (retry-- == 0) {
////                    break;
////                } else {
////                    continue;
////                }
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//        }
//        HTMLAnalyseServiceImpl.PreviewData previewData = datas.get(0);
//        // 解析实况页面数据
//
//        if ((previewData.getLeft() == null || previewData.getLeft().isEmpty()) && (previewData.getRight() == null || previewData.getRight().isEmpty())) {
//            return true;
//        }
//
//
//        StrategyEntity strategyEntity = biddingRuleEntity.getStrategyEntity();
//        int pos = strategyEntity.getExpPosition();
//        List<CreativeDTO> leftList = previewData.getLeft();
//        List<CreativeDTO> rightList = previewData.getRight();
//        CreativeDTO creativeDTO = null;
//
//        if (pos == BiddingStrategyConstants.POS_LEFT_1.value() && leftList.size() > 0) {
//            creativeDTO = leftList.get(0);
//        } else if (pos == BiddingStrategyConstants.POS_LEFT_2_3.value() && leftList.size() > 1) {
//            creativeDTO = leftList.get(1);
//        } else if (pos == BiddingStrategyConstants.POS_RIGHT_1_3.value() && rightList.size() > 1) {
//            creativeDTO = rightList.get(0);
//        } else if (pos == BiddingStrategyConstants.POS_RIGHT_OTHERS.value() && rightList.size() > 3) {
//            creativeDTO = rightList.get(3);
//        }
//
//        String url = creativeDTO.getUrl();
//        // 已经达到排名
//        if (url.equals(host)) {
//
//            // 单次竞价或者重复竞价
//            Date time = BiddingRuleUtils.getDateInvMinute(strategyEntity.getTimes(), -1);
//            biddingRuleEntity.setNext(time.getTime());
//            biddingRuleService.updateRule(biddingRuleEntity);
//
//            if (logger.isDebugEnabled()) {
//                logger.debug("达到排名..." + host + "\n下次启动时间: " + time);
//            }
//            return false;
//        } else {
//            // 出价策略
//            BigDecimal currentPrice = biddingRuleEntity.getCurrentPrice();
//
//            // 竞价日志
//            BiddingLogEntity biddingLogEntity = new BiddingLogEntity();
//            biddingLogEntity.setKeywordId(keywordEntityList.getKeywordId());
//            biddingLogEntity.setDate(System.currentTimeMillis());
//
//            if (currentPrice.compareTo(strategyEntity.getMinPrice()) == -1) {
//                currentPrice = strategyEntity.getMinPrice();
//                biddingLogEntity.setBefore(keywordEntityList.getPrice());
//                biddingLogEntity.setAfter(currentPrice);
//            } else {
//                biddingLogEntity.setBefore(currentPrice);
//                if (strategyEntity.getMode() == BiddingStrategyConstants.SPD_FAST.value()) {
//                    currentPrice = currentPrice.add(FAST_PRICE);
//                } else {
//                    currentPrice = currentPrice.add(ECON_PRICE);
//                }
//                biddingLogEntity.setAfter(currentPrice);
//
//            }
//
//            if (logger.isDebugEnabled()) {
//                logger.debug("未达到排名..." + host + "\n最新出价: " + currentPrice);
//            }
//
//            if (currentPrice.compareTo(strategyEntity.getMaxPrice()) == 1) {
//
//                if (logger.isDebugEnabled()) {
//                    logger.debug("未达到排名..." + host + "\n出价以超过最大价格!");
//                }
//                int failed = strategyEntity.getFailedStrategy();
//                if (failed == BiddingStrategyConstants.FAILED_KEEP.value()) {
//                    // 保持当前排名
//                    if (logger.isDebugEnabled()) {
//                        logger.debug("未达到排名..." + host + "\n保持当前排名.");
//                    }
//                } else if (failed == BiddingStrategyConstants.FAILED_ROLLBACK.value()) {
//                    //竞价失败 恢复之前的原始竞价
//                    if (logger.isDebugEnabled()) {
//                        logger.debug("未达到排名..." + host + "\n恢复原始竞价名.");
//                    }
//
//                    KeywordType keywordType = new KeywordType();
//                    keywordType.setKeywordId(keywordEntityList.getKeywordId());
//                    keywordType.setPrice(keywordEntityList.getPrice().doubleValue());
//
//                    biddingRuleEntity.setCurrentPrice(keywordEntityList.getPrice());
//                    apiService.setKeywordPrice(keywordType);
//                    biddingRuleService.updateRule(biddingRuleEntity);
//                }
//                return false;
//            } else {
//                // 正常流程
//                KeywordType keywordType = new KeywordType();
//                keywordType.setKeywordId(keywordEntityList.getKeywordId());
//                keywordType.setPrice(currentPrice.doubleValue());
//                biddingRuleEntity.setCurrentPrice(currentPrice);
//                apiService.setKeywordPrice(keywordType);
//                biddingRuleService.updateRule(biddingRuleEntity);
//
//                if (logger.isDebugEnabled()) {
//                    logger.debug("未达到排名..." + host + "\n出价完成,十秒后进行下一次出价.");
//                }
//
//                try {
//                    //竞价完成一个阶段
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return true;
//            }
//        }
//    }

    public BiddingLogService getBiddingLogService() {
        return biddingLogService;
    }

    public void setBiddingLogService(BiddingLogService biddingLogService) {
        this.biddingLogService = biddingLogService;
    }

    public EsThreadPoolTaskExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(EsThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }

    public void setBiddingRuleService(BiddingRuleService biddingRuleService) {
        this.biddingRuleService = biddingRuleService;
    }

    public BiddingRuleService getBiddingRuleService() {
        return biddingRuleService;
    }

    public void setAdgroupService(SysAdgroupService adgroupService) {
        this.adgroupService = adgroupService;
    }

    public SysAdgroupService getAdgroupService() {
        return adgroupService;
    }

    public void setService(CommonService service) {
        this.service = service;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAccountInfoEntity(BaiduAccountInfoEntity accountInfoEntity) {
        this.accountInfoEntity = accountInfoEntity;
    }

    public void setKeywordEntityList(List<KeywordEntity> keywordEntity) {
        this.keywordEntityList = keywordEntity;
    }

    public void setApplicationContextHelper(ApplicationContextHelper applicationContextHelper) {
        this.applicationContextHelper = applicationContextHelper;
    }

    public ApplicationContextHelper getApplicationContextHelper() {
        return applicationContextHelper;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public Integer getRegion() {
        return region;
    }

//    public void setEsRunnableFactory(EsRunnableFactory esRunnableFactory) {
//        this.esRunnableFactory = esRunnableFactory;
//    }
}
