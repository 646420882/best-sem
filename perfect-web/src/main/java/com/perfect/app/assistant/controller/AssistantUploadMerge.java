package com.perfect.app.assistant.controller;

import com.perfect.web.suport.WebContextSupport;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.service.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiaoWei on 2015/1/19.
 */
@Controller
@RequestMapping(value = "/uploadMerge")
public class AssistantUploadMerge extends WebContextSupport {

    public static final int OBJ_SIZE = 18;

    @Resource
    private AdgroupService adgroupService;

    @Resource
    private AssistantKeywordService assistantKeywordService;

    @Resource
    private CreativeService creativeService;

    @Resource
    private CampaignService campaignService;

    @Resource
    private CampaignBackUpService campaignBackUpService;


    @RequestMapping(value = "/upload")
    public ModelAndView uploadMerge() {

        return writeMapObject(MSG, SUCCESS);
    }

    @RequestMapping(value = "/getCampList")
    public ModelAndView getUploadOperate() {
        List<CampaignDTO> campaignDTOs = campaignService.getOperateCamp();
        return writeMapObject(DATA, campaignDTOs);
    }

    /**
     * 上传全部数据
     *
     * @return
     */
    @RequestMapping(value = "/uploadByAll")
    public ModelAndView uploadByAll() {
        try {
            //1,计划
            uploadCampaign();
            //2,单元
            uploadAdgroup();
            //3,关键字
            uploadKeyword();
            //4,创意
            uploadCreative();
            return writeMapObject(MSG, SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return writeMapObject(MSG, "批量上传部分失败");
        }
    }

    @RequestMapping(value = "/uploadBySomeCamp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView uploadBySomeCamp(@RequestParam(value = "cids") String cids) {
        List<String> cidStr = new ArrayList<>();
        List<Long> cidLong = new ArrayList<>();
        if (cids.indexOf(",") > -1) {
            for (String s : cids.split(",")) {
                if (s.length() > OBJ_SIZE) {
                    cidStr.add(s);
                } else {
                    cidLong.add(Long.valueOf(s));
                }
            }
        } else {
            if (cids.length() > OBJ_SIZE) {
                cidStr.add(cids);
            } else {
                cidLong.add(Long.valueOf(cids));
            }
        }
        try {
            //1,执行上传创意
            List<CampaignDTO> campaignDTOStr = campaignService.findHasLocalStatusByStrings(cidStr);
            List<CampaignDTO> campaignDTOLong = campaignService.findHasLocalStatusByLongs(cidLong);
            onlyUploadCampaign(campaignDTOStr);
            onlyUploadCampaign(campaignDTOLong);

            //2.执行上传单元
            List<AdgroupDTO> adgroupDTOStr = adgroupService.findHasLocalStatusStr(campaignDTOStr);
            List<AdgroupDTO> adgroupDTOLong = adgroupService.findHasLocalStatusLong(campaignDTOLong);
            onlyUploadAdgroup(adgroupDTOStr);
            onlyUploadAdgroup(adgroupDTOLong);

            //3,执行上传关键字
            List<KeywordDTO> keywordDTOStr = assistantKeywordService.findHasLocalStatusStr(adgroupDTOStr);
            List<KeywordDTO> keywordDTOLong = assistantKeywordService.findHasLocalStatusLong(adgroupDTOLong);
            onlyUploadKeyword(keywordDTOStr);
            onlyUploadKeyword(keywordDTOLong);

            //4,执行上传创意
            List<CreativeDTO> creativeDTOStr = creativeService.findHasLocalStatusStr(adgroupDTOStr);
            List<CreativeDTO> creativeDTOLong = creativeService.findHasLocalStatusLong(adgroupDTOLong);
            onlyUploadCreative(creativeDTOStr);
            onlyUploadCreative(creativeDTOLong);
            return writeMapObject(MSG, SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return writeMapObject(MSG, "批量上传部分失败");
        }
    }

    private void uploadCampaign() {
        //首先，查询出计划中所有要本地有操作的计划
        List<CampaignDTO> campaignDTOsFind = campaignService.findHasLocalStatus();
        //查询出所有的计划后，遍历所有计划
        onlyUploadCampaign(campaignDTOsFind);
    }

    private void uploadAdgroup() {
        List<AdgroupDTO> adgroupDTOsFind = adgroupService.findHasLocalStatus();
        //仍然遍历其中所有单元,并过滤其中ls有改动的
        onlyUploadAdgroup(adgroupDTOsFind);
    }

    private void uploadKeyword() {
        List<KeywordDTO> keywordDTOsFind = assistantKeywordService.findHasLocalStatus();
//        遍历其中所有关键字,并过滤其中ls有改动的
        onlyUploadKeyword(keywordDTOsFind);
    }

    private void uploadCreative() {
        //创意全表查询很不合理，所以只查询ls有改动的
        List<CreativeDTO> creativeDTOsFind = creativeService.findHasLocalStatus();
        onlyUploadCreative(creativeDTOsFind);
    }

    private void onlyUploadCampaign(List<CampaignDTO> campaignDTOsFind) {
        campaignDTOsFind.stream().filter(s -> s.getLocalStatus() != null).forEach(s -> {//遍历
            switch (s.getLocalStatus()) {
                case 1://执行添加操作
                    if (s.getCampaignId() == null) {
                        List<CampaignDTO> dtos = campaignService.uploadAdd(s.getId());
                        dtos.stream().filter(f -> f.getCampaignId() != null).forEach(f -> campaignService.update(f, s.getId()));
                    }
                    break;
                case 2://执行修改操作
                    //修改后获取到修改成功的一些cid
                    List<Long> returnCapaignIds = campaignService.uploadUpdate(new ArrayList<Long>() {{
                        add(s.getCampaignId());
                    }});
                    //获取到修改成功后的cid去本地查询该cid 的mongoid
                    List<String> afterUpdateObjId = campaignService.getCampaignStrIdByCampaignLongId(returnCapaignIds);
                    //获取到mongoId后去campaign_bak表查询，查询到备份的数据然后将备份的数据删除掉,最后将上传更新的那个方法的ls改为null
                    campaignBackUpService.deleteByOId(afterUpdateObjId);
                    break;
                case 3://执行删除操作
                    campaignService.uploadDel(new ArrayList<Long>() {{
                        add(s.getCampaignId());
                    }});
                    break;
            }
        });
    }

    private void onlyUploadAdgroup(List<AdgroupDTO> adgroupDTOsFind) {
        adgroupDTOsFind.stream().filter(s -> s.getLocalStatus() != null).forEach(s -> {
            switch (s.getLocalStatus()) {
                case 1:
                    if (s.getAdgroupId() == null) {
                        List<AdgroupDTO> returnAids = adgroupService.uploadAdd(new ArrayList<String>() {{
                            add(s.getId());
                        }});
                        returnAids.stream().forEach(f -> adgroupService.update(s.getId(), f));
                    }
                    break;
                case 2:
                    List<AdgroupDTO> updatedAdgroupDTO = adgroupService.uploadUpdate(new ArrayList<Long>() {{
                        add(s.getAdgroupId());
                    }});
                    updatedAdgroupDTO.stream().forEach(f -> adgroupService.updateUpdate(s.getAdgroupId(), f));
                    break;
                case 3:
                    String result = adgroupService.uploadDel(s.getAdgroupId());
                    //如果返回null，则表示删除成功，接着要删除该单元以及该单元下的创意和关键字
                    if (result == null) {
                        adgroupService.deleteBubLinks(s.getAdgroupId());
                    }
                    break;
            }
        });
    }

    private void onlyUploadKeyword(List<KeywordDTO> keywordDTOsFind) {
        keywordDTOsFind.stream().filter(s -> s.getLocalStatus() != null).forEach(s -> {
            switch (s.getLocalStatus()) {
                case 1:
                    if (s.getKeywordId() == null) {
                        List<KeywordDTO> returnKeywordDTOs = assistantKeywordService.uploadAdd(new ArrayList<String>() {{
                            add(s.getId());
                        }});
                        returnKeywordDTOs.stream().forEach(f -> assistantKeywordService.update(s.getId(), f));
                        break;
                    }
                case 2:
                    assistantKeywordService.uploadUpdate(new ArrayList<Long>() {{
                        add(s.getKeywordId());
                    }});
                    break;
                case 3:
                    assistantKeywordService.uploadDel(s.getKeywordId());
                    break;
            }
        });
    }

    private void onlyUploadCreative(List<CreativeDTO> creativeDTOsFind) {
        creativeDTOsFind.stream().filter(s -> s.getLocalStatus() != null).forEach(s -> {
            switch (s.getLocalStatus()) {
                case 1:
                    if (s.getCreativeId() == null) {
                        List<CreativeDTO> returnCreativeDTOs = creativeService.uploadAdd(new ArrayList<String>() {{
                            add(s.getId());
                        }});
                        if (returnCreativeDTOs.size() > 0) {
                            returnCreativeDTOs.stream().forEach(f -> creativeService.update(s.getId(), f));
                        }
                    }
                    break;
                case 2:
                    List<CreativeDTO> dtos = creativeService.uploadUpdate(new ArrayList<Long>() {{
                        add(s.getCreativeId());
                    }});
                    if (dtos.size() > 0) {
                        dtos.stream().forEach(f -> creativeService.updateLs(s.getCreativeId(), f));
                    }
                    break;
                case 3:
                    Integer result = creativeService.uploadDel(s.getCreativeId());
                    if (result != 0) {
                        creativeService.deleteByLongId(s.getCreativeId());
                    }
                    break;
            }
        });
    }


}
