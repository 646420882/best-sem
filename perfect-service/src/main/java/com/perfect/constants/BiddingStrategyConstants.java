package com.perfect.constants;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
public enum BiddingStrategyConstants {

    //竞价成功就放弃
//    SUCCESS_SKIP,
//    //竞价成功继续竞价
//    SUCCESS_CONTINUE,
//
//    SUCCESS_SKIP_IF_LAST,
//    FAILED_OK_HIGHPOS,FAILED_OK_RESET,FAILED_OK_SPECIFY,FAILED_OK_MAX,
    POS_LEFT_1(1),POS_LEFT_2_3(2),POS_RIGHT_1_3(3),POS_RIGHT_OTHERS(4),

    FAILED_KEEP(11),FAILED_ROLLBACK(12),

    TYPE_PC(10000), TYPE_MOBILE(10001),

    SPD_FAST(101), SPD_ECONOMIC(102);
    private final int value;

    private BiddingStrategyConstants(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
