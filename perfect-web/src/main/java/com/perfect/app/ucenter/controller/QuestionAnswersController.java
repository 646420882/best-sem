package com.perfect.app.ucenter.controller;

import com.perfect.dto.account.QuestionAnswersDTO;
import com.perfect.service.QuestionAnswersService;
import com.perfect.utils.json.JSONUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by subdong on 15-9-25.
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/qa")
public class QuestionAnswersController {

    @Resource
    private QuestionAnswersService questionAnswersService;

    /**
     * 添加问答
     *
     * @return
     */
    @RequestMapping(value = "/getPage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getPage() {
        return new ModelAndView("help/help");
    }




    /**
     * 添加问答
     *
     * @return
     */
    @RequestMapping(value = "/addAnswers", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public void addAnswers(@RequestBody QuestionAnswersDTO querstions) {
        questionAnswersService.insertQuestions(querstions);
    }
    /**
     * 修改问答
     *
     * @return
     */
    @RequestMapping(value = "/modifyAnswers", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public void modifyAnswers(@RequestBody QuestionAnswersDTO querstions) {
        questionAnswersService.modifyQuestions(querstions);
    }

    /**
     * 通过id删除问答
     *
     * @return
     */
    @RequestMapping(value = "/delAnswers", method = {RequestMethod.GET, RequestMethod.POST})
    public void delAnswers(@RequestParam(value = "id") String id) {
        questionAnswersService.deleteQuestions(id);
    }

    /**
     * 通过查询问答
     *
     * @return
     */
    @RequestMapping(value = "/findAnswers", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView findAnswers() {

        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

        List<QuestionAnswersDTO> dtoList = questionAnswersService.findQuestionsAll();

        jsonView.setAttributesMap(JSONUtils.getJsonMapData(dtoList));
        return new ModelAndView(jsonView);
    }

}
