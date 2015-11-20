package com.perfect.service.impl;

import com.perfect.dao.report.BasisReportDAO;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.service.AccountManageService;
import com.perfect.service.BasisReportUCService;
import com.perfect.utils.DateUtils;
import com.perfect.utils.report.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.perfect.commons.constants.ReportConstants.REPORT_ROWS;
import static com.perfect.commons.constants.ReportConstants.REPORT_STATISTICS;

/**
 * Created by subdong on 15-9-18.
 */
@Service("basisReportUCService")
public class BasisReportUCServiceImpl implements BasisReportUCService {

    @Resource
    private BasisReportDAO basisReportDAO;

    @Override
    public Map<String, List<Object>> getAccountDateVS(Date startDate, Date endDate, Date startDate1, Date endDate1, int dateType, int devices, int compare, String sortVS, int startVS, int limitVS) {
        Date[] dateOne = BasisReportDataHandleUtil.getDateProcessing(startDate, endDate);
        Date[] dateTow = BasisReportDataHandleUtil.getDateProcessing(startDate1, endDate1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        switch (dateType) {
            case 0:
                //默认
                Map<String, List<Object>> retrunMap = new HashMap<>();
                List<Object> objectListDate1 = new ArrayList<>();
                List<Object> objectListDate2 = new ArrayList<>();
                //获取数据
                List<AccountReportDTO> listOne = new ArrayList<>(basisReportDAO.getAccountReport(dateOne[0], dateOne[1]));
                //获取用户统计数据
                List<Object> userProAll = new ArrayList<>();
                List<AccountReportDTO> userPro = AccountReportStatisticsUtil.getUserPro(listOne);
                List<AccountReportDTO> userProAcerage = AccountReportStatisticsUtil.getAveragePro(userPro);
                userProAll.addAll(userProAcerage);
                //统计数据
                Map<String, List<AccountReportDTO>> responseMapOne = BasisReportDataHandleUtil.getUserDataPro(listOne, dateOne[0], dateOne[1]);
                Map<String, List<AccountReportDTO>> responseMapTow = null;
                //比较数据
                if (compare == 1) {
                    List<AccountReportDTO> listTow = new ArrayList<>(basisReportDAO.getAccountReport(dateTow[0], dateTow[1]));
                    responseMapTow = BasisReportDataHandleUtil.getUserDataPro(listTow, dateTow[0], dateTow[1]);
                }
                //如果要求是全部数据
                if (devices == 0) {
                    Map<String, List<AccountReportDTO>> responseMapDevicesOne = BasisReportDataHandleUtil.getPcPlusMobileDate(responseMapOne);
                    List<Object> objectList = new ArrayList<>();

                    //比较数据
                    if (compare == 1) {
                        Map<String, List<AccountReportDTO>> responseMapDevicesTow = BasisReportDataHandleUtil.getPcPlusMobileDate(responseMapTow);
                        objectList.add(responseMapDevicesTow);
                        objectListDate2.add(dateFormat.format(dateTow[0]) + " 至 " + dateFormat.format(dateTow[1]));
                        retrunMap.put("date1", objectListDate2);
                    }
                    //计算点击率、平均价格
                    Map<String, List<AccountReportDTO>> responseMapAverageOne = BasisReportCalculateUtil.getAverage(responseMapDevicesOne);

                    objectList.add(responseMapAverageOne);
                    objectListDate1.add(dateFormat.format(dateOne[0]) + " 至 " + dateFormat.format(dateOne[1]));
                    retrunMap.put(REPORT_STATISTICS, userProAll);
                    retrunMap.put(REPORT_ROWS, objectList);
                    retrunMap.put("date", objectListDate1);

                    return retrunMap;
                }

                //计算点击率、平均价格
                Map<String, List<AccountReportDTO>> responseMapAverageOne = BasisReportCalculateUtil.getAverage(responseMapOne);
                List<Object> objectList = new ArrayList<>();
                objectList.add(responseMapAverageOne);

                //比较数据
                if (compare == 1) {
                    Map<String, List<AccountReportDTO>> responseMapAverageTow = BasisReportCalculateUtil.getAverage(responseMapTow);
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

                List<AccountReportDTO> listOne1 = new ArrayList<>(basisReportDAO.getAccountReport(dateOne[0], dateOne[1]));
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
                    List<AccountReportDTO> listTow1 = new ArrayList<>(basisReportDAO.getAccountReport(dateTow[0], dateTow[1]));
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
                    Map<String, List<AccountReportDTO>> responseMapDevicesOne = BasisReportDataHandleUtil.getPcPlusMobileDate(responseMapOne1);
                    List<Object> objectList1 = new ArrayList<>();
                    objectList1.add(responseMapDevicesOne);

                    //比较数据
                    if (compare == 1) {
                        Map<String, List<AccountReportDTO>> responseMapDevicesTow = BasisReportDataHandleUtil.getPcPlusMobileDate(responseMapTow1);
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

                Map<String, List<AccountReportDTO>> responseMapDevicesOne1 = BasisReportDataHandleUtil.getPcPlusMobileDate(responseMapOne1);

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

                List<AccountReportDTO> listOne2 = new ArrayList<>(basisReportDAO.getAccountReport(dateOne[0], dateOne[1]));

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
                    listTow2 = new ArrayList<>(basisReportDAO.getAccountReport(dateTow[0], dateTow[1]));
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
                    Map<String, List<AccountReportDTO>> responseMapOne3 = BasisReportDataHandleUtil.getUserDataPro(listDateOne, listDateOne.get(0).getDate(), listDateOne.get(listDateOne.size() - 1).getDate());
                    newDateOne = new Date[]{listDateOne.get(0).getDate(), listDateOne.get(listDateOne.size() - 1).getDate()};

                    //比较数据
                    if (compare == 1) {
                        responseMapTow3 = BasisReportDataHandleUtil.getUserDataPro(listDateTow, listDateTow.get(0).getDate(), listDateTow.get(listDateTow.size() - 1).getDate());
                        newDateTow = new Date[]{listDateTow.get(0).getDate(), listDateTow.get(listDateTow.size() - 1).getDate()};
                    }
                    if (devices == 0) {
                        Map<String, List<AccountReportDTO>> responseMapDevicesOne = BasisReportDataHandleUtil.getPcPlusMobileDate(responseMapOne3);

                        //比较数据
                        if (compare == 1) {
                            Map<String, List<AccountReportDTO>> responseMapDevicesTow = BasisReportDataHandleUtil.getPcPlusMobileDate(responseMapTow3);
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
                List<Object> objectListDateOne31 = (List<Object>) new ArrayList<>();
                List<Object> objectListDateTow31 = new ArrayList<>();

                List<AccountReportDTO> listOne3 = new ArrayList<>(basisReportDAO.getAccountReport(dateOne[0], dateOne[1]));
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
                    listTow3 = new ArrayList<>(basisReportDAO.getAccountReport(dateTow[0], dateTow[1]));

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
                            responseMapOne4 = BasisReportDataHandleUtil.getUserDataPro(listDateOne1, dateFormat.parse(dateListStringt.get(numbert)), dateFormat.parse(dateListStringt.get(is)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        if (devices == 0) {
                            Map<String, List<AccountReportDTO>> responseMapDevicesOne = BasisReportDataHandleUtil.getPcPlusMobileDate(responseMapOne4);
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
                        responseMapOne4 = BasisReportDataHandleUtil.getUserDataPro(listDateOne1, dateFormat.parse(dateListStringt.get(numbert)), dateFormat.parse(dateListStringt.get((numbert + index) - (im - 1))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    if (devices == 0) {
                        Map<String, List<AccountReportDTO>> responseMapDevicesOne = BasisReportDataHandleUtil.getPcPlusMobileDate(responseMapOne4);
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
                                responseMapTow4 = BasisReportDataHandleUtil.getUserDataPro(listDateTow1, dateFormat.parse(dateListStringst.get(numbert1)), dateFormat.parse(dateListStringst.get(is)));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if (devices == 0) {
                                //比较数据
                                Map<String, List<AccountReportDTO>> responseMapDevicesTow = BasisReportDataHandleUtil.getPcPlusMobileDate(responseMapTow4);
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
                            responseMapTow4 = BasisReportDataHandleUtil.getUserDataPro(listDateTow1, dateFormat.parse(dateListStringst.get(numbert1)), dateFormat.parse(dateListStringst.get((numbert1 + index1) - (imt - 1))));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (devices == 0) {
                            //比较数据
                            Map<String, List<AccountReportDTO>> responseMapDevicesTow = BasisReportDataHandleUtil.getPcPlusMobileDate(responseMapTow4);
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
}
