package com.perfect.app.bidding.controller;

import com.perfect.core.AppContext;
import com.perfect.entity.CustomGroupEntity;
import com.perfect.entity.KeywordImEntity;
import com.perfect.service.CustomGroupService;
import com.perfect.service.KeywordImService;
import com.perfect.utils.web.WebContextSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/19.
 */
@Controller
@RequestMapping("/importBid")
public class ImportKeywordBiddingController extends WebContextSupport {

    @Resource
    private CustomGroupService customGroupSerivice;

    @Resource
    private KeywordImService keywordImService;
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insertCustomGroup(@RequestParam(value = "gname") String groupName, HttpServletResponse response) {
        CustomGroupEntity customGroupEntity = new CustomGroupEntity();
        customGroupEntity.setAccountId(AppContext.getAccountId());
        customGroupEntity.setGroupName(groupName);
        customGroupSerivice.insert(customGroupEntity);
        String oid = customGroupEntity.getId();
        writeData(SUCCESS, response, oid);
        return null;
    }

    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ModelAndView findCustomGroupAll(HttpServletResponse response) {
        List<CustomGroupEntity> list = customGroupSerivice.findAll(AppContext.getAccountId());
        writeJson(list, response);
        return null;
    }

    @RequestMapping(value = "/getCustomTree", method = RequestMethod.GET)
    public ModelAndView getzTree(HttpServletResponse response) {
        Map<String, Object> map = customGroupSerivice.getCustomGroupTree();
        if (map.size() > 0) {
            writeJson(map, response);
        }
        return null;
    }

    @RequestMapping(value = "/addKeyword", method = RequestMethod.POST)
    public ModelAndView addKeyword(HttpServletResponse response,
                                    @RequestParam(value = "cgroupId", required = true) String cgroupId,
                                   @RequestParam(value = "imId", required = true) String[] imId,
                                   @RequestParam(value = "imap") String[] imap,
                                   @RequestParam(value = "imcost") String[] imcost,
                                   @RequestParam(value = "imimpression") String[] imimpression,
                                   @RequestParam(value = "imclick") String[] imclick,
                                   @RequestParam(value = "imctr") String[] imctr,
                                   @RequestParam(value = "imprice") String[] imprice,
                                   @RequestParam(value = "imcpc") String[] imcpc,
                                   @RequestParam(value = "imcpm") String[] imcpm,
                                   @RequestParam(value = "impcQuality") String[] impcQuality,
                                   @RequestParam(value = "immQuality") String[] immQuality,
                                   @RequestParam(value = "imstatusStr") String[] imstatusStr,
                                   @RequestParam(value = "impcDestinationUrl") String[] impcDestinationUrl,
                                   @RequestParam(value = "immobileDestinationUrl") String[] immobileDestinationUrl,
                                   @RequestParam(value = "imbiddingStatus") String[] imbiddingStatus,
                                   @RequestParam(value = "imrule") String[] imrule) {
        List<KeywordImEntity> keywordImEntities = new ArrayList<>();
        //判断是否选择的关键词不止一个，如果不止一个，就进行循环添加
        if (imap.length > 1) {
            for (int i = 0; i < imap.length; i++) {
                KeywordImEntity keywordImEntity = new KeywordImEntity();
                keywordImEntity.setCustomGroupId(cgroupId);
                keywordImEntity.setAccountId(AppContext.getAccountId());
                keywordImEntity.setKeyword(imap[i]);
                keywordImEntity.setCost(BigDecimal.valueOf(Double.parseDouble(imcost[i])));
                keywordImEntity.setImpression(Integer.valueOf(imimpression[i]));
                keywordImEntity.setClick(Integer.valueOf(imclick[i]));
                keywordImEntity.setCtr(Double.valueOf(imctr[i]));
                keywordImEntity.setCost(BigDecimal.valueOf(Double.parseDouble(imprice[i])));
                keywordImEntity.setCpc(BigDecimal.valueOf(Double.parseDouble(imcpc[i])));
                keywordImEntity.setCpm(BigDecimal.valueOf(Double.valueOf(imcpm[i])));
                keywordImEntity.setPcQuality(Integer.parseInt(impcQuality[i]));
                keywordImEntity.setmQuality(Integer.parseInt(immQuality[i]));
                keywordImEntity.setStatusStr(imstatusStr[i]);
                keywordImEntity.setPcDestinationUrl(impcDestinationUrl[i]);
                keywordImEntity.setMobileDestinationUrl(immobileDestinationUrl[i]);
                keywordImEntity.setBiddingStatus(getBidding(imbiddingStatus[i]));
                keywordImEntity.setRule(Boolean.parseBoolean(imrule[i]));
                keywordImEntities.add(keywordImEntity);
            }
            keywordImService.insertAll(keywordImEntities);
            //如果不是则只进行一次添加
        }else{
            KeywordImEntity keywordImEntity = new KeywordImEntity();
            keywordImEntity.setCustomGroupId(cgroupId);
            keywordImEntity.setAccountId(AppContext.getAccountId());
            keywordImEntity.setKeyword(imap[0]);
            keywordImEntity.setCost(BigDecimal.valueOf(Double.parseDouble(imcost[0])));
            keywordImEntity.setImpression(Integer.valueOf(imimpression[0]));
            keywordImEntity.setClick(Integer.valueOf(imclick[0]));
            keywordImEntity.setCtr(Double.valueOf(imctr[0]));
            keywordImEntity.setCost(BigDecimal.valueOf(Double.parseDouble(imprice[0])));
            keywordImEntity.setCpc(BigDecimal.valueOf(Double.parseDouble(imcpc[0])));
            keywordImEntity.setCpm(BigDecimal.valueOf(Double.valueOf(imcpm[0])));
            keywordImEntity.setPcQuality(Integer.parseInt(impcQuality[0]));
            keywordImEntity.setmQuality(Integer.parseInt(immQuality[0]));
            keywordImEntity.setStatusStr(imstatusStr[0]);
            keywordImEntity.setPcDestinationUrl(impcDestinationUrl[0]);
            keywordImEntity.setMobileDestinationUrl(immobileDestinationUrl[0]);
            keywordImEntity.setBiddingStatus(getBidding(imbiddingStatus[0]));
            keywordImEntity.setRule(Boolean.parseBoolean(imrule[0]));
            //
            keywordImService.insert(keywordImEntity);
        }
        writeHtml(SUCCESS,response);
        return null;
    }

    private int getBidding(String str) {
        switch (str) {
            case "已启动":
                return 1;
            case "已暂停":
                return 0;
            default:
                return 2;
        }
    }
}
