package com.perfect.dao.report;

import com.perfect.account.SystemUserInfoVO;
import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.account.*;
import com.perfect.dto.keyword.KeywordReportDTO;

import java.util.List;

/**
 * Created by baizz on 2014-08-07.
 */
public interface AsynchronousReportDAO extends HeyCrudRepository<AccountReportDTO, Long> {

    void getAccountReportData(List<AccountReportDTO> accountReportDTOs, SystemUserInfoVO systemUser, String dateStr, String baiduUserName);

    void getCampaignReportData(List<CampaignReportDTO> campaignReportDTOs, SystemUserInfoVO systemUser, String dateStr, int i);

    void getAdgroupReportData(List<AdgroupReportDTO> adgroupReportDTOs, SystemUserInfoVO systemUser, String dateStr, int i);

    void getCreativeReportData(List<CreativeReportDTO> creativeReportDTOs, SystemUserInfoVO systemUser, String dateStr, int i);

    void getKeywordReportData(List<KeywordReportDTO> keywordReportDTOs, SystemUserInfoVO systemUser, String dateStr, int i);

    void getRegionReportData(List<RegionReportDTO> regionReportDTOs, SystemUserInfoVO systemUser, String dateStr, int i);

}