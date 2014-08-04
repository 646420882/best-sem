package com.perfect.schedule.core.taskmanager;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**

 * @author xuannan
 *
 */
public class ScheduleTaskType implements java.io.Serializable {
	
	/**

	 */
	private static final long serialVersionUID = 1L;
	/**

	 */
	private String baseTaskType;
    /**

     */
    private long heartBeatRate = 5*1000;//1����
    
    /**

     */
    private long judgeDeadInterval = 1*60*1000;//2����
    
    /**


     */
    private int sleepTimeNoData = 500;
    
    /**

     */
    private int sleepTimeInterval = 0;
    
    /**

     */
    private int fetchDataNumber = 500;
    
    /**

     */
    private int executeNumber =1;
    
    private int threadNumber = 5;
    
    /**

     */
    private String processorType="SLEEP" ;
    /**

     */
    private String permitRunStartTime;
    /**

     */
    private String permitRunEndTime;
    
    /**

     */
    private double expireOwnSignInterval = 1;
    
    /**

     */
    private String dealBeanName;
    /**

     */
    private String taskParameter;
    
    //�������ͣ���̬static,��̬dynamic
    private String taskKind = TASKKIND_STATIC;
    
    public static String TASKKIND_STATIC="static";
    public static String TASKKIND_DYNAMIC="dynamic";
 
    
    /**

     */
    private String[] taskItems;
    
    /**

     */
    private int maxTaskItemsOfOneThreadGroup = 0;
    /**

     */
    private long version;
    
    /**

     */
    private String sts = STS_RESUME;
	
    public static String STS_PAUSE="pause";
    public static String STS_RESUME="resume";
    
    public static String[] splitTaskItem(String str){
    	List<String> list = new ArrayList<String>();
		int start = 0;
		int index = 0;
    	while(index < str.length()){
    		if(str.charAt(index)==':'){
    			index = str.indexOf('}', index) + 1;
    			list.add(str.substring(start,index).trim());
    			while(index <str.length()){
    				if(str.charAt(index) ==' '){
    					index = index +1;
    				}else{
    					break;
    				}
    			}
    			index = index + 1; //����
    			start = index;
    		}else if(str.charAt(index)==','){
    			list.add(str.substring(start,index).trim());
    			while(index <str.length()){
    				if(str.charAt(index) ==' '){
    					index = index +1;
    				}else{
    					break;
    				}
    			}
    			index = index + 1; //����
    			start = index;
    		}else{
    			index = index + 1;
    		}
    	}
    	if(start < str.length()){
    		list.add(str.substring(start).trim());
    	}
    	return (String[]) list.toArray(new String[0]);
     }
    
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	
	public String getBaseTaskType() {
		return baseTaskType;
	}
	public void setBaseTaskType(String baseTaskType) {
		this.baseTaskType = baseTaskType;
	}
	public long getHeartBeatRate() {
		return heartBeatRate;
	}
	public void setHeartBeatRate(long heartBeatRate) {
		this.heartBeatRate = heartBeatRate;
	}

	public long getJudgeDeadInterval() {
		return judgeDeadInterval;
	}

	public void setJudgeDeadInterval(long judgeDeadInterval) {
		this.judgeDeadInterval = judgeDeadInterval;
	}

	public int getFetchDataNumber() {
		return fetchDataNumber;
	}

	public void setFetchDataNumber(int fetchDataNumber) {
		this.fetchDataNumber = fetchDataNumber;
	}

	public int getExecuteNumber() {
		return executeNumber;
	}

	public void setExecuteNumber(int executeNumber) {
		this.executeNumber = executeNumber;
	}

	public int getSleepTimeNoData() {
		return sleepTimeNoData;
	}

	public void setSleepTimeNoData(int sleepTimeNoData) {
		this.sleepTimeNoData = sleepTimeNoData;
	}

	public int getSleepTimeInterval() {
		return sleepTimeInterval;
	}

	public void setSleepTimeInterval(int sleepTimeInterval) {
		this.sleepTimeInterval = sleepTimeInterval;
	}

	public int getThreadNumber() {
		return threadNumber;
	}

	public void setThreadNumber(int threadNumber) {
		this.threadNumber = threadNumber;
	}

	public String getPermitRunStartTime() {
		return permitRunStartTime;
	}

	public String getProcessorType() {
		return processorType;
	}

	public void setProcessorType(String processorType) {
		this.processorType = processorType;
	}

	public void setPermitRunStartTime(String permitRunStartTime) {
		this.permitRunStartTime = permitRunStartTime;
		if(this.permitRunStartTime != null && this.permitRunStartTime.trim().length() ==0){
			this.permitRunStartTime = null;
		}	
	}

	public String getPermitRunEndTime() {
		return permitRunEndTime;
	}

	public double getExpireOwnSignInterval() {
		return expireOwnSignInterval;
	}
	public void setExpireOwnSignInterval(double expireOwnSignInterval) {
		this.expireOwnSignInterval = expireOwnSignInterval;
	}
	
	public String getDealBeanName() {
		return dealBeanName;
	}
	public void setDealBeanName(String dealBeanName) {
		this.dealBeanName = dealBeanName;
	}
	public void setPermitRunEndTime(String permitRunEndTime) {
		this.permitRunEndTime = permitRunEndTime;
		if(this.permitRunEndTime != null && this.permitRunEndTime.trim().length() ==0){
			this.permitRunEndTime = null;
		}	

	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	public void setTaskItems(String[] aTaskItems) {
		this.taskItems = aTaskItems;
	}
	public String[] getTaskItems() {
		return taskItems;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	public String getSts() {
		return sts;
	}
	public void setTaskKind(String taskKind) {
		this.taskKind = taskKind;
	}
	public String getTaskKind() {
		return taskKind;
	}
	public void setTaskParameter(String taskParameter) {
		this.taskParameter = taskParameter;
	}
	public String getTaskParameter() {
		return taskParameter;
	}

	public int getMaxTaskItemsOfOneThreadGroup() {
		return maxTaskItemsOfOneThreadGroup;
	}

	public void setMaxTaskItemsOfOneThreadGroup(int maxTaskItemsOfOneThreadGroup) {
		this.maxTaskItemsOfOneThreadGroup = maxTaskItemsOfOneThreadGroup;
	}
	
	
}
