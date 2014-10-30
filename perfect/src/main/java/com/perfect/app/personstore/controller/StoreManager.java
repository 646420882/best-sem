package com.perfect.app.personstore.controller;

import com.perfect.service.KeywordGroupService;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/10/28.
 */
@Controller
@RequestMapping("/person")
public class StoreManager extends WebContextSupport {

    @Resource
    private KeywordGroupService keywordGroupService;

    @RequestMapping(value = "/getHyk",method = RequestMethod.GET)
    public ModelAndView getHyk(){
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String,Object> map=keywordGroupService.findTr();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }
    @RequestMapping(value = "/saveTr",method = RequestMethod.POST,produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView saveTr(HttpServletResponse response,@RequestParam(value = "tr")String tr,
                               @RequestParam(value = "cg")String cg,
                               @RequestParam(value = "gr")String gr,
                               @RequestParam(value = "kw")String kw,
                               @RequestParam(value = "url")String url
                               ){

        try{
           int count= keywordGroupService.saveTrade(tr,cg,gr,kw,url);
            if(count==1){
                writeData(SUCCESS,response,null);
            }else if(count==3){
                writeData(FAIL,response,null);
            }

        }catch (Exception e){
            e.printStackTrace();
            writeData(EXCEPTION,response,null);
        }
        return null;
    }
}
