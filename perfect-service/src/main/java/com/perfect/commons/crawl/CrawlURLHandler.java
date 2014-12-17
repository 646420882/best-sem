package com.perfect.commons.crawl;

import com.perfect.commons.constants.WebSiteConstants;
import com.perfect.dao.sys.CrawlWordDAO;
import com.perfect.db.mongodb.impl.CrawlWordDAOImpl;
import com.perfect.dto.CrawlWordDTO;
import com.perfect.utils.json.JSONUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by baizz on 2014-11-20.
 * <p>
 * use ConcurrentHashMap to build local cache
 * 2014-11-28 refactor
 */
public class CrawlURLHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String crawler_queue = "crawler_queue";

    //本地缓存
    private Map<String, List<CrawlWordDTO>> cacheMap = new ConcurrentHashMap<>();

    //阻塞队列
    private BlockingQueue<CrawlWordDTO> queue = new LinkedBlockingQueue<>();

    //需要爬取的站点
    private List<String> sites = new ArrayList<>();

    private JedisPool pool;

    @Resource
    private CrawlWordDAO crawlWordDAO1;

    private CrawlURLHandler() {
        CrawlURLHandler.JedisPools.init("182.150.24.24");
        this.pool = CrawlURLHandler.JedisPools.getPool();
    }

    public CrawlURLHandler setSites(String... sites) {
        this.sites.addAll(Arrays.asList(sites));
        return this;
    }

    @Override
    public void run() {
        if (logger.isInfoEnabled()) {
            logger.info("starting reading data...");
        }

        //
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        CrawlWordDAO crawlWordDAO = (CrawlWordDAOImpl) context.getBean("crawlWordDAO");
        //

        //从本地缓存中取值放于阻塞队列中
        sites.forEach(site -> cacheMap.computeIfAbsent(site, (key) -> crawlWordDAO.findBySite(site)));
        cacheMap.get(sites.get(0)).forEach(queue::offer);
        cacheMap.remove(sites.get(0));
        sites.remove(0);

        while (true) {
            Jedis jedis = pool.getResource();

            try {
                if (jedis.llen(crawler_queue) >= 50000) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    //从阻塞队列中取值
                    CrawlWordDTO dto;
                    if ((dto = queue.poll()) == null) {
                        if (sites.size() > 0) {
                            cacheMap.get(sites.get(0)).forEach(queue::offer);
                            cacheMap.remove(sites.get(0));
                            sites.remove(0);
                            continue;
                        } else {
                            break;
                        }
                    }

                    Map<String, Object> conf = new HashMap<>();
                    String _site = dto.getSite();
                    conf.put("k", dto.getKeyword());
                    conf.put("p", _site);

                    switch (_site) {
                        case "lefeng":
                            conf.put("q", WebSiteConstants.lefengUrlTemplate);
                            conf.put("d", "default");
                            break;
                        case "vip":
                            conf.put("q", WebSiteConstants.vipTempUrlTemplate);
                            conf.put("d", "default");
                            break;
                        case "yhd":
                            conf.put("q", WebSiteConstants.yhdUrlTemplate);
                            conf.put("d", "default");
                            break;
                        case "xiu":
                            conf.put("q", WebSiteConstants.xiuUrlTemplate);
                            conf.put("d", "default");
                            break;
                        case "gome":
                            conf.put("q", WebSiteConstants.gomeUrlTemplate);
                            conf.put("d", "default");
                            break;
                        case "suning":
                            conf.put("q", WebSiteConstants.suningUrlTemplate);
                            conf.put("d", "default");
                            break;
                        case "dangdang":
                            conf.put("q", WebSiteConstants.dangdangUrlTemplate);
                            conf.put("d", "default");
                            break;
                        case "amazon":
                            conf.put("q", WebSiteConstants.amazonUrlTemplate);
                            conf.put("d", "default");
                            break;
                        case "taobao":
                            conf.put("q", getTaobaoURL(dto.getKeyword(), WebSiteConstants.taobaoUrlTemplate));
                            conf.put("d", "phantomjs");
                            break;
                        default:
                            break;
                    }

                    String confStr = JSONUtils.getJsonString(conf);
                    String md5 = DigestUtils.md5Hex(confStr);

                    jedis.rpush("crawler_queue", md5);
                    jedis.set("extras_" + md5, confStr);
                }

                if (logger.isInfoEnabled()) {
                    logger.info("Redis 当前键值个数: " + jedis.llen(crawler_queue));
                }
            } finally {
                pool.returnResource(jedis);
            }
        }

        if (logger.isInfoEnabled()) {
            logger.info("read end...");
        }
    }

    /**
     * A method to get taobao's url
     *
     * @param inputWord
     * @param taobaoUrlTemplate
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
            init = true;
        }

        public static JedisPool getPool() {
            return jedisPool;
        }
    }
}