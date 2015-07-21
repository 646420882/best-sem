package com.perfect.service.impl;

import com.google.common.collect.Lists;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.account.NmsAccountReportDTO;
import com.perfect.dto.account.NmsAdReportDTO;
import com.perfect.dto.account.NmsCampaignReportDTO;
import com.perfect.dto.account.NmsGroupReportDTO;
import com.perfect.nms.NmsReportIdAPI;
import com.perfect.service.AsynchronousNmsReportService;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.redis.JRedisUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import static com.perfect.commons.constants.RedisConstants.REPORT_FILE_URL_SUCCEED;
import static com.perfect.commons.constants.RedisConstants.REPORT_ID_COMMIT_STATUS;

/**
 * Created by subdong on 15-7-21.
 */
public class AsynchronousNmsReportServiceImpl implements AsynchronousNmsReportService {

    @Resource
    private SystemUserDAO systemUserDAO;

    @Override
    public void getNmsAccountReportData(Date dateStr, String userName) {
        List<SystemUserDTO> systemUserList = this.getBaiduUser(userName);
        systemUserList.forEach(user -> {
            user.getBaiduAccounts().forEach(baiduAccount -> {
                NmsReportIdAPI nmsReportIdAPI = new NmsReportIdAPI();
                nmsReportIdAPI.getAccountApi(baiduAccount.getBaiduUserName(), baiduAccount.getBaiduPassword(), baiduAccount.getToken());
            });
        });

    }

    @Override
    public void getNmsCampaignReportData(Date dateStr, String userName) {

    }

    @Override
    public void getNmsGroupReportData(Date dateStr, String userName) {

    }

    @Override
    public void getNmsAdReportData(Date dateStr, String userName) {

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
            boolean judge = (e.getState() != 0 && e.getBaiduAccounts().size() > 0 && e.getAccess() == 2 && e.getAccountState() > 0);
            return judge;
        }).collect(Collectors.toList());
        return newEntityList;
    }


    class HttpFileHandler {

        private BlockingQueue<String> accountQueue = new LinkedBlockingQueue<>();
        private BlockingQueue<String> campaignQueue = new LinkedBlockingQueue<>();
        private BlockingQueue<String> groupQueue = new LinkedBlockingQueue<>();
        private BlockingQueue<String> adQueue = new LinkedBlockingQueue<>();


        public HttpFileHandler() {
            init();
        }

        private void init() {
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
                            accountQueue.add(fileUrl);
                            break;
                        case 2:
                            campaignQueue.add(fileUrl);
                            break;
                        case 3:
                            groupQueue.add(fileUrl);
                            break;
                        case 4:
                            adQueue.add(fileUrl);
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

        public List<NmsAccountReportDTO> getNmsAccountReport(String fileUrl) {
            return Collections.emptyList();
        }

        public List<NmsCampaignReportDTO> getNmsCampaignReport(String fileUrl) {
            return Collections.emptyList();
        }

        public List<NmsGroupReportDTO> getNmsGroupReport(String fileUrl) {
            return Collections.emptyList();
        }

        public List<NmsAdReportDTO> getNmsAdReport(String fileUrl) {
            return Collections.emptyList();
        }
    }
}
