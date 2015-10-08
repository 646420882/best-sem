package com.perfect.app.upload.controller;

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


    @RequestMapping(value = "/upload/schedule/add", method = GET, produces = "application/json")
    public String configureScheduler(@RequestParam(value = "date") Long dateMilli,
                                     @RequestParam(value = "jobDescription", required = false) String jobDescription) {
        Date date = new Date(dateMilli);

        materialsScheduledService.configureScheduler("*/3 * * * * ?", jobDescription);

        return "success";
    }

    @RequestMapping(value = "/upload/schedule/pause", method = GET)
    public String pauseJob() {
        materialsScheduledService.pauseJob();
        return "success";
    }

    @RequestMapping(value = "/upload/schedule/resume", method = GET)
    public String resumeJob() {
        materialsScheduledService.resumeJob();
        return "success";
    }

    @RequestMapping(value = "/upload/schedule/delete", method = GET)
    public String deleteJob() {
        materialsScheduledService.deleteJob();
        return "success";
    }
}
