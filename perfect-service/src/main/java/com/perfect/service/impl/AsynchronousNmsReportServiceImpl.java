package com.perfect.service.impl;

import com.google.common.collect.Lists;
import com.perfect.dao.report.AsynchronousNmsReportDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.account.NmsAccountReportDTO;
import com.perfect.nms.NmsReportIdAPI;
import com.perfect.nms.ReportFileUrlTask;
import com.perfect.service.AsynchronousNmsReportService;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.redis.JRedisUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.perfect.commons.constants.RedisConstants.REPORT_FILE_URL_SUCCEED;
import static com.perfect.commons.constants.RedisConstants.REPORT_ID_COMMIT_STATUS;

/**
 * Created by subdong on 15-7-21.
 */
@Service(value = "asynchronousNmsReportService")
public class AsynchronousNmsReportServiceImpl implements AsynchronousNmsReportService {

    @Resource
    private AsynchronousNmsReportDAO asynchronousNmsReportDAO;

    @Resource
    private SystemUserDAO systemUserDAO;

    private final JedisPool pool = JRedisUtils.getPool();


    @Override
    public void generateReportId(Date[] dates, String... args) {
        if (dates == null || dates.length == 0) {
            dates = new Date[]{null, null};
        }

        final Date[] _dates = dates;

        Jedis jedis = null;
        try {
            jedis = JRedisUtils.get();
            jedis.set(REPORT_ID_COMMIT_STATUS, "0");
        } finally {
            if (jedis != null)
                closeRedis(jedis);
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            ReportFileUrlTask fileUrlTask = new ReportFileUrlTask();
            NmsReportIdAPI nmsApi = new NmsReportIdAPI(fileUrlTask);
            List<SystemUserDTO> systemUserList;

            int l = args.length;
            if (l == 0) {
                // 拉取全部报告
                systemUserList = getBaiduUser(null);
                if (systemUserList != null && !systemUserList.isEmpty()) {
                    systemUserList.forEach(sysUser -> {
                        sysUser.getBaiduAccounts().forEach(ba -> {
                            nmsApi.getAllApi(ba.getBaiduUserName(), ba.getBaiduPassword(), ba.getToken(), _dates);
                        });
                    });
                }
            } else if (l == 1) {
                // 拉取指定用户的报告
                systemUserList = getBaiduUser(args[0]);
                if (systemUserList != null && !systemUserList.isEmpty()) {
                    SystemUserDTO systemUser = systemUserList.get(0);
                    systemUser.getBaiduAccounts()
                            .forEach(ba -> nmsApi.getAllApi(ba.getBaiduUserName(), ba.getBaiduPassword(), ba.getToken(), _dates));
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
                                    .forEach(ba -> nmsApi.getAccountApi(ba.getBaiduUserName(), ba.getBaiduPassword(), ba.getToken(), _dates));
                            break;
                        case 2:
                            systemUser.getBaiduAccounts()
                                    .forEach(ba -> nmsApi.getCampaignApi(ba.getBaiduUserName(), ba.getBaiduPassword(), ba.getToken(), _dates));
                            break;
                        case 3:
                            systemUser.getBaiduAccounts()
                                    .forEach(ba -> nmsApi.getGroupApi(ba.getBaiduUserName(), ba.getBaiduPassword(), ba.getToken(), _dates));
                            break;
                        case 4:
                            systemUser.getBaiduAccounts()
                                    .forEach(ba -> nmsApi.getAdbyGroupApi(ba.getBaiduUserName(), ba.getBaiduPassword(), ba.getToken(), _dates));
                            break;
                        default:
                            break;
                    }
                }

            }
        });

        Executors.newSingleThreadExecutor().execute(this::readReportFileUrlFromRedis);

    }

    @Override
    public void readReportFileUrlFromRedis() {
        while (true) {
            Jedis jedis = null;
            try {
                jedis = JRedisUtils.get();
                String value = jedis.rpop(REPORT_FILE_URL_SUCCEED);
                String status = jedis.get(REPORT_ID_COMMIT_STATUS);
                if (value == null && "1".equals(status)) {
                    closeRedis(jedis);
                    break;
                }

                if (value == null) {
                    closeRedis(jedis);
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

            } finally {
                if (jedis != null)
                    closeRedis(jedis);
            }
        }
    }

    //获取用户公用方法
    public List<SystemUserDTO> getBaiduUser(String userName) {
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


    class AccountAction implements Action1<String> {

        @Override
        public void call(String s) {
            // 解析Url生成报告并入库
            List<NmsAccountReportDTO> list = new ArrayList<>();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(s);

            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));

                List<String> lines = br.lines().collect(Collectors.toList());
                if (lines != null && !lines.isEmpty())
                    lines.remove(0);
                else
                    return;

                lines.forEach(e -> {
                        try {
                            String[] sp = e.split("\\t");
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

                            System.out.println();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                    });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class CampaignAction implements Action1<String> {

        @Override
        public void call(String s) {
            // implement
        }
    }

    class GroupAction implements Action1<String> {

        @Override
        public void call(String s) {
            // implement
        }
    }

    class AdAction implements Action1<String> {

        @Override
        public void call(String s) {
            // implement
        }
    }

    private void closeRedis(Jedis jedis) {
        if (jedis != null && pool.getNumActive() > 0) {
            jedis.close();
        }
    }

    public static void main(String[] args) {

        AsynchronousNmsReportServiceImpl asynchronousNmsReportService = new AsynchronousNmsReportServiceImpl();
        asynchronousNmsReportService.generateReportId(null, "perfect2015", "1");

        //httpFileHandler.getNmsAccountReport("https://apidata.baidu.com/data/v2/getFile.do?t=1437469726&u=10394588&i=398ad60984563e2f1b846d37ba4080d3&f=%2Fapireport%2F398ad60984563e2f1b846d37ba4080d3&h=400&s=82ec14640ccae476c40d6962442d6d83");
    }
}
