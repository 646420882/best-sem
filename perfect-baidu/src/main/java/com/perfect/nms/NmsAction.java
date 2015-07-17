package com.perfect.nms;

import rx.functions.Action1;

/**
 * Created by dolphineor on 2015-7-17.
 */
public class NmsAction implements Action1<String> {
    @Override
    public void call(String fileUrl) {
        // 消息处理
        System.out.println("fileUrl: " + fileUrl);
    }
}
