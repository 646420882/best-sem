package com.perfect.utils.report;

import com.google.common.collect.Lists;
import com.perfect.dto.keyword.SearchwordReportDTO;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by subdong on 15-10-9.
 */
public class AssistantKwdUtil extends RecursiveTask<List<SearchwordReportDTO>> {

    private final int threshold = 100;

    private int endNumber;
    private int begin;
    private String[] date;

    private List<SearchwordReportDTO> objectList;

    public AssistantKwdUtil(List<SearchwordReportDTO> objects, int begin, int endNumber, String[] date) {
        this.objectList = objects;
        this.endNumber = endNumber;
        this.begin = begin;
        this.date = date;
    }

    @Override
    protected List<SearchwordReportDTO> compute() {
        List<SearchwordReportDTO> list = Lists.newArrayList();
        DecimalFormat dft = new DecimalFormat("0.00");
        if ((endNumber - begin) < threshold) {
            if (objectList.size() > 0) {
                list = new ArrayList<>(objectList
                        .stream()
                        .collect(Collectors.groupingBy(t -> t.getCampaignName() + "-" + t.getAdgroupName() + "-" + t.getKeyword() + "-" + t.getSearchWord()))
                        .values()
                        .stream()
                        .filter(o -> o.size() > 0)
                        .map(o -> {
                            int _size = o.size();
                            if (_size == 1) {
                                o.get(0).setDate(date[0].substring(0, 10) + " 至 " + date[1].substring(0, 10));
                                return o.get(0);
                            } else {
                                SearchwordReportDTO tmpResult = o.stream().reduce((result, dto) -> {
                                    result.setClick((Integer.valueOf(result.getClick()) + Integer.valueOf(dto.getClick())) + "");
                                    result.setImpression((Integer.valueOf(result.getImpression()) + Integer.valueOf(dto.getImpression())) + "");
                                    return result;
                                }).get();
                                double rate = Double.parseDouble(tmpResult.getClick()) / Double.parseDouble(tmpResult.getImpression());
                                tmpResult.setClickRate(dft.format(BigDecimal.valueOf(rate * 100)) + "%");
                                tmpResult.setDate(date[0].substring(0, 10) + " 至 " + date[1].substring(0, 10));
                                return tmpResult;
                            }
                        })
                        .collect(Collectors.toList()));
            }
        } else {
            int midpoint = (begin + endNumber) / 2;
            AssistantKwdUtil left = new AssistantKwdUtil(objectList, begin, midpoint, date);
            AssistantKwdUtil right = new AssistantKwdUtil(objectList, midpoint, endNumber, date);
            invokeAll(left, right);
        }

        return list;
    }
}
