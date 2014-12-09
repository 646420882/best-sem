package com.perfect.service.impl;


import com.google.common.primitives.Bytes;
import com.perfect.dao.account.AccountAnalyzeDAO;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.dto.keyword.KeywordRealDTO;
import com.perfect.service.PerformanceService;
import com.perfect.utils.report.Performance;
import org.springframework.stereotype.Service;

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

/**
 * Created by SubDong on 2014/7/25.
 */
@Service("performanceService")
public class PerformanceServiceImpl implements PerformanceService {

    @Resource
    private AccountAnalyzeDAO accountAnalyzeDAO;


    private static final String DEFAULT_DELIMITER = ",";
    private static final String DEFAULT_END = "\r\n";
    private static final byte commonCSVHead[] = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    /**
     * 账户表现中的分日表现数据
     *
     * @param userTable
     * @param date
     * @return
     */
    @Override
    public List<KeywordRealDTO> performance(String userTable, String[] date) {

        //首字母替换成大写
        String first = userTable.substring(0, 1).toUpperCase();
        String rest = userTable.substring(1, userTable.length());
        String newStr = new StringBuffer(first).append(rest).toString();

        Map<Long, KeywordRealDTO> map = new HashMap<>();
        List<KeywordRealDTO> analyzeEntities;
        List<KeywordRealDTO> entities = new ArrayList<>();
        for (int i = 0; i < date.length; i++) {
            analyzeEntities = accountAnalyzeDAO.performance(newStr + "-KeywordRealTimeData-log-" + date[i]);
            entities.addAll(analyzeEntities);
        }
        if (entities.size() != 0) {
            ForkJoinPool joinPool = new ForkJoinPool();
            try {
                Future<Map<Long, KeywordRealDTO>> joinTask = joinPool.submit(new Performance(entities, 0, entities.size()));
                map = joinTask.get();
                DecimalFormat df = new DecimalFormat("#.00");
                for (Map.Entry<Long, KeywordRealDTO> entry : map.entrySet()) {
                    if (entry.getValue().getImpression() == 0) {
                        entry.getValue().setCtr(0.00);
                    } else {
                        entry.getValue().setCtr(Double.parseDouble(df.format(entry.getValue().getClick().doubleValue() / entry.getValue().getImpression().doubleValue())));
                    }
                    if (entry.getValue().getClick() == 0) {
                        entry.getValue().setCpc(0.00);
                    } else {
                        entry.getValue().setCpc(Double.parseDouble(df.format(entry.getValue().getCost() / entry.getValue().getClick().doubleValue())));
                    }

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            joinPool.shutdown();
        }
        List<KeywordRealDTO> list = new ArrayList<>(map.values());
        return list;
    }

    /**
     * 获取账户表现中的所有数据
     *
     * @return
     */
    @Override
    public List<AccountReportDTO> performanceUser(Date startDate, Date endDate, String sorted, int limit, int startPer, List<String> date) {

        List<AccountReportDTO> listUser = new ArrayList<>(accountAnalyzeDAO.performaneUser(startDate, endDate));
        DecimalFormat df = new DecimalFormat("#.0000");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (AccountReportDTO list : listUser) {
            list.setPcImpression(list.getPcImpression() + ((list.getMobileImpression() == null) ? 0 : list.getMobileImpression()));
            list.setPcConversion(list.getPcConversion() + ((list.getMobileConversion() == null) ? 0 : list.getMobileConversion()));
            list.setPcClick(list.getPcClick() + ((list.getMobileClick() == null) ? 0 : list.getMobileClick()));
            list.setPcCost(list.getPcCost().add((list.getMobileCost() == null) ? BigDecimal.valueOf(0) : list.getMobileCost()));
            //计算点击率
            if (((list.getPcImpression() == null) ? 0 : list.getPcImpression()) == 0) {
                list.setPcCtr(0.00);
            } else {
                BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format((list.getPcClick().doubleValue() / list.getPcImpression().doubleValue()))));
                BigDecimal big = new BigDecimal(100);
                double divide = ctrBig.multiply(big).doubleValue();
                list.setPcCtr(divide);
            }
            //计算平均点击价格
            if (((list.getPcClick() == null) ? 0 : list.getPcClick()) == 0) {
                list.setPcCpc(BigDecimal.valueOf(0));
            } else {
                list.setPcCpc(list.getPcCost().divide(BigDecimal.valueOf(list.getPcClick()), 2, BigDecimal.ROUND_UP));
            }
            list.setMobileImpression(null);
            list.setMobileClick(null);
            list.setMobileConversion(null);
            list.setMobileCost(null);
            list.setMobileCpc(null);
            list.setMobileCpm(null);
            list.setMobileCtr(null);
        }
        int jueds = -1;
        for (String s : date) {
            for (AccountReportDTO accountReportDTO : listUser) {
                String d = dateFormat.format(accountReportDTO.getDate());
                if (s.equals(d)) {
                    jueds = 1;
                    break;
                } else {
                    jueds = -1;
                }
            }
            if (jueds == -1) {
                AccountReportDTO entity = new AccountReportDTO();
                try {
                    entity.setDate(dateFormat.parse(s));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                entity.setPcImpression(0);
                entity.setPcClick(0);
                entity.setPcConversion(0.00);
                entity.setPcCpc(BigDecimal.valueOf(0.00));
                entity.setPcCpm(BigDecimal.valueOf(0.00));
                entity.setPcCost(BigDecimal.valueOf(0.00));
                entity.setPcCtr(0.00);
                listUser.add(entity);
            }
        }
        for (AccountReportDTO accountReport : listUser) {
            accountReport.setOrderBy(sorted);
            accountReport.setCount(date.size());
        }
        Collections.sort(listUser);
        List<AccountReportDTO> entities = new ArrayList<>();
        for (int i = startPer; i < limit; i++) {
            if (i >= listUser.size()) break;
            entities.add(listUser.get(i));
        }
        return entities;
    }

    /**
     * 获取账户表现中的曲线图所有数据
     *
     * @return
     */
    @Override
    public List<AccountReportDTO> performanceCurve(Date startDate, Date endDate, List<String> date) {

        List<AccountReportDTO> listUser = accountAnalyzeDAO.performaneCurve(startDate, endDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("#.0000");
        for (AccountReportDTO list : listUser) {
            list.setPcImpression(list.getPcImpression() + ((list.getMobileImpression() == null) ? 0 : list.getMobileImpression()));
            list.setPcConversion(list.getPcConversion() + ((list.getMobileConversion() == null) ? 0 : list.getMobileConversion()));
            list.setPcClick(list.getPcClick() + ((list.getMobileClick() == null) ? 0 : list.getMobileClick()));
            list.setPcCost(list.getPcCost().add((list.getMobileCost() == null) ? BigDecimal.valueOf(0) : list.getMobileCost()));
            //计算点击率
            if (((list.getPcImpression() == null) ? 0 : list.getPcImpression()) == 0) {
                list.setPcCtr(0.00);
            } else {
                BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format((list.getPcClick().doubleValue() / list.getPcImpression().doubleValue()))));
                BigDecimal big = new BigDecimal(100);
                double divide = ctrBig.multiply(big).doubleValue();
                list.setPcCtr(divide);
            }

            //计算平均点击价格
            if (((list.getPcClick() == null) ? 0 : list.getPcClick()) == 0) {
                list.setPcCpc(BigDecimal.valueOf(0));
            } else {
                list.setPcCpc(list.getPcCost().divide(BigDecimal.valueOf(list.getPcClick()), 2, BigDecimal.ROUND_UP));
            }

            list.setMobileImpression(null);
            list.setMobileClick(null);
            list.setMobileConversion(null);
            list.setMobileCost(null);
            list.setMobileCpc(null);
            list.setMobileCpm(null);
            list.setMobileCtr(null);
        }
        int jueds = -1;
        for (String s : date) {
            for (AccountReportDTO accountReportEntity : listUser) {
                String d = dateFormat.format(accountReportEntity.getDate());
                if (s.equals(d)) {
                    jueds = 1;
                    break;
                } else {
                    jueds = -1;
                }
            }
            if (jueds == -1) {
                AccountReportDTO entity = new AccountReportDTO();
                try {
                    entity.setDate(dateFormat.parse(s));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                entity.setPcImpression(0);
                entity.setPcClick(0);
                entity.setPcConversion(0.00);
                entity.setPcCpc(BigDecimal.valueOf(0.00));
                entity.setPcCpm(BigDecimal.valueOf(0.00));
                entity.setPcCost(BigDecimal.valueOf(0.00));
                entity.setPcCtr(0.00);
                listUser.add(entity);
            }
        }
        for (AccountReportDTO accountReport : listUser) {
            accountReport.setOrderBy("1");
            accountReport.setCount(date.size());
        }
        Collections.sort(listUser);
        return listUser;
    }

    @Override
    public void downAccountCSV(OutputStream os) {

        List<AccountReportDTO> listUser = accountAnalyzeDAO.downAccountCSV();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("#.0000");
        List<Date> date = new ArrayList<>();
        for (AccountReportDTO list : listUser) {
            list.setPcImpression(list.getPcImpression() + ((list.getMobileImpression() == null) ? 0 : list.getMobileImpression()));
            list.setPcConversion(list.getPcConversion() + ((list.getMobileConversion() == null) ? 0 : list.getMobileConversion()));
            list.setPcClick(list.getPcClick() + ((list.getMobileClick() == null) ? 0 : list.getMobileClick()));
            list.setPcCost(list.getPcCost().add((list.getMobileCost() == null) ? BigDecimal.valueOf(0) : list.getMobileCost()));
            //计算点击率
            if (((list.getPcImpression() == null) ? 0 : list.getPcImpression()) == 0) {
                list.setPcCtr(0.00);
            } else {
                double ctrBig = Double.parseDouble(df.format((list.getPcClick().doubleValue() / list.getPcImpression().doubleValue())));

                double divide = ctrBig * 100;
                list.setPcCtr(divide);
            }

            //计算平均点击价格
            if (((list.getPcClick() == null) ? 0 : list.getPcClick()) == 0) {
                list.setPcCpc(BigDecimal.valueOf(0));
            } else {
                list.setPcCpc(list.getPcCost().divide(BigDecimal.valueOf(list.getPcClick()), 2, BigDecimal.ROUND_UP));
            }

            list.setMobileImpression(null);
            list.setMobileClick(null);
            list.setMobileConversion(null);
            list.setMobileCost(null);
            list.setMobileCpc(null);
            list.setMobileCpm(null);
            list.setMobileCtr(null);
            date.add(list.getDate());
        }
        for (int i = 0; i < date.size(); i++) {
            if (i > 0) {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(date.get(i));
                cal2.setTime(date.get(i - 1));
                long e = (cal1.getTimeInMillis() - cal2.getTimeInMillis()) / (1000 * 60 * 60 * 24);
                long dataMis = cal2.getTimeInMillis();
                for (int s = 0; s < e; s++) {
                    dataMis = dataMis + (1000 * 60 * 60 * 24);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(dataMis);
                    date.add(calendar.getTime());
                }
            }
        }
        List<String> dateNow = new ArrayList<>();
        for (Date date1 : date) {
            dateNow.add(dateFormat.format(date1));
        }
        int jueds = -1;
        for (String s : dateNow) {
            for (AccountReportDTO accountReportEntity : listUser) {
                String d = dateFormat.format(accountReportEntity.getDate());
                if (s.equals(d)) {
                    jueds = 1;
                    break;
                } else {
                    jueds = -1;
                }
            }
            if (jueds == -1) {
                AccountReportDTO entity = new AccountReportDTO();
                try {
                    entity.setDate(dateFormat.parse(s));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                entity.setPcImpression(0);
                entity.setPcClick(0);
                entity.setPcConversion(0.00);
                entity.setPcCpc(BigDecimal.valueOf(0.00));
                entity.setPcCpm(BigDecimal.valueOf(0.00));
                entity.setPcCost(BigDecimal.valueOf(0.00));
                entity.setPcCtr(0.00);
                listUser.add(entity);
            }
        }
        for (AccountReportDTO reportEntity : listUser) {
            reportEntity.setOrderBy("1");
        }
        Collections.sort(listUser);
        try {
            os.write(Bytes.concat(commonCSVHead, ("时间" +
                    DEFAULT_DELIMITER + "展现量" +
                    DEFAULT_DELIMITER + "点击量" +
                    DEFAULT_DELIMITER + "消费" +
                    DEFAULT_DELIMITER + "点击率" +
                    DEFAULT_DELIMITER + "平均点击价格" +
                    DEFAULT_DELIMITER + "转化(页面)" +
                    DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
            for (AccountReportDTO entity : listUser) {
                os.write(Bytes.concat(commonCSVHead, (dateFormat.format(entity.getDate()) +
                        DEFAULT_DELIMITER + entity.getPcImpression() +
                        DEFAULT_DELIMITER + entity.getPcClick() +
                        DEFAULT_DELIMITER + entity.getPcCost() +
                        DEFAULT_DELIMITER + entity.getPcCtr() * 100 / 100 + "%" +
                        DEFAULT_DELIMITER + entity.getPcCpc() +
                        DEFAULT_DELIMITER + entity.getPcConversion() +
                        DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


