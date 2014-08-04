package com.perfect.schedule.core.taskmanager;

import com.perfect.schedule.core.TaskItemDefine;

import java.util.List;


public interface IScheduleDataManager{
	public long getSystemTime();
	public List<TaskItemDefine> reloadDealTaskItem(String taskType, String uuid) throws Exception;

	public List<ScheduleTaskItem> loadAllTaskItem(String taskType) throws Exception;
	
	public void releaseDealTaskItem(String taskType, String uuid) throws Exception;

	public int queryTaskItemCount(String taskType) throws Exception;

	public ScheduleTaskType loadTaskTypeBaseInfo(String taskType) throws Exception;
	
	public int clearExpireScheduleServer(String taskType, long expireTime) throws Exception;

	public int clearTaskItem(String taskType, List<String> serverList) throws Exception;

	public List<ScheduleServer> selectAllValidScheduleServer(String taskType) throws Exception;
	public List<String> loadScheduleServerNames(String taskType)throws Exception;
	public void assignTaskItem(String taskType, String currentUuid, int maxNumOfOneServer, List<String> serverList) throws Exception;

	public boolean refreshScheduleServer(ScheduleServer server) throws Exception;

	public void registerScheduleServer(ScheduleServer server) throws Exception;

	public void unRegisterScheduleServer(String taskType, String serverUUID) throws Exception;


	public void clearExpireTaskTypeRunningInfo(String baseTaskType, String serverUUID, double expireDateInternal)throws Exception;
	
	public boolean isLeader(String uuid, List<String> serverList);
	
	public void pauseAllServer(String baseTaskType)throws Exception;
	public void resumeAllServer(String baseTaskType)throws Exception;

	public List<ScheduleTaskType> getAllTaskTypeBaseInfo()throws Exception;
	public void clearTaskType(String baseTaskType) throws Exception;

    public void createBaseTaskType(ScheduleTaskType baseTaskType) throws Exception;
    public void updateBaseTaskType(ScheduleTaskType baseTaskType) throws Exception;
    public List<ScheduleTaskTypeRunningInfo> getAllTaskTypeRunningInfo(String baseTaskType) throws Exception;
    
    public void deleteTaskType(String baseTaskType) throws Exception;
	
	public List<ScheduleServer> selectScheduleServer(String baseTaskType, String ownSign, String ip, String orderStr)
			throws Exception;

	public List<ScheduleServer> selectHistoryScheduleServer(String baseTaskType, String ownSign, String ip, String orderStr)
			throws Exception;

	public List<ScheduleServer> selectScheduleServerByManagerFactoryUUID(String factoryUUID) throws Exception;

	public void createScheduleTaskItem(ScheduleTaskItem[] taskItems) throws Exception;
	
	public void updateScheduleTaskItemStatus(String taskType, String taskItem, ScheduleTaskItem.TaskItemSts sts, String message)throws Exception;

	public void deleteScheduleTaskItem(String taskType, String taskItem) throws Exception;

	public void initialRunningInfo4Static(String baseTaskType, String ownSign, String uuid)throws Exception;
	public void initialRunningInfo4Dynamic(String baseTaskType, String ownSign)throws Exception;

    public boolean isInitialRunningInfoSucuss(String baseTaskType, String ownSign) throws Exception;
	public void setInitialRunningInfoSucuss(String baseTaskType, String taskType, String uuid) throws Exception;
	public String getLeader(List<String> serverList);
	
	public long updateReloadTaskItemFlag(String taskType) throws Exception;
	public long getReloadTaskItemFlag(String taskType) throws Exception;
	 
}
