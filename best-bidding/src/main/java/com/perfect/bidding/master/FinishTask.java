package com.perfect.bidding.master;

import com.perfect.bidding.redis.Constants;
import com.perfect.bidding.redis.JRedisPools;
import com.perfect.commons.context.ApplicationContextHelper;
import com.perfect.dto.bidding.BiddingRuleDTO;
import com.perfect.service.BiddingRuleService;
import com.perfect.utils.json.JSONUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by baizz on 2015-1-4.
 */
class FinishTask extends JedisPubSub implements Constants {

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        if (isFinishedChannel(channel)) {
            // the specified keyword bidding finished
            BiddingRuleDTO biddingRule = JSONUtils.getObjectByJson(message, BiddingRuleDTO.class);
            BiddingRuleService biddingRuleService = (BiddingRuleService) ApplicationContextHelper
                    .getBeanByName("biddingRuleService");
            biddingRuleService.saveWithAccountId(biddingRule);

            // 从HashMap中移除已完成竞价的关键词
            Jedis jedis = null;
            try {
                jedis = JRedisPools.getConnection();
                String workerId = channel.replace("$", "");
                String field = biddingRule.getId();
                jedis.hdel(workerId, field);
            } finally {
                if (jedis != null) {
                    JRedisPools.returnJedis(jedis);
                }
            }
        }
    }

}
