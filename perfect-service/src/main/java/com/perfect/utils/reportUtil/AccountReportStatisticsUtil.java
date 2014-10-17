package com.perfect.utils.reportUtil;

import com.perfect.dto.AccountReportDTO;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by SubDong on 2014/9/22.
 */
public abstract class AccountReportStatisticsUtil {
    /**
     * 账户对用户数据进行处理
     *
     * @param responses 用户List 数据
     * @return
     */
    public static List<AccountReportDTO> getUserPro(List<AccountReportDTO> responses) {
        List<AccountReportDTO> list = new ArrayList<>();
        for (AccountReportDTO enit : responses) {
            if (list.size() > 0) {
                list.get(0).setPcImpression(list.get(0).getPcImpression() + ((enit.getPcImpression() == null) ? 0 : enit.getPcImpression()));
                list.get(0).setPcClick(list.get(0).getPcClick() + ((enit.getPcClick() == null) ? 0 : enit.getPcClick()));
                list.get(0).setPcCost(list.get(0).getPcCost().add((enit.getPcCost() == null) ? BigDecimal.valueOf(0) : enit.getPcCost()));
                list.get(0).setPcConversion(list.get(0).getPcConversion() + ((enit.getPcConversion() == null) ? 0 : enit.getPcConversion()));
                list.get(0).setPcCtr(0d);
                list.get(0).setPcCpc(BigDecimal.valueOf(0));
                list.get(0).setMobileImpression(((list.get(0).getMobileImpression() == null) ? 0 : list.get(0).getMobileImpression()) + ((enit.getMobileImpression() == null) ? 0 : enit.getMobileImpression()));
                list.get(0).setMobileClick(((list.get(0).getMobileClick() == null) ? 0 : list.get(0).getMobileClick()) + ((enit.getMobileClick() == null) ? 0 : enit.getMobileClick()));
                list.get(0).setMobileCost(((list.get(0).getMobileCost() == null) ? BigDecimal.valueOf(0) : list.get(0).getMobileCost()).add((enit.getMobileCost() == null) ? BigDecimal.valueOf(0) : enit.getMobileCost()));
                list.get(0).setMobileConversion(((list.get(0).getMobileConversion() == null) ? 0 : list.get(0).getMobileConversion()) + ((enit.getMobileConversion() == null) ? 0 : enit.getMobileConversion()));
                list.get(0).setMobileCtr(0d);
                list.get(0).setMobileCpc(BigDecimal.valueOf(0));
            } else {
                list.add(enit);
            }
        }
        return list;
    }

    /**
     * 账户计算点击率 平均价格等
     */
    public static List<AccountReportDTO> getAveragePro(List<AccountReportDTO> dtos) {
        List<AccountReportDTO> list = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.0000");
        for (AccountReportDTO response : dtos) {
            if (response.getMobileImpression() == null || response.getMobileImpression() == 0) {
                response.setMobileCtr(0.00);
            } else {
                BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format(response.getMobileClick().doubleValue() / response.getMobileImpression().doubleValue())));
                BigDecimal big = new BigDecimal(100);
                double divide = ctrBig.multiply(big).doubleValue();
                response.setMobileCtr(divide);
            }
            if (response.getMobileClick() == null || response.getMobileClick() == 0) {
                response.setMobileCpc(BigDecimal.valueOf(0.00));
            } else {
                response.setMobileCpc(response.getMobileCost().divide(BigDecimal.valueOf(response.getMobileClick()), 2, BigDecimal.ROUND_UP));
            }

            if (response.getPcImpression() == null || response.getPcImpression() == 0) {
                response.setPcCtr(0.00);
            } else {
                double ctrBig = Double.parseDouble(df.format(response.getPcClick().doubleValue() / response.getPcImpression().doubleValue()));
                int i = (int) (ctrBig * 10000);
                double big = i / 100.00;
                response.setPcCtr(big);
            }
            if (response.getPcClick() == null || response.getPcClick() == 0) {
                response.setPcCpc(BigDecimal.valueOf(0.00));
            } else {
                response.setPcCpc(response.getPcCost().divide(BigDecimal.valueOf(response.getPcClick()), 2, BigDecimal.ROUND_UP));
            }
            list.add(response);
        }
        return list;
    }


}
