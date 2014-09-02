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
public class AccountReportPCPlusMobUtil extends RecursiveTask<List<AccountReportDTO>>{

        private final int threshold = 100;

        private int endNumber;
        private int begin;
        private int terminal;

        private List<AccountReportDTO> objectList;

        public AccountReportPCPlusMobUtil(List<AccountReportDTO> objects, int begin, int endNumber){
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
                    objectList.get(i).setDateRep(dateFormat.format(objectList.get(i).getDate()));
                    objectList.get(i).setPcClick(objectList.get(i).getPcClick() + ((objectList.get(i).getMobileClick() == null) ? 0 : objectList.get(i).getMobileClick()));
                    objectList.get(i).setPcConversion(objectList.get(i).getPcConversion() + ((objectList.get(i).getMobileConversion() == null) ? 0 : objectList.get(i).getMobileConversion()));
                    objectList.get(i).setPcCost(objectList.get(i).getPcCost() + ((objectList.get(i).getMobileCost() == null) ? 0 : objectList.get(i).getMobileCost()));
                    //计算点击率
                    if(((objectList.get(i).getMobileImpression() == null) ? 0 : objectList.get(i).getMobileImpression()) == 0){
                        objectList.get(i).setPcCtr(0.00);
                        if(((objectList.get(i).getPcImpression() == null) ? 0 : objectList.get(i).getPcImpression()) == 0){
                            objectList.get(i).setPcCtr(0d);
                        }else{
                            BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format((objectList.get(i).getPcClick().doubleValue() / objectList.get(i).getPcImpression().doubleValue()))));
                            BigDecimal big = new BigDecimal(100);
                            double divide = ctrBig.multiply(big).doubleValue();
                            objectList.get(i).setPcCtr(divide);
                        }
                    }else{
                        double newNumber = Double.parseDouble(df.format((objectList.get(i).getPcClick() + ((objectList.get(i).getMobileClick() == null) ? 0 : objectList.get(i).getMobileClick()))/(objectList.get(i).getMobileImpression() + ((objectList.get(i).getMobileImpression() == null) ? 0 : objectList.get(i).getMobileImpression()))));
                        BigDecimal ctrBig = new BigDecimal(newNumber);
                        BigDecimal big = new BigDecimal(100);
                        double divide = ctrBig.multiply(big).doubleValue();
                        objectList.get(i).setPcCtr(divide);
                    }
                    //计算平均点击价格
                    if(((objectList.get(i).getMobileClick() == null) ? 0 : objectList.get(i).getMobileClick()) == 0){
                        if(((objectList.get(i).getPcClick() == null) ? 0 : objectList.get(i).getPcClick()) == 0){
                            objectList.get(i).setPcCpc(0d);
                        }else{
                            objectList.get(i).setPcCpc(Double.parseDouble(df.format((objectList.get(i).getPcCost().doubleValue()/objectList.get(i).getPcClick().doubleValue()))));
                        }
                    }else{
                        double newNumber =  Double.parseDouble(df.format((objectList.get(i).getPcCost() + ((objectList.get(i).getMobileCost() == null) ? 0 : objectList.get(i).getMobileCost()))/(objectList.get(i).getPcClick() + ((objectList.get(i).getMobileClick() == null) ? 0 : objectList.get(i).getMobileClick()))));
                        objectList.get(i).setPcCpc(newNumber);
                    }
                    objectList.get(i).setPcImpression(objectList.get(i).getPcImpression() + ((objectList.get(i).getMobileImpression() == null) ? 0 : objectList.get(i).getMobileImpression()));
                    objectList.get(i).setMobileClick(null);
                    objectList.get(i).setMobileConversion(null);
                    objectList.get(i).setMobileCost(null);
                    objectList.get(i).setMobileCtr(null);
                    objectList.get(i).setMobileCpc(null);
                    objectList.get(i).setMobileImpression(null);
                    list.add(objectList.get(i));
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
