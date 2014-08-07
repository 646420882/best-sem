package com.perfect.schedule.task.execute;

import com.perfect.schedule.core.IScheduleTaskDealSingle;
import com.perfect.schedule.task.conf.TaskConfig;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yousheng on 2014/8/7.
 *
 * @author yousheng
 */
@Component("reportDataTask")
public class ReportDataTask implements IScheduleTaskDealSingle{



    @Override
    public boolean execute(Object task, String ownSign) throws Exception {
        return false;
    }


    /**
     *
     * @param taskParameter
     * @param ownSign
     * @param taskItemNum
     * @param taskItemList
     * @param eachFetchDataNum
     * @return
     * @throws Exception
     */
    @Override
    public List selectTasks(String taskParameter, String ownSign, int taskItemNum, List taskItemList, int eachFetchDataNum) throws Exception {
        if(ownSign == null || !ownSign.equals(TaskConfig.TASK_DOMAIN)){
            return Collections.emptyList();
        }



        return null;
    }

    @Override
    public Comparator getComparator() {
        return null;
    }
}
