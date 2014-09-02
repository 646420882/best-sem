package com.perfect.utils.reportUtil;

import com.perfect.entity.StructureReportEntity;

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
    public List<StructureReportEntity> getReportDetailsPage(Map<String, List<StructureReportEntity>> pageData,int devices,String sortVS,int startVS,int limitVS){
        List<StructureReportEntity> accountReports = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int count=0;
        for (Map.Entry<String, List<StructureReportEntity>> voEntity : pageData.entrySet()){
            List<StructureReportEntity> StructureReportEntitys = voEntity.getValue();
            for (StructureReportEntity entity : StructureReportEntitys) {
                entity.setOrderBy(sortVS);
                entity.setTerminal(devices);
                if(entity.getDate() == null){
                    try {
                        entity.setDateRep(dateFormat.parse(voEntity.getKey()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(entity.getPcImpression() == null){entity.setPcImpression(0);}
                if(entity.getPcClick() == null){entity.setPcClick(0);}
                if(entity.getPcCost() == null){entity.setPcCost(0.00);}
                if(entity.getPcCpc() == null){entity.setPcCpc(0.00);}
                if(entity.getPcCtr() == null){entity.setPcCtr(0.00);}
                if(entity.getPcConversion() == null){entity.setPcConversion(0.00);}

                if(entity.getMobileImpression() == null){entity.setMobileImpression(0);}
                if(entity.getMobileClick() == null){entity.setMobileClick(0);}
                if(entity.getMobileCost() == null){entity.setMobileCost(0.00);}
                if(entity.getMobileCpc() == null){entity.setMobileCpc(0.00);}
                if(entity.getMobileCtr() == null){entity.setMobileCtr(0.00);}
                if(entity.getMobileConversion() == null){entity.setMobileConversion(0.00);}
                entity.setDate(voEntity.getKey());
                entity.setCount(pageData.size());
                accountReports.add(entity);
                count++;
            }

        }
        Collections.sort(accountReports);
        List<StructureReportEntity> finalList = new ArrayList<>();

            for (int i = startVS; i <= limitVS; i++) {
                if(i<accountReports.size()){
                    accountReports.get(i).setCount(count);
                    finalList.add(accountReports.get(i));
                }
            }
        return finalList;
    }

    public List<StructureReportEntity> getReportDetailsPageObj(List<StructureReportEntity> pageData,int devices,String sortVS,int startVS,int limitVS,String data){
        List<StructureReportEntity> accountReports = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

                for (StructureReportEntity entity : pageData) {
                    entity.setOrderBy(sortVS);
                    entity.setTerminal(devices);

                    if(entity.getPcImpression() == null){entity.setPcImpression(0);}
                    if(entity.getPcClick() == null){entity.setPcClick(0);}
                    if(entity.getPcCost() == null){entity.setPcCost(0.00);}
                    if(entity.getPcCpc() == null){entity.setPcCpc(0.00);}
                    if(entity.getPcCtr() == null){entity.setPcCtr(0.00);}
                    if(entity.getPcConversion() == null){entity.setPcConversion(0.00);}

                    if(entity.getMobileImpression() == null){entity.setMobileImpression(0);}
                    if(entity.getMobileClick() == null){entity.setMobileClick(0);}
                    if(entity.getMobileCost() == null){entity.setMobileCost(0.00);}
                    if(entity.getMobileCpc() == null){entity.setMobileCpc(0.00);}
                    if(entity.getMobileCtr() == null){entity.setMobileCtr(0.00);}
                    if(entity.getMobileConversion() == null){entity.setMobileConversion(0.00);}
                    entity.setDate(data);
                    entity.setCount(pageData.size());
                    accountReports.add(entity);

        }

        Collections.sort(accountReports);
        List<StructureReportEntity> finalList = new ArrayList<>();
            for (int i = startVS; i <= limitVS; i++) {
                if(i<accountReports.size()){
                finalList.add(accountReports.get(i));
                }
            }
        return finalList;
    }
}
