package com.perfect.utils.report;


import com.perfect.dto.StructureReportDTO;

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
public class ReportPageDetails {
    public List<StructureReportDTO> getReportDetailsPage(Map<String, List<StructureReportDTO>> pageData, int devices, String sortVS, int startVS, int limitVS, int dateType) {
        List<StructureReportDTO> accountReports = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int count = 0;
        for (Map.Entry<String, List<StructureReportDTO>> voEntity : pageData.entrySet()) {
            List<StructureReportDTO> StructureReportEntitys = voEntity.getValue();
            for (StructureReportDTO entity : StructureReportEntitys) {
                entity.setOrderBy(sortVS);
                entity.setTerminal(devices);
                if (entity.getDate() == null) {
                    try {
                        entity.setDateRep(dateFormat.parse(voEntity.getKey().substring(0, 10)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (entity.getAdgroupId() == null) {
                    entity.setAdgroupId(0l);
                }
                if (entity.getCreativeId() == null) {
                    entity.setCreativeId(0l);
                }
                if (entity.getKeywordId() == null) {
                    entity.setKeywordId(0l);
                }
                if (entity.getRegionId() == null) {
                    entity.setRegionId(0l);
                }
                if (entity.getAdgroupName() == null) {
                    entity.setAdgroupName("-");
                }
                if (entity.getCampaignName() == null) {
                    entity.setCampaignName("-");
                }
                if (entity.getKeywordName() == null) {
                    entity.setKeywordName("-");
                }
                if (entity.getRegionName() == null) {
                    entity.setRegionName("-");
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
                entity.setDate(voEntity.getKey());
                if (dateType != 1) {
                    try {
                        entity.setDateRep(dateFormat.parse(voEntity.getKey().substring(0, 10)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                entity.setCount(pageData.size());
                accountReports.add(entity);
                count++;
            }

        }
        Collections.sort(accountReports);
        List<StructureReportDTO> finalList = new ArrayList<>();

        for (int i = startVS; i < limitVS; i++) {
            if (i < accountReports.size()) {
                accountReports.get(i).setCount(count);
                finalList.add(accountReports.get(i));
            }
        }
        return finalList;
    }

    public List<StructureReportDTO> getReportDetailsPageObj(List<StructureReportDTO> pageData, int devices, String sortVS, int startVS, int limitVS, String data) {
        List<StructureReportDTO> accountReports = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        for (StructureReportDTO entity : pageData) {
            entity.setOrderBy(sortVS);
            entity.setTerminal(devices);

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
            entity.setDate(data);
            try {
                entity.setDateRep(dateFormat.parse(data.substring(0, 10)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            entity.setCount(pageData.size());
            accountReports.add(entity);

        }

        Collections.sort(accountReports);
        List<StructureReportDTO> finalList = new ArrayList<>();
        for (int i = startVS; i <= limitVS; i++) {
            if (i < accountReports.size()) {
                finalList.add(accountReports.get(i));
            }
        }
        return finalList;
    }
}
