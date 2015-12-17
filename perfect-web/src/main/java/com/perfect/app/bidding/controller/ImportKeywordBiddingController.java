package com.perfect.app.bidding.controller;

import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.bidding.KeywordBiddingInfoDTO;
import com.perfect.autosdk.sms.v3.Quality10Type;
import com.perfect.commons.constants.KeywordStatusEnum;
import com.perfect.core.AppContext;
import com.perfect.dto.CustomGroupDTO;
import com.perfect.dto.StructureReportDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.keyword.KeywordImportDTO;
import com.perfect.service.*;
import com.perfect.utils.DateUtils;
import com.perfect.utils.json.JSONUtils;
import com.perfect.utils.NumberUtils;
import com.perfect.web.suport.WebContextSupport;
import com.perfect.utils.paging.PaginationParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/19.
 * 2014-11-16 refactor
 */
@Controller
@RequestMapping("/importBid")
public class ImportKeywordBiddingController extends WebContextSupport {

    @Resource
    private CustomGroupService customGroupSerivice;

    @Resource
    private KeywordImportService keywordImportService;

    @Resource
    private SysKeywordService sysKeywordService;

    @Resource
    private SysAdgroupService sysAdgroupService;

    @Resource
    private Keyword10QualityService keyword10QualityService;

    @Resource
    private SysCampaignService sysCampaignService;

