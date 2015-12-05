package com.perfect.app.count.controller;

import com.perfect.web.support.WebContextSupport;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.baidu.BaiduAccountInfoNoPasswordDTO;
import com.perfect.service.AccountManageService;
import com.perfect.utils.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by XiaoWei on 2014/12/17.
 */
@Controller
@RequestMapping(value = "/pftstis")
public class DomainController extends WebContextSupport {
    @Resource
    AccountManageService manageService;

    @RequestMapping(value = "/getDomainList")
    public ModelAndView getDomainList(@RequestParam(value = "account")String account){
        List<BaiduAccountInfoDTO> accountInfoDTOs=manageService.getAllBaiduAccount();
        List<BaiduAccountInfoNoPasswordDTO> baiduAccountInfoNoPasswordDTOs =ObjectUtils.convert(accountInfoDTOs,BaiduAccountInfoNoPasswordDTO.class);

        return writeMapObject(DATA,baiduAccountInfoNoPasswordDTOs);
    }
}
