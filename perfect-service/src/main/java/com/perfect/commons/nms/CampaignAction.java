package com.perfect.commons.nms;

import rx.functions.Action1;

/**
 * Created by dolphineor on 2015-7-21.
 */
public class CampaignAction implements Action1<String> {

    @Override
    public void call(String s) {
        System.out.println(s);
    }
}
