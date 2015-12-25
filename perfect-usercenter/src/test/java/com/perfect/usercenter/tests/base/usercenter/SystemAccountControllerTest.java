package com.perfect.usercenter.tests.base.usercenter;

import com.google.common.collect.Lists;
import com.perfect.commons.constants.SystemNameConstant;
import com.perfect.dao.account.SystemAccountDAO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.sys.SystemUserModuleDTO;
import com.perfect.dto.sys.UserModuleMenuDTO;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created on 2015-12-22.
 *
 * @author dolphineor
 */
public class SystemAccountControllerTest extends JUnitBaseTest {

    @Autowired
    private SystemAccountDAO systemAccountDAO;


    @Test
    public void insertSystemUser() throws Exception {
        SystemUserDTO systemUserDTO = new SystemUserDTO();
        systemUserDTO.setUserName("perfect2017");
        systemUserDTO.setPassword("aec34f5b755ae6f9550620d5f502d4ff");
        systemUserDTO.setCompanyName("普菲特广告有限公司");
        systemUserDTO.setContactName("Yangle");
        systemUserDTO.setPayed(true);
        systemUserDTO.setCtime(1450810594776L);
        systemUserDTO.setEmail("perfect-cn@perfect-cn.com");
        systemUserDTO.setAddress("北京 · 朝阳 · 北苑路36号金苑大厦402");
        systemUserDTO.setStartTime(Calendar.getInstance().getTimeInMillis());
        systemUserDTO.setEndTime(Calendar.getInstance().getTimeInMillis() + 3600_000 * 24 * 365);
        systemUserDTO.setAccountState(1);
        systemUserDTO.setAccess(2);
        systemUserDTO.setState(1);
        systemUserDTO.setTelephone("010-95658819");
        systemUserDTO.setMobilePhone("18065985236");


        List<SystemUserModuleDTO> userModuleDTOs = Lists.newArrayList();

        // 搜客模块
        SystemUserModuleDTO systemUserModuleDTO = new SystemUserModuleDTO();
        systemUserModuleDTO.setId(new ObjectId().toString());
//        systemUserModuleDTO.setModuleId(systemAccountDAO.findByModuleName(SystemNameConstant.SOUKE_SYSTEM_NAME).getId());
        systemUserModuleDTO.setEnabled(true);
        systemUserModuleDTO.setStartTime(Calendar.getInstance().getTimeInMillis());
        systemUserModuleDTO.setEndTime(Calendar.getInstance().getTimeInMillis() + 3600_000 * 24 * 365);
        systemUserModuleDTO.setPayed(true);
        systemUserModuleDTO.setModuleName(SystemNameConstant.SOUKE_SYSTEM_NAME);
        systemUserModuleDTO.setModuleUrl(SystemNameConstant.SOUKE_SYSTEM_URL);
        // 搜客模块菜单
        UserModuleMenuDTO userModuleMenuDTO = new UserModuleMenuDTO();
        userModuleMenuDTO.setModuleName(systemUserModuleDTO.getModuleName());
        userModuleMenuDTO.setModuleUrl(systemUserModuleDTO.getModuleUrl());

        systemUserModuleDTO.getMenus().addAll(new ArrayList<String>() {{
            add("账户全景");
            add("推广助手");
            add("智能结构");
            add("智能竞价");
            add("数据报告");
        }});
        userModuleDTOs.add(systemUserModuleDTO);


        // 慧眼模块
        SystemUserModuleDTO systemUserModuleDTO1 = new SystemUserModuleDTO();
        systemUserModuleDTO1.setId(new ObjectId().toString());
//        systemUserModuleDTO1.setModuleId(systemAccountDAO.findByModuleName(SystemNameConstant.HUIYAN_SYSTEM_NAME).getId());
        systemUserModuleDTO1.setEnabled(true);
        systemUserModuleDTO1.setStartTime(Calendar.getInstance().getTimeInMillis());
        systemUserModuleDTO1.setEndTime(Calendar.getInstance().getTimeInMillis() + 3600_000 * 24 * 365);
        systemUserModuleDTO1.setPayed(true);
        systemUserModuleDTO1.setModuleName(SystemNameConstant.HUIYAN_SYSTEM_NAME);
        systemUserModuleDTO1.setModuleUrl(SystemNameConstant.HUIYAN_SYSTEM_URL);

        // 慧眼模块菜单
        UserModuleMenuDTO userModuleMenuDTO1 = new UserModuleMenuDTO();
        userModuleMenuDTO1.setModuleName(systemUserModuleDTO1.getModuleName());
        userModuleMenuDTO1.setModuleUrl(systemUserModuleDTO1.getModuleUrl());
        List<String> menus = Lists.newArrayList();
        menus.add("网站概览");
        menus.add("趋向分析|实时访客");
        menus.add("趋向分析|今日统计");
        menus.add("趋向分析|昨日统计");
        menus.add("趋向分析|最近30天");
        menus.add("来源分析|全部来源");
        menus.add("来源分析|搜索引擎");
        menus.add("来源分析|搜索词");
        menus.add("来源分析|外部链接");
        menus.add("来源分析|来源变化榜");
        menus.add("页面分析|受访页面");
        menus.add("页面分析|入口页面");
        menus.add("页面分析|页面热点图");
        menus.add("访客分析|访客地图");
        menus.add("访客分析|设备环境");
        menus.add("访客分析|新老访客");
        menus.add("价值透析|流量地图");
        menus.add("价值透析|频道流转");
        menus.add("转化分析");
        menus.add("指定广告跟踪");
        menus.add("同类群组分析");
//        userModuleMenuDTO1.getMenus().addAll(menus);
        systemUserModuleDTO1.getMenus().addAll(menus);
        userModuleDTOs.add(systemUserModuleDTO1);

        systemUserDTO.setSystemUserModules(userModuleDTOs);

        systemAccountDAO.insertUser(systemUserDTO);

    }

