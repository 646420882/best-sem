package com.perfect.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.perfect.commons.constants.EmailConstants;
import com.perfect.commons.constants.PasswordSalts;
import com.perfect.commons.constants.SystemNameConstant;
import com.perfect.dao.account.AccountRegisterDAO;
import com.perfect.dao.account.SystemAccountDAO;
import com.perfect.dto.huiyan.InsightWebsiteDTO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.sys.SystemUserModuleDTO;
import com.perfect.param.RegisterParam;
import com.perfect.service.AccountRegisterService;
import com.perfect.service.UserAccountService;
import com.perfect.utils.MD5;
import com.perfect.utils.email.EmailUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by SubDong on 2014/9/30.
 */
@Service("accountRegisterService")
public class AccountRegisterServiceImpl implements AccountRegisterService, EmailConstants {

    private final String userSalt = PasswordSalts.USER_SALT;

    private final MD5.Builder md5Builder = new MD5.Builder();

    @Resource
    private AccountRegisterDAO accountRegisterDAO;

    @Resource
    private SystemAccountDAO systemAccountDAO;

    @Resource
    private UserAccountService userAccountService;


    @Override
    public int addAccount(String account, String pwd, String company, String email) {
        SystemUserDTO systemUserDTO = new SystemUserDTO();
        MD5 md5 = md5Builder.source(pwd).salt(userSalt).build();
        systemUserDTO.setAccess(2);
        systemUserDTO.setUserName(account);
        systemUserDTO.setPassword(md5.getMD5());
        systemUserDTO.setCompanyName(company);
        systemUserDTO.setEmail(email);
        systemUserDTO.setState(0);

        systemUserDTO.setAccountState(1);
        int returnState;
        SystemUserDTO user = accountRegisterDAO.getAccount(account);
        if (user == null) {
            accountRegisterDAO.addAccount(systemUserDTO);
            returnState = 1;
        } else {
            returnState = -1;
        }
        return returnState;
    }

    @Override
    public int addAccount(RegisterParam registerParam) {
        int returnState = -1;
        if (registerParam != null) {
            SystemUserDTO systemUserDTO = new SystemUserDTO();
            //公司名称
            systemUserDTO.setCompanyName(registerParam.getCompanyname());
            //帐号
            systemUserDTO.setUserName(registerParam.getUsername());
            MD5 md5 = md5Builder.source(registerParam.getPassword()).salt(userSalt).build();
            //密码
            systemUserDTO.setPassword(md5.getMD5());
            //邮箱
            systemUserDTO.setEmail(registerParam.getEmail());
            //联系人
            systemUserDTO.setContactName(registerParam.getContacts());
            //办公电话
            systemUserDTO.setTelephone("");
            //移动电话
            systemUserDTO.setMobilePhone(registerParam.getContactsPhone());
            //通讯地址
            systemUserDTO.setAddress(registerParam.getMailinAddress());
            //审核状态
            systemUserDTO.setState(1);
            systemUserDTO.setAccess(2);
            systemUserDTO.setAccountState(1);
            //注册时间
            systemUserDTO.setCtime(new Date().getTime());
            //是否付费用户
            systemUserDTO.setPayed(registerParam.getAccountType() == 2);

            //开通平台
            String[] dd = registerParam.getOpenPlatform().split(",");
            List<SystemUserModuleDTO> entities = Lists.newArrayList();
            Map<String, ModuleAccountInfoDTO> moduleAccountInfoDTOMap = Maps.newHashMap();
            for (String s : dd) {
//                SystemModuleDTO systemModuleDTO = systemAccountDAO.findByModuleName(s);

                // 百思系统模块
                SystemUserModuleDTO systemUserModuleDTO = new SystemUserModuleDTO();
                String sysUserModuleId = new ObjectId().toString();
                systemUserModuleDTO.setId(sysUserModuleId);
//                systemUserModuleDTO.setModuleId(systemModuleDTO.getId());
                systemUserModuleDTO.setModuleName(s);
//                systemUserModuleDTO.setModuleUrl(systemModuleDTO.getModuleUrl());
                systemUserModuleDTO.setPayed(true);
                systemUserModuleDTO.setEnabled(true);
                systemUserModuleDTO.setStartTime(Calendar.getInstance().getTimeInMillis());
                systemUserModuleDTO.setEndTime(Calendar.getInstance().getTimeInMillis() + 3600_000 * 24 * 365);

                //百度账户处理
                ModuleAccountInfoDTO baiduAccountInfo = new ModuleAccountInfoDTO();
                baiduAccountInfo.setModuleId(sysUserModuleId);
                baiduAccountInfo.setAccountBindingTime(Calendar.getInstance().getTimeInMillis());
                baiduAccountInfo.setBaiduUserName(registerParam.getBaiduUserName());
                baiduAccountInfo.setBaiduPassword(registerParam.getBaiduUserPassword());
                baiduAccountInfo.setBestRegDomain(registerParam.getUrlAddress());
                moduleAccountInfoDTOMap.put(s, baiduAccountInfo);

                entities.add(systemUserModuleDTO);
            }
            systemUserDTO.setSystemUserModules(entities);

            SystemUserDTO user = accountRegisterDAO.getAccount(registerParam.getUsername());

            if (user == null) {
                // 注册用户
                systemAccountDAO.insertUser(systemUserDTO);

                // 获取用户注册后的Mongo ID
                String userId = systemAccountDAO.findByUserName(systemUserDTO.getUserName()).getId();
                for (Map.Entry<String, ModuleAccountInfoDTO> entry : moduleAccountInfoDTOMap.entrySet()) {
                    entry.getValue().setUserId(userId);

                    if (Objects.equals(SystemNameConstant.HUIYAN_SYSTEM_NAME, entry.getKey())) {
                        try {
                            InsightWebsiteDTO insightWebsiteDTO = new InsightWebsiteDTO();

                            insightWebsiteDTO.setBname(entry.getValue().getBaiduUserName());
                            insightWebsiteDTO.setBpasswd(entry.getValue().getBaiduPassword());
                            insightWebsiteDTO.setRname(entry.getValue().getBaiduRemarkName());
                            insightWebsiteDTO.setUid(userId);

                            userAccountService.insertInsight(insightWebsiteDTO);
                        } catch (final Exception ignored) {

                        }
                    }

                    // 添加模块帐号
                    systemAccountDAO.insertModuleAccount(entry.getValue());
                }

//                accountRegisterDAO.regAccount(systemUserDTO);
                returnState = 1;

                // 发送邮件通知管理员
                EmailUtils.sendHtmlEmail("新用户注册", String.format(registerUserForAdmin, systemUserDTO.getUserName(), systemUserDTO.getCompanyName()), adminEmail);
            } else {
                returnState = -1;
            }
        }
        return returnState;
    }
}
