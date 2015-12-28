package com.perfect.app.upload.controller;

import com.google.common.collect.Maps;
import com.perfect.commons.constants.MaterialsJobEnum;
import com.perfect.service.MaterialsScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created on 2015-09-29.
 * <p>物料定时上传控制器.
 *
 * @author dolphineor
 */
@RestController
@Scope(SCOPE_PROTOTYPE)
@RequestMapping("/material")
public class MaterialsScheduledController {

    @Autowired
    private MaterialsScheduledService materialsScheduledService;

    public void setMaterialsScheduledService(@Qualifier("materialsScheduledService")
                                             MaterialsScheduledService materialsScheduledService) {
        this.materialsScheduledService = materialsScheduledService;
    }


    @RequestMapping(value = "/schedule/upload/{level}/{cron}", method = POST, produces = "application/json")
    public ModelAndView configureScheduler(@PathVariable String level,
                                           @PathVariable(value = "cron") String cronExpression,
                                           @RequestBody String[] ids) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = Maps.newHashMap();

        int jobLevel = 0;
        switch (level) {
            case "campaign": {
                jobLevel = MaterialsJobEnum.CAMPAIGN.value();
                break;
            }
            case "adgroup": {
                jobLevel = MaterialsJobEnum.ADGROUP.value();
                break;
            }
            case "keyword": {
                jobLevel = MaterialsJobEnum.KEYWORD.value();
                break;
            }
            case "creative": {
                jobLevel = MaterialsJobEnum.CREATIVE.value();
                break;
            }
            default:
                break;
        }

        if (jobLevel == 0) {
            attrMap.put("status", "不合法的物料层级");
            jsonView.setAttributesMap(attrMap);
            return new ModelAndView(jsonView);
        }

        if (materialsScheduledService.isExists(MaterialsJobEnum.UPLOAD_MATERIALS.value(), jobLevel, ids)) {
            attrMap.put("status", "任务已经存在");
            jsonView.setAttributesMap(attrMap);
            return new ModelAndView(jsonView);
        } else {
            materialsScheduledService.configureScheduler(MaterialsJobEnum.UPLOAD_MATERIALS.value(), jobLevel, ids, cronExpression);
            attrMap.put("status", "ok");
            jsonView.setAttributesMap(attrMap);
            return new ModelAndView(jsonView);
        }
    }

    // TODO 物料定时暂停
    @RequestMapping(value = "/schedule/pause", method = POST, produces = "application/json")
    public ModelAndView pause(@RequestParam(value = "cron") String cronExpression) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = Maps.newHashMap();

        synchronized (this) {
            if (materialsScheduledService.isExists(MaterialsJobEnum.PAUSE_MATERIALS.value(), 1, null)) {
                attrMap.put("status", "failed");

                jsonView.setAttributesMap(attrMap);
                return new ModelAndView(jsonView);
            } else {
                materialsScheduledService.configureScheduler(MaterialsJobEnum.PAUSE_MATERIALS.value(), 1, null, cronExpression);
                attrMap.put("status", "success");

                jsonView.setAttributesMap(attrMap);
                return new ModelAndView(jsonView);
            }
        }
    }
}
