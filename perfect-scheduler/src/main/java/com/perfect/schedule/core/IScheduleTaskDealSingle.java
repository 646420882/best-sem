package com.perfect.schedule.core;

/**
 * @author xuannan
 *
 */
public interface IScheduleTaskDealSingle<T> extends IScheduleTaskDeal<T> {
  /**
   * @param task Object
   * @throws Exception
   */
  public boolean execute(T task, String ownSign) throws Exception;
  
}
