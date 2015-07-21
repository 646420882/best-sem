package com.perfect.db.mongodb.impl;

import com.perfect.dao.report.AsynchronousNmsReportDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.account.NmsAccountReportDTO;
import com.perfect.dto.account.NmsAdReportDTO;
import com.perfect.dto.account.NmsCampaignReportDTO;
import com.perfect.dto.account.NmsGroupReportDTO;
import com.perfect.entity.report.NmsAccountReportEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dolphineor on 2015-7-21.
 */
@Repository("asynchronousNmsReportDAO")
public class AsynchronousNmsReportDAOImpl extends AbstractUserBaseDAOImpl<NmsAccountReportDTO, Long> implements AsynchronousNmsReportDAO {
    @Override
    public void getNmsAccountReportData(List<NmsAccountReportDTO> nmsAccountReportDtos, SystemUserDTO systemUser, String dateStr, String baiduUserName) {

    }

    @Override
    public void getNmsCampaignReportData(List<NmsCampaignReportDTO> nmsCampaignReportDTOs, SystemUserDTO systemUser, String dateStr, int i) {

    }

    @Override
    public void getNmsGroupReportData(List<NmsGroupReportDTO> nmsGroupReportDtos, SystemUserDTO systemUser, String dateStr, int i) {

    }

    @Override
    public void getNmsAdReportData(List<NmsAdReportDTO> nmsAdReportDTOs, SystemUserDTO systemUser, String dateStr, int i) {

    }

    @Override
    public Class<NmsAccountReportEntity> getEntityClass() {
        return NmsAccountReportEntity.class;
    }

    @Override
    public Class<NmsAccountReportDTO> getDTOClass() {
        return NmsAccountReportDTO.class;
    }
}
