package com.perfect.schedule.core;

/**
 * @author xuannan
 *
 */
public interface IScheduleTaskDealMulti<T>  extends IScheduleTaskDeal<T> {
 
    /**
     * ִ�и���������顣��Ϊ���Ͳ�֧��new ���飬ֻ�ܴ���OBJECT[]
     *
     * @param tasks   ��������
     * @param ownSign ��ǰ�������
     * @return
     * @throws Exception
     */
    public boolean execute(T[] tasks, String ownSign) throws Exception;
}
