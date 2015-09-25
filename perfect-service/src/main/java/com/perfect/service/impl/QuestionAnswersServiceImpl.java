package com.perfect.service.impl;

import com.perfect.dao.account.QuestionsAnswersDAO;
import com.perfect.dto.account.QuestionAnswersDTO;
import com.perfect.service.QuestionAnswersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by subdong on 15-9-25.
 */
@Service
public class QuestionAnswersServiceImpl implements QuestionAnswersService {

    @Resource
    private QuestionsAnswersDAO questionsAnswersDAO;

    @Override
    public List<QuestionAnswersDTO> findQuestionsAll() {
        return questionsAnswersDAO.findQuestionsAll();
    }

    @Override
    public void deleteQuestions(String id) {
        questionsAnswersDAO.deleteQuestions(id);
    }

    @Override
    public void insertQuestions(QuestionAnswersDTO questionAnswersDTO) {
        questionsAnswersDAO.insertQuestions(questionAnswersDTO);
    }
}
