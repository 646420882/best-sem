package com.perfect.app.assistant.controller;

import com.google.common.collect.Lists;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.commons.web.WebContextSupport;
import com.perfect.core.AppContext;
import com.perfect.dto.StructureReportDTO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.campaign.CampaignTreeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.keyword.KeywordInfoDTO;
import com.perfect.dto.keyword.SearchwordReportDTO;
import com.perfect.param.SearchFilterParam;
import com.perfect.service.*;
import com.perfect.service.AdgroupService;
import com.perfect.utils.IdConvertUtils;
import com.perfect.utils.csv.UploadHelper;
import com.perfect.utils.paging.PagerInfo;
import com.perfect.utils.report.AssistantKwdUtil;
import com.perfect.utils.report.BasistReportPCPlusMobUtil;
import com.perfect.vo.ValidateKeywordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Created by john on 2014/8/14.
 * 2014-11-28 refactor XiaoWei
 */
@RestController
@Scope("prototype")
public class AssistantKeywordController extends WebContextSupport {
    private static final String RES_SUCCESS = "success";
    private static Integer OBJ_SIZE = 18;

    @Resource
    private AssistantKeywordService assistantKeywordService;

    @Resource
    private KeywordBackUpService keywordBackUpService;

    @Resource
    private BaiduAccountService baiduAccountService;

    @Resource
    private SysRegionalService sysRegionalService;

    @Resource
    private BasisReportDownService basisReportDownService;

    @Resource
    private KeywordDeduplicateService keywordDeduplicateService;

    @Resource
    private AdgroupService adgroupService;

    /**
     * 批量添加或者修改关键词
     */
    @RequestMapping(value = "assistantKeyword/batchAddOrUpdate", method = RequestMethod.POST)
    public void batchAddkeyword(
            @RequestParam(value = "isReplace") Boolean isReplace,
            @RequestParam(value = "cids") String cids,
            @RequestParam(value = "aids") String aids,
            @RequestParam(value = "kwds") String kwds,
            @RequestParam(value = "mts") String mts,
            @RequestParam(value = "pts") String pts,
            @RequestParam(value = "prices") String prices,
            @RequestParam(value = "pcs") String pcs,
            @RequestParam(value = "mibs") String mibs,
            @RequestParam(value = "pauses") String pauses,
            @RequestParam(value = "index") Integer index,
            HttpServletResponse response) {
        try {
            if (aids.contains("\n")) {
                String[] cidStr = cids.split("\n");
                String[] aidStr = aids.split("\n");
                String[] kwdStr = kwds.split("\n");
                String[] mtStr = mts.split("\n");
                String[] pricesStr = prices.split("\n");
                String[] pcsStr = pcs.split("\n");
                String[] mibStr = mibs.split("\n");
                String[] ptsStr = pts.split("\n");
                String[] pauseStr = pauses.split("\n");
                for (int i = 0; i < aidStr.length; i++) {
                    innerUpdate(cidStr[i], isReplace, kwdStr[i], aidStr[i], mtStr[i], ptsStr[i], pricesStr[i], pcsStr[i], mibStr[i], pauseStr[i], index);
                }
            } else {
                innerUpdate(cids, isReplace, kwds, aids, mts, pts, prices, pcs, mibs, pauses, index);
            }
            writeHtml(SUCCESS, response);
        } catch (Exception e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }

    }

