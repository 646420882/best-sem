package com.perfect.app.assistant.controller;

import com.perfect.commons.web.WebContextSupport;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.campaign.CampaignDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2015/1/19.
 */
@Controller
@RequestMapping(value = "/uploadMerge")
public class AssistantUploadMerge extends WebContextSupport {
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
    public ModelAndView uploadMerge(){

        return writeMapObject(MSG,SUCCESS);
    }

    @RequestMapping(value = "/getCampList")
    public ModelAndView getUploadOperate(){
       List<CampaignDTO> campaignDTOs= campaignService.getOperateCamp();
        return writeMapObject(DATA,campaignDTOs);
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
            return writeMapObject(MSG, "批量上传部分成功!");
        }


    }

    private void uploadCampaign() {
        //首先，查询出计划中所有要本地有操作的计划
        List<CampaignDTO> campaignDTOsFind = campaignService.findHasLocalStatus();
        //查询出所有的计划后，遍历所有计划
        campaignDTOsFind.parallelStream().filter(s -> s.getLocalStatus() != null).forEach(s -> {//遍历
            switch (s.getLocalStatus()) {
                case 1://执行添加操作
                    List<CampaignDTO> dtos = campaignService.uploadAdd(s.getId());
                    dtos.parallelStream().filter(f -> f.getCampaignId() != null).forEach(f -> campaignService.update(f, s.getId()));
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

    private void uploadAdgroup() {
        List<AdgroupDTO> adgroupDTOsFind = adgroupService.findHasLocalStatus();
        //仍然遍历其中所有单元,并过滤其中ls有改动的
        adgroupDTOsFind.parallelStream().filter(s -> s.getLocalStatus() != null).forEach(s -> {
            switch (s.getLocalStatus()) {
                case 1:
                    List<AdgroupDTO> returnAids = adgroupService.uploadAdd(new ArrayList<String>() {{
                        add(s.getId());
                    }});
                    returnAids.parallelStream().forEach(f -> adgroupService.update(s.getId(), f));
                    break;
                case 2:
                    List<AdgroupDTO> updatedAdgroupDTO = adgroupService.uploadUpdate(new ArrayList<Long>() {{
                        add(s.getAdgroupId());
                    }});
                    updatedAdgroupDTO.parallelStream().forEach(f -> adgroupService.updateUpdate(s.getAdgroupId(), f));
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

    private void uploadKeyword() {
        List<KeywordDTO> keywordDTOsFind = assistantKeywordService.findHasLocalStatus();
//        遍历其中所有关键字,并过滤其中ls有改动的
        keywordDTOsFind.parallelStream().filter(s -> s.getLocalStatus() != null).forEach(s -> {
            switch (s.getLocalStatus()) {
                case 1:
                    List<KeywordDTO> returnKeywordDTOs = assistantKeywordService.uploadAdd(new ArrayList<String>() {{
                        add(s.getId());
                    }});
                    returnKeywordDTOs.parallelStream().forEach(f -> assistantKeywordService.update(s.getId(), f));
                    break;
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

    private void uploadCreative() {
        //创意全表查询很不合理，所以只查询ls有改动的
        List<CreativeDTO> creativeDTOFind = creativeService.findHasLocalStatus();
        creativeDTOFind.parallelStream().filter(s -> s.getLocalStatus() != null).forEach(s -> {
            switch (s.getLocalStatus()) {
                case 1:
                    List<CreativeDTO> returnCreativeDTOs = creativeService.uploadAdd(new ArrayList<String>() {{
                        add(s.getId());
                    }});
                    if (returnCreativeDTOs.size() > 0) {
                        returnCreativeDTOs.parallelStream().forEach(f -> creativeService.update(s.getId(), f));
                    }
                    break;
                case 2:
                    List<CreativeDTO> dtos = creativeService.uploadUpdate(new ArrayList<Long>() {{
                        add(s.getCreativeId());
                    }});
                    if (dtos.size() > 0) {
                        dtos.parallelStream().forEach(f -> creativeService.updateLs(s.getCreativeId(), f));
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
