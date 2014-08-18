package com.perfect.service.impl;

import com.perfect.dao.KeywordQualityDAO;
import com.perfect.entity.KeywordReportEntity;
import com.perfect.service.KeywordQualityService;
import com.perfect.utils.JSONUtils;
import com.perfect.utils.TopN;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by baizz on 2014-08-16.
 */
@Service("keywordQualityService")
public class KeywordQualityServiceImpl implements KeywordQualityService {

    @Resource
    private KeywordQualityDAO keywordQualityDAO;

    @Resource
    private TopN<KeywordReportEntity> topN;

    @Override
    public Map<String, Object> find(String startDate, String endDate, String fieldName, int n, int sort) {
        fieldName = "pc" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        List<KeywordReportEntity> list = keywordQualityDAO.find(startDate, endDate);
        if (list.size() == 0)
            return null;

        //去重, 求和
        Map<String, KeywordReportEntity> map = new LinkedHashMap<>();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        try {
            CalculateTask task = new CalculateTask(list, 0, list.size());
            Future<Map<String, KeywordReportEntity>> result = forkJoinPool.submit(task);
            map = result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }

        //计算点击率和平均点击价格
        for (Map.Entry<String, KeywordReportEntity> entry : map.entrySet()) {
            KeywordReportEntity vo = entry.getValue();
            Double cost = vo.getPcCost();
            Double ctr = (vo.getPcClick() + 0.) / vo.getPcImpression();
            Double cpc = 0.;
            cost = new BigDecimal(cost).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            ctr = new BigDecimal(ctr * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            if (vo.getPcClick() > 0)
                cpc = vo.getPcCost() / vo.getPcClick();
            cpc = new BigDecimal(cpc).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            vo.setPcCost(cost);
            vo.setPcCtr(ctr);
            vo.setPcCpc(cpc);
            entry.setValue(vo);
        }

        list = new ArrayList<>(map.values());

        //获取前N条数据
        KeywordReportEntity[] topNData = topN.getTopN(list.toArray(new KeywordReportEntity[list.size()]), n, fieldName, sort);
        Map<String, Object> values = JSONUtils.getJsonMapData(topNData);
        return values;
    }

    class CalculateTask extends RecursiveTask<Map<String, KeywordReportEntity>> {

        private static final int threshold = 1000;

        private int first;
        private int last;
        private List<KeywordReportEntity> list;

        CalculateTask(List<KeywordReportEntity> list, int first, int last) {
            this.first = first;
            this.last = last;
            this.list = list;
        }

        @Override
        protected Map<String, KeywordReportEntity> compute() {
            Map<String, KeywordReportEntity> map = new HashMap<>();
            boolean stat = (last - first) < threshold;
            if (stat) {
                for (int i = first; i < last; i++) {
                    KeywordReportEntity vo = list.get(i);
                    String keywordId = vo.getKeywordId().toString();
                    KeywordReportEntity _vo = map.get(keywordId);
                    if (_vo != null) {
                        _vo.setPcImpression(_vo.getPcImpression() + vo.getPcImpression());
                        _vo.setPcClick(_vo.getPcClick() + vo.getPcClick());
                        _vo.setPcCtr(0.);
                        _vo.setPcCost(_vo.getPcCost() + vo.getPcCost());
                        _vo.setPcCpc(0.);
                        _vo.setPcPosition(_vo.getPcPosition() + vo.getPcPosition());
                        _vo.setPcConversion(_vo.getPcConversion() + vo.getPcConversion());
                        map.put(keywordId, _vo);
                    } else {
                        map.put(keywordId, vo);
                    }
                }
            } else {
                int middle = (first + last) / 2;
                CalculateTask task1 = new CalculateTask(list, first, middle);
                CalculateTask task2 = new CalculateTask(list, middle, last);

                invokeAll(task1, task2);

                //map合并处理
                map.clear();
                map = mergeMap(task1.join(), task2.join());
            }
            return map;
        }

        private Map<String, KeywordReportEntity> mergeMap(Map<String, KeywordReportEntity> map1, Map<String, KeywordReportEntity> map2) {
            Map<String, KeywordReportEntity> _map = new HashMap<>();
            for (Iterator<Map.Entry<String, KeywordReportEntity>> iterator1 = map1.entrySet().iterator(); iterator1.hasNext(); ) {
                KeywordReportEntity vo = iterator1.next().getValue();
                for (Iterator<Map.Entry<String, KeywordReportEntity>> iterator2 = map2.entrySet().iterator(); iterator2.hasNext(); ) {
                    KeywordReportEntity _vo = iterator2.next().getValue();
                    if (_vo.getKeywordId().compareTo(vo.getKeywordId()) == 0) {
                        _vo.setPcImpression(_vo.getPcImpression() + vo.getPcImpression());
                        _vo.setPcClick(_vo.getPcClick() + vo.getPcClick());
                        _vo.setPcCtr(0.);
                        _vo.setPcCost(_vo.getPcCost() + vo.getPcCost());
                        _vo.setPcCpc(0.);
                        _vo.setPcPosition(_vo.getPcPosition() + vo.getPcPosition());
                        _vo.setPcConversion(_vo.getPcConversion() + vo.getPcConversion());
                        _map.put(_vo.getKeywordId().toString(), _vo);
                        iterator1.remove();
                        iterator2.remove();
                        break;
                    }
                }
            }

            for (Map.Entry<String, KeywordReportEntity> entry : map1.entrySet()) {
                KeywordReportEntity vo = entry.getValue();
                _map.put(vo.getKeywordId().toString(), vo);
            }

            for (Map.Entry<String, KeywordReportEntity> entry : map2.entrySet()) {
                KeywordReportEntity vo = entry.getValue();
                _map.put(vo.getKeywordId().toString(), vo);
            }

            return _map;
        }

    }
}
