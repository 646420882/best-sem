package com.perfect.service.impl;

import com.google.common.primitives.Bytes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.perfect.core.AppContext;
import com.perfect.dao.report.BasisReportDAO;
import com.perfect.dto.StructureReportDTO;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.service.AccountManageService;
import com.perfect.service.BasisReportService;
import com.perfect.utils.DateUtils;
import com.perfect.utils.redis.JRedisUtils;
import com.perfect.utils.report.*;
import org.springframework.stereotype.Service;
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
@Service("basisReportService")
public class BasisReportServiceImpl implements BasisReportService {
    @Resource
    private BasisReportDAO basisReportDAO;
    @Resource
    private AccountManageService accountManageService;

    private static int REDISKEYTIME = 1 * 60 * 60;

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
                if (jedisKey == -1 || jedisKey == -2) {
                    List<StructureReportDTO> dateMap0 = new ArrayList<>();
                    StructureReportDTO dateObject0 = new StructureReportDTO();
                    //初始化容器
                    Map<String, StructureReportDTO> map;
                    List<StructureReportDTO> returnList = new ArrayList<>();
                    Map<String, List<StructureReportDTO>> listMap = new HashMap<>();
                    List<StructureReportDTO> objectsList = new ArrayList<>();
                    //获取需要的数据
                    for (int i = 0; i < date.length; i++) {
                        List<StructureReportDTO> object = new ArrayList<>(basisReportDAO.getUnitReportDate(date[i] + BasisReportCalculateUtil.getTableType(reportType), dataId, dateName));
                        if (object.size() != 0) {
                            objectsList.addAll(object);
                        }
                    }
                    ForkJoinPool joinPool = new ForkJoinPool();

                    //计算合计数据
                    List<StructureReportDTO> returnListAll = BasisReportCalculateUtil.dataALL(objectsList);

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
                        map1 = BasisReportCalculateUtil.percentage(map);
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
                            List<StructureReportDTO> pieIpmr = BasisReportCalculateUtil.getPieData(returnList, terminal, REPORT_IMPR, REPORT_FUYI);
                            //计算饼状图 点击数据
                            List<StructureReportDTO> pieClick = BasisReportCalculateUtil.getPieData(returnList, terminal, REPORT_CLICK, REPORT_FUER);
                            //计算饼状图 消费数据
                            List<StructureReportDTO> pieCost = BasisReportCalculateUtil.getPieData(returnList, terminal, REPORT_COST, REPORT_FUSAN);
                            //计算饼状图 转化数据
                            List<StructureReportDTO> pieConv = BasisReportCalculateUtil.getPieData(returnList, terminal, REPORT_CONV, REPORT_FULIU);

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
                        List<StructureReportDTO> entityList1 = BasisReportCalculateUtil.getCountStructure(listMap);
                        listMap.put(REPORT_STATISTICS, returnListAll);
                        listMap.put(REPORT_COUNTDATA, entityList1);
                        listMap.put(REPORT_ROWS, pageReport);
                        if (jc != null) {
                            JRedisUtils.returnJedis(jc);
                        }
                        return listMap;
                    } else {
                        if (reportType == 4) {
                            //计算饼状图 展现数据
                            List<StructureReportDTO> pieIpmr = BasisReportCalculateUtil.getPieData(list, terminal, REPORT_IMPR, REPORT_FUYI);
                            //计算饼状图 点击数据
                            List<StructureReportDTO> pieClick = BasisReportCalculateUtil.getPieData(list, terminal, REPORT_CLICK, REPORT_FUER);
                            //计算饼状图 消费数据
                            List<StructureReportDTO> pieCost = BasisReportCalculateUtil.getPieData(list, terminal, REPORT_COST, REPORT_FUSAN);
                            //计算饼状图 转化数据
                            List<StructureReportDTO> pieConv = BasisReportCalculateUtil.getPieData(list, terminal, REPORT_CONV, REPORT_FULIU);

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
                        List<StructureReportDTO> entityList1 = BasisReportCalculateUtil.getCountStructure(listMap);
                        listMap.put(REPORT_STATISTICS, returnListAll);
                        listMap.put(REPORT_COUNTDATA, entityList1);
                        listMap.put(REPORT_ROWS, pageReport);
                        if (jc != null) {
                            JRedisUtils.returnJedis(jc);
                        }
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
                        List<StructureReportDTO> pieIpmr = BasisReportCalculateUtil.getPieData(list, terminal, REPORT_IMPR, REPORT_FUYI);
                        //计算饼状图 点击数据
                        List<StructureReportDTO> pieClick = BasisReportCalculateUtil.getPieData(list, terminal, REPORT_CLICK, REPORT_FUER);
                        //计算饼状图 消费数据
                        List<StructureReportDTO> pieCost = BasisReportCalculateUtil.getPieData(list, terminal, REPORT_COST, REPORT_FUSAN);
                        //计算饼状图 转化数据
                        List<StructureReportDTO> pieConv = BasisReportCalculateUtil.getPieData(list, terminal, REPORT_CONV, REPORT_FULIU);

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
                    List<StructureReportDTO> entityList1 = BasisReportCalculateUtil.getCountStructure(listMap);
                    listMap.put(REPORT_STATISTICS, listAll);
                    listMap.put(REPORT_COUNTDATA, entityList1);
                    listMap.put(REPORT_ROWS, pageReport);
                    if (jc != null) {
                        JRedisUtils.returnJedis(jc);
                    }
                    return listMap;
                }
                //分日生成报告
            case 1:
                Jedis jc1 = JRedisUtils.get();

                Long jedisKey1 = jc1.ttl(date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId);
                if (jedisKey1 == -1 || jedisKey1 == -2) {
                    List<StructureReportDTO> dateMap = new ArrayList<>();

                    //初始化容器
                    Map<String, List<StructureReportDTO>> mapDay = new HashMap<>();
                    //获得需要的数据
                    for (int i = 0; i < date.length; i++) {
                        StructureReportDTO dateObject = new StructureReportDTO();
                        List<StructureReportDTO> object = new ArrayList<>(basisReportDAO.getUnitReportDate(date[i] + BasisReportCalculateUtil.getTableType(reportType), dataId, dateName));
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
                    List<StructureReportDTO> returnListAll = BasisReportCalculateUtil.dataALL(entityListAll);


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


                        List<StructureReportDTO> entityList1 = BasisReportCalculateUtil.getCountStructure(listMap1);
                        //曲线图数据计算
                        List<StructureReportDTO> lineChart = BasisReportCalculateUtil.getLineChart(listMap1, terminal);
                        ReportPageDetails pageDetails = new ReportPageDetails();
                        List<StructureReportDTO> pageReport = pageDetails.getReportDetailsPage(listMap1, terminal, sort, start, limit, categoryTime);
                        listMap1.put(REPORT_STATISTICS, returnListAll);
                        listMap1.put(REPORT_COUNTDATA, entityList1);
                        listMap1.put(REPORT_CHART, lineChart);
                        listMap1.put(REPORT_ROWS, pageReport);
                        if (jc1 != null) {
                            JRedisUtils.returnJedis(jc1);
                        }
                        return listMap1;
                    } else {
                        //对相应的数据进行计算百分比
                        mapDay1 = BasisReportCalculateUtil.percentageList(mapDay, userName, reportType);
                    }
                    //数据放入redis中
                    String lists = new Gson().toJson(mapDay1);
                    String listsALL = new Gson().toJson(returnListAll);
                    jc1.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), lists);
                    jc1.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                    jc1.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), listsALL);
                    jc1.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);

                    List<StructureReportDTO> entityList1 = BasisReportCalculateUtil.getCountStructure(mapDay1);

                    //曲线图数据计算
                    List<StructureReportDTO> lineChart = BasisReportCalculateUtil.getLineChart(mapDay1, terminal);
                    ReportPageDetails pageDetails = new ReportPageDetails();
                    List<StructureReportDTO> pageReport = pageDetails.getReportDetailsPage(mapDay1, terminal, sort, start, limit, categoryTime);
                    mapDay.put(REPORT_STATISTICS, returnListAll);
                    mapDay.put(REPORT_CHART, lineChart);
                    mapDay.put(REPORT_COUNTDATA, entityList1);
                    mapDay.put(REPORT_ROWS, pageReport);
                    if (jc1 != null) {
                        JRedisUtils.returnJedis(jc1);
                    }
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

                    List<StructureReportDTO> entityList1 = BasisReportCalculateUtil.getCountStructure(list);
                    //曲线图数据计算
                    List<StructureReportDTO> lineChart = BasisReportCalculateUtil.getLineChart(list, terminal);
                    ReportPageDetails pageDetails = new ReportPageDetails();
                    List<StructureReportDTO> pageReport = pageDetails.getReportDetailsPage(list, terminal, sort, start, limit, categoryTime);
                    listMap1.put(REPORT_STATISTICS, listAll);
                    listMap1.put(REPORT_COUNTDATA, entityList1);
                    listMap1.put(REPORT_CHART, lineChart);
                    listMap1.put(REPORT_ROWS, pageReport);
                    if (jc1 != null) {
                        JRedisUtils.returnJedis(jc1);
                    }
                    return listMap1;
                }

                //分周生成报告
            case 2:
                Jedis jc2 = JRedisUtils.get();

                Long jedisKey2 = jc2.ttl(date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId);
                if (jedisKey2 == -1 || jedisKey2 == -2) {
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
                                List<StructureReportDTO> object = new ArrayList<>(basisReportDAO.getUnitReportDate(date[i] + BasisReportCalculateUtil.getTableType(reportType), dataId, dateName));
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
                        List<StructureReportDTO> returnListAll = BasisReportCalculateUtil.dataALL(entityListAll);

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
                            Map<String, StructureReportDTO> reportEntities1 = BasisReportCalculateUtil.percentage(reportEntities);

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

                            List<StructureReportDTO> entityList2 = BasisReportCalculateUtil.getCountStructure(listMap1);
                            //曲线图数据计算
                            List<StructureReportDTO> lineChart1 = BasisReportCalculateUtil.getLineChart(listMap1, terminal);
                            ReportPageDetails pageDetails1 = new ReportPageDetails();
                            List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(listMap1, terminal, sort, start, limit, categoryTime);
                            listMap1.put(REPORT_STATISTICS, returnListAll);
                            listMap1.put(REPORT_CHART, lineChart1);
                            listMap1.put(REPORT_COUNTDATA, entityList2);
                            listMap1.put(REPORT_ROWS, pageReport1);
                            if (jc2 != null) {
                                JRedisUtils.returnJedis(jc2);
                            }
                            return listMap1;
                        }

                        //数据放入redis中
                        String lists = new Gson().toJson(endListMap);
                        String listsALL = new Gson().toJson(returnListAll);
                        jc2.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), lists);
                        jc2.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                        jc2.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), listsALL);
                        jc2.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);

                        List<StructureReportDTO> entityList2 = BasisReportCalculateUtil.getCountStructure(endListMap);
                        //曲线图数据计算
                        List<StructureReportDTO> lineChart1 = BasisReportCalculateUtil.getLineChart(endListMap, terminal);
                        ReportPageDetails pageDetails1 = new ReportPageDetails();
                        List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(endListMap, terminal, sort, start, limit, categoryTime);
                        endListMap.put(REPORT_STATISTICS, returnListAll);
                        endListMap.put(REPORT_CHART, lineChart1);
                        endListMap.put(REPORT_COUNTDATA, entityList2);
                        endListMap.put(REPORT_ROWS, pageReport1);
                        if (jc2 != null) {
                            JRedisUtils.returnJedis(jc2);
                        }
                        return endListMap;
                    } else {
                        List<StructureReportDTO> dateMap2else = new ArrayList<>();
                        StructureReportDTO dateObject2else = new StructureReportDTO();
                        List<StructureReportDTO> objectsList = new ArrayList<>();
                        //如果用户选择的时间范围小于或者等于7天
                        for (int i = 0; i < date.length; i++) {
                            List<StructureReportDTO> object = new ArrayList<>(basisReportDAO.getUnitReportDate(date[i] + BasisReportCalculateUtil.getTableType(reportType), dataId, dateName));
                            if (object.size() != 0) {
                                objectsList.addAll(object);
                            }
                        }

                        //计算合计数据
                        List<StructureReportDTO> returnListAll = BasisReportCalculateUtil.dataALL(objectsList);

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
                        Map<String, StructureReportDTO> reportEntities1 = BasisReportCalculateUtil.percentage(reportEntities);

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

                            List<StructureReportDTO> entityList2 = BasisReportCalculateUtil.getCountStructure(endListMap);
                            //曲线图数据计算
                            List<StructureReportDTO> lineChart1 = BasisReportCalculateUtil.getLineChart(listMap1, terminal);
                            ReportPageDetails pageDetails1 = new ReportPageDetails();
                            List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(listMap1, terminal, sort, start, limit, categoryTime);
                            listMap1.put(REPORT_STATISTICS, returnListAll);
                            listMap1.put(REPORT_CHART, lineChart1);
                            listMap1.put(REPORT_COUNTDATA, entityList2);
                            listMap1.put(REPORT_ROWS, pageReport1);
                            if (jc2 != null) {
                                JRedisUtils.returnJedis(jc2);
                            }
                            return listMap1;
                        }

                        //数据放入redis中
                        String lists = new Gson().toJson(endListMap);
                        String listsALL = new Gson().toJson(returnListAll);
                        jc2.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), lists);
                        jc2.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                        jc2.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), listsALL);
                        jc2.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);

                        List<StructureReportDTO> entityList2 = BasisReportCalculateUtil.getCountStructure(endListMap);
                        //曲线图数据计算
                        List<StructureReportDTO> lineChart1 = BasisReportCalculateUtil.getLineChart(endListMap, terminal);
                        ReportPageDetails pageDetails1 = new ReportPageDetails();
                        List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(endListMap, terminal, sort, start, limit, categoryTime);
                        endListMap.put(REPORT_STATISTICS, returnListAll);
                        endListMap.put(REPORT_CHART, lineChart1);
                        endListMap.put(REPORT_COUNTDATA, entityList2);
                        endListMap.put(REPORT_ROWS, pageReport1);
                        if (jc2 != null) {
                            JRedisUtils.returnJedis(jc2);
                        }
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

                    List<StructureReportDTO> entityList2 = BasisReportCalculateUtil.getCountStructure(list);
                    //曲线图数据计算
                    List<StructureReportDTO> lineChart1 = BasisReportCalculateUtil.getLineChart(list, terminal);
                    ReportPageDetails pageDetails1 = new ReportPageDetails();
                    List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(list, terminal, sort, start, limit, categoryTime);
                    endListMap.put(REPORT_STATISTICS, listAll);
                    endListMap.put(REPORT_CHART, lineChart1);
                    endListMap.put(REPORT_COUNTDATA, entityList2);
                    endListMap.put(REPORT_ROWS, pageReport1);
                    if (jc2 != null) {
                        JRedisUtils.returnJedis(jc2);
                    }
                    return endListMap;
                }

                //分月生成报告
            case 3:
                Jedis jc3 = JRedisUtils.get();

                Long jedisKey3 = jc3.ttl(date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId);
                if (jedisKey3 == -1 || jedisKey3 == -2) {
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

                                List<StructureReportDTO> object = new ArrayList<>(basisReportDAO.getUnitReportDate(date[j] + BasisReportCalculateUtil.getTableType(reportType), dataId, dateName));
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
                        List<StructureReportDTO> returnListAll = BasisReportCalculateUtil.dataALL(entityListAll);


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
                            Map<String, StructureReportDTO> reportEntities2 = BasisReportCalculateUtil.percentage(reportEntities1);

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

                            List<StructureReportDTO> entityList3 = BasisReportCalculateUtil.getCountStructure(listMap1);
                            //曲线图数据计算
                            List<StructureReportDTO> lineChart1 = BasisReportCalculateUtil.getLineChart(listMap1, terminal);
                            ReportPageDetails pageDetails1 = new ReportPageDetails();
                            List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(listMap1, terminal, sort, start, limit, categoryTime);
                            listMap1.put(REPORT_STATISTICS, returnListAll);
                            listMap1.put(REPORT_CHART, lineChart1);
                            listMap1.put(REPORT_COUNTDATA, entityList3);
                            listMap1.put(REPORT_ROWS, pageReport1);
                            if (jc3 != null) {
                                JRedisUtils.returnJedis(jc3);
                            }
                            return listMap1;
                        }
                        //数据放入redis中
                        String lists = new Gson().toJson(endListMap1);
                        String listsAll = new Gson().toJson(returnListAll);
                        jc3.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), lists);
                        jc3.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                        jc3.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), listsAll);
                        jc3.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);

                        List<StructureReportDTO> entityList3 = BasisReportCalculateUtil.getCountStructure(endListMap1);
                        //曲线图数据计算
                        List<StructureReportDTO> lineChart1 = BasisReportCalculateUtil.getLineChart(endListMap1, terminal);
                        ReportPageDetails pageDetails1 = new ReportPageDetails();
                        List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(endListMap1, terminal, sort, start, limit, categoryTime);
                        endListMap1.put(REPORT_STATISTICS, returnListAll);
                        endListMap1.put(REPORT_CHART, lineChart1);
                        endListMap1.put(REPORT_COUNTDATA, entityList3);
                        endListMap1.put(REPORT_ROWS, pageReport1);
                        if (jc3 != null) {
                            JRedisUtils.returnJedis(jc3);
                        }
                        return endListMap1;
                    } else {
                        List<StructureReportDTO> dateMap3else = new ArrayList<>();
                        StructureReportDTO dateObject3else = new StructureReportDTO();
                        //如果用户选择的时间范围小于或者等于30天
                        for (int i = 0; i < date.length; i++) {
                            List<StructureReportDTO> object = new ArrayList<>(basisReportDAO.getUnitReportDate(date[i] + BasisReportCalculateUtil.getTableType(reportType), dataId, dateName));
                            if (object.size() != 0) {
                                objectsList.addAll(object);
                            }
                        }
                        //计算合计数据
                        List<StructureReportDTO> returnListAll = BasisReportCalculateUtil.dataALL(objectsList);

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
                        Map<String, StructureReportDTO> reportEntities2 = BasisReportCalculateUtil.percentage(reportEntities1);

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

                            List<StructureReportDTO> entityList3 = BasisReportCalculateUtil.getCountStructure(listMap1);
                            //曲线图数据计算
                            List<StructureReportDTO> lineChart1 = BasisReportCalculateUtil.getLineChart(listMap1, terminal);
                            ReportPageDetails pageDetails1 = new ReportPageDetails();
                            List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(listMap1, terminal, sort, start, limit, categoryTime);
                            listMap1.put(REPORT_STATISTICS, returnListAll);
                            listMap1.put(REPORT_CHART, lineChart1);
                            listMap1.put(REPORT_COUNTDATA, entityList3);
                            listMap1.put(REPORT_ROWS, pageReport1);
                            if (jc3 != null) {
                                JRedisUtils.returnJedis(jc3);
                            }
                            return listMap1;
                        }
                        //数据放入redis中
                        String lists = new Gson().toJson(endListMap1);
                        String listsAll = new Gson().toJson(returnListAll);
                        jc3.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), lists);
                        jc3.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId), REDISKEYTIME);
                        jc3.set((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), listsAll);
                        jc3.expire((date[0] + "|" + date[date.length - 1] + "|" + terminal + "|" + categoryTime + "|" + reportType + "|" + AppContext.getAccountId() + "|" + dataId + "|Statist"), REDISKEYTIME);

                        List<StructureReportDTO> entityList3 = BasisReportCalculateUtil.getCountStructure(endListMap1);
                        //曲线图数据计算
                        List<StructureReportDTO> lineChart1 = BasisReportCalculateUtil.getLineChart(endListMap1, terminal);
                        ReportPageDetails pageDetails1 = new ReportPageDetails();
                        List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(endListMap1, terminal, sort, start, limit, categoryTime);
                        endListMap1.put(REPORT_STATISTICS, returnListAll);
                        endListMap1.put(REPORT_CHART, lineChart1);
                        endListMap1.put(REPORT_COUNTDATA, entityList3);
                        endListMap1.put(REPORT_ROWS, pageReport1);
                        if (jc3 != null) {
                            JRedisUtils.returnJedis(jc3);
                        }
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
                    List<StructureReportDTO> entityList3 = BasisReportCalculateUtil.getCountStructure(list);
                    //曲线图数据计算
                    List<StructureReportDTO> lineChart1 = BasisReportCalculateUtil.getLineChart(list, terminal);
                    ReportPageDetails pageDetails1 = new ReportPageDetails();
                    List<StructureReportDTO> pageReport1 = pageDetails1.getReportDetailsPage(list, terminal, sort, start, limit, categoryTime);
                    endListMap1.put(REPORT_STATISTICS, listAll);
                    endListMap1.put(REPORT_CHART, lineChart1);
                    endListMap1.put(REPORT_COUNTDATA, entityList3);
                    endListMap1.put(REPORT_ROWS, pageReport1);
                    if (jc3 != null) {
                        JRedisUtils.returnJedis(jc3);
                    }
                    return endListMap1;
                }

        }
        return null;
    }

    @Override
    public Map<String, List<AccountReportDTO>> getAccountAll(int Sorted, String fieldName, int startJC, int limitJC) {

        List<AccountReportDTO> reportEntities = new ArrayList<>(basisReportDAO.getAccountReport(Sorted, fieldName));

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
                dtoRings.setPcCost(((dtoRing.getPcCost().subtract(listAve.get(x).getPcCost())).divide((listAve.get(x).getPcCost().doubleValue() == 0) ? BigDecimal.valueOf(1) : listAve.get(x).getPcCost(), 4, BigDecimal.ROUND_UP)).multiply(BigDecimal.valueOf(100)));
                int s = (int) ((dtoRing.getPcCtr() - listAve.get(x).getPcCtr()) / ((listAve.get(x).getPcCtr() <= 0) ? 1 : listAve.get(x).getPcCtr()) * 10000);
                dtoRings.setPcCtr((double) s);
                dtoRings.setPcCpc(((dtoRing.getPcCpc().subtract(listAve.get(x).getPcCpc())).divide((listAve.get(x).getPcCpc().doubleValue() == 0) ? BigDecimal.valueOf(1) : listAve.get(x).getPcCpc(), 4, BigDecimal.ROUND_UP)).multiply(BigDecimal.valueOf(100)));
                int a = (int) ((dtoRing.getPcConversion() - listAve.get(x).getPcConversion()) / ((listAve.get(x).getPcConversion() <= 0) ? 1 : listAve.get(x).getPcConversion()) * 10000);
                dtoRings.setPcConversion((double) a);
                list.add(dtoRings);
            }
        }
        map.put(REPORT_ROWS, listAve);
        map.put("Ring", list);
        return map;
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
                List<StructureReportDTO> returnStructure = new ArrayList<>(basisReportDAO.getKeywordReport(id, newDate.get(i) + "-keyword"));
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
}
