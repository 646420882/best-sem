package com.perfect.utils.report;

/**
 * Created by SubDong on 2014/9/25.
 */
public class ReportDownUtil {
    private static final String DEFAULT_DELIMITER = ",";
    private static final String DEFAULT_END = "\r\n";
    private static final byte commonCSVHead[] = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

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
                head = "时间" + DEFAULT_DELIMITER + "账户" + DEFAULT_DELIMITER + "推广计划" + DEFAULT_DELIMITER + "推广单元" + DEFAULT_DELIMITER + "展现量" + DEFAULT_DELIMITER + "点击量" +
                        DEFAULT_DELIMITER + "消费" + DEFAULT_DELIMITER + "点击率" + DEFAULT_DELIMITER + "平均点击价格" +
                        DEFAULT_DELIMITER + "转化(页面)" + DEFAULT_END;
                break;
            case 2:
                head = "时间" + DEFAULT_DELIMITER + "账户" + DEFAULT_DELIMITER + "推广计划" + DEFAULT_DELIMITER + "推广单元" + DEFAULT_DELIMITER + "关键字" + DEFAULT_DELIMITER + "展现量" +
                        DEFAULT_DELIMITER + "点击量" + DEFAULT_DELIMITER + "消费" + DEFAULT_DELIMITER + "点击率" + DEFAULT_DELIMITER + "平均点击价格" +
                        DEFAULT_DELIMITER + "转化(页面)" + DEFAULT_END;
                break;
            case 3:
                head = "时间" + DEFAULT_DELIMITER + "账户" + DEFAULT_DELIMITER + "推广计划" + DEFAULT_DELIMITER + "推广单元" + DEFAULT_DELIMITER + "创意" + DEFAULT_DELIMITER + "展现量" +
                        DEFAULT_DELIMITER + "点击量" + DEFAULT_DELIMITER + "消费" + DEFAULT_DELIMITER + "点击率" + DEFAULT_DELIMITER + "平均点击价格" +
                        DEFAULT_DELIMITER + "转化(页面)" + DEFAULT_END;
                break;
            case 4:
                head = "时间" + DEFAULT_DELIMITER + "账户" + DEFAULT_DELIMITER + "地域" + DEFAULT_DELIMITER + "展现量" +
                        DEFAULT_DELIMITER + "点击量" + DEFAULT_DELIMITER + "消费" + DEFAULT_DELIMITER + "点击率" + DEFAULT_DELIMITER + "平均点击价格" +
                        DEFAULT_DELIMITER + "转化(页面)" + DEFAULT_END;
                break;
            case 5:
                head = "时间" + DEFAULT_DELIMITER + "账户" + DEFAULT_DELIMITER + "推广计划" + DEFAULT_DELIMITER + "展现量" +
                        DEFAULT_DELIMITER + "点击量" + DEFAULT_DELIMITER + "消费" + DEFAULT_DELIMITER + "点击率" + DEFAULT_DELIMITER + "平均点击价格" +
                        DEFAULT_DELIMITER + "转化(页面)" + DEFAULT_END;
                break;
            case 6:
                head = "时间" + DEFAULT_DELIMITER + "账户" + DEFAULT_DELIMITER + "推广计划" + DEFAULT_DELIMITER + "推广单元" +
                        DEFAULT_DELIMITER + "展现量" + DEFAULT_DELIMITER + "点击量" + DEFAULT_DELIMITER + "消费" + DEFAULT_DELIMITER + "点击率" +
                        DEFAULT_DELIMITER + "平均点击价格" + DEFAULT_DELIMITER + "转化(页面)" + DEFAULT_END;
                break;
            case 7:
                head = "时间" + DEFAULT_DELIMITER + "账户" + DEFAULT_DELIMITER + "推广计划" + DEFAULT_DELIMITER + "推广单元" + DEFAULT_DELIMITER + "关键字" +
                        DEFAULT_DELIMITER + "展现量" + DEFAULT_DELIMITER + "点击量" + DEFAULT_DELIMITER + "消费" + DEFAULT_DELIMITER + "点击率" +
                        DEFAULT_DELIMITER + "平均点击价格" + DEFAULT_DELIMITER + "转化(页面)" + DEFAULT_END;
                break;
        }
        return head;
    }

    public static String getReportBody() {

        return null;
    }


}
