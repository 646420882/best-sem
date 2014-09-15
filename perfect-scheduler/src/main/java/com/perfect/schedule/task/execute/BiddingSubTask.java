package com.perfect.schedule.task.execute;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.sms.v3.GetPreviewRequest;
import com.perfect.autosdk.sms.v3.KeywordType;
import com.perfect.constants.BiddingStrategyConstants;
import com.perfect.core.AppContext;
import com.perfect.dto.CreativeDTO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.CampaignEntity;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.bidding.BiddingLogEntity;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.entity.bidding.StrategyEntity;
import com.perfect.service.*;
import com.perfect.service.impl.HTMLAnalyseServiceImpl;
import com.perfect.utils.BiddingRuleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
public class BiddingSubTask implements Runnable {

    private static final BigDecimal FAST_PRICE = BigDecimal.ONE.divide(BigDecimal.ONE.add(BigDecimal.ONE));
    private static final BigDecimal ECON_PRICE = BigDecimal.ONE.divide(BigDecimal.TEN).divide(BigDecimal.TEN);

    public static final int RETRY = 5;

    private final HTMLAnalyseService service;
    private final String host;
    private String userName;
    private final BaiduApiService apiService;
    private BaiduAccountInfoEntity accountInfoEntity;
    private final BiddingRuleEntity biddingRuleEntity;
    private final KeywordEntity keywordEntity;
    private BiddingRuleService biddingRuleService;
    private final SysCampaignService campaignService;
    private final SysAdgroupService adgroupService;

    private BiddingLogService biddingLogService;

    private Logger logger = LoggerFactory.getLogger(BiddingSubTask.class);

    public BiddingSubTask(String userName, String host, BaiduApiService apiService, BiddingRuleService biddingRuleService, SysCampaignService sysCampaignService, HTMLAnalyseService htmlAnalyseService,
                          BaiduAccountInfoEntity accountInfoEntity, BiddingRuleEntity biddingRuleEntity, KeywordEntity keywordEntity, SysAdgroupService sysAdgroupService) {
        this.userName = userName;
        this.host = host;
        this.apiService = apiService;
        this.biddingRuleService = biddingRuleService;
        this.campaignService = sysCampaignService;
        this.accountInfoEntity = accountInfoEntity;
        this.biddingRuleEntity = biddingRuleEntity;
        this.service = htmlAnalyseService;
        this.keywordEntity = keywordEntity;
        this.adgroupService = sysAdgroupService;
    }

    public BiddingSubTask(String user, String host, CommonService service, BiddingRuleService biddingRuleService, SysCampaignService sysCampaignService, BaiduAccountInfoEntity accountInfoEntity, BiddingRuleEntity biddingRuleEntity, KeywordEntity keywordEntity, SysAdgroupService sysAdgroupService) {

        this(user, host, new BaiduApiService(service), biddingRuleService, sysCampaignService, HTMLAnalyseServiceImpl.createService(service), accountInfoEntity, biddingRuleEntity, keywordEntity, sysAdgroupService);
    }

