package com.perfect.schedule.core;

import java.util.Comparator;
import java.util.List;


/**
 * ����������Ļ�ӿ�
 *
 * @param <T> ��������
 * @author xuannan
 */
public interface IScheduleTaskDeal<T> {

    /**
     * �����������ѯ��ǰ���ȷ������ɴ��������
     *
     * @param taskParameter    ������Զ������
     * @param ownSign          ��ǰ�������
     * @param taskItemNum      ��ǰ�������͵������������
     * @param taskItemList     ��ǰ���ȷ����������䵽�Ŀɴ������
     * @param eachFetchDataNum ÿ�λ�ȡ��ݵ�����
     * @return
     * @throws Exception
     */
    public List<T> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception;

    /**
     * ��ȡ����ıȽ���,��Ҫ��NotSleepģʽ����Ҫ�õ�
     *
     * @return
     */
    public Comparator<T> getComparator();

}
