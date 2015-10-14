package com.perfect.service.impl;

import com.google.common.primitives.Bytes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.perfect.dao.report.BasisReportDAO;
import com.perfect.dto.StructureReportDTO;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.dto.keyword.SearchwordReportDTO;
import com.perfect.service.BasisReportDownService;
import com.perfect.utils.DateUtils;
import com.perfect.utils.redis.JRedisUtils;
import com.perfect.utils.report.AccountReportStatisticsUtil;
import com.perfect.utils.report.BasisReportCalculateUtil;
import com.perfect.utils.report.BasisReportDataHandleUtil;
import com.perfect.utils.report.ReportDownUtil;
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

/**
 * Created by subdong on 15-9-18.
 */
@Service("basisReportDownService")
public class BasisReportDownServiceImpl implements BasisReportDownService {

    @Resource
    private BasisReportDAO basisReportDAO;

    private static final String DEFAULT_DELIMITER = ",";
    private static final String DEFAULT_END = "\r\n";
    private static final byte commonCSVHead[] = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

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
                                ((entity.getAccount() == null) ? "" : DEFAULT_DELIMITER + entity.getAccount().replace(",", "，")) +
                                ((entity.getCampaignName() == null) ? "" : DEFAULT_DELIMITER + entity.getCampaignName().replace(",", "，")) +
                                ((entity.getAdgroupName() == null) ? "" : DEFAULT_DELIMITER + entity.getAdgroupName().replace(",", "，")) +
                                ((entity.getKeywordName() == null) ? "" : DEFAULT_DELIMITER + entity.getKeywordName().replace(",", "，")) +
                                ((entity.getCreativeTitle() == null) ? "" : DEFAULT_DELIMITER + entity.getCreativeTitle().replace(",", "，")) +
                                ((entity.getRegionName() == null) ? "" : DEFAULT_DELIMITER + entity.getRegionName().replace(",", "，")) +
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
                                ((entity.getAccount() == null) ? "" : DEFAULT_DELIMITER + entity.getAccount().replace(",", "，")) +
                                ((entity.getCampaignName() == null) ? "" : DEFAULT_DELIMITER + entity.getCampaignName().replace(",", "，")) +
                                ((entity.getAdgroupName() == null) ? "" : DEFAULT_DELIMITER + entity.getAdgroupName().replace(",", "，")) +
                                ((entity.getKeywordName() == null) ? "" : DEFAULT_DELIMITER + entity.getKeywordName().replace(",", "，")) +
                                ((entity.getCreativeTitle() == null) ? "" : DEFAULT_DELIMITER + entity.getCreativeTitle().replace(",", "，")) +
                                ((entity.getRegionName() == null) ? "" : DEFAULT_DELIMITER + entity.getRegionName().replace(",", "，")) +
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
                                    ((entity.getAccount() == null) ? "" : DEFAULT_DELIMITER + entity.getAccount().replace(",", "，")) +
                                    ((entity.getCampaignName() == null) ? "" : DEFAULT_DELIMITER + entity.getCampaignName().replace(",", "，")) +
                                    ((entity.getAdgroupName() == null) ? "" : DEFAULT_DELIMITER + entity.getAdgroupName().replace(",", "，")) +
                                    ((entity.getKeywordName() == null) ? "" : DEFAULT_DELIMITER + entity.getKeywordName().replace(",", "，")) +
                                    ((entity.getCreativeTitle() == null) ? "" : DEFAULT_DELIMITER + entity.getCreativeTitle().replace(",", "，")) +
                                    ((entity.getRegionName() == null) ? "" : DEFAULT_DELIMITER + entity.getRegionName().replace(",", "，")) +
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
                                    ((entity.getAccount() == null) ? "" : DEFAULT_DELIMITER + entity.getAccount().replace(",", "，")) +
                                    ((entity.getCampaignName() == null) ? "" : DEFAULT_DELIMITER + entity.getCampaignName().replace(",", "，")) +
                                    ((entity.getAdgroupName() == null) ? "" : DEFAULT_DELIMITER + entity.getAdgroupName().replace(",", "，")) +
                                    ((entity.getKeywordName() == null) ? "" : DEFAULT_DELIMITER + entity.getKeywordName().replace(",", "，")) +
                                    ((entity.getCreativeTitle() == null) ? "" : DEFAULT_DELIMITER + entity.getCreativeTitle().replace(",", "，")) +
                                    ((entity.getRegionName() == null) ? "" : DEFAULT_DELIMITER + entity.getRegionName().replace(",", "，")) +
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
        Date[] dateOne = BasisReportDataHandleUtil.getDateProcessing(startDate, endDate);
        Date[] dateTow = BasisReportDataHandleUtil.getDateProcessing(startDate1, endDate1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        switch (dateType) {
            case 0:
                //获取数据
                List<AccountReportDTO> listOne = new ArrayList<>(basisReportDAO.getAccountReport(dateOne[0], dateOne[1]));
                //获取用户统计数据
                List<Object> userProAll = new ArrayList<>();
                List<AccountReportDTO> userPro = AccountReportStatisticsUtil.getUserPro(listOne);
                List<AccountReportDTO> userProAcerage = AccountReportStatisticsUtil.getAveragePro(userPro);
                userProAll.addAll(userProAcerage);
                //统计数据
                Map<String, List<AccountReportDTO>> responseMapOne = BasisReportDataHandleUtil.getUserDataPro(listOne, dateOne[0], dateOne[1]);

                //如果要求是全部数据
                if (devices == 0) {
                    Map<String, List<AccountReportDTO>> responseMapDevicesOne = BasisReportDataHandleUtil.getPcPlusMobileDate(responseMapOne);

                    //计算点击率、平均价格
                    Map<String, List<AccountReportDTO>> responseMapAverageOne = BasisReportCalculateUtil.getAverage(responseMapDevicesOne);
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
                    Map<String, List<AccountReportDTO>> responseMapAverageOne = BasisReportCalculateUtil.getAverage(responseMapOne);

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

                List<AccountReportDTO> listOne1 = new ArrayList<>(basisReportDAO.getAccountReport(dateOne[0], dateOne[1]));
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
                    Map<String, List<AccountReportDTO>> responseMapDevicesOne = BasisReportDataHandleUtil.getPcPlusMobileDate(responseMapOne1);
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
                    Map<String, List<AccountReportDTO>> responseMapOne3 = BasisReportDataHandleUtil.getUserDataPro(listDateOne, listDateOne.get(0).getDate(), listDateOne.get(listDateOne.size() - 1).getDate());
                    if (devices == 0) {
                        Map<String, List<AccountReportDTO>> responseMapDevicesOne = BasisReportDataHandleUtil.getPcPlusMobileDate(responseMapOne3);

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
                            responseMapOne4 = BasisReportDataHandleUtil.getUserDataPro(listDateOne1, dateFormat.parse(dateListStringt.get(numbert)), dateFormat.parse(dateListStringt.get(is)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        if (devices == 0) {
                            Map<String, List<AccountReportDTO>> responseMapDevicesOne = BasisReportDataHandleUtil.getPcPlusMobileDate(responseMapOne4);
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
                        responseMapOne4 = BasisReportDataHandleUtil.getUserDataPro(listDateOne1, dateFormat.parse(dateListStringt.get(numbert)), dateFormat.parse(dateListStringt.get((numbert + index) - (im - 1))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    if (devices == 0) {
                        Map<String, List<AccountReportDTO>> responseMapDevicesOne = BasisReportDataHandleUtil.getPcPlusMobileDate(responseMapOne4);
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

    @Override
    public void downSeachKeyWordReportCSV(OutputStream os, List<SearchwordReportDTO> dtos) {
        try {
            os.write(Bytes.concat(commonCSVHead, (ReportDownUtil.getBetyHead())));
            dtos.forEach(e -> {
                try {
                    os.write(Bytes.concat(commonCSVHead, (
                            e.getDate() + DEFAULT_DELIMITER +
                                    e.getCampaignName() + DEFAULT_DELIMITER +
                                    e.getAdgroupName() + DEFAULT_DELIMITER +
                                    e.getSearchEngine() + DEFAULT_DELIMITER +
                                    e.getClick() + DEFAULT_DELIMITER +
                                    e.getImpression() + DEFAULT_DELIMITER +
                                    e.getClickRate() + DEFAULT_DELIMITER +
                                    e.getSearchWord() + DEFAULT_DELIMITER +
                                    e.getKeyword() + DEFAULT_DELIMITER +
                                    e.getParseExtent() + DEFAULT_END).getBytes()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
