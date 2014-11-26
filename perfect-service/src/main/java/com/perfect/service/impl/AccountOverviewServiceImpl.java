package com.perfect.service.impl;

import com.perfect.dao.AccountAnalyzeDAO;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.service.AccountOverviewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by john on 2014/7/25.
 */
@Service("accountOverviewService")
public class AccountOverviewServiceImpl implements AccountOverviewService {

    @Resource
    private AccountAnalyzeDAO accountAnalyzeDAO;

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 汇总。。。。
     *
     * @return
     */
    @Override
    public Map<String, Object> getKeyWordSum(String startDate, String endDate) {
        //各种汇总初始化
        long impressionCount = 0;
        long clickCount = 0;
        double costCount = 0.0;
        double conversionCount = 0.0;

        //开始获取数据汇总
        List<AccountReportDTO> list = null;
        try {
            list = accountAnalyzeDAO.performaneCurve(df.parse(startDate), df.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (AccountReportDTO are : list) {
            if (are.getPcImpression() != null) {
                impressionCount += are.getPcImpression();
            }
            if (are.getMobileImpression() != null) {
                impressionCount += are.getMobileImpression();
            }

            if (are.getPcClick() != null) {
                clickCount += are.getPcClick();
            }
            if (are.getMobileClick() != null) {
                clickCount += are.getMobileClick();
            }

            if (are.getPcCost() != null) {
                costCount += are.getPcCost().doubleValue();
            }
            if (are.getMobileCost() != null) {
                costCount += are.getMobileCost().doubleValue();
            }

            if (are.getPcConversion() != null) {
                conversionCount += are.getPcConversion();
            }
            if (are.getMobileConversion() != null) {
                conversionCount += are.getMobileConversion();
            }

        }

        //数字格式化
        DecimalFormat decimalFormat = new DecimalFormat("#,##,###");
        DecimalFormat costFormat = new DecimalFormat("#,##,##0.00");
        Map<String, Object> map = new Hashtable<String, Object>();
        map.put("impression", decimalFormat.format(impressionCount));
        map.put("click", decimalFormat.format(clickCount));
        map.put("cos", costFormat.format(costCount));
        map.put("conversion", decimalFormat.format((long) conversionCount));
        return map;
    }

}
