package com.perfect.bidding.heartbeat;

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
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

/**
 * Created by vbzer_000 on 2014/9/25.
 */
public class HeartBeatJob {

    private Logger logger = LoggerFactory.getLogger(HeartBeatJob.class);
    @Resource
    private FarmDAO farmDAO;

    public HeartBeatJob(){
        System.out.println("Started!");
    }

    public void start() {

        if (logger.isDebugEnabled()) {
            logger.debug("心跳开始..");
        }

        List<UrlEntity> urlEntityList = farmDAO.allUnused();

        HttpClient client = new HttpClient();
        for (UrlEntity urlEntity : urlEntityList) {
            PostMethod method = PostMethodFactory.getMethod(urlEntity.getRequest(), "test", 1, 1, 0);
            try {
                int code = client.executeMethod(method);
                if (code == HttpStatus.SC_OK) {
                    Thread.sleep(1000);
                    InputStream is = null;
                    try {
                        is = new GZIPInputStream(method.getResponseBodyAsStream());
                    } catch (ZipException ze) {
                        if (logger.isWarnEnabled()) {
                            logger.warn(urlEntity.getId() + " 需要重新登录.");
                        }
                        farmDAO.delete(urlEntity.getId());
                        continue;
                    } finally {
                        if (is != null) {
                            is.close();
                        }
                    }
                    farmDAO.returnOne(urlEntity);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("心跳结束..");
        }
    }
}
