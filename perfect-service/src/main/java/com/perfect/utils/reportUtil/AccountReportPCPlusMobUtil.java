package com.perfect.utils.reportUtil;

import com.perfect.dto.AccountReportDTO;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Created by SubDong on 2014/8/13.
 */
public class AccountReportPCPlusMobUtil extends RecursiveTask<List<AccountReportDTO>> {

    private final int threshold = 100;

    private int endNumber;
    private int begin;
    private int terminal;

    private List<AccountReportDTO> objectList;

    public AccountReportPCPlusMobUtil(List<AccountReportDTO> objects, int begin, int endNumber) {
        this.objectList = objects;
        this.endNumber = endNumber;
        this.begin = begin;
    }

    @Override
    protected List<AccountReportDTO> compute() {
        List<AccountReportDTO> list = new ArrayList<>();
        if ((endNumber - begin) < threshold) {
            DecimalFormat df = new DecimalFormat("#.00");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = begin; i < endNumber; i++) {
                AccountReportDTO dto = new AccountReportDTO();
                dto.setDateRep(dateFormat.format(objectList.get(i).getDate()));
                dto.setPcClick(objectList.get(i).getPcClick() + ((objectList.get(i).getMobileClick() == null) ? 0 : objectList.get(i).getMobileClick()));
                dto.setPcConversion(objectList.get(i).getPcConversion() + ((objectList.get(i).getMobileConversion() == null) ? 0 : objectList.get(i).getMobileConversion()));
                dto.setPcCost(objectList.get(i).getPcCost().add((objectList.get(i).getMobileCost() == null) ? BigDecimal.valueOf(0) : objectList.get(i).getMobileCost()));
                //计算点击率
                if (((objectList.get(i).getMobileImpression() == null) ? 0 : objectList.get(i).getMobileImpression()) == 0) {
                    dto.setPcCtr(0.00);
                    if (((objectList.get(i).getPcImpression() == null) ? 0 : objectList.get(i).getPcImpression()) == 0) {
                        dto.setPcCtr(0d);
                    } else {
                        BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format((objectList.get(i).getPcClick().doubleValue() / objectList.get(i).getPcImpression().doubleValue()))));
                        BigDecimal big = new BigDecimal(100);
                        double divide = ctrBig.multiply(big).doubleValue();
                        dto.setPcCtr(divide);
                    }
                } else {
                    double newNumber = Double.parseDouble(df.format((objectList.get(i).getPcClick() + ((objectList.get(i).getMobileClick() == null) ? 0 : objectList.get(i).getMobileClick())) / (objectList.get(i).getMobileImpression() + ((objectList.get(i).getMobileImpression() == null) ? 0 : objectList.get(i).getMobileImpression()))));
                    BigDecimal ctrBig = new BigDecimal(newNumber);
                    BigDecimal big = new BigDecimal(100);
                    double divide = ctrBig.multiply(big).doubleValue();
                    dto.setPcCtr(divide);
                }
                //计算平均点击价格
                if (((objectList.get(i).getMobileClick() == null) ? 0 : objectList.get(i).getMobileClick()) == 0) {
                    if (((objectList.get(i).getPcClick() == null) ? 0 : objectList.get(i).getPcClick()) == 0) {
                        dto.setPcCpc(BigDecimal.valueOf(0));
                    } else {
                        dto.setPcCpc((objectList.get(i).getPcCost().divide(BigDecimal.valueOf(objectList.get(i).getPcClick()), 2, BigDecimal.ROUND_UP)));
                    }
                } else {
                    BigDecimal newNumber = (objectList.get(i).getPcCost().add((objectList.get(i).getMobileCost() == null) ? BigDecimal.valueOf(0) : objectList.get(i).getMobileCost())).divide(BigDecimal.valueOf(objectList.get(i).getPcClick() + ((objectList.get(i).getMobileClick() == null) ? 0 : objectList.get(i).getMobileClick())),2,BigDecimal.ROUND_UP);
                    dto.setPcCpc(newNumber);
                }
                dto.setPcImpression(objectList.get(i).getPcImpression() + ((objectList.get(i).getMobileImpression() == null) ? 0 : objectList.get(i).getMobileImpression()));
                dto.setMobileClick(0);
                dto.setMobileConversion(0.00);
                dto.setMobileCost(BigDecimal.ZERO);
                dto.setMobileCtr(0.00);
                dto.setMobileCpc(BigDecimal.ZERO);
                dto.setMobileImpression(0);
                dto.setMobileCpm(BigDecimal.ZERO);
                list.add(dto);
            }
            return list;
        } else {
            int midpoint = (begin + endNumber) / 2;
            AccountReportPCPlusMobUtil left = new AccountReportPCPlusMobUtil(objectList, begin, midpoint);
            AccountReportPCPlusMobUtil right = new AccountReportPCPlusMobUtil(objectList, midpoint, endNumber);
            left.fork();
            right.fork();
            list.addAll(left.join());
            list.addAll(right.join());
            return list;
        }
    }
}
