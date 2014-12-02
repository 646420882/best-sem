package com.perfect.db.mongodb.impl;

import com.perfect.utils.ObjectUtils;
import com.perfect.api.baidu.AccountRealTimeReport;
import com.perfect.autosdk.sms.v3.RealTimeResultType;
import com.perfect.dao.report.GetAccountReportDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.RealTimeResultDTO;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.entity.account.AccountReportEntity;
import com.perfect.utils.mongodb.DBNameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.perfect.commons.constants.MongoEntityConstants.ACCOUNT_ID;
import static com.perfect.commons.constants.MongoEntityConstants.TBL_ACCOUNT_REPORT;

/**
 * Created by john on 2014/8/8.
 * 2014-11-24 refactor
 */
@Component("getAccountReportDAO")
public class GetAccountReportDAOImpl implements GetAccountReportDAO {

    @Resource
    private AccountRealTimeReport accountRealTimeReport;

    @Resource
    private SystemUserDAO systemUserDAO;

    /**
     * 得到本地的数据报告（数据来自本地）
     *
     * @param startDate
     * @return
     */
    public AccountReportDTO getLocalAccountRealData(String userName, long accountId, Date startDate, Date endDate) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(userName));
        List<AccountReportEntity> list = mongoTemplate.find(Query.query(Criteria.where(ACCOUNT_ID).is(accountId).and("date").gte(startDate).lte(endDate)).with(new Sort(Sort.Direction.DESC, "date")), AccountReportEntity.class, TBL_ACCOUNT_REPORT);
        AccountReportEntity accountReportEntity= list.size() == 0 ? null : list.get(0);
        AccountReportDTO accountReportDTO=new AccountReportDTO();
        BeanUtils.copyProperties(accountReportEntity,accountReportDTO);
        return accountReportDTO;
    }


    /**
     * 得到当天的实时数据报告(来自百度)
     *
     * @return
     */
    public List<RealTimeResultDTO> getAccountRealTimeTypeByDate(String systemUserName, Long accountId, String startDate, String endDate) {
        SystemUserDTO systemUserDTO=systemUserDAO.findByAid(accountId);
        List<BaiduAccountInfoDTO> baiduAccountInfoDTO=systemUserDTO.getBaiduAccountInfoDTOs();
        BaiduAccountInfoDTO accountInfoDTO=baiduAccountInfoDTO.get(0);
        List<RealTimeResultType> realTimeDataList = accountRealTimeReport.getAccountRealTimeData(systemUserName,accountInfoDTO.getBaiduPassword(),accountInfoDTO.getToken(), startDate, endDate);
        return ObjectUtils.convert(realTimeDataList,RealTimeResultDTO.class);
    }

}
