package com.perfect.dao.account;

import com.perfect.dto.account.QuestionAnswersDTO;

import java.util.List;

/**
 * Created by subdong on 15-9-25.
 */
public interface QuestionsAnswersDAO {

    /**
     * 查询所有问答
     *
     * @return
     */
    List<QuestionAnswersDTO> findQuestionsAll();

    /**
     * 通过id删除问答
     *
     * @param id
     */
    void deleteQuestions(String id);

    /**
     * 添加一条问答
     *
     * @param questionAnswersDTO
     */
    void insertQuestions(QuestionAnswersDTO questionAnswersDTO);

    /**
     * 修改问答
     * @param modifyAnswers
     */
    void modifyQuestions(QuestionAnswersDTO modifyAnswers);
}
