package com.perfect.dto.account;

/**
 * Created by subdong on 15-9-25.
 */
public class QuestionAnswersDTO {

    private String id;

    /**
     * 问题
     */
    private String questions;

    /**
     * 答案
     */
    private String answers;

    /**
     * 问题分类 0、账户全景  1 推广助手  2 智能结构  3 智能竞价  4 数据报告
     */
    private String questionType;

    /**
     * 字体颜色
     * （0：黑色  1：红色  2：绿色  3：蓝色）
     */
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

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }
    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
}
