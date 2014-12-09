package com.perfect.utils.report;

/**
 * Created by SubDong on 2014/9/25.
 */
public class ReportDownUtil {
    private static final String DEFAULT_DELIMITER = ",";
    private static final String DEFAULT_END = "\r\n";
    private static final byte commonCSVHead[] = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};


    private static final String DATE_STRING = "时间";
    private static final String ACCOUNT_STRING = "账户";
    private static final String SPREAD_STRING = "推广计划";
    private static final String SPREAD_UNIT_STRING = "推广单元";
    private static final String IMPR_STRING = "展现量";
    private static final String CLICK_STRING = "点击量";
    private static final String COST_STRING = "消费";
    private static final String CLICK_RATE_STRING = "点击率";
    private static final String AVG_CLICK_STRING = "平均点击价格";
    private static final String CONVERSION_STRING = "转化(页面)";
    private static final String KEYWORD_STRING = "关键字";
    private static final String CREATIVE_STRING = "创意";
    private static final String REGIONAL_STRING = "地域";
    /**
     * 获取下载头
     *
     * @param reportType
     * @return
     */
    public static String getHead(int reportType) {
        String head = "";
        switch (reportType) {
            case 1:
                head = DATE_STRING + DEFAULT_DELIMITER + ACCOUNT_STRING + DEFAULT_DELIMITER + SPREAD_STRING + DEFAULT_DELIMITER + SPREAD_UNIT_STRING + DEFAULT_DELIMITER + IMPR_STRING + DEFAULT_DELIMITER + CLICK_STRING +
                        DEFAULT_DELIMITER + COST_STRING + DEFAULT_DELIMITER + CLICK_RATE_STRING + DEFAULT_DELIMITER + AVG_CLICK_STRING +
                        DEFAULT_DELIMITER + CONVERSION_STRING + DEFAULT_END;
                break;
            case 2:
                head = DATE_STRING + DEFAULT_DELIMITER + ACCOUNT_STRING + DEFAULT_DELIMITER + SPREAD_STRING + DEFAULT_DELIMITER + SPREAD_UNIT_STRING + DEFAULT_DELIMITER + KEYWORD_STRING + DEFAULT_DELIMITER + IMPR_STRING +
                        DEFAULT_DELIMITER + CLICK_STRING + DEFAULT_DELIMITER + COST_STRING + DEFAULT_DELIMITER + CLICK_RATE_STRING + DEFAULT_DELIMITER + AVG_CLICK_STRING +
                        DEFAULT_DELIMITER + CONVERSION_STRING + DEFAULT_END;
                break;
            case 3:
                head = DATE_STRING + DEFAULT_DELIMITER + ACCOUNT_STRING + DEFAULT_DELIMITER + SPREAD_STRING + DEFAULT_DELIMITER + SPREAD_UNIT_STRING + DEFAULT_DELIMITER + CREATIVE_STRING + DEFAULT_DELIMITER + IMPR_STRING +
                        DEFAULT_DELIMITER + CLICK_STRING + DEFAULT_DELIMITER + COST_STRING + DEFAULT_DELIMITER + CLICK_RATE_STRING + DEFAULT_DELIMITER + AVG_CLICK_STRING +
                        DEFAULT_DELIMITER + CONVERSION_STRING + DEFAULT_END;
                break;
            case 4:
                head = DATE_STRING + DEFAULT_DELIMITER + ACCOUNT_STRING + DEFAULT_DELIMITER + REGIONAL_STRING + DEFAULT_DELIMITER + IMPR_STRING +
                        DEFAULT_DELIMITER + CLICK_STRING + DEFAULT_DELIMITER + COST_STRING + DEFAULT_DELIMITER + CLICK_RATE_STRING + DEFAULT_DELIMITER + AVG_CLICK_STRING +
                        DEFAULT_DELIMITER + CONVERSION_STRING + DEFAULT_END;
                break;
            case 5:
                head = DATE_STRING + DEFAULT_DELIMITER + ACCOUNT_STRING + DEFAULT_DELIMITER + SPREAD_STRING + DEFAULT_DELIMITER + IMPR_STRING +
                        DEFAULT_DELIMITER + CLICK_STRING + DEFAULT_DELIMITER + COST_STRING + DEFAULT_DELIMITER + CLICK_RATE_STRING + DEFAULT_DELIMITER + AVG_CLICK_STRING +
                        DEFAULT_DELIMITER + CONVERSION_STRING + DEFAULT_END;
                break;
            case 6:
                head = DATE_STRING + DEFAULT_DELIMITER + ACCOUNT_STRING + DEFAULT_DELIMITER + SPREAD_STRING + DEFAULT_DELIMITER + SPREAD_UNIT_STRING +
                        DEFAULT_DELIMITER + IMPR_STRING + DEFAULT_DELIMITER + CLICK_STRING + DEFAULT_DELIMITER + COST_STRING + DEFAULT_DELIMITER + CLICK_RATE_STRING +
                        DEFAULT_DELIMITER + AVG_CLICK_STRING + DEFAULT_DELIMITER + CONVERSION_STRING + DEFAULT_END;
                break;
            case 7:
                head = DATE_STRING + DEFAULT_DELIMITER + ACCOUNT_STRING + DEFAULT_DELIMITER + SPREAD_STRING + DEFAULT_DELIMITER + SPREAD_UNIT_STRING + DEFAULT_DELIMITER + KEYWORD_STRING +
                        DEFAULT_DELIMITER + IMPR_STRING + DEFAULT_DELIMITER + CLICK_STRING + DEFAULT_DELIMITER + COST_STRING + DEFAULT_DELIMITER + CLICK_RATE_STRING +
                        DEFAULT_DELIMITER + AVG_CLICK_STRING + DEFAULT_DELIMITER + CONVERSION_STRING + DEFAULT_END;
                break;
        }
        return head;
    }
}
