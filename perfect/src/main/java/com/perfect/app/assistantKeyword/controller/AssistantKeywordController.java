package com.perfect.app.assistantKeyword.controller;

import com.perfect.entity.KeywordEntity;
import com.perfect.service.AssistantKeywordService;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Created by john on 2014/8/14.
 */
@RestController
@Scope("prototype")
public class AssistantKeywordController {


    @Resource
    private AssistantKeywordService assistantKeywordService;


    /**
     * 得到所有的关键词
     * @return
     */
    @RequestMapping(value = "assistantKeyword/list",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView getAllKeywordList(ModelMap model){
        model.addAttribute("list",assistantKeywordService.getAllKeyWord());
        return new ModelAndView("");
    }

    /**
     * 根据一个或者多个关键词id删除关键词
     * @param kwids
     * @return
     */
    @RequestMapping(value = "assistantKeyword/deleteById" ,method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView deleteKeywordById(Long[] kwids){
        assistantKeywordService.deleteByKwIds(Arrays.asList(kwids));
        return new ModelAndView();
    }


    /**
     * 修改以下参数的信息(!!!)
     * @param kwid
     * @param price
     * @param pcDestinationUrl
     * @param mobileDestinationUrl
     * @param phraseType
     * @param pause
     * @return
     */
    @RequestMapping(value = "assistantKeyword/update*",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView updateKeywordName(Long kwid,Double price,String pcDestinationUrl,String mobileDestinationUrl,Integer phraseType,Boolean pause){
        KeywordEntity keywordEntity = new KeywordEntity();
        keywordEntity.setKeywordId(kwid);
        keywordEntity.setPrice(price);
        keywordEntity.setPcDestinationUrl(pcDestinationUrl);
        keywordEntity.setMobileDestinationUrl(mobileDestinationUrl);
        keywordEntity.setPhraseType(phraseType);
        keywordEntity.setPause(pause);
        assistantKeywordService.updateKeyword(keywordEntity);

        return new ModelAndView();
    }

    /**
     * 测试页面
     * @return
     */
    @RequestMapping(value = "assistantKeyword/showTestPage",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView showTestPage(){
        return new ModelAndView("promotionAssistant/test");
    }


    /**
     * 批量删除关键词(第二种方式)
     * @return
     */
    @RequestMapping(value = "assistantKeyword/deleteByName")
    public ModelAndView deleteKeywordByNames(String deleteInfos){
        String[] everyDeleInfo = deleteInfos.split("\r\n");

        for(String str:everyDeleInfo){
            String[] fields = str.split(",|\t");
//                List<CampaignEntity> campaignEntityList = assistantKeywordService.find(new Query().addCriteria(Criteria.where("name").is(fields[0])));
//                CampaignEntity campaignEntity = campaignEntityList==null||campaignEntityList.size()==0?null:campaignEntityList.get(0);

           /* if(campaignEntity != null){

            }*/

        }

        return new ModelAndView();
    }

}
