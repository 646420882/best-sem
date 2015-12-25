package com.perfect.db.mongodb.impl;


import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.dao.account.QuestionsAnswersDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.account.QuestionAnswersDTO;
import com.perfect.entity.account.QuestionAnswersEntity;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by subdong on 15-9-25.
 */
@Repository("questionsAnswersDAO")
public class QuestionsAnswersDAOImpl extends AbstractSysBaseDAOImpl<QuestionAnswersDTO, String> implements QuestionsAnswersDAO {
    @Override
    public List<QuestionAnswersDTO> findQuestionsAll() {
        List<QuestionAnswersEntity> entities = getSysMongoTemplate().find(new Query(), QuestionAnswersEntity.class);
        return ObjectUtils.convert(entities, QuestionAnswersDTO.class);
    }

    @Override
    public void deleteQuestions(String id) {
        getSysMongoTemplate().remove(Query.query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(id)), MongoEntityConstants.TBL_ANSWERS);
    }

    @Override
    public void insertQuestions(QuestionAnswersDTO questionAnswersDTO) {

        getSysMongoTemplate().insert(ObjectUtils.convert(questionAnswersDTO, QuestionAnswersEntity.class));
    }

    @Override
    public void modifyQuestions(QuestionAnswersDTO modifyAnswers) {
        Update update = new Update();
        update.set("ques", modifyAnswers.getQuestions());
        update.set("ans", modifyAnswers.getAnswers());
        update.set("qt", modifyAnswers.getQuestionType());
        update.set("fc", modifyAnswers.getFontColor());


        getSysMongoTemplate().updateFirst(new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(modifyAnswers.getId())), update, QuestionAnswersEntity.class);
    }

    @Override
    public Class<QuestionAnswersEntity> getEntityClass() {
        return QuestionAnswersEntity.class;
    }

    @Override
    public Class<QuestionAnswersDTO> getDTOClass() {
        return QuestionAnswersDTO.class;
    }
}
