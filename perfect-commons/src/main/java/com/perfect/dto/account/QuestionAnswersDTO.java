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
     * 字体颜色
     * （0：黑色  1：红色  2：绿色  3：蓝色）
     */
    private Integer fontColor;

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

    public Integer getFontColor() {
        return fontColor;
    }

    public void setFontColor(Integer fontColor) {
        this.fontColor = fontColor;
    }
}
