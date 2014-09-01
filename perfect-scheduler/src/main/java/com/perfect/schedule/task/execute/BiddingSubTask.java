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
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.entity.bidding.StrategyEntity;
import com.perfect.service.BaiduApiService;
import com.perfect.service.BiddingRuleService;
import com.perfect.service.HTMLAnalyseService;
import com.perfect.service.SysCampaignService;
import com.perfect.service.impl.HTMLAnalyseServiceImpl;
import com.perfect.utils.BiddingRuleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
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

    private static final double FAST_PRICE = 0.5;
    private static final double ECON_PRICE = 0.01;

    private final HTMLAnalyseService service;
    private String userName;
    private final BaiduApiService apiService;
    private BaiduAccountInfoEntity accountInfoEntity;
    private final BiddingRuleEntity biddingRuleEntity;
    private final KeywordEntity keywordEntity;
    private BiddingRuleService biddingRuleService;
    private final SysCampaignService campaignService;

    private Logger logger = LoggerFactory.getLogger(BiddingSubTask.class);

    public BiddingSubTask(String userName, BaiduApiService apiService, BiddingRuleService biddingRuleService, SysCampaignService sysCampaignService, HTMLAnalyseService htmlAnalyseService,
                          BaiduAccountInfoEntity accountInfoEntity, BiddingRuleEntity biddingRuleEntity, KeywordEntity keywordEntity) {
        this.userName = userName;
        this.apiService = apiService;
        this.biddingRuleService = biddingRuleService;
        this.campaignService = sysCampaignService;
        this.accountInfoEntity = accountInfoEntity;
        this.biddingRuleEntity = biddingRuleEntity;
        this.service = htmlAnalyseService;
        this.keywordEntity = keywordEntity;
    }

    public BiddingSubTask(String user, CommonService service, BiddingRuleService biddingRuleService, SysCampaignService sysCampaignService, BaiduAccountInfoEntity accountInfoEntity, BiddingRuleEntity biddingRuleEntity, KeywordEntity keywordEntity) {

        this(user, new BaiduApiService(service), biddingRuleService, sysCampaignService, HTMLAnalyseServiceImpl.createService(service), accountInfoEntity, biddingRuleEntity, keywordEntity);
    }

    @Override
    public void run() {
        AppContext.setUser(userName);
        if (logger.isInfoEnabled()) {
            logger.info("正在执行竞价..." + keywordEntity);
        }

        StrategyEntity strategyEntity = biddingRuleEntity.getStrategyEntity();

        String host = null;

        if (strategyEntity.getDevice() == BiddingStrategyConstants.TYPE_PC.value()) {
            try {
                URL url = new URL(keywordEntity.getPcDestinationUrl());
                host = url.getHost();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                URL url = new URL(keywordEntity.getMobileDestinationUrl());
                host = url.getHost();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        if (host == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("未匹配正确的host地址! keywordId=" + keywordEntity.getKeywordId());
            }
            return;
        }

        Integer[] regionList = strategyEntity.getRegionTarget();
        if (regionList == null) {
            CampaignEntity campaignEntity = campaignService.findByKeywordId(keywordEntity.getKeywordId());
            if (campaignEntity.getRegionTarget() != null && !campaignEntity.getRegionTarget().isEmpty()) {
                regionList = campaignEntity.getRegionTarget().toArray(new Integer[]{});
            } else {
                regionList = accountInfoEntity.getRegionTarget().toArray(new Integer[]{});
            }
        }

        for (Integer region : regionList) {

            if (logger.isDebugEnabled()) {
                logger.debug("竞价地域..." + region);
            }

            GetPreviewRequest getPreviewRequest = new GetPreviewRequest();
            getPreviewRequest.setRegion(region);
            getPreviewRequest.setKeyWords(Arrays.asList(keywordEntity.getKeyword()));

            while (true) {
                List<HTMLAnalyseServiceImpl.PreviewData> datas = service.getPageData(getPreviewRequest);

                if (datas.isEmpty()) {
                    try {
                        Thread.sleep(1000);
                        continue;
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
                    break;
                } else {
                    // 出价策略
                    double currentPrice = biddingRuleEntity.getCurrentPrice();
                    if (currentPrice < strategyEntity.getMinPrice()) {
                        currentPrice = strategyEntity.getMinPrice();
                    }
                    if (strategyEntity.getMode() == BiddingStrategyConstants.SPD_FAST.value()) {
                        currentPrice = currentPrice + FAST_PRICE;
                    } else {
                        currentPrice = currentPrice + ECON_PRICE;
                    }

                    if (logger.isDebugEnabled()) {
                        logger.debug("未达到排名..." + host + "\n最新出价: " + currentPrice);
                    }

                    if (currentPrice > strategyEntity.getMaxPrice()) {

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
                            keywordType.setPrice(keywordEntity.getPrice());

                            biddingRuleEntity.setCurrentPrice(keywordEntity.getPrice());
//                        apiService.setKeywordPrice(keywordType);
                            biddingRuleService.updateRule(biddingRuleEntity);
                        }
                        break;
                    } else {
                        // 正常流程
                        KeywordType keywordType = new KeywordType();
                        keywordType.setKeywordId(keywordEntity.getKeywordId());
                        keywordType.setPrice(currentPrice);
                        biddingRuleEntity.setCurrentPrice(currentPrice);
//                    apiService.setKeywordPrice(keywordType);
                        biddingRuleService.updateRule(biddingRuleEntity);

                        if (logger.isDebugEnabled()) {
                            logger.debug("未达到排名..." + host + "\n出价完成,一秒后进行下一次出价.");
                        }

                        try {
                            //竞价完成一个阶段
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


        }
    }
}