    @Override
    public void run() {
        AppContext.setUser(userName);
        if (logger.isInfoEnabled()) {
            logger.info("正在执行竞价..." + keywordEntity);
        }

        StrategyEntity strategyEntity = biddingRuleEntity.getStrategyEntity();

        Integer[] regionList = strategyEntity.getRegionTarget();
        if (regionList == null) {
            CampaignEntity campaignEntity = campaignService.findByKeywordId(keywordEntity.getKeywordId());
            if (campaignEntity.getRegionTarget() != null && !campaignEntity.getRegionTarget().isEmpty()) {
                regionList = campaignEntity.getRegionTarget().toArray(new Integer[]{});
            } else {
                regionList = accountInfoEntity.getRegionTarget().toArray(new Integer[]{});
            }
        }

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

        biddingRuleService.updateRule(biddingRuleEntity);

        //判断该关键词所在的单元或是计划是否处于暂停状态
        boolean isPause = keywordEntity.getPause();

        if (!isPause) {
            isPause = adgroupService.findByAdgroupId(keywordEntity.getAdgroupId()).getPause();
            if (!isPause) {
                isPause = campaignService.findByKeywordId(keywordEntity.getKeywordId()).getPause();
            }
        }

        if (isPause) {
            return;
        }

        //关键词状态为42 45 46时, 不进行竞价
        Integer keywordStatus = keywordEntity.getStatus();  //KeywordStatusEnum
        if (keywordStatus == 42 || keywordStatus == 45 || keywordStatus == 46) {
            return;
        }

        for (Integer region : regionList) {

            if (logger.isInfoEnabled()) {
                logger.info("竞价地域..." + region);
            }

            GetPreviewRequest getPreviewRequest = new GetPreviewRequest();
            getPreviewRequest.setRegion(region);
            getPreviewRequest.setKeyWords(Arrays.asList(keywordEntity.getKeyword()));

            int retry = RETRY;
            while (true) {
                List<HTMLAnalyseServiceImpl.PreviewData> datas = service.getPageData(getPreviewRequest);

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
                HTMLAnalyseServiceImpl.PreviewData previewData = datas.get(0);
                // 解析实况页面数据

                if ((previewData.getLeft() == null || previewData.getLeft().isEmpty()) && (previewData.getRight() == null || previewData.getRight().isEmpty())) {
                    continue;
                }

                int pos = strategyEntity.getExpPosition();
                List<CreativeDTO> leftList = previewData.getLeft();
                List<CreativeDTO> rightList = previewData.getRight();

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
                    e.printStackTrace();
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

                if (logger.isInfoEnabled()) {
                    logger.info("当前排名: " + host + " " + keywordEntity.getKeyword() + " " + ((rank == 0) ? "暂无当前排名" : rank));
                }
                // 已经达到排名
                if (match) {

                    // 单次竞价或者重复竞价
                    if (strategyEntity.getInterval() == -1) {
                        KeywordType keywordType = new KeywordType();
                        keywordType.setKeywordId(keywordEntity.getKeywordId());
                    }

                    if (logger.isInfoEnabled()) {
                        logger.info("达到排名..." + host + " " + keywordEntity.getKeyword() + "\n下次启动时间: " + new Date(biddingRuleEntity.getNext()));
                    }

                    biddingRuleEntity.setCurrentPrice(keywordEntity.getPrice());
                    break;
                } else {
                    // 出价策略
                    BigDecimal currentPrice = biddingRuleEntity.getCurrentPrice();

                    if(currentPrice == null){
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


                    if (logger.isInfoEnabled()) {
                        logger.info("未达到排名..." + host + "\n最新出价: " + currentPrice);
                    }

                    if (currentPrice.compareTo(strategyEntity.getMaxPrice()) == 1) {

                        if (logger.isInfoEnabled()) {
                            logger.info("未达到排名..." + host + "\n出价已超过最大价格!");
                        }
                        int failed = strategyEntity.getFailedStrategy();
                        if (failed == BiddingStrategyConstants.FAILED_KEEP.value()) {
                            // 保持当前排名
                            if (logger.isInfoEnabled()) {
                                logger.info("未达到排名..." + host + "\n保持当前排名.");
                            }
                        } else if (failed == BiddingStrategyConstants.FAILED_ROLLBACK.value()) {
                            //竞价失败 恢复之前的原始竞价
                            if (logger.isInfoEnabled()) {
                                logger.info("未达到排名..." + host + "\n恢复原始竞价名.");
                            }

                            KeywordType keywordType = new KeywordType();
                            keywordType.setKeywordId(keywordEntity.getKeywordId());
                            keywordType.setPrice(keywordEntity.getPrice().doubleValue());

                            biddingRuleEntity.setCurrentPrice(keywordEntity.getPrice());
                            apiService.setKeywordPrice(keywordType);

                            biddingLogEntity.setAfter(BigDecimal.valueOf(keywordType.getPrice()));

                            biddingLogService.save(biddingLogEntity);

                            biddingRuleService.updateRule(biddingRuleEntity);
                        }
                        break;
                    } else {
                        // 正常流程
                        KeywordType keywordType = new KeywordType();
                        keywordType.setKeywordId(keywordEntity.getKeywordId());
                        keywordType.setPrice(currentPrice.doubleValue());
                        biddingRuleEntity.setCurrentPrice(currentPrice);
                        apiService.setKeywordPrice(keywordType);
                        biddingLogService.save(biddingLogEntity);

                        biddingRuleService.updateRule(biddingRuleEntity);

                        if (logger.isInfoEnabled()) {
                            logger.info("未达到排名..." + host + "\n出价完成,十秒后进行下一次出价.");
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

        }
    }

    public long computNext(StrategyEntity strategyEntity) {

        return 0l;
    }

    private boolean innerBidding(BiddingRuleEntity biddingRuleEntity, GetPreviewRequest request) {
        List<HTMLAnalyseServiceImpl.PreviewData> datas = service.getPageData(request);

        if (datas.isEmpty()) {
            return true;
//            try {
//                Thread.sleep(1000);
//                if (retry-- == 0) {
//                    break;
//                } else {
//                    continue;
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        HTMLAnalyseServiceImpl.PreviewData previewData = datas.get(0);
        // 解析实况页面数据

        if ((previewData.getLeft() == null || previewData.getLeft().isEmpty()) && (previewData.getRight() == null || previewData.getRight().isEmpty())) {
            return true;
        }


        StrategyEntity strategyEntity = biddingRuleEntity.getStrategyEntity();
        int pos = strategyEntity.getExpPosition();
        List<CreativeDTO> leftList = previewData.getLeft();
        List<CreativeDTO> rightList = previewData.getRight();
        CreativeDTO creativeDTO = null;

        if (pos == BiddingStrategyConstants.POS_LEFT_1.value() && leftList.size() > 0) {
            creativeDTO = leftList.get(0);
        } else if (pos == BiddingStrategyConstants.POS_LEFT_2_3.value() && leftList.size() > 1) {
            creativeDTO = leftList.get(1);
        } else if (pos == BiddingStrategyConstants.POS_RIGHT_1_3.value() && rightList.size() > 1) {
            creativeDTO = rightList.get(0);
        } else if (pos == BiddingStrategyConstants.POS_RIGHT_OTHERS.value() && rightList.size() > 3) {
            creativeDTO = rightList.get(3);
        }

        String url = creativeDTO.getUrl();
        // 已经达到排名
        if (url.equals(host)) {

            // 单次竞价或者重复竞价
            Date time = BiddingRuleUtils.getDateInvMinute(strategyEntity.getTimes(), -1);
            biddingRuleEntity.setNext(time.getTime());
            biddingRuleService.updateRule(biddingRuleEntity);

            if (logger.isDebugEnabled()) {
                logger.debug("达到排名..." + host + "\n下次启动时间: " + time);
            }
            return false;
        } else {
            // 出价策略
            BigDecimal currentPrice = biddingRuleEntity.getCurrentPrice();

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

                if (logger.isDebugEnabled()) {
                    logger.debug("未达到排名..." + host + "\n出价以超过最大价格!");
                }
                int failed = strategyEntity.getFailedStrategy();
                if (failed == BiddingStrategyConstants.FAILED_KEEP.value()) {
                    // 保持当前排名
                    if (logger.isDebugEnabled()) {
                        logger.debug("未达到排名..." + host + "\n保持当前排名.");
                    }
                } else if (failed == BiddingStrategyConstants.FAILED_ROLLBACK.value()) {
                    //竞价失败 恢复之前的原始竞价
                    if (logger.isDebugEnabled()) {
                        logger.debug("未达到排名..." + host + "\n恢复原始竞价名.");
                    }

                    KeywordType keywordType = new KeywordType();
                    keywordType.setKeywordId(keywordEntity.getKeywordId());
                    keywordType.setPrice(keywordEntity.getPrice().doubleValue());

                    biddingRuleEntity.setCurrentPrice(keywordEntity.getPrice());
                    apiService.setKeywordPrice(keywordType);
                    biddingRuleService.updateRule(biddingRuleEntity);
                }
                return false;
            } else {
                // 正常流程
                KeywordType keywordType = new KeywordType();
                keywordType.setKeywordId(keywordEntity.getKeywordId());
                keywordType.setPrice(currentPrice.doubleValue());
                biddingRuleEntity.setCurrentPrice(currentPrice);
                apiService.setKeywordPrice(keywordType);
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
                return true;
            }
        }
    }

    public BiddingLogService getBiddingLogService() {
        return biddingLogService;
    }

    public void setBiddingLogService(BiddingLogService biddingLogService) {
        this.biddingLogService = biddingLogService;
    }
}
