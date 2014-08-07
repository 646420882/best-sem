package com.perfect.schedule.core;

import java.util.Comparator;
import java.util.List;


/**
 */
public interface IScheduleTaskDeal<T> {

    /**
     */
    public List<T> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception;

    /**


     */
    public Comparator<T> getComparator();

}
