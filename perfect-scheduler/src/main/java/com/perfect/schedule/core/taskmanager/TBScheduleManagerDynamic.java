package com.perfect.schedule.core.taskmanager;

import com.perfect.schedule.core.TaskItemDefine;
import com.perfect.schedule.core.strategy.TBScheduleManagerFactory;

import java.util.List;

public class TBScheduleManagerDynamic extends TBScheduleManager {
	//private static transient Log log = LogFactory.getLog(TBScheduleManagerDynamic.class);
	
	TBScheduleManagerDynamic(TBScheduleManagerFactory aFactory,
			String baseTaskType, String ownSign, int managerPort,
			String jxmUrl, IScheduleDataManager aScheduleCenter) throws Exception {
		super(aFactory, baseTaskType, ownSign,aScheduleCenter);
	}
	public void initial() throws Exception {
		if (scheduleCenter.isLeader(this.currenScheduleServer.getUuid(),
				scheduleCenter.loadScheduleServerNames(this.currenScheduleServer.getTaskType()))) {

			this.scheduleCenter.initialRunningInfo4Dynamic(	this.currenScheduleServer.getBaseTaskType(),
					this.currenScheduleServer.getOwnSign());
		}
		computerStart();
    }
	
	public void refreshScheduleServerInfo() throws Exception {
		throw new Exception("");
	}

	public boolean isNeedReLoadTaskItemList() throws Exception {
		throw new Exception("");
	}
	public void assignScheduleTask() throws Exception {
		throw new Exception("");
		
	}
	public List<TaskItemDefine> getCurrentScheduleTaskItemList() {
		throw new RuntimeException("");
	}
	public int getTaskItemCount() {
		throw new RuntimeException("");
	}
}
