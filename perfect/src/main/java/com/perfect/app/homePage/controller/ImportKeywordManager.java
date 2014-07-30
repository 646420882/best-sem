package com.perfect.app.homePage.controller;



import com.perfect.app.homePage.service.ImportKeywordService;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by XiaoWei on 2014/7/29.
 */
@Controller
public class ImportKeywordManager  extends WebContextSupport{

    public ImportKeywordService importKeywordService;

    @RequestMapping("/main/getImportKeywordList")
    public void getImportKeywordList(HttpServletRequest request,ModelMap modelMap){
        List<KeywordRealTimeDataVOEntity> list=importKeywordService.getList(request);

    }

}
