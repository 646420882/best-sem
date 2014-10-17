package com.perfect.mongodb.utils;

import com.perfect.entity.KeywordRealTimeDataVOEntity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

/**
 * Created by SubDong on 2014/7/28.
 */
public class Performance extends RecursiveTask<Map<Long, KeywordRealTimeDataVOEntity>> {

    private final int threshold = 100;

    private int endNumber;
    private int begin;

    private List<KeywordRealTimeDataVOEntity> analyzeEntities;


    public Performance(List<KeywordRealTimeDataVOEntity> analyzeEntities, int begin, int endNumber) {
        this.analyzeEntities = analyzeEntities;
        this.endNumber = endNumber;
        this.begin = begin;
    }

    @Override
    protected Map<Long, KeywordRealTimeDataVOEntity> compute() {
        Map<Long, KeywordRealTimeDataVOEntity> map = new HashMap<>();
        if ((endNumber - begin) < threshold) {
            for (int i = begin; i < endNumber; i++) {
                if (map.get(analyzeEntities.get(i).getKeywordId()) != null) {
                    Long keyId = analyzeEntities.get(i).getKeywordId();
                    KeywordRealTimeDataVOEntity voEntity = map.get(keyId);
                    voEntity.setImpression(voEntity.getImpression() + map.get(keyId).getImpression());
                    voEntity.setClick(voEntity.getClick() + map.get(keyId).getClick());
                    voEntity.setCost(voEntity.getCost() + map.get(keyId).getCost());
                    voEntity.setCtr(0d);
                    voEntity.setCpc(0d);
                    voEntity.setPosition(voEntity.getPosition() + map.get(keyId).getPosition());
                    voEntity.setConversion(voEntity.getConversion() + map.get(keyId).getConversion());
                    map.put(analyzeEntities.get(i).getKeywordId(), voEntity);
                } else {
                    map.put(analyzeEntities.get(i).getKeywordId(), analyzeEntities.get(i));
                }
            }
            return map;
        } else {
            int midpoint = (begin + endNumber) / 2;
            Performance left = new Performance(analyzeEntities, begin, midpoint);
            Performance right = new Performance(analyzeEntities, midpoint, endNumber);
            invokeAll(left, right);
            try {
                Map<Long, KeywordRealTimeDataVOEntity> leftMap = left.get();
                Map<Long, KeywordRealTimeDataVOEntity> rightMap = right.get();
                map.putAll(merge(leftMap, rightMap));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return map;
        }
    }


    public Map<Long, KeywordRealTimeDataVOEntity> merge(Map<Long, KeywordRealTimeDataVOEntity> leftMap, Map<Long, KeywordRealTimeDataVOEntity> rightMap) {
        Map<Long, KeywordRealTimeDataVOEntity> dataMap = new HashMap<>();

        for (Iterator<Map.Entry<Long, KeywordRealTimeDataVOEntity>> entry1 = leftMap.entrySet().iterator(); entry1.hasNext(); ) {

            KeywordRealTimeDataVOEntity mapValue1 = entry1.next().getValue();

            for (Iterator<Map.Entry<Long, KeywordRealTimeDataVOEntity>> entry2 = rightMap.entrySet().iterator(); entry2.hasNext(); ) {

                KeywordRealTimeDataVOEntity mapValue2 = entry2.next().getValue();

                if (mapValue1.getKeywordId() == mapValue2.getKeywordId()) {
                    mapValue1.setImpression(mapValue1.getImpression() + mapValue2.getImpression());
                    mapValue1.setClick(mapValue1.getClick() + mapValue2.getClick());
                    mapValue1.setCost(mapValue1.getCost() + mapValue2.getCost());
                    mapValue1.setCtr(0d);
                    mapValue1.setCpc(0d);
                    mapValue1.setPosition(mapValue1.getPosition() + mapValue2.getPosition());
                    mapValue1.setConversion(mapValue1.getConversion() + mapValue2.getConversion());
                    dataMap.put(mapValue1.getImpression().longValue(), mapValue1);
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
