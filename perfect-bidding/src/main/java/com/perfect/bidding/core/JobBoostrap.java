package com.perfect.bidding.core;

import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.SystemUserEntity;
import com.perfect.service.SystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/23.
 */
public class JobBoostrap {

    private Logger logger = LoggerFactory.getLogger(JobBoostrap.class);

    @Resource
    private AccountThreadTaskExecutors accountThreadTaskExecutors;

    @Resource
    private SystemUserService systemUserService;

    public void start() {
        Iterable<SystemUserEntity> userEntityList = systemUserService.getAllUser();

        for (SystemUserEntity userEntity : userEntityList) {
            List<BaiduAccountInfoEntity> accountInfoEntityList = userEntity.getBaiduAccountInfoEntities();
            for (BaiduAccountInfoEntity baiduAccountInfoEntity : accountInfoEntityList) {
                if (logger.isInfoEnabled()) {
                    logger.info("正在启动 " + baiduAccountInfoEntity.getBaiduUserName() + " 竞价器");
                }
                AccountWorker accountWorker = new AccountWorker(userEntity.getUserName(), baiduAccountInfoEntity);
                accountThreadTaskExecutors.execute(accountWorker);

            }
        }


        while(true) {
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
