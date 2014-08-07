package com.perfect.schedule.task.execute;

import com.perfect.schedule.core.IScheduleTaskDealSingle;

import java.util.Comparator;
import java.util.List;

/**
 * Created by yousheng on 2014/8/7.
 *
 * @author yousheng
 */
public class ReportDataTask implements IScheduleTaskDealSingle{


    @Override
    public boolean execute(Object task, String ownSign) throws Exception {
        return false;
    }

    @Override
    public List selectTasks(String taskParameter, String ownSign, int taskItemNum, List taskItemList, int eachFetchDataNum) throws Exception {
        return null;
    }

    @Override
    public Comparator getComparator() {
        return null;
    }
}
