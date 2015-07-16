package com.baidu.api;

import com.baidu.api.client.core.*;
import com.baidu.api.sem.common.v2.ResHeader;
import com.baidu.api.sem.nms.v2.*;

import java.util.Calendar;

public class BaiduApiExample {

    private ReportService service;

    public BaiduApiExample() {
        // Get service factory. Your authentication information will be popped up automatically from
        // baidu-api.properties
        VersionService factory = ServiceFactory.getInstance("baidu-renrenche2143339", "Renrenche2015", "58b2073002e0777ae636766b15444201", null);
        // Get service stub by given the Service interface.
        // Please see the bean-api.tar.gz to get more details about all the service interfaces.
        this.service = factory.getService(ReportService.class);
    }

    public GetReportIdResponse getReportId() {
        // Prepare your parameters.
        GetReportIdRequest parameter = new GetReportIdRequest();
        // get report
        ReportRequestType request = new ReportRequestType();

        //report type: 2 means campaign report
        request.setReportType(2);

        //startDate: here is 20110309
        Calendar startDate = Calendar.getInstance();
        startDate.set(2015, Calendar.JUNE, 1, 0, 0, 0);
        request.setStartDate(startDate.getTime());
        //endDate: here is 20110309
        Calendar endDate = Calendar.getInstance();
        endDate.set(2015, Calendar.JUNE, 5, 23, 59, 59);
        request.setEndDate(endDate.getTime());

        //statRange: 2 means statRange is campaign
        request.setStatRange(2);

        //statIds is not required, default is all the Ids that belong the the statRange specified.
        //If set, the result will limit to only the campaignIds specified
        long[] campaignIds = new long[]{757446, 757447, 757448};
        for (long campaignId : campaignIds) {
            request.getStatIds().add(campaignId);
        }

        //performanceData is required! below items are the maximun extent we support
        request.getPerformanceData().add("srch");
        request.getPerformanceData().add("click");
        request.getPerformanceData().add("cost");
        request.getPerformanceData().add("ctr");
        request.getPerformanceData().add("cpm");
        request.getPerformanceData().add("acp");

        //idOnly is not required, default value is false which means you can get literal info of material in returned result.
        //When literal info is not that useful for you, we appreciate you set true to this field, which could provide you with higher performance
        request.setIdOnly(false);

        //format is not required, default value is 0, means zip. you can specify 1 to csv
        request.setFormat(0);
        parameter.setReportRequestType(request);
        // Invoke the method.
        GetReportIdResponse ret = service.getReportId(parameter);
        // Deal with the response header, the second parameter controls whether to print the response header to console
        // or not.
        ResHeader rheader = ResHeaderUtil.getResHeader(service, true);
        // If status equals zero, there is no error. Otherwise, you need to check the errors in the response header.
        if (rheader.getStatus() == 0) {
            System.out.println("result\n" + ObjToStringUtil.objToString(ret));
            return ret;
        } else {
            throw new ClientBusinessException(rheader, ret);
        }
    }

    public GetReportStateResponse getReportState(String reportId) {
        // This is the request
        GetReportStateRequest parameters = new GetReportStateRequest();
        parameters.setReportId(reportId);
        // Invoke the method.
        GetReportStateResponse ret = service.getReportState(parameters);
        // Deal with the response header, the second parameter controls whether to print the response header to console
        // or not.
        ResHeader rheader = ResHeaderUtil.getResHeader(service, true);
        // If status equals zero, there is no error. Otherwise, you need to check the errors in the response header.
        if (rheader.getStatus() == 0) {
            System.out.println("getReportState.result\n" + ObjToStringUtil.objToString(ret));
            return ret;
        } else {
            throw new ClientBusinessException(rheader, ret);
        }
    }

    public GetReportFileUrlResponse getReportFileUrl(String reportId) {
        // This is the request
        GetReportFileUrlRequest parameters = new GetReportFileUrlRequest();
        parameters.setReportId(reportId);
        // Invoke the method.
        GetReportFileUrlResponse ret = service.getReportFileUrl(parameters);
        // Deal with the response header, the second parameter controls whether to print the response header to console
        // or not.
        ResHeader rheader = ResHeaderUtil.getResHeader(service, true);
        // If status equals zero, there is no error. Otherwise, you need to check the errors in the response header.
        if (rheader.getStatus() == 0) {
            System.out.println("getReportFileUrl.result\n" + ObjToStringUtil.objToString(ret));
            return ret;
        } else {
            throw new ClientBusinessException(rheader, ret);
        }
    }

    public static void main(String[] args) throws Exception {
        BaiduApiExample example = new BaiduApiExample();
        GetReportIdResponse id = example.getReportId();
        System.out.println("report id: " + id);

        GetReportFileUrlResponse urlRes = ReportUtil.getReportFileUrl(
                example.service, id.getReportId(), 60);
        String content = ReportUtil.getFileContent(urlRes.getReportFilePath(),
                "zip");
        System.out.println(content);
        System.out.println("=============================");
    }

}
