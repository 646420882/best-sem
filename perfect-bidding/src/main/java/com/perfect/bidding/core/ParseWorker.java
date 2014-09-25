package com.perfect.bidding.core;

import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduSpiderHelper;
import com.perfect.autosdk.sms.v3.KeywordType;
import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.constants.BiddingStrategyConstants;
import com.perfect.dto.CreativeDTO;
import com.perfect.elasticsearch.threads.EsRunnable;
import com.perfect.elasticsearch.threads.EsThreadPoolTaskExecutor;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.bidding.BiddingLogEntity;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.entity.bidding.StrategyEntity;
import com.perfect.service.BiddingLogService;
import com.perfect.service.BiddingRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/24.
 */
public class ParseWorker implements Runnable {

    private Logger logger = LoggerFactory.getLogger(ParseWorker.class);

    private static final BigDecimal FAST_PRICE = BigDecimal.ONE.divide(BigDecimal.ONE.add(BigDecimal.ONE));

    private static final BigDecimal ECON_PRICE = BigDecimal.valueOf(0.05);


    private final String keyword;
    private final Integer region;
    private BiddingRuleEntity biddingRuleEntity;
    private KeywordEntity keywordEntity;
    private BaiduApiService apiService;
    private String host;

    public ParseWorker(String keyword, Integer region) {
        this.keyword = keyword;
        this.region = region;
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

        BiddingRuleService biddingRuleService = (BiddingRuleService) ApplicationContextHelper.getBeanByClass(BiddingRuleService.class);

        EsThreadPoolTaskExecutor executors = (EsThreadPoolTaskExecutor) ApplicationContextHelper.getBeanByClass(EsThreadPoolTaskExecutor.class);

        BiddingLogService biddingLogService = (BiddingLogService) ApplicationContextHelper.getBeanByClass(BiddingLogService.class);
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
                if (logger.isErrorEnabled()) {
                    logger.error("MalformedURLException", e);
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

                        biddingRuleEntity.setCurrentPrice(keywordEntity.getPrice());

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

                    biddingRuleEntity.setCurrentPrice(currentPrice);
                    biddingRuleService.updateRule(biddingRuleEntity);

                    biddingLogService.save(biddingLogEntity);

                    if (logger.isDebugEnabled()) {
                        logger.debug("未达到排名..." + host + "\n出价完成,1秒后进行下一次出价.");
                    }
                }

            }
        }
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
        this.host = host;
    }

    public String getHost() {
        return host;
    }
}
