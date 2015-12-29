package com.perfect.app.upload.controller;

import com.google.common.collect.Maps;
import com.perfect.commons.constants.MaterialsJobEnum;
import com.perfect.service.MaterialsScheduledService;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

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


    @RequestMapping(value = "/schedule/upload/{level}", method = POST, produces = "application/json")
    public ModelAndView configureScheduler(@PathVariable String level,
                                           @RequestParam(value = "cron") String cronExpression,
                                           @RequestBody String[] ids) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = Maps.newHashMap();

        try {
            if (Objects.isNull(new CronExpression(cronExpression).getNextValidTimeAfter(new Date()))) {
                attrMap.put("status", "无效的Cron表达式");
                jsonView.setAttributesMap(attrMap);
                return new ModelAndView(jsonView);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            attrMap.put("status", "无效的Cron表达式");
            jsonView.setAttributesMap(attrMap);
            return new ModelAndView(jsonView);
        }

        int jobLevel = getJobLevel(level);

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

    @RequestMapping(value = "/schedule/pause/{level}", method = POST, produces = "application/json")
    public ModelAndView pause(@PathVariable String level, @RequestBody String cronExpression) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = Maps.newHashMap();

        try {
            if (Objects.isNull(new CronExpression(cronExpression).getNextValidTimeAfter(new Date()))) {
                attrMap.put("status", "无效的Cron表达式");
                jsonView.setAttributesMap(attrMap);
                return new ModelAndView(jsonView);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            attrMap.put("status", "无效的Cron表达式");
            jsonView.setAttributesMap(attrMap);
            return new ModelAndView(jsonView);
        }

        int jobLevel = getJobLevel(level);

        if (jobLevel == 0) {
            attrMap.put("status", "不合法的物料层级");
            jsonView.setAttributesMap(attrMap);
            return new ModelAndView(jsonView);
        }

        if (materialsScheduledService.isExists(MaterialsJobEnum.PAUSE_MATERIALS.value(), jobLevel, new String[]{})) {
            attrMap.put("status", "任务已经存在");

            jsonView.setAttributesMap(attrMap);
            return new ModelAndView(jsonView);
        } else {
            materialsScheduledService.configureScheduler(MaterialsJobEnum.PAUSE_MATERIALS.value(), jobLevel, new String[]{}, cronExpression);
            attrMap.put("status", "ok");

            jsonView.setAttributesMap(attrMap);
            return new ModelAndView(jsonView);
        }
    }

    private int getJobLevel(String level) {
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

        return jobLevel;
    }
}
