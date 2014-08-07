package com.perfect.constants;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
public enum BiddingStrategy {

    //竞价成功就放弃
    SUCCESS_SKIP,
    //竞价成功继续竞价
    SUCCESS_CONTINUE,

    SUCCESS_SKIP_IF_LAST,
    FAILED_OK_HIGHPOS,FAILED_OK_RESET,FAILED_OK_SPECIFY,FAILED_OK_MAX,
    ;

}
