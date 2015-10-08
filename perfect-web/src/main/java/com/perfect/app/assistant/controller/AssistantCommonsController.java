package com.perfect.app.assistant.controller;

import com.perfect.autosdk.sms.v3.KeywordInfo;
import com.perfect.commons.web.WebContextSupport;
import com.perfect.dto.keyword.AssistantKeywordIgnoreDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.keyword.KeywordInfoDTO;
import com.perfect.param.FindOrReplaceParam;
import com.perfect.service.AssistantKeywordService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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


    private static Integer OBJ_SIZE = 18;

    @RequestMapping(value = "/checkSome", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView findOrReplace(@RequestBody FindOrReplaceParam forp) {
        List<KeywordInfoDTO> findDto = null;
        switch (forp.getType()) {
            case "keyword":
                findDto = keyWordFindOrReplace(forp);
                break;
            case "creative":
                System.out.println("creative");
                break;
            case "adgroup":
                System.out.println("adgroup");
                break;
            case "campaign":
                System.out.println("campaign");
                break;
        }
            return writeMapObject(DATA, findDto);
    }

    /**
     * 文字替换参数对象
     *
     * @param forp
     * @return
     */
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
        } else {
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
        if (forp.isfQcaseLowerAndUpper()) {//isfQcaseLowerAndUpper
            switch (type) {
                case 1:
                    if (dto.getObject().getKeyword().contains(forp.getFindText())) {
                        if (forp.getReplaceText() != null) {
                            dto.getObject().setKeyword(dto.getObject().getKeyword().replaceAll(forp.getFindText(), forp.getReplaceText()));
                            KeywordDTO updateDTO = dto.getObject();
                            assistantKeywordService.updateKeyword(updateDTO);
//                            System.out.println("将关键词为：" + dto.getKeyword() + "的文字替换为：" + forp.getReplaceText() + "匹配大小写，位置位于关键字!!!!!!!!");
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
//                                System.out.println("将默认访问Url为：" + dto.getObject().getPcDestinationUrl() + "的文字替换为：" + forp.getReplaceText() + "匹配大小写，位置位于默认访问Url");
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
//                                System.out.println("将移动访问Url为：" + dto.getObject().getMobileDestinationUrl() + "的文字替换为：" + forp.getReplaceText() + "匹配大小写，位置位于移动访问Url");
                            } else {
                                return dto;
                            }
                        }
                    } else {
                        return null;
                    }
                    break;
                case 4:
                    int findOrReplaceCount = 0;
                    if (dto.getObject().getMobileDestinationUrl() != null) {
                        if (dto.getObject().getMobileDestinationUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
//                                System.out.println("将移动访问Url为：" + dto.getObject().getMobileDestinationUrl() + "的文字替换为：" + forp.getReplaceText());
                            } else {
                                findOrReplaceCount++;
                            }
                        }
                    }
                    if (dto.getObject().getPcDestinationUrl() != null) {
                        if (dto.getObject().getPcDestinationUrl().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
//                                System.out.println("将默认访问Url为：" + dto.getObject().getPcDestinationUrl() + "的文字替换为：" + forp.getReplaceText());
                            } else {
                                findOrReplaceCount++;
                            }
                        }
                    }
                    if (findOrReplaceCount > 0) {
                        return dto;
                    }
                    break;
            }
        }
        if (forp.isfQcaseAll()) {//isfQigonreTirm
            switch (type) {
                case 1:
                    if (dto.getObject().getKeyword().equals(forp.getFindText())) {
                        if (forp.getReplaceText() != null) {
//                            System.out.println("将关键词为：" + dto.getKeyword() + "的文字替换为：" + forp.getReplaceText() + "匹配整个字词，位置位于关键字");
                        } else {
                            return dto;
                        }
                    }
                    break;
                case 2:
                    if (dto.getObject().getPcDestinationUrl() != null) {
                        if (dto.getObject().getPcDestinationUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
//                                System.out.println("将默认访问Url为：" + dto.getObject().getPcDestinationUrl() + "的文字替换为：" + forp.getReplaceText() + "匹配整个字词，位置位于默认访问Url");
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
//                                System.out.println("将移动访问Url为：" + dto.getObject().getMobileDestinationUrl() + "的文字替换为：" + forp.getReplaceText() + "匹配整个字词，位置位于移动访问Url");
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 4:
                    int findOrReplaceCount = 0;
                    if (dto.getObject().getMobileDestinationUrl() != null) {
                        if (dto.getObject().getMobileDestinationUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
//                                System.out.println("将移动访问Url为：" + dto.getObject().getMobileDestinationUrl() + "的文字替换为：" + forp.getReplaceText());
                            } else {
                                findOrReplaceCount++;
                            }
                        }
                    }

                    if (dto.getObject().getPcDestinationUrl() != null) {
                        if (dto.getObject().getPcDestinationUrl().equals(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
//                                System.out.println("将默认访问Url为：" + dto.getObject().getPcDestinationUrl() + "的文字替换为：" + forp.getReplaceText());
                            } else {
                                findOrReplaceCount++;
                            }
                        }
                    }
                    if (findOrReplaceCount > 0) {
                        return dto;
                    }
                    break;
            }
        }
        if (forp.isfQigonreTirm()) {//isfQcaseAll
            switch (type) {
                case 1:
                    if (dto.getObject().getKeyword().trim().contains(forp.getFindText())) {
                        if (forp.getReplaceText() != null) {
//                            System.out.println("将关键词为：" + dto.getKeyword() + "的文字替换为：" + forp.getReplaceText() + "忽略文字两端空格，位置位于关键字");
                        } else {
                            return dto;
                        }
                    }
                    break;
                case 2:
                    if (dto.getObject().getPcDestinationUrl() != null) {
                        if (dto.getObject().getPcDestinationUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
//                                System.out.println("将访问Url为：" + dto.getKeyword() + "的文字替换为：" + forp.getReplaceText() + "忽略文字两端空格，位置位默认访问Url");
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
//                                System.out.println("将移动访问Url为：" + dto.getKeyword() + "的文字替换为：" + forp.getReplaceText() + "忽略文字两端空格，位置位移动访问Url");
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 4:
                    int findOrReplaceCount = 0;

                    if (dto.getObject().getMobileDestinationUrl() != null) {
                        if (dto.getObject().getMobileDestinationUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
//                                System.out.println("将移动访问Url为：" + dto.getKeyword() + "的文字替换为：" + forp.getReplaceText() + "忽略文字两端空格，位置位移动访问Url");
                            } else {
                                findOrReplaceCount++;
                            }
                        }
                    }

                    if (dto.getObject().getPcDestinationUrl() != null) {
                        if (dto.getObject().getPcDestinationUrl().trim().contains(forp.getFindText())) {
                            if (forp.getReplaceText() != null) {
//                                System.out.println("将访问Url为：" + dto.getKeyword() + "的文字替换为：" + forp.getReplaceText() + "忽略文字两端空格，位置位默认访问Url");
                            } else {
                                findOrReplaceCount++;
                            }
                        }
                    }
                    if (findOrReplaceCount > 0) {
                        return dto;
                    }
                    break;
            }
        }

        if (!forp.isfQcaseLowerAndUpper() && !forp.isfQcaseAll() && !forp.isfQigonreTirm()) {
            switch (type) {
                case 1:
                    if (dto.getObject().getKeyword().contains(forp.getFindText())) {
                        if (forp.getReplaceText() != null) {
                            dto.getObject().setKeyword(dto.getObject().getKeyword().replaceAll(forp.getFindText(), forp.getReplaceText()));
                            KeywordDTO updateDTO = dto.getObject();
                            assistantKeywordService.updateKeyword(updateDTO);
//                            System.out.println("将关键词为：" + dto.getKeyword() + "的文字替换为：" + forp.getReplaceText() + "匹配大小写，位置位于关键字!!!!!!!!");
                            return dto;
                        } else {
                            return dto;
                        }
                    }
                    break;
                case 2:
                    if (dto.getObject().getPcDestinationUrl() != null) {
                        if (dto.getObject().getPcDestinationUrl().toLowerCase().contains(forp.getFindText().toLowerCase())) {
                            if (forp.getReplaceText() != null) {
//                                System.out.println("将默认访问Url为：" + dto.getObject().getPcDestinationUrl() + "的文字替换为：" + forp.getReplaceText() + "匹配大小写，位置位于默认访问Url");
                            } else {
                                return dto;
                            }
                        }
                    }
                    break;
                case 3:
                    if (dto.getObject().getMobileDestinationUrl() != null) {
                        if (dto.getObject().getMobileDestinationUrl().toLowerCase().contains(forp.getFindText().toLowerCase())) {
                            if (forp.getReplaceText() != null) {
//                                System.out.println("将移动访问Url为：" + dto.getObject().getMobileDestinationUrl() + "的文字替换为：" + forp.getReplaceText() + "匹配大小写，位置位于移动访问Url");
                            } else {
                                return dto;
                            }
                        }
                    } else {
                        return null;
                    }
                    break;
                case 4:
                    int findOrReplaceCount = 0;
                    if (dto.getObject().getMobileDestinationUrl() != null) {
                        if (dto.getObject().getMobileDestinationUrl().toLowerCase().contains(forp.getFindText().toLowerCase())) {
                            if (forp.getReplaceText() != null) {
//                                System.out.println("将移动访问Url为：" + dto.getObject().getMobileDestinationUrl() + "的文字替换为：" + forp.getReplaceText());
                            } else {
                                findOrReplaceCount++;
                            }
                        }
                    }
                    if (dto.getObject().getPcDestinationUrl() != null) {
                        if (dto.getObject().getPcDestinationUrl().toLowerCase().contains(forp.getFindText().toLowerCase())) {
                            if (forp.getReplaceText() != null) {
//                                System.out.println("将默认访问Url为：" + dto.getObject().getPcDestinationUrl() + "的文字替换为：" + forp.getReplaceText());
                            } else {
                                findOrReplaceCount++;
                            }
                        }
                    }
                    if (findOrReplaceCount > 0) {
                        return dto;
                    }
                    break;
            }
        }
        return keywordDTO;
    }
}
