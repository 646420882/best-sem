package com.perfect.service.impl;

import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.core.AppContext;
import com.perfect.dao.report.GetAccountReportDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.dto.RealTimeResultDTO;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.service.GetAccountReportService;
import com.perfect.utils.DateUtils;
import com.perfect.utils.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by XiaoWei on 2014/12/3.
 */
@Service("getAccountReportService")
public class GetAccountReportServiceImpl implements GetAccountReportService {

    @Resource
    private GetAccountReportDAO getAccountReportDAO;
    @Resource
    private SystemUserDAO systemUserDAO;

    @Override
    public AccountReportDTO getLocalAccountRealData(String userName, long accountId, Date startDate, Date endDate) {
        return getAccountReportDAO.getLocalAccountRealData(userName, accountId, startDate, endDate);
    }

    @Override
    public List<RealTimeResultDTO> getAccountRealTimeTypeByDate(String systemUserName, Long accountId, String startDate, String endDate) {

        SystemUserDTO systemUserDTO = systemUserDAO.findByAid(accountId);
        try {
            List<ModuleAccountInfoDTO> accountInfoDTO = systemUserDTO.getModuleDTOList().stream()
                    .filter((systemUserModuleDTO -> systemUserModuleDTO.getModuleName().equals(AppContext.getModuleName())))
                    .findFirst().get().getAccounts();
            ModuleAccountInfoDTO baiduAccountInfoDTO = new ModuleAccountInfoDTO();
            for (ModuleAccountInfoDTO infoDTO : accountInfoDTO) {
                if (infoDTO.getBaiduAccountId().equals(accountId)) {
                    baiduAccountInfoDTO = infoDTO;
                }
            }
            List<RealTimeResultDTO> realTimeDataList = getAccountRealTimeData(systemUserName, baiduAccountInfoDTO.getBaiduPassword(), baiduAccountInfoDTO.getToken(), startDate, endDate);
            return ObjectUtils.convert(realTimeDataList, RealTimeResultDTO.class);
        } catch (Exception ex) {
            return Collections.EMPTY_LIST;
        }


    }

    private List<RealTimeResultDTO> getAccountRealTimeData(String username, String passwd, String token, String _startDate, String _endDate) {
        Date startDate = null, endDate = null;
        if (_startDate == null && _endDate == null) {
            startDate = DateUtils.getYesterday();
            endDate = startDate;
        } else {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-M-d");
            try {
                startDate = sd.parse(_startDate);
                endDate = sd.parse(_endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        ReportService reportService = null;
        try {
            CommonService commonService = BaiduServiceSupport.getCommonService(username, passwd, token);
            if (commonService == null) {
                return new ArrayList<>();
            }
            reportService = commonService.getService(ReportService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        //设置请求参数
        RealTimeRequestType realTimeRequestType = new RealTimeRequestType();
        //指定返回数据类型
        realTimeRequestType.setPerformanceData(Arrays.asList("impression", "click", "ctr", "cost", "cpc", "conversion"));
        //指定起始时间
        realTimeRequestType.setStartDate(startDate);
        realTimeRequestType.setEndDate(endDate);
        //指定实时数据类型
        realTimeRequestType.setReportType(2);
        realTimeRequestType.setDevice(1);

        //创建请求
        GetRealTimeDataRequest getRealTimeDataRequest = new GetRealTimeDataRequest();
        getRealTimeDataRequest.setRealTimeRequestTypes(realTimeRequestType);
        GetRealTimeDataResponse response = null;
        if (reportService != null) {
            response = reportService.getRealTimeData(getRealTimeDataRequest);
        }
        if (response == null) {
            return new ArrayList<>();
        }
        List<RealTimeResultType> realTimeResultTypes = response.getRealTimeResultTypes();
        return ObjectUtils.convert(realTimeResultTypes, RealTimeResultDTO.class);
    }
}
