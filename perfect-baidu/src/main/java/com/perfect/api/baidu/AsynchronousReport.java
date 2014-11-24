package com.perfect.api.baidu;


import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by SubDong on 2014/8/7.
 */
public class AsynchronousReport {

    //得到百度aip
    private CommonService service = null;

    private ReportService reportService = null;


    public AsynchronousReport(String userName, String password, String token) {
        service = BaiduServiceSupport.getCommonService(userName, password, token);
        init();
    }

    public void init() {
        try {
            reportService = service.getService(ReportService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化API中需要共用到的属性
     *
     * @param _startDate 开始时间
     * @param _endDate   结束时间
     */
    private Date[] processingTime(String _startDate, String _endDate) {
        Date[] date = null;
        if (_startDate == null) {
//            Assert.notNull(_startDate, "_startDate must not be null!");
        }
        if (_endDate == null) {
//            Assert.notNull(_endDate, "_endDate must not be null!");
        }
        if (_startDate == null && _endDate == null) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.DATE, -1);
            date = new Date[]{cal.getTime(), cal.getTime()};
        } else {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = new Date[]{sd.parse(_startDate), sd.parse(_endDate)};
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    private String realTime(List<Long> listKey, int format, int granularity, Date startDate, Date endDate, int dataType, int device, String[] PerformanceData) {
        //得到设置返回数据工厂
        ReportRequestType requestType = new ReportRequestType();
        //指定返回数据类型
        if (PerformanceData != null) {
            requestType.setPerformanceData(Arrays.asList(PerformanceData));
        } else {
            if (format == 0) {
                requestType.setPerformanceData(Arrays.asList(new String[]{"cost", "cpc", "click", "impression", "ctr", "cpm", "conversion"}));
            } else {
                requestType.setPerformanceData(Arrays.asList(new String[]{"cost", "cpc", "click", "impression", "ctr", "cpm", "position", "conversion"}));
            }
        }
        //关键词统计范围下的id集合
        if (listKey != null) {
            requestType.setStatIds(listKey);
        }
        // 指定返回的数据层级
        // 默认为账户
        // 2：账户粒度 3：计划粒度 5：单元粒度 7：创意粒度 11：关键词(keywordid)粒度 12：关键词(keywordid)+创意粒度 6：关键词(wordid)粒度
        requestType.setLevelOfDetails(granularity);
        //指定起始时间
        requestType.setStartDate(startDate);
        requestType.setEndDate(endDate);
        //设置实时数据类型
        //2：账户 10：计划 11：单元 14：关键词(keywordid) 12：创意 15：配对 3：地域 9：关键词(wordid)
        requestType.setReportType(dataType);
        // 设置搜索推广渠道
        // 0：全部搜索推广设备  1：仅计算机 2：仅移动
        requestType.setDevice(device);

        //创建访问百度接口请求
        GetProfessionalReportIdRequest dataRequest = new GetProfessionalReportIdRequest();
        dataRequest.setReportRequestType(requestType);
        GetProfessionalReportIdResponse dataResponse = null;
        int reRetry = 1;
        while (reRetry > 0) {
            try {
                dataResponse = reportService.getProfessionalReportId(dataRequest);
                reRetry = 0;
            } catch (Exception e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }


        int reTime = 0;
        while (dataResponse == null) {
            if (reTime >= 3) {
                return null;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int reRetry1 = 1;
            while (reRetry1 > 0) {
                try {
                    dataResponse = reportService.getProfessionalReportId(dataRequest);
                    reRetry1 = 0;
                } catch (Exception e) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            reTime++;
        }

        String reportId = dataResponse.getReportId();

        GetReportStateRequest reportStateRequest = new GetReportStateRequest();
        reportStateRequest.setReportId(reportId);
        GetReportStateResponse reportStateResponse = null;
        int reRetry2 = 1;
        while (reRetry2 > 0) {
            try {
                reportStateResponse = reportService.getReportState(reportStateRequest);
                reRetry2 = 0;
            } catch (Exception e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }


        int reTimeTow = 0;
        while (reportStateResponse == null) {
            if (reTimeTow >= 5) {
                return null;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int reRetry3 = 1;
            while (reRetry3 > 0) {
                try {
                    reportStateResponse = reportService.getReportState(reportStateRequest);
                    reRetry3 = 0;
                } catch (Exception e) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            reTimeTow++;
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int isGenerated = 0;
        int sleepTime = 30 * 1000;
        int views = 0;
        isGenerated = reportStateResponse.getIsGenerated();

        String fileUrl = null;
        do {
            if (isGenerated == 1 || isGenerated == 2) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                GetReportStateResponse reportState = null;
                int reRetry4 = 1;
                while (reRetry4 > 0) {
                    try {
                        reportState = reportService.getReportState(reportStateRequest);
                        reRetry4 = 0;
                    } catch (Exception e) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

                views++;
                if (reportState == null) {
                    continue;
                }
                isGenerated = reportState.getIsGenerated();
            } else if (isGenerated == 3) {
                GetReportFileUrlRequest fileUrlRequest = new GetReportFileUrlRequest();
                fileUrlRequest.setReportId(reportId);

                GetReportFileUrlResponse stateResponse = null;
                int reRetry4 = 1;
                while (reRetry4 > 0) {
                    try {
                        stateResponse = reportService.getReportFileUrl(fileUrlRequest);
                        reRetry4 = 0;
                    } catch (Exception e) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

                if (stateResponse == null) {
                    fileUrl = null;
                } else
                    fileUrl = stateResponse.getReportFilePath();
                break;
            } else {
                break;
            }
        } while (views < 3);

        return fileUrl;
    }

    /**
     * 获取账户PC端数据
     *
     * @param _startDate
     * @param _endDate
     * @return
     */
    public String getAccountReportDataPC(String[] PerformanceData, String _startDate, String _endDate) {
        //初始化时间
        Date[] dates = processingTime(_startDate, _endDate);
        /**
         * RealTime(需要查询ID，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        String resultTypes = realTime(null, 0, 2, dates[0], dates[1], 2, 1, PerformanceData);

        return resultTypes;
    }

    /**
     * 获取账户移动端数据
     *
     * @param _startDate
     * @param _endDate
     * @return
     */
    public String getAccountReportDataMobile(String[] PerformanceData, String _startDate, String _endDate) {
        //初始化时间
        Date[] dates = processingTime(_startDate, _endDate);
        /**
         * RealTime(需要查询ID，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        String resultTypes = realTime(null, 0, 2, dates[0], dates[1], 2, 2, PerformanceData);

        return resultTypes;
    }

    /**
     * 获取关键词PC端数据
     *
     * @param _startDate
     * @param _endDate
     * @return
     */
    public String getKeyWordidReportDataPC(List<Long> listKey, String[] PerformanceData, String _startDate, String _endDate) {
        //初始化时间
        Date[] dates = processingTime(_startDate, _endDate);
        /**
         * RealTime(需要查询ID，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        String resultTypes = realTime(listKey, 1, 11, dates[0], dates[1], 14, 1, PerformanceData);

        return resultTypes;
    }

    /**
     * 获取关键词移动端数据
     *
     * @param _startDate
     * @param _endDate
     * @return
     */
    public String getKeyWordidReportDataMobile(List<Long> listKey, String[] PerformanceData, String _startDate, String _endDate) {
        //初始化时间
        Date[] dates = processingTime(_startDate, _endDate);
        /**
         * RealTime(需要查询ID，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        String resultTypes = realTime(listKey, 1, 11, dates[0], dates[1], 14, 2, PerformanceData);

        return resultTypes;
    }

    /**
     * 获取单元PC端数据
     *
     * @param _startDate
     * @param _endDate
     * @return
     */
    public String getUnitReportDataPC(List<Long> listKey, String[] PerformanceData, String _startDate, String _endDate) {
        //初始化时间
        Date[] dates = processingTime(_startDate, _endDate);
        /**
         * RealTime(需要查询ID ，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        String resultTypes = realTime(listKey, 0, 5, dates[0], dates[1], 11, 1, PerformanceData);

        return resultTypes;
    }

    /**
     * 获取单元移动端数据
     *
     * @param _startDate
     * @param _endDate
     * @return
     */
    public String getUnitReportDataMobile(List<Long> listKey, String[] PerformanceData, String _startDate, String _endDate) {
        //初始化时间
        Date[] dates = processingTime(_startDate, _endDate);
        /**
         * RealTime(需要查询ID ，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        String resultTypes = realTime(listKey, 0, 5, dates[0], dates[1], 11, 2, PerformanceData);

        return resultTypes;
    }

    /**
     * 获取创意PC端数据
     *
     * @param _startDate
     * @param _endDate
     * @return
     */
    public String getCreativeReportDataPC(List<Long> listKey, String[] PerformanceData, String _startDate, String _endDate) {
        //初始化时间
        Date[] dates = processingTime(_startDate, _endDate);
        /**
         * RealTime(需要查询ID ，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        String resultTypes = realTime(listKey, 1, 7, dates[0], dates[1], 12, 1, PerformanceData);

        return resultTypes;
    }

    /**
     * 获取创意移动端数据
     *
     * @param _startDate
     * @param _endDate
     * @return
     */
    public String getCreativeReportDataMobile(List<Long> listKey, String[] PerformanceData, String _startDate, String _endDate) {
        //初始化时间
        Date[] dates = processingTime(_startDate, _endDate);
        /**
         * RealTime(需要查询ID ，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        String resultTypes = realTime(listKey, 1, 7, dates[0], dates[1], 12, 2, PerformanceData);

        return resultTypes;
    }

    /**
     * 获取地域PC端数据
     *
     * @param _startDate
     * @param _endDate
     * @return
     */
    public String getRegionalReportDataPC(List<Long> listKey, String[] PerformanceData, String _startDate, String _endDate) {
        //初始化时间
        Date[] dates = processingTime(_startDate, _endDate);
        /**
         * RealTime(需要查询ID ，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        String resultTypes = realTime(listKey, 1, 5, dates[0], dates[1], 3, 1, PerformanceData);

        return resultTypes;
    }

    /**
     * 获取地域移动端数据
     *
     * @param _startDate
     * @param _endDate
     * @return
     */
    public String getRegionalReportDataMobile(List<Long> listKey, String[] PerformanceData, String _startDate, String _endDate) {
        //初始化时间
        Date[] dates = processingTime(_startDate, _endDate);
        /**
         * RealTime(需要查询ID ，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        String resultTypes = realTime(listKey, 0, 3, dates[0], dates[1], 3, 2, PerformanceData);

        return resultTypes;
    }

    /**
     * 获取计划移动端数据
     *
     * @param _startDate
     * @param _endDate
     * @return
     */
    public String getCampaignReportDataMobile(List<Long> listKey, String[] PerformanceData, String _startDate, String _endDate) {
        //初始化时间
        Date[] dates = processingTime(_startDate, _endDate);
        /**
         * RealTime(需要查询ID ，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        String resultTypes = realTime(listKey, 0, 3, dates[0], dates[1], 10, 2, PerformanceData);

        return resultTypes;
    }

    /**
     * 获取计划PC端数据
     *
     * @param _startDate
     * @param _endDate
     * @return
     */
    public String getCampaignReportDataPC(List<Long> listKey, String[] PerformanceData, String _startDate, String _endDate) {
        //初始化时间
        Date[] dates = processingTime(_startDate, _endDate);
        /**
         * RealTime(需要查询ID ，返回数据格式，粒度，开始时间，结束时间，实时数据类型)
         */
        String resultTypes = realTime(listKey, 0, 3, dates[0], dates[1], 10, 1, PerformanceData);

        return resultTypes;
    }
}
