package com.perfect.app.personstore.controller;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.entity.LexiconEntity;
import com.perfect.redis.JRedisUtils;
import com.perfect.service.KeywordGroupService;
import com.perfect.commons.web.WebContextSupport;
import com.perfect.utils.PagerInfo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/10/28.
 * 2014-11-26 refactor
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
                               @RequestParam(value = "url",defaultValue = "")String url
                               ){

        try{
           int count= keywordGroupService.saveTrade(tr,cg,gr,kw,url);
            if(count==1){
                writeData(SUCCESS,response,null);
                Jedis jc= JRedisUtils.get();
                if(jc.exists(MongoEntityConstants.TRADE_KEY)){
                    jc.del(MongoEntityConstants.TRADE_KEY);
                }
            }else if(count==3){
                writeData(FAIL,response,null);
            }

        }catch (Exception e){
            e.printStackTrace();
            writeData(EXCEPTION,response,null);
        }
        return null;
    }
    @RequestMapping(value = "/findPager",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView findPager(HttpServletResponse response,
                                  @RequestParam(value = "trade",required = false,defaultValue = "")String trad,
                                  @RequestParam(value = "category",required = false,defaultValue = "")String category,
                                    @RequestParam(value = "page",required = false,defaultValue = "0")int nowPage,
                                   @RequestParam(value = "limit",required = false,defaultValue = "20")int pageSize){
        PagerInfo pagerInfo=null;
        Map<String,Object> map=new HashMap<>();
        if(!trad.equals("")&&!category.equals("")){
            map.put("tr",trad);
            map.put("cg",category);
            pagerInfo  =keywordGroupService.findByPager(map,nowPage,pageSize);
        }else if(!trad.equals("")&&category.equals("")){
            map.put("tr",trad);
            pagerInfo  =keywordGroupService.findByPager(map,nowPage,pageSize);
        }else{
            pagerInfo=keywordGroupService.findByPager(new HashMap<String, Object>(),nowPage,pageSize);
        }
        writeJson(pagerInfo, response);
        return null;
    }
    @RequestMapping(value = "/deleteByParams",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteByParams(HttpServletResponse response,
                                       @RequestParam(value = "trade",defaultValue = "",required = true) String trade,
                                       @RequestParam(value = "keyword",defaultValue = "",required = true)String keyword){
        try{
            MongoTemplate mongoTemplate= BaseMongoTemplate.getSysMongo();
            Query q=new Query();
            q.addCriteria(Criteria.where("tr").is(trade).and("kw").is(keyword));
            mongoTemplate.remove(q, LexiconEntity.class);
            writeData(SUCCESS, response, null);
            Jedis jc= JRedisUtils.get();
            if(jc.exists(MongoEntityConstants.TRADE_KEY)){
                jc.del(MongoEntityConstants.TRADE_KEY);
            }
        }catch (Exception e){
            e.printStackTrace();
            writeData(EXCEPTION,response,null);
        }
        return null;
    }

    @RequestMapping(value = "/updateByParams",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public  ModelAndView updateByParams(HttpServletResponse response,
                                        @RequestParam(value = "id",required = true)String id,
                                        @RequestParam(value = "trade",required = true)String trade,
                                        @RequestParam(value = "category",required = false,defaultValue = "")String category,
                                        @RequestParam(value = "group",required = false,defaultValue = "")String group,
                                        @RequestParam(value = "keyword",required = true)String keyword,
                                        @RequestParam(value = "url",required = true)String url){
        MongoTemplate mongoTemplate=BaseMongoTemplate.getSysMongo();
        Update up=new Update();
        up.set("tr", trade);
        up.set("cg", category);
        up.set("gr",group);
        up.set("kw",keyword);
        up.set("url",url);
        try{
            mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)),up,LexiconEntity.class);
            writeData(SUCCESS,response,null);
        }catch (Exception e){
            e.printStackTrace();
            writeData(FAIL,response,null);
        }
        return null;
    }
}
