package com.perfect.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.perfect.core.AppContext;
import com.perfect.dao.BasisReportDAO;
import com.perfect.dto.AccountReportDTO;
import com.perfect.entity.StructureReportEntity;
import com.perfect.mongodb.utils.DateUtils;
import com.perfect.redis.JRedisUtils;
import com.perfect.service.BasisReportService;
import com.perfect.utils.JSONUtils;
import com.perfect.utils.reportUtil.*;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * Created by SubDong on 2014/8/6.
 */
@Repository("basisReportService")
public class BasisReportServiceImpl implements BasisReportService {
    @Resource
    private BasisReportDAO basisReportDAO;

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
    public Map<String, List<StructureReportEntity>> getReportDate(String[] date, int terminal, int categoryTime, int reportType, int start, int limit, String sort) {
        List<StructureReportEntity> objectsList = new ArrayList<>();


        switch (categoryTime) {
            //默认时间生成报告
            case 0:
                Jedis jc = JRedisUtils.get();

                Long jedisKey = jc.ttl(date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId());
                if (jedisKey == -1) {
                    List<StructureReportEntity> dateMap0 = new ArrayList<>();
                    StructureReportEntity dateObject0 = new StructureReportEntity();
                    //初始化容器
                    Map<String, StructureReportEntity> map = new HashMap<>();
                    List<StructureReportEntity> returnList = new ArrayList<>();
                    Map<String, List<StructureReportEntity>> listMap = new HashMap<>();
                    //获取需要的数据
                    for (int i = 0; i < date.length; i++) {
                        List<StructureReportEntity> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType));
                        if (object.size() != 0) {
                            objectsList.addAll(object);
                        }
                    }
                    dateObject0.setDate(date[0] + " 至 " + date[date.length - 1]);
                    dateMap0.add(dateObject0);
                    //创建一个并行计算框架
                    ForkJoinPool joinPool = new ForkJoinPool();
                    Map<String, StructureReportEntity> map1 = null;
                    try {
                        //开始对数据处理
                        Future<Map<String, StructureReportEntity>> joinTask = joinPool.submit(new BasisReportDefaultUtil(objectsList, 0, objectsList.size(), reportType));
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
                    List<StructureReportEntity> list = new ArrayList<>(map1.values());

                    //选择全部内容
                    if (terminal == 0) {
                        //创建一个并行计算框架
                        ForkJoinPool joinPoolTow = new ForkJoinPool();
                        //对第一次处理后的数据进行第二次处理
                        Future<List<StructureReportEntity>> joinTaskTow = joinPoolTow.submit(new BasistReportPCPlusMobUtil(list, 0, list.size()));
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
                            List<StructureReportEntity> pieIpmr = getPieData(returnList, terminal, "impr", "-1");
                            //计算饼状图 点击数据
                            List<StructureReportEntity> pieClick = getPieData(returnList, terminal, "click", "-2");
                            //计算饼状图 消费数据
                            List<StructureReportEntity> pieCost = getPieData(returnList, terminal, "cost", "-3");
                            //计算饼状图 转化数据
                            List<StructureReportEntity> pieConv = getPieData(returnList, terminal, "conv", "-6");

                            listMap.put("impr", pieIpmr);
                            listMap.put("click", pieClick);
                            listMap.put("cost", pieCost);
                            listMap.put("conv", pieConv);
                        }
                        String dds = new Gson().toJson(returnList);
                        jc.set((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), dds);
                        jc.expire((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), REDISKEYTIME);
                        ReportPageDetails pageDetails = new ReportPageDetails();
                        List<StructureReportEntity> pageReport = pageDetails.getReportDetailsPageObj(returnList, terminal, sort, start, limit, date[0] + " 至 " + date[date.length - 1]);
                        List<StructureReportEntity> entityList1 = getCountStructure(listMap);
                        listMap.put("countData", entityList1);
                        listMap.put("rows", pageReport);
                        return listMap;
                    } else {
                        if (reportType == 4) {
                            //计算饼状图 展现数据
                            List<StructureReportEntity> pieIpmr = getPieData(list, terminal, "impr", "-1");
                            //计算饼状图 点击数据
                            List<StructureReportEntity> pieClick = getPieData(list, terminal, "click", "-2");
                            //计算饼状图 消费数据
                            List<StructureReportEntity> pieCost = getPieData(list, terminal, "cost", "-3");
                            //计算饼状图 转化数据
                            List<StructureReportEntity> pieConv = getPieData(list, terminal, "conv", "-6");

                            listMap.put("impr", pieIpmr);
                            listMap.put("click", pieClick);
                            listMap.put("cost", pieCost);
                            listMap.put("conv", pieConv);
                        }
                        String lists = new Gson().toJson(list);
                        jc.set((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), lists);
                        jc.expire((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), REDISKEYTIME);
                        ReportPageDetails pageDetails = new ReportPageDetails();
                        List<StructureReportEntity> pageReport = pageDetails.getReportDetailsPageObj(list, terminal, sort, start, limit, date[0] + " 至 " + date[date.length - 1]);
                        List<StructureReportEntity> entityList1 = getCountStructure(listMap);
                        listMap.put("countData", entityList1);
                        listMap.put("rows", pageReport);
                        return listMap;
                    }
                } else {
                    String data = jc.get((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()));
                    Gson gson = new Gson();
                    List<StructureReportEntity> list = gson.fromJson(data, new TypeToken<List<StructureReportEntity>>() {
                    }.getType());


                    Map<String, List<StructureReportEntity>> listMap = new HashMap<>();
                    if (reportType == 4) {
                        //计算饼状图 展现数据
                        List<StructureReportEntity> pieIpmr = getPieData(list, terminal, "impr", "-1");
                        //计算饼状图 点击数据
                        List<StructureReportEntity> pieClick = getPieData(list, terminal, "click", "-2");
                        //计算饼状图 消费数据
                        List<StructureReportEntity> pieCost = getPieData(list, terminal, "cost", "-3");
                        //计算饼状图 转化数据
                        List<StructureReportEntity> pieConv = getPieData(list, terminal, "conv", "-6");

                        listMap.put("impr", pieIpmr);
                        listMap.put("click", pieClick);
                        listMap.put("cost", pieCost);
                        listMap.put("conv", pieConv);
                    }
                    ReportPageDetails pageDetails = new ReportPageDetails();
                    if ("-11".equals(sort) || "11".equals(sort)) {
                        sort = "-1";
                    }
                    List<StructureReportEntity> pageReport = pageDetails.getReportDetailsPageObj(list, terminal, sort, start, limit, date[0] + " 至 " + date[date.length - 1]);
                    List<StructureReportEntity> entityList1 = getCountStructure(listMap);
                    listMap.put("countData", entityList1);
                    listMap.put("rows", pageReport);
                    return listMap;
                }
                //分日生成报告
            case 1:
                Jedis jc1 = JRedisUtils.get();

