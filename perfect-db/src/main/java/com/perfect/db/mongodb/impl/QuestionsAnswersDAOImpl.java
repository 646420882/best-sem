package com.perfect.db.mongodb.impl;


import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.dao.account.QuestionsAnswersDAO;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.account.QuestionAnswersDTO;
import com.perfect.entity.account.QuestionAnswersEntity;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by subdong on 15-9-25.
 */
@Repository("questionsAnswersDAO")
public class QuestionsAnswersDAOImpl implements QuestionsAnswersDAO {
    @Override
    public List<QuestionAnswersDTO> findQuestionsAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        List<QuestionAnswersEntity> entities = mongoTemplate.find(new Query(), QuestionAnswersEntity.class);
        return ObjectUtils.convert(entities, QuestionAnswersDTO.class);
    }

    @Override
    public void deleteQuestions(String id) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        mongoTemplate.remove(Query.query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(id)), MongoEntityConstants.TBL_ANSWERS);
    }

    @Override
    public void insertQuestions(QuestionAnswersDTO questionAnswersDTO) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();

        mongoTemplate.insert(ObjectUtils.convert(questionAnswersDTO, QuestionAnswersEntity.class));
    }
}
