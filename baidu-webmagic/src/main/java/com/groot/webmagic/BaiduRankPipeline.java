package com.groot.webmagic;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yousheng on 14/11/5.
 */
public class BaiduRankPipeline implements Pipeline, Constant {

    @Override
    public void process(ResultItems resultItems, Task task) {

        Container.put(resultItems.get(REQUEST_UUID).toString(),resultItems.getAll());
    }
}
