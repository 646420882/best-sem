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
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import redis.clients.jedis.Jedis;
import rx.Observable;
import rx.functions.Action1;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import static com.perfect.commons.constants.RedisConstants.REPORT_FILE_URL_SUCCEED;
import static com.perfect.commons.constants.RedisConstants.REPORT_ID_COMMIT_STATUS;

/**
 * Created by subdong on 15-7-21.
 */
public class AsynchronousNmsReportServiceImpl implements AsynchronousNmsReportService {

    @Resource
    private AsynchronousNmsReportDAO asynchronousNmsReportDAO;

    @Resource
    private SystemUserDAO systemUserDAO;


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
                jedis.close();
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
                    jedis.close();
                    break;
                }

                if (value == null) {
                    jedis.close();
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
                    jedis.close();
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
}
