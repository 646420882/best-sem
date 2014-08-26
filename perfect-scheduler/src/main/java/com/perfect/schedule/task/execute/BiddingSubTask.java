package com.perfect.schedule.task.execute;

import com.perfect.autosdk.sms.v3.GetPreviewRequest;
import com.perfect.autosdk.sms.v3.KeywordType;
import com.perfect.constants.BiddingStrategyConstants;
import com.perfect.dto.CreativeDTO;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.entity.bidding.StrategyEntity;
import com.perfect.service.BaiduApiService;
import com.perfect.service.BiddingRuleService;
import com.perfect.service.HTMLAnalyseService;
import com.perfect.service.impl.HTMLAnalyseServiceImpl;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *                   _ooOoo_
 *                  o8888888o
 *                  88" . "88
 *                  (| -_- |)
 *                  O\  =  /O
 *               ____/`---'\____
 *             .'  \\|     |//  `.
 *            /  \\|||  :  |||//  \
 *           /  _||||| -:- |||||-  \
 *           |   | \\\  -  /// |   |
 *           | \_|  ''\---/''  |_/ |
 *           \  .-\__  '-'  ___/-. /
 *          __'. .'  /--.--\  `. .' __
 *      ."" '<  `.___\_<|>_/___.'  >'"".
 *     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *     \  \ `-.   \_ __\ /__ _/   .-` /  /
 *======`-.____`-.___\_____/___.-`____.-'======
 *                   `=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                佛祖保佑       永无BUG

 * Created by yousheng on 2014/8/19.
 *
 * @author yousheng
 */
public class BiddingSubTask implements Runnable {

    private static final double FAST_PRICE = 0.5;
    private static final double ECON_PRICE = 0.01;

    private final HTMLAnalyseService service;
    private final List<BiddingRuleEntity> biddingRuleEntities;
    private final Map<String, KeywordEntity> keywordEntityMap;
    private final List<KeywordType> keywordQueue;
    private final BaiduApiService apiService;

    @Resource
    private BiddingRuleService biddingRuleService;

    public BiddingSubTask(BaiduApiService apiService, HTMLAnalyseService htmlAnalyseService, List<BiddingRuleEntity> biddingRuleEntity, Map<String, KeywordEntity> keywordEntityMap, List<KeywordType> keywordQueue) {
        this.apiService = apiService;
        this.biddingRuleEntities = biddingRuleEntity;
        this.service = htmlAnalyseService;
        this.keywordEntityMap = keywordEntityMap;
        this.keywordQueue = keywordQueue;
    }

    @Override
    public void run() {
        GetPreviewRequest getPreviewRequest = new GetPreviewRequest();
        getPreviewRequest.setRegion(4116);
        List<String> kwList = new ArrayList<>(5);

        Map<String, BiddingRuleEntity> ruleEntityMap = new HashMap<>(5);
        for (BiddingRuleEntity ruleEntity : biddingRuleEntities) {
            kwList.add(ruleEntity.getKeyword());
            ruleEntityMap.put(ruleEntity.getKeyword(), ruleEntity);
        }
        getPreviewRequest.setKeyWords(kwList);

        List<HTMLAnalyseServiceImpl.PreviewData> datas = service.getPageData(getPreviewRequest);

        // 解析实况页面数据
        for (HTMLAnalyseServiceImpl.PreviewData previewData : datas) {

            if ((previewData.getLeft() == null || previewData.getLeft().isEmpty()) && (previewData.getRight() == null || previewData.getRight().isEmpty())) {
                continue;
            }
            BiddingRuleEntity ruleEntity = ruleEntityMap.get(previewData.getKeyword());

            KeywordEntity keywordEntity = keywordEntityMap.get(previewData.getKeyword());

            StrategyEntity strategyEntity = ruleEntity.getStrategyEntity();

            String host = null;

            if (strategyEntity.getType() == BiddingStrategyConstants.TYPE_PC.value()) {
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

            int pos = strategyEntity.getPositionStrategy();
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
                if (strategyEntity.getInterval() != -1) {
                    long nextTime = System.currentTimeMillis() + strategyEntity.getInterval() * 60 * 1000;
                    ruleEntity.setNextTime(nextTime);
                } else {
                    ruleEntity.setNextTime(-1);
                }
            } else {
                // 出价策略
                double currentPrice = ruleEntity.getCurrentPrice();
                if (strategyEntity.getSpd() == BiddingStrategyConstants.SPD_FAST.value()) {
                    currentPrice = currentPrice + FAST_PRICE;
                } else {
                    currentPrice = currentPrice + ECON_PRICE;
                }

                if (currentPrice > strategyEntity.getMaxPrice()) {
                    int failed = strategyEntity.getFailedStrategy();
                    if (failed == BiddingStrategyConstants.FAILED_KEEP.value()) {

                    } else if (failed == BiddingStrategyConstants.FAILED_ROLLBACK.value()) {
                        KeywordType keywordType = new KeywordType();
                        keywordType.setKeywordId(keywordEntity.getKeywordId());
                        keywordType.setPrice(strategyEntity.getMinPrice());
                        keywordQueue.add(keywordType);

                        ruleEntity.setCurrentPrice(strategyEntity.getMinPrice());
                    }
                } else {

                    KeywordType keywordType = new KeywordType();
                    keywordType.setKeywordId(keywordEntity.getKeywordId());
                    keywordType.setPrice(currentPrice);
                    keywordQueue.add(keywordType);

                    ruleEntity.setCurrentPrice(currentPrice);
                }
            }

        }

        apiService.setKeywordPrice(keywordQueue);

        biddingRuleService.updateRule(biddingRuleEntities);
    }
}
