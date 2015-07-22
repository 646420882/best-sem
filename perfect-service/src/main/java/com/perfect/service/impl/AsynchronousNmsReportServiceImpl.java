package com.perfect.service.impl;

import com.google.common.collect.Lists;
import com.perfect.dao.report.AsynchronousNmsReportDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.account.NmsAccountReportDTO;
import com.perfect.dto.account.NmsAdReportDTO;
import com.perfect.dto.account.NmsCampaignReportDTO;
import com.perfect.dto.account.NmsGroupReportDTO;
import com.perfect.nms.NmsReportIdAPI;
import com.perfect.nms.ReportFileUrlTask;
import com.perfect.service.AsynchronousNmsReportService;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.redis.JRedisUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import rx.Observable;
import rx.functions.Action1;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.perfect.commons.constants.RedisConstants.*;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by subdong on 15-7-21.
 */
@Service(value = "asynchronousNmsReportService")
public class AsynchronousNmsReportServiceImpl implements AsynchronousNmsReportService {

    @Resource
    private AsynchronousNmsReportDAO asynchronousNmsReportDAO;

    @Resource
    private SystemUserDAO systemUserDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(AsynchronousNmsReportServiceImpl.class);

    public static final String ZERO = "0";

    public static final String ONE = "1";

    private final JedisPool pool = JRedisUtils.getPool();

    private final ReentrantLock lock = new ReentrantLock();


