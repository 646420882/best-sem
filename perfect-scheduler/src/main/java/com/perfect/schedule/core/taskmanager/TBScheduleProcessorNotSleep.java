package com.perfect.schedule.core.taskmanager;

import com.perfect.schedule.core.IScheduleTaskDeal;
import com.perfect.schedule.core.IScheduleTaskDealMulti;
import com.perfect.schedule.core.IScheduleTaskDealSingle;
import com.perfect.schedule.core.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 */
class TBScheduleProcessorNotSleep<T> implements IScheduleProcessor, Runnable {
	
	private static transient Logger logger = LoggerFactory.getLogger(TBScheduleProcessorNotSleep.class);
	
	List<Thread> threadList =  Collections.synchronizedList(new ArrayList<Thread>());
	/**
	 */
	protected TBScheduleManager scheduleManager;
	/**
	 */
	ScheduleTaskType taskTypeInfo;
	
	
	/**
	 */
	protected IScheduleTaskDeal<T> taskDealBean;
	
	/**
	 */
	Comparator<T> taskComparator;

	StatisticsInfo statisticsInfo;

	protected List<T> taskList = Collections.synchronizedList(new ArrayList<T>());
	/**
	 */
	protected List<Object> runningTaskList = Collections.synchronizedList(new ArrayList<Object>());
	/**
	 */
	protected List<T> maybeRepeatTaskList = Collections.synchronizedList(new ArrayList<T>());

	Lock lockFetchID = new ReentrantLock();
	Lock lockFetchMutilID = new ReentrantLock();
	Lock lockLoadData = new ReentrantLock();
	/**
	 */
	boolean isMutilTask = false;
	
	/**

	 */
	boolean isStopSchedule = false;
	boolean isSleeping = false;
	
	/**





	 */
	public TBScheduleProcessorNotSleep(TBScheduleManager aManager,
			IScheduleTaskDeal<T> aTaskDealBean,StatisticsInfo aStatisticsInfo) throws Exception {
		this.scheduleManager = aManager;
		this.statisticsInfo = aStatisticsInfo;
		this.taskTypeInfo = this.scheduleManager.getTaskTypeInfo();
		this.taskDealBean = aTaskDealBean;
		this.taskComparator = new MYComparator(this.taskDealBean.getComparator());
		if (this.taskDealBean instanceof IScheduleTaskDealSingle<?>) {
			if (taskTypeInfo.getExecuteNumber() > 1) {
				taskTypeInfo.setExecuteNumber(1);
			}
			isMutilTask = false;
		} else {
			isMutilTask = true;
		}
		if (taskTypeInfo.getFetchDataNumber() < taskTypeInfo.getThreadNumber() * 10) {
			logger.warn("");
		}
		for (int i = 0; i < taskTypeInfo.getThreadNumber(); i++) {
			this.startThread(i);
		}
	}

	/**

	 * @throws Exception
	 */
	public void stopSchedule() throws Exception {

		this.isStopSchedule = true;

		this.taskList.clear();
	}

	private void startThread(int index) {
		Thread thread = new Thread(this);
		threadList.add(thread);
		String threadName = this.scheduleManager.getScheduleServer().getTaskType()+"-"
				+ this.scheduleManager.getCurrentSerialNumber() + "-exe"
				+ index;
		thread.setName(threadName);
		thread.start();
	}
	
	@SuppressWarnings("unchecked")
	protected boolean isDealing(T aTask) {
		if (this.maybeRepeatTaskList.size() == 0) {
			return false;
		}
		T[] tmpList = (T[]) this.maybeRepeatTaskList.toArray();
		for (int i = 0; i < tmpList.length; i++) {
			if(this.taskComparator.compare(aTask, tmpList[i]) == 0){
				this.maybeRepeatTaskList.remove(tmpList[i]);
				return true;
			}
		}
		return false;
	}

