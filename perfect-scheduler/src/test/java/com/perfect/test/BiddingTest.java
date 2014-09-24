package com.perfect.test;

import com.perfect.constants.BiddingStrategyConstants;
import com.perfect.dao.BiddingRuleDAO;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.entity.bidding.StrategyEntity;
import com.perfect.schedule.task.execute.BiddingJob;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yousheng on 2014/8/20.
 *
 * @author yousheng
 */
@SpringApplicationContext({"schedule.xml"})
public class BiddingTest extends UnitilsJUnit4 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("schedule.xml");
        BiddingJob biddingJob = (BiddingJob) applicationContext.getBean("biddingTask");

        List<BiddingJob.TaskObject> list = null;
        try {
            list = biddingJob.selectTasks();
            biddingJob.execute1(list.toArray(new BiddingJob.TaskObject[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SpringBeanByType
    private BiddingJob biddingJob;

    @Test
    public void test1() {

        List<BiddingJob.TaskObject> list = null;
        try {
            list = biddingJob.selectTasks();
            biddingJob.execute1(list.toArray(new BiddingJob.TaskObject[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void createEntity(ClassPathXmlApplicationContext applicationContext) {
        BiddingRuleDAO biddingRuleDAO = (BiddingRuleDAO) applicationContext.getBean("biddingRuleDAO");

        BiddingRuleEntity biddingRuleEntity = new BiddingRuleEntity();
        biddingRuleEntity.setAccountId(7001963);
//        biddingRuleEntity.setStart(1);
        biddingRuleEntity.setKeywordId(906068486l);
        biddingRuleEntity.setKeyword("深圳南山 买房");
        biddingRuleEntity.setEnabled(true);
        biddingRuleEntity.setCurrentPrice(BigDecimal.ONE);

        StrategyEntity strategyEntity = new StrategyEntity();
        biddingRuleEntity.setStrategyEntity(strategyEntity);
        strategyEntity.setInterval(30);
        strategyEntity.setMode(BiddingStrategyConstants.SPD_FAST.value());
        strategyEntity.setMaxPrice(BigDecimal.valueOf(5l));
        strategyEntity.setMinPrice(BigDecimal.ONE);

        strategyEntity.setExpPosition(BiddingStrategyConstants.POS_LEFT_1.value());

        biddingRuleDAO.createBidding(biddingRuleEntity);
    }

    public void test() {

        try {
            BiddingJob biddingJob = new BiddingJob();
            List<BiddingJob.TaskObject> list = biddingJob.selectTasks();

            biddingJob.execute1(list.toArray(new BiddingJob.TaskObject[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
