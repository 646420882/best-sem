package com.perfect.dao.report;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.account.*;
import com.perfect.dto.keyword.KeywordReportDTO;

import java.util.List;

/**
 * Created by baizz on 2014-08-07.
 */
public interface AsynchronousReportDAO extends HeyCrudRepository<AccountReportDTO,Long> {

    void getAccountReportData(List<AccountReportDTO> accountReportDTOs, SystemUserDTO systemUser, String dateStr, String baiduUserName);

    void getCampaignReportData(List<CampaignReportDTO> campaignReportDTOs, SystemUserDTO systemUser, String dateStr);

    void getAdgroupReportData(List<AdgroupReportDTO> adgroupReportDTOs, SystemUserDTO systemUser, String dateStr);

    void getCreativeReportData(List<CreativeReportDTO> creativeReportDTOs, SystemUserDTO systemUser, String dateStr);

    void getKeywordReportData(List<KeywordReportDTO> keywordReportDTOs, SystemUserDTO systemUser, String dateStr);

    void getRegionReportData(List<RegionReportDTO> regionReportDTOs, SystemUserDTO systemUser, String dateStr);
}