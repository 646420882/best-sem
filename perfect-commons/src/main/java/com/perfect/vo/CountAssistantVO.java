package com.perfect.vo;

/**
 * Created by subdong on 15-9-24.
 * 统计　推广助手中的关键字，创意，单元，计划。更改状况
 */
public class CountAssistantVO {

    /**
     * 统计目标名称
     */
    private String name;

    /**
     * 目标名count数量
     */
    private Long countNumber;

    private Long modifiyNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(Long countNumber) {
        this.countNumber = countNumber;
    }

    public Long getModifiyNumber() {
        return modifiyNumber;
    }

    public void setModifiyNumber(Long modifiyNumber) {
        this.modifiyNumber = modifiyNumber;
    }
}
