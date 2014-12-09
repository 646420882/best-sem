package com.perfect.service.impl;

import com.google.common.collect.Lists;
import com.perfect.api.baidu.AsynchronousReport;
import com.perfect.dao.report.AsynchronousReportDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.account.*;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.keyword.KeywordReportDTO;
import com.perfect.service.AsynchronousReportService;
import com.perfect.utils.ObjectUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Repository;

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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by baizz on 2014-08-07.
 * 2014-11-26 refactor
 */
@Repository("asynchronousReportService")
public class AsynchronousReportServiceImpl implements AsynchronousReportService {

    private HttpFileHandler httpFileHandler = new HttpFileHandler();

    @Resource
    private AsynchronousReportDAO asynchronousReportDAO;

    @Resource
    private SystemUserDAO systemUserDAO;

    private List<AccountReportDTO> acrmList;

    private List<CampaignReportDTO> cprmList;

    private List<AdgroupReportDTO> armList;

    private List<CreativeReportDTO> crmList;

    private List<KeywordReportDTO> krmList;

    private List<RegionReportDTO> rrmList;

    public void getAccountReportData(String dateStr, String userName) {

        List<SystemUserDTO> entityList = new ArrayList<>();
        if (userName == null) {
            Iterable<SystemUserDTO> entities = systemUserDAO.findAll();
            entityList = ObjectUtils.convertToList(Lists.newArrayList(entities), SystemUserDTO.class);
        } else {
            SystemUserDTO userEntity = systemUserDAO.findByUserName(userName);
            entityList.add(userEntity);
        }

        for (SystemUserDTO systemUser : entityList) {
            if (systemUser.getState() == 0 || systemUser.getBaiduAccounts() == null || systemUser.getBaiduAccounts().size() <= 0 || systemUser.getAccess() == 1 || systemUser.getAccountState() == 0) {
                continue;
            }
            for (BaiduAccountInfoDTO entity : systemUser.getBaiduAccounts()) {

                if (entity.getState() == 0) {
                    continue;
                }

                AsynchronousReport report = new AsynchronousReport(entity.getBaiduUserName(), entity.getBaiduPassword(), entity.getToken());
                String pcFilePath = report.getAccountReportDataPC(null, dateStr, dateStr);
                String mobileFilePath = report.getAccountReportDataMobile(null, dateStr, dateStr);
                if (pcFilePath == null && mobileFilePath == null) {
                    continue;
                }
                List<AccountReportDTO> pcList = httpFileHandler.getAccountReport(pcFilePath, 1);
                acrmList = httpFileHandler.getAccountReport(mobileFilePath, 2);

                ForkJoinPool forkJoinPool = new ForkJoinPool();
                AccountReportHandler task = new AccountReportHandler(pcList, 0, pcList.size());
                Future<List<AccountReportDTO>> voResult = forkJoinPool.submit(task);
                List<AccountReportDTO> list;
                try {
                    list = voResult.get();
                    asynchronousReportDAO.getAccountReportData(list, systemUser, dateStr);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    forkJoinPool.shutdown();
                }
            }
        }
    }

    public void getCampaignReportData(String dateStr, String userName) {

        List<SystemUserDTO> entityList = new ArrayList<>();
        if (userName == null) {
            Iterable<SystemUserDTO> entities = systemUserDAO.findAll();
            entityList = ObjectUtils.convertToList(Lists.newArrayList(entities), SystemUserDTO.class);
        } else {
            SystemUserDTO userEntity = systemUserDAO.findByUserName(userName);
            entityList.add(userEntity);
        }

        for (SystemUserDTO systemUser : entityList) {
            if (systemUser.getState() == 0 || systemUser.getBaiduAccounts() == null || systemUser.getBaiduAccounts().size() <= 0 || systemUser.getAccess() == 1 || systemUser.getAccountState() == 0) {
                continue;
            }
            for (BaiduAccountInfoDTO entity : systemUser.getBaiduAccounts()) {

                if (entity.getState() == 0) {
                    continue;
                }
                AsynchronousReport report = new AsynchronousReport(entity.getBaiduUserName(), entity.getBaiduPassword(), entity.getToken());
                String pcFilePath = report.getCampaignReportDataPC(null, null, dateStr, dateStr);
                String mobileFilePath = report.getCampaignReportDataMobile(null, null, dateStr, dateStr);
                if (pcFilePath == null && mobileFilePath == null) {
                    continue;
                }
                List<CampaignReportDTO> pcList = httpFileHandler.getCampaignReport(pcFilePath, 1);
                cprmList = httpFileHandler.getCampaignReport(mobileFilePath, 2);

                ForkJoinPool forkJoinPool = new ForkJoinPool();
                CampaignReportHandler task = new CampaignReportHandler(pcList, 0, pcList.size());
                Future<List<CampaignReportDTO>> voResult = forkJoinPool.submit(task);
                List<CampaignReportDTO> list;
                try {
                    list = voResult.get();
                    asynchronousReportDAO.getCampaignReportData(list, systemUser, dateStr);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    forkJoinPool.shutdown();
                }
            }
        }
    }

    public void getAdgroupReportData(String dateStr, String userName) {

        List<SystemUserDTO> entityList = new ArrayList<>();
        if (userName == null) {
            Iterable<SystemUserDTO> entities = systemUserDAO.findAll();
            entityList = ObjectUtils.convertToList(Lists.newArrayList(entities), SystemUserDTO.class);
        } else {
            SystemUserDTO userEntity = systemUserDAO.findByUserName(userName);
            entityList.add(userEntity);
        }

        for (SystemUserDTO systemUser : entityList) {
            if (systemUser.getState() == 0 || systemUser.getBaiduAccounts() == null || systemUser.getBaiduAccounts().size() <= 0 || systemUser.getAccess() == 1 || systemUser.getAccountState() == 0) {
                continue;
            }
            for (BaiduAccountInfoDTO entity : systemUser.getBaiduAccounts()) {

                if (entity.getState() == 0) {
                    continue;
                }

                AsynchronousReport report = new AsynchronousReport(entity.getBaiduUserName(), entity.getBaiduPassword(), entity.getToken());

                String pcFilePath = report.getUnitReportDataPC(null, null, dateStr, dateStr);
                String mobileFilePath = report.getUnitReportDataMobile(null, null, dateStr, dateStr);
                if (pcFilePath == null && mobileFilePath == null) {
                    continue;
                }
                List<AdgroupReportDTO> pcList = httpFileHandler.getAdgroupReport(pcFilePath, 1);
                armList = httpFileHandler.getAdgroupReport(mobileFilePath, 2);

                ForkJoinPool forkJoinPool = new ForkJoinPool();
                AdgroupReportHandler task = new AdgroupReportHandler(pcList, 0, pcList.size());
                Future<List<AdgroupReportDTO>> voResult = forkJoinPool.submit(task);
                List<AdgroupReportDTO> list;
                try {
                    list = voResult.get();
                    asynchronousReportDAO.getAdgroupReportData(list, systemUser, dateStr);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    forkJoinPool.shutdown();
                }
            }
        }
    }

