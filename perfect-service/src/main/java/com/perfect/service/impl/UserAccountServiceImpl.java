package com.perfect.service.impl;

import com.perfect.commons.constants.SystemNameConstant;
import com.perfect.dao.account.SystemAccountDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.dto.huiyan.InsightWebsiteDTO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.sys.SystemUserModuleDTO;
import com.perfect.service.UserAccountService;
import com.perfect.utils.HttpClientUtils;
import com.perfect.utils.HuiyanJsonConverUtils;
import com.perfect.utils.json.JSONUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Created on 2015-12-20.
 *
 * @author dolphineor
 */
@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {


    @Resource
    private SystemUserDAO systemUserDAO;

    @Resource
    private SystemAccountDAO systemAccountDAO;


    private static final String HTTP_URL = "http://192.168.1.104:8000/config/site_list?";

    private static final String HTTP_UTF8 = "UTF-8";

    @Override
    public List<ModuleAccountInfoDTO> getSemAccounts(String username) {
        String userId = systemAccountDAO.findByUserName(username).getId();
        String sysUserModuleId = systemAccountDAO.findSysUserModuleId(username, SystemNameConstant.SOUKE_SYSTEM_NAME);

        return systemAccountDAO.findByUserIdAndModuleId(userId, sysUserModuleId);
    }

    @Override
    public long[] getSemModuleServiceLife(String username) {
        SystemUserDTO systemUserDTO = systemAccountDAO.findByUserName(username);
        try {
            SystemUserModuleDTO systemUserModuleDTO = systemUserDTO.getSystemUserModules()
                    .stream()
                    .filter(o -> Objects.equals(SystemNameConstant.SOUKE_SYSTEM_NAME, o.getModuleName()))
                    .findFirst()
                    .get();

            long[] serviceLife = new long[2];
            serviceLife[0] = systemUserModuleDTO.getStartTime();
            serviceLife[1] = systemUserModuleDTO.getEndTime();

            return serviceLife;
        } catch (NullPointerException e) {
            return new long[2];
        }
    }

    @Override
    public boolean bindAccountForSem(String username, ModuleAccountInfoDTO dto) {
        // 设置帐号绑定时间
        dto.setAccountBindingTime(Calendar.getInstance().getTimeInMillis());
        // 设置帐号为可用状态
        dto.setState(1L);
        setModuleAccountBasicInfoForSem(username, dto);

        systemAccountDAO.insertModuleAccount(dto);

        return true;
    }

    @Override
    public void unbindAccountForSem(String username, String moduleAccountId) {
        ModuleAccountInfoDTO moduleAccountInfoDTO = new ModuleAccountInfoDTO();
        setModuleAccountBasicInfoForSem(username, moduleAccountInfoDTO);
        moduleAccountInfoDTO.setState(0L);
        systemAccountDAO.updateModuleAccount(moduleAccountInfoDTO);
    }

    @Override
    public void activeAccountForSem(String username, String moduleAccountId) {
        ModuleAccountInfoDTO moduleAccountInfoDTO = new ModuleAccountInfoDTO();
        setModuleAccountBasicInfoForSem(username, moduleAccountInfoDTO);
        moduleAccountInfoDTO.setId(moduleAccountId);
        moduleAccountInfoDTO.setState(1L);
        systemAccountDAO.updateModuleAccount(moduleAccountInfoDTO);
    }

    @Override
    public void updateAccountForSem(String username, ModuleAccountInfoDTO dto) {
        setModuleAccountBasicInfoForSem(username, dto);
        systemAccountDAO.updateModuleAccount(dto);
    }

    @Override
    public void deleteAccountForSem(String moduleAccountId) {
        systemAccountDAO.deleteByObjectId(moduleAccountId);
    }

    @Override
    public String getUserEmail(String username) {
        String email = systemUserDAO.getUserEmail(username);

        if (Objects.isNull(email))
            return "";

        return email;
    }

    @Override
    public void updateEmail(String username, String email) {
        String sysUserId = systemUserDAO.findByUserName(username).getId();
        systemUserDAO.updateUserEmail(sysUserId, email);
    }

    @Override
    public String insertInsight(InsightWebsiteDTO websiteDTO) {
        if (Objects.nonNull(websiteDTO)) {
            websiteDTO.setSite_pause(false);
            websiteDTO.setTrack_status(-1);
            websiteDTO.setIcon(1);
            websiteDTO.setIs_use(1);
            String websiteString = JSONUtils.getJsonString(websiteDTO);
            try {
                String insert = HttpClientUtils.getRequest(HTTP_URL + "type=save&entity=" + URLEncoder.encode(websiteString, HTTP_UTF8));
                if (Objects.nonNull(insert)) return insert;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<InsightWebsiteDTO> queryInfo(String uid) {
        if (Objects.nonNull(uid)) {
            String urlKey = "{\"uid\":\"" + uid + "\"}";
            try {
                String queryString = HttpClientUtils.getRequest(HTTP_URL + "type=search&query=" + URLEncoder.encode(urlKey, HTTP_UTF8));
                List<InsightWebsiteDTO> dtos = HuiyanJsonConverUtils.toInsight(queryString);
                if (Objects.nonNull(dtos)) return dtos;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return new ArrayList<>();
    }

    @Override
    public String del(String id) {
        if (Objects.nonNull(id)) {
            String newid = "{\"_id\":\"" + id + "\"}";
            try {
                String s = HttpClientUtils.getRequest(HTTP_URL + "type=delete&query=" + URLEncoder.encode(newid, HTTP_UTF8));
                if (Objects.nonNull(s)) return s;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String huiyanUpdate(String uid, String rname, String url, String webName) {
        if (Objects.nonNull(uid)) {
            try {
                String query = URLEncoder.encode("{\"_id\":\"" + uid + "\"}", HTTP_UTF8);
                String update = "{";
                int i = 0;
                if (Objects.nonNull(uid)) {
                    update = update + "\"rname\":\"" + rname + "\"";
                    i++;
                }
                if (Objects.nonNull(url)) {
                    if (i > 0) {
                        update = update + ",\"site_url\":\"" + url + "\"";
                    } else {
                        update = update + "\"site_url\":\"" + url + "\"";
                    }
                    i++;
                }
                if (Objects.nonNull(webName)) {
                    if (i > 0) {
                        update = update + ",\"site_name\":\"" + webName + "\"";
                    } else {
                        update = update + "\"site_name\":\"" + webName + "\"";
                    }
                }
                update = update + "}";

                String s = HttpClientUtils.getRequest(HTTP_URL + "type=update&query=" + query + "&updates=" + URLEncoder.encode(update, HTTP_UTF8));
                if (Objects.nonNull(s)) return s;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String huiyanEnableOrPause(String id, int enable) {
        try {
            String query = URLEncoder.encode("{\"_id\":\"" + id + "\"}", HTTP_UTF8);
            String update;
            if (enable == 0) {
                update = "{\"site_pause\": true}";
            } else {
                update = "{\"site_pause\": false}";
            }

            String s = HttpClientUtils.getRequest(HTTP_URL + "type=update&query=" + query + "&updates=" + URLEncoder.encode(update, HTTP_UTF8));
            if (Objects.nonNull(s)) return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setModuleAccountBasicInfoForSem(String username, ModuleAccountInfoDTO dto) {
        SystemUserDTO systemUserDTO = systemAccountDAO.findByUserName(username);
        dto.setUserId(systemUserDTO.getId());

        String sysUserModuleId = systemAccountDAO.findSysUserModuleId(username, SystemNameConstant.SOUKE_SYSTEM_NAME);
        dto.setModuleId(sysUserModuleId);
    }
}
