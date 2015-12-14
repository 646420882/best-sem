package com.perfect.dao.report;

import com.perfect.account.SystemUserInfoVO;
import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.account.NmsAccountReportDTO;
import com.perfect.dto.account.NmsAdReportDTO;
import com.perfect.dto.account.NmsCampaignReportDTO;
import com.perfect.dto.account.NmsGroupReportDTO;

import java.util.List;

/**
 * Created by dolphineor on 2015-7-21.
 */
public interface AsynchronousNmsReportDAO extends HeyCrudRepository<NmsAccountReportDTO, Long> {

    /*
     * 网盟推广
     */
    //网盟账户报告
    void getNmsAccountReportData(List<NmsAccountReportDTO> nmsAccountReportDtos, SystemUserInfoVO systemUser, String dateStr, String baiduUserName);

    //网盟计划报告
    void getNmsCampaignReportData(List<NmsCampaignReportDTO> nmsCampaignReportDTOs, SystemUserInfoVO systemUser, String dateStr);

    //网盟组报告
    void getNmsGroupReportData(List<NmsGroupReportDTO> nmsGroupReportDtos, SystemUserInfoVO systemUser, String dateStr);

    //网盟创意报告
    void getNmsAdReportData(List<NmsAdReportDTO> nmsAdReportDTOs, SystemUserInfoVO systemUser, String dateStr);
}