    public void getCreativeReportData(String dateStr, String userName) {

        List<SystemUserDTO> entityList = new ArrayList<>();
        if (userName == null) {
            Iterable<SystemUserDTO> entities = systemUserDAO.findAll();
            entityList = ObjectUtils.convertToList(Lists.newArrayList(entities), SystemUserDTO.class);
        } else {
            SystemUserDTO userEntity = systemUserDAO.findByUserName(userName);
            entityList.add(userEntity);
        }

        for (SystemUserDTO systemUser : entityList) {
            if (systemUser.getState() == 0 || systemUser.getBaiduAccounts() == null || systemUser.getBaiduAccounts().size() <= 0 || systemUser.getAccess() == 1 || systemUser.getAccountState() == 0) {
                continue;
            }
            for (BaiduAccountInfoDTO entity : systemUser.getBaiduAccounts()) {

                if (entity.getState() == 0) {
                    continue;
                }

                AsynchronousReport report = new AsynchronousReport(entity.getBaiduUserName(), entity.getBaiduPassword(), entity.getToken());

                String pcFilePath = report.getCreativeReportDataPC(null, null, dateStr, dateStr);
                String mobileFilePath = report.getCreativeReportDataMobile(null, null, dateStr, dateStr);
                if (pcFilePath == null && mobileFilePath == null) {
                    continue;
                }
                List<CreativeReportDTO> pcList = httpFileHandler.getCreativeReport(pcFilePath, 1);
                crmList = httpFileHandler.getCreativeReport(mobileFilePath, 2);

                ForkJoinPool forkJoinPool = new ForkJoinPool();
                CreativeReportHandler task = new CreativeReportHandler(pcList, 0, pcList.size());
                Future<List<CreativeReportDTO>> voResult = forkJoinPool.submit(task);
                List<CreativeReportDTO> list;
                try {
                    list = voResult.get();
                    asynchronousReportDAO.getCreativeReportData(list, systemUser, dateStr);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    forkJoinPool.shutdown();
                }
            }
        }
    }

    public void getKeywordReportData(String dateStr, String userName) {
        List<SystemUserDTO> entityList = new ArrayList<>();
        if (userName == null) {
            Iterable<SystemUserDTO> entities = systemUserDAO.findAll();
            entityList = ObjectUtils.convertToList(Lists.newArrayList(entities), SystemUserDTO.class);
        } else {
            SystemUserDTO userEntity = systemUserDAO.findByUserName(userName);
            entityList.add(userEntity);
        }

        for (SystemUserDTO systemUser : entityList) {
            if (systemUser.getState() == 0 || systemUser.getBaiduAccounts() == null || systemUser.getBaiduAccounts().size() <= 0 || systemUser.getAccess() == 1 || systemUser.getAccountState() == 0) {
                continue;
            }
            for (BaiduAccountInfoDTO entity : systemUser.getBaiduAccounts()) {

                if (entity.getState() == 0) {
                    continue;
                }

                AsynchronousReport report = new AsynchronousReport(entity.getBaiduUserName(), entity.getBaiduPassword(), entity.getToken());

                String pcFilePath = report.getKeyWordidReportDataPC(null, null, dateStr, dateStr);
                String mobileFilePath = report.getKeyWordidReportDataMobile(null, null, dateStr, dateStr);
                if (pcFilePath == null && mobileFilePath == null) {
                    continue;
                }
                List<KeywordReportDTO> pcList = httpFileHandler.getKeywordReport(pcFilePath, 1);
                krmList = httpFileHandler.getKeywordReport(mobileFilePath, 2);

                ForkJoinPool forkJoinPool = new ForkJoinPool();
                KeywordReportHandler task = new KeywordReportHandler(pcList, 0, pcList.size());
                Future<List<KeywordReportDTO>> voResult = forkJoinPool.submit(task);
                List<KeywordReportDTO> list;
                try {
                    list = voResult.get();
                    asynchronousReportDAO.getKeywordReportData(list, systemUser, dateStr);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    forkJoinPool.shutdown();
                }
            }
        }
    }

    public void getRegionReportData(String dateStr, String userName) {
        List<SystemUserDTO> entityList = new ArrayList<>();
        if (userName == null) {
            Iterable<SystemUserDTO> entities = systemUserDAO.findAll();
            entityList = ObjectUtils.convertToList(Lists.newArrayList(entities), SystemUserDTO.class);
        } else {
            SystemUserDTO userEntity = systemUserDAO.findByUserName(userName);
            entityList.add(userEntity);
        }

        for (SystemUserDTO systemUser : entityList) {
            if (systemUser.getState() == 0 || systemUser.getBaiduAccounts() == null || systemUser.getBaiduAccounts().size() <= 0 || systemUser.getAccess() == 1 || systemUser.getAccountState() == 0) {
                continue;
            }
            for (BaiduAccountInfoDTO entity : systemUser.getBaiduAccounts()) {

                if (entity.getState() == 0) {
                    continue;
                }

                AsynchronousReport report = new AsynchronousReport(entity.getBaiduUserName(), entity.getBaiduPassword(), entity.getToken());

                String pcFilePath = report.getRegionalReportDataPC(null, null, dateStr, dateStr);
                /*String mobileFilePath = report.getRegionalReportDataMobile(null, null, dateStr, dateStr); */
                if (pcFilePath == null /*|| mobileFilePath == null*/) {
                    continue;
                }

                List<RegionReportDTO> pcList = httpFileHandler.getRegionReport(pcFilePath, 1);
                /*rrmList = httpFileHandler.getRegionReport(mobileFilePath, 2);*/
                rrmList = new ArrayList<>();
                ForkJoinPool forkJoinPool = new ForkJoinPool();
                RegionReportHandler task = new RegionReportHandler(pcList, 0, pcList.size());
                Future<List<RegionReportDTO>> voResult = forkJoinPool.submit(task);
                List<RegionReportDTO> list;
                try {
                    list = voResult.get();
                    asynchronousReportDAO.getRegionReportData(list, systemUser, dateStr);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    forkJoinPool.shutdown();
                }
            }
        }
    }

    class HttpFileHandler {

