package com.perfect.utils.report;

import com.perfect.entity.StructureReportEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

/**
 * Created by SubDong on 2014/9/22.
 */
public class ReportStatisticsUtil extends RecursiveTask<Map<String, StructureReportEntity>> {
    private final int threshold = 100;

    private int endNumber;
    private int begin;

    private List<StructureReportEntity> objectList;

    public ReportStatisticsUtil(List<StructureReportEntity> objects, int begin, int endNumber) {
        this.objectList = objects;
        this.endNumber = endNumber;
        this.begin = begin;
    }

    @Override
    protected Map<String, StructureReportEntity> compute() {
        Map<String, StructureReportEntity> map = new HashMap<>();
        StructureReportEntity voEntity = new StructureReportEntity();
        if ((endNumber - begin) < threshold) {
            for (int i = begin; i < endNumber; i++) {
                voEntity.setMobileClick(((voEntity.getMobileClick() == null) ? 0 : voEntity.getMobileClick()) + ((objectList.get(i).getMobileClick() == null) ? 0 : objectList.get(i).getMobileClick()));
                voEntity.setMobileConversion((voEntity.getMobileConversion() == null ? 0 : voEntity.getMobileConversion()) + ((objectList.get(i).getMobileConversion() == null) ? 0 : objectList.get(i).getMobileClick()));
                voEntity.setMobileCost((voEntity.getMobileCost() == null ? BigDecimal.ZERO : voEntity.getMobileCost()).add((objectList.get(i).getMobileCost() == null) ? BigDecimal.ZERO : objectList.get(i).getMobileCost()));
                voEntity.setMobileCtr(0d);
                voEntity.setMobileCpc(BigDecimal.valueOf(0));
                voEntity.setMobileImpression((voEntity.getMobileImpression() == null ? 0 : voEntity.getMobileImpression()) + ((objectList.get(i).getMobileImpression() == null) ? 0 : objectList.get(i).getMobileImpression()));
                voEntity.setPcClick((voEntity.getPcClick() == null ? 0 : voEntity.getPcClick()) + ((objectList.get(i).getPcClick() == null) ? 0 : objectList.get(i).getPcClick()));
                voEntity.setPcConversion((voEntity.getPcConversion() == null ? 0 : voEntity.getPcConversion()) + ((objectList.get(i).getPcConversion() == null) ? 0 : objectList.get(i).getPcConversion()));
                voEntity.setPcCost((voEntity.getPcCost() == null ? BigDecimal.valueOf(0) : voEntity.getPcCost()).add((objectList.get(i).getPcCost() == null) ? BigDecimal.ZERO : objectList.get(i).getPcCost()));
                voEntity.setPcCtr(0d);
                voEntity.setPcCpc(BigDecimal.valueOf(0));
                voEntity.setPcImpression((voEntity.getPcImpression() == null ? 0 : voEntity.getPcImpression()) + ((objectList.get(i).getPcImpression() == null) ? 0 : objectList.get(i).getPcImpression()));
            }
            map.put("Statist", voEntity);
            return map;
        } else {
            int midpoint = (begin + endNumber) / 2;
            ReportStatisticsUtil left = new ReportStatisticsUtil(objectList, begin, midpoint);
            ReportStatisticsUtil right = new ReportStatisticsUtil(objectList, midpoint, endNumber);
            invokeAll(left, right);
            try {
                Map<String, StructureReportEntity> leftMap = left.get();
                Map<String, StructureReportEntity> rightMap = right.get();
                map.putAll(merge(leftMap, rightMap));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return map;
        }
    }


    public Map<String, StructureReportEntity> merge(Map<String, StructureReportEntity> leftMap, Map<String, StructureReportEntity> rightMap) {
        Map<String, StructureReportEntity> dataMap = new HashMap<>();


        for (Iterator<Map.Entry<String, StructureReportEntity>> entry1 = leftMap.entrySet().iterator(); entry1.hasNext(); ) {

            StructureReportEntity mapValue1 = entry1.next().getValue();

            for (Iterator<Map.Entry<String, StructureReportEntity>> entry2 = rightMap.entrySet().iterator(); entry2.hasNext(); ) {

                StructureReportEntity mapValue2 = entry2.next().getValue();
                mapValue1.setMobileClick((mapValue1.getMobileClick() == null ? 0 : mapValue1.getMobileClick()) + (mapValue2.getMobileClick() == null ? 0 : mapValue2.getMobileClick()));
                mapValue1.setMobileConversion((mapValue1.getMobileConversion() == null ? 0 : mapValue1.getMobileConversion()) + (mapValue2.getMobileConversion() == null ? 0 : mapValue2.getMobileConversion()));
                mapValue1.setMobileCost((mapValue1.getMobileCost() == null ? BigDecimal.valueOf(0) : mapValue1.getMobileCost()).add(mapValue2.getMobileCost() == null ? BigDecimal.valueOf(0) : mapValue2.getMobileCost()));
                mapValue1.setMobileCtr(0d);
                mapValue1.setMobileCtr(0d);
                mapValue1.setMobileImpression((mapValue1.getMobileImpression() == null ? 0 : mapValue1.getMobileImpression()) + (mapValue2.getMobileImpression() == null ? 0 : mapValue2.getMobileImpression()));
                mapValue1.setPcClick((mapValue1.getPcClick() == null ? 0 : mapValue1.getPcClick()) + (mapValue2.getPcClick() == null ? 0 : mapValue2.getPcClick()));
                mapValue1.setPcConversion((mapValue1.getPcConversion() == null ? 0 : mapValue1.getPcConversion()) + (mapValue2.getPcConversion() == null ? 0 : mapValue2.getPcConversion()));
                mapValue1.setPcCost((mapValue1.getPcCost() == null ? BigDecimal.valueOf(0) : mapValue1.getPcCost()).add(mapValue2.getPcCost() == null ? BigDecimal.valueOf(0) : mapValue2.getPcCost()));
                mapValue1.setPcCtr(0d);
                mapValue1.setPcCpc(BigDecimal.valueOf(0));
                mapValue1.setPcImpression((mapValue1.getPcImpression() == null ? 0 : mapValue1.getPcImpression()) + (mapValue2.getPcImpression() == null ? 0 : mapValue2.getPcImpression()));

                dataMap.put("Statist", mapValue1);

                entry1.remove();
                entry2.remove();
                break;
            }
        }
        dataMap.putAll(leftMap);
        dataMap.putAll(rightMap);
        return dataMap;
    }
}
