package com.perfect.bidding.heartbeat;

import com.groot.webmagic.BaiduKeywordScheduler;
import com.groot.webmagic.Constant;
import com.groot.webmagic.Container;
import com.perfect.api.baidu.PostMethodFactory;
import com.perfect.dao.FarmDAO;
import com.perfect.entity.UrlEntity;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

/**
 * Created by vbzer_000 on 2014/9/25.
 */
public class HeartBeatJob implements Constant {

    private Logger logger = LoggerFactory.getLogger(HeartBeatJob.class);
    @Resource
    private FarmDAO farmDAO;

    public HeartBeatJob() {
        System.out.println("Started!");
    }

    public void start() {

        if (logger.isDebugEnabled()) {
            logger.debug("心跳开始..");
        }

        List<UrlEntity> urlEntityList = farmDAO.allUnused();

        HttpClient client = new HttpClient();
        for (UrlEntity urlEntity : urlEntityList) {

            String uuid = UUID.randomUUID().toString();
            BaiduKeywordScheduler.getInstance().push(uuid, urlEntity.getRequest(), null, 1);

            try {
                Object map = Container.get(uuid);
                if (map != null) {
                    Map<String, Object> cmap = (Map<String, Object>) map;
                    if (cmap.containsKey(PAGE_TIMEOUT)) {
                        farmDAO.delete(urlEntity.getId());
                        continue;
                    }
                }

                urlEntity.setFinishTime(System.currentTimeMillis() + 5000);
                farmDAO.returnOne(urlEntity);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            continue;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("心跳结束..");
        }
    }
}
