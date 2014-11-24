package com.perfect.core;

/**
 * Created by yousheng on 2014/7/25.
 *
 * @author yousheng
 */
public class ContextLocal extends ThreadLocal<SessionObject> {

    @Override
    protected SessionObject initialValue() {
        return null;
    }

}
