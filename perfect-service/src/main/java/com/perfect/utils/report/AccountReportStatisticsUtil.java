package com.perfect.utils.report;

import com.perfect.dto.account.AccountReportDTO;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
        AccountReportDTO reportDTO = new AccountReportDTO();
        for (AccountReportDTO enit : responses) {
            reportDTO.setPcImpression((reportDTO.getPcImpression() == null ? 0 : reportDTO.getPcImpression()) + ((enit.getPcImpression() == null) ? 0 : enit.getPcImpression()));
            reportDTO.setPcClick((reportDTO.getPcClick() == null ? 0 : reportDTO.getPcClick()) + ((enit.getPcClick() == null) ? 0 : enit.getPcClick()));
            reportDTO.setPcCost((reportDTO.getPcCost() == null ? BigDecimal.ZERO : reportDTO.getPcCost()).add((enit.getPcCost() == null) ? BigDecimal.valueOf(0) : enit.getPcCost()));
            reportDTO.setPcConversion((reportDTO.getPcConversion() == null ? 0 : reportDTO.getPcConversion()) + ((enit.getPcConversion() == null) ? 0 : enit.getPcConversion()));
            reportDTO.setPcCtr(0d);
            reportDTO.setPcCpc(BigDecimal.valueOf(0));
            reportDTO.setMobileImpression(((reportDTO.getMobileImpression() == null) ? 0 : reportDTO.getMobileImpression()) + ((enit.getMobileImpression() == null) ? 0 : enit.getMobileImpression()));
            reportDTO.setMobileClick(((reportDTO.getMobileClick() == null) ? 0 : reportDTO.getMobileClick()) + ((enit.getMobileClick() == null) ? 0 : enit.getMobileClick()));
            reportDTO.setMobileCost(((reportDTO.getMobileCost() == null) ? BigDecimal.valueOf(0) : reportDTO.getMobileCost()).add((enit.getMobileCost() == null) ? BigDecimal.valueOf(0) : enit.getMobileCost()));
            reportDTO.setMobileConversion(((reportDTO.getMobileConversion() == null) ? 0 : reportDTO.getMobileConversion()) + ((enit.getMobileConversion() == null) ? 0 : enit.getMobileConversion()));
            reportDTO.setMobileCtr(0d);
            reportDTO.setMobileCpc(BigDecimal.valueOf(0));
        }
        list.add(reportDTO);
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
