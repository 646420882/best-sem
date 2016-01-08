package com.perfect.utils.report;

import com.perfect.core.AppContext;
import com.perfect.dto.StructureReportDTO;
import com.perfect.dto.account.AccountReportDTO;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * Created by subdong on 15-9-18.
 */
public class BasisReportDataHandleUtil {

    /**
     * 账户 计算所有数据  包含PC端 、 移动端
     *
     * @param responseMap
     * @return
     */
    public static Map<String, List<AccountReportDTO>> getPcPlusMobileDate(Map<String, List<AccountReportDTO>> responseMap) {
        Map<String, List<AccountReportDTO>> respons = new HashMap<>();


        DecimalFormat df = new DecimalFormat("#.0000");
        for (Map.Entry<String, List<AccountReportDTO>> voEntity : responseMap.entrySet()) {
            List<AccountReportDTO> list = new ArrayList<>();
            AccountReportDTO dto = new AccountReportDTO();
            for (AccountReportDTO response : voEntity.getValue()) {
                dto.setPcImpression(((response.getPcImpression() == null) ? 0 : response.getPcImpression()) + ((response.getMobileImpression() == null) ? 0 : response.getMobileImpression()));
                dto.setPcConversion(((response.getPcConversion() == null) ? 0 : response.getPcConversion()) + ((response.getMobileConversion() == null) ? 0 : response.getMobileConversion()));
                dto.setPcClick(((response.getPcClick() == null) ? 0 : response.getPcClick()) + ((response.getMobileClick() == null) ? 0 : response.getMobileClick()));
                dto.setPcCost(response.getPcCost().add((response.getMobileCost() == null) ? BigDecimal.valueOf(0) : response.getMobileCost()));
                //计算点击率
                if (((response.getPcImpression() == null) ? 0 : response.getPcImpression()) == 0) {
                    dto.setPcCtr(0.00);
                } else {
                    BigDecimal ctrBig = new BigDecimal(Double.parseDouble(df.format((response.getPcClick().doubleValue() / response.getPcImpression().doubleValue()))));
                    BigDecimal big = new BigDecimal(100);
                    double divide = ctrBig.multiply(big).doubleValue();
                    dto.setPcCtr(divide);
                }

                //计算平均点击价格
                if (((response.getPcClick() == null) ? 0 : response.getPcClick()) == 0) {
                    dto.setPcCpc(BigDecimal.valueOf(0));
                } else {
                    dto.setPcCpc(response.getPcCost().divide(BigDecimal.valueOf(response.getPcClick()), 2, BigDecimal.ROUND_UP));
                }

                dto.setMobileImpression(0);
                dto.setMobileClick(0);
                dto.setMobileConversion(0.00);
                dto.setMobileCost(BigDecimal.ZERO);
                dto.setMobileCpc(BigDecimal.ZERO);
                dto.setMobileCpm(BigDecimal.ZERO);
                dto.setMobileCtr(0.00);
                list.add(dto);
            }
            respons.put(voEntity.getKey(), list);
        }
        return respons;
    }

    /**
     * 账户对用户数据进行处理
     *
     * @param responses 用户List 数据
     * @param date1     数据开始时间
     * @param date2     数据结束时间
     * @return
     */
    public static Map<String, List<AccountReportDTO>> getUserDataPro(List<AccountReportDTO> responses, Date date1, Date date2) {
        Map<String, List<AccountReportDTO>> responseMap = new HashMap<>();
        List<AccountReportDTO> responseList = new ArrayList<>();
        AccountReportDTO reportDTO = new AccountReportDTO();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (AccountReportDTO enit : responses) {
            if (reportDTO.getDateRep() == null) {
                reportDTO.setDateRep(dateFormat.format(enit.getDate()));
            }
            reportDTO.setPcImpression((reportDTO.getPcImpression() == null ? 0 : reportDTO.getPcImpression()) + ((enit.getPcImpression() == null) ? 0 : enit.getPcImpression()));
            reportDTO.setPcClick((reportDTO.getPcClick() == null ? 0 : reportDTO.getPcClick()) + ((enit.getPcClick() == null) ? 0 : enit.getPcClick()));
            reportDTO.setPcCost((reportDTO.getPcCost() == null ? BigDecimal.ZERO : reportDTO.getPcCost()).add((enit.getPcCost() == null) ? BigDecimal.valueOf(0) : enit.getPcCost()));
            reportDTO.setPcConversion(reportDTO.getPcConversion() == null ? 0 : reportDTO.getPcConversion() + ((enit.getPcConversion() == null) ? 0 : enit.getPcConversion()));
            reportDTO.setPcCtr(0d);
            reportDTO.setPcCpc(BigDecimal.valueOf(0));
            reportDTO.setMobileImpression(((reportDTO.getMobileImpression() == null) ? 0 : reportDTO.getMobileImpression()) + ((enit.getMobileImpression() == null) ? 0 : enit.getMobileImpression()));
            reportDTO.setMobileClick(((reportDTO.getMobileClick() == null) ? 0 : reportDTO.getMobileClick()) + ((enit.getMobileClick() == null) ? 0 : enit.getMobileClick()));
            reportDTO.setMobileCost(((reportDTO.getMobileCost() == null) ? BigDecimal.valueOf(0) : reportDTO.getMobileCost()).add((enit.getMobileCost() == null) ? BigDecimal.valueOf(0) : enit.getMobileCost()));
            reportDTO.setMobileConversion(((reportDTO.getMobileConversion() == null) ? 0 : reportDTO.getMobileConversion()) + ((enit.getMobileConversion() == null) ? 0 : enit.getMobileConversion()));
            reportDTO.setMobileCtr(0d);
            reportDTO.setMobileCpc(BigDecimal.valueOf(0));
        }
        responseList.add(reportDTO);
        responseMap.put(dateFormat.format(date1) + " 至 " + dateFormat.format(date2), responseList);
        return responseMap;
    }

    /**
     * 账户时间处理
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Date[] getDateProcessing(Date startDate, Date endDate) {
        Date dateOne = null;
        Date dateTow = null;
        if (startDate == null && endDate != null) {
            dateOne = endDate;
        }
        if (endDate == null && startDate != null) {
            dateTow = startDate;
        }
        if (startDate == null && endDate == null) {
            dateOne = new Date();
            dateTow = new Date();
        } else {
            dateOne = startDate;
            dateTow = endDate;
        }
        Date[] dates = {dateOne, dateTow};
        return dates;
    }

}
