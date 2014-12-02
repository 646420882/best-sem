package com.perfect.service;

import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;

import java.util.List;

/**
 * Created by yousheng on 2014/8/12.
 *
 * @author yousheng
 */
public interface SystemUserService {

    void initAccount(String uname, Long accountId);

    void updateAccountData(String userName, long accountId);

    void updateAccountData(String userName, long accountId, List<Long> camIds);

    void updateBaiduAccountInfo(String userName, Long accountId, BaiduAccountInfoDTO dto);

    List<CampaignDTO> getCampaign(String userName, long accountId);

    SystemUserDTO getSystemUser(String userName);

    SystemUserDTO getSystemUser(long aid);

    Iterable<SystemUserDTO> getAllUser();

    void save(SystemUserDTO systemUserDTO);

    boolean removeAccount(Long id);

    void addAccount(String user, BaiduAccountInfoDTO baiduAccountInfoDTO);

    boolean updatePassword(String userName, String pwd);

    void clearAccountData(Long accountId);

    void clearCampaignData(Long accountId, List<Long> campaignIds);

    void clearAdgroupData(Long accountId, List<Long> adgroupIds);

    void clearKeywordData(Long accountId, List<Long> keywordIds);

    void clearCreativeData(Long accountId, List<Long> creativeIds);

    List<Long> getLocalAdgroupIds(Long accountId, List<Long> campaignIds);

    List<Long> getLocalKeywordIds(Long accountId, List<Long> adgroupIds);

    List<Long> getLocalCreativeIds(Long accountId, List<Long> adgroupIds);

}
