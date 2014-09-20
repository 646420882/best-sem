package com.perfect.utils.reportUtil;

import com.perfect.entity.StructureReportEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

/**
 * Created by SubDong on 2014/8/8.
 */
public class BasisReportPCus extends RecursiveTask<Map<String, StructureReportEntity>> {

    private final int threshold = 100;

    private int endNumber;
    private int begin;
    private int terminal;
    private int report;

    private List<StructureReportEntity> objectList;

    public BasisReportPCus(List<StructureReportEntity> objects, int begin, int endNumber, int report) {
        this.objectList = objects;
        this.endNumber = endNumber;
        this.begin = begin;
        this.report = report;
    }

    @Override
    protected Map<String, StructureReportEntity> compute() {
        Map<String, StructureReportEntity> map = new HashMap<>();
        if ((endNumber - begin) < threshold) {
            for (int i = begin; i < endNumber; i++) {
                boolean repotr = false;
                String adgroupName = "";
                switch (report) {
                    case 1:
                        repotr = map.get(objectList.get(i).getAdgroupId().toString()) != null;
                        adgroupName = objectList.get(i).getAdgroupId().toString();
                        break;
                    case 2:
                        repotr = map.get(objectList.get(i).getKeywordId().toString()) != null;
                        adgroupName = objectList.get(i).getKeywordId().toString();
                        break;
                    case 3:
                        repotr = map.get(objectList.get(i).getCreativeId().toString()) != null;
                        adgroupName = objectList.get(i).getCreativeId().toString();
                        break;
                    case 4:
                        repotr = map.get(objectList.get(i).getRegionId().toString()) != null;
                        adgroupName = objectList.get(i).getRegionId().toString();
                        break;
                    case 5:
                        repotr = map.get(objectList.get(i).getCampaignId().toString()) != null;
                        adgroupName = objectList.get(i).getCampaignId().toString();
                        break;
                    case 6:
                        repotr = map.get(objectList.get(i).getAdgroupId().toString()) != null;
                        adgroupName = objectList.get(i).getAdgroupId().toString();
                        break;
                    case 7:
                        repotr = map.get(objectList.get(i).getKeywordId().toString()) != null;
                        adgroupName = objectList.get(i).getKeywordId().toString();
                        break;
                }
                if (repotr) {
                    StructureReportEntity voEntity = map.get(adgroupName);
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
                    switch (report) {
                        case 1:
                            map.put(objectList.get(i).getAdgroupId().toString(), voEntity);
                            break;
                        case 2:
                            map.put(objectList.get(i).getKeywordId().toString(), voEntity);
                            break;
                        case 3:
                            map.put(objectList.get(i).getCreativeId().toString(), voEntity);
                            break;
                        case 4:
                            map.put(objectList.get(i).getRegionId().toString(), voEntity);
                            break;
                        case 5:
                            map.put(objectList.get(i).getCampaignId().toString(), voEntity);
                            break;
                        case 6:
                            map.put(objectList.get(i).getAdgroupId().toString(), voEntity);
                            break;
                        case 7:
                            map.put(objectList.get(i).getKeywordId().toString(), voEntity);
                            break;
                    }

                } else {
                    switch (report) {
                        case 1:
                            map.put(objectList.get(i).getAdgroupId().toString(), objectList.get(i));
                            break;
                        case 2:
                            map.put(objectList.get(i).getKeywordId().toString(), objectList.get(i));
                            break;
                        case 3:
                            map.put(objectList.get(i).getCreativeId().toString(), objectList.get(i));
                            break;
                        case 4:
                            map.put(objectList.get(i).getRegionId().toString(), objectList.get(i));
                            break;
                        case 5:
                            map.put(objectList.get(i).getCampaignId().toString(), objectList.get(i));
                            break;
                        case 6:
                            map.put(objectList.get(i).getAdgroupId().toString(), objectList.get(i));
                            break;
                        case 7:
                            map.put(objectList.get(i).getKeywordId().toString(), objectList.get(i));
                            break;
                    }
                }
            }
            return map;
        } else {
            int midpoint = (begin + endNumber) / 2;
            BasisReportPCus left = new BasisReportPCus(objectList, begin, midpoint, report);
            BasisReportPCus right = new BasisReportPCus(objectList, midpoint, endNumber, report);
            invokeAll(left, right);
            try {
                Map<String, StructureReportEntity> leftMap = left.get();
                Map<String, StructureReportEntity> rightMap = right.get();
                map.putAll(merge(leftMap, rightMap, report));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return map;
        }
    }


    public Map<String, StructureReportEntity> merge(Map<String, StructureReportEntity> leftMap, Map<String, StructureReportEntity> rightMap, int reportType) {
        Map<String, StructureReportEntity> dataMap = new HashMap<>();


        for (Iterator<Map.Entry<String, StructureReportEntity>> entry1 = leftMap.entrySet().iterator(); entry1.hasNext(); ) {

            StructureReportEntity mapValue1 = entry1.next().getValue();

            for (Iterator<Map.Entry<String, StructureReportEntity>> entry2 = rightMap.entrySet().iterator(); entry2.hasNext(); ) {

                StructureReportEntity mapValue2 = entry2.next().getValue();
                boolean repotr = false;
                switch (report) {
                    case 1:
                        repotr = mapValue1.getAdgroupId().equals(mapValue2.getAdgroupId());
                        break;
                    case 2:
                        repotr = mapValue1.getKeywordId().equals(mapValue2.getKeywordId());
                        break;
                    case 3:
                        repotr = mapValue1.getCreativeId().equals(mapValue2.getCreativeId());
                        break;
                    case 4:
                        repotr = mapValue1.getRegionId().equals(mapValue2.getRegionId());
                        break;
                    case 5:
                        repotr = mapValue1.getCampaignId().equals(mapValue2.getCampaignId());
                        break;
                    case 6:
                        repotr = mapValue1.getAdgroupId().equals(mapValue2.getAdgroupId());
                        break;
                    case 7:
                        repotr = mapValue1.getKeywordId().equals(mapValue2.getKeywordId());
                        break;
                }
                if (repotr) {
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
                    switch (report) {
                        case 1:
                            dataMap.put(mapValue1.getAdgroupId().toString(), mapValue1);
                            break;
                        case 2:
                            dataMap.put(mapValue1.getKeywordId().toString(), mapValue1);
                            break;
                        case 3:
                            dataMap.put(mapValue1.getCreativeId().toString(), mapValue1);
                            break;
                        case 4:
                            dataMap.put(mapValue1.getRegionId().toString(), mapValue1);
                            break;
                        case 5:
                            dataMap.put(mapValue1.getCampaignId().toString(), mapValue1);
                            break;
                        case 6:
                            dataMap.put(mapValue1.getAdgroupId().toString(), mapValue1);
                            break;
                        case 7:
                            dataMap.put(mapValue1.getKeywordId().toString(), mapValue1);
                            break;
                    }

                    entry1.remove();
                    entry2.remove();
                    break;
                }
            }
        }
        dataMap.putAll(leftMap);
        dataMap.putAll(rightMap);
        return dataMap;
    }
}