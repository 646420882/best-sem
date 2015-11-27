package com.perfect.app.assistant.controller;

import com.perfect.autosdk.sms.v3.KeywordInfo;
import com.perfect.commons.web.WebContextSupport;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.KeywordBackUpDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.AssistantKeywordIgnoreDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.keyword.KeywordInfoDTO;
import com.perfect.param.EditParam;
import com.perfect.param.EnableOrPauseParam;
import com.perfect.param.FindOrReplaceParam;
import com.perfect.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;

/**
 * @author xiaowei
 * @title AssistantCommonsController
 * @package com.perfect.app.assistant.controller
 * @description 业务公共类方法，包括各层级的文字替换，复制粘贴等业务功能
 * @update 2015年09月25日. 上午11:45
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/assistantCommons")
public class AssistantCommonsController extends WebContextSupport {


    @Resource
    private AssistantKeywordService assistantKeywordService;

    @Resource
    private CreativeService creativeService;

    @Resource
    private AdgroupService adgroupService;

    @Resource
    private CampaignService campaignService;

    @Resource
    private KeywordService keywordService;

    private static Integer OBJ_SIZE = 18;

    @RequestMapping(value = "/dataParse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView editData(@RequestBody EditParam ep) {
        int error = 0;
        switch (ep.getType()) {
            case "keyword":
                String[] kIds = ep.getEditData().split(",");
                List<String> keywordIds = Arrays.asList(kIds);
                for (String s : keywordIds) {
                    if (s.length() > OBJ_SIZE) {
                        KeywordInfoDTO dto = assistantKeywordService.findByInfoStrId(s);
                        error += cutOrCopyKeyword(ep, dto);
                    } else {
                        KeywordInfoDTO dto = assistantKeywordService.findByInfoLongId(Long.valueOf(s));
                        error += cutOrCopyKeyword(ep, dto);
                    }
                }
                break;
            case "creative":
                String[] cIds = ep.getEditData().split(",");
                List<String> creativeIds = Arrays.asList(cIds);
                for (String s : creativeIds) {
                    if (s.length() > OBJ_SIZE) {
                        CreativeDTO dto = creativeService.findByObjId(s);
                        error += cutOrCopyCreative(ep, dto);
                    } else {
                        CreativeDTO dto = creativeService.findOne(Long.valueOf(s));
                        error += cutOrCopyCreative(ep, dto);
                    }
                }
                break;
            case "adgroup":
                String[] adIds = ep.getEditData().split(",");
                List<String> adgroupIds = Arrays.asList(adIds);
                for (String s : adgroupIds) {
                    if (s.length() > OBJ_SIZE) {
                        AdgroupDTO dto = adgroupService.findByObjId(s);
                        error += cutOrCopyAdgroup(ep, dto);
                    } else {
                        AdgroupDTO dto = adgroupService.findOne(Long.valueOf(s));
                        error += cutOrCopyAdgroup(ep, dto);
                    }
                }
                break;
            case "campaign":
                String[] caids = ep.getEditData().split(",");
                List<String> campaginIds = Arrays.asList(caids);
                for (String s : campaginIds) {
                    if (s.length() > OBJ_SIZE) {
                        CampaignDTO dto = campaignService.findByObjectId(s);
                        error += cutOrCopyCampaign(ep, dto);
                    } else {
                        CampaignDTO dto = campaignService.findOne(Long.valueOf(s));
                        error += cutOrCopyCampaign(ep, dto);
                    }
                }
                break;
        }
        if (error == 0) {
            return writeMapObject(MSG, SUCCESS);
        }
        return writeMapObject(MSG, FAIL);
    }

    /**
     * @param ep  剪切或者复制的参数对象
     * @param dto 被复制或者粘贴的对象
     * @return
     */
    private Integer cutOrCopyKeyword(final EditParam ep, KeywordInfoDTO dto) {
        if (Objects.equals("copy", ep.getEditType())) {//复制数据
            KeywordDTO keywordDTO = new KeywordDTO();
            BeanUtils.copyProperties(dto.getObject(), keywordDTO);
            if (ep.getAid().length() > OBJ_SIZE) {
                keywordDTO.setAdgroupObjId(ep.getAid());
                keywordDTO.setAdgroupId(null);
            } else {
                keywordDTO.setAdgroupId(Long.valueOf(ep.getAid()));
                keywordDTO.setAdgroupObjId(null);
            }
            keywordDTO.setId(null);
            keywordDTO.setKeywordId(null);
            keywordDTO.setLocalStatus(1);
            try {
                keywordService.save(keywordDTO);
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 1;
            }
        } else {//剪切数据
            try {
                KeywordDTO updateDTO = dto.getObject();
                assistantKeywordService.cut(updateDTO, ep.getAid());
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 1;
            }
        }
    }

    private Integer cutOrCopyCreative(final EditParam ep, CreativeDTO dto) {
        if (Objects.equals("copy", ep.getEditType())) {
            CreativeDTO creativeDTO = new CreativeDTO();
            BeanUtils.copyProperties(dto, creativeDTO);
            if (ep.getAid().length() > OBJ_SIZE) {
                creativeDTO.setAdgroupObjId(ep.getAid());
                creativeDTO.setAdgroupId(null);
            } else {
                creativeDTO.setAdgroupId(Long.valueOf(ep.getAid()));
                creativeDTO.setAdgroupObjId(null);
            }
            creativeDTO.setId(null);
            creativeDTO.setCreativeId(null);
            creativeDTO.setLocalStatus(1);
            try {
                creativeService.insertOutId(creativeDTO);
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 1;
            }
        } else {//剪切
            try {
                creativeService.cut(dto, ep.getAid());
                return 0;
            } catch (Exception e) {
                return 1;
            }
        }
    }

    private Integer cutOrCopyAdgroup(final EditParam ep, AdgroupDTO dto) {
        Integer result = 0;
        AdgroupDTO adgroupDTO = new AdgroupDTO();
        BeanUtils.copyProperties(dto, adgroupDTO);
        if (Objects.equals("copy", ep.getEditType())) {
            if (ep.getCid().length() > OBJ_SIZE) {
                adgroupDTO.setCampaignObjId(ep.getCid());
            } else {
                adgroupDTO.setCampaignId(Long.valueOf(ep.getCid()));
            }
            adgroupDTO.setId("edit");
            adgroupDTO.setAdgroupId(null);
            adgroupDTO.setLocalStatus(1);
            try {
                adgroupService.insertOutId(adgroupDTO);
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 1;
            }
        } else {//单元剪切，剪切单元后要将该单元下的所有关键字跟创意剪切到新的计划下
            try {
                adgroupService.cut(dto, ep.getCid());
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 1;
            }
        }
    }


    private Integer cutOrCopyCampaign(final EditParam ep, CampaignDTO dto) {
        Integer result = 0;
        CampaignDTO campaignDTO = new CampaignDTO();
        BeanUtils.copyProperties(dto, campaignDTO);
        if (Objects.equals("copy", ep.getEditType())) {
            campaignDTO.setId("edit");
            campaignDTO.setCampaignId(null);
            campaignDTO.setLocalStatus(1);
            try {
                campaignService.insertReturnId(campaignDTO);
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 1;
            }
        }
        return result;
    }

    @RequestMapping(value = "/checkSome", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView findOrReplace(@RequestBody FindOrReplaceParam forp) {
        switch (forp.getType()) {
            case "keyword":
                List<KeywordInfoDTO> findDto = keyWordFindOrReplace(forp);
                return writeMapObject(DATA, findDto);
            case "creative":
                List<CreativeDTO> creativeDTOs = creativeWordFindOrReplace(forp);
                return writeMapObject(DATA, creativeDTOs);
            case "adgroup":
                List<AdgroupDTO> adgroupDTOs = adgroudFindOreReplace(forp);
                setCampaignNameByLongId(adgroupDTOs);
                return writeMapObject(DATA, adgroupDTOs);
            case "campaign":
                List<CampaignDTO> campaignDTOs = campaignFindOrReplace(forp);
                return writeMapObject(DATA, campaignDTOs);
        }
        return writeMapObject(DATA, null);
    }

    /**
     * 批量启用/暂停
     * @param param
     * @return
     */
    @RequestMapping(value = "/enableOrPause", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView enableOrPause(@RequestBody EnableOrPauseParam param) {
        if(param != null){
            switch (param.getEnableOrPauseType()){
                case "keyword":
                    assistantKeywordService.enableOrPauseKeyword(param);
                    break;
                case "creative":
                    creativeService.enableOrPauseCreative(param);
                    break;
                case "adgroup":
                    adgroupService.enableOrPauseAdgroup(param);
                    break;
                case "campaign":
                    campaignService.enableOrPauseCampaign(param);
                    break;
            }
        }
        return writeMapObject(DATA, null);
    }

    /**
     * 批量删除
     * @param batchId
     * @return
     */
    @RequestMapping(value = "/batchDel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView batchDel(@RequestBody FindOrReplaceParam batchId) {
        if (batchId != null) {
            switch (batchId.getType()) {
                case "keyword":
                    assistantKeywordService.batchDelete(batchId);
                    return writeMapObject(DATA, null);
                case "creative":
                    creativeService.batchDelete(batchId);
                    return writeMapObject(DATA, null);
                case "adgroup":
                    adgroupService.batchDelete(batchId);
                    return writeMapObject(DATA, null);
                case "campaign":
                    campaignService.batchDelete(batchId);
                    return writeMapObject(DATA, null);
            }
        }
        return writeMapObject(DATA, null);
    }

    /**
     * 文字替换参数对象
     *
     * @param forp
     * @return
     */

    //start keywordTextFindOrReplace
    private List<KeywordInfoDTO> keyWordFindOrReplace(final FindOrReplaceParam forp) {
        List<KeywordInfoDTO> returnResult = new ArrayList<>();
        if (forp.getForType() == 0) {
            String[] keywordIds = forp.getCheckData().split(",");
            List<String> kidStr = Arrays.asList(keywordIds);
            kidStr.stream().forEach(s -> {
                if (s.length() > OBJ_SIZE) {
                    KeywordInfoDTO dto = assistantKeywordService.findByInfoStrId(s);
                    switchCaseKeyword(forp, dto, returnResult);
                } else {
                    KeywordInfoDTO dto = assistantKeywordService.findByInfoLongId(Long.valueOf(s));
                    switchCaseKeyword(forp, dto, returnResult);
                }
            });
        } else if (forp.getForType() == 1) {
            if (forp.getCampaignId() != null) {
                if (forp.getCampaignId().length() > OBJ_SIZE) {
                    List<KeywordInfoDTO> keywordInfoDTOLis = assistantKeywordService.getKeywordInfoByCampaignIdStr(forp.getCampaignId());
                    keywordInfoDTOLis.stream().forEach(s -> {
                        switchCaseKeyword(forp, s, returnResult);
                    });
                } else {
                    List<KeywordInfoDTO> keywordInfoDTOLis = assistantKeywordService.getKeywordInfoByCampaignIdLong(Long.valueOf(forp.getCampaignId()));
                    keywordInfoDTOLis.stream().forEach(s -> {
                        switchCaseKeyword(forp, s, returnResult);
                    });
                }
            }
        } else {
            List<KeywordInfoDTO> keywordInfoDTOs = assistantKeywordService.getAll(forp);
            keywordInfoDTOs.stream().forEach(s -> {
                switchCaseKeyword(forp, s, returnResult);
            });
        }
        return returnResult;
    }

    private void switchCaseKeyword(FindOrReplaceParam forp, KeywordInfoDTO dto, List<KeywordInfoDTO> returnResult) {
        switch (forp.getForPlace()) {
            case "keyword":
                KeywordInfoDTO keyword = getRuleData(forp, 1, dto);
                if (keyword != null)
                    returnResult.add(keyword);
                break;
            case "pcUrl":
                KeywordInfoDTO pcUrl = getRuleData(forp, 2, dto);
                if (pcUrl != null)
                    returnResult.add(pcUrl);
                break;
            case "mibUrl":
                KeywordInfoDTO mibUrl = getRuleData(forp, 3, dto);
                if (mibUrl != null)
                    returnResult.add(mibUrl);
                break;
            case "allUrl":
                KeywordInfoDTO allUrl = getRuleData(forp, 4, dto);
                if (allUrl != null)
                    returnResult.add(allUrl);
                break;
        }
    }

    private KeywordInfoDTO getRuleData(FindOrReplaceParam forp, Integer type, KeywordInfoDTO dto) {
        KeywordInfoDTO keywordDTO = null;
        if (forp.isfQcaseLowerAndUpper() || (!forp.isfQcaseLowerAndUpper() && !forp.isfQcaseAll() && !forp.isfQigonreTirm())) {//isfQcaseLowerAndUpper
            switch (type) {
                case 1:
                    if (dto.getObject().getKeyword().contains(forp.getFindText())) {
                        if (forp.getReplaceText() != null) {
                            dto.getObject().setKeyword(dto.getObject().getKeyword().replace(forp.getFindText(), forp.getReplaceText()));
                            KeywordDTO updateDTO = dto.getObject();
                            assistantKeywordService.updateKeyword(updateDTO);
                            return dto;
                        } else {
                            return dto;
                        }
                    }
                    break;
                case 2:
                    if (dto.getObject().getPcDestinationUrl() != null) {
                        if (dto.getObject().getPcDestinationUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.getObject().setPcDestinationUrl(dto.getObject().getPcDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                KeywordDTO updateDTO = dto.getObject();
                                assistantKeywordService.updateKeyword(updateDTO);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 3:
                    if (dto.getObject().getMobileDestinationUrl() != null) {
                        if (dto.getObject().getMobileDestinationUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.getObject().setMobileDestinationUrl(dto.getObject().getMobileDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                KeywordDTO updateDTO = dto.getObject();
                                assistantKeywordService.updateKeyword(updateDTO);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 4:
                    if (dto.getObject().getMobileDestinationUrl() != null) {
                        if (dto.getObject().getMobileDestinationUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.getObject().setMobileDestinationUrl(dto.getObject().getMobileDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                KeywordDTO updateDTO = dto.getObject();
                                assistantKeywordService.updateKeyword(updateDTO);
                            }
                        }
                    }
                    if (dto.getObject().getPcDestinationUrl() != null) {
                        if (dto.getObject().getPcDestinationUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.getObject().setPcDestinationUrl(dto.getObject().getPcDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                KeywordDTO updateDTOPc = dto.getObject();
                                assistantKeywordService.updateKeyword(updateDTOPc);
                            }
                        }
                    }
                    return dto;
            }
        }
        if (forp.isfQcaseAll()) {//isfQigonreTirm
            switch (type) {
                case 1:
                    if (dto.getObject().getKeyword().equals(forp.getFindText())) {
                        if (forp.getReplaceText() != null) {
                            dto.getObject().setKeyword(dto.getObject().getKeyword().replace(forp.getFindText(), forp.getReplaceText()));
                            KeywordDTO updateDTO = dto.getObject();
                            assistantKeywordService.updateKeyword(updateDTO);
                            return dto;
                        } else {
                            return dto;
                        }
                    }
                    break;
                case 2:
                    if (dto.getObject().getPcDestinationUrl() != null) {
                        if (dto.getObject().getPcDestinationUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.getObject().setPcDestinationUrl(dto.getObject().getPcDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                KeywordDTO updateDTO = dto.getObject();
                                assistantKeywordService.updateKeyword(updateDTO);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 3:
                    if (dto.getObject().getMobileDestinationUrl() != null) {
                        if (dto.getObject().getMobileDestinationUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.getObject().setMobileDestinationUrl(dto.getObject().getMobileDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                KeywordDTO updateDTO = dto.getObject();
                                assistantKeywordService.updateKeyword(updateDTO);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 4:
                    if (dto.getObject().getMobileDestinationUrl() != null) {
                        if (dto.getObject().getMobileDestinationUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.getObject().setMobileDestinationUrl(dto.getObject().getMobileDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                KeywordDTO updateDTO = dto.getObject();
                                assistantKeywordService.updateKeyword(updateDTO);
                            }
                        }
                    }
                    if (dto.getObject().getPcDestinationUrl() != null) {
                        if (dto.getObject().getPcDestinationUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.getObject().setPcDestinationUrl(dto.getObject().getPcDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                KeywordDTO updateDTOPc = dto.getObject();
                                assistantKeywordService.updateKeyword(updateDTOPc);
                            }
                        }
                    }
                    return dto;
            }
        }
        if (forp.isfQigonreTirm()) {//isfQcaseAll
            switch (type) {
                case 1:
                    if (dto.getObject().getKeyword().trim().contains(forp.getFindText())) {
                        if (forp.getReplaceText() != null) {
                            dto.getObject().setKeyword(dto.getObject().getKeyword().replace(forp.getFindText(), forp.getReplaceText()));
                            KeywordDTO updateDTO = dto.getObject();
                            assistantKeywordService.updateKeyword(updateDTO);
                            return dto;
                        } else {
                            return dto;
                        }
                    }
                    break;
                case 2:
                    if (dto.getObject().getPcDestinationUrl() != null) {
                        if (dto.getObject().getPcDestinationUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.getObject().setPcDestinationUrl(dto.getObject().getPcDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                KeywordDTO updateDTO = dto.getObject();
                                assistantKeywordService.updateKeyword(updateDTO);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 3:
                    if (dto.getObject().getMobileDestinationUrl() != null) {
                        if (dto.getObject().getMobileDestinationUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.getObject().setMobileDestinationUrl(dto.getObject().getMobileDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                KeywordDTO updateDTO = dto.getObject();
                                assistantKeywordService.updateKeyword(updateDTO);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 4:
                    if (dto.getObject().getMobileDestinationUrl() != null) {
                        if (dto.getObject().getMobileDestinationUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.getObject().setMobileDestinationUrl(dto.getObject().getMobileDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                KeywordDTO updateDTO = dto.getObject();
                                assistantKeywordService.updateKeyword(updateDTO);
                                return dto;
                            }
                        }
                    }

                    if (dto.getObject().getPcDestinationUrl() != null) {
                        if (dto.getObject().getPcDestinationUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.getObject().setPcDestinationUrl(dto.getObject().getPcDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                KeywordDTO updateDTOPc = dto.getObject();
                                assistantKeywordService.updateKeyword(updateDTOPc);
                            }
                        }
                    }
                    return dto;
            }
        }
        return keywordDTO;
    }
    //end keywordTextFindOrReplace


    //start creativeTextFindOrReplace

    private List<CreativeDTO> creativeWordFindOrReplace(final FindOrReplaceParam forp) {
        List<CreativeDTO> returnResult = new ArrayList<>();
        if (forp.getForType() == 0) {//操作选中
            String[] creativeIds = forp.getCheckData().split(",");
            List<String> cridStr = Arrays.asList(creativeIds);
            cridStr.stream().forEach(crid -> {//如果选中是本地添加的创意
                if (crid.length() > OBJ_SIZE) {
                    CreativeDTO creativeDTO = creativeService.findByObjId(crid);
                    switchCaseCreative(forp, creativeDTO, returnResult);
                } else {
                    CreativeDTO creativeDTO = creativeService.findOne(Long.valueOf(crid));
                    switchCaseCreative(forp, creativeDTO, returnResult);
                }
            });
        } else {//操作整个计划下的创意
            if (forp.getCampaignId().length() > OBJ_SIZE) {
                List<CreativeDTO> creativeDTOs = creativeService.getByCampaignIdStr(forp.getCampaignId());
                creativeDTOs.stream().forEach(s -> {
                    switchCaseCreative(forp, s, returnResult);
                });
            } else {
                List<CreativeDTO> creativeDTOs = creativeService.getByCampaignIdLong(Long.valueOf(forp.getCampaignId()));
                creativeDTOs.stream().forEach(s -> {
                    switchCaseCreative(forp, s, returnResult);
                });
            }
        }
        return returnResult;
    }

    private void switchCaseCreative(FindOrReplaceParam forp, CreativeDTO dto, List<CreativeDTO> returnResult) {
        switch (forp.getForPlace()) {
            case "cTitle":
                CreativeDTO creativeTitle = getRuleData(forp, 1, dto);
                if (creativeTitle != null)
                    returnResult.add(creativeTitle);
                break;
            case "cDesc1":
                CreativeDTO creativeDesc1 = getRuleData(forp, 2, dto);
                if (creativeDesc1 != null)
                    returnResult.add(creativeDesc1);
                break;
            case "cDesc2":
                CreativeDTO creativeDesc2 = getRuleData(forp, 3, dto);
                if (creativeDesc2 != null)
                    returnResult.add(creativeDesc2);
                break;
            case "titleAndDesc":
                CreativeDTO creativeTitleAndDesc = getRuleData(forp, 4, dto);
                if (creativeTitleAndDesc != null)
                    returnResult.add(creativeTitleAndDesc);
                break;
            case "pcUrl":
                CreativeDTO creativePcUrl = getRuleData(forp, 5, dto);
                if (creativePcUrl != null)
                    returnResult.add(creativePcUrl);
                break;
            case "pcsUrl":
                CreativeDTO creativePcsUrl = getRuleData(forp, 6, dto);
                if (creativePcsUrl != null)
                    returnResult.add(creativePcsUrl);
                break;
            case "pcAllUrl":
                CreativeDTO creativePcsAllUrl = getRuleData(forp, 7, dto);
                if (creativePcsAllUrl != null)
                    returnResult.add(creativePcsAllUrl);
                break;
            case "mibUrl":
                CreativeDTO creativeMibUrl = getRuleData(forp, 8, dto);
                if (creativeMibUrl != null)
                    returnResult.add(creativeMibUrl);
                break;
            case "mibsUrl":
                CreativeDTO creativeMibsUrl = getRuleData(forp, 9, dto);
                if (creativeMibsUrl != null)
                    returnResult.add(creativeMibsUrl);
                break;
            case "mibAllUrl":
                CreativeDTO creativemMibAllUrl = getRuleData(forp, 10, dto);
                if (creativemMibAllUrl != null)
                    returnResult.add(creativemMibAllUrl);
                break;
            case "AllUrl":
                CreativeDTO creativeAllUrl = getRuleData(forp, 11, dto);
                if (creativeAllUrl != null)
                    returnResult.add(creativeAllUrl);
                break;
        }

    }

    private CreativeDTO getRuleData(FindOrReplaceParam forp, Integer type, CreativeDTO dto) {
        CreativeDTO creativeDTO = null;
        if (forp.isfQcaseLowerAndUpper() || (!forp.isfQcaseLowerAndUpper() && !forp.isfQcaseAll() && !forp.isfQigonreTirm())) {//isfQcaseLowerAndUpper
            switch (type) {
                case 1:
                    if (dto.getTitle() != null) {
                        if (dto.getTitle().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setTitle(dto.getTitle().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 2:
                    if (dto.getDescription1() != null) {
                        if (dto.getDescription1().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setDescription1(dto.getDescription1().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 3:
                    if (dto.getDescription2() != null) {
                        if (dto.getDescription2().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setDescription2(dto.getDescription2().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 4:
                    if (dto.getTitle() != null) {
                        if (dto.getTitle().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setTitle(dto.getTitle().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getDescription1() != null) {
                        if (dto.getDescription1().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setDescription1(dto.getDescription1().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getDescription2() != null) {
                        if (dto.getDescription2().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setDescription2(dto.getDescription2().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    return dto;
                case 5:
                    if (dto.getPcDestinationUrl() != null) {
                        if (dto.getPcDestinationUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDestinationUrl(dto.getPcDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 6:
                    if (dto.getPcDisplayUrl() != null) {
                        if (dto.getPcDisplayUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDisplayUrl(dto.getPcDisplayUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 7:
                    if (dto.getPcDestinationUrl() != null) {
                        if (dto.getPcDestinationUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDestinationUrl(dto.getPcDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getPcDisplayUrl() != null) {
                        if (dto.getPcDisplayUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDisplayUrl(dto.getPcDisplayUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    return dto;
                case 8:
                    if (dto.getMobileDestinationUrl() != null) {
                        if (dto.getMobileDestinationUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDestinationUrl(dto.getMobileDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 9:
                    if (dto.getMobileDisplayUrl() != null) {
                        if (dto.getMobileDisplayUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDisplayUrl(dto.getMobileDisplayUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 10:
                    if (dto.getMobileDestinationUrl() != null) {
                        if (dto.getMobileDestinationUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDestinationUrl(dto.getMobileDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getMobileDisplayUrl() != null) {
                        if (dto.getMobileDisplayUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDisplayUrl(dto.getMobileDisplayUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    return dto;
                case 11:
                    if (dto.getPcDestinationUrl() != null) {
                        if (dto.getPcDestinationUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDestinationUrl(dto.getPcDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getPcDisplayUrl() != null) {
                        if (dto.getPcDisplayUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDisplayUrl(dto.getPcDisplayUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getMobileDestinationUrl() != null) {
                        if (dto.getMobileDestinationUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDestinationUrl(dto.getMobileDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getMobileDisplayUrl() != null) {
                        if (dto.getMobileDisplayUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDisplayUrl(dto.getMobileDisplayUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    return dto;
            }
        }

        if (forp.isfQcaseAll()) {
            switch (type) {
                case 1:
                    if (dto.getTitle() != null) {
                        if (dto.getTitle().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setTitle(dto.getTitle().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 2:
                    if (dto.getDescription1() != null) {
                        if (dto.getDescription1().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setDescription1(dto.getDescription1().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 3:
                    if (dto.getDescription2() != null) {
                        if (dto.getDescription2().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setDescription2(dto.getDescription2().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 4:
                    if (dto.getTitle() != null) {
                        if (dto.getTitle().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setTitle(dto.getTitle().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getDescription1() != null) {
                        if (dto.getDescription1().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setDescription1(dto.getDescription1().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getDescription2() != null) {
                        if (dto.getDescription2().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setDescription2(dto.getDescription2().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    return dto;
                case 5:
                    if (dto.getPcDestinationUrl() != null) {
                        if (dto.getPcDestinationUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDestinationUrl(dto.getPcDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 6:
                    if (dto.getPcDisplayUrl() != null) {
                        if (dto.getPcDisplayUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDisplayUrl(dto.getPcDisplayUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 7:
                    if (dto.getPcDestinationUrl() != null) {
                        if (dto.getPcDestinationUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDestinationUrl(dto.getPcDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getPcDisplayUrl() != null) {
                        if (dto.getPcDisplayUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDisplayUrl(dto.getPcDisplayUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    return dto;
                case 8:
                    if (dto.getMobileDestinationUrl() != null) {
                        if (dto.getMobileDestinationUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDestinationUrl(dto.getMobileDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 9:
                    if (dto.getMobileDisplayUrl() != null) {
                        if (dto.getMobileDisplayUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDisplayUrl(dto.getMobileDisplayUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 10:
                    if (dto.getMobileDestinationUrl() != null) {
                        if (dto.getMobileDestinationUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDestinationUrl(dto.getMobileDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getMobileDisplayUrl() != null) {
                        if (dto.getMobileDisplayUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDisplayUrl(dto.getMobileDisplayUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    return dto;
                case 11:
                    if (dto.getPcDestinationUrl() != null) {
                        if (dto.getPcDestinationUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDestinationUrl(dto.getPcDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getPcDisplayUrl() != null) {
                        if (dto.getPcDisplayUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDisplayUrl(dto.getPcDisplayUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getMobileDestinationUrl() != null) {
                        if (dto.getMobileDestinationUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDestinationUrl(dto.getMobileDestinationUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getMobileDisplayUrl() != null) {
                        if (dto.getMobileDisplayUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDisplayUrl(dto.getMobileDisplayUrl().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    return dto;
            }
        }

        if (forp.isfQigonreTirm()) {
            switch (type) {
                case 1:
                    if (dto.getTitle() != null) {
                        if (dto.getTitle().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setTitle(dto.getTitle().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 2:
                    if (dto.getDescription1() != null) {
                        if (dto.getDescription1().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setDescription1(dto.getDescription1().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 3:
                    if (dto.getDescription2() != null) {
                        if (dto.getDescription2().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setDescription2(dto.getDescription2().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 4:
                    if (dto.getTitle() != null) {
                        if (dto.getTitle().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setTitle(dto.getTitle().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getDescription1() != null) {
                        if (dto.getDescription1().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setDescription1(dto.getDescription1().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getDescription2() != null) {
                        if (dto.getDescription2().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setDescription2(dto.getDescription2().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    return dto;
                case 5:
                    if (dto.getPcDestinationUrl() != null) {
                        if (dto.getPcDestinationUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDestinationUrl(dto.getPcDestinationUrl().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 6:
                    if (dto.getPcDisplayUrl() != null) {
                        if (dto.getPcDisplayUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDisplayUrl(dto.getPcDisplayUrl().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 7:
                    if (dto.getPcDestinationUrl() != null) {
                        if (dto.getPcDestinationUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDestinationUrl(dto.getPcDestinationUrl().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getPcDisplayUrl() != null) {
                        if (dto.getPcDisplayUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDisplayUrl(dto.getPcDisplayUrl().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    break;
                case 8:
                    if (dto.getMobileDestinationUrl() != null) {
                        if (dto.getMobileDestinationUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDestinationUrl(dto.getMobileDestinationUrl().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 9:
                    if (dto.getMobileDisplayUrl() != null) {
                        if (dto.getMobileDisplayUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDisplayUrl(dto.getMobileDisplayUrl().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 10:
                    if (dto.getMobileDestinationUrl() != null) {
                        if (dto.getMobileDestinationUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDestinationUrl(dto.getMobileDestinationUrl().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getMobileDisplayUrl() != null) {
                        if (dto.getMobileDisplayUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDisplayUrl(dto.getMobileDisplayUrl().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    return dto;
                case 11:
                    if (dto.getPcDestinationUrl() != null) {
                        if (dto.getPcDestinationUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDestinationUrl(dto.getPcDestinationUrl().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getPcDisplayUrl() != null) {
                        if (dto.getPcDisplayUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setPcDisplayUrl(dto.getPcDisplayUrl().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getMobileDestinationUrl() != null) {
                        if (dto.getMobileDestinationUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDestinationUrl(dto.getMobileDestinationUrl().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    if (dto.getMobileDisplayUrl() != null) {
                        if (dto.getMobileDisplayUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setMobileDisplayUrl(dto.getMobileDisplayUrl().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                creativeService.updateCreative(dto);
                            }
                        }
                    }
                    return dto;
            }
        }

        return creativeDTO;
    }

    //end creativeTextFindOrReplace


    //start adgroupTextFindOrReplace

    private List<AdgroupDTO> adgroudFindOreReplace(final FindOrReplaceParam forp) {
        List<AdgroupDTO> returnResult = new ArrayList<>();
        if (forp.getForType() == 0) {
            String[] ids = forp.getCheckData().split(",");
            List<String> strIds = Arrays.asList(ids);
            strIds.stream().forEach(s -> {
                if (s.length() > OBJ_SIZE) {
                    AdgroupDTO adgroupDTO = adgroupService.findByObjId(s);
                    switchCaseAdgroup(forp, adgroupDTO, returnResult);
                } else {
                    AdgroupDTO adgroupDTO = adgroupService.findOne(Long.valueOf(s));
                    switchCaseAdgroup(forp, adgroupDTO, returnResult);
                }
            });
        } else {
            if (forp.getCampaignId().length() > OBJ_SIZE) {
                List<AdgroupDTO> adgroupDTOs = adgroupService.getAdgroupByCampaignObjId(forp.getCampaignId());
                adgroupDTOs.stream().forEach(s -> {
                    switchCaseAdgroup(forp, s, returnResult);
                });
            } else {
                List<AdgroupDTO> adgroupDTOs = adgroupService.getAdgroupByCampaignId(Long.valueOf(forp.getCampaignId()));
                adgroupDTOs.stream().forEach(s -> {
                    switchCaseAdgroup(forp, s, returnResult);
                });
            }
        }

        return returnResult;
    }

    private void switchCaseAdgroup(FindOrReplaceParam forp, AdgroupDTO dto, List<AdgroupDTO> returnResult) {
        if (forp.getForPlace().equals("adgroupName")) {
            AdgroupDTO creativeTitle = getRuleData(forp, 1, dto);
            if (creativeTitle != null)
                returnResult.add(creativeTitle);
        }
    }

    private AdgroupDTO getRuleData(FindOrReplaceParam forp, Integer type, AdgroupDTO dto) {
        AdgroupDTO adgroupDTO = null;
        if (forp.isfQcaseLowerAndUpper() || (!forp.isfQcaseLowerAndUpper() && !forp.isfQcaseAll() && !forp.isfQigonreTirm())) {//isfQcaseLowerAndUpper
            switch (type) {
                case 1:
                    if (dto.getAdgroupName() != null) {
                        if (dto.getAdgroupName().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setAdgroupName(dto.getAdgroupName().replace(forp.getFindText(), forp.getReplaceText()));
                                adgroupService.updateAdgroup(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
            }
        }

        if (forp.isfQcaseAll()) {
            switch (type) {
                case 1:
                    if (dto.getAdgroupName() != null) {
                        if (dto.getAdgroupName().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setAdgroupName(dto.getAdgroupName().replace(forp.getFindText(), forp.getReplaceText()));
                                adgroupService.updateAdgroup(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
            }
        }

        if (forp.isfQigonreTirm()) {
            switch (type) {
                case 1:
                    if (dto.getAdgroupName() != null) {
                        if (dto.getAdgroupName().trim().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setAdgroupName(dto.getAdgroupName().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                adgroupService.updateAdgroup(dto);
                                return dto;
                            }
                        }
                    }
                    break;
            }
        }

        return adgroupDTO;
    }

    //end adgroupTextFindOrReplace


    //start campaignTextFindOrReplace
    private List<CampaignDTO> campaignFindOrReplace(final FindOrReplaceParam forp) {
        List<CampaignDTO> returnResult = new ArrayList<>();
        if (forp.getForType() == 0) {//选中
            String[] campaignIds = forp.getCheckData().split(",");
            List<String> cids = Arrays.asList(campaignIds);
            cids.stream().forEach(s -> {
                if (s.length() > OBJ_SIZE) {
                    CampaignDTO campaignDTO = campaignService.findByObjectId(s);
                    switchCaseCampaign(forp, campaignDTO, returnResult);
                } else {
                    CampaignDTO campaignDTO = campaignService.findOne(Long.valueOf(s));
                    switchCaseCampaign(forp, campaignDTO, returnResult);
                }
            });
        } else {//全部
            List<CampaignDTO> campaignDTOs = (List<CampaignDTO>) campaignService.findAll();
            campaignDTOs.stream().filter(f -> f.getCampaignId() != null).forEach(s -> {//Long
                switchCaseCampaign(forp, s, returnResult);
            });
            campaignDTOs.stream().filter(f -> f.getCampaignId() == null).forEach(s -> {
                switchCaseCampaign(forp, s, returnResult);
            });
        }
        return returnResult;
    }

    private void switchCaseCampaign(FindOrReplaceParam forp, CampaignDTO dto, List<CampaignDTO> returnResult) {
        if (Objects.equals("campaignName", forp.getForPlace())) {
            CampaignDTO campaignDTO = getRuleData(forp, 1, dto);
            if (campaignDTO != null) {
                returnResult.add(campaignDTO);
            }
        }
    }

    private CampaignDTO getRuleData(FindOrReplaceParam forp, Integer type, CampaignDTO dto) {
        CampaignDTO campaignDTO = null;

        if (forp.isfQcaseLowerAndUpper() || (!forp.isfQcaseLowerAndUpper() && !forp.isfQcaseAll() && !forp.isfQigonreTirm())) {
            switch (type) {
                case 1:
                    if (dto.getCampaignName() != null) {
                        if (dto.getCampaignName().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setCampaignName(dto.getCampaignName().replace(forp.getFindText(), forp.getReplaceText()));
                                campaignService.updateCampaign(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
            }
        }
        if (forp.isfQcaseAll()) {
            switch (type) {
                case 1:
                    if (dto.getCampaignName() != null) {
                        if (dto.getCampaignName().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setCampaignName(dto.getCampaignName().replace(forp.getFindText(), forp.getReplaceText()));
                                campaignService.updateCampaign(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
            }
        }
        if (forp.isfQigonreTirm()) {
            switch (type) {
                case 1:
                    if (dto.getCampaignName() != null) {
                        if (dto.getCampaignName().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
                                dto.setCampaignName(dto.getCampaignName().trim().replace(forp.getFindText(), forp.getReplaceText()));
                                campaignService.updateCampaign(dto);
                                return dto;
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
            }
        }

        return campaignDTO;
    }
    //end campaignTextFindOrReplace


    private void setCampaignNameByLongId(List<AdgroupDTO> list) {
        if (list.size() > 0) {
            List<CampaignDTO> campaignEntity = (List<CampaignDTO>) campaignService.findAll();
            for (int i = 0; i < campaignEntity.size(); i++) {
                for (AdgroupDTO a : list) {
                    if (a.getCampaignId() != null) {
                        if (a.getCampaignId().equals(campaignEntity.get(i).getCampaignId())) {
                            a.setCampaignName(campaignEntity.get(i).getCampaignName());
                        }
                    } else {
                        if (a.getCampaignObjId().equals(campaignEntity.get(i).getId())) {
                            a.setCampaignName(campaignEntity.get(i).getCampaignName());
                        }
                    }

                }
            }
        }
    }
}