    private void innerUpdate(String cid, Boolean isReplace, String name, String aid, String mt, String pt, String price, String pc, String mib, String pause, Integer index) {
        if (pc.equals("空")) {
            pc = null;
        }
        if (mib.equals("空")) {
            mib = null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        if (index == 1) {
            AdgroupDTO adgroupDTO = adgroupService.autoBAG(cid, aid);
            if (adgroupDTO.getAdgroupId() != null) {
                map.put(MongoEntityConstants.ADGROUP_ID,adgroupDTO.getAdgroupId());
                aid=adgroupDTO.getAdgroupId().toString();
            } else {
                map.put(MongoEntityConstants.OBJ_ADGROUP_ID, adgroupDTO.getId());
                aid=adgroupDTO.getId();
            }
        } else {
            if (aid.length() > 18) {
                map.put(MongoEntityConstants.OBJ_ADGROUP_ID, aid);
            } else {
                map.put(MongoEntityConstants.ADGROUP_ID, Long.valueOf(aid));
            }
        }
        //如果查询到有关键词名为此的，需要替换
        KeywordDTO dto = assistantKeywordService.findByParams(map);
        //如果能查到匹配的数据，则执行修改操作
        if (dto != null) {
            if (isReplace) {
                KeywordDTO keywordDTOFind = null;
                //判断如果该条数据不为已经同步的数据，则视为本地数据，本地数据库数据修改则不需要备份操作
                if (dto.getKeywordId() == null) {
                    keywordDTOFind = assistantKeywordService.findByObjId(dto.getId());
                    keywordDTOFind.setKeyword(name);
                    keywordDTOFind.setMatchType(Integer.parseInt(mt));
                    keywordDTOFind.setPhraseType(Integer.parseInt(pt));
                    keywordDTOFind.setPrice(BigDecimal.valueOf(Double.parseDouble(price)));
                    keywordDTOFind.setPcDestinationUrl(pc);
                    keywordDTOFind.setMobileDestinationUrl(mib);
                    keywordDTOFind.setPause(Boolean.parseBoolean(pause));
                    keywordDTOFind.setLocalStatus(1);
                    assistantKeywordService.updateByObjId(keywordDTOFind);
                } else { //如果已经是同步到本地的数据，则要执行备份操作，将这条数据备份到备份数据库中
                    keywordDTOFind = assistantKeywordService.findByLongId(dto.getKeywordId());
                    KeywordDTO keywordDTOBackUp = new KeywordDTO();
                    keywordDTOFind.setLocalStatus(2);
                    BeanUtils.copyProperties(keywordDTOFind, dto);
                    keywordDTOFind.setKeyword(name);
                    keywordDTOFind.setMatchType(Integer.parseInt(mt));
                    keywordDTOFind.setPhraseType(Integer.parseInt(pt));
                    keywordDTOFind.setPrice(BigDecimal.valueOf(Double.parseDouble(price)));
                    keywordDTOFind.setPcDestinationUrl(pc);
                    keywordDTOFind.setMobileDestinationUrl(mib);
                    keywordDTOFind.setPause(Boolean.parseBoolean(pause));
                    assistantKeywordService.update(keywordDTOFind, keywordDTOBackUp);
                }
            }
        } else {
            KeywordDTO insertDTO = new KeywordDTO();
            insertDTO.setAccountId(AppContext.getAccountId());
            insertDTO.setLocalStatus(1);
            insertDTO.setKeyword(name);
            insertDTO.setMatchType(Integer.parseInt(mt));
            insertDTO.setPhraseType(Integer.parseInt(pt));
            insertDTO.setPrice(BigDecimal.valueOf(Double.parseDouble(price)));
            insertDTO.setPcDestinationUrl(pc);
            insertDTO.setMobileDestinationUrl(mib);
            insertDTO.setStatus(-1);
            insertDTO.setPause(Boolean.parseBoolean(pause));
            if (aid.length() > 18) {
                insertDTO.setAdgroupObjId(aid);
                insertDTO.setKeywordId(null);
            } else {
                insertDTO.setAdgroupId(Long.parseLong(aid));
            }
            assistantKeywordService.insert(insertDTO);
        }
    }

    @RequestMapping(value = "assistantKeyword/vaildateKeyword")
    private void vaildateKeyword(@RequestParam(value = "isReplace") Boolean isReplace,
                                 @RequestParam(value = "cids") String cids,
                                 @RequestParam(value = "aids") String aids,
                                 @RequestParam(value = "kwds") String kwds,
                                 @RequestParam(value = "mts") String mts,
                                 @RequestParam(value = "pts") String pts,
                                 @RequestParam(value = "prices") String prices,
                                 @RequestParam(value = "pcs") String pcs,
                                 @RequestParam(value = "mibs") String mibs,
                                 @RequestParam(value = "pauses") String pauses,
                                 @RequestParam(value = "index") Integer index,
                                 HttpServletResponse response) {
        try {
            if (aids.contains("\n")) {
                String[] cidStr = cids.split("\n");
                String[] aidStr = aids.split("\n");
                String[] kwdStr = kwds.split("\n");
                String[] mtStr = mts.split("\n");
                String[] pricesStr = prices.split("\n");
                String[] pcsStr = pcs.split("\n");
                String[] mibStr = mibs.split("\n");
                String[] ptsStr = pts.split("\n");
                String[] pauseStr = pauses.split("\n");

                List<KeywordInfoDTO> baseKeywordDto = new ArrayList<>();
                for (int i = 0; i < aidStr.length; i++) {
                    KeywordInfoDTO keywordInfoDTO = new KeywordInfoDTO();
                    keywordInfoDTO.setCampaignName(cidStr[i]);
                    keywordInfoDTO.setAdgroupName(aidStr[i]);
                    keywordInfoDTO.setKeyword(kwdStr[i]);
                    KeywordDTO keywordDTO = new KeywordDTO();
                    keywordDTO.setKeyword(kwdStr[i]);
                    keywordDTO.setMatchType(Integer.valueOf(mtStr[i]));
                    keywordDTO.setPrice(BigDecimal.valueOf(Double.valueOf(pricesStr[i])));
                    keywordDTO.setPhraseType(Integer.valueOf(ptsStr[i]));
                    keywordDTO.setPcDestinationUrl(pcsStr[i]);
                    keywordDTO.setMobileDestinationUrl(mibStr[i]);
                    keywordDTO.setPause(Objects.equals(pauseStr[i], "true") ? true : false);
                    keywordInfoDTO.setObject(keywordDTO);
                    baseKeywordDto.add(keywordInfoDTO);
//                    innerUpdate(cidStr[i], isReplace, kwdStr[i], aidStr[i], mtStr[i], ptsStr[i], pricesStr[i], pcsStr[i], mibStr[i], pauseStr[i], index);
                }


                ValidateKeywordVO vk = new ValidateKeywordVO();
                List<KeywordInfoDTO> dbExistKeywordDto = null;
                Map<String, List<KeywordInfoDTO>> mapx = baseKeywordDto
                        .stream()
                        .collect(Collectors.groupingBy(KeywordInfoDTO::getKeyword));

                List<KeywordInfoDTO> selfList = new ArrayList<>();
                for (Map.Entry<String, List<KeywordInfoDTO>> k : mapx.entrySet()) {
                    List<KeywordInfoDTO> keywordDTOs = k.getValue();
                    selfList.add(keywordDTOs.get(0));
                }
                vk.setEndGetCount(baseKeywordDto.size() - selfList.size());
                vk.setSafeKeywordList(selfList);
                if (selfList.size() > 0) {
                    List<String> keywords = new ArrayList<>();
                    selfList.stream().forEach(k -> {
                        keywords.add(k.getKeyword());
                    });
                    List<String> existKwd = keywordDeduplicateService.deduplicate(AppContext.getAccountId(), keywords);
                    if (existKwd.size() > 0) {
                        dbExistKeywordDto = assistantKeywordService.vaildateKeywordByIds(existKwd);
                    }
                    vk.setDbExistKeywordList(dbExistKeywordDto);

                    writeJson(vk, response);
                }
            } else {

                List<String> existKwd = keywordDeduplicateService.deduplicate(AppContext.getAccountId(), new ArrayList<String>() {{
                    add(kwds);
                }});
                List<KeywordInfoDTO> keywordDTOs = assistantKeywordService.vaildateKeywordByIds(existKwd);
                writeJson(keywordDTOs, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            writeHtml(EXCEPTION, response);
        }

    }


    /**
     * 得到当前账户所有的关键词
     *
     * @return
     */
    @RequestMapping(value = "assistantKeyword/list", method = {RequestMethod.GET, RequestMethod.POST})
    public void getAllKeywordList(HttpServletResponse response,
                                  @RequestParam(value = "cid", required = false, defaultValue = "") String cid,
                                  @RequestParam(value = "aid", required = false, defaultValue = "") String aid,
                                  @RequestParam(value = "nowPage") int nowPage,
                                  @RequestParam(value = "pageSize") int pageSize) {
        PagerInfo page = null;
        if (!aid.equals("-1")) {
            page = assistantKeywordService.getKeyWords(cid, aid, nowPage, pageSize, null);
        }
        writeJson(page, response);
    }

    /**
     * 根据一个或者多个关键词id删除关键词
     *
     * @param kwids
     * @return
     */
    @RequestMapping(value = "assistantKeyword/deleteById", method = {RequestMethod.GET, RequestMethod.POST})
    public void deleteKeywordById(HttpServletResponse response, String kwids) {
        String[] ids = kwids.split(",");
        assistantKeywordService.deleteByKwIds(Arrays.asList(ids));
        writeJson(RES_SUCCESS, response);
    }


    /**
     * 修改以下参数的信息
     *
     * @param kwid
     * @param price
     * @param pcDestinationUrl
     * @param mobileDestinationUrl
     * @param matchType
     * @param phraseType
     * @param pause
     * @return
     */
    @RequestMapping(value = "assistantKeyword/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public void updateKeywordName(HttpServletResponse response, String kwid, Double price, String pcDestinationUrl, String mobileDestinationUrl, Integer matchType, Integer phraseType, Boolean pause) {
        String regex = "^\\d+$";

        KeywordDTO keywordEntity = new KeywordDTO();

        if (kwid.matches(regex) == true) {
            keywordEntity.setKeywordId(Long.parseLong(kwid));
        } else {
            keywordEntity.setId(kwid);
        }
        keywordEntity.setPrice(price != null ? BigDecimal.valueOf(price) : null);
        keywordEntity.setPcDestinationUrl(pcDestinationUrl);
        keywordEntity.setMobileDestinationUrl(mobileDestinationUrl);
        keywordEntity.setMatchType(matchType);
        keywordEntity.setPhraseType(phraseType);
        keywordEntity.setPause(pause);
        KeywordDTO keywordDTO = assistantKeywordService.updateKeyword(keywordEntity);
        writeJson(keywordDTO, response);
    }


    /**
     * 得到当前账户的推广计划的树形列表数据
     *
     * @return
     */
    @RequestMapping(value = "assistantKeyword/campaignTree", method = {RequestMethod.GET, RequestMethod.POST})
    public void getCampaignTree(HttpServletResponse response) {
        List<CampaignTreeDTO> treeList = assistantKeywordService.getCampaignTree(AppContext.getAccountId());
        writeJson(treeList, response);
    }

    /**
     * (选择计划和单元，只输入关键词名称的方式)
     * 根据用户的选择计划和单元，以及输入的关键词名称进行批量删除关键词
     *
     * @param chooseInfos  用户选择的一个或多个计划和单元
     * @param keywordNames 用户输入的一个或多个的关键词名称
     * @return
     */
    @RequestMapping(value = "assistantKeyword/deleteByNameChoose", method = {RequestMethod.GET, RequestMethod.POST})
    public void deleteKeywordByNamesChoose(HttpServletResponse response, String chooseInfos, String keywordNames, Integer nowPage, Integer pageSize) {
        Map<String, Object> map = assistantKeywordService.validateDeleteKeywordByChoose(AppContext.getAccountId(), chooseInfos, keywordNames, nowPage, pageSize);
        writeJson(map, response);
    }


    /**
     * （输入的方式)
     * 根据用户输入的删除信息（计划名称，单元名称，关键词名称）批量删除关键词
     *
     * @param deleteInfos 用户输入的一系列的计划名称，单元名称，关键词名称
     * @return
     */
    @RequestMapping(value = "assistantKeyword/validateDeleteByInput", method = {RequestMethod.GET, RequestMethod.POST})
    public void validateDeleteByInput(HttpServletResponse response, String deleteInfos) {
        Map<String, Object> map = assistantKeywordService.validateDeleteByInput(AppContext.getAccountId(), deleteInfos);
        writeJson(map, response);
    }


    /**
     * （用户选择计划，单元，输入关键词的方式）
     * 将用户输入的关键词信息添加或更新到数据库
     *
     * @param isReplace    是否将用户输入的信息替换该单元下相应的内容
     * @param chooseInfos  用户选择的推广计划和推广单元
     * @param keywordInfos 用户输入的多个关键词信息
     * @return
     */
    @RequestMapping(value = "assistantKeyword/addOrUpdateKeywordByChoose", method = {RequestMethod.GET, RequestMethod.POST})
    public void batchAddOrUpdateKeywordByChoose(HttpServletResponse response, Boolean isReplace, String chooseInfos, String keywordInfos) {
        Map<String, Object> map = assistantKeywordService.batchAddOrUpdateKeywordByChoose(AppContext.getAccountId(), isReplace, chooseInfos, keywordInfos);
        writeJson(map, response);
    }

    /**
     * 显示批量删除弹出窗口
     *
     * @return
     */
    @RequestMapping(value = "assistantKeyword/showBatchDelDialog", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showBatchDelDialog() {
        return new ModelAndView("promotionAssistant/alert/batchDelKeyword");
    }


    /**
     * 显示批量添加更新弹出窗口
     *
     * @return
     */
    @RequestMapping(value = "assistantKeyword/showAddOrUpdateKeywordDialog", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showAddOrUpdateKeywordDialog() {
        return new ModelAndView("promotionAssistant/alert/addOrUpdateKeyword");
    }


    /**
     * 显示搜索词报告弹出窗口
     *
     * @return
     */
    @RequestMapping(value = "assistantKeyword/showSearchWordDialog", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showSearchWordDialog() {
        return new ModelAndView("promotionAssistant/alert/searchwordReport");
    }

    /**
     * 显示定时暂停更新弹出窗口
     *
     * @return
     */

    @RequestMapping(value = "assistantKeyword/showTimingPauseDialog", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showTimingPauseDialog() {
        return new ModelAndView("promotionAssistant/alert/TimingPauseDialog");
    }

    /**
     * 显示定时上传更新弹出窗口
     *
     * @return
     */
    @RequestMapping(value = "assistantKeyword/showTimingDelDialog", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showTimingDelDialog() {
        return new ModelAndView("promotionAssistant/alert/TimingDelDialog");
    }

    /**
     * 还原新增的关键词
     *
     * @param response
     * @param id
     */
    @RequestMapping(value = "assistantKeyword/reducAdd", method = {RequestMethod.GET, RequestMethod.POST})
    public void reducAdd(HttpServletResponse response, String id) {
        assistantKeywordService.deleteByKwIds(Arrays.asList(new String[]{id}));
        writeJson(RES_SUCCESS, response);
    }


    /**
     * 还原修改的关键词
     *
     * @param response
     * @param id
     */
    @RequestMapping(value = "assistantKeyword/reducUpdate", method = {RequestMethod.GET, RequestMethod.POST})
    public void reducUpdate(HttpServletResponse response, String id) {
        KeywordDTO keywordDTO = keywordBackUpService.reducUpdate(id);
        writeJson(keywordDTO, response);
    }

    /**
     * 还原功能软删除
     *
     * @param response
     * @param id
     */
    @RequestMapping(value = "assistantKeyword/reducDel", method = {RequestMethod.GET, RequestMethod.POST})
    public void reducDel(HttpServletResponse response, String id) {
        keywordBackUpService.reducDel(id);
        writeJson(RES_SUCCESS, response);
    }


    /**
     * 根据账户id得到计划
     */
    @RequestMapping(value = "assistantKeyword/getCampaignByAccountId", method = {RequestMethod.GET, RequestMethod.POST})
    public void getCampaignByAccountId(HttpServletResponse response) {
        Iterable<CampaignDTO> list = assistantKeywordService.getCampaignByAccountId();
        writeJson(list, response);
    }

    /**
     * 根据计划cid得到单元
     */
    @RequestMapping(value = "assistantKeyword/getAdgroupByCid", method = {RequestMethod.GET, RequestMethod.POST})
    public void getAdgroupByCid(HttpServletResponse response, String cid) {
        Iterable<AdgroupDTO> list = assistantKeywordService.getAdgroupByCid(cid);
        writeJson(list, response);
    }


    /**
     * 从百度上获取搜索词报告
     */
    @RequestMapping(value = "assistantKeyword/getSearchWordReport", method = {RequestMethod.GET, RequestMethod.POST})
    public void getSearchWordReport(HttpServletResponse response,
                                    Integer levelOfDetails,
                                    String startDate,
                                    String endDate,
                                    String attributes,
                                    Integer device,
                                    Integer searchType,
                                    @RequestParam(defaultValue = "1") Integer status,
                                    @RequestParam(defaultValue = "0") String downStatus) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cal.getTime());

        List<AttributeType> list = null;
        if (startDate == null || startDate.equals("") || (endDate == null)) {
            startDate = yesterday;
            endDate = yesterday;
        }
        if (attributes != null && !Objects.equals(attributes, "")) {
            list = new ArrayList<>();
            String[] attrs = attributes.split(",");
            Map<Integer, String> regions = sysRegionalService.getRegionalByRegionName(Arrays.asList(attrs));
            AttributeType attributeType = new AttributeType();
            attributeType.setKey("provid");
            attributeType.setValue(new ArrayList<Integer>(regions.keySet()));
            list.add(attributeType);
        }

        List<RealTimeQueryResultType> resultList = null;
        try {
            BaiduAccountInfoDTO accountInfoDTO = baiduAccountService.getBaiduAccountInfoBySystemUserNameAndAcId(AppContext.getUser(), AppContext.getAccountId());
            resultList = getSearchTermsReprot(accountInfoDTO, levelOfDetails, df.parse(startDate), df.parse(endDate), list, device, searchType);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<SearchwordReportDTO> dtoList = new ArrayList<>();
        DecimalFormat dft = new DecimalFormat("0.00");
        if (resultList != null) {
            for (RealTimeQueryResultType resultType : resultList) {
                SearchwordReportDTO searchwordReportDTO = new SearchwordReportDTO();
                searchwordReportDTO.setKeyword(resultType.getQueryInfo(3));
                searchwordReportDTO.setSearchWord(resultType.getQuery());
                searchwordReportDTO.setClick(resultType.getKPI(0));
                searchwordReportDTO.setImpression(resultType.getKPI(1));
                double rate = Double.parseDouble(resultType.getKPI(0)) / Double.parseDouble(resultType.getKPI(1));
                searchwordReportDTO.setClickRate(dft.format(BigDecimal.valueOf(rate * 100)) + "%");
                searchwordReportDTO.setSearchEngine(resultType.getQueryInfo(8));
                searchwordReportDTO.setAdgroupName(resultType.getQueryInfo(2));
                searchwordReportDTO.setCampaignName(resultType.getQueryInfo(1));
                searchwordReportDTO.setCreateTitle(resultType.getQueryInfo(4));
                searchwordReportDTO.setCreateDesc1(resultType.getQueryInfo(5));
                searchwordReportDTO.setCreateDesc2(resultType.getQueryInfo(6));
                searchwordReportDTO.setDate(resultType.getDate());
                searchwordReportDTO.setParseExtent(resultType.getQueryInfo(9));
                dtoList.add(searchwordReportDTO);
            }
        }
        if (!downStatus.equals("0")) {
            try (OutputStream os = response.getOutputStream()) {
                String filename = UUID.randomUUID().toString().replace("-", "") + ".csv";
                response.addHeader("Content-Disposition", "attachment;filename=" + filename);
                basisReportDownService.downSeachKeyWordCSV(os, dtoList);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (status == 0) {
            List<SearchwordReportDTO> returnList = Lists.newArrayList();
            ForkJoinPool joinPoolTow = new ForkJoinPool();
            String[] date = new String[]{startDate, endDate};
            try {
                Future<List<SearchwordReportDTO>> joinTaskTow = joinPoolTow.submit(new AssistantKwdUtil(dtoList, 0, dtoList.size(), date));
                returnList = joinTaskTow.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                joinPoolTow.shutdown();
            }
            writeJson(returnList, response);
        } else {
            writeJson(dtoList, response);
        }
    }

    /**
     * 将搜索词报告中关键词添加到现登录的账户
     */
    @RequestMapping(value = "assistantKeyword/addSearchwordKeyword", method = {RequestMethod.GET, RequestMethod.POST})
    public void addSearchwordKeyword(HttpServletResponse response, String agid, String keywords, String matchType) {
        String[] keywordArray = keywords.split("\n");

        List<KeywordDTO> list = new ArrayList<KeywordDTO>();
        for (String kwd : keywordArray) {
            KeywordDTO keywordEntity = new KeywordDTO();
            keywordEntity.setKeyword(kwd);
            if (agid.matches("^\\d+$")) {
                keywordEntity.setAdgroupId(Long.parseLong(agid));
            } else {
                keywordEntity.setAdgroupObjId(agid);
            }
            String[] match = matchType.split("-");
            if (match.length == 1) {
                keywordEntity.setMatchType(Integer.parseInt(match[0]));
            } else {
                keywordEntity.setMatchType(Integer.parseInt(match[0]));
                keywordEntity.setPhraseType(Integer.parseInt(match[1]));
            }
            keywordEntity.setAccountId(AppContext.getAccountId());
            keywordEntity.setPause(false);
            keywordEntity.setStatus(-1);
            keywordEntity.setLocalStatus(1);
            list.add(keywordEntity);
        }
        assistantKeywordService.saveSearchwordKeyword(list);
        writeJson(RES_SUCCESS, response);
    }

    /**
     * 将搜索词报告中关键词作为否定词设置到现登录的账户
     */
    @RequestMapping(value = "assistantKeyword/setNeigWord", method = {RequestMethod.GET, RequestMethod.POST})
    public void setNeigWord(HttpServletResponse response, String agid, String keywords, Integer neigType) {
        assistantKeywordService.setNeigWord(agid, keywords, neigType);
        writeJson(RES_SUCCESS, response);
    }

    public List<RealTimeQueryResultType> getSearchTermsReprot(BaiduAccountInfoDTO accountInfoDTO, Integer levelOfDetails, Date startDate, Date endDate, List<AttributeType> attributes, Integer device, Integer searchType) {
        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        CommonService commonService = BaiduServiceSupport.getCommonService(accountInfoDTO.getBaiduUserName(), accountInfoDTO.getBaiduPassword(), accountInfoDTO.getToken());
        List<RealTimeQueryResultType> resList = new ArrayList<>();
        try {
            Date baseDate = df.parse("23:59:59");
            Calendar beforeYesterDay = Calendar.getInstance();
            beforeYesterDay.add(Calendar.DAY_OF_YEAR, -2);//前天的日期
            Calendar yesterDay = Calendar.getInstance();
            yesterDay.add(Calendar.DAY_OF_YEAR, -1);//昨天的日期

            //若小于baseDate，则是11:51之前的,否则是11:51之后的,     请求时间在当天中午11:51前，startDate范围可取：[前天，前天-30] 请求时间在当天中午11:51后，startDate范围可取：[昨天，昨天-30]
            if (df.parse(df.format(startDate)).getTime() < baseDate.getTime()) {
                //若开始日期大于了前天，就将开始日期置为前天
                if (startDate.getTime() > beforeYesterDay.getTime().getTime()) {
                    startDate = beforeYesterDay.getTime();
                }
            } else {
                if (startDate.getTime() > yesterDay.getTime().getTime()) {
                    startDate = yesterDay.getTime();
                }
            }
            if (df.parse(df.format(endDate)).getTime() < baseDate.getTime()) {
                //若开始日期大于了前天，就将开始日期置为前天
                if (endDate.getTime() > beforeYesterDay.getTime().getTime()) {
                    endDate = beforeYesterDay.getTime();
                }
            } else {
                if (endDate.getTime() > yesterDay.getTime().getTime()) {
                    endDate = yesterDay.getTime();
                }
            }
            ReportService reportService = commonService.getService(ReportService.class);
            //设置请求参数
            RealTimeQueryRequestType realTimeQueryRequestType = new RealTimeQueryRequestType();
            String[] returnFileds = new String[]{"click", "impression"};
            realTimeQueryRequestType.setPerformanceData(Arrays.asList(returnFileds));
            realTimeQueryRequestType.setLevelOfDetails(levelOfDetails);
            realTimeQueryRequestType.setStartDate(startDate);
            realTimeQueryRequestType.setEndDate(endDate);
            realTimeQueryRequestType.setAttributes(attributes);
            realTimeQueryRequestType.setDevice(device);
            realTimeQueryRequestType.setReportType(6);//报告类型
            realTimeQueryRequestType.setNumber(1000);//获取数据的条数

            //创建请求
            GetRealTimeQueryDataRequest getRealTimeQueryDataRequest = new GetRealTimeQueryDataRequest();
            getRealTimeQueryDataRequest.setRealTimeQueryRequestTypes(realTimeQueryRequestType);
            GetRealTimeQueryDataResponse response1 = reportService.getRealTimeQueryData(getRealTimeQueryDataRequest);

            if (response1 == null) {
                return new ArrayList<>();
            } else {
                resList = response1.getRealTimeQueryResultTypes();
            }
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resList;
    }

    @RequestMapping(value = "assistantKeyword/uploadOperate")
    public ModelAndView uploadOperate(@RequestParam(value = "kid") String kid, Integer ls) {
        if (kid.length() > OBJ_SIZE) {
            List<KeywordDTO> keywordDTOs = assistantKeywordService.uploadAdd(new ArrayList<String>() {{
                add(kid);
            }});
            if (keywordDTOs.size() > 0) {
                int error = 0;
                for (KeywordDTO s : keywordDTOs) {
                    if (s.getKeywordId() != 0) {
                        assistantKeywordService.update(kid, s);
                    } else {
                        error++;
                    }
                }
                if (error > 0) {
                    return writeMapObject(MSG, "部分关键词上传失败，不符合规范，请检查关键词是否重复，出价等条件...");
                } else {
                    return writeMapObject(MSG, SUCCESS);
                }
            } else {
                return writeMapObject(MSG, "noUp");
            }
        } else {
            switch (ls) {
                case 2:
                    List<KeywordDTO> keywordDTOList = assistantKeywordService.uploadUpdate(new ArrayList<Long>() {{
                        add(Long.valueOf(kid));
                    }});
                    if (keywordDTOList.size() > 0) {
                        return writeMapObject(MSG, SUCCESS);
                    } else {
                        return writeMapObject(MSG, "部分修改失败！");
                    }
                case 3:
                    Integer result = assistantKeywordService.uploadDel(Long.valueOf(kid));
                    if (result == 1) {
                        return writeMapObject(MSG, SUCCESS);
                    } else {
                        return writeMapObject(MSG, "删除部分失败");
                    }
            }
        }
        return writeMapObject(MSG, "上传失败");
    }

    @RequestMapping(value = "assistantKeyword/uploadAddByUp")
    public ModelAndView uploadAddByUp(@RequestParam(value = "kid", required = true) String kid) {
        List<KeywordDTO> returnKeywordDTO = assistantKeywordService.uploadAddByUp(kid);
        if (returnKeywordDTO.size() > 0) {
            returnKeywordDTO.stream().filter(s -> s.getKeywordId() != null).forEach(s -> assistantKeywordService.update(kid, s));
            return writeMapObject(MSG, SUCCESS);
        }
        return writeMapObject(MSG, "级联上传失败");
    }

    @RequestMapping(value = "assistantKeyword/getNoKeywords")
    public ModelAndView getNoKeywords(@RequestParam(value = "aid", required = true) String aid) {
        if (aid.length() > OBJ_SIZE) {
            Map<String, Map<String, List<String>>> map = assistantKeywordService.getNoKeywords(aid);
            return writeMapObject(DATA, map);
        } else {
            Map<String, Map<String, List<String>>> map = assistantKeywordService.getNoKeywords(Long.parseLong(aid));
            return writeMapObject(DATA, map);
        }
    }

    @RequestMapping(value = "assistantKeyword/addCensus")
    public ModelAndView addCensus() {
        try {
            Iterable<KeywordDTO> list = assistantKeywordService.findAll();
            BaiduAccountInfoDTO accountIdDTO = baiduAccountService.getBaiduAccountInfoBySystemUserNameAndAcId(AppContext.getUser(), AppContext.getAccountId());
            String domain = accountIdDTO.getRegDomain();
            list.forEach(s -> {
                String pcUrl = s.getPcDestinationUrl();
                if (pcUrl == null) {
                    s.setPcDestinationUrl("http://www." + domain + "?ca=" + IdConvertUtils.convert(s.getKeywordId()));
                } else {
                    if (pcUrl.contains("?")) {
                        if (!pcUrl.contains("ca=")) {
                            s.setPcDestinationUrl(pcUrl + "&ca=" + IdConvertUtils.convert(s.getKeywordId()));
                        }
                    } else {
                        s.setPcDestinationUrl(pcUrl + "?ca=" + IdConvertUtils.convert(s.getKeywordId()));
                    }
                }
                assistantKeywordService.updateKeyword(s);
            });
            return writeMapObject(MSG, SUCCESS);
        } catch (Exception e) {
            return writeMapObject(MSG, FAIL);
        }
    }


    /**
     * 下载搜索词报告
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "assistantKeyword/downSeachKeyWordReportCSV", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downSeachKeyWordReportCSV(HttpServletResponse response,
                                          Integer levelOfDetails,
                                          String startDate,
                                          String endDate,
                                          String attributes,
                                          Integer device,
                                          Integer searchType,
                                          @RequestParam(defaultValue = "1") Integer status) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cal.getTime());

        List<AttributeType> list = null;
        if (startDate == null || startDate.equals("") || (endDate == null)) {
            startDate = yesterday;
            endDate = yesterday;
        }
        if (attributes != null && !Objects.equals(attributes, "")) {
            list = new ArrayList<>();
            String[] attrs = attributes.split(",");
            Map<Integer, String> regions = sysRegionalService.getRegionalByRegionName(Arrays.asList(attrs));
            AttributeType attributeType = new AttributeType();
            attributeType.setKey("provid");
            attributeType.setValue(new ArrayList<Integer>(regions.keySet()));
            list.add(attributeType);
        }

        List<RealTimeQueryResultType> resultList = null;
        try {
            BaiduAccountInfoDTO accountInfoDTO = baiduAccountService.getBaiduAccountInfoBySystemUserNameAndAcId(AppContext.getUser(), AppContext.getAccountId());
            resultList = getSearchTermsReprot(accountInfoDTO, levelOfDetails, df.parse(startDate), df.parse(endDate), list, device, searchType);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<SearchwordReportDTO> dtoList = new ArrayList<>();
        DecimalFormat dft = new DecimalFormat("0.00");
        if (resultList != null) {
            for (RealTimeQueryResultType resultType : resultList) {
                SearchwordReportDTO searchwordReportDTO = new SearchwordReportDTO();
                searchwordReportDTO.setKeyword(resultType.getQueryInfo(3));
                searchwordReportDTO.setSearchWord(resultType.getQuery());
                searchwordReportDTO.setClick(resultType.getKPI(0));
                searchwordReportDTO.setImpression(resultType.getKPI(1));
                double rate = Double.parseDouble(resultType.getKPI(0)) / Double.parseDouble(resultType.getKPI(1));
                searchwordReportDTO.setClickRate(dft.format(BigDecimal.valueOf(rate * 100)) + "%");
                searchwordReportDTO.setSearchEngine(resultType.getQueryInfo(8));
                searchwordReportDTO.setAdgroupName(resultType.getQueryInfo(2));
                searchwordReportDTO.setCampaignName(resultType.getQueryInfo(1));
                searchwordReportDTO.setCreateTitle(resultType.getQueryInfo(4));
                searchwordReportDTO.setCreateDesc1(resultType.getQueryInfo(5));
                searchwordReportDTO.setCreateDesc2(resultType.getQueryInfo(6));
                searchwordReportDTO.setDate(resultType.getDate());
                searchwordReportDTO.setParseExtent(resultType.getQueryInfo(9));
                dtoList.add(searchwordReportDTO);
            }
        }

        if (status == 0) {
            List<SearchwordReportDTO> returnList;
            ForkJoinPool joinPoolTow = new ForkJoinPool();
            String[] date = new String[]{startDate, endDate};
            try (OutputStream os = response.getOutputStream()) {
                Future<List<SearchwordReportDTO>> joinTaskTow = joinPoolTow.submit(new AssistantKwdUtil(dtoList, 0, dtoList.size(), date));
                returnList = joinTaskTow.get();
                String filename = UUID.randomUUID().toString().replace("-", "") + ".csv";
                response.addHeader("Content-Disposition", "attachment;filename=" + filename);
                basisReportDownService.downSeachKeyWordReportCSV(os, returnList);
            } catch (InterruptedException | ExecutionException | IOException e) {
                e.printStackTrace();
            } finally {
                joinPoolTow.shutdown();
            }
        } else {
            try (OutputStream os = response.getOutputStream()) {
                String filename = UUID.randomUUID().toString().replace("-", "") + ".csv";
                response.addHeader("Content-Disposition", "attachment;filename=" + filename);
                basisReportDownService.downSeachKeyWordReportCSV(os, dtoList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @RequestMapping(value = "assistantKeyword/filterSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView filterSearchKeyword(@RequestBody SearchFilterParam sp) {
        PagerInfo page = null;
        if (!Objects.equals("-1", sp.getAid())) {
            page = assistantKeywordService.getKeyWords(sp.getCid(), sp.getAid(), 1, 1000, sp);
        }
        return writeMapObject(DATA, page);
    }


    @RequestMapping(value = "assistantKeyword/importByFile", method = RequestMethod.POST)
    public void uploadFile(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file, String jsessionid) throws IOException {
        UploadHelper upload = new UploadHelper();
        String ext = upload.getExt(file.getOriginalFilename());
        if (ext.equals("xls") || ext.equals("xlsx")) {
//            excelSupport(upload, file, response);
        } else if (ext.equals("csv")) {
//            csvSupport(upload, file, response);
        }
    }
}
