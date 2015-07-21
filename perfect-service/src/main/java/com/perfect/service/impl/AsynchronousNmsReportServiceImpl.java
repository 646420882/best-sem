package com.perfect.service.impl;

import com.perfect.dto.account.NmsAccountReportDTO;
import com.perfect.dto.account.NmsAdReportDTO;
import com.perfect.dto.account.NmsCampaignReportDTO;
import com.perfect.dto.account.NmsGroupReportDTO;
import com.perfect.service.AsynchronousNmsReportService;

import java.util.Collections;
import java.util.List;

/**
 * Created by subdong on 15-7-21.
 */
public class AsynchronousNmsReportServiceImpl implements AsynchronousNmsReportService {

    @Override
    public void getNmsAccountReportData(String dateStr, String userName) {

    }

    @Override
    public void getNmsCampaignReportData(String dateStr, String userName) {

    }

    @Override
    public void getNmsGroupReportData(String dateStr, String userName) {

    }

    @Override
    public void getNmsAdReportData(String dateStr, String userName) {

    }


    class HttpFileHandler {

        public List<NmsAccountReportDTO> getNmsAccountReport(String fileUrl) {
            return Collections.emptyList();
        }

        public List<NmsCampaignReportDTO> getNmsCampaignReport(String fileUrl) {
            return Collections.emptyList();
        }

        public List<NmsGroupReportDTO> getNmsGroupReport(String fileUrl) {
            return Collections.emptyList();
        }

        public List<NmsAdReportDTO> getNmsAdReport(String fileUrl) {
            return Collections.emptyList();
        }
    }
}
