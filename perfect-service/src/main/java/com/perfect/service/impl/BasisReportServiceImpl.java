package com.perfect.service.impl;

import com.perfect.dao.BasisReportDAO;
import com.perfect.entity.AccountReportEntity;
import com.perfect.entity.AccountReportResponse;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import com.perfect.entity.StructureReportEntity;
import com.perfect.service.BasisReportService;
import com.perfect.utils.reportUtil.AccountReportPCPlusMobUtil;
import com.perfect.utils.reportUtil.BasisReportDefaultUtil;
import com.perfect.utils.reportUtil.BasistReportPCPlusMobUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
    public Map<String, List<StructureReportEntity>> getUnitReportDate(String[] date, int terminal, int categoryTime,int reportType,int reportPageNumber) {
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
                    if(object.size() != 0) {
                        objectsList.addAll(object);
                    }
                }
                dateObject0.setDate(date[0]+" 至 "+date[date.length-1]);
                dateMap0.add(dateObject0);
                //创建一个并行计算框架
                ForkJoinPool joinPool = new ForkJoinPool();
                try {
                    //开始对数据处理
                    Future<Map<String, StructureReportEntity>> joinTask = joinPool.submit(new BasisReportDefaultUtil(objectsList, 0, objectsList.size(),reportType));
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
                    listMap.put("rows",dateMap0);
                    return listMap;
                } else {
                    listMap.put(date[0] + " 至 " + date[date.length - 1], list);
                    listMap.put("rows",dateMap0);
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
                    if(object.size() != 0){
                        mapDay.put(date[i], object);
                        dateObject.setDate(date[i]);
                        dateMap.add(dateObject);
                    }
                }

                if (terminal == 0) {
                    Map<String,List<StructureReportEntity>> listMap1 = terminalAll(mapDay);
                    listMap1.put("rows",dateMap);
                    return listMap1;
                }else{
                    DecimalFormat df = new DecimalFormat("#.00");
                    //对相应的数据进行计算百分比
                    for (Map.Entry<String, List<StructureReportEntity>> voEntity : mapDay.entrySet()) {
                        for (StructureReportEntity entity : voEntity.getValue()){
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
                mapDay.put("rows",dateMap);
                return mapDay;
            //分周生成报告
            case 2:
                List<StructureReportEntity> dateMap2 = new ArrayList<>();

                Map<String, StructureReportEntity> reportEntities = new HashMap<>();
                Map<String ,List<StructureReportEntity>> stringListMap = new HashMap<>();
                Map<String ,List<StructureReportEntity>> endListMap = new HashMap<>();
                //如果用户选择的时间范围大于7天
                if(date.length > 7){
                    int i = 0;
                    int endNumber = 0;
                    int endStep = (date.length / 7)+1;
                    for (int x = 0; x < endStep; x++){
                        StructureReportEntity dateObject2 = new StructureReportEntity();
                        String [] strings = new String[2];
                        for (i = endNumber; i < endNumber + 7; i++) {
                            List<StructureReportEntity> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType));
                            if(object.size() != 0) {
                                objectsList.addAll(object);
                            }
                            if(i == endNumber){
                                strings[0] = date[i];
                            }else if(i == endNumber +6){
                                strings[1] = date[i];
                            }
                        }
                        stringListMap.put(strings[endNumber]+" 至 "+strings[i],objectsList);
                        dateObject2.setDate(strings[endNumber]+" 至 "+strings[i]);
                        dateMap2.add(dateObject2);
                        endNumber = i;
                    }

                    for (Iterator<Map.Entry<String, List<StructureReportEntity>>> entry1 = stringListMap.entrySet().iterator(); entry1.hasNext(); ) {
                        //创建一个并行计算框架
                        ForkJoinPool joinPoolTow = new ForkJoinPool();
                        //获取map中的value
                        List<StructureReportEntity> list1 = entry1.next().getValue();
                        //开始对数据进行处理
                        Future<Map<String,StructureReportEntity>> joinTask = joinPoolTow.submit(new BasisReportDefaultUtil(list1, 0, list1.size(),reportType));
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
                    if(terminal == 0){
                        Map<String,List<StructureReportEntity>> listMap1 = terminalAll(endListMap);
                        listMap1.put("rows",dateMap2);
                        return listMap1;
                    }
                    endListMap.put("rows",dateMap2);
                    return endListMap;
                }else{
                    List<StructureReportEntity> dateMap2else = new ArrayList<>();
                    StructureReportEntity dateObject2else = new StructureReportEntity();
                    //如果用户选择的时间范围小于或者等于7天
                    for (int i = 0; i < date.length; i++) {
                        List<StructureReportEntity> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType));
                        if (object.size() != 0) {
                            objectsList.addAll(object);
                        }
                    }
                    dateObject2else.setDate(date[0]+" 至 "+date[date.length-1]);
                    dateMap2else.add(dateObject2else);
                    //创建一个并行计算框架
                    ForkJoinPool joinPoolTow = new ForkJoinPool();
                    //开始对数据进行处理
                    Future<Map<String,StructureReportEntity>> joinTask = joinPoolTow.submit(new BasisReportDefaultUtil(objectsList, 0, objectsList.size(),reportType));
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
                    endListMap.put(date[0]+" 至 "+date[date.length-1], entityList);
                    if(terminal == 0){
                        Map<String,List<StructureReportEntity>> listMap1 = terminalAll(endListMap);
                        listMap1.put("rows",dateMap2else);
                        return listMap1;
                    }
                    endListMap.put("rows",dateMap2else);
                    return endListMap;
                }
            //分月生成报告
            case 3:
                List<StructureReportEntity> dateMap3 = new ArrayList<>();

                Map<String, StructureReportEntity> reportEntities1 = new HashMap<>();
                Map<String ,List<StructureReportEntity>> stringListMap1 = new HashMap<>();
                Map<String ,List<StructureReportEntity>> endListMap1 = new HashMap<>();
                //如果用户选择的时间范围大于30天
                if(date.length > 30){
                    int i = 0;
                    int endNumber = 0;
                    int endStep = (date.length / 30)+1;
                    for (int x = 0; x < endStep; x++){
                        StructureReportEntity dateObject3 = new StructureReportEntity();
                        String [] strings = new String[2];
                        for (i = endNumber; i < endNumber + 30; i++) {
                            List<StructureReportEntity> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType));
                            if(object.size() != 0) {
                                objectsList.addAll(object);
                            }
                            if(i == endNumber){
                                strings[0] = date[i];
                            }else if(i == endNumber +6){
                                strings[1] = date[i];
                            }
                        }
                        stringListMap1.put(strings[endNumber]+" 至 "+strings[i],objectsList);
                        dateObject3.setDate(strings[endNumber]+" 至 "+strings[i]);
                        dateMap3.add(dateObject3);
                        endNumber = i;
                    }

                    for (Iterator<Map.Entry<String, List<StructureReportEntity>>> entry1 = stringListMap1.entrySet().iterator(); entry1.hasNext(); ) {
                        //创建一个并行计算框架
                        ForkJoinPool joinPoolTow = new ForkJoinPool();
                        //获取map中的value
                        List<StructureReportEntity> list1 = entry1.next().getValue();
                        //开始对数据进行处理
                        Future<Map<String,StructureReportEntity>> joinTask = joinPoolTow.submit(new BasisReportDefaultUtil(list1, 0, list1.size(),reportType));
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
                    if(terminal == 0){
                        Map<String,List<StructureReportEntity>> listMap1 = terminalAll(endListMap1);
                        listMap1.put("rows",dateMap3);
                        return listMap1;
                    }
                    endListMap1.put("rows",dateMap3);
                    return endListMap1;
                }else{
                    List<StructureReportEntity> dateMap3else = new ArrayList<>();
                    StructureReportEntity dateObject3else = new StructureReportEntity();
                    //如果用户选择的时间范围小于或者等于30天
                    for (int i = 0; i < date.length; i++) {
                        List<StructureReportEntity> object = basisReportDAO.getUnitReportDate(date[i] + getTableType(reportType));
                        if(object.size() != 0) {
                            objectsList.addAll(object);
                        }
                    }
                    dateObject3else.setDate(date[0]+" 至 "+date[date.length-1]);
                    dateMap3else.add(dateObject3else);
                    //创建一个并行计算框架
                    ForkJoinPool joinPoolTow = new ForkJoinPool();
                    //开始对数据进行处理
                    Future<Map<String,StructureReportEntity>> joinTask = joinPoolTow.submit(new BasisReportDefaultUtil(objectsList, 0, objectsList.size(),reportType));
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
                    endListMap1.put(date[0]+" 至 "+date[date.length-1], entityList);
                    if(terminal == 0){
                        Map<String,List<StructureReportEntity>> listMap1 = terminalAll(endListMap1);
                        listMap1.put("rows",dateMap3else);
                        return listMap1;
                    }
                    endListMap1.put("rows",dateMap3else);
                    return endListMap1;
                }
        }
        return null;
    }

    @Override
    public Map<String,List<AccountReportResponse>> getAccountAll(int Sorted, String fieldName) {

        List<AccountReportResponse> reportEntities = basisReportDAO.getAccountReport(Sorted,fieldName);

        Map<String,List<AccountReportResponse>> map = new HashMap<>();
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








    /**
     * 对全部终端数据进行处理
     * @param entitiesMap
     * @return
     */
    private Map<String ,List<StructureReportEntity>> terminalAll(Map<String,List<StructureReportEntity>> entitiesMap){
            List<StructureReportEntity> entities = new ArrayList<>();
            Map<String,List<StructureReportEntity>> listMapDay = new HashMap<>();
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
     * @param reportType
     * @return
     */
    public String getTableType(int reportType){
        String reportName = "";
        switch (reportType){
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

}
