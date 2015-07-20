package com.perfect.entity.report;

import com.perfect.entity.account.AccountIdEntity;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

/**
 * Created by subdong on 15-7-20.
 */
public class NmsAccountReportEntity extends AccountIdEntity {



    @Field(value = "impr")
    private Integer impression;     // 展现次数

    @Field(value = "click")
    private Integer click;      // 点击次数

    @Field(value = "ctr")
    private Double ctr;     // 点击率=点击次数/展现次数

    @Field(value = "cost")
    private BigDecimal cost;        // 消费

    @Field(value = "cpm")
    private BigDecimal cpm;       // 千次展现消费

    @Field(value = "acp")
    private BigDecimal acp;     // 平均点击价格=消费/点击次数

    @Field(value = "srchuv")
    private Integer srchuv;     // 展现独立访客

    @Field(value = "clickuv")
    private Integer clickuv;    // 点击独立访客

    @Field(value = "srsur")
    private Integer srsur;  // 展现频次

    @Field(value = "cusur")
    private Double cusur;  // 独立访客点击率

    @Field(value = "cocur")
    private BigDecimal cocur;  // 平均独立访客点击价格

    @Field(value = "ar")
    private Double arrivalRate; // 抵达率

    @Field(value = "hr")
    private Double hopRate;    // 二跳率

    @Field(value = "art")
    private Long avgResTime;  // 平均访问时间

    @Field(value = "dt")
    private Integer directTrans;    // 直接转化

    @Field(value = "idt")
    private Integer indirectTrans;  // 间接转化

}
