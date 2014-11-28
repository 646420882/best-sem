package com.perfect.service.impl;

import com.google.common.primitives.Bytes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.perfect.core.AppContext;
import com.perfect.dao.report.BasisReportDAO;
import com.perfect.dto.StructureReportDTO;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.redis.JRedisUtils;
import com.perfect.service.AccountManageService;
import com.perfect.service.BasisReportService;
import com.perfect.DateUtils;
import com.perfect.utils.report.*;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import static com.perfect.commons.constants.ReportConstants.*;

/**
 * Created by SubDong on 2014/8/6.
 */
@Repository("basisReportService")
public class BasisReportServiceImpl implements BasisReportService {
    @Resource
    private BasisReportDAO basisReportDAO;
    @Resource
    private AccountManageService accountManageService;

    private static int REDISKEYTIME = 1 * 60 * 60;
    private static final String DEFAULT_DELIMITER = ",";
    private static final String DEFAULT_END = "\r\n";
    private static final byte commonCSVHead[] = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    /**
     * 生成报告
     *
     * @param terminal     推广设备 0、全部  1、PC端 2、移动端
     * @param date         时间
     * @param categoryTime 分类时间  0、默认 1、分日 2、分周 3、分月
     * @param reportType   报告类型
     * @param limit        显示个数
     * @param start        开始数
     * @return
     */
    public Map<String, List<StructureReportDTO>> getReportDate(String[] date, int terminal, int categoryTime, int reportType, int start, int limit, String sort, Long dataId, String dateName) {
        String userName = accountManageService.getBaiduAccountInfoById(AppContext.getAccountId()).getBaiduUserName();
        switch (categoryTime) {
            //默认时间生成报告
            case 0:
                Jedis jc = JRedisUtils.get();

                Long jedisKey = jc.ttl(date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId);
                if (jedisKey == -1) {
                    List<StructureReportDTO> dateMap0 = new ArrayList<>();
                    StructureReportDTO dateObject0 = new StructureReportDTO();
                    //初始化容器
                    Map<String, StructureReportDTO> map = new HashMap<>();
                    Map<String, StructureReportDTO> mapAll = new HashMap<>();
                    List<StructureReportDTO> returnList = new ArrayList<>();
                    Map<String, List<StructureReportDTO>> listMap = new HashMap<>();
                    List<StructureReportDTO> objectsList = new ArrayList<>();
                    //获取需要的数据
                    for (int i = 0; i < date.length; i++) {
                        List<StructureReportDTO> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType), dataId, dateName);
                        if (object.size() != 0) {
                            objectsList.addAll(object);
                        }
                    }
                    ForkJoinPool joinPool = new ForkJoinPool();

                    //计算合计数据
                    List<StructureReportDTO> returnListAll = dataALL(objectsList);

                    dateObject0.setDate(date[0] + " 至 " + date[date.length - 1]);
                    dateMap0.add(dateObject0);
                    //创建一个并行计算框架

                    Map<String, StructureReportDTO> map1 = null;
                    try {
                        //开始对数据处理
                        Future<Map<String, StructureReportDTO>> joinTask = joinPool.submit(new BasisReportDefaultUtil(objectsList, 0, objectsList.size(), reportType, userName));
                        //得到处理结果
                        map = joinTask.get();
                        //计算百分比
                        map1 = percentage(map);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    //关闭并行计算框架
                    joinPool.shutdown();
                    List<StructureReportDTO> list = new ArrayList<>(map1.values());

                    //选择全部内容
                    if (terminal == 0) {
                        //创建一个并行计算框架
                        ForkJoinPool joinPoolTow = new ForkJoinPool();
                        //对第一次处理后的数据进行第二次处理
                        Future<List<StructureReportDTO>> joinTaskTow = joinPoolTow.submit(new BasistReportPCPlusMobUtil(list, 0, list.size(), userName));
                        try {
                            //得到第二次处理后的数据
                            returnList = joinTaskTow.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        //关闭并行计算框架
                        joinPoolTow.shutdown();
                        if ("-11".equals(sort) || "11".equals(sort)) {
                            sort = "-1";
                        }
                        if (reportType == 4) {
                            //计算饼状图 展现数据
                            List<StructureReportDTO> pieIpmr = getPieData(returnList, terminal, REPORT_IMPR, REPORT_FUYI);
                            //计算饼状图 点击数据
                            List<StructureReportDTO> pieClick = getPieData(returnList, terminal, REPORT_CLICK, REPORT_FUER);
                            //计算饼状图 消费数据
                            List<StructureReportDTO> pieCost = getPieData(returnList, terminal, REPORT_COST, REPORT_FUSAN);
                            //计算饼状图 转化数据
                            List<StructureReportDTO> pieConv = getPieData(returnList, terminal, REPORT_CONV, REPORT_FULIU);

                            listMap.put(REPORT_IMPR, pieIpmr);
                            listMap.put(REPORT_CLICK, pieClick);
                            listMap.put(REPORT_COST, pieCost);
                            listMap.put(REPORT_CONV, pieConv);
                        }
                        String dds = new Gson().toJson(returnList);
                        String StatistAll = new Gson().toJson(returnListAll);
                        jc.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), dds);
                        jc.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                        jc.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), StatistAll);
                        jc.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);
                        ReportPageDetails pageDetails = new ReportPageDetails();
                        List<StructureReportDTO> pageReport = pageDetails.getReportDetailsPageObj(returnList, terminal, sort, start, limit, date[0] + " 至 " + date[date.length - 1]);
                        List<StructureReportDTO> entityList1 = getCountStructure(listMap);
                        listMap.put(REPORT_STATISTICS, returnListAll);
                        listMap.put(REPORT_COUNTDATA, entityList1);
                        listMap.put(REPORT_ROWS, pageReport);
                        return listMap;
                    } else {
                        if (reportType == 4) {
                            //计算饼状图 展现数据
                            List<StructureReportDTO> pieIpmr = getPieData(list, terminal, REPORT_IMPR, REPORT_FUYI);
                            //计算饼状图 点击数据
                            List<StructureReportDTO> pieClick = getPieData(list, terminal, REPORT_CLICK, REPORT_FUER);
                            //计算饼状图 消费数据
                            List<StructureReportDTO> pieCost = getPieData(list, terminal, REPORT_COST, REPORT_FUSAN);
                            //计算饼状图 转化数据
                            List<StructureReportDTO> pieConv = getPieData(list, terminal, REPORT_CONV, REPORT_FULIU);

                            listMap.put(REPORT_IMPR, pieIpmr);
                            listMap.put(REPORT_CLICK, pieClick);
                            listMap.put(REPORT_COST, pieCost);
                            listMap.put(REPORT_CONV, pieConv);
                        }
                        String lists = new Gson().toJson(list);
                        String StatistAll = new Gson().toJson(returnListAll);
                        jc.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), lists);
                        jc.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), StatistAll);
                        jc.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                        jc.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);
                        ReportPageDetails pageDetails = new ReportPageDetails();
                        List<StructureReportDTO> pageReport = pageDetails.getReportDetailsPageObj(list, terminal, sort, start, limit, date[0] + " 至 " + date[date.length - 1]);
                        List<StructureReportDTO> entityList1 = getCountStructure(listMap);
                        listMap.put(REPORT_STATISTICS, returnListAll);
                        listMap.put(REPORT_COUNTDATA, entityList1);
                        listMap.put(REPORT_ROWS, pageReport);
                        return listMap;
                    }
                } else {
                    String data = jc.get((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId));
                    String dataAll = jc.get((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"));

                    Gson gson = new Gson();
                    List<StructureReportDTO> list = gson.fromJson(data, new TypeToken<List<StructureReportDTO>>() {
                    }.getType());
                    List<StructureReportDTO> listAll = gson.fromJson(dataAll, new TypeToken<List<StructureReportDTO>>() {
                    }.getType());


                    Map<String, List<StructureReportDTO>> listMap = new HashMap<>();
                    if (reportType == 4) {
                        //计算饼状图 展现数据
                        List<StructureReportDTO> pieIpmr = getPieData(list, terminal, REPORT_IMPR, REPORT_FUYI);
                        //计算饼状图 点击数据
                        List<StructureReportDTO> pieClick = getPieData(list, terminal, REPORT_CLICK, REPORT_FUER);
                        //计算饼状图 消费数据
                        List<StructureReportDTO> pieCost = getPieData(list, terminal, REPORT_COST, REPORT_FUSAN);
                        //计算饼状图 转化数据
                        List<StructureReportDTO> pieConv = getPieData(list, terminal, REPORT_CONV, REPORT_FULIU);

                        listMap.put(REPORT_IMPR, pieIpmr);
                        listMap.put(REPORT_CLICK, pieClick);
                        listMap.put(REPORT_COST, pieCost);
                        listMap.put(REPORT_CONV, pieConv);
                    }
                    ReportPageDetails pageDetails = new ReportPageDetails();
                    if ("-11".equals(sort) || "11".equals(sort)) {
                        sort = REPORT_FUYI;
                    }
                    List<StructureReportDTO> pageReport = pageDetails.getReportDetailsPageObj(list, terminal, sort, start, limit, date[0] + " 至 " + date[date.length - 1]);
                    List<StructureReportDTO> entityList1 = getCountStructure(listMap);
                    listMap.put(REPORT_STATISTICS, listAll);
                    listMap.put(REPORT_COUNTDATA, entityList1);
                    listMap.put(REPORT_ROWS, pageReport);
                    return listMap;
                }
                //分日生成报告
            case 1:
                Jedis jc1 = JRedisUtils.get();

                Long jedisKey1 = jc1.ttl(date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId);
                if (jedisKey1 == -1) {
                    List<StructureReportDTO> dateMap = new ArrayList<>();

                    //初始化容器
                    Map<String, List<StructureReportDTO>> mapDay = new HashMap<>();
                    //获得需要的数据
                    for (int i = 0; i < date.length; i++) {
                        StructureReportDTO dateObject = new StructureReportDTO();
                        List<StructureReportDTO> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType), dataId, dateName);
                        if (object.size() == 0) {
                            StructureReportDTO entity = new StructureReportDTO();
                            entity.setKeywordId(0l);
                            entity.setKeywordName("-");
                            entity.setRegionId(0l);
                            entity.setRegionName("-");
                            entity.setAdgroupId(0l);
                            entity.setAdgroupName("-");
                            entity.setCampaignId(0l);
                            entity.setCampaignName("-");

                            entity.setMobileClick(0);
                            entity.setMobileConversion(0d);
                            entity.setMobileCost(BigDecimal.ZERO);
                            entity.setMobileCpc(BigDecimal.ZERO);
                            entity.setMobileCpm(BigDecimal.ZERO);
                            entity.setMobileImpression(0);
                            entity.setPcClick(0);
                            entity.setPcConversion(0d);
                            entity.setPcCost(BigDecimal.ZERO);
                            entity.setPcCpc(BigDecimal.ZERO);
                            entity.setPcCpm(BigDecimal.ZERO);
                            entity.setPcImpression(0);
                            object.add(entity);
                            mapDay.put(date[i], object);
                        } else {
                            mapDay.put(date[i], object);
                        }

                        dateObject.setDate(date[i]);
                        dateMap.add(dateObject);
                    }


                    List<StructureReportDTO> entityListAll = new ArrayList<>();
                    for (Iterator<Map.Entry<String, List<StructureReportDTO>>> entry1 = mapDay.entrySet().iterator(); entry1.hasNext(); ) {
                        entityListAll.addAll(entry1.next().getValue());
                    }

                    //计算合计数据
                    List<StructureReportDTO> returnListAll = dataALL(entityListAll);


                    Map<String, List<StructureReportDTO>> mapDay1 = null;
                    if (terminal == 0) {
                        Map<String, List<StructureReportDTO>> listMap1 = terminalAll(mapDay, reportType);

                        //数据放入redis中
                        String lists = new Gson().toJson(listMap1);
                        String listsALL = new Gson().toJson(returnListAll);
                        jc1.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), lists);
                        jc1.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                        jc1.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), listsALL);
                        jc1.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);


                        List<StructureReportDTO> entityList1 = getCountStructure(listMap1);
                        //曲线图数据计算
                        List<StructureReportDTO> lineChart = getLineChart(listMap1, terminal);
                        ReportPageDetails pageDetails = new ReportPageDetails();
                        List<StructureReportDTO> pageReport = pageDetails.getReportDetailsPage(listMap1, terminal, sort, start, limit, categoryTime);
                        listMap1.put(REPORT_STATISTICS, returnListAll);
                        listMap1.put(REPORT_COUNTDATA, entityList1);
                        listMap1.put(REPORT_CHART, lineChart);
                        listMap1.put(REPORT_ROWS, pageReport);
                        return listMap1;
                    } else {
                        //对相应的数据进行计算百分比
                        mapDay1 = percentageList(mapDay, userName, reportType);
                    }
                    //数据放入redis中
                    String lists = new Gson().toJson(mapDay1);
                    String listsALL = new Gson().toJson(returnListAll);
                    jc1.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), lists);
                    jc1.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                    jc1.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), listsALL);
                    jc1.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);

                    List<StructureReportDTO> entityList1 = getCountStructure(mapDay1);

                    //曲线图数据计算
                    List<StructureReportDTO> lineChart = getLineChart(mapDay1, terminal);
                    ReportPageDetails pageDetails = new ReportPageDetails();
                    List<StructureReportDTO> pageReport = pageDetails.getReportDetailsPage(mapDay1, terminal, sort, start, limit, categoryTime);
                    mapDay.put(REPORT_STATISTICS, returnListAll);
                    mapDay.put(REPORT_CHART, lineChart);
                    mapDay.put(REPORT_COUNTDATA, entityList1);
                    mapDay.put(REPORT_ROWS, pageReport);
                    return mapDay;
                } else {
                    String data = jc1.get((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId));
                    String dataAll = jc1.get((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"));
                    Gson gson = new Gson();

                    Map<String, List<StructureReportDTO>> list = gson.fromJson(data, new TypeToken<Map<String, List<StructureReportDTO>>>() {
                    }.getType());
                    List<StructureReportDTO> listAll = gson.fromJson(dataAll, new TypeToken<List<StructureReportDTO>>() {
                    }.getType());

                    Map<String, List<StructureReportDTO>> listMap1 = new HashMap<>();

                    List<StructureReportDTO> entityList1 = getCountStructure(list);
                    //曲线图数据计算
                    List<StructureReportDTO> lineChart = getLineChart(list, terminal);
                    ReportPageDetails pageDetails = new ReportPageDetails();
                    List<StructureReportDTO> pageReport = pageDetails.getReportDetailsPage(list, terminal, sort, start, limit, categoryTime);
                    listMap1.put(REPORT_STATISTICS, listAll);
                    listMap1.put(REPORT_COUNTDATA, entityList1);
                    listMap1.put(REPORT_CHART, lineChart);
                    listMap1.put(REPORT_ROWS, pageReport);
                    return listMap1;
                }

                //分周生成报告
            case 2:
                Jedis jc2 = JRedisUtils.get();

                Long jedisKey2 = jc2.ttl(date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId);
                if (jedisKey2 == -1) {
                    List<StructureReportDTO> dateMap2 = new ArrayList<>();

                    Map<String, StructureReportDTO> reportEntities = new HashMap<>();
                    Map<String, List<StructureReportDTO>> stringListMap = new HashMap<>();
                    Map<String, List<StructureReportDTO>> endListMap = new HashMap<>();
                    //如果用户选择的时间范围大于7天
                    if (date.length > 7) {
                        int i = 0;
                        int endNumber = 0;
                        int endStep = endStep = date.length < 7 ? 1 : date.length % 7 == 0 ? date.length : (date.length / 7) + 1;
                        for (int x = 0; x < endStep; x++) {
                            StructureReportDTO dateObject2 = new StructureReportDTO();
                            List<StructureReportDTO> objectsList1 = new ArrayList<>();
                            String[] strings = new String[2];
                            for (i = endNumber; i < ((i == 0) ? endNumber + 6 : endNumber + 7); i++) {
                                if (i >= date.length) {
                                    continue;
                                }
                                List<StructureReportDTO> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType), dataId, dateName);
                                if (object.size() != 0) {
                                    objectsList1.addAll(object);
                                }
                                if (i == endNumber) {
                                    strings[0] = date[i];
                                    if (i == date.length - 1) {
                                        strings[1] = date[i];
                                    }
                                } else {
                                    strings[1] = date[i];
                                }
                            }
                            if (objectsList1.size() == 0) {
                                StructureReportDTO entity = new StructureReportDTO();
                                entity.setKeywordId(0l);
                                entity.setKeywordName("-");
                                entity.setRegionId(0l);
                                entity.setRegionName("-");
                                entity.setAdgroupId(0l);
                                entity.setAdgroupName("-");
                                entity.setCampaignId(0l);
                                entity.setCampaignName("-");

                                entity.setMobileClick(0);
                                entity.setMobileConversion(0d);
                                entity.setMobileCost(BigDecimal.ZERO);
                                entity.setMobileCpc(BigDecimal.ZERO);
                                entity.setMobileCpm(BigDecimal.ZERO);
                                entity.setMobileImpression(0);
                                entity.setPcClick(0);
                                entity.setPcConversion(0d);
                                entity.setPcCost(BigDecimal.ZERO);
                                entity.setPcCpc(BigDecimal.ZERO);
                                entity.setPcCpm(BigDecimal.ZERO);
                                entity.setPcImpression(0);
                                objectsList1.add(entity);
                            }
                            stringListMap.put(strings[0] + " 至 " + strings[1], objectsList1);
                            dateObject2.setDate(strings[0] + " 至 " + strings[1]);
                            dateMap2.add(dateObject2);
                            endNumber = i;
                        }

                        List<StructureReportDTO> entityListAll = new ArrayList<>();
                        for (Iterator<Map.Entry<String, List<StructureReportDTO>>> entry1 = stringListMap.entrySet().iterator(); entry1.hasNext(); ) {
                            entityListAll.addAll(entry1.next().getValue());
                        }

                        //计算合计数据
                        List<StructureReportDTO> returnListAll = dataALL(entityListAll);

                        for (Map.Entry<String, List<StructureReportDTO>> entry1 : stringListMap.entrySet()) {
                            //创建一个并行计算框架
                            ForkJoinPool joinPoolTow = new ForkJoinPool();
                            //获取map中的value
                            List<StructureReportDTO> list1 = entry1.getValue();
                            //开始对数据进行处理
                            Future<Map<String, StructureReportDTO>> joinTask = joinPoolTow.submit(new BasisReportDefaultUtil(list1, 0, list1.size(), reportType, userName));
                            //接收处理好的数据
                            try {
                                reportEntities = joinTask.get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

                            //对相应的数据进行计算百分比
                            Map<String, StructureReportDTO> reportEntities1 = percentage(reportEntities);

                            //关闭并行计算框架
                            joinPoolTow.shutdown();
                            List<StructureReportDTO> arrayList = new ArrayList<>(reportEntities1.values());
                            endListMap.put(entry1.getKey(), arrayList);
                        }
                        if (terminal == 0) {
                            Map<String, List<StructureReportDTO>> listMap1 = terminalAll(endListMap, reportType);

                            //数据放入redis中
                            String lists = new Gson().toJson(listMap1);
                            String listsALL = new Gson().toJson(returnListAll);
                            jc2.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), lists);
                            jc2.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                            jc2.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), listsALL);
                            jc2.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);

                            List<StructureReportDTO> entityList2 = getCountStructure(listMap1);
                            //曲线图数据计算
                            List<StructureReportDTO> lineChart1 = getLineChart(listMap1, terminal);
                            ReportPageDetails pageDetails1 = new ReportPageDetails();
                            List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(listMap1, terminal, sort, start, limit, categoryTime);
                            listMap1.put(REPORT_STATISTICS, returnListAll);
                            listMap1.put(REPORT_CHART, lineChart1);
                            listMap1.put(REPORT_COUNTDATA, entityList2);
                            listMap1.put(REPORT_ROWS, pageReport1);
                            return listMap1;
                        }

                        //数据放入redis中
                        String lists = new Gson().toJson(endListMap);
                        String listsALL = new Gson().toJson(returnListAll);
                        jc2.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), lists);
                        jc2.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                        jc2.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), listsALL);
                        jc2.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);

                        List<StructureReportDTO> entityList2 = getCountStructure(endListMap);
                        //曲线图数据计算
                        List<StructureReportDTO> lineChart1 = getLineChart(endListMap, terminal);
                        ReportPageDetails pageDetails1 = new ReportPageDetails();
                        List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(endListMap, terminal, sort, start, limit, categoryTime);
                        endListMap.put(REPORT_STATISTICS, returnListAll);
                        endListMap.put(REPORT_CHART, lineChart1);
                        endListMap.put(REPORT_COUNTDATA, entityList2);
                        endListMap.put(REPORT_ROWS, pageReport1);
                        return endListMap;
                    } else {
                        List<StructureReportDTO> dateMap2else = new ArrayList<>();
                        StructureReportDTO dateObject2else = new StructureReportDTO();
                        List<StructureReportDTO> objectsList = new ArrayList<>();
                        //如果用户选择的时间范围小于或者等于7天
                        for (int i = 0; i < date.length; i++) {
                            List<StructureReportDTO> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType), dataId, dateName);
                            if (object.size() != 0) {
                                objectsList.addAll(object);
                            }
                        }

                        //计算合计数据
                        List<StructureReportDTO> returnListAll = dataALL(objectsList);

                        dateObject2else.setDate(date[0] + " 至 " + date[date.length - 1]);
                        dateMap2else.add(dateObject2else);
                        //创建一个并行计算框架
                        ForkJoinPool joinPoolTow = new ForkJoinPool();
                        //开始对数据进行处理
                        Future<Map<String, StructureReportDTO>> joinTask = joinPoolTow.submit(new BasisReportDefaultUtil(objectsList, 0, objectsList.size(), reportType, userName));
                        try {
                            //得到处理好的数据
                            reportEntities = joinTask.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        //对相应的数据进行计算百分比
                        Map<String, StructureReportDTO> reportEntities1 = percentage(reportEntities);

                        //关闭并行计算框架
                        joinPoolTow.shutdown();
                        List<StructureReportDTO> entityList = new ArrayList<>(reportEntities1.values());
                        endListMap.put(date[0] + " 至 " + date[date.length - 1], entityList);
                        if (terminal == 0) {
                            Map<String, List<StructureReportDTO>> listMap1 = terminalAll(endListMap, reportType);
                            //数据放入redis中
                            String lists = new Gson().toJson(listMap1);
                            String listsALL = new Gson().toJson(returnListAll);
                            jc2.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), lists);
                            jc2.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                            jc2.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), listsALL);
                            jc2.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);

                            List<StructureReportDTO> entityList2 = getCountStructure(endListMap);
                            //曲线图数据计算
                            List<StructureReportDTO> lineChart1 = getLineChart(listMap1, terminal);
                            ReportPageDetails pageDetails1 = new ReportPageDetails();
                            List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(listMap1, terminal, sort, start, limit, categoryTime);
                            listMap1.put(REPORT_STATISTICS, returnListAll);
                            listMap1.put(REPORT_CHART, lineChart1);
                            listMap1.put(REPORT_COUNTDATA, entityList2);
                            listMap1.put(REPORT_ROWS, pageReport1);
                            return listMap1;
                        }

                        //数据放入redis中
                        String lists = new Gson().toJson(endListMap);
                        String listsALL = new Gson().toJson(returnListAll);
                        jc2.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), lists);
                        jc2.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                        jc2.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), listsALL);
                        jc2.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);

                        List<StructureReportDTO> entityList2 = getCountStructure(endListMap);
                        //曲线图数据计算
                        List<StructureReportDTO> lineChart1 = getLineChart(endListMap, terminal);
                        ReportPageDetails pageDetails1 = new ReportPageDetails();
                        List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(endListMap, terminal, sort, start, limit, categoryTime);
                        endListMap.put(REPORT_STATISTICS, returnListAll);
                        endListMap.put(REPORT_CHART, lineChart1);
                        endListMap.put(REPORT_COUNTDATA, entityList2);
                        endListMap.put(REPORT_ROWS, pageReport1);
                        return endListMap;
                    }
                } else {
                    String data = jc2.get((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId));
                    String dataAll = jc2.get((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"));
                    Gson gson = new Gson();
                    Map<String, List<StructureReportDTO>> list = gson.fromJson(data, new TypeToken<Map<String, List<StructureReportDTO>>>() {
                    }.getType());
                    List<StructureReportDTO> listAll = gson.fromJson(dataAll, new TypeToken<List<StructureReportDTO>>() {
                    }.getType());

                    Map<String, List<StructureReportDTO>> endListMap = new HashMap<>();

                    List<StructureReportDTO> entityList2 = getCountStructure(list);
                    //曲线图数据计算
                    List<StructureReportDTO> lineChart1 = getLineChart(list, terminal);
                    ReportPageDetails pageDetails1 = new ReportPageDetails();
                    List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(list, terminal, sort, start, limit, categoryTime);
                    endListMap.put(REPORT_STATISTICS, listAll);
                    endListMap.put(REPORT_CHART, lineChart1);
                    endListMap.put(REPORT_COUNTDATA, entityList2);
                    endListMap.put(REPORT_ROWS, pageReport1);
                    return endListMap;
                }

                //分月生成报告
            case 3:
                Jedis jc3 = JRedisUtils.get();

                Long jedisKey3 = jc3.ttl(date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId);
                if (jedisKey3 == -1) {
                    List<StructureReportDTO> dateMap3 = new ArrayList<>();

                    Map<String, StructureReportDTO> reportEntities1 = new HashMap<>();
                    Map<String, List<StructureReportDTO>> stringListMap1 = new HashMap<>();
                    Map<String, List<StructureReportDTO>> endListMap1 = new HashMap<>();
                    List<StructureReportDTO> objectsList = new ArrayList<>();
                    //如果用户选择的时间范围大于30天
                    if (date.length > 30) {
                        Calendar calendar = Calendar.getInstance();
                        try {
                            int index = 0;

                            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date[0]));
                            int im = Integer.parseInt(date[0].substring(date[0].length() - 2, date[0].length()));

                            if (im == 1) {
                                index = 0;
                            } else {
                                index = im - 1;
                            }
                            int numbert = 0;
                            for (int j = 0; j < date.length; j++) {
                                index++;
                                int s = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                                if (index == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                                    StructureReportDTO dateObject3 = new StructureReportDTO();
                                    if (objectsList.size() == 0) {
                                        StructureReportDTO entity = new StructureReportDTO();
                                        entity.setKeywordId(0l);
                                        entity.setKeywordName("-");
                                        entity.setRegionId(0l);
                                        entity.setRegionName("-");
                                        entity.setAdgroupId(0l);
                                        entity.setAdgroupName("-");
                                        entity.setCampaignId(0l);
                                        entity.setCampaignName("-");

                                        entity.setMobileClick(0);
                                        entity.setMobileConversion(0d);
                                        entity.setMobileCost(BigDecimal.ZERO);
                                        entity.setMobileCpc(BigDecimal.ZERO);
                                        entity.setMobileCpm(BigDecimal.ZERO);
                                        entity.setMobileImpression(0);
                                        entity.setPcClick(0);
                                        entity.setPcConversion(0d);
                                        entity.setPcCost(BigDecimal.ZERO);
                                        entity.setPcCpc(BigDecimal.ZERO);
                                        entity.setPcCpm(BigDecimal.ZERO);
                                        entity.setPcImpression(0);
                                        objectsList.add(entity);
                                    }
                                    if (im != 1) {
                                        stringListMap1.put(date[numbert] + " 至 " + date[(numbert + index) - (im - 1) - 1], objectsList);
                                        dateObject3.setDate(date[numbert] + " 至 " + date[(numbert + index) - (im - 1) - 1]);
                                        dateMap3.add(dateObject3);
                                        objectsList = new ArrayList<>();
                                        numbert = (numbert + index) - (im - 1);
                                        im = 1;
                                    } else {
                                        stringListMap1.put(date[numbert] + " 至 " + date[numbert + index - 1], objectsList);
                                        dateObject3.setDate(date[numbert] + " 至 " + date[numbert + index - 1]);
                                        dateMap3.add(dateObject3);
                                        objectsList = new ArrayList<>();
                                        numbert = numbert + index;
                                    }
                                    index = 0;
                                }

                                List<StructureReportDTO> object = basisReportDAO.getUnitReportDate(date[j] + getTableType(reportType), dataId, dateName);
                                if (object.size() != 0) {
                                    objectsList.addAll(object);
                                }
                                calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date[j]));

                            }
                            if (index > 0) {
                                StructureReportDTO dateObject3 = new StructureReportDTO();
                                if (objectsList.size() == 0) {
                                    StructureReportDTO entity = new StructureReportDTO();
                                    entity.setKeywordId(0l);
                                    entity.setKeywordName("-");
                                    entity.setRegionId(0l);
                                    entity.setRegionName("-");
                                    entity.setAdgroupId(0l);
                                    entity.setAdgroupName("-");
                                    entity.setCampaignId(0l);
                                    entity.setCampaignName("-");

                                    entity.setMobileClick(0);
                                    entity.setMobileConversion(0d);
                                    entity.setMobileCost(BigDecimal.ZERO);
                                    entity.setMobileCpc(BigDecimal.ZERO);
                                    entity.setMobileCpm(BigDecimal.ZERO);
                                    entity.setMobileImpression(0);
                                    entity.setPcClick(0);
                                    entity.setPcConversion(0d);
                                    entity.setPcCost(BigDecimal.ZERO);
                                    entity.setPcCpc(BigDecimal.ZERO);
                                    entity.setPcCpm(BigDecimal.ZERO);
                                    entity.setPcImpression(0);
                                    objectsList.add(entity);
                                }
                                stringListMap1.put(date[numbert] + " 至 " + date[numbert + index - 1], objectsList);
                                dateObject3.setDate(date[numbert] + " 至 " + date[numbert + index - 1]);
                                dateMap3.add(dateObject3);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        List<StructureReportDTO> entityListAll = new ArrayList<>();
                        for (Iterator<Map.Entry<String, List<StructureReportDTO>>> entry1 = stringListMap1.entrySet().iterator(); entry1.hasNext(); ) {
                            entityListAll.addAll(entry1.next().getValue());
                        }

                        //计算合计数据
                        List<StructureReportDTO> returnListAll = dataALL(entityListAll);


                        for (Map.Entry<String, List<StructureReportDTO>> entry1 : stringListMap1.entrySet()) {
                            //创建一个并行计算框架
                            ForkJoinPool joinPoolTow = new ForkJoinPool();
                            //获取map中的value
                            List<StructureReportDTO> list1 = entry1.getValue();
                            //开始对数据进行处理
                            Future<Map<String, StructureReportDTO>> joinTask = joinPoolTow.submit(new BasisReportDefaultUtil(list1, 0, list1.size(), reportType, userName));
                            //接收处理好的数据
                            try {
                                reportEntities1 = joinTask.get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                            //对相应的数据进行计算百分比
                            Map<String, StructureReportDTO> reportEntities2 = percentage(reportEntities1);

                            //关闭并行计算框架
                            joinPoolTow.shutdown();
                            List<StructureReportDTO> entityList = new ArrayList<>(reportEntities2.values());
                            endListMap1.put(entry1.getKey(), entityList);
                        }
                        if (terminal == 0) {
                            Map<String, List<StructureReportDTO>> listMap1 = terminalAll(endListMap1, reportType);

                            //数据放入redis中
                            String lists = new Gson().toJson(listMap1);
                            String listsAll = new Gson().toJson(returnListAll);
                            jc3.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), lists);
                            jc3.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                            jc3.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), listsAll);
                            jc3.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);

                            List<StructureReportDTO> entityList3 = getCountStructure(listMap1);
                            //曲线图数据计算
                            List<StructureReportDTO> lineChart1 = getLineChart(listMap1, terminal);
                            ReportPageDetails pageDetails1 = new ReportPageDetails();
                            List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(listMap1, terminal, sort, start, limit, categoryTime);
                            listMap1.put(REPORT_STATISTICS, returnListAll);
                            listMap1.put(REPORT_CHART, lineChart1);
                            listMap1.put(REPORT_COUNTDATA, entityList3);
                            listMap1.put(REPORT_ROWS, pageReport1);
                            return listMap1;
                        }
                        //数据放入redis中
                        String lists = new Gson().toJson(endListMap1);
                        String listsAll = new Gson().toJson(returnListAll);
                        jc3.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), lists);
                        jc3.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                        jc3.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), listsAll);
                        jc3.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);

                        List<StructureReportDTO> entityList3 = getCountStructure(endListMap1);
                        //曲线图数据计算
                        List<StructureReportDTO> lineChart1 = getLineChart(endListMap1, terminal);
                        ReportPageDetails pageDetails1 = new ReportPageDetails();
                        List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(endListMap1, terminal, sort, start, limit, categoryTime);
                        endListMap1.put(REPORT_STATISTICS, returnListAll);
                        endListMap1.put(REPORT_CHART, lineChart1);
                        endListMap1.put(REPORT_COUNTDATA, entityList3);
                        endListMap1.put(REPORT_ROWS, pageReport1);
                        return endListMap1;
                    } else {
                        List<StructureReportDTO> dateMap3else = new ArrayList<>();
                        StructureReportDTO dateObject3else = new StructureReportDTO();
                        //如果用户选择的时间范围小于或者等于30天
                        for (int i = 0; i < date.length; i++) {
                            List<StructureReportDTO> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType), dataId, dateName);
                            if (object.size() != 0) {
                                objectsList.addAll(object);
                            }
                        }
                        //计算合计数据
                        List<StructureReportDTO> returnListAll = dataALL(objectsList);

                        dateObject3else.setDate(date[0] + " 至 " + date[date.length - 1]);
                        dateMap3else.add(dateObject3else);
                        //创建一个并行计算框架
                        ForkJoinPool joinPoolTow = new ForkJoinPool();
                        //开始对数据进行处理
                        Future<Map<String, StructureReportDTO>> joinTask = joinPoolTow.submit(new BasisReportDefaultUtil(objectsList, 0, objectsList.size(), reportType, userName));
                        try {
                            //得到处理好的数据
                            reportEntities1 = joinTask.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        DecimalFormat df = new DecimalFormat("#.00");
                        //对相应的数据进行计算百分比
                        Map<String, StructureReportDTO> reportEntities2 = percentage(reportEntities1);

                        //关闭并行计算框架
                        joinPoolTow.shutdown();
                        List<StructureReportDTO> entityList = new ArrayList<>(reportEntities2.values());
                        endListMap1.put(date[0] + " 至 " + date[date.length - 1], entityList);
                        if (terminal == 0) {
                            Map<String, List<StructureReportDTO>> listMap1 = terminalAll(endListMap1, reportType);
                            //数据放入redis中
                            String lists = new Gson().toJson(listMap1);
                            String listsAll = new Gson().toJson(returnListAll);
                            jc3.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), lists);
                            jc3.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                            jc3.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), listsAll);
                            jc3.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);

                            List<StructureReportDTO> entityList3 = getCountStructure(listMap1);
                            //曲线图数据计算
                            List<StructureReportDTO> lineChart1 = getLineChart(listMap1, terminal);
                            ReportPageDetails pageDetails1 = new ReportPageDetails();
                            List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(listMap1, terminal, sort, start, limit, categoryTime);
                            listMap1.put(REPORT_STATISTICS, returnListAll);
                            listMap1.put(REPORT_CHART, lineChart1);
                            listMap1.put(REPORT_COUNTDATA, entityList3);
                            listMap1.put(REPORT_ROWS, pageReport1);
                            return listMap1;
                        }
                        //数据放入redis中
                        String lists = new Gson().toJson(endListMap1);
                        String listsAll = new Gson().toJson(returnListAll);
                        jc3.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), lists);
                        jc3.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                        jc3.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), listsAll);
                        jc3.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);

                        List<StructureReportDTO> entityList3 = getCountStructure(endListMap1);
                        //曲线图数据计算
                        List<StructureReportDTO> lineChart1 = getLineChart(endListMap1, terminal);
                        ReportPageDetails pageDetails1 = new ReportPageDetails();
                        List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(endListMap1, terminal, sort, start, limit, categoryTime);
                        endListMap1.put(REPORT_STATISTICS, returnListAll);
                        endListMap1.put(REPORT_CHART, lineChart1);
                        endListMap1.put(REPORT_COUNTDATA, entityList3);
                        endListMap1.put(REPORT_ROWS, pageReport1);
                        return endListMap1;
                    }
                } else {
                    String data = jc3.get((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId));
                    String dataAll = jc3.get((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"));
                    Gson gson = new Gson();
                    Map<String, List<StructureReportDTO>> list = gson.fromJson(data, new TypeToken<Map<String, List<StructureReportDTO>>>() {
                    }.getType());

                    List<StructureReportDTO> listAll = gson.fromJson(dataAll, new TypeToken<List<StructureReportDTO>>() {
                    }.getType());

                    Map<String, List<StructureReportDTO>> endListMap1 = new HashMap<>();
                    List<StructureReportDTO> entityList3 = getCountStructure(list);
                    //曲线图数据计算
                    List<StructureReportDTO> lineChart1 = getLineChart(list, terminal);
                    ReportPageDetails pageDetails1 = new ReportPageDetails();
                    List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(list, terminal, sort, start, limit, categoryTime);
                    endListMap1.put(REPORT_STATISTICS, listAll);
                    endListMap1.put(REPORT_CHART, lineChart1);
                    endListMap1.put(REPORT_COUNTDATA, entityList3);
                    endListMap1.put(REPORT_ROWS, pageReport1);
                    return endListMap1;
                }

        }
        return null;
    }

    @Override
    public Map<String, List<AccountReportDTO>> getAccountAll(int Sorted, String fieldName, int startJC, int limitJC) {

        List<AccountReportDTO> reportEntities = basisReportDAO.getAccountReport(Sorted, fieldName);

        Map<String, List<AccountReportDTO>> map = new HashMap<>();
        List<AccountReportDTO> entities = new ArrayList<>();
        ForkJoinPool joinPoolTow = new ForkJoinPool();
        //开始对数据进行处理
        Future<List<AccountReportDTO>> joinTask = joinPoolTow.submit(new AccountReportPCPlusMobUtil(reportEntities, 0, reportEntities.size()));

        try {
            entities = joinTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //关闭并行计算框架
        joinPoolTow.shutdown();

        List<AccountReportDTO> finalList = new ArrayList<>();
        for (int i = startJC; i < limitJC; i++) {
            if (i < entities.size()) {
                entities.get(i).setCount(entities.size());
                finalList.add(entities.get(i));
            }
        }
        List<AccountReportDTO> listAve = AccountReportStatisticsUtil.getAveragePro(finalList);
        AccountReportDTO dtoRing = null;
        List<AccountReportDTO> list = new ArrayList<>();
        for (int x = 0; x < listAve.size(); x++) {
            if (x % 2 == 0) {
                dtoRing = new AccountReportDTO();
                dtoRing = listAve.get(x);
            } else {
                AccountReportDTO dtoRings = new AccountReportDTO();
                dtoRings.setMobileImpression(dtoRing.getPcImpression() - listAve.get(x).getPcImpression());
                dtoRings.setMobileClick(dtoRing.getPcClick() - listAve.get(x).getPcClick());
                dtoRings.setMobileCost(dtoRing.getPcCost().subtract(listAve.get(x).getPcCost()));
                dtoRings.setMobileCpc(dtoRing.getPcCpc().subtract(listAve.get(x).getPcCpc()));
                dtoRings.setMobileCtr(dtoRing.getPcCtr() - listAve.get(x).getPcCtr());
                dtoRings.setMobileConversion(dtoRing.getPcConversion() - listAve.get(x).getPcConversion());
                dtoRings.setPcImpression((int) (((dtoRing.getPcImpression().doubleValue() - listAve.get(x).getPcImpression().doubleValue()) / ((listAve.get(x).getPcImpression() <= 0) ? 1 : listAve.get(x).getPcImpression())) * 10000));
                dtoRings.setPcClick((int) (((dtoRing.getPcClick().doubleValue() - listAve.get(x).getPcClick().doubleValue()) / ((listAve.get(x).getPcClick() <= 0) ? 1 : listAve.get(x).getPcClick())) * 10000));
                dtoRings.setPcCost(((dtoRing.getPcCost().subtract(listAve.get(x).getPcCost())).divide((listAve.get(x).getPcCost() == BigDecimal.ZERO) ? BigDecimal.valueOf(1) : listAve.get(x).getPcCost(), 4, BigDecimal.ROUND_UP)).multiply(BigDecimal.valueOf(100)));
                int s = (int) ((dtoRing.getPcCtr() - listAve.get(x).getPcCtr()) / ((listAve.get(x).getPcCtr() <= 0) ? 1 : listAve.get(x).getPcCtr()) * 10000);
                dtoRings.setPcCtr((double) s);
                dtoRings.setPcCpc(((dtoRing.getPcCpc().subtract(listAve.get(x).getPcCpc())).divide((listAve.get(x).getPcCpc() == BigDecimal.ZERO) ? BigDecimal.valueOf(1) : listAve.get(x).getPcCpc(), 4, BigDecimal.ROUND_UP)).multiply(BigDecimal.valueOf(100)));
                int a = (int) ((dtoRing.getPcConversion() - listAve.get(x).getPcConversion()) / ((listAve.get(x).getPcConversion() <= 0) ? 1 : listAve.get(x).getPcConversion()) * 10000);
                dtoRings.setPcConversion((double) a);
                list.add(dtoRings);
            }
        }
        map.put(REPORT_ROWS, listAve);
        map.put("Ring", list);
        return map;
    }

    @Override
    public Map<String, List<Object>> getAccountDateVS(Date startDate, Date endDate, Date startDate1, Date endDate1, int dateType, int devices, int compare, String sortVS, int startVS, int limitVS) {
        Date[] dateOne = getDateProcessing(startDate, endDate);
        Date[] dateTow = getDateProcessing(startDate1, endDate1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        switch (dateType) {
            case 0:
                //默认
                Map<String, List<Object>> retrunMap = new HashMap<>();
                List<Object> objectListDate1 = new ArrayList<>();
                List<Object> objectListDate2 = new ArrayList<>();
                //获取数据
                List<AccountReportDTO> listOne = basisReportDAO.getAccountReport(dateOne[0], dateOne[1]);
                //获取用户统计数据
                List<Object> userProAll = new ArrayList<>();
                List<AccountReportDTO> userPro = AccountReportStatisticsUtil.getUserPro(listOne);
                List<AccountReportDTO> userProAcerage = AccountReportStatisticsUtil.getAveragePro(userPro);
                userProAll.addAll(userProAcerage);
                //统计数据
                Map<String, List<AccountReportDTO>> responseMapOne = getUserDataPro(listOne, dateOne[0], dateOne[1]);
                Map<String, List<AccountReportDTO>> responseMapTow = null;
                //比较数据
                if (compare == 1) {
                    List<AccountReportDTO> listTow = basisReportDAO.getAccountReport(dateTow[0], dateTow[1]);
                    responseMapTow = getUserDataPro(listTow, dateTow[0], dateTow[1]);
                }
                //如果要求是全部数据
                if (devices == 0) {
                    Map<String, List<AccountReportDTO>> responseMapDevicesOne = getPcPlusMobileDate(responseMapOne);
                    List<Object> objectList = new ArrayList<>();

                    //比较数据
                    if (compare == 1) {
                        Map<String, List<AccountReportDTO>> responseMapDevicesTow = getPcPlusMobileDate(responseMapTow);
                        objectList.add(responseMapDevicesTow);
                        objectListDate2.add(dateFormat.format(dateTow[0]) + " 至 " + dateFormat.format(dateTow[1]));
                        retrunMap.put("date1", objectListDate2);
                    }
                    //计算点击率、平均价格
                    Map<String, List<AccountReportDTO>> responseMapAverageOne = getAverage(responseMapDevicesOne);

                    objectList.add(responseMapAverageOne);
                    objectListDate1.add(dateFormat.format(dateOne[0]) + " 至 " + dateFormat.format(dateOne[1]));
                    retrunMap.put(REPORT_STATISTICS, userProAll);
                    retrunMap.put(REPORT_ROWS, objectList);
                    retrunMap.put("date", objectListDate1);

                    return retrunMap;
                }

                //计算点击率、平均价格
                Map<String, List<AccountReportDTO>> responseMapAverageOne = getAverage(responseMapOne);
                List<Object> objectList = new ArrayList<>();
                objectList.add(responseMapAverageOne);

                //比较数据
                if (compare == 1) {
                    Map<String, List<AccountReportDTO>> responseMapAverageTow = getAverage(responseMapTow);
                    objectList.add(responseMapAverageTow);
                    objectListDate2.add(dateFormat.format(dateTow[0]) + " 至 " + dateFormat.format(dateTow[1]));
                    retrunMap.put("date1", objectListDate2);
                }
                objectListDate1.add(dateFormat.format(dateOne[0]) + " 至 " + dateFormat.format(dateOne[1]));
                retrunMap.put(REPORT_STATISTICS, userProAll);
                retrunMap.put(REPORT_ROWS, objectList);
                retrunMap.put("date", objectListDate1);


                return retrunMap;
            case 1:
                //分日
                Map<String, List<Object>> retrunMap1 = new HashMap<>();
                Map<String, List<AccountReportDTO>> responseMapOne1 = new HashMap<>();
                Map<String, List<AccountReportDTO>> responseMapTow1 = new HashMap<>();

                List<AccountReportDTO> listOne1 = basisReportDAO.getAccountReport(dateOne[0], dateOne[1]);
                //获取用户统计数据
                List<Object> userProAll1 = new ArrayList<>();
                List<AccountReportDTO> userPro1 = AccountReportStatisticsUtil.getUserPro(listOne1);
                List<AccountReportDTO> userProAcerage1 = AccountReportStatisticsUtil.getAveragePro(userPro1);
                userProAll1.addAll(userProAcerage1);


                List<Object> objectListDateOne1 = new ArrayList<>();
                List<Object> objectListDateTow1 = new ArrayList<>();
                for (AccountReportDTO listEnd : listOne1) {
                    List<AccountReportDTO> list = new ArrayList<>();
                    list.add(listEnd);
                    responseMapOne1.put(dateFormat.format(listEnd.getDate()), list);
                }
                List<String> dateString = DateUtils.getPeriod(dateFormat.format(dateOne[0]), dateFormat.format(dateOne[1]));
                String[] newDate = dateString.toArray(new String[dateString.size()]);
                for (String s : newDate) {
                    objectListDateOne1.add(s);
                }


                //比较数据
                if (compare == 1) {
                    List<AccountReportDTO> listTow1 = basisReportDAO.getAccountReport(dateTow[0], dateTow[1]);
                    for (AccountReportDTO listEnd : listTow1) {
                        List<AccountReportDTO> list = new ArrayList<>();
                        list.add(listEnd);
                        responseMapTow1.put(dateFormat.format(listEnd.getDate()), list);
                    }
                    List<String> dateString1 = DateUtils.getPeriod(dateFormat.format(dateTow[0]), dateFormat.format(dateTow[1]));
                    String[] newDate1 = dateString1.toArray(new String[dateString1.size()]);
                    for (String s : newDate1) {
                        objectListDateTow1.add(s);
                    }
                }
                if (devices == 0) {
                    Map<String, List<AccountReportDTO>> responseMapDevicesOne = getPcPlusMobileDate(responseMapOne1);
                    List<Object> objectList1 = new ArrayList<>();
                    objectList1.add(responseMapDevicesOne);

                    //比较数据
                    if (compare == 1) {
                        Map<String, List<AccountReportDTO>> responseMapDevicesTow = getPcPlusMobileDate(responseMapTow1);
                        for (Object o : objectListDateTow1) {
                            if (responseMapDevicesTow.get(o) == null) {
                                List<AccountReportDTO> accountReportDTOs = new ArrayList<>();
                                AccountReportDTO accountReportDTO = new AccountReportDTO();
                                accountReportDTOs.add(accountReportDTO);
                                responseMapDevicesTow.put(o.toString(), accountReportDTOs);
                            }
                        }
                        objectList1.add(responseMapDevicesTow);
                        retrunMap1.put("date1", objectListDateTow1);
                    }
                    for (Object o : objectListDateOne1) {
                        if (responseMapDevicesOne.get(o) == null) {
                            List<AccountReportDTO> accountReportDTOs = new ArrayList<>();
                            AccountReportDTO accountReportDTO = new AccountReportDTO();
                            accountReportDTOs.add(accountReportDTO);
                            responseMapDevicesOne.put(o.toString(), accountReportDTOs);
                        }
                    }
                    if (compare != 1) {
                        ReportPage reportPage = new ReportPage();
                        List<Object> pageDate = reportPage.getReportPage(responseMapDevicesOne, devices, sortVS, startVS, limitVS);
                        retrunMap1.put(REPORT_STATISTICS, userProAll1);
                        retrunMap1.put(REPORT_ROWS, objectList1);
                        retrunMap1.put("date", pageDate);
                    } else {
                        retrunMap1.put(REPORT_ROWS, objectList1);
                        retrunMap1.put("date", objectListDateOne1);
                    }
                    return retrunMap1;
                }

                Map<String, List<AccountReportDTO>> responseMapDevicesOne1 = getPcPlusMobileDate(responseMapOne1);

                List<Object> objectList1 = new ArrayList<>();
                objectList1.add(responseMapDevicesOne1);
                //比较数据
                if (compare == 1) {
                    for (Object o : objectListDateTow1) {
                        if (responseMapTow1.get(o) == null) {
                            List<AccountReportDTO> accountReportDTOs = new ArrayList<>();
                            AccountReportDTO accountReportDTO = new AccountReportDTO();
                            accountReportDTOs.add(accountReportDTO);
                            responseMapTow1.put(o.toString(), accountReportDTOs);
                        }
                    }
                    objectList1.add(responseMapTow1);
                    retrunMap1.put("date1", objectListDateTow1);
                }
                for (Object o : objectListDateOne1) {
                    if (responseMapOne1.get(o) == null) {
                        List<AccountReportDTO> accountReportDTOs = new ArrayList<>();
                        AccountReportDTO accountReportDTO = new AccountReportDTO();
                        accountReportDTOs.add(accountReportDTO);
                        responseMapOne1.put(o.toString(), accountReportDTOs);
                    }
                }
                if (compare != 1) {
                    ReportPage reportPage = new ReportPage();
                    List<Object> pageDate = reportPage.getReportPage(responseMapOne1, devices, sortVS, startVS, limitVS);
                    objectList1.add(responseMapOne1);
                    retrunMap1.put(REPORT_STATISTICS, userProAll1);
                    retrunMap1.put(REPORT_ROWS, objectList1);
                    retrunMap1.put("date", pageDate);
                } else {
                    retrunMap1.put(REPORT_ROWS, objectList1);
                    retrunMap1.put("date", objectListDateOne1);
                }
                return retrunMap1;
            case 2:
                ///分周
                Map<String, List<Object>> retrunMap2 = new HashMap<>();

                List<Object> objectListDateOne2 = new ArrayList<>();
                List<Object> objectListDateTow2 = new ArrayList<>();
                List<Object> objectListDateOne21 = new ArrayList<>();
                List<Object> objectListDateTow21 = new ArrayList<>();

                List<AccountReportDTO> listOne2 = basisReportDAO.getAccountReport(dateOne[0], dateOne[1]);

                //获取用户统计数据
                List<Object> userProAll2 = new ArrayList<>();
                List<AccountReportDTO> userPro2 = AccountReportStatisticsUtil.getUserPro(listOne2);
                List<AccountReportDTO> userProAcerage2 = AccountReportStatisticsUtil.getAveragePro(userPro2);
                userProAll2.addAll(userProAcerage2);

                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                List<String> dateListString = DateUtils.getPeriod(dateFormat.format(dateOne[0]), dateFormat.format(dateOne[1]));
                boolean judgei = true;
                for (String s : dateListString) {
                    Date judgeDate = null;
                    try {
                        judgeDate = sim.parse(s);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    for (AccountReportDTO dto : listOne2) {
                        if (judgeDate.getTime() == dto.getDate().getTime()) {
                            dto.setOrderBy("1");
                            judgei = false;
                            break;
                        } else {
                            judgei = true;
                        }
                    }
                    if (judgei) {
                        AccountReportDTO reportDTO = new AccountReportDTO();
                        reportDTO.setMobileClick(0);
                        reportDTO.setMobileConversion(0d);
                        reportDTO.setMobileCost(BigDecimal.ZERO);
                        reportDTO.setMobileCpc(BigDecimal.ZERO);
                        reportDTO.setMobileCpm(BigDecimal.ZERO);
                        reportDTO.setMobileImpression(0);
                        reportDTO.setPcClick(0);
                        reportDTO.setPcConversion(0d);
                        reportDTO.setPcCost(BigDecimal.ZERO);
                        reportDTO.setPcCpc(BigDecimal.ZERO);
                        reportDTO.setPcCpm(BigDecimal.ZERO);
                        reportDTO.setPcImpression(0);
                        reportDTO.setDate(judgeDate);
                        reportDTO.setOrderBy("1");
                        listOne2.add(reportDTO);
                    }
                }
                Collections.sort(listOne2);

                for (AccountReportDTO responseZou : listOne2) {
                    objectListDateOne2.add(responseZou.getDate());
                }

                List<Object> objectList2 = new ArrayList<>();
                List<AccountReportDTO> listTow2 = null;
                //比较数据
                if (compare == 1) {
                    listTow2 = basisReportDAO.getAccountReport(dateTow[0], dateTow[1]);
                    List<String> dateListStrings = DateUtils.getPeriod(dateFormat.format(dateTow[0]), dateFormat.format(dateTow[1]));
                    boolean judgeis = true;
                    for (String s : dateListStrings) {
                        Date judgeDate = null;
                        try {
                            judgeDate = sim.parse(s);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        for (AccountReportDTO dto : listTow2) {
                            if (judgeDate.getTime() == dto.getDate().getTime()) {
                                dto.setOrderBy("1");
                                judgeis = false;
                                break;
                            } else {
                                judgeis = true;
                            }
                        }
                        if (judgeis || listTow2.size() == 0) {
                            AccountReportDTO reportDTO = new AccountReportDTO();
                            reportDTO.setMobileClick(0);
                            reportDTO.setMobileConversion(0d);
                            reportDTO.setMobileCost(BigDecimal.ZERO);
                            reportDTO.setMobileCpc(BigDecimal.ZERO);
                            reportDTO.setMobileCpm(BigDecimal.ZERO);
                            reportDTO.setMobileImpression(0);
                            reportDTO.setPcClick(0);
                            reportDTO.setPcConversion(0d);
                            reportDTO.setPcCost(BigDecimal.ZERO);
                            reportDTO.setPcCpc(BigDecimal.ZERO);
                            reportDTO.setPcCpm(BigDecimal.ZERO);
                            reportDTO.setPcImpression(0);
                            reportDTO.setDate(judgeDate);
                            reportDTO.setOrderBy("1");
                            listTow2.add(reportDTO);
                        }
                    }

                    for (AccountReportDTO responseTowZou : listTow2) {
                        objectListDateTow2.add(responseTowZou.getDate());
                    }
                }

                int s = 0;
                int endNumber = 0;
                int steep = (objectListDateOne2.size() % 7 == 0) ? (objectListDateOne2.size() / 7) : (objectListDateOne2.size() / 7) + 1;
                for (int i = 0; i < steep; i++) {
                    List<AccountReportDTO> listDateOne = new ArrayList<>();
                    List<AccountReportDTO> listDateTow = new ArrayList<>();
                    Date[] newDateOne = null;
                    Date[] newDateTow = null;
                    for (s = endNumber; s < endNumber + 7; s++) {
                        if (endNumber >= objectListDateOne2.size() || s >= objectListDateOne2.size()) {
                            continue;
                        }
                        listDateOne.add(listOne2.get(s));
                        //比较数据
                        if (compare == 1) {
                            if (endNumber >= objectListDateTow2.size() || s >= objectListDateTow2.size()) {
                                continue;
                            }
                            listDateTow.add(listTow2.get(s));
                        }
                    }
                    endNumber = s;
                    Map<String, List<AccountReportDTO>> responseMapTow3 = null;
                    //获取数据
                    Map<String, List<AccountReportDTO>> responseMapOne3 = getUserDataPro(listDateOne, listDateOne.get(0).getDate(), listDateOne.get(listDateOne.size() - 1).getDate());
                    newDateOne = new Date[]{listDateOne.get(0).getDate(), listDateOne.get(listDateOne.size() - 1).getDate()};

                    //比较数据
                    if (compare == 1) {
                        responseMapTow3 = getUserDataPro(listDateTow, listDateTow.get(0).getDate(), listDateTow.get(listDateTow.size() - 1).getDate());
                        newDateTow = new Date[]{listDateTow.get(0).getDate(), listDateTow.get(listDateTow.size() - 1).getDate()};
                    }
                    if (devices == 0) {
                        Map<String, List<AccountReportDTO>> responseMapDevicesOne = getPcPlusMobileDate(responseMapOne3);

                        //比较数据
                        if (compare == 1) {
                            Map<String, List<AccountReportDTO>> responseMapDevicesTow = getPcPlusMobileDate(responseMapTow3);
                            objectList2.add(responseMapDevicesTow);
                            objectListDateTow21.add(dateFormat.format(newDateTow[0]) + " 至 " + dateFormat.format(newDateTow[1]));
                        }

                        objectList2.add(responseMapDevicesOne);
                        objectListDateOne21.add(dateFormat.format(newDateOne[0]) + " 至 " + dateFormat.format(newDateOne[1]));
                    } else {
                        //比较数据
                        if (compare == 1) {
                            objectList2.add(responseMapTow3);
                            objectListDateTow21.add(dateFormat.format(newDateTow[0]) + " 至 " + dateFormat.format(newDateTow[1]));
                        }
                        objectList2.add(responseMapOne3);
                        objectListDateOne21.add(dateFormat.format(newDateOne[0]) + " 至 " + dateFormat.format(newDateOne[1]));
                    }
                }
                if (compare != 1) {
                    ReportPage reportPage1 = new ReportPage();
                    List<Object> pageDate1 = reportPage1.getReportPageObj(objectList2, devices, sortVS, startVS, limitVS);
                    retrunMap2.put(REPORT_STATISTICS, userProAll2);
                    retrunMap2.put(REPORT_ROWS, objectList2);
                    retrunMap2.put("date", pageDate1);
                } else {
                    retrunMap2.put(REPORT_ROWS, objectList2);
                    retrunMap2.put("date", objectListDateOne21);
                }
                //比较数据
                if (compare == 1) {
                    retrunMap2.put("date1", objectListDateTow21);
                }
                return retrunMap2;
            case 3:
                //分月
                Map<String, List<Object>> retrunMap3 = new HashMap<>();

                List<Object> objectListDateOne3 = new ArrayList<>();
                List<Object> objectListDateTow3 = new ArrayList<>();
                List<Object> objectListDateOne31 = new ArrayList<>();
                List<Object> objectListDateTow31 = new ArrayList<>();

                List<AccountReportDTO> listOne3 = basisReportDAO.getAccountReport(dateOne[0], dateOne[1]);
                //获取用户统计数据
                List<Object> userProAll3 = new ArrayList<>();
                List<AccountReportDTO> userPro3 = AccountReportStatisticsUtil.getUserPro(listOne3);
                List<AccountReportDTO> userProAcerage3 = AccountReportStatisticsUtil.getAveragePro(userPro3);
                userProAll3.addAll(userProAcerage3);


                SimpleDateFormat simt = new SimpleDateFormat("yyyy-MM-dd");
                List<String> dateListStringt = DateUtils.getPeriod(dateFormat.format(dateOne[0]), dateFormat.format(dateOne[1]));
                boolean judgeit = true;
                for (String st : dateListStringt) {
                    Date judgeDate = null;
                    try {
                        judgeDate = simt.parse(st);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    for (AccountReportDTO dto : listOne3) {
                        if (judgeDate.getTime() == dto.getDate().getTime()) {
                            dto.setOrderBy("1");
                            judgeit = false;
                            break;
                        } else {
                            judgeit = true;
                        }
                    }
                    if (judgeit) {
                        AccountReportDTO reportDTO = new AccountReportDTO();
                        reportDTO.setMobileClick(0);
                        reportDTO.setMobileConversion(0d);
                        reportDTO.setMobileCost(BigDecimal.ZERO);
                        reportDTO.setMobileCpc(BigDecimal.ZERO);
                        reportDTO.setMobileCpm(BigDecimal.ZERO);
                        reportDTO.setMobileImpression(0);
                        reportDTO.setPcClick(0);
                        reportDTO.setPcConversion(0d);
                        reportDTO.setPcCost(BigDecimal.ZERO);
                        reportDTO.setPcCpc(BigDecimal.ZERO);
                        reportDTO.setPcCpm(BigDecimal.ZERO);
                        reportDTO.setPcImpression(0);
                        reportDTO.setDate(judgeDate);
                        reportDTO.setOrderBy("1");
                        listOne3.add(reportDTO);
                    }
                }
                Collections.sort(listOne3);

                for (AccountReportDTO responseYue : listOne3) {
                    objectListDateOne3.add(responseYue.getDate());
                }
                List<AccountReportDTO> listTow3 = null;
                List<String> dateListStringst = null;

                //比较数据
                if (compare == 1) {
                    listTow3 = basisReportDAO.getAccountReport(dateTow[0], dateTow[1]);

                    dateListStringst = DateUtils.getPeriod(dateFormat.format(dateTow[0]), dateFormat.format(dateTow[1]));
                    boolean judgeist = true;
                    for (String st : dateListStringst) {
                        Date judgeDate = null;
                        try {
                            judgeDate = simt.parse(st);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        for (AccountReportDTO dto : listTow3) {
                            if (judgeDate.getTime() == dto.getDate().getTime()) {
                                dto.setOrderBy("1");
                                judgeist = false;
                                break;
                            } else {
                                judgeist = true;
                            }
                        }
                        if (judgeist || listTow3.size() == 0) {
                            AccountReportDTO reportDTO = new AccountReportDTO();
                            reportDTO.setMobileClick(0);
                            reportDTO.setMobileConversion(0d);
                            reportDTO.setMobileCost(BigDecimal.ZERO);
                            reportDTO.setMobileCpc(BigDecimal.ZERO);
                            reportDTO.setMobileCpm(BigDecimal.ZERO);
                            reportDTO.setMobileImpression(0);
                            reportDTO.setPcClick(0);
                            reportDTO.setPcConversion(0d);
                            reportDTO.setPcCost(BigDecimal.ZERO);
                            reportDTO.setPcCpc(BigDecimal.ZERO);
                            reportDTO.setPcCpm(BigDecimal.ZERO);
                            reportDTO.setPcImpression(0);
                            reportDTO.setDate(judgeDate);
                            reportDTO.setOrderBy("1");
                            listTow3.add(reportDTO);
                        }
                    }
                    Collections.sort(listTow3);

                    for (AccountReportDTO responseTowYue : listTow3) {
                        objectListDateTow3.add(responseTowYue.getDate());
                    }
                }

                List<Object> objectList3 = new ArrayList<>();


                Calendar calendar = Calendar.getInstance();
                int index = 0;
                int im = Integer.parseInt(dateListStringt.get(0).substring(dateListStringt.get(0).length() - 2, dateListStringt.get(0).length()));

                if (im == 1) {
                    index = 0;
                } else {
                    index = im - 1;
                }

                int numbert = 0;
                try {
                    calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dateListStringt.get(0)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                List<AccountReportDTO> listDateOne1 = new ArrayList<>();
                for (int j = 0; j < dateListStringt.size() - 1; j++) {
                    int is = 0;
                    if (im != 1) {
                        is = (numbert + index) - (im - 1);
                    } else {
                        is = (numbert + index);
                    }
                    index++;

                    listDateOne1.add(listOne3.get(j));

                    if (index == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                        //获取数据
                        Map<String, List<AccountReportDTO>> responseMapOne4 = null;
                        try {
                            responseMapOne4 = getUserDataPro(listDateOne1, dateFormat.parse(dateListStringt.get(numbert)), dateFormat.parse(dateListStringt.get(is)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        if (devices == 0) {
                            Map<String, List<AccountReportDTO>> responseMapDevicesOne = getPcPlusMobileDate(responseMapOne4);
                            objectList3.add(responseMapDevicesOne);
                            objectListDateOne31.add(dateListStringt.get(numbert) + " 至 " + dateListStringt.get(is));

                        } else {
                            objectList3.add(responseMapOne4);
                            objectListDateOne31.add(dateListStringt.get(numbert) + " 至 " + dateListStringt.get(is));

                        }
                        if (im != 1) {
                            numbert = (numbert + index) - (im - 1);
                            im = 1;
                        } else {
                            numbert = numbert + index;
                        }


                        index = 0;
                        listDateOne1 = new ArrayList<>();

                    }
                    try {
                        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dateListStringt.get(j)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (dateListStringt.size() == 1) {
                    listDateOne1.add(listOne3.get(0));
                }
                if (index > 0) {
                    //获取数据
                    Map<String, List<AccountReportDTO>> responseMapOne4 = null;
                    try {
                        responseMapOne4 = getUserDataPro(listDateOne1, dateFormat.parse(dateListStringt.get(numbert)), dateFormat.parse(dateListStringt.get((numbert + index) - (im - 1))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    if (devices == 0) {
                        Map<String, List<AccountReportDTO>> responseMapDevicesOne = getPcPlusMobileDate(responseMapOne4);
                        objectList3.add(responseMapDevicesOne);
                        objectListDateOne31.add(dateListStringt.get(numbert) + " 至 " + dateListStringt.get((numbert + index) - (im - 1)));

                    } else {
                        objectList3.add(responseMapOne4);
                        objectListDateOne31.add(dateListStringt.get(numbert) + " 至 " + dateListStringt.get((numbert + index) - (im - 1)));
                    }
                }


                //比较数据
                if (compare == 1) {

                    Calendar calendar1 = Calendar.getInstance();
                    int index1 = 0;
                    int imt = Integer.parseInt(dateListStringst.get(0).substring(dateListStringst.get(0).length() - 2, dateListStringst.get(0).length()));

                    if (imt == 1) {
                        index1 = 0;
                    } else {
                        index1 = imt - 1;
                    }

                    int numbert1 = 0;
                    try {
                        calendar1.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dateListStringst.get(0)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    List<AccountReportDTO> listDateTow1 = new ArrayList<>();
                    for (int j = 0; j < dateListStringst.size() - 1; j++) {
                        int is = 0;
                        if (imt != 1) {
                            is = (numbert1 + index1) - (imt - 1);
                        } else {
                            is = (numbert1 + index1);
                        }

                        index1++;

                        listDateTow1.add(listTow3.get(j));


                        if (index1 == calendar1.getActualMaximum(Calendar.DAY_OF_MONTH)) {

                            //比较数据
                            Map<String, List<AccountReportDTO>> responseMapTow4 = null;
                            try {
                                responseMapTow4 = getUserDataPro(listDateTow1, dateFormat.parse(dateListStringst.get(numbert1)), dateFormat.parse(dateListStringst.get(is)));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if (devices == 0) {
                                //比较数据
                                Map<String, List<AccountReportDTO>> responseMapDevicesTow = getPcPlusMobileDate(responseMapTow4);
                                objectList3.add(responseMapDevicesTow);
                                objectListDateTow31.add(dateListStringst.get(numbert1) + " 至 " + dateListStringst.get(is));
                            } else {
                                //比较数据
                                objectList3.add(responseMapTow4);
                                objectListDateTow31.add(dateListStringst.get(numbert1) + " 至 " + dateListStringst.get(is));
                            }
                            if (imt != 1) {
                                numbert1 = (numbert1 + index1) - (imt - 1);
                                imt = 1;
                            } else {
                                numbert1 = numbert1 + index1;
                            }
                            index1 = 0;
                            listDateTow1 = new ArrayList<>();

                        }
                        try {
                            calendar1.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dateListStringst.get(j)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if (index1 > 0) {
                        //比较数据
                        Map<String, List<AccountReportDTO>> responseMapTow4 = null;
                        try {
                            responseMapTow4 = getUserDataPro(listDateTow1, dateFormat.parse(dateListStringst.get(numbert1)), dateFormat.parse(dateListStringst.get((numbert1 + index1) - (imt - 1))));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (devices == 0) {
                            //比较数据
                            Map<String, List<AccountReportDTO>> responseMapDevicesTow = getPcPlusMobileDate(responseMapTow4);
                            objectList3.add(responseMapDevicesTow);
                            objectListDateTow31.add(dateListStringst.get(numbert1) + " 至 " + dateListStringst.get((numbert1 + index1) - (imt - 1)));
                        } else {
                            //比较数据
                            objectList3.add(responseMapTow4);
                            objectListDateTow31.add(dateListStringst.get(numbert1) + " 至 " + dateListStringst.get((numbert1 + index1) - (imt - 1)));
                        }
                    }
                }


                if (compare != 1) {
                    ReportPage reportPage2 = new ReportPage();
                    List<Object> pageDate2 = reportPage2.getReportPageObj(objectList3, devices, sortVS, startVS, limitVS);
                    retrunMap3.put(REPORT_STATISTICS, userProAll3);
                    retrunMap3.put(REPORT_ROWS, objectList3);
                    retrunMap3.put("date", pageDate2);
                } else {
                    retrunMap3.put(REPORT_STATISTICS, userProAll3);
                    retrunMap3.put(REPORT_ROWS, objectList3);
                    retrunMap3.put("date", objectListDateOne31);
                }
                //比较数据
                if (compare == 1) {
                    retrunMap3.put(REPORT_STATISTICS, userProAll3);
                    retrunMap3.put("date1", objectListDateTow31);
                }
                return retrunMap3;
        }
        return null;
    }

    @Override
    public void downReportCSV(OutputStream os, String redisKey, int dateType, int terminal, int reportType, String dateHead) {
        Jedis jc = JRedisUtils.get();
        Gson gson = new Gson();
        String data = jc.get(redisKey);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("#.0000");
        String head = ReportDownUtil.getHead(reportType);
        if (dateType < 1) {
            List<StructureReportDTO> list = gson.fromJson(data, new TypeToken<List<StructureReportDTO>>() {
            }.getType());
            try {

                os.write(Bytes.concat(commonCSVHead, (head).getBytes(StandardCharsets.UTF_8)));
                if (terminal != 2) {
                    for (StructureReportDTO entity : list) {
                        os.write(Bytes.concat(commonCSVHead, (dateHead +
                                ((entity.getAccount() == null) ? "" : DEFAULT_DELIMITER + entity.getAccount()) +
                                ((entity.getCampaignName() == null) ? "" : DEFAULT_DELIMITER + entity.getCampaignName()) +
                                ((entity.getAdgroupName() == null) ? "" : DEFAULT_DELIMITER + entity.getAdgroupName()) +
                                ((entity.getKeywordName() == null) ? "" : DEFAULT_DELIMITER + entity.getKeywordName()) +
                                ((entity.getCreativeTitle() == null) ? "" : DEFAULT_DELIMITER + entity.getCreativeTitle()) +
                                ((entity.getRegionName() == null) ? "" : DEFAULT_DELIMITER + entity.getRegionName()) +
                                DEFAULT_DELIMITER + entity.getPcImpression() +
                                DEFAULT_DELIMITER + entity.getPcClick() +
                                DEFAULT_DELIMITER + entity.getPcCost() +
                                DEFAULT_DELIMITER + entity.getPcCtr() * 100 / 100 + "%" +
                                DEFAULT_DELIMITER + entity.getPcCpc() +
                                DEFAULT_DELIMITER + entity.getPcConversion() +
                                DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                    }
                } else {
                    for (StructureReportDTO entity : list) {
                        os.write(Bytes.concat(commonCSVHead, (dateHead +
                                ((entity.getAccount() == null) ? "" : DEFAULT_DELIMITER + entity.getAccount()) +
                                ((entity.getCampaignName() == null) ? "" : DEFAULT_DELIMITER + entity.getCampaignName()) +
                                ((entity.getAdgroupName() == null) ? "" : DEFAULT_DELIMITER + entity.getAdgroupName()) +
                                ((entity.getKeywordName() == null) ? "" : DEFAULT_DELIMITER + entity.getKeywordName()) +
                                ((entity.getCreativeTitle() == null) ? "" : DEFAULT_DELIMITER + entity.getCreativeTitle()) +
                                ((entity.getRegionName() == null) ? "" : DEFAULT_DELIMITER + entity.getRegionName()) +
                                DEFAULT_DELIMITER + entity.getMobileImpression() +
                                DEFAULT_DELIMITER + entity.getMobileClick() +
                                DEFAULT_DELIMITER + entity.getMobileCost() +
                                DEFAULT_DELIMITER + entity.getMobileCtr() * 100 / 100 + "%" +
                                DEFAULT_DELIMITER + entity.getMobileCpc() +
                                DEFAULT_DELIMITER + entity.getMobileConversion() +
                                DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Map<String, List<StructureReportDTO>> responseMap = gson.fromJson(data, new TypeToken<Map<String, List<StructureReportDTO>>>() {
            }.getType());

            try {

                os.write(Bytes.concat(commonCSVHead, (head).getBytes(StandardCharsets.UTF_8)));
                if (terminal != 2) {
                    for (Map.Entry<String, List<StructureReportDTO>> voEntity : responseMap.entrySet()) {
                        for (StructureReportDTO entity : voEntity.getValue()) {
                            os.write(Bytes.concat(commonCSVHead, (voEntity.getKey() +
                                    ((entity.getAccount() == null) ? "" : DEFAULT_DELIMITER + entity.getAccount()) +
                                    ((entity.getCampaignName() == null) ? "" : DEFAULT_DELIMITER + entity.getCampaignName()) +
                                    ((entity.getAdgroupName() == null) ? "" : DEFAULT_DELIMITER + entity.getAdgroupName()) +
                                    ((entity.getKeywordName() == null) ? "" : DEFAULT_DELIMITER + entity.getKeywordName()) +
                                    ((entity.getCreativeTitle() == null) ? "" : DEFAULT_DELIMITER + entity.getCreativeTitle()) +
                                    ((entity.getRegionName() == null) ? "" : DEFAULT_DELIMITER + entity.getRegionName()) +
                                    DEFAULT_DELIMITER + entity.getPcImpression() +
                                    DEFAULT_DELIMITER + entity.getPcClick() +
                                    DEFAULT_DELIMITER + entity.getPcCost() +
                                    DEFAULT_DELIMITER + entity.getPcCtr() * 100 / 100 + "%" +
                                    DEFAULT_DELIMITER + entity.getPcCpc() +
                                    DEFAULT_DELIMITER + entity.getPcConversion() +
                                    DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                        }
                    }
                } else {
                    for (Map.Entry<String, List<StructureReportDTO>> voEntity : responseMap.entrySet()) {
                        for (StructureReportDTO entity : voEntity.getValue()) {
                            os.write(Bytes.concat(commonCSVHead, (voEntity.getKey() +
                                    ((entity.getAccount() == null) ? "" : DEFAULT_DELIMITER + entity.getAccount()) +
                                    ((entity.getCampaignName() == null) ? "" : DEFAULT_DELIMITER + entity.getCampaignName()) +
                                    ((entity.getAdgroupName() == null) ? "" : DEFAULT_DELIMITER + entity.getAdgroupName()) +
                                    ((entity.getKeywordName() == null) ? "" : DEFAULT_DELIMITER + entity.getKeywordName()) +
                                    ((entity.getCreativeTitle() == null) ? "" : DEFAULT_DELIMITER + entity.getCreativeTitle()) +
                                    ((entity.getRegionName() == null) ? "" : DEFAULT_DELIMITER + entity.getRegionName()) +
                                    DEFAULT_DELIMITER + entity.getMobileImpression() +
                                    DEFAULT_DELIMITER + entity.getMobileClick() +
                                    DEFAULT_DELIMITER + entity.getMobileCost() +
                                    DEFAULT_DELIMITER + entity.getMobileCtr() * 100 / 100 + "%" +
                                    DEFAULT_DELIMITER + entity.getMobileCpc() +
                                    DEFAULT_DELIMITER + entity.getMobileConversion() +
                                    DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void downAccountReportCSV(OutputStream os, Date startDate, Date endDate, Date startDate1, Date endDate1, int dateType, int devices, String sortVS, int startVS, int limitVS) {
        Date[] dateOne = getDateProcessing(startDate, endDate);
        Date[] dateTow = getDateProcessing(startDate1, endDate1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        switch (dateType) {
            case 0:
                //获取数据
                List<AccountReportDTO> listOne = basisReportDAO.getAccountReport(dateOne[0], dateOne[1]);
                //获取用户统计数据
                List<Object> userProAll = new ArrayList<>();
                List<AccountReportDTO> userPro = AccountReportStatisticsUtil.getUserPro(listOne);
                List<AccountReportDTO> userProAcerage = AccountReportStatisticsUtil.getAveragePro(userPro);
                userProAll.addAll(userProAcerage);
                //统计数据
                Map<String, List<AccountReportDTO>> responseMapOne = getUserDataPro(listOne, dateOne[0], dateOne[1]);

                //如果要求是全部数据
                if (devices == 0) {
                    Map<String, List<AccountReportDTO>> responseMapDevicesOne = getPcPlusMobileDate(responseMapOne);

                    //计算点击率、平均价格
                    Map<String, List<AccountReportDTO>> responseMapAverageOne = getAverage(responseMapDevicesOne);
                    try {
                        os.write(Bytes.concat(commonCSVHead, ("时间" +
                                DEFAULT_DELIMITER + "展现量" +
                                DEFAULT_DELIMITER + "点击量" +
                                DEFAULT_DELIMITER + "消费" +
                                DEFAULT_DELIMITER + "点击率" +
                                DEFAULT_DELIMITER + "平均点击价格" +
                                DEFAULT_DELIMITER + "转化(页面)" +
                                DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                        for (Map.Entry<String, List<AccountReportDTO>> voEntity : responseMapAverageOne.entrySet()) {
                            for (AccountReportDTO entity : voEntity.getValue()) {
                                os.write(Bytes.concat(commonCSVHead, (voEntity.getKey() +
                                        DEFAULT_DELIMITER + entity.getPcImpression() +
                                        DEFAULT_DELIMITER + entity.getPcClick() +
                                        DEFAULT_DELIMITER + entity.getPcCost() +
                                        DEFAULT_DELIMITER + entity.getPcCtr() * 100 / 100 + "%" +
                                        DEFAULT_DELIMITER + entity.getPcCpc() +
                                        DEFAULT_DELIMITER + entity.getPcConversion() +
                                        DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    //计算点击率、平均价格
                    Map<String, List<AccountReportDTO>> responseMapAverageOne = getAverage(responseMapOne);

                    if (devices == 1) {
                        try {
                            os.write(Bytes.concat(commonCSVHead, ("时间" +
                                    DEFAULT_DELIMITER + "展现量" +
                                    DEFAULT_DELIMITER + "点击量" +
                                    DEFAULT_DELIMITER + "消费" +
                                    DEFAULT_DELIMITER + "点击率" +
                                    DEFAULT_DELIMITER + "平均点击价格" +
                                    DEFAULT_DELIMITER + "转化(页面)" +
                                    DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                            for (Map.Entry<String, List<AccountReportDTO>> voEntity : responseMapAverageOne.entrySet()) {
                                for (AccountReportDTO entity : voEntity.getValue()) {
                                    os.write(Bytes.concat(commonCSVHead, (voEntity.getKey() +
                                            DEFAULT_DELIMITER + entity.getPcImpression() +
                                            DEFAULT_DELIMITER + entity.getPcClick() +
                                            DEFAULT_DELIMITER + entity.getPcCost() +
                                            DEFAULT_DELIMITER + entity.getPcCtr() * 100 / 100 + "%" +
                                            DEFAULT_DELIMITER + entity.getPcCpc() +
                                            DEFAULT_DELIMITER + entity.getPcConversion() +
                                            DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (devices == 2) {
                        try {
                            os.write(Bytes.concat(commonCSVHead, ("时间" +
                                    DEFAULT_DELIMITER + "展现量" +
                                    DEFAULT_DELIMITER + "点击量" +
                                    DEFAULT_DELIMITER + "消费" +
                                    DEFAULT_DELIMITER + "点击率" +
                                    DEFAULT_DELIMITER + "平均点击价格" +
                                    DEFAULT_DELIMITER + "转化(页面)" +
                                    DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                            for (Map.Entry<String, List<AccountReportDTO>> voEntity : responseMapAverageOne.entrySet()) {
                                for (AccountReportDTO entity : voEntity.getValue()) {
                                    os.write(Bytes.concat(commonCSVHead, (voEntity.getKey() +
                                            DEFAULT_DELIMITER + entity.getMobileImpression() +
                                            DEFAULT_DELIMITER + entity.getMobileClick() +
                                            DEFAULT_DELIMITER + entity.getMobileCost() +
                                            DEFAULT_DELIMITER + entity.getMobileCtr() * 100 / 100 + "%" +
                                            DEFAULT_DELIMITER + entity.getMobileCpc() +
                                            DEFAULT_DELIMITER + entity.getMobileConversion() +
                                            DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case 1:
                //分日
                Map<String, List<AccountReportDTO>> responseMapOne1 = new HashMap<>();

                List<AccountReportDTO> listOne1 = basisReportDAO.getAccountReport(dateOne[0], dateOne[1]);
                //获取用户统计数据
                List<Object> userProAll1 = new ArrayList<>();
                List<AccountReportDTO> userPro1 = AccountReportStatisticsUtil.getUserPro(listOne1);
                List<AccountReportDTO> userProAcerage1 = AccountReportStatisticsUtil.getAveragePro(userPro1);
                userProAll1.addAll(userProAcerage1);


                List<Object> objectListDateOne1 = new ArrayList<>();
                for (AccountReportDTO listEnd : listOne1) {
                    List<AccountReportDTO> list = new ArrayList<>();
                    list.add(listEnd);
                    responseMapOne1.put(dateFormat.format(listEnd.getDate()), list);
                }
                List<String> dateString = DateUtils.getPeriod(dateFormat.format(dateOne[0]), dateFormat.format(dateOne[1]));
                String[] newDate = dateString.toArray(new String[dateString.size()]);
                for (String s : newDate) {
                    objectListDateOne1.add(s);
                }


                if (devices == 0) {
                    Map<String, List<AccountReportDTO>> responseMapDevicesOne = getPcPlusMobileDate(responseMapOne1);
                    List<Object> objectList1 = new ArrayList<>();
                    objectList1.add(responseMapDevicesOne);


                    for (Object o : objectListDateOne1) {
                        if (responseMapDevicesOne.get(o) == null) {
                            List<AccountReportDTO> accountReportDTOs = new ArrayList<>();
                            AccountReportDTO accountReportDTO = new AccountReportDTO();
                            accountReportDTOs.add(accountReportDTO);
                            responseMapDevicesOne.put(o.toString(), accountReportDTOs);
                        }
                    }

                    try {
                        os.write(Bytes.concat(commonCSVHead, ("时间" +
                                DEFAULT_DELIMITER + "展现量" +
                                DEFAULT_DELIMITER + "点击量" +
                                DEFAULT_DELIMITER + "消费" +
                                DEFAULT_DELIMITER + "点击率" +
                                DEFAULT_DELIMITER + "平均点击价格" +
                                DEFAULT_DELIMITER + "转化(页面)" +
                                DEFAULT_END).getBytes(StandardCharsets.UTF_8)));

                        for (Map.Entry<String, List<AccountReportDTO>> voEntity : responseMapDevicesOne.entrySet()) {
                            for (AccountReportDTO entity : voEntity.getValue()) {
                                os.write(Bytes.concat(commonCSVHead, (voEntity.getKey() +
                                        DEFAULT_DELIMITER + entity.getPcImpression() +
                                        DEFAULT_DELIMITER + entity.getPcClick() +
                                        DEFAULT_DELIMITER + entity.getPcCost() +
                                        DEFAULT_DELIMITER + entity.getPcCtr() * 100 / 100 + "%" +
                                        DEFAULT_DELIMITER + entity.getPcCpc() +
                                        DEFAULT_DELIMITER + entity.getPcConversion() +
                                        DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    for (Object o : objectListDateOne1) {
                        if (responseMapOne1.get(o) == null) {
                            List<AccountReportDTO> accountReportDTOs = new ArrayList<>();
                            AccountReportDTO accountReportDTO = new AccountReportDTO();
                            accountReportDTOs.add(accountReportDTO);
                            responseMapOne1.put(o.toString(), accountReportDTOs);
                        }
                    }

                    if (devices == 1) {
                        try {
                            os.write(Bytes.concat(commonCSVHead, ("时间" +
                                    DEFAULT_DELIMITER + "展现量" +
                                    DEFAULT_DELIMITER + "点击量" +
                                    DEFAULT_DELIMITER + "消费" +
                                    DEFAULT_DELIMITER + "点击率" +
                                    DEFAULT_DELIMITER + "平均点击价格" +
                                    DEFAULT_DELIMITER + "转化(页面)" +
                                    DEFAULT_END).getBytes(StandardCharsets.UTF_8)));

                            for (Map.Entry<String, List<AccountReportDTO>> voEntity : responseMapOne1.entrySet()) {
                                for (AccountReportDTO entity : voEntity.getValue()) {
                                    os.write(Bytes.concat(commonCSVHead, (voEntity.getKey() +
                                            DEFAULT_DELIMITER + entity.getPcImpression() +
                                            DEFAULT_DELIMITER + entity.getPcClick() +
                                            DEFAULT_DELIMITER + entity.getPcCost() +
                                            DEFAULT_DELIMITER + entity.getPcCtr() * 100 / 100 + "%" +
                                            DEFAULT_DELIMITER + entity.getPcCpc() +
                                            DEFAULT_DELIMITER + entity.getPcConversion() +
                                            DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            os.write(Bytes.concat(commonCSVHead, ("时间" +
                                    DEFAULT_DELIMITER + "展现量" +
                                    DEFAULT_DELIMITER + "点击量" +
                                    DEFAULT_DELIMITER + "消费" +
                                    DEFAULT_DELIMITER + "点击率" +
                                    DEFAULT_DELIMITER + "平均点击价格" +
                                    DEFAULT_DELIMITER + "转化(页面)" +
                                    DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                            for (Map.Entry<String, List<AccountReportDTO>> voEntity : responseMapOne1.entrySet()) {
                                for (AccountReportDTO entity : voEntity.getValue()) {
                                    os.write(Bytes.concat(commonCSVHead, (voEntity.getKey() +
                                            DEFAULT_DELIMITER + entity.getMobileImpression() +
                                            DEFAULT_DELIMITER + entity.getMobileClick() +
                                            DEFAULT_DELIMITER + entity.getMobileCost() +
                                            DEFAULT_DELIMITER + entity.getMobileCtr() * 100 / 100 + "%" +
                                            DEFAULT_DELIMITER + entity.getMobileCpc() +
                                            DEFAULT_DELIMITER + entity.getMobileConversion() +
                                            DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case 2:
                ///分周

                List<Object> objectListDateOne2 = new ArrayList<>();
                List<AccountReportDTO> listOne2 = basisReportDAO.getAccountReport(dateOne[0], dateOne[1]);

                //获取用户统计数据
                List<Object> userProAll2 = new ArrayList<>();
                List<AccountReportDTO> userPro2 = AccountReportStatisticsUtil.getUserPro(listOne2);
                List<AccountReportDTO> userProAcerage2 = AccountReportStatisticsUtil.getAveragePro(userPro2);
                userProAll2.addAll(userProAcerage2);

                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                List<String> dateListString = DateUtils.getPeriod(dateFormat.format(dateOne[0]), dateFormat.format(dateOne[1]));
                boolean judgei = true;
                for (String s : dateListString) {
                    Date judgeDate = null;
                    try {
                        judgeDate = sim.parse(s);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    for (AccountReportDTO dto : listOne2) {
                        if (judgeDate.getTime() == dto.getDate().getTime()) {
                            dto.setOrderBy("1");
                            judgei = false;
                            break;
                        } else {
                            judgei = true;
                        }
                    }
                    if (judgei) {
                        AccountReportDTO reportDTO = new AccountReportDTO();
                        reportDTO.setMobileClick(0);
                        reportDTO.setMobileConversion(0d);
                        reportDTO.setMobileCost(BigDecimal.ZERO);
                        reportDTO.setMobileCpc(BigDecimal.ZERO);
                        reportDTO.setMobileCpm(BigDecimal.ZERO);
                        reportDTO.setMobileImpression(0);
                        reportDTO.setPcClick(0);
                        reportDTO.setPcConversion(0d);
                        reportDTO.setPcCost(BigDecimal.ZERO);
                        reportDTO.setPcCpc(BigDecimal.ZERO);
                        reportDTO.setPcCpm(BigDecimal.ZERO);
                        reportDTO.setPcImpression(0);
                        reportDTO.setDate(judgeDate);
                        reportDTO.setOrderBy("1");
                        listOne2.add(reportDTO);
                    }
                }
                Collections.sort(listOne2);

                for (AccountReportDTO responseZou : listOne2) {
                    objectListDateOne2.add(responseZou.getDate());
                }

                try {
                    os.write(Bytes.concat(commonCSVHead, ("时间" +
                            DEFAULT_DELIMITER + "展现量" +
                            DEFAULT_DELIMITER + "点击量" +
                            DEFAULT_DELIMITER + "消费" +
                            DEFAULT_DELIMITER + "点击率" +
                            DEFAULT_DELIMITER + "平均点击价格" +
                            DEFAULT_DELIMITER + "转化(页面)" +
                            DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int s = 0;
                int endNumber = 0;
                int steep = (objectListDateOne2.size() % 7 == 0) ? (objectListDateOne2.size() / 7) : (objectListDateOne2.size() / 7) + 1;
                for (int i = 0; i < steep; i++) {
                    List<AccountReportDTO> listDateOne = new ArrayList<>();
                    for (s = endNumber; s < endNumber + 7; s++) {
                        if (endNumber >= objectListDateOne2.size() || s >= objectListDateOne2.size()) {
                            continue;
                        }
                        listDateOne.add(listOne2.get(s));

                    }
                    endNumber = s;

                    //获取数据
                    Map<String, List<AccountReportDTO>> responseMapOne3 = getUserDataPro(listDateOne, listDateOne.get(0).getDate(), listDateOne.get(listDateOne.size() - 1).getDate());
                    if (devices == 0) {
                        Map<String, List<AccountReportDTO>> responseMapDevicesOne = getPcPlusMobileDate(responseMapOne3);

                        try {
                            for (Map.Entry<String, List<AccountReportDTO>> voEntity : responseMapDevicesOne.entrySet()) {
                                for (AccountReportDTO entity : voEntity.getValue()) {
                                    os.write(Bytes.concat(commonCSVHead, (voEntity.getKey() +
                                            DEFAULT_DELIMITER + entity.getPcImpression() +
                                            DEFAULT_DELIMITER + entity.getPcClick() +
                                            DEFAULT_DELIMITER + entity.getPcCost() +
                                            DEFAULT_DELIMITER + entity.getPcCtr() * 100 / 100 + "%" +
                                            DEFAULT_DELIMITER + entity.getPcCpc() +
                                            DEFAULT_DELIMITER + entity.getPcConversion() +
                                            DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (devices == 1) {
                            try {
                                for (Map.Entry<String, List<AccountReportDTO>> voEntity : responseMapOne3.entrySet()) {
                                    for (AccountReportDTO entity : voEntity.getValue()) {
                                        os.write(Bytes.concat(commonCSVHead, (voEntity.getKey() +
                                                DEFAULT_DELIMITER + entity.getPcImpression() +
                                                DEFAULT_DELIMITER + entity.getPcClick() +
                                                DEFAULT_DELIMITER + entity.getPcCost() +
                                                DEFAULT_DELIMITER + entity.getPcCtr() * 100 / 100 + "%" +
                                                DEFAULT_DELIMITER + entity.getPcCpc() +
                                                DEFAULT_DELIMITER + entity.getPcConversion() +
                                                DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                for (Map.Entry<String, List<AccountReportDTO>> voEntity : responseMapOne3.entrySet()) {
                                    for (AccountReportDTO entity : voEntity.getValue()) {
                                        os.write(Bytes.concat(commonCSVHead, (voEntity.getKey() +
                                                DEFAULT_DELIMITER + entity.getMobileImpression() +
                                                DEFAULT_DELIMITER + entity.getMobileClick() +
                                                DEFAULT_DELIMITER + entity.getMobileCost() +
                                                DEFAULT_DELIMITER + entity.getMobileCtr() * 100 / 100 + "%" +
                                                DEFAULT_DELIMITER + entity.getMobileCpc() +
                                                DEFAULT_DELIMITER + entity.getMobileConversion() +
                                                DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }


                break;
            case 3:
                //分月

                List<Object> objectListDateOne3 = new ArrayList<>();
                List<Object> objectListDateOne31 = new ArrayList<>();

                List<AccountReportDTO> listOne3 = basisReportDAO.getAccountReport(dateOne[0], dateOne[1]);
                //获取用户统计数据
                List<Object> userProAll3 = new ArrayList<>();
                List<AccountReportDTO> userPro3 = AccountReportStatisticsUtil.getUserPro(listOne3);
                List<AccountReportDTO> userProAcerage3 = AccountReportStatisticsUtil.getAveragePro(userPro3);
                userProAll3.addAll(userProAcerage3);


                SimpleDateFormat simt = new SimpleDateFormat("yyyy-MM-dd");
                List<String> dateListStringt = DateUtils.getPeriod(dateFormat.format(dateOne[0]), dateFormat.format(dateOne[1]));
                boolean judgeit = true;
                for (String st : dateListStringt) {
                    Date judgeDate = null;
                    try {
                        judgeDate = simt.parse(st);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    for (AccountReportDTO dto : listOne3) {
                        if (judgeDate.getTime() == dto.getDate().getTime()) {
                            dto.setOrderBy("1");
                            judgeit = false;
                            break;
                        } else {
                            judgeit = true;
                        }
                    }
                    if (judgeit) {
                        AccountReportDTO reportDTO = new AccountReportDTO();
                        reportDTO.setMobileClick(0);
                        reportDTO.setMobileConversion(0d);
                        reportDTO.setMobileCost(BigDecimal.ZERO);
                        reportDTO.setMobileCpc(BigDecimal.ZERO);
                        reportDTO.setMobileCpm(BigDecimal.ZERO);
                        reportDTO.setMobileImpression(0);
                        reportDTO.setPcClick(0);
                        reportDTO.setPcConversion(0d);
                        reportDTO.setPcCost(BigDecimal.ZERO);
                        reportDTO.setPcCpc(BigDecimal.ZERO);
                        reportDTO.setPcCpm(BigDecimal.ZERO);
                        reportDTO.setPcImpression(0);
                        reportDTO.setDate(judgeDate);
                        reportDTO.setOrderBy("1");
                        listOne3.add(reportDTO);
                    }
                }
                Collections.sort(listOne3);

                for (AccountReportDTO responseYue : listOne3) {
                    objectListDateOne3.add(responseYue.getDate());
                }


                Map<String, List<AccountReportDTO>> objectList3 = new HashMap<>();


                Calendar calendar = Calendar.getInstance();
                int index = 0;
                int im = Integer.parseInt(dateListStringt.get(0).substring(dateListStringt.get(0).length() - 2, dateListStringt.get(0).length()));

                if (im == 1) {
                    index = 0;
                } else {
                    index = im - 1;
                }

                int numbert = 0;
                try {
                    calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dateListStringt.get(0)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                List<AccountReportDTO> listDateOne1 = new ArrayList<>();
                for (int j = 0; j < dateListStringt.size() - 1; j++) {
                    int is = 0;
                    if (im != 1) {
                        is = (numbert + index) - (im - 1);
                    } else {
                        is = (numbert + index);
                    }
                    index++;

                    listDateOne1.add(listOne3.get(j));

                    if (index == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                        //获取数据
                        Map<String, List<AccountReportDTO>> responseMapOne4 = null;
                        try {
                            responseMapOne4 = getUserDataPro(listDateOne1, dateFormat.parse(dateListStringt.get(numbert)), dateFormat.parse(dateListStringt.get(is)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        if (devices == 0) {
                            Map<String, List<AccountReportDTO>> responseMapDevicesOne = getPcPlusMobileDate(responseMapOne4);
                            objectList3.putAll(responseMapDevicesOne);
                            objectListDateOne31.add(dateListStringt.get(numbert) + " 至 " + dateListStringt.get(is));

                        } else {
                            objectList3.putAll(responseMapOne4);
                            objectListDateOne31.add(dateListStringt.get(numbert) + " 至 " + dateListStringt.get(is));

                        }
                        if (im != 1) {
                            numbert = (numbert + index) - (im - 1);
                            im = 1;
                        } else {
                            numbert = numbert + index;
                        }


                        index = 0;
                        listDateOne1 = new ArrayList<>();

                    }
                    try {
                        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dateListStringt.get(j)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (dateListStringt.size() == 1) {
                    listDateOne1.add(listOne3.get(0));
                }
                if (index > 0) {
                    //获取数据
                    Map<String, List<AccountReportDTO>> responseMapOne4 = null;
                    try {
                        responseMapOne4 = getUserDataPro(listDateOne1, dateFormat.parse(dateListStringt.get(numbert)), dateFormat.parse(dateListStringt.get((numbert + index) - (im - 1))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    if (devices == 0) {
                        Map<String, List<AccountReportDTO>> responseMapDevicesOne = getPcPlusMobileDate(responseMapOne4);
                        objectList3.putAll(responseMapDevicesOne);

                    } else {
                        objectList3.putAll(responseMapOne4);
                    }
                }

                if (devices == 0) {
                    try {
                        os.write(Bytes.concat(commonCSVHead, ("时间" +
                                DEFAULT_DELIMITER + "展现量" +
                                DEFAULT_DELIMITER + "点击量" +
                                DEFAULT_DELIMITER + "消费" +
                                DEFAULT_DELIMITER + "点击率" +
                                DEFAULT_DELIMITER + "平均点击价格" +
                                DEFAULT_DELIMITER + "转化(页面)" +
                                DEFAULT_END).getBytes(StandardCharsets.UTF_8)));

                        for (Map.Entry<String, List<AccountReportDTO>> voEntity : objectList3.entrySet()) {
                            for (AccountReportDTO entity : voEntity.getValue()) {
                                os.write(Bytes.concat(commonCSVHead, (voEntity.getKey() +
                                        DEFAULT_DELIMITER + entity.getPcImpression() +
                                        DEFAULT_DELIMITER + entity.getPcClick() +
                                        DEFAULT_DELIMITER + entity.getPcCost() +
                                        DEFAULT_DELIMITER + entity.getPcCtr() * 100 / 100 + "%" +
                                        DEFAULT_DELIMITER + entity.getPcCpc() +
                                        DEFAULT_DELIMITER + entity.getPcConversion() +
                                        DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (devices == 1) {
                        try {
                            os.write(Bytes.concat(commonCSVHead, ("时间" +
                                    DEFAULT_DELIMITER + "展现量" +
                                    DEFAULT_DELIMITER + "点击量" +
                                    DEFAULT_DELIMITER + "消费" +
                                    DEFAULT_DELIMITER + "点击率" +
                                    DEFAULT_DELIMITER + "平均点击价格" +
                                    DEFAULT_DELIMITER + "转化(页面)" +
                                    DEFAULT_END).getBytes(StandardCharsets.UTF_8)));

                            for (Map.Entry<String, List<AccountReportDTO>> voEntity : objectList3.entrySet()) {
                                for (AccountReportDTO entity : voEntity.getValue()) {
                                    os.write(Bytes.concat(commonCSVHead, (voEntity.getKey() +
                                            DEFAULT_DELIMITER + entity.getPcImpression() +
                                            DEFAULT_DELIMITER + entity.getPcClick() +
                                            DEFAULT_DELIMITER + entity.getPcCost() +
                                            DEFAULT_DELIMITER + entity.getPcCtr() * 100 / 100 + "%" +
                                            DEFAULT_DELIMITER + entity.getPcCpc() +
                                            DEFAULT_DELIMITER + entity.getPcConversion() +
                                            DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            os.write(Bytes.concat(commonCSVHead, ("时间" +
                                    DEFAULT_DELIMITER + "展现量" +
                                    DEFAULT_DELIMITER + "点击量" +
                                    DEFAULT_DELIMITER + "消费" +
                                    DEFAULT_DELIMITER + "点击率" +
                                    DEFAULT_DELIMITER + "平均点击价格" +
                                    DEFAULT_DELIMITER + "转化(页面)" +
                                    DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                            for (Map.Entry<String, List<AccountReportDTO>> voEntity : objectList3.entrySet()) {
                                for (AccountReportDTO entity : voEntity.getValue()) {
                                    os.write(Bytes.concat(commonCSVHead, (voEntity.getKey() +
                                            DEFAULT_DELIMITER + entity.getMobileImpression() +
                                            DEFAULT_DELIMITER + entity.getMobileClick() +
                                            DEFAULT_DELIMITER + entity.getMobileCost() +
                                            DEFAULT_DELIMITER + entity.getMobileCtr() * 100 / 100 + "%" +
                                            DEFAULT_DELIMITER + entity.getMobileCpc() +
                                            DEFAULT_DELIMITER + entity.getMobileConversion() +
                                            DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
    }

    /**
     * ****************API****************************
     */
    @Override
    public Map<String, List<StructureReportDTO>> getKeywordReport(Long[] id, String startDate, String endDate, int devices) {
        String userName = accountManageService.getBaiduAccountInfoById(AppContext.getAccountId()).getBaiduUserName();
        List<String> newDate = DateUtils.getPeriod(startDate, endDate);
        Map<String, List<StructureReportDTO>> listMap = new HashMap<>();
        if (newDate.size() > 0) {
            for (int i = 0; i < newDate.size(); i++) {
                List<StructureReportDTO> returnStructure = basisReportDAO.getKeywordReport(id, newDate.get(i) + "-keyword");
                if (devices == 0) {
                    ForkJoinPool joinPoolTow = new ForkJoinPool();
                    //开始对数据进行处理
                    Future<List<StructureReportDTO>> joinTask = joinPoolTow.submit(new BasistReportPCPlusMobUtil(returnStructure, 0, returnStructure.size(), userName));
                    try {
                        listMap.put(newDate.get(i), joinTask.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    listMap.put(newDate.get(i), returnStructure);
                }
            }
        }
        return listMap;
    }


    /**
     * 账户 计算所有数据  包含PC端 、 移动端
     *
     * @param responseMap
     * @return
     */
    public Map<String, List<AccountReportDTO>> getPcPlusMobileDate(Map<String, List<AccountReportDTO>> responseMap) {
        Map<String, List<AccountReportDTO>> respons = new HashMap<>();


        DecimalFormat df = new DecimalFormat("#.0000");
        for (Map.Entry<String, List<AccountReportDTO>> voEntity : responseMap.entrySet()) {
            List<AccountReportDTO> list = new ArrayList<>();
            AccountReportDTO dto = new AccountReportDTO();
            for (AccountReportDTO response : voEntity.getValue()) {
                dto.setPcImpression(response.getPcImpression() + ((response.getMobileImpression() == null) ? 0 : response.getMobileImpression()));
                dto.setPcConversion(response.getPcConversion() + ((response.getMobileConversion() == null) ? 0 : response.getMobileConversion()));
                dto.setPcClick(response.getPcClick() + ((response.getMobileClick() == null) ? 0 : response.getMobileClick()));
                dto.setPcCost(response.getPcCost().add((response.getMobileCost() == null) ? BigDecimal.valueOf(0) : response.getMobileCost()));
                //计算点击率
                if (((response.getPcImpression() == null) ? 0 : response.getPcImpression()) == 0) {
                    dto.setPcCtr(0.00);
                } else {
                    BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format((response.getPcClick().doubleValue() / response.getPcImpression().doubleValue()))));
                    BigDecimal big = new BigDecimal(100);
                    double divide = ctrBig.multiply(big).doubleValue();
                    dto.setPcCtr(divide);
                }

                //计算平均点击价格
                if (((response.getPcClick() == null) ? 0 : response.getPcClick()) == 0) {
                    dto.setPcCpc(BigDecimal.valueOf(0));
                } else {
                    dto.setPcCpc(response.getPcCost().divide(BigDecimal.valueOf(response.getPcClick()), 2, BigDecimal.ROUND_UP));
                }

                dto.setMobileImpression(0);
                dto.setMobileClick(0);
                dto.setMobileConversion(0.00);
                dto.setMobileCost(BigDecimal.ZERO);
                dto.setMobileCpc(BigDecimal.ZERO);
                dto.setMobileCpm(BigDecimal.ZERO);
                dto.setMobileCtr(0.00);
                list.add(dto);
            }
            respons.put(voEntity.getKey(), list);
        }
        return respons;
    }

    /**
     * 账户计算点击率 平均价格等
     */
    private Map<String, List<AccountReportDTO>> getAverage(Map<String, List<AccountReportDTO>> responseMap) {
        DecimalFormat df = new DecimalFormat("#.0000");
        for (Map.Entry<String, List<AccountReportDTO>> voEntity : responseMap.entrySet()) {
            for (AccountReportDTO response : voEntity.getValue()) {
                if (response.getMobileImpression() == null || response.getMobileImpression() == 0) {
                    response.setMobileCtr(0.00);
                } else {
                    BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format(response.getMobileClick().doubleValue() / response.getMobileImpression().doubleValue())));
                    BigDecimal big = new BigDecimal(100);
                    double divide = ctrBig.multiply(big).doubleValue();
                    response.setMobileCtr(divide);
                }
                if (response.getMobileClick() == null || response.getMobileClick() == 0) {
                    response.setMobileCpc(BigDecimal.valueOf(0.00));
                } else {
                    response.setMobileCpc(response.getMobileCost().divide(BigDecimal.valueOf(response.getMobileClick()), 2, BigDecimal.ROUND_UP));
                }

                if (response.getPcImpression() == null || response.getPcImpression() == 0) {
                    response.setPcCtr(0.00);
                } else {
                    BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format(response.getPcClick().doubleValue() / response.getPcImpression().doubleValue())));
                    BigDecimal big = new BigDecimal(10000);
                    double divide = ctrBig.multiply(big).doubleValue();
                    response.setPcCtr(divide / 100);
                }
                if (response.getPcClick() == null || response.getPcClick() == 0) {
                    response.setPcCpc(BigDecimal.valueOf(0.00));
                } else {
                    response.setPcCpc(response.getPcCost().divide(BigDecimal.valueOf(response.getPcClick()), 2, BigDecimal.ROUND_UP));
                }
            }
        }
        return responseMap;
    }

    /**
     * 账户对用户数据进行处理
     *
     * @param responses 用户List 数据
     * @param date1     数据开始时间
     * @param date2     数据结束时间
     * @return
     */
    private Map<String, List<AccountReportDTO>> getUserDataPro(List<AccountReportDTO> responses, Date date1, Date date2) {
        Map<String, List<AccountReportDTO>> responseMap = new HashMap<>();
        List<AccountReportDTO> responseList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (AccountReportDTO enit : responses) {
            if (responseList.size() > 0) {
                responseList.get(0).setPcImpression(responseList.get(0).getPcImpression() + ((enit.getPcImpression() == null) ? 0 : enit.getPcImpression()));
                responseList.get(0).setPcClick(responseList.get(0).getPcClick() + ((enit.getPcClick() == null) ? 0 : enit.getPcClick()));
                responseList.get(0).setPcCost(responseList.get(0).getPcCost().add((enit.getPcCost() == null) ? BigDecimal.valueOf(0) : enit.getPcCost()));
                responseList.get(0).setPcConversion(responseList.get(0).getPcConversion() + ((enit.getPcConversion() == null) ? 0 : enit.getPcConversion()));
                responseList.get(0).setPcCtr(0d);
                responseList.get(0).setPcCpc(BigDecimal.valueOf(0));
                responseList.get(0).setMobileImpression(((responseList.get(0).getMobileImpression() == null) ? 0 : responseList.get(0).getMobileImpression()) + ((enit.getMobileImpression() == null) ? 0 : enit.getMobileImpression()));
                responseList.get(0).setMobileClick(((responseList.get(0).getMobileClick() == null) ? 0 : responseList.get(0).getMobileClick()) + ((enit.getMobileClick() == null) ? 0 : enit.getMobileClick()));
                responseList.get(0).setMobileCost(((responseList.get(0).getMobileCost() == null) ? BigDecimal.valueOf(0) : responseList.get(0).getMobileCost()).add((enit.getMobileCost() == null) ? BigDecimal.valueOf(0) : enit.getMobileCost()));
                responseList.get(0).setMobileConversion(((responseList.get(0).getMobileConversion() == null) ? 0 : responseList.get(0).getMobileConversion()) + ((enit.getMobileConversion() == null) ? 0 : enit.getMobileConversion()));
                responseList.get(0).setMobileCtr(0d);
                responseList.get(0).setMobileCpc(BigDecimal.valueOf(0));
            } else {
                enit.setDateRep(dateFormat.format(enit.getDate()));
                responseList.add(enit);
            }
        }
        responseMap.put(dateFormat.format(date1) + " 至 " + dateFormat.format(date2), responseList);
        return responseMap;
    }

    /**
     * 账户时间处理
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private Date[] getDateProcessing(Date startDate, Date endDate) {
        Date dateOne = null;
        Date dateTow = null;
        if (startDate == null && endDate != null) {
            dateOne = endDate;
        }
        if (endDate == null && startDate != null) {
            dateTow = startDate;
        }
        if (startDate == null && endDate == null) {
            dateOne = new Date();
            dateTow = new Date();
        } else {
            dateOne = startDate;
            dateTow = endDate;
        }
        Date[] dates = {dateOne, dateTow};
        return dates;
    }

    /**
     * 对全部终端数据进行处理
     *
     * @param entitiesMap
     * @return
     */
    private Map<String, List<StructureReportDTO>> terminalAll(Map<String, List<StructureReportDTO>> entitiesMap, int reportType) {
        String userName = accountManageService.getBaiduAccountInfoById(AppContext.getAccountId()).getBaiduUserName();
        List<StructureReportDTO> entities = new ArrayList<>();
        Map<String, List<StructureReportDTO>> listMapDay = new HashMap<>();
        for (Iterator<Map.Entry<String, List<StructureReportDTO>>> entry1 = entitiesMap.entrySet().iterator(); entry1.hasNext(); ) {
            //创建一个并行计算框架
            ForkJoinPool joinPoolTow = new ForkJoinPool();
            Map.Entry<String, List<StructureReportDTO>> mapData = entry1.next();

            //获取map中的value
            List<StructureReportDTO> list1 = mapData.getValue();
            //获取map中的key
            String mapKey = mapData.getKey();
            //开始对数据进行处理
            Future<List<StructureReportDTO>> joinTask = joinPoolTow.submit(new BasistReportPCPlusMobUtil(list1, 0, list1.size(), userName));
            try {
                //得到处理后的数据
                entities = joinTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            Map<String, StructureReportDTO> objects = new HashMap<>();

            //开始对数据进行处理
            Future<Map<String, StructureReportDTO>> joinTasks = joinPoolTow.submit(new BasisReportPCus(entities, 0, entities.size(), reportType));
            try {
                //得到处理后的数据
                objects = joinTasks.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            List<StructureReportDTO> entities1 = new ArrayList<>();
            for (Iterator<Map.Entry<String, StructureReportDTO>> entry2 = objects.entrySet().iterator(); entry2.hasNext(); ) {
                entities1.add(entry2.next().getValue());
            }
            //关闭并行计算框架
            joinPoolTow.shutdown();
            listMapDay.put(mapKey, entities1);
        }
        return listMapDay;
    }

    /**
     * 获取表结尾单词
     *
     * @param reportType
     * @return
     */
    public String getTableType(int reportType) {
        String reportName = "";
        switch (reportType) {
            case 0:
                //计划表结尾
                reportName = "-campaign";
                break;
            case 1:
                //单元表结尾
                reportName = "-adgroup";
                break;
            case 2:
                //创意表结尾
                reportName = "-keyword";
                break;
            case 3:
                //关键字表结尾
                reportName = "-creative";
                break;
            case 4:
                //地域表结尾
                reportName = "-region";
                break;
            case 5:
                //地域表结尾
                reportName = "-region";
                break;
            case 6:
                //地域表结尾
                reportName = "-region";
                break;
            case 7:
                //地域表结尾
                reportName = "-keyword";
                break;
        }

        return reportName;
    }

    //对单个的数据进行计算百分比
    private Map<String, StructureReportDTO> percentage(Map<String, StructureReportDTO> map) {
        DecimalFormat df = new DecimalFormat("#.00");
        for (Map.Entry<String, StructureReportDTO> voEntity : map.entrySet()) {
            if (voEntity.getValue().getMobileImpression() == null || voEntity.getValue().getMobileImpression() == 0) {
                voEntity.getValue().setMobileCtr(0.00);
            } else {
                BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format(voEntity.getValue().getMobileClick().doubleValue() / voEntity.getValue().getMobileImpression().doubleValue())));
                BigDecimal big = new BigDecimal(100);
                double divide = ctrBig.multiply(big).doubleValue();
                voEntity.getValue().setMobileCtr(divide);
            }
            if (voEntity.getValue().getMobileClick() == null || voEntity.getValue().getMobileClick() == 0) {
                voEntity.getValue().setMobileCpc(BigDecimal.valueOf(0.00));
            } else {
                voEntity.getValue().setMobileCpc(voEntity.getValue().getMobileCost().divide(BigDecimal.valueOf(voEntity.getValue().getMobileClick()), 3, BigDecimal.ROUND_UP));
            }

            if (voEntity.getValue().getPcImpression() == null || voEntity.getValue().getPcImpression() == 0) {
                voEntity.getValue().setPcCtr(0.00);
            } else {
                double ctrAve = voEntity.getValue().getPcClick().doubleValue() / voEntity.getValue().getPcImpression();
                double divide = ctrAve * 10000;
                voEntity.getValue().setPcCtr(divide / 100);
            }
            if (voEntity.getValue().getPcClick() == null || voEntity.getValue().getPcClick() == 0) {
                voEntity.getValue().setPcCpc(BigDecimal.valueOf(0.00));
            } else {
                voEntity.getValue().setPcCpc(voEntity.getValue().getPcCost().divide(BigDecimal.valueOf(voEntity.getValue().getPcClick().doubleValue()), 2, BigDecimal.ROUND_UP));
            }
        }
        return map;
    }

    //对一个集合中的数据进行计算百分比
    private Map<String, List<StructureReportDTO>> percentageList(Map<String, List<StructureReportDTO>> mapDay, String userName, int reportType) {
        DecimalFormat df = new DecimalFormat("#.0000");
        Map<String, List<StructureReportDTO>> stringListMap = new HashMap<>();
        for (Map.Entry<String, List<StructureReportDTO>> voEntity : mapDay.entrySet()) {
            for (StructureReportDTO entity : voEntity.getValue()) {
                entity.setAccount(userName);
                if (entity.getMobileImpression() == null || entity.getMobileImpression() == 0) {
                    entity.setMobileCtr(0.00);
                } else {
                    BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format(entity.getMobileClick().doubleValue() / entity.getMobileImpression().doubleValue())));
                    BigDecimal big = new BigDecimal(100);
                    double divide = ctrBig.multiply(big).doubleValue();
                    entity.setMobileCtr(divide);
                }
                if (entity.getMobileClick() == null || entity.getMobileClick() == 0) {
                    entity.setMobileCpc(BigDecimal.valueOf(0.00));
                } else {
                    entity.setMobileCpc(entity.getMobileCost().divide(BigDecimal.valueOf(entity.getMobileClick()), 2, BigDecimal.ROUND_UP));
                }

                if (entity.getPcImpression() == null || entity.getPcImpression() == 0) {
                    entity.setPcCtr(0.00);
                } else {
                    BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format(entity.getPcClick().doubleValue() / entity.getPcImpression().doubleValue())));
                    BigDecimal big = new BigDecimal(100);
                    double divide = ctrBig.multiply(big).doubleValue();
                    entity.setPcCtr(divide);
                }
                if (entity.getPcClick() == null || entity.getPcClick() == 0) {
                    entity.setPcCpc(BigDecimal.valueOf(0.00));
                } else {
                    entity.setPcCpc(entity.getPcCost().divide(BigDecimal.valueOf(entity.getPcClick()), 2, BigDecimal.ROUND_UP));
                }
            }

        }
        for (Map.Entry<String, List<StructureReportDTO>> voEntity : mapDay.entrySet()) {
            Map<String, StructureReportDTO> objects = new HashMap<>();
            ForkJoinPool joinPoolTow = new ForkJoinPool();
            //开始对数据进行处理
            Future<Map<String, StructureReportDTO>> joinTasks = joinPoolTow.submit(new BasisReportPCus(voEntity.getValue(), 0, voEntity.getValue().size(), reportType));
            try {
                //得到处理后的数据
                objects = joinTasks.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            List<StructureReportDTO> entities1 = new ArrayList<>();
            for (Iterator<Map.Entry<String, StructureReportDTO>> entry2 = objects.entrySet().iterator(); entry2.hasNext(); ) {
                entities1.add(entry2.next().getValue());
            }
            stringListMap.put(voEntity.getKey(), entities1);
        }


        return stringListMap;
    }

    //统计当前查出数据的总和（分标示）
    private List<StructureReportDTO> getCountStructure(Map<String, List<StructureReportDTO>> dateMap) {
        StructureReportDTO objEntity = new StructureReportDTO();
        for (Map.Entry<String, List<StructureReportDTO>> voEntity : dateMap.entrySet()) {
            for (StructureReportDTO entity : voEntity.getValue()) {
                objEntity.setMobileImpression(((objEntity.getMobileImpression() == null) ? 0 : objEntity.getMobileImpression()) + ((entity.getMobileImpression() == null) ? 0 : entity.getMobileImpression()));
                objEntity.setMobileClick(((objEntity.getMobileClick() == null) ? 0 : objEntity.getMobileClick()) + ((entity.getMobileClick() == null) ? 0 : entity.getMobileClick()));
                objEntity.setMobileCost(((objEntity.getMobileCost() == null) ? BigDecimal.valueOf(0) : objEntity.getMobileCost()).add((entity.getMobileCost() == null) ? BigDecimal.valueOf(0) : entity.getMobileCost()));
                objEntity.setMobileConversion(((objEntity.getMobileConversion() == null) ? 0 : objEntity.getMobileConversion()) + ((entity.getMobileConversion() == null) ? 0 : entity.getMobileConversion()));

                objEntity.setPcImpression(((objEntity.getPcImpression() == null) ? 0 : objEntity.getPcImpression()) + ((entity.getPcImpression() == null) ? 0 : entity.getPcImpression()));
                objEntity.setPcClick(((objEntity.getPcClick() == null) ? 0 : objEntity.getPcClick()) + ((entity.getPcClick() == null) ? 0 : entity.getPcClick()));
                objEntity.setPcCost(((objEntity.getPcCost() == null) ? BigDecimal.valueOf(0) : objEntity.getPcCost()).add((entity.getPcCost() == null) ? BigDecimal.valueOf(0) : entity.getPcCost()));
                objEntity.setPcConversion(((objEntity.getPcConversion() == null) ? 0 : objEntity.getPcConversion()) + ((entity.getPcConversion() == null) ? 0 : entity.getPcConversion()));
            }
        }
        List<StructureReportDTO> entityList = new ArrayList<>();
        entityList.add(objEntity);
        return entityList;
    }

    //获取饼状图需要数据
    private List<StructureReportDTO> getPieData(List<StructureReportDTO> returnList, int terminal, String sortField, String sort) {
        for (StructureReportDTO entity : returnList) {
            entity.setOrderBy(sort);
            entity.setTerminal(terminal);
        }
        Collections.sort(returnList);
        int i = 0;
        List<StructureReportDTO> entityList = new ArrayList<>();
        StructureReportDTO entity = new StructureReportDTO();
        for (StructureReportDTO entityReport : returnList) {
            StructureReportDTO entity1 = new StructureReportDTO();
            if (i < 10) {
                i++;
                switch (sortField) {
                    case REPORT_IMPR:
                        //如果用户只查询手机
                        if (terminal == 2) {
                            entity1.setMobileImpression(entityReport.getMobileImpression());
                            entity1.setRegionName(entityReport.getRegionName());
                            entityList.add(entity1);
                        } else {
                            entity1.setPcImpression(entityReport.getPcImpression());
                            entity1.setRegionName(entityReport.getRegionName());
                            entityList.add(entity1);
                        }
                        break;
                    case REPORT_CLICK:
                        if (terminal == 2) {
                            entity1.setMobileClick(entityReport.getMobileClick());
                            entity1.setRegionName(entityReport.getRegionName());
                            entityList.add(entity1);
                        } else {
                            entity1.setPcClick(entityReport.getPcClick());
                            entity1.setRegionName(entityReport.getRegionName());
                            entityList.add(entity1);
                        }
                        break;
                    case REPORT_COST:
                        if (terminal == 2) {
                            entity1.setMobileCost(entityReport.getMobileCost());
                            entity1.setRegionName(entityReport.getRegionName());
                            entityList.add(entity1);
                        } else {
                            entity1.setPcCost(entityReport.getPcCost());
                            entity1.setRegionName(entityReport.getRegionName());
                            entityList.add(entity1);
                        }
                        break;
                    case REPORT_CONV:
                        if (terminal == 2) {
                            entity1.setMobileConversion(entityReport.getMobileConversion());
                            entity1.setRegionName(entityReport.getRegionName());
                            entityList.add(entity1);
                        } else {
                            entity1.setPcConversion(entityReport.getPcConversion());
                            entity1.setRegionName(entityReport.getRegionName());
                            entityList.add(entity1);
                        }
                }
            } else {
                switch (sortField) {
                    case REPORT_IMPR:
                        //如果用户只查询手机
                        if (terminal == 2) {
                            entity.setMobileImpression(((entity.getMobileImpression() == null) ? 0 : entity.getMobileImpression()) + entityReport.getMobileImpression());
                        } else {
                            entity.setPcImpression(((entity.getPcImpression() == null) ? 0 : entity.getPcImpression()) + entityReport.getPcImpression());
                        }
                        break;
                    case REPORT_CLICK:
                        if (terminal == 2) {
                            entity.setMobileClick(((entity.getMobileClick() == null) ? 0 : entity.getMobileClick()) + entityReport.getMobileClick());
                        } else {
                            entity.setPcClick(((entity.getPcClick() == null) ? 0 : entity.getPcClick()) + entityReport.getPcClick());
                        }
                        break;
                    case REPORT_COST:
                        if (terminal == 2) {
                            entity.setMobileCost(((entity.getMobileCost() == null) ? BigDecimal.valueOf(0) : entity.getMobileCost()).add(entityReport.getMobileCost()));
                        } else {
                            entity.setPcCost(((entity.getPcCost() == null) ? BigDecimal.valueOf(0) : entity.getPcCost()).add(entityReport.getPcCost()));
                        }
                        break;
                    case REPORT_CONV:
                        if (terminal == 2) {
                            entity.setMobileConversion(((entity.getMobileConversion() == null) ? 0 : entity.getMobileConversion()) + entityReport.getMobileConversion());
                        } else {
                            entity.setPcConversion(((entity.getPcImpression() == null) ? 0 : entity.getPcConversion()) + entityReport.getPcConversion());
                        }
                }
            }
        }
        if (entity != null) {
            entity.setRegionName("其他");
            entityList.add(entity);
        }
        return entityList;
    }

    //数据排序
    private List<StructureReportDTO> dataSort(List<StructureReportDTO> returnList, String sort, int terminal, int start, int limit) {
        for (StructureReportDTO entity : returnList) {
            entity.setOrderBy(sort);
            entity.setTerminal(terminal);
        }
        List<StructureReportDTO> finalList = new ArrayList<>();

        if (returnList.size() > limit) {
            for (int i = start; i < limit; i++) {
                finalList.add(returnList.get(i));
            }
            Collections.sort(finalList);
            return finalList;
        } else {
            Collections.sort(returnList);
            return returnList;
        }
    }

    //曲线图数据计算
    private List<StructureReportDTO> getLineChart(Map<String, List<StructureReportDTO>> listMap, int dive) {
        List<StructureReportDTO> entityList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Map.Entry<String, List<StructureReportDTO>> voEntity : listMap.entrySet()) {
            StructureReportDTO e = new StructureReportDTO();
            List<StructureReportDTO> entity = voEntity.getValue();

            for (StructureReportDTO entity1 : entity) {
                e.setPcImpression(((e.getPcImpression() == null) ? 0 : e.getPcImpression()) + ((entity1.getPcImpression() == null) ? 0 : entity1.getPcImpression()));
                e.setPcClick(((e.getPcClick() == null) ? 0 : e.getPcClick()) + ((entity1.getPcClick() == null) ? 0 : entity1.getPcClick()));
                e.setPcCost(((e.getPcCost() == null) ? BigDecimal.valueOf(0) : e.getPcCost()).add((entity1.getPcCost() == null) ? BigDecimal.valueOf(0) : entity1.getPcCost()));
                e.setPcConversion(((e.getPcConversion() == null) ? 0 : e.getPcConversion()) + ((entity1.getPcConversion() == null) ? 0 : entity1.getPcConversion()));

                e.setMobileImpression(((e.getMobileImpression() == null) ? 0 : e.getMobileImpression()) + ((entity1.getMobileImpression() == null) ? 0 : entity1.getMobileImpression()));
                e.setMobileClick(((e.getMobileClick() == null) ? 0 : e.getMobileClick()) + ((entity1.getMobileClick() == null) ? 0 : entity1.getMobileClick()));
                e.setMobileCost(((e.getMobileCost() == null) ? BigDecimal.valueOf(0) : e.getMobileCost()).add((entity1.getMobileCost() == null) ? BigDecimal.valueOf(0) : entity1.getMobileCost()));
                e.setMobileConversion(((e.getMobileConversion() == null) ? 0 : e.getMobileConversion()) + ((entity1.getMobileConversion() == null) ? 0 : entity1.getMobileConversion()));
            }
            //----
            if (e.getPcClick() == null || e.getPcClick() == 0) {
                e.setPcCpc(BigDecimal.valueOf(0.00));
            } else {
                e.setPcCpc(e.getPcCost().divide(BigDecimal.valueOf(e.getPcClick()), 2, BigDecimal.ROUND_UP));
            }
            //----
            if (e.getPcImpression() == null || e.getPcImpression() == 0) {
                e.setPcCtr(0.00);
            } else {
                e.setPcCtr(e.getPcClick().doubleValue() / e.getPcImpression().doubleValue());
            }
            //---
            if (e.getMobileClick() == null || e.getMobileClick() == 0) {
                e.setMobileCpc(BigDecimal.valueOf(0.00));
            } else {
                e.setMobileCpc(e.getMobileCost().divide(BigDecimal.valueOf(e.getMobileClick()), 2, BigDecimal.ROUND_UP));
            }
            //---
            if (e.getMobileImpression() == null || e.getMobileImpression() == 0) {
                e.setMobileCtr(0.00);
            } else {
                e.setMobileCtr(e.getMobileClick().doubleValue() / e.getMobileImpression().doubleValue());
            }
            e.setDate(voEntity.getKey());
            e.setTerminal(dive);
            e.setOrderBy("11");
            try {
                e.setDateRep(dateFormat.parse(voEntity.getKey().substring(0, 10)));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            entityList.add(e);
        }
        Collections.sort(entityList);
        return entityList;
    }

    /**
     * 计算合计数据
     *
     * @param list
     * @return
     */
    private List<StructureReportDTO> dataALL(List<StructureReportDTO> list) {
        ForkJoinPool joinPool = new ForkJoinPool();
        Map<String, StructureReportDTO> mapAll = new HashMap<>();
        try {

            Future<Map<String, StructureReportDTO>> joinTaskAll = joinPool.submit(new ReportStatisticsUtil(list, 0, list.size()));

            mapAll = percentage(joinTaskAll.get());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        List<StructureReportDTO> returnListAll = new ArrayList<>(mapAll.values());
        return returnListAll;
    }

}
