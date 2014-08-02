package com.perfect.schedule.core.taskmanager;

import com.perfect.schedule.core.TaskItemDefine;

import java.util.List;


/**
 * �����������Ŀͻ��˽ӿڣ������л�����ݿ��ʵ�֣������л���ConfigServer��ʵ��
 *
 * @author xuannan
 */
public interface IScheduleDataManager {
    public long getSystemTime();

    /**
     * ����װ�ص�ǰserver��Ҫ�������ݶ���
     *
     * @param taskType ��������
     * @param uuid     ��ǰserver��UUID
     * @return
     * @throws Exception
     */
    public List<TaskItemDefine> reloadDealTaskItem(String taskType, String uuid) throws Exception;

    /**
     * װ�����е����������Ϣ
     *
     * @param taskType
     * @return
     * @throws Exception
     */
    public List<ScheduleTaskItem> loadAllTaskItem(String taskType) throws Exception;

    /**
     * �ͷ��Լ��ѳ֣���������Ķ���
     *
     * @param taskType
     * @param uuid
     * @return
     * @throws Exception
     */
    public void releaseDealTaskItem(String taskType, String uuid) throws Exception;

    /**
     * ��ȡһ���������͵Ĵ����������
     *
     * @param taskType
     * @return
     * @throws Exception
     */
    public int queryTaskItemCount(String taskType) throws Exception;

    /**
     * װ���������������Ϣ
     *
     * @param taskType
     * @throws Exception
     */
    public ScheduleTaskType loadTaskTypeBaseInfo(String taskType) throws Exception;

    /**
     * ����Ѿ����ڵĵ��ȷ�������Ϣ
     *
     * @param taskInfo
     * @throws Exception
     */
    public int clearExpireScheduleServer(String taskType, long expireTime) throws Exception;

    /**
     * ���������Ϣ���������Ѿ������ڵ�ʱ��
     *
     * @param taskInfo
     * @throws Exception
     */
    public int clearTaskItem(String taskType, List<String> serverList) throws Exception;

    /**
     * ��ȡ���е���Ч��������Ϣ
     *
     * @param taskInfo
     * @return
     * @throws Exception
     */
    public List<ScheduleServer> selectAllValidScheduleServer(String taskType) throws Exception;

    public List<String> loadScheduleServerNames(String taskType) throws Exception;

    /**
     * ���·�������Item
     *
     * @param taskType
     * @param serverList
     * @throws Exception
     */
    public void assignTaskItem(String taskType, String currentUuid, int maxNumOfOneServer, List<String> serverList) throws Exception;

    /**
     * ����������Ϣ
     *
     * @param server
     * @throws Exception
     */
    public boolean refreshScheduleServer(ScheduleServer server) throws Exception;

    /**
     * ע�������
     *
     * @param server
     * @throws Exception
     */
    public void registerScheduleServer(ScheduleServer server) throws Exception;

    /**
     * ע�������
     *
     * @param serverUUID
     * @throws Exception
     */
    public void unRegisterScheduleServer(String taskType, String serverUUID) throws Exception;

    /**
     * ����Ѿ����ڵ�OWN_SIGN���Զ���ɵ����
     *
     * @param taskType           ��������
     * @param serverUUID         ������
     * @param expireDateInternal ����ʱ�䣬����Ϊ��λ
     * @throws Exception
     */
    public void clearExpireTaskTypeRunningInfo(String baseTaskType, String serverUUID, double expireDateInternal) throws Exception;

    public boolean isLeader(String uuid, List<String> serverList);

    public void pauseAllServer(String baseTaskType) throws Exception;

    public void resumeAllServer(String baseTaskType) throws Exception;

    public List<ScheduleTaskType> getAllTaskTypeBaseInfo() throws Exception;

    /**
     * ���һ���������͵���������Ϣ
     *
     * @param baseTaskType
     * @throws Exception
     */
    public void clearTaskType(String baseTaskType) throws Exception;

    /**
     * ����һ���µ���������
     *
     * @param baseTaskType
     * @throws Exception
     */
    public void createBaseTaskType(ScheduleTaskType baseTaskType) throws Exception;

    public void updateBaseTaskType(ScheduleTaskType baseTaskType) throws Exception;

    public List<ScheduleTaskTypeRunningInfo> getAllTaskTypeRunningInfo(String baseTaskType) throws Exception;

    /**
     * ɾ��һ����������
     *
     * @param baseTaskType
     * @throws Exception
     */
    public void deleteTaskType(String baseTaskType) throws Exception;

    /**
     * ���������ѯ��ǰ���ȷ���
     *
     * @param baseTaskType
     * @param ownSign
     * @param ip
     * @param orderStr
     * @return
     * @throws Exception
     */
    public List<ScheduleServer> selectScheduleServer(String baseTaskType, String ownSign, String ip, String orderStr)
            throws Exception;

    /**
     * ��ѯ���ȷ������ʷ��¼
     *
     * @param baseTaskType
     * @param ownSign
     * @param ip
     * @param orderStr
     * @return
     * @throws Exception
     */
    public List<ScheduleServer> selectHistoryScheduleServer(String baseTaskType, String ownSign, String ip, String orderStr)
            throws Exception;

    public List<ScheduleServer> selectScheduleServerByManagerFactoryUUID(String factoryUUID) throws Exception;

    /**
     * ���������ע�����е� CurrentSever��RequestServer����������
     *
     * @param taskItems
     * @throws Exception
     */
    public void createScheduleTaskItem(ScheduleTaskItem[] taskItems) throws Exception;

    /**
     * ���������״̬�ʹ�����Ϣ
     *
     * @param taskType
     * @param sts
     * @param message
     */
    public void updateScheduleTaskItemStatus(String taskType, String taskItem, ScheduleTaskItem.TaskItemSts sts, String message) throws Exception;

    /**
     * ɾ��������
     *
     * @param taskType
     * @param taskItem
     */
    public void deleteScheduleTaskItem(String taskType, String taskItem) throws Exception;

    /**
     * ��ʼ��������ȵ�����Ϣ�;�̬������Ϣ
     *
     * @param baseTaskType
     * @param ownSign
     * @param serverUUID
     * @throws Exception
     */
    public void initialRunningInfo4Static(String baseTaskType, String ownSign, String uuid) throws Exception;

    public void initialRunningInfo4Dynamic(String baseTaskType, String ownSign) throws Exception;

    /**
     * ��������Ϣ�Ƿ��ʼ���ɹ�
     *
     * @param baseTaskType
     * @param ownSign
     * @param serverUUID
     * @return
     * @throws Exception
     */
    public boolean isInitialRunningInfoSucuss(String baseTaskType, String ownSign) throws Exception;

    public void setInitialRunningInfoSucuss(String baseTaskType, String taskType, String uuid) throws Exception;

    public String getLeader(List<String> serverList);

    public long updateReloadTaskItemFlag(String taskType) throws Exception;

    public long getReloadTaskItemFlag(String taskType) throws Exception;

}