        public List<AccountReportDTO> getAccountReport(String filePath, int type) {
            List<AccountReportDTO> list = new ArrayList<>();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(filePath);
            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "GBK"));
                String str;
                int index = -1;
                while ((str = br.readLine()) != null) {
                    if (index == -1) {
                        index++;
                        continue;
                    }
                    String[] arr = str.split("\\t");
                    AccountReportDTO entity1 = new AccountReportDTO();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = format.parse(arr[0]);
                    entity1.setDate(date);
                    entity1.setAccountId(Long.valueOf(arr[1]));
                    entity1.setAccountName(arr[2]);
                    if (type == 1) {
                        entity1.setPcImpression(Integer.valueOf(arr[3]));
                        entity1.setPcClick(Integer.valueOf(arr[4]));
                        entity1.setPcCost(BigDecimal.valueOf(Double.valueOf(arr[5])));
                        entity1.setPcCtr(Double.valueOf(arr[6].substring(0, arr[6].length() - 1)));
                        entity1.setPcCpc(BigDecimal.valueOf(Double.valueOf(arr[7])));
                        entity1.setPcCpm(BigDecimal.valueOf(Double.valueOf(arr[8])));
                        entity1.setPcConversion(Double.valueOf(arr[9]));
                    } else if (type == 2) {
                        entity1.setMobileImpression(Integer.valueOf(arr[3]));
                        entity1.setMobileClick(Integer.valueOf(arr[4]));
                        entity1.setMobileCost(BigDecimal.valueOf(Double.valueOf(arr[5])));
                        entity1.setMobileCtr(Double.valueOf(arr[6].substring(0, arr[6].length() - 1)));
                        entity1.setMobileCpc(BigDecimal.valueOf(Double.valueOf(arr[7])));
                        entity1.setMobileCpm(BigDecimal.valueOf(Double.valueOf(arr[8])));
                        entity1.setMobileConversion(Double.valueOf(arr[9]));
                    }
                    list.add(entity1);
                    index++;
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }


