package com.perfect.schedule.core;

/**
 * @author xuannan
 *
 */
public interface IScheduleTaskDealMulti<T>  extends IScheduleTaskDeal<T> {
 
/**
 * @throws Exception
 */
  public boolean execute(T[] tasks, String ownSign) throws Exception;
}
