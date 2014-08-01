package com.perfect.app.homePage.controller;



import com.google.gson.Gson;
import com.perfect.app.homePage.service.ImportKeywordService;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/7/29.
 */
@RestController
@Scope("prototype")
public class ImportKeywordManager  extends WebContextSupport{

    @Resource
    public ImportKeywordService importKeywordService;

    @RequestMapping(value="/main/getImportKeywordList",method = {RequestMethod.GET, RequestMethod.POST})
    public void getImportKeywordList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<KeywordRealTimeDataVOEntity> list=importKeywordService.getMap(request);
        String data=new Gson().toJson(list);
        writeHtml(data, response);

    }

}
