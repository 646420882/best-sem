package com.perfect.utils.report;

import com.perfect.dto.StructureReportDTO;
import com.perfect.dto.account.AccountReportDTO;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import static com.perfect.commons.constants.ReportConstants.*;
import static com.perfect.commons.constants.ReportConstants.REPORT_CONV;

/**
 * Created by subdong on 15-9-18.
 */
public class BasisReportCalculateUtil {

    /**
     * 对单个的数据进行计算百分比
     * @param map
     * @return
     */
    public static Map<String, StructureReportDTO> percentage(Map<String, StructureReportDTO> map) {
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


    /**
     * 曲线图数据计算
     * @param listMap
     * @param dive
     * @return
     */
    public static List<StructureReportDTO> getLineChart(Map<String, List<StructureReportDTO>> listMap, int dive) {
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
     * 获取饼状图需要数据
     * @param returnList
     * @param terminal
     * @param sortField
     * @param sort
     * @return
     */
    public static List<StructureReportDTO> getPieData(List<StructureReportDTO> returnList, int terminal, String sortField, String sort) {
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

    /**
     * 计算合计数据
     *
     * @param list
     * @return
     */
    public static List<StructureReportDTO> dataALL(List<StructureReportDTO> list) {
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


    /**
     * 数据排序
     * @param returnList
     * @param sort
     * @param terminal
     * @param start
     * @param limit
     * @return
     */
    public static List<StructureReportDTO> dataSort(List<StructureReportDTO> returnList, String sort, int terminal, int start, int limit) {
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

    /**
     * 统计当前查出数据的总和（分标示）
     * @param dateMap
     * @return
     */
    public static List<StructureReportDTO> getCountStructure(Map<String, List<StructureReportDTO>> dateMap) {
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


    /**
     * 对一个集合中的数据进行计算百分比
     * @param mapDay
     * @param userName
     * @param reportType
     * @return
     */
    public static Map<String, List<StructureReportDTO>> percentageList(Map<String, List<StructureReportDTO>> mapDay, String userName, int reportType) {
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

    /**
     * 账户计算点击率 平均价格等
     */
    public static Map<String, List<AccountReportDTO>> getAverage(Map<String, List<AccountReportDTO>> responseMap) {
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
     * 获取表结尾单词
     * @param reportType
     * @return
     */
    public static String getTableType(int reportType) {
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
}
