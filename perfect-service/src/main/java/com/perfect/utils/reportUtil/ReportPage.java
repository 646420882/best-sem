package com.perfect.utils.reportUtil;

import com.perfect.dto.AccountReportDTO;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/9/1.
 */
public class ReportPage {
    public List<Object> getReportPage(Map<String, List<AccountReportDTO>> pageData, int devices, String sortVS, int startVS, int limitVS) {
        List<AccountReportDTO> accountReports = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Map.Entry<String, List<AccountReportDTO>> voEntity : pageData.entrySet()) {
            List<AccountReportDTO> accountReportDTOs = voEntity.getValue();
            for (AccountReportDTO entity : accountReportDTOs) {
                entity.setOrderBy(sortVS);
                entity.setDevices(devices);
                if (entity.getDate() == null) {
                    try {
                        entity.setDate(dateFormat.parse(voEntity.getKey()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (entity.getPcImpression() == null) {
                    entity.setPcImpression(0);
                }
                if (entity.getPcClick() == null) {
                    entity.setPcClick(0);
                }
                if (entity.getPcCost() == null) {
                    entity.setPcCost(BigDecimal.valueOf(0.00));
                }
                if (entity.getPcCpc() == null) {
                    entity.setPcCpc(BigDecimal.valueOf(0.00));
                }
                if (entity.getPcCtr() == null) {
                    entity.setPcCtr(0.00);
                }
                if (entity.getPcConversion() == null) {
                    entity.setPcConversion(0.00);
                }

                if (entity.getMobileImpression() == null) {
                    entity.setMobileImpression(0);
                }
                if (entity.getMobileClick() == null) {
                    entity.setMobileClick(0);
                }
                if (entity.getMobileCost() == null) {
                    entity.setMobileCost(BigDecimal.valueOf(0.00));
                }
                if (entity.getMobileCpc() == null) {
                    entity.setMobileCpc(BigDecimal.valueOf(0.00));
                }
                if (entity.getMobileCtr() == null) {
                    entity.setMobileCtr(0.00);
                }
                if (entity.getMobileConversion() == null) {
                    entity.setMobileConversion(0.00);
                }
                entity.setDateRep(voEntity.getKey());
                entity.setCount(pageData.size());
                accountReports.add(entity);
            }

        }
        Collections.sort(accountReports);
        List<Object> finalList = new ArrayList<>();

        for (int i = startVS; i < limitVS; i++) {
            if (i < accountReports.size()) {
                finalList.add(accountReports.get(i).getDateRep());
            }
        }
        return finalList;
    }

    public List<Object> getReportPageObj(List<Object> pageData, int devices, String sortVS, int startVS, int limitVS) {
        List<AccountReportDTO> accountReports = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        for (Object o : pageData) {
            Map<String, List<AccountReportDTO>> m = (Map<String, List<AccountReportDTO>>) o;
            for (Map.Entry<String, List<AccountReportDTO>> voEntity : m.entrySet()) {
                List<AccountReportDTO> accountReportDTOs = voEntity.getValue();
                for (AccountReportDTO entity : accountReportDTOs) {
                    entity.setOrderBy(sortVS);
                    entity.setDevices(devices);
                    if (entity.getDate() == null) {
                        try {
                            entity.setDate(dateFormat.parse(voEntity.getKey()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if (entity.getPcImpression() == null) {
                        entity.setPcImpression(0);
                    }
                    if (entity.getPcClick() == null) {
                        entity.setPcClick(0);
                    }
                    if (entity.getPcCost() == null) {
                        entity.setPcCost(BigDecimal.valueOf(0.00));
                    }
                    if (entity.getPcCpc() == null) {
                        entity.setPcCpc(BigDecimal.valueOf(0.00));
                    }
                    if (entity.getPcCtr() == null) {
                        entity.setPcCtr(0.00);
                    }
                    if (entity.getPcConversion() == null) {
                        entity.setPcConversion(0.00);
                    }

                    if (entity.getMobileImpression() == null) {
                        entity.setMobileImpression(0);
                    }
                    if (entity.getMobileClick() == null) {
                        entity.setMobileClick(0);
                    }
                    if (entity.getMobileCost() == null) {
                        entity.setMobileCost(BigDecimal.valueOf(0.00));
                    }
                    if (entity.getMobileCpc() == null) {
                        entity.setMobileCpc(BigDecimal.valueOf(0.00));
                    }
                    if (entity.getMobileCtr() == null) {
                        entity.setMobileCtr(0.00);
                    }
                    if (entity.getMobileConversion() == null) {
                        entity.setMobileConversion(0.00);
                    }
                    entity.setDateRep(voEntity.getKey());
                    entity.setCount(pageData.size());
                    accountReports.add(entity);
                }
            }
        }

        Collections.sort(accountReports);
        List<Object> finalList = new ArrayList<>();
        for (int i = startVS; i < limitVS; i++) {
            if (i < accountReports.size()) {
                finalList.add(accountReports.get(i).getDateRep());
            }
        }
        return finalList;
    }
}