        public List<CampaignReportDTO> getCampaignReport(String filePath, int type) {
            List<CampaignReportDTO> list = new ArrayList<>();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(filePath);
            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "GBK"));
                String str;
                int index = -1;
                while ((str = br.readLine()) != null) {
                    if (index == -1) {
                        index++;
                        continue;
                    }
                    String[] arr = str.split("\\t");
                    CampaignReportDTO entity1 = new CampaignReportDTO();
                    entity1.setAccountId(Long.valueOf(arr[1]));
                    entity1.setCampaignId(Long.valueOf(arr[3]));
                    entity1.setCampaignName(arr[4]);
                    if (type == 1) {
                        entity1.setPcImpression(Integer.valueOf(arr[5]));
                        entity1.setPcClick(Integer.valueOf(arr[6]));
                        entity1.setPcCost(BigDecimal.valueOf(Double.valueOf(arr[7])));
                        entity1.setPcCtr(Double.valueOf(arr[8].substring(0, arr[8].length() - 1)));
                        entity1.setPcCpc(BigDecimal.valueOf(Double.valueOf(arr[9])));
                        entity1.setPcCpm(BigDecimal.valueOf(Double.valueOf(arr[10])));
                        entity1.setPcConversion(Double.valueOf(arr[11]));
                    } else if (type == 2) {
                        entity1.setMobileImpression(Integer.valueOf(arr[5]));
                        entity1.setMobileClick(Integer.valueOf(arr[6]));
                        entity1.setMobileCost(BigDecimal.valueOf(Double.valueOf(arr[7])));
                        entity1.setMobileCtr(Double.valueOf(arr[8].substring(0, arr[8].length() - 1)));
                        entity1.setMobileCpc(BigDecimal.valueOf(Double.valueOf(arr[9])));
                        entity1.setMobileCpm(BigDecimal.valueOf(Double.valueOf(arr[10])));
                        entity1.setMobileConversion(Double.valueOf(arr[11]));
                    }
                    list.add(entity1);
                    index++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }

        public List<AdgroupReportDTO> getAdgroupReport(String filePath, int type) {
            List<AdgroupReportDTO> list = new ArrayList<>();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(filePath);
            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "GBK"));
                String str;
                int index = -1;
                while ((str = br.readLine()) != null) {
                    if (index == -1) {
                        index++;
                        continue;
                    }
                    String[] arr = str.split("\\t");
                    AdgroupReportDTO entity1 = new AdgroupReportDTO();
                    entity1.setAccountId(Long.valueOf(arr[1]));
                    entity1.setCampaignId(Long.valueOf(arr[3]));
                    entity1.setCampaignName(arr[4]);
                    entity1.setAdgroupId(Long.valueOf(arr[5]));
                    entity1.setAdgroupName(arr[6]);
                    if (type == 1) {
                        entity1.setPcImpression(Integer.valueOf(arr[7]));
                        entity1.setPcClick(Integer.valueOf(arr[8]));
                        entity1.setPcCost(BigDecimal.valueOf(Double.valueOf(arr[9])));
                        entity1.setPcCtr(Double.valueOf(arr[10].substring(0, arr[10].length() - 1)));
                        entity1.setPcCpc(BigDecimal.valueOf(Double.valueOf(arr[11])));
                        entity1.setPcCpm(BigDecimal.valueOf(Double.valueOf(arr[12])));
                        entity1.setPcConversion(Double.valueOf(arr[13]));
                    } else if (type == 2) {
                        entity1.setMobileImpression(Integer.valueOf(arr[7]));
                        entity1.setMobileClick(Integer.valueOf(arr[8]));
                        entity1.setMobileCost(BigDecimal.valueOf(Double.valueOf(arr[9])));
                        entity1.setMobileCtr(Double.valueOf(arr[10].substring(0, arr[10].length() - 1)));
                        entity1.setMobileCpc(BigDecimal.valueOf(Double.valueOf(arr[11])));
                        entity1.setMobileCpm(BigDecimal.valueOf(Double.valueOf(arr[12])));
                        entity1.setMobileConversion(Double.valueOf(arr[13]));
                    }
                    list.add(entity1);
                    index++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }

        public List<CreativeReportDTO> getCreativeReport(String filePath, int type) {
            List<CreativeReportDTO> list = new ArrayList<>();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(filePath);
            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "GBK"));
                String str;
                int index = -1;
                while ((str = br.readLine()) != null) {
                    if (index == -1) {
                        index++;
                        continue;
                    }
                    String[] arr = str.split("\\t");
                    CreativeReportDTO entity1 = new CreativeReportDTO();
                    entity1.setCreativeId(Long.valueOf(arr[7]));
                    entity1.setCreativeTitle(arr[8]);
                    entity1.setDescription1(arr[9]);
                    entity1.setDescription2(arr[10]);
                    entity1.setShowUrl(arr[11]);
                    entity1.setAccountId(Long.valueOf(arr[1]));
                    entity1.setCampaignId(Long.valueOf(arr[3]));
                    entity1.setCampaignName(arr[4]);
                    entity1.setAdgroupId(Long.valueOf(arr[5]));
                    entity1.setAdgroupName(arr[6]);
                    if (type == 1) {
                        entity1.setPcImpression(Integer.valueOf(arr[12]));
                        entity1.setPcClick(Integer.valueOf(arr[13]));
                        entity1.setPcCost(BigDecimal.valueOf(Double.valueOf(arr[14])));
                        entity1.setPcCtr(Double.valueOf(arr[15].substring(0, arr[15].length() - 1)));
                        entity1.setPcCpc(BigDecimal.valueOf(Double.valueOf(arr[16])));
                        entity1.setPcCpm(BigDecimal.valueOf(Double.valueOf(arr[17])));
                        entity1.setPcConversion(Double.valueOf(arr[18]));
                    } else if (type == 2) {
                        entity1.setMobileImpression(Integer.valueOf(arr[12]));
                        entity1.setMobileClick(Integer.valueOf(arr[13]));
                        entity1.setMobileCost(BigDecimal.valueOf(Double.valueOf(arr[14])));
                        entity1.setMobileCtr(Double.valueOf(arr[15].substring(0, arr[15].length() - 1)));
                        entity1.setMobileCpc(BigDecimal.valueOf(Double.valueOf(arr[16])));
                        entity1.setMobileCpm(BigDecimal.valueOf(Double.valueOf(arr[17])));
                        entity1.setMobileConversion(Double.valueOf(arr[18]));
                    }
                    list.add(entity1);
                    index++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }

        public List<KeywordReportDTO> getKeywordReport(String filePath, int type) {
            List<KeywordReportDTO> list = new ArrayList<>();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(filePath);
            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "GBK"));
                String str;
                int index = -1;
                while ((str = br.readLine()) != null) {
                    if (index == -1) {
                        index++;
                        continue;
                    }
                    String[] arr = str.split("\\t");
                    KeywordReportDTO entity1 = new KeywordReportDTO();

                    entity1.setKeywordId(Long.valueOf(arr[7]));
                    entity1.setKeywordName(arr[9]);
                    entity1.setAccountId(Long.valueOf(arr[1]));
                    entity1.setCampaignId(Long.valueOf(arr[3]));
                    entity1.setCampaignName(arr[4]);
                    entity1.setAdgroupId(Long.valueOf(arr[5]));
                    entity1.setAdgroupName(arr[6]);
                    if (type == 1) {
                        entity1.setPcImpression(Integer.valueOf(arr[10]));
                        entity1.setPcClick(Integer.valueOf(arr[11]));
                        entity1.setPcCost(BigDecimal.valueOf(Double.valueOf(arr[12])));
                        entity1.setPcCtr(Double.valueOf(arr[13].substring(0, arr[13].length() - 1)));
                        entity1.setPcCpc(BigDecimal.valueOf(Double.valueOf(arr[14])));
                        entity1.setPcCpm(BigDecimal.valueOf(Double.valueOf(arr[15])));
                        entity1.setPcConversion(Double.valueOf(arr[16]));
                        entity1.setPcPosition(Double.valueOf(arr[17]));
                    } else if (type == 2) {
                        entity1.setMobileImpression(Integer.valueOf(arr[10]));
                        entity1.setMobileClick(Integer.valueOf(arr[11]));
                        entity1.setMobileCost(BigDecimal.valueOf(Double.valueOf(arr[12])));
                        entity1.setMobileCtr(Double.valueOf(arr[13].substring(0, arr[13].length() - 1)));
                        entity1.setMobileCpc(BigDecimal.valueOf(Double.valueOf(arr[14])));
                        entity1.setMobileCpm(BigDecimal.valueOf(Double.valueOf(arr[15])));
                        entity1.setMobileConversion(Double.valueOf(arr[16]));
                        entity1.setMobilePosition(Double.valueOf(arr[17]));
                    }
                    list.add(entity1);
                    index++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }

        public List<RegionReportDTO> getRegionReport(String filePath, int type) {
            List<RegionReportDTO> list = new ArrayList<>();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(filePath);
            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "GBK"));
                String str;
                int index = -1;
                while ((str = br.readLine()) != null) {
                    if (index == -1) {
                        index++;
                        continue;
                    }
                    String[] arr = str.split("\\t");
                    RegionReportDTO entity1 = new RegionReportDTO();
                    entity1.setAccountId(Long.valueOf(arr[1]));
                    entity1.setCampaignId(Long.valueOf(arr[3]));
                    entity1.setCampaignName(arr[4]);
                    if (type == 1) {
                        entity1.setAdgroupId(Long.valueOf(arr[5]));
                        entity1.setAdgroupName(arr[6]);
                        entity1.setRegionId(((arr[7].equals("-")) ? -1l : Long.valueOf(arr[7])));
                        entity1.setRegionName(arr[8]);
                        entity1.setPcImpression(Integer.valueOf(arr[9]));
                        entity1.setPcClick(Integer.valueOf(arr[10]));
                        entity1.setPcCost(BigDecimal.valueOf(Double.valueOf(arr[11])));
                        entity1.setPcCtr(Double.valueOf(arr[12].substring(0, arr[12].length() - 1)));
                        entity1.setPcCpc(BigDecimal.valueOf(Double.valueOf(arr[13])));
                        entity1.setPcCpm(BigDecimal.valueOf(Double.valueOf(arr[14])));
                        entity1.setPcConversion(Double.valueOf(arr[15]));
                        entity1.setPcPosition(Double.valueOf(arr[16]));
                    } else if (type == 2) {
                        if (arr[5].equals("-")) {
                            entity1.setRegionId(-1l);
                        } else {
                            entity1.setRegionId(Long.valueOf(arr[5]));
                        }
                        entity1.setRegionName(arr[6]);
                        entity1.setMobileImpression(Integer.valueOf(arr[7]));
                        entity1.setMobileClick(Integer.valueOf(arr[8]));
                        entity1.setMobileCost(BigDecimal.valueOf(Double.valueOf(arr[9])));
                        entity1.setMobileCtr(Double.valueOf(arr[10].substring(0, arr[10].length() - 1)));
                        entity1.setMobileCpc(BigDecimal.valueOf(Double.valueOf(arr[11])));
                        entity1.setMobileCpm(BigDecimal.valueOf(Double.valueOf(arr[12])));
                        entity1.setMobileConversion(Double.valueOf(arr[13]));
                    }
                    list.add(entity1);
                    index++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }
    }

    class AccountReportHandler extends RecursiveTask<List<AccountReportDTO>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<AccountReportDTO> pcList;

        AccountReportHandler(List<AccountReportDTO> pcList, int first, int last) {
            this.first = first;
            this.last = last;
            this.pcList = pcList;
        }

        @Override
        protected List<AccountReportDTO> compute() {
            List<AccountReportDTO> list = new ArrayList<>();
            if (pcList.size() == 0) {
                //
                for (AccountReportDTO type : acrmList) {
                    AccountReportDTO dto = new AccountReportDTO();
                    dto.setDate(type.getDate());
                    dto.setAccountId(type.getAccountId());
                    dto.setAccountName(type.getAccountName());
                    dto.setMobileImpression(type.getMobileImpression());
                    dto.setMobileClick(type.getMobileClick());
                    dto.setMobileCtr(type.getMobileCtr());
                    dto.setMobileCost(type.getMobileCost());
                    dto.setMobileCpc(type.getMobileCpc());
                    dto.setMobileCpm(type.getMobileCpm());
                    dto.setMobileConversion(type.getMobileConversion());
                    list.add(dto);
                }
                //
            } else {
                boolean status = (last - first) < threshold;
                if (status) {
                    for (int i = first; i < last; i++) {
                        AccountReportDTO entity = pcList.get(i);
                        boolean temp = true;
                        for (AccountReportDTO type : acrmList) {
                            if (Long.valueOf(entity.getAccountId()).compareTo(type.getAccountId()) == 0) {
                                AccountReportDTO _entity = new AccountReportDTO();
                                _entity.setDate(entity.getDate());
                                _entity.setAccountId(entity.getAccountId());
                                _entity.setAccountName(entity.getAccountName());
                                _entity.setPcImpression(entity.getPcImpression());
                                _entity.setPcClick(entity.getPcClick());
                                _entity.setPcCtr(entity.getPcCtr());
                                _entity.setPcCost(entity.getPcCost());
                                _entity.setPcCpc(entity.getPcCpc());
                                _entity.setPcCpm(entity.getPcCpm());
                                _entity.setPcConversion(entity.getPcConversion());
                                _entity.setMobileImpression(type.getMobileImpression());
                                _entity.setMobileClick(type.getMobileClick());
                                _entity.setMobileCtr(type.getMobileCtr());
                                _entity.setMobileCost(type.getMobileCost());
                                _entity.setMobileCpc(type.getMobileCpc());
                                _entity.setMobileCpm(type.getMobileCpm());
                                _entity.setMobileConversion(type.getMobileConversion());
                                list.add(_entity);
                                temp = false;
                                break;
                            }
                        }
                        if (temp) {
                            AccountReportDTO _entity = new AccountReportDTO();
                            _entity.setDate(entity.getDate());
                            _entity.setAccountId(entity.getAccountId());
                            _entity.setAccountName(entity.getAccountName());
                            _entity.setPcImpression(entity.getPcImpression());
                            _entity.setPcClick(entity.getPcClick());
                            _entity.setPcCtr(entity.getPcCtr());
                            _entity.setPcCost(entity.getPcCost());
                            _entity.setPcCpc(entity.getPcCpc());
                            _entity.setPcCpm(entity.getPcCpm());
                            _entity.setPcConversion(entity.getPcConversion());
                            list.add(_entity);
                        }
                    }
                } else {
                    int middle = (first + last) / 2;
                    AccountReportHandler task1 = new AccountReportHandler(pcList, first, middle);
                    AccountReportHandler task2 = new AccountReportHandler(pcList, middle, last);

                    invokeAll(task1, task2);
                    list.clear();
                    list.addAll(task1.join());
                    list.addAll(task2.join());
                }
            }
            return list;
        }
    }

    class CampaignReportHandler extends RecursiveTask<List<CampaignReportDTO>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<CampaignReportDTO> pcList;

        CampaignReportHandler(List<CampaignReportDTO> pcList, int first, int last) {
            this.first = first;
            this.last = last;
            this.pcList = pcList;
        }

        @Override
        protected List<CampaignReportDTO> compute() {
            List<CampaignReportDTO> list = new ArrayList<>();
            if (pcList.size() == 0) {
                for (CampaignReportDTO type : cprmList) {
                    CampaignReportDTO dto = new CampaignReportDTO();
                    dto.setAccountId(type.getAccountId());
                    dto.setCampaignId(type.getCampaignId());
                    dto.setCampaignName(type.getCampaignName());
                    dto.setMobileImpression(type.getMobileImpression());
                    dto.setMobileClick(type.getMobileClick());
                    dto.setMobileCtr(type.getMobileCtr());
                    dto.setMobileCost(type.getMobileCost());
                    dto.setMobileCpc(type.getMobileCpc());
                    dto.setMobileCpm(type.getMobileCpm());
                    dto.setMobileConversion(type.getMobileConversion());
                    list.add(dto);
                }
            } else {
                boolean status = (last - first) < threshold;
                if (status) {
                    for (int i = first; i < last; i++) {
                        CampaignReportDTO entity = pcList.get(i);
                        boolean temp = true;
                        for (CampaignReportDTO type : cprmList) {
                            if (entity.getCampaignId().compareTo(type.getCampaignId()) == 0) {
                                CampaignReportDTO _entity = new CampaignReportDTO();
                                _entity.setAccountId(entity.getAccountId());
                                _entity.setCampaignId(entity.getCampaignId());
                                _entity.setCampaignName(entity.getCampaignName());
                                _entity.setPcImpression(entity.getPcImpression());
                                _entity.setPcClick(entity.getPcClick());
                                _entity.setPcCtr(entity.getPcCtr());
                                _entity.setPcCost(entity.getPcCost());
                                _entity.setPcCpc(entity.getPcCpc());
                                _entity.setPcCpm(entity.getPcCpm());
                                _entity.setPcConversion(entity.getPcConversion());
                                _entity.setMobileImpression(type.getMobileImpression());
                                _entity.setMobileClick(type.getMobileClick());
                                _entity.setMobileCtr(type.getMobileCtr());
                                _entity.setMobileCost(type.getMobileCost());
                                _entity.setMobileCpc(type.getMobileCpc());
                                _entity.setMobileCpm(type.getMobileCpm());
                                _entity.setMobileConversion(type.getMobileConversion());
                                list.add(_entity);
                                temp = false;
                                break;
                            }
                        }
                        if (temp) {
                            CampaignReportDTO _entity = new CampaignReportDTO();
                            _entity.setAccountId(entity.getAccountId());
                            _entity.setCampaignId(entity.getCampaignId());
                            _entity.setCampaignName(entity.getCampaignName());
                            _entity.setPcImpression(entity.getPcImpression());
                            _entity.setPcClick(entity.getPcClick());
                            _entity.setPcCtr(entity.getPcCtr());
                            _entity.setPcCost(entity.getPcCost());
                            _entity.setPcCpc(entity.getPcCpc());
                            _entity.setPcCpm(entity.getPcCpm());
                            _entity.setPcConversion(entity.getPcConversion());
                            list.add(_entity);
                        }
                    }
                } else {
                    int middle = (first + last) / 2;
                    CampaignReportHandler task1 = new CampaignReportHandler(pcList, first, middle);
                    CampaignReportHandler task2 = new CampaignReportHandler(pcList, middle, last);

                    invokeAll(task1, task2);
                    list.clear();
                    list.addAll(task1.join());
                    list.addAll(task2.join());
                }
            }
            return list;
        }
    }

    class AdgroupReportHandler extends RecursiveTask<List<AdgroupReportDTO>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<AdgroupReportDTO> pcList;

        AdgroupReportHandler(List<AdgroupReportDTO> pcList, int first, int last) {
            this.first = first;
            this.last = last;
            this.pcList = pcList;
        }

        @Override
        protected List<AdgroupReportDTO> compute() {
            List<AdgroupReportDTO> list = new ArrayList<>();
            if (pcList.size() == 0) {
                for (AdgroupReportDTO type : armList) {
                    AdgroupReportDTO dto = new AdgroupReportDTO();
                    dto.setAdgroupId(type.getAdgroupId());
                    dto.setAdgroupName(type.getAdgroupName());
                    dto.setAccountId(type.getAccountId());
                    dto.setCampaignId(type.getCampaignId());
                    dto.setCampaignName(type.getCampaignName());
                    dto.setMobileImpression(type.getMobileImpression());
                    dto.setMobileClick(type.getMobileClick());
                    dto.setMobileCtr(type.getMobileCtr());
                    dto.setMobileCost(type.getMobileCost());
                    dto.setMobileCpc(type.getMobileCpc());
                    dto.setMobileCpm(type.getMobileCpm());
                    dto.setMobileConversion(type.getMobileConversion());
                    list.add(dto);
                }
            } else {
                boolean status = (last - first) < threshold;
                if (status) {
                    for (int i = first; i < last; i++) {
                        AdgroupReportDTO entity = pcList.get(i);
                        boolean temp = true;
                        for (AdgroupReportDTO type : armList) {
                            if (entity.getAdgroupId().compareTo(type.getAdgroupId()) == 0) {
                                AdgroupReportDTO _entity = new AdgroupReportDTO();
                                _entity.setAdgroupId(entity.getAdgroupId());
                                _entity.setAdgroupName(entity.getAdgroupName());
                                _entity.setAccountId(entity.getAccountId());
                                _entity.setCampaignId(entity.getCampaignId());
                                _entity.setCampaignName(entity.getCampaignName());
                                _entity.setPcImpression(entity.getPcImpression());
                                _entity.setPcClick(entity.getPcClick());
                                _entity.setPcCtr(entity.getPcCtr());
                                _entity.setPcCost(entity.getPcCost());
                                _entity.setPcCpc(entity.getPcCpc());
                                _entity.setPcCpm(entity.getPcCpm());
                                _entity.setPcConversion(entity.getPcConversion());
                                _entity.setMobileImpression(type.getMobileImpression());
                                _entity.setMobileClick(type.getMobileClick());
                                _entity.setMobileCtr(type.getMobileCtr());
                                _entity.setMobileCost(type.getMobileCost());
                                _entity.setMobileCpc(type.getMobileCpc());
                                _entity.setMobileCpm(type.getMobileCpm());
                                _entity.setMobileConversion(type.getMobileConversion());
                                list.add(_entity);
                                temp = false;
                                break;
                            }
                        }
                        if (temp) {
                            AdgroupReportDTO _entity = new AdgroupReportDTO();
                            _entity.setAdgroupId(entity.getAdgroupId());
                            _entity.setAdgroupName(entity.getAdgroupName());
                            _entity.setAccountId(entity.getAccountId());
                            _entity.setCampaignId(entity.getCampaignId());
                            _entity.setCampaignName(entity.getCampaignName());
                            _entity.setPcImpression(entity.getPcImpression());
                            _entity.setPcClick(entity.getPcClick());
                            _entity.setPcCtr(entity.getPcCtr());
                            _entity.setPcCost(entity.getPcCost());
                            _entity.setPcCpc(entity.getPcCpc());
                            _entity.setPcCpm(entity.getPcCpm());
                            _entity.setPcConversion(entity.getPcConversion());
                            list.add(_entity);
                        }
                    }
                } else {
                    int middle = (first + last) / 2;
                    AdgroupReportHandler task1 = new AdgroupReportHandler(pcList, first, middle);
                    AdgroupReportHandler task2 = new AdgroupReportHandler(pcList, middle, last);

                    invokeAll(task1, task2);
                    list.clear();
                    list.addAll(task1.join());
                    list.addAll(task2.join());
                }
            }
            return list;
        }

    }


    class CreativeReportHandler extends RecursiveTask<List<CreativeReportDTO>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<CreativeReportDTO> pcList;

        CreativeReportHandler(List<CreativeReportDTO> pcList, int first, int last) {
            this.first = first;
            this.last = last;
            this.pcList = pcList;
        }

        @Override
        protected List<CreativeReportDTO> compute() {
            List<CreativeReportDTO> list = new ArrayList<>();
            if (pcList.size() == 0) {
                for (CreativeReportDTO type : crmList) {
                    CreativeReportDTO dto = new CreativeReportDTO();
                    dto.setCreativeId(type.getCreativeId());
                    dto.setCreativeTitle(type.getCreativeTitle());
                    dto.setDescription1(type.getDescription1());
                    dto.setDescription2(type.getDescription2());
                    dto.setShowUrl(type.getShowUrl());
                    dto.setAdgroupId(type.getAdgroupId());
                    dto.setAdgroupName(type.getAdgroupName());
                    dto.setAccountId(type.getAccountId());
                    dto.setCampaignId(type.getCampaignId());
                    dto.setCampaignName(type.getCampaignName());
                    dto.setMobileImpression(type.getMobileImpression());
                    dto.setMobileClick(type.getMobileClick());
                    dto.setMobileCtr(type.getMobileCtr());
                    dto.setMobileCost(type.getMobileCost());
                    dto.setMobileCpc(type.getMobileCpc());
                    dto.setMobileCpm(type.getMobileCpm());
                    dto.setMobileConversion(type.getMobileConversion());
                    list.add(dto);
                }
            } else {
                boolean status = (last - first) < threshold;
                if (status) {
                    for (int i = first; i < last; i++) {
                        CreativeReportDTO entity = pcList.get(i);
                        boolean temp = true;
                        for (CreativeReportDTO type : crmList) {
                            if (entity.getCreativeId().compareTo(type.getCreativeId()) == 0) {
                                CreativeReportDTO _entity = new CreativeReportDTO();
                                _entity.setCreativeId(entity.getCreativeId());
                                _entity.setCreativeTitle(entity.getCreativeTitle());
                                _entity.setDescription1(entity.getDescription1());
                                _entity.setDescription2(entity.getDescription2());
                                _entity.setShowUrl(entity.getShowUrl());
                                _entity.setAdgroupId(entity.getAdgroupId());
                                _entity.setAdgroupName(entity.getAdgroupName());
                                _entity.setAccountId(entity.getAccountId());
                                _entity.setCampaignId(entity.getCampaignId());
                                _entity.setCampaignName(entity.getCampaignName());
                                _entity.setPcImpression(entity.getPcImpression());
                                _entity.setPcClick(entity.getPcClick());
                                _entity.setPcCtr(entity.getPcCtr());
                                _entity.setPcCost(entity.getPcCost());
                                _entity.setPcCpc(entity.getPcCpc());
                                _entity.setPcCpm(entity.getPcCpm());
                                _entity.setPcConversion(entity.getPcConversion());
                                _entity.setMobileImpression(type.getMobileImpression());
                                _entity.setMobileClick(type.getMobileClick());
                                _entity.setMobileCtr(type.getMobileCtr());
                                _entity.setMobileCost(type.getMobileCost());
                                _entity.setMobileCpc(type.getMobileCpc());
                                _entity.setMobileCpm(type.getMobileCpm());
                                _entity.setMobileConversion(type.getMobileConversion());
                                list.add(_entity);
                                temp = false;
                                break;
                            }
                        }
                        if (temp) {
                            CreativeReportDTO _entity = new CreativeReportDTO();
                            _entity.setCreativeId(entity.getCreativeId());
                            _entity.setCreativeTitle(entity.getCreativeTitle());
                            _entity.setDescription1(entity.getDescription1());
                            _entity.setDescription2(entity.getDescription2());
                            _entity.setShowUrl(entity.getShowUrl());
                            _entity.setAdgroupId(entity.getAdgroupId());
                            _entity.setAdgroupName(entity.getAdgroupName());
                            _entity.setAccountId(entity.getAccountId());
                            _entity.setCampaignId(entity.getCampaignId());
                            _entity.setCampaignName(entity.getCampaignName());
                            _entity.setPcImpression(entity.getPcImpression());
                            _entity.setPcClick(entity.getPcClick());
                            _entity.setPcCtr(entity.getPcCtr());
                            _entity.setPcCost(entity.getPcCost());
                            _entity.setPcCpc(entity.getPcCpc());
                            _entity.setPcCpm(entity.getPcCpm());
                            _entity.setPcConversion(entity.getPcConversion());
                            list.add(_entity);
                        }
                    }
                } else {
                    int middle = (first + last) / 2;
                    CreativeReportHandler task1 = new CreativeReportHandler(pcList, first, middle);
                    CreativeReportHandler task2 = new CreativeReportHandler(pcList, middle, last);

                    invokeAll(task1, task2);
                    list.clear();
                    list.addAll(task1.join());
                    list.addAll(task2.join());
                }
            }
            return list;
        }
    }

    class KeywordReportHandler extends RecursiveTask<List<KeywordReportDTO>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<KeywordReportDTO> pcList;

        KeywordReportHandler(List<KeywordReportDTO> pcList, int first, int last) {
            this.first = first;
            this.last = last;
            this.pcList = pcList;
        }

        @Override
        protected List<KeywordReportDTO> compute() {
            List<KeywordReportDTO> list = new ArrayList<>();
            if (pcList.size() == 0) {
                for (KeywordReportDTO type : krmList) {
                    KeywordReportDTO dto = new KeywordReportDTO();
                    dto.setKeywordId(dto.getKeywordId());
                    dto.setKeywordName(dto.getKeywordName());
                    dto.setAdgroupId(dto.getAdgroupId());
                    dto.setAdgroupName(dto.getAdgroupName());
                    dto.setAccountId(dto.getAccountId());
                    dto.setCampaignId(dto.getCampaignId());
                    dto.setCampaignName(dto.getCampaignName());
                    dto.setMobileImpression(type.getMobileImpression());
                    dto.setMobileClick(type.getMobileClick());
                    dto.setMobileCtr(type.getMobileCtr());
                    dto.setMobileCost(type.getMobileCost());
                    dto.setMobileCpc(type.getMobileCpc());
                    dto.setMobileCpm(type.getMobileCpm());
                    dto.setMobileConversion(type.getMobileConversion());
                    list.add(dto);
                }
            } else {
                boolean status = (last - first) < threshold;
                if (status) {
                    for (int i = first; i < last; i++) {
                        KeywordReportDTO entity = pcList.get(i);
                        boolean temp = true;
                        for (KeywordReportDTO type : krmList) {
                            if (entity.getKeywordId().compareTo(type.getKeywordId()) == 0) {
                                KeywordReportDTO _entity = new KeywordReportDTO();
                                _entity.setKeywordId(entity.getKeywordId());
                                _entity.setKeywordName(entity.getKeywordName());
                                _entity.setAdgroupId(entity.getAdgroupId());
                                _entity.setAdgroupName(entity.getAdgroupName());
                                _entity.setAccountId(entity.getAccountId());
                                _entity.setCampaignId(entity.getCampaignId());
                                _entity.setCampaignName(entity.getCampaignName());
                                _entity.setPcImpression(entity.getPcImpression());
                                _entity.setPcClick(entity.getPcClick());
                                _entity.setPcCtr(entity.getPcCtr());
                                _entity.setPcCost(entity.getPcCost());
                                _entity.setPcCpc(entity.getPcCpc());
                                _entity.setPcCpm(entity.getPcCpm());
                                _entity.setPcConversion(entity.getPcConversion());
                                _entity.setPcPosition(entity.getPcPosition());
                                _entity.setMobileImpression(type.getMobileImpression());
                                _entity.setMobileClick(type.getMobileClick());
                                _entity.setMobileCtr(type.getMobileCtr());
                                _entity.setMobileCost(type.getMobileCost());
                                _entity.setMobileCpc(type.getMobileCpc());
                                _entity.setMobileCpm(type.getMobileCpm());
                                _entity.setMobileConversion(type.getMobileConversion());
                                _entity.setMobilePosition(type.getMobilePosition());
                                list.add(_entity);
                                temp = false;
                                break;
                            }
                        }
                        if (temp) {
                            KeywordReportDTO _entity = new KeywordReportDTO();
                            _entity.setKeywordId(entity.getKeywordId());
                            _entity.setKeywordName(entity.getKeywordName());
                            _entity.setAdgroupId(entity.getAdgroupId());
                            _entity.setAdgroupName(entity.getAdgroupName());
                            _entity.setAccountId(entity.getAccountId());
                            _entity.setCampaignId(entity.getCampaignId());
                            _entity.setCampaignName(entity.getCampaignName());
                            _entity.setPcImpression(entity.getPcImpression());
                            _entity.setPcClick(entity.getPcClick());
                            _entity.setPcCtr(entity.getPcCtr());
                            _entity.setPcCost(entity.getPcCost());
                            _entity.setPcCpc(entity.getPcCpc());
                            _entity.setPcCpm(entity.getPcCpm());
                            _entity.setPcConversion(entity.getPcConversion());
                            _entity.setPcPosition(entity.getPcPosition());
                            list.add(_entity);
                        }
                    }
                } else {
                    int middle = (first + last) / 2;
                    KeywordReportHandler task1 = new KeywordReportHandler(pcList, first, middle);
                    KeywordReportHandler task2 = new KeywordReportHandler(pcList, middle, last);

                    invokeAll(task1, task2);
                    list.clear();
                    list.addAll(task1.join());
                    list.addAll(task2.join());
                }
            }
            return list;
        }
    }

    class RegionReportHandler extends RecursiveTask<List<RegionReportDTO>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<RegionReportDTO> pcList;

        RegionReportHandler(List<RegionReportDTO> pcList, int first, int last) {
            this.first = first;
            this.last = last;
            this.pcList = pcList;
        }

        @Override
        protected List<RegionReportDTO> compute() {
            List<RegionReportDTO> list = new ArrayList<>();
            if (pcList.size() == 0) {
                for (RegionReportDTO type : rrmList) {
                    RegionReportDTO dto = new RegionReportDTO();
                    dto.setRegionId(type.getRegionId());
                    dto.setRegionName(type.getRegionName());
                    dto.setAdgroupId(type.getAdgroupId());
                    dto.setAdgroupName(type.getAdgroupName());
                    dto.setAccountId(type.getAccountId());
                    dto.setCampaignId(type.getCampaignId());
                    dto.setCampaignName(type.getCampaignName());
                    dto.setMobileImpression(type.getMobileImpression());
                    dto.setMobileClick(type.getMobileClick());
                    dto.setMobileCtr(type.getMobileCtr());
                    dto.setMobileCost(type.getMobileCost());
                    dto.setMobileCpc(type.getMobileCpc());
                    dto.setMobileCpm(type.getMobileCpm());
                    dto.setMobileConversion(type.getMobileConversion());
                    list.add(dto);
                }
            } else {
                boolean status = (last - first) < threshold;
                if (status) {
                    for (int i = first; i < last; i++) {
                        RegionReportDTO entity = pcList.get(i);
                        boolean temp = true;
                        for (RegionReportDTO type : rrmList) {
                            if (entity.getRegionId().compareTo(type.getRegionId()) == 0) {
                                RegionReportDTO _entity = new RegionReportDTO();
                                _entity.setRegionId(entity.getRegionId());
                                _entity.setRegionName(entity.getRegionName());
                                _entity.setAdgroupId(entity.getAdgroupId());
                                _entity.setAdgroupName(entity.getAdgroupName());
                                _entity.setAccountId(entity.getAccountId());
                                _entity.setCampaignId(entity.getCampaignId());
                                _entity.setCampaignName(entity.getCampaignName());
                                _entity.setPcImpression(entity.getPcImpression());
                                _entity.setPcClick(entity.getPcClick());
                                _entity.setPcCtr(entity.getPcCtr());
                                _entity.setPcCost(entity.getPcCost());
                                _entity.setPcCpc(entity.getPcCpc());
                                _entity.setPcCpm(entity.getPcCpm());
                                _entity.setPcConversion(entity.getPcConversion());
                                _entity.setPcPosition(entity.getPcPosition());
                                _entity.setMobileImpression(type.getMobileImpression());
                                _entity.setMobileClick(type.getMobileClick());
                                _entity.setMobileCtr(type.getMobileCtr());
                                _entity.setMobileCost(type.getMobileCost());
                                _entity.setMobileCpc(type.getMobileCpc());
                                _entity.setMobileCpm(type.getMobileCpm());
                                _entity.setMobileConversion(type.getMobileConversion());
                                _entity.setMobilePosition(type.getMobilePosition());
                                list.add(_entity);
                                temp = false;
                                break;
                            }
                        }
                        if (temp) {
                            RegionReportDTO _entity = new RegionReportDTO();
                            _entity.setRegionId(entity.getRegionId());
                            _entity.setRegionName(entity.getRegionName());
                            _entity.setAdgroupId(entity.getAdgroupId());
                            _entity.setAdgroupName(entity.getAdgroupName());
                            _entity.setAccountId(entity.getAccountId());
                            _entity.setCampaignId(entity.getCampaignId());
                            _entity.setCampaignName(entity.getCampaignName());
                            _entity.setPcImpression(entity.getPcImpression());
                            _entity.setPcClick(entity.getPcClick());
                            _entity.setPcCtr(entity.getPcCtr());
                            _entity.setPcCost(entity.getPcCost());
                            _entity.setPcCpc(entity.getPcCpc());
                            _entity.setPcCpm(entity.getPcCpm());
                            _entity.setPcConversion(entity.getPcConversion());
                            _entity.setPcPosition(entity.getPcPosition());
                            list.add(_entity);
                        }
                    }
                } else {
                    int middle = (first + last) / 2;
                    RegionReportHandler task1 = new RegionReportHandler(pcList, first, middle);
                    RegionReportHandler task2 = new RegionReportHandler(pcList, middle, last);

                    invokeAll(task1, task2);
                    list.clear();
                    list.addAll(task1.join());
                    list.addAll(task2.join());
                }
            }
            return list;
        }
    }
}