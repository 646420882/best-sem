package com.perfect.mongodb.dao.impl;

import com.perfect.api.baidu.AsynchronousReport;
import com.perfect.dao.AsynchronousReportDAO;
import com.perfect.entity.*;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by baizz on 2014-08-07.
 */
@Repository("asynchronousReportDAO")
public class AsynchronousReportDAOImpl implements AsynchronousReportDAO {

    /*private MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(
            DBNameUtil.getReportDBName(AppContext.getUser().toString()));*/

    private MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate("user_shangpin_report");

    private HttpFileHandler httpFileHandler = new HttpFileHandler();

    private List<AccountReportEntity> acrmList;

    private List<CampaignReportEntity> cprmList;

    private List<AdgroupReportEntity> armList;

    private List<CreativeReportEntity> crmList;

    private List<KeywordReportEntity> krmList;

    private List<RegionReportEntity> rrmList;

    public void getAccountReportData(String dateStr) {
        AsynchronousReport report = new AsynchronousReport();
        String pcFilePath = report.getAccountReportDataPC(null, dateStr, dateStr);
        String mobileFilePath = report.getAccountReportDataMobile(null, dateStr, dateStr);

        List<AccountReportEntity> pcList = httpFileHandler.getAccountReport(pcFilePath, 1);
        acrmList = httpFileHandler.getAccountReport(mobileFilePath, 2);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        AccountReportHandler task = new AccountReportHandler(pcList, 0, pcList.size());
        Future<List<AccountReportEntity>> voResult = forkJoinPool.submit(task);
        List<AccountReportEntity> list;
        try {
            list = voResult.get();
            mongoTemplate.insert(list, "account_report");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }
    }

    public void getCampaignReportData(String dateStr) {
        AsynchronousReport report = new AsynchronousReport();
        String pcFilePath = report.getCampaignReportDataPC(null, null, dateStr, dateStr);
        String mobileFilePath = report.getCampaignReportDataMobile(null, null, dateStr, dateStr);

        List<CampaignReportEntity> pcList = httpFileHandler.getCampaignReport(pcFilePath, 1);
        cprmList = httpFileHandler.getCampaignReport(mobileFilePath, 2);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CampaignReportHandler task = new CampaignReportHandler(pcList, 0, pcList.size());
        Future<List<CampaignReportEntity>> voResult = forkJoinPool.submit(task);
        List<CampaignReportEntity> list;
        try {
            list = voResult.get();
            mongoTemplate.insert(list, dateStr + "-campaign");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }
    }

    public void getAdgroupReportData(String dateStr) {
        AsynchronousReport report = new AsynchronousReport();
        String pcFilePath = report.getUnitReportDataPC(null, null, dateStr, dateStr);
        String mobileFilePath = report.getUnitReportDataMobile(null, null, dateStr, dateStr);

        List<AdgroupReportEntity> pcList = httpFileHandler.getAdgroupReport(pcFilePath, 1);
        armList = httpFileHandler.getAdgroupReport(mobileFilePath, 2);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        AdgroupReportHandler task = new AdgroupReportHandler(pcList, 0, pcList.size());
        Future<List<AdgroupReportEntity>> voResult = forkJoinPool.submit(task);
        List<AdgroupReportEntity> list;
        try {
            list = voResult.get();
            mongoTemplate.insert(list, dateStr + "-adgroup");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }
    }

    public void getCreativeReportData(String dateStr) {
        AsynchronousReport report = new AsynchronousReport();
        String pcFilePath = report.getCreativeReportDataPC(null, null, dateStr, dateStr);
        String mobileFilePath = report.getCreativeReportDataMobile(null, null, dateStr, dateStr);

        List<CreativeReportEntity> pcList = httpFileHandler.getCreativeReport(pcFilePath, 1);
        crmList = httpFileHandler.getCreativeReport(mobileFilePath, 2);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CreativeReportHandler task = new CreativeReportHandler(pcList, 0, pcList.size());
        Future<List<CreativeReportEntity>> voResult = forkJoinPool.submit(task);
        List<CreativeReportEntity> list;
        try {
            list = voResult.get();
            mongoTemplate.insert(list, dateStr + "-creative");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }
    }

