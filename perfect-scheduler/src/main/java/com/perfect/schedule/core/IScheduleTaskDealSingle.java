package com.perfect.schedule.core;

/**
 * ����������Ľӿ�
 *
 * @param <T>��������
 * @author xuannan
 */
public interface IScheduleTaskDealSingle<T> extends IScheduleTaskDeal<T> {
    /**
     * ִ�е�������
     *
     * @param task    Object
     * @param ownSign ��ǰ�������
     * @throws Exception
     */
    public boolean execute(T task, String ownSign) throws Exception;

}
