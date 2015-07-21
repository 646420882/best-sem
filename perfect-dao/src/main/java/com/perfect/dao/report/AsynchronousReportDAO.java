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

    void getCampaignReportData(List<CampaignReportDTO> campaignReportDTOs, SystemUserDTO systemUser, String dateStr,int i);

    void getAdgroupReportData(List<AdgroupReportDTO> adgroupReportDTOs, SystemUserDTO systemUser, String dateStr,int i);

    void getCreativeReportData(List<CreativeReportDTO> creativeReportDTOs, SystemUserDTO systemUser, String dateStr,int i);

    void getKeywordReportData(List<KeywordReportDTO> keywordReportDTOs, SystemUserDTO systemUser, String dateStr,int i);

    void getRegionReportData(List<RegionReportDTO> regionReportDTOs, SystemUserDTO systemUser, String dateStr,int i);

    /*
     * 网盟推广
     */
    //网盟账户报告
    void getNmsAccountReportData(List<NmsAccountReportDto> nmsAccountReportDtos, SystemUserDTO systemUser, String dateStr, String baiduUserName);

    //网盟计划报告
    void getNmsCampaignReportData(List<NmsCampaignReportDTO> nmsCampaignReportDTOs, SystemUserDTO systemUser, String dateStr,int i);

    //网盟组报告
    void getNmsGroupReportData(List<NmsGroupReportDto> nmsGroupReportDtos, SystemUserDTO systemUser, String dateStr,int i);

    //网盟创意报告
    void getNmsAdReportData(List<NmsAdReportDTO> nmsAdReportDTOs, SystemUserDTO systemUser, String dateStr,int i);
}