    public void getKeywordReportData(String dateStr) {
        AsynchronousReport report = new AsynchronousReport();
        String pcFilePath = report.getKeyWordidReportDataPC(null, null, dateStr, dateStr);
        String mobileFilePath = report.getKeyWordidReportDataMobile(null, null, dateStr, dateStr);

        List<KeywordReportEntity> pcList = httpFileHandler.getKeywordReport(pcFilePath, 1);
        krmList = httpFileHandler.getKeywordReport(mobileFilePath, 2);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        KeywordReportHandler task = new KeywordReportHandler(pcList, 0, pcList.size());
        Future<List<KeywordReportEntity>> voResult = forkJoinPool.submit(task);
        List<KeywordReportEntity> list;
        try {
            list = voResult.get();
            mongoTemplate.insert(list, dateStr + "-keyword");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }
    }

    public void getRegionReportData(String dateStr) {
        AsynchronousReport report = new AsynchronousReport();
        String pcFilePath = report.getRegionalReportDataPC(null, null, dateStr, dateStr);
        String mobileFilePath = report.getRegionalReportDataMobile(null, null, dateStr, dateStr);

        List<RegionReportEntity> pcList = httpFileHandler.getRegionReport(pcFilePath, 1);
        rrmList = httpFileHandler.getRegionReport(mobileFilePath, 2);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        RegionReportHandler task = new RegionReportHandler(pcList, 0, pcList.size());
        Future<List<RegionReportEntity>> voResult = forkJoinPool.submit(task);
        List<RegionReportEntity> list;
        try {
            list = voResult.get();
            mongoTemplate.insert(list, dateStr + "-region");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }
    }

    class HttpFileHandler {

