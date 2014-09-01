package com.perfect.utils;

import com.perfect.constants.BiddingStrategyConstants;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.entity.bidding.StrategyEntity;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vbzer_000 on 2014/8/31.
 */
public class BiddingRuleUtils {

    public static String getRule(BiddingRuleEntity biddingRuleEntity) {
        StringBuilder sb = new StringBuilder();

        StrategyEntity entity = biddingRuleEntity.getStrategyEntity();

        int positionStrategy = entity.getExpPosition();
        if (positionStrategy == BiddingStrategyConstants.POS_LEFT_1.value()) {
            sb.append("左:[1,");
        } else if (positionStrategy == BiddingStrategyConstants.POS_LEFT_2_3.value()) {
            sb.append("左:[2-3,");
        } else if (positionStrategy == BiddingStrategyConstants.POS_RIGHT_1_3.value()) {
            sb.append("右:[1-3,");
        } else if (positionStrategy == BiddingStrategyConstants.POS_RIGHT_OTHERS.value()) {
            sb.append("右:[").append(entity.getPosition()).append(",");
        }

        sb.append("上限").append(entity.getMaxPrice()).append("元,下限").append(entity.getMinPrice()).append("元]");
        sb.append("(").append((entity.getDevice() == BiddingStrategyConstants.TYPE_PC.value()) ? "计算机" : "移动").append(")");

        return sb.toString();
    }


    public static Date getDateInvMinute(Integer[] times, int interval) {

        StringBuilder sb = new StringBuilder();
        sb.append("0 *");
        if(interval > 0){
            sb.append("/").append(interval).append(" ");
        }else{
            sb.append(" ");
        }

        for (int i = 0; i < times.length; i++) {
            sb.append(times[i]).append("-").append(times[++i] - 1).append(",");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(" * * ?");

        try {
            CronExpression cronExpression = new CronExpression(sb.toString());
            Date time = cronExpression.getNextValidTimeAfter(Calendar.getInstance().getTime());
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Date getDateInvHour(Integer[] times, int interval) {
        StringBuilder sb = new StringBuilder("0 0 ");

        for (int i = 0; i < times.length; i++) {
            sb.append(times[i]).append("-").append(times[++i] - 1).append(",");
        }

        sb.deleteCharAt(sb.length() - 1).append("/").append(interval);
        sb.append(" * * ?");

        try {
            CronExpression cronExpression = new CronExpression(sb.toString());
            Date time = cronExpression.getNextValidTimeAfter(Calendar.getInstance().getTime());
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