    @Override
    public void retrieveReport(Date[] dates, String... args) {
        if (dates == null || dates.length == 0) {
            dates = new Date[]{null, null};
        }

        preSetting();

        ExecutorService reportFileUrlExecutor = Executors.newSingleThreadExecutor();
        reportFileUrlExecutor.execute(this::readReportFileUrlFromRedis);

        final Date[] _dates = dates;
        final ReportFileUrlTask fileUrlTask = new ReportFileUrlTask();
        ReportTask task = new ReportTask(fileUrlTask, _dates, args);
        FutureTask<Boolean> futureTask = new FutureTask<>(task);
        try {
            if (futureTask.get()) {
                reportFileUrlExecutor.shutdown();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void readReportFileUrlFromRedis() {
        while (true) {
            try {
                lock.lock();

                Jedis jedis = JRedisUtils.get();
                String value = jedis.rpop(REPORT_FILE_URL_SUCCEED);
                String status = jedis.get(REPORT_FILE_URL_GENERATE_COMPLETE);
                if (value == null && ONE.equals(status)) {
                    closeRedis(jedis);
                    lock.unlock();
                    break;
                }

                if (value == null) {
                    closeRedis(jedis);
                    lock.unlock();
                    continue;
                }

                int type = Integer.parseInt(value.split("\\|")[0]);
                String fileUrl = value.split("\\|")[1];

                switch (type) {
                    case 1:
                        Observable<String> observable1 = Observable.just(fileUrl);
                        observable1.subscribe(new AccountAction());
                        break;
                    case 2:
                        Observable<String> observable2 = Observable.just(fileUrl);
                        observable2.subscribe(new CampaignAction());
                        break;
                    case 3:
                        Observable<String> observable3 = Observable.just(fileUrl);
                        observable3.subscribe(new GroupAction());
                        break;
                    case 4:
                        Observable<String> observable4 = Observable.just(fileUrl);
                        observable4.subscribe(new AdAction());
                        break;
                    default:
                        break;
                }

                closeRedis(jedis);
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
    }

    //获取用户公用方法
    private List<SystemUserDTO> getBaiduUser(String userName) {
        List<SystemUserDTO> entityList = new ArrayList<>();
        if (userName == null) {
            Iterable<SystemUserDTO> entities = systemUserDAO.findAll();
            entityList = ObjectUtils.convertToList(Lists.newArrayList(entities), SystemUserDTO.class);
        } else {
            SystemUserDTO userEntity = systemUserDAO.findByUserName(userName);
            entityList.add(userEntity);
        }

        List<SystemUserDTO> newEntityList = entityList.stream().filter(e -> e != null).filter(e -> e.getBaiduAccounts() != null).filter(e -> {
            return (e.getState() != 0 && e.getBaiduAccounts().size() > 0 && e.getAccess() == 2 && e.getAccountState() > 0);
        }).collect(Collectors.toList());
        return newEntityList;
    }

    // 预设置
    private void preSetting() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(REPORT_ID_COMMIT_STATUS, ZERO);
            jedis.set(REPORT_FILE_URL_GENERATE_COMPLETE, ZERO);
        } finally {
            closeRedis(jedis);
        }
    }

    private void closeRedis(Jedis jedis) {
        if (jedis != null && pool.getNumActive() > 0) {
            jedis.close();
        }
    }

    /**
     * 读取CSV文件的所有行
     *
     * @param s
     * @return
     */
    private Map<String, List<String>> readAllLines(String s) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(new HttpGet(s));
             BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), UTF_8))) {
            List<String> lines = br.lines().collect(Collectors.toList());
            if (lines != null && !lines.isEmpty()) {
                lines.remove(0);
                String dateStr = lines.stream().findFirst().get().split("\\t")[0];
                dateStr = dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6);
                Map<String, List<String>> linesMap = new HashMap<>();
                linesMap.put(dateStr, lines);
                return linesMap;
            }
        } catch (IOException e) {
            LOGGER.info("java.io.IOException");
            e.printStackTrace();
        }

        return Collections.emptyMap();
    }

    private String headKey(Map<String, List<String>> map) {
        String key = null;
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            key = entry.getKey();
            break;
        }

        return key;
    }

    private List<String> headValue(Map<String, List<String>> map) {
        List<String> lines = null;
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            lines = entry.getValue();
            break;
        }

        return lines == null ? Collections.emptyList() : lines;
    }

    private final Function<String, NmsAccountReportDTO> accountFunc = (String line) -> {
        try {
            String[] sp = line.split("\\t");
            NmsAccountReportDTO account = new NmsAccountReportDTO();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

            Date date = format.parse(sp[0]);
            account.setDate(date);
            account.setAccountId(Long.valueOf(sp[1]));
            account.setAccountName(sp[2]);
            account.setImpression(sp[3] == null || sp[3].equals("-1") ? -1 : Integer.valueOf(sp[3]));
            account.setClick(sp[4] == null || sp[4].equals("-1") ? -1 : Integer.valueOf(sp[4]));
            account.setCost(BigDecimal.valueOf(sp[5] == null || sp[5].equals("-1") ? -1 : Double.valueOf(sp[5])));
            account.setCtr(sp[6] == null || sp[6].equals("-1") ? -1 : Double.valueOf(sp[6]));
            account.setCpm(BigDecimal.valueOf(sp[7] == null || sp[7].equals("-1") ? -1 : Double.valueOf(sp[7])));
            account.setAcp(BigDecimal.valueOf(sp[8] == null || sp[8].equals("-1") ? -1 : Double.valueOf(sp[8])));
            account.setSrchuv(sp[9] == null || sp[9].equals("-1") ? -1 : Integer.valueOf(sp[9]));
            account.setClickuv(sp[10] == null || sp[10].equals("-1") ? -1 : Integer.valueOf(sp[10]));
            account.setSrsur(sp[11] == null || sp[11].equals("-1") ? -1 : Integer.valueOf(sp[11]));
            account.setCusur(sp[12] == null || sp[12].equals("-1") ? -1 : Double.valueOf(sp[12]));
            account.setCocur(BigDecimal.valueOf(sp[13] == null || sp[13].equals("-1") ? -1 : Double.valueOf(sp[13])));
            account.setArrivalRate(sp[14] == null || sp[14].equals("-1") ? -1 : Double.valueOf(sp[14]));
            account.setHopRate(sp[15] == null || sp[15].equals("-1") ? -1 : Double.valueOf(sp[15]));
            account.setAvgResTime(sp[16] == null || sp[16].equals("-1") ? -1 : Long.valueOf(sp[16]));
            account.setDirectTrans(sp[17] == null || sp[17].equals("-1") ? -1 : Integer.valueOf(sp[17]));
            account.setIndirectTrans(sp[18] == null || sp[18].equals("-1") ? -1 : Integer.valueOf(sp[18]));
            return account;
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        return null;
    };

    private final Function<String, NmsCampaignReportDTO> campaignFunc = (String line) -> {
        String[] sp = line.split("\\t");
        NmsCampaignReportDTO campaign = new NmsCampaignReportDTO();

        campaign.setAccountId(Long.valueOf(sp[1]));
        campaign.setCampaignId(Long.valueOf(sp[3]));
        campaign.setCampaignName(sp[4] == null || sp[4].equals("-1") ? "-1" : sp[4]);

        campaign.setImpression(sp[5] == null || sp[5].equals("-1") ? -1 : Integer.valueOf(sp[5]));
        campaign.setClick(sp[6] == null || sp[6].equals("-1") ? -1 : Integer.valueOf(sp[6]));
        campaign.setCost(BigDecimal.valueOf(sp[7] == null || sp[7].equals("-1") ? -1 : Double.valueOf(sp[7])));
        campaign.setCtr(sp[8] == null || sp[8].equals("-1") ? -1 : Double.valueOf(sp[8]));
        campaign.setCpm(BigDecimal.valueOf(sp[9] == null || sp[9].equals("-1") ? -1 : Double.valueOf(sp[9])));
        campaign.setAcp(BigDecimal.valueOf(sp[10] == null || sp[10].equals("-1") ? -1 : Double.valueOf(sp[10])));
        campaign.setSrchuv(sp[11] == null || sp[11].equals("-1") ? -1 : Integer.valueOf(sp[11]));
        campaign.setClickuv(sp[12] == null || sp[12].equals("-1") ? -1 : Integer.valueOf(sp[12]));
        campaign.setSrsur(sp[13] == null || sp[13].equals("-1") ? -1 : Integer.valueOf(sp[13]));
        campaign.setCusur(sp[14] == null || sp[14].equals("-1") ? -1 : Double.valueOf(sp[14]));
        campaign.setCocur(BigDecimal.valueOf(sp[15] == null || sp[15].equals("-1") ? -1 : Double.valueOf(sp[15])));
        campaign.setArrivalRate(sp[16] == null || sp[16].equals("-1") ? -1 : Double.valueOf(sp[16]));
        campaign.setHopRate(sp[17] == null || sp[17].equals("-1") ? -1 : Double.valueOf(sp[17]));
        campaign.setAvgResTime(sp[18] == null || sp[18].equals("-1") ? -1 : Long.valueOf(sp[18]));
        campaign.setDirectTrans(sp[19] == null || sp[19].equals("-1") ? -1 : Integer.valueOf(sp[19]));
        campaign.setIndirectTrans(sp[20] == null || sp[20].equals("-1") ? -1 : Integer.valueOf(sp[20]));
        return campaign;
    };

    private final Function<String, NmsGroupReportDTO> groupFunc = (String line) -> {
        String[] sp = line.split("\\t");
        NmsGroupReportDTO group = new NmsGroupReportDTO();

        group.setAccountId(Long.parseLong(sp[1]));
        group.setCampaignId(Long.parseLong(sp[3]));
        group.setCampaignName(sp[4] == null || sp[4].equals("-1") ? "-1" : sp[4]);
        group.setGroupId(Long.parseLong(sp[5]));
        group.setGroupName(sp[6] == null || sp[6].equals("-1") ? "-1" : sp[6]);

        group.setImpression(sp[7] == null || sp[7].equals("-1") ? -1 : Integer.valueOf(sp[7]));
        group.setClick(sp[8] == null || sp[8].equals("-1") ? -1 : Integer.valueOf(sp[8]));
        group.setCost(BigDecimal.valueOf(sp[9] == null || sp[9].equals("-1") ? -1 : Double.valueOf(sp[9])));
        group.setCtr(sp[10] == null || sp[10].equals("-1") ? -1 : Double.valueOf(sp[10]));
        group.setCpm(BigDecimal.valueOf(sp[11] == null || sp[11].equals("-1") ? -1 : Double.valueOf(sp[11])));
        group.setAcp(BigDecimal.valueOf(sp[12] == null || sp[12].equals("-1") ? -1 : Double.valueOf(sp[12])));
        group.setSrchuv(sp[13] == null || sp[13].equals("-1") ? -1 : Integer.valueOf(sp[13]));
        group.setClickuv(sp[14] == null || sp[14].equals("-1") ? -1 : Integer.valueOf(sp[14]));
        group.setSrsur(sp[15] == null || sp[15].equals("-1") ? -1 : Integer.valueOf(sp[15]));
        group.setCusur(sp[16] == null || sp[16].equals("-1") ? -1 : Double.valueOf(sp[16]));
        group.setCocur(BigDecimal.valueOf(sp[17] == null || sp[17].equals("-1") ? -1 : Double.valueOf(sp[17])));
        group.setArrivalRate(sp[18] == null || sp[18].equals("-1") ? -1 : Double.valueOf(sp[18]));
        group.setHopRate(sp[19] == null || sp[19].equals("-1") ? -1 : Double.valueOf(sp[19]));
        group.setAvgResTime(sp[20] == null || sp[20].equals("-1") ? -1 : Long.valueOf(sp[20]));
        group.setDirectTrans(sp[21] == null || sp[21].equals("-1") ? -1 : Integer.valueOf(sp[21]));
        group.setIndirectTrans(sp[22] == null || sp[22].equals("-1") ? -1 : Integer.valueOf(sp[22]));
        return group;
    };

    private final Function<String, NmsAdReportDTO> adFunc = (String line) -> {
        String[] sp = line.split("\\t");
        NmsAdReportDTO ad = new NmsAdReportDTO();
        ad.setAccountId(Long.valueOf(sp[1]));
        ad.setCampaignId(Long.valueOf(sp[3]));
        ad.setCampaignName(sp[4] == null || sp[4].equals("-1") ? "-1" : sp[4]);

        ad.setGroupId(Long.valueOf(sp[5]));
        ad.setGroupName(sp[6] == null || sp[6].equals("-1") ? "-1" : sp[6]);
        ad.setAdId(Long.valueOf(sp[7]));
        ad.setAdTitle(sp[8] == null || sp[8].equals("-1") ? "-1" : sp[8]);
        ad.setAdType(sp[9] == null || sp[9].equals("-1") ? -1 : Integer.valueOf(sp[9]));

        ad.setImpression(sp[10] == null || sp[10].equals("-1") ? -1 : Integer.valueOf(sp[10]));
        ad.setClick(sp[11] == null || sp[11].equals("-1") ? -1 : Integer.valueOf(sp[11]));
        ad.setCost(BigDecimal.valueOf(sp[12] == null || sp[12].equals("-1") ? -1 : Double.valueOf(sp[12])));
        ad.setCtr(sp[13] == null || sp[13].equals("-1") ? -1 : Double.valueOf(sp[13]));
        ad.setCpm(BigDecimal.valueOf(sp[14] == null || sp[14].equals("-1") ? -1 : Double.valueOf(sp[14])));
        ad.setAcp(BigDecimal.valueOf(sp[15] == null || sp[15].equals("-1") ? -1 : Double.valueOf(sp[15])));
        ad.setSrchuv(sp[16] == null || sp[16].equals("-1") ? -1 : Integer.valueOf(sp[16]));
        ad.setClickuv(sp[17] == null || sp[17].equals("-1") ? -1 : Integer.valueOf(sp[17]));
        ad.setSrsur(sp[18] == null || sp[18].equals("-1") ? -1 : Integer.valueOf(sp[18]));
        ad.setCusur(sp[19] == null || sp[19].equals("-1") ? -1 : Double.valueOf(sp[19]));
        ad.setCocur(BigDecimal.valueOf(sp[20] == null || sp[20].equals("-1") ? -1 : Double.valueOf(sp[20])));
        ad.setArrivalRate(sp[21] == null || sp[12].equals("-1") ? -1 : Double.valueOf(sp[21]));
        ad.setHopRate(sp[22] == null || sp[22].equals("-1") ? -1 : Double.valueOf(sp[22]));
        ad.setAvgResTime(sp[23] == null || sp[23].equals("-1") ? -1 : Long.valueOf(sp[23]));
        ad.setDirectTrans(sp[24] == null || sp[24].equals("-1") ? -1 : Integer.valueOf(sp[24]));
        ad.setIndirectTrans(sp[25] == null || sp[25].equals("-1") ? -1 : Integer.valueOf(sp[25]));
        return ad;
    };


    /**
     * 账户执行器
     */
    class AccountAction implements Action1<String> {

        @Override
        public void call(String s) {
            // download report and parse data
            Map<String, List<String>> linesMap = readAllLines(s);
            String dateStr = headKey(linesMap);
            List<String> lines = headValue(linesMap);


            if (StringUtils.isNotEmpty(dateStr) && !lines.isEmpty()) {
                List<NmsAccountReportDTO> accountReportList =
                        lines.stream().map(accountFunc).filter(o -> o != null).collect(Collectors.toList());
                // save to mongodb
                if (accountReportList.size() != 0) {
                    SystemUserDTO systemUserDTO = systemUserDAO.findByAid(accountReportList.get(0).getAccountId());
                    asynchronousNmsReportDAO.getNmsAccountReportData(accountReportList, systemUserDTO, dateStr, accountReportList.get(0).getAccountName());
                }

            }
        }
    }

    /**
     * 推广计划执行器
     */
    class CampaignAction implements Action1<String> {

        @Override
        public void call(String s) {
            // download report and parse data
            Map<String, List<String>> linesMap = readAllLines(s);
            String dateStr = headKey(linesMap);
            List<String> lines = headValue(linesMap);

            if (StringUtils.isNotEmpty(dateStr) && !lines.isEmpty()) {
                List<NmsCampaignReportDTO> campaignReportList =
                        lines.stream().map(campaignFunc).collect(Collectors.toList());
                // save to mongodb
                if (campaignReportList.size() != 0) {
                    SystemUserDTO systemUserDTO = systemUserDAO.findByAid(campaignReportList.get(0).getAccountId());
                    asynchronousNmsReportDAO.getNmsCampaignReportData(campaignReportList, systemUserDTO, dateStr);
                }
            }
        }
    }

    /**
     * 推广组执行器
     */
    class GroupAction implements Action1<String> {

        @Override
        public void call(String s) {
            // download report and parse data
            Map<String, List<String>> linesMap = readAllLines(s);
            String dateStr = headKey(linesMap);
            List<String> lines = headValue(linesMap);

            if (StringUtils.isNotEmpty(dateStr) && !lines.isEmpty()) {
                List<NmsGroupReportDTO> groupReportList =
                        lines.stream().map(groupFunc).collect(Collectors.toList());
                // save to mongodb
                if (groupReportList.size() != 0) {
                    SystemUserDTO systemUserDTO = systemUserDAO.findByAid(groupReportList.get(0).getAccountId());
                    asynchronousNmsReportDAO.getNmsGroupReportData(groupReportList, systemUserDTO, dateStr);
                }
            }
        }
    }

    /**
     * 创意执行器
     */
    class AdAction implements Action1<String> {

        @Override
        public void call(String s) {
            // download report and parse data
            Map<String, List<String>> linesMap = readAllLines(s);
            String dateStr = headKey(linesMap);
            List<String> lines = headValue(linesMap);

            if (StringUtils.isNotEmpty(dateStr) && !lines.isEmpty()) {
                List<NmsAdReportDTO> adReportList =
                        lines.stream().map(adFunc).collect(Collectors.toList());
                // save to mongodb
                if (adReportList.size() != 0) {
                    SystemUserDTO systemUserDTO = systemUserDAO.findByAid(adReportList.get(0).getAccountId());
                    asynchronousNmsReportDAO.getNmsAdReportData(adReportList, systemUserDTO, dateStr);
                }
            }
        }
    }

    /**
     * 1. 调用百度网盟推广Api生成报告ID
     * 2. ReportFileUrlTask {@link com.perfect.nms.ReportFileUrlTask},
     * 依据报告ID解析出报告具体的url下载地址, 解析完毕, 返回任务完成标识
     */
    class ReportTask implements Callable<Boolean> {

        private final ReportFileUrlTask fileUrlTask;
        private final Date[] dates;
        private final String[] args;


        public ReportTask(ReportFileUrlTask fileUrlTask, Date[] dates, String... args) {
            this.fileUrlTask = fileUrlTask;
            this.dates = dates;
            this.args = args;
        }

        @Override
        public Boolean call() throws Exception {
            NmsReportIdAPI nmsApi = new NmsReportIdAPI(fileUrlTask);
            List<SystemUserDTO> systemUserList;

            int l = args.length;
            if (l == 0) {
                // 拉取全部报告
                systemUserList = getBaiduUser(null);
                if (systemUserList != null && !systemUserList.isEmpty()) {
                    systemUserList.forEach(sysUser -> {
                        sysUser.getBaiduAccounts().forEach(ba -> {
                            nmsApi.getAllApi(ba.getBaiduUserName(), ba.getBaiduPassword(), ba.getToken(), dates);
                        });
                    });
                }
            } else if (l == 1) {
                // 拉取指定用户的报告
                systemUserList = getBaiduUser(args[0]);
                if (systemUserList != null && !systemUserList.isEmpty()) {
                    SystemUserDTO systemUser = systemUserList.get(0);
                    systemUser.getBaiduAccounts()
                            .forEach(ba -> nmsApi.getAllApi(ba.getBaiduUserName(), ba.getBaiduPassword(), ba.getToken(), dates));
                }
            } else if (l == 2) {
                // 拉取指定用户的某一类型报告
                systemUserList = getBaiduUser(args[0]);
                int type = Integer.parseInt(args[1]);   // 1 -> account, 2 -> campaign, 3 -> group, 4 -> ad

                if (systemUserList != null && !systemUserList.isEmpty()) {
                    SystemUserDTO systemUser = systemUserList.get(0);

                    switch (type) {
                        case 1:
                            systemUser.getBaiduAccounts()
                                    .forEach(ba -> nmsApi.getAccountApi(ba.getBaiduUserName(), ba.getBaiduPassword(), ba.getToken(), dates));
                            break;
                        case 2:
                            systemUser.getBaiduAccounts()
                                    .forEach(ba -> nmsApi.getCampaignApi(ba.getBaiduUserName(), ba.getBaiduPassword(), ba.getToken(), dates));
                            break;
                        case 3:
                            systemUser.getBaiduAccounts()
                                    .forEach(ba -> nmsApi.getGroupApi(ba.getBaiduUserName(), ba.getBaiduPassword(), ba.getToken(), dates));
                            break;
                        case 4:
                            systemUser.getBaiduAccounts()
                                    .forEach(ba -> nmsApi.getAdbyGroupApi(ba.getBaiduUserName(), ba.getBaiduPassword(), ba.getToken(), dates));
                            break;
                        default:
                            break;
                    }
                }

            }

            // SET COMPLETE STATUS
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                jedis.set(REPORT_FILE_URL_GENERATE_COMPLETE, ONE);
            } finally {
                closeRedis(jedis);
            }

            return true;
        }
    }

}