        public List<AccountReportEntity> getAccountReport(String filePath, int type) {
            List<AccountReportEntity> list = new ArrayList<>();
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
                    AccountReportEntity entity1 = new AccountReportEntity();
                    entity1.setAccountId(Long.valueOf(arr[1]));
                    entity1.setAccountName(arr[2]);
                    if (type == 1) {
                        entity1.setPcImpression(Integer.valueOf(arr[3]));
                        entity1.setPcClick(Integer.valueOf(arr[4]));
                        entity1.setPcCost(Double.valueOf(arr[5]));
                        entity1.setPcCtr(Double.valueOf(arr[6].substring(0, arr[6].length() - 1)));
                        entity1.setPcCpc(Double.valueOf(arr[7]));
                        entity1.setPcCpm(Double.valueOf(arr[8]));
                        entity1.setPcConversion(Double.valueOf(arr[9]));
                    } else if (type == 2) {
                        entity1.setMobileImpression(Integer.valueOf(arr[3]));
                        entity1.setMobileClick(Integer.valueOf(arr[4]));
                        entity1.setMobileCost(Double.valueOf(arr[5]));
                        entity1.setMobileCtr(Double.valueOf(arr[6].substring(0, arr[6].length() - 1)));
                        entity1.setMobileCpc(Double.valueOf(arr[7]));
                        entity1.setMobileCpm(Double.valueOf(arr[8]));
                        entity1.setMobileConversion(Double.valueOf(arr[9]));
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


        public List<CampaignReportEntity> getCampaignReport(String filePath, int type) {
            List<CampaignReportEntity> list = new ArrayList<>();
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
                    CampaignReportEntity entity1 = new CampaignReportEntity();
                    entity1.setAccountId(Long.valueOf(arr[1]));
                    entity1.setAccountName(arr[2]);
                    entity1.setCampaignId(Long.valueOf(arr[3]));
                    entity1.setCampaignName(arr[4]);
                    if (type == 1) {
                        entity1.setPcImpression(Integer.valueOf(arr[5]));
                        entity1.setPcClick(Integer.valueOf(arr[6]));
                        entity1.setPcCost(Double.valueOf(arr[7]));
                        entity1.setPcCtr(Double.valueOf(arr[8].substring(0, arr[8].length() - 1)));
                        entity1.setPcCpc(Double.valueOf(arr[9]));
                        entity1.setPcCpm(Double.valueOf(arr[10]));
                        entity1.setPcConversion(Double.valueOf(arr[11]));
                    } else if (type == 2) {
                        entity1.setMobileImpression(Integer.valueOf(arr[5]));
                        entity1.setMobileClick(Integer.valueOf(arr[6]));
                        entity1.setMobileCost(Double.valueOf(arr[7]));
                        entity1.setMobileCtr(Double.valueOf(arr[8].substring(0, arr[8].length() - 1)));
                        entity1.setMobileCpc(Double.valueOf(arr[9]));
                        entity1.setMobileCpm(Double.valueOf(arr[10]));
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

        public List<AdgroupReportEntity> getAdgroupReport(String filePath, int type) {
            List<AdgroupReportEntity> list = new ArrayList<>();
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
                    AdgroupReportEntity entity1 = new AdgroupReportEntity();
                    entity1.setAccountId(Long.valueOf(arr[1]));
                    entity1.setAccountName(arr[2]);
                    entity1.setCampaignId(Long.valueOf(arr[3]));
                    entity1.setCampaignName(arr[4]);
                    entity1.setAdgroupId(Long.valueOf(arr[5]));
                    entity1.setAdgroupName(arr[6]);
                    if (type == 1) {
                        entity1.setPcImpression(Integer.valueOf(arr[7]));
                        entity1.setPcClick(Integer.valueOf(arr[8]));
                        entity1.setPcCost(Double.valueOf(arr[9]));
                        entity1.setPcCtr(Double.valueOf(arr[10].substring(0, arr[10].length() - 1)));
                        entity1.setPcCpc(Double.valueOf(arr[11]));
                        entity1.setPcCpm(Double.valueOf(arr[12]));
                        entity1.setPcConversion(Double.valueOf(arr[13]));
                    } else if (type == 2) {
                        entity1.setMobileImpression(Integer.valueOf(arr[7]));
                        entity1.setMobileClick(Integer.valueOf(arr[8]));
                        entity1.setMobileCost(Double.valueOf(arr[9]));
                        entity1.setMobileCtr(Double.valueOf(arr[10].substring(0, arr[10].length() - 1)));
                        entity1.setMobileCpc(Double.valueOf(arr[11]));
                        entity1.setMobileCpm(Double.valueOf(arr[12]));
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

        public List<CreativeReportEntity> getCreativeReport(String filePath, int type) {
            List<CreativeReportEntity> list = new ArrayList<>();
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
                    CreativeReportEntity entity1 = new CreativeReportEntity();
                    entity1.setCreativeId(Long.valueOf(arr[7]));
                    entity1.setCreativeTitle(arr[8]);
                    entity1.setDescription1(arr[9]);
                    entity1.setDescription2(arr[10]);
                    entity1.setShowUrl(arr[11]);
                    entity1.setAccountId(Long.valueOf(arr[1]));
                    entity1.setAccountName(arr[2]);
                    entity1.setCampaignId(Long.valueOf(arr[3]));
                    entity1.setCampaignName(arr[4]);
                    entity1.setAdgroupId(Long.valueOf(arr[5]));
                    entity1.setAdgroupName(arr[6]);
                    if (type == 1) {
                        entity1.setPcImpression(Integer.valueOf(arr[12]));
                        entity1.setPcClick(Integer.valueOf(arr[13]));
                        entity1.setPcCost(Double.valueOf(arr[14]));
                        entity1.setPcCtr(Double.valueOf(arr[15].substring(0, arr[15].length() - 1)));
                        entity1.setPcCpc(Double.valueOf(arr[16]));
                        entity1.setPcCpm(Double.valueOf(arr[17]));
                        entity1.setPcConversion(Double.valueOf(arr[18]));
                    } else if (type == 2) {
                        entity1.setMobileImpression(Integer.valueOf(arr[12]));
                        entity1.setMobileClick(Integer.valueOf(arr[13]));
                        entity1.setMobileCost(Double.valueOf(arr[14]));
                        entity1.setMobileCtr(Double.valueOf(arr[15].substring(0, arr[15].length() - 1)));
                        entity1.setMobileCpc(Double.valueOf(arr[16]));
                        entity1.setMobileCpm(Double.valueOf(arr[17]));
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

        public List<KeywordReportEntity> getKeywordReport(String filePath, int type) {
            List<KeywordReportEntity> list = new ArrayList<>();
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
                    KeywordReportEntity entity1 = new KeywordReportEntity();
                    entity1.setKeywordId(Long.valueOf(arr[7]));
                    entity1.setKeywordName(arr[9]);
                    entity1.setAccountId(Long.valueOf(arr[1]));
                    entity1.setAccountName(arr[2]);
                    entity1.setCampaignId(Long.valueOf(arr[3]));
                    entity1.setCampaignName(arr[4]);
                    entity1.setAdgroupId(Long.valueOf(arr[5]));
                    entity1.setAdgroupName(arr[6]);
                    if (type == 1) {
                        entity1.setPcImpression(Integer.valueOf(arr[10]));
                        entity1.setPcClick(Integer.valueOf(arr[11]));
                        entity1.setPcCost(Double.valueOf(arr[12]));
                        entity1.setPcCtr(Double.valueOf(arr[13].substring(0, arr[13].length() - 1)));
                        entity1.setPcCpc(Double.valueOf(arr[14]));
                        entity1.setPcCpm(Double.valueOf(arr[15]));
                        entity1.setPcConversion(Double.valueOf(arr[16]));
                        entity1.setPcPosition(Double.valueOf(arr[17]));
                    } else if (type == 2) {
                        entity1.setMobileImpression(Integer.valueOf(arr[10]));
                        entity1.setMobileClick(Integer.valueOf(arr[11]));
                        entity1.setMobileCost(Double.valueOf(arr[12]));
                        entity1.setMobileCtr(Double.valueOf(arr[13].substring(0, arr[13].length() - 1)));
                        entity1.setMobileCpc(Double.valueOf(arr[14]));
                        entity1.setMobileCpm(Double.valueOf(arr[15]));
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

        public List<RegionReportEntity> getRegionReport(String filePath, int type) {
            List<RegionReportEntity> list = new ArrayList<>();
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
                    RegionReportEntity entity1 = new RegionReportEntity();
                    entity1.setRegionId(Long.valueOf(arr[7]));
                    entity1.setRegionName(arr[8]);
                    entity1.setAccountId(Long.valueOf(arr[1]));
                    entity1.setAccountName(arr[2]);
                    entity1.setCampaignId(Long.valueOf(arr[3]));
                    entity1.setCampaignName(arr[4]);
                    entity1.setAdgroupId(Long.valueOf(arr[5]));
                    entity1.setAdgroupName(arr[6]);
                    if (type == 1) {
                        entity1.setPcImpression(Integer.valueOf(arr[9]));
                        entity1.setPcClick(Integer.valueOf(arr[10]));
                        entity1.setPcCost(Double.valueOf(arr[11]));
                        entity1.setPcCtr(Double.valueOf(arr[12].substring(0, arr[12].length() - 1)));
                        entity1.setPcCpc(Double.valueOf(arr[13]));
                        entity1.setPcCpm(Double.valueOf(arr[14]));
                        entity1.setPcConversion(Double.valueOf(arr[15]));
                        entity1.setPcPosition(Double.valueOf(arr[16]));
                    } else if (type == 2) {
                        entity1.setMobileImpression(Integer.valueOf(arr[9]));
                        entity1.setMobileClick(Integer.valueOf(arr[10]));
                        entity1.setMobileCost(Double.valueOf(arr[11]));
                        entity1.setMobileCtr(Double.valueOf(arr[12].substring(0, arr[12].length() - 1)));
                        entity1.setMobileCpc(Double.valueOf(arr[13]));
                        entity1.setMobileCpm(Double.valueOf(arr[14]));
                        entity1.setMobileConversion(Double.valueOf(arr[15]));
                        entity1.setMobilePosition(Double.valueOf(arr[16]));
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

    class AccountReportHandler extends RecursiveTask<List<AccountReportEntity>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<AccountReportEntity> pcList;

        AccountReportHandler(List<AccountReportEntity> pcList, int first, int last) {
            this.first = first;
            this.last = last;
            this.pcList = pcList;
        }

        @Override
        protected List<AccountReportEntity> compute() {
            List<AccountReportEntity> list = new ArrayList<>();
            boolean status = (last - first) < threshold;
            if (status) {
                for (int i = first; i < last; i++) {
                    AccountReportEntity entity = pcList.get(i);
                    boolean temp = true;
                    for (AccountReportEntity type : acrmList) {
                        if (entity.getAccountId().compareTo(type.getAccountId()) == 0) {
                            AccountReportEntity _entity = new AccountReportEntity();
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
                        AccountReportEntity _entity = new AccountReportEntity();
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
            return list;
        }
    }

    class CampaignReportHandler extends RecursiveTask<List<CampaignReportEntity>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<CampaignReportEntity> pcList;

        CampaignReportHandler(List<CampaignReportEntity> pcList, int first, int last) {
            this.first = first;
            this.last = last;
            this.pcList = pcList;
        }

        @Override
        protected List<CampaignReportEntity> compute() {
            List<CampaignReportEntity> list = new ArrayList<>();
            boolean status = (last - first) < threshold;
            if (status) {
                for (int i = first; i < last; i++) {
                    CampaignReportEntity entity = pcList.get(i);
                    boolean temp = true;
                    for (CampaignReportEntity type : cprmList) {
                        if (entity.getCampaignId().compareTo(type.getCampaignId()) == 0) {
                            CampaignReportEntity _entity = new CampaignReportEntity();
                            _entity.setAccountId(entity.getAccountId());
                            _entity.setAccountName(entity.getAccountName());
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
                        CampaignReportEntity _entity = new CampaignReportEntity();
                        _entity.setAccountId(entity.getAccountId());
                        _entity.setAccountName(entity.getAccountName());
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
            return list;
        }
    }

    class AdgroupReportHandler extends RecursiveTask<List<AdgroupReportEntity>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<AdgroupReportEntity> pcList;

        AdgroupReportHandler(List<AdgroupReportEntity> pcList, int first, int last) {
            this.first = first;
            this.last = last;
            this.pcList = pcList;
        }

        @Override
        protected List<AdgroupReportEntity> compute() {
            List<AdgroupReportEntity> list = new ArrayList<>();
            boolean status = (last - first) < threshold;
            if (status) {
                for (int i = first; i < last; i++) {
                    AdgroupReportEntity entity = pcList.get(i);
                    boolean temp = true;
                    for (AdgroupReportEntity type : armList) {
                        if (entity.getAdgroupId().compareTo(type.getAdgroupId()) == 0) {
                            AdgroupReportEntity _entity = new AdgroupReportEntity();
                            _entity.setAdgroupId(entity.getAdgroupId());
                            _entity.setAdgroupName(entity.getAdgroupName());
                            _entity.setAccountId(entity.getAccountId());
                            _entity.setAccountName(entity.getAccountName());
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
                        AdgroupReportEntity _entity = new AdgroupReportEntity();
                        _entity.setAdgroupId(entity.getAdgroupId());
                        _entity.setAdgroupName(entity.getAdgroupName());
                        _entity.setAccountId(entity.getAccountId());
                        _entity.setAccountName(entity.getAccountName());
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
            return list;
        }

    }


    class CreativeReportHandler extends RecursiveTask<List<CreativeReportEntity>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<CreativeReportEntity> pcList;

        CreativeReportHandler(List<CreativeReportEntity> pcList, int first, int last) {
            this.first = first;
            this.last = last;
            this.pcList = pcList;
        }

        @Override
        protected List<CreativeReportEntity> compute() {
            List<CreativeReportEntity> list = new ArrayList<>();
            boolean status = (last - first) < threshold;
            if (status) {
                for (int i = first; i < last; i++) {
                    CreativeReportEntity entity = pcList.get(i);
                    boolean temp = true;
                    for (CreativeReportEntity type : crmList) {
                        if (entity.getCreativeId().compareTo(type.getCreativeId()) == 0) {
                            CreativeReportEntity _entity = new CreativeReportEntity();
                            _entity.setCreativeId(entity.getCreativeId());
                            _entity.setCreativeTitle(entity.getCreativeTitle());
                            _entity.setDescription1(entity.getDescription1());
                            _entity.setDescription2(entity.getDescription2());
                            _entity.setShowUrl(entity.getShowUrl());
                            _entity.setAdgroupId(entity.getAdgroupId());
                            _entity.setAdgroupName(entity.getAdgroupName());
                            _entity.setAccountId(entity.getAccountId());
                            _entity.setAccountName(entity.getAccountName());
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
                        CreativeReportEntity _entity = new CreativeReportEntity();
                        _entity.setCreativeId(entity.getCreativeId());
                        _entity.setCreativeTitle(entity.getCreativeTitle());
                        _entity.setDescription1(entity.getDescription1());
                        _entity.setDescription2(entity.getDescription2());
                        _entity.setShowUrl(entity.getShowUrl());
                        _entity.setAdgroupId(entity.getAdgroupId());
                        _entity.setAdgroupName(entity.getAdgroupName());
                        _entity.setAccountId(entity.getAccountId());
                        _entity.setAccountName(entity.getAccountName());
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
            return list;
        }
    }

    class KeywordReportHandler extends RecursiveTask<List<KeywordReportEntity>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<KeywordReportEntity> pcList;

        KeywordReportHandler(List<KeywordReportEntity> pcList, int first, int last) {
            this.first = first;
            this.last = last;
            this.pcList = pcList;
        }

        @Override
        protected List<KeywordReportEntity> compute() {
            List<KeywordReportEntity> list = new ArrayList<>();
            boolean status = (last - first) < threshold;
            if (status) {
                for (int i = first; i < last; i++) {
                    KeywordReportEntity entity = pcList.get(i);
                    boolean temp = true;
                    for (KeywordReportEntity type : krmList) {
                        if (entity.getKeywordId().compareTo(type.getKeywordId()) == 0) {
                            KeywordReportEntity _entity = new KeywordReportEntity();
                            _entity.setKeywordId(entity.getKeywordId());
                            _entity.setKeywordName(entity.getKeywordName());
                            _entity.setAdgroupId(entity.getAdgroupId());
                            _entity.setAdgroupName(entity.getAdgroupName());
                            _entity.setAccountId(entity.getAccountId());
                            _entity.setAccountName(entity.getAccountName());
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
                        KeywordReportEntity _entity = new KeywordReportEntity();
                        _entity.setKeywordId(entity.getKeywordId());
                        _entity.setKeywordName(entity.getKeywordName());
                        _entity.setAdgroupId(entity.getAdgroupId());
                        _entity.setAdgroupName(entity.getAdgroupName());
                        _entity.setAccountId(entity.getAccountId());
                        _entity.setAccountName(entity.getAccountName());
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
            return list;
        }
    }

    class RegionReportHandler extends RecursiveTask<List<RegionReportEntity>> {

        private static final int threshold = 100;

        private int first;
        private int last;
        private List<RegionReportEntity> pcList;

        RegionReportHandler(List<RegionReportEntity> pcList, int first, int last) {
            this.first = first;
            this.last = last;
            this.pcList = pcList;
        }

        @Override
        protected List<RegionReportEntity> compute() {
            List<RegionReportEntity> list = new ArrayList<>();
            boolean status = (last - first) < threshold;
            if (status) {
                for (int i = first; i < last; i++) {
                    RegionReportEntity entity = pcList.get(i);
                    boolean temp = true;
                    for (RegionReportEntity type : rrmList) {
                        if (entity.getRegionId().compareTo(type.getRegionId()) == 0) {
                            RegionReportEntity _entity = new RegionReportEntity();
                            _entity.setRegionId(entity.getRegionId());
                            _entity.setRegionName(entity.getRegionName());
                            _entity.setAdgroupId(entity.getAdgroupId());
                            _entity.setAdgroupName(entity.getAdgroupName());
                            _entity.setAccountId(entity.getAccountId());
                            _entity.setAccountName(entity.getAccountName());
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
                        RegionReportEntity _entity = new RegionReportEntity();
                        _entity.setRegionId(entity.getRegionId());
                        _entity.setRegionName(entity.getRegionName());
                        _entity.setAdgroupId(entity.getAdgroupId());
                        _entity.setAdgroupName(entity.getAdgroupName());
                        _entity.setAccountId(entity.getAccountId());
                        _entity.setAccountName(entity.getAccountName());
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
            return list;
        }
    }
}