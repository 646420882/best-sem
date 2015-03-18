package com.perfect.dto.count;

import java.util.Date;
import java.util.List;

/**
 * Created by XiaoWei on 2014/12/4.
 */
public class CountDTO {
    private String timeSpan;
    private int total;
    private List<Integer> sum;
    private List<ConstantsDTO> items;

    public String getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Integer> getSum() {
        return sum;
    }

    public void setSum(List<Integer> sum) {
        this.sum = sum;
    }

    public List<ConstantsDTO> getItems() {
        return items;
    }

    public void setItems(List<ConstantsDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CountDTO{" +
                "timeSpan=" + timeSpan +
                ", total=" + total +
                ", sum=" + sum +
                ", items=" + items +
                '}';
    }
}