    @Test
    public void insertModuleAccount() {
        ModuleAccountInfoDTO moduleAccountInfoDTO = new ModuleAccountInfoDTO();
        moduleAccountInfoDTO.setUserId("567a50f860b2f16482cd77a6");
        moduleAccountInfoDTO.setModuleId("567a50f860b2f16482cd77a4");
        moduleAccountInfoDTO.setAccountBindingTime(Calendar.getInstance().getTimeInMillis());
        moduleAccountInfoDTO.setBaiduAccountId(10394588L);
        moduleAccountInfoDTO.setBaiduPassword("Ab1234890");
        moduleAccountInfoDTO.setBaiduUserName("baidu-perfect2151880");
        moduleAccountInfoDTO.setAccountPlatformType("百思搜客");
        moduleAccountInfoDTO.setToken("2c5fb53fc0003f407bc495b391d05e2e");
        moduleAccountInfoDTO.setBaiduRemarkName("perfect-2016");
        moduleAccountInfoDTO.setBalance(496898.33);
        moduleAccountInfoDTO.setCost(621.67);
        moduleAccountInfoDTO.setPayment(497520.0);
        moduleAccountInfoDTO.setBudgetType(1);
        moduleAccountInfoDTO.setBudget(201.0);
        moduleAccountInfoDTO.setRegionTarget(Lists.newArrayList(1000, 2000, 28000));
        moduleAccountInfoDTO.setRegDomain("perfect-cn.cn");
        moduleAccountInfoDTO.setBestRegDomain("www.best-ad.cn");
        moduleAccountInfoDTO.setOpenDomains(Lists.newArrayList("best-ad.cn", "perfect-cn.cn"));
        moduleAccountInfoDTO.setState(1L);

        systemAccountDAO.insertModuleAccount(moduleAccountInfoDTO);
    }
}