    @Resource
    private BasisReportService basisReportService;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insertCustomGroup(@RequestParam(value = "gname") String groupName, HttpServletResponse response) {
        CustomGroupDTO customGroupEntityFind = customGroupSerivice.findByCustomName(groupName);
        if (customGroupEntityFind == null) {
            CustomGroupDTO customGroupEntity = new CustomGroupDTO();
            customGroupEntity.setAccountId(AppContext.getAccountId());
            customGroupEntity.setGroupName(groupName);
            String oid = customGroupSerivice.myInsert(customGroupEntity);
            writeData(SUCCESS, response, oid);
        } else {
            writeData(FAIL, response, null);
        }
        return null;
    }

    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ModelAndView findCustomGroupAll(HttpServletResponse response) {
        List<CustomGroupDTO> list = customGroupSerivice.findAll(AppContext.getAccountId());
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

    /**
     * 分组
     *
     * @param response
     * @param cgroupId
     * @param imId
     * @param imap
     * @param imbiddingStatus
     * @param imrule
     * @return
     */
    @RequestMapping(value = "/addKeyword", method = RequestMethod.POST)
    public ModelAndView addKeyword(HttpServletResponse response,
                                   @RequestParam(value = "cgroupId", required = true) String cgroupId,
                                   @RequestParam(value = "imId", required = true) String[] imId,
                                   @RequestParam(value = "imap") String[] imap,
                                   @RequestParam(value = "imbiddingStatus") String[] imbiddingStatus,
                                   @RequestParam(value = "imrule") String[] imrule,
                                   @RequestParam(value = "imadgroupId") String[] imadgroupId) {
        List<KeywordImportDTO> keywordImEntities = new ArrayList<KeywordImportDTO>();
        //判断是否选择的关键词不止一个，如果不止一个，就进行循环添加
        if (imap.length > 1) {
            for (int i = 0; i < imap.length; i++) {
                KeywordImportDTO keywordImportEntityFind = keywordImportService.findByKwdId(Long.valueOf(imId[i]));
                if (keywordImportEntityFind == null) {
                    KeywordImportDTO keywordImportEntity = new KeywordImportDTO();
                    keywordImportEntity.setAccountId(AppContext.getAccountId());
                    keywordImportEntity.setCustomGroupId(cgroupId);
                    keywordImportEntity.setKeywordId(Long.valueOf(imId[i]));
                    keywordImportEntity.setKeywordName(imap[i]);
                    keywordImportEntity.setBiddingStatus(imbiddingStatus[i]);
                    keywordImportEntity.setRule(Boolean.parseBoolean(imrule[i]));
                    keywordImportEntity.setAdgroupId(Long.valueOf(imadgroupId[i]));
                    keywordImEntities.add(keywordImportEntity);
                } else {
                    if (!keywordImportEntityFind.getCustomGroupId().equals(cgroupId)) {
                        keywordImportEntityFind.setCustomGroupId(cgroupId);
                        keywordImportService.update(keywordImportEntityFind);
                    }
                }
            }
            if (keywordImEntities.size() > 0) {
                keywordImportService.myInsertAll(keywordImEntities);
            }
            //如果不是则只进行一次添加
        } else {
            KeywordImportDTO keywordImportEntityFind = keywordImportService.findByKwdId(Long.valueOf(imId[0]));
            if (keywordImportEntityFind == null) {
                KeywordImportDTO keywordImportEntity = new KeywordImportDTO();
                keywordImportEntity.setAccountId(AppContext.getAccountId());
                keywordImportEntity.setCustomGroupId(cgroupId);
                keywordImportEntity.setKeywordId(Long.valueOf(imId[0]));
                keywordImportEntity.setKeywordName(imap[0]);
                keywordImportEntity.setBiddingStatus(imbiddingStatus[0]);
                keywordImportEntity.setRule(Boolean.parseBoolean(imrule[0]));
                keywordImportEntity.setAdgroupId(Long.valueOf(imadgroupId[0]));
                keywordImEntities.add(keywordImportEntity);
                keywordImportService.myInsert(keywordImportEntity);
            } else {
                if (!keywordImportEntityFind.getCustomGroupId().equals(cgroupId)) {
                    keywordImportEntityFind.setCustomGroupId(cgroupId);
                    keywordImportService.update(keywordImportEntityFind);
                }
            }
        }
        writeHtml(SUCCESS, response);
        return null;
    }

    @RequestMapping(value = "/loadData", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public ModelAndView loadData(
            @RequestParam(value = "cgId", required = false) String cgId,
            @RequestParam(value = "campaignId", required = false) Long campaignId,
            @RequestParam(value = "adgroupId", required = false) Long adgroupId,
            @RequestParam(value = "keywordName", required = false) String keywordName,
            @RequestParam(value = "s", required = false, defaultValue = "0") int skip,
            @RequestParam(value = "l", required = false, defaultValue = "20") int limit,
            @RequestParam(value = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(value = "o", required = false, defaultValue = "true") boolean asc
    ) {
        AbstractView jsonView = new MappingJackson2JsonView();
        List<KeywordDTO> entities = null;
        Integer total = 0;
        List<KeywordImportDTO> keywordImEntities = null;
        PaginationParam param = new PaginationParam();
        if (campaignId != null || adgroupId == null || keywordName != null) {
            param.setOrderBy(sort);
            param.setAsc(asc);
        } else {
            param.setOrderBy(sort);
            param.setAsc(asc);
            param.setStart(skip);
            param.setLimit(limit);
        }

        if (cgId != null) {
            keywordImEntities = keywordImportService.findByCgId(cgId);
            List<Long> keywordIds = new ArrayList<>(keywordImEntities.size());
            for (KeywordImportDTO kwd : keywordImEntities) {
                keywordIds.add(kwd.getKeywordId());
            }
            entities = sysKeywordService.findByIds(keywordIds, param);
        }
        //判定，如果只传入计划编号
        if (campaignId != null && adgroupId == null) {
            //查询单元列表
            List<AdgroupDTO> adgroupEntityList = sysAdgroupService.findIdByCampaignId(campaignId);
            //定义单元编号列表
            List<Long> adGroupIds = new ArrayList<>(adgroupEntityList.size());
            for (AdgroupDTO adgroupEntity : adgroupEntityList) {
                adGroupIds.add(adgroupEntity.getAdgroupId());
            }
            entities = sysKeywordService.findByIds(keywordImportService.findByAdgroupIds(adGroupIds));
            total = entities.size();
        } else if (campaignId != null && adgroupId != null) {
            entities = sysKeywordService.findByIds(keywordImportService.findByAdgroupId(adgroupId));
            total = entities.size();
        } else if (keywordName != null) {
            entities = sysKeywordService.findByIds(keywordImportService.findByKeywordName(keywordName));
            total = entities.size();
        }


        if (entities != null) {

            //根据筛选条件处理数据


            //获取entities的质量度
            Map<Long, KeywordBiddingInfoDTO> keywordReportDTOHashMap = new HashMap<>();
            List<KeywordBiddingInfoDTO> resultList = new ArrayList<>();

            List<Long> tmpKeywordIdList = new ArrayList<>();
            for (KeywordDTO entity : entities) {
                tmpKeywordIdList.add(entity.getKeywordId());
            }
            Map<Long, Quality10Type> quality10TypeMap = keyword10QualityService.getKeyword10Quality(tmpKeywordIdList);

            Integer index = 0;
            for (KeywordDTO entity : entities) {
                KeywordBiddingInfoDTO keywordBiddingInfoDTO = new KeywordBiddingInfoDTO();
                BeanUtils.copyProperties(entity, keywordBiddingInfoDTO);

                Long kwid = entity.getKeywordId();

                index++;
                AdgroupDTO adgroupEntity = sysAdgroupService.findByAdgroupId(entity.getAdgroupId());
                CampaignDTO campaignEntity = sysCampaignService.findById(adgroupEntity.getCampaignId());
                keywordBiddingInfoDTO.setCampaignName(campaignEntity.getCampaignName());
                keywordBiddingInfoDTO.setAdgroupName(adgroupEntity.getAdgroupName());

//                setting quality/暂时注释掉，配额不够！
                if (quality10TypeMap.size() > 0) {
                    keywordBiddingInfoDTO.setPcQuality(quality10TypeMap.get(kwid).getPcQuality());
                    keywordBiddingInfoDTO.setmQuality(quality10TypeMap.get(kwid).getMobileQuality());
                }
                if (entity.getStatus() != null) {
                    keywordBiddingInfoDTO.setStatusStr(KeywordStatusEnum.getName(entity.getStatus()));
                } else {
                    keywordBiddingInfoDTO.setStatusStr(KeywordStatusEnum.STATUS_UNKNOWN.name());
                }

                keywordReportDTOHashMap.put(entity.getKeywordId(), keywordBiddingInfoDTO);
                resultList.add(keywordBiddingInfoDTO);
            }
            String yesterday = DateUtils.getYesterdayStr();
            Map<String, List<StructureReportDTO>> reports = basisReportService.getKeywordReport(tmpKeywordIdList.toArray(new Long[tmpKeywordIdList.size()]), yesterday, yesterday, 0);
            List<StructureReportDTO> list = reports.get(yesterday);
            for (StructureReportDTO entity : list) {
                long kwid = entity.getKeywordId();
                KeywordBiddingInfoDTO dto = keywordReportDTOHashMap.get(kwid);
                dto.setClick(NumberUtils.getInteger(entity.getPcClick()));
                dto.setConversion(NumberUtils.getDouble(entity.getPcConversion()));
                dto.setCost(entity.getPcCost());
                dto.setCpc(entity.getPcCpc());
                dto.setCpm(entity.getPcCpm());
                dto.setCtr(NumberUtils.getDouble(entity.getPcCtr()));
                dto.setImpression(NumberUtils.getInteger(entity.getPcImpression()));
            }

            Map<String, Object> attributes = new HashMap<>();
            attributes = JSONUtils.getJsonMapData(resultList);
            if (total > 0) {
                attributes.put("records", total);
            }
            jsonView.setAttributesMap(attributes);
            return new ModelAndView(jsonView);
        } else {
            jsonView.setAttributesMap(new HashMap<String, Object>());
            return new ModelAndView(jsonView);
        }
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
    //删除方法
    @RequestMapping(value = "/deleteByCGId", method = RequestMethod.GET)
    public ModelAndView deleteByCGId(HttpServletResponse response, @RequestParam(value = "cgid", required = true, defaultValue = "") String cgid) {
        try {
            if (!cgid.equals("")) {
                keywordImportService.deleteByObjId(cgid);
            }
            writeHtml(SUCCESS, response);
        } catch (Exception e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }

        return null;
    }

    /**
     *
     * @param cgid 自定义分组id
     * @param kwdId 关键词id
     * @return
     */
    @RequestMapping(value = "/deleteBySelection",method = RequestMethod.GET)
    public ModelAndView deleteBySelection(@RequestParam(value = "cgid", required = true, defaultValue = "") String cgid, @RequestParam(value = "kwdId", required = true, defaultValue = "") String kwdId) {
        try {
            String[] kwds = kwdId.split(",");
            for (String kwd : kwds) {
                if (kwd.length() > 18) {
                    keywordImportService.deleteBySelectObj(cgid, kwd);
                }else{
                    keywordImportService.deleteBySelectLong(cgid, Long.valueOf(kwd));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return writeMapObject("error", EXCEPTION);
        }
        return writeMapObject("status", SUCCESS);
    }
}
