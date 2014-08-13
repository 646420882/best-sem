package com.perfect.utils.reportUtil;

import com.perfect.entity.StructureReportEntity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

/**
 * Created by SubDong on 2014/8/8.
 */
public class BasisReportDefaultUtil extends RecursiveTask<Map<String, StructureReportEntity>> {

    private final int threshold = 100;

    private int endNumber;
    private int begin;
    private int terminal;

    private List<StructureReportEntity> objectList;

    public BasisReportDefaultUtil(List<StructureReportEntity> objects, int begin, int endNumber){
        this.objectList = objects;
        this.endNumber = endNumber;
        this.begin = begin;
    }

    @Override
    protected Map<String, StructureReportEntity> compute() {
        Map<String , StructureReportEntity> map = new HashMap<>();
        if ((endNumber - begin) < threshold) {
                for (int i = begin; i < endNumber; i++) {
                    if (map.get(objectList.get(i).getAdgroupName()) != null) {
                        String adgroupName = objectList.get(i).getAdgroupName();
                        StructureReportEntity voEntity = map.get(adgroupName);
                        voEntity.setMobileClick(voEntity.getMobileClick() + ((map.get(adgroupName).getMobileClick() == null) ? 0 : map.get(adgroupName).getMobileClick()));
                        voEntity.setMobileConversion(voEntity.getMobileConversion() + ((map.get(adgroupName).getMobileConversion() == null) ? 0 :map.get(adgroupName).getMobileClick()));
                        voEntity.setMobileCost(voEntity.getMobileCost() + ((map.get(adgroupName).getMobileCost() == null) ? 0 : map.get(adgroupName).getMobileCost()));
                        voEntity.setMobileCtr(0d);
                        voEntity.setMobileCpc(0d);
                        voEntity.setMobileImpression(voEntity.getMobileImpression() + ((map.get(adgroupName).getMobileImpression() == null) ? 0 : map.get(adgroupName).getMobileImpression()));
                        voEntity.setPcClick(voEntity.getPcClick() + ((map.get(adgroupName).getPcClick() == null) ? 0 : map.get(adgroupName).getPcClick()));
                        voEntity.setPcConversion(voEntity.getPcConversion() + ((map.get(adgroupName).getPcConversion() == null) ? 0 : map.get(adgroupName).getPcConversion()));
                        voEntity.setPcCost(voEntity.getPcCost() + ((map.get(adgroupName).getPcCost() == null)? 0 : map.get(adgroupName).getPcCost()));
                        voEntity.setPcCtr(0d);
                        voEntity.setPcCpc(0d);
                        voEntity.setPcImpression(voEntity.getPcImpression() + ((map.get(adgroupName).getPcImpression() == null) ? 0 : map.get(adgroupName).getPcImpression()));
                        map.put(objectList.get(i).getAdgroupName()+"", voEntity);
                    } else {
                        map.put(objectList.get(i).getAdgroupName()+"", objectList.get(i));
                    }
                }
            return map;
        } else {
            int midpoint = (begin + endNumber) / 2;
            BasisReportDefaultUtil left = new BasisReportDefaultUtil(objectList, begin, midpoint);
            BasisReportDefaultUtil right = new BasisReportDefaultUtil(objectList, midpoint, endNumber);
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

        for (Iterator<Map.Entry<String, StructureReportEntity>> entry1 = leftMap.entrySet().iterator();entry1.hasNext();) {

            StructureReportEntity mapValue1 = entry1.next().getValue();

            for (Iterator<Map.Entry<String,StructureReportEntity>> entry2 = rightMap.entrySet().iterator();entry2.hasNext();) {

                StructureReportEntity mapValue2 = entry2.next().getValue();

                if (mapValue1.getAdgroupName().equals(mapValue2.getAdgroupName())) {
                    mapValue1.setMobileClick(mapValue1.getMobileClick() + mapValue2.getMobileClick());
                    mapValue1.setMobileConversion(mapValue1.getMobileConversion() + mapValue2.getMobileConversion());
                    mapValue1.setMobileCost(mapValue1.getMobileCost() + mapValue2.getMobileCost());
                    mapValue1.setMobileCtr(0d);
                    mapValue1.setMobileCtr(0d);
                    mapValue1.setMobileImpression(mapValue1.getMobileImpression() + mapValue2.getMobileImpression());
                    mapValue1.setPcClick(mapValue1.getPcClick() + mapValue2.getPcClick());
                    mapValue1.setPcConversion(mapValue1.getPcConversion() + mapValue2.getPcConversion());
                    mapValue1.setPcCost(mapValue1.getPcCost() + mapValue2.getPcCost());
                    mapValue1.setPcCtr(0d);
                    mapValue1.setPcCpc(0d);
                    mapValue1.setPcImpression(mapValue1.getPcImpression() + mapValue2.getPcImpression());
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
