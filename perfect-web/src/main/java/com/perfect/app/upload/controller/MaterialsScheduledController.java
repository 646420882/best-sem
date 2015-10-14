package com.perfect.app.upload.controller;

import com.perfect.commons.constants.MaterialsJobEnum;
import com.perfect.service.MaterialsScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

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


    @RequestMapping(value = "/schedule/upload", method = GET, produces = "application/json")
    public String configureScheduler(@RequestParam(value = "date") Long dateMilli) {
        Date date = new Date(dateMilli);

        materialsScheduledService.configureScheduler(MaterialsJobEnum.UPLOAD_MATERIALS.value(), "*/3 * * * * ?");

        return "success";
    }

    @RequestMapping(value = "/schedule/pause", method = GET, produces = "application/json")
    public String pause() {
        materialsScheduledService.configureScheduler(MaterialsJobEnum.PAUSE_MATERIALS.value(), "*/3 * * * * ?");

        return "success";
    }
}
