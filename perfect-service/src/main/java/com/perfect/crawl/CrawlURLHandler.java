package com.perfect.crawl;

import com.perfect.entity.CrawlWordEntity;
import com.perfect.mongodb.dao.impl.CrawlWordDAOImpl;
import com.perfect.utils.JSONUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-11-17.
 */
public class CrawlURLHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private JedisPool pool;

//    @Resource
//    private CrawlWordDAOImpl crawlWordDAO;

    private CrawlURLHandler() {
        CrawlURLHandler.JedisPools.init("182.150.24.24");
        pool = CrawlURLHandler.JedisPools.getPool();
    }

    @Override
    public void run() {
        if (logger.isInfoEnabled()) {
            logger.info("starting reading data...");
        }
        Jedis jedis = pool.getResource();
        List<CrawlWordEntity> results;
        int index = 0;
        //===
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        CrawlWordDAOImpl crawlWordDAO = (CrawlWordDAOImpl) applicationContext.getBean("crawlWordDAO");
        //===
        while (true) {
            //
            System.out.println("Redis 键值个数: " + jedis.dbSize());
            //
            if (jedis.dbSize() > 100) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                results = crawlWordDAO.find(null, index, 10, null, null);

                if (results.size() == 0)
                    break;

                for (CrawlWordEntity entity : results) {
                    try {
                        Map<String, Object> conf = new HashMap<>();
                        String site = entity.getSite();
                        conf.put("k", entity.getKeyword());
                        conf.put("p", site);
                        //
                        if ("lefeng&vip".equals(site)) {
                            continue;
                        }
                        if ("yhd".equals(site)) {
                            continue;
                        }
                        if ("xiu".equals(site)) {
                            continue;
                        }
                        if ("gome".equals(site)) {
                            continue;
                        }
                        if ("suning".equals(site)) {
                            continue;
                        }
                        if ("dangdang".equals(site)) {
                            continue;
                        }
//                        if ("taobao".equals(site)) {
//                            continue;
//                        }
                        //
                        switch (site) {
                            case "lefeng&vip":
                                break;
                            case "yhd":
                                break;
                            case "xiu":
                                break;
                            case "gome":
                                conf.put("q", WebSiteConstant.gomeUrlTemplate);
                                break;
                            case "suning":
                                break;
                            case "dangdang":
                                conf.put("q", WebSiteConstant.dangdangUrlTemplate);
                                break;
                            case "amazon":
                                conf.put("q", WebSiteConstant.amazonUrlTemplate);
                                conf.put("d", "default");
                                break;
                            case "taobao":
                                conf.put("q", getTaobaoURL(entity.getKeyword(), WebSiteConstant.taobaoUrlTemplate));
                                conf.put("d", "phantomjs");
                                break;
                            default:
                                break;
                        }

                        String confStr = JSONUtils.getJsonString(conf);
                        String md5 = DigestUtils.md5Hex(confStr);


                        jedis.rpush("crawler_queue", md5);
                        jedis.set("extras_" + md5, confStr);
                    } finally {
                        index++;
//                        pool.returnResource(jedis);
                    }
                }
            }
        }

        pool.destroy();

        if (logger.isInfoEnabled()) {
            logger.info("read end...");
        }
    }

    /**
     * A method to get taobao's url
     *
     * @param inputWord
     * @return
     */
    private String getTaobaoURL(String inputWord, String taobaoUrlTemplate) {
        HttpURLConnection httpURLConnection = null;
        try {
            inputWord = java.net.URLEncoder.encode(inputWord, "GBK");
            URL url = new URL(String.format(taobaoUrlTemplate, inputWord));
            httpURLConnection = (HttpURLConnection) url.openConnection();
            if (httpURLConnection.getResponseCode() == HttpStatus.SC_OK) {
                return httpURLConnection.getURL().toString().replace(inputWord, "%s") + "&sort=sale-desc";
            } else {
                //try again
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                httpURLConnection = (HttpURLConnection) url.openConnection();
                if (httpURLConnection.getResponseCode() == HttpStatus.SC_OK) {
                    return httpURLConnection.getURL().toString().replace(inputWord, "%s") + "&sort=sale-desc";
                }
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }

    public static CrawlURLHandler build() {
        return new CrawlURLHandler();
    }


    protected static class JedisPools {

        private static JedisPool jedisPool;
        private static boolean init = false;

        public static void init(String host) {
            if (init)
                return;

            jedisPool = new JedisPool(new JedisPoolConfig(), host, Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, "3edcvfr4");

            if (jedisPool != null)
                init = true;
        }

        public static JedisPool getPool() {
            return jedisPool;
        }
    }
}