                Long jedisKey1 = jc1.ttl(date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId());
                if (jedisKey1 == -1) {
                    List<StructureReportEntity> dateMap = new ArrayList<>();

                    //初始化容器
                    Map<String, List<StructureReportEntity>> mapDay = new HashMap<>();
                    //获得需要的数据
                    for (int i = 0; i < date.length; i++) {
                        StructureReportEntity dateObject = new StructureReportEntity();
                        List<StructureReportEntity> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType));
                        if (object.size() != 0) {
                            mapDay.put(date[i], object);
                            dateObject.setDate(date[i]);
                            dateMap.add(dateObject);
                        }
                    }

                    Map<String, List<StructureReportEntity>> mapDay1 = null;
                    if (terminal == 0) {
                        Map<String, List<StructureReportEntity>> listMap1 = terminalAll(mapDay);

                        //数据放入redis中
                        String lists = new Gson().toJson(listMap1);
                        jc1.set((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), lists);
                        jc1.expire((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), REDISKEYTIME);

                        List<StructureReportEntity> entityList1 = getCountStructure(listMap1);
                        //曲线图数据计算
                        List<StructureReportEntity> lineChart = getLineChart(listMap1, terminal);
                        ReportPageDetails pageDetails = new ReportPageDetails();
                        List<StructureReportEntity> pageReport = pageDetails.getReportDetailsPage(listMap1, terminal, sort, start, limit,categoryTime);
                        listMap1.put("countData", entityList1);
                        listMap1.put("chart", lineChart);
                        listMap1.put("rows", pageReport);
                        return listMap1;
                    } else {
                        //对相应的数据进行计算百分比
                        mapDay1 = percentageList(mapDay);
                    }
                    //数据放入redis中
                    String lists = new Gson().toJson(mapDay1);
                    jc1.set((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), lists);
                    jc1.expire((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), REDISKEYTIME);

                    List<StructureReportEntity> entityList1 = getCountStructure(mapDay1);
                    //曲线图数据计算
                    List<StructureReportEntity> lineChart = getLineChart(mapDay1, terminal);
                    ReportPageDetails pageDetails = new ReportPageDetails();
                    List<StructureReportEntity> pageReport = pageDetails.getReportDetailsPage(mapDay1, terminal, sort, start, limit,categoryTime);
                    mapDay.put("chart", lineChart);
                    mapDay.put("countData", entityList1);
                    mapDay.put("rows", pageReport);
                    return mapDay;
                } else {
                    String data = jc1.get((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()));
                    Gson gson = new Gson();
                    Map<String, List<StructureReportEntity>> list = gson.fromJson(data, new TypeToken<Map<String, List<StructureReportEntity>>>() {
                    }.getType());

                    Map<String, List<StructureReportEntity>> listMap1 = new HashMap<>();

                    List<StructureReportEntity> entityList1 = getCountStructure(list);
                    //曲线图数据计算
                    List<StructureReportEntity> lineChart = getLineChart(list, terminal);
                    ReportPageDetails pageDetails = new ReportPageDetails();
                    List<StructureReportEntity> pageReport = pageDetails.getReportDetailsPage(list, terminal, sort, start, limit,categoryTime);
                    listMap1.put("countData", entityList1);
                    listMap1.put("chart", lineChart);
                    listMap1.put("rows", pageReport);
                    return listMap1;
                }

                //分周生成报告
            case 2:
                Jedis jc2 = JRedisUtils.get();

                Long jedisKey2 = jc2.ttl(date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId());
                if (jedisKey2 == -1) {
                    List<StructureReportEntity> dateMap2 = new ArrayList<>();

                    Map<String, StructureReportEntity> reportEntities = new HashMap<>();
                    Map<String, List<StructureReportEntity>> stringListMap = new HashMap<>();
                    Map<String, List<StructureReportEntity>> endListMap = new HashMap<>();
                    //如果用户选择的时间范围大于7天
                    if (date.length > 7) {
                        int i = 0;
                        int endNumber = 0;
                        int endStep = endStep = date.length < 7 ? 1 : date.length % 7 == 0 ? date.length : (date.length / 7) + 1;
                        for (int x = 0; x < endStep; x++) {
                            StructureReportEntity dateObject2 = new StructureReportEntity();
                            List<StructureReportEntity> objectsList1 = new ArrayList<>();
                            String[] strings = new String[2];
                            for (i = endNumber; i < ((i == 0) ? endNumber + 6 : endNumber + 7); i++) {
                                if (i >= date.length) {
                                    continue;
                                }
                                List<StructureReportEntity> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType));
                                if (object.size() != 0) {
                                    objectsList1.addAll(object);
                                }
                                if (i == endNumber) {
                                    strings[0] = date[i];
                                    if(i == date.length-1){
                                        strings[1] = date[i];
                                    }
                                } else {
                                    strings[1] = date[i];
                                }
                            }
                            stringListMap.put(strings[0] + " 至 " + strings[1], objectsList1);
                            dateObject2.setDate(strings[0] + " 至 " + strings[1]);
                            dateMap2.add(dateObject2);
                            endNumber = i;
                        }

                        for (Map.Entry<String, List<StructureReportEntity>> entry1 : stringListMap.entrySet()) {
                            //创建一个并行计算框架
                            ForkJoinPool joinPoolTow = new ForkJoinPool();
                            //获取map中的value
                            List<StructureReportEntity> list1 = entry1.getValue();
                            //开始对数据进行处理
                            Future<Map<String, StructureReportEntity>> joinTask = joinPoolTow.submit(new BasisReportDefaultUtil(list1, 0, list1.size(), reportType));
                            //接收处理好的数据
                            try {
                                reportEntities = joinTask.get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

                            //对相应的数据进行计算百分比
                            Map<String, StructureReportEntity> reportEntities1 = percentage(reportEntities);

                            //关闭并行计算框架
                            joinPoolTow.shutdown();
                            List<StructureReportEntity> arrayList = new ArrayList<>(reportEntities1.values());
                            endListMap.put(entry1.getKey(), arrayList);
                        }
                        if (terminal == 0) {
                            Map<String, List<StructureReportEntity>> listMap1 = terminalAll(endListMap);

                            //数据放入redis中
                            String lists = new Gson().toJson(listMap1);
                            jc2.set((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), lists);
                            jc2.expire((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), REDISKEYTIME);

                            List<StructureReportEntity> entityList2 = getCountStructure(listMap1);
                            //曲线图数据计算
                            List<StructureReportEntity> lineChart1 = getLineChart(listMap1, terminal);
                            ReportPageDetails pageDetails1 = new ReportPageDetails();
                            List<StructureReportEntity> pageReport1 = pageDetails1.getReportDetailsPage(listMap1, terminal, sort, start, limit,categoryTime);
                            listMap1.put("chart", lineChart1);
                            listMap1.put("countData", entityList2);
                            listMap1.put("rows", pageReport1);
                            return listMap1;
                        }

                        //数据放入redis中
                        String lists = new Gson().toJson(endListMap);
                        jc2.set((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), lists);
                        jc2.expire((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), REDISKEYTIME);

                        List<StructureReportEntity> entityList2 = getCountStructure(endListMap);
                        //曲线图数据计算
                        List<StructureReportEntity> lineChart1 = getLineChart(endListMap, terminal);
                        ReportPageDetails pageDetails1 = new ReportPageDetails();
                        List<StructureReportEntity> pageReport1 = pageDetails1.getReportDetailsPage(endListMap, terminal, sort, start, limit,categoryTime);
                        endListMap.put("chart", lineChart1);
                        endListMap.put("countData", entityList2);
                        endListMap.put("rows", pageReport1);
                        return endListMap;
                    } else {
                        List<StructureReportEntity> dateMap2else = new ArrayList<>();
                        StructureReportEntity dateObject2else = new StructureReportEntity();
                        //如果用户选择的时间范围小于或者等于7天
                        for (int i = 0; i < date.length; i++) {
                            List<StructureReportEntity> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType));
                            if (object.size() != 0) {
                                objectsList.addAll(object);
                            }
                        }
                        dateObject2else.setDate(date[0] + " 至 " + date[date.length - 1]);
                        dateMap2else.add(dateObject2else);
                        //创建一个并行计算框架
                        ForkJoinPool joinPoolTow = new ForkJoinPool();
                        //开始对数据进行处理
                        Future<Map<String, StructureReportEntity>> joinTask = joinPoolTow.submit(new BasisReportDefaultUtil(objectsList, 0, objectsList.size(), reportType));
                        try {
                            //得到处理好的数据
                            reportEntities = joinTask.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        //对相应的数据进行计算百分比
                        Map<String, StructureReportEntity> reportEntities1 = percentage(reportEntities);

                        //关闭并行计算框架
                        joinPoolTow.shutdown();
                        List<StructureReportEntity> entityList = new ArrayList<>(reportEntities1.values());
                        endListMap.put(date[0] + " 至 " + date[date.length - 1], entityList);
                        if (terminal == 0) {
                            Map<String, List<StructureReportEntity>> listMap1 = terminalAll(endListMap);
                            //数据放入redis中
                            String lists = new Gson().toJson(listMap1);
                            jc2.set((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), lists);
                            jc2.expire((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), REDISKEYTIME);

                            List<StructureReportEntity> entityList2 = getCountStructure(endListMap);
                            //曲线图数据计算
                            List<StructureReportEntity> lineChart1 = getLineChart(listMap1, terminal);
                            ReportPageDetails pageDetails1 = new ReportPageDetails();
                            List<StructureReportEntity> pageReport1 = pageDetails1.getReportDetailsPage(listMap1, terminal, sort, start, limit,categoryTime);
                            listMap1.put("chart", lineChart1);
                            listMap1.put("countData", entityList2);
                            listMap1.put("rows", pageReport1);
                            return listMap1;
                        }

                        //数据放入redis中
                        String lists = new Gson().toJson(endListMap);
                        jc2.set((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), lists);
                        jc2.expire((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), REDISKEYTIME);

                        List<StructureReportEntity> entityList2 = getCountStructure(endListMap);
                        //曲线图数据计算
                        List<StructureReportEntity> lineChart1 = getLineChart(endListMap, terminal);
                        ReportPageDetails pageDetails1 = new ReportPageDetails();
                        List<StructureReportEntity> pageReport1 = pageDetails1.getReportDetailsPage(endListMap, terminal, sort, start, limit,categoryTime);
                        endListMap.put("chart", lineChart1);
                        endListMap.put("countData", entityList2);
                        endListMap.put("rows", pageReport1);
                        return endListMap;
                    }
                } else {
                    String data = jc2.get((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()));
                    Gson gson = new Gson();
                    Map<String, List<StructureReportEntity>> list = gson.fromJson(data, new TypeToken<Map<String, List<StructureReportEntity>>>() {
                    }.getType());

                    Map<String, List<StructureReportEntity>> endListMap = new HashMap<>();

                    List<StructureReportEntity> entityList2 = getCountStructure(list);
                    //曲线图数据计算
                    List<StructureReportEntity> lineChart1 = getLineChart(list, terminal);
                    ReportPageDetails pageDetails1 = new ReportPageDetails();
                    List<StructureReportEntity> pageReport1 = pageDetails1.getReportDetailsPage(list, terminal, sort, start, limit,categoryTime);
                    endListMap.put("chart", lineChart1);
                    endListMap.put("countData", entityList2);
                    endListMap.put("rows", pageReport1);
                    return endListMap;
                }

                //分月生成报告
            case 3:
                Jedis jc3 = JRedisUtils.get();

                Long jedisKey3 = jc3.ttl(date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId());
                if (jedisKey3 == -1) {
                    List<StructureReportEntity> dateMap3 = new ArrayList<>();

                    Map<String, StructureReportEntity> reportEntities1 = new HashMap<>();
                    Map<String, List<StructureReportEntity>> stringListMap1 = new HashMap<>();
                    Map<String, List<StructureReportEntity>> endListMap1 = new HashMap<>();
                    //如果用户选择的时间范围大于30天
                    if (date.length > 30) {
                        int i = 0;
                        int endNumber = 0;
                        int endStep = date.length < 30 ? 1 : date.length % 30 == 0 ? date.length : (date.length / 30) + 1;
                        for (int x = 0; x < endStep; x++) {
                            StructureReportEntity dateObject3 = new StructureReportEntity();
                            String[] strings = new String[2];
                            for (i = endNumber; i < endNumber + ((i == 0) ? endNumber + 29 : endNumber + 30); i++) {
                                if (i >= date.length) {
                                    continue;
                                }
                                List<StructureReportEntity> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType));
                                if (object.size() != 0) {
                                    objectsList.addAll(object);
                                }
                                if (i == endNumber) {
                                    strings[0] = date[i];
                                    if(i == date.length-1){
                                        strings[1] = date[i];
                                    }
                                } else {
                                    strings[1] = date[i];
                                }
                            }
                            stringListMap1.put(strings[0] + " 至 " + strings[1], objectsList);
                            dateObject3.setDate(strings[0] + " 至 " + strings[1]);
                            dateMap3.add(dateObject3);
                            endNumber = i;
                        }

                        for (Map.Entry<String, List<StructureReportEntity>> entry1 : stringListMap1.entrySet()) {
                            //创建一个并行计算框架
                            ForkJoinPool joinPoolTow = new ForkJoinPool();
                            //获取map中的value
                            List<StructureReportEntity> list1 = entry1.getValue();
                            //开始对数据进行处理
                            Future<Map<String, StructureReportEntity>> joinTask = joinPoolTow.submit(new BasisReportDefaultUtil(list1, 0, list1.size(), reportType));
                            //接收处理好的数据
                            try {
                                reportEntities1 = joinTask.get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                            //对相应的数据进行计算百分比
                            Map<String, StructureReportEntity> reportEntities2 = percentage(reportEntities1);

                            //关闭并行计算框架
                            joinPoolTow.shutdown();
                            List<StructureReportEntity> entityList = new ArrayList<>(reportEntities2.values());
                            endListMap1.put(entry1.getKey(), entityList);
                        }
                        if (terminal == 0) {
                            Map<String, List<StructureReportEntity>> listMap1 = terminalAll(endListMap1);

                            //数据放入redis中
                            String lists = new Gson().toJson(listMap1);
                            jc3.set((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), lists);
                            jc3.expire((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), REDISKEYTIME);

                            List<StructureReportEntity> entityList3 = getCountStructure(listMap1);
                            //曲线图数据计算
                            List<StructureReportEntity> lineChart1 = getLineChart(listMap1, terminal);
                            ReportPageDetails pageDetails1 = new ReportPageDetails();
                            List<StructureReportEntity> pageReport1 = pageDetails1.getReportDetailsPage(listMap1, terminal, sort, start, limit,categoryTime);
                            listMap1.put("chart", lineChart1);
                            listMap1.put("countData", entityList3);
                            listMap1.put("rows", pageReport1);
                            return listMap1;
                        }
                        //数据放入redis中
                        String lists = new Gson().toJson(endListMap1);
                        jc3.set((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), lists);
                        jc3.expire((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), REDISKEYTIME);

                        List<StructureReportEntity> entityList3 = getCountStructure(endListMap1);
                        //曲线图数据计算
                        List<StructureReportEntity> lineChart1 = getLineChart(endListMap1, terminal);
                        ReportPageDetails pageDetails1 = new ReportPageDetails();
                        List<StructureReportEntity> pageReport1 = pageDetails1.getReportDetailsPage(endListMap1, terminal, sort, start, limit,categoryTime);
                        endListMap1.put("chart", lineChart1);
                        endListMap1.put("countData", entityList3);
                        endListMap1.put("rows", pageReport1);
                        return endListMap1;
                    } else {
                        List<StructureReportEntity> dateMap3else = new ArrayList<>();
                        StructureReportEntity dateObject3else = new StructureReportEntity();
                        //如果用户选择的时间范围小于或者等于30天
                        for (int i = 0; i < date.length; i++) {
                            List<StructureReportEntity> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType));
                            if (object.size() != 0) {
                                objectsList.addAll(object);
                            }
                        }
                        dateObject3else.setDate(date[0] + " 至 " + date[date.length - 1]);
                        dateMap3else.add(dateObject3else);
                        //创建一个并行计算框架
                        ForkJoinPool joinPoolTow = new ForkJoinPool();
                        //开始对数据进行处理
                        Future<Map<String, StructureReportEntity>> joinTask = joinPoolTow.submit(new BasisReportDefaultUtil(objectsList, 0, objectsList.size(), reportType));
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
                        Map<String, StructureReportEntity> reportEntities2 = percentage(reportEntities1);

                        //关闭并行计算框架
                        joinPoolTow.shutdown();
                        List<StructureReportEntity> entityList = new ArrayList<>(reportEntities2.values());
                        endListMap1.put(date[0] + " 至 " + date[date.length - 1], entityList);
                        if (terminal == 0) {
                            Map<String, List<StructureReportEntity>> listMap1 = terminalAll(endListMap1);
                            //数据放入redis中
                            String lists = new Gson().toJson(listMap1);
                            jc3.set((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), lists);
                            jc3.expire((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), REDISKEYTIME);

                            List<StructureReportEntity> entityList3 = getCountStructure(listMap1);
                            //曲线图数据计算
                            List<StructureReportEntity> lineChart1 = getLineChart(listMap1, terminal);
                            ReportPageDetails pageDetails1 = new ReportPageDetails();
                            List<StructureReportEntity> pageReport1 = pageDetails1.getReportDetailsPage(listMap1, terminal, sort, start, limit,categoryTime);
                            listMap1.put("chart", lineChart1);
                            listMap1.put("countData", entityList3);
                            listMap1.put("rows", pageReport1);
                            return listMap1;
                        }
                        //数据放入redis中
                        String lists = new Gson().toJson(endListMap1);
                        jc3.set((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), lists);
                        jc3.expire((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()), REDISKEYTIME);

                        List<StructureReportEntity> entityList3 = getCountStructure(endListMap1);
                        //曲线图数据计算
                        List<StructureReportEntity> lineChart1 = getLineChart(endListMap1, terminal);
                        ReportPageDetails pageDetails1 = new ReportPageDetails();
                        List<StructureReportEntity> pageReport1 = pageDetails1.getReportDetailsPage(endListMap1, terminal, sort, start, limit,categoryTime);
                        endListMap1.put("chart", lineChart1);
                        endListMap1.put("countData", entityList3);
                        endListMap1.put("rows", pageReport1);
                        return endListMap1;
                    }
                } else {
                    String data = jc3.get((date[0] + date[date.length - 1] + terminal + categoryTime + reportType + AppContext.getAccountId()));
                    Gson gson = new Gson();
                    Map<String, List<StructureReportEntity>> list = gson.fromJson(data, new TypeToken<Map<String, List<StructureReportEntity>>>() {
                    }.getType());
                    Map<String, List<StructureReportEntity>> endListMap1 = new HashMap<>();
                    List<StructureReportEntity> entityList3 = getCountStructure(list);
                    //曲线图数据计算
                    List<StructureReportEntity> lineChart1 = getLineChart(list, terminal);
                    ReportPageDetails pageDetails1 = new ReportPageDetails();
                    List<StructureReportEntity> pageReport1 = pageDetails1.getReportDetailsPage(list, terminal, sort, start, limit,categoryTime);
                    endListMap1.put("chart", lineChart1);
                    endListMap1.put("countData", entityList3);
                    endListMap1.put("rows", pageReport1);
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
        map.put("rows", finalList);
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
                    retrunMap.put("rows", objectList);
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
                retrunMap.put("rows", objectList);
                retrunMap.put("date", objectListDate1);


                return retrunMap;
            case 1:
                //分日
                Map<String, List<Object>> retrunMap1 = new HashMap<>();
                Map<String, List<AccountReportDTO>> responseMapOne1 = new HashMap<>();
                Map<String, List<AccountReportDTO>> responseMapTow1 = new HashMap<>();

                List<AccountReportDTO> listOne1 = basisReportDAO.getAccountReport(dateOne[0], dateOne[1]);
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
                        retrunMap1.put("rows", objectList1);
                        retrunMap1.put("date", pageDate);
                    } else {
                        retrunMap1.put("rows", objectList1);
                        retrunMap1.put("date", objectListDateOne1);
                    }
                    return retrunMap1;
                }

                List<Object> objectList1 = new ArrayList<>();
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
                    retrunMap1.put("rows", objectList1);
                    retrunMap1.put("date", pageDate);
                } else {
                    retrunMap1.put("rows", objectList1);
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


                for (AccountReportDTO responseZou : listOne2) {
                    objectListDateOne2.add(responseZou.getDate());
                }

                List<Object> objectList2 = new ArrayList<>();
                List<AccountReportDTO> listTow2 = null;
                //比较数据
                if (compare == 1) {
                    listTow2 = basisReportDAO.getAccountReport(dateTow[0], dateTow[1]);

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
                    for (s = endNumber; s < endNumber + ((i == 0) ? 6 : 7); s++) {
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
                    retrunMap2.put("rows", objectList2);
                    retrunMap2.put("date", pageDate1);
                } else {
                    retrunMap2.put("rows", objectList2);
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


                for (AccountReportDTO responseYue : listOne3) {
                    objectListDateOne3.add(responseYue.getDate());
                }
                List<AccountReportDTO> listTow3 = null;
                //比较数据
                if (compare == 1) {
                    listTow3 = basisReportDAO.getAccountReport(dateTow[0], dateTow[1]);
                    for (AccountReportDTO responseTowYue : listTow3) {
                        objectListDateTow3.add(responseTowYue.getDate());
                    }
                }

                List<Object> objectList3 = new ArrayList<>();
                int y = 0;
                int endNumber1 = 0;
                int steeps = (objectListDateOne3.size() % 30 == 0) ? (objectListDateOne3.size() / 30) : (objectListDateOne3.size() / 30) + 1;
                for (int i = 0; i < steeps; i++) {
                    List<AccountReportDTO> listDateOne1 = new ArrayList<>();
                    List<AccountReportDTO> listDateTow1 = new ArrayList<>();
                    Date[] newDateOne = null;
                    Date[] newDateTow = null;
                    for (y = endNumber1; y < endNumber1 + 30; y++) {
                        if (endNumber1 >= objectListDateOne3.size() || y >= objectListDateOne3.size()) {
                            continue;
                        }
                        listDateOne1.add(listOne3.get(y));
                        //比较数据
                        if (compare == 1) {
                            listDateTow1.add(listTow3.get(i));
                        }
                    }
                    endNumber1 = y;
                    //获取数据
                    Map<String, List<AccountReportDTO>> responseMapOne4 = getUserDataPro(listDateOne1, listDateOne1.get(0).getDate(), listDateOne1.get(listDateOne1.size() - 1).getDate());
                    newDateOne = new Date[]{listDateOne1.get(0).getDate(), listDateOne1.get(listDateOne1.size() - 1).getDate()};

                    //比较数据
                    Map<String, List<AccountReportDTO>> responseMapTow4 = null;
                    if (compare == 1) {
                        responseMapTow4 = getUserDataPro(listDateTow1, listDateTow1.get(0).getDate(), listDateTow1.get(listDateTow1.size() - 1).getDate());
                        newDateTow = new Date[]{listDateTow1.get(0).getDate(), listDateTow1.get(listDateTow1.size() - 1).getDate()};
                    }

                    if (devices == 0) {
                        Map<String, List<AccountReportDTO>> responseMapDevicesOne = getPcPlusMobileDate(responseMapOne4);
                        objectList3.add(responseMapDevicesOne);
                        objectListDateOne31.add(dateFormat.format(newDateOne[0]) + " 至 " + dateFormat.format(newDateOne[1]));
                        //比较数据
                        if (compare == 1) {
                            Map<String, List<AccountReportDTO>> responseMapDevicesTow = getPcPlusMobileDate(responseMapTow4);
                            objectList3.add(responseMapDevicesTow);
                            objectListDateTow31.add(dateFormat.format(newDateTow[0]) + " 至 " + dateFormat.format(newDateTow[1]));
                        }
                    } else {
                        objectList3.add(responseMapOne4);
                        objectListDateOne31.add(dateFormat.format(newDateOne[0]) + " 至 " + dateFormat.format(newDateOne[1]));
                        //比较数据
                        if (compare == 1) {
                            objectList3.add(responseMapTow4);
                            objectListDateTow31.add(dateFormat.format(newDateTow[0]) + " 至 " + dateFormat.format(newDateTow[1]));
                        }
                    }
                }
                if (compare != 1) {
                    ReportPage reportPage2 = new ReportPage();
                    List<Object> pageDate2 = reportPage2.getReportPageObj(objectList3, devices, sortVS, startVS, limitVS);
                    retrunMap3.put("rows", objectList3);
                    retrunMap3.put("date", pageDate2);
                } else {
                    retrunMap3.put("rows", objectList3);
                    retrunMap3.put("date", objectListDateOne31);
                }
                //比较数据
                if (compare == 1) {
                    retrunMap3.put("date1", objectListDateTow31);
                }
                return retrunMap3;
        }
        return null;
    }

    /**
     * ****************API****************************
     */
    @Override
    public Map<String, List<StructureReportEntity>> getKeywordReport(Long[] id, String startDate, String endDate, int devices) {
        List<String> newDate = DateUtils.getPeriod(startDate, endDate);
        Map<String, List<StructureReportEntity>> listMap = new HashMap<>();
        if (newDate.size() > 0) {
            for (int i = 0; i < newDate.size(); i++) {
                List<StructureReportEntity> returnStructure = basisReportDAO.getKeywordReport(id, newDate.get(i) + "-keyword");
                if (devices == 0) {
                    ForkJoinPool joinPoolTow = new ForkJoinPool();
                    //开始对数据进行处理
                    Future<List<StructureReportEntity>> joinTask = joinPoolTow.submit(new BasistReportPCPlusMobUtil(returnStructure, 0, returnStructure.size()));
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
        DecimalFormat df = new DecimalFormat("#.0000");
        for (Map.Entry<String, List<AccountReportDTO>> voEntity : responseMap.entrySet()) {
            for (AccountReportDTO response : voEntity.getValue()) {
                response.setPcImpression(response.getPcImpression() + ((response.getMobileImpression() == null) ? 0 : response.getMobileImpression()));
                response.setPcConversion(response.getPcConversion() + ((response.getMobileConversion() == null) ? 0 : response.getMobileConversion()));
                response.setPcClick(response.getPcClick() + ((response.getMobileClick() == null) ? 0 : response.getMobileClick()));
                response.setPcCost(response.getPcCost().add((response.getMobileCost() == null) ? BigDecimal.valueOf(0) : response.getMobileCost()));
                //计算点击率
                if (((response.getPcImpression() == null) ? 0 : response.getPcImpression()) == 0) {
                    response.setPcCtr(0.00);
                } else {
                    BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format((response.getPcClick().doubleValue() / response.getPcImpression().doubleValue()))));
                    BigDecimal big = new BigDecimal(100);
                    double divide = ctrBig.multiply(big).doubleValue();
                    response.setPcCtr(divide);
                }

                //计算平均点击价格
                if (((response.getPcClick() == null) ? 0 : response.getPcClick()) == 0) {
                    response.setPcCpc(BigDecimal.valueOf(0));
                } else {
                    response.setPcCpc(response.getPcCost().divide(BigDecimal.valueOf(response.getPcClick()), 2, BigDecimal.ROUND_UP));
                }

                response.setMobileImpression(null);
                response.setMobileClick(null);
                response.setMobileConversion(null);
                response.setMobileCost(null);
                response.setMobileCpc(null);
                response.setMobileCpm(null);
                response.setMobileCtr(null);
            }
        }
        return responseMap;
    }

    /**
     * 账户计算点击率 平均价格等
     */
    private Map<String, List<AccountReportDTO>> getAverage(Map<String, List<AccountReportDTO>> responseMap) {
        DecimalFormat df = new DecimalFormat("#.00");
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
                    BigDecimal big = new BigDecimal(100);
                    double divide = ctrBig.multiply(big).doubleValue();
                    response.setPcCtr(divide);
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
    private Map<String, List<StructureReportEntity>> terminalAll(Map<String, List<StructureReportEntity>> entitiesMap) {
        List<StructureReportEntity> entities = new ArrayList<>();
        Map<String, List<StructureReportEntity>> listMapDay = new HashMap<>();
        for (Iterator<Map.Entry<String, List<StructureReportEntity>>> entry1 = entitiesMap.entrySet().iterator(); entry1.hasNext(); ) {
            //创建一个并行计算框架
            ForkJoinPool joinPoolTow = new ForkJoinPool();
            Map.Entry<String, List<StructureReportEntity>> mapData = entry1.next();

            //获取map中的value
            List<StructureReportEntity> list1 = mapData.getValue();
            //获取map中的key
            String mapKey = mapData.getKey();
            //开始对数据进行处理
            Future<List<StructureReportEntity>> joinTask = joinPoolTow.submit(new BasistReportPCPlusMobUtil(list1, 0, list1.size()));
            try {
                //得到处理后的数据
                entities = joinTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            //关闭并行计算框架
            joinPoolTow.shutdown();
            listMapDay.put(mapKey, entities);
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
        }

        return reportName;
    }

    //对单个的数据进行计算百分比
    private Map<String, StructureReportEntity> percentage(Map<String, StructureReportEntity> map) {
        DecimalFormat df = new DecimalFormat("#.00");
        for (Map.Entry<String, StructureReportEntity> voEntity : map.entrySet()) {
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
                BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format(voEntity.getValue().getPcClick().doubleValue() / voEntity.getValue().getPcImpression().doubleValue())));
                BigDecimal big = new BigDecimal(100);
                double divide = ctrBig.multiply(big).doubleValue();
                voEntity.getValue().setPcCtr(divide);
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
    private Map<String, List<StructureReportEntity>> percentageList(Map<String, List<StructureReportEntity>> mapDay) {
        DecimalFormat df = new DecimalFormat("#.00");
        for (Map.Entry<String, List<StructureReportEntity>> voEntity : mapDay.entrySet()) {
            for (StructureReportEntity entity : voEntity.getValue()) {
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
        return mapDay;
    }

    //统计当前查出数据的总和（分标示）
    private List<StructureReportEntity> getCountStructure(Map<String, List<StructureReportEntity>> dateMap) {
        StructureReportEntity objEntity = new StructureReportEntity();
        for (Map.Entry<String, List<StructureReportEntity>> voEntity : dateMap.entrySet()) {
            for (StructureReportEntity entity : voEntity.getValue()) {
                objEntity.setMobileImpression(((objEntity.getMobileImpression() == null) ? 0 : objEntity.getMobileImpression()) + ((entity.getMobileImpression() == null) ? 0 : entity.getMobileImpression()));
                objEntity.setMobileClick(((objEntity.getMobileClick() == null) ? 0 : objEntity.getMobileClick()) + ((entity.getMobileClick() == null) ? 0 : entity.getMobileClick()));
                objEntity.setMobileCost(((objEntity.getMobileCost() == null) ? BigDecimal.valueOf(0) : objEntity.getMobileCost()).add((entity.getMobileCost() == null) ? BigDecimal.valueOf(0) : entity.getMobileCost()));
                objEntity.setMobileConversion(((objEntity.getMobileConversion() == null) ? 0 : objEntity.getMobileConversion()) + ((entity.getMobileConversion() == null) ? 0 : entity.getMobileConversion()));

                objEntity.setPcImpression(((objEntity.getPcImpression() == null) ? 0 : objEntity.getPcImpression()) + ((entity.getPcImpression() == null) ? 0 : entity.getPcImpression()));
                objEntity.setPcClick(((objEntity.getPcClick() == null) ? 0 : objEntity.getPcClick()) + ((entity.getPcClick() == null) ? 0 : entity.getPcClick()));
                objEntity.setPcCost(((objEntity.getPcCost() == null) ? BigDecimal.valueOf(0) : objEntity.getPcCost()).add((entity.getPcCost() == null) ? BigDecimal.valueOf(0) : entity.getPcCost()));
                objEntity.setPcConversion(((objEntity.getPcConversion() == null) ? 0 : objEntity.getMobileConversion()) + ((entity.getPcConversion() == null) ? 0 : entity.getPcConversion()));
            }
        }
        List<StructureReportEntity> entityList = new ArrayList<>();
        entityList.add(objEntity);
        return entityList;
    }

    //获取饼状图需要数据
    private List<StructureReportEntity> getPieData(List<StructureReportEntity> returnList, int terminal, String sortField, String sort) {
        for (StructureReportEntity entity : returnList) {
            entity.setOrderBy(sort);
            entity.setTerminal(terminal);
        }
        Collections.sort(returnList);
        int i = 0;
        List<StructureReportEntity> entityList = new ArrayList<>();
        StructureReportEntity entity = new StructureReportEntity();
        for (StructureReportEntity entityReport : returnList) {
            StructureReportEntity entity1 = new StructureReportEntity();
            if (i < 10) {
                i++;
                switch (sortField) {
                    case "impr":
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
                    case "click":
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
                    case "cost":
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
                    case "conv":
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
                    case "impr":
                        //如果用户只查询手机
                        if (terminal == 2) {
                            entity.setMobileImpression(((entity.getMobileImpression() == null) ? 0 : entity.getMobileImpression()) + entityReport.getMobileImpression());
                        } else {
                            entity.setPcImpression(((entity.getPcImpression() == null) ? 0 : entity.getPcImpression()) + entityReport.getPcImpression());
                        }
                        break;
                    case "click":
                        if (terminal == 2) {
                            entity.setMobileClick(((entity.getMobileClick() == null) ? 0 : entity.getMobileClick()) + entityReport.getMobileClick());
                        } else {
                            entity.setPcClick(((entity.getPcClick() == null) ? 0 : entity.getPcClick()) + entityReport.getPcClick());
                        }
                        break;
                    case "cost":
                        if (terminal == 2) {
                            entity.setMobileCost(((entity.getMobileCost() == null) ? BigDecimal.valueOf(0) : entity.getMobileCost()).add(entityReport.getMobileCost()));
                        } else {
                            entity.setPcCost(((entity.getPcCost() == null) ? BigDecimal.valueOf(0) : entity.getPcCost()).add(entityReport.getPcCost()));
                        }
                        break;
                    case "conv":
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
    private List<StructureReportEntity> dataSort(List<StructureReportEntity> returnList, String sort, int terminal, int start, int limit) {
        for (StructureReportEntity entity : returnList) {
            entity.setOrderBy(sort);
            entity.setTerminal(terminal);
        }
        List<StructureReportEntity> finalList = new ArrayList<>();
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
    private List<StructureReportEntity> getLineChart(Map<String, List<StructureReportEntity>> listMap, int dive) {
        List<StructureReportEntity> entityList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Map.Entry<String, List<StructureReportEntity>> voEntity : listMap.entrySet()) {
            StructureReportEntity e = new StructureReportEntity();
            List<StructureReportEntity> entity = voEntity.getValue();

            for (StructureReportEntity entity1 : entity) {
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

}
