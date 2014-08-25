package com.perfect.service.impl;

import com.perfect.dao.BasisReportDAO;
import com.perfect.entity.AccountReportResponse;
import com.perfect.entity.StructureReportEntity;
import com.perfect.mongodb.utils.DateUtils;
import com.perfect.service.BasisReportService;
import com.perfect.utils.reportUtil.AccountReportPCPlusMobUtil;
import com.perfect.utils.reportUtil.BasisReportDefaultUtil;
import com.perfect.utils.reportUtil.BasistReportPCPlusMobUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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

    /**
     * 获取单元报告
     *
     * @param terminal     推广设备 0、全部  1、PC端 2、移动端
     * @param date         时间
     * @param categoryTime 分类时间  0、默认 1、分日 2、分周 3、分月
     * @return
     */
    public Map<String, List<StructureReportEntity>> getReportDate(String[] date, int terminal, int categoryTime, int reportType, int reportPageNumber) {
        List<StructureReportEntity> objectsList = new ArrayList<>();

        switch (categoryTime) {
            //默认时间生成报告
            case 0:
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
                try {
                    //开始对数据处理
                    Future<Map<String, StructureReportEntity>> joinTask = joinPool.submit(new BasisReportDefaultUtil(objectsList, 0, objectsList.size(), reportType));
                    //得到处理结果
                    map = joinTask.get();
                    DecimalFormat df = new DecimalFormat("#.00");
                    //对相应的数据进行计算百分比
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
                            voEntity.getValue().setMobileCpc(0.00);
                        } else {
                            voEntity.getValue().setMobileCpc(Double.parseDouble(df.format(voEntity.getValue().getMobileCost() / voEntity.getValue().getMobileClick().doubleValue())));
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
                            voEntity.getValue().setPcCpc(0.00);
                        } else {
                            voEntity.getValue().setPcCpc(Double.parseDouble(df.format(voEntity.getValue().getPcCost() / voEntity.getValue().getPcClick().doubleValue())));
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                //关闭并行计算框架
                joinPool.shutdown();
                List<StructureReportEntity> list = new ArrayList<>(map.values());
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
                    listMap.put(date[0] + " 至 " + date[date.length - 1], returnList);
                    listMap.put("rows", dateMap0);
                    return listMap;
                } else {
                    listMap.put(date[0] + " 至 " + date[date.length - 1], list);
                    listMap.put("rows", dateMap0);
                    return listMap;
                }
                //分日生成报告
            case 1:
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

                if (terminal == 0) {
                    Map<String, List<StructureReportEntity>> listMap1 = terminalAll(mapDay);
                    listMap1.put("rows", dateMap);
                    return listMap1;
                } else {
                    DecimalFormat df = new DecimalFormat("#.00");
                    //对相应的数据进行计算百分比
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
                                entity.setMobileCpc(0.00);
                            } else {
                                entity.setMobileCpc(Double.parseDouble(df.format(entity.getMobileCost() / entity.getMobileClick().doubleValue())));
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
                                entity.setPcCpc(0.00);
                            } else {
                                entity.setPcCpc(Double.parseDouble(df.format(entity.getPcCost() / entity.getPcClick().doubleValue())));
                            }
                        }
                    }
                }
                mapDay.put("rows", dateMap);
                return mapDay;
            //分周生成报告
            case 2:
                List<StructureReportEntity> dateMap2 = new ArrayList<>();

                Map<String, StructureReportEntity> reportEntities = new HashMap<>();
                Map<String, List<StructureReportEntity>> stringListMap = new HashMap<>();
                Map<String, List<StructureReportEntity>> endListMap = new HashMap<>();
                //如果用户选择的时间范围大于7天
                if (date.length > 7) {
                    int i = 0;
                    int endNumber = 0;
                    int endStep = endStep = date.length < 7?1:date.length%7 == 0?date.length:(date.length / 7) + 1;
                    for (int x = 0; x < endStep; x++) {
                        StructureReportEntity dateObject2 = new StructureReportEntity();
                        String[] strings = new String[2];
                        for (i = endNumber; i < endNumber + 7; i++) {
                            List<StructureReportEntity> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType));
                            if (object.size() != 0) {
                                objectsList.addAll(object);
                            }
                            if (i == endNumber) {
                                strings[0] = date[i];
                            } else if (i == endNumber + 6) {
                                strings[1] = date[i];
                            }
                        }
                        stringListMap.put(strings[endNumber] + " 至 " + strings[i], objectsList);
                        dateObject2.setDate(strings[endNumber] + " 至 " + strings[i]);
                        dateMap2.add(dateObject2);
                        endNumber = i;
                    }

                    for (Iterator<Map.Entry<String, List<StructureReportEntity>>> entry1 = stringListMap.entrySet().iterator(); entry1.hasNext(); ) {
                        //创建一个并行计算框架
                        ForkJoinPool joinPoolTow = new ForkJoinPool();
                        //获取map中的value
                        List<StructureReportEntity> list1 = entry1.next().getValue();
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

                        DecimalFormat df = new DecimalFormat("#.00");
                        //对相应的数据进行计算百分比
                        for (Map.Entry<String, StructureReportEntity> voEntity : reportEntities.entrySet()) {
                            if (voEntity.getValue().getMobileImpression() == null || voEntity.getValue().getMobileImpression() == 0) {
                                voEntity.getValue().setMobileCtr(0.00);
                            } else {
                                BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format(voEntity.getValue().getMobileClick().doubleValue() / voEntity.getValue().getMobileImpression().doubleValue())));
                                BigDecimal big = new BigDecimal(100);
                                double divide = ctrBig.multiply(big).doubleValue();
                                voEntity.getValue().setMobileCtr(divide);
                            }
                            if (voEntity.getValue().getMobileClick() == null || voEntity.getValue().getMobileClick() == 0) {
                                voEntity.getValue().setMobileCpc(0.00);
                            } else {
                                voEntity.getValue().setMobileCpc(Double.parseDouble(df.format(voEntity.getValue().getMobileCost() / voEntity.getValue().getMobileClick().doubleValue())));
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
                                voEntity.getValue().setPcCpc(0.00);
                            } else {
                                voEntity.getValue().setPcCpc(Double.parseDouble(df.format(voEntity.getValue().getPcCost() / voEntity.getValue().getPcClick().doubleValue())));
                            }
                        }
                        //关闭并行计算框架
                        joinPoolTow.shutdown();
                        List<StructureReportEntity> arrayList = new ArrayList<>(reportEntities.values());
                        endListMap.put(entry1.next().getKey(), arrayList);
                    }
                    if (terminal == 0) {
                        Map<String, List<StructureReportEntity>> listMap1 = terminalAll(endListMap);
                        listMap1.put("rows", dateMap2);
                        return listMap1;
                    }
                    endListMap.put("rows", dateMap2);
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
                    DecimalFormat df = new DecimalFormat("#.00");
                    //对相应的数据进行计算百分比
                    for (Map.Entry<String, StructureReportEntity> voEntity : reportEntities.entrySet()) {
                        if (voEntity.getValue().getMobileImpression() == null || voEntity.getValue().getMobileImpression() == 0) {
                            voEntity.getValue().setMobileCtr(0.00);
                        } else {
                            BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format(voEntity.getValue().getMobileClick().doubleValue() / voEntity.getValue().getMobileImpression().doubleValue())));
                            BigDecimal big = new BigDecimal(100);
                            double divide = ctrBig.multiply(big).doubleValue();
                            voEntity.getValue().setMobileCtr(divide);
                        }
                        if (voEntity.getValue().getMobileClick() == null || voEntity.getValue().getMobileClick() == 0) {
                            voEntity.getValue().setMobileCpc(0.00);
                        } else {
                            voEntity.getValue().setMobileCpc(Double.parseDouble(df.format(voEntity.getValue().getMobileCost() / voEntity.getValue().getMobileClick().doubleValue())));
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
                            voEntity.getValue().setPcCpc(0.00);
                        } else {
                            voEntity.getValue().setPcCpc(Double.parseDouble(df.format(voEntity.getValue().getPcCost() / voEntity.getValue().getPcClick().doubleValue())));
                        }
                    }
                    //关闭并行计算框架
                    joinPoolTow.shutdown();
                    List<StructureReportEntity> entityList = new ArrayList<>(reportEntities.values());
                    endListMap.put(date[0] + " 至 " + date[date.length - 1], entityList);
                    if (terminal == 0) {
                        Map<String, List<StructureReportEntity>> listMap1 = terminalAll(endListMap);
                        listMap1.put("rows", dateMap2else);
                        return listMap1;
                    }
                    endListMap.put("rows", dateMap2else);
                    return endListMap;
                }
                //分月生成报告
            case 3:
                List<StructureReportEntity> dateMap3 = new ArrayList<>();

                Map<String, StructureReportEntity> reportEntities1 = new HashMap<>();
                Map<String, List<StructureReportEntity>> stringListMap1 = new HashMap<>();
                Map<String, List<StructureReportEntity>> endListMap1 = new HashMap<>();
                //如果用户选择的时间范围大于30天
                if (date.length > 30) {
                    int i = 0;
                    int endNumber = 0;
                    int endStep = date.length < 30?1:date.length%30 == 0?date.length:(date.length / 30) + 1;
                    for (int x = 0; x < endStep; x++) {
                        StructureReportEntity dateObject3 = new StructureReportEntity();
                        String[] strings = new String[2];
                        for (i = endNumber; i < endNumber + 30; i++) {
                            List<StructureReportEntity> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType));
                            if (object.size() != 0) {
                                objectsList.addAll(object);
                            }
                            if (i == endNumber) {
                                strings[0] = date[i];
                            } else if (i == endNumber + 6) {
                                strings[1] = date[i];
                            }
                        }
                        stringListMap1.put(strings[endNumber] + " 至 " + strings[i], objectsList);
                        dateObject3.setDate(strings[endNumber] + " 至 " + strings[i]);
                        dateMap3.add(dateObject3);
                        endNumber = i;
                    }

                    for (Iterator<Map.Entry<String, List<StructureReportEntity>>> entry1 = stringListMap1.entrySet().iterator(); entry1.hasNext(); ) {
                        //创建一个并行计算框架
                        ForkJoinPool joinPoolTow = new ForkJoinPool();
                        //获取map中的value
                        List<StructureReportEntity> list1 = entry1.next().getValue();
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
                        DecimalFormat df = new DecimalFormat("#.00");
                        //对相应的数据进行计算百分比
                        for (Map.Entry<String, StructureReportEntity> voEntity : reportEntities1.entrySet()) {
                            if (voEntity.getValue().getMobileImpression() == null || voEntity.getValue().getMobileImpression() == 0) {
                                voEntity.getValue().setMobileCtr(0.00);
                            } else {
                                BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format(voEntity.getValue().getMobileClick().doubleValue() / voEntity.getValue().getMobileImpression().doubleValue())));
                                BigDecimal big = new BigDecimal(100);
                                double divide = ctrBig.multiply(big).doubleValue();
                                voEntity.getValue().setMobileCtr(divide);
                            }
                            if (voEntity.getValue().getMobileClick() == null || voEntity.getValue().getMobileClick() == 0) {
                                voEntity.getValue().setMobileCpc(0.00);
                            } else {
                                voEntity.getValue().setMobileCpc(Double.parseDouble(df.format(voEntity.getValue().getMobileCost() / voEntity.getValue().getMobileClick().doubleValue())));
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
                                voEntity.getValue().setPcCpc(0.00);
                            } else {
                                voEntity.getValue().setPcCpc(Double.parseDouble(df.format(voEntity.getValue().getPcCost() / voEntity.getValue().getPcClick().doubleValue())));
                            }
                        }
                        //关闭并行计算框架
                        joinPoolTow.shutdown();
                        List<StructureReportEntity> entityList = new ArrayList<>(reportEntities1.values());
                        endListMap1.put(entry1.next().getKey(), entityList);
                    }
                    if (terminal == 0) {
                        Map<String, List<StructureReportEntity>> listMap1 = terminalAll(endListMap1);
                        listMap1.put("rows", dateMap3);
                        return listMap1;
                    }
                    endListMap1.put("rows", dateMap3);
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
                    for (Map.Entry<String, StructureReportEntity> voEntity : reportEntities1.entrySet()) {
                        if (voEntity.getValue().getMobileImpression() == null || voEntity.getValue().getMobileImpression() == 0) {
                            voEntity.getValue().setMobileCtr(0.00);
                        } else {
                            BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format(voEntity.getValue().getMobileClick().doubleValue() / voEntity.getValue().getMobileImpression().doubleValue())));
                            BigDecimal big = new BigDecimal(100);
                            double divide = ctrBig.multiply(big).doubleValue();
                            voEntity.getValue().setMobileCtr(divide);
                        }
                        if (voEntity.getValue().getMobileClick() == null || voEntity.getValue().getMobileClick() == 0) {
                            voEntity.getValue().setMobileCpc(0.00);
                        } else {
                            voEntity.getValue().setMobileCpc(Double.parseDouble(df.format(voEntity.getValue().getMobileCost() / voEntity.getValue().getMobileClick().doubleValue())));
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
                            voEntity.getValue().setPcCpc(0.00);
                        } else {
                            voEntity.getValue().setPcCpc(Double.parseDouble(df.format(voEntity.getValue().getPcCost() / voEntity.getValue().getPcClick().doubleValue())));
                        }
                    }
                    //关闭并行计算框架
                    joinPoolTow.shutdown();
                    List<StructureReportEntity> entityList = new ArrayList<>(reportEntities1.values());
                    endListMap1.put(date[0] + " 至 " + date[date.length - 1], entityList);
                    if (terminal == 0) {
                        Map<String, List<StructureReportEntity>> listMap1 = terminalAll(endListMap1);
                        listMap1.put("rows", dateMap3else);
                        return listMap1;
                    }
                    endListMap1.put("rows", dateMap3else);
                    return endListMap1;
                }
        }
        return null;
    }

    @Override
    public Map<String, List<AccountReportResponse>> getAccountAll(int Sorted, String fieldName) {

        List<AccountReportResponse> reportEntities = basisReportDAO.getAccountReport(Sorted, fieldName);

        Map<String, List<AccountReportResponse>> map = new HashMap<>();
        List<AccountReportResponse> entities = new ArrayList<>();
        ForkJoinPool joinPoolTow = new ForkJoinPool();
        //开始对数据进行处理
        Future<List<AccountReportResponse>> joinTask = joinPoolTow.submit(new AccountReportPCPlusMobUtil(reportEntities, 0, reportEntities.size()));

        try {
            entities = joinTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //关闭并行计算框架
        joinPoolTow.shutdown();

        map.put("rows", entities);
        return map;
    }

    @Override
    public Map<String, List<Object>> getAccountDateVS(Date startDate, Date endDate, Date startDate1, Date endDate1, int dateType,int devices) {
        Date[] dateOne = getDateProcessing(startDate, endDate);
        Date[] dateTow = getDateProcessing(startDate1, endDate1);


        switch (dateType) {
            case 0:
                Map<String, List<Object>> retrunMap = new HashMap<>();
                List<Object> objectListDate = new ArrayList<>();
                List<AccountReportResponse> listOne = basisReportDAO.getAccountReport(dateOne[0], dateOne[1]);
                List<AccountReportResponse> listTow = basisReportDAO.getAccountReport(dateTow[0], dateTow[1]);
                //获取数据
                Map<String, List<AccountReportResponse>> responseMapOne =  getUserDataPro(listOne,dateOne[0], dateOne[1]);
                Map<String, List<AccountReportResponse>> responseMapTow =  getUserDataPro(listTow,dateTow[0], dateTow[1]);

                //如果要求是全部数据
                if(devices == 0){
                    Map<String, List<AccountReportResponse>> responseMapDevicesOne = getPcPlusMobileDate(responseMapOne);
                    Map<String, List<AccountReportResponse>> responseMapDevicesTow = getPcPlusMobileDate(responseMapTow);
                    List<Object> objectList = new ArrayList<>();
                    objectList.add(responseMapDevicesOne);
                    objectList.add(responseMapDevicesTow);
                    objectListDate.add(dateOne[0]+" 至 "+dateOne[1]);
                    objectListDate.add(dateTow[0]+" 至 "+dateTow[1]);
                    retrunMap.put("rows",objectList);
                    retrunMap.put("date",objectListDate);

                    return retrunMap;
                }

                //计算点击率、平均价格
                Map<String, List<AccountReportResponse>> responseMapAverageOne = getAverage(responseMapOne);
                Map<String, List<AccountReportResponse>> responseMapAverageTow = getAverage(responseMapTow);

                List<Object> objectList = new ArrayList<>();
                objectList.add(responseMapAverageOne);
                objectList.add(responseMapAverageTow);
                objectListDate.add(dateOne[0]+" 至 "+dateOne[1]);
                objectListDate.add(dateTow[0]+" 至 "+dateTow[1]);
                retrunMap.put("rows",objectList);
                retrunMap.put("date",objectListDate);

                return retrunMap;
            case 1:
                Map<String, List<Object>> retrunMap1 = new HashMap<>();
                Map<String, List<AccountReportResponse>> responseMapOne1 = new HashMap<>();
                Map<String, List<AccountReportResponse>> responseMapTow1 = new HashMap<>();
                List<AccountReportResponse> listOne1 = basisReportDAO.getAccountReport(dateOne[0], dateOne[1]);
                List<AccountReportResponse> listTow1 = basisReportDAO.getAccountReport(dateTow[0], dateTow[1]);
                List<Object> objectListDateOne1 = new ArrayList<>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                for (AccountReportResponse listEnd:listOne1){
                    List<AccountReportResponse> list = new ArrayList<>();
                    list.add(listEnd);
                    responseMapOne1.put(dateFormat.format(listEnd.getDate()),list);
                }
                for (AccountReportResponse listEnd:listTow1){
                    List<AccountReportResponse> list = new ArrayList<>();
                    list.add(listEnd);
                    responseMapTow1.put(dateFormat.format(listEnd.getDate()),list);
                }
                if(devices == 0){
                    Map<String, List<AccountReportResponse>> responseMapDevicesOne = getPcPlusMobileDate(null);
                    Map<String, List<AccountReportResponse>> responseMapDevicesTow = getPcPlusMobileDate(null);
                    List<Object> objectList1 = new ArrayList<>();
                    objectList1.add(responseMapOne1);
                    objectList1.add(responseMapTow1);
                    objectListDateOne1.add(dateOne[0]+" 至 "+dateOne[1]);/***********************/
                    objectListDateOne1.add(dateTow[0]+" 至 "+dateTow[1]);
                    retrunMap1.put("rows",objectList1);
                    retrunMap1.put("date",objectListDateOne1);

                    return retrunMap1;
                }

                List<Object> objectList1 = new ArrayList<>();
                objectList1.add(responseMapOne1);
                objectList1.add(responseMapTow1);
                objectListDateOne1.add(dateOne[0]+" 至 "+dateOne[1]);/***********************/
                objectListDateOne1.add(dateTow[0]+" 至 "+dateTow[1]);
                retrunMap1.put("rows",objectList1);
                retrunMap1.put("date",objectListDateOne1);

                return retrunMap1;
            case 2:

                return null;
            case 3:

                return null;
        }

        return null;
    }

    @Override
    public Map<String,List<StructureReportEntity>> getKeywordReport(Long[] id, String startDate, String endDate, int devices) {
        List<String> newDate = DateUtils.getPeriod(startDate, endDate);
        Map<String,List<StructureReportEntity>> listMap = new HashMap<>();
        if(newDate.size() > 0){
            for (int i = 0 ; i< newDate.size() ; i++){
                List<StructureReportEntity> returnStructure = basisReportDAO.getKeywordReport(id,newDate.get(i)+"-keyword");
                if (devices == 0){
                    ForkJoinPool joinPoolTow = new ForkJoinPool();
                    //开始对数据进行处理
                    Future<List<StructureReportEntity>> joinTask = joinPoolTow.submit(new BasistReportPCPlusMobUtil(returnStructure, 0, returnStructure.size()));
                    try {
                        listMap.put(newDate.get(i),joinTask.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }else{
                    listMap.put(newDate.get(i),returnStructure);
                }
            }
        }
        return listMap;
    }


    /**
     * 账户 计算所有数据  包含PC端 、 移动端
     * @param responseMap
     * @return
     */
    public Map<String, List<AccountReportResponse>> getPcPlusMobileDate(Map<String, List<AccountReportResponse>> responseMap){
        DecimalFormat df = new DecimalFormat("#.00");
        for (Map.Entry<String, List<AccountReportResponse>> voEntity : responseMap.entrySet()) {
            for (AccountReportResponse response : voEntity.getValue()){
                response.setPcImpression(response.getPcImpression() + ((response.getMobileImpression() == null)?0:response.getMobileImpression()));
                response.setPcConversion(response.getPcConversion() + ((response.getMobileConversion() == null)?0:response.getMobileConversion()));
                response.setPcClick(response.getPcClick() + ((response.getMobileClick() == null)?0:response.getMobileClick()));
                response.setPcCost(response.getPcCost() + ((response.getMobileCost() == null)?0:response.getMobileCost()));
                //计算点击率
                if(((response.getMobileImpression() == null) ? 0 : response.getMobileImpression()) == 0){
                    response.setPcCtr(0.00);
                    if(((response.getPcImpression() == null) ? 0 : response.getPcImpression()) == 0){
                        response.setPcCtr(0.00);
                    }else{
                        BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format((response.getPcClick() / response.getPcImpression()))));
                        BigDecimal big = new BigDecimal(100);
                        double divide = ctrBig.multiply(big).doubleValue();
                        response.setPcCtr(divide);
                    }
                }else{
                    double newNumber = Double.parseDouble(df.format((response.getPcClick() + ((response.getMobileClick() == null) ? 0 : response.getMobileClick()))/(response.getMobileImpression() + ((response.getMobileImpression() == null) ? 0 : response.getMobileImpression()))));
                    BigDecimal ctrBig = new BigDecimal(newNumber);
                    BigDecimal big = new BigDecimal(100);
                    double divide = ctrBig.multiply(big).doubleValue();
                    response.setPcCtr(divide);
                }
                //计算平均点击价格
                if(((response.getMobileClick() == null) ? 0 : response.getMobileClick()) == 0){
                    if(((response.getPcClick() == null) ? 0 : response.getPcClick()) == 0){
                        response.setPcCpc(0d);
                    }else{
                        response.setPcCpc(Double.parseDouble(df.format((response.getPcCost()/response.getPcClick()))));
                    }
                }else{
                    double newNumber =  Double.parseDouble(df.format((response.getPcCost() + ((response.getMobileCost() == null) ? 0 : response.getMobileCost()))/(response.getPcClick() + ((response.getMobileClick() == null) ? 0 : response.getMobileClick()))));
                    response.setPcCpc(newNumber);
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
    private Map<String, List<AccountReportResponse>> getAverage(Map<String, List<AccountReportResponse>> responseMap){
        DecimalFormat df = new DecimalFormat("#.00");
        for (Map.Entry<String, List<AccountReportResponse>> voEntity : responseMap.entrySet()) {
            for (AccountReportResponse response : voEntity.getValue()){
                if (response.getMobileImpression() == null || response.getMobileImpression() == 0) {
                    response.setMobileCtr(0.00);
                } else {
                    BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format(response.getMobileClick().doubleValue() / response.getMobileImpression().doubleValue())));
                    BigDecimal big = new BigDecimal(100);
                    double divide = ctrBig.multiply(big).doubleValue();
                    response.setMobileCtr(divide);
                }
                if (response.getMobileClick() == null || response.getMobileClick() == 0) {
                    response.setMobileCpc(0.00);
                } else {
                    response.setMobileCpc(Double.parseDouble(df.format(response.getMobileCost() / response.getMobileClick().doubleValue())));
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
                    response.setPcCpc(0.00);
                } else {
                    response.setPcCpc(Double.parseDouble(df.format(response.getPcCost() / response.getPcClick().doubleValue())));
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
    private Map<String, List<AccountReportResponse>> getUserDataPro(List<AccountReportResponse> responses, Date date1, Date date2) {
        Map<String, List<AccountReportResponse>> responseMap = new HashMap<>();
        List<AccountReportResponse> responseList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (AccountReportResponse enit : responses) {
            if (responseList.size() > 0) {
                responseList.get(0).setPcImpression(responseList.get(0).getPcImpression() + ((enit.getPcImpression() == null)?0:enit.getPcImpression()));
                responseList.get(0).setPcClick(responseList.get(0).getPcClick() + ((enit.getPcClick() == null) ? 0 : enit.getPcClick()));
                responseList.get(0).setPcCost(responseList.get(0).getPcCost() + ((enit.getPcCost() == null) ? 0 : enit.getPcCost()));
                responseList.get(0).setPcConversion(responseList.get(0).getPcConversion() + ((enit.getPcConversion() == null) ? 0 : enit.getPcConversion()));
                responseList.get(0).setPcCtr(0d);
                responseList.get(0).setPcCpc(0d);
                responseList.get(0).setMobileImpression(responseList.get(0).getMobileImpression() + ((enit.getMobileImpression() == null) ? 0 : enit.getMobileImpression()));
                responseList.get(0).setMobileClick(responseList.get(0).getMobileClick() + ((enit.getMobileClick() == null) ? 0 : enit.getMobileClick()));
                responseList.get(0).setMobileCost(responseList.get(0).getMobileCost() + ((enit.getMobileCost() == null) ? 0 : enit.getMobileCost()));
                responseList.get(0).setMobileConversion(responseList.get(0).getMobileConversion() + ((enit.getMobileConversion() == null) ? 0 : enit.getMobileConversion()));
                responseList.get(0).setMobileCtr(0d);
                responseList.get(0).setMobileCpc(0d);
            } else {
                enit.setDateRep(dateFormat.format(enit.getDate()));
                responseList.add(enit);
            }
        }
        responseMap.put(dateFormat.format(date1)+ "至" +dateFormat.format(date2),responseList);
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
        if (startDate == null) {
            dateOne = endDate;
        }
        if (endDate == null) {
            dateTow = startDate;
        }
        if (startDate == null && endDate == null) {
            dateOne = new Date();
            dateTow = new Date();
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
/*******************API*****************************/

}
