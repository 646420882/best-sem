package com.perfect.schedule.core.taskmanager;

interface IScheduleProcessor {
	 /**



	  */
	 public boolean isDealFinishAllData();
	 /**


	  */
	 public boolean isSleeping();
	 /**


	  */
	 public void stopSchedule() throws Exception;
	 
	 /**

	  */
	 public void clearAllHasFetchData();
}
