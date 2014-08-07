package com.perfect.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yousheng on 2014/7/25.
 *
 * @author yousheng
 */
public class ContextLocal extends ThreadLocal<Map<String,Object>> {

    @Override
    protected Map<String, Object> initialValue() {
        return new HashMap<String,Object>();
    }


    public void clear(){
        get().clear();
    }
}
