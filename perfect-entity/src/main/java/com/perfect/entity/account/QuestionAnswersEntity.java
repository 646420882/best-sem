package com.perfect.entity.account;

import com.perfect.commons.constants.MongoEntityConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by subdong on 15-9-25.
 *
 * @description Q&A 实体类
 */
@Document(collection = MongoEntityConstants.TBL_ANSWERS)
public class QuestionAnswersEntity {

    @Id
    private String id;

    /**
     * 问题
     */
    @Field("ques")
    private String questions;

    /**
     * 答案
     */
    @Field("ans")
    private String answers;

    /**
     * 问题分类 0、账户全景  1推广助手  2智能结构  3智能竞价  4数据报告
     */
    @Field("qt")
    private String questionType;

    /**
     * 字体颜色
     * （0：黑色  1：红色  2：绿色  3：蓝色）
     */
    @Field("fc")
    private String fontColor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getFontColor() {
        return fontColor;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }
}
