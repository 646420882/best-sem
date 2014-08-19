package com.perfect.test;

import com.perfect.schedule.core.IScheduleTaskDealMulti;
import com.perfect.schedule.core.TaskItemDefine;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yousheng on 2014/8/15.
 *
 * @author yousheng
 */
@Component("testTask")
public class Test implements IScheduleTaskDealMulti<Integer> {


    @Override
    public boolean execute(Integer[] task, String ownSign) throws Exception {
        System.out.println(Thread.currentThread().getName() + " ============ " + Arrays.toString(task));
        return true;
    }

    @Override
    public List<Integer> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {

        List<Integer> strList = new ArrayList<>();
        for (int i = 1 ; i <=20 ; i++){
            strList.add(i);
        }
        return strList;
    }

    @Override
    public Comparator<Integer> getComparator() {
        return null;
    }
}