	/**


	 * @return
	 */
	public T getScheduleTaskId() {
		lockFetchID.lock();
		try {
			T result = null;
			while (true) {
				if (this.taskList.size() > 0) {
					result = this.taskList.remove(0);
				} else {
					return null;
				}
				if (this.isDealing(result) == false) {
					return result;
				}
			}
		} finally {
			lockFetchID.unlock();
		}
	}
	/**


	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T[] getScheduleTaskIdMulti() {
		lockFetchMutilID.lock();
		try {
			if (this.taskList.size() == 0) {
				return null;
			}
			int size = taskList.size() > taskTypeInfo.getExecuteNumber() ? taskTypeInfo
					.getExecuteNumber() : taskList.size();

			List<T> result = new ArrayList<T>();
			int point = 0;
			T tmpObject = null;
			while (point < size
					&& ((tmpObject = this.getScheduleTaskId()) != null)) {
				result.add(tmpObject);
				point = point + 1;
			}
			if (result.size() == 0) {
				return null;
			} else {
				return (T[]) result.toArray((T[]) Array.newInstance(result.get(0).getClass(), 0));
			}
		} finally {
			lockFetchMutilID.unlock();
		}
	}
	
	public void clearAllHasFetchData(){
		this.taskList.clear();
	}
    public boolean isDealFinishAllData(){
    	return this.taskList.size() == 0 && this.runningTaskList.size() ==0;  
    }
    
    public boolean isSleeping(){
    	return this.isSleeping;
    }
    /**

     * @return
     */
	protected int loadScheduleData() {
		lockLoadData.lock();
		try {
			if (this.taskList.size() > 0 || this.isStopSchedule == true) {
				return this.taskList.size();
			}

			try {
				if (this.taskTypeInfo.getSleepTimeInterval() > 0) {
					if (logger.isTraceEnabled()) {
						logger.trace(""
								+ this.taskTypeInfo.getSleepTimeInterval());
					}
					this.isSleeping = true;
					Thread.sleep(taskTypeInfo.getSleepTimeInterval());
					this.isSleeping = false;
					
					if (logger.isTraceEnabled()) {
						logger.trace("");
					}
				}
			} catch (Throwable ex) {
				logger.error("", ex);
			}

			putLastRunningTaskList();

			try {
				List<TaskItemDefine> taskItems = this.scheduleManager
						.getCurrentScheduleTaskItemList();

				if (taskItems.size() > 0) {
					List<T> tmpList = this.taskDealBean.selectTasks(
							taskTypeInfo.getTaskParameter(),
							scheduleManager.getScheduleServer()
									.getOwnSign(), this.scheduleManager.getTaskItemCount(), taskItems,
							taskTypeInfo.getFetchDataNumber());
					scheduleManager.getScheduleServer().setLastFetchDataTime(new Timestamp(scheduleManager.scheduleCenter.getSystemTime()));
					if (tmpList != null) {
						this.taskList.addAll(tmpList);
					}
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("");
					}
				}
				addFetchNum(taskList.size(),
						"TBScheduleProcessor.loadScheduleData");
				if (taskList.size() <= 0) {

					if (this.scheduleManager.isContinueWhenData() == true) {
						if (taskTypeInfo.getSleepTimeNoData() > 0) {
							if (logger.isDebugEnabled()) {
								logger.debug("sleep "
										+ taskTypeInfo.getSleepTimeNoData());
							}
							this.isSleeping = true;
							Thread.sleep(taskTypeInfo.getSleepTimeNoData());
							this.isSleeping = false;							
						}
					}
				}
				return this.taskList.size();
			} catch (Throwable ex) {
				logger.error("��ȡ������ݴ���", ex);
			}
			return 0;
		} finally {
			lockLoadData.unlock();
		}
	}
	/**

	 */
	@SuppressWarnings("unchecked")
	public void putLastRunningTaskList() {
		lockFetchID.lock();
		try {
			this.maybeRepeatTaskList.clear();
			if (this.runningTaskList.size() == 0) {
				return;
			}
			Object[] tmpList = this.runningTaskList.toArray();
			for (int i = 0; i < tmpList.length; i++) {
				if (this.isMutilTask == false) {
					this.maybeRepeatTaskList.add((T) tmpList[i]);
				} else {
					T[] aTasks = (T[]) tmpList[i];
					for (int j = 0; j < aTasks.length; j++) {
						this.maybeRepeatTaskList.add(aTasks[j]);
					}
				}
			}
		} finally {
			lockFetchID.unlock();
		}
	}
	
	/**

	 */
	@SuppressWarnings("unchecked")
	public void run() {
		long startTime = 0;
		long sequence = 0;
		Object executeTask = null;
		while (true) {
			try {
				if (this.isStopSchedule == true) {
					synchronized (this.threadList) {
						this.threadList.remove(Thread.currentThread());
						if(this.threadList.size()==0){
							this.scheduleManager.unRegisterScheduleServer();
						}
					}
					return;
				}

				if (this.isMutilTask == false) {
					executeTask = this.getScheduleTaskId();
				} else {
					executeTask = this.getScheduleTaskIdMulti();
				}
				if (executeTask == null ) {
					this.loadScheduleData();
					continue;
				}
				
				try {
					this.runningTaskList.add(executeTask);
					startTime = scheduleManager.scheduleCenter.getSystemTime();
					sequence = sequence + 1;
					if (this.isMutilTask == false) {
						if (((IScheduleTaskDealSingle<Object>) this.taskDealBean).execute(executeTask,scheduleManager.getScheduleServer().getOwnSign()) == true) {
							addSuccessNum(1, scheduleManager.scheduleCenter.getSystemTime()
									- startTime,
									"com.taobao.pamirs.com.perfect.schedule.TBScheduleProcessorNotSleep.run");
						} else {
							addFailNum(1,scheduleManager.scheduleCenter.getSystemTime()
									- startTime,
									"com.taobao.pamirs.com.perfect.schedule.TBScheduleProcessorNotSleep.run");
						}
					} else {
						if (((IScheduleTaskDealMulti<Object>) this.taskDealBean)
								.execute((Object[]) executeTask,scheduleManager.getScheduleServer().getOwnSign()) == true) {
							addSuccessNum(((Object[]) executeTask).length, scheduleManager.scheduleCenter.getSystemTime()
									- startTime,
									"com.taobao.pamirs.com.perfect.schedule.TBScheduleProcessorNotSleep.run");
						} else {
							addFailNum(((Object[]) executeTask).length, scheduleManager.scheduleCenter.getSystemTime()
									- startTime,
									"com.taobao.pamirs.com.perfect.schedule.TBScheduleProcessorNotSleep.run");
						}
					}
				} catch (Throwable ex) {
					if (this.isMutilTask == false) {
						addFailNum(1, scheduleManager.scheduleCenter.getSystemTime() - startTime,
								"TBScheduleProcessor.run");
					} else {
						addFailNum(((Object[]) executeTask).length, scheduleManager.scheduleCenter.getSystemTime()
								- startTime,
								"TBScheduleProcessor.run");
					}
					logger.error("Task :" + executeTask + " error", ex);
				} finally {
					this.runningTaskList.remove(executeTask);
				}
			} catch (Throwable e) {
				throw new RuntimeException(e);

			}
		}
	}

	public void addFetchNum(long num, String addr) {
			this.statisticsInfo.addFetchDataCount(1);
			this.statisticsInfo.addFetchDataNum(num);
	}

	public void addSuccessNum(long num, long spendTime, String addr) {
			this.statisticsInfo.addDealDataSucess(num);
			this.statisticsInfo.addDealSpendTime(spendTime);
	}

	public void addFailNum(long num, long spendTime, String addr) {
			this.statisticsInfo.addDealDataFail(num);
			this.statisticsInfo.addDealSpendTime(spendTime);
	}
	
    class MYComparator implements Comparator<T> {
    	Comparator<T> comparator;
    	public MYComparator(Comparator<T> aComparator){
    		this.comparator = aComparator;
    	}

		public int compare(T o1, T o2) {
			statisticsInfo.addOtherCompareCount(1);
			return this.comparator.compare(o1, o2);
		}
    	public  boolean equals(Object obj){
    	 return this.comparator.equals(obj);
    	}
    }
    
}
