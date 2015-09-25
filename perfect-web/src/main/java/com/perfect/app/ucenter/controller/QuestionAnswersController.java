package com.perfect.app.ucenter.controller;

import com.perfect.core.AppContext;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.account.QuestionAnswersDTO;
import com.perfect.entity.account.QuestionAnswersEntity;
import com.perfect.service.QuestionAnswersService;
import com.perfect.utils.json.JSONUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
    @RequestMapping(value = "/addAnswers", method = {RequestMethod.GET, RequestMethod.POST})
    public void addAnswers(@RequestParam(value = "querstions") String querstions,
                           @RequestParam(value = "answers") String answers,
                           @RequestParam(value = "fontColor") Integer fontColor) {

        QuestionAnswersDTO dto = new QuestionAnswersDTO();
        dto.setQuestions(querstions);
        dto.setAnswers(answers);
        dto.setFontColor(fontColor);
        questionAnswersService.insertQuestions(dto);
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
     * 通过id删除问答
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